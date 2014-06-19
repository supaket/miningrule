package th.ku.ac.mcpe.thesis.model;

import org.perf4j.LoggingStopWatch;
import org.perf4j.StopWatch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

public class RuleParser {

    private static Long trxnCount;
    private static BigInteger bitLenght;

    private static Map<Integer, List<I>> piMap = new HashMap<Integer, List<I>>();
    private static List<I> piTab = new ArrayList<I>();

    public static void main(String[] args) {
        try {

            StopWatch st1 = new LoggingStopWatch();

            StopWatch st = new LoggingStopWatch();

            RuleParser parser = new RuleParser();

            st1.start("rule P1[start].");

            List<I> rules = parser.parse(args[0]);

            List<String> trxLines = parseTrxnLines(args[1]);

            for (I rule : rules) {

                StringBuffer sb = new StringBuffer();

                st.start("processing rule i[" + rule.val + "[" + rule.type + "]" + "][start].");
                for (String line : trxLines) {
                    sb.append(isFreqItemFoundInTrxn(rule.patterns, line) ? "1" : "0");
                }
                st.stop("processing rule i[" + rule.val + "[" + rule.type + "]" + "][done].");
                if (rule.type == Type.p || rule.type == Type.ns) {
                    rule.bit = new BigInteger(sb.toString(), 2);
                } else {
                    // it's negative
                    rule.bit = new BigInteger(sb.toString(), 2).xor(RuleParser.bitLenght);
                }
            }

            // At here we get rule [p1,n,ns]
            st1.stop("rule P1[done].");

            // join p1 x n1
            List<I> l2Coll = new ArrayList<I>();
            List<I> n2Coll = new ArrayList<I>();

            st1.start("rule L2[start].");
            for (I ruleL : rules) {
                for (I ruleR : rules) {
                    if (ruleR.type == Type.n || ruleR.type == Type.ns) {
                        if (!(ruleL instanceof C && ruleR instanceof C)) {

                            l2 = new L(ruleL.val);
                            l2.addVal(ruleR.val);
                            l2.bit = ruleL.bit.and(ruleR.bit);

                            if (ruleL.type == Type.p) {
                                l2Coll.add(l2);
                            } else {
                                n2Coll.add(l2);
                            }
                        }
                    }
                }
            }
            st1.stop("rule L2[done].");
            // At here we get rule L2 [p1,n,ns]

            st1.start("rule Li[start].");
            int startFrom = 3;
            // cross join

            int i = startFrom;
            st1.stop("rule Li[done].");

            // Join Pi, Li, Ni with P1, N1
            // where i start from 3,4,5...n
            level2 = join(getPi(2), getNi(1));
            l2Coll n2Coll // == starting with P2, L2, N2

            List<List<I>> ruleR = new ArrayList<List<I>>();
            //TODO: Create Rule R ( P1, N1 )

            //TODO: Implement cross join here
            for (List<I> ruleL : ruleL) {

            }

        } catch (IOException e) {
            e.printStackTrace();
        } ;
    }

    private static void createPiTab(Integer level, HashMap<String, I> p1Tab) {
        piTab.put(level, p1Tab);
    }

    private static List<I> getPi(Integer level) {
        return piTab.get(level);
    }

    private static HashMap<String, I> createP1Map(List<I> rules) {
        HashMap<String, I> tab = new HashMap<String, I>();
        for (I rule : rules) {
            String s = ((String) (new ArrayList<String>(rule.val)).get(0));
            tab.put(s, rule);
        }
        return tab;
    }

    private static boolean isFreqItemFoundInTrxn(Collection<Pattern> patterns, String line) {
        for (Pattern p : patterns) {
            if (!p.matcher(line).find()) {
                return false;
            }
        }
        return true;
    }

    public static List<String> parseTrxnLines(String trxnFile) throws IOException {
        List<String> trxnLines = new ArrayList<String>();
        BufferedReader trxN = new BufferedReader(new FileReader(trxnFile));

        while (trxN.ready()) {
            trxnLines.add(trxN.readLine());
        }
        trxN.close();
        return trxnLines;
    }

    public List<I> parse(String ruleFile) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(ruleFile));

        boolean isFirstLine = true;
        List<I> p1n1ns1 = new ArrayList<I>();
        while (in.ready()) {

            String line = in.readLine();

            if (isFirstLine) {
                isFirstLine = false;
                trxnCount = Long.valueOf(line.replace('(', ' ').replace(')', ' ').trim());
                createXorBit();
                continue;
            }
            p1n1ns1.addAll(create_p(line));
        }

        in.close();
        return p1n1ns1;
    }

    private List<I> create_p(String line) {
        List<I> p1List = new ArrayList<I>();
        String[] split = line.split("\\s+");
        List<String> items = new ArrayList<String>(Arrays.asList(split).subList(0, split.length - 1));
        if (items.size() < 2) {
            String item = items.get(0);
            if (item.startsWith("1")) {

                C cp = new C(item);
                cp.type = Type.p;
                p1List.add(cp);

                C cn = new C(item);
                cn.type = Type.n;
                p1List.add(cn);

            } else {

                F fp = new F(item);
                fp.type = Type.p;
                p1List.add(fp);

                F fn = new F(item);
                fn.type = Type.n;
                p1List.add(fn);

            }
        } else {

            if (items.get(0).startsWith("1")) {

                if (isNotMixed(items, "1")) {
                    final C cNS = new C(items);
                    cNS.type = Type.ns;
                    p1List.add(cNS);
                }

                // class can be mixed if positive
                final C cPi = new C(items);
                cPi.type = Type.ps;
                p1List.add(cPi);
                List<I> iList = piTab.get(items.size());
                if (iList == null) {
                    ArrayList<I> psList = new ArrayList<I>();
                    psList.add(cPi);
                    piTab.put(items.size(), psList);
                } else {
                    iList.add(cPi);
                    piTab.put(items.size(), iList);
                }

            } else {

                if (isNotMixed(items, "2")) {
                    final F f = new F(items);
                    f.type = Type.ns;
                    p1List.add(f);
                }

                //feature can be mixed if positve
                final F fPi = new F(items);
                fPi.type = Type.ps;
                p1List.add(fPi);

                List<I> iList = piTab.get(items.size());
                if (iList == null) {
                    ArrayList<I> psList = new ArrayList<I>();
                    psList.add(fPi);
                    piTab.put(items.size(), psList);
                } else {
                    iList.add(fPi);
                    piTab.put(items.size(), iList);
                }

            }
        }
        return p1List;
    }

    public boolean isNotMixed(List<String> items, String startWith) {
        for (String item : items) {
            if (!item.startsWith(startWith)) {
                return false;
            }
        }
        return true;
    }

    private void createXorBit() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < trxnCount; i++) {
            sb.append('1');
        }
        bitLenght = new BigInteger(sb.toString(), 2);
    }

}

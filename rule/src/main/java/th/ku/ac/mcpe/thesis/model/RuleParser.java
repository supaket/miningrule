package th.ku.ac.mcpe.thesis.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.perf4j.LoggingStopWatch;
import org.perf4j.StopWatch;

public class RuleParser {

  private static Long trxnCount;
  private static BigInteger bitLenght;
  private static HashMap<String, I> p1Tab;

  public static void main(String[] args) {
    try {
      StopWatch st1 = new LoggingStopWatch();
      StopWatch st = new LoggingStopWatch();
      RuleParser parser = new RuleParser();
      st1.start("rule P1[start].");
      List<I> rules = parser.parse(args[0]);
      p1Tab = createP1Tab(rules);

      List<String> trxnLines = parseTrxnLines(args[1]);
      for (I rule : rules) {
        StringBuffer sb = new StringBuffer();

        st.start("processing rule i[" + rule.val + "[" + rule.type + "]" + "][start].");
        for (String line : trxnLines) {
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

              L l2 = new L(ruleL.val);
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

    } catch (IOException e) {
      e.printStackTrace();
    };
  }

  private static HashMap<String, I> createP1Tab(List<I> rules) {
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
        setTrxnCount(Long.valueOf(line.replace('(', ' ').replace(')', ' ').trim()));
        createXorBit();
        continue;
      }
      p1n1ns1.addAll(create_p(1, line));
    }

    in.close();
    return p1n1ns1;
  }

  private List<I> create_p(int i, String line) {
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
          final C c = new C(items);
          c.type = Type.ns;
          p1List.add(c);
        }
      } else {
        if (isNotMixed(items, "2")) {
          final F f = new F(items);
          f.type = Type.ns;
          p1List.add(f);
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
    for (int i = 0; i < getTrxnCount(); i++) {
      sb.append('1');
    }
    setBitLenght(new BigInteger(sb.toString(), 2));
  }

  public BigInteger getBitLenght() {
    return bitLenght;
  }

  public void setBitLenght(BigInteger bitLenght) {
    this.bitLenght = bitLenght;
  }

  public Long getTrxnCount() {
    return trxnCount;
  }

  public void setTrxnCount(Long trxnCount) {
    this.trxnCount = trxnCount;
  }
}

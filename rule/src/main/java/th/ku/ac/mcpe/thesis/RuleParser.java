package th.ku.ac.mcpe.thesis;

import th.ku.ac.mcpe.thesis.model.R;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RuleParser {

    public double supportThreshold;

    public List<R> parseItems(String itemsFile, String transFile, double supportThreshold) throws IOException {

        this.supportThreshold = supportThreshold;
        BufferedReader in = new BufferedReader(new FileReader(itemsFile));
        ArrayList<R> rules = new ArrayList<>();

        BigInteger xorBit = BigInteger.valueOf(0);

        List<String> transLines = parseTransLines(transFile);
        boolean isFirstLine = true;

        while (in.ready()) {

            String line = in.readLine();
            if (isFirstLine) {
                isFirstLine = false;
                xorBit = xorBit(Long.valueOf(line.replace('(', ' ').replace(')', ' ').trim()));
                continue;
            }
            rules.addAll(createRuleSet(line, transLines, xorBit));
        }

        in.close();
        return rules;
    }

    public List<String> parseTransLines(String transFile) throws IOException {

        List<String> transLines = new ArrayList<>();
        BufferedReader trxN = new BufferedReader(new FileReader(transFile));
        while (trxN.ready()) {
            transLines.add(trxN.readLine());
        }

        trxN.close();
        return transLines;
    }

    public List<R> createRuleSet(final String line, final List<String> transLines, BigInteger xorBit) {

        List<R> rules = getEmptyRules();
        List<R> positiveRulesSet = createPositiveRule(line, transLines, xorBit);
        rules.addAll(positiveRulesSet);
        rules.addAll(createNegativeRuleSet(positiveRulesSet));
        return rules;
    }

    private Collection<? extends R> createNegativeRuleSet(final List<R> positiveRulesSet) {

        List<R> negativeRules = getEmptyRules();
        for (R posRule : positiveRulesSet) {
            R r = posRule.negative();
            if (r != null) {
                negativeRules.add(r);
            }
        }
        return negativeRules;
    }

    public List<R> createPositiveRule(final String line, List<String> transLines, BigInteger xorBit) {
        List<R> rules = getEmptyRules();
        rules.add(new R(line, transLines, xorBit));
        return rules;
    }

    public BigInteger xorBit(Long transCount) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < transCount; i++) {
            sb.append('1');
        }
        return new BigInteger(sb.toString(), 2);
    }

    public List<R> getEmptyRules() {
        return new ArrayList<>();
    }
}
package th.ku.ac.mcpe.thesis.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class I {

    public BigInteger bit;
    public List<String>  values   = new ArrayList<>();
    public List<Pattern> patterns = new ArrayList<>();

    private List<Pattern> getPattern(Collection<String> items) {
        List<Pattern> patterns = new ArrayList<>();
        for (String item : items) {
            patterns.add(Pattern.compile("\\b(\\t)*" + item + "\\b(\\t)*"));
        }
        return patterns;
    }


    @Override
    public String toString() {
        return values.toString();
    }

    public I(final String value, List<String> transLines, BigInteger xorBit) {
        if (null != value) {
            String[] ir = value.split("\\s+");
            for (String item : ir) {
                values.add(item);
            }
            patterns = getPattern(values);
            bit = searchTrans(transLines);
        }
    }

    private BigInteger searchTrans(final List<String> transLines) {
        if (transLines != null) {
            StringBuffer sb = new StringBuffer();
            for (String line : transLines) {
                sb.append(isFreqItemFoundInTrans(line) ? "1" : "0");
            }
            return new BigInteger(sb.toString(), 2);
        }
        return null;
    }

    private boolean isFreqItemFoundInTrans(String line) {
        for (Pattern p : patterns) {
            if (!p.matcher(line).find()) {
                return false;
            }
        }
        return true;
    }
}

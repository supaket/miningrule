package th.ku.ac.mcpe.thesis.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class I {
    public Type       type;
    public int        level;
    public BigInteger bit;
    public List<String>  val      = new ArrayList<>();
    public List<Pattern> patterns = new ArrayList<>();

    public I(Type type, String value) {
        this(value);
        level = 1;
        this.type = type;
    }

    public I(Type type, List<String> values) {
        patterns = getPattern(val);
        level = (type == Type.ns) ? 1 : val.size();
    }

    public I(String i) {
        if (null != i) {
            String[] ir = i.split("\\s+");
            for (String item : ir) {
                val.add(item);
            }
            level = val.size();
            patterns = getPattern(val);
        }
    }

    private List<Pattern> getPattern(Collection<String> items) {
        List<Pattern> patterns = new ArrayList<>();
        for (String item : items) {
            patterns.add(Pattern.compile("\\b(\\t)*" + item + "\\b(\\t)*"));
        }
        return patterns;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass().isInstance(obj)) {
            if (((I) obj).val.size() == val.size()) {
                return ((I) obj).val.containsAll(val);
            }
        }
        if (obj instanceof String) {
            return val.equals(new I((String) obj));
        }
        return false;
    }

    @Override
    public String toString() {
        return val.toString();
    }

    public enum Type {
        p, ps, n, ns
    }

}

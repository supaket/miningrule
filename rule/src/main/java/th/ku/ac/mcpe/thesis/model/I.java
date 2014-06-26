package th.ku.ac.mcpe.thesis.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class I {

    public Type type;

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
    public boolean equals(Object obj) {
        if (getClass().isInstance(obj)) {
            if (((I) obj).values.size() == values.size()) {
                return ((I) obj).values.containsAll(values);
            }
        }
        if (obj instanceof String) {
            return values.equals(new I((String) obj));
        }
        return false;
    }


    @Override
    public String toString() {
        return values.toString();
    }

    public I(Type type, String value) {
        this(value);
        this.type = type;
    }

    public I(List<String> values) {
        this.values = values;
        patterns = getPattern(this.values);
    }

    public I(String value) {
        if (null != value) {
            String[] ir = value.split("\\s+");
            for (String item : ir) {
                values.add(item);
            }
            patterns = getPattern(values);
        }
    }

}

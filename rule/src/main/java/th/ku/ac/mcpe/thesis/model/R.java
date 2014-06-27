package th.ku.ac.mcpe.thesis.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class R {

    private String classAlpha   = "1";
    private String featureAlpha = "2";

    public Type type;
    public int  level;
    public List<I> values = new LinkedList<>();

    public String getRaw() {
        final StringBuffer sb = new StringBuffer();
        for (I val : values) {
            String item = val.values.get(0);
            if (item != null) {
                sb.append(item).append(" ");
            }
        }
        return sb.toString();
    }

    public R negative() {
        if (isItemsNotMixed()) {
            R negRule = new R(getRaw());
            negRule.type = getNegType();
            return negRule;
        }
        return null;
    }

    private Type getNegType() {
        switch (type) {
            case p:
                return Type.n;
            case ps:
                return Type.ns;
            default:
                return Type.ps;
        }
    }

    public R(String i) {
        type = getType(i);
        switch (type) {
            case n:
            case p:
                level = 1;
                if (isClass(i)) {
                    values.add(new C(i));
                } else {
                    values.add(new F(i));
                }
                break;
            case ns:
            case ps:
                if (null != i) {

                    String[] items = i.split("\\s+");
                    for (String item : items) {
                        if (isClass(item)) {
                            values.add(new C(item));
                        } else {
                            values.add(new F(item));
                        }
                    }
                    level = items.length;
                }
                break;
            default:
        }
    }

    private Type getType(final String items) {
        List<String> asList = split(items);
        if (isFirstLevel(asList)) {
            return Type.p;
        } else {
            return Type.ps;
        }
    }

    public boolean isItemsNotMixed() {
        return isItemsNotMixed(getRawList());
    }

    public List<String> getRawList() {
        return Arrays.asList(getRaw().split("\\s+"));
    }

    public boolean isItemsNotMixed(List<String> items) {
        return isClassNotMixed(items) || isFeatureNotMixed(items);
    }

    public boolean isClassNotMixed(List<String> items) {

        for (String item : items) {
            if (!item.startsWith(getClassAlpha())) {
                return false;
            }
        }
        return true;
    }

    public boolean isFeatureNotMixed(List<String> items) {

        for (String item : items) {
            if (!item.startsWith(getFeatureAlpha())) {
                return false;
            }
        }
        return true;
    }

    public String getClassAlpha() {
        return classAlpha;
    }

    public String getFeatureAlpha() {
        return featureAlpha;
    }

    private boolean isClass(final String item) {
        return item.startsWith("1");
    }

    private boolean isFirstLevel(List<String> items) {
        return items.size() < 2;
    }

    private List<String> split(final String s) {
        String[] split = s.split("\\s+");
        List<String> items = java.util.Arrays.asList(split);
        return new java.util.ArrayList<>(items);
    }
}

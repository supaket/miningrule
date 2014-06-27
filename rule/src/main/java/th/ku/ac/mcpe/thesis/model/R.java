package th.ku.ac.mcpe.thesis.model;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class R {

    public Type       type;
    public int        level;
    public BigInteger bit;

    private String  classAlpha   = "1";
    private String  featureAlpha = "2";
    public  List<I> values       = new LinkedList<>();

    public R() {
    }

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

    public R negative(final BigInteger xorBit) {
        if (isItemsNotMixed()) {
            R nr = new R();
            nr.bit = bit.xor(xorBit);
            nr.type = getNegType();
            if (values.size() != 1) {
                nr.level = values.size();
            } else {
                nr.level = 1;
            }
            nr.values.addAll(values);
            return nr;
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

    public R(String i, final List<String> transLines, final BigInteger xorBit) {
        if (bit == null) {
            bit = xorBit.xor(xorBit);
        }

        type = getType(i);

        switch (type) {
            case n:
            case p:
                level = 1;
                if (isClass(i)) {
                    C r = new C(i, transLines, xorBit);
                    bit = bit.and(r.bit);
                    values.add(r);
                } else {
                    F r = new F(i, transLines, xorBit);
                    bit = bit.and(r.bit);
                    values.add(r);
                }
                break;
            case ns:
            case ps:
                if (null != i) {

                    String[] items = i.split("\\s+");
                    for (String item : items) {
                        if (isClass(item)) {
                            C r = new C(item, transLines, xorBit);
                            bit = bit.and(r.bit);
                            values.add(r);
                        } else {
                            F r = new F(item, transLines, xorBit);
                            bit = bit.and(r.bit);
                            values.add(r);
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

package th.ku.ac.mcpe.thesis.model;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

public class R {

    public List<C> pClass    = new LinkedList<>();
    public List<F> pFeature  = new LinkedList<>();
    public List<C> psClass   = new LinkedList<>();
    public List<F> psFeature = new LinkedList<>();

    public List<C> nClass    = new LinkedList<>();
    public List<F> nFeature  = new LinkedList<>();
    public List<C> nsClass   = new LinkedList<>();
    public List<F> nsFeature = new LinkedList<>();

    public R(String s) {

        java.util.List<String> items = getItems(s);

        String item = items.get(0);

        if (isFirstLevel(items)) {
            if (item.startsWith("1")) {
                //115 (704)
                pClass.add(getItem(C.class, I.Type.p, item));
                nClass.add(getItem(C.class, I.Type.n, item));
            } else {
                //210 (292)
                pFeature.add(getItem(F.class, I.Type.p, item));
                nFeature.add(getItem(F.class, I.Type.n, item));
            }
        } else {

            String CLASS_ALPHA = "1";
            String FEATURE_ALPHA = "2";

            if (isNotMixed(items, CLASS_ALPHA)) {
                // 15737 15839 (215)
                psClass.add(getItem(C.class, I.Type.ps, items));
                nsClass.add(getItem(C.class, I.Type.ns, items));
            } else if (isNotMixed(items, FEATURE_ALPHA)) {
                // 2232 250268 (242)
                psFeature.add(getItem(F.class, I.Type.ps, items));
                nsFeature.add(getItem(F.class, I.Type.ns, items));
            } else {
                // mixed we don't use this value
                // If positive class and feature
                // 2843 2844 15618 15737 250975 (434)
                // 278 15743 116021 170469 250857 250999 (233)
            }
        }
    }

    private java.util.List<String> getItems(final String s) {
        String[] split = s.split("\\s+");
        List<String> items = java.util.Arrays.asList(split).subList(0, split.length - 1);
        return new java.util.ArrayList<>(items);
    }

    public boolean isNotMixed(List<String> items, String startWith) {
        for (String item : items) {
            if (!item.startsWith(startWith)) {
                return false;
            }
        }
        return true;
    }

    private <T extends I> T getItem(Class<T> cls, I.Type type, String val) {
        try {
            T i = cls.getDeclaredConstructor(I.Type.class, String.class).newInstance(type, val);
            return i;
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T extends I> T getItem(Class<T> cls, I.Type type, List<String> val) {
        try {
            T i = cls.getDeclaredConstructor(I.Type.class, List.class).newInstance(type, val);
            return i;
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isFirstLevel(List<String> items) {
        return items.size() < 2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof R) {
            R r = ((R) obj);
            return eq(r.pClass, pClass) && eq(r.pFeature, pFeature);
        }
        return false;
    }

    private boolean eq(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 != null) {
            return o1.equals(o2);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("C[");
        sb.append(String.valueOf(pClass));
        sb.append("]");
        sb.append(",");
        sb.append("F[");
        sb.append(String.valueOf(pFeature));
        sb.append("]");
        return sb.toString();
    }
}

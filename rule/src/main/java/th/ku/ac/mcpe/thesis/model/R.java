package th.ku.ac.mcpe.thesis.model;

import java.util.LinkedList;
import java.util.List;

public class R {

    public int level;
    public List<I> values = new LinkedList<>();

    public R(String i, Type type) {
        switch (type) {
            case n:
            case p:
                if (isClass(i)) {
                    values.add(new C(i));
                } else {
                    values.add(new F(i));
                }
                break;
            case ns:
            case ps:
                if (null != i) {
                    String[] ir = i.split("\\s+");
                    for (String item : ir) {
                        if (isClass(item)) {
                            values.add(new C(item));
                        } else {
                            values.add(new F(item));
                        }
                    }
                }
                break;
            default:
        }
    }

    private boolean isClass(final String item) {
        return item.startsWith("1");
    }
}

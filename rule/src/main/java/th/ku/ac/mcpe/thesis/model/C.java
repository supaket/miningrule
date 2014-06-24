package th.ku.ac.mcpe.thesis.model;

import java.util.List;

public class C extends I {

    public C(Type type, String value) {
        super(type, value);
    }

    public C(Type type, List<String> values) {
        super(type, values);
    }

    public C(String i) {
        super(i);
    }
}

package th.ku.ac.mcpe.thesis.model;

import java.util.List;


public class F extends I {

    public F(Type type, String value) {
        super(type, value);
    }

    public F(Type type, List<String> values) {
        super(type, values);
    }

    public F(String i) {
        super(i);
    }
}

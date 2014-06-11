package th.ku.ac.mcpe.thesis;

import th.ku.ac.mcpe.thesis.model.RuleParser;

import java.io.IOException;

public class Startup {
    public static void main(String[] args) throws IOException {
        RuleParser rp = new RuleParser();
        rp.parse(args[0]);
    }
}

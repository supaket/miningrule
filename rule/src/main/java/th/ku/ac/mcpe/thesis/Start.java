package th.ku.ac.mcpe.thesis;

import java.io.IOException;

public class Start {

    public static void main(String[] args) {
        try {
            RuleParser rp = new RuleParser();
            String itemsFile = "/Users/tomz/Thesis/Test/C/seqCno_sup001_1234.txt";
            String transFile = "/Users/tomz/Thesis/Test/B/seqCno1234.txt";

            rp.parseItems(itemsFile, transFile, 0.01d);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

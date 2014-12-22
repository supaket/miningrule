package th.ku.ac.mcpe.thesis;

import junit.framework.Assert;
import org.testng.annotations.Test;
import th.ku.ac.mcpe.thesis.model.R;
import th.ku.ac.mcpe.thesis.model.Type;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class RuleParserTest {

    String transFile = "resources/test/transactionFile_ex.txt";
    String seqFile   = "resources/test/parse_posClass_seqCno_sup001_1234.txt";

    @Test
    public void parseItems$FileName_name_sup001_1234_text$ruleParserWithThreshold001() throws IOException {

        final RuleParser rp = new RuleParser();
        rp.parseItems(seqFile, transFile, 0.01d);
        Assert.assertEquals(rp.supportThreshold, 0.01d);

    }

    @Test
    public void createXorBit$10L$1111111111() {

        final RuleParser rp = new RuleParser();
        String expectBit = "1111111111";
        BigInteger bit = rp.xorBit(10l);
        Assert.assertEquals(expectBit, bit.toString(2));

    }

    @Test
    public void createRule$1positiveClass$1Positive1NegativeRule() throws IOException {

        long transCount = 10l;// 1111111111

        RuleParser rp = new RuleParser();
        List<R> rules = rp.createRuleSet("111", rp.parseTransLines(transFile), rp.xorBit(transCount));

        Assert.assertEquals(2, rules.size());

        String expectedPositiveBit = "0100010001";
        Assert.assertEquals(expectedPositiveBit, rules.get(0).toString());
        Assert.assertEquals(Type.p, rules.get(0).type);
        Assert.assertEquals(1, rules.get(0).level);

        //check only 1 class that 111 is a value of class
        Assert.assertEquals(1, rules.get(0).values.size(), 1);
        Assert.assertEquals("111", rules.get(0).values.get(0).values.get(0).toString());
        Assert.assertEquals("0100010001", rules.get(0).values.get(0).bit.toString());

        String expectedNegativeBit = "1011101110";
        Assert.assertEquals(expectedNegativeBit, rules.get(1).toString());
        Assert.assertEquals(Type.n, rules.get(1).type);
        Assert.assertEquals(1, rules.get(1).level);


    }

    @Test
    public void createRule_positiveFeature_expectPositiveNegativeRule() throws IOException {

        long transCount = 10l;// 1111111111


        RuleParser rp = new RuleParser();
        List<R> rules = rp.createRuleSet("211", rp.parseTransLines(transFile), rp.xorBit(transCount));

        Assert.assertEquals(2, rules.size());

        String expectedPositiveBit = "0001110101";
        Assert.assertEquals(expectedPositiveBit, rules.get(0).toString());
        Assert.assertEquals(Type.p, rules.get(0).type);

        String expectedNegativeBit = "1110001010";
        Assert.assertEquals(expectedNegativeBit, rules.get(1).toString());
        Assert.assertEquals(Type.n, rules.get(1).type);

    }

    @Test
    public void createRule_positiveMixL3_expectPositiveSetL3Rule() throws IOException {

        RuleParser rp = new RuleParser();
        List<R> rules = rp.createRuleSet("111 122 211", rp.parseTransLines(transFile), rp.xorBit(10l));
        Assert.assertEquals(1, rules.size());

        String expectedPositiveBit = "0000010000";
        Assert.assertEquals(expectedPositiveBit, rules.get(0).toString());
        Assert.assertEquals(Type.ps, rules.get(0).type);
        Assert.assertEquals(3, rules.get(0).level);

    }

    @Test
    public void createRule_positiveMixL4_expectPositiveSetL4Rule() throws IOException {

        RuleParser rp = new RuleParser();
        List<R> rules = rp.createRuleSet("111 122 211 222", rp.parseTransLines(transFile), rp.xorBit(10l));
        Assert.assertEquals(1, rules.size());

        String expectedPositiveBit = "0000010000";
        Assert.assertEquals(expectedPositiveBit, rules.get(0).toString());
        Assert.assertEquals(Type.ps, rules.get(0).type);
        Assert.assertEquals(4, rules.get(0).level);

    }

    @Test
    public void createRule_positiveMixL3_expectPositiveSetL3NegativeSetL1Rule() throws IOException {

        RuleParser rp = new RuleParser();
        List<R> rules = rp.createRuleSet("111 122 133", rp.parseTransLines(transFile), rp.xorBit(10l));
        Assert.assertEquals(2, rules.size());

        String expectedPositiveBit = "0100000000";
        Assert.assertEquals(expectedPositiveBit, rules.get(0).toString());
        Assert.assertEquals(Type.ps, rules.get(0).type);
        Assert.assertEquals(3, rules.get(0).level);

    }

    @Test
    public void createRule_positiveClassL2_expectPositiveSetL2NegativeSet1() throws IOException {

        RuleParser rp = new RuleParser();
        List<R> rules = rp.createRuleSet("111 122", rp.parseTransLines(transFile), rp.xorBit(10l));
        Assert.assertEquals(2, rules.size());

    }

    @Test
    public void createRule_positiveFeatureL2_expectPoisitiveSetL2NegativeSet1() throws IOException {

        RuleParser rp = new RuleParser();
        List<R> rules = rp.createRuleSet("211 222", rp.parseTransLines(transFile), rp.xorBit(10l));
        Assert.assertEquals(2, rules.size());

    }

    @Test
    public void createRule_positiveMixL2_expectPositiveSetL2() throws IOException {

        RuleParser rp = new RuleParser();
        List<R> rules = rp.createRuleSet("111 211", rp.parseTransLines(transFile), rp.xorBit(10l));
        Assert.assertEquals(1, rules.size());

    }
}

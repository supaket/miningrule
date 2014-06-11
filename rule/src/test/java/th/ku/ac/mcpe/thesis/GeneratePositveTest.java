package th.ku.ac.mcpe.thesis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

public class GeneratePositveTest {

    @Test
    public void testSameType() {

        GenerateL1 g = new GenerateL1();

        String s1 = "15622 228 228";
        String s2 = "142735    2305 2305";
        String s3 = "112507    1139    130670  131901  142612  171556  2290    250835";
        Assert.assertFalse(g.isNotMixed(s1.split("\\s+")));
        Assert.assertFalse(g.isNotMixed(s2.split("\\s+")));
        Assert.assertFalse(g.isNotMixed(s3.split("\\s+")));

        String s4 = "15622 1228";
        String s5 = "142735    12305";
        String s6 = "112507    1139    130670  131901  142612  171556  12290    1250835";
        Assert.assertTrue(g.isNotMixed(s4.split("\\s+")));
        Assert.assertTrue(g.isNotMixed(s5.split("\\s+")));
        Assert.assertTrue(g.isNotMixed(s6.split("\\s+")));
    }

    @Test
    public void testSameTypeFile() {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("/Users/tomz/Thesis/Test/C/seqCno_sup001_1234.txt"));
            GenerateL1 g = new GenerateL1();
            int lineCount = 0;
            while (in.ready()) {
                String line = in.readLine();
                if (g.isNotMixed(line.split("\\s+"))) {
                    lineCount++;
                }
            }
            Assert.assertEquals(lineCount, 772);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    @Test
    public void testIsFoundNegativeMustNotFoundInNeativeSet() {
        String s1 = "115 (704)";
        String s2 = "210 (292)";
        String s3 = "212 (404)";

        GenerateL1 g = new GenerateL1();
        Assert.assertNotNull(g.getMatcher(g.nPattern, s1));
        Assert.assertNotNull(g.getMatcher(g.nPattern, s2));
        Assert.assertNotNull(g.getMatcher(g.nPattern, s3));

        Assert.assertNull(g.getMatcher(g.nsPattern, s1));
        Assert.assertNull(g.getMatcher(g.nsPattern, s2));
        Assert.assertNull(g.getMatcher(g.nsPattern, s3));
    }

    @Test
    public void testIsFoundNegativeSetMustNotFoundInNeative() {
        String s1 = "116021 250253 (170)";
        String s2 = "2854 251476 (173)";
        String s3 = "250404 250405 (171)";
        String s4 = "2217 250850 (174)";
        String s5 = "2238 250262 (173)";

        GenerateL1 g = new GenerateL1();
        Assert.assertNotNull(g.getMatcher(g.nsPattern, s1));
        Assert.assertNotNull(g.getMatcher(g.nsPattern, s2));
        Assert.assertNotNull(g.getMatcher(g.nsPattern, s3));
        Assert.assertNotNull(g.getMatcher(g.nsPattern, s4));
        Assert.assertNotNull(g.getMatcher(g.nsPattern, s5));

        Assert.assertNull(g.getMatcher(g.nPattern, s1));
        Assert.assertNull(g.getMatcher(g.nPattern, s2));
        Assert.assertNull(g.getMatcher(g.nPattern, s3));
        Assert.assertNull(g.getMatcher(g.nPattern, s2));
        Assert.assertNull(g.getMatcher(g.nPattern, s3));
    }

}

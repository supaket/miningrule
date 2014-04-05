package th.ku.ac.mcpe.thesis;

import org.testng.Assert;
import org.testng.annotations.Test;

public class GenerateL2Test {

  @Test
  public void testLookupPInNL1() {
    String inputP = "115 (704)\t0000000000000000000000000000000000000000";

    String nl1Case1 = "~115 (168878)\t111111111111111111111111111111111111";
    String nl1Case2 = "~113 (168878)\t111111111111111111111111111111111111";
    String nl1Case3 = "~(210 115) (168878)\t111111111111111111111111111111111111";
    String nl1Case4 = "~(212 214) (168878)\t111111111111111111111111111111111111";

    GenerateL2 generateL2 = new GenerateL2();
    Assert.assertTrue(generateL2.isFound(inputP, nl1Case1));
    Assert.assertFalse(generateL2.isFound(inputP, nl1Case2));
    Assert.assertTrue(generateL2.isFound(inputP, nl1Case3));
    Assert.assertFalse(generateL2.isFound(inputP, nl1Case4));
  }
}

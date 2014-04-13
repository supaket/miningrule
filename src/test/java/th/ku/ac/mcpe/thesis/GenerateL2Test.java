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

    GenerateL2_1 generateL2 = new GenerateL2_1();
    Assert.assertTrue(generateL2.isFound(inputP, nl1Case1));
    Assert.assertFalse(generateL2.isFound(inputP, nl1Case2));
    Assert.assertTrue(generateL2.isFound(inputP, nl1Case3));
    Assert.assertFalse(generateL2.isFound(inputP, nl1Case4));
  }

  @Test
  public void testJoinPNL1Case1() {
    String inputP1 = "115 (704)\t0000000000";

    String nl1Case1 = "~113 (168878)\t1111111111";
    String nl1Case2 = "~(212 214) (168878)\t0000000000";
    String nl1Case3 = "~(212 214) (168878)\t0000011111";
    String nl1Case4 = "~(212 214) (168878)\t1111100000";
    String nl1Case5 = "~(212 214) (168878)\t1010101010";
    String nl1Case6 = "~(212 214) (168878)\t0101010101";

    String resultNl1Case1 = "115 ~113 (0)\t0000000000";
    String resultNl1Case2 = "115 ~(212 214) (0)\t0000000000";
    String resultNl1Case3 = "115 ~(212 214) (0)\t0000000000";
    String resultNl1Case4 = "115 ~(212 214) (0)\t0000000000";
    String resultNl1Case5 = "115 ~(212 214) (0)\t0000000000";
    String resultNl1Case6 = "115 ~(212 214) (0)\t0000000000";

    GenerateL2_1 generateL2 = new GenerateL2_1();

    Assert.assertEquals(generateL2.join(inputP1, nl1Case1), resultNl1Case1);
    Assert.assertEquals(generateL2.join(inputP1, nl1Case2), resultNl1Case2);
    Assert.assertEquals(generateL2.join(inputP1, nl1Case3), resultNl1Case3);
    Assert.assertEquals(generateL2.join(inputP1, nl1Case4), resultNl1Case4);
    Assert.assertEquals(generateL2.join(inputP1, nl1Case5), resultNl1Case5);
    Assert.assertEquals(generateL2.join(inputP1, nl1Case6), resultNl1Case6);

  }

  @Test
  public void testJoinPNL1Case2() {
    String inputP1 = "115 (704)\t1111100000";

    String nl1Case1 = "~113 (168878)\t1111111111";
    String nl1Case2 = "~(212 214) (168878)\t0000000000";
    String nl1Case3 = "~(212 214) (168878)\t0000011111";
    String nl1Case4 = "~(212 214) (168878)\t1111100000";
    String nl1Case5 = "~(212 214) (168878)\t1010101010";
    String nl1Case6 = "~(212 214) (168878)\t0101010101";

    String rnl1Case1 = "115 ~113 (5)\t1111100000";
    String rnl1Case2 = "115 ~(212 214) (0)\t0000000000";
    String rnl1Case3 = "115 ~(212 214) (0)\t0000000000";
    String rnl1Case4 = "115 ~(212 214) (5)\t1111100000";
    String rnl1Case5 = "115 ~(212 214) (3)\t1010100000";
    String rnl1Case6 = "115 ~(212 214) (2)\t0101000000";

    GenerateL2_1 generateL2 = new GenerateL2_1();
    Assert.assertEquals(generateL2.join(inputP1, nl1Case1), rnl1Case1);
    Assert.assertEquals(generateL2.join(inputP1, nl1Case2), rnl1Case2);
    Assert.assertEquals(generateL2.join(inputP1, nl1Case3), rnl1Case3);
    Assert.assertEquals(generateL2.join(inputP1, nl1Case4), rnl1Case4);
    Assert.assertEquals(generateL2.join(inputP1, nl1Case5), rnl1Case5);
    Assert.assertEquals(generateL2.join(inputP1, nl1Case6), rnl1Case6);
  }

  @Test
  public void testJoinPNL1Case3() {
    String inputP1 = "115 (704)\t0000011111";

    String nl1Case1 = "~113 (168878)\t1111111111";
    String nl1Case2 = "~(212 214) (168878)\t0000000000";
    String nl1Case3 = "~(212 214) (168878)\t0000011111";
    String nl1Case4 = "~(212 214) (168878)\t1111100000";
    String nl1Case5 = "~(212 214) (168878)\t1010101010";
    String nl1Case6 = "~(212 214) (168878)\t0101010101";

    String rnl1Case1 = "115 ~113 (5)\t0000011111";
    String rnl1Case2 = "115 ~(212 214) (0)\t0000000000";
    String rnl1Case3 = "115 ~(212 214) (5)\t0000011111";
    String rnl1Case4 = "115 ~(212 214) (0)\t0000000000";
    String rnl1Case5 = "115 ~(212 214) (2)\t0000001010";
    String rnl1Case6 = "115 ~(212 214) (3)\t0000010101";

    GenerateL2_1 generateL2 = new GenerateL2_1();
    Assert.assertEquals(generateL2.join(inputP1, nl1Case1), rnl1Case1);
    Assert.assertEquals(generateL2.join(inputP1, nl1Case2), rnl1Case2);
    Assert.assertEquals(generateL2.join(inputP1, nl1Case3), rnl1Case3);
    Assert.assertEquals(generateL2.join(inputP1, nl1Case4), rnl1Case4);
    Assert.assertEquals(generateL2.join(inputP1, nl1Case5), rnl1Case5);
    Assert.assertEquals(generateL2.join(inputP1, nl1Case6), rnl1Case6);
  }

  @Test
  public void testJoinPNL1Case4() {
    String inputP1 = "115 (704)\t1111111111";

    String nl1Case1 = "~113 (168878)\t1111111111";
    String nl1Case2 = "~(212 214) (168878)\t0000000000";
    String nl1Case3 = "~(212 214) (168878)\t0000011111";
    String nl1Case4 = "~(212 214) (168878)\t1111100000";
    String nl1Case5 = "~(212 214) (168878)\t1010101010";
    String nl1Case6 = "~(212 214) (168878)\t0101010101";

    String rnl1Case1 = "115 ~113 (10)\t1111111111";
    String rnl1Case2 = "115 ~(212 214) (0)\t0000000000";
    String rnl1Case3 = "115 ~(212 214) (5)\t0000011111";
    String rnl1Case4 = "115 ~(212 214) (5)\t1111100000";
    String rnl1Case5 = "115 ~(212 214) (5)\t1010101010";
    String rnl1Case6 = "115 ~(212 214) (5)\t0101010101";
    
    GenerateL2_1 generateL2 = new GenerateL2_1();
    Assert.assertEquals(generateL2.join(inputP1, nl1Case1), rnl1Case1);
    Assert.assertEquals(generateL2.join(inputP1, nl1Case2), rnl1Case2);
    Assert.assertEquals(generateL2.join(inputP1, nl1Case3), rnl1Case3);
    Assert.assertEquals(generateL2.join(inputP1, nl1Case4), rnl1Case4);
    Assert.assertEquals(generateL2.join(inputP1, nl1Case5), rnl1Case5);
    Assert.assertEquals(generateL2.join(inputP1, nl1Case6), rnl1Case6);
  }

  @Test
  public void testAnBit() {
    GenerateL2_1 gen2 = new GenerateL2_1();
    String input1 = "11";
    String input2 = "00";

    String case1 = "00";
    String case2 = "01";
    String case3 = "10";
    String case4 = "11";

    Assert.assertEquals(gen2.anbit(input1, case1).getPostiveBit(), "00");
    Assert.assertEquals(gen2.anbit(input1, case2).getPostiveBit(), "01");
    Assert.assertEquals(gen2.anbit(input1, case3).getPostiveBit(), "10");
    Assert.assertEquals(gen2.anbit(input1, case4).getPostiveBit(), "11");

    Assert.assertEquals(gen2.anbit(input2, case1).getPostiveBit(), "00");
    Assert.assertEquals(gen2.anbit(input2, case2).getPostiveBit(), "00");
    Assert.assertEquals(gen2.anbit(input2, case3).getPostiveBit(), "00");
    Assert.assertEquals(gen2.anbit(input2, case4).getPostiveBit(), "00");

  }
}

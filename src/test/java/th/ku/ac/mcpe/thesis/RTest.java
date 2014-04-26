package th.ku.ac.mcpe.thesis;

import junit.framework.Assert;

import org.testng.annotations.Test;

import th.ku.ac.mcpe.thesis.model.C;
import th.ku.ac.mcpe.thesis.model.R;

public class RTest {

  @Test
  public void testCreateRuleWithPositiveClassItem() {

    C c = new C("115");
    C expectedC = new C("115");
    R r = new R(c, null, null, null);
    Assert.assertEquals(r.c, expectedC);

    String expectedEqC1 = "115";
    String expectedNeqC1 = "";
    String expectedNeqC2 = "118";
    Assert.assertEquals(r.c, expectedEqC1);
    Assert.assertNotSame(r.c, expectedNeqC1);
    Assert.assertNotSame(r.c, expectedNeqC2);

  }

  @Test
  public void testCreateRuleWithPositiveClassSet() {
    C clsSet = new C("115 118");
    C expectedEqCSet = new C("115 118");
    C expectedNEqCSet1 = new C("115");
    C expectedNEqCSet2 = new C("115 119");

    R clsSetR = new R(clsSet, null, null, null);
    Assert.assertEquals(clsSetR.c, expectedEqCSet);
    Assert.assertNotSame(clsSetR.c, expectedNEqCSet1);
    Assert.assertNotSame(clsSetR.c, expectedNEqCSet2);

    String expectedCSetString = "115 118";
    Assert.assertEquals(clsSetR.c, expectedCSetString);
  }

  @Test
  public void testCreateRuleWithNegativeClassItem() {

  }

}

package th.ku.ac.mcpe.thesis;

import junit.framework.Assert;

import org.testng.annotations.Test;

import th.ku.ac.mcpe.thesis.model.C;
import th.ku.ac.mcpe.thesis.model.F;
import th.ku.ac.mcpe.thesis.model.R;
import th.ku.ac.mcpe.thesis.model.S;

public class RTest {

  @Test
  public void testCreatePositiveClassNoFeatureRule() {
    C c = new C("115");
    S s = new S(1234);
    R r = new R(c, null, s, null);
    R expR = new R("115", null, 1234, null);

    Assert.assertEquals(r, expR);
  }

  @Test
  public void testCreatePositiveFeatureNoClassRule() {
    F f = new F("115");
    S s = new S(1234);
    R r = new R(null, f, s, null);
    R expR = new R(null, "115", 1234, null);
    Assert.assertEquals(r, expR);
  }
}

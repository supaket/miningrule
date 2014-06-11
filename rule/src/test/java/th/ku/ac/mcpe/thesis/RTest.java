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

    Assert.assertEquals(expR, r);

    r = new R();
    Assert.assertNotSame(expR, r);

  }

  @Test
  public void testCreatePositiveFeatureNoClassRule() {
    F f = new F("115");
    S s = new S(1234);
    R r = new R(null, f, s, null);
    R expR = new R(null, "115", 1234, null);
    Assert.assertEquals(expR, r);

    r = new R();
    Assert.assertNotSame(expR, r);
  }

  @Test
  public void testCreatePostiveClassAndFeatureRule() {
    C c = new C("115");
    F f = new F("215");
    S s = new S(1234);
    R r = new R(c, f, s, null);
    R expr = new R("115", "215", s, null);

    Assert.assertEquals(expr, r);

    r = new R(c, f, null, null);
    Assert.assertNotSame(expr, r);
  }

  @Test
  public void testCreate2ClassWith0Feature() {
    C c = new C("115");
    S s = new S(1234);
    R r = new R(c, null, s, null);
    c.val.add("118");
    R expr = new R("115 118", null, s, null);
    Assert.assertEquals(expr, r);
  }

  @Test
  public void testCreate2ClassWith1Feature() {
    C c = new C("115");
    F f = new F("215");
    S s = new S(1234);
    R r = new R(c, f, s, null);
    c.val.add("118");
    R expr = new R("115 118", "215", s, null);
    Assert.assertEquals(expr, r);
  }

}

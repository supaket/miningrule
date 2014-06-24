package th.ku.ac.mcpe.thesis;

import org.testng.*;
import org.testng.annotations.*;
import th.ku.ac.mcpe.thesis.model.*;

/**
 * Created by tomz on 6/22/2014 AD.
 */
public class RTest {

    @Test
    public void newRule_C1p0ps1n0ns_expectRule() {
        String inputC1P0PS0N0NS = "100 (1)";
        R r = new R(inputC1P0PS0N0NS);
        Assert.assertNotNull(r);
        Assert.assertEquals(r.pClass.size(), 1);
        Assert.assertEquals(r.nClass.size(), 1);

        Assert.assertEquals(r.pClass.get(0).level, 1);
        Assert.assertEquals(r.nClass.get(0).level, 1);
    }

    @Test
    public void newRule_C0p1ps1n0ns_expectRule() {
        String inputC1P0PS0N0NS = "15737 15839 (215)";
        R r = new R(inputC1P0PS0N0NS);
        Assert.assertNotNull(r);

        Assert.assertEquals(r.pClass.size(), 0);
        Assert.assertEquals(r.psClass.size(), 1);
        Assert.assertEquals(r.nsClass.size(), 1);

        Assert.assertEquals(r.psClass.get(0).level, 2);
        Assert.assertEquals(r.nsClass.get(0).level, 1);
    }

    @Test
    public void newRule_F1p0ps1n0ns_expectRule() {
        String inputF1p0ps1n0ns = "2232 (242)";
        R r = new R(inputF1p0ps1n0ns);
        Assert.assertNotNull(r);

        Assert.assertEquals(r.pFeature.size(), 1);
        Assert.assertEquals(r.nFeature.size(), 1);

        Assert.assertEquals(r.pFeature.get(0).level, 1);
        Assert.assertEquals(r.nFeature.get(0).level, 1);

    }

    @Test
    public void newRule_C0p0ps1n1ns_expectRule() {
        String inputC0p0ps1n1ns = "2232 250268 (242)";

        R r = new R(inputC0p0ps1n1ns);
        Assert.assertNotNull(r);

        Assert.assertEquals(r.psFeature.size(), 1);
        Assert.assertEquals(r.nsFeature.size(), 1);

        Assert.assertEquals(r.psFeature.get(0).level, 2);
        Assert.assertEquals(r.nsFeature.get(0).level, 1);

    }
}

package th.ku.ac.mcpe.thesis;

import org.testng.Assert;
import org.testng.annotations.Test;
import th.ku.ac.mcpe.thesis.model.C;
import th.ku.ac.mcpe.thesis.model.R;

/**
 * Created by tomz on 6/27/2014 AD.
 */
public class RTest {

    @Test
    public void createRule_SimplePositiveString_expectPositiveRule() {

        final R expectedRule = new R("1111");

        Assert.assertNotNull(expectedRule);

        Assert.assertEquals(1, expectedRule.values.size());

        Assert.assertEquals(C.class, expectedRule.values.get(0).getClass());
    }
}

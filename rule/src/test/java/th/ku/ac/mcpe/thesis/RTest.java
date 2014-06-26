package th.ku.ac.mcpe.thesis;

import org.testng.Assert;
import org.testng.annotations.Test;
import th.ku.ac.mcpe.thesis.model.R;
import th.ku.ac.mcpe.thesis.model.Type;

/**
 * Created by tomz on 6/27/2014 AD.
 */
public class RTest {

    @Test
    public void createRule_SimplePositiveString_expectPositiveRule() {

        final R expectedRule = new R("1111", Type.p);

        Assert.assertNotNull(expectedRule);

        Assert.assertEquals(1, expectedRule.values.size());
    }
}

package th.ku.ac.mcpe.thesis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	@Test
	public void testMatching() {
		String search = "115	148046	15634	15740	15886	19506	19507	2164";
		Matcher matcher = Pattern.compile("\\b115\\b").matcher(search);
		Assert.assertEquals(matcher.find(), true);
	}
	
	@Test
	public void testTrimTab() {
		String tabString = "\tTomz\t";
		Assert.assertTrue("Tomz".equals(tabString.trim()));
	}
}

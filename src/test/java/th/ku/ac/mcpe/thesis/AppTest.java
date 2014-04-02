package th.ku.ac.mcpe.thesis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.login.FailedLoginException;

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

	@Test
	public void testGetLineN() {
		String line = "115\t(704)\t010100101";
		Matcher matcher = Pattern.compile("^(\\d+)\\t(\\((\\d+)\\))\t([0|1]+)")
				.matcher(line);
		// Assert.assertTrue(matcher.find());
		// Assert.assertEquals(matcher.group(0), "115");
		// while (matcher.find()) {
		// System.out.println("0:" + matcher.group(0));
		// System.out.println("1:" + matcher.group(1));
		// System.out.println("2:" + matcher.group(2));
		// System.out.println("3:" + matcher.group(3));
		// System.out.println("4:" + matcher.group(4));
		// }
	}
	@Test
	public void testGetLineN2() {
		String line = "115\t(704)\t000000000001000000000000000000000000000";
		Matcher matcher = Pattern.compile("^(\\d+)\\t(\\((\\d+)\\))\t([0|1]+)$")
				.matcher(line);
		Assert.assertTrue(matcher.find());
	}

	@Test
	public void testIteratorString() {
		String transactionMatched = "1111100000";
		for (int i = 0; i < transactionMatched.length(); i++) {
			char c = transactionMatched.charAt(i);
			System.err.println(c);
		}
	}

	@Test
	public void testRevertL1() {
		String expected = "~115\t(168878)\t0000011111";
		String input = "115\t(704)\t1111100000";
		Matcher matcher = Pattern.compile("^(\\d+)\\t(\\((\\d+)\\))\t([0|1]+)$")
				.matcher(input);
		if (matcher.find()) {
			FindNegative findNeg = new FindNegative();
			Assert.assertEquals(findNeg.revertL1(169582, matcher), expected);
		} else {
			Assert.fail();
		}
	}

}

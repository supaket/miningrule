package th.ku.ac.mcpe.thesis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindNegative {

	public void parsePositiveFile(String path) {

		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			int seqCount = 0;

			String nl1FileName = path + ".NL1.txt";
			File nL1File = new File(nl1FileName);
			PrintWriter printwriter = new PrintWriter(nL1File);

			String nsl1FileName = path + ".NSL1.txt";
			File nsl1File = new File(nsl1FileName);
			PrintWriter nsl1Printer = new PrintWriter(nsl1File);

			boolean isFirstLine = true;
			int countDoLine = 0;
			int revertL1Count = 0;
			int revertNSL1Count = 0;
			while (in.ready()) {
				countDoLine++;
				String line = in.readLine();
				if (isFirstLine) {
					isFirstLine = false;
					seqCount = Integer.valueOf(line.replace('(', ' ')
							.replace(')', ' ').trim());
				}
				Matcher matcher = Pattern.compile(
						"^(\\d+) +(\\((\\d+)\\))\t([0|1]+)$").matcher(line);

				if (matcher.find()) {
					revertL1Count++;
					printwriter.println(revertL1(seqCount, matcher));
				} else {
					// NS
					Matcher nslMatcher = Pattern.compile(
							"^((\\d+ +)+)\\((\\d+)\\)\t([0|1]+)$")
							.matcher(line);
					if (nslMatcher.find()) {
						revertNSL1Count++;
						nsl1Printer.println(revertNsl1(seqCount, nslMatcher));
					}

				}
			}
			printwriter.close();
			nsl1Printer.close();
			in.close();
			System.out.println("I process NL1: " + revertL1Count + " lines");
			System.out.println("I process NSL1: " + revertNSL1Count + " lines");
			System.out.println("I process " + countDoLine + " lines");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String revertNsl1(int seqCount, Matcher matcher) {
		String s = "~(" + matcher.group(1).trim() + ")";
		s += " ";
		s += "(" + String.valueOf(seqCount - Integer.valueOf(matcher.group(3)))
				+ ")" + "\t";
		s += revertTrnxSeq(matcher.group(4));
		return s;
	}

	/**
	 * Case N
	 * 
	 * @param seqCount
	 * @param matcher
	 * @return
	 */
	public String revertL1(int seqCount, Matcher matcher) {
		String neg = ("~" + matcher.group(1)) + " " + "("
				+ String.valueOf(seqCount - Integer.valueOf(matcher.group(3)))
				+ ")" + "\t" + revertTrnxSeq(matcher.group(4));
		return neg;
	}

	private String revertTrnxSeq(String transactionMatched) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < transactionMatched.length(); i++) {
			if (transactionMatched.charAt(i) == '0') {
				buff.append('1');
			} else {
				buff.append('0');
			}
		}
		return buff.toString();
	}

	public static void main(String[] args) {

		FindNegative findNeg = new FindNegative();

		String path = "/Users/tomz/Thesis/Test/C/seqCno_sup001_1234.txt.out";
		findNeg.parsePositiveFile(path);
	}
}

package th.ku.ac.mcpe.thesis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 * 
 */
public class App {

	private static Map<String, PrintWriter> printWriterMap = new HashMap<String, PrintWriter>();

	public static void main(String[] args) {

		try {

			String cfile = args[0];
			String bfile = args[1];

			PrintWriter printer = getPrintWriter(cfile + ".out");

			BufferedReader in = new BufferedReader(new FileReader(cfile));

			while (in.ready()) {

				String fline = in.readLine();
				String[] freq = fline.split("\\s+");

				if (freq.length > 1) {
					printer.print(fline + "\t");
					BufferedReader trxN = new BufferedReader(new FileReader(
							bfile));
					while (trxN.ready()) {
						String trxnLine = trxN.readLine();

						boolean isFound = true;
						for (int i = 0; i < freq.length - 1; i++) {
							isFound &= isFreqFound(trxnLine, freq[i]);
							if (!isFound) {
								break;
							}
						}

						if (isFound) {
							printer.print("1");
						} else {
							printer.print("0");
						}
					}

					printer.println();
					trxN.close();
				} else {
					printer.println(fline);
				}
			}

			in.close();
			FindNegative findNeg = new FindNegative();
			findNeg.parsePositiveFile(cfile + ".out");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static PrintWriter getPrintWriter(String fileName) {
		PrintWriter printwriter = printWriterMap.get(fileName);
		if (printwriter == null) {
			File file = new File(fileName);
			try {
				printwriter = new PrintWriter(file);
				printWriterMap.put(fileName, printwriter);
			} catch (FileNotFoundException e) {
			}
		}
		return printwriter;
	}

	private static boolean isFreqFound(String search, String record) {
		Matcher matcher = Pattern.compile("\\b(\\t)*" + record + "\\b(\\t)*")
				.matcher(search);
		return matcher.find();
	}
}

package th.ku.ac.mcpe.thesis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

			String cpath = "/Users/tomz/Thesis/Test/C";

			List<String> clistFile = getCListFiles(cpath);

			String bpath = "/Users/tomz/Thesis/Test/B";
			List<String> blistFile = getCListFiles(bpath);

			for (String cfile : clistFile) {

				if (cfile.equalsIgnoreCase(cpath+"/seqCno_sup001_1234.txt")) {
					
					PrintWriter printer = getPrintWriter(cfile + ".out");
					
					for (String bfile : blistFile) {
						if (bfile.contains("seqCno1234.txt")) {

							BufferedReader in = new BufferedReader(
									new FileReader(cfile));

							while (in.ready()) {

								String fline = in.readLine();
								String[] freq = fline.split("\\s+");

								if (freq.length > 1) {
									printer.print(fline + "\t");
									BufferedReader trxN = new BufferedReader(
											new FileReader(bfile));
									while (trxN.ready()) {
										String trxnLine = trxN.readLine();

										boolean isFound = true;
										for (int i = 0; i < freq.length - 1; i++) {
											isFound &= isFreqFound(trxnLine,
													freq[i]);
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
								}
							}

							in.close();
						}
					}
				}
			}
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

	/**
	 * 
	 * List file in path with extension .txt in case sensitive
	 * 
	 * @param path
	 */
	private static List<String> getCListFiles(String path) {

		List<String> listFiles = new ArrayList<String>();
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String files = listOfFiles[i].getName();
				if (files.endsWith(".txt") || files.endsWith(".TXT")) {
					listFiles.add(path.concat(File.separator).concat(files));
				}
			}
		}

		return listFiles;
	}

	/**
	 * @param writer
	 * @param cType
	 * @param bType
	 */
	private static void convertToBinary(PrintWriter writer, List<String> cdata,
			BType btype) {
		lookUpFile(writer, cdata, btype);
	}

	/**
	 * @param writer
	 * @param dataSeq
	 * @param btype
	 * @param cPath
	 */
	private static void lookUpFile(PrintWriter writer, List<String> cdata,
			BType btype) {
		printList(writer, cdata);

		for (List<String> dataSeq : btype.getDataSeq()) {

			int found = 0;
			for (String data : cdata) {
				if (dataSeq.contains(data)) {
					found++;
				}
			}

			if (cdata.size() == found) {
				writer.print("1");
			} else {
				writer.print("0");
			}
		}
		writer.println();
	}

	/**
	 * @param writer
	 * @param dataSeq
	 */
	private static void printList(PrintWriter writer, List<String> dataSeq) {

		String space = " ";
		int count = 0;

		for (String data : dataSeq) {
			writer.print(data);
			if (++count < dataSeq.size()) {
				writer.print(space);
			}
		}

		writer.print(space);

		writer.print(space);

	}

	public static List<List<String>> readSeqBType(String path)
			throws IOException {
		List<List<String>> dataList = new ArrayList<List<String>>();
		BufferedReader in = null;

		try {
			in = new BufferedReader(new FileReader(path));
			while (in.ready()) {

				String s = in.readLine();
				String[] rowRecord = s.split("\\s+");
				List<String> list = new ArrayList<String>();

				for (String row : rowRecord) {
					if (!(row.contains("(") || row.contains(")"))) {
						list.add(row);
					} else {
						// support value
					}
				}
				dataList.add(list);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return dataList;
	}
}

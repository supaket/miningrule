package th.ku.ac.mcpe.thesis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 * 
 */
public class App {

	public static void main(String[] args) {

		try {

			String cpath = "/Users/tomz/Thesis/Test/C";

			List<String> clistFile = getCListFiles(cpath);

			String bpath = "/Users/tomz/Thesis/Test/B";
			List<String> blistFile = getCListFiles(bpath);

			for (String cfile : clistFile) {
				if (cfile.contains("seqCno_sup001_1234.txt")) {

					BufferedReader in = new BufferedReader(
							new FileReader(cfile));

					for (String bfile : blistFile) {
						if (bfile.contains("seqCno1234.txt")) {

							BufferedReader trxN = new BufferedReader(
									new FileReader(bfile));
							int foundCount =0;
							while (trxN.ready()) {
								String trxnLine = trxN.readLine();

								// Do Bot seq 1234
								while (in.ready()) {
									String s = in.readLine();
									String[] rowRecord = s.split("\\s+");

									// / check that this row is 0|1
									boolean isFound = true;
									for (int i = 0; i < rowRecord.length - 1; i++) {
										isFound &= trxnLine.matches("\\b"
												+ rowRecord[i] + "\\b");
										if (!isFound) {
											break;
										}
									}
									if (rowRecord.length > 1) {
										if (isFound) {
											System.out.print("1");
											foundCount++;
										} else {
											System.out.print("0");
										}
									}
								}
							}
							System.out.println("\n");
							System.out.println("Found:" + foundCount);
						}
					}
					in.close();
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

	private static String readLine(String cfile) {

		return null;
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

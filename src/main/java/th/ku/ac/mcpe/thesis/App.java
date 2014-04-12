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

import org.perf4j.LoggingStopWatch;
import org.perf4j.StopWatch;

public class App {

  private static Map<String, PrintWriter> printWriterMap = new HashMap<String, PrintWriter>();

  public static void main(String[] args) {
    try {

      String cfile = args[0];
      String bfile = args[1];

      PrintWriter printer = getPrintWriter(cfile + ".out");

      BufferedReader in = new BufferedReader(new FileReader(cfile));

      ArrayList<PositiveA> txnLines = new ArrayList<PositiveA>();
      boolean isFirstLine = true;
      String headbf = "";

      while (in.ready()) {
        if (isFirstLine) {
          headbf = in.readLine();
          isFirstLine &= false;
          continue;
        }

        PositiveA positveA = new PositiveA();
        positveA.head = in.readLine();
        positveA.freqs = positveA.head.split("\\s+");
        //positveA.p = Pattern.compile("\\b(\\t)*" + record + "\\b(\\t)*");
        txnLines.add(positveA);
      }

      in.close();
      printer.print(headbf);
      List<String> trxnLines = new ArrayList<String>();
      BufferedReader trxN = new BufferedReader(new FileReader(bfile));
      while (trxN.ready()) {
        trxnLines.add(trxN.readLine());
      }
      trxN.close();

      StopWatch stop1 = new LoggingStopWatch("16xxxx lines");
      for (PositiveA posA : txnLines) {
        StringBuilder sb = new StringBuilder();
        for (String line : trxnLines) {
          if (isFreqItemFoundInTrxn(posA.freqs, line)) {
            sb.append("1");
          } else {
            sb.append("0");
          }
        }
        //BigInteger lineBit = new BigInteger(sb.toString(), 2);
        //printer.println(lineBit.toString(Character.MAX_RADIX));
      }
      stop1.stop();
      //FindNegative findNeg = new FindNegative();
      //String positiveFileName = cfile + ".out";

      //Long run
      //findNeg.parsePositiveFile(positiveFileName);
      printer.close();
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static boolean isFreqItemFoundInTrxn(String[] freqs, String trxnLine) {
    for (int i = 0; i < freqs.length; i++) {
      if (!isFreqFound(trxnLine, freqs[i])) {
        return false;
      }
    }
    return true;
  }

  private static PrintWriter getPrintWriter(String fileName) {
    PrintWriter printwriter = printWriterMap.get(fileName);
    if (printwriter == null) {
      File file = new File(fileName);
      try {
        printwriter = new PrintWriter(file);
        printWriterMap.put(fileName, printwriter);
      } catch (FileNotFoundException e) {}
    }
    return printwriter;
  }

  private static boolean isFreqFound(String search, String record) {
    if (null != search && null != record) {
      if (search.indexOf(record) > -1) {
        return true;
      }
    }
    return false;
  }
}

package th.ku.ac.mcpe.thesis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.perf4j.LoggingStopWatch;
import org.perf4j.StopWatch;

import th.ku.ac.mcpe.thesis.model.PositiveFreq;

public class App {

  private static Map<String, PrintWriter> printWriterMap = new HashMap<String, PrintWriter>();

  public static void main(String[] args) {
    try {

      String cfile = args[0];
      String bfile = args[1];

      PrintWriter printer = getPrintWriter(cfile + ".out");

      BufferedReader in = new BufferedReader(new FileReader(cfile));

      ArrayList<PositiveFreq> positiveLines = new ArrayList<PositiveFreq>();
      boolean isFirstLine = true;
      String headbf = "";

      while (in.ready()) {

        if (isFirstLine) {
          headbf = in.readLine();
          headbf = headbf.replace('(', ' ').replace(')', ' ').trim();
          isFirstLine &= false;
          continue;
        }

        PositiveFreq positveA = new PositiveFreq();
        positveA.line = in.readLine();
        positveA.freqs = positveA.line.split("\\s+");
        positveA.patterns = getPatternList(positveA.freqs);
        positiveLines.add(positveA);
      }
      in.close();
      List<String> trxnLines = new ArrayList<String>();
      BufferedReader trxN = new BufferedReader(new FileReader(bfile));

      while (trxN.ready()) {
        trxnLines.add(trxN.readLine());
      }
      trxN.close();

      StopWatch stop1 = new LoggingStopWatch("16xxxx lines");
      stop1.start();

      for (PositiveFreq posA : positiveLines) {

        StringBuffer sb = new StringBuffer();

        for (String line : trxnLines) {
          if (isFreqItemFoundInTrxn(posA.patterns, line)) {
            sb.append("1");
          } else {
            sb.append("0");
          }
        }

        posA.bit = new BigInteger(sb.toString(), 2);
      }

      stop1.stop();

      stop1.start();
      GenerateL1 findNeg = new GenerateL1();
      findNeg.parsePositiveFile(Long.valueOf(headbf), positiveLines);
      stop1.stop("process L1");
      stop1.start();
      GenerateL2 generateL2 = new GenerateL2();
      generateL2.genL2(positiveLines);
      stop1.stop("process L2");
      printer.close();

      long totalMemory = Runtime.getRuntime().totalMemory() / 1048576;
      long freeMemory = Runtime.getRuntime().freeMemory() / 1048576;
      System.out.println("memory used:" + totalMemory + " mb");
      System.out.println("freemem :" + freeMemory + " mb");

    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static List<Pattern> getPatternList(String[] freqs) {
    List<Pattern> patterns = new ArrayList<Pattern>();
    for (int i = 0; i < freqs.length - 1; i++) {
      patterns.add(Pattern.compile("\\b(\\t)*" + freqs[i] + "\\b(\\t)*"));
    }
    return patterns;
  }

  public static boolean isFreqItemFoundInTrxn(List<Pattern> patterns, String trxnLine) {
    for (Pattern p : patterns) {
      if (!p.matcher(trxnLine).find()) {
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
}

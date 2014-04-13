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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.perf4j.LoggingStopWatch;
import org.perf4j.StopWatch;

public class GenerateL2_1 {

  private Pattern pattern = Pattern.compile("^~(\\()*((\\d+ )*(\\d+)( *\\d+)*)\\)* \\((\\d+)\\)\\t([0|1]+)$");
  private Pattern positivePattern = Pattern.compile("^(\\d+) \\((\\d+)\\)\\t([0|1]+)$");

  public String generatedL2(String line1, String line2) {
    if (!!isFound(line1, line2)) {
      return join(line1, line2);
    }
    return null;
  }

  public boolean isFound(String lineP, String nl1Case) {
    PositiveSet positive = getPositiveBit(lineP);
    //    if (positive != null) {
    //      String inputP = positive.getFreq();
    //      String testPatter = "^~(\\()*(\\d+ )*" + inputP + "( \\d+)*(\\))* \\((\\d+)\\)\\t([0|1]+)";
    //      Matcher matcher1 = Pattern.compile(testPatter).matcher(nl1Case);
    //      if (matcher1.find()) {
    //        return true;
    //      }
    //    }
    return false;
  }

  private PositiveSet getPositiveBit(String lineP) {
    Matcher matcher = positivePattern.matcher(lineP);
    if (matcher.find()) {
      PositiveSet positive = new PositiveSet();
      positive.setFreq(matcher.group(1));
      positive.setPostiveBit(matcher.group(3));
      return positive;
    }
    return null;
  }

  public String join(String lineInputP, String nl1Case1) {

    PositiveSet positive = getPositiveBit(lineInputP);

    if (positive != null) {

      Matcher nslMatcher = pattern.matcher(nl1Case1);

      String s = "~";

      if (nslMatcher.find()) {

        if ("(".equals(nslMatcher.group(1))) {
          s += "(";
        }
        s += nslMatcher.group(2);

        if ("(".equals(nslMatcher.group(1))) {
          s += ")";
        }

        s = positive.getFreq() + " " + s;

        s += " ";

        //        StopWatch stopWatch = new LoggingStopWatch();
        //        BigInteger bigInt = new BigInteger(positive.getPostiveBit(), 2);
        //        BigInteger joinResult = bigInt.and(new BigInteger(nslMatcher.group(7), 2));
        //
        //        s += "(" + joinResult.bitCount() + ")";
        //        s += "\t";
        //        s += joinResult.toString(2);
        //        
        //        stopWatch.stop("AND BTI OPER");

        /**
         StopWatch stopWatch = new LoggingStopWatch();
         
        PositiveSet positiveResult = anbit(positive.getPostiveBit(), nslMatcher.group(7));
        stopWatch.stop("positiveResult");
        s += "(" + positiveResult.getSupport() + ")";
        s += "\t";
        s += positiveResult.getPostiveBit();
        */
        return s;
      }
    }
    return null;
  }

  public PositiveSet anbit(String bit1, String bit2) {
    PositiveSet pos = new PositiveSet();
    String andBit = "";
    int supportCount = 0;
    for (int chIdx = 0; chIdx < bit1.length(); chIdx++) {
      if (bit1.charAt(chIdx) != bit2.charAt(chIdx)) {
        andBit += "0";
      } else {
        if (bit1.charAt(chIdx) == '1') {
          andBit += "1";
          supportCount++;
        } else {
          andBit += "0";
        }
      }
    }
    pos.setPostiveBit(andBit);
    pos.setSupport(supportCount);
    return pos;
  }

  public static void main(final String[] args) {
    try {
      String positiveFileName = args[0];

      String nl2FileName = positiveFileName + ".NL2.txt";
      File nL2File = new File(nl2FileName);
      PrintWriter nl2Printwriter = new PrintWriter(nL2File);

      GenerateL2_1 genL2 = new GenerateL2_1();

      BufferedReader posBr = new BufferedReader(new FileReader(positiveFileName));
      BufferedReader genL2BrNL1 = new BufferedReader(new FileReader(positiveFileName + ".NL1.txt"));
      List<String> l2BrNl1Lines = new ArrayList<String>();
      while (genL2BrNL1.ready()) {
        l2BrNl1Lines.add(genL2BrNL1.readLine());
      }
      genL2BrNL1.close();
      boolean isFirstLine = true;
      int count = 0;
      StopWatch stopWatch = new LoggingStopWatch();

      while (posBr.ready()) {
        stopWatch.start();

        String posline = posBr.readLine();
        if (isFirstLine) {
          isFirstLine = false;
          continue;
        }

        for (String genLine : l2BrNl1Lines) {
          String out = genL2.generatedL2(posline, genLine);
          //          if (out != null) {
          //            nl2Printwriter.println(out);
          //          }
        }
        stopWatch.stop("generate L2 done seq:" + count++);
      }
      posBr.close();
      nl2Printwriter.close();
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}

package th.ku.ac.mcpe.thesis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateL2 {

  public boolean isFound(String lineP, String nl1Case) {
    PositiveSet positive = getPositiveBit(lineP);
    if (positive != null) {
      String inputP = positive.getFreq();
      String testPatter = "^~(\\()*(\\d+ )*" + inputP + "( \\d+)*(\\))* \\((\\d+)\\)\\t([0|1]+)";
      Matcher matcher1 = Pattern.compile(testPatter).matcher(nl1Case);
      if (matcher1.find()) {
        return true;
      }
    }
    return false;
  }

  private PositiveSet getPositiveBit(String lineP) {

    String pattern = "^(\\d+) \\((\\d+)\\)\\t([0|1]+)$";
    Matcher matcher = Pattern.compile(pattern).matcher(lineP);
    PositiveSet positive = null;

    if (matcher.find()) {
      positive = new PositiveSet();
      positive.setFreq(matcher.group(1));
      positive.setPostiveBit(matcher.group(3));

    }

    return positive;
  }

  public String join(String lineInputP, String nl1Case1) {

    PositiveSet positive = getPositiveBit(lineInputP);

    if (positive != null) {

      Matcher nslMatcher = Pattern.compile("^~(\\()*((\\d+ )*(\\d+)( *\\d+)*)\\)* \\((\\d+)\\)\\t([0|1]+)$").matcher(nl1Case1);

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
        PositiveSet positiveResult = anbit(positive.getPostiveBit(), nslMatcher.group(7));
        s += "(" + positiveResult.getSupport() + ")";
        s += "\t";
        s += positiveResult.getPostiveBit();
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
      positiveFileName = "seqCno_sup001_1234.txt.out";


      String nl2FileName = positiveFileName + ".NL2.txt";
      File nL2File = new File(nl2FileName);
      PrintWriter nl2Printwriter = new PrintWriter(nL2File);

      GenerateL2 genL2 = new GenerateL2();

      BufferedReader posBr = new BufferedReader(new FileReader(positiveFileName));
      
      boolean isFirstLine = true;
      while (posBr.ready()) {
        String posline = posBr.readLine();
        if (isFirstLine) {
          isFirstLine = false;
        }

        BufferedReader genL2BrNL1 = new BufferedReader(new FileReader(positiveFileName + ".NL1.txt"));
        boolean genFirstLine = true;
        while (genL2BrNL1.ready()) {
          String genLine = posBr.readLine();
          if (genFirstLine) {
            genFirstLine = false;
          }
          nl2Printwriter.println(genL2.join(posline, genLine));
        }
        genL2BrNL1.close();
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

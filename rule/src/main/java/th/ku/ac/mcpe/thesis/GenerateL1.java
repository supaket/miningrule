package th.ku.ac.mcpe.thesis;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import th.ku.ac.mcpe.thesis.model.DataFileParser;
import th.ku.ac.mcpe.thesis.model.Negative;
import th.ku.ac.mcpe.thesis.model.Negative.NEG_TYPE;
import th.ku.ac.mcpe.thesis.model.PositiveWrapper;

public class GenerateL1 {

  public Pattern nPattern = Pattern.compile("^(\\d+) +(\\((\\d+)\\))$");
  public Pattern nsPattern = Pattern.compile("^((\\d+ ){2,})\\((\\d+)\\)$");

  public void genL1(DataFileParser dataFile) {
    for (PositiveWrapper posLine : dataFile.getPositiveLines()) {
      if (posLine.getLenght() == 1) {
        posLine.setNegative(getNegItem(dataFile, posLine));
      } else {
        if (isNotMixed(posLine.getFreqs())) {
          posLine.setNegative(getNegSet(dataFile, posLine));
        }
      }
    }
  }

  private Negative getNegSet(DataFileParser dataFile, PositiveWrapper positiveLine) {
    Matcher nslMatcher = getMatcher(nsPattern, positiveLine.getLine());
    if (nslMatcher != null) {
      String nline = "~(" + nslMatcher.group(1).trim() + ")" + " (" + getSuppCount(dataFile, nslMatcher) + ")";
      boolean isClassSet = nslMatcher.group(1).trim().charAt(0) == '1';
      return getNegativeL(1, NEG_TYPE.ns, positiveLine.getBit().xor(dataFile.getBitLenght()), nline, isClassSet);
    }
    throw new RuntimeException("data exception");
  }

  private Negative getNegItem(DataFileParser df, PositiveWrapper pos) {
    Matcher matcher = getMatcher(nPattern, pos.getLine());
    String line = ("~" + matcher.group(1)) + " " + "(" + getSuppCount(df, matcher) + ")";
    boolean isClass = (matcher.group(1).charAt(0) == '1');
    return getNegativeL(1, NEG_TYPE.n, pos.getBit().xor(df.getBitLenght()), line, isClass);
  }

  private String getSuppCount(DataFileParser df, Matcher matcher) {
    return String.valueOf(df.getTrxnCount() - Integer.valueOf(matcher.group(3)));
  }

  private Negative getNegativeL(int level, NEG_TYPE type, BigInteger bit, String line, boolean isClass) {
    return new Negative(type, level, line, bit, isClass);
  }

  public Matcher getMatcher(Pattern p, String line) {
    Matcher matcher = p.matcher(line);
    if (matcher.find()) {
      return matcher;
    }
    return null;
  }

  public boolean isNotMixed(String[] freqs) {
    boolean isFirstLine = true;
    char c = ' ';
    for (int i = 0; i < freqs.length - 1; i++) {
      if (isFirstLine) {
        isFirstLine = false;
        c = freqs[i].charAt(0);
        continue;
      }
      if (c != freqs[i].charAt(0)) {
        return false;
      }
    }
    return true;
  }
}

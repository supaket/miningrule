package th.ku.ac.mcpe.thesis;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import th.ku.ac.mcpe.thesis.model.DataFileParser;
import th.ku.ac.mcpe.thesis.model.NegativeL;
import th.ku.ac.mcpe.thesis.model.NegativeL.NEG_TYPE;
import th.ku.ac.mcpe.thesis.model.PosFreq;

public class GenerateL1 {

  public Pattern nPattern = Pattern.compile("^(\\d+) +(\\((\\d+)\\))$");
  public Pattern nsPattern = Pattern.compile("^((\\d+ ){2,})\\((\\d+)\\)$");

  public void genL1(DataFileParser dataFile) {
    for (PosFreq positiveLine : dataFile.getPositiveLines()) {
      if (positiveLine.isSingleItem()) {
        positiveLine.setNegative(getNegativeSingleItem(dataFile, positiveLine));
      } else {
        if (isNotMixed(positiveLine.getFreqs())) {
          positiveLine.setNegative(getNegativeSet(dataFile, positiveLine));
        }
      }
    }
  }

  private NegativeL getNegativeSet(DataFileParser dataFile, PosFreq positiveLine) {
    Matcher nslMatcher = isFound(nsPattern, positiveLine.getLine());
    String nline = "~(" + nslMatcher.group(1).trim() + ")" + " (" + String.valueOf(dataFile.getTrxnCount() - Integer.valueOf(nslMatcher.group(3))) + ")";
    boolean isClassSet = nslMatcher.group(1).trim().charAt(0) == '1';
    return getNegativeL(1, NEG_TYPE.ns, positiveLine.getBit().xor(dataFile.getBitLenght()), nline, isClassSet);
  }

  private NegativeL getNegativeSingleItem(DataFileParser dataFile, PosFreq positiveLine) {
    Matcher matcher = isFound(nPattern, positiveLine.getLine());
    String line = ("~" + matcher.group(1)) + " " + "(" + String.valueOf(dataFile.getTrxnCount() - Integer.valueOf(matcher.group(3))) + ")";
    boolean isClass = (matcher.group(1).charAt(0) == '1');
    return getNegativeL(1, NEG_TYPE.n, positiveLine.getBit().xor(dataFile.getBitLenght()), line, isClass);
  }

  private NegativeL getNegativeL(int level, NEG_TYPE type, BigInteger bit, String line, boolean isClass) {
    NegativeL nl = new NegativeL();
    nl.level = level;
    nl.type = type;
    nl.line = line;
    nl.isClass = isClass;
    nl.bit = bit;
    return nl;
  }

  public Matcher isFound(Pattern p, String line) {
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

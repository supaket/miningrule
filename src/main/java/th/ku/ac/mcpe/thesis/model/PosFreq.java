package th.ku.ac.mcpe.thesis.model;

import java.math.BigInteger;
import java.util.List;
import java.util.regex.Pattern;

public class PosFreq {
  private String line;
  private String[] freqs;
  private List<Pattern> patterns;
  private BigInteger bit;
  private NegativeL negative;
  private boolean singleItem;

  public String getLine() {
    return line;
  }

  public void setLine(String line) {
    this.line = line;
  }

  public String[] getFreqs() {
    return freqs;
  }

  public void setFreqs(String[] freqs) {
    this.freqs = freqs;
  }

  public List<Pattern> getPatterns() {
    return patterns;
  }

  public void setPatterns(List<Pattern> patterns) {
    this.patterns = patterns;
  }

  public BigInteger getBit() {
    return bit;
  }

  public void setBit(BigInteger bit) {
    this.bit = bit;
  }

  public NegativeL getNegative() {
    return negative;
  }

  public void setNegative(NegativeL negative) {
    this.negative = negative;
  }

  public boolean isSingleItem() {
    return singleItem;
  }

  public void setSingleItem(boolean singleItem) {
    this.singleItem = singleItem;
  }
}

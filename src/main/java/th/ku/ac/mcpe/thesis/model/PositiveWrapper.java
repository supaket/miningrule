package th.ku.ac.mcpe.thesis.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PositiveWrapper {

  public String line;
  public String[] freqs;
  public List<Pattern> patterns;
  public BigInteger bit;
  public Negative negative;
  public int lenght;
  public boolean hasClass;

  public PositiveWrapper(String line) {
    super();
    this.line = line;
    this.freqs = line.split("\\s+");
    this.hasClass = isFoundClass(freqs);
    this.patterns = getPatternList(freqs);
    this.setLenght(getFreqs().length - 1);
  }

  private boolean isFoundClass(String[] freqs) {
    for (String freq : freqs) {
      if (freq.startsWith("1")) {
        return true;
      }
    }
    return false;
  }

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

  public Negative getNegative() {
    return negative;
  }

  public void setNegative(Negative negative) {
    this.negative = negative;
  }

  private List<Pattern> getPatternList(String[] freqs) {
    List<Pattern> patterns = new ArrayList<Pattern>();
    for (int i = 0; i < freqs.length - 1; i++) {
      patterns.add(Pattern.compile("\\b(\\t)*" + freqs[i] + "\\b(\\t)*"));
    }
    return patterns;
  }

  public int getLenght() {
    return lenght;
  }

  public void setLenght(int lenght) {
    this.lenght = lenght;
  }

  public boolean isHasClass() {
    return hasClass;
  }

  public void setHasClass(boolean hasClass) {
    this.hasClass = hasClass;
  }
}

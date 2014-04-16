package th.ku.ac.mcpe.thesis.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DataFileParser {

  private Long trxnCount;
  private BigInteger bitLenght;
  private List<PosFreq> positiveLines;
  private List<String> trxnLines;

  public DataFileParser() {
    positiveLines = new ArrayList<PosFreq>();
  }

  public Long getTrxnCount() {
    return trxnCount;
  }

  public void setTrxnCount(Long trxnCount) {
    this.trxnCount = trxnCount;
  }

  public List<PosFreq> getPositiveLines() {
    return positiveLines;
  }

  public void setPositiveLines(List<PosFreq> positiveLines) {
    this.positiveLines = positiveLines;
  }

  public void parseFreqFile(String freqFile) throws IOException {
    BufferedReader in = new BufferedReader(new FileReader(freqFile));
    boolean isFirstLine = true;
    while (in.ready()) {
      String line = in.readLine();
      if (isFirstLine) {
        isFirstLine &= false;
        setTrxnCount(Long.valueOf(line.replace('(', ' ').replace(')', ' ').trim()));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < getTrxnCount(); i++) {
          sb.append('1');
        }
        setBitLenght(new BigInteger(sb.toString(), 2));
        continue;
      }
      positiveLines.add(getPosFreq(line));
    }
    in.close();
  }

  public void parseTrxnLines(String trxnFile) throws IOException {
    List<String> trxnLines = new ArrayList<String>();
    BufferedReader trxN = new BufferedReader(new FileReader(trxnFile));

    while (trxN.ready()) {
      trxnLines.add(trxN.readLine());
    }
    trxN.close();
    this.trxnLines = trxnLines;
  }

  private PosFreq getPosFreq(String line) {
    PosFreq pos = new PosFreq();
    pos.setLine(line);
    pos.setFreqs(pos.getLine().split("\\s+"));
    pos.setSingleItem(pos.getFreqs().length == 2);
    pos.setPatterns(getPatternList(pos.getFreqs()));
    return pos;
  }

  private List<Pattern> getPatternList(String[] freqs) {
    List<Pattern> patterns = new ArrayList<Pattern>();
    for (int i = 0; i < freqs.length - 1; i++) {
      patterns.add(Pattern.compile("\\b(\\t)*" + freqs[i] + "\\b(\\t)*"));
    }
    return patterns;
  }

  public List<String> getTrxnLines() {
    return trxnLines;
  }

  public void setTrxnLines(List<String> trxnLines) {
    this.trxnLines = trxnLines;
  }

  public void processNegativeL0() {
    for (PosFreq posA : positiveLines) {
      posA.setBit(new BigInteger(getBitLine(posA.getPatterns()), 2));
    }
  }

  private String getBitLine(List<Pattern> patterns) {
    StringBuffer sb = new StringBuffer();
    for (String line : trxnLines) {
      sb.append(isFreqItemFoundInTrxn(patterns, line) ? "1" : "0");
    }
    return sb.toString();
  }

  private boolean isFreqItemFoundInTrxn(List<Pattern> patterns, String line) {
    for (Pattern p : patterns) {
      if (!p.matcher(line).find()) {
        return false;
      }
    }
    return true;
  }


  public BigInteger getBitLenght() {
    return bitLenght;
  }

  public void setBitLenght(BigInteger bitLenght) {
    this.bitLenght = bitLenght;
  }

  public void writeBit(String filePath) {
    try {
      File file = new File(filePath);
      PrintWriter pwr = new PrintWriter(file);
      for (PosFreq posA : positiveLines) {
        pwr.println(posA.getBit().toString(2));
      }
      pwr.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}

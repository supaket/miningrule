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
  private List<PositiveWrapper> positiveLines;
  private List<String> trxnLines;

  public DataFileParser() {
    positiveLines = new ArrayList<PositiveWrapper>();
  }

  public Long getTrxnCount() {
    return trxnCount;
  }

  public void setTrxnCount(Long trxnCount) {
    this.trxnCount = trxnCount;
  }

  public List<PositiveWrapper> getPositiveLines() {
    return positiveLines;
  }

  public void setPositiveLines(List<PositiveWrapper> positiveLines) {
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
        createXorBit();
        continue;
      }
      positiveLines.add(getWrapper(line));
    }
    in.close();
  }

  private void createXorBit() {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < getTrxnCount(); i++) {
      sb.append('1');
    }
    setBitLenght(new BigInteger(sb.toString(), 2));
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

  private PositiveWrapper getWrapper(String line) {
    return new PositiveWrapper(line);
  }

  public List<String> getTrxnLines() {
    return trxnLines;
  }

  public void setTrxnLines(List<String> trxnLines) {
    this.trxnLines = trxnLines;
  }

  public void processNegativeL0() {
    for (PositiveWrapper pos : positiveLines) {
      pos.setBit(new BigInteger(getBitLine(pos.getPatterns()), 2));
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
      for (PositiveWrapper posA : positiveLines) {
        pwr.println(posA.getBit().toString(2));
      }
      pwr.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}

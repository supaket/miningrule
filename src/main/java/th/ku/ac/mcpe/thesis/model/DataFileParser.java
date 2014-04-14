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

  private int trxnCount;

  private List<PositiveFreq> positiveLines;
  private List<String> trxnLines;

  public DataFileParser() {
    positiveLines = new ArrayList<PositiveFreq>();
  }

  public int getTrxnCount() {
    return trxnCount;
  }

  public void setTrxnCount(int trxnCount) {
    this.trxnCount = trxnCount;
  }

  public List<PositiveFreq> getPositiveLines() {
    return positiveLines;
  }

  public void setPositiveLines(List<PositiveFreq> positiveLines) {
    this.positiveLines = positiveLines;
  }

  public void parseFreqFile(String freqFile) throws IOException {
    BufferedReader in = new BufferedReader(new FileReader(freqFile));
    boolean isFirstLine = true;
    while (in.ready()) {
      String line = in.readLine();
      if (isFirstLine) {
        isFirstLine &= false;
        setTrxnCount(Integer.valueOf(line.replace('(', ' ').replace(')', ' ').trim()));
        continue;
      }
      positiveLines.add(getPositiveFreq(line));
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

  private PositiveFreq getPositiveFreq(String line) {
    PositiveFreq positveA = new PositiveFreq();
    positveA.line = line;
    positveA.freqs = positveA.line.split("\\s+");
    positveA.patterns = getPatternList(positveA.freqs);
    return positveA;
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
    for (PositiveFreq posA : positiveLines) {
      posA.setBit(new BigInteger(getBitLine(posA.patterns), 2));
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

  public void writeBit(String filePath) {
    try {
      File file = new File(filePath);
      PrintWriter pwr = new PrintWriter(file);
      for (PositiveFreq posA : positiveLines) {
        pwr.println(posA.getBit().toString(2));
      }
      pwr.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}

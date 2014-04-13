package th.ku.ac.mcpe.thesis;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.perf4j.LoggingStopWatch;
import org.perf4j.StopWatch;

import th.ku.ac.mcpe.thesis.model.NegativeL;
import th.ku.ac.mcpe.thesis.model.NegativeL.NEG_TYPE;
import th.ku.ac.mcpe.thesis.model.PositiveFreq;

public class GenerateL1 {

  public Pattern nPattern = Pattern.compile("^(\\d+) +(\\((\\d+)\\))$");
  public Pattern nsPattern = Pattern.compile("^((\\d+ ){2,})\\((\\d+)\\)$");

  public void parsePositiveFile(Long trxCount, ArrayList<PositiveFreq> positiveLines) {
    int countN = 0;
    int countNS = 0;
    StopWatch st = new LoggingStopWatch();
    for (PositiveFreq positiveLine : positiveLines) {
      String line = positiveLine.line;
      Matcher m = isFound(nPattern, line);
      if (null != m) {
        countN++;
        NegativeL negL1 = new NegativeL();
        negL1.level = 1;
        negL1.type = NEG_TYPE.n;
        negL1.line = ("~" + m.group(1)) + " " + "(" + String.valueOf(trxCount - Integer.valueOf(m.group(3))) + ")";
        negL1.is1 = (m.group(1).charAt(0) == '1');
        negL1.bit.add(positiveLine.bit.negate());
        positiveLine.negative = negL1;
      } else {
        if (isOnlyOneTypeInSingleLine(positiveLine.freqs)) {
          countNS++;
          Matcher nslMatcher = isFound(nsPattern, line);
          if (null != nslMatcher) {
            String s = "~(" + nslMatcher.group(1).trim() + ")";
            s += " ";
            s += "(" + String.valueOf(trxCount - Integer.valueOf(nslMatcher.group(3))) + ")";
            NegativeL negsL1 = new NegativeL();
            negsL1.line = s;
            negsL1.level = 1;
            negsL1.type = NEG_TYPE.ns;
            negsL1.bit.add(positiveLine.bit.negate());
            positiveLine.negative = negsL1;
          }
        }
      }
    }
    st.stop("finish n count (" + countN + "), ns count(" + countNS + ")");
  }

  public Matcher isFound(Pattern p, String line) {
    Matcher matcher = p.matcher(line);
    if (matcher.find()) {
      return matcher;
    }
    return null;
  }

  public boolean isOnlyOneTypeInSingleLine(String[] freqs) {
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

package th.ku.ac.mcpe.thesis;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.perf4j.LoggingStopWatch;
import org.perf4j.StopWatch;

import th.ku.ac.mcpe.thesis.model.NegativeL.NEG_TYPE;
import th.ku.ac.mcpe.thesis.model.PositiveFreq;

public class GenerateL2 {
  public void genL2(ArrayList<PositiveFreq> positiveLines) {
    StopWatch st = new LoggingStopWatch();
    int countAll = 1;
    int countJoin = 0;
    for (PositiveFreq positiveLineFreq1 : positiveLines) {
      if (positiveLineFreq1.negative != null && positiveLineFreq1.negative.type == NEG_TYPE.n) {
        for (PositiveFreq negativeLine : positiveLines) {
          st.start();
          if (negativeLine.negative != null) {
            if (positiveLineFreq1.freqs[0].charAt(0) == '1') {
              if (!negativeLine.negative.is1) {
                if (isJoinable(positiveLineFreq1, negativeLine)) {
                  positiveLineFreq1.bit.and(negativeLine.negative.bit.get(0));
                  countJoin++;
                }
              }
            } else {
              if (isJoinable(positiveLineFreq1, negativeLine)) {
                positiveLineFreq1.bit.and(negativeLine.negative.bit.get(0));
                countJoin++;
              }
            }
            st.stop("join count " + countAll++);
          }
        }
      }
    }
    st.stop("join count " + countJoin);
  }

  private boolean isJoinable(PositiveFreq positiveLineFreq1, PositiveFreq negativeLine) {
    Pattern p = Pattern.compile("^~(\\()*(\\d+ )*" + positiveLineFreq1.freqs[0] + "( \\d+)*(\\))* \\((\\d+)\\)");
    Matcher m = p.matcher(negativeLine.negative.line);
    return m.find();
  }
}

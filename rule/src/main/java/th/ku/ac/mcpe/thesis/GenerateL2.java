package th.ku.ac.mcpe.thesis;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import th.ku.ac.mcpe.thesis.model.DataFileParser;
import th.ku.ac.mcpe.thesis.model.Negative;
import th.ku.ac.mcpe.thesis.model.Negative.NEG_TYPE;
import th.ku.ac.mcpe.thesis.model.PositiveWrapper;

public class GenerateL2 {

  public void genL2(DataFileParser df) {
    for (PositiveWrapper posLine : df.getPositiveLines()) {
      if (isNegItem(posLine.getNegative())) {
        findPosNextLevel(df.getPositiveLines(), posLine);
      }
    }
  }

  private void findPosNextLevel(List<PositiveWrapper> positiveLines, PositiveWrapper pos) {
    for (PositiveWrapper negativeLine : positiveLines) {
      if (isJoinable(pos, negativeLine)) {
        pos.getBit().and(negativeLine.getNegative().bit);
      }
    }
  }

  /**
   * Only positive item will return true, positive set will return false
   * @param negative
   * @return true if positive item has been detect
   */
  private boolean isNegItem(final Negative negative) {
    if (negative != null) {
      return negative.type == NEG_TYPE.n;
    }
    return false;
  }

  private boolean isJoinable(PositiveWrapper pos, PositiveWrapper neg) {
    if (neg != null) {
      if (!(pos.hasClass && neg.hasClass)) {
        return isItemFound(pos, neg);
      }
    }
    return false;
  }

  private boolean isItemFound(PositiveWrapper positiveLineFreq1, PositiveWrapper negativeLine) {
    if (negativeLine.getNegative() == null) {
      return false;
    }
    Pattern p = Pattern.compile("^~(\\()*(\\d+ )*" + positiveLineFreq1.getFreqs()[0] + "( \\d+)*(\\))* \\((\\d+)\\)");
    Matcher m = p.matcher(negativeLine.getNegative().line);
    return m.find();
  }
}

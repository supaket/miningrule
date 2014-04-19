package th.ku.ac.mcpe.thesis;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import th.ku.ac.mcpe.thesis.model.DataFileParser;
import th.ku.ac.mcpe.thesis.model.NegativeL;
import th.ku.ac.mcpe.thesis.model.NegativeL.NEG_TYPE;
import th.ku.ac.mcpe.thesis.model.PosFreq;

public class GenerateL2 {

  public void genL2(DataFileParser df) {
    for (PosFreq positiveLineFreq1 : df.getPositiveLines()) {
      if (isPostiveItem(positiveLineFreq1.getNegative())) {
        findPostiveNextLevel(df.getPositiveLines(), positiveLineFreq1);
      }
    }
  }

  private void findPostiveNextLevel(List<PosFreq> positiveLines, PosFreq positiveLineFreq1) {
    for (PosFreq negativeLine : positiveLines) {
      if (isJoinable(positiveLineFreq1, negativeLine)) {
        positiveLineFreq1.getBit().and(negativeLine.getNegative().bit);
      }
    }
  }

  /**
   * Only positive item will return true, positive set will return false
   * @param negative
   * @return true if positive item has been detect
   */
  private boolean isPostiveItem(final NegativeL negative) {
    if (negative != null) {
      return negative.type == NEG_TYPE.n;
    }
    return false;
  }

  private boolean isJoinable(PosFreq positiveLineFreq1, PosFreq negativeLine) {
    if (negativeLine != null) {
      if (!(positiveLineFreq1.getFreqs()[0].charAt(0) == '1' && negativeLine.getNegative() != null && negativeLine.getNegative().isClass)) {
        return isItemFound(positiveLineFreq1, negativeLine);
      }
    }
    return false;
  }

  private boolean isItemFound(PosFreq positiveLineFreq1, PosFreq negativeLine) {
    if (negativeLine.getNegative() == null) {
      return false;
    }
    Pattern p = Pattern.compile("^~(\\()*(\\d+ )*" + positiveLineFreq1.getFreqs()[0] + "( \\d+)*(\\))* \\((\\d+)\\)");
    Matcher m = p.matcher(negativeLine.getNegative().line);
    return m.find();
  }
}

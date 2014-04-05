package th.ku.ac.mcpe.thesis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateL2 {

  public boolean isFound(String lineP, String nl1Case) {
    String pattern = "^(\\d+) \\((\\d+)\\)\\t([0|1]+)$";

    //    String case1 = "^(~\\d+ +)\\((\\d+)\\)\\t([0|1]+)";
    //    String case2 = "^~\\(((\\d+ *)+)\\) \\((\\d+)\\)\\t([0|1]+)";

    Matcher matcher = Pattern.compile(pattern).matcher(lineP);
    if (matcher.find()) {
      String inputP = matcher.group(1);
      String testPatter = "^~(\\()*(\\d+ )*" + inputP + "( \\d+)*(\\))* \\((\\d+)\\)\\t([0|1]+)";
      Matcher matcher1 = Pattern.compile(testPatter).matcher(nl1Case);

      if (matcher1.find()) {
        return true;
      }
    }
    return false;
  }
}

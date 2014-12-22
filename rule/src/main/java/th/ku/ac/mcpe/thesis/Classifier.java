package th.ku.ac.mcpe.thesis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomz on 12/21/14.
 */
public class Classifier {

    //2xxxx 2xxxx -2xxx -2xxx -(2xxxx 2xxxx) 1xxx
    //2xxxx 2xxxx -2xxx -2xxx -(2xxxx 2xxxx) 1xxx 1xxxx

    List<RuleSet> ruleSetList = new ArrayList<RuleSet>();
    String defaultClass;
    Double accr = 0.0d;

    public void replaceWith(final Classifier newClassifier) {

    }
}

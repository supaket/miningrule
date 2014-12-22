package th.ku.ac.mcpe.thesis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomz on 12/21/14.
 */
public class Transaction {
    List<String> left  = new ArrayList<>();
    List<String> right = new ArrayList<>();
    private boolean isMarked4Delete;

    /**
     * all positive feature in rpc must have in transaction feature (1)
     * any negative feature in rpc must not have in transaction feature (2)
     * for any negative feature SET  must not have( together ) in transaction feature (3)
     *
     * @param rpc
     * @return
     */
    public boolean isFeatureMatched(final RPCRule rpc) {
        return false;
    }

    /**
     * check every RNC must not matched
     *
     * @param rncList
     * @return
     */
    public boolean isFeatureMatched(final List<RNCRule> rncList) {
        return false;
    }

    public boolean isMarked4Delete() {
        return isMarked4Delete;
    }

    public void setMarked4Delete(final boolean isMarked4Delete) {
        this.isMarked4Delete = isMarked4Delete;
    }
}

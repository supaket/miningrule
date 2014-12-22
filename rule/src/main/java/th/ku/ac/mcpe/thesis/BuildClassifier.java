package th.ku.ac.mcpe.thesis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomz on 12/21/14.
 */
public class BuildClassifier {

    //** cOld create , assign default class
    //set accr = 0;
    Classifier classifyOld = new Classifier();


    List<RPCRule>     rpcList   = new ArrayList<RPCRule>();
    List<RNCRule>     rncList   = new ArrayList<RNCRule>();
    List<Transaction> transList = new ArrayList<>();

    String defaultClassOfTransactionFile;
    String rcpFileName;
    String transactionFileName;

    public void build() {
        for (RPCRule rpc : rpcList) {

            if (!transList.isEmpty()) {
                boolean isGenRNC = false;
                boolean isMarked = false;
                boolean unMark = false;

                for (Transaction transaction : transList) {
                    if (transaction.isFeatureMatched(rpc) && isGenRNC == false) {
                        rncList = loadRNCRule(rcpFileName, rpc);
                        isGenRNC = true;
                    }

                    if (isGenRNC && transaction.isFeatureMatched(rpc) &&
                            transaction.isFeatureMatched(rncList)) {
                        transaction.setMarked4Delete(true);
                        isMarked = true;
                    } else {
                        unMark = true;
                    }

                }

                if (isMarked) {


                    String defaultClassFromUnMark = "";
                    if (unMark) {
                        defaultClassFromUnMark = findDefaultClassFromUnMark(transList);
                    } else {
                        defaultClassFromUnMark = defaultClassOfTransactionFile;
                    }

                    final Classifier newClassifier = createNewClassifier(defaultClassFromUnMark, classifyOld, rpc, rncList);
                    final Double accrNew = findNewAccr(newClassifier, transactionFileName);
                    if (newClassifier.accr > classifyOld.accr) {
                        deleteMarkedTransaction(transList);
                        classifyOld.replaceWith(newClassifier);
                        writeClassfierToFile(classifyOld);
                    } else {
                        unMarkAll(transList);
                    }

                }
            } else {
                break;
            }
        }
    }

    private void writeClassfierToFile(final Classifier classifyOld) {

    }

    /**
     * Unmark all
     *
     * @param transList
     */
    private void unMarkAll(final List<Transaction> transList) {

    }

    // remove all marked
    private void deleteMarkedTransaction(final List<Transaction> transList) {

    }

    /**
     * Lookup seq ที่ไม่มีชื่อใน transaction file name ที่เราส่งเข้าไป
     *
     * @param newClassifier
     * @param transactionFileName
     * @return
     */
    private Double findNewAccr(final Classifier newClassifier, String transactionFileName) {
        return null;
    }

    private Classifier createNewClassifier(final String defaultClassFromUnMark, Classifier cOldList, final RPCRule rpc, final List<RNCRule> rncList) {
        return null;
    }

    private String findDefaultClassFromUnMark(final List<Transaction> transList) {
        return null;
    }

    /**
     * Load from file
     * 1.ขวาต้องเหมือนกันเป๊ะไม่สนใจเครื่องหมาย
     * 2.Feature ของ RPC ต้องเป็น subset ของ Feature ของ RNC ไม่สนใจเครื่องหมาย
     * 3.Subset ของ RPC ต้องเป็น subset ของ RNC
     *
     * @param rpc
     * @return
     */
    private List<RNCRule> loadRNCRule(final String rcpFileName, final RPCRule rpc) {
        return null;
    }
}

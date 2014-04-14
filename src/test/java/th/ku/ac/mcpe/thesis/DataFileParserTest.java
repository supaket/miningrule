package th.ku.ac.mcpe.thesis;

import junit.framework.Assert;

import org.testng.annotations.Test;

import th.ku.ac.mcpe.thesis.model.DataFileParser;

public class DataFileParserTest {

  @Test
  public void testFreqFileParser() throws Exception {
    DataFileParser dataFileParser = new DataFileParser();
    dataFileParser.parseFreqFile("/Users/tomz/Thesis/dbtest/seqCno_sup001_1234.txt");
    dataFileParser.parseTrxnLines("/Users/tomz/Thesis/dbtest/seqCno1234.txt");
    dataFileParser.processNegativeL0();
    dataFileParser.writeBit("/Users/tomz/Thesis/dbtest/seqCno_sup001_1234.txt.test");

    Assert.assertEquals(10, dataFileParser.getTrxnCount());

    Assert.assertEquals(3, dataFileParser.getPositiveLines().get(0).bit.bitCount());

    AssertHelper.sameContent("/Users/tomz/Thesis/dbtest/seqCno_sup001_1234.txt.test", "/Users/tomz/Thesis/dbtest/seqCno_sup001_1234.txt.expected");

  }
}

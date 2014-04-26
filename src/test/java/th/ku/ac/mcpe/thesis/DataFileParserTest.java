package th.ku.ac.mcpe.thesis;

import java.math.BigInteger;

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

    Assert.assertEquals(Long.valueOf(10), dataFileParser.getTrxnCount());

    Assert.assertEquals(3, dataFileParser.getPositiveLines().get(0).getBit().bitCount());

    AssertHelper.sameContent("/Users/tomz/Thesis/dbtest/seqCno_sup001_1234.txt.test", "/Users/tomz/Thesis/dbtest/seqCno_sup001_1234.txt.expected");

    Assert.assertEquals(getBigInt10Bit(), dataFileParser.getBitLenght());

  }

  
  private BigInteger getBigInt10Bit() {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < 10; i++) {
      sb.append('1');
    }
    BigInteger bigInt = new BigInteger(sb.toString(), 2);
    return bigInt;
  }

}

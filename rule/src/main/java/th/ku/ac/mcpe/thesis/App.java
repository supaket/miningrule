package th.ku.ac.mcpe.thesis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.perf4j.LoggingStopWatch;
import org.perf4j.StopWatch;

import th.ku.ac.mcpe.thesis.model.DataFileParser;

public class App {

  public static void main(String[] args) {
    try {
      StopWatch st = new LoggingStopWatch();
      DataFileParser dataFileParser = new DataFileParser();
      dataFileParser.parseFreqFile(args[0]);
      dataFileParser.parseTrxnLines(args[1]);
      dataFileParser.processNegativeL0();
      st.stop("find postive done.");
      st.start();
      new GenerateL1().genL1(dataFileParser);
      st.stop("genL1 done.");
      st.start();
      new GenerateL2().genL2(dataFileParser);
      st.stop("genL2 done.");
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

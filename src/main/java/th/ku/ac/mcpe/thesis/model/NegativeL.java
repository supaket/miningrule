package th.ku.ac.mcpe.thesis.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class NegativeL {
  public enum NEG_TYPE {
    n, ns
  }

  public NEG_TYPE type;
  public int level = 1;
  public String line;
  public List<BigInteger> bit = new ArrayList<BigInteger>();
  public boolean is1;
}

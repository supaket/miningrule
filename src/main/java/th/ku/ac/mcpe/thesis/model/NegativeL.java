package th.ku.ac.mcpe.thesis.model;

import java.math.BigInteger;

public class NegativeL {
  public enum NEG_TYPE {
    n, ns
  }

  public NEG_TYPE type;
  public int level = 1;
  public String line;
  public BigInteger bit;
  public boolean isClass;
}

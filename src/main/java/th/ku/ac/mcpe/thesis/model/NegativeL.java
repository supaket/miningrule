package th.ku.ac.mcpe.thesis.model;

import java.math.BigInteger;

public class NegativeL {
  public enum NEG_TYPE {
    n, ns
  }

  public NegativeL(NEG_TYPE type, int level, String line, BigInteger bit, boolean isClass) {
    super();
    this.type = type;
    this.level = level;
    this.line = line;
    this.bit = bit;
    this.isClass = isClass;
  }

  public NEG_TYPE type;
  public int level = 1;
  public String line;
  public BigInteger bit;
  public boolean isClass;
}

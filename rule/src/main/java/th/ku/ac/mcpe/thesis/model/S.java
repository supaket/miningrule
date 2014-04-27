package th.ku.ac.mcpe.thesis.model;

public class S {
  public Integer s;

  public S(int s) {
    super();
    this.s = s;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj instanceof S) {
      return s.intValue() == ((S) obj).s.intValue();
    }
    return false;
  }

  @Override
  public String toString() {
    return s.toString();
  }
}

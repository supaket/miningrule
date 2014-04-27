package th.ku.ac.mcpe.thesis.model;

import java.util.ArrayList;
import java.util.Collection;

public class I {

  private Collection<String> i = new ArrayList<String>();

  public I(String i) {
    if (null != i) {
      String[] ir = i.split("\\s+");
      for (String istr : ir) {
        this.i.add(istr);
      }
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (getClass().isInstance(obj)) {
      if (((I) obj).i.size() == i.size()) {
        return ((I) obj).i.containsAll(i);
      }
    }
    if (obj instanceof String) {
      return i.equals(new I((String) obj));
    }
    return false;
  }

  @Override
  public String toString() {
    return i.toString();
  }
}

package th.ku.ac.mcpe.thesis.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class C {

  protected Set<String> clsSet;

  public C(String c) {
    clsSet = new HashSet<String>();
    String[] clsAr = c.split("\\s+");
    for (String cls : clsAr) {
      clsSet.add(cls);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof C) {
      return ((C) obj).clsSet.equals(clsSet);
    }
    if (obj instanceof String) {
      return clsSet.equals(new HashSet<String>(Arrays.asList(((String) obj).split("\\s+"))));
    }
    return false;
  }

  @Override
  public String toString() {
    return clsSet.toString();
  }
}

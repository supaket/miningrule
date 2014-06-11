package th.ku.ac.mcpe.thesis.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class I {


  public List<String> val = new ArrayList<String>();
  public List<Pattern> patterns = new ArrayList<Pattern>();

  public BigInteger bit;

  public Type type;

  public void addVal(List<String> col) {
    val.addAll(col);
    patterns.addAll(getPattern(col));
  }

  public I(List<String> sList) {
    val = sList;
    patterns = getPattern(val);
  }

  public I(String i) {
    if (null != i) {
      String[] ir = i.split("\\s+");
      for (String istr : ir) {
        this.val.add(istr);
      }
      patterns = getPattern(val);
    }
  }

  private List<Pattern> getPattern(Collection<String> items) {
    List<Pattern> patterns = new ArrayList<Pattern>();
    for (String item : items) {
      patterns.add(Pattern.compile("\\b(\\t)*" + item + "\\b(\\t)*"));
    }
    return patterns;
  }

  @Override
  public boolean equals(Object obj) {
    if (getClass().isInstance(obj)) {
      if (((I) obj).val.size() == val.size()) {
        return ((I) obj).val.containsAll(val);
      }
    }
    if (obj instanceof String) {
      return val.equals(new I((String) obj));
    }
    return false;
  }

  @Override
  public String toString() {
    return val.toString();
  }

}

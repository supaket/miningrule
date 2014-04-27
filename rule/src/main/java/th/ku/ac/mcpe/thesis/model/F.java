package th.ku.ac.mcpe.thesis.model;

import java.util.ArrayList;
import java.util.List;

public class F {
  public Type type;
  public List<String> fs = new ArrayList<String>();

  public F(List<String> fs, Type type) {
    super();
    this.type = type;
    this.fs = fs;
  }

  public F(String fs) {
    this.fs.add(fs);
  }
}

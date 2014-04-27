package th.ku.ac.mcpe.thesis.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class R {

  public Collection<C> c = new ArrayList<C>();

  public Collection<F> f = new ArrayList<F>();

  public S s;
  public T t;

  public R(Set<C> c, Set<F> f, S s, T t) {
    super();
    this.c = c;
    this.f = f;
    this.s = s;
    this.t = t;
  }

  public R(C c, F f, S s, T t) {
    super();
    if (c != null) this.c.add(c);
    if (f != null) this.f.add(f);
    this.s = s;
    this.t = t;
  }

  public R(String c, String f, int i, T t) {
    if (c != null) this.c.add(new C(c));
    if (f != null) this.f.add(new F(f));
    this.s = new S(i);
    this.t = t;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof R) {
      return ((R) obj).c.equals(c) && ((R) obj).f.equals(f);
    }
    return false;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(c.toString());
    sb.append(",");
    sb.append(f.toString());
    return sb.toString();
  }
}

package th.ku.ac.mcpe.thesis.model;

import java.util.HashSet;
import java.util.Set;

public class R {

  public Set<C> c = new HashSet<C>();
  public Set<F> f = new HashSet<F>();

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
    this.c.add(c);
    this.f.add(f);
    this.s = s;
    this.t = t;
  }

  public R(String c, String f, int i, T t) {
    this.c.add(new C(c));
    this.f.add(new F(f));
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
}

package th.ku.ac.mcpe.thesis.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class R {

  public Collection<C> c = new ArrayList<C>();

  public Collection<F> f = new ArrayList<F>();

  public S s;
  public T t;

  public R() {}

  public R(Set<C> c, Set<F> f, S s, T t) {
    this.c = c;
    this.f = f;
    this.s = s;
    this.t = t;
  }

  public R(C c, F f, S s, T t) {
    if (c != null) this.c.add(c);
    if (f != null) this.f.add(f);
    this.s = s;
    this.t = t;
  }

  public R(String c, String f, S s, T t) {
    if (c != null) this.c.add(new C(c));
    if (f != null) this.f.add(new F(f));
    this.s = s;
    this.t = t;
  }

  public R(String c, String f, int s, T t) {
    this(c, f, new S(s), t);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof R) {
      R r = ((R) obj);
      return eq(r.c, c) && eq(r.f, f) && eq(r.s, s) && eq(r.t, t);
    }
    return false;
  }

  private boolean eq(Object o1, Object o2) {
    if (o1 == o2) {
      return true;
    }
    if (o1 != null) {
      return o1.equals(o2);
    }
    return false;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("C[");
    sb.append(String.valueOf(c));
    sb.append("]");
    sb.append(",");
    sb.append("F[");
    sb.append(String.valueOf(f));
    sb.append("]");
    sb.append(",");
    sb.append("S[");
    sb.append(String.valueOf(s));
    sb.append("]");
    sb.append(",");
    sb.append("T[");
    sb.append(String.valueOf(t));
    sb.append("]");
    return sb.toString();
  }
}

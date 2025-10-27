package core.data;

/* loaded from: classes8.dex */
public interface Convert<S, T> {
    T toCore(S s2, T t2);

    S toProxy(T t2, S s2);
}

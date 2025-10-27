package cn.hutool.core.lang.hash;

@FunctionalInterface
/* loaded from: classes.dex */
public interface Hash32<T> extends Hash<T> {
    @Override // cn.hutool.core.lang.hash.Hash
    Number hash(T t2);

    int hash32(T t2);
}

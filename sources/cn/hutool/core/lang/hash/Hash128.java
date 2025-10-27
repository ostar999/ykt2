package cn.hutool.core.lang.hash;

@FunctionalInterface
/* loaded from: classes.dex */
public interface Hash128<T> extends Hash<T> {
    @Override // cn.hutool.core.lang.hash.Hash
    Number hash(T t2);

    Number128 hash128(T t2);
}

package cn.hutool.core.lang.hash;

@FunctionalInterface
/* loaded from: classes.dex */
public interface Hash64<T> extends Hash<T> {
    @Override // cn.hutool.core.lang.hash.Hash
    Number hash(T t2);

    long hash64(T t2);
}

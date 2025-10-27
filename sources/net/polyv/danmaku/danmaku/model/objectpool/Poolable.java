package net.polyv.danmaku.danmaku.model.objectpool;

/* loaded from: classes9.dex */
public interface Poolable<T> {
    T getNextPoolable();

    boolean isPooled();

    void setNextPoolable(T t2);

    void setPooled(boolean z2);
}

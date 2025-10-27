package net.polyv.danmaku.danmaku.model;

/* loaded from: classes9.dex */
public interface IDrawingCache<T> {
    void build(int i2, int i3, int i4, boolean z2, int i5);

    void decreaseReference();

    void destroy();

    void erase();

    T get();

    boolean hasReferences();

    int height();

    void increaseReference();

    int size();

    int width();
}

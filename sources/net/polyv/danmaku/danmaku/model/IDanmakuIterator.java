package net.polyv.danmaku.danmaku.model;

/* loaded from: classes9.dex */
public interface IDanmakuIterator {
    boolean hasNext();

    BaseDanmaku next();

    void remove();

    void reset();
}

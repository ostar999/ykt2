package net.polyv.danmaku.controller;

import android.content.Context;

/* loaded from: classes9.dex */
public interface IDanmakuViewController {
    void clear();

    long drawDanmakus();

    Context getContext();

    int getViewHeight();

    int getViewWidth();

    boolean isDanmakuDrawingCacheEnabled();

    boolean isHardwareAccelerated();

    boolean isViewReady();
}

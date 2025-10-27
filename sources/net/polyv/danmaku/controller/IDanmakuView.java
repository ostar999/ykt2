package net.polyv.danmaku.controller;

import android.view.View;
import net.polyv.danmaku.controller.DrawHandler;
import net.polyv.danmaku.danmaku.model.BaseDanmaku;
import net.polyv.danmaku.danmaku.model.IDanmakus;
import net.polyv.danmaku.danmaku.model.android.DanmakuContext;
import net.polyv.danmaku.danmaku.parser.BaseDanmakuParser;

/* loaded from: classes9.dex */
public interface IDanmakuView {
    public static final int THREAD_TYPE_HIGH_PRIORITY = 2;
    public static final int THREAD_TYPE_LOW_PRIORITY = 3;
    public static final int THREAD_TYPE_MAIN_THREAD = 1;
    public static final int THREAD_TYPE_NORMAL_PRIORITY = 0;

    public interface OnDanmakuClickListener {
        boolean onDanmakuClick(IDanmakus iDanmakus);

        boolean onDanmakuLongClick(IDanmakus iDanmakus);

        boolean onViewClick(IDanmakuView iDanmakuView);
    }

    void addDanmaku(BaseDanmaku baseDanmaku);

    void clearDanmakusOnScreen();

    void enableDanmakuDrawingCache(boolean z2);

    void forceRender();

    DanmakuContext getConfig();

    long getCurrentTime();

    IDanmakus getCurrentVisibleDanmakus();

    int getHeight();

    OnDanmakuClickListener getOnDanmakuClickListener();

    View getView();

    int getWidth();

    float getXOff();

    float getYOff();

    void hide();

    long hideAndPauseDrawTask();

    void invalidateDanmaku(BaseDanmaku baseDanmaku, boolean z2);

    boolean isDanmakuDrawingCacheEnabled();

    boolean isHardwareAccelerated();

    boolean isPaused();

    boolean isPrepared();

    boolean isShown();

    void pause();

    void prepare(BaseDanmakuParser baseDanmakuParser, DanmakuContext danmakuContext);

    void release();

    void removeAllDanmakus(boolean z2);

    void removeAllLiveDanmakus();

    void resume();

    void seekTo(Long l2);

    void setCallback(DrawHandler.Callback callback);

    void setDrawingThreadType(int i2);

    void setOnDanmakuClickListener(OnDanmakuClickListener onDanmakuClickListener);

    void setOnDanmakuClickListener(OnDanmakuClickListener onDanmakuClickListener, float f2, float f3);

    void setVisibility(int i2);

    void show();

    void showAndResumeDrawTask(Long l2);

    void showFPS(boolean z2);

    void start();

    void start(long j2);

    void stop();

    void toggle();
}

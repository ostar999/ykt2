package master.flame.danmaku.controller;

import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.renderer.IRenderer;

/* loaded from: classes6.dex */
public interface IDrawTask {
    public static final int PLAY_STATE_PAUSE = 2;
    public static final int PLAY_STATE_PLAYING = 1;

    public interface TaskListener {
        void onDanmakuAdd(BaseDanmaku baseDanmaku);

        void onDanmakuConfigChanged();

        void onDanmakuShown(BaseDanmaku baseDanmaku);

        void onDanmakusDrawingFinished();

        void ready();
    }

    void addDanmaku(BaseDanmaku baseDanmaku);

    void clearDanmakusOnScreen(long j2);

    IRenderer.RenderingState draw(AbsDisplayer absDisplayer);

    IDanmakus getVisibleDanmakusOnTime(long j2);

    void invalidateDanmaku(BaseDanmaku baseDanmaku, boolean z2);

    void onPlayStateChanged(int i2);

    void prepare();

    void quit();

    void removeAllDanmakus(boolean z2);

    void removeAllLiveDanmakus();

    void requestClear();

    void requestClearRetainer();

    void requestHide();

    void requestRender();

    void requestSync(long j2, long j3, long j4);

    void reset();

    void seek(long j2);

    void setParser(BaseDanmakuParser baseDanmakuParser);

    void start();
}

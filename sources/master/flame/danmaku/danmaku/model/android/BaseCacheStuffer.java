package master.flame.danmaku.danmaku.model.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDrawingCache;
import master.flame.danmaku.danmaku.model.android.AndroidDisplayer;

/* loaded from: classes8.dex */
public abstract class BaseCacheStuffer {
    protected Proxy mProxy;

    public static abstract class Proxy {
        public abstract void prepareDrawing(BaseDanmaku baseDanmaku, boolean z2);

        public abstract void releaseResource(BaseDanmaku baseDanmaku);
    }

    public void clearCache(BaseDanmaku baseDanmaku) {
    }

    public abstract void clearCaches();

    public boolean drawCache(BaseDanmaku baseDanmaku, Canvas canvas, float f2, float f3, Paint paint, TextPaint textPaint) {
        DrawingCacheHolder drawingCacheHolder;
        IDrawingCache<?> drawingCache = baseDanmaku.getDrawingCache();
        if (drawingCache == null || (drawingCacheHolder = (DrawingCacheHolder) drawingCache.get()) == null) {
            return false;
        }
        return drawingCacheHolder.draw(canvas, f2, f3, paint);
    }

    public abstract void drawDanmaku(BaseDanmaku baseDanmaku, Canvas canvas, float f2, float f3, boolean z2, AndroidDisplayer.DisplayerConfig displayerConfig);

    public abstract void measure(BaseDanmaku baseDanmaku, TextPaint textPaint, boolean z2);

    public void prepare(BaseDanmaku baseDanmaku, boolean z2) {
        Proxy proxy = this.mProxy;
        if (proxy != null) {
            proxy.prepareDrawing(baseDanmaku, z2);
        }
    }

    public void releaseResource(BaseDanmaku baseDanmaku) {
        Proxy proxy = this.mProxy;
        if (proxy != null) {
            proxy.releaseResource(baseDanmaku);
        }
    }

    public void setProxy(Proxy proxy) {
        this.mProxy = proxy;
    }
}

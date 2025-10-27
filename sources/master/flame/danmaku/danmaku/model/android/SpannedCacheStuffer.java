package master.flame.danmaku.danmaku.model.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import java.lang.ref.SoftReference;
import master.flame.danmaku.danmaku.model.BaseDanmaku;

/* loaded from: classes8.dex */
public class SpannedCacheStuffer extends SimpleTextCacheStuffer {
    @Override // master.flame.danmaku.danmaku.model.android.BaseCacheStuffer
    public void clearCache(BaseDanmaku baseDanmaku) {
        super.clearCache(baseDanmaku);
        Object obj = baseDanmaku.obj;
        if (obj instanceof SoftReference) {
            ((SoftReference) obj).clear();
        }
    }

    @Override // master.flame.danmaku.danmaku.model.android.SimpleTextCacheStuffer, master.flame.danmaku.danmaku.model.android.BaseCacheStuffer
    public void clearCaches() {
        super.clearCaches();
        System.gc();
    }

    @Override // master.flame.danmaku.danmaku.model.android.SimpleTextCacheStuffer
    public void drawStroke(BaseDanmaku baseDanmaku, String str, Canvas canvas, float f2, float f3, Paint paint) {
        if (baseDanmaku.obj == null) {
            super.drawStroke(baseDanmaku, str, canvas, f2, f3, paint);
        }
    }

    @Override // master.flame.danmaku.danmaku.model.android.SimpleTextCacheStuffer
    public void drawText(BaseDanmaku baseDanmaku, String str, Canvas canvas, float f2, float f3, TextPaint textPaint, boolean z2) {
        Object obj = baseDanmaku.obj;
        if (obj == null) {
            super.drawText(baseDanmaku, str, canvas, f2, f3, textPaint, z2);
            return;
        }
        StaticLayout staticLayout = (StaticLayout) ((SoftReference) obj).get();
        int i2 = baseDanmaku.requestFlags;
        boolean z3 = false;
        boolean z4 = (i2 & 1) != 0;
        boolean z5 = (i2 & 2) != 0;
        if (z5 || staticLayout == null) {
            if (z5) {
                baseDanmaku.requestFlags = i2 & (-3);
            }
            CharSequence charSequence = baseDanmaku.text;
            if (charSequence == null) {
                return;
            }
            if (z4) {
                staticLayout = new StaticLayout(charSequence, textPaint, (int) Math.ceil(Layout.getDesiredWidth(baseDanmaku.text, textPaint)), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
                baseDanmaku.paintWidth = staticLayout.getWidth();
                baseDanmaku.paintHeight = staticLayout.getHeight();
                baseDanmaku.requestFlags &= -2;
            } else {
                staticLayout = new StaticLayout(charSequence, textPaint, (int) baseDanmaku.paintWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
            }
            baseDanmaku.obj = new SoftReference(staticLayout);
        }
        if (f2 != 0.0f && f3 != 0.0f) {
            canvas.save();
            canvas.translate(f2, f3 + textPaint.ascent());
            z3 = true;
        }
        staticLayout.draw(canvas);
        if (z3) {
            canvas.restore();
        }
    }

    @Override // master.flame.danmaku.danmaku.model.android.SimpleTextCacheStuffer, master.flame.danmaku.danmaku.model.android.BaseCacheStuffer
    public void measure(BaseDanmaku baseDanmaku, TextPaint textPaint, boolean z2) {
        CharSequence charSequence = baseDanmaku.text;
        if (!(charSequence instanceof Spanned) || charSequence == null) {
            super.measure(baseDanmaku, textPaint, z2);
            return;
        }
        StaticLayout staticLayout = new StaticLayout(charSequence, textPaint, (int) Math.ceil(Layout.getDesiredWidth(baseDanmaku.text, textPaint)), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        baseDanmaku.paintWidth = staticLayout.getWidth();
        baseDanmaku.paintHeight = staticLayout.getHeight();
        baseDanmaku.obj = new SoftReference(staticLayout);
    }

    @Override // master.flame.danmaku.danmaku.model.android.BaseCacheStuffer
    public void releaseResource(BaseDanmaku baseDanmaku) {
        clearCache(baseDanmaku);
        super.releaseResource(baseDanmaku);
    }
}

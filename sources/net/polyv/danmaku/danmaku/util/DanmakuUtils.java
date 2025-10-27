package net.polyv.danmaku.danmaku.util;

import android.text.TextUtils;
import net.polyv.danmaku.danmaku.model.AbsDisplayer;
import net.polyv.danmaku.danmaku.model.BaseDanmaku;
import net.polyv.danmaku.danmaku.model.IDisplayer;
import net.polyv.danmaku.danmaku.model.android.DrawingCache;
import net.polyv.danmaku.danmaku.model.android.DrawingCacheHolder;

/* loaded from: classes9.dex */
public class DanmakuUtils {
    public static DrawingCache buildDanmakuDrawingCache(BaseDanmaku baseDanmaku, IDisplayer iDisplayer, DrawingCache drawingCache, int i2) {
        if (drawingCache == null) {
            drawingCache = new DrawingCache();
        }
        drawingCache.build((int) Math.ceil(baseDanmaku.paintWidth), (int) Math.ceil(baseDanmaku.paintHeight), iDisplayer.getDensityDpi(), false, i2);
        DrawingCacheHolder drawingCacheHolder = drawingCache.get();
        if (drawingCacheHolder != null) {
            ((AbsDisplayer) iDisplayer).drawDanmaku(baseDanmaku, drawingCacheHolder.canvas, 0.0f, 0.0f, true);
            if (iDisplayer.isHardwareAccelerated()) {
                drawingCacheHolder.splitWith(iDisplayer.getWidth(), iDisplayer.getHeight(), iDisplayer.getMaximumCacheWidth(), iDisplayer.getMaximumCacheHeight());
            }
        }
        return drawingCache;
    }

    private static boolean checkHit(int i2, int i3, float[] fArr, float[] fArr2) {
        if (i2 != i3) {
            return false;
        }
        return i2 == 1 ? fArr2[0] < fArr[2] : i2 == 6 && fArr2[2] > fArr[0];
    }

    private static boolean checkHitAtTime(IDisplayer iDisplayer, BaseDanmaku baseDanmaku, BaseDanmaku baseDanmaku2, long j2) {
        float[] rectAtTime = baseDanmaku.getRectAtTime(iDisplayer, j2);
        float[] rectAtTime2 = baseDanmaku2.getRectAtTime(iDisplayer, j2);
        if (rectAtTime == null || rectAtTime2 == null) {
            return false;
        }
        return checkHit(baseDanmaku.getType(), baseDanmaku2.getType(), rectAtTime, rectAtTime2);
    }

    public static final int compare(BaseDanmaku baseDanmaku, BaseDanmaku baseDanmaku2) {
        if (baseDanmaku == baseDanmaku2) {
            return 0;
        }
        if (baseDanmaku == null) {
            return -1;
        }
        if (baseDanmaku2 == null) {
            return 1;
        }
        long time = baseDanmaku.getTime() - baseDanmaku2.getTime();
        if (time > 0) {
            return 1;
        }
        if (time < 0) {
            return -1;
        }
        int i2 = baseDanmaku.index - baseDanmaku2.index;
        return i2 != 0 ? i2 < 0 ? -1 : 1 : baseDanmaku.hashCode() - baseDanmaku.hashCode();
    }

    public static void fillText(BaseDanmaku baseDanmaku, CharSequence charSequence) {
        baseDanmaku.text = charSequence;
        if (TextUtils.isEmpty(charSequence) || !charSequence.toString().contains("/n")) {
            return;
        }
        String[] strArrSplit = String.valueOf(baseDanmaku.text).split("/n", -1);
        if (strArrSplit.length > 1) {
            baseDanmaku.lines = strArrSplit;
        }
    }

    public static int getCacheSize(int i2, int i3, int i4) {
        return i2 * i3 * i4;
    }

    public static final boolean isDuplicate(BaseDanmaku baseDanmaku, BaseDanmaku baseDanmaku2) {
        if (baseDanmaku == baseDanmaku2) {
            return false;
        }
        CharSequence charSequence = baseDanmaku.text;
        CharSequence charSequence2 = baseDanmaku2.text;
        if (charSequence == charSequence2) {
            return true;
        }
        return charSequence != null && charSequence.equals(charSequence2);
    }

    public static final boolean isOverSize(IDisplayer iDisplayer, BaseDanmaku baseDanmaku) {
        return iDisplayer.isHardwareAccelerated() && (baseDanmaku.paintWidth > ((float) iDisplayer.getMaximumCacheWidth()) || baseDanmaku.paintHeight > ((float) iDisplayer.getMaximumCacheHeight()));
    }

    public static boolean willHitInDuration(IDisplayer iDisplayer, BaseDanmaku baseDanmaku, BaseDanmaku baseDanmaku2, long j2, long j3) {
        int type = baseDanmaku.getType();
        if (type != baseDanmaku2.getType() || baseDanmaku.isOutside()) {
            return false;
        }
        long actualTime = baseDanmaku2.getActualTime() - baseDanmaku.getActualTime();
        if (actualTime <= 0) {
            return true;
        }
        if (Math.abs(actualTime) >= j2 || baseDanmaku.isTimeOut() || baseDanmaku2.isTimeOut()) {
            return false;
        }
        if (type == 5 || type == 4) {
            return true;
        }
        return checkHitAtTime(iDisplayer, baseDanmaku, baseDanmaku2, j3) || checkHitAtTime(iDisplayer, baseDanmaku, baseDanmaku2, baseDanmaku.getActualTime() + baseDanmaku.getDuration());
    }
}

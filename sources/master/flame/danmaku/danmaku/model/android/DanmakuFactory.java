package master.flame.danmaku.danmaku.model.android;

import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.Duration;
import master.flame.danmaku.danmaku.model.FBDanmaku;
import master.flame.danmaku.danmaku.model.FTDanmaku;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.L2RDanmaku;
import master.flame.danmaku.danmaku.model.R2LDanmaku;
import master.flame.danmaku.danmaku.model.SpecialDanmaku;

/* loaded from: classes8.dex */
public class DanmakuFactory {
    public static final float BILI_PLAYER_HEIGHT = 438.0f;
    public static final float BILI_PLAYER_WIDTH = 682.0f;
    public static final long COMMON_DANMAKU_DURATION = 3800;
    public static final int DANMAKU_MEDIUM_TEXTSIZE = 25;
    public static final long MAX_DANMAKU_DURATION_HIGH_DENSITY = 9000;
    public static final long MIN_DANMAKU_DURATION = 4000;
    public static final float OLD_BILI_PLAYER_HEIGHT = 385.0f;
    public static final float OLD_BILI_PLAYER_WIDTH = 539.0f;
    public Duration MAX_Duration_Fix_Danmaku;
    public Duration MAX_Duration_Scroll_Danmaku;
    public Duration MAX_Duration_Special_Danmaku;
    private DanmakuContext sLastConfig;
    public IDisplayer sLastDisp;
    public int CURRENT_DISP_WIDTH = 0;
    public int CURRENT_DISP_HEIGHT = 0;
    private SpecialDanmaku.ScaleFactor mScaleFactor = null;
    private float CURRENT_DISP_SIZE_FACTOR = 1.0f;
    public long REAL_DANMAKU_DURATION = 3800;
    public long MAX_DANMAKU_DURATION = 4000;

    public static DanmakuFactory create() {
        return new DanmakuFactory();
    }

    public static void fillLinePathData(BaseDanmaku baseDanmaku, float[][] fArr, float f2, float f3) {
        if (baseDanmaku.getType() == 7 && fArr.length != 0 && fArr[0].length == 2) {
            for (float[] fArr2 : fArr) {
                fArr2[0] = fArr2[0] * f2;
                fArr2[1] = fArr2[1] * f3;
            }
            ((SpecialDanmaku) baseDanmaku).setLinePathData(fArr);
        }
    }

    private void updateScaleFactor(int i2, int i3, float f2, float f3) {
        if (this.mScaleFactor == null) {
            this.mScaleFactor = new SpecialDanmaku.ScaleFactor(i2, i3, f2, f3);
        }
        this.mScaleFactor.update(i2, i3, f2, f3);
    }

    private synchronized void updateSpecialDanmakusDate(int i2, int i3, float f2, float f3) {
        SpecialDanmaku.ScaleFactor scaleFactor = this.mScaleFactor;
        if (scaleFactor != null) {
            scaleFactor.update(i2, i3, f2, f3);
        }
    }

    private void updateSpecicalDanmakuDuration(BaseDanmaku baseDanmaku) {
        Duration duration;
        Duration duration2 = this.MAX_Duration_Special_Danmaku;
        if (duration2 == null || ((duration = baseDanmaku.duration) != null && duration.value > duration2.value)) {
            this.MAX_Duration_Special_Danmaku = baseDanmaku.duration;
            updateMaxDanmakuDuration();
        }
    }

    public BaseDanmaku createDanmaku(int i2) {
        return createDanmaku(i2, this.sLastConfig);
    }

    public void fillAlphaData(BaseDanmaku baseDanmaku, int i2, int i3, long j2) {
        if (baseDanmaku.getType() != 7) {
            return;
        }
        ((SpecialDanmaku) baseDanmaku).setAlphaData(i2, i3, j2);
        updateSpecicalDanmakuDuration(baseDanmaku);
    }

    public void fillTranslationData(BaseDanmaku baseDanmaku, float f2, float f3, float f4, float f5, long j2, long j3, float f6, float f7) {
        if (baseDanmaku.getType() != 7) {
            return;
        }
        ((SpecialDanmaku) baseDanmaku).setTranslationData(f2 * f6, f3 * f7, f4 * f6, f5 * f7, j2, j3);
        updateSpecicalDanmakuDuration(baseDanmaku);
    }

    public void notifyDispSizeChanged(DanmakuContext danmakuContext) {
        this.sLastConfig = danmakuContext;
        this.sLastDisp = danmakuContext.getDisplayer();
        createDanmaku(1, danmakuContext);
    }

    public void resetDurationsData() {
        this.sLastDisp = null;
        this.CURRENT_DISP_HEIGHT = 0;
        this.CURRENT_DISP_WIDTH = 0;
        this.MAX_Duration_Scroll_Danmaku = null;
        this.MAX_Duration_Fix_Danmaku = null;
        this.MAX_Duration_Special_Danmaku = null;
        this.MAX_DANMAKU_DURATION = 4000L;
    }

    public void updateDurationFactor(float f2) {
        Duration duration = this.MAX_Duration_Scroll_Danmaku;
        if (duration == null || this.MAX_Duration_Fix_Danmaku == null) {
            return;
        }
        duration.setFactor(f2);
        updateMaxDanmakuDuration();
    }

    public void updateMaxDanmakuDuration() {
        Duration duration = this.MAX_Duration_Scroll_Danmaku;
        long j2 = duration == null ? 0L : duration.value;
        Duration duration2 = this.MAX_Duration_Fix_Danmaku;
        long j3 = duration2 == null ? 0L : duration2.value;
        Duration duration3 = this.MAX_Duration_Special_Danmaku;
        long j4 = duration3 != null ? duration3.value : 0L;
        long jMax = Math.max(j2, j3);
        this.MAX_DANMAKU_DURATION = jMax;
        long jMax2 = Math.max(jMax, j4);
        this.MAX_DANMAKU_DURATION = jMax2;
        long jMax3 = Math.max(3800L, jMax2);
        this.MAX_DANMAKU_DURATION = jMax3;
        this.MAX_DANMAKU_DURATION = Math.max(this.REAL_DANMAKU_DURATION, jMax3);
    }

    public boolean updateViewportState(float f2, float f3, float f4) {
        int i2 = (int) f2;
        if (this.CURRENT_DISP_WIDTH == i2 && this.CURRENT_DISP_HEIGHT == ((int) f3) && this.CURRENT_DISP_SIZE_FACTOR == f4) {
            return false;
        }
        long j2 = (long) (((f2 * f4) / 682.0f) * 3800.0f);
        this.REAL_DANMAKU_DURATION = j2;
        long jMin = Math.min(9000L, j2);
        this.REAL_DANMAKU_DURATION = jMin;
        this.REAL_DANMAKU_DURATION = Math.max(4000L, jMin);
        this.CURRENT_DISP_WIDTH = i2;
        this.CURRENT_DISP_HEIGHT = (int) f3;
        this.CURRENT_DISP_SIZE_FACTOR = f4;
        return true;
    }

    public BaseDanmaku createDanmaku(int i2, DanmakuContext danmakuContext) {
        if (danmakuContext == null) {
            return null;
        }
        this.sLastConfig = danmakuContext;
        AbsDisplayer displayer = danmakuContext.getDisplayer();
        this.sLastDisp = displayer;
        return createDanmaku(i2, displayer.getWidth(), this.sLastDisp.getHeight(), this.CURRENT_DISP_SIZE_FACTOR, danmakuContext.scrollSpeedFactor);
    }

    public BaseDanmaku createDanmaku(int i2, IDisplayer iDisplayer, float f2, float f3) {
        if (iDisplayer == null) {
            return null;
        }
        this.sLastDisp = iDisplayer;
        return createDanmaku(i2, iDisplayer.getWidth(), iDisplayer.getHeight(), f2, f3);
    }

    public BaseDanmaku createDanmaku(int i2, int i3, int i4, float f2, float f3) {
        return createDanmaku(i2, i3, i4, f2, f3);
    }

    public BaseDanmaku createDanmaku(int i2, float f2, float f3, float f4, float f5) {
        float f6;
        int i3 = this.CURRENT_DISP_WIDTH;
        int i4 = this.CURRENT_DISP_HEIGHT;
        boolean zUpdateViewportState = updateViewportState(f2, f3, f4);
        Duration duration = this.MAX_Duration_Scroll_Danmaku;
        if (duration == null) {
            Duration duration2 = new Duration(this.REAL_DANMAKU_DURATION);
            this.MAX_Duration_Scroll_Danmaku = duration2;
            duration2.setFactor(f5);
        } else if (zUpdateViewportState) {
            duration.setValue(this.REAL_DANMAKU_DURATION);
        }
        if (this.MAX_Duration_Fix_Danmaku == null) {
            this.MAX_Duration_Fix_Danmaku = new Duration(3800L);
        }
        float f7 = 1.0f;
        if (!zUpdateViewportState || f2 <= 0.0f) {
            f6 = 1.0f;
        } else {
            updateMaxDanmakuDuration();
            if (i3 <= 0 || i4 <= 0) {
                f6 = 1.0f;
            } else {
                f7 = f2 / i3;
                f6 = f3 / i4;
            }
            int i5 = (int) f2;
            int i6 = (int) f3;
            updateScaleFactor(i5, i6, f7, f6);
            if (f3 > 0.0f) {
                updateSpecialDanmakusDate(i5, i6, f7, f6);
            }
        }
        if (i2 == 1) {
            return new R2LDanmaku(this.MAX_Duration_Scroll_Danmaku);
        }
        if (i2 == 4) {
            return new FBDanmaku(this.MAX_Duration_Fix_Danmaku);
        }
        if (i2 == 5) {
            return new FTDanmaku(this.MAX_Duration_Fix_Danmaku);
        }
        if (i2 == 6) {
            return new L2RDanmaku(this.MAX_Duration_Scroll_Danmaku);
        }
        if (i2 != 7) {
            return null;
        }
        SpecialDanmaku specialDanmaku = new SpecialDanmaku();
        updateScaleFactor((int) f2, (int) f3, f7, f6);
        specialDanmaku.setScaleFactor(this.mScaleFactor);
        return specialDanmaku;
    }
}

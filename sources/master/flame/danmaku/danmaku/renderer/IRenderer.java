package master.flame.danmaku.danmaku.renderer;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.ICacheManager;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.Danmakus;

/* loaded from: classes8.dex */
public interface IRenderer {
    public static final int CACHE_RENDERING = 1;
    public static final int NOTHING_RENDERING = 0;
    public static final int TEXT_RENDERING = 2;

    public static class Area {
        private int mMaxHeight;
        private int mMaxWidth;
        public final float[] mRefreshRect = new float[4];

        public void reset() {
            set(this.mMaxWidth, this.mMaxHeight, 0.0f, 0.0f);
        }

        public void resizeToMax() {
            set(0.0f, 0.0f, this.mMaxWidth, this.mMaxHeight);
        }

        public void set(float f2, float f3, float f4, float f5) {
            float[] fArr = this.mRefreshRect;
            fArr[0] = f2;
            fArr[1] = f3;
            fArr[2] = f4;
            fArr[3] = f5;
        }

        public void setEdge(int i2, int i3) {
            this.mMaxWidth = i2;
            this.mMaxHeight = i3;
        }
    }

    public interface OnDanmakuShownListener {
        void onDanmakuShown(BaseDanmaku baseDanmaku);
    }

    public static class RenderingState {
        public static final int UNKNOWN_TIME = -1;
        public long beginTime;
        public long cacheHitCount;
        public long cacheMissCount;
        public long consumingTime;
        public long endTime;
        public int fbDanmakuCount;
        public int ftDanmakuCount;
        public int indexInScreen;
        public boolean isRunningDanmakus;
        public int l2rDanmakuCount;
        public BaseDanmaku lastDanmaku;
        public int lastTotalDanmakuCount;
        private boolean mIsObtaining;
        public boolean nothingRendered;
        public int r2lDanmakuCount;
        public int specialDanmakuCount;
        public long sysTime;
        public int totalDanmakuCount;
        public int totalSizeInScreen;
        public DanmakuTimer timer = new DanmakuTimer();
        private IDanmakus runningDanmakus = new Danmakus(4);

        public int addCount(int i2, int i3) {
            if (i2 == 1) {
                int i4 = this.r2lDanmakuCount + i3;
                this.r2lDanmakuCount = i4;
                return i4;
            }
            if (i2 == 4) {
                int i5 = this.fbDanmakuCount + i3;
                this.fbDanmakuCount = i5;
                return i5;
            }
            if (i2 == 5) {
                int i6 = this.ftDanmakuCount + i3;
                this.ftDanmakuCount = i6;
                return i6;
            }
            if (i2 == 6) {
                int i7 = this.l2rDanmakuCount + i3;
                this.l2rDanmakuCount = i7;
                return i7;
            }
            if (i2 != 7) {
                return 0;
            }
            int i8 = this.specialDanmakuCount + i3;
            this.specialDanmakuCount = i8;
            return i8;
        }

        public int addTotalCount(int i2) {
            int i3 = this.totalDanmakuCount + i2;
            this.totalDanmakuCount = i3;
            return i3;
        }

        public void appendToRunningDanmakus(BaseDanmaku baseDanmaku) {
            if (this.mIsObtaining) {
                return;
            }
            this.runningDanmakus.addItem(baseDanmaku);
        }

        public IDanmakus obtainRunningDanmakus() {
            IDanmakus iDanmakus;
            this.mIsObtaining = true;
            synchronized (this) {
                iDanmakus = this.runningDanmakus;
                this.runningDanmakus = new Danmakus(4);
            }
            this.mIsObtaining = false;
            return iDanmakus;
        }

        public void reset() {
            this.lastTotalDanmakuCount = this.totalDanmakuCount;
            this.totalDanmakuCount = 0;
            this.specialDanmakuCount = 0;
            this.fbDanmakuCount = 0;
            this.ftDanmakuCount = 0;
            this.l2rDanmakuCount = 0;
            this.r2lDanmakuCount = 0;
            this.consumingTime = 0L;
            this.endTime = 0L;
            this.beginTime = 0L;
            this.sysTime = 0L;
            this.nothingRendered = false;
            synchronized (this) {
                this.runningDanmakus.clear();
            }
        }

        public void set(RenderingState renderingState) {
            if (renderingState == null) {
                return;
            }
            this.lastTotalDanmakuCount = renderingState.lastTotalDanmakuCount;
            this.r2lDanmakuCount = renderingState.r2lDanmakuCount;
            this.l2rDanmakuCount = renderingState.l2rDanmakuCount;
            this.ftDanmakuCount = renderingState.ftDanmakuCount;
            this.fbDanmakuCount = renderingState.fbDanmakuCount;
            this.specialDanmakuCount = renderingState.specialDanmakuCount;
            this.totalDanmakuCount = renderingState.totalDanmakuCount;
            this.consumingTime = renderingState.consumingTime;
            this.beginTime = renderingState.beginTime;
            this.endTime = renderingState.endTime;
            this.nothingRendered = renderingState.nothingRendered;
            this.sysTime = renderingState.sysTime;
            this.cacheHitCount = renderingState.cacheHitCount;
            this.cacheMissCount = renderingState.cacheMissCount;
        }
    }

    void alignBottom(boolean z2);

    void clear();

    void clearRetainer();

    void draw(IDisplayer iDisplayer, IDanmakus iDanmakus, long j2, RenderingState renderingState);

    void release();

    void removeOnDanmakuShownListener();

    void setCacheManager(ICacheManager iCacheManager);

    void setOnDanmakuShownListener(OnDanmakuShownListener onDanmakuShownListener);

    void setVerifierEnabled(boolean z2);
}

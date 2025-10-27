package net.polyv.danmaku.danmaku.model;

import android.util.SparseArray;

/* loaded from: classes9.dex */
public abstract class BaseDanmaku {
    public static final String DANMAKU_BR_CHAR = "/n";
    public static final int FLAG_REQUEST_INVALIDATE = 2;
    public static final int FLAG_REQUEST_REMEASURE = 1;
    public static final int INVISIBLE = 0;
    public static final int TYPE_FIX_BOTTOM = 4;
    public static final int TYPE_FIX_TOP = 5;
    public static final int TYPE_MOVEABLE_XXX = 0;
    public static final int TYPE_SCROLL_LR = 6;
    public static final int TYPE_SCROLL_RL = 1;
    public static final int TYPE_SPECIAL = 7;
    public static final int VISIBLE = 1;
    public IDrawingCache<?> cache;
    public Duration duration;
    public boolean forceBuildCacheInSameThread;
    public int index;
    public boolean isGuest;
    public boolean isLive;
    public String[] lines;
    protected DanmakuTimer mTimer;
    public Object obj;
    public float rotationY;
    public float rotationZ;
    public Object tag;
    public CharSequence text;
    public int textColor;
    public int textShadowColor;
    private long time;
    public long timeOffset;
    public String userHash;
    public int visibility;
    public int underlineColor = 0;
    public float textSize = -1.0f;
    public int borderColor = 0;
    public int padding = 0;
    public byte priority = 0;
    public float paintWidth = -1.0f;
    public float paintHeight = -1.0f;
    private int visibleResetFlag = 0;
    public int measureResetFlag = 0;
    public int syncTimeOffsetResetFlag = 0;
    public int prepareResetFlag = -1;
    public int userId = 0;
    protected int alpha = AlphaValue.MAX;
    public int mFilterParam = 0;
    public int filterResetFlag = -1;
    public GlobalFlagValues flags = null;
    public int requestFlags = 0;
    public int firstShownFlag = -1;
    private SparseArray<Object> mTags = new SparseArray<>();

    public int draw(IDisplayer iDisplayer) {
        return iDisplayer.draw(this);
    }

    public long getActualTime() {
        GlobalFlagValues globalFlagValues = this.flags;
        if (globalFlagValues != null && globalFlagValues.SYNC_TIME_OFFSET_RESET_FLAG == this.syncTimeOffsetResetFlag) {
            return this.time + this.timeOffset;
        }
        this.timeOffset = 0L;
        return this.time;
    }

    public int getAlpha() {
        return this.alpha;
    }

    public abstract float getBottom();

    public IDrawingCache<?> getDrawingCache() {
        return this.cache;
    }

    public long getDuration() {
        return this.duration.value;
    }

    public abstract float getLeft();

    public abstract float[] getRectAtTime(IDisplayer iDisplayer, long j2);

    public abstract float getRight();

    public Object getTag(int i2) {
        SparseArray<Object> sparseArray = this.mTags;
        if (sparseArray == null) {
            return null;
        }
        return sparseArray.get(i2);
    }

    public long getTime() {
        return this.time;
    }

    public DanmakuTimer getTimer() {
        return this.mTimer;
    }

    public abstract float getTop();

    public abstract int getType();

    public boolean hasPassedFilter() {
        if (this.filterResetFlag == this.flags.FILTER_RESET_FLAG) {
            return true;
        }
        this.mFilterParam = 0;
        return false;
    }

    public boolean isFiltered() {
        return this.filterResetFlag == this.flags.FILTER_RESET_FLAG && this.mFilterParam != 0;
    }

    public boolean isFilteredBy(int i2) {
        return this.filterResetFlag == this.flags.FILTER_RESET_FLAG && (this.mFilterParam & i2) == i2;
    }

    public boolean isLate() {
        DanmakuTimer danmakuTimer = this.mTimer;
        return danmakuTimer == null || danmakuTimer.currMillisecond < getActualTime();
    }

    public boolean isMeasured() {
        return this.paintWidth > -1.0f && this.paintHeight > -1.0f && this.measureResetFlag == this.flags.MEASURE_RESET_FLAG;
    }

    public boolean isOffset() {
        GlobalFlagValues globalFlagValues = this.flags;
        if (globalFlagValues != null && globalFlagValues.SYNC_TIME_OFFSET_RESET_FLAG == this.syncTimeOffsetResetFlag) {
            return this.timeOffset != 0;
        }
        this.timeOffset = 0L;
        return false;
    }

    public boolean isOutside() {
        DanmakuTimer danmakuTimer = this.mTimer;
        return danmakuTimer == null || isOutside(danmakuTimer.currMillisecond);
    }

    public boolean isPrepared() {
        return this.prepareResetFlag == this.flags.PREPARE_RESET_FLAG;
    }

    public boolean isShown() {
        return this.visibility == 1 && this.visibleResetFlag == this.flags.VISIBLE_RESET_FLAG;
    }

    public boolean isTimeOut() {
        DanmakuTimer danmakuTimer = this.mTimer;
        return danmakuTimer == null || isTimeOut(danmakuTimer.currMillisecond);
    }

    public abstract void layout(IDisplayer iDisplayer, float f2, float f3);

    public void measure(IDisplayer iDisplayer, boolean z2) {
        iDisplayer.measure(this, z2);
        this.measureResetFlag = this.flags.MEASURE_RESET_FLAG;
    }

    public void prepare(IDisplayer iDisplayer, boolean z2) {
        iDisplayer.prepare(this, z2);
        this.prepareResetFlag = this.flags.PREPARE_RESET_FLAG;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setTag(Object obj) {
        this.tag = obj;
    }

    public void setTime(long j2) {
        this.time = j2;
        this.timeOffset = 0L;
    }

    public void setTimeOffset(long j2) {
        this.timeOffset = j2;
        this.syncTimeOffsetResetFlag = this.flags.SYNC_TIME_OFFSET_RESET_FLAG;
    }

    public void setTimer(DanmakuTimer danmakuTimer) {
        this.mTimer = danmakuTimer;
    }

    public void setVisibility(boolean z2) {
        if (!z2) {
            this.visibility = 0;
        } else {
            this.visibleResetFlag = this.flags.VISIBLE_RESET_FLAG;
            this.visibility = 1;
        }
    }

    public boolean isOutside(long j2) {
        long actualTime = j2 - getActualTime();
        return actualTime <= 0 || actualTime >= this.duration.value;
    }

    public boolean isTimeOut(long j2) {
        return j2 - getActualTime() >= this.duration.value;
    }

    public void setTag(int i2, Object obj) {
        this.mTags.put(i2, obj);
    }
}

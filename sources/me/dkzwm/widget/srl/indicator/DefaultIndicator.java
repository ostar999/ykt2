package me.dkzwm.widget.srl.indicator;

import android.util.Log;
import androidx.annotation.NonNull;
import me.dkzwm.widget.srl.indicator.IIndicator;

/* loaded from: classes9.dex */
public class DefaultIndicator implements IIndicator, IIndicatorSetter {
    protected float mOffset;
    protected IIndicator.IOffsetCalculator mOffsetCalculator;
    private float mRawOffset;
    protected final float[] mLastMovePoint = {0.0f, 0.0f};
    protected final float[] mFingerDownPoint = {0.0f, 0.0f};
    protected int mCurrentPos = 0;
    protected int mLastPos = 0;
    protected int mHeaderHeight = -1;
    protected int mFooterHeight = -1;
    protected int mPressedPos = 0;
    protected boolean mTouched = false;
    protected int mStatus = 0;
    protected float mResistanceHeader = 1.65f;
    protected float mResistanceFooter = 1.65f;
    private int mOffsetToRefresh = 0;
    private int mOffsetToKeepHeader = 0;
    private int mOffsetToLoadMore = 0;
    private int mOffsetToKeepFooter = 0;
    private float mOffsetRatioToKeepHeaderWhileLoading = 1.0f;
    private float mOffsetRatioToKeepFooterWhileLoading = 1.0f;
    private float mRatioOfHeaderHeightToRefresh = 1.0f;
    private float mRatioOfFooterHeightToLoadMore = 1.0f;
    private float mCanMoveTheMaxRatioOfHeaderHeight = 0.0f;
    private float mCanMoveTheMaxRatioOfFooterHeight = 0.0f;

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public void checkConfig() {
        float f2 = this.mCanMoveTheMaxRatioOfHeaderHeight;
        if (f2 > 0.0f && f2 < this.mRatioOfHeaderHeightToRefresh) {
            Log.w(getClass().getSimpleName(), "If the max can move ratio of header less than the triggered refresh ratio of header, refresh will be never trigger!");
        }
        float f3 = this.mCanMoveTheMaxRatioOfFooterHeight;
        if (f3 <= 0.0f || f3 >= this.mRatioOfFooterHeightToLoadMore) {
            return;
        }
        Log.w(getClass().getSimpleName(), "If the max can move ratio of footer less than the triggered load more ratio of footer, load more will be never trigger!");
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public float getCanMoveTheMaxDistanceOfFooter() {
        return this.mCanMoveTheMaxRatioOfFooterHeight * this.mFooterHeight;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public float getCanMoveTheMaxDistanceOfHeader() {
        return this.mCanMoveTheMaxRatioOfHeaderHeight * this.mHeaderHeight;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public float getCurrentPercentOfLoadMoreOffset() {
        if (this.mFooterHeight <= 0) {
            return 0.0f;
        }
        return (this.mCurrentPos * 1.0f) / this.mOffsetToLoadMore;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public float getCurrentPercentOfRefreshOffset() {
        if (this.mHeaderHeight <= 0) {
            return 0.0f;
        }
        return (this.mCurrentPos * 1.0f) / this.mOffsetToRefresh;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public int getCurrentPos() {
        return this.mCurrentPos;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    @NonNull
    public float[] getFingerDownPoint() {
        return this.mFingerDownPoint;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public int getFooterHeight() {
        return this.mFooterHeight;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public int getHeaderHeight() {
        return this.mHeaderHeight;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    @NonNull
    public float[] getLastMovePoint() {
        return this.mLastMovePoint;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public int getLastPos() {
        return this.mLastPos;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public int getMovingStatus() {
        return this.mStatus;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public float getOffset() {
        return this.mOffset;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public int getOffsetToKeepFooterWhileLoading() {
        return this.mOffsetToKeepFooter;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public int getOffsetToKeepHeaderWhileLoading() {
        return this.mOffsetToKeepHeader;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public int getOffsetToLoadMore() {
        return this.mOffsetToLoadMore;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public int getOffsetToRefresh() {
        return this.mOffsetToRefresh;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public float getRawOffset() {
        return this.mRawOffset;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public boolean hasJustBackToStartPosition() {
        return this.mLastPos != 0 && this.mCurrentPos == 0;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public boolean hasJustLeftStartPosition() {
        return this.mLastPos == 0 && hasLeftStartPosition();
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public boolean hasLeftStartPosition() {
        return this.mCurrentPos > 0;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public boolean hasMoved() {
        return this.mCurrentPos != this.mPressedPos;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public boolean hasTouched() {
        return this.mTouched;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public boolean isAlreadyHere(int i2) {
        return this.mCurrentPos == i2;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public boolean isJustReturnedOffsetToLoadMore() {
        int i2;
        int i3 = this.mLastPos;
        int i4 = this.mOffsetToLoadMore;
        return i3 > i4 && i3 > (i2 = this.mCurrentPos) && i2 <= i4;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public boolean isJustReturnedOffsetToRefresh() {
        int i2;
        int i3 = this.mLastPos;
        int i4 = this.mOffsetToRefresh;
        return i3 > i4 && i3 > (i2 = this.mCurrentPos) && i2 <= i4;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public boolean isOverOffsetToKeepFooterWhileLoading() {
        return this.mFooterHeight >= 0 && this.mCurrentPos >= this.mOffsetToKeepFooter;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public boolean isOverOffsetToKeepHeaderWhileLoading() {
        return this.mHeaderHeight >= 0 && this.mCurrentPos >= this.mOffsetToKeepHeader;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public boolean isOverOffsetToLoadMore() {
        return this.mCurrentPos >= this.mOffsetToLoadMore;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicator
    public boolean isOverOffsetToRefresh() {
        return this.mCurrentPos >= this.mOffsetToRefresh;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicatorSetter
    public void onFingerDown(float f2, float f3) {
        this.mTouched = true;
        this.mPressedPos = this.mCurrentPos;
        float[] fArr = this.mLastMovePoint;
        fArr[0] = f2;
        fArr[1] = f3;
        float[] fArr2 = this.mFingerDownPoint;
        fArr2[0] = f2;
        fArr2[1] = f3;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicatorSetter
    public void onFingerMove(float f2, float f3) {
        processOnMove(f3 - this.mLastMovePoint[1]);
        float[] fArr = this.mLastMovePoint;
        fArr[0] = f2;
        fArr[1] = f3;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicatorSetter
    public void onFingerUp() {
        this.mTouched = false;
        this.mPressedPos = 0;
    }

    public void processOnMove(float f2) {
        IIndicator.IOffsetCalculator iOffsetCalculator = this.mOffsetCalculator;
        if (iOffsetCalculator != null) {
            this.mOffset = iOffsetCalculator.calculate(this.mStatus, this.mCurrentPos, f2);
        } else {
            int i2 = this.mStatus;
            if (i2 == 2) {
                this.mOffset = f2 / this.mResistanceHeader;
            } else if (i2 == 1) {
                this.mOffset = f2 / this.mResistanceFooter;
            } else if (f2 > 0.0f) {
                this.mOffset = f2 / this.mResistanceHeader;
            } else if (f2 < 0.0f) {
                this.mOffset = f2 / this.mResistanceFooter;
            } else {
                this.mOffset = f2;
            }
        }
        this.mRawOffset = f2;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicatorSetter
    public void setCurrentPos(int i2) {
        this.mLastPos = this.mCurrentPos;
        this.mCurrentPos = i2;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicatorSetter
    public void setFooterHeight(int i2) {
        this.mFooterHeight = i2;
        this.mOffsetToLoadMore = (int) (this.mRatioOfFooterHeightToLoadMore * i2);
        this.mOffsetToKeepFooter = (int) (this.mOffsetRatioToKeepFooterWhileLoading * i2);
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicatorSetter
    public void setHeaderHeight(int i2) {
        this.mHeaderHeight = i2;
        this.mOffsetToRefresh = (int) (this.mRatioOfHeaderHeightToRefresh * i2);
        this.mOffsetToKeepHeader = (int) (this.mOffsetRatioToKeepHeaderWhileLoading * i2);
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicatorSetter
    public void setMaxMoveRatio(float f2) {
        setMaxMoveRatioOfHeader(f2);
        setMaxMoveRatioOfFooter(f2);
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicatorSetter
    public void setMaxMoveRatioOfFooter(float f2) {
        this.mCanMoveTheMaxRatioOfFooterHeight = f2;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicatorSetter
    public void setMaxMoveRatioOfHeader(float f2) {
        this.mCanMoveTheMaxRatioOfHeaderHeight = f2;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicatorSetter
    public void setMovingStatus(int i2) {
        this.mStatus = i2;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicatorSetter
    public void setOffsetCalculator(IIndicator.IOffsetCalculator iOffsetCalculator) {
        this.mOffsetCalculator = iOffsetCalculator;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicatorSetter
    public void setRatioOfFooterToRefresh(float f2) {
        this.mRatioOfFooterHeightToLoadMore = f2;
        this.mOffsetToLoadMore = (int) (this.mFooterHeight * f2);
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicatorSetter
    public void setRatioOfHeaderToRefresh(float f2) {
        this.mRatioOfHeaderHeightToRefresh = f2;
        this.mOffsetToRefresh = (int) (this.mHeaderHeight * f2);
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicatorSetter
    public void setRatioToKeepFooter(float f2) {
        this.mOffsetRatioToKeepFooterWhileLoading = f2;
        this.mOffsetToKeepFooter = (int) (f2 * this.mFooterHeight);
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicatorSetter
    public void setRatioToKeepHeader(float f2) {
        this.mOffsetRatioToKeepHeaderWhileLoading = f2;
        this.mOffsetToKeepHeader = (int) (f2 * this.mHeaderHeight);
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicatorSetter
    public void setRatioToRefresh(float f2) {
        setRatioOfHeaderToRefresh(f2);
        setRatioOfFooterToRefresh(f2);
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicatorSetter
    public void setResistance(float f2) {
        this.mResistanceHeader = f2;
        this.mResistanceFooter = f2;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicatorSetter
    public void setResistanceOfFooter(float f2) {
        this.mResistanceFooter = f2;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicatorSetter
    public void setResistanceOfHeader(float f2) {
        this.mResistanceHeader = f2;
    }

    @Override // me.dkzwm.widget.srl.indicator.IIndicatorSetter
    public void onFingerDown() {
        this.mTouched = true;
    }
}

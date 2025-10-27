package me.dkzwm.widget.srl.extra.footer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.annotation.StringRes;
import me.dkzwm.widget.srl.SmoothRefreshLayout;
import me.dkzwm.widget.srl.ext.classic.R;
import me.dkzwm.widget.srl.extra.AbsClassicRefreshView;
import me.dkzwm.widget.srl.extra.ClassicConfig;
import me.dkzwm.widget.srl.indicator.IIndicator;

/* loaded from: classes9.dex */
public class ClassicFooter<T extends IIndicator> extends AbsClassicRefreshView<T> {

    @StringRes
    private int mLoadFailRes;

    @StringRes
    private int mLoadSuccessfulRes;

    @StringRes
    private int mLoadingRes;
    private boolean mNoMoreDataChangedView;

    @StringRes
    private int mNoMoreDataRes;

    @StringRes
    private int mPullUpRes;

    @StringRes
    private int mPullUpToLoadRes;

    @StringRes
    private int mReleaseToLoadRes;

    public ClassicFooter(Context context) {
        this(context, null);
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public int getType() {
        return 1;
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onRefreshBegin(SmoothRefreshLayout smoothRefreshLayout, T t2) {
        this.mArrowImageView.clearAnimation();
        this.mArrowImageView.setVisibility(4);
        this.mProgressBar.setVisibility(0);
        this.mTitleTextView.setVisibility(0);
        this.mTitleTextView.setText(this.mLoadingRes);
        tryUpdateLastUpdateTime();
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onRefreshComplete(SmoothRefreshLayout smoothRefreshLayout, boolean z2) {
        this.mArrowImageView.clearAnimation();
        this.mArrowImageView.setVisibility(4);
        this.mProgressBar.setVisibility(4);
        this.mTitleTextView.setVisibility(0);
        boolean zIsEnabledNoMoreData = smoothRefreshLayout.isEnabledNoMoreData();
        if (smoothRefreshLayout.isRefreshSuccessful()) {
            this.mTitleTextView.setText(zIsEnabledNoMoreData ? this.mNoMoreDataRes : this.mLoadSuccessfulRes);
            this.mLastUpdateTime = System.currentTimeMillis();
            ClassicConfig.updateTime(getContext(), this.mLastUpdateTimeKey, this.mLastUpdateTime);
        } else {
            this.mTitleTextView.setText(zIsEnabledNoMoreData ? this.mNoMoreDataRes : this.mLoadFailRes);
        }
        if (zIsEnabledNoMoreData) {
            this.mLastUpdateTimeUpdater.stop();
            this.mLastUpdateTextView.setVisibility(8);
        }
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onRefreshPositionChanged(SmoothRefreshLayout smoothRefreshLayout, byte b3, T t2) {
        int offsetToLoadMore = t2.getOffsetToLoadMore();
        int currentPos = t2.getCurrentPos();
        int lastPos = t2.getLastPos();
        if (smoothRefreshLayout.isEnabledNoMoreData()) {
            if (currentPos <= lastPos || this.mNoMoreDataChangedView) {
                return;
            }
            this.mTitleTextView.setVisibility(0);
            this.mLastUpdateTextView.setVisibility(8);
            this.mProgressBar.setVisibility(4);
            this.mLastUpdateTimeUpdater.stop();
            this.mArrowImageView.clearAnimation();
            this.mArrowImageView.setVisibility(8);
            this.mTitleTextView.setText(this.mNoMoreDataRes);
            this.mNoMoreDataChangedView = true;
            return;
        }
        this.mNoMoreDataChangedView = false;
        if (currentPos < offsetToLoadMore && lastPos >= offsetToLoadMore) {
            if (t2.hasTouched() && b3 == 2) {
                this.mTitleTextView.setVisibility(0);
                if (!smoothRefreshLayout.isEnabledPullToRefresh() || smoothRefreshLayout.isDisabledPerformLoadMore()) {
                    this.mTitleTextView.setText(this.mPullUpRes);
                } else {
                    this.mTitleTextView.setText(this.mPullUpToLoadRes);
                }
                this.mArrowImageView.setVisibility(0);
                this.mArrowImageView.clearAnimation();
                this.mArrowImageView.startAnimation(this.mReverseFlipAnimation);
                return;
            }
            return;
        }
        if (currentPos <= offsetToLoadMore || lastPos > offsetToLoadMore || !t2.hasTouched() || b3 != 2) {
            return;
        }
        this.mTitleTextView.setVisibility(0);
        if (!smoothRefreshLayout.isEnabledPullToRefresh() && !smoothRefreshLayout.isDisabledPerformLoadMore()) {
            this.mTitleTextView.setText(this.mReleaseToLoadRes);
        }
        this.mArrowImageView.setVisibility(0);
        this.mArrowImageView.clearAnimation();
        this.mArrowImageView.startAnimation(this.mFlipAnimation);
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onRefreshPrepare(SmoothRefreshLayout smoothRefreshLayout) {
        this.mShouldShowLastUpdate = true;
        this.mNoMoreDataChangedView = false;
        tryUpdateLastUpdateTime();
        if (TextUtils.isEmpty(this.mLastUpdateTimeKey)) {
            this.mLastUpdateTimeUpdater.start();
        }
        this.mProgressBar.setVisibility(4);
        this.mArrowImageView.setVisibility(0);
        this.mTitleTextView.setVisibility(0);
        if (!smoothRefreshLayout.isEnabledPullToRefresh() || smoothRefreshLayout.isDisabledPerformLoadMore()) {
            this.mTitleTextView.setText(this.mPullUpRes);
        } else {
            this.mTitleTextView.setText(this.mPullUpToLoadRes);
        }
    }

    @Override // me.dkzwm.widget.srl.extra.AbsClassicRefreshView, me.dkzwm.widget.srl.extra.IRefreshView
    public void onReset(SmoothRefreshLayout smoothRefreshLayout) {
        super.onReset(smoothRefreshLayout);
        this.mNoMoreDataChangedView = false;
    }

    public void setLoadFailRes(@StringRes int i2) {
        this.mLoadFailRes = i2;
    }

    public void setLoadSuccessfulRes(@StringRes int i2) {
        this.mLoadSuccessfulRes = i2;
    }

    public void setLoadingRes(@StringRes int i2) {
        this.mLoadingRes = i2;
    }

    public void setNoMoreDataRes(int i2) {
        this.mNoMoreDataRes = i2;
    }

    public void setPullUpRes(@StringRes int i2) {
        this.mPullUpRes = i2;
    }

    public void setPullUpToLoadRes(@StringRes int i2) {
        this.mPullUpToLoadRes = i2;
    }

    public void setReleaseToLoadRes(@StringRes int i2) {
        this.mReleaseToLoadRes = i2;
    }

    public ClassicFooter(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ClassicFooter(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mNoMoreDataChangedView = false;
        this.mPullUpToLoadRes = R.string.sr_pull_up_to_load;
        this.mPullUpRes = R.string.sr_pull_up;
        this.mLoadingRes = R.string.sr_loading;
        this.mLoadSuccessfulRes = R.string.sr_load_complete;
        this.mLoadFailRes = R.string.sr_load_failed;
        this.mReleaseToLoadRes = R.string.sr_release_to_load;
        this.mNoMoreDataRes = R.string.sr_no_more_data;
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.sr_classic_arrow_icon);
        Matrix matrix = new Matrix();
        matrix.postRotate(180.0f);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeResource, 0, 0, bitmapDecodeResource.getWidth(), bitmapDecodeResource.getHeight(), matrix, true);
        if (!bitmapDecodeResource.isRecycled()) {
            bitmapDecodeResource.recycle();
        }
        this.mArrowImageView.setImageBitmap(bitmapCreateBitmap);
    }
}

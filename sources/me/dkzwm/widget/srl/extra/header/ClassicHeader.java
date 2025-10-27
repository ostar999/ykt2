package me.dkzwm.widget.srl.extra.header;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.annotation.StringRes;
import me.dkzwm.widget.srl.SmoothRefreshLayout;
import me.dkzwm.widget.srl.ext.classic.R;
import me.dkzwm.widget.srl.extra.AbsClassicRefreshView;
import me.dkzwm.widget.srl.extra.ClassicConfig;
import me.dkzwm.widget.srl.indicator.IIndicator;

/* loaded from: classes9.dex */
public class ClassicHeader<T extends IIndicator> extends AbsClassicRefreshView<T> {

    @StringRes
    private int mPullDownRes;

    @StringRes
    private int mPullDownToRefreshRes;

    @StringRes
    private int mRefreshFailRes;

    @StringRes
    private int mRefreshSuccessfulRes;

    @StringRes
    private int mRefreshingRes;

    @StringRes
    private int mReleaseToRefreshRes;

    public ClassicHeader(Context context) {
        this(context, null);
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public int getType() {
        return 0;
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onRefreshBegin(SmoothRefreshLayout smoothRefreshLayout, T t2) {
        this.mArrowImageView.clearAnimation();
        this.mArrowImageView.setVisibility(4);
        this.mProgressBar.setVisibility(0);
        this.mTitleTextView.setVisibility(0);
        this.mTitleTextView.setText(this.mRefreshingRes);
        tryUpdateLastUpdateTime();
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onRefreshComplete(SmoothRefreshLayout smoothRefreshLayout, boolean z2) {
        this.mArrowImageView.clearAnimation();
        this.mArrowImageView.setVisibility(4);
        this.mProgressBar.setVisibility(4);
        this.mTitleTextView.setVisibility(0);
        if (!smoothRefreshLayout.isRefreshSuccessful()) {
            this.mTitleTextView.setText(this.mRefreshFailRes);
            return;
        }
        this.mTitleTextView.setText(this.mRefreshSuccessfulRes);
        this.mLastUpdateTime = System.currentTimeMillis();
        ClassicConfig.updateTime(getContext(), this.mLastUpdateTimeKey, this.mLastUpdateTime);
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onRefreshPositionChanged(SmoothRefreshLayout smoothRefreshLayout, byte b3, T t2) {
        int offsetToRefresh = t2.getOffsetToRefresh();
        int currentPos = t2.getCurrentPos();
        int lastPos = t2.getLastPos();
        if (currentPos < offsetToRefresh && lastPos >= offsetToRefresh) {
            if (t2.hasTouched() && b3 == 2) {
                this.mTitleTextView.setVisibility(0);
                if (smoothRefreshLayout.isEnabledPullToRefresh()) {
                    this.mTitleTextView.setText(this.mPullDownToRefreshRes);
                } else {
                    this.mTitleTextView.setText(this.mPullDownRes);
                }
                this.mArrowImageView.setVisibility(0);
                this.mArrowImageView.clearAnimation();
                this.mArrowImageView.startAnimation(this.mReverseFlipAnimation);
                return;
            }
            return;
        }
        if (currentPos <= offsetToRefresh || lastPos > offsetToRefresh || !t2.hasTouched() || b3 != 2) {
            return;
        }
        this.mTitleTextView.setVisibility(0);
        if (!smoothRefreshLayout.isEnabledPullToRefresh()) {
            this.mTitleTextView.setText(this.mReleaseToRefreshRes);
        }
        this.mArrowImageView.setVisibility(0);
        this.mArrowImageView.clearAnimation();
        this.mArrowImageView.startAnimation(this.mFlipAnimation);
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onRefreshPrepare(SmoothRefreshLayout smoothRefreshLayout) {
        this.mShouldShowLastUpdate = true;
        tryUpdateLastUpdateTime();
        if (!TextUtils.isEmpty(this.mLastUpdateTimeKey)) {
            this.mLastUpdateTimeUpdater.start();
        }
        this.mProgressBar.setVisibility(4);
        this.mArrowImageView.setVisibility(0);
        this.mTitleTextView.setVisibility(0);
        if (smoothRefreshLayout.isEnabledPullToRefresh()) {
            this.mTitleTextView.setText(this.mPullDownToRefreshRes);
        } else {
            this.mTitleTextView.setText(this.mPullDownRes);
        }
    }

    public void setPullDownRes(@StringRes int i2) {
        this.mPullDownRes = i2;
    }

    public void setPullDownToRefreshRes(@StringRes int i2) {
        this.mPullDownToRefreshRes = i2;
    }

    public void setRefreshFailRes(@StringRes int i2) {
        this.mRefreshFailRes = i2;
    }

    public void setRefreshSuccessfulRes(@StringRes int i2) {
        this.mRefreshSuccessfulRes = i2;
    }

    public void setRefreshingRes(@StringRes int i2) {
        this.mRefreshingRes = i2;
    }

    public void setReleaseToRefreshRes(@StringRes int i2) {
        this.mReleaseToRefreshRes = i2;
    }

    public ClassicHeader(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ClassicHeader(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mPullDownToRefreshRes = R.string.sr_pull_down_to_refresh;
        this.mPullDownRes = R.string.sr_pull_down;
        this.mRefreshingRes = R.string.sr_refreshing;
        this.mRefreshSuccessfulRes = R.string.sr_refresh_complete;
        this.mRefreshFailRes = R.string.sr_refresh_failed;
        this.mReleaseToRefreshRes = R.string.sr_release_to_refresh;
        this.mArrowImageView.setImageResource(R.drawable.sr_classic_arrow_icon);
    }
}

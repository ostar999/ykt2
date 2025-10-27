package me.dkzwm.widget.srl.extra;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import me.dkzwm.widget.srl.SmoothRefreshLayout;
import me.dkzwm.widget.srl.ext.classic.R;
import me.dkzwm.widget.srl.extra.LastUpdateTimeUpdater;
import me.dkzwm.widget.srl.indicator.IIndicator;
import me.dkzwm.widget.srl.utils.PixelUtl;

/* loaded from: classes9.dex */
public abstract class AbsClassicRefreshView<T extends IIndicator> extends RelativeLayout implements IRefreshView<T>, LastUpdateTimeUpdater.ITimeUpdater {
    private static final Interpolator sLinearInterpolator = new LinearInterpolator();
    protected ImageView mArrowImageView;
    protected int mDefaultHeightInDP;
    protected RotateAnimation mFlipAnimation;
    protected TextView mLastUpdateTextView;
    protected long mLastUpdateTime;
    protected String mLastUpdateTimeKey;
    protected LastUpdateTimeUpdater mLastUpdateTimeUpdater;
    protected ProgressBar mProgressBar;
    protected RotateAnimation mReverseFlipAnimation;
    protected int mRotateAniTime;
    protected boolean mShouldShowLastUpdate;
    protected int mStyle;
    protected TextView mTitleTextView;

    public AbsClassicRefreshView(Context context) {
        this(context, null);
    }

    @Override // me.dkzwm.widget.srl.extra.LastUpdateTimeUpdater.ITimeUpdater
    public boolean canUpdate() {
        return !TextUtils.isEmpty(this.mLastUpdateTimeKey) && this.mShouldShowLastUpdate;
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public int getCustomHeight() {
        return PixelUtl.dp2px(getContext(), this.mDefaultHeightInDP);
    }

    public TextView getLastUpdateTextView() {
        return this.mLastUpdateTextView;
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public int getStyle() {
        return this.mStyle;
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    @NonNull
    public View getView() {
        return this;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mLastUpdateTimeUpdater.stop();
        this.mFlipAnimation.cancel();
        this.mReverseFlipAnimation.cancel();
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onFingerUp(SmoothRefreshLayout smoothRefreshLayout, T t2) {
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onPureScrollPositionChanged(SmoothRefreshLayout smoothRefreshLayout, byte b3, T t2) {
        if (t2.hasJustLeftStartPosition()) {
            this.mArrowImageView.clearAnimation();
            this.mArrowImageView.setVisibility(4);
            this.mProgressBar.setVisibility(4);
            this.mTitleTextView.setVisibility(8);
            this.mArrowImageView.setVisibility(8);
            this.mLastUpdateTextView.setVisibility(8);
            this.mShouldShowLastUpdate = false;
            this.mLastUpdateTimeUpdater.stop();
            tryUpdateLastUpdateTime();
        }
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onReset(SmoothRefreshLayout smoothRefreshLayout) {
        this.mArrowImageView.clearAnimation();
        this.mArrowImageView.setVisibility(0);
        this.mProgressBar.setVisibility(4);
        this.mShouldShowLastUpdate = true;
        this.mLastUpdateTimeUpdater.stop();
        tryUpdateLastUpdateTime();
    }

    public void setDefaultHeightInDP(@IntRange(from = 0) int i2) {
        this.mDefaultHeightInDP = i2;
    }

    public void setLastUpdateTextColor(@ColorInt int i2) {
        this.mLastUpdateTextView.setTextColor(i2);
    }

    public void setLastUpdateTimeKey(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mLastUpdateTimeKey = str;
    }

    public void setRotateAniTime(int i2) {
        if (i2 == this.mRotateAniTime || i2 <= 0) {
            return;
        }
        this.mRotateAniTime = i2;
        this.mFlipAnimation.setDuration(i2);
        this.mReverseFlipAnimation.setDuration(this.mRotateAniTime);
    }

    public void setStyle(int i2) {
        this.mStyle = i2;
        requestLayout();
    }

    public void setTimeUpdater(@NonNull LastUpdateTimeUpdater.ITimeUpdater iTimeUpdater) {
        this.mLastUpdateTimeUpdater.setTimeUpdater(iTimeUpdater);
    }

    public void setTitleTextColor(@ColorInt int i2) {
        this.mTitleTextView.setTextColor(i2);
    }

    public void tryUpdateLastUpdateTime() {
        if (canUpdate()) {
            updateTime(this);
        }
    }

    @Override // me.dkzwm.widget.srl.extra.LastUpdateTimeUpdater.ITimeUpdater
    public void updateTime(AbsClassicRefreshView absClassicRefreshView) {
        String lastUpdateTime = ClassicConfig.getLastUpdateTime(getContext(), this.mLastUpdateTime, this.mLastUpdateTimeKey);
        if (TextUtils.isEmpty(lastUpdateTime)) {
            this.mLastUpdateTextView.setVisibility(8);
        } else {
            this.mLastUpdateTextView.setVisibility(0);
            this.mLastUpdateTextView.setText(lastUpdateTime);
        }
    }

    public AbsClassicRefreshView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AbsClassicRefreshView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mStyle = 0;
        this.mDefaultHeightInDP = 64;
        this.mLastUpdateTime = -1L;
        this.mRotateAniTime = 200;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AbsClassicRefreshView, 0, 0);
            this.mStyle = typedArrayObtainStyledAttributes.getInt(R.styleable.AbsClassicRefreshView_sr_style, this.mStyle);
            typedArrayObtainStyledAttributes.recycle();
        }
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, -180.0f, 1, 0.5f, 1, 0.5f);
        this.mFlipAnimation = rotateAnimation;
        Interpolator interpolator = sLinearInterpolator;
        rotateAnimation.setInterpolator(interpolator);
        this.mFlipAnimation.setDuration(this.mRotateAniTime);
        this.mFlipAnimation.setFillAfter(true);
        RotateAnimation rotateAnimation2 = new RotateAnimation(-180.0f, 0.0f, 1, 0.5f, 1, 0.5f);
        this.mReverseFlipAnimation = rotateAnimation2;
        rotateAnimation2.setInterpolator(interpolator);
        this.mReverseFlipAnimation.setDuration(this.mRotateAniTime);
        this.mReverseFlipAnimation.setFillAfter(true);
        ClassicConfig.createClassicViews(this);
        this.mArrowImageView = (ImageView) findViewById(R.id.sr_classic_arrow);
        this.mTitleTextView = (TextView) findViewById(R.id.sr_classic_title);
        this.mLastUpdateTextView = (TextView) findViewById(R.id.sr_classic_last_update);
        this.mProgressBar = (ProgressBar) findViewById(R.id.sr_classic_progress);
        this.mLastUpdateTimeUpdater = new LastUpdateTimeUpdater(this);
        this.mArrowImageView.clearAnimation();
        this.mArrowImageView.setVisibility(0);
        this.mProgressBar.setVisibility(4);
    }
}

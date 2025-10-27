package com.scwang.smart.refresh.classics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.scwang.smart.drawable.PaintDrawable;
import com.scwang.smart.refresh.classics.ClassicsAbstract;
import com.scwang.smart.refresh.footer.classics.R;
import com.scwang.smart.refresh.layout.api.RefreshComponent;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.simple.SimpleComponent;
import com.scwang.smart.refresh.layout.util.SmartUtil;

/* loaded from: classes6.dex */
public abstract class ClassicsAbstract<T extends ClassicsAbstract<?>> extends SimpleComponent implements RefreshComponent {
    protected PaintDrawable mArrowDrawable;
    protected ImageView mArrowView;
    protected int mBackgroundColor;
    protected int mFinishDuration;
    protected int mMinHeightOfContent;
    protected int mPaddingBottom;
    protected int mPaddingTop;
    protected PaintDrawable mProgressDrawable;
    protected ImageView mProgressView;
    protected RefreshKernel mRefreshKernel;
    protected boolean mSetAccentColor;
    protected boolean mSetPrimaryColor;
    protected TextView mTitleText;
    public static final int ID_TEXT_TITLE = R.id.srl_classics_title;
    public static final int ID_IMAGE_ARROW = R.id.srl_classics_arrow;
    public static final int ID_IMAGE_PROGRESS = R.id.srl_classics_progress;

    public ClassicsAbstract(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mFinishDuration = 500;
        this.mPaddingTop = 20;
        this.mPaddingBottom = 20;
        this.mMinHeightOfContent = 0;
        this.mSpinnerStyle = SpinnerStyle.Translate;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ImageView imageView = this.mArrowView;
        ImageView imageView2 = this.mProgressView;
        imageView.animate().cancel();
        imageView2.animate().cancel();
        Object drawable = this.mProgressView.getDrawable();
        if (drawable instanceof Animatable) {
            Animatable animatable = (Animatable) drawable;
            if (animatable.isRunning()) {
                animatable.stop();
            }
        }
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean z2) {
        ImageView imageView = this.mProgressView;
        Object drawable = imageView.getDrawable();
        if (drawable instanceof Animatable) {
            Animatable animatable = (Animatable) drawable;
            if (animatable.isRunning()) {
                animatable.stop();
            }
        } else {
            imageView.animate().rotation(0.0f).setDuration(0L);
        }
        imageView.setVisibility(8);
        return this.mFinishDuration;
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onInitialized(@NonNull RefreshKernel refreshKernel, int i2, int i3) {
        this.mRefreshKernel = refreshKernel;
        refreshKernel.requestDrawBackgroundFor(this, this.mBackgroundColor);
    }

    @Override // android.widget.RelativeLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        if (this.mMinHeightOfContent == 0) {
            this.mPaddingTop = getPaddingTop();
            int paddingBottom = getPaddingBottom();
            this.mPaddingBottom = paddingBottom;
            if (this.mPaddingTop == 0 || paddingBottom == 0) {
                int paddingLeft = getPaddingLeft();
                int paddingRight = getPaddingRight();
                int iDp2px = this.mPaddingTop;
                if (iDp2px == 0) {
                    iDp2px = SmartUtil.dp2px(20.0f);
                }
                this.mPaddingTop = iDp2px;
                int iDp2px2 = this.mPaddingBottom;
                if (iDp2px2 == 0) {
                    iDp2px2 = SmartUtil.dp2px(20.0f);
                }
                this.mPaddingBottom = iDp2px2;
                setPadding(paddingLeft, this.mPaddingTop, paddingRight, iDp2px2);
            }
            setClipToPadding(false);
        }
        if (View.MeasureSpec.getMode(i3) == 1073741824) {
            int size = View.MeasureSpec.getSize(i3);
            int i4 = this.mMinHeightOfContent;
            if (size < i4) {
                int i5 = (size - i4) / 2;
                setPadding(getPaddingLeft(), i5, getPaddingRight(), i5);
            } else {
                setPadding(getPaddingLeft(), 0, getPaddingRight(), 0);
            }
        } else {
            setPadding(getPaddingLeft(), this.mPaddingTop, getPaddingRight(), this.mPaddingBottom);
        }
        super.onMeasure(i2, i3);
        if (this.mMinHeightOfContent == 0) {
            for (int i6 = 0; i6 < getChildCount(); i6++) {
                int measuredHeight = getChildAt(i6).getMeasuredHeight();
                if (this.mMinHeightOfContent < measuredHeight) {
                    this.mMinHeightOfContent = measuredHeight;
                }
            }
        }
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onReleased(@NonNull RefreshLayout refreshLayout, int i2, int i3) {
        onStartAnimator(refreshLayout, i2, i3);
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int i2, int i3) {
        ImageView imageView = this.mProgressView;
        if (imageView.getVisibility() != 0) {
            imageView.setVisibility(0);
            Object drawable = this.mProgressView.getDrawable();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            } else {
                imageView.animate().rotation(36000.0f).setDuration(100000L);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T self() {
        return this;
    }

    public T setAccentColor(@ColorInt int i2) {
        this.mSetAccentColor = true;
        this.mTitleText.setTextColor(i2);
        PaintDrawable paintDrawable = this.mArrowDrawable;
        if (paintDrawable != null) {
            paintDrawable.setColor(i2);
            this.mArrowView.invalidateDrawable(this.mArrowDrawable);
        }
        PaintDrawable paintDrawable2 = this.mProgressDrawable;
        if (paintDrawable2 != null) {
            paintDrawable2.setColor(i2);
            this.mProgressView.invalidateDrawable(this.mProgressDrawable);
        }
        return (T) self();
    }

    public T setAccentColorId(@ColorRes int i2) {
        setAccentColor(ContextCompat.getColor(getContext(), i2));
        return (T) self();
    }

    public T setArrowBitmap(Bitmap bitmap) {
        this.mArrowDrawable = null;
        this.mArrowView.setImageBitmap(bitmap);
        return (T) self();
    }

    public T setArrowDrawable(Drawable drawable) {
        this.mArrowDrawable = null;
        this.mArrowView.setImageDrawable(drawable);
        return (T) self();
    }

    public T setArrowResource(@DrawableRes int i2) {
        this.mArrowDrawable = null;
        this.mArrowView.setImageResource(i2);
        return (T) self();
    }

    public T setDrawableArrowSize(float f2) {
        ImageView imageView = this.mArrowView;
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        int iDp2px = SmartUtil.dp2px(f2);
        layoutParams.width = iDp2px;
        layoutParams.height = iDp2px;
        imageView.setLayoutParams(layoutParams);
        return (T) self();
    }

    public T setDrawableArrowSizePx(int i2) {
        ViewGroup.LayoutParams layoutParams = this.mArrowView.getLayoutParams();
        layoutParams.width = i2;
        layoutParams.height = i2;
        this.mArrowView.setLayoutParams(layoutParams);
        return (T) self();
    }

    public T setDrawableMarginRight(float f2) {
        ImageView imageView = this.mArrowView;
        ImageView imageView2 = this.mProgressView;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) imageView2.getLayoutParams();
        int iDp2px = SmartUtil.dp2px(f2);
        marginLayoutParams2.rightMargin = iDp2px;
        marginLayoutParams.rightMargin = iDp2px;
        imageView.setLayoutParams(marginLayoutParams);
        imageView2.setLayoutParams(marginLayoutParams2);
        return (T) self();
    }

    public T setDrawableMarginRightPx(int i2) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mArrowView.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mProgressView.getLayoutParams();
        marginLayoutParams2.rightMargin = i2;
        marginLayoutParams.rightMargin = i2;
        this.mArrowView.setLayoutParams(marginLayoutParams);
        this.mProgressView.setLayoutParams(marginLayoutParams2);
        return (T) self();
    }

    public T setDrawableProgressSize(float f2) {
        ImageView imageView = this.mProgressView;
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        int iDp2px = SmartUtil.dp2px(f2);
        layoutParams.width = iDp2px;
        layoutParams.height = iDp2px;
        imageView.setLayoutParams(layoutParams);
        return (T) self();
    }

    public T setDrawableProgressSizePx(int i2) {
        ViewGroup.LayoutParams layoutParams = this.mProgressView.getLayoutParams();
        layoutParams.width = i2;
        layoutParams.height = i2;
        this.mProgressView.setLayoutParams(layoutParams);
        return (T) self();
    }

    public T setDrawableSize(float f2) {
        ImageView imageView = this.mArrowView;
        ImageView imageView2 = this.mProgressView;
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = imageView2.getLayoutParams();
        int iDp2px = SmartUtil.dp2px(f2);
        layoutParams2.width = iDp2px;
        layoutParams.width = iDp2px;
        int iDp2px2 = SmartUtil.dp2px(f2);
        layoutParams2.height = iDp2px2;
        layoutParams.height = iDp2px2;
        imageView.setLayoutParams(layoutParams);
        imageView2.setLayoutParams(layoutParams2);
        return (T) self();
    }

    public T setDrawableSizePx(int i2) {
        ViewGroup.LayoutParams layoutParams = this.mArrowView.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = this.mProgressView.getLayoutParams();
        layoutParams2.width = i2;
        layoutParams.width = i2;
        layoutParams2.height = i2;
        layoutParams.height = i2;
        this.mArrowView.setLayoutParams(layoutParams);
        this.mProgressView.setLayoutParams(layoutParams2);
        return (T) self();
    }

    public T setFinishDuration(int i2) {
        this.mFinishDuration = i2;
        return (T) self();
    }

    public T setPrimaryColor(@ColorInt int i2) {
        this.mSetPrimaryColor = true;
        this.mBackgroundColor = i2;
        RefreshKernel refreshKernel = this.mRefreshKernel;
        if (refreshKernel != null) {
            refreshKernel.requestDrawBackgroundFor(this, i2);
        }
        return (T) self();
    }

    public T setPrimaryColorId(@ColorRes int i2) {
        setPrimaryColor(ContextCompat.getColor(getContext(), i2));
        return (T) self();
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void setPrimaryColors(@ColorInt int... iArr) {
        if (iArr.length > 0) {
            if (!(getBackground() instanceof BitmapDrawable) && !this.mSetPrimaryColor) {
                setPrimaryColor(iArr[0]);
                this.mSetPrimaryColor = false;
            }
            if (this.mSetAccentColor) {
                return;
            }
            if (iArr.length > 1) {
                setAccentColor(iArr[1]);
            }
            this.mSetAccentColor = false;
        }
    }

    public T setProgressBitmap(Bitmap bitmap) {
        this.mProgressDrawable = null;
        this.mProgressView.setImageBitmap(bitmap);
        return (T) self();
    }

    public T setProgressDrawable(Drawable drawable) {
        this.mProgressDrawable = null;
        this.mProgressView.setImageDrawable(drawable);
        return (T) self();
    }

    public T setProgressResource(@DrawableRes int i2) {
        this.mProgressDrawable = null;
        this.mProgressView.setImageResource(i2);
        return (T) self();
    }

    public T setSpinnerStyle(SpinnerStyle spinnerStyle) {
        this.mSpinnerStyle = spinnerStyle;
        return (T) self();
    }

    public T setTextSizeTitle(float f2) {
        this.mTitleText.setTextSize(f2);
        RefreshKernel refreshKernel = this.mRefreshKernel;
        if (refreshKernel != null) {
            refreshKernel.requestRemeasureHeightFor(this);
        }
        return (T) self();
    }

    public T setTextSizeTitle(int i2, float f2) {
        this.mTitleText.setTextSize(i2, f2);
        RefreshKernel refreshKernel = this.mRefreshKernel;
        if (refreshKernel != null) {
            refreshKernel.requestRemeasureHeightFor(this);
        }
        return (T) self();
    }
}

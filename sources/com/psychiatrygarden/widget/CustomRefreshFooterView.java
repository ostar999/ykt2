package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.internal.ArrowDrawable;
import com.scwang.smartrefresh.layout.internal.InternalClassics;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.scwang.smartrefresh.layout.util.SmartUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class CustomRefreshFooterView extends InternalClassics<ClassicsFooter> implements RefreshFooter {
    public static String REFRESH_FOOTER_FAILED;
    public static String REFRESH_FOOTER_FINISH;
    public static String REFRESH_FOOTER_LOADING;
    public static String REFRESH_FOOTER_NOTHING;
    public static String REFRESH_FOOTER_PULLING;
    public static String REFRESH_FOOTER_REFRESHING;
    public static String REFRESH_FOOTER_RELEASE;
    protected boolean mNoMoreData;
    protected String mTextFailed;
    protected String mTextFinish;
    protected String mTextLoading;
    protected String mTextNothing;
    protected String mTextPulling;
    protected String mTextRefreshing;
    protected String mTextRelease;

    /* renamed from: com.psychiatrygarden.widget.CustomRefreshFooterView$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState;

        static {
            int[] iArr = new int[RefreshState.values().length];
            $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState = iArr;
            try {
                iArr[RefreshState.None.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.PullUpToLoad.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.Loading.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.LoadReleased.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.ReleaseToLoad.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.Refreshing.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public CustomRefreshFooterView(Context context) {
        this(context, null);
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalClassics, com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        super.onFinish(layout, success);
        if (this.mNoMoreData) {
            return 0;
        }
        this.mTitleText.setText(success ? this.mTextFinish : this.mTextFailed);
        return this.mFinishDuration;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.listener.OnStateChangedListener
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        ImageView imageView = this.mArrowView;
        if (this.mNoMoreData) {
            this.mTitleText.setVisibility(0);
            return;
        }
        switch (AnonymousClass1.$SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[newState.ordinal()]) {
            case 1:
                imageView.setVisibility(0);
                break;
            case 2:
                break;
            case 3:
            case 4:
                imageView.setVisibility(8);
                this.mTitleText.setText(this.mTextLoading);
                return;
            case 5:
                this.mTitleText.setText(this.mTextRelease);
                imageView.animate().rotation(0.0f);
                return;
            case 6:
                this.mTitleText.setText(this.mTextRefreshing);
                imageView.setVisibility(8);
                return;
            default:
                return;
        }
        this.mTitleText.setText(this.mTextPulling);
        imageView.animate().rotation(180.0f);
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshFooter
    public boolean setNoMoreData(boolean noMoreData) {
        if (this.mNoMoreData == noMoreData) {
            return true;
        }
        this.mNoMoreData = noMoreData;
        ImageView imageView = this.mArrowView;
        if (noMoreData) {
            this.mTitleText.setText(this.mTextNothing);
            imageView.setVisibility(8);
            return true;
        }
        this.mTitleText.setText(this.mTextPulling);
        imageView.setVisibility(0);
        return true;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalClassics, com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    @Deprecated
    public void setPrimaryColors(@ColorInt int... colors) {
        if (this.mSpinnerStyle == SpinnerStyle.FixedBehind) {
            super.setPrimaryColors(colors);
        }
    }

    public CustomRefreshFooterView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.mNoMoreData = false;
        View.inflate(context, R.layout.srl_classics_footer, this);
        ImageView imageView = (ImageView) findViewById(R.id.srl_classics_arrow);
        this.mArrowView = imageView;
        ImageView imageView2 = (ImageView) findViewById(R.id.srl_classics_progress);
        this.mProgressView = imageView2;
        this.mTitleText = (TextView) findViewById(R.id.srl_classics_title);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, com.scwang.smartrefresh.layout.R.styleable.ClassicsFooter);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) imageView2.getLayoutParams();
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(4, SmartUtil.dp2px(20.0f));
        layoutParams2.rightMargin = dimensionPixelSize;
        layoutParams.rightMargin = dimensionPixelSize;
        layoutParams.width = typedArrayObtainStyledAttributes.getLayoutDimension(3, layoutParams.width);
        layoutParams.height = typedArrayObtainStyledAttributes.getLayoutDimension(3, layoutParams.height);
        layoutParams2.width = typedArrayObtainStyledAttributes.getLayoutDimension(6, layoutParams2.width);
        layoutParams2.height = typedArrayObtainStyledAttributes.getLayoutDimension(6, layoutParams2.height);
        layoutParams.width = typedArrayObtainStyledAttributes.getLayoutDimension(7, layoutParams.width);
        layoutParams.height = typedArrayObtainStyledAttributes.getLayoutDimension(7, layoutParams.height);
        layoutParams2.width = typedArrayObtainStyledAttributes.getLayoutDimension(7, layoutParams2.width);
        layoutParams2.height = typedArrayObtainStyledAttributes.getLayoutDimension(7, layoutParams2.height);
        this.mFinishDuration = typedArrayObtainStyledAttributes.getInt(8, this.mFinishDuration);
        this.mSpinnerStyle = SpinnerStyle.values[typedArrayObtainStyledAttributes.getInt(1, this.mSpinnerStyle.ordinal)];
        if (typedArrayObtainStyledAttributes.hasValue(2)) {
            this.mArrowView.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(2));
        } else if (this.mArrowView.getDrawable() == null) {
            ArrowDrawable arrowDrawable = new ArrowDrawable();
            this.mArrowDrawable = arrowDrawable;
            arrowDrawable.setColor(-10066330);
            this.mArrowView.setImageDrawable(this.mArrowDrawable);
        }
        if (typedArrayObtainStyledAttributes.hasValue(5)) {
            this.mProgressView.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(5));
        } else if (this.mProgressView.getDrawable() == null) {
            ProgressDrawable progressDrawable = new ProgressDrawable();
            this.mProgressDrawable = progressDrawable;
            progressDrawable.setColor(-10066330);
            this.mProgressView.setImageDrawable(this.mProgressDrawable);
        }
        if (typedArrayObtainStyledAttributes.hasValue(9)) {
            super.setPrimaryColor(typedArrayObtainStyledAttributes.getColor(9, 0));
        }
        if (typedArrayObtainStyledAttributes.hasValue(0)) {
            super.setAccentColor(typedArrayObtainStyledAttributes.getColor(0, 0));
        }
        if (typedArrayObtainStyledAttributes.hasValue(14)) {
            this.mTextPulling = typedArrayObtainStyledAttributes.getString(14);
        } else {
            String str = REFRESH_FOOTER_PULLING;
            if (str != null) {
                this.mTextPulling = str;
            } else {
                this.mTextPulling = context.getString(R.string.srl_footer_pulling);
            }
        }
        if (typedArrayObtainStyledAttributes.hasValue(16)) {
            this.mTextRelease = typedArrayObtainStyledAttributes.getString(16);
        } else {
            String str2 = REFRESH_FOOTER_RELEASE;
            if (str2 != null) {
                this.mTextRelease = str2;
            } else {
                this.mTextRelease = context.getString(R.string.srl_footer_release);
            }
        }
        if (typedArrayObtainStyledAttributes.hasValue(12)) {
            this.mTextLoading = typedArrayObtainStyledAttributes.getString(12);
        } else {
            String str3 = REFRESH_FOOTER_LOADING;
            if (str3 != null) {
                this.mTextLoading = str3;
            } else {
                this.mTextLoading = context.getString(R.string.srl_footer_loading);
            }
        }
        if (typedArrayObtainStyledAttributes.hasValue(15)) {
            this.mTextRefreshing = typedArrayObtainStyledAttributes.getString(15);
        } else {
            String str4 = REFRESH_FOOTER_REFRESHING;
            if (str4 != null) {
                this.mTextRefreshing = str4;
            } else {
                this.mTextRefreshing = context.getString(R.string.srl_footer_refreshing);
            }
        }
        if (typedArrayObtainStyledAttributes.hasValue(11)) {
            this.mTextFinish = typedArrayObtainStyledAttributes.getString(11);
        } else {
            String str5 = REFRESH_FOOTER_FINISH;
            if (str5 != null) {
                this.mTextFinish = str5;
            } else {
                this.mTextFinish = context.getString(R.string.srl_footer_finish);
            }
        }
        if (typedArrayObtainStyledAttributes.hasValue(10)) {
            this.mTextFailed = typedArrayObtainStyledAttributes.getString(10);
        } else {
            String str6 = REFRESH_FOOTER_FAILED;
            if (str6 != null) {
                this.mTextFailed = str6;
            } else {
                this.mTextFailed = context.getString(R.string.srl_footer_failed);
            }
        }
        if (typedArrayObtainStyledAttributes.hasValue(13)) {
            this.mTextNothing = typedArrayObtainStyledAttributes.getString(13);
        } else {
            String str7 = REFRESH_FOOTER_NOTHING;
            if (str7 != null) {
                this.mTextNothing = str7;
            } else {
                this.mTextNothing = context.getString(R.string.srl_footer_nothing);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
        imageView2.animate().setInterpolator(null);
        this.mTitleText.setText(isInEditMode() ? this.mTextLoading : this.mTextPulling);
        if (isInEditMode()) {
            imageView.setVisibility(8);
        } else {
            imageView2.setVisibility(8);
        }
    }
}

package com.scwang.smartrefresh.layout.footer;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import com.scwang.smartrefresh.layout.R;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.ArrowDrawable;
import com.scwang.smartrefresh.layout.internal.InternalClassics;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.scwang.smartrefresh.layout.util.SmartUtil;

/* loaded from: classes6.dex */
public class ClassicsFooter extends InternalClassics<ClassicsFooter> implements RefreshFooter {
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

    /* renamed from: com.scwang.smartrefresh.layout.footer.ClassicsFooter$1, reason: invalid class name */
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

    public ClassicsFooter(Context context) {
        this(context, null);
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalClassics, com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean z2) {
        super.onFinish(refreshLayout, z2);
        if (this.mNoMoreData) {
            return 0;
        }
        this.mTitleText.setText(z2 ? this.mTextFinish : this.mTextFailed);
        return this.mFinishDuration;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.listener.OnStateChangedListener
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState refreshState, @NonNull RefreshState refreshState2) {
        ImageView imageView = this.mArrowView;
        if (this.mNoMoreData) {
            return;
        }
        switch (AnonymousClass1.$SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[refreshState2.ordinal()]) {
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
    public boolean setNoMoreData(boolean z2) {
        if (this.mNoMoreData == z2) {
            return true;
        }
        this.mNoMoreData = z2;
        ImageView imageView = this.mArrowView;
        if (z2) {
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
    public void setPrimaryColors(@ColorInt int... iArr) {
        if (this.mSpinnerStyle == SpinnerStyle.FixedBehind) {
            super.setPrimaryColors(iArr);
        }
    }

    public ClassicsFooter(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.mNoMoreData = false;
        View.inflate(context, R.layout.srl_classics_footer, this);
        ImageView imageView = (ImageView) findViewById(R.id.srl_classics_arrow);
        this.mArrowView = imageView;
        ImageView imageView2 = (ImageView) findViewById(R.id.srl_classics_progress);
        this.mProgressView = imageView2;
        this.mTitleText = (TextView) findViewById(R.id.srl_classics_title);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ClassicsFooter);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) imageView2.getLayoutParams();
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ClassicsFooter_srlDrawableMarginRight, SmartUtil.dp2px(20.0f));
        layoutParams2.rightMargin = dimensionPixelSize;
        layoutParams.rightMargin = dimensionPixelSize;
        int i2 = R.styleable.ClassicsFooter_srlDrawableArrowSize;
        layoutParams.width = typedArrayObtainStyledAttributes.getLayoutDimension(i2, layoutParams.width);
        layoutParams.height = typedArrayObtainStyledAttributes.getLayoutDimension(i2, layoutParams.height);
        int i3 = R.styleable.ClassicsFooter_srlDrawableProgressSize;
        layoutParams2.width = typedArrayObtainStyledAttributes.getLayoutDimension(i3, layoutParams2.width);
        layoutParams2.height = typedArrayObtainStyledAttributes.getLayoutDimension(i3, layoutParams2.height);
        int i4 = R.styleable.ClassicsFooter_srlDrawableSize;
        layoutParams.width = typedArrayObtainStyledAttributes.getLayoutDimension(i4, layoutParams.width);
        layoutParams.height = typedArrayObtainStyledAttributes.getLayoutDimension(i4, layoutParams.height);
        layoutParams2.width = typedArrayObtainStyledAttributes.getLayoutDimension(i4, layoutParams2.width);
        layoutParams2.height = typedArrayObtainStyledAttributes.getLayoutDimension(i4, layoutParams2.height);
        this.mFinishDuration = typedArrayObtainStyledAttributes.getInt(R.styleable.ClassicsFooter_srlFinishDuration, this.mFinishDuration);
        this.mSpinnerStyle = SpinnerStyle.values[typedArrayObtainStyledAttributes.getInt(R.styleable.ClassicsFooter_srlClassicsSpinnerStyle, this.mSpinnerStyle.ordinal)];
        int i5 = R.styleable.ClassicsFooter_srlDrawableArrow;
        if (typedArrayObtainStyledAttributes.hasValue(i5)) {
            this.mArrowView.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(i5));
        } else if (this.mArrowView.getDrawable() == null) {
            ArrowDrawable arrowDrawable = new ArrowDrawable();
            this.mArrowDrawable = arrowDrawable;
            arrowDrawable.setColor(-10066330);
            this.mArrowView.setImageDrawable(this.mArrowDrawable);
        }
        int i6 = R.styleable.ClassicsFooter_srlDrawableProgress;
        if (typedArrayObtainStyledAttributes.hasValue(i6)) {
            this.mProgressView.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(i6));
        } else if (this.mProgressView.getDrawable() == null) {
            ProgressDrawable progressDrawable = new ProgressDrawable();
            this.mProgressDrawable = progressDrawable;
            progressDrawable.setColor(-10066330);
            this.mProgressView.setImageDrawable(this.mProgressDrawable);
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.ClassicsFooter_srlTextSizeTitle)) {
            this.mTitleText.setTextSize(0, typedArrayObtainStyledAttributes.getDimensionPixelSize(r3, SmartUtil.dp2px(16.0f)));
        }
        int i7 = R.styleable.ClassicsFooter_srlPrimaryColor;
        if (typedArrayObtainStyledAttributes.hasValue(i7)) {
            super.setPrimaryColor(typedArrayObtainStyledAttributes.getColor(i7, 0));
        }
        int i8 = R.styleable.ClassicsFooter_srlAccentColor;
        if (typedArrayObtainStyledAttributes.hasValue(i8)) {
            super.setAccentColor(typedArrayObtainStyledAttributes.getColor(i8, 0));
        }
        int i9 = R.styleable.ClassicsFooter_srlTextPulling;
        if (typedArrayObtainStyledAttributes.hasValue(i9)) {
            this.mTextPulling = typedArrayObtainStyledAttributes.getString(i9);
        } else {
            String str = REFRESH_FOOTER_PULLING;
            if (str != null) {
                this.mTextPulling = str;
            } else {
                this.mTextPulling = context.getString(R.string.srl_footer_pulling);
            }
        }
        int i10 = R.styleable.ClassicsFooter_srlTextRelease;
        if (typedArrayObtainStyledAttributes.hasValue(i10)) {
            this.mTextRelease = typedArrayObtainStyledAttributes.getString(i10);
        } else {
            String str2 = REFRESH_FOOTER_RELEASE;
            if (str2 != null) {
                this.mTextRelease = str2;
            } else {
                this.mTextRelease = context.getString(R.string.srl_footer_release);
            }
        }
        int i11 = R.styleable.ClassicsFooter_srlTextLoading;
        if (typedArrayObtainStyledAttributes.hasValue(i11)) {
            this.mTextLoading = typedArrayObtainStyledAttributes.getString(i11);
        } else {
            String str3 = REFRESH_FOOTER_LOADING;
            if (str3 != null) {
                this.mTextLoading = str3;
            } else {
                this.mTextLoading = context.getString(R.string.srl_footer_loading);
            }
        }
        int i12 = R.styleable.ClassicsFooter_srlTextRefreshing;
        if (typedArrayObtainStyledAttributes.hasValue(i12)) {
            this.mTextRefreshing = typedArrayObtainStyledAttributes.getString(i12);
        } else {
            String str4 = REFRESH_FOOTER_REFRESHING;
            if (str4 != null) {
                this.mTextRefreshing = str4;
            } else {
                this.mTextRefreshing = context.getString(R.string.srl_footer_refreshing);
            }
        }
        int i13 = R.styleable.ClassicsFooter_srlTextFinish;
        if (typedArrayObtainStyledAttributes.hasValue(i13)) {
            this.mTextFinish = typedArrayObtainStyledAttributes.getString(i13);
        } else {
            String str5 = REFRESH_FOOTER_FINISH;
            if (str5 != null) {
                this.mTextFinish = str5;
            } else {
                this.mTextFinish = context.getString(R.string.srl_footer_finish);
            }
        }
        int i14 = R.styleable.ClassicsFooter_srlTextFailed;
        if (typedArrayObtainStyledAttributes.hasValue(i14)) {
            this.mTextFailed = typedArrayObtainStyledAttributes.getString(i14);
        } else {
            String str6 = REFRESH_FOOTER_FAILED;
            if (str6 != null) {
                this.mTextFailed = str6;
            } else {
                this.mTextFailed = context.getString(R.string.srl_footer_failed);
            }
        }
        int i15 = R.styleable.ClassicsFooter_srlTextNothing;
        if (typedArrayObtainStyledAttributes.hasValue(i15)) {
            this.mTextNothing = typedArrayObtainStyledAttributes.getString(i15);
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

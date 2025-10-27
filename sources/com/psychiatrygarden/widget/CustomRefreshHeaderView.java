package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.psychiatrygarden.utils.SkinManager;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.internal.ArrowDrawable;
import com.scwang.smartrefresh.layout.internal.InternalClassics;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.scwang.smartrefresh.layout.util.SmartUtil;
import com.yikaobang.yixue.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes6.dex */
public class CustomRefreshHeaderView extends InternalClassics<ClassicsHeader> implements RefreshHeader {
    public static final int ID_TEXT_UPDATE = 2131367031;
    public static String REFRESH_HEADER_FAILED;
    public static String REFRESH_HEADER_FINISH;
    public static String REFRESH_HEADER_LOADING;
    public static String REFRESH_HEADER_PULLING;
    public static String REFRESH_HEADER_REFRESHING;
    public static String REFRESH_HEADER_RELEASE;
    public static String REFRESH_HEADER_SECONDARY;
    public static String REFRESH_HEADER_UPDATE;
    protected String KEY_LAST_UPDATE_TIME;
    protected boolean mEnableLastTime;
    protected Date mLastTime;
    protected DateFormat mLastUpdateFormat;
    protected TextView mLastUpdateText;
    protected SharedPreferences mShared;
    protected String mTextFailed;
    protected String mTextFinish;
    protected String mTextLoading;
    protected String mTextPulling;
    protected String mTextRefreshing;
    protected String mTextRelease;
    protected String mTextSecondary;
    protected String mTextUpdate;

    /* renamed from: com.psychiatrygarden.widget.CustomRefreshHeaderView$1, reason: invalid class name */
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
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.PullDownToRefresh.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.Refreshing.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.RefreshReleased.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.ReleaseToRefresh.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.ReleaseToTwoLevel.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.Loading.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public CustomRefreshHeaderView(Context context) {
        this(context, null);
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalClassics, com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        if (success) {
            this.mTitleText.setText(this.mTextFinish);
            if (this.mLastTime != null) {
                setLastUpdateTime(new Date());
            }
        } else {
            this.mTitleText.setText(this.mTextFailed);
        }
        return super.onFinish(layout, success);
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.listener.OnStateChangedListener
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        ImageView imageView = this.mArrowView;
        TextView textView = this.mLastUpdateText;
        switch (AnonymousClass1.$SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[newState.ordinal()]) {
            case 1:
                textView.setVisibility(this.mEnableLastTime ? 0 : 8);
                break;
            case 2:
                break;
            case 3:
            case 4:
                this.mTitleText.setText(this.mTextRefreshing);
                imageView.setVisibility(8);
                return;
            case 5:
                this.mTitleText.setText(this.mTextRelease);
                imageView.animate().rotation(180.0f);
                return;
            case 6:
                this.mTitleText.setText(this.mTextSecondary);
                imageView.animate().rotation(0.0f);
                return;
            case 7:
                imageView.setVisibility(8);
                textView.setVisibility(this.mEnableLastTime ? 4 : 8);
                this.mTitleText.setText(this.mTextLoading);
                return;
            default:
                return;
        }
        this.mTitleText.setText(this.mTextPulling);
        imageView.setVisibility(0);
        imageView.animate().rotation(0.0f);
    }

    public CustomRefreshHeaderView setEnableLastTime(boolean enable) {
        TextView textView = this.mLastUpdateText;
        this.mEnableLastTime = enable;
        textView.setVisibility(enable ? 0 : 8);
        RefreshKernel refreshKernel = this.mRefreshKernel;
        if (refreshKernel != null) {
            refreshKernel.requestRemeasureHeightFor(this);
        }
        return this;
    }

    public CustomRefreshHeaderView setLastUpdateText(CharSequence text) {
        this.mLastTime = null;
        this.mLastUpdateText.setText(text);
        return this;
    }

    public CustomRefreshHeaderView setLastUpdateTime(Date time) {
        this.mLastTime = time;
        this.mLastUpdateText.setText(this.mLastUpdateFormat.format(time));
        if (this.mShared != null && !isInEditMode()) {
            this.mShared.edit().putLong(this.KEY_LAST_UPDATE_TIME, time.getTime()).apply();
        }
        return this;
    }

    public CustomRefreshHeaderView setTextSizeTime(float size) {
        this.mLastUpdateText.setTextSize(size);
        RefreshKernel refreshKernel = this.mRefreshKernel;
        if (refreshKernel != null) {
            refreshKernel.requestRemeasureHeightFor(this);
        }
        return this;
    }

    public CustomRefreshHeaderView setTextTimeMarginTop(float dp) {
        TextView textView = this.mLastUpdateText;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();
        marginLayoutParams.topMargin = SmartUtil.dp2px(dp);
        textView.setLayoutParams(marginLayoutParams);
        return this;
    }

    public CustomRefreshHeaderView setTimeFormat(DateFormat format) {
        this.mLastUpdateFormat = format;
        Date date = this.mLastTime;
        if (date != null) {
            this.mLastUpdateText.setText(format.format(date));
        }
        return this;
    }

    public CustomRefreshHeaderView(Context context, AttributeSet attrs) {
        FragmentManager supportFragmentManager;
        super(context, attrs, 0);
        this.KEY_LAST_UPDATE_TIME = "LAST_UPDATE_TIME";
        this.mEnableLastTime = true;
        View.inflate(context, R.layout.srl_classics_header, this);
        ImageView imageView = (ImageView) findViewById(R.id.srl_classics_arrow);
        this.mArrowView = imageView;
        TextView textView = (TextView) findViewById(R.id.srl_classics_update);
        this.mLastUpdateText = textView;
        ImageView imageView2 = (ImageView) findViewById(R.id.srl_classics_progress);
        this.mProgressView = imageView2;
        TextView textView2 = (TextView) findViewById(R.id.srl_classics_title);
        this.mTitleText = textView2;
        textView2.setTextColor(SkinManager.getCurrentSkinType(getContext()) == 0 ? -4012341 : -12235676);
        this.mLastUpdateText.setTextColor(SkinManager.getCurrentSkinType(getContext()) == 0 ? -4012341 : -12235676);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ClassicsHeader);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) imageView2.getLayoutParams();
        new LinearLayout.LayoutParams(-2, -2).topMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(20, SmartUtil.dp2px(0.0f));
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
        this.mFinishDuration = typedArrayObtainStyledAttributes.getInt(9, this.mFinishDuration);
        this.mEnableLastTime = typedArrayObtainStyledAttributes.getBoolean(8, this.mEnableLastTime);
        this.mSpinnerStyle = SpinnerStyle.values[typedArrayObtainStyledAttributes.getInt(1, this.mSpinnerStyle.ordinal)];
        if (typedArrayObtainStyledAttributes.hasValue(2)) {
            this.mArrowView.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(2));
        } else if (this.mArrowView.getDrawable() == null) {
            ArrowDrawable arrowDrawable = new ArrowDrawable();
            this.mArrowDrawable = arrowDrawable;
            arrowDrawable.setColor(SkinManager.getCurrentSkinType(getContext()) == 0 ? -4012341 : -12235676);
            this.mArrowView.setImageDrawable(this.mArrowDrawable);
        }
        if (typedArrayObtainStyledAttributes.hasValue(5)) {
            this.mProgressView.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(5));
        } else if (this.mProgressView.getDrawable() == null) {
            ProgressDrawable progressDrawable = new ProgressDrawable();
            this.mProgressDrawable = progressDrawable;
            progressDrawable.setColor(SkinManager.getCurrentSkinType(getContext()) != 0 ? -12235676 : -4012341);
            this.mProgressView.setImageDrawable(this.mProgressDrawable);
        }
        if (typedArrayObtainStyledAttributes.hasValue(19)) {
            this.mTitleText.setTextSize(0, typedArrayObtainStyledAttributes.getDimensionPixelSize(19, SmartUtil.dp2px(16.0f)));
        }
        if (typedArrayObtainStyledAttributes.hasValue(18)) {
            this.mLastUpdateText.setTextSize(0, typedArrayObtainStyledAttributes.getDimensionPixelSize(18, SmartUtil.dp2px(12.0f)));
        }
        if (typedArrayObtainStyledAttributes.hasValue(10)) {
            super.setPrimaryColor(typedArrayObtainStyledAttributes.getColor(10, 0));
        }
        if (typedArrayObtainStyledAttributes.hasValue(0)) {
            setAccentColor(typedArrayObtainStyledAttributes.getColor(0, 0));
        }
        if (typedArrayObtainStyledAttributes.hasValue(14)) {
            this.mTextPulling = typedArrayObtainStyledAttributes.getString(14);
        } else {
            String str = REFRESH_HEADER_PULLING;
            if (str != null) {
                this.mTextPulling = str;
            } else {
                this.mTextPulling = context.getString(R.string.srl_header_pulling);
            }
        }
        if (typedArrayObtainStyledAttributes.hasValue(13)) {
            this.mTextLoading = typedArrayObtainStyledAttributes.getString(13);
        } else {
            String str2 = REFRESH_HEADER_LOADING;
            if (str2 != null) {
                this.mTextLoading = str2;
            } else {
                this.mTextLoading = context.getString(R.string.srl_header_loading);
            }
        }
        if (typedArrayObtainStyledAttributes.hasValue(16)) {
            this.mTextRelease = typedArrayObtainStyledAttributes.getString(16);
        } else {
            String str3 = REFRESH_HEADER_RELEASE;
            if (str3 != null) {
                this.mTextRelease = str3;
            } else {
                this.mTextRelease = context.getString(R.string.srl_header_release);
            }
        }
        if (typedArrayObtainStyledAttributes.hasValue(12)) {
            this.mTextFinish = typedArrayObtainStyledAttributes.getString(12);
        } else {
            String str4 = REFRESH_HEADER_FINISH;
            if (str4 != null) {
                this.mTextFinish = str4;
            } else {
                this.mTextFinish = context.getString(R.string.srl_header_finish);
            }
        }
        if (typedArrayObtainStyledAttributes.hasValue(11)) {
            this.mTextFailed = typedArrayObtainStyledAttributes.getString(11);
        } else {
            String str5 = REFRESH_HEADER_FAILED;
            if (str5 != null) {
                this.mTextFailed = str5;
            } else {
                this.mTextFailed = context.getString(R.string.srl_header_failed);
            }
        }
        if (typedArrayObtainStyledAttributes.hasValue(17)) {
            this.mTextSecondary = typedArrayObtainStyledAttributes.getString(17);
        } else {
            String str6 = REFRESH_HEADER_SECONDARY;
            if (str6 != null) {
                this.mTextSecondary = str6;
            } else {
                this.mTextSecondary = context.getString(R.string.srl_header_secondary);
            }
        }
        if (typedArrayObtainStyledAttributes.hasValue(15)) {
            this.mTextRefreshing = typedArrayObtainStyledAttributes.getString(15);
        } else {
            String str7 = REFRESH_HEADER_REFRESHING;
            if (str7 != null) {
                this.mTextRefreshing = str7;
            } else {
                this.mTextRefreshing = context.getString(R.string.srl_header_refreshings);
            }
        }
        if (typedArrayObtainStyledAttributes.hasValue(21)) {
            this.mTextUpdate = typedArrayObtainStyledAttributes.getString(21);
        } else {
            String str8 = REFRESH_HEADER_UPDATE;
            if (str8 != null) {
                this.mTextUpdate = str8;
            } else {
                this.mTextUpdate = context.getString(R.string.srl_header_updates);
            }
        }
        this.mLastUpdateFormat = new SimpleDateFormat(this.mTextUpdate, Locale.getDefault());
        typedArrayObtainStyledAttributes.recycle();
        imageView2.animate().setInterpolator(null);
        textView.setVisibility(this.mEnableLastTime ? 0 : 8);
        this.mTitleText.setText(isInEditMode() ? this.mTextRefreshing : this.mTextPulling);
        if (isInEditMode()) {
            imageView.setVisibility(8);
        } else {
            imageView2.setVisibility(8);
        }
        try {
            if ((context instanceof FragmentActivity) && (supportFragmentManager = ((FragmentActivity) context).getSupportFragmentManager()) != null && supportFragmentManager.getFragments().size() > 0) {
                setLastUpdateTime(new Date());
                return;
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        this.KEY_LAST_UPDATE_TIME += context.getClass().getName();
        this.mShared = context.getSharedPreferences("ClassicsHeader", 0);
        setLastUpdateTime(new Date(this.mShared.getLong(this.KEY_LAST_UPDATE_TIME, System.currentTimeMillis())));
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalClassics
    public ClassicsHeader setAccentColor(@ColorInt int accentColor) {
        this.mLastUpdateText.setTextColor((16777215 & accentColor) | (-872415232));
        return (ClassicsHeader) super.setAccentColor(accentColor);
    }
}

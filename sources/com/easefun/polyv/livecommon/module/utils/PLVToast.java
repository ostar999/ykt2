package com.easefun.polyv.livecommon.module.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.PLVUIUtil;
import com.plv.foundationsdk.utils.PLVAppUtils;
import com.plv.foundationsdk.utils.PLVSugarUtil;

/* loaded from: classes3.dex */
public class PLVToast {
    private static Toast lastShowToast;
    private ToastParam param;
    private Toast toast;

    public static class ToastParam {

        @ColorInt
        private int backgroundColor;
        private Context context;

        @DrawableRes
        private int drawableResId;
        private int showDuration;
        private CharSequence text;

        @ColorInt
        private int textColor;

        private ToastParam() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initToast() {
        Toast toast = new Toast(this.param.context);
        this.toast = toast;
        toast.setGravity(17, 0, 0);
        this.toast.setDuration(this.param.showDuration);
        final AppCompatTextView appCompatTextView = new AppCompatTextView(this.param.context);
        appCompatTextView.setMinWidth(-2);
        appCompatTextView.setMaxWidth(PLVUIUtil.dip2px(this.param.context, 228.0d));
        appCompatTextView.setHeight(-2);
        appCompatTextView.setMaxLines(4);
        appCompatTextView.setEllipsize(TextUtils.TruncateAt.END);
        appCompatTextView.setText(this.param.text);
        appCompatTextView.setTextColor(this.param.textColor);
        int iDip2px = PLVUIUtil.dip2px(this.param.context, 16.0d);
        int iDip2px2 = PLVUIUtil.dip2px(this.param.context, 10.0d);
        if (this.param.drawableResId == 0) {
            appCompatTextView.setPadding(iDip2px, iDip2px2, iDip2px, iDip2px2);
            appCompatTextView.post(new Runnable() { // from class: com.easefun.polyv.livecommon.module.utils.PLVToast.3
                @Override // java.lang.Runnable
                public void run() {
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(PLVToast.this.param.backgroundColor);
                    if (appCompatTextView.getLayout() == null || appCompatTextView.getLayout().getLineCount() > 1) {
                        gradientDrawable.setCornerRadius(PLVUIUtil.dip2px(PLVToast.this.param.context, 8.0d));
                    } else {
                        gradientDrawable.setCornerRadius(PLVUIUtil.dip2px(PLVToast.this.param.context, 20.0d));
                    }
                    appCompatTextView.setBackground(gradientDrawable);
                }
            });
            this.toast.setView(appCompatTextView);
            return;
        }
        appCompatTextView.setMaxWidth(PLVUIUtil.dip2px(this.param.context, 228.0d));
        appCompatTextView.setPadding(PLVUIUtil.dip2px(this.param.context, 8.0d), 0, 0, 0);
        AppCompatImageView appCompatImageView = new AppCompatImageView(this.param.context);
        appCompatImageView.setImageResource(this.param.drawableResId);
        final LinearLayout linearLayout = new LinearLayout(this.param.context);
        linearLayout.setOrientation(0);
        linearLayout.setGravity(17);
        linearLayout.setPadding(iDip2px, iDip2px2, iDip2px, iDip2px2);
        linearLayout.addView(appCompatImageView);
        linearLayout.addView(appCompatTextView);
        linearLayout.post(new Runnable() { // from class: com.easefun.polyv.livecommon.module.utils.PLVToast.2
            @Override // java.lang.Runnable
            public void run() {
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(PLVToast.this.param.backgroundColor);
                if (appCompatTextView.getLayout() == null || appCompatTextView.getLayout().getLineCount() > 1) {
                    gradientDrawable.setCornerRadius(PLVUIUtil.dip2px(PLVToast.this.param.context, 8.0d));
                } else {
                    gradientDrawable.setCornerRadius(PLVUIUtil.dip2px(PLVToast.this.param.context, 20.0d));
                }
                linearLayout.setBackground(gradientDrawable);
            }
        });
        this.toast.setView(linearLayout);
    }

    public void cancel() {
        PLVAppUtils.postToMainThread(new Runnable() { // from class: com.easefun.polyv.livecommon.module.utils.PLVToast.5
            @Override // java.lang.Runnable
            public void run() {
                if (PLVToast.this.toast != null) {
                    PLVToast.this.toast.cancel();
                    PLVToast.this.toast = null;
                }
            }
        });
    }

    public void show() {
        PLVAppUtils.postToMainThread(new Runnable() { // from class: com.easefun.polyv.livecommon.module.utils.PLVToast.4
            @Override // java.lang.Runnable
            public void run() {
                if (PLVToast.lastShowToast != null) {
                    PLVToast.lastShowToast.cancel();
                }
                if (PLVToast.this.toast != null) {
                    PLVToast.this.toast.show();
                }
                Toast unused = PLVToast.lastShowToast = PLVToast.this.toast;
            }
        });
    }

    public static class Builder {
        private static final int DEFAULT_SHOW_DURATION = 0;
        private ToastParam param;
        private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#F0F1F5");
        private static final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#991B202D");

        private Builder() {
            ToastParam toastParam = new ToastParam();
            this.param = toastParam;
            toastParam.textColor = DEFAULT_TEXT_COLOR;
            this.param.backgroundColor = DEFAULT_BACKGROUND_COLOR;
            this.param.showDuration = 0;
        }

        public static Builder context(@NonNull Context context) {
            Builder builder = new Builder();
            builder.param.context = context.getApplicationContext();
            return builder;
        }

        public static Builder create() {
            return context((Context) PLVSugarUtil.requireNotNull(PLVAppUtils.getApp()));
        }

        public PLVToast build() {
            return new PLVToast(this.param);
        }

        @Deprecated
        public Builder duration(int toastDuration) {
            if (toastDuration != 0 && toastDuration != 1) {
                toastDuration = 0;
            }
            this.param.showDuration = toastDuration;
            return this;
        }

        public Builder longDuration() {
            this.param.showDuration = 1;
            return this;
        }

        public Builder setBackgroundColor(@ColorInt int backgroundColor) {
            this.param.backgroundColor = backgroundColor;
            return this;
        }

        public Builder setDrawable(@DrawableRes int drawableResId) {
            this.param.drawableResId = drawableResId;
            return this;
        }

        public Builder setText(CharSequence text) {
            if (text == null) {
                this.param.text = "";
            } else {
                this.param.text = text;
            }
            return this;
        }

        public Builder setTextColor(@ColorInt int textColor) {
            this.param.textColor = textColor;
            return this;
        }

        public Builder shortDuration() {
            this.param.showDuration = 0;
            return this;
        }

        public void show() {
            build().show();
        }

        public Builder setText(@StringRes int stringResId) {
            ToastParam toastParam = this.param;
            toastParam.text = toastParam.context.getString(stringResId);
            return this;
        }
    }

    private PLVToast(ToastParam param) {
        this.param = param;
        PLVAppUtils.postToMainThread(new Runnable() { // from class: com.easefun.polyv.livecommon.module.utils.PLVToast.1
            @Override // java.lang.Runnable
            public void run() {
                PLVToast.this.initToast();
            }
        });
    }
}

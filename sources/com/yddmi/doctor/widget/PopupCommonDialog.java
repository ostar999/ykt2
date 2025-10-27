package com.yddmi.doctor.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.lxj.xpopup.core.CenterPopupView;
import com.yddmi.doctor.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001:\u0001\u001dB\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u000bH\u0014J\b\u0010\u0012\u001a\u00020\u0010H\u0014J.\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\b2\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\bJ\u000e\u0010\u0018\u001a\u00020\u00002\u0006\u0010\u0019\u001a\u00020\u0006J\u001c\u0010\u001a\u001a\u00020\u00002\b\b\u0001\u0010\u001b\u001a\u00020\u000b2\b\b\u0001\u0010\u001c\u001a\u00020\u000bH\u0007R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\n\u001a\u00020\u000b8\u0002@\u0002X\u0083\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\f\u001a\u00020\u000b8\u0002@\u0002X\u0083\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/yddmi/doctor/widget/PopupCommonDialog;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "mListener", "Lcom/yddmi/doctor/widget/PopupCommonDialog$OnPopupCommonDialogClickListener;", "mOneStr", "", "mTipStr", "mTitleColorRes", "", "mTitleSize", "mTitleStr", "mTwoStr", "dealInit", "", "getImplLayoutId", "onCreate", "setCommonDialog", "tipStr", "oneStr", "twoStr", "titleStr", "setOnPopupCommonClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "setTitelColorSize", "titleColorRes", "titleSize", "OnPopupCommonDialogClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupCommonDialog extends CenterPopupView {

    @Nullable
    private OnPopupCommonDialogClickListener mListener;

    @NotNull
    private String mOneStr;

    @NotNull
    private String mTipStr;

    @ColorInt
    private int mTitleColorRes;

    @DimenRes
    private int mTitleSize;

    @Nullable
    private String mTitleStr;

    @Nullable
    private String mTwoStr;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/widget/PopupCommonDialog$OnPopupCommonDialogClickListener;", "", "onClick", "", "view", "Landroid/view/View;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupCommonDialogClickListener {
        void onClick(@Nullable View view);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupCommonDialog(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mTitleStr = "";
        this.mTipStr = "";
        this.mOneStr = "";
        this.mTwoStr = "";
        this.mTitleColorRes = -1;
        this.mTitleSize = -1;
    }

    private final void dealInit() {
        int i2 = R.id.oneTv;
        TextView textView = (TextView) findViewById(i2);
        if (textView != null) {
            ViewExtKt.setDebounceClickListener$default(textView, new View.OnClickListener() { // from class: com.yddmi.doctor.widget.b
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PopupCommonDialog.dealInit$lambda$0(this.f26062c, view);
                }
            }, 0L, 2, null);
        }
        int i3 = R.id.twoTv;
        TextView textView2 = (TextView) findViewById(i3);
        if (textView2 != null) {
            ViewExtKt.setDebounceClickListener$default(textView2, new View.OnClickListener() { // from class: com.yddmi.doctor.widget.c
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PopupCommonDialog.dealInit$lambda$1(this.f26063c, view);
                }
            }, 0L, 2, null);
        }
        int i4 = R.id.singleTv;
        TextView textView3 = (TextView) findViewById(i4);
        if (textView3 != null) {
            ViewExtKt.setDebounceClickListener$default(textView3, new View.OnClickListener() { // from class: com.yddmi.doctor.widget.d
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PopupCommonDialog.dealInit$lambda$2(this.f26064c, view);
                }
            }, 0L, 2, null);
        }
        TextView textView4 = (TextView) findViewById(R.id.detailTv);
        if (textView4 != null) {
            textView4.setText(this.mTipStr);
        }
        String str = this.mTwoStr;
        boolean z2 = true;
        if (str == null || str.length() == 0) {
            Group group = (Group) findViewById(R.id.twoCvGrop);
            if (group != null) {
                group.setVisibility(4);
            }
            TextView textView5 = (TextView) findViewById(i4);
            if (textView5 != null) {
                textView5.setVisibility(0);
            }
            TextView textView6 = (TextView) findViewById(i4);
            if (textView6 != null) {
                textView6.setText(this.mOneStr);
            }
        } else {
            Group group2 = (Group) findViewById(R.id.twoCvGrop);
            if (group2 != null) {
                group2.setVisibility(0);
            }
            TextView textView7 = (TextView) findViewById(i4);
            if (textView7 != null) {
                textView7.setVisibility(4);
            }
            TextView textView8 = (TextView) findViewById(i2);
            if (textView8 != null) {
                textView8.setText(this.mOneStr);
            }
            TextView textView9 = (TextView) findViewById(i3);
            if (textView9 != null) {
                textView9.setText(this.mTwoStr);
            }
        }
        String str2 = this.mTitleStr;
        if (str2 != null && str2.length() != 0) {
            z2 = false;
        }
        if (z2) {
            TextView textView10 = (TextView) findViewById(R.id.titleTv);
            if (textView10 == null) {
                return;
            }
            textView10.setVisibility(8);
            return;
        }
        TextView textView11 = (TextView) findViewById(R.id.titleTv);
        if (textView11 != null) {
            textView11.setVisibility(0);
            int i5 = this.mTitleColorRes;
            if (-1 != i5) {
                textView11.setTextColor(i5);
            }
            if (-1 != this.mTitleSize) {
                textView11.setTextSize(getContext().getResources().getDimension(this.mTitleSize));
            }
            textView11.setText(this.mTitleStr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dealInit$lambda$0(PopupCommonDialog this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupCommonDialogClickListener onPopupCommonDialogClickListener = this$0.mListener;
        if (onPopupCommonDialogClickListener != null) {
            onPopupCommonDialogClickListener.onClick(view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dealInit$lambda$1(PopupCommonDialog this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupCommonDialogClickListener onPopupCommonDialogClickListener = this$0.mListener;
        if (onPopupCommonDialogClickListener != null) {
            onPopupCommonDialogClickListener.onClick(view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dealInit$lambda$2(PopupCommonDialog this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupCommonDialogClickListener onPopupCommonDialogClickListener = this$0.mListener;
        if (onPopupCommonDialogClickListener != null) {
            onPopupCommonDialogClickListener.onClick(view);
        }
    }

    public static /* synthetic */ PopupCommonDialog setCommonDialog$default(PopupCommonDialog popupCommonDialog, String str, String str2, String str3, String str4, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            str3 = "";
        }
        if ((i2 & 8) != 0) {
            str4 = "";
        }
        return popupCommonDialog.setCommonDialog(str, str2, str3, str4);
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.popup_common_dialog;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        dealInit();
    }

    @NotNull
    public final PopupCommonDialog setCommonDialog(@NotNull String tipStr, @NotNull String oneStr, @Nullable String twoStr, @Nullable String titleStr) {
        Intrinsics.checkNotNullParameter(tipStr, "tipStr");
        Intrinsics.checkNotNullParameter(oneStr, "oneStr");
        this.mTipStr = tipStr;
        this.mOneStr = oneStr;
        this.mTwoStr = twoStr;
        this.mTitleStr = titleStr;
        return this;
    }

    @NotNull
    public final PopupCommonDialog setOnPopupCommonClickListener(@NotNull OnPopupCommonDialogClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
        return this;
    }

    @SuppressLint({"ResourceAsColor"})
    @NotNull
    public final PopupCommonDialog setTitelColorSize(@ColorRes int titleColorRes, @DimenRes int titleSize) {
        this.mTitleColorRes = titleColorRes;
        this.mTitleSize = titleSize;
        return this;
    }
}

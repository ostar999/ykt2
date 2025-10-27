package com.yddmi.doctor.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.lxj.xpopup.core.CenterPopupView;
import com.yddmi.doctor.R;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0010\u0018\u00002\u00020\u0001:\u0001-B\u0019\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u001d\u001a\u00020\u001eH\u0002J\b\u0010\u001f\u001a\u00020\u000fH\u0014J\b\u0010 \u001a\u00020\u001eH\u0014J\u000e\u0010!\u001a\u00020\u00002\u0006\u0010\"\u001a\u00020\nJ\u0016\u0010#\u001a\u00020\u00002\u0006\u0010$\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020\u000fJ<\u0010&\u001a\u00020\u00002\u0006\u0010'\u001a\u00020\r2\n\b\u0002\u0010(\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010)\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010*\u001a\u0004\u0018\u00010\r2\b\b\u0002\u0010+\u001a\u00020\u000fJ\u0016\u0010,\u001a\u00020\u00002\u0006\u0010$\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020\u000fR\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lcom/yddmi/doctor/widget/PopupTipDialog;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "oneBtn", "", "(Landroid/content/Context;Z)V", "detailTv", "Landroid/widget/TextView;", "mListener", "Lcom/yddmi/doctor/widget/PopupTipDialog$OnPopupTipDialogClickListener;", "mOneBtn", "mOneOkStr", "", "mTipColorRes", "", "mTipGravity", "mTipSize", "mTipStr", "mTitleColorRes", "mTitleSize", "mTitleStr", "mTwoStr", "okTv", "oneTv", "titleTv", "twoCl", "Landroidx/constraintlayout/widget/ConstraintLayout;", "twoTv", "dealInit", "", "getImplLayoutId", "onCreate", "setOnPopupTipDialogClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "setTipColorSize", "titleColorRes", "titleSize", "setTipDialog", "tipStr", "oneOkStr", "twoStr", "titleStr", "tipGravity", "setTitelColorSize", "OnPopupTipDialogClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupTipDialog extends CenterPopupView {
    private TextView detailTv;

    @Nullable
    private OnPopupTipDialogClickListener mListener;
    private boolean mOneBtn;

    @Nullable
    private String mOneOkStr;
    private int mTipColorRes;
    private int mTipGravity;
    private int mTipSize;

    @NotNull
    private String mTipStr;
    private int mTitleColorRes;
    private int mTitleSize;

    @Nullable
    private String mTitleStr;

    @Nullable
    private String mTwoStr;
    private TextView okTv;
    private TextView oneTv;
    private TextView titleTv;
    private ConstraintLayout twoCl;
    private TextView twoTv;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/widget/PopupTipDialog$OnPopupTipDialogClickListener;", "", "onClick", "", "view", "Landroid/view/View;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupTipDialogClickListener {
        void onClick(@NotNull View view);
    }

    public /* synthetic */ PopupTipDialog(Context context, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? true : z2);
    }

    private final void dealInit() {
        TextView textView;
        TextView textView2;
        TextView textView3;
        View viewFindViewById = findViewById(R.id.titleTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.titleTv)");
        this.titleTv = (TextView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.detailTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.detailTv)");
        this.detailTv = (TextView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.okTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.okTv)");
        this.okTv = (TextView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.twoCl);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.twoCl)");
        this.twoCl = (ConstraintLayout) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.oneTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.oneTv)");
        this.oneTv = (TextView) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.twoTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.twoTv)");
        this.twoTv = (TextView) viewFindViewById6;
        if (this.mOneBtn) {
            ConstraintLayout constraintLayout = this.twoCl;
            if (constraintLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("twoCl");
                constraintLayout = null;
            }
            constraintLayout.setVisibility(8);
            TextView textView4 = this.okTv;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("okTv");
                textView4 = null;
            }
            textView4.setVisibility(0);
            TextView textView5 = this.okTv;
            if (textView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("okTv");
                textView5 = null;
            }
            String str = this.mOneOkStr;
            textView5.setText(str == null || str.length() == 0 ? getContext().getString(R.string.common_ok) : this.mOneOkStr);
        } else {
            ConstraintLayout constraintLayout2 = this.twoCl;
            if (constraintLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("twoCl");
                constraintLayout2 = null;
            }
            constraintLayout2.setVisibility(0);
            TextView textView6 = this.okTv;
            if (textView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("okTv");
                textView6 = null;
            }
            textView6.setVisibility(8);
            TextView textView7 = this.oneTv;
            if (textView7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("oneTv");
                textView7 = null;
            }
            textView7.setText(this.mOneOkStr);
            TextView textView8 = this.twoTv;
            if (textView8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("twoTv");
                textView8 = null;
            }
            textView8.setText(this.mTwoStr);
        }
        TextView textView9 = this.titleTv;
        if (textView9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("titleTv");
            textView9 = null;
        }
        String str2 = this.mTitleStr;
        textView9.setText(str2 == null || str2.length() == 0 ? textView9.getContext().getString(R.string.common_tip) : this.mTitleStr);
        int i2 = this.mTitleColorRes;
        if (-1 != i2) {
            textView9.setTextColor(i2);
        }
        int i3 = this.mTitleSize;
        if (-1 != i3) {
            textView9.setTextSize(1, i3);
        }
        TextView textView10 = this.detailTv;
        if (textView10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("detailTv");
            textView10 = null;
        }
        textView10.setText(this.mTipStr);
        int i4 = this.mTipColorRes;
        if (-1 != i4) {
            textView10.setTextColor(i4);
        }
        int i5 = this.mTipSize;
        if (-1 != i5) {
            textView10.setTextSize(1, i5);
        }
        textView10.setGravity(this.mTipGravity);
        TextView textView11 = this.okTv;
        if (textView11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("okTv");
            textView = null;
        } else {
            textView = textView11;
        }
        ViewExtKt.setDebounceClickListener$default(textView, new View.OnClickListener() { // from class: com.yddmi.doctor.widget.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupTipDialog.dealInit$lambda$2(this.f26067c, view);
            }
        }, 0L, 2, null);
        TextView textView12 = this.oneTv;
        if (textView12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("oneTv");
            textView2 = null;
        } else {
            textView2 = textView12;
        }
        ViewExtKt.setDebounceClickListener$default(textView2, new View.OnClickListener() { // from class: com.yddmi.doctor.widget.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupTipDialog.dealInit$lambda$3(this.f26068c, view);
            }
        }, 0L, 2, null);
        TextView textView13 = this.twoTv;
        if (textView13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("twoTv");
            textView3 = null;
        } else {
            textView3 = textView13;
        }
        ViewExtKt.setDebounceClickListener$default(textView3, new View.OnClickListener() { // from class: com.yddmi.doctor.widget.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupTipDialog.dealInit$lambda$4(this.f26069c, view);
            }
        }, 0L, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dealInit$lambda$2(PopupTipDialog this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupTipDialogClickListener onPopupTipDialogClickListener = this$0.mListener;
        if (onPopupTipDialogClickListener != null) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            onPopupTipDialogClickListener.onClick(it);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dealInit$lambda$3(PopupTipDialog this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupTipDialogClickListener onPopupTipDialogClickListener = this$0.mListener;
        if (onPopupTipDialogClickListener != null) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            onPopupTipDialogClickListener.onClick(it);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dealInit$lambda$4(PopupTipDialog this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupTipDialogClickListener onPopupTipDialogClickListener = this$0.mListener;
        if (onPopupTipDialogClickListener != null) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            onPopupTipDialogClickListener.onClick(it);
        }
    }

    public static /* synthetic */ PopupTipDialog setTipDialog$default(PopupTipDialog popupTipDialog, String str, String str2, String str3, String str4, int i2, int i3, Object obj) {
        String str5 = (i3 & 2) != 0 ? "" : str2;
        String str6 = (i3 & 4) != 0 ? "" : str3;
        String str7 = (i3 & 8) != 0 ? "" : str4;
        if ((i3 & 16) != 0) {
            i2 = 17;
        }
        return popupTipDialog.setTipDialog(str, str5, str6, str7, i2);
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.popup_tip_dialog;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        dealInit();
    }

    @NotNull
    public final PopupTipDialog setOnPopupTipDialogClickListener(@NotNull OnPopupTipDialogClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
        return this;
    }

    @NotNull
    public final PopupTipDialog setTipColorSize(int titleColorRes, int titleSize) {
        this.mTipColorRes = titleColorRes;
        this.mTipSize = titleSize;
        return this;
    }

    @NotNull
    public final PopupTipDialog setTipDialog(@NotNull String tipStr, @Nullable String oneOkStr, @Nullable String twoStr, @Nullable String titleStr, int tipGravity) {
        Intrinsics.checkNotNullParameter(tipStr, "tipStr");
        this.mTipStr = tipStr;
        this.mOneOkStr = oneOkStr;
        this.mTwoStr = twoStr;
        this.mTitleStr = titleStr;
        this.mTipGravity = tipGravity;
        return this;
    }

    @NotNull
    public final PopupTipDialog setTitelColorSize(int titleColorRes, int titleSize) {
        this.mTitleColorRes = titleColorRes;
        this.mTitleSize = titleSize;
        return this;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupTipDialog(@NonNull @NotNull Context context, boolean z2) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mOneBtn = z2;
        this.mTitleStr = "";
        this.mTitleColorRes = -1;
        this.mTitleSize = -1;
        this.mTipStr = "";
        this.mTipColorRes = -1;
        this.mTipSize = -1;
        this.mTipGravity = -1;
        this.mOneOkStr = "";
        this.mTwoStr = "";
    }
}

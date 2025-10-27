package com.yddmi.doctor.pages.login;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.lxj.xpopup.core.BottomPopupView;
import com.yddmi.doctor.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\rB\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0014J\b\u0010\t\u001a\u00020\nH\u0014J\u000e\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0006R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/yddmi/doctor/pages/login/PopupBottomAgreement;", "Lcom/lxj/xpopup/core/BottomPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "mListener", "Lcom/yddmi/doctor/pages/login/PopupBottomAgreement$OnPopupLoginAgreementClickListener;", "getImplLayoutId", "", "onCreate", "", "setOnPopupAgreementClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "OnPopupLoginAgreementClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupBottomAgreement extends BottomPopupView {

    @Nullable
    private OnPopupLoginAgreementClickListener mListener;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/pages/login/PopupBottomAgreement$OnPopupLoginAgreementClickListener;", "", "onClick", "", "view", "Landroid/view/View;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupLoginAgreementClickListener {
        void onClick(@NotNull View view);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupBottomAgreement(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopupBottomAgreement this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PopupBottomAgreement this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupLoginAgreementClickListener onPopupLoginAgreementClickListener = this$0.mListener;
        if (onPopupLoginAgreementClickListener != null) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            onPopupLoginAgreementClickListener.onClick(it);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(PopupBottomAgreement this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupLoginAgreementClickListener onPopupLoginAgreementClickListener = this$0.mListener;
        if (onPopupLoginAgreementClickListener != null) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            onPopupLoginAgreementClickListener.onClick(it);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$3(PopupBottomAgreement this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupLoginAgreementClickListener onPopupLoginAgreementClickListener = this$0.mListener;
        if (onPopupLoginAgreementClickListener != null) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            onPopupLoginAgreementClickListener.onClick(it);
        }
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.login_popup_bottom_agreement;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        View viewFindViewById = findViewById(R.id.xImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById<ImageView>(R.id.xImgv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.j0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupBottomAgreement.onCreate$lambda$0(this.f25868c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById2 = findViewById(R.id.f25733tv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById<TextView>(R.id.tv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById2, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.k0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupBottomAgreement.onCreate$lambda$1(this.f25873c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById3 = findViewById(R.id.tv1);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById<TextView>(R.id.tv1)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById3, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.l0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupBottomAgreement.onCreate$lambda$2(this.f25874c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById4 = findViewById(R.id.goTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById<BLTextView>(R.id.goTv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById4, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.m0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupBottomAgreement.onCreate$lambda$3(this.f25878c, view);
            }
        }, 0L, 2, null);
    }

    public final void setOnPopupAgreementClickListener(@NotNull OnPopupLoginAgreementClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }
}

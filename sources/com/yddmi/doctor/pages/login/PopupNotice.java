package com.yddmi.doctor.pages.login;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.lxj.xpopup.core.CenterPopupView;
import com.yddmi.doctor.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001\u000eB\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0014J\b\u0010\t\u001a\u00020\nH\u0014J\u000e\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0006J\b\u0010\r\u001a\u00020\nH\u0002R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/yddmi/doctor/pages/login/PopupNotice;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "mListener", "Lcom/yddmi/doctor/pages/login/PopupNotice$OnPopupNoticeClickListener;", "getImplLayoutId", "", "onCreate", "", "setOnPopupNoticeClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "viewDetailsShow", "OnPopupNoticeClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupNotice extends CenterPopupView {

    @Nullable
    private OnPopupNoticeClickListener mListener;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/pages/login/PopupNotice$OnPopupNoticeClickListener;", "", "onClick", "", "view", "Landroid/view/View;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupNoticeClickListener {
        void onClick(@Nullable View view);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupNotice(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    private final void viewDetailsShow() {
        View viewFindViewById = findViewById(R.id.xImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById<ImageView>(R.id.xImgv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.v0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupNotice.viewDetailsShow$lambda$0(this.f25899c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById2 = findViewById(R.id.tip2Tv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById<TextView>(R.id.tip2Tv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById2, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.w0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupNotice.viewDetailsShow$lambda$1(this.f25902c, view);
            }
        }, 0L, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void viewDetailsShow$lambda$0(PopupNotice this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void viewDetailsShow$lambda$1(PopupNotice this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupNoticeClickListener onPopupNoticeClickListener = this$0.mListener;
        if (onPopupNoticeClickListener != null) {
            onPopupNoticeClickListener.onClick(this$0);
        }
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.login_popup_notice;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        viewDetailsShow();
    }

    public final void setOnPopupNoticeClickListener(@NotNull OnPopupNoticeClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }
}

package com.yddmi.doctor.widget;

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

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001\u000eB\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0002J\b\u0010\t\u001a\u00020\nH\u0014J\b\u0010\u000b\u001a\u00020\bH\u0014J\u000e\u0010\f\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u0006R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/yddmi/doctor/widget/PopupLogoutDialog;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "mListener", "Lcom/yddmi/doctor/widget/PopupLogoutDialog$OnPopupLogoutDialogClickListener;", "dealInit", "", "getImplLayoutId", "", "onCreate", "setOnPopupLogoutDialogClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "OnPopupLogoutDialogClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupLogoutDialog extends CenterPopupView {

    @Nullable
    private OnPopupLogoutDialogClickListener mListener;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/widget/PopupLogoutDialog$OnPopupLogoutDialogClickListener;", "", "onClick", "", "view", "Landroid/view/View;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupLogoutDialogClickListener {
        void onClick(@NotNull View view);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupLogoutDialog(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    private final void dealInit() {
        View viewFindViewById = findViewById(R.id.oneTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById<TextView>(R.id.oneTv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById, new View.OnClickListener() { // from class: com.yddmi.doctor.widget.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupLogoutDialog.dealInit$lambda$0(this.f26065c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById2 = findViewById(R.id.twoTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById<TextView>(R.id.twoTv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById2, new View.OnClickListener() { // from class: com.yddmi.doctor.widget.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupLogoutDialog.dealInit$lambda$1(this.f26066c, view);
            }
        }, 0L, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dealInit$lambda$0(PopupLogoutDialog this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupLogoutDialogClickListener onPopupLogoutDialogClickListener = this$0.mListener;
        if (onPopupLogoutDialogClickListener != null) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            onPopupLogoutDialogClickListener.onClick(it);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dealInit$lambda$1(PopupLogoutDialog this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupLogoutDialogClickListener onPopupLogoutDialogClickListener = this$0.mListener;
        if (onPopupLogoutDialogClickListener != null) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            onPopupLogoutDialogClickListener.onClick(it);
        }
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.popup_logout_dialog;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        dealInit();
    }

    @NotNull
    public final PopupLogoutDialog setOnPopupLogoutDialogClickListener(@NotNull OnPopupLogoutDialogClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
        return this;
    }
}

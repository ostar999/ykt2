package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\t\u001a\u00020\nH\u0014J\b\u0010\u000b\u001a\u00020\fH\u0014J\b\u0010\r\u001a\u00020\fH\u0014J\b\u0010\u000e\u001a\u00020\fH\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000f"}, d2 = {"Lcom/psychiatrygarden/widget/CutQuestionSuccessPop;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lcom/lxj/xpopup/interfaces/OnConfirmListener;", "(Landroid/content/Context;Lcom/lxj/xpopup/interfaces/OnConfirmListener;)V", "getListener", "()Lcom/lxj/xpopup/interfaces/OnConfirmListener;", "getImplLayoutId", "", "onCreate", "", "onDismiss", "remind", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class CutQuestionSuccessPop extends CenterPopupView {

    @NotNull
    private final OnConfirmListener listener;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CutQuestionSuccessPop(@NotNull Context context, @NotNull OnConfirmListener listener) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.listener = listener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(CutQuestionSuccessPop this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.remind();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(CutQuestionSuccessPop this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.remind();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(CutQuestionSuccessPop this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    private final void remind() {
        ((ImageView) findViewById(R.id.iv_select)).setSelected(!r0.isSelected());
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_pop_cut_question_success;
    }

    @NotNull
    public final OnConfirmListener getListener() {
        return this.listener;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.tv_not_remind_later).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.k5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CutQuestionSuccessPop.onCreate$lambda$0(this.f16639c, view);
            }
        });
        findViewById(R.id.iv_select).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.l5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CutQuestionSuccessPop.onCreate$lambda$1(this.f16669c, view);
            }
        });
        findViewById(R.id.tv_i_know).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.m5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CutQuestionSuccessPop.onCreate$lambda$2(this.f16700c, view);
            }
        });
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.CUT_QUESTION_SUCCESS_REMIND_NEVER, ((ImageView) findViewById(R.id.iv_select)).isSelected(), getContext());
        this.listener.onConfirm();
        super.onDismiss();
    }
}

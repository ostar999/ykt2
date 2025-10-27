package com.psychiatrygarden.widget;

import android.content.Context;
import com.lxj.xpopup.core.PositionPopupView;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0014J\b\u0010\u0007\u001a\u00020\bH\u0014¨\u0006\t"}, d2 = {"Lcom/psychiatrygarden/widget/LoginContractConfirmPop;", "Lcom/lxj/xpopup/core/PositionPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "getImplLayoutId", "", "onShow", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class LoginContractConfirmPop extends PositionPopupView {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LoginContractConfirmPop(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onShow$lambda$0(LoginContractConfirmPop this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_pop_contract_confirm;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onShow() {
        postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.wa
            @Override // java.lang.Runnable
            public final void run() {
                LoginContractConfirmPop.onShow$lambda$0(this.f17045c);
            }
        }, 1500L);
    }
}

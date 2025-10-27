package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0014J\b\u0010\t\u001a\u00020\nH\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/psychiatrygarden/widget/RequestMediaPermissionPop;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "listenr", "Lcom/lxj/xpopup/interfaces/OnConfirmListener;", "(Landroid/content/Context;Lcom/lxj/xpopup/interfaces/OnConfirmListener;)V", "getImplLayoutId", "", "onCreate", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class RequestMediaPermissionPop extends CenterPopupView {

    @NotNull
    private final OnConfirmListener listenr;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RequestMediaPermissionPop(@NotNull Context context, @NotNull OnConfirmListener listenr) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(listenr, "listenr");
        this.listenr = listenr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(RequestMediaPermissionPop this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        EventBus.getDefault().post("closePermission");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(RequestMediaPermissionPop this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.listenr.onConfirm();
        this$0.dismiss();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_request_permission_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.gg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RequestMediaPermissionPop.onCreate$lambda$0(this.f16525c, view);
            }
        });
        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.hg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RequestMediaPermissionPop.onCreate$lambda$1(this.f16559c, view);
            }
        });
        ((TextView) findViewById(R.id.tv_content)).setText(getContext().getResources().getString(R.string.str_permission_hint));
    }
}

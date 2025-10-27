package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0014J\b\u0010\t\u001a\u00020\nH\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/psychiatrygarden/widget/UnlockNextChapterPop;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "onConfirmListener", "Lcom/lxj/xpopup/interfaces/OnConfirmListener;", "(Landroid/content/Context;Lcom/lxj/xpopup/interfaces/OnConfirmListener;)V", "getImplLayoutId", "", "onCreate", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class UnlockNextChapterPop extends CenterPopupView {

    @NotNull
    private final OnConfirmListener onConfirmListener;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnlockNextChapterPop(@NotNull Context context, @NotNull OnConfirmListener onConfirmListener) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(onConfirmListener, "onConfirmListener");
        this.onConfirmListener = onConfirmListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(UnlockNextChapterPop this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onConfirmListener.onConfirm();
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(UnlockNextChapterPop this$0, LinearLayout linearLayout) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int top2 = ((TextView) this$0.findViewById(R.id.tv_unlock)).getTop() + CommonUtil.dip2px(this$0.getContext(), 40.0f) + CommonUtil.dip2px(this$0.getContext(), 48.0f);
        ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
        layoutParams.height = top2;
        linearLayout.setLayoutParams(layoutParams);
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_pop_unlock_next_chapter;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        ((TextView) findViewById(R.id.tv_unlock)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.yi
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UnlockNextChapterPop.onCreate$lambda$0(this.f17136c, view);
            }
        });
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_content);
        linearLayout.post(new Runnable() { // from class: com.psychiatrygarden.widget.zi
            @Override // java.lang.Runnable
            public final void run() {
                UnlockNextChapterPop.onCreate$lambda$1(this.f17158c, linearLayout);
            }
        });
    }
}

package com.psychiatrygarden.activity.chooseSchool.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.exam.RvCountDownHelper;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\u000e\u001a\u00020\u000fH\u0014J\b\u0010\u0010\u001a\u00020\u0006H\u0014J\b\u0010\u0011\u001a\u00020\u0006H\u0014R \u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/widget/GetFreePopupView;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "clickListener", "Lkotlin/Function0;", "", "(Landroid/content/Context;Lkotlin/jvm/functions/Function0;)V", "getClickListener", "()Lkotlin/jvm/functions/Function0;", "setClickListener", "(Lkotlin/jvm/functions/Function0;)V", "helper", "Lcom/psychiatrygarden/exam/RvCountDownHelper;", "getImplLayoutId", "", "onCreate", "onDismiss", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class GetFreePopupView extends CenterPopupView {

    @NotNull
    private Function0<Unit> clickListener;

    @Nullable
    private RvCountDownHelper helper;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GetFreePopupView(@NotNull Context context, @NotNull Function0<Unit> clickListener) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(clickListener, "clickListener");
        this.clickListener = clickListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(GetFreePopupView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.clickListener.invoke();
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(GetFreePopupView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    @NotNull
    public final Function0<Unit> getClickListener() {
        return this.clickListener;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_pop_free_choose_school;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        View viewFindViewById = findViewById(R.id.imgFree);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.imgFree)");
        ((ImageView) viewFindViewById).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.widget.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GetFreePopupView.onCreate$lambda$0(this.f11453c, view);
            }
        });
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.widget.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GetFreePopupView.onCreate$lambda$1(this.f11454c, view);
            }
        });
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
        RvCountDownHelper rvCountDownHelper = this.helper;
        if (rvCountDownHelper != null) {
            rvCountDownHelper.destroy();
        }
        super.onDismiss();
    }

    public final void setClickListener(@NotNull Function0<Unit> function0) {
        Intrinsics.checkNotNullParameter(function0, "<set-?>");
        this.clickListener = function0;
    }
}

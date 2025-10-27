package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.lxj.xpopup.impl.FullScreenPopupView;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\u0005H\u0014J\b\u0010\t\u001a\u00020\nH\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/psychiatrygarden/widget/CutQuestionGuidePop;", "Lcom/lxj/xpopup/impl/FullScreenPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "guideXLineCoordinate", "", "guideYLineCoordinate", "(Landroid/content/Context;II)V", "getImplLayoutId", "onCreate", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class CutQuestionGuidePop extends FullScreenPopupView {
    private final int guideXLineCoordinate;
    private final int guideYLineCoordinate;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CutQuestionGuidePop(@NotNull Context context, int i2, int i3) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.guideXLineCoordinate = i2;
        this.guideYLineCoordinate = i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(CutQuestionGuidePop this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_pop_cut_question_guide;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        View viewFindViewById = findViewById(R.id.line);
        ViewGroup.LayoutParams layoutParams = viewFindViewById.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) layoutParams;
        ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin = this.guideXLineCoordinate;
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = this.guideYLineCoordinate;
        viewFindViewById.setLayoutParams(layoutParams2);
        ((TextView) findViewById(R.id.tv_ok)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.i5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CutQuestionGuidePop.onCreate$lambda$0(this.f16578c, view);
            }
        });
    }
}

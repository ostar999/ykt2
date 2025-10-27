package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.yikaobang.yixue.R;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0018\u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00060\u0005\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\u0011\u001a\u00020\u0012H\u0014J\b\u0010\u0013\u001a\u00020\u0014H\u0014R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR#\u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/psychiatrygarden/widget/PopQuestionDetailFilterShow;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "labels", "", "Lkotlin/Pair;", "", NotifyType.LIGHTS, "Lcom/lxj/xpopup/interfaces/OnConfirmListener;", "(Landroid/content/Context;Ljava/util/List;Lcom/lxj/xpopup/interfaces/OnConfirmListener;)V", "getL", "()Lcom/lxj/xpopup/interfaces/OnConfirmListener;", "getLabels", "()Ljava/util/List;", "rvLabels", "Landroidx/recyclerview/widget/RecyclerView;", "getImplLayoutId", "", "onCreate", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopQuestionDetailFilterShow extends CenterPopupView {

    @NotNull
    private final OnConfirmListener l;

    @NotNull
    private final List<Pair<String, String>> labels;
    private RecyclerView rvLabels;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopQuestionDetailFilterShow(@NotNull Context context, @NotNull List<Pair<String, String>> labels, @NotNull OnConfirmListener l2) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(labels, "labels");
        Intrinsics.checkNotNullParameter(l2, "l");
        this.labels = labels;
        this.l = l2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopQuestionDetailFilterShow this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PopQuestionDetailFilterShow this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.l.onConfirm();
        this$0.dismiss();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_question_detail_filter_show;
    }

    @NotNull
    public final OnConfirmListener getL() {
        return this.l;
    }

    @NotNull
    public final List<Pair<String, String>> getLabels() {
        return this.labels;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.tc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopQuestionDetailFilterShow.onCreate$lambda$0(this.f16933c, view);
            }
        });
        View viewFindViewById = findViewById(R.id.tv_do_question);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.tv_do_question)");
        ((TextView) viewFindViewById).setText("去做本考点试题");
        View viewFindViewById2 = findViewById(R.id.rvLabels);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.rvLabels)");
        RecyclerView recyclerView = (RecyclerView) viewFindViewById2;
        this.rvLabels = recyclerView;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvLabels");
            recyclerView = null;
        }
        recyclerView.setAdapter(new BaseQuickAdapter<Pair<? extends String, ? extends String>, BaseViewHolder>(this) { // from class: com.psychiatrygarden.widget.PopQuestionDetailFilterShow.onCreate.2
            {
                super(R.layout.item_filter_label, null, 2, null);
                setList(this.getLabels());
            }

            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public /* bridge */ /* synthetic */ void convert(BaseViewHolder baseViewHolder, Pair<? extends String, ? extends String> pair) {
                convert2(baseViewHolder, (Pair<String, String>) pair);
            }

            /* renamed from: convert, reason: avoid collision after fix types in other method */
            public void convert2(@NotNull BaseViewHolder holder, @NotNull Pair<String, String> item) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                Intrinsics.checkNotNullParameter(item, "item");
                holder.setText(R.id.tv_key, item.getFirst()).setText(R.id.tv_value, item.getSecond());
            }
        });
        findViewById(R.id.tv_do_question).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.uc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopQuestionDetailFilterShow.onCreate$lambda$1(this.f16959c, view);
            }
        });
    }
}

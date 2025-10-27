package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.CommonProblem;
import com.psychiatrygarden.bean.GoodsDetailItem;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B#\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/psychiatrygarden/widget/CommonProblemWidget;", "Landroid/widget/LinearLayout;", "Lcom/psychiatrygarden/widget/BaseContentWidget;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "def", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "rvProblems", "Landroidx/recyclerview/widget/RecyclerView;", com.umeng.socialize.tracker.a.f23806c, "", "data", "Lcom/psychiatrygarden/bean/GoodsDetailItem;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class CommonProblemWidget extends LinearLayout implements BaseContentWidget {

    @NotNull
    private RecyclerView rvProblems;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public CommonProblemWidget(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public CommonProblemWidget(@NotNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        View.inflate(context, R.layout.layout_common_problem, this);
        View viewFindViewById = findViewById(R.id.rvProblems);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.rvProblems)");
        this.rvProblems = (RecyclerView) viewFindViewById;
    }

    @Override // com.psychiatrygarden.widget.BaseContentWidget
    public void initData(@NotNull GoodsDetailItem data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.rvProblems.setAdapter(new BaseQuickAdapter<CommonProblem, BaseViewHolder>(data) { // from class: com.psychiatrygarden.widget.CommonProblemWidget.initData.1
            {
                super(R.layout.item_problem, null, 2, null);
                setList(data.getCourseData().getCommonProblem());
            }

            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NotNull BaseViewHolder holder, @NotNull CommonProblem item) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                Intrinsics.checkNotNullParameter(item, "item");
                holder.setText(R.id.tv_answer, item.getAnswer()).setText(R.id.tv_title, "Q:" + item.getQuestion());
            }
        });
    }

    public /* synthetic */ CommonProblemWidget(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }
}

package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.activity.mine.knowledge.KnowledgePointStatisticsAct;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.bean.KnowledgeNodeItemBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0014¨\u0006\b"}, d2 = {"com/psychiatrygarden/fragmenthome/KnowledgeListFragment$mNodeAdapter$1", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/KnowledgeNodeItemBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class KnowledgeListFragment$mNodeAdapter$1 extends BaseQuickAdapter<KnowledgeNodeItemBean, BaseViewHolder> {
    final /* synthetic */ KnowledgeListFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KnowledgeListFragment$mNodeAdapter$1(KnowledgeListFragment knowledgeListFragment) {
        super(R.layout.item_knowledge, null, 2, null);
        this.this$0 = knowledgeListFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$0(KnowledgeListFragment this$0, KnowledgeNodeItemBean item, View view) {
        String string;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        if (this$0.isLogin() || !CommonUtil.isFastClick()) {
            Context context = ((BaseFragment) this$0).mContext;
            String id = item.getId();
            Bundle arguments = this$0.getArguments();
            if (arguments == null || (string = arguments.getString("desc")) == null) {
                string = "";
            }
            KnowledgePointStatisticsAct.newIntent(context, id, string, this$0.question_bank_id);
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder holder, @NotNull final KnowledgeNodeItemBean item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        BaseViewHolder gone = holder.setText(R.id.tv_title, item.getName()).setText(R.id.tv_type, item.getCategory_str()).setGone(R.id.tv_continue_do, !item.isContinueDo());
        String category_str = item.getCategory_str();
        BaseViewHolder text = gone.setGone(R.id.tv_type, category_str == null || category_str.length() == 0).setText(R.id.tv_frequency, item.getFrequency_str()).setText(R.id.tv_star, item.getStar_str());
        String frequency_str = item.getFrequency_str();
        BaseViewHolder gone2 = text.setGone(R.id.tv_frequency, frequency_str == null || frequency_str.length() == 0);
        String star_str = item.getStar_str();
        BaseViewHolder gone3 = gone2.setGone(R.id.tv_star, star_str == null || star_str.length() == 0);
        String accuracy = item.getAccuracy();
        BaseViewHolder text2 = gone3.setGone(R.id.tv_correction_percent, accuracy == null || accuracy.length() == 0).setText(R.id.tv_count, item.getAnswer_count() + '/' + item.getQuestion_count());
        StringBuilder sb = new StringBuilder();
        sb.append("正确率");
        sb.append(item.getAccuracy());
        text2.setText(R.id.tv_correction_percent, sb.toString());
        ImageView imageView = (ImageView) holder.getView(R.id.iv_statistics);
        TypedArray typedArrayObtainStyledAttributes = ((BaseFragment) this.this$0).mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.ic_knowledge_chart_no_data, R.attr.ic_knowledge_chart_data, R.attr.ic_knowledge_chart_answer_lock});
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "mContext.theme.obtainSty…ledge_chart_answer_lock))");
        if (Intrinsics.areEqual(item.getIs_permission(), "0")) {
            imageView.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(2));
        } else {
            final KnowledgeListFragment knowledgeListFragment = this.this$0;
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.x7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    KnowledgeListFragment$mNodeAdapter$1.convert$lambda$0(knowledgeListFragment, item, view);
                }
            });
            if (Intrinsics.areEqual(item.getIs_do(), "1")) {
                imageView.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(1));
            } else {
                imageView.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(0));
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }
}

package com.psychiatrygarden.adapter;

import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.CombineQuestionRecordItem;
import com.psychiatrygarden.widget.SpringProgressView;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class HomeCombineQuestionListAdapter extends BaseQuickAdapter<CombineQuestionRecordItem, BaseViewHolder> {
    public HomeCombineQuestionListAdapter() {
        super(R.layout.item_home_combine_question);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, CombineQuestionRecordItem item) {
        holder.setText(R.id.tv_title, item.getTitle());
        if (item.getCurrent() > item.getErrorCount() + item.getRightCount()) {
            holder.setText(R.id.tv_question_progress, item.getCurrent() + "/" + item.getTotalCount());
        } else {
            holder.setText(R.id.tv_question_progress, (item.getErrorCount() + item.getRightCount()) + "/" + item.getTotalCount());
        }
        ((SpringProgressView) holder.getView(R.id.spring_progress_view)).setMaxErrRightCount(item.getTotalCount(), item.getErrorCount(), item.getRightCount());
    }
}

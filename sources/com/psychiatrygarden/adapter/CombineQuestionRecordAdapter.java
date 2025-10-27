package com.psychiatrygarden.adapter;

import android.text.TextUtils;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.CombineQuestionRecordItem;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.SpringProgressView;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class CombineQuestionRecordAdapter extends BaseQuickAdapter<CombineQuestionRecordItem, BaseViewHolder> {
    public CombineQuestionRecordAdapter() {
        super(R.layout.item_combine_question_record);
        addChildClickViewIds(R.id.iv_select);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, CombineQuestionRecordItem item) {
        holder.getView(R.id.iv_select).setSelected(item.isSelect());
        holder.setText(R.id.tv_question_title, item.getTitle()).setImageResource(R.id.iv_select, item.isSelect() ? SkinManager.getCurrentSkinType(getContext()) == 0 ? R.drawable.ic_selec_selected_night : R.drawable.ic_select_selected_night : SkinManager.getCurrentSkinType(getContext()) == 1 ? R.mipmap.ic_select_no_night : R.mipmap.ic_select_no_day).setText(R.id.tv_question_progress, item.getCurrent() + "/" + item.getTotalCount()).setGone(R.id.iv_select, true ^ item.isEdit());
        TextView textView = (TextView) holder.getView(R.id.tvFlag);
        TextView textView2 = (TextView) holder.getView(R.id.tvTime);
        if (TextUtils.isEmpty(item.getQuestion_cate_st())) {
            textView.setVisibility(8);
        } else {
            textView.setText(item.getQuestion_cate_st());
        }
        if (TextUtils.isEmpty(item.getTime())) {
            textView2.setText("");
        } else {
            textView2.setText(item.getTime().replace("/", "-"));
        }
        ((SpringProgressView) holder.getView(R.id.spring_progress_view)).setMaxErrRightCount(item.getTotalCount(), item.getErrorCount(), item.getRightCount());
    }
}

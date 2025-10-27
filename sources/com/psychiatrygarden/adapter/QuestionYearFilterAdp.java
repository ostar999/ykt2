package com.psychiatrygarden.adapter;

import android.text.TextUtils;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.activity.online.bean.QuestionListBean;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class QuestionYearFilterAdp extends BaseQuickAdapter<QuestionListBean.DataDTO.SearchDTO.SearchDataDTO, BaseViewHolder> {
    public QuestionYearFilterAdp(int resLayoutId) {
        super(resLayoutId);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, QuestionListBean.DataDTO.SearchDTO.SearchDataDTO item) {
        TextView textView = (TextView) holder.getView(R.id.tv_year);
        if (!item.getId().equals("free_year") || TextUtils.isEmpty(item.getYearTitle())) {
            textView.setText(item.getTitle());
        } else {
            textView.setText(item.getYearTitle());
        }
        textView.setSelected(item.getIsSelected() == 1);
    }
}

package com.psychiatrygarden.adapter;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.SurveyBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class SurveyAdp extends BaseQuickAdapter<SurveyBean.SurveyDataBean, BaseViewHolder> {
    public SurveyAdp() {
        super(R.layout.suevey_item);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, SurveyBean.SurveyDataBean item) {
        ImageView imageView = (ImageView) holder.getView(R.id.icon);
        TextView textView = (TextView) holder.getView(R.id.tv_title);
        TextView textView2 = (TextView) holder.getView(R.id.tv_desc);
        textView.setText(item.getTitle());
        textView2.setText(item.getDescribe());
        GlideUtils.loadImage(getContext(), item.getImg_url(), imageView);
    }
}

package com.psychiatrygarden.adapter;

import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.ComputerStatisticsBean;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ComputerStatisticsTypeAdp extends BaseQuickAdapter<ComputerStatisticsBean.StatisticsBean, BaseViewHolder> {
    private boolean isType;

    public ComputerStatisticsTypeAdp(boolean isType) {
        super(R.layout.item_computer_statistics);
        this.isType = isType;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, ComputerStatisticsBean.StatisticsBean item) {
        LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.ly_item);
        TextView textView = (TextView) holder.getView(R.id.tv_one);
        TextView textView2 = (TextView) holder.getView(R.id.tv_two);
        TextView textView3 = (TextView) holder.getView(R.id.tv_three);
        TextView textView4 = (TextView) holder.getView(R.id.tv_four);
        if (holder.getLayoutPosition() == 0) {
            linearLayout.setBackgroundResource(R.drawable.shape_new_gray_title_socket_bg);
            textView.setTypeface(Typeface.defaultFromStyle(1));
            textView2.setTypeface(Typeface.defaultFromStyle(1));
            textView3.setTypeface(Typeface.defaultFromStyle(1));
        } else {
            linearLayout.setBackgroundResource(R.drawable.shape_new_gray_socket_bg);
            textView.setTypeface(Typeface.defaultFromStyle(0));
            textView2.setTypeface(Typeface.defaultFromStyle(0));
            textView3.setTypeface(Typeface.defaultFromStyle(0));
        }
        if (this.isType) {
            int screenWidth = (UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 32.0d)) / 3;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.width = screenWidth;
            textView.setLayoutParams(layoutParams);
            textView2.setLayoutParams(layoutParams);
            textView3.setLayoutParams(layoutParams);
            textView.setText(item.getQuestion_type());
            textView2.setText(item.getWrong_count());
            textView3.setText(item.getWrong_percent());
            textView4.setVisibility(8);
            return;
        }
        int screenWidth2 = (UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 32.0d)) / 4;
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) textView.getLayoutParams();
        layoutParams2.width = screenWidth2;
        textView.setLayoutParams(layoutParams2);
        textView2.setLayoutParams(layoutParams2);
        textView3.setLayoutParams(layoutParams2);
        textView4.setLayoutParams(layoutParams2);
        if (holder.getLayoutPosition() == 0) {
            textView4.setTypeface(Typeface.defaultFromStyle(1));
        } else {
            textView4.setTypeface(Typeface.defaultFromStyle(0));
        }
        textView.setText(item.getSubject_name());
        textView2.setText(item.getChapter_name());
        textView3.setText(item.getWrong_count());
        textView4.setText(item.getWrong_percent());
        textView4.setVisibility(0);
    }
}

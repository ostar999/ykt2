package com.psychiatrygarden.adapter;

import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.ComputerTwoStatisticsBean;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ComputerTwoStatisticsAdp extends BaseQuickAdapter<ComputerTwoStatisticsBean.StatisticsBean, BaseViewHolder> {
    public ComputerTwoStatisticsAdp() {
        super(R.layout.item_computer_statistics);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, ComputerTwoStatisticsBean.StatisticsBean item) {
        LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.ly_item);
        TextView textView = (TextView) holder.getView(R.id.tv_one);
        TextView textView2 = (TextView) holder.getView(R.id.tv_two);
        TextView textView3 = (TextView) holder.getView(R.id.tv_three);
        TextView textView4 = (TextView) holder.getView(R.id.tv_four);
        TextView textView5 = (TextView) holder.getView(R.id.tv_five);
        textView4.setVisibility(0);
        textView5.setVisibility(0);
        textView.setGravity(17);
        textView2.setGravity(17);
        textView3.setGravity(17);
        textView4.setGravity(17);
        textView5.setGravity(17);
        if (holder.getLayoutPosition() == 0) {
            linearLayout.setBackgroundResource(R.drawable.shape_new_gray_title_socket_bg);
            textView.setTypeface(Typeface.defaultFromStyle(1));
            textView2.setTypeface(Typeface.defaultFromStyle(1));
            textView3.setTypeface(Typeface.defaultFromStyle(1));
            textView4.setTypeface(Typeface.defaultFromStyle(1));
            textView5.setTypeface(Typeface.defaultFromStyle(1));
        } else {
            linearLayout.setBackgroundResource(R.drawable.shape_new_gray_socket_bg);
            textView.setTypeface(Typeface.defaultFromStyle(0));
            textView2.setTypeface(Typeface.defaultFromStyle(0));
            textView3.setTypeface(Typeface.defaultFromStyle(0));
            textView4.setTypeface(Typeface.defaultFromStyle(0));
            textView5.setTypeface(Typeface.defaultFromStyle(0));
        }
        int screenWidth = (UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 32.0d)) / 5;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
        layoutParams.width = screenWidth;
        textView.setLayoutParams(layoutParams);
        textView2.setLayoutParams(layoutParams);
        textView3.setLayoutParams(layoutParams);
        textView4.setLayoutParams(layoutParams);
        textView5.setLayoutParams(layoutParams);
        textView.setText(item.getQuestion_type());
        textView2.setText(item.getRight_count());
        textView3.setText(item.getScore());
        textView4.setText(item.getScore_total());
        textView5.setText(item.getAccuracy());
    }
}

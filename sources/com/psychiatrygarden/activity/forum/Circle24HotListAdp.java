package com.psychiatrygarden.activity.forum;

import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.CirclrListBean;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class Circle24HotListAdp extends BaseQuickAdapter<CirclrListBean.DataBean, BaseViewHolder> {
    public Circle24HotListAdp() {
        super(R.layout.item_hot_circle_list);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, CirclrListBean.DataBean item) {
        LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.ly_number);
        TextView textView = (TextView) holder.getView(R.id.tv_index);
        TextView textView2 = (TextView) holder.getView(R.id.tv_title);
        LinearLayout linearLayout2 = (LinearLayout) holder.getView(R.id.ly_item);
        textView.setVisibility(item.getSort() == 0 ? 8 : 0);
        textView.setText(item.getSort() + "");
        int sort = item.getSort();
        if (sort == 0) {
            linearLayout2.setBackgroundResource(R.drawable.shape_hot_circle_one_bg);
            linearLayout.setBackgroundResource(R.mipmap.ic_hot_fire_top);
        } else if (sort == 1) {
            linearLayout2.setBackgroundResource(R.drawable.shape_hot_circle_one_bg);
            linearLayout.setBackgroundResource(R.mipmap.ic_hot_fire_one);
        } else if (sort == 2) {
            linearLayout2.setBackgroundResource(R.drawable.shape_hot_circle_two_bg);
            linearLayout.setBackgroundResource(R.mipmap.ic_hot_fire_two);
        } else if (sort != 3) {
            linearLayout2.setBackgroundResource(R.drawable.shape_hot_circle_gray_bg);
            linearLayout.setBackgroundResource(R.mipmap.ic_hot_fire_gray);
        } else {
            linearLayout2.setBackgroundResource(R.drawable.shape_hot_circle_three_bg);
            linearLayout.setBackgroundResource(R.mipmap.ic_hot_fire_four);
        }
        textView2.setText(item.getTitle());
    }
}

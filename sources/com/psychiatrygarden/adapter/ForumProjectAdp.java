package com.psychiatrygarden.adapter;

import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.ForumTopBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ForumProjectAdp extends BaseQuickAdapter<ForumTopBean.TopModule, BaseViewHolder> {
    public ForumProjectAdp() {
        super(R.layout.item_circle_kingkong);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, ForumTopBean.TopModule item) {
        int screenWidth = (UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 32.0d)) / 5;
        LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.ly_item);
        ImageView imageView = (ImageView) holder.getView(R.id.iv_icon);
        TextView textView = (TextView) holder.getView(R.id.tv_title);
        TextView textView2 = (TextView) holder.getView(R.id.tv_tag);
        TextView textView3 = (TextView) holder.getView(R.id.tv_tag_more);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) linearLayout.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = screenWidth;
        linearLayout.setLayoutParams(layoutParams);
        textView.setText(item.getName());
        if (TextUtils.isEmpty(item.getLabel())) {
            textView2.setVisibility(8);
            textView3.setVisibility(8);
        } else if (item.getLabel().length() > 2) {
            textView3.setVisibility(0);
            textView2.setVisibility(8);
            textView3.setText(item.getLabel());
        } else {
            textView2.setVisibility(0);
            textView3.setVisibility(8);
            textView2.setText(item.getLabel());
        }
        GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(item.getIcon())).into(imageView);
    }
}

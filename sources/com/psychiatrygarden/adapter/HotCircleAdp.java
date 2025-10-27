package com.psychiatrygarden.adapter;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.HotCircleBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class HotCircleAdp extends BaseMultiItemQuickAdapter<HotCircleBean, BaseViewHolder> {
    public HotCircleAdp() {
        addItemType(1, R.layout.item_24_hot_circle);
        addItemType(2, R.layout.item_circle_24_hot_more);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, HotCircleBean item) {
        int itemType = item.getItemType();
        if (itemType != 1) {
            if (itemType != 2) {
                return;
            }
            return;
        }
        TextView textView = (TextView) holder.getView(R.id.tv_title);
        ImageView imageView = (ImageView) holder.getView(R.id.iv_icon);
        if (item.getCircleInfo().getIcon_img() == null || item.getCircleInfo().getIcon_img().isEmpty()) {
            imageView.setVisibility(8);
        } else {
            imageView.setVisibility(0);
            GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(item.getCircleInfo().getIcon_img().get(0))).into(imageView);
        }
        textView.setText(item.getCircleInfo().getTitle());
    }
}

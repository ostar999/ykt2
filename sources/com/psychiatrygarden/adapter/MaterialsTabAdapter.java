package com.psychiatrygarden.adapter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.MaterialTab;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class MaterialsTabAdapter extends BaseQuickAdapter<MaterialTab.MaterialTabData, BaseViewHolder> {
    public MaterialsTabAdapter() {
        super(R.layout.item_material_list);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder helper, MaterialTab.MaterialTabData item) {
        ImageView imageView = (ImageView) helper.getView(R.id.iv_file_type);
        ImageView imageView2 = (ImageView) helper.getView(R.id.ic_lock);
        TextView textView = (TextView) helper.getView(R.id.tv_file_name);
        TextView textView2 = (TextView) helper.getView(R.id.tv_author);
        TextView textView3 = (TextView) helper.getView(R.id.tv_file_size);
        TextView textView4 = (TextView) helper.getView(R.id.tv_download_count);
        LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.ly_author_info);
        helper.getView(R.id.line).setVisibility(8);
        textView.setText(item.getApp_name());
        textView2.setVisibility(8);
        if (item.getId().equals("0")) {
            linearLayout.setVisibility(8);
            GlideApp.with(getContext()).load(Integer.valueOf(R.drawable.ic_directory)).placeholder(R.mipmap.ic_order_default).into(imageView);
        } else {
            linearLayout.setVisibility(0);
            textView3.setText(item.getFile_count() + "个资料");
            textView4.setText(item.getDownload_count() + "次下载");
            GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(item.getIcon())).placeholder(R.mipmap.ic_order_default).into(imageView);
        }
        imageView2.setVisibility(8);
    }
}

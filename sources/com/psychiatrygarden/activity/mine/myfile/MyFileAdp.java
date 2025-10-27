package com.psychiatrygarden.activity.mine.myfile;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.MaterialListBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class MyFileAdp extends BaseQuickAdapter<MaterialListBean.MaterialListData, BaseViewHolder> {
    private boolean isEdit;

    public MyFileAdp() {
        super(R.layout.item_my_file);
        this.isEdit = false;
        addChildClickViewIds(R.id.iv_select);
    }

    public void setEdit(boolean edit) {
        this.isEdit = edit;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public /* bridge */ /* synthetic */ void convert(@NonNull BaseViewHolder holder, MaterialListBean.MaterialListData item, @NonNull List payloads) {
        convert2(holder, item, (List<?>) payloads);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder helper, MaterialListBean.MaterialListData item) {
        ImageView imageView = (ImageView) helper.getView(R.id.iv_file_type);
        ImageView imageView2 = (ImageView) helper.getView(R.id.ic_lock);
        TextView textView = (TextView) helper.getView(R.id.tv_file_name);
        TextView textView2 = (TextView) helper.getView(R.id.tv_author);
        TextView textView3 = (TextView) helper.getView(R.id.tv_file_size);
        TextView textView4 = (TextView) helper.getView(R.id.tv_download_count);
        ImageView imageView3 = (ImageView) helper.getView(R.id.iv_select);
        helper.getView(R.id.line).setVisibility(8);
        textView4.setText(item.getDownload_count() + "次下载");
        GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(item.getIcon())).into(imageView);
        textView.setText(item.getTitle() + item.getType_name());
        textView3.setText(item.getSize());
        if (this.isEdit) {
            imageView3.setVisibility(0);
            imageView2.setVisibility(8);
            imageView3.setSelected(item.isSelect());
        } else {
            imageView3.setVisibility(8);
            if (!TextUtils.isEmpty(item.getIs_rights()) && item.getIs_rights().equals("1")) {
                imageView2.setVisibility(8);
            } else {
                imageView2.setVisibility(0);
            }
        }
        if (TextUtils.isEmpty(item.getNickname())) {
            textView2.setVisibility(8);
        } else {
            textView2.setVisibility(0);
            textView2.setText(item.getNickname());
        }
    }

    /* renamed from: convert, reason: avoid collision after fix types in other method */
    public void convert2(@NonNull BaseViewHolder holder, MaterialListBean.MaterialListData item, @NonNull List<?> payloads) {
        super.convert((MyFileAdp) holder, (BaseViewHolder) item, (List<? extends Object>) payloads);
        if (payloads == null || ((Integer) payloads.get(0)).intValue() != 1) {
            return;
        }
        ((ImageView) holder.getView(R.id.iv_select)).setSelected(item.isSelect());
    }
}

package com.psychiatrygarden.activity.setting;

import android.text.TextUtils;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.bean.MyMessageBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ServiceNoticeAdp extends BaseQuickAdapter<MyMessageBean.DataBean, BaseViewHolder> {
    public ServiceNoticeAdp() {
        super(R.layout.item_service_notice);
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public void convert(BaseViewHolder holder, MyMessageBean.DataBean item) {
        holder.setText(R.id.tv_time, item.getTimestr());
        holder.setText(R.id.tv_title, item.getTitle());
        holder.setText(R.id.tv_content, item.getContent());
        RoundedImageView roundedImageView = (RoundedImageView) holder.getView(R.id.img_icon);
        if (TextUtils.isEmpty(item.getImg())) {
            roundedImageView.setVisibility(8);
            GlideApp.with(this.mContext).load(Integer.valueOf(R.drawable.app_icon)).into(roundedImageView);
        } else {
            roundedImageView.setVisibility(0);
            GlideApp.with(this.mContext).load((Object) GlideUtils.generateUrl(item.getImg())).into(roundedImageView);
        }
    }
}

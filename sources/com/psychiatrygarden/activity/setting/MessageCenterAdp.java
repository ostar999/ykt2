package com.psychiatrygarden.activity.setting;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.psychiatrygarden.bean.MessageCenterBean;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class MessageCenterAdp extends BaseQuickAdapter<MessageCenterBean.MessageCenterItemBean, BaseViewHolder> {
    public MessageCenterAdp() {
        super(R.layout.item_message_center);
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public void convert(BaseViewHolder holder, MessageCenterBean.MessageCenterItemBean item) {
        ImageView imageView;
        imageView = (ImageView) holder.getView(R.id.img_icon);
        String type = item.getType();
        type.hashCode();
        switch (type) {
            case "friends":
                imageView.setImageResource(SkinManager.getCurrentSkinType(this.mContext) == 1 ? R.mipmap.ic_message_friend_night : R.mipmap.ic_message_friend);
                break;
            case "live":
                imageView.setImageResource(SkinManager.getCurrentSkinType(this.mContext) == 1 ? R.mipmap.ic_message_get_class_night : R.mipmap.ic_message_get_class);
                break;
            case "service":
                imageView.setImageResource(SkinManager.getCurrentSkinType(this.mContext) == 1 ? R.mipmap.ic_message_service_night : R.mipmap.ic_message_service);
                break;
        }
        holder.setText(R.id.tv_time, item.getDate());
        holder.setGone(R.id.tv_number, Integer.parseInt(item.getCount()) > 0);
        holder.setText(R.id.tv_number, item.getCount());
        TextView textView = (TextView) holder.getView(R.id.tv_desc);
        if (TextUtils.isEmpty(item.getContent())) {
            holder.setGone(R.id.tv_title_center, true);
            holder.setGone(R.id.tv_title, false);
            holder.setText(R.id.tv_title_center, item.getTitle());
            textView.setVisibility(8);
            return;
        }
        textView.setText(item.getContent());
        textView.setVisibility(0);
        holder.setGone(R.id.tv_title_center, false);
        holder.setGone(R.id.tv_title, true);
        holder.setText(R.id.tv_title, item.getTitle());
    }
}

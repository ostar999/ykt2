package com.psychiatrygarden.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.psychiatrygarden.bean.HandoutChannelBean;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.glideUtil.GlideImageView;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class OtherNewAdapter extends BaseAdapter {
    public List<HandoutChannelBean.DataBean.ListBean.SubBean> channelList;
    private Context context;
    boolean isVisible = true;
    public int remove_position = -1;

    public OtherNewAdapter(Context context, List<HandoutChannelBean.DataBean.ListBean.SubBean> channelList) {
        this.context = context;
        this.channelList = channelList;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        List<HandoutChannelBean.DataBean.ListBean.SubBean> list = this.channelList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    public int getPositionForSection(int section) {
        for (int i2 = 0; i2 < getCount(); i2++) {
            if (this.channelList.get(i2).getSortLetters().toUpperCase().charAt(0) == section) {
                return i2;
            }
        }
        return -1;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.channel_new_item, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.text_item);
        GlideImageView glideImageView = (GlideImageView) viewInflate.findViewById(R.id.jykb);
        HandoutChannelBean.DataBean.ListBean.SubBean item = getItem(position);
        textView.setText(item.getTitle());
        if (!this.isVisible && position == this.channelList.size() - 1) {
            textView.setText("");
        }
        if (this.remove_position == position) {
            textView.setText("");
        }
        if (item.getSelected().equals("1")) {
            if (SkinManager.getCurrentSkinType(this.context) == 1) {
                glideImageView.setColorFilter(Color.parseColor("#B2575C"));
            }
            glideImageView.setVisibility(0);
        } else {
            glideImageView.setVisibility(8);
        }
        return viewInflate;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void remove() {
        this.channelList.remove(this.remove_position);
        this.remove_position = -1;
        notifyDataSetChanged();
    }

    public void setRemove(int position) {
        this.remove_position = position;
        notifyDataSetChanged();
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    @Override // android.widget.Adapter
    public HandoutChannelBean.DataBean.ListBean.SubBean getItem(int position) {
        List<HandoutChannelBean.DataBean.ListBean.SubBean> list = this.channelList;
        if (list == null || list.size() == 0) {
            return null;
        }
        return this.channelList.get(position);
    }
}

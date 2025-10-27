package com.psychiatrygarden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.psychiatrygarden.bean.ChannelItem;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class OtherAdapter extends BaseAdapter {
    public List<ChannelItem> channelList;
    private Context context;
    boolean isVisible = true;
    public int remove_position = -1;

    public OtherAdapter(Context context, List<ChannelItem> channelList) {
        this.context = context;
        this.channelList = channelList;
    }

    public void addItem(ChannelItem channel) {
        this.channelList.add(channel);
        notifyDataSetChanged();
    }

    public List<ChannelItem> getChannnelLst() {
        return this.channelList;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        List<ChannelItem> list = this.channelList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.channel_item, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.text_item);
        textView.setText(getItem(position).getName());
        if (!this.isVisible && position == this.channelList.size() - 1) {
            textView.setText("");
        }
        if (this.remove_position == position) {
            textView.setText("");
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

    public void setListDate(List<ChannelItem> list) {
        this.channelList = list;
    }

    public void setRemove(int position) {
        this.remove_position = position;
        notifyDataSetChanged();
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    @Override // android.widget.Adapter
    public ChannelItem getItem(int position) {
        List<ChannelItem> list = this.channelList;
        if (list == null || list.size() == 0) {
            return null;
        }
        return this.channelList.get(position);
    }
}

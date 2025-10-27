package com.psychiatrygarden.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.ChannelItem;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class DragAdapter extends BaseAdapter {
    private static final String TAG = "DragAdapter";
    public List<ChannelItem> channelList;
    private Context context;
    private int holdPosition;
    public boolean isCircleTrue;
    private boolean isItemShow = false;
    private boolean isChanged = false;
    private boolean isListChanged = false;
    boolean isVisible = true;
    public int remove_position = -1;

    public DragAdapter(Context context, List<ChannelItem> channelList, boolean isCircleTrue) {
        this.context = context;
        this.channelList = channelList;
        this.isCircleTrue = isCircleTrue;
    }

    public void addItem(ChannelItem channel) {
        this.channelList.add(channel);
        this.isListChanged = true;
        notifyDataSetChanged();
    }

    public void exchange(int dragPostion, int dropPostion) {
        this.holdPosition = dropPostion;
        ChannelItem item = getItem(dragPostion);
        Log.d(TAG, "startPostion=" + dragPostion + ";endPosition=" + dropPostion);
        if (dragPostion < dropPostion) {
            this.channelList.add(dropPostion + 1, item);
            this.channelList.remove(dragPostion);
        } else {
            this.channelList.add(dropPostion, item);
            this.channelList.remove(dragPostion + 1);
        }
        this.isChanged = true;
        this.isListChanged = true;
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
        View viewInflate = View.inflate(this.context, R.layout.channel_item, null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.text_item);
        textView.setText(getItem(position).getName());
        if (this.isCircleTrue) {
            if (position <= SharePreferencesUtils.readLongConfig(CommonParameter.circlrListnum, ProjectApp.instance(), 1L).longValue() - 1) {
                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    textView.setTextColor(ContextCompat.getColor(this.context, R.color.gray_font_new));
                } else {
                    textView.setTextColor(ContextCompat.getColor(this.context, R.color.jiucuo_night));
                }
                textView.setEnabled(false);
            }
        } else if (position <= SharePreferencesUtils.readLongConfig(CommonParameter.Lock_NUM_TYPE, ProjectApp.instance(), 1L).longValue() - 1) {
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                textView.setTextColor(ContextCompat.getColor(this.context, R.color.gray_font_new));
            } else {
                textView.setTextColor(ContextCompat.getColor(this.context, R.color.jiucuo_night));
            }
            textView.setEnabled(false);
        }
        if (this.isChanged && position == this.holdPosition && !this.isItemShow) {
            textView.setText("");
            textView.setSelected(true);
            textView.setEnabled(true);
            this.isChanged = false;
        }
        if (!this.isVisible && position == this.channelList.size() - 1) {
            textView.setText("");
            textView.setSelected(true);
            textView.setEnabled(true);
        }
        if (this.remove_position == position) {
            textView.setText("");
        }
        return viewInflate;
    }

    public boolean isListChanged() {
        return this.isListChanged;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void remove() {
        this.channelList.remove(this.remove_position);
        this.remove_position = -1;
        this.isListChanged = true;
        notifyDataSetChanged();
    }

    public void setListDate(List<ChannelItem> list) {
        this.channelList = list;
    }

    public void setRemove(int position) {
        this.remove_position = position;
        notifyDataSetChanged();
    }

    public void setShowDropItem(boolean show) {
        this.isItemShow = show;
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

    public DragAdapter(Context context, List<ChannelItem> channelList) {
        this.context = context;
        this.channelList = channelList;
    }
}

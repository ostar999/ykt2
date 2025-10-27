package com.psychiatrygarden.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.HandoutChannelBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.glideUtil.GlideImageView;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class DragNewAdapter extends BaseAdapter {
    public List<HandoutChannelBean.DataBean.DefaultBean> channelList;
    private Context context;
    private int holdPosition;
    public boolean isCircleTrue;
    private TextView item_text;
    private GlideImageView jykb;
    private boolean isItemShow = false;
    private boolean isChanged = false;
    private boolean isListChanged = false;
    boolean isVisible = true;
    public int remove_position = -1;

    public DragNewAdapter(Context context, List<HandoutChannelBean.DataBean.DefaultBean> channelList, boolean isCircleTrue) {
        this.context = context;
        this.channelList = channelList;
        this.isCircleTrue = isCircleTrue;
    }

    public void addItem(HandoutChannelBean.DataBean.DefaultBean channel) {
        this.channelList.add(channel);
        this.isListChanged = true;
        notifyDataSetChanged();
    }

    public void exchange(int dragPostion, int dropPostion) {
        this.holdPosition = dropPostion;
        HandoutChannelBean.DataBean.DefaultBean item = getItem(dragPostion);
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

    @Override // android.widget.Adapter
    public int getCount() {
        List<HandoutChannelBean.DataBean.DefaultBean> list = this.channelList;
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
        View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.channel_new_item, (ViewGroup) null);
        this.item_text = (TextView) viewInflate.findViewById(R.id.text_item);
        this.jykb = (GlideImageView) viewInflate.findViewById(R.id.jykb);
        HandoutChannelBean.DataBean.DefaultBean item = getItem(position);
        this.item_text.setText(String.format("%s %s", item.getTitle(), item.getPname()));
        if (!this.isCircleTrue) {
            if (item.getSelected().equals("1")) {
                if (SkinManager.getCurrentSkinType(this.context) == 1) {
                    this.jykb.setColorFilter(Color.parseColor("#B2575C"));
                }
                this.jykb.setVisibility(0);
            } else {
                this.jykb.setVisibility(8);
            }
            if (position <= SharePreferencesUtils.readLongConfig(CommonParameter.Lock_NUM_TYPE, ProjectApp.instance(), 1L).longValue() - 1) {
                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    this.item_text.setTextColor(ContextCompat.getColor(this.context, R.color.gray_font_new));
                } else {
                    this.item_text.setTextColor(ContextCompat.getColor(this.context, R.color.fourth_text_color_night));
                }
                this.item_text.setEnabled(false);
            }
        } else if (position <= SharePreferencesUtils.readLongConfig(CommonParameter.circlrListnum, ProjectApp.instance(), 1L).longValue() - 1) {
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                this.item_text.setTextColor(ContextCompat.getColor(this.context, R.color.gray_font_new));
            } else {
                this.item_text.setTextColor(ContextCompat.getColor(this.context, R.color.fourth_text_color_night));
            }
            this.item_text.setEnabled(false);
        }
        if (this.isChanged && position == this.holdPosition && !this.isItemShow) {
            this.item_text.setText("");
            this.item_text.setSelected(true);
            this.item_text.setEnabled(true);
            this.isChanged = false;
        }
        if (!this.isVisible && position == this.channelList.size() - 1) {
            this.item_text.setText("");
            this.item_text.setSelected(true);
            this.item_text.setEnabled(true);
        }
        if (this.remove_position == position) {
            this.item_text.setText("");
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
    public HandoutChannelBean.DataBean.DefaultBean getItem(int position) {
        List<HandoutChannelBean.DataBean.DefaultBean> list = this.channelList;
        if (list == null || list.size() == 0) {
            return null;
        }
        return this.channelList.get(position);
    }

    public DragNewAdapter(Context context, List<HandoutChannelBean.DataBean.DefaultBean> channelList) {
        this.context = context;
        this.channelList = channelList;
    }
}

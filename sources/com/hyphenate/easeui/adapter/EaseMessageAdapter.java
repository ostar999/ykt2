package com.hyphenate.easeui.adapter;

import android.view.ViewGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter;
import com.hyphenate.easeui.delegate.EaseMessageAdapterDelegate;
import com.hyphenate.easeui.interfaces.MessageListItemClickListener;
import com.hyphenate.easeui.model.styles.EaseMessageListItemStyle;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseMessageAdapter extends EaseBaseDelegateAdapter<EMMessage> {
    public MessageListItemClickListener itemClickListener;

    private EMMessage getItemMessage(int i2) {
        List<T> list = this.mData;
        if (list == 0 || list.isEmpty()) {
            return null;
        }
        return (EMMessage) this.mData.get(i2);
    }

    @Override // com.hyphenate.easeui.adapter.EaseBaseDelegateAdapter
    public EaseBaseDelegateAdapter addDelegate(EaseAdapterDelegate easeAdapterDelegate) {
        try {
            EaseAdapterDelegate easeAdapterDelegate2 = (EaseAdapterDelegate) easeAdapterDelegate.clone();
            easeAdapterDelegate2.setTag(EMMessage.Direct.RECEIVE.name());
            if (easeAdapterDelegate2 instanceof EaseMessageAdapterDelegate) {
                ((EaseMessageAdapterDelegate) easeAdapterDelegate2).setListItemClickListener(this.itemClickListener);
            }
            super.addDelegate(easeAdapterDelegate2);
        } catch (CloneNotSupportedException e2) {
            e2.printStackTrace();
        }
        easeAdapterDelegate.setTag(EMMessage.Direct.SEND.name());
        if (easeAdapterDelegate instanceof EaseMessageAdapterDelegate) {
            ((EaseMessageAdapterDelegate) easeAdapterDelegate).setListItemClickListener(this.itemClickListener);
        }
        return super.addDelegate(easeAdapterDelegate);
    }

    public EaseMessageListItemStyle createDefaultItemStyle() {
        EaseMessageListItemStyle.Builder builder = new EaseMessageListItemStyle.Builder();
        builder.showAvatar(true).showUserNick(false);
        return builder.build();
    }

    @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter
    public int getEmptyLayoutId() {
        return R.layout.ease_layout_empty_list_invisible;
    }

    @Override // com.hyphenate.easeui.adapter.EaseBaseDelegateAdapter, com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter
    public EaseBaseRecyclerViewAdapter.ViewHolder getViewHolder(ViewGroup viewGroup, int i2) {
        EaseAdapterDelegate adapterDelegate = getAdapterDelegate(i2);
        if (adapterDelegate instanceof EaseMessageAdapterDelegate) {
            ((EaseMessageAdapterDelegate) adapterDelegate).setListItemClickListener(this.itemClickListener);
        }
        return super.getViewHolder(viewGroup, i2);
    }

    @Override // com.hyphenate.easeui.adapter.EaseBaseDelegateAdapter
    public EaseBaseDelegateAdapter setFallbackDelegate(EaseAdapterDelegate easeAdapterDelegate) {
        try {
            EaseAdapterDelegate easeAdapterDelegate2 = (EaseAdapterDelegate) easeAdapterDelegate.clone();
            easeAdapterDelegate2.setTag(EMMessage.Direct.RECEIVE.name());
            if (easeAdapterDelegate2 instanceof EaseMessageAdapterDelegate) {
                ((EaseMessageAdapterDelegate) easeAdapterDelegate2).setListItemClickListener(this.itemClickListener);
            }
            super.setFallbackDelegate(easeAdapterDelegate2);
        } catch (CloneNotSupportedException e2) {
            e2.printStackTrace();
        }
        easeAdapterDelegate.setTag(EMMessage.Direct.SEND.name());
        if (easeAdapterDelegate instanceof EaseMessageAdapterDelegate) {
            ((EaseMessageAdapterDelegate) easeAdapterDelegate).setListItemClickListener(this.itemClickListener);
        }
        return super.setFallbackDelegate(easeAdapterDelegate);
    }

    public void setListItemClickListener(MessageListItemClickListener messageListItemClickListener) {
        this.itemClickListener = messageListItemClickListener;
    }
}

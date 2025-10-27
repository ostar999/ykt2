package com.easefun.polyv.livecloudclass.modules.chatroom.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.chatroom.adapter.holder.PLVLCMessageViewHolder;
import com.easefun.polyv.livecloudclass.modules.chatroom.adapter.holder.PLVLCRewardViewHolder;
import com.easefun.polyv.livecommon.module.modules.chatroom.PLVSpecialTypeTag;
import com.easefun.polyv.livecommon.module.modules.chatroom.holder.PLVChatMessageBaseViewHolder;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livecommon.ui.widget.itemview.adapter.PLVBaseAdapter;
import com.easefun.polyv.livecommon.ui.widget.itemview.holder.PLVBaseViewHolder;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.socket.event.PLVBaseEvent;
import com.plv.socket.event.chat.IPLVIdEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVLCMessageAdapter extends PLVBaseAdapter<PLVBaseViewData, PLVBaseViewHolder<PLVBaseViewData, PLVLCMessageAdapter>> {
    public static final int DISPLAY_DATA_TYPE_FOCUS_MODE = 3;
    public static final int DISPLAY_DATA_TYPE_FULL = 1;
    public static final int DISPLAY_DATA_TYPE_SPECIAL = 2;
    private List<PLVBaseViewData> dataList;
    private List<PLVBaseViewData> focusModeDataList;
    private boolean isLandscapeLayout;
    private int msgIndex;
    private OnViewActionListener onViewActionListener;
    private int displayDataType = 1;
    private List<PLVBaseViewData> fullDataList = new ArrayList();
    private List<PLVBaseViewData> specialDataList = new ArrayList();

    public interface OnViewActionListener {
        void onChatImgClick(int i2, View view, String str, boolean z2);
    }

    public PLVLCMessageAdapter() {
        ArrayList arrayList = new ArrayList();
        this.focusModeDataList = arrayList;
        int i2 = this.displayDataType;
        if (i2 == 1) {
            this.dataList = this.fullDataList;
        } else if (i2 == 2) {
            this.dataList = this.specialDataList;
        } else {
            this.dataList = arrayList;
        }
    }

    private int remove(String str, List<PLVBaseViewData> list) {
        Iterator<PLVBaseViewData> it = list.iterator();
        int i2 = -1;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            PLVBaseViewData next = it.next();
            i2++;
            if ((next.getData() instanceof IPLVIdEvent) && str.equals(((IPLVIdEvent) next.getData()).getId())) {
                list.remove(next);
                break;
            }
        }
        return i2;
    }

    public boolean addDataChangedAtLast(PLVBaseViewData pLVBaseViewData) {
        int size = this.dataList.size();
        this.fullDataList.add(pLVBaseViewData);
        if (pLVBaseViewData.getTag() instanceof PLVSpecialTypeTag) {
            this.specialDataList.add(pLVBaseViewData);
            if (!((PLVSpecialTypeTag) pLVBaseViewData.getTag()).isMySelf()) {
                this.focusModeDataList.add(pLVBaseViewData);
            }
        }
        if (this.dataList.size() == size) {
            return false;
        }
        notifyItemInserted(this.dataList.size() - 1);
        return true;
    }

    public boolean addDataListChangedAtFirst(List<PLVBaseViewData<PLVBaseEvent>> list) {
        int size = this.dataList.size();
        this.fullDataList.addAll(0, list);
        for (int size2 = list.size() - 1; size2 >= 0; size2--) {
            PLVBaseViewData<PLVBaseEvent> pLVBaseViewData = list.get(size2);
            if (pLVBaseViewData.getTag() instanceof PLVSpecialTypeTag) {
                this.specialDataList.add(0, pLVBaseViewData);
                if (!((PLVSpecialTypeTag) pLVBaseViewData.getTag()).isMySelf()) {
                    this.focusModeDataList.add(0, pLVBaseViewData);
                }
            }
        }
        if (this.dataList.size() == size) {
            return false;
        }
        notifyItemRangeInserted(0, this.dataList.size() - size);
        return true;
    }

    public boolean addDataListChangedAtHead(List<PLVBaseViewData> list) {
        int size = this.dataList.size();
        this.fullDataList.addAll(0, list);
        for (int size2 = list.size() - 1; size2 >= 0; size2--) {
            PLVBaseViewData pLVBaseViewData = list.get(size2);
            if (pLVBaseViewData.getTag() instanceof PLVSpecialTypeTag) {
                this.specialDataList.add(0, pLVBaseViewData);
                if (!((PLVSpecialTypeTag) pLVBaseViewData.getTag()).isMySelf()) {
                    this.focusModeDataList.add(0, pLVBaseViewData);
                }
            }
        }
        if (this.dataList.size() == size) {
            return false;
        }
        notifyItemRangeInserted(0, this.dataList.size() - size);
        return true;
    }

    public boolean addDataListChangedAtLast(List<PLVBaseViewData> list) {
        int size = this.dataList.size();
        this.fullDataList.addAll(list);
        for (PLVBaseViewData pLVBaseViewData : list) {
            if (pLVBaseViewData.getTag() instanceof PLVSpecialTypeTag) {
                this.specialDataList.add(pLVBaseViewData);
                if (!((PLVSpecialTypeTag) pLVBaseViewData.getTag()).isMySelf()) {
                    this.focusModeDataList.add(pLVBaseViewData);
                }
            }
        }
        if (this.dataList.size() == size) {
            return false;
        }
        notifyItemRangeInserted(size, this.dataList.size() - size);
        return true;
    }

    public void callOnChatImgClick(int i2, View view, String str, boolean z2) {
        OnViewActionListener onViewActionListener = this.onViewActionListener;
        if (onViewActionListener != null) {
            onViewActionListener.onChatImgClick(i2, view, str, z2);
        }
    }

    public void changeDisplayType(int i2) {
        if (this.displayDataType == i2) {
            return;
        }
        this.displayDataType = i2;
        if (i2 == 1) {
            this.dataList = this.fullDataList;
        } else if (i2 == 2) {
            this.dataList = this.specialDataList;
        } else if (i2 == 3) {
            this.dataList = this.focusModeDataList;
        }
        notifyDataSetChanged();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.itemview.adapter.PLVBaseAdapter
    public List<PLVBaseViewData> getDataList() {
        return this.dataList;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.dataList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i2) {
        return this.dataList.get(i2).getItemType();
    }

    public boolean removeAllDataChanged() {
        int size = this.dataList.size();
        this.fullDataList.clear();
        this.specialDataList.clear();
        this.focusModeDataList.clear();
        if (this.dataList.size() == size) {
            return false;
        }
        notifyDataSetChanged();
        return true;
    }

    public boolean removeDataChanged(int i2, int i3) {
        if (i2 < 0 || i3 <= 0) {
            return false;
        }
        int size = this.dataList.size();
        ArrayList arrayList = new ArrayList();
        for (int i4 = i3; i4 > 0; i4--) {
            arrayList.add(this.fullDataList.remove(i2));
        }
        this.specialDataList.removeAll(arrayList);
        this.focusModeDataList.removeAll(arrayList);
        if (this.dataList.size() != size) {
            if (this.displayDataType == 1) {
                notifyItemRangeRemoved(i2, i3);
            } else {
                notifyDataSetChanged();
            }
        }
        return true;
    }

    public void setLandscapeLayout(boolean z2) {
        this.isLandscapeLayout = z2;
    }

    public void setMsgIndex(int i2) {
        this.msgIndex = i2;
    }

    public void setOnViewActionListener(OnViewActionListener onViewActionListener) {
        this.onViewActionListener = onViewActionListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull PLVBaseViewHolder<PLVBaseViewData, PLVLCMessageAdapter> pLVBaseViewHolder, int i2) {
        pLVBaseViewHolder.processData(this.dataList.get(i2), i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public PLVBaseViewHolder<PLVBaseViewData, PLVLCMessageAdapter> onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
        PLVChatMessageBaseViewHolder pLVLCMessageViewHolder;
        switch (i2) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
                pLVLCMessageViewHolder = new PLVLCMessageViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(this.isLandscapeLayout ? R.layout.plvlc_chatroom_message_landscape_item : R.layout.plvlc_chatroom_message_portrait_item, viewGroup, false), this);
                break;
            case 7:
                pLVLCMessageViewHolder = new PLVLCRewardViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(this.isLandscapeLayout ? R.layout.plvlc_chatroom_reward_landscape_item : R.layout.plvlc_chatroom_reward_item, viewGroup, false), this);
                break;
            default:
                PLVCommonLog.exception(new RuntimeException("itemType error"));
                pLVLCMessageViewHolder = new PLVChatMessageBaseViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plv_horizontal_linear_layout, viewGroup, false), this);
                break;
        }
        pLVLCMessageViewHolder.setMsgIndex(this.msgIndex);
        return pLVLCMessageViewHolder;
    }

    public boolean removeDataChanged(String str) {
        boolean z2 = false;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        int size = this.dataList.size();
        int iRemove = remove(str, this.fullDataList);
        int iRemove2 = remove(str, this.specialDataList);
        int iRemove3 = remove(str, this.focusModeDataList);
        if (this.dataList.size() != size) {
            int i2 = this.displayDataType;
            z2 = true;
            if (i2 != 1) {
                iRemove = i2 == 2 ? iRemove2 : i2 == 3 ? iRemove3 : -1;
            }
            notifyItemRemoved(iRemove);
        }
        return z2;
    }
}

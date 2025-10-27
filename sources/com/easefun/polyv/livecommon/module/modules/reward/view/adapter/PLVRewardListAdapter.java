package com.easefun.polyv.livecommon.module.modules.reward.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livecommon.ui.widget.itemview.adapter.PLVBaseAdapter;
import com.easefun.polyv.livecommon.ui.widget.itemview.holder.PLVBaseViewHolder;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVRewardListAdapter extends PLVBaseAdapter<PLVBaseViewData, PLVBaseViewHolder<PLVBaseViewData, PLVRewardListAdapter>> {
    public static final int ITEM_CASH_REWARD = 3;
    public static final int ITEM_GIFT_CASH_REWARD = 2;
    public static final int ITEM_GIFT_POINT_REWARD = 1;

    @Deprecated
    public static final int ITEM_PROP_REWARD = 3;
    private List<PLVBaseViewData> dataList;
    private boolean isLandscape;
    private OnCheckItemListener onCheckItemListener;
    private PLVBaseViewData selectData;

    public interface OnCheckItemListener {
        void onItemCheck(PLVBaseViewData selectData, int position);
    }

    public PLVRewardListAdapter() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectItem(int position) {
        PLVBaseViewData pLVBaseViewData = this.selectData;
        if (pLVBaseViewData != null) {
            int iIntValue = ((Integer) pLVBaseViewData.getTag()).intValue();
            this.selectData.setTag(Integer.valueOf(position));
            notifyItemChanged(iIntValue);
        }
        PLVBaseViewData pLVBaseViewData2 = this.dataList.get(position);
        this.selectData = pLVBaseViewData2;
        pLVBaseViewData2.setTag(Integer.valueOf(position));
        notifyItemChanged(position);
    }

    public void clearSelectState() {
        PLVBaseViewData pLVBaseViewData = this.selectData;
        if (pLVBaseViewData != null) {
            int iIntValue = ((Integer) pLVBaseViewData.getTag()).intValue();
            this.selectData.setTag(-1);
            notifyItemChanged(iIntValue);
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.itemview.adapter.PLVBaseAdapter
    public List<PLVBaseViewData> getDataList() {
        return this.dataList;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<PLVBaseViewData> list = this.dataList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        return this.dataList.get(position).getItemType();
    }

    public PLVBaseViewData getSelectData() {
        return this.selectData;
    }

    public void setDataList(List<PLVBaseViewData> dataList) {
        this.dataList = dataList;
    }

    public void setOnCheckItemListener(OnCheckItemListener onCheckItemListener) {
        this.onCheckItemListener = onCheckItemListener;
    }

    public PLVRewardListAdapter(boolean isLandscape) {
        this.isLandscape = isLandscape;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull final PLVBaseViewHolder<PLVBaseViewData, PLVRewardListAdapter> holder, final int position) {
        holder.processData(this.dataList.get(position), position);
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.adapter.PLVRewardListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                PLVRewardListAdapter.this.selectItem(position);
                if (PLVRewardListAdapter.this.onCheckItemListener != null) {
                    PLVRewardListAdapter.this.onCheckItemListener.onItemCheck(PLVRewardListAdapter.this.selectData, position);
                }
            }
        });
        PLVBaseViewData pLVBaseViewData = this.selectData;
        if (pLVBaseViewData == null) {
            holder.itemView.setSelected(false);
        } else {
            holder.itemView.setSelected(((Integer) pLVBaseViewData.getTag()).intValue() == position);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public PLVBaseViewHolder<PLVBaseViewData, PLVRewardListAdapter> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1 || viewType == 2) {
            return !this.isLandscape ? new PLVRewardPointViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.plv_point_reward_item_portrait, parent, false), this) : new PLVRewardPointViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.plv_point_reward_item_landscape, parent, false), this);
        }
        return null;
    }
}

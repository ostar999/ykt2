package com.easefun.polyv.livecommon.module.modules.previous.customview;

import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livecommon.ui.widget.itemview.adapter.PLVBaseAdapter;
import com.easefun.polyv.livecommon.ui.widget.itemview.holder.PLVBaseViewHolder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class PLVChapterAdapter<Data extends PLVBaseViewData, Holder extends PLVBaseViewHolder> extends PLVBaseAdapter<Data, Holder> {
    private OnViewActionListener listener;
    private List<Data> dataList = new ArrayList();
    private int currentPosition = 0;

    public interface OnViewActionListener {
        void changeVideoSeekClick(int timeStamp);
    }

    private void updateItem(int newPosition) {
        int i2 = this.currentPosition;
        this.currentPosition = newPosition;
        notifyItemChanged(i2);
        notifyItemChanged(this.currentPosition);
    }

    public void callChangeVideoSeekClick(int timeStamp, int position) {
        OnViewActionListener onViewActionListener = this.listener;
        if (onViewActionListener != null) {
            onViewActionListener.changeVideoSeekClick(timeStamp);
            updateItem(position);
        }
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.itemview.adapter.PLVBaseAdapter
    public List<Data> getDataList() {
        return this.dataList;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.dataList.size();
    }

    public void setDataList(List<Data> list) {
        if (list.isEmpty()) {
            return;
        }
        this.dataList.clear();
        this.dataList.addAll(list);
        notifyDataSetChanged();
    }

    public void setOnViewActionListener(OnViewActionListener actionListener) {
        this.listener = actionListener;
    }

    public void updataItmeTime(int position) {
        if (this.currentPosition != position) {
            updateItem(position);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.processData(this.dataList.get(position), position);
    }
}

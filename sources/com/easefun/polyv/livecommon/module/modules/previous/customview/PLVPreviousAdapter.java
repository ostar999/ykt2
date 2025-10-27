package com.easefun.polyv.livecommon.module.modules.previous.customview;

import android.annotation.SuppressLint;
import android.view.View;
import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livecommon.ui.widget.itemview.adapter.PLVBaseAdapter;
import com.easefun.polyv.livecommon.ui.widget.itemview.holder.PLVBaseViewHolder;
import com.plv.livescenes.model.PLVPlaybackListVO;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class PLVPreviousAdapter<Data extends PLVBaseViewData, Holder extends PLVBaseViewHolder> extends PLVBaseAdapter<Data, Holder> {
    private int currentPosition = 0;
    private final List<Data> dataList = new ArrayList();
    private OnViewActionListener listener;

    public interface OnViewActionListener {
        void changeVideoVidClick(String vid);
    }

    public void callChangeVideoVidClick(View view, PLVPlaybackListVO.DataBean.ContentsBean bean, int position) {
        OnViewActionListener onViewActionListener = this.listener;
        if (onViewActionListener != null) {
            onViewActionListener.changeVideoVidClick(bean.getVideoPoolId());
            int i2 = this.currentPosition;
            this.currentPosition = position;
            notifyItemChanged(i2);
            notifyItemChanged(this.currentPosition);
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

    public void loadMore(List<Data> moreList) {
        if (moreList.isEmpty()) {
            return;
        }
        int size = this.dataList.size();
        this.dataList.addAll(moreList);
        notifyItemRangeChanged(size, this.dataList.size());
    }

    public void setCurrentPosition(int position) {
        this.currentPosition = position;
    }

    @SuppressLint({"NotifyDataSetChanged"})
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

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.processData(this.dataList.get(position), position);
    }
}

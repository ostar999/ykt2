package com.easefun.polyv.livecommon.ui.widget.itemview.holder;

import android.view.View;
import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livecommon.ui.widget.itemview.adapter.PLVBaseAdapter;

/* loaded from: classes3.dex */
public class PLVBaseViewHolder<Data extends PLVBaseViewData, Adapter extends PLVBaseAdapter> extends RecyclerView.ViewHolder {
    protected Adapter adapter;
    protected Data data;

    public PLVBaseViewHolder(View itemView, Adapter adapter) {
        super(itemView);
        this.adapter = adapter;
    }

    public <T extends View> T findViewById(@IdRes int i2) {
        return (T) this.itemView.findViewById(i2);
    }

    public int getVHPosition() {
        for (int i2 = 0; i2 < this.adapter.getDataList().size(); i2++) {
            if (this.adapter.getDataList().get(i2) == this.data) {
                return i2;
            }
        }
        return 0;
    }

    public void processData(Data data, int position) {
        this.data = data;
    }
}

package com.psychiatrygarden.widget.treenode;

import android.util.SparseArray;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes6.dex */
public class ViewHolder extends RecyclerView.ViewHolder {
    private View mConvertView;
    private SparseArray<View> mViews;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        this.mConvertView = itemView;
        this.mViews = new SparseArray<>();
    }

    public View getConvertView() {
        return this.mConvertView;
    }

    public <T extends View> T getView(int i2) {
        T t2 = (T) this.mViews.get(i2);
        if (t2 != null) {
            return t2;
        }
        T t3 = (T) this.mConvertView.findViewById(i2);
        this.mViews.put(i2, t3);
        return t3;
    }
}

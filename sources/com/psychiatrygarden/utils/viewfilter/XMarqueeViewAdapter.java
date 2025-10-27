package com.psychiatrygarden.utils.viewfilter;

import android.view.View;
import java.util.List;

/* loaded from: classes6.dex */
public abstract class XMarqueeViewAdapter<T> {
    protected List<T> mDatas;
    private OnDataChangedListener mOnDataChangedListener;

    public interface OnDataChangedListener {
        void onChanged();
    }

    public XMarqueeViewAdapter(List<T> datas) {
        this.mDatas = datas;
        if (datas == null) {
            throw new RuntimeException("XMarqueeView datas is Null");
        }
    }

    public int getItemCount() {
        List<T> list = this.mDatas;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void notifyDataChanged() {
        OnDataChangedListener onDataChangedListener = this.mOnDataChangedListener;
        if (onDataChangedListener != null) {
            onDataChangedListener.onChanged();
        }
    }

    public abstract void onBindView(View parent, View view, int position);

    public abstract View onCreateView(XMarqueeView parent);

    public void setData(List<T> datas) {
        this.mDatas = datas;
        notifyDataChanged();
    }

    public void setOnDataChangedListener(OnDataChangedListener onDataChangedListener) {
        this.mOnDataChangedListener = onDataChangedListener;
    }
}

package com.lwkandroid.widget.ngv;

import android.content.Context;
import android.view.View;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class AbsNgvAdapter<P extends View, V extends View, D> {
    private List<OnDataChangedListener<D>> mDataChangedListenerList;
    private final List<D> mDataList;
    private int mMaxDataSize;

    public interface OnDataChangedListener<D> {
        void onAllDataChanged(List<D> dataList, boolean reachLimitedSize);

        void onDataAdded(D data, int position, boolean reachLimitedSize);

        void onDataChanged(D data, int position, boolean reachLimitedSize);

        void onDataListAdded(List<D> dataList, int startPosition, boolean reachLimitedSize);

        void onDataRemoved(D data, int position, boolean reachLimitedSize);
    }

    public AbsNgvAdapter(int maxDataSize) {
        this(maxDataSize, null);
    }

    public void addData(D data) {
        addData(data, this.mDataList.size());
    }

    public void addDataChangedListener(OnDataChangedListener<D> listener) {
        if (listener == null) {
            return;
        }
        this.mDataChangedListenerList.add(listener);
    }

    public void addDataList(List<D> dataList) {
        addDataList(dataList, this.mDataList.size());
    }

    public abstract void bindContentView(V childView, D data, int position, NgvAttrOptions attrOptions);

    public abstract void bindPlusView(P plusView, NgvAttrOptions attrOptions);

    public abstract V createContentView(Context context);

    public abstract P createPlusView(Context context);

    public int getDValueToLimited() {
        return Math.max(this.mMaxDataSize - this.mDataList.size(), 0);
    }

    public List<D> getDataList() {
        return this.mDataList;
    }

    public int getMaxDataSize() {
        return this.mMaxDataSize;
    }

    public boolean isDataToLimited() {
        return this.mDataList.size() >= this.mMaxDataSize;
    }

    public void notifyAllDataChanged() {
        for (OnDataChangedListener<D> onDataChangedListener : this.mDataChangedListenerList) {
            List<D> list = this.mDataList;
            onDataChangedListener.onAllDataChanged(list, list.size() == this.mMaxDataSize);
        }
    }

    public void notifyDataAdded(D data, int position) {
        Iterator<OnDataChangedListener<D>> it = this.mDataChangedListenerList.iterator();
        while (it.hasNext()) {
            it.next().onDataAdded(data, position, this.mDataList.size() == this.mMaxDataSize);
        }
    }

    public void notifyDataChanged(D data, int position) {
        Iterator<OnDataChangedListener<D>> it = this.mDataChangedListenerList.iterator();
        while (it.hasNext()) {
            it.next().onDataChanged(data, position, this.mDataList.size() == this.mMaxDataSize);
        }
    }

    public void notifyDataListAdded(List<D> dataList, int startPosition) {
        Iterator<OnDataChangedListener<D>> it = this.mDataChangedListenerList.iterator();
        while (it.hasNext()) {
            it.next().onDataListAdded(dataList, startPosition, this.mDataList.size() == this.mMaxDataSize);
        }
    }

    public void notifyDataRemoved(D data, int position) {
        Iterator<OnDataChangedListener<D>> it = this.mDataChangedListenerList.iterator();
        while (it.hasNext()) {
            it.next().onDataRemoved(data, position, this.mDataList.size() == this.mMaxDataSize);
        }
    }

    public void removeData(int position) {
        if (position < 0 || position >= this.mDataList.size()) {
            return;
        }
        D d3 = this.mDataList.get(position);
        if (this.mDataList.remove(d3)) {
            notifyDataRemoved(d3, position);
        }
    }

    public void removeDataChangedListener(OnDataChangedListener<D> listener) {
        this.mDataChangedListenerList.remove(listener);
    }

    public void replaceData(int position, D data) {
        if (position < 0 || position >= this.mDataList.size()) {
            return;
        }
        this.mDataList.remove(position);
        this.mDataList.add(position, data);
        notifyDataChanged(data, position);
    }

    public void setDataList(List<D> dataList) {
        this.mDataList.clear();
        if (dataList != null) {
            int size = this.mMaxDataSize - getDataList().size();
            if (dataList.size() > size) {
                this.mDataList.addAll(dataList.subList(0, size - 1));
            } else {
                this.mDataList.addAll(dataList);
            }
        }
        notifyAllDataChanged();
    }

    public void setMaxDataSize(int maxDataSize) {
        this.mMaxDataSize = maxDataSize;
    }

    public AbsNgvAdapter(int maxDataSize, List<D> dataList) {
        this.mDataList = new LinkedList();
        this.mDataChangedListenerList = new LinkedList();
        this.mMaxDataSize = maxDataSize;
        setDataList(dataList);
    }

    public void addData(D data, int position) {
        if (data == null || getDataList().size() == this.mMaxDataSize) {
            return;
        }
        if (position < 0) {
            position = 0;
        }
        if (position > this.mDataList.size()) {
            position = this.mDataList.size();
        }
        this.mDataList.add(position, data);
        notifyDataAdded(data, position);
    }

    public void addDataList(List<D> dataList, int startPosition) {
        if (dataList == null) {
            return;
        }
        int size = this.mMaxDataSize - getDataList().size();
        if (startPosition < 0) {
            startPosition = 0;
        }
        int iMin = Math.min(startPosition, getDataList().size());
        if (dataList.size() > size) {
            this.mDataList.addAll(iMin, dataList.subList(0, size - 1));
        } else {
            this.mDataList.addAll(iMin, dataList);
        }
        notifyDataListAdded(dataList, iMin);
    }

    public void removeData(D data) {
        int iIndexOf = this.mDataList.indexOf(data);
        if (this.mDataList.remove(data)) {
            notifyDataRemoved(data, iIndexOf);
        }
    }
}

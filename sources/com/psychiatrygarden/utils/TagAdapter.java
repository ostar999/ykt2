package com.psychiatrygarden.utils;

import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes6.dex */
public abstract class TagAdapter<T> {

    @Deprecated
    private HashSet<Integer> mCheckedPosList = new HashSet<>();
    private OnDataChangedListener mOnDataChangedListener;
    private List<T> mTagDatas;

    public interface OnDataChangedListener {
        void onChanged();
    }

    public TagAdapter(List<T> datas) {
        this.mTagDatas = datas;
    }

    public int getCount() {
        List<T> list = this.mTagDatas;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public T getItem(int position) {
        return this.mTagDatas.get(position);
    }

    @Deprecated
    public HashSet<Integer> getPreCheckedList() {
        return this.mCheckedPosList;
    }

    public abstract View getView(FlowLayout parent, int position, T t2);

    public void notifyDataChanged() {
        OnDataChangedListener onDataChangedListener = this.mOnDataChangedListener;
        if (onDataChangedListener != null) {
            onDataChangedListener.onChanged();
        }
    }

    public void onSelected(int position, View view, boolean isClick) {
        Log.d("zhy", "onSelected " + position);
    }

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.mOnDataChangedListener = listener;
    }

    public boolean setSelected(int position, T t2) {
        return false;
    }

    @Deprecated
    public void setSelectedList(int... poses) {
        HashSet hashSet = new HashSet();
        for (int i2 : poses) {
            hashSet.add(Integer.valueOf(i2));
        }
        setSelectedList(hashSet);
    }

    public void unSelected(int position, View view, boolean isClick) {
        Log.d("zhy", "unSelected " + position);
    }

    public void onSelected(int position, View view) {
        Log.d("zhy", "onSelected " + position);
    }

    public void unSelected(int position, View view) {
        Log.d("zhy", "unSelected " + position);
    }

    @Deprecated
    public TagAdapter(T[] datas) {
        this.mTagDatas = new ArrayList(Arrays.asList(datas));
    }

    @Deprecated
    public void setSelectedList(Set<Integer> set) {
        this.mCheckedPosList.clear();
        if (set != null) {
            this.mCheckedPosList.addAll(set);
        }
        notifyDataChanged();
    }
}

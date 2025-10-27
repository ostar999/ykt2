package com.psychiatrygarden.activity.purchase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

/* loaded from: classes5.dex */
public abstract class CommAdapter<T> extends BaseAdapter {
    LayoutInflater lInflater;
    int layoutId;
    protected List<T> mData;
    Context mcontext;

    public CommAdapter(List<T> mData, Context mcontext, int layoutId) {
        this.mData = mData;
        this.mcontext = mcontext;
        this.lInflater = LayoutInflater.from(mcontext);
        this.layoutId = layoutId;
    }

    public abstract void convert(ViewHolder vHolder, T t2, int position);

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mData.size();
    }

    @Override // android.widget.Adapter
    public T getItem(int position) {
        return this.mData.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(this.mcontext, convertView, parent, this.layoutId, position);
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    public List<T> getmData() {
        return this.mData;
    }

    public CommAdapter(Context mcontext) {
        this.mcontext = mcontext;
        this.lInflater = LayoutInflater.from(mcontext);
    }
}

package com.aliyun.svideo.common.baseAdapter.provider;

import android.content.Context;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class BaseItemProvider<T, V extends BaseViewHolder> {
    public Context mContext;
    public List<T> mData;

    public abstract void convert(V v2, T t2, int i2);

    public abstract int layout();

    public void onClick(V v2, T t2, int i2) {
    }

    public boolean onLongClick(V v2, T t2, int i2) {
        return false;
    }

    public abstract int viewType();
}

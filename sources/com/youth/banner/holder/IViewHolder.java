package com.youth.banner.holder;

import android.view.ViewGroup;

/* loaded from: classes8.dex */
public interface IViewHolder<T, VH> {
    void onBindView(VH vh, T t2, int i2, int i3);

    VH onCreateHolder(ViewGroup viewGroup, int i2);
}

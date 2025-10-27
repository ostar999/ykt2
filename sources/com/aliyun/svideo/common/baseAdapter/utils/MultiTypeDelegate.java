package com.aliyun.svideo.common.baseAdapter.utils;

import android.util.SparseIntArray;
import androidx.annotation.LayoutRes;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class MultiTypeDelegate<T> {
    private static final int DEFAULT_VIEW_TYPE = -255;
    private boolean autoMode;
    private SparseIntArray layouts;
    private boolean selfMode;

    public MultiTypeDelegate(SparseIntArray sparseIntArray) {
        this.layouts = sparseIntArray;
    }

    private void addItemType(int i2, @LayoutRes int i3) {
        if (this.layouts == null) {
            this.layouts = new SparseIntArray();
        }
        this.layouts.put(i2, i3);
    }

    private void checkMode(boolean z2) {
        if (z2) {
            throw new IllegalArgumentException("Don't mess two register mode");
        }
    }

    public final int getDefItemViewType(List<T> list, int i2) {
        T t2 = list.get(i2);
        return t2 != null ? getItemType(t2) : DEFAULT_VIEW_TYPE;
    }

    public abstract int getItemType(T t2);

    public final int getLayoutId(int i2) {
        return this.layouts.get(i2, com.aliyun.svideo.common.baseAdapter.delegate.MultiTypeDelegate.TYPE_NOT_FOUND);
    }

    public MultiTypeDelegate registerItemType(int i2, @LayoutRes int i3) {
        this.selfMode = true;
        checkMode(this.autoMode);
        addItemType(i2, i3);
        return this;
    }

    public MultiTypeDelegate registerItemTypeAutoIncrease(@LayoutRes int... iArr) {
        this.autoMode = true;
        checkMode(this.selfMode);
        for (int i2 = 0; i2 < iArr.length; i2++) {
            addItemType(i2, iArr[i2]);
        }
        return this;
    }

    public MultiTypeDelegate() {
    }
}

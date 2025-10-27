package com.chad.library.adapter.base.diff;

import androidx.annotation.NonNull;
import java.util.List;

/* loaded from: classes2.dex */
public interface ListChangeListener<T> {
    void onCurrentListChanged(@NonNull List<T> list, @NonNull List<T> list2);
}

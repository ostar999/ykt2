package com.google.android.gms.common.data;

import android.os.Bundle;
import androidx.databinding.ObservableArrayList;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public final class DataBufferUtils {

    @KeepForSdk
    public static final String KEY_NEXT_PAGE_TOKEN = "next_page_token";

    @KeepForSdk
    public static final String KEY_PREV_PAGE_TOKEN = "prev_page_token";

    private DataBufferUtils() {
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freezeAndClose(DataBuffer<E> dataBuffer) {
        ObservableArrayList observableArrayList = (ArrayList<T>) new ArrayList(dataBuffer.getCount());
        try {
            Iterator<E> it = dataBuffer.iterator();
            while (it.hasNext()) {
                observableArrayList.add(it.next().freeze());
            }
            return observableArrayList;
        } finally {
            dataBuffer.close();
        }
    }

    public static boolean hasData(DataBuffer<?> dataBuffer) {
        return dataBuffer != null && dataBuffer.getCount() > 0;
    }

    public static boolean hasNextPage(DataBuffer<?> dataBuffer) {
        Bundle metadata = dataBuffer.getMetadata();
        return (metadata == null || metadata.getString(KEY_NEXT_PAGE_TOKEN) == null) ? false : true;
    }

    public static boolean hasPrevPage(DataBuffer<?> dataBuffer) {
        Bundle metadata = dataBuffer.getMetadata();
        return (metadata == null || metadata.getString(KEY_PREV_PAGE_TOKEN) == null) ? false : true;
    }
}

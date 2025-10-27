package com.google.android.gms.common.data;

import androidx.databinding.ObservableArrayList;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public final class FreezableUtils {
    public static <T, E extends Freezable<T>> ArrayList<T> freeze(ArrayList<E> arrayList) {
        ObservableArrayList observableArrayList = (ArrayList<T>) new ArrayList(arrayList.size());
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            observableArrayList.add(arrayList.get(i2).freeze());
        }
        return observableArrayList;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freezeIterable(Iterable<E> iterable) {
        ObservableArrayList observableArrayList = (ArrayList<T>) new ArrayList();
        Iterator<E> it = iterable.iterator();
        while (it.hasNext()) {
            observableArrayList.add(it.next().freeze());
        }
        return observableArrayList;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freeze(E[] eArr) {
        ObservableArrayList observableArrayList = (ArrayList<T>) new ArrayList(eArr.length);
        for (E e2 : eArr) {
            observableArrayList.add(e2.freeze());
        }
        return observableArrayList;
    }
}

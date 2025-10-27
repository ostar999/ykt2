package com.github.mikephil.charting.utils;

import com.github.mikephil.charting.data.Entry;
import java.util.Comparator;

/* loaded from: classes3.dex */
public class EntryXComparator implements Comparator<Entry> {
    @Override // java.util.Comparator
    public int compare(Entry entry, Entry entry2) {
        float x2 = entry.getX() - entry2.getX();
        if (x2 == 0.0f) {
            return 0;
        }
        return x2 > 0.0f ? 1 : -1;
    }
}

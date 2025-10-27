package com.bumptech.glide.repackaged.com.google.common.collect;

import java.util.Iterator;
import java.util.Set;

/* loaded from: classes2.dex */
public final class Sets {
    public static boolean equalsImpl(Set<?> set, Object obj) {
        if (set == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set2 = (Set) obj;
            try {
                if (set.size() == set2.size()) {
                    if (set.containsAll(set2)) {
                        return true;
                    }
                }
                return false;
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false;
    }

    public static int hashCodeImpl(Set<?> set) {
        Iterator<?> it = set.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            Object next = it.next();
            i2 = ~(~(i2 + (next != null ? next.hashCode() : 0)));
        }
        return i2;
    }
}

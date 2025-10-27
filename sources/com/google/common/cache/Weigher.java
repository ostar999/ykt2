package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
/* loaded from: classes4.dex */
public interface Weigher<K, V> {
    int weigh(K k2, V v2);
}

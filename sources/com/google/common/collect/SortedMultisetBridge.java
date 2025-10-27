package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import java.util.SortedSet;

@GwtIncompatible
/* loaded from: classes4.dex */
interface SortedMultisetBridge<E> extends Multiset<E> {
    @Override // com.google.common.collect.Multiset
    SortedSet<E> elementSet();
}

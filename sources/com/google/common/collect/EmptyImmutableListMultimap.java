package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true)
/* loaded from: classes4.dex */
class EmptyImmutableListMultimap extends ImmutableListMultimap<Object, Object> {
    static final EmptyImmutableListMultimap INSTANCE = new EmptyImmutableListMultimap();
    private static final long serialVersionUID = 0;

    private EmptyImmutableListMultimap() {
        super(ImmutableMap.of(), 0);
    }

    private Object readResolve() {
        return INSTANCE;
    }
}

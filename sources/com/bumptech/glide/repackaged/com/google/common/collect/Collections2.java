package com.bumptech.glide.repackaged.com.google.common.collect;

import com.bumptech.glide.repackaged.com.google.common.base.Joiner;
import java.util.Collection;

/* loaded from: classes2.dex */
public final class Collections2 {
    static final Joiner STANDARD_JOINER = Joiner.on(", ").useForNull("null");

    public static <T> Collection<T> cast(Iterable<T> iterable) {
        return (Collection) iterable;
    }
}

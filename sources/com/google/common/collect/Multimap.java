package com.google.common.collect;

import androidx.exifinterface.media.ExifInterface;
import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CompatibleWith;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
public interface Multimap<K, V> {
    Map<K, Collection<V>> asMap();

    void clear();

    boolean containsEntry(@NullableDecl @CompatibleWith("K") Object obj, @NullableDecl @CompatibleWith(ExifInterface.GPS_MEASUREMENT_INTERRUPTED) Object obj2);

    boolean containsKey(@NullableDecl @CompatibleWith("K") Object obj);

    boolean containsValue(@NullableDecl @CompatibleWith(ExifInterface.GPS_MEASUREMENT_INTERRUPTED) Object obj);

    Collection<Map.Entry<K, V>> entries();

    boolean equals(@NullableDecl Object obj);

    Collection<V> get(@NullableDecl K k2);

    int hashCode();

    boolean isEmpty();

    Set<K> keySet();

    Multiset<K> keys();

    @CanIgnoreReturnValue
    boolean put(@NullableDecl K k2, @NullableDecl V v2);

    @CanIgnoreReturnValue
    boolean putAll(Multimap<? extends K, ? extends V> multimap);

    @CanIgnoreReturnValue
    boolean putAll(@NullableDecl K k2, Iterable<? extends V> iterable);

    @CanIgnoreReturnValue
    boolean remove(@NullableDecl @CompatibleWith("K") Object obj, @NullableDecl @CompatibleWith(ExifInterface.GPS_MEASUREMENT_INTERRUPTED) Object obj2);

    @CanIgnoreReturnValue
    Collection<V> removeAll(@NullableDecl @CompatibleWith("K") Object obj);

    @CanIgnoreReturnValue
    Collection<V> replaceValues(@NullableDecl K k2, Iterable<? extends V> iterable);

    int size();

    Collection<V> values();
}

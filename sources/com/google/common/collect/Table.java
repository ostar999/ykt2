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
public interface Table<R, C, V> {

    public interface Cell<R, C, V> {
        boolean equals(@NullableDecl Object obj);

        @NullableDecl
        C getColumnKey();

        @NullableDecl
        R getRowKey();

        @NullableDecl
        V getValue();

        int hashCode();
    }

    Set<Cell<R, C, V>> cellSet();

    void clear();

    Map<R, V> column(C c3);

    Set<C> columnKeySet();

    Map<C, Map<R, V>> columnMap();

    boolean contains(@NullableDecl @CompatibleWith("R") Object obj, @NullableDecl @CompatibleWith("C") Object obj2);

    boolean containsColumn(@NullableDecl @CompatibleWith("C") Object obj);

    boolean containsRow(@NullableDecl @CompatibleWith("R") Object obj);

    boolean containsValue(@NullableDecl @CompatibleWith(ExifInterface.GPS_MEASUREMENT_INTERRUPTED) Object obj);

    boolean equals(@NullableDecl Object obj);

    V get(@NullableDecl @CompatibleWith("R") Object obj, @NullableDecl @CompatibleWith("C") Object obj2);

    int hashCode();

    boolean isEmpty();

    @CanIgnoreReturnValue
    @NullableDecl
    V put(R r2, C c3, V v2);

    void putAll(Table<? extends R, ? extends C, ? extends V> table);

    @CanIgnoreReturnValue
    @NullableDecl
    V remove(@NullableDecl @CompatibleWith("R") Object obj, @NullableDecl @CompatibleWith("C") Object obj2);

    Map<C, V> row(R r2);

    Set<R> rowKeySet();

    Map<R, Map<C, V>> rowMap();

    int size();

    Collection<V> values();
}

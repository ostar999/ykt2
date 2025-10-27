package cn.hutool.core.map.multi;

import cn.hutool.core.lang.func.Consumer3;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public interface Table<R, C, V> extends Iterable<Cell<R, C, V>> {

    public interface Cell<R, C, V> {
        C getColumnKey();

        R getRowKey();

        V getValue();
    }

    Set<Cell<R, C, V>> cellSet();

    void clear();

    Set<C> columnKeySet();

    List<C> columnKeys();

    Map<C, Map<R, V>> columnMap();

    boolean contains(R r2, C c3);

    boolean containsColumn(C c3);

    boolean containsRow(R r2);

    boolean containsValue(V v2);

    void forEach(Consumer3<? super R, ? super C, ? super V> consumer3);

    V get(R r2, C c3);

    Map<R, V> getColumn(C c3);

    Map<C, V> getRow(R r2);

    boolean isEmpty();

    V put(R r2, C c3, V v2);

    void putAll(Table<? extends R, ? extends C, ? extends V> table);

    V remove(R r2, C c3);

    Set<R> rowKeySet();

    Map<R, Map<C, V>> rowMap();

    int size();

    Collection<V> values();
}

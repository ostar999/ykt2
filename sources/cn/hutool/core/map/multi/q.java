package cn.hutool.core.map.multi;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.lang.func.Consumer3;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.map.multi.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/* loaded from: classes.dex */
public final /* synthetic */ class q<R, C, V> {
    public static Set a(Table table) {
        return (Set) Opt.ofNullable(table.columnMap()).map(new l()).get();
    }

    public static List b(Table table) {
        Map<C, Map<R, V>> mapColumnMap = table.columnMap();
        if (MapUtil.isEmpty(mapColumnMap)) {
            return ListUtil.empty();
        }
        ArrayList arrayList = new ArrayList(mapColumnMap.size());
        Iterator<Map.Entry<C, Map<R, V>>> it = mapColumnMap.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getKey());
        }
        return arrayList;
    }

    public static boolean c(Table table, Object obj, final Object obj2) {
        return ((Boolean) Opt.ofNullable(table.getRow(obj)).map(new Function() { // from class: cn.hutool.core.map.multi.j
            @Override // java.util.function.Function
            public final Object apply(Object obj3) {
                return Boolean.valueOf(((Map) obj3).containsKey(obj2));
            }
        }).orElse(Boolean.FALSE)).booleanValue();
    }

    public static boolean d(Table table, final Object obj) {
        return ((Boolean) Opt.ofNullable(table.columnMap()).map(new Function() { // from class: cn.hutool.core.map.multi.k
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return Boolean.valueOf(((Map) obj2).containsKey(obj));
            }
        }).get()).booleanValue();
    }

    public static boolean e(Table table, final Object obj) {
        return ((Boolean) Opt.ofNullable(table.rowMap()).map(new Function() { // from class: cn.hutool.core.map.multi.p
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return Boolean.valueOf(((Map) obj2).containsKey(obj));
            }
        }).get()).booleanValue();
    }

    public static boolean f(Table table, Object obj) {
        Collection collection = (Collection) Opt.ofNullable(table.rowMap()).map(new cn.hutool.core.map.l()).get();
        if (collection == null) {
            return false;
        }
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (((Map) it.next()).containsValue(obj)) {
                return true;
            }
        }
        return false;
    }

    public static void g(Table table, Consumer3 consumer3) {
        for (Table.Cell<R, C, V> cell : table) {
            consumer3.accept(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
        }
    }

    public static Object h(Table table, Object obj, final Object obj2) {
        return Opt.ofNullable(table.getRow(obj)).map(new Function() { // from class: cn.hutool.core.map.multi.m
            @Override // java.util.function.Function
            public final Object apply(Object obj3) {
                return ((Map) obj3).get(obj2);
            }
        }).get();
    }

    public static Map i(Table table, final Object obj) {
        return (Map) Opt.ofNullable(table.columnMap()).map(new Function() { // from class: cn.hutool.core.map.multi.o
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return q.r(obj, (Map) obj2);
            }
        }).get();
    }

    public static Map j(Table table, final Object obj) {
        return (Map) Opt.ofNullable(table.rowMap()).map(new Function() { // from class: cn.hutool.core.map.multi.n
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return q.s(obj, (Map) obj2);
            }
        }).get();
    }

    public static void k(Table table, Table table2) {
        if (table2 != null) {
            for (Table.Cell<R, C, V> cell : table2.cellSet()) {
                table.put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
            }
        }
    }

    public static Set l(Table table) {
        return (Set) Opt.ofNullable(table.rowMap()).map(new l()).get();
    }

    public static int m(Table table) {
        Map<R, Map<C, V>> mapRowMap = table.rowMap();
        int size = 0;
        if (MapUtil.isEmpty(mapRowMap)) {
            return 0;
        }
        Iterator<Map<C, V>> it = mapRowMap.values().iterator();
        while (it.hasNext()) {
            size += it.next().size();
        }
        return size;
    }

    public static /* synthetic */ Map r(Object obj, Map map) {
        return (Map) map.get(obj);
    }

    public static /* synthetic */ Map s(Object obj, Map map) {
        return (Map) map.get(obj);
    }
}

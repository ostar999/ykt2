package com.squareup.wire.internal;

import com.squareup.wire.ProtoAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public final class Internal {
    private Internal() {
    }

    public static void checkElementsNotNull(List<?> list) {
        if (list == null) {
            throw new NullPointerException("list == null");
        }
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (list.get(i2) == null) {
                throw new NullPointerException("Element at index " + i2 + " is null");
            }
        }
    }

    public static <T> List<T> copyOf(String str, List<T> list) {
        if (list != null) {
            return (list == Collections.emptyList() || (list instanceof ImmutableList)) ? new MutableOnWriteList(list) : new ArrayList(list);
        }
        throw new NullPointerException(str + " == null");
    }

    public static int countNonNull(Object obj, Object obj2) {
        return (obj != null ? 1 : 0) + (obj2 == null ? 0 : 1);
    }

    public static int countNonNull(Object obj, Object obj2, Object obj3) {
        return (obj != null ? 1 : 0) + (obj2 != null ? 1 : 0) + (obj3 == null ? 0 : 1);
    }

    public static int countNonNull(Object obj, Object obj2, Object obj3, Object obj4, Object... objArr) {
        int i2 = obj != null ? 1 : 0;
        if (obj2 != null) {
            i2++;
        }
        if (obj3 != null) {
            i2++;
        }
        if (obj4 != null) {
            i2++;
        }
        for (Object obj5 : objArr) {
            if (obj5 != null) {
                i2++;
            }
        }
        return i2;
    }

    public static boolean equals(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static <T> List<T> immutableCopyOf(String str, List<T> list) {
        if (list == null) {
            throw new NullPointerException(str + " == null");
        }
        if (list instanceof MutableOnWriteList) {
            list = ((MutableOnWriteList) list).mutableList;
        }
        if (list == Collections.emptyList() || (list instanceof ImmutableList)) {
            return list;
        }
        ImmutableList immutableList = new ImmutableList(list);
        if (!immutableList.contains(null)) {
            return immutableList;
        }
        throw new IllegalArgumentException(str + ".contains(null)");
    }

    public static IllegalStateException missingRequiredFields(Object... objArr) {
        StringBuilder sb = new StringBuilder();
        int length = objArr.length;
        String str = "";
        for (int i2 = 0; i2 < length; i2 += 2) {
            if (objArr[i2] == null) {
                if (sb.length() > 0) {
                    str = "s";
                }
                sb.append("\n  ");
                sb.append(objArr[i2 + 1]);
            }
        }
        throw new IllegalStateException("Required field" + str + " not set:" + ((Object) sb));
    }

    public static <T> List<T> newMutableList() {
        return new MutableOnWriteList(Collections.emptyList());
    }

    public static <K, V> Map<K, V> newMutableMap() {
        return new LinkedHashMap();
    }

    public static <T> void redactElements(List<T> list, ProtoAdapter<T> protoAdapter) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            list.set(i2, protoAdapter.redact(list.get(i2)));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> void redactElements(Map<?, T> map, ProtoAdapter<T> protoAdapter) {
        for (Map.Entry entry : map.entrySet()) {
            entry.setValue(protoAdapter.redact(entry.getValue()));
        }
    }

    public static void checkElementsNotNull(Map<?, ?> map) {
        if (map != null) {
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (entry.getKey() != null) {
                    if (entry.getValue() == null) {
                        throw new NullPointerException("Value for key " + entry.getKey() + " is null");
                    }
                } else {
                    throw new NullPointerException("map.containsKey(null)");
                }
            }
            return;
        }
        throw new NullPointerException("map == null");
    }

    public static <K, V> Map<K, V> copyOf(String str, Map<K, V> map) {
        if (map != null) {
            return new LinkedHashMap(map);
        }
        throw new NullPointerException(str + " == null");
    }

    public static <K, V> Map<K, V> immutableCopyOf(String str, Map<K, V> map) {
        if (map != null) {
            if (map.isEmpty()) {
                return Collections.emptyMap();
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap(map);
            if (!linkedHashMap.containsKey(null)) {
                if (!linkedHashMap.containsValue(null)) {
                    return Collections.unmodifiableMap(linkedHashMap);
                }
                throw new IllegalArgumentException(str + ".containsValue(null)");
            }
            throw new IllegalArgumentException(str + ".containsKey(null)");
        }
        throw new NullPointerException(str + " == null");
    }
}

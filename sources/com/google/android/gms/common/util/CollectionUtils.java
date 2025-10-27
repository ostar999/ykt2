package com.google.android.gms.common.util;

import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@KeepForSdk
/* loaded from: classes3.dex */
public final class CollectionUtils {
    private CollectionUtils() {
    }

    @KeepForSdk
    public static boolean isEmpty(@Nullable Collection<?> collection) {
        if (collection == null) {
            return true;
        }
        return collection.isEmpty();
    }

    @KeepForSdk
    @Deprecated
    public static <T> List<T> listOf() {
        return Collections.emptyList();
    }

    @KeepForSdk
    public static <K, V> Map<K, V> mapOf(K k2, V v2, K k3, V v3, K k4, V v4) {
        Map mapZzb = zzb(3, false);
        mapZzb.put(k2, v2);
        mapZzb.put(k3, v3);
        mapZzb.put(k4, v4);
        return Collections.unmodifiableMap(mapZzb);
    }

    @KeepForSdk
    public static <K, V> Map<K, V> mapOfKeyValueArrays(K[] kArr, V[] vArr) {
        if (kArr.length != vArr.length) {
            int length = kArr.length;
            int length2 = vArr.length;
            StringBuilder sb = new StringBuilder(66);
            sb.append("Key and values array lengths not equal: ");
            sb.append(length);
            sb.append(" != ");
            sb.append(length2);
            throw new IllegalArgumentException(sb.toString());
        }
        int length3 = kArr.length;
        if (length3 == 0) {
            return Collections.emptyMap();
        }
        if (length3 == 1) {
            return Collections.singletonMap(kArr[0], vArr[0]);
        }
        Map mapZzb = zzb(kArr.length, false);
        for (int i2 = 0; i2 < kArr.length; i2++) {
            mapZzb.put(kArr[i2], vArr[i2]);
        }
        return Collections.unmodifiableMap(mapZzb);
    }

    @KeepForSdk
    public static <T> Set<T> mutableSetOfWithSize(int i2) {
        return i2 == 0 ? new ArraySet() : zza(i2, true);
    }

    @KeepForSdk
    @Deprecated
    public static <T> Set<T> setOf(T t2, T t3, T t4) {
        Set setZza = zza(3, false);
        setZza.add(t2);
        setZza.add(t3);
        setZza.add(t4);
        return Collections.unmodifiableSet(setZza);
    }

    private static <T> Set<T> zza(int i2, boolean z2) {
        return i2 <= (z2 ? 128 : 256) ? new ArraySet(i2) : new HashSet(i2, z2 ? 0.75f : 1.0f);
    }

    private static <K, V> Map<K, V> zzb(int i2, boolean z2) {
        return i2 <= 256 ? new ArrayMap(i2) : new HashMap(i2, 1.0f);
    }

    @KeepForSdk
    @Deprecated
    public static <T> List<T> listOf(T t2) {
        return Collections.singletonList(t2);
    }

    @KeepForSdk
    @Deprecated
    public static <T> List<T> listOf(T... tArr) {
        int length = tArr.length;
        if (length == 0) {
            return listOf();
        }
        if (length != 1) {
            return Collections.unmodifiableList(Arrays.asList(tArr));
        }
        return listOf(tArr[0]);
    }

    @KeepForSdk
    public static <K, V> Map<K, V> mapOf(K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
        Map mapZzb = zzb(6, false);
        mapZzb.put(k2, v2);
        mapZzb.put(k3, v3);
        mapZzb.put(k4, v4);
        mapZzb.put(k5, v5);
        mapZzb.put(k6, v6);
        mapZzb.put(k7, v7);
        return Collections.unmodifiableMap(mapZzb);
    }

    @KeepForSdk
    @Deprecated
    public static <T> Set<T> setOf(T... tArr) {
        int length = tArr.length;
        if (length == 0) {
            return Collections.emptySet();
        }
        if (length == 1) {
            return Collections.singleton(tArr[0]);
        }
        if (length == 2) {
            T t2 = tArr[0];
            T t3 = tArr[1];
            Set setZza = zza(2, false);
            setZza.add(t2);
            setZza.add(t3);
            return Collections.unmodifiableSet(setZza);
        }
        if (length == 3) {
            return setOf(tArr[0], tArr[1], tArr[2]);
        }
        if (length != 4) {
            Set setZza2 = zza(tArr.length, false);
            Collections.addAll(setZza2, tArr);
            return Collections.unmodifiableSet(setZza2);
        }
        T t4 = tArr[0];
        T t5 = tArr[1];
        T t6 = tArr[2];
        T t7 = tArr[3];
        Set setZza3 = zza(4, false);
        setZza3.add(t4);
        setZza3.add(t5);
        setZza3.add(t6);
        setZza3.add(t7);
        return Collections.unmodifiableSet(setZza3);
    }
}

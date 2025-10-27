package com.blankj.utilcode.util;

import android.util.Pair;
import androidx.datastore.preferences.protobuf.MapFieldLite;
import com.bumptech.glide.load.engine.bitmap_recycle.PrettyPrintTreeMap;
import com.hp.hpl.sparta.Sparta;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes2.dex */
public class MapUtils {

    public interface Closure<K, V> {
        void execute(K k2, V v2);
    }

    public interface Transformer<K1, V1, K2, V2> {
        Pair<K2, V2> transform(K1 k12, V1 v12);
    }

    private MapUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static <K, V> void forAllDo(Map<K, V> map, Closure<K, V> closure) {
        if (map == null || closure == null) {
            return;
        }
        for (Map.Entry<K, V> entry : map.entrySet()) {
            closure.execute(entry.getKey(), entry.getValue());
        }
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.size() == 0;
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SafeVarargs
    public static <K, V> HashMap<K, V> newHashMap(Pair<K, V>... pairArr) {
        MapFieldLite mapFieldLite = (HashMap<K, V>) new HashMap();
        if (pairArr != null && pairArr.length != 0) {
            for (Pair<K, V> pair : pairArr) {
                if (pair != null) {
                    mapFieldLite.put(pair.first, pair.second);
                }
            }
        }
        return mapFieldLite;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SafeVarargs
    public static <K, V> Hashtable<K, V> newHashTable(Pair<K, V>... pairArr) {
        Sparta.HashtableCache hashtableCache = (Hashtable<K, V>) new Hashtable();
        if (pairArr != null && pairArr.length != 0) {
            for (Pair<K, V> pair : pairArr) {
                if (pair != null) {
                    hashtableCache.put(pair.first, pair.second);
                }
            }
        }
        return hashtableCache;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SafeVarargs
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Pair<K, V>... pairArr) {
        MapFieldLite mapFieldLite = (LinkedHashMap<K, V>) new LinkedHashMap();
        if (pairArr != null && pairArr.length != 0) {
            for (Pair<K, V> pair : pairArr) {
                if (pair != null) {
                    mapFieldLite.put(pair.first, pair.second);
                }
            }
        }
        return mapFieldLite;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SafeVarargs
    public static <K, V> TreeMap<K, V> newTreeMap(Comparator<K> comparator, Pair<K, V>... pairArr) {
        if (comparator == null) {
            throw new IllegalArgumentException("comparator must not be null");
        }
        PrettyPrintTreeMap prettyPrintTreeMap = (TreeMap<K, V>) new TreeMap(comparator);
        if (pairArr != null && pairArr.length != 0) {
            for (Pair<K, V> pair : pairArr) {
                if (pair != null) {
                    prettyPrintTreeMap.put(pair.first, pair.second);
                }
            }
        }
        return prettyPrintTreeMap;
    }

    @SafeVarargs
    public static <K, V> Map<K, V> newUnmodifiableMap(Pair<K, V>... pairArr) {
        return Collections.unmodifiableMap(newHashMap(pairArr));
    }

    public static int size(Map map) {
        if (map == null) {
            return 0;
        }
        return map.size();
    }

    public static String toString(Map map) {
        return map == null ? "null" : map.toString();
    }

    public static <K1, V1, K2, V2> Map<K2, V2> transform(Map<K1, V1> map, final Transformer<K1, V1, K2, V2> transformer) {
        if (map != null && transformer != null) {
            try {
                final Map<K2, V2> map2 = (Map) map.getClass().newInstance();
                forAllDo(map, new Closure<K1, V1>() { // from class: com.blankj.utilcode.util.MapUtils.1
                    @Override // com.blankj.utilcode.util.MapUtils.Closure
                    public void execute(K1 k12, V1 v12) {
                        Pair pairTransform = transformer.transform(k12, v12);
                        map2.put(pairTransform.first, pairTransform.second);
                    }
                });
                return map2;
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (InstantiationException e3) {
                e3.printStackTrace();
            }
        }
        return null;
    }
}

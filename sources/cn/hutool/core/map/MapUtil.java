package cn.hutool.core.map;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.lang.Editor;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.stream.CollectorUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.JdkUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import java.lang.reflect.Array;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes.dex */
public class MapUtil {
    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public static <K, V> MapBuilder<K, V> builder() {
        return builder(new HashMap());
    }

    public static void clear(Map<?, ?>... mapArr) {
        for (Map<?, ?> map : mapArr) {
            if (isNotEmpty(map)) {
                map.clear();
            }
        }
    }

    public static <K, V> V computeIfAbsent(Map<K, V> map, K k2, Function<? super K, ? extends V> function) {
        return JdkUtil.IS_JDK8 ? (V) computeIfAbsentForJdk8(map, k2, function) : (V) map.computeIfAbsent(k2, function);
    }

    public static <K, V> V computeIfAbsentForJdk8(Map<K, V> map, K k2, Function<? super K, ? extends V> function) {
        V v2 = map.get(k2);
        if (v2 == null) {
            v2 = (V) function.apply(k2);
            V v3 = (V) map.putIfAbsent(k2, v2);
            if (v3 != null) {
                return v3;
            }
        }
        return v2;
    }

    public static <K, V> Map<K, V> createMap(Class<?> cls) {
        if (cls == null || cls.isAssignableFrom(AbstractMap.class)) {
            return new HashMap();
        }
        try {
            return (Map) ReflectUtil.newInstance(cls, new Object[0]);
        } catch (UtilException unused) {
            return new HashMap();
        }
    }

    public static MapProxy createProxy(Map<?, ?> map) {
        return MapProxy.create(map);
    }

    public static <T extends Map<K, V>, K, V> T defaultIfEmpty(T t2, T t3) {
        return isEmpty(t2) ? t3 : t2;
    }

    public static <K, V> Map<K, V> edit(Map<K, V> map, Editor<Map.Entry<K, V>> editor) {
        if (map == null || editor == null) {
            return map;
        }
        Map<K, V> map2 = (Map) ReflectUtil.newInstanceIfPossible(map.getClass());
        if (map2 == null) {
            map2 = new HashMap<>(map.size(), 1.0f);
        }
        if (isEmpty(map)) {
            return map2;
        }
        if (!map2.isEmpty()) {
            map2.clear();
        }
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> entryEdit = editor.edit(it.next());
            if (entryEdit != null) {
                map2.put(entryEdit.getKey(), entryEdit.getValue());
            }
        }
        return map2;
    }

    public static <K, V> Map<K, V> empty() {
        return Collections.emptyMap();
    }

    public static <K, V> Map<K, V> emptyIfNull(Map<K, V> map) {
        return map == null ? Collections.emptyMap() : map;
    }

    public static <K, V> Map.Entry<K, V> entry(K k2, V v2) {
        return entry(k2, v2, true);
    }

    public static <K, V> Map<K, V> filter(Map<K, V> map, final Filter<Map.Entry<K, V>> filter) {
        return (map == null || filter == null) ? map : edit(map, new Editor() { // from class: cn.hutool.core.map.x0
            @Override // cn.hutool.core.lang.Editor
            public final Object edit(Object obj) {
                return MapUtil.lambda$filter$1(filter, (Map.Entry) obj);
            }
        });
    }

    public static <T> T get(Map<?, ?> map, Object obj, Class<T> cls) {
        return (T) get(map, obj, cls, (Object) null);
    }

    public static <K, V> Map<K, V> getAny(Map<K, V> map, final K... kArr) {
        return filter(map, new Filter() { // from class: cn.hutool.core.map.v0
            @Override // cn.hutool.core.lang.Filter
            public final boolean accept(Object obj) {
                return MapUtil.lambda$getAny$7(kArr, (Map.Entry) obj);
            }
        });
    }

    public static Boolean getBool(Map<?, ?> map, Object obj) {
        return (Boolean) get(map, obj, Boolean.class);
    }

    public static Character getChar(Map<?, ?> map, Object obj) {
        return (Character) get(map, obj, Character.class);
    }

    public static Date getDate(Map<?, ?> map, Object obj) {
        return (Date) get(map, obj, Date.class);
    }

    public static Double getDouble(Map<?, ?> map, Object obj) {
        return (Double) get(map, obj, Double.class);
    }

    public static Float getFloat(Map<?, ?> map, Object obj) {
        return (Float) get(map, obj, Float.class);
    }

    public static Integer getInt(Map<?, ?> map, Object obj) {
        return (Integer) get(map, obj, Integer.class);
    }

    public static Long getLong(Map<?, ?> map, Object obj) {
        return (Long) get(map, obj, Long.class);
    }

    public static <T> T getQuietly(Map<?, ?> map, Object obj, Class<T> cls, T t2) {
        return map == null ? t2 : (T) Convert.convertQuietly(cls, map.get(obj), t2);
    }

    public static Short getShort(Map<?, ?> map, Object obj) {
        return (Short) get(map, obj, Short.class);
    }

    public static String getStr(Map<?, ?> map, Object obj) {
        return (String) get(map, obj, String.class);
    }

    public static <K, V> Map<K, List<V>> grouping(Iterable<Map.Entry<K, V>> iterable) {
        HashMap map = new HashMap();
        if (CollUtil.isEmpty(iterable)) {
            return map;
        }
        for (Map.Entry<K, V> entry : iterable) {
            ((List) map.computeIfAbsent(entry.getKey(), new Function() { // from class: cn.hutool.core.map.u0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return MapUtil.lambda$grouping$0(obj);
                }
            })).add(entry.getValue());
        }
        return map;
    }

    public static <K, V> Map<V, K> inverse(Map<K, V> map) {
        final Map<V, K> mapCreateMap = createMap(map.getClass());
        map.forEach(new BiConsumer() { // from class: cn.hutool.core.map.y0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                mapCreateMap.put(obj2, obj);
            }
        });
        return mapCreateMap;
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty()) ? false : true;
    }

    public static <K, V> String join(Map<K, V> map, String str, String str2, String... strArr) {
        return join(map, str, str2, false, strArr);
    }

    public static <K, V> String joinIgnoreNull(Map<K, V> map, String str, String str2, String... strArr) {
        return join(map, str, str2, true, strArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map.Entry lambda$filter$1(Filter filter, Map.Entry entry) {
        if (filter.accept(entry)) {
            return entry;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getAny$7(Object[] objArr, Map.Entry entry) {
        return ArrayUtil.contains(objArr, entry.getKey());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List lambda$grouping$0(Object obj) {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$map$2(BiFunction biFunction, Map.Entry entry) {
        return biFunction.apply(entry.getKey(), entry.getValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$map$3(Object obj, Object obj2) {
        return obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map.Entry lambda$reverse$4(final Map.Entry entry) {
        return new Map.Entry<Object, Object>() { // from class: cn.hutool.core.map.MapUtil.1
            @Override // java.util.Map.Entry
            public Object getKey() {
                return entry.getValue();
            }

            @Override // java.util.Map.Entry
            public Object getValue() {
                return entry.getKey();
            }

            @Override // java.util.Map.Entry
            public Object setValue(Object obj) {
                throw new UnsupportedOperationException("Unsupported setValue method !");
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$sortByValue$6(Map map, Map.Entry entry) {
    }

    public static <K, V, R> Map<K, R> map(Map<K, V> map, final BiFunction<K, V, R> biFunction) {
        return (map == null || biFunction == null) ? newHashMap() : (Map) map.entrySet().stream().collect(CollectorUtil.toMap(new Function() { // from class: cn.hutool.core.map.a1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Map.Entry) obj).getKey();
            }
        }, new Function() { // from class: cn.hutool.core.map.s0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MapUtil.lambda$map$2(biFunction, (Map.Entry) obj);
            }
        }, new BinaryOperator() { // from class: cn.hutool.core.map.t0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return MapUtil.lambda$map$3(obj, obj2);
            }
        }));
    }

    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap<>(16);
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    public static <K, V> Map<K, V> newIdentityMap(int i2) {
        return new IdentityHashMap(i2);
    }

    public static <K, V> TreeMap<K, V> newTreeMap(Comparator<? super K> comparator) {
        return new TreeMap<>(comparator);
    }

    public static <K, V> HashMap<K, V> of(K k2, V v2) {
        return of(k2, v2, false);
    }

    @SafeVarargs
    public static <K, V> Map<K, V> ofEntries(Map.Entry<K, V>... entryArr) {
        HashMap map = new HashMap();
        for (Map.Entry<K, V> entry : entryArr) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public static <K, V> Map<K, V> removeAny(Map<K, V> map, K... kArr) {
        for (K k2 : kArr) {
            map.remove(k2);
        }
        return map;
    }

    public static <K, V> Map<K, V> removeNullValue(Map<K, V> map) {
        if (isEmpty(map)) {
            return map;
        }
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getValue() == null) {
                it.remove();
            }
        }
        return map;
    }

    public static <K, V> Map<K, V> renameKey(Map<K, V> map, K k2, K k3) {
        if (isNotEmpty(map) && map.containsKey(k2)) {
            if (map.containsKey(k3)) {
                throw new IllegalArgumentException(CharSequenceUtil.format("The key '{}' exist !", k3));
            }
            map.put(k3, map.remove(k2));
        }
        return map;
    }

    public static <T> Map<T, T> reverse(Map<T, T> map) {
        return edit(map, new Editor() { // from class: cn.hutool.core.map.w0
            @Override // cn.hutool.core.lang.Editor
            public final Object edit(Object obj) {
                return MapUtil.lambda$reverse$4((Map.Entry) obj);
            }
        });
    }

    public static <K, V> TreeMap<K, V> sort(Map<K, V> map) {
        return sort(map, null);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean z2) {
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        Comparator comparatorComparingByValue = Map.Entry.comparingByValue();
        if (z2) {
            comparatorComparingByValue = comparatorComparingByValue.reversed();
        }
        map.entrySet().stream().sorted(comparatorComparingByValue).forEachOrdered(new Consumer() { // from class: cn.hutool.core.map.z0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MapUtil.lambda$sortByValue$6(linkedHashMap, (Map.Entry) obj);
            }
        });
        return linkedHashMap;
    }

    public static String sortJoin(Map<?, ?> map, String str, String str2, boolean z2, String... strArr) {
        return join(sort(map), str, str2, z2, strArr);
    }

    public static <K, V> Map<K, V> toCamelCaseMap(Map<K, V> map) {
        return map instanceof LinkedHashMap ? new CamelCaseLinkedMap(map) : new CamelCaseMap(map);
    }

    public static <K, V> Map<K, List<V>> toListMap(Iterable<? extends Map<K, V>> iterable) {
        HashMap map = new HashMap();
        if (CollUtil.isEmpty(iterable)) {
            return map;
        }
        Iterator<? extends Map<K, V>> it = iterable.iterator();
        while (it.hasNext()) {
            for (Map.Entry<K, V> entry : it.next().entrySet()) {
                K key = entry.getKey();
                List list = (List) map.get(key);
                if (list == null) {
                    map.put(key, CollUtil.newArrayList(entry.getValue()));
                } else {
                    list.add(entry.getValue());
                }
            }
        }
        return map;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <K, V> List<Map<K, V>> toMapList(Map<K, ? extends Iterable<V>> map) {
        boolean z2;
        ArrayList arrayList = new ArrayList();
        if (isEmpty(map)) {
            return arrayList;
        }
        int i2 = 0;
        do {
            HashMap map2 = new HashMap();
            z2 = true;
            for (Map.Entry<K, ? extends Iterable<V>> entry : map.entrySet()) {
                ArrayList arrayListNewArrayList = CollUtil.newArrayList(entry.getValue());
                int size = arrayListNewArrayList.size();
                if (i2 < size) {
                    map2.put(entry.getKey(), arrayListNewArrayList.get(i2));
                    if (i2 != size - 1) {
                        z2 = false;
                    }
                }
            }
            if (!map2.isEmpty()) {
                arrayList.add(map2);
            }
            i2++;
        } while (!z2);
        return arrayList;
    }

    public static Object[][] toObjectArray(Map<?, ?> map) {
        if (map == null) {
            return null;
        }
        Object[][] objArr = (Object[][]) Array.newInstance((Class<?>) Object.class, map.size(), 2);
        if (map.isEmpty()) {
            return objArr;
        }
        int i2 = 0;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            objArr[i2][0] = entry.getKey();
            objArr[i2][1] = entry.getValue();
            i2++;
        }
        return objArr;
    }

    public static <K, V> Map<K, V> unmodifiable(Map<K, V> map) {
        return Collections.unmodifiableMap(map);
    }

    public static <K, V> ArrayList<V> valuesOfKeys(Map<K, V> map, Iterator<K> it) {
        ArrayList<V> arrayList = new ArrayList<>();
        while (it.hasNext()) {
            arrayList.add(map.get(it.next()));
        }
        return arrayList;
    }

    public static <K, V> MapWrapper<K, V> wrap(Map<K, V> map) {
        return new MapWrapper<>(map);
    }

    public static <K, V> MapBuilder<K, V> builder(Map<K, V> map) {
        return new MapBuilder<>(map);
    }

    public static <K, V, T extends Map<K, V>> T empty(Class<?> cls) {
        if (cls == null) {
            return (T) Collections.emptyMap();
        }
        if (NavigableMap.class == cls) {
            return Collections.emptyNavigableMap();
        }
        if (SortedMap.class == cls) {
            return Collections.emptySortedMap();
        }
        if (Map.class == cls) {
            return (T) Collections.emptyMap();
        }
        throw new IllegalArgumentException(CharSequenceUtil.format("[{}] is not support to get empty!", cls));
    }

    public static <K, V> Map.Entry<K, V> entry(K k2, V v2, boolean z2) {
        return z2 ? new AbstractMap.SimpleImmutableEntry(k2, v2) : new AbstractMap.SimpleEntry(k2, v2);
    }

    public static <K, V> Map<K, V> filter(Map<K, V> map, K... kArr) {
        if (map == null || kArr == null) {
            return map;
        }
        Map<K, V> map2 = (Map) ReflectUtil.newInstanceIfPossible(map.getClass());
        if (map2 == null) {
            map2 = new HashMap<>(map.size(), 1.0f);
        }
        if (isEmpty(map)) {
            return map2;
        }
        if (!map2.isEmpty()) {
            map2.clear();
        }
        for (K k2 : kArr) {
            if (map.containsKey(k2)) {
                map2.put(k2, map.get(k2));
            }
        }
        return map2;
    }

    public static <T> T get(Map<?, ?> map, Object obj, Class<T> cls, T t2) {
        return map == null ? t2 : (T) Convert.convert((Class) cls, map.get(obj), (Object) t2);
    }

    public static Boolean getBool(Map<?, ?> map, Object obj, Boolean bool) {
        return (Boolean) get(map, obj, (Class<Boolean>) Boolean.class, bool);
    }

    public static Character getChar(Map<?, ?> map, Object obj, Character ch) {
        return (Character) get(map, obj, (Class<Character>) Character.class, ch);
    }

    public static Date getDate(Map<?, ?> map, Object obj, Date date) {
        return (Date) get(map, obj, (Class<Date>) Date.class, date);
    }

    public static Double getDouble(Map<?, ?> map, Object obj, Double d3) {
        return (Double) get(map, obj, (Class<Double>) Double.class, d3);
    }

    public static Float getFloat(Map<?, ?> map, Object obj, Float f2) {
        return (Float) get(map, obj, (Class<Float>) Float.class, f2);
    }

    public static Integer getInt(Map<?, ?> map, Object obj, Integer num) {
        return (Integer) get(map, obj, (Class<Integer>) Integer.class, num);
    }

    public static Long getLong(Map<?, ?> map, Object obj, Long l2) {
        return (Long) get(map, obj, (Class<Long>) Long.class, l2);
    }

    public static <T> T getQuietly(Map<?, ?> map, Object obj, TypeReference<T> typeReference, T t2) {
        return map == null ? t2 : (T) Convert.convertQuietly(typeReference, map.get(obj), t2);
    }

    public static Short getShort(Map<?, ?> map, Object obj, Short sh) {
        return (Short) get(map, obj, (Class<Short>) Short.class, sh);
    }

    public static String getStr(Map<?, ?> map, Object obj, String str) {
        return (String) get(map, obj, (Class<String>) String.class, str);
    }

    public static <K, V> String join(Map<K, V> map, String str, String str2, boolean z2, String... strArr) {
        StringBuilder sbBuilder = StrUtil.builder();
        if (isNotEmpty(map)) {
            boolean z3 = true;
            for (Map.Entry<K, V> entry : map.entrySet()) {
                if (!z2 || (entry.getKey() != null && entry.getValue() != null)) {
                    if (z3) {
                        z3 = false;
                    } else {
                        sbBuilder.append(str);
                    }
                    sbBuilder.append(Convert.toStr(entry.getKey()));
                    sbBuilder.append(str2);
                    sbBuilder.append(Convert.toStr(entry.getValue()));
                }
            }
        }
        if (ArrayUtil.isNotEmpty((Object[]) strArr)) {
            for (String str3 : strArr) {
                sbBuilder.append(str3);
            }
        }
        return sbBuilder.toString();
    }

    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(int i2) {
        if (i2 <= 0) {
            i2 = 16;
        }
        return new ConcurrentHashMap<>(i2);
    }

    public static <K, V> HashMap<K, V> newHashMap(int i2, boolean z2) {
        int i3 = ((int) (i2 / 0.75f)) + 1;
        return z2 ? new LinkedHashMap(i3) : new HashMap<>(i3);
    }

    public static <K, V> TreeMap<K, V> newTreeMap(Map<K, V> map, Comparator<? super K> comparator) {
        TreeMap<K, V> treeMap = new TreeMap<>(comparator);
        if (!isEmpty(map)) {
            treeMap.putAll(map);
        }
        return treeMap;
    }

    public static <K, V> HashMap<K, V> of(K k2, V v2, boolean z2) {
        HashMap<K, V> mapNewHashMap = newHashMap(z2);
        mapNewHashMap.put(k2, v2);
        return mapNewHashMap;
    }

    public static <K, V> TreeMap<K, V> sort(Map<K, V> map, Comparator<? super K> comparator) {
        if (map == null) {
            return null;
        }
        if (map instanceof TreeMap) {
            TreeMap<K, V> treeMap = (TreeMap) map;
            if (comparator == null || comparator.equals(treeMap.comparator())) {
                return treeMap;
            }
        }
        return newTreeMap(map, comparator);
    }

    public static <K, V> MapBuilder<K, V> builder(K k2, V v2) {
        return builder(new HashMap()).put(k2, v2);
    }

    public static <T> T get(Map<?, ?> map, Object obj, TypeReference<T> typeReference) {
        return (T) get(map, obj, typeReference, (Object) null);
    }

    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(Map<K, V> map) {
        if (isEmpty(map)) {
            return new ConcurrentHashMap<>(16);
        }
        return new ConcurrentHashMap<>(map);
    }

    public static <K, V> HashMap<K, V> newHashMap(int i2) {
        return newHashMap(i2, false);
    }

    public static <T> T get(Map<?, ?> map, Object obj, TypeReference<T> typeReference, T t2) {
        return map == null ? t2 : (T) Convert.convert(typeReference, map.get(obj), t2);
    }

    public static <K, V> HashMap<K, V> newHashMap(boolean z2) {
        return newHashMap(16, z2);
    }

    @SafeVarargs
    @Deprecated
    public static <K, V> Map<K, V> of(Pair<K, V>... pairArr) {
        HashMap map = new HashMap();
        for (Pair<K, V> pair : pairArr) {
            map.put(pair.getKey(), pair.getValue());
        }
        return map;
    }

    public static HashMap<Object, Object> of(Object[] objArr) {
        if (objArr == null) {
            return null;
        }
        HashMap<Object, Object> map = new HashMap<>((int) (objArr.length * 1.5d));
        for (int i2 = 0; i2 < objArr.length; i2++) {
            Object obj = objArr[i2];
            if (obj instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) obj;
                map.put(entry.getKey(), entry.getValue());
            } else if (obj instanceof Object[]) {
                Object[] objArr2 = (Object[]) obj;
                if (objArr2.length > 1) {
                    map.put(objArr2[0], objArr2[1]);
                }
            } else if (obj instanceof Iterable) {
                Iterator it = ((Iterable) obj).iterator();
                if (it.hasNext()) {
                    Object next = it.next();
                    if (it.hasNext()) {
                        map.put(next, it.next());
                    }
                }
            } else if (obj instanceof Iterator) {
                Iterator it2 = (Iterator) obj;
                if (it2.hasNext()) {
                    Object next2 = it2.next();
                    if (it2.hasNext()) {
                        map.put(next2, it2.next());
                    }
                }
            } else {
                throw new IllegalArgumentException(CharSequenceUtil.format("Array element {}, '{}', is not type of Map.Entry or Array or Iterable or Iterator", Integer.valueOf(i2), obj));
            }
        }
        return map;
    }
}

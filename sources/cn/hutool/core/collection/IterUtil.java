package cn.hutool.core.collection;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Editor;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.lang.Matcher;
import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrJoiner;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* loaded from: classes.dex */
public class IterUtil {
    public static <E> Iterable<E> asIterable(final Iterator<E> it) {
        return new Iterable() { // from class: cn.hutool.core.collection.j0
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return IterUtil.lambda$asIterable$3(it);
            }
        };
    }

    public static <E> Iterator<E> asIterator(Enumeration<E> enumeration) {
        return new EnumerationIter(enumeration);
    }

    public static void clear(Iterator<?> it) {
        if (it != null) {
            while (it.hasNext()) {
                it.next();
                it.remove();
            }
        }
    }

    public static <T> Map<T, Integer> countMap(Iterator<T> it) {
        HashMap map = new HashMap();
        if (it != null) {
            while (it.hasNext()) {
                T next = it.next();
                map.put(next, Integer.valueOf(((Integer) map.getOrDefault(next, 0)).intValue() + 1));
            }
        }
        return map;
    }

    public static <T> List<T> edit(Iterable<T> iterable, Editor<T> editor) {
        ArrayList arrayList = new ArrayList();
        if (iterable == null) {
            return arrayList;
        }
        for (T tEdit : iterable) {
            if (editor != null) {
                tEdit = editor.edit(tEdit);
            }
            if (tEdit != null) {
                arrayList.add(tEdit);
            }
        }
        return arrayList;
    }

    public static <T> Iterator<T> empty() {
        return Collections.emptyIterator();
    }

    public static <K, V> Map<K, V> fieldValueAsMap(Iterator<?> it, String str, String str2) {
        return toMap(it, new HashMap(), new s0(str), new r0(str2));
    }

    public static <V> List<Object> fieldValueList(Iterable<V> iterable, String str) {
        return fieldValueList(getIter((Iterable) iterable), str);
    }

    public static <K, V> Map<K, V> fieldValueMap(Iterator<V> it, String str) {
        return toMap(it, new HashMap(), new n0(str));
    }

    public static <T extends Iterable<E>, E> T filter(T t2, Filter<E> filter) {
        if (t2 == null) {
            return null;
        }
        filter(t2.iterator(), filter);
        return t2;
    }

    public static <E> List<E> filterToList(Iterator<E> it, Filter<E> filter) {
        return toList(filtered(it, filter));
    }

    public static <E> FilterIter<E> filtered(Iterator<? extends E> it, Filter<? super E> filter) {
        return new FilterIter<>(it, filter);
    }

    public static <T> T firstMatch(Iterator<T> it, Matcher<T> matcher) throws IllegalArgumentException {
        Assert.notNull(matcher, "Matcher must be not null !", new Object[0]);
        if (it == null) {
            return null;
        }
        while (it.hasNext()) {
            T next = it.next();
            if (matcher.match(next)) {
                return next;
            }
        }
        return null;
    }

    public static <E> void forEach(Iterator<E> it, Consumer<? super E> consumer) {
        if (it != null) {
            while (it.hasNext()) {
                E next = it.next();
                if (consumer != null) {
                    consumer.accept(next);
                }
            }
        }
    }

    public static <E> E get(Iterator<E> it, int i2) throws Throwable {
        if (it == null) {
            return null;
        }
        Assert.isTrue(i2 >= 0, "[index] must be >= 0", new Object[0]);
        while (it.hasNext()) {
            i2--;
            if (-1 == i2) {
                return it.next();
            }
            it.next();
        }
        return null;
    }

    public static Class<?> getElementType(Iterable<?> iterable) {
        return getElementType((Iterator<?>) getIter((Iterable) iterable));
    }

    public static <T> T getFirst(Iterable<T> iterable) {
        if (!(iterable instanceof List)) {
            return (T) getFirst(getIter((Iterable) iterable));
        }
        List list = (List) iterable;
        if (CollUtil.isEmpty((Collection<?>) list)) {
            return null;
        }
        return (T) list.get(0);
    }

    public static <T> T getFirstNoneNull(Iterable<T> iterable) {
        if (iterable == null) {
            return null;
        }
        return (T) getFirstNoneNull(iterable.iterator());
    }

    public static <T> Iterator<T> getIter(Iterable<T> iterable) {
        if (iterable == null) {
            return null;
        }
        return iterable.iterator();
    }

    public static boolean hasNull(Iterable<?> iterable) {
        return hasNull(iterable == null ? null : iterable.iterator());
    }

    public static boolean isAllNull(Iterable<?> iterable) {
        return isAllNull(iterable == null ? null : iterable.iterator());
    }

    public static boolean isEmpty(Iterable<?> iterable) {
        return iterable == null || isEmpty(iterable.iterator());
    }

    public static boolean isEqualList(Iterable<?> iterable, Iterable<?> iterable2) {
        if (iterable == iterable2) {
            return true;
        }
        Iterator<?> it = iterable.iterator();
        Iterator<?> it2 = iterable2.iterator();
        do {
            if (!it.hasNext() || !it2.hasNext()) {
                return !(it.hasNext() || it2.hasNext());
            }
        } while (Objects.equals(it.next(), it2.next()));
        return false;
    }

    public static boolean isNotEmpty(Iterable<?> iterable) {
        return iterable != null && isNotEmpty(iterable.iterator());
    }

    public static <T> String join(Iterator<T> it, CharSequence charSequence) {
        return StrJoiner.of(charSequence).append((Iterator) it).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Iterator lambda$asIterable$3(Iterator it) {
        return it;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$fieldValueAsMap$ceda202c$1(String str, Object obj) throws Exception {
        return ReflectUtil.getFieldValue(obj, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$fieldValueAsMap$f61513e$1(String str, Object obj) throws Exception {
        return ReflectUtil.getFieldValue(obj, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$fieldValueMap$a3f4a90f$1(String str, Object obj) throws Exception {
        return ReflectUtil.getFieldValue(obj, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$toListMap$0(Object obj) {
        return obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List lambda$toListMap$1(Object obj) {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$toMap$2(Object obj) {
        return obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$toMap$ed1d981b$1(Object obj) throws Exception {
        return obj;
    }

    public static int size(Iterable<?> iterable) {
        if (iterable == null) {
            return 0;
        }
        return iterable instanceof Collection ? ((Collection) iterable).size() : size(iterable.iterator());
    }

    public static <E> List<E> toList(Iterable<E> iterable) {
        if (iterable == null) {
            return null;
        }
        return toList(iterable.iterator());
    }

    public static <K, V> Map<K, List<V>> toListMap(Iterable<V> iterable, Function<V, K> function) {
        return toListMap(iterable, function, new Function() { // from class: cn.hutool.core.collection.q0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return IterUtil.lambda$toListMap$0(obj);
            }
        });
    }

    public static <K, V> HashMap<K, V> toMap(Iterable<Map.Entry<K, V>> iterable) {
        HashMap<K, V> map = new HashMap<>();
        if (isNotEmpty(iterable)) {
            for (Map.Entry<K, V> entry : iterable) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }

    public static <E> String toStr(Iterator<E> it) {
        return toStr(it, new Function() { // from class: cn.hutool.core.collection.k0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ObjectUtil.toString(obj);
            }
        });
    }

    public static <F, T> Iterator<T> trans(Iterator<F> it, Function<? super F, ? extends T> function) {
        return new TransIter(it, function);
    }

    public static <V> List<Object> fieldValueList(Iterator<V> it, String str) {
        ArrayList arrayList = new ArrayList();
        if (it != null) {
            while (it.hasNext()) {
                arrayList.add(ReflectUtil.getFieldValue(it.next(), str));
            }
        }
        return arrayList;
    }

    public static <E> Iterator<E> filter(Iterator<E> it, Filter<E> filter) {
        if (it != null && filter != null) {
            while (it.hasNext()) {
                if (!filter.accept(it.next())) {
                    it.remove();
                }
            }
        }
        return it;
    }

    public static Class<?> getElementType(Iterator<?> it) {
        Object firstNoneNull;
        if (it == null || (firstNoneNull = getFirstNoneNull(it)) == null) {
            return null;
        }
        return firstNoneNull.getClass();
    }

    public static <T> T getFirstNoneNull(Iterator<T> it) {
        return (T) firstMatch(it, new Matcher() { // from class: cn.hutool.core.collection.m0
            @Override // cn.hutool.core.lang.Matcher
            public final boolean match(Object obj) {
                return cn.hutool.core.annotation.g0.a(obj);
            }
        });
    }

    public static Iterator<?> getIter(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Iterator) {
            return (Iterator) obj;
        }
        if (obj instanceof Iterable) {
            return ((Iterable) obj).iterator();
        }
        if (ArrayUtil.isArray(obj)) {
            return new ArrayIter(obj);
        }
        if (obj instanceof Enumeration) {
            return new EnumerationIter((Enumeration) obj);
        }
        if (obj instanceof Map) {
            return ((Map) obj).entrySet().iterator();
        }
        if (obj instanceof NodeList) {
            return new NodeListIter((NodeList) obj);
        }
        if (obj instanceof Node) {
            return new NodeListIter(((Node) obj).getChildNodes());
        }
        if (obj instanceof Dictionary) {
            return new EnumerationIter(((Dictionary) obj).elements());
        }
        try {
            Object objInvoke = ReflectUtil.invoke(obj, "iterator", new Object[0]);
            if (objInvoke instanceof Iterator) {
                return (Iterator) objInvoke;
            }
        } catch (RuntimeException unused) {
        }
        return new ArrayIter(new Object[]{obj});
    }

    public static boolean hasNull(Iterator<?> it) {
        if (it == null) {
            return true;
        }
        while (it.hasNext()) {
            if (it.next() == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAllNull(Iterator<?> it) {
        return getFirstNoneNull(it) == null;
    }

    public static boolean isEmpty(Iterator<?> it) {
        return it == null || !it.hasNext();
    }

    public static boolean isNotEmpty(Iterator<?> it) {
        return it != null && it.hasNext();
    }

    public static <T> String join(Iterator<T> it, CharSequence charSequence, String str, String str2) {
        return StrJoiner.of(charSequence, str, str2).setWrapElement(true).append((Iterator) it).toString();
    }

    public static <E> List<E> toList(Iterator<E> it) {
        return ListUtil.toList(it);
    }

    public static <T, K, V> Map<K, List<V>> toListMap(Iterable<T> iterable, Function<T, K> function, Function<T, V> function2) {
        return toListMap(MapUtil.newHashMap(), iterable, function, function2);
    }

    public static <E> String toStr(Iterator<E> it, Function<? super E, String> function) {
        return toStr(it, function, ", ", StrPool.BRACKET_START, StrPool.BRACKET_END);
    }

    public static <T, K, V> Map<K, List<V>> toListMap(Map<K, List<V>> map, Iterable<T> iterable, Function<T, K> function, Function<T, V> function2) {
        if (map == null) {
            map = MapUtil.newHashMap();
        }
        if (ObjectUtil.isNull(iterable)) {
            return map;
        }
        for (T t2 : iterable) {
            ((List) map.computeIfAbsent(function.apply(t2), new Function() { // from class: cn.hutool.core.collection.l0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return IterUtil.lambda$toListMap$1(obj);
                }
            })).add(function2.apply(t2));
        }
        return map;
    }

    public static <E> String toStr(Iterator<E> it, Function<? super E, String> function, String str, String str2, String str3) {
        StrJoiner strJoinerOf = StrJoiner.of(str, str2, str3);
        strJoinerOf.append(it, function);
        return strJoinerOf.toString();
    }

    public static int size(Iterator<?> it) {
        int i2 = 0;
        if (it != null) {
            while (it.hasNext()) {
                it.next();
                i2++;
            }
        }
        return i2;
    }

    public static <T> T getFirst(Iterator<T> it) {
        return (T) get(it, 0);
    }

    public static <K, V> Map<K, V> toMap(Iterable<K> iterable, Iterable<V> iterable2) {
        return toMap((Iterable) iterable, (Iterable) iterable2, false);
    }

    public static <T> String join(Iterator<T> it, CharSequence charSequence, Function<T, ? extends CharSequence> function) {
        if (it == null) {
            return null;
        }
        return StrJoiner.of(charSequence).append(it, function).toString();
    }

    public static <K, V> Map<K, V> toMap(Iterable<K> iterable, Iterable<V> iterable2, boolean z2) {
        return toMap(iterable == null ? null : iterable.iterator(), iterable2 != null ? iterable2.iterator() : null, z2);
    }

    public static <K, V> Map<K, V> toMap(Iterator<K> it, Iterator<V> it2) {
        return toMap((Iterator) it, (Iterator) it2, false);
    }

    public static <K, V> Map<K, V> toMap(Iterator<K> it, Iterator<V> it2, boolean z2) {
        HashMap mapNewHashMap = MapUtil.newHashMap(z2);
        if (isNotEmpty((Iterator<?>) it)) {
            while (it.hasNext()) {
                mapNewHashMap.put(it.next(), (it2 == null || !it2.hasNext()) ? null : it2.next());
            }
        }
        return mapNewHashMap;
    }

    public static <K, V> Map<K, V> toMap(Iterable<V> iterable, Function<V, K> function) {
        return toMap(iterable, function, new Function() { // from class: cn.hutool.core.collection.o0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return IterUtil.lambda$toMap$2(obj);
            }
        });
    }

    public static <T, K, V> Map<K, V> toMap(Iterable<T> iterable, Function<T, K> function, Function<T, V> function2) {
        return toMap(MapUtil.newHashMap(), iterable, function, function2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T, K, V> Map<K, V> toMap(Map<K, V> map, Iterable<T> iterable, Function<T, K> function, Function<T, V> function2) {
        Object objNewHashMap = map;
        if (map == null) {
            objNewHashMap = MapUtil.newHashMap();
        }
        if (ObjectUtil.isNull(iterable)) {
            return (Map<K, V>) objNewHashMap;
        }
        for (T t2 : iterable) {
            ((Map) objNewHashMap).put(function.apply(t2), function2.apply(t2));
        }
        return (Map<K, V>) objNewHashMap;
    }

    public static <K, V> Map<K, V> toMap(Iterator<V> it, Map<K, V> map, Func1<V, K> func1) {
        return toMap(it, map, func1, new p0());
    }

    public static <K, V, E> Map<K, V> toMap(Iterator<E> it, Map<K, V> map, Func1<E, K> func1, Func1<E, V> func12) {
        if (it == null) {
            return map;
        }
        if (map == null) {
            map = MapUtil.newHashMap(true);
        }
        while (it.hasNext()) {
            E next = it.next();
            try {
                map.put(func1.call(next), func12.call(next));
            } catch (Exception e2) {
                throw new UtilException(e2);
            }
        }
        return map;
    }
}

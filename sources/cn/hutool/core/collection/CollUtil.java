package cn.hutool.core.collection;

import androidx.datastore.preferences.protobuf.MapFieldLite;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.comparator.PinyinComparator;
import cn.hutool.core.comparator.PropertyComparator;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Editor;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.lang.Matcher;
import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.hash.Hash32;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.TypeUtil;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class CollUtil {

    @FunctionalInterface
    public interface Consumer<T> extends Serializable {
        void accept(T t2, int i2);
    }

    @FunctionalInterface
    public interface KVConsumer<K, V> extends Serializable {
        void accept(K k2, V v2, int i2);
    }

    public static <T> Collection<T> addAll(Collection<T> collection, Object obj) {
        return addAll(collection, obj, TypeUtil.getTypeArgument(collection.getClass()));
    }

    public static <T> List<T> addAllIfNotContains(List<T> list, List<T> list2) {
        for (T t2 : list2) {
            if (!list.contains(t2)) {
                list.add(t2);
            }
        }
        return list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T, S extends T> boolean addIfAbsent(Collection<T> collection, S s2) {
        if (s2 == 0 || collection == null || collection.contains(s2)) {
            return false;
        }
        return collection.add(s2);
    }

    public static <T> boolean allMatch(Collection<T> collection, Predicate<T> predicate) {
        if (isEmpty((Collection<?>) collection)) {
            return false;
        }
        return collection.stream().allMatch(predicate);
    }

    public static <T> boolean anyMatch(Collection<T> collection, Predicate<T> predicate) {
        if (isEmpty((Collection<?>) collection)) {
            return false;
        }
        return collection.stream().anyMatch(predicate);
    }

    public static <E> Enumeration<E> asEnumeration(Iterator<E> it) {
        return new IteratorEnumeration(it);
    }

    public static <E> Iterable<E> asIterable(Iterator<E> it) {
        return IterUtil.asIterable(it);
    }

    public static <E> Iterator<E> asIterator(Enumeration<E> enumeration) {
        return IterUtil.asIterator(enumeration);
    }

    public static void clear(Collection<?>... collectionArr) {
        for (Collection<?> collection : collectionArr) {
            if (isNotEmpty(collection)) {
                collection.clear();
            }
        }
    }

    public static boolean contains(Collection<?> collection, Object obj) {
        return isNotEmpty(collection) && collection.contains(obj);
    }

    public static boolean containsAll(Collection<?> collection, Collection<?> collection2) {
        if (isEmpty(collection)) {
            return isEmpty(collection2);
        }
        if (isEmpty(collection2)) {
            return true;
        }
        if (collection.size() < collection2.size()) {
            return false;
        }
        Iterator<?> it = collection2.iterator();
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsAny(Collection<?> collection, Collection<?> collection2) {
        if (!isEmpty(collection) && !isEmpty(collection2)) {
            if (collection.size() < collection2.size()) {
                Iterator<?> it = collection.iterator();
                while (it.hasNext()) {
                    if (collection2.contains(it.next())) {
                        return true;
                    }
                }
            } else {
                Iterator<?> it2 = collection2.iterator();
                while (it2.hasNext()) {
                    if (collection.contains(it2.next())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static <T> int count(Iterable<T> iterable, Matcher<T> matcher) {
        int i2 = 0;
        if (iterable != null) {
            for (T t2 : iterable) {
                if (matcher == null || matcher.match(t2)) {
                    i2++;
                }
            }
        }
        return i2;
    }

    public static <T> Map<T, Integer> countMap(Iterable<T> iterable) {
        return IterUtil.countMap(iterable == null ? null : iterable.iterator());
    }

    public static <T> Collection<T> create(Class<?> cls) {
        return create(cls, null);
    }

    public static <T extends Collection<E>, E> T defaultIfEmpty(T t2, T t3) {
        return isEmpty((Collection<?>) t2) ? t3 : t2;
    }

    public static <T> Collection<T> disjunction(Collection<T> collection, Collection<T> collection2) {
        if (isEmpty((Collection<?>) collection)) {
            return collection2;
        }
        if (isEmpty((Collection<?>) collection2)) {
            return collection;
        }
        ArrayList arrayList = new ArrayList();
        Map mapCountMap = countMap(collection);
        Map mapCountMap2 = countMap(collection2);
        HashSet hashSetNewHashSet = newHashSet(collection2);
        hashSetNewHashSet.addAll(collection);
        for (Object obj : hashSetNewHashSet) {
            int iAbs = Math.abs(Convert.toInt(mapCountMap.get(obj), 0).intValue() - Convert.toInt(mapCountMap2.get(obj), 0).intValue());
            for (int i2 = 0; i2 < iAbs; i2++) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static <T> ArrayList<T> distinct(Collection<T> collection) {
        return isEmpty((Collection<?>) collection) ? new ArrayList<>() : collection instanceof Set ? new ArrayList<>(collection) : new ArrayList<>(new LinkedHashSet(collection));
    }

    public static <T> Collection<T> edit(Collection<T> collection, Editor<T> editor) {
        if (collection == null || editor == null) {
            return collection;
        }
        Collection<T> collectionCreate = create(collection.getClass());
        if (isEmpty((Collection<?>) collection)) {
            return collectionCreate;
        }
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            T tEdit = editor.edit(it.next());
            if (tEdit != null) {
                collectionCreate.add(tEdit);
            }
        }
        return collectionCreate;
    }

    public static <E, T extends Collection<E>> T empty(Class<?> cls) {
        if (cls == null) {
            return Collections.emptyList();
        }
        if (Set.class.isAssignableFrom(cls)) {
            return NavigableSet.class == cls ? Collections.emptyNavigableSet() : SortedSet.class == cls ? Collections.emptySortedSet() : Collections.emptySet();
        }
        if (List.class.isAssignableFrom(cls)) {
            return Collections.emptyList();
        }
        throw new IllegalArgumentException(CharSequenceUtil.format("[{}] is not support to get empty!", cls));
    }

    public static <T> Set<T> emptyIfNull(Set<T> set) {
        return set == null ? Collections.emptySet() : set;
    }

    public static List<Object> extract(Iterable<?> iterable, Editor<Object> editor) {
        return extract(iterable, editor, false);
    }

    public static <K, V> Map<K, V> fieldValueAsMap(Iterable<?> iterable, String str, String str2) {
        return IterUtil.fieldValueAsMap(IterUtil.getIter((Iterable) iterable), str, str2);
    }

    public static <K, V> Map<K, V> fieldValueMap(Iterable<V> iterable, String str) {
        return IterUtil.fieldValueMap(IterUtil.getIter((Iterable) iterable), str);
    }

    public static <T extends Collection<E>, E> T filter(T t2, Filter<E> filter) {
        return (T) IterUtil.filter(t2, filter);
    }

    public static <T> Collection<T> filterNew(Collection<T> collection, final Filter<T> filter) {
        return (collection == null || filter == null) ? collection : edit(collection, new Editor() { // from class: cn.hutool.core.collection.w
            @Override // cn.hutool.core.lang.Editor
            public final Object edit(Object obj) {
                return CollUtil.lambda$filterNew$1(filter, obj);
            }
        });
    }

    public static <T> T findOne(Iterable<T> iterable, Filter<T> filter) {
        if (iterable == null) {
            return null;
        }
        for (T t2 : iterable) {
            if (filter.accept(t2)) {
                return t2;
            }
        }
        return null;
    }

    public static <T> T findOneByField(Iterable<T> iterable, final String str, final Object obj) {
        return (T) findOne(iterable, new Filter() { // from class: cn.hutool.core.collection.f0
            @Override // cn.hutool.core.lang.Filter
            public final boolean accept(Object obj2) {
                return CollUtil.lambda$findOneByField$3(str, obj, obj2);
            }
        });
    }

    public static <T> void forEach(Iterable<T> iterable, Consumer<T> consumer) {
        if (iterable == null) {
            return;
        }
        forEach(iterable.iterator(), consumer);
    }

    public static <T> T get(Collection<T> collection, int i2) {
        int size;
        if (collection == null || (size = collection.size()) == 0) {
            return null;
        }
        if (i2 < 0) {
            i2 += size;
        }
        if (i2 >= size || i2 < 0) {
            return null;
        }
        return collection instanceof List ? (T) ((List) collection).get(i2) : (T) IterUtil.get(collection.iterator(), i2);
    }

    public static <T> List<T> getAny(Collection<T> collection, int... iArr) {
        int size = collection.size();
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        if (collection instanceof List) {
            List list = (List) collection;
            int length = iArr.length;
            while (i2 < length) {
                int i3 = iArr[i2];
                if (i3 < 0) {
                    i3 += size;
                }
                arrayList.add(list.get(i3));
                i2++;
            }
        } else {
            Object[] array = collection.toArray();
            int length2 = iArr.length;
            while (i2 < length2) {
                int i4 = iArr[i2];
                if (i4 < 0) {
                    i4 += size;
                }
                arrayList.add(array[i4]);
                i2++;
            }
        }
        return arrayList;
    }

    @Deprecated
    public static Class<?> getElementType(Iterable<?> iterable) {
        return IterUtil.getElementType(iterable);
    }

    public static List<Object> getFieldValues(Iterable<?> iterable, String str) {
        return getFieldValues(iterable, str, false);
    }

    public static <T> T getFirst(Iterable<T> iterable) {
        return (T) IterUtil.getFirst(iterable);
    }

    public static <T> T getLast(Collection<T> collection) {
        return (T) get(collection, -1);
    }

    public static <T> List<List<T>> group(Collection<T> collection, Hash32<T> hash32) {
        ArrayList arrayList = new ArrayList();
        if (isEmpty((Collection<?>) collection)) {
            return arrayList;
        }
        if (hash32 == null) {
            hash32 = new Hash32() { // from class: cn.hutool.core.collection.x
                @Override // cn.hutool.core.lang.hash.Hash32, cn.hutool.core.lang.hash.Hash
                public /* synthetic */ Number hash(Object obj) {
                    return d0.b.a(this, obj);
                }

                @Override // cn.hutool.core.lang.hash.Hash32
                public final int hash32(Object obj) {
                    return CollUtil.lambda$group$5(obj);
                }
            };
        }
        for (T t2 : collection) {
            int iHash32 = hash32.hash32(t2);
            if (arrayList.size() - 1 < iHash32) {
                while (arrayList.size() - 1 < iHash32) {
                    arrayList.add(null);
                }
                arrayList.set(iHash32, newArrayList(t2));
            } else {
                List list = (List) arrayList.get(iHash32);
                if (list == null) {
                    arrayList.set(iHash32, newArrayList(t2));
                } else {
                    list.add(t2);
                }
            }
        }
        return arrayList;
    }

    public static <T> List<List<T>> groupByField(Collection<T> collection, final String str) {
        return group(collection, new Hash32<T>() { // from class: cn.hutool.core.collection.CollUtil.1
            private final List<Object> fieldNameList = new ArrayList();

            @Override // cn.hutool.core.lang.hash.Hash32, cn.hutool.core.lang.hash.Hash
            public /* synthetic */ Number hash(Object obj) {
                return d0.b.a(this, obj);
            }

            @Override // cn.hutool.core.lang.hash.Hash32
            public int hash32(T t2) throws UtilException {
                if (t2 == null || !BeanUtil.isBean(t2.getClass())) {
                    return 0;
                }
                Object fieldValue = ReflectUtil.getFieldValue(t2, str);
                int iIndexOf = this.fieldNameList.indexOf(fieldValue);
                if (iIndexOf >= 0) {
                    return iIndexOf;
                }
                this.fieldNameList.add(fieldValue);
                return this.fieldNameList.size() - 1;
            }
        });
    }

    public static boolean hasNull(Iterable<?> iterable) {
        return IterUtil.hasNull(iterable);
    }

    public static <T> int indexOf(Collection<T> collection, Matcher<T> matcher) {
        if (!isNotEmpty((Collection<?>) collection)) {
            return -1;
        }
        int i2 = 0;
        for (T t2 : collection) {
            if (matcher == null || matcher.match(t2)) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public static <T> int[] indexOfAll(Collection<T> collection, Matcher<T> matcher) {
        ArrayList arrayList = new ArrayList();
        if (collection != null) {
            int i2 = 0;
            for (T t2 : collection) {
                if (matcher == null || matcher.match(t2)) {
                    arrayList.add(Integer.valueOf(i2));
                }
                i2++;
            }
        }
        return (int[]) Convert.convert(int[].class, (Object) arrayList);
    }

    public static <T> Collection<T> intersection(Collection<T> collection, Collection<T> collection2) {
        if (!isNotEmpty((Collection<?>) collection) || !isNotEmpty((Collection<?>) collection2)) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList(Math.min(collection.size(), collection2.size()));
        Map mapCountMap = countMap(collection);
        Map mapCountMap2 = countMap(collection2);
        for (Object obj : newHashSet(collection2)) {
            int iMin = Math.min(Convert.toInt(mapCountMap.get(obj), 0).intValue(), Convert.toInt(mapCountMap2.get(obj), 0).intValue());
            for (int i2 = 0; i2 < iMin; i2++) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @SafeVarargs
    public static <T> Set<T> intersectionDistinct(Collection<T> collection, Collection<T> collection2, Collection<T>... collectionArr) {
        if (isEmpty((Collection<?>) collection) || isEmpty((Collection<?>) collection2)) {
            return new LinkedHashSet();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(collection);
        if (ArrayUtil.isNotEmpty((Object[]) collectionArr)) {
            for (Collection<T> collection3 : collectionArr) {
                if (!isNotEmpty((Collection<?>) collection3)) {
                    return new LinkedHashSet();
                }
                linkedHashSet.retainAll(collection3);
            }
        }
        linkedHashSet.retainAll(collection2);
        return linkedHashSet;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEqualList(Collection<?> collection, Collection<?> collection2) {
        if (collection == collection2) {
            return true;
        }
        if (collection == null || collection2 == null || collection.size() != collection2.size()) {
            return false;
        }
        return IterUtil.isEqualList(collection, collection2);
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static <T> String join(Iterable<T> iterable, CharSequence charSequence, Function<T, ? extends CharSequence> function) {
        if (iterable == null) {
            return null;
        }
        return IterUtil.join(iterable.iterator(), charSequence, function);
    }

    public static <K> Set<K> keySet(Collection<Map<K, ?>> collection) {
        if (isEmpty((Collection<?>) collection)) {
            return new HashSet();
        }
        HashSet hashSet = new HashSet(collection.size() * 16);
        Iterator<Map<K, ?>> it = collection.iterator();
        while (it.hasNext()) {
            hashSet.addAll(it.next().keySet());
        }
        return hashSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$create$0(Object obj, Object obj2) {
        return obj instanceof Comparable ? ((Comparable) obj).compareTo(obj2) : CompareUtil.compare(obj.toString(), obj2.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$filterNew$1(Filter filter, Object obj) {
        if (filter.accept(obj)) {
            return obj;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$findOneByField$3(String str, Object obj, Object obj2) {
        return obj2 instanceof Map ? ObjectUtil.equal(((Map) obj2).get(str), obj) : ObjectUtil.equal(ReflectUtil.getFieldValue(obj2, str), obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$getFieldValues$2(String str, Object obj) {
        return obj instanceof Map ? ((Map) obj).get(str) : ReflectUtil.getFieldValue(obj, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$group$5(Object obj) {
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setValueByMap$7(Map map, Function function, final BiConsumer biConsumer, final Object obj) {
        Optional.ofNullable(map.get(function.apply(obj))).ifPresent(new java.util.function.Consumer() { // from class: cn.hutool.core.collection.a0
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                r.b.a(biConsumer, obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$sortEntryToList$4(Map.Entry entry, Map.Entry entry2) {
        Object value = entry.getValue();
        Object value2 = entry2.getValue();
        return value instanceof Comparable ? ((Comparable) value).compareTo(value2) : value.toString().compareTo(value2.toString());
    }

    public static <T> int lastIndexOf(Collection<T> collection, Matcher<T> matcher) {
        if (collection instanceof List) {
            return ListUtil.lastIndexOf((List) collection, matcher);
        }
        int i2 = -1;
        if (isNotEmpty((Collection<?>) collection)) {
            int i3 = 0;
            for (T t2 : collection) {
                if (matcher == null || matcher.match(t2)) {
                    i2 = i3;
                }
                i3++;
            }
        }
        return i2;
    }

    public static <T> List<T> list(boolean z2) {
        return ListUtil.list(z2);
    }

    public static <T, R> List<R> map(Iterable<T> iterable, Function<? super T, ? extends R> function, boolean z2) {
        ArrayList arrayList = new ArrayList();
        if (iterable == null) {
            return arrayList;
        }
        for (T t2 : iterable) {
            if (t2 != null || !z2) {
                Object objApply = function.apply(t2);
                if (objApply != null || !z2) {
                    arrayList.add(objApply);
                }
            }
        }
        return arrayList;
    }

    public static <T extends Comparable<? super T>> T max(Collection<T> collection) {
        if (isEmpty((Collection<?>) collection)) {
            return null;
        }
        return (T) Collections.max(collection);
    }

    public static <T extends Comparable<? super T>> T min(Collection<T> collection) {
        if (isEmpty((Collection<?>) collection)) {
            return null;
        }
        return (T) Collections.min(collection);
    }

    @SafeVarargs
    public static <T> ArrayList<T> newArrayList(T... tArr) {
        return ListUtil.toList(tArr);
    }

    public static <T> BlockingQueue<T> newBlockingQueue(int i2, boolean z2) {
        return z2 ? new LinkedBlockingDeque(i2) : new ArrayBlockingQueue(i2);
    }

    public static <T> CopyOnWriteArrayList<T> newCopyOnWriteArrayList(Collection<T> collection) {
        return ListUtil.toCopyOnWriteArrayList(collection);
    }

    @SafeVarargs
    public static <T> HashSet<T> newHashSet(T... tArr) {
        return set(false, tArr);
    }

    @SafeVarargs
    public static <T> LinkedHashSet<T> newLinkedHashSet(T... tArr) {
        return (LinkedHashSet) set(true, tArr);
    }

    @SafeVarargs
    public static <T> LinkedList<T> newLinkedList(T... tArr) {
        return ListUtil.toLinkedList(tArr);
    }

    public static <T> void padLeft(List<T> list, int i2, T t2) {
        Objects.requireNonNull(list);
        if (list.isEmpty()) {
            padRight(list, i2, t2);
            return;
        }
        for (int size = list.size(); size < i2; size++) {
            list.add(0, t2);
        }
    }

    public static <T> void padRight(Collection<T> collection, int i2, T t2) {
        Objects.requireNonNull(collection);
        for (int size = collection.size(); size < i2; size++) {
            collection.add(t2);
        }
    }

    public static <T> List<T> page(int i2, int i3, List<T> list) {
        return ListUtil.page(i2, i3, list);
    }

    public static <T> List<T> popPart(Stack<T> stack, int i2) {
        if (isEmpty((Collection<?>) stack)) {
            return ListUtil.empty();
        }
        ArrayList arrayList = new ArrayList();
        int size = stack.size();
        int i3 = 0;
        if (size > i2) {
            while (i3 < i2) {
                arrayList.add(stack.pop());
                i3++;
            }
        } else {
            while (i3 < size) {
                arrayList.add(stack.pop());
                i3++;
            }
        }
        return arrayList;
    }

    public static <T extends Collection<E>, E> T removeAny(T t2, E... eArr) {
        t2.removeAll(newHashSet(eArr));
        return t2;
    }

    public static <T extends Collection<E>, E extends CharSequence> T removeBlank(T t2) {
        return (T) filter(t2, new d0());
    }

    public static <T extends Collection<E>, E extends CharSequence> T removeEmpty(T t2) {
        return (T) filter(t2, new z());
    }

    public static <T extends Collection<E>, E> T removeNull(T t2) {
        return (T) filter(t2, new Filter() { // from class: cn.hutool.core.collection.v
            @Override // cn.hutool.core.lang.Filter
            public final boolean accept(Object obj) {
                return cn.hutool.core.annotation.g0.a(obj);
            }
        });
    }

    public static <T extends Collection<E>, E> T removeWithAddIf(T t2, T t3, Predicate<? super E> predicate) {
        Objects.requireNonNull(predicate);
        Iterator<E> it = t2.iterator();
        while (it.hasNext()) {
            E next = it.next();
            if (predicate.test(next)) {
                t3.add(next);
                it.remove();
            }
        }
        return t3;
    }

    public static <T> List<T> reverse(List<T> list) {
        return ListUtil.reverse(list);
    }

    public static <T> List<T> reverseNew(List<T> list) {
        return ListUtil.reverseNew(list);
    }

    public static boolean safeContains(Collection<?> collection, Object obj) {
        try {
            return contains(collection, obj);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    @SafeVarargs
    public static <T> HashSet<T> set(boolean z2, T... tArr) {
        if (tArr == null) {
            return z2 ? new LinkedHashSet() : new HashSet<>();
        }
        int iMax = Math.max(((int) (tArr.length / 0.75f)) + 1, 16);
        HashSet<T> linkedHashSet = z2 ? new LinkedHashSet<>(iMax) : new HashSet<>(iMax);
        Collections.addAll(linkedHashSet, tArr);
        return linkedHashSet;
    }

    public static <T> List<T> setOrAppend(List<T> list, int i2, T t2) {
        return ListUtil.setOrAppend(list, i2, t2);
    }

    public static <E, K, V> void setValueByMap(Iterable<E> iterable, final Map<K, V> map, final Function<E, K> function, final BiConsumer<E, V> biConsumer) {
        iterable.forEach(new java.util.function.Consumer() { // from class: cn.hutool.core.collection.y
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CollUtil.lambda$setValueByMap$7(map, function, biConsumer, obj);
            }
        });
    }

    public static int size(Object obj) {
        int i2 = 0;
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Map) {
            return ((Map) obj).size();
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).size();
        }
        if (obj instanceof Iterable) {
            return IterUtil.size((Iterable<?>) obj);
        }
        if (obj instanceof Iterator) {
            return IterUtil.size((Iterator<?>) obj);
        }
        if (obj instanceof Enumeration) {
            Enumeration enumeration = (Enumeration) obj;
            while (enumeration.hasMoreElements()) {
                i2++;
                enumeration.nextElement();
            }
            return i2;
        }
        if (ArrayUtil.isArray(obj)) {
            return ArrayUtil.length(obj);
        }
        throw new IllegalArgumentException("Unsupported object type: " + obj.getClass().getName());
    }

    public static <T> List<T> sort(Collection<T> collection, Comparator<? super T> comparator) {
        ArrayList arrayList = new ArrayList(collection);
        arrayList.sort(comparator);
        return arrayList;
    }

    public static <K, V> LinkedHashMap<K, V> sortByEntry(Map<K, V> map, Comparator<Map.Entry<K, V>> comparator) {
        return sortToMap(map.entrySet(), comparator);
    }

    public static List<String> sortByPinyin(Collection<String> collection) {
        return sort(collection, new PinyinComparator());
    }

    public static <T> List<T> sortByProperty(Collection<T> collection, String str) {
        return sort(collection, new PropertyComparator(str));
    }

    public static <K, V> List<Map.Entry<K, V>> sortEntryToList(Collection<Map.Entry<K, V>> collection) {
        LinkedList linkedList = new LinkedList(collection);
        linkedList.sort(new Comparator() { // from class: cn.hutool.core.collection.e0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return CollUtil.lambda$sortEntryToList$4((Map.Entry) obj, (Map.Entry) obj2);
            }
        });
        return linkedList;
    }

    @SafeVarargs
    public static <T> List<T> sortPageAll(int i2, int i3, Comparator<T> comparator, Collection<T>... collectionArr) {
        ArrayList arrayList = new ArrayList(i2 * i3);
        for (Collection<T> collection : collectionArr) {
            arrayList.addAll(collection);
        }
        if (comparator != null) {
            arrayList.sort(comparator);
        }
        return page(i2, i3, arrayList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <K, V> LinkedHashMap<K, V> sortToMap(Collection<Map.Entry<K, V>> collection, Comparator<Map.Entry<K, V>> comparator) {
        LinkedList<Map.Entry> linkedList = new LinkedList(collection);
        linkedList.sort(comparator);
        MapFieldLite mapFieldLite = (LinkedHashMap<K, V>) new LinkedHashMap();
        for (Map.Entry entry : linkedList) {
            mapFieldLite.put(entry.getKey(), entry.getValue());
        }
        return mapFieldLite;
    }

    public static <T> List<List<T>> split(Collection<T> collection, int i2) {
        ArrayList arrayList = new ArrayList();
        if (isEmpty((Collection<?>) collection)) {
            return arrayList;
        }
        int iMin = Math.min(collection.size(), i2);
        ArrayList arrayList2 = new ArrayList(iMin);
        for (T t2 : collection) {
            if (arrayList2.size() >= i2) {
                arrayList.add(arrayList2);
                arrayList2 = new ArrayList(iMin);
            }
            arrayList2.add(t2);
        }
        arrayList.add(arrayList2);
        return arrayList;
    }

    @Deprecated
    public static <T> List<List<T>> splitList(List<T> list, int i2) {
        return ListUtil.partition(list, i2);
    }

    public static <T> List<T> sub(List<T> list, int i2, int i3) {
        return ListUtil.sub(list, i2, i3);
    }

    public static <T> Collection<T> subtract(Collection<T> collection, Collection<T> collection2) {
        Collection<T> collectionCreate = (Collection) ObjectUtil.clone(collection);
        if (collectionCreate == null) {
            try {
                collectionCreate = create(collection.getClass());
                collectionCreate.addAll(collection);
            } catch (UnsupportedOperationException unused) {
                Collection<T> collectionCreate2 = create(AbstractCollection.class);
                collectionCreate2.addAll(collection);
                collectionCreate2.removeAll(collection2);
                return collectionCreate2;
            }
        }
        collectionCreate.removeAll(collection2);
        return collectionCreate;
    }

    public static <T> List<T> subtractToList(Collection<T> collection, Collection<T> collection2) {
        if (isEmpty((Collection<?>) collection)) {
            return ListUtil.empty();
        }
        if (isEmpty((Collection<?>) collection2)) {
            return ListUtil.list(true, (Collection) collection);
        }
        LinkedList linkedList = new LinkedList();
        HashSet hashSet = new HashSet(collection2);
        for (T t2 : collection) {
            if (!hashSet.contains(t2)) {
                linkedList.add(t2);
            }
        }
        return linkedList;
    }

    public static <E> Collection<E> toCollection(Iterable<E> iterable) {
        return iterable instanceof Collection ? (Collection) iterable : newArrayList(iterable.iterator());
    }

    @SafeVarargs
    public static <T> ArrayList<T> toList(T... tArr) {
        return ListUtil.toList(tArr);
    }

    public static <K, V> Map<K, List<V>> toListMap(Iterable<? extends Map<K, V>> iterable) {
        return MapUtil.toListMap(iterable);
    }

    public static <K, V> HashMap<K, V> toMap(Iterable<Map.Entry<K, V>> iterable) {
        return IterUtil.toMap(iterable);
    }

    public static <K, V> List<Map<K, V>> toMapList(Map<K, ? extends Iterable<V>> map) {
        return MapUtil.toMapList(map);
    }

    public static <T> TreeSet<T> toTreeSet(Collection<T> collection, Comparator<T> comparator) {
        TreeSet<T> treeSet = new TreeSet<>((Comparator<? super T>) comparator);
        treeSet.addAll(collection);
        return treeSet;
    }

    public static <F, T> Collection<T> trans(Collection<F> collection, Function<? super F, ? extends T> function) {
        return new TransCollection(collection, function);
    }

    public static <T> Collection<T> union(Collection<T> collection, Collection<T> collection2) {
        if (isEmpty((Collection<?>) collection) && isEmpty((Collection<?>) collection2)) {
            return new ArrayList();
        }
        if (isEmpty((Collection<?>) collection)) {
            return new ArrayList(collection2);
        }
        if (isEmpty((Collection<?>) collection2)) {
            return new ArrayList(collection);
        }
        ArrayList arrayList = new ArrayList(Math.max(collection.size(), collection2.size()));
        Map mapCountMap = countMap(collection);
        Map mapCountMap2 = countMap(collection2);
        HashSet hashSetNewHashSet = newHashSet(collection2);
        hashSetNewHashSet.addAll(collection);
        for (Object obj : hashSetNewHashSet) {
            int iMax = Math.max(Convert.toInt(mapCountMap.get(obj), 0).intValue(), Convert.toInt(mapCountMap2.get(obj), 0).intValue());
            for (int i2 = 0; i2 < iMax; i2++) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @SafeVarargs
    public static <T> List<T> unionAll(Collection<T> collection, Collection<T> collection2, Collection<T>... collectionArr) {
        if (isEmpty((Collection<?>) collection) && isEmpty((Collection<?>) collection2) && ArrayUtil.isEmpty((Object[]) collectionArr)) {
            return new ArrayList(0);
        }
        int size = size(collection) + 0 + size(collection2);
        if (collectionArr != null) {
            for (Collection<T> collection3 : collectionArr) {
                size += size(collection3);
            }
        }
        ArrayList arrayList = new ArrayList(size);
        if (collection != null) {
            arrayList.addAll(collection);
        }
        if (collection2 != null) {
            arrayList.addAll(collection2);
        }
        if (collectionArr == null) {
            return arrayList;
        }
        for (Collection<T> collection4 : collectionArr) {
            if (collection4 != null) {
                arrayList.addAll(collection4);
            }
        }
        return arrayList;
    }

    @SafeVarargs
    public static <T> Set<T> unionDistinct(Collection<T> collection, Collection<T> collection2, Collection<T>... collectionArr) {
        LinkedHashSet linkedHashSet = isEmpty((Collection<?>) collection) ? new LinkedHashSet() : new LinkedHashSet(collection);
        if (isNotEmpty((Collection<?>) collection2)) {
            linkedHashSet.addAll(collection2);
        }
        if (ArrayUtil.isNotEmpty((Object[]) collectionArr)) {
            for (Collection<T> collection3 : collectionArr) {
                if (!isEmpty((Collection<?>) collection3)) {
                    linkedHashSet.addAll(collection3);
                }
            }
        }
        return linkedHashSet;
    }

    public static <T> Collection<T> unmodifiable(Collection<? extends T> collection) {
        return Collections.unmodifiableCollection(collection);
    }

    public static <V> List<V> values(Collection<Map<?, V>> collection) {
        ArrayList arrayList = new ArrayList();
        Iterator<Map<?, V>> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.addAll(it.next().values());
        }
        return arrayList;
    }

    public static <K, V> ArrayList<V> valuesOfKeys(Map<K, V> map, K... kArr) {
        return MapUtil.valuesOfKeys(map, new ArrayIter((Object[]) kArr));
    }

    public static Map<String, String> zip(String str, String str2, String str3, boolean z2) {
        return ArrayUtil.zip(CharSequenceUtil.splitToArray(str, str3), CharSequenceUtil.splitToArray(str2, str3), z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> Collection<T> addAll(Collection<T> collection, Object obj, Type type) {
        Iterator<String> it;
        Iterator<String> arrayIter;
        if (collection != 0 && obj != null) {
            if (TypeUtil.isUnknown(type)) {
                type = Object.class;
            }
            if (obj instanceof Iterator) {
                it = (Iterator) obj;
            } else if (!(obj instanceof Iterable)) {
                if (obj instanceof Enumeration) {
                    arrayIter = new EnumerationIter<>((Enumeration) obj);
                } else if (ArrayUtil.isArray(obj)) {
                    arrayIter = new ArrayIter<>(obj);
                } else {
                    it = obj instanceof CharSequence ? CharSequenceUtil.splitTrim((CharSequence) CharSequenceUtil.unWrap((CharSequence) obj, '[', ']'), ',').iterator() : newArrayList(obj).iterator();
                }
                it = arrayIter;
            } else if ((obj instanceof Map) && BeanUtil.isBean(TypeUtil.getClass(type))) {
                arrayIter = new ArrayIter<>((String[]) new Object[]{obj});
                it = arrayIter;
            } else {
                it = ((Iterable) obj).iterator();
            }
            ConverterRegistry converterRegistry = ConverterRegistry.getInstance();
            while (it.hasNext()) {
                collection.add(converterRegistry.convert(type, it.next()));
            }
        }
        return collection;
    }

    public static <T> boolean contains(Collection<T> collection, Predicate<? super T> predicate) {
        if (isEmpty((Collection<?>) collection)) {
            return false;
        }
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            if (predicate.test(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static <T> Collection<T> create(Class<?> cls, Class<T> cls2) {
        if (cls.isAssignableFrom(AbstractCollection.class)) {
            return new ArrayList();
        }
        if (cls.isAssignableFrom(HashSet.class)) {
            return new HashSet();
        }
        if (cls.isAssignableFrom(LinkedHashSet.class)) {
            return new LinkedHashSet();
        }
        if (cls.isAssignableFrom(TreeSet.class)) {
            return new TreeSet(new Comparator() { // from class: cn.hutool.core.collection.b0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return CollUtil.lambda$create$0(obj, obj2);
                }
            });
        }
        if (cls.isAssignableFrom(EnumSet.class)) {
            return EnumSet.noneOf((Class) Assert.notNull(cls2));
        }
        if (cls.isAssignableFrom(ArrayList.class)) {
            return new ArrayList();
        }
        if (cls.isAssignableFrom(LinkedList.class)) {
            return new LinkedList();
        }
        try {
            return (Collection) ReflectUtil.newInstance(cls, new Object[0]);
        } catch (Exception e2) {
            Class<? super Object> superclass = cls.getSuperclass();
            if (superclass == null || cls == superclass) {
                throw new UtilException(e2);
            }
            return create(superclass);
        }
    }

    public static <T extends Collection<E>, E> T defaultIfEmpty(T t2, Supplier<? extends T> supplier) {
        return isEmpty((Collection<?>) t2) ? (T) supplier.get() : t2;
    }

    public static <T> List<T> emptyIfNull(List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }

    public static List<Object> extract(Iterable<?> iterable, final Editor<Object> editor, boolean z2) {
        editor.getClass();
        return map(iterable, new Function() { // from class: cn.hutool.core.collection.g0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return editor.edit(obj);
            }
        }, z2);
    }

    public static <T> void forEach(Iterator<T> it, Consumer<T> consumer) {
        if (it == null) {
            return;
        }
        int i2 = 0;
        while (it.hasNext()) {
            consumer.accept(it.next(), i2);
            i2++;
        }
    }

    @Deprecated
    public static Class<?> getElementType(Iterator<?> it) {
        return IterUtil.getElementType(it);
    }

    public static List<Object> getFieldValues(Iterable<?> iterable, final String str, boolean z2) {
        return map(iterable, new Function() { // from class: cn.hutool.core.collection.c0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CollUtil.lambda$getFieldValues$2(str, obj);
            }
        }, z2);
    }

    public static <T> T getFirst(Iterator<T> it) {
        return (T) IterUtil.getFirst(it);
    }

    public static boolean isEmpty(Iterable<?> iterable) {
        return IterUtil.isEmpty(iterable);
    }

    public static boolean isNotEmpty(Iterable<?> iterable) {
        return IterUtil.isNotEmpty(iterable);
    }

    public static <T> String join(Iterable<T> iterable, CharSequence charSequence) {
        if (iterable == null) {
            return null;
        }
        return IterUtil.join(iterable.iterator(), charSequence);
    }

    @SafeVarargs
    public static <T> List<T> list(boolean z2, T... tArr) {
        return ListUtil.list(z2, tArr);
    }

    public static <T> ArrayList<T> newArrayList(Collection<T> collection) {
        return ListUtil.toList((Collection) collection);
    }

    public static <T> HashSet<T> newHashSet(Collection<T> collection) {
        return newHashSet(false, (Collection) collection);
    }

    public static List<String> sortByPinyin(List<String> list) {
        return ListUtil.sortByPinyin(list);
    }

    public static <T> List<T> sortByProperty(List<T> list, String str) {
        return ListUtil.sortByProperty(list, str);
    }

    public static <T> List<T> sub(List<T> list, int i2, int i3, int i4) {
        return ListUtil.sub(list, i2, i3, i4);
    }

    public static HashMap<Object, Object> toMap(Object[] objArr) {
        return MapUtil.of(objArr);
    }

    public static <K, V> ArrayList<V> valuesOfKeys(Map<K, V> map, Iterable<K> iterable) {
        return valuesOfKeys(map, iterable.iterator());
    }

    public static Map<String, String> zip(String str, String str2, String str3) {
        return zip(str, str2, str3, false);
    }

    public static <T> List<T> getFieldValues(Iterable<?> iterable, String str, Class<T> cls) {
        return Convert.toList(cls, getFieldValues(iterable, str));
    }

    public static boolean isEmpty(Iterator<?> it) {
        return IterUtil.isEmpty(it);
    }

    public static boolean isNotEmpty(Iterator<?> it) {
        return IterUtil.isNotEmpty(it);
    }

    public static <T> String join(Iterable<T> iterable, CharSequence charSequence, String str, String str2) {
        if (iterable == null) {
            return null;
        }
        return IterUtil.join(iterable.iterator(), charSequence, str, str2);
    }

    public static <T> List<T> list(boolean z2, Collection<T> collection) {
        return ListUtil.list(z2, (Collection) collection);
    }

    public static <T> ArrayList<T> newArrayList(Iterable<T> iterable) {
        return ListUtil.toList(iterable);
    }

    public static <T> HashSet<T> newHashSet(boolean z2, Collection<T> collection) {
        return z2 ? new LinkedHashSet(collection) : new HashSet<>(collection);
    }

    public static <T> List<T> sort(List<T> list, Comparator<? super T> comparator) {
        return ListUtil.sort(list, comparator);
    }

    public static <T> List<T> sub(Collection<T> collection, int i2, int i3) {
        return sub(collection, i2, i3, 1);
    }

    public static <K, V> Map<K, V> toMap(Iterable<V> iterable, Map<K, V> map, Func1<V, K> func1) {
        return IterUtil.toMap(iterable == null ? null : iterable.iterator(), map, func1);
    }

    public static <K, V> ArrayList<V> valuesOfKeys(Map<K, V> map, Iterator<K> it) {
        return MapUtil.valuesOfKeys(map, it);
    }

    public static <K, V> Map<K, V> zip(Collection<K> collection, Collection<V> collection2) {
        if (!isEmpty((Collection<?>) collection) && !isEmpty((Collection<?>) collection2)) {
            int iMin = Math.min(collection.size(), collection2.size());
            HashMap mapNewHashMap = MapUtil.newHashMap(iMin);
            Iterator<K> it = collection.iterator();
            Iterator<V> it2 = collection2.iterator();
            while (iMin > 0) {
                mapNewHashMap.put(it.next(), it2.next());
                iMin--;
            }
            return mapNewHashMap;
        }
        return MapUtil.empty();
    }

    public static <T> void forEach(Enumeration<T> enumeration, Consumer<T> consumer) {
        if (enumeration == null) {
            return;
        }
        int i2 = 0;
        while (enumeration.hasMoreElements()) {
            consumer.accept(enumeration.nextElement(), i2);
            i2++;
        }
    }

    public static boolean isEmpty(Enumeration<?> enumeration) {
        return enumeration == null || !enumeration.hasMoreElements();
    }

    public static boolean isNotEmpty(Enumeration<?> enumeration) {
        return enumeration != null && enumeration.hasMoreElements();
    }

    @Deprecated
    public static <T> String join(Iterator<T> it, CharSequence charSequence) {
        return IterUtil.join(it, charSequence);
    }

    public static <T> List<T> list(boolean z2, Iterable<T> iterable) {
        return ListUtil.list(z2, iterable);
    }

    public static <T> ArrayList<T> newArrayList(Iterator<T> it) {
        return ListUtil.toList(it);
    }

    public static <T> HashSet<T> newHashSet(boolean z2, Iterator<T> it) {
        if (it == null) {
            return set(z2, null);
        }
        HashSet<T> linkedHashSet = z2 ? new LinkedHashSet<>() : new HashSet<>();
        while (it.hasNext()) {
            linkedHashSet.add(it.next());
        }
        return linkedHashSet;
    }

    public static <K, V> TreeMap<K, V> sort(Map<K, V> map, Comparator<? super K> comparator) {
        TreeMap<K, V> treeMap = new TreeMap<>(comparator);
        treeMap.putAll(map);
        return treeMap;
    }

    public static <T> List<T> sub(Collection<T> collection, int i2, int i3, int i4) {
        if (isEmpty((Collection<?>) collection)) {
            return ListUtil.empty();
        }
        return sub(collection instanceof List ? (List) collection : ListUtil.toList((Collection) collection), i2, i3, i4);
    }

    public static <K, V, E> Map<K, V> toMap(Iterable<E> iterable, Map<K, V> map, Func1<E, K> func1, Func1<E, V> func12) {
        return IterUtil.toMap(iterable == null ? null : iterable.iterator(), map, func1, func12);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return MapUtil.isEmpty(map);
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return MapUtil.isNotEmpty(map);
    }

    public static <T> List<T> list(boolean z2, Iterator<T> it) {
        return ListUtil.list(z2, it);
    }

    public static <T> ArrayList<T> newArrayList(Enumeration<T> enumeration) {
        return ListUtil.toList(enumeration);
    }

    public static <T, K> List<T> distinct(Collection<T> collection, Function<T, K> function, boolean z2) {
        if (isEmpty((Collection<?>) collection)) {
            return new ArrayList();
        }
        UniqueKeySet uniqueKeySet = new UniqueKeySet(true, (Function) function);
        if (z2) {
            uniqueKeySet.addAll(collection);
        } else {
            uniqueKeySet.addAllIfAbsent(collection);
        }
        return new ArrayList(uniqueKeySet);
    }

    public static <K, V> void forEach(Map<K, V> map, KVConsumer<K, V> kVConsumer) {
        if (map == null) {
            return;
        }
        int i2 = 0;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            kVConsumer.accept(entry.getKey(), entry.getValue(), i2);
            i2++;
        }
    }

    public static <T> List<T> list(boolean z2, Enumeration<T> enumeration) {
        return ListUtil.list(z2, enumeration);
    }

    public static <T> List<T> popPart(Deque<T> deque, int i2) {
        if (isEmpty((Collection<?>) deque)) {
            return ListUtil.empty();
        }
        ArrayList arrayList = new ArrayList();
        int size = deque.size();
        int i3 = 0;
        if (size > i2) {
            while (i3 < i2) {
                arrayList.add(deque.pop());
                i3++;
            }
        } else {
            while (i3 < size) {
                arrayList.add(deque.pop());
                i3++;
            }
        }
        return arrayList;
    }

    public static <T> HashSet<T> newHashSet(boolean z2, Enumeration<T> enumeration) {
        if (enumeration == null) {
            return set(z2, null);
        }
        HashSet<T> linkedHashSet = z2 ? new LinkedHashSet<>() : new HashSet<>();
        while (enumeration.hasMoreElements()) {
            linkedHashSet.add(enumeration.nextElement());
        }
        return linkedHashSet;
    }

    public static <T extends Collection<E>, E> List<E> removeWithAddIf(T t2, Predicate<? super E> predicate) {
        ArrayList arrayList = new ArrayList();
        removeWithAddIf(t2, arrayList, predicate);
        return arrayList;
    }

    @SafeVarargs
    public static <T> Collection<T> intersection(Collection<T> collection, Collection<T> collection2, Collection<T>... collectionArr) {
        Collection<T> collectionIntersection = intersection(collection, collection2);
        if (isEmpty((Collection<?>) collectionIntersection)) {
            return collectionIntersection;
        }
        for (Collection<T> collection3 : collectionArr) {
            collectionIntersection = intersection(collectionIntersection, collection3);
            if (isEmpty((Collection<?>) collectionIntersection)) {
                return collectionIntersection;
            }
        }
        return collectionIntersection;
    }

    @SafeVarargs
    public static <T> Collection<T> union(Collection<T> collection, Collection<T> collection2, Collection<T>... collectionArr) {
        Collection<T> collectionUnion = union(collection, collection2);
        for (Collection<T> collection3 : collectionArr) {
            if (!isEmpty((Collection<?>) collection3)) {
                collectionUnion = union(collectionUnion, collection3);
            }
        }
        return collectionUnion;
    }

    public static <T> Collection<T> addAll(Collection<T> collection, Iterator<T> it) {
        if (collection != null && it != null) {
            while (it.hasNext()) {
                collection.add(it.next());
            }
        }
        return collection;
    }

    public static <T> Collection<T> addAll(Collection<T> collection, Iterable<T> iterable) {
        return iterable == null ? collection : addAll((Collection) collection, (Iterator) iterable.iterator());
    }

    public static <T> Collection<T> addAll(Collection<T> collection, Enumeration<T> enumeration) {
        if (collection != null && enumeration != null) {
            while (enumeration.hasMoreElements()) {
                collection.add(enumeration.nextElement());
            }
        }
        return collection;
    }

    public static <T> Collection<T> addAll(Collection<T> collection, T[] tArr) {
        if (collection != null && tArr != null) {
            Collections.addAll(collection, tArr);
        }
        return collection;
    }
}

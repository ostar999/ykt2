package cn.hutool.core.collection;

import cn.hutool.core.comparator.PinyinComparator;
import cn.hutool.core.comparator.PropertyComparator;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Matcher;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PageUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.RandomAccess;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class ListUtil {
    public static <T> List<T> empty() {
        return Collections.emptyList();
    }

    public static <T> int[] indexOfAll(List<T> list, Matcher<T> matcher) {
        return CollUtil.indexOfAll(list, matcher);
    }

    public static <T> int lastIndexOf(List<T> list, Matcher<T> matcher) {
        int size;
        if (list == null || (size = list.size()) <= 0) {
            return -1;
        }
        for (int i2 = size - 1; i2 >= 0; i2--) {
            if (matcher == null || matcher.match(list.get(i2))) {
                return i2;
            }
        }
        return -1;
    }

    public static <T> List<T> list(boolean z2) {
        return z2 ? new LinkedList() : new ArrayList();
    }

    @SafeVarargs
    public static <T> List<T> of(T... tArr) {
        return ArrayUtil.isEmpty((Object[]) tArr) ? Collections.emptyList() : Collections.unmodifiableList(toList(tArr));
    }

    public static <T> List<T> page(int i2, int i3, List<T> list) {
        if (CollUtil.isEmpty((Collection<?>) list)) {
            return new ArrayList(0);
        }
        int size = list.size();
        if (size <= i3) {
            return i2 < PageUtil.getFirstPageNo() + 1 ? unmodifiable(list) : new ArrayList(0);
        }
        if ((i2 - PageUtil.getFirstPageNo()) * i3 > size) {
            return new ArrayList(0);
        }
        int[] iArrTransToStartEnd = PageUtil.transToStartEnd(i2, i3);
        if (iArrTransToStartEnd[1] > size) {
            iArrTransToStartEnd[1] = size;
            if (iArrTransToStartEnd[0] > size) {
                return new ArrayList(0);
            }
        }
        return sub(list, iArrTransToStartEnd[0], iArrTransToStartEnd[1]);
    }

    public static <T> List<List<T>> partition(List<T> list, int i2) {
        return CollUtil.isEmpty((Collection<?>) list) ? empty() : list instanceof RandomAccess ? new RandomAccessPartition(list, i2) : new Partition(list, i2);
    }

    public static <T> List<T> reverse(List<T> list) {
        Collections.reverse(list);
        return list;
    }

    public static <T> List<T> reverseNew(List<T> list) {
        List arrayList = (List) ObjectUtil.clone(list);
        if (arrayList == null) {
            arrayList = new ArrayList(list);
        }
        try {
            return reverse(arrayList);
        } catch (UnsupportedOperationException unused) {
            return reverse(list(false, (Collection) list));
        }
    }

    public static <T> List<T> setOrAppend(List<T> list, int i2, T t2) throws IllegalArgumentException {
        Assert.notNull(list, "List must be not null !", new Object[0]);
        if (i2 < list.size()) {
            list.set(i2, t2);
        } else {
            list.add(t2);
        }
        return list;
    }

    public static <T> List<T> setOrPadding(List<T> list, int i2, T t2) {
        return setOrPadding(list, i2, t2, null);
    }

    public static <T> List<T> sort(List<T> list, Comparator<? super T> comparator) {
        if (CollUtil.isEmpty((Collection<?>) list)) {
            return list;
        }
        list.sort(comparator);
        return list;
    }

    public static List<String> sortByPinyin(List<String> list) {
        return sort(list, new PinyinComparator());
    }

    public static <T> List<T> sortByProperty(List<T> list, String str) {
        return sort(list, new PropertyComparator(str));
    }

    public static <T> List<List<T>> split(List<T> list, int i2) {
        return partition(list, i2);
    }

    public static <T> List<List<T>> splitAvg(List<T> list, int i2) {
        return CollUtil.isEmpty((Collection<?>) list) ? empty() : list instanceof RandomAccess ? new RandomAccessAvgPartition(list, i2) : new AvgPartition(list, i2);
    }

    public static <T> List<T> sub(List<T> list, int i2, int i3) {
        return sub(list, i2, i3, 1);
    }

    public static <T> void swapElement(List<T> list, T t2, T t3) {
        int iIndexOf;
        if (!CollUtil.isNotEmpty((Collection<?>) list) || (iIndexOf = list.indexOf(t3)) < 0) {
            return;
        }
        swapTo(list, t2, Integer.valueOf(iIndexOf));
    }

    public static <T> void swapTo(List<T> list, T t2, Integer num) {
        int iIndexOf;
        if (!CollUtil.isNotEmpty((Collection<?>) list) || (iIndexOf = list.indexOf(t2)) < 0) {
            return;
        }
        Collections.swap(list, iIndexOf, num.intValue());
    }

    public static <T> CopyOnWriteArrayList<T> toCopyOnWriteArrayList(Collection<T> collection) {
        return collection == null ? new CopyOnWriteArrayList<>() : new CopyOnWriteArrayList<>(collection);
    }

    @SafeVarargs
    public static <T> LinkedList<T> toLinkedList(T... tArr) {
        return (LinkedList) list(true, (Object[]) tArr);
    }

    @SafeVarargs
    public static <T> ArrayList<T> toList(T... tArr) {
        return (ArrayList) list(false, (Object[]) tArr);
    }

    public static <T> List<T> unmodifiable(List<T> list) {
        if (list == null) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }

    @SafeVarargs
    public static <T> List<T> list(boolean z2, T... tArr) {
        if (ArrayUtil.isEmpty((Object[]) tArr)) {
            return list(z2);
        }
        List<T> linkedList = z2 ? new LinkedList<>() : new ArrayList<>(tArr.length);
        Collections.addAll(linkedList, tArr);
        return linkedList;
    }

    public static <T> List<T> setOrPadding(List<T> list, int i2, T t2, T t3) throws IllegalArgumentException {
        Assert.notNull(list, "List must be not null !", new Object[0]);
        int size = list.size();
        if (i2 < size) {
            list.set(i2, t2);
        } else {
            Validator.checkIndexLimit(i2, list.size());
            while (size < i2) {
                list.add(t3);
                size++;
            }
            list.add(t2);
        }
        return list;
    }

    public static <T> List<T> sub(List<T> list, int i2, int i3, int i4) {
        if (list == null) {
            return null;
        }
        if (list.isEmpty()) {
            return new ArrayList(0);
        }
        int size = list.size();
        if (i2 < 0) {
            i2 += size;
        }
        if (i3 < 0) {
            i3 += size;
        }
        if (i2 == size) {
            return new ArrayList(0);
        }
        if (i2 <= i3) {
            int i5 = i3;
            i3 = i2;
            i2 = i5;
        }
        if (i2 <= size) {
            size = i2;
        } else if (i3 >= size) {
            return new ArrayList(0);
        }
        if (i4 < 1) {
            i4 = 1;
        }
        ArrayList arrayList = new ArrayList();
        while (i3 < size) {
            arrayList.add(list.get(i3));
            i3 += i4;
        }
        return arrayList;
    }

    public static <T> ArrayList<T> toList(Collection<T> collection) {
        return (ArrayList) list(false, (Collection) collection);
    }

    public static <T> ArrayList<T> toList(Iterable<T> iterable) {
        return (ArrayList) list(false, (Iterable) iterable);
    }

    public static <T> ArrayList<T> toList(Iterator<T> it) {
        return (ArrayList) list(false, (Iterator) it);
    }

    public static <T> ArrayList<T> toList(Enumeration<T> enumeration) {
        return (ArrayList) list(false, (Enumeration) enumeration);
    }

    public static <T> List<T> list(boolean z2, Collection<T> collection) {
        if (collection == null) {
            return list(z2);
        }
        return z2 ? new LinkedList(collection) : new ArrayList(collection);
    }

    public static <T> List<T> list(boolean z2, Iterable<T> iterable) {
        if (iterable == null) {
            return list(z2);
        }
        return list(z2, iterable.iterator());
    }

    public static <T> List<T> list(boolean z2, Iterator<T> it) {
        List<T> list = list(z2);
        if (it != null) {
            while (it.hasNext()) {
                list.add(it.next());
            }
        }
        return list;
    }

    public static <T> List<T> list(boolean z2, Enumeration<T> enumeration) {
        List<T> list = list(z2);
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                list.add(enumeration.nextElement());
            }
        }
        return list;
    }

    public static <T> void page(List<T> list, int i2, Consumer<List<T>> consumer) {
        if (CollUtil.isEmpty((Collection<?>) list) || i2 <= 0) {
            return;
        }
        int size = list.size();
        int i3 = PageUtil.totalPage(size, i2);
        for (int firstPageNo = PageUtil.getFirstPageNo(); firstPageNo < PageUtil.getFirstPageNo() + i3; firstPageNo++) {
            int[] iArrTransToStartEnd = PageUtil.transToStartEnd(firstPageNo, i2);
            if (iArrTransToStartEnd[1] > size) {
                iArrTransToStartEnd[1] = size;
            }
            consumer.accept(sub(list, iArrTransToStartEnd[0], iArrTransToStartEnd[1]));
        }
    }
}

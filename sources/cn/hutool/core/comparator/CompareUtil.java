package cn.hutool.core.comparator;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

/* loaded from: classes.dex */
public class CompareUtil {
    public static <T> int compare(T t2, T t3, Comparator<T> comparator) {
        return comparator == null ? compare((Comparable) t2, (Comparable) t3) : comparator.compare(t2, t3);
    }

    public static <T, U> Comparator<T> comparingIndexed(Function<? super T, ? extends U> function, U... uArr) {
        return comparingIndexed(function, false, uArr);
    }

    public static <T> Comparator<T> comparingPinyin(Function<T, String> function) {
        return comparingPinyin(function, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$comparingIndexed$2(IndexedComparator indexedComparator, Function function, Object obj, Object obj2) {
        return indexedComparator.compare(function.apply(obj), function.apply(obj2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$comparingPinyin$0(PinyinComparator pinyinComparator, Function function, Object obj, Object obj2) {
        return pinyinComparator.compare((String) function.apply(obj2), (String) function.apply(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$comparingPinyin$1(PinyinComparator pinyinComparator, Function function, Object obj, Object obj2) {
        return pinyinComparator.compare((String) function.apply(obj), (String) function.apply(obj2));
    }

    public static <E extends Comparable<? super E>> Comparator<E> naturalComparator() {
        return ComparableComparator.INSTANCE;
    }

    public static <T, U> Comparator<T> comparingIndexed(final Function<? super T, ? extends U> function, boolean z2, U... uArr) {
        Objects.requireNonNull(function);
        final IndexedComparator indexedComparator = new IndexedComparator(z2, uArr);
        return new Comparator() { // from class: cn.hutool.core.comparator.c
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return CompareUtil.lambda$comparingIndexed$2(indexedComparator, function, obj, obj2);
            }
        };
    }

    public static <T> Comparator<T> comparingPinyin(final Function<T, String> function, boolean z2) {
        Objects.requireNonNull(function);
        final PinyinComparator pinyinComparator = new PinyinComparator();
        return z2 ? new Comparator() { // from class: cn.hutool.core.comparator.a
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return CompareUtil.lambda$comparingPinyin$0(pinyinComparator, function, obj, obj2);
            }
        } : new Comparator() { // from class: cn.hutool.core.comparator.b
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return CompareUtil.lambda$comparingPinyin$1(pinyinComparator, function, obj, obj2);
            }
        };
    }

    public static <T extends Comparable<? super T>> int compare(T t2, T t3) {
        return compare((Comparable) t2, (Comparable) t3, false);
    }

    public static <T extends Comparable<? super T>> int compare(T t2, T t3, boolean z2) {
        if (t2 == t3) {
            return 0;
        }
        if (t2 == null) {
            return z2 ? 1 : -1;
        }
        if (t3 == null) {
            return z2 ? -1 : 1;
        }
        return t2.compareTo(t3);
    }

    public static <T> int compare(T t2, T t3, boolean z2) {
        if (t2 == t3) {
            return 0;
        }
        if (t2 == null) {
            return z2 ? 1 : -1;
        }
        if (t3 == null) {
            return z2 ? -1 : 1;
        }
        if ((t2 instanceof Comparable) && (t3 instanceof Comparable)) {
            return ((Comparable) t2).compareTo(t3);
        }
        if (t2.equals(t3)) {
            return 0;
        }
        int iCompare = Integer.compare(t2.hashCode(), t3.hashCode());
        return iCompare == 0 ? compare(t2.toString(), t3.toString()) : iCompare;
    }
}

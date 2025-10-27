package com.bumptech.glide.repackaged.com.google.common.collect;

import com.bumptech.glide.repackaged.com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;

/* loaded from: classes2.dex */
final class SortedLists {

    public enum KeyAbsentBehavior {
        NEXT_LOWER { // from class: com.bumptech.glide.repackaged.com.google.common.collect.SortedLists.KeyAbsentBehavior.1
            @Override // com.bumptech.glide.repackaged.com.google.common.collect.SortedLists.KeyAbsentBehavior
            public int resultIndex(int i2) {
                return i2 - 1;
            }
        },
        NEXT_HIGHER { // from class: com.bumptech.glide.repackaged.com.google.common.collect.SortedLists.KeyAbsentBehavior.2
            @Override // com.bumptech.glide.repackaged.com.google.common.collect.SortedLists.KeyAbsentBehavior
            public int resultIndex(int i2) {
                return i2;
            }
        },
        INVERTED_INSERTION_INDEX { // from class: com.bumptech.glide.repackaged.com.google.common.collect.SortedLists.KeyAbsentBehavior.3
            @Override // com.bumptech.glide.repackaged.com.google.common.collect.SortedLists.KeyAbsentBehavior
            public int resultIndex(int i2) {
                return ~i2;
            }
        };

        public abstract int resultIndex(int i2);
    }

    public enum KeyPresentBehavior {
        ANY_PRESENT { // from class: com.bumptech.glide.repackaged.com.google.common.collect.SortedLists.KeyPresentBehavior.1
            @Override // com.bumptech.glide.repackaged.com.google.common.collect.SortedLists.KeyPresentBehavior
            public <E> int resultIndex(Comparator<? super E> comparator, E e2, List<? extends E> list, int i2) {
                return i2;
            }
        },
        LAST_PRESENT { // from class: com.bumptech.glide.repackaged.com.google.common.collect.SortedLists.KeyPresentBehavior.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.bumptech.glide.repackaged.com.google.common.collect.SortedLists.KeyPresentBehavior
            public <E> int resultIndex(Comparator<? super E> comparator, E e2, List<? extends E> list, int i2) {
                int size = list.size() - 1;
                while (i2 < size) {
                    int i3 = ((i2 + size) + 1) >>> 1;
                    if (comparator.compare(list.get(i3), e2) > 0) {
                        size = i3 - 1;
                    } else {
                        i2 = i3;
                    }
                }
                return i2;
            }
        },
        FIRST_PRESENT { // from class: com.bumptech.glide.repackaged.com.google.common.collect.SortedLists.KeyPresentBehavior.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.bumptech.glide.repackaged.com.google.common.collect.SortedLists.KeyPresentBehavior
            public <E> int resultIndex(Comparator<? super E> comparator, E e2, List<? extends E> list, int i2) {
                int i3 = 0;
                while (i3 < i2) {
                    int i4 = (i3 + i2) >>> 1;
                    if (comparator.compare(list.get(i4), e2) < 0) {
                        i3 = i4 + 1;
                    } else {
                        i2 = i4;
                    }
                }
                return i3;
            }
        },
        FIRST_AFTER { // from class: com.bumptech.glide.repackaged.com.google.common.collect.SortedLists.KeyPresentBehavior.4
            @Override // com.bumptech.glide.repackaged.com.google.common.collect.SortedLists.KeyPresentBehavior
            public <E> int resultIndex(Comparator<? super E> comparator, E e2, List<? extends E> list, int i2) {
                return KeyPresentBehavior.LAST_PRESENT.resultIndex(comparator, e2, list, i2) + 1;
            }
        },
        LAST_BEFORE { // from class: com.bumptech.glide.repackaged.com.google.common.collect.SortedLists.KeyPresentBehavior.5
            @Override // com.bumptech.glide.repackaged.com.google.common.collect.SortedLists.KeyPresentBehavior
            public <E> int resultIndex(Comparator<? super E> comparator, E e2, List<? extends E> list, int i2) {
                return KeyPresentBehavior.FIRST_PRESENT.resultIndex(comparator, e2, list, i2) - 1;
            }
        };

        public abstract <E> int resultIndex(Comparator<? super E> comparator, E e2, List<? extends E> list, int i2);
    }

    public static <E> int binarySearch(List<? extends E> list, E e2, Comparator<? super E> comparator, KeyPresentBehavior keyPresentBehavior, KeyAbsentBehavior keyAbsentBehavior) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(list);
        Preconditions.checkNotNull(keyPresentBehavior);
        Preconditions.checkNotNull(keyAbsentBehavior);
        if (!(list instanceof RandomAccess)) {
            list = Lists.newArrayList(list);
        }
        int size = list.size() - 1;
        int i2 = 0;
        while (i2 <= size) {
            int i3 = (i2 + size) >>> 1;
            int iCompare = comparator.compare(e2, list.get(i3));
            if (iCompare < 0) {
                size = i3 - 1;
            } else {
                if (iCompare <= 0) {
                    return i2 + keyPresentBehavior.resultIndex(comparator, e2, list.subList(i2, size + 1), i3 - i2);
                }
                i2 = i3 + 1;
            }
        }
        return keyAbsentBehavior.resultIndex(i2);
    }
}

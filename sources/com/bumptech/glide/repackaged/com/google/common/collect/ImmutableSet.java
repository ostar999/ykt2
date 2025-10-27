package com.bumptech.glide.repackaged.com.google.common.collect;

import com.bumptech.glide.repackaged.com.google.common.base.Preconditions;
import com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes2.dex */
public abstract class ImmutableSet<E> extends ImmutableCollection<E> implements Set<E> {

    public static class Builder<E> extends ImmutableCollection.ArrayBasedBuilder<E> {
        public Builder() {
            this(4);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection.ArrayBasedBuilder, com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection.Builder
        public /* bridge */ /* synthetic */ ImmutableCollection.ArrayBasedBuilder add(Object obj) {
            return add((Builder<E>) obj);
        }

        public ImmutableSet<E> build() {
            ImmutableSet<E> immutableSetConstruct = ImmutableSet.construct(this.size, this.contents);
            this.size = immutableSetConstruct.size();
            return immutableSetConstruct;
        }

        public Builder(int i2) {
            super(i2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection.ArrayBasedBuilder, com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection.Builder
        public /* bridge */ /* synthetic */ ImmutableCollection.Builder add(Object obj) {
            return add((Builder<E>) obj);
        }

        @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> addAll(Iterator<? extends E> it) {
            super.addAll((Iterator) it);
            return this;
        }

        @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection.ArrayBasedBuilder, com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> add(E e2) {
            super.add((Builder<E>) e2);
            return this;
        }
    }

    static int chooseTableSize(int i2) {
        if (i2 >= 751619276) {
            Preconditions.checkArgument(i2 < 1073741824, "collection too large");
            return 1073741824;
        }
        int iHighestOneBit = Integer.highestOneBit(i2 - 1) << 1;
        while (iHighestOneBit * 0.7d < i2) {
            iHighestOneBit <<= 1;
        }
        return iHighestOneBit;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> ImmutableSet<E> construct(int i2, Object... objArr) {
        if (i2 == 0) {
            return of();
        }
        if (i2 == 1) {
            return of(objArr[0]);
        }
        int iChooseTableSize = chooseTableSize(i2);
        Object[] objArr2 = new Object[iChooseTableSize];
        int i3 = iChooseTableSize - 1;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < i2; i6++) {
            Object objCheckElementNotNull = ObjectArrays.checkElementNotNull(objArr[i6], i6);
            int iHashCode = objCheckElementNotNull.hashCode();
            int iSmear = Hashing.smear(iHashCode);
            while (true) {
                int i7 = iSmear & i3;
                Object obj = objArr2[i7];
                if (obj == null) {
                    objArr[i4] = objCheckElementNotNull;
                    objArr2[i7] = objCheckElementNotNull;
                    i5 += iHashCode;
                    i4++;
                    break;
                }
                if (obj.equals(objCheckElementNotNull)) {
                    break;
                }
                iSmear++;
            }
        }
        Arrays.fill(objArr, i4, i2, (Object) null);
        if (i4 == 1) {
            return new SingletonImmutableSet(objArr[0], i5);
        }
        if (iChooseTableSize != chooseTableSize(i4)) {
            return construct(i4, objArr);
        }
        if (i4 < objArr.length) {
            objArr = ObjectArrays.arraysCopyOf(objArr, i4);
        }
        return new RegularImmutableSet(objArr, i5, objArr2, i3);
    }

    public static <E> ImmutableSet<E> copyOf(Collection<? extends E> collection) {
        if ((collection instanceof ImmutableSet) && !(collection instanceof ImmutableSortedSet)) {
            ImmutableSet<E> immutableSet = (ImmutableSet) collection;
            if (!immutableSet.isPartialView()) {
                return immutableSet;
            }
        } else if (collection instanceof EnumSet) {
            return copyOfEnumSet((EnumSet) collection);
        }
        Object[] array = collection.toArray();
        return construct(array.length, array);
    }

    private static ImmutableSet copyOfEnumSet(EnumSet enumSet) {
        return ImmutableEnumSet.asImmutable(EnumSet.copyOf(enumSet));
    }

    public static <E> ImmutableSet<E> of() {
        return RegularImmutableSet.EMPTY;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if ((obj instanceof ImmutableSet) && isHashCodeFast() && ((ImmutableSet) obj).isHashCodeFast() && hashCode() != obj.hashCode()) {
            return false;
        }
        return Sets.equalsImpl(this, obj);
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        return Sets.hashCodeImpl(this);
    }

    boolean isHashCodeFast() {
        return false;
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet
    public abstract UnmodifiableIterator<E> iterator();

    public static <E> ImmutableSet<E> of(E e2) {
        return new SingletonImmutableSet(e2);
    }

    public static <E> ImmutableSet<E> of(E e2, E e3, E e4, E e5, E e6) {
        return construct(5, e2, e3, e4, e5, e6);
    }

    public static <E> ImmutableSet<E> copyOf(Iterable<? extends E> iterable) {
        return iterable instanceof Collection ? copyOf((Collection) iterable) : copyOf(iterable.iterator());
    }

    public static <E> ImmutableSet<E> copyOf(Iterator<? extends E> it) {
        if (!it.hasNext()) {
            return of();
        }
        E next = it.next();
        if (!it.hasNext()) {
            return of((Object) next);
        }
        return new Builder().add((Builder) next).addAll((Iterator) it).build();
    }
}

package cn.hutool.core.comparator;

import cn.hutool.core.lang.Chain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public class ComparatorChain<E> implements Chain<Comparator<E>, ComparatorChain<E>>, Comparator<E>, Serializable {
    private static final long serialVersionUID = -2426725788913962429L;
    private final List<Comparator<E>> chain;
    private boolean lock;
    private final BitSet orderingBits;

    public ComparatorChain() {
        this(new ArrayList(), new BitSet());
    }

    private void checkChainIntegrity() {
        if (this.chain.size() == 0) {
            throw new UnsupportedOperationException("ComparatorChains must contain at least one Comparator");
        }
    }

    private void checkLocked() {
        if (this.lock) {
            throw new UnsupportedOperationException("Comparator ordering cannot be changed after the first comparison is performed");
        }
    }

    public static <E> ComparatorChain<E> of(Comparator<E> comparator) {
        return of((Comparator) comparator, false);
    }

    public ComparatorChain<E> addComparator(Comparator<E> comparator) {
        return addComparator(comparator, false);
    }

    @Override // java.util.Comparator
    public int compare(E e2, E e3) throws UnsupportedOperationException {
        if (!this.lock) {
            checkChainIntegrity();
            this.lock = true;
        }
        Iterator<Comparator<E>> it = this.chain.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            int iCompare = it.next().compare(e2, e3);
            if (iCompare != 0) {
                if (true == this.orderingBits.get(i2)) {
                    return iCompare > 0 ? -1 : 1;
                }
                return iCompare;
            }
            i2++;
        }
        return 0;
    }

    @Override // java.util.Comparator
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }
        ComparatorChain comparatorChain = (ComparatorChain) obj;
        return Objects.equals(this.orderingBits, comparatorChain.orderingBits) && this.chain.equals(comparatorChain.chain);
    }

    public int hashCode() {
        List<Comparator<E>> list = this.chain;
        int iHashCode = list != null ? 0 ^ list.hashCode() : 0;
        BitSet bitSet = this.orderingBits;
        return bitSet != null ? iHashCode ^ bitSet.hashCode() : iHashCode;
    }

    public boolean isLocked() {
        return this.lock;
    }

    @Override // java.lang.Iterable
    public Iterator<Comparator<E>> iterator() {
        return this.chain.iterator();
    }

    public ComparatorChain<E> setComparator(int i2, Comparator<E> comparator) throws IndexOutOfBoundsException {
        return setComparator(i2, comparator, false);
    }

    public ComparatorChain<E> setForwardSort(int i2) {
        checkLocked();
        this.orderingBits.clear(i2);
        return this;
    }

    public ComparatorChain<E> setReverseSort(int i2) {
        checkLocked();
        this.orderingBits.set(i2);
        return this;
    }

    public int size() {
        return this.chain.size();
    }

    public ComparatorChain(Comparator<E> comparator) {
        this((Comparator) comparator, false);
    }

    public static <E> ComparatorChain<E> of(Comparator<E> comparator, boolean z2) {
        return new ComparatorChain<>(comparator, z2);
    }

    @Override // cn.hutool.core.lang.Chain
    public ComparatorChain<E> addChain(Comparator<E> comparator) {
        return addComparator(comparator);
    }

    public ComparatorChain<E> addComparator(Comparator<E> comparator, boolean z2) {
        checkLocked();
        this.chain.add(comparator);
        if (z2) {
            this.orderingBits.set(this.chain.size() - 1);
        }
        return this;
    }

    public ComparatorChain<E> setComparator(int i2, Comparator<E> comparator, boolean z2) {
        checkLocked();
        this.chain.set(i2, comparator);
        if (z2) {
            this.orderingBits.set(i2);
        } else {
            this.orderingBits.clear(i2);
        }
        return this;
    }

    public ComparatorChain(Comparator<E> comparator, boolean z2) {
        this.lock = false;
        ArrayList arrayList = new ArrayList(1);
        this.chain = arrayList;
        arrayList.add(comparator);
        BitSet bitSet = new BitSet(1);
        this.orderingBits = bitSet;
        if (z2) {
            bitSet.set(0);
        }
    }

    @SafeVarargs
    public static <E> ComparatorChain<E> of(Comparator<E>... comparatorArr) {
        return of(Arrays.asList(comparatorArr));
    }

    public static <E> ComparatorChain<E> of(List<Comparator<E>> list) {
        return new ComparatorChain<>(list);
    }

    public static <E> ComparatorChain<E> of(List<Comparator<E>> list, BitSet bitSet) {
        return new ComparatorChain<>(list, bitSet);
    }

    public ComparatorChain(List<Comparator<E>> list) {
        this(list, new BitSet(list.size()));
    }

    public ComparatorChain(List<Comparator<E>> list, BitSet bitSet) {
        this.lock = false;
        this.chain = list;
        this.orderingBits = bitSet;
    }
}

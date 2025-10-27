package cn.hutool.core.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/* loaded from: classes.dex */
public class BoundedPriorityQueue<E> extends PriorityQueue<E> {
    private static final long serialVersionUID = 3794348988671694820L;
    private final int capacity;
    private final Comparator<? super E> comparator;

    public BoundedPriorityQueue(int i2) {
        this(i2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$new$0(Comparator comparator, Object obj, Object obj2) {
        return -(comparator != null ? comparator.compare(obj, obj2) : ((Comparable) obj).compareTo(obj2));
    }

    public boolean addAll(E[] eArr) {
        return addAll(Arrays.asList(eArr));
    }

    @Override // java.util.PriorityQueue, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return toList().iterator();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.PriorityQueue, java.util.Queue
    public boolean offer(E e2) {
        if (size() >= this.capacity) {
            if (comparator().compare(e2, peek()) <= 0) {
                return true;
            }
            poll();
        }
        return super.offer(e2);
    }

    public ArrayList<E> toList() {
        ArrayList<E> arrayList = new ArrayList<>(this);
        arrayList.sort(this.comparator);
        return arrayList;
    }

    public BoundedPriorityQueue(int i2, final Comparator<? super E> comparator) {
        super(i2, new Comparator() { // from class: cn.hutool.core.collection.b
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return BoundedPriorityQueue.lambda$new$0(comparator, obj, obj2);
            }
        });
        this.capacity = i2;
        this.comparator = comparator;
    }
}

package com.nostra13.universalimageloader.core.assist.deque;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import okhttp3.HttpUrl;

/* loaded from: classes4.dex */
public class LinkedBlockingDeque<E> extends AbstractQueue<E> implements BlockingDeque<E>, Serializable {
    private static final long serialVersionUID = -387911632671998426L;
    private final int capacity;
    private transient int count;
    transient Node<E> first;
    transient Node<E> last;
    final ReentrantLock lock;
    private final Condition notEmpty;
    private final Condition notFull;

    public abstract class AbstractItr implements Iterator<E> {
        private Node<E> lastRet;
        Node<E> next;
        E nextItem;

        public AbstractItr() {
            ReentrantLock reentrantLock = LinkedBlockingDeque.this.lock;
            reentrantLock.lock();
            try {
                Node<E> nodeFirstNode = firstNode();
                this.next = nodeFirstNode;
                this.nextItem = nodeFirstNode == null ? null : nodeFirstNode.item;
            } finally {
                reentrantLock.unlock();
            }
        }

        private Node<E> succ(Node<E> node) {
            while (true) {
                Node<E> nodeNextNode = nextNode(node);
                if (nodeNextNode == null) {
                    return null;
                }
                if (nodeNextNode.item != null) {
                    return nodeNextNode;
                }
                if (nodeNextNode == node) {
                    return firstNode();
                }
                node = nodeNextNode;
            }
        }

        public void advance() {
            ReentrantLock reentrantLock = LinkedBlockingDeque.this.lock;
            reentrantLock.lock();
            try {
                Node<E> nodeSucc = succ(this.next);
                this.next = nodeSucc;
                this.nextItem = nodeSucc == null ? null : nodeSucc.item;
            } finally {
                reentrantLock.unlock();
            }
        }

        public abstract Node<E> firstNode();

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.next != null;
        }

        @Override // java.util.Iterator
        public E next() {
            Node<E> node = this.next;
            if (node == null) {
                throw new NoSuchElementException();
            }
            this.lastRet = node;
            E e2 = this.nextItem;
            advance();
            return e2;
        }

        public abstract Node<E> nextNode(Node<E> node);

        @Override // java.util.Iterator
        public void remove() {
            Node<E> node = this.lastRet;
            if (node == null) {
                throw new IllegalStateException();
            }
            this.lastRet = null;
            ReentrantLock reentrantLock = LinkedBlockingDeque.this.lock;
            reentrantLock.lock();
            try {
                if (node.item != null) {
                    LinkedBlockingDeque.this.unlink(node);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    public class DescendingItr extends LinkedBlockingDeque<E>.AbstractItr {
        private DescendingItr() {
            super();
        }

        @Override // com.nostra13.universalimageloader.core.assist.deque.LinkedBlockingDeque.AbstractItr
        public Node<E> firstNode() {
            return LinkedBlockingDeque.this.last;
        }

        @Override // com.nostra13.universalimageloader.core.assist.deque.LinkedBlockingDeque.AbstractItr
        public Node<E> nextNode(Node<E> node) {
            return node.prev;
        }
    }

    public class Itr extends LinkedBlockingDeque<E>.AbstractItr {
        private Itr() {
            super();
        }

        @Override // com.nostra13.universalimageloader.core.assist.deque.LinkedBlockingDeque.AbstractItr
        public Node<E> firstNode() {
            return LinkedBlockingDeque.this.first;
        }

        @Override // com.nostra13.universalimageloader.core.assist.deque.LinkedBlockingDeque.AbstractItr
        public Node<E> nextNode(Node<E> node) {
            return node.next;
        }
    }

    public static final class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        public Node(E e2) {
            this.item = e2;
        }
    }

    public LinkedBlockingDeque() {
        this(Integer.MAX_VALUE);
    }

    private boolean linkFirst(Node<E> node) {
        int i2 = this.count;
        if (i2 >= this.capacity) {
            return false;
        }
        Node<E> node2 = this.first;
        node.next = node2;
        this.first = node;
        if (this.last == null) {
            this.last = node;
        } else {
            node2.prev = node;
        }
        this.count = i2 + 1;
        this.notEmpty.signal();
        return true;
    }

    private boolean linkLast(Node<E> node) {
        int i2 = this.count;
        if (i2 >= this.capacity) {
            return false;
        }
        Node<E> node2 = this.last;
        node.prev = node2;
        this.last = node;
        if (this.first == null) {
            this.first = node;
        } else {
            node2.next = node;
        }
        this.count = i2 + 1;
        this.notEmpty.signal();
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.count = 0;
        this.first = null;
        this.last = null;
        while (true) {
            Object object = objectInputStream.readObject();
            if (object == null) {
                return;
            } else {
                add(object);
            }
        }
    }

    private E unlinkFirst() {
        Node<E> node = this.first;
        if (node == null) {
            return null;
        }
        Node<E> node2 = node.next;
        E e2 = node.item;
        node.item = null;
        node.next = node;
        this.first = node2;
        if (node2 == null) {
            this.last = null;
        } else {
            node2.prev = null;
        }
        this.count--;
        this.notFull.signal();
        return e2;
    }

    private E unlinkLast() {
        Node<E> node = this.last;
        if (node == null) {
            return null;
        }
        Node<E> node2 = node.prev;
        E e2 = node.item;
        node.item = null;
        node.prev = node;
        this.last = node2;
        if (node2 == null) {
            this.first = null;
        } else {
            node2.next = null;
        }
        this.count--;
        this.notFull.signal();
        return e2;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            objectOutputStream.defaultWriteObject();
            for (Node<E> node = this.first; node != null; node = node.next) {
                objectOutputStream.writeObject(node.item);
            }
            objectOutputStream.writeObject(null);
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection, java.util.Queue, com.nostra13.universalimageloader.core.assist.deque.BlockingDeque, java.util.concurrent.BlockingQueue, com.nostra13.universalimageloader.core.assist.deque.Deque
    public boolean add(E e2) {
        addLast(e2);
        return true;
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.BlockingDeque, com.nostra13.universalimageloader.core.assist.deque.Deque
    public void addFirst(E e2) {
        if (!offerFirst(e2)) {
            throw new IllegalStateException("Deque full");
        }
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.BlockingDeque, com.nostra13.universalimageloader.core.assist.deque.Deque
    public void addLast(E e2) {
        if (!offerLast(e2)) {
            throw new IllegalStateException("Deque full");
        }
    }

    @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection
    public void clear() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Node<E> node = this.first;
            while (node != null) {
                node.item = null;
                Node<E> node2 = node.next;
                node.prev = null;
                node.next = null;
                node = node2;
            }
            this.last = null;
            this.first = null;
            this.count = 0;
            this.notFull.signalAll();
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.nostra13.universalimageloader.core.assist.deque.BlockingDeque, java.util.concurrent.BlockingQueue, com.nostra13.universalimageloader.core.assist.deque.Deque
    public boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            for (Node<E> node = this.first; node != null; node = node.next) {
                if (obj.equals(node.item)) {
                    reentrantLock.unlock();
                    return true;
                }
            }
            return false;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.Deque
    public Iterator<E> descendingIterator() {
        return new DescendingItr();
    }

    @Override // java.util.concurrent.BlockingQueue
    public int drainTo(Collection<? super E> collection) {
        return drainTo(collection, Integer.MAX_VALUE);
    }

    @Override // java.util.AbstractQueue, java.util.Queue, com.nostra13.universalimageloader.core.assist.deque.BlockingDeque, com.nostra13.universalimageloader.core.assist.deque.Deque
    public E element() {
        return getFirst();
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.Deque
    public E getFirst() {
        E ePeekFirst = peekFirst();
        if (ePeekFirst != null) {
            return ePeekFirst;
        }
        throw new NoSuchElementException();
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.Deque
    public E getLast() {
        E ePeekLast = peekLast();
        if (ePeekLast != null) {
            return ePeekLast;
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.nostra13.universalimageloader.core.assist.deque.BlockingDeque, com.nostra13.universalimageloader.core.assist.deque.Deque
    public Iterator<E> iterator() {
        return new Itr();
    }

    public boolean offer(E e2) {
        return offerLast(e2);
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.BlockingDeque, com.nostra13.universalimageloader.core.assist.deque.Deque
    public boolean offerFirst(E e2) {
        e2.getClass();
        Node<E> node = new Node<>(e2);
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return linkFirst(node);
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.BlockingDeque, com.nostra13.universalimageloader.core.assist.deque.Deque
    public boolean offerLast(E e2) {
        e2.getClass();
        Node<E> node = new Node<>(e2);
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return linkLast(node);
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // java.util.Queue, com.nostra13.universalimageloader.core.assist.deque.BlockingDeque, com.nostra13.universalimageloader.core.assist.deque.Deque
    public E peek() {
        return peekFirst();
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.Deque
    public E peekFirst() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Node<E> node = this.first;
            return node == null ? null : node.item;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.Deque
    public E peekLast() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Node<E> node = this.last;
            return node == null ? null : node.item;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // java.util.Queue, com.nostra13.universalimageloader.core.assist.deque.BlockingDeque, com.nostra13.universalimageloader.core.assist.deque.Deque
    public E poll() {
        return pollFirst();
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.Deque
    public E pollFirst() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return unlinkFirst();
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.Deque
    public E pollLast() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return unlinkLast();
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.Deque
    public E pop() {
        return removeFirst();
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.BlockingDeque, com.nostra13.universalimageloader.core.assist.deque.Deque
    public void push(E e2) {
        addFirst(e2);
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.BlockingDeque, java.util.concurrent.BlockingQueue
    public void put(E e2) throws InterruptedException {
        putLast(e2);
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.BlockingDeque
    public void putFirst(E e2) throws InterruptedException {
        e2.getClass();
        Node<E> node = new Node<>(e2);
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        while (!linkFirst(node)) {
            try {
                this.notFull.await();
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.BlockingDeque
    public void putLast(E e2) throws InterruptedException {
        e2.getClass();
        Node<E> node = new Node<>(e2);
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        while (!linkLast(node)) {
            try {
                this.notFull.await();
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    @Override // java.util.concurrent.BlockingQueue
    public int remainingCapacity() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return this.capacity - this.count;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // java.util.AbstractQueue, java.util.Queue, com.nostra13.universalimageloader.core.assist.deque.BlockingDeque, com.nostra13.universalimageloader.core.assist.deque.Deque
    public E remove() {
        return removeFirst();
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.Deque
    public E removeFirst() {
        E ePollFirst = pollFirst();
        if (ePollFirst != null) {
            return ePollFirst;
        }
        throw new NoSuchElementException();
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.BlockingDeque, com.nostra13.universalimageloader.core.assist.deque.Deque
    public boolean removeFirstOccurrence(Object obj) {
        if (obj == null) {
            return false;
        }
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            for (Node<E> node = this.first; node != null; node = node.next) {
                if (obj.equals(node.item)) {
                    unlink(node);
                    reentrantLock.unlock();
                    return true;
                }
            }
            return false;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.Deque
    public E removeLast() {
        E ePollLast = pollLast();
        if (ePollLast != null) {
            return ePollLast;
        }
        throw new NoSuchElementException();
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.BlockingDeque, com.nostra13.universalimageloader.core.assist.deque.Deque
    public boolean removeLastOccurrence(Object obj) {
        if (obj == null) {
            return false;
        }
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            for (Node<E> node = this.last; node != null; node = node.prev) {
                if (obj.equals(node.item)) {
                    unlink(node);
                    reentrantLock.unlock();
                    return true;
                }
            }
            return false;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.nostra13.universalimageloader.core.assist.deque.BlockingDeque, com.nostra13.universalimageloader.core.assist.deque.Deque
    public int size() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return this.count;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.BlockingDeque, java.util.concurrent.BlockingQueue
    public E take() throws InterruptedException {
        return takeFirst();
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.BlockingDeque
    public E takeFirst() throws InterruptedException {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        while (true) {
            try {
                E eUnlinkFirst = unlinkFirst();
                if (eUnlinkFirst != null) {
                    return eUnlinkFirst;
                }
                this.notEmpty.await();
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.BlockingDeque
    public E takeLast() throws InterruptedException {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        while (true) {
            try {
                E eUnlinkLast = unlinkLast();
                if (eUnlinkLast != null) {
                    return eUnlinkLast;
                }
                this.notEmpty.await();
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public Object[] toArray() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Object[] objArr = new Object[this.count];
            Node<E> node = this.first;
            int i2 = 0;
            while (node != null) {
                int i3 = i2 + 1;
                objArr[i2] = node.item;
                node = node.next;
                i2 = i3;
            }
            return objArr;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Node<E> node = this.first;
            if (node == null) {
                return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
            }
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            while (true) {
                Object obj = node.item;
                if (obj == this) {
                    obj = "(this Collection)";
                }
                sb.append(obj);
                node = node.next;
                if (node == null) {
                    sb.append(']');
                    return sb.toString();
                }
                sb.append(',');
                sb.append(' ');
            }
        } finally {
            reentrantLock.unlock();
        }
    }

    public void unlink(Node<E> node) {
        Node<E> node2 = node.prev;
        Node<E> node3 = node.next;
        if (node2 == null) {
            unlinkFirst();
            return;
        }
        if (node3 == null) {
            unlinkLast();
            return;
        }
        node2.next = node3;
        node3.prev = node2;
        node.item = null;
        this.count--;
        this.notFull.signal();
    }

    public LinkedBlockingDeque(int i2) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.lock = reentrantLock;
        this.notEmpty = reentrantLock.newCondition();
        this.notFull = reentrantLock.newCondition();
        if (i2 <= 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = i2;
    }

    @Override // java.util.concurrent.BlockingQueue
    public int drainTo(Collection<? super E> collection, int i2) {
        collection.getClass();
        if (collection == this) {
            throw new IllegalArgumentException();
        }
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            int iMin = Math.min(i2, this.count);
            for (int i3 = 0; i3 < iMin; i3++) {
                collection.add(this.first.item);
                unlinkFirst();
            }
            return iMin;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.BlockingDeque, java.util.concurrent.BlockingQueue
    public boolean offer(E e2, long j2, TimeUnit timeUnit) throws InterruptedException {
        return offerLast(e2, j2, timeUnit);
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.BlockingDeque, java.util.concurrent.BlockingQueue
    public E poll(long j2, TimeUnit timeUnit) throws InterruptedException {
        return pollFirst(j2, timeUnit);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.nostra13.universalimageloader.core.assist.deque.BlockingDeque, java.util.concurrent.BlockingQueue, com.nostra13.universalimageloader.core.assist.deque.Deque
    public boolean remove(Object obj) {
        return removeFirstOccurrence(obj);
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.BlockingDeque
    public E pollFirst(long j2, TimeUnit timeUnit) throws InterruptedException {
        long nanos = timeUnit.toNanos(j2);
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lockInterruptibly();
        while (true) {
            try {
                E eUnlinkFirst = unlinkFirst();
                if (eUnlinkFirst != null) {
                    return eUnlinkFirst;
                }
                if (nanos <= 0) {
                    reentrantLock.unlock();
                    return null;
                }
                nanos = this.notEmpty.awaitNanos(nanos);
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.BlockingDeque
    public E pollLast(long j2, TimeUnit timeUnit) throws InterruptedException {
        long nanos = timeUnit.toNanos(j2);
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lockInterruptibly();
        while (true) {
            try {
                E eUnlinkLast = unlinkLast();
                if (eUnlinkLast != null) {
                    return eUnlinkLast;
                }
                if (nanos <= 0) {
                    reentrantLock.unlock();
                    return null;
                }
                nanos = this.notEmpty.awaitNanos(nanos);
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.BlockingDeque
    public boolean offerFirst(E e2, long j2, TimeUnit timeUnit) throws InterruptedException {
        e2.getClass();
        Node<E> node = new Node<>(e2);
        long nanos = timeUnit.toNanos(j2);
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lockInterruptibly();
        while (!linkFirst(node)) {
            try {
                if (nanos <= 0) {
                    reentrantLock.unlock();
                    return false;
                }
                nanos = this.notFull.awaitNanos(nanos);
            } catch (Throwable th) {
                reentrantLock.unlock();
                throw th;
            }
        }
        reentrantLock.unlock();
        return true;
    }

    @Override // com.nostra13.universalimageloader.core.assist.deque.BlockingDeque
    public boolean offerLast(E e2, long j2, TimeUnit timeUnit) throws InterruptedException {
        e2.getClass();
        Node<E> node = new Node<>(e2);
        long nanos = timeUnit.toNanos(j2);
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lockInterruptibly();
        while (!linkLast(node)) {
            try {
                if (nanos <= 0) {
                    reentrantLock.unlock();
                    return false;
                }
                nanos = this.notFull.awaitNanos(nanos);
            } catch (Throwable th) {
                reentrantLock.unlock();
                throw th;
            }
        }
        reentrantLock.unlock();
        return true;
    }

    public LinkedBlockingDeque(Collection<? extends E> collection) {
        this(Integer.MAX_VALUE);
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            for (E e2 : collection) {
                if (e2 != null) {
                    if (!linkLast(new Node<>(e2))) {
                        throw new IllegalStateException("Deque full");
                    }
                } else {
                    throw new NullPointerException();
                }
            }
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            if (tArr.length < this.count) {
                tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), this.count));
            }
            Node<E> node = this.first;
            int i2 = 0;
            while (node != null) {
                tArr[i2] = node.item;
                node = node.next;
                i2++;
            }
            if (tArr.length > i2) {
                tArr[i2] = null;
            }
            return tArr;
        } finally {
            reentrantLock.unlock();
        }
    }
}

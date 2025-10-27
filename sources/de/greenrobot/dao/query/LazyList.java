package de.greenrobot.dao.query;

import android.database.Cursor;
import de.greenrobot.dao.DaoException;
import de.greenrobot.dao.InternalQueryDaoAccess;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes8.dex */
public class LazyList<E> implements List<E>, Closeable {
    private final Cursor cursor;
    private final InternalQueryDaoAccess<E> daoAccess;
    private final List<E> entities;
    private volatile int loadedCount;
    private final ReentrantLock lock;
    private final int size;

    public class LazyIterator implements CloseableListIterator<E> {
        private final boolean closeWhenDone;
        private int index;

        public LazyIterator(int i2, boolean z2) {
            this.index = i2;
            this.closeWhenDone = z2;
        }

        @Override // java.util.ListIterator
        public void add(E e2) {
            throw new UnsupportedOperationException();
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            LazyList.this.close();
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public boolean hasNext() {
            return this.index < LazyList.this.size;
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            return this.index > 0;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public E next() {
            if (this.index >= LazyList.this.size) {
                throw new NoSuchElementException();
            }
            E e2 = (E) LazyList.this.get(this.index);
            int i2 = this.index + 1;
            this.index = i2;
            if (i2 == LazyList.this.size && this.closeWhenDone) {
                close();
            }
            return e2;
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.index;
        }

        @Override // java.util.ListIterator
        public E previous() {
            int i2 = this.index;
            if (i2 <= 0) {
                throw new NoSuchElementException();
            }
            int i3 = i2 - 1;
            this.index = i3;
            return (E) LazyList.this.get(i3);
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.index - 1;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.ListIterator
        public void set(E e2) {
            throw new UnsupportedOperationException();
        }
    }

    public LazyList(InternalQueryDaoAccess<E> internalQueryDaoAccess, Cursor cursor, boolean z2) {
        this.cursor = cursor;
        this.daoAccess = internalQueryDaoAccess;
        int count = cursor.getCount();
        this.size = count;
        if (z2) {
            this.entities = new ArrayList(count);
            for (int i2 = 0; i2 < this.size; i2++) {
                this.entities.add(null);
            }
        } else {
            this.entities = null;
        }
        if (this.size == 0) {
            cursor.close();
        }
        this.lock = new ReentrantLock();
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(E e2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    public void checkCached() {
        if (this.entities == null) {
            throw new DaoException("This operation only works with cached lazy lists");
        }
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.cursor.close();
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object obj) {
        loadRemaining();
        return this.entities.contains(obj);
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        loadRemaining();
        return this.entities.containsAll(collection);
    }

    @Override // java.util.List
    public E get(int i2) {
        List<E> list = this.entities;
        if (list == null) {
            return loadEntity(i2);
        }
        E eLoadEntity = list.get(i2);
        if (eLoadEntity == null) {
            this.lock.lock();
            try {
                eLoadEntity = this.entities.get(i2);
                if (eLoadEntity == null) {
                    eLoadEntity = loadEntity(i2);
                    this.entities.set(i2, eLoadEntity);
                    this.loadedCount++;
                    if (this.loadedCount == this.size) {
                        this.cursor.close();
                    }
                }
            } finally {
                this.lock.unlock();
            }
        }
        return eLoadEntity;
    }

    public int getLoadedCount() {
        return this.loadedCount;
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        loadRemaining();
        return this.entities.indexOf(obj);
    }

    public boolean isClosed() {
        return this.cursor.isClosed();
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean isLoadedCompletely() {
        return this.loadedCount == this.size;
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return new LazyIterator(0, false);
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        loadRemaining();
        return this.entities.lastIndexOf(obj);
    }

    public CloseableListIterator<E> listIteratorAutoClose() {
        return new LazyIterator(0, true);
    }

    public E loadEntity(int i2) {
        this.cursor.moveToPosition(i2);
        E eLoadCurrent = this.daoAccess.loadCurrent(this.cursor, 0, true);
        if (eLoadCurrent != null) {
            return eLoadCurrent;
        }
        throw new DaoException("Loading of entity failed (null) at position " + i2);
    }

    public void loadRemaining() {
        checkCached();
        int size = this.entities.size();
        for (int i2 = 0; i2 < size; i2++) {
            get(i2);
        }
    }

    public E peak(int i2) {
        List<E> list = this.entities;
        if (list != null) {
            return list.get(i2);
        }
        return null;
    }

    @Override // java.util.List
    public E remove(int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public E set(int i2, E e2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        return this.size;
    }

    @Override // java.util.List
    public List<E> subList(int i2, int i3) {
        checkCached();
        for (int i4 = i2; i4 < i3; i4++) {
            this.entities.get(i4);
        }
        return this.entities.subList(i2, i3);
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        loadRemaining();
        return this.entities.toArray();
    }

    @Override // java.util.List
    public void add(int i2, E e2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public boolean addAll(int i2, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public CloseableListIterator<E> listIterator() {
        return new LazyIterator(0, false);
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public ListIterator<E> listIterator(int i2) {
        return new LazyIterator(i2, false);
    }

    @Override // java.util.List, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        loadRemaining();
        return (T[]) this.entities.toArray(tArr);
    }
}

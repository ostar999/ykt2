package de.greenrobot.dao.identityscope;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes8.dex */
public class IdentityScopeObject<K, T> implements IdentityScope<K, T> {
    private final HashMap<K, Reference<T>> map = new HashMap<>();
    private final ReentrantLock lock = new ReentrantLock();

    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public void clear() {
        this.lock.lock();
        try {
            this.map.clear();
        } finally {
            this.lock.unlock();
        }
    }

    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public boolean detach(K k2, T t2) {
        this.lock.lock();
        try {
            if (get(k2) != t2 || t2 == null) {
                this.lock.unlock();
                return false;
            }
            remove((IdentityScopeObject<K, T>) k2);
            this.lock.unlock();
            return true;
        } catch (Throwable th) {
            this.lock.unlock();
            throw th;
        }
    }

    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public T get(K k2) {
        this.lock.lock();
        try {
            Reference<T> reference = this.map.get(k2);
            if (reference != null) {
                return reference.get();
            }
            return null;
        } finally {
            this.lock.unlock();
        }
    }

    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public T getNoLock(K k2) {
        Reference<T> reference = this.map.get(k2);
        if (reference != null) {
            return reference.get();
        }
        return null;
    }

    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public void lock() {
        this.lock.lock();
    }

    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public void put(K k2, T t2) {
        this.lock.lock();
        try {
            this.map.put(k2, new WeakReference(t2));
        } finally {
            this.lock.unlock();
        }
    }

    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public void putNoLock(K k2, T t2) {
        this.map.put(k2, new WeakReference(t2));
    }

    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public void remove(K k2) {
        this.lock.lock();
        try {
            this.map.remove(k2);
        } finally {
            this.lock.unlock();
        }
    }

    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public void reserveRoom(int i2) {
    }

    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public void unlock() {
        this.lock.unlock();
    }

    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public void remove(Iterable<K> iterable) {
        this.lock.lock();
        try {
            Iterator<K> it = iterable.iterator();
            while (it.hasNext()) {
                this.map.remove(it.next());
            }
        } finally {
            this.lock.unlock();
        }
    }
}

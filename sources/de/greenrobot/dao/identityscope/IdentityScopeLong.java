package de.greenrobot.dao.identityscope;

import de.greenrobot.dao.internal.LongHashMap;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes8.dex */
public class IdentityScopeLong<T> implements IdentityScope<Long, T> {
    private final LongHashMap<Reference<T>> map = new LongHashMap<>();
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

    /* JADX WARN: Multi-variable type inference failed */
    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public /* bridge */ /* synthetic */ boolean detach(Long l2, Object obj) {
        return detach2(l2, (Long) obj);
    }

    public T get2(long j2) {
        this.lock.lock();
        try {
            Reference<T> reference = this.map.get(j2);
            if (reference != null) {
                return reference.get();
            }
            return null;
        } finally {
            this.lock.unlock();
        }
    }

    public T get2NoLock(long j2) {
        Reference<T> reference = this.map.get(j2);
        if (reference != null) {
            return reference.get();
        }
        return null;
    }

    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public void lock() {
        this.lock.lock();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public /* bridge */ /* synthetic */ void put(Long l2, Object obj) {
        put3(l2, (Long) obj);
    }

    public void put2(long j2, T t2) {
        this.lock.lock();
        try {
            this.map.put(j2, new WeakReference(t2));
        } finally {
            this.lock.unlock();
        }
    }

    public void put2NoLock(long j2, T t2) {
        this.map.put(j2, new WeakReference(t2));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public /* bridge */ /* synthetic */ void putNoLock(Long l2, Object obj) {
        putNoLock2(l2, (Long) obj);
    }

    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public void reserveRoom(int i2) {
        this.map.reserveRoom(i2);
    }

    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public void unlock() {
        this.lock.unlock();
    }

    /* renamed from: detach, reason: avoid collision after fix types in other method */
    public boolean detach2(Long l2, T t2) {
        this.lock.lock();
        try {
            if (get(l2) != t2 || t2 == null) {
                this.lock.unlock();
                return false;
            }
            remove(l2);
            this.lock.unlock();
            return true;
        } catch (Throwable th) {
            this.lock.unlock();
            throw th;
        }
    }

    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public T get(Long l2) {
        return get2(l2.longValue());
    }

    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public T getNoLock(Long l2) {
        return get2NoLock(l2.longValue());
    }

    /* renamed from: put, reason: avoid collision after fix types in other method */
    public void put3(Long l2, T t2) {
        put2(l2.longValue(), t2);
    }

    /* renamed from: putNoLock, reason: avoid collision after fix types in other method */
    public void putNoLock2(Long l2, T t2) {
        put2NoLock(l2.longValue(), t2);
    }

    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public void remove(Long l2) {
        this.lock.lock();
        try {
            this.map.remove(l2.longValue());
        } finally {
            this.lock.unlock();
        }
    }

    @Override // de.greenrobot.dao.identityscope.IdentityScope
    public void remove(Iterable<Long> iterable) {
        this.lock.lock();
        try {
            Iterator<Long> it = iterable.iterator();
            while (it.hasNext()) {
                this.map.remove(it.next().longValue());
            }
        } finally {
            this.lock.unlock();
        }
    }
}

package de.greenrobot.dao.identityscope;

/* loaded from: classes8.dex */
public interface IdentityScope<K, T> {
    void clear();

    boolean detach(K k2, T t2);

    T get(K k2);

    T getNoLock(K k2);

    void lock();

    void put(K k2, T t2);

    void putNoLock(K k2, T t2);

    void remove(Iterable<K> iterable);

    void remove(K k2);

    void reserveRoom(int i2);

    void unlock();
}

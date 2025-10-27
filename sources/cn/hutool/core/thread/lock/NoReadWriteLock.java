package cn.hutool.core.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/* loaded from: classes.dex */
public class NoReadWriteLock implements ReadWriteLock {
    @Override // java.util.concurrent.locks.ReadWriteLock
    public Lock readLock() {
        return NoLock.INSTANCE;
    }

    @Override // java.util.concurrent.locks.ReadWriteLock
    public Lock writeLock() {
        return NoLock.INSTANCE;
    }
}

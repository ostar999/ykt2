package cn.hutool.core.thread.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

/* loaded from: classes.dex */
public class LockUtil {
    private static final NoLock NO_LOCK = new NoLock();

    public static ReentrantReadWriteLock createReadWriteLock(boolean z2) {
        return new ReentrantReadWriteLock(z2);
    }

    public static StampedLock createStampLock() {
        return new StampedLock();
    }

    public static NoLock getNoLock() {
        return NO_LOCK;
    }
}

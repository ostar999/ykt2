package kotlin.reflect.jvm.internal.impl.storage;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public class DefaultSimpleLock implements SimpleLock {

    @NotNull
    private final Lock lock;

    public DefaultSimpleLock() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public DefaultSimpleLock(@NotNull Lock lock) {
        Intrinsics.checkNotNullParameter(lock, "lock");
        this.lock = lock;
    }

    @NotNull
    public final Lock getLock() {
        return this.lock;
    }

    @Override // kotlin.reflect.jvm.internal.impl.storage.SimpleLock
    public void lock() {
        this.lock.lock();
    }

    @Override // kotlin.reflect.jvm.internal.impl.storage.SimpleLock
    public void unlock() {
        this.lock.unlock();
    }

    public /* synthetic */ DefaultSimpleLock(Lock lock, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? new ReentrantLock() : lock);
    }
}

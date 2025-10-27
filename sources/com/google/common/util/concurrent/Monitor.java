package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.google.j2objc.annotations.Weak;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
@GwtIncompatible
/* loaded from: classes4.dex */
public final class Monitor {

    @GuardedBy("lock")
    private Guard activeGuards;
    private final boolean fair;
    private final ReentrantLock lock;

    @Beta
    public static abstract class Guard {
        final Condition condition;

        @Weak
        final Monitor monitor;

        @NullableDecl
        @GuardedBy("monitor.lock")
        Guard next;

        @GuardedBy("monitor.lock")
        int waiterCount = 0;

        public Guard(Monitor monitor) {
            this.monitor = (Monitor) Preconditions.checkNotNull(monitor, "monitor");
            this.condition = monitor.lock.newCondition();
        }

        public abstract boolean isSatisfied();
    }

    public Monitor() {
        this(false);
    }

    @GuardedBy("lock")
    private void await(Guard guard, boolean z2) throws InterruptedException {
        if (z2) {
            signalNextWaiter();
        }
        beginWaitingFor(guard);
        do {
            try {
                guard.condition.await();
            } finally {
                endWaitingFor(guard);
            }
        } while (!guard.isSatisfied());
    }

    @GuardedBy("lock")
    private boolean awaitNanos(Guard guard, long j2, boolean z2) throws InterruptedException {
        boolean z3 = true;
        while (j2 > 0) {
            if (z3) {
                if (z2) {
                    try {
                        signalNextWaiter();
                    } finally {
                        if (!z3) {
                            endWaitingFor(guard);
                        }
                    }
                }
                beginWaitingFor(guard);
                z3 = false;
            }
            j2 = guard.condition.awaitNanos(j2);
            if (guard.isSatisfied()) {
                if (!z3) {
                    endWaitingFor(guard);
                }
                return true;
            }
        }
        return false;
    }

    @GuardedBy("lock")
    private void awaitUninterruptibly(Guard guard, boolean z2) {
        if (z2) {
            signalNextWaiter();
        }
        beginWaitingFor(guard);
        do {
            try {
                guard.condition.awaitUninterruptibly();
            } finally {
                endWaitingFor(guard);
            }
        } while (!guard.isSatisfied());
    }

    @GuardedBy("lock")
    private void beginWaitingFor(Guard guard) {
        int i2 = guard.waiterCount;
        guard.waiterCount = i2 + 1;
        if (i2 == 0) {
            guard.next = this.activeGuards;
            this.activeGuards = guard;
        }
    }

    @GuardedBy("lock")
    private void endWaitingFor(Guard guard) {
        int i2 = guard.waiterCount - 1;
        guard.waiterCount = i2;
        if (i2 == 0) {
            Guard guard2 = this.activeGuards;
            Guard guard3 = null;
            while (guard2 != guard) {
                guard3 = guard2;
                guard2 = guard2.next;
            }
            if (guard3 == null) {
                this.activeGuards = guard2.next;
            } else {
                guard3.next = guard2.next;
            }
            guard2.next = null;
        }
    }

    private static long initNanoTime(long j2) {
        if (j2 <= 0) {
            return 0L;
        }
        long jNanoTime = System.nanoTime();
        if (jNanoTime == 0) {
            return 1L;
        }
        return jNanoTime;
    }

    @GuardedBy("lock")
    private boolean isSatisfied(Guard guard) {
        try {
            return guard.isSatisfied();
        } catch (Throwable th) {
            signalAllWaiters();
            throw th;
        }
    }

    private static long remainingNanos(long j2, long j3) {
        if (j3 <= 0) {
            return 0L;
        }
        return j3 - (System.nanoTime() - j2);
    }

    @GuardedBy("lock")
    private void signalAllWaiters() {
        for (Guard guard = this.activeGuards; guard != null; guard = guard.next) {
            guard.condition.signalAll();
        }
    }

    @GuardedBy("lock")
    private void signalNextWaiter() {
        for (Guard guard = this.activeGuards; guard != null; guard = guard.next) {
            if (isSatisfied(guard)) {
                guard.condition.signal();
                return;
            }
        }
    }

    private static long toSafeNanos(long j2, TimeUnit timeUnit) {
        long nanos = timeUnit.toNanos(j2);
        long j3 = 0;
        if (nanos > 0) {
            j3 = 6917529027641081853L;
            if (nanos <= 6917529027641081853L) {
                return nanos;
            }
        }
        return j3;
    }

    public void enter() {
        this.lock.lock();
    }

    public boolean enterIf(Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            boolean zIsSatisfied = guard.isSatisfied();
            if (!zIsSatisfied) {
            }
            return zIsSatisfied;
        } finally {
            reentrantLock.unlock();
        }
    }

    public boolean enterIfInterruptibly(Guard guard) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lockInterruptibly();
        try {
            boolean zIsSatisfied = guard.isSatisfied();
            if (!zIsSatisfied) {
            }
            return zIsSatisfied;
        } finally {
            reentrantLock.unlock();
        }
    }

    public void enterInterruptibly() throws InterruptedException {
        this.lock.lockInterruptibly();
    }

    public void enterWhen(Guard guard) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.lock;
        boolean zIsHeldByCurrentThread = reentrantLock.isHeldByCurrentThread();
        reentrantLock.lockInterruptibly();
        try {
            if (guard.isSatisfied()) {
                return;
            }
            await(guard, zIsHeldByCurrentThread);
        } catch (Throwable th) {
            leave();
            throw th;
        }
    }

    public void enterWhenUninterruptibly(Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.lock;
        boolean zIsHeldByCurrentThread = reentrantLock.isHeldByCurrentThread();
        reentrantLock.lock();
        try {
            if (guard.isSatisfied()) {
                return;
            }
            awaitUninterruptibly(guard, zIsHeldByCurrentThread);
        } catch (Throwable th) {
            leave();
            throw th;
        }
    }

    public int getOccupiedDepth() {
        return this.lock.getHoldCount();
    }

    public int getQueueLength() {
        return this.lock.getQueueLength();
    }

    public int getWaitQueueLength(Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        this.lock.lock();
        try {
            return guard.waiterCount;
        } finally {
            this.lock.unlock();
        }
    }

    public boolean hasQueuedThread(Thread thread) {
        return this.lock.hasQueuedThread(thread);
    }

    public boolean hasQueuedThreads() {
        return this.lock.hasQueuedThreads();
    }

    public boolean hasWaiters(Guard guard) {
        return getWaitQueueLength(guard) > 0;
    }

    public boolean isFair() {
        return this.fair;
    }

    public boolean isOccupied() {
        return this.lock.isLocked();
    }

    public boolean isOccupiedByCurrentThread() {
        return this.lock.isHeldByCurrentThread();
    }

    public void leave() {
        ReentrantLock reentrantLock = this.lock;
        try {
            if (reentrantLock.getHoldCount() == 1) {
                signalNextWaiter();
            }
        } finally {
            reentrantLock.unlock();
        }
    }

    public boolean tryEnter() {
        return this.lock.tryLock();
    }

    public boolean tryEnterIf(Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.lock;
        if (!reentrantLock.tryLock()) {
            return false;
        }
        try {
            boolean zIsSatisfied = guard.isSatisfied();
            if (!zIsSatisfied) {
            }
            return zIsSatisfied;
        } finally {
            reentrantLock.unlock();
        }
    }

    public void waitFor(Guard guard) throws InterruptedException {
        if (!(guard.monitor == this) || !this.lock.isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        }
        if (guard.isSatisfied()) {
            return;
        }
        await(guard, true);
    }

    public void waitForUninterruptibly(Guard guard) {
        if (!(guard.monitor == this) || !this.lock.isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        }
        if (guard.isSatisfied()) {
            return;
        }
        awaitUninterruptibly(guard, true);
    }

    public Monitor(boolean z2) {
        this.activeGuards = null;
        this.fair = z2;
        this.lock = new ReentrantLock(z2);
    }

    public boolean enter(long j2, TimeUnit timeUnit) throws Throwable {
        boolean zTryLock;
        long safeNanos = toSafeNanos(j2, timeUnit);
        ReentrantLock reentrantLock = this.lock;
        boolean z2 = true;
        if (!this.fair && reentrantLock.tryLock()) {
            return true;
        }
        boolean zInterrupted = Thread.interrupted();
        try {
            long jNanoTime = System.nanoTime();
            long jRemainingNanos = safeNanos;
            while (true) {
                try {
                    try {
                        zTryLock = reentrantLock.tryLock(jRemainingNanos, TimeUnit.NANOSECONDS);
                        break;
                    } catch (InterruptedException unused) {
                        jRemainingNanos = remainingNanos(jNanoTime, safeNanos);
                        zInterrupted = true;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (z2) {
                        Thread.currentThread().interrupt();
                    }
                    throw th;
                }
            }
            if (zInterrupted) {
                Thread.currentThread().interrupt();
            }
            return zTryLock;
        } catch (Throwable th2) {
            th = th2;
            z2 = zInterrupted;
        }
    }

    public boolean enterInterruptibly(long j2, TimeUnit timeUnit) throws InterruptedException {
        return this.lock.tryLock(j2, timeUnit);
    }

    public boolean waitFor(Guard guard, long j2, TimeUnit timeUnit) throws InterruptedException {
        long safeNanos = toSafeNanos(j2, timeUnit);
        if ((guard.monitor == this) & this.lock.isHeldByCurrentThread()) {
            if (guard.isSatisfied()) {
                return true;
            }
            if (!Thread.interrupted()) {
                return awaitNanos(guard, safeNanos, true);
            }
            throw new InterruptedException();
        }
        throw new IllegalMonitorStateException();
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean waitForUninterruptibly(com.google.common.util.concurrent.Monitor.Guard r8, long r9, java.util.concurrent.TimeUnit r11) throws java.lang.Throwable {
        /*
            r7 = this;
            long r9 = toSafeNanos(r9, r11)
            com.google.common.util.concurrent.Monitor r11 = r8.monitor
            r0 = 0
            r1 = 1
            if (r11 != r7) goto Lc
            r11 = r1
            goto Ld
        Lc:
            r11 = r0
        Ld:
            java.util.concurrent.locks.ReentrantLock r2 = r7.lock
            boolean r2 = r2.isHeldByCurrentThread()
            r11 = r11 & r2
            if (r11 == 0) goto L58
            boolean r11 = r8.isSatisfied()
            if (r11 == 0) goto L1d
            return r1
        L1d:
            long r2 = initNanoTime(r9)
            boolean r11 = java.lang.Thread.interrupted()
            r4 = r9
            r6 = r1
        L27:
            boolean r8 = r7.awaitNanos(r8, r4, r6)     // Catch: java.lang.Throwable -> L35 java.lang.InterruptedException -> L38
            if (r11 == 0) goto L34
            java.lang.Thread r9 = java.lang.Thread.currentThread()
            r9.interrupt()
        L34:
            return r8
        L35:
            r8 = move-exception
            r1 = r11
            goto L4e
        L38:
            boolean r11 = r8.isSatisfied()     // Catch: java.lang.Throwable -> L4d
            if (r11 == 0) goto L46
            java.lang.Thread r8 = java.lang.Thread.currentThread()
            r8.interrupt()
            return r1
        L46:
            long r4 = remainingNanos(r2, r9)     // Catch: java.lang.Throwable -> L4d
            r6 = r0
            r11 = r1
            goto L27
        L4d:
            r8 = move-exception
        L4e:
            if (r1 == 0) goto L57
            java.lang.Thread r9 = java.lang.Thread.currentThread()
            r9.interrupt()
        L57:
            throw r8
        L58:
            java.lang.IllegalMonitorStateException r8 = new java.lang.IllegalMonitorStateException
            r8.<init>()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.Monitor.waitForUninterruptibly(com.google.common.util.concurrent.Monitor$Guard, long, java.util.concurrent.TimeUnit):boolean");
    }

    public boolean enterIf(Guard guard, long j2, TimeUnit timeUnit) {
        if (guard.monitor == this) {
            if (!enter(j2, timeUnit)) {
                return false;
            }
            try {
                boolean zIsSatisfied = guard.isSatisfied();
                if (!zIsSatisfied) {
                }
                return zIsSatisfied;
            } finally {
                this.lock.unlock();
            }
        }
        throw new IllegalMonitorStateException();
    }

    public boolean enterIfInterruptibly(Guard guard, long j2, TimeUnit timeUnit) throws InterruptedException {
        if (guard.monitor == this) {
            ReentrantLock reentrantLock = this.lock;
            if (!reentrantLock.tryLock(j2, timeUnit)) {
                return false;
            }
            try {
                boolean zIsSatisfied = guard.isSatisfied();
                if (!zIsSatisfied) {
                }
                return zIsSatisfied;
            } finally {
                reentrantLock.unlock();
            }
        }
        throw new IllegalMonitorStateException();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0029  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0049  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean enterWhen(com.google.common.util.concurrent.Monitor.Guard r11, long r12, java.util.concurrent.TimeUnit r14) throws java.lang.InterruptedException {
        /*
            r10 = this;
            long r0 = toSafeNanos(r12, r14)
            com.google.common.util.concurrent.Monitor r2 = r11.monitor
            if (r2 != r10) goto L60
            java.util.concurrent.locks.ReentrantLock r2 = r10.lock
            boolean r3 = r2.isHeldByCurrentThread()
            boolean r4 = r10.fair
            r5 = 0
            r6 = 0
            if (r4 != 0) goto L29
            boolean r4 = java.lang.Thread.interrupted()
            if (r4 != 0) goto L23
            boolean r4 = r2.tryLock()
            if (r4 == 0) goto L29
            r8 = r6
            goto L34
        L23:
            java.lang.InterruptedException r11 = new java.lang.InterruptedException
            r11.<init>()
            throw r11
        L29:
            long r8 = initNanoTime(r0)
            boolean r12 = r2.tryLock(r12, r14)
            if (r12 != 0) goto L34
            return r5
        L34:
            boolean r12 = r11.isSatisfied()     // Catch: java.lang.Throwable -> L50
            if (r12 != 0) goto L49
            int r12 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r12 != 0) goto L3f
            goto L43
        L3f:
            long r0 = remainingNanos(r8, r0)     // Catch: java.lang.Throwable -> L50
        L43:
            boolean r11 = r10.awaitNanos(r11, r0, r3)     // Catch: java.lang.Throwable -> L50
            if (r11 == 0) goto L4a
        L49:
            r5 = 1
        L4a:
            if (r5 != 0) goto L4f
            r2.unlock()
        L4f:
            return r5
        L50:
            r11 = move-exception
            if (r3 != 0) goto L5c
            r10.signalNextWaiter()     // Catch: java.lang.Throwable -> L57
            goto L5c
        L57:
            r11 = move-exception
            r2.unlock()
            throw r11
        L5c:
            r2.unlock()
            throw r11
        L60:
            java.lang.IllegalMonitorStateException r11 = new java.lang.IllegalMonitorStateException
            r11.<init>()
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.Monitor.enterWhen(com.google.common.util.concurrent.Monitor$Guard, long, java.util.concurrent.TimeUnit):boolean");
    }

    public boolean enterWhenUninterruptibly(Guard guard, long j2, TimeUnit timeUnit) throws Throwable {
        long jInitNanoTime;
        long jRemainingNanos;
        long safeNanos = toSafeNanos(j2, timeUnit);
        if (guard.monitor == this) {
            ReentrantLock reentrantLock = this.lock;
            boolean zIsHeldByCurrentThread = reentrantLock.isHeldByCurrentThread();
            boolean zInterrupted = Thread.interrupted();
            try {
                boolean zAwaitNanos = true;
                if (this.fair || !reentrantLock.tryLock()) {
                    jInitNanoTime = initNanoTime(safeNanos);
                    long jRemainingNanos2 = safeNanos;
                    while (true) {
                        try {
                            try {
                                break;
                            } catch (InterruptedException unused) {
                                jRemainingNanos2 = remainingNanos(jInitNanoTime, safeNanos);
                                zInterrupted = true;
                            }
                        } catch (Throwable th) {
                            th = th;
                            zInterrupted = true;
                            if (zInterrupted) {
                                Thread.currentThread().interrupt();
                            }
                            throw th;
                        }
                    }
                    if (!reentrantLock.tryLock(jRemainingNanos2, TimeUnit.NANOSECONDS)) {
                        if (zInterrupted) {
                            Thread.currentThread().interrupt();
                        }
                        return false;
                    }
                } else {
                    jInitNanoTime = 0;
                }
                while (!guard.isSatisfied()) {
                    try {
                        if (jInitNanoTime == 0) {
                            jInitNanoTime = initNanoTime(safeNanos);
                            jRemainingNanos = safeNanos;
                        } else {
                            jRemainingNanos = remainingNanos(jInitNanoTime, safeNanos);
                        }
                        zAwaitNanos = awaitNanos(guard, jRemainingNanos, zIsHeldByCurrentThread);
                    } catch (InterruptedException unused2) {
                        zIsHeldByCurrentThread = false;
                        zInterrupted = zAwaitNanos;
                    } catch (Throwable th2) {
                        reentrantLock.unlock();
                        throw th2;
                    }
                }
                if (!zAwaitNanos) {
                    reentrantLock.unlock();
                }
                if (zInterrupted) {
                    Thread.currentThread().interrupt();
                }
                return zAwaitNanos;
            } catch (Throwable th3) {
                th = th3;
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }
}

package kotlinx.coroutines;

import com.umeng.analytics.pro.d;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.time.DurationKt;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import kotlinx.coroutines.internal.ThreadSafeHeap;
import kotlinx.coroutines.internal.ThreadSafeHeapNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b \u0018\u00002\u0002092\u00020::\u00044567B\u0007¢\u0006\u0004\b\u0001\u0010\u0002J\u000f\u0010\u0004\u001a\u00020\u0003H\u0002¢\u0006\u0004\b\u0004\u0010\u0002J\u0017\u0010\u0007\u001a\n\u0018\u00010\u0005j\u0004\u0018\u0001`\u0006H\u0002¢\u0006\u0004\b\u0007\u0010\bJ!\u0010\f\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\t2\n\u0010\u000b\u001a\u00060\u0005j\u0002`\u0006¢\u0006\u0004\b\f\u0010\rJ\u001b\u0010\u000f\u001a\u00020\u00032\n\u0010\u000e\u001a\u00060\u0005j\u0002`\u0006H\u0016¢\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\u0012\u001a\u00020\u00112\n\u0010\u000e\u001a\u00060\u0005j\u0002`\u0006H\u0002¢\u0006\u0004\b\u0012\u0010\u0013J\u000f\u0010\u0015\u001a\u00020\u0014H\u0016¢\u0006\u0004\b\u0015\u0010\u0016J\u000f\u0010\u0017\u001a\u00020\u0003H\u0002¢\u0006\u0004\b\u0017\u0010\u0002J\u000f\u0010\u0018\u001a\u00020\u0003H\u0004¢\u0006\u0004\b\u0018\u0010\u0002J\u001d\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001a¢\u0006\u0004\b\u001c\u0010\u001dJ\u001f\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001aH\u0002¢\u0006\u0004\b\u001f\u0010 J#\u0010#\u001a\u00020\"2\u0006\u0010!\u001a\u00020\u00142\n\u0010\u000b\u001a\u00060\u0005j\u0002`\u0006H\u0004¢\u0006\u0004\b#\u0010$J%\u0010'\u001a\u00020\u00032\u0006\u0010!\u001a\u00020\u00142\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00030%H\u0016¢\u0006\u0004\b'\u0010(J\u0017\u0010)\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u001aH\u0002¢\u0006\u0004\b)\u0010*J\u000f\u0010+\u001a\u00020\u0003H\u0016¢\u0006\u0004\b+\u0010\u0002R$\u0010-\u001a\u00020\u00112\u0006\u0010,\u001a\u00020\u00118B@BX\u0082\u000e¢\u0006\f\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u0014\u00101\u001a\u00020\u00118TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b1\u0010.R\u0014\u00103\u001a\u00020\u00148TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b2\u0010\u0016¨\u00068"}, d2 = {"Lkotlinx/coroutines/EventLoopImplBase;", "<init>", "()V", "", "closeQueue", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dequeue", "()Ljava/lang/Runnable;", "Lkotlin/coroutines/CoroutineContext;", d.R, "block", "dispatch", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Runnable;)V", "task", "enqueue", "(Ljava/lang/Runnable;)V", "", "enqueueImpl", "(Ljava/lang/Runnable;)Z", "", "processNextEvent", "()J", "rescheduleAllDelayed", "resetAll", "now", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "delayedTask", "schedule", "(JLkotlinx/coroutines/EventLoopImplBase$DelayedTask;)V", "", "scheduleImpl", "(JLkotlinx/coroutines/EventLoopImplBase$DelayedTask;)I", "timeMillis", "Lkotlinx/coroutines/DisposableHandle;", "scheduleInvokeOnTimeout", "(JLjava/lang/Runnable;)Lkotlinx/coroutines/DisposableHandle;", "Lkotlinx/coroutines/CancellableContinuation;", "continuation", "scheduleResumeAfterDelay", "(JLkotlinx/coroutines/CancellableContinuation;)V", "shouldUnpark", "(Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;)Z", "shutdown", "value", "isCompleted", "()Z", "setCompleted", "(Z)V", "isEmpty", "getNextTime", "nextTime", "DelayedResumeTask", "DelayedRunnableTask", "DelayedTask", "DelayedTaskQueue", "kotlinx-coroutines-core", "Lkotlinx/coroutines/EventLoopImplPlatform;", "Lkotlinx/coroutines/Delay;"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes8.dex */
public abstract class EventLoopImplBase extends EventLoopImplPlatform implements Delay {
    private static final /* synthetic */ AtomicReferenceFieldUpdater _queue$FU = AtomicReferenceFieldUpdater.newUpdater(EventLoopImplBase.class, Object.class, "_queue");
    private static final /* synthetic */ AtomicReferenceFieldUpdater _delayed$FU = AtomicReferenceFieldUpdater.newUpdater(EventLoopImplBase.class, Object.class, "_delayed");

    @NotNull
    private volatile /* synthetic */ Object _queue = null;

    @NotNull
    private volatile /* synthetic */ Object _delayed = null;

    @NotNull
    private volatile /* synthetic */ int _isCompleted = 0;

    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\u0006H\u0016J\b\u0010\t\u001a\u00020\nH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lkotlinx/coroutines/EventLoopImplBase$DelayedResumeTask;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "nanoTime", "", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlinx/coroutines/EventLoopImplBase;JLkotlinx/coroutines/CancellableContinuation;)V", "run", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public final class DelayedResumeTask extends DelayedTask {

        @NotNull
        private final CancellableContinuation<Unit> cont;

        /* JADX WARN: Multi-variable type inference failed */
        public DelayedResumeTask(long j2, @NotNull CancellableContinuation<? super Unit> cancellableContinuation) {
            super(j2);
            this.cont = cancellableContinuation;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.cont.resumeUndispatched(EventLoopImplBase.this, Unit.INSTANCE);
        }

        @Override // kotlinx.coroutines.EventLoopImplBase.DelayedTask
        @NotNull
        public String toString() {
            return super.toString() + this.cont;
        }
    }

    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016R\u0012\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lkotlinx/coroutines/EventLoopImplBase$DelayedRunnableTask;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "nanoTime", "", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "(JLjava/lang/Runnable;)V", "run", "", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class DelayedRunnableTask extends DelayedTask {

        @NotNull
        private final Runnable block;

        public DelayedRunnableTask(long j2, @NotNull Runnable runnable) {
            super(j2);
            this.block = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.block.run();
        }

        @Override // kotlinx.coroutines.EventLoopImplBase.DelayedTask
        @NotNull
        public String toString() {
            return super.toString() + this.block;
        }
    }

    @Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\b \u0018\u00002\u00060\u0001j\u0002`\u00022\b\u0012\u0004\u0012\u00020\u00000\u00032\u00020\u00042\u00020\u0005B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0011\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0000H\u0096\u0002J\u0006\u0010\u001a\u001a\u00020\u001bJ\u001e\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!J\u000e\u0010\"\u001a\u00020#2\u0006\u0010\u001d\u001a\u00020\u0007J\b\u0010$\u001a\u00020%H\u0016R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R0\u0010\r\u001a\b\u0012\u0002\b\u0003\u0018\u00010\f2\f\u0010\u000b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0012\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "", "Lkotlinx/coroutines/DisposableHandle;", "Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "nanoTime", "", "(J)V", "_heap", "", "value", "Lkotlinx/coroutines/internal/ThreadSafeHeap;", "heap", "getHeap", "()Lkotlinx/coroutines/internal/ThreadSafeHeap;", "setHeap", "(Lkotlinx/coroutines/internal/ThreadSafeHeap;)V", "index", "", "getIndex", "()I", "setIndex", "(I)V", "compareTo", "other", "dispose", "", "scheduleTask", "now", "delayed", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;", "eventLoop", "Lkotlinx/coroutines/EventLoopImplBase;", "timeToExecute", "", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static abstract class DelayedTask implements Runnable, Comparable<DelayedTask>, DisposableHandle, ThreadSafeHeapNode {

        @Nullable
        private volatile Object _heap;
        private int index = -1;

        @JvmField
        public long nanoTime;

        public DelayedTask(long j2) {
            this.nanoTime = j2;
        }

        @Override // kotlinx.coroutines.DisposableHandle
        public final synchronized void dispose() {
            Object obj = this._heap;
            if (obj == EventLoop_commonKt.DISPOSED_TASK) {
                return;
            }
            DelayedTaskQueue delayedTaskQueue = obj instanceof DelayedTaskQueue ? (DelayedTaskQueue) obj : null;
            if (delayedTaskQueue != null) {
                delayedTaskQueue.remove(this);
            }
            this._heap = EventLoop_commonKt.DISPOSED_TASK;
        }

        @Override // kotlinx.coroutines.internal.ThreadSafeHeapNode
        @Nullable
        public ThreadSafeHeap<?> getHeap() {
            Object obj = this._heap;
            if (obj instanceof ThreadSafeHeap) {
                return (ThreadSafeHeap) obj;
            }
            return null;
        }

        @Override // kotlinx.coroutines.internal.ThreadSafeHeapNode
        public int getIndex() {
            return this.index;
        }

        public final synchronized int scheduleTask(long now, @NotNull DelayedTaskQueue delayed, @NotNull EventLoopImplBase eventLoop) {
            if (this._heap == EventLoop_commonKt.DISPOSED_TASK) {
                return 2;
            }
            synchronized (delayed) {
                DelayedTask delayedTaskFirstImpl = delayed.firstImpl();
                if (eventLoop.isCompleted()) {
                    return 1;
                }
                if (delayedTaskFirstImpl == null) {
                    delayed.timeNow = now;
                } else {
                    long j2 = delayedTaskFirstImpl.nanoTime;
                    if (j2 - now < 0) {
                        now = j2;
                    }
                    if (now - delayed.timeNow > 0) {
                        delayed.timeNow = now;
                    }
                }
                long j3 = this.nanoTime;
                long j4 = delayed.timeNow;
                if (j3 - j4 < 0) {
                    this.nanoTime = j4;
                }
                delayed.addImpl(this);
                return 0;
            }
        }

        @Override // kotlinx.coroutines.internal.ThreadSafeHeapNode
        public void setHeap(@Nullable ThreadSafeHeap<?> threadSafeHeap) {
            if (!(this._heap != EventLoop_commonKt.DISPOSED_TASK)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            this._heap = threadSafeHeap;
        }

        @Override // kotlinx.coroutines.internal.ThreadSafeHeapNode
        public void setIndex(int i2) {
            this.index = i2;
        }

        public final boolean timeToExecute(long now) {
            return now - this.nanoTime >= 0;
        }

        @NotNull
        public String toString() {
            return "Delayed[nanos=" + this.nanoTime + ']';
        }

        @Override // java.lang.Comparable
        public int compareTo(@NotNull DelayedTask other) {
            long j2 = this.nanoTime - other.nanoTime;
            if (j2 > 0) {
                return 1;
            }
            return j2 < 0 ? -1 : 0;
        }
    }

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0012\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;", "Lkotlinx/coroutines/internal/ThreadSafeHeap;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "timeNow", "", "(J)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class DelayedTaskQueue extends ThreadSafeHeap<DelayedTask> {

        @JvmField
        public long timeNow;

        public DelayedTaskQueue(long j2) {
            this.timeNow = j2;
        }
    }

    private final void closeQueue() {
        while (true) {
            Object obj = this._queue;
            if (obj == null) {
                if (androidx.concurrent.futures.a.a(_queue$FU, this, null, EventLoop_commonKt.CLOSED_EMPTY)) {
                    return;
                }
            } else if (obj instanceof LockFreeTaskQueueCore) {
                ((LockFreeTaskQueueCore) obj).close();
                return;
            } else {
                if (obj == EventLoop_commonKt.CLOSED_EMPTY) {
                    return;
                }
                LockFreeTaskQueueCore lockFreeTaskQueueCore = new LockFreeTaskQueueCore(8, true);
                lockFreeTaskQueueCore.addLast((Runnable) obj);
                if (androidx.concurrent.futures.a.a(_queue$FU, this, obj, lockFreeTaskQueueCore)) {
                    return;
                }
            }
        }
    }

    private final Runnable dequeue() {
        while (true) {
            Object obj = this._queue;
            if (obj == null) {
                return null;
            }
            if (obj instanceof LockFreeTaskQueueCore) {
                LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) obj;
                Object objRemoveFirstOrNull = lockFreeTaskQueueCore.removeFirstOrNull();
                if (objRemoveFirstOrNull != LockFreeTaskQueueCore.REMOVE_FROZEN) {
                    return (Runnable) objRemoveFirstOrNull;
                }
                androidx.concurrent.futures.a.a(_queue$FU, this, obj, lockFreeTaskQueueCore.next());
            } else {
                if (obj == EventLoop_commonKt.CLOSED_EMPTY) {
                    return null;
                }
                if (androidx.concurrent.futures.a.a(_queue$FU, this, obj, null)) {
                    return (Runnable) obj;
                }
            }
        }
    }

    private final boolean enqueueImpl(Runnable task) {
        while (true) {
            Object obj = this._queue;
            if (isCompleted()) {
                return false;
            }
            if (obj == null) {
                if (androidx.concurrent.futures.a.a(_queue$FU, this, null, task)) {
                    return true;
                }
            } else if (obj instanceof LockFreeTaskQueueCore) {
                LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) obj;
                int iAddLast = lockFreeTaskQueueCore.addLast(task);
                if (iAddLast == 0) {
                    return true;
                }
                if (iAddLast == 1) {
                    androidx.concurrent.futures.a.a(_queue$FU, this, obj, lockFreeTaskQueueCore.next());
                } else if (iAddLast == 2) {
                    return false;
                }
            } else {
                if (obj == EventLoop_commonKt.CLOSED_EMPTY) {
                    return false;
                }
                LockFreeTaskQueueCore lockFreeTaskQueueCore2 = new LockFreeTaskQueueCore(8, true);
                lockFreeTaskQueueCore2.addLast((Runnable) obj);
                lockFreeTaskQueueCore2.addLast(task);
                if (androidx.concurrent.futures.a.a(_queue$FU, this, obj, lockFreeTaskQueueCore2)) {
                    return true;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v0, types: [boolean, int] */
    public final boolean isCompleted() {
        return this._isCompleted;
    }

    private final void rescheduleAllDelayed() {
        DelayedTask delayedTaskRemoveFirstOrNull;
        AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
        long jNanoTime = timeSource != null ? timeSource.nanoTime() : System.nanoTime();
        while (true) {
            DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed;
            if (delayedTaskQueue == null || (delayedTaskRemoveFirstOrNull = delayedTaskQueue.removeFirstOrNull()) == null) {
                return;
            } else {
                reschedule(jNanoTime, delayedTaskRemoveFirstOrNull);
            }
        }
    }

    private final int scheduleImpl(long now, DelayedTask delayedTask) {
        if (isCompleted()) {
            return 1;
        }
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed;
        if (delayedTaskQueue == null) {
            androidx.concurrent.futures.a.a(_delayed$FU, this, null, new DelayedTaskQueue(now));
            Object obj = this._delayed;
            Intrinsics.checkNotNull(obj);
            delayedTaskQueue = (DelayedTaskQueue) obj;
        }
        return delayedTask.scheduleTask(now, delayedTaskQueue, this);
    }

    private final void setCompleted(boolean z2) {
        this._isCompleted = z2 ? 1 : 0;
    }

    private final boolean shouldUnpark(DelayedTask task) {
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed;
        return (delayedTaskQueue != null ? delayedTaskQueue.peek() : null) == task;
    }

    @Override // kotlinx.coroutines.Delay
    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated without replacement as an internal method never intended for public use")
    @Nullable
    public Object delay(long j2, @NotNull Continuation<? super Unit> continuation) {
        return Delay.DefaultImpls.delay(this, j2, continuation);
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    /* renamed from: dispatch */
    public final void mo2362dispatch(@NotNull CoroutineContext context, @NotNull Runnable block) {
        enqueue(block);
    }

    public void enqueue(@NotNull Runnable task) {
        if (enqueueImpl(task)) {
            unpark();
        } else {
            DefaultExecutor.INSTANCE.enqueue(task);
        }
    }

    @Override // kotlinx.coroutines.EventLoop
    public long getNextTime() {
        DelayedTask delayedTaskPeek;
        if (super.getNextTime() == 0) {
            return 0L;
        }
        Object obj = this._queue;
        if (obj != null) {
            if (!(obj instanceof LockFreeTaskQueueCore)) {
                return obj == EventLoop_commonKt.CLOSED_EMPTY ? Long.MAX_VALUE : 0L;
            }
            if (!((LockFreeTaskQueueCore) obj).isEmpty()) {
                return 0L;
            }
        }
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed;
        if (delayedTaskQueue == null || (delayedTaskPeek = delayedTaskQueue.peek()) == null) {
            return Long.MAX_VALUE;
        }
        long j2 = delayedTaskPeek.nanoTime;
        AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
        return RangesKt___RangesKt.coerceAtLeast(j2 - (timeSource != null ? timeSource.nanoTime() : System.nanoTime()), 0L);
    }

    @NotNull
    public DisposableHandle invokeOnTimeout(long j2, @NotNull Runnable runnable, @NotNull CoroutineContext coroutineContext) {
        return Delay.DefaultImpls.invokeOnTimeout(this, j2, runnable, coroutineContext);
    }

    @Override // kotlinx.coroutines.EventLoop
    public boolean isEmpty() {
        if (!isUnconfinedQueueEmpty()) {
            return false;
        }
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed;
        if (delayedTaskQueue != null && !delayedTaskQueue.isEmpty()) {
            return false;
        }
        Object obj = this._queue;
        if (obj != null) {
            if (obj instanceof LockFreeTaskQueueCore) {
                return ((LockFreeTaskQueueCore) obj).isEmpty();
            }
            if (obj != EventLoop_commonKt.CLOSED_EMPTY) {
                return false;
            }
        }
        return true;
    }

    @Override // kotlinx.coroutines.EventLoop
    public long processNextEvent() {
        DelayedTask delayedTaskRemoveAtImpl;
        if (processUnconfinedEvent()) {
            return 0L;
        }
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed;
        if (delayedTaskQueue != null && !delayedTaskQueue.isEmpty()) {
            AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
            long jNanoTime = timeSource != null ? timeSource.nanoTime() : System.nanoTime();
            do {
                synchronized (delayedTaskQueue) {
                    DelayedTask delayedTaskFirstImpl = delayedTaskQueue.firstImpl();
                    if (delayedTaskFirstImpl != null) {
                        DelayedTask delayedTask = delayedTaskFirstImpl;
                        delayedTaskRemoveAtImpl = delayedTask.timeToExecute(jNanoTime) ? enqueueImpl(delayedTask) : false ? delayedTaskQueue.removeAtImpl(0) : null;
                    }
                }
            } while (delayedTaskRemoveAtImpl != null);
        }
        Runnable runnableDequeue = dequeue();
        if (runnableDequeue == null) {
            return getNextTime();
        }
        runnableDequeue.run();
        return 0L;
    }

    public final void resetAll() {
        this._queue = null;
        this._delayed = null;
    }

    public final void schedule(long now, @NotNull DelayedTask delayedTask) {
        int iScheduleImpl = scheduleImpl(now, delayedTask);
        if (iScheduleImpl == 0) {
            if (shouldUnpark(delayedTask)) {
                unpark();
            }
        } else if (iScheduleImpl == 1) {
            reschedule(now, delayedTask);
        } else if (iScheduleImpl != 2) {
            throw new IllegalStateException("unexpected result".toString());
        }
    }

    @NotNull
    public final DisposableHandle scheduleInvokeOnTimeout(long timeMillis, @NotNull Runnable block) {
        long jDelayToNanos = EventLoop_commonKt.delayToNanos(timeMillis);
        if (jDelayToNanos >= DurationKt.MAX_MILLIS) {
            return NonDisposableHandle.INSTANCE;
        }
        AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
        long jNanoTime = timeSource != null ? timeSource.nanoTime() : System.nanoTime();
        DelayedRunnableTask delayedRunnableTask = new DelayedRunnableTask(jDelayToNanos + jNanoTime, block);
        schedule(jNanoTime, delayedRunnableTask);
        return delayedRunnableTask;
    }

    @Override // kotlinx.coroutines.Delay
    /* renamed from: scheduleResumeAfterDelay */
    public void mo2363scheduleResumeAfterDelay(long timeMillis, @NotNull CancellableContinuation<? super Unit> continuation) {
        long jDelayToNanos = EventLoop_commonKt.delayToNanos(timeMillis);
        if (jDelayToNanos < DurationKt.MAX_MILLIS) {
            AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
            long jNanoTime = timeSource != null ? timeSource.nanoTime() : System.nanoTime();
            DelayedResumeTask delayedResumeTask = new DelayedResumeTask(jDelayToNanos + jNanoTime, continuation);
            schedule(jNanoTime, delayedResumeTask);
            CancellableContinuationKt.disposeOnCancellation(continuation, delayedResumeTask);
        }
    }

    @Override // kotlinx.coroutines.EventLoop
    public void shutdown() {
        ThreadLocalEventLoop.INSTANCE.resetEventLoop$kotlinx_coroutines_core();
        setCompleted(true);
        closeQueue();
        while (processNextEvent() <= 0) {
        }
        rescheduleAllDelayed();
    }
}

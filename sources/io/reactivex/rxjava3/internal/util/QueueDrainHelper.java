package io.reactivex.rxjava3.internal.util;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.BooleanSupplier;
import io.reactivex.rxjava3.operators.SimplePlainQueue;
import io.reactivex.rxjava3.operators.SimpleQueue;
import io.reactivex.rxjava3.operators.SpscArrayQueue;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class QueueDrainHelper {
    static final long COMPLETED_MASK = Long.MIN_VALUE;
    static final long REQUESTED_MASK = Long.MAX_VALUE;

    private QueueDrainHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static <T, U> boolean checkTerminated(boolean d3, boolean empty, Subscriber<?> s2, boolean delayError, SimpleQueue<?> q2, QueueDrain<T, U> qd) {
        if (qd.cancelled()) {
            q2.clear();
            return true;
        }
        if (!d3) {
            return false;
        }
        if (delayError) {
            if (!empty) {
                return false;
            }
            Throwable thError = qd.error();
            if (thError != null) {
                s2.onError(thError);
            } else {
                s2.onComplete();
            }
            return true;
        }
        Throwable thError2 = qd.error();
        if (thError2 != null) {
            q2.clear();
            s2.onError(thError2);
            return true;
        }
        if (!empty) {
            return false;
        }
        s2.onComplete();
        return true;
    }

    public static <T> SimpleQueue<T> createQueue(int capacityHint) {
        return capacityHint < 0 ? new SpscLinkedArrayQueue(-capacityHint) : new SpscArrayQueue(capacityHint);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0033, code lost:
    
        r1 = r15.leave(-r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0038, code lost:
    
        if (r1 != 0) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x003a, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static <T, U> void drainLoop(io.reactivex.rxjava3.operators.SimplePlainQueue<T> r11, io.reactivex.rxjava3.core.Observer<? super U> r12, boolean r13, io.reactivex.rxjava3.disposables.Disposable r14, io.reactivex.rxjava3.internal.util.ObservableQueueDrain<T, U> r15) {
        /*
            r0 = 1
            r1 = r0
        L2:
            boolean r2 = r15.done()
            boolean r3 = r11.isEmpty()
            r4 = r12
            r5 = r13
            r6 = r11
            r7 = r14
            r8 = r15
            boolean r2 = checkTerminated(r2, r3, r4, r5, r6, r7, r8)
            if (r2 == 0) goto L16
            return
        L16:
            boolean r3 = r15.done()
            java.lang.Object r2 = r11.poll()
            if (r2 != 0) goto L22
            r10 = r0
            goto L24
        L22:
            r4 = 0
            r10 = r4
        L24:
            r4 = r10
            r5 = r12
            r6 = r13
            r7 = r11
            r8 = r14
            r9 = r15
            boolean r3 = checkTerminated(r3, r4, r5, r6, r7, r8, r9)
            if (r3 == 0) goto L31
            return
        L31:
            if (r10 == 0) goto L3b
            int r1 = -r1
            int r1 = r15.leave(r1)
            if (r1 != 0) goto L2
            return
        L3b:
            r15.accept(r12, r2)
            goto L16
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.util.QueueDrainHelper.drainLoop(io.reactivex.rxjava3.operators.SimplePlainQueue, io.reactivex.rxjava3.core.Observer, boolean, io.reactivex.rxjava3.disposables.Disposable, io.reactivex.rxjava3.internal.util.ObservableQueueDrain):void");
    }

    public static <T, U> void drainMaxLoop(SimplePlainQueue<T> q2, Subscriber<? super U> a3, boolean delayError, Disposable dispose, QueueDrain<T, U> qd) {
        int iLeave = 1;
        while (true) {
            boolean zDone = qd.done();
            T tPoll = q2.poll();
            boolean z2 = tPoll == null;
            if (checkTerminated(zDone, z2, a3, delayError, q2, qd)) {
                if (dispose != null) {
                    dispose.dispose();
                    return;
                }
                return;
            } else if (z2) {
                iLeave = qd.leave(-iLeave);
                if (iLeave == 0) {
                    return;
                }
            } else {
                long jRequested = qd.requested();
                if (jRequested == 0) {
                    q2.clear();
                    if (dispose != null) {
                        dispose.dispose();
                    }
                    a3.onError(new MissingBackpressureException("Could not emit value due to lack of requests."));
                    return;
                }
                if (qd.accept(a3, tPoll) && jRequested != Long.MAX_VALUE) {
                    qd.produced(1L);
                }
            }
        }
    }

    public static boolean isCancelled(BooleanSupplier cancelled) {
        try {
            return cancelled.getAsBoolean();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            return true;
        }
    }

    public static <T> void postComplete(Subscriber<? super T> actual, Queue<T> queue, AtomicLong state, BooleanSupplier isCancelled) {
        long j2;
        long j3;
        if (queue.isEmpty()) {
            actual.onComplete();
            return;
        }
        if (postCompleteDrain(state.get(), actual, queue, state, isCancelled)) {
            return;
        }
        do {
            j2 = state.get();
            if ((j2 & Long.MIN_VALUE) != 0) {
                return;
            } else {
                j3 = j2 | Long.MIN_VALUE;
            }
        } while (!state.compareAndSet(j2, j3));
        if (j2 != 0) {
            postCompleteDrain(j3, actual, queue, state, isCancelled);
        }
    }

    public static <T> boolean postCompleteDrain(long j2, Subscriber<? super T> subscriber, Queue<T> queue, AtomicLong atomicLong, BooleanSupplier booleanSupplier) {
        long j3 = j2 & Long.MIN_VALUE;
        while (true) {
            if (j3 != j2) {
                if (isCancelled(booleanSupplier)) {
                    return true;
                }
                T tPoll = queue.poll();
                if (tPoll == null) {
                    subscriber.onComplete();
                    return true;
                }
                subscriber.onNext(tPoll);
                j3++;
            } else {
                if (isCancelled(booleanSupplier)) {
                    return true;
                }
                if (queue.isEmpty()) {
                    subscriber.onComplete();
                    return true;
                }
                j2 = atomicLong.get();
                if (j2 == j3) {
                    long jAddAndGet = atomicLong.addAndGet(-(j3 & Long.MAX_VALUE));
                    if ((Long.MAX_VALUE & jAddAndGet) == 0) {
                        return false;
                    }
                    j2 = jAddAndGet;
                    j3 = jAddAndGet & Long.MIN_VALUE;
                } else {
                    continue;
                }
            }
        }
    }

    public static <T> boolean postCompleteRequest(long n2, Subscriber<? super T> actual, Queue<T> queue, AtomicLong state, BooleanSupplier isCancelled) {
        long j2;
        do {
            j2 = state.get();
        } while (!state.compareAndSet(j2, BackpressureHelper.addCap(Long.MAX_VALUE & j2, n2) | (j2 & Long.MIN_VALUE)));
        if (j2 != Long.MIN_VALUE) {
            return false;
        }
        postCompleteDrain(n2 | Long.MIN_VALUE, actual, queue, state, isCancelled);
        return true;
    }

    public static void request(Subscription s2, int prefetch) {
        s2.request(prefetch < 0 ? Long.MAX_VALUE : prefetch);
    }

    public static <T, U> boolean checkTerminated(boolean d3, boolean empty, Observer<?> observer, boolean delayError, SimpleQueue<?> q2, Disposable disposable, ObservableQueueDrain<T, U> qd) {
        if (qd.cancelled()) {
            q2.clear();
            disposable.dispose();
            return true;
        }
        if (!d3) {
            return false;
        }
        if (delayError) {
            if (!empty) {
                return false;
            }
            if (disposable != null) {
                disposable.dispose();
            }
            Throwable thError = qd.error();
            if (thError != null) {
                observer.onError(thError);
            } else {
                observer.onComplete();
            }
            return true;
        }
        Throwable thError2 = qd.error();
        if (thError2 != null) {
            q2.clear();
            if (disposable != null) {
                disposable.dispose();
            }
            observer.onError(thError2);
            return true;
        }
        if (!empty) {
            return false;
        }
        if (disposable != null) {
            disposable.dispose();
        }
        observer.onComplete();
        return true;
    }
}

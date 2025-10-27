package io.reactivex.observers;

import io.reactivex.Notification;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.VolatileSizeArrayList;
import io.reactivex.observers.BaseTestConsumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
public abstract class BaseTestConsumer<T, U extends BaseTestConsumer<T, U>> implements Disposable {
    protected boolean checkSubscriptionOnce;
    protected long completions;
    protected int establishedFusionMode;
    protected int initialFusionMode;
    protected Thread lastThread;
    protected CharSequence tag;
    protected boolean timeout;
    protected final List<T> values = new VolatileSizeArrayList();
    protected final List<Throwable> errors = new VolatileSizeArrayList();
    protected final CountDownLatch done = new CountDownLatch(1);

    public enum TestWaitStrategy implements Runnable {
        SPIN { // from class: io.reactivex.observers.BaseTestConsumer.TestWaitStrategy.1
            @Override // io.reactivex.observers.BaseTestConsumer.TestWaitStrategy, java.lang.Runnable
            public void run() {
            }
        },
        YIELD { // from class: io.reactivex.observers.BaseTestConsumer.TestWaitStrategy.2
            @Override // io.reactivex.observers.BaseTestConsumer.TestWaitStrategy, java.lang.Runnable
            public void run() {
                Thread.yield();
            }
        },
        SLEEP_1MS { // from class: io.reactivex.observers.BaseTestConsumer.TestWaitStrategy.3
            @Override // io.reactivex.observers.BaseTestConsumer.TestWaitStrategy, java.lang.Runnable
            public void run() throws InterruptedException {
                TestWaitStrategy.sleep(1);
            }
        },
        SLEEP_10MS { // from class: io.reactivex.observers.BaseTestConsumer.TestWaitStrategy.4
            @Override // io.reactivex.observers.BaseTestConsumer.TestWaitStrategy, java.lang.Runnable
            public void run() throws InterruptedException {
                TestWaitStrategy.sleep(10);
            }
        },
        SLEEP_100MS { // from class: io.reactivex.observers.BaseTestConsumer.TestWaitStrategy.5
            @Override // io.reactivex.observers.BaseTestConsumer.TestWaitStrategy, java.lang.Runnable
            public void run() throws InterruptedException {
                TestWaitStrategy.sleep(100);
            }
        },
        SLEEP_1000MS { // from class: io.reactivex.observers.BaseTestConsumer.TestWaitStrategy.6
            @Override // io.reactivex.observers.BaseTestConsumer.TestWaitStrategy, java.lang.Runnable
            public void run() throws InterruptedException {
                TestWaitStrategy.sleep(1000);
            }
        };

        public static void sleep(int i2) throws InterruptedException {
            try {
                Thread.sleep(i2);
            } catch (InterruptedException e2) {
                throw new RuntimeException(e2);
            }
        }

        @Override // java.lang.Runnable
        public abstract void run();
    }

    public static String valueAndClass(Object obj) {
        if (obj == null) {
            return "null";
        }
        return obj + " (class: " + obj.getClass().getSimpleName() + ")";
    }

    public final U assertComplete() {
        long j2 = this.completions;
        if (j2 == 0) {
            throw fail("Not completed");
        }
        if (j2 <= 1) {
            return this;
        }
        throw fail("Multiple completions: " + j2);
    }

    public final U assertEmpty() {
        return (U) assertSubscribed().assertNoValues().assertNoErrors().assertNotComplete();
    }

    public final U assertError(Throwable th) {
        return (U) assertError(Functions.equalsWith(th));
    }

    public final U assertErrorMessage(String str) {
        int size = this.errors.size();
        if (size == 0) {
            throw fail("No errors");
        }
        if (size != 1) {
            throw fail("Multiple errors");
        }
        String message = this.errors.get(0).getMessage();
        if (ObjectHelper.equals(str, message)) {
            return this;
        }
        throw fail("Error message differs; exptected: " + str + " but was: " + message);
    }

    public final U assertFailure(Class<? extends Throwable> cls, T... tArr) {
        return (U) assertSubscribed().assertValues(tArr).assertError(cls).assertNotComplete();
    }

    public final U assertFailureAndMessage(Class<? extends Throwable> cls, String str, T... tArr) {
        return (U) assertSubscribed().assertValues(tArr).assertError(cls).assertErrorMessage(str).assertNotComplete();
    }

    public final U assertNever(T t2) {
        int size = this.values.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (ObjectHelper.equals(this.values.get(i2), t2)) {
                throw fail("Value at position " + i2 + " is equal to " + valueAndClass(t2) + "; Expected them to be different");
            }
        }
        return this;
    }

    public final U assertNoErrors() {
        if (this.errors.size() == 0) {
            return this;
        }
        throw fail("Error(s) present: " + this.errors);
    }

    public final U assertNoTimeout() {
        if (this.timeout) {
            throw fail("Timeout?!");
        }
        return this;
    }

    public final U assertNoValues() {
        return (U) assertValueCount(0);
    }

    public final U assertNotComplete() {
        long j2 = this.completions;
        if (j2 == 1) {
            throw fail("Completed!");
        }
        if (j2 <= 1) {
            return this;
        }
        throw fail("Multiple completions: " + j2);
    }

    public abstract U assertNotSubscribed();

    public final U assertNotTerminated() {
        if (this.done.getCount() != 0) {
            return this;
        }
        throw fail("Subscriber terminated!");
    }

    public final U assertResult(T... tArr) {
        return (U) assertSubscribed().assertValues(tArr).assertNoErrors().assertComplete();
    }

    public abstract U assertSubscribed();

    public final U assertTerminated() {
        if (this.done.getCount() != 0) {
            throw fail("Subscriber still running!");
        }
        long j2 = this.completions;
        if (j2 > 1) {
            throw fail("Terminated with multiple completions: " + j2);
        }
        int size = this.errors.size();
        if (size > 1) {
            throw fail("Terminated with multiple errors: " + size);
        }
        if (j2 == 0 || size == 0) {
            return this;
        }
        throw fail("Terminated with multiple completions and errors: " + j2);
    }

    public final U assertTimeout() {
        if (this.timeout) {
            return this;
        }
        throw fail("No timeout?!");
    }

    public final U assertValue(T t2) {
        if (this.values.size() != 1) {
            throw fail("expected: " + valueAndClass(t2) + " but was: " + this.values);
        }
        T t3 = this.values.get(0);
        if (ObjectHelper.equals(t2, t3)) {
            return this;
        }
        throw fail("expected: " + valueAndClass(t2) + " but was: " + valueAndClass(t3));
    }

    public final U assertValueAt(int i2, T t2) {
        int size = this.values.size();
        if (size == 0) {
            throw fail("No values");
        }
        if (i2 >= size) {
            throw fail("Invalid index: " + i2);
        }
        T t3 = this.values.get(i2);
        if (ObjectHelper.equals(t2, t3)) {
            return this;
        }
        throw fail("expected: " + valueAndClass(t2) + " but was: " + valueAndClass(t3));
    }

    public final U assertValueCount(int i2) {
        int size = this.values.size();
        if (size == i2) {
            return this;
        }
        throw fail("Value counts differ; expected: " + i2 + " but was: " + size);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0059, code lost:
    
        if (r3 != false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x005b, code lost:
    
        if (r2 != false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x005d, code lost:
    
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0076, code lost:
    
        throw fail("Fewer values received than expected (" + r1 + ")");
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x008f, code lost:
    
        throw fail("More values received than expected (" + r1 + ")");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final U assertValueSequence(java.lang.Iterable<? extends T> r6) {
        /*
            r5 = this;
            java.util.List<T> r0 = r5.values
            java.util.Iterator r0 = r0.iterator()
            java.util.Iterator r6 = r6.iterator()
            r1 = 0
        Lb:
            boolean r2 = r6.hasNext()
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L57
            if (r2 != 0) goto L18
            goto L57
        L18:
            java.lang.Object r2 = r6.next()
            java.lang.Object r3 = r0.next()
            boolean r4 = io.reactivex.internal.functions.ObjectHelper.equals(r2, r3)
            if (r4 == 0) goto L29
            int r1 = r1 + 1
            goto Lb
        L29:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r0 = "Values at position "
            r6.append(r0)
            r6.append(r1)
            java.lang.String r0 = " differ; expected: "
            r6.append(r0)
            java.lang.String r0 = valueAndClass(r2)
            r6.append(r0)
            java.lang.String r0 = " but was: "
            r6.append(r0)
            java.lang.String r0 = valueAndClass(r3)
            r6.append(r0)
            java.lang.String r6 = r6.toString()
            java.lang.AssertionError r6 = r5.fail(r6)
            throw r6
        L57:
            java.lang.String r6 = ")"
            if (r3 != 0) goto L77
            if (r2 != 0) goto L5e
            return r5
        L5e:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "Fewer values received than expected ("
            r0.append(r2)
            r0.append(r1)
            r0.append(r6)
            java.lang.String r6 = r0.toString()
            java.lang.AssertionError r6 = r5.fail(r6)
            throw r6
        L77:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "More values received than expected ("
            r0.append(r2)
            r0.append(r1)
            r0.append(r6)
            java.lang.String r6 = r0.toString()
            java.lang.AssertionError r6 = r5.fail(r6)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.observers.BaseTestConsumer.assertValueSequence(java.lang.Iterable):io.reactivex.observers.BaseTestConsumer");
    }

    public final U assertValueSequenceOnly(Iterable<? extends T> iterable) {
        return (U) assertSubscribed().assertValueSequence(iterable).assertNoErrors().assertNotComplete();
    }

    public final U assertValueSet(Collection<? extends T> collection) {
        if (collection.isEmpty()) {
            assertNoValues();
            return this;
        }
        for (T t2 : this.values) {
            if (!collection.contains(t2)) {
                throw fail("Value not in the expected collection: " + valueAndClass(t2));
            }
        }
        return this;
    }

    public final U assertValueSetOnly(Collection<? extends T> collection) {
        return (U) assertSubscribed().assertValueSet(collection).assertNoErrors().assertNotComplete();
    }

    public final U assertValues(T... tArr) {
        int size = this.values.size();
        if (size != tArr.length) {
            throw fail("Value count differs; expected: " + tArr.length + " " + Arrays.toString(tArr) + " but was: " + size + " " + this.values);
        }
        for (int i2 = 0; i2 < size; i2++) {
            T t2 = this.values.get(i2);
            T t3 = tArr[i2];
            if (!ObjectHelper.equals(t3, t2)) {
                throw fail("Values at position " + i2 + " differ; expected: " + valueAndClass(t3) + " but was: " + valueAndClass(t2));
            }
        }
        return this;
    }

    public final U assertValuesOnly(T... tArr) {
        return (U) assertSubscribed().assertValues(tArr).assertNoErrors().assertNotComplete();
    }

    public final U await() throws InterruptedException {
        if (this.done.getCount() == 0) {
            return this;
        }
        this.done.await();
        return this;
    }

    public final U awaitCount(int i2) {
        return (U) awaitCount(i2, TestWaitStrategy.SLEEP_10MS, 5000L);
    }

    public final U awaitDone(long j2, TimeUnit timeUnit) {
        try {
            if (!this.done.await(j2, timeUnit)) {
                this.timeout = true;
                dispose();
            }
            return this;
        } catch (InterruptedException e2) {
            dispose();
            throw ExceptionHelper.wrapOrThrow(e2);
        }
    }

    public final boolean awaitTerminalEvent() {
        try {
            await();
            return true;
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public final U clearTimeout() {
        this.timeout = false;
        return this;
    }

    public final long completions() {
        return this.completions;
    }

    public final int errorCount() {
        return this.errors.size();
    }

    public final List<Throwable> errors() {
        return this.errors;
    }

    public final AssertionError fail(String str) {
        StringBuilder sb = new StringBuilder(str.length() + 64);
        sb.append(str);
        sb.append(" (");
        sb.append("latch = ");
        sb.append(this.done.getCount());
        sb.append(", ");
        sb.append("values = ");
        sb.append(this.values.size());
        sb.append(", ");
        sb.append("errors = ");
        sb.append(this.errors.size());
        sb.append(", ");
        sb.append("completions = ");
        sb.append(this.completions);
        if (this.timeout) {
            sb.append(", timeout!");
        }
        if (isDisposed()) {
            sb.append(", disposed!");
        }
        CharSequence charSequence = this.tag;
        if (charSequence != null) {
            sb.append(", tag = ");
            sb.append(charSequence);
        }
        sb.append(')');
        AssertionError assertionError = new AssertionError(sb.toString());
        if (!this.errors.isEmpty()) {
            if (this.errors.size() == 1) {
                assertionError.initCause(this.errors.get(0));
            } else {
                assertionError.initCause(new CompositeException(this.errors));
            }
        }
        return assertionError;
    }

    public final List<List<Object>> getEvents() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(values());
        arrayList.add(errors());
        ArrayList arrayList2 = new ArrayList();
        for (long j2 = 0; j2 < this.completions; j2++) {
            arrayList2.add(Notification.createOnComplete());
        }
        arrayList.add(arrayList2);
        return arrayList;
    }

    public final boolean isTerminated() {
        return this.done.getCount() == 0;
    }

    public final boolean isTimeout() {
        return this.timeout;
    }

    public final Thread lastThread() {
        return this.lastThread;
    }

    public final int valueCount() {
        return this.values.size();
    }

    public final List<T> values() {
        return this.values;
    }

    public final U withTag(CharSequence charSequence) {
        this.tag = charSequence;
        return this;
    }

    public final U assertError(Class<? extends Throwable> cls) {
        return (U) assertError(Functions.isInstanceOf(cls));
    }

    public final U awaitCount(int i2, Runnable runnable) {
        return (U) awaitCount(i2, runnable, 5000L);
    }

    public final U assertError(Predicate<Throwable> predicate) {
        boolean z2;
        int size = this.errors.size();
        if (size != 0) {
            Iterator<Throwable> it = this.errors.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z2 = false;
                    break;
                }
                try {
                    if (predicate.test(it.next())) {
                        z2 = true;
                        break;
                    }
                } catch (Exception e2) {
                    throw ExceptionHelper.wrapOrThrow(e2);
                }
            }
            if (!z2) {
                throw fail("Error not present");
            }
            if (size == 1) {
                return this;
            }
            throw fail("Error present but other errors as well");
        }
        throw fail("No errors");
    }

    public final boolean await(long j2, TimeUnit timeUnit) throws InterruptedException {
        boolean z2 = this.done.getCount() == 0 || this.done.await(j2, timeUnit);
        this.timeout = !z2;
        return z2;
    }

    public final U awaitCount(int i2, Runnable runnable, long j2) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        while (true) {
            if (j2 > 0 && System.currentTimeMillis() - jCurrentTimeMillis >= j2) {
                this.timeout = true;
                break;
            }
            if (this.done.getCount() == 0 || this.values.size() >= i2) {
                break;
            }
            runnable.run();
        }
        return this;
    }

    public final boolean awaitTerminalEvent(long j2, TimeUnit timeUnit) {
        try {
            return await(j2, timeUnit);
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public final U assertFailure(Predicate<Throwable> predicate, T... tArr) {
        return (U) assertSubscribed().assertValues(tArr).assertError(predicate).assertNotComplete();
    }

    public final U assertNever(Predicate<? super T> predicate) {
        int size = this.values.size();
        for (int i2 = 0; i2 < size; i2++) {
            try {
                if (predicate.test(this.values.get(i2))) {
                    throw fail("Value at position " + i2 + " matches predicate " + predicate.toString() + ", which was not expected.");
                }
            } catch (Exception e2) {
                throw ExceptionHelper.wrapOrThrow(e2);
            }
        }
        return this;
    }

    public final U assertValue(Predicate<T> predicate) {
        assertValueAt(0, (Predicate) predicate);
        if (this.values.size() <= 1) {
            return this;
        }
        throw fail("Value present but other values as well");
    }

    public final U assertValueAt(int i2, Predicate<T> predicate) {
        if (this.values.size() != 0) {
            if (i2 < this.values.size()) {
                try {
                    if (predicate.test(this.values.get(i2))) {
                        return this;
                    }
                    throw fail("Value not present");
                } catch (Exception e2) {
                    throw ExceptionHelper.wrapOrThrow(e2);
                }
            }
            throw fail("Invalid index: " + i2);
        }
        throw fail("No values");
    }
}

package io.reactivex.rxjava3.observers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.internal.util.VolatileSizeArrayList;
import io.reactivex.rxjava3.observers.BaseTestConsumer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
public abstract class BaseTestConsumer<T, U extends BaseTestConsumer<T, U>> {
    protected boolean checkSubscriptionOnce;
    protected long completions;
    protected Thread lastThread;
    protected CharSequence tag;
    protected boolean timeout;
    protected final List<T> values = new VolatileSizeArrayList();
    protected final List<Throwable> errors = new VolatileSizeArrayList();
    protected final CountDownLatch done = new CountDownLatch(1);

    @NonNull
    public static String valueAndClass(@Nullable Object o2) {
        if (o2 == null) {
            return "null";
        }
        return o2 + " (class: " + o2.getClass().getSimpleName() + ")";
    }

    @NonNull
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

    @NonNull
    public final U assertEmpty() {
        return (U) assertSubscribed().assertNoValues().assertNoErrors().assertNotComplete();
    }

    @NonNull
    public final U assertError(@NonNull Throwable th) {
        return (U) assertError(Functions.equalsWith(th), true);
    }

    @SafeVarargs
    @NonNull
    public final U assertFailure(@NonNull Class<? extends Throwable> cls, @NonNull T... tArr) {
        return (U) assertSubscribed().assertValues(tArr).assertError(cls).assertNotComplete();
    }

    @NonNull
    public final U assertNoErrors() {
        if (this.errors.size() == 0) {
            return this;
        }
        throw fail("Error(s) present: " + this.errors);
    }

    @NonNull
    public final U assertNoValues() {
        return (U) assertValueCount(0);
    }

    @NonNull
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

    @SafeVarargs
    @NonNull
    public final U assertResult(@NonNull T... tArr) {
        return (U) assertSubscribed().assertValues(tArr).assertNoErrors().assertComplete();
    }

    @NonNull
    public abstract U assertSubscribed();

    @NonNull
    public final U assertValue(@NonNull T value) {
        if (this.values.size() != 1) {
            throw fail("\nexpected: " + valueAndClass(value) + "\ngot: " + this.values);
        }
        T t2 = this.values.get(0);
        if (Objects.equals(value, t2)) {
            return this;
        }
        throw fail("\nexpected: " + valueAndClass(value) + "\ngot: " + valueAndClass(t2));
    }

    @NonNull
    public final U assertValueAt(int index, @NonNull T value) {
        int size = this.values.size();
        if (size == 0) {
            throw fail("No values");
        }
        if (index < 0 || index >= size) {
            throw fail("Index " + index + " is out of range [0, " + size + ")");
        }
        T t2 = this.values.get(index);
        if (Objects.equals(value, t2)) {
            return this;
        }
        throw fail("\nexpected: " + valueAndClass(value) + "\ngot: " + valueAndClass(t2) + "; Value at position " + index + " differ");
    }

    @NonNull
    public final U assertValueCount(int count) {
        int size = this.values.size();
        if (size == count) {
            return this;
        }
        throw fail("\nexpected: " + count + "\ngot: " + size + "; Value counts differ");
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x005e, code lost:
    
        if (r3 != false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0060, code lost:
    
        if (r2 != false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0062, code lost:
    
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x007b, code lost:
    
        throw fail("Fewer values received than expected (" + r1 + ")");
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0094, code lost:
    
        throw fail("More values received than expected (" + r1 + ")");
     */
    @io.reactivex.rxjava3.annotations.NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final U assertValueSequence(@io.reactivex.rxjava3.annotations.NonNull java.lang.Iterable<? extends T> r6) {
        /*
            r5 = this;
            java.util.List<T> r0 = r5.values
            java.util.Iterator r0 = r0.iterator()
            java.util.Iterator r6 = r6.iterator()
            r1 = 0
        Lb:
            boolean r2 = r6.hasNext()
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L5c
            if (r2 != 0) goto L18
            goto L5c
        L18:
            java.lang.Object r2 = r6.next()
            java.lang.Object r3 = r0.next()
            boolean r4 = java.util.Objects.equals(r2, r3)
            if (r4 == 0) goto L29
            int r1 = r1 + 1
            goto Lb
        L29:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r0 = "\nexpected: "
            r6.append(r0)
            java.lang.String r0 = valueAndClass(r2)
            r6.append(r0)
            java.lang.String r0 = "\ngot: "
            r6.append(r0)
            java.lang.String r0 = valueAndClass(r3)
            r6.append(r0)
            java.lang.String r0 = "; Value at position "
            r6.append(r0)
            r6.append(r1)
            java.lang.String r0 = " differ"
            r6.append(r0)
            java.lang.String r6 = r6.toString()
            java.lang.AssertionError r6 = r5.fail(r6)
            throw r6
        L5c:
            java.lang.String r6 = ")"
            if (r3 != 0) goto L7c
            if (r2 != 0) goto L63
            return r5
        L63:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "Fewer values received than expected ("
            r0.append(r2)
            r0.append(r1)
            r0.append(r6)
            java.lang.String r6 = r0.toString()
            java.lang.AssertionError r6 = r5.fail(r6)
            throw r6
        L7c:
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
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.observers.BaseTestConsumer.assertValueSequence(java.lang.Iterable):io.reactivex.rxjava3.observers.BaseTestConsumer");
    }

    @SafeVarargs
    @NonNull
    public final U assertValues(@NonNull T... values) {
        int size = this.values.size();
        if (size != values.length) {
            throw fail("\nexpected: " + values.length + " " + Arrays.toString(values) + "\ngot: " + size + " " + this.values + "; Value count differs");
        }
        for (int i2 = 0; i2 < size; i2++) {
            T t2 = this.values.get(i2);
            T t3 = values[i2];
            if (!Objects.equals(t3, t2)) {
                throw fail("\nexpected: " + valueAndClass(t3) + "\ngot: " + valueAndClass(t2) + "; Value at position " + i2 + " differ");
            }
        }
        return this;
    }

    @SafeVarargs
    @NonNull
    public final U assertValuesOnly(@NonNull T... tArr) {
        return (U) assertSubscribed().assertValues(tArr).assertNoErrors().assertNotComplete();
    }

    @NonNull
    public final U await() throws InterruptedException {
        if (this.done.getCount() == 0) {
            return this;
        }
        this.done.await();
        return this;
    }

    @NonNull
    public final U awaitCount(int atLeast) throws InterruptedException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - jCurrentTimeMillis < 5000) {
                if (this.done.getCount() == 0 || this.values.size() >= atLeast) {
                    break;
                }
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e2) {
                    throw new RuntimeException(e2);
                }
            } else {
                this.timeout = true;
                break;
            }
        }
        return this;
    }

    @NonNull
    public final U awaitDone(long time, @NonNull TimeUnit unit) {
        try {
            if (!this.done.await(time, unit)) {
                this.timeout = true;
                dispose();
            }
            return this;
        } catch (InterruptedException e2) {
            dispose();
            throw ExceptionHelper.wrapOrThrow(e2);
        }
    }

    public abstract void dispose();

    @NonNull
    public final AssertionError fail(@NonNull String message) {
        StringBuilder sb = new StringBuilder(message.length() + 64);
        sb.append(message);
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

    public abstract boolean isDisposed();

    @NonNull
    public final List<T> values() {
        return this.values;
    }

    @NonNull
    public final U withTag(@Nullable CharSequence tag) {
        this.tag = tag;
        return this;
    }

    @NonNull
    public final U assertError(@NonNull Class<? extends Throwable> cls) {
        return (U) assertError(Functions.isInstanceOf(cls), true);
    }

    @NonNull
    public final U assertError(@NonNull Predicate<Throwable> predicate) {
        return (U) assertError(predicate, false);
    }

    public final boolean await(long time, @NonNull TimeUnit unit) throws InterruptedException {
        boolean z2 = this.done.getCount() == 0 || this.done.await(time, unit);
        this.timeout = !z2;
        return z2;
    }

    @NonNull
    private U assertError(@NonNull Predicate<Throwable> errorPredicate, boolean exact) {
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
                    if (errorPredicate.test(it.next())) {
                        z2 = true;
                        break;
                    }
                } catch (Throwable th) {
                    throw ExceptionHelper.wrapOrThrow(th);
                }
            }
            if (!z2) {
                if (exact) {
                    throw fail("Error not present");
                }
                throw fail("No error(s) passed the predicate");
            }
            if (size == 1) {
                return this;
            }
            if (exact) {
                throw fail("Error present but other errors as well");
            }
            throw fail("One error passed the predicate but other errors are present as well");
        }
        throw fail("No errors");
    }

    @NonNull
    public final U assertValue(@NonNull Predicate<T> valuePredicate) {
        assertValueAt(0, (Predicate) valuePredicate);
        if (this.values.size() <= 1) {
            return this;
        }
        throw fail("The first value passed the predicate but this consumer received more than one value");
    }

    @NonNull
    public final U assertValueAt(int index, @NonNull Predicate<T> valuePredicate) {
        int size = this.values.size();
        if (size == 0) {
            throw fail("No values");
        }
        if (index >= 0 && index < size) {
            T t2 = this.values.get(index);
            try {
                if (valuePredicate.test(t2)) {
                    return this;
                }
                throw fail("Value " + valueAndClass(t2) + " at position " + index + " did not pass the predicate");
            } catch (Throwable th) {
                throw ExceptionHelper.wrapOrThrow(th);
            }
        }
        throw fail("Index " + index + " is out of range [0, " + size + ")");
    }
}

package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
public abstract class AbstractIterator<T> extends UnmodifiableIterator<T> {

    @NullableDecl
    private T next;
    private State state = State.NOT_READY;

    /* renamed from: com.google.common.collect.AbstractIterator$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$common$collect$AbstractIterator$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$com$google$common$collect$AbstractIterator$State = iArr;
            try {
                iArr[State.DONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$common$collect$AbstractIterator$State[State.READY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public enum State {
        READY,
        NOT_READY,
        DONE,
        FAILED
    }

    private boolean tryToComputeNext() {
        this.state = State.FAILED;
        this.next = computeNext();
        if (this.state == State.DONE) {
            return false;
        }
        this.state = State.READY;
        return true;
    }

    public abstract T computeNext();

    @CanIgnoreReturnValue
    public final T endOfData() {
        this.state = State.DONE;
        return null;
    }

    @Override // java.util.Iterator
    @CanIgnoreReturnValue
    public final boolean hasNext() {
        Preconditions.checkState(this.state != State.FAILED);
        int i2 = AnonymousClass1.$SwitchMap$com$google$common$collect$AbstractIterator$State[this.state.ordinal()];
        if (i2 == 1) {
            return false;
        }
        if (i2 != 2) {
            return tryToComputeNext();
        }
        return true;
    }

    @Override // java.util.Iterator
    @CanIgnoreReturnValue
    public final T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        this.state = State.NOT_READY;
        T t2 = this.next;
        this.next = null;
        return t2;
    }

    public final T peek() {
        if (hasNext()) {
            return this.next;
        }
        throw new NoSuchElementException();
    }
}

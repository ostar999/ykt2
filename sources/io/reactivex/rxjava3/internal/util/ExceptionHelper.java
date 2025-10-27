package io.reactivex.rxjava3.internal.util;

import androidx.camera.view.j;
import io.reactivex.rxjava3.exceptions.CompositeException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ExceptionHelper {
    public static final Throwable TERMINATED = new Termination();

    public static final class Termination extends Throwable {
        private static final long serialVersionUID = -4649703670690200604L;

        public Termination() {
            super("No further exceptions");
        }

        @Override // java.lang.Throwable
        public Throwable fillInStackTrace() {
            return this;
        }
    }

    private ExceptionHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static boolean addThrowable(AtomicReference<Throwable> field, Throwable exception) {
        Throwable th;
        do {
            th = field.get();
            if (th == TERMINATED) {
                return false;
            }
        } while (!j.a(field, th, th == null ? exception : new CompositeException(th, exception)));
        return true;
    }

    public static NullPointerException createNullPointerException(String prefix) {
        return new NullPointerException(nullWarning(prefix));
    }

    public static List<Throwable> flatten(Throwable t2) {
        ArrayList arrayList = new ArrayList();
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.offer(t2);
        while (!arrayDeque.isEmpty()) {
            Throwable th = (Throwable) arrayDeque.removeFirst();
            if (th instanceof CompositeException) {
                List<Throwable> exceptions = ((CompositeException) th).getExceptions();
                for (int size = exceptions.size() - 1; size >= 0; size--) {
                    arrayDeque.offerFirst(exceptions.get(size));
                }
            } else {
                arrayList.add(th);
            }
        }
        return arrayList;
    }

    public static <T> T nullCheck(T value, String prefix) {
        if (value != null) {
            return value;
        }
        throw createNullPointerException(prefix);
    }

    public static String nullWarning(String prefix) {
        return prefix + " Null values are generally not allowed in 3.x operators and sources.";
    }

    public static Throwable terminate(AtomicReference<Throwable> field) {
        Throwable th = field.get();
        Throwable th2 = TERMINATED;
        return th != th2 ? field.getAndSet(th2) : th;
    }

    public static <E extends Throwable> Exception throwIfThrowable(Throwable e2) throws Throwable {
        if (e2 instanceof Exception) {
            return (Exception) e2;
        }
        throw e2;
    }

    public static String timeoutMessage(long timeout, TimeUnit unit) {
        return "The source did not signal an event for " + timeout + " " + unit.toString().toLowerCase() + " and has been terminated.";
    }

    public static RuntimeException wrapOrThrow(Throwable error) {
        if (error instanceof Error) {
            throw ((Error) error);
        }
        return error instanceof RuntimeException ? (RuntimeException) error : new RuntimeException(error);
    }
}

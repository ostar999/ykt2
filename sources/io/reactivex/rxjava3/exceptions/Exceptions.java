package io.reactivex.rxjava3.exceptions;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;

/* loaded from: classes8.dex */
public final class Exceptions {
    private Exceptions() {
        throw new IllegalStateException("No instances!");
    }

    @NonNull
    public static RuntimeException propagate(@NonNull Throwable t2) {
        throw ExceptionHelper.wrapOrThrow(t2);
    }

    public static void throwIfFatal(@NonNull Throwable t2) {
        if (t2 instanceof VirtualMachineError) {
            throw ((VirtualMachineError) t2);
        }
        if (t2 instanceof ThreadDeath) {
            throw ((ThreadDeath) t2);
        }
        if (t2 instanceof LinkageError) {
            throw ((LinkageError) t2);
        }
    }
}

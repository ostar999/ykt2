package com.jakewharton.rxbinding2.internal;

import androidx.annotation.RestrictTo;
import io.reactivex.functions.Predicate;
import java.util.concurrent.Callable;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes4.dex */
public final class Functions {
    private static final Always ALWAYS_TRUE;
    public static final Callable<Boolean> CALLABLE_ALWAYS_TRUE;
    public static final Predicate<Object> PREDICATE_ALWAYS_TRUE;

    public static final class Always implements Callable<Boolean>, Predicate<Object> {
        private final Boolean value;

        public Always(Boolean bool) {
            this.value = bool;
        }

        @Override // io.reactivex.functions.Predicate
        public boolean test(Object obj) throws Exception {
            return this.value.booleanValue();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        public Boolean call() {
            return this.value;
        }
    }

    static {
        Always always = new Always(Boolean.TRUE);
        ALWAYS_TRUE = always;
        CALLABLE_ALWAYS_TRUE = always;
        PREDICATE_ALWAYS_TRUE = always;
    }

    private Functions() {
        throw new AssertionError("No instances.");
    }
}

package androidx.core.util;

import android.annotation.SuppressLint;
import java.util.Objects;

/* loaded from: classes.dex */
public final /* synthetic */ class j<T> {
    @SuppressLint({"MissingNullability"})
    public static Predicate a(final Predicate predicate, @SuppressLint({"MissingNullability"}) final Predicate predicate2) {
        Objects.requireNonNull(predicate2);
        return new Predicate() { // from class: androidx.core.util.h
            @Override // androidx.core.util.Predicate
            public /* synthetic */ Predicate and(Predicate predicate3) {
                return j.a(this, predicate3);
            }

            @Override // androidx.core.util.Predicate
            public /* synthetic */ Predicate negate() {
                return j.b(this);
            }

            @Override // androidx.core.util.Predicate
            public /* synthetic */ Predicate or(Predicate predicate3) {
                return j.c(this, predicate3);
            }

            @Override // androidx.core.util.Predicate
            public final boolean test(Object obj) {
                return j.d(predicate, predicate2, obj);
            }
        };
    }

    @SuppressLint({"MissingNullability"})
    public static Predicate b(final Predicate predicate) {
        return new Predicate() { // from class: androidx.core.util.i
            @Override // androidx.core.util.Predicate
            public /* synthetic */ Predicate and(Predicate predicate2) {
                return j.a(this, predicate2);
            }

            @Override // androidx.core.util.Predicate
            public /* synthetic */ Predicate negate() {
                return j.b(this);
            }

            @Override // androidx.core.util.Predicate
            public /* synthetic */ Predicate or(Predicate predicate2) {
                return j.c(this, predicate2);
            }

            @Override // androidx.core.util.Predicate
            public final boolean test(Object obj) {
                return j.e(predicate, obj);
            }
        };
    }

    @SuppressLint({"MissingNullability"})
    public static Predicate c(final Predicate predicate, @SuppressLint({"MissingNullability"}) final Predicate predicate2) {
        Objects.requireNonNull(predicate2);
        return new Predicate() { // from class: androidx.core.util.e
            @Override // androidx.core.util.Predicate
            public /* synthetic */ Predicate and(Predicate predicate3) {
                return j.a(this, predicate3);
            }

            @Override // androidx.core.util.Predicate
            public /* synthetic */ Predicate negate() {
                return j.b(this);
            }

            @Override // androidx.core.util.Predicate
            public /* synthetic */ Predicate or(Predicate predicate3) {
                return j.c(this, predicate3);
            }

            @Override // androidx.core.util.Predicate
            public final boolean test(Object obj) {
                return j.f(predicate, predicate2, obj);
            }
        };
    }

    public static /* synthetic */ boolean d(Predicate predicate, Predicate predicate2, Object obj) {
        return predicate.test(obj) && predicate2.test(obj);
    }

    public static /* synthetic */ boolean e(Predicate predicate, Object obj) {
        return !predicate.test(obj);
    }

    public static /* synthetic */ boolean f(Predicate predicate, Predicate predicate2, Object obj) {
        return predicate.test(obj) || predicate2.test(obj);
    }

    @SuppressLint({"MissingNullability"})
    public static <T> Predicate<T> g(@SuppressLint({"MissingNullability"}) final Object obj) {
        return obj == null ? new Predicate() { // from class: androidx.core.util.f
            @Override // androidx.core.util.Predicate
            public /* synthetic */ Predicate and(Predicate predicate) {
                return j.a(this, predicate);
            }

            @Override // androidx.core.util.Predicate
            public /* synthetic */ Predicate negate() {
                return j.b(this);
            }

            @Override // androidx.core.util.Predicate
            public /* synthetic */ Predicate or(Predicate predicate) {
                return j.c(this, predicate);
            }

            @Override // androidx.core.util.Predicate
            public final boolean test(Object obj2) {
                return d.a(obj2);
            }
        } : new Predicate() { // from class: androidx.core.util.g
            @Override // androidx.core.util.Predicate
            public /* synthetic */ Predicate and(Predicate predicate) {
                return j.a(this, predicate);
            }

            @Override // androidx.core.util.Predicate
            public /* synthetic */ Predicate negate() {
                return j.b(this);
            }

            @Override // androidx.core.util.Predicate
            public /* synthetic */ Predicate or(Predicate predicate) {
                return j.c(this, predicate);
            }

            @Override // androidx.core.util.Predicate
            public final boolean test(Object obj2) {
                return obj.equals(obj2);
            }
        };
    }

    @SuppressLint({"MissingNullability"})
    public static <T> Predicate<T> j(@SuppressLint({"MissingNullability"}) Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        return predicate.negate();
    }
}

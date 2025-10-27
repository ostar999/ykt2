package androidx.core.content;

import androidx.core.util.Predicate;

/* loaded from: classes.dex */
public final /* synthetic */ class n implements Predicate {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ String f1800a;

    @Override // androidx.core.util.Predicate
    public /* synthetic */ Predicate and(Predicate predicate) {
        return androidx.core.util.j.a(this, predicate);
    }

    @Override // androidx.core.util.Predicate
    public /* synthetic */ Predicate negate() {
        return androidx.core.util.j.b(this);
    }

    @Override // androidx.core.util.Predicate
    public /* synthetic */ Predicate or(Predicate predicate) {
        return androidx.core.util.j.c(this, predicate);
    }

    @Override // androidx.core.util.Predicate
    public final boolean test(Object obj) {
        return this.f1800a.equals((String) obj);
    }
}

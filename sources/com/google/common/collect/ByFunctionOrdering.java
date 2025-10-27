package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(serializable = true)
/* loaded from: classes4.dex */
final class ByFunctionOrdering<F, T> extends Ordering<F> implements Serializable {
    private static final long serialVersionUID = 0;
    final Function<F, ? extends T> function;
    final Ordering<T> ordering;

    public ByFunctionOrdering(Function<F, ? extends T> function, Ordering<T> ordering) {
        this.function = (Function) Preconditions.checkNotNull(function);
        this.ordering = (Ordering) Preconditions.checkNotNull(ordering);
    }

    @Override // com.google.common.collect.Ordering, java.util.Comparator
    public int compare(F f2, F f3) {
        return this.ordering.compare(this.function.apply(f2), this.function.apply(f3));
    }

    @Override // java.util.Comparator
    public boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ByFunctionOrdering)) {
            return false;
        }
        ByFunctionOrdering byFunctionOrdering = (ByFunctionOrdering) obj;
        return this.function.equals(byFunctionOrdering.function) && this.ordering.equals(byFunctionOrdering.ordering);
    }

    public int hashCode() {
        return Objects.hashCode(this.function, this.ordering);
    }

    public String toString() {
        return this.ordering + ".onResultOf(" + this.function + ")";
    }
}

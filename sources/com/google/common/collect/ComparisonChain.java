package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.primitives.Booleans;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.util.Comparator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
public abstract class ComparisonChain {
    private static final ComparisonChain ACTIVE = new ComparisonChain() { // from class: com.google.common.collect.ComparisonChain.1
        public ComparisonChain classify(int i2) {
            return i2 < 0 ? ComparisonChain.LESS : i2 > 0 ? ComparisonChain.GREATER : ComparisonChain.ACTIVE;
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compare(Comparable comparable, Comparable comparable2) {
            return classify(comparable.compareTo(comparable2));
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compareFalseFirst(boolean z2, boolean z3) {
            return classify(Booleans.compare(z2, z3));
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compareTrueFirst(boolean z2, boolean z3) {
            return classify(Booleans.compare(z3, z2));
        }

        @Override // com.google.common.collect.ComparisonChain
        public int result() {
            return 0;
        }

        @Override // com.google.common.collect.ComparisonChain
        public <T> ComparisonChain compare(@NullableDecl T t2, @NullableDecl T t3, Comparator<T> comparator) {
            return classify(comparator.compare(t2, t3));
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compare(int i2, int i3) {
            return classify(Ints.compare(i2, i3));
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compare(long j2, long j3) {
            return classify(Longs.compare(j2, j3));
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compare(float f2, float f3) {
            return classify(Float.compare(f2, f3));
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compare(double d3, double d4) {
            return classify(Double.compare(d3, d4));
        }
    };
    private static final ComparisonChain LESS = new InactiveComparisonChain(-1);
    private static final ComparisonChain GREATER = new InactiveComparisonChain(1);

    public static final class InactiveComparisonChain extends ComparisonChain {
        final int result;

        public InactiveComparisonChain(int i2) {
            super();
            this.result = i2;
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compare(double d3, double d4) {
            return this;
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compare(float f2, float f3) {
            return this;
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compare(int i2, int i3) {
            return this;
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compare(long j2, long j3) {
            return this;
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compare(@NullableDecl Comparable comparable, @NullableDecl Comparable comparable2) {
            return this;
        }

        @Override // com.google.common.collect.ComparisonChain
        public <T> ComparisonChain compare(@NullableDecl T t2, @NullableDecl T t3, @NullableDecl Comparator<T> comparator) {
            return this;
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compareFalseFirst(boolean z2, boolean z3) {
            return this;
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compareTrueFirst(boolean z2, boolean z3) {
            return this;
        }

        @Override // com.google.common.collect.ComparisonChain
        public int result() {
            return this.result;
        }
    }

    public static ComparisonChain start() {
        return ACTIVE;
    }

    public abstract ComparisonChain compare(double d3, double d4);

    public abstract ComparisonChain compare(float f2, float f3);

    public abstract ComparisonChain compare(int i2, int i3);

    public abstract ComparisonChain compare(long j2, long j3);

    @Deprecated
    public final ComparisonChain compare(Boolean bool, Boolean bool2) {
        return compareFalseFirst(bool.booleanValue(), bool2.booleanValue());
    }

    public abstract ComparisonChain compare(Comparable<?> comparable, Comparable<?> comparable2);

    public abstract <T> ComparisonChain compare(@NullableDecl T t2, @NullableDecl T t3, Comparator<T> comparator);

    public abstract ComparisonChain compareFalseFirst(boolean z2, boolean z3);

    public abstract ComparisonChain compareTrueFirst(boolean z2, boolean z3);

    public abstract int result();

    private ComparisonChain() {
    }
}

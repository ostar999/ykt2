package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.lang.Comparable;
import java.math.BigInteger;
import java.util.NoSuchElementException;

@GwtCompatible
/* loaded from: classes4.dex */
public abstract class DiscreteDomain<C extends Comparable> {
    final boolean supportsFastOffset;

    public static final class BigIntegerDomain extends DiscreteDomain<BigInteger> implements Serializable {
        private static final long serialVersionUID = 0;
        private static final BigIntegerDomain INSTANCE = new BigIntegerDomain();
        private static final BigInteger MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
        private static final BigInteger MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);

        public BigIntegerDomain() {
            super(true);
        }

        private Object readResolve() {
            return INSTANCE;
        }

        public String toString() {
            return "DiscreteDomain.bigIntegers()";
        }

        @Override // com.google.common.collect.DiscreteDomain
        public long distance(BigInteger bigInteger, BigInteger bigInteger2) {
            return bigInteger2.subtract(bigInteger).max(MIN_LONG).min(MAX_LONG).longValue();
        }

        @Override // com.google.common.collect.DiscreteDomain
        public BigInteger next(BigInteger bigInteger) {
            return bigInteger.add(BigInteger.ONE);
        }

        @Override // com.google.common.collect.DiscreteDomain
        public BigInteger offset(BigInteger bigInteger, long j2) {
            CollectPreconditions.checkNonnegative(j2, "distance");
            return bigInteger.add(BigInteger.valueOf(j2));
        }

        @Override // com.google.common.collect.DiscreteDomain
        public BigInteger previous(BigInteger bigInteger) {
            return bigInteger.subtract(BigInteger.ONE);
        }
    }

    public static final class IntegerDomain extends DiscreteDomain<Integer> implements Serializable {
        private static final IntegerDomain INSTANCE = new IntegerDomain();
        private static final long serialVersionUID = 0;

        public IntegerDomain() {
            super(true);
        }

        private Object readResolve() {
            return INSTANCE;
        }

        public String toString() {
            return "DiscreteDomain.integers()";
        }

        @Override // com.google.common.collect.DiscreteDomain
        public long distance(Integer num, Integer num2) {
            return num2.intValue() - num.intValue();
        }

        @Override // com.google.common.collect.DiscreteDomain
        public Integer maxValue() {
            return Integer.MAX_VALUE;
        }

        @Override // com.google.common.collect.DiscreteDomain
        public Integer minValue() {
            return Integer.MIN_VALUE;
        }

        @Override // com.google.common.collect.DiscreteDomain
        public Integer next(Integer num) {
            int iIntValue = num.intValue();
            if (iIntValue == Integer.MAX_VALUE) {
                return null;
            }
            return Integer.valueOf(iIntValue + 1);
        }

        @Override // com.google.common.collect.DiscreteDomain
        public Integer offset(Integer num, long j2) {
            CollectPreconditions.checkNonnegative(j2, "distance");
            return Integer.valueOf(Ints.checkedCast(num.longValue() + j2));
        }

        @Override // com.google.common.collect.DiscreteDomain
        public Integer previous(Integer num) {
            int iIntValue = num.intValue();
            if (iIntValue == Integer.MIN_VALUE) {
                return null;
            }
            return Integer.valueOf(iIntValue - 1);
        }
    }

    public static final class LongDomain extends DiscreteDomain<Long> implements Serializable {
        private static final LongDomain INSTANCE = new LongDomain();
        private static final long serialVersionUID = 0;

        public LongDomain() {
            super(true);
        }

        private Object readResolve() {
            return INSTANCE;
        }

        public String toString() {
            return "DiscreteDomain.longs()";
        }

        @Override // com.google.common.collect.DiscreteDomain
        public long distance(Long l2, Long l3) {
            long jLongValue = l3.longValue() - l2.longValue();
            if (l3.longValue() > l2.longValue() && jLongValue < 0) {
                return Long.MAX_VALUE;
            }
            if (l3.longValue() >= l2.longValue() || jLongValue <= 0) {
                return jLongValue;
            }
            return Long.MIN_VALUE;
        }

        @Override // com.google.common.collect.DiscreteDomain
        public Long maxValue() {
            return Long.MAX_VALUE;
        }

        @Override // com.google.common.collect.DiscreteDomain
        public Long minValue() {
            return Long.MIN_VALUE;
        }

        @Override // com.google.common.collect.DiscreteDomain
        public Long next(Long l2) {
            long jLongValue = l2.longValue();
            if (jLongValue == Long.MAX_VALUE) {
                return null;
            }
            return Long.valueOf(jLongValue + 1);
        }

        @Override // com.google.common.collect.DiscreteDomain
        public Long offset(Long l2, long j2) {
            CollectPreconditions.checkNonnegative(j2, "distance");
            long jLongValue = l2.longValue() + j2;
            if (jLongValue < 0) {
                Preconditions.checkArgument(l2.longValue() < 0, "overflow");
            }
            return Long.valueOf(jLongValue);
        }

        @Override // com.google.common.collect.DiscreteDomain
        public Long previous(Long l2) {
            long jLongValue = l2.longValue();
            if (jLongValue == Long.MIN_VALUE) {
                return null;
            }
            return Long.valueOf(jLongValue - 1);
        }
    }

    public static DiscreteDomain<BigInteger> bigIntegers() {
        return BigIntegerDomain.INSTANCE;
    }

    public static DiscreteDomain<Integer> integers() {
        return IntegerDomain.INSTANCE;
    }

    public static DiscreteDomain<Long> longs() {
        return LongDomain.INSTANCE;
    }

    public abstract long distance(C c3, C c4);

    @CanIgnoreReturnValue
    public C maxValue() {
        throw new NoSuchElementException();
    }

    @CanIgnoreReturnValue
    public C minValue() {
        throw new NoSuchElementException();
    }

    public abstract C next(C c3);

    public C offset(C c3, long j2) {
        CollectPreconditions.checkNonnegative(j2, "distance");
        for (long j3 = 0; j3 < j2; j3++) {
            c3 = (C) next(c3);
        }
        return c3;
    }

    public abstract C previous(C c3);

    public DiscreteDomain() {
        this(false);
    }

    private DiscreteDomain(boolean z2) {
        this.supportsFastOffset = z2;
    }
}

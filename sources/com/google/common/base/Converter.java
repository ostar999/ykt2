package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.ForOverride;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes3.dex */
public abstract class Converter<A, B> implements Function<A, B> {
    private final boolean handleNullAutomatically;

    @LazyInit
    @MonotonicNonNullDecl
    private transient Converter<B, A> reverse;

    public static final class ConverterComposition<A, B, C> extends Converter<A, C> implements Serializable {
        private static final long serialVersionUID = 0;
        final Converter<A, B> first;
        final Converter<B, C> second;

        public ConverterComposition(Converter<A, B> converter, Converter<B, C> converter2) {
            this.first = converter;
            this.second = converter2;
        }

        @Override // com.google.common.base.Converter
        @NullableDecl
        public A correctedDoBackward(@NullableDecl C c3) {
            return (A) this.first.correctedDoBackward(this.second.correctedDoBackward(c3));
        }

        @Override // com.google.common.base.Converter
        @NullableDecl
        public C correctedDoForward(@NullableDecl A a3) {
            return (C) this.second.correctedDoForward(this.first.correctedDoForward(a3));
        }

        @Override // com.google.common.base.Converter
        public A doBackward(C c3) {
            throw new AssertionError();
        }

        @Override // com.google.common.base.Converter
        public C doForward(A a3) {
            throw new AssertionError();
        }

        @Override // com.google.common.base.Converter, com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof ConverterComposition)) {
                return false;
            }
            ConverterComposition converterComposition = (ConverterComposition) obj;
            return this.first.equals(converterComposition.first) && this.second.equals(converterComposition.second);
        }

        public int hashCode() {
            return (this.first.hashCode() * 31) + this.second.hashCode();
        }

        public String toString() {
            return this.first + ".andThen(" + this.second + ")";
        }
    }

    public static final class FunctionBasedConverter<A, B> extends Converter<A, B> implements Serializable {
        private final Function<? super B, ? extends A> backwardFunction;
        private final Function<? super A, ? extends B> forwardFunction;

        @Override // com.google.common.base.Converter
        public A doBackward(B b3) {
            return this.backwardFunction.apply(b3);
        }

        @Override // com.google.common.base.Converter
        public B doForward(A a3) {
            return this.forwardFunction.apply(a3);
        }

        @Override // com.google.common.base.Converter, com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof FunctionBasedConverter)) {
                return false;
            }
            FunctionBasedConverter functionBasedConverter = (FunctionBasedConverter) obj;
            return this.forwardFunction.equals(functionBasedConverter.forwardFunction) && this.backwardFunction.equals(functionBasedConverter.backwardFunction);
        }

        public int hashCode() {
            return (this.forwardFunction.hashCode() * 31) + this.backwardFunction.hashCode();
        }

        public String toString() {
            return "Converter.from(" + this.forwardFunction + ", " + this.backwardFunction + ")";
        }

        private FunctionBasedConverter(Function<? super A, ? extends B> function, Function<? super B, ? extends A> function2) {
            this.forwardFunction = (Function) Preconditions.checkNotNull(function);
            this.backwardFunction = (Function) Preconditions.checkNotNull(function2);
        }
    }

    public static final class IdentityConverter<T> extends Converter<T, T> implements Serializable {
        static final IdentityConverter INSTANCE = new IdentityConverter();
        private static final long serialVersionUID = 0;

        private IdentityConverter() {
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override // com.google.common.base.Converter
        public <S> Converter<T, S> doAndThen(Converter<T, S> converter) {
            return (Converter) Preconditions.checkNotNull(converter, "otherConverter");
        }

        @Override // com.google.common.base.Converter
        public T doBackward(T t2) {
            return t2;
        }

        @Override // com.google.common.base.Converter
        public T doForward(T t2) {
            return t2;
        }

        @Override // com.google.common.base.Converter
        public IdentityConverter<T> reverse() {
            return this;
        }

        public String toString() {
            return "Converter.identity()";
        }
    }

    public static final class ReverseConverter<A, B> extends Converter<B, A> implements Serializable {
        private static final long serialVersionUID = 0;
        final Converter<A, B> original;

        public ReverseConverter(Converter<A, B> converter) {
            this.original = converter;
        }

        @Override // com.google.common.base.Converter
        @NullableDecl
        public B correctedDoBackward(@NullableDecl A a3) {
            return this.original.correctedDoForward(a3);
        }

        @Override // com.google.common.base.Converter
        @NullableDecl
        public A correctedDoForward(@NullableDecl B b3) {
            return this.original.correctedDoBackward(b3);
        }

        @Override // com.google.common.base.Converter
        public B doBackward(A a3) {
            throw new AssertionError();
        }

        @Override // com.google.common.base.Converter
        public A doForward(B b3) {
            throw new AssertionError();
        }

        @Override // com.google.common.base.Converter, com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof ReverseConverter) {
                return this.original.equals(((ReverseConverter) obj).original);
            }
            return false;
        }

        public int hashCode() {
            return ~this.original.hashCode();
        }

        @Override // com.google.common.base.Converter
        public Converter<A, B> reverse() {
            return this.original;
        }

        public String toString() {
            return this.original + ".reverse()";
        }
    }

    public Converter() {
        this(true);
    }

    public static <A, B> Converter<A, B> from(Function<? super A, ? extends B> function, Function<? super B, ? extends A> function2) {
        return new FunctionBasedConverter(function, function2);
    }

    public static <T> Converter<T, T> identity() {
        return IdentityConverter.INSTANCE;
    }

    public final <C> Converter<A, C> andThen(Converter<B, C> converter) {
        return doAndThen(converter);
    }

    @Override // com.google.common.base.Function
    @CanIgnoreReturnValue
    @NullableDecl
    @Deprecated
    public final B apply(@NullableDecl A a3) {
        return convert(a3);
    }

    @CanIgnoreReturnValue
    @NullableDecl
    public final B convert(@NullableDecl A a3) {
        return correctedDoForward(a3);
    }

    @CanIgnoreReturnValue
    public Iterable<B> convertAll(final Iterable<? extends A> iterable) {
        Preconditions.checkNotNull(iterable, "fromIterable");
        return new Iterable<B>() { // from class: com.google.common.base.Converter.1
            @Override // java.lang.Iterable
            public Iterator<B> iterator() {
                return new Iterator<B>() { // from class: com.google.common.base.Converter.1.1
                    private final Iterator<? extends A> fromIterator;

                    {
                        this.fromIterator = iterable.iterator();
                    }

                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return this.fromIterator.hasNext();
                    }

                    @Override // java.util.Iterator
                    public B next() {
                        return (B) Converter.this.convert(this.fromIterator.next());
                    }

                    @Override // java.util.Iterator
                    public void remove() {
                        this.fromIterator.remove();
                    }
                };
            }
        };
    }

    @NullableDecl
    public A correctedDoBackward(@NullableDecl B b3) {
        if (!this.handleNullAutomatically) {
            return doBackward(b3);
        }
        if (b3 == null) {
            return null;
        }
        return (A) Preconditions.checkNotNull(doBackward(b3));
    }

    @NullableDecl
    public B correctedDoForward(@NullableDecl A a3) {
        if (!this.handleNullAutomatically) {
            return doForward(a3);
        }
        if (a3 == null) {
            return null;
        }
        return (B) Preconditions.checkNotNull(doForward(a3));
    }

    public <C> Converter<A, C> doAndThen(Converter<B, C> converter) {
        return new ConverterComposition(this, (Converter) Preconditions.checkNotNull(converter));
    }

    @ForOverride
    public abstract A doBackward(B b3);

    @ForOverride
    public abstract B doForward(A a3);

    @Override // com.google.common.base.Function
    public boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }

    @CanIgnoreReturnValue
    public Converter<B, A> reverse() {
        Converter<B, A> converter = this.reverse;
        if (converter != null) {
            return converter;
        }
        ReverseConverter reverseConverter = new ReverseConverter(this);
        this.reverse = reverseConverter;
        return reverseConverter;
    }

    public Converter(boolean z2) {
        this.handleNullAutomatically = z2;
    }
}

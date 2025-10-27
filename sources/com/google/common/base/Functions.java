package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes3.dex */
public final class Functions {

    public static class ConstantFunction<E> implements Function<Object, E>, Serializable {
        private static final long serialVersionUID = 0;

        @NullableDecl
        private final E value;

        public ConstantFunction(@NullableDecl E e2) {
            this.value = e2;
        }

        @Override // com.google.common.base.Function
        public E apply(@NullableDecl Object obj) {
            return this.value;
        }

        @Override // com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof ConstantFunction) {
                return Objects.equal(this.value, ((ConstantFunction) obj).value);
            }
            return false;
        }

        public int hashCode() {
            E e2 = this.value;
            if (e2 == null) {
                return 0;
            }
            return e2.hashCode();
        }

        public String toString() {
            return "Functions.constant(" + this.value + ")";
        }
    }

    public static class ForMapWithDefault<K, V> implements Function<K, V>, Serializable {
        private static final long serialVersionUID = 0;

        @NullableDecl
        final V defaultValue;
        final Map<K, ? extends V> map;

        public ForMapWithDefault(Map<K, ? extends V> map, @NullableDecl V v2) {
            this.map = (Map) Preconditions.checkNotNull(map);
            this.defaultValue = v2;
        }

        @Override // com.google.common.base.Function
        public V apply(@NullableDecl K k2) {
            V v2 = this.map.get(k2);
            return (v2 != null || this.map.containsKey(k2)) ? v2 : this.defaultValue;
        }

        @Override // com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof ForMapWithDefault)) {
                return false;
            }
            ForMapWithDefault forMapWithDefault = (ForMapWithDefault) obj;
            return this.map.equals(forMapWithDefault.map) && Objects.equal(this.defaultValue, forMapWithDefault.defaultValue);
        }

        public int hashCode() {
            return Objects.hashCode(this.map, this.defaultValue);
        }

        public String toString() {
            return "Functions.forMap(" + this.map + ", defaultValue=" + this.defaultValue + ")";
        }
    }

    public static class FunctionComposition<A, B, C> implements Function<A, C>, Serializable {
        private static final long serialVersionUID = 0;

        /* renamed from: f, reason: collision with root package name */
        private final Function<A, ? extends B> f7041f;

        /* renamed from: g, reason: collision with root package name */
        private final Function<B, C> f7042g;

        public FunctionComposition(Function<B, C> function, Function<A, ? extends B> function2) {
            this.f7042g = (Function) Preconditions.checkNotNull(function);
            this.f7041f = (Function) Preconditions.checkNotNull(function2);
        }

        @Override // com.google.common.base.Function
        public C apply(@NullableDecl A a3) {
            return (C) this.f7042g.apply(this.f7041f.apply(a3));
        }

        @Override // com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof FunctionComposition)) {
                return false;
            }
            FunctionComposition functionComposition = (FunctionComposition) obj;
            return this.f7041f.equals(functionComposition.f7041f) && this.f7042g.equals(functionComposition.f7042g);
        }

        public int hashCode() {
            return this.f7041f.hashCode() ^ this.f7042g.hashCode();
        }

        public String toString() {
            return this.f7042g + "(" + this.f7041f + ")";
        }
    }

    public static class FunctionForMapNoDefault<K, V> implements Function<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        final Map<K, V> map;

        public FunctionForMapNoDefault(Map<K, V> map) {
            this.map = (Map) Preconditions.checkNotNull(map);
        }

        @Override // com.google.common.base.Function
        public V apply(@NullableDecl K k2) {
            V v2 = this.map.get(k2);
            Preconditions.checkArgument(v2 != null || this.map.containsKey(k2), "Key '%s' not present in map", k2);
            return v2;
        }

        @Override // com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof FunctionForMapNoDefault) {
                return this.map.equals(((FunctionForMapNoDefault) obj).map);
            }
            return false;
        }

        public int hashCode() {
            return this.map.hashCode();
        }

        public String toString() {
            return "Functions.forMap(" + this.map + ")";
        }
    }

    public enum IdentityFunction implements Function<Object, Object> {
        INSTANCE;

        @Override // com.google.common.base.Function
        @NullableDecl
        public Object apply(@NullableDecl Object obj) {
            return obj;
        }

        @Override // java.lang.Enum
        public String toString() {
            return "Functions.identity()";
        }
    }

    public static class PredicateFunction<T> implements Function<T, Boolean>, Serializable {
        private static final long serialVersionUID = 0;
        private final Predicate<T> predicate;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.base.Function
        public /* bridge */ /* synthetic */ Boolean apply(@NullableDecl Object obj) {
            return apply((PredicateFunction<T>) obj);
        }

        @Override // com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof PredicateFunction) {
                return this.predicate.equals(((PredicateFunction) obj).predicate);
            }
            return false;
        }

        public int hashCode() {
            return this.predicate.hashCode();
        }

        public String toString() {
            return "Functions.forPredicate(" + this.predicate + ")";
        }

        private PredicateFunction(Predicate<T> predicate) {
            this.predicate = (Predicate) Preconditions.checkNotNull(predicate);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.common.base.Function
        public Boolean apply(@NullableDecl T t2) {
            return Boolean.valueOf(this.predicate.apply(t2));
        }
    }

    public static class SupplierFunction<T> implements Function<Object, T>, Serializable {
        private static final long serialVersionUID = 0;
        private final Supplier<T> supplier;

        @Override // com.google.common.base.Function
        public T apply(@NullableDecl Object obj) {
            return this.supplier.get();
        }

        @Override // com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof SupplierFunction) {
                return this.supplier.equals(((SupplierFunction) obj).supplier);
            }
            return false;
        }

        public int hashCode() {
            return this.supplier.hashCode();
        }

        public String toString() {
            return "Functions.forSupplier(" + this.supplier + ")";
        }

        private SupplierFunction(Supplier<T> supplier) {
            this.supplier = (Supplier) Preconditions.checkNotNull(supplier);
        }
    }

    public enum ToStringFunction implements Function<Object, String> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "Functions.toStringFunction()";
        }

        @Override // com.google.common.base.Function
        public String apply(Object obj) {
            Preconditions.checkNotNull(obj);
            return obj.toString();
        }
    }

    private Functions() {
    }

    public static <A, B, C> Function<A, C> compose(Function<B, C> function, Function<A, ? extends B> function2) {
        return new FunctionComposition(function, function2);
    }

    public static <E> Function<Object, E> constant(@NullableDecl E e2) {
        return new ConstantFunction(e2);
    }

    public static <K, V> Function<K, V> forMap(Map<K, V> map) {
        return new FunctionForMapNoDefault(map);
    }

    public static <T> Function<T, Boolean> forPredicate(Predicate<T> predicate) {
        return new PredicateFunction(predicate);
    }

    public static <T> Function<Object, T> forSupplier(Supplier<T> supplier) {
        return new SupplierFunction(supplier);
    }

    public static <E> Function<E, E> identity() {
        return IdentityFunction.INSTANCE;
    }

    public static Function<Object, String> toStringFunction() {
        return ToStringFunction.INSTANCE;
    }

    public static <K, V> Function<K, V> forMap(Map<K, ? extends V> map, @NullableDecl V v2) {
        return new ForMapWithDefault(map, v2);
    }
}

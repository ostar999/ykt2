package cn.hutool.core.lang;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.func.Func0;
import cn.hutool.core.lang.func.VoidFunc0;
import cn.hutool.core.util.StrUtil;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/* loaded from: classes.dex */
public class Opt<T> {
    private static final Opt<?> EMPTY = new Opt<>(null);
    private Exception exception;
    private final T value;

    private Opt(T t2) {
        this.value = t2;
    }

    public static <T> Opt<T> empty() {
        return (Opt<T>) EMPTY;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Opt lambda$peeks$0(Opt opt, Opt opt2) {
        return null;
    }

    public static <T> Opt<T> of(T t2) {
        Objects.requireNonNull(t2);
        return new Opt<>(t2);
    }

    public static <T> Opt<T> ofBlankAble(T t2) {
        return StrUtil.isBlankIfStr(t2) ? empty() : new Opt<>(t2);
    }

    public static <T, R extends Collection<T>> Opt<R> ofEmptyAble(R r2) {
        return CollUtil.isEmpty((Collection<?>) r2) ? empty() : new Opt<>(r2);
    }

    public static <T> Opt<T> ofNullable(T t2) {
        return t2 == null ? empty() : new Opt<>(t2);
    }

    public static <T> Opt<T> ofTry(Func0<T> func0) {
        try {
            return ofNullable(func0.call());
        } catch (Exception e2) {
            Opt<T> opt = new Opt<>(null);
            ((Opt) opt).exception = e2;
            return opt;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Opt) {
            return Objects.equals(this.value, ((Opt) obj).value);
        }
        return false;
    }

    public T exceptionOrElse(T t2) {
        return isFail() ? t2 : this.value;
    }

    public Opt<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        return isEmpty() ? this : predicate.test(this.value) ? this : empty();
    }

    public <U> Opt<U> flatMap(Function<? super T, ? extends Opt<? extends U>> function) {
        Objects.requireNonNull(function);
        if (isEmpty()) {
            return empty();
        }
        Opt<U> opt = (Opt) function.apply(this.value);
        Objects.requireNonNull(opt);
        return opt;
    }

    public <U> Opt<U> flattedMap(Function<? super T, ? extends Optional<? extends U>> function) {
        Objects.requireNonNull(function);
        return isEmpty() ? empty() : ofNullable(((Optional) function.apply(this.value)).orElse(null));
    }

    public T get() {
        return this.value;
    }

    public Exception getException() {
        return this.exception;
    }

    public int hashCode() {
        return Objects.hashCode(this.value);
    }

    public Opt<T> ifPresent(Consumer<? super T> consumer) {
        if (isPresent()) {
            consumer.accept(this.value);
        }
        return this;
    }

    public Opt<T> ifPresentOrElse(Consumer<? super T> consumer, VoidFunc0 voidFunc0) {
        if (isPresent()) {
            consumer.accept(this.value);
        } else {
            voidFunc0.callWithRuntimeException();
        }
        return this;
    }

    public boolean isEmpty() {
        return this.value == null;
    }

    public boolean isFail() {
        return this.exception != null;
    }

    public boolean isPresent() {
        return this.value != null;
    }

    public <U> Opt<U> map(Function<? super T, ? extends U> function) {
        Objects.requireNonNull(function);
        return isEmpty() ? empty() : ofNullable(function.apply(this.value));
    }

    public <U> Opt<U> mapOrElse(Function<? super T, ? extends U> function, VoidFunc0 voidFunc0) {
        if (isPresent()) {
            return ofNullable(function.apply(this.value));
        }
        voidFunc0.callWithRuntimeException();
        return empty();
    }

    public Opt<T> or(Supplier<? extends Opt<? extends T>> supplier) {
        Objects.requireNonNull(supplier);
        if (isPresent()) {
            return this;
        }
        Opt<T> opt = (Opt) supplier.get();
        Objects.requireNonNull(opt);
        return opt;
    }

    public T orElse(T t2) {
        return isPresent() ? this.value : t2;
    }

    public T orElseGet(Supplier<? extends T> supplier) {
        return isPresent() ? this.value : (T) supplier.get();
    }

    public T orElseThrow() {
        return orElseThrow(new Function() { // from class: cn.hutool.core.lang.e0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new NoSuchElementException((String) obj);
            }
        }, "No value present");
    }

    public Opt<T> peek(Consumer<T> consumer) throws NullPointerException {
        Objects.requireNonNull(consumer);
        if (isEmpty()) {
            return empty();
        }
        consumer.accept(this.value);
        return this;
    }

    @SafeVarargs
    public final Opt<T> peeks(Consumer<T>... consumerArr) throws NullPointerException {
        return (Opt) Stream.of((Object[]) consumerArr).reduce(this, new BiFunction() { // from class: cn.hutool.core.lang.c0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((Opt) obj).peek((Consumer) obj2);
            }
        }, new BinaryOperator() { // from class: cn.hutool.core.lang.d0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Opt.lambda$peeks$0((Opt) obj, (Opt) obj2);
            }
        });
    }

    public Stream<T> stream() {
        return isEmpty() ? Stream.empty() : Stream.of(this.value);
    }

    public Optional<T> toOptional() {
        return Optional.ofNullable(this.value);
    }

    public String toString() {
        return StrUtil.toStringOrNull(this.value);
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> supplier) throws Throwable {
        if (isPresent()) {
            return this.value;
        }
        throw ((Throwable) supplier.get());
    }

    public <X extends Throwable> T orElseThrow(Function<String, ? extends X> function, String str) throws Throwable {
        if (isPresent()) {
            return this.value;
        }
        throw ((Throwable) function.apply(str));
    }
}

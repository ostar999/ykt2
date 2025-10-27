package io.reactivex.rxjava3.internal.functions;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.exceptions.OnErrorNotImplementedException;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.BooleanSupplier;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Function3;
import io.reactivex.rxjava3.functions.Function4;
import io.reactivex.rxjava3.functions.Function5;
import io.reactivex.rxjava3.functions.Function6;
import io.reactivex.rxjava3.functions.Function7;
import io.reactivex.rxjava3.functions.Function8;
import io.reactivex.rxjava3.functions.Function9;
import io.reactivex.rxjava3.functions.LongConsumer;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Timed;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class Functions {
    static final Function<Object, Object> IDENTITY = new Identity();
    public static final Runnable EMPTY_RUNNABLE = new EmptyRunnable();
    public static final Action EMPTY_ACTION = new EmptyAction();
    static final Consumer<Object> EMPTY_CONSUMER = new EmptyConsumer();
    public static final Consumer<Throwable> ERROR_CONSUMER = new ErrorConsumer();
    public static final Consumer<Throwable> ON_ERROR_MISSING = new OnErrorMissingConsumer();
    public static final LongConsumer EMPTY_LONG_CONSUMER = new EmptyLongConsumer();
    static final Predicate<Object> ALWAYS_TRUE = new TruePredicate();
    static final Predicate<Object> ALWAYS_FALSE = new FalsePredicate();
    static final Supplier<Object> NULL_SUPPLIER = new NullProvider();
    public static final Consumer<Subscription> REQUEST_MAX = new MaxRequestSubscription();

    public static final class ActionConsumer<T> implements Consumer<T> {
        final Action action;

        public ActionConsumer(Action action) {
            this.action = action;
        }

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(T t2) throws Throwable {
            this.action.run();
        }
    }

    public static final class Array2Func<T1, T2, R> implements Function<Object[], R> {

        /* renamed from: f, reason: collision with root package name */
        final BiFunction<? super T1, ? super T2, ? extends R> f27328f;

        public Array2Func(BiFunction<? super T1, ? super T2, ? extends R> f2) {
            this.f27328f = f2;
        }

        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(Object[] a3) throws Throwable {
            if (a3.length == 2) {
                return this.f27328f.apply(a3[0], a3[1]);
            }
            throw new IllegalArgumentException("Array of size 2 expected but got " + a3.length);
        }
    }

    public static final class Array3Func<T1, T2, T3, R> implements Function<Object[], R> {

        /* renamed from: f, reason: collision with root package name */
        final Function3<T1, T2, T3, R> f27329f;

        public Array3Func(Function3<T1, T2, T3, R> f2) {
            this.f27329f = f2;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(Object[] objArr) throws Throwable {
            if (objArr.length == 3) {
                return (R) this.f27329f.apply(objArr[0], objArr[1], objArr[2]);
            }
            throw new IllegalArgumentException("Array of size 3 expected but got " + objArr.length);
        }
    }

    public static final class Array4Func<T1, T2, T3, T4, R> implements Function<Object[], R> {

        /* renamed from: f, reason: collision with root package name */
        final Function4<T1, T2, T3, T4, R> f27330f;

        public Array4Func(Function4<T1, T2, T3, T4, R> f2) {
            this.f27330f = f2;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(Object[] objArr) throws Throwable {
            if (objArr.length == 4) {
                return (R) this.f27330f.apply(objArr[0], objArr[1], objArr[2], objArr[3]);
            }
            throw new IllegalArgumentException("Array of size 4 expected but got " + objArr.length);
        }
    }

    public static final class Array5Func<T1, T2, T3, T4, T5, R> implements Function<Object[], R> {

        /* renamed from: f, reason: collision with root package name */
        private final Function5<T1, T2, T3, T4, T5, R> f27331f;

        public Array5Func(Function5<T1, T2, T3, T4, T5, R> f2) {
            this.f27331f = f2;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(Object[] objArr) throws Throwable {
            if (objArr.length == 5) {
                return (R) this.f27331f.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4]);
            }
            throw new IllegalArgumentException("Array of size 5 expected but got " + objArr.length);
        }
    }

    public static final class Array6Func<T1, T2, T3, T4, T5, T6, R> implements Function<Object[], R> {

        /* renamed from: f, reason: collision with root package name */
        final Function6<T1, T2, T3, T4, T5, T6, R> f27332f;

        public Array6Func(Function6<T1, T2, T3, T4, T5, T6, R> f2) {
            this.f27332f = f2;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(Object[] objArr) throws Throwable {
            if (objArr.length == 6) {
                return (R) this.f27332f.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            }
            throw new IllegalArgumentException("Array of size 6 expected but got " + objArr.length);
        }
    }

    public static final class Array7Func<T1, T2, T3, T4, T5, T6, T7, R> implements Function<Object[], R> {

        /* renamed from: f, reason: collision with root package name */
        final Function7<T1, T2, T3, T4, T5, T6, T7, R> f27333f;

        public Array7Func(Function7<T1, T2, T3, T4, T5, T6, T7, R> f2) {
            this.f27333f = f2;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(Object[] objArr) throws Throwable {
            if (objArr.length == 7) {
                return (R) this.f27333f.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6]);
            }
            throw new IllegalArgumentException("Array of size 7 expected but got " + objArr.length);
        }
    }

    public static final class Array8Func<T1, T2, T3, T4, T5, T6, T7, T8, R> implements Function<Object[], R> {

        /* renamed from: f, reason: collision with root package name */
        final Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> f27334f;

        public Array8Func(Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> f2) {
            this.f27334f = f2;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(Object[] objArr) throws Throwable {
            if (objArr.length == 8) {
                return (R) this.f27334f.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6], objArr[7]);
            }
            throw new IllegalArgumentException("Array of size 8 expected but got " + objArr.length);
        }
    }

    public static final class Array9Func<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> implements Function<Object[], R> {

        /* renamed from: f, reason: collision with root package name */
        final Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> f27335f;

        public Array9Func(Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> f2) {
            this.f27335f = f2;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(Object[] objArr) throws Throwable {
            if (objArr.length == 9) {
                return (R) this.f27335f.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6], objArr[7], objArr[8]);
            }
            throw new IllegalArgumentException("Array of size 9 expected but got " + objArr.length);
        }
    }

    public static final class ArrayListCapacityCallable<T> implements Supplier<List<T>> {
        final int capacity;

        public ArrayListCapacityCallable(int capacity) {
            this.capacity = capacity;
        }

        @Override // io.reactivex.rxjava3.functions.Supplier
        public List<T> get() {
            return new ArrayList(this.capacity);
        }
    }

    public static final class BooleanSupplierPredicateReverse<T> implements Predicate<T> {
        final BooleanSupplier supplier;

        public BooleanSupplierPredicateReverse(BooleanSupplier supplier) {
            this.supplier = supplier;
        }

        @Override // io.reactivex.rxjava3.functions.Predicate
        public boolean test(T t2) throws Throwable {
            return !this.supplier.getAsBoolean();
        }
    }

    public static class BoundedConsumer implements Consumer<Subscription> {
        final int bufferSize;

        public BoundedConsumer(int bufferSize) {
            this.bufferSize = bufferSize;
        }

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Subscription s2) {
            s2.request(this.bufferSize);
        }
    }

    public static final class CastToClass<T, U> implements Function<T, U> {
        final Class<U> clazz;

        public CastToClass(Class<U> clazz) {
            this.clazz = clazz;
        }

        @Override // io.reactivex.rxjava3.functions.Function
        public U apply(T t2) {
            return this.clazz.cast(t2);
        }
    }

    public static final class ClassFilter<T, U> implements Predicate<T> {
        final Class<U> clazz;

        public ClassFilter(Class<U> clazz) {
            this.clazz = clazz;
        }

        @Override // io.reactivex.rxjava3.functions.Predicate
        public boolean test(T t2) {
            return this.clazz.isInstance(t2);
        }
    }

    public static final class EmptyAction implements Action {
        @Override // io.reactivex.rxjava3.functions.Action
        public void run() {
        }

        public String toString() {
            return "EmptyAction";
        }
    }

    public static final class EmptyConsumer implements Consumer<Object> {
        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Object v2) {
        }

        public String toString() {
            return "EmptyConsumer";
        }
    }

    public static final class EmptyLongConsumer implements LongConsumer {
        @Override // io.reactivex.rxjava3.functions.LongConsumer
        public void accept(long v2) {
        }
    }

    public static final class EmptyRunnable implements Runnable {
        @Override // java.lang.Runnable
        public void run() {
        }

        public String toString() {
            return "EmptyRunnable";
        }
    }

    public static final class EqualsPredicate<T> implements Predicate<T> {
        final T value;

        public EqualsPredicate(T value) {
            this.value = value;
        }

        @Override // io.reactivex.rxjava3.functions.Predicate
        public boolean test(T t2) {
            return Objects.equals(t2, this.value);
        }
    }

    public static final class ErrorConsumer implements Consumer<Throwable> {
        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Throwable error) {
            RxJavaPlugins.onError(error);
        }
    }

    public static final class FalsePredicate implements Predicate<Object> {
        @Override // io.reactivex.rxjava3.functions.Predicate
        public boolean test(Object o2) {
            return false;
        }
    }

    public static final class FutureAction implements Action {
        final Future<?> future;

        public FutureAction(Future<?> future) {
            this.future = future;
        }

        @Override // io.reactivex.rxjava3.functions.Action
        public void run() throws Exception {
            this.future.get();
        }
    }

    public enum HashSetSupplier implements Supplier<Set<Object>> {
        INSTANCE;

        @Override // io.reactivex.rxjava3.functions.Supplier
        public Set<Object> get() {
            return new HashSet();
        }
    }

    public static final class Identity implements Function<Object, Object> {
        @Override // io.reactivex.rxjava3.functions.Function
        public Object apply(Object v2) {
            return v2;
        }

        public String toString() {
            return "IdentityFunction";
        }
    }

    public static final class JustValue<T, U> implements Callable<U>, Supplier<U>, Function<T, U> {
        final U value;

        public JustValue(U value) {
            this.value = value;
        }

        @Override // io.reactivex.rxjava3.functions.Function
        public U apply(T t2) {
            return this.value;
        }

        @Override // java.util.concurrent.Callable
        public U call() {
            return this.value;
        }

        @Override // io.reactivex.rxjava3.functions.Supplier
        public U get() {
            return this.value;
        }
    }

    public static final class ListSorter<T> implements Function<List<T>, List<T>> {
        final Comparator<? super T> comparator;

        public ListSorter(Comparator<? super T> comparator) {
            this.comparator = comparator;
        }

        @Override // io.reactivex.rxjava3.functions.Function
        public List<T> apply(List<T> v2) {
            Collections.sort(v2, this.comparator);
            return v2;
        }
    }

    public static final class MaxRequestSubscription implements Consumer<Subscription> {
        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Subscription t2) {
            t2.request(Long.MAX_VALUE);
        }
    }

    public enum NaturalComparator implements Comparator<Object> {
        INSTANCE;

        @Override // java.util.Comparator
        public int compare(Object o12, Object o2) {
            return ((Comparable) o12).compareTo(o2);
        }
    }

    public static final class NotificationOnComplete<T> implements Action {
        final Consumer<? super Notification<T>> onNotification;

        public NotificationOnComplete(Consumer<? super Notification<T>> onNotification) {
            this.onNotification = onNotification;
        }

        @Override // io.reactivex.rxjava3.functions.Action
        public void run() throws Throwable {
            this.onNotification.accept(Notification.createOnComplete());
        }
    }

    public static final class NotificationOnError<T> implements Consumer<Throwable> {
        final Consumer<? super Notification<T>> onNotification;

        public NotificationOnError(Consumer<? super Notification<T>> onNotification) {
            this.onNotification = onNotification;
        }

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Throwable v2) throws Throwable {
            this.onNotification.accept(Notification.createOnError(v2));
        }
    }

    public static final class NotificationOnNext<T> implements Consumer<T> {
        final Consumer<? super Notification<T>> onNotification;

        public NotificationOnNext(Consumer<? super Notification<T>> onNotification) {
            this.onNotification = onNotification;
        }

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(T v2) throws Throwable {
            this.onNotification.accept(Notification.createOnNext(v2));
        }
    }

    public static final class NullProvider implements Supplier<Object> {
        @Override // io.reactivex.rxjava3.functions.Supplier
        public Object get() {
            return null;
        }
    }

    public static final class OnErrorMissingConsumer implements Consumer<Throwable> {
        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Throwable error) {
            RxJavaPlugins.onError(new OnErrorNotImplementedException(error));
        }
    }

    public static final class TimestampFunction<T> implements Function<T, Timed<T>> {
        final Scheduler scheduler;
        final TimeUnit unit;

        public TimestampFunction(TimeUnit unit, Scheduler scheduler) {
            this.unit = unit;
            this.scheduler = scheduler;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.functions.Function
        public /* bridge */ /* synthetic */ Object apply(Object t2) throws Throwable {
            return apply((TimestampFunction<T>) t2);
        }

        @Override // io.reactivex.rxjava3.functions.Function
        public Timed<T> apply(T t2) {
            return new Timed<>(t2, this.scheduler.now(this.unit), this.unit);
        }
    }

    public static final class ToMapKeySelector<K, T> implements BiConsumer<Map<K, T>, T> {
        private final Function<? super T, ? extends K> keySelector;

        public ToMapKeySelector(Function<? super T, ? extends K> keySelector) {
            this.keySelector = keySelector;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.functions.BiConsumer
        public /* bridge */ /* synthetic */ void accept(Object m2, Object t2) throws Throwable {
            accept((Map<K, Map<K, T>>) m2, (Map<K, T>) t2);
        }

        public void accept(Map<K, T> m2, T t2) throws Throwable {
            m2.put(this.keySelector.apply(t2), t2);
        }
    }

    public static final class ToMapKeyValueSelector<K, V, T> implements BiConsumer<Map<K, V>, T> {
        private final Function<? super T, ? extends K> keySelector;
        private final Function<? super T, ? extends V> valueSelector;

        public ToMapKeyValueSelector(Function<? super T, ? extends V> valueSelector, Function<? super T, ? extends K> keySelector) {
            this.valueSelector = valueSelector;
            this.keySelector = keySelector;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.functions.BiConsumer
        public /* bridge */ /* synthetic */ void accept(Object m2, Object t2) throws Throwable {
            accept((Map) m2, (Map<K, V>) t2);
        }

        public void accept(Map<K, V> m2, T t2) throws Throwable {
            m2.put(this.keySelector.apply(t2), this.valueSelector.apply(t2));
        }
    }

    public static final class ToMultimapKeyValueSelector<K, V, T> implements BiConsumer<Map<K, Collection<V>>, T> {
        private final Function<? super K, ? extends Collection<? super V>> collectionFactory;
        private final Function<? super T, ? extends K> keySelector;
        private final Function<? super T, ? extends V> valueSelector;

        public ToMultimapKeyValueSelector(Function<? super K, ? extends Collection<? super V>> collectionFactory, Function<? super T, ? extends V> valueSelector, Function<? super T, ? extends K> keySelector) {
            this.collectionFactory = collectionFactory;
            this.valueSelector = valueSelector;
            this.keySelector = keySelector;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.functions.BiConsumer
        public /* bridge */ /* synthetic */ void accept(Object m2, Object t2) throws Throwable {
            accept((Map) m2, (Map<K, Collection<V>>) t2);
        }

        public void accept(Map<K, Collection<V>> m2, T t2) throws Throwable {
            K kApply = this.keySelector.apply(t2);
            Collection<? super V> collectionApply = (Collection) m2.get(kApply);
            if (collectionApply == null) {
                collectionApply = this.collectionFactory.apply(kApply);
                m2.put(kApply, collectionApply);
            }
            collectionApply.add(this.valueSelector.apply(t2));
        }
    }

    public static final class TruePredicate implements Predicate<Object> {
        @Override // io.reactivex.rxjava3.functions.Predicate
        public boolean test(Object o2) {
            return true;
        }
    }

    private Functions() {
        throw new IllegalStateException("No instances!");
    }

    public static <T> Consumer<T> actionConsumer(Action action) {
        return new ActionConsumer(action);
    }

    @NonNull
    public static <T> Predicate<T> alwaysFalse() {
        return (Predicate<T>) ALWAYS_FALSE;
    }

    @NonNull
    public static <T> Predicate<T> alwaysTrue() {
        return (Predicate<T>) ALWAYS_TRUE;
    }

    public static <T> Consumer<T> boundedConsumer(int bufferSize) {
        return new BoundedConsumer(bufferSize);
    }

    @NonNull
    public static <T, U> Function<T, U> castFunction(@NonNull Class<U> target) {
        return new CastToClass(target);
    }

    public static <T> Supplier<List<T>> createArrayList(int capacity) {
        return new ArrayListCapacityCallable(capacity);
    }

    public static <T> Supplier<Set<T>> createHashSet() {
        return HashSetSupplier.INSTANCE;
    }

    public static <T> Consumer<T> emptyConsumer() {
        return (Consumer<T>) EMPTY_CONSUMER;
    }

    public static <T> Predicate<T> equalsWith(T value) {
        return new EqualsPredicate(value);
    }

    @NonNull
    public static Action futureAction(@NonNull Future<?> future) {
        return new FutureAction(future);
    }

    @NonNull
    public static <T> Function<T, T> identity() {
        return (Function<T, T>) IDENTITY;
    }

    public static <T, U> Predicate<T> isInstanceOf(Class<U> clazz) {
        return new ClassFilter(clazz);
    }

    @NonNull
    public static <T> Callable<T> justCallable(@NonNull T value) {
        return new JustValue(value);
    }

    @NonNull
    public static <T, U> Function<T, U> justFunction(@NonNull U value) {
        return new JustValue(value);
    }

    @NonNull
    public static <T> Supplier<T> justSupplier(@NonNull T value) {
        return new JustValue(value);
    }

    public static <T> Function<List<T>, List<T>> listSorter(final Comparator<? super T> comparator) {
        return new ListSorter(comparator);
    }

    public static <T> Comparator<T> naturalComparator() {
        return NaturalComparator.INSTANCE;
    }

    public static <T> Action notificationOnComplete(Consumer<? super Notification<T>> onNotification) {
        return new NotificationOnComplete(onNotification);
    }

    public static <T> Consumer<Throwable> notificationOnError(Consumer<? super Notification<T>> onNotification) {
        return new NotificationOnError(onNotification);
    }

    public static <T> Consumer<T> notificationOnNext(Consumer<? super Notification<T>> onNotification) {
        return new NotificationOnNext(onNotification);
    }

    @NonNull
    public static <T> Supplier<T> nullSupplier() {
        return (Supplier<T>) NULL_SUPPLIER;
    }

    public static <T> Predicate<T> predicateReverseFor(BooleanSupplier supplier) {
        return new BooleanSupplierPredicateReverse(supplier);
    }

    public static <T> Function<T, Timed<T>> timestampWith(TimeUnit unit, Scheduler scheduler) {
        return new TimestampFunction(unit, scheduler);
    }

    @NonNull
    public static <T1, T2, R> Function<Object[], R> toFunction(@NonNull BiFunction<? super T1, ? super T2, ? extends R> f2) {
        return new Array2Func(f2);
    }

    public static <T, K> BiConsumer<Map<K, T>, T> toMapKeySelector(final Function<? super T, ? extends K> keySelector) {
        return new ToMapKeySelector(keySelector);
    }

    public static <T, K, V> BiConsumer<Map<K, V>, T> toMapKeyValueSelector(final Function<? super T, ? extends K> keySelector, final Function<? super T, ? extends V> valueSelector) {
        return new ToMapKeyValueSelector(valueSelector, keySelector);
    }

    public static <T, K, V> BiConsumer<Map<K, Collection<V>>, T> toMultimapKeyValueSelector(final Function<? super T, ? extends K> keySelector, final Function<? super T, ? extends V> valueSelector, final Function<? super K, ? extends Collection<? super V>> collectionFactory) {
        return new ToMultimapKeyValueSelector(collectionFactory, valueSelector, keySelector);
    }

    @NonNull
    public static <T1, T2, T3, R> Function<Object[], R> toFunction(@NonNull Function3<T1, T2, T3, R> f2) {
        return new Array3Func(f2);
    }

    @NonNull
    public static <T1, T2, T3, T4, R> Function<Object[], R> toFunction(@NonNull Function4<T1, T2, T3, T4, R> f2) {
        return new Array4Func(f2);
    }

    @NonNull
    public static <T1, T2, T3, T4, T5, R> Function<Object[], R> toFunction(@NonNull Function5<T1, T2, T3, T4, T5, R> f2) {
        return new Array5Func(f2);
    }

    @NonNull
    public static <T1, T2, T3, T4, T5, T6, R> Function<Object[], R> toFunction(@NonNull Function6<T1, T2, T3, T4, T5, T6, R> f2) {
        return new Array6Func(f2);
    }

    @NonNull
    public static <T1, T2, T3, T4, T5, T6, T7, R> Function<Object[], R> toFunction(@NonNull Function7<T1, T2, T3, T4, T5, T6, T7, R> f2) {
        return new Array7Func(f2);
    }

    @NonNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Function<Object[], R> toFunction(@NonNull Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> f2) {
        return new Array8Func(f2);
    }

    @NonNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Function<Object[], R> toFunction(@NonNull Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> f2) {
        return new Array9Func(f2);
    }
}

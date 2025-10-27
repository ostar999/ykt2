package cn.hutool.core.collection;

import cn.hutool.core.lang.Assert;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class TransCollection<F, T> extends AbstractCollection<T> {
    private final Collection<F> fromCollection;
    private final Function<? super F, ? extends T> function;

    public TransCollection(Collection<F> collection, Function<? super F, ? extends T> function) {
        this.fromCollection = (Collection) Assert.notNull(collection);
        this.function = (Function) Assert.notNull(function);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$forEach$0(Consumer consumer, Object obj) {
        consumer.accept(this.function.apply(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$removeIf$1(Predicate predicate, Object obj) {
        return predicate.test(this.function.apply(obj));
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public void clear() {
        this.fromCollection.clear();
    }

    @Override // java.lang.Iterable
    public void forEach(final Consumer<? super T> consumer) throws IllegalArgumentException {
        Assert.notNull(consumer);
        this.fromCollection.forEach(new Consumer() { // from class: cn.hutool.core.collection.x0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f2432c.lambda$forEach$0(consumer, obj);
            }
        });
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean isEmpty() {
        return this.fromCollection.isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<T> iterator() {
        return IterUtil.trans(this.fromCollection.iterator(), this.function);
    }

    @Override // java.util.Collection
    public boolean removeIf(final Predicate<? super T> predicate) throws IllegalArgumentException {
        Assert.notNull(predicate);
        return this.fromCollection.removeIf(new Predicate() { // from class: cn.hutool.core.collection.w0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f2430a.lambda$removeIf$1(predicate, obj);
            }
        });
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public int size() {
        return this.fromCollection.size();
    }

    @Override // java.util.Collection, java.lang.Iterable
    public Spliterator<T> spliterator() {
        return SpliteratorUtil.trans(this.fromCollection.spliterator(), this.function);
    }
}

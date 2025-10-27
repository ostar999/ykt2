package cn.hutool.core.collection;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes.dex */
public class TransSpliterator<F, T> implements Spliterator<T> {
    private final Spliterator<F> fromSpliterator;
    private final Function<? super F, ? extends T> function;

    public TransSpliterator(Spliterator<F> spliterator, Function<? super F, ? extends T> function) {
        this.fromSpliterator = spliterator;
        this.function = function;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$forEachRemaining$1(Consumer consumer, Object obj) {
        consumer.accept(this.function.apply(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$tryAdvance$0(Consumer consumer, Object obj) {
        consumer.accept(this.function.apply(obj));
    }

    @Override // java.util.Spliterator
    public int characteristics() {
        return this.fromSpliterator.characteristics() & (-262);
    }

    @Override // java.util.Spliterator
    public long estimateSize() {
        return this.fromSpliterator.estimateSize();
    }

    @Override // java.util.Spliterator
    public void forEachRemaining(final Consumer<? super T> consumer) {
        this.fromSpliterator.forEachRemaining(new Consumer() { // from class: cn.hutool.core.collection.d1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f2414c.lambda$forEachRemaining$1(consumer, obj);
            }
        });
    }

    @Override // java.util.Spliterator
    public boolean tryAdvance(final Consumer<? super T> consumer) {
        return this.fromSpliterator.tryAdvance(new Consumer() { // from class: cn.hutool.core.collection.e1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f2416c.lambda$tryAdvance$0(consumer, obj);
            }
        });
    }

    @Override // java.util.Spliterator
    public Spliterator<T> trySplit() {
        Spliterator spliteratorTrySplit = this.fromSpliterator.trySplit();
        if (spliteratorTrySplit != null) {
            return new TransSpliterator(spliteratorTrySplit, this.function);
        }
        return null;
    }
}

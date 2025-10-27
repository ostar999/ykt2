package cn.hutool.core.lang.loader;

import cn.hutool.core.lang.Assert;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class LazyFunLoader<T> extends LazyLoader<T> {
    private static final long serialVersionUID = 1;
    private Supplier<T> supplier;

    public LazyFunLoader(Supplier<T> supplier) throws IllegalArgumentException {
        Assert.notNull(supplier);
        this.supplier = supplier;
    }

    public static <T> LazyFunLoader<T> on(Supplier<T> supplier) throws IllegalArgumentException {
        Assert.notNull(supplier, "supplier must be not null!", new Object[0]);
        return new LazyFunLoader<>(supplier);
    }

    public void ifInitialized(Consumer<T> consumer) throws IllegalArgumentException {
        Assert.notNull(consumer);
        if (isInitialize()) {
            consumer.accept(get());
        }
    }

    @Override // cn.hutool.core.lang.loader.LazyLoader
    public T init() {
        T t2 = (T) this.supplier.get();
        this.supplier = null;
        return t2;
    }

    public boolean isInitialize() {
        return this.supplier == null;
    }
}

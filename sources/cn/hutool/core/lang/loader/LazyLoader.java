package cn.hutool.core.lang.loader;

import java.io.Serializable;

/* loaded from: classes.dex */
public abstract class LazyLoader<T> implements Loader<T>, Serializable {
    private static final long serialVersionUID = 1;
    private volatile T object;

    @Override // cn.hutool.core.lang.loader.Loader
    public T get() {
        T tInit = this.object;
        if (tInit == null) {
            synchronized (this) {
                tInit = this.object;
                if (tInit == null) {
                    tInit = init();
                    this.object = tInit;
                }
            }
        }
        return tInit;
    }

    public abstract T init();
}

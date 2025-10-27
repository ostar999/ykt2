package cn.hutool.core.lang.loader;

import androidx.camera.view.j;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public abstract class AtomicLoader<T> implements Loader<T>, Serializable {
    private static final long serialVersionUID = 1;
    private final AtomicReference<T> reference = new AtomicReference<>();

    @Override // cn.hutool.core.lang.loader.Loader
    public T get() {
        T t2 = this.reference.get();
        if (t2 != null) {
            return t2;
        }
        T tInit = init();
        return !j.a(this.reference, null, tInit) ? this.reference.get() : tInit;
    }

    public abstract T init();
}

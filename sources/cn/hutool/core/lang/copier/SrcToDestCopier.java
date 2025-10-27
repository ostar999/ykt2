package cn.hutool.core.lang.copier;

import cn.hutool.core.lang.Filter;
import cn.hutool.core.lang.copier.SrcToDestCopier;
import java.io.Serializable;

/* loaded from: classes.dex */
public abstract class SrcToDestCopier<T, C extends SrcToDestCopier<T, C>> implements Copier<T>, Serializable {
    private static final long serialVersionUID = 1;
    protected Filter<T> copyFilter;
    protected T dest;
    protected T src;

    public Filter<T> getCopyFilter() {
        return this.copyFilter;
    }

    public T getDest() {
        return this.dest;
    }

    public T getSrc() {
        return this.src;
    }

    public C setCopyFilter(Filter<T> filter) {
        this.copyFilter = filter;
        return this;
    }

    public C setDest(T t2) {
        this.dest = t2;
        return this;
    }

    public C setSrc(T t2) {
        this.src = t2;
        return this;
    }
}

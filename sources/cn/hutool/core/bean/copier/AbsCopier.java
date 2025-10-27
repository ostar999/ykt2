package cn.hutool.core.bean.copier;

import cn.hutool.core.bean.p;
import cn.hutool.core.lang.copier.Copier;
import cn.hutool.core.util.ObjectUtil;

/* loaded from: classes.dex */
public abstract class AbsCopier<S, T> implements Copier<T> {
    protected final CopyOptions copyOptions;
    protected final S source;
    protected final T target;

    public AbsCopier(S s2, T t2, CopyOptions copyOptions) {
        this.source = s2;
        this.target = t2;
        this.copyOptions = (CopyOptions) ObjectUtil.defaultIfNull(copyOptions, new p());
    }
}

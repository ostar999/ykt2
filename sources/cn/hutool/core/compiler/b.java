package cn.hutool.core.compiler;

import cn.hutool.core.util.ClassLoaderUtil;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public final /* synthetic */ class b implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return ClassLoaderUtil.getClassLoader();
    }
}

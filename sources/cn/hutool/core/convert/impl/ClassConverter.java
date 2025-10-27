package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.util.ClassLoaderUtil;

/* loaded from: classes.dex */
public class ClassConverter extends AbstractConverter<Class<?>> {
    private static final long serialVersionUID = 1;
    private final boolean isInitialized;

    public ClassConverter() {
        this(true);
    }

    public ClassConverter(boolean z2) {
        this.isInitialized = z2;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // cn.hutool.core.convert.AbstractConverter
    public Class<?> convertInternal(Object obj) {
        return ClassLoaderUtil.loadClass(convertToStr(obj), this.isInitialized);
    }
}

package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.convert.Convert;
import java.util.concurrent.atomic.AtomicLongArray;

/* loaded from: classes.dex */
public class AtomicLongArrayConverter extends AbstractConverter<AtomicLongArray> {
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.convert.AbstractConverter
    public AtomicLongArray convertInternal(Object obj) {
        return new AtomicLongArray((long[]) Convert.convert(long[].class, obj));
    }
}

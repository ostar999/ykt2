package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.convert.Convert;
import java.util.concurrent.atomic.AtomicIntegerArray;

/* loaded from: classes.dex */
public class AtomicIntegerArrayConverter extends AbstractConverter<AtomicIntegerArray> {
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.convert.AbstractConverter
    public AtomicIntegerArray convertInternal(Object obj) {
        return new AtomicIntegerArray((int[]) Convert.convert(int[].class, obj));
    }
}

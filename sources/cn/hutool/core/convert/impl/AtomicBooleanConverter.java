package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.util.BooleanUtil;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class AtomicBooleanConverter extends AbstractConverter<AtomicBoolean> {
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.convert.AbstractConverter
    public AtomicBoolean convertInternal(Object obj) {
        return obj instanceof Boolean ? new AtomicBoolean(((Boolean) obj).booleanValue()) : new AtomicBoolean(BooleanUtil.toBoolean(convertToStr(obj)));
    }
}

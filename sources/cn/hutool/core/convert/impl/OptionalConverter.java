package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import java.util.Optional;

/* loaded from: classes.dex */
public class OptionalConverter extends AbstractConverter<Optional<?>> {
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.convert.AbstractConverter
    public Optional<?> convertInternal(Object obj) {
        return Optional.ofNullable(obj);
    }
}

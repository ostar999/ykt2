package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import java.util.UUID;

/* loaded from: classes.dex */
public class UUIDConverter extends AbstractConverter<UUID> {
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.convert.AbstractConverter
    public UUID convertInternal(Object obj) {
        return UUID.fromString(convertToStr(obj));
    }
}

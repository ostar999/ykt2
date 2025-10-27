package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.util.BooleanUtil;

/* loaded from: classes.dex */
public class BooleanConverter extends AbstractConverter<Boolean> {
    private static final long serialVersionUID = 1;

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // cn.hutool.core.convert.AbstractConverter
    public Boolean convertInternal(Object obj) {
        if (obj instanceof Number) {
            return Boolean.valueOf(0.0d != ((Number) obj).doubleValue());
        }
        return Boolean.valueOf(BooleanUtil.toBoolean(convertToStr(obj)));
    }
}

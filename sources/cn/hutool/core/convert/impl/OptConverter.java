package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.lang.Opt;

/* loaded from: classes.dex */
public class OptConverter extends AbstractConverter<Opt<?>> {
    private static final long serialVersionUID = 1;

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // cn.hutool.core.convert.AbstractConverter
    public Opt<?> convertInternal(Object obj) {
        return Opt.ofNullable(obj);
    }
}

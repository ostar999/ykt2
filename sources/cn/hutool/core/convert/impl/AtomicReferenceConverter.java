package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.util.TypeUtil;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public class AtomicReferenceConverter extends AbstractConverter<AtomicReference> {
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.convert.AbstractConverter
    public AtomicReference convertInternal(Object obj) {
        Type typeArgument = TypeUtil.getTypeArgument(AtomicReference.class);
        Object objConvert = !TypeUtil.isUnknown(typeArgument) ? ConverterRegistry.getInstance().convert(typeArgument, obj) : null;
        if (objConvert != null) {
            obj = objConvert;
        }
        return new AtomicReference(obj);
    }
}

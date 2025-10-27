package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.TypeUtil;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public class ReferenceConverter extends AbstractConverter<Reference> {
    private static final long serialVersionUID = 1;
    private final Class<? extends Reference> targetType;

    public ReferenceConverter(Class<? extends Reference> cls) {
        this.targetType = cls;
    }

    @Override // cn.hutool.core.convert.AbstractConverter
    public Reference convertInternal(Object obj) {
        Type typeArgument = TypeUtil.getTypeArgument(this.targetType);
        Object objConvert = !TypeUtil.isUnknown(typeArgument) ? ConverterRegistry.getInstance().convert(typeArgument, obj) : null;
        if (objConvert != null) {
            obj = objConvert;
        }
        Class<? extends Reference> cls = this.targetType;
        if (cls == WeakReference.class) {
            return new WeakReference(obj);
        }
        if (cls == SoftReference.class) {
            return new SoftReference(obj);
        }
        throw new UnsupportedOperationException(CharSequenceUtil.format("Unsupport Reference type: {}", this.targetType.getName()));
    }
}

package cn.hutool.core.convert.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Converter;
import cn.hutool.core.convert.a;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.TypeUtil;
import java.lang.reflect.Type;
import java.util.Collection;

/* loaded from: classes.dex */
public class CollectionConverter implements Converter<Collection<?>> {
    private final Type collectionType;
    private final Type elementType;

    public CollectionConverter() {
        this((Class<?>) Collection.class);
    }

    public Collection<?> convertInternal(Object obj) {
        return CollUtil.addAll(CollUtil.create(TypeUtil.getClass(this.collectionType), TypeUtil.getClass(this.elementType)), obj, this.elementType);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, java.util.Collection<?>] */
    @Override // cn.hutool.core.convert.Converter
    public /* synthetic */ Collection<?> convertWithCheck(Object obj, Collection<?> collection, boolean z2) {
        return a.a(this, obj, collection, z2);
    }

    public CollectionConverter(Type type) {
        this(type, TypeUtil.getTypeArgument(type));
    }

    @Override // cn.hutool.core.convert.Converter
    public Collection<?> convert(Object obj, Collection<?> collection) throws IllegalArgumentException {
        return (Collection) ObjectUtil.defaultIfNull(convertInternal(obj), collection);
    }

    public CollectionConverter(Class<?> cls) {
        this(cls, TypeUtil.getTypeArgument(cls));
    }

    public CollectionConverter(Type type, Type type2) {
        this.collectionType = type;
        this.elementType = type2;
    }
}

package cn.hutool.core.convert.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.convert.ConvertException;
import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.TypeUtil;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public class MapConverter extends AbstractConverter<Map<?, ?>> {
    private static final long serialVersionUID = 1;
    private final Type keyType;
    private final Type mapType;
    private final Type valueType;

    public MapConverter(Type type) {
        this(type, TypeUtil.getTypeArgument(type, 0), TypeUtil.getTypeArgument(type, 1));
    }

    private void convertMapToMap(Map<?, ?> map, final Map<Object, Object> map2) {
        final ConverterRegistry converterRegistry = ConverterRegistry.getInstance();
        map.forEach(new BiConsumer() { // from class: v.n
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) throws ConvertException {
                this.f28246c.lambda$convertMapToMap$0(converterRegistry, map2, obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertMapToMap$0(ConverterRegistry converterRegistry, Map map, Object obj, Object obj2) throws ConvertException {
        if (!TypeUtil.isUnknown(this.keyType)) {
            obj = converterRegistry.convert(this.keyType, obj);
        }
        if (!TypeUtil.isUnknown(this.valueType)) {
            obj2 = converterRegistry.convert(this.valueType, obj2);
        }
        map.put(obj, obj2);
    }

    @Override // cn.hutool.core.convert.AbstractConverter
    public Class<Map<?, ?>> getTargetType() {
        return TypeUtil.getClass(this.mapType);
    }

    public MapConverter(Type type, Type type2, Type type3) {
        this.mapType = type;
        this.keyType = type2;
        this.valueType = type3;
    }

    @Override // cn.hutool.core.convert.AbstractConverter
    public Map<?, ?> convertInternal(Object obj) {
        Type[] typeArguments;
        if (!(obj instanceof Map)) {
            if (BeanUtil.isBean(obj.getClass())) {
                return convertInternal((Object) BeanUtil.beanToMap(obj, new String[0]));
            }
            throw new UnsupportedOperationException(CharSequenceUtil.format("Unsupported toMap value type: {}", obj.getClass().getName()));
        }
        Class<?> cls = obj.getClass();
        if (cls.equals(this.mapType) && (typeArguments = TypeUtil.getTypeArguments(cls)) != null && 2 == typeArguments.length && Objects.equals(this.keyType, typeArguments[0]) && Objects.equals(this.valueType, typeArguments[1])) {
            return (Map) obj;
        }
        Class<?> cls2 = TypeUtil.getClass(this.mapType);
        Map<?, ?> linkedHashMap = (cls2 == null || cls2.isAssignableFrom(AbstractMap.class)) ? new LinkedHashMap<>() : MapUtil.createMap(cls2);
        convertMapToMap((Map) obj, linkedHashMap);
        return linkedHashMap;
    }
}

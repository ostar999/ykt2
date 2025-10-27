package cn.hutool.core.convert.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.convert.ConvertException;
import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.TypeUtil;
import java.lang.reflect.Type;
import java.util.Map;

/* loaded from: classes.dex */
public class PairConverter extends AbstractConverter<Pair<?, ?>> {
    private final Type keyType;
    private final Type pairType;
    private final Type valueType;

    public PairConverter(Type type) {
        this(type, null, null);
    }

    private static Pair<?, ?> mapToPair(Type type, Type type2, Type type3, Map map) throws ConvertException {
        Object objConvert;
        Object objConvert2;
        if (1 == map.size()) {
            Map.Entry entry = (Map.Entry) map.entrySet().iterator().next();
            objConvert = entry.getKey();
            objConvert2 = entry.getValue();
        } else if (2 == map.size()) {
            objConvert = map.get("key");
            objConvert2 = map.get("value");
        } else {
            objConvert = null;
            objConvert2 = null;
        }
        ConverterRegistry converterRegistry = ConverterRegistry.getInstance();
        Class<?> cls = TypeUtil.getClass(type);
        Object[] objArr = new Object[2];
        if (!TypeUtil.isUnknown(type2)) {
            objConvert = converterRegistry.convert(type2, objConvert);
        }
        objArr[0] = objConvert;
        if (!TypeUtil.isUnknown(type3)) {
            objConvert2 = converterRegistry.convert(type3, objConvert2);
        }
        objArr[1] = objConvert2;
        return (Pair) ReflectUtil.newInstance(cls, objArr);
    }

    private static Map<CharSequence, CharSequence> strToMap(CharSequence charSequence) {
        int iIndexOf = CharSequenceUtil.indexOf(charSequence, '=', 0, charSequence.length());
        if (iIndexOf > -1) {
            return MapUtil.of(charSequence.subSequence(0, iIndexOf + 1), charSequence.subSequence(iIndexOf, charSequence.length()));
        }
        return null;
    }

    public PairConverter(Type type, Type type2, Type type3) {
        this.pairType = type;
        this.keyType = type2;
        this.valueType = type3;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // cn.hutool.core.convert.AbstractConverter
    public Pair<?, ?> convertInternal(Object obj) {
        Map mapStrToMap;
        if (obj instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) obj;
            mapStrToMap = MapUtil.of(entry.getKey(), entry.getValue());
        } else {
            mapStrToMap = obj instanceof Map ? (Map) obj : obj instanceof CharSequence ? strToMap((CharSequence) obj) : BeanUtil.isReadableBean(obj.getClass()) ? BeanUtil.beanToMap(obj, new String[0]) : null;
        }
        if (mapStrToMap != null) {
            return mapToPair(this.pairType, this.keyType, this.valueType, mapStrToMap);
        }
        throw new ConvertException("Unsupported to map from [{}] of type: {}", obj, obj.getClass().getName());
    }
}

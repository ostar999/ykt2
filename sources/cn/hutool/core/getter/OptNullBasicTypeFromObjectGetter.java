package cn.hutool.core.getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/* loaded from: classes.dex */
public interface OptNullBasicTypeFromObjectGetter<K> extends OptNullBasicTypeGetter<K> {
    @Override // cn.hutool.core.getter.OptBasicTypeGetter
    BigDecimal getBigDecimal(K k2, BigDecimal bigDecimal);

    @Override // cn.hutool.core.getter.OptBasicTypeGetter
    BigInteger getBigInteger(K k2, BigInteger bigInteger);

    @Override // cn.hutool.core.getter.OptBasicTypeGetter
    Boolean getBool(K k2, Boolean bool);

    @Override // cn.hutool.core.getter.OptBasicTypeGetter
    Byte getByte(K k2, Byte b3);

    @Override // cn.hutool.core.getter.OptBasicTypeGetter
    Character getChar(K k2, Character ch);

    @Override // cn.hutool.core.getter.OptBasicTypeGetter
    Date getDate(K k2, Date date);

    @Override // cn.hutool.core.getter.OptBasicTypeGetter
    Double getDouble(K k2, Double d3);

    @Override // cn.hutool.core.getter.OptBasicTypeGetter
    <E extends Enum<E>> E getEnum(Class<E> cls, K k2, E e2);

    @Override // cn.hutool.core.getter.OptBasicTypeGetter
    Float getFloat(K k2, Float f2);

    @Override // cn.hutool.core.getter.OptBasicTypeGetter
    Integer getInt(K k2, Integer num);

    @Override // cn.hutool.core.getter.OptBasicTypeGetter
    Long getLong(K k2, Long l2);

    @Override // cn.hutool.core.getter.OptBasicTypeGetter
    Short getShort(K k2, Short sh);

    @Override // cn.hutool.core.getter.OptBasicTypeGetter
    String getStr(K k2, String str);
}

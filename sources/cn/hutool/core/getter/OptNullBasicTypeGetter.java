package cn.hutool.core.getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/* loaded from: classes.dex */
public interface OptNullBasicTypeGetter<K> extends BasicTypeGetter<K>, OptBasicTypeGetter<K> {
    @Override // cn.hutool.core.getter.BasicTypeGetter
    BigDecimal getBigDecimal(K k2);

    @Override // cn.hutool.core.getter.BasicTypeGetter
    BigInteger getBigInteger(K k2);

    @Override // cn.hutool.core.getter.BasicTypeGetter
    Boolean getBool(K k2);

    @Override // cn.hutool.core.getter.BasicTypeGetter
    Byte getByte(K k2);

    @Override // cn.hutool.core.getter.BasicTypeGetter
    Character getChar(K k2);

    @Override // cn.hutool.core.getter.BasicTypeGetter
    Date getDate(K k2);

    @Override // cn.hutool.core.getter.BasicTypeGetter
    Double getDouble(K k2);

    @Override // cn.hutool.core.getter.BasicTypeGetter
    <E extends Enum<E>> E getEnum(Class<E> cls, K k2);

    @Override // cn.hutool.core.getter.BasicTypeGetter
    Float getFloat(K k2);

    @Override // cn.hutool.core.getter.BasicTypeGetter
    Integer getInt(K k2);

    @Override // cn.hutool.core.getter.BasicTypeGetter
    Long getLong(K k2);

    @Override // cn.hutool.core.getter.BasicTypeGetter
    Object getObj(K k2);

    @Override // cn.hutool.core.getter.BasicTypeGetter
    Short getShort(K k2);

    @Override // cn.hutool.core.getter.BasicTypeGetter
    String getStr(K k2);
}

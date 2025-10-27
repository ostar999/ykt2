package cn.hutool.core.getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/* loaded from: classes.dex */
public interface BasicTypeGetter<K> {
    BigDecimal getBigDecimal(K k2);

    BigInteger getBigInteger(K k2);

    Boolean getBool(K k2);

    Byte getByte(K k2);

    Character getChar(K k2);

    Date getDate(K k2);

    Double getDouble(K k2);

    <E extends Enum<E>> E getEnum(Class<E> cls, K k2);

    Float getFloat(K k2);

    Integer getInt(K k2);

    Long getLong(K k2);

    Object getObj(K k2);

    Short getShort(K k2);

    String getStr(K k2);
}

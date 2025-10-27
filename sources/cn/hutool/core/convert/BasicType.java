package cn.hutool.core.convert;

import cn.hutool.core.map.SafeConcurrentHashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public enum BasicType {
    BYTE,
    SHORT,
    INT,
    INTEGER,
    LONG,
    DOUBLE,
    FLOAT,
    BOOLEAN,
    CHAR,
    CHARACTER,
    STRING;

    public static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_MAP;
    public static final Map<Class<?>, Class<?>> WRAPPER_PRIMITIVE_MAP;

    /* JADX WARN: Multi-variable type inference failed */
    static {
        SafeConcurrentHashMap safeConcurrentHashMap = new SafeConcurrentHashMap(8);
        WRAPPER_PRIMITIVE_MAP = safeConcurrentHashMap;
        PRIMITIVE_WRAPPER_MAP = new SafeConcurrentHashMap(8);
        safeConcurrentHashMap.put(Boolean.class, Boolean.TYPE);
        safeConcurrentHashMap.put(Byte.class, Byte.TYPE);
        safeConcurrentHashMap.put(Character.class, Character.TYPE);
        safeConcurrentHashMap.put(Double.class, Double.TYPE);
        safeConcurrentHashMap.put(Float.class, Float.TYPE);
        safeConcurrentHashMap.put(Integer.class, Integer.TYPE);
        safeConcurrentHashMap.put(Long.class, Long.TYPE);
        safeConcurrentHashMap.put(Short.class, Short.TYPE);
        Iterator it = safeConcurrentHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            PRIMITIVE_WRAPPER_MAP.put(entry.getValue(), entry.getKey());
        }
    }

    public static Class<?> unWrap(Class<?> cls) {
        Class<?> cls2;
        return (cls == null || cls.isPrimitive() || (cls2 = WRAPPER_PRIMITIVE_MAP.get(cls)) == null) ? cls : cls2;
    }

    public static Class<?> wrap(Class<?> cls) {
        Class<?> cls2;
        return (cls == null || !cls.isPrimitive() || (cls2 = PRIMITIVE_WRAPPER_MAP.get(cls)) == null) ? cls : cls2;
    }
}

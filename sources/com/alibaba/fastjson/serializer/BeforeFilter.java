package com.alibaba.fastjson.serializer;

/* loaded from: classes2.dex */
public abstract class BeforeFilter implements SerializeFilter {
    private static final ThreadLocal<JSONSerializer> serializerLocal = new ThreadLocal<>();
    private static final ThreadLocal<Character> seperatorLocal = new ThreadLocal<>();
    private static final Character COMMA = ',';

    public final char writeBefore(JSONSerializer jSONSerializer, Object obj, char c3) {
        ThreadLocal<JSONSerializer> threadLocal = serializerLocal;
        threadLocal.set(jSONSerializer);
        ThreadLocal<Character> threadLocal2 = seperatorLocal;
        threadLocal2.set(Character.valueOf(c3));
        writeBefore(obj);
        threadLocal.set(null);
        return threadLocal2.get().charValue();
    }

    public abstract void writeBefore(Object obj);

    public final void writeKeyValue(String str, Object obj) {
        JSONSerializer jSONSerializer = serializerLocal.get();
        ThreadLocal<Character> threadLocal = seperatorLocal;
        char cCharValue = threadLocal.get().charValue();
        jSONSerializer.writeKeyValue(cCharValue, str, obj);
        if (cCharValue != ',') {
            threadLocal.set(COMMA);
        }
    }
}

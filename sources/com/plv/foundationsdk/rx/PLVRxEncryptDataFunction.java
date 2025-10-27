package com.plv.foundationsdk.rx;

import android.util.Pair;
import com.plv.foundationsdk.utils.PLVJsonUtils;
import io.reactivex.functions.Function;

/* loaded from: classes4.dex */
public abstract class PLVRxEncryptDataFunction<T> implements Function<T, T> {
    public static final String SET_DATA_METHOD = "setData";
    private Class filedMapClass;
    private boolean isList;
    private String methodName;

    public PLVRxEncryptDataFunction(Class cls) {
        this(SET_DATA_METHOD, cls);
    }

    public static <T> T transformData(T t2, Object obj, Class cls, boolean z2) {
        return (T) transformData(t2, SET_DATA_METHOD, obj, cls, z2);
    }

    public abstract Pair<Object, Boolean> accept(T t2);

    @Override // io.reactivex.functions.Function
    public T apply(T t2) throws Exception {
        Pair<Object, Boolean> pairAccept = accept(t2);
        return this.isList ? (T) PLVJsonUtils.parseEncryptDataAndTransformList(t2, this.methodName, pairAccept.first, this.filedMapClass, ((Boolean) pairAccept.second).booleanValue()) : (T) PLVJsonUtils.parseEncryptDataAndTransformObject(t2, this.methodName, pairAccept.first, this.filedMapClass, ((Boolean) pairAccept.second).booleanValue());
    }

    public PLVRxEncryptDataFunction(String str, Class cls) {
        this(str, cls, false);
    }

    public static <T> T transformData(T t2, String str, Object obj, Class cls, boolean z2) {
        return (T) PLVJsonUtils.parseEncryptDataAndTransformObject(t2, str, obj, cls, z2);
    }

    public PLVRxEncryptDataFunction(Class cls, boolean z2) {
        this(SET_DATA_METHOD, cls, z2);
    }

    public PLVRxEncryptDataFunction(String str, Class cls, boolean z2) {
        this.methodName = str;
        this.filedMapClass = cls;
        this.isList = z2;
    }
}

package com.plv.foundationsdk.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVJsonUtils {
    private static final String PERFORM_TRANSFORM_WITH_EVALUATION = "performTransformWithEvaluation:";
    private static final String TAG = "PLVJsonUtils";

    public static class ParameterizedTypeImpl implements ParameterizedType {
        Class clazz;

        public ParameterizedTypeImpl(Class cls) {
            this.clazz = cls;
        }

        @Override // java.lang.reflect.ParameterizedType
        public Type[] getActualTypeArguments() {
            return new Type[]{this.clazz};
        }

        @Override // java.lang.reflect.ParameterizedType
        public Type getOwnerType() {
            return null;
        }

        @Override // java.lang.reflect.ParameterizedType
        public Type getRawType() {
            return List.class;
        }
    }

    public static <T> List<T> jsonToList(String str, Class cls) {
        return (List) new Gson().fromJson(str, new ParameterizedTypeImpl(cls));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v3, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v4 */
    private static <T> T parseEncryptDataAndTransform(T t2, String str, Object obj, Class cls, boolean z2, boolean z3) {
        if ((obj instanceof String) && z2) {
            obj = PLVUtils.parseEncryptData((String) obj);
            Object objFromJson = z3 ? PLVGsonUtil.fromJson(new TypeToken<List<T>>() { // from class: com.plv.foundationsdk.utils.PLVJsonUtils.1
            }, (String) obj) : PLVGsonUtil.fromJson(cls, obj, false);
            if (objFromJson != null) {
                obj = objFromJson;
            }
        }
        return z3 ? (T) performTransformWithEvaluation(t2, str, obj, cls) : (T) performTransformWithEvaluationObject(t2, str, obj, cls);
    }

    public static <T> T parseEncryptDataAndTransformList(T t2, String str, Object obj, Class cls, boolean z2) {
        return (T) parseEncryptDataAndTransform(t2, str, obj, cls, z2, true);
    }

    public static <T> T parseEncryptDataAndTransformObject(T t2, String str, Object obj, Class cls, boolean z2) {
        return (T) parseEncryptDataAndTransform(t2, str, obj, cls, z2, false);
    }

    private static <T> List<T> performTransform(Object obj, Class cls) throws JsonSyntaxException {
        ArrayList arrayList = new ArrayList();
        if (obj == null || (obj instanceof String) || cls.equals(obj.getClass())) {
            arrayList.add(obj);
            return arrayList;
        }
        Gson gson = new Gson();
        JsonElement jsonElement = new JsonParser().parse(gson.toJson(obj));
        if (jsonElement == null) {
            return null;
        }
        if (jsonElement.isJsonObject() || jsonElement.isJsonPrimitive()) {
            arrayList.add(gson.fromJson(jsonElement, cls));
        } else {
            if (!jsonElement.isJsonArray()) {
                return null;
            }
            arrayList.addAll(jsonToList(jsonElement, cls));
        }
        return arrayList;
    }

    private static <T> T performTransformWithEvaluation(T t2, String str, Object obj, Class cls) throws IllegalAccessException, JsonSyntaxException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        List listPerformTransform = performTransform(obj, cls);
        try {
            Method method = t2.getClass().getMethod(str, Object.class);
            method.setAccessible(true);
            method.invoke(t2, listPerformTransform);
        } catch (IllegalAccessException e2) {
            PLVCommonLog.e(TAG, PERFORM_TRANSFORM_WITH_EVALUATION + e2.getMessage());
        } catch (NoSuchMethodException e3) {
            PLVCommonLog.e(TAG, PERFORM_TRANSFORM_WITH_EVALUATION + e3.getMessage());
        } catch (InvocationTargetException e4) {
            PLVCommonLog.e(TAG, PERFORM_TRANSFORM_WITH_EVALUATION + e4.getMessage());
        }
        return t2;
    }

    private static <T> T performTransformWithEvaluationObject(T t2, String str, Object obj, Class cls) throws IllegalAccessException, JsonSyntaxException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        List listPerformTransform = performTransform(obj, cls);
        try {
            Method method = t2.getClass().getMethod(str, Object.class);
            method.setAccessible(true);
            if (listPerformTransform == null || listPerformTransform.isEmpty()) {
                method.invoke(t2, listPerformTransform);
            } else {
                method.invoke(t2, listPerformTransform.get(0));
            }
        } catch (IllegalAccessException e2) {
            PLVCommonLog.e(TAG, PERFORM_TRANSFORM_WITH_EVALUATION + e2.getMessage());
        } catch (NoSuchMethodException e3) {
            PLVCommonLog.e(TAG, PERFORM_TRANSFORM_WITH_EVALUATION + e3.getMessage());
        } catch (InvocationTargetException e4) {
            PLVCommonLog.e(TAG, PERFORM_TRANSFORM_WITH_EVALUATION + e4.getMessage());
        }
        return t2;
    }

    public static <T> List<T> jsonToList(JsonElement jsonElement, Class cls) {
        return (List) new Gson().fromJson(jsonElement, new ParameterizedTypeImpl(cls));
    }
}

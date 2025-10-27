package com.blankj.utilcode.util;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public final class GsonUtils {
    private static final Map<String, Gson> GSONS = new ConcurrentHashMap();
    private static final String KEY_DEFAULT = "defaultGson";
    private static final String KEY_DELEGATE = "delegateGson";
    private static final String KEY_LOG_UTILS = "logUtilsGson";

    private GsonUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static Gson createGson() {
        return new GsonBuilder().serializeNulls().disableHtmlEscaping().create();
    }

    public static <T> T fromJson(String str, @NonNull Class<T> cls) {
        return (T) fromJson(getGson(), str, (Class) cls);
    }

    public static Type getArrayType(@NonNull Type type) {
        return TypeToken.getArray(type).getType();
    }

    public static Gson getGson(String str) {
        return GSONS.get(str);
    }

    public static Gson getGson4LogUtils() {
        Map<String, Gson> map = GSONS;
        Gson gson = map.get(KEY_LOG_UTILS);
        if (gson != null) {
            return gson;
        }
        Gson gsonCreate = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        map.put(KEY_LOG_UTILS, gsonCreate);
        return gsonCreate;
    }

    public static Type getListType(@NonNull Type type) {
        return TypeToken.getParameterized(List.class, type).getType();
    }

    public static Type getMapType(@NonNull Type type, @NonNull Type type2) {
        return TypeToken.getParameterized(Map.class, type, type2).getType();
    }

    public static Type getSetType(@NonNull Type type) {
        return TypeToken.getParameterized(Set.class, type).getType();
    }

    public static Type getType(@NonNull Type type, @NonNull Type... typeArr) {
        return TypeToken.getParameterized(type, typeArr).getType();
    }

    public static void setGson(String str, Gson gson) {
        if (TextUtils.isEmpty(str) || gson == null) {
            return;
        }
        GSONS.put(str, gson);
    }

    public static void setGsonDelegate(Gson gson) {
        if (gson == null) {
            return;
        }
        GSONS.put(KEY_DELEGATE, gson);
    }

    public static String toJson(Object obj) {
        return toJson(getGson(), obj);
    }

    public static <T> T fromJson(String str, @NonNull Type type) {
        return (T) fromJson(getGson(), str, type);
    }

    public static Gson getGson() {
        Map<String, Gson> map = GSONS;
        Gson gson = map.get(KEY_DELEGATE);
        if (gson != null) {
            return gson;
        }
        Gson gson2 = map.get(KEY_DEFAULT);
        if (gson2 != null) {
            return gson2;
        }
        Gson gsonCreateGson = createGson();
        map.put(KEY_DEFAULT, gsonCreateGson);
        return gsonCreateGson;
    }

    public static String toJson(Object obj, @NonNull Type type) {
        return toJson(getGson(), obj, type);
    }

    public static <T> T fromJson(@NonNull Reader reader, @NonNull Class<T> cls) {
        return (T) fromJson(getGson(), reader, (Class) cls);
    }

    public static String toJson(@NonNull Gson gson, Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJson(@NonNull Reader reader, @NonNull Type type) {
        return (T) fromJson(getGson(), reader, type);
    }

    public static String toJson(@NonNull Gson gson, Object obj, @NonNull Type type) {
        return gson.toJson(obj, type);
    }

    public static <T> T fromJson(@NonNull Gson gson, String str, @NonNull Class<T> cls) {
        return (T) gson.fromJson(str, (Class) cls);
    }

    public static <T> T fromJson(@NonNull Gson gson, String str, @NonNull Type type) {
        return (T) gson.fromJson(str, type);
    }

    public static <T> T fromJson(@NonNull Gson gson, Reader reader, @NonNull Class<T> cls) {
        return (T) gson.fromJson(reader, (Class) cls);
    }

    public static <T> T fromJson(@NonNull Gson gson, Reader reader, @NonNull Type type) {
        return (T) gson.fromJson(reader, type);
    }
}

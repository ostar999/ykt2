package com.catchpig.mvvm.utils;

import androidx.exifinterface.media.ExifInterface;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.internal.C$Gson$Types;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001J-\u0010\b\u001a\u0004\u0018\u0001H\t\"\u0004\b\u0000\u0010\t2\b\u0010\n\u001a\u0004\u0018\u00010\u00062\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u0002H\t\u0018\u00010\f¢\u0006\u0002\u0010\rJ.\u0010\u000e\u001a\n\u0012\u0004\u0012\u0002H\t\u0018\u00010\u000f\"\u0004\b\u0000\u0010\t2\b\u0010\n\u001a\u0004\u0018\u00010\u00062\u000e\u0010\u0010\u001a\n\u0012\u0004\u0012\u0002H\t\u0018\u00010\fJ.\u0010\u0011\u001a\u001a\u0012\u0014\u0012\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0012\u0004\u0012\u0002H\t\u0018\u00010\u0012\u0018\u00010\u000f\"\u0004\b\u0000\u0010\t2\b\u0010\n\u001a\u0004\u0018\u00010\u0006J&\u0010\u0013\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0012\u0004\u0012\u0002H\t\u0018\u00010\u0012\"\u0004\b\u0000\u0010\t2\b\u0010\n\u001a\u0004\u0018\u00010\u0006J.\u0010\u0014\u001a\n\u0012\u0004\u0012\u0002H\t\u0018\u00010\u0015\"\u0004\b\u0000\u0010\t2\b\u0010\n\u001a\u0004\u0018\u00010\u00062\u000e\u0010\u0010\u001a\n\u0012\u0004\u0012\u0002H\t\u0018\u00010\fJ,\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\t0\u000f\"\u0004\b\u0000\u0010\t2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00062\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u0002H\t\u0018\u00010\fR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/catchpig/mvvm/utils/GsonUtil;", "", "()V", "gson", "Lcom/google/gson/Gson;", "GsonString", "", "object", "GsonToBean", ExifInterface.GPS_DIRECTION_TRUE, "gsonString", "cls", "Ljava/lang/Class;", "(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;", "GsonToList", "", "clazz", "GsonToListMaps", "", "GsonToMaps", "GsonToMutableList", "", "jsonToList", AliyunVodHttpCommon.Format.FORMAT_JSON, "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class GsonUtil {

    @NotNull
    public static final GsonUtil INSTANCE = new GsonUtil();

    @Nullable
    private static Gson gson;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private GsonUtil() {
    }

    @Nullable
    public final String GsonString(@Nullable Object object) {
        Gson gson2 = gson;
        if (gson2 == null) {
            return null;
        }
        Intrinsics.checkNotNull(gson2);
        return gson2.toJson(object);
    }

    @Nullable
    public final <T> T GsonToBean(@Nullable String gsonString, @Nullable Class<T> cls) {
        Gson gson2 = gson;
        if (gson2 == null) {
            return null;
        }
        Intrinsics.checkNotNull(gson2);
        return (T) gson2.fromJson(gsonString, (Class) cls);
    }

    @Nullable
    public final <T> List<T> GsonToList(@Nullable String gsonString, @Nullable Class<T> clazz) {
        if ((gsonString == null || gsonString.length() == 0) || gson == null) {
            return null;
        }
        ParameterizedType parameterizedTypeNewParameterizedTypeWithOwner = C$Gson$Types.newParameterizedTypeWithOwner(null, ArrayList.class, clazz);
        Gson gson2 = gson;
        Intrinsics.checkNotNull(gson2);
        return (List) gson2.fromJson(gsonString, parameterizedTypeNewParameterizedTypeWithOwner);
    }

    @Nullable
    public final <T> List<Map<String, T>> GsonToListMaps(@Nullable String gsonString) {
        Gson gson2 = gson;
        if (gson2 == null) {
            return null;
        }
        Intrinsics.checkNotNull(gson2);
        return (List) gson2.fromJson(gsonString, new TypeToken<List<? extends Map<String, ? extends T>>>() { // from class: com.catchpig.mvvm.utils.GsonUtil.GsonToListMaps.1
        }.getType());
    }

    @Nullable
    public final <T> Map<String, T> GsonToMaps(@Nullable String gsonString) {
        Gson gson2 = gson;
        if (gson2 == null) {
            return null;
        }
        Intrinsics.checkNotNull(gson2);
        return (Map) gson2.fromJson(gsonString, new TypeToken<Map<String, ? extends T>>() { // from class: com.catchpig.mvvm.utils.GsonUtil.GsonToMaps.1
        }.getType());
    }

    @Nullable
    public final <T> List<T> GsonToMutableList(@Nullable String gsonString, @Nullable Class<T> clazz) {
        if ((gsonString == null || gsonString.length() == 0) || gson == null) {
            return null;
        }
        ParameterizedType parameterizedTypeNewParameterizedTypeWithOwner = C$Gson$Types.newParameterizedTypeWithOwner(null, List.class, clazz);
        Gson gson2 = gson;
        Intrinsics.checkNotNull(gson2);
        return (List) gson2.fromJson(gsonString, parameterizedTypeNewParameterizedTypeWithOwner);
    }

    @NotNull
    public final <T> List<T> jsonToList(@Nullable String json, @Nullable Class<T> cls) {
        Gson gson2 = new Gson();
        ArrayList arrayList = new ArrayList();
        Iterator<JsonElement> it = new JsonParser().parse(json).getAsJsonArray().iterator();
        while (it.hasNext()) {
            arrayList.add(gson2.fromJson(it.next(), (Class) cls));
        }
        return arrayList;
    }
}

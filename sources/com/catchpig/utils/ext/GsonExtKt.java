package com.catchpig.utils.ext;

import androidx.exifinterface.media.ExifInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\u001a$\u0010\u0004\u001a\u0002H\u0005\"\u0006\b\u0000\u0010\u0005\u0018\u0001*\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0001H\u0086\b¢\u0006\u0002\u0010\b\u001a\u0014\u0010\t\u001a\u00020\u0006*\u00020\n2\b\b\u0002\u0010\u0007\u001a\u00020\u0001\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u000b"}, d2 = {"defaultGson", "Lcom/google/gson/Gson;", "getDefaultGson", "()Lcom/google/gson/Gson;", "jsonToClass", ExifInterface.GPS_DIRECTION_TRUE, "", "gson", "(Ljava/lang/String;Lcom/google/gson/Gson;)Ljava/lang/Object;", "jsonToString", "", "utils_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class GsonExtKt {

    @NotNull
    private static final Gson defaultGson;

    static {
        Gson gsonCreate = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Intrinsics.checkNotNullExpressionValue(gsonCreate, "GsonBuilder().setDateFormat(DATE_FORMAT).create()");
        defaultGson = gsonCreate;
    }

    @NotNull
    public static final Gson getDefaultGson() {
        return defaultGson;
    }

    public static final /* synthetic */ <T> T jsonToClass(String str, Gson gson) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(gson, "gson");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) gson.fromJson(str, (Class) Object.class);
    }

    public static /* synthetic */ Object jsonToClass$default(String str, Gson gson, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            gson = getDefaultGson();
        }
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(gson, "gson");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return gson.fromJson(str, Object.class);
    }

    @NotNull
    public static final String jsonToString(@NotNull Object obj, @NotNull Gson gson) {
        Intrinsics.checkNotNullParameter(obj, "<this>");
        Intrinsics.checkNotNullParameter(gson, "gson");
        String json = gson.toJson(obj);
        Intrinsics.checkNotNullExpressionValue(json, "gson.toJson(this)");
        return json;
    }

    public static /* synthetic */ String jsonToString$default(Object obj, Gson gson, int i2, Object obj2) {
        if ((i2 & 1) != 0) {
            gson = defaultGson;
        }
        return jsonToString(obj, gson);
    }
}

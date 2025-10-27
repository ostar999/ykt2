package com.catchpig.mvvm.ext;

import androidx.exifinterface.media.ExifInterface;
import com.catchpig.mvvm.config.Config;
import com.google.gson.Gson;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\u001a$\u0010\u0000\u001a\u0002H\u0001\"\u0006\b\u0000\u0010\u0001\u0018\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0086\b¢\u0006\u0002\u0010\u0005\u001a\u0014\u0010\u0006\u001a\u00020\u0002*\u00020\u00072\b\b\u0002\u0010\u0003\u001a\u00020\u0004¨\u0006\b"}, d2 = {"jsonToClass", ExifInterface.GPS_DIRECTION_TRUE, "", "gson", "Lcom/google/gson/Gson;", "(Ljava/lang/String;Lcom/google/gson/Gson;)Ljava/lang/Object;", "jsonToString", "", "mvvm_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class GsonExtKt {
    public static final /* synthetic */ <T> T jsonToClass(String str, Gson gson) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(gson, "gson");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) gson.fromJson(str, (Class) Object.class);
    }

    public static /* synthetic */ Object jsonToClass$default(String str, Gson gson, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            gson = Config.INSTANCE.getGson();
            Intrinsics.checkNotNullExpressionValue(gson, "Config.gson");
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
            gson = Config.INSTANCE.getGson();
            Intrinsics.checkNotNullExpressionValue(gson, "Config.gson");
        }
        return jsonToString(obj, gson);
    }
}

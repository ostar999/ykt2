package com.catchpig.mvvm.utils;

import androidx.exifinterface.media.ExifInterface;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u0002H\u0004\"\u0006\b\u0000\u0010\u0004\u0018\u00012\u0006\u0010\u0005\u001a\u0002H\u0004H\u0086\b¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u001a\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u000e¨\u0006\u000f"}, d2 = {"Lcom/catchpig/mvvm/utils/ConvertUtil;", "", "()V", "deepClone", ExifInterface.GPS_DIRECTION_TRUE, "data", "(Ljava/lang/Object;)Ljava/lang/Object;", "floatToString", "", "num", "", "getJsonparameter", "Lokhttp3/RequestBody;", "map", "", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class ConvertUtil {

    @NotNull
    public static final ConvertUtil INSTANCE = new ConvertUtil();

    private ConvertUtil() {
    }

    public final /* synthetic */ <T> T deepClone(T data) {
        Gson gson = new Gson();
        String json = new Gson().toJson(data);
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) gson.fromJson(json, (Class) Object.class);
    }

    @NotNull
    public final String floatToString(float num) {
        String plainString = new BigDecimal(String.valueOf(num)).stripTrailingZeros().toPlainString();
        Intrinsics.checkNotNullExpressionValue(plainString, "bd.stripTrailingZeros().toPlainString()");
        return plainString;
    }

    @NotNull
    public final synchronized RequestBody getJsonparameter(@NotNull Map<String, String> map) {
        String json;
        RequestBody.Companion companion;
        Intrinsics.checkNotNullParameter(map, "map");
        json = new Gson().toJson(map);
        companion = RequestBody.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(json, "json");
        return companion.create(json, MediaType.INSTANCE.parse("application/json; charset=utf-8"));
    }
}

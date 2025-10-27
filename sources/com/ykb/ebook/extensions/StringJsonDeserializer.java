package com.ykb.ebook.extensions;

import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J$\u0010\u0004\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016¨\u0006\u000b"}, d2 = {"Lcom/ykb/ebook/extensions/StringJsonDeserializer;", "Lcom/google/gson/JsonDeserializer;", "", "()V", "deserialize", AliyunVodHttpCommon.Format.FORMAT_JSON, "Lcom/google/gson/JsonElement;", "typeOfT", "Ljava/lang/reflect/Type;", com.umeng.analytics.pro.d.R, "Lcom/google/gson/JsonDeserializationContext;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class StringJsonDeserializer implements JsonDeserializer<String> {
    @Override // com.google.gson.JsonDeserializer
    @Nullable
    public String deserialize(@NotNull JsonElement json, @NotNull Type typeOfT, @Nullable JsonDeserializationContext context) {
        Intrinsics.checkNotNullParameter(json, "json");
        Intrinsics.checkNotNullParameter(typeOfT, "typeOfT");
        if (json.isJsonPrimitive()) {
            return json.getAsString();
        }
        if (json.isJsonNull()) {
            return null;
        }
        return json.toString();
    }
}

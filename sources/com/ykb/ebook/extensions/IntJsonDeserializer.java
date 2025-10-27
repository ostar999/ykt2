package com.ykb.ebook.extensions;

import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J+\u0010\u0004\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"}, d2 = {"Lcom/ykb/ebook/extensions/IntJsonDeserializer;", "Lcom/google/gson/JsonDeserializer;", "", "()V", "deserialize", AliyunVodHttpCommon.Format.FORMAT_JSON, "Lcom/google/gson/JsonElement;", "typeOfT", "Ljava/lang/reflect/Type;", com.umeng.analytics.pro.d.R, "Lcom/google/gson/JsonDeserializationContext;", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Ljava/lang/Integer;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class IntJsonDeserializer implements JsonDeserializer<Integer> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.gson.JsonDeserializer
    @Nullable
    public Integer deserialize(@NotNull JsonElement json, @Nullable Type typeOfT, @Nullable JsonDeserializationContext context) {
        Intrinsics.checkNotNullParameter(json, "json");
        if (!json.isJsonPrimitive()) {
            return null;
        }
        JsonPrimitive asJsonPrimitive = json.getAsJsonPrimitive();
        if (asJsonPrimitive.isNumber()) {
            return Integer.valueOf(asJsonPrimitive.getAsNumber().intValue());
        }
        return null;
    }
}

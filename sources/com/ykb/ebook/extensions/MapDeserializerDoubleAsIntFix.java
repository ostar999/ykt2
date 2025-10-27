package com.ykb.ebook.extensions;

import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.LinkedTreeMap;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0005J0\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0012\u0010\r\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000e\u001a\u00020\bH\u0002¨\u0006\u000f"}, d2 = {"Lcom/ykb/ebook/extensions/MapDeserializerDoubleAsIntFix;", "Lcom/google/gson/JsonDeserializer;", "", "", "", "()V", "deserialize", "jsonElement", "Lcom/google/gson/JsonElement;", "type", "Ljava/lang/reflect/Type;", "jsonDeserializationContext", "Lcom/google/gson/JsonDeserializationContext;", "read", AliyunVodHttpCommon.Format.FORMAT_JSON, "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class MapDeserializerDoubleAsIntFix implements JsonDeserializer<Map<String, ? extends Object>> {
    private final Object read(JsonElement json) {
        if (json.isJsonArray()) {
            ArrayList arrayList = new ArrayList();
            Iterator<JsonElement> it = json.getAsJsonArray().iterator();
            while (it.hasNext()) {
                JsonElement anArr = it.next();
                Intrinsics.checkNotNullExpressionValue(anArr, "anArr");
                arrayList.add(read(anArr));
            }
            return arrayList;
        }
        if (json.isJsonObject()) {
            LinkedTreeMap linkedTreeMap = new LinkedTreeMap();
            for (Map.Entry<String, JsonElement> entitySet : json.getAsJsonObject().entrySet()) {
                Intrinsics.checkNotNullExpressionValue(entitySet, "entitySet");
                String key = entitySet.getKey();
                JsonElement value = entitySet.getValue();
                Intrinsics.checkNotNullExpressionValue(key, "key");
                Intrinsics.checkNotNullExpressionValue(value, "value");
                linkedTreeMap.put(key, read(value));
            }
            return linkedTreeMap;
        }
        if (!json.isJsonPrimitive()) {
            return null;
        }
        JsonPrimitive asJsonPrimitive = json.getAsJsonPrimitive();
        if (asJsonPrimitive.isBoolean()) {
            return Boolean.valueOf(asJsonPrimitive.getAsBoolean());
        }
        if (asJsonPrimitive.isString()) {
            return asJsonPrimitive.getAsString();
        }
        if (!asJsonPrimitive.isNumber()) {
            return null;
        }
        Number asNumber = asJsonPrimitive.getAsNumber();
        Intrinsics.checkNotNullExpressionValue(asNumber, "prim.asNumber");
        return (Math.ceil(asNumber.doubleValue()) > ((double) asNumber.longValue()) ? 1 : (Math.ceil(asNumber.doubleValue()) == ((double) asNumber.longValue()) ? 0 : -1)) == 0 ? Long.valueOf(asNumber.longValue()) : Double.valueOf(asNumber.doubleValue());
    }

    @Override // com.google.gson.JsonDeserializer
    @Nullable
    public Map<String, ? extends Object> deserialize(@NotNull JsonElement jsonElement, @NotNull Type type, @NotNull JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Intrinsics.checkNotNullParameter(jsonElement, "jsonElement");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(jsonDeserializationContext, "jsonDeserializationContext");
        Object obj = read(jsonElement);
        if (obj instanceof Map) {
            return (Map) obj;
        }
        return null;
    }
}

package com.psychiatrygarden.http;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

/* loaded from: classes4.dex */
public class CodeParseJsonAdapter implements JsonDeserializer<Boolean> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.gson.JsonDeserializer
    public Boolean deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return Boolean.valueOf(jsonElement.getAsInt() == 200);
    }
}

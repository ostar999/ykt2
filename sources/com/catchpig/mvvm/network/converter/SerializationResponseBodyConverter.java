package com.catchpig.mvvm.network.converter;

import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.catchpig.annotation.interfaces.SerializationConverter;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.SerializersKt;
import kotlinx.serialization.json.Json;
import kotlinx.serialization.json.JsonBuilder;
import kotlinx.serialization.json.JsonKt;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0012\u001a\u00020\u0002H\u0016R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0013"}, d2 = {"Lcom/catchpig/mvvm/network/converter/SerializationResponseBodyConverter;", "Lcom/catchpig/annotation/interfaces/SerializationConverter;", "Lokhttp3/ResponseBody;", "", "()V", AliyunVodHttpCommon.Format.FORMAT_JSON, "Lkotlinx/serialization/json/Json;", "getJson", "()Lkotlinx/serialization/json/Json;", "setJson", "(Lkotlinx/serialization/json/Json;)V", "type", "Ljava/lang/reflect/Type;", "getType", "()Ljava/lang/reflect/Type;", "setType", "(Ljava/lang/reflect/Type;)V", "convert", "value", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class SerializationResponseBodyConverter implements SerializationConverter<ResponseBody, Object> {

    @NotNull
    private Json json = JsonKt.Json$default(null, new Function1<JsonBuilder, Unit>() { // from class: com.catchpig.mvvm.network.converter.SerializationResponseBodyConverter$json$1
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(JsonBuilder jsonBuilder) {
            invoke2(jsonBuilder);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull JsonBuilder Json) {
            Intrinsics.checkNotNullParameter(Json, "$this$Json");
            Json.setLenient(true);
            Json.setIgnoreUnknownKeys(true);
            Json.setCoerceInputValues(true);
            Json.setExplicitNulls(true);
        }
    }, 1, null);
    public Type type;

    @Override // com.catchpig.annotation.interfaces.SerializationConverter
    @NotNull
    public Json getJson() {
        return this.json;
    }

    @NotNull
    public final Type getType() {
        Type type = this.type;
        if (type != null) {
            return type;
        }
        Intrinsics.throwUninitializedPropertyAccessException("type");
        return null;
    }

    @Override // com.catchpig.annotation.interfaces.SerializationConverter
    public void setJson(@NotNull Json json) {
        Intrinsics.checkNotNullParameter(json, "<set-?>");
        this.json = json;
    }

    public final void setType(@NotNull Type type) {
        Intrinsics.checkNotNullParameter(type, "<set-?>");
        this.type = type;
    }

    @Override // retrofit2.Converter
    @Nullable
    public Object convert(@NotNull ResponseBody value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return getJson().decodeFromString(SerializersKt.serializer(getType()), value.string());
    }
}

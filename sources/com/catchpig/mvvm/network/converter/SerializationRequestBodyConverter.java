package com.catchpig.mvvm.network.converter;

import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.catchpig.annotation.interfaces.SerializationConverter;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlinx.serialization.SerializersKt;
import kotlinx.serialization.json.Json;
import kotlinx.serialization.json.JsonBuilder;
import kotlinx.serialization.json.JsonKt;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u000f2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u000fB\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\r\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000e\u001a\u00020\u0002H\u0016R\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/catchpig/mvvm/network/converter/SerializationRequestBodyConverter;", "Lcom/catchpig/annotation/interfaces/SerializationConverter;", "", "Lokhttp3/RequestBody;", "type", "Ljava/lang/reflect/Type;", "(Ljava/lang/reflect/Type;)V", AliyunVodHttpCommon.Format.FORMAT_JSON, "Lkotlinx/serialization/json/Json;", "getJson", "()Lkotlinx/serialization/json/Json;", "setJson", "(Lkotlinx/serialization/json/Json;)V", "convert", "value", "Companion", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class SerializationRequestBodyConverter implements SerializationConverter<Object, RequestBody> {

    @NotNull
    private static final MediaType MEDIA_TYPE = MediaType.INSTANCE.get("application/json; charset=UTF-8");

    @NotNull
    private Json json;

    @NotNull
    private final Type type;

    public SerializationRequestBodyConverter(@NotNull Type type) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        this.json = JsonKt.Json$default(null, new Function1<JsonBuilder, Unit>() { // from class: com.catchpig.mvvm.network.converter.SerializationRequestBodyConverter$json$1
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
    }

    @Override // com.catchpig.annotation.interfaces.SerializationConverter
    @NotNull
    public Json getJson() {
        return this.json;
    }

    @Override // com.catchpig.annotation.interfaces.SerializationConverter
    public void setJson(@NotNull Json json) {
        Intrinsics.checkNotNullParameter(json, "<set-?>");
        this.json = json;
    }

    @Override // retrofit2.Converter
    @Nullable
    public RequestBody convert(@NotNull Object value) {
        Intrinsics.checkNotNullParameter(value, "value");
        String strEncodeToString = getJson().encodeToString(SerializersKt.serializer(this.type), value);
        Buffer buffer = new Buffer();
        buffer.writeString(strEncodeToString, Charsets.UTF_8);
        return RequestBody.INSTANCE.create(buffer.readByteString(), MEDIA_TYPE);
    }
}

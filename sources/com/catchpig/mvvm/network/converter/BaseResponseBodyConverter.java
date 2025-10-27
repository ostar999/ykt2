package com.catchpig.mvvm.network.converter;

import com.catchpig.annotation.interfaces.SerializationConverter;
import com.catchpig.mvvm.network.data.IResponseData;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.SerializersKt;
import kotlinx.serialization.json.Json;
import kotlinx.serialization.json.JsonElement;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000 \u00182\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u0018B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0012\u0010\r\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000e\u001a\u00020\u0002H\u0016J\u0016\u0010\u000f\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\u0010H&J\u001c\u0010\u0013\u001a\u00060\u0014j\u0002`\u00152\u0006\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\fH&R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u0019"}, d2 = {"Lcom/catchpig/mvvm/network/converter/BaseResponseBodyConverter;", "Lcom/catchpig/annotation/interfaces/SerializationConverter;", "Lokhttp3/ResponseBody;", "", "()V", "type", "Ljava/lang/reflect/Type;", "getType", "()Ljava/lang/reflect/Type;", "setType", "(Ljava/lang/reflect/Type;)V", "checkType", "", "convert", "value", "getResultClass", "Lkotlin/reflect/KClass;", "Lcom/catchpig/mvvm/network/data/IResponseData;", "Lkotlinx/serialization/json/JsonElement;", "handlerErrorCode", "Ljava/lang/Exception;", "Lkotlin/Exception;", "errorCode", "msg", "Companion", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nBaseResponseBodyConverter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BaseResponseBodyConverter.kt\ncom/catchpig/mvvm/network/converter/BaseResponseBodyConverter\n+ 2 SerialFormat.kt\nkotlinx/serialization/SerialFormatKt\n*L\n1#1,84:1\n113#2:85\n*S KotlinDebug\n*F\n+ 1 BaseResponseBodyConverter.kt\ncom/catchpig/mvvm/network/converter/BaseResponseBodyConverter\n*L\n39#1:85\n*E\n"})
/* loaded from: classes2.dex */
public abstract class BaseResponseBodyConverter implements SerializationConverter<ResponseBody, Object> {

    @NotNull
    private static final String LIST_EMPTY = "[]";

    @NotNull
    private static final String MAP_EMPTY = "{}";

    @NotNull
    private static final String NUMBER_ZERO = "0";

    @NotNull
    private static final String STRING_EMPTY = "\"\"";
    public Type type;

    private final String checkType(Type type) {
        if (type instanceof ParameterizedType) {
            return Intrinsics.areEqual(((ParameterizedType) type).getRawType(), List.class) ? "[]" : "{}";
        }
        if (Intrinsics.areEqual(type, String.class)) {
            return STRING_EMPTY;
        }
        return Intrinsics.areEqual(type, Integer.TYPE) ? true : Intrinsics.areEqual(type, Double.TYPE) ? true : Intrinsics.areEqual(type, Float.TYPE) ? true : Intrinsics.areEqual(type, Long.TYPE) ? "0" : "{}";
    }

    @NotNull
    public abstract KClass<? extends IResponseData<JsonElement>> getResultClass();

    @NotNull
    public final Type getType() {
        Type type = this.type;
        if (type != null) {
            return type;
        }
        Intrinsics.throwUninitializedPropertyAccessException("type");
        return null;
    }

    @NotNull
    public abstract Exception handlerErrorCode(@NotNull String errorCode, @NotNull String msg);

    public final void setType(@NotNull Type type) {
        Intrinsics.checkNotNullParameter(type, "<set-?>");
        this.type = type;
    }

    @Override // retrofit2.Converter
    @Nullable
    public Object convert(@NotNull ResponseBody value) throws Exception {
        Intrinsics.checkNotNullParameter(value, "value");
        Object objDecodeFromString = getJson().decodeFromString(SerializersKt.serializer(JvmClassMappingKt.getJavaClass((KClass) getResultClass())), value.string());
        Intrinsics.checkNotNull(objDecodeFromString, "null cannot be cast to non-null type com.catchpig.mvvm.network.data.IResponseData<kotlinx.serialization.json.JsonElement>");
        IResponseData iResponseData = (IResponseData) objDecodeFromString;
        if (!Intrinsics.areEqual(iResponseData.getErrorCode(), iResponseData.isSuccess())) {
            String errorMessage = iResponseData.getErrorMessage();
            String errorMessage2 = errorMessage == null || errorMessage.length() == 0 ? "unknown error!" : iResponseData.getErrorMessage();
            String errorCode = iResponseData.getErrorCode();
            Intrinsics.checkNotNull(errorMessage2);
            throw handlerErrorCode(errorCode, errorMessage2);
        }
        JsonElement jsonElementData = iResponseData.data();
        if (jsonElementData == null) {
            return getJson().decodeFromString(SerializersKt.serializer(getType()), checkType(getType()));
        }
        Json json = getJson();
        KSerializer<Object> kSerializerSerializer = SerializersKt.serializer(getType());
        Json json2 = getJson();
        json2.getSerializersModule();
        return json.decodeFromString(kSerializerSerializer, json2.encodeToString(JsonElement.INSTANCE.serializer(), jsonElementData));
    }
}

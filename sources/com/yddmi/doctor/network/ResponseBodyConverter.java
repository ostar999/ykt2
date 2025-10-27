package com.yddmi.doctor.network;

import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.catchpig.mvvm.network.converter.BaseResponseBodyConverter;
import com.catchpig.mvvm.network.data.IResponseData;
import com.catchpig.utils.ext.LogExtKt;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.exception.HttpLogout401Exception;
import com.yddmi.doctor.exception.HttpServerException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlinx.serialization.json.Json;
import kotlinx.serialization.json.JsonBuilder;
import kotlinx.serialization.json.JsonElement;
import kotlinx.serialization.json.JsonKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\t\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\nH\u0016J\u001c\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u0013"}, d2 = {"Lcom/yddmi/doctor/network/ResponseBodyConverter;", "Lcom/catchpig/mvvm/network/converter/BaseResponseBodyConverter;", "()V", AliyunVodHttpCommon.Format.FORMAT_JSON, "Lkotlinx/serialization/json/Json;", "getJson", "()Lkotlinx/serialization/json/Json;", "setJson", "(Lkotlinx/serialization/json/Json;)V", "getResultClass", "Lkotlin/reflect/KClass;", "Lcom/catchpig/mvvm/network/data/IResponseData;", "Lkotlinx/serialization/json/JsonElement;", "handlerErrorCode", "Ljava/lang/Exception;", "Lkotlin/Exception;", "errorCode", "", "msg", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ResponseBodyConverter extends BaseResponseBodyConverter {

    @NotNull
    private Json json = JsonKt.Json$default(null, new Function1<JsonBuilder, Unit>() { // from class: com.yddmi.doctor.network.ResponseBodyConverter$json$1
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

    @Override // com.catchpig.annotation.interfaces.SerializationConverter
    @NotNull
    public Json getJson() {
        return this.json;
    }

    @Override // com.catchpig.mvvm.network.converter.BaseResponseBodyConverter
    @NotNull
    public KClass<? extends IResponseData<JsonElement>> getResultClass() {
        return Reflection.getOrCreateKotlinClass(Result.class);
    }

    @Override // com.catchpig.mvvm.network.converter.BaseResponseBodyConverter
    @NotNull
    public Exception handlerErrorCode(@NotNull String errorCode, @NotNull String msg) {
        Intrinsics.checkNotNullParameter(errorCode, "errorCode");
        Intrinsics.checkNotNullParameter(msg, "msg");
        LogExtKt.loge("handlerErrorCode " + errorCode + " " + msg, YddConfig.TAG);
        if (!Intrinsics.areEqual("401", errorCode)) {
            return new HttpServerException(errorCode, msg);
        }
        GlobalAction.INSTANCE.setM401ErrorMsg(msg);
        return new HttpLogout401Exception(errorCode, msg);
    }

    @Override // com.catchpig.annotation.interfaces.SerializationConverter
    public void setJson(@NotNull Json json) {
        Intrinsics.checkNotNullParameter(json, "<set-?>");
        this.json = json;
    }
}

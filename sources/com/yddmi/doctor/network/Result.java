package com.yddmi.doctor.network;

import androidx.annotation.Keep;
import com.catchpig.mvvm.network.data.IResponseData;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.Serializable;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import kotlinx.serialization.json.JsonElement;
import kotlinx.serialization.json.JsonElementSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 (2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002'(B5\b\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bB%\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\fJ\t\u0010\u0013\u001a\u00020\u0004HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0002HÆ\u0003J+\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u00042\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0002HÆ\u0001J\n\u0010\b\u001a\u0004\u0018\u00010\u0002H\u0016J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aHÖ\u0003J\b\u0010\u001b\u001a\u00020\u0007H\u0016J\n\u0010\u001c\u001a\u0004\u0018\u00010\u0007H\u0016J\t\u0010\u001d\u001a\u00020\u0004HÖ\u0001J\b\u0010\u001e\u001a\u00020\u0007H\u0016J\t\u0010\u001f\u001a\u00020\u0007HÖ\u0001J!\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u00002\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&HÇ\u0001R\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006)"}, d2 = {"Lcom/yddmi/doctor/network/Result;", "Lcom/catchpig/mvvm/network/data/IResponseData;", "Lkotlinx/serialization/json/JsonElement;", "seen1", "", "code", "msg", "", "data", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IILjava/lang/String;Lkotlinx/serialization/json/JsonElement;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(ILjava/lang/String;Lkotlinx/serialization/json/JsonElement;)V", "getCode", "()I", "getData", "()Lkotlinx/serialization/json/JsonElement;", "getMsg", "()Ljava/lang/String;", "component1", "component2", "component3", "copy", "equals", "", "other", "", "getErrorCode", "getErrorMessage", "hashCode", "isSuccess", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class Result implements IResponseData<JsonElement> {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    public static final String SUCCESS_CODE = "200";
    private final int code;

    @Nullable
    private final JsonElement data;

    @Nullable
    private final String msg;

    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006HÆ\u0001R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/yddmi/doctor/network/Result$Companion;", "", "()V", "SUCCESS_CODE", "", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/network/Result;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<Result> serializer() {
            return Result$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ Result(int i2, int i3, String str, JsonElement jsonElement, SerializationConstructorMarker serializationConstructorMarker) {
        if (1 != (i2 & 1)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 1, Result$$serializer.INSTANCE.getDescriptor());
        }
        this.code = i3;
        if ((i2 & 2) == 0) {
            this.msg = "";
        } else {
            this.msg = str;
        }
        if ((i2 & 4) == 0) {
            this.data = null;
        } else {
            this.data = jsonElement;
        }
    }

    public static /* synthetic */ Result copy$default(Result result, int i2, String str, JsonElement jsonElement, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = result.code;
        }
        if ((i3 & 2) != 0) {
            str = result.msg;
        }
        if ((i3 & 4) != 0) {
            jsonElement = result.data;
        }
        return result.copy(i2, str, jsonElement);
    }

    @JvmStatic
    public static final void write$Self(@NotNull Result self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        output.encodeIntElement(serialDesc, 0, self.code);
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.msg, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.msg);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.data != null) {
            output.encodeNullableSerializableElement(serialDesc, 2, JsonElementSerializer.INSTANCE, self.data);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getCode() {
        return this.code;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getMsg() {
        return this.msg;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final JsonElement getData() {
        return this.data;
    }

    @NotNull
    public final Result copy(int code, @Nullable String msg, @Nullable JsonElement data) {
        return new Result(code, msg, data);
    }

    @Override // com.catchpig.mvvm.network.data.IResponseData
    @Nullable
    public JsonElement data() {
        return this.data;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Result)) {
            return false;
        }
        Result result = (Result) other;
        return this.code == result.code && Intrinsics.areEqual(this.msg, result.msg) && Intrinsics.areEqual(this.data, result.data);
    }

    public final int getCode() {
        return this.code;
    }

    @Nullable
    public final JsonElement getData() {
        return this.data;
    }

    @Override // com.catchpig.mvvm.network.data.IResponseData
    @NotNull
    public String getErrorCode() {
        return String.valueOf(this.code);
    }

    @Override // com.catchpig.mvvm.network.data.IResponseData
    @Nullable
    public String getErrorMessage() {
        return this.msg;
    }

    @Nullable
    public final String getMsg() {
        return this.msg;
    }

    public int hashCode() {
        int i2 = this.code * 31;
        String str = this.msg;
        int iHashCode = (i2 + (str == null ? 0 : str.hashCode())) * 31;
        JsonElement jsonElement = this.data;
        return iHashCode + (jsonElement != null ? jsonElement.hashCode() : 0);
    }

    @Override // com.catchpig.mvvm.network.data.IResponseData
    @NotNull
    public String isSuccess() {
        return "200";
    }

    @NotNull
    public String toString() {
        return "Result(code=" + this.code + ", msg=" + this.msg + ", data=" + this.data + ")";
    }

    public Result(int i2, @Nullable String str, @Nullable JsonElement jsonElement) {
        this.code = i2;
        this.msg = str;
        this.data = jsonElement;
    }

    public /* synthetic */ Result(int i2, String str, JsonElement jsonElement, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i2, (i3 & 2) != 0 ? "" : str, (i3 & 4) != 0 ? null : jsonElement);
    }
}

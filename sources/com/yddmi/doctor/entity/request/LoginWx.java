package com.yddmi.doctor.entity.request;

import androidx.annotation.Keep;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 32\u00020\u0001:\u000223BS\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\rBK\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u000eJ\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0005HÆ\u0003JO\u0010%\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010)\u001a\u00020\u0003HÖ\u0001J\t\u0010*\u001a\u00020\u0005HÖ\u0001J!\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u00002\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u000201HÇ\u0001R\u001c\u0010\t\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\n\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0010\"\u0004\b\u0014\u0010\u0012R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0010\"\u0004\b\u0016\u0010\u0012R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0010\"\u0004\b\u0018\u0010\u0012R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0010\"\u0004\b\u001a\u0010\u0012R\u001a\u0010\u0007\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001e¨\u00064"}, d2 = {"Lcom/yddmi/doctor/entity/request/LoginWx;", "", "seen1", "", ConstantsAPI.Token.WX_TOKEN_PLATFORMID_KEY, "", "phoneSystem", "platformType", AliyunLogCommon.TERMINAL_TYPE, "code", "logToken", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCode", "()Ljava/lang/String;", "setCode", "(Ljava/lang/String;)V", "getLogToken", "setLogToken", "getPhone", "setPhone", "getPhoneSystem", "setPhoneSystem", "getPlatformId", "setPlatformId", "getPlatformType", "()I", "setPlatformType", "(I)V", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class LoginWx {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private String code;

    @Nullable
    private String logToken;

    @Nullable
    private String phone;

    @Nullable
    private String phoneSystem;

    @Nullable
    private String platformId;
    private int platformType;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/LoginWx$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/LoginWx;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<LoginWx> serializer() {
            return LoginWx$$serializer.INSTANCE;
        }
    }

    public LoginWx() {
        this((String) null, (String) null, 0, (String) null, (String) null, (String) null, 63, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ LoginWx(int i2, String str, String str2, int i3, String str3, String str4, String str5, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, LoginWx$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.platformId = "";
        } else {
            this.platformId = str;
        }
        if ((i2 & 2) == 0) {
            this.phoneSystem = "";
        } else {
            this.phoneSystem = str2;
        }
        if ((i2 & 4) == 0) {
            this.platformType = -1;
        } else {
            this.platformType = i3;
        }
        if ((i2 & 8) == 0) {
            this.phone = "";
        } else {
            this.phone = str3;
        }
        if ((i2 & 16) == 0) {
            this.code = "";
        } else {
            this.code = str4;
        }
        if ((i2 & 32) == 0) {
            this.logToken = "";
        } else {
            this.logToken = str5;
        }
    }

    public static /* synthetic */ LoginWx copy$default(LoginWx loginWx, String str, String str2, int i2, String str3, String str4, String str5, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = loginWx.platformId;
        }
        if ((i3 & 2) != 0) {
            str2 = loginWx.phoneSystem;
        }
        String str6 = str2;
        if ((i3 & 4) != 0) {
            i2 = loginWx.platformType;
        }
        int i4 = i2;
        if ((i3 & 8) != 0) {
            str3 = loginWx.phone;
        }
        String str7 = str3;
        if ((i3 & 16) != 0) {
            str4 = loginWx.code;
        }
        String str8 = str4;
        if ((i3 & 32) != 0) {
            str5 = loginWx.logToken;
        }
        return loginWx.copy(str, str6, i4, str7, str8, str5);
    }

    @JvmStatic
    public static final void write$Self(@NotNull LoginWx self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.platformId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.platformId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.phoneSystem, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.phoneSystem);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.platformType != -1) {
            output.encodeIntElement(serialDesc, 2, self.platformType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.phone, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.phone);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.code, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.code);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.logToken, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.logToken);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getPlatformId() {
        return this.platformId;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getPhoneSystem() {
        return this.phoneSystem;
    }

    /* renamed from: component3, reason: from getter */
    public final int getPlatformType() {
        return this.platformType;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getPhone() {
        return this.phone;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getCode() {
        return this.code;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getLogToken() {
        return this.logToken;
    }

    @NotNull
    public final LoginWx copy(@Nullable String platformId, @Nullable String phoneSystem, int platformType, @Nullable String phone, @Nullable String code, @Nullable String logToken) {
        return new LoginWx(platformId, phoneSystem, platformType, phone, code, logToken);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LoginWx)) {
            return false;
        }
        LoginWx loginWx = (LoginWx) other;
        return Intrinsics.areEqual(this.platformId, loginWx.platformId) && Intrinsics.areEqual(this.phoneSystem, loginWx.phoneSystem) && this.platformType == loginWx.platformType && Intrinsics.areEqual(this.phone, loginWx.phone) && Intrinsics.areEqual(this.code, loginWx.code) && Intrinsics.areEqual(this.logToken, loginWx.logToken);
    }

    @Nullable
    public final String getCode() {
        return this.code;
    }

    @Nullable
    public final String getLogToken() {
        return this.logToken;
    }

    @Nullable
    public final String getPhone() {
        return this.phone;
    }

    @Nullable
    public final String getPhoneSystem() {
        return this.phoneSystem;
    }

    @Nullable
    public final String getPlatformId() {
        return this.platformId;
    }

    public final int getPlatformType() {
        return this.platformType;
    }

    public int hashCode() {
        String str = this.platformId;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.phoneSystem;
        int iHashCode2 = (((iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31) + this.platformType) * 31;
        String str3 = this.phone;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.code;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.logToken;
        return iHashCode4 + (str5 != null ? str5.hashCode() : 0);
    }

    public final void setCode(@Nullable String str) {
        this.code = str;
    }

    public final void setLogToken(@Nullable String str) {
        this.logToken = str;
    }

    public final void setPhone(@Nullable String str) {
        this.phone = str;
    }

    public final void setPhoneSystem(@Nullable String str) {
        this.phoneSystem = str;
    }

    public final void setPlatformId(@Nullable String str) {
        this.platformId = str;
    }

    public final void setPlatformType(int i2) {
        this.platformType = i2;
    }

    @NotNull
    public String toString() {
        return "LoginWx(platformId=" + this.platformId + ", phoneSystem=" + this.phoneSystem + ", platformType=" + this.platformType + ", phone=" + this.phone + ", code=" + this.code + ", logToken=" + this.logToken + ")";
    }

    public LoginWx(@Nullable String str, @Nullable String str2, int i2, @Nullable String str3, @Nullable String str4, @Nullable String str5) {
        this.platformId = str;
        this.phoneSystem = str2;
        this.platformType = i2;
        this.phone = str3;
        this.code = str4;
        this.logToken = str5;
    }

    public /* synthetic */ LoginWx(String str, String str2, int i2, String str3, String str4, String str5, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? "" : str, (i3 & 2) != 0 ? "" : str2, (i3 & 4) != 0 ? -1 : i2, (i3 & 8) != 0 ? "" : str3, (i3 & 16) != 0 ? "" : str4, (i3 & 32) != 0 ? "" : str5);
    }
}

package com.yddmi.doctor.entity.request;

import androidx.annotation.Keep;
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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 32\u00020\u0001:\u000223BQ\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\rB=\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005¢\u0006\u0002\u0010\u000eJ\t\u0010\u001f\u001a\u00020\u0005HÆ\u0003J\t\u0010 \u001a\u00020\u0005HÆ\u0003J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\t\u0010\"\u001a\u00020\u0003HÆ\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010$\u001a\u00020\u0005HÆ\u0003JG\u0010%\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\n\u001a\u00020\u0005HÆ\u0001J\u0013\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010)\u001a\u00020\u0003HÖ\u0001J\t\u0010*\u001a\u00020\u0005HÖ\u0001J!\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u00002\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u000201HÇ\u0001R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0010\"\u0004\b\u0014\u0010\u0012R\u001a\u0010\b\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0007\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0016\"\u0004\b\u001a\u0010\u0018R\u001c\u0010\t\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0010\"\u0004\b\u001c\u0010\u0012R\u001a\u0010\n\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0010\"\u0004\b\u001e\u0010\u0012¨\u00064"}, d2 = {"Lcom/yddmi/doctor/entity/request/LoginWxBindingReq;", "", "seen1", "", ConstantsAPI.Token.WX_TOKEN_PLATFORMID_KEY, "", "personId", "userId", "platformType", "wxCode", "wxToken", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;)V", "getPersonId", "()Ljava/lang/String;", "setPersonId", "(Ljava/lang/String;)V", "getPlatformId", "setPlatformId", "getPlatformType", "()I", "setPlatformType", "(I)V", "getUserId", "setUserId", "getWxCode", "setWxCode", "getWxToken", "setWxToken", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class LoginWxBindingReq {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private String personId;

    @NotNull
    private String platformId;
    private int platformType;
    private int userId;

    @Nullable
    private String wxCode;

    @NotNull
    private String wxToken;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/LoginWxBindingReq$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/LoginWxBindingReq;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<LoginWxBindingReq> serializer() {
            return LoginWxBindingReq$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ LoginWxBindingReq(int i2, String str, String str2, int i3, int i4, String str3, String str4, SerializationConstructorMarker serializationConstructorMarker) {
        if (7 != (i2 & 7)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 7, LoginWxBindingReq$$serializer.INSTANCE.getDescriptor());
        }
        this.platformId = str;
        this.personId = str2;
        this.userId = i3;
        if ((i2 & 8) == 0) {
            this.platformType = -1;
        } else {
            this.platformType = i4;
        }
        if ((i2 & 16) == 0) {
            this.wxCode = "";
        } else {
            this.wxCode = str3;
        }
        if ((i2 & 32) == 0) {
            this.wxToken = "";
        } else {
            this.wxToken = str4;
        }
    }

    public static /* synthetic */ LoginWxBindingReq copy$default(LoginWxBindingReq loginWxBindingReq, String str, String str2, int i2, int i3, String str3, String str4, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            str = loginWxBindingReq.platformId;
        }
        if ((i4 & 2) != 0) {
            str2 = loginWxBindingReq.personId;
        }
        String str5 = str2;
        if ((i4 & 4) != 0) {
            i2 = loginWxBindingReq.userId;
        }
        int i5 = i2;
        if ((i4 & 8) != 0) {
            i3 = loginWxBindingReq.platformType;
        }
        int i6 = i3;
        if ((i4 & 16) != 0) {
            str3 = loginWxBindingReq.wxCode;
        }
        String str6 = str3;
        if ((i4 & 32) != 0) {
            str4 = loginWxBindingReq.wxToken;
        }
        return loginWxBindingReq.copy(str, str5, i5, i6, str6, str4);
    }

    @JvmStatic
    public static final void write$Self(@NotNull LoginWxBindingReq self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        output.encodeStringElement(serialDesc, 0, self.platformId);
        output.encodeStringElement(serialDesc, 1, self.personId);
        output.encodeIntElement(serialDesc, 2, self.userId);
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.platformType != -1) {
            output.encodeIntElement(serialDesc, 3, self.platformType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.wxCode, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.wxCode);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.wxToken, "")) {
            output.encodeStringElement(serialDesc, 5, self.wxToken);
        }
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getPlatformId() {
        return this.platformId;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getPersonId() {
        return this.personId;
    }

    /* renamed from: component3, reason: from getter */
    public final int getUserId() {
        return this.userId;
    }

    /* renamed from: component4, reason: from getter */
    public final int getPlatformType() {
        return this.platformType;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getWxCode() {
        return this.wxCode;
    }

    @NotNull
    /* renamed from: component6, reason: from getter */
    public final String getWxToken() {
        return this.wxToken;
    }

    @NotNull
    public final LoginWxBindingReq copy(@NotNull String platformId, @NotNull String personId, int userId, int platformType, @Nullable String wxCode, @NotNull String wxToken) {
        Intrinsics.checkNotNullParameter(platformId, "platformId");
        Intrinsics.checkNotNullParameter(personId, "personId");
        Intrinsics.checkNotNullParameter(wxToken, "wxToken");
        return new LoginWxBindingReq(platformId, personId, userId, platformType, wxCode, wxToken);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LoginWxBindingReq)) {
            return false;
        }
        LoginWxBindingReq loginWxBindingReq = (LoginWxBindingReq) other;
        return Intrinsics.areEqual(this.platformId, loginWxBindingReq.platformId) && Intrinsics.areEqual(this.personId, loginWxBindingReq.personId) && this.userId == loginWxBindingReq.userId && this.platformType == loginWxBindingReq.platformType && Intrinsics.areEqual(this.wxCode, loginWxBindingReq.wxCode) && Intrinsics.areEqual(this.wxToken, loginWxBindingReq.wxToken);
    }

    @NotNull
    public final String getPersonId() {
        return this.personId;
    }

    @NotNull
    public final String getPlatformId() {
        return this.platformId;
    }

    public final int getPlatformType() {
        return this.platformType;
    }

    public final int getUserId() {
        return this.userId;
    }

    @Nullable
    public final String getWxCode() {
        return this.wxCode;
    }

    @NotNull
    public final String getWxToken() {
        return this.wxToken;
    }

    public int hashCode() {
        int iHashCode = ((((((this.platformId.hashCode() * 31) + this.personId.hashCode()) * 31) + this.userId) * 31) + this.platformType) * 31;
        String str = this.wxCode;
        return ((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.wxToken.hashCode();
    }

    public final void setPersonId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.personId = str;
    }

    public final void setPlatformId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.platformId = str;
    }

    public final void setPlatformType(int i2) {
        this.platformType = i2;
    }

    public final void setUserId(int i2) {
        this.userId = i2;
    }

    public final void setWxCode(@Nullable String str) {
        this.wxCode = str;
    }

    public final void setWxToken(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.wxToken = str;
    }

    @NotNull
    public String toString() {
        return "LoginWxBindingReq(platformId=" + this.platformId + ", personId=" + this.personId + ", userId=" + this.userId + ", platformType=" + this.platformType + ", wxCode=" + this.wxCode + ", wxToken=" + this.wxToken + ")";
    }

    public LoginWxBindingReq(@NotNull String platformId, @NotNull String personId, int i2, int i3, @Nullable String str, @NotNull String wxToken) {
        Intrinsics.checkNotNullParameter(platformId, "platformId");
        Intrinsics.checkNotNullParameter(personId, "personId");
        Intrinsics.checkNotNullParameter(wxToken, "wxToken");
        this.platformId = platformId;
        this.personId = personId;
        this.userId = i2;
        this.platformType = i3;
        this.wxCode = str;
        this.wxToken = wxToken;
    }

    public /* synthetic */ LoginWxBindingReq(String str, String str2, int i2, int i3, String str3, String str4, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, i2, (i4 & 8) != 0 ? -1 : i3, (i4 & 16) != 0 ? "" : str3, (i4 & 32) != 0 ? "" : str4);
    }
}

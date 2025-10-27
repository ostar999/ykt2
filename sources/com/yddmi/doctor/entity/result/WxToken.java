package com.yddmi.doctor.entity.result;

import com.tencent.connect.common.Constants;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 02\u00020\u0001:\u0002/0B]\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eB?\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\u0006\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005¢\u0006\u0002\u0010\u000fJ\t\u0010\u001b\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0005HÆ\u0003J\t\u0010 \u001a\u00020\u0005HÆ\u0003J\t\u0010!\u001a\u00020\u0005HÆ\u0003JO\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u0005HÆ\u0001J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010&\u001a\u00020\u0003HÖ\u0001J\t\u0010'\u001a\u00020\u0005HÖ\u0001J!\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u00002\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.HÇ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u000b\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0011\"\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0011\u0010\t\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011R\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0011¨\u00061"}, d2 = {"Lcom/yddmi/doctor/entity/result/WxToken;", "", "seen1", "", "access_token", "", "expires_in", "openid", "refresh_token", Constants.PARAM_SCOPE, "unionid", "mCode", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAccess_token", "()Ljava/lang/String;", "getExpires_in", "()I", "getMCode", "setMCode", "(Ljava/lang/String;)V", "getOpenid", "getRefresh_token", "getScope", "getUnionid", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class WxToken {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final String access_token;
    private final int expires_in;

    @NotNull
    private String mCode;

    @NotNull
    private final String openid;

    @NotNull
    private final String refresh_token;

    @NotNull
    private final String scope;

    @NotNull
    private final String unionid;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/WxToken$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/WxToken;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<WxToken> serializer() {
            return WxToken$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ WxToken(int i2, String str, int i3, String str2, String str3, String str4, String str5, String str6, SerializationConstructorMarker serializationConstructorMarker) {
        if (63 != (i2 & 63)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 63, WxToken$$serializer.INSTANCE.getDescriptor());
        }
        this.access_token = str;
        this.expires_in = i3;
        this.openid = str2;
        this.refresh_token = str3;
        this.scope = str4;
        this.unionid = str5;
        if ((i2 & 64) == 0) {
            this.mCode = "";
        } else {
            this.mCode = str6;
        }
    }

    public static /* synthetic */ WxToken copy$default(WxToken wxToken, String str, int i2, String str2, String str3, String str4, String str5, String str6, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = wxToken.access_token;
        }
        if ((i3 & 2) != 0) {
            i2 = wxToken.expires_in;
        }
        int i4 = i2;
        if ((i3 & 4) != 0) {
            str2 = wxToken.openid;
        }
        String str7 = str2;
        if ((i3 & 8) != 0) {
            str3 = wxToken.refresh_token;
        }
        String str8 = str3;
        if ((i3 & 16) != 0) {
            str4 = wxToken.scope;
        }
        String str9 = str4;
        if ((i3 & 32) != 0) {
            str5 = wxToken.unionid;
        }
        String str10 = str5;
        if ((i3 & 64) != 0) {
            str6 = wxToken.mCode;
        }
        return wxToken.copy(str, i4, str7, str8, str9, str10, str6);
    }

    @JvmStatic
    public static final void write$Self(@NotNull WxToken self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        output.encodeStringElement(serialDesc, 0, self.access_token);
        output.encodeIntElement(serialDesc, 1, self.expires_in);
        output.encodeStringElement(serialDesc, 2, self.openid);
        output.encodeStringElement(serialDesc, 3, self.refresh_token);
        output.encodeStringElement(serialDesc, 4, self.scope);
        output.encodeStringElement(serialDesc, 5, self.unionid);
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.mCode, "")) {
            output.encodeStringElement(serialDesc, 6, self.mCode);
        }
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getAccess_token() {
        return this.access_token;
    }

    /* renamed from: component2, reason: from getter */
    public final int getExpires_in() {
        return this.expires_in;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getOpenid() {
        return this.openid;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getRefresh_token() {
        return this.refresh_token;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getScope() {
        return this.scope;
    }

    @NotNull
    /* renamed from: component6, reason: from getter */
    public final String getUnionid() {
        return this.unionid;
    }

    @NotNull
    /* renamed from: component7, reason: from getter */
    public final String getMCode() {
        return this.mCode;
    }

    @NotNull
    public final WxToken copy(@NotNull String access_token, int expires_in, @NotNull String openid, @NotNull String refresh_token, @NotNull String scope, @NotNull String unionid, @NotNull String mCode) {
        Intrinsics.checkNotNullParameter(access_token, "access_token");
        Intrinsics.checkNotNullParameter(openid, "openid");
        Intrinsics.checkNotNullParameter(refresh_token, "refresh_token");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(unionid, "unionid");
        Intrinsics.checkNotNullParameter(mCode, "mCode");
        return new WxToken(access_token, expires_in, openid, refresh_token, scope, unionid, mCode);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof WxToken)) {
            return false;
        }
        WxToken wxToken = (WxToken) other;
        return Intrinsics.areEqual(this.access_token, wxToken.access_token) && this.expires_in == wxToken.expires_in && Intrinsics.areEqual(this.openid, wxToken.openid) && Intrinsics.areEqual(this.refresh_token, wxToken.refresh_token) && Intrinsics.areEqual(this.scope, wxToken.scope) && Intrinsics.areEqual(this.unionid, wxToken.unionid) && Intrinsics.areEqual(this.mCode, wxToken.mCode);
    }

    @NotNull
    public final String getAccess_token() {
        return this.access_token;
    }

    public final int getExpires_in() {
        return this.expires_in;
    }

    @NotNull
    public final String getMCode() {
        return this.mCode;
    }

    @NotNull
    public final String getOpenid() {
        return this.openid;
    }

    @NotNull
    public final String getRefresh_token() {
        return this.refresh_token;
    }

    @NotNull
    public final String getScope() {
        return this.scope;
    }

    @NotNull
    public final String getUnionid() {
        return this.unionid;
    }

    public int hashCode() {
        return (((((((((((this.access_token.hashCode() * 31) + this.expires_in) * 31) + this.openid.hashCode()) * 31) + this.refresh_token.hashCode()) * 31) + this.scope.hashCode()) * 31) + this.unionid.hashCode()) * 31) + this.mCode.hashCode();
    }

    public final void setMCode(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.mCode = str;
    }

    @NotNull
    public String toString() {
        return "WxToken(access_token=" + this.access_token + ", expires_in=" + this.expires_in + ", openid=" + this.openid + ", refresh_token=" + this.refresh_token + ", scope=" + this.scope + ", unionid=" + this.unionid + ", mCode=" + this.mCode + ")";
    }

    public WxToken(@NotNull String access_token, int i2, @NotNull String openid, @NotNull String refresh_token, @NotNull String scope, @NotNull String unionid, @NotNull String mCode) {
        Intrinsics.checkNotNullParameter(access_token, "access_token");
        Intrinsics.checkNotNullParameter(openid, "openid");
        Intrinsics.checkNotNullParameter(refresh_token, "refresh_token");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(unionid, "unionid");
        Intrinsics.checkNotNullParameter(mCode, "mCode");
        this.access_token = access_token;
        this.expires_in = i2;
        this.openid = openid;
        this.refresh_token = refresh_token;
        this.scope = scope;
        this.unionid = unionid;
        this.mCode = mCode;
    }

    public /* synthetic */ WxToken(String str, int i2, String str2, String str3, String str4, String str5, String str6, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i2, str2, str3, str4, str5, (i3 & 64) != 0 ? "" : str6);
    }
}

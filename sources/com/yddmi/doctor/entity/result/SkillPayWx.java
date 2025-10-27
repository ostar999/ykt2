package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
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

@Keep
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 -2\u00020\u0001:\u0002,-B_\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eB=\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\u0006\u0010\n\u001a\u00020\u0005\u0012\u0006\u0010\u000b\u001a\u00020\u0005¢\u0006\u0002\u0010\u000fJ\t\u0010\u0018\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0005HÆ\u0003JO\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u0005HÆ\u0001J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010#\u001a\u00020\u0003HÖ\u0001J\t\u0010$\u001a\u00020\u0005HÖ\u0001J!\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u00002\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+HÇ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0011\u0010\t\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0011\u0010\u000b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011¨\u0006."}, d2 = {"Lcom/yddmi/doctor/entity/result/SkillPayWx;", "", "seen1", "", "appid", "", "nonceStr", "packageVal", "partnerId", "prepayId", "sign", "timestamp", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAppid", "()Ljava/lang/String;", "getNonceStr", "getPackageVal", "getPartnerId", "getPrepayId", "getSign", "getTimestamp", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class SkillPayWx {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final String appid;

    @NotNull
    private final String nonceStr;

    @NotNull
    private final String packageVal;

    @NotNull
    private final String partnerId;

    @NotNull
    private final String prepayId;

    @NotNull
    private final String sign;

    @NotNull
    private final String timestamp;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/SkillPayWx$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/SkillPayWx;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<SkillPayWx> serializer() {
            return SkillPayWx$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ SkillPayWx(int i2, String str, String str2, String str3, String str4, String str5, String str6, String str7, SerializationConstructorMarker serializationConstructorMarker) {
        if (127 != (i2 & 127)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 127, SkillPayWx$$serializer.INSTANCE.getDescriptor());
        }
        this.appid = str;
        this.nonceStr = str2;
        this.packageVal = str3;
        this.partnerId = str4;
        this.prepayId = str5;
        this.sign = str6;
        this.timestamp = str7;
    }

    public static /* synthetic */ SkillPayWx copy$default(SkillPayWx skillPayWx, String str, String str2, String str3, String str4, String str5, String str6, String str7, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = skillPayWx.appid;
        }
        if ((i2 & 2) != 0) {
            str2 = skillPayWx.nonceStr;
        }
        String str8 = str2;
        if ((i2 & 4) != 0) {
            str3 = skillPayWx.packageVal;
        }
        String str9 = str3;
        if ((i2 & 8) != 0) {
            str4 = skillPayWx.partnerId;
        }
        String str10 = str4;
        if ((i2 & 16) != 0) {
            str5 = skillPayWx.prepayId;
        }
        String str11 = str5;
        if ((i2 & 32) != 0) {
            str6 = skillPayWx.sign;
        }
        String str12 = str6;
        if ((i2 & 64) != 0) {
            str7 = skillPayWx.timestamp;
        }
        return skillPayWx.copy(str, str8, str9, str10, str11, str12, str7);
    }

    @JvmStatic
    public static final void write$Self(@NotNull SkillPayWx self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        output.encodeStringElement(serialDesc, 0, self.appid);
        output.encodeStringElement(serialDesc, 1, self.nonceStr);
        output.encodeStringElement(serialDesc, 2, self.packageVal);
        output.encodeStringElement(serialDesc, 3, self.partnerId);
        output.encodeStringElement(serialDesc, 4, self.prepayId);
        output.encodeStringElement(serialDesc, 5, self.sign);
        output.encodeStringElement(serialDesc, 6, self.timestamp);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getAppid() {
        return this.appid;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getNonceStr() {
        return this.nonceStr;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getPackageVal() {
        return this.packageVal;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getPartnerId() {
        return this.partnerId;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getPrepayId() {
        return this.prepayId;
    }

    @NotNull
    /* renamed from: component6, reason: from getter */
    public final String getSign() {
        return this.sign;
    }

    @NotNull
    /* renamed from: component7, reason: from getter */
    public final String getTimestamp() {
        return this.timestamp;
    }

    @NotNull
    public final SkillPayWx copy(@NotNull String appid, @NotNull String nonceStr, @NotNull String packageVal, @NotNull String partnerId, @NotNull String prepayId, @NotNull String sign, @NotNull String timestamp) {
        Intrinsics.checkNotNullParameter(appid, "appid");
        Intrinsics.checkNotNullParameter(nonceStr, "nonceStr");
        Intrinsics.checkNotNullParameter(packageVal, "packageVal");
        Intrinsics.checkNotNullParameter(partnerId, "partnerId");
        Intrinsics.checkNotNullParameter(prepayId, "prepayId");
        Intrinsics.checkNotNullParameter(sign, "sign");
        Intrinsics.checkNotNullParameter(timestamp, "timestamp");
        return new SkillPayWx(appid, nonceStr, packageVal, partnerId, prepayId, sign, timestamp);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SkillPayWx)) {
            return false;
        }
        SkillPayWx skillPayWx = (SkillPayWx) other;
        return Intrinsics.areEqual(this.appid, skillPayWx.appid) && Intrinsics.areEqual(this.nonceStr, skillPayWx.nonceStr) && Intrinsics.areEqual(this.packageVal, skillPayWx.packageVal) && Intrinsics.areEqual(this.partnerId, skillPayWx.partnerId) && Intrinsics.areEqual(this.prepayId, skillPayWx.prepayId) && Intrinsics.areEqual(this.sign, skillPayWx.sign) && Intrinsics.areEqual(this.timestamp, skillPayWx.timestamp);
    }

    @NotNull
    public final String getAppid() {
        return this.appid;
    }

    @NotNull
    public final String getNonceStr() {
        return this.nonceStr;
    }

    @NotNull
    public final String getPackageVal() {
        return this.packageVal;
    }

    @NotNull
    public final String getPartnerId() {
        return this.partnerId;
    }

    @NotNull
    public final String getPrepayId() {
        return this.prepayId;
    }

    @NotNull
    public final String getSign() {
        return this.sign;
    }

    @NotNull
    public final String getTimestamp() {
        return this.timestamp;
    }

    public int hashCode() {
        return (((((((((((this.appid.hashCode() * 31) + this.nonceStr.hashCode()) * 31) + this.packageVal.hashCode()) * 31) + this.partnerId.hashCode()) * 31) + this.prepayId.hashCode()) * 31) + this.sign.hashCode()) * 31) + this.timestamp.hashCode();
    }

    @NotNull
    public String toString() {
        return "SkillPayWx(appid=" + this.appid + ", nonceStr=" + this.nonceStr + ", packageVal=" + this.packageVal + ", partnerId=" + this.partnerId + ", prepayId=" + this.prepayId + ", sign=" + this.sign + ", timestamp=" + this.timestamp + ")";
    }

    public SkillPayWx(@NotNull String appid, @NotNull String nonceStr, @NotNull String packageVal, @NotNull String partnerId, @NotNull String prepayId, @NotNull String sign, @NotNull String timestamp) {
        Intrinsics.checkNotNullParameter(appid, "appid");
        Intrinsics.checkNotNullParameter(nonceStr, "nonceStr");
        Intrinsics.checkNotNullParameter(packageVal, "packageVal");
        Intrinsics.checkNotNullParameter(partnerId, "partnerId");
        Intrinsics.checkNotNullParameter(prepayId, "prepayId");
        Intrinsics.checkNotNullParameter(sign, "sign");
        Intrinsics.checkNotNullParameter(timestamp, "timestamp");
        this.appid = appid;
        this.nonceStr = nonceStr;
        this.packageVal = packageVal;
        this.partnerId = partnerId;
        this.prepayId = prepayId;
        this.sign = sign;
        this.timestamp = timestamp;
    }
}

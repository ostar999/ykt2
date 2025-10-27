package com.yddmi.doctor.entity.request;

import androidx.annotation.Keep;
import com.aliyun.vod.log.core.AliyunLogCommon;
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
import kotlinx.serialization.internal.DoubleSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 42\u00020\u0001:\u000234Bi\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\t\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010\u0010BU\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\t\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0011J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0010\u0010 \u001a\u0004\u0018\u00010\tHÆ\u0003¢\u0006\u0002\u0010\u0017J\u0010\u0010!\u001a\u0004\u0018\u00010\tHÆ\u0003¢\u0006\u0002\u0010\u0017J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0005HÆ\u0003Jn\u0010%\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0005HÆ\u0001¢\u0006\u0002\u0010&J\u0013\u0010'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010*\u001a\u00020\u0003HÖ\u0001J\t\u0010+\u001a\u00020\u0005HÖ\u0001J!\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\u00002\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u000202HÇ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0015\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\n\n\u0002\u0010\u0018\u001a\u0004\b\u0016\u0010\u0017R\u0015\u0010\n\u001a\u0004\u0018\u00010\t¢\u0006\n\n\u0002\u0010\u0018\u001a\u0004\b\u0019\u0010\u0017R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0013R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0013R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0013¨\u00065"}, d2 = {"Lcom/yddmi/doctor/entity/request/HomeTenantReq;", "", "seen1", "", "address", "", "contacts", "introduction", "latitude", "", "longitude", "name", AliyunLogCommon.TERMINAL_TYPE, "reason", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAddress", "()Ljava/lang/String;", "getContacts", "getIntroduction", "getLatitude", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getLongitude", "getName", "getPhone", "getReason", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/yddmi/doctor/entity/request/HomeTenantReq;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeTenantReq {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final String address;

    @Nullable
    private final String contacts;

    @Nullable
    private final String introduction;

    @Nullable
    private final Double latitude;

    @Nullable
    private final Double longitude;

    @Nullable
    private final String name;

    @Nullable
    private final String phone;

    @Nullable
    private final String reason;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/HomeTenantReq$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/HomeTenantReq;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeTenantReq> serializer() {
            return HomeTenantReq$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeTenantReq(int i2, String str, String str2, String str3, Double d3, Double d4, String str4, String str5, String str6, SerializationConstructorMarker serializationConstructorMarker) {
        if (255 != (i2 & 255)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 255, HomeTenantReq$$serializer.INSTANCE.getDescriptor());
        }
        this.address = str;
        this.contacts = str2;
        this.introduction = str3;
        this.latitude = d3;
        this.longitude = d4;
        this.name = str4;
        this.phone = str5;
        this.reason = str6;
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeTenantReq self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        output.encodeNullableSerializableElement(serialDesc, 0, stringSerializer, self.address);
        output.encodeNullableSerializableElement(serialDesc, 1, stringSerializer, self.contacts);
        output.encodeNullableSerializableElement(serialDesc, 2, stringSerializer, self.introduction);
        DoubleSerializer doubleSerializer = DoubleSerializer.INSTANCE;
        output.encodeNullableSerializableElement(serialDesc, 3, doubleSerializer, self.latitude);
        output.encodeNullableSerializableElement(serialDesc, 4, doubleSerializer, self.longitude);
        output.encodeNullableSerializableElement(serialDesc, 5, stringSerializer, self.name);
        output.encodeNullableSerializableElement(serialDesc, 6, stringSerializer, self.phone);
        output.encodeNullableSerializableElement(serialDesc, 7, stringSerializer, self.reason);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getAddress() {
        return this.address;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getContacts() {
        return this.contacts;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getIntroduction() {
        return this.introduction;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final Double getLatitude() {
        return this.latitude;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final Double getLongitude() {
        return this.longitude;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getPhone() {
        return this.phone;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getReason() {
        return this.reason;
    }

    @NotNull
    public final HomeTenantReq copy(@Nullable String address, @Nullable String contacts, @Nullable String introduction, @Nullable Double latitude, @Nullable Double longitude, @Nullable String name, @Nullable String phone, @Nullable String reason) {
        return new HomeTenantReq(address, contacts, introduction, latitude, longitude, name, phone, reason);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeTenantReq)) {
            return false;
        }
        HomeTenantReq homeTenantReq = (HomeTenantReq) other;
        return Intrinsics.areEqual(this.address, homeTenantReq.address) && Intrinsics.areEqual(this.contacts, homeTenantReq.contacts) && Intrinsics.areEqual(this.introduction, homeTenantReq.introduction) && Intrinsics.areEqual((Object) this.latitude, (Object) homeTenantReq.latitude) && Intrinsics.areEqual((Object) this.longitude, (Object) homeTenantReq.longitude) && Intrinsics.areEqual(this.name, homeTenantReq.name) && Intrinsics.areEqual(this.phone, homeTenantReq.phone) && Intrinsics.areEqual(this.reason, homeTenantReq.reason);
    }

    @Nullable
    public final String getAddress() {
        return this.address;
    }

    @Nullable
    public final String getContacts() {
        return this.contacts;
    }

    @Nullable
    public final String getIntroduction() {
        return this.introduction;
    }

    @Nullable
    public final Double getLatitude() {
        return this.latitude;
    }

    @Nullable
    public final Double getLongitude() {
        return this.longitude;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final String getPhone() {
        return this.phone;
    }

    @Nullable
    public final String getReason() {
        return this.reason;
    }

    public int hashCode() {
        String str = this.address;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.contacts;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.introduction;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        Double d3 = this.latitude;
        int iHashCode4 = (iHashCode3 + (d3 == null ? 0 : d3.hashCode())) * 31;
        Double d4 = this.longitude;
        int iHashCode5 = (iHashCode4 + (d4 == null ? 0 : d4.hashCode())) * 31;
        String str4 = this.name;
        int iHashCode6 = (iHashCode5 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.phone;
        int iHashCode7 = (iHashCode6 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.reason;
        return iHashCode7 + (str6 != null ? str6.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "HomeTenantReq(address=" + this.address + ", contacts=" + this.contacts + ", introduction=" + this.introduction + ", latitude=" + this.latitude + ", longitude=" + this.longitude + ", name=" + this.name + ", phone=" + this.phone + ", reason=" + this.reason + ")";
    }

    public HomeTenantReq(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable Double d3, @Nullable Double d4, @Nullable String str4, @Nullable String str5, @Nullable String str6) {
        this.address = str;
        this.contacts = str2;
        this.introduction = str3;
        this.latitude = d3;
        this.longitude = d4;
        this.name = str4;
        this.phone = str5;
        this.reason = str6;
    }
}

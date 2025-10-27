package com.yddmi.doctor.entity.result;

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
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 02\u00020\u0001:\u0002/0Bi\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fB]\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\f\u001a\u00020\u0005¢\u0006\u0002\u0010\u0010J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0005HÆ\u0003J\t\u0010 \u001a\u00020\u0005HÆ\u0003J\t\u0010!\u001a\u00020\u0005HÆ\u0003Ja\u0010\"\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u0005HÆ\u0001J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010&\u001a\u00020\u0003HÖ\u0001J\t\u0010'\u001a\u00020\u0005HÖ\u0001J!\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u00002\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.HÇ\u0001R\u0011\u0010\f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\t\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012R\u0011\u0010\u000b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0012¨\u00061"}, d2 = {"Lcom/yddmi/doctor/entity/result/SkillCall;", "", "seen1", "", "id", "", "name", "url", AliyunLogCommon.TERMINAL_TYPE, "companyLogUrl", "companySlogan", "phoneNumber", "address", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAddress", "()Ljava/lang/String;", "getCompanyLogUrl", "getCompanySlogan", "getId", "getName", "getPhone", "getPhoneNumber", "getUrl", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class SkillCall {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final String address;

    @NotNull
    private final String companyLogUrl;

    @NotNull
    private final String companySlogan;

    @Nullable
    private final String id;

    @Nullable
    private final String name;

    @Nullable
    private final String phone;

    @NotNull
    private final String phoneNumber;

    @Nullable
    private final String url;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/SkillCall$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/SkillCall;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<SkillCall> serializer() {
            return SkillCall$$serializer.INSTANCE;
        }
    }

    public SkillCall() {
        this((String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, 255, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ SkillCall(int i2, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, SkillCall$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.id = "";
        } else {
            this.id = str;
        }
        if ((i2 & 2) == 0) {
            this.name = "";
        } else {
            this.name = str2;
        }
        if ((i2 & 4) == 0) {
            this.url = "";
        } else {
            this.url = str3;
        }
        if ((i2 & 8) == 0) {
            this.phone = "";
        } else {
            this.phone = str4;
        }
        if ((i2 & 16) == 0) {
            this.companyLogUrl = "";
        } else {
            this.companyLogUrl = str5;
        }
        if ((i2 & 32) == 0) {
            this.companySlogan = "";
        } else {
            this.companySlogan = str6;
        }
        if ((i2 & 64) == 0) {
            this.phoneNumber = "";
        } else {
            this.phoneNumber = str7;
        }
        if ((i2 & 128) == 0) {
            this.address = "";
        } else {
            this.address = str8;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull SkillCall self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.id, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.url, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.url);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.phone, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.phone);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.companyLogUrl, "")) {
            output.encodeStringElement(serialDesc, 4, self.companyLogUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.companySlogan, "")) {
            output.encodeStringElement(serialDesc, 5, self.companySlogan);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.phoneNumber, "")) {
            output.encodeStringElement(serialDesc, 6, self.phoneNumber);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || !Intrinsics.areEqual(self.address, "")) {
            output.encodeStringElement(serialDesc, 7, self.address);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getUrl() {
        return this.url;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getPhone() {
        return this.phone;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getCompanyLogUrl() {
        return this.companyLogUrl;
    }

    @NotNull
    /* renamed from: component6, reason: from getter */
    public final String getCompanySlogan() {
        return this.companySlogan;
    }

    @NotNull
    /* renamed from: component7, reason: from getter */
    public final String getPhoneNumber() {
        return this.phoneNumber;
    }

    @NotNull
    /* renamed from: component8, reason: from getter */
    public final String getAddress() {
        return this.address;
    }

    @NotNull
    public final SkillCall copy(@Nullable String id, @Nullable String name, @Nullable String url, @Nullable String phone, @NotNull String companyLogUrl, @NotNull String companySlogan, @NotNull String phoneNumber, @NotNull String address) {
        Intrinsics.checkNotNullParameter(companyLogUrl, "companyLogUrl");
        Intrinsics.checkNotNullParameter(companySlogan, "companySlogan");
        Intrinsics.checkNotNullParameter(phoneNumber, "phoneNumber");
        Intrinsics.checkNotNullParameter(address, "address");
        return new SkillCall(id, name, url, phone, companyLogUrl, companySlogan, phoneNumber, address);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SkillCall)) {
            return false;
        }
        SkillCall skillCall = (SkillCall) other;
        return Intrinsics.areEqual(this.id, skillCall.id) && Intrinsics.areEqual(this.name, skillCall.name) && Intrinsics.areEqual(this.url, skillCall.url) && Intrinsics.areEqual(this.phone, skillCall.phone) && Intrinsics.areEqual(this.companyLogUrl, skillCall.companyLogUrl) && Intrinsics.areEqual(this.companySlogan, skillCall.companySlogan) && Intrinsics.areEqual(this.phoneNumber, skillCall.phoneNumber) && Intrinsics.areEqual(this.address, skillCall.address);
    }

    @NotNull
    public final String getAddress() {
        return this.address;
    }

    @NotNull
    public final String getCompanyLogUrl() {
        return this.companyLogUrl;
    }

    @NotNull
    public final String getCompanySlogan() {
        return this.companySlogan;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final String getPhone() {
        return this.phone;
    }

    @NotNull
    public final String getPhoneNumber() {
        return this.phoneNumber;
    }

    @Nullable
    public final String getUrl() {
        return this.url;
    }

    public int hashCode() {
        String str = this.id;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.name;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.url;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.phone;
        return ((((((((iHashCode3 + (str4 != null ? str4.hashCode() : 0)) * 31) + this.companyLogUrl.hashCode()) * 31) + this.companySlogan.hashCode()) * 31) + this.phoneNumber.hashCode()) * 31) + this.address.hashCode();
    }

    @NotNull
    public String toString() {
        return "SkillCall(id=" + this.id + ", name=" + this.name + ", url=" + this.url + ", phone=" + this.phone + ", companyLogUrl=" + this.companyLogUrl + ", companySlogan=" + this.companySlogan + ", phoneNumber=" + this.phoneNumber + ", address=" + this.address + ")";
    }

    public SkillCall(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @NotNull String companyLogUrl, @NotNull String companySlogan, @NotNull String phoneNumber, @NotNull String address) {
        Intrinsics.checkNotNullParameter(companyLogUrl, "companyLogUrl");
        Intrinsics.checkNotNullParameter(companySlogan, "companySlogan");
        Intrinsics.checkNotNullParameter(phoneNumber, "phoneNumber");
        Intrinsics.checkNotNullParameter(address, "address");
        this.id = str;
        this.name = str2;
        this.url = str3;
        this.phone = str4;
        this.companyLogUrl = companyLogUrl;
        this.companySlogan = companySlogan;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public /* synthetic */ SkillCall(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? "" : str, (i2 & 2) != 0 ? "" : str2, (i2 & 4) != 0 ? "" : str3, (i2 & 8) != 0 ? "" : str4, (i2 & 16) != 0 ? "" : str5, (i2 & 32) != 0 ? "" : str6, (i2 & 64) != 0 ? "" : str7, (i2 & 128) == 0 ? str8 : "");
    }
}

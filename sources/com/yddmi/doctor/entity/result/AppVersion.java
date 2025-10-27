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
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 .2\u00020\u0001:\u0002-.BY\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eBS\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003¢\u0006\u0002\u0010\u000fJ\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003JW\u0010 \u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\b\u001a\u00020\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u0003HÆ\u0001J\u0013\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010$\u001a\u00020\u0003HÖ\u0001J\t\u0010%\u001a\u00020\u0005HÖ\u0001J!\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020\u00002\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,HÇ\u0001R\u0011\u0010\u000b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0013R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013¨\u0006/"}, d2 = {"Lcom/yddmi/doctor/entity/result/AppVersion;", "", "seen1", "", "externalVersion", "", "internalVersion", "versionIntroduce", "status", "versionUrl", "upgradeStatus", "auditStatus", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;IILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;II)V", "getAuditStatus", "()I", "getExternalVersion", "()Ljava/lang/String;", "getInternalVersion", "getStatus", "getUpgradeStatus", "getVersionIntroduce", "getVersionUrl", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class AppVersion {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private final int auditStatus;

    @Nullable
    private final String externalVersion;

    @Nullable
    private final String internalVersion;
    private final int status;
    private final int upgradeStatus;

    @Nullable
    private final String versionIntroduce;

    @Nullable
    private final String versionUrl;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/AppVersion$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/AppVersion;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<AppVersion> serializer() {
            return AppVersion$$serializer.INSTANCE;
        }
    }

    public AppVersion() {
        this((String) null, (String) null, (String) null, 0, (String) null, 0, 0, 127, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ AppVersion(int i2, String str, String str2, String str3, int i3, String str4, int i4, int i5, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, AppVersion$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.externalVersion = "";
        } else {
            this.externalVersion = str;
        }
        if ((i2 & 2) == 0) {
            this.internalVersion = "";
        } else {
            this.internalVersion = str2;
        }
        if ((i2 & 4) == 0) {
            this.versionIntroduce = "";
        } else {
            this.versionIntroduce = str3;
        }
        if ((i2 & 8) == 0) {
            this.status = 0;
        } else {
            this.status = i3;
        }
        if ((i2 & 16) == 0) {
            this.versionUrl = "";
        } else {
            this.versionUrl = str4;
        }
        if ((i2 & 32) == 0) {
            this.upgradeStatus = 0;
        } else {
            this.upgradeStatus = i4;
        }
        if ((i2 & 64) == 0) {
            this.auditStatus = 0;
        } else {
            this.auditStatus = i5;
        }
    }

    public static /* synthetic */ AppVersion copy$default(AppVersion appVersion, String str, String str2, String str3, int i2, String str4, int i3, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            str = appVersion.externalVersion;
        }
        if ((i5 & 2) != 0) {
            str2 = appVersion.internalVersion;
        }
        String str5 = str2;
        if ((i5 & 4) != 0) {
            str3 = appVersion.versionIntroduce;
        }
        String str6 = str3;
        if ((i5 & 8) != 0) {
            i2 = appVersion.status;
        }
        int i6 = i2;
        if ((i5 & 16) != 0) {
            str4 = appVersion.versionUrl;
        }
        String str7 = str4;
        if ((i5 & 32) != 0) {
            i3 = appVersion.upgradeStatus;
        }
        int i7 = i3;
        if ((i5 & 64) != 0) {
            i4 = appVersion.auditStatus;
        }
        return appVersion.copy(str, str5, str6, i6, str7, i7, i4);
    }

    @JvmStatic
    public static final void write$Self(@NotNull AppVersion self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.externalVersion, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.externalVersion);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.internalVersion, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.internalVersion);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.versionIntroduce, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.versionIntroduce);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.status != 0) {
            output.encodeIntElement(serialDesc, 3, self.status);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.versionUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.versionUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || self.upgradeStatus != 0) {
            output.encodeIntElement(serialDesc, 5, self.upgradeStatus);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || self.auditStatus != 0) {
            output.encodeIntElement(serialDesc, 6, self.auditStatus);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getExternalVersion() {
        return this.externalVersion;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getInternalVersion() {
        return this.internalVersion;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getVersionIntroduce() {
        return this.versionIntroduce;
    }

    /* renamed from: component4, reason: from getter */
    public final int getStatus() {
        return this.status;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getVersionUrl() {
        return this.versionUrl;
    }

    /* renamed from: component6, reason: from getter */
    public final int getUpgradeStatus() {
        return this.upgradeStatus;
    }

    /* renamed from: component7, reason: from getter */
    public final int getAuditStatus() {
        return this.auditStatus;
    }

    @NotNull
    public final AppVersion copy(@Nullable String externalVersion, @Nullable String internalVersion, @Nullable String versionIntroduce, int status, @Nullable String versionUrl, int upgradeStatus, int auditStatus) {
        return new AppVersion(externalVersion, internalVersion, versionIntroduce, status, versionUrl, upgradeStatus, auditStatus);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AppVersion)) {
            return false;
        }
        AppVersion appVersion = (AppVersion) other;
        return Intrinsics.areEqual(this.externalVersion, appVersion.externalVersion) && Intrinsics.areEqual(this.internalVersion, appVersion.internalVersion) && Intrinsics.areEqual(this.versionIntroduce, appVersion.versionIntroduce) && this.status == appVersion.status && Intrinsics.areEqual(this.versionUrl, appVersion.versionUrl) && this.upgradeStatus == appVersion.upgradeStatus && this.auditStatus == appVersion.auditStatus;
    }

    public final int getAuditStatus() {
        return this.auditStatus;
    }

    @Nullable
    public final String getExternalVersion() {
        return this.externalVersion;
    }

    @Nullable
    public final String getInternalVersion() {
        return this.internalVersion;
    }

    public final int getStatus() {
        return this.status;
    }

    public final int getUpgradeStatus() {
        return this.upgradeStatus;
    }

    @Nullable
    public final String getVersionIntroduce() {
        return this.versionIntroduce;
    }

    @Nullable
    public final String getVersionUrl() {
        return this.versionUrl;
    }

    public int hashCode() {
        String str = this.externalVersion;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.internalVersion;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.versionIntroduce;
        int iHashCode3 = (((iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31) + this.status) * 31;
        String str4 = this.versionUrl;
        return ((((iHashCode3 + (str4 != null ? str4.hashCode() : 0)) * 31) + this.upgradeStatus) * 31) + this.auditStatus;
    }

    @NotNull
    public String toString() {
        return "AppVersion(externalVersion=" + this.externalVersion + ", internalVersion=" + this.internalVersion + ", versionIntroduce=" + this.versionIntroduce + ", status=" + this.status + ", versionUrl=" + this.versionUrl + ", upgradeStatus=" + this.upgradeStatus + ", auditStatus=" + this.auditStatus + ")";
    }

    public AppVersion(@Nullable String str, @Nullable String str2, @Nullable String str3, int i2, @Nullable String str4, int i3, int i4) {
        this.externalVersion = str;
        this.internalVersion = str2;
        this.versionIntroduce = str3;
        this.status = i2;
        this.versionUrl = str4;
        this.upgradeStatus = i3;
        this.auditStatus = i4;
    }

    public /* synthetic */ AppVersion(String str, String str2, String str3, int i2, String str4, int i3, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? "" : str, (i5 & 2) != 0 ? "" : str2, (i5 & 4) != 0 ? "" : str3, (i5 & 8) != 0 ? 0 : i2, (i5 & 16) == 0 ? str4 : "", (i5 & 32) != 0 ? 0 : i3, (i5 & 64) != 0 ? 0 : i4);
    }
}

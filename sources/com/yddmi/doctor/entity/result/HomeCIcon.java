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
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 02\u00020\u0001:\u0002/0B_\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eBY\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0011J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0011J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0010\u0010 \u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0011Jb\u0010!\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010\"J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010&\u001a\u00020\u0003HÖ\u0001J\t\u0010'\u001a\u00020\u0006HÖ\u0001J!\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u00002\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.HÇ\u0001R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0015\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0017\u0010\u0011R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014R\u0015\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0019\u0010\u0011¨\u00061"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeCIcon;", "", "seen1", "", "appType", "diamondId", "", "iconUrl", "name", "status", "url", "urlType", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;)V", "getAppType", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getDiamondId", "()Ljava/lang/String;", "getIconUrl", "getName", "getStatus", "getUrl", "getUrlType", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;)Lcom/yddmi/doctor/entity/result/HomeCIcon;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeCIcon {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final Integer appType;

    @Nullable
    private final String diamondId;

    @Nullable
    private final String iconUrl;

    @Nullable
    private final String name;

    @Nullable
    private final Integer status;

    @Nullable
    private final String url;

    @Nullable
    private final Integer urlType;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeCIcon$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/HomeCIcon;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeCIcon> serializer() {
            return HomeCIcon$$serializer.INSTANCE;
        }
    }

    public HomeCIcon() {
        this((Integer) null, (String) null, (String) null, (String) null, (Integer) null, (String) null, (Integer) null, 127, (DefaultConstructorMarker) null);
    }

    public HomeCIcon(@Nullable Integer num, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable Integer num2, @Nullable String str4, @Nullable Integer num3) {
        this.appType = num;
        this.diamondId = str;
        this.iconUrl = str2;
        this.name = str3;
        this.status = num2;
        this.url = str4;
        this.urlType = num3;
    }

    public static /* synthetic */ HomeCIcon copy$default(HomeCIcon homeCIcon, Integer num, String str, String str2, String str3, Integer num2, String str4, Integer num3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            num = homeCIcon.appType;
        }
        if ((i2 & 2) != 0) {
            str = homeCIcon.diamondId;
        }
        String str5 = str;
        if ((i2 & 4) != 0) {
            str2 = homeCIcon.iconUrl;
        }
        String str6 = str2;
        if ((i2 & 8) != 0) {
            str3 = homeCIcon.name;
        }
        String str7 = str3;
        if ((i2 & 16) != 0) {
            num2 = homeCIcon.status;
        }
        Integer num4 = num2;
        if ((i2 & 32) != 0) {
            str4 = homeCIcon.url;
        }
        String str8 = str4;
        if ((i2 & 64) != 0) {
            num3 = homeCIcon.urlType;
        }
        return homeCIcon.copy(num, str5, str6, str7, num4, str8, num3);
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeCIcon self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Integer num3;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || (num = self.appType) == null || num.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 0, IntSerializer.INSTANCE, self.appType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.diamondId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.diamondId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.iconUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.iconUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || (num2 = self.status) == null || num2.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 4, IntSerializer.INSTANCE, self.status);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.url, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.url);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || (num3 = self.urlType) == null || num3.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 6, IntSerializer.INSTANCE, self.urlType);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Integer getAppType() {
        return this.appType;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getDiamondId() {
        return this.diamondId;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getIconUrl() {
        return this.iconUrl;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final Integer getStatus() {
        return this.status;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getUrl() {
        return this.url;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final Integer getUrlType() {
        return this.urlType;
    }

    @NotNull
    public final HomeCIcon copy(@Nullable Integer appType, @Nullable String diamondId, @Nullable String iconUrl, @Nullable String name, @Nullable Integer status, @Nullable String url, @Nullable Integer urlType) {
        return new HomeCIcon(appType, diamondId, iconUrl, name, status, url, urlType);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeCIcon)) {
            return false;
        }
        HomeCIcon homeCIcon = (HomeCIcon) other;
        return Intrinsics.areEqual(this.appType, homeCIcon.appType) && Intrinsics.areEqual(this.diamondId, homeCIcon.diamondId) && Intrinsics.areEqual(this.iconUrl, homeCIcon.iconUrl) && Intrinsics.areEqual(this.name, homeCIcon.name) && Intrinsics.areEqual(this.status, homeCIcon.status) && Intrinsics.areEqual(this.url, homeCIcon.url) && Intrinsics.areEqual(this.urlType, homeCIcon.urlType);
    }

    @Nullable
    public final Integer getAppType() {
        return this.appType;
    }

    @Nullable
    public final String getDiamondId() {
        return this.diamondId;
    }

    @Nullable
    public final String getIconUrl() {
        return this.iconUrl;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final Integer getStatus() {
        return this.status;
    }

    @Nullable
    public final String getUrl() {
        return this.url;
    }

    @Nullable
    public final Integer getUrlType() {
        return this.urlType;
    }

    public int hashCode() {
        Integer num = this.appType;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        String str = this.diamondId;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.iconUrl;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.name;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        Integer num2 = this.status;
        int iHashCode5 = (iHashCode4 + (num2 == null ? 0 : num2.hashCode())) * 31;
        String str4 = this.url;
        int iHashCode6 = (iHashCode5 + (str4 == null ? 0 : str4.hashCode())) * 31;
        Integer num3 = this.urlType;
        return iHashCode6 + (num3 != null ? num3.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "HomeCIcon(appType=" + this.appType + ", diamondId=" + this.diamondId + ", iconUrl=" + this.iconUrl + ", name=" + this.name + ", status=" + this.status + ", url=" + this.url + ", urlType=" + this.urlType + ")";
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeCIcon(int i2, Integer num, String str, String str2, String str3, Integer num2, String str4, Integer num3, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, HomeCIcon$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.appType = 0;
        } else {
            this.appType = num;
        }
        if ((i2 & 2) == 0) {
            this.diamondId = "";
        } else {
            this.diamondId = str;
        }
        if ((i2 & 4) == 0) {
            this.iconUrl = "";
        } else {
            this.iconUrl = str2;
        }
        if ((i2 & 8) == 0) {
            this.name = "";
        } else {
            this.name = str3;
        }
        if ((i2 & 16) == 0) {
            this.status = 0;
        } else {
            this.status = num2;
        }
        if ((i2 & 32) == 0) {
            this.url = "";
        } else {
            this.url = str4;
        }
        if ((i2 & 64) == 0) {
            this.urlType = -1;
        } else {
            this.urlType = num3;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ HomeCIcon(Integer num, String str, String str2, String str3, Integer num2, String str4, Integer num3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? num : num, (i2 & 2) != 0 ? "" : str, (i2 & 4) != 0 ? "" : str2, (i2 & 8) != 0 ? "" : str3, (i2 & 16) == 0 ? num2 : 0, (i2 & 32) == 0 ? str4 : "", (i2 & 64) != 0 ? -1 : num3);
    }
}

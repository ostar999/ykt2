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
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 02\u00020\u0001:\u0002/0B_\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eBY\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0011J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0006HÆ\u0003Jb\u0010!\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0006HÆ\u0001¢\u0006\u0002\u0010\"J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010&\u001a\u00020\u0003HÖ\u0001J\t\u0010'\u001a\u00020\u0006HÖ\u0001J!\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u00002\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.HÇ\u0001R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0014R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0014¨\u00061"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeCPorcelain;", "", "seen1", "", "appType", "contentType", "", "imageUrl", "name", "part", "porcelainId", "url", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAppType", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getContentType", "()Ljava/lang/String;", "getImageUrl", "getName", "getPart", "getPorcelainId", "getUrl", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/yddmi/doctor/entity/result/HomeCPorcelain;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeCPorcelain {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final Integer appType;

    @Nullable
    private final String contentType;

    @Nullable
    private final String imageUrl;

    @Nullable
    private final String name;

    @Nullable
    private final String part;

    @Nullable
    private final String porcelainId;

    @Nullable
    private final String url;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeCPorcelain$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/HomeCPorcelain;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeCPorcelain> serializer() {
            return HomeCPorcelain$$serializer.INSTANCE;
        }
    }

    public HomeCPorcelain() {
        this((Integer) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, 127, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeCPorcelain(int i2, Integer num, String str, String str2, String str3, String str4, String str5, String str6, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, HomeCPorcelain$$serializer.INSTANCE.getDescriptor());
        }
        this.appType = (i2 & 1) == 0 ? 0 : num;
        if ((i2 & 2) == 0) {
            this.contentType = "";
        } else {
            this.contentType = str;
        }
        if ((i2 & 4) == 0) {
            this.imageUrl = "";
        } else {
            this.imageUrl = str2;
        }
        if ((i2 & 8) == 0) {
            this.name = "";
        } else {
            this.name = str3;
        }
        if ((i2 & 16) == 0) {
            this.part = "";
        } else {
            this.part = str4;
        }
        if ((i2 & 32) == 0) {
            this.porcelainId = "";
        } else {
            this.porcelainId = str5;
        }
        if ((i2 & 64) == 0) {
            this.url = "";
        } else {
            this.url = str6;
        }
    }

    public static /* synthetic */ HomeCPorcelain copy$default(HomeCPorcelain homeCPorcelain, Integer num, String str, String str2, String str3, String str4, String str5, String str6, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            num = homeCPorcelain.appType;
        }
        if ((i2 & 2) != 0) {
            str = homeCPorcelain.contentType;
        }
        String str7 = str;
        if ((i2 & 4) != 0) {
            str2 = homeCPorcelain.imageUrl;
        }
        String str8 = str2;
        if ((i2 & 8) != 0) {
            str3 = homeCPorcelain.name;
        }
        String str9 = str3;
        if ((i2 & 16) != 0) {
            str4 = homeCPorcelain.part;
        }
        String str10 = str4;
        if ((i2 & 32) != 0) {
            str5 = homeCPorcelain.porcelainId;
        }
        String str11 = str5;
        if ((i2 & 64) != 0) {
            str6 = homeCPorcelain.url;
        }
        return homeCPorcelain.copy(num, str7, str8, str9, str10, str11, str6);
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeCPorcelain self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || (num = self.appType) == null || num.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 0, IntSerializer.INSTANCE, self.appType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.contentType, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.contentType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.imageUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.imageUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.part, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.part);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.porcelainId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.porcelainId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.url, "")) {
            output.encodeNullableSerializableElement(serialDesc, 6, StringSerializer.INSTANCE, self.url);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Integer getAppType() {
        return this.appType;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getContentType() {
        return this.contentType;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getImageUrl() {
        return this.imageUrl;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getPart() {
        return this.part;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getPorcelainId() {
        return this.porcelainId;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getUrl() {
        return this.url;
    }

    @NotNull
    public final HomeCPorcelain copy(@Nullable Integer appType, @Nullable String contentType, @Nullable String imageUrl, @Nullable String name, @Nullable String part, @Nullable String porcelainId, @Nullable String url) {
        return new HomeCPorcelain(appType, contentType, imageUrl, name, part, porcelainId, url);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeCPorcelain)) {
            return false;
        }
        HomeCPorcelain homeCPorcelain = (HomeCPorcelain) other;
        return Intrinsics.areEqual(this.appType, homeCPorcelain.appType) && Intrinsics.areEqual(this.contentType, homeCPorcelain.contentType) && Intrinsics.areEqual(this.imageUrl, homeCPorcelain.imageUrl) && Intrinsics.areEqual(this.name, homeCPorcelain.name) && Intrinsics.areEqual(this.part, homeCPorcelain.part) && Intrinsics.areEqual(this.porcelainId, homeCPorcelain.porcelainId) && Intrinsics.areEqual(this.url, homeCPorcelain.url);
    }

    @Nullable
    public final Integer getAppType() {
        return this.appType;
    }

    @Nullable
    public final String getContentType() {
        return this.contentType;
    }

    @Nullable
    public final String getImageUrl() {
        return this.imageUrl;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final String getPart() {
        return this.part;
    }

    @Nullable
    public final String getPorcelainId() {
        return this.porcelainId;
    }

    @Nullable
    public final String getUrl() {
        return this.url;
    }

    public int hashCode() {
        Integer num = this.appType;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        String str = this.contentType;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.imageUrl;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.name;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.part;
        int iHashCode5 = (iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.porcelainId;
        int iHashCode6 = (iHashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.url;
        return iHashCode6 + (str6 != null ? str6.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "HomeCPorcelain(appType=" + this.appType + ", contentType=" + this.contentType + ", imageUrl=" + this.imageUrl + ", name=" + this.name + ", part=" + this.part + ", porcelainId=" + this.porcelainId + ", url=" + this.url + ")";
    }

    public HomeCPorcelain(@Nullable Integer num, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6) {
        this.appType = num;
        this.contentType = str;
        this.imageUrl = str2;
        this.name = str3;
        this.part = str4;
        this.porcelainId = str5;
        this.url = str6;
    }

    public /* synthetic */ HomeCPorcelain(Integer num, String str, String str2, String str3, String str4, String str5, String str6, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : num, (i2 & 2) != 0 ? "" : str, (i2 & 4) != 0 ? "" : str2, (i2 & 8) != 0 ? "" : str3, (i2 & 16) != 0 ? "" : str4, (i2 & 32) != 0 ? "" : str5, (i2 & 64) == 0 ? str6 : "");
    }
}

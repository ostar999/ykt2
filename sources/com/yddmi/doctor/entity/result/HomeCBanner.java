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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 12\u00020\u0001:\u000201Ba\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fB]\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003¢\u0006\u0002\u0010\u0010J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010 \u001a\u00020\u0003HÆ\u0003J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\t\u0010\"\u001a\u00020\u0003HÆ\u0003Ja\u0010#\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\b\u001a\u00020\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u0003HÆ\u0001J\u0013\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010'\u001a\u00020\u0003HÖ\u0001J\t\u0010(\u001a\u00020\u0005HÖ\u0001J!\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u00002\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/HÇ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012R\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u0011\u0010\u000b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u0011\u0010\f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016¨\u00062"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeCBanner;", "", "seen1", "", "bannerPhotoName", "", "bannerPhotoUrl", "bannerTitle", "bannerType", "content", "contentType", "pageTurns", "status", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;IIILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;III)V", "getBannerPhotoName", "()Ljava/lang/String;", "getBannerPhotoUrl", "getBannerTitle", "getBannerType", "()I", "getContent", "getContentType", "getPageTurns", "getStatus", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeCBanner {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final String bannerPhotoName;

    @Nullable
    private final String bannerPhotoUrl;

    @Nullable
    private final String bannerTitle;
    private final int bannerType;

    @Nullable
    private final String content;
    private final int contentType;
    private final int pageTurns;
    private final int status;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeCBanner$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/HomeCBanner;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeCBanner> serializer() {
            return HomeCBanner$$serializer.INSTANCE;
        }
    }

    public HomeCBanner() {
        this((String) null, (String) null, (String) null, 0, (String) null, 0, 0, 0, 255, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeCBanner(int i2, String str, String str2, String str3, int i3, String str4, int i4, int i5, int i6, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, HomeCBanner$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.bannerPhotoName = "";
        } else {
            this.bannerPhotoName = str;
        }
        if ((i2 & 2) == 0) {
            this.bannerPhotoUrl = "";
        } else {
            this.bannerPhotoUrl = str2;
        }
        if ((i2 & 4) == 0) {
            this.bannerTitle = "";
        } else {
            this.bannerTitle = str3;
        }
        if ((i2 & 8) == 0) {
            this.bannerType = -1;
        } else {
            this.bannerType = i3;
        }
        if ((i2 & 16) == 0) {
            this.content = "";
        } else {
            this.content = str4;
        }
        if ((i2 & 32) == 0) {
            this.contentType = -1;
        } else {
            this.contentType = i4;
        }
        if ((i2 & 64) == 0) {
            this.pageTurns = -1;
        } else {
            this.pageTurns = i5;
        }
        if ((i2 & 128) == 0) {
            this.status = -1;
        } else {
            this.status = i6;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeCBanner self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.bannerPhotoName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.bannerPhotoName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.bannerPhotoUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.bannerPhotoUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.bannerTitle, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.bannerTitle);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.bannerType != -1) {
            output.encodeIntElement(serialDesc, 3, self.bannerType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.content, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.content);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || self.contentType != -1) {
            output.encodeIntElement(serialDesc, 5, self.contentType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || self.pageTurns != -1) {
            output.encodeIntElement(serialDesc, 6, self.pageTurns);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || self.status != -1) {
            output.encodeIntElement(serialDesc, 7, self.status);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getBannerPhotoName() {
        return this.bannerPhotoName;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getBannerPhotoUrl() {
        return this.bannerPhotoUrl;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getBannerTitle() {
        return this.bannerTitle;
    }

    /* renamed from: component4, reason: from getter */
    public final int getBannerType() {
        return this.bannerType;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getContent() {
        return this.content;
    }

    /* renamed from: component6, reason: from getter */
    public final int getContentType() {
        return this.contentType;
    }

    /* renamed from: component7, reason: from getter */
    public final int getPageTurns() {
        return this.pageTurns;
    }

    /* renamed from: component8, reason: from getter */
    public final int getStatus() {
        return this.status;
    }

    @NotNull
    public final HomeCBanner copy(@Nullable String bannerPhotoName, @Nullable String bannerPhotoUrl, @Nullable String bannerTitle, int bannerType, @Nullable String content, int contentType, int pageTurns, int status) {
        return new HomeCBanner(bannerPhotoName, bannerPhotoUrl, bannerTitle, bannerType, content, contentType, pageTurns, status);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeCBanner)) {
            return false;
        }
        HomeCBanner homeCBanner = (HomeCBanner) other;
        return Intrinsics.areEqual(this.bannerPhotoName, homeCBanner.bannerPhotoName) && Intrinsics.areEqual(this.bannerPhotoUrl, homeCBanner.bannerPhotoUrl) && Intrinsics.areEqual(this.bannerTitle, homeCBanner.bannerTitle) && this.bannerType == homeCBanner.bannerType && Intrinsics.areEqual(this.content, homeCBanner.content) && this.contentType == homeCBanner.contentType && this.pageTurns == homeCBanner.pageTurns && this.status == homeCBanner.status;
    }

    @Nullable
    public final String getBannerPhotoName() {
        return this.bannerPhotoName;
    }

    @Nullable
    public final String getBannerPhotoUrl() {
        return this.bannerPhotoUrl;
    }

    @Nullable
    public final String getBannerTitle() {
        return this.bannerTitle;
    }

    public final int getBannerType() {
        return this.bannerType;
    }

    @Nullable
    public final String getContent() {
        return this.content;
    }

    public final int getContentType() {
        return this.contentType;
    }

    public final int getPageTurns() {
        return this.pageTurns;
    }

    public final int getStatus() {
        return this.status;
    }

    public int hashCode() {
        String str = this.bannerPhotoName;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.bannerPhotoUrl;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.bannerTitle;
        int iHashCode3 = (((iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31) + this.bannerType) * 31;
        String str4 = this.content;
        return ((((((iHashCode3 + (str4 != null ? str4.hashCode() : 0)) * 31) + this.contentType) * 31) + this.pageTurns) * 31) + this.status;
    }

    @NotNull
    public String toString() {
        return "HomeCBanner(bannerPhotoName=" + this.bannerPhotoName + ", bannerPhotoUrl=" + this.bannerPhotoUrl + ", bannerTitle=" + this.bannerTitle + ", bannerType=" + this.bannerType + ", content=" + this.content + ", contentType=" + this.contentType + ", pageTurns=" + this.pageTurns + ", status=" + this.status + ")";
    }

    public HomeCBanner(@Nullable String str, @Nullable String str2, @Nullable String str3, int i2, @Nullable String str4, int i3, int i4, int i5) {
        this.bannerPhotoName = str;
        this.bannerPhotoUrl = str2;
        this.bannerTitle = str3;
        this.bannerType = i2;
        this.content = str4;
        this.contentType = i3;
        this.pageTurns = i4;
        this.status = i5;
    }

    public /* synthetic */ HomeCBanner(String str, String str2, String str3, int i2, String str4, int i3, int i4, int i5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this((i6 & 1) != 0 ? "" : str, (i6 & 2) != 0 ? "" : str2, (i6 & 4) != 0 ? "" : str3, (i6 & 8) != 0 ? -1 : i2, (i6 & 16) == 0 ? str4 : "", (i6 & 32) != 0 ? -1 : i3, (i6 & 64) != 0 ? -1 : i4, (i6 & 128) == 0 ? i5 : -1);
    }
}

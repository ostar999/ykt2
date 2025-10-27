package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 42\u00020\u0001:\u000234Bg\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fBc\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0010J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0015J\u000b\u0010 \u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010#\u001a\u00020\u0003HÆ\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0005HÆ\u0003Jl\u0010%\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0001¢\u0006\u0002\u0010&J\u0013\u0010'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010*\u001a\u00020\u0003HÖ\u0001J\t\u0010+\u001a\u00020\u0005HÖ\u0001J!\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\u00002\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u000202HÇ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0015\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0012R\u0011\u0010\u000b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\f\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0012¨\u00065"}, d2 = {"Lcom/yddmi/doctor/entity/result/TeachRow;", "", "seen1", "", SocializeProtocolConstants.AUTHOR, "", "description", "id", "name", "picUrl", "remark", CrashHianalyticsData.TIME, "videoUrl", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V", "getAuthor", "()Ljava/lang/String;", "getDescription", "getId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getName", "getPicUrl", "getRemark", "getTime", "()I", "getVideoUrl", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)Lcom/yddmi/doctor/entity/result/TeachRow;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class TeachRow {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final String author;

    @Nullable
    private final String description;

    @Nullable
    private final Integer id;

    @Nullable
    private final String name;

    @Nullable
    private final String picUrl;

    @Nullable
    private final String remark;
    private final int time;

    @Nullable
    private final String videoUrl;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/TeachRow$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/TeachRow;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<TeachRow> serializer() {
            return TeachRow$$serializer.INSTANCE;
        }
    }

    public TeachRow() {
        this((String) null, (String) null, (Integer) null, (String) null, (String) null, (String) null, 0, (String) null, 255, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ TeachRow(int i2, String str, String str2, Integer num, String str3, String str4, String str5, int i3, String str6, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, TeachRow$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.author = "";
        } else {
            this.author = str;
        }
        if ((i2 & 2) == 0) {
            this.description = "";
        } else {
            this.description = str2;
        }
        if ((i2 & 4) == 0) {
            this.id = -1;
        } else {
            this.id = num;
        }
        if ((i2 & 8) == 0) {
            this.name = "";
        } else {
            this.name = str3;
        }
        if ((i2 & 16) == 0) {
            this.picUrl = "";
        } else {
            this.picUrl = str4;
        }
        if ((i2 & 32) == 0) {
            this.remark = "";
        } else {
            this.remark = str5;
        }
        if ((i2 & 64) == 0) {
            this.time = 0;
        } else {
            this.time = i3;
        }
        if ((i2 & 128) == 0) {
            this.videoUrl = "";
        } else {
            this.videoUrl = str6;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull TeachRow self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.author, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.author);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.description, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.description);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || (num = self.id) == null || num.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 2, IntSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.picUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.picUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.remark, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.remark);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || self.time != 0) {
            output.encodeIntElement(serialDesc, 6, self.time);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || !Intrinsics.areEqual(self.videoUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 7, StringSerializer.INSTANCE, self.videoUrl);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getAuthor() {
        return this.author;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final Integer getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getPicUrl() {
        return this.picUrl;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getRemark() {
        return this.remark;
    }

    /* renamed from: component7, reason: from getter */
    public final int getTime() {
        return this.time;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getVideoUrl() {
        return this.videoUrl;
    }

    @NotNull
    public final TeachRow copy(@Nullable String author, @Nullable String description, @Nullable Integer id, @Nullable String name, @Nullable String picUrl, @Nullable String remark, int time, @Nullable String videoUrl) {
        return new TeachRow(author, description, id, name, picUrl, remark, time, videoUrl);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TeachRow)) {
            return false;
        }
        TeachRow teachRow = (TeachRow) other;
        return Intrinsics.areEqual(this.author, teachRow.author) && Intrinsics.areEqual(this.description, teachRow.description) && Intrinsics.areEqual(this.id, teachRow.id) && Intrinsics.areEqual(this.name, teachRow.name) && Intrinsics.areEqual(this.picUrl, teachRow.picUrl) && Intrinsics.areEqual(this.remark, teachRow.remark) && this.time == teachRow.time && Intrinsics.areEqual(this.videoUrl, teachRow.videoUrl);
    }

    @Nullable
    public final String getAuthor() {
        return this.author;
    }

    @Nullable
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    public final Integer getId() {
        return this.id;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final String getPicUrl() {
        return this.picUrl;
    }

    @Nullable
    public final String getRemark() {
        return this.remark;
    }

    public final int getTime() {
        return this.time;
    }

    @Nullable
    public final String getVideoUrl() {
        return this.videoUrl;
    }

    public int hashCode() {
        String str = this.author;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.description;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        Integer num = this.id;
        int iHashCode3 = (iHashCode2 + (num == null ? 0 : num.hashCode())) * 31;
        String str3 = this.name;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.picUrl;
        int iHashCode5 = (iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.remark;
        int iHashCode6 = (((iHashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31) + this.time) * 31;
        String str6 = this.videoUrl;
        return iHashCode6 + (str6 != null ? str6.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "TeachRow(author=" + this.author + ", description=" + this.description + ", id=" + this.id + ", name=" + this.name + ", picUrl=" + this.picUrl + ", remark=" + this.remark + ", time=" + this.time + ", videoUrl=" + this.videoUrl + ")";
    }

    public TeachRow(@Nullable String str, @Nullable String str2, @Nullable Integer num, @Nullable String str3, @Nullable String str4, @Nullable String str5, int i2, @Nullable String str6) {
        this.author = str;
        this.description = str2;
        this.id = num;
        this.name = str3;
        this.picUrl = str4;
        this.remark = str5;
        this.time = i2;
        this.videoUrl = str6;
    }

    public /* synthetic */ TeachRow(String str, String str2, Integer num, String str3, String str4, String str5, int i2, String str6, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? "" : str, (i3 & 2) != 0 ? "" : str2, (i3 & 4) != 0 ? -1 : num, (i3 & 8) != 0 ? "" : str3, (i3 & 16) != 0 ? "" : str4, (i3 & 32) != 0 ? "" : str5, (i3 & 64) != 0 ? 0 : i2, (i3 & 128) == 0 ? str6 : "");
    }
}

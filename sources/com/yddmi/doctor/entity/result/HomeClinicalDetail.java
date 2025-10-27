package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.yikaobang.yixue.R2;
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
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 :2\u00020\u0001:\u00029:B\u0081\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011¢\u0006\u0002\u0010\u0012B\u0083\u0001\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0013J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010&\u001a\u00020\u0003HÆ\u0003J\u000b\u0010'\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010)\u001a\u00020\u0003HÆ\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010+\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0087\u0001\u0010,\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\b\u001a\u00020\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u000b\u001a\u00020\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0006HÆ\u0001J\u0013\u0010-\u001a\u00020.2\b\u0010/\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00100\u001a\u00020\u0003HÖ\u0001J\t\u00101\u001a\u00020\u0006HÖ\u0001J!\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\u00002\u0006\u00105\u001a\u0002062\u0006\u00107\u001a\u000208HÇ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0017R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0017R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0017R\u0011\u0010\u000b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0015R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0017¨\u0006;"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeClinicalDetail;", "", "seen1", "", "categoryId", "content", "", "course", "id", AliyunLogCommon.Module.PUBLISHER, "title", "type", "updateTime", "releaseTime", "fileName", "fileUrl", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IILjava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(ILjava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCategoryId", "()I", "getContent", "()Ljava/lang/String;", "getCourse", "getFileName", "getFileUrl", "getId", "getPublisher", "getReleaseTime", "getTitle", "getType", "getUpdateTime", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeClinicalDetail {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private final int categoryId;

    @Nullable
    private final String content;

    @Nullable
    private final String course;

    @Nullable
    private final String fileName;

    @Nullable
    private final String fileUrl;
    private final int id;

    @Nullable
    private final String publisher;

    @Nullable
    private final String releaseTime;

    @Nullable
    private final String title;
    private final int type;

    @Nullable
    private final String updateTime;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeClinicalDetail$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/HomeClinicalDetail;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeClinicalDetail> serializer() {
            return HomeClinicalDetail$$serializer.INSTANCE;
        }
    }

    public HomeClinicalDetail() {
        this(0, (String) null, (String) null, 0, (String) null, (String) null, 0, (String) null, (String) null, (String) null, (String) null, R2.attr.indicatorSize, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeClinicalDetail(int i2, int i3, String str, String str2, int i4, String str3, String str4, int i5, String str5, String str6, String str7, String str8, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, HomeClinicalDetail$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.categoryId = -1;
        } else {
            this.categoryId = i3;
        }
        if ((i2 & 2) == 0) {
            this.content = "";
        } else {
            this.content = str;
        }
        if ((i2 & 4) == 0) {
            this.course = "";
        } else {
            this.course = str2;
        }
        if ((i2 & 8) == 0) {
            this.id = -1;
        } else {
            this.id = i4;
        }
        if ((i2 & 16) == 0) {
            this.publisher = "";
        } else {
            this.publisher = str3;
        }
        if ((i2 & 32) == 0) {
            this.title = "";
        } else {
            this.title = str4;
        }
        if ((i2 & 64) == 0) {
            this.type = -1;
        } else {
            this.type = i5;
        }
        if ((i2 & 128) == 0) {
            this.updateTime = "";
        } else {
            this.updateTime = str5;
        }
        if ((i2 & 256) == 0) {
            this.releaseTime = "";
        } else {
            this.releaseTime = str6;
        }
        if ((i2 & 512) == 0) {
            this.fileName = "";
        } else {
            this.fileName = str7;
        }
        if ((i2 & 1024) == 0) {
            this.fileUrl = "";
        } else {
            this.fileUrl = str8;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeClinicalDetail self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.categoryId != -1) {
            output.encodeIntElement(serialDesc, 0, self.categoryId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.content, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.content);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.course, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.course);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.id != -1) {
            output.encodeIntElement(serialDesc, 3, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.publisher, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.publisher);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.title, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.title);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || self.type != -1) {
            output.encodeIntElement(serialDesc, 6, self.type);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || !Intrinsics.areEqual(self.updateTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 7, StringSerializer.INSTANCE, self.updateTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || !Intrinsics.areEqual(self.releaseTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 8, StringSerializer.INSTANCE, self.releaseTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || !Intrinsics.areEqual(self.fileName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 9, StringSerializer.INSTANCE, self.fileName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || !Intrinsics.areEqual(self.fileUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 10, StringSerializer.INSTANCE, self.fileUrl);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getCategoryId() {
        return this.categoryId;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getFileName() {
        return this.fileName;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final String getFileUrl() {
        return this.fileUrl;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getContent() {
        return this.content;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getCourse() {
        return this.course;
    }

    /* renamed from: component4, reason: from getter */
    public final int getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getPublisher() {
        return this.publisher;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    /* renamed from: component7, reason: from getter */
    public final int getType() {
        return this.type;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getUpdateTime() {
        return this.updateTime;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getReleaseTime() {
        return this.releaseTime;
    }

    @NotNull
    public final HomeClinicalDetail copy(int categoryId, @Nullable String content, @Nullable String course, int id, @Nullable String publisher, @Nullable String title, int type, @Nullable String updateTime, @Nullable String releaseTime, @Nullable String fileName, @Nullable String fileUrl) {
        return new HomeClinicalDetail(categoryId, content, course, id, publisher, title, type, updateTime, releaseTime, fileName, fileUrl);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeClinicalDetail)) {
            return false;
        }
        HomeClinicalDetail homeClinicalDetail = (HomeClinicalDetail) other;
        return this.categoryId == homeClinicalDetail.categoryId && Intrinsics.areEqual(this.content, homeClinicalDetail.content) && Intrinsics.areEqual(this.course, homeClinicalDetail.course) && this.id == homeClinicalDetail.id && Intrinsics.areEqual(this.publisher, homeClinicalDetail.publisher) && Intrinsics.areEqual(this.title, homeClinicalDetail.title) && this.type == homeClinicalDetail.type && Intrinsics.areEqual(this.updateTime, homeClinicalDetail.updateTime) && Intrinsics.areEqual(this.releaseTime, homeClinicalDetail.releaseTime) && Intrinsics.areEqual(this.fileName, homeClinicalDetail.fileName) && Intrinsics.areEqual(this.fileUrl, homeClinicalDetail.fileUrl);
    }

    public final int getCategoryId() {
        return this.categoryId;
    }

    @Nullable
    public final String getContent() {
        return this.content;
    }

    @Nullable
    public final String getCourse() {
        return this.course;
    }

    @Nullable
    public final String getFileName() {
        return this.fileName;
    }

    @Nullable
    public final String getFileUrl() {
        return this.fileUrl;
    }

    public final int getId() {
        return this.id;
    }

    @Nullable
    public final String getPublisher() {
        return this.publisher;
    }

    @Nullable
    public final String getReleaseTime() {
        return this.releaseTime;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    public final int getType() {
        return this.type;
    }

    @Nullable
    public final String getUpdateTime() {
        return this.updateTime;
    }

    public int hashCode() {
        int i2 = this.categoryId * 31;
        String str = this.content;
        int iHashCode = (i2 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.course;
        int iHashCode2 = (((iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31) + this.id) * 31;
        String str3 = this.publisher;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.title;
        int iHashCode4 = (((iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31) + this.type) * 31;
        String str5 = this.updateTime;
        int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.releaseTime;
        int iHashCode6 = (iHashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.fileName;
        int iHashCode7 = (iHashCode6 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.fileUrl;
        return iHashCode7 + (str8 != null ? str8.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "HomeClinicalDetail(categoryId=" + this.categoryId + ", content=" + this.content + ", course=" + this.course + ", id=" + this.id + ", publisher=" + this.publisher + ", title=" + this.title + ", type=" + this.type + ", updateTime=" + this.updateTime + ", releaseTime=" + this.releaseTime + ", fileName=" + this.fileName + ", fileUrl=" + this.fileUrl + ")";
    }

    public HomeClinicalDetail(int i2, @Nullable String str, @Nullable String str2, int i3, @Nullable String str3, @Nullable String str4, int i4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable String str8) {
        this.categoryId = i2;
        this.content = str;
        this.course = str2;
        this.id = i3;
        this.publisher = str3;
        this.title = str4;
        this.type = i4;
        this.updateTime = str5;
        this.releaseTime = str6;
        this.fileName = str7;
        this.fileUrl = str8;
    }

    public /* synthetic */ HomeClinicalDetail(int i2, String str, String str2, int i3, String str3, String str4, int i4, String str5, String str6, String str7, String str8, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? -1 : i2, (i5 & 2) != 0 ? "" : str, (i5 & 4) != 0 ? "" : str2, (i5 & 8) != 0 ? -1 : i3, (i5 & 16) != 0 ? "" : str3, (i5 & 32) != 0 ? "" : str4, (i5 & 64) == 0 ? i4 : -1, (i5 & 128) != 0 ? "" : str5, (i5 & 256) != 0 ? "" : str6, (i5 & 512) != 0 ? "" : str7, (i5 & 1024) == 0 ? str8 : "");
    }
}

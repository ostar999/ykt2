package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.aliyun.auth.common.AliyunVodHttpCommon;
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
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b#\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 D2\u00020\u0001:\u0002CDB\u008f\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0011\u001a\u00020\u0003\u0012\u0006\u0010\u0012\u001a\u00020\u0003\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014¢\u0006\u0002\u0010\u0015B\u0095\u0001\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0003¢\u0006\u0002\u0010\u0016J\u0010\u0010(\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0018J\t\u0010)\u001a\u00020\u0003HÆ\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010+\u001a\u00020\u0003HÆ\u0003J\t\u0010,\u001a\u00020\u0003HÆ\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010/\u001a\u00020\u0003HÆ\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u000bHÆ\u0003J\t\u00102\u001a\u00020\u0003HÆ\u0003J\t\u00103\u001a\u00020\u0003HÆ\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u009e\u0001\u00105\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\b\u001a\u00020\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u0003HÆ\u0001¢\u0006\u0002\u00106J\u0013\u00107\u001a\u0002082\b\u00109\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010:\u001a\u00020\u0003HÖ\u0001J\t\u0010;\u001a\u00020\u0006HÖ\u0001J!\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020\u00002\u0006\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020BHÇ\u0001R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0019\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001bR\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001eR\u0011\u0010\r\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u001eR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001bR\u0011\u0010\u000f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001eR\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001bR\u0011\u0010\u0011\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001eR\u0011\u0010\u0012\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u001e¨\u0006E"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeTeachingClassItem;", "", "seen1", "", "categoryId", AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, "", "createTime", "createUser", "description", "fileList", "Lcom/yddmi/doctor/entity/result/FileList;", "id", "isDelete", "name", "status", "updateTime", "updateUser", "version", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Lcom/yddmi/doctor/entity/result/FileList;IILjava/lang/String;ILjava/lang/String;IILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Lcom/yddmi/doctor/entity/result/FileList;IILjava/lang/String;ILjava/lang/String;II)V", "getCategoryId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getCover", "()Ljava/lang/String;", "getCreateTime", "getCreateUser", "()I", "getDescription", "getFileList", "()Lcom/yddmi/doctor/entity/result/FileList;", "getId", "getName", "getStatus", "getUpdateTime", "getUpdateUser", "getVersion", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Lcom/yddmi/doctor/entity/result/FileList;IILjava/lang/String;ILjava/lang/String;II)Lcom/yddmi/doctor/entity/result/HomeTeachingClassItem;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeTeachingClassItem {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final Integer categoryId;

    @Nullable
    private final String cover;

    @Nullable
    private final String createTime;
    private final int createUser;

    @Nullable
    private final String description;

    @Nullable
    private final FileList fileList;
    private final int id;
    private final int isDelete;

    @Nullable
    private final String name;
    private final int status;

    @Nullable
    private final String updateTime;
    private final int updateUser;
    private final int version;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeTeachingClassItem$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/HomeTeachingClassItem;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeTeachingClassItem> serializer() {
            return HomeTeachingClassItem$$serializer.INSTANCE;
        }
    }

    public HomeTeachingClassItem() {
        this((Integer) null, (String) null, (String) null, 0, (String) null, (FileList) null, 0, 0, (String) null, 0, (String) null, 0, 0, R2.dimen.preference_seekbar_padding_start, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeTeachingClassItem(int i2, Integer num, String str, String str2, int i3, String str3, FileList fileList, int i4, int i5, String str4, int i6, String str5, int i7, int i8, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, HomeTeachingClassItem$$serializer.INSTANCE.getDescriptor());
        }
        this.categoryId = (i2 & 1) == 0 ? -1 : num;
        if ((i2 & 2) == 0) {
            this.cover = "";
        } else {
            this.cover = str;
        }
        if ((i2 & 4) == 0) {
            this.createTime = "";
        } else {
            this.createTime = str2;
        }
        if ((i2 & 8) == 0) {
            this.createUser = -1;
        } else {
            this.createUser = i3;
        }
        if ((i2 & 16) == 0) {
            this.description = "";
        } else {
            this.description = str3;
        }
        this.fileList = (i2 & 32) == 0 ? null : fileList;
        if ((i2 & 64) == 0) {
            this.id = -1;
        } else {
            this.id = i4;
        }
        if ((i2 & 128) == 0) {
            this.isDelete = -1;
        } else {
            this.isDelete = i5;
        }
        if ((i2 & 256) == 0) {
            this.name = "";
        } else {
            this.name = str4;
        }
        if ((i2 & 512) == 0) {
            this.status = -1;
        } else {
            this.status = i6;
        }
        if ((i2 & 1024) == 0) {
            this.updateTime = "";
        } else {
            this.updateTime = str5;
        }
        if ((i2 & 2048) == 0) {
            this.updateUser = -1;
        } else {
            this.updateUser = i7;
        }
        if ((i2 & 4096) == 0) {
            this.version = -1;
        } else {
            this.version = i8;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeTeachingClassItem self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || (num = self.categoryId) == null || num.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 0, IntSerializer.INSTANCE, self.categoryId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.cover, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.cover);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.createTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.createTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.createUser != -1) {
            output.encodeIntElement(serialDesc, 3, self.createUser);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.description, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.description);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || self.fileList != null) {
            output.encodeNullableSerializableElement(serialDesc, 5, FileList$$serializer.INSTANCE, self.fileList);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || self.id != -1) {
            output.encodeIntElement(serialDesc, 6, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || self.isDelete != -1) {
            output.encodeIntElement(serialDesc, 7, self.isDelete);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 8, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || self.status != -1) {
            output.encodeIntElement(serialDesc, 9, self.status);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || !Intrinsics.areEqual(self.updateTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 10, StringSerializer.INSTANCE, self.updateTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || self.updateUser != -1) {
            output.encodeIntElement(serialDesc, 11, self.updateUser);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 12) || self.version != -1) {
            output.encodeIntElement(serialDesc, 12, self.version);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Integer getCategoryId() {
        return this.categoryId;
    }

    /* renamed from: component10, reason: from getter */
    public final int getStatus() {
        return this.status;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final String getUpdateTime() {
        return this.updateTime;
    }

    /* renamed from: component12, reason: from getter */
    public final int getUpdateUser() {
        return this.updateUser;
    }

    /* renamed from: component13, reason: from getter */
    public final int getVersion() {
        return this.version;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getCover() {
        return this.cover;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getCreateTime() {
        return this.createTime;
    }

    /* renamed from: component4, reason: from getter */
    public final int getCreateUser() {
        return this.createUser;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final FileList getFileList() {
        return this.fileList;
    }

    /* renamed from: component7, reason: from getter */
    public final int getId() {
        return this.id;
    }

    /* renamed from: component8, reason: from getter */
    public final int getIsDelete() {
        return this.isDelete;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final HomeTeachingClassItem copy(@Nullable Integer categoryId, @Nullable String cover, @Nullable String createTime, int createUser, @Nullable String description, @Nullable FileList fileList, int id, int isDelete, @Nullable String name, int status, @Nullable String updateTime, int updateUser, int version) {
        return new HomeTeachingClassItem(categoryId, cover, createTime, createUser, description, fileList, id, isDelete, name, status, updateTime, updateUser, version);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeTeachingClassItem)) {
            return false;
        }
        HomeTeachingClassItem homeTeachingClassItem = (HomeTeachingClassItem) other;
        return Intrinsics.areEqual(this.categoryId, homeTeachingClassItem.categoryId) && Intrinsics.areEqual(this.cover, homeTeachingClassItem.cover) && Intrinsics.areEqual(this.createTime, homeTeachingClassItem.createTime) && this.createUser == homeTeachingClassItem.createUser && Intrinsics.areEqual(this.description, homeTeachingClassItem.description) && Intrinsics.areEqual(this.fileList, homeTeachingClassItem.fileList) && this.id == homeTeachingClassItem.id && this.isDelete == homeTeachingClassItem.isDelete && Intrinsics.areEqual(this.name, homeTeachingClassItem.name) && this.status == homeTeachingClassItem.status && Intrinsics.areEqual(this.updateTime, homeTeachingClassItem.updateTime) && this.updateUser == homeTeachingClassItem.updateUser && this.version == homeTeachingClassItem.version;
    }

    @Nullable
    public final Integer getCategoryId() {
        return this.categoryId;
    }

    @Nullable
    public final String getCover() {
        return this.cover;
    }

    @Nullable
    public final String getCreateTime() {
        return this.createTime;
    }

    public final int getCreateUser() {
        return this.createUser;
    }

    @Nullable
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    public final FileList getFileList() {
        return this.fileList;
    }

    public final int getId() {
        return this.id;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    public final int getStatus() {
        return this.status;
    }

    @Nullable
    public final String getUpdateTime() {
        return this.updateTime;
    }

    public final int getUpdateUser() {
        return this.updateUser;
    }

    public final int getVersion() {
        return this.version;
    }

    public int hashCode() {
        Integer num = this.categoryId;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        String str = this.cover;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.createTime;
        int iHashCode3 = (((iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31) + this.createUser) * 31;
        String str3 = this.description;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        FileList fileList = this.fileList;
        int iHashCode5 = (((((iHashCode4 + (fileList == null ? 0 : fileList.hashCode())) * 31) + this.id) * 31) + this.isDelete) * 31;
        String str4 = this.name;
        int iHashCode6 = (((iHashCode5 + (str4 == null ? 0 : str4.hashCode())) * 31) + this.status) * 31;
        String str5 = this.updateTime;
        return ((((iHashCode6 + (str5 != null ? str5.hashCode() : 0)) * 31) + this.updateUser) * 31) + this.version;
    }

    public final int isDelete() {
        return this.isDelete;
    }

    @NotNull
    public String toString() {
        return "HomeTeachingClassItem(categoryId=" + this.categoryId + ", cover=" + this.cover + ", createTime=" + this.createTime + ", createUser=" + this.createUser + ", description=" + this.description + ", fileList=" + this.fileList + ", id=" + this.id + ", isDelete=" + this.isDelete + ", name=" + this.name + ", status=" + this.status + ", updateTime=" + this.updateTime + ", updateUser=" + this.updateUser + ", version=" + this.version + ")";
    }

    public HomeTeachingClassItem(@Nullable Integer num, @Nullable String str, @Nullable String str2, int i2, @Nullable String str3, @Nullable FileList fileList, int i3, int i4, @Nullable String str4, int i5, @Nullable String str5, int i6, int i7) {
        this.categoryId = num;
        this.cover = str;
        this.createTime = str2;
        this.createUser = i2;
        this.description = str3;
        this.fileList = fileList;
        this.id = i3;
        this.isDelete = i4;
        this.name = str4;
        this.status = i5;
        this.updateTime = str5;
        this.updateUser = i6;
        this.version = i7;
    }

    public /* synthetic */ HomeTeachingClassItem(Integer num, String str, String str2, int i2, String str3, FileList fileList, int i3, int i4, String str4, int i5, String str5, int i6, int i7, int i8, DefaultConstructorMarker defaultConstructorMarker) {
        this((i8 & 1) != 0 ? -1 : num, (i8 & 2) != 0 ? "" : str, (i8 & 4) != 0 ? "" : str2, (i8 & 8) != 0 ? -1 : i2, (i8 & 16) != 0 ? "" : str3, (i8 & 32) != 0 ? null : fileList, (i8 & 64) != 0 ? -1 : i3, (i8 & 128) != 0 ? -1 : i4, (i8 & 256) != 0 ? "" : str4, (i8 & 512) != 0 ? -1 : i5, (i8 & 1024) == 0 ? str5 : "", (i8 & 2048) != 0 ? -1 : i6, (i8 & 4096) == 0 ? i7 : -1);
    }
}

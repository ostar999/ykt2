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
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b&\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 H2\u00020\u0001:\u0002GHB\u009d\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0011\u001a\u00020\u0003\u0012\u0006\u0010\u0012\u001a\u00020\u0003\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015¢\u0006\u0002\u0010\u0016B£\u0001\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0006\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0017J\u0010\u0010*\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0019J\t\u0010+\u001a\u00020\u0003HÆ\u0003J\t\u0010,\u001a\u00020\u0003HÆ\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010.\u001a\u00020\u0003HÆ\u0003J\t\u0010/\u001a\u00020\u0003HÆ\u0003J\u0010\u00100\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0019J\u000b\u00101\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u00102\u001a\u00020\u0003HÆ\u0003J\t\u00103\u001a\u00020\u0003HÆ\u0003J\t\u00104\u001a\u00020\u0003HÆ\u0003J\t\u00105\u001a\u00020\u0006HÆ\u0003J\t\u00106\u001a\u00020\u0006HÆ\u0003J\t\u00107\u001a\u00020\u0003HÆ\u0003J\t\u00108\u001a\u00020\u0003HÆ\u0003J¬\u0001\u00109\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00062\b\b\u0002\u0010\u000b\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010:J\u0013\u0010;\u001a\u00020<2\b\u0010=\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010>\u001a\u00020\u0003HÖ\u0001J\t\u0010?\u001a\u00020\u0006HÖ\u0001J!\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020\u00002\u0006\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020FHÇ\u0001R\u0015\u0010\u0013\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u001b\u0010\u0019R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u001fR\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u001fR\u0011\u0010\n\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001dR\u0011\u0010\u000b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001dR\u0011\u0010\f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001fR\u0011\u0010\r\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001fR\u0011\u0010\u000e\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001fR\u0011\u0010\u000f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001fR\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u001dR\u0011\u0010\u0011\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001fR\u0011\u0010\u0012\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001f¨\u0006I"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeDaskItem;", "", "seen1", "", "appOrgMenuId", "createTime", "", "createUser", "id", "isDelete", "menuCode", "menuName", "menuType", "orderNum", "orgId", "status", "updateTime", "updateUser", "version", "appOrgMainMenuId", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Integer;Ljava/lang/String;IIILjava/lang/String;Ljava/lang/String;IIIILjava/lang/String;IILjava/lang/Integer;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Integer;Ljava/lang/String;IIILjava/lang/String;Ljava/lang/String;IIIILjava/lang/String;IILjava/lang/Integer;)V", "getAppOrgMainMenuId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getAppOrgMenuId", "getCreateTime", "()Ljava/lang/String;", "getCreateUser", "()I", "getId", "getMenuCode", "getMenuName", "getMenuType", "getOrderNum", "getOrgId", "getStatus", "getUpdateTime", "getUpdateUser", "getVersion", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Integer;Ljava/lang/String;IIILjava/lang/String;Ljava/lang/String;IIIILjava/lang/String;IILjava/lang/Integer;)Lcom/yddmi/doctor/entity/result/HomeDaskItem;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeDaskItem {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final Integer appOrgMainMenuId;

    @Nullable
    private final Integer appOrgMenuId;

    @Nullable
    private final String createTime;
    private final int createUser;
    private final int id;
    private final int isDelete;

    @NotNull
    private final String menuCode;

    @NotNull
    private final String menuName;
    private final int menuType;
    private final int orderNum;
    private final int orgId;
    private final int status;

    @Nullable
    private final String updateTime;
    private final int updateUser;
    private final int version;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeDaskItem$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/HomeDaskItem;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeDaskItem> serializer() {
            return HomeDaskItem$$serializer.INSTANCE;
        }
    }

    public HomeDaskItem() {
        this((Integer) null, (String) null, 0, 0, 0, (String) null, (String) null, 0, 0, 0, 0, (String) null, 0, 0, (Integer) null, 32767, (DefaultConstructorMarker) null);
    }

    public HomeDaskItem(@Nullable Integer num, @Nullable String str, int i2, int i3, int i4, @NotNull String menuCode, @NotNull String menuName, int i5, int i6, int i7, int i8, @Nullable String str2, int i9, int i10, @Nullable Integer num2) {
        Intrinsics.checkNotNullParameter(menuCode, "menuCode");
        Intrinsics.checkNotNullParameter(menuName, "menuName");
        this.appOrgMenuId = num;
        this.createTime = str;
        this.createUser = i2;
        this.id = i3;
        this.isDelete = i4;
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuType = i5;
        this.orderNum = i6;
        this.orgId = i7;
        this.status = i8;
        this.updateTime = str2;
        this.updateUser = i9;
        this.version = i10;
        this.appOrgMainMenuId = num2;
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeDaskItem self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || (num = self.appOrgMenuId) == null || num.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 0, IntSerializer.INSTANCE, self.appOrgMenuId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.createTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.createTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.createUser != -1) {
            output.encodeIntElement(serialDesc, 2, self.createUser);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.id != -1) {
            output.encodeIntElement(serialDesc, 3, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || self.isDelete != -1) {
            output.encodeIntElement(serialDesc, 4, self.isDelete);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.menuCode, "")) {
            output.encodeStringElement(serialDesc, 5, self.menuCode);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.menuName, "")) {
            output.encodeStringElement(serialDesc, 6, self.menuName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || self.menuType != -1) {
            output.encodeIntElement(serialDesc, 7, self.menuType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || self.orderNum != -1) {
            output.encodeIntElement(serialDesc, 8, self.orderNum);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || self.orgId != -1) {
            output.encodeIntElement(serialDesc, 9, self.orgId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || self.status != -1) {
            output.encodeIntElement(serialDesc, 10, self.status);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || !Intrinsics.areEqual(self.updateTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 11, StringSerializer.INSTANCE, self.updateTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 12) || self.updateUser != -1) {
            output.encodeIntElement(serialDesc, 12, self.updateUser);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 13) || self.version != -1) {
            output.encodeIntElement(serialDesc, 13, self.version);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 14) || (num2 = self.appOrgMainMenuId) == null || num2.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 14, IntSerializer.INSTANCE, self.appOrgMainMenuId);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Integer getAppOrgMenuId() {
        return this.appOrgMenuId;
    }

    /* renamed from: component10, reason: from getter */
    public final int getOrgId() {
        return this.orgId;
    }

    /* renamed from: component11, reason: from getter */
    public final int getStatus() {
        return this.status;
    }

    @Nullable
    /* renamed from: component12, reason: from getter */
    public final String getUpdateTime() {
        return this.updateTime;
    }

    /* renamed from: component13, reason: from getter */
    public final int getUpdateUser() {
        return this.updateUser;
    }

    /* renamed from: component14, reason: from getter */
    public final int getVersion() {
        return this.version;
    }

    @Nullable
    /* renamed from: component15, reason: from getter */
    public final Integer getAppOrgMainMenuId() {
        return this.appOrgMainMenuId;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getCreateTime() {
        return this.createTime;
    }

    /* renamed from: component3, reason: from getter */
    public final int getCreateUser() {
        return this.createUser;
    }

    /* renamed from: component4, reason: from getter */
    public final int getId() {
        return this.id;
    }

    /* renamed from: component5, reason: from getter */
    public final int getIsDelete() {
        return this.isDelete;
    }

    @NotNull
    /* renamed from: component6, reason: from getter */
    public final String getMenuCode() {
        return this.menuCode;
    }

    @NotNull
    /* renamed from: component7, reason: from getter */
    public final String getMenuName() {
        return this.menuName;
    }

    /* renamed from: component8, reason: from getter */
    public final int getMenuType() {
        return this.menuType;
    }

    /* renamed from: component9, reason: from getter */
    public final int getOrderNum() {
        return this.orderNum;
    }

    @NotNull
    public final HomeDaskItem copy(@Nullable Integer appOrgMenuId, @Nullable String createTime, int createUser, int id, int isDelete, @NotNull String menuCode, @NotNull String menuName, int menuType, int orderNum, int orgId, int status, @Nullable String updateTime, int updateUser, int version, @Nullable Integer appOrgMainMenuId) {
        Intrinsics.checkNotNullParameter(menuCode, "menuCode");
        Intrinsics.checkNotNullParameter(menuName, "menuName");
        return new HomeDaskItem(appOrgMenuId, createTime, createUser, id, isDelete, menuCode, menuName, menuType, orderNum, orgId, status, updateTime, updateUser, version, appOrgMainMenuId);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeDaskItem)) {
            return false;
        }
        HomeDaskItem homeDaskItem = (HomeDaskItem) other;
        return Intrinsics.areEqual(this.appOrgMenuId, homeDaskItem.appOrgMenuId) && Intrinsics.areEqual(this.createTime, homeDaskItem.createTime) && this.createUser == homeDaskItem.createUser && this.id == homeDaskItem.id && this.isDelete == homeDaskItem.isDelete && Intrinsics.areEqual(this.menuCode, homeDaskItem.menuCode) && Intrinsics.areEqual(this.menuName, homeDaskItem.menuName) && this.menuType == homeDaskItem.menuType && this.orderNum == homeDaskItem.orderNum && this.orgId == homeDaskItem.orgId && this.status == homeDaskItem.status && Intrinsics.areEqual(this.updateTime, homeDaskItem.updateTime) && this.updateUser == homeDaskItem.updateUser && this.version == homeDaskItem.version && Intrinsics.areEqual(this.appOrgMainMenuId, homeDaskItem.appOrgMainMenuId);
    }

    @Nullable
    public final Integer getAppOrgMainMenuId() {
        return this.appOrgMainMenuId;
    }

    @Nullable
    public final Integer getAppOrgMenuId() {
        return this.appOrgMenuId;
    }

    @Nullable
    public final String getCreateTime() {
        return this.createTime;
    }

    public final int getCreateUser() {
        return this.createUser;
    }

    public final int getId() {
        return this.id;
    }

    @NotNull
    public final String getMenuCode() {
        return this.menuCode;
    }

    @NotNull
    public final String getMenuName() {
        return this.menuName;
    }

    public final int getMenuType() {
        return this.menuType;
    }

    public final int getOrderNum() {
        return this.orderNum;
    }

    public final int getOrgId() {
        return this.orgId;
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
        Integer num = this.appOrgMenuId;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        String str = this.createTime;
        int iHashCode2 = (((((((((((((((((((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.createUser) * 31) + this.id) * 31) + this.isDelete) * 31) + this.menuCode.hashCode()) * 31) + this.menuName.hashCode()) * 31) + this.menuType) * 31) + this.orderNum) * 31) + this.orgId) * 31) + this.status) * 31;
        String str2 = this.updateTime;
        int iHashCode3 = (((((iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31) + this.updateUser) * 31) + this.version) * 31;
        Integer num2 = this.appOrgMainMenuId;
        return iHashCode3 + (num2 != null ? num2.hashCode() : 0);
    }

    public final int isDelete() {
        return this.isDelete;
    }

    @NotNull
    public String toString() {
        return "HomeDaskItem(appOrgMenuId=" + this.appOrgMenuId + ", createTime=" + this.createTime + ", createUser=" + this.createUser + ", id=" + this.id + ", isDelete=" + this.isDelete + ", menuCode=" + this.menuCode + ", menuName=" + this.menuName + ", menuType=" + this.menuType + ", orderNum=" + this.orderNum + ", orgId=" + this.orgId + ", status=" + this.status + ", updateTime=" + this.updateTime + ", updateUser=" + this.updateUser + ", version=" + this.version + ", appOrgMainMenuId=" + this.appOrgMainMenuId + ")";
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeDaskItem(int i2, Integer num, String str, int i3, int i4, int i5, String str2, String str3, int i6, int i7, int i8, int i9, String str4, int i10, int i11, Integer num2, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, HomeDaskItem$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.appOrgMenuId = 0;
        } else {
            this.appOrgMenuId = num;
        }
        if ((i2 & 2) == 0) {
            this.createTime = "";
        } else {
            this.createTime = str;
        }
        if ((i2 & 4) == 0) {
            this.createUser = -1;
        } else {
            this.createUser = i3;
        }
        if ((i2 & 8) == 0) {
            this.id = -1;
        } else {
            this.id = i4;
        }
        if ((i2 & 16) == 0) {
            this.isDelete = -1;
        } else {
            this.isDelete = i5;
        }
        if ((i2 & 32) == 0) {
            this.menuCode = "";
        } else {
            this.menuCode = str2;
        }
        if ((i2 & 64) == 0) {
            this.menuName = "";
        } else {
            this.menuName = str3;
        }
        if ((i2 & 128) == 0) {
            this.menuType = -1;
        } else {
            this.menuType = i6;
        }
        if ((i2 & 256) == 0) {
            this.orderNum = -1;
        } else {
            this.orderNum = i7;
        }
        if ((i2 & 512) == 0) {
            this.orgId = -1;
        } else {
            this.orgId = i8;
        }
        if ((i2 & 1024) == 0) {
            this.status = -1;
        } else {
            this.status = i9;
        }
        if ((i2 & 2048) == 0) {
            this.updateTime = "";
        } else {
            this.updateTime = str4;
        }
        if ((i2 & 4096) == 0) {
            this.updateUser = -1;
        } else {
            this.updateUser = i10;
        }
        if ((i2 & 8192) == 0) {
            this.version = -1;
        } else {
            this.version = i11;
        }
        if ((i2 & 16384) == 0) {
            this.appOrgMainMenuId = 0;
        } else {
            this.appOrgMainMenuId = num2;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ HomeDaskItem(Integer num, String str, int i2, int i3, int i4, String str2, String str3, int i5, int i6, int i7, int i8, String str4, int i9, int i10, Integer num2, int i11, DefaultConstructorMarker defaultConstructorMarker) {
        this((i11 & 1) != 0 ? num : num, (i11 & 2) != 0 ? "" : str, (i11 & 4) != 0 ? -1 : i2, (i11 & 8) != 0 ? -1 : i3, (i11 & 16) != 0 ? -1 : i4, (i11 & 32) != 0 ? "" : str2, (i11 & 64) != 0 ? "" : str3, (i11 & 128) != 0 ? -1 : i5, (i11 & 256) != 0 ? -1 : i6, (i11 & 512) != 0 ? -1 : i7, (i11 & 1024) != 0 ? -1 : i8, (i11 & 2048) == 0 ? str4 : "", (i11 & 4096) != 0 ? -1 : i9, (i11 & 8192) != 0 ? -1 : i10, (i11 & 16384) == 0 ? num2 : 0);
    }
}

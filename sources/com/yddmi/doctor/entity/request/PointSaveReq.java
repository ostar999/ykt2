package com.yddmi.doctor.entity.request;

import androidx.annotation.Keep;
import java.util.List;
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
import kotlinx.serialization.internal.ArrayListSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 42\u00020\u0001:\u000234BY\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fBI\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b¢\u0006\u0002\u0010\u0010J\t\u0010 \u001a\u00020\u0005HÆ\u0003J\t\u0010!\u001a\u00020\u0005HÆ\u0003J\t\u0010\"\u001a\u00020\u0005HÆ\u0003J\t\u0010#\u001a\u00020\u0003HÆ\u0003J\t\u0010$\u001a\u00020\u0005HÆ\u0003J\u0011\u0010%\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bHÆ\u0003JM\u0010&\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00052\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bHÆ\u0001J\u0013\u0010'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010*\u001a\u00020\u0003HÖ\u0001J\t\u0010+\u001a\u00020\u0005HÖ\u0001J!\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\u00002\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u000202HÇ\u0001R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0012\"\u0004\b\u0016\u0010\u0014R\"\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0012\"\u0004\b\u001c\u0010\u0014R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\t\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0012¨\u00065"}, d2 = {"Lcom/yddmi/doctor/entity/request/PointSaveReq;", "", "seen1", "", "equipmentBrand", "", "equipmentType", "releaseInfo", "userId", "userName", "operationList", "", "Lcom/yddmi/doctor/entity/request/Operation;", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/util/List;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/util/List;)V", "getEquipmentBrand", "()Ljava/lang/String;", "setEquipmentBrand", "(Ljava/lang/String;)V", "getEquipmentType", "setEquipmentType", "getOperationList", "()Ljava/util/List;", "setOperationList", "(Ljava/util/List;)V", "getReleaseInfo", "setReleaseInfo", "getUserId", "()I", "getUserName", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class PointSaveReq {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private String equipmentBrand;

    @NotNull
    private String equipmentType;

    @Nullable
    private List<Operation> operationList;

    @NotNull
    private String releaseInfo;
    private final int userId;

    @NotNull
    private final String userName;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/PointSaveReq$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/PointSaveReq;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<PointSaveReq> serializer() {
            return PointSaveReq$$serializer.INSTANCE;
        }
    }

    public PointSaveReq() {
        this((String) null, (String) null, (String) null, 0, (String) null, (List) null, 63, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ PointSaveReq(int i2, String str, String str2, String str3, int i3, String str4, List list, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, PointSaveReq$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.equipmentBrand = "";
        } else {
            this.equipmentBrand = str;
        }
        if ((i2 & 2) == 0) {
            this.equipmentType = "";
        } else {
            this.equipmentType = str2;
        }
        if ((i2 & 4) == 0) {
            this.releaseInfo = "";
        } else {
            this.releaseInfo = str3;
        }
        if ((i2 & 8) == 0) {
            this.userId = -1;
        } else {
            this.userId = i3;
        }
        if ((i2 & 16) == 0) {
            this.userName = "";
        } else {
            this.userName = str4;
        }
        if ((i2 & 32) == 0) {
            this.operationList = null;
        } else {
            this.operationList = list;
        }
    }

    public static /* synthetic */ PointSaveReq copy$default(PointSaveReq pointSaveReq, String str, String str2, String str3, int i2, String str4, List list, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = pointSaveReq.equipmentBrand;
        }
        if ((i3 & 2) != 0) {
            str2 = pointSaveReq.equipmentType;
        }
        String str5 = str2;
        if ((i3 & 4) != 0) {
            str3 = pointSaveReq.releaseInfo;
        }
        String str6 = str3;
        if ((i3 & 8) != 0) {
            i2 = pointSaveReq.userId;
        }
        int i4 = i2;
        if ((i3 & 16) != 0) {
            str4 = pointSaveReq.userName;
        }
        String str7 = str4;
        if ((i3 & 32) != 0) {
            list = pointSaveReq.operationList;
        }
        return pointSaveReq.copy(str, str5, str6, i4, str7, list);
    }

    @JvmStatic
    public static final void write$Self(@NotNull PointSaveReq self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.equipmentBrand, "")) {
            output.encodeStringElement(serialDesc, 0, self.equipmentBrand);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.equipmentType, "")) {
            output.encodeStringElement(serialDesc, 1, self.equipmentType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.releaseInfo, "")) {
            output.encodeStringElement(serialDesc, 2, self.releaseInfo);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.userId != -1) {
            output.encodeIntElement(serialDesc, 3, self.userId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.userName, "")) {
            output.encodeStringElement(serialDesc, 4, self.userName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || self.operationList != null) {
            output.encodeNullableSerializableElement(serialDesc, 5, new ArrayListSerializer(Operation$$serializer.INSTANCE), self.operationList);
        }
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getEquipmentBrand() {
        return this.equipmentBrand;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getEquipmentType() {
        return this.equipmentType;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getReleaseInfo() {
        return this.releaseInfo;
    }

    /* renamed from: component4, reason: from getter */
    public final int getUserId() {
        return this.userId;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getUserName() {
        return this.userName;
    }

    @Nullable
    public final List<Operation> component6() {
        return this.operationList;
    }

    @NotNull
    public final PointSaveReq copy(@NotNull String equipmentBrand, @NotNull String equipmentType, @NotNull String releaseInfo, int userId, @NotNull String userName, @Nullable List<Operation> operationList) {
        Intrinsics.checkNotNullParameter(equipmentBrand, "equipmentBrand");
        Intrinsics.checkNotNullParameter(equipmentType, "equipmentType");
        Intrinsics.checkNotNullParameter(releaseInfo, "releaseInfo");
        Intrinsics.checkNotNullParameter(userName, "userName");
        return new PointSaveReq(equipmentBrand, equipmentType, releaseInfo, userId, userName, operationList);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PointSaveReq)) {
            return false;
        }
        PointSaveReq pointSaveReq = (PointSaveReq) other;
        return Intrinsics.areEqual(this.equipmentBrand, pointSaveReq.equipmentBrand) && Intrinsics.areEqual(this.equipmentType, pointSaveReq.equipmentType) && Intrinsics.areEqual(this.releaseInfo, pointSaveReq.releaseInfo) && this.userId == pointSaveReq.userId && Intrinsics.areEqual(this.userName, pointSaveReq.userName) && Intrinsics.areEqual(this.operationList, pointSaveReq.operationList);
    }

    @NotNull
    public final String getEquipmentBrand() {
        return this.equipmentBrand;
    }

    @NotNull
    public final String getEquipmentType() {
        return this.equipmentType;
    }

    @Nullable
    public final List<Operation> getOperationList() {
        return this.operationList;
    }

    @NotNull
    public final String getReleaseInfo() {
        return this.releaseInfo;
    }

    public final int getUserId() {
        return this.userId;
    }

    @NotNull
    public final String getUserName() {
        return this.userName;
    }

    public int hashCode() {
        int iHashCode = ((((((((this.equipmentBrand.hashCode() * 31) + this.equipmentType.hashCode()) * 31) + this.releaseInfo.hashCode()) * 31) + this.userId) * 31) + this.userName.hashCode()) * 31;
        List<Operation> list = this.operationList;
        return iHashCode + (list == null ? 0 : list.hashCode());
    }

    public final void setEquipmentBrand(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.equipmentBrand = str;
    }

    public final void setEquipmentType(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.equipmentType = str;
    }

    public final void setOperationList(@Nullable List<Operation> list) {
        this.operationList = list;
    }

    public final void setReleaseInfo(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.releaseInfo = str;
    }

    @NotNull
    public String toString() {
        return "PointSaveReq(equipmentBrand=" + this.equipmentBrand + ", equipmentType=" + this.equipmentType + ", releaseInfo=" + this.releaseInfo + ", userId=" + this.userId + ", userName=" + this.userName + ", operationList=" + this.operationList + ")";
    }

    public PointSaveReq(@NotNull String equipmentBrand, @NotNull String equipmentType, @NotNull String releaseInfo, int i2, @NotNull String userName, @Nullable List<Operation> list) {
        Intrinsics.checkNotNullParameter(equipmentBrand, "equipmentBrand");
        Intrinsics.checkNotNullParameter(equipmentType, "equipmentType");
        Intrinsics.checkNotNullParameter(releaseInfo, "releaseInfo");
        Intrinsics.checkNotNullParameter(userName, "userName");
        this.equipmentBrand = equipmentBrand;
        this.equipmentType = equipmentType;
        this.releaseInfo = releaseInfo;
        this.userId = i2;
        this.userName = userName;
        this.operationList = list;
    }

    public /* synthetic */ PointSaveReq(String str, String str2, String str3, int i2, String str4, List list, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? "" : str, (i3 & 2) != 0 ? "" : str2, (i3 & 4) != 0 ? "" : str3, (i3 & 8) != 0 ? -1 : i2, (i3 & 16) == 0 ? str4 : "", (i3 & 32) != 0 ? null : list);
    }
}

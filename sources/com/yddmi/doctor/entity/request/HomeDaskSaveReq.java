package com.yddmi.doctor.entity.request;

import androidx.annotation.Keep;
import com.yddmi.doctor.entity.result.HomeDaskItem;
import com.yddmi.doctor.entity.result.HomeDaskItem$$serializer;
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
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 -2\u00020\u0001:\u0002,-BI\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u000e\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007\u0012\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fB9\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007\u0012\u0010\b\u0002\u0010\t\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007¢\u0006\u0002\u0010\rJ\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\u0011\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007HÆ\u0003J\u0011\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007HÆ\u0003JA\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00072\u0010\b\u0002\u0010\t\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\"\u001a\u00020\u0003HÖ\u0001J\t\u0010#\u001a\u00020$HÖ\u0001J!\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u00002\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+HÇ\u0001R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\"\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000f\"\u0004\b\u0017\u0010\u0011R\"\u0010\t\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0013\"\u0004\b\u0019\u0010\u0015¨\u0006."}, d2 = {"Lcom/yddmi/doctor/entity/request/HomeDaskSaveReq;", "", "seen1", "", "orgId", "userId", "orgWorkMenu", "", "Lcom/yddmi/doctor/entity/result/HomeDaskItem;", "userWorkMenu", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IIILjava/util/List;Ljava/util/List;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(IILjava/util/List;Ljava/util/List;)V", "getOrgId", "()I", "setOrgId", "(I)V", "getOrgWorkMenu", "()Ljava/util/List;", "setOrgWorkMenu", "(Ljava/util/List;)V", "getUserId", "setUserId", "getUserWorkMenu", "setUserWorkMenu", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeDaskSaveReq {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private int orgId;

    @Nullable
    private List<HomeDaskItem> orgWorkMenu;
    private int userId;

    @Nullable
    private List<HomeDaskItem> userWorkMenu;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/HomeDaskSaveReq$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/HomeDaskSaveReq;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeDaskSaveReq> serializer() {
            return HomeDaskSaveReq$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeDaskSaveReq(int i2, int i3, int i4, List list, List list2, SerializationConstructorMarker serializationConstructorMarker) {
        if (3 != (i2 & 3)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 3, HomeDaskSaveReq$$serializer.INSTANCE.getDescriptor());
        }
        this.orgId = i3;
        this.userId = i4;
        if ((i2 & 4) == 0) {
            this.orgWorkMenu = null;
        } else {
            this.orgWorkMenu = list;
        }
        if ((i2 & 8) == 0) {
            this.userWorkMenu = null;
        } else {
            this.userWorkMenu = list2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ HomeDaskSaveReq copy$default(HomeDaskSaveReq homeDaskSaveReq, int i2, int i3, List list, List list2, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = homeDaskSaveReq.orgId;
        }
        if ((i4 & 2) != 0) {
            i3 = homeDaskSaveReq.userId;
        }
        if ((i4 & 4) != 0) {
            list = homeDaskSaveReq.orgWorkMenu;
        }
        if ((i4 & 8) != 0) {
            list2 = homeDaskSaveReq.userWorkMenu;
        }
        return homeDaskSaveReq.copy(i2, i3, list, list2);
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeDaskSaveReq self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        output.encodeIntElement(serialDesc, 0, self.orgId);
        output.encodeIntElement(serialDesc, 1, self.userId);
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.orgWorkMenu != null) {
            output.encodeNullableSerializableElement(serialDesc, 2, new ArrayListSerializer(HomeDaskItem$$serializer.INSTANCE), self.orgWorkMenu);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.userWorkMenu != null) {
            output.encodeNullableSerializableElement(serialDesc, 3, new ArrayListSerializer(HomeDaskItem$$serializer.INSTANCE), self.userWorkMenu);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getOrgId() {
        return this.orgId;
    }

    /* renamed from: component2, reason: from getter */
    public final int getUserId() {
        return this.userId;
    }

    @Nullable
    public final List<HomeDaskItem> component3() {
        return this.orgWorkMenu;
    }

    @Nullable
    public final List<HomeDaskItem> component4() {
        return this.userWorkMenu;
    }

    @NotNull
    public final HomeDaskSaveReq copy(int orgId, int userId, @Nullable List<HomeDaskItem> orgWorkMenu, @Nullable List<HomeDaskItem> userWorkMenu) {
        return new HomeDaskSaveReq(orgId, userId, orgWorkMenu, userWorkMenu);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeDaskSaveReq)) {
            return false;
        }
        HomeDaskSaveReq homeDaskSaveReq = (HomeDaskSaveReq) other;
        return this.orgId == homeDaskSaveReq.orgId && this.userId == homeDaskSaveReq.userId && Intrinsics.areEqual(this.orgWorkMenu, homeDaskSaveReq.orgWorkMenu) && Intrinsics.areEqual(this.userWorkMenu, homeDaskSaveReq.userWorkMenu);
    }

    public final int getOrgId() {
        return this.orgId;
    }

    @Nullable
    public final List<HomeDaskItem> getOrgWorkMenu() {
        return this.orgWorkMenu;
    }

    public final int getUserId() {
        return this.userId;
    }

    @Nullable
    public final List<HomeDaskItem> getUserWorkMenu() {
        return this.userWorkMenu;
    }

    public int hashCode() {
        int i2 = ((this.orgId * 31) + this.userId) * 31;
        List<HomeDaskItem> list = this.orgWorkMenu;
        int iHashCode = (i2 + (list == null ? 0 : list.hashCode())) * 31;
        List<HomeDaskItem> list2 = this.userWorkMenu;
        return iHashCode + (list2 != null ? list2.hashCode() : 0);
    }

    public final void setOrgId(int i2) {
        this.orgId = i2;
    }

    public final void setOrgWorkMenu(@Nullable List<HomeDaskItem> list) {
        this.orgWorkMenu = list;
    }

    public final void setUserId(int i2) {
        this.userId = i2;
    }

    public final void setUserWorkMenu(@Nullable List<HomeDaskItem> list) {
        this.userWorkMenu = list;
    }

    @NotNull
    public String toString() {
        return "HomeDaskSaveReq(orgId=" + this.orgId + ", userId=" + this.userId + ", orgWorkMenu=" + this.orgWorkMenu + ", userWorkMenu=" + this.userWorkMenu + ")";
    }

    public HomeDaskSaveReq(int i2, int i3, @Nullable List<HomeDaskItem> list, @Nullable List<HomeDaskItem> list2) {
        this.orgId = i2;
        this.userId = i3;
        this.orgWorkMenu = list;
        this.userWorkMenu = list2;
    }

    public /* synthetic */ HomeDaskSaveReq(int i2, int i3, List list, List list2, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i2, i3, (i4 & 4) != 0 ? null : list, (i4 & 8) != 0 ? null : list2);
    }
}

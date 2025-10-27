package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.huawei.hms.push.constant.RemoteMessageConst;
import java.util.ArrayList;
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
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 *2\u00020\u0001:\u0002)*B_\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0005\u0012\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u0005\u0012\u0014\u0010\u000b\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u0005\u0018\u00010\u0005\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eBM\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005\u0012\u0016\b\u0002\u0010\u000b\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u0005\u0018\u00010\u0005¢\u0006\u0002\u0010\u000fJ\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\b0\u0005HÆ\u0003J\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\n0\u0005HÆ\u0003J\u0017\u0010\u001a\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u0005\u0018\u00010\u0005HÆ\u0003JQ\u0010\u001b\u001a\u00020\u00002\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00052\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00052\u0016\b\u0002\u0010\u000b\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u0005\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÖ\u0001J\t\u0010 \u001a\u00020!HÖ\u0001J!\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00002\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(HÇ\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R(\u0010\u000b\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u0005\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0011\"\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011¨\u0006+"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeCData;", "", "seen1", "", "banner", "", "Lcom/yddmi/doctor/entity/result/HomeCBanner;", "porcelain", "Lcom/yddmi/doctor/entity/result/HomeCPorcelain;", RemoteMessageConst.Notification.ICON, "Lcom/yddmi/doctor/entity/result/HomeCIcon;", "iconBanner", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getBanner", "()Ljava/util/List;", "getIcon", "getIconBanner", "setIconBanner", "(Ljava/util/List;)V", "getPorcelain", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeCData {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final List<HomeCBanner> banner;

    @NotNull
    private final List<HomeCIcon> icon;

    @Nullable
    private List<List<HomeCIcon>> iconBanner;

    @NotNull
    private final List<HomeCPorcelain> porcelain;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeCData$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/HomeCData;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeCData> serializer() {
            return HomeCData$$serializer.INSTANCE;
        }
    }

    public HomeCData() {
        this((List) null, (List) null, (List) null, (List) null, 15, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeCData(int i2, List list, List list2, List list3, List list4, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, HomeCData$$serializer.INSTANCE.getDescriptor());
        }
        this.banner = (i2 & 1) == 0 ? new ArrayList() : list;
        if ((i2 & 2) == 0) {
            this.porcelain = new ArrayList();
        } else {
            this.porcelain = list2;
        }
        if ((i2 & 4) == 0) {
            this.icon = new ArrayList();
        } else {
            this.icon = list3;
        }
        if ((i2 & 8) == 0) {
            this.iconBanner = null;
        } else {
            this.iconBanner = list4;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ HomeCData copy$default(HomeCData homeCData, List list, List list2, List list3, List list4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            list = homeCData.banner;
        }
        if ((i2 & 2) != 0) {
            list2 = homeCData.porcelain;
        }
        if ((i2 & 4) != 0) {
            list3 = homeCData.icon;
        }
        if ((i2 & 8) != 0) {
            list4 = homeCData.iconBanner;
        }
        return homeCData.copy(list, list2, list3, list4);
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeCData self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.banner, new ArrayList())) {
            output.encodeSerializableElement(serialDesc, 0, new ArrayListSerializer(HomeCBanner$$serializer.INSTANCE), self.banner);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.porcelain, new ArrayList())) {
            output.encodeSerializableElement(serialDesc, 1, new ArrayListSerializer(HomeCPorcelain$$serializer.INSTANCE), self.porcelain);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.icon, new ArrayList())) {
            output.encodeSerializableElement(serialDesc, 2, new ArrayListSerializer(HomeCIcon$$serializer.INSTANCE), self.icon);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.iconBanner != null) {
            output.encodeNullableSerializableElement(serialDesc, 3, new ArrayListSerializer(new ArrayListSerializer(HomeCIcon$$serializer.INSTANCE)), self.iconBanner);
        }
    }

    @NotNull
    public final List<HomeCBanner> component1() {
        return this.banner;
    }

    @NotNull
    public final List<HomeCPorcelain> component2() {
        return this.porcelain;
    }

    @NotNull
    public final List<HomeCIcon> component3() {
        return this.icon;
    }

    @Nullable
    public final List<List<HomeCIcon>> component4() {
        return this.iconBanner;
    }

    @NotNull
    public final HomeCData copy(@NotNull List<HomeCBanner> banner, @NotNull List<HomeCPorcelain> porcelain, @NotNull List<HomeCIcon> icon, @Nullable List<List<HomeCIcon>> iconBanner) {
        Intrinsics.checkNotNullParameter(banner, "banner");
        Intrinsics.checkNotNullParameter(porcelain, "porcelain");
        Intrinsics.checkNotNullParameter(icon, "icon");
        return new HomeCData(banner, porcelain, icon, iconBanner);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeCData)) {
            return false;
        }
        HomeCData homeCData = (HomeCData) other;
        return Intrinsics.areEqual(this.banner, homeCData.banner) && Intrinsics.areEqual(this.porcelain, homeCData.porcelain) && Intrinsics.areEqual(this.icon, homeCData.icon) && Intrinsics.areEqual(this.iconBanner, homeCData.iconBanner);
    }

    @NotNull
    public final List<HomeCBanner> getBanner() {
        return this.banner;
    }

    @NotNull
    public final List<HomeCIcon> getIcon() {
        return this.icon;
    }

    @Nullable
    public final List<List<HomeCIcon>> getIconBanner() {
        return this.iconBanner;
    }

    @NotNull
    public final List<HomeCPorcelain> getPorcelain() {
        return this.porcelain;
    }

    public int hashCode() {
        int iHashCode = ((((this.banner.hashCode() * 31) + this.porcelain.hashCode()) * 31) + this.icon.hashCode()) * 31;
        List<List<HomeCIcon>> list = this.iconBanner;
        return iHashCode + (list == null ? 0 : list.hashCode());
    }

    public final void setIconBanner(@Nullable List<List<HomeCIcon>> list) {
        this.iconBanner = list;
    }

    @NotNull
    public String toString() {
        return "HomeCData(banner=" + this.banner + ", porcelain=" + this.porcelain + ", icon=" + this.icon + ", iconBanner=" + this.iconBanner + ")";
    }

    public HomeCData(@NotNull List<HomeCBanner> banner, @NotNull List<HomeCPorcelain> porcelain, @NotNull List<HomeCIcon> icon, @Nullable List<List<HomeCIcon>> list) {
        Intrinsics.checkNotNullParameter(banner, "banner");
        Intrinsics.checkNotNullParameter(porcelain, "porcelain");
        Intrinsics.checkNotNullParameter(icon, "icon");
        this.banner = banner;
        this.porcelain = porcelain;
        this.icon = icon;
        this.iconBanner = list;
    }

    public /* synthetic */ HomeCData(List list, List list2, List list3, List list4, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? new ArrayList() : list, (i2 & 2) != 0 ? new ArrayList() : list2, (i2 & 4) != 0 ? new ArrayList() : list3, (i2 & 8) != 0 ? null : list4);
    }
}

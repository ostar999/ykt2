package com.yddmi.doctor.entity.result;

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
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b%\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 =2\u00020\u0001:\u0002<=Bc\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011B[\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u0012J\t\u0010)\u001a\u00020\u0005HÆ\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010+\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010,\u001a\u00020\tHÆ\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0011\u0010.\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\fHÆ\u0003J\u000b\u0010/\u001a\u0004\u0018\u00010\u000eHÆ\u0003J_\u00100\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00052\u0010\b\u0002\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000eHÆ\u0001J\u0013\u00101\u001a\u00020\t2\b\u00102\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00103\u001a\u00020\u0003HÖ\u0001J\t\u00104\u001a\u00020\u0005HÖ\u0001J!\u00105\u001a\u0002062\u0006\u00107\u001a\u00020\u00002\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020;HÇ\u0001R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001c\u0010\n\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0014\"\u0004\b\u0018\u0010\u0016R\"\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0014\"\u0004\b\"\u0010\u0016R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0014\"\u0004\b$\u0010\u0016R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(¨\u0006>"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeItem;", "", "seen1", "", "menuCode", "", "menuName", "des", "more", "", "imageUrl", "item", "", "mHomeStudyResult", "Lcom/yddmi/doctor/entity/result/HomeStudyResult;", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;Ljava/util/List;Lcom/yddmi/doctor/entity/result/HomeStudyResult;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;Ljava/util/List;Lcom/yddmi/doctor/entity/result/HomeStudyResult;)V", "getDes", "()Ljava/lang/String;", "setDes", "(Ljava/lang/String;)V", "getImageUrl", "setImageUrl", "getItem", "()Ljava/util/List;", "setItem", "(Ljava/util/List;)V", "getMHomeStudyResult", "()Lcom/yddmi/doctor/entity/result/HomeStudyResult;", "setMHomeStudyResult", "(Lcom/yddmi/doctor/entity/result/HomeStudyResult;)V", "getMenuCode", "setMenuCode", "getMenuName", "setMenuName", "getMore", "()Z", "setMore", "(Z)V", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeItem {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private String des;

    @Nullable
    private String imageUrl;

    @Nullable
    private List<HomeItem> item;

    @Nullable
    private HomeStudyResult mHomeStudyResult;

    @NotNull
    private String menuCode;

    @Nullable
    private String menuName;
    private boolean more;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeItem$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/HomeItem;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeItem> serializer() {
            return HomeItem$$serializer.INSTANCE;
        }
    }

    public HomeItem() {
        this((String) null, (String) null, (String) null, false, (String) null, (List) null, (HomeStudyResult) null, 127, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeItem(int i2, String str, String str2, String str3, boolean z2, String str4, List list, HomeStudyResult homeStudyResult, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, HomeItem$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.menuCode = "";
        } else {
            this.menuCode = str;
        }
        if ((i2 & 2) == 0) {
            this.menuName = "";
        } else {
            this.menuName = str2;
        }
        if ((i2 & 4) == 0) {
            this.des = "功能区描述";
        } else {
            this.des = str3;
        }
        if ((i2 & 8) == 0) {
            this.more = false;
        } else {
            this.more = z2;
        }
        if ((i2 & 16) == 0) {
            this.imageUrl = "";
        } else {
            this.imageUrl = str4;
        }
        if ((i2 & 32) == 0) {
            this.item = null;
        } else {
            this.item = list;
        }
        if ((i2 & 64) == 0) {
            this.mHomeStudyResult = null;
        } else {
            this.mHomeStudyResult = homeStudyResult;
        }
    }

    public static /* synthetic */ HomeItem copy$default(HomeItem homeItem, String str, String str2, String str3, boolean z2, String str4, List list, HomeStudyResult homeStudyResult, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = homeItem.menuCode;
        }
        if ((i2 & 2) != 0) {
            str2 = homeItem.menuName;
        }
        String str5 = str2;
        if ((i2 & 4) != 0) {
            str3 = homeItem.des;
        }
        String str6 = str3;
        if ((i2 & 8) != 0) {
            z2 = homeItem.more;
        }
        boolean z3 = z2;
        if ((i2 & 16) != 0) {
            str4 = homeItem.imageUrl;
        }
        String str7 = str4;
        if ((i2 & 32) != 0) {
            list = homeItem.item;
        }
        List list2 = list;
        if ((i2 & 64) != 0) {
            homeStudyResult = homeItem.mHomeStudyResult;
        }
        return homeItem.copy(str, str5, str6, z3, str7, list2, homeStudyResult);
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeItem self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.menuCode, "")) {
            output.encodeStringElement(serialDesc, 0, self.menuCode);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.menuName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.menuName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.des, "功能区描述")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.des);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.more) {
            output.encodeBooleanElement(serialDesc, 3, self.more);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.imageUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.imageUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || self.item != null) {
            output.encodeNullableSerializableElement(serialDesc, 5, new ArrayListSerializer(HomeItem$$serializer.INSTANCE), self.item);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || self.mHomeStudyResult != null) {
            output.encodeNullableSerializableElement(serialDesc, 6, HomeStudyResult$$serializer.INSTANCE, self.mHomeStudyResult);
        }
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getMenuCode() {
        return this.menuCode;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getMenuName() {
        return this.menuName;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getDes() {
        return this.des;
    }

    /* renamed from: component4, reason: from getter */
    public final boolean getMore() {
        return this.more;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getImageUrl() {
        return this.imageUrl;
    }

    @Nullable
    public final List<HomeItem> component6() {
        return this.item;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final HomeStudyResult getMHomeStudyResult() {
        return this.mHomeStudyResult;
    }

    @NotNull
    public final HomeItem copy(@NotNull String menuCode, @Nullable String menuName, @Nullable String des, boolean more, @Nullable String imageUrl, @Nullable List<HomeItem> item, @Nullable HomeStudyResult mHomeStudyResult) {
        Intrinsics.checkNotNullParameter(menuCode, "menuCode");
        return new HomeItem(menuCode, menuName, des, more, imageUrl, item, mHomeStudyResult);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeItem)) {
            return false;
        }
        HomeItem homeItem = (HomeItem) other;
        return Intrinsics.areEqual(this.menuCode, homeItem.menuCode) && Intrinsics.areEqual(this.menuName, homeItem.menuName) && Intrinsics.areEqual(this.des, homeItem.des) && this.more == homeItem.more && Intrinsics.areEqual(this.imageUrl, homeItem.imageUrl) && Intrinsics.areEqual(this.item, homeItem.item) && Intrinsics.areEqual(this.mHomeStudyResult, homeItem.mHomeStudyResult);
    }

    @Nullable
    public final String getDes() {
        return this.des;
    }

    @Nullable
    public final String getImageUrl() {
        return this.imageUrl;
    }

    @Nullable
    public final List<HomeItem> getItem() {
        return this.item;
    }

    @Nullable
    public final HomeStudyResult getMHomeStudyResult() {
        return this.mHomeStudyResult;
    }

    @NotNull
    public final String getMenuCode() {
        return this.menuCode;
    }

    @Nullable
    public final String getMenuName() {
        return this.menuName;
    }

    public final boolean getMore() {
        return this.more;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = this.menuCode.hashCode() * 31;
        String str = this.menuName;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.des;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        boolean z2 = this.more;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        int i3 = (iHashCode3 + i2) * 31;
        String str3 = this.imageUrl;
        int iHashCode4 = (i3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        List<HomeItem> list = this.item;
        int iHashCode5 = (iHashCode4 + (list == null ? 0 : list.hashCode())) * 31;
        HomeStudyResult homeStudyResult = this.mHomeStudyResult;
        return iHashCode5 + (homeStudyResult != null ? homeStudyResult.hashCode() : 0);
    }

    public final void setDes(@Nullable String str) {
        this.des = str;
    }

    public final void setImageUrl(@Nullable String str) {
        this.imageUrl = str;
    }

    public final void setItem(@Nullable List<HomeItem> list) {
        this.item = list;
    }

    public final void setMHomeStudyResult(@Nullable HomeStudyResult homeStudyResult) {
        this.mHomeStudyResult = homeStudyResult;
    }

    public final void setMenuCode(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.menuCode = str;
    }

    public final void setMenuName(@Nullable String str) {
        this.menuName = str;
    }

    public final void setMore(boolean z2) {
        this.more = z2;
    }

    @NotNull
    public String toString() {
        return "HomeItem(menuCode=" + this.menuCode + ", menuName=" + this.menuName + ", des=" + this.des + ", more=" + this.more + ", imageUrl=" + this.imageUrl + ", item=" + this.item + ", mHomeStudyResult=" + this.mHomeStudyResult + ")";
    }

    public HomeItem(@NotNull String menuCode, @Nullable String str, @Nullable String str2, boolean z2, @Nullable String str3, @Nullable List<HomeItem> list, @Nullable HomeStudyResult homeStudyResult) {
        Intrinsics.checkNotNullParameter(menuCode, "menuCode");
        this.menuCode = menuCode;
        this.menuName = str;
        this.des = str2;
        this.more = z2;
        this.imageUrl = str3;
        this.item = list;
        this.mHomeStudyResult = homeStudyResult;
    }

    public /* synthetic */ HomeItem(String str, String str2, String str3, boolean z2, String str4, List list, HomeStudyResult homeStudyResult, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? "" : str, (i2 & 2) != 0 ? "" : str2, (i2 & 4) != 0 ? "功能区描述" : str3, (i2 & 8) != 0 ? false : z2, (i2 & 16) == 0 ? str4 : "", (i2 & 32) != 0 ? null : list, (i2 & 64) != 0 ? null : homeStudyResult);
    }
}

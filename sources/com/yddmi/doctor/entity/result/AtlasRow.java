package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.yikaobang.yixue.R2;
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
import kotlinx.serialization.builtins.BuiltinSerializersKt;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.internal.ArrayListSerializer;
import kotlinx.serialization.internal.FloatSerializer;
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b$\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 @2\u00020\u0001:\u0002?@B\u0089\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0007\u0012\u0010\u0010\u0011\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0000\u0018\u00010\u0012\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014¢\u0006\u0002\u0010\u0015B\u008b\u0001\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0007\u0012\u0012\b\u0002\u0010\u0011\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0000\u0018\u00010\u0012¢\u0006\u0002\u0010\u0016J\u0010\u0010'\u001a\u0004\u0018\u00010\u0005HÆ\u0003¢\u0006\u0002\u0010\u0018J\u000b\u0010(\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u0013\u0010)\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0000\u0018\u00010\u0012HÆ\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u0010\u0010+\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010!J\t\u0010,\u001a\u00020\u0003HÆ\u0003J\t\u0010-\u001a\u00020\u0003HÆ\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u0010\u0010/\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010!J\t\u00100\u001a\u00020\u000eHÆ\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u0094\u0001\u00102\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\r\u001a\u00020\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00072\u0012\b\u0002\u0010\u0011\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0000\u0018\u00010\u0012HÆ\u0001¢\u0006\u0002\u00103J\u0013\u00104\u001a\u00020\u000e2\b\u00105\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00106\u001a\u00020\u0003HÖ\u0001J\t\u00107\u001a\u00020\u0007HÖ\u0001J!\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020\u00002\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020>HÇ\u0001R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\n\n\u0002\u0010\u0019\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001bR\u001b\u0010\u0011\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0000\u0018\u00010\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0015\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\"\u001a\u0004\b \u0010!R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010#R\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010#R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010$R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001bR\u0015\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\"\u001a\u0004\b&\u0010!¨\u0006A"}, d2 = {"Lcom/yddmi/doctor/entity/result/AtlasRow;", "", "seen1", "", "bearing", "", "description", "", "id", "isCmmon", "isDelete", "name", "version", "isMatche", "", "extentName", "diseaseName", "extentVos", "", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Float;Ljava/lang/String;Ljava/lang/Integer;IILjava/lang/String;Ljava/lang/Integer;ZLjava/lang/String;Ljava/lang/String;Ljava/util/List;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Float;Ljava/lang/String;Ljava/lang/Integer;IILjava/lang/String;Ljava/lang/Integer;ZLjava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "getBearing", "()Ljava/lang/Float;", "Ljava/lang/Float;", "getDescription", "()Ljava/lang/String;", "getDiseaseName", "getExtentName", "getExtentVos", "()Ljava/util/List;", "getId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "()I", "()Z", "getName", "getVersion", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Float;Ljava/lang/String;Ljava/lang/Integer;IILjava/lang/String;Ljava/lang/Integer;ZLjava/lang/String;Ljava/lang/String;Ljava/util/List;)Lcom/yddmi/doctor/entity/result/AtlasRow;", "equals", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class AtlasRow {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final Float bearing;

    @Nullable
    private final String description;

    @Nullable
    private final String diseaseName;

    @Nullable
    private final String extentName;

    @Nullable
    private final List<AtlasRow> extentVos;

    @Nullable
    private final Integer id;
    private final int isCmmon;
    private final int isDelete;
    private final boolean isMatche;

    @Nullable
    private final String name;

    @Nullable
    private final Integer version;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/AtlasRow$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/AtlasRow;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<AtlasRow> serializer() {
            return AtlasRow$$serializer.INSTANCE;
        }
    }

    public AtlasRow() {
        this((Float) null, (String) null, (Integer) null, 0, 0, (String) null, (Integer) null, false, (String) null, (String) null, (List) null, R2.attr.indicatorSize, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ AtlasRow(int i2, Float f2, String str, Integer num, int i3, int i4, String str2, Integer num2, boolean z2, String str3, String str4, List list, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, AtlasRow$$serializer.INSTANCE.getDescriptor());
        }
        this.bearing = (i2 & 1) == 0 ? Float.valueOf(-1.0f) : f2;
        if ((i2 & 2) == 0) {
            this.description = "";
        } else {
            this.description = str;
        }
        if ((i2 & 4) == 0) {
            this.id = -1;
        } else {
            this.id = num;
        }
        if ((i2 & 8) == 0) {
            this.isCmmon = -1;
        } else {
            this.isCmmon = i3;
        }
        if ((i2 & 16) == 0) {
            this.isDelete = -1;
        } else {
            this.isDelete = i4;
        }
        if ((i2 & 32) == 0) {
            this.name = "";
        } else {
            this.name = str2;
        }
        if ((i2 & 64) == 0) {
            this.version = -1;
        } else {
            this.version = num2;
        }
        if ((i2 & 128) == 0) {
            this.isMatche = false;
        } else {
            this.isMatche = z2;
        }
        if ((i2 & 256) == 0) {
            this.extentName = "";
        } else {
            this.extentName = str3;
        }
        if ((i2 & 512) == 0) {
            this.diseaseName = "";
        } else {
            this.diseaseName = str4;
        }
        if ((i2 & 1024) == 0) {
            this.extentVos = null;
        } else {
            this.extentVos = list;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull AtlasRow self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual((Object) self.bearing, (Object) Float.valueOf(-1.0f))) {
            output.encodeNullableSerializableElement(serialDesc, 0, FloatSerializer.INSTANCE, self.bearing);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.description, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.description);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || (num = self.id) == null || num.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 2, IntSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.isCmmon != -1) {
            output.encodeIntElement(serialDesc, 3, self.isCmmon);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || self.isDelete != -1) {
            output.encodeIntElement(serialDesc, 4, self.isDelete);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || (num2 = self.version) == null || num2.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 6, IntSerializer.INSTANCE, self.version);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || self.isMatche) {
            output.encodeBooleanElement(serialDesc, 7, self.isMatche);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || !Intrinsics.areEqual(self.extentName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 8, StringSerializer.INSTANCE, self.extentName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || !Intrinsics.areEqual(self.diseaseName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 9, StringSerializer.INSTANCE, self.diseaseName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || self.extentVos != null) {
            output.encodeNullableSerializableElement(serialDesc, 10, new ArrayListSerializer(BuiltinSerializersKt.getNullable(AtlasRow$$serializer.INSTANCE)), self.extentVos);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Float getBearing() {
        return this.bearing;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getDiseaseName() {
        return this.diseaseName;
    }

    @Nullable
    public final List<AtlasRow> component11() {
        return this.extentVos;
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

    /* renamed from: component4, reason: from getter */
    public final int getIsCmmon() {
        return this.isCmmon;
    }

    /* renamed from: component5, reason: from getter */
    public final int getIsDelete() {
        return this.isDelete;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final Integer getVersion() {
        return this.version;
    }

    /* renamed from: component8, reason: from getter */
    public final boolean getIsMatche() {
        return this.isMatche;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getExtentName() {
        return this.extentName;
    }

    @NotNull
    public final AtlasRow copy(@Nullable Float bearing, @Nullable String description, @Nullable Integer id, int isCmmon, int isDelete, @Nullable String name, @Nullable Integer version, boolean isMatche, @Nullable String extentName, @Nullable String diseaseName, @Nullable List<AtlasRow> extentVos) {
        return new AtlasRow(bearing, description, id, isCmmon, isDelete, name, version, isMatche, extentName, diseaseName, extentVos);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AtlasRow)) {
            return false;
        }
        AtlasRow atlasRow = (AtlasRow) other;
        return Intrinsics.areEqual((Object) this.bearing, (Object) atlasRow.bearing) && Intrinsics.areEqual(this.description, atlasRow.description) && Intrinsics.areEqual(this.id, atlasRow.id) && this.isCmmon == atlasRow.isCmmon && this.isDelete == atlasRow.isDelete && Intrinsics.areEqual(this.name, atlasRow.name) && Intrinsics.areEqual(this.version, atlasRow.version) && this.isMatche == atlasRow.isMatche && Intrinsics.areEqual(this.extentName, atlasRow.extentName) && Intrinsics.areEqual(this.diseaseName, atlasRow.diseaseName) && Intrinsics.areEqual(this.extentVos, atlasRow.extentVos);
    }

    @Nullable
    public final Float getBearing() {
        return this.bearing;
    }

    @Nullable
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    public final String getDiseaseName() {
        return this.diseaseName;
    }

    @Nullable
    public final String getExtentName() {
        return this.extentName;
    }

    @Nullable
    public final List<AtlasRow> getExtentVos() {
        return this.extentVos;
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
    public final Integer getVersion() {
        return this.version;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        Float f2 = this.bearing;
        int iHashCode = (f2 == null ? 0 : f2.hashCode()) * 31;
        String str = this.description;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        Integer num = this.id;
        int iHashCode3 = (((((iHashCode2 + (num == null ? 0 : num.hashCode())) * 31) + this.isCmmon) * 31) + this.isDelete) * 31;
        String str2 = this.name;
        int iHashCode4 = (iHashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        Integer num2 = this.version;
        int iHashCode5 = (iHashCode4 + (num2 == null ? 0 : num2.hashCode())) * 31;
        boolean z2 = this.isMatche;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        int i3 = (iHashCode5 + i2) * 31;
        String str3 = this.extentName;
        int iHashCode6 = (i3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.diseaseName;
        int iHashCode7 = (iHashCode6 + (str4 == null ? 0 : str4.hashCode())) * 31;
        List<AtlasRow> list = this.extentVos;
        return iHashCode7 + (list != null ? list.hashCode() : 0);
    }

    public final int isCmmon() {
        return this.isCmmon;
    }

    public final int isDelete() {
        return this.isDelete;
    }

    public final boolean isMatche() {
        return this.isMatche;
    }

    @NotNull
    public String toString() {
        return "AtlasRow(bearing=" + this.bearing + ", description=" + this.description + ", id=" + this.id + ", isCmmon=" + this.isCmmon + ", isDelete=" + this.isDelete + ", name=" + this.name + ", version=" + this.version + ", isMatche=" + this.isMatche + ", extentName=" + this.extentName + ", diseaseName=" + this.diseaseName + ", extentVos=" + this.extentVos + ")";
    }

    public AtlasRow(@Nullable Float f2, @Nullable String str, @Nullable Integer num, int i2, int i3, @Nullable String str2, @Nullable Integer num2, boolean z2, @Nullable String str3, @Nullable String str4, @Nullable List<AtlasRow> list) {
        this.bearing = f2;
        this.description = str;
        this.id = num;
        this.isCmmon = i2;
        this.isDelete = i3;
        this.name = str2;
        this.version = num2;
        this.isMatche = z2;
        this.extentName = str3;
        this.diseaseName = str4;
        this.extentVos = list;
    }

    public /* synthetic */ AtlasRow(Float f2, String str, Integer num, int i2, int i3, String str2, Integer num2, boolean z2, String str3, String str4, List list, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? Float.valueOf(-1.0f) : f2, (i4 & 2) != 0 ? "" : str, (i4 & 4) != 0 ? -1 : num, (i4 & 8) != 0 ? -1 : i2, (i4 & 16) != 0 ? -1 : i3, (i4 & 32) != 0 ? "" : str2, (i4 & 64) != 0 ? -1 : num2, (i4 & 128) != 0 ? false : z2, (i4 & 256) != 0 ? "" : str3, (i4 & 512) == 0 ? str4 : "", (i4 & 1024) != 0 ? null : list);
    }
}

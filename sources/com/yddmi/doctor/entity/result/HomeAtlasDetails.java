package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
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
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 :2\u00020\u0001:\u00029:Bw\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0007\u0012\u0010\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\f\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011¢\u0006\u0002\u0010\u0012Bu\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0007\u0012\u0012\b\u0002\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\f\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\u0013J\t\u0010\"\u001a\u00020\u0003HÆ\u0003J\t\u0010#\u001a\u00020\u0003HÆ\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u0010\u0010%\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001aJ\u000b\u0010&\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010'\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u0013\u0010(\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\fHÆ\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0007HÆ\u0003J~\u0010+\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00072\u0012\b\u0002\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\f2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0007HÆ\u0001¢\u0006\u0002\u0010,J\u0013\u0010-\u001a\u00020.2\b\u0010/\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00100\u001a\u00020\u0003HÖ\u0001J\t\u00101\u001a\u00020\u0007HÖ\u0001J!\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\u00002\u0006\u00105\u001a\u0002062\u0006\u00107\u001a\u000208HÇ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0015\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b\u0019\u0010\u001aR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0018R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0018R\u001b\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0018R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0018¨\u0006;"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeAtlasDetails;", "", "seen1", "", "diseaseId", "id", "inspections", "", DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, "name", "plan", "relations", "", "Lcom/yddmi/doctor/entity/result/AtlasRow;", "result", "symptomDscription", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IIILjava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(IILjava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "getDiseaseId", "()I", "getId", "getInspections", "()Ljava/lang/String;", "getLevel", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getName", "getPlan", "getRelations", "()Ljava/util/List;", "getResult", "getSymptomDscription", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(IILjava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)Lcom/yddmi/doctor/entity/result/HomeAtlasDetails;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeAtlasDetails {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private final int diseaseId;
    private final int id;

    @Nullable
    private final String inspections;

    @Nullable
    private final Integer level;

    @Nullable
    private final String name;

    @Nullable
    private final String plan;

    @Nullable
    private final List<AtlasRow> relations;

    @Nullable
    private final String result;

    @Nullable
    private final String symptomDscription;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeAtlasDetails$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/HomeAtlasDetails;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeAtlasDetails> serializer() {
            return HomeAtlasDetails$$serializer.INSTANCE;
        }
    }

    public HomeAtlasDetails() {
        this(0, 0, (String) null, (Integer) null, (String) null, (String) null, (List) null, (String) null, (String) null, 511, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeAtlasDetails(int i2, int i3, int i4, String str, Integer num, String str2, String str3, List list, String str4, String str5, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, HomeAtlasDetails$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.diseaseId = -1;
        } else {
            this.diseaseId = i3;
        }
        if ((i2 & 2) == 0) {
            this.id = -1;
        } else {
            this.id = i4;
        }
        if ((i2 & 4) == 0) {
            this.inspections = "";
        } else {
            this.inspections = str;
        }
        if ((i2 & 8) == 0) {
            this.level = -1;
        } else {
            this.level = num;
        }
        if ((i2 & 16) == 0) {
            this.name = "";
        } else {
            this.name = str2;
        }
        if ((i2 & 32) == 0) {
            this.plan = "";
        } else {
            this.plan = str3;
        }
        if ((i2 & 64) == 0) {
            this.relations = null;
        } else {
            this.relations = list;
        }
        if ((i2 & 128) == 0) {
            this.result = "";
        } else {
            this.result = str4;
        }
        if ((i2 & 256) == 0) {
            this.symptomDscription = "";
        } else {
            this.symptomDscription = str5;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeAtlasDetails self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.diseaseId != -1) {
            output.encodeIntElement(serialDesc, 0, self.diseaseId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.id != -1) {
            output.encodeIntElement(serialDesc, 1, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.inspections, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.inspections);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || (num = self.level) == null || num.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 3, IntSerializer.INSTANCE, self.level);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.plan, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.plan);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || self.relations != null) {
            output.encodeNullableSerializableElement(serialDesc, 6, new ArrayListSerializer(BuiltinSerializersKt.getNullable(AtlasRow$$serializer.INSTANCE)), self.relations);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || !Intrinsics.areEqual(self.result, "")) {
            output.encodeNullableSerializableElement(serialDesc, 7, StringSerializer.INSTANCE, self.result);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || !Intrinsics.areEqual(self.symptomDscription, "")) {
            output.encodeNullableSerializableElement(serialDesc, 8, StringSerializer.INSTANCE, self.symptomDscription);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getDiseaseId() {
        return this.diseaseId;
    }

    /* renamed from: component2, reason: from getter */
    public final int getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getInspections() {
        return this.inspections;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final Integer getLevel() {
        return this.level;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getPlan() {
        return this.plan;
    }

    @Nullable
    public final List<AtlasRow> component7() {
        return this.relations;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getResult() {
        return this.result;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getSymptomDscription() {
        return this.symptomDscription;
    }

    @NotNull
    public final HomeAtlasDetails copy(int diseaseId, int id, @Nullable String inspections, @Nullable Integer level, @Nullable String name, @Nullable String plan, @Nullable List<AtlasRow> relations, @Nullable String result, @Nullable String symptomDscription) {
        return new HomeAtlasDetails(diseaseId, id, inspections, level, name, plan, relations, result, symptomDscription);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeAtlasDetails)) {
            return false;
        }
        HomeAtlasDetails homeAtlasDetails = (HomeAtlasDetails) other;
        return this.diseaseId == homeAtlasDetails.diseaseId && this.id == homeAtlasDetails.id && Intrinsics.areEqual(this.inspections, homeAtlasDetails.inspections) && Intrinsics.areEqual(this.level, homeAtlasDetails.level) && Intrinsics.areEqual(this.name, homeAtlasDetails.name) && Intrinsics.areEqual(this.plan, homeAtlasDetails.plan) && Intrinsics.areEqual(this.relations, homeAtlasDetails.relations) && Intrinsics.areEqual(this.result, homeAtlasDetails.result) && Intrinsics.areEqual(this.symptomDscription, homeAtlasDetails.symptomDscription);
    }

    public final int getDiseaseId() {
        return this.diseaseId;
    }

    public final int getId() {
        return this.id;
    }

    @Nullable
    public final String getInspections() {
        return this.inspections;
    }

    @Nullable
    public final Integer getLevel() {
        return this.level;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final String getPlan() {
        return this.plan;
    }

    @Nullable
    public final List<AtlasRow> getRelations() {
        return this.relations;
    }

    @Nullable
    public final String getResult() {
        return this.result;
    }

    @Nullable
    public final String getSymptomDscription() {
        return this.symptomDscription;
    }

    public int hashCode() {
        int i2 = ((this.diseaseId * 31) + this.id) * 31;
        String str = this.inspections;
        int iHashCode = (i2 + (str == null ? 0 : str.hashCode())) * 31;
        Integer num = this.level;
        int iHashCode2 = (iHashCode + (num == null ? 0 : num.hashCode())) * 31;
        String str2 = this.name;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.plan;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        List<AtlasRow> list = this.relations;
        int iHashCode5 = (iHashCode4 + (list == null ? 0 : list.hashCode())) * 31;
        String str4 = this.result;
        int iHashCode6 = (iHashCode5 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.symptomDscription;
        return iHashCode6 + (str5 != null ? str5.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "HomeAtlasDetails(diseaseId=" + this.diseaseId + ", id=" + this.id + ", inspections=" + this.inspections + ", level=" + this.level + ", name=" + this.name + ", plan=" + this.plan + ", relations=" + this.relations + ", result=" + this.result + ", symptomDscription=" + this.symptomDscription + ")";
    }

    public HomeAtlasDetails(int i2, int i3, @Nullable String str, @Nullable Integer num, @Nullable String str2, @Nullable String str3, @Nullable List<AtlasRow> list, @Nullable String str4, @Nullable String str5) {
        this.diseaseId = i2;
        this.id = i3;
        this.inspections = str;
        this.level = num;
        this.name = str2;
        this.plan = str3;
        this.relations = list;
        this.result = str4;
        this.symptomDscription = str5;
    }

    public /* synthetic */ HomeAtlasDetails(int i2, int i3, String str, Integer num, String str2, String str3, List list, String str4, String str5, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? -1 : i2, (i4 & 2) != 0 ? -1 : i3, (i4 & 4) != 0 ? "" : str, (i4 & 8) != 0 ? -1 : num, (i4 & 16) != 0 ? "" : str2, (i4 & 32) != 0 ? "" : str3, (i4 & 64) != 0 ? null : list, (i4 & 128) != 0 ? "" : str4, (i4 & 256) == 0 ? str5 : "");
    }
}

package com.yddmi.doctor.entity.result;

import com.google.gson.annotations.SerializedName;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 #2\u00020\u0001:\u0002\"#B5\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\u0010\u0004\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nB%\u0012\u0012\b\u0002\u0010\u0004\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u000bJ\u0013\u0010\u0011\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u0005HÆ\u0003J\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u000fJ.\u0010\u0013\u001a\u00020\u00002\u0012\b\u0002\u0010\u0004\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010\u0014J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J!\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!HÇ\u0001R \u0010\u0004\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0007\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000f¨\u0006$"}, d2 = {"Lcom/yddmi/doctor/entity/result/InquiryDiseaseList;", "", "seen1", "", "rows", "", "Lcom/yddmi/doctor/entity/result/InquiryCheckInfo;", "total", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/util/List;Ljava/lang/Integer;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/util/List;Ljava/lang/Integer;)V", "getRows", "()Ljava/util/List;", "getTotal", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "component1", "component2", "copy", "(Ljava/util/List;Ljava/lang/Integer;)Lcom/yddmi/doctor/entity/result/InquiryDiseaseList;", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class InquiryDiseaseList {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @SerializedName("rows")
    @Nullable
    private final List<InquiryCheckInfo> rows;

    @SerializedName("total")
    @Nullable
    private final Integer total;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/InquiryDiseaseList$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/InquiryDiseaseList;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<InquiryDiseaseList> serializer() {
            return InquiryDiseaseList$$serializer.INSTANCE;
        }
    }

    public InquiryDiseaseList() {
        this((List) null, (Integer) (0 == true ? 1 : 0), 3, (DefaultConstructorMarker) (0 == true ? 1 : 0));
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ InquiryDiseaseList(int i2, List list, Integer num, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, InquiryDiseaseList$$serializer.INSTANCE.getDescriptor());
        }
        this.rows = (i2 & 1) == 0 ? null : list;
        if ((i2 & 2) == 0) {
            this.total = 0;
        } else {
            this.total = num;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ InquiryDiseaseList copy$default(InquiryDiseaseList inquiryDiseaseList, List list, Integer num, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            list = inquiryDiseaseList.rows;
        }
        if ((i2 & 2) != 0) {
            num = inquiryDiseaseList.total;
        }
        return inquiryDiseaseList.copy(list, num);
    }

    @JvmStatic
    public static final void write$Self(@NotNull InquiryDiseaseList self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.rows != null) {
            output.encodeNullableSerializableElement(serialDesc, 0, new ArrayListSerializer(BuiltinSerializersKt.getNullable(InquiryCheckInfo$$serializer.INSTANCE)), self.rows);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || (num = self.total) == null || num.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 1, IntSerializer.INSTANCE, self.total);
        }
    }

    @Nullable
    public final List<InquiryCheckInfo> component1() {
        return this.rows;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final Integer getTotal() {
        return this.total;
    }

    @NotNull
    public final InquiryDiseaseList copy(@Nullable List<InquiryCheckInfo> rows, @Nullable Integer total) {
        return new InquiryDiseaseList(rows, total);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InquiryDiseaseList)) {
            return false;
        }
        InquiryDiseaseList inquiryDiseaseList = (InquiryDiseaseList) other;
        return Intrinsics.areEqual(this.rows, inquiryDiseaseList.rows) && Intrinsics.areEqual(this.total, inquiryDiseaseList.total);
    }

    @Nullable
    public final List<InquiryCheckInfo> getRows() {
        return this.rows;
    }

    @Nullable
    public final Integer getTotal() {
        return this.total;
    }

    public int hashCode() {
        List<InquiryCheckInfo> list = this.rows;
        int iHashCode = (list == null ? 0 : list.hashCode()) * 31;
        Integer num = this.total;
        return iHashCode + (num != null ? num.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "InquiryDiseaseList(rows=" + this.rows + ", total=" + this.total + ")";
    }

    public InquiryDiseaseList(@Nullable List<InquiryCheckInfo> list, @Nullable Integer num) {
        this.rows = list;
        this.total = num;
    }

    public /* synthetic */ InquiryDiseaseList(List list, Integer num, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : list, (i2 & 2) != 0 ? 0 : num);
    }
}

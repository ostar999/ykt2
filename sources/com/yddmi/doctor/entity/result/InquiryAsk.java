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
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 *2\u00020\u0001:\u0002)*BI\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0010\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\b\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\rB=\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0012\b\u0002\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\b\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0010J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0013\u0010\u0019\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\bHÆ\u0003J\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0010JF\u0010\u001b\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0012\b\u0002\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\b2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010\u001cJ\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010 \u001a\u00020\u0003HÖ\u0001J\t\u0010!\u001a\u00020\u0006HÖ\u0001J!\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00002\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(HÇ\u0001R\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R \u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\b8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\n\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u0016\u0010\u0010¨\u0006+"}, d2 = {"Lcom/yddmi/doctor/entity/result/InquiryAsk;", "", "seen1", "", "id", "name", "", "patientAskVos", "", "Lcom/yddmi/doctor/entity/result/InquiryAskInfo;", "pid", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Integer;Ljava/lang/String;Ljava/util/List;Ljava/lang/Integer;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/util/List;Ljava/lang/Integer;)V", "getId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getName", "()Ljava/lang/String;", "getPatientAskVos", "()Ljava/util/List;", "getPid", "component1", "component2", "component3", "component4", "copy", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/util/List;Ljava/lang/Integer;)Lcom/yddmi/doctor/entity/result/InquiryAsk;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class InquiryAsk {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @SerializedName("id")
    @Nullable
    private final Integer id;

    @SerializedName("name")
    @Nullable
    private final String name;

    @SerializedName("patientAskVos")
    @Nullable
    private final List<InquiryAskInfo> patientAskVos;

    @SerializedName("pid")
    @Nullable
    private final Integer pid;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/InquiryAsk$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/InquiryAsk;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<InquiryAsk> serializer() {
            return InquiryAsk$$serializer.INSTANCE;
        }
    }

    public InquiryAsk() {
        this((Integer) null, (String) null, (List) null, (Integer) null, 15, (DefaultConstructorMarker) null);
    }

    public InquiryAsk(@Nullable Integer num, @Nullable String str, @Nullable List<InquiryAskInfo> list, @Nullable Integer num2) {
        this.id = num;
        this.name = str;
        this.patientAskVos = list;
        this.pid = num2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ InquiryAsk copy$default(InquiryAsk inquiryAsk, Integer num, String str, List list, Integer num2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            num = inquiryAsk.id;
        }
        if ((i2 & 2) != 0) {
            str = inquiryAsk.name;
        }
        if ((i2 & 4) != 0) {
            list = inquiryAsk.patientAskVos;
        }
        if ((i2 & 8) != 0) {
            num2 = inquiryAsk.pid;
        }
        return inquiryAsk.copy(num, str, list, num2);
    }

    @JvmStatic
    public static final void write$Self(@NotNull InquiryAsk self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || (num = self.id) == null || num.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 0, IntSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.patientAskVos != null) {
            output.encodeNullableSerializableElement(serialDesc, 2, new ArrayListSerializer(BuiltinSerializersKt.getNullable(InquiryAskInfo$$serializer.INSTANCE)), self.patientAskVos);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || (num2 = self.pid) == null || num2.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 3, IntSerializer.INSTANCE, self.pid);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Integer getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final List<InquiryAskInfo> component3() {
        return this.patientAskVos;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final Integer getPid() {
        return this.pid;
    }

    @NotNull
    public final InquiryAsk copy(@Nullable Integer id, @Nullable String name, @Nullable List<InquiryAskInfo> patientAskVos, @Nullable Integer pid) {
        return new InquiryAsk(id, name, patientAskVos, pid);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InquiryAsk)) {
            return false;
        }
        InquiryAsk inquiryAsk = (InquiryAsk) other;
        return Intrinsics.areEqual(this.id, inquiryAsk.id) && Intrinsics.areEqual(this.name, inquiryAsk.name) && Intrinsics.areEqual(this.patientAskVos, inquiryAsk.patientAskVos) && Intrinsics.areEqual(this.pid, inquiryAsk.pid);
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
    public final List<InquiryAskInfo> getPatientAskVos() {
        return this.patientAskVos;
    }

    @Nullable
    public final Integer getPid() {
        return this.pid;
    }

    public int hashCode() {
        Integer num = this.id;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        String str = this.name;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        List<InquiryAskInfo> list = this.patientAskVos;
        int iHashCode3 = (iHashCode2 + (list == null ? 0 : list.hashCode())) * 31;
        Integer num2 = this.pid;
        return iHashCode3 + (num2 != null ? num2.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "InquiryAsk(id=" + this.id + ", name=" + this.name + ", patientAskVos=" + this.patientAskVos + ", pid=" + this.pid + ")";
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ InquiryAsk(int i2, Integer num, String str, List list, Integer num2, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, InquiryAsk$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.id = 0;
        } else {
            this.id = num;
        }
        if ((i2 & 2) == 0) {
            this.name = "";
        } else {
            this.name = str;
        }
        if ((i2 & 4) == 0) {
            this.patientAskVos = null;
        } else {
            this.patientAskVos = list;
        }
        if ((i2 & 8) == 0) {
            this.pid = 0;
        } else {
            this.pid = num2;
        }
    }

    public /* synthetic */ InquiryAsk(Integer num, String str, List list, Integer num2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : num, (i2 & 2) != 0 ? "" : str, (i2 & 4) != 0 ? null : list, (i2 & 8) != 0 ? 0 : num2);
    }
}

package com.yddmi.doctor.entity.request;

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
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 22\u00020\u0001:\u000212BE\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fB;\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\rJ\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001aJ\t\u0010 \u001a\u00020\u0003HÆ\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\bHÆ\u0003J\t\u0010\"\u001a\u00020\u0003HÆ\u0003JD\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\u0003HÆ\u0001¢\u0006\u0002\u0010$J\u0013\u0010%\u001a\u00020&2\b\u0010'\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010(\u001a\u00020\u0003HÖ\u0001J\t\u0010)\u001a\u00020\bHÖ\u0001J!\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\u00002\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200HÇ\u0001R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\t\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\u0013\"\u0004\b\u0016\u0010\u0015R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0013\"\u0004\b\u0018\u0010\u0015R\u001e\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u001d\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001c¨\u00063"}, d2 = {"Lcom/yddmi/doctor/entity/request/InquiryUpdateStatus;", "", "seen1", "", "status", "timeCost", "id", "examId", "", "isExam", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IILjava/lang/Integer;ILjava/lang/String;ILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(ILjava/lang/Integer;ILjava/lang/String;I)V", "getExamId", "()Ljava/lang/String;", "setExamId", "(Ljava/lang/String;)V", "getId", "()I", "setId", "(I)V", "setExam", "getStatus", "setStatus", "getTimeCost", "()Ljava/lang/Integer;", "setTimeCost", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "component1", "component2", "component3", "component4", "component5", "copy", "(ILjava/lang/Integer;ILjava/lang/String;I)Lcom/yddmi/doctor/entity/request/InquiryUpdateStatus;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class InquiryUpdateStatus {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private String examId;
    private int id;
    private int isExam;
    private int status;

    @Nullable
    private Integer timeCost;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/InquiryUpdateStatus$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/InquiryUpdateStatus;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<InquiryUpdateStatus> serializer() {
            return InquiryUpdateStatus$$serializer.INSTANCE;
        }
    }

    public InquiryUpdateStatus() {
        this(0, (Integer) null, 0, (String) null, 0, 31, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ InquiryUpdateStatus(int i2, int i3, Integer num, int i4, String str, int i5, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, InquiryUpdateStatus$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.status = -1;
        } else {
            this.status = i3;
        }
        if ((i2 & 2) == 0) {
            this.timeCost = 0;
        } else {
            this.timeCost = num;
        }
        if ((i2 & 4) == 0) {
            this.id = -1;
        } else {
            this.id = i4;
        }
        if ((i2 & 8) == 0) {
            this.examId = "";
        } else {
            this.examId = str;
        }
        if ((i2 & 16) == 0) {
            this.isExam = -1;
        } else {
            this.isExam = i5;
        }
    }

    public static /* synthetic */ InquiryUpdateStatus copy$default(InquiryUpdateStatus inquiryUpdateStatus, int i2, Integer num, int i3, String str, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i2 = inquiryUpdateStatus.status;
        }
        if ((i5 & 2) != 0) {
            num = inquiryUpdateStatus.timeCost;
        }
        Integer num2 = num;
        if ((i5 & 4) != 0) {
            i3 = inquiryUpdateStatus.id;
        }
        int i6 = i3;
        if ((i5 & 8) != 0) {
            str = inquiryUpdateStatus.examId;
        }
        String str2 = str;
        if ((i5 & 16) != 0) {
            i4 = inquiryUpdateStatus.isExam;
        }
        return inquiryUpdateStatus.copy(i2, num2, i6, str2, i4);
    }

    @JvmStatic
    public static final void write$Self(@NotNull InquiryUpdateStatus self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.status != -1) {
            output.encodeIntElement(serialDesc, 0, self.status);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || (num = self.timeCost) == null || num.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 1, IntSerializer.INSTANCE, self.timeCost);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.id != -1) {
            output.encodeIntElement(serialDesc, 2, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.examId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.examId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || self.isExam != -1) {
            output.encodeIntElement(serialDesc, 4, self.isExam);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getStatus() {
        return this.status;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final Integer getTimeCost() {
        return this.timeCost;
    }

    /* renamed from: component3, reason: from getter */
    public final int getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getExamId() {
        return this.examId;
    }

    /* renamed from: component5, reason: from getter */
    public final int getIsExam() {
        return this.isExam;
    }

    @NotNull
    public final InquiryUpdateStatus copy(int status, @Nullable Integer timeCost, int id, @Nullable String examId, int isExam) {
        return new InquiryUpdateStatus(status, timeCost, id, examId, isExam);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InquiryUpdateStatus)) {
            return false;
        }
        InquiryUpdateStatus inquiryUpdateStatus = (InquiryUpdateStatus) other;
        return this.status == inquiryUpdateStatus.status && Intrinsics.areEqual(this.timeCost, inquiryUpdateStatus.timeCost) && this.id == inquiryUpdateStatus.id && Intrinsics.areEqual(this.examId, inquiryUpdateStatus.examId) && this.isExam == inquiryUpdateStatus.isExam;
    }

    @Nullable
    public final String getExamId() {
        return this.examId;
    }

    public final int getId() {
        return this.id;
    }

    public final int getStatus() {
        return this.status;
    }

    @Nullable
    public final Integer getTimeCost() {
        return this.timeCost;
    }

    public int hashCode() {
        int i2 = this.status * 31;
        Integer num = this.timeCost;
        int iHashCode = (((i2 + (num == null ? 0 : num.hashCode())) * 31) + this.id) * 31;
        String str = this.examId;
        return ((iHashCode + (str != null ? str.hashCode() : 0)) * 31) + this.isExam;
    }

    public final int isExam() {
        return this.isExam;
    }

    public final void setExam(int i2) {
        this.isExam = i2;
    }

    public final void setExamId(@Nullable String str) {
        this.examId = str;
    }

    public final void setId(int i2) {
        this.id = i2;
    }

    public final void setStatus(int i2) {
        this.status = i2;
    }

    public final void setTimeCost(@Nullable Integer num) {
        this.timeCost = num;
    }

    @NotNull
    public String toString() {
        return "InquiryUpdateStatus(status=" + this.status + ", timeCost=" + this.timeCost + ", id=" + this.id + ", examId=" + this.examId + ", isExam=" + this.isExam + ")";
    }

    public InquiryUpdateStatus(int i2, @Nullable Integer num, int i3, @Nullable String str, int i4) {
        this.status = i2;
        this.timeCost = num;
        this.id = i3;
        this.examId = str;
        this.isExam = i4;
    }

    public /* synthetic */ InquiryUpdateStatus(int i2, Integer num, int i3, String str, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? -1 : i2, (i5 & 2) != 0 ? 0 : num, (i5 & 4) != 0 ? -1 : i3, (i5 & 8) != 0 ? "" : str, (i5 & 16) != 0 ? -1 : i4);
    }
}

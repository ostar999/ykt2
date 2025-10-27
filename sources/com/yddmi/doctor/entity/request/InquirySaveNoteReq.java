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
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 -2\u00020\u0001:\u0002,-BA\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bB5\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\fJ\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0012J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0012J\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0012J>\u0010\u001e\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010\u001fJ\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010#\u001a\u00020\u0003HÖ\u0001J\t\u0010$\u001a\u00020\u0006HÖ\u0001J!\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u00002\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+HÇ\u0001R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001e\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0015\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001e\u0010\b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0015\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0014R\u001e\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0015\u001a\u0004\b\u0018\u0010\u0012\"\u0004\b\u0019\u0010\u0014¨\u0006."}, d2 = {"Lcom/yddmi/doctor/entity/request/InquirySaveNoteReq;", "", "seen1", "", "id", "content", "", "trainId", "patientId", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;)V", "getContent", "()Ljava/lang/String;", "setContent", "(Ljava/lang/String;)V", "getId", "()Ljava/lang/Integer;", "setId", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getPatientId", "setPatientId", "getTrainId", "setTrainId", "component1", "component2", "component3", "component4", "copy", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;)Lcom/yddmi/doctor/entity/request/InquirySaveNoteReq;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class InquirySaveNoteReq {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private String content;

    @Nullable
    private Integer id;

    @Nullable
    private Integer patientId;

    @Nullable
    private Integer trainId;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/InquirySaveNoteReq$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/InquirySaveNoteReq;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<InquirySaveNoteReq> serializer() {
            return InquirySaveNoteReq$$serializer.INSTANCE;
        }
    }

    public InquirySaveNoteReq() {
        this((Integer) null, (String) null, (Integer) null, (Integer) null, 15, (DefaultConstructorMarker) null);
    }

    public InquirySaveNoteReq(@Nullable Integer num, @Nullable String str, @Nullable Integer num2, @Nullable Integer num3) {
        this.id = num;
        this.content = str;
        this.trainId = num2;
        this.patientId = num3;
    }

    public static /* synthetic */ InquirySaveNoteReq copy$default(InquirySaveNoteReq inquirySaveNoteReq, Integer num, String str, Integer num2, Integer num3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            num = inquirySaveNoteReq.id;
        }
        if ((i2 & 2) != 0) {
            str = inquirySaveNoteReq.content;
        }
        if ((i2 & 4) != 0) {
            num2 = inquirySaveNoteReq.trainId;
        }
        if ((i2 & 8) != 0) {
            num3 = inquirySaveNoteReq.patientId;
        }
        return inquirySaveNoteReq.copy(num, str, num2, num3);
    }

    @JvmStatic
    public static final void write$Self(@NotNull InquirySaveNoteReq self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Integer num3;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || (num = self.id) == null || num.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 0, IntSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.content, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.content);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || (num2 = self.trainId) == null || num2.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 2, IntSerializer.INSTANCE, self.trainId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || (num3 = self.patientId) == null || num3.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 3, IntSerializer.INSTANCE, self.patientId);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Integer getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getContent() {
        return this.content;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final Integer getTrainId() {
        return this.trainId;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final Integer getPatientId() {
        return this.patientId;
    }

    @NotNull
    public final InquirySaveNoteReq copy(@Nullable Integer id, @Nullable String content, @Nullable Integer trainId, @Nullable Integer patientId) {
        return new InquirySaveNoteReq(id, content, trainId, patientId);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InquirySaveNoteReq)) {
            return false;
        }
        InquirySaveNoteReq inquirySaveNoteReq = (InquirySaveNoteReq) other;
        return Intrinsics.areEqual(this.id, inquirySaveNoteReq.id) && Intrinsics.areEqual(this.content, inquirySaveNoteReq.content) && Intrinsics.areEqual(this.trainId, inquirySaveNoteReq.trainId) && Intrinsics.areEqual(this.patientId, inquirySaveNoteReq.patientId);
    }

    @Nullable
    public final String getContent() {
        return this.content;
    }

    @Nullable
    public final Integer getId() {
        return this.id;
    }

    @Nullable
    public final Integer getPatientId() {
        return this.patientId;
    }

    @Nullable
    public final Integer getTrainId() {
        return this.trainId;
    }

    public int hashCode() {
        Integer num = this.id;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        String str = this.content;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        Integer num2 = this.trainId;
        int iHashCode3 = (iHashCode2 + (num2 == null ? 0 : num2.hashCode())) * 31;
        Integer num3 = this.patientId;
        return iHashCode3 + (num3 != null ? num3.hashCode() : 0);
    }

    public final void setContent(@Nullable String str) {
        this.content = str;
    }

    public final void setId(@Nullable Integer num) {
        this.id = num;
    }

    public final void setPatientId(@Nullable Integer num) {
        this.patientId = num;
    }

    public final void setTrainId(@Nullable Integer num) {
        this.trainId = num;
    }

    @NotNull
    public String toString() {
        return "InquirySaveNoteReq(id=" + this.id + ", content=" + this.content + ", trainId=" + this.trainId + ", patientId=" + this.patientId + ")";
    }

    public /* synthetic */ InquirySaveNoteReq(Integer num, String str, Integer num2, Integer num3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? -1 : num, (i2 & 2) != 0 ? "" : str, (i2 & 4) != 0 ? 0 : num2, (i2 & 8) != 0 ? 0 : num3);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ InquirySaveNoteReq(int i2, Integer num, String str, Integer num2, Integer num3, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, InquirySaveNoteReq$$serializer.INSTANCE.getDescriptor());
        }
        this.id = (i2 & 1) == 0 ? -1 : num;
        if ((i2 & 2) == 0) {
            this.content = "";
        } else {
            this.content = str;
        }
        if ((i2 & 4) == 0) {
            this.trainId = 0;
        } else {
            this.trainId = num2;
        }
        if ((i2 & 8) == 0) {
            this.patientId = 0;
        } else {
            this.patientId = num3;
        }
    }
}

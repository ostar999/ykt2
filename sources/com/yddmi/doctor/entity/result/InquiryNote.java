package com.yddmi.doctor.entity.result;

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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 )2\u00020\u0001:\u0002()BA\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bB5\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\fJ\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0012J\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0012J\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0012J>\u0010\u001a\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010\u001bJ\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÖ\u0001J\t\u0010 \u001a\u00020\u0005HÖ\u0001J!\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00002\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'HÇ\u0001R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u0015\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0014\u0010\u0012R\u0015\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0015\u0010\u0012¨\u0006*"}, d2 = {"Lcom/yddmi/doctor/entity/result/InquiryNote;", "", "seen1", "", "content", "", "id", "trainId", "patientId", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)V", "getContent", "()Ljava/lang/String;", "setContent", "(Ljava/lang/String;)V", "getId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getPatientId", "getTrainId", "component1", "component2", "component3", "component4", "copy", "(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)Lcom/yddmi/doctor/entity/result/InquiryNote;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class InquiryNote {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private String content;

    @Nullable
    private final Integer id;

    @Nullable
    private final Integer patientId;

    @Nullable
    private final Integer trainId;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/InquiryNote$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/InquiryNote;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<InquiryNote> serializer() {
            return InquiryNote$$serializer.INSTANCE;
        }
    }

    public InquiryNote() {
        this((String) null, (Integer) null, (Integer) null, (Integer) null, 15, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ InquiryNote(int i2, String str, Integer num, Integer num2, Integer num3, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, InquiryNote$$serializer.INSTANCE.getDescriptor());
        }
        this.content = (i2 & 1) == 0 ? "" : str;
        if ((i2 & 2) == 0) {
            this.id = -1;
        } else {
            this.id = num;
        }
        if ((i2 & 4) == 0) {
            this.trainId = -1;
        } else {
            this.trainId = num2;
        }
        if ((i2 & 8) == 0) {
            this.patientId = -1;
        } else {
            this.patientId = num3;
        }
    }

    public static /* synthetic */ InquiryNote copy$default(InquiryNote inquiryNote, String str, Integer num, Integer num2, Integer num3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = inquiryNote.content;
        }
        if ((i2 & 2) != 0) {
            num = inquiryNote.id;
        }
        if ((i2 & 4) != 0) {
            num2 = inquiryNote.trainId;
        }
        if ((i2 & 8) != 0) {
            num3 = inquiryNote.patientId;
        }
        return inquiryNote.copy(str, num, num2, num3);
    }

    @JvmStatic
    public static final void write$Self(@NotNull InquiryNote self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Integer num3;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.content, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.content);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || (num = self.id) == null || num.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 1, IntSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || (num2 = self.trainId) == null || num2.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 2, IntSerializer.INSTANCE, self.trainId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || (num3 = self.patientId) == null || num3.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 3, IntSerializer.INSTANCE, self.patientId);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getContent() {
        return this.content;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final Integer getId() {
        return this.id;
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
    public final InquiryNote copy(@Nullable String content, @Nullable Integer id, @Nullable Integer trainId, @Nullable Integer patientId) {
        return new InquiryNote(content, id, trainId, patientId);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InquiryNote)) {
            return false;
        }
        InquiryNote inquiryNote = (InquiryNote) other;
        return Intrinsics.areEqual(this.content, inquiryNote.content) && Intrinsics.areEqual(this.id, inquiryNote.id) && Intrinsics.areEqual(this.trainId, inquiryNote.trainId) && Intrinsics.areEqual(this.patientId, inquiryNote.patientId);
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
        String str = this.content;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        Integer num = this.id;
        int iHashCode2 = (iHashCode + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.trainId;
        int iHashCode3 = (iHashCode2 + (num2 == null ? 0 : num2.hashCode())) * 31;
        Integer num3 = this.patientId;
        return iHashCode3 + (num3 != null ? num3.hashCode() : 0);
    }

    public final void setContent(@Nullable String str) {
        this.content = str;
    }

    @NotNull
    public String toString() {
        return "InquiryNote(content=" + this.content + ", id=" + this.id + ", trainId=" + this.trainId + ", patientId=" + this.patientId + ")";
    }

    public InquiryNote(@Nullable String str, @Nullable Integer num, @Nullable Integer num2, @Nullable Integer num3) {
        this.content = str;
        this.id = num;
        this.trainId = num2;
        this.patientId = num3;
    }

    public /* synthetic */ InquiryNote(String str, Integer num, Integer num2, Integer num3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? "" : str, (i2 & 2) != 0 ? -1 : num, (i2 & 4) != 0 ? -1 : num2, (i2 & 8) != 0 ? -1 : num3);
    }
}

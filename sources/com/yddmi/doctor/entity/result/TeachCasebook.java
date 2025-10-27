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
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 !2\u00020\u0001:\u0002 !B-\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nB\u001d\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\u000bJ\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0007HÆ\u0003J!\u0010\u0012\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J!\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fHÇ\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\""}, d2 = {"Lcom/yddmi/doctor/entity/result/TeachCasebook;", "", "seen1", "", "patientInfoVo", "Lcom/yddmi/doctor/entity/result/RowCase;", "ecommendedVideoDTO", "Lcom/yddmi/doctor/entity/result/TeachRow;", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILcom/yddmi/doctor/entity/result/RowCase;Lcom/yddmi/doctor/entity/result/TeachRow;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Lcom/yddmi/doctor/entity/result/RowCase;Lcom/yddmi/doctor/entity/result/TeachRow;)V", "getEcommendedVideoDTO", "()Lcom/yddmi/doctor/entity/result/TeachRow;", "getPatientInfoVo", "()Lcom/yddmi/doctor/entity/result/RowCase;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class TeachCasebook {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final TeachRow ecommendedVideoDTO;

    @Nullable
    private final RowCase patientInfoVo;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/TeachCasebook$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/TeachCasebook;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<TeachCasebook> serializer() {
            return TeachCasebook$$serializer.INSTANCE;
        }
    }

    public TeachCasebook() {
        this((RowCase) null, (TeachRow) (0 == true ? 1 : 0), 3, (DefaultConstructorMarker) (0 == true ? 1 : 0));
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ TeachCasebook(int i2, RowCase rowCase, TeachRow teachRow, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, TeachCasebook$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.patientInfoVo = null;
        } else {
            this.patientInfoVo = rowCase;
        }
        if ((i2 & 2) == 0) {
            this.ecommendedVideoDTO = null;
        } else {
            this.ecommendedVideoDTO = teachRow;
        }
    }

    public static /* synthetic */ TeachCasebook copy$default(TeachCasebook teachCasebook, RowCase rowCase, TeachRow teachRow, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            rowCase = teachCasebook.patientInfoVo;
        }
        if ((i2 & 2) != 0) {
            teachRow = teachCasebook.ecommendedVideoDTO;
        }
        return teachCasebook.copy(rowCase, teachRow);
    }

    @JvmStatic
    public static final void write$Self(@NotNull TeachCasebook self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.patientInfoVo != null) {
            output.encodeNullableSerializableElement(serialDesc, 0, RowCase$$serializer.INSTANCE, self.patientInfoVo);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.ecommendedVideoDTO != null) {
            output.encodeNullableSerializableElement(serialDesc, 1, TeachRow$$serializer.INSTANCE, self.ecommendedVideoDTO);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final RowCase getPatientInfoVo() {
        return this.patientInfoVo;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final TeachRow getEcommendedVideoDTO() {
        return this.ecommendedVideoDTO;
    }

    @NotNull
    public final TeachCasebook copy(@Nullable RowCase patientInfoVo, @Nullable TeachRow ecommendedVideoDTO) {
        return new TeachCasebook(patientInfoVo, ecommendedVideoDTO);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TeachCasebook)) {
            return false;
        }
        TeachCasebook teachCasebook = (TeachCasebook) other;
        return Intrinsics.areEqual(this.patientInfoVo, teachCasebook.patientInfoVo) && Intrinsics.areEqual(this.ecommendedVideoDTO, teachCasebook.ecommendedVideoDTO);
    }

    @Nullable
    public final TeachRow getEcommendedVideoDTO() {
        return this.ecommendedVideoDTO;
    }

    @Nullable
    public final RowCase getPatientInfoVo() {
        return this.patientInfoVo;
    }

    public int hashCode() {
        RowCase rowCase = this.patientInfoVo;
        int iHashCode = (rowCase == null ? 0 : rowCase.hashCode()) * 31;
        TeachRow teachRow = this.ecommendedVideoDTO;
        return iHashCode + (teachRow != null ? teachRow.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "TeachCasebook(patientInfoVo=" + this.patientInfoVo + ", ecommendedVideoDTO=" + this.ecommendedVideoDTO + ")";
    }

    public TeachCasebook(@Nullable RowCase rowCase, @Nullable TeachRow teachRow) {
        this.patientInfoVo = rowCase;
        this.ecommendedVideoDTO = teachRow;
    }

    public /* synthetic */ TeachCasebook(RowCase rowCase, TeachRow teachRow, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : rowCase, (i2 & 2) != 0 ? null : teachRow);
    }
}

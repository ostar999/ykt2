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
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 $2\u00020\u0001:\u0002#$B;\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bB)\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003¢\u0006\u0002\u0010\fJ\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J1\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001b\u001a\u00020\u0006HÖ\u0001J!\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"HÇ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u000eR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000e¨\u0006%"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamCheat;", "", "seen1", "", "cheatNum", "examId", "", "isCheat", "maxCheatNum", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IILjava/lang/String;IILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(ILjava/lang/String;II)V", "getCheatNum", "()I", "getExamId", "()Ljava/lang/String;", "getMaxCheatNum", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class ExamCheat {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private final int cheatNum;

    @NotNull
    private final String examId;
    private final int isCheat;
    private final int maxCheatNum;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamCheat$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/ExamCheat;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<ExamCheat> serializer() {
            return ExamCheat$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ ExamCheat(int i2, int i3, String str, int i4, int i5, SerializationConstructorMarker serializationConstructorMarker) {
        if (3 != (i2 & 3)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 3, ExamCheat$$serializer.INSTANCE.getDescriptor());
        }
        this.cheatNum = i3;
        this.examId = str;
        if ((i2 & 4) == 0) {
            this.isCheat = 0;
        } else {
            this.isCheat = i4;
        }
        if ((i2 & 8) == 0) {
            this.maxCheatNum = 0;
        } else {
            this.maxCheatNum = i5;
        }
    }

    public static /* synthetic */ ExamCheat copy$default(ExamCheat examCheat, int i2, String str, int i3, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i2 = examCheat.cheatNum;
        }
        if ((i5 & 2) != 0) {
            str = examCheat.examId;
        }
        if ((i5 & 4) != 0) {
            i3 = examCheat.isCheat;
        }
        if ((i5 & 8) != 0) {
            i4 = examCheat.maxCheatNum;
        }
        return examCheat.copy(i2, str, i3, i4);
    }

    @JvmStatic
    public static final void write$Self(@NotNull ExamCheat self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        output.encodeIntElement(serialDesc, 0, self.cheatNum);
        output.encodeStringElement(serialDesc, 1, self.examId);
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.isCheat != 0) {
            output.encodeIntElement(serialDesc, 2, self.isCheat);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.maxCheatNum != 0) {
            output.encodeIntElement(serialDesc, 3, self.maxCheatNum);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getCheatNum() {
        return this.cheatNum;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getExamId() {
        return this.examId;
    }

    /* renamed from: component3, reason: from getter */
    public final int getIsCheat() {
        return this.isCheat;
    }

    /* renamed from: component4, reason: from getter */
    public final int getMaxCheatNum() {
        return this.maxCheatNum;
    }

    @NotNull
    public final ExamCheat copy(int cheatNum, @NotNull String examId, int isCheat, int maxCheatNum) {
        Intrinsics.checkNotNullParameter(examId, "examId");
        return new ExamCheat(cheatNum, examId, isCheat, maxCheatNum);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExamCheat)) {
            return false;
        }
        ExamCheat examCheat = (ExamCheat) other;
        return this.cheatNum == examCheat.cheatNum && Intrinsics.areEqual(this.examId, examCheat.examId) && this.isCheat == examCheat.isCheat && this.maxCheatNum == examCheat.maxCheatNum;
    }

    public final int getCheatNum() {
        return this.cheatNum;
    }

    @NotNull
    public final String getExamId() {
        return this.examId;
    }

    public final int getMaxCheatNum() {
        return this.maxCheatNum;
    }

    public int hashCode() {
        return (((((this.cheatNum * 31) + this.examId.hashCode()) * 31) + this.isCheat) * 31) + this.maxCheatNum;
    }

    public final int isCheat() {
        return this.isCheat;
    }

    @NotNull
    public String toString() {
        return "ExamCheat(cheatNum=" + this.cheatNum + ", examId=" + this.examId + ", isCheat=" + this.isCheat + ", maxCheatNum=" + this.maxCheatNum + ")";
    }

    public ExamCheat(int i2, @NotNull String examId, int i3, int i4) {
        Intrinsics.checkNotNullParameter(examId, "examId");
        this.cheatNum = i2;
        this.examId = examId;
        this.isCheat = i3;
        this.maxCheatNum = i4;
    }

    public /* synthetic */ ExamCheat(int i2, String str, int i3, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this(i2, str, (i5 & 4) != 0 ? 0 : i3, (i5 & 8) != 0 ? 0 : i4);
    }
}

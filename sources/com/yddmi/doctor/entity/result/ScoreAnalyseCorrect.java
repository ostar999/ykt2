package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import com.yikaobang.yixue.R2;
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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u001f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 ?2\u00020\u0001:\u0002>?B\u0081\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\u0006\u0010\u0010\u001a\u00020\u0003\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012¢\u0006\u0002\u0010\u0013B\u0087\u0001\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0003¢\u0006\u0002\u0010\u0014J\t\u0010#\u001a\u00020\u0003HÆ\u0003J\t\u0010$\u001a\u00020\u0003HÆ\u0003J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\t\u0010&\u001a\u00020\u0003HÆ\u0003J\t\u0010'\u001a\u00020\u0003HÆ\u0003J\t\u0010(\u001a\u00020\u0003HÆ\u0003J\t\u0010)\u001a\u00020\u0003HÆ\u0003J\t\u0010*\u001a\u00020\u0003HÆ\u0003J\t\u0010+\u001a\u00020\u0003HÆ\u0003J\t\u0010,\u001a\u00020\u0003HÆ\u0003J\t\u0010-\u001a\u00020\u0003HÆ\u0003J\t\u0010.\u001a\u00020\u0003HÆ\u0003J\t\u0010/\u001a\u00020\u0003HÆ\u0003J\u008b\u0001\u00100\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u0003HÆ\u0001J\u0013\u00101\u001a\u0002022\b\u00103\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00104\u001a\u00020\u0003HÖ\u0001J\t\u00105\u001a\u000206HÖ\u0001J!\u00107\u001a\u0002082\u0006\u00109\u001a\u00020\u00002\u0006\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020=HÇ\u0001R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u0016\u0010\u0007\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0016\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0016R\u0016\u0010\n\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u0016\u0010\u000b\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0016R\u0016\u0010\f\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0016R\u0016\u0010\r\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0016R\u0016\u0010\u000e\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0016R\u0016\u0010\u000f\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0016R\u0016\u0010\u0010\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0016¨\u0006@"}, d2 = {"Lcom/yddmi/doctor/entity/result/ScoreAnalyseCorrect;", "", "seen1", "", "askAvg", "askRatio", "assistCheckAvg", "assistCheckRatio", "bodyCheckAvg", "bodyCheckRatio", "diagnoseAvg", "diagnoseRatio", "ratioId", "trainId", "treatmentAvg", "treatmentRatio", "type", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IIIIIIIIIIIIIILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(IIIIIIIIIIIII)V", "getAskAvg", "()I", "getAskRatio", "getAssistCheckAvg", "getAssistCheckRatio", "getBodyCheckAvg", "getBodyCheckRatio", "getDiagnoseAvg", "getDiagnoseRatio", "getRatioId", "getTrainId", "getTreatmentAvg", "getTreatmentRatio", "getType", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class ScoreAnalyseCorrect {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @SerializedName("askAvg")
    private final int askAvg;

    @SerializedName("askRatio")
    private final int askRatio;

    @SerializedName("assistCheckAvg")
    private final int assistCheckAvg;

    @SerializedName("assistCheckRatio")
    private final int assistCheckRatio;

    @SerializedName("bodyCheckAvg")
    private final int bodyCheckAvg;

    @SerializedName("bodyCheckRatio")
    private final int bodyCheckRatio;

    @SerializedName("diagnoseAvg")
    private final int diagnoseAvg;

    @SerializedName("diagnoseRatio")
    private final int diagnoseRatio;

    @SerializedName("ratioId")
    private final int ratioId;

    @SerializedName("trainId")
    private final int trainId;

    @SerializedName("treatmentAvg")
    private final int treatmentAvg;

    @SerializedName("treatmentRatio")
    private final int treatmentRatio;

    @SerializedName("type")
    private final int type;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/ScoreAnalyseCorrect$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/ScoreAnalyseCorrect;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<ScoreAnalyseCorrect> serializer() {
            return ScoreAnalyseCorrect$$serializer.INSTANCE;
        }
    }

    public ScoreAnalyseCorrect() {
        this(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, R2.dimen.preference_seekbar_padding_start, (DefaultConstructorMarker) null);
    }

    public ScoreAnalyseCorrect(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14) {
        this.askAvg = i2;
        this.askRatio = i3;
        this.assistCheckAvg = i4;
        this.assistCheckRatio = i5;
        this.bodyCheckAvg = i6;
        this.bodyCheckRatio = i7;
        this.diagnoseAvg = i8;
        this.diagnoseRatio = i9;
        this.ratioId = i10;
        this.trainId = i11;
        this.treatmentAvg = i12;
        this.treatmentRatio = i13;
        this.type = i14;
    }

    @JvmStatic
    public static final void write$Self(@NotNull ScoreAnalyseCorrect self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.askAvg != 0) {
            output.encodeIntElement(serialDesc, 0, self.askAvg);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.askRatio != 0) {
            output.encodeIntElement(serialDesc, 1, self.askRatio);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.assistCheckAvg != 0) {
            output.encodeIntElement(serialDesc, 2, self.assistCheckAvg);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.assistCheckRatio != 0) {
            output.encodeIntElement(serialDesc, 3, self.assistCheckRatio);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || self.bodyCheckAvg != 0) {
            output.encodeIntElement(serialDesc, 4, self.bodyCheckAvg);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || self.bodyCheckRatio != 0) {
            output.encodeIntElement(serialDesc, 5, self.bodyCheckRatio);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || self.diagnoseAvg != 0) {
            output.encodeIntElement(serialDesc, 6, self.diagnoseAvg);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || self.diagnoseRatio != 0) {
            output.encodeIntElement(serialDesc, 7, self.diagnoseRatio);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || self.ratioId != 0) {
            output.encodeIntElement(serialDesc, 8, self.ratioId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || self.trainId != 0) {
            output.encodeIntElement(serialDesc, 9, self.trainId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || self.treatmentAvg != 0) {
            output.encodeIntElement(serialDesc, 10, self.treatmentAvg);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || self.treatmentRatio != 0) {
            output.encodeIntElement(serialDesc, 11, self.treatmentRatio);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 12) || self.type != -1) {
            output.encodeIntElement(serialDesc, 12, self.type);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getAskAvg() {
        return this.askAvg;
    }

    /* renamed from: component10, reason: from getter */
    public final int getTrainId() {
        return this.trainId;
    }

    /* renamed from: component11, reason: from getter */
    public final int getTreatmentAvg() {
        return this.treatmentAvg;
    }

    /* renamed from: component12, reason: from getter */
    public final int getTreatmentRatio() {
        return this.treatmentRatio;
    }

    /* renamed from: component13, reason: from getter */
    public final int getType() {
        return this.type;
    }

    /* renamed from: component2, reason: from getter */
    public final int getAskRatio() {
        return this.askRatio;
    }

    /* renamed from: component3, reason: from getter */
    public final int getAssistCheckAvg() {
        return this.assistCheckAvg;
    }

    /* renamed from: component4, reason: from getter */
    public final int getAssistCheckRatio() {
        return this.assistCheckRatio;
    }

    /* renamed from: component5, reason: from getter */
    public final int getBodyCheckAvg() {
        return this.bodyCheckAvg;
    }

    /* renamed from: component6, reason: from getter */
    public final int getBodyCheckRatio() {
        return this.bodyCheckRatio;
    }

    /* renamed from: component7, reason: from getter */
    public final int getDiagnoseAvg() {
        return this.diagnoseAvg;
    }

    /* renamed from: component8, reason: from getter */
    public final int getDiagnoseRatio() {
        return this.diagnoseRatio;
    }

    /* renamed from: component9, reason: from getter */
    public final int getRatioId() {
        return this.ratioId;
    }

    @NotNull
    public final ScoreAnalyseCorrect copy(int askAvg, int askRatio, int assistCheckAvg, int assistCheckRatio, int bodyCheckAvg, int bodyCheckRatio, int diagnoseAvg, int diagnoseRatio, int ratioId, int trainId, int treatmentAvg, int treatmentRatio, int type) {
        return new ScoreAnalyseCorrect(askAvg, askRatio, assistCheckAvg, assistCheckRatio, bodyCheckAvg, bodyCheckRatio, diagnoseAvg, diagnoseRatio, ratioId, trainId, treatmentAvg, treatmentRatio, type);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScoreAnalyseCorrect)) {
            return false;
        }
        ScoreAnalyseCorrect scoreAnalyseCorrect = (ScoreAnalyseCorrect) other;
        return this.askAvg == scoreAnalyseCorrect.askAvg && this.askRatio == scoreAnalyseCorrect.askRatio && this.assistCheckAvg == scoreAnalyseCorrect.assistCheckAvg && this.assistCheckRatio == scoreAnalyseCorrect.assistCheckRatio && this.bodyCheckAvg == scoreAnalyseCorrect.bodyCheckAvg && this.bodyCheckRatio == scoreAnalyseCorrect.bodyCheckRatio && this.diagnoseAvg == scoreAnalyseCorrect.diagnoseAvg && this.diagnoseRatio == scoreAnalyseCorrect.diagnoseRatio && this.ratioId == scoreAnalyseCorrect.ratioId && this.trainId == scoreAnalyseCorrect.trainId && this.treatmentAvg == scoreAnalyseCorrect.treatmentAvg && this.treatmentRatio == scoreAnalyseCorrect.treatmentRatio && this.type == scoreAnalyseCorrect.type;
    }

    public final int getAskAvg() {
        return this.askAvg;
    }

    public final int getAskRatio() {
        return this.askRatio;
    }

    public final int getAssistCheckAvg() {
        return this.assistCheckAvg;
    }

    public final int getAssistCheckRatio() {
        return this.assistCheckRatio;
    }

    public final int getBodyCheckAvg() {
        return this.bodyCheckAvg;
    }

    public final int getBodyCheckRatio() {
        return this.bodyCheckRatio;
    }

    public final int getDiagnoseAvg() {
        return this.diagnoseAvg;
    }

    public final int getDiagnoseRatio() {
        return this.diagnoseRatio;
    }

    public final int getRatioId() {
        return this.ratioId;
    }

    public final int getTrainId() {
        return this.trainId;
    }

    public final int getTreatmentAvg() {
        return this.treatmentAvg;
    }

    public final int getTreatmentRatio() {
        return this.treatmentRatio;
    }

    public final int getType() {
        return this.type;
    }

    public int hashCode() {
        return (((((((((((((((((((((((this.askAvg * 31) + this.askRatio) * 31) + this.assistCheckAvg) * 31) + this.assistCheckRatio) * 31) + this.bodyCheckAvg) * 31) + this.bodyCheckRatio) * 31) + this.diagnoseAvg) * 31) + this.diagnoseRatio) * 31) + this.ratioId) * 31) + this.trainId) * 31) + this.treatmentAvg) * 31) + this.treatmentRatio) * 31) + this.type;
    }

    @NotNull
    public String toString() {
        return "ScoreAnalyseCorrect(askAvg=" + this.askAvg + ", askRatio=" + this.askRatio + ", assistCheckAvg=" + this.assistCheckAvg + ", assistCheckRatio=" + this.assistCheckRatio + ", bodyCheckAvg=" + this.bodyCheckAvg + ", bodyCheckRatio=" + this.bodyCheckRatio + ", diagnoseAvg=" + this.diagnoseAvg + ", diagnoseRatio=" + this.diagnoseRatio + ", ratioId=" + this.ratioId + ", trainId=" + this.trainId + ", treatmentAvg=" + this.treatmentAvg + ", treatmentRatio=" + this.treatmentRatio + ", type=" + this.type + ")";
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ ScoreAnalyseCorrect(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, ScoreAnalyseCorrect$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.askAvg = 0;
        } else {
            this.askAvg = i3;
        }
        if ((i2 & 2) == 0) {
            this.askRatio = 0;
        } else {
            this.askRatio = i4;
        }
        if ((i2 & 4) == 0) {
            this.assistCheckAvg = 0;
        } else {
            this.assistCheckAvg = i5;
        }
        if ((i2 & 8) == 0) {
            this.assistCheckRatio = 0;
        } else {
            this.assistCheckRatio = i6;
        }
        if ((i2 & 16) == 0) {
            this.bodyCheckAvg = 0;
        } else {
            this.bodyCheckAvg = i7;
        }
        if ((i2 & 32) == 0) {
            this.bodyCheckRatio = 0;
        } else {
            this.bodyCheckRatio = i8;
        }
        if ((i2 & 64) == 0) {
            this.diagnoseAvg = 0;
        } else {
            this.diagnoseAvg = i9;
        }
        if ((i2 & 128) == 0) {
            this.diagnoseRatio = 0;
        } else {
            this.diagnoseRatio = i10;
        }
        if ((i2 & 256) == 0) {
            this.ratioId = 0;
        } else {
            this.ratioId = i11;
        }
        if ((i2 & 512) == 0) {
            this.trainId = 0;
        } else {
            this.trainId = i12;
        }
        if ((i2 & 1024) == 0) {
            this.treatmentAvg = 0;
        } else {
            this.treatmentAvg = i13;
        }
        if ((i2 & 2048) == 0) {
            this.treatmentRatio = 0;
        } else {
            this.treatmentRatio = i14;
        }
        this.type = (i2 & 4096) == 0 ? -1 : i15;
    }

    public /* synthetic */ ScoreAnalyseCorrect(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, DefaultConstructorMarker defaultConstructorMarker) {
        this((i15 & 1) != 0 ? 0 : i2, (i15 & 2) != 0 ? 0 : i3, (i15 & 4) != 0 ? 0 : i4, (i15 & 8) != 0 ? 0 : i5, (i15 & 16) != 0 ? 0 : i6, (i15 & 32) != 0 ? 0 : i7, (i15 & 64) != 0 ? 0 : i8, (i15 & 128) != 0 ? 0 : i9, (i15 & 256) != 0 ? 0 : i10, (i15 & 512) != 0 ? 0 : i11, (i15 & 1024) != 0 ? 0 : i12, (i15 & 2048) == 0 ? i13 : 0, (i15 & 4096) != 0 ? -1 : i14);
    }
}

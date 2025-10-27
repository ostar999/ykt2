package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 02\u00020\u0001:\u0002/0BY\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eBU\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003¢\u0006\u0002\u0010\u000fJ\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003J\t\u0010 \u001a\u00020\u0003HÆ\u0003JY\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010%\u001a\u00020\u0003HÖ\u0001J\t\u0010&\u001a\u00020'HÖ\u0001J!\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u00002\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.HÇ\u0001R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0016\u0010\u0007\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0016\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0016\u0010\n\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u0016\u0010\u000b\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011¨\u00061"}, d2 = {"Lcom/yddmi/doctor/entity/result/ScoreAnalyseRadar;", "", "seen1", "", "analyseId", "diagnose", "logic", "overall", "precise", "risk", "trainId", "treatment", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IIIIIIIIILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(IIIIIIII)V", "getAnalyseId", "()I", "getDiagnose", "getLogic", "getOverall", "getPrecise", "getRisk", "getTrainId", "getTreatment", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class ScoreAnalyseRadar {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @SerializedName("analyseId")
    private final int analyseId;

    @SerializedName("diagnose")
    private final int diagnose;

    @SerializedName("logic")
    private final int logic;

    @SerializedName("overall")
    private final int overall;

    @SerializedName("precise")
    private final int precise;

    @SerializedName("risk")
    private final int risk;

    @SerializedName("trainId")
    private final int trainId;

    @SerializedName("treatment")
    private final int treatment;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/ScoreAnalyseRadar$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/ScoreAnalyseRadar;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<ScoreAnalyseRadar> serializer() {
            return ScoreAnalyseRadar$$serializer.INSTANCE;
        }
    }

    public ScoreAnalyseRadar() {
        this(0, 0, 0, 0, 0, 0, 0, 0, 255, (DefaultConstructorMarker) null);
    }

    public ScoreAnalyseRadar(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        this.analyseId = i2;
        this.diagnose = i3;
        this.logic = i4;
        this.overall = i5;
        this.precise = i6;
        this.risk = i7;
        this.trainId = i8;
        this.treatment = i9;
    }

    @JvmStatic
    public static final void write$Self(@NotNull ScoreAnalyseRadar self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.analyseId != 0) {
            output.encodeIntElement(serialDesc, 0, self.analyseId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.diagnose != 0) {
            output.encodeIntElement(serialDesc, 1, self.diagnose);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.logic != 0) {
            output.encodeIntElement(serialDesc, 2, self.logic);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.overall != 0) {
            output.encodeIntElement(serialDesc, 3, self.overall);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || self.precise != 0) {
            output.encodeIntElement(serialDesc, 4, self.precise);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || self.risk != 0) {
            output.encodeIntElement(serialDesc, 5, self.risk);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || self.trainId != 0) {
            output.encodeIntElement(serialDesc, 6, self.trainId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || self.treatment != 0) {
            output.encodeIntElement(serialDesc, 7, self.treatment);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getAnalyseId() {
        return this.analyseId;
    }

    /* renamed from: component2, reason: from getter */
    public final int getDiagnose() {
        return this.diagnose;
    }

    /* renamed from: component3, reason: from getter */
    public final int getLogic() {
        return this.logic;
    }

    /* renamed from: component4, reason: from getter */
    public final int getOverall() {
        return this.overall;
    }

    /* renamed from: component5, reason: from getter */
    public final int getPrecise() {
        return this.precise;
    }

    /* renamed from: component6, reason: from getter */
    public final int getRisk() {
        return this.risk;
    }

    /* renamed from: component7, reason: from getter */
    public final int getTrainId() {
        return this.trainId;
    }

    /* renamed from: component8, reason: from getter */
    public final int getTreatment() {
        return this.treatment;
    }

    @NotNull
    public final ScoreAnalyseRadar copy(int analyseId, int diagnose, int logic, int overall, int precise, int risk, int trainId, int treatment) {
        return new ScoreAnalyseRadar(analyseId, diagnose, logic, overall, precise, risk, trainId, treatment);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScoreAnalyseRadar)) {
            return false;
        }
        ScoreAnalyseRadar scoreAnalyseRadar = (ScoreAnalyseRadar) other;
        return this.analyseId == scoreAnalyseRadar.analyseId && this.diagnose == scoreAnalyseRadar.diagnose && this.logic == scoreAnalyseRadar.logic && this.overall == scoreAnalyseRadar.overall && this.precise == scoreAnalyseRadar.precise && this.risk == scoreAnalyseRadar.risk && this.trainId == scoreAnalyseRadar.trainId && this.treatment == scoreAnalyseRadar.treatment;
    }

    public final int getAnalyseId() {
        return this.analyseId;
    }

    public final int getDiagnose() {
        return this.diagnose;
    }

    public final int getLogic() {
        return this.logic;
    }

    public final int getOverall() {
        return this.overall;
    }

    public final int getPrecise() {
        return this.precise;
    }

    public final int getRisk() {
        return this.risk;
    }

    public final int getTrainId() {
        return this.trainId;
    }

    public final int getTreatment() {
        return this.treatment;
    }

    public int hashCode() {
        return (((((((((((((this.analyseId * 31) + this.diagnose) * 31) + this.logic) * 31) + this.overall) * 31) + this.precise) * 31) + this.risk) * 31) + this.trainId) * 31) + this.treatment;
    }

    @NotNull
    public String toString() {
        return "ScoreAnalyseRadar(analyseId=" + this.analyseId + ", diagnose=" + this.diagnose + ", logic=" + this.logic + ", overall=" + this.overall + ", precise=" + this.precise + ", risk=" + this.risk + ", trainId=" + this.trainId + ", treatment=" + this.treatment + ")";
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ ScoreAnalyseRadar(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, ScoreAnalyseRadar$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.analyseId = 0;
        } else {
            this.analyseId = i3;
        }
        if ((i2 & 2) == 0) {
            this.diagnose = 0;
        } else {
            this.diagnose = i4;
        }
        if ((i2 & 4) == 0) {
            this.logic = 0;
        } else {
            this.logic = i5;
        }
        if ((i2 & 8) == 0) {
            this.overall = 0;
        } else {
            this.overall = i6;
        }
        if ((i2 & 16) == 0) {
            this.precise = 0;
        } else {
            this.precise = i7;
        }
        if ((i2 & 32) == 0) {
            this.risk = 0;
        } else {
            this.risk = i8;
        }
        if ((i2 & 64) == 0) {
            this.trainId = 0;
        } else {
            this.trainId = i9;
        }
        if ((i2 & 128) == 0) {
            this.treatment = 0;
        } else {
            this.treatment = i10;
        }
    }

    public /* synthetic */ ScoreAnalyseRadar(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, DefaultConstructorMarker defaultConstructorMarker) {
        this((i10 & 1) != 0 ? 0 : i2, (i10 & 2) != 0 ? 0 : i3, (i10 & 4) != 0 ? 0 : i4, (i10 & 8) != 0 ? 0 : i5, (i10 & 16) != 0 ? 0 : i6, (i10 & 32) != 0 ? 0 : i7, (i10 & 64) != 0 ? 0 : i8, (i10 & 128) == 0 ? i9 : 0);
    }
}

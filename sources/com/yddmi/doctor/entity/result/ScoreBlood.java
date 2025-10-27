package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.yikaobang.yixue.R2;
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
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b'\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 I2\u00020\u0001:\u0002HIB¡\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0010\u0010\u0005\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0018\u00010\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\t\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\t\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\t\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\t\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015¢\u0006\u0002\u0010\u0016B§\u0001\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0012\b\u0002\u0010\u0005\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\u0017J\u0010\u0010-\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0019J\u000b\u0010.\u001a\u0004\u0018\u00010\tHÆ\u0003J\u0010\u0010/\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0019J\u000b\u00100\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\tHÆ\u0003J\u0013\u00102\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0018\u00010\u0006HÆ\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\tHÆ\u0003J\u0010\u00104\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0019J\t\u00105\u001a\u00020\u0003HÆ\u0003J\u000b\u00106\u001a\u0004\u0018\u00010\tHÆ\u0003J\u0010\u00107\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0019J\u000b\u00108\u001a\u0004\u0018\u00010\tHÆ\u0003J\u0010\u00109\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0019J°\u0001\u0010:\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\u0012\b\u0002\u0010\u0005\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\tHÆ\u0001¢\u0006\u0002\u0010;J\u0013\u0010<\u001a\u00020=2\b\u0010>\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010?\u001a\u00020\u0003HÖ\u0001J\t\u0010@\u001a\u00020\tHÖ\u0001J!\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020\u00002\u0006\u0010D\u001a\u00020E2\u0006\u0010F\u001a\u00020GHÇ\u0001R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019R\u001b\u0010\u0005\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0015\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u001f\u0010\u0019R\u0011\u0010\u000b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u001e\"\u0004\b#\u0010$R\u0013\u0010\f\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001eR\u0015\u0010\r\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b&\u0010\u0019R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u001eR\u0015\u0010\u000f\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b(\u0010\u0019R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001eR\u0015\u0010\u0011\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b*\u0010\u0019R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,¨\u0006J"}, d2 = {"Lcom/yddmi/doctor/entity/result/ScoreBlood;", "", "seen1", "", "assistTypeId", "auxiliaryVos", "", "Lcom/yddmi/doctor/entity/result/AuxiliaryVo;", "bodyName", "", "categoryType", "checkType", "fileId", "id", "name", "patientId", "result", "scoreType", "tbOpticalResultVo", "clinicalSignificance", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Integer;Ljava/util/List;Ljava/lang/String;Ljava/lang/Integer;ILjava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Lcom/yddmi/doctor/entity/result/AuxiliaryVo;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Integer;Ljava/util/List;Ljava/lang/String;Ljava/lang/Integer;ILjava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Lcom/yddmi/doctor/entity/result/AuxiliaryVo;Ljava/lang/String;)V", "getAssistTypeId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getAuxiliaryVos", "()Ljava/util/List;", "getBodyName", "()Ljava/lang/String;", "getCategoryType", "getCheckType", "()I", "getClinicalSignificance", "setClinicalSignificance", "(Ljava/lang/String;)V", "getFileId", "getId", "getName", "getPatientId", "getResult", "getScoreType", "getTbOpticalResultVo", "()Lcom/yddmi/doctor/entity/result/AuxiliaryVo;", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Integer;Ljava/util/List;Ljava/lang/String;Ljava/lang/Integer;ILjava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Lcom/yddmi/doctor/entity/result/AuxiliaryVo;Ljava/lang/String;)Lcom/yddmi/doctor/entity/result/ScoreBlood;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class ScoreBlood {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final Integer assistTypeId;

    @Nullable
    private final List<AuxiliaryVo> auxiliaryVos;

    @Nullable
    private final String bodyName;

    @Nullable
    private final Integer categoryType;
    private final int checkType;

    @Nullable
    private String clinicalSignificance;

    @Nullable
    private final String fileId;

    @Nullable
    private final Integer id;

    @Nullable
    private final String name;

    @Nullable
    private final Integer patientId;

    @Nullable
    private final String result;

    @Nullable
    private final Integer scoreType;

    @Nullable
    private final AuxiliaryVo tbOpticalResultVo;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/ScoreBlood$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/ScoreBlood;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<ScoreBlood> serializer() {
            return ScoreBlood$$serializer.INSTANCE;
        }
    }

    public ScoreBlood() {
        this((Integer) null, (List) null, (String) null, (Integer) null, 0, (String) null, (Integer) null, (String) null, (Integer) null, (String) null, (Integer) null, (AuxiliaryVo) null, (String) null, R2.dimen.preference_seekbar_padding_start, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ ScoreBlood(int i2, Integer num, List list, String str, Integer num2, int i3, String str2, Integer num3, String str3, Integer num4, String str4, Integer num5, AuxiliaryVo auxiliaryVo, String str5, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, ScoreBlood$$serializer.INSTANCE.getDescriptor());
        }
        this.assistTypeId = (i2 & 1) == 0 ? -1 : num;
        if ((i2 & 2) == 0) {
            this.auxiliaryVos = null;
        } else {
            this.auxiliaryVos = list;
        }
        if ((i2 & 4) == 0) {
            this.bodyName = "";
        } else {
            this.bodyName = str;
        }
        this.categoryType = (i2 & 8) == 0 ? -1 : num2;
        if ((i2 & 16) == 0) {
            this.checkType = -1;
        } else {
            this.checkType = i3;
        }
        if ((i2 & 32) == 0) {
            this.fileId = "";
        } else {
            this.fileId = str2;
        }
        this.id = (i2 & 64) == 0 ? -1 : num3;
        if ((i2 & 128) == 0) {
            this.name = "";
        } else {
            this.name = str3;
        }
        this.patientId = (i2 & 256) == 0 ? -1 : num4;
        if ((i2 & 512) == 0) {
            this.result = "";
        } else {
            this.result = str4;
        }
        this.scoreType = (i2 & 1024) == 0 ? -1 : num5;
        if ((i2 & 2048) == 0) {
            this.tbOpticalResultVo = null;
        } else {
            this.tbOpticalResultVo = auxiliaryVo;
        }
        if ((i2 & 4096) == 0) {
            this.clinicalSignificance = "";
        } else {
            this.clinicalSignificance = str5;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull ScoreBlood self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4;
        Integer num5;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || (num = self.assistTypeId) == null || num.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 0, IntSerializer.INSTANCE, self.assistTypeId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.auxiliaryVos != null) {
            output.encodeNullableSerializableElement(serialDesc, 1, new ArrayListSerializer(BuiltinSerializersKt.getNullable(AuxiliaryVo$$serializer.INSTANCE)), self.auxiliaryVos);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.bodyName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.bodyName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || (num2 = self.categoryType) == null || num2.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 3, IntSerializer.INSTANCE, self.categoryType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || self.checkType != -1) {
            output.encodeIntElement(serialDesc, 4, self.checkType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.fileId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.fileId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || (num3 = self.id) == null || num3.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 6, IntSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 7, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || (num4 = self.patientId) == null || num4.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 8, IntSerializer.INSTANCE, self.patientId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || !Intrinsics.areEqual(self.result, "")) {
            output.encodeNullableSerializableElement(serialDesc, 9, StringSerializer.INSTANCE, self.result);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || (num5 = self.scoreType) == null || num5.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 10, IntSerializer.INSTANCE, self.scoreType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || self.tbOpticalResultVo != null) {
            output.encodeNullableSerializableElement(serialDesc, 11, AuxiliaryVo$$serializer.INSTANCE, self.tbOpticalResultVo);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 12) || !Intrinsics.areEqual(self.clinicalSignificance, "")) {
            output.encodeNullableSerializableElement(serialDesc, 12, StringSerializer.INSTANCE, self.clinicalSignificance);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Integer getAssistTypeId() {
        return this.assistTypeId;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getResult() {
        return this.result;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final Integer getScoreType() {
        return this.scoreType;
    }

    @Nullable
    /* renamed from: component12, reason: from getter */
    public final AuxiliaryVo getTbOpticalResultVo() {
        return this.tbOpticalResultVo;
    }

    @Nullable
    /* renamed from: component13, reason: from getter */
    public final String getClinicalSignificance() {
        return this.clinicalSignificance;
    }

    @Nullable
    public final List<AuxiliaryVo> component2() {
        return this.auxiliaryVos;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getBodyName() {
        return this.bodyName;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final Integer getCategoryType() {
        return this.categoryType;
    }

    /* renamed from: component5, reason: from getter */
    public final int getCheckType() {
        return this.checkType;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getFileId() {
        return this.fileId;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final Integer getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final Integer getPatientId() {
        return this.patientId;
    }

    @NotNull
    public final ScoreBlood copy(@Nullable Integer assistTypeId, @Nullable List<AuxiliaryVo> auxiliaryVos, @Nullable String bodyName, @Nullable Integer categoryType, int checkType, @Nullable String fileId, @Nullable Integer id, @Nullable String name, @Nullable Integer patientId, @Nullable String result, @Nullable Integer scoreType, @Nullable AuxiliaryVo tbOpticalResultVo, @Nullable String clinicalSignificance) {
        return new ScoreBlood(assistTypeId, auxiliaryVos, bodyName, categoryType, checkType, fileId, id, name, patientId, result, scoreType, tbOpticalResultVo, clinicalSignificance);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScoreBlood)) {
            return false;
        }
        ScoreBlood scoreBlood = (ScoreBlood) other;
        return Intrinsics.areEqual(this.assistTypeId, scoreBlood.assistTypeId) && Intrinsics.areEqual(this.auxiliaryVos, scoreBlood.auxiliaryVos) && Intrinsics.areEqual(this.bodyName, scoreBlood.bodyName) && Intrinsics.areEqual(this.categoryType, scoreBlood.categoryType) && this.checkType == scoreBlood.checkType && Intrinsics.areEqual(this.fileId, scoreBlood.fileId) && Intrinsics.areEqual(this.id, scoreBlood.id) && Intrinsics.areEqual(this.name, scoreBlood.name) && Intrinsics.areEqual(this.patientId, scoreBlood.patientId) && Intrinsics.areEqual(this.result, scoreBlood.result) && Intrinsics.areEqual(this.scoreType, scoreBlood.scoreType) && Intrinsics.areEqual(this.tbOpticalResultVo, scoreBlood.tbOpticalResultVo) && Intrinsics.areEqual(this.clinicalSignificance, scoreBlood.clinicalSignificance);
    }

    @Nullable
    public final Integer getAssistTypeId() {
        return this.assistTypeId;
    }

    @Nullable
    public final List<AuxiliaryVo> getAuxiliaryVos() {
        return this.auxiliaryVos;
    }

    @Nullable
    public final String getBodyName() {
        return this.bodyName;
    }

    @Nullable
    public final Integer getCategoryType() {
        return this.categoryType;
    }

    public final int getCheckType() {
        return this.checkType;
    }

    @Nullable
    public final String getClinicalSignificance() {
        return this.clinicalSignificance;
    }

    @Nullable
    public final String getFileId() {
        return this.fileId;
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
    public final Integer getPatientId() {
        return this.patientId;
    }

    @Nullable
    public final String getResult() {
        return this.result;
    }

    @Nullable
    public final Integer getScoreType() {
        return this.scoreType;
    }

    @Nullable
    public final AuxiliaryVo getTbOpticalResultVo() {
        return this.tbOpticalResultVo;
    }

    public int hashCode() {
        Integer num = this.assistTypeId;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        List<AuxiliaryVo> list = this.auxiliaryVos;
        int iHashCode2 = (iHashCode + (list == null ? 0 : list.hashCode())) * 31;
        String str = this.bodyName;
        int iHashCode3 = (iHashCode2 + (str == null ? 0 : str.hashCode())) * 31;
        Integer num2 = this.categoryType;
        int iHashCode4 = (((iHashCode3 + (num2 == null ? 0 : num2.hashCode())) * 31) + this.checkType) * 31;
        String str2 = this.fileId;
        int iHashCode5 = (iHashCode4 + (str2 == null ? 0 : str2.hashCode())) * 31;
        Integer num3 = this.id;
        int iHashCode6 = (iHashCode5 + (num3 == null ? 0 : num3.hashCode())) * 31;
        String str3 = this.name;
        int iHashCode7 = (iHashCode6 + (str3 == null ? 0 : str3.hashCode())) * 31;
        Integer num4 = this.patientId;
        int iHashCode8 = (iHashCode7 + (num4 == null ? 0 : num4.hashCode())) * 31;
        String str4 = this.result;
        int iHashCode9 = (iHashCode8 + (str4 == null ? 0 : str4.hashCode())) * 31;
        Integer num5 = this.scoreType;
        int iHashCode10 = (iHashCode9 + (num5 == null ? 0 : num5.hashCode())) * 31;
        AuxiliaryVo auxiliaryVo = this.tbOpticalResultVo;
        int iHashCode11 = (iHashCode10 + (auxiliaryVo == null ? 0 : auxiliaryVo.hashCode())) * 31;
        String str5 = this.clinicalSignificance;
        return iHashCode11 + (str5 != null ? str5.hashCode() : 0);
    }

    public final void setClinicalSignificance(@Nullable String str) {
        this.clinicalSignificance = str;
    }

    @NotNull
    public String toString() {
        return "ScoreBlood(assistTypeId=" + this.assistTypeId + ", auxiliaryVos=" + this.auxiliaryVos + ", bodyName=" + this.bodyName + ", categoryType=" + this.categoryType + ", checkType=" + this.checkType + ", fileId=" + this.fileId + ", id=" + this.id + ", name=" + this.name + ", patientId=" + this.patientId + ", result=" + this.result + ", scoreType=" + this.scoreType + ", tbOpticalResultVo=" + this.tbOpticalResultVo + ", clinicalSignificance=" + this.clinicalSignificance + ")";
    }

    public ScoreBlood(@Nullable Integer num, @Nullable List<AuxiliaryVo> list, @Nullable String str, @Nullable Integer num2, int i2, @Nullable String str2, @Nullable Integer num3, @Nullable String str3, @Nullable Integer num4, @Nullable String str4, @Nullable Integer num5, @Nullable AuxiliaryVo auxiliaryVo, @Nullable String str5) {
        this.assistTypeId = num;
        this.auxiliaryVos = list;
        this.bodyName = str;
        this.categoryType = num2;
        this.checkType = i2;
        this.fileId = str2;
        this.id = num3;
        this.name = str3;
        this.patientId = num4;
        this.result = str4;
        this.scoreType = num5;
        this.tbOpticalResultVo = auxiliaryVo;
        this.clinicalSignificance = str5;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ ScoreBlood(Integer num, List list, String str, Integer num2, int i2, String str2, Integer num3, String str3, Integer num4, String str4, Integer num5, AuxiliaryVo auxiliaryVo, String str5, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? num : num, (i3 & 2) != 0 ? null : list, (i3 & 4) != 0 ? "" : str, (i3 & 8) != 0 ? num : num2, (i3 & 16) == 0 ? i2 : -1, (i3 & 32) != 0 ? "" : str2, (i3 & 64) != 0 ? num : num3, (i3 & 128) != 0 ? "" : str3, (i3 & 256) != 0 ? num : num4, (i3 & 512) != 0 ? "" : str4, (i3 & 1024) == 0 ? num5 : -1, (i3 & 2048) == 0 ? auxiliaryVo : null, (i3 & 4096) == 0 ? str5 : "");
    }
}

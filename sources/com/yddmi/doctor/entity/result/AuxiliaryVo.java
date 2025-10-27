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
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b&\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 H2\u00020\u0001:\u0002GHB©\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\u0013\u001a\u00020\u0003\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015¢\u0006\u0002\u0010\u0016B³\u0001\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0003¢\u0006\u0002\u0010\u0017J\u0010\u0010*\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0019J\t\u0010+\u001a\u00020\u0003HÆ\u0003J\t\u0010,\u001a\u00020\u0003HÆ\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010/\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u00100\u001a\u00020\u0003HÆ\u0003J\u0010\u00101\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0019J\u000b\u00102\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u0010\u00103\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0019J\u000b\u00104\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u0010\u00105\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0019J\u000b\u00106\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u00108\u001a\u0004\u0018\u00010\u0007HÆ\u0003J¼\u0001\u00109\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u0013\u001a\u00020\u0003HÆ\u0001¢\u0006\u0002\u0010:J\u0013\u0010;\u001a\u00020<2\b\u0010=\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010>\u001a\u00020\u0003HÖ\u0001J\t\u0010?\u001a\u00020\u0007HÖ\u0001J!\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020\u00002\u0006\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020FHÇ\u0001R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u000f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u001d\u0010\u0019R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u001fR\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001fR\u0015\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\"\u0010\u0019R\u0011\u0010\u0013\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u001cR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001fR\u0015\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b$\u0010\u0019R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001fR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001fR\u0013\u0010\f\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u001fR\u0011\u0010\u000e\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001cR\u0013\u0010\r\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001f¨\u0006I"}, d2 = {"Lcom/yddmi/doctor/entity/result/AuxiliaryVo;", "", "seen1", "", "auxiliaryId", "checkId", "code", "", "id", "name", "patientId", "reference", "result", "unit", "scanType", "bodyId", "fileId", "content", "prompt", "isAbnormal", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;ILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V", "getAuxiliaryId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getBodyId", "()I", "getCheckId", "getCode", "()Ljava/lang/String;", "getContent", "getFileId", "getId", "getName", "getPatientId", "getPrompt", "getReference", "getResult", "getScanType", "getUnit", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;I)Lcom/yddmi/doctor/entity/result/AuxiliaryVo;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class AuxiliaryVo {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final Integer auxiliaryId;
    private final int bodyId;

    @Nullable
    private final Integer checkId;

    @Nullable
    private final String code;

    @Nullable
    private final String content;

    @Nullable
    private final String fileId;

    @Nullable
    private final Integer id;
    private final int isAbnormal;

    @Nullable
    private final String name;

    @Nullable
    private final Integer patientId;

    @Nullable
    private final String prompt;

    @Nullable
    private final String reference;

    @Nullable
    private final String result;
    private final int scanType;

    @Nullable
    private final String unit;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/AuxiliaryVo$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/AuxiliaryVo;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<AuxiliaryVo> serializer() {
            return AuxiliaryVo$$serializer.INSTANCE;
        }
    }

    public AuxiliaryVo() {
        this((Integer) null, (Integer) null, (String) null, (Integer) null, (String) null, (Integer) null, (String) null, (String) null, (String) null, 0, 0, (String) null, (String) null, (String) null, 0, 32767, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ AuxiliaryVo(int i2, Integer num, Integer num2, String str, Integer num3, String str2, Integer num4, String str3, String str4, String str5, int i3, int i4, String str6, String str7, String str8, int i5, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, AuxiliaryVo$$serializer.INSTANCE.getDescriptor());
        }
        this.auxiliaryId = (i2 & 1) == 0 ? -1 : num;
        this.checkId = (i2 & 2) == 0 ? -1 : num2;
        if ((i2 & 4) == 0) {
            this.code = "";
        } else {
            this.code = str;
        }
        this.id = (i2 & 8) == 0 ? -1 : num3;
        if ((i2 & 16) == 0) {
            this.name = "";
        } else {
            this.name = str2;
        }
        this.patientId = (i2 & 32) == 0 ? -1 : num4;
        if ((i2 & 64) == 0) {
            this.reference = "";
        } else {
            this.reference = str3;
        }
        if ((i2 & 128) == 0) {
            this.result = "";
        } else {
            this.result = str4;
        }
        if ((i2 & 256) == 0) {
            this.unit = "";
        } else {
            this.unit = str5;
        }
        if ((i2 & 512) == 0) {
            this.scanType = -1;
        } else {
            this.scanType = i3;
        }
        if ((i2 & 1024) == 0) {
            this.bodyId = -1;
        } else {
            this.bodyId = i4;
        }
        if ((i2 & 2048) == 0) {
            this.fileId = "";
        } else {
            this.fileId = str6;
        }
        if ((i2 & 4096) == 0) {
            this.content = "";
        } else {
            this.content = str7;
        }
        if ((i2 & 8192) == 0) {
            this.prompt = "";
        } else {
            this.prompt = str8;
        }
        if ((i2 & 16384) == 0) {
            this.isAbnormal = -1;
        } else {
            this.isAbnormal = i5;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull AuxiliaryVo self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || (num = self.auxiliaryId) == null || num.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 0, IntSerializer.INSTANCE, self.auxiliaryId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || (num2 = self.checkId) == null || num2.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 1, IntSerializer.INSTANCE, self.checkId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.code, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.code);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || (num3 = self.id) == null || num3.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 3, IntSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || (num4 = self.patientId) == null || num4.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 5, IntSerializer.INSTANCE, self.patientId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.reference, "")) {
            output.encodeNullableSerializableElement(serialDesc, 6, StringSerializer.INSTANCE, self.reference);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || !Intrinsics.areEqual(self.result, "")) {
            output.encodeNullableSerializableElement(serialDesc, 7, StringSerializer.INSTANCE, self.result);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || !Intrinsics.areEqual(self.unit, "")) {
            output.encodeNullableSerializableElement(serialDesc, 8, StringSerializer.INSTANCE, self.unit);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || self.scanType != -1) {
            output.encodeIntElement(serialDesc, 9, self.scanType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || self.bodyId != -1) {
            output.encodeIntElement(serialDesc, 10, self.bodyId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || !Intrinsics.areEqual(self.fileId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 11, StringSerializer.INSTANCE, self.fileId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 12) || !Intrinsics.areEqual(self.content, "")) {
            output.encodeNullableSerializableElement(serialDesc, 12, StringSerializer.INSTANCE, self.content);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 13) || !Intrinsics.areEqual(self.prompt, "")) {
            output.encodeNullableSerializableElement(serialDesc, 13, StringSerializer.INSTANCE, self.prompt);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 14) || self.isAbnormal != -1) {
            output.encodeIntElement(serialDesc, 14, self.isAbnormal);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Integer getAuxiliaryId() {
        return this.auxiliaryId;
    }

    /* renamed from: component10, reason: from getter */
    public final int getScanType() {
        return this.scanType;
    }

    /* renamed from: component11, reason: from getter */
    public final int getBodyId() {
        return this.bodyId;
    }

    @Nullable
    /* renamed from: component12, reason: from getter */
    public final String getFileId() {
        return this.fileId;
    }

    @Nullable
    /* renamed from: component13, reason: from getter */
    public final String getContent() {
        return this.content;
    }

    @Nullable
    /* renamed from: component14, reason: from getter */
    public final String getPrompt() {
        return this.prompt;
    }

    /* renamed from: component15, reason: from getter */
    public final int getIsAbnormal() {
        return this.isAbnormal;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final Integer getCheckId() {
        return this.checkId;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getCode() {
        return this.code;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final Integer getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final Integer getPatientId() {
        return this.patientId;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getReference() {
        return this.reference;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getResult() {
        return this.result;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getUnit() {
        return this.unit;
    }

    @NotNull
    public final AuxiliaryVo copy(@Nullable Integer auxiliaryId, @Nullable Integer checkId, @Nullable String code, @Nullable Integer id, @Nullable String name, @Nullable Integer patientId, @Nullable String reference, @Nullable String result, @Nullable String unit, int scanType, int bodyId, @Nullable String fileId, @Nullable String content, @Nullable String prompt, int isAbnormal) {
        return new AuxiliaryVo(auxiliaryId, checkId, code, id, name, patientId, reference, result, unit, scanType, bodyId, fileId, content, prompt, isAbnormal);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AuxiliaryVo)) {
            return false;
        }
        AuxiliaryVo auxiliaryVo = (AuxiliaryVo) other;
        return Intrinsics.areEqual(this.auxiliaryId, auxiliaryVo.auxiliaryId) && Intrinsics.areEqual(this.checkId, auxiliaryVo.checkId) && Intrinsics.areEqual(this.code, auxiliaryVo.code) && Intrinsics.areEqual(this.id, auxiliaryVo.id) && Intrinsics.areEqual(this.name, auxiliaryVo.name) && Intrinsics.areEqual(this.patientId, auxiliaryVo.patientId) && Intrinsics.areEqual(this.reference, auxiliaryVo.reference) && Intrinsics.areEqual(this.result, auxiliaryVo.result) && Intrinsics.areEqual(this.unit, auxiliaryVo.unit) && this.scanType == auxiliaryVo.scanType && this.bodyId == auxiliaryVo.bodyId && Intrinsics.areEqual(this.fileId, auxiliaryVo.fileId) && Intrinsics.areEqual(this.content, auxiliaryVo.content) && Intrinsics.areEqual(this.prompt, auxiliaryVo.prompt) && this.isAbnormal == auxiliaryVo.isAbnormal;
    }

    @Nullable
    public final Integer getAuxiliaryId() {
        return this.auxiliaryId;
    }

    public final int getBodyId() {
        return this.bodyId;
    }

    @Nullable
    public final Integer getCheckId() {
        return this.checkId;
    }

    @Nullable
    public final String getCode() {
        return this.code;
    }

    @Nullable
    public final String getContent() {
        return this.content;
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
    public final String getPrompt() {
        return this.prompt;
    }

    @Nullable
    public final String getReference() {
        return this.reference;
    }

    @Nullable
    public final String getResult() {
        return this.result;
    }

    public final int getScanType() {
        return this.scanType;
    }

    @Nullable
    public final String getUnit() {
        return this.unit;
    }

    public int hashCode() {
        Integer num = this.auxiliaryId;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        Integer num2 = this.checkId;
        int iHashCode2 = (iHashCode + (num2 == null ? 0 : num2.hashCode())) * 31;
        String str = this.code;
        int iHashCode3 = (iHashCode2 + (str == null ? 0 : str.hashCode())) * 31;
        Integer num3 = this.id;
        int iHashCode4 = (iHashCode3 + (num3 == null ? 0 : num3.hashCode())) * 31;
        String str2 = this.name;
        int iHashCode5 = (iHashCode4 + (str2 == null ? 0 : str2.hashCode())) * 31;
        Integer num4 = this.patientId;
        int iHashCode6 = (iHashCode5 + (num4 == null ? 0 : num4.hashCode())) * 31;
        String str3 = this.reference;
        int iHashCode7 = (iHashCode6 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.result;
        int iHashCode8 = (iHashCode7 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.unit;
        int iHashCode9 = (((((iHashCode8 + (str5 == null ? 0 : str5.hashCode())) * 31) + this.scanType) * 31) + this.bodyId) * 31;
        String str6 = this.fileId;
        int iHashCode10 = (iHashCode9 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.content;
        int iHashCode11 = (iHashCode10 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.prompt;
        return ((iHashCode11 + (str8 != null ? str8.hashCode() : 0)) * 31) + this.isAbnormal;
    }

    public final int isAbnormal() {
        return this.isAbnormal;
    }

    @NotNull
    public String toString() {
        return "AuxiliaryVo(auxiliaryId=" + this.auxiliaryId + ", checkId=" + this.checkId + ", code=" + this.code + ", id=" + this.id + ", name=" + this.name + ", patientId=" + this.patientId + ", reference=" + this.reference + ", result=" + this.result + ", unit=" + this.unit + ", scanType=" + this.scanType + ", bodyId=" + this.bodyId + ", fileId=" + this.fileId + ", content=" + this.content + ", prompt=" + this.prompt + ", isAbnormal=" + this.isAbnormal + ")";
    }

    public AuxiliaryVo(@Nullable Integer num, @Nullable Integer num2, @Nullable String str, @Nullable Integer num3, @Nullable String str2, @Nullable Integer num4, @Nullable String str3, @Nullable String str4, @Nullable String str5, int i2, int i3, @Nullable String str6, @Nullable String str7, @Nullable String str8, int i4) {
        this.auxiliaryId = num;
        this.checkId = num2;
        this.code = str;
        this.id = num3;
        this.name = str2;
        this.patientId = num4;
        this.reference = str3;
        this.result = str4;
        this.unit = str5;
        this.scanType = i2;
        this.bodyId = i3;
        this.fileId = str6;
        this.content = str7;
        this.prompt = str8;
        this.isAbnormal = i4;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ AuxiliaryVo(Integer num, Integer num2, String str, Integer num3, String str2, Integer num4, String str3, String str4, String str5, int i2, int i3, String str6, String str7, String str8, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? num : num, (i5 & 2) != 0 ? num : num2, (i5 & 4) != 0 ? "" : str, (i5 & 8) != 0 ? num : num3, (i5 & 16) != 0 ? "" : str2, (i5 & 32) == 0 ? num4 : -1, (i5 & 64) != 0 ? "" : str3, (i5 & 128) != 0 ? "" : str4, (i5 & 256) != 0 ? "" : str5, (i5 & 512) != 0 ? -1 : i2, (i5 & 1024) != 0 ? -1 : i3, (i5 & 2048) != 0 ? "" : str6, (i5 & 4096) != 0 ? "" : str7, (i5 & 8192) == 0 ? str8 : "", (i5 & 16384) != 0 ? -1 : i4);
    }
}

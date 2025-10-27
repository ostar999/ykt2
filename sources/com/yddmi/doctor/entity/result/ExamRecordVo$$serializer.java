package com.yddmi.doctor.entity.result;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.UnknownFieldException;
import kotlinx.serialization.builtins.BuiltinSerializersKt;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeDecoder;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.encoding.Decoder;
import kotlinx.serialization.encoding.Encoder;
import kotlinx.serialization.internal.GeneratedSerializer;
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginGeneratedSerialDescriptor;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/ExamRecordVo.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/ExamRecordVo;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class ExamRecordVo$$serializer implements GeneratedSerializer<ExamRecordVo> {

    @NotNull
    public static final ExamRecordVo$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        ExamRecordVo$$serializer examRecordVo$$serializer = new ExamRecordVo$$serializer();
        INSTANCE = examRecordVo$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.ExamRecordVo", examRecordVo$$serializer, 16);
        pluginGeneratedSerialDescriptor.addElement("candidateId", true);
        pluginGeneratedSerialDescriptor.addElement("caseName", true);
        pluginGeneratedSerialDescriptor.addElement("degree", true);
        pluginGeneratedSerialDescriptor.addElement("examId", true);
        pluginGeneratedSerialDescriptor.addElement("id", true);
        pluginGeneratedSerialDescriptor.addElement("patientId", true);
        pluginGeneratedSerialDescriptor.addElement("score", true);
        pluginGeneratedSerialDescriptor.addElement("status", true);
        pluginGeneratedSerialDescriptor.addElement("timeCost", true);
        pluginGeneratedSerialDescriptor.addElement("trainId", true);
        pluginGeneratedSerialDescriptor.addElement("endTime", true);
        pluginGeneratedSerialDescriptor.addElement("name", true);
        pluginGeneratedSerialDescriptor.addElement("paperId", true);
        pluginGeneratedSerialDescriptor.addElement("paperScore", true);
        pluginGeneratedSerialDescriptor.addElement("questionCount", true);
        pluginGeneratedSerialDescriptor.addElement("startTime", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private ExamRecordVo$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        return new KSerializer[]{BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, BuiltinSerializersKt.getNullable(stringSerializer)};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public ExamRecordVo deserialize(@NotNull Decoder decoder) {
        Object objDecodeNullableSerializableElement;
        Object obj;
        int iDecodeIntElement;
        Object obj2;
        Object objDecodeNullableSerializableElement2;
        Object objDecodeNullableSerializableElement3;
        Object objDecodeNullableSerializableElement4;
        Object objDecodeNullableSerializableElement5;
        Object objDecodeNullableSerializableElement6;
        int i2;
        Object objDecodeNullableSerializableElement7;
        Object objDecodeNullableSerializableElement8;
        Object objDecodeNullableSerializableElement9;
        Object objDecodeNullableSerializableElement10;
        Object objDecodeNullableSerializableElement11;
        Object objDecodeNullableSerializableElement12;
        Object objDecodeNullableSerializableElement13;
        Object obj3;
        char c3;
        Object obj4;
        Object obj5;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        Object objDecodeNullableSerializableElement14 = null;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            IntSerializer intSerializer = IntSerializer.INSTANCE;
            Object objDecodeNullableSerializableElement15 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, intSerializer, null);
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, stringSerializer, null);
            objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, stringSerializer, null);
            objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, stringSerializer, null);
            objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, stringSerializer, null);
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, intSerializer, null);
            objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, stringSerializer, null);
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, intSerializer, null);
            objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, intSerializer, null);
            Object objDecodeNullableSerializableElement16 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, intSerializer, null);
            objDecodeNullableSerializableElement13 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 10, stringSerializer, null);
            objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 11, stringSerializer, null);
            objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 12, stringSerializer, null);
            objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 13, stringSerializer, null);
            iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 14);
            i2 = 65535;
            obj = objDecodeNullableSerializableElement16;
            objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 15, stringSerializer, null);
            obj2 = objDecodeNullableSerializableElement15;
        } else {
            int i3 = 0;
            int iDecodeIntElement2 = 0;
            boolean z2 = true;
            Object objDecodeNullableSerializableElement17 = null;
            Object objDecodeNullableSerializableElement18 = null;
            Object objDecodeNullableSerializableElement19 = null;
            Object objDecodeNullableSerializableElement20 = null;
            Object objDecodeNullableSerializableElement21 = null;
            Object objDecodeNullableSerializableElement22 = null;
            Object objDecodeNullableSerializableElement23 = null;
            Object objDecodeNullableSerializableElement24 = null;
            Object objDecodeNullableSerializableElement25 = null;
            Object objDecodeNullableSerializableElement26 = null;
            Object objDecodeNullableSerializableElement27 = null;
            Object objDecodeNullableSerializableElement28 = null;
            Object objDecodeNullableSerializableElement29 = null;
            Object objDecodeNullableSerializableElement30 = null;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                        objDecodeNullableSerializableElement14 = objDecodeNullableSerializableElement14;
                        objDecodeNullableSerializableElement19 = objDecodeNullableSerializableElement19;
                        objDecodeNullableSerializableElement28 = objDecodeNullableSerializableElement28;
                        objDecodeNullableSerializableElement18 = objDecodeNullableSerializableElement18;
                    case 0:
                        Object obj6 = objDecodeNullableSerializableElement17;
                        i3 |= 1;
                        objDecodeNullableSerializableElement14 = objDecodeNullableSerializableElement14;
                        objDecodeNullableSerializableElement19 = objDecodeNullableSerializableElement19;
                        objDecodeNullableSerializableElement18 = objDecodeNullableSerializableElement18;
                        objDecodeNullableSerializableElement28 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, IntSerializer.INSTANCE, objDecodeNullableSerializableElement28);
                        objDecodeNullableSerializableElement17 = obj6;
                    case 1:
                        objDecodeNullableSerializableElement17 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, StringSerializer.INSTANCE, objDecodeNullableSerializableElement17);
                        i3 |= 2;
                        objDecodeNullableSerializableElement14 = objDecodeNullableSerializableElement14;
                        objDecodeNullableSerializableElement18 = objDecodeNullableSerializableElement18;
                    case 2:
                        obj4 = objDecodeNullableSerializableElement17;
                        obj5 = objDecodeNullableSerializableElement14;
                        objDecodeNullableSerializableElement19 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, StringSerializer.INSTANCE, objDecodeNullableSerializableElement19);
                        i3 |= 4;
                        objDecodeNullableSerializableElement14 = obj5;
                        objDecodeNullableSerializableElement17 = obj4;
                    case 3:
                        obj4 = objDecodeNullableSerializableElement17;
                        obj5 = objDecodeNullableSerializableElement14;
                        objDecodeNullableSerializableElement18 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, StringSerializer.INSTANCE, objDecodeNullableSerializableElement18);
                        i3 |= 8;
                        objDecodeNullableSerializableElement14 = obj5;
                        objDecodeNullableSerializableElement17 = obj4;
                    case 4:
                        obj4 = objDecodeNullableSerializableElement17;
                        obj5 = objDecodeNullableSerializableElement14;
                        objDecodeNullableSerializableElement25 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, StringSerializer.INSTANCE, objDecodeNullableSerializableElement25);
                        i3 |= 16;
                        objDecodeNullableSerializableElement14 = obj5;
                        objDecodeNullableSerializableElement17 = obj4;
                    case 5:
                        obj4 = objDecodeNullableSerializableElement17;
                        obj5 = objDecodeNullableSerializableElement14;
                        objDecodeNullableSerializableElement27 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, IntSerializer.INSTANCE, objDecodeNullableSerializableElement27);
                        i3 |= 32;
                        objDecodeNullableSerializableElement14 = obj5;
                        objDecodeNullableSerializableElement17 = obj4;
                    case 6:
                        obj4 = objDecodeNullableSerializableElement17;
                        obj5 = objDecodeNullableSerializableElement14;
                        objDecodeNullableSerializableElement24 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, StringSerializer.INSTANCE, objDecodeNullableSerializableElement24);
                        i3 |= 64;
                        objDecodeNullableSerializableElement14 = obj5;
                        objDecodeNullableSerializableElement17 = obj4;
                    case 7:
                        obj4 = objDecodeNullableSerializableElement17;
                        obj5 = objDecodeNullableSerializableElement14;
                        objDecodeNullableSerializableElement23 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, IntSerializer.INSTANCE, objDecodeNullableSerializableElement23);
                        i3 |= 128;
                        objDecodeNullableSerializableElement14 = obj5;
                        objDecodeNullableSerializableElement17 = obj4;
                    case 8:
                        obj4 = objDecodeNullableSerializableElement17;
                        obj5 = objDecodeNullableSerializableElement14;
                        objDecodeNullableSerializableElement22 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, IntSerializer.INSTANCE, objDecodeNullableSerializableElement22);
                        i3 |= 256;
                        objDecodeNullableSerializableElement14 = obj5;
                        objDecodeNullableSerializableElement17 = obj4;
                    case 9:
                        obj4 = objDecodeNullableSerializableElement17;
                        obj5 = objDecodeNullableSerializableElement14;
                        objDecodeNullableSerializableElement26 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, IntSerializer.INSTANCE, objDecodeNullableSerializableElement26);
                        i3 |= 512;
                        objDecodeNullableSerializableElement14 = obj5;
                        objDecodeNullableSerializableElement17 = obj4;
                    case 10:
                        obj4 = objDecodeNullableSerializableElement17;
                        obj5 = objDecodeNullableSerializableElement14;
                        objDecodeNullableSerializableElement21 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 10, StringSerializer.INSTANCE, objDecodeNullableSerializableElement21);
                        i3 |= 1024;
                        objDecodeNullableSerializableElement14 = obj5;
                        objDecodeNullableSerializableElement17 = obj4;
                    case 11:
                        obj4 = objDecodeNullableSerializableElement17;
                        obj5 = objDecodeNullableSerializableElement14;
                        objDecodeNullableSerializableElement20 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 11, StringSerializer.INSTANCE, objDecodeNullableSerializableElement20);
                        i3 |= 2048;
                        objDecodeNullableSerializableElement14 = obj5;
                        objDecodeNullableSerializableElement17 = obj4;
                    case 12:
                        obj4 = objDecodeNullableSerializableElement17;
                        objDecodeNullableSerializableElement29 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 12, StringSerializer.INSTANCE, objDecodeNullableSerializableElement29);
                        i3 |= 4096;
                        objDecodeNullableSerializableElement14 = objDecodeNullableSerializableElement14;
                        objDecodeNullableSerializableElement30 = objDecodeNullableSerializableElement30;
                        objDecodeNullableSerializableElement17 = obj4;
                    case 13:
                        obj4 = objDecodeNullableSerializableElement17;
                        obj5 = objDecodeNullableSerializableElement14;
                        objDecodeNullableSerializableElement30 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 13, StringSerializer.INSTANCE, objDecodeNullableSerializableElement30);
                        i3 |= 8192;
                        objDecodeNullableSerializableElement14 = obj5;
                        objDecodeNullableSerializableElement17 = obj4;
                    case 14:
                        obj3 = objDecodeNullableSerializableElement17;
                        c3 = 15;
                        iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 14);
                        i3 |= 16384;
                        objDecodeNullableSerializableElement17 = obj3;
                    case 15:
                        obj3 = objDecodeNullableSerializableElement17;
                        c3 = 15;
                        objDecodeNullableSerializableElement14 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 15, StringSerializer.INSTANCE, objDecodeNullableSerializableElement14);
                        i3 |= 32768;
                        objDecodeNullableSerializableElement17 = obj3;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            Object obj7 = objDecodeNullableSerializableElement18;
            Object obj8 = objDecodeNullableSerializableElement28;
            Object obj9 = objDecodeNullableSerializableElement19;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement17;
            obj = objDecodeNullableSerializableElement26;
            iDecodeIntElement = iDecodeIntElement2;
            obj2 = obj8;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement22;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement23;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement24;
            objDecodeNullableSerializableElement5 = objDecodeNullableSerializableElement27;
            objDecodeNullableSerializableElement6 = obj9;
            i2 = i3;
            objDecodeNullableSerializableElement7 = objDecodeNullableSerializableElement25;
            objDecodeNullableSerializableElement8 = obj7;
            objDecodeNullableSerializableElement9 = objDecodeNullableSerializableElement29;
            objDecodeNullableSerializableElement10 = objDecodeNullableSerializableElement14;
            objDecodeNullableSerializableElement11 = objDecodeNullableSerializableElement30;
            Object obj10 = objDecodeNullableSerializableElement21;
            objDecodeNullableSerializableElement12 = objDecodeNullableSerializableElement20;
            objDecodeNullableSerializableElement13 = obj10;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new ExamRecordVo(i2, (Integer) obj2, (String) objDecodeNullableSerializableElement, (String) objDecodeNullableSerializableElement6, (String) objDecodeNullableSerializableElement8, (String) objDecodeNullableSerializableElement7, (Integer) objDecodeNullableSerializableElement5, (String) objDecodeNullableSerializableElement4, (Integer) objDecodeNullableSerializableElement3, (Integer) objDecodeNullableSerializableElement2, (Integer) obj, (String) objDecodeNullableSerializableElement13, (String) objDecodeNullableSerializableElement12, (String) objDecodeNullableSerializableElement9, (String) objDecodeNullableSerializableElement11, iDecodeIntElement, (String) objDecodeNullableSerializableElement10, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull ExamRecordVo value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        ExamRecordVo.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

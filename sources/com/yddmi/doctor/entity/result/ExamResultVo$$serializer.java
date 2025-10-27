package com.yddmi.doctor.entity.result;

import java.util.List;
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
import kotlinx.serialization.internal.ArrayListSerializer;
import kotlinx.serialization.internal.GeneratedSerializer;
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginGeneratedSerialDescriptor;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/ExamResultVo.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/ExamResultVo;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class ExamResultVo$$serializer implements GeneratedSerializer<ExamResultVo> {

    @NotNull
    public static final ExamResultVo$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        ExamResultVo$$serializer examResultVo$$serializer = new ExamResultVo$$serializer();
        INSTANCE = examResultVo$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.ExamResultVo", examResultVo$$serializer, 15);
        pluginGeneratedSerialDescriptor.addElement("candidateId", true);
        pluginGeneratedSerialDescriptor.addElement("candidateSize", true);
        pluginGeneratedSerialDescriptor.addElement("content", true);
        pluginGeneratedSerialDescriptor.addElement("contentType", true);
        pluginGeneratedSerialDescriptor.addElement("endTime", true);
        pluginGeneratedSerialDescriptor.addElement("examId", true);
        pluginGeneratedSerialDescriptor.addElement("examRecordVoList", true);
        pluginGeneratedSerialDescriptor.addElement("examScore", true);
        pluginGeneratedSerialDescriptor.addElement("examTrainRecordVoList", true);
        pluginGeneratedSerialDescriptor.addElement("id", true);
        pluginGeneratedSerialDescriptor.addElement("paperFinishCount", true);
        pluginGeneratedSerialDescriptor.addElement("patientFinishCount", true);
        pluginGeneratedSerialDescriptor.addElement("startTime", true);
        pluginGeneratedSerialDescriptor.addElement("status", true);
        pluginGeneratedSerialDescriptor.addElement("timeCost", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private ExamResultVo$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        ExamRecordVo$$serializer examRecordVo$$serializer = ExamRecordVo$$serializer.INSTANCE;
        return new KSerializer[]{BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(new ArrayListSerializer(BuiltinSerializersKt.getNullable(examRecordVo$$serializer))), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(new ArrayListSerializer(BuiltinSerializersKt.getNullable(examRecordVo$$serializer))), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer)};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public ExamResultVo deserialize(@NotNull Decoder decoder) {
        Object objDecodeNullableSerializableElement;
        Object objDecodeNullableSerializableElement2;
        Object objDecodeNullableSerializableElement3;
        Object obj;
        Object objDecodeNullableSerializableElement4;
        Object obj2;
        Object objDecodeNullableSerializableElement5;
        Object objDecodeNullableSerializableElement6;
        Object objDecodeNullableSerializableElement7;
        Object objDecodeNullableSerializableElement8;
        Object objDecodeNullableSerializableElement9;
        Object objDecodeNullableSerializableElement10;
        Object objDecodeNullableSerializableElement11;
        Object objDecodeNullableSerializableElement12;
        Object objDecodeNullableSerializableElement13;
        int i2;
        Object obj3;
        Object obj4;
        Object obj5;
        Object obj6;
        Object obj7;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            Object objDecodeNullableSerializableElement14 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, stringSerializer, null);
            IntSerializer intSerializer = IntSerializer.INSTANCE;
            Object objDecodeNullableSerializableElement15 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, intSerializer, null);
            objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, stringSerializer, null);
            objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, stringSerializer, null);
            objDecodeNullableSerializableElement13 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, stringSerializer, null);
            objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, stringSerializer, null);
            ExamRecordVo$$serializer examRecordVo$$serializer = ExamRecordVo$$serializer.INSTANCE;
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, new ArrayListSerializer(BuiltinSerializersKt.getNullable(examRecordVo$$serializer)), null);
            objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, stringSerializer, null);
            objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, new ArrayListSerializer(BuiltinSerializersKt.getNullable(examRecordVo$$serializer)), null);
            objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, stringSerializer, null);
            objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 10, intSerializer, null);
            objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 11, intSerializer, null);
            objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 12, stringSerializer, null);
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 13, intSerializer, null);
            i2 = 32767;
            obj2 = objDecodeNullableSerializableElement15;
            objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 14, intSerializer, null);
            obj = objDecodeNullableSerializableElement14;
        } else {
            boolean z2 = true;
            Object objDecodeNullableSerializableElement16 = null;
            Object objDecodeNullableSerializableElement17 = null;
            Object objDecodeNullableSerializableElement18 = null;
            Object objDecodeNullableSerializableElement19 = null;
            Object objDecodeNullableSerializableElement20 = null;
            objDecodeNullableSerializableElement = null;
            Object objDecodeNullableSerializableElement21 = null;
            Object objDecodeNullableSerializableElement22 = null;
            Object objDecodeNullableSerializableElement23 = null;
            Object objDecodeNullableSerializableElement24 = null;
            Object objDecodeNullableSerializableElement25 = null;
            Object objDecodeNullableSerializableElement26 = null;
            Object objDecodeNullableSerializableElement27 = null;
            Object objDecodeNullableSerializableElement28 = null;
            int i3 = 0;
            Object objDecodeNullableSerializableElement29 = null;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        obj3 = objDecodeNullableSerializableElement29;
                        obj4 = objDecodeNullableSerializableElement16;
                        obj5 = objDecodeNullableSerializableElement17;
                        z2 = false;
                        objDecodeNullableSerializableElement17 = obj5;
                        objDecodeNullableSerializableElement16 = obj4;
                        objDecodeNullableSerializableElement29 = obj3;
                    case 0:
                        obj3 = objDecodeNullableSerializableElement29;
                        obj5 = objDecodeNullableSerializableElement17;
                        obj4 = objDecodeNullableSerializableElement16;
                        objDecodeNullableSerializableElement28 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, StringSerializer.INSTANCE, objDecodeNullableSerializableElement28);
                        i3 |= 1;
                        objDecodeNullableSerializableElement17 = obj5;
                        objDecodeNullableSerializableElement16 = obj4;
                        objDecodeNullableSerializableElement29 = obj3;
                    case 1:
                        obj3 = objDecodeNullableSerializableElement29;
                        objDecodeNullableSerializableElement18 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, IntSerializer.INSTANCE, objDecodeNullableSerializableElement18);
                        i3 |= 2;
                        objDecodeNullableSerializableElement17 = objDecodeNullableSerializableElement17;
                        objDecodeNullableSerializableElement29 = obj3;
                    case 2:
                        obj6 = objDecodeNullableSerializableElement17;
                        obj7 = objDecodeNullableSerializableElement18;
                        objDecodeNullableSerializableElement19 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, StringSerializer.INSTANCE, objDecodeNullableSerializableElement19);
                        i3 |= 4;
                        objDecodeNullableSerializableElement17 = obj6;
                        objDecodeNullableSerializableElement18 = obj7;
                    case 3:
                        obj6 = objDecodeNullableSerializableElement17;
                        obj7 = objDecodeNullableSerializableElement18;
                        objDecodeNullableSerializableElement29 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, StringSerializer.INSTANCE, objDecodeNullableSerializableElement29);
                        i3 |= 8;
                        objDecodeNullableSerializableElement17 = obj6;
                        objDecodeNullableSerializableElement18 = obj7;
                    case 4:
                        obj6 = objDecodeNullableSerializableElement17;
                        obj7 = objDecodeNullableSerializableElement18;
                        objDecodeNullableSerializableElement16 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, StringSerializer.INSTANCE, objDecodeNullableSerializableElement16);
                        i3 |= 16;
                        objDecodeNullableSerializableElement17 = obj6;
                        objDecodeNullableSerializableElement18 = obj7;
                    case 5:
                        obj6 = objDecodeNullableSerializableElement17;
                        obj7 = objDecodeNullableSerializableElement18;
                        objDecodeNullableSerializableElement25 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, StringSerializer.INSTANCE, objDecodeNullableSerializableElement25);
                        i3 |= 32;
                        objDecodeNullableSerializableElement17 = obj6;
                        objDecodeNullableSerializableElement18 = obj7;
                    case 6:
                        obj6 = objDecodeNullableSerializableElement17;
                        obj7 = objDecodeNullableSerializableElement18;
                        objDecodeNullableSerializableElement22 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, new ArrayListSerializer(BuiltinSerializersKt.getNullable(ExamRecordVo$$serializer.INSTANCE)), objDecodeNullableSerializableElement22);
                        i3 |= 64;
                        objDecodeNullableSerializableElement17 = obj6;
                        objDecodeNullableSerializableElement18 = obj7;
                    case 7:
                        obj6 = objDecodeNullableSerializableElement17;
                        obj7 = objDecodeNullableSerializableElement18;
                        objDecodeNullableSerializableElement24 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, StringSerializer.INSTANCE, objDecodeNullableSerializableElement24);
                        i3 |= 128;
                        objDecodeNullableSerializableElement17 = obj6;
                        objDecodeNullableSerializableElement18 = obj7;
                    case 8:
                        obj6 = objDecodeNullableSerializableElement17;
                        obj7 = objDecodeNullableSerializableElement18;
                        objDecodeNullableSerializableElement21 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, new ArrayListSerializer(BuiltinSerializersKt.getNullable(ExamRecordVo$$serializer.INSTANCE)), objDecodeNullableSerializableElement21);
                        i3 |= 256;
                        objDecodeNullableSerializableElement17 = obj6;
                        objDecodeNullableSerializableElement18 = obj7;
                    case 9:
                        obj6 = objDecodeNullableSerializableElement17;
                        obj7 = objDecodeNullableSerializableElement18;
                        objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, StringSerializer.INSTANCE, objDecodeNullableSerializableElement);
                        i3 |= 512;
                        objDecodeNullableSerializableElement17 = obj6;
                        objDecodeNullableSerializableElement18 = obj7;
                    case 10:
                        obj6 = objDecodeNullableSerializableElement17;
                        obj7 = objDecodeNullableSerializableElement18;
                        objDecodeNullableSerializableElement20 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 10, IntSerializer.INSTANCE, objDecodeNullableSerializableElement20);
                        i3 |= 1024;
                        objDecodeNullableSerializableElement17 = obj6;
                        objDecodeNullableSerializableElement18 = obj7;
                    case 11:
                        obj6 = objDecodeNullableSerializableElement17;
                        obj7 = objDecodeNullableSerializableElement18;
                        objDecodeNullableSerializableElement23 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 11, IntSerializer.INSTANCE, objDecodeNullableSerializableElement23);
                        i3 |= 2048;
                        objDecodeNullableSerializableElement17 = obj6;
                        objDecodeNullableSerializableElement18 = obj7;
                    case 12:
                        obj7 = objDecodeNullableSerializableElement18;
                        objDecodeNullableSerializableElement26 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 12, StringSerializer.INSTANCE, objDecodeNullableSerializableElement26);
                        i3 |= 4096;
                        objDecodeNullableSerializableElement17 = objDecodeNullableSerializableElement17;
                        objDecodeNullableSerializableElement27 = objDecodeNullableSerializableElement27;
                        objDecodeNullableSerializableElement18 = obj7;
                    case 13:
                        obj7 = objDecodeNullableSerializableElement18;
                        obj6 = objDecodeNullableSerializableElement17;
                        objDecodeNullableSerializableElement27 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 13, IntSerializer.INSTANCE, objDecodeNullableSerializableElement27);
                        i3 |= 8192;
                        objDecodeNullableSerializableElement17 = obj6;
                        objDecodeNullableSerializableElement18 = obj7;
                    case 14:
                        objDecodeNullableSerializableElement17 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 14, IntSerializer.INSTANCE, objDecodeNullableSerializableElement17);
                        i3 |= 16384;
                        objDecodeNullableSerializableElement18 = objDecodeNullableSerializableElement18;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            Object obj8 = objDecodeNullableSerializableElement29;
            Object obj9 = objDecodeNullableSerializableElement16;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement17;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement27;
            obj = objDecodeNullableSerializableElement28;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement26;
            obj2 = objDecodeNullableSerializableElement18;
            objDecodeNullableSerializableElement5 = objDecodeNullableSerializableElement22;
            objDecodeNullableSerializableElement6 = objDecodeNullableSerializableElement23;
            objDecodeNullableSerializableElement7 = obj8;
            objDecodeNullableSerializableElement8 = objDecodeNullableSerializableElement20;
            objDecodeNullableSerializableElement9 = objDecodeNullableSerializableElement21;
            objDecodeNullableSerializableElement10 = objDecodeNullableSerializableElement25;
            objDecodeNullableSerializableElement11 = objDecodeNullableSerializableElement19;
            objDecodeNullableSerializableElement12 = objDecodeNullableSerializableElement24;
            objDecodeNullableSerializableElement13 = obj9;
            i2 = i3;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new ExamResultVo(i2, (String) obj, (Integer) obj2, (String) objDecodeNullableSerializableElement11, (String) objDecodeNullableSerializableElement7, (String) objDecodeNullableSerializableElement13, (String) objDecodeNullableSerializableElement10, (List) objDecodeNullableSerializableElement5, (String) objDecodeNullableSerializableElement12, (List) objDecodeNullableSerializableElement9, (String) objDecodeNullableSerializableElement, (Integer) objDecodeNullableSerializableElement8, (Integer) objDecodeNullableSerializableElement6, (String) objDecodeNullableSerializableElement4, (Integer) objDecodeNullableSerializableElement3, (Integer) objDecodeNullableSerializableElement2, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull ExamResultVo value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        ExamResultVo.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

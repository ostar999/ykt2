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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/ExamRow.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/ExamRow;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class ExamRow$$serializer implements GeneratedSerializer<ExamRow> {

    @NotNull
    public static final ExamRow$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        ExamRow$$serializer examRow$$serializer = new ExamRow$$serializer();
        INSTANCE = examRow$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.ExamRow", examRow$$serializer, 12);
        pluginGeneratedSerialDescriptor.addElement("candidateId", true);
        pluginGeneratedSerialDescriptor.addElement("examId", true);
        pluginGeneratedSerialDescriptor.addElement("fullMarks", true);
        pluginGeneratedSerialDescriptor.addElement("id", true);
        pluginGeneratedSerialDescriptor.addElement("name", true);
        pluginGeneratedSerialDescriptor.addElement("paperId", true);
        pluginGeneratedSerialDescriptor.addElement("paperName", true);
        pluginGeneratedSerialDescriptor.addElement("patientId", true);
        pluginGeneratedSerialDescriptor.addElement("patientName", true);
        pluginGeneratedSerialDescriptor.addElement("questionCount", true);
        pluginGeneratedSerialDescriptor.addElement("trainId", true);
        pluginGeneratedSerialDescriptor.addElement("status", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private ExamRow$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        return new KSerializer[]{BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer)};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public ExamRow deserialize(@NotNull Decoder decoder) {
        Object objDecodeNullableSerializableElement;
        Object objDecodeNullableSerializableElement2;
        Object objDecodeNullableSerializableElement3;
        Object objDecodeNullableSerializableElement4;
        Object obj;
        Object objDecodeNullableSerializableElement5;
        Object objDecodeNullableSerializableElement6;
        Object objDecodeNullableSerializableElement7;
        Object objDecodeNullableSerializableElement8;
        Object objDecodeNullableSerializableElement9;
        int i2;
        Object objDecodeNullableSerializableElement10;
        Object objDecodeNullableSerializableElement11;
        Object obj2;
        Object obj3;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        Object objDecodeNullableSerializableElement12 = null;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            IntSerializer intSerializer = IntSerializer.INSTANCE;
            objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, intSerializer, null);
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, stringSerializer, null);
            objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, stringSerializer, null);
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, stringSerializer, null);
            Object objDecodeNullableSerializableElement13 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, stringSerializer, null);
            Object objDecodeNullableSerializableElement14 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, stringSerializer, null);
            Object objDecodeNullableSerializableElement15 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, stringSerializer, null);
            Object objDecodeNullableSerializableElement16 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, intSerializer, null);
            objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, stringSerializer, null);
            objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, stringSerializer, null);
            objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 10, intSerializer, null);
            objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 11, intSerializer, null);
            i2 = 4095;
            obj = objDecodeNullableSerializableElement13;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement14;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement15;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement16;
        } else {
            boolean z2 = true;
            int i3 = 0;
            Object objDecodeNullableSerializableElement17 = null;
            Object objDecodeNullableSerializableElement18 = null;
            Object objDecodeNullableSerializableElement19 = null;
            objDecodeNullableSerializableElement = null;
            objDecodeNullableSerializableElement2 = null;
            Object objDecodeNullableSerializableElement20 = null;
            Object objDecodeNullableSerializableElement21 = null;
            objDecodeNullableSerializableElement3 = null;
            Object objDecodeNullableSerializableElement22 = null;
            Object objDecodeNullableSerializableElement23 = null;
            Object objDecodeNullableSerializableElement24 = null;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        obj2 = objDecodeNullableSerializableElement17;
                        z2 = false;
                        objDecodeNullableSerializableElement17 = obj2;
                    case 0:
                        obj2 = objDecodeNullableSerializableElement17;
                        objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, IntSerializer.INSTANCE, objDecodeNullableSerializableElement12);
                        i3 |= 1;
                        objDecodeNullableSerializableElement17 = obj2;
                    case 1:
                        obj3 = objDecodeNullableSerializableElement12;
                        objDecodeNullableSerializableElement24 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, StringSerializer.INSTANCE, objDecodeNullableSerializableElement24);
                        i3 |= 2;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 2:
                        obj3 = objDecodeNullableSerializableElement12;
                        objDecodeNullableSerializableElement23 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, StringSerializer.INSTANCE, objDecodeNullableSerializableElement23);
                        i3 |= 4;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 3:
                        obj3 = objDecodeNullableSerializableElement12;
                        objDecodeNullableSerializableElement22 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, StringSerializer.INSTANCE, objDecodeNullableSerializableElement22);
                        i3 |= 8;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 4:
                        obj3 = objDecodeNullableSerializableElement12;
                        objDecodeNullableSerializableElement20 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, StringSerializer.INSTANCE, objDecodeNullableSerializableElement20);
                        i3 |= 16;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 5:
                        obj3 = objDecodeNullableSerializableElement12;
                        objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, StringSerializer.INSTANCE, objDecodeNullableSerializableElement3);
                        i3 |= 32;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 6:
                        obj3 = objDecodeNullableSerializableElement12;
                        objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, StringSerializer.INSTANCE, objDecodeNullableSerializableElement2);
                        i3 |= 64;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 7:
                        obj3 = objDecodeNullableSerializableElement12;
                        objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, IntSerializer.INSTANCE, objDecodeNullableSerializableElement);
                        i3 |= 128;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 8:
                        obj3 = objDecodeNullableSerializableElement12;
                        objDecodeNullableSerializableElement19 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, StringSerializer.INSTANCE, objDecodeNullableSerializableElement19);
                        i3 |= 256;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 9:
                        obj3 = objDecodeNullableSerializableElement12;
                        objDecodeNullableSerializableElement21 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, StringSerializer.INSTANCE, objDecodeNullableSerializableElement21);
                        i3 |= 512;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 10:
                        obj3 = objDecodeNullableSerializableElement12;
                        objDecodeNullableSerializableElement18 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 10, IntSerializer.INSTANCE, objDecodeNullableSerializableElement18);
                        i3 |= 1024;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 11:
                        objDecodeNullableSerializableElement17 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 11, IntSerializer.INSTANCE, objDecodeNullableSerializableElement17);
                        i3 |= 2048;
                        objDecodeNullableSerializableElement12 = objDecodeNullableSerializableElement12;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            Object obj4 = objDecodeNullableSerializableElement17;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement18;
            obj = objDecodeNullableSerializableElement20;
            objDecodeNullableSerializableElement5 = objDecodeNullableSerializableElement22;
            objDecodeNullableSerializableElement6 = objDecodeNullableSerializableElement23;
            objDecodeNullableSerializableElement7 = objDecodeNullableSerializableElement24;
            objDecodeNullableSerializableElement8 = objDecodeNullableSerializableElement12;
            objDecodeNullableSerializableElement9 = obj4;
            Object obj5 = objDecodeNullableSerializableElement19;
            i2 = i3;
            objDecodeNullableSerializableElement10 = objDecodeNullableSerializableElement21;
            objDecodeNullableSerializableElement11 = obj5;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new ExamRow(i2, (Integer) objDecodeNullableSerializableElement8, (String) objDecodeNullableSerializableElement7, (String) objDecodeNullableSerializableElement6, (String) objDecodeNullableSerializableElement5, (String) obj, (String) objDecodeNullableSerializableElement3, (String) objDecodeNullableSerializableElement2, (Integer) objDecodeNullableSerializableElement, (String) objDecodeNullableSerializableElement11, (String) objDecodeNullableSerializableElement10, (Integer) objDecodeNullableSerializableElement4, (Integer) objDecodeNullableSerializableElement9, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull ExamRow value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        ExamRow.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/ExamQuestionVo.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/ExamQuestionVo;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class ExamQuestionVo$$serializer implements GeneratedSerializer<ExamQuestionVo> {

    @NotNull
    public static final ExamQuestionVo$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        ExamQuestionVo$$serializer examQuestionVo$$serializer = new ExamQuestionVo$$serializer();
        INSTANCE = examQuestionVo$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.ExamQuestionVo", examQuestionVo$$serializer, 13);
        pluginGeneratedSerialDescriptor.addElement("analysis", true);
        pluginGeneratedSerialDescriptor.addElement("answer", true);
        pluginGeneratedSerialDescriptor.addElement("content", true);
        pluginGeneratedSerialDescriptor.addElement("correct", true);
        pluginGeneratedSerialDescriptor.addElement("selectList", true);
        pluginGeneratedSerialDescriptor.addElement("id", true);
        pluginGeneratedSerialDescriptor.addElement("index", true);
        pluginGeneratedSerialDescriptor.addElement("isRight", true);
        pluginGeneratedSerialDescriptor.addElement("questionType", true);
        pluginGeneratedSerialDescriptor.addElement("type", true);
        pluginGeneratedSerialDescriptor.addElement("stem", true);
        pluginGeneratedSerialDescriptor.addElement("itemVpIndex", true);
        pluginGeneratedSerialDescriptor.addElement("itemVpItemIndex", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private ExamQuestionVo$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        return new KSerializer[]{BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(new ArrayListSerializer(BuiltinSerializersKt.getNullable(ExamQuestionSelectVo$$serializer.INSTANCE))), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, intSerializer};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public ExamQuestionVo deserialize(@NotNull Decoder decoder) {
        Object objDecodeNullableSerializableElement;
        Object objDecodeNullableSerializableElement2;
        Object objDecodeNullableSerializableElement3;
        Object objDecodeNullableSerializableElement4;
        Object objDecodeNullableSerializableElement5;
        Object objDecodeNullableSerializableElement6;
        Object objDecodeNullableSerializableElement7;
        int iDecodeIntElement;
        int iDecodeIntElement2;
        Object objDecodeNullableSerializableElement8;
        Object obj;
        int i2;
        Object objDecodeNullableSerializableElement9;
        Object objDecodeNullableSerializableElement10;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        Object objDecodeNullableSerializableElement11 = null;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, stringSerializer, null);
            Object objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, stringSerializer, null);
            objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, stringSerializer, null);
            objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, stringSerializer, null);
            Object objDecodeNullableSerializableElement13 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, new ArrayListSerializer(BuiltinSerializersKt.getNullable(ExamQuestionSelectVo$$serializer.INSTANCE)), null);
            objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, stringSerializer, null);
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, IntSerializer.INSTANCE, null);
            objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, stringSerializer, null);
            objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, stringSerializer, null);
            objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, stringSerializer, null);
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 10, stringSerializer, null);
            iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 11);
            iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 12);
            obj = objDecodeNullableSerializableElement12;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement13;
            i2 = 8191;
        } else {
            int i3 = 12;
            Object objDecodeNullableSerializableElement14 = null;
            Object objDecodeNullableSerializableElement15 = null;
            Object objDecodeNullableSerializableElement16 = null;
            Object objDecodeNullableSerializableElement17 = null;
            objDecodeNullableSerializableElement = null;
            Object objDecodeNullableSerializableElement18 = null;
            Object objDecodeNullableSerializableElement19 = null;
            Object objDecodeNullableSerializableElement20 = null;
            boolean z2 = true;
            int i4 = 0;
            int iDecodeIntElement3 = 0;
            int iDecodeIntElement4 = 0;
            objDecodeNullableSerializableElement2 = null;
            Object objDecodeNullableSerializableElement21 = null;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                    case 0:
                        objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, StringSerializer.INSTANCE, objDecodeNullableSerializableElement11);
                        i4 |= 1;
                        i3 = 12;
                    case 1:
                        objDecodeNullableSerializableElement14 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, StringSerializer.INSTANCE, objDecodeNullableSerializableElement14);
                        i4 |= 2;
                        i3 = 12;
                    case 2:
                        objDecodeNullableSerializableElement15 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, StringSerializer.INSTANCE, objDecodeNullableSerializableElement15);
                        i4 |= 4;
                        i3 = 12;
                    case 3:
                        objDecodeNullableSerializableElement21 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, StringSerializer.INSTANCE, objDecodeNullableSerializableElement21);
                        i4 |= 8;
                        i3 = 12;
                    case 4:
                        objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, new ArrayListSerializer(BuiltinSerializersKt.getNullable(ExamQuestionSelectVo$$serializer.INSTANCE)), objDecodeNullableSerializableElement2);
                        i4 |= 16;
                        i3 = 12;
                    case 5:
                        objDecodeNullableSerializableElement20 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, StringSerializer.INSTANCE, objDecodeNullableSerializableElement20);
                        i4 |= 32;
                        i3 = 12;
                    case 6:
                        objDecodeNullableSerializableElement18 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, IntSerializer.INSTANCE, objDecodeNullableSerializableElement18);
                        i4 |= 64;
                        i3 = 12;
                    case 7:
                        objDecodeNullableSerializableElement19 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, StringSerializer.INSTANCE, objDecodeNullableSerializableElement19);
                        i4 |= 128;
                        i3 = 12;
                    case 8:
                        objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, StringSerializer.INSTANCE, objDecodeNullableSerializableElement);
                        i4 |= 256;
                        i3 = 12;
                    case 9:
                        objDecodeNullableSerializableElement17 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, StringSerializer.INSTANCE, objDecodeNullableSerializableElement17);
                        i4 |= 512;
                        i3 = 12;
                    case 10:
                        objDecodeNullableSerializableElement16 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 10, StringSerializer.INSTANCE, objDecodeNullableSerializableElement16);
                        i4 |= 1024;
                        i3 = 12;
                    case 11:
                        iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 11);
                        i4 |= 2048;
                    case 12:
                        iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, i3);
                        i4 |= 4096;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement16;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement17;
            objDecodeNullableSerializableElement5 = objDecodeNullableSerializableElement18;
            objDecodeNullableSerializableElement6 = objDecodeNullableSerializableElement19;
            objDecodeNullableSerializableElement7 = objDecodeNullableSerializableElement21;
            iDecodeIntElement = iDecodeIntElement3;
            iDecodeIntElement2 = iDecodeIntElement4;
            objDecodeNullableSerializableElement8 = objDecodeNullableSerializableElement11;
            obj = objDecodeNullableSerializableElement14;
            i2 = i4;
            Object obj2 = objDecodeNullableSerializableElement20;
            objDecodeNullableSerializableElement9 = objDecodeNullableSerializableElement15;
            objDecodeNullableSerializableElement10 = obj2;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new ExamQuestionVo(i2, (String) objDecodeNullableSerializableElement8, (String) obj, (String) objDecodeNullableSerializableElement9, (String) objDecodeNullableSerializableElement7, (List) objDecodeNullableSerializableElement2, (String) objDecodeNullableSerializableElement10, (Integer) objDecodeNullableSerializableElement5, (String) objDecodeNullableSerializableElement6, (String) objDecodeNullableSerializableElement, (String) objDecodeNullableSerializableElement4, (String) objDecodeNullableSerializableElement3, iDecodeIntElement, iDecodeIntElement2, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull ExamQuestionVo value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        ExamQuestionVo.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

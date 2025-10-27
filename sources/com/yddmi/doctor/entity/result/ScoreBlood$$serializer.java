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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/ScoreBlood.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/ScoreBlood;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class ScoreBlood$$serializer implements GeneratedSerializer<ScoreBlood> {

    @NotNull
    public static final ScoreBlood$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        ScoreBlood$$serializer scoreBlood$$serializer = new ScoreBlood$$serializer();
        INSTANCE = scoreBlood$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.ScoreBlood", scoreBlood$$serializer, 13);
        pluginGeneratedSerialDescriptor.addElement("assistTypeId", true);
        pluginGeneratedSerialDescriptor.addElement("auxiliaryVos", true);
        pluginGeneratedSerialDescriptor.addElement("bodyName", true);
        pluginGeneratedSerialDescriptor.addElement("categoryType", true);
        pluginGeneratedSerialDescriptor.addElement("checkType", true);
        pluginGeneratedSerialDescriptor.addElement("fileId", true);
        pluginGeneratedSerialDescriptor.addElement("id", true);
        pluginGeneratedSerialDescriptor.addElement("name", true);
        pluginGeneratedSerialDescriptor.addElement("patientId", true);
        pluginGeneratedSerialDescriptor.addElement("result", true);
        pluginGeneratedSerialDescriptor.addElement("scoreType", true);
        pluginGeneratedSerialDescriptor.addElement("tbOpticalResultVo", true);
        pluginGeneratedSerialDescriptor.addElement("clinicalSignificance", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private ScoreBlood$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        AuxiliaryVo$$serializer auxiliaryVo$$serializer = AuxiliaryVo$$serializer.INSTANCE;
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        return new KSerializer[]{BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(new ArrayListSerializer(BuiltinSerializersKt.getNullable(auxiliaryVo$$serializer))), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(auxiliaryVo$$serializer), BuiltinSerializersKt.getNullable(stringSerializer)};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public ScoreBlood deserialize(@NotNull Decoder decoder) {
        Object objDecodeNullableSerializableElement;
        Object objDecodeNullableSerializableElement2;
        Object objDecodeNullableSerializableElement3;
        int iDecodeIntElement;
        Object objDecodeNullableSerializableElement4;
        Object objDecodeNullableSerializableElement5;
        Object objDecodeNullableSerializableElement6;
        Object objDecodeNullableSerializableElement7;
        Object obj;
        Object objDecodeNullableSerializableElement8;
        Object objDecodeNullableSerializableElement9;
        Object objDecodeNullableSerializableElement10;
        Object objDecodeNullableSerializableElement11;
        int i2;
        Object obj2;
        Object obj3;
        Object obj4;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        Object objDecodeNullableSerializableElement12 = null;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            IntSerializer intSerializer = IntSerializer.INSTANCE;
            objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, intSerializer, null);
            AuxiliaryVo$$serializer auxiliaryVo$$serializer = AuxiliaryVo$$serializer.INSTANCE;
            Object objDecodeNullableSerializableElement13 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, new ArrayListSerializer(BuiltinSerializersKt.getNullable(auxiliaryVo$$serializer)), null);
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, stringSerializer, null);
            Object objDecodeNullableSerializableElement14 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, intSerializer, null);
            int iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 4);
            objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, stringSerializer, null);
            Object objDecodeNullableSerializableElement15 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, intSerializer, null);
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, stringSerializer, null);
            objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, intSerializer, null);
            Object objDecodeNullableSerializableElement16 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, stringSerializer, null);
            objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 10, intSerializer, null);
            Object objDecodeNullableSerializableElement17 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 11, auxiliaryVo$$serializer, null);
            objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 12, stringSerializer, null);
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement14;
            iDecodeIntElement = iDecodeIntElement2;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement16;
            i2 = 8191;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement15;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement17;
            obj = objDecodeNullableSerializableElement13;
        } else {
            Object objDecodeNullableSerializableElement18 = null;
            Object objDecodeNullableSerializableElement19 = null;
            objDecodeNullableSerializableElement = null;
            Object objDecodeNullableSerializableElement20 = null;
            objDecodeNullableSerializableElement2 = null;
            Object objDecodeNullableSerializableElement21 = null;
            Object objDecodeNullableSerializableElement22 = null;
            objDecodeNullableSerializableElement3 = null;
            Object objDecodeNullableSerializableElement23 = null;
            boolean z2 = true;
            int i3 = 0;
            iDecodeIntElement = 0;
            objDecodeNullableSerializableElement4 = null;
            Object objDecodeNullableSerializableElement24 = null;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        obj2 = objDecodeNullableSerializableElement20;
                        z2 = false;
                        objDecodeNullableSerializableElement20 = obj2;
                    case 0:
                        obj2 = objDecodeNullableSerializableElement20;
                        objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, IntSerializer.INSTANCE, objDecodeNullableSerializableElement12);
                        i3 |= 1;
                        objDecodeNullableSerializableElement20 = obj2;
                    case 1:
                        obj3 = objDecodeNullableSerializableElement12;
                        obj4 = objDecodeNullableSerializableElement20;
                        objDecodeNullableSerializableElement18 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, new ArrayListSerializer(BuiltinSerializersKt.getNullable(AuxiliaryVo$$serializer.INSTANCE)), objDecodeNullableSerializableElement18);
                        i3 |= 2;
                        objDecodeNullableSerializableElement20 = obj4;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 2:
                        obj3 = objDecodeNullableSerializableElement12;
                        obj4 = objDecodeNullableSerializableElement20;
                        objDecodeNullableSerializableElement19 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, StringSerializer.INSTANCE, objDecodeNullableSerializableElement19);
                        i3 |= 4;
                        objDecodeNullableSerializableElement20 = obj4;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 3:
                        obj3 = objDecodeNullableSerializableElement12;
                        obj4 = objDecodeNullableSerializableElement20;
                        objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, IntSerializer.INSTANCE, objDecodeNullableSerializableElement);
                        i3 |= 8;
                        objDecodeNullableSerializableElement20 = obj4;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 4:
                        obj3 = objDecodeNullableSerializableElement12;
                        iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 4);
                        i3 |= 16;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 5:
                        obj3 = objDecodeNullableSerializableElement12;
                        objDecodeNullableSerializableElement24 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, StringSerializer.INSTANCE, objDecodeNullableSerializableElement24);
                        i3 |= 32;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 6:
                        obj3 = objDecodeNullableSerializableElement12;
                        objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, IntSerializer.INSTANCE, objDecodeNullableSerializableElement4);
                        i3 |= 64;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 7:
                        obj3 = objDecodeNullableSerializableElement12;
                        objDecodeNullableSerializableElement23 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, StringSerializer.INSTANCE, objDecodeNullableSerializableElement23);
                        i3 |= 128;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 8:
                        obj3 = objDecodeNullableSerializableElement12;
                        objDecodeNullableSerializableElement22 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, IntSerializer.INSTANCE, objDecodeNullableSerializableElement22);
                        i3 |= 256;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 9:
                        obj3 = objDecodeNullableSerializableElement12;
                        objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, StringSerializer.INSTANCE, objDecodeNullableSerializableElement3);
                        i3 |= 512;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 10:
                        obj3 = objDecodeNullableSerializableElement12;
                        objDecodeNullableSerializableElement21 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 10, IntSerializer.INSTANCE, objDecodeNullableSerializableElement21);
                        i3 |= 1024;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 11:
                        obj3 = objDecodeNullableSerializableElement12;
                        objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 11, AuxiliaryVo$$serializer.INSTANCE, objDecodeNullableSerializableElement2);
                        i3 |= 2048;
                        objDecodeNullableSerializableElement12 = obj3;
                    case 12:
                        objDecodeNullableSerializableElement20 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 12, StringSerializer.INSTANCE, objDecodeNullableSerializableElement20);
                        i3 |= 4096;
                        objDecodeNullableSerializableElement12 = objDecodeNullableSerializableElement12;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            Object obj5 = objDecodeNullableSerializableElement12;
            Object obj6 = objDecodeNullableSerializableElement20;
            objDecodeNullableSerializableElement5 = objDecodeNullableSerializableElement23;
            objDecodeNullableSerializableElement6 = obj6;
            objDecodeNullableSerializableElement7 = objDecodeNullableSerializableElement19;
            obj = objDecodeNullableSerializableElement18;
            objDecodeNullableSerializableElement8 = objDecodeNullableSerializableElement21;
            objDecodeNullableSerializableElement9 = objDecodeNullableSerializableElement24;
            objDecodeNullableSerializableElement10 = obj5;
            int i4 = i3;
            objDecodeNullableSerializableElement11 = objDecodeNullableSerializableElement22;
            i2 = i4;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new ScoreBlood(i2, (Integer) objDecodeNullableSerializableElement10, (List) obj, (String) objDecodeNullableSerializableElement7, (Integer) objDecodeNullableSerializableElement, iDecodeIntElement, (String) objDecodeNullableSerializableElement9, (Integer) objDecodeNullableSerializableElement4, (String) objDecodeNullableSerializableElement5, (Integer) objDecodeNullableSerializableElement11, (String) objDecodeNullableSerializableElement3, (Integer) objDecodeNullableSerializableElement8, (AuxiliaryVo) objDecodeNullableSerializableElement2, (String) objDecodeNullableSerializableElement6, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull ScoreBlood value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        ScoreBlood.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

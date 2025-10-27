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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/AuxiliaryVo.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/AuxiliaryVo;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class AuxiliaryVo$$serializer implements GeneratedSerializer<AuxiliaryVo> {

    @NotNull
    public static final AuxiliaryVo$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        AuxiliaryVo$$serializer auxiliaryVo$$serializer = new AuxiliaryVo$$serializer();
        INSTANCE = auxiliaryVo$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.AuxiliaryVo", auxiliaryVo$$serializer, 15);
        pluginGeneratedSerialDescriptor.addElement("auxiliaryId", true);
        pluginGeneratedSerialDescriptor.addElement("checkId", true);
        pluginGeneratedSerialDescriptor.addElement("code", true);
        pluginGeneratedSerialDescriptor.addElement("id", true);
        pluginGeneratedSerialDescriptor.addElement("name", true);
        pluginGeneratedSerialDescriptor.addElement("patientId", true);
        pluginGeneratedSerialDescriptor.addElement("reference", true);
        pluginGeneratedSerialDescriptor.addElement("result", true);
        pluginGeneratedSerialDescriptor.addElement("unit", true);
        pluginGeneratedSerialDescriptor.addElement("scanType", true);
        pluginGeneratedSerialDescriptor.addElement("bodyId", true);
        pluginGeneratedSerialDescriptor.addElement("fileId", true);
        pluginGeneratedSerialDescriptor.addElement("content", true);
        pluginGeneratedSerialDescriptor.addElement("prompt", true);
        pluginGeneratedSerialDescriptor.addElement("isAbnormal", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private AuxiliaryVo$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        return new KSerializer[]{BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), intSerializer};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public AuxiliaryVo deserialize(@NotNull Decoder decoder) {
        int iDecodeIntElement;
        Object objDecodeNullableSerializableElement;
        int i2;
        Object obj;
        Object objDecodeNullableSerializableElement2;
        int iDecodeIntElement2;
        int i3;
        Object objDecodeNullableSerializableElement3;
        Object objDecodeNullableSerializableElement4;
        Object objDecodeNullableSerializableElement5;
        Object objDecodeNullableSerializableElement6;
        Object objDecodeNullableSerializableElement7;
        Object objDecodeNullableSerializableElement8;
        Object objDecodeNullableSerializableElement9;
        Object objDecodeNullableSerializableElement10;
        Object objDecodeNullableSerializableElement11;
        Object obj2;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        Object objDecodeNullableSerializableElement12 = null;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            IntSerializer intSerializer = IntSerializer.INSTANCE;
            objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, intSerializer, null);
            objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, intSerializer, null);
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, stringSerializer, null);
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, intSerializer, null);
            objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, stringSerializer, null);
            Object objDecodeNullableSerializableElement13 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, intSerializer, null);
            objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, stringSerializer, null);
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, stringSerializer, null);
            objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, stringSerializer, null);
            int iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 9);
            int iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 10);
            objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 11, stringSerializer, null);
            objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 12, stringSerializer, null);
            Object objDecodeNullableSerializableElement14 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 13, stringSerializer, null);
            iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 14);
            i2 = 32767;
            iDecodeIntElement = iDecodeIntElement4;
            i3 = iDecodeIntElement3;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement14;
            obj = objDecodeNullableSerializableElement13;
        } else {
            int i4 = 14;
            boolean z2 = true;
            int i5 = 0;
            int iDecodeIntElement5 = 0;
            iDecodeIntElement = 0;
            int iDecodeIntElement6 = 0;
            Object objDecodeNullableSerializableElement15 = null;
            Object objDecodeNullableSerializableElement16 = null;
            Object objDecodeNullableSerializableElement17 = null;
            objDecodeNullableSerializableElement = null;
            Object objDecodeNullableSerializableElement18 = null;
            Object objDecodeNullableSerializableElement19 = null;
            Object objDecodeNullableSerializableElement20 = null;
            Object objDecodeNullableSerializableElement21 = null;
            Object objDecodeNullableSerializableElement22 = null;
            Object objDecodeNullableSerializableElement23 = null;
            Object objDecodeNullableSerializableElement24 = null;
            while (z2) {
                Object obj3 = objDecodeNullableSerializableElement12;
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        obj2 = objDecodeNullableSerializableElement15;
                        z2 = false;
                        objDecodeNullableSerializableElement12 = obj3;
                        objDecodeNullableSerializableElement15 = obj2;
                        i4 = 14;
                    case 0:
                        obj2 = objDecodeNullableSerializableElement15;
                        objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, IntSerializer.INSTANCE, obj3);
                        i5 |= 1;
                        objDecodeNullableSerializableElement15 = obj2;
                        i4 = 14;
                    case 1:
                        objDecodeNullableSerializableElement15 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, IntSerializer.INSTANCE, objDecodeNullableSerializableElement15);
                        i5 |= 2;
                        objDecodeNullableSerializableElement12 = obj3;
                        i4 = 14;
                    case 2:
                        objDecodeNullableSerializableElement17 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, StringSerializer.INSTANCE, objDecodeNullableSerializableElement17);
                        i5 |= 4;
                        objDecodeNullableSerializableElement12 = obj3;
                        i4 = 14;
                    case 3:
                        objDecodeNullableSerializableElement16 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, IntSerializer.INSTANCE, objDecodeNullableSerializableElement16);
                        i5 |= 8;
                        objDecodeNullableSerializableElement12 = obj3;
                        i4 = 14;
                    case 4:
                        objDecodeNullableSerializableElement24 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, StringSerializer.INSTANCE, objDecodeNullableSerializableElement24);
                        i5 |= 16;
                        objDecodeNullableSerializableElement12 = obj3;
                        i4 = 14;
                    case 5:
                        objDecodeNullableSerializableElement21 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, IntSerializer.INSTANCE, objDecodeNullableSerializableElement21);
                        i5 |= 32;
                        objDecodeNullableSerializableElement12 = obj3;
                        i4 = 14;
                    case 6:
                        objDecodeNullableSerializableElement23 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, StringSerializer.INSTANCE, objDecodeNullableSerializableElement23);
                        i5 |= 64;
                        objDecodeNullableSerializableElement12 = obj3;
                        i4 = 14;
                    case 7:
                        objDecodeNullableSerializableElement20 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, StringSerializer.INSTANCE, objDecodeNullableSerializableElement20);
                        i5 |= 128;
                        objDecodeNullableSerializableElement12 = obj3;
                        i4 = 14;
                    case 8:
                        objDecodeNullableSerializableElement19 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, StringSerializer.INSTANCE, objDecodeNullableSerializableElement19);
                        i5 |= 256;
                        objDecodeNullableSerializableElement12 = obj3;
                        i4 = 14;
                    case 9:
                        iDecodeIntElement6 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 9);
                        i5 |= 512;
                        objDecodeNullableSerializableElement12 = obj3;
                        i4 = 14;
                    case 10:
                        iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 10);
                        i5 |= 1024;
                        objDecodeNullableSerializableElement12 = obj3;
                        i4 = 14;
                    case 11:
                        objDecodeNullableSerializableElement18 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 11, StringSerializer.INSTANCE, objDecodeNullableSerializableElement18);
                        i5 |= 2048;
                        objDecodeNullableSerializableElement12 = obj3;
                        i4 = 14;
                    case 12:
                        objDecodeNullableSerializableElement22 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 12, StringSerializer.INSTANCE, objDecodeNullableSerializableElement22);
                        i5 |= 4096;
                        objDecodeNullableSerializableElement12 = obj3;
                        i4 = 14;
                    case 13:
                        objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 13, StringSerializer.INSTANCE, objDecodeNullableSerializableElement);
                        i5 |= 8192;
                        objDecodeNullableSerializableElement12 = obj3;
                        i4 = 14;
                    case 14:
                        iDecodeIntElement5 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, i4);
                        i5 |= 16384;
                        objDecodeNullableSerializableElement12 = obj3;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            Object obj4 = objDecodeNullableSerializableElement15;
            i2 = i5;
            obj = objDecodeNullableSerializableElement21;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement22;
            iDecodeIntElement2 = iDecodeIntElement5;
            i3 = iDecodeIntElement6;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement16;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement18;
            objDecodeNullableSerializableElement5 = objDecodeNullableSerializableElement20;
            objDecodeNullableSerializableElement6 = obj4;
            objDecodeNullableSerializableElement7 = objDecodeNullableSerializableElement19;
            objDecodeNullableSerializableElement8 = objDecodeNullableSerializableElement23;
            objDecodeNullableSerializableElement9 = objDecodeNullableSerializableElement24;
            objDecodeNullableSerializableElement10 = objDecodeNullableSerializableElement17;
            objDecodeNullableSerializableElement11 = objDecodeNullableSerializableElement12;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new AuxiliaryVo(i2, (Integer) objDecodeNullableSerializableElement11, (Integer) objDecodeNullableSerializableElement6, (String) objDecodeNullableSerializableElement10, (Integer) objDecodeNullableSerializableElement3, (String) objDecodeNullableSerializableElement9, (Integer) obj, (String) objDecodeNullableSerializableElement8, (String) objDecodeNullableSerializableElement5, (String) objDecodeNullableSerializableElement7, i3, iDecodeIntElement, (String) objDecodeNullableSerializableElement4, (String) objDecodeNullableSerializableElement2, (String) objDecodeNullableSerializableElement, iDecodeIntElement2, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull AuxiliaryVo value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        AuxiliaryVo.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

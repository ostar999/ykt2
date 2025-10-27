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
import kotlinx.serialization.internal.BooleanSerializer;
import kotlinx.serialization.internal.GeneratedSerializer;
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginGeneratedSerialDescriptor;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/InquiryAskInfo.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/InquiryAskInfo;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class InquiryAskInfo$$serializer implements GeneratedSerializer<InquiryAskInfo> {

    @NotNull
    public static final InquiryAskInfo$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        InquiryAskInfo$$serializer inquiryAskInfo$$serializer = new InquiryAskInfo$$serializer();
        INSTANCE = inquiryAskInfo$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.InquiryAskInfo", inquiryAskInfo$$serializer, 12);
        pluginGeneratedSerialDescriptor.addElement("answer", true);
        pluginGeneratedSerialDescriptor.addElement("askId", true);
        pluginGeneratedSerialDescriptor.addElement("askTypeId", true);
        pluginGeneratedSerialDescriptor.addElement("id", true);
        pluginGeneratedSerialDescriptor.addElement("patientId", true);
        pluginGeneratedSerialDescriptor.addElement("question", true);
        pluginGeneratedSerialDescriptor.addElement("type", true);
        pluginGeneratedSerialDescriptor.addElement("operateId", true);
        pluginGeneratedSerialDescriptor.addElement("kind", true);
        pluginGeneratedSerialDescriptor.addElement("itemTitle", true);
        pluginGeneratedSerialDescriptor.addElement("itemReply", true);
        pluginGeneratedSerialDescriptor.addElement("itemSelect", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private InquiryAskInfo$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        BooleanSerializer booleanSerializer = BooleanSerializer.INSTANCE;
        return new KSerializer[]{BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer), intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), booleanSerializer, booleanSerializer};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public InquiryAskInfo deserialize(@NotNull Decoder decoder) {
        Object objDecodeNullableSerializableElement;
        boolean zDecodeBooleanElement;
        int i2;
        boolean zDecodeBooleanElement2;
        Object objDecodeNullableSerializableElement2;
        Object objDecodeNullableSerializableElement3;
        Object objDecodeNullableSerializableElement4;
        Object objDecodeNullableSerializableElement5;
        Object objDecodeNullableSerializableElement6;
        Object objDecodeNullableSerializableElement7;
        Object objDecodeNullableSerializableElement8;
        Object objDecodeNullableSerializableElement9;
        int i3;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i4 = 10;
        int i5 = 9;
        int i6 = 0;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, stringSerializer, null);
            IntSerializer intSerializer = IntSerializer.INSTANCE;
            objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, intSerializer, null);
            objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, intSerializer, null);
            objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, intSerializer, null);
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, intSerializer, null);
            objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, stringSerializer, null);
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, intSerializer, null);
            objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, intSerializer, null);
            int iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 8);
            objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, stringSerializer, null);
            zDecodeBooleanElement2 = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, 10);
            zDecodeBooleanElement = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, 11);
            i2 = iDecodeIntElement;
            i3 = 4095;
        } else {
            int i7 = 11;
            boolean z2 = true;
            boolean zDecodeBooleanElement3 = false;
            int iDecodeIntElement2 = 0;
            Object objDecodeNullableSerializableElement10 = null;
            Object objDecodeNullableSerializableElement11 = null;
            Object objDecodeNullableSerializableElement12 = null;
            Object objDecodeNullableSerializableElement13 = null;
            Object objDecodeNullableSerializableElement14 = null;
            Object objDecodeNullableSerializableElement15 = null;
            Object objDecodeNullableSerializableElement16 = null;
            Object objDecodeNullableSerializableElement17 = null;
            Object objDecodeNullableSerializableElement18 = null;
            boolean zDecodeBooleanElement4 = false;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                        i4 = 10;
                        i5 = 9;
                    case 0:
                        i6 |= 1;
                        objDecodeNullableSerializableElement17 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, StringSerializer.INSTANCE, objDecodeNullableSerializableElement17);
                        i7 = 11;
                        i4 = 10;
                        i5 = 9;
                    case 1:
                        i6 |= 2;
                        objDecodeNullableSerializableElement18 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, IntSerializer.INSTANCE, objDecodeNullableSerializableElement18);
                        i7 = 11;
                        i4 = 10;
                        i5 = 9;
                    case 2:
                        objDecodeNullableSerializableElement16 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, IntSerializer.INSTANCE, objDecodeNullableSerializableElement16);
                        i6 |= 4;
                        i7 = 11;
                        i4 = 10;
                    case 3:
                        objDecodeNullableSerializableElement15 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, IntSerializer.INSTANCE, objDecodeNullableSerializableElement15);
                        i6 |= 8;
                        i7 = 11;
                        i4 = 10;
                    case 4:
                        objDecodeNullableSerializableElement13 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, IntSerializer.INSTANCE, objDecodeNullableSerializableElement13);
                        i6 |= 16;
                        i7 = 11;
                        i4 = 10;
                    case 5:
                        objDecodeNullableSerializableElement14 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, StringSerializer.INSTANCE, objDecodeNullableSerializableElement14);
                        i6 |= 32;
                        i7 = 11;
                        i4 = 10;
                    case 6:
                        objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, IntSerializer.INSTANCE, objDecodeNullableSerializableElement12);
                        i6 |= 64;
                        i7 = 11;
                        i4 = 10;
                    case 7:
                        objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, IntSerializer.INSTANCE, objDecodeNullableSerializableElement11);
                        i6 |= 128;
                        i7 = 11;
                        i4 = 10;
                    case 8:
                        iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 8);
                        i6 |= 256;
                    case 9:
                        objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, i5, StringSerializer.INSTANCE, objDecodeNullableSerializableElement10);
                        i6 |= 512;
                    case 10:
                        zDecodeBooleanElement3 = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, i4);
                        i6 |= 1024;
                    case 11:
                        zDecodeBooleanElement4 = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, i7);
                        i6 |= 2048;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            Object obj = objDecodeNullableSerializableElement17;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement10;
            zDecodeBooleanElement = zDecodeBooleanElement4;
            i2 = iDecodeIntElement2;
            zDecodeBooleanElement2 = zDecodeBooleanElement3;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement11;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement12;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement14;
            objDecodeNullableSerializableElement5 = objDecodeNullableSerializableElement13;
            objDecodeNullableSerializableElement6 = objDecodeNullableSerializableElement15;
            objDecodeNullableSerializableElement7 = objDecodeNullableSerializableElement16;
            objDecodeNullableSerializableElement8 = objDecodeNullableSerializableElement18;
            int i8 = i6;
            objDecodeNullableSerializableElement9 = obj;
            i3 = i8;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new InquiryAskInfo(i3, (String) objDecodeNullableSerializableElement9, (Integer) objDecodeNullableSerializableElement8, (Integer) objDecodeNullableSerializableElement7, (Integer) objDecodeNullableSerializableElement6, (Integer) objDecodeNullableSerializableElement5, (String) objDecodeNullableSerializableElement4, (Integer) objDecodeNullableSerializableElement3, (Integer) objDecodeNullableSerializableElement2, i2, (String) objDecodeNullableSerializableElement, zDecodeBooleanElement2, zDecodeBooleanElement, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull InquiryAskInfo value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        InquiryAskInfo.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

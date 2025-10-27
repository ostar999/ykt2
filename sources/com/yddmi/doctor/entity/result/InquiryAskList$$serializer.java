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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/InquiryAskList.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/InquiryAskList;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class InquiryAskList$$serializer implements GeneratedSerializer<InquiryAskList> {

    @NotNull
    public static final InquiryAskList$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        InquiryAskList$$serializer inquiryAskList$$serializer = new InquiryAskList$$serializer();
        INSTANCE = inquiryAskList$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.InquiryAskList", inquiryAskList$$serializer, 10);
        pluginGeneratedSerialDescriptor.addElement("patientAskList", true);
        pluginGeneratedSerialDescriptor.addElement("askList", true);
        pluginGeneratedSerialDescriptor.addElement("bodyCheckList", true);
        pluginGeneratedSerialDescriptor.addElement("assistCheckList", true);
        pluginGeneratedSerialDescriptor.addElement("diagnoseList", true);
        pluginGeneratedSerialDescriptor.addElement("treatmentList", true);
        pluginGeneratedSerialDescriptor.addElement("diagnoseId", true);
        pluginGeneratedSerialDescriptor.addElement("diagnoseName", true);
        pluginGeneratedSerialDescriptor.addElement("kind", true);
        pluginGeneratedSerialDescriptor.addElement("mScore", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private InquiryAskList$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        InquiryAskInfo$$serializer inquiryAskInfo$$serializer = InquiryAskInfo$$serializer.INSTANCE;
        RowCase$$serializer rowCase$$serializer = RowCase$$serializer.INSTANCE;
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        return new KSerializer[]{BuiltinSerializersKt.getNullable(new ArrayListSerializer(BuiltinSerializersKt.getNullable(inquiryAskInfo$$serializer))), BuiltinSerializersKt.getNullable(new ArrayListSerializer(BuiltinSerializersKt.getNullable(inquiryAskInfo$$serializer))), BuiltinSerializersKt.getNullable(new ArrayListSerializer(BuiltinSerializersKt.getNullable(rowCase$$serializer))), BuiltinSerializersKt.getNullable(new ArrayListSerializer(BuiltinSerializersKt.getNullable(rowCase$$serializer))), BuiltinSerializersKt.getNullable(new ArrayListSerializer(BuiltinSerializersKt.getNullable(rowCase$$serializer))), BuiltinSerializersKt.getNullable(new ArrayListSerializer(BuiltinSerializersKt.getNullable(rowCase$$serializer))), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(StringSerializer.INSTANCE), BuiltinSerializersKt.getNullable(intSerializer), intSerializer};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public InquiryAskList deserialize(@NotNull Decoder decoder) {
        Object objDecodeNullableSerializableElement;
        Object objDecodeNullableSerializableElement2;
        Object objDecodeNullableSerializableElement3;
        Object objDecodeNullableSerializableElement4;
        Object objDecodeNullableSerializableElement5;
        Object objDecodeNullableSerializableElement6;
        Object objDecodeNullableSerializableElement7;
        Object objDecodeNullableSerializableElement8;
        int i2;
        int iDecodeIntElement;
        Object objDecodeNullableSerializableElement9;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i3 = 9;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            InquiryAskInfo$$serializer inquiryAskInfo$$serializer = InquiryAskInfo$$serializer.INSTANCE;
            objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, new ArrayListSerializer(BuiltinSerializersKt.getNullable(inquiryAskInfo$$serializer)), null);
            Object objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, new ArrayListSerializer(BuiltinSerializersKt.getNullable(inquiryAskInfo$$serializer)), null);
            RowCase$$serializer rowCase$$serializer = RowCase$$serializer.INSTANCE;
            objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, new ArrayListSerializer(BuiltinSerializersKt.getNullable(rowCase$$serializer)), null);
            Object objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, new ArrayListSerializer(BuiltinSerializersKt.getNullable(rowCase$$serializer)), null);
            objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, new ArrayListSerializer(BuiltinSerializersKt.getNullable(rowCase$$serializer)), null);
            Object objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, new ArrayListSerializer(BuiltinSerializersKt.getNullable(rowCase$$serializer)), null);
            IntSerializer intSerializer = IntSerializer.INSTANCE;
            objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, intSerializer, null);
            Object objDecodeNullableSerializableElement13 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, StringSerializer.INSTANCE, null);
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, intSerializer, null);
            iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 9);
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement10;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement11;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement12;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement13;
            i2 = 1023;
        } else {
            boolean z2 = true;
            int iDecodeIntElement2 = 0;
            Object objDecodeNullableSerializableElement14 = null;
            objDecodeNullableSerializableElement = null;
            objDecodeNullableSerializableElement2 = null;
            Object objDecodeNullableSerializableElement15 = null;
            Object objDecodeNullableSerializableElement16 = null;
            objDecodeNullableSerializableElement3 = null;
            Object objDecodeNullableSerializableElement17 = null;
            objDecodeNullableSerializableElement4 = null;
            Object objDecodeNullableSerializableElement18 = null;
            int i4 = 0;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                        i3 = 9;
                    case 0:
                        objDecodeNullableSerializableElement17 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, new ArrayListSerializer(BuiltinSerializersKt.getNullable(InquiryAskInfo$$serializer.INSTANCE)), objDecodeNullableSerializableElement17);
                        i4 |= 1;
                        i3 = 9;
                    case 1:
                        objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, new ArrayListSerializer(BuiltinSerializersKt.getNullable(InquiryAskInfo$$serializer.INSTANCE)), objDecodeNullableSerializableElement4);
                        i4 |= 2;
                        i3 = 9;
                    case 2:
                        objDecodeNullableSerializableElement18 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, new ArrayListSerializer(BuiltinSerializersKt.getNullable(RowCase$$serializer.INSTANCE)), objDecodeNullableSerializableElement18);
                        i4 |= 4;
                        i3 = 9;
                    case 3:
                        objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, new ArrayListSerializer(BuiltinSerializersKt.getNullable(RowCase$$serializer.INSTANCE)), objDecodeNullableSerializableElement3);
                        i4 |= 8;
                        i3 = 9;
                    case 4:
                        objDecodeNullableSerializableElement16 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, new ArrayListSerializer(BuiltinSerializersKt.getNullable(RowCase$$serializer.INSTANCE)), objDecodeNullableSerializableElement16);
                        i4 |= 16;
                        i3 = 9;
                    case 5:
                        objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, new ArrayListSerializer(BuiltinSerializersKt.getNullable(RowCase$$serializer.INSTANCE)), objDecodeNullableSerializableElement2);
                        i4 |= 32;
                        i3 = 9;
                    case 6:
                        objDecodeNullableSerializableElement15 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, IntSerializer.INSTANCE, objDecodeNullableSerializableElement15);
                        i4 |= 64;
                        i3 = 9;
                    case 7:
                        objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, StringSerializer.INSTANCE, objDecodeNullableSerializableElement);
                        i4 |= 128;
                        i3 = 9;
                    case 8:
                        objDecodeNullableSerializableElement14 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, IntSerializer.INSTANCE, objDecodeNullableSerializableElement14);
                        i4 |= 256;
                    case 9:
                        iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, i3);
                        i4 |= 512;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            objDecodeNullableSerializableElement5 = objDecodeNullableSerializableElement14;
            objDecodeNullableSerializableElement6 = objDecodeNullableSerializableElement15;
            objDecodeNullableSerializableElement7 = objDecodeNullableSerializableElement16;
            objDecodeNullableSerializableElement8 = objDecodeNullableSerializableElement18;
            i2 = i4;
            Object obj = objDecodeNullableSerializableElement17;
            iDecodeIntElement = iDecodeIntElement2;
            objDecodeNullableSerializableElement9 = obj;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new InquiryAskList(i2, (List) objDecodeNullableSerializableElement9, (List) objDecodeNullableSerializableElement4, (List) objDecodeNullableSerializableElement8, (List) objDecodeNullableSerializableElement3, (List) objDecodeNullableSerializableElement7, (List) objDecodeNullableSerializableElement2, (Integer) objDecodeNullableSerializableElement6, (String) objDecodeNullableSerializableElement, (Integer) objDecodeNullableSerializableElement5, iDecodeIntElement, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull InquiryAskList value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        InquiryAskList.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

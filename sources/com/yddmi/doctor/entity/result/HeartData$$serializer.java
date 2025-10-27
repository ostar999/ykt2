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
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/HeartData.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/HeartData;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class HeartData$$serializer implements GeneratedSerializer<HeartData> {

    @NotNull
    public static final HeartData$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        HeartData$$serializer heartData$$serializer = new HeartData$$serializer();
        INSTANCE = heartData$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.HeartData", heartData$$serializer, 5);
        pluginGeneratedSerialDescriptor.addElement("totalNum", true);
        pluginGeneratedSerialDescriptor.addElement("unMasterNum", true);
        pluginGeneratedSerialDescriptor.addElement("masterNum", true);
        pluginGeneratedSerialDescriptor.addElement("isExchange", true);
        pluginGeneratedSerialDescriptor.addElement("medicalKnowledgeNum", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private HeartData$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        return new KSerializer[]{intSerializer, intSerializer, intSerializer, intSerializer, BuiltinSerializersKt.getNullable(new ArrayListSerializer(HeartDetail$$serializer.INSTANCE))};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public HeartData deserialize(@NotNull Decoder decoder) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        Object objDecodeNullableSerializableElement;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            int iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 0);
            int iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 1);
            int iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 2);
            int iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
            objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, new ArrayListSerializer(HeartDetail$$serializer.INSTANCE), null);
            i2 = iDecodeIntElement;
            i3 = iDecodeIntElement4;
            i4 = iDecodeIntElement3;
            i5 = iDecodeIntElement2;
            i6 = 31;
        } else {
            boolean z2 = true;
            int iDecodeIntElement5 = 0;
            int iDecodeIntElement6 = 0;
            int iDecodeIntElement7 = 0;
            int i7 = 0;
            Object objDecodeNullableSerializableElement2 = null;
            int iDecodeIntElement8 = 0;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                if (iDecodeElementIndex == -1) {
                    z2 = false;
                } else if (iDecodeElementIndex == 0) {
                    iDecodeIntElement5 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 0);
                    i7 |= 1;
                } else if (iDecodeElementIndex == 1) {
                    iDecodeIntElement7 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 1);
                    i7 |= 2;
                } else if (iDecodeElementIndex == 2) {
                    iDecodeIntElement6 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 2);
                    i7 |= 4;
                } else if (iDecodeElementIndex == 3) {
                    iDecodeIntElement8 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
                    i7 |= 8;
                } else {
                    if (iDecodeElementIndex != 4) {
                        throw new UnknownFieldException(iDecodeElementIndex);
                    }
                    objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, new ArrayListSerializer(HeartDetail$$serializer.INSTANCE), objDecodeNullableSerializableElement2);
                    i7 |= 16;
                }
            }
            i2 = iDecodeIntElement5;
            i3 = iDecodeIntElement8;
            i4 = iDecodeIntElement6;
            i5 = iDecodeIntElement7;
            i6 = i7;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement2;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new HeartData(i6, i2, i5, i4, i3, (List) objDecodeNullableSerializableElement, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull HeartData value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        HeartData.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

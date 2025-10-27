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
import kotlinx.serialization.internal.PluginGeneratedSerialDescriptor;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/HomeDaskResult.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/HomeDaskResult;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class HomeDaskResult$$serializer implements GeneratedSerializer<HomeDaskResult> {

    @NotNull
    public static final HomeDaskResult$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        HomeDaskResult$$serializer homeDaskResult$$serializer = new HomeDaskResult$$serializer();
        INSTANCE = homeDaskResult$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.HomeDaskResult", homeDaskResult$$serializer, 5);
        pluginGeneratedSerialDescriptor.addElement("kingKongArea", true);
        pluginGeneratedSerialDescriptor.addElement("kingKongAreaUser", true);
        pluginGeneratedSerialDescriptor.addElement("teachingCenter", true);
        pluginGeneratedSerialDescriptor.addElement("brushQuestionBank", true);
        pluginGeneratedSerialDescriptor.addElement("brushQuestionBankUser", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private HomeDaskResult$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        HomeDaskItem$$serializer homeDaskItem$$serializer = HomeDaskItem$$serializer.INSTANCE;
        return new KSerializer[]{BuiltinSerializersKt.getNullable(new ArrayListSerializer(homeDaskItem$$serializer)), BuiltinSerializersKt.getNullable(new ArrayListSerializer(homeDaskItem$$serializer)), BuiltinSerializersKt.getNullable(new ArrayListSerializer(homeDaskItem$$serializer)), BuiltinSerializersKt.getNullable(new ArrayListSerializer(homeDaskItem$$serializer)), BuiltinSerializersKt.getNullable(new ArrayListSerializer(homeDaskItem$$serializer))};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public HomeDaskResult deserialize(@NotNull Decoder decoder) {
        Object objDecodeNullableSerializableElement;
        Object objDecodeNullableSerializableElement2;
        Object objDecodeNullableSerializableElement3;
        Object objDecodeNullableSerializableElement4;
        int i2;
        Object objDecodeNullableSerializableElement5;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        Object objDecodeNullableSerializableElement6 = null;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            HomeDaskItem$$serializer homeDaskItem$$serializer = HomeDaskItem$$serializer.INSTANCE;
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, new ArrayListSerializer(homeDaskItem$$serializer), null);
            objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, new ArrayListSerializer(homeDaskItem$$serializer), null);
            objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, new ArrayListSerializer(homeDaskItem$$serializer), null);
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, new ArrayListSerializer(homeDaskItem$$serializer), null);
            objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, new ArrayListSerializer(homeDaskItem$$serializer), null);
            i2 = 31;
        } else {
            boolean z2 = true;
            int i3 = 0;
            Object objDecodeNullableSerializableElement7 = null;
            Object objDecodeNullableSerializableElement8 = null;
            Object objDecodeNullableSerializableElement9 = null;
            Object objDecodeNullableSerializableElement10 = null;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                if (iDecodeElementIndex == -1) {
                    z2 = false;
                } else if (iDecodeElementIndex == 0) {
                    objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, new ArrayListSerializer(HomeDaskItem$$serializer.INSTANCE), objDecodeNullableSerializableElement6);
                    i3 |= 1;
                } else if (iDecodeElementIndex == 1) {
                    objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, new ArrayListSerializer(HomeDaskItem$$serializer.INSTANCE), objDecodeNullableSerializableElement7);
                    i3 |= 2;
                } else if (iDecodeElementIndex == 2) {
                    objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, new ArrayListSerializer(HomeDaskItem$$serializer.INSTANCE), objDecodeNullableSerializableElement8);
                    i3 |= 4;
                } else if (iDecodeElementIndex == 3) {
                    objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, new ArrayListSerializer(HomeDaskItem$$serializer.INSTANCE), objDecodeNullableSerializableElement9);
                    i3 |= 8;
                } else {
                    if (iDecodeElementIndex != 4) {
                        throw new UnknownFieldException(iDecodeElementIndex);
                    }
                    objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, new ArrayListSerializer(HomeDaskItem$$serializer.INSTANCE), objDecodeNullableSerializableElement10);
                    i3 |= 16;
                }
            }
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement7;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement8;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement9;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement10;
            Object obj = objDecodeNullableSerializableElement6;
            i2 = i3;
            objDecodeNullableSerializableElement5 = obj;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new HomeDaskResult(i2, (List) objDecodeNullableSerializableElement5, (List) objDecodeNullableSerializableElement, (List) objDecodeNullableSerializableElement2, (List) objDecodeNullableSerializableElement3, (List) objDecodeNullableSerializableElement4, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull HomeDaskResult value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        HomeDaskResult.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

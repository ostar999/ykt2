package com.yddmi.doctor.entity.request;

import com.yddmi.doctor.entity.result.HomeDaskItem$$serializer;
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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/request/HomeDaskSaveReq.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/request/HomeDaskSaveReq;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class HomeDaskSaveReq$$serializer implements GeneratedSerializer<HomeDaskSaveReq> {

    @NotNull
    public static final HomeDaskSaveReq$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        HomeDaskSaveReq$$serializer homeDaskSaveReq$$serializer = new HomeDaskSaveReq$$serializer();
        INSTANCE = homeDaskSaveReq$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.request.HomeDaskSaveReq", homeDaskSaveReq$$serializer, 4);
        pluginGeneratedSerialDescriptor.addElement("orgId", false);
        pluginGeneratedSerialDescriptor.addElement("userId", false);
        pluginGeneratedSerialDescriptor.addElement("orgWorkMenu", true);
        pluginGeneratedSerialDescriptor.addElement("userWorkMenu", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private HomeDaskSaveReq$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        HomeDaskItem$$serializer homeDaskItem$$serializer = HomeDaskItem$$serializer.INSTANCE;
        return new KSerializer[]{intSerializer, intSerializer, BuiltinSerializersKt.getNullable(new ArrayListSerializer(homeDaskItem$$serializer)), BuiltinSerializersKt.getNullable(new ArrayListSerializer(homeDaskItem$$serializer))};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public HomeDaskSaveReq deserialize(@NotNull Decoder decoder) {
        int i2;
        int i3;
        int i4;
        Object objDecodeNullableSerializableElement;
        Object objDecodeNullableSerializableElement2;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            int iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 0);
            int iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 1);
            HomeDaskItem$$serializer homeDaskItem$$serializer = HomeDaskItem$$serializer.INSTANCE;
            objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, new ArrayListSerializer(homeDaskItem$$serializer), null);
            objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, new ArrayListSerializer(homeDaskItem$$serializer), null);
            i2 = iDecodeIntElement;
            i3 = iDecodeIntElement2;
            i4 = 15;
        } else {
            boolean z2 = true;
            int iDecodeIntElement3 = 0;
            int i5 = 0;
            Object objDecodeNullableSerializableElement3 = null;
            Object objDecodeNullableSerializableElement4 = null;
            int iDecodeIntElement4 = 0;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                if (iDecodeElementIndex == -1) {
                    z2 = false;
                } else if (iDecodeElementIndex == 0) {
                    iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 0);
                    i5 |= 1;
                } else if (iDecodeElementIndex == 1) {
                    iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 1);
                    i5 |= 2;
                } else if (iDecodeElementIndex == 2) {
                    objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, new ArrayListSerializer(HomeDaskItem$$serializer.INSTANCE), objDecodeNullableSerializableElement3);
                    i5 |= 4;
                } else {
                    if (iDecodeElementIndex != 3) {
                        throw new UnknownFieldException(iDecodeElementIndex);
                    }
                    objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, new ArrayListSerializer(HomeDaskItem$$serializer.INSTANCE), objDecodeNullableSerializableElement4);
                    i5 |= 8;
                }
            }
            i2 = iDecodeIntElement3;
            i3 = iDecodeIntElement4;
            i4 = i5;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement3;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement4;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new HomeDaskSaveReq(i4, i2, i3, (List) objDecodeNullableSerializableElement, (List) objDecodeNullableSerializableElement2, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull HomeDaskSaveReq value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        HomeDaskSaveReq.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

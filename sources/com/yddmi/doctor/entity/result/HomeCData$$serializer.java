package com.yddmi.doctor.entity.result;

import com.huawei.hms.push.constant.RemoteMessageConst;
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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/HomeCData.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/HomeCData;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class HomeCData$$serializer implements GeneratedSerializer<HomeCData> {

    @NotNull
    public static final HomeCData$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        HomeCData$$serializer homeCData$$serializer = new HomeCData$$serializer();
        INSTANCE = homeCData$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.HomeCData", homeCData$$serializer, 4);
        pluginGeneratedSerialDescriptor.addElement("banner", true);
        pluginGeneratedSerialDescriptor.addElement("porcelain", true);
        pluginGeneratedSerialDescriptor.addElement(RemoteMessageConst.Notification.ICON, true);
        pluginGeneratedSerialDescriptor.addElement("iconBanner", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private HomeCData$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        HomeCIcon$$serializer homeCIcon$$serializer = HomeCIcon$$serializer.INSTANCE;
        return new KSerializer[]{new ArrayListSerializer(HomeCBanner$$serializer.INSTANCE), new ArrayListSerializer(HomeCPorcelain$$serializer.INSTANCE), new ArrayListSerializer(homeCIcon$$serializer), BuiltinSerializersKt.getNullable(new ArrayListSerializer(new ArrayListSerializer(homeCIcon$$serializer)))};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public HomeCData deserialize(@NotNull Decoder decoder) {
        Object objDecodeSerializableElement;
        Object objDecodeSerializableElement2;
        Object objDecodeNullableSerializableElement;
        int i2;
        Object objDecodeSerializableElement3;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        Object objDecodeSerializableElement4 = null;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            objDecodeSerializableElement3 = compositeDecoderBeginStructure.decodeSerializableElement(descriptor2, 0, new ArrayListSerializer(HomeCBanner$$serializer.INSTANCE), null);
            objDecodeSerializableElement = compositeDecoderBeginStructure.decodeSerializableElement(descriptor2, 1, new ArrayListSerializer(HomeCPorcelain$$serializer.INSTANCE), null);
            HomeCIcon$$serializer homeCIcon$$serializer = HomeCIcon$$serializer.INSTANCE;
            objDecodeSerializableElement2 = compositeDecoderBeginStructure.decodeSerializableElement(descriptor2, 2, new ArrayListSerializer(homeCIcon$$serializer), null);
            objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, new ArrayListSerializer(new ArrayListSerializer(homeCIcon$$serializer)), null);
            i2 = 15;
        } else {
            boolean z2 = true;
            int i3 = 0;
            Object objDecodeSerializableElement5 = null;
            Object objDecodeSerializableElement6 = null;
            Object objDecodeNullableSerializableElement2 = null;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                if (iDecodeElementIndex == -1) {
                    z2 = false;
                } else if (iDecodeElementIndex == 0) {
                    objDecodeSerializableElement4 = compositeDecoderBeginStructure.decodeSerializableElement(descriptor2, 0, new ArrayListSerializer(HomeCBanner$$serializer.INSTANCE), objDecodeSerializableElement4);
                    i3 |= 1;
                } else if (iDecodeElementIndex == 1) {
                    objDecodeSerializableElement5 = compositeDecoderBeginStructure.decodeSerializableElement(descriptor2, 1, new ArrayListSerializer(HomeCPorcelain$$serializer.INSTANCE), objDecodeSerializableElement5);
                    i3 |= 2;
                } else if (iDecodeElementIndex == 2) {
                    objDecodeSerializableElement6 = compositeDecoderBeginStructure.decodeSerializableElement(descriptor2, 2, new ArrayListSerializer(HomeCIcon$$serializer.INSTANCE), objDecodeSerializableElement6);
                    i3 |= 4;
                } else {
                    if (iDecodeElementIndex != 3) {
                        throw new UnknownFieldException(iDecodeElementIndex);
                    }
                    objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, new ArrayListSerializer(new ArrayListSerializer(HomeCIcon$$serializer.INSTANCE)), objDecodeNullableSerializableElement2);
                    i3 |= 8;
                }
            }
            objDecodeSerializableElement = objDecodeSerializableElement5;
            objDecodeSerializableElement2 = objDecodeSerializableElement6;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement2;
            Object obj = objDecodeSerializableElement4;
            i2 = i3;
            objDecodeSerializableElement3 = obj;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new HomeCData(i2, (List) objDecodeSerializableElement3, (List) objDecodeSerializableElement, (List) objDecodeSerializableElement2, (List) objDecodeNullableSerializableElement, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull HomeCData value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        HomeCData.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

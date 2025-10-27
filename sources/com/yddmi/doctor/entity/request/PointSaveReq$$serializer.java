package com.yddmi.doctor.entity.request;

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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/request/PointSaveReq.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/request/PointSaveReq;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class PointSaveReq$$serializer implements GeneratedSerializer<PointSaveReq> {

    @NotNull
    public static final PointSaveReq$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        PointSaveReq$$serializer pointSaveReq$$serializer = new PointSaveReq$$serializer();
        INSTANCE = pointSaveReq$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.request.PointSaveReq", pointSaveReq$$serializer, 6);
        pluginGeneratedSerialDescriptor.addElement("equipmentBrand", true);
        pluginGeneratedSerialDescriptor.addElement("equipmentType", true);
        pluginGeneratedSerialDescriptor.addElement("releaseInfo", true);
        pluginGeneratedSerialDescriptor.addElement("userId", true);
        pluginGeneratedSerialDescriptor.addElement("userName", true);
        pluginGeneratedSerialDescriptor.addElement("operationList", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private PointSaveReq$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        return new KSerializer[]{stringSerializer, stringSerializer, stringSerializer, IntSerializer.INSTANCE, stringSerializer, BuiltinSerializersKt.getNullable(new ArrayListSerializer(Operation$$serializer.INSTANCE))};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public PointSaveReq deserialize(@NotNull Decoder decoder) {
        String str;
        Object objDecodeNullableSerializableElement;
        int i2;
        String str2;
        String str3;
        String str4;
        int i3;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i4 = 1;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            String strDecodeStringElement = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 0);
            String strDecodeStringElement2 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 1);
            String strDecodeStringElement3 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 2);
            int iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
            String strDecodeStringElement4 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 4);
            objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, new ArrayListSerializer(Operation$$serializer.INSTANCE), null);
            str4 = strDecodeStringElement;
            i2 = iDecodeIntElement;
            str = strDecodeStringElement4;
            str2 = strDecodeStringElement3;
            str3 = strDecodeStringElement2;
            i3 = 63;
        } else {
            boolean z2 = true;
            int iDecodeIntElement2 = 0;
            String strDecodeStringElement5 = null;
            String strDecodeStringElement6 = null;
            String strDecodeStringElement7 = null;
            String strDecodeStringElement8 = null;
            Object objDecodeNullableSerializableElement2 = null;
            int i5 = 0;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                    case 0:
                        strDecodeStringElement5 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 0);
                        i5 |= 1;
                    case 1:
                        strDecodeStringElement6 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, i4);
                        i5 |= 2;
                    case 2:
                        strDecodeStringElement7 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 2);
                        i5 |= 4;
                        i4 = 1;
                    case 3:
                        iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
                        i5 |= 8;
                        i4 = 1;
                    case 4:
                        strDecodeStringElement8 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 4);
                        i5 |= 16;
                        i4 = 1;
                    case 5:
                        objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, new ArrayListSerializer(Operation$$serializer.INSTANCE), objDecodeNullableSerializableElement2);
                        i5 |= 32;
                        i4 = 1;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            str = strDecodeStringElement8;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement2;
            i2 = iDecodeIntElement2;
            str2 = strDecodeStringElement7;
            str3 = strDecodeStringElement6;
            str4 = strDecodeStringElement5;
            i3 = i5;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new PointSaveReq(i3, str4, str3, str2, i2, str, (List) objDecodeNullableSerializableElement, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull PointSaveReq value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        PointSaveReq.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

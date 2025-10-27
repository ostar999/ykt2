package com.yddmi.doctor.entity.request;

import com.aliyun.vod.log.core.AliyunLogCommon;
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
import kotlinx.serialization.internal.DoubleSerializer;
import kotlinx.serialization.internal.GeneratedSerializer;
import kotlinx.serialization.internal.PluginGeneratedSerialDescriptor;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/request/HomeTenantReq.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/request/HomeTenantReq;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class HomeTenantReq$$serializer implements GeneratedSerializer<HomeTenantReq> {

    @NotNull
    public static final HomeTenantReq$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        HomeTenantReq$$serializer homeTenantReq$$serializer = new HomeTenantReq$$serializer();
        INSTANCE = homeTenantReq$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.request.HomeTenantReq", homeTenantReq$$serializer, 8);
        pluginGeneratedSerialDescriptor.addElement("address", false);
        pluginGeneratedSerialDescriptor.addElement("contacts", false);
        pluginGeneratedSerialDescriptor.addElement("introduction", false);
        pluginGeneratedSerialDescriptor.addElement("latitude", false);
        pluginGeneratedSerialDescriptor.addElement("longitude", false);
        pluginGeneratedSerialDescriptor.addElement("name", false);
        pluginGeneratedSerialDescriptor.addElement(AliyunLogCommon.TERMINAL_TYPE, false);
        pluginGeneratedSerialDescriptor.addElement("reason", false);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private HomeTenantReq$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        DoubleSerializer doubleSerializer = DoubleSerializer.INSTANCE;
        return new KSerializer[]{BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(doubleSerializer), BuiltinSerializersKt.getNullable(doubleSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer)};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public HomeTenantReq deserialize(@NotNull Decoder decoder) {
        Object objDecodeNullableSerializableElement;
        Object objDecodeNullableSerializableElement2;
        int i2;
        Object objDecodeNullableSerializableElement3;
        Object objDecodeNullableSerializableElement4;
        Object objDecodeNullableSerializableElement5;
        Object objDecodeNullableSerializableElement6;
        Object objDecodeNullableSerializableElement7;
        Object objDecodeNullableSerializableElement8;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i3 = 7;
        Object objDecodeNullableSerializableElement9 = null;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, stringSerializer, null);
            Object objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, stringSerializer, null);
            objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, stringSerializer, null);
            DoubleSerializer doubleSerializer = DoubleSerializer.INSTANCE;
            objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, doubleSerializer, null);
            objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, doubleSerializer, null);
            objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, stringSerializer, null);
            Object objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, stringSerializer, null);
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, stringSerializer, null);
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement10;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement11;
            i2 = 255;
        } else {
            boolean z2 = true;
            int i4 = 0;
            Object objDecodeNullableSerializableElement12 = null;
            objDecodeNullableSerializableElement = null;
            Object objDecodeNullableSerializableElement13 = null;
            objDecodeNullableSerializableElement2 = null;
            Object objDecodeNullableSerializableElement14 = null;
            Object objDecodeNullableSerializableElement15 = null;
            Object objDecodeNullableSerializableElement16 = null;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                        i3 = 7;
                    case 0:
                        objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, StringSerializer.INSTANCE, objDecodeNullableSerializableElement9);
                        i4 |= 1;
                        i3 = 7;
                    case 1:
                        objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, StringSerializer.INSTANCE, objDecodeNullableSerializableElement2);
                        i4 |= 2;
                        i3 = 7;
                    case 2:
                        objDecodeNullableSerializableElement14 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, StringSerializer.INSTANCE, objDecodeNullableSerializableElement14);
                        i4 |= 4;
                        i3 = 7;
                    case 3:
                        objDecodeNullableSerializableElement15 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, DoubleSerializer.INSTANCE, objDecodeNullableSerializableElement15);
                        i4 |= 8;
                        i3 = 7;
                    case 4:
                        objDecodeNullableSerializableElement16 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, DoubleSerializer.INSTANCE, objDecodeNullableSerializableElement16);
                        i4 |= 16;
                    case 5:
                        objDecodeNullableSerializableElement13 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, StringSerializer.INSTANCE, objDecodeNullableSerializableElement13);
                        i4 |= 32;
                    case 6:
                        objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, StringSerializer.INSTANCE, objDecodeNullableSerializableElement);
                        i4 |= 64;
                    case 7:
                        objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, i3, StringSerializer.INSTANCE, objDecodeNullableSerializableElement12);
                        i4 |= 128;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            i2 = i4;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement12;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement13;
            objDecodeNullableSerializableElement5 = objDecodeNullableSerializableElement9;
            objDecodeNullableSerializableElement6 = objDecodeNullableSerializableElement14;
            objDecodeNullableSerializableElement7 = objDecodeNullableSerializableElement15;
            objDecodeNullableSerializableElement8 = objDecodeNullableSerializableElement16;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new HomeTenantReq(i2, (String) objDecodeNullableSerializableElement5, (String) objDecodeNullableSerializableElement2, (String) objDecodeNullableSerializableElement6, (Double) objDecodeNullableSerializableElement7, (Double) objDecodeNullableSerializableElement8, (String) objDecodeNullableSerializableElement4, (String) objDecodeNullableSerializableElement, (String) objDecodeNullableSerializableElement3, null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull HomeTenantReq value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        HomeTenantReq.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

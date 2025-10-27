package com.yddmi.doctor.entity.result;

import com.aliyun.vod.log.core.AliyunLogCommon;
import com.psychiatrygarden.utils.Constants;
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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/InquiryCheckInfoLeft.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/InquiryCheckInfoLeft;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class InquiryCheckInfoLeft$$serializer implements GeneratedSerializer<InquiryCheckInfoLeft> {

    @NotNull
    public static final InquiryCheckInfoLeft$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        InquiryCheckInfoLeft$$serializer inquiryCheckInfoLeft$$serializer = new InquiryCheckInfoLeft$$serializer();
        INSTANCE = inquiryCheckInfoLeft$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.InquiryCheckInfoLeft", inquiryCheckInfoLeft$$serializer, 12);
        pluginGeneratedSerialDescriptor.addElement("id", true);
        pluginGeneratedSerialDescriptor.addElement(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_LABEL, true);
        pluginGeneratedSerialDescriptor.addElement("result", true);
        pluginGeneratedSerialDescriptor.addElement("course", true);
        pluginGeneratedSerialDescriptor.addElement(AliyunLogCommon.Module.PUBLISHER, true);
        pluginGeneratedSerialDescriptor.addElement("releaseTime", true);
        pluginGeneratedSerialDescriptor.addElement("title", true);
        pluginGeneratedSerialDescriptor.addElement("type", true);
        pluginGeneratedSerialDescriptor.addElement("itemState", true);
        pluginGeneratedSerialDescriptor.addElement("itemSelected", true);
        pluginGeneratedSerialDescriptor.addElement("itemIsTitle", true);
        pluginGeneratedSerialDescriptor.addElement("itemSelectNum", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private InquiryCheckInfoLeft$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        BooleanSerializer booleanSerializer = BooleanSerializer.INSTANCE;
        return new KSerializer[]{intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, intSerializer, booleanSerializer, booleanSerializer, intSerializer};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public InquiryCheckInfoLeft deserialize(@NotNull Decoder decoder) {
        Object objDecodeNullableSerializableElement;
        Object objDecodeNullableSerializableElement2;
        Object obj;
        int i2;
        Object objDecodeNullableSerializableElement3;
        Object objDecodeNullableSerializableElement4;
        int iDecodeIntElement;
        boolean z2;
        Object objDecodeNullableSerializableElement5;
        int i3;
        boolean z3;
        int i4;
        int i5;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i6 = 11;
        int i7 = 10;
        int i8 = 0;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            int iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 0);
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            Object objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, stringSerializer, null);
            Object objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, stringSerializer, null);
            Object objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, stringSerializer, null);
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, stringSerializer, null);
            objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, stringSerializer, null);
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, stringSerializer, null);
            int iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 7);
            int iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 8);
            boolean zDecodeBooleanElement = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, 9);
            boolean zDecodeBooleanElement2 = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, 10);
            iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 11);
            z2 = zDecodeBooleanElement2;
            z3 = zDecodeBooleanElement;
            i4 = iDecodeIntElement3;
            i5 = iDecodeIntElement4;
            obj = objDecodeNullableSerializableElement6;
            i2 = iDecodeIntElement2;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement7;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement8;
            i3 = 4095;
        } else {
            boolean z4 = true;
            int iDecodeIntElement5 = 0;
            boolean zDecodeBooleanElement3 = false;
            boolean zDecodeBooleanElement4 = false;
            int iDecodeIntElement6 = 0;
            int iDecodeIntElement7 = 0;
            Object objDecodeNullableSerializableElement9 = null;
            Object objDecodeNullableSerializableElement10 = null;
            Object objDecodeNullableSerializableElement11 = null;
            objDecodeNullableSerializableElement = null;
            objDecodeNullableSerializableElement2 = null;
            Object objDecodeNullableSerializableElement12 = null;
            int iDecodeIntElement8 = 0;
            while (z4) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z4 = false;
                        i6 = 11;
                    case 0:
                        iDecodeIntElement5 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 0);
                        i8 |= 1;
                        i6 = 11;
                        i7 = 10;
                    case 1:
                        i8 |= 2;
                        objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, StringSerializer.INSTANCE, objDecodeNullableSerializableElement12);
                        i6 = 11;
                        i7 = 10;
                    case 2:
                        objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, StringSerializer.INSTANCE, objDecodeNullableSerializableElement2);
                        i8 |= 4;
                        i6 = 11;
                    case 3:
                        objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, StringSerializer.INSTANCE, objDecodeNullableSerializableElement);
                        i8 |= 8;
                        i6 = 11;
                    case 4:
                        objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, StringSerializer.INSTANCE, objDecodeNullableSerializableElement10);
                        i8 |= 16;
                        i6 = 11;
                    case 5:
                        objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, StringSerializer.INSTANCE, objDecodeNullableSerializableElement11);
                        i8 |= 32;
                        i6 = 11;
                    case 6:
                        objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, StringSerializer.INSTANCE, objDecodeNullableSerializableElement9);
                        i8 |= 64;
                        i6 = 11;
                    case 7:
                        iDecodeIntElement6 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 7);
                        i8 |= 128;
                    case 8:
                        iDecodeIntElement7 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 8);
                        i8 |= 256;
                    case 9:
                        zDecodeBooleanElement4 = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, 9);
                        i8 |= 512;
                    case 10:
                        zDecodeBooleanElement3 = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, i7);
                        i8 |= 1024;
                    case 11:
                        iDecodeIntElement8 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, i6);
                        i8 |= 2048;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            obj = objDecodeNullableSerializableElement12;
            i2 = iDecodeIntElement5;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement9;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement11;
            iDecodeIntElement = iDecodeIntElement8;
            z2 = zDecodeBooleanElement3;
            objDecodeNullableSerializableElement5 = objDecodeNullableSerializableElement10;
            i3 = i8;
            int i9 = iDecodeIntElement7;
            z3 = zDecodeBooleanElement4;
            i4 = iDecodeIntElement6;
            i5 = i9;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new InquiryCheckInfoLeft(i3, i2, (String) obj, (String) objDecodeNullableSerializableElement2, (String) objDecodeNullableSerializableElement, (String) objDecodeNullableSerializableElement5, (String) objDecodeNullableSerializableElement4, (String) objDecodeNullableSerializableElement3, i4, i5, z3, z2, iDecodeIntElement, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull InquiryCheckInfoLeft value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        InquiryCheckInfoLeft.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

package com.yddmi.doctor.entity.result;

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
import kotlinx.serialization.internal.GeneratedSerializer;
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginGeneratedSerialDescriptor;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/IntegralRow.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/IntegralRow;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class IntegralRow$$serializer implements GeneratedSerializer<IntegralRow> {

    @NotNull
    public static final IntegralRow$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        IntegralRow$$serializer integralRow$$serializer = new IntegralRow$$serializer();
        INSTANCE = integralRow$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.IntegralRow", integralRow$$serializer, 12);
        pluginGeneratedSerialDescriptor.addElement("businessName", true);
        pluginGeneratedSerialDescriptor.addElement("businessType", true);
        pluginGeneratedSerialDescriptor.addElement("createTime", true);
        pluginGeneratedSerialDescriptor.addElement("createUser", true);
        pluginGeneratedSerialDescriptor.addElement("currentScore", true);
        pluginGeneratedSerialDescriptor.addElement("name", true);
        pluginGeneratedSerialDescriptor.addElement(AliyunLogCommon.TERMINAL_TYPE, true);
        pluginGeneratedSerialDescriptor.addElement("reason", true);
        pluginGeneratedSerialDescriptor.addElement("score", true);
        pluginGeneratedSerialDescriptor.addElement("status", true);
        pluginGeneratedSerialDescriptor.addElement("type", true);
        pluginGeneratedSerialDescriptor.addElement("userId", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private IntegralRow$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        return new KSerializer[]{BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, intSerializer, intSerializer, intSerializer};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public IntegralRow deserialize(@NotNull Decoder decoder) {
        int iDecodeIntElement;
        Object objDecodeNullableSerializableElement;
        Object objDecodeNullableSerializableElement2;
        Object objDecodeNullableSerializableElement3;
        int i2;
        int i3;
        int i4;
        int iDecodeIntElement2;
        Object objDecodeNullableSerializableElement4;
        Object objDecodeNullableSerializableElement5;
        Object objDecodeNullableSerializableElement6;
        Object objDecodeNullableSerializableElement7;
        int i5;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i6 = 10;
        int i7 = 9;
        int i8 = 7;
        int i9 = 0;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, stringSerializer, null);
            IntSerializer intSerializer = IntSerializer.INSTANCE;
            objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, intSerializer, null);
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, stringSerializer, null);
            objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, intSerializer, null);
            int iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 4);
            objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, stringSerializer, null);
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, stringSerializer, null);
            objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, stringSerializer, null);
            int iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 8);
            int iDecodeIntElement5 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 9);
            iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 10);
            i3 = iDecodeIntElement5;
            i4 = iDecodeIntElement4;
            iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 11);
            i5 = 4095;
            i2 = iDecodeIntElement3;
        } else {
            int i10 = 11;
            boolean z2 = true;
            int iDecodeIntElement6 = 0;
            int iDecodeIntElement7 = 0;
            int iDecodeIntElement8 = 0;
            iDecodeIntElement = 0;
            Object objDecodeNullableSerializableElement8 = null;
            Object objDecodeNullableSerializableElement9 = null;
            Object objDecodeNullableSerializableElement10 = null;
            Object objDecodeNullableSerializableElement11 = null;
            Object objDecodeNullableSerializableElement12 = null;
            Object objDecodeNullableSerializableElement13 = null;
            Object objDecodeNullableSerializableElement14 = null;
            int iDecodeIntElement9 = 0;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                        i6 = 10;
                        i7 = 9;
                        i8 = 7;
                    case 0:
                        objDecodeNullableSerializableElement13 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, StringSerializer.INSTANCE, objDecodeNullableSerializableElement13);
                        i9 |= 1;
                        i10 = 11;
                        i6 = 10;
                        i7 = 9;
                        i8 = 7;
                    case 1:
                        i9 |= 2;
                        objDecodeNullableSerializableElement14 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, IntSerializer.INSTANCE, objDecodeNullableSerializableElement14);
                        i10 = 11;
                        i6 = 10;
                        i7 = 9;
                    case 2:
                        objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, StringSerializer.INSTANCE, objDecodeNullableSerializableElement12);
                        i9 |= 4;
                        i10 = 11;
                        i6 = 10;
                    case 3:
                        objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, IntSerializer.INSTANCE, objDecodeNullableSerializableElement11);
                        i9 |= 8;
                        i10 = 11;
                        i6 = 10;
                    case 4:
                        iDecodeIntElement8 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 4);
                        i9 |= 16;
                        i10 = 11;
                    case 5:
                        objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, StringSerializer.INSTANCE, objDecodeNullableSerializableElement9);
                        i9 |= 32;
                        i10 = 11;
                    case 6:
                        objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, StringSerializer.INSTANCE, objDecodeNullableSerializableElement10);
                        i9 |= 64;
                        i10 = 11;
                    case 7:
                        objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, i8, StringSerializer.INSTANCE, objDecodeNullableSerializableElement8);
                        i9 |= 128;
                    case 8:
                        iDecodeIntElement7 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 8);
                        i9 |= 256;
                    case 9:
                        iDecodeIntElement9 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, i7);
                        i9 |= 512;
                    case 10:
                        iDecodeIntElement6 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, i6);
                        i9 |= 1024;
                    case 11:
                        iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, i10);
                        i9 |= 2048;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement8;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement9;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement10;
            i2 = iDecodeIntElement8;
            i3 = iDecodeIntElement9;
            i4 = iDecodeIntElement7;
            iDecodeIntElement2 = iDecodeIntElement6;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement11;
            objDecodeNullableSerializableElement5 = objDecodeNullableSerializableElement12;
            objDecodeNullableSerializableElement6 = objDecodeNullableSerializableElement14;
            int i11 = i9;
            objDecodeNullableSerializableElement7 = objDecodeNullableSerializableElement13;
            i5 = i11;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new IntegralRow(i5, (String) objDecodeNullableSerializableElement7, (Integer) objDecodeNullableSerializableElement6, (String) objDecodeNullableSerializableElement5, (Integer) objDecodeNullableSerializableElement4, i2, (String) objDecodeNullableSerializableElement2, (String) objDecodeNullableSerializableElement3, (String) objDecodeNullableSerializableElement, i4, i3, iDecodeIntElement2, iDecodeIntElement, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull IntegralRow value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        IntegralRow.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

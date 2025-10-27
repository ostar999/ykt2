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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/HomeClinicalDetail.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/HomeClinicalDetail;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class HomeClinicalDetail$$serializer implements GeneratedSerializer<HomeClinicalDetail> {

    @NotNull
    public static final HomeClinicalDetail$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        HomeClinicalDetail$$serializer homeClinicalDetail$$serializer = new HomeClinicalDetail$$serializer();
        INSTANCE = homeClinicalDetail$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.HomeClinicalDetail", homeClinicalDetail$$serializer, 11);
        pluginGeneratedSerialDescriptor.addElement("categoryId", true);
        pluginGeneratedSerialDescriptor.addElement("content", true);
        pluginGeneratedSerialDescriptor.addElement("course", true);
        pluginGeneratedSerialDescriptor.addElement("id", true);
        pluginGeneratedSerialDescriptor.addElement(AliyunLogCommon.Module.PUBLISHER, true);
        pluginGeneratedSerialDescriptor.addElement("title", true);
        pluginGeneratedSerialDescriptor.addElement("type", true);
        pluginGeneratedSerialDescriptor.addElement("updateTime", true);
        pluginGeneratedSerialDescriptor.addElement("releaseTime", true);
        pluginGeneratedSerialDescriptor.addElement("fileName", true);
        pluginGeneratedSerialDescriptor.addElement("fileUrl", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private HomeClinicalDetail$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        return new KSerializer[]{intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer)};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public HomeClinicalDetail deserialize(@NotNull Decoder decoder) {
        int iDecodeIntElement;
        Object objDecodeNullableSerializableElement;
        Object objDecodeNullableSerializableElement2;
        Object objDecodeNullableSerializableElement3;
        Object objDecodeNullableSerializableElement4;
        Object objDecodeNullableSerializableElement5;
        Object obj;
        int i2;
        int i3;
        int i4;
        Object obj2;
        Object objDecodeNullableSerializableElement6;
        Object obj3;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i5 = 10;
        int i6 = 9;
        int i7 = 0;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            int iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 0);
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            Object objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, stringSerializer, null);
            Object objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, stringSerializer, null);
            int iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, stringSerializer, null);
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, stringSerializer, null);
            int iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 6);
            objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, stringSerializer, null);
            objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, stringSerializer, null);
            objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, stringSerializer, null);
            objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 10, stringSerializer, null);
            i3 = iDecodeIntElement4;
            iDecodeIntElement = iDecodeIntElement3;
            obj2 = objDecodeNullableSerializableElement7;
            i4 = iDecodeIntElement2;
            obj = objDecodeNullableSerializableElement8;
            i2 = 2047;
        } else {
            boolean z2 = true;
            int iDecodeIntElement5 = 0;
            iDecodeIntElement = 0;
            Object objDecodeNullableSerializableElement9 = null;
            Object objDecodeNullableSerializableElement10 = null;
            Object objDecodeNullableSerializableElement11 = null;
            Object objDecodeNullableSerializableElement12 = null;
            Object objDecodeNullableSerializableElement13 = null;
            Object objDecodeNullableSerializableElement14 = null;
            Object objDecodeNullableSerializableElement15 = null;
            Object objDecodeNullableSerializableElement16 = null;
            int iDecodeIntElement6 = 0;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        obj3 = objDecodeNullableSerializableElement16;
                        z2 = false;
                        i5 = 10;
                        objDecodeNullableSerializableElement16 = obj3;
                        i6 = 9;
                    case 0:
                        obj3 = objDecodeNullableSerializableElement16;
                        i7 |= 1;
                        iDecodeIntElement5 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 0);
                        i5 = 10;
                        objDecodeNullableSerializableElement16 = obj3;
                        i6 = 9;
                    case 1:
                        i7 |= 2;
                        iDecodeIntElement5 = iDecodeIntElement5;
                        i6 = 9;
                        objDecodeNullableSerializableElement16 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, StringSerializer.INSTANCE, objDecodeNullableSerializableElement16);
                        i5 = 10;
                    case 2:
                        objDecodeNullableSerializableElement15 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, StringSerializer.INSTANCE, objDecodeNullableSerializableElement15);
                        i7 |= 4;
                        i5 = 10;
                        i6 = 9;
                    case 3:
                        iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
                        i7 |= 8;
                        i5 = 10;
                        i6 = 9;
                    case 4:
                        objDecodeNullableSerializableElement14 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, StringSerializer.INSTANCE, objDecodeNullableSerializableElement14);
                        i7 |= 16;
                        i5 = 10;
                        i6 = 9;
                    case 5:
                        objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, StringSerializer.INSTANCE, objDecodeNullableSerializableElement12);
                        i7 |= 32;
                        i5 = 10;
                        i6 = 9;
                    case 6:
                        iDecodeIntElement6 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 6);
                        i7 |= 64;
                        i5 = 10;
                    case 7:
                        objDecodeNullableSerializableElement13 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, StringSerializer.INSTANCE, objDecodeNullableSerializableElement13);
                        i7 |= 128;
                        i5 = 10;
                    case 8:
                        objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, StringSerializer.INSTANCE, objDecodeNullableSerializableElement11);
                        i7 |= 256;
                        i5 = 10;
                    case 9:
                        objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, i6, StringSerializer.INSTANCE, objDecodeNullableSerializableElement10);
                        i7 |= 512;
                    case 10:
                        objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, i5, StringSerializer.INSTANCE, objDecodeNullableSerializableElement9);
                        i7 |= 1024;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            Object obj4 = objDecodeNullableSerializableElement16;
            int i8 = iDecodeIntElement5;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement9;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement11;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement12;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement13;
            objDecodeNullableSerializableElement5 = objDecodeNullableSerializableElement14;
            obj = objDecodeNullableSerializableElement15;
            i2 = i7;
            i3 = iDecodeIntElement6;
            i4 = i8;
            Object obj5 = objDecodeNullableSerializableElement10;
            obj2 = obj4;
            objDecodeNullableSerializableElement6 = obj5;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new HomeClinicalDetail(i2, i4, (String) obj2, (String) obj, iDecodeIntElement, (String) objDecodeNullableSerializableElement5, (String) objDecodeNullableSerializableElement3, i3, (String) objDecodeNullableSerializableElement4, (String) objDecodeNullableSerializableElement2, (String) objDecodeNullableSerializableElement6, (String) objDecodeNullableSerializableElement, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull HomeClinicalDetail value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        HomeClinicalDetail.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

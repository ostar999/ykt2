package com.yddmi.doctor.entity.result;

import com.aliyun.auth.common.AliyunVodHttpCommon;
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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/HomeTeachingClassItem.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/HomeTeachingClassItem;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class HomeTeachingClassItem$$serializer implements GeneratedSerializer<HomeTeachingClassItem> {

    @NotNull
    public static final HomeTeachingClassItem$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        HomeTeachingClassItem$$serializer homeTeachingClassItem$$serializer = new HomeTeachingClassItem$$serializer();
        INSTANCE = homeTeachingClassItem$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.HomeTeachingClassItem", homeTeachingClassItem$$serializer, 13);
        pluginGeneratedSerialDescriptor.addElement("categoryId", true);
        pluginGeneratedSerialDescriptor.addElement(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, true);
        pluginGeneratedSerialDescriptor.addElement("createTime", true);
        pluginGeneratedSerialDescriptor.addElement("createUser", true);
        pluginGeneratedSerialDescriptor.addElement("description", true);
        pluginGeneratedSerialDescriptor.addElement("fileList", true);
        pluginGeneratedSerialDescriptor.addElement("id", true);
        pluginGeneratedSerialDescriptor.addElement("isDelete", true);
        pluginGeneratedSerialDescriptor.addElement("name", true);
        pluginGeneratedSerialDescriptor.addElement("status", true);
        pluginGeneratedSerialDescriptor.addElement("updateTime", true);
        pluginGeneratedSerialDescriptor.addElement("updateUser", true);
        pluginGeneratedSerialDescriptor.addElement("version", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private HomeTeachingClassItem$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        return new KSerializer[]{BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(FileList$$serializer.INSTANCE), intSerializer, intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, intSerializer};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public HomeTeachingClassItem deserialize(@NotNull Decoder decoder) {
        int iDecodeIntElement;
        int iDecodeIntElement2;
        Object objDecodeNullableSerializableElement;
        int i2;
        Object objDecodeNullableSerializableElement2;
        Object objDecodeNullableSerializableElement3;
        Object objDecodeNullableSerializableElement4;
        int iDecodeIntElement3;
        int i3;
        int iDecodeIntElement4;
        int iDecodeIntElement5;
        Object objDecodeNullableSerializableElement5;
        Object objDecodeNullableSerializableElement6;
        Object objDecodeNullableSerializableElement7;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i4 = 10;
        Object objDecodeNullableSerializableElement8 = null;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, IntSerializer.INSTANCE, null);
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, stringSerializer, null);
            objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, stringSerializer, null);
            iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
            objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, stringSerializer, null);
            objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, FileList$$serializer.INSTANCE, null);
            int iDecodeIntElement6 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 6);
            int iDecodeIntElement7 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 7);
            objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, stringSerializer, null);
            int iDecodeIntElement8 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 9);
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 10, stringSerializer, null);
            iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 11);
            i3 = iDecodeIntElement8;
            iDecodeIntElement = iDecodeIntElement7;
            iDecodeIntElement2 = iDecodeIntElement6;
            iDecodeIntElement5 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 12);
            i2 = 8191;
        } else {
            int i5 = 12;
            Object objDecodeNullableSerializableElement9 = null;
            Object objDecodeNullableSerializableElement10 = null;
            Object objDecodeNullableSerializableElement11 = null;
            Object objDecodeNullableSerializableElement12 = null;
            boolean z2 = true;
            int i6 = 0;
            int iDecodeIntElement9 = 0;
            int iDecodeIntElement10 = 0;
            iDecodeIntElement = 0;
            iDecodeIntElement2 = 0;
            int iDecodeIntElement11 = 0;
            int iDecodeIntElement12 = 0;
            Object objDecodeNullableSerializableElement13 = null;
            Object objDecodeNullableSerializableElement14 = null;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                        i4 = 10;
                    case 0:
                        objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, IntSerializer.INSTANCE, objDecodeNullableSerializableElement8);
                        i6 |= 1;
                        i5 = 12;
                        i4 = 10;
                    case 1:
                        objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, StringSerializer.INSTANCE, objDecodeNullableSerializableElement9);
                        i6 |= 2;
                        i5 = 12;
                        i4 = 10;
                    case 2:
                        objDecodeNullableSerializableElement14 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, StringSerializer.INSTANCE, objDecodeNullableSerializableElement14);
                        i6 |= 4;
                        i5 = 12;
                        i4 = 10;
                    case 3:
                        iDecodeIntElement11 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
                        i6 |= 8;
                        i5 = 12;
                        i4 = 10;
                    case 4:
                        objDecodeNullableSerializableElement13 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, StringSerializer.INSTANCE, objDecodeNullableSerializableElement13);
                        i6 |= 16;
                        i5 = 12;
                        i4 = 10;
                    case 5:
                        objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, FileList$$serializer.INSTANCE, objDecodeNullableSerializableElement12);
                        i6 |= 32;
                        i5 = 12;
                        i4 = 10;
                    case 6:
                        iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 6);
                        i6 |= 64;
                        i5 = 12;
                    case 7:
                        iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 7);
                        i6 |= 128;
                        i5 = 12;
                    case 8:
                        objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, StringSerializer.INSTANCE, objDecodeNullableSerializableElement10);
                        i6 |= 256;
                        i5 = 12;
                    case 9:
                        iDecodeIntElement10 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 9);
                        i6 |= 512;
                        i5 = 12;
                    case 10:
                        objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, i4, StringSerializer.INSTANCE, objDecodeNullableSerializableElement11);
                        i6 |= 1024;
                        i5 = 12;
                    case 11:
                        iDecodeIntElement9 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 11);
                        i6 |= 2048;
                    case 12:
                        iDecodeIntElement12 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, i5);
                        i6 |= 4096;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement8;
            i2 = i6;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement10;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement11;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement13;
            iDecodeIntElement3 = iDecodeIntElement9;
            i3 = iDecodeIntElement10;
            iDecodeIntElement4 = iDecodeIntElement11;
            iDecodeIntElement5 = iDecodeIntElement12;
            objDecodeNullableSerializableElement5 = objDecodeNullableSerializableElement9;
            objDecodeNullableSerializableElement6 = objDecodeNullableSerializableElement12;
            objDecodeNullableSerializableElement7 = objDecodeNullableSerializableElement14;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new HomeTeachingClassItem(i2, (Integer) objDecodeNullableSerializableElement, (String) objDecodeNullableSerializableElement5, (String) objDecodeNullableSerializableElement7, iDecodeIntElement4, (String) objDecodeNullableSerializableElement4, (FileList) objDecodeNullableSerializableElement6, iDecodeIntElement2, iDecodeIntElement, (String) objDecodeNullableSerializableElement2, i3, (String) objDecodeNullableSerializableElement3, iDecodeIntElement3, iDecodeIntElement5, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull HomeTeachingClassItem value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        HomeTeachingClassItem.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

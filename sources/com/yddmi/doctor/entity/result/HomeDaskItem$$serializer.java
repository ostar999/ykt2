package com.yddmi.doctor.entity.result;

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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/HomeDaskItem.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/HomeDaskItem;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class HomeDaskItem$$serializer implements GeneratedSerializer<HomeDaskItem> {

    @NotNull
    public static final HomeDaskItem$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        HomeDaskItem$$serializer homeDaskItem$$serializer = new HomeDaskItem$$serializer();
        INSTANCE = homeDaskItem$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.HomeDaskItem", homeDaskItem$$serializer, 15);
        pluginGeneratedSerialDescriptor.addElement("appOrgMenuId", true);
        pluginGeneratedSerialDescriptor.addElement("createTime", true);
        pluginGeneratedSerialDescriptor.addElement("createUser", true);
        pluginGeneratedSerialDescriptor.addElement("id", true);
        pluginGeneratedSerialDescriptor.addElement("isDelete", true);
        pluginGeneratedSerialDescriptor.addElement("menuCode", true);
        pluginGeneratedSerialDescriptor.addElement("menuName", true);
        pluginGeneratedSerialDescriptor.addElement("menuType", true);
        pluginGeneratedSerialDescriptor.addElement("orderNum", true);
        pluginGeneratedSerialDescriptor.addElement("orgId", true);
        pluginGeneratedSerialDescriptor.addElement("status", true);
        pluginGeneratedSerialDescriptor.addElement("updateTime", true);
        pluginGeneratedSerialDescriptor.addElement("updateUser", true);
        pluginGeneratedSerialDescriptor.addElement("version", true);
        pluginGeneratedSerialDescriptor.addElement("appOrgMainMenuId", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private HomeDaskItem$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        return new KSerializer[]{BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, intSerializer, intSerializer, stringSerializer, stringSerializer, intSerializer, intSerializer, intSerializer, intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, intSerializer, BuiltinSerializersKt.getNullable(intSerializer)};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public HomeDaskItem deserialize(@NotNull Decoder decoder) {
        int iDecodeIntElement;
        Object obj;
        int i2;
        int i3;
        Object obj2;
        int i4;
        int i5;
        int i6;
        int i7;
        Object obj3;
        Object objDecodeNullableSerializableElement;
        int i8;
        int i9;
        String str;
        String str2;
        int i10;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i11 = 10;
        int i12 = 9;
        int i13 = 4;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            IntSerializer intSerializer = IntSerializer.INSTANCE;
            Object objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, intSerializer, null);
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            Object objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, stringSerializer, null);
            int iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 2);
            int iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
            int iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 4);
            String strDecodeStringElement = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 5);
            String strDecodeStringElement2 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 6);
            int iDecodeIntElement5 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 7);
            int iDecodeIntElement6 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 8);
            int iDecodeIntElement7 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 9);
            int iDecodeIntElement8 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 10);
            Object objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 11, stringSerializer, null);
            int iDecodeIntElement9 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 12);
            obj = objDecodeNullableSerializableElement4;
            int iDecodeIntElement10 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 13);
            objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 14, intSerializer, null);
            i2 = iDecodeIntElement10;
            i4 = iDecodeIntElement9;
            i8 = iDecodeIntElement8;
            i9 = iDecodeIntElement7;
            iDecodeIntElement = iDecodeIntElement5;
            str2 = strDecodeStringElement2;
            str = strDecodeStringElement;
            i5 = iDecodeIntElement3;
            i6 = iDecodeIntElement6;
            i7 = iDecodeIntElement4;
            i10 = iDecodeIntElement2;
            obj3 = objDecodeNullableSerializableElement3;
            i3 = 32767;
            obj2 = objDecodeNullableSerializableElement2;
        } else {
            int i14 = 14;
            int i15 = 0;
            int iDecodeIntElement11 = 0;
            int iDecodeIntElement12 = 0;
            int iDecodeIntElement13 = 0;
            iDecodeIntElement = 0;
            int iDecodeIntElement14 = 0;
            int iDecodeIntElement15 = 0;
            int iDecodeIntElement16 = 0;
            int iDecodeIntElement17 = 0;
            boolean z2 = true;
            Object objDecodeNullableSerializableElement5 = null;
            Object objDecodeNullableSerializableElement6 = null;
            Object objDecodeNullableSerializableElement7 = null;
            String strDecodeStringElement3 = null;
            String strDecodeStringElement4 = null;
            Object objDecodeNullableSerializableElement8 = null;
            int iDecodeIntElement18 = 0;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                        i11 = 10;
                        i12 = 9;
                        i13 = 4;
                    case 0:
                        objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, IntSerializer.INSTANCE, objDecodeNullableSerializableElement7);
                        i15 |= 1;
                        i14 = 14;
                        i11 = 10;
                        i12 = 9;
                        i13 = 4;
                    case 1:
                        objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, StringSerializer.INSTANCE, objDecodeNullableSerializableElement8);
                        i15 |= 2;
                        i14 = 14;
                        i11 = 10;
                        i13 = 4;
                    case 2:
                        iDecodeIntElement17 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 2);
                        i15 |= 4;
                        i14 = 14;
                        i13 = 4;
                    case 3:
                        iDecodeIntElement14 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
                        i15 |= 8;
                        i14 = 14;
                        i13 = 4;
                    case 4:
                        int i16 = i13;
                        iDecodeIntElement16 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, i16);
                        i15 |= 16;
                        i13 = i16;
                        i14 = 14;
                    case 5:
                        strDecodeStringElement3 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 5);
                        i15 |= 32;
                        i14 = 14;
                        i13 = 4;
                    case 6:
                        strDecodeStringElement4 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 6);
                        i15 |= 64;
                        i14 = 14;
                        i13 = 4;
                    case 7:
                        iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 7);
                        i15 |= 128;
                        i14 = 14;
                        i13 = 4;
                    case 8:
                        iDecodeIntElement15 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 8);
                        i15 |= 256;
                        i14 = 14;
                        i13 = 4;
                    case 9:
                        iDecodeIntElement13 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, i12);
                        i15 |= 512;
                        i14 = 14;
                        i13 = 4;
                    case 10:
                        iDecodeIntElement12 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, i11);
                        i15 |= 1024;
                        i14 = 14;
                        i13 = 4;
                    case 11:
                        objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 11, StringSerializer.INSTANCE, objDecodeNullableSerializableElement5);
                        i15 |= 2048;
                        i14 = 14;
                        i13 = 4;
                    case 12:
                        iDecodeIntElement11 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 12);
                        i15 |= 4096;
                        i14 = 14;
                    case 13:
                        iDecodeIntElement18 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 13);
                        i15 |= 8192;
                    case 14:
                        objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, i14, IntSerializer.INSTANCE, objDecodeNullableSerializableElement6);
                        i15 |= 16384;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            obj = objDecodeNullableSerializableElement5;
            i2 = iDecodeIntElement18;
            i3 = i15;
            obj2 = objDecodeNullableSerializableElement7;
            i4 = iDecodeIntElement11;
            i5 = iDecodeIntElement14;
            i6 = iDecodeIntElement15;
            i7 = iDecodeIntElement16;
            obj3 = objDecodeNullableSerializableElement8;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement6;
            i8 = iDecodeIntElement12;
            i9 = iDecodeIntElement13;
            str = strDecodeStringElement3;
            str2 = strDecodeStringElement4;
            i10 = iDecodeIntElement17;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new HomeDaskItem(i3, (Integer) obj2, (String) obj3, i10, i5, i7, str, str2, iDecodeIntElement, i6, i9, i8, (String) obj, i4, i2, (Integer) objDecodeNullableSerializableElement, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull HomeDaskItem value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        HomeDaskItem.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

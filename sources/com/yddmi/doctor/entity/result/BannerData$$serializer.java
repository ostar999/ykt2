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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/BannerData.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/BannerData;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class BannerData$$serializer implements GeneratedSerializer<BannerData> {

    @NotNull
    public static final BannerData$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        BannerData$$serializer bannerData$$serializer = new BannerData$$serializer();
        INSTANCE = bannerData$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.BannerData", bannerData$$serializer, 17);
        pluginGeneratedSerialDescriptor.addElement("bannerPhotoName", true);
        pluginGeneratedSerialDescriptor.addElement("bannerPhotoUrl", true);
        pluginGeneratedSerialDescriptor.addElement("bannerTitle", true);
        pluginGeneratedSerialDescriptor.addElement("bannerType", true);
        pluginGeneratedSerialDescriptor.addElement("content", true);
        pluginGeneratedSerialDescriptor.addElement("contentType", true);
        pluginGeneratedSerialDescriptor.addElement("createTime", true);
        pluginGeneratedSerialDescriptor.addElement("createUser", true);
        pluginGeneratedSerialDescriptor.addElement("id", true);
        pluginGeneratedSerialDescriptor.addElement("isDelete", true);
        pluginGeneratedSerialDescriptor.addElement("pageTurns", true);
        pluginGeneratedSerialDescriptor.addElement("seriaNum", true);
        pluginGeneratedSerialDescriptor.addElement("status", true);
        pluginGeneratedSerialDescriptor.addElement("updateTime", true);
        pluginGeneratedSerialDescriptor.addElement("updateUser", true);
        pluginGeneratedSerialDescriptor.addElement("version", true);
        pluginGeneratedSerialDescriptor.addElement("itemIconType", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private BannerData$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        return new KSerializer[]{BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer), intSerializer};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public BannerData deserialize(@NotNull Decoder decoder) {
        Object objDecodeNullableSerializableElement;
        Object objDecodeNullableSerializableElement2;
        Object obj;
        Object obj2;
        int i2;
        Object obj3;
        Object objDecodeNullableSerializableElement3;
        Object objDecodeNullableSerializableElement4;
        Object objDecodeNullableSerializableElement5;
        Object objDecodeNullableSerializableElement6;
        Object objDecodeNullableSerializableElement7;
        Object objDecodeNullableSerializableElement8;
        Object objDecodeNullableSerializableElement9;
        int iDecodeIntElement;
        int i3;
        Object objDecodeNullableSerializableElement10;
        Object obj4;
        Object objDecodeNullableSerializableElement11;
        Object obj5;
        int i4;
        Object obj6;
        Object obj7;
        Object obj8;
        int i5;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        Object objDecodeNullableSerializableElement12 = null;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, stringSerializer, null);
            objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, stringSerializer, null);
            Object objDecodeNullableSerializableElement13 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, stringSerializer, null);
            IntSerializer intSerializer = IntSerializer.INSTANCE;
            objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, intSerializer, null);
            objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, stringSerializer, null);
            int iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 5);
            objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, stringSerializer, null);
            objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, intSerializer, null);
            Object objDecodeNullableSerializableElement14 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, intSerializer, null);
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, intSerializer, null);
            objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 10, intSerializer, null);
            Object objDecodeNullableSerializableElement15 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 11, intSerializer, null);
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 12, intSerializer, null);
            Object objDecodeNullableSerializableElement16 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 13, stringSerializer, null);
            Object objDecodeNullableSerializableElement17 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 14, intSerializer, null);
            Object objDecodeNullableSerializableElement18 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 15, intSerializer, null);
            obj = objDecodeNullableSerializableElement17;
            iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 16);
            i2 = 131071;
            i3 = iDecodeIntElement2;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement14;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement15;
            obj4 = objDecodeNullableSerializableElement13;
            obj2 = objDecodeNullableSerializableElement18;
            obj3 = objDecodeNullableSerializableElement16;
        } else {
            int i6 = 16;
            boolean z2 = true;
            int iDecodeIntElement3 = 0;
            int i7 = 0;
            int iDecodeIntElement4 = 0;
            Object objDecodeNullableSerializableElement19 = null;
            Object objDecodeNullableSerializableElement20 = null;
            Object objDecodeNullableSerializableElement21 = null;
            Object objDecodeNullableSerializableElement22 = null;
            Object objDecodeNullableSerializableElement23 = null;
            Object objDecodeNullableSerializableElement24 = null;
            Object objDecodeNullableSerializableElement25 = null;
            Object objDecodeNullableSerializableElement26 = null;
            objDecodeNullableSerializableElement = null;
            Object objDecodeNullableSerializableElement27 = null;
            Object objDecodeNullableSerializableElement28 = null;
            objDecodeNullableSerializableElement2 = null;
            Object objDecodeNullableSerializableElement29 = null;
            Object objDecodeNullableSerializableElement30 = null;
            while (z2) {
                Object obj9 = objDecodeNullableSerializableElement12;
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                        objDecodeNullableSerializableElement20 = objDecodeNullableSerializableElement20;
                        i6 = 16;
                        objDecodeNullableSerializableElement12 = obj9;
                        objDecodeNullableSerializableElement21 = objDecodeNullableSerializableElement21;
                    case 0:
                        objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, StringSerializer.INSTANCE, obj9);
                        objDecodeNullableSerializableElement19 = objDecodeNullableSerializableElement19;
                        i7 |= 1;
                        objDecodeNullableSerializableElement20 = objDecodeNullableSerializableElement20;
                        objDecodeNullableSerializableElement21 = objDecodeNullableSerializableElement21;
                        i6 = 16;
                    case 1:
                        objDecodeNullableSerializableElement19 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, StringSerializer.INSTANCE, objDecodeNullableSerializableElement19);
                        i7 |= 2;
                        objDecodeNullableSerializableElement20 = objDecodeNullableSerializableElement20;
                        objDecodeNullableSerializableElement21 = objDecodeNullableSerializableElement21;
                        objDecodeNullableSerializableElement12 = obj9;
                        i6 = 16;
                    case 2:
                        int i8 = i7;
                        obj5 = objDecodeNullableSerializableElement19;
                        objDecodeNullableSerializableElement22 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, StringSerializer.INSTANCE, objDecodeNullableSerializableElement22);
                        i4 = i8 | 4;
                        objDecodeNullableSerializableElement20 = objDecodeNullableSerializableElement20;
                        objDecodeNullableSerializableElement12 = obj9;
                        i6 = 16;
                        Object obj10 = obj5;
                        i7 = i4;
                        objDecodeNullableSerializableElement19 = obj10;
                    case 3:
                        obj6 = objDecodeNullableSerializableElement22;
                        int i9 = i7;
                        obj5 = objDecodeNullableSerializableElement19;
                        objDecodeNullableSerializableElement21 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, IntSerializer.INSTANCE, objDecodeNullableSerializableElement21);
                        i4 = i9 | 8;
                        objDecodeNullableSerializableElement22 = obj6;
                        objDecodeNullableSerializableElement12 = obj9;
                        i6 = 16;
                        Object obj102 = obj5;
                        i7 = i4;
                        objDecodeNullableSerializableElement19 = obj102;
                    case 4:
                        int i10 = i7;
                        obj5 = objDecodeNullableSerializableElement19;
                        objDecodeNullableSerializableElement20 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, StringSerializer.INSTANCE, objDecodeNullableSerializableElement20);
                        i4 = i10 | 16;
                        objDecodeNullableSerializableElement22 = objDecodeNullableSerializableElement22;
                        objDecodeNullableSerializableElement29 = objDecodeNullableSerializableElement29;
                        objDecodeNullableSerializableElement12 = obj9;
                        i6 = 16;
                        Object obj1022 = obj5;
                        i7 = i4;
                        objDecodeNullableSerializableElement19 = obj1022;
                    case 5:
                        iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 5);
                        objDecodeNullableSerializableElement22 = objDecodeNullableSerializableElement22;
                        objDecodeNullableSerializableElement19 = objDecodeNullableSerializableElement19;
                        objDecodeNullableSerializableElement12 = obj9;
                        i7 |= 32;
                        i6 = 16;
                    case 6:
                        obj7 = objDecodeNullableSerializableElement22;
                        int i11 = i7;
                        obj8 = objDecodeNullableSerializableElement19;
                        objDecodeNullableSerializableElement27 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, StringSerializer.INSTANCE, objDecodeNullableSerializableElement27);
                        i5 = i11 | 64;
                        objDecodeNullableSerializableElement22 = obj7;
                        objDecodeNullableSerializableElement19 = obj8;
                        i6 = 16;
                        i7 = i5;
                        objDecodeNullableSerializableElement12 = obj9;
                    case 7:
                        obj7 = objDecodeNullableSerializableElement22;
                        int i12 = i7;
                        obj8 = objDecodeNullableSerializableElement19;
                        objDecodeNullableSerializableElement28 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, IntSerializer.INSTANCE, objDecodeNullableSerializableElement28);
                        i5 = i12 | 128;
                        objDecodeNullableSerializableElement22 = obj7;
                        objDecodeNullableSerializableElement19 = obj8;
                        i6 = 16;
                        i7 = i5;
                        objDecodeNullableSerializableElement12 = obj9;
                    case 8:
                        obj7 = objDecodeNullableSerializableElement22;
                        int i13 = i7;
                        obj8 = objDecodeNullableSerializableElement19;
                        objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, IntSerializer.INSTANCE, objDecodeNullableSerializableElement);
                        i5 = i13 | 256;
                        objDecodeNullableSerializableElement22 = obj7;
                        objDecodeNullableSerializableElement19 = obj8;
                        i6 = 16;
                        i7 = i5;
                        objDecodeNullableSerializableElement12 = obj9;
                    case 9:
                        obj7 = objDecodeNullableSerializableElement22;
                        int i14 = i7;
                        obj8 = objDecodeNullableSerializableElement19;
                        objDecodeNullableSerializableElement26 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, IntSerializer.INSTANCE, objDecodeNullableSerializableElement26);
                        i5 = i14 | 512;
                        objDecodeNullableSerializableElement22 = obj7;
                        objDecodeNullableSerializableElement19 = obj8;
                        i6 = 16;
                        i7 = i5;
                        objDecodeNullableSerializableElement12 = obj9;
                    case 10:
                        obj7 = objDecodeNullableSerializableElement22;
                        int i15 = i7;
                        obj8 = objDecodeNullableSerializableElement19;
                        objDecodeNullableSerializableElement25 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 10, IntSerializer.INSTANCE, objDecodeNullableSerializableElement25);
                        i5 = i15 | 1024;
                        objDecodeNullableSerializableElement22 = obj7;
                        objDecodeNullableSerializableElement19 = obj8;
                        i6 = 16;
                        i7 = i5;
                        objDecodeNullableSerializableElement12 = obj9;
                    case 11:
                        obj7 = objDecodeNullableSerializableElement22;
                        int i16 = i7;
                        obj8 = objDecodeNullableSerializableElement19;
                        objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 11, IntSerializer.INSTANCE, objDecodeNullableSerializableElement2);
                        i5 = i16 | 2048;
                        objDecodeNullableSerializableElement22 = obj7;
                        objDecodeNullableSerializableElement19 = obj8;
                        i6 = 16;
                        i7 = i5;
                        objDecodeNullableSerializableElement12 = obj9;
                    case 12:
                        obj7 = objDecodeNullableSerializableElement22;
                        int i17 = i7;
                        obj8 = objDecodeNullableSerializableElement19;
                        objDecodeNullableSerializableElement24 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 12, IntSerializer.INSTANCE, objDecodeNullableSerializableElement24);
                        i5 = i17 | 4096;
                        objDecodeNullableSerializableElement22 = obj7;
                        objDecodeNullableSerializableElement19 = obj8;
                        i6 = 16;
                        i7 = i5;
                        objDecodeNullableSerializableElement12 = obj9;
                    case 13:
                        obj7 = objDecodeNullableSerializableElement22;
                        int i18 = i7;
                        obj8 = objDecodeNullableSerializableElement19;
                        objDecodeNullableSerializableElement23 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 13, StringSerializer.INSTANCE, objDecodeNullableSerializableElement23);
                        i5 = i18 | 8192;
                        objDecodeNullableSerializableElement22 = obj7;
                        objDecodeNullableSerializableElement19 = obj8;
                        i6 = 16;
                        i7 = i5;
                        objDecodeNullableSerializableElement12 = obj9;
                    case 14:
                        Object obj11 = objDecodeNullableSerializableElement22;
                        objDecodeNullableSerializableElement29 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 14, IntSerializer.INSTANCE, objDecodeNullableSerializableElement29);
                        int i19 = i7 | 16384;
                        objDecodeNullableSerializableElement30 = objDecodeNullableSerializableElement30;
                        objDecodeNullableSerializableElement19 = objDecodeNullableSerializableElement19;
                        objDecodeNullableSerializableElement12 = obj9;
                        i6 = 16;
                        i7 = i19;
                        objDecodeNullableSerializableElement22 = obj11;
                    case 15:
                        int i20 = i7;
                        obj5 = objDecodeNullableSerializableElement19;
                        obj6 = objDecodeNullableSerializableElement22;
                        objDecodeNullableSerializableElement30 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 15, IntSerializer.INSTANCE, objDecodeNullableSerializableElement30);
                        i4 = 32768 | i20;
                        objDecodeNullableSerializableElement22 = obj6;
                        objDecodeNullableSerializableElement12 = obj9;
                        i6 = 16;
                        Object obj10222 = obj5;
                        i7 = i4;
                        objDecodeNullableSerializableElement19 = obj10222;
                    case 16:
                        iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, i6);
                        i7 |= 65536;
                        objDecodeNullableSerializableElement12 = obj9;
                        i6 = 16;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            Object obj12 = objDecodeNullableSerializableElement20;
            obj = objDecodeNullableSerializableElement29;
            obj2 = objDecodeNullableSerializableElement30;
            Object obj13 = objDecodeNullableSerializableElement19;
            i2 = i7;
            obj3 = objDecodeNullableSerializableElement23;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement24;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement25;
            objDecodeNullableSerializableElement5 = objDecodeNullableSerializableElement26;
            objDecodeNullableSerializableElement6 = objDecodeNullableSerializableElement27;
            objDecodeNullableSerializableElement7 = objDecodeNullableSerializableElement28;
            objDecodeNullableSerializableElement8 = obj12;
            objDecodeNullableSerializableElement9 = objDecodeNullableSerializableElement21;
            iDecodeIntElement = iDecodeIntElement3;
            i3 = iDecodeIntElement4;
            objDecodeNullableSerializableElement10 = obj13;
            obj4 = objDecodeNullableSerializableElement22;
            objDecodeNullableSerializableElement11 = objDecodeNullableSerializableElement12;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new BannerData(i2, (String) objDecodeNullableSerializableElement11, (String) objDecodeNullableSerializableElement10, (String) obj4, (Integer) objDecodeNullableSerializableElement9, (String) objDecodeNullableSerializableElement8, i3, (String) objDecodeNullableSerializableElement6, (Integer) objDecodeNullableSerializableElement7, (Integer) objDecodeNullableSerializableElement, (Integer) objDecodeNullableSerializableElement5, (Integer) objDecodeNullableSerializableElement4, (Integer) objDecodeNullableSerializableElement2, (Integer) objDecodeNullableSerializableElement3, (String) obj3, (Integer) obj, (Integer) obj2, iDecodeIntElement, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull BannerData value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        BannerData.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

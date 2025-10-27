package com.yddmi.doctor.entity.result;

import com.aliyun.vod.common.utils.UriUtil;
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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/HomeMsg.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class HomeMsg$$serializer implements GeneratedSerializer<HomeMsg> {

    @NotNull
    public static final HomeMsg$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        HomeMsg$$serializer homeMsg$$serializer = new HomeMsg$$serializer();
        INSTANCE = homeMsg$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.HomeMsg", homeMsg$$serializer, 30);
        pluginGeneratedSerialDescriptor.addElement(UriUtil.QUERY_CATEGORY, true);
        pluginGeneratedSerialDescriptor.addElement("categoryChildren", true);
        pluginGeneratedSerialDescriptor.addElement("content", true);
        pluginGeneratedSerialDescriptor.addElement("createTime", true);
        pluginGeneratedSerialDescriptor.addElement("examId", true);
        pluginGeneratedSerialDescriptor.addElement("id", true);
        pluginGeneratedSerialDescriptor.addElement("isRead", true);
        pluginGeneratedSerialDescriptor.addElement("messageType", true);
        pluginGeneratedSerialDescriptor.addElement("title", true);
        pluginGeneratedSerialDescriptor.addElement("type", true);
        pluginGeneratedSerialDescriptor.addElement("typeName", true);
        pluginGeneratedSerialDescriptor.addElement("updateTime", true);
        pluginGeneratedSerialDescriptor.addElement("userId", true);
        pluginGeneratedSerialDescriptor.addElement("releaseTime", true);
        pluginGeneratedSerialDescriptor.addElement("warnStatus", true);
        pluginGeneratedSerialDescriptor.addElement("name", true);
        pluginGeneratedSerialDescriptor.addElement("reply", true);
        pluginGeneratedSerialDescriptor.addElement("coverUrl", true);
        pluginGeneratedSerialDescriptor.addElement("fileUrl", true);
        pluginGeneratedSerialDescriptor.addElement("skipWay", true);
        pluginGeneratedSerialDescriptor.addElement("skipUrl", true);
        pluginGeneratedSerialDescriptor.addElement("showPic", true);
        pluginGeneratedSerialDescriptor.addElement("status", true);
        pluginGeneratedSerialDescriptor.addElement("popupId", true);
        pluginGeneratedSerialDescriptor.addElement("inviterCode", true);
        pluginGeneratedSerialDescriptor.addElement("inviterId", true);
        pluginGeneratedSerialDescriptor.addElement("inviterUrl", true);
        pluginGeneratedSerialDescriptor.addElement("mFullImg", true);
        pluginGeneratedSerialDescriptor.addElement("mOneDayShow", true);
        pluginGeneratedSerialDescriptor.addElement("mHalfClick", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private HomeMsg$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        BooleanSerializer booleanSerializer = BooleanSerializer.INSTANCE;
        return new KSerializer[]{intSerializer, intSerializer, stringSerializer, stringSerializer, intSerializer, intSerializer, intSerializer, intSerializer, stringSerializer, intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), stringSerializer, stringSerializer, stringSerializer, intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, stringSerializer, stringSerializer, stringSerializer, stringSerializer, booleanSerializer, booleanSerializer, booleanSerializer};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public HomeMsg deserialize(@NotNull Decoder decoder) {
        int i2;
        int iDecodeIntElement;
        Object objDecodeNullableSerializableElement;
        Object objDecodeNullableSerializableElement2;
        Object objDecodeNullableSerializableElement3;
        Object objDecodeNullableSerializableElement4;
        Object objDecodeNullableSerializableElement5;
        Object objDecodeNullableSerializableElement6;
        Object obj;
        int i3;
        boolean z2;
        boolean z3;
        boolean zDecodeBooleanElement;
        int i4;
        int i5;
        int iDecodeIntElement2;
        int i6;
        String strDecodeStringElement;
        String strDecodeStringElement2;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        int i7;
        int i8;
        Object obj2;
        int i9;
        int i10;
        int i11;
        int i12;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i13 = 11;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            int iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 0);
            iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 1);
            strDecodeStringElement = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 2);
            strDecodeStringElement2 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 3);
            int iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 4);
            int iDecodeIntElement5 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 5);
            int iDecodeIntElement6 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 6);
            int iDecodeIntElement7 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 7);
            String strDecodeStringElement3 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 8);
            int iDecodeIntElement8 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 9);
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 10, stringSerializer, null);
            String strDecodeStringElement4 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 11);
            String strDecodeStringElement5 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 12);
            String strDecodeStringElement6 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 13);
            int iDecodeIntElement9 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 14);
            Object objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 15, stringSerializer, null);
            objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 16, stringSerializer, null);
            objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 17, stringSerializer, null);
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 18, stringSerializer, null);
            Object objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 19, stringSerializer, null);
            Object objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 20, stringSerializer, null);
            Object objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 21, stringSerializer, null);
            int iDecodeIntElement10 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 22);
            String strDecodeStringElement7 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 23);
            obj2 = objDecodeNullableSerializableElement10;
            String strDecodeStringElement8 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 24);
            String strDecodeStringElement9 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 25);
            String strDecodeStringElement10 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 26);
            boolean zDecodeBooleanElement2 = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, 27);
            boolean zDecodeBooleanElement3 = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, 28);
            zDecodeBooleanElement = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, 29);
            i9 = iDecodeIntElement10;
            iDecodeIntElement = iDecodeIntElement8;
            str5 = strDecodeStringElement7;
            obj = objDecodeNullableSerializableElement7;
            str6 = strDecodeStringElement8;
            str7 = strDecodeStringElement9;
            str8 = strDecodeStringElement10;
            z2 = zDecodeBooleanElement2;
            z3 = zDecodeBooleanElement3;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement9;
            str = strDecodeStringElement3;
            str3 = strDecodeStringElement5;
            str2 = strDecodeStringElement4;
            i8 = iDecodeIntElement9;
            str4 = strDecodeStringElement6;
            i2 = 1073741823;
            i4 = iDecodeIntElement5;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement8;
            i6 = iDecodeIntElement6;
            i3 = iDecodeIntElement3;
            i5 = iDecodeIntElement4;
            i7 = iDecodeIntElement7;
        } else {
            i2 = 0;
            int iDecodeIntElement11 = 0;
            boolean zDecodeBooleanElement4 = false;
            boolean zDecodeBooleanElement5 = false;
            boolean zDecodeBooleanElement6 = false;
            int iDecodeIntElement12 = 0;
            int iDecodeIntElement13 = 0;
            int iDecodeIntElement14 = 0;
            int iDecodeIntElement15 = 0;
            int iDecodeIntElement16 = 0;
            iDecodeIntElement = 0;
            int iDecodeIntElement17 = 0;
            boolean z4 = true;
            objDecodeNullableSerializableElement = null;
            Object objDecodeNullableSerializableElement11 = null;
            Object objDecodeNullableSerializableElement12 = null;
            Object objDecodeNullableSerializableElement13 = null;
            Object objDecodeNullableSerializableElement14 = null;
            Object objDecodeNullableSerializableElement15 = null;
            objDecodeNullableSerializableElement2 = null;
            String strDecodeStringElement11 = null;
            String strDecodeStringElement12 = null;
            String strDecodeStringElement13 = null;
            String strDecodeStringElement14 = null;
            String strDecodeStringElement15 = null;
            String strDecodeStringElement16 = null;
            String strDecodeStringElement17 = null;
            String strDecodeStringElement18 = null;
            String strDecodeStringElement19 = null;
            String strDecodeStringElement20 = null;
            Object objDecodeNullableSerializableElement16 = null;
            int iDecodeIntElement18 = 0;
            while (z4) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z4 = false;
                    case 0:
                        i2 |= 1;
                        iDecodeIntElement18 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 0);
                        i13 = 11;
                    case 1:
                        iDecodeIntElement14 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 1);
                        i2 |= 2;
                        i13 = 11;
                    case 2:
                        strDecodeStringElement11 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 2);
                        i2 |= 4;
                        i13 = 11;
                    case 3:
                        strDecodeStringElement12 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 3);
                        i2 |= 8;
                        i13 = 11;
                    case 4:
                        iDecodeIntElement13 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 4);
                        i2 |= 16;
                        i13 = 11;
                    case 5:
                        iDecodeIntElement12 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 5);
                        i2 |= 32;
                        i13 = 11;
                    case 6:
                        iDecodeIntElement15 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 6);
                        i2 |= 64;
                        i13 = 11;
                    case 7:
                        iDecodeIntElement17 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 7);
                        i2 |= 128;
                        i13 = 11;
                    case 8:
                        strDecodeStringElement13 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 8);
                        i2 |= 256;
                        i13 = 11;
                    case 9:
                        iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 9);
                        i2 |= 512;
                        i13 = 11;
                    case 10:
                        objDecodeNullableSerializableElement16 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 10, StringSerializer.INSTANCE, objDecodeNullableSerializableElement16);
                        i2 |= 1024;
                        i13 = 11;
                    case 11:
                        strDecodeStringElement14 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, i13);
                        i2 |= 2048;
                    case 12:
                        strDecodeStringElement15 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 12);
                        i2 |= 4096;
                        i13 = 11;
                    case 13:
                        strDecodeStringElement16 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 13);
                        i2 |= 8192;
                        i13 = 11;
                    case 14:
                        iDecodeIntElement11 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 14);
                        i2 |= 16384;
                        i13 = 11;
                    case 15:
                        objDecodeNullableSerializableElement14 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 15, StringSerializer.INSTANCE, objDecodeNullableSerializableElement14);
                        i10 = 32768;
                        i2 |= i10;
                        i13 = 11;
                    case 16:
                        objDecodeNullableSerializableElement13 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 16, StringSerializer.INSTANCE, objDecodeNullableSerializableElement13);
                        i10 = 65536;
                        i2 |= i10;
                        i13 = 11;
                    case 17:
                        objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 17, StringSerializer.INSTANCE, objDecodeNullableSerializableElement11);
                        i10 = 131072;
                        i2 |= i10;
                        i13 = 11;
                    case 18:
                        objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 18, StringSerializer.INSTANCE, objDecodeNullableSerializableElement12);
                        i10 = 262144;
                        i2 |= i10;
                        i13 = 11;
                    case 19:
                        objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 19, StringSerializer.INSTANCE, objDecodeNullableSerializableElement);
                        i10 = 524288;
                        i2 |= i10;
                        i13 = 11;
                    case 20:
                        objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 20, StringSerializer.INSTANCE, objDecodeNullableSerializableElement2);
                        i11 = 1048576;
                        i2 |= i11;
                    case 21:
                        objDecodeNullableSerializableElement15 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 21, StringSerializer.INSTANCE, objDecodeNullableSerializableElement15);
                        i11 = 2097152;
                        i2 |= i11;
                    case 22:
                        iDecodeIntElement16 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 22);
                        i12 = 4194304;
                        i2 |= i12;
                    case 23:
                        strDecodeStringElement17 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 23);
                        i12 = 8388608;
                        i2 |= i12;
                    case 24:
                        strDecodeStringElement18 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 24);
                        i12 = 16777216;
                        i2 |= i12;
                    case 25:
                        strDecodeStringElement19 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 25);
                        i12 = 33554432;
                        i2 |= i12;
                    case 26:
                        strDecodeStringElement20 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 26);
                        i12 = 67108864;
                        i2 |= i12;
                    case 27:
                        zDecodeBooleanElement4 = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, 27);
                        i12 = 134217728;
                        i2 |= i12;
                    case 28:
                        zDecodeBooleanElement5 = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, 28);
                        i12 = 268435456;
                        i2 |= i12;
                    case 29:
                        zDecodeBooleanElement6 = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, 29);
                        i12 = 536870912;
                        i2 |= i12;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement16;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement11;
            objDecodeNullableSerializableElement5 = objDecodeNullableSerializableElement12;
            objDecodeNullableSerializableElement6 = objDecodeNullableSerializableElement13;
            obj = objDecodeNullableSerializableElement14;
            i3 = iDecodeIntElement18;
            z2 = zDecodeBooleanElement4;
            z3 = zDecodeBooleanElement5;
            zDecodeBooleanElement = zDecodeBooleanElement6;
            i4 = iDecodeIntElement12;
            i5 = iDecodeIntElement13;
            iDecodeIntElement2 = iDecodeIntElement14;
            i6 = iDecodeIntElement15;
            strDecodeStringElement = strDecodeStringElement11;
            strDecodeStringElement2 = strDecodeStringElement12;
            str = strDecodeStringElement13;
            str2 = strDecodeStringElement14;
            str3 = strDecodeStringElement15;
            str4 = strDecodeStringElement16;
            str5 = strDecodeStringElement17;
            str6 = strDecodeStringElement18;
            str7 = strDecodeStringElement19;
            str8 = strDecodeStringElement20;
            i7 = iDecodeIntElement17;
            i8 = iDecodeIntElement11;
            obj2 = objDecodeNullableSerializableElement15;
            i9 = iDecodeIntElement16;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new HomeMsg(i2, i3, iDecodeIntElement2, strDecodeStringElement, strDecodeStringElement2, i5, i4, i6, i7, str, iDecodeIntElement, (String) objDecodeNullableSerializableElement3, str2, str3, str4, i8, (String) obj, (String) objDecodeNullableSerializableElement6, (String) objDecodeNullableSerializableElement4, (String) objDecodeNullableSerializableElement5, (String) objDecodeNullableSerializableElement, (String) objDecodeNullableSerializableElement2, (String) obj2, i9, str5, str6, str7, str8, z2, z3, zDecodeBooleanElement, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull HomeMsg value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        HomeMsg.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

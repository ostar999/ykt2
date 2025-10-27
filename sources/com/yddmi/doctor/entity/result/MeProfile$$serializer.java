package com.yddmi.doctor.entity.result;

import com.aliyun.vod.log.core.AliyunLogCommon;
import com.plv.socket.user.PLVSocketUserConstant;
import com.umeng.socialize.net.dplus.CommonNetImpl;
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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/MeProfile.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/MeProfile;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class MeProfile$$serializer implements GeneratedSerializer<MeProfile> {

    @NotNull
    public static final MeProfile$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        MeProfile$$serializer meProfile$$serializer = new MeProfile$$serializer();
        INSTANCE = meProfile$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.MeProfile", meProfile$$serializer, 23);
        pluginGeneratedSerialDescriptor.addElement(PLVSocketUserConstant.ROLE_ADMIN, true);
        pluginGeneratedSerialDescriptor.addElement("avatar", true);
        pluginGeneratedSerialDescriptor.addElement("createTime", true);
        pluginGeneratedSerialDescriptor.addElement("delFlag", true);
        pluginGeneratedSerialDescriptor.addElement("deptId", true);
        pluginGeneratedSerialDescriptor.addElement("email", true);
        pluginGeneratedSerialDescriptor.addElement("loginIp", true);
        pluginGeneratedSerialDescriptor.addElement("nickName", true);
        pluginGeneratedSerialDescriptor.addElement("position", true);
        pluginGeneratedSerialDescriptor.addElement("realName", true);
        pluginGeneratedSerialDescriptor.addElement(CommonNetImpl.SEX, true);
        pluginGeneratedSerialDescriptor.addElement("status", true);
        pluginGeneratedSerialDescriptor.addElement("userId", true);
        pluginGeneratedSerialDescriptor.addElement("userName", true);
        pluginGeneratedSerialDescriptor.addElement(AliyunLogCommon.TERMINAL_TYPE, true);
        pluginGeneratedSerialDescriptor.addElement("roleId", true);
        pluginGeneratedSerialDescriptor.addElement("orgName", true);
        pluginGeneratedSerialDescriptor.addElement("orgId", true);
        pluginGeneratedSerialDescriptor.addElement("studyMode", true);
        pluginGeneratedSerialDescriptor.addElement("identityName", true);
        pluginGeneratedSerialDescriptor.addElement("wxUnionFlag", true);
        pluginGeneratedSerialDescriptor.addElement("wxNickName", true);
        pluginGeneratedSerialDescriptor.addElement("wxHeadImgUrl", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private MeProfile$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        return new KSerializer[]{BooleanSerializer.INSTANCE, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), stringSerializer, BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer)};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public MeProfile deserialize(@NotNull Decoder decoder) {
        Object objDecodeNullableSerializableElement;
        Object objDecodeNullableSerializableElement2;
        String strDecodeStringElement;
        int iDecodeIntElement;
        int iDecodeIntElement2;
        Object objDecodeNullableSerializableElement3;
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        int i2;
        Object objDecodeNullableSerializableElement4;
        Object obj5;
        Object obj6;
        int i3;
        Object obj7;
        Object objDecodeNullableSerializableElement5;
        boolean z2;
        Object obj8;
        Object obj9;
        int i4;
        int i5;
        Object objDecodeNullableSerializableElement6;
        Object obj10;
        Object obj11;
        int i6;
        Object obj12;
        Object obj13;
        Object obj14;
        Object obj15;
        Object obj16;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i7 = 0;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            boolean zDecodeBooleanElement = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, 0);
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            Object objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, stringSerializer, null);
            Object objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, stringSerializer, null);
            Object objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, stringSerializer, null);
            int iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 4);
            Object objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, stringSerializer, null);
            Object objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, stringSerializer, null);
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, stringSerializer, null);
            Object objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, stringSerializer, null);
            Object objDecodeNullableSerializableElement13 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, stringSerializer, null);
            String strDecodeStringElement2 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 10);
            Object objDecodeNullableSerializableElement14 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 11, stringSerializer, null);
            int iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 12);
            Object objDecodeNullableSerializableElement15 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 13, stringSerializer, null);
            objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 14, stringSerializer, null);
            Object objDecodeNullableSerializableElement16 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 15, stringSerializer, null);
            Object objDecodeNullableSerializableElement17 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 16, stringSerializer, null);
            int iDecodeIntElement5 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 17);
            int iDecodeIntElement6 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 18);
            Object objDecodeNullableSerializableElement18 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 19, stringSerializer, null);
            int iDecodeIntElement7 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 20);
            obj7 = objDecodeNullableSerializableElement8;
            objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 21, stringSerializer, null);
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 22, stringSerializer, null);
            obj = objDecodeNullableSerializableElement10;
            i3 = iDecodeIntElement7;
            iDecodeIntElement = iDecodeIntElement5;
            strDecodeStringElement = strDecodeStringElement2;
            obj2 = objDecodeNullableSerializableElement11;
            obj10 = objDecodeNullableSerializableElement7;
            i4 = iDecodeIntElement6;
            obj4 = objDecodeNullableSerializableElement17;
            obj5 = objDecodeNullableSerializableElement15;
            iDecodeIntElement2 = iDecodeIntElement4;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement12;
            i2 = 8388607;
            obj3 = objDecodeNullableSerializableElement14;
            obj6 = objDecodeNullableSerializableElement13;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement16;
            z2 = zDecodeBooleanElement;
            obj8 = objDecodeNullableSerializableElement9;
            i5 = iDecodeIntElement3;
            obj9 = objDecodeNullableSerializableElement18;
        } else {
            Object objDecodeNullableSerializableElement19 = null;
            Object objDecodeNullableSerializableElement20 = null;
            Object objDecodeNullableSerializableElement21 = null;
            Object objDecodeNullableSerializableElement22 = null;
            Object objDecodeNullableSerializableElement23 = null;
            Object objDecodeNullableSerializableElement24 = null;
            Object objDecodeNullableSerializableElement25 = null;
            objDecodeNullableSerializableElement = null;
            Object objDecodeNullableSerializableElement26 = null;
            Object objDecodeNullableSerializableElement27 = null;
            Object objDecodeNullableSerializableElement28 = null;
            objDecodeNullableSerializableElement2 = null;
            Object objDecodeNullableSerializableElement29 = null;
            strDecodeStringElement = null;
            Object objDecodeNullableSerializableElement30 = null;
            Object objDecodeNullableSerializableElement31 = null;
            boolean z3 = true;
            int iDecodeIntElement8 = 0;
            int iDecodeIntElement9 = 0;
            int iDecodeIntElement10 = 0;
            boolean zDecodeBooleanElement2 = false;
            iDecodeIntElement = 0;
            iDecodeIntElement2 = 0;
            Object objDecodeNullableSerializableElement32 = null;
            while (z3) {
                Object obj17 = objDecodeNullableSerializableElement22;
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        obj12 = objDecodeNullableSerializableElement23;
                        obj13 = objDecodeNullableSerializableElement29;
                        z3 = false;
                        objDecodeNullableSerializableElement22 = obj17;
                        objDecodeNullableSerializableElement21 = objDecodeNullableSerializableElement21;
                        objDecodeNullableSerializableElement29 = obj13;
                        objDecodeNullableSerializableElement23 = obj12;
                    case 0:
                        obj12 = objDecodeNullableSerializableElement23;
                        obj13 = objDecodeNullableSerializableElement29;
                        zDecodeBooleanElement2 = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, 0);
                        i7 |= 1;
                        objDecodeNullableSerializableElement22 = obj17;
                        objDecodeNullableSerializableElement21 = objDecodeNullableSerializableElement21;
                        objDecodeNullableSerializableElement20 = objDecodeNullableSerializableElement20;
                        objDecodeNullableSerializableElement29 = obj13;
                        objDecodeNullableSerializableElement23 = obj12;
                    case 1:
                        i7 |= 2;
                        objDecodeNullableSerializableElement30 = objDecodeNullableSerializableElement30;
                        objDecodeNullableSerializableElement23 = objDecodeNullableSerializableElement23;
                        objDecodeNullableSerializableElement22 = obj17;
                        objDecodeNullableSerializableElement20 = objDecodeNullableSerializableElement20;
                        objDecodeNullableSerializableElement29 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, StringSerializer.INSTANCE, objDecodeNullableSerializableElement29);
                        objDecodeNullableSerializableElement21 = objDecodeNullableSerializableElement21;
                    case 2:
                        obj14 = objDecodeNullableSerializableElement20;
                        obj15 = objDecodeNullableSerializableElement21;
                        obj16 = obj17;
                        objDecodeNullableSerializableElement30 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, StringSerializer.INSTANCE, objDecodeNullableSerializableElement30);
                        i7 |= 4;
                        objDecodeNullableSerializableElement23 = objDecodeNullableSerializableElement23;
                        objDecodeNullableSerializableElement22 = obj16;
                        objDecodeNullableSerializableElement21 = obj15;
                        objDecodeNullableSerializableElement20 = obj14;
                    case 3:
                        obj14 = objDecodeNullableSerializableElement20;
                        obj15 = objDecodeNullableSerializableElement21;
                        obj16 = obj17;
                        objDecodeNullableSerializableElement31 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, StringSerializer.INSTANCE, objDecodeNullableSerializableElement31);
                        i7 |= 8;
                        objDecodeNullableSerializableElement22 = obj16;
                        objDecodeNullableSerializableElement21 = obj15;
                        objDecodeNullableSerializableElement20 = obj14;
                    case 4:
                        obj14 = objDecodeNullableSerializableElement20;
                        obj15 = objDecodeNullableSerializableElement21;
                        iDecodeIntElement9 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 4);
                        i7 |= 16;
                        objDecodeNullableSerializableElement22 = obj17;
                        objDecodeNullableSerializableElement21 = obj15;
                        objDecodeNullableSerializableElement20 = obj14;
                    case 5:
                        obj14 = objDecodeNullableSerializableElement20;
                        obj15 = objDecodeNullableSerializableElement21;
                        objDecodeNullableSerializableElement22 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, StringSerializer.INSTANCE, obj17);
                        i7 |= 32;
                        objDecodeNullableSerializableElement21 = obj15;
                        objDecodeNullableSerializableElement20 = obj14;
                    case 6:
                        objDecodeNullableSerializableElement23 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, StringSerializer.INSTANCE, objDecodeNullableSerializableElement23);
                        i7 |= 64;
                        objDecodeNullableSerializableElement20 = objDecodeNullableSerializableElement20;
                        objDecodeNullableSerializableElement22 = obj17;
                    case 7:
                        obj11 = objDecodeNullableSerializableElement23;
                        objDecodeNullableSerializableElement21 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, StringSerializer.INSTANCE, objDecodeNullableSerializableElement21);
                        i7 |= 128;
                        objDecodeNullableSerializableElement22 = obj17;
                        objDecodeNullableSerializableElement23 = obj11;
                    case 8:
                        obj11 = objDecodeNullableSerializableElement23;
                        objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, StringSerializer.INSTANCE, objDecodeNullableSerializableElement2);
                        i7 |= 256;
                        objDecodeNullableSerializableElement22 = obj17;
                        objDecodeNullableSerializableElement23 = obj11;
                    case 9:
                        obj11 = objDecodeNullableSerializableElement23;
                        objDecodeNullableSerializableElement28 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, StringSerializer.INSTANCE, objDecodeNullableSerializableElement28);
                        i7 |= 512;
                        objDecodeNullableSerializableElement22 = obj17;
                        objDecodeNullableSerializableElement23 = obj11;
                    case 10:
                        obj11 = objDecodeNullableSerializableElement23;
                        strDecodeStringElement = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 10);
                        i7 |= 1024;
                        objDecodeNullableSerializableElement22 = obj17;
                        objDecodeNullableSerializableElement23 = obj11;
                    case 11:
                        obj11 = objDecodeNullableSerializableElement23;
                        objDecodeNullableSerializableElement24 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 11, StringSerializer.INSTANCE, objDecodeNullableSerializableElement24);
                        i7 |= 2048;
                        objDecodeNullableSerializableElement22 = obj17;
                        objDecodeNullableSerializableElement23 = obj11;
                    case 12:
                        obj11 = objDecodeNullableSerializableElement23;
                        iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 12);
                        i7 |= 4096;
                        objDecodeNullableSerializableElement22 = obj17;
                        objDecodeNullableSerializableElement23 = obj11;
                    case 13:
                        obj11 = objDecodeNullableSerializableElement23;
                        objDecodeNullableSerializableElement27 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 13, StringSerializer.INSTANCE, objDecodeNullableSerializableElement27);
                        i7 |= 8192;
                        objDecodeNullableSerializableElement22 = obj17;
                        objDecodeNullableSerializableElement23 = obj11;
                    case 14:
                        obj11 = objDecodeNullableSerializableElement23;
                        objDecodeNullableSerializableElement26 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 14, StringSerializer.INSTANCE, objDecodeNullableSerializableElement26);
                        i7 |= 16384;
                        objDecodeNullableSerializableElement22 = obj17;
                        objDecodeNullableSerializableElement23 = obj11;
                    case 15:
                        obj11 = objDecodeNullableSerializableElement23;
                        objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 15, StringSerializer.INSTANCE, objDecodeNullableSerializableElement);
                        i6 = 32768;
                        i7 |= i6;
                        objDecodeNullableSerializableElement22 = obj17;
                        objDecodeNullableSerializableElement23 = obj11;
                    case 16:
                        obj11 = objDecodeNullableSerializableElement23;
                        objDecodeNullableSerializableElement19 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 16, StringSerializer.INSTANCE, objDecodeNullableSerializableElement19);
                        i6 = 65536;
                        i7 |= i6;
                        objDecodeNullableSerializableElement22 = obj17;
                        objDecodeNullableSerializableElement23 = obj11;
                    case 17:
                        obj11 = objDecodeNullableSerializableElement23;
                        iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 17);
                        i7 |= 131072;
                        objDecodeNullableSerializableElement22 = obj17;
                        objDecodeNullableSerializableElement23 = obj11;
                    case 18:
                        obj11 = objDecodeNullableSerializableElement23;
                        iDecodeIntElement8 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 18);
                        i7 |= 262144;
                        objDecodeNullableSerializableElement22 = obj17;
                        objDecodeNullableSerializableElement23 = obj11;
                    case 19:
                        obj11 = objDecodeNullableSerializableElement23;
                        objDecodeNullableSerializableElement25 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 19, StringSerializer.INSTANCE, objDecodeNullableSerializableElement25);
                        i6 = 524288;
                        i7 |= i6;
                        objDecodeNullableSerializableElement22 = obj17;
                        objDecodeNullableSerializableElement23 = obj11;
                    case 20:
                        obj11 = objDecodeNullableSerializableElement23;
                        iDecodeIntElement10 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 20);
                        i6 = 1048576;
                        i7 |= i6;
                        objDecodeNullableSerializableElement22 = obj17;
                        objDecodeNullableSerializableElement23 = obj11;
                    case 21:
                        obj11 = objDecodeNullableSerializableElement23;
                        objDecodeNullableSerializableElement32 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 21, StringSerializer.INSTANCE, objDecodeNullableSerializableElement32);
                        i6 = 2097152;
                        i7 |= i6;
                        objDecodeNullableSerializableElement22 = obj17;
                        objDecodeNullableSerializableElement23 = obj11;
                    case 22:
                        obj11 = objDecodeNullableSerializableElement23;
                        objDecodeNullableSerializableElement20 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 22, StringSerializer.INSTANCE, objDecodeNullableSerializableElement20);
                        i6 = 4194304;
                        i7 |= i6;
                        objDecodeNullableSerializableElement22 = obj17;
                        objDecodeNullableSerializableElement23 = obj11;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement20;
            Object obj18 = objDecodeNullableSerializableElement21;
            obj = objDecodeNullableSerializableElement22;
            obj2 = objDecodeNullableSerializableElement23;
            obj3 = objDecodeNullableSerializableElement24;
            obj4 = objDecodeNullableSerializableElement19;
            i2 = i7;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement26;
            obj5 = objDecodeNullableSerializableElement27;
            obj6 = objDecodeNullableSerializableElement28;
            i3 = iDecodeIntElement10;
            obj7 = objDecodeNullableSerializableElement30;
            objDecodeNullableSerializableElement5 = obj18;
            z2 = zDecodeBooleanElement2;
            obj8 = objDecodeNullableSerializableElement31;
            obj9 = objDecodeNullableSerializableElement25;
            i4 = iDecodeIntElement8;
            i5 = iDecodeIntElement9;
            objDecodeNullableSerializableElement6 = objDecodeNullableSerializableElement32;
            obj10 = objDecodeNullableSerializableElement29;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new MeProfile(i2, z2, (String) obj10, (String) obj7, (String) obj8, i5, (String) obj, (String) obj2, (String) objDecodeNullableSerializableElement5, (String) objDecodeNullableSerializableElement2, (String) obj6, strDecodeStringElement, (String) obj3, iDecodeIntElement2, (String) obj5, (String) objDecodeNullableSerializableElement4, (String) objDecodeNullableSerializableElement, (String) obj4, iDecodeIntElement, i4, (String) obj9, i3, (String) objDecodeNullableSerializableElement6, (String) objDecodeNullableSerializableElement3, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull MeProfile value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        MeProfile.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

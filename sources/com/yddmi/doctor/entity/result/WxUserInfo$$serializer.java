package com.yddmi.doctor.entity.result;

import com.umeng.analytics.pro.am;
import com.umeng.socialize.net.dplus.CommonNetImpl;
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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/WxUserInfo.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/WxUserInfo;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class WxUserInfo$$serializer implements GeneratedSerializer<WxUserInfo> {

    @NotNull
    public static final WxUserInfo$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        WxUserInfo$$serializer wxUserInfo$$serializer = new WxUserInfo$$serializer();
        INSTANCE = wxUserInfo$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.WxUserInfo", wxUserInfo$$serializer, 9);
        pluginGeneratedSerialDescriptor.addElement("city", false);
        pluginGeneratedSerialDescriptor.addElement(am.O, false);
        pluginGeneratedSerialDescriptor.addElement("headimgurl", true);
        pluginGeneratedSerialDescriptor.addElement("nickname", false);
        pluginGeneratedSerialDescriptor.addElement("openid", false);
        pluginGeneratedSerialDescriptor.addElement("privilege", true);
        pluginGeneratedSerialDescriptor.addElement("province", true);
        pluginGeneratedSerialDescriptor.addElement(CommonNetImpl.SEX, false);
        pluginGeneratedSerialDescriptor.addElement("unionid", false);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private WxUserInfo$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        return new KSerializer[]{stringSerializer, stringSerializer, BuiltinSerializersKt.getNullable(stringSerializer), stringSerializer, stringSerializer, BuiltinSerializersKt.getNullable(new ArrayListSerializer(stringSerializer)), BuiltinSerializersKt.getNullable(stringSerializer), IntSerializer.INSTANCE, stringSerializer};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public WxUserInfo deserialize(@NotNull Decoder decoder) {
        Object objDecodeNullableSerializableElement;
        Object objDecodeNullableSerializableElement2;
        int i2;
        String str;
        String str2;
        Object objDecodeNullableSerializableElement3;
        String str3;
        String str4;
        String strDecodeStringElement;
        int iDecodeIntElement;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i3 = 7;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            String strDecodeStringElement2 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 0);
            String strDecodeStringElement3 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 1);
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, stringSerializer, null);
            String strDecodeStringElement4 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 3);
            String strDecodeStringElement5 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 4);
            objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, new ArrayListSerializer(stringSerializer), null);
            objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, stringSerializer, null);
            i2 = 511;
            str = strDecodeStringElement2;
            iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 7);
            str3 = strDecodeStringElement4;
            strDecodeStringElement = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 8);
            str4 = strDecodeStringElement5;
            str2 = strDecodeStringElement3;
        } else {
            boolean z2 = true;
            int iDecodeIntElement2 = 0;
            Object objDecodeNullableSerializableElement4 = null;
            String strDecodeStringElement6 = null;
            String strDecodeStringElement7 = null;
            Object objDecodeNullableSerializableElement5 = null;
            String strDecodeStringElement8 = null;
            String strDecodeStringElement9 = null;
            String strDecodeStringElement10 = null;
            Object objDecodeNullableSerializableElement6 = null;
            int i4 = 0;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                        i3 = 7;
                    case 0:
                        i4 |= 1;
                        strDecodeStringElement6 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 0);
                        i3 = 7;
                    case 1:
                        strDecodeStringElement7 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 1);
                        i4 |= 2;
                        i3 = 7;
                    case 2:
                        objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, StringSerializer.INSTANCE, objDecodeNullableSerializableElement5);
                        i4 |= 4;
                        i3 = 7;
                    case 3:
                        strDecodeStringElement8 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 3);
                        i4 |= 8;
                        i3 = 7;
                    case 4:
                        strDecodeStringElement9 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 4);
                        i4 |= 16;
                        i3 = 7;
                    case 5:
                        objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, new ArrayListSerializer(StringSerializer.INSTANCE), objDecodeNullableSerializableElement6);
                        i4 |= 32;
                        i3 = 7;
                    case 6:
                        objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, StringSerializer.INSTANCE, objDecodeNullableSerializableElement4);
                        i4 |= 64;
                    case 7:
                        iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, i3);
                        i4 |= 128;
                    case 8:
                        strDecodeStringElement10 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 8);
                        i4 |= 256;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement4;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement6;
            i2 = i4;
            str = strDecodeStringElement6;
            str2 = strDecodeStringElement7;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement5;
            str3 = strDecodeStringElement8;
            str4 = strDecodeStringElement9;
            strDecodeStringElement = strDecodeStringElement10;
            iDecodeIntElement = iDecodeIntElement2;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new WxUserInfo(i2, str, str2, (String) objDecodeNullableSerializableElement3, str3, str4, (List) objDecodeNullableSerializableElement2, (String) objDecodeNullableSerializableElement, iDecodeIntElement, strDecodeStringElement, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull WxUserInfo value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        WxUserInfo.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

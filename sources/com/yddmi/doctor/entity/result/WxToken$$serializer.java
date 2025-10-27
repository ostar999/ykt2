package com.yddmi.doctor.entity.result;

import com.tencent.connect.common.Constants;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.UnknownFieldException;
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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/WxToken.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/WxToken;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class WxToken$$serializer implements GeneratedSerializer<WxToken> {

    @NotNull
    public static final WxToken$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        WxToken$$serializer wxToken$$serializer = new WxToken$$serializer();
        INSTANCE = wxToken$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.WxToken", wxToken$$serializer, 7);
        pluginGeneratedSerialDescriptor.addElement("access_token", false);
        pluginGeneratedSerialDescriptor.addElement("expires_in", false);
        pluginGeneratedSerialDescriptor.addElement("openid", false);
        pluginGeneratedSerialDescriptor.addElement("refresh_token", false);
        pluginGeneratedSerialDescriptor.addElement(Constants.PARAM_SCOPE, false);
        pluginGeneratedSerialDescriptor.addElement("unionid", false);
        pluginGeneratedSerialDescriptor.addElement("mCode", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private WxToken$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        return new KSerializer[]{stringSerializer, IntSerializer.INSTANCE, stringSerializer, stringSerializer, stringSerializer, stringSerializer, stringSerializer};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public WxToken deserialize(@NotNull Decoder decoder) {
        String strDecodeStringElement;
        String strDecodeStringElement2;
        String str;
        int i2;
        String str2;
        int i3;
        String str3;
        String str4;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            String strDecodeStringElement3 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 0);
            int iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 1);
            String strDecodeStringElement4 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 2);
            String strDecodeStringElement5 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 3);
            String strDecodeStringElement6 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 4);
            String strDecodeStringElement7 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 5);
            str4 = strDecodeStringElement3;
            strDecodeStringElement2 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 6);
            str = strDecodeStringElement7;
            str3 = strDecodeStringElement5;
            str2 = strDecodeStringElement6;
            strDecodeStringElement = strDecodeStringElement4;
            i3 = iDecodeIntElement;
            i2 = 127;
        } else {
            String strDecodeStringElement8 = null;
            String strDecodeStringElement9 = null;
            String strDecodeStringElement10 = null;
            String strDecodeStringElement11 = null;
            String strDecodeStringElement12 = null;
            strDecodeStringElement = null;
            boolean z2 = true;
            int iDecodeIntElement2 = 0;
            int i4 = 0;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                        continue;
                    case 0:
                        strDecodeStringElement8 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 0);
                        i4 |= 1;
                        continue;
                    case 1:
                        iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 1);
                        i4 |= 2;
                        break;
                    case 2:
                        strDecodeStringElement = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 2);
                        i4 |= 4;
                        break;
                    case 3:
                        strDecodeStringElement11 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 3);
                        i4 |= 8;
                        break;
                    case 4:
                        strDecodeStringElement12 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 4);
                        i4 |= 16;
                        break;
                    case 5:
                        strDecodeStringElement10 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 5);
                        i4 |= 32;
                        break;
                    case 6:
                        strDecodeStringElement9 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 6);
                        i4 |= 64;
                        break;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            strDecodeStringElement2 = strDecodeStringElement9;
            str = strDecodeStringElement10;
            i2 = i4;
            str2 = strDecodeStringElement12;
            i3 = iDecodeIntElement2;
            str3 = strDecodeStringElement11;
            str4 = strDecodeStringElement8;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new WxToken(i2, str4, i3, strDecodeStringElement, str3, str2, str, strDecodeStringElement2, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull WxToken value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        WxToken.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

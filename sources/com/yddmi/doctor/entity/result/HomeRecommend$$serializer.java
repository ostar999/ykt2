package com.yddmi.doctor.entity.result;

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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/HomeRecommend.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/HomeRecommend;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class HomeRecommend$$serializer implements GeneratedSerializer<HomeRecommend> {

    @NotNull
    public static final HomeRecommend$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        HomeRecommend$$serializer homeRecommend$$serializer = new HomeRecommend$$serializer();
        INSTANCE = homeRecommend$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.HomeRecommend", homeRecommend$$serializer, 9);
        pluginGeneratedSerialDescriptor.addElement("createTime", true);
        pluginGeneratedSerialDescriptor.addElement("fileUrl", true);
        pluginGeneratedSerialDescriptor.addElement("inviterCode", true);
        pluginGeneratedSerialDescriptor.addElement("inviterId", true);
        pluginGeneratedSerialDescriptor.addElement("inviterUrl", true);
        pluginGeneratedSerialDescriptor.addElement("name", true);
        pluginGeneratedSerialDescriptor.addElement("num", true);
        pluginGeneratedSerialDescriptor.addElement("phoneNo", true);
        pluginGeneratedSerialDescriptor.addElement("userId", false);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private HomeRecommend$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        return new KSerializer[]{stringSerializer, stringSerializer, stringSerializer, intSerializer, stringSerializer, stringSerializer, intSerializer, stringSerializer, intSerializer};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public HomeRecommend deserialize(@NotNull Decoder decoder) {
        String strDecodeStringElement;
        String strDecodeStringElement2;
        int iDecodeIntElement;
        int i2;
        int i3;
        String str;
        String str2;
        String str3;
        int i4;
        String str4;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i5 = 0;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            String strDecodeStringElement3 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 0);
            String strDecodeStringElement4 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 1);
            String strDecodeStringElement5 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 2);
            int iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
            String strDecodeStringElement6 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 4);
            String strDecodeStringElement7 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 5);
            int iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 6);
            str4 = strDecodeStringElement3;
            strDecodeStringElement2 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 7);
            i4 = iDecodeIntElement3;
            str2 = strDecodeStringElement7;
            i3 = iDecodeIntElement2;
            iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 8);
            strDecodeStringElement = strDecodeStringElement6;
            str = strDecodeStringElement5;
            str3 = strDecodeStringElement4;
            i2 = 511;
        } else {
            String strDecodeStringElement8 = null;
            String strDecodeStringElement9 = null;
            String strDecodeStringElement10 = null;
            strDecodeStringElement = null;
            String strDecodeStringElement11 = null;
            String strDecodeStringElement12 = null;
            boolean z2 = true;
            int iDecodeIntElement4 = 0;
            int iDecodeIntElement5 = 0;
            int iDecodeIntElement6 = 0;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                        break;
                    case 0:
                        i5 |= 1;
                        strDecodeStringElement8 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 0);
                        continue;
                    case 1:
                        strDecodeStringElement12 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 1);
                        i5 |= 2;
                        continue;
                    case 2:
                        strDecodeStringElement11 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 2);
                        i5 |= 4;
                        break;
                    case 3:
                        iDecodeIntElement5 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
                        i5 |= 8;
                        break;
                    case 4:
                        strDecodeStringElement = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 4);
                        i5 |= 16;
                        break;
                    case 5:
                        strDecodeStringElement10 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 5);
                        i5 |= 32;
                        break;
                    case 6:
                        iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 6);
                        i5 |= 64;
                        break;
                    case 7:
                        strDecodeStringElement9 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 7);
                        i5 |= 128;
                        break;
                    case 8:
                        iDecodeIntElement6 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 8);
                        i5 |= 256;
                        break;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            strDecodeStringElement2 = strDecodeStringElement9;
            iDecodeIntElement = iDecodeIntElement6;
            i2 = i5;
            i3 = iDecodeIntElement5;
            str = strDecodeStringElement11;
            str2 = strDecodeStringElement10;
            str3 = strDecodeStringElement12;
            i4 = iDecodeIntElement4;
            str4 = strDecodeStringElement8;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new HomeRecommend(i2, str4, str3, str, i3, strDecodeStringElement, str2, i4, strDecodeStringElement2, iDecodeIntElement, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull HomeRecommend value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        HomeRecommend.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

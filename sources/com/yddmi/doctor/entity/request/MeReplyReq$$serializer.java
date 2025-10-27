package com.yddmi.doctor.entity.request;

import androidx.core.app.NotificationCompat;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.tencent.open.SocialConstants;
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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/request/MeReplyReq.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/request/MeReplyReq;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class MeReplyReq$$serializer implements GeneratedSerializer<MeReplyReq> {

    @NotNull
    public static final MeReplyReq$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        MeReplyReq$$serializer meReplyReq$$serializer = new MeReplyReq$$serializer();
        INSTANCE = meReplyReq$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.request.MeReplyReq", meReplyReq$$serializer, 10);
        pluginGeneratedSerialDescriptor.addElement("content", true);
        pluginGeneratedSerialDescriptor.addElement("type", true);
        pluginGeneratedSerialDescriptor.addElement("userId", true);
        pluginGeneratedSerialDescriptor.addElement("fileId", true);
        pluginGeneratedSerialDescriptor.addElement(NotificationCompat.CATEGORY_SYSTEM, true);
        pluginGeneratedSerialDescriptor.addElement("name", true);
        pluginGeneratedSerialDescriptor.addElement(AliyunLogCommon.TERMINAL_TYPE, true);
        pluginGeneratedSerialDescriptor.addElement("userName", true);
        pluginGeneratedSerialDescriptor.addElement("userPhone", true);
        pluginGeneratedSerialDescriptor.addElement(SocialConstants.PARAM_SOURCE, true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private MeReplyReq$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        return new KSerializer[]{stringSerializer, intSerializer, intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), stringSerializer, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), intSerializer};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public MeReplyReq deserialize(@NotNull Decoder decoder) {
        int iDecodeIntElement;
        Object objDecodeNullableSerializableElement;
        Object obj;
        Object objDecodeNullableSerializableElement2;
        Object objDecodeNullableSerializableElement3;
        Object objDecodeNullableSerializableElement4;
        int i2;
        int i3;
        int i4;
        String str;
        String str2;
        char c3;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i5 = 9;
        int i6 = 7;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            String strDecodeStringElement = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 0);
            int iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 1);
            int iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 2);
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, stringSerializer, null);
            String strDecodeStringElement2 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 4);
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, stringSerializer, null);
            objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, stringSerializer, null);
            objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, stringSerializer, null);
            Object objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, stringSerializer, null);
            iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 9);
            str2 = strDecodeStringElement2;
            str = strDecodeStringElement;
            obj = objDecodeNullableSerializableElement5;
            i4 = 1023;
            i2 = iDecodeIntElement3;
            i3 = iDecodeIntElement2;
        } else {
            boolean z2 = true;
            int iDecodeIntElement4 = 0;
            int iDecodeIntElement5 = 0;
            int i7 = 0;
            Object objDecodeNullableSerializableElement6 = null;
            Object objDecodeNullableSerializableElement7 = null;
            Object objDecodeNullableSerializableElement8 = null;
            Object objDecodeNullableSerializableElement9 = null;
            String strDecodeStringElement3 = null;
            String strDecodeStringElement4 = null;
            Object objDecodeNullableSerializableElement10 = null;
            int iDecodeIntElement6 = 0;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                        i5 = 9;
                    case 0:
                        strDecodeStringElement3 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 0);
                        i7 |= 1;
                        i5 = 9;
                        i6 = 7;
                    case 1:
                        i7 |= 2;
                        iDecodeIntElement5 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 1);
                        i5 = 9;
                        i6 = 7;
                    case 2:
                        iDecodeIntElement6 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 2);
                        i7 |= 4;
                        i5 = 9;
                        i6 = 7;
                    case 3:
                        objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, StringSerializer.INSTANCE, objDecodeNullableSerializableElement10);
                        i7 |= 8;
                        i5 = 9;
                        i6 = 7;
                    case 4:
                        c3 = 5;
                        strDecodeStringElement4 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 4);
                        i7 |= 16;
                        i5 = 9;
                    case 5:
                        c3 = 5;
                        objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, StringSerializer.INSTANCE, objDecodeNullableSerializableElement9);
                        i7 |= 32;
                        i5 = 9;
                    case 6:
                        objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, StringSerializer.INSTANCE, objDecodeNullableSerializableElement8);
                        i7 |= 64;
                    case 7:
                        objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, i6, StringSerializer.INSTANCE, objDecodeNullableSerializableElement6);
                        i7 |= 128;
                    case 8:
                        objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, StringSerializer.INSTANCE, objDecodeNullableSerializableElement7);
                        i7 |= 256;
                    case 9:
                        iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, i5);
                        i7 |= 512;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            iDecodeIntElement = iDecodeIntElement4;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement6;
            obj = objDecodeNullableSerializableElement7;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement8;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement9;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement10;
            i2 = iDecodeIntElement6;
            i3 = iDecodeIntElement5;
            i4 = i7;
            str = strDecodeStringElement3;
            str2 = strDecodeStringElement4;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new MeReplyReq(i4, str, i3, i2, (String) objDecodeNullableSerializableElement4, str2, (String) objDecodeNullableSerializableElement3, (String) objDecodeNullableSerializableElement2, (String) objDecodeNullableSerializableElement, (String) obj, iDecodeIntElement, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull MeReplyReq value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        MeReplyReq.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

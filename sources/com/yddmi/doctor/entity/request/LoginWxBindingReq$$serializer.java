package com.yddmi.doctor.entity.request;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/request/LoginWxBindingReq.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/request/LoginWxBindingReq;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class LoginWxBindingReq$$serializer implements GeneratedSerializer<LoginWxBindingReq> {

    @NotNull
    public static final LoginWxBindingReq$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        LoginWxBindingReq$$serializer loginWxBindingReq$$serializer = new LoginWxBindingReq$$serializer();
        INSTANCE = loginWxBindingReq$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.request.LoginWxBindingReq", loginWxBindingReq$$serializer, 6);
        pluginGeneratedSerialDescriptor.addElement(ConstantsAPI.Token.WX_TOKEN_PLATFORMID_KEY, false);
        pluginGeneratedSerialDescriptor.addElement("personId", false);
        pluginGeneratedSerialDescriptor.addElement("userId", false);
        pluginGeneratedSerialDescriptor.addElement("platformType", true);
        pluginGeneratedSerialDescriptor.addElement("wxCode", true);
        pluginGeneratedSerialDescriptor.addElement("wxToken", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private LoginWxBindingReq$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        return new KSerializer[]{stringSerializer, stringSerializer, intSerializer, intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), stringSerializer};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public LoginWxBindingReq deserialize(@NotNull Decoder decoder) {
        int i2;
        String strDecodeStringElement;
        String strDecodeStringElement2;
        Object objDecodeNullableSerializableElement;
        String strDecodeStringElement3;
        int i3;
        int i4;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            String strDecodeStringElement4 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 0);
            String strDecodeStringElement5 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 1);
            int iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 2);
            int iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
            objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, StringSerializer.INSTANCE, null);
            strDecodeStringElement = strDecodeStringElement4;
            strDecodeStringElement3 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 5);
            i3 = iDecodeIntElement2;
            i4 = iDecodeIntElement;
            strDecodeStringElement2 = strDecodeStringElement5;
            i2 = 63;
        } else {
            boolean z2 = true;
            int iDecodeIntElement3 = 0;
            i2 = 0;
            strDecodeStringElement = null;
            strDecodeStringElement2 = null;
            Object objDecodeNullableSerializableElement2 = null;
            String strDecodeStringElement6 = null;
            int iDecodeIntElement4 = 0;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                        continue;
                    case 0:
                        strDecodeStringElement = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 0);
                        i2 |= 1;
                        continue;
                    case 1:
                        strDecodeStringElement2 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 1);
                        i2 |= 2;
                        break;
                    case 2:
                        iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 2);
                        i2 |= 4;
                        break;
                    case 3:
                        iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
                        i2 |= 8;
                        break;
                    case 4:
                        objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, StringSerializer.INSTANCE, objDecodeNullableSerializableElement2);
                        i2 |= 16;
                        break;
                    case 5:
                        strDecodeStringElement6 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 5);
                        i2 |= 32;
                        break;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement2;
            strDecodeStringElement3 = strDecodeStringElement6;
            i3 = iDecodeIntElement3;
            i4 = iDecodeIntElement4;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new LoginWxBindingReq(i2, strDecodeStringElement, strDecodeStringElement2, i4, i3, (String) objDecodeNullableSerializableElement, strDecodeStringElement3, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull LoginWxBindingReq value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        LoginWxBindingReq.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

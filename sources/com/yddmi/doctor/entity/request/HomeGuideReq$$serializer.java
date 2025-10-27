package com.yddmi.doctor.entity.request;

import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.psychiatrygarden.utils.Constants;
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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/request/HomeGuideReq.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/request/HomeGuideReq;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class HomeGuideReq$$serializer implements GeneratedSerializer<HomeGuideReq> {

    @NotNull
    public static final HomeGuideReq$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        HomeGuideReq$$serializer homeGuideReq$$serializer = new HomeGuideReq$$serializer();
        INSTANCE = homeGuideReq$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.request.HomeGuideReq", homeGuideReq$$serializer, 7);
        pluginGeneratedSerialDescriptor.addElement("type", false);
        pluginGeneratedSerialDescriptor.addElement("title", true);
        pluginGeneratedSerialDescriptor.addElement(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, true);
        pluginGeneratedSerialDescriptor.addElement("categoryId", true);
        pluginGeneratedSerialDescriptor.addElement("pageNum", true);
        pluginGeneratedSerialDescriptor.addElement(ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, true);
        pluginGeneratedSerialDescriptor.addElement("labels", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private HomeGuideReq$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        return new KSerializer[]{intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, intSerializer, intSerializer, BuiltinSerializersKt.getNullable(new ArrayListSerializer(stringSerializer))};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public HomeGuideReq deserialize(@NotNull Decoder decoder) {
        Object objDecodeNullableSerializableElement;
        int i2;
        int i3;
        Object objDecodeNullableSerializableElement2;
        Object objDecodeNullableSerializableElement3;
        int i4;
        int i5;
        int i6;
        char c3;
        char c4;
        boolean z2;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i7 = 6;
        char c5 = 2;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            int iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 0);
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, stringSerializer, null);
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, stringSerializer, null);
            int iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
            int iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 4);
            int iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 5);
            objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, new ArrayListSerializer(stringSerializer), null);
            i6 = iDecodeIntElement;
            i2 = iDecodeIntElement4;
            i4 = iDecodeIntElement2;
            i5 = iDecodeIntElement3;
            i3 = 127;
        } else {
            boolean z3 = true;
            int iDecodeIntElement5 = 0;
            int iDecodeIntElement6 = 0;
            int iDecodeIntElement7 = 0;
            int i8 = 0;
            Object objDecodeNullableSerializableElement4 = null;
            Object objDecodeNullableSerializableElement5 = null;
            Object objDecodeNullableSerializableElement6 = null;
            int iDecodeIntElement8 = 0;
            while (z3) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        c3 = c5;
                        z3 = false;
                        c5 = c3;
                    case 0:
                        c4 = c5;
                        z2 = true;
                        iDecodeIntElement5 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 0);
                        i8 |= 1;
                        c5 = c4;
                        i7 = 6;
                    case 1:
                        c4 = c5;
                        z2 = true;
                        objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, StringSerializer.INSTANCE, objDecodeNullableSerializableElement4);
                        i8 |= 2;
                        c5 = c4;
                        i7 = 6;
                    case 2:
                        c3 = 2;
                        objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, StringSerializer.INSTANCE, objDecodeNullableSerializableElement5);
                        i8 |= 4;
                        c5 = c3;
                    case 3:
                        iDecodeIntElement6 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
                        i8 |= 8;
                        c5 = 2;
                    case 4:
                        iDecodeIntElement7 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 4);
                        i8 |= 16;
                        c5 = 2;
                    case 5:
                        iDecodeIntElement8 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 5);
                        i8 |= 32;
                        c5 = 2;
                    case 6:
                        objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, i7, new ArrayListSerializer(StringSerializer.INSTANCE), objDecodeNullableSerializableElement6);
                        i8 |= 64;
                        c5 = 2;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement6;
            i2 = iDecodeIntElement8;
            i3 = i8;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement4;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement5;
            i4 = iDecodeIntElement6;
            i5 = iDecodeIntElement7;
            i6 = iDecodeIntElement5;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new HomeGuideReq(i3, i6, (String) objDecodeNullableSerializableElement2, (String) objDecodeNullableSerializableElement3, i4, i5, i2, (List) objDecodeNullableSerializableElement, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull HomeGuideReq value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        HomeGuideReq.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

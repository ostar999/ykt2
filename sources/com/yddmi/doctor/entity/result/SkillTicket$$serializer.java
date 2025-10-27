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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/SkillTicket.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/SkillTicket;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class SkillTicket$$serializer implements GeneratedSerializer<SkillTicket> {

    @NotNull
    public static final SkillTicket$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        SkillTicket$$serializer skillTicket$$serializer = new SkillTicket$$serializer();
        INSTANCE = skillTicket$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.SkillTicket", skillTicket$$serializer, 11);
        pluginGeneratedSerialDescriptor.addElement("canUse", true);
        pluginGeneratedSerialDescriptor.addElement("claimTime", true);
        pluginGeneratedSerialDescriptor.addElement("couponAmount", true);
        pluginGeneratedSerialDescriptor.addElement("couponId", true);
        pluginGeneratedSerialDescriptor.addElement("couponRecord", true);
        pluginGeneratedSerialDescriptor.addElement("couponRecordId", false);
        pluginGeneratedSerialDescriptor.addElement("description", false);
        pluginGeneratedSerialDescriptor.addElement("enableTime", false);
        pluginGeneratedSerialDescriptor.addElement("type", true);
        pluginGeneratedSerialDescriptor.addElement("typeName", true);
        pluginGeneratedSerialDescriptor.addElement("usableRange", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private SkillTicket$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        return new KSerializer[]{intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), stringSerializer, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), intSerializer};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SkillTicket deserialize(@NotNull Decoder decoder) {
        String strDecodeStringElement;
        Object objDecodeNullableSerializableElement;
        int i2;
        int iDecodeIntElement;
        int i3;
        int i4;
        Object obj;
        Object objDecodeNullableSerializableElement2;
        Object objDecodeNullableSerializableElement3;
        Object objDecodeNullableSerializableElement4;
        Object objDecodeNullableSerializableElement5;
        Object objDecodeNullableSerializableElement6;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i5 = 10;
        int i6 = 9;
        int i7 = 8;
        int i8 = 0;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            int iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 0);
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, stringSerializer, null);
            objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, stringSerializer, null);
            String strDecodeStringElement2 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 3);
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, stringSerializer, null);
            Object objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, stringSerializer, null);
            objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, stringSerializer, null);
            objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, stringSerializer, null);
            int iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 8);
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, stringSerializer, null);
            iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 10);
            strDecodeStringElement = strDecodeStringElement2;
            i3 = iDecodeIntElement3;
            i4 = iDecodeIntElement2;
            obj = objDecodeNullableSerializableElement7;
            i2 = 2047;
        } else {
            boolean z2 = true;
            int iDecodeIntElement4 = 0;
            int iDecodeIntElement5 = 0;
            Object objDecodeNullableSerializableElement8 = null;
            Object objDecodeNullableSerializableElement9 = null;
            Object objDecodeNullableSerializableElement10 = null;
            Object objDecodeNullableSerializableElement11 = null;
            Object objDecodeNullableSerializableElement12 = null;
            Object objDecodeNullableSerializableElement13 = null;
            Object objDecodeNullableSerializableElement14 = null;
            strDecodeStringElement = null;
            int iDecodeIntElement6 = 0;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                        i5 = 10;
                        i6 = 9;
                    case 0:
                        iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 0);
                        i8 |= 1;
                        i5 = 10;
                        i6 = 9;
                        i7 = 8;
                    case 1:
                        i8 |= 2;
                        objDecodeNullableSerializableElement14 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, StringSerializer.INSTANCE, objDecodeNullableSerializableElement14);
                        i5 = 10;
                        i6 = 9;
                        i7 = 8;
                    case 2:
                        objDecodeNullableSerializableElement13 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, StringSerializer.INSTANCE, objDecodeNullableSerializableElement13);
                        i8 |= 4;
                        i5 = 10;
                        i6 = 9;
                    case 3:
                        strDecodeStringElement = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 3);
                        i8 |= 8;
                        i5 = 10;
                    case 4:
                        objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, StringSerializer.INSTANCE, objDecodeNullableSerializableElement12);
                        i8 |= 16;
                        i5 = 10;
                    case 5:
                        objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, StringSerializer.INSTANCE, objDecodeNullableSerializableElement10);
                        i8 |= 32;
                        i5 = 10;
                    case 6:
                        objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, StringSerializer.INSTANCE, objDecodeNullableSerializableElement11);
                        i8 |= 64;
                        i5 = 10;
                    case 7:
                        objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, StringSerializer.INSTANCE, objDecodeNullableSerializableElement9);
                        i8 |= 128;
                        i5 = 10;
                    case 8:
                        iDecodeIntElement5 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, i7);
                        i8 |= 256;
                    case 9:
                        objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, i6, StringSerializer.INSTANCE, objDecodeNullableSerializableElement8);
                        i8 |= 512;
                    case 10:
                        iDecodeIntElement6 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, i5);
                        i8 |= 1024;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            Object obj2 = objDecodeNullableSerializableElement14;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement9;
            i2 = i8;
            iDecodeIntElement = iDecodeIntElement6;
            i3 = iDecodeIntElement5;
            Object obj3 = objDecodeNullableSerializableElement10;
            i4 = iDecodeIntElement4;
            obj = obj3;
            Object obj4 = objDecodeNullableSerializableElement13;
            objDecodeNullableSerializableElement2 = obj2;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement8;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement11;
            objDecodeNullableSerializableElement5 = objDecodeNullableSerializableElement12;
            objDecodeNullableSerializableElement6 = obj4;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new SkillTicket(i2, i4, (String) objDecodeNullableSerializableElement2, (String) objDecodeNullableSerializableElement6, strDecodeStringElement, (String) objDecodeNullableSerializableElement5, (String) obj, (String) objDecodeNullableSerializableElement4, (String) objDecodeNullableSerializableElement, i3, (String) objDecodeNullableSerializableElement3, iDecodeIntElement, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull SkillTicket value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        SkillTicket.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

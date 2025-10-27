package com.yddmi.doctor.entity.result;

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
import kotlinx.serialization.internal.BooleanSerializer;
import kotlinx.serialization.internal.FloatSerializer;
import kotlinx.serialization.internal.GeneratedSerializer;
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginGeneratedSerialDescriptor;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/AtlasRow.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/AtlasRow;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class AtlasRow$$serializer implements GeneratedSerializer<AtlasRow> {

    @NotNull
    public static final AtlasRow$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        AtlasRow$$serializer atlasRow$$serializer = new AtlasRow$$serializer();
        INSTANCE = atlasRow$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.AtlasRow", atlasRow$$serializer, 11);
        pluginGeneratedSerialDescriptor.addElement("bearing", true);
        pluginGeneratedSerialDescriptor.addElement("description", true);
        pluginGeneratedSerialDescriptor.addElement("id", true);
        pluginGeneratedSerialDescriptor.addElement("isCmmon", true);
        pluginGeneratedSerialDescriptor.addElement("isDelete", true);
        pluginGeneratedSerialDescriptor.addElement("name", true);
        pluginGeneratedSerialDescriptor.addElement("version", true);
        pluginGeneratedSerialDescriptor.addElement("isMatche", true);
        pluginGeneratedSerialDescriptor.addElement("extentName", true);
        pluginGeneratedSerialDescriptor.addElement("diseaseName", true);
        pluginGeneratedSerialDescriptor.addElement("extentVos", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private AtlasRow$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        return new KSerializer[]{BuiltinSerializersKt.getNullable(FloatSerializer.INSTANCE), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), intSerializer, intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BooleanSerializer.INSTANCE, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(new ArrayListSerializer(BuiltinSerializersKt.getNullable(INSTANCE)))};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public AtlasRow deserialize(@NotNull Decoder decoder) {
        int iDecodeIntElement;
        Object objDecodeNullableSerializableElement;
        Object objDecodeNullableSerializableElement2;
        Object objDecodeNullableSerializableElement3;
        int i2;
        int i3;
        boolean z2;
        Object objDecodeNullableSerializableElement4;
        Object objDecodeNullableSerializableElement5;
        Object objDecodeNullableSerializableElement6;
        Object objDecodeNullableSerializableElement7;
        Object objDecodeNullableSerializableElement8;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i4 = 10;
        int i5 = 9;
        int i6 = 0;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, FloatSerializer.INSTANCE, null);
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, stringSerializer, null);
            IntSerializer intSerializer = IntSerializer.INSTANCE;
            objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, intSerializer, null);
            int iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
            int iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 4);
            objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, stringSerializer, null);
            Object objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, intSerializer, null);
            boolean zDecodeBooleanElement = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, 7);
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, stringSerializer, null);
            objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, stringSerializer, null);
            objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 10, new ArrayListSerializer(BuiltinSerializersKt.getNullable(INSTANCE)), null);
            z2 = zDecodeBooleanElement;
            iDecodeIntElement = iDecodeIntElement3;
            i3 = iDecodeIntElement2;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement9;
            i2 = 2047;
        } else {
            boolean z3 = true;
            boolean zDecodeBooleanElement2 = false;
            iDecodeIntElement = 0;
            Object objDecodeNullableSerializableElement10 = null;
            Object objDecodeNullableSerializableElement11 = null;
            objDecodeNullableSerializableElement = null;
            Object objDecodeNullableSerializableElement12 = null;
            Object objDecodeNullableSerializableElement13 = null;
            Object objDecodeNullableSerializableElement14 = null;
            Object objDecodeNullableSerializableElement15 = null;
            Object objDecodeNullableSerializableElement16 = null;
            int iDecodeIntElement4 = 0;
            while (z3) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z3 = false;
                        i5 = 9;
                    case 0:
                        objDecodeNullableSerializableElement15 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, FloatSerializer.INSTANCE, objDecodeNullableSerializableElement15);
                        i6 |= 1;
                        i4 = 10;
                        i5 = 9;
                    case 1:
                        objDecodeNullableSerializableElement16 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, StringSerializer.INSTANCE, objDecodeNullableSerializableElement16);
                        i6 |= 2;
                        i4 = 10;
                        i5 = 9;
                    case 2:
                        objDecodeNullableSerializableElement14 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, IntSerializer.INSTANCE, objDecodeNullableSerializableElement14);
                        i6 |= 4;
                        i4 = 10;
                        i5 = 9;
                    case 3:
                        i6 |= 8;
                        iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
                        i4 = 10;
                        i5 = 9;
                    case 4:
                        iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 4);
                        i6 |= 16;
                        i4 = 10;
                        i5 = 9;
                    case 5:
                        objDecodeNullableSerializableElement13 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, StringSerializer.INSTANCE, objDecodeNullableSerializableElement13);
                        i6 |= 32;
                        i4 = 10;
                        i5 = 9;
                    case 6:
                        objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, IntSerializer.INSTANCE, objDecodeNullableSerializableElement);
                        i6 |= 64;
                        i4 = 10;
                        i5 = 9;
                    case 7:
                        zDecodeBooleanElement2 = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, 7);
                        i6 |= 128;
                    case 8:
                        objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, StringSerializer.INSTANCE, objDecodeNullableSerializableElement12);
                        i6 |= 256;
                    case 9:
                        objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, i5, StringSerializer.INSTANCE, objDecodeNullableSerializableElement11);
                        i6 |= 512;
                    case 10:
                        objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, i4, new ArrayListSerializer(BuiltinSerializersKt.getNullable(INSTANCE)), objDecodeNullableSerializableElement10);
                        i6 |= 1024;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement10;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement12;
            i2 = i6;
            i3 = iDecodeIntElement4;
            z2 = zDecodeBooleanElement2;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement15;
            Object obj = objDecodeNullableSerializableElement14;
            objDecodeNullableSerializableElement5 = objDecodeNullableSerializableElement16;
            objDecodeNullableSerializableElement6 = objDecodeNullableSerializableElement11;
            objDecodeNullableSerializableElement7 = objDecodeNullableSerializableElement13;
            objDecodeNullableSerializableElement8 = obj;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new AtlasRow(i2, (Float) objDecodeNullableSerializableElement4, (String) objDecodeNullableSerializableElement5, (Integer) objDecodeNullableSerializableElement8, i3, iDecodeIntElement, (String) objDecodeNullableSerializableElement7, (Integer) objDecodeNullableSerializableElement, z2, (String) objDecodeNullableSerializableElement3, (String) objDecodeNullableSerializableElement6, (List) objDecodeNullableSerializableElement2, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull AtlasRow value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        AtlasRow.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

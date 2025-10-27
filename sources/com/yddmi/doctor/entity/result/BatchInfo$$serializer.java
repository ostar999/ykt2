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
import kotlinx.serialization.internal.GeneratedSerializer;
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginGeneratedSerialDescriptor;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/BatchInfo.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/BatchInfo;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class BatchInfo$$serializer implements GeneratedSerializer<BatchInfo> {

    @NotNull
    public static final BatchInfo$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        BatchInfo$$serializer batchInfo$$serializer = new BatchInfo$$serializer();
        INSTANCE = batchInfo$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.BatchInfo", batchInfo$$serializer, 6);
        pluginGeneratedSerialDescriptor.addElement("costPrice", true);
        pluginGeneratedSerialDescriptor.addElement("marketPrice", true);
        pluginGeneratedSerialDescriptor.addElement("preferentialPrice", true);
        pluginGeneratedSerialDescriptor.addElement("productIds", true);
        pluginGeneratedSerialDescriptor.addElement("validEndDate", true);
        pluginGeneratedSerialDescriptor.addElement("validStartDate", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private BatchInfo$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        return new KSerializer[]{stringSerializer, stringSerializer, stringSerializer, BuiltinSerializersKt.getNullable(new ArrayListSerializer(IntSerializer.INSTANCE)), stringSerializer, stringSerializer};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public BatchInfo deserialize(@NotNull Decoder decoder) {
        Object objDecodeNullableSerializableElement;
        String str;
        String strDecodeStringElement;
        String str2;
        String str3;
        int i2;
        String str4;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i3 = 5;
        String strDecodeStringElement2 = null;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            String strDecodeStringElement3 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 0);
            String strDecodeStringElement4 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 1);
            String strDecodeStringElement5 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 2);
            objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, new ArrayListSerializer(IntSerializer.INSTANCE), null);
            String strDecodeStringElement6 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 4);
            str4 = strDecodeStringElement3;
            strDecodeStringElement = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 5);
            str = strDecodeStringElement6;
            str3 = strDecodeStringElement5;
            str2 = strDecodeStringElement4;
            i2 = 63;
        } else {
            boolean z2 = true;
            int i4 = 0;
            String strDecodeStringElement7 = null;
            String strDecodeStringElement8 = null;
            Object objDecodeNullableSerializableElement2 = null;
            String strDecodeStringElement9 = null;
            String strDecodeStringElement10 = null;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                        i3 = 5;
                    case 0:
                        strDecodeStringElement2 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 0);
                        i4 |= 1;
                        i3 = 5;
                    case 1:
                        strDecodeStringElement7 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 1);
                        i4 |= 2;
                        i3 = 5;
                    case 2:
                        strDecodeStringElement8 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 2);
                        i4 |= 4;
                        i3 = 5;
                    case 3:
                        objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, new ArrayListSerializer(IntSerializer.INSTANCE), objDecodeNullableSerializableElement2);
                        i4 |= 8;
                        i3 = 5;
                    case 4:
                        strDecodeStringElement9 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, 4);
                        i4 |= 16;
                    case 5:
                        strDecodeStringElement10 = compositeDecoderBeginStructure.decodeStringElement(descriptor2, i3);
                        i4 |= 32;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement2;
            str = strDecodeStringElement9;
            strDecodeStringElement = strDecodeStringElement10;
            str2 = strDecodeStringElement7;
            str3 = strDecodeStringElement8;
            i2 = i4;
            str4 = strDecodeStringElement2;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new BatchInfo(i2, str4, str2, str3, (List) objDecodeNullableSerializableElement, str, strDecodeStringElement, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull BatchInfo value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        BatchInfo.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

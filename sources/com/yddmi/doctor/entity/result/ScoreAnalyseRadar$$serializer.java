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
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/ScoreAnalyseRadar.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/ScoreAnalyseRadar;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class ScoreAnalyseRadar$$serializer implements GeneratedSerializer<ScoreAnalyseRadar> {

    @NotNull
    public static final ScoreAnalyseRadar$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        ScoreAnalyseRadar$$serializer scoreAnalyseRadar$$serializer = new ScoreAnalyseRadar$$serializer();
        INSTANCE = scoreAnalyseRadar$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.ScoreAnalyseRadar", scoreAnalyseRadar$$serializer, 8);
        pluginGeneratedSerialDescriptor.addElement("analyseId", true);
        pluginGeneratedSerialDescriptor.addElement("diagnose", true);
        pluginGeneratedSerialDescriptor.addElement("logic", true);
        pluginGeneratedSerialDescriptor.addElement("overall", true);
        pluginGeneratedSerialDescriptor.addElement("precise", true);
        pluginGeneratedSerialDescriptor.addElement("risk", true);
        pluginGeneratedSerialDescriptor.addElement("trainId", true);
        pluginGeneratedSerialDescriptor.addElement("treatment", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private ScoreAnalyseRadar$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        return new KSerializer[]{intSerializer, intSerializer, intSerializer, intSerializer, intSerializer, intSerializer, intSerializer, intSerializer};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public ScoreAnalyseRadar deserialize(@NotNull Decoder decoder) {
        int iDecodeIntElement;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i10 = 0;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            int iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 0);
            int iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 1);
            int iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 2);
            int iDecodeIntElement5 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
            int iDecodeIntElement6 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 4);
            int iDecodeIntElement7 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 5);
            int iDecodeIntElement8 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 6);
            i3 = iDecodeIntElement2;
            iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 7);
            i2 = iDecodeIntElement8;
            i5 = iDecodeIntElement7;
            i7 = iDecodeIntElement5;
            i9 = iDecodeIntElement6;
            i8 = iDecodeIntElement4;
            i6 = iDecodeIntElement3;
            i4 = 255;
        } else {
            boolean z2 = true;
            int iDecodeIntElement9 = 0;
            int iDecodeIntElement10 = 0;
            int iDecodeIntElement11 = 0;
            int iDecodeIntElement12 = 0;
            int iDecodeIntElement13 = 0;
            int iDecodeIntElement14 = 0;
            int iDecodeIntElement15 = 0;
            int iDecodeIntElement16 = 0;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                        break;
                    case 0:
                        i10 |= 1;
                        iDecodeIntElement9 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 0);
                        continue;
                    case 1:
                        iDecodeIntElement16 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 1);
                        i10 |= 2;
                        continue;
                    case 2:
                        iDecodeIntElement15 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 2);
                        i10 |= 4;
                        break;
                    case 3:
                        iDecodeIntElement13 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
                        i10 |= 8;
                        break;
                    case 4:
                        iDecodeIntElement14 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 4);
                        i10 |= 16;
                        break;
                    case 5:
                        iDecodeIntElement12 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 5);
                        i10 |= 32;
                        break;
                    case 6:
                        iDecodeIntElement11 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 6);
                        i10 |= 64;
                        break;
                    case 7:
                        iDecodeIntElement10 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 7);
                        i10 |= 128;
                        break;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            iDecodeIntElement = iDecodeIntElement10;
            i2 = iDecodeIntElement11;
            i3 = iDecodeIntElement9;
            i4 = i10;
            int i11 = iDecodeIntElement16;
            i5 = iDecodeIntElement12;
            i6 = i11;
            int i12 = iDecodeIntElement14;
            i7 = iDecodeIntElement13;
            i8 = iDecodeIntElement15;
            i9 = i12;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new ScoreAnalyseRadar(i4, i3, i6, i8, i7, i9, i5, i2, iDecodeIntElement, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull ScoreAnalyseRadar value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        ScoreAnalyseRadar.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

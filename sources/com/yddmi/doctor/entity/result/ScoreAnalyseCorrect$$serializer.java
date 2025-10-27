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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/ScoreAnalyseCorrect.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/ScoreAnalyseCorrect;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class ScoreAnalyseCorrect$$serializer implements GeneratedSerializer<ScoreAnalyseCorrect> {

    @NotNull
    public static final ScoreAnalyseCorrect$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        ScoreAnalyseCorrect$$serializer scoreAnalyseCorrect$$serializer = new ScoreAnalyseCorrect$$serializer();
        INSTANCE = scoreAnalyseCorrect$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.ScoreAnalyseCorrect", scoreAnalyseCorrect$$serializer, 13);
        pluginGeneratedSerialDescriptor.addElement("askAvg", true);
        pluginGeneratedSerialDescriptor.addElement("askRatio", true);
        pluginGeneratedSerialDescriptor.addElement("assistCheckAvg", true);
        pluginGeneratedSerialDescriptor.addElement("assistCheckRatio", true);
        pluginGeneratedSerialDescriptor.addElement("bodyCheckAvg", true);
        pluginGeneratedSerialDescriptor.addElement("bodyCheckRatio", true);
        pluginGeneratedSerialDescriptor.addElement("diagnoseAvg", true);
        pluginGeneratedSerialDescriptor.addElement("diagnoseRatio", true);
        pluginGeneratedSerialDescriptor.addElement("ratioId", true);
        pluginGeneratedSerialDescriptor.addElement("trainId", true);
        pluginGeneratedSerialDescriptor.addElement("treatmentAvg", true);
        pluginGeneratedSerialDescriptor.addElement("treatmentRatio", true);
        pluginGeneratedSerialDescriptor.addElement("type", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private ScoreAnalyseCorrect$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        return new KSerializer[]{intSerializer, intSerializer, intSerializer, intSerializer, intSerializer, intSerializer, intSerializer, intSerializer, intSerializer, intSerializer, intSerializer, intSerializer, intSerializer};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public ScoreAnalyseCorrect deserialize(@NotNull Decoder decoder) {
        int iDecodeIntElement;
        int iDecodeIntElement2;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i14 = 0;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            int iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 0);
            int iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 1);
            int iDecodeIntElement5 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 2);
            int iDecodeIntElement6 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
            int iDecodeIntElement7 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 4);
            int iDecodeIntElement8 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 5);
            int iDecodeIntElement9 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 6);
            int iDecodeIntElement10 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 7);
            int iDecodeIntElement11 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 8);
            int iDecodeIntElement12 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 9);
            int iDecodeIntElement13 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 10);
            int iDecodeIntElement14 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 11);
            i3 = iDecodeIntElement3;
            iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 12);
            i2 = iDecodeIntElement14;
            i5 = iDecodeIntElement13;
            i7 = iDecodeIntElement12;
            i9 = iDecodeIntElement10;
            i11 = iDecodeIntElement9;
            iDecodeIntElement = iDecodeIntElement8;
            i10 = iDecodeIntElement6;
            i13 = iDecodeIntElement11;
            i12 = iDecodeIntElement7;
            i8 = iDecodeIntElement5;
            i6 = iDecodeIntElement4;
            i4 = 8191;
        } else {
            boolean z2 = true;
            int iDecodeIntElement15 = 0;
            int iDecodeIntElement16 = 0;
            int iDecodeIntElement17 = 0;
            int iDecodeIntElement18 = 0;
            int iDecodeIntElement19 = 0;
            int iDecodeIntElement20 = 0;
            int iDecodeIntElement21 = 0;
            iDecodeIntElement = 0;
            int iDecodeIntElement22 = 0;
            int iDecodeIntElement23 = 0;
            int iDecodeIntElement24 = 0;
            int iDecodeIntElement25 = 0;
            int iDecodeIntElement26 = 0;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                        break;
                    case 0:
                        i14 |= 1;
                        iDecodeIntElement15 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 0);
                        continue;
                    case 1:
                        iDecodeIntElement26 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 1);
                        i14 |= 2;
                        continue;
                    case 2:
                        iDecodeIntElement25 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 2);
                        i14 |= 4;
                        break;
                    case 3:
                        iDecodeIntElement22 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
                        i14 |= 8;
                        break;
                    case 4:
                        iDecodeIntElement24 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 4);
                        i14 |= 16;
                        break;
                    case 5:
                        iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 5);
                        i14 |= 32;
                        break;
                    case 6:
                        iDecodeIntElement21 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 6);
                        i14 |= 64;
                        break;
                    case 7:
                        iDecodeIntElement20 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 7);
                        i14 |= 128;
                        break;
                    case 8:
                        iDecodeIntElement23 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 8);
                        i14 |= 256;
                        break;
                    case 9:
                        iDecodeIntElement19 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 9);
                        i14 |= 512;
                        break;
                    case 10:
                        iDecodeIntElement18 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 10);
                        i14 |= 1024;
                        break;
                    case 11:
                        iDecodeIntElement17 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 11);
                        i14 |= 2048;
                        break;
                    case 12:
                        iDecodeIntElement16 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 12);
                        i14 |= 4096;
                        break;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            iDecodeIntElement2 = iDecodeIntElement16;
            i2 = iDecodeIntElement17;
            i3 = iDecodeIntElement15;
            i4 = i14;
            int i15 = iDecodeIntElement26;
            i5 = iDecodeIntElement18;
            i6 = i15;
            int i16 = iDecodeIntElement25;
            i7 = iDecodeIntElement19;
            i8 = i16;
            int i17 = iDecodeIntElement23;
            i9 = iDecodeIntElement20;
            i10 = iDecodeIntElement22;
            i11 = iDecodeIntElement21;
            i12 = iDecodeIntElement24;
            i13 = i17;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new ScoreAnalyseCorrect(i4, i3, i6, i8, i10, i12, iDecodeIntElement, i11, i9, i13, i7, i5, i2, iDecodeIntElement2, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull ScoreAnalyseCorrect value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        ScoreAnalyseCorrect.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

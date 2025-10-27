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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/ScoreAnalyseTrainInfo.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/ScoreAnalyseTrainInfo;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class ScoreAnalyseTrainInfo$$serializer implements GeneratedSerializer<ScoreAnalyseTrainInfo> {

    @NotNull
    public static final ScoreAnalyseTrainInfo$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        ScoreAnalyseTrainInfo$$serializer scoreAnalyseTrainInfo$$serializer = new ScoreAnalyseTrainInfo$$serializer();
        INSTANCE = scoreAnalyseTrainInfo$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.ScoreAnalyseTrainInfo", scoreAnalyseTrainInfo$$serializer, 10);
        pluginGeneratedSerialDescriptor.addElement("degree", true);
        pluginGeneratedSerialDescriptor.addElement("id", true);
        pluginGeneratedSerialDescriptor.addElement("isExam", true);
        pluginGeneratedSerialDescriptor.addElement("lastBeginTime", true);
        pluginGeneratedSerialDescriptor.addElement("lastStopTime", true);
        pluginGeneratedSerialDescriptor.addElement("patientId", true);
        pluginGeneratedSerialDescriptor.addElement("score", true);
        pluginGeneratedSerialDescriptor.addElement("status", true);
        pluginGeneratedSerialDescriptor.addElement("timeCost", true);
        pluginGeneratedSerialDescriptor.addElement("userId", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private ScoreAnalyseTrainInfo$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        return new KSerializer[]{BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(intSerializer)};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public ScoreAnalyseTrainInfo deserialize(@NotNull Decoder decoder) {
        Object objDecodeNullableSerializableElement;
        Object objDecodeNullableSerializableElement2;
        Object objDecodeNullableSerializableElement3;
        Object objDecodeNullableSerializableElement4;
        Object objDecodeNullableSerializableElement5;
        Object objDecodeNullableSerializableElement6;
        Object objDecodeNullableSerializableElement7;
        Object objDecodeNullableSerializableElement8;
        int i2;
        Object objDecodeNullableSerializableElement9;
        Object objDecodeNullableSerializableElement10;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i3 = 9;
        Object objDecodeNullableSerializableElement11 = null;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, stringSerializer, null);
            IntSerializer intSerializer = IntSerializer.INSTANCE;
            objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, intSerializer, null);
            Object objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, intSerializer, null);
            objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, stringSerializer, null);
            objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, stringSerializer, null);
            objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, intSerializer, null);
            objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, intSerializer, null);
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, intSerializer, null);
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, intSerializer, null);
            objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, intSerializer, null);
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement12;
            i2 = 1023;
        } else {
            boolean z2 = true;
            int i4 = 0;
            Object objDecodeNullableSerializableElement13 = null;
            Object objDecodeNullableSerializableElement14 = null;
            Object objDecodeNullableSerializableElement15 = null;
            Object objDecodeNullableSerializableElement16 = null;
            Object objDecodeNullableSerializableElement17 = null;
            Object objDecodeNullableSerializableElement18 = null;
            Object objDecodeNullableSerializableElement19 = null;
            Object objDecodeNullableSerializableElement20 = null;
            objDecodeNullableSerializableElement = null;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                        i3 = 9;
                    case 0:
                        objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, StringSerializer.INSTANCE, objDecodeNullableSerializableElement11);
                        i4 |= 1;
                        i3 = 9;
                    case 1:
                        objDecodeNullableSerializableElement20 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, IntSerializer.INSTANCE, objDecodeNullableSerializableElement20);
                        i4 |= 2;
                        i3 = 9;
                    case 2:
                        objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, IntSerializer.INSTANCE, objDecodeNullableSerializableElement);
                        i4 |= 4;
                        i3 = 9;
                    case 3:
                        objDecodeNullableSerializableElement19 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, StringSerializer.INSTANCE, objDecodeNullableSerializableElement19);
                        i4 |= 8;
                        i3 = 9;
                    case 4:
                        objDecodeNullableSerializableElement18 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, StringSerializer.INSTANCE, objDecodeNullableSerializableElement18);
                        i4 |= 16;
                        i3 = 9;
                    case 5:
                        objDecodeNullableSerializableElement17 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, IntSerializer.INSTANCE, objDecodeNullableSerializableElement17);
                        i4 |= 32;
                        i3 = 9;
                    case 6:
                        objDecodeNullableSerializableElement15 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, IntSerializer.INSTANCE, objDecodeNullableSerializableElement15);
                        i4 |= 64;
                        i3 = 9;
                    case 7:
                        objDecodeNullableSerializableElement16 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, IntSerializer.INSTANCE, objDecodeNullableSerializableElement16);
                        i4 |= 128;
                        i3 = 9;
                    case 8:
                        objDecodeNullableSerializableElement14 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, IntSerializer.INSTANCE, objDecodeNullableSerializableElement14);
                        i4 |= 256;
                    case 9:
                        objDecodeNullableSerializableElement13 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, i3, IntSerializer.INSTANCE, objDecodeNullableSerializableElement13);
                        i4 |= 512;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement13;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement14;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement15;
            objDecodeNullableSerializableElement5 = objDecodeNullableSerializableElement16;
            objDecodeNullableSerializableElement6 = objDecodeNullableSerializableElement17;
            objDecodeNullableSerializableElement7 = objDecodeNullableSerializableElement19;
            objDecodeNullableSerializableElement8 = objDecodeNullableSerializableElement11;
            i2 = i4;
            objDecodeNullableSerializableElement9 = objDecodeNullableSerializableElement18;
            objDecodeNullableSerializableElement10 = objDecodeNullableSerializableElement20;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new ScoreAnalyseTrainInfo(i2, (String) objDecodeNullableSerializableElement8, (Integer) objDecodeNullableSerializableElement10, (Integer) objDecodeNullableSerializableElement, (String) objDecodeNullableSerializableElement7, (String) objDecodeNullableSerializableElement9, (Integer) objDecodeNullableSerializableElement6, (Integer) objDecodeNullableSerializableElement4, (Integer) objDecodeNullableSerializableElement5, (Integer) objDecodeNullableSerializableElement3, (Integer) objDecodeNullableSerializableElement2, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull ScoreAnalyseTrainInfo value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        ScoreAnalyseTrainInfo.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

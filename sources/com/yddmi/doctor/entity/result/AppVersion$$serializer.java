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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/AppVersion.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/AppVersion;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class AppVersion$$serializer implements GeneratedSerializer<AppVersion> {

    @NotNull
    public static final AppVersion$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        AppVersion$$serializer appVersion$$serializer = new AppVersion$$serializer();
        INSTANCE = appVersion$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.AppVersion", appVersion$$serializer, 7);
        pluginGeneratedSerialDescriptor.addElement("externalVersion", true);
        pluginGeneratedSerialDescriptor.addElement("internalVersion", true);
        pluginGeneratedSerialDescriptor.addElement("versionIntroduce", true);
        pluginGeneratedSerialDescriptor.addElement("status", true);
        pluginGeneratedSerialDescriptor.addElement("versionUrl", true);
        pluginGeneratedSerialDescriptor.addElement("upgradeStatus", true);
        pluginGeneratedSerialDescriptor.addElement("auditStatus", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private AppVersion$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        return new KSerializer[]{BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, intSerializer};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public AppVersion deserialize(@NotNull Decoder decoder) {
        Object objDecodeNullableSerializableElement;
        int iDecodeIntElement;
        int i2;
        Object objDecodeNullableSerializableElement2;
        Object objDecodeNullableSerializableElement3;
        int iDecodeIntElement2;
        Object objDecodeNullableSerializableElement4;
        int i3;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i4 = 6;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            Object objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, stringSerializer, null);
            objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, stringSerializer, null);
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, stringSerializer, null);
            int iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
            objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, stringSerializer, null);
            int iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 5);
            iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 6);
            iDecodeIntElement = iDecodeIntElement4;
            i2 = 127;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement5;
            i3 = iDecodeIntElement3;
        } else {
            boolean z2 = true;
            int iDecodeIntElement5 = 0;
            int iDecodeIntElement6 = 0;
            int i5 = 0;
            objDecodeNullableSerializableElement = null;
            Object objDecodeNullableSerializableElement6 = null;
            Object objDecodeNullableSerializableElement7 = null;
            Object objDecodeNullableSerializableElement8 = null;
            iDecodeIntElement = 0;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        z2 = false;
                        i4 = 6;
                    case 0:
                        objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, StringSerializer.INSTANCE, objDecodeNullableSerializableElement);
                        i5 |= 1;
                        i4 = 6;
                    case 1:
                        objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, StringSerializer.INSTANCE, objDecodeNullableSerializableElement6);
                        i5 |= 2;
                        i4 = 6;
                    case 2:
                        objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, StringSerializer.INSTANCE, objDecodeNullableSerializableElement7);
                        i5 |= 4;
                    case 3:
                        iDecodeIntElement6 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 3);
                        i5 |= 8;
                    case 4:
                        objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, StringSerializer.INSTANCE, objDecodeNullableSerializableElement8);
                        i5 |= 16;
                    case 5:
                        iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 5);
                        i5 |= 32;
                    case 6:
                        iDecodeIntElement5 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, i4);
                        i5 |= 64;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            i2 = i5;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement6;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement7;
            int i6 = iDecodeIntElement6;
            iDecodeIntElement2 = iDecodeIntElement5;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement8;
            i3 = i6;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new AppVersion(i2, (String) objDecodeNullableSerializableElement, (String) objDecodeNullableSerializableElement2, (String) objDecodeNullableSerializableElement3, i3, (String) objDecodeNullableSerializableElement4, iDecodeIntElement, iDecodeIntElement2, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull AppVersion value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        AppVersion.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

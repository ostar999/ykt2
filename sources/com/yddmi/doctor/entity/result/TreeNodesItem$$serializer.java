package com.yddmi.doctor.entity.result;

import com.nirvana.tools.logger.cache.db.DBHelpTool;
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

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/TreeNodesItem.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/TreeNodesItem;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class TreeNodesItem$$serializer implements GeneratedSerializer<TreeNodesItem> {

    @NotNull
    public static final TreeNodesItem$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        TreeNodesItem$$serializer treeNodesItem$$serializer = new TreeNodesItem$$serializer();
        INSTANCE = treeNodesItem$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.TreeNodesItem", treeNodesItem$$serializer, 12);
        pluginGeneratedSerialDescriptor.addElement("children", true);
        pluginGeneratedSerialDescriptor.addElement("id", true);
        pluginGeneratedSerialDescriptor.addElement(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, true);
        pluginGeneratedSerialDescriptor.addElement("pid", true);
        pluginGeneratedSerialDescriptor.addElement("primaryCategory", true);
        pluginGeneratedSerialDescriptor.addElement("ratingCriteriaDetailId", true);
        pluginGeneratedSerialDescriptor.addElement("ratingCriteriaId", true);
        pluginGeneratedSerialDescriptor.addElement("ratingItem", true);
        pluginGeneratedSerialDescriptor.addElement("score", true);
        pluginGeneratedSerialDescriptor.addElement("secondaryCategory", true);
        pluginGeneratedSerialDescriptor.addElement("seq", true);
        pluginGeneratedSerialDescriptor.addElement("mShow", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private TreeNodesItem$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        return new KSerializer[]{BuiltinSerializersKt.getNullable(new ArrayListSerializer(INSTANCE)), BuiltinSerializersKt.getNullable(stringSerializer), IntSerializer.INSTANCE, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(FloatSerializer.INSTANCE), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BooleanSerializer.INSTANCE};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public TreeNodesItem deserialize(@NotNull Decoder decoder) {
        Object objDecodeNullableSerializableElement;
        Object objDecodeNullableSerializableElement2;
        Object objDecodeNullableSerializableElement3;
        Object objDecodeNullableSerializableElement4;
        int iDecodeIntElement;
        Object obj;
        boolean zDecodeBooleanElement;
        Object objDecodeNullableSerializableElement5;
        Object objDecodeNullableSerializableElement6;
        int i2;
        Object objDecodeNullableSerializableElement7;
        Object objDecodeNullableSerializableElement8;
        Object objDecodeNullableSerializableElement9;
        Object obj2;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        int i3 = 10;
        int i4 = 0;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, new ArrayListSerializer(INSTANCE), null);
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            Object objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, stringSerializer, null);
            int iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 2);
            Object objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, stringSerializer, null);
            objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, stringSerializer, null);
            Object objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, stringSerializer, null);
            objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, stringSerializer, null);
            Object objDecodeNullableSerializableElement13 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, stringSerializer, null);
            objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, FloatSerializer.INSTANCE, null);
            Object objDecodeNullableSerializableElement14 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, stringSerializer, null);
            objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 10, stringSerializer, null);
            zDecodeBooleanElement = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, 11);
            iDecodeIntElement = iDecodeIntElement2;
            obj = objDecodeNullableSerializableElement10;
            objDecodeNullableSerializableElement4 = objDecodeNullableSerializableElement11;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement12;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement13;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement14;
            i2 = 4095;
        } else {
            int i5 = 11;
            boolean z2 = true;
            boolean zDecodeBooleanElement2 = false;
            objDecodeNullableSerializableElement = null;
            Object objDecodeNullableSerializableElement15 = null;
            objDecodeNullableSerializableElement2 = null;
            objDecodeNullableSerializableElement3 = null;
            Object objDecodeNullableSerializableElement16 = null;
            Object objDecodeNullableSerializableElement17 = null;
            Object objDecodeNullableSerializableElement18 = null;
            objDecodeNullableSerializableElement4 = null;
            Object objDecodeNullableSerializableElement19 = null;
            Object objDecodeNullableSerializableElement20 = null;
            iDecodeIntElement = 0;
            while (z2) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        obj2 = objDecodeNullableSerializableElement20;
                        z2 = false;
                        zDecodeBooleanElement2 = zDecodeBooleanElement2;
                        objDecodeNullableSerializableElement20 = obj2;
                        i3 = 10;
                    case 0:
                        obj2 = objDecodeNullableSerializableElement20;
                        i4 |= 1;
                        objDecodeNullableSerializableElement19 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, new ArrayListSerializer(INSTANCE), objDecodeNullableSerializableElement19);
                        zDecodeBooleanElement2 = zDecodeBooleanElement2;
                        i5 = 11;
                        objDecodeNullableSerializableElement20 = obj2;
                        i3 = 10;
                    case 1:
                        i4 |= 2;
                        zDecodeBooleanElement2 = zDecodeBooleanElement2;
                        i3 = 10;
                        objDecodeNullableSerializableElement20 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, StringSerializer.INSTANCE, objDecodeNullableSerializableElement20);
                        i5 = 11;
                    case 2:
                        iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 2);
                        i4 |= 4;
                        i5 = 11;
                    case 3:
                        objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, StringSerializer.INSTANCE, objDecodeNullableSerializableElement4);
                        i4 |= 8;
                        i5 = 11;
                    case 4:
                        objDecodeNullableSerializableElement18 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 4, StringSerializer.INSTANCE, objDecodeNullableSerializableElement18);
                        i4 |= 16;
                        i5 = 11;
                    case 5:
                        objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, StringSerializer.INSTANCE, objDecodeNullableSerializableElement3);
                        i4 |= 32;
                        i5 = 11;
                    case 6:
                        objDecodeNullableSerializableElement17 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, StringSerializer.INSTANCE, objDecodeNullableSerializableElement17);
                        i4 |= 64;
                        i5 = 11;
                    case 7:
                        objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, StringSerializer.INSTANCE, objDecodeNullableSerializableElement2);
                        i4 |= 128;
                        i5 = 11;
                    case 8:
                        objDecodeNullableSerializableElement15 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, FloatSerializer.INSTANCE, objDecodeNullableSerializableElement15);
                        i4 |= 256;
                        i5 = 11;
                    case 9:
                        objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, StringSerializer.INSTANCE, objDecodeNullableSerializableElement);
                        i4 |= 512;
                        i5 = 11;
                    case 10:
                        objDecodeNullableSerializableElement16 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, i3, StringSerializer.INSTANCE, objDecodeNullableSerializableElement16);
                        i4 |= 1024;
                    case 11:
                        zDecodeBooleanElement2 = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, i5);
                        i4 |= 2048;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            obj = objDecodeNullableSerializableElement20;
            zDecodeBooleanElement = zDecodeBooleanElement2;
            objDecodeNullableSerializableElement5 = objDecodeNullableSerializableElement19;
            objDecodeNullableSerializableElement6 = objDecodeNullableSerializableElement16;
            i2 = i4;
            objDecodeNullableSerializableElement7 = objDecodeNullableSerializableElement15;
            objDecodeNullableSerializableElement8 = objDecodeNullableSerializableElement17;
            objDecodeNullableSerializableElement9 = objDecodeNullableSerializableElement18;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new TreeNodesItem(i2, (List) objDecodeNullableSerializableElement5, (String) obj, iDecodeIntElement, (String) objDecodeNullableSerializableElement4, (String) objDecodeNullableSerializableElement9, (String) objDecodeNullableSerializableElement3, (String) objDecodeNullableSerializableElement8, (String) objDecodeNullableSerializableElement2, (Float) objDecodeNullableSerializableElement7, (String) objDecodeNullableSerializableElement, (String) objDecodeNullableSerializableElement6, zDecodeBooleanElement, (SerializationConstructorMarker) null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull TreeNodesItem value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        TreeNodesItem.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

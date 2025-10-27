package com.yddmi.doctor.entity.result;

import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.psychiatrygarden.db.SQLHelper;
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
import kotlinx.serialization.internal.BooleanSerializer;
import kotlinx.serialization.internal.GeneratedSerializer;
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginGeneratedSerialDescriptor;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tHÖ\u0001¢\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VXÖ\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"com/yddmi/doctor/entity/result/InquiryCheckInfo.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/yddmi/doctor/entity/result/InquiryCheckInfo;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
/* loaded from: classes6.dex */
public final class InquiryCheckInfo$$serializer implements GeneratedSerializer<InquiryCheckInfo> {

    @NotNull
    public static final InquiryCheckInfo$$serializer INSTANCE;
    private static final /* synthetic */ PluginGeneratedSerialDescriptor descriptor;

    static {
        InquiryCheckInfo$$serializer inquiryCheckInfo$$serializer = new InquiryCheckInfo$$serializer();
        INSTANCE = inquiryCheckInfo$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.yddmi.doctor.entity.result.InquiryCheckInfo", inquiryCheckInfo$$serializer, 18);
        pluginGeneratedSerialDescriptor.addElement("id", true);
        pluginGeneratedSerialDescriptor.addElement(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_LABEL, true);
        pluginGeneratedSerialDescriptor.addElement("title", true);
        pluginGeneratedSerialDescriptor.addElement("course", true);
        pluginGeneratedSerialDescriptor.addElement(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, true);
        pluginGeneratedSerialDescriptor.addElement("pid", true);
        pluginGeneratedSerialDescriptor.addElement("result", true);
        pluginGeneratedSerialDescriptor.addElement("children", true);
        pluginGeneratedSerialDescriptor.addElement("name", true);
        pluginGeneratedSerialDescriptor.addElement("way", true);
        pluginGeneratedSerialDescriptor.addElement("description", true);
        pluginGeneratedSerialDescriptor.addElement("parentId", true);
        pluginGeneratedSerialDescriptor.addElement(SQLHelper.SELECTED, true);
        pluginGeneratedSerialDescriptor.addElement("picUrl", true);
        pluginGeneratedSerialDescriptor.addElement("categoryId", true);
        pluginGeneratedSerialDescriptor.addElement("childNode", true);
        pluginGeneratedSerialDescriptor.addElement("isCategory", true);
        pluginGeneratedSerialDescriptor.addElement("ancestors", true);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    private InquiryCheckInfo$$serializer() {
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] childSerializers() {
        IntSerializer intSerializer = IntSerializer.INSTANCE;
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        return new KSerializer[]{BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, BuiltinSerializersKt.getNullable(intSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(new ArrayListSerializer(BuiltinSerializersKt.getNullable(INSTANCE))), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(stringSerializer), intSerializer, BooleanSerializer.INSTANCE, BuiltinSerializersKt.getNullable(stringSerializer), BuiltinSerializersKt.getNullable(intSerializer), intSerializer, intSerializer, BuiltinSerializersKt.getNullable(stringSerializer)};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    @NotNull
    public InquiryCheckInfo deserialize(@NotNull Decoder decoder) {
        Object objDecodeNullableSerializableElement;
        int iDecodeIntElement;
        int iDecodeIntElement2;
        Object obj;
        Object objDecodeNullableSerializableElement2;
        Object obj2;
        Object obj3;
        int i2;
        int i3;
        Object obj4;
        Object obj5;
        Object obj6;
        int i4;
        boolean zDecodeBooleanElement;
        Object obj7;
        Object obj8;
        Object obj9;
        Object objDecodeNullableSerializableElement3;
        Object obj10;
        boolean z2;
        char c3;
        Object obj11;
        Object obj12;
        boolean z3;
        Object obj13;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor2);
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            IntSerializer intSerializer = IntSerializer.INSTANCE;
            Object objDecodeNullableSerializableElement4 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, intSerializer, null);
            StringSerializer stringSerializer = StringSerializer.INSTANCE;
            Object objDecodeNullableSerializableElement5 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, stringSerializer, null);
            Object objDecodeNullableSerializableElement6 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, stringSerializer, null);
            Object objDecodeNullableSerializableElement7 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, stringSerializer, null);
            int iDecodeIntElement3 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 4);
            Object objDecodeNullableSerializableElement8 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, intSerializer, null);
            objDecodeNullableSerializableElement3 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, stringSerializer, null);
            Object objDecodeNullableSerializableElement9 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, new ArrayListSerializer(BuiltinSerializersKt.getNullable(INSTANCE)), null);
            Object objDecodeNullableSerializableElement10 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, stringSerializer, null);
            Object objDecodeNullableSerializableElement11 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, stringSerializer, null);
            Object objDecodeNullableSerializableElement12 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 10, stringSerializer, null);
            int iDecodeIntElement4 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 11);
            zDecodeBooleanElement = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, 12);
            objDecodeNullableSerializableElement2 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 13, stringSerializer, null);
            Object objDecodeNullableSerializableElement13 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 14, intSerializer, null);
            int iDecodeIntElement5 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 15);
            int iDecodeIntElement6 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 16);
            obj2 = objDecodeNullableSerializableElement5;
            Object objDecodeNullableSerializableElement14 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 17, stringSerializer, null);
            i2 = iDecodeIntElement5;
            obj = objDecodeNullableSerializableElement6;
            iDecodeIntElement = iDecodeIntElement4;
            obj7 = objDecodeNullableSerializableElement7;
            iDecodeIntElement2 = iDecodeIntElement3;
            obj9 = objDecodeNullableSerializableElement10;
            i4 = iDecodeIntElement6;
            obj8 = objDecodeNullableSerializableElement11;
            obj10 = objDecodeNullableSerializableElement12;
            obj4 = objDecodeNullableSerializableElement8;
            obj6 = objDecodeNullableSerializableElement13;
            obj5 = objDecodeNullableSerializableElement14;
            objDecodeNullableSerializableElement = objDecodeNullableSerializableElement9;
            i3 = 262143;
            obj3 = objDecodeNullableSerializableElement4;
        } else {
            Object objDecodeNullableSerializableElement15 = null;
            boolean z4 = true;
            Object objDecodeNullableSerializableElement16 = null;
            Object objDecodeNullableSerializableElement17 = null;
            Object objDecodeNullableSerializableElement18 = null;
            Object objDecodeNullableSerializableElement19 = null;
            Object objDecodeNullableSerializableElement20 = null;
            objDecodeNullableSerializableElement = null;
            Object objDecodeNullableSerializableElement21 = null;
            Object objDecodeNullableSerializableElement22 = null;
            Object objDecodeNullableSerializableElement23 = null;
            Object objDecodeNullableSerializableElement24 = null;
            Object objDecodeNullableSerializableElement25 = null;
            boolean zDecodeBooleanElement2 = false;
            int iDecodeIntElement7 = 0;
            int i5 = 0;
            iDecodeIntElement = 0;
            int iDecodeIntElement8 = 0;
            iDecodeIntElement2 = 0;
            Object objDecodeNullableSerializableElement26 = null;
            while (z4) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(descriptor2);
                switch (iDecodeElementIndex) {
                    case -1:
                        obj11 = objDecodeNullableSerializableElement16;
                        obj12 = objDecodeNullableSerializableElement15;
                        z3 = zDecodeBooleanElement2;
                        z4 = false;
                        objDecodeNullableSerializableElement15 = obj12;
                        objDecodeNullableSerializableElement16 = obj11;
                        zDecodeBooleanElement2 = z3;
                    case 0:
                        obj12 = objDecodeNullableSerializableElement15;
                        z3 = zDecodeBooleanElement2;
                        obj11 = objDecodeNullableSerializableElement16;
                        objDecodeNullableSerializableElement23 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 0, IntSerializer.INSTANCE, objDecodeNullableSerializableElement23);
                        i5 |= 1;
                        objDecodeNullableSerializableElement15 = obj12;
                        objDecodeNullableSerializableElement16 = obj11;
                        zDecodeBooleanElement2 = z3;
                    case 1:
                        obj13 = objDecodeNullableSerializableElement15;
                        z3 = zDecodeBooleanElement2;
                        objDecodeNullableSerializableElement26 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 1, StringSerializer.INSTANCE, objDecodeNullableSerializableElement26);
                        i5 |= 2;
                        objDecodeNullableSerializableElement15 = obj13;
                        zDecodeBooleanElement2 = z3;
                    case 2:
                        obj13 = objDecodeNullableSerializableElement15;
                        z3 = zDecodeBooleanElement2;
                        objDecodeNullableSerializableElement16 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 2, StringSerializer.INSTANCE, objDecodeNullableSerializableElement16);
                        i5 |= 4;
                        objDecodeNullableSerializableElement15 = obj13;
                        zDecodeBooleanElement2 = z3;
                    case 3:
                        obj13 = objDecodeNullableSerializableElement15;
                        z3 = zDecodeBooleanElement2;
                        objDecodeNullableSerializableElement18 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 3, StringSerializer.INSTANCE, objDecodeNullableSerializableElement18);
                        i5 |= 8;
                        objDecodeNullableSerializableElement15 = obj13;
                        zDecodeBooleanElement2 = z3;
                    case 4:
                        obj13 = objDecodeNullableSerializableElement15;
                        z3 = zDecodeBooleanElement2;
                        iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 4);
                        i5 |= 16;
                        objDecodeNullableSerializableElement15 = obj13;
                        zDecodeBooleanElement2 = z3;
                    case 5:
                        obj13 = objDecodeNullableSerializableElement15;
                        z3 = zDecodeBooleanElement2;
                        objDecodeNullableSerializableElement21 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 5, IntSerializer.INSTANCE, objDecodeNullableSerializableElement21);
                        i5 |= 32;
                        objDecodeNullableSerializableElement15 = obj13;
                        zDecodeBooleanElement2 = z3;
                    case 6:
                        obj13 = objDecodeNullableSerializableElement15;
                        z3 = zDecodeBooleanElement2;
                        objDecodeNullableSerializableElement22 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 6, StringSerializer.INSTANCE, objDecodeNullableSerializableElement22);
                        i5 |= 64;
                        objDecodeNullableSerializableElement15 = obj13;
                        zDecodeBooleanElement2 = z3;
                    case 7:
                        obj13 = objDecodeNullableSerializableElement15;
                        z3 = zDecodeBooleanElement2;
                        objDecodeNullableSerializableElement = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 7, new ArrayListSerializer(BuiltinSerializersKt.getNullable(INSTANCE)), objDecodeNullableSerializableElement);
                        i5 |= 128;
                        objDecodeNullableSerializableElement15 = obj13;
                        zDecodeBooleanElement2 = z3;
                    case 8:
                        obj13 = objDecodeNullableSerializableElement15;
                        z3 = zDecodeBooleanElement2;
                        objDecodeNullableSerializableElement20 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 8, StringSerializer.INSTANCE, objDecodeNullableSerializableElement20);
                        i5 |= 256;
                        objDecodeNullableSerializableElement15 = obj13;
                        zDecodeBooleanElement2 = z3;
                    case 9:
                        obj13 = objDecodeNullableSerializableElement15;
                        z3 = zDecodeBooleanElement2;
                        objDecodeNullableSerializableElement19 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 9, StringSerializer.INSTANCE, objDecodeNullableSerializableElement19);
                        i5 |= 512;
                        objDecodeNullableSerializableElement15 = obj13;
                        zDecodeBooleanElement2 = z3;
                    case 10:
                        obj13 = objDecodeNullableSerializableElement15;
                        z3 = zDecodeBooleanElement2;
                        objDecodeNullableSerializableElement17 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 10, StringSerializer.INSTANCE, objDecodeNullableSerializableElement17);
                        i5 |= 1024;
                        objDecodeNullableSerializableElement15 = obj13;
                        zDecodeBooleanElement2 = z3;
                    case 11:
                        obj13 = objDecodeNullableSerializableElement15;
                        z3 = zDecodeBooleanElement2;
                        iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 11);
                        i5 |= 2048;
                        objDecodeNullableSerializableElement15 = obj13;
                        zDecodeBooleanElement2 = z3;
                    case 12:
                        i5 |= 4096;
                        objDecodeNullableSerializableElement15 = objDecodeNullableSerializableElement15;
                        zDecodeBooleanElement2 = compositeDecoderBeginStructure.decodeBooleanElement(descriptor2, 12);
                    case 13:
                        z3 = zDecodeBooleanElement2;
                        objDecodeNullableSerializableElement24 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 13, StringSerializer.INSTANCE, objDecodeNullableSerializableElement24);
                        i5 |= 8192;
                        objDecodeNullableSerializableElement15 = objDecodeNullableSerializableElement15;
                        objDecodeNullableSerializableElement25 = objDecodeNullableSerializableElement25;
                        zDecodeBooleanElement2 = z3;
                    case 14:
                        z3 = zDecodeBooleanElement2;
                        obj13 = objDecodeNullableSerializableElement15;
                        objDecodeNullableSerializableElement25 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 14, IntSerializer.INSTANCE, objDecodeNullableSerializableElement25);
                        i5 |= 16384;
                        objDecodeNullableSerializableElement15 = obj13;
                        zDecodeBooleanElement2 = z3;
                    case 15:
                        z2 = zDecodeBooleanElement2;
                        i5 |= 32768;
                        iDecodeIntElement7 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 15);
                        zDecodeBooleanElement2 = z2;
                    case 16:
                        z2 = zDecodeBooleanElement2;
                        c3 = 17;
                        iDecodeIntElement8 = compositeDecoderBeginStructure.decodeIntElement(descriptor2, 16);
                        i5 |= 65536;
                        zDecodeBooleanElement2 = z2;
                    case 17:
                        z2 = zDecodeBooleanElement2;
                        c3 = 17;
                        objDecodeNullableSerializableElement15 = compositeDecoderBeginStructure.decodeNullableSerializableElement(descriptor2, 17, StringSerializer.INSTANCE, objDecodeNullableSerializableElement15);
                        i5 |= 131072;
                        zDecodeBooleanElement2 = z2;
                    default:
                        throw new UnknownFieldException(iDecodeElementIndex);
                }
            }
            obj = objDecodeNullableSerializableElement16;
            Object obj14 = objDecodeNullableSerializableElement15;
            objDecodeNullableSerializableElement2 = objDecodeNullableSerializableElement24;
            obj2 = objDecodeNullableSerializableElement26;
            obj3 = objDecodeNullableSerializableElement23;
            i2 = iDecodeIntElement7;
            i3 = i5;
            obj4 = objDecodeNullableSerializableElement21;
            obj5 = obj14;
            obj6 = objDecodeNullableSerializableElement25;
            i4 = iDecodeIntElement8;
            zDecodeBooleanElement = zDecodeBooleanElement2;
            obj7 = objDecodeNullableSerializableElement18;
            obj8 = objDecodeNullableSerializableElement19;
            obj9 = objDecodeNullableSerializableElement20;
            objDecodeNullableSerializableElement3 = objDecodeNullableSerializableElement22;
            obj10 = objDecodeNullableSerializableElement17;
        }
        compositeDecoderBeginStructure.endStructure(descriptor2);
        return new InquiryCheckInfo(i3, (Integer) obj3, (String) obj2, (String) obj, (String) obj7, iDecodeIntElement2, (Integer) obj4, (String) objDecodeNullableSerializableElement3, (List) objDecodeNullableSerializableElement, (String) obj9, (String) obj8, (String) obj10, iDecodeIntElement, zDecodeBooleanElement, (String) objDecodeNullableSerializableElement2, (Integer) obj6, i2, i4, (String) obj5, null);
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    @NotNull
    public SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public void serialize(@NotNull Encoder encoder, @NotNull InquiryCheckInfo value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor descriptor2 = getDescriptor();
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(descriptor2);
        InquiryCheckInfo.write$Self(value, compositeEncoderBeginStructure, descriptor2);
        compositeEncoderBeginStructure.endStructure(descriptor2);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    @NotNull
    public KSerializer<?>[] typeParametersSerializers() {
        return GeneratedSerializer.DefaultImpls.typeParametersSerializers(this);
    }
}

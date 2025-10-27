package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.ArrayDecoders;
import androidx.datastore.preferences.protobuf.ByteString;
import androidx.datastore.preferences.protobuf.Internal;
import androidx.datastore.preferences.protobuf.MapEntryLite;
import androidx.datastore.preferences.protobuf.WireFormat;
import androidx.datastore.preferences.protobuf.Writer;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* loaded from: classes.dex */
final class MessageSchema<T> implements Schema<T> {
    private static final int ENFORCE_UTF8_MASK = 536870912;
    private static final int FIELD_TYPE_MASK = 267386880;
    private static final int INTS_PER_FIELD = 3;
    private static final int OFFSET_BITS = 20;
    private static final int OFFSET_MASK = 1048575;
    static final int ONEOF_TYPE_OFFSET = 51;
    private static final int REQUIRED_MASK = 268435456;
    private final int[] buffer;
    private final int checkInitializedCount;
    private final MessageLite defaultInstance;
    private final ExtensionSchema<?> extensionSchema;
    private final boolean hasExtensions;
    private final int[] intArray;
    private final ListFieldSchema listFieldSchema;
    private final boolean lite;
    private final MapFieldSchema mapFieldSchema;
    private final int maxFieldNumber;
    private final int minFieldNumber;
    private final NewInstanceSchema newInstanceSchema;
    private final Object[] objects;
    private final boolean proto3;
    private final int repeatedFieldOffsetStart;
    private final UnknownFieldSchema<?, ?> unknownFieldSchema;
    private final boolean useCachedSizeField;
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final Unsafe UNSAFE = UnsafeUtil.getUnsafe();

    /* renamed from: androidx.datastore.preferences.protobuf.MessageSchema$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$WireFormat$FieldType;

        static {
            int[] iArr = new int[WireFormat.FieldType.values().length];
            $SwitchMap$com$google$protobuf$WireFormat$FieldType = iArr;
            try {
                iArr[WireFormat.FieldType.BOOL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.BYTES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.DOUBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.FIXED32.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.SFIXED32.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.FIXED64.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.SFIXED64.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.FLOAT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.ENUM.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.INT32.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.UINT32.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.INT64.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.UINT64.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.MESSAGE.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.SINT32.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.SINT64.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.STRING.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
        }
    }

    private MessageSchema(int[] iArr, Object[] objArr, int i2, int i3, MessageLite messageLite, boolean z2, boolean z3, int[] iArr2, int i4, int i5, NewInstanceSchema newInstanceSchema, ListFieldSchema listFieldSchema, UnknownFieldSchema<?, ?> unknownFieldSchema, ExtensionSchema<?> extensionSchema, MapFieldSchema mapFieldSchema) {
        this.buffer = iArr;
        this.objects = objArr;
        this.minFieldNumber = i2;
        this.maxFieldNumber = i3;
        this.lite = messageLite instanceof GeneratedMessageLite;
        this.proto3 = z2;
        this.hasExtensions = extensionSchema != null && extensionSchema.hasExtensions(messageLite);
        this.useCachedSizeField = z3;
        this.intArray = iArr2;
        this.checkInitializedCount = i4;
        this.repeatedFieldOffsetStart = i5;
        this.newInstanceSchema = newInstanceSchema;
        this.listFieldSchema = listFieldSchema;
        this.unknownFieldSchema = unknownFieldSchema;
        this.extensionSchema = extensionSchema;
        this.defaultInstance = messageLite;
        this.mapFieldSchema = mapFieldSchema;
    }

    private boolean arePresentForEquals(T t2, T t3, int i2) {
        return isFieldPresent(t2, i2) == isFieldPresent(t3, i2);
    }

    private static <T> boolean booleanAt(T t2, long j2) {
        return UnsafeUtil.getBoolean(t2, j2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r19v0, types: [java.util.Map, java.util.Map<K, V>] */
    /* JADX WARN: Type inference failed for: r1v10, types: [int] */
    private <K, V> int decodeMapEntry(byte[] bArr, int i2, int i3, MapEntryLite.Metadata<K, V> metadata, Map<K, V> map, ArrayDecoders.Registers registers) throws IOException {
        int iDecodeVarint32;
        int iDecodeVarint322 = ArrayDecoders.decodeVarint32(bArr, i2, registers);
        int i4 = registers.int1;
        if (i4 < 0 || i4 > i3 - iDecodeVarint322) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        int i5 = iDecodeVarint322 + i4;
        Object obj = metadata.defaultKey;
        Object obj2 = metadata.defaultValue;
        while (iDecodeVarint322 < i5) {
            int i6 = iDecodeVarint322 + 1;
            byte b3 = bArr[iDecodeVarint322];
            if (b3 < 0) {
                iDecodeVarint32 = ArrayDecoders.decodeVarint32(b3, bArr, i6, registers);
                b3 = registers.int1;
            } else {
                iDecodeVarint32 = i6;
            }
            int i7 = b3 >>> 3;
            int i8 = b3 & 7;
            if (i7 != 1) {
                if (i7 == 2 && i8 == metadata.valueType.getWireType()) {
                    iDecodeVarint322 = decodeMapEntryValue(bArr, iDecodeVarint32, i3, metadata.valueType, metadata.defaultValue.getClass(), registers);
                    obj2 = registers.object1;
                } else {
                    iDecodeVarint322 = ArrayDecoders.skipField(b3, bArr, iDecodeVarint32, i3, registers);
                }
            } else if (i8 == metadata.keyType.getWireType()) {
                iDecodeVarint322 = decodeMapEntryValue(bArr, iDecodeVarint32, i3, metadata.keyType, null, registers);
                obj = registers.object1;
            } else {
                iDecodeVarint322 = ArrayDecoders.skipField(b3, bArr, iDecodeVarint32, i3, registers);
            }
        }
        if (iDecodeVarint322 != i5) {
            throw InvalidProtocolBufferException.parseFailure();
        }
        map.put(obj, obj2);
        return i5;
    }

    private int decodeMapEntryValue(byte[] bArr, int i2, int i3, WireFormat.FieldType fieldType, Class<?> cls, ArrayDecoders.Registers registers) throws IOException {
        switch (AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[fieldType.ordinal()]) {
            case 1:
                int iDecodeVarint64 = ArrayDecoders.decodeVarint64(bArr, i2, registers);
                registers.object1 = Boolean.valueOf(registers.long1 != 0);
                return iDecodeVarint64;
            case 2:
                return ArrayDecoders.decodeBytes(bArr, i2, registers);
            case 3:
                registers.object1 = Double.valueOf(ArrayDecoders.decodeDouble(bArr, i2));
                return i2 + 8;
            case 4:
            case 5:
                registers.object1 = Integer.valueOf(ArrayDecoders.decodeFixed32(bArr, i2));
                return i2 + 4;
            case 6:
            case 7:
                registers.object1 = Long.valueOf(ArrayDecoders.decodeFixed64(bArr, i2));
                return i2 + 8;
            case 8:
                registers.object1 = Float.valueOf(ArrayDecoders.decodeFloat(bArr, i2));
                return i2 + 4;
            case 9:
            case 10:
            case 11:
                int iDecodeVarint32 = ArrayDecoders.decodeVarint32(bArr, i2, registers);
                registers.object1 = Integer.valueOf(registers.int1);
                return iDecodeVarint32;
            case 12:
            case 13:
                int iDecodeVarint642 = ArrayDecoders.decodeVarint64(bArr, i2, registers);
                registers.object1 = Long.valueOf(registers.long1);
                return iDecodeVarint642;
            case 14:
                return ArrayDecoders.decodeMessageField(Protobuf.getInstance().schemaFor((Class) cls), bArr, i2, i3, registers);
            case 15:
                int iDecodeVarint322 = ArrayDecoders.decodeVarint32(bArr, i2, registers);
                registers.object1 = Integer.valueOf(CodedInputStream.decodeZigZag32(registers.int1));
                return iDecodeVarint322;
            case 16:
                int iDecodeVarint643 = ArrayDecoders.decodeVarint64(bArr, i2, registers);
                registers.object1 = Long.valueOf(CodedInputStream.decodeZigZag64(registers.long1));
                return iDecodeVarint643;
            case 17:
                return ArrayDecoders.decodeStringRequireUtf8(bArr, i2, registers);
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    private static <T> double doubleAt(T t2, long j2) {
        return UnsafeUtil.getDouble(t2, j2);
    }

    private final <UT, UB> UB filterMapUnknownEnumValues(Object obj, int i2, UB ub, UnknownFieldSchema<UT, UB> unknownFieldSchema) {
        Internal.EnumVerifier enumFieldVerifier;
        int iNumberAt = numberAt(i2);
        Object object = UnsafeUtil.getObject(obj, offset(typeAndOffsetAt(i2)));
        return (object == null || (enumFieldVerifier = getEnumFieldVerifier(i2)) == null) ? ub : (UB) filterUnknownEnumMap(i2, iNumberAt, this.mapFieldSchema.forMutableMapData(object), enumFieldVerifier, ub, unknownFieldSchema);
    }

    private final <K, V, UT, UB> UB filterUnknownEnumMap(int i2, int i3, Map<K, V> map, Internal.EnumVerifier enumVerifier, UB ub, UnknownFieldSchema<UT, UB> unknownFieldSchema) {
        MapEntryLite.Metadata<?, ?> metadataForMapMetadata = this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(i2));
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (!enumVerifier.isInRange(((Integer) next.getValue()).intValue())) {
                if (ub == null) {
                    ub = unknownFieldSchema.newBuilder();
                }
                ByteString.CodedBuilder codedBuilderNewCodedBuilder = ByteString.newCodedBuilder(MapEntryLite.computeSerializedSize(metadataForMapMetadata, next.getKey(), next.getValue()));
                try {
                    MapEntryLite.writeTo(codedBuilderNewCodedBuilder.getCodedOutput(), metadataForMapMetadata, next.getKey(), next.getValue());
                    unknownFieldSchema.addLengthDelimited(ub, i3, codedBuilderNewCodedBuilder.build());
                    it.remove();
                } catch (IOException e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
        return ub;
    }

    private static <T> float floatAt(T t2, long j2) {
        return UnsafeUtil.getFloat(t2, j2);
    }

    private Internal.EnumVerifier getEnumFieldVerifier(int i2) {
        return (Internal.EnumVerifier) this.objects[((i2 / 3) * 2) + 1];
    }

    private Object getMapFieldDefaultEntry(int i2) {
        return this.objects[(i2 / 3) * 2];
    }

    private Schema getMessageFieldSchema(int i2) {
        int i3 = (i2 / 3) * 2;
        Schema schema = (Schema) this.objects[i3];
        if (schema != null) {
            return schema;
        }
        Schema<T> schemaSchemaFor = Protobuf.getInstance().schemaFor((Class) this.objects[i3 + 1]);
        this.objects[i3] = schemaSchemaFor;
        return schemaSchemaFor;
    }

    public static UnknownFieldSetLite getMutableUnknownFields(Object obj) {
        GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) obj;
        UnknownFieldSetLite unknownFieldSetLite = generatedMessageLite.unknownFields;
        if (unknownFieldSetLite != UnknownFieldSetLite.getDefaultInstance()) {
            return unknownFieldSetLite;
        }
        UnknownFieldSetLite unknownFieldSetLiteNewInstance = UnknownFieldSetLite.newInstance();
        generatedMessageLite.unknownFields = unknownFieldSetLiteNewInstance;
        return unknownFieldSetLiteNewInstance;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int getSerializedSizeProto2(T t2) {
        int i2;
        int i3;
        int iComputeDoubleSize;
        int iComputeBoolSize;
        int iComputeSFixed32Size;
        boolean z2;
        int iComputeSizeFixed32List;
        int iComputeSizeFixed64ListNoTag;
        int iComputeTagSize;
        int iComputeUInt32SizeNoTag;
        Unsafe unsafe = UNSAFE;
        int i4 = -1;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (i5 < this.buffer.length) {
            int iTypeAndOffsetAt = typeAndOffsetAt(i5);
            int iNumberAt = numberAt(i5);
            int iType = type(iTypeAndOffsetAt);
            if (iType <= 17) {
                i2 = this.buffer[i5 + 2];
                int i8 = OFFSET_MASK & i2;
                int i9 = 1 << (i2 >>> 20);
                if (i8 != i4) {
                    i7 = unsafe.getInt(t2, i8);
                    i4 = i8;
                }
                i3 = i9;
            } else {
                i2 = (!this.useCachedSizeField || iType < FieldType.DOUBLE_LIST_PACKED.id() || iType > FieldType.SINT64_LIST_PACKED.id()) ? 0 : this.buffer[i5 + 2] & OFFSET_MASK;
                i3 = 0;
            }
            long jOffset = offset(iTypeAndOffsetAt);
            int i10 = i4;
            switch (iType) {
                case 0:
                    if ((i7 & i3) == 0) {
                        break;
                    } else {
                        iComputeDoubleSize = CodedOutputStream.computeDoubleSize(iNumberAt, 0.0d);
                        i6 += iComputeDoubleSize;
                        break;
                    }
                case 1:
                    if ((i7 & i3) == 0) {
                        break;
                    } else {
                        iComputeDoubleSize = CodedOutputStream.computeFloatSize(iNumberAt, 0.0f);
                        i6 += iComputeDoubleSize;
                        break;
                    }
                case 2:
                    if ((i7 & i3) == 0) {
                        break;
                    } else {
                        iComputeDoubleSize = CodedOutputStream.computeInt64Size(iNumberAt, unsafe.getLong(t2, jOffset));
                        i6 += iComputeDoubleSize;
                        break;
                    }
                case 3:
                    if ((i7 & i3) == 0) {
                        break;
                    } else {
                        iComputeDoubleSize = CodedOutputStream.computeUInt64Size(iNumberAt, unsafe.getLong(t2, jOffset));
                        i6 += iComputeDoubleSize;
                        break;
                    }
                case 4:
                    if ((i7 & i3) == 0) {
                        break;
                    } else {
                        iComputeDoubleSize = CodedOutputStream.computeInt32Size(iNumberAt, unsafe.getInt(t2, jOffset));
                        i6 += iComputeDoubleSize;
                        break;
                    }
                case 5:
                    if ((i7 & i3) == 0) {
                        break;
                    } else {
                        iComputeDoubleSize = CodedOutputStream.computeFixed64Size(iNumberAt, 0L);
                        i6 += iComputeDoubleSize;
                        break;
                    }
                case 6:
                    if ((i7 & i3) != 0) {
                        iComputeDoubleSize = CodedOutputStream.computeFixed32Size(iNumberAt, 0);
                        i6 += iComputeDoubleSize;
                        break;
                    }
                    break;
                case 7:
                    if ((i7 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeBoolSize(iNumberAt, true);
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 8:
                    if ((i7 & i3) != 0) {
                        Object object = unsafe.getObject(t2, jOffset);
                        iComputeBoolSize = object instanceof ByteString ? CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) object) : CodedOutputStream.computeStringSize(iNumberAt, (String) object);
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 9:
                    if ((i7 & i3) != 0) {
                        iComputeBoolSize = SchemaUtil.computeSizeMessage(iNumberAt, unsafe.getObject(t2, jOffset), getMessageFieldSchema(i5));
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 10:
                    if ((i7 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) unsafe.getObject(t2, jOffset));
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 11:
                    if ((i7 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeUInt32Size(iNumberAt, unsafe.getInt(t2, jOffset));
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 12:
                    if ((i7 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeEnumSize(iNumberAt, unsafe.getInt(t2, jOffset));
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 13:
                    if ((i7 & i3) != 0) {
                        iComputeSFixed32Size = CodedOutputStream.computeSFixed32Size(iNumberAt, 0);
                        i6 += iComputeSFixed32Size;
                    }
                    break;
                case 14:
                    if ((i7 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeSFixed64Size(iNumberAt, 0L);
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 15:
                    if ((i7 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeSInt32Size(iNumberAt, unsafe.getInt(t2, jOffset));
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 16:
                    if ((i7 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeSInt64Size(iNumberAt, unsafe.getLong(t2, jOffset));
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 17:
                    if ((i7 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeGroupSize(iNumberAt, (MessageLite) unsafe.getObject(t2, jOffset), getMessageFieldSchema(i5));
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 18:
                    iComputeBoolSize = SchemaUtil.computeSizeFixed64List(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i6 += iComputeBoolSize;
                    break;
                case 19:
                    z2 = false;
                    iComputeSizeFixed32List = SchemaUtil.computeSizeFixed32List(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i6 += iComputeSizeFixed32List;
                    break;
                case 20:
                    z2 = false;
                    iComputeSizeFixed32List = SchemaUtil.computeSizeInt64List(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i6 += iComputeSizeFixed32List;
                    break;
                case 21:
                    z2 = false;
                    iComputeSizeFixed32List = SchemaUtil.computeSizeUInt64List(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i6 += iComputeSizeFixed32List;
                    break;
                case 22:
                    z2 = false;
                    iComputeSizeFixed32List = SchemaUtil.computeSizeInt32List(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i6 += iComputeSizeFixed32List;
                    break;
                case 23:
                    z2 = false;
                    iComputeSizeFixed32List = SchemaUtil.computeSizeFixed64List(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i6 += iComputeSizeFixed32List;
                    break;
                case 24:
                    z2 = false;
                    iComputeSizeFixed32List = SchemaUtil.computeSizeFixed32List(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i6 += iComputeSizeFixed32List;
                    break;
                case 25:
                    z2 = false;
                    iComputeSizeFixed32List = SchemaUtil.computeSizeBoolList(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i6 += iComputeSizeFixed32List;
                    break;
                case 26:
                    iComputeBoolSize = SchemaUtil.computeSizeStringList(iNumberAt, (List) unsafe.getObject(t2, jOffset));
                    i6 += iComputeBoolSize;
                    break;
                case 27:
                    iComputeBoolSize = SchemaUtil.computeSizeMessageList(iNumberAt, (List) unsafe.getObject(t2, jOffset), getMessageFieldSchema(i5));
                    i6 += iComputeBoolSize;
                    break;
                case 28:
                    iComputeBoolSize = SchemaUtil.computeSizeByteStringList(iNumberAt, (List) unsafe.getObject(t2, jOffset));
                    i6 += iComputeBoolSize;
                    break;
                case 29:
                    iComputeBoolSize = SchemaUtil.computeSizeUInt32List(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i6 += iComputeBoolSize;
                    break;
                case 30:
                    z2 = false;
                    iComputeSizeFixed32List = SchemaUtil.computeSizeEnumList(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i6 += iComputeSizeFixed32List;
                    break;
                case 31:
                    z2 = false;
                    iComputeSizeFixed32List = SchemaUtil.computeSizeFixed32List(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i6 += iComputeSizeFixed32List;
                    break;
                case 32:
                    z2 = false;
                    iComputeSizeFixed32List = SchemaUtil.computeSizeFixed64List(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i6 += iComputeSizeFixed32List;
                    break;
                case 33:
                    z2 = false;
                    iComputeSizeFixed32List = SchemaUtil.computeSizeSInt32List(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i6 += iComputeSizeFixed32List;
                    break;
                case 34:
                    z2 = false;
                    iComputeSizeFixed32List = SchemaUtil.computeSizeSInt64List(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i6 += iComputeSizeFixed32List;
                    break;
                case 35:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i6 += iComputeSFixed32Size;
                    }
                    break;
                case 36:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i6 += iComputeSFixed32Size;
                    }
                    break;
                case 37:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeInt64ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i6 += iComputeSFixed32Size;
                    }
                    break;
                case 38:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeUInt64ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i6 += iComputeSFixed32Size;
                    }
                    break;
                case 39:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeInt32ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i6 += iComputeSFixed32Size;
                    }
                    break;
                case 40:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i6 += iComputeSFixed32Size;
                    }
                    break;
                case 41:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i6 += iComputeSFixed32Size;
                    }
                    break;
                case 42:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeBoolListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i6 += iComputeSFixed32Size;
                    }
                    break;
                case 43:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeUInt32ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i6 += iComputeSFixed32Size;
                    }
                    break;
                case 44:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeEnumListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i6 += iComputeSFixed32Size;
                    }
                    break;
                case 45:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i6 += iComputeSFixed32Size;
                    }
                    break;
                case 46:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i6 += iComputeSFixed32Size;
                    }
                    break;
                case 47:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeSInt32ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i6 += iComputeSFixed32Size;
                    }
                    break;
                case 48:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeSInt64ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i6 += iComputeSFixed32Size;
                    }
                    break;
                case 49:
                    iComputeBoolSize = SchemaUtil.computeSizeGroupList(iNumberAt, (List) unsafe.getObject(t2, jOffset), getMessageFieldSchema(i5));
                    i6 += iComputeBoolSize;
                    break;
                case 50:
                    iComputeBoolSize = this.mapFieldSchema.getSerializedSize(iNumberAt, unsafe.getObject(t2, jOffset), getMapFieldDefaultEntry(i5));
                    i6 += iComputeBoolSize;
                    break;
                case 51:
                    if (isOneofPresent(t2, iNumberAt, i5)) {
                        iComputeBoolSize = CodedOutputStream.computeDoubleSize(iNumberAt, 0.0d);
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 52:
                    if (isOneofPresent(t2, iNumberAt, i5)) {
                        iComputeBoolSize = CodedOutputStream.computeFloatSize(iNumberAt, 0.0f);
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 53:
                    if (isOneofPresent(t2, iNumberAt, i5)) {
                        iComputeBoolSize = CodedOutputStream.computeInt64Size(iNumberAt, oneofLongAt(t2, jOffset));
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 54:
                    if (isOneofPresent(t2, iNumberAt, i5)) {
                        iComputeBoolSize = CodedOutputStream.computeUInt64Size(iNumberAt, oneofLongAt(t2, jOffset));
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 55:
                    if (isOneofPresent(t2, iNumberAt, i5)) {
                        iComputeBoolSize = CodedOutputStream.computeInt32Size(iNumberAt, oneofIntAt(t2, jOffset));
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 56:
                    if (isOneofPresent(t2, iNumberAt, i5)) {
                        iComputeBoolSize = CodedOutputStream.computeFixed64Size(iNumberAt, 0L);
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 57:
                    if (isOneofPresent(t2, iNumberAt, i5)) {
                        iComputeSFixed32Size = CodedOutputStream.computeFixed32Size(iNumberAt, 0);
                        i6 += iComputeSFixed32Size;
                    }
                    break;
                case 58:
                    if (isOneofPresent(t2, iNumberAt, i5)) {
                        iComputeBoolSize = CodedOutputStream.computeBoolSize(iNumberAt, true);
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 59:
                    if (isOneofPresent(t2, iNumberAt, i5)) {
                        Object object2 = unsafe.getObject(t2, jOffset);
                        iComputeBoolSize = object2 instanceof ByteString ? CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) object2) : CodedOutputStream.computeStringSize(iNumberAt, (String) object2);
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 60:
                    if (isOneofPresent(t2, iNumberAt, i5)) {
                        iComputeBoolSize = SchemaUtil.computeSizeMessage(iNumberAt, unsafe.getObject(t2, jOffset), getMessageFieldSchema(i5));
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 61:
                    if (isOneofPresent(t2, iNumberAt, i5)) {
                        iComputeBoolSize = CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) unsafe.getObject(t2, jOffset));
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 62:
                    if (isOneofPresent(t2, iNumberAt, i5)) {
                        iComputeBoolSize = CodedOutputStream.computeUInt32Size(iNumberAt, oneofIntAt(t2, jOffset));
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 63:
                    if (isOneofPresent(t2, iNumberAt, i5)) {
                        iComputeBoolSize = CodedOutputStream.computeEnumSize(iNumberAt, oneofIntAt(t2, jOffset));
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 64:
                    if (isOneofPresent(t2, iNumberAt, i5)) {
                        iComputeSFixed32Size = CodedOutputStream.computeSFixed32Size(iNumberAt, 0);
                        i6 += iComputeSFixed32Size;
                    }
                    break;
                case 65:
                    if (isOneofPresent(t2, iNumberAt, i5)) {
                        iComputeBoolSize = CodedOutputStream.computeSFixed64Size(iNumberAt, 0L);
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 66:
                    if (isOneofPresent(t2, iNumberAt, i5)) {
                        iComputeBoolSize = CodedOutputStream.computeSInt32Size(iNumberAt, oneofIntAt(t2, jOffset));
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 67:
                    if (isOneofPresent(t2, iNumberAt, i5)) {
                        iComputeBoolSize = CodedOutputStream.computeSInt64Size(iNumberAt, oneofLongAt(t2, jOffset));
                        i6 += iComputeBoolSize;
                    }
                    break;
                case 68:
                    if (isOneofPresent(t2, iNumberAt, i5)) {
                        iComputeBoolSize = CodedOutputStream.computeGroupSize(iNumberAt, (MessageLite) unsafe.getObject(t2, jOffset), getMessageFieldSchema(i5));
                        i6 += iComputeBoolSize;
                    }
                    break;
            }
            i5 += 3;
            i4 = i10;
        }
        int unknownFieldsSerializedSize = i6 + getUnknownFieldsSerializedSize(this.unknownFieldSchema, t2);
        return this.hasExtensions ? unknownFieldsSerializedSize + this.extensionSchema.getExtensions(t2).getSerializedSize() : unknownFieldsSerializedSize;
    }

    private int getSerializedSizeProto3(T t2) {
        int iComputeDoubleSize;
        int iComputeSizeFixed64ListNoTag;
        int iComputeTagSize;
        int iComputeUInt32SizeNoTag;
        Unsafe unsafe = UNSAFE;
        int i2 = 0;
        for (int i3 = 0; i3 < this.buffer.length; i3 += 3) {
            int iTypeAndOffsetAt = typeAndOffsetAt(i3);
            int iType = type(iTypeAndOffsetAt);
            int iNumberAt = numberAt(i3);
            long jOffset = offset(iTypeAndOffsetAt);
            int i4 = (iType < FieldType.DOUBLE_LIST_PACKED.id() || iType > FieldType.SINT64_LIST_PACKED.id()) ? 0 : this.buffer[i3 + 2] & OFFSET_MASK;
            switch (iType) {
                case 0:
                    if (isFieldPresent(t2, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeDoubleSize(iNumberAt, 0.0d);
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (isFieldPresent(t2, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeFloatSize(iNumberAt, 0.0f);
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (isFieldPresent(t2, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeInt64Size(iNumberAt, UnsafeUtil.getLong(t2, jOffset));
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (isFieldPresent(t2, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeUInt64Size(iNumberAt, UnsafeUtil.getLong(t2, jOffset));
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (isFieldPresent(t2, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeInt32Size(iNumberAt, UnsafeUtil.getInt(t2, jOffset));
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (isFieldPresent(t2, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeFixed64Size(iNumberAt, 0L);
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (isFieldPresent(t2, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeFixed32Size(iNumberAt, 0);
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (isFieldPresent(t2, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeBoolSize(iNumberAt, true);
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (isFieldPresent(t2, i3)) {
                        Object object = UnsafeUtil.getObject(t2, jOffset);
                        iComputeDoubleSize = object instanceof ByteString ? CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) object) : CodedOutputStream.computeStringSize(iNumberAt, (String) object);
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 9:
                    if (isFieldPresent(t2, i3)) {
                        iComputeDoubleSize = SchemaUtil.computeSizeMessage(iNumberAt, UnsafeUtil.getObject(t2, jOffset), getMessageFieldSchema(i3));
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 10:
                    if (isFieldPresent(t2, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) UnsafeUtil.getObject(t2, jOffset));
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (isFieldPresent(t2, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeUInt32Size(iNumberAt, UnsafeUtil.getInt(t2, jOffset));
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (isFieldPresent(t2, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeEnumSize(iNumberAt, UnsafeUtil.getInt(t2, jOffset));
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (isFieldPresent(t2, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeSFixed32Size(iNumberAt, 0);
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (isFieldPresent(t2, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeSFixed64Size(iNumberAt, 0L);
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (isFieldPresent(t2, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeSInt32Size(iNumberAt, UnsafeUtil.getInt(t2, jOffset));
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (isFieldPresent(t2, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeSInt64Size(iNumberAt, UnsafeUtil.getLong(t2, jOffset));
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 17:
                    if (isFieldPresent(t2, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeGroupSize(iNumberAt, (MessageLite) UnsafeUtil.getObject(t2, jOffset), getMessageFieldSchema(i3));
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 18:
                    iComputeDoubleSize = SchemaUtil.computeSizeFixed64List(iNumberAt, listAt(t2, jOffset), false);
                    i2 += iComputeDoubleSize;
                    break;
                case 19:
                    iComputeDoubleSize = SchemaUtil.computeSizeFixed32List(iNumberAt, listAt(t2, jOffset), false);
                    i2 += iComputeDoubleSize;
                    break;
                case 20:
                    iComputeDoubleSize = SchemaUtil.computeSizeInt64List(iNumberAt, listAt(t2, jOffset), false);
                    i2 += iComputeDoubleSize;
                    break;
                case 21:
                    iComputeDoubleSize = SchemaUtil.computeSizeUInt64List(iNumberAt, listAt(t2, jOffset), false);
                    i2 += iComputeDoubleSize;
                    break;
                case 22:
                    iComputeDoubleSize = SchemaUtil.computeSizeInt32List(iNumberAt, listAt(t2, jOffset), false);
                    i2 += iComputeDoubleSize;
                    break;
                case 23:
                    iComputeDoubleSize = SchemaUtil.computeSizeFixed64List(iNumberAt, listAt(t2, jOffset), false);
                    i2 += iComputeDoubleSize;
                    break;
                case 24:
                    iComputeDoubleSize = SchemaUtil.computeSizeFixed32List(iNumberAt, listAt(t2, jOffset), false);
                    i2 += iComputeDoubleSize;
                    break;
                case 25:
                    iComputeDoubleSize = SchemaUtil.computeSizeBoolList(iNumberAt, listAt(t2, jOffset), false);
                    i2 += iComputeDoubleSize;
                    break;
                case 26:
                    iComputeDoubleSize = SchemaUtil.computeSizeStringList(iNumberAt, listAt(t2, jOffset));
                    i2 += iComputeDoubleSize;
                    break;
                case 27:
                    iComputeDoubleSize = SchemaUtil.computeSizeMessageList(iNumberAt, listAt(t2, jOffset), getMessageFieldSchema(i3));
                    i2 += iComputeDoubleSize;
                    break;
                case 28:
                    iComputeDoubleSize = SchemaUtil.computeSizeByteStringList(iNumberAt, listAt(t2, jOffset));
                    i2 += iComputeDoubleSize;
                    break;
                case 29:
                    iComputeDoubleSize = SchemaUtil.computeSizeUInt32List(iNumberAt, listAt(t2, jOffset), false);
                    i2 += iComputeDoubleSize;
                    break;
                case 30:
                    iComputeDoubleSize = SchemaUtil.computeSizeEnumList(iNumberAt, listAt(t2, jOffset), false);
                    i2 += iComputeDoubleSize;
                    break;
                case 31:
                    iComputeDoubleSize = SchemaUtil.computeSizeFixed32List(iNumberAt, listAt(t2, jOffset), false);
                    i2 += iComputeDoubleSize;
                    break;
                case 32:
                    iComputeDoubleSize = SchemaUtil.computeSizeFixed64List(iNumberAt, listAt(t2, jOffset), false);
                    i2 += iComputeDoubleSize;
                    break;
                case 33:
                    iComputeDoubleSize = SchemaUtil.computeSizeSInt32List(iNumberAt, listAt(t2, jOffset), false);
                    i2 += iComputeDoubleSize;
                    break;
                case 34:
                    iComputeDoubleSize = SchemaUtil.computeSizeSInt64List(iNumberAt, listAt(t2, jOffset), false);
                    i2 += iComputeDoubleSize;
                    break;
                case 35:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i4, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i2 += iComputeDoubleSize;
                        break;
                    }
                case 36:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i4, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i2 += iComputeDoubleSize;
                        break;
                    }
                case 37:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeInt64ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i4, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i2 += iComputeDoubleSize;
                        break;
                    }
                case 38:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeUInt64ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i4, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i2 += iComputeDoubleSize;
                        break;
                    }
                case 39:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeInt32ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i4, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i2 += iComputeDoubleSize;
                        break;
                    }
                case 40:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i4, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i2 += iComputeDoubleSize;
                        break;
                    }
                case 41:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i4, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i2 += iComputeDoubleSize;
                        break;
                    }
                case 42:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeBoolListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i4, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i2 += iComputeDoubleSize;
                        break;
                    }
                case 43:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeUInt32ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i4, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i2 += iComputeDoubleSize;
                        break;
                    }
                case 44:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeEnumListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i4, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i2 += iComputeDoubleSize;
                        break;
                    }
                case 45:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i4, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i2 += iComputeDoubleSize;
                        break;
                    }
                case 46:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i4, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i2 += iComputeDoubleSize;
                        break;
                    }
                case 47:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeSInt32ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i4, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i2 += iComputeDoubleSize;
                        break;
                    }
                case 48:
                    iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeSInt64ListNoTag((List) unsafe.getObject(t2, jOffset));
                    if (iComputeSizeFixed64ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i4, iComputeSizeFixed64ListNoTag);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(iComputeSizeFixed64ListNoTag);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + iComputeSizeFixed64ListNoTag;
                        i2 += iComputeDoubleSize;
                        break;
                    }
                case 49:
                    iComputeDoubleSize = SchemaUtil.computeSizeGroupList(iNumberAt, listAt(t2, jOffset), getMessageFieldSchema(i3));
                    i2 += iComputeDoubleSize;
                    break;
                case 50:
                    iComputeDoubleSize = this.mapFieldSchema.getSerializedSize(iNumberAt, UnsafeUtil.getObject(t2, jOffset), getMapFieldDefaultEntry(i3));
                    i2 += iComputeDoubleSize;
                    break;
                case 51:
                    if (isOneofPresent(t2, iNumberAt, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeDoubleSize(iNumberAt, 0.0d);
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (isOneofPresent(t2, iNumberAt, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeFloatSize(iNumberAt, 0.0f);
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (isOneofPresent(t2, iNumberAt, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeInt64Size(iNumberAt, oneofLongAt(t2, jOffset));
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (isOneofPresent(t2, iNumberAt, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeUInt64Size(iNumberAt, oneofLongAt(t2, jOffset));
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (isOneofPresent(t2, iNumberAt, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeInt32Size(iNumberAt, oneofIntAt(t2, jOffset));
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (isOneofPresent(t2, iNumberAt, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeFixed64Size(iNumberAt, 0L);
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (isOneofPresent(t2, iNumberAt, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeFixed32Size(iNumberAt, 0);
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (isOneofPresent(t2, iNumberAt, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeBoolSize(iNumberAt, true);
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (isOneofPresent(t2, iNumberAt, i3)) {
                        Object object2 = UnsafeUtil.getObject(t2, jOffset);
                        iComputeDoubleSize = object2 instanceof ByteString ? CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) object2) : CodedOutputStream.computeStringSize(iNumberAt, (String) object2);
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (isOneofPresent(t2, iNumberAt, i3)) {
                        iComputeDoubleSize = SchemaUtil.computeSizeMessage(iNumberAt, UnsafeUtil.getObject(t2, jOffset), getMessageFieldSchema(i3));
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (isOneofPresent(t2, iNumberAt, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) UnsafeUtil.getObject(t2, jOffset));
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (isOneofPresent(t2, iNumberAt, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeUInt32Size(iNumberAt, oneofIntAt(t2, jOffset));
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (isOneofPresent(t2, iNumberAt, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeEnumSize(iNumberAt, oneofIntAt(t2, jOffset));
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (isOneofPresent(t2, iNumberAt, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeSFixed32Size(iNumberAt, 0);
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (isOneofPresent(t2, iNumberAt, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeSFixed64Size(iNumberAt, 0L);
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (isOneofPresent(t2, iNumberAt, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeSInt32Size(iNumberAt, oneofIntAt(t2, jOffset));
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (isOneofPresent(t2, iNumberAt, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeSInt64Size(iNumberAt, oneofLongAt(t2, jOffset));
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (isOneofPresent(t2, iNumberAt, i3)) {
                        iComputeDoubleSize = CodedOutputStream.computeGroupSize(iNumberAt, (MessageLite) UnsafeUtil.getObject(t2, jOffset), getMessageFieldSchema(i3));
                        i2 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
            }
        }
        return i2 + getUnknownFieldsSerializedSize(this.unknownFieldSchema, t2);
    }

    private <UT, UB> int getUnknownFieldsSerializedSize(UnknownFieldSchema<UT, UB> unknownFieldSchema, T t2) {
        return unknownFieldSchema.getSerializedSize(unknownFieldSchema.getFromMessage(t2));
    }

    private static <T> int intAt(T t2, long j2) {
        return UnsafeUtil.getInt(t2, j2);
    }

    private static boolean isEnforceUtf8(int i2) {
        return (i2 & 536870912) != 0;
    }

    private boolean isFieldPresent(T t2, int i2, int i3, int i4) {
        return this.proto3 ? isFieldPresent(t2, i2) : (i3 & i4) != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <N> boolean isListInitialized(Object obj, int i2, int i3) {
        List list = (List) UnsafeUtil.getObject(obj, offset(i2));
        if (list.isEmpty()) {
            return true;
        }
        Schema messageFieldSchema = getMessageFieldSchema(i3);
        for (int i4 = 0; i4 < list.size(); i4++) {
            if (!messageFieldSchema.isInitialized(list.get(i4))) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8, types: [androidx.datastore.preferences.protobuf.Schema] */
    private boolean isMapInitialized(T t2, int i2, int i3) {
        Map<?, ?> mapForMapData = this.mapFieldSchema.forMapData(UnsafeUtil.getObject(t2, offset(i2)));
        if (mapForMapData.isEmpty()) {
            return true;
        }
        if (this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(i3)).valueType.getJavaType() != WireFormat.JavaType.MESSAGE) {
            return true;
        }
        ?? SchemaFor = 0;
        for (Object obj : mapForMapData.values()) {
            SchemaFor = SchemaFor;
            if (SchemaFor == 0) {
                SchemaFor = Protobuf.getInstance().schemaFor((Class) obj.getClass());
            }
            if (!SchemaFor.isInitialized(obj)) {
                return false;
            }
        }
        return true;
    }

    private boolean isOneofCaseEqual(T t2, T t3, int i2) {
        long jPresenceMaskAndOffsetAt = presenceMaskAndOffsetAt(i2) & OFFSET_MASK;
        return UnsafeUtil.getInt(t2, jPresenceMaskAndOffsetAt) == UnsafeUtil.getInt(t3, jPresenceMaskAndOffsetAt);
    }

    private boolean isOneofPresent(T t2, int i2, int i3) {
        return UnsafeUtil.getInt(t2, (long) (presenceMaskAndOffsetAt(i3) & OFFSET_MASK)) == i2;
    }

    private static boolean isRequired(int i2) {
        return (i2 & 268435456) != 0;
    }

    private static List<?> listAt(Object obj, long j2) {
        return (List) UnsafeUtil.getObject(obj, j2);
    }

    private static <T> long longAt(T t2, long j2) {
        return UnsafeUtil.getLong(t2, j2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0077, code lost:
    
        r0 = r16.checkInitializedCount;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x007b, code lost:
    
        if (r0 >= r16.repeatedFieldOffsetStart) goto L359;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x007d, code lost:
    
        r13 = filterMapUnknownEnumValues(r19, r16.intArray[r0], r13, r17);
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:363:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0088, code lost:
    
        if (r13 == null) goto L363;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x008a, code lost:
    
        r17.setBuilderToMessage(r19, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x008d, code lost:
    
        return;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private <UT, UB, ET extends androidx.datastore.preferences.protobuf.FieldSet.FieldDescriptorLite<ET>> void mergeFromHelper(androidx.datastore.preferences.protobuf.UnknownFieldSchema<UT, UB> r17, androidx.datastore.preferences.protobuf.ExtensionSchema<ET> r18, T r19, androidx.datastore.preferences.protobuf.Reader r20, androidx.datastore.preferences.protobuf.ExtensionRegistryLite r21) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1720
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.mergeFromHelper(androidx.datastore.preferences.protobuf.UnknownFieldSchema, androidx.datastore.preferences.protobuf.ExtensionSchema, java.lang.Object, androidx.datastore.preferences.protobuf.Reader, androidx.datastore.preferences.protobuf.ExtensionRegistryLite):void");
    }

    private final <K, V> void mergeMap(Object obj, int i2, Object obj2, ExtensionRegistryLite extensionRegistryLite, Reader reader) throws IOException {
        long jOffset = offset(typeAndOffsetAt(i2));
        Object object = UnsafeUtil.getObject(obj, jOffset);
        if (object == null) {
            object = this.mapFieldSchema.newMapField(obj2);
            UnsafeUtil.putObject(obj, jOffset, object);
        } else if (this.mapFieldSchema.isImmutable(object)) {
            Object objNewMapField = this.mapFieldSchema.newMapField(obj2);
            this.mapFieldSchema.mergeFrom(objNewMapField, object);
            UnsafeUtil.putObject(obj, jOffset, objNewMapField);
            object = objNewMapField;
        }
        reader.readMap(this.mapFieldSchema.forMutableMapData(object), this.mapFieldSchema.forMapMetadata(obj2), extensionRegistryLite);
    }

    private void mergeMessage(T t2, T t3, int i2) {
        long jOffset = offset(typeAndOffsetAt(i2));
        if (isFieldPresent(t3, i2)) {
            Object object = UnsafeUtil.getObject(t2, jOffset);
            Object object2 = UnsafeUtil.getObject(t3, jOffset);
            if (object != null && object2 != null) {
                UnsafeUtil.putObject(t2, jOffset, Internal.mergeMessage(object, object2));
                setFieldPresent(t2, i2);
            } else if (object2 != null) {
                UnsafeUtil.putObject(t2, jOffset, object2);
                setFieldPresent(t2, i2);
            }
        }
    }

    private void mergeOneofMessage(T t2, T t3, int i2) {
        int iTypeAndOffsetAt = typeAndOffsetAt(i2);
        int iNumberAt = numberAt(i2);
        long jOffset = offset(iTypeAndOffsetAt);
        if (isOneofPresent(t3, iNumberAt, i2)) {
            Object object = UnsafeUtil.getObject(t2, jOffset);
            Object object2 = UnsafeUtil.getObject(t3, jOffset);
            if (object != null && object2 != null) {
                UnsafeUtil.putObject(t2, jOffset, Internal.mergeMessage(object, object2));
                setOneofPresent(t2, iNumberAt, i2);
            } else if (object2 != null) {
                UnsafeUtil.putObject(t2, jOffset, object2);
                setOneofPresent(t2, iNumberAt, i2);
            }
        }
    }

    private void mergeSingleField(T t2, T t3, int i2) {
        int iTypeAndOffsetAt = typeAndOffsetAt(i2);
        long jOffset = offset(iTypeAndOffsetAt);
        int iNumberAt = numberAt(i2);
        switch (type(iTypeAndOffsetAt)) {
            case 0:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.putDouble(t2, jOffset, UnsafeUtil.getDouble(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 1:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.putFloat(t2, jOffset, UnsafeUtil.getFloat(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 2:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.putLong(t2, jOffset, UnsafeUtil.getLong(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 3:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.putLong(t2, jOffset, UnsafeUtil.getLong(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 4:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.putInt(t2, jOffset, UnsafeUtil.getInt(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 5:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.putLong(t2, jOffset, UnsafeUtil.getLong(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 6:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.putInt(t2, jOffset, UnsafeUtil.getInt(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 7:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.putBoolean(t2, jOffset, UnsafeUtil.getBoolean(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 8:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.putObject(t2, jOffset, UnsafeUtil.getObject(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 9:
                mergeMessage(t2, t3, i2);
                break;
            case 10:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.putObject(t2, jOffset, UnsafeUtil.getObject(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 11:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.putInt(t2, jOffset, UnsafeUtil.getInt(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 12:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.putInt(t2, jOffset, UnsafeUtil.getInt(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 13:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.putInt(t2, jOffset, UnsafeUtil.getInt(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 14:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.putLong(t2, jOffset, UnsafeUtil.getLong(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 15:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.putInt(t2, jOffset, UnsafeUtil.getInt(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 16:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.putLong(t2, jOffset, UnsafeUtil.getLong(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 17:
                mergeMessage(t2, t3, i2);
                break;
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
                this.listFieldSchema.mergeListsAt(t2, t3, jOffset);
                break;
            case 50:
                SchemaUtil.mergeMap(this.mapFieldSchema, t2, t3, jOffset);
                break;
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
                if (isOneofPresent(t3, iNumberAt, i2)) {
                    UnsafeUtil.putObject(t2, jOffset, UnsafeUtil.getObject(t3, jOffset));
                    setOneofPresent(t2, iNumberAt, i2);
                    break;
                }
                break;
            case 60:
                mergeOneofMessage(t2, t3, i2);
                break;
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
                if (isOneofPresent(t3, iNumberAt, i2)) {
                    UnsafeUtil.putObject(t2, jOffset, UnsafeUtil.getObject(t3, jOffset));
                    setOneofPresent(t2, iNumberAt, i2);
                    break;
                }
                break;
            case 68:
                mergeOneofMessage(t2, t3, i2);
                break;
        }
    }

    public static <T> MessageSchema<T> newSchema(Class<T> cls, MessageInfo messageInfo, NewInstanceSchema newInstanceSchema, ListFieldSchema listFieldSchema, UnknownFieldSchema<?, ?> unknownFieldSchema, ExtensionSchema<?> extensionSchema, MapFieldSchema mapFieldSchema) {
        return messageInfo instanceof RawMessageInfo ? newSchemaForRawMessageInfo((RawMessageInfo) messageInfo, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema) : newSchemaForMessageInfo((StructuralMessageInfo) messageInfo, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
    }

    public static <T> MessageSchema<T> newSchemaForMessageInfo(StructuralMessageInfo structuralMessageInfo, NewInstanceSchema newInstanceSchema, ListFieldSchema listFieldSchema, UnknownFieldSchema<?, ?> unknownFieldSchema, ExtensionSchema<?> extensionSchema, MapFieldSchema mapFieldSchema) {
        int fieldNumber;
        int fieldNumber2;
        int i2;
        boolean z2 = structuralMessageInfo.getSyntax() == ProtoSyntax.PROTO3;
        FieldInfo[] fields = structuralMessageInfo.getFields();
        if (fields.length == 0) {
            fieldNumber = 0;
            fieldNumber2 = 0;
        } else {
            fieldNumber = fields[0].getFieldNumber();
            fieldNumber2 = fields[fields.length - 1].getFieldNumber();
        }
        int length = fields.length;
        int[] iArr = new int[length * 3];
        Object[] objArr = new Object[length * 2];
        int i3 = 0;
        int i4 = 0;
        for (FieldInfo fieldInfo : fields) {
            if (fieldInfo.getType() == FieldType.MAP) {
                i3++;
            } else if (fieldInfo.getType().id() >= 18 && fieldInfo.getType().id() <= 49) {
                i4++;
            }
        }
        int[] iArr2 = i3 > 0 ? new int[i3] : null;
        int[] iArr3 = i4 > 0 ? new int[i4] : null;
        int[] checkInitialized = structuralMessageInfo.getCheckInitialized();
        if (checkInitialized == null) {
            checkInitialized = EMPTY_INT_ARRAY;
        }
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        while (i5 < fields.length) {
            FieldInfo fieldInfo2 = fields[i5];
            int fieldNumber3 = fieldInfo2.getFieldNumber();
            storeFieldData(fieldInfo2, iArr, i6, z2, objArr);
            if (i7 < checkInitialized.length && checkInitialized[i7] == fieldNumber3) {
                checkInitialized[i7] = i6;
                i7++;
            }
            if (fieldInfo2.getType() == FieldType.MAP) {
                iArr2[i8] = i6;
                i8++;
            } else {
                if (fieldInfo2.getType().id() >= 18 && fieldInfo2.getType().id() <= 49) {
                    i2 = i6;
                    iArr3[i9] = (int) UnsafeUtil.objectFieldOffset(fieldInfo2.getField());
                    i9++;
                }
                i5++;
                i6 = i2 + 3;
            }
            i2 = i6;
            i5++;
            i6 = i2 + 3;
        }
        if (iArr2 == null) {
            iArr2 = EMPTY_INT_ARRAY;
        }
        if (iArr3 == null) {
            iArr3 = EMPTY_INT_ARRAY;
        }
        int[] iArr4 = new int[checkInitialized.length + iArr2.length + iArr3.length];
        System.arraycopy(checkInitialized, 0, iArr4, 0, checkInitialized.length);
        System.arraycopy(iArr2, 0, iArr4, checkInitialized.length, iArr2.length);
        System.arraycopy(iArr3, 0, iArr4, checkInitialized.length + iArr2.length, iArr3.length);
        return new MessageSchema<>(iArr, objArr, fieldNumber, fieldNumber2, structuralMessageInfo.getDefaultInstance(), z2, true, iArr4, checkInitialized.length, checkInitialized.length + iArr2.length, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
    }

    /* JADX WARN: Removed duplicated region for block: B:124:0x0277  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x027a  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0292  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0295  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x033c  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0391  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x039e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static <T> androidx.datastore.preferences.protobuf.MessageSchema<T> newSchemaForRawMessageInfo(androidx.datastore.preferences.protobuf.RawMessageInfo r36, androidx.datastore.preferences.protobuf.NewInstanceSchema r37, androidx.datastore.preferences.protobuf.ListFieldSchema r38, androidx.datastore.preferences.protobuf.UnknownFieldSchema<?, ?> r39, androidx.datastore.preferences.protobuf.ExtensionSchema<?> r40, androidx.datastore.preferences.protobuf.MapFieldSchema r41) {
        /*
            Method dump skipped, instructions count: 1041
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.newSchemaForRawMessageInfo(androidx.datastore.preferences.protobuf.RawMessageInfo, androidx.datastore.preferences.protobuf.NewInstanceSchema, androidx.datastore.preferences.protobuf.ListFieldSchema, androidx.datastore.preferences.protobuf.UnknownFieldSchema, androidx.datastore.preferences.protobuf.ExtensionSchema, androidx.datastore.preferences.protobuf.MapFieldSchema):androidx.datastore.preferences.protobuf.MessageSchema");
    }

    private int numberAt(int i2) {
        return this.buffer[i2];
    }

    private static long offset(int i2) {
        return i2 & OFFSET_MASK;
    }

    private static <T> boolean oneofBooleanAt(T t2, long j2) {
        return ((Boolean) UnsafeUtil.getObject(t2, j2)).booleanValue();
    }

    private static <T> double oneofDoubleAt(T t2, long j2) {
        return ((Double) UnsafeUtil.getObject(t2, j2)).doubleValue();
    }

    private static <T> float oneofFloatAt(T t2, long j2) {
        return ((Float) UnsafeUtil.getObject(t2, j2)).floatValue();
    }

    private static <T> int oneofIntAt(T t2, long j2) {
        return ((Integer) UnsafeUtil.getObject(t2, j2)).intValue();
    }

    private static <T> long oneofLongAt(T t2, long j2) {
        return ((Long) UnsafeUtil.getObject(t2, j2)).longValue();
    }

    private <K, V> int parseMapField(T t2, byte[] bArr, int i2, int i3, int i4, long j2, ArrayDecoders.Registers registers) throws IOException {
        Unsafe unsafe = UNSAFE;
        Object mapFieldDefaultEntry = getMapFieldDefaultEntry(i4);
        Object object = unsafe.getObject(t2, j2);
        if (this.mapFieldSchema.isImmutable(object)) {
            Object objNewMapField = this.mapFieldSchema.newMapField(mapFieldDefaultEntry);
            this.mapFieldSchema.mergeFrom(objNewMapField, object);
            unsafe.putObject(t2, j2, objNewMapField);
            object = objNewMapField;
        }
        return decodeMapEntry(bArr, i2, i3, this.mapFieldSchema.forMapMetadata(mapFieldDefaultEntry), this.mapFieldSchema.forMutableMapData(object), registers);
    }

    private int parseOneofField(T t2, byte[] bArr, int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j2, int i9, ArrayDecoders.Registers registers) throws IOException {
        Unsafe unsafe = UNSAFE;
        long j3 = this.buffer[i9 + 2] & OFFSET_MASK;
        switch (i8) {
            case 51:
                if (i6 == 1) {
                    unsafe.putObject(t2, j2, Double.valueOf(ArrayDecoders.decodeDouble(bArr, i2)));
                    int i10 = i2 + 8;
                    unsafe.putInt(t2, j3, i5);
                    return i10;
                }
                break;
            case 52:
                if (i6 == 5) {
                    unsafe.putObject(t2, j2, Float.valueOf(ArrayDecoders.decodeFloat(bArr, i2)));
                    int i11 = i2 + 4;
                    unsafe.putInt(t2, j3, i5);
                    return i11;
                }
                break;
            case 53:
            case 54:
                if (i6 == 0) {
                    int iDecodeVarint64 = ArrayDecoders.decodeVarint64(bArr, i2, registers);
                    unsafe.putObject(t2, j2, Long.valueOf(registers.long1));
                    unsafe.putInt(t2, j3, i5);
                    return iDecodeVarint64;
                }
                break;
            case 55:
            case 62:
                if (i6 == 0) {
                    int iDecodeVarint32 = ArrayDecoders.decodeVarint32(bArr, i2, registers);
                    unsafe.putObject(t2, j2, Integer.valueOf(registers.int1));
                    unsafe.putInt(t2, j3, i5);
                    return iDecodeVarint32;
                }
                break;
            case 56:
            case 65:
                if (i6 == 1) {
                    unsafe.putObject(t2, j2, Long.valueOf(ArrayDecoders.decodeFixed64(bArr, i2)));
                    int i12 = i2 + 8;
                    unsafe.putInt(t2, j3, i5);
                    return i12;
                }
                break;
            case 57:
            case 64:
                if (i6 == 5) {
                    unsafe.putObject(t2, j2, Integer.valueOf(ArrayDecoders.decodeFixed32(bArr, i2)));
                    int i13 = i2 + 4;
                    unsafe.putInt(t2, j3, i5);
                    return i13;
                }
                break;
            case 58:
                if (i6 == 0) {
                    int iDecodeVarint642 = ArrayDecoders.decodeVarint64(bArr, i2, registers);
                    unsafe.putObject(t2, j2, Boolean.valueOf(registers.long1 != 0));
                    unsafe.putInt(t2, j3, i5);
                    return iDecodeVarint642;
                }
                break;
            case 59:
                if (i6 == 2) {
                    int iDecodeVarint322 = ArrayDecoders.decodeVarint32(bArr, i2, registers);
                    int i14 = registers.int1;
                    if (i14 == 0) {
                        unsafe.putObject(t2, j2, "");
                    } else {
                        if ((i7 & 536870912) != 0 && !Utf8.isValidUtf8(bArr, iDecodeVarint322, iDecodeVarint322 + i14)) {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                        unsafe.putObject(t2, j2, new String(bArr, iDecodeVarint322, i14, Internal.UTF_8));
                        iDecodeVarint322 += i14;
                    }
                    unsafe.putInt(t2, j3, i5);
                    return iDecodeVarint322;
                }
                break;
            case 60:
                if (i6 == 2) {
                    int iDecodeMessageField = ArrayDecoders.decodeMessageField(getMessageFieldSchema(i9), bArr, i2, i3, registers);
                    Object object = unsafe.getInt(t2, j3) == i5 ? unsafe.getObject(t2, j2) : null;
                    if (object == null) {
                        unsafe.putObject(t2, j2, registers.object1);
                    } else {
                        unsafe.putObject(t2, j2, Internal.mergeMessage(object, registers.object1));
                    }
                    unsafe.putInt(t2, j3, i5);
                    return iDecodeMessageField;
                }
                break;
            case 61:
                if (i6 == 2) {
                    int iDecodeBytes = ArrayDecoders.decodeBytes(bArr, i2, registers);
                    unsafe.putObject(t2, j2, registers.object1);
                    unsafe.putInt(t2, j3, i5);
                    return iDecodeBytes;
                }
                break;
            case 63:
                if (i6 == 0) {
                    int iDecodeVarint323 = ArrayDecoders.decodeVarint32(bArr, i2, registers);
                    int i15 = registers.int1;
                    Internal.EnumVerifier enumFieldVerifier = getEnumFieldVerifier(i9);
                    if (enumFieldVerifier == null || enumFieldVerifier.isInRange(i15)) {
                        unsafe.putObject(t2, j2, Integer.valueOf(i15));
                        unsafe.putInt(t2, j3, i5);
                    } else {
                        getMutableUnknownFields(t2).storeField(i4, Long.valueOf(i15));
                    }
                    return iDecodeVarint323;
                }
                break;
            case 66:
                if (i6 == 0) {
                    int iDecodeVarint324 = ArrayDecoders.decodeVarint32(bArr, i2, registers);
                    unsafe.putObject(t2, j2, Integer.valueOf(CodedInputStream.decodeZigZag32(registers.int1)));
                    unsafe.putInt(t2, j3, i5);
                    return iDecodeVarint324;
                }
                break;
            case 67:
                if (i6 == 0) {
                    int iDecodeVarint643 = ArrayDecoders.decodeVarint64(bArr, i2, registers);
                    unsafe.putObject(t2, j2, Long.valueOf(CodedInputStream.decodeZigZag64(registers.long1)));
                    unsafe.putInt(t2, j3, i5);
                    return iDecodeVarint643;
                }
                break;
            case 68:
                if (i6 == 3) {
                    int iDecodeGroupField = ArrayDecoders.decodeGroupField(getMessageFieldSchema(i9), bArr, i2, i3, (i4 & (-8)) | 4, registers);
                    Object object2 = unsafe.getInt(t2, j3) == i5 ? unsafe.getObject(t2, j2) : null;
                    if (object2 == null) {
                        unsafe.putObject(t2, j2, registers.object1);
                    } else {
                        unsafe.putObject(t2, j2, Internal.mergeMessage(object2, registers.object1));
                    }
                    unsafe.putInt(t2, j3, i5);
                    return iDecodeGroupField;
                }
                break;
        }
        return i2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:102:0x022b, code lost:
    
        if (r0 != r15) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x022e, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01de, code lost:
    
        if (r0 != r15) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x020c, code lost:
    
        if (r0 != r15) goto L106;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:18:0x005c. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v12, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int parseProto3Message(T r28, byte[] r29, int r30, int r31, androidx.datastore.preferences.protobuf.ArrayDecoders.Registers r32) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 642
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.parseProto3Message(java.lang.Object, byte[], int, int, androidx.datastore.preferences.protobuf.ArrayDecoders$Registers):int");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private int parseRepeatedField(T t2, byte[] bArr, int i2, int i3, int i4, int i5, int i6, int i7, long j2, int i8, long j3, ArrayDecoders.Registers registers) throws IOException {
        int iDecodeVarint32List;
        Unsafe unsafe = UNSAFE;
        Internal.ProtobufList protobufListMutableCopyWithCapacity2 = (Internal.ProtobufList) unsafe.getObject(t2, j3);
        if (!protobufListMutableCopyWithCapacity2.isModifiable()) {
            int size = protobufListMutableCopyWithCapacity2.size();
            protobufListMutableCopyWithCapacity2 = protobufListMutableCopyWithCapacity2.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
            unsafe.putObject(t2, j3, protobufListMutableCopyWithCapacity2);
        }
        switch (i8) {
            case 18:
            case 35:
                if (i6 == 2) {
                    return ArrayDecoders.decodePackedDoubleList(bArr, i2, protobufListMutableCopyWithCapacity2, registers);
                }
                if (i6 == 1) {
                    return ArrayDecoders.decodeDoubleList(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 19:
            case 36:
                if (i6 == 2) {
                    return ArrayDecoders.decodePackedFloatList(bArr, i2, protobufListMutableCopyWithCapacity2, registers);
                }
                if (i6 == 5) {
                    return ArrayDecoders.decodeFloatList(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i6 == 2) {
                    return ArrayDecoders.decodePackedVarint64List(bArr, i2, protobufListMutableCopyWithCapacity2, registers);
                }
                if (i6 == 0) {
                    return ArrayDecoders.decodeVarint64List(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i6 == 2) {
                    return ArrayDecoders.decodePackedVarint32List(bArr, i2, protobufListMutableCopyWithCapacity2, registers);
                }
                if (i6 == 0) {
                    return ArrayDecoders.decodeVarint32List(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i6 == 2) {
                    return ArrayDecoders.decodePackedFixed64List(bArr, i2, protobufListMutableCopyWithCapacity2, registers);
                }
                if (i6 == 1) {
                    return ArrayDecoders.decodeFixed64List(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i6 == 2) {
                    return ArrayDecoders.decodePackedFixed32List(bArr, i2, protobufListMutableCopyWithCapacity2, registers);
                }
                if (i6 == 5) {
                    return ArrayDecoders.decodeFixed32List(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 25:
            case 42:
                if (i6 == 2) {
                    return ArrayDecoders.decodePackedBoolList(bArr, i2, protobufListMutableCopyWithCapacity2, registers);
                }
                if (i6 == 0) {
                    return ArrayDecoders.decodeBoolList(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 26:
                if (i6 == 2) {
                    return (j2 & IjkMediaMeta.AV_CH_STEREO_LEFT) == 0 ? ArrayDecoders.decodeStringList(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers) : ArrayDecoders.decodeStringListRequireUtf8(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 27:
                if (i6 == 2) {
                    return ArrayDecoders.decodeMessageList(getMessageFieldSchema(i7), i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 28:
                if (i6 == 2) {
                    return ArrayDecoders.decodeBytesList(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 30:
            case 44:
                if (i6 == 2) {
                    iDecodeVarint32List = ArrayDecoders.decodePackedVarint32List(bArr, i2, protobufListMutableCopyWithCapacity2, registers);
                } else if (i6 == 0) {
                    iDecodeVarint32List = ArrayDecoders.decodeVarint32List(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) t2;
                UnknownFieldSetLite unknownFieldSetLite = generatedMessageLite.unknownFields;
                if (unknownFieldSetLite == UnknownFieldSetLite.getDefaultInstance()) {
                    unknownFieldSetLite = null;
                }
                UnknownFieldSetLite unknownFieldSetLite2 = (UnknownFieldSetLite) SchemaUtil.filterUnknownEnumList(i5, (List<Integer>) protobufListMutableCopyWithCapacity2, getEnumFieldVerifier(i7), unknownFieldSetLite, (UnknownFieldSchema<UT, UnknownFieldSetLite>) this.unknownFieldSchema);
                if (unknownFieldSetLite2 != null) {
                    generatedMessageLite.unknownFields = unknownFieldSetLite2;
                }
                return iDecodeVarint32List;
            case 33:
            case 47:
                if (i6 == 2) {
                    return ArrayDecoders.decodePackedSInt32List(bArr, i2, protobufListMutableCopyWithCapacity2, registers);
                }
                if (i6 == 0) {
                    return ArrayDecoders.decodeSInt32List(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 34:
            case 48:
                if (i6 == 2) {
                    return ArrayDecoders.decodePackedSInt64List(bArr, i2, protobufListMutableCopyWithCapacity2, registers);
                }
                if (i6 == 0) {
                    return ArrayDecoders.decodeSInt64List(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 49:
                if (i6 == 3) {
                    return ArrayDecoders.decodeGroupList(getMessageFieldSchema(i7), i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
        }
        return i2;
    }

    private int positionForFieldNumber(int i2) {
        if (i2 < this.minFieldNumber || i2 > this.maxFieldNumber) {
            return -1;
        }
        return slowPositionForFieldNumber(i2, 0);
    }

    private int presenceMaskAndOffsetAt(int i2) {
        return this.buffer[i2 + 2];
    }

    private <E> void readGroupList(Object obj, long j2, Reader reader, Schema<E> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        reader.readGroupList(this.listFieldSchema.mutableListAt(obj, j2), schema, extensionRegistryLite);
    }

    private <E> void readMessageList(Object obj, int i2, Reader reader, Schema<E> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        reader.readMessageList(this.listFieldSchema.mutableListAt(obj, offset(i2)), schema, extensionRegistryLite);
    }

    private void readString(Object obj, int i2, Reader reader) throws IOException {
        if (isEnforceUtf8(i2)) {
            UnsafeUtil.putObject(obj, offset(i2), reader.readStringRequireUtf8());
        } else if (this.lite) {
            UnsafeUtil.putObject(obj, offset(i2), reader.readString());
        } else {
            UnsafeUtil.putObject(obj, offset(i2), reader.readBytes());
        }
    }

    private void readStringList(Object obj, int i2, Reader reader) throws IOException {
        if (isEnforceUtf8(i2)) {
            reader.readStringListRequireUtf8(this.listFieldSchema.mutableListAt(obj, offset(i2)));
        } else {
            reader.readStringList(this.listFieldSchema.mutableListAt(obj, offset(i2)));
        }
    }

    private static java.lang.reflect.Field reflectField(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            java.lang.reflect.Field[] declaredFields = cls.getDeclaredFields();
            for (java.lang.reflect.Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            throw new RuntimeException("Field " + str + " for " + cls.getName() + " not found. Known fields are " + Arrays.toString(declaredFields));
        }
    }

    private void setFieldPresent(T t2, int i2) {
        if (this.proto3) {
            return;
        }
        int iPresenceMaskAndOffsetAt = presenceMaskAndOffsetAt(i2);
        long j2 = iPresenceMaskAndOffsetAt & OFFSET_MASK;
        UnsafeUtil.putInt(t2, j2, UnsafeUtil.getInt(t2, j2) | (1 << (iPresenceMaskAndOffsetAt >>> 20)));
    }

    private void setOneofPresent(T t2, int i2, int i3) {
        UnsafeUtil.putInt(t2, presenceMaskAndOffsetAt(i3) & OFFSET_MASK, i2);
    }

    private int slowPositionForFieldNumber(int i2, int i3) {
        int length = (this.buffer.length / 3) - 1;
        while (i3 <= length) {
            int i4 = (length + i3) >>> 1;
            int i5 = i4 * 3;
            int iNumberAt = numberAt(i5);
            if (i2 == iNumberAt) {
                return i5;
            }
            if (i2 < iNumberAt) {
                length = i4 - 1;
            } else {
                i3 = i4 + 1;
            }
        }
        return -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00c5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void storeFieldData(androidx.datastore.preferences.protobuf.FieldInfo r8, int[] r9, int r10, boolean r11, java.lang.Object[] r12) {
        /*
            androidx.datastore.preferences.protobuf.OneofInfo r0 = r8.getOneof()
            r1 = 0
            if (r0 == 0) goto L27
            androidx.datastore.preferences.protobuf.FieldType r11 = r8.getType()
            int r11 = r11.id()
            int r11 = r11 + 51
            java.lang.reflect.Field r2 = r0.getValueField()
            long r2 = androidx.datastore.preferences.protobuf.UnsafeUtil.objectFieldOffset(r2)
            int r2 = (int) r2
            java.lang.reflect.Field r0 = r0.getCaseField()
            long r3 = androidx.datastore.preferences.protobuf.UnsafeUtil.objectFieldOffset(r0)
            int r0 = (int) r3
        L23:
            r3 = r2
            r2 = r0
            r0 = r1
            goto L73
        L27:
            androidx.datastore.preferences.protobuf.FieldType r0 = r8.getType()
            java.lang.reflect.Field r2 = r8.getField()
            long r2 = androidx.datastore.preferences.protobuf.UnsafeUtil.objectFieldOffset(r2)
            int r2 = (int) r2
            int r3 = r0.id()
            if (r11 != 0) goto L5d
            boolean r11 = r0.isList()
            if (r11 != 0) goto L5d
            boolean r11 = r0.isMap()
            if (r11 != 0) goto L5d
            java.lang.reflect.Field r11 = r8.getPresenceField()
            long r4 = androidx.datastore.preferences.protobuf.UnsafeUtil.objectFieldOffset(r11)
            int r0 = (int) r4
            int r11 = r8.getPresenceMask()
            int r11 = java.lang.Integer.numberOfTrailingZeros(r11)
            r7 = r0
            r0 = r11
            r11 = r3
            r3 = r2
            r2 = r7
            goto L73
        L5d:
            java.lang.reflect.Field r11 = r8.getCachedSizeField()
            if (r11 != 0) goto L68
            r0 = r1
            r11 = r3
            r3 = r2
            r2 = r0
            goto L73
        L68:
            java.lang.reflect.Field r11 = r8.getCachedSizeField()
            long r4 = androidx.datastore.preferences.protobuf.UnsafeUtil.objectFieldOffset(r11)
            int r0 = (int) r4
            r11 = r3
            goto L23
        L73:
            int r4 = r8.getFieldNumber()
            r9[r10] = r4
            int r4 = r10 + 1
            boolean r5 = r8.isEnforceUtf8()
            if (r5 == 0) goto L84
            r5 = 536870912(0x20000000, float:1.0842022E-19)
            goto L85
        L84:
            r5 = r1
        L85:
            boolean r6 = r8.isRequired()
            if (r6 == 0) goto L8d
            r1 = 268435456(0x10000000, float:2.524355E-29)
        L8d:
            r1 = r1 | r5
            int r11 = r11 << 20
            r11 = r11 | r1
            r11 = r11 | r3
            r9[r4] = r11
            int r11 = r10 + 2
            int r0 = r0 << 20
            r0 = r0 | r2
            r9[r11] = r0
            java.lang.Class r9 = r8.getMessageFieldClass()
            java.lang.Object r11 = r8.getMapDefaultEntry()
            if (r11 == 0) goto Lc5
            int r10 = r10 / 3
            int r10 = r10 * 2
            java.lang.Object r11 = r8.getMapDefaultEntry()
            r12[r10] = r11
            if (r9 == 0) goto Lb6
            int r10 = r10 + 1
            r12[r10] = r9
            goto Le2
        Lb6:
            androidx.datastore.preferences.protobuf.Internal$EnumVerifier r9 = r8.getEnumVerifier()
            if (r9 == 0) goto Le2
            int r10 = r10 + 1
            androidx.datastore.preferences.protobuf.Internal$EnumVerifier r8 = r8.getEnumVerifier()
            r12[r10] = r8
            goto Le2
        Lc5:
            if (r9 == 0) goto Ld0
            int r10 = r10 / 3
            int r10 = r10 * 2
            int r10 = r10 + 1
            r12[r10] = r9
            goto Le2
        Ld0:
            androidx.datastore.preferences.protobuf.Internal$EnumVerifier r9 = r8.getEnumVerifier()
            if (r9 == 0) goto Le2
            int r10 = r10 / 3
            int r10 = r10 * 2
            int r10 = r10 + 1
            androidx.datastore.preferences.protobuf.Internal$EnumVerifier r8 = r8.getEnumVerifier()
            r12[r10] = r8
        Le2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.storeFieldData(androidx.datastore.preferences.protobuf.FieldInfo, int[], int, boolean, java.lang.Object[]):void");
    }

    private static int type(int i2) {
        return (i2 & FIELD_TYPE_MASK) >>> 20;
    }

    private int typeAndOffsetAt(int i2) {
        return this.buffer[i2 + 1];
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void writeFieldsInAscendingOrderProto2(T r18, androidx.datastore.preferences.protobuf.Writer r19) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1352
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.writeFieldsInAscendingOrderProto2(java.lang.Object, androidx.datastore.preferences.protobuf.Writer):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void writeFieldsInAscendingOrderProto3(T r13, androidx.datastore.preferences.protobuf.Writer r14) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1584
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.writeFieldsInAscendingOrderProto3(java.lang.Object, androidx.datastore.preferences.protobuf.Writer):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void writeFieldsInDescendingOrder(T r11, androidx.datastore.preferences.protobuf.Writer r12) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1586
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.writeFieldsInDescendingOrder(java.lang.Object, androidx.datastore.preferences.protobuf.Writer):void");
    }

    private <K, V> void writeMapHelper(Writer writer, int i2, Object obj, int i3) throws IOException {
        if (obj != null) {
            writer.writeMap(i2, this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(i3)), this.mapFieldSchema.forMapData(obj));
        }
    }

    private void writeString(int i2, Object obj, Writer writer) throws IOException {
        if (obj instanceof String) {
            writer.writeString(i2, (String) obj);
        } else {
            writer.writeBytes(i2, (ByteString) obj);
        }
    }

    private <UT, UB> void writeUnknownInMessageTo(UnknownFieldSchema<UT, UB> unknownFieldSchema, T t2, Writer writer) throws IOException {
        unknownFieldSchema.writeTo(unknownFieldSchema.getFromMessage(t2), writer);
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public boolean equals(T t2, T t3) {
        int length = this.buffer.length;
        for (int i2 = 0; i2 < length; i2 += 3) {
            if (!equals(t2, t3, i2)) {
                return false;
            }
        }
        if (!this.unknownFieldSchema.getFromMessage(t2).equals(this.unknownFieldSchema.getFromMessage(t3))) {
            return false;
        }
        if (this.hasExtensions) {
            return this.extensionSchema.getExtensions(t2).equals(this.extensionSchema.getExtensions(t3));
        }
        return true;
    }

    public int getSchemaSize() {
        return this.buffer.length * 3;
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public int getSerializedSize(T t2) {
        return this.proto3 ? getSerializedSizeProto3(t2) : getSerializedSizeProto2(t2);
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public int hashCode(T t2) {
        int i2;
        int iHashLong;
        int length = this.buffer.length;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4 += 3) {
            int iTypeAndOffsetAt = typeAndOffsetAt(i4);
            int iNumberAt = numberAt(i4);
            long jOffset = offset(iTypeAndOffsetAt);
            int iHashCode = 37;
            switch (type(iTypeAndOffsetAt)) {
                case 0:
                    i2 = i3 * 53;
                    iHashLong = Internal.hashLong(Double.doubleToLongBits(UnsafeUtil.getDouble(t2, jOffset)));
                    i3 = i2 + iHashLong;
                    break;
                case 1:
                    i2 = i3 * 53;
                    iHashLong = Float.floatToIntBits(UnsafeUtil.getFloat(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 2:
                    i2 = i3 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.getLong(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 3:
                    i2 = i3 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.getLong(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 4:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.getInt(t2, jOffset);
                    i3 = i2 + iHashLong;
                    break;
                case 5:
                    i2 = i3 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.getLong(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 6:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.getInt(t2, jOffset);
                    i3 = i2 + iHashLong;
                    break;
                case 7:
                    i2 = i3 * 53;
                    iHashLong = Internal.hashBoolean(UnsafeUtil.getBoolean(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 8:
                    i2 = i3 * 53;
                    iHashLong = ((String) UnsafeUtil.getObject(t2, jOffset)).hashCode();
                    i3 = i2 + iHashLong;
                    break;
                case 9:
                    Object object = UnsafeUtil.getObject(t2, jOffset);
                    if (object != null) {
                        iHashCode = object.hashCode();
                    }
                    i3 = (i3 * 53) + iHashCode;
                    break;
                case 10:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.getObject(t2, jOffset).hashCode();
                    i3 = i2 + iHashLong;
                    break;
                case 11:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.getInt(t2, jOffset);
                    i3 = i2 + iHashLong;
                    break;
                case 12:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.getInt(t2, jOffset);
                    i3 = i2 + iHashLong;
                    break;
                case 13:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.getInt(t2, jOffset);
                    i3 = i2 + iHashLong;
                    break;
                case 14:
                    i2 = i3 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.getLong(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 15:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.getInt(t2, jOffset);
                    i3 = i2 + iHashLong;
                    break;
                case 16:
                    i2 = i3 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.getLong(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 17:
                    Object object2 = UnsafeUtil.getObject(t2, jOffset);
                    if (object2 != null) {
                        iHashCode = object2.hashCode();
                    }
                    i3 = (i3 * 53) + iHashCode;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.getObject(t2, jOffset).hashCode();
                    i3 = i2 + iHashLong;
                    break;
                case 50:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.getObject(t2, jOffset).hashCode();
                    i3 = i2 + iHashLong;
                    break;
                case 51:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = Internal.hashLong(Double.doubleToLongBits(oneofDoubleAt(t2, jOffset)));
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = Float.floatToIntBits(oneofFloatAt(t2, jOffset));
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = Internal.hashLong(oneofLongAt(t2, jOffset));
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = Internal.hashLong(oneofLongAt(t2, jOffset));
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = oneofIntAt(t2, jOffset);
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = Internal.hashLong(oneofLongAt(t2, jOffset));
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = oneofIntAt(t2, jOffset);
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = Internal.hashBoolean(oneofBooleanAt(t2, jOffset));
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = ((String) UnsafeUtil.getObject(t2, jOffset)).hashCode();
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = UnsafeUtil.getObject(t2, jOffset).hashCode();
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = UnsafeUtil.getObject(t2, jOffset).hashCode();
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = oneofIntAt(t2, jOffset);
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = oneofIntAt(t2, jOffset);
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = oneofIntAt(t2, jOffset);
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = Internal.hashLong(oneofLongAt(t2, jOffset));
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = oneofIntAt(t2, jOffset);
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = Internal.hashLong(oneofLongAt(t2, jOffset));
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = UnsafeUtil.getObject(t2, jOffset).hashCode();
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int iHashCode2 = (i3 * 53) + this.unknownFieldSchema.getFromMessage(t2).hashCode();
        return this.hasExtensions ? (iHashCode2 * 53) + this.extensionSchema.getExtensions(t2).hashCode() : iHashCode2;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0078  */
    @Override // androidx.datastore.preferences.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isInitialized(T r13) {
        /*
            r12 = this;
            r0 = -1
            r1 = 0
            r2 = r1
            r3 = r2
        L4:
            int r4 = r12.checkInitializedCount
            r5 = 1
            if (r2 >= r4) goto L94
            int[] r4 = r12.intArray
            r4 = r4[r2]
            int r6 = r12.numberAt(r4)
            int r7 = r12.typeAndOffsetAt(r4)
            boolean r8 = r12.proto3
            if (r8 != 0) goto L31
            int[] r8 = r12.buffer
            int r9 = r4 + 2
            r8 = r8[r9]
            r9 = 1048575(0xfffff, float:1.469367E-39)
            r9 = r9 & r8
            int r8 = r8 >>> 20
            int r5 = r5 << r8
            if (r9 == r0) goto L32
            sun.misc.Unsafe r0 = androidx.datastore.preferences.protobuf.MessageSchema.UNSAFE
            long r10 = (long) r9
            int r3 = r0.getInt(r13, r10)
            r0 = r9
            goto L32
        L31:
            r5 = r1
        L32:
            boolean r8 = isRequired(r7)
            if (r8 == 0) goto L3f
            boolean r8 = r12.isFieldPresent(r13, r4, r3, r5)
            if (r8 != 0) goto L3f
            return r1
        L3f:
            int r8 = type(r7)
            r9 = 9
            if (r8 == r9) goto L7f
            r9 = 17
            if (r8 == r9) goto L7f
            r5 = 27
            if (r8 == r5) goto L78
            r5 = 60
            if (r8 == r5) goto L67
            r5 = 68
            if (r8 == r5) goto L67
            r5 = 49
            if (r8 == r5) goto L78
            r5 = 50
            if (r8 == r5) goto L60
            goto L90
        L60:
            boolean r4 = r12.isMapInitialized(r13, r7, r4)
            if (r4 != 0) goto L90
            return r1
        L67:
            boolean r5 = r12.isOneofPresent(r13, r6, r4)
            if (r5 == 0) goto L90
            androidx.datastore.preferences.protobuf.Schema r4 = r12.getMessageFieldSchema(r4)
            boolean r4 = isInitialized(r13, r7, r4)
            if (r4 != 0) goto L90
            return r1
        L78:
            boolean r4 = r12.isListInitialized(r13, r7, r4)
            if (r4 != 0) goto L90
            return r1
        L7f:
            boolean r5 = r12.isFieldPresent(r13, r4, r3, r5)
            if (r5 == 0) goto L90
            androidx.datastore.preferences.protobuf.Schema r4 = r12.getMessageFieldSchema(r4)
            boolean r4 = isInitialized(r13, r7, r4)
            if (r4 != 0) goto L90
            return r1
        L90:
            int r2 = r2 + 1
            goto L4
        L94:
            boolean r0 = r12.hasExtensions
            if (r0 == 0) goto La5
            androidx.datastore.preferences.protobuf.ExtensionSchema<?> r0 = r12.extensionSchema
            androidx.datastore.preferences.protobuf.FieldSet r13 = r0.getExtensions(r13)
            boolean r13 = r13.isInitialized()
            if (r13 != 0) goto La5
            return r1
        La5:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.isInitialized(java.lang.Object):boolean");
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public void makeImmutable(T t2) {
        int i2;
        int i3 = this.checkInitializedCount;
        while (true) {
            i2 = this.repeatedFieldOffsetStart;
            if (i3 >= i2) {
                break;
            }
            long jOffset = offset(typeAndOffsetAt(this.intArray[i3]));
            Object object = UnsafeUtil.getObject(t2, jOffset);
            if (object != null) {
                UnsafeUtil.putObject(t2, jOffset, this.mapFieldSchema.toImmutable(object));
            }
            i3++;
        }
        int length = this.intArray.length;
        while (i2 < length) {
            this.listFieldSchema.makeImmutableListAt(t2, this.intArray[i2]);
            i2++;
        }
        this.unknownFieldSchema.makeImmutable(t2);
        if (this.hasExtensions) {
            this.extensionSchema.makeImmutable(t2);
        }
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public void mergeFrom(T t2, T t3) {
        t3.getClass();
        for (int i2 = 0; i2 < this.buffer.length; i2 += 3) {
            mergeSingleField(t2, t3, i2);
        }
        if (this.proto3) {
            return;
        }
        SchemaUtil.mergeUnknownFields(this.unknownFieldSchema, t2, t3);
        if (this.hasExtensions) {
            SchemaUtil.mergeExtensions(this.extensionSchema, t2, t3);
        }
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public T newInstance() {
        return (T) this.newInstanceSchema.newInstance(this.defaultInstance);
    }

    /* JADX WARN: Code restructure failed: missing block: B:119:0x0359, code lost:
    
        if (r0 != r11) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x035b, code lost:
    
        r15 = r30;
        r14 = r31;
        r12 = r32;
        r13 = r34;
        r11 = r35;
        r9 = r36;
        r1 = r17;
        r3 = r18;
        r7 = r19;
        r2 = r20;
        r6 = r22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x03a2, code lost:
    
        if (r0 != r15) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x03c5, code lost:
    
        if (r0 != r15) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x03c8, code lost:
    
        r2 = r0;
        r8 = r18;
        r0 = r35;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:25:0x008b. Please report as an issue. */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int parseProto2Message(T r31, byte[] r32, int r33, int r34, int r35, androidx.datastore.preferences.protobuf.ArrayDecoders.Registers r36) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1172
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.parseProto2Message(java.lang.Object, byte[], int, int, int, androidx.datastore.preferences.protobuf.ArrayDecoders$Registers):int");
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public void writeTo(T t2, Writer writer) throws IOException {
        if (writer.fieldOrder() == Writer.FieldOrder.DESCENDING) {
            writeFieldsInDescendingOrder(t2, writer);
        } else if (this.proto3) {
            writeFieldsInAscendingOrderProto3(t2, writer);
        } else {
            writeFieldsInAscendingOrderProto2(t2, writer);
        }
    }

    private boolean isFieldPresent(T t2, int i2) {
        if (this.proto3) {
            int iTypeAndOffsetAt = typeAndOffsetAt(i2);
            long jOffset = offset(iTypeAndOffsetAt);
            switch (type(iTypeAndOffsetAt)) {
                case 0:
                    return UnsafeUtil.getDouble(t2, jOffset) != 0.0d;
                case 1:
                    return UnsafeUtil.getFloat(t2, jOffset) != 0.0f;
                case 2:
                    return UnsafeUtil.getLong(t2, jOffset) != 0;
                case 3:
                    return UnsafeUtil.getLong(t2, jOffset) != 0;
                case 4:
                    return UnsafeUtil.getInt(t2, jOffset) != 0;
                case 5:
                    return UnsafeUtil.getLong(t2, jOffset) != 0;
                case 6:
                    return UnsafeUtil.getInt(t2, jOffset) != 0;
                case 7:
                    return UnsafeUtil.getBoolean(t2, jOffset);
                case 8:
                    Object object = UnsafeUtil.getObject(t2, jOffset);
                    if (object instanceof String) {
                        return !((String) object).isEmpty();
                    }
                    if (object instanceof ByteString) {
                        return !ByteString.EMPTY.equals(object);
                    }
                    throw new IllegalArgumentException();
                case 9:
                    return UnsafeUtil.getObject(t2, jOffset) != null;
                case 10:
                    return !ByteString.EMPTY.equals(UnsafeUtil.getObject(t2, jOffset));
                case 11:
                    return UnsafeUtil.getInt(t2, jOffset) != 0;
                case 12:
                    return UnsafeUtil.getInt(t2, jOffset) != 0;
                case 13:
                    return UnsafeUtil.getInt(t2, jOffset) != 0;
                case 14:
                    return UnsafeUtil.getLong(t2, jOffset) != 0;
                case 15:
                    return UnsafeUtil.getInt(t2, jOffset) != 0;
                case 16:
                    return UnsafeUtil.getLong(t2, jOffset) != 0;
                case 17:
                    return UnsafeUtil.getObject(t2, jOffset) != null;
                default:
                    throw new IllegalArgumentException();
            }
        }
        int iPresenceMaskAndOffsetAt = presenceMaskAndOffsetAt(i2);
        return (UnsafeUtil.getInt(t2, (long) (iPresenceMaskAndOffsetAt & OFFSET_MASK)) & (1 << (iPresenceMaskAndOffsetAt >>> 20))) != 0;
    }

    private int positionForFieldNumber(int i2, int i3) {
        if (i2 < this.minFieldNumber || i2 > this.maxFieldNumber) {
            return -1;
        }
        return slowPositionForFieldNumber(i2, i3);
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public void mergeFrom(T t2, Reader reader, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        extensionRegistryLite.getClass();
        mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, t2, reader, extensionRegistryLite);
    }

    private boolean equals(T t2, T t3, int i2) {
        int iTypeAndOffsetAt = typeAndOffsetAt(i2);
        long jOffset = offset(iTypeAndOffsetAt);
        switch (type(iTypeAndOffsetAt)) {
            case 0:
                if (arePresentForEquals(t2, t3, i2) && Double.doubleToLongBits(UnsafeUtil.getDouble(t2, jOffset)) == Double.doubleToLongBits(UnsafeUtil.getDouble(t3, jOffset))) {
                    break;
                }
                break;
            case 1:
                if (arePresentForEquals(t2, t3, i2) && Float.floatToIntBits(UnsafeUtil.getFloat(t2, jOffset)) == Float.floatToIntBits(UnsafeUtil.getFloat(t3, jOffset))) {
                    break;
                }
                break;
            case 2:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.getLong(t2, jOffset) == UnsafeUtil.getLong(t3, jOffset)) {
                    break;
                }
                break;
            case 3:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.getLong(t2, jOffset) == UnsafeUtil.getLong(t3, jOffset)) {
                    break;
                }
                break;
            case 4:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.getInt(t2, jOffset) == UnsafeUtil.getInt(t3, jOffset)) {
                    break;
                }
                break;
            case 5:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.getLong(t2, jOffset) == UnsafeUtil.getLong(t3, jOffset)) {
                    break;
                }
                break;
            case 6:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.getInt(t2, jOffset) == UnsafeUtil.getInt(t3, jOffset)) {
                    break;
                }
                break;
            case 7:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.getBoolean(t2, jOffset) == UnsafeUtil.getBoolean(t3, jOffset)) {
                    break;
                }
                break;
            case 8:
                if (arePresentForEquals(t2, t3, i2) && SchemaUtil.safeEquals(UnsafeUtil.getObject(t2, jOffset), UnsafeUtil.getObject(t3, jOffset))) {
                    break;
                }
                break;
            case 9:
                if (arePresentForEquals(t2, t3, i2) && SchemaUtil.safeEquals(UnsafeUtil.getObject(t2, jOffset), UnsafeUtil.getObject(t3, jOffset))) {
                    break;
                }
                break;
            case 10:
                if (arePresentForEquals(t2, t3, i2) && SchemaUtil.safeEquals(UnsafeUtil.getObject(t2, jOffset), UnsafeUtil.getObject(t3, jOffset))) {
                    break;
                }
                break;
            case 11:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.getInt(t2, jOffset) == UnsafeUtil.getInt(t3, jOffset)) {
                    break;
                }
                break;
            case 12:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.getInt(t2, jOffset) == UnsafeUtil.getInt(t3, jOffset)) {
                    break;
                }
                break;
            case 13:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.getInt(t2, jOffset) == UnsafeUtil.getInt(t3, jOffset)) {
                    break;
                }
                break;
            case 14:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.getLong(t2, jOffset) == UnsafeUtil.getLong(t3, jOffset)) {
                    break;
                }
                break;
            case 15:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.getInt(t2, jOffset) == UnsafeUtil.getInt(t3, jOffset)) {
                    break;
                }
                break;
            case 16:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.getLong(t2, jOffset) == UnsafeUtil.getLong(t3, jOffset)) {
                    break;
                }
                break;
            case 17:
                if (arePresentForEquals(t2, t3, i2) && SchemaUtil.safeEquals(UnsafeUtil.getObject(t2, jOffset), UnsafeUtil.getObject(t3, jOffset))) {
                    break;
                }
                break;
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
                if (isOneofCaseEqual(t2, t3, i2) && SchemaUtil.safeEquals(UnsafeUtil.getObject(t2, jOffset), UnsafeUtil.getObject(t3, jOffset))) {
                    break;
                }
                break;
        }
        return true;
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public void mergeFrom(T t2, byte[] bArr, int i2, int i3, ArrayDecoders.Registers registers) throws IOException {
        if (this.proto3) {
            parseProto3Message(t2, bArr, i2, i3, registers);
        } else {
            parseProto2Message(t2, bArr, i2, i3, 0, registers);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean isInitialized(Object obj, int i2, Schema schema) {
        return schema.isInitialized(UnsafeUtil.getObject(obj, offset(i2)));
    }
}

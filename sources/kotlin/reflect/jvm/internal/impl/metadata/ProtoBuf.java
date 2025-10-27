package kotlin.reflect.jvm.internal.impl.metadata;

import com.yikaobang.yixue.R2;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.AbstractParser;
import kotlin.reflect.jvm.internal.impl.protobuf.ByteString;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedOutputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.Internal;
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException;
import kotlin.reflect.jvm.internal.impl.protobuf.LazyStringArrayList;
import kotlin.reflect.jvm.internal.impl.protobuf.LazyStringList;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder;
import kotlin.reflect.jvm.internal.impl.protobuf.Parser;
import kotlin.reflect.jvm.internal.impl.protobuf.ProtocolStringList;

/* loaded from: classes8.dex */
public final class ProtoBuf {

    public static final class Annotation extends GeneratedMessageLite implements AnnotationOrBuilder {
        public static Parser<Annotation> PARSER = new AbstractParser<Annotation>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public Annotation parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new Annotation(codedInputStream, extensionRegistryLite);
            }
        };
        private static final Annotation defaultInstance;
        private List<Argument> argument_;
        private int bitField0_;
        private int id_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private final ByteString unknownFields;

        public static final class Argument extends GeneratedMessageLite implements ArgumentOrBuilder {
            public static Parser<Argument> PARSER = new AbstractParser<Argument>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.Argument.1
                @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
                public Argument parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                    return new Argument(codedInputStream, extensionRegistryLite);
                }
            };
            private static final Argument defaultInstance;
            private int bitField0_;
            private byte memoizedIsInitialized;
            private int memoizedSerializedSize;
            private int nameId_;
            private final ByteString unknownFields;
            private Value value_;

            public static final class Builder extends GeneratedMessageLite.Builder<Argument, Builder> implements ArgumentOrBuilder {
                private int bitField0_;
                private int nameId_;
                private Value value_ = Value.getDefaultInstance();

                private Builder() {
                    maybeForceBuilderInitialization();
                }

                /* JADX INFO: Access modifiers changed from: private */
                public static Builder create() {
                    return new Builder();
                }

                private void maybeForceBuilderInitialization() {
                }

                public Argument buildPartial() {
                    Argument argument = new Argument(this);
                    int i2 = this.bitField0_;
                    int i3 = (i2 & 1) != 1 ? 0 : 1;
                    argument.nameId_ = this.nameId_;
                    if ((i2 & 2) == 2) {
                        i3 |= 2;
                    }
                    argument.value_ = this.value_;
                    argument.bitField0_ = i3;
                    return argument;
                }

                public Value getValue() {
                    return this.value_;
                }

                public boolean hasNameId() {
                    return (this.bitField0_ & 1) == 1;
                }

                public boolean hasValue() {
                    return (this.bitField0_ & 2) == 2;
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
                public final boolean isInitialized() {
                    return hasNameId() && hasValue() && getValue().isInitialized();
                }

                public Builder mergeValue(Value value) {
                    if ((this.bitField0_ & 2) != 2 || this.value_ == Value.getDefaultInstance()) {
                        this.value_ = value;
                    } else {
                        this.value_ = Value.newBuilder(this.value_).mergeFrom(value).buildPartial();
                    }
                    this.bitField0_ |= 2;
                    return this;
                }

                public Builder setNameId(int i2) {
                    this.bitField0_ |= 1;
                    this.nameId_ = i2;
                    return this;
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
                public Argument build() {
                    Argument argumentBuildPartial = buildPartial();
                    if (argumentBuildPartial.isInitialized()) {
                        return argumentBuildPartial;
                    }
                    throw AbstractMessageLite.Builder.newUninitializedMessageException(argumentBuildPartial);
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
                public Argument getDefaultInstanceForType() {
                    return Argument.getDefaultInstance();
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
                /* renamed from: clone, reason: merged with bridge method [inline-methods] */
                public Builder mo2084clone() {
                    return create().mergeFrom(buildPartial());
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
                public Builder mergeFrom(Argument argument) {
                    if (argument == Argument.getDefaultInstance()) {
                        return this;
                    }
                    if (argument.hasNameId()) {
                        setNameId(argument.getNameId());
                    }
                    if (argument.hasValue()) {
                        mergeValue(argument.getValue());
                    }
                    setUnknownFields(getUnknownFields().concat(argument.unknownFields));
                    return this;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
                @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.Argument.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                    /*
                        r2 = this;
                        r0 = 0
                        kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Annotation$Argument> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.Argument.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                        java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                        kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Annotation$Argument r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.Argument) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                        if (r3 == 0) goto Le
                        r2.mergeFrom(r3)
                    Le:
                        return r2
                    Lf:
                        r3 = move-exception
                        goto L1b
                    L11:
                        r3 = move-exception
                        kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                        kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Annotation$Argument r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.Argument) r4     // Catch: java.lang.Throwable -> Lf
                        throw r3     // Catch: java.lang.Throwable -> L19
                    L19:
                        r3 = move-exception
                        r0 = r4
                    L1b:
                        if (r0 == 0) goto L20
                        r2.mergeFrom(r0)
                    L20:
                        throw r3
                    */
                    throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.Argument.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Annotation$Argument$Builder");
                }
            }

            public static final class Value extends GeneratedMessageLite implements ValueOrBuilder {
                public static Parser<Value> PARSER = new AbstractParser<Value>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.Argument.Value.1
                    @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
                    public Value parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                        return new Value(codedInputStream, extensionRegistryLite);
                    }
                };
                private static final Value defaultInstance;
                private Annotation annotation_;
                private int arrayDimensionCount_;
                private List<Value> arrayElement_;
                private int bitField0_;
                private int classId_;
                private double doubleValue_;
                private int enumValueId_;
                private int flags_;
                private float floatValue_;
                private long intValue_;
                private byte memoizedIsInitialized;
                private int memoizedSerializedSize;
                private int stringValue_;
                private Type type_;
                private final ByteString unknownFields;

                public static final class Builder extends GeneratedMessageLite.Builder<Value, Builder> implements ValueOrBuilder {
                    private int arrayDimensionCount_;
                    private int bitField0_;
                    private int classId_;
                    private double doubleValue_;
                    private int enumValueId_;
                    private int flags_;
                    private float floatValue_;
                    private long intValue_;
                    private int stringValue_;
                    private Type type_ = Type.BYTE;
                    private Annotation annotation_ = Annotation.getDefaultInstance();
                    private List<Value> arrayElement_ = Collections.emptyList();

                    private Builder() {
                        maybeForceBuilderInitialization();
                    }

                    /* JADX INFO: Access modifiers changed from: private */
                    public static Builder create() {
                        return new Builder();
                    }

                    private void ensureArrayElementIsMutable() {
                        if ((this.bitField0_ & 256) != 256) {
                            this.arrayElement_ = new ArrayList(this.arrayElement_);
                            this.bitField0_ |= 256;
                        }
                    }

                    private void maybeForceBuilderInitialization() {
                    }

                    public Value buildPartial() {
                        Value value = new Value(this);
                        int i2 = this.bitField0_;
                        int i3 = (i2 & 1) != 1 ? 0 : 1;
                        value.type_ = this.type_;
                        if ((i2 & 2) == 2) {
                            i3 |= 2;
                        }
                        value.intValue_ = this.intValue_;
                        if ((i2 & 4) == 4) {
                            i3 |= 4;
                        }
                        value.floatValue_ = this.floatValue_;
                        if ((i2 & 8) == 8) {
                            i3 |= 8;
                        }
                        value.doubleValue_ = this.doubleValue_;
                        if ((i2 & 16) == 16) {
                            i3 |= 16;
                        }
                        value.stringValue_ = this.stringValue_;
                        if ((i2 & 32) == 32) {
                            i3 |= 32;
                        }
                        value.classId_ = this.classId_;
                        if ((i2 & 64) == 64) {
                            i3 |= 64;
                        }
                        value.enumValueId_ = this.enumValueId_;
                        if ((i2 & 128) == 128) {
                            i3 |= 128;
                        }
                        value.annotation_ = this.annotation_;
                        if ((this.bitField0_ & 256) == 256) {
                            this.arrayElement_ = Collections.unmodifiableList(this.arrayElement_);
                            this.bitField0_ &= -257;
                        }
                        value.arrayElement_ = this.arrayElement_;
                        if ((i2 & 512) == 512) {
                            i3 |= 256;
                        }
                        value.arrayDimensionCount_ = this.arrayDimensionCount_;
                        if ((i2 & 1024) == 1024) {
                            i3 |= 512;
                        }
                        value.flags_ = this.flags_;
                        value.bitField0_ = i3;
                        return value;
                    }

                    public Annotation getAnnotation() {
                        return this.annotation_;
                    }

                    public Value getArrayElement(int i2) {
                        return this.arrayElement_.get(i2);
                    }

                    public int getArrayElementCount() {
                        return this.arrayElement_.size();
                    }

                    public boolean hasAnnotation() {
                        return (this.bitField0_ & 128) == 128;
                    }

                    @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
                    public final boolean isInitialized() {
                        if (hasAnnotation() && !getAnnotation().isInitialized()) {
                            return false;
                        }
                        for (int i2 = 0; i2 < getArrayElementCount(); i2++) {
                            if (!getArrayElement(i2).isInitialized()) {
                                return false;
                            }
                        }
                        return true;
                    }

                    public Builder mergeAnnotation(Annotation annotation) {
                        if ((this.bitField0_ & 128) != 128 || this.annotation_ == Annotation.getDefaultInstance()) {
                            this.annotation_ = annotation;
                        } else {
                            this.annotation_ = Annotation.newBuilder(this.annotation_).mergeFrom(annotation).buildPartial();
                        }
                        this.bitField0_ |= 128;
                        return this;
                    }

                    public Builder setArrayDimensionCount(int i2) {
                        this.bitField0_ |= 512;
                        this.arrayDimensionCount_ = i2;
                        return this;
                    }

                    public Builder setClassId(int i2) {
                        this.bitField0_ |= 32;
                        this.classId_ = i2;
                        return this;
                    }

                    public Builder setDoubleValue(double d3) {
                        this.bitField0_ |= 8;
                        this.doubleValue_ = d3;
                        return this;
                    }

                    public Builder setEnumValueId(int i2) {
                        this.bitField0_ |= 64;
                        this.enumValueId_ = i2;
                        return this;
                    }

                    public Builder setFlags(int i2) {
                        this.bitField0_ |= 1024;
                        this.flags_ = i2;
                        return this;
                    }

                    public Builder setFloatValue(float f2) {
                        this.bitField0_ |= 4;
                        this.floatValue_ = f2;
                        return this;
                    }

                    public Builder setIntValue(long j2) {
                        this.bitField0_ |= 2;
                        this.intValue_ = j2;
                        return this;
                    }

                    public Builder setStringValue(int i2) {
                        this.bitField0_ |= 16;
                        this.stringValue_ = i2;
                        return this;
                    }

                    public Builder setType(Type type) {
                        type.getClass();
                        this.bitField0_ |= 1;
                        this.type_ = type;
                        return this;
                    }

                    @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
                    public Value build() {
                        Value valueBuildPartial = buildPartial();
                        if (valueBuildPartial.isInitialized()) {
                            return valueBuildPartial;
                        }
                        throw AbstractMessageLite.Builder.newUninitializedMessageException(valueBuildPartial);
                    }

                    @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
                    public Value getDefaultInstanceForType() {
                        return Value.getDefaultInstance();
                    }

                    @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
                    /* renamed from: clone */
                    public Builder mo2084clone() {
                        return create().mergeFrom(buildPartial());
                    }

                    @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
                    public Builder mergeFrom(Value value) {
                        if (value == Value.getDefaultInstance()) {
                            return this;
                        }
                        if (value.hasType()) {
                            setType(value.getType());
                        }
                        if (value.hasIntValue()) {
                            setIntValue(value.getIntValue());
                        }
                        if (value.hasFloatValue()) {
                            setFloatValue(value.getFloatValue());
                        }
                        if (value.hasDoubleValue()) {
                            setDoubleValue(value.getDoubleValue());
                        }
                        if (value.hasStringValue()) {
                            setStringValue(value.getStringValue());
                        }
                        if (value.hasClassId()) {
                            setClassId(value.getClassId());
                        }
                        if (value.hasEnumValueId()) {
                            setEnumValueId(value.getEnumValueId());
                        }
                        if (value.hasAnnotation()) {
                            mergeAnnotation(value.getAnnotation());
                        }
                        if (!value.arrayElement_.isEmpty()) {
                            if (this.arrayElement_.isEmpty()) {
                                this.arrayElement_ = value.arrayElement_;
                                this.bitField0_ &= -257;
                            } else {
                                ensureArrayElementIsMutable();
                                this.arrayElement_.addAll(value.arrayElement_);
                            }
                        }
                        if (value.hasArrayDimensionCount()) {
                            setArrayDimensionCount(value.getArrayDimensionCount());
                        }
                        if (value.hasFlags()) {
                            setFlags(value.getFlags());
                        }
                        setUnknownFields(getUnknownFields().concat(value.unknownFields));
                        return this;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
                    @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.Argument.Value.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                        /*
                            r2 = this;
                            r0 = 0
                            kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Annotation$Argument$Value> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.Argument.Value.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                            java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                            kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Annotation$Argument$Value r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.Argument.Value) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                            if (r3 == 0) goto Le
                            r2.mergeFrom(r3)
                        Le:
                            return r2
                        Lf:
                            r3 = move-exception
                            goto L1b
                        L11:
                            r3 = move-exception
                            kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                            kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Annotation$Argument$Value r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.Argument.Value) r4     // Catch: java.lang.Throwable -> Lf
                            throw r3     // Catch: java.lang.Throwable -> L19
                        L19:
                            r3 = move-exception
                            r0 = r4
                        L1b:
                            if (r0 == 0) goto L20
                            r2.mergeFrom(r0)
                        L20:
                            throw r3
                        */
                        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.Argument.Value.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Annotation$Argument$Value$Builder");
                    }
                }

                public enum Type implements Internal.EnumLite {
                    BYTE(0, 0),
                    CHAR(1, 1),
                    SHORT(2, 2),
                    INT(3, 3),
                    LONG(4, 4),
                    FLOAT(5, 5),
                    DOUBLE(6, 6),
                    BOOLEAN(7, 7),
                    STRING(8, 8),
                    CLASS(9, 9),
                    ENUM(10, 10),
                    ANNOTATION(11, 11),
                    ARRAY(12, 12);

                    private static Internal.EnumLiteMap<Type> internalValueMap = new Internal.EnumLiteMap<Type>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.Argument.Value.Type.1
                        @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLiteMap
                        public Type findValueByNumber(int i2) {
                            return Type.valueOf(i2);
                        }
                    };
                    private final int value;

                    Type(int i2, int i3) {
                        this.value = i3;
                    }

                    @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLite
                    public final int getNumber() {
                        return this.value;
                    }

                    public static Type valueOf(int i2) {
                        switch (i2) {
                            case 0:
                                return BYTE;
                            case 1:
                                return CHAR;
                            case 2:
                                return SHORT;
                            case 3:
                                return INT;
                            case 4:
                                return LONG;
                            case 5:
                                return FLOAT;
                            case 6:
                                return DOUBLE;
                            case 7:
                                return BOOLEAN;
                            case 8:
                                return STRING;
                            case 9:
                                return CLASS;
                            case 10:
                                return ENUM;
                            case 11:
                                return ANNOTATION;
                            case 12:
                                return ARRAY;
                            default:
                                return null;
                        }
                    }
                }

                static {
                    Value value = new Value(true);
                    defaultInstance = value;
                    value.initFields();
                }

                public static Value getDefaultInstance() {
                    return defaultInstance;
                }

                private void initFields() {
                    this.type_ = Type.BYTE;
                    this.intValue_ = 0L;
                    this.floatValue_ = 0.0f;
                    this.doubleValue_ = 0.0d;
                    this.stringValue_ = 0;
                    this.classId_ = 0;
                    this.enumValueId_ = 0;
                    this.annotation_ = Annotation.getDefaultInstance();
                    this.arrayElement_ = Collections.emptyList();
                    this.arrayDimensionCount_ = 0;
                    this.flags_ = 0;
                }

                public static Builder newBuilder() {
                    return Builder.create();
                }

                public Annotation getAnnotation() {
                    return this.annotation_;
                }

                public int getArrayDimensionCount() {
                    return this.arrayDimensionCount_;
                }

                public Value getArrayElement(int i2) {
                    return this.arrayElement_.get(i2);
                }

                public int getArrayElementCount() {
                    return this.arrayElement_.size();
                }

                public List<Value> getArrayElementList() {
                    return this.arrayElement_;
                }

                public int getClassId() {
                    return this.classId_;
                }

                public double getDoubleValue() {
                    return this.doubleValue_;
                }

                public int getEnumValueId() {
                    return this.enumValueId_;
                }

                public int getFlags() {
                    return this.flags_;
                }

                public float getFloatValue() {
                    return this.floatValue_;
                }

                public long getIntValue() {
                    return this.intValue_;
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
                public Parser<Value> getParserForType() {
                    return PARSER;
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
                public int getSerializedSize() {
                    int i2 = this.memoizedSerializedSize;
                    if (i2 != -1) {
                        return i2;
                    }
                    int iComputeEnumSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeEnumSize(1, this.type_.getNumber()) + 0 : 0;
                    if ((this.bitField0_ & 2) == 2) {
                        iComputeEnumSize += CodedOutputStream.computeSInt64Size(2, this.intValue_);
                    }
                    if ((this.bitField0_ & 4) == 4) {
                        iComputeEnumSize += CodedOutputStream.computeFloatSize(3, this.floatValue_);
                    }
                    if ((this.bitField0_ & 8) == 8) {
                        iComputeEnumSize += CodedOutputStream.computeDoubleSize(4, this.doubleValue_);
                    }
                    if ((this.bitField0_ & 16) == 16) {
                        iComputeEnumSize += CodedOutputStream.computeInt32Size(5, this.stringValue_);
                    }
                    if ((this.bitField0_ & 32) == 32) {
                        iComputeEnumSize += CodedOutputStream.computeInt32Size(6, this.classId_);
                    }
                    if ((this.bitField0_ & 64) == 64) {
                        iComputeEnumSize += CodedOutputStream.computeInt32Size(7, this.enumValueId_);
                    }
                    if ((this.bitField0_ & 128) == 128) {
                        iComputeEnumSize += CodedOutputStream.computeMessageSize(8, this.annotation_);
                    }
                    for (int i3 = 0; i3 < this.arrayElement_.size(); i3++) {
                        iComputeEnumSize += CodedOutputStream.computeMessageSize(9, this.arrayElement_.get(i3));
                    }
                    if ((this.bitField0_ & 512) == 512) {
                        iComputeEnumSize += CodedOutputStream.computeInt32Size(10, this.flags_);
                    }
                    if ((this.bitField0_ & 256) == 256) {
                        iComputeEnumSize += CodedOutputStream.computeInt32Size(11, this.arrayDimensionCount_);
                    }
                    int size = iComputeEnumSize + this.unknownFields.size();
                    this.memoizedSerializedSize = size;
                    return size;
                }

                public int getStringValue() {
                    return this.stringValue_;
                }

                public Type getType() {
                    return this.type_;
                }

                public boolean hasAnnotation() {
                    return (this.bitField0_ & 128) == 128;
                }

                public boolean hasArrayDimensionCount() {
                    return (this.bitField0_ & 256) == 256;
                }

                public boolean hasClassId() {
                    return (this.bitField0_ & 32) == 32;
                }

                public boolean hasDoubleValue() {
                    return (this.bitField0_ & 8) == 8;
                }

                public boolean hasEnumValueId() {
                    return (this.bitField0_ & 64) == 64;
                }

                public boolean hasFlags() {
                    return (this.bitField0_ & 512) == 512;
                }

                public boolean hasFloatValue() {
                    return (this.bitField0_ & 4) == 4;
                }

                public boolean hasIntValue() {
                    return (this.bitField0_ & 2) == 2;
                }

                public boolean hasStringValue() {
                    return (this.bitField0_ & 16) == 16;
                }

                public boolean hasType() {
                    return (this.bitField0_ & 1) == 1;
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
                public final boolean isInitialized() {
                    byte b3 = this.memoizedIsInitialized;
                    if (b3 == 1) {
                        return true;
                    }
                    if (b3 == 0) {
                        return false;
                    }
                    if (hasAnnotation() && !getAnnotation().isInitialized()) {
                        this.memoizedIsInitialized = (byte) 0;
                        return false;
                    }
                    for (int i2 = 0; i2 < getArrayElementCount(); i2++) {
                        if (!getArrayElement(i2).isInitialized()) {
                            this.memoizedIsInitialized = (byte) 0;
                            return false;
                        }
                    }
                    this.memoizedIsInitialized = (byte) 1;
                    return true;
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
                public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
                    getSerializedSize();
                    if ((this.bitField0_ & 1) == 1) {
                        codedOutputStream.writeEnum(1, this.type_.getNumber());
                    }
                    if ((this.bitField0_ & 2) == 2) {
                        codedOutputStream.writeSInt64(2, this.intValue_);
                    }
                    if ((this.bitField0_ & 4) == 4) {
                        codedOutputStream.writeFloat(3, this.floatValue_);
                    }
                    if ((this.bitField0_ & 8) == 8) {
                        codedOutputStream.writeDouble(4, this.doubleValue_);
                    }
                    if ((this.bitField0_ & 16) == 16) {
                        codedOutputStream.writeInt32(5, this.stringValue_);
                    }
                    if ((this.bitField0_ & 32) == 32) {
                        codedOutputStream.writeInt32(6, this.classId_);
                    }
                    if ((this.bitField0_ & 64) == 64) {
                        codedOutputStream.writeInt32(7, this.enumValueId_);
                    }
                    if ((this.bitField0_ & 128) == 128) {
                        codedOutputStream.writeMessage(8, this.annotation_);
                    }
                    for (int i2 = 0; i2 < this.arrayElement_.size(); i2++) {
                        codedOutputStream.writeMessage(9, this.arrayElement_.get(i2));
                    }
                    if ((this.bitField0_ & 512) == 512) {
                        codedOutputStream.writeInt32(10, this.flags_);
                    }
                    if ((this.bitField0_ & 256) == 256) {
                        codedOutputStream.writeInt32(11, this.arrayDimensionCount_);
                    }
                    codedOutputStream.writeRawBytes(this.unknownFields);
                }

                public static Builder newBuilder(Value value) {
                    return newBuilder().mergeFrom(value);
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
                public Value getDefaultInstanceForType() {
                    return defaultInstance;
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
                public Builder newBuilderForType() {
                    return newBuilder();
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
                public Builder toBuilder() {
                    return newBuilder(this);
                }

                private Value(GeneratedMessageLite.Builder builder) {
                    super(builder);
                    this.memoizedIsInitialized = (byte) -1;
                    this.memoizedSerializedSize = -1;
                    this.unknownFields = builder.getUnknownFields();
                }

                private Value(boolean z2) {
                    this.memoizedIsInitialized = (byte) -1;
                    this.memoizedSerializedSize = -1;
                    this.unknownFields = ByteString.EMPTY;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r5v0 */
                /* JADX WARN: Type inference failed for: r5v1 */
                /* JADX WARN: Type inference failed for: r5v2, types: [boolean] */
                private Value(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                    this.memoizedIsInitialized = (byte) -1;
                    this.memoizedSerializedSize = -1;
                    initFields();
                    ByteString.Output outputNewOutput = ByteString.newOutput();
                    CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
                    boolean z2 = false;
                    int i2 = 0;
                    while (true) {
                        ?? unknownField = 256;
                        if (!z2) {
                            try {
                                try {
                                    int tag = codedInputStream.readTag();
                                    switch (tag) {
                                        case 0:
                                            z2 = true;
                                        case 8:
                                            int i3 = codedInputStream.readEnum();
                                            Type typeValueOf = Type.valueOf(i3);
                                            if (typeValueOf == null) {
                                                codedOutputStreamNewInstance.writeRawVarint32(tag);
                                                codedOutputStreamNewInstance.writeRawVarint32(i3);
                                            } else {
                                                this.bitField0_ |= 1;
                                                this.type_ = typeValueOf;
                                            }
                                        case 16:
                                            this.bitField0_ |= 2;
                                            this.intValue_ = codedInputStream.readSInt64();
                                        case 29:
                                            this.bitField0_ |= 4;
                                            this.floatValue_ = codedInputStream.readFloat();
                                        case 33:
                                            this.bitField0_ |= 8;
                                            this.doubleValue_ = codedInputStream.readDouble();
                                        case 40:
                                            this.bitField0_ |= 16;
                                            this.stringValue_ = codedInputStream.readInt32();
                                        case 48:
                                            this.bitField0_ |= 32;
                                            this.classId_ = codedInputStream.readInt32();
                                        case 56:
                                            this.bitField0_ |= 64;
                                            this.enumValueId_ = codedInputStream.readInt32();
                                        case 66:
                                            Builder builder = (this.bitField0_ & 128) == 128 ? this.annotation_.toBuilder() : null;
                                            Annotation annotation = (Annotation) codedInputStream.readMessage(Annotation.PARSER, extensionRegistryLite);
                                            this.annotation_ = annotation;
                                            if (builder != null) {
                                                builder.mergeFrom(annotation);
                                                this.annotation_ = builder.buildPartial();
                                            }
                                            this.bitField0_ |= 128;
                                        case 74:
                                            if ((i2 & 256) != 256) {
                                                this.arrayElement_ = new ArrayList();
                                                i2 |= 256;
                                            }
                                            this.arrayElement_.add(codedInputStream.readMessage(PARSER, extensionRegistryLite));
                                        case 80:
                                            this.bitField0_ |= 512;
                                            this.flags_ = codedInputStream.readInt32();
                                        case 88:
                                            this.bitField0_ |= 256;
                                            this.arrayDimensionCount_ = codedInputStream.readInt32();
                                        default:
                                            unknownField = parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag);
                                            if (unknownField == 0) {
                                                z2 = true;
                                            }
                                    }
                                } catch (InvalidProtocolBufferException e2) {
                                    throw e2.setUnfinishedMessage(this);
                                } catch (IOException e3) {
                                    throw new InvalidProtocolBufferException(e3.getMessage()).setUnfinishedMessage(this);
                                }
                            } catch (Throwable th) {
                                if ((i2 & 256) == unknownField) {
                                    this.arrayElement_ = Collections.unmodifiableList(this.arrayElement_);
                                }
                                try {
                                    codedOutputStreamNewInstance.flush();
                                } catch (IOException unused) {
                                } catch (Throwable th2) {
                                    this.unknownFields = outputNewOutput.toByteString();
                                    throw th2;
                                }
                                this.unknownFields = outputNewOutput.toByteString();
                                makeExtensionsImmutable();
                                throw th;
                            }
                        } else {
                            if ((i2 & 256) == 256) {
                                this.arrayElement_ = Collections.unmodifiableList(this.arrayElement_);
                            }
                            try {
                                codedOutputStreamNewInstance.flush();
                            } catch (IOException unused2) {
                            } catch (Throwable th3) {
                                this.unknownFields = outputNewOutput.toByteString();
                                throw th3;
                            }
                            this.unknownFields = outputNewOutput.toByteString();
                            makeExtensionsImmutable();
                            return;
                        }
                    }
                }
            }

            public interface ValueOrBuilder extends MessageLiteOrBuilder {
            }

            static {
                Argument argument = new Argument(true);
                defaultInstance = argument;
                argument.initFields();
            }

            public static Argument getDefaultInstance() {
                return defaultInstance;
            }

            private void initFields() {
                this.nameId_ = 0;
                this.value_ = Value.getDefaultInstance();
            }

            public static Builder newBuilder() {
                return Builder.create();
            }

            public int getNameId() {
                return this.nameId_;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
            public Parser<Argument> getParserForType() {
                return PARSER;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
            public int getSerializedSize() {
                int i2 = this.memoizedSerializedSize;
                if (i2 != -1) {
                    return i2;
                }
                int iComputeInt32Size = (this.bitField0_ & 1) == 1 ? 0 + CodedOutputStream.computeInt32Size(1, this.nameId_) : 0;
                if ((this.bitField0_ & 2) == 2) {
                    iComputeInt32Size += CodedOutputStream.computeMessageSize(2, this.value_);
                }
                int size = iComputeInt32Size + this.unknownFields.size();
                this.memoizedSerializedSize = size;
                return size;
            }

            public Value getValue() {
                return this.value_;
            }

            public boolean hasNameId() {
                return (this.bitField0_ & 1) == 1;
            }

            public boolean hasValue() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                byte b3 = this.memoizedIsInitialized;
                if (b3 == 1) {
                    return true;
                }
                if (b3 == 0) {
                    return false;
                }
                if (!hasNameId()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
                if (!hasValue()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
                if (getValue().isInitialized()) {
                    this.memoizedIsInitialized = (byte) 1;
                    return true;
                }
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
            public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
                getSerializedSize();
                if ((this.bitField0_ & 1) == 1) {
                    codedOutputStream.writeInt32(1, this.nameId_);
                }
                if ((this.bitField0_ & 2) == 2) {
                    codedOutputStream.writeMessage(2, this.value_);
                }
                codedOutputStream.writeRawBytes(this.unknownFields);
            }

            public static Builder newBuilder(Argument argument) {
                return newBuilder().mergeFrom(argument);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public Argument getDefaultInstanceForType() {
                return defaultInstance;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
            public Builder newBuilderForType() {
                return newBuilder();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
            public Builder toBuilder() {
                return newBuilder(this);
            }

            private Argument(GeneratedMessageLite.Builder builder) {
                super(builder);
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                this.unknownFields = builder.getUnknownFields();
            }

            private Argument(boolean z2) {
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                this.unknownFields = ByteString.EMPTY;
            }

            private Argument(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                initFields();
                ByteString.Output outputNewOutput = ByteString.newOutput();
                CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
                boolean z2 = false;
                while (!z2) {
                    try {
                        try {
                            try {
                                int tag = codedInputStream.readTag();
                                if (tag != 0) {
                                    if (tag == 8) {
                                        this.bitField0_ |= 1;
                                        this.nameId_ = codedInputStream.readInt32();
                                    } else if (tag != 18) {
                                        if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                        }
                                    } else {
                                        Value.Builder builder = (this.bitField0_ & 2) == 2 ? this.value_.toBuilder() : null;
                                        Value value = (Value) codedInputStream.readMessage(Value.PARSER, extensionRegistryLite);
                                        this.value_ = value;
                                        if (builder != null) {
                                            builder.mergeFrom(value);
                                            this.value_ = builder.buildPartial();
                                        }
                                        this.bitField0_ |= 2;
                                    }
                                }
                                z2 = true;
                            } catch (InvalidProtocolBufferException e2) {
                                throw e2.setUnfinishedMessage(this);
                            }
                        } catch (IOException e3) {
                            throw new InvalidProtocolBufferException(e3.getMessage()).setUnfinishedMessage(this);
                        }
                    } catch (Throwable th) {
                        try {
                            codedOutputStreamNewInstance.flush();
                        } catch (IOException unused) {
                        } catch (Throwable th2) {
                            this.unknownFields = outputNewOutput.toByteString();
                            throw th2;
                        }
                        this.unknownFields = outputNewOutput.toByteString();
                        makeExtensionsImmutable();
                        throw th;
                    }
                }
                try {
                    codedOutputStreamNewInstance.flush();
                } catch (IOException unused2) {
                } catch (Throwable th3) {
                    this.unknownFields = outputNewOutput.toByteString();
                    throw th3;
                }
                this.unknownFields = outputNewOutput.toByteString();
                makeExtensionsImmutable();
            }
        }

        public interface ArgumentOrBuilder extends MessageLiteOrBuilder {
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Annotation, Builder> implements AnnotationOrBuilder {
            private List<Argument> argument_ = Collections.emptyList();
            private int bitField0_;
            private int id_;

            private Builder() {
                maybeForceBuilderInitialization();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            private void ensureArgumentIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.argument_ = new ArrayList(this.argument_);
                    this.bitField0_ |= 2;
                }
            }

            private void maybeForceBuilderInitialization() {
            }

            public Annotation buildPartial() {
                Annotation annotation = new Annotation(this);
                int i2 = (this.bitField0_ & 1) != 1 ? 0 : 1;
                annotation.id_ = this.id_;
                if ((this.bitField0_ & 2) == 2) {
                    this.argument_ = Collections.unmodifiableList(this.argument_);
                    this.bitField0_ &= -3;
                }
                annotation.argument_ = this.argument_;
                annotation.bitField0_ = i2;
                return annotation;
            }

            public Argument getArgument(int i2) {
                return this.argument_.get(i2);
            }

            public int getArgumentCount() {
                return this.argument_.size();
            }

            public boolean hasId() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                if (!hasId()) {
                    return false;
                }
                for (int i2 = 0; i2 < getArgumentCount(); i2++) {
                    if (!getArgument(i2).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            public Builder setId(int i2) {
                this.bitField0_ |= 1;
                this.id_ = i2;
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public Annotation build() {
                Annotation annotationBuildPartial = buildPartial();
                if (annotationBuildPartial.isInitialized()) {
                    return annotationBuildPartial;
                }
                throw AbstractMessageLite.Builder.newUninitializedMessageException(annotationBuildPartial);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public Annotation getDefaultInstanceForType() {
                return Annotation.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo2084clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(Annotation annotation) {
                if (annotation == Annotation.getDefaultInstance()) {
                    return this;
                }
                if (annotation.hasId()) {
                    setId(annotation.getId());
                }
                if (!annotation.argument_.isEmpty()) {
                    if (this.argument_.isEmpty()) {
                        this.argument_ = annotation.argument_;
                        this.bitField0_ &= -3;
                    } else {
                        ensureArgumentIsMutable();
                        this.argument_.addAll(annotation.argument_);
                    }
                }
                setUnknownFields(getUnknownFields().concat(annotation.unknownFields));
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Annotation> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Annotation r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    if (r3 == 0) goto Le
                    r2.mergeFrom(r3)
                Le:
                    return r2
                Lf:
                    r3 = move-exception
                    goto L1b
                L11:
                    r3 = move-exception
                    kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Annotation r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation) r4     // Catch: java.lang.Throwable -> Lf
                    throw r3     // Catch: java.lang.Throwable -> L19
                L19:
                    r3 = move-exception
                    r0 = r4
                L1b:
                    if (r0 == 0) goto L20
                    r2.mergeFrom(r0)
                L20:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Annotation$Builder");
            }
        }

        static {
            Annotation annotation = new Annotation(true);
            defaultInstance = annotation;
            annotation.initFields();
        }

        public static Annotation getDefaultInstance() {
            return defaultInstance;
        }

        private void initFields() {
            this.id_ = 0;
            this.argument_ = Collections.emptyList();
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Argument getArgument(int i2) {
            return this.argument_.get(i2);
        }

        public int getArgumentCount() {
            return this.argument_.size();
        }

        public List<Argument> getArgumentList() {
            return this.argument_;
        }

        public int getId() {
            return this.id_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<Annotation> getParserForType() {
            return PARSER;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSerializedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeInt32Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt32Size(1, this.id_) + 0 : 0;
            for (int i3 = 0; i3 < this.argument_.size(); i3++) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(2, this.argument_.get(i3));
            }
            int size = iComputeInt32Size + this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
        }

        public boolean hasId() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b3 = this.memoizedIsInitialized;
            if (b3 == 1) {
                return true;
            }
            if (b3 == 0) {
                return false;
            }
            if (!hasId()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            for (int i2 = 0; i2 < getArgumentCount(); i2++) {
                if (!getArgument(i2).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeInt32(1, this.id_);
            }
            for (int i2 = 0; i2 < this.argument_.size(); i2++) {
                codedOutputStream.writeMessage(2, this.argument_.get(i2));
            }
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        public static Builder newBuilder(Annotation annotation) {
            return newBuilder().mergeFrom(annotation);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public Annotation getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        private Annotation(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private Annotation(boolean z2) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private Annotation(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z2 = false;
            int i2 = 0;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 8) {
                                this.bitField0_ |= 1;
                                this.id_ = codedInputStream.readInt32();
                            } else if (tag != 18) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                if ((i2 & 2) != 2) {
                                    this.argument_ = new ArrayList();
                                    i2 |= 2;
                                }
                                this.argument_.add(codedInputStream.readMessage(Argument.PARSER, extensionRegistryLite));
                            }
                        }
                        z2 = true;
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if ((i2 & 2) == 2) {
                        this.argument_ = Collections.unmodifiableList(this.argument_);
                    }
                    try {
                        codedOutputStreamNewInstance.flush();
                    } catch (IOException unused) {
                    } catch (Throwable th2) {
                        this.unknownFields = outputNewOutput.toByteString();
                        throw th2;
                    }
                    this.unknownFields = outputNewOutput.toByteString();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((i2 & 2) == 2) {
                this.argument_ = Collections.unmodifiableList(this.argument_);
            }
            try {
                codedOutputStreamNewInstance.flush();
            } catch (IOException unused2) {
            } catch (Throwable th3) {
                this.unknownFields = outputNewOutput.toByteString();
                throw th3;
            }
            this.unknownFields = outputNewOutput.toByteString();
            makeExtensionsImmutable();
        }
    }

    public interface AnnotationOrBuilder extends MessageLiteOrBuilder {
    }

    public static final class Class extends GeneratedMessageLite.ExtendableMessage<Class> implements ClassOrBuilder {
        public static Parser<Class> PARSER = new AbstractParser<Class>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Class.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public Class parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new Class(codedInputStream, extensionRegistryLite);
            }
        };
        private static final Class defaultInstance;
        private int bitField0_;
        private int companionObjectName_;
        private List<Constructor> constructor_;
        private int contextReceiverTypeIdMemoizedSerializedSize;
        private List<Integer> contextReceiverTypeId_;
        private List<Type> contextReceiverType_;
        private List<EnumEntry> enumEntry_;
        private int flags_;
        private int fqName_;
        private List<Function> function_;
        private int inlineClassUnderlyingPropertyName_;
        private int inlineClassUnderlyingTypeId_;
        private Type inlineClassUnderlyingType_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private int multiFieldValueClassUnderlyingNameMemoizedSerializedSize;
        private List<Integer> multiFieldValueClassUnderlyingName_;
        private int multiFieldValueClassUnderlyingTypeIdMemoizedSerializedSize;
        private List<Integer> multiFieldValueClassUnderlyingTypeId_;
        private List<Type> multiFieldValueClassUnderlyingType_;
        private int nestedClassNameMemoizedSerializedSize;
        private List<Integer> nestedClassName_;
        private List<Property> property_;
        private int sealedSubclassFqNameMemoizedSerializedSize;
        private List<Integer> sealedSubclassFqName_;
        private int supertypeIdMemoizedSerializedSize;
        private List<Integer> supertypeId_;
        private List<Type> supertype_;
        private List<TypeAlias> typeAlias_;
        private List<TypeParameter> typeParameter_;
        private TypeTable typeTable_;
        private final ByteString unknownFields;
        private VersionRequirementTable versionRequirementTable_;
        private List<Integer> versionRequirement_;

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<Class, Builder> implements ClassOrBuilder {
            private int bitField0_;
            private int companionObjectName_;
            private int fqName_;
            private int inlineClassUnderlyingPropertyName_;
            private int inlineClassUnderlyingTypeId_;
            private int flags_ = 6;
            private List<TypeParameter> typeParameter_ = Collections.emptyList();
            private List<Type> supertype_ = Collections.emptyList();
            private List<Integer> supertypeId_ = Collections.emptyList();
            private List<Integer> nestedClassName_ = Collections.emptyList();
            private List<Type> contextReceiverType_ = Collections.emptyList();
            private List<Integer> contextReceiverTypeId_ = Collections.emptyList();
            private List<Constructor> constructor_ = Collections.emptyList();
            private List<Function> function_ = Collections.emptyList();
            private List<Property> property_ = Collections.emptyList();
            private List<TypeAlias> typeAlias_ = Collections.emptyList();
            private List<EnumEntry> enumEntry_ = Collections.emptyList();
            private List<Integer> sealedSubclassFqName_ = Collections.emptyList();
            private Type inlineClassUnderlyingType_ = Type.getDefaultInstance();
            private List<Integer> multiFieldValueClassUnderlyingName_ = Collections.emptyList();
            private List<Type> multiFieldValueClassUnderlyingType_ = Collections.emptyList();
            private List<Integer> multiFieldValueClassUnderlyingTypeId_ = Collections.emptyList();
            private TypeTable typeTable_ = TypeTable.getDefaultInstance();
            private List<Integer> versionRequirement_ = Collections.emptyList();
            private VersionRequirementTable versionRequirementTable_ = VersionRequirementTable.getDefaultInstance();

            private Builder() {
                maybeForceBuilderInitialization();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            private void ensureConstructorIsMutable() {
                if ((this.bitField0_ & 512) != 512) {
                    this.constructor_ = new ArrayList(this.constructor_);
                    this.bitField0_ |= 512;
                }
            }

            private void ensureContextReceiverTypeIdIsMutable() {
                if ((this.bitField0_ & 256) != 256) {
                    this.contextReceiverTypeId_ = new ArrayList(this.contextReceiverTypeId_);
                    this.bitField0_ |= 256;
                }
            }

            private void ensureContextReceiverTypeIsMutable() {
                if ((this.bitField0_ & 128) != 128) {
                    this.contextReceiverType_ = new ArrayList(this.contextReceiverType_);
                    this.bitField0_ |= 128;
                }
            }

            private void ensureEnumEntryIsMutable() {
                if ((this.bitField0_ & 8192) != 8192) {
                    this.enumEntry_ = new ArrayList(this.enumEntry_);
                    this.bitField0_ |= 8192;
                }
            }

            private void ensureFunctionIsMutable() {
                if ((this.bitField0_ & 1024) != 1024) {
                    this.function_ = new ArrayList(this.function_);
                    this.bitField0_ |= 1024;
                }
            }

            private void ensureMultiFieldValueClassUnderlyingNameIsMutable() {
                if ((this.bitField0_ & 262144) != 262144) {
                    this.multiFieldValueClassUnderlyingName_ = new ArrayList(this.multiFieldValueClassUnderlyingName_);
                    this.bitField0_ |= 262144;
                }
            }

            private void ensureMultiFieldValueClassUnderlyingTypeIdIsMutable() {
                if ((this.bitField0_ & 1048576) != 1048576) {
                    this.multiFieldValueClassUnderlyingTypeId_ = new ArrayList(this.multiFieldValueClassUnderlyingTypeId_);
                    this.bitField0_ |= 1048576;
                }
            }

            private void ensureMultiFieldValueClassUnderlyingTypeIsMutable() {
                if ((this.bitField0_ & 524288) != 524288) {
                    this.multiFieldValueClassUnderlyingType_ = new ArrayList(this.multiFieldValueClassUnderlyingType_);
                    this.bitField0_ |= 524288;
                }
            }

            private void ensureNestedClassNameIsMutable() {
                if ((this.bitField0_ & 64) != 64) {
                    this.nestedClassName_ = new ArrayList(this.nestedClassName_);
                    this.bitField0_ |= 64;
                }
            }

            private void ensurePropertyIsMutable() {
                if ((this.bitField0_ & 2048) != 2048) {
                    this.property_ = new ArrayList(this.property_);
                    this.bitField0_ |= 2048;
                }
            }

            private void ensureSealedSubclassFqNameIsMutable() {
                if ((this.bitField0_ & 16384) != 16384) {
                    this.sealedSubclassFqName_ = new ArrayList(this.sealedSubclassFqName_);
                    this.bitField0_ |= 16384;
                }
            }

            private void ensureSupertypeIdIsMutable() {
                if ((this.bitField0_ & 32) != 32) {
                    this.supertypeId_ = new ArrayList(this.supertypeId_);
                    this.bitField0_ |= 32;
                }
            }

            private void ensureSupertypeIsMutable() {
                if ((this.bitField0_ & 16) != 16) {
                    this.supertype_ = new ArrayList(this.supertype_);
                    this.bitField0_ |= 16;
                }
            }

            private void ensureTypeAliasIsMutable() {
                if ((this.bitField0_ & 4096) != 4096) {
                    this.typeAlias_ = new ArrayList(this.typeAlias_);
                    this.bitField0_ |= 4096;
                }
            }

            private void ensureTypeParameterIsMutable() {
                if ((this.bitField0_ & 8) != 8) {
                    this.typeParameter_ = new ArrayList(this.typeParameter_);
                    this.bitField0_ |= 8;
                }
            }

            private void ensureVersionRequirementIsMutable() {
                if ((this.bitField0_ & 4194304) != 4194304) {
                    this.versionRequirement_ = new ArrayList(this.versionRequirement_);
                    this.bitField0_ |= 4194304;
                }
            }

            private void maybeForceBuilderInitialization() {
            }

            public Class buildPartial() {
                Class r02 = new Class(this);
                int i2 = this.bitField0_;
                int i3 = (i2 & 1) != 1 ? 0 : 1;
                r02.flags_ = this.flags_;
                if ((i2 & 2) == 2) {
                    i3 |= 2;
                }
                r02.fqName_ = this.fqName_;
                if ((i2 & 4) == 4) {
                    i3 |= 4;
                }
                r02.companionObjectName_ = this.companionObjectName_;
                if ((this.bitField0_ & 8) == 8) {
                    this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                    this.bitField0_ &= -9;
                }
                r02.typeParameter_ = this.typeParameter_;
                if ((this.bitField0_ & 16) == 16) {
                    this.supertype_ = Collections.unmodifiableList(this.supertype_);
                    this.bitField0_ &= -17;
                }
                r02.supertype_ = this.supertype_;
                if ((this.bitField0_ & 32) == 32) {
                    this.supertypeId_ = Collections.unmodifiableList(this.supertypeId_);
                    this.bitField0_ &= -33;
                }
                r02.supertypeId_ = this.supertypeId_;
                if ((this.bitField0_ & 64) == 64) {
                    this.nestedClassName_ = Collections.unmodifiableList(this.nestedClassName_);
                    this.bitField0_ &= -65;
                }
                r02.nestedClassName_ = this.nestedClassName_;
                if ((this.bitField0_ & 128) == 128) {
                    this.contextReceiverType_ = Collections.unmodifiableList(this.contextReceiverType_);
                    this.bitField0_ &= -129;
                }
                r02.contextReceiverType_ = this.contextReceiverType_;
                if ((this.bitField0_ & 256) == 256) {
                    this.contextReceiverTypeId_ = Collections.unmodifiableList(this.contextReceiverTypeId_);
                    this.bitField0_ &= -257;
                }
                r02.contextReceiverTypeId_ = this.contextReceiverTypeId_;
                if ((this.bitField0_ & 512) == 512) {
                    this.constructor_ = Collections.unmodifiableList(this.constructor_);
                    this.bitField0_ &= -513;
                }
                r02.constructor_ = this.constructor_;
                if ((this.bitField0_ & 1024) == 1024) {
                    this.function_ = Collections.unmodifiableList(this.function_);
                    this.bitField0_ &= -1025;
                }
                r02.function_ = this.function_;
                if ((this.bitField0_ & 2048) == 2048) {
                    this.property_ = Collections.unmodifiableList(this.property_);
                    this.bitField0_ &= -2049;
                }
                r02.property_ = this.property_;
                if ((this.bitField0_ & 4096) == 4096) {
                    this.typeAlias_ = Collections.unmodifiableList(this.typeAlias_);
                    this.bitField0_ &= -4097;
                }
                r02.typeAlias_ = this.typeAlias_;
                if ((this.bitField0_ & 8192) == 8192) {
                    this.enumEntry_ = Collections.unmodifiableList(this.enumEntry_);
                    this.bitField0_ &= -8193;
                }
                r02.enumEntry_ = this.enumEntry_;
                if ((this.bitField0_ & 16384) == 16384) {
                    this.sealedSubclassFqName_ = Collections.unmodifiableList(this.sealedSubclassFqName_);
                    this.bitField0_ &= -16385;
                }
                r02.sealedSubclassFqName_ = this.sealedSubclassFqName_;
                if ((i2 & 32768) == 32768) {
                    i3 |= 8;
                }
                r02.inlineClassUnderlyingPropertyName_ = this.inlineClassUnderlyingPropertyName_;
                if ((i2 & 65536) == 65536) {
                    i3 |= 16;
                }
                r02.inlineClassUnderlyingType_ = this.inlineClassUnderlyingType_;
                if ((i2 & 131072) == 131072) {
                    i3 |= 32;
                }
                r02.inlineClassUnderlyingTypeId_ = this.inlineClassUnderlyingTypeId_;
                if ((this.bitField0_ & 262144) == 262144) {
                    this.multiFieldValueClassUnderlyingName_ = Collections.unmodifiableList(this.multiFieldValueClassUnderlyingName_);
                    this.bitField0_ &= -262145;
                }
                r02.multiFieldValueClassUnderlyingName_ = this.multiFieldValueClassUnderlyingName_;
                if ((this.bitField0_ & 524288) == 524288) {
                    this.multiFieldValueClassUnderlyingType_ = Collections.unmodifiableList(this.multiFieldValueClassUnderlyingType_);
                    this.bitField0_ &= -524289;
                }
                r02.multiFieldValueClassUnderlyingType_ = this.multiFieldValueClassUnderlyingType_;
                if ((this.bitField0_ & 1048576) == 1048576) {
                    this.multiFieldValueClassUnderlyingTypeId_ = Collections.unmodifiableList(this.multiFieldValueClassUnderlyingTypeId_);
                    this.bitField0_ &= -1048577;
                }
                r02.multiFieldValueClassUnderlyingTypeId_ = this.multiFieldValueClassUnderlyingTypeId_;
                if ((i2 & 2097152) == 2097152) {
                    i3 |= 64;
                }
                r02.typeTable_ = this.typeTable_;
                if ((this.bitField0_ & 4194304) == 4194304) {
                    this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                    this.bitField0_ &= -4194305;
                }
                r02.versionRequirement_ = this.versionRequirement_;
                if ((i2 & 8388608) == 8388608) {
                    i3 |= 128;
                }
                r02.versionRequirementTable_ = this.versionRequirementTable_;
                r02.bitField0_ = i3;
                return r02;
            }

            public Constructor getConstructor(int i2) {
                return this.constructor_.get(i2);
            }

            public int getConstructorCount() {
                return this.constructor_.size();
            }

            public Type getContextReceiverType(int i2) {
                return this.contextReceiverType_.get(i2);
            }

            public int getContextReceiverTypeCount() {
                return this.contextReceiverType_.size();
            }

            public EnumEntry getEnumEntry(int i2) {
                return this.enumEntry_.get(i2);
            }

            public int getEnumEntryCount() {
                return this.enumEntry_.size();
            }

            public Function getFunction(int i2) {
                return this.function_.get(i2);
            }

            public int getFunctionCount() {
                return this.function_.size();
            }

            public Type getInlineClassUnderlyingType() {
                return this.inlineClassUnderlyingType_;
            }

            public Type getMultiFieldValueClassUnderlyingType(int i2) {
                return this.multiFieldValueClassUnderlyingType_.get(i2);
            }

            public int getMultiFieldValueClassUnderlyingTypeCount() {
                return this.multiFieldValueClassUnderlyingType_.size();
            }

            public Property getProperty(int i2) {
                return this.property_.get(i2);
            }

            public int getPropertyCount() {
                return this.property_.size();
            }

            public Type getSupertype(int i2) {
                return this.supertype_.get(i2);
            }

            public int getSupertypeCount() {
                return this.supertype_.size();
            }

            public TypeAlias getTypeAlias(int i2) {
                return this.typeAlias_.get(i2);
            }

            public int getTypeAliasCount() {
                return this.typeAlias_.size();
            }

            public TypeParameter getTypeParameter(int i2) {
                return this.typeParameter_.get(i2);
            }

            public int getTypeParameterCount() {
                return this.typeParameter_.size();
            }

            public TypeTable getTypeTable() {
                return this.typeTable_;
            }

            public boolean hasFqName() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean hasInlineClassUnderlyingType() {
                return (this.bitField0_ & 65536) == 65536;
            }

            public boolean hasTypeTable() {
                return (this.bitField0_ & 2097152) == 2097152;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                if (!hasFqName()) {
                    return false;
                }
                for (int i2 = 0; i2 < getTypeParameterCount(); i2++) {
                    if (!getTypeParameter(i2).isInitialized()) {
                        return false;
                    }
                }
                for (int i3 = 0; i3 < getSupertypeCount(); i3++) {
                    if (!getSupertype(i3).isInitialized()) {
                        return false;
                    }
                }
                for (int i4 = 0; i4 < getContextReceiverTypeCount(); i4++) {
                    if (!getContextReceiverType(i4).isInitialized()) {
                        return false;
                    }
                }
                for (int i5 = 0; i5 < getConstructorCount(); i5++) {
                    if (!getConstructor(i5).isInitialized()) {
                        return false;
                    }
                }
                for (int i6 = 0; i6 < getFunctionCount(); i6++) {
                    if (!getFunction(i6).isInitialized()) {
                        return false;
                    }
                }
                for (int i7 = 0; i7 < getPropertyCount(); i7++) {
                    if (!getProperty(i7).isInitialized()) {
                        return false;
                    }
                }
                for (int i8 = 0; i8 < getTypeAliasCount(); i8++) {
                    if (!getTypeAlias(i8).isInitialized()) {
                        return false;
                    }
                }
                for (int i9 = 0; i9 < getEnumEntryCount(); i9++) {
                    if (!getEnumEntry(i9).isInitialized()) {
                        return false;
                    }
                }
                if (hasInlineClassUnderlyingType() && !getInlineClassUnderlyingType().isInitialized()) {
                    return false;
                }
                for (int i10 = 0; i10 < getMultiFieldValueClassUnderlyingTypeCount(); i10++) {
                    if (!getMultiFieldValueClassUnderlyingType(i10).isInitialized()) {
                        return false;
                    }
                }
                return (!hasTypeTable() || getTypeTable().isInitialized()) && extensionsAreInitialized();
            }

            public Builder mergeInlineClassUnderlyingType(Type type) {
                if ((this.bitField0_ & 65536) != 65536 || this.inlineClassUnderlyingType_ == Type.getDefaultInstance()) {
                    this.inlineClassUnderlyingType_ = type;
                } else {
                    this.inlineClassUnderlyingType_ = Type.newBuilder(this.inlineClassUnderlyingType_).mergeFrom(type).buildPartial();
                }
                this.bitField0_ |= 65536;
                return this;
            }

            public Builder mergeTypeTable(TypeTable typeTable) {
                if ((this.bitField0_ & 2097152) != 2097152 || this.typeTable_ == TypeTable.getDefaultInstance()) {
                    this.typeTable_ = typeTable;
                } else {
                    this.typeTable_ = TypeTable.newBuilder(this.typeTable_).mergeFrom(typeTable).buildPartial();
                }
                this.bitField0_ |= 2097152;
                return this;
            }

            public Builder mergeVersionRequirementTable(VersionRequirementTable versionRequirementTable) {
                if ((this.bitField0_ & 8388608) != 8388608 || this.versionRequirementTable_ == VersionRequirementTable.getDefaultInstance()) {
                    this.versionRequirementTable_ = versionRequirementTable;
                } else {
                    this.versionRequirementTable_ = VersionRequirementTable.newBuilder(this.versionRequirementTable_).mergeFrom(versionRequirementTable).buildPartial();
                }
                this.bitField0_ |= 8388608;
                return this;
            }

            public Builder setCompanionObjectName(int i2) {
                this.bitField0_ |= 4;
                this.companionObjectName_ = i2;
                return this;
            }

            public Builder setFlags(int i2) {
                this.bitField0_ |= 1;
                this.flags_ = i2;
                return this;
            }

            public Builder setFqName(int i2) {
                this.bitField0_ |= 2;
                this.fqName_ = i2;
                return this;
            }

            public Builder setInlineClassUnderlyingPropertyName(int i2) {
                this.bitField0_ |= 32768;
                this.inlineClassUnderlyingPropertyName_ = i2;
                return this;
            }

            public Builder setInlineClassUnderlyingTypeId(int i2) {
                this.bitField0_ |= 131072;
                this.inlineClassUnderlyingTypeId_ = i2;
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public Class build() {
                Class classBuildPartial = buildPartial();
                if (classBuildPartial.isInitialized()) {
                    return classBuildPartial;
                }
                throw AbstractMessageLite.Builder.newUninitializedMessageException(classBuildPartial);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public Class getDefaultInstanceForType() {
                return Class.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(Class r3) {
                if (r3 == Class.getDefaultInstance()) {
                    return this;
                }
                if (r3.hasFlags()) {
                    setFlags(r3.getFlags());
                }
                if (r3.hasFqName()) {
                    setFqName(r3.getFqName());
                }
                if (r3.hasCompanionObjectName()) {
                    setCompanionObjectName(r3.getCompanionObjectName());
                }
                if (!r3.typeParameter_.isEmpty()) {
                    if (this.typeParameter_.isEmpty()) {
                        this.typeParameter_ = r3.typeParameter_;
                        this.bitField0_ &= -9;
                    } else {
                        ensureTypeParameterIsMutable();
                        this.typeParameter_.addAll(r3.typeParameter_);
                    }
                }
                if (!r3.supertype_.isEmpty()) {
                    if (this.supertype_.isEmpty()) {
                        this.supertype_ = r3.supertype_;
                        this.bitField0_ &= -17;
                    } else {
                        ensureSupertypeIsMutable();
                        this.supertype_.addAll(r3.supertype_);
                    }
                }
                if (!r3.supertypeId_.isEmpty()) {
                    if (this.supertypeId_.isEmpty()) {
                        this.supertypeId_ = r3.supertypeId_;
                        this.bitField0_ &= -33;
                    } else {
                        ensureSupertypeIdIsMutable();
                        this.supertypeId_.addAll(r3.supertypeId_);
                    }
                }
                if (!r3.nestedClassName_.isEmpty()) {
                    if (this.nestedClassName_.isEmpty()) {
                        this.nestedClassName_ = r3.nestedClassName_;
                        this.bitField0_ &= -65;
                    } else {
                        ensureNestedClassNameIsMutable();
                        this.nestedClassName_.addAll(r3.nestedClassName_);
                    }
                }
                if (!r3.contextReceiverType_.isEmpty()) {
                    if (this.contextReceiverType_.isEmpty()) {
                        this.contextReceiverType_ = r3.contextReceiverType_;
                        this.bitField0_ &= -129;
                    } else {
                        ensureContextReceiverTypeIsMutable();
                        this.contextReceiverType_.addAll(r3.contextReceiverType_);
                    }
                }
                if (!r3.contextReceiverTypeId_.isEmpty()) {
                    if (this.contextReceiverTypeId_.isEmpty()) {
                        this.contextReceiverTypeId_ = r3.contextReceiverTypeId_;
                        this.bitField0_ &= -257;
                    } else {
                        ensureContextReceiverTypeIdIsMutable();
                        this.contextReceiverTypeId_.addAll(r3.contextReceiverTypeId_);
                    }
                }
                if (!r3.constructor_.isEmpty()) {
                    if (this.constructor_.isEmpty()) {
                        this.constructor_ = r3.constructor_;
                        this.bitField0_ &= -513;
                    } else {
                        ensureConstructorIsMutable();
                        this.constructor_.addAll(r3.constructor_);
                    }
                }
                if (!r3.function_.isEmpty()) {
                    if (this.function_.isEmpty()) {
                        this.function_ = r3.function_;
                        this.bitField0_ &= -1025;
                    } else {
                        ensureFunctionIsMutable();
                        this.function_.addAll(r3.function_);
                    }
                }
                if (!r3.property_.isEmpty()) {
                    if (this.property_.isEmpty()) {
                        this.property_ = r3.property_;
                        this.bitField0_ &= -2049;
                    } else {
                        ensurePropertyIsMutable();
                        this.property_.addAll(r3.property_);
                    }
                }
                if (!r3.typeAlias_.isEmpty()) {
                    if (this.typeAlias_.isEmpty()) {
                        this.typeAlias_ = r3.typeAlias_;
                        this.bitField0_ &= -4097;
                    } else {
                        ensureTypeAliasIsMutable();
                        this.typeAlias_.addAll(r3.typeAlias_);
                    }
                }
                if (!r3.enumEntry_.isEmpty()) {
                    if (this.enumEntry_.isEmpty()) {
                        this.enumEntry_ = r3.enumEntry_;
                        this.bitField0_ &= -8193;
                    } else {
                        ensureEnumEntryIsMutable();
                        this.enumEntry_.addAll(r3.enumEntry_);
                    }
                }
                if (!r3.sealedSubclassFqName_.isEmpty()) {
                    if (this.sealedSubclassFqName_.isEmpty()) {
                        this.sealedSubclassFqName_ = r3.sealedSubclassFqName_;
                        this.bitField0_ &= -16385;
                    } else {
                        ensureSealedSubclassFqNameIsMutable();
                        this.sealedSubclassFqName_.addAll(r3.sealedSubclassFqName_);
                    }
                }
                if (r3.hasInlineClassUnderlyingPropertyName()) {
                    setInlineClassUnderlyingPropertyName(r3.getInlineClassUnderlyingPropertyName());
                }
                if (r3.hasInlineClassUnderlyingType()) {
                    mergeInlineClassUnderlyingType(r3.getInlineClassUnderlyingType());
                }
                if (r3.hasInlineClassUnderlyingTypeId()) {
                    setInlineClassUnderlyingTypeId(r3.getInlineClassUnderlyingTypeId());
                }
                if (!r3.multiFieldValueClassUnderlyingName_.isEmpty()) {
                    if (this.multiFieldValueClassUnderlyingName_.isEmpty()) {
                        this.multiFieldValueClassUnderlyingName_ = r3.multiFieldValueClassUnderlyingName_;
                        this.bitField0_ &= -262145;
                    } else {
                        ensureMultiFieldValueClassUnderlyingNameIsMutable();
                        this.multiFieldValueClassUnderlyingName_.addAll(r3.multiFieldValueClassUnderlyingName_);
                    }
                }
                if (!r3.multiFieldValueClassUnderlyingType_.isEmpty()) {
                    if (this.multiFieldValueClassUnderlyingType_.isEmpty()) {
                        this.multiFieldValueClassUnderlyingType_ = r3.multiFieldValueClassUnderlyingType_;
                        this.bitField0_ &= -524289;
                    } else {
                        ensureMultiFieldValueClassUnderlyingTypeIsMutable();
                        this.multiFieldValueClassUnderlyingType_.addAll(r3.multiFieldValueClassUnderlyingType_);
                    }
                }
                if (!r3.multiFieldValueClassUnderlyingTypeId_.isEmpty()) {
                    if (this.multiFieldValueClassUnderlyingTypeId_.isEmpty()) {
                        this.multiFieldValueClassUnderlyingTypeId_ = r3.multiFieldValueClassUnderlyingTypeId_;
                        this.bitField0_ &= -1048577;
                    } else {
                        ensureMultiFieldValueClassUnderlyingTypeIdIsMutable();
                        this.multiFieldValueClassUnderlyingTypeId_.addAll(r3.multiFieldValueClassUnderlyingTypeId_);
                    }
                }
                if (r3.hasTypeTable()) {
                    mergeTypeTable(r3.getTypeTable());
                }
                if (!r3.versionRequirement_.isEmpty()) {
                    if (this.versionRequirement_.isEmpty()) {
                        this.versionRequirement_ = r3.versionRequirement_;
                        this.bitField0_ &= -4194305;
                    } else {
                        ensureVersionRequirementIsMutable();
                        this.versionRequirement_.addAll(r3.versionRequirement_);
                    }
                }
                if (r3.hasVersionRequirementTable()) {
                    mergeVersionRequirementTable(r3.getVersionRequirementTable());
                }
                mergeExtensionFields(r3);
                setUnknownFields(getUnknownFields().concat(r3.unknownFields));
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.ExtendableBuilder, kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo2084clone() {
                return create().mergeFrom(buildPartial());
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Class.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Class> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Class.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Class r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Class) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    if (r3 == 0) goto Le
                    r2.mergeFrom(r3)
                Le:
                    return r2
                Lf:
                    r3 = move-exception
                    goto L1b
                L11:
                    r3 = move-exception
                    kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Class r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Class) r4     // Catch: java.lang.Throwable -> Lf
                    throw r3     // Catch: java.lang.Throwable -> L19
                L19:
                    r3 = move-exception
                    r0 = r4
                L1b:
                    if (r0 == 0) goto L20
                    r2.mergeFrom(r0)
                L20:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Class.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Class$Builder");
            }
        }

        public enum Kind implements Internal.EnumLite {
            CLASS(0, 0),
            INTERFACE(1, 1),
            ENUM_CLASS(2, 2),
            ENUM_ENTRY(3, 3),
            ANNOTATION_CLASS(4, 4),
            OBJECT(5, 5),
            COMPANION_OBJECT(6, 6);

            private static Internal.EnumLiteMap<Kind> internalValueMap = new Internal.EnumLiteMap<Kind>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Class.Kind.1
                @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLiteMap
                public Kind findValueByNumber(int i2) {
                    return Kind.valueOf(i2);
                }
            };
            private final int value;

            Kind(int i2, int i3) {
                this.value = i3;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLite
            public final int getNumber() {
                return this.value;
            }

            public static Kind valueOf(int i2) {
                switch (i2) {
                    case 0:
                        return CLASS;
                    case 1:
                        return INTERFACE;
                    case 2:
                        return ENUM_CLASS;
                    case 3:
                        return ENUM_ENTRY;
                    case 4:
                        return ANNOTATION_CLASS;
                    case 5:
                        return OBJECT;
                    case 6:
                        return COMPANION_OBJECT;
                    default:
                        return null;
                }
            }
        }

        static {
            Class r02 = new Class(true);
            defaultInstance = r02;
            r02.initFields();
        }

        public static Class getDefaultInstance() {
            return defaultInstance;
        }

        private void initFields() {
            this.flags_ = 6;
            this.fqName_ = 0;
            this.companionObjectName_ = 0;
            this.typeParameter_ = Collections.emptyList();
            this.supertype_ = Collections.emptyList();
            this.supertypeId_ = Collections.emptyList();
            this.nestedClassName_ = Collections.emptyList();
            this.contextReceiverType_ = Collections.emptyList();
            this.contextReceiverTypeId_ = Collections.emptyList();
            this.constructor_ = Collections.emptyList();
            this.function_ = Collections.emptyList();
            this.property_ = Collections.emptyList();
            this.typeAlias_ = Collections.emptyList();
            this.enumEntry_ = Collections.emptyList();
            this.sealedSubclassFqName_ = Collections.emptyList();
            this.inlineClassUnderlyingPropertyName_ = 0;
            this.inlineClassUnderlyingType_ = Type.getDefaultInstance();
            this.inlineClassUnderlyingTypeId_ = 0;
            this.multiFieldValueClassUnderlyingName_ = Collections.emptyList();
            this.multiFieldValueClassUnderlyingType_ = Collections.emptyList();
            this.multiFieldValueClassUnderlyingTypeId_ = Collections.emptyList();
            this.typeTable_ = TypeTable.getDefaultInstance();
            this.versionRequirement_ = Collections.emptyList();
            this.versionRequirementTable_ = VersionRequirementTable.getDefaultInstance();
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public static Class parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public int getCompanionObjectName() {
            return this.companionObjectName_;
        }

        public Constructor getConstructor(int i2) {
            return this.constructor_.get(i2);
        }

        public int getConstructorCount() {
            return this.constructor_.size();
        }

        public List<Constructor> getConstructorList() {
            return this.constructor_;
        }

        public Type getContextReceiverType(int i2) {
            return this.contextReceiverType_.get(i2);
        }

        public int getContextReceiverTypeCount() {
            return this.contextReceiverType_.size();
        }

        public List<Integer> getContextReceiverTypeIdList() {
            return this.contextReceiverTypeId_;
        }

        public List<Type> getContextReceiverTypeList() {
            return this.contextReceiverType_;
        }

        public EnumEntry getEnumEntry(int i2) {
            return this.enumEntry_.get(i2);
        }

        public int getEnumEntryCount() {
            return this.enumEntry_.size();
        }

        public List<EnumEntry> getEnumEntryList() {
            return this.enumEntry_;
        }

        public int getFlags() {
            return this.flags_;
        }

        public int getFqName() {
            return this.fqName_;
        }

        public Function getFunction(int i2) {
            return this.function_.get(i2);
        }

        public int getFunctionCount() {
            return this.function_.size();
        }

        public List<Function> getFunctionList() {
            return this.function_;
        }

        public int getInlineClassUnderlyingPropertyName() {
            return this.inlineClassUnderlyingPropertyName_;
        }

        public Type getInlineClassUnderlyingType() {
            return this.inlineClassUnderlyingType_;
        }

        public int getInlineClassUnderlyingTypeId() {
            return this.inlineClassUnderlyingTypeId_;
        }

        public int getMultiFieldValueClassUnderlyingNameCount() {
            return this.multiFieldValueClassUnderlyingName_.size();
        }

        public List<Integer> getMultiFieldValueClassUnderlyingNameList() {
            return this.multiFieldValueClassUnderlyingName_;
        }

        public Type getMultiFieldValueClassUnderlyingType(int i2) {
            return this.multiFieldValueClassUnderlyingType_.get(i2);
        }

        public int getMultiFieldValueClassUnderlyingTypeCount() {
            return this.multiFieldValueClassUnderlyingType_.size();
        }

        public int getMultiFieldValueClassUnderlyingTypeIdCount() {
            return this.multiFieldValueClassUnderlyingTypeId_.size();
        }

        public List<Integer> getMultiFieldValueClassUnderlyingTypeIdList() {
            return this.multiFieldValueClassUnderlyingTypeId_;
        }

        public List<Type> getMultiFieldValueClassUnderlyingTypeList() {
            return this.multiFieldValueClassUnderlyingType_;
        }

        public List<Integer> getNestedClassNameList() {
            return this.nestedClassName_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<Class> getParserForType() {
            return PARSER;
        }

        public Property getProperty(int i2) {
            return this.property_.get(i2);
        }

        public int getPropertyCount() {
            return this.property_.size();
        }

        public List<Property> getPropertyList() {
            return this.property_;
        }

        public List<Integer> getSealedSubclassFqNameList() {
            return this.sealedSubclassFqName_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSerializedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeInt32Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt32Size(1, this.flags_) + 0 : 0;
            int iComputeInt32SizeNoTag = 0;
            for (int i3 = 0; i3 < this.supertypeId_.size(); i3++) {
                iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(this.supertypeId_.get(i3).intValue());
            }
            int iComputeMessageSize = iComputeInt32Size + iComputeInt32SizeNoTag;
            if (!getSupertypeIdList().isEmpty()) {
                iComputeMessageSize = iComputeMessageSize + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag);
            }
            this.supertypeIdMemoizedSerializedSize = iComputeInt32SizeNoTag;
            if ((this.bitField0_ & 2) == 2) {
                iComputeMessageSize += CodedOutputStream.computeInt32Size(3, this.fqName_);
            }
            if ((this.bitField0_ & 4) == 4) {
                iComputeMessageSize += CodedOutputStream.computeInt32Size(4, this.companionObjectName_);
            }
            for (int i4 = 0; i4 < this.typeParameter_.size(); i4++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(5, this.typeParameter_.get(i4));
            }
            for (int i5 = 0; i5 < this.supertype_.size(); i5++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(6, this.supertype_.get(i5));
            }
            int iComputeInt32SizeNoTag2 = 0;
            for (int i6 = 0; i6 < this.nestedClassName_.size(); i6++) {
                iComputeInt32SizeNoTag2 += CodedOutputStream.computeInt32SizeNoTag(this.nestedClassName_.get(i6).intValue());
            }
            int iComputeMessageSize2 = iComputeMessageSize + iComputeInt32SizeNoTag2;
            if (!getNestedClassNameList().isEmpty()) {
                iComputeMessageSize2 = iComputeMessageSize2 + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag2);
            }
            this.nestedClassNameMemoizedSerializedSize = iComputeInt32SizeNoTag2;
            for (int i7 = 0; i7 < this.constructor_.size(); i7++) {
                iComputeMessageSize2 += CodedOutputStream.computeMessageSize(8, this.constructor_.get(i7));
            }
            for (int i8 = 0; i8 < this.function_.size(); i8++) {
                iComputeMessageSize2 += CodedOutputStream.computeMessageSize(9, this.function_.get(i8));
            }
            for (int i9 = 0; i9 < this.property_.size(); i9++) {
                iComputeMessageSize2 += CodedOutputStream.computeMessageSize(10, this.property_.get(i9));
            }
            for (int i10 = 0; i10 < this.typeAlias_.size(); i10++) {
                iComputeMessageSize2 += CodedOutputStream.computeMessageSize(11, this.typeAlias_.get(i10));
            }
            for (int i11 = 0; i11 < this.enumEntry_.size(); i11++) {
                iComputeMessageSize2 += CodedOutputStream.computeMessageSize(13, this.enumEntry_.get(i11));
            }
            int iComputeInt32SizeNoTag3 = 0;
            for (int i12 = 0; i12 < this.sealedSubclassFqName_.size(); i12++) {
                iComputeInt32SizeNoTag3 += CodedOutputStream.computeInt32SizeNoTag(this.sealedSubclassFqName_.get(i12).intValue());
            }
            int iComputeMessageSize3 = iComputeMessageSize2 + iComputeInt32SizeNoTag3;
            if (!getSealedSubclassFqNameList().isEmpty()) {
                iComputeMessageSize3 = iComputeMessageSize3 + 2 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag3);
            }
            this.sealedSubclassFqNameMemoizedSerializedSize = iComputeInt32SizeNoTag3;
            if ((this.bitField0_ & 8) == 8) {
                iComputeMessageSize3 += CodedOutputStream.computeInt32Size(17, this.inlineClassUnderlyingPropertyName_);
            }
            if ((this.bitField0_ & 16) == 16) {
                iComputeMessageSize3 += CodedOutputStream.computeMessageSize(18, this.inlineClassUnderlyingType_);
            }
            if ((this.bitField0_ & 32) == 32) {
                iComputeMessageSize3 += CodedOutputStream.computeInt32Size(19, this.inlineClassUnderlyingTypeId_);
            }
            for (int i13 = 0; i13 < this.contextReceiverType_.size(); i13++) {
                iComputeMessageSize3 += CodedOutputStream.computeMessageSize(20, this.contextReceiverType_.get(i13));
            }
            int iComputeInt32SizeNoTag4 = 0;
            for (int i14 = 0; i14 < this.contextReceiverTypeId_.size(); i14++) {
                iComputeInt32SizeNoTag4 += CodedOutputStream.computeInt32SizeNoTag(this.contextReceiverTypeId_.get(i14).intValue());
            }
            int iComputeInt32SizeNoTag5 = iComputeMessageSize3 + iComputeInt32SizeNoTag4;
            if (!getContextReceiverTypeIdList().isEmpty()) {
                iComputeInt32SizeNoTag5 = iComputeInt32SizeNoTag5 + 2 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag4);
            }
            this.contextReceiverTypeIdMemoizedSerializedSize = iComputeInt32SizeNoTag4;
            int iComputeInt32SizeNoTag6 = 0;
            for (int i15 = 0; i15 < this.multiFieldValueClassUnderlyingName_.size(); i15++) {
                iComputeInt32SizeNoTag6 += CodedOutputStream.computeInt32SizeNoTag(this.multiFieldValueClassUnderlyingName_.get(i15).intValue());
            }
            int iComputeMessageSize4 = iComputeInt32SizeNoTag5 + iComputeInt32SizeNoTag6;
            if (!getMultiFieldValueClassUnderlyingNameList().isEmpty()) {
                iComputeMessageSize4 = iComputeMessageSize4 + 2 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag6);
            }
            this.multiFieldValueClassUnderlyingNameMemoizedSerializedSize = iComputeInt32SizeNoTag6;
            for (int i16 = 0; i16 < this.multiFieldValueClassUnderlyingType_.size(); i16++) {
                iComputeMessageSize4 += CodedOutputStream.computeMessageSize(23, this.multiFieldValueClassUnderlyingType_.get(i16));
            }
            int iComputeInt32SizeNoTag7 = 0;
            for (int i17 = 0; i17 < this.multiFieldValueClassUnderlyingTypeId_.size(); i17++) {
                iComputeInt32SizeNoTag7 += CodedOutputStream.computeInt32SizeNoTag(this.multiFieldValueClassUnderlyingTypeId_.get(i17).intValue());
            }
            int iComputeMessageSize5 = iComputeMessageSize4 + iComputeInt32SizeNoTag7;
            if (!getMultiFieldValueClassUnderlyingTypeIdList().isEmpty()) {
                iComputeMessageSize5 = iComputeMessageSize5 + 2 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag7);
            }
            this.multiFieldValueClassUnderlyingTypeIdMemoizedSerializedSize = iComputeInt32SizeNoTag7;
            if ((this.bitField0_ & 64) == 64) {
                iComputeMessageSize5 += CodedOutputStream.computeMessageSize(30, this.typeTable_);
            }
            int iComputeInt32SizeNoTag8 = 0;
            for (int i18 = 0; i18 < this.versionRequirement_.size(); i18++) {
                iComputeInt32SizeNoTag8 += CodedOutputStream.computeInt32SizeNoTag(this.versionRequirement_.get(i18).intValue());
            }
            int size = iComputeMessageSize5 + iComputeInt32SizeNoTag8 + (getVersionRequirementList().size() * 2);
            if ((this.bitField0_ & 128) == 128) {
                size += CodedOutputStream.computeMessageSize(32, this.versionRequirementTable_);
            }
            int iExtensionsSerializedSize = size + extensionsSerializedSize() + this.unknownFields.size();
            this.memoizedSerializedSize = iExtensionsSerializedSize;
            return iExtensionsSerializedSize;
        }

        public Type getSupertype(int i2) {
            return this.supertype_.get(i2);
        }

        public int getSupertypeCount() {
            return this.supertype_.size();
        }

        public List<Integer> getSupertypeIdList() {
            return this.supertypeId_;
        }

        public List<Type> getSupertypeList() {
            return this.supertype_;
        }

        public TypeAlias getTypeAlias(int i2) {
            return this.typeAlias_.get(i2);
        }

        public int getTypeAliasCount() {
            return this.typeAlias_.size();
        }

        public List<TypeAlias> getTypeAliasList() {
            return this.typeAlias_;
        }

        public TypeParameter getTypeParameter(int i2) {
            return this.typeParameter_.get(i2);
        }

        public int getTypeParameterCount() {
            return this.typeParameter_.size();
        }

        public List<TypeParameter> getTypeParameterList() {
            return this.typeParameter_;
        }

        public TypeTable getTypeTable() {
            return this.typeTable_;
        }

        public List<Integer> getVersionRequirementList() {
            return this.versionRequirement_;
        }

        public VersionRequirementTable getVersionRequirementTable() {
            return this.versionRequirementTable_;
        }

        public boolean hasCompanionObjectName() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean hasFlags() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean hasFqName() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean hasInlineClassUnderlyingPropertyName() {
            return (this.bitField0_ & 8) == 8;
        }

        public boolean hasInlineClassUnderlyingType() {
            return (this.bitField0_ & 16) == 16;
        }

        public boolean hasInlineClassUnderlyingTypeId() {
            return (this.bitField0_ & 32) == 32;
        }

        public boolean hasTypeTable() {
            return (this.bitField0_ & 64) == 64;
        }

        public boolean hasVersionRequirementTable() {
            return (this.bitField0_ & 128) == 128;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b3 = this.memoizedIsInitialized;
            if (b3 == 1) {
                return true;
            }
            if (b3 == 0) {
                return false;
            }
            if (!hasFqName()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            for (int i2 = 0; i2 < getTypeParameterCount(); i2++) {
                if (!getTypeParameter(i2).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i3 = 0; i3 < getSupertypeCount(); i3++) {
                if (!getSupertype(i3).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i4 = 0; i4 < getContextReceiverTypeCount(); i4++) {
                if (!getContextReceiverType(i4).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i5 = 0; i5 < getConstructorCount(); i5++) {
                if (!getConstructor(i5).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i6 = 0; i6 < getFunctionCount(); i6++) {
                if (!getFunction(i6).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i7 = 0; i7 < getPropertyCount(); i7++) {
                if (!getProperty(i7).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i8 = 0; i8 < getTypeAliasCount(); i8++) {
                if (!getTypeAlias(i8).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i9 = 0; i9 < getEnumEntryCount(); i9++) {
                if (!getEnumEntry(i9).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (hasInlineClassUnderlyingType() && !getInlineClassUnderlyingType().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            for (int i10 = 0; i10 < getMultiFieldValueClassUnderlyingTypeCount(); i10++) {
                if (!getMultiFieldValueClassUnderlyingType(i10).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (hasTypeTable() && !getTypeTable().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            if (extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            GeneratedMessageLite.ExtendableMessage<MessageType>.ExtensionWriter extensionWriterNewExtensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeInt32(1, this.flags_);
            }
            if (getSupertypeIdList().size() > 0) {
                codedOutputStream.writeRawVarint32(18);
                codedOutputStream.writeRawVarint32(this.supertypeIdMemoizedSerializedSize);
            }
            for (int i2 = 0; i2 < this.supertypeId_.size(); i2++) {
                codedOutputStream.writeInt32NoTag(this.supertypeId_.get(i2).intValue());
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeInt32(3, this.fqName_);
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeInt32(4, this.companionObjectName_);
            }
            for (int i3 = 0; i3 < this.typeParameter_.size(); i3++) {
                codedOutputStream.writeMessage(5, this.typeParameter_.get(i3));
            }
            for (int i4 = 0; i4 < this.supertype_.size(); i4++) {
                codedOutputStream.writeMessage(6, this.supertype_.get(i4));
            }
            if (getNestedClassNameList().size() > 0) {
                codedOutputStream.writeRawVarint32(58);
                codedOutputStream.writeRawVarint32(this.nestedClassNameMemoizedSerializedSize);
            }
            for (int i5 = 0; i5 < this.nestedClassName_.size(); i5++) {
                codedOutputStream.writeInt32NoTag(this.nestedClassName_.get(i5).intValue());
            }
            for (int i6 = 0; i6 < this.constructor_.size(); i6++) {
                codedOutputStream.writeMessage(8, this.constructor_.get(i6));
            }
            for (int i7 = 0; i7 < this.function_.size(); i7++) {
                codedOutputStream.writeMessage(9, this.function_.get(i7));
            }
            for (int i8 = 0; i8 < this.property_.size(); i8++) {
                codedOutputStream.writeMessage(10, this.property_.get(i8));
            }
            for (int i9 = 0; i9 < this.typeAlias_.size(); i9++) {
                codedOutputStream.writeMessage(11, this.typeAlias_.get(i9));
            }
            for (int i10 = 0; i10 < this.enumEntry_.size(); i10++) {
                codedOutputStream.writeMessage(13, this.enumEntry_.get(i10));
            }
            if (getSealedSubclassFqNameList().size() > 0) {
                codedOutputStream.writeRawVarint32(130);
                codedOutputStream.writeRawVarint32(this.sealedSubclassFqNameMemoizedSerializedSize);
            }
            for (int i11 = 0; i11 < this.sealedSubclassFqName_.size(); i11++) {
                codedOutputStream.writeInt32NoTag(this.sealedSubclassFqName_.get(i11).intValue());
            }
            if ((this.bitField0_ & 8) == 8) {
                codedOutputStream.writeInt32(17, this.inlineClassUnderlyingPropertyName_);
            }
            if ((this.bitField0_ & 16) == 16) {
                codedOutputStream.writeMessage(18, this.inlineClassUnderlyingType_);
            }
            if ((this.bitField0_ & 32) == 32) {
                codedOutputStream.writeInt32(19, this.inlineClassUnderlyingTypeId_);
            }
            for (int i12 = 0; i12 < this.contextReceiverType_.size(); i12++) {
                codedOutputStream.writeMessage(20, this.contextReceiverType_.get(i12));
            }
            if (getContextReceiverTypeIdList().size() > 0) {
                codedOutputStream.writeRawVarint32(R2.anim.welcome_loading);
                codedOutputStream.writeRawVarint32(this.contextReceiverTypeIdMemoizedSerializedSize);
            }
            for (int i13 = 0; i13 < this.contextReceiverTypeId_.size(); i13++) {
                codedOutputStream.writeInt32NoTag(this.contextReceiverTypeId_.get(i13).intValue());
            }
            if (getMultiFieldValueClassUnderlyingNameList().size() > 0) {
                codedOutputStream.writeRawVarint32(178);
                codedOutputStream.writeRawVarint32(this.multiFieldValueClassUnderlyingNameMemoizedSerializedSize);
            }
            for (int i14 = 0; i14 < this.multiFieldValueClassUnderlyingName_.size(); i14++) {
                codedOutputStream.writeInt32NoTag(this.multiFieldValueClassUnderlyingName_.get(i14).intValue());
            }
            for (int i15 = 0; i15 < this.multiFieldValueClassUnderlyingType_.size(); i15++) {
                codedOutputStream.writeMessage(23, this.multiFieldValueClassUnderlyingType_.get(i15));
            }
            if (getMultiFieldValueClassUnderlyingTypeIdList().size() > 0) {
                codedOutputStream.writeRawVarint32(R2.array.ease_numbers_file_suffix);
                codedOutputStream.writeRawVarint32(this.multiFieldValueClassUnderlyingTypeIdMemoizedSerializedSize);
            }
            for (int i16 = 0; i16 < this.multiFieldValueClassUnderlyingTypeId_.size(); i16++) {
                codedOutputStream.writeInt32NoTag(this.multiFieldValueClassUnderlyingTypeId_.get(i16).intValue());
            }
            if ((this.bitField0_ & 64) == 64) {
                codedOutputStream.writeMessage(30, this.typeTable_);
            }
            for (int i17 = 0; i17 < this.versionRequirement_.size(); i17++) {
                codedOutputStream.writeInt32(31, this.versionRequirement_.get(i17).intValue());
            }
            if ((this.bitField0_ & 128) == 128) {
                codedOutputStream.writeMessage(32, this.versionRequirementTable_);
            }
            extensionWriterNewExtensionWriter.writeUntil(R2.id.tvDetail, codedOutputStream);
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        public static Builder newBuilder(Class r12) {
            return newBuilder().mergeFrom(r12);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public Class getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        private Class(GeneratedMessageLite.ExtendableBuilder<Class, ?> extendableBuilder) {
            super(extendableBuilder);
            this.supertypeIdMemoizedSerializedSize = -1;
            this.nestedClassNameMemoizedSerializedSize = -1;
            this.contextReceiverTypeIdMemoizedSerializedSize = -1;
            this.sealedSubclassFqNameMemoizedSerializedSize = -1;
            this.multiFieldValueClassUnderlyingNameMemoizedSerializedSize = -1;
            this.multiFieldValueClassUnderlyingTypeIdMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = extendableBuilder.getUnknownFields();
        }

        private Class(boolean z2) {
            this.supertypeIdMemoizedSerializedSize = -1;
            this.nestedClassNameMemoizedSerializedSize = -1;
            this.contextReceiverTypeIdMemoizedSerializedSize = -1;
            this.sealedSubclassFqNameMemoizedSerializedSize = -1;
            this.multiFieldValueClassUnderlyingNameMemoizedSerializedSize = -1;
            this.multiFieldValueClassUnderlyingTypeIdMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r5v1 */
        /* JADX WARN: Type inference failed for: r5v2 */
        /* JADX WARN: Type inference failed for: r5v4, types: [boolean] */
        /* JADX WARN: Type inference failed for: r8v11 */
        /* JADX WARN: Type inference failed for: r8v13 */
        /* JADX WARN: Type inference failed for: r8v15 */
        /* JADX WARN: Type inference failed for: r8v17 */
        /* JADX WARN: Type inference failed for: r8v19 */
        /* JADX WARN: Type inference failed for: r8v21 */
        /* JADX WARN: Type inference failed for: r8v23 */
        /* JADX WARN: Type inference failed for: r8v25 */
        /* JADX WARN: Type inference failed for: r8v27 */
        /* JADX WARN: Type inference failed for: r8v29 */
        /* JADX WARN: Type inference failed for: r8v3 */
        /* JADX WARN: Type inference failed for: r8v31 */
        /* JADX WARN: Type inference failed for: r8v33 */
        /* JADX WARN: Type inference failed for: r8v35 */
        /* JADX WARN: Type inference failed for: r8v37 */
        /* JADX WARN: Type inference failed for: r8v39 */
        /* JADX WARN: Type inference failed for: r8v41 */
        /* JADX WARN: Type inference failed for: r8v43 */
        /* JADX WARN: Type inference failed for: r8v45 */
        /* JADX WARN: Type inference failed for: r8v47 */
        /* JADX WARN: Type inference failed for: r8v5 */
        /* JADX WARN: Type inference failed for: r8v7 */
        /* JADX WARN: Type inference failed for: r8v9 */
        private Class(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            boolean z2;
            this.supertypeIdMemoizedSerializedSize = -1;
            this.nestedClassNameMemoizedSerializedSize = -1;
            this.contextReceiverTypeIdMemoizedSerializedSize = -1;
            this.sealedSubclassFqNameMemoizedSerializedSize = -1;
            this.multiFieldValueClassUnderlyingNameMemoizedSerializedSize = -1;
            this.multiFieldValueClassUnderlyingTypeIdMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z3 = false;
            char c3 = 0;
            while (true) {
                ?? unknownField = 4194304;
                if (!z3) {
                    try {
                        try {
                            int tag = codedInputStream.readTag();
                            switch (tag) {
                                case 0:
                                    z2 = true;
                                    z3 = z2;
                                    c3 = c3;
                                case 8:
                                    z2 = true;
                                    this.bitField0_ |= 1;
                                    this.flags_ = codedInputStream.readInt32();
                                    c3 = c3;
                                case 16:
                                    int i2 = (c3 == true ? 1 : 0) & 32;
                                    char c4 = c3;
                                    if (i2 != 32) {
                                        this.supertypeId_ = new ArrayList();
                                        c4 = (c3 == true ? 1 : 0) | ' ';
                                    }
                                    this.supertypeId_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    c3 = c4;
                                    z2 = true;
                                    c3 = c3;
                                case 18:
                                    int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                    int i3 = (c3 == true ? 1 : 0) & 32;
                                    char c5 = c3;
                                    if (i3 != 32) {
                                        c5 = c3;
                                        if (codedInputStream.getBytesUntilLimit() > 0) {
                                            this.supertypeId_ = new ArrayList();
                                            c5 = (c3 == true ? 1 : 0) | ' ';
                                        }
                                    }
                                    while (codedInputStream.getBytesUntilLimit() > 0) {
                                        this.supertypeId_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    }
                                    codedInputStream.popLimit(iPushLimit);
                                    c3 = c5;
                                    z2 = true;
                                    c3 = c3;
                                case 24:
                                    this.bitField0_ |= 2;
                                    this.fqName_ = codedInputStream.readInt32();
                                    c3 = c3;
                                    z2 = true;
                                    c3 = c3;
                                case 32:
                                    this.bitField0_ |= 4;
                                    this.companionObjectName_ = codedInputStream.readInt32();
                                    c3 = c3;
                                    z2 = true;
                                    c3 = c3;
                                case 42:
                                    int i4 = (c3 == true ? 1 : 0) & 8;
                                    char c6 = c3;
                                    if (i4 != 8) {
                                        this.typeParameter_ = new ArrayList();
                                        c6 = (c3 == true ? 1 : 0) | '\b';
                                    }
                                    this.typeParameter_.add(codedInputStream.readMessage(TypeParameter.PARSER, extensionRegistryLite));
                                    c3 = c6;
                                    z2 = true;
                                    c3 = c3;
                                case 50:
                                    int i5 = (c3 == true ? 1 : 0) & 16;
                                    char c7 = c3;
                                    if (i5 != 16) {
                                        this.supertype_ = new ArrayList();
                                        c7 = (c3 == true ? 1 : 0) | 16;
                                    }
                                    this.supertype_.add(codedInputStream.readMessage(Type.PARSER, extensionRegistryLite));
                                    c3 = c7;
                                    z2 = true;
                                    c3 = c3;
                                case 56:
                                    int i6 = (c3 == true ? 1 : 0) & 64;
                                    char c8 = c3;
                                    if (i6 != 64) {
                                        this.nestedClassName_ = new ArrayList();
                                        c8 = (c3 == true ? 1 : 0) | '@';
                                    }
                                    this.nestedClassName_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    c3 = c8;
                                    z2 = true;
                                    c3 = c3;
                                case 58:
                                    int iPushLimit2 = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                    int i7 = (c3 == true ? 1 : 0) & 64;
                                    char c9 = c3;
                                    if (i7 != 64) {
                                        c9 = c3;
                                        if (codedInputStream.getBytesUntilLimit() > 0) {
                                            this.nestedClassName_ = new ArrayList();
                                            c9 = (c3 == true ? 1 : 0) | '@';
                                        }
                                    }
                                    while (codedInputStream.getBytesUntilLimit() > 0) {
                                        this.nestedClassName_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    }
                                    codedInputStream.popLimit(iPushLimit2);
                                    c3 = c9;
                                    z2 = true;
                                    c3 = c3;
                                case 66:
                                    int i8 = (c3 == true ? 1 : 0) & 512;
                                    char c10 = c3;
                                    if (i8 != 512) {
                                        this.constructor_ = new ArrayList();
                                        c10 = (c3 == true ? 1 : 0) | 512;
                                    }
                                    this.constructor_.add(codedInputStream.readMessage(Constructor.PARSER, extensionRegistryLite));
                                    c3 = c10;
                                    z2 = true;
                                    c3 = c3;
                                case 74:
                                    int i9 = (c3 == true ? 1 : 0) & 1024;
                                    char c11 = c3;
                                    if (i9 != 1024) {
                                        this.function_ = new ArrayList();
                                        c11 = (c3 == true ? 1 : 0) | 1024;
                                    }
                                    this.function_.add(codedInputStream.readMessage(Function.PARSER, extensionRegistryLite));
                                    c3 = c11;
                                    z2 = true;
                                    c3 = c3;
                                case 82:
                                    int i10 = (c3 == true ? 1 : 0) & 2048;
                                    char c12 = c3;
                                    if (i10 != 2048) {
                                        this.property_ = new ArrayList();
                                        c12 = (c3 == true ? 1 : 0) | 2048;
                                    }
                                    this.property_.add(codedInputStream.readMessage(Property.PARSER, extensionRegistryLite));
                                    c3 = c12;
                                    z2 = true;
                                    c3 = c3;
                                case 90:
                                    int i11 = (c3 == true ? 1 : 0) & 4096;
                                    char c13 = c3;
                                    if (i11 != 4096) {
                                        this.typeAlias_ = new ArrayList();
                                        c13 = (c3 == true ? 1 : 0) | 4096;
                                    }
                                    this.typeAlias_.add(codedInputStream.readMessage(TypeAlias.PARSER, extensionRegistryLite));
                                    c3 = c13;
                                    z2 = true;
                                    c3 = c3;
                                case 106:
                                    int i12 = (c3 == true ? 1 : 0) & 8192;
                                    char c14 = c3;
                                    if (i12 != 8192) {
                                        this.enumEntry_ = new ArrayList();
                                        c14 = (c3 == true ? 1 : 0) | 8192;
                                    }
                                    this.enumEntry_.add(codedInputStream.readMessage(EnumEntry.PARSER, extensionRegistryLite));
                                    c3 = c14;
                                    z2 = true;
                                    c3 = c3;
                                case 128:
                                    int i13 = (c3 == true ? 1 : 0) & 16384;
                                    char c15 = c3;
                                    if (i13 != 16384) {
                                        this.sealedSubclassFqName_ = new ArrayList();
                                        c15 = (c3 == true ? 1 : 0) | 16384;
                                    }
                                    this.sealedSubclassFqName_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    c3 = c15;
                                    z2 = true;
                                    c3 = c3;
                                case 130:
                                    int iPushLimit3 = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                    int i14 = (c3 == true ? 1 : 0) & 16384;
                                    char c16 = c3;
                                    if (i14 != 16384) {
                                        c16 = c3;
                                        if (codedInputStream.getBytesUntilLimit() > 0) {
                                            this.sealedSubclassFqName_ = new ArrayList();
                                            c16 = (c3 == true ? 1 : 0) | 16384;
                                        }
                                    }
                                    while (codedInputStream.getBytesUntilLimit() > 0) {
                                        this.sealedSubclassFqName_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    }
                                    codedInputStream.popLimit(iPushLimit3);
                                    c3 = c16;
                                    z2 = true;
                                    c3 = c3;
                                case 136:
                                    this.bitField0_ |= 8;
                                    this.inlineClassUnderlyingPropertyName_ = codedInputStream.readInt32();
                                    c3 = c3;
                                    z2 = true;
                                    c3 = c3;
                                case 146:
                                    Type.Builder builder = (this.bitField0_ & 16) == 16 ? this.inlineClassUnderlyingType_.toBuilder() : null;
                                    Type type = (Type) codedInputStream.readMessage(Type.PARSER, extensionRegistryLite);
                                    this.inlineClassUnderlyingType_ = type;
                                    if (builder != null) {
                                        builder.mergeFrom(type);
                                        this.inlineClassUnderlyingType_ = builder.buildPartial();
                                    }
                                    this.bitField0_ |= 16;
                                    c3 = c3;
                                    z2 = true;
                                    c3 = c3;
                                case 152:
                                    this.bitField0_ |= 32;
                                    this.inlineClassUnderlyingTypeId_ = codedInputStream.readInt32();
                                    c3 = c3;
                                    z2 = true;
                                    c3 = c3;
                                case 162:
                                    int i15 = (c3 == true ? 1 : 0) & 128;
                                    char c17 = c3;
                                    if (i15 != 128) {
                                        this.contextReceiverType_ = new ArrayList();
                                        c17 = (c3 == true ? 1 : 0) | 128;
                                    }
                                    this.contextReceiverType_.add(codedInputStream.readMessage(Type.PARSER, extensionRegistryLite));
                                    c3 = c17;
                                    z2 = true;
                                    c3 = c3;
                                case R2.anim.voice_from_icon /* 168 */:
                                    int i16 = (c3 == true ? 1 : 0) & 256;
                                    char c18 = c3;
                                    if (i16 != 256) {
                                        this.contextReceiverTypeId_ = new ArrayList();
                                        c18 = (c3 == true ? 1 : 0) | 256;
                                    }
                                    this.contextReceiverTypeId_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    c3 = c18;
                                    z2 = true;
                                    c3 = c3;
                                case R2.anim.welcome_loading /* 170 */:
                                    int iPushLimit4 = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                    int i17 = (c3 == true ? 1 : 0) & 256;
                                    char c19 = c3;
                                    if (i17 != 256) {
                                        c19 = c3;
                                        if (codedInputStream.getBytesUntilLimit() > 0) {
                                            this.contextReceiverTypeId_ = new ArrayList();
                                            c19 = (c3 == true ? 1 : 0) | 256;
                                        }
                                    }
                                    while (codedInputStream.getBytesUntilLimit() > 0) {
                                        this.contextReceiverTypeId_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    }
                                    codedInputStream.popLimit(iPushLimit4);
                                    c3 = c19;
                                    z2 = true;
                                    c3 = c3;
                                case 176:
                                    int i18 = (c3 == true ? 1 : 0) & 262144;
                                    char c20 = c3;
                                    if (i18 != 262144) {
                                        this.multiFieldValueClassUnderlyingName_ = new ArrayList();
                                        c20 = (c3 == true ? 1 : 0) | 0;
                                    }
                                    this.multiFieldValueClassUnderlyingName_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    c3 = c20;
                                    z2 = true;
                                    c3 = c3;
                                case 178:
                                    int iPushLimit5 = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                    int i19 = (c3 == true ? 1 : 0) & 262144;
                                    char c21 = c3;
                                    if (i19 != 262144) {
                                        c21 = c3;
                                        if (codedInputStream.getBytesUntilLimit() > 0) {
                                            this.multiFieldValueClassUnderlyingName_ = new ArrayList();
                                            c21 = (c3 == true ? 1 : 0) | 0;
                                        }
                                    }
                                    while (codedInputStream.getBytesUntilLimit() > 0) {
                                        this.multiFieldValueClassUnderlyingName_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    }
                                    codedInputStream.popLimit(iPushLimit5);
                                    c3 = c21;
                                    z2 = true;
                                    c3 = c3;
                                case 186:
                                    int i20 = (c3 == true ? 1 : 0) & 524288;
                                    char c22 = c3;
                                    if (i20 != 524288) {
                                        this.multiFieldValueClassUnderlyingType_ = new ArrayList();
                                        c22 = (c3 == true ? 1 : 0) | 0;
                                    }
                                    this.multiFieldValueClassUnderlyingType_.add(codedInputStream.readMessage(Type.PARSER, extensionRegistryLite));
                                    c3 = c22;
                                    z2 = true;
                                    c3 = c3;
                                case 192:
                                    int i21 = (c3 == true ? 1 : 0) & 1048576;
                                    char c23 = c3;
                                    if (i21 != 1048576) {
                                        this.multiFieldValueClassUnderlyingTypeId_ = new ArrayList();
                                        c23 = (c3 == true ? 1 : 0) | 0;
                                    }
                                    this.multiFieldValueClassUnderlyingTypeId_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    c3 = c23;
                                    z2 = true;
                                    c3 = c3;
                                case R2.array.ease_numbers_file_suffix /* 194 */:
                                    int iPushLimit6 = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                    int i22 = (c3 == true ? 1 : 0) & 1048576;
                                    char c24 = c3;
                                    if (i22 != 1048576) {
                                        c24 = c3;
                                        if (codedInputStream.getBytesUntilLimit() > 0) {
                                            this.multiFieldValueClassUnderlyingTypeId_ = new ArrayList();
                                            c24 = (c3 == true ? 1 : 0) | 0;
                                        }
                                    }
                                    while (codedInputStream.getBytesUntilLimit() > 0) {
                                        this.multiFieldValueClassUnderlyingTypeId_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    }
                                    codedInputStream.popLimit(iPushLimit6);
                                    c3 = c24;
                                    z2 = true;
                                    c3 = c3;
                                case 242:
                                    TypeTable.Builder builder2 = (this.bitField0_ & 64) == 64 ? this.typeTable_.toBuilder() : null;
                                    TypeTable typeTable = (TypeTable) codedInputStream.readMessage(TypeTable.PARSER, extensionRegistryLite);
                                    this.typeTable_ = typeTable;
                                    if (builder2 != null) {
                                        builder2.mergeFrom(typeTable);
                                        this.typeTable_ = builder2.buildPartial();
                                    }
                                    this.bitField0_ |= 64;
                                    c3 = c3;
                                    z2 = true;
                                    c3 = c3;
                                case R2.attr.actionModeFindDrawable /* 248 */:
                                    int i23 = (c3 == true ? 1 : 0) & 4194304;
                                    char c25 = c3;
                                    if (i23 != 4194304) {
                                        this.versionRequirement_ = new ArrayList();
                                        c25 = (c3 == true ? 1 : 0) | 0;
                                    }
                                    this.versionRequirement_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    c3 = c25;
                                    z2 = true;
                                    c3 = c3;
                                case 250:
                                    int iPushLimit7 = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                    int i24 = (c3 == true ? 1 : 0) & 4194304;
                                    char c26 = c3;
                                    if (i24 != 4194304) {
                                        c26 = c3;
                                        if (codedInputStream.getBytesUntilLimit() > 0) {
                                            this.versionRequirement_ = new ArrayList();
                                            c26 = (c3 == true ? 1 : 0) | 0;
                                        }
                                    }
                                    while (codedInputStream.getBytesUntilLimit() > 0) {
                                        this.versionRequirement_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    }
                                    codedInputStream.popLimit(iPushLimit7);
                                    c3 = c26;
                                    z2 = true;
                                    c3 = c3;
                                case 258:
                                    VersionRequirementTable.Builder builder3 = (this.bitField0_ & 128) == 128 ? this.versionRequirementTable_.toBuilder() : null;
                                    VersionRequirementTable versionRequirementTable = (VersionRequirementTable) codedInputStream.readMessage(VersionRequirementTable.PARSER, extensionRegistryLite);
                                    this.versionRequirementTable_ = versionRequirementTable;
                                    if (builder3 != null) {
                                        builder3.mergeFrom(versionRequirementTable);
                                        this.versionRequirementTable_ = builder3.buildPartial();
                                    }
                                    this.bitField0_ |= 128;
                                    c3 = c3;
                                    z2 = true;
                                    c3 = c3;
                                default:
                                    z2 = true;
                                    unknownField = parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag);
                                    if (unknownField == 0) {
                                        z3 = z2;
                                    }
                                    c3 = c3;
                            }
                        } catch (InvalidProtocolBufferException e2) {
                            throw e2.setUnfinishedMessage(this);
                        } catch (IOException e3) {
                            throw new InvalidProtocolBufferException(e3.getMessage()).setUnfinishedMessage(this);
                        }
                    } catch (Throwable th) {
                        if (((c3 == true ? 1 : 0) & 32) == 32) {
                            this.supertypeId_ = Collections.unmodifiableList(this.supertypeId_);
                        }
                        if (((c3 == true ? 1 : 0) & 8) == 8) {
                            this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                        }
                        if (((c3 == true ? 1 : 0) & 16) == 16) {
                            this.supertype_ = Collections.unmodifiableList(this.supertype_);
                        }
                        if (((c3 == true ? 1 : 0) & 64) == 64) {
                            this.nestedClassName_ = Collections.unmodifiableList(this.nestedClassName_);
                        }
                        if (((c3 == true ? 1 : 0) & 512) == 512) {
                            this.constructor_ = Collections.unmodifiableList(this.constructor_);
                        }
                        if (((c3 == true ? 1 : 0) & 1024) == 1024) {
                            this.function_ = Collections.unmodifiableList(this.function_);
                        }
                        if (((c3 == true ? 1 : 0) & 2048) == 2048) {
                            this.property_ = Collections.unmodifiableList(this.property_);
                        }
                        if (((c3 == true ? 1 : 0) & 4096) == 4096) {
                            this.typeAlias_ = Collections.unmodifiableList(this.typeAlias_);
                        }
                        if (((c3 == true ? 1 : 0) & 8192) == 8192) {
                            this.enumEntry_ = Collections.unmodifiableList(this.enumEntry_);
                        }
                        if (((c3 == true ? 1 : 0) & 16384) == 16384) {
                            this.sealedSubclassFqName_ = Collections.unmodifiableList(this.sealedSubclassFqName_);
                        }
                        if (((c3 == true ? 1 : 0) & 128) == 128) {
                            this.contextReceiverType_ = Collections.unmodifiableList(this.contextReceiverType_);
                        }
                        if (((c3 == true ? 1 : 0) & 256) == 256) {
                            this.contextReceiverTypeId_ = Collections.unmodifiableList(this.contextReceiverTypeId_);
                        }
                        if (((c3 == true ? 1 : 0) & 262144) == 262144) {
                            this.multiFieldValueClassUnderlyingName_ = Collections.unmodifiableList(this.multiFieldValueClassUnderlyingName_);
                        }
                        if (((c3 == true ? 1 : 0) & 524288) == 524288) {
                            this.multiFieldValueClassUnderlyingType_ = Collections.unmodifiableList(this.multiFieldValueClassUnderlyingType_);
                        }
                        if (((c3 == true ? 1 : 0) & 1048576) == 1048576) {
                            this.multiFieldValueClassUnderlyingTypeId_ = Collections.unmodifiableList(this.multiFieldValueClassUnderlyingTypeId_);
                        }
                        if (((c3 == true ? 1 : 0) & unknownField) == unknownField) {
                            this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                        }
                        try {
                            codedOutputStreamNewInstance.flush();
                        } catch (IOException unused) {
                        } catch (Throwable th2) {
                            this.unknownFields = outputNewOutput.toByteString();
                            throw th2;
                        }
                        this.unknownFields = outputNewOutput.toByteString();
                        makeExtensionsImmutable();
                        throw th;
                    }
                } else {
                    if (((c3 == true ? 1 : 0) & 32) == 32) {
                        this.supertypeId_ = Collections.unmodifiableList(this.supertypeId_);
                    }
                    if (((c3 == true ? 1 : 0) & 8) == 8) {
                        this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                    }
                    if (((c3 == true ? 1 : 0) & 16) == 16) {
                        this.supertype_ = Collections.unmodifiableList(this.supertype_);
                    }
                    if (((c3 == true ? 1 : 0) & 64) == 64) {
                        this.nestedClassName_ = Collections.unmodifiableList(this.nestedClassName_);
                    }
                    if (((c3 == true ? 1 : 0) & 512) == 512) {
                        this.constructor_ = Collections.unmodifiableList(this.constructor_);
                    }
                    if (((c3 == true ? 1 : 0) & 1024) == 1024) {
                        this.function_ = Collections.unmodifiableList(this.function_);
                    }
                    if (((c3 == true ? 1 : 0) & 2048) == 2048) {
                        this.property_ = Collections.unmodifiableList(this.property_);
                    }
                    if (((c3 == true ? 1 : 0) & 4096) == 4096) {
                        this.typeAlias_ = Collections.unmodifiableList(this.typeAlias_);
                    }
                    if (((c3 == true ? 1 : 0) & 8192) == 8192) {
                        this.enumEntry_ = Collections.unmodifiableList(this.enumEntry_);
                    }
                    if (((c3 == true ? 1 : 0) & 16384) == 16384) {
                        this.sealedSubclassFqName_ = Collections.unmodifiableList(this.sealedSubclassFqName_);
                    }
                    if (((c3 == true ? 1 : 0) & 128) == 128) {
                        this.contextReceiverType_ = Collections.unmodifiableList(this.contextReceiverType_);
                    }
                    if (((c3 == true ? 1 : 0) & 256) == 256) {
                        this.contextReceiverTypeId_ = Collections.unmodifiableList(this.contextReceiverTypeId_);
                    }
                    if (((c3 == true ? 1 : 0) & 262144) == 262144) {
                        this.multiFieldValueClassUnderlyingName_ = Collections.unmodifiableList(this.multiFieldValueClassUnderlyingName_);
                    }
                    if (((c3 == true ? 1 : 0) & 524288) == 524288) {
                        this.multiFieldValueClassUnderlyingType_ = Collections.unmodifiableList(this.multiFieldValueClassUnderlyingType_);
                    }
                    if (((c3 == true ? 1 : 0) & 1048576) == 1048576) {
                        this.multiFieldValueClassUnderlyingTypeId_ = Collections.unmodifiableList(this.multiFieldValueClassUnderlyingTypeId_);
                    }
                    if (((c3 == true ? 1 : 0) & 4194304) == 4194304) {
                        this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                    }
                    try {
                        codedOutputStreamNewInstance.flush();
                    } catch (IOException unused2) {
                    } catch (Throwable th3) {
                        this.unknownFields = outputNewOutput.toByteString();
                        throw th3;
                    }
                    this.unknownFields = outputNewOutput.toByteString();
                    makeExtensionsImmutable();
                    return;
                }
            }
        }
    }

    public interface ClassOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder {
    }

    public static final class Constructor extends GeneratedMessageLite.ExtendableMessage<Constructor> implements ConstructorOrBuilder {
        public static Parser<Constructor> PARSER = new AbstractParser<Constructor>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Constructor.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public Constructor parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new Constructor(codedInputStream, extensionRegistryLite);
            }
        };
        private static final Constructor defaultInstance;
        private int bitField0_;
        private int flags_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private final ByteString unknownFields;
        private List<ValueParameter> valueParameter_;
        private List<Integer> versionRequirement_;

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<Constructor, Builder> implements ConstructorOrBuilder {
            private int bitField0_;
            private int flags_ = 6;
            private List<ValueParameter> valueParameter_ = Collections.emptyList();
            private List<Integer> versionRequirement_ = Collections.emptyList();

            private Builder() {
                maybeForceBuilderInitialization();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            private void ensureValueParameterIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.valueParameter_ = new ArrayList(this.valueParameter_);
                    this.bitField0_ |= 2;
                }
            }

            private void ensureVersionRequirementIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.versionRequirement_ = new ArrayList(this.versionRequirement_);
                    this.bitField0_ |= 4;
                }
            }

            private void maybeForceBuilderInitialization() {
            }

            public Constructor buildPartial() {
                Constructor constructor = new Constructor(this);
                int i2 = (this.bitField0_ & 1) != 1 ? 0 : 1;
                constructor.flags_ = this.flags_;
                if ((this.bitField0_ & 2) == 2) {
                    this.valueParameter_ = Collections.unmodifiableList(this.valueParameter_);
                    this.bitField0_ &= -3;
                }
                constructor.valueParameter_ = this.valueParameter_;
                if ((this.bitField0_ & 4) == 4) {
                    this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                    this.bitField0_ &= -5;
                }
                constructor.versionRequirement_ = this.versionRequirement_;
                constructor.bitField0_ = i2;
                return constructor;
            }

            public ValueParameter getValueParameter(int i2) {
                return this.valueParameter_.get(i2);
            }

            public int getValueParameterCount() {
                return this.valueParameter_.size();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i2 = 0; i2 < getValueParameterCount(); i2++) {
                    if (!getValueParameter(i2).isInitialized()) {
                        return false;
                    }
                }
                return extensionsAreInitialized();
            }

            public Builder setFlags(int i2) {
                this.bitField0_ |= 1;
                this.flags_ = i2;
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public Constructor build() {
                Constructor constructorBuildPartial = buildPartial();
                if (constructorBuildPartial.isInitialized()) {
                    return constructorBuildPartial;
                }
                throw AbstractMessageLite.Builder.newUninitializedMessageException(constructorBuildPartial);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public Constructor getDefaultInstanceForType() {
                return Constructor.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(Constructor constructor) {
                if (constructor == Constructor.getDefaultInstance()) {
                    return this;
                }
                if (constructor.hasFlags()) {
                    setFlags(constructor.getFlags());
                }
                if (!constructor.valueParameter_.isEmpty()) {
                    if (this.valueParameter_.isEmpty()) {
                        this.valueParameter_ = constructor.valueParameter_;
                        this.bitField0_ &= -3;
                    } else {
                        ensureValueParameterIsMutable();
                        this.valueParameter_.addAll(constructor.valueParameter_);
                    }
                }
                if (!constructor.versionRequirement_.isEmpty()) {
                    if (this.versionRequirement_.isEmpty()) {
                        this.versionRequirement_ = constructor.versionRequirement_;
                        this.bitField0_ &= -5;
                    } else {
                        ensureVersionRequirementIsMutable();
                        this.versionRequirement_.addAll(constructor.versionRequirement_);
                    }
                }
                mergeExtensionFields(constructor);
                setUnknownFields(getUnknownFields().concat(constructor.unknownFields));
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.ExtendableBuilder, kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo2084clone() {
                return create().mergeFrom(buildPartial());
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Constructor.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Constructor> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Constructor.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Constructor r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Constructor) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    if (r3 == 0) goto Le
                    r2.mergeFrom(r3)
                Le:
                    return r2
                Lf:
                    r3 = move-exception
                    goto L1b
                L11:
                    r3 = move-exception
                    kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Constructor r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Constructor) r4     // Catch: java.lang.Throwable -> Lf
                    throw r3     // Catch: java.lang.Throwable -> L19
                L19:
                    r3 = move-exception
                    r0 = r4
                L1b:
                    if (r0 == 0) goto L20
                    r2.mergeFrom(r0)
                L20:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Constructor.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Constructor$Builder");
            }
        }

        static {
            Constructor constructor = new Constructor(true);
            defaultInstance = constructor;
            constructor.initFields();
        }

        public static Constructor getDefaultInstance() {
            return defaultInstance;
        }

        private void initFields() {
            this.flags_ = 6;
            this.valueParameter_ = Collections.emptyList();
            this.versionRequirement_ = Collections.emptyList();
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public int getFlags() {
            return this.flags_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<Constructor> getParserForType() {
            return PARSER;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSerializedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeInt32Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt32Size(1, this.flags_) + 0 : 0;
            for (int i3 = 0; i3 < this.valueParameter_.size(); i3++) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(2, this.valueParameter_.get(i3));
            }
            int iComputeInt32SizeNoTag = 0;
            for (int i4 = 0; i4 < this.versionRequirement_.size(); i4++) {
                iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(this.versionRequirement_.get(i4).intValue());
            }
            int size = iComputeInt32Size + iComputeInt32SizeNoTag + (getVersionRequirementList().size() * 2) + extensionsSerializedSize() + this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
        }

        public ValueParameter getValueParameter(int i2) {
            return this.valueParameter_.get(i2);
        }

        public int getValueParameterCount() {
            return this.valueParameter_.size();
        }

        public List<ValueParameter> getValueParameterList() {
            return this.valueParameter_;
        }

        public List<Integer> getVersionRequirementList() {
            return this.versionRequirement_;
        }

        public boolean hasFlags() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b3 = this.memoizedIsInitialized;
            if (b3 == 1) {
                return true;
            }
            if (b3 == 0) {
                return false;
            }
            for (int i2 = 0; i2 < getValueParameterCount(); i2++) {
                if (!getValueParameter(i2).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            GeneratedMessageLite.ExtendableMessage<MessageType>.ExtensionWriter extensionWriterNewExtensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeInt32(1, this.flags_);
            }
            for (int i2 = 0; i2 < this.valueParameter_.size(); i2++) {
                codedOutputStream.writeMessage(2, this.valueParameter_.get(i2));
            }
            for (int i3 = 0; i3 < this.versionRequirement_.size(); i3++) {
                codedOutputStream.writeInt32(31, this.versionRequirement_.get(i3).intValue());
            }
            extensionWriterNewExtensionWriter.writeUntil(R2.id.tvDetail, codedOutputStream);
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        public static Builder newBuilder(Constructor constructor) {
            return newBuilder().mergeFrom(constructor);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public Constructor getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        private Constructor(GeneratedMessageLite.ExtendableBuilder<Constructor, ?> extendableBuilder) {
            super(extendableBuilder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = extendableBuilder.getUnknownFields();
        }

        private Constructor(boolean z2) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private Constructor(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z2 = false;
            int i2 = 0;
            while (!z2) {
                try {
                    try {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 8) {
                                    this.bitField0_ |= 1;
                                    this.flags_ = codedInputStream.readInt32();
                                } else if (tag == 18) {
                                    if ((i2 & 2) != 2) {
                                        this.valueParameter_ = new ArrayList();
                                        i2 |= 2;
                                    }
                                    this.valueParameter_.add(codedInputStream.readMessage(ValueParameter.PARSER, extensionRegistryLite));
                                } else if (tag == 248) {
                                    if ((i2 & 4) != 4) {
                                        this.versionRequirement_ = new ArrayList();
                                        i2 |= 4;
                                    }
                                    this.versionRequirement_.add(Integer.valueOf(codedInputStream.readInt32()));
                                } else if (tag != 250) {
                                    if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                    if ((i2 & 4) != 4 && codedInputStream.getBytesUntilLimit() > 0) {
                                        this.versionRequirement_ = new ArrayList();
                                        i2 |= 4;
                                    }
                                    while (codedInputStream.getBytesUntilLimit() > 0) {
                                        this.versionRequirement_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    }
                                    codedInputStream.popLimit(iPushLimit);
                                }
                            }
                            z2 = true;
                        } catch (IOException e2) {
                            throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                        }
                    } catch (InvalidProtocolBufferException e3) {
                        throw e3.setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if ((i2 & 2) == 2) {
                        this.valueParameter_ = Collections.unmodifiableList(this.valueParameter_);
                    }
                    if ((i2 & 4) == 4) {
                        this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                    }
                    try {
                        codedOutputStreamNewInstance.flush();
                    } catch (IOException unused) {
                    } catch (Throwable th2) {
                        this.unknownFields = outputNewOutput.toByteString();
                        throw th2;
                    }
                    this.unknownFields = outputNewOutput.toByteString();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((i2 & 2) == 2) {
                this.valueParameter_ = Collections.unmodifiableList(this.valueParameter_);
            }
            if ((i2 & 4) == 4) {
                this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
            }
            try {
                codedOutputStreamNewInstance.flush();
            } catch (IOException unused2) {
            } catch (Throwable th3) {
                this.unknownFields = outputNewOutput.toByteString();
                throw th3;
            }
            this.unknownFields = outputNewOutput.toByteString();
            makeExtensionsImmutable();
        }
    }

    public interface ConstructorOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder {
    }

    public static final class Contract extends GeneratedMessageLite implements ContractOrBuilder {
        public static Parser<Contract> PARSER = new AbstractParser<Contract>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Contract.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public Contract parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new Contract(codedInputStream, extensionRegistryLite);
            }
        };
        private static final Contract defaultInstance;
        private List<Effect> effect_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private final ByteString unknownFields;

        public static final class Builder extends GeneratedMessageLite.Builder<Contract, Builder> implements ContractOrBuilder {
            private int bitField0_;
            private List<Effect> effect_ = Collections.emptyList();

            private Builder() {
                maybeForceBuilderInitialization();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            private void ensureEffectIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.effect_ = new ArrayList(this.effect_);
                    this.bitField0_ |= 1;
                }
            }

            private void maybeForceBuilderInitialization() {
            }

            public Contract buildPartial() {
                Contract contract = new Contract(this);
                if ((this.bitField0_ & 1) == 1) {
                    this.effect_ = Collections.unmodifiableList(this.effect_);
                    this.bitField0_ &= -2;
                }
                contract.effect_ = this.effect_;
                return contract;
            }

            public Effect getEffect(int i2) {
                return this.effect_.get(i2);
            }

            public int getEffectCount() {
                return this.effect_.size();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i2 = 0; i2 < getEffectCount(); i2++) {
                    if (!getEffect(i2).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public Contract build() {
                Contract contractBuildPartial = buildPartial();
                if (contractBuildPartial.isInitialized()) {
                    return contractBuildPartial;
                }
                throw AbstractMessageLite.Builder.newUninitializedMessageException(contractBuildPartial);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public Contract getDefaultInstanceForType() {
                return Contract.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo2084clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(Contract contract) {
                if (contract == Contract.getDefaultInstance()) {
                    return this;
                }
                if (!contract.effect_.isEmpty()) {
                    if (this.effect_.isEmpty()) {
                        this.effect_ = contract.effect_;
                        this.bitField0_ &= -2;
                    } else {
                        ensureEffectIsMutable();
                        this.effect_.addAll(contract.effect_);
                    }
                }
                setUnknownFields(getUnknownFields().concat(contract.unknownFields));
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Contract.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Contract> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Contract.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Contract r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Contract) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    if (r3 == 0) goto Le
                    r2.mergeFrom(r3)
                Le:
                    return r2
                Lf:
                    r3 = move-exception
                    goto L1b
                L11:
                    r3 = move-exception
                    kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Contract r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Contract) r4     // Catch: java.lang.Throwable -> Lf
                    throw r3     // Catch: java.lang.Throwable -> L19
                L19:
                    r3 = move-exception
                    r0 = r4
                L1b:
                    if (r0 == 0) goto L20
                    r2.mergeFrom(r0)
                L20:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Contract.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Contract$Builder");
            }
        }

        static {
            Contract contract = new Contract(true);
            defaultInstance = contract;
            contract.initFields();
        }

        public static Contract getDefaultInstance() {
            return defaultInstance;
        }

        private void initFields() {
            this.effect_ = Collections.emptyList();
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Effect getEffect(int i2) {
            return this.effect_.get(i2);
        }

        public int getEffectCount() {
            return this.effect_.size();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<Contract> getParserForType() {
            return PARSER;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSerializedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeMessageSize = 0;
            for (int i3 = 0; i3 < this.effect_.size(); i3++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(1, this.effect_.get(i3));
            }
            int size = iComputeMessageSize + this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b3 = this.memoizedIsInitialized;
            if (b3 == 1) {
                return true;
            }
            if (b3 == 0) {
                return false;
            }
            for (int i2 = 0; i2 < getEffectCount(); i2++) {
                if (!getEffect(i2).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            for (int i2 = 0; i2 < this.effect_.size(); i2++) {
                codedOutputStream.writeMessage(1, this.effect_.get(i2));
            }
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        public static Builder newBuilder(Contract contract) {
            return newBuilder().mergeFrom(contract);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public Contract getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        private Contract(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private Contract(boolean z2) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private Contract(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z2 = false;
            boolean z3 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag != 10) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                if (!(z3 & true)) {
                                    this.effect_ = new ArrayList();
                                    z3 |= true;
                                }
                                this.effect_.add(codedInputStream.readMessage(Effect.PARSER, extensionRegistryLite));
                            }
                        }
                        z2 = true;
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if (z3 & true) {
                        this.effect_ = Collections.unmodifiableList(this.effect_);
                    }
                    try {
                        codedOutputStreamNewInstance.flush();
                    } catch (IOException unused) {
                    } catch (Throwable th2) {
                        this.unknownFields = outputNewOutput.toByteString();
                        throw th2;
                    }
                    this.unknownFields = outputNewOutput.toByteString();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if (z3 & true) {
                this.effect_ = Collections.unmodifiableList(this.effect_);
            }
            try {
                codedOutputStreamNewInstance.flush();
            } catch (IOException unused2) {
            } catch (Throwable th3) {
                this.unknownFields = outputNewOutput.toByteString();
                throw th3;
            }
            this.unknownFields = outputNewOutput.toByteString();
            makeExtensionsImmutable();
        }
    }

    public interface ContractOrBuilder extends MessageLiteOrBuilder {
    }

    public static final class Effect extends GeneratedMessageLite implements EffectOrBuilder {
        public static Parser<Effect> PARSER = new AbstractParser<Effect>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Effect.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public Effect parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new Effect(codedInputStream, extensionRegistryLite);
            }
        };
        private static final Effect defaultInstance;
        private int bitField0_;
        private Expression conclusionOfConditionalEffect_;
        private List<Expression> effectConstructorArgument_;
        private EffectType effectType_;
        private InvocationKind kind_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private final ByteString unknownFields;

        public static final class Builder extends GeneratedMessageLite.Builder<Effect, Builder> implements EffectOrBuilder {
            private int bitField0_;
            private EffectType effectType_ = EffectType.RETURNS_CONSTANT;
            private List<Expression> effectConstructorArgument_ = Collections.emptyList();
            private Expression conclusionOfConditionalEffect_ = Expression.getDefaultInstance();
            private InvocationKind kind_ = InvocationKind.AT_MOST_ONCE;

            private Builder() {
                maybeForceBuilderInitialization();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            private void ensureEffectConstructorArgumentIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.effectConstructorArgument_ = new ArrayList(this.effectConstructorArgument_);
                    this.bitField0_ |= 2;
                }
            }

            private void maybeForceBuilderInitialization() {
            }

            public Effect buildPartial() {
                Effect effect = new Effect(this);
                int i2 = this.bitField0_;
                int i3 = (i2 & 1) != 1 ? 0 : 1;
                effect.effectType_ = this.effectType_;
                if ((this.bitField0_ & 2) == 2) {
                    this.effectConstructorArgument_ = Collections.unmodifiableList(this.effectConstructorArgument_);
                    this.bitField0_ &= -3;
                }
                effect.effectConstructorArgument_ = this.effectConstructorArgument_;
                if ((i2 & 4) == 4) {
                    i3 |= 2;
                }
                effect.conclusionOfConditionalEffect_ = this.conclusionOfConditionalEffect_;
                if ((i2 & 8) == 8) {
                    i3 |= 4;
                }
                effect.kind_ = this.kind_;
                effect.bitField0_ = i3;
                return effect;
            }

            public Expression getConclusionOfConditionalEffect() {
                return this.conclusionOfConditionalEffect_;
            }

            public Expression getEffectConstructorArgument(int i2) {
                return this.effectConstructorArgument_.get(i2);
            }

            public int getEffectConstructorArgumentCount() {
                return this.effectConstructorArgument_.size();
            }

            public boolean hasConclusionOfConditionalEffect() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i2 = 0; i2 < getEffectConstructorArgumentCount(); i2++) {
                    if (!getEffectConstructorArgument(i2).isInitialized()) {
                        return false;
                    }
                }
                return !hasConclusionOfConditionalEffect() || getConclusionOfConditionalEffect().isInitialized();
            }

            public Builder mergeConclusionOfConditionalEffect(Expression expression) {
                if ((this.bitField0_ & 4) != 4 || this.conclusionOfConditionalEffect_ == Expression.getDefaultInstance()) {
                    this.conclusionOfConditionalEffect_ = expression;
                } else {
                    this.conclusionOfConditionalEffect_ = Expression.newBuilder(this.conclusionOfConditionalEffect_).mergeFrom(expression).buildPartial();
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder setEffectType(EffectType effectType) {
                effectType.getClass();
                this.bitField0_ |= 1;
                this.effectType_ = effectType;
                return this;
            }

            public Builder setKind(InvocationKind invocationKind) {
                invocationKind.getClass();
                this.bitField0_ |= 8;
                this.kind_ = invocationKind;
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public Effect build() {
                Effect effectBuildPartial = buildPartial();
                if (effectBuildPartial.isInitialized()) {
                    return effectBuildPartial;
                }
                throw AbstractMessageLite.Builder.newUninitializedMessageException(effectBuildPartial);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public Effect getDefaultInstanceForType() {
                return Effect.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo2084clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(Effect effect) {
                if (effect == Effect.getDefaultInstance()) {
                    return this;
                }
                if (effect.hasEffectType()) {
                    setEffectType(effect.getEffectType());
                }
                if (!effect.effectConstructorArgument_.isEmpty()) {
                    if (this.effectConstructorArgument_.isEmpty()) {
                        this.effectConstructorArgument_ = effect.effectConstructorArgument_;
                        this.bitField0_ &= -3;
                    } else {
                        ensureEffectConstructorArgumentIsMutable();
                        this.effectConstructorArgument_.addAll(effect.effectConstructorArgument_);
                    }
                }
                if (effect.hasConclusionOfConditionalEffect()) {
                    mergeConclusionOfConditionalEffect(effect.getConclusionOfConditionalEffect());
                }
                if (effect.hasKind()) {
                    setKind(effect.getKind());
                }
                setUnknownFields(getUnknownFields().concat(effect.unknownFields));
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Effect.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Effect> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Effect.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Effect r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Effect) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    if (r3 == 0) goto Le
                    r2.mergeFrom(r3)
                Le:
                    return r2
                Lf:
                    r3 = move-exception
                    goto L1b
                L11:
                    r3 = move-exception
                    kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Effect r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Effect) r4     // Catch: java.lang.Throwable -> Lf
                    throw r3     // Catch: java.lang.Throwable -> L19
                L19:
                    r3 = move-exception
                    r0 = r4
                L1b:
                    if (r0 == 0) goto L20
                    r2.mergeFrom(r0)
                L20:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Effect.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Effect$Builder");
            }
        }

        public enum EffectType implements Internal.EnumLite {
            RETURNS_CONSTANT(0, 0),
            CALLS(1, 1),
            RETURNS_NOT_NULL(2, 2);

            private static Internal.EnumLiteMap<EffectType> internalValueMap = new Internal.EnumLiteMap<EffectType>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Effect.EffectType.1
                @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLiteMap
                public EffectType findValueByNumber(int i2) {
                    return EffectType.valueOf(i2);
                }
            };
            private final int value;

            EffectType(int i2, int i3) {
                this.value = i3;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLite
            public final int getNumber() {
                return this.value;
            }

            public static EffectType valueOf(int i2) {
                if (i2 == 0) {
                    return RETURNS_CONSTANT;
                }
                if (i2 == 1) {
                    return CALLS;
                }
                if (i2 != 2) {
                    return null;
                }
                return RETURNS_NOT_NULL;
            }
        }

        public enum InvocationKind implements Internal.EnumLite {
            AT_MOST_ONCE(0, 0),
            EXACTLY_ONCE(1, 1),
            AT_LEAST_ONCE(2, 2);

            private static Internal.EnumLiteMap<InvocationKind> internalValueMap = new Internal.EnumLiteMap<InvocationKind>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Effect.InvocationKind.1
                @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLiteMap
                public InvocationKind findValueByNumber(int i2) {
                    return InvocationKind.valueOf(i2);
                }
            };
            private final int value;

            InvocationKind(int i2, int i3) {
                this.value = i3;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLite
            public final int getNumber() {
                return this.value;
            }

            public static InvocationKind valueOf(int i2) {
                if (i2 == 0) {
                    return AT_MOST_ONCE;
                }
                if (i2 == 1) {
                    return EXACTLY_ONCE;
                }
                if (i2 != 2) {
                    return null;
                }
                return AT_LEAST_ONCE;
            }
        }

        static {
            Effect effect = new Effect(true);
            defaultInstance = effect;
            effect.initFields();
        }

        public static Effect getDefaultInstance() {
            return defaultInstance;
        }

        private void initFields() {
            this.effectType_ = EffectType.RETURNS_CONSTANT;
            this.effectConstructorArgument_ = Collections.emptyList();
            this.conclusionOfConditionalEffect_ = Expression.getDefaultInstance();
            this.kind_ = InvocationKind.AT_MOST_ONCE;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Expression getConclusionOfConditionalEffect() {
            return this.conclusionOfConditionalEffect_;
        }

        public Expression getEffectConstructorArgument(int i2) {
            return this.effectConstructorArgument_.get(i2);
        }

        public int getEffectConstructorArgumentCount() {
            return this.effectConstructorArgument_.size();
        }

        public EffectType getEffectType() {
            return this.effectType_;
        }

        public InvocationKind getKind() {
            return this.kind_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<Effect> getParserForType() {
            return PARSER;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSerializedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeEnumSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeEnumSize(1, this.effectType_.getNumber()) + 0 : 0;
            for (int i3 = 0; i3 < this.effectConstructorArgument_.size(); i3++) {
                iComputeEnumSize += CodedOutputStream.computeMessageSize(2, this.effectConstructorArgument_.get(i3));
            }
            if ((this.bitField0_ & 2) == 2) {
                iComputeEnumSize += CodedOutputStream.computeMessageSize(3, this.conclusionOfConditionalEffect_);
            }
            if ((this.bitField0_ & 4) == 4) {
                iComputeEnumSize += CodedOutputStream.computeEnumSize(4, this.kind_.getNumber());
            }
            int size = iComputeEnumSize + this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
        }

        public boolean hasConclusionOfConditionalEffect() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean hasEffectType() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean hasKind() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b3 = this.memoizedIsInitialized;
            if (b3 == 1) {
                return true;
            }
            if (b3 == 0) {
                return false;
            }
            for (int i2 = 0; i2 < getEffectConstructorArgumentCount(); i2++) {
                if (!getEffectConstructorArgument(i2).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (!hasConclusionOfConditionalEffect() || getConclusionOfConditionalEffect().isInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeEnum(1, this.effectType_.getNumber());
            }
            for (int i2 = 0; i2 < this.effectConstructorArgument_.size(); i2++) {
                codedOutputStream.writeMessage(2, this.effectConstructorArgument_.get(i2));
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeMessage(3, this.conclusionOfConditionalEffect_);
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeEnum(4, this.kind_.getNumber());
            }
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        public static Builder newBuilder(Effect effect) {
            return newBuilder().mergeFrom(effect);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public Effect getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        private Effect(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private Effect(boolean z2) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private Effect(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z2 = false;
            int i2 = 0;
            while (!z2) {
                try {
                    try {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 8) {
                                    int i3 = codedInputStream.readEnum();
                                    EffectType effectTypeValueOf = EffectType.valueOf(i3);
                                    if (effectTypeValueOf == null) {
                                        codedOutputStreamNewInstance.writeRawVarint32(tag);
                                        codedOutputStreamNewInstance.writeRawVarint32(i3);
                                    } else {
                                        this.bitField0_ |= 1;
                                        this.effectType_ = effectTypeValueOf;
                                    }
                                } else if (tag == 18) {
                                    if ((i2 & 2) != 2) {
                                        this.effectConstructorArgument_ = new ArrayList();
                                        i2 |= 2;
                                    }
                                    this.effectConstructorArgument_.add(codedInputStream.readMessage(Expression.PARSER, extensionRegistryLite));
                                } else if (tag == 26) {
                                    Expression.Builder builder = (this.bitField0_ & 2) == 2 ? this.conclusionOfConditionalEffect_.toBuilder() : null;
                                    Expression expression = (Expression) codedInputStream.readMessage(Expression.PARSER, extensionRegistryLite);
                                    this.conclusionOfConditionalEffect_ = expression;
                                    if (builder != null) {
                                        builder.mergeFrom(expression);
                                        this.conclusionOfConditionalEffect_ = builder.buildPartial();
                                    }
                                    this.bitField0_ |= 2;
                                } else if (tag != 32) {
                                    if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    int i4 = codedInputStream.readEnum();
                                    InvocationKind invocationKindValueOf = InvocationKind.valueOf(i4);
                                    if (invocationKindValueOf == null) {
                                        codedOutputStreamNewInstance.writeRawVarint32(tag);
                                        codedOutputStreamNewInstance.writeRawVarint32(i4);
                                    } else {
                                        this.bitField0_ |= 4;
                                        this.kind_ = invocationKindValueOf;
                                    }
                                }
                            }
                            z2 = true;
                        } catch (IOException e2) {
                            throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                        }
                    } catch (InvalidProtocolBufferException e3) {
                        throw e3.setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if ((i2 & 2) == 2) {
                        this.effectConstructorArgument_ = Collections.unmodifiableList(this.effectConstructorArgument_);
                    }
                    try {
                        codedOutputStreamNewInstance.flush();
                    } catch (IOException unused) {
                    } catch (Throwable th2) {
                        this.unknownFields = outputNewOutput.toByteString();
                        throw th2;
                    }
                    this.unknownFields = outputNewOutput.toByteString();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((i2 & 2) == 2) {
                this.effectConstructorArgument_ = Collections.unmodifiableList(this.effectConstructorArgument_);
            }
            try {
                codedOutputStreamNewInstance.flush();
            } catch (IOException unused2) {
            } catch (Throwable th3) {
                this.unknownFields = outputNewOutput.toByteString();
                throw th3;
            }
            this.unknownFields = outputNewOutput.toByteString();
            makeExtensionsImmutable();
        }
    }

    public interface EffectOrBuilder extends MessageLiteOrBuilder {
    }

    public static final class EnumEntry extends GeneratedMessageLite.ExtendableMessage<EnumEntry> implements EnumEntryOrBuilder {
        public static Parser<EnumEntry> PARSER = new AbstractParser<EnumEntry>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.EnumEntry.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public EnumEntry parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new EnumEntry(codedInputStream, extensionRegistryLite);
            }
        };
        private static final EnumEntry defaultInstance;
        private int bitField0_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private int name_;
        private final ByteString unknownFields;

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<EnumEntry, Builder> implements EnumEntryOrBuilder {
            private int bitField0_;
            private int name_;

            private Builder() {
                maybeForceBuilderInitialization();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            private void maybeForceBuilderInitialization() {
            }

            public EnumEntry buildPartial() {
                EnumEntry enumEntry = new EnumEntry(this);
                int i2 = (this.bitField0_ & 1) != 1 ? 0 : 1;
                enumEntry.name_ = this.name_;
                enumEntry.bitField0_ = i2;
                return enumEntry;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return extensionsAreInitialized();
            }

            public Builder setName(int i2) {
                this.bitField0_ |= 1;
                this.name_ = i2;
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public EnumEntry build() {
                EnumEntry enumEntryBuildPartial = buildPartial();
                if (enumEntryBuildPartial.isInitialized()) {
                    return enumEntryBuildPartial;
                }
                throw AbstractMessageLite.Builder.newUninitializedMessageException(enumEntryBuildPartial);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public EnumEntry getDefaultInstanceForType() {
                return EnumEntry.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(EnumEntry enumEntry) {
                if (enumEntry == EnumEntry.getDefaultInstance()) {
                    return this;
                }
                if (enumEntry.hasName()) {
                    setName(enumEntry.getName());
                }
                mergeExtensionFields(enumEntry);
                setUnknownFields(getUnknownFields().concat(enumEntry.unknownFields));
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.ExtendableBuilder, kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo2084clone() {
                return create().mergeFrom(buildPartial());
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.EnumEntry.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$EnumEntry> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.EnumEntry.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$EnumEntry r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.EnumEntry) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    if (r3 == 0) goto Le
                    r2.mergeFrom(r3)
                Le:
                    return r2
                Lf:
                    r3 = move-exception
                    goto L1b
                L11:
                    r3 = move-exception
                    kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$EnumEntry r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.EnumEntry) r4     // Catch: java.lang.Throwable -> Lf
                    throw r3     // Catch: java.lang.Throwable -> L19
                L19:
                    r3 = move-exception
                    r0 = r4
                L1b:
                    if (r0 == 0) goto L20
                    r2.mergeFrom(r0)
                L20:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.EnumEntry.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$EnumEntry$Builder");
            }
        }

        static {
            EnumEntry enumEntry = new EnumEntry(true);
            defaultInstance = enumEntry;
            enumEntry.initFields();
        }

        public static EnumEntry getDefaultInstance() {
            return defaultInstance;
        }

        private void initFields() {
            this.name_ = 0;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public int getName() {
            return this.name_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<EnumEntry> getParserForType() {
            return PARSER;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSerializedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeInt32Size = ((this.bitField0_ & 1) == 1 ? 0 + CodedOutputStream.computeInt32Size(1, this.name_) : 0) + extensionsSerializedSize() + this.unknownFields.size();
            this.memoizedSerializedSize = iComputeInt32Size;
            return iComputeInt32Size;
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b3 = this.memoizedIsInitialized;
            if (b3 == 1) {
                return true;
            }
            if (b3 == 0) {
                return false;
            }
            if (extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            GeneratedMessageLite.ExtendableMessage<MessageType>.ExtensionWriter extensionWriterNewExtensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeInt32(1, this.name_);
            }
            extensionWriterNewExtensionWriter.writeUntil(200, codedOutputStream);
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        public static Builder newBuilder(EnumEntry enumEntry) {
            return newBuilder().mergeFrom(enumEntry);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public EnumEntry getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        private EnumEntry(GeneratedMessageLite.ExtendableBuilder<EnumEntry, ?> extendableBuilder) {
            super(extendableBuilder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = extendableBuilder.getUnknownFields();
        }

        private EnumEntry(boolean z2) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        private EnumEntry(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z2 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag != 8) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                this.bitField0_ |= 1;
                                this.name_ = codedInputStream.readInt32();
                            }
                        }
                        z2 = true;
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    try {
                        codedOutputStreamNewInstance.flush();
                    } catch (IOException unused) {
                    } catch (Throwable th2) {
                        this.unknownFields = outputNewOutput.toByteString();
                        throw th2;
                    }
                    this.unknownFields = outputNewOutput.toByteString();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            try {
                codedOutputStreamNewInstance.flush();
            } catch (IOException unused2) {
            } catch (Throwable th3) {
                this.unknownFields = outputNewOutput.toByteString();
                throw th3;
            }
            this.unknownFields = outputNewOutput.toByteString();
            makeExtensionsImmutable();
        }
    }

    public interface EnumEntryOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder {
    }

    public static final class Expression extends GeneratedMessageLite implements ExpressionOrBuilder {
        public static Parser<Expression> PARSER = new AbstractParser<Expression>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Expression.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public Expression parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new Expression(codedInputStream, extensionRegistryLite);
            }
        };
        private static final Expression defaultInstance;
        private List<Expression> andArgument_;
        private int bitField0_;
        private ConstantValue constantValue_;
        private int flags_;
        private int isInstanceTypeId_;
        private Type isInstanceType_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<Expression> orArgument_;
        private final ByteString unknownFields;
        private int valueParameterReference_;

        public static final class Builder extends GeneratedMessageLite.Builder<Expression, Builder> implements ExpressionOrBuilder {
            private int bitField0_;
            private int flags_;
            private int isInstanceTypeId_;
            private int valueParameterReference_;
            private ConstantValue constantValue_ = ConstantValue.TRUE;
            private Type isInstanceType_ = Type.getDefaultInstance();
            private List<Expression> andArgument_ = Collections.emptyList();
            private List<Expression> orArgument_ = Collections.emptyList();

            private Builder() {
                maybeForceBuilderInitialization();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            private void ensureAndArgumentIsMutable() {
                if ((this.bitField0_ & 32) != 32) {
                    this.andArgument_ = new ArrayList(this.andArgument_);
                    this.bitField0_ |= 32;
                }
            }

            private void ensureOrArgumentIsMutable() {
                if ((this.bitField0_ & 64) != 64) {
                    this.orArgument_ = new ArrayList(this.orArgument_);
                    this.bitField0_ |= 64;
                }
            }

            private void maybeForceBuilderInitialization() {
            }

            public Expression buildPartial() {
                Expression expression = new Expression(this);
                int i2 = this.bitField0_;
                int i3 = (i2 & 1) != 1 ? 0 : 1;
                expression.flags_ = this.flags_;
                if ((i2 & 2) == 2) {
                    i3 |= 2;
                }
                expression.valueParameterReference_ = this.valueParameterReference_;
                if ((i2 & 4) == 4) {
                    i3 |= 4;
                }
                expression.constantValue_ = this.constantValue_;
                if ((i2 & 8) == 8) {
                    i3 |= 8;
                }
                expression.isInstanceType_ = this.isInstanceType_;
                if ((i2 & 16) == 16) {
                    i3 |= 16;
                }
                expression.isInstanceTypeId_ = this.isInstanceTypeId_;
                if ((this.bitField0_ & 32) == 32) {
                    this.andArgument_ = Collections.unmodifiableList(this.andArgument_);
                    this.bitField0_ &= -33;
                }
                expression.andArgument_ = this.andArgument_;
                if ((this.bitField0_ & 64) == 64) {
                    this.orArgument_ = Collections.unmodifiableList(this.orArgument_);
                    this.bitField0_ &= -65;
                }
                expression.orArgument_ = this.orArgument_;
                expression.bitField0_ = i3;
                return expression;
            }

            public Expression getAndArgument(int i2) {
                return this.andArgument_.get(i2);
            }

            public int getAndArgumentCount() {
                return this.andArgument_.size();
            }

            public Type getIsInstanceType() {
                return this.isInstanceType_;
            }

            public Expression getOrArgument(int i2) {
                return this.orArgument_.get(i2);
            }

            public int getOrArgumentCount() {
                return this.orArgument_.size();
            }

            public boolean hasIsInstanceType() {
                return (this.bitField0_ & 8) == 8;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                if (hasIsInstanceType() && !getIsInstanceType().isInitialized()) {
                    return false;
                }
                for (int i2 = 0; i2 < getAndArgumentCount(); i2++) {
                    if (!getAndArgument(i2).isInitialized()) {
                        return false;
                    }
                }
                for (int i3 = 0; i3 < getOrArgumentCount(); i3++) {
                    if (!getOrArgument(i3).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            public Builder mergeIsInstanceType(Type type) {
                if ((this.bitField0_ & 8) != 8 || this.isInstanceType_ == Type.getDefaultInstance()) {
                    this.isInstanceType_ = type;
                } else {
                    this.isInstanceType_ = Type.newBuilder(this.isInstanceType_).mergeFrom(type).buildPartial();
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder setConstantValue(ConstantValue constantValue) {
                constantValue.getClass();
                this.bitField0_ |= 4;
                this.constantValue_ = constantValue;
                return this;
            }

            public Builder setFlags(int i2) {
                this.bitField0_ |= 1;
                this.flags_ = i2;
                return this;
            }

            public Builder setIsInstanceTypeId(int i2) {
                this.bitField0_ |= 16;
                this.isInstanceTypeId_ = i2;
                return this;
            }

            public Builder setValueParameterReference(int i2) {
                this.bitField0_ |= 2;
                this.valueParameterReference_ = i2;
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public Expression build() {
                Expression expressionBuildPartial = buildPartial();
                if (expressionBuildPartial.isInitialized()) {
                    return expressionBuildPartial;
                }
                throw AbstractMessageLite.Builder.newUninitializedMessageException(expressionBuildPartial);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public Expression getDefaultInstanceForType() {
                return Expression.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo2084clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(Expression expression) {
                if (expression == Expression.getDefaultInstance()) {
                    return this;
                }
                if (expression.hasFlags()) {
                    setFlags(expression.getFlags());
                }
                if (expression.hasValueParameterReference()) {
                    setValueParameterReference(expression.getValueParameterReference());
                }
                if (expression.hasConstantValue()) {
                    setConstantValue(expression.getConstantValue());
                }
                if (expression.hasIsInstanceType()) {
                    mergeIsInstanceType(expression.getIsInstanceType());
                }
                if (expression.hasIsInstanceTypeId()) {
                    setIsInstanceTypeId(expression.getIsInstanceTypeId());
                }
                if (!expression.andArgument_.isEmpty()) {
                    if (this.andArgument_.isEmpty()) {
                        this.andArgument_ = expression.andArgument_;
                        this.bitField0_ &= -33;
                    } else {
                        ensureAndArgumentIsMutable();
                        this.andArgument_.addAll(expression.andArgument_);
                    }
                }
                if (!expression.orArgument_.isEmpty()) {
                    if (this.orArgument_.isEmpty()) {
                        this.orArgument_ = expression.orArgument_;
                        this.bitField0_ &= -65;
                    } else {
                        ensureOrArgumentIsMutable();
                        this.orArgument_.addAll(expression.orArgument_);
                    }
                }
                setUnknownFields(getUnknownFields().concat(expression.unknownFields));
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Expression.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Expression> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Expression.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Expression r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Expression) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    if (r3 == 0) goto Le
                    r2.mergeFrom(r3)
                Le:
                    return r2
                Lf:
                    r3 = move-exception
                    goto L1b
                L11:
                    r3 = move-exception
                    kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Expression r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Expression) r4     // Catch: java.lang.Throwable -> Lf
                    throw r3     // Catch: java.lang.Throwable -> L19
                L19:
                    r3 = move-exception
                    r0 = r4
                L1b:
                    if (r0 == 0) goto L20
                    r2.mergeFrom(r0)
                L20:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Expression.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Expression$Builder");
            }
        }

        public enum ConstantValue implements Internal.EnumLite {
            TRUE(0, 0),
            FALSE(1, 1),
            NULL(2, 2);

            private static Internal.EnumLiteMap<ConstantValue> internalValueMap = new Internal.EnumLiteMap<ConstantValue>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Expression.ConstantValue.1
                @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLiteMap
                public ConstantValue findValueByNumber(int i2) {
                    return ConstantValue.valueOf(i2);
                }
            };
            private final int value;

            ConstantValue(int i2, int i3) {
                this.value = i3;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLite
            public final int getNumber() {
                return this.value;
            }

            public static ConstantValue valueOf(int i2) {
                if (i2 == 0) {
                    return TRUE;
                }
                if (i2 == 1) {
                    return FALSE;
                }
                if (i2 != 2) {
                    return null;
                }
                return NULL;
            }
        }

        static {
            Expression expression = new Expression(true);
            defaultInstance = expression;
            expression.initFields();
        }

        public static Expression getDefaultInstance() {
            return defaultInstance;
        }

        private void initFields() {
            this.flags_ = 0;
            this.valueParameterReference_ = 0;
            this.constantValue_ = ConstantValue.TRUE;
            this.isInstanceType_ = Type.getDefaultInstance();
            this.isInstanceTypeId_ = 0;
            this.andArgument_ = Collections.emptyList();
            this.orArgument_ = Collections.emptyList();
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Expression getAndArgument(int i2) {
            return this.andArgument_.get(i2);
        }

        public int getAndArgumentCount() {
            return this.andArgument_.size();
        }

        public ConstantValue getConstantValue() {
            return this.constantValue_;
        }

        public int getFlags() {
            return this.flags_;
        }

        public Type getIsInstanceType() {
            return this.isInstanceType_;
        }

        public int getIsInstanceTypeId() {
            return this.isInstanceTypeId_;
        }

        public Expression getOrArgument(int i2) {
            return this.orArgument_.get(i2);
        }

        public int getOrArgumentCount() {
            return this.orArgument_.size();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<Expression> getParserForType() {
            return PARSER;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSerializedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeInt32Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt32Size(1, this.flags_) + 0 : 0;
            if ((this.bitField0_ & 2) == 2) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(2, this.valueParameterReference_);
            }
            if ((this.bitField0_ & 4) == 4) {
                iComputeInt32Size += CodedOutputStream.computeEnumSize(3, this.constantValue_.getNumber());
            }
            if ((this.bitField0_ & 8) == 8) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(4, this.isInstanceType_);
            }
            if ((this.bitField0_ & 16) == 16) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(5, this.isInstanceTypeId_);
            }
            for (int i3 = 0; i3 < this.andArgument_.size(); i3++) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(6, this.andArgument_.get(i3));
            }
            for (int i4 = 0; i4 < this.orArgument_.size(); i4++) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(7, this.orArgument_.get(i4));
            }
            int size = iComputeInt32Size + this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
        }

        public int getValueParameterReference() {
            return this.valueParameterReference_;
        }

        public boolean hasConstantValue() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean hasFlags() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean hasIsInstanceType() {
            return (this.bitField0_ & 8) == 8;
        }

        public boolean hasIsInstanceTypeId() {
            return (this.bitField0_ & 16) == 16;
        }

        public boolean hasValueParameterReference() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b3 = this.memoizedIsInitialized;
            if (b3 == 1) {
                return true;
            }
            if (b3 == 0) {
                return false;
            }
            if (hasIsInstanceType() && !getIsInstanceType().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            for (int i2 = 0; i2 < getAndArgumentCount(); i2++) {
                if (!getAndArgument(i2).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i3 = 0; i3 < getOrArgumentCount(); i3++) {
                if (!getOrArgument(i3).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeInt32(1, this.flags_);
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeInt32(2, this.valueParameterReference_);
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeEnum(3, this.constantValue_.getNumber());
            }
            if ((this.bitField0_ & 8) == 8) {
                codedOutputStream.writeMessage(4, this.isInstanceType_);
            }
            if ((this.bitField0_ & 16) == 16) {
                codedOutputStream.writeInt32(5, this.isInstanceTypeId_);
            }
            for (int i2 = 0; i2 < this.andArgument_.size(); i2++) {
                codedOutputStream.writeMessage(6, this.andArgument_.get(i2));
            }
            for (int i3 = 0; i3 < this.orArgument_.size(); i3++) {
                codedOutputStream.writeMessage(7, this.orArgument_.get(i3));
            }
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        public static Builder newBuilder(Expression expression) {
            return newBuilder().mergeFrom(expression);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public Expression getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        private Expression(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private Expression(boolean z2) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private Expression(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z2 = false;
            int i2 = 0;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 8) {
                                this.bitField0_ |= 1;
                                this.flags_ = codedInputStream.readInt32();
                            } else if (tag == 16) {
                                this.bitField0_ |= 2;
                                this.valueParameterReference_ = codedInputStream.readInt32();
                            } else if (tag == 24) {
                                int i3 = codedInputStream.readEnum();
                                ConstantValue constantValueValueOf = ConstantValue.valueOf(i3);
                                if (constantValueValueOf == null) {
                                    codedOutputStreamNewInstance.writeRawVarint32(tag);
                                    codedOutputStreamNewInstance.writeRawVarint32(i3);
                                } else {
                                    this.bitField0_ |= 4;
                                    this.constantValue_ = constantValueValueOf;
                                }
                            } else if (tag == 34) {
                                Type.Builder builder = (this.bitField0_ & 8) == 8 ? this.isInstanceType_.toBuilder() : null;
                                Type type = (Type) codedInputStream.readMessage(Type.PARSER, extensionRegistryLite);
                                this.isInstanceType_ = type;
                                if (builder != null) {
                                    builder.mergeFrom(type);
                                    this.isInstanceType_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 8;
                            } else if (tag == 40) {
                                this.bitField0_ |= 16;
                                this.isInstanceTypeId_ = codedInputStream.readInt32();
                            } else if (tag == 50) {
                                if ((i2 & 32) != 32) {
                                    this.andArgument_ = new ArrayList();
                                    i2 |= 32;
                                }
                                this.andArgument_.add(codedInputStream.readMessage(PARSER, extensionRegistryLite));
                            } else if (tag != 58) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                if ((i2 & 64) != 64) {
                                    this.orArgument_ = new ArrayList();
                                    i2 |= 64;
                                }
                                this.orArgument_.add(codedInputStream.readMessage(PARSER, extensionRegistryLite));
                            }
                        }
                        z2 = true;
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if ((i2 & 32) == 32) {
                        this.andArgument_ = Collections.unmodifiableList(this.andArgument_);
                    }
                    if ((i2 & 64) == 64) {
                        this.orArgument_ = Collections.unmodifiableList(this.orArgument_);
                    }
                    try {
                        codedOutputStreamNewInstance.flush();
                    } catch (IOException unused) {
                    } catch (Throwable th2) {
                        this.unknownFields = outputNewOutput.toByteString();
                        throw th2;
                    }
                    this.unknownFields = outputNewOutput.toByteString();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((i2 & 32) == 32) {
                this.andArgument_ = Collections.unmodifiableList(this.andArgument_);
            }
            if ((i2 & 64) == 64) {
                this.orArgument_ = Collections.unmodifiableList(this.orArgument_);
            }
            try {
                codedOutputStreamNewInstance.flush();
            } catch (IOException unused2) {
            } catch (Throwable th3) {
                this.unknownFields = outputNewOutput.toByteString();
                throw th3;
            }
            this.unknownFields = outputNewOutput.toByteString();
            makeExtensionsImmutable();
        }
    }

    public interface ExpressionOrBuilder extends MessageLiteOrBuilder {
    }

    public static final class Function extends GeneratedMessageLite.ExtendableMessage<Function> implements FunctionOrBuilder {
        public static Parser<Function> PARSER = new AbstractParser<Function>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Function.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public Function parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new Function(codedInputStream, extensionRegistryLite);
            }
        };
        private static final Function defaultInstance;
        private int bitField0_;
        private int contextReceiverTypeIdMemoizedSerializedSize;
        private List<Integer> contextReceiverTypeId_;
        private List<Type> contextReceiverType_;
        private Contract contract_;
        private int flags_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private int name_;
        private int oldFlags_;
        private int receiverTypeId_;
        private Type receiverType_;
        private int returnTypeId_;
        private Type returnType_;
        private List<TypeParameter> typeParameter_;
        private TypeTable typeTable_;
        private final ByteString unknownFields;
        private List<ValueParameter> valueParameter_;
        private List<Integer> versionRequirement_;

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<Function, Builder> implements FunctionOrBuilder {
            private int bitField0_;
            private int name_;
            private int receiverTypeId_;
            private int returnTypeId_;
            private int flags_ = 6;
            private int oldFlags_ = 6;
            private Type returnType_ = Type.getDefaultInstance();
            private List<TypeParameter> typeParameter_ = Collections.emptyList();
            private Type receiverType_ = Type.getDefaultInstance();
            private List<Type> contextReceiverType_ = Collections.emptyList();
            private List<Integer> contextReceiverTypeId_ = Collections.emptyList();
            private List<ValueParameter> valueParameter_ = Collections.emptyList();
            private TypeTable typeTable_ = TypeTable.getDefaultInstance();
            private List<Integer> versionRequirement_ = Collections.emptyList();
            private Contract contract_ = Contract.getDefaultInstance();

            private Builder() {
                maybeForceBuilderInitialization();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            private void ensureContextReceiverTypeIdIsMutable() {
                if ((this.bitField0_ & 512) != 512) {
                    this.contextReceiverTypeId_ = new ArrayList(this.contextReceiverTypeId_);
                    this.bitField0_ |= 512;
                }
            }

            private void ensureContextReceiverTypeIsMutable() {
                if ((this.bitField0_ & 256) != 256) {
                    this.contextReceiverType_ = new ArrayList(this.contextReceiverType_);
                    this.bitField0_ |= 256;
                }
            }

            private void ensureTypeParameterIsMutable() {
                if ((this.bitField0_ & 32) != 32) {
                    this.typeParameter_ = new ArrayList(this.typeParameter_);
                    this.bitField0_ |= 32;
                }
            }

            private void ensureValueParameterIsMutable() {
                if ((this.bitField0_ & 1024) != 1024) {
                    this.valueParameter_ = new ArrayList(this.valueParameter_);
                    this.bitField0_ |= 1024;
                }
            }

            private void ensureVersionRequirementIsMutable() {
                if ((this.bitField0_ & 4096) != 4096) {
                    this.versionRequirement_ = new ArrayList(this.versionRequirement_);
                    this.bitField0_ |= 4096;
                }
            }

            private void maybeForceBuilderInitialization() {
            }

            public Function buildPartial() {
                Function function = new Function(this);
                int i2 = this.bitField0_;
                int i3 = (i2 & 1) != 1 ? 0 : 1;
                function.flags_ = this.flags_;
                if ((i2 & 2) == 2) {
                    i3 |= 2;
                }
                function.oldFlags_ = this.oldFlags_;
                if ((i2 & 4) == 4) {
                    i3 |= 4;
                }
                function.name_ = this.name_;
                if ((i2 & 8) == 8) {
                    i3 |= 8;
                }
                function.returnType_ = this.returnType_;
                if ((i2 & 16) == 16) {
                    i3 |= 16;
                }
                function.returnTypeId_ = this.returnTypeId_;
                if ((this.bitField0_ & 32) == 32) {
                    this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                    this.bitField0_ &= -33;
                }
                function.typeParameter_ = this.typeParameter_;
                if ((i2 & 64) == 64) {
                    i3 |= 32;
                }
                function.receiverType_ = this.receiverType_;
                if ((i2 & 128) == 128) {
                    i3 |= 64;
                }
                function.receiverTypeId_ = this.receiverTypeId_;
                if ((this.bitField0_ & 256) == 256) {
                    this.contextReceiverType_ = Collections.unmodifiableList(this.contextReceiverType_);
                    this.bitField0_ &= -257;
                }
                function.contextReceiverType_ = this.contextReceiverType_;
                if ((this.bitField0_ & 512) == 512) {
                    this.contextReceiverTypeId_ = Collections.unmodifiableList(this.contextReceiverTypeId_);
                    this.bitField0_ &= -513;
                }
                function.contextReceiverTypeId_ = this.contextReceiverTypeId_;
                if ((this.bitField0_ & 1024) == 1024) {
                    this.valueParameter_ = Collections.unmodifiableList(this.valueParameter_);
                    this.bitField0_ &= -1025;
                }
                function.valueParameter_ = this.valueParameter_;
                if ((i2 & 2048) == 2048) {
                    i3 |= 128;
                }
                function.typeTable_ = this.typeTable_;
                if ((this.bitField0_ & 4096) == 4096) {
                    this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                    this.bitField0_ &= -4097;
                }
                function.versionRequirement_ = this.versionRequirement_;
                if ((i2 & 8192) == 8192) {
                    i3 |= 256;
                }
                function.contract_ = this.contract_;
                function.bitField0_ = i3;
                return function;
            }

            public Type getContextReceiverType(int i2) {
                return this.contextReceiverType_.get(i2);
            }

            public int getContextReceiverTypeCount() {
                return this.contextReceiverType_.size();
            }

            public Contract getContract() {
                return this.contract_;
            }

            public Type getReceiverType() {
                return this.receiverType_;
            }

            public Type getReturnType() {
                return this.returnType_;
            }

            public TypeParameter getTypeParameter(int i2) {
                return this.typeParameter_.get(i2);
            }

            public int getTypeParameterCount() {
                return this.typeParameter_.size();
            }

            public TypeTable getTypeTable() {
                return this.typeTable_;
            }

            public ValueParameter getValueParameter(int i2) {
                return this.valueParameter_.get(i2);
            }

            public int getValueParameterCount() {
                return this.valueParameter_.size();
            }

            public boolean hasContract() {
                return (this.bitField0_ & 8192) == 8192;
            }

            public boolean hasName() {
                return (this.bitField0_ & 4) == 4;
            }

            public boolean hasReceiverType() {
                return (this.bitField0_ & 64) == 64;
            }

            public boolean hasReturnType() {
                return (this.bitField0_ & 8) == 8;
            }

            public boolean hasTypeTable() {
                return (this.bitField0_ & 2048) == 2048;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                if (!hasName()) {
                    return false;
                }
                if (hasReturnType() && !getReturnType().isInitialized()) {
                    return false;
                }
                for (int i2 = 0; i2 < getTypeParameterCount(); i2++) {
                    if (!getTypeParameter(i2).isInitialized()) {
                        return false;
                    }
                }
                if (hasReceiverType() && !getReceiverType().isInitialized()) {
                    return false;
                }
                for (int i3 = 0; i3 < getContextReceiverTypeCount(); i3++) {
                    if (!getContextReceiverType(i3).isInitialized()) {
                        return false;
                    }
                }
                for (int i4 = 0; i4 < getValueParameterCount(); i4++) {
                    if (!getValueParameter(i4).isInitialized()) {
                        return false;
                    }
                }
                if (!hasTypeTable() || getTypeTable().isInitialized()) {
                    return (!hasContract() || getContract().isInitialized()) && extensionsAreInitialized();
                }
                return false;
            }

            public Builder mergeContract(Contract contract) {
                if ((this.bitField0_ & 8192) != 8192 || this.contract_ == Contract.getDefaultInstance()) {
                    this.contract_ = contract;
                } else {
                    this.contract_ = Contract.newBuilder(this.contract_).mergeFrom(contract).buildPartial();
                }
                this.bitField0_ |= 8192;
                return this;
            }

            public Builder mergeReceiverType(Type type) {
                if ((this.bitField0_ & 64) != 64 || this.receiverType_ == Type.getDefaultInstance()) {
                    this.receiverType_ = type;
                } else {
                    this.receiverType_ = Type.newBuilder(this.receiverType_).mergeFrom(type).buildPartial();
                }
                this.bitField0_ |= 64;
                return this;
            }

            public Builder mergeReturnType(Type type) {
                if ((this.bitField0_ & 8) != 8 || this.returnType_ == Type.getDefaultInstance()) {
                    this.returnType_ = type;
                } else {
                    this.returnType_ = Type.newBuilder(this.returnType_).mergeFrom(type).buildPartial();
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder mergeTypeTable(TypeTable typeTable) {
                if ((this.bitField0_ & 2048) != 2048 || this.typeTable_ == TypeTable.getDefaultInstance()) {
                    this.typeTable_ = typeTable;
                } else {
                    this.typeTable_ = TypeTable.newBuilder(this.typeTable_).mergeFrom(typeTable).buildPartial();
                }
                this.bitField0_ |= 2048;
                return this;
            }

            public Builder setFlags(int i2) {
                this.bitField0_ |= 1;
                this.flags_ = i2;
                return this;
            }

            public Builder setName(int i2) {
                this.bitField0_ |= 4;
                this.name_ = i2;
                return this;
            }

            public Builder setOldFlags(int i2) {
                this.bitField0_ |= 2;
                this.oldFlags_ = i2;
                return this;
            }

            public Builder setReceiverTypeId(int i2) {
                this.bitField0_ |= 128;
                this.receiverTypeId_ = i2;
                return this;
            }

            public Builder setReturnTypeId(int i2) {
                this.bitField0_ |= 16;
                this.returnTypeId_ = i2;
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public Function build() {
                Function functionBuildPartial = buildPartial();
                if (functionBuildPartial.isInitialized()) {
                    return functionBuildPartial;
                }
                throw AbstractMessageLite.Builder.newUninitializedMessageException(functionBuildPartial);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public Function getDefaultInstanceForType() {
                return Function.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(Function function) {
                if (function == Function.getDefaultInstance()) {
                    return this;
                }
                if (function.hasFlags()) {
                    setFlags(function.getFlags());
                }
                if (function.hasOldFlags()) {
                    setOldFlags(function.getOldFlags());
                }
                if (function.hasName()) {
                    setName(function.getName());
                }
                if (function.hasReturnType()) {
                    mergeReturnType(function.getReturnType());
                }
                if (function.hasReturnTypeId()) {
                    setReturnTypeId(function.getReturnTypeId());
                }
                if (!function.typeParameter_.isEmpty()) {
                    if (this.typeParameter_.isEmpty()) {
                        this.typeParameter_ = function.typeParameter_;
                        this.bitField0_ &= -33;
                    } else {
                        ensureTypeParameterIsMutable();
                        this.typeParameter_.addAll(function.typeParameter_);
                    }
                }
                if (function.hasReceiverType()) {
                    mergeReceiverType(function.getReceiverType());
                }
                if (function.hasReceiverTypeId()) {
                    setReceiverTypeId(function.getReceiverTypeId());
                }
                if (!function.contextReceiverType_.isEmpty()) {
                    if (this.contextReceiverType_.isEmpty()) {
                        this.contextReceiverType_ = function.contextReceiverType_;
                        this.bitField0_ &= -257;
                    } else {
                        ensureContextReceiverTypeIsMutable();
                        this.contextReceiverType_.addAll(function.contextReceiverType_);
                    }
                }
                if (!function.contextReceiverTypeId_.isEmpty()) {
                    if (this.contextReceiverTypeId_.isEmpty()) {
                        this.contextReceiverTypeId_ = function.contextReceiverTypeId_;
                        this.bitField0_ &= -513;
                    } else {
                        ensureContextReceiverTypeIdIsMutable();
                        this.contextReceiverTypeId_.addAll(function.contextReceiverTypeId_);
                    }
                }
                if (!function.valueParameter_.isEmpty()) {
                    if (this.valueParameter_.isEmpty()) {
                        this.valueParameter_ = function.valueParameter_;
                        this.bitField0_ &= -1025;
                    } else {
                        ensureValueParameterIsMutable();
                        this.valueParameter_.addAll(function.valueParameter_);
                    }
                }
                if (function.hasTypeTable()) {
                    mergeTypeTable(function.getTypeTable());
                }
                if (!function.versionRequirement_.isEmpty()) {
                    if (this.versionRequirement_.isEmpty()) {
                        this.versionRequirement_ = function.versionRequirement_;
                        this.bitField0_ &= -4097;
                    } else {
                        ensureVersionRequirementIsMutable();
                        this.versionRequirement_.addAll(function.versionRequirement_);
                    }
                }
                if (function.hasContract()) {
                    mergeContract(function.getContract());
                }
                mergeExtensionFields(function);
                setUnknownFields(getUnknownFields().concat(function.unknownFields));
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.ExtendableBuilder, kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo2084clone() {
                return create().mergeFrom(buildPartial());
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Function.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Function> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Function.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Function r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Function) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    if (r3 == 0) goto Le
                    r2.mergeFrom(r3)
                Le:
                    return r2
                Lf:
                    r3 = move-exception
                    goto L1b
                L11:
                    r3 = move-exception
                    kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Function r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Function) r4     // Catch: java.lang.Throwable -> Lf
                    throw r3     // Catch: java.lang.Throwable -> L19
                L19:
                    r3 = move-exception
                    r0 = r4
                L1b:
                    if (r0 == 0) goto L20
                    r2.mergeFrom(r0)
                L20:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Function.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Function$Builder");
            }
        }

        static {
            Function function = new Function(true);
            defaultInstance = function;
            function.initFields();
        }

        public static Function getDefaultInstance() {
            return defaultInstance;
        }

        private void initFields() {
            this.flags_ = 6;
            this.oldFlags_ = 6;
            this.name_ = 0;
            this.returnType_ = Type.getDefaultInstance();
            this.returnTypeId_ = 0;
            this.typeParameter_ = Collections.emptyList();
            this.receiverType_ = Type.getDefaultInstance();
            this.receiverTypeId_ = 0;
            this.contextReceiverType_ = Collections.emptyList();
            this.contextReceiverTypeId_ = Collections.emptyList();
            this.valueParameter_ = Collections.emptyList();
            this.typeTable_ = TypeTable.getDefaultInstance();
            this.versionRequirement_ = Collections.emptyList();
            this.contract_ = Contract.getDefaultInstance();
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public static Function parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public Type getContextReceiverType(int i2) {
            return this.contextReceiverType_.get(i2);
        }

        public int getContextReceiverTypeCount() {
            return this.contextReceiverType_.size();
        }

        public List<Integer> getContextReceiverTypeIdList() {
            return this.contextReceiverTypeId_;
        }

        public List<Type> getContextReceiverTypeList() {
            return this.contextReceiverType_;
        }

        public Contract getContract() {
            return this.contract_;
        }

        public int getFlags() {
            return this.flags_;
        }

        public int getName() {
            return this.name_;
        }

        public int getOldFlags() {
            return this.oldFlags_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<Function> getParserForType() {
            return PARSER;
        }

        public Type getReceiverType() {
            return this.receiverType_;
        }

        public int getReceiverTypeId() {
            return this.receiverTypeId_;
        }

        public Type getReturnType() {
            return this.returnType_;
        }

        public int getReturnTypeId() {
            return this.returnTypeId_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSerializedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeInt32Size = (this.bitField0_ & 2) == 2 ? CodedOutputStream.computeInt32Size(1, this.oldFlags_) + 0 : 0;
            if ((this.bitField0_ & 4) == 4) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(2, this.name_);
            }
            if ((this.bitField0_ & 8) == 8) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(3, this.returnType_);
            }
            for (int i3 = 0; i3 < this.typeParameter_.size(); i3++) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(4, this.typeParameter_.get(i3));
            }
            if ((this.bitField0_ & 32) == 32) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(5, this.receiverType_);
            }
            for (int i4 = 0; i4 < this.valueParameter_.size(); i4++) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(6, this.valueParameter_.get(i4));
            }
            if ((this.bitField0_ & 16) == 16) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(7, this.returnTypeId_);
            }
            if ((this.bitField0_ & 64) == 64) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(8, this.receiverTypeId_);
            }
            if ((this.bitField0_ & 1) == 1) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(9, this.flags_);
            }
            for (int i5 = 0; i5 < this.contextReceiverType_.size(); i5++) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(10, this.contextReceiverType_.get(i5));
            }
            int iComputeInt32SizeNoTag = 0;
            for (int i6 = 0; i6 < this.contextReceiverTypeId_.size(); i6++) {
                iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(this.contextReceiverTypeId_.get(i6).intValue());
            }
            int iComputeMessageSize = iComputeInt32Size + iComputeInt32SizeNoTag;
            if (!getContextReceiverTypeIdList().isEmpty()) {
                iComputeMessageSize = iComputeMessageSize + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag);
            }
            this.contextReceiverTypeIdMemoizedSerializedSize = iComputeInt32SizeNoTag;
            if ((this.bitField0_ & 128) == 128) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(30, this.typeTable_);
            }
            int iComputeInt32SizeNoTag2 = 0;
            for (int i7 = 0; i7 < this.versionRequirement_.size(); i7++) {
                iComputeInt32SizeNoTag2 += CodedOutputStream.computeInt32SizeNoTag(this.versionRequirement_.get(i7).intValue());
            }
            int size = iComputeMessageSize + iComputeInt32SizeNoTag2 + (getVersionRequirementList().size() * 2);
            if ((this.bitField0_ & 256) == 256) {
                size += CodedOutputStream.computeMessageSize(32, this.contract_);
            }
            int iExtensionsSerializedSize = size + extensionsSerializedSize() + this.unknownFields.size();
            this.memoizedSerializedSize = iExtensionsSerializedSize;
            return iExtensionsSerializedSize;
        }

        public TypeParameter getTypeParameter(int i2) {
            return this.typeParameter_.get(i2);
        }

        public int getTypeParameterCount() {
            return this.typeParameter_.size();
        }

        public List<TypeParameter> getTypeParameterList() {
            return this.typeParameter_;
        }

        public TypeTable getTypeTable() {
            return this.typeTable_;
        }

        public ValueParameter getValueParameter(int i2) {
            return this.valueParameter_.get(i2);
        }

        public int getValueParameterCount() {
            return this.valueParameter_.size();
        }

        public List<ValueParameter> getValueParameterList() {
            return this.valueParameter_;
        }

        public List<Integer> getVersionRequirementList() {
            return this.versionRequirement_;
        }

        public boolean hasContract() {
            return (this.bitField0_ & 256) == 256;
        }

        public boolean hasFlags() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean hasName() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean hasOldFlags() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean hasReceiverType() {
            return (this.bitField0_ & 32) == 32;
        }

        public boolean hasReceiverTypeId() {
            return (this.bitField0_ & 64) == 64;
        }

        public boolean hasReturnType() {
            return (this.bitField0_ & 8) == 8;
        }

        public boolean hasReturnTypeId() {
            return (this.bitField0_ & 16) == 16;
        }

        public boolean hasTypeTable() {
            return (this.bitField0_ & 128) == 128;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b3 = this.memoizedIsInitialized;
            if (b3 == 1) {
                return true;
            }
            if (b3 == 0) {
                return false;
            }
            if (!hasName()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            if (hasReturnType() && !getReturnType().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            for (int i2 = 0; i2 < getTypeParameterCount(); i2++) {
                if (!getTypeParameter(i2).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (hasReceiverType() && !getReceiverType().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            for (int i3 = 0; i3 < getContextReceiverTypeCount(); i3++) {
                if (!getContextReceiverType(i3).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i4 = 0; i4 < getValueParameterCount(); i4++) {
                if (!getValueParameter(i4).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (hasTypeTable() && !getTypeTable().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            if (hasContract() && !getContract().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            if (extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            GeneratedMessageLite.ExtendableMessage<MessageType>.ExtensionWriter extensionWriterNewExtensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeInt32(1, this.oldFlags_);
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeInt32(2, this.name_);
            }
            if ((this.bitField0_ & 8) == 8) {
                codedOutputStream.writeMessage(3, this.returnType_);
            }
            for (int i2 = 0; i2 < this.typeParameter_.size(); i2++) {
                codedOutputStream.writeMessage(4, this.typeParameter_.get(i2));
            }
            if ((this.bitField0_ & 32) == 32) {
                codedOutputStream.writeMessage(5, this.receiverType_);
            }
            for (int i3 = 0; i3 < this.valueParameter_.size(); i3++) {
                codedOutputStream.writeMessage(6, this.valueParameter_.get(i3));
            }
            if ((this.bitField0_ & 16) == 16) {
                codedOutputStream.writeInt32(7, this.returnTypeId_);
            }
            if ((this.bitField0_ & 64) == 64) {
                codedOutputStream.writeInt32(8, this.receiverTypeId_);
            }
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeInt32(9, this.flags_);
            }
            for (int i4 = 0; i4 < this.contextReceiverType_.size(); i4++) {
                codedOutputStream.writeMessage(10, this.contextReceiverType_.get(i4));
            }
            if (getContextReceiverTypeIdList().size() > 0) {
                codedOutputStream.writeRawVarint32(90);
                codedOutputStream.writeRawVarint32(this.contextReceiverTypeIdMemoizedSerializedSize);
            }
            for (int i5 = 0; i5 < this.contextReceiverTypeId_.size(); i5++) {
                codedOutputStream.writeInt32NoTag(this.contextReceiverTypeId_.get(i5).intValue());
            }
            if ((this.bitField0_ & 128) == 128) {
                codedOutputStream.writeMessage(30, this.typeTable_);
            }
            for (int i6 = 0; i6 < this.versionRequirement_.size(); i6++) {
                codedOutputStream.writeInt32(31, this.versionRequirement_.get(i6).intValue());
            }
            if ((this.bitField0_ & 256) == 256) {
                codedOutputStream.writeMessage(32, this.contract_);
            }
            extensionWriterNewExtensionWriter.writeUntil(R2.id.tvDetail, codedOutputStream);
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        public static Builder newBuilder(Function function) {
            return newBuilder().mergeFrom(function);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public Function getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        private Function(GeneratedMessageLite.ExtendableBuilder<Function, ?> extendableBuilder) {
            super(extendableBuilder);
            this.contextReceiverTypeIdMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = extendableBuilder.getUnknownFields();
        }

        private Function(boolean z2) {
            this.contextReceiverTypeIdMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r4v11 */
        /* JADX WARN: Type inference failed for: r4v13 */
        /* JADX WARN: Type inference failed for: r4v15 */
        /* JADX WARN: Type inference failed for: r4v3 */
        /* JADX WARN: Type inference failed for: r4v5 */
        /* JADX WARN: Type inference failed for: r4v7 */
        /* JADX WARN: Type inference failed for: r4v9 */
        /* JADX WARN: Type inference failed for: r5v0 */
        /* JADX WARN: Type inference failed for: r5v1 */
        /* JADX WARN: Type inference failed for: r5v2, types: [boolean] */
        private Function(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.contextReceiverTypeIdMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z2 = false;
            char c3 = 0;
            while (true) {
                ?? unknownField = 1024;
                if (!z2) {
                    try {
                        try {
                            try {
                                int tag = codedInputStream.readTag();
                                switch (tag) {
                                    case 0:
                                        z2 = true;
                                    case 8:
                                        this.bitField0_ |= 2;
                                        this.oldFlags_ = codedInputStream.readInt32();
                                    case 16:
                                        this.bitField0_ |= 4;
                                        this.name_ = codedInputStream.readInt32();
                                    case 26:
                                        Type.Builder builder = (this.bitField0_ & 8) == 8 ? this.returnType_.toBuilder() : null;
                                        Type type = (Type) codedInputStream.readMessage(Type.PARSER, extensionRegistryLite);
                                        this.returnType_ = type;
                                        if (builder != null) {
                                            builder.mergeFrom(type);
                                            this.returnType_ = builder.buildPartial();
                                        }
                                        this.bitField0_ |= 8;
                                    case 34:
                                        int i2 = (c3 == true ? 1 : 0) & 32;
                                        c3 = c3;
                                        if (i2 != 32) {
                                            this.typeParameter_ = new ArrayList();
                                            c3 = (c3 == true ? 1 : 0) | ' ';
                                        }
                                        this.typeParameter_.add(codedInputStream.readMessage(TypeParameter.PARSER, extensionRegistryLite));
                                    case 42:
                                        Type.Builder builder2 = (this.bitField0_ & 32) == 32 ? this.receiverType_.toBuilder() : null;
                                        Type type2 = (Type) codedInputStream.readMessage(Type.PARSER, extensionRegistryLite);
                                        this.receiverType_ = type2;
                                        if (builder2 != null) {
                                            builder2.mergeFrom(type2);
                                            this.receiverType_ = builder2.buildPartial();
                                        }
                                        this.bitField0_ |= 32;
                                    case 50:
                                        int i3 = (c3 == true ? 1 : 0) & 1024;
                                        c3 = c3;
                                        if (i3 != 1024) {
                                            this.valueParameter_ = new ArrayList();
                                            c3 = (c3 == true ? 1 : 0) | 1024;
                                        }
                                        this.valueParameter_.add(codedInputStream.readMessage(ValueParameter.PARSER, extensionRegistryLite));
                                    case 56:
                                        this.bitField0_ |= 16;
                                        this.returnTypeId_ = codedInputStream.readInt32();
                                    case 64:
                                        this.bitField0_ |= 64;
                                        this.receiverTypeId_ = codedInputStream.readInt32();
                                    case 72:
                                        this.bitField0_ |= 1;
                                        this.flags_ = codedInputStream.readInt32();
                                    case 82:
                                        int i4 = (c3 == true ? 1 : 0) & 256;
                                        c3 = c3;
                                        if (i4 != 256) {
                                            this.contextReceiverType_ = new ArrayList();
                                            c3 = (c3 == true ? 1 : 0) | 256;
                                        }
                                        this.contextReceiverType_.add(codedInputStream.readMessage(Type.PARSER, extensionRegistryLite));
                                    case 88:
                                        int i5 = (c3 == true ? 1 : 0) & 512;
                                        c3 = c3;
                                        if (i5 != 512) {
                                            this.contextReceiverTypeId_ = new ArrayList();
                                            c3 = (c3 == true ? 1 : 0) | 512;
                                        }
                                        this.contextReceiverTypeId_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    case 90:
                                        int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                        int i6 = (c3 == true ? 1 : 0) & 512;
                                        c3 = c3;
                                        if (i6 != 512) {
                                            c3 = c3;
                                            if (codedInputStream.getBytesUntilLimit() > 0) {
                                                this.contextReceiverTypeId_ = new ArrayList();
                                                c3 = (c3 == true ? 1 : 0) | 512;
                                            }
                                        }
                                        while (codedInputStream.getBytesUntilLimit() > 0) {
                                            this.contextReceiverTypeId_.add(Integer.valueOf(codedInputStream.readInt32()));
                                        }
                                        codedInputStream.popLimit(iPushLimit);
                                    case 242:
                                        TypeTable.Builder builder3 = (this.bitField0_ & 128) == 128 ? this.typeTable_.toBuilder() : null;
                                        TypeTable typeTable = (TypeTable) codedInputStream.readMessage(TypeTable.PARSER, extensionRegistryLite);
                                        this.typeTable_ = typeTable;
                                        if (builder3 != null) {
                                            builder3.mergeFrom(typeTable);
                                            this.typeTable_ = builder3.buildPartial();
                                        }
                                        this.bitField0_ |= 128;
                                    case R2.attr.actionModeFindDrawable /* 248 */:
                                        int i7 = (c3 == true ? 1 : 0) & 4096;
                                        c3 = c3;
                                        if (i7 != 4096) {
                                            this.versionRequirement_ = new ArrayList();
                                            c3 = (c3 == true ? 1 : 0) | 4096;
                                        }
                                        this.versionRequirement_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    case 250:
                                        int iPushLimit2 = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                        int i8 = (c3 == true ? 1 : 0) & 4096;
                                        c3 = c3;
                                        if (i8 != 4096) {
                                            c3 = c3;
                                            if (codedInputStream.getBytesUntilLimit() > 0) {
                                                this.versionRequirement_ = new ArrayList();
                                                c3 = (c3 == true ? 1 : 0) | 4096;
                                            }
                                        }
                                        while (codedInputStream.getBytesUntilLimit() > 0) {
                                            this.versionRequirement_.add(Integer.valueOf(codedInputStream.readInt32()));
                                        }
                                        codedInputStream.popLimit(iPushLimit2);
                                    case 258:
                                        Contract.Builder builder4 = (this.bitField0_ & 256) == 256 ? this.contract_.toBuilder() : null;
                                        Contract contract = (Contract) codedInputStream.readMessage(Contract.PARSER, extensionRegistryLite);
                                        this.contract_ = contract;
                                        if (builder4 != null) {
                                            builder4.mergeFrom(contract);
                                            this.contract_ = builder4.buildPartial();
                                        }
                                        this.bitField0_ |= 256;
                                    default:
                                        unknownField = parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag);
                                        if (unknownField == 0) {
                                            z2 = true;
                                        }
                                }
                            } catch (InvalidProtocolBufferException e2) {
                                throw e2.setUnfinishedMessage(this);
                            }
                        } catch (IOException e3) {
                            throw new InvalidProtocolBufferException(e3.getMessage()).setUnfinishedMessage(this);
                        }
                    } catch (Throwable th) {
                        if (((c3 == true ? 1 : 0) & 32) == 32) {
                            this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                        }
                        if (((c3 == true ? 1 : 0) & 1024) == unknownField) {
                            this.valueParameter_ = Collections.unmodifiableList(this.valueParameter_);
                        }
                        if (((c3 == true ? 1 : 0) & 256) == 256) {
                            this.contextReceiverType_ = Collections.unmodifiableList(this.contextReceiverType_);
                        }
                        if (((c3 == true ? 1 : 0) & 512) == 512) {
                            this.contextReceiverTypeId_ = Collections.unmodifiableList(this.contextReceiverTypeId_);
                        }
                        if (((c3 == true ? 1 : 0) & 4096) == 4096) {
                            this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                        }
                        try {
                            codedOutputStreamNewInstance.flush();
                        } catch (IOException unused) {
                        } catch (Throwable th2) {
                            this.unknownFields = outputNewOutput.toByteString();
                            throw th2;
                        }
                        this.unknownFields = outputNewOutput.toByteString();
                        makeExtensionsImmutable();
                        throw th;
                    }
                } else {
                    if (((c3 == true ? 1 : 0) & 32) == 32) {
                        this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                    }
                    if (((c3 == true ? 1 : 0) & 1024) == 1024) {
                        this.valueParameter_ = Collections.unmodifiableList(this.valueParameter_);
                    }
                    if (((c3 == true ? 1 : 0) & 256) == 256) {
                        this.contextReceiverType_ = Collections.unmodifiableList(this.contextReceiverType_);
                    }
                    if (((c3 == true ? 1 : 0) & 512) == 512) {
                        this.contextReceiverTypeId_ = Collections.unmodifiableList(this.contextReceiverTypeId_);
                    }
                    if (((c3 == true ? 1 : 0) & 4096) == 4096) {
                        this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                    }
                    try {
                        codedOutputStreamNewInstance.flush();
                    } catch (IOException unused2) {
                    } catch (Throwable th3) {
                        this.unknownFields = outputNewOutput.toByteString();
                        throw th3;
                    }
                    this.unknownFields = outputNewOutput.toByteString();
                    makeExtensionsImmutable();
                    return;
                }
            }
        }
    }

    public interface FunctionOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder {
    }

    public enum MemberKind implements Internal.EnumLite {
        DECLARATION(0, 0),
        FAKE_OVERRIDE(1, 1),
        DELEGATION(2, 2),
        SYNTHESIZED(3, 3);

        private static Internal.EnumLiteMap<MemberKind> internalValueMap = new Internal.EnumLiteMap<MemberKind>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.MemberKind.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLiteMap
            public MemberKind findValueByNumber(int i2) {
                return MemberKind.valueOf(i2);
            }
        };
        private final int value;

        MemberKind(int i2, int i3) {
            this.value = i3;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLite
        public final int getNumber() {
            return this.value;
        }

        public static MemberKind valueOf(int i2) {
            if (i2 == 0) {
                return DECLARATION;
            }
            if (i2 == 1) {
                return FAKE_OVERRIDE;
            }
            if (i2 == 2) {
                return DELEGATION;
            }
            if (i2 != 3) {
                return null;
            }
            return SYNTHESIZED;
        }
    }

    public enum Modality implements Internal.EnumLite {
        FINAL(0, 0),
        OPEN(1, 1),
        ABSTRACT(2, 2),
        SEALED(3, 3);

        private static Internal.EnumLiteMap<Modality> internalValueMap = new Internal.EnumLiteMap<Modality>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Modality.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLiteMap
            public Modality findValueByNumber(int i2) {
                return Modality.valueOf(i2);
            }
        };
        private final int value;

        Modality(int i2, int i3) {
            this.value = i3;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLite
        public final int getNumber() {
            return this.value;
        }

        public static Modality valueOf(int i2) {
            if (i2 == 0) {
                return FINAL;
            }
            if (i2 == 1) {
                return OPEN;
            }
            if (i2 == 2) {
                return ABSTRACT;
            }
            if (i2 != 3) {
                return null;
            }
            return SEALED;
        }
    }

    public static final class Package extends GeneratedMessageLite.ExtendableMessage<Package> implements PackageOrBuilder {
        public static Parser<Package> PARSER = new AbstractParser<Package>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Package.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public Package parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new Package(codedInputStream, extensionRegistryLite);
            }
        };
        private static final Package defaultInstance;
        private int bitField0_;
        private List<Function> function_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<Property> property_;
        private List<TypeAlias> typeAlias_;
        private TypeTable typeTable_;
        private final ByteString unknownFields;
        private VersionRequirementTable versionRequirementTable_;

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<Package, Builder> implements PackageOrBuilder {
            private int bitField0_;
            private List<Function> function_ = Collections.emptyList();
            private List<Property> property_ = Collections.emptyList();
            private List<TypeAlias> typeAlias_ = Collections.emptyList();
            private TypeTable typeTable_ = TypeTable.getDefaultInstance();
            private VersionRequirementTable versionRequirementTable_ = VersionRequirementTable.getDefaultInstance();

            private Builder() {
                maybeForceBuilderInitialization();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            private void ensureFunctionIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.function_ = new ArrayList(this.function_);
                    this.bitField0_ |= 1;
                }
            }

            private void ensurePropertyIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.property_ = new ArrayList(this.property_);
                    this.bitField0_ |= 2;
                }
            }

            private void ensureTypeAliasIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.typeAlias_ = new ArrayList(this.typeAlias_);
                    this.bitField0_ |= 4;
                }
            }

            private void maybeForceBuilderInitialization() {
            }

            public Package buildPartial() {
                Package r02 = new Package(this);
                int i2 = this.bitField0_;
                if ((i2 & 1) == 1) {
                    this.function_ = Collections.unmodifiableList(this.function_);
                    this.bitField0_ &= -2;
                }
                r02.function_ = this.function_;
                if ((this.bitField0_ & 2) == 2) {
                    this.property_ = Collections.unmodifiableList(this.property_);
                    this.bitField0_ &= -3;
                }
                r02.property_ = this.property_;
                if ((this.bitField0_ & 4) == 4) {
                    this.typeAlias_ = Collections.unmodifiableList(this.typeAlias_);
                    this.bitField0_ &= -5;
                }
                r02.typeAlias_ = this.typeAlias_;
                int i3 = (i2 & 8) != 8 ? 0 : 1;
                r02.typeTable_ = this.typeTable_;
                if ((i2 & 16) == 16) {
                    i3 |= 2;
                }
                r02.versionRequirementTable_ = this.versionRequirementTable_;
                r02.bitField0_ = i3;
                return r02;
            }

            public Function getFunction(int i2) {
                return this.function_.get(i2);
            }

            public int getFunctionCount() {
                return this.function_.size();
            }

            public Property getProperty(int i2) {
                return this.property_.get(i2);
            }

            public int getPropertyCount() {
                return this.property_.size();
            }

            public TypeAlias getTypeAlias(int i2) {
                return this.typeAlias_.get(i2);
            }

            public int getTypeAliasCount() {
                return this.typeAlias_.size();
            }

            public TypeTable getTypeTable() {
                return this.typeTable_;
            }

            public boolean hasTypeTable() {
                return (this.bitField0_ & 8) == 8;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i2 = 0; i2 < getFunctionCount(); i2++) {
                    if (!getFunction(i2).isInitialized()) {
                        return false;
                    }
                }
                for (int i3 = 0; i3 < getPropertyCount(); i3++) {
                    if (!getProperty(i3).isInitialized()) {
                        return false;
                    }
                }
                for (int i4 = 0; i4 < getTypeAliasCount(); i4++) {
                    if (!getTypeAlias(i4).isInitialized()) {
                        return false;
                    }
                }
                return (!hasTypeTable() || getTypeTable().isInitialized()) && extensionsAreInitialized();
            }

            public Builder mergeTypeTable(TypeTable typeTable) {
                if ((this.bitField0_ & 8) != 8 || this.typeTable_ == TypeTable.getDefaultInstance()) {
                    this.typeTable_ = typeTable;
                } else {
                    this.typeTable_ = TypeTable.newBuilder(this.typeTable_).mergeFrom(typeTable).buildPartial();
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder mergeVersionRequirementTable(VersionRequirementTable versionRequirementTable) {
                if ((this.bitField0_ & 16) != 16 || this.versionRequirementTable_ == VersionRequirementTable.getDefaultInstance()) {
                    this.versionRequirementTable_ = versionRequirementTable;
                } else {
                    this.versionRequirementTable_ = VersionRequirementTable.newBuilder(this.versionRequirementTable_).mergeFrom(versionRequirementTable).buildPartial();
                }
                this.bitField0_ |= 16;
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public Package build() {
                Package packageBuildPartial = buildPartial();
                if (packageBuildPartial.isInitialized()) {
                    return packageBuildPartial;
                }
                throw AbstractMessageLite.Builder.newUninitializedMessageException(packageBuildPartial);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public Package getDefaultInstanceForType() {
                return Package.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(Package r3) {
                if (r3 == Package.getDefaultInstance()) {
                    return this;
                }
                if (!r3.function_.isEmpty()) {
                    if (this.function_.isEmpty()) {
                        this.function_ = r3.function_;
                        this.bitField0_ &= -2;
                    } else {
                        ensureFunctionIsMutable();
                        this.function_.addAll(r3.function_);
                    }
                }
                if (!r3.property_.isEmpty()) {
                    if (this.property_.isEmpty()) {
                        this.property_ = r3.property_;
                        this.bitField0_ &= -3;
                    } else {
                        ensurePropertyIsMutable();
                        this.property_.addAll(r3.property_);
                    }
                }
                if (!r3.typeAlias_.isEmpty()) {
                    if (this.typeAlias_.isEmpty()) {
                        this.typeAlias_ = r3.typeAlias_;
                        this.bitField0_ &= -5;
                    } else {
                        ensureTypeAliasIsMutable();
                        this.typeAlias_.addAll(r3.typeAlias_);
                    }
                }
                if (r3.hasTypeTable()) {
                    mergeTypeTable(r3.getTypeTable());
                }
                if (r3.hasVersionRequirementTable()) {
                    mergeVersionRequirementTable(r3.getVersionRequirementTable());
                }
                mergeExtensionFields(r3);
                setUnknownFields(getUnknownFields().concat(r3.unknownFields));
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.ExtendableBuilder, kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo2084clone() {
                return create().mergeFrom(buildPartial());
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Package.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Package> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Package.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Package r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Package) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    if (r3 == 0) goto Le
                    r2.mergeFrom(r3)
                Le:
                    return r2
                Lf:
                    r3 = move-exception
                    goto L1b
                L11:
                    r3 = move-exception
                    kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Package r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Package) r4     // Catch: java.lang.Throwable -> Lf
                    throw r3     // Catch: java.lang.Throwable -> L19
                L19:
                    r3 = move-exception
                    r0 = r4
                L1b:
                    if (r0 == 0) goto L20
                    r2.mergeFrom(r0)
                L20:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Package.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Package$Builder");
            }
        }

        static {
            Package r02 = new Package(true);
            defaultInstance = r02;
            r02.initFields();
        }

        public static Package getDefaultInstance() {
            return defaultInstance;
        }

        private void initFields() {
            this.function_ = Collections.emptyList();
            this.property_ = Collections.emptyList();
            this.typeAlias_ = Collections.emptyList();
            this.typeTable_ = TypeTable.getDefaultInstance();
            this.versionRequirementTable_ = VersionRequirementTable.getDefaultInstance();
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public static Package parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public Function getFunction(int i2) {
            return this.function_.get(i2);
        }

        public int getFunctionCount() {
            return this.function_.size();
        }

        public List<Function> getFunctionList() {
            return this.function_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<Package> getParserForType() {
            return PARSER;
        }

        public Property getProperty(int i2) {
            return this.property_.get(i2);
        }

        public int getPropertyCount() {
            return this.property_.size();
        }

        public List<Property> getPropertyList() {
            return this.property_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSerializedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeMessageSize = 0;
            for (int i3 = 0; i3 < this.function_.size(); i3++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(3, this.function_.get(i3));
            }
            for (int i4 = 0; i4 < this.property_.size(); i4++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(4, this.property_.get(i4));
            }
            for (int i5 = 0; i5 < this.typeAlias_.size(); i5++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(5, this.typeAlias_.get(i5));
            }
            if ((this.bitField0_ & 1) == 1) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(30, this.typeTable_);
            }
            if ((this.bitField0_ & 2) == 2) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(32, this.versionRequirementTable_);
            }
            int iExtensionsSerializedSize = iComputeMessageSize + extensionsSerializedSize() + this.unknownFields.size();
            this.memoizedSerializedSize = iExtensionsSerializedSize;
            return iExtensionsSerializedSize;
        }

        public TypeAlias getTypeAlias(int i2) {
            return this.typeAlias_.get(i2);
        }

        public int getTypeAliasCount() {
            return this.typeAlias_.size();
        }

        public List<TypeAlias> getTypeAliasList() {
            return this.typeAlias_;
        }

        public TypeTable getTypeTable() {
            return this.typeTable_;
        }

        public VersionRequirementTable getVersionRequirementTable() {
            return this.versionRequirementTable_;
        }

        public boolean hasTypeTable() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean hasVersionRequirementTable() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b3 = this.memoizedIsInitialized;
            if (b3 == 1) {
                return true;
            }
            if (b3 == 0) {
                return false;
            }
            for (int i2 = 0; i2 < getFunctionCount(); i2++) {
                if (!getFunction(i2).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i3 = 0; i3 < getPropertyCount(); i3++) {
                if (!getProperty(i3).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i4 = 0; i4 < getTypeAliasCount(); i4++) {
                if (!getTypeAlias(i4).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (hasTypeTable() && !getTypeTable().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            if (extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            GeneratedMessageLite.ExtendableMessage<MessageType>.ExtensionWriter extensionWriterNewExtensionWriter = newExtensionWriter();
            for (int i2 = 0; i2 < this.function_.size(); i2++) {
                codedOutputStream.writeMessage(3, this.function_.get(i2));
            }
            for (int i3 = 0; i3 < this.property_.size(); i3++) {
                codedOutputStream.writeMessage(4, this.property_.get(i3));
            }
            for (int i4 = 0; i4 < this.typeAlias_.size(); i4++) {
                codedOutputStream.writeMessage(5, this.typeAlias_.get(i4));
            }
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeMessage(30, this.typeTable_);
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeMessage(32, this.versionRequirementTable_);
            }
            extensionWriterNewExtensionWriter.writeUntil(200, codedOutputStream);
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        public static Builder newBuilder(Package r12) {
            return newBuilder().mergeFrom(r12);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public Package getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        private Package(GeneratedMessageLite.ExtendableBuilder<Package, ?> extendableBuilder) {
            super(extendableBuilder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = extendableBuilder.getUnknownFields();
        }

        private Package(boolean z2) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r4v4 */
        /* JADX WARN: Type inference failed for: r4v6 */
        /* JADX WARN: Type inference failed for: r4v8 */
        private Package(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z2 = false;
            char c3 = 0;
            while (!z2) {
                try {
                    try {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 26) {
                                    int i2 = (c3 == true ? 1 : 0) & 1;
                                    c3 = c3;
                                    if (i2 != 1) {
                                        this.function_ = new ArrayList();
                                        c3 = (c3 == true ? 1 : 0) | 1;
                                    }
                                    this.function_.add(codedInputStream.readMessage(Function.PARSER, extensionRegistryLite));
                                } else if (tag == 34) {
                                    int i3 = (c3 == true ? 1 : 0) & 2;
                                    c3 = c3;
                                    if (i3 != 2) {
                                        this.property_ = new ArrayList();
                                        c3 = (c3 == true ? 1 : 0) | 2;
                                    }
                                    this.property_.add(codedInputStream.readMessage(Property.PARSER, extensionRegistryLite));
                                } else if (tag != 42) {
                                    if (tag == 242) {
                                        TypeTable.Builder builder = (this.bitField0_ & 1) == 1 ? this.typeTable_.toBuilder() : null;
                                        TypeTable typeTable = (TypeTable) codedInputStream.readMessage(TypeTable.PARSER, extensionRegistryLite);
                                        this.typeTable_ = typeTable;
                                        if (builder != null) {
                                            builder.mergeFrom(typeTable);
                                            this.typeTable_ = builder.buildPartial();
                                        }
                                        this.bitField0_ |= 1;
                                    } else if (tag != 258) {
                                        if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                        }
                                    } else {
                                        VersionRequirementTable.Builder builder2 = (this.bitField0_ & 2) == 2 ? this.versionRequirementTable_.toBuilder() : null;
                                        VersionRequirementTable versionRequirementTable = (VersionRequirementTable) codedInputStream.readMessage(VersionRequirementTable.PARSER, extensionRegistryLite);
                                        this.versionRequirementTable_ = versionRequirementTable;
                                        if (builder2 != null) {
                                            builder2.mergeFrom(versionRequirementTable);
                                            this.versionRequirementTable_ = builder2.buildPartial();
                                        }
                                        this.bitField0_ |= 2;
                                    }
                                } else {
                                    int i4 = (c3 == true ? 1 : 0) & 4;
                                    c3 = c3;
                                    if (i4 != 4) {
                                        this.typeAlias_ = new ArrayList();
                                        c3 = (c3 == true ? 1 : 0) | 4;
                                    }
                                    this.typeAlias_.add(codedInputStream.readMessage(TypeAlias.PARSER, extensionRegistryLite));
                                }
                            }
                            z2 = true;
                        } catch (IOException e2) {
                            throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                        }
                    } catch (InvalidProtocolBufferException e3) {
                        throw e3.setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if (((c3 == true ? 1 : 0) & 1) == 1) {
                        this.function_ = Collections.unmodifiableList(this.function_);
                    }
                    if (((c3 == true ? 1 : 0) & 2) == 2) {
                        this.property_ = Collections.unmodifiableList(this.property_);
                    }
                    if (((c3 == true ? 1 : 0) & 4) == 4) {
                        this.typeAlias_ = Collections.unmodifiableList(this.typeAlias_);
                    }
                    try {
                        codedOutputStreamNewInstance.flush();
                    } catch (IOException unused) {
                    } catch (Throwable th2) {
                        this.unknownFields = outputNewOutput.toByteString();
                        throw th2;
                    }
                    this.unknownFields = outputNewOutput.toByteString();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if (((c3 == true ? 1 : 0) & 1) == 1) {
                this.function_ = Collections.unmodifiableList(this.function_);
            }
            if (((c3 == true ? 1 : 0) & 2) == 2) {
                this.property_ = Collections.unmodifiableList(this.property_);
            }
            if (((c3 == true ? 1 : 0) & 4) == 4) {
                this.typeAlias_ = Collections.unmodifiableList(this.typeAlias_);
            }
            try {
                codedOutputStreamNewInstance.flush();
            } catch (IOException unused2) {
            } catch (Throwable th3) {
                this.unknownFields = outputNewOutput.toByteString();
                throw th3;
            }
            this.unknownFields = outputNewOutput.toByteString();
            makeExtensionsImmutable();
        }
    }

    public static final class PackageFragment extends GeneratedMessageLite.ExtendableMessage<PackageFragment> implements PackageFragmentOrBuilder {
        public static Parser<PackageFragment> PARSER = new AbstractParser<PackageFragment>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.PackageFragment.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public PackageFragment parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new PackageFragment(codedInputStream, extensionRegistryLite);
            }
        };
        private static final PackageFragment defaultInstance;
        private int bitField0_;
        private List<Class> class__;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private Package package_;
        private QualifiedNameTable qualifiedNames_;
        private StringTable strings_;
        private final ByteString unknownFields;

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<PackageFragment, Builder> implements PackageFragmentOrBuilder {
            private int bitField0_;
            private StringTable strings_ = StringTable.getDefaultInstance();
            private QualifiedNameTable qualifiedNames_ = QualifiedNameTable.getDefaultInstance();
            private Package package_ = Package.getDefaultInstance();
            private List<Class> class__ = Collections.emptyList();

            private Builder() {
                maybeForceBuilderInitialization();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            private void ensureClass_IsMutable() {
                if ((this.bitField0_ & 8) != 8) {
                    this.class__ = new ArrayList(this.class__);
                    this.bitField0_ |= 8;
                }
            }

            private void maybeForceBuilderInitialization() {
            }

            public PackageFragment buildPartial() {
                PackageFragment packageFragment = new PackageFragment(this);
                int i2 = this.bitField0_;
                int i3 = (i2 & 1) != 1 ? 0 : 1;
                packageFragment.strings_ = this.strings_;
                if ((i2 & 2) == 2) {
                    i3 |= 2;
                }
                packageFragment.qualifiedNames_ = this.qualifiedNames_;
                if ((i2 & 4) == 4) {
                    i3 |= 4;
                }
                packageFragment.package_ = this.package_;
                if ((this.bitField0_ & 8) == 8) {
                    this.class__ = Collections.unmodifiableList(this.class__);
                    this.bitField0_ &= -9;
                }
                packageFragment.class__ = this.class__;
                packageFragment.bitField0_ = i3;
                return packageFragment;
            }

            public Class getClass_(int i2) {
                return this.class__.get(i2);
            }

            public int getClass_Count() {
                return this.class__.size();
            }

            public Package getPackage() {
                return this.package_;
            }

            public QualifiedNameTable getQualifiedNames() {
                return this.qualifiedNames_;
            }

            public boolean hasPackage() {
                return (this.bitField0_ & 4) == 4;
            }

            public boolean hasQualifiedNames() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                if (hasQualifiedNames() && !getQualifiedNames().isInitialized()) {
                    return false;
                }
                if (hasPackage() && !getPackage().isInitialized()) {
                    return false;
                }
                for (int i2 = 0; i2 < getClass_Count(); i2++) {
                    if (!getClass_(i2).isInitialized()) {
                        return false;
                    }
                }
                return extensionsAreInitialized();
            }

            public Builder mergePackage(Package r4) {
                if ((this.bitField0_ & 4) != 4 || this.package_ == Package.getDefaultInstance()) {
                    this.package_ = r4;
                } else {
                    this.package_ = Package.newBuilder(this.package_).mergeFrom(r4).buildPartial();
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder mergeQualifiedNames(QualifiedNameTable qualifiedNameTable) {
                if ((this.bitField0_ & 2) != 2 || this.qualifiedNames_ == QualifiedNameTable.getDefaultInstance()) {
                    this.qualifiedNames_ = qualifiedNameTable;
                } else {
                    this.qualifiedNames_ = QualifiedNameTable.newBuilder(this.qualifiedNames_).mergeFrom(qualifiedNameTable).buildPartial();
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder mergeStrings(StringTable stringTable) {
                if ((this.bitField0_ & 1) != 1 || this.strings_ == StringTable.getDefaultInstance()) {
                    this.strings_ = stringTable;
                } else {
                    this.strings_ = StringTable.newBuilder(this.strings_).mergeFrom(stringTable).buildPartial();
                }
                this.bitField0_ |= 1;
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public PackageFragment build() {
                PackageFragment packageFragmentBuildPartial = buildPartial();
                if (packageFragmentBuildPartial.isInitialized()) {
                    return packageFragmentBuildPartial;
                }
                throw AbstractMessageLite.Builder.newUninitializedMessageException(packageFragmentBuildPartial);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public PackageFragment getDefaultInstanceForType() {
                return PackageFragment.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(PackageFragment packageFragment) {
                if (packageFragment == PackageFragment.getDefaultInstance()) {
                    return this;
                }
                if (packageFragment.hasStrings()) {
                    mergeStrings(packageFragment.getStrings());
                }
                if (packageFragment.hasQualifiedNames()) {
                    mergeQualifiedNames(packageFragment.getQualifiedNames());
                }
                if (packageFragment.hasPackage()) {
                    mergePackage(packageFragment.getPackage());
                }
                if (!packageFragment.class__.isEmpty()) {
                    if (this.class__.isEmpty()) {
                        this.class__ = packageFragment.class__;
                        this.bitField0_ &= -9;
                    } else {
                        ensureClass_IsMutable();
                        this.class__.addAll(packageFragment.class__);
                    }
                }
                mergeExtensionFields(packageFragment);
                setUnknownFields(getUnknownFields().concat(packageFragment.unknownFields));
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.ExtendableBuilder, kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo2084clone() {
                return create().mergeFrom(buildPartial());
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.PackageFragment.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$PackageFragment> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.PackageFragment.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$PackageFragment r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.PackageFragment) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    if (r3 == 0) goto Le
                    r2.mergeFrom(r3)
                Le:
                    return r2
                Lf:
                    r3 = move-exception
                    goto L1b
                L11:
                    r3 = move-exception
                    kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$PackageFragment r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.PackageFragment) r4     // Catch: java.lang.Throwable -> Lf
                    throw r3     // Catch: java.lang.Throwable -> L19
                L19:
                    r3 = move-exception
                    r0 = r4
                L1b:
                    if (r0 == 0) goto L20
                    r2.mergeFrom(r0)
                L20:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.PackageFragment.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$PackageFragment$Builder");
            }
        }

        static {
            PackageFragment packageFragment = new PackageFragment(true);
            defaultInstance = packageFragment;
            packageFragment.initFields();
        }

        public static PackageFragment getDefaultInstance() {
            return defaultInstance;
        }

        private void initFields() {
            this.strings_ = StringTable.getDefaultInstance();
            this.qualifiedNames_ = QualifiedNameTable.getDefaultInstance();
            this.package_ = Package.getDefaultInstance();
            this.class__ = Collections.emptyList();
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public static PackageFragment parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public Class getClass_(int i2) {
            return this.class__.get(i2);
        }

        public int getClass_Count() {
            return this.class__.size();
        }

        public List<Class> getClass_List() {
            return this.class__;
        }

        public Package getPackage() {
            return this.package_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<PackageFragment> getParserForType() {
            return PARSER;
        }

        public QualifiedNameTable getQualifiedNames() {
            return this.qualifiedNames_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSerializedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeMessageSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeMessageSize(1, this.strings_) + 0 : 0;
            if ((this.bitField0_ & 2) == 2) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(2, this.qualifiedNames_);
            }
            if ((this.bitField0_ & 4) == 4) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(3, this.package_);
            }
            for (int i3 = 0; i3 < this.class__.size(); i3++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(4, this.class__.get(i3));
            }
            int iExtensionsSerializedSize = iComputeMessageSize + extensionsSerializedSize() + this.unknownFields.size();
            this.memoizedSerializedSize = iExtensionsSerializedSize;
            return iExtensionsSerializedSize;
        }

        public StringTable getStrings() {
            return this.strings_;
        }

        public boolean hasPackage() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean hasQualifiedNames() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean hasStrings() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b3 = this.memoizedIsInitialized;
            if (b3 == 1) {
                return true;
            }
            if (b3 == 0) {
                return false;
            }
            if (hasQualifiedNames() && !getQualifiedNames().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            if (hasPackage() && !getPackage().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            for (int i2 = 0; i2 < getClass_Count(); i2++) {
                if (!getClass_(i2).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            GeneratedMessageLite.ExtendableMessage<MessageType>.ExtensionWriter extensionWriterNewExtensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeMessage(1, this.strings_);
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeMessage(2, this.qualifiedNames_);
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeMessage(3, this.package_);
            }
            for (int i2 = 0; i2 < this.class__.size(); i2++) {
                codedOutputStream.writeMessage(4, this.class__.get(i2));
            }
            extensionWriterNewExtensionWriter.writeUntil(200, codedOutputStream);
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        public static Builder newBuilder(PackageFragment packageFragment) {
            return newBuilder().mergeFrom(packageFragment);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public PackageFragment getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        private PackageFragment(GeneratedMessageLite.ExtendableBuilder<PackageFragment, ?> extendableBuilder) {
            super(extendableBuilder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = extendableBuilder.getUnknownFields();
        }

        private PackageFragment(boolean z2) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r4v4 */
        private PackageFragment(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z2 = false;
            char c3 = 0;
            while (!z2) {
                try {
                    try {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 10) {
                                    StringTable.Builder builder = (this.bitField0_ & 1) == 1 ? this.strings_.toBuilder() : null;
                                    StringTable stringTable = (StringTable) codedInputStream.readMessage(StringTable.PARSER, extensionRegistryLite);
                                    this.strings_ = stringTable;
                                    if (builder != null) {
                                        builder.mergeFrom(stringTable);
                                        this.strings_ = builder.buildPartial();
                                    }
                                    this.bitField0_ |= 1;
                                } else if (tag == 18) {
                                    QualifiedNameTable.Builder builder2 = (this.bitField0_ & 2) == 2 ? this.qualifiedNames_.toBuilder() : null;
                                    QualifiedNameTable qualifiedNameTable = (QualifiedNameTable) codedInputStream.readMessage(QualifiedNameTable.PARSER, extensionRegistryLite);
                                    this.qualifiedNames_ = qualifiedNameTable;
                                    if (builder2 != null) {
                                        builder2.mergeFrom(qualifiedNameTable);
                                        this.qualifiedNames_ = builder2.buildPartial();
                                    }
                                    this.bitField0_ |= 2;
                                } else if (tag == 26) {
                                    Package.Builder builder3 = (this.bitField0_ & 4) == 4 ? this.package_.toBuilder() : null;
                                    Package r6 = (Package) codedInputStream.readMessage(Package.PARSER, extensionRegistryLite);
                                    this.package_ = r6;
                                    if (builder3 != null) {
                                        builder3.mergeFrom(r6);
                                        this.package_ = builder3.buildPartial();
                                    }
                                    this.bitField0_ |= 4;
                                } else if (tag != 34) {
                                    if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    int i2 = (c3 == true ? 1 : 0) & 8;
                                    c3 = c3;
                                    if (i2 != 8) {
                                        this.class__ = new ArrayList();
                                        c3 = (c3 == true ? 1 : 0) | '\b';
                                    }
                                    this.class__.add(codedInputStream.readMessage(Class.PARSER, extensionRegistryLite));
                                }
                            }
                            z2 = true;
                        } catch (InvalidProtocolBufferException e2) {
                            throw e2.setUnfinishedMessage(this);
                        }
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if (((c3 == true ? 1 : 0) & 8) == 8) {
                        this.class__ = Collections.unmodifiableList(this.class__);
                    }
                    try {
                        codedOutputStreamNewInstance.flush();
                    } catch (IOException unused) {
                    } catch (Throwable th2) {
                        this.unknownFields = outputNewOutput.toByteString();
                        throw th2;
                    }
                    this.unknownFields = outputNewOutput.toByteString();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if (((c3 == true ? 1 : 0) & 8) == 8) {
                this.class__ = Collections.unmodifiableList(this.class__);
            }
            try {
                codedOutputStreamNewInstance.flush();
            } catch (IOException unused2) {
            } catch (Throwable th3) {
                this.unknownFields = outputNewOutput.toByteString();
                throw th3;
            }
            this.unknownFields = outputNewOutput.toByteString();
            makeExtensionsImmutable();
        }
    }

    public interface PackageFragmentOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder {
    }

    public interface PackageOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder {
    }

    public static final class Property extends GeneratedMessageLite.ExtendableMessage<Property> implements PropertyOrBuilder {
        public static Parser<Property> PARSER = new AbstractParser<Property>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Property.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public Property parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new Property(codedInputStream, extensionRegistryLite);
            }
        };
        private static final Property defaultInstance;
        private int bitField0_;
        private int contextReceiverTypeIdMemoizedSerializedSize;
        private List<Integer> contextReceiverTypeId_;
        private List<Type> contextReceiverType_;
        private int flags_;
        private int getterFlags_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private int name_;
        private int oldFlags_;
        private int receiverTypeId_;
        private Type receiverType_;
        private int returnTypeId_;
        private Type returnType_;
        private int setterFlags_;
        private ValueParameter setterValueParameter_;
        private List<TypeParameter> typeParameter_;
        private final ByteString unknownFields;
        private List<Integer> versionRequirement_;

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<Property, Builder> implements PropertyOrBuilder {
            private int bitField0_;
            private int getterFlags_;
            private int name_;
            private int receiverTypeId_;
            private int returnTypeId_;
            private int setterFlags_;
            private int flags_ = R2.attr.bigEmojiconRows;
            private int oldFlags_ = R2.attr.inner_corner_color;
            private Type returnType_ = Type.getDefaultInstance();
            private List<TypeParameter> typeParameter_ = Collections.emptyList();
            private Type receiverType_ = Type.getDefaultInstance();
            private List<Type> contextReceiverType_ = Collections.emptyList();
            private List<Integer> contextReceiverTypeId_ = Collections.emptyList();
            private ValueParameter setterValueParameter_ = ValueParameter.getDefaultInstance();
            private List<Integer> versionRequirement_ = Collections.emptyList();

            private Builder() {
                maybeForceBuilderInitialization();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            private void ensureContextReceiverTypeIdIsMutable() {
                if ((this.bitField0_ & 512) != 512) {
                    this.contextReceiverTypeId_ = new ArrayList(this.contextReceiverTypeId_);
                    this.bitField0_ |= 512;
                }
            }

            private void ensureContextReceiverTypeIsMutable() {
                if ((this.bitField0_ & 256) != 256) {
                    this.contextReceiverType_ = new ArrayList(this.contextReceiverType_);
                    this.bitField0_ |= 256;
                }
            }

            private void ensureTypeParameterIsMutable() {
                if ((this.bitField0_ & 32) != 32) {
                    this.typeParameter_ = new ArrayList(this.typeParameter_);
                    this.bitField0_ |= 32;
                }
            }

            private void ensureVersionRequirementIsMutable() {
                if ((this.bitField0_ & 8192) != 8192) {
                    this.versionRequirement_ = new ArrayList(this.versionRequirement_);
                    this.bitField0_ |= 8192;
                }
            }

            private void maybeForceBuilderInitialization() {
            }

            public Property buildPartial() {
                Property property = new Property(this);
                int i2 = this.bitField0_;
                int i3 = (i2 & 1) != 1 ? 0 : 1;
                property.flags_ = this.flags_;
                if ((i2 & 2) == 2) {
                    i3 |= 2;
                }
                property.oldFlags_ = this.oldFlags_;
                if ((i2 & 4) == 4) {
                    i3 |= 4;
                }
                property.name_ = this.name_;
                if ((i2 & 8) == 8) {
                    i3 |= 8;
                }
                property.returnType_ = this.returnType_;
                if ((i2 & 16) == 16) {
                    i3 |= 16;
                }
                property.returnTypeId_ = this.returnTypeId_;
                if ((this.bitField0_ & 32) == 32) {
                    this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                    this.bitField0_ &= -33;
                }
                property.typeParameter_ = this.typeParameter_;
                if ((i2 & 64) == 64) {
                    i3 |= 32;
                }
                property.receiverType_ = this.receiverType_;
                if ((i2 & 128) == 128) {
                    i3 |= 64;
                }
                property.receiverTypeId_ = this.receiverTypeId_;
                if ((this.bitField0_ & 256) == 256) {
                    this.contextReceiverType_ = Collections.unmodifiableList(this.contextReceiverType_);
                    this.bitField0_ &= -257;
                }
                property.contextReceiverType_ = this.contextReceiverType_;
                if ((this.bitField0_ & 512) == 512) {
                    this.contextReceiverTypeId_ = Collections.unmodifiableList(this.contextReceiverTypeId_);
                    this.bitField0_ &= -513;
                }
                property.contextReceiverTypeId_ = this.contextReceiverTypeId_;
                if ((i2 & 1024) == 1024) {
                    i3 |= 128;
                }
                property.setterValueParameter_ = this.setterValueParameter_;
                if ((i2 & 2048) == 2048) {
                    i3 |= 256;
                }
                property.getterFlags_ = this.getterFlags_;
                if ((i2 & 4096) == 4096) {
                    i3 |= 512;
                }
                property.setterFlags_ = this.setterFlags_;
                if ((this.bitField0_ & 8192) == 8192) {
                    this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                    this.bitField0_ &= -8193;
                }
                property.versionRequirement_ = this.versionRequirement_;
                property.bitField0_ = i3;
                return property;
            }

            public Type getContextReceiverType(int i2) {
                return this.contextReceiverType_.get(i2);
            }

            public int getContextReceiverTypeCount() {
                return this.contextReceiverType_.size();
            }

            public Type getReceiverType() {
                return this.receiverType_;
            }

            public Type getReturnType() {
                return this.returnType_;
            }

            public ValueParameter getSetterValueParameter() {
                return this.setterValueParameter_;
            }

            public TypeParameter getTypeParameter(int i2) {
                return this.typeParameter_.get(i2);
            }

            public int getTypeParameterCount() {
                return this.typeParameter_.size();
            }

            public boolean hasName() {
                return (this.bitField0_ & 4) == 4;
            }

            public boolean hasReceiverType() {
                return (this.bitField0_ & 64) == 64;
            }

            public boolean hasReturnType() {
                return (this.bitField0_ & 8) == 8;
            }

            public boolean hasSetterValueParameter() {
                return (this.bitField0_ & 1024) == 1024;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                if (!hasName()) {
                    return false;
                }
                if (hasReturnType() && !getReturnType().isInitialized()) {
                    return false;
                }
                for (int i2 = 0; i2 < getTypeParameterCount(); i2++) {
                    if (!getTypeParameter(i2).isInitialized()) {
                        return false;
                    }
                }
                if (hasReceiverType() && !getReceiverType().isInitialized()) {
                    return false;
                }
                for (int i3 = 0; i3 < getContextReceiverTypeCount(); i3++) {
                    if (!getContextReceiverType(i3).isInitialized()) {
                        return false;
                    }
                }
                return (!hasSetterValueParameter() || getSetterValueParameter().isInitialized()) && extensionsAreInitialized();
            }

            public Builder mergeReceiverType(Type type) {
                if ((this.bitField0_ & 64) != 64 || this.receiverType_ == Type.getDefaultInstance()) {
                    this.receiverType_ = type;
                } else {
                    this.receiverType_ = Type.newBuilder(this.receiverType_).mergeFrom(type).buildPartial();
                }
                this.bitField0_ |= 64;
                return this;
            }

            public Builder mergeReturnType(Type type) {
                if ((this.bitField0_ & 8) != 8 || this.returnType_ == Type.getDefaultInstance()) {
                    this.returnType_ = type;
                } else {
                    this.returnType_ = Type.newBuilder(this.returnType_).mergeFrom(type).buildPartial();
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder mergeSetterValueParameter(ValueParameter valueParameter) {
                if ((this.bitField0_ & 1024) != 1024 || this.setterValueParameter_ == ValueParameter.getDefaultInstance()) {
                    this.setterValueParameter_ = valueParameter;
                } else {
                    this.setterValueParameter_ = ValueParameter.newBuilder(this.setterValueParameter_).mergeFrom(valueParameter).buildPartial();
                }
                this.bitField0_ |= 1024;
                return this;
            }

            public Builder setFlags(int i2) {
                this.bitField0_ |= 1;
                this.flags_ = i2;
                return this;
            }

            public Builder setGetterFlags(int i2) {
                this.bitField0_ |= 2048;
                this.getterFlags_ = i2;
                return this;
            }

            public Builder setName(int i2) {
                this.bitField0_ |= 4;
                this.name_ = i2;
                return this;
            }

            public Builder setOldFlags(int i2) {
                this.bitField0_ |= 2;
                this.oldFlags_ = i2;
                return this;
            }

            public Builder setReceiverTypeId(int i2) {
                this.bitField0_ |= 128;
                this.receiverTypeId_ = i2;
                return this;
            }

            public Builder setReturnTypeId(int i2) {
                this.bitField0_ |= 16;
                this.returnTypeId_ = i2;
                return this;
            }

            public Builder setSetterFlags(int i2) {
                this.bitField0_ |= 4096;
                this.setterFlags_ = i2;
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public Property build() {
                Property propertyBuildPartial = buildPartial();
                if (propertyBuildPartial.isInitialized()) {
                    return propertyBuildPartial;
                }
                throw AbstractMessageLite.Builder.newUninitializedMessageException(propertyBuildPartial);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public Property getDefaultInstanceForType() {
                return Property.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(Property property) {
                if (property == Property.getDefaultInstance()) {
                    return this;
                }
                if (property.hasFlags()) {
                    setFlags(property.getFlags());
                }
                if (property.hasOldFlags()) {
                    setOldFlags(property.getOldFlags());
                }
                if (property.hasName()) {
                    setName(property.getName());
                }
                if (property.hasReturnType()) {
                    mergeReturnType(property.getReturnType());
                }
                if (property.hasReturnTypeId()) {
                    setReturnTypeId(property.getReturnTypeId());
                }
                if (!property.typeParameter_.isEmpty()) {
                    if (this.typeParameter_.isEmpty()) {
                        this.typeParameter_ = property.typeParameter_;
                        this.bitField0_ &= -33;
                    } else {
                        ensureTypeParameterIsMutable();
                        this.typeParameter_.addAll(property.typeParameter_);
                    }
                }
                if (property.hasReceiverType()) {
                    mergeReceiverType(property.getReceiverType());
                }
                if (property.hasReceiverTypeId()) {
                    setReceiverTypeId(property.getReceiverTypeId());
                }
                if (!property.contextReceiverType_.isEmpty()) {
                    if (this.contextReceiverType_.isEmpty()) {
                        this.contextReceiverType_ = property.contextReceiverType_;
                        this.bitField0_ &= -257;
                    } else {
                        ensureContextReceiverTypeIsMutable();
                        this.contextReceiverType_.addAll(property.contextReceiverType_);
                    }
                }
                if (!property.contextReceiverTypeId_.isEmpty()) {
                    if (this.contextReceiverTypeId_.isEmpty()) {
                        this.contextReceiverTypeId_ = property.contextReceiverTypeId_;
                        this.bitField0_ &= -513;
                    } else {
                        ensureContextReceiverTypeIdIsMutable();
                        this.contextReceiverTypeId_.addAll(property.contextReceiverTypeId_);
                    }
                }
                if (property.hasSetterValueParameter()) {
                    mergeSetterValueParameter(property.getSetterValueParameter());
                }
                if (property.hasGetterFlags()) {
                    setGetterFlags(property.getGetterFlags());
                }
                if (property.hasSetterFlags()) {
                    setSetterFlags(property.getSetterFlags());
                }
                if (!property.versionRequirement_.isEmpty()) {
                    if (this.versionRequirement_.isEmpty()) {
                        this.versionRequirement_ = property.versionRequirement_;
                        this.bitField0_ &= -8193;
                    } else {
                        ensureVersionRequirementIsMutable();
                        this.versionRequirement_.addAll(property.versionRequirement_);
                    }
                }
                mergeExtensionFields(property);
                setUnknownFields(getUnknownFields().concat(property.unknownFields));
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.ExtendableBuilder, kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo2084clone() {
                return create().mergeFrom(buildPartial());
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Property.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Property> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Property.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Property r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Property) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    if (r3 == 0) goto Le
                    r2.mergeFrom(r3)
                Le:
                    return r2
                Lf:
                    r3 = move-exception
                    goto L1b
                L11:
                    r3 = move-exception
                    kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Property r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Property) r4     // Catch: java.lang.Throwable -> Lf
                    throw r3     // Catch: java.lang.Throwable -> L19
                L19:
                    r3 = move-exception
                    r0 = r4
                L1b:
                    if (r0 == 0) goto L20
                    r2.mergeFrom(r0)
                L20:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Property.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Property$Builder");
            }
        }

        static {
            Property property = new Property(true);
            defaultInstance = property;
            property.initFields();
        }

        public static Property getDefaultInstance() {
            return defaultInstance;
        }

        private void initFields() {
            this.flags_ = R2.attr.bigEmojiconRows;
            this.oldFlags_ = R2.attr.inner_corner_color;
            this.name_ = 0;
            this.returnType_ = Type.getDefaultInstance();
            this.returnTypeId_ = 0;
            this.typeParameter_ = Collections.emptyList();
            this.receiverType_ = Type.getDefaultInstance();
            this.receiverTypeId_ = 0;
            this.contextReceiverType_ = Collections.emptyList();
            this.contextReceiverTypeId_ = Collections.emptyList();
            this.setterValueParameter_ = ValueParameter.getDefaultInstance();
            this.getterFlags_ = 0;
            this.setterFlags_ = 0;
            this.versionRequirement_ = Collections.emptyList();
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Type getContextReceiverType(int i2) {
            return this.contextReceiverType_.get(i2);
        }

        public int getContextReceiverTypeCount() {
            return this.contextReceiverType_.size();
        }

        public List<Integer> getContextReceiverTypeIdList() {
            return this.contextReceiverTypeId_;
        }

        public List<Type> getContextReceiverTypeList() {
            return this.contextReceiverType_;
        }

        public int getFlags() {
            return this.flags_;
        }

        public int getGetterFlags() {
            return this.getterFlags_;
        }

        public int getName() {
            return this.name_;
        }

        public int getOldFlags() {
            return this.oldFlags_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<Property> getParserForType() {
            return PARSER;
        }

        public Type getReceiverType() {
            return this.receiverType_;
        }

        public int getReceiverTypeId() {
            return this.receiverTypeId_;
        }

        public Type getReturnType() {
            return this.returnType_;
        }

        public int getReturnTypeId() {
            return this.returnTypeId_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSerializedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeInt32Size = (this.bitField0_ & 2) == 2 ? CodedOutputStream.computeInt32Size(1, this.oldFlags_) + 0 : 0;
            if ((this.bitField0_ & 4) == 4) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(2, this.name_);
            }
            if ((this.bitField0_ & 8) == 8) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(3, this.returnType_);
            }
            for (int i3 = 0; i3 < this.typeParameter_.size(); i3++) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(4, this.typeParameter_.get(i3));
            }
            if ((this.bitField0_ & 32) == 32) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(5, this.receiverType_);
            }
            if ((this.bitField0_ & 128) == 128) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(6, this.setterValueParameter_);
            }
            if ((this.bitField0_ & 256) == 256) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(7, this.getterFlags_);
            }
            if ((this.bitField0_ & 512) == 512) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(8, this.setterFlags_);
            }
            if ((this.bitField0_ & 16) == 16) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(9, this.returnTypeId_);
            }
            if ((this.bitField0_ & 64) == 64) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(10, this.receiverTypeId_);
            }
            if ((this.bitField0_ & 1) == 1) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(11, this.flags_);
            }
            for (int i4 = 0; i4 < this.contextReceiverType_.size(); i4++) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(12, this.contextReceiverType_.get(i4));
            }
            int iComputeInt32SizeNoTag = 0;
            for (int i5 = 0; i5 < this.contextReceiverTypeId_.size(); i5++) {
                iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(this.contextReceiverTypeId_.get(i5).intValue());
            }
            int iComputeInt32SizeNoTag2 = iComputeInt32Size + iComputeInt32SizeNoTag;
            if (!getContextReceiverTypeIdList().isEmpty()) {
                iComputeInt32SizeNoTag2 = iComputeInt32SizeNoTag2 + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag);
            }
            this.contextReceiverTypeIdMemoizedSerializedSize = iComputeInt32SizeNoTag;
            int iComputeInt32SizeNoTag3 = 0;
            for (int i6 = 0; i6 < this.versionRequirement_.size(); i6++) {
                iComputeInt32SizeNoTag3 += CodedOutputStream.computeInt32SizeNoTag(this.versionRequirement_.get(i6).intValue());
            }
            int size = iComputeInt32SizeNoTag2 + iComputeInt32SizeNoTag3 + (getVersionRequirementList().size() * 2) + extensionsSerializedSize() + this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
        }

        public int getSetterFlags() {
            return this.setterFlags_;
        }

        public ValueParameter getSetterValueParameter() {
            return this.setterValueParameter_;
        }

        public TypeParameter getTypeParameter(int i2) {
            return this.typeParameter_.get(i2);
        }

        public int getTypeParameterCount() {
            return this.typeParameter_.size();
        }

        public List<TypeParameter> getTypeParameterList() {
            return this.typeParameter_;
        }

        public List<Integer> getVersionRequirementList() {
            return this.versionRequirement_;
        }

        public boolean hasFlags() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean hasGetterFlags() {
            return (this.bitField0_ & 256) == 256;
        }

        public boolean hasName() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean hasOldFlags() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean hasReceiverType() {
            return (this.bitField0_ & 32) == 32;
        }

        public boolean hasReceiverTypeId() {
            return (this.bitField0_ & 64) == 64;
        }

        public boolean hasReturnType() {
            return (this.bitField0_ & 8) == 8;
        }

        public boolean hasReturnTypeId() {
            return (this.bitField0_ & 16) == 16;
        }

        public boolean hasSetterFlags() {
            return (this.bitField0_ & 512) == 512;
        }

        public boolean hasSetterValueParameter() {
            return (this.bitField0_ & 128) == 128;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b3 = this.memoizedIsInitialized;
            if (b3 == 1) {
                return true;
            }
            if (b3 == 0) {
                return false;
            }
            if (!hasName()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            if (hasReturnType() && !getReturnType().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            for (int i2 = 0; i2 < getTypeParameterCount(); i2++) {
                if (!getTypeParameter(i2).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (hasReceiverType() && !getReceiverType().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            for (int i3 = 0; i3 < getContextReceiverTypeCount(); i3++) {
                if (!getContextReceiverType(i3).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (hasSetterValueParameter() && !getSetterValueParameter().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            if (extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            GeneratedMessageLite.ExtendableMessage<MessageType>.ExtensionWriter extensionWriterNewExtensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeInt32(1, this.oldFlags_);
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeInt32(2, this.name_);
            }
            if ((this.bitField0_ & 8) == 8) {
                codedOutputStream.writeMessage(3, this.returnType_);
            }
            for (int i2 = 0; i2 < this.typeParameter_.size(); i2++) {
                codedOutputStream.writeMessage(4, this.typeParameter_.get(i2));
            }
            if ((this.bitField0_ & 32) == 32) {
                codedOutputStream.writeMessage(5, this.receiverType_);
            }
            if ((this.bitField0_ & 128) == 128) {
                codedOutputStream.writeMessage(6, this.setterValueParameter_);
            }
            if ((this.bitField0_ & 256) == 256) {
                codedOutputStream.writeInt32(7, this.getterFlags_);
            }
            if ((this.bitField0_ & 512) == 512) {
                codedOutputStream.writeInt32(8, this.setterFlags_);
            }
            if ((this.bitField0_ & 16) == 16) {
                codedOutputStream.writeInt32(9, this.returnTypeId_);
            }
            if ((this.bitField0_ & 64) == 64) {
                codedOutputStream.writeInt32(10, this.receiverTypeId_);
            }
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeInt32(11, this.flags_);
            }
            for (int i3 = 0; i3 < this.contextReceiverType_.size(); i3++) {
                codedOutputStream.writeMessage(12, this.contextReceiverType_.get(i3));
            }
            if (getContextReceiverTypeIdList().size() > 0) {
                codedOutputStream.writeRawVarint32(106);
                codedOutputStream.writeRawVarint32(this.contextReceiverTypeIdMemoizedSerializedSize);
            }
            for (int i4 = 0; i4 < this.contextReceiverTypeId_.size(); i4++) {
                codedOutputStream.writeInt32NoTag(this.contextReceiverTypeId_.get(i4).intValue());
            }
            for (int i5 = 0; i5 < this.versionRequirement_.size(); i5++) {
                codedOutputStream.writeInt32(31, this.versionRequirement_.get(i5).intValue());
            }
            extensionWriterNewExtensionWriter.writeUntil(R2.id.tvDetail, codedOutputStream);
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        public static Builder newBuilder(Property property) {
            return newBuilder().mergeFrom(property);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public Property getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        private Property(GeneratedMessageLite.ExtendableBuilder<Property, ?> extendableBuilder) {
            super(extendableBuilder);
            this.contextReceiverTypeIdMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = extendableBuilder.getUnknownFields();
        }

        private Property(boolean z2) {
            this.contextReceiverTypeIdMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r4v11 */
        /* JADX WARN: Type inference failed for: r4v14 */
        /* JADX WARN: Type inference failed for: r4v3 */
        /* JADX WARN: Type inference failed for: r4v5 */
        /* JADX WARN: Type inference failed for: r4v7 */
        /* JADX WARN: Type inference failed for: r4v9 */
        /* JADX WARN: Type inference failed for: r5v0 */
        /* JADX WARN: Type inference failed for: r5v1 */
        /* JADX WARN: Type inference failed for: r5v2, types: [boolean] */
        private Property(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.contextReceiverTypeIdMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z2 = false;
            char c3 = 0;
            while (true) {
                ?? unknownField = 256;
                if (!z2) {
                    try {
                        try {
                            try {
                                int tag = codedInputStream.readTag();
                                switch (tag) {
                                    case 0:
                                        z2 = true;
                                    case 8:
                                        this.bitField0_ |= 2;
                                        this.oldFlags_ = codedInputStream.readInt32();
                                    case 16:
                                        this.bitField0_ |= 4;
                                        this.name_ = codedInputStream.readInt32();
                                    case 26:
                                        Type.Builder builder = (this.bitField0_ & 8) == 8 ? this.returnType_.toBuilder() : null;
                                        Type type = (Type) codedInputStream.readMessage(Type.PARSER, extensionRegistryLite);
                                        this.returnType_ = type;
                                        if (builder != null) {
                                            builder.mergeFrom(type);
                                            this.returnType_ = builder.buildPartial();
                                        }
                                        this.bitField0_ |= 8;
                                    case 34:
                                        int i2 = (c3 == true ? 1 : 0) & 32;
                                        c3 = c3;
                                        if (i2 != 32) {
                                            this.typeParameter_ = new ArrayList();
                                            c3 = (c3 == true ? 1 : 0) | ' ';
                                        }
                                        this.typeParameter_.add(codedInputStream.readMessage(TypeParameter.PARSER, extensionRegistryLite));
                                    case 42:
                                        Type.Builder builder2 = (this.bitField0_ & 32) == 32 ? this.receiverType_.toBuilder() : null;
                                        Type type2 = (Type) codedInputStream.readMessage(Type.PARSER, extensionRegistryLite);
                                        this.receiverType_ = type2;
                                        if (builder2 != null) {
                                            builder2.mergeFrom(type2);
                                            this.receiverType_ = builder2.buildPartial();
                                        }
                                        this.bitField0_ |= 32;
                                    case 50:
                                        ValueParameter.Builder builder3 = (this.bitField0_ & 128) == 128 ? this.setterValueParameter_.toBuilder() : null;
                                        ValueParameter valueParameter = (ValueParameter) codedInputStream.readMessage(ValueParameter.PARSER, extensionRegistryLite);
                                        this.setterValueParameter_ = valueParameter;
                                        if (builder3 != null) {
                                            builder3.mergeFrom(valueParameter);
                                            this.setterValueParameter_ = builder3.buildPartial();
                                        }
                                        this.bitField0_ |= 128;
                                    case 56:
                                        this.bitField0_ |= 256;
                                        this.getterFlags_ = codedInputStream.readInt32();
                                    case 64:
                                        this.bitField0_ |= 512;
                                        this.setterFlags_ = codedInputStream.readInt32();
                                    case 72:
                                        this.bitField0_ |= 16;
                                        this.returnTypeId_ = codedInputStream.readInt32();
                                    case 80:
                                        this.bitField0_ |= 64;
                                        this.receiverTypeId_ = codedInputStream.readInt32();
                                    case 88:
                                        this.bitField0_ |= 1;
                                        this.flags_ = codedInputStream.readInt32();
                                    case 98:
                                        int i3 = (c3 == true ? 1 : 0) & 256;
                                        c3 = c3;
                                        if (i3 != 256) {
                                            this.contextReceiverType_ = new ArrayList();
                                            c3 = (c3 == true ? 1 : 0) | 256;
                                        }
                                        this.contextReceiverType_.add(codedInputStream.readMessage(Type.PARSER, extensionRegistryLite));
                                    case 104:
                                        int i4 = (c3 == true ? 1 : 0) & 512;
                                        c3 = c3;
                                        if (i4 != 512) {
                                            this.contextReceiverTypeId_ = new ArrayList();
                                            c3 = (c3 == true ? 1 : 0) | 512;
                                        }
                                        this.contextReceiverTypeId_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    case 106:
                                        int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                        int i5 = (c3 == true ? 1 : 0) & 512;
                                        c3 = c3;
                                        if (i5 != 512) {
                                            c3 = c3;
                                            if (codedInputStream.getBytesUntilLimit() > 0) {
                                                this.contextReceiverTypeId_ = new ArrayList();
                                                c3 = (c3 == true ? 1 : 0) | 512;
                                            }
                                        }
                                        while (codedInputStream.getBytesUntilLimit() > 0) {
                                            this.contextReceiverTypeId_.add(Integer.valueOf(codedInputStream.readInt32()));
                                        }
                                        codedInputStream.popLimit(iPushLimit);
                                    case R2.attr.actionModeFindDrawable /* 248 */:
                                        int i6 = (c3 == true ? 1 : 0) & 8192;
                                        c3 = c3;
                                        if (i6 != 8192) {
                                            this.versionRequirement_ = new ArrayList();
                                            c3 = (c3 == true ? 1 : 0) | 8192;
                                        }
                                        this.versionRequirement_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    case 250:
                                        int iPushLimit2 = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                        int i7 = (c3 == true ? 1 : 0) & 8192;
                                        c3 = c3;
                                        if (i7 != 8192) {
                                            c3 = c3;
                                            if (codedInputStream.getBytesUntilLimit() > 0) {
                                                this.versionRequirement_ = new ArrayList();
                                                c3 = (c3 == true ? 1 : 0) | 8192;
                                            }
                                        }
                                        while (codedInputStream.getBytesUntilLimit() > 0) {
                                            this.versionRequirement_.add(Integer.valueOf(codedInputStream.readInt32()));
                                        }
                                        codedInputStream.popLimit(iPushLimit2);
                                    default:
                                        unknownField = parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag);
                                        if (unknownField == 0) {
                                            z2 = true;
                                        }
                                }
                            } catch (IOException e2) {
                                throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                            }
                        } catch (InvalidProtocolBufferException e3) {
                            throw e3.setUnfinishedMessage(this);
                        }
                    } catch (Throwable th) {
                        if (((c3 == true ? 1 : 0) & 32) == 32) {
                            this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                        }
                        if (((c3 == true ? 1 : 0) & 256) == unknownField) {
                            this.contextReceiverType_ = Collections.unmodifiableList(this.contextReceiverType_);
                        }
                        if (((c3 == true ? 1 : 0) & 512) == 512) {
                            this.contextReceiverTypeId_ = Collections.unmodifiableList(this.contextReceiverTypeId_);
                        }
                        if (((c3 == true ? 1 : 0) & 8192) == 8192) {
                            this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                        }
                        try {
                            codedOutputStreamNewInstance.flush();
                        } catch (IOException unused) {
                        } catch (Throwable th2) {
                            this.unknownFields = outputNewOutput.toByteString();
                            throw th2;
                        }
                        this.unknownFields = outputNewOutput.toByteString();
                        makeExtensionsImmutable();
                        throw th;
                    }
                } else {
                    if (((c3 == true ? 1 : 0) & 32) == 32) {
                        this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                    }
                    if (((c3 == true ? 1 : 0) & 256) == 256) {
                        this.contextReceiverType_ = Collections.unmodifiableList(this.contextReceiverType_);
                    }
                    if (((c3 == true ? 1 : 0) & 512) == 512) {
                        this.contextReceiverTypeId_ = Collections.unmodifiableList(this.contextReceiverTypeId_);
                    }
                    if (((c3 == true ? 1 : 0) & 8192) == 8192) {
                        this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                    }
                    try {
                        codedOutputStreamNewInstance.flush();
                    } catch (IOException unused2) {
                    } catch (Throwable th3) {
                        this.unknownFields = outputNewOutput.toByteString();
                        throw th3;
                    }
                    this.unknownFields = outputNewOutput.toByteString();
                    makeExtensionsImmutable();
                    return;
                }
            }
        }
    }

    public interface PropertyOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder {
    }

    public static final class QualifiedNameTable extends GeneratedMessageLite implements QualifiedNameTableOrBuilder {
        public static Parser<QualifiedNameTable> PARSER = new AbstractParser<QualifiedNameTable>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.QualifiedNameTable.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public QualifiedNameTable parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new QualifiedNameTable(codedInputStream, extensionRegistryLite);
            }
        };
        private static final QualifiedNameTable defaultInstance;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<QualifiedName> qualifiedName_;
        private final ByteString unknownFields;

        public static final class Builder extends GeneratedMessageLite.Builder<QualifiedNameTable, Builder> implements QualifiedNameTableOrBuilder {
            private int bitField0_;
            private List<QualifiedName> qualifiedName_ = Collections.emptyList();

            private Builder() {
                maybeForceBuilderInitialization();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            private void ensureQualifiedNameIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.qualifiedName_ = new ArrayList(this.qualifiedName_);
                    this.bitField0_ |= 1;
                }
            }

            private void maybeForceBuilderInitialization() {
            }

            public QualifiedNameTable buildPartial() {
                QualifiedNameTable qualifiedNameTable = new QualifiedNameTable(this);
                if ((this.bitField0_ & 1) == 1) {
                    this.qualifiedName_ = Collections.unmodifiableList(this.qualifiedName_);
                    this.bitField0_ &= -2;
                }
                qualifiedNameTable.qualifiedName_ = this.qualifiedName_;
                return qualifiedNameTable;
            }

            public QualifiedName getQualifiedName(int i2) {
                return this.qualifiedName_.get(i2);
            }

            public int getQualifiedNameCount() {
                return this.qualifiedName_.size();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i2 = 0; i2 < getQualifiedNameCount(); i2++) {
                    if (!getQualifiedName(i2).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public QualifiedNameTable build() {
                QualifiedNameTable qualifiedNameTableBuildPartial = buildPartial();
                if (qualifiedNameTableBuildPartial.isInitialized()) {
                    return qualifiedNameTableBuildPartial;
                }
                throw AbstractMessageLite.Builder.newUninitializedMessageException(qualifiedNameTableBuildPartial);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public QualifiedNameTable getDefaultInstanceForType() {
                return QualifiedNameTable.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo2084clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(QualifiedNameTable qualifiedNameTable) {
                if (qualifiedNameTable == QualifiedNameTable.getDefaultInstance()) {
                    return this;
                }
                if (!qualifiedNameTable.qualifiedName_.isEmpty()) {
                    if (this.qualifiedName_.isEmpty()) {
                        this.qualifiedName_ = qualifiedNameTable.qualifiedName_;
                        this.bitField0_ &= -2;
                    } else {
                        ensureQualifiedNameIsMutable();
                        this.qualifiedName_.addAll(qualifiedNameTable.qualifiedName_);
                    }
                }
                setUnknownFields(getUnknownFields().concat(qualifiedNameTable.unknownFields));
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.QualifiedNameTable.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$QualifiedNameTable> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.QualifiedNameTable.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$QualifiedNameTable r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.QualifiedNameTable) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    if (r3 == 0) goto Le
                    r2.mergeFrom(r3)
                Le:
                    return r2
                Lf:
                    r3 = move-exception
                    goto L1b
                L11:
                    r3 = move-exception
                    kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$QualifiedNameTable r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.QualifiedNameTable) r4     // Catch: java.lang.Throwable -> Lf
                    throw r3     // Catch: java.lang.Throwable -> L19
                L19:
                    r3 = move-exception
                    r0 = r4
                L1b:
                    if (r0 == 0) goto L20
                    r2.mergeFrom(r0)
                L20:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.QualifiedNameTable.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$QualifiedNameTable$Builder");
            }
        }

        public static final class QualifiedName extends GeneratedMessageLite implements QualifiedNameOrBuilder {
            public static Parser<QualifiedName> PARSER = new AbstractParser<QualifiedName>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.QualifiedNameTable.QualifiedName.1
                @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
                public QualifiedName parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                    return new QualifiedName(codedInputStream, extensionRegistryLite);
                }
            };
            private static final QualifiedName defaultInstance;
            private int bitField0_;
            private Kind kind_;
            private byte memoizedIsInitialized;
            private int memoizedSerializedSize;
            private int parentQualifiedName_;
            private int shortName_;
            private final ByteString unknownFields;

            public static final class Builder extends GeneratedMessageLite.Builder<QualifiedName, Builder> implements QualifiedNameOrBuilder {
                private int bitField0_;
                private int shortName_;
                private int parentQualifiedName_ = -1;
                private Kind kind_ = Kind.PACKAGE;

                private Builder() {
                    maybeForceBuilderInitialization();
                }

                /* JADX INFO: Access modifiers changed from: private */
                public static Builder create() {
                    return new Builder();
                }

                private void maybeForceBuilderInitialization() {
                }

                public QualifiedName buildPartial() {
                    QualifiedName qualifiedName = new QualifiedName(this);
                    int i2 = this.bitField0_;
                    int i3 = (i2 & 1) != 1 ? 0 : 1;
                    qualifiedName.parentQualifiedName_ = this.parentQualifiedName_;
                    if ((i2 & 2) == 2) {
                        i3 |= 2;
                    }
                    qualifiedName.shortName_ = this.shortName_;
                    if ((i2 & 4) == 4) {
                        i3 |= 4;
                    }
                    qualifiedName.kind_ = this.kind_;
                    qualifiedName.bitField0_ = i3;
                    return qualifiedName;
                }

                public boolean hasShortName() {
                    return (this.bitField0_ & 2) == 2;
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
                public final boolean isInitialized() {
                    return hasShortName();
                }

                public Builder setKind(Kind kind) {
                    kind.getClass();
                    this.bitField0_ |= 4;
                    this.kind_ = kind;
                    return this;
                }

                public Builder setParentQualifiedName(int i2) {
                    this.bitField0_ |= 1;
                    this.parentQualifiedName_ = i2;
                    return this;
                }

                public Builder setShortName(int i2) {
                    this.bitField0_ |= 2;
                    this.shortName_ = i2;
                    return this;
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
                public QualifiedName build() {
                    QualifiedName qualifiedNameBuildPartial = buildPartial();
                    if (qualifiedNameBuildPartial.isInitialized()) {
                        return qualifiedNameBuildPartial;
                    }
                    throw AbstractMessageLite.Builder.newUninitializedMessageException(qualifiedNameBuildPartial);
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
                public QualifiedName getDefaultInstanceForType() {
                    return QualifiedName.getDefaultInstance();
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
                /* renamed from: clone */
                public Builder mo2084clone() {
                    return create().mergeFrom(buildPartial());
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
                public Builder mergeFrom(QualifiedName qualifiedName) {
                    if (qualifiedName == QualifiedName.getDefaultInstance()) {
                        return this;
                    }
                    if (qualifiedName.hasParentQualifiedName()) {
                        setParentQualifiedName(qualifiedName.getParentQualifiedName());
                    }
                    if (qualifiedName.hasShortName()) {
                        setShortName(qualifiedName.getShortName());
                    }
                    if (qualifiedName.hasKind()) {
                        setKind(qualifiedName.getKind());
                    }
                    setUnknownFields(getUnknownFields().concat(qualifiedName.unknownFields));
                    return this;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
                @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.QualifiedNameTable.QualifiedName.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                    /*
                        r2 = this;
                        r0 = 0
                        kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$QualifiedNameTable$QualifiedName> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.QualifiedNameTable.QualifiedName.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                        java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                        kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$QualifiedNameTable$QualifiedName r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.QualifiedNameTable.QualifiedName) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                        if (r3 == 0) goto Le
                        r2.mergeFrom(r3)
                    Le:
                        return r2
                    Lf:
                        r3 = move-exception
                        goto L1b
                    L11:
                        r3 = move-exception
                        kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                        kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$QualifiedNameTable$QualifiedName r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.QualifiedNameTable.QualifiedName) r4     // Catch: java.lang.Throwable -> Lf
                        throw r3     // Catch: java.lang.Throwable -> L19
                    L19:
                        r3 = move-exception
                        r0 = r4
                    L1b:
                        if (r0 == 0) goto L20
                        r2.mergeFrom(r0)
                    L20:
                        throw r3
                    */
                    throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.QualifiedNameTable.QualifiedName.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$QualifiedNameTable$QualifiedName$Builder");
                }
            }

            public enum Kind implements Internal.EnumLite {
                CLASS(0, 0),
                PACKAGE(1, 1),
                LOCAL(2, 2);

                private static Internal.EnumLiteMap<Kind> internalValueMap = new Internal.EnumLiteMap<Kind>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.QualifiedNameTable.QualifiedName.Kind.1
                    @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLiteMap
                    public Kind findValueByNumber(int i2) {
                        return Kind.valueOf(i2);
                    }
                };
                private final int value;

                Kind(int i2, int i3) {
                    this.value = i3;
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLite
                public final int getNumber() {
                    return this.value;
                }

                public static Kind valueOf(int i2) {
                    if (i2 == 0) {
                        return CLASS;
                    }
                    if (i2 == 1) {
                        return PACKAGE;
                    }
                    if (i2 != 2) {
                        return null;
                    }
                    return LOCAL;
                }
            }

            static {
                QualifiedName qualifiedName = new QualifiedName(true);
                defaultInstance = qualifiedName;
                qualifiedName.initFields();
            }

            public static QualifiedName getDefaultInstance() {
                return defaultInstance;
            }

            private void initFields() {
                this.parentQualifiedName_ = -1;
                this.shortName_ = 0;
                this.kind_ = Kind.PACKAGE;
            }

            public static Builder newBuilder() {
                return Builder.create();
            }

            public Kind getKind() {
                return this.kind_;
            }

            public int getParentQualifiedName() {
                return this.parentQualifiedName_;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
            public Parser<QualifiedName> getParserForType() {
                return PARSER;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
            public int getSerializedSize() {
                int i2 = this.memoizedSerializedSize;
                if (i2 != -1) {
                    return i2;
                }
                int iComputeInt32Size = (this.bitField0_ & 1) == 1 ? 0 + CodedOutputStream.computeInt32Size(1, this.parentQualifiedName_) : 0;
                if ((this.bitField0_ & 2) == 2) {
                    iComputeInt32Size += CodedOutputStream.computeInt32Size(2, this.shortName_);
                }
                if ((this.bitField0_ & 4) == 4) {
                    iComputeInt32Size += CodedOutputStream.computeEnumSize(3, this.kind_.getNumber());
                }
                int size = iComputeInt32Size + this.unknownFields.size();
                this.memoizedSerializedSize = size;
                return size;
            }

            public int getShortName() {
                return this.shortName_;
            }

            public boolean hasKind() {
                return (this.bitField0_ & 4) == 4;
            }

            public boolean hasParentQualifiedName() {
                return (this.bitField0_ & 1) == 1;
            }

            public boolean hasShortName() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                byte b3 = this.memoizedIsInitialized;
                if (b3 == 1) {
                    return true;
                }
                if (b3 == 0) {
                    return false;
                }
                if (hasShortName()) {
                    this.memoizedIsInitialized = (byte) 1;
                    return true;
                }
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
            public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
                getSerializedSize();
                if ((this.bitField0_ & 1) == 1) {
                    codedOutputStream.writeInt32(1, this.parentQualifiedName_);
                }
                if ((this.bitField0_ & 2) == 2) {
                    codedOutputStream.writeInt32(2, this.shortName_);
                }
                if ((this.bitField0_ & 4) == 4) {
                    codedOutputStream.writeEnum(3, this.kind_.getNumber());
                }
                codedOutputStream.writeRawBytes(this.unknownFields);
            }

            public static Builder newBuilder(QualifiedName qualifiedName) {
                return newBuilder().mergeFrom(qualifiedName);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public QualifiedName getDefaultInstanceForType() {
                return defaultInstance;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
            public Builder newBuilderForType() {
                return newBuilder();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
            public Builder toBuilder() {
                return newBuilder(this);
            }

            private QualifiedName(GeneratedMessageLite.Builder builder) {
                super(builder);
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                this.unknownFields = builder.getUnknownFields();
            }

            private QualifiedName(boolean z2) {
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                this.unknownFields = ByteString.EMPTY;
            }

            private QualifiedName(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                initFields();
                ByteString.Output outputNewOutput = ByteString.newOutput();
                CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
                boolean z2 = false;
                while (!z2) {
                    try {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 8) {
                                    this.bitField0_ |= 1;
                                    this.parentQualifiedName_ = codedInputStream.readInt32();
                                } else if (tag == 16) {
                                    this.bitField0_ |= 2;
                                    this.shortName_ = codedInputStream.readInt32();
                                } else if (tag != 24) {
                                    if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    int i2 = codedInputStream.readEnum();
                                    Kind kindValueOf = Kind.valueOf(i2);
                                    if (kindValueOf == null) {
                                        codedOutputStreamNewInstance.writeRawVarint32(tag);
                                        codedOutputStreamNewInstance.writeRawVarint32(i2);
                                    } else {
                                        this.bitField0_ |= 4;
                                        this.kind_ = kindValueOf;
                                    }
                                }
                            }
                            z2 = true;
                        } catch (InvalidProtocolBufferException e2) {
                            throw e2.setUnfinishedMessage(this);
                        } catch (IOException e3) {
                            throw new InvalidProtocolBufferException(e3.getMessage()).setUnfinishedMessage(this);
                        }
                    } catch (Throwable th) {
                        try {
                            codedOutputStreamNewInstance.flush();
                        } catch (IOException unused) {
                        } catch (Throwable th2) {
                            this.unknownFields = outputNewOutput.toByteString();
                            throw th2;
                        }
                        this.unknownFields = outputNewOutput.toByteString();
                        makeExtensionsImmutable();
                        throw th;
                    }
                }
                try {
                    codedOutputStreamNewInstance.flush();
                } catch (IOException unused2) {
                } catch (Throwable th3) {
                    this.unknownFields = outputNewOutput.toByteString();
                    throw th3;
                }
                this.unknownFields = outputNewOutput.toByteString();
                makeExtensionsImmutable();
            }
        }

        public interface QualifiedNameOrBuilder extends MessageLiteOrBuilder {
        }

        static {
            QualifiedNameTable qualifiedNameTable = new QualifiedNameTable(true);
            defaultInstance = qualifiedNameTable;
            qualifiedNameTable.initFields();
        }

        public static QualifiedNameTable getDefaultInstance() {
            return defaultInstance;
        }

        private void initFields() {
            this.qualifiedName_ = Collections.emptyList();
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<QualifiedNameTable> getParserForType() {
            return PARSER;
        }

        public QualifiedName getQualifiedName(int i2) {
            return this.qualifiedName_.get(i2);
        }

        public int getQualifiedNameCount() {
            return this.qualifiedName_.size();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSerializedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeMessageSize = 0;
            for (int i3 = 0; i3 < this.qualifiedName_.size(); i3++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(1, this.qualifiedName_.get(i3));
            }
            int size = iComputeMessageSize + this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b3 = this.memoizedIsInitialized;
            if (b3 == 1) {
                return true;
            }
            if (b3 == 0) {
                return false;
            }
            for (int i2 = 0; i2 < getQualifiedNameCount(); i2++) {
                if (!getQualifiedName(i2).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            for (int i2 = 0; i2 < this.qualifiedName_.size(); i2++) {
                codedOutputStream.writeMessage(1, this.qualifiedName_.get(i2));
            }
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        public static Builder newBuilder(QualifiedNameTable qualifiedNameTable) {
            return newBuilder().mergeFrom(qualifiedNameTable);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public QualifiedNameTable getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        private QualifiedNameTable(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private QualifiedNameTable(boolean z2) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private QualifiedNameTable(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z2 = false;
            boolean z3 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag != 10) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                if (!(z3 & true)) {
                                    this.qualifiedName_ = new ArrayList();
                                    z3 |= true;
                                }
                                this.qualifiedName_.add(codedInputStream.readMessage(QualifiedName.PARSER, extensionRegistryLite));
                            }
                        }
                        z2 = true;
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if (z3 & true) {
                        this.qualifiedName_ = Collections.unmodifiableList(this.qualifiedName_);
                    }
                    try {
                        codedOutputStreamNewInstance.flush();
                    } catch (IOException unused) {
                    } catch (Throwable th2) {
                        this.unknownFields = outputNewOutput.toByteString();
                        throw th2;
                    }
                    this.unknownFields = outputNewOutput.toByteString();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if (z3 & true) {
                this.qualifiedName_ = Collections.unmodifiableList(this.qualifiedName_);
            }
            try {
                codedOutputStreamNewInstance.flush();
            } catch (IOException unused2) {
            } catch (Throwable th3) {
                this.unknownFields = outputNewOutput.toByteString();
                throw th3;
            }
            this.unknownFields = outputNewOutput.toByteString();
            makeExtensionsImmutable();
        }
    }

    public interface QualifiedNameTableOrBuilder extends MessageLiteOrBuilder {
    }

    public static final class StringTable extends GeneratedMessageLite implements StringTableOrBuilder {
        public static Parser<StringTable> PARSER = new AbstractParser<StringTable>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.StringTable.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public StringTable parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new StringTable(codedInputStream, extensionRegistryLite);
            }
        };
        private static final StringTable defaultInstance;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private LazyStringList string_;
        private final ByteString unknownFields;

        public static final class Builder extends GeneratedMessageLite.Builder<StringTable, Builder> implements StringTableOrBuilder {
            private int bitField0_;
            private LazyStringList string_ = LazyStringArrayList.EMPTY;

            private Builder() {
                maybeForceBuilderInitialization();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            private void ensureStringIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.string_ = new LazyStringArrayList(this.string_);
                    this.bitField0_ |= 1;
                }
            }

            private void maybeForceBuilderInitialization() {
            }

            public StringTable buildPartial() {
                StringTable stringTable = new StringTable(this);
                if ((this.bitField0_ & 1) == 1) {
                    this.string_ = this.string_.getUnmodifiableView();
                    this.bitField0_ &= -2;
                }
                stringTable.string_ = this.string_;
                return stringTable;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return true;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public StringTable build() {
                StringTable stringTableBuildPartial = buildPartial();
                if (stringTableBuildPartial.isInitialized()) {
                    return stringTableBuildPartial;
                }
                throw AbstractMessageLite.Builder.newUninitializedMessageException(stringTableBuildPartial);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public StringTable getDefaultInstanceForType() {
                return StringTable.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo2084clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(StringTable stringTable) {
                if (stringTable == StringTable.getDefaultInstance()) {
                    return this;
                }
                if (!stringTable.string_.isEmpty()) {
                    if (this.string_.isEmpty()) {
                        this.string_ = stringTable.string_;
                        this.bitField0_ &= -2;
                    } else {
                        ensureStringIsMutable();
                        this.string_.addAll(stringTable.string_);
                    }
                }
                setUnknownFields(getUnknownFields().concat(stringTable.unknownFields));
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.StringTable.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$StringTable> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.StringTable.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$StringTable r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.StringTable) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    if (r3 == 0) goto Le
                    r2.mergeFrom(r3)
                Le:
                    return r2
                Lf:
                    r3 = move-exception
                    goto L1b
                L11:
                    r3 = move-exception
                    kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$StringTable r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.StringTable) r4     // Catch: java.lang.Throwable -> Lf
                    throw r3     // Catch: java.lang.Throwable -> L19
                L19:
                    r3 = move-exception
                    r0 = r4
                L1b:
                    if (r0 == 0) goto L20
                    r2.mergeFrom(r0)
                L20:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.StringTable.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$StringTable$Builder");
            }
        }

        static {
            StringTable stringTable = new StringTable(true);
            defaultInstance = stringTable;
            stringTable.initFields();
        }

        public static StringTable getDefaultInstance() {
            return defaultInstance;
        }

        private void initFields() {
            this.string_ = LazyStringArrayList.EMPTY;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<StringTable> getParserForType() {
            return PARSER;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSerializedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeBytesSizeNoTag = 0;
            for (int i3 = 0; i3 < this.string_.size(); i3++) {
                iComputeBytesSizeNoTag += CodedOutputStream.computeBytesSizeNoTag(this.string_.getByteString(i3));
            }
            int size = 0 + iComputeBytesSizeNoTag + (getStringList().size() * 1) + this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
        }

        public String getString(int i2) {
            return this.string_.get(i2);
        }

        public ProtocolStringList getStringList() {
            return this.string_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b3 = this.memoizedIsInitialized;
            if (b3 == 1) {
                return true;
            }
            if (b3 == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            for (int i2 = 0; i2 < this.string_.size(); i2++) {
                codedOutputStream.writeBytes(1, this.string_.getByteString(i2));
            }
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        public static Builder newBuilder(StringTable stringTable) {
            return newBuilder().mergeFrom(stringTable);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public StringTable getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        private StringTable(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private StringTable(boolean z2) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        private StringTable(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z2 = false;
            boolean z3 = false;
            while (!z2) {
                try {
                    try {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag != 10) {
                                    if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    ByteString bytes = codedInputStream.readBytes();
                                    if (!(z3 & true)) {
                                        this.string_ = new LazyStringArrayList();
                                        z3 |= true;
                                    }
                                    this.string_.add(bytes);
                                }
                            }
                            z2 = true;
                        } catch (IOException e2) {
                            throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                        }
                    } catch (InvalidProtocolBufferException e3) {
                        throw e3.setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if (z3 & true) {
                        this.string_ = this.string_.getUnmodifiableView();
                    }
                    try {
                        codedOutputStreamNewInstance.flush();
                    } catch (IOException unused) {
                    } catch (Throwable th2) {
                        this.unknownFields = outputNewOutput.toByteString();
                        throw th2;
                    }
                    this.unknownFields = outputNewOutput.toByteString();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if (z3 & true) {
                this.string_ = this.string_.getUnmodifiableView();
            }
            try {
                codedOutputStreamNewInstance.flush();
            } catch (IOException unused2) {
            } catch (Throwable th3) {
                this.unknownFields = outputNewOutput.toByteString();
                throw th3;
            }
            this.unknownFields = outputNewOutput.toByteString();
            makeExtensionsImmutable();
        }
    }

    public interface StringTableOrBuilder extends MessageLiteOrBuilder {
    }

    public static final class Type extends GeneratedMessageLite.ExtendableMessage<Type> implements TypeOrBuilder {
        public static Parser<Type> PARSER = new AbstractParser<Type>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public Type parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new Type(codedInputStream, extensionRegistryLite);
            }
        };
        private static final Type defaultInstance;
        private int abbreviatedTypeId_;
        private Type abbreviatedType_;
        private List<Argument> argument_;
        private int bitField0_;
        private int className_;
        private int flags_;
        private int flexibleTypeCapabilitiesId_;
        private int flexibleUpperBoundId_;
        private Type flexibleUpperBound_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private boolean nullable_;
        private int outerTypeId_;
        private Type outerType_;
        private int typeAliasName_;
        private int typeParameterName_;
        private int typeParameter_;
        private final ByteString unknownFields;

        public static final class Argument extends GeneratedMessageLite implements ArgumentOrBuilder {
            public static Parser<Argument> PARSER = new AbstractParser<Argument>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type.Argument.1
                @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
                public Argument parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                    return new Argument(codedInputStream, extensionRegistryLite);
                }
            };
            private static final Argument defaultInstance;
            private int bitField0_;
            private byte memoizedIsInitialized;
            private int memoizedSerializedSize;
            private Projection projection_;
            private int typeId_;
            private Type type_;
            private final ByteString unknownFields;

            public static final class Builder extends GeneratedMessageLite.Builder<Argument, Builder> implements ArgumentOrBuilder {
                private int bitField0_;
                private int typeId_;
                private Projection projection_ = Projection.INV;
                private Type type_ = Type.getDefaultInstance();

                private Builder() {
                    maybeForceBuilderInitialization();
                }

                /* JADX INFO: Access modifiers changed from: private */
                public static Builder create() {
                    return new Builder();
                }

                private void maybeForceBuilderInitialization() {
                }

                public Argument buildPartial() {
                    Argument argument = new Argument(this);
                    int i2 = this.bitField0_;
                    int i3 = (i2 & 1) != 1 ? 0 : 1;
                    argument.projection_ = this.projection_;
                    if ((i2 & 2) == 2) {
                        i3 |= 2;
                    }
                    argument.type_ = this.type_;
                    if ((i2 & 4) == 4) {
                        i3 |= 4;
                    }
                    argument.typeId_ = this.typeId_;
                    argument.bitField0_ = i3;
                    return argument;
                }

                public Type getType() {
                    return this.type_;
                }

                public boolean hasType() {
                    return (this.bitField0_ & 2) == 2;
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
                public final boolean isInitialized() {
                    return !hasType() || getType().isInitialized();
                }

                public Builder mergeType(Type type) {
                    if ((this.bitField0_ & 2) != 2 || this.type_ == Type.getDefaultInstance()) {
                        this.type_ = type;
                    } else {
                        this.type_ = Type.newBuilder(this.type_).mergeFrom(type).buildPartial();
                    }
                    this.bitField0_ |= 2;
                    return this;
                }

                public Builder setProjection(Projection projection) {
                    projection.getClass();
                    this.bitField0_ |= 1;
                    this.projection_ = projection;
                    return this;
                }

                public Builder setTypeId(int i2) {
                    this.bitField0_ |= 4;
                    this.typeId_ = i2;
                    return this;
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
                public Argument build() {
                    Argument argumentBuildPartial = buildPartial();
                    if (argumentBuildPartial.isInitialized()) {
                        return argumentBuildPartial;
                    }
                    throw AbstractMessageLite.Builder.newUninitializedMessageException(argumentBuildPartial);
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
                public Argument getDefaultInstanceForType() {
                    return Argument.getDefaultInstance();
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
                /* renamed from: clone */
                public Builder mo2084clone() {
                    return create().mergeFrom(buildPartial());
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
                public Builder mergeFrom(Argument argument) {
                    if (argument == Argument.getDefaultInstance()) {
                        return this;
                    }
                    if (argument.hasProjection()) {
                        setProjection(argument.getProjection());
                    }
                    if (argument.hasType()) {
                        mergeType(argument.getType());
                    }
                    if (argument.hasTypeId()) {
                        setTypeId(argument.getTypeId());
                    }
                    setUnknownFields(getUnknownFields().concat(argument.unknownFields));
                    return this;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
                @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type.Argument.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                    /*
                        r2 = this;
                        r0 = 0
                        kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Type$Argument> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type.Argument.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                        java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                        kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Type$Argument r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type.Argument) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                        if (r3 == 0) goto Le
                        r2.mergeFrom(r3)
                    Le:
                        return r2
                    Lf:
                        r3 = move-exception
                        goto L1b
                    L11:
                        r3 = move-exception
                        kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                        kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Type$Argument r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type.Argument) r4     // Catch: java.lang.Throwable -> Lf
                        throw r3     // Catch: java.lang.Throwable -> L19
                    L19:
                        r3 = move-exception
                        r0 = r4
                    L1b:
                        if (r0 == 0) goto L20
                        r2.mergeFrom(r0)
                    L20:
                        throw r3
                    */
                    throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type.Argument.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Type$Argument$Builder");
                }
            }

            public enum Projection implements Internal.EnumLite {
                IN(0, 0),
                OUT(1, 1),
                INV(2, 2),
                STAR(3, 3);

                private static Internal.EnumLiteMap<Projection> internalValueMap = new Internal.EnumLiteMap<Projection>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type.Argument.Projection.1
                    @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLiteMap
                    public Projection findValueByNumber(int i2) {
                        return Projection.valueOf(i2);
                    }
                };
                private final int value;

                Projection(int i2, int i3) {
                    this.value = i3;
                }

                @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLite
                public final int getNumber() {
                    return this.value;
                }

                public static Projection valueOf(int i2) {
                    if (i2 == 0) {
                        return IN;
                    }
                    if (i2 == 1) {
                        return OUT;
                    }
                    if (i2 == 2) {
                        return INV;
                    }
                    if (i2 != 3) {
                        return null;
                    }
                    return STAR;
                }
            }

            static {
                Argument argument = new Argument(true);
                defaultInstance = argument;
                argument.initFields();
            }

            public static Argument getDefaultInstance() {
                return defaultInstance;
            }

            private void initFields() {
                this.projection_ = Projection.INV;
                this.type_ = Type.getDefaultInstance();
                this.typeId_ = 0;
            }

            public static Builder newBuilder() {
                return Builder.create();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
            public Parser<Argument> getParserForType() {
                return PARSER;
            }

            public Projection getProjection() {
                return this.projection_;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
            public int getSerializedSize() {
                int i2 = this.memoizedSerializedSize;
                if (i2 != -1) {
                    return i2;
                }
                int iComputeEnumSize = (this.bitField0_ & 1) == 1 ? 0 + CodedOutputStream.computeEnumSize(1, this.projection_.getNumber()) : 0;
                if ((this.bitField0_ & 2) == 2) {
                    iComputeEnumSize += CodedOutputStream.computeMessageSize(2, this.type_);
                }
                if ((this.bitField0_ & 4) == 4) {
                    iComputeEnumSize += CodedOutputStream.computeInt32Size(3, this.typeId_);
                }
                int size = iComputeEnumSize + this.unknownFields.size();
                this.memoizedSerializedSize = size;
                return size;
            }

            public Type getType() {
                return this.type_;
            }

            public int getTypeId() {
                return this.typeId_;
            }

            public boolean hasProjection() {
                return (this.bitField0_ & 1) == 1;
            }

            public boolean hasType() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean hasTypeId() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                byte b3 = this.memoizedIsInitialized;
                if (b3 == 1) {
                    return true;
                }
                if (b3 == 0) {
                    return false;
                }
                if (!hasType() || getType().isInitialized()) {
                    this.memoizedIsInitialized = (byte) 1;
                    return true;
                }
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
            public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
                getSerializedSize();
                if ((this.bitField0_ & 1) == 1) {
                    codedOutputStream.writeEnum(1, this.projection_.getNumber());
                }
                if ((this.bitField0_ & 2) == 2) {
                    codedOutputStream.writeMessage(2, this.type_);
                }
                if ((this.bitField0_ & 4) == 4) {
                    codedOutputStream.writeInt32(3, this.typeId_);
                }
                codedOutputStream.writeRawBytes(this.unknownFields);
            }

            public static Builder newBuilder(Argument argument) {
                return newBuilder().mergeFrom(argument);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public Argument getDefaultInstanceForType() {
                return defaultInstance;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
            public Builder newBuilderForType() {
                return newBuilder();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
            public Builder toBuilder() {
                return newBuilder(this);
            }

            private Argument(GeneratedMessageLite.Builder builder) {
                super(builder);
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                this.unknownFields = builder.getUnknownFields();
            }

            private Argument(boolean z2) {
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                this.unknownFields = ByteString.EMPTY;
            }

            private Argument(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                initFields();
                ByteString.Output outputNewOutput = ByteString.newOutput();
                CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
                boolean z2 = false;
                while (!z2) {
                    try {
                        try {
                            try {
                                int tag = codedInputStream.readTag();
                                if (tag != 0) {
                                    if (tag == 8) {
                                        int i2 = codedInputStream.readEnum();
                                        Projection projectionValueOf = Projection.valueOf(i2);
                                        if (projectionValueOf == null) {
                                            codedOutputStreamNewInstance.writeRawVarint32(tag);
                                            codedOutputStreamNewInstance.writeRawVarint32(i2);
                                        } else {
                                            this.bitField0_ |= 1;
                                            this.projection_ = projectionValueOf;
                                        }
                                    } else if (tag == 18) {
                                        Builder builder = (this.bitField0_ & 2) == 2 ? this.type_.toBuilder() : null;
                                        Type type = (Type) codedInputStream.readMessage(Type.PARSER, extensionRegistryLite);
                                        this.type_ = type;
                                        if (builder != null) {
                                            builder.mergeFrom(type);
                                            this.type_ = builder.buildPartial();
                                        }
                                        this.bitField0_ |= 2;
                                    } else if (tag != 24) {
                                        if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                        }
                                    } else {
                                        this.bitField0_ |= 4;
                                        this.typeId_ = codedInputStream.readInt32();
                                    }
                                }
                                z2 = true;
                            } catch (InvalidProtocolBufferException e2) {
                                throw e2.setUnfinishedMessage(this);
                            }
                        } catch (IOException e3) {
                            throw new InvalidProtocolBufferException(e3.getMessage()).setUnfinishedMessage(this);
                        }
                    } catch (Throwable th) {
                        try {
                            codedOutputStreamNewInstance.flush();
                        } catch (IOException unused) {
                        } catch (Throwable th2) {
                            this.unknownFields = outputNewOutput.toByteString();
                            throw th2;
                        }
                        this.unknownFields = outputNewOutput.toByteString();
                        makeExtensionsImmutable();
                        throw th;
                    }
                }
                try {
                    codedOutputStreamNewInstance.flush();
                } catch (IOException unused2) {
                } catch (Throwable th3) {
                    this.unknownFields = outputNewOutput.toByteString();
                    throw th3;
                }
                this.unknownFields = outputNewOutput.toByteString();
                makeExtensionsImmutable();
            }
        }

        public interface ArgumentOrBuilder extends MessageLiteOrBuilder {
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<Type, Builder> implements TypeOrBuilder {
            private int abbreviatedTypeId_;
            private int bitField0_;
            private int className_;
            private int flags_;
            private int flexibleTypeCapabilitiesId_;
            private int flexibleUpperBoundId_;
            private boolean nullable_;
            private int outerTypeId_;
            private int typeAliasName_;
            private int typeParameterName_;
            private int typeParameter_;
            private List<Argument> argument_ = Collections.emptyList();
            private Type flexibleUpperBound_ = Type.getDefaultInstance();
            private Type outerType_ = Type.getDefaultInstance();
            private Type abbreviatedType_ = Type.getDefaultInstance();

            private Builder() {
                maybeForceBuilderInitialization();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            private void ensureArgumentIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.argument_ = new ArrayList(this.argument_);
                    this.bitField0_ |= 1;
                }
            }

            private void maybeForceBuilderInitialization() {
            }

            public Type buildPartial() {
                Type type = new Type(this);
                int i2 = this.bitField0_;
                if ((i2 & 1) == 1) {
                    this.argument_ = Collections.unmodifiableList(this.argument_);
                    this.bitField0_ &= -2;
                }
                type.argument_ = this.argument_;
                int i3 = (i2 & 2) != 2 ? 0 : 1;
                type.nullable_ = this.nullable_;
                if ((i2 & 4) == 4) {
                    i3 |= 2;
                }
                type.flexibleTypeCapabilitiesId_ = this.flexibleTypeCapabilitiesId_;
                if ((i2 & 8) == 8) {
                    i3 |= 4;
                }
                type.flexibleUpperBound_ = this.flexibleUpperBound_;
                if ((i2 & 16) == 16) {
                    i3 |= 8;
                }
                type.flexibleUpperBoundId_ = this.flexibleUpperBoundId_;
                if ((i2 & 32) == 32) {
                    i3 |= 16;
                }
                type.className_ = this.className_;
                if ((i2 & 64) == 64) {
                    i3 |= 32;
                }
                type.typeParameter_ = this.typeParameter_;
                if ((i2 & 128) == 128) {
                    i3 |= 64;
                }
                type.typeParameterName_ = this.typeParameterName_;
                if ((i2 & 256) == 256) {
                    i3 |= 128;
                }
                type.typeAliasName_ = this.typeAliasName_;
                if ((i2 & 512) == 512) {
                    i3 |= 256;
                }
                type.outerType_ = this.outerType_;
                if ((i2 & 1024) == 1024) {
                    i3 |= 512;
                }
                type.outerTypeId_ = this.outerTypeId_;
                if ((i2 & 2048) == 2048) {
                    i3 |= 1024;
                }
                type.abbreviatedType_ = this.abbreviatedType_;
                if ((i2 & 4096) == 4096) {
                    i3 |= 2048;
                }
                type.abbreviatedTypeId_ = this.abbreviatedTypeId_;
                if ((i2 & 8192) == 8192) {
                    i3 |= 4096;
                }
                type.flags_ = this.flags_;
                type.bitField0_ = i3;
                return type;
            }

            public Type getAbbreviatedType() {
                return this.abbreviatedType_;
            }

            public Argument getArgument(int i2) {
                return this.argument_.get(i2);
            }

            public int getArgumentCount() {
                return this.argument_.size();
            }

            public Type getFlexibleUpperBound() {
                return this.flexibleUpperBound_;
            }

            public Type getOuterType() {
                return this.outerType_;
            }

            public boolean hasAbbreviatedType() {
                return (this.bitField0_ & 2048) == 2048;
            }

            public boolean hasFlexibleUpperBound() {
                return (this.bitField0_ & 8) == 8;
            }

            public boolean hasOuterType() {
                return (this.bitField0_ & 512) == 512;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i2 = 0; i2 < getArgumentCount(); i2++) {
                    if (!getArgument(i2).isInitialized()) {
                        return false;
                    }
                }
                if (hasFlexibleUpperBound() && !getFlexibleUpperBound().isInitialized()) {
                    return false;
                }
                if (!hasOuterType() || getOuterType().isInitialized()) {
                    return (!hasAbbreviatedType() || getAbbreviatedType().isInitialized()) && extensionsAreInitialized();
                }
                return false;
            }

            public Builder mergeAbbreviatedType(Type type) {
                if ((this.bitField0_ & 2048) != 2048 || this.abbreviatedType_ == Type.getDefaultInstance()) {
                    this.abbreviatedType_ = type;
                } else {
                    this.abbreviatedType_ = Type.newBuilder(this.abbreviatedType_).mergeFrom(type).buildPartial();
                }
                this.bitField0_ |= 2048;
                return this;
            }

            public Builder mergeFlexibleUpperBound(Type type) {
                if ((this.bitField0_ & 8) != 8 || this.flexibleUpperBound_ == Type.getDefaultInstance()) {
                    this.flexibleUpperBound_ = type;
                } else {
                    this.flexibleUpperBound_ = Type.newBuilder(this.flexibleUpperBound_).mergeFrom(type).buildPartial();
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder mergeOuterType(Type type) {
                if ((this.bitField0_ & 512) != 512 || this.outerType_ == Type.getDefaultInstance()) {
                    this.outerType_ = type;
                } else {
                    this.outerType_ = Type.newBuilder(this.outerType_).mergeFrom(type).buildPartial();
                }
                this.bitField0_ |= 512;
                return this;
            }

            public Builder setAbbreviatedTypeId(int i2) {
                this.bitField0_ |= 4096;
                this.abbreviatedTypeId_ = i2;
                return this;
            }

            public Builder setClassName(int i2) {
                this.bitField0_ |= 32;
                this.className_ = i2;
                return this;
            }

            public Builder setFlags(int i2) {
                this.bitField0_ |= 8192;
                this.flags_ = i2;
                return this;
            }

            public Builder setFlexibleTypeCapabilitiesId(int i2) {
                this.bitField0_ |= 4;
                this.flexibleTypeCapabilitiesId_ = i2;
                return this;
            }

            public Builder setFlexibleUpperBoundId(int i2) {
                this.bitField0_ |= 16;
                this.flexibleUpperBoundId_ = i2;
                return this;
            }

            public Builder setNullable(boolean z2) {
                this.bitField0_ |= 2;
                this.nullable_ = z2;
                return this;
            }

            public Builder setOuterTypeId(int i2) {
                this.bitField0_ |= 1024;
                this.outerTypeId_ = i2;
                return this;
            }

            public Builder setTypeAliasName(int i2) {
                this.bitField0_ |= 256;
                this.typeAliasName_ = i2;
                return this;
            }

            public Builder setTypeParameter(int i2) {
                this.bitField0_ |= 64;
                this.typeParameter_ = i2;
                return this;
            }

            public Builder setTypeParameterName(int i2) {
                this.bitField0_ |= 128;
                this.typeParameterName_ = i2;
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public Type build() {
                Type typeBuildPartial = buildPartial();
                if (typeBuildPartial.isInitialized()) {
                    return typeBuildPartial;
                }
                throw AbstractMessageLite.Builder.newUninitializedMessageException(typeBuildPartial);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public Type getDefaultInstanceForType() {
                return Type.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(Type type) {
                if (type == Type.getDefaultInstance()) {
                    return this;
                }
                if (!type.argument_.isEmpty()) {
                    if (this.argument_.isEmpty()) {
                        this.argument_ = type.argument_;
                        this.bitField0_ &= -2;
                    } else {
                        ensureArgumentIsMutable();
                        this.argument_.addAll(type.argument_);
                    }
                }
                if (type.hasNullable()) {
                    setNullable(type.getNullable());
                }
                if (type.hasFlexibleTypeCapabilitiesId()) {
                    setFlexibleTypeCapabilitiesId(type.getFlexibleTypeCapabilitiesId());
                }
                if (type.hasFlexibleUpperBound()) {
                    mergeFlexibleUpperBound(type.getFlexibleUpperBound());
                }
                if (type.hasFlexibleUpperBoundId()) {
                    setFlexibleUpperBoundId(type.getFlexibleUpperBoundId());
                }
                if (type.hasClassName()) {
                    setClassName(type.getClassName());
                }
                if (type.hasTypeParameter()) {
                    setTypeParameter(type.getTypeParameter());
                }
                if (type.hasTypeParameterName()) {
                    setTypeParameterName(type.getTypeParameterName());
                }
                if (type.hasTypeAliasName()) {
                    setTypeAliasName(type.getTypeAliasName());
                }
                if (type.hasOuterType()) {
                    mergeOuterType(type.getOuterType());
                }
                if (type.hasOuterTypeId()) {
                    setOuterTypeId(type.getOuterTypeId());
                }
                if (type.hasAbbreviatedType()) {
                    mergeAbbreviatedType(type.getAbbreviatedType());
                }
                if (type.hasAbbreviatedTypeId()) {
                    setAbbreviatedTypeId(type.getAbbreviatedTypeId());
                }
                if (type.hasFlags()) {
                    setFlags(type.getFlags());
                }
                mergeExtensionFields(type);
                setUnknownFields(getUnknownFields().concat(type.unknownFields));
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.ExtendableBuilder, kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo2084clone() {
                return create().mergeFrom(buildPartial());
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Type> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Type r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    if (r3 == 0) goto Le
                    r2.mergeFrom(r3)
                Le:
                    return r2
                Lf:
                    r3 = move-exception
                    goto L1b
                L11:
                    r3 = move-exception
                    kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Type r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type) r4     // Catch: java.lang.Throwable -> Lf
                    throw r3     // Catch: java.lang.Throwable -> L19
                L19:
                    r3 = move-exception
                    r0 = r4
                L1b:
                    if (r0 == 0) goto L20
                    r2.mergeFrom(r0)
                L20:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$Type$Builder");
            }
        }

        static {
            Type type = new Type(true);
            defaultInstance = type;
            type.initFields();
        }

        public static Type getDefaultInstance() {
            return defaultInstance;
        }

        private void initFields() {
            this.argument_ = Collections.emptyList();
            this.nullable_ = false;
            this.flexibleTypeCapabilitiesId_ = 0;
            this.flexibleUpperBound_ = getDefaultInstance();
            this.flexibleUpperBoundId_ = 0;
            this.className_ = 0;
            this.typeParameter_ = 0;
            this.typeParameterName_ = 0;
            this.typeAliasName_ = 0;
            this.outerType_ = getDefaultInstance();
            this.outerTypeId_ = 0;
            this.abbreviatedType_ = getDefaultInstance();
            this.abbreviatedTypeId_ = 0;
            this.flags_ = 0;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Type getAbbreviatedType() {
            return this.abbreviatedType_;
        }

        public int getAbbreviatedTypeId() {
            return this.abbreviatedTypeId_;
        }

        public Argument getArgument(int i2) {
            return this.argument_.get(i2);
        }

        public int getArgumentCount() {
            return this.argument_.size();
        }

        public List<Argument> getArgumentList() {
            return this.argument_;
        }

        public int getClassName() {
            return this.className_;
        }

        public int getFlags() {
            return this.flags_;
        }

        public int getFlexibleTypeCapabilitiesId() {
            return this.flexibleTypeCapabilitiesId_;
        }

        public Type getFlexibleUpperBound() {
            return this.flexibleUpperBound_;
        }

        public int getFlexibleUpperBoundId() {
            return this.flexibleUpperBoundId_;
        }

        public boolean getNullable() {
            return this.nullable_;
        }

        public Type getOuterType() {
            return this.outerType_;
        }

        public int getOuterTypeId() {
            return this.outerTypeId_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<Type> getParserForType() {
            return PARSER;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSerializedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeInt32Size = (this.bitField0_ & 4096) == 4096 ? CodedOutputStream.computeInt32Size(1, this.flags_) + 0 : 0;
            for (int i3 = 0; i3 < this.argument_.size(); i3++) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(2, this.argument_.get(i3));
            }
            if ((this.bitField0_ & 1) == 1) {
                iComputeInt32Size += CodedOutputStream.computeBoolSize(3, this.nullable_);
            }
            if ((this.bitField0_ & 2) == 2) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(4, this.flexibleTypeCapabilitiesId_);
            }
            if ((this.bitField0_ & 4) == 4) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(5, this.flexibleUpperBound_);
            }
            if ((this.bitField0_ & 16) == 16) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(6, this.className_);
            }
            if ((this.bitField0_ & 32) == 32) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(7, this.typeParameter_);
            }
            if ((this.bitField0_ & 8) == 8) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(8, this.flexibleUpperBoundId_);
            }
            if ((this.bitField0_ & 64) == 64) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(9, this.typeParameterName_);
            }
            if ((this.bitField0_ & 256) == 256) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(10, this.outerType_);
            }
            if ((this.bitField0_ & 512) == 512) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(11, this.outerTypeId_);
            }
            if ((this.bitField0_ & 128) == 128) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(12, this.typeAliasName_);
            }
            if ((this.bitField0_ & 1024) == 1024) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(13, this.abbreviatedType_);
            }
            if ((this.bitField0_ & 2048) == 2048) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(14, this.abbreviatedTypeId_);
            }
            int iExtensionsSerializedSize = iComputeInt32Size + extensionsSerializedSize() + this.unknownFields.size();
            this.memoizedSerializedSize = iExtensionsSerializedSize;
            return iExtensionsSerializedSize;
        }

        public int getTypeAliasName() {
            return this.typeAliasName_;
        }

        public int getTypeParameter() {
            return this.typeParameter_;
        }

        public int getTypeParameterName() {
            return this.typeParameterName_;
        }

        public boolean hasAbbreviatedType() {
            return (this.bitField0_ & 1024) == 1024;
        }

        public boolean hasAbbreviatedTypeId() {
            return (this.bitField0_ & 2048) == 2048;
        }

        public boolean hasClassName() {
            return (this.bitField0_ & 16) == 16;
        }

        public boolean hasFlags() {
            return (this.bitField0_ & 4096) == 4096;
        }

        public boolean hasFlexibleTypeCapabilitiesId() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean hasFlexibleUpperBound() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean hasFlexibleUpperBoundId() {
            return (this.bitField0_ & 8) == 8;
        }

        public boolean hasNullable() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean hasOuterType() {
            return (this.bitField0_ & 256) == 256;
        }

        public boolean hasOuterTypeId() {
            return (this.bitField0_ & 512) == 512;
        }

        public boolean hasTypeAliasName() {
            return (this.bitField0_ & 128) == 128;
        }

        public boolean hasTypeParameter() {
            return (this.bitField0_ & 32) == 32;
        }

        public boolean hasTypeParameterName() {
            return (this.bitField0_ & 64) == 64;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b3 = this.memoizedIsInitialized;
            if (b3 == 1) {
                return true;
            }
            if (b3 == 0) {
                return false;
            }
            for (int i2 = 0; i2 < getArgumentCount(); i2++) {
                if (!getArgument(i2).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (hasFlexibleUpperBound() && !getFlexibleUpperBound().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            if (hasOuterType() && !getOuterType().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            if (hasAbbreviatedType() && !getAbbreviatedType().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            if (extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            GeneratedMessageLite.ExtendableMessage<MessageType>.ExtensionWriter extensionWriterNewExtensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 4096) == 4096) {
                codedOutputStream.writeInt32(1, this.flags_);
            }
            for (int i2 = 0; i2 < this.argument_.size(); i2++) {
                codedOutputStream.writeMessage(2, this.argument_.get(i2));
            }
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeBool(3, this.nullable_);
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeInt32(4, this.flexibleTypeCapabilitiesId_);
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeMessage(5, this.flexibleUpperBound_);
            }
            if ((this.bitField0_ & 16) == 16) {
                codedOutputStream.writeInt32(6, this.className_);
            }
            if ((this.bitField0_ & 32) == 32) {
                codedOutputStream.writeInt32(7, this.typeParameter_);
            }
            if ((this.bitField0_ & 8) == 8) {
                codedOutputStream.writeInt32(8, this.flexibleUpperBoundId_);
            }
            if ((this.bitField0_ & 64) == 64) {
                codedOutputStream.writeInt32(9, this.typeParameterName_);
            }
            if ((this.bitField0_ & 256) == 256) {
                codedOutputStream.writeMessage(10, this.outerType_);
            }
            if ((this.bitField0_ & 512) == 512) {
                codedOutputStream.writeInt32(11, this.outerTypeId_);
            }
            if ((this.bitField0_ & 128) == 128) {
                codedOutputStream.writeInt32(12, this.typeAliasName_);
            }
            if ((this.bitField0_ & 1024) == 1024) {
                codedOutputStream.writeMessage(13, this.abbreviatedType_);
            }
            if ((this.bitField0_ & 2048) == 2048) {
                codedOutputStream.writeInt32(14, this.abbreviatedTypeId_);
            }
            extensionWriterNewExtensionWriter.writeUntil(200, codedOutputStream);
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        public static Builder newBuilder(Type type) {
            return newBuilder().mergeFrom(type);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public Type getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        private Type(GeneratedMessageLite.ExtendableBuilder<Type, ?> extendableBuilder) {
            super(extendableBuilder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = extendableBuilder.getUnknownFields();
        }

        private Type(boolean z2) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private Type(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            Builder builder;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z2 = false;
            boolean z3 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        switch (tag) {
                            case 0:
                                z2 = true;
                            case 8:
                                this.bitField0_ |= 4096;
                                this.flags_ = codedInputStream.readInt32();
                            case 18:
                                if (!(z3 & true)) {
                                    this.argument_ = new ArrayList();
                                    z3 |= true;
                                }
                                this.argument_.add(codedInputStream.readMessage(Argument.PARSER, extensionRegistryLite));
                            case 24:
                                this.bitField0_ |= 1;
                                this.nullable_ = codedInputStream.readBool();
                            case 32:
                                this.bitField0_ |= 2;
                                this.flexibleTypeCapabilitiesId_ = codedInputStream.readInt32();
                            case 42:
                                builder = (this.bitField0_ & 4) == 4 ? this.flexibleUpperBound_.toBuilder() : null;
                                Type type = (Type) codedInputStream.readMessage(PARSER, extensionRegistryLite);
                                this.flexibleUpperBound_ = type;
                                if (builder != null) {
                                    builder.mergeFrom(type);
                                    this.flexibleUpperBound_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 4;
                            case 48:
                                this.bitField0_ |= 16;
                                this.className_ = codedInputStream.readInt32();
                            case 56:
                                this.bitField0_ |= 32;
                                this.typeParameter_ = codedInputStream.readInt32();
                            case 64:
                                this.bitField0_ |= 8;
                                this.flexibleUpperBoundId_ = codedInputStream.readInt32();
                            case 72:
                                this.bitField0_ |= 64;
                                this.typeParameterName_ = codedInputStream.readInt32();
                            case 82:
                                builder = (this.bitField0_ & 256) == 256 ? this.outerType_.toBuilder() : null;
                                Type type2 = (Type) codedInputStream.readMessage(PARSER, extensionRegistryLite);
                                this.outerType_ = type2;
                                if (builder != null) {
                                    builder.mergeFrom(type2);
                                    this.outerType_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 256;
                            case 88:
                                this.bitField0_ |= 512;
                                this.outerTypeId_ = codedInputStream.readInt32();
                            case 96:
                                this.bitField0_ |= 128;
                                this.typeAliasName_ = codedInputStream.readInt32();
                            case 106:
                                builder = (this.bitField0_ & 1024) == 1024 ? this.abbreviatedType_.toBuilder() : null;
                                Type type3 = (Type) codedInputStream.readMessage(PARSER, extensionRegistryLite);
                                this.abbreviatedType_ = type3;
                                if (builder != null) {
                                    builder.mergeFrom(type3);
                                    this.abbreviatedType_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 1024;
                            case 112:
                                this.bitField0_ |= 2048;
                                this.abbreviatedTypeId_ = codedInputStream.readInt32();
                            default:
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                    z2 = true;
                                }
                        }
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if (z3 & true) {
                        this.argument_ = Collections.unmodifiableList(this.argument_);
                    }
                    try {
                        codedOutputStreamNewInstance.flush();
                    } catch (IOException unused) {
                    } catch (Throwable th2) {
                        this.unknownFields = outputNewOutput.toByteString();
                        throw th2;
                    }
                    this.unknownFields = outputNewOutput.toByteString();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if (z3 & true) {
                this.argument_ = Collections.unmodifiableList(this.argument_);
            }
            try {
                codedOutputStreamNewInstance.flush();
            } catch (IOException unused2) {
            } catch (Throwable th3) {
                this.unknownFields = outputNewOutput.toByteString();
                throw th3;
            }
            this.unknownFields = outputNewOutput.toByteString();
            makeExtensionsImmutable();
        }
    }

    public static final class TypeAlias extends GeneratedMessageLite.ExtendableMessage<TypeAlias> implements TypeAliasOrBuilder {
        public static Parser<TypeAlias> PARSER = new AbstractParser<TypeAlias>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeAlias.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public TypeAlias parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new TypeAlias(codedInputStream, extensionRegistryLite);
            }
        };
        private static final TypeAlias defaultInstance;
        private List<Annotation> annotation_;
        private int bitField0_;
        private int expandedTypeId_;
        private Type expandedType_;
        private int flags_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private int name_;
        private List<TypeParameter> typeParameter_;
        private int underlyingTypeId_;
        private Type underlyingType_;
        private final ByteString unknownFields;
        private List<Integer> versionRequirement_;

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<TypeAlias, Builder> implements TypeAliasOrBuilder {
            private int bitField0_;
            private int expandedTypeId_;
            private int name_;
            private int underlyingTypeId_;
            private int flags_ = 6;
            private List<TypeParameter> typeParameter_ = Collections.emptyList();
            private Type underlyingType_ = Type.getDefaultInstance();
            private Type expandedType_ = Type.getDefaultInstance();
            private List<Annotation> annotation_ = Collections.emptyList();
            private List<Integer> versionRequirement_ = Collections.emptyList();

            private Builder() {
                maybeForceBuilderInitialization();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            private void ensureAnnotationIsMutable() {
                if ((this.bitField0_ & 128) != 128) {
                    this.annotation_ = new ArrayList(this.annotation_);
                    this.bitField0_ |= 128;
                }
            }

            private void ensureTypeParameterIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.typeParameter_ = new ArrayList(this.typeParameter_);
                    this.bitField0_ |= 4;
                }
            }

            private void ensureVersionRequirementIsMutable() {
                if ((this.bitField0_ & 256) != 256) {
                    this.versionRequirement_ = new ArrayList(this.versionRequirement_);
                    this.bitField0_ |= 256;
                }
            }

            private void maybeForceBuilderInitialization() {
            }

            public TypeAlias buildPartial() {
                TypeAlias typeAlias = new TypeAlias(this);
                int i2 = this.bitField0_;
                int i3 = (i2 & 1) != 1 ? 0 : 1;
                typeAlias.flags_ = this.flags_;
                if ((i2 & 2) == 2) {
                    i3 |= 2;
                }
                typeAlias.name_ = this.name_;
                if ((this.bitField0_ & 4) == 4) {
                    this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                    this.bitField0_ &= -5;
                }
                typeAlias.typeParameter_ = this.typeParameter_;
                if ((i2 & 8) == 8) {
                    i3 |= 4;
                }
                typeAlias.underlyingType_ = this.underlyingType_;
                if ((i2 & 16) == 16) {
                    i3 |= 8;
                }
                typeAlias.underlyingTypeId_ = this.underlyingTypeId_;
                if ((i2 & 32) == 32) {
                    i3 |= 16;
                }
                typeAlias.expandedType_ = this.expandedType_;
                if ((i2 & 64) == 64) {
                    i3 |= 32;
                }
                typeAlias.expandedTypeId_ = this.expandedTypeId_;
                if ((this.bitField0_ & 128) == 128) {
                    this.annotation_ = Collections.unmodifiableList(this.annotation_);
                    this.bitField0_ &= -129;
                }
                typeAlias.annotation_ = this.annotation_;
                if ((this.bitField0_ & 256) == 256) {
                    this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                    this.bitField0_ &= -257;
                }
                typeAlias.versionRequirement_ = this.versionRequirement_;
                typeAlias.bitField0_ = i3;
                return typeAlias;
            }

            public Annotation getAnnotation(int i2) {
                return this.annotation_.get(i2);
            }

            public int getAnnotationCount() {
                return this.annotation_.size();
            }

            public Type getExpandedType() {
                return this.expandedType_;
            }

            public TypeParameter getTypeParameter(int i2) {
                return this.typeParameter_.get(i2);
            }

            public int getTypeParameterCount() {
                return this.typeParameter_.size();
            }

            public Type getUnderlyingType() {
                return this.underlyingType_;
            }

            public boolean hasExpandedType() {
                return (this.bitField0_ & 32) == 32;
            }

            public boolean hasName() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean hasUnderlyingType() {
                return (this.bitField0_ & 8) == 8;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                if (!hasName()) {
                    return false;
                }
                for (int i2 = 0; i2 < getTypeParameterCount(); i2++) {
                    if (!getTypeParameter(i2).isInitialized()) {
                        return false;
                    }
                }
                if (hasUnderlyingType() && !getUnderlyingType().isInitialized()) {
                    return false;
                }
                if (hasExpandedType() && !getExpandedType().isInitialized()) {
                    return false;
                }
                for (int i3 = 0; i3 < getAnnotationCount(); i3++) {
                    if (!getAnnotation(i3).isInitialized()) {
                        return false;
                    }
                }
                return extensionsAreInitialized();
            }

            public Builder mergeExpandedType(Type type) {
                if ((this.bitField0_ & 32) != 32 || this.expandedType_ == Type.getDefaultInstance()) {
                    this.expandedType_ = type;
                } else {
                    this.expandedType_ = Type.newBuilder(this.expandedType_).mergeFrom(type).buildPartial();
                }
                this.bitField0_ |= 32;
                return this;
            }

            public Builder mergeUnderlyingType(Type type) {
                if ((this.bitField0_ & 8) != 8 || this.underlyingType_ == Type.getDefaultInstance()) {
                    this.underlyingType_ = type;
                } else {
                    this.underlyingType_ = Type.newBuilder(this.underlyingType_).mergeFrom(type).buildPartial();
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder setExpandedTypeId(int i2) {
                this.bitField0_ |= 64;
                this.expandedTypeId_ = i2;
                return this;
            }

            public Builder setFlags(int i2) {
                this.bitField0_ |= 1;
                this.flags_ = i2;
                return this;
            }

            public Builder setName(int i2) {
                this.bitField0_ |= 2;
                this.name_ = i2;
                return this;
            }

            public Builder setUnderlyingTypeId(int i2) {
                this.bitField0_ |= 16;
                this.underlyingTypeId_ = i2;
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public TypeAlias build() {
                TypeAlias typeAliasBuildPartial = buildPartial();
                if (typeAliasBuildPartial.isInitialized()) {
                    return typeAliasBuildPartial;
                }
                throw AbstractMessageLite.Builder.newUninitializedMessageException(typeAliasBuildPartial);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public TypeAlias getDefaultInstanceForType() {
                return TypeAlias.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(TypeAlias typeAlias) {
                if (typeAlias == TypeAlias.getDefaultInstance()) {
                    return this;
                }
                if (typeAlias.hasFlags()) {
                    setFlags(typeAlias.getFlags());
                }
                if (typeAlias.hasName()) {
                    setName(typeAlias.getName());
                }
                if (!typeAlias.typeParameter_.isEmpty()) {
                    if (this.typeParameter_.isEmpty()) {
                        this.typeParameter_ = typeAlias.typeParameter_;
                        this.bitField0_ &= -5;
                    } else {
                        ensureTypeParameterIsMutable();
                        this.typeParameter_.addAll(typeAlias.typeParameter_);
                    }
                }
                if (typeAlias.hasUnderlyingType()) {
                    mergeUnderlyingType(typeAlias.getUnderlyingType());
                }
                if (typeAlias.hasUnderlyingTypeId()) {
                    setUnderlyingTypeId(typeAlias.getUnderlyingTypeId());
                }
                if (typeAlias.hasExpandedType()) {
                    mergeExpandedType(typeAlias.getExpandedType());
                }
                if (typeAlias.hasExpandedTypeId()) {
                    setExpandedTypeId(typeAlias.getExpandedTypeId());
                }
                if (!typeAlias.annotation_.isEmpty()) {
                    if (this.annotation_.isEmpty()) {
                        this.annotation_ = typeAlias.annotation_;
                        this.bitField0_ &= -129;
                    } else {
                        ensureAnnotationIsMutable();
                        this.annotation_.addAll(typeAlias.annotation_);
                    }
                }
                if (!typeAlias.versionRequirement_.isEmpty()) {
                    if (this.versionRequirement_.isEmpty()) {
                        this.versionRequirement_ = typeAlias.versionRequirement_;
                        this.bitField0_ &= -257;
                    } else {
                        ensureVersionRequirementIsMutable();
                        this.versionRequirement_.addAll(typeAlias.versionRequirement_);
                    }
                }
                mergeExtensionFields(typeAlias);
                setUnknownFields(getUnknownFields().concat(typeAlias.unknownFields));
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.ExtendableBuilder, kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo2084clone() {
                return create().mergeFrom(buildPartial());
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeAlias.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$TypeAlias> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeAlias.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$TypeAlias r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeAlias) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    if (r3 == 0) goto Le
                    r2.mergeFrom(r3)
                Le:
                    return r2
                Lf:
                    r3 = move-exception
                    goto L1b
                L11:
                    r3 = move-exception
                    kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$TypeAlias r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeAlias) r4     // Catch: java.lang.Throwable -> Lf
                    throw r3     // Catch: java.lang.Throwable -> L19
                L19:
                    r3 = move-exception
                    r0 = r4
                L1b:
                    if (r0 == 0) goto L20
                    r2.mergeFrom(r0)
                L20:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeAlias.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$TypeAlias$Builder");
            }
        }

        static {
            TypeAlias typeAlias = new TypeAlias(true);
            defaultInstance = typeAlias;
            typeAlias.initFields();
        }

        public static TypeAlias getDefaultInstance() {
            return defaultInstance;
        }

        private void initFields() {
            this.flags_ = 6;
            this.name_ = 0;
            this.typeParameter_ = Collections.emptyList();
            this.underlyingType_ = Type.getDefaultInstance();
            this.underlyingTypeId_ = 0;
            this.expandedType_ = Type.getDefaultInstance();
            this.expandedTypeId_ = 0;
            this.annotation_ = Collections.emptyList();
            this.versionRequirement_ = Collections.emptyList();
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public static TypeAlias parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public Annotation getAnnotation(int i2) {
            return this.annotation_.get(i2);
        }

        public int getAnnotationCount() {
            return this.annotation_.size();
        }

        public List<Annotation> getAnnotationList() {
            return this.annotation_;
        }

        public Type getExpandedType() {
            return this.expandedType_;
        }

        public int getExpandedTypeId() {
            return this.expandedTypeId_;
        }

        public int getFlags() {
            return this.flags_;
        }

        public int getName() {
            return this.name_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<TypeAlias> getParserForType() {
            return PARSER;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSerializedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeInt32Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt32Size(1, this.flags_) + 0 : 0;
            if ((this.bitField0_ & 2) == 2) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(2, this.name_);
            }
            for (int i3 = 0; i3 < this.typeParameter_.size(); i3++) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(3, this.typeParameter_.get(i3));
            }
            if ((this.bitField0_ & 4) == 4) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(4, this.underlyingType_);
            }
            if ((this.bitField0_ & 8) == 8) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(5, this.underlyingTypeId_);
            }
            if ((this.bitField0_ & 16) == 16) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(6, this.expandedType_);
            }
            if ((this.bitField0_ & 32) == 32) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(7, this.expandedTypeId_);
            }
            for (int i4 = 0; i4 < this.annotation_.size(); i4++) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(8, this.annotation_.get(i4));
            }
            int iComputeInt32SizeNoTag = 0;
            for (int i5 = 0; i5 < this.versionRequirement_.size(); i5++) {
                iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(this.versionRequirement_.get(i5).intValue());
            }
            int size = iComputeInt32Size + iComputeInt32SizeNoTag + (getVersionRequirementList().size() * 2) + extensionsSerializedSize() + this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
        }

        public TypeParameter getTypeParameter(int i2) {
            return this.typeParameter_.get(i2);
        }

        public int getTypeParameterCount() {
            return this.typeParameter_.size();
        }

        public List<TypeParameter> getTypeParameterList() {
            return this.typeParameter_;
        }

        public Type getUnderlyingType() {
            return this.underlyingType_;
        }

        public int getUnderlyingTypeId() {
            return this.underlyingTypeId_;
        }

        public List<Integer> getVersionRequirementList() {
            return this.versionRequirement_;
        }

        public boolean hasExpandedType() {
            return (this.bitField0_ & 16) == 16;
        }

        public boolean hasExpandedTypeId() {
            return (this.bitField0_ & 32) == 32;
        }

        public boolean hasFlags() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean hasName() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean hasUnderlyingType() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean hasUnderlyingTypeId() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b3 = this.memoizedIsInitialized;
            if (b3 == 1) {
                return true;
            }
            if (b3 == 0) {
                return false;
            }
            if (!hasName()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            for (int i2 = 0; i2 < getTypeParameterCount(); i2++) {
                if (!getTypeParameter(i2).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (hasUnderlyingType() && !getUnderlyingType().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            if (hasExpandedType() && !getExpandedType().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            for (int i3 = 0; i3 < getAnnotationCount(); i3++) {
                if (!getAnnotation(i3).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            GeneratedMessageLite.ExtendableMessage<MessageType>.ExtensionWriter extensionWriterNewExtensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeInt32(1, this.flags_);
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeInt32(2, this.name_);
            }
            for (int i2 = 0; i2 < this.typeParameter_.size(); i2++) {
                codedOutputStream.writeMessage(3, this.typeParameter_.get(i2));
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeMessage(4, this.underlyingType_);
            }
            if ((this.bitField0_ & 8) == 8) {
                codedOutputStream.writeInt32(5, this.underlyingTypeId_);
            }
            if ((this.bitField0_ & 16) == 16) {
                codedOutputStream.writeMessage(6, this.expandedType_);
            }
            if ((this.bitField0_ & 32) == 32) {
                codedOutputStream.writeInt32(7, this.expandedTypeId_);
            }
            for (int i3 = 0; i3 < this.annotation_.size(); i3++) {
                codedOutputStream.writeMessage(8, this.annotation_.get(i3));
            }
            for (int i4 = 0; i4 < this.versionRequirement_.size(); i4++) {
                codedOutputStream.writeInt32(31, this.versionRequirement_.get(i4).intValue());
            }
            extensionWriterNewExtensionWriter.writeUntil(200, codedOutputStream);
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        public static Builder newBuilder(TypeAlias typeAlias) {
            return newBuilder().mergeFrom(typeAlias);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public TypeAlias getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        private TypeAlias(GeneratedMessageLite.ExtendableBuilder<TypeAlias, ?> extendableBuilder) {
            super(extendableBuilder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = extendableBuilder.getUnknownFields();
        }

        private TypeAlias(boolean z2) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r5v0 */
        /* JADX WARN: Type inference failed for: r5v1 */
        /* JADX WARN: Type inference failed for: r5v2, types: [boolean] */
        private TypeAlias(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            Type.Builder builder;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z2 = false;
            int i2 = 0;
            while (true) {
                ?? unknownField = 128;
                if (!z2) {
                    try {
                        try {
                            try {
                                int tag = codedInputStream.readTag();
                                switch (tag) {
                                    case 0:
                                        z2 = true;
                                    case 8:
                                        this.bitField0_ |= 1;
                                        this.flags_ = codedInputStream.readInt32();
                                    case 16:
                                        this.bitField0_ |= 2;
                                        this.name_ = codedInputStream.readInt32();
                                    case 26:
                                        if ((i2 & 4) != 4) {
                                            this.typeParameter_ = new ArrayList();
                                            i2 |= 4;
                                        }
                                        this.typeParameter_.add(codedInputStream.readMessage(TypeParameter.PARSER, extensionRegistryLite));
                                    case 34:
                                        builder = (this.bitField0_ & 4) == 4 ? this.underlyingType_.toBuilder() : null;
                                        Type type = (Type) codedInputStream.readMessage(Type.PARSER, extensionRegistryLite);
                                        this.underlyingType_ = type;
                                        if (builder != null) {
                                            builder.mergeFrom(type);
                                            this.underlyingType_ = builder.buildPartial();
                                        }
                                        this.bitField0_ |= 4;
                                    case 40:
                                        this.bitField0_ |= 8;
                                        this.underlyingTypeId_ = codedInputStream.readInt32();
                                    case 50:
                                        builder = (this.bitField0_ & 16) == 16 ? this.expandedType_.toBuilder() : null;
                                        Type type2 = (Type) codedInputStream.readMessage(Type.PARSER, extensionRegistryLite);
                                        this.expandedType_ = type2;
                                        if (builder != null) {
                                            builder.mergeFrom(type2);
                                            this.expandedType_ = builder.buildPartial();
                                        }
                                        this.bitField0_ |= 16;
                                    case 56:
                                        this.bitField0_ |= 32;
                                        this.expandedTypeId_ = codedInputStream.readInt32();
                                    case 66:
                                        if ((i2 & 128) != 128) {
                                            this.annotation_ = new ArrayList();
                                            i2 |= 128;
                                        }
                                        this.annotation_.add(codedInputStream.readMessage(Annotation.PARSER, extensionRegistryLite));
                                    case R2.attr.actionModeFindDrawable /* 248 */:
                                        if ((i2 & 256) != 256) {
                                            this.versionRequirement_ = new ArrayList();
                                            i2 |= 256;
                                        }
                                        this.versionRequirement_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    case 250:
                                        int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                        if ((i2 & 256) != 256 && codedInputStream.getBytesUntilLimit() > 0) {
                                            this.versionRequirement_ = new ArrayList();
                                            i2 |= 256;
                                        }
                                        while (codedInputStream.getBytesUntilLimit() > 0) {
                                            this.versionRequirement_.add(Integer.valueOf(codedInputStream.readInt32()));
                                        }
                                        codedInputStream.popLimit(iPushLimit);
                                        break;
                                    default:
                                        unknownField = parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag);
                                        if (unknownField == 0) {
                                            z2 = true;
                                        }
                                }
                            } catch (InvalidProtocolBufferException e2) {
                                throw e2.setUnfinishedMessage(this);
                            }
                        } catch (IOException e3) {
                            throw new InvalidProtocolBufferException(e3.getMessage()).setUnfinishedMessage(this);
                        }
                    } catch (Throwable th) {
                        if ((i2 & 4) == 4) {
                            this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                        }
                        if ((i2 & 128) == unknownField) {
                            this.annotation_ = Collections.unmodifiableList(this.annotation_);
                        }
                        if ((i2 & 256) == 256) {
                            this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                        }
                        try {
                            codedOutputStreamNewInstance.flush();
                        } catch (IOException unused) {
                        } catch (Throwable th2) {
                            this.unknownFields = outputNewOutput.toByteString();
                            throw th2;
                        }
                        this.unknownFields = outputNewOutput.toByteString();
                        makeExtensionsImmutable();
                        throw th;
                    }
                } else {
                    if ((i2 & 4) == 4) {
                        this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                    }
                    if ((i2 & 128) == 128) {
                        this.annotation_ = Collections.unmodifiableList(this.annotation_);
                    }
                    if ((i2 & 256) == 256) {
                        this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                    }
                    try {
                        codedOutputStreamNewInstance.flush();
                    } catch (IOException unused2) {
                    } catch (Throwable th3) {
                        this.unknownFields = outputNewOutput.toByteString();
                        throw th3;
                    }
                    this.unknownFields = outputNewOutput.toByteString();
                    makeExtensionsImmutable();
                    return;
                }
            }
        }
    }

    public interface TypeAliasOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder {
    }

    public interface TypeOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder {
    }

    public static final class TypeParameter extends GeneratedMessageLite.ExtendableMessage<TypeParameter> implements TypeParameterOrBuilder {
        public static Parser<TypeParameter> PARSER = new AbstractParser<TypeParameter>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeParameter.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public TypeParameter parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new TypeParameter(codedInputStream, extensionRegistryLite);
            }
        };
        private static final TypeParameter defaultInstance;
        private int bitField0_;
        private int id_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private int name_;
        private boolean reified_;
        private final ByteString unknownFields;
        private int upperBoundIdMemoizedSerializedSize;
        private List<Integer> upperBoundId_;
        private List<Type> upperBound_;
        private Variance variance_;

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<TypeParameter, Builder> implements TypeParameterOrBuilder {
            private int bitField0_;
            private int id_;
            private int name_;
            private boolean reified_;
            private Variance variance_ = Variance.INV;
            private List<Type> upperBound_ = Collections.emptyList();
            private List<Integer> upperBoundId_ = Collections.emptyList();

            private Builder() {
                maybeForceBuilderInitialization();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            private void ensureUpperBoundIdIsMutable() {
                if ((this.bitField0_ & 32) != 32) {
                    this.upperBoundId_ = new ArrayList(this.upperBoundId_);
                    this.bitField0_ |= 32;
                }
            }

            private void ensureUpperBoundIsMutable() {
                if ((this.bitField0_ & 16) != 16) {
                    this.upperBound_ = new ArrayList(this.upperBound_);
                    this.bitField0_ |= 16;
                }
            }

            private void maybeForceBuilderInitialization() {
            }

            public TypeParameter buildPartial() {
                TypeParameter typeParameter = new TypeParameter(this);
                int i2 = this.bitField0_;
                int i3 = (i2 & 1) != 1 ? 0 : 1;
                typeParameter.id_ = this.id_;
                if ((i2 & 2) == 2) {
                    i3 |= 2;
                }
                typeParameter.name_ = this.name_;
                if ((i2 & 4) == 4) {
                    i3 |= 4;
                }
                typeParameter.reified_ = this.reified_;
                if ((i2 & 8) == 8) {
                    i3 |= 8;
                }
                typeParameter.variance_ = this.variance_;
                if ((this.bitField0_ & 16) == 16) {
                    this.upperBound_ = Collections.unmodifiableList(this.upperBound_);
                    this.bitField0_ &= -17;
                }
                typeParameter.upperBound_ = this.upperBound_;
                if ((this.bitField0_ & 32) == 32) {
                    this.upperBoundId_ = Collections.unmodifiableList(this.upperBoundId_);
                    this.bitField0_ &= -33;
                }
                typeParameter.upperBoundId_ = this.upperBoundId_;
                typeParameter.bitField0_ = i3;
                return typeParameter;
            }

            public Type getUpperBound(int i2) {
                return this.upperBound_.get(i2);
            }

            public int getUpperBoundCount() {
                return this.upperBound_.size();
            }

            public boolean hasId() {
                return (this.bitField0_ & 1) == 1;
            }

            public boolean hasName() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                if (!hasId() || !hasName()) {
                    return false;
                }
                for (int i2 = 0; i2 < getUpperBoundCount(); i2++) {
                    if (!getUpperBound(i2).isInitialized()) {
                        return false;
                    }
                }
                return extensionsAreInitialized();
            }

            public Builder setId(int i2) {
                this.bitField0_ |= 1;
                this.id_ = i2;
                return this;
            }

            public Builder setName(int i2) {
                this.bitField0_ |= 2;
                this.name_ = i2;
                return this;
            }

            public Builder setReified(boolean z2) {
                this.bitField0_ |= 4;
                this.reified_ = z2;
                return this;
            }

            public Builder setVariance(Variance variance) {
                variance.getClass();
                this.bitField0_ |= 8;
                this.variance_ = variance;
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public TypeParameter build() {
                TypeParameter typeParameterBuildPartial = buildPartial();
                if (typeParameterBuildPartial.isInitialized()) {
                    return typeParameterBuildPartial;
                }
                throw AbstractMessageLite.Builder.newUninitializedMessageException(typeParameterBuildPartial);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public TypeParameter getDefaultInstanceForType() {
                return TypeParameter.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(TypeParameter typeParameter) {
                if (typeParameter == TypeParameter.getDefaultInstance()) {
                    return this;
                }
                if (typeParameter.hasId()) {
                    setId(typeParameter.getId());
                }
                if (typeParameter.hasName()) {
                    setName(typeParameter.getName());
                }
                if (typeParameter.hasReified()) {
                    setReified(typeParameter.getReified());
                }
                if (typeParameter.hasVariance()) {
                    setVariance(typeParameter.getVariance());
                }
                if (!typeParameter.upperBound_.isEmpty()) {
                    if (this.upperBound_.isEmpty()) {
                        this.upperBound_ = typeParameter.upperBound_;
                        this.bitField0_ &= -17;
                    } else {
                        ensureUpperBoundIsMutable();
                        this.upperBound_.addAll(typeParameter.upperBound_);
                    }
                }
                if (!typeParameter.upperBoundId_.isEmpty()) {
                    if (this.upperBoundId_.isEmpty()) {
                        this.upperBoundId_ = typeParameter.upperBoundId_;
                        this.bitField0_ &= -33;
                    } else {
                        ensureUpperBoundIdIsMutable();
                        this.upperBoundId_.addAll(typeParameter.upperBoundId_);
                    }
                }
                mergeExtensionFields(typeParameter);
                setUnknownFields(getUnknownFields().concat(typeParameter.unknownFields));
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.ExtendableBuilder, kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo2084clone() {
                return create().mergeFrom(buildPartial());
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeParameter.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$TypeParameter> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeParameter.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$TypeParameter r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeParameter) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    if (r3 == 0) goto Le
                    r2.mergeFrom(r3)
                Le:
                    return r2
                Lf:
                    r3 = move-exception
                    goto L1b
                L11:
                    r3 = move-exception
                    kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$TypeParameter r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeParameter) r4     // Catch: java.lang.Throwable -> Lf
                    throw r3     // Catch: java.lang.Throwable -> L19
                L19:
                    r3 = move-exception
                    r0 = r4
                L1b:
                    if (r0 == 0) goto L20
                    r2.mergeFrom(r0)
                L20:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeParameter.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$TypeParameter$Builder");
            }
        }

        public enum Variance implements Internal.EnumLite {
            IN(0, 0),
            OUT(1, 1),
            INV(2, 2);

            private static Internal.EnumLiteMap<Variance> internalValueMap = new Internal.EnumLiteMap<Variance>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeParameter.Variance.1
                @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLiteMap
                public Variance findValueByNumber(int i2) {
                    return Variance.valueOf(i2);
                }
            };
            private final int value;

            Variance(int i2, int i3) {
                this.value = i3;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLite
            public final int getNumber() {
                return this.value;
            }

            public static Variance valueOf(int i2) {
                if (i2 == 0) {
                    return IN;
                }
                if (i2 == 1) {
                    return OUT;
                }
                if (i2 != 2) {
                    return null;
                }
                return INV;
            }
        }

        static {
            TypeParameter typeParameter = new TypeParameter(true);
            defaultInstance = typeParameter;
            typeParameter.initFields();
        }

        public static TypeParameter getDefaultInstance() {
            return defaultInstance;
        }

        private void initFields() {
            this.id_ = 0;
            this.name_ = 0;
            this.reified_ = false;
            this.variance_ = Variance.INV;
            this.upperBound_ = Collections.emptyList();
            this.upperBoundId_ = Collections.emptyList();
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public int getId() {
            return this.id_;
        }

        public int getName() {
            return this.name_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<TypeParameter> getParserForType() {
            return PARSER;
        }

        public boolean getReified() {
            return this.reified_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSerializedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeInt32Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt32Size(1, this.id_) + 0 : 0;
            if ((this.bitField0_ & 2) == 2) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(2, this.name_);
            }
            if ((this.bitField0_ & 4) == 4) {
                iComputeInt32Size += CodedOutputStream.computeBoolSize(3, this.reified_);
            }
            if ((this.bitField0_ & 8) == 8) {
                iComputeInt32Size += CodedOutputStream.computeEnumSize(4, this.variance_.getNumber());
            }
            for (int i3 = 0; i3 < this.upperBound_.size(); i3++) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(5, this.upperBound_.get(i3));
            }
            int iComputeInt32SizeNoTag = 0;
            for (int i4 = 0; i4 < this.upperBoundId_.size(); i4++) {
                iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(this.upperBoundId_.get(i4).intValue());
            }
            int iComputeInt32SizeNoTag2 = iComputeInt32Size + iComputeInt32SizeNoTag;
            if (!getUpperBoundIdList().isEmpty()) {
                iComputeInt32SizeNoTag2 = iComputeInt32SizeNoTag2 + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag);
            }
            this.upperBoundIdMemoizedSerializedSize = iComputeInt32SizeNoTag;
            int iExtensionsSerializedSize = iComputeInt32SizeNoTag2 + extensionsSerializedSize() + this.unknownFields.size();
            this.memoizedSerializedSize = iExtensionsSerializedSize;
            return iExtensionsSerializedSize;
        }

        public Type getUpperBound(int i2) {
            return this.upperBound_.get(i2);
        }

        public int getUpperBoundCount() {
            return this.upperBound_.size();
        }

        public List<Integer> getUpperBoundIdList() {
            return this.upperBoundId_;
        }

        public List<Type> getUpperBoundList() {
            return this.upperBound_;
        }

        public Variance getVariance() {
            return this.variance_;
        }

        public boolean hasId() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean hasName() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean hasReified() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean hasVariance() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b3 = this.memoizedIsInitialized;
            if (b3 == 1) {
                return true;
            }
            if (b3 == 0) {
                return false;
            }
            if (!hasId()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            if (!hasName()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            for (int i2 = 0; i2 < getUpperBoundCount(); i2++) {
                if (!getUpperBound(i2).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            GeneratedMessageLite.ExtendableMessage<MessageType>.ExtensionWriter extensionWriterNewExtensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeInt32(1, this.id_);
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeInt32(2, this.name_);
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeBool(3, this.reified_);
            }
            if ((this.bitField0_ & 8) == 8) {
                codedOutputStream.writeEnum(4, this.variance_.getNumber());
            }
            for (int i2 = 0; i2 < this.upperBound_.size(); i2++) {
                codedOutputStream.writeMessage(5, this.upperBound_.get(i2));
            }
            if (getUpperBoundIdList().size() > 0) {
                codedOutputStream.writeRawVarint32(50);
                codedOutputStream.writeRawVarint32(this.upperBoundIdMemoizedSerializedSize);
            }
            for (int i3 = 0; i3 < this.upperBoundId_.size(); i3++) {
                codedOutputStream.writeInt32NoTag(this.upperBoundId_.get(i3).intValue());
            }
            extensionWriterNewExtensionWriter.writeUntil(1000, codedOutputStream);
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        public static Builder newBuilder(TypeParameter typeParameter) {
            return newBuilder().mergeFrom(typeParameter);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public TypeParameter getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        private TypeParameter(GeneratedMessageLite.ExtendableBuilder<TypeParameter, ?> extendableBuilder) {
            super(extendableBuilder);
            this.upperBoundIdMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = extendableBuilder.getUnknownFields();
        }

        private TypeParameter(boolean z2) {
            this.upperBoundIdMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private TypeParameter(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.upperBoundIdMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z2 = false;
            int i2 = 0;
            while (!z2) {
                try {
                    try {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 8) {
                                    this.bitField0_ |= 1;
                                    this.id_ = codedInputStream.readInt32();
                                } else if (tag == 16) {
                                    this.bitField0_ |= 2;
                                    this.name_ = codedInputStream.readInt32();
                                } else if (tag == 24) {
                                    this.bitField0_ |= 4;
                                    this.reified_ = codedInputStream.readBool();
                                } else if (tag == 32) {
                                    int i3 = codedInputStream.readEnum();
                                    Variance varianceValueOf = Variance.valueOf(i3);
                                    if (varianceValueOf == null) {
                                        codedOutputStreamNewInstance.writeRawVarint32(tag);
                                        codedOutputStreamNewInstance.writeRawVarint32(i3);
                                    } else {
                                        this.bitField0_ |= 8;
                                        this.variance_ = varianceValueOf;
                                    }
                                } else if (tag == 42) {
                                    if ((i2 & 16) != 16) {
                                        this.upperBound_ = new ArrayList();
                                        i2 |= 16;
                                    }
                                    this.upperBound_.add(codedInputStream.readMessage(Type.PARSER, extensionRegistryLite));
                                } else if (tag == 48) {
                                    if ((i2 & 32) != 32) {
                                        this.upperBoundId_ = new ArrayList();
                                        i2 |= 32;
                                    }
                                    this.upperBoundId_.add(Integer.valueOf(codedInputStream.readInt32()));
                                } else if (tag != 50) {
                                    if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                    if ((i2 & 32) != 32 && codedInputStream.getBytesUntilLimit() > 0) {
                                        this.upperBoundId_ = new ArrayList();
                                        i2 |= 32;
                                    }
                                    while (codedInputStream.getBytesUntilLimit() > 0) {
                                        this.upperBoundId_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    }
                                    codedInputStream.popLimit(iPushLimit);
                                }
                            }
                            z2 = true;
                        } catch (InvalidProtocolBufferException e2) {
                            throw e2.setUnfinishedMessage(this);
                        }
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if ((i2 & 16) == 16) {
                        this.upperBound_ = Collections.unmodifiableList(this.upperBound_);
                    }
                    if ((i2 & 32) == 32) {
                        this.upperBoundId_ = Collections.unmodifiableList(this.upperBoundId_);
                    }
                    try {
                        codedOutputStreamNewInstance.flush();
                    } catch (IOException unused) {
                    } catch (Throwable th2) {
                        this.unknownFields = outputNewOutput.toByteString();
                        throw th2;
                    }
                    this.unknownFields = outputNewOutput.toByteString();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((i2 & 16) == 16) {
                this.upperBound_ = Collections.unmodifiableList(this.upperBound_);
            }
            if ((i2 & 32) == 32) {
                this.upperBoundId_ = Collections.unmodifiableList(this.upperBoundId_);
            }
            try {
                codedOutputStreamNewInstance.flush();
            } catch (IOException unused2) {
            } catch (Throwable th3) {
                this.unknownFields = outputNewOutput.toByteString();
                throw th3;
            }
            this.unknownFields = outputNewOutput.toByteString();
            makeExtensionsImmutable();
        }
    }

    public interface TypeParameterOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder {
    }

    public static final class TypeTable extends GeneratedMessageLite implements TypeTableOrBuilder {
        public static Parser<TypeTable> PARSER = new AbstractParser<TypeTable>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeTable.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public TypeTable parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new TypeTable(codedInputStream, extensionRegistryLite);
            }
        };
        private static final TypeTable defaultInstance;
        private int bitField0_;
        private int firstNullable_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<Type> type_;
        private final ByteString unknownFields;

        public static final class Builder extends GeneratedMessageLite.Builder<TypeTable, Builder> implements TypeTableOrBuilder {
            private int bitField0_;
            private List<Type> type_ = Collections.emptyList();
            private int firstNullable_ = -1;

            private Builder() {
                maybeForceBuilderInitialization();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            private void ensureTypeIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.type_ = new ArrayList(this.type_);
                    this.bitField0_ |= 1;
                }
            }

            private void maybeForceBuilderInitialization() {
            }

            public TypeTable buildPartial() {
                TypeTable typeTable = new TypeTable(this);
                int i2 = this.bitField0_;
                if ((i2 & 1) == 1) {
                    this.type_ = Collections.unmodifiableList(this.type_);
                    this.bitField0_ &= -2;
                }
                typeTable.type_ = this.type_;
                int i3 = (i2 & 2) != 2 ? 0 : 1;
                typeTable.firstNullable_ = this.firstNullable_;
                typeTable.bitField0_ = i3;
                return typeTable;
            }

            public Type getType(int i2) {
                return this.type_.get(i2);
            }

            public int getTypeCount() {
                return this.type_.size();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i2 = 0; i2 < getTypeCount(); i2++) {
                    if (!getType(i2).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            public Builder setFirstNullable(int i2) {
                this.bitField0_ |= 2;
                this.firstNullable_ = i2;
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public TypeTable build() {
                TypeTable typeTableBuildPartial = buildPartial();
                if (typeTableBuildPartial.isInitialized()) {
                    return typeTableBuildPartial;
                }
                throw AbstractMessageLite.Builder.newUninitializedMessageException(typeTableBuildPartial);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public TypeTable getDefaultInstanceForType() {
                return TypeTable.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo2084clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(TypeTable typeTable) {
                if (typeTable == TypeTable.getDefaultInstance()) {
                    return this;
                }
                if (!typeTable.type_.isEmpty()) {
                    if (this.type_.isEmpty()) {
                        this.type_ = typeTable.type_;
                        this.bitField0_ &= -2;
                    } else {
                        ensureTypeIsMutable();
                        this.type_.addAll(typeTable.type_);
                    }
                }
                if (typeTable.hasFirstNullable()) {
                    setFirstNullable(typeTable.getFirstNullable());
                }
                setUnknownFields(getUnknownFields().concat(typeTable.unknownFields));
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeTable.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$TypeTable> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeTable.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$TypeTable r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeTable) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    if (r3 == 0) goto Le
                    r2.mergeFrom(r3)
                Le:
                    return r2
                Lf:
                    r3 = move-exception
                    goto L1b
                L11:
                    r3 = move-exception
                    kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$TypeTable r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeTable) r4     // Catch: java.lang.Throwable -> Lf
                    throw r3     // Catch: java.lang.Throwable -> L19
                L19:
                    r3 = move-exception
                    r0 = r4
                L1b:
                    if (r0 == 0) goto L20
                    r2.mergeFrom(r0)
                L20:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeTable.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$TypeTable$Builder");
            }
        }

        static {
            TypeTable typeTable = new TypeTable(true);
            defaultInstance = typeTable;
            typeTable.initFields();
        }

        public static TypeTable getDefaultInstance() {
            return defaultInstance;
        }

        private void initFields() {
            this.type_ = Collections.emptyList();
            this.firstNullable_ = -1;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public int getFirstNullable() {
            return this.firstNullable_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<TypeTable> getParserForType() {
            return PARSER;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSerializedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeInt32Size = 0;
            for (int i3 = 0; i3 < this.type_.size(); i3++) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(1, this.type_.get(i3));
            }
            if ((this.bitField0_ & 1) == 1) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(2, this.firstNullable_);
            }
            int size = iComputeInt32Size + this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
        }

        public Type getType(int i2) {
            return this.type_.get(i2);
        }

        public int getTypeCount() {
            return this.type_.size();
        }

        public List<Type> getTypeList() {
            return this.type_;
        }

        public boolean hasFirstNullable() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b3 = this.memoizedIsInitialized;
            if (b3 == 1) {
                return true;
            }
            if (b3 == 0) {
                return false;
            }
            for (int i2 = 0; i2 < getTypeCount(); i2++) {
                if (!getType(i2).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            for (int i2 = 0; i2 < this.type_.size(); i2++) {
                codedOutputStream.writeMessage(1, this.type_.get(i2));
            }
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeInt32(2, this.firstNullable_);
            }
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        public static Builder newBuilder(TypeTable typeTable) {
            return newBuilder().mergeFrom(typeTable);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public TypeTable getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        private TypeTable(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private TypeTable(boolean z2) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private TypeTable(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z2 = false;
            boolean z3 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 10) {
                                if (!(z3 & true)) {
                                    this.type_ = new ArrayList();
                                    z3 |= true;
                                }
                                this.type_.add(codedInputStream.readMessage(Type.PARSER, extensionRegistryLite));
                            } else if (tag != 16) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                this.bitField0_ |= 1;
                                this.firstNullable_ = codedInputStream.readInt32();
                            }
                        }
                        z2 = true;
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if (z3 & true) {
                        this.type_ = Collections.unmodifiableList(this.type_);
                    }
                    try {
                        codedOutputStreamNewInstance.flush();
                    } catch (IOException unused) {
                    } catch (Throwable th2) {
                        this.unknownFields = outputNewOutput.toByteString();
                        throw th2;
                    }
                    this.unknownFields = outputNewOutput.toByteString();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if (z3 & true) {
                this.type_ = Collections.unmodifiableList(this.type_);
            }
            try {
                codedOutputStreamNewInstance.flush();
            } catch (IOException unused2) {
            } catch (Throwable th3) {
                this.unknownFields = outputNewOutput.toByteString();
                throw th3;
            }
            this.unknownFields = outputNewOutput.toByteString();
            makeExtensionsImmutable();
        }
    }

    public interface TypeTableOrBuilder extends MessageLiteOrBuilder {
    }

    public static final class ValueParameter extends GeneratedMessageLite.ExtendableMessage<ValueParameter> implements ValueParameterOrBuilder {
        public static Parser<ValueParameter> PARSER = new AbstractParser<ValueParameter>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.ValueParameter.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public ValueParameter parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new ValueParameter(codedInputStream, extensionRegistryLite);
            }
        };
        private static final ValueParameter defaultInstance;
        private int bitField0_;
        private int flags_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private int name_;
        private int typeId_;
        private Type type_;
        private final ByteString unknownFields;
        private int varargElementTypeId_;
        private Type varargElementType_;

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<ValueParameter, Builder> implements ValueParameterOrBuilder {
            private int bitField0_;
            private int flags_;
            private int name_;
            private int typeId_;
            private int varargElementTypeId_;
            private Type type_ = Type.getDefaultInstance();
            private Type varargElementType_ = Type.getDefaultInstance();

            private Builder() {
                maybeForceBuilderInitialization();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            private void maybeForceBuilderInitialization() {
            }

            public ValueParameter buildPartial() {
                ValueParameter valueParameter = new ValueParameter(this);
                int i2 = this.bitField0_;
                int i3 = (i2 & 1) != 1 ? 0 : 1;
                valueParameter.flags_ = this.flags_;
                if ((i2 & 2) == 2) {
                    i3 |= 2;
                }
                valueParameter.name_ = this.name_;
                if ((i2 & 4) == 4) {
                    i3 |= 4;
                }
                valueParameter.type_ = this.type_;
                if ((i2 & 8) == 8) {
                    i3 |= 8;
                }
                valueParameter.typeId_ = this.typeId_;
                if ((i2 & 16) == 16) {
                    i3 |= 16;
                }
                valueParameter.varargElementType_ = this.varargElementType_;
                if ((i2 & 32) == 32) {
                    i3 |= 32;
                }
                valueParameter.varargElementTypeId_ = this.varargElementTypeId_;
                valueParameter.bitField0_ = i3;
                return valueParameter;
            }

            public Type getType() {
                return this.type_;
            }

            public Type getVarargElementType() {
                return this.varargElementType_;
            }

            public boolean hasName() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean hasType() {
                return (this.bitField0_ & 4) == 4;
            }

            public boolean hasVarargElementType() {
                return (this.bitField0_ & 16) == 16;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                if (!hasName()) {
                    return false;
                }
                if (!hasType() || getType().isInitialized()) {
                    return (!hasVarargElementType() || getVarargElementType().isInitialized()) && extensionsAreInitialized();
                }
                return false;
            }

            public Builder mergeType(Type type) {
                if ((this.bitField0_ & 4) != 4 || this.type_ == Type.getDefaultInstance()) {
                    this.type_ = type;
                } else {
                    this.type_ = Type.newBuilder(this.type_).mergeFrom(type).buildPartial();
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder mergeVarargElementType(Type type) {
                if ((this.bitField0_ & 16) != 16 || this.varargElementType_ == Type.getDefaultInstance()) {
                    this.varargElementType_ = type;
                } else {
                    this.varargElementType_ = Type.newBuilder(this.varargElementType_).mergeFrom(type).buildPartial();
                }
                this.bitField0_ |= 16;
                return this;
            }

            public Builder setFlags(int i2) {
                this.bitField0_ |= 1;
                this.flags_ = i2;
                return this;
            }

            public Builder setName(int i2) {
                this.bitField0_ |= 2;
                this.name_ = i2;
                return this;
            }

            public Builder setTypeId(int i2) {
                this.bitField0_ |= 8;
                this.typeId_ = i2;
                return this;
            }

            public Builder setVarargElementTypeId(int i2) {
                this.bitField0_ |= 32;
                this.varargElementTypeId_ = i2;
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public ValueParameter build() {
                ValueParameter valueParameterBuildPartial = buildPartial();
                if (valueParameterBuildPartial.isInitialized()) {
                    return valueParameterBuildPartial;
                }
                throw AbstractMessageLite.Builder.newUninitializedMessageException(valueParameterBuildPartial);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public ValueParameter getDefaultInstanceForType() {
                return ValueParameter.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(ValueParameter valueParameter) {
                if (valueParameter == ValueParameter.getDefaultInstance()) {
                    return this;
                }
                if (valueParameter.hasFlags()) {
                    setFlags(valueParameter.getFlags());
                }
                if (valueParameter.hasName()) {
                    setName(valueParameter.getName());
                }
                if (valueParameter.hasType()) {
                    mergeType(valueParameter.getType());
                }
                if (valueParameter.hasTypeId()) {
                    setTypeId(valueParameter.getTypeId());
                }
                if (valueParameter.hasVarargElementType()) {
                    mergeVarargElementType(valueParameter.getVarargElementType());
                }
                if (valueParameter.hasVarargElementTypeId()) {
                    setVarargElementTypeId(valueParameter.getVarargElementTypeId());
                }
                mergeExtensionFields(valueParameter);
                setUnknownFields(getUnknownFields().concat(valueParameter.unknownFields));
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.ExtendableBuilder, kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo2084clone() {
                return create().mergeFrom(buildPartial());
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.ValueParameter.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$ValueParameter> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.ValueParameter.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$ValueParameter r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.ValueParameter) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    if (r3 == 0) goto Le
                    r2.mergeFrom(r3)
                Le:
                    return r2
                Lf:
                    r3 = move-exception
                    goto L1b
                L11:
                    r3 = move-exception
                    kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$ValueParameter r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.ValueParameter) r4     // Catch: java.lang.Throwable -> Lf
                    throw r3     // Catch: java.lang.Throwable -> L19
                L19:
                    r3 = move-exception
                    r0 = r4
                L1b:
                    if (r0 == 0) goto L20
                    r2.mergeFrom(r0)
                L20:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.ValueParameter.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$ValueParameter$Builder");
            }
        }

        static {
            ValueParameter valueParameter = new ValueParameter(true);
            defaultInstance = valueParameter;
            valueParameter.initFields();
        }

        public static ValueParameter getDefaultInstance() {
            return defaultInstance;
        }

        private void initFields() {
            this.flags_ = 0;
            this.name_ = 0;
            this.type_ = Type.getDefaultInstance();
            this.typeId_ = 0;
            this.varargElementType_ = Type.getDefaultInstance();
            this.varargElementTypeId_ = 0;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public int getFlags() {
            return this.flags_;
        }

        public int getName() {
            return this.name_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<ValueParameter> getParserForType() {
            return PARSER;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSerializedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeInt32Size = (this.bitField0_ & 1) == 1 ? 0 + CodedOutputStream.computeInt32Size(1, this.flags_) : 0;
            if ((this.bitField0_ & 2) == 2) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(2, this.name_);
            }
            if ((this.bitField0_ & 4) == 4) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(3, this.type_);
            }
            if ((this.bitField0_ & 16) == 16) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(4, this.varargElementType_);
            }
            if ((this.bitField0_ & 8) == 8) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(5, this.typeId_);
            }
            if ((this.bitField0_ & 32) == 32) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(6, this.varargElementTypeId_);
            }
            int iExtensionsSerializedSize = iComputeInt32Size + extensionsSerializedSize() + this.unknownFields.size();
            this.memoizedSerializedSize = iExtensionsSerializedSize;
            return iExtensionsSerializedSize;
        }

        public Type getType() {
            return this.type_;
        }

        public int getTypeId() {
            return this.typeId_;
        }

        public Type getVarargElementType() {
            return this.varargElementType_;
        }

        public int getVarargElementTypeId() {
            return this.varargElementTypeId_;
        }

        public boolean hasFlags() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean hasName() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean hasType() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean hasTypeId() {
            return (this.bitField0_ & 8) == 8;
        }

        public boolean hasVarargElementType() {
            return (this.bitField0_ & 16) == 16;
        }

        public boolean hasVarargElementTypeId() {
            return (this.bitField0_ & 32) == 32;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b3 = this.memoizedIsInitialized;
            if (b3 == 1) {
                return true;
            }
            if (b3 == 0) {
                return false;
            }
            if (!hasName()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            if (hasType() && !getType().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            if (hasVarargElementType() && !getVarargElementType().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            if (extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            GeneratedMessageLite.ExtendableMessage<MessageType>.ExtensionWriter extensionWriterNewExtensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeInt32(1, this.flags_);
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeInt32(2, this.name_);
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeMessage(3, this.type_);
            }
            if ((this.bitField0_ & 16) == 16) {
                codedOutputStream.writeMessage(4, this.varargElementType_);
            }
            if ((this.bitField0_ & 8) == 8) {
                codedOutputStream.writeInt32(5, this.typeId_);
            }
            if ((this.bitField0_ & 32) == 32) {
                codedOutputStream.writeInt32(6, this.varargElementTypeId_);
            }
            extensionWriterNewExtensionWriter.writeUntil(200, codedOutputStream);
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        public static Builder newBuilder(ValueParameter valueParameter) {
            return newBuilder().mergeFrom(valueParameter);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public ValueParameter getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        private ValueParameter(GeneratedMessageLite.ExtendableBuilder<ValueParameter, ?> extendableBuilder) {
            super(extendableBuilder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = extendableBuilder.getUnknownFields();
        }

        private ValueParameter(boolean z2) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        private ValueParameter(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            Type.Builder builder;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z2 = false;
            while (!z2) {
                try {
                    try {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 8) {
                                    this.bitField0_ |= 1;
                                    this.flags_ = codedInputStream.readInt32();
                                } else if (tag != 16) {
                                    if (tag == 26) {
                                        builder = (this.bitField0_ & 4) == 4 ? this.type_.toBuilder() : null;
                                        Type type = (Type) codedInputStream.readMessage(Type.PARSER, extensionRegistryLite);
                                        this.type_ = type;
                                        if (builder != null) {
                                            builder.mergeFrom(type);
                                            this.type_ = builder.buildPartial();
                                        }
                                        this.bitField0_ |= 4;
                                    } else if (tag == 34) {
                                        builder = (this.bitField0_ & 16) == 16 ? this.varargElementType_.toBuilder() : null;
                                        Type type2 = (Type) codedInputStream.readMessage(Type.PARSER, extensionRegistryLite);
                                        this.varargElementType_ = type2;
                                        if (builder != null) {
                                            builder.mergeFrom(type2);
                                            this.varargElementType_ = builder.buildPartial();
                                        }
                                        this.bitField0_ |= 16;
                                    } else if (tag == 40) {
                                        this.bitField0_ |= 8;
                                        this.typeId_ = codedInputStream.readInt32();
                                    } else if (tag != 48) {
                                        if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                        }
                                    } else {
                                        this.bitField0_ |= 32;
                                        this.varargElementTypeId_ = codedInputStream.readInt32();
                                    }
                                } else {
                                    this.bitField0_ |= 2;
                                    this.name_ = codedInputStream.readInt32();
                                }
                            }
                            z2 = true;
                        } catch (InvalidProtocolBufferException e2) {
                            throw e2.setUnfinishedMessage(this);
                        }
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    try {
                        codedOutputStreamNewInstance.flush();
                    } catch (IOException unused) {
                    } catch (Throwable th2) {
                        this.unknownFields = outputNewOutput.toByteString();
                        throw th2;
                    }
                    this.unknownFields = outputNewOutput.toByteString();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            try {
                codedOutputStreamNewInstance.flush();
            } catch (IOException unused2) {
            } catch (Throwable th3) {
                this.unknownFields = outputNewOutput.toByteString();
                throw th3;
            }
            this.unknownFields = outputNewOutput.toByteString();
            makeExtensionsImmutable();
        }
    }

    public interface ValueParameterOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder {
    }

    public static final class VersionRequirement extends GeneratedMessageLite implements VersionRequirementOrBuilder {
        public static Parser<VersionRequirement> PARSER = new AbstractParser<VersionRequirement>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.VersionRequirement.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public VersionRequirement parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new VersionRequirement(codedInputStream, extensionRegistryLite);
            }
        };
        private static final VersionRequirement defaultInstance;
        private int bitField0_;
        private int errorCode_;
        private Level level_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private int message_;
        private final ByteString unknownFields;
        private int versionFull_;
        private VersionKind versionKind_;
        private int version_;

        public static final class Builder extends GeneratedMessageLite.Builder<VersionRequirement, Builder> implements VersionRequirementOrBuilder {
            private int bitField0_;
            private int errorCode_;
            private int message_;
            private int versionFull_;
            private int version_;
            private Level level_ = Level.ERROR;
            private VersionKind versionKind_ = VersionKind.LANGUAGE_VERSION;

            private Builder() {
                maybeForceBuilderInitialization();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            private void maybeForceBuilderInitialization() {
            }

            public VersionRequirement buildPartial() {
                VersionRequirement versionRequirement = new VersionRequirement(this);
                int i2 = this.bitField0_;
                int i3 = (i2 & 1) != 1 ? 0 : 1;
                versionRequirement.version_ = this.version_;
                if ((i2 & 2) == 2) {
                    i3 |= 2;
                }
                versionRequirement.versionFull_ = this.versionFull_;
                if ((i2 & 4) == 4) {
                    i3 |= 4;
                }
                versionRequirement.level_ = this.level_;
                if ((i2 & 8) == 8) {
                    i3 |= 8;
                }
                versionRequirement.errorCode_ = this.errorCode_;
                if ((i2 & 16) == 16) {
                    i3 |= 16;
                }
                versionRequirement.message_ = this.message_;
                if ((i2 & 32) == 32) {
                    i3 |= 32;
                }
                versionRequirement.versionKind_ = this.versionKind_;
                versionRequirement.bitField0_ = i3;
                return versionRequirement;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return true;
            }

            public Builder setErrorCode(int i2) {
                this.bitField0_ |= 8;
                this.errorCode_ = i2;
                return this;
            }

            public Builder setLevel(Level level) {
                level.getClass();
                this.bitField0_ |= 4;
                this.level_ = level;
                return this;
            }

            public Builder setMessage(int i2) {
                this.bitField0_ |= 16;
                this.message_ = i2;
                return this;
            }

            public Builder setVersion(int i2) {
                this.bitField0_ |= 1;
                this.version_ = i2;
                return this;
            }

            public Builder setVersionFull(int i2) {
                this.bitField0_ |= 2;
                this.versionFull_ = i2;
                return this;
            }

            public Builder setVersionKind(VersionKind versionKind) {
                versionKind.getClass();
                this.bitField0_ |= 32;
                this.versionKind_ = versionKind;
                return this;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public VersionRequirement build() {
                VersionRequirement versionRequirementBuildPartial = buildPartial();
                if (versionRequirementBuildPartial.isInitialized()) {
                    return versionRequirementBuildPartial;
                }
                throw AbstractMessageLite.Builder.newUninitializedMessageException(versionRequirementBuildPartial);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public VersionRequirement getDefaultInstanceForType() {
                return VersionRequirement.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo2084clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(VersionRequirement versionRequirement) {
                if (versionRequirement == VersionRequirement.getDefaultInstance()) {
                    return this;
                }
                if (versionRequirement.hasVersion()) {
                    setVersion(versionRequirement.getVersion());
                }
                if (versionRequirement.hasVersionFull()) {
                    setVersionFull(versionRequirement.getVersionFull());
                }
                if (versionRequirement.hasLevel()) {
                    setLevel(versionRequirement.getLevel());
                }
                if (versionRequirement.hasErrorCode()) {
                    setErrorCode(versionRequirement.getErrorCode());
                }
                if (versionRequirement.hasMessage()) {
                    setMessage(versionRequirement.getMessage());
                }
                if (versionRequirement.hasVersionKind()) {
                    setVersionKind(versionRequirement.getVersionKind());
                }
                setUnknownFields(getUnknownFields().concat(versionRequirement.unknownFields));
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.VersionRequirement.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$VersionRequirement> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.VersionRequirement.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$VersionRequirement r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.VersionRequirement) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    if (r3 == 0) goto Le
                    r2.mergeFrom(r3)
                Le:
                    return r2
                Lf:
                    r3 = move-exception
                    goto L1b
                L11:
                    r3 = move-exception
                    kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$VersionRequirement r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.VersionRequirement) r4     // Catch: java.lang.Throwable -> Lf
                    throw r3     // Catch: java.lang.Throwable -> L19
                L19:
                    r3 = move-exception
                    r0 = r4
                L1b:
                    if (r0 == 0) goto L20
                    r2.mergeFrom(r0)
                L20:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.VersionRequirement.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$VersionRequirement$Builder");
            }
        }

        public enum Level implements Internal.EnumLite {
            WARNING(0, 0),
            ERROR(1, 1),
            HIDDEN(2, 2);

            private static Internal.EnumLiteMap<Level> internalValueMap = new Internal.EnumLiteMap<Level>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.VersionRequirement.Level.1
                @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLiteMap
                public Level findValueByNumber(int i2) {
                    return Level.valueOf(i2);
                }
            };
            private final int value;

            Level(int i2, int i3) {
                this.value = i3;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLite
            public final int getNumber() {
                return this.value;
            }

            public static Level valueOf(int i2) {
                if (i2 == 0) {
                    return WARNING;
                }
                if (i2 == 1) {
                    return ERROR;
                }
                if (i2 != 2) {
                    return null;
                }
                return HIDDEN;
            }
        }

        public enum VersionKind implements Internal.EnumLite {
            LANGUAGE_VERSION(0, 0),
            COMPILER_VERSION(1, 1),
            API_VERSION(2, 2);

            private static Internal.EnumLiteMap<VersionKind> internalValueMap = new Internal.EnumLiteMap<VersionKind>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.VersionRequirement.VersionKind.1
                @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLiteMap
                public VersionKind findValueByNumber(int i2) {
                    return VersionKind.valueOf(i2);
                }
            };
            private final int value;

            VersionKind(int i2, int i3) {
                this.value = i3;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLite
            public final int getNumber() {
                return this.value;
            }

            public static VersionKind valueOf(int i2) {
                if (i2 == 0) {
                    return LANGUAGE_VERSION;
                }
                if (i2 == 1) {
                    return COMPILER_VERSION;
                }
                if (i2 != 2) {
                    return null;
                }
                return API_VERSION;
            }
        }

        static {
            VersionRequirement versionRequirement = new VersionRequirement(true);
            defaultInstance = versionRequirement;
            versionRequirement.initFields();
        }

        public static VersionRequirement getDefaultInstance() {
            return defaultInstance;
        }

        private void initFields() {
            this.version_ = 0;
            this.versionFull_ = 0;
            this.level_ = Level.ERROR;
            this.errorCode_ = 0;
            this.message_ = 0;
            this.versionKind_ = VersionKind.LANGUAGE_VERSION;
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public int getErrorCode() {
            return this.errorCode_;
        }

        public Level getLevel() {
            return this.level_;
        }

        public int getMessage() {
            return this.message_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<VersionRequirement> getParserForType() {
            return PARSER;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSerializedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeInt32Size = (this.bitField0_ & 1) == 1 ? 0 + CodedOutputStream.computeInt32Size(1, this.version_) : 0;
            if ((this.bitField0_ & 2) == 2) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(2, this.versionFull_);
            }
            if ((this.bitField0_ & 4) == 4) {
                iComputeInt32Size += CodedOutputStream.computeEnumSize(3, this.level_.getNumber());
            }
            if ((this.bitField0_ & 8) == 8) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(4, this.errorCode_);
            }
            if ((this.bitField0_ & 16) == 16) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(5, this.message_);
            }
            if ((this.bitField0_ & 32) == 32) {
                iComputeInt32Size += CodedOutputStream.computeEnumSize(6, this.versionKind_.getNumber());
            }
            int size = iComputeInt32Size + this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
        }

        public int getVersion() {
            return this.version_;
        }

        public int getVersionFull() {
            return this.versionFull_;
        }

        public VersionKind getVersionKind() {
            return this.versionKind_;
        }

        public boolean hasErrorCode() {
            return (this.bitField0_ & 8) == 8;
        }

        public boolean hasLevel() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean hasMessage() {
            return (this.bitField0_ & 16) == 16;
        }

        public boolean hasVersion() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean hasVersionFull() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean hasVersionKind() {
            return (this.bitField0_ & 32) == 32;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b3 = this.memoizedIsInitialized;
            if (b3 == 1) {
                return true;
            }
            if (b3 == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeInt32(1, this.version_);
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeInt32(2, this.versionFull_);
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeEnum(3, this.level_.getNumber());
            }
            if ((this.bitField0_ & 8) == 8) {
                codedOutputStream.writeInt32(4, this.errorCode_);
            }
            if ((this.bitField0_ & 16) == 16) {
                codedOutputStream.writeInt32(5, this.message_);
            }
            if ((this.bitField0_ & 32) == 32) {
                codedOutputStream.writeEnum(6, this.versionKind_.getNumber());
            }
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        public static Builder newBuilder(VersionRequirement versionRequirement) {
            return newBuilder().mergeFrom(versionRequirement);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public VersionRequirement getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        private VersionRequirement(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private VersionRequirement(boolean z2) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        private VersionRequirement(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z2 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 8) {
                                this.bitField0_ |= 1;
                                this.version_ = codedInputStream.readInt32();
                            } else if (tag == 16) {
                                this.bitField0_ |= 2;
                                this.versionFull_ = codedInputStream.readInt32();
                            } else if (tag == 24) {
                                int i2 = codedInputStream.readEnum();
                                Level levelValueOf = Level.valueOf(i2);
                                if (levelValueOf == null) {
                                    codedOutputStreamNewInstance.writeRawVarint32(tag);
                                    codedOutputStreamNewInstance.writeRawVarint32(i2);
                                } else {
                                    this.bitField0_ |= 4;
                                    this.level_ = levelValueOf;
                                }
                            } else if (tag == 32) {
                                this.bitField0_ |= 8;
                                this.errorCode_ = codedInputStream.readInt32();
                            } else if (tag == 40) {
                                this.bitField0_ |= 16;
                                this.message_ = codedInputStream.readInt32();
                            } else if (tag != 48) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                int i3 = codedInputStream.readEnum();
                                VersionKind versionKindValueOf = VersionKind.valueOf(i3);
                                if (versionKindValueOf == null) {
                                    codedOutputStreamNewInstance.writeRawVarint32(tag);
                                    codedOutputStreamNewInstance.writeRawVarint32(i3);
                                } else {
                                    this.bitField0_ |= 32;
                                    this.versionKind_ = versionKindValueOf;
                                }
                            }
                        }
                        z2 = true;
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    try {
                        codedOutputStreamNewInstance.flush();
                    } catch (IOException unused) {
                    } catch (Throwable th2) {
                        this.unknownFields = outputNewOutput.toByteString();
                        throw th2;
                    }
                    this.unknownFields = outputNewOutput.toByteString();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            try {
                codedOutputStreamNewInstance.flush();
            } catch (IOException unused2) {
            } catch (Throwable th3) {
                this.unknownFields = outputNewOutput.toByteString();
                throw th3;
            }
            this.unknownFields = outputNewOutput.toByteString();
            makeExtensionsImmutable();
        }
    }

    public interface VersionRequirementOrBuilder extends MessageLiteOrBuilder {
    }

    public static final class VersionRequirementTable extends GeneratedMessageLite implements VersionRequirementTableOrBuilder {
        public static Parser<VersionRequirementTable> PARSER = new AbstractParser<VersionRequirementTable>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.VersionRequirementTable.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Parser
            public VersionRequirementTable parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new VersionRequirementTable(codedInputStream, extensionRegistryLite);
            }
        };
        private static final VersionRequirementTable defaultInstance;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<VersionRequirement> requirement_;
        private final ByteString unknownFields;

        public static final class Builder extends GeneratedMessageLite.Builder<VersionRequirementTable, Builder> implements VersionRequirementTableOrBuilder {
            private int bitField0_;
            private List<VersionRequirement> requirement_ = Collections.emptyList();

            private Builder() {
                maybeForceBuilderInitialization();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            private void ensureRequirementIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.requirement_ = new ArrayList(this.requirement_);
                    this.bitField0_ |= 1;
                }
            }

            private void maybeForceBuilderInitialization() {
            }

            public VersionRequirementTable buildPartial() {
                VersionRequirementTable versionRequirementTable = new VersionRequirementTable(this);
                if ((this.bitField0_ & 1) == 1) {
                    this.requirement_ = Collections.unmodifiableList(this.requirement_);
                    this.bitField0_ &= -2;
                }
                versionRequirementTable.requirement_ = this.requirement_;
                return versionRequirementTable;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return true;
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            public VersionRequirementTable build() {
                VersionRequirementTable versionRequirementTableBuildPartial = buildPartial();
                if (versionRequirementTableBuildPartial.isInitialized()) {
                    return versionRequirementTableBuildPartial;
                }
                throw AbstractMessageLite.Builder.newUninitializedMessageException(versionRequirementTableBuildPartial);
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
            public VersionRequirementTable getDefaultInstanceForType() {
                return VersionRequirementTable.getDefaultInstance();
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo2084clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(VersionRequirementTable versionRequirementTable) {
                if (versionRequirementTable == VersionRequirementTable.getDefaultInstance()) {
                    return this;
                }
                if (!versionRequirementTable.requirement_.isEmpty()) {
                    if (this.requirement_.isEmpty()) {
                        this.requirement_ = versionRequirementTable.requirement_;
                        this.bitField0_ &= -2;
                    } else {
                        ensureRequirementIsMutable();
                        this.requirement_.addAll(versionRequirementTable.requirement_);
                    }
                }
                setUnknownFields(getUnknownFields().concat(versionRequirementTable.unknownFields));
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite.Builder, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.VersionRequirementTable.Builder mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream r3, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    kotlin.reflect.jvm.internal.impl.protobuf.Parser<kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$VersionRequirementTable> r1 = kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.VersionRequirementTable.PARSER     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$VersionRequirementTable r3 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.VersionRequirementTable) r3     // Catch: java.lang.Throwable -> Lf kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException -> L11
                    if (r3 == 0) goto Le
                    r2.mergeFrom(r3)
                Le:
                    return r2
                Lf:
                    r3 = move-exception
                    goto L1b
                L11:
                    r3 = move-exception
                    kotlin.reflect.jvm.internal.impl.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> Lf
                    kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$VersionRequirementTable r4 = (kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.VersionRequirementTable) r4     // Catch: java.lang.Throwable -> Lf
                    throw r3     // Catch: java.lang.Throwable -> L19
                L19:
                    r3 = move-exception
                    r0 = r4
                L1b:
                    if (r0 == 0) goto L20
                    r2.mergeFrom(r0)
                L20:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.VersionRequirementTable.Builder.mergeFrom(kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream, kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite):kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf$VersionRequirementTable$Builder");
            }
        }

        static {
            VersionRequirementTable versionRequirementTable = new VersionRequirementTable(true);
            defaultInstance = versionRequirementTable;
            versionRequirementTable.initFields();
        }

        public static VersionRequirementTable getDefaultInstance() {
            return defaultInstance;
        }

        private void initFields() {
            this.requirement_ = Collections.emptyList();
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite, kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Parser<VersionRequirementTable> getParserForType() {
            return PARSER;
        }

        public int getRequirementCount() {
            return this.requirement_.size();
        }

        public List<VersionRequirement> getRequirementList() {
            return this.requirement_;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSerializedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeMessageSize = 0;
            for (int i3 = 0; i3 < this.requirement_.size(); i3++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(1, this.requirement_.get(i3));
            }
            int size = iComputeMessageSize + this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b3 = this.memoizedIsInitialized;
            if (b3 == 1) {
                return true;
            }
            if (b3 == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            for (int i2 = 0; i2 < this.requirement_.size(); i2++) {
                codedOutputStream.writeMessage(1, this.requirement_.get(i2));
            }
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        public static Builder newBuilder(VersionRequirementTable versionRequirementTable) {
            return newBuilder().mergeFrom(versionRequirementTable);
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLiteOrBuilder
        public VersionRequirementTable getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        private VersionRequirementTable(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private VersionRequirementTable(boolean z2) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private VersionRequirementTable(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z2 = false;
            boolean z3 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag != 10) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                if (!(z3 & true)) {
                                    this.requirement_ = new ArrayList();
                                    z3 |= true;
                                }
                                this.requirement_.add(codedInputStream.readMessage(VersionRequirement.PARSER, extensionRegistryLite));
                            }
                        }
                        z2 = true;
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if (z3 & true) {
                        this.requirement_ = Collections.unmodifiableList(this.requirement_);
                    }
                    try {
                        codedOutputStreamNewInstance.flush();
                    } catch (IOException unused) {
                    } catch (Throwable th2) {
                        this.unknownFields = outputNewOutput.toByteString();
                        throw th2;
                    }
                    this.unknownFields = outputNewOutput.toByteString();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if (z3 & true) {
                this.requirement_ = Collections.unmodifiableList(this.requirement_);
            }
            try {
                codedOutputStreamNewInstance.flush();
            } catch (IOException unused2) {
            } catch (Throwable th3) {
                this.unknownFields = outputNewOutput.toByteString();
                throw th3;
            }
            this.unknownFields = outputNewOutput.toByteString();
            makeExtensionsImmutable();
        }
    }

    public interface VersionRequirementTableOrBuilder extends MessageLiteOrBuilder {
    }

    public enum Visibility implements Internal.EnumLite {
        INTERNAL(0, 0),
        PRIVATE(1, 1),
        PROTECTED(2, 2),
        PUBLIC(3, 3),
        PRIVATE_TO_THIS(4, 4),
        LOCAL(5, 5);

        private static Internal.EnumLiteMap<Visibility> internalValueMap = new Internal.EnumLiteMap<Visibility>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Visibility.1
            @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLiteMap
            public Visibility findValueByNumber(int i2) {
                return Visibility.valueOf(i2);
            }
        };
        private final int value;

        Visibility(int i2, int i3) {
            this.value = i3;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLite
        public final int getNumber() {
            return this.value;
        }

        public static Visibility valueOf(int i2) {
            if (i2 == 0) {
                return INTERNAL;
            }
            if (i2 == 1) {
                return PRIVATE;
            }
            if (i2 == 2) {
                return PROTECTED;
            }
            if (i2 == 3) {
                return PUBLIC;
            }
            if (i2 == 4) {
                return PRIVATE_TO_THIS;
            }
            if (i2 != 5) {
                return null;
            }
            return LOCAL;
        }
    }
}

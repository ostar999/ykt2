package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.EnumValue;
import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import androidx.datastore.preferences.protobuf.Internal;
import androidx.datastore.preferences.protobuf.Option;
import androidx.datastore.preferences.protobuf.SourceContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class Enum extends GeneratedMessageLite<Enum, Builder> implements EnumOrBuilder {
    private static final Enum DEFAULT_INSTANCE;
    public static final int ENUMVALUE_FIELD_NUMBER = 2;
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int OPTIONS_FIELD_NUMBER = 3;
    private static volatile Parser<Enum> PARSER = null;
    public static final int SOURCE_CONTEXT_FIELD_NUMBER = 4;
    public static final int SYNTAX_FIELD_NUMBER = 5;
    private SourceContext sourceContext_;
    private int syntax_;
    private String name_ = "";
    private Internal.ProtobufList<EnumValue> enumvalue_ = GeneratedMessageLite.emptyProtobufList();
    private Internal.ProtobufList<Option> options_ = GeneratedMessageLite.emptyProtobufList();

    /* renamed from: androidx.datastore.preferences.protobuf.Enum$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Enum, Builder> implements EnumOrBuilder {
        public /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        public Builder addAllEnumvalue(Iterable<? extends EnumValue> iterable) {
            copyOnWrite();
            ((Enum) this.instance).addAllEnumvalue(iterable);
            return this;
        }

        public Builder addAllOptions(Iterable<? extends Option> iterable) {
            copyOnWrite();
            ((Enum) this.instance).addAllOptions(iterable);
            return this;
        }

        public Builder addEnumvalue(EnumValue enumValue) {
            copyOnWrite();
            ((Enum) this.instance).addEnumvalue(enumValue);
            return this;
        }

        public Builder addOptions(Option option) {
            copyOnWrite();
            ((Enum) this.instance).addOptions(option);
            return this;
        }

        public Builder clearEnumvalue() {
            copyOnWrite();
            ((Enum) this.instance).clearEnumvalue();
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((Enum) this.instance).clearName();
            return this;
        }

        public Builder clearOptions() {
            copyOnWrite();
            ((Enum) this.instance).clearOptions();
            return this;
        }

        public Builder clearSourceContext() {
            copyOnWrite();
            ((Enum) this.instance).clearSourceContext();
            return this;
        }

        public Builder clearSyntax() {
            copyOnWrite();
            ((Enum) this.instance).clearSyntax();
            return this;
        }

        @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
        public EnumValue getEnumvalue(int i2) {
            return ((Enum) this.instance).getEnumvalue(i2);
        }

        @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
        public int getEnumvalueCount() {
            return ((Enum) this.instance).getEnumvalueCount();
        }

        @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
        public List<EnumValue> getEnumvalueList() {
            return Collections.unmodifiableList(((Enum) this.instance).getEnumvalueList());
        }

        @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
        public String getName() {
            return ((Enum) this.instance).getName();
        }

        @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
        public ByteString getNameBytes() {
            return ((Enum) this.instance).getNameBytes();
        }

        @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
        public Option getOptions(int i2) {
            return ((Enum) this.instance).getOptions(i2);
        }

        @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
        public int getOptionsCount() {
            return ((Enum) this.instance).getOptionsCount();
        }

        @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
        public List<Option> getOptionsList() {
            return Collections.unmodifiableList(((Enum) this.instance).getOptionsList());
        }

        @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
        public SourceContext getSourceContext() {
            return ((Enum) this.instance).getSourceContext();
        }

        @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
        public Syntax getSyntax() {
            return ((Enum) this.instance).getSyntax();
        }

        @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
        public int getSyntaxValue() {
            return ((Enum) this.instance).getSyntaxValue();
        }

        @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
        public boolean hasSourceContext() {
            return ((Enum) this.instance).hasSourceContext();
        }

        public Builder mergeSourceContext(SourceContext sourceContext) {
            copyOnWrite();
            ((Enum) this.instance).mergeSourceContext(sourceContext);
            return this;
        }

        public Builder removeEnumvalue(int i2) {
            copyOnWrite();
            ((Enum) this.instance).removeEnumvalue(i2);
            return this;
        }

        public Builder removeOptions(int i2) {
            copyOnWrite();
            ((Enum) this.instance).removeOptions(i2);
            return this;
        }

        public Builder setEnumvalue(int i2, EnumValue enumValue) {
            copyOnWrite();
            ((Enum) this.instance).setEnumvalue(i2, enumValue);
            return this;
        }

        public Builder setName(String str) {
            copyOnWrite();
            ((Enum) this.instance).setName(str);
            return this;
        }

        public Builder setNameBytes(ByteString byteString) throws IllegalArgumentException {
            copyOnWrite();
            ((Enum) this.instance).setNameBytes(byteString);
            return this;
        }

        public Builder setOptions(int i2, Option option) {
            copyOnWrite();
            ((Enum) this.instance).setOptions(i2, option);
            return this;
        }

        public Builder setSourceContext(SourceContext sourceContext) {
            copyOnWrite();
            ((Enum) this.instance).setSourceContext(sourceContext);
            return this;
        }

        public Builder setSyntax(Syntax syntax) {
            copyOnWrite();
            ((Enum) this.instance).setSyntax(syntax);
            return this;
        }

        public Builder setSyntaxValue(int i2) {
            copyOnWrite();
            ((Enum) this.instance).setSyntaxValue(i2);
            return this;
        }

        private Builder() {
            super(Enum.DEFAULT_INSTANCE);
        }

        public Builder addEnumvalue(int i2, EnumValue enumValue) {
            copyOnWrite();
            ((Enum) this.instance).addEnumvalue(i2, enumValue);
            return this;
        }

        public Builder addOptions(int i2, Option option) {
            copyOnWrite();
            ((Enum) this.instance).addOptions(i2, option);
            return this;
        }

        public Builder setEnumvalue(int i2, EnumValue.Builder builder) {
            copyOnWrite();
            ((Enum) this.instance).setEnumvalue(i2, builder);
            return this;
        }

        public Builder setOptions(int i2, Option.Builder builder) {
            copyOnWrite();
            ((Enum) this.instance).setOptions(i2, builder);
            return this;
        }

        public Builder setSourceContext(SourceContext.Builder builder) {
            copyOnWrite();
            ((Enum) this.instance).setSourceContext(builder);
            return this;
        }

        public Builder addEnumvalue(EnumValue.Builder builder) {
            copyOnWrite();
            ((Enum) this.instance).addEnumvalue(builder);
            return this;
        }

        public Builder addOptions(Option.Builder builder) {
            copyOnWrite();
            ((Enum) this.instance).addOptions(builder);
            return this;
        }

        public Builder addEnumvalue(int i2, EnumValue.Builder builder) {
            copyOnWrite();
            ((Enum) this.instance).addEnumvalue(i2, builder);
            return this;
        }

        public Builder addOptions(int i2, Option.Builder builder) {
            copyOnWrite();
            ((Enum) this.instance).addOptions(i2, builder);
            return this;
        }
    }

    static {
        Enum r02 = new Enum();
        DEFAULT_INSTANCE = r02;
        GeneratedMessageLite.registerDefaultInstance(Enum.class, r02);
    }

    private Enum() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllEnumvalue(Iterable<? extends EnumValue> iterable) {
        ensureEnumvalueIsMutable();
        AbstractMessageLite.addAll((Iterable) iterable, (List) this.enumvalue_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllOptions(Iterable<? extends Option> iterable) {
        ensureOptionsIsMutable();
        AbstractMessageLite.addAll((Iterable) iterable, (List) this.options_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addEnumvalue(EnumValue enumValue) {
        enumValue.getClass();
        ensureEnumvalueIsMutable();
        this.enumvalue_.add(enumValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addOptions(Option option) {
        option.getClass();
        ensureOptionsIsMutable();
        this.options_.add(option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearEnumvalue() {
        this.enumvalue_ = GeneratedMessageLite.emptyProtobufList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearName() {
        this.name_ = getDefaultInstance().getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearOptions() {
        this.options_ = GeneratedMessageLite.emptyProtobufList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearSourceContext() {
        this.sourceContext_ = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearSyntax() {
        this.syntax_ = 0;
    }

    private void ensureEnumvalueIsMutable() {
        if (this.enumvalue_.isModifiable()) {
            return;
        }
        this.enumvalue_ = GeneratedMessageLite.mutableCopy(this.enumvalue_);
    }

    private void ensureOptionsIsMutable() {
        if (this.options_.isModifiable()) {
            return;
        }
        this.options_ = GeneratedMessageLite.mutableCopy(this.options_);
    }

    public static Enum getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mergeSourceContext(SourceContext sourceContext) {
        sourceContext.getClass();
        SourceContext sourceContext2 = this.sourceContext_;
        if (sourceContext2 == null || sourceContext2 == SourceContext.getDefaultInstance()) {
            this.sourceContext_ = sourceContext;
        } else {
            this.sourceContext_ = SourceContext.newBuilder(this.sourceContext_).mergeFrom((SourceContext.Builder) sourceContext).buildPartial();
        }
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.createBuilder();
    }

    public static Enum parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Enum) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static Enum parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
    }

    public static Parser<Enum> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeEnumvalue(int i2) {
        ensureEnumvalueIsMutable();
        this.enumvalue_.remove(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeOptions(int i2) {
        ensureOptionsIsMutable();
        this.options_.remove(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEnumvalue(int i2, EnumValue enumValue) {
        enumValue.getClass();
        ensureEnumvalueIsMutable();
        this.enumvalue_.set(i2, enumValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setName(String str) {
        str.getClass();
        this.name_ = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNameBytes(ByteString byteString) throws IllegalArgumentException {
        byteString.getClass();
        AbstractMessageLite.checkByteStringIsUtf8(byteString);
        this.name_ = byteString.toStringUtf8();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setOptions(int i2, Option option) {
        option.getClass();
        ensureOptionsIsMutable();
        this.options_.set(i2, option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSourceContext(SourceContext sourceContext) {
        sourceContext.getClass();
        this.sourceContext_ = sourceContext;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSyntax(Syntax syntax) {
        syntax.getClass();
        this.syntax_ = syntax.getNumber();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSyntaxValue(int i2) {
        this.syntax_ = i2;
    }

    @Override // androidx.datastore.preferences.protobuf.GeneratedMessageLite
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        AnonymousClass1 anonymousClass1 = null;
        switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
            case 1:
                return new Enum();
            case 2:
                return new Builder(anonymousClass1);
            case 3:
                return GeneratedMessageLite.newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0005\u0000\u0000\u0001\u0005\u0005\u0000\u0002\u0000\u0001Ȉ\u0002\u001b\u0003\u001b\u0004\t\u0005\f", new Object[]{"name_", "enumvalue_", EnumValue.class, "options_", Option.class, "sourceContext_", "syntax_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<Enum> defaultInstanceBasedParser = PARSER;
                if (defaultInstanceBasedParser == null) {
                    synchronized (Enum.class) {
                        defaultInstanceBasedParser = PARSER;
                        if (defaultInstanceBasedParser == null) {
                            defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                            PARSER = defaultInstanceBasedParser;
                        }
                    }
                }
                return defaultInstanceBasedParser;
            case 6:
                return (byte) 1;
            case 7:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
    public EnumValue getEnumvalue(int i2) {
        return this.enumvalue_.get(i2);
    }

    @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
    public int getEnumvalueCount() {
        return this.enumvalue_.size();
    }

    @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
    public List<EnumValue> getEnumvalueList() {
        return this.enumvalue_;
    }

    public EnumValueOrBuilder getEnumvalueOrBuilder(int i2) {
        return this.enumvalue_.get(i2);
    }

    public List<? extends EnumValueOrBuilder> getEnumvalueOrBuilderList() {
        return this.enumvalue_;
    }

    @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
    public String getName() {
        return this.name_;
    }

    @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
    public ByteString getNameBytes() {
        return ByteString.copyFromUtf8(this.name_);
    }

    @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
    public Option getOptions(int i2) {
        return this.options_.get(i2);
    }

    @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
    public int getOptionsCount() {
        return this.options_.size();
    }

    @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
    public List<Option> getOptionsList() {
        return this.options_;
    }

    public OptionOrBuilder getOptionsOrBuilder(int i2) {
        return this.options_.get(i2);
    }

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
        return this.options_;
    }

    @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
    public SourceContext getSourceContext() {
        SourceContext sourceContext = this.sourceContext_;
        return sourceContext == null ? SourceContext.getDefaultInstance() : sourceContext;
    }

    @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
    public Syntax getSyntax() {
        Syntax syntaxForNumber = Syntax.forNumber(this.syntax_);
        return syntaxForNumber == null ? Syntax.UNRECOGNIZED : syntaxForNumber;
    }

    @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
    public int getSyntaxValue() {
        return this.syntax_;
    }

    @Override // androidx.datastore.preferences.protobuf.EnumOrBuilder
    public boolean hasSourceContext() {
        return this.sourceContext_ != null;
    }

    public static Builder newBuilder(Enum r12) {
        return DEFAULT_INSTANCE.createBuilder(r12);
    }

    public static Enum parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Enum) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Enum parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
    }

    public static Enum parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSourceContext(SourceContext.Builder builder) {
        this.sourceContext_ = builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addEnumvalue(int i2, EnumValue enumValue) {
        enumValue.getClass();
        ensureEnumvalueIsMutable();
        this.enumvalue_.add(i2, enumValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addOptions(int i2, Option option) {
        option.getClass();
        ensureOptionsIsMutable();
        this.options_.add(i2, option);
    }

    public static Enum parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEnumvalue(int i2, EnumValue.Builder builder) {
        ensureEnumvalueIsMutable();
        this.enumvalue_.set(i2, builder.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setOptions(int i2, Option.Builder builder) {
        ensureOptionsIsMutable();
        this.options_.set(i2, builder.build());
    }

    public static Enum parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static Enum parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addEnumvalue(EnumValue.Builder builder) {
        ensureEnumvalueIsMutable();
        this.enumvalue_.add(builder.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addOptions(Option.Builder builder) {
        ensureOptionsIsMutable();
        this.options_.add(builder.build());
    }

    public static Enum parseFrom(InputStream inputStream) throws IOException {
        return (Enum) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static Enum parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Enum) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addEnumvalue(int i2, EnumValue.Builder builder) {
        ensureEnumvalueIsMutable();
        this.enumvalue_.add(i2, builder.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addOptions(int i2, Option.Builder builder) {
        ensureOptionsIsMutable();
        this.options_.add(i2, builder.build());
    }

    public static Enum parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Enum) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static Enum parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Enum) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }
}

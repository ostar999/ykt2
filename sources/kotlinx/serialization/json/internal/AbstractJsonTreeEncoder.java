package kotlinx.serialization.json.internal;

import androidx.exifinterface.media.ExifInterface;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.Unit;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.serialization.ExperimentalSerializationApi;
import kotlinx.serialization.PolymorphicSerializerKt;
import kotlinx.serialization.SerializationStrategy;
import kotlinx.serialization.descriptors.PolymorphicKind;
import kotlinx.serialization.descriptors.PrimitiveKind;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.descriptors.SerialKind;
import kotlinx.serialization.descriptors.StructureKind;
import kotlinx.serialization.encoding.AbstractEncoder;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.encoding.Encoder;
import kotlinx.serialization.internal.AbstractPolymorphicSerializer;
import kotlinx.serialization.internal.NamedValueEncoder;
import kotlinx.serialization.json.Json;
import kotlinx.serialization.json.JsonConfiguration;
import kotlinx.serialization.json.JsonElement;
import kotlinx.serialization.json.JsonElementKt;
import kotlinx.serialization.json.JsonElementSerializer;
import kotlinx.serialization.json.JsonEncoder;
import kotlinx.serialization.json.JsonLiteral;
import kotlinx.serialization.json.JsonNull;
import kotlinx.serialization.modules.SerializersModule;
import net.lingala.zip4j.util.InternalZipConstants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000²\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000*\u0002EH\b3\u0018\u00002\u00020\u00012\u00020\u0002B#\b\u0004\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\u0002\u0010\tJ\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000fH\u0014J\u0018\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001dH\u0014J\u0010\u0010\u001e\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\u0007H\u0016J\b\u0010 \u001a\u00020\bH\u0016J\b\u0010!\u001a\u00020\bH\u0016J)\u0010\"\u001a\u00020\b\"\u0004\b\u0000\u0010#2\f\u0010$\u001a\b\u0012\u0004\u0012\u0002H#0%2\u0006\u0010&\u001a\u0002H#H\u0016¢\u0006\u0002\u0010'J\u0018\u0010(\u001a\u00020\b2\u0006\u0010)\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020*H\u0014J\u0018\u0010+\u001a\u00020\b2\u0006\u0010)\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020,H\u0014J\u0018\u0010-\u001a\u00020\b2\u0006\u0010)\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020.H\u0014J\u0018\u0010/\u001a\u00020\b2\u0006\u0010)\u001a\u00020\u000f2\u0006\u0010&\u001a\u000200H\u0014J \u00101\u001a\u00020\b2\u0006\u0010)\u001a\u00020\u000f2\u0006\u00102\u001a\u00020\u00172\u0006\u00103\u001a\u00020\u001dH\u0014J\u0018\u00104\u001a\u00020\b2\u0006\u0010)\u001a\u00020\u000f2\u0006\u0010&\u001a\u000205H\u0014J\u0018\u00106\u001a\u0002072\u0006\u0010)\u001a\u00020\u000f2\u0006\u00108\u001a\u00020\u0017H\u0014J\u0018\u00109\u001a\u00020\b2\u0006\u0010)\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020\u001dH\u0014J\u0018\u0010:\u001a\u00020\b2\u0006\u0010)\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020;H\u0014J\u0010\u0010<\u001a\u00020\b2\u0006\u0010)\u001a\u00020\u000fH\u0014J\u0018\u0010=\u001a\u00020\b2\u0006\u0010)\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020>H\u0014J\u0018\u0010?\u001a\u00020\b2\u0006\u0010)\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020\u000fH\u0014J\u0018\u0010@\u001a\u00020\b2\u0006\u0010)\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020AH\u0014J\u0010\u0010B\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u0017H\u0014J\b\u0010C\u001a\u00020\u0007H&J\u001d\u0010D\u001a\u00020E2\u0006\u0010)\u001a\u00020\u000f2\u0006\u00108\u001a\u00020\u0017H\u0002¢\u0006\u0002\u0010FJ\u0015\u0010G\u001a\u00020H2\u0006\u0010)\u001a\u00020\u000fH\u0003¢\u0006\u0002\u0010IJ\u0018\u0010J\u001a\u00020\b2\u0006\u0010K\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u0007H&J\u0018\u0010L\u001a\u00020*2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001dH\u0016R\u0010\u0010\n\u001a\u00020\u000b8\u0004X\u0085\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0010\u001a\u00020\u00118F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\u0082\u0001\u0003MNO¨\u0006P"}, d2 = {"Lkotlinx/serialization/json/internal/AbstractJsonTreeEncoder;", "Lkotlinx/serialization/internal/NamedValueEncoder;", "Lkotlinx/serialization/json/JsonEncoder;", AliyunVodHttpCommon.Format.FORMAT_JSON, "Lkotlinx/serialization/json/Json;", "nodeConsumer", "Lkotlin/Function1;", "Lkotlinx/serialization/json/JsonElement;", "", "(Lkotlinx/serialization/json/Json;Lkotlin/jvm/functions/Function1;)V", "configuration", "Lkotlinx/serialization/json/JsonConfiguration;", "getJson", "()Lkotlinx/serialization/json/Json;", "polymorphicDiscriminator", "", "serializersModule", "Lkotlinx/serialization/modules/SerializersModule;", "getSerializersModule", "()Lkotlinx/serialization/modules/SerializersModule;", "beginStructure", "Lkotlinx/serialization/encoding/CompositeEncoder;", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "composeName", "parentName", "childName", "elementName", "index", "", "encodeJsonElement", "element", "encodeNotNullMark", "encodeNull", "encodeSerializableValue", ExifInterface.GPS_DIRECTION_TRUE, "serializer", "Lkotlinx/serialization/SerializationStrategy;", "value", "(Lkotlinx/serialization/SerializationStrategy;Ljava/lang/Object;)V", "encodeTaggedBoolean", "tag", "", "encodeTaggedByte", "", "encodeTaggedChar", "", "encodeTaggedDouble", "", "encodeTaggedEnum", "enumDescriptor", "ordinal", "encodeTaggedFloat", "", "encodeTaggedInline", "Lkotlinx/serialization/encoding/Encoder;", "inlineDescriptor", "encodeTaggedInt", "encodeTaggedLong", "", "encodeTaggedNull", "encodeTaggedShort", "", "encodeTaggedString", "encodeTaggedValue", "", "endEncode", "getCurrent", "inlineUnquotedLiteralEncoder", "kotlinx/serialization/json/internal/AbstractJsonTreeEncoder$inlineUnquotedLiteralEncoder$1", "(Ljava/lang/String;Lkotlinx/serialization/descriptors/SerialDescriptor;)Lkotlinx/serialization/json/internal/AbstractJsonTreeEncoder$inlineUnquotedLiteralEncoder$1;", "inlineUnsignedNumberEncoder", "kotlinx/serialization/json/internal/AbstractJsonTreeEncoder$inlineUnsignedNumberEncoder$1", "(Ljava/lang/String;)Lkotlinx/serialization/json/internal/AbstractJsonTreeEncoder$inlineUnsignedNumberEncoder$1;", "putElement", "key", "shouldEncodeElementDefault", "Lkotlinx/serialization/json/internal/JsonPrimitiveEncoder;", "Lkotlinx/serialization/json/internal/JsonTreeEncoder;", "Lkotlinx/serialization/json/internal/JsonTreeListEncoder;", "kotlinx-serialization-json"}, k = 1, mv = {1, 8, 0}, xi = 48)
@ExperimentalSerializationApi
@SourceDebugExtension({"SMAP\nTreeJsonEncoder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TreeJsonEncoder.kt\nkotlinx/serialization/json/internal/AbstractJsonTreeEncoder\n+ 2 Polymorphic.kt\nkotlinx/serialization/json/internal/PolymorphicKt\n+ 3 WriteMode.kt\nkotlinx/serialization/json/internal/WriteModeKt\n*L\n1#1,255:1\n20#2,12:256\n36#3,9:268\n*S KotlinDebug\n*F\n+ 1 TreeJsonEncoder.kt\nkotlinx/serialization/json/internal/AbstractJsonTreeEncoder\n*L\n80#1:256,12\n139#1:268,9\n*E\n"})
/* loaded from: classes8.dex */
abstract class AbstractJsonTreeEncoder extends NamedValueEncoder implements JsonEncoder {

    @JvmField
    @NotNull
    protected final JsonConfiguration configuration;

    @NotNull
    private final Json json;

    @NotNull
    private final Function1<JsonElement, Unit> nodeConsumer;

    @Nullable
    private String polymorphicDiscriminator;

    /* JADX WARN: Multi-variable type inference failed */
    private AbstractJsonTreeEncoder(Json json, Function1<? super JsonElement, Unit> function1) {
        this.json = json;
        this.nodeConsumer = function1;
        this.configuration = json.getConfiguration();
    }

    public /* synthetic */ AbstractJsonTreeEncoder(Json json, Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
        this(json, function1);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [kotlinx.serialization.json.internal.AbstractJsonTreeEncoder$inlineUnquotedLiteralEncoder$1] */
    private final AnonymousClass1 inlineUnquotedLiteralEncoder(final String tag, final SerialDescriptor inlineDescriptor) {
        return new AbstractEncoder() { // from class: kotlinx.serialization.json.internal.AbstractJsonTreeEncoder.inlineUnquotedLiteralEncoder.1
            @Override // kotlinx.serialization.encoding.AbstractEncoder, kotlinx.serialization.encoding.Encoder
            public void encodeString(@NotNull String value) {
                Intrinsics.checkNotNullParameter(value, "value");
                AbstractJsonTreeEncoder.this.putElement(tag, new JsonLiteral(value, false, inlineDescriptor));
            }

            @Override // kotlinx.serialization.encoding.Encoder, kotlinx.serialization.encoding.CompositeEncoder
            @NotNull
            public SerializersModule getSerializersModule() {
                return AbstractJsonTreeEncoder.this.getJson().getSerializersModule();
            }
        };
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [kotlinx.serialization.json.internal.AbstractJsonTreeEncoder$inlineUnsignedNumberEncoder$1] */
    @SuppressAnimalSniffer
    private final C12831 inlineUnsignedNumberEncoder(final String tag) {
        return new AbstractEncoder() { // from class: kotlinx.serialization.json.internal.AbstractJsonTreeEncoder.inlineUnsignedNumberEncoder.1

            @NotNull
            private final SerializersModule serializersModule;

            {
                this.serializersModule = AbstractJsonTreeEncoder.this.getJson().getSerializersModule();
            }

            @Override // kotlinx.serialization.encoding.AbstractEncoder, kotlinx.serialization.encoding.Encoder
            public void encodeByte(byte value) {
                putUnquotedString(UByte.m845toStringimpl(UByte.m801constructorimpl(value)));
            }

            @Override // kotlinx.serialization.encoding.AbstractEncoder, kotlinx.serialization.encoding.Encoder
            public void encodeInt(int value) {
                putUnquotedString(Long.toString(UInt.m878constructorimpl(value) & InternalZipConstants.ZIP_64_LIMIT, 10));
            }

            @Override // kotlinx.serialization.encoding.AbstractEncoder, kotlinx.serialization.encoding.Encoder
            public void encodeLong(long value) {
                putUnquotedString(c.a(ULong.m957constructorimpl(value), 10));
            }

            @Override // kotlinx.serialization.encoding.AbstractEncoder, kotlinx.serialization.encoding.Encoder
            public void encodeShort(short value) {
                putUnquotedString(UShort.m1108toStringimpl(UShort.m1064constructorimpl(value)));
            }

            @Override // kotlinx.serialization.encoding.Encoder, kotlinx.serialization.encoding.CompositeEncoder
            @NotNull
            public SerializersModule getSerializersModule() {
                return this.serializersModule;
            }

            public final void putUnquotedString(@NotNull String s2) {
                Intrinsics.checkNotNullParameter(s2, "s");
                AbstractJsonTreeEncoder.this.putElement(tag, new JsonLiteral(s2, false, null, 4, null));
            }
        };
    }

    @Override // kotlinx.serialization.internal.TaggedEncoder, kotlinx.serialization.encoding.Encoder
    @NotNull
    public CompositeEncoder beginStructure(@NotNull SerialDescriptor descriptor) {
        AbstractJsonTreeEncoder jsonTreeEncoder;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Function1<JsonElement, Unit> function1 = getCurrentTagOrNull() == null ? this.nodeConsumer : new Function1<JsonElement, Unit>() { // from class: kotlinx.serialization.json.internal.AbstractJsonTreeEncoder$beginStructure$consumer$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(JsonElement jsonElement) {
                invoke2(jsonElement);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull JsonElement node) {
                Intrinsics.checkNotNullParameter(node, "node");
                AbstractJsonTreeEncoder abstractJsonTreeEncoder = this.this$0;
                abstractJsonTreeEncoder.putElement(abstractJsonTreeEncoder.getCurrentTag(), node);
            }
        };
        SerialKind kind = descriptor.getKind();
        if (Intrinsics.areEqual(kind, StructureKind.LIST.INSTANCE) ? true : kind instanceof PolymorphicKind) {
            jsonTreeEncoder = new JsonTreeListEncoder(this.json, function1);
        } else if (Intrinsics.areEqual(kind, StructureKind.MAP.INSTANCE)) {
            Json json = this.json;
            SerialDescriptor serialDescriptorCarrierDescriptor = WriteModeKt.carrierDescriptor(descriptor.getElementDescriptor(0), json.getSerializersModule());
            SerialKind kind2 = serialDescriptorCarrierDescriptor.getKind();
            if ((kind2 instanceof PrimitiveKind) || Intrinsics.areEqual(kind2, SerialKind.ENUM.INSTANCE)) {
                jsonTreeEncoder = new JsonTreeMapEncoder(this.json, function1);
            } else {
                if (!json.getConfiguration().getAllowStructuredMapKeys()) {
                    throw JsonExceptionsKt.InvalidKeyKindException(serialDescriptorCarrierDescriptor);
                }
                jsonTreeEncoder = new JsonTreeListEncoder(this.json, function1);
            }
        } else {
            jsonTreeEncoder = new JsonTreeEncoder(this.json, function1);
        }
        String str = this.polymorphicDiscriminator;
        if (str != null) {
            Intrinsics.checkNotNull(str);
            jsonTreeEncoder.putElement(str, JsonElementKt.JsonPrimitive(descriptor.getSerialName()));
            this.polymorphicDiscriminator = null;
        }
        return jsonTreeEncoder;
    }

    @Override // kotlinx.serialization.internal.NamedValueEncoder
    @NotNull
    public String composeName(@NotNull String parentName, @NotNull String childName) {
        Intrinsics.checkNotNullParameter(parentName, "parentName");
        Intrinsics.checkNotNullParameter(childName, "childName");
        return childName;
    }

    @Override // kotlinx.serialization.internal.NamedValueEncoder
    @NotNull
    public String elementName(@NotNull SerialDescriptor descriptor, int index) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return JsonNamesMapKt.getJsonElementName(descriptor, this.json, index);
    }

    @Override // kotlinx.serialization.json.JsonEncoder
    public void encodeJsonElement(@NotNull JsonElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        encodeSerializableValue(JsonElementSerializer.INSTANCE, element);
    }

    @Override // kotlinx.serialization.internal.TaggedEncoder, kotlinx.serialization.encoding.Encoder
    public void encodeNotNullMark() {
    }

    @Override // kotlinx.serialization.internal.TaggedEncoder, kotlinx.serialization.encoding.Encoder
    public void encodeNull() {
        String currentTagOrNull = getCurrentTagOrNull();
        if (currentTagOrNull == null) {
            this.nodeConsumer.invoke(JsonNull.INSTANCE);
        } else {
            encodeTaggedNull(currentTagOrNull);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.serialization.internal.TaggedEncoder, kotlinx.serialization.encoding.Encoder
    public <T> void encodeSerializableValue(@NotNull SerializationStrategy<? super T> serializer, T value) {
        Intrinsics.checkNotNullParameter(serializer, "serializer");
        if (getCurrentTagOrNull() == null && TreeJsonEncoderKt.getRequiresTopLevelTag(WriteModeKt.carrierDescriptor(serializer.getDescriptor(), getSerializersModule()))) {
            JsonPrimitiveEncoder jsonPrimitiveEncoder = new JsonPrimitiveEncoder(this.json, this.nodeConsumer);
            jsonPrimitiveEncoder.encodeSerializableValue(serializer, value);
            jsonPrimitiveEncoder.endEncode(serializer.getDescriptor());
        } else {
            if (!(serializer instanceof AbstractPolymorphicSerializer) || getJson().getConfiguration().getUseArrayPolymorphism()) {
                serializer.serialize(this, value);
                return;
            }
            AbstractPolymorphicSerializer abstractPolymorphicSerializer = (AbstractPolymorphicSerializer) serializer;
            String strClassDiscriminator = PolymorphicKt.classDiscriminator(serializer.getDescriptor(), getJson());
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Any");
            SerializationStrategy serializationStrategyFindPolymorphicSerializer = PolymorphicSerializerKt.findPolymorphicSerializer(abstractPolymorphicSerializer, this, value);
            PolymorphicKt.validateIfSealed(abstractPolymorphicSerializer, serializationStrategyFindPolymorphicSerializer, strClassDiscriminator);
            PolymorphicKt.checkKind(serializationStrategyFindPolymorphicSerializer.getDescriptor().getKind());
            this.polymorphicDiscriminator = strClassDiscriminator;
            serializationStrategyFindPolymorphicSerializer.serialize(this, value);
        }
    }

    @Override // kotlinx.serialization.internal.TaggedEncoder
    public void endEncode(@NotNull SerialDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        this.nodeConsumer.invoke(getCurrent());
    }

    @NotNull
    public abstract JsonElement getCurrent();

    @Override // kotlinx.serialization.json.JsonEncoder
    @NotNull
    public final Json getJson() {
        return this.json;
    }

    @Override // kotlinx.serialization.internal.TaggedEncoder, kotlinx.serialization.encoding.Encoder, kotlinx.serialization.encoding.CompositeEncoder
    @NotNull
    public final SerializersModule getSerializersModule() {
        return this.json.getSerializersModule();
    }

    public abstract void putElement(@NotNull String key, @NotNull JsonElement element);

    @Override // kotlinx.serialization.internal.TaggedEncoder, kotlinx.serialization.encoding.CompositeEncoder
    public boolean shouldEncodeElementDefault(@NotNull SerialDescriptor descriptor, int index) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return this.configuration.getEncodeDefaults();
    }

    @Override // kotlinx.serialization.internal.TaggedEncoder
    public void encodeTaggedBoolean(@NotNull String tag, boolean value) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        putElement(tag, JsonElementKt.JsonPrimitive(Boolean.valueOf(value)));
    }

    @Override // kotlinx.serialization.internal.TaggedEncoder
    public void encodeTaggedByte(@NotNull String tag, byte value) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        putElement(tag, JsonElementKt.JsonPrimitive(Byte.valueOf(value)));
    }

    @Override // kotlinx.serialization.internal.TaggedEncoder
    public void encodeTaggedChar(@NotNull String tag, char value) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        putElement(tag, JsonElementKt.JsonPrimitive(String.valueOf(value)));
    }

    @Override // kotlinx.serialization.internal.TaggedEncoder
    public void encodeTaggedDouble(@NotNull String tag, double value) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        putElement(tag, JsonElementKt.JsonPrimitive(Double.valueOf(value)));
        if (this.configuration.getAllowSpecialFloatingPointValues()) {
            return;
        }
        if (!((Double.isInfinite(value) || Double.isNaN(value)) ? false : true)) {
            throw JsonExceptionsKt.InvalidFloatingPointEncoded(Double.valueOf(value), tag, getCurrent().toString());
        }
    }

    @Override // kotlinx.serialization.internal.TaggedEncoder
    public void encodeTaggedEnum(@NotNull String tag, @NotNull SerialDescriptor enumDescriptor, int ordinal) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(enumDescriptor, "enumDescriptor");
        putElement(tag, JsonElementKt.JsonPrimitive(enumDescriptor.getElementName(ordinal)));
    }

    @Override // kotlinx.serialization.internal.TaggedEncoder
    public void encodeTaggedFloat(@NotNull String tag, float value) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        putElement(tag, JsonElementKt.JsonPrimitive(Float.valueOf(value)));
        if (this.configuration.getAllowSpecialFloatingPointValues()) {
            return;
        }
        if (!((Float.isInfinite(value) || Float.isNaN(value)) ? false : true)) {
            throw JsonExceptionsKt.InvalidFloatingPointEncoded(Float.valueOf(value), tag, getCurrent().toString());
        }
    }

    @Override // kotlinx.serialization.internal.TaggedEncoder
    @NotNull
    public Encoder encodeTaggedInline(@NotNull String tag, @NotNull SerialDescriptor inlineDescriptor) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(inlineDescriptor, "inlineDescriptor");
        return StreamingJsonEncoderKt.isUnsignedNumber(inlineDescriptor) ? inlineUnsignedNumberEncoder(tag) : StreamingJsonEncoderKt.isUnquotedLiteral(inlineDescriptor) ? inlineUnquotedLiteralEncoder(tag, inlineDescriptor) : super.encodeTaggedInline((AbstractJsonTreeEncoder) tag, inlineDescriptor);
    }

    @Override // kotlinx.serialization.internal.TaggedEncoder
    public void encodeTaggedInt(@NotNull String tag, int value) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        putElement(tag, JsonElementKt.JsonPrimitive(Integer.valueOf(value)));
    }

    @Override // kotlinx.serialization.internal.TaggedEncoder
    public void encodeTaggedLong(@NotNull String tag, long value) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        putElement(tag, JsonElementKt.JsonPrimitive(Long.valueOf(value)));
    }

    @Override // kotlinx.serialization.internal.TaggedEncoder
    public void encodeTaggedNull(@NotNull String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        putElement(tag, JsonNull.INSTANCE);
    }

    @Override // kotlinx.serialization.internal.TaggedEncoder
    public void encodeTaggedShort(@NotNull String tag, short value) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        putElement(tag, JsonElementKt.JsonPrimitive(Short.valueOf(value)));
    }

    @Override // kotlinx.serialization.internal.TaggedEncoder
    public void encodeTaggedString(@NotNull String tag, @NotNull String value) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(value, "value");
        putElement(tag, JsonElementKt.JsonPrimitive(value));
    }

    @Override // kotlinx.serialization.internal.TaggedEncoder
    public void encodeTaggedValue(@NotNull String tag, @NotNull Object value) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(value, "value");
        putElement(tag, JsonElementKt.JsonPrimitive(value.toString()));
    }
}

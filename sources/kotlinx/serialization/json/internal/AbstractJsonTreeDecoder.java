package kotlinx.serialization.json.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import cn.hutool.core.text.CharPool;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt___StringsKt;
import kotlinx.serialization.DeserializationStrategy;
import kotlinx.serialization.descriptors.PolymorphicKind;
import kotlinx.serialization.descriptors.PrimitiveKind;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.descriptors.SerialKind;
import kotlinx.serialization.descriptors.StructureKind;
import kotlinx.serialization.encoding.CompositeDecoder;
import kotlinx.serialization.encoding.Decoder;
import kotlinx.serialization.internal.NamedValueDecoder;
import kotlinx.serialization.json.Json;
import kotlinx.serialization.json.JsonArray;
import kotlinx.serialization.json.JsonConfiguration;
import kotlinx.serialization.json.JsonDecoder;
import kotlinx.serialization.json.JsonElement;
import kotlinx.serialization.json.JsonElementKt;
import kotlinx.serialization.json.JsonLiteral;
import kotlinx.serialization.json.JsonNull;
import kotlinx.serialization.json.JsonObject;
import kotlinx.serialization.json.JsonPrimitive;
import kotlinx.serialization.modules.SerializersModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000À\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\b\u0004\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u0017H\u0014J\u0010\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u0017H$J\b\u0010\u001c\u001a\u00020\u0006H\u0002J\b\u0010\u001d\u001a\u00020\u0006H\u0016J\b\u0010\u001e\u001a\u00020\u001fH\u0016J!\u0010 \u001a\u0002H!\"\u0004\b\u0000\u0010!2\f\u0010\"\u001a\b\u0012\u0004\u0012\u0002H!0#H\u0016¢\u0006\u0002\u0010$J\u0010\u0010%\u001a\u00020\u001f2\u0006\u0010\u001b\u001a\u00020\u0017H\u0014J\u0010\u0010&\u001a\u00020'2\u0006\u0010\u001b\u001a\u00020\u0017H\u0014J\u0010\u0010(\u001a\u00020)2\u0006\u0010\u001b\u001a\u00020\u0017H\u0014J\u0010\u0010*\u001a\u00020+2\u0006\u0010\u001b\u001a\u00020\u0017H\u0014J\u0018\u0010,\u001a\u00020-2\u0006\u0010\u001b\u001a\u00020\u00172\u0006\u0010.\u001a\u00020\u0015H\u0014J\u0010\u0010/\u001a\u0002002\u0006\u0010\u001b\u001a\u00020\u0017H\u0014J\u0018\u00101\u001a\u0002022\u0006\u0010\u001b\u001a\u00020\u00172\u0006\u00103\u001a\u00020\u0015H\u0014J\u0010\u00104\u001a\u00020-2\u0006\u0010\u001b\u001a\u00020\u0017H\u0014J\u0010\u00105\u001a\u0002062\u0006\u0010\u001b\u001a\u00020\u0017H\u0014J\u0010\u00107\u001a\u00020\u001f2\u0006\u0010\u001b\u001a\u00020\u0017H\u0014J\u0012\u00108\u001a\u0004\u0018\u0001092\u0006\u0010\u001b\u001a\u00020\u0017H\u0014J\u0010\u0010:\u001a\u00020;2\u0006\u0010\u001b\u001a\u00020\u0017H\u0014J\u0010\u0010<\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u0017H\u0014J\u0010\u0010=\u001a\u00020>2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010?\u001a\u00020@2\u0006\u0010\u001b\u001a\u00020\u0017H\u0004J\u0010\u0010A\u001a\u0002092\u0006\u0010B\u001a\u00020\u0017H\u0002J\u0014\u0010C\u001a\u00020D*\u00020@2\u0006\u0010E\u001a\u00020\u0017H\u0002J?\u0010B\u001a\u0002H!\"\b\b\u0000\u0010!*\u00020F*\u00020@2\u0006\u0010B\u001a\u00020\u00172\u0019\u0010G\u001a\u0015\u0012\u0004\u0012\u00020@\u0012\u0006\u0012\u0004\u0018\u0001H!0H¢\u0006\u0002\bIH\u0082\b¢\u0006\u0002\u0010JR\u0010\u0010\b\u001a\u00020\t8\u0004X\u0085\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u0082\u0001\u0003KLM¨\u0006N"}, d2 = {"Lkotlinx/serialization/json/internal/AbstractJsonTreeDecoder;", "Lkotlinx/serialization/internal/NamedValueDecoder;", "Lkotlinx/serialization/json/JsonDecoder;", AliyunVodHttpCommon.Format.FORMAT_JSON, "Lkotlinx/serialization/json/Json;", "value", "Lkotlinx/serialization/json/JsonElement;", "(Lkotlinx/serialization/json/Json;Lkotlinx/serialization/json/JsonElement;)V", "configuration", "Lkotlinx/serialization/json/JsonConfiguration;", "getJson", "()Lkotlinx/serialization/json/Json;", "serializersModule", "Lkotlinx/serialization/modules/SerializersModule;", "getSerializersModule", "()Lkotlinx/serialization/modules/SerializersModule;", "getValue", "()Lkotlinx/serialization/json/JsonElement;", "beginStructure", "Lkotlinx/serialization/encoding/CompositeDecoder;", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "composeName", "", "parentName", "childName", "currentElement", "tag", "currentObject", "decodeJsonElement", "decodeNotNullMark", "", "decodeSerializableValue", ExifInterface.GPS_DIRECTION_TRUE, "deserializer", "Lkotlinx/serialization/DeserializationStrategy;", "(Lkotlinx/serialization/DeserializationStrategy;)Ljava/lang/Object;", "decodeTaggedBoolean", "decodeTaggedByte", "", "decodeTaggedChar", "", "decodeTaggedDouble", "", "decodeTaggedEnum", "", "enumDescriptor", "decodeTaggedFloat", "", "decodeTaggedInline", "Lkotlinx/serialization/encoding/Decoder;", "inlineDescriptor", "decodeTaggedInt", "decodeTaggedLong", "", "decodeTaggedNotNullMark", "decodeTaggedNull", "", "decodeTaggedShort", "", "decodeTaggedString", "endStructure", "", "getPrimitiveValue", "Lkotlinx/serialization/json/JsonPrimitive;", "unparsedPrimitive", TreeJsonEncoderKt.PRIMITIVE_TAG, "asLiteral", "Lkotlinx/serialization/json/JsonLiteral;", "type", "", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/serialization/json/JsonPrimitive;Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "Lkotlinx/serialization/json/internal/JsonPrimitiveDecoder;", "Lkotlinx/serialization/json/internal/JsonTreeDecoder;", "Lkotlinx/serialization/json/internal/JsonTreeListDecoder;", "kotlinx-serialization-json"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nTreeJsonDecoder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TreeJsonDecoder.kt\nkotlinx/serialization/json/internal/AbstractJsonTreeDecoder\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 TreeJsonEncoder.kt\nkotlinx/serialization/json/internal/TreeJsonEncoderKt\n+ 4 WriteMode.kt\nkotlinx/serialization/json/internal/WriteModeKt\n*L\n1#1,327:1\n138#1,4:365\n138#1,4:369\n138#1,4:373\n138#1,4:377\n138#1,4:381\n138#1,4:385\n138#1,4:389\n138#1,4:393\n1#2:328\n247#3,7:329\n247#3,7:341\n247#3,7:350\n247#3,7:358\n36#4,5:336\n41#4,2:348\n44#4:357\n*S KotlinDebug\n*F\n+ 1 TreeJsonDecoder.kt\nkotlinx/serialization/json/internal/AbstractJsonTreeDecoder\n*L\n101#1:365,4\n106#1:369,4\n112#1:373,4\n118#1:377,4\n119#1:381,4\n122#1:385,4\n129#1:389,4\n135#1:393,4\n60#1:329,7\n63#1:341,7\n64#1:350,7\n66#1:358,7\n61#1:336,5\n61#1:348,2\n61#1:357\n*E\n"})
/* loaded from: classes8.dex */
abstract class AbstractJsonTreeDecoder extends NamedValueDecoder implements JsonDecoder {

    @JvmField
    @NotNull
    protected final JsonConfiguration configuration;

    @NotNull
    private final Json json;

    @NotNull
    private final JsonElement value;

    private AbstractJsonTreeDecoder(Json json, JsonElement jsonElement) {
        this.json = json;
        this.value = jsonElement;
        this.configuration = getJson().getConfiguration();
    }

    public /* synthetic */ AbstractJsonTreeDecoder(Json json, JsonElement jsonElement, DefaultConstructorMarker defaultConstructorMarker) {
        this(json, jsonElement);
    }

    private final JsonLiteral asLiteral(JsonPrimitive jsonPrimitive, String str) {
        JsonLiteral jsonLiteral = jsonPrimitive instanceof JsonLiteral ? (JsonLiteral) jsonPrimitive : null;
        if (jsonLiteral != null) {
            return jsonLiteral;
        }
        throw JsonExceptionsKt.JsonDecodingException(-1, "Unexpected 'null' when " + str + " was expected");
    }

    private final JsonElement currentObject() {
        JsonElement jsonElementCurrentElement;
        String currentTagOrNull = getCurrentTagOrNull();
        return (currentTagOrNull == null || (jsonElementCurrentElement = currentElement(currentTagOrNull)) == null) ? getValue() : jsonElementCurrentElement;
    }

    private final <T> T primitive(JsonPrimitive jsonPrimitive, String str, Function1<? super JsonPrimitive, ? extends T> function1) {
        try {
            T tInvoke = function1.invoke(jsonPrimitive);
            if (tInvoke != null) {
                return tInvoke;
            }
            unparsedPrimitive(str);
            throw new KotlinNothingValueException();
        } catch (IllegalArgumentException unused) {
            unparsedPrimitive(str);
            throw new KotlinNothingValueException();
        }
    }

    private final Void unparsedPrimitive(String primitive) {
        throw JsonExceptionsKt.JsonDecodingException(-1, "Failed to parse '" + primitive + CharPool.SINGLE_QUOTE, currentObject().toString());
    }

    @Override // kotlinx.serialization.internal.TaggedDecoder, kotlinx.serialization.encoding.Decoder
    @NotNull
    public CompositeDecoder beginStructure(@NotNull SerialDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        JsonElement jsonElementCurrentObject = currentObject();
        SerialKind kind = descriptor.getKind();
        if (Intrinsics.areEqual(kind, StructureKind.LIST.INSTANCE) ? true : kind instanceof PolymorphicKind) {
            Json json = getJson();
            if (jsonElementCurrentObject instanceof JsonArray) {
                return new JsonTreeListDecoder(json, (JsonArray) jsonElementCurrentObject);
            }
            throw JsonExceptionsKt.JsonDecodingException(-1, "Expected " + Reflection.getOrCreateKotlinClass(JsonArray.class) + " as the serialized body of " + descriptor.getSerialName() + ", but had " + Reflection.getOrCreateKotlinClass(jsonElementCurrentObject.getClass()));
        }
        if (!Intrinsics.areEqual(kind, StructureKind.MAP.INSTANCE)) {
            Json json2 = getJson();
            if (jsonElementCurrentObject instanceof JsonObject) {
                return new JsonTreeDecoder(json2, (JsonObject) jsonElementCurrentObject, null, null, 12, null);
            }
            throw JsonExceptionsKt.JsonDecodingException(-1, "Expected " + Reflection.getOrCreateKotlinClass(JsonObject.class) + " as the serialized body of " + descriptor.getSerialName() + ", but had " + Reflection.getOrCreateKotlinClass(jsonElementCurrentObject.getClass()));
        }
        Json json3 = getJson();
        SerialDescriptor serialDescriptorCarrierDescriptor = WriteModeKt.carrierDescriptor(descriptor.getElementDescriptor(0), json3.getSerializersModule());
        SerialKind kind2 = serialDescriptorCarrierDescriptor.getKind();
        if ((kind2 instanceof PrimitiveKind) || Intrinsics.areEqual(kind2, SerialKind.ENUM.INSTANCE)) {
            Json json4 = getJson();
            if (jsonElementCurrentObject instanceof JsonObject) {
                return new JsonTreeMapDecoder(json4, (JsonObject) jsonElementCurrentObject);
            }
            throw JsonExceptionsKt.JsonDecodingException(-1, "Expected " + Reflection.getOrCreateKotlinClass(JsonObject.class) + " as the serialized body of " + descriptor.getSerialName() + ", but had " + Reflection.getOrCreateKotlinClass(jsonElementCurrentObject.getClass()));
        }
        if (!json3.getConfiguration().getAllowStructuredMapKeys()) {
            throw JsonExceptionsKt.InvalidKeyKindException(serialDescriptorCarrierDescriptor);
        }
        Json json5 = getJson();
        if (jsonElementCurrentObject instanceof JsonArray) {
            return new JsonTreeListDecoder(json5, (JsonArray) jsonElementCurrentObject);
        }
        throw JsonExceptionsKt.JsonDecodingException(-1, "Expected " + Reflection.getOrCreateKotlinClass(JsonArray.class) + " as the serialized body of " + descriptor.getSerialName() + ", but had " + Reflection.getOrCreateKotlinClass(jsonElementCurrentObject.getClass()));
    }

    @Override // kotlinx.serialization.internal.NamedValueDecoder
    @NotNull
    public String composeName(@NotNull String parentName, @NotNull String childName) {
        Intrinsics.checkNotNullParameter(parentName, "parentName");
        Intrinsics.checkNotNullParameter(childName, "childName");
        return childName;
    }

    @NotNull
    public abstract JsonElement currentElement(@NotNull String tag);

    @Override // kotlinx.serialization.json.JsonDecoder
    @NotNull
    public JsonElement decodeJsonElement() {
        return currentObject();
    }

    @Override // kotlinx.serialization.internal.TaggedDecoder, kotlinx.serialization.encoding.Decoder
    public boolean decodeNotNullMark() {
        return !(currentObject() instanceof JsonNull);
    }

    @Override // kotlinx.serialization.internal.TaggedDecoder, kotlinx.serialization.encoding.Decoder
    public <T> T decodeSerializableValue(@NotNull DeserializationStrategy<? extends T> deserializer) {
        Intrinsics.checkNotNullParameter(deserializer, "deserializer");
        return (T) PolymorphicKt.decodeSerializableValuePolymorphic(this, deserializer);
    }

    @Override // kotlinx.serialization.internal.TaggedDecoder
    @Nullable
    public Void decodeTaggedNull(@NotNull String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        return null;
    }

    @Override // kotlinx.serialization.internal.TaggedDecoder, kotlinx.serialization.encoding.CompositeDecoder
    public void endStructure(@NotNull SerialDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
    }

    @Override // kotlinx.serialization.json.JsonDecoder
    @NotNull
    public Json getJson() {
        return this.json;
    }

    @NotNull
    public final JsonPrimitive getPrimitiveValue(@NotNull String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        JsonElement jsonElementCurrentElement = currentElement(tag);
        JsonPrimitive jsonPrimitive = jsonElementCurrentElement instanceof JsonPrimitive ? (JsonPrimitive) jsonElementCurrentElement : null;
        if (jsonPrimitive != null) {
            return jsonPrimitive;
        }
        throw JsonExceptionsKt.JsonDecodingException(-1, "Expected JsonPrimitive at " + tag + ", found " + jsonElementCurrentElement, currentObject().toString());
    }

    @Override // kotlinx.serialization.internal.TaggedDecoder, kotlinx.serialization.encoding.Decoder, kotlinx.serialization.encoding.CompositeDecoder
    @NotNull
    public SerializersModule getSerializersModule() {
        return getJson().getSerializersModule();
    }

    @NotNull
    public JsonElement getValue() {
        return this.value;
    }

    @Override // kotlinx.serialization.internal.TaggedDecoder
    public boolean decodeTaggedBoolean(@NotNull String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        JsonPrimitive primitiveValue = getPrimitiveValue(tag);
        if (!getJson().getConfiguration().getIsLenient() && asLiteral(primitiveValue, TypedValues.Custom.S_BOOLEAN).getIsString()) {
            throw JsonExceptionsKt.JsonDecodingException(-1, "Boolean literal for key '" + tag + "' should be unquoted.\nUse 'isLenient = true' in 'Json {}` builder to accept non-compliant JSON.", currentObject().toString());
        }
        try {
            Boolean booleanOrNull = JsonElementKt.getBooleanOrNull(primitiveValue);
            if (booleanOrNull != null) {
                return booleanOrNull.booleanValue();
            }
            throw new IllegalArgumentException();
        } catch (IllegalArgumentException unused) {
            unparsedPrimitive(TypedValues.Custom.S_BOOLEAN);
            throw new KotlinNothingValueException();
        }
    }

    @Override // kotlinx.serialization.internal.TaggedDecoder
    public byte decodeTaggedByte(@NotNull String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        try {
            int i2 = JsonElementKt.getInt(getPrimitiveValue(tag));
            boolean z2 = false;
            if (-128 <= i2 && i2 <= 127) {
                z2 = true;
            }
            Byte bValueOf = z2 ? Byte.valueOf((byte) i2) : null;
            if (bValueOf != null) {
                return bValueOf.byteValue();
            }
            unparsedPrimitive("byte");
            throw new KotlinNothingValueException();
        } catch (IllegalArgumentException unused) {
            unparsedPrimitive("byte");
            throw new KotlinNothingValueException();
        }
    }

    @Override // kotlinx.serialization.internal.TaggedDecoder
    public char decodeTaggedChar(@NotNull String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        try {
            return StringsKt___StringsKt.single(getPrimitiveValue(tag).getContent());
        } catch (IllegalArgumentException unused) {
            unparsedPrimitive("char");
            throw new KotlinNothingValueException();
        }
    }

    @Override // kotlinx.serialization.internal.TaggedDecoder
    public double decodeTaggedDouble(@NotNull String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        try {
            double d3 = JsonElementKt.getDouble(getPrimitiveValue(tag));
            if (!getJson().getConfiguration().getAllowSpecialFloatingPointValues()) {
                if (!((Double.isInfinite(d3) || Double.isNaN(d3)) ? false : true)) {
                    throw JsonExceptionsKt.InvalidFloatingPointDecoded(Double.valueOf(d3), tag, currentObject().toString());
                }
            }
            return d3;
        } catch (IllegalArgumentException unused) {
            unparsedPrimitive("double");
            throw new KotlinNothingValueException();
        }
    }

    @Override // kotlinx.serialization.internal.TaggedDecoder
    public int decodeTaggedEnum(@NotNull String tag, @NotNull SerialDescriptor enumDescriptor) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(enumDescriptor, "enumDescriptor");
        return JsonNamesMapKt.getJsonNameIndexOrThrow$default(enumDescriptor, getJson(), getPrimitiveValue(tag).getContent(), null, 4, null);
    }

    @Override // kotlinx.serialization.internal.TaggedDecoder
    public float decodeTaggedFloat(@NotNull String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        try {
            float f2 = JsonElementKt.getFloat(getPrimitiveValue(tag));
            if (!getJson().getConfiguration().getAllowSpecialFloatingPointValues()) {
                if (!((Float.isInfinite(f2) || Float.isNaN(f2)) ? false : true)) {
                    throw JsonExceptionsKt.InvalidFloatingPointDecoded(Float.valueOf(f2), tag, currentObject().toString());
                }
            }
            return f2;
        } catch (IllegalArgumentException unused) {
            unparsedPrimitive(TypedValues.Custom.S_FLOAT);
            throw new KotlinNothingValueException();
        }
    }

    @Override // kotlinx.serialization.internal.TaggedDecoder
    @NotNull
    public Decoder decodeTaggedInline(@NotNull String tag, @NotNull SerialDescriptor inlineDescriptor) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(inlineDescriptor, "inlineDescriptor");
        return StreamingJsonEncoderKt.isUnsignedNumber(inlineDescriptor) ? new JsonDecoderForUnsignedTypes(new StringJsonLexer(getPrimitiveValue(tag).getContent()), getJson()) : super.decodeTaggedInline((AbstractJsonTreeDecoder) tag, inlineDescriptor);
    }

    @Override // kotlinx.serialization.internal.TaggedDecoder
    public int decodeTaggedInt(@NotNull String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        try {
            return JsonElementKt.getInt(getPrimitiveValue(tag));
        } catch (IllegalArgumentException unused) {
            unparsedPrimitive("int");
            throw new KotlinNothingValueException();
        }
    }

    @Override // kotlinx.serialization.internal.TaggedDecoder
    public long decodeTaggedLong(@NotNull String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        try {
            return JsonElementKt.getLong(getPrimitiveValue(tag));
        } catch (IllegalArgumentException unused) {
            unparsedPrimitive("long");
            throw new KotlinNothingValueException();
        }
    }

    @Override // kotlinx.serialization.internal.TaggedDecoder
    public boolean decodeTaggedNotNullMark(@NotNull String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        return currentElement(tag) != JsonNull.INSTANCE;
    }

    @Override // kotlinx.serialization.internal.TaggedDecoder
    public short decodeTaggedShort(@NotNull String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        try {
            int i2 = JsonElementKt.getInt(getPrimitiveValue(tag));
            boolean z2 = false;
            if (-32768 <= i2 && i2 <= 32767) {
                z2 = true;
            }
            Short shValueOf = z2 ? Short.valueOf((short) i2) : null;
            if (shValueOf != null) {
                return shValueOf.shortValue();
            }
            unparsedPrimitive("short");
            throw new KotlinNothingValueException();
        } catch (IllegalArgumentException unused) {
            unparsedPrimitive("short");
            throw new KotlinNothingValueException();
        }
    }

    @Override // kotlinx.serialization.internal.TaggedDecoder
    @NotNull
    public String decodeTaggedString(@NotNull String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        JsonPrimitive primitiveValue = getPrimitiveValue(tag);
        if (getJson().getConfiguration().getIsLenient() || asLiteral(primitiveValue, TypedValues.Custom.S_STRING).getIsString()) {
            if (primitiveValue instanceof JsonNull) {
                throw JsonExceptionsKt.JsonDecodingException(-1, "Unexpected 'null' value instead of string literal", currentObject().toString());
            }
            return primitiveValue.getContent();
        }
        throw JsonExceptionsKt.JsonDecodingException(-1, "String literal for key '" + tag + "' should be quoted.\nUse 'isLenient = true' in 'Json {}` builder to accept non-compliant JSON.", currentObject().toString());
    }
}

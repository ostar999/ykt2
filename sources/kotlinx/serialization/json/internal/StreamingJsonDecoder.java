package kotlinx.serialization.json.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import cn.hutool.core.text.CharPool;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.serialization.DeserializationStrategy;
import kotlinx.serialization.MissingFieldException;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.descriptors.SerialKind;
import kotlinx.serialization.encoding.AbstractDecoder;
import kotlinx.serialization.encoding.ChunkedDecoder;
import kotlinx.serialization.encoding.CompositeDecoder;
import kotlinx.serialization.encoding.Decoder;
import kotlinx.serialization.internal.AbstractPolymorphicSerializer;
import kotlinx.serialization.json.Json;
import kotlinx.serialization.json.JsonConfiguration;
import kotlinx.serialization.json.JsonDecoder;
import kotlinx.serialization.json.JsonElement;
import kotlinx.serialization.modules.SerializersModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000¾\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0010\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003:\u0001TB/\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0002J\u0018\u0010\u001f\u001a\u00020 2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u0012H\u0002J\b\u0010\"\u001a\u00020 H\u0016J\b\u0010#\u001a\u00020$H\u0016J\b\u0010%\u001a\u00020&H\u0016J\b\u0010'\u001a\u00020(H\u0016J\u0010\u0010)\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010*\u001a\u00020\u00122\u0006\u0010+\u001a\u00020\u000bH\u0016J\b\u0010,\u001a\u00020-H\u0016J\u0010\u0010.\u001a\u00020/2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u00100\u001a\u00020\u0012H\u0016J\b\u00101\u001a\u000202H\u0016J\b\u00103\u001a\u00020\u0012H\u0002J\b\u00104\u001a\u000205H\u0016J\b\u00106\u001a\u00020\u0012H\u0002J\b\u00107\u001a\u00020 H\u0016J\n\u00108\u001a\u0004\u0018\u000109H\u0016J\u0010\u0010:\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\u000bH\u0002J;\u0010;\u001a\u0002H<\"\u0004\b\u0000\u0010<2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u00122\f\u0010=\u001a\b\u0012\u0004\u0012\u0002H<0>2\b\u0010?\u001a\u0004\u0018\u0001H<H\u0016¢\u0006\u0002\u0010@J!\u0010A\u001a\u0002H<\"\u0004\b\u0000\u0010<2\f\u0010=\u001a\b\u0012\u0004\u0012\u0002H<0>H\u0016¢\u0006\u0002\u0010BJ\b\u0010C\u001a\u00020DH\u0016J\b\u0010E\u001a\u00020FH\u0016J+\u0010G\u001a\u00020\u001e2!\u0010H\u001a\u001d\u0012\u0013\u0012\u00110F¢\u0006\f\bJ\u0012\b\bK\u0012\u0004\b\b(L\u0012\u0004\u0012\u00020\u001e0IH\u0016J\b\u0010M\u001a\u00020FH\u0002J\u0010\u0010N\u001a\u00020\u001e2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010O\u001a\u00020 2\u0006\u0010P\u001a\u00020FH\u0002J\u0010\u0010Q\u001a\u00020\u001e2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0016\u0010R\u001a\u00020 *\u0004\u0018\u00010\r2\u0006\u0010S\u001a\u00020FH\u0002R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0010\u0010\b\u001a\u00020\t8\u0000X\u0081\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\u00020\u0018X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006U"}, d2 = {"Lkotlinx/serialization/json/internal/StreamingJsonDecoder;", "Lkotlinx/serialization/json/JsonDecoder;", "Lkotlinx/serialization/encoding/ChunkedDecoder;", "Lkotlinx/serialization/encoding/AbstractDecoder;", AliyunVodHttpCommon.Format.FORMAT_JSON, "Lkotlinx/serialization/json/Json;", "mode", "Lkotlinx/serialization/json/internal/WriteMode;", "lexer", "Lkotlinx/serialization/json/internal/AbstractJsonLexer;", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "discriminatorHolder", "Lkotlinx/serialization/json/internal/StreamingJsonDecoder$DiscriminatorHolder;", "(Lkotlinx/serialization/json/Json;Lkotlinx/serialization/json/internal/WriteMode;Lkotlinx/serialization/json/internal/AbstractJsonLexer;Lkotlinx/serialization/descriptors/SerialDescriptor;Lkotlinx/serialization/json/internal/StreamingJsonDecoder$DiscriminatorHolder;)V", "configuration", "Lkotlinx/serialization/json/JsonConfiguration;", "currentIndex", "", "elementMarker", "Lkotlinx/serialization/json/internal/JsonElementMarker;", "getJson", "()Lkotlinx/serialization/json/Json;", "serializersModule", "Lkotlinx/serialization/modules/SerializersModule;", "getSerializersModule", "()Lkotlinx/serialization/modules/SerializersModule;", "beginStructure", "Lkotlinx/serialization/encoding/CompositeDecoder;", "checkLeadingComma", "", "coerceInputValue", "", "index", "decodeBoolean", "decodeByte", "", "decodeChar", "", "decodeDouble", "", "decodeElementIndex", "decodeEnum", "enumDescriptor", "decodeFloat", "", "decodeInline", "Lkotlinx/serialization/encoding/Decoder;", "decodeInt", "decodeJsonElement", "Lkotlinx/serialization/json/JsonElement;", "decodeListIndex", "decodeLong", "", "decodeMapIndex", "decodeNotNullMark", "decodeNull", "", "decodeObjectIndex", "decodeSerializableElement", ExifInterface.GPS_DIRECTION_TRUE, "deserializer", "Lkotlinx/serialization/DeserializationStrategy;", "previousValue", "(Lkotlinx/serialization/descriptors/SerialDescriptor;ILkotlinx/serialization/DeserializationStrategy;Ljava/lang/Object;)Ljava/lang/Object;", "decodeSerializableValue", "(Lkotlinx/serialization/DeserializationStrategy;)Ljava/lang/Object;", "decodeShort", "", "decodeString", "", "decodeStringChunked", "consumeChunk", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "chunk", "decodeStringKey", "endStructure", "handleUnknown", "key", "skipLeftoverElements", "trySkip", "unknownKey", "DiscriminatorHolder", "kotlinx-serialization-json"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nStreamingJsonDecoder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StreamingJsonDecoder.kt\nkotlinx/serialization/json/internal/StreamingJsonDecoder\n+ 2 AbstractJsonLexer.kt\nkotlinx/serialization/json/internal/AbstractJsonLexer\n+ 3 JsonNamesMap.kt\nkotlinx/serialization/json/internal/JsonNamesMapKt\n+ 4 StreamingJsonDecoder.kt\nkotlinx/serialization/json/internal/StreamingJsonDecoderKt\n*L\n1#1,393:1\n517#2,3:394\n517#2,3:397\n105#3,15:400\n386#4,5:415\n386#4,5:420\n*S KotlinDebug\n*F\n+ 1 StreamingJsonDecoder.kt\nkotlinx/serialization/json/internal/StreamingJsonDecoder\n*L\n196#1:394,3\n197#1:397,3\n209#1:400,15\n311#1:415,5\n318#1:420,5\n*E\n"})
/* loaded from: classes8.dex */
public class StreamingJsonDecoder extends AbstractDecoder implements JsonDecoder, ChunkedDecoder {

    @NotNull
    private final JsonConfiguration configuration;
    private int currentIndex;

    @Nullable
    private DiscriminatorHolder discriminatorHolder;

    @Nullable
    private final JsonElementMarker elementMarker;

    @NotNull
    private final Json json;

    @JvmField
    @NotNull
    public final JsonReader lexer;

    @NotNull
    private final WriteMode mode;

    @NotNull
    private final SerializersModule serializersModule;

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lkotlinx/serialization/json/internal/StreamingJsonDecoder$DiscriminatorHolder;", "", "discriminatorToSkip", "", "(Ljava/lang/String;)V", "kotlinx-serialization-json"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class DiscriminatorHolder {

        @JvmField
        @Nullable
        public String discriminatorToSkip;

        public DiscriminatorHolder(@Nullable String str) {
            this.discriminatorToSkip = str;
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[WriteMode.values().length];
            try {
                iArr[WriteMode.LIST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[WriteMode.MAP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[WriteMode.POLY_OBJ.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[WriteMode.OBJ.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public StreamingJsonDecoder(@NotNull Json json, @NotNull WriteMode mode, @NotNull JsonReader lexer, @NotNull SerialDescriptor descriptor, @Nullable DiscriminatorHolder discriminatorHolder) {
        Intrinsics.checkNotNullParameter(json, "json");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(lexer, "lexer");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        this.json = json;
        this.mode = mode;
        this.lexer = lexer;
        this.serializersModule = json.getSerializersModule();
        this.currentIndex = -1;
        this.discriminatorHolder = discriminatorHolder;
        JsonConfiguration configuration = json.getConfiguration();
        this.configuration = configuration;
        this.elementMarker = configuration.getExplicitNulls() ? null : new JsonElementMarker(descriptor);
    }

    private final void checkLeadingComma() {
        if (this.lexer.peekNextToken() != 4) {
            return;
        }
        JsonReader.fail$default(this.lexer, "Unexpected leading comma", 0, null, 6, null);
        throw new KotlinNothingValueException();
    }

    private final boolean coerceInputValue(SerialDescriptor descriptor, int index) {
        String strPeekString;
        Json json = this.json;
        SerialDescriptor elementDescriptor = descriptor.getElementDescriptor(index);
        if (!elementDescriptor.isNullable() && this.lexer.tryConsumeNull(true)) {
            return true;
        }
        if (!Intrinsics.areEqual(elementDescriptor.getKind(), SerialKind.ENUM.INSTANCE) || ((elementDescriptor.isNullable() && this.lexer.tryConsumeNull(false)) || (strPeekString = this.lexer.peekString(this.configuration.getIsLenient())) == null || JsonNamesMapKt.getJsonNameIndex(elementDescriptor, json, strPeekString) != -3)) {
            return false;
        }
        this.lexer.consumeString();
        return true;
    }

    private final int decodeListIndex() {
        boolean zTryConsumeComma = this.lexer.tryConsumeComma();
        if (!this.lexer.canConsumeValue()) {
            if (!zTryConsumeComma) {
                return -1;
            }
            JsonReader.fail$default(this.lexer, "Unexpected trailing comma", 0, null, 6, null);
            throw new KotlinNothingValueException();
        }
        int i2 = this.currentIndex;
        if (i2 != -1 && !zTryConsumeComma) {
            JsonReader.fail$default(this.lexer, "Expected end of the array or comma", 0, null, 6, null);
            throw new KotlinNothingValueException();
        }
        int i3 = i2 + 1;
        this.currentIndex = i3;
        return i3;
    }

    private final int decodeMapIndex() {
        int i2 = this.currentIndex;
        boolean zTryConsumeComma = false;
        boolean z2 = i2 % 2 != 0;
        if (!z2) {
            this.lexer.consumeNextToken(':');
        } else if (i2 != -1) {
            zTryConsumeComma = this.lexer.tryConsumeComma();
        }
        if (!this.lexer.canConsumeValue()) {
            if (!zTryConsumeComma) {
                return -1;
            }
            JsonReader.fail$default(this.lexer, "Expected '}', but had ',' instead", 0, null, 6, null);
            throw new KotlinNothingValueException();
        }
        if (z2) {
            if (this.currentIndex == -1) {
                JsonReader jsonReader = this.lexer;
                boolean z3 = !zTryConsumeComma;
                int i3 = jsonReader.currentPosition;
                if (!z3) {
                    JsonReader.fail$default(jsonReader, "Unexpected trailing comma", i3, null, 4, null);
                    throw new KotlinNothingValueException();
                }
            } else {
                JsonReader jsonReader2 = this.lexer;
                int i4 = jsonReader2.currentPosition;
                if (!zTryConsumeComma) {
                    JsonReader.fail$default(jsonReader2, "Expected comma after the key-value pair", i4, null, 4, null);
                    throw new KotlinNothingValueException();
                }
            }
        }
        int i5 = this.currentIndex + 1;
        this.currentIndex = i5;
        return i5;
    }

    private final int decodeObjectIndex(SerialDescriptor descriptor) {
        boolean zTryConsumeComma;
        boolean zTryConsumeComma2 = this.lexer.tryConsumeComma();
        while (this.lexer.canConsumeValue()) {
            String strDecodeStringKey = decodeStringKey();
            this.lexer.consumeNextToken(':');
            int jsonNameIndex = JsonNamesMapKt.getJsonNameIndex(descriptor, this.json, strDecodeStringKey);
            boolean z2 = false;
            if (jsonNameIndex == -3) {
                z2 = true;
                zTryConsumeComma = false;
            } else {
                if (!this.configuration.getCoerceInputValues() || !coerceInputValue(descriptor, jsonNameIndex)) {
                    JsonElementMarker jsonElementMarker = this.elementMarker;
                    if (jsonElementMarker != null) {
                        jsonElementMarker.mark$kotlinx_serialization_json(jsonNameIndex);
                    }
                    return jsonNameIndex;
                }
                zTryConsumeComma = this.lexer.tryConsumeComma();
            }
            zTryConsumeComma2 = z2 ? handleUnknown(strDecodeStringKey) : zTryConsumeComma;
        }
        if (zTryConsumeComma2) {
            JsonReader.fail$default(this.lexer, "Unexpected trailing comma", 0, null, 6, null);
            throw new KotlinNothingValueException();
        }
        JsonElementMarker jsonElementMarker2 = this.elementMarker;
        if (jsonElementMarker2 != null) {
            return jsonElementMarker2.nextUnmarkedIndex$kotlinx_serialization_json();
        }
        return -1;
    }

    private final String decodeStringKey() {
        return this.configuration.getIsLenient() ? this.lexer.consumeStringLenientNotNull() : this.lexer.consumeKeyString();
    }

    private final boolean handleUnknown(String key) {
        if (this.configuration.getIgnoreUnknownKeys() || trySkip(this.discriminatorHolder, key)) {
            this.lexer.skipElement(this.configuration.getIsLenient());
        } else {
            this.lexer.failOnUnknownKey(key);
        }
        return this.lexer.tryConsumeComma();
    }

    private final void skipLeftoverElements(SerialDescriptor descriptor) {
        while (decodeElementIndex(descriptor) != -1) {
        }
    }

    private final boolean trySkip(DiscriminatorHolder discriminatorHolder, String str) {
        if (discriminatorHolder == null || !Intrinsics.areEqual(discriminatorHolder.discriminatorToSkip, str)) {
            return false;
        }
        discriminatorHolder.discriminatorToSkip = null;
        return true;
    }

    @Override // kotlinx.serialization.encoding.AbstractDecoder, kotlinx.serialization.encoding.Decoder
    @NotNull
    public CompositeDecoder beginStructure(@NotNull SerialDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        WriteMode writeModeSwitchMode = WriteModeKt.switchMode(this.json, descriptor);
        this.lexer.path.pushDescriptor(descriptor);
        this.lexer.consumeNextToken(writeModeSwitchMode.begin);
        checkLeadingComma();
        int i2 = WhenMappings.$EnumSwitchMapping$0[writeModeSwitchMode.ordinal()];
        return (i2 == 1 || i2 == 2 || i2 == 3) ? new StreamingJsonDecoder(this.json, writeModeSwitchMode, this.lexer, descriptor, this.discriminatorHolder) : (this.mode == writeModeSwitchMode && this.json.getConfiguration().getExplicitNulls()) ? this : new StreamingJsonDecoder(this.json, writeModeSwitchMode, this.lexer, descriptor, this.discriminatorHolder);
    }

    @Override // kotlinx.serialization.encoding.AbstractDecoder, kotlinx.serialization.encoding.Decoder
    public boolean decodeBoolean() {
        return this.configuration.getIsLenient() ? this.lexer.consumeBooleanLenient() : this.lexer.consumeBoolean();
    }

    @Override // kotlinx.serialization.encoding.AbstractDecoder, kotlinx.serialization.encoding.Decoder
    public byte decodeByte() {
        long jConsumeNumericLiteral = this.lexer.consumeNumericLiteral();
        byte b3 = (byte) jConsumeNumericLiteral;
        if (jConsumeNumericLiteral == b3) {
            return b3;
        }
        JsonReader.fail$default(this.lexer, "Failed to parse byte for input '" + jConsumeNumericLiteral + CharPool.SINGLE_QUOTE, 0, null, 6, null);
        throw new KotlinNothingValueException();
    }

    @Override // kotlinx.serialization.encoding.AbstractDecoder, kotlinx.serialization.encoding.Decoder
    public char decodeChar() {
        String strConsumeStringLenient = this.lexer.consumeStringLenient();
        if (strConsumeStringLenient.length() == 1) {
            return strConsumeStringLenient.charAt(0);
        }
        JsonReader.fail$default(this.lexer, "Expected single char, but got '" + strConsumeStringLenient + CharPool.SINGLE_QUOTE, 0, null, 6, null);
        throw new KotlinNothingValueException();
    }

    @Override // kotlinx.serialization.encoding.AbstractDecoder, kotlinx.serialization.encoding.Decoder
    public double decodeDouble() throws NumberFormatException {
        JsonReader jsonReader = this.lexer;
        String strConsumeStringLenient = jsonReader.consumeStringLenient();
        try {
            double d3 = Double.parseDouble(strConsumeStringLenient);
            if (!this.json.getConfiguration().getAllowSpecialFloatingPointValues()) {
                if (!((Double.isInfinite(d3) || Double.isNaN(d3)) ? false : true)) {
                    JsonExceptionsKt.throwInvalidFloatingPointDecoded(this.lexer, Double.valueOf(d3));
                    throw new KotlinNothingValueException();
                }
            }
            return d3;
        } catch (IllegalArgumentException unused) {
            JsonReader.fail$default(jsonReader, "Failed to parse type 'double' for input '" + strConsumeStringLenient + CharPool.SINGLE_QUOTE, 0, null, 6, null);
            throw new KotlinNothingValueException();
        }
    }

    @Override // kotlinx.serialization.encoding.CompositeDecoder
    public int decodeElementIndex(@NotNull SerialDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        int i2 = WhenMappings.$EnumSwitchMapping$0[this.mode.ordinal()];
        int iDecodeListIndex = i2 != 2 ? i2 != 4 ? decodeListIndex() : decodeObjectIndex(descriptor) : decodeMapIndex();
        if (this.mode != WriteMode.MAP) {
            this.lexer.path.updateDescriptorIndex(iDecodeListIndex);
        }
        return iDecodeListIndex;
    }

    @Override // kotlinx.serialization.encoding.AbstractDecoder, kotlinx.serialization.encoding.Decoder
    public int decodeEnum(@NotNull SerialDescriptor enumDescriptor) {
        Intrinsics.checkNotNullParameter(enumDescriptor, "enumDescriptor");
        return JsonNamesMapKt.getJsonNameIndexOrThrow(enumDescriptor, this.json, decodeString(), " at path " + this.lexer.path.getPath());
    }

    @Override // kotlinx.serialization.encoding.AbstractDecoder, kotlinx.serialization.encoding.Decoder
    public float decodeFloat() throws NumberFormatException {
        JsonReader jsonReader = this.lexer;
        String strConsumeStringLenient = jsonReader.consumeStringLenient();
        try {
            float f2 = Float.parseFloat(strConsumeStringLenient);
            if (!this.json.getConfiguration().getAllowSpecialFloatingPointValues()) {
                if (!((Float.isInfinite(f2) || Float.isNaN(f2)) ? false : true)) {
                    JsonExceptionsKt.throwInvalidFloatingPointDecoded(this.lexer, Float.valueOf(f2));
                    throw new KotlinNothingValueException();
                }
            }
            return f2;
        } catch (IllegalArgumentException unused) {
            JsonReader.fail$default(jsonReader, "Failed to parse type '" + TypedValues.Custom.S_FLOAT + "' for input '" + strConsumeStringLenient + CharPool.SINGLE_QUOTE, 0, null, 6, null);
            throw new KotlinNothingValueException();
        }
    }

    @Override // kotlinx.serialization.encoding.AbstractDecoder, kotlinx.serialization.encoding.Decoder
    @NotNull
    public Decoder decodeInline(@NotNull SerialDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return StreamingJsonEncoderKt.isUnsignedNumber(descriptor) ? new JsonDecoderForUnsignedTypes(this.lexer, this.json) : super.decodeInline(descriptor);
    }

    @Override // kotlinx.serialization.encoding.AbstractDecoder, kotlinx.serialization.encoding.Decoder
    public int decodeInt() {
        long jConsumeNumericLiteral = this.lexer.consumeNumericLiteral();
        int i2 = (int) jConsumeNumericLiteral;
        if (jConsumeNumericLiteral == i2) {
            return i2;
        }
        JsonReader.fail$default(this.lexer, "Failed to parse int for input '" + jConsumeNumericLiteral + CharPool.SINGLE_QUOTE, 0, null, 6, null);
        throw new KotlinNothingValueException();
    }

    @Override // kotlinx.serialization.json.JsonDecoder
    @NotNull
    public JsonElement decodeJsonElement() {
        return new JsonTreeReader(this.json.getConfiguration(), this.lexer).read();
    }

    @Override // kotlinx.serialization.encoding.AbstractDecoder, kotlinx.serialization.encoding.Decoder
    public long decodeLong() {
        return this.lexer.consumeNumericLiteral();
    }

    @Override // kotlinx.serialization.encoding.AbstractDecoder, kotlinx.serialization.encoding.Decoder
    public boolean decodeNotNullMark() {
        JsonElementMarker jsonElementMarker = this.elementMarker;
        return ((jsonElementMarker != null ? jsonElementMarker.getIsUnmarkedNull() : false) || JsonReader.tryConsumeNull$default(this.lexer, false, 1, null)) ? false : true;
    }

    @Override // kotlinx.serialization.encoding.AbstractDecoder, kotlinx.serialization.encoding.Decoder
    @Nullable
    public Void decodeNull() {
        return null;
    }

    @Override // kotlinx.serialization.encoding.AbstractDecoder, kotlinx.serialization.encoding.CompositeDecoder
    public <T> T decodeSerializableElement(@NotNull SerialDescriptor descriptor, int index, @NotNull DeserializationStrategy<? extends T> deserializer, @Nullable T previousValue) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(deserializer, "deserializer");
        boolean z2 = this.mode == WriteMode.MAP && (index & 1) == 0;
        if (z2) {
            this.lexer.path.resetCurrentMapKey();
        }
        T t2 = (T) super.decodeSerializableElement(descriptor, index, deserializer, previousValue);
        if (z2) {
            this.lexer.path.updateCurrentMapKey(t2);
        }
        return t2;
    }

    @Override // kotlinx.serialization.encoding.AbstractDecoder, kotlinx.serialization.encoding.Decoder
    public <T> T decodeSerializableValue(@NotNull DeserializationStrategy<? extends T> deserializer) {
        Intrinsics.checkNotNullParameter(deserializer, "deserializer");
        try {
            if ((deserializer instanceof AbstractPolymorphicSerializer) && !this.json.getConfiguration().getUseArrayPolymorphism()) {
                String strClassDiscriminator = PolymorphicKt.classDiscriminator(deserializer.getDescriptor(), this.json);
                String strConsumeLeadingMatchingValue = this.lexer.consumeLeadingMatchingValue(strClassDiscriminator, this.configuration.getIsLenient());
                DeserializationStrategy<T> deserializationStrategyFindPolymorphicSerializerOrNull = strConsumeLeadingMatchingValue != null ? ((AbstractPolymorphicSerializer) deserializer).findPolymorphicSerializerOrNull(this, strConsumeLeadingMatchingValue) : null;
                if (deserializationStrategyFindPolymorphicSerializerOrNull == null) {
                    return (T) PolymorphicKt.decodeSerializableValuePolymorphic(this, deserializer);
                }
                this.discriminatorHolder = new DiscriminatorHolder(strClassDiscriminator);
                return deserializationStrategyFindPolymorphicSerializerOrNull.deserialize(this);
            }
            return deserializer.deserialize(this);
        } catch (MissingFieldException e2) {
            throw new MissingFieldException(e2.getMissingFields(), e2.getMessage() + " at path: " + this.lexer.path.getPath(), e2);
        }
    }

    @Override // kotlinx.serialization.encoding.AbstractDecoder, kotlinx.serialization.encoding.Decoder
    public short decodeShort() {
        long jConsumeNumericLiteral = this.lexer.consumeNumericLiteral();
        short s2 = (short) jConsumeNumericLiteral;
        if (jConsumeNumericLiteral == s2) {
            return s2;
        }
        JsonReader.fail$default(this.lexer, "Failed to parse short for input '" + jConsumeNumericLiteral + CharPool.SINGLE_QUOTE, 0, null, 6, null);
        throw new KotlinNothingValueException();
    }

    @Override // kotlinx.serialization.encoding.AbstractDecoder, kotlinx.serialization.encoding.Decoder
    @NotNull
    public String decodeString() {
        return this.configuration.getIsLenient() ? this.lexer.consumeStringLenientNotNull() : this.lexer.consumeString();
    }

    @Override // kotlinx.serialization.encoding.ChunkedDecoder
    public void decodeStringChunked(@NotNull Function1<? super String, Unit> consumeChunk) {
        Intrinsics.checkNotNullParameter(consumeChunk, "consumeChunk");
        this.lexer.consumeStringChunked(this.configuration.getIsLenient(), consumeChunk);
    }

    @Override // kotlinx.serialization.encoding.AbstractDecoder, kotlinx.serialization.encoding.CompositeDecoder
    public void endStructure(@NotNull SerialDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (this.json.getConfiguration().getIgnoreUnknownKeys() && descriptor.getElementsCount() == 0) {
            skipLeftoverElements(descriptor);
        }
        this.lexer.consumeNextToken(this.mode.end);
        this.lexer.path.popDescriptor();
    }

    @Override // kotlinx.serialization.json.JsonDecoder
    @NotNull
    public final Json getJson() {
        return this.json;
    }

    @Override // kotlinx.serialization.encoding.Decoder, kotlinx.serialization.encoding.CompositeDecoder
    @NotNull
    public SerializersModule getSerializersModule() {
        return this.serializersModule;
    }
}

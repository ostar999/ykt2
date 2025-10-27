package kotlinx.serialization.json.internal;

import androidx.exifinterface.media.ExifInterface;
import cn.hutool.core.text.CharPool;
import com.tencent.open.SocialConstants;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.DeserializationStrategy;
import kotlinx.serialization.InternalSerializationApi;
import kotlinx.serialization.json.Json;
import kotlinx.serialization.json.JsonElement;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a(\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007\u001a9\u0010\b\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\u00020\t2\u0006\u0010\n\u001a\u00020\u00072\u0017\u0010\u000b\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u0002H\u00020\f¢\u0006\u0002\b\rH\u0082\b¢\u0006\u0002\u0010\u000e¨\u0006\u000f"}, d2 = {"decodeStringToJsonTree", "Lkotlinx/serialization/json/JsonElement;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/serialization/json/Json;", "deserializer", "Lkotlinx/serialization/DeserializationStrategy;", SocialConstants.PARAM_SOURCE, "", "parseString", "Lkotlinx/serialization/json/internal/AbstractJsonLexer;", "expectedType", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/serialization/json/internal/AbstractJsonLexer;Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlinx-serialization-json"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class StreamingJsonDecoderKt {
    @InternalSerializationApi
    @NotNull
    public static final <T> JsonElement decodeStringToJsonTree(@NotNull Json json, @NotNull DeserializationStrategy<? extends T> deserializer, @NotNull String source) {
        Intrinsics.checkNotNullParameter(json, "<this>");
        Intrinsics.checkNotNullParameter(deserializer, "deserializer");
        Intrinsics.checkNotNullParameter(source, "source");
        StringJsonLexer stringJsonLexer = new StringJsonLexer(source);
        JsonElement jsonElementDecodeJsonElement = new StreamingJsonDecoder(json, WriteMode.OBJ, stringJsonLexer, deserializer.getDescriptor(), null).decodeJsonElement();
        stringJsonLexer.expectEof();
        return jsonElementDecodeJsonElement;
    }

    private static final <T> T parseString(JsonReader jsonReader, String str, Function1<? super String, ? extends T> function1) {
        String strConsumeStringLenient = jsonReader.consumeStringLenient();
        try {
            return function1.invoke(strConsumeStringLenient);
        } catch (IllegalArgumentException unused) {
            JsonReader.fail$default(jsonReader, "Failed to parse type '" + str + "' for input '" + strConsumeStringLenient + CharPool.SINGLE_QUOTE, 0, null, 6, null);
            throw new KotlinNothingValueException();
        }
    }
}

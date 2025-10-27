package kotlinx.serialization.json.internal;

import androidx.exifinterface.media.ExifInterface;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.SerializationStrategy;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.json.Json;
import kotlinx.serialization.json.JsonElement;
import kotlinx.serialization.json.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0012\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\u0010\bJ?\u0010\u000e\u001a\u00020\u0007\"\b\b\u0000\u0010\u000f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u00162\b\u0010\u0017\u001a\u0004\u0018\u0001H\u000fH\u0016¢\u0006\u0002\u0010\u0018J\b\u0010\u0019\u001a\u00020\u0006H\u0016J\u0018\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u0006H\u0016R \u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00060\nX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u001d"}, d2 = {"Lkotlinx/serialization/json/internal/JsonTreeEncoder;", "Lkotlinx/serialization/json/internal/AbstractJsonTreeEncoder;", AliyunVodHttpCommon.Format.FORMAT_JSON, "Lkotlinx/serialization/json/Json;", "nodeConsumer", "Lkotlin/Function1;", "Lkotlinx/serialization/json/JsonElement;", "", "(Lkotlinx/serialization/json/Json;Lkotlin/jvm/functions/Function1;)V", "content", "", "", "getContent", "()Ljava/util/Map;", "encodeNullableSerializableElement", ExifInterface.GPS_DIRECTION_TRUE, "", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "index", "", "serializer", "Lkotlinx/serialization/SerializationStrategy;", "value", "(Lkotlinx/serialization/descriptors/SerialDescriptor;ILkotlinx/serialization/SerializationStrategy;Ljava/lang/Object;)V", "getCurrent", "putElement", "key", "element", "kotlinx-serialization-json"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
class JsonTreeEncoder extends AbstractJsonTreeEncoder {

    @NotNull
    private final Map<String, JsonElement> content;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsonTreeEncoder(@NotNull Json json, @NotNull Function1<? super JsonElement, Unit> nodeConsumer) {
        super(json, nodeConsumer, null);
        Intrinsics.checkNotNullParameter(json, "json");
        Intrinsics.checkNotNullParameter(nodeConsumer, "nodeConsumer");
        this.content = new LinkedHashMap();
    }

    @Override // kotlinx.serialization.internal.TaggedEncoder, kotlinx.serialization.encoding.CompositeEncoder
    public <T> void encodeNullableSerializableElement(@NotNull SerialDescriptor descriptor, int index, @NotNull SerializationStrategy<? super T> serializer, @Nullable T value) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(serializer, "serializer");
        if (value != null || this.configuration.getExplicitNulls()) {
            super.encodeNullableSerializableElement(descriptor, index, serializer, value);
        }
    }

    @NotNull
    public final Map<String, JsonElement> getContent() {
        return this.content;
    }

    @Override // kotlinx.serialization.json.internal.AbstractJsonTreeEncoder
    @NotNull
    public JsonElement getCurrent() {
        return new JsonObject(this.content);
    }

    @Override // kotlinx.serialization.json.internal.AbstractJsonTreeEncoder
    public void putElement(@NotNull String key, @NotNull JsonElement element) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(element, "element");
        this.content.put(key, element);
    }
}

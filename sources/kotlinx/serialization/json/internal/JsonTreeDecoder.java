package kotlinx.serialization.json.internal;

import com.aliyun.auth.common.AliyunVodHttpCommon;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.serialization.descriptors.PolymorphicKind;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.descriptors.SerialKind;
import kotlinx.serialization.encoding.CompositeDecoder;
import kotlinx.serialization.internal.JsonInternalDependenciesKt;
import kotlinx.serialization.json.Json;
import kotlinx.serialization.json.JsonElement;
import kotlinx.serialization.json.JsonElementKt;
import kotlinx.serialization.json.JsonNamingStrategy;
import kotlinx.serialization.json.JsonNull;
import kotlinx.serialization.json.JsonObject;
import kotlinx.serialization.json.JsonPrimitive;
import kotlinx.serialization.json.JsonSchemaCacheKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\b\u0012\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\u0018\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u000eH\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\tH\u0016J \u0010\u0016\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u0007H\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0017\u001a\u00020\u0007H\u0014J\u0010\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\tH\u0016J\b\u0010\u001b\u001a\u00020\fH\u0016J\u0018\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u000eH\u0014J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0012\u001a\u00020\tH\u0016R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001f"}, d2 = {"Lkotlinx/serialization/json/internal/JsonTreeDecoder;", "Lkotlinx/serialization/json/internal/AbstractJsonTreeDecoder;", AliyunVodHttpCommon.Format.FORMAT_JSON, "Lkotlinx/serialization/json/Json;", "value", "Lkotlinx/serialization/json/JsonObject;", "polyDiscriminator", "", "polyDescriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "(Lkotlinx/serialization/json/Json;Lkotlinx/serialization/json/JsonObject;Ljava/lang/String;Lkotlinx/serialization/descriptors/SerialDescriptor;)V", "forceNull", "", "position", "", "getValue", "()Lkotlinx/serialization/json/JsonObject;", "absenceIsNull", "descriptor", "index", "beginStructure", "Lkotlinx/serialization/encoding/CompositeDecoder;", "coerceInputValue", "tag", "currentElement", "Lkotlinx/serialization/json/JsonElement;", "decodeElementIndex", "decodeNotNullMark", "elementName", "endStructure", "", "kotlinx-serialization-json"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nTreeJsonDecoder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TreeJsonDecoder.kt\nkotlinx/serialization/json/internal/JsonTreeDecoder\n+ 2 JsonNamesMap.kt\nkotlinx/serialization/json/internal/JsonNamesMapKt\n+ 3 JsonNamesMap.kt\nkotlinx/serialization/json/internal/JsonNamesMapKt$tryCoerceValue$1\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,327:1\n99#2,17:328\n116#2,4:346\n103#3:345\n1#4:350\n*S KotlinDebug\n*F\n+ 1 TreeJsonDecoder.kt\nkotlinx/serialization/json/internal/JsonTreeDecoder\n*L\n196#1:328,17\n196#1:346,4\n196#1:345\n*E\n"})
/* loaded from: classes8.dex */
class JsonTreeDecoder extends AbstractJsonTreeDecoder {
    private boolean forceNull;

    @Nullable
    private final SerialDescriptor polyDescriptor;

    @Nullable
    private final String polyDiscriminator;
    private int position;

    @NotNull
    private final JsonObject value;

    public /* synthetic */ JsonTreeDecoder(Json json, JsonObject jsonObject, String str, SerialDescriptor serialDescriptor, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(json, jsonObject, (i2 & 4) != 0 ? null : str, (i2 & 8) != 0 ? null : serialDescriptor);
    }

    private final boolean absenceIsNull(SerialDescriptor descriptor, int index) {
        boolean z2 = (getJson().getConfiguration().getExplicitNulls() || descriptor.isElementOptional(index) || !descriptor.getElementDescriptor(index).isNullable()) ? false : true;
        this.forceNull = z2;
        return z2;
    }

    private final boolean coerceInputValue(SerialDescriptor descriptor, int index, String tag) {
        Json json = getJson();
        SerialDescriptor elementDescriptor = descriptor.getElementDescriptor(index);
        if (!elementDescriptor.isNullable() && (currentElement(tag) instanceof JsonNull)) {
            return true;
        }
        if (Intrinsics.areEqual(elementDescriptor.getKind(), SerialKind.ENUM.INSTANCE) && (!elementDescriptor.isNullable() || !(currentElement(tag) instanceof JsonNull))) {
            JsonElement jsonElementCurrentElement = currentElement(tag);
            JsonPrimitive jsonPrimitive = jsonElementCurrentElement instanceof JsonPrimitive ? (JsonPrimitive) jsonElementCurrentElement : null;
            String contentOrNull = jsonPrimitive != null ? JsonElementKt.getContentOrNull(jsonPrimitive) : null;
            if (contentOrNull != null && JsonNamesMapKt.getJsonNameIndex(elementDescriptor, json, contentOrNull) == -3) {
                return true;
            }
        }
        return false;
    }

    @Override // kotlinx.serialization.json.internal.AbstractJsonTreeDecoder, kotlinx.serialization.internal.TaggedDecoder, kotlinx.serialization.encoding.Decoder
    @NotNull
    public CompositeDecoder beginStructure(@NotNull SerialDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return descriptor == this.polyDescriptor ? this : super.beginStructure(descriptor);
    }

    @Override // kotlinx.serialization.json.internal.AbstractJsonTreeDecoder
    @NotNull
    public JsonElement currentElement(@NotNull String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        return (JsonElement) MapsKt__MapsKt.getValue(getValue(), tag);
    }

    @Override // kotlinx.serialization.encoding.CompositeDecoder
    public int decodeElementIndex(@NotNull SerialDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        while (this.position < descriptor.getElementsCount()) {
            int i2 = this.position;
            this.position = i2 + 1;
            String tag = getTag(descriptor, i2);
            int i3 = this.position - 1;
            this.forceNull = false;
            if (getValue().containsKey((Object) tag) || absenceIsNull(descriptor, i3)) {
                if (!this.configuration.getCoerceInputValues() || !coerceInputValue(descriptor, i3, tag)) {
                    return i3;
                }
            }
        }
        return -1;
    }

    @Override // kotlinx.serialization.json.internal.AbstractJsonTreeDecoder, kotlinx.serialization.internal.TaggedDecoder, kotlinx.serialization.encoding.Decoder
    public boolean decodeNotNullMark() {
        return !this.forceNull && super.decodeNotNullMark();
    }

    @Override // kotlinx.serialization.internal.NamedValueDecoder
    @NotNull
    public String elementName(@NotNull SerialDescriptor descriptor, int index) {
        Object next;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        JsonNamingStrategy jsonNamingStrategyNamingStrategy = JsonNamesMapKt.namingStrategy(descriptor, getJson());
        String elementName = descriptor.getElementName(index);
        if (jsonNamingStrategyNamingStrategy == null && (!this.configuration.getUseAlternativeNames() || getValue().keySet().contains(elementName))) {
            return elementName;
        }
        Map<String, Integer> mapDeserializationNamesMap = JsonNamesMapKt.deserializationNamesMap(getJson(), descriptor);
        Iterator<T> it = getValue().keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            Integer num = mapDeserializationNamesMap.get((String) next);
            if (num != null && num.intValue() == index) {
                break;
            }
        }
        String str = (String) next;
        if (str != null) {
            return str;
        }
        String strSerialNameForJson = jsonNamingStrategyNamingStrategy != null ? jsonNamingStrategyNamingStrategy.serialNameForJson(descriptor, index, elementName) : null;
        return strSerialNameForJson == null ? elementName : strSerialNameForJson;
    }

    @Override // kotlinx.serialization.json.internal.AbstractJsonTreeDecoder, kotlinx.serialization.internal.TaggedDecoder, kotlinx.serialization.encoding.CompositeDecoder
    public void endStructure(@NotNull SerialDescriptor descriptor) {
        Set<String> setPlus;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (this.configuration.getIgnoreUnknownKeys() || (descriptor.getKind() instanceof PolymorphicKind)) {
            return;
        }
        JsonNamingStrategy jsonNamingStrategyNamingStrategy = JsonNamesMapKt.namingStrategy(descriptor, getJson());
        if (jsonNamingStrategyNamingStrategy == null && !this.configuration.getUseAlternativeNames()) {
            setPlus = JsonInternalDependenciesKt.jsonCachedSerialNames(descriptor);
        } else if (jsonNamingStrategyNamingStrategy != null) {
            setPlus = JsonNamesMapKt.deserializationNamesMap(getJson(), descriptor).keySet();
        } else {
            Set<String> setJsonCachedSerialNames = JsonInternalDependenciesKt.jsonCachedSerialNames(descriptor);
            Map map = (Map) JsonSchemaCacheKt.getSchemaCache(getJson()).get(descriptor, JsonNamesMapKt.getJsonDeserializationNamesKey());
            Set setKeySet = map != null ? map.keySet() : null;
            if (setKeySet == null) {
                setKeySet = SetsKt__SetsKt.emptySet();
            }
            setPlus = SetsKt___SetsKt.plus((Set) setJsonCachedSerialNames, (Iterable) setKeySet);
        }
        for (String str : getValue().keySet()) {
            if (!setPlus.contains(str) && !Intrinsics.areEqual(str, this.polyDiscriminator)) {
                throw JsonExceptionsKt.UnknownKeyException(str, getValue().toString());
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsonTreeDecoder(@NotNull Json json, @NotNull JsonObject value, @Nullable String str, @Nullable SerialDescriptor serialDescriptor) {
        super(json, value, null);
        Intrinsics.checkNotNullParameter(json, "json");
        Intrinsics.checkNotNullParameter(value, "value");
        this.value = value;
        this.polyDiscriminator = str;
        this.polyDescriptor = serialDescriptor;
    }

    @Override // kotlinx.serialization.json.internal.AbstractJsonTreeDecoder
    @NotNull
    public JsonObject getValue() {
        return this.value;
    }
}

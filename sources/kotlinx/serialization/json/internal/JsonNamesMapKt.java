package kotlinx.serialization.json.internal;

import cn.hutool.core.text.CharPool;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.serialization.SerializationException;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.descriptors.SerialKind;
import kotlinx.serialization.descriptors.StructureKind;
import kotlinx.serialization.json.Json;
import kotlinx.serialization.json.JsonNames;
import kotlinx.serialization.json.JsonNamingStrategy;
import kotlinx.serialization.json.JsonSchemaCacheKt;
import kotlinx.serialization.json.internal.DescriptorSchemaCache;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000T\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u001a \u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002\u001a \u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002*\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u000eH\u0000\u001a\u001c\u0010\u0013\u001a\u00020\u0003*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0004H\u0000\u001a\u001c\u0010\u0015\u001a\u00020\u0004*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0003H\u0000\u001a&\u0010\u0017\u001a\u00020\u0004*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00032\b\b\u0002\u0010\u0018\u001a\u00020\u0003H\u0000\u001a\u0016\u0010\u0019\u001a\u0004\u0018\u00010\u001a*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0000\u001a'\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00030\n*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001aH\u0000¢\u0006\u0002\u0010\u001d\u001a[\u0010\u001e\u001a\u00020\u001f*\u00020\u00102\u0006\u0010 \u001a\u00020\u000e2!\u0010!\u001a\u001d\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b#\u0012\b\b\u0016\u0012\u0004\b\b($\u0012\u0004\u0012\u00020\u001f0\"2\u000e\u0010%\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030&2\u000e\b\u0002\u0010'\u001a\b\u0012\u0004\u0012\u00020(0&H\u0080\bø\u0001\u0000\".\u0010\u0000\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00020\u00018\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\b\"(\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\n0\u00018\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u000b\u0010\u0006\u001a\u0004\b\f\u0010\b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006)"}, d2 = {"JsonDeserializationNamesKey", "Lkotlinx/serialization/json/internal/DescriptorSchemaCache$Key;", "", "", "", "getJsonDeserializationNamesKey$annotations", "()V", "getJsonDeserializationNamesKey", "()Lkotlinx/serialization/json/internal/DescriptorSchemaCache$Key;", "JsonSerializationNamesKey", "", "getJsonSerializationNamesKey$annotations", "getJsonSerializationNamesKey", "buildDeserializationNamesMap", "Lkotlinx/serialization/descriptors/SerialDescriptor;", AliyunVodHttpCommon.Format.FORMAT_JSON, "Lkotlinx/serialization/json/Json;", "deserializationNamesMap", "descriptor", "getJsonElementName", "index", "getJsonNameIndex", "name", "getJsonNameIndexOrThrow", "suffix", "namingStrategy", "Lkotlinx/serialization/json/JsonNamingStrategy;", "serializationNamesIndices", DBHelpTool.RecordEntry.COLUMN_NAME_STRATEGY, "(Lkotlinx/serialization/descriptors/SerialDescriptor;Lkotlinx/serialization/json/Json;Lkotlinx/serialization/json/JsonNamingStrategy;)[Ljava/lang/String;", "tryCoerceValue", "", "elementDescriptor", "peekNull", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "consume", "peekString", "Lkotlin/Function0;", "onEnumCoercing", "", "kotlinx-serialization-json"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nJsonNamesMap.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonNamesMap.kt\nkotlinx/serialization/json/internal/JsonNamesMapKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,121:1\n800#2,11:122\n13579#3,2:133\n1#4:135\n*S KotlinDebug\n*F\n+ 1 JsonNamesMap.kt\nkotlinx/serialization/json/internal/JsonNamesMapKt\n*L\n35#1:122,11\n35#1:133,2\n*E\n"})
/* loaded from: classes8.dex */
public final class JsonNamesMapKt {

    @NotNull
    private static final DescriptorSchemaCache.Key<Map<String, Integer>> JsonDeserializationNamesKey = new DescriptorSchemaCache.Key<>();

    @NotNull
    private static final DescriptorSchemaCache.Key<String[]> JsonSerializationNamesKey = new DescriptorSchemaCache.Key<>();

    /* JADX INFO: Access modifiers changed from: private */
    public static final Map<String, Integer> buildDeserializationNamesMap(SerialDescriptor serialDescriptor, Json json) {
        String[] strArr;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        JsonNamingStrategy jsonNamingStrategyNamingStrategy = namingStrategy(serialDescriptor, json);
        int elementsCount = serialDescriptor.getElementsCount();
        for (int i2 = 0; i2 < elementsCount; i2++) {
            List<Annotation> elementAnnotations = serialDescriptor.getElementAnnotations(i2);
            ArrayList arrayList = new ArrayList();
            for (Object obj : elementAnnotations) {
                if (obj instanceof JsonNames) {
                    arrayList.add(obj);
                }
            }
            JsonNames jsonNames = (JsonNames) CollectionsKt___CollectionsKt.singleOrNull((List) arrayList);
            if (jsonNames != null && (strArr = jsonNames.get_names()) != null) {
                for (String str : strArr) {
                    buildDeserializationNamesMap$putOrThrow(linkedHashMap, serialDescriptor, str, i2);
                }
            }
            if (jsonNamingStrategyNamingStrategy != null) {
                buildDeserializationNamesMap$putOrThrow(linkedHashMap, serialDescriptor, jsonNamingStrategyNamingStrategy.serialNameForJson(serialDescriptor, i2, serialDescriptor.getElementName(i2)), i2);
            }
        }
        return linkedHashMap.isEmpty() ? MapsKt__MapsKt.emptyMap() : linkedHashMap;
    }

    private static final void buildDeserializationNamesMap$putOrThrow(Map<String, Integer> map, SerialDescriptor serialDescriptor, String str, int i2) {
        if (!map.containsKey(str)) {
            map.put(str, Integer.valueOf(i2));
            return;
        }
        throw new JsonException("The suggested name '" + str + "' for property " + serialDescriptor.getElementName(i2) + " is already one of the names for property " + serialDescriptor.getElementName(((Number) MapsKt__MapsKt.getValue(map, str)).intValue()) + " in " + serialDescriptor);
    }

    @NotNull
    public static final Map<String, Integer> deserializationNamesMap(@NotNull final Json json, @NotNull final SerialDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(json, "<this>");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return (Map) JsonSchemaCacheKt.getSchemaCache(json).getOrPut(descriptor, JsonDeserializationNamesKey, new Function0<Map<String, ? extends Integer>>() { // from class: kotlinx.serialization.json.internal.JsonNamesMapKt.deserializationNamesMap.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Map<String, ? extends Integer> invoke() {
                return JsonNamesMapKt.buildDeserializationNamesMap(descriptor, json);
            }
        });
    }

    @NotNull
    public static final DescriptorSchemaCache.Key<Map<String, Integer>> getJsonDeserializationNamesKey() {
        return JsonDeserializationNamesKey;
    }

    public static /* synthetic */ void getJsonDeserializationNamesKey$annotations() {
    }

    @NotNull
    public static final String getJsonElementName(@NotNull SerialDescriptor serialDescriptor, @NotNull Json json, int i2) {
        Intrinsics.checkNotNullParameter(serialDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(json, "json");
        JsonNamingStrategy jsonNamingStrategyNamingStrategy = namingStrategy(serialDescriptor, json);
        return jsonNamingStrategyNamingStrategy == null ? serialDescriptor.getElementName(i2) : serializationNamesIndices(serialDescriptor, json, jsonNamingStrategyNamingStrategy)[i2];
    }

    public static final int getJsonNameIndex(@NotNull SerialDescriptor serialDescriptor, @NotNull Json json, @NotNull String name) {
        Intrinsics.checkNotNullParameter(serialDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(json, "json");
        Intrinsics.checkNotNullParameter(name, "name");
        if (namingStrategy(serialDescriptor, json) != null) {
            return getJsonNameIndex$getJsonNameIndexSlowPath(json, serialDescriptor, name);
        }
        int elementIndex = serialDescriptor.getElementIndex(name);
        return (elementIndex == -3 && json.getConfiguration().getUseAlternativeNames()) ? getJsonNameIndex$getJsonNameIndexSlowPath(json, serialDescriptor, name) : elementIndex;
    }

    private static final int getJsonNameIndex$getJsonNameIndexSlowPath(Json json, SerialDescriptor serialDescriptor, String str) {
        Integer num = deserializationNamesMap(json, serialDescriptor).get(str);
        if (num != null) {
            return num.intValue();
        }
        return -3;
    }

    public static final int getJsonNameIndexOrThrow(@NotNull SerialDescriptor serialDescriptor, @NotNull Json json, @NotNull String name, @NotNull String suffix) {
        Intrinsics.checkNotNullParameter(serialDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(json, "json");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        int jsonNameIndex = getJsonNameIndex(serialDescriptor, json, name);
        if (jsonNameIndex != -3) {
            return jsonNameIndex;
        }
        throw new SerializationException(serialDescriptor.getSerialName() + " does not contain element with name '" + name + CharPool.SINGLE_QUOTE + suffix);
    }

    public static /* synthetic */ int getJsonNameIndexOrThrow$default(SerialDescriptor serialDescriptor, Json json, String str, String str2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            str2 = "";
        }
        return getJsonNameIndexOrThrow(serialDescriptor, json, str, str2);
    }

    @NotNull
    public static final DescriptorSchemaCache.Key<String[]> getJsonSerializationNamesKey() {
        return JsonSerializationNamesKey;
    }

    public static /* synthetic */ void getJsonSerializationNamesKey$annotations() {
    }

    @Nullable
    public static final JsonNamingStrategy namingStrategy(@NotNull SerialDescriptor serialDescriptor, @NotNull Json json) {
        Intrinsics.checkNotNullParameter(serialDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(json, "json");
        if (Intrinsics.areEqual(serialDescriptor.getKind(), StructureKind.CLASS.INSTANCE)) {
            return json.getConfiguration().getNamingStrategy();
        }
        return null;
    }

    @NotNull
    public static final String[] serializationNamesIndices(@NotNull final SerialDescriptor serialDescriptor, @NotNull Json json, @NotNull final JsonNamingStrategy strategy) {
        Intrinsics.checkNotNullParameter(serialDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(json, "json");
        Intrinsics.checkNotNullParameter(strategy, "strategy");
        return (String[]) JsonSchemaCacheKt.getSchemaCache(json).getOrPut(serialDescriptor, JsonSerializationNamesKey, new Function0<String[]>() { // from class: kotlinx.serialization.json.internal.JsonNamesMapKt.serializationNamesIndices.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final String[] invoke() {
                int elementsCount = serialDescriptor.getElementsCount();
                String[] strArr = new String[elementsCount];
                for (int i2 = 0; i2 < elementsCount; i2++) {
                    strArr[i2] = strategy.serialNameForJson(serialDescriptor, i2, serialDescriptor.getElementName(i2));
                }
                return strArr;
            }
        });
    }

    public static final boolean tryCoerceValue(@NotNull Json json, @NotNull SerialDescriptor elementDescriptor, @NotNull Function1<? super Boolean, Boolean> peekNull, @NotNull Function0<String> peekString, @NotNull Function0<Unit> onEnumCoercing) {
        String strInvoke;
        Intrinsics.checkNotNullParameter(json, "<this>");
        Intrinsics.checkNotNullParameter(elementDescriptor, "elementDescriptor");
        Intrinsics.checkNotNullParameter(peekNull, "peekNull");
        Intrinsics.checkNotNullParameter(peekString, "peekString");
        Intrinsics.checkNotNullParameter(onEnumCoercing, "onEnumCoercing");
        if (!elementDescriptor.isNullable() && peekNull.invoke(Boolean.TRUE).booleanValue()) {
            return true;
        }
        if (!Intrinsics.areEqual(elementDescriptor.getKind(), SerialKind.ENUM.INSTANCE) || ((elementDescriptor.isNullable() && peekNull.invoke(Boolean.FALSE).booleanValue()) || (strInvoke = peekString.invoke()) == null || getJsonNameIndex(elementDescriptor, json, strInvoke) != -3)) {
            return false;
        }
        onEnumCoercing.invoke();
        return true;
    }

    public static /* synthetic */ boolean tryCoerceValue$default(Json json, SerialDescriptor elementDescriptor, Function1 peekNull, Function0 peekString, Function0 onEnumCoercing, int i2, Object obj) {
        String str;
        if ((i2 & 8) != 0) {
            onEnumCoercing = new Function0<Unit>() { // from class: kotlinx.serialization.json.internal.JsonNamesMapKt.tryCoerceValue.1
                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                }
            };
        }
        Intrinsics.checkNotNullParameter(json, "<this>");
        Intrinsics.checkNotNullParameter(elementDescriptor, "elementDescriptor");
        Intrinsics.checkNotNullParameter(peekNull, "peekNull");
        Intrinsics.checkNotNullParameter(peekString, "peekString");
        Intrinsics.checkNotNullParameter(onEnumCoercing, "onEnumCoercing");
        if (!elementDescriptor.isNullable() && ((Boolean) peekNull.invoke(Boolean.TRUE)).booleanValue()) {
            return true;
        }
        if (!Intrinsics.areEqual(elementDescriptor.getKind(), SerialKind.ENUM.INSTANCE) || ((elementDescriptor.isNullable() && ((Boolean) peekNull.invoke(Boolean.FALSE)).booleanValue()) || (str = (String) peekString.invoke()) == null || getJsonNameIndex(elementDescriptor, json, str) != -3)) {
            return false;
        }
        onEnumCoercing.invoke();
        return true;
    }
}

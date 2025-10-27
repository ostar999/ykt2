package kotlinx.serialization.json;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.serialization.ExperimentalSerializationApi;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0002\u0010\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a2\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a2\u0010\u0007\u001a\u00020\b2\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a\u0019\u0010\n\u001a\u00020\u000b*\u00020\u00042\b\u0010\f\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\r\u001a\u0016\u0010\n\u001a\u00020\u000b*\u00020\u00042\b\u0010\f\u001a\u0004\u0018\u00010\u000eH\u0007\u001a\u0014\u0010\n\u001a\u00020\u000b*\u00020\u00042\b\u0010\f\u001a\u0004\u0018\u00010\u000f\u001a\u0014\u0010\n\u001a\u00020\u000b*\u00020\u00042\b\u0010\f\u001a\u0004\u0018\u00010\u0010\u001a#\u0010\u0011\u001a\u00020\u000b*\u00020\u00042\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006\u001a#\u0010\u0012\u001a\u00020\u000b*\u00020\u00042\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006\u001a#\u0010\u0013\u001a\u0004\u0018\u00010\u0014*\u00020\t2\u0006\u0010\u0015\u001a\u00020\u00102\b\u0010\f\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\u0016\u001a \u0010\u0013\u001a\u0004\u0018\u00010\u0014*\u00020\t2\u0006\u0010\u0015\u001a\u00020\u00102\b\u0010\f\u001a\u0004\u0018\u00010\u000eH\u0007\u001a\u001e\u0010\u0013\u001a\u0004\u0018\u00010\u0014*\u00020\t2\u0006\u0010\u0015\u001a\u00020\u00102\b\u0010\f\u001a\u0004\u0018\u00010\u000f\u001a\u001e\u0010\u0013\u001a\u0004\u0018\u00010\u0014*\u00020\t2\u0006\u0010\u0015\u001a\u00020\u00102\b\u0010\f\u001a\u0004\u0018\u00010\u0010\u001a-\u0010\u0017\u001a\u0004\u0018\u00010\u0014*\u00020\t2\u0006\u0010\u0015\u001a\u00020\u00102\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006\u001a-\u0010\u0018\u001a\u0004\u0018\u00010\u0014*\u00020\t2\u0006\u0010\u0015\u001a\u00020\u00102\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0019"}, d2 = {"buildJsonArray", "Lkotlinx/serialization/json/JsonArray;", "builderAction", "Lkotlin/Function1;", "Lkotlinx/serialization/json/JsonArrayBuilder;", "", "Lkotlin/ExtensionFunctionType;", "buildJsonObject", "Lkotlinx/serialization/json/JsonObject;", "Lkotlinx/serialization/json/JsonObjectBuilder;", "add", "", "value", "(Lkotlinx/serialization/json/JsonArrayBuilder;Ljava/lang/Boolean;)Z", "", "", "", "addJsonArray", "addJsonObject", "put", "Lkotlinx/serialization/json/JsonElement;", "key", "(Lkotlinx/serialization/json/JsonObjectBuilder;Ljava/lang/String;Ljava/lang/Boolean;)Lkotlinx/serialization/json/JsonElement;", "putJsonArray", "putJsonObject", "kotlinx-serialization-json"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nJsonElementBuilders.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonElementBuilders.kt\nkotlinx/serialization/json/JsonElementBuildersKt\n*L\n1#1,192:1\n27#1,4:193\n50#1,4:197\n27#1,4:201\n50#1,4:205\n*S KotlinDebug\n*F\n+ 1 JsonElementBuilders.kt\nkotlinx/serialization/json/JsonElementBuildersKt\n*L\n81#1:193,4\n89#1:197,4\n179#1:201,4\n187#1:205,4\n*E\n"})
/* loaded from: classes8.dex */
public final class JsonElementBuildersKt {
    public static final boolean add(@NotNull JsonArrayBuilder jsonArrayBuilder, @Nullable Boolean bool) {
        Intrinsics.checkNotNullParameter(jsonArrayBuilder, "<this>");
        return jsonArrayBuilder.add(JsonElementKt.JsonPrimitive(bool));
    }

    public static final boolean addJsonArray(@NotNull JsonArrayBuilder jsonArrayBuilder, @NotNull Function1<? super JsonArrayBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(jsonArrayBuilder, "<this>");
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        JsonArrayBuilder jsonArrayBuilder2 = new JsonArrayBuilder();
        builderAction.invoke(jsonArrayBuilder2);
        return jsonArrayBuilder.add(jsonArrayBuilder2.build());
    }

    public static final boolean addJsonObject(@NotNull JsonArrayBuilder jsonArrayBuilder, @NotNull Function1<? super JsonObjectBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(jsonArrayBuilder, "<this>");
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilder();
        builderAction.invoke(jsonObjectBuilder);
        return jsonArrayBuilder.add(jsonObjectBuilder.build());
    }

    @NotNull
    public static final JsonArray buildJsonArray(@NotNull Function1<? super JsonArrayBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        JsonArrayBuilder jsonArrayBuilder = new JsonArrayBuilder();
        builderAction.invoke(jsonArrayBuilder);
        return jsonArrayBuilder.build();
    }

    @NotNull
    public static final JsonObject buildJsonObject(@NotNull Function1<? super JsonObjectBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilder();
        builderAction.invoke(jsonObjectBuilder);
        return jsonObjectBuilder.build();
    }

    @Nullable
    public static final JsonElement put(@NotNull JsonObjectBuilder jsonObjectBuilder, @NotNull String key, @Nullable Boolean bool) {
        Intrinsics.checkNotNullParameter(jsonObjectBuilder, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        return jsonObjectBuilder.put(key, JsonElementKt.JsonPrimitive(bool));
    }

    @Nullable
    public static final JsonElement putJsonArray(@NotNull JsonObjectBuilder jsonObjectBuilder, @NotNull String key, @NotNull Function1<? super JsonArrayBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(jsonObjectBuilder, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        JsonArrayBuilder jsonArrayBuilder = new JsonArrayBuilder();
        builderAction.invoke(jsonArrayBuilder);
        return jsonObjectBuilder.put(key, jsonArrayBuilder.build());
    }

    @Nullable
    public static final JsonElement putJsonObject(@NotNull JsonObjectBuilder jsonObjectBuilder, @NotNull String key, @NotNull Function1<? super JsonObjectBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(jsonObjectBuilder, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        JsonObjectBuilder jsonObjectBuilder2 = new JsonObjectBuilder();
        builderAction.invoke(jsonObjectBuilder2);
        return jsonObjectBuilder.put(key, jsonObjectBuilder2.build());
    }

    public static final boolean add(@NotNull JsonArrayBuilder jsonArrayBuilder, @Nullable Number number) {
        Intrinsics.checkNotNullParameter(jsonArrayBuilder, "<this>");
        return jsonArrayBuilder.add(JsonElementKt.JsonPrimitive(number));
    }

    @Nullable
    public static final JsonElement put(@NotNull JsonObjectBuilder jsonObjectBuilder, @NotNull String key, @Nullable Number number) {
        Intrinsics.checkNotNullParameter(jsonObjectBuilder, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        return jsonObjectBuilder.put(key, JsonElementKt.JsonPrimitive(number));
    }

    public static final boolean add(@NotNull JsonArrayBuilder jsonArrayBuilder, @Nullable String str) {
        Intrinsics.checkNotNullParameter(jsonArrayBuilder, "<this>");
        return jsonArrayBuilder.add(JsonElementKt.JsonPrimitive(str));
    }

    @Nullable
    public static final JsonElement put(@NotNull JsonObjectBuilder jsonObjectBuilder, @NotNull String key, @Nullable String str) {
        Intrinsics.checkNotNullParameter(jsonObjectBuilder, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        return jsonObjectBuilder.put(key, JsonElementKt.JsonPrimitive(str));
    }

    @ExperimentalSerializationApi
    public static final boolean add(@NotNull JsonArrayBuilder jsonArrayBuilder, @Nullable Void r12) {
        Intrinsics.checkNotNullParameter(jsonArrayBuilder, "<this>");
        return jsonArrayBuilder.add(JsonNull.INSTANCE);
    }

    @ExperimentalSerializationApi
    @Nullable
    public static final JsonElement put(@NotNull JsonObjectBuilder jsonObjectBuilder, @NotNull String key, @Nullable Void r2) {
        Intrinsics.checkNotNullParameter(jsonObjectBuilder, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        return jsonObjectBuilder.put(key, JsonNull.INSTANCE);
    }
}

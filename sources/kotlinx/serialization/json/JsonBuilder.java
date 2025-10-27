package kotlinx.serialization.json;

import com.aliyun.auth.common.AliyunVodHttpCommon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.serialization.ExperimentalSerializationApi;
import kotlinx.serialization.modules.SerializersModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\r\u0010>\u001a\u00020?H\u0000¢\u0006\u0002\b@R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\b\"\u0004\b\u0016\u0010\nR\u001a\u0010\u0017\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\b\"\u0004\b\u0019\u0010\nR$\u0010\u001a\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\b\"\u0004\b\u001e\u0010\nR\u001a\u0010\u001f\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\b\"\u0004\b!\u0010\nR\u001a\u0010\"\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\b\"\u0004\b#\u0010\nR&\u0010$\u001a\u0004\u0018\u00010%8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b&\u0010\u001c\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001a\u0010+\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\b\"\u0004\b-\u0010\nR$\u0010.\u001a\u00020\u000f8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b/\u0010\u001c\u001a\u0004\b0\u0010\u0011\"\u0004\b1\u0010\u0013R\u001a\u00102\u001a\u000203X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\u001a\u00108\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010\b\"\u0004\b:\u0010\nR\u001a\u0010;\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010\b\"\u0004\b=\u0010\n¨\u0006A"}, d2 = {"Lkotlinx/serialization/json/JsonBuilder;", "", AliyunVodHttpCommon.Format.FORMAT_JSON, "Lkotlinx/serialization/json/Json;", "(Lkotlinx/serialization/json/Json;)V", "allowSpecialFloatingPointValues", "", "getAllowSpecialFloatingPointValues", "()Z", "setAllowSpecialFloatingPointValues", "(Z)V", "allowStructuredMapKeys", "getAllowStructuredMapKeys", "setAllowStructuredMapKeys", "classDiscriminator", "", "getClassDiscriminator", "()Ljava/lang/String;", "setClassDiscriminator", "(Ljava/lang/String;)V", "coerceInputValues", "getCoerceInputValues", "setCoerceInputValues", "encodeDefaults", "getEncodeDefaults", "setEncodeDefaults", "explicitNulls", "getExplicitNulls$annotations", "()V", "getExplicitNulls", "setExplicitNulls", "ignoreUnknownKeys", "getIgnoreUnknownKeys", "setIgnoreUnknownKeys", "isLenient", "setLenient", "namingStrategy", "Lkotlinx/serialization/json/JsonNamingStrategy;", "getNamingStrategy$annotations", "getNamingStrategy", "()Lkotlinx/serialization/json/JsonNamingStrategy;", "setNamingStrategy", "(Lkotlinx/serialization/json/JsonNamingStrategy;)V", "prettyPrint", "getPrettyPrint", "setPrettyPrint", "prettyPrintIndent", "getPrettyPrintIndent$annotations", "getPrettyPrintIndent", "setPrettyPrintIndent", "serializersModule", "Lkotlinx/serialization/modules/SerializersModule;", "getSerializersModule", "()Lkotlinx/serialization/modules/SerializersModule;", "setSerializersModule", "(Lkotlinx/serialization/modules/SerializersModule;)V", "useAlternativeNames", "getUseAlternativeNames", "setUseAlternativeNames", "useArrayPolymorphism", "getUseArrayPolymorphism", "setUseArrayPolymorphism", "build", "Lkotlinx/serialization/json/JsonConfiguration;", "build$kotlinx_serialization_json", "kotlinx-serialization-json"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nJson.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Json.kt\nkotlinx/serialization/json/JsonBuilder\n+ 2 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n*L\n1#1,386:1\n1064#2,2:387\n*S KotlinDebug\n*F\n+ 1 Json.kt\nkotlinx/serialization/json/JsonBuilder\n*L\n349#1:387,2\n*E\n"})
/* loaded from: classes8.dex */
public final class JsonBuilder {
    private boolean allowSpecialFloatingPointValues;
    private boolean allowStructuredMapKeys;

    @NotNull
    private String classDiscriminator;
    private boolean coerceInputValues;
    private boolean encodeDefaults;
    private boolean explicitNulls;
    private boolean ignoreUnknownKeys;
    private boolean isLenient;

    @Nullable
    private JsonNamingStrategy namingStrategy;
    private boolean prettyPrint;

    @NotNull
    private String prettyPrintIndent;

    @NotNull
    private SerializersModule serializersModule;
    private boolean useAlternativeNames;
    private boolean useArrayPolymorphism;

    public JsonBuilder(@NotNull Json json) {
        Intrinsics.checkNotNullParameter(json, "json");
        this.encodeDefaults = json.getConfiguration().getEncodeDefaults();
        this.explicitNulls = json.getConfiguration().getExplicitNulls();
        this.ignoreUnknownKeys = json.getConfiguration().getIgnoreUnknownKeys();
        this.isLenient = json.getConfiguration().getIsLenient();
        this.allowStructuredMapKeys = json.getConfiguration().getAllowStructuredMapKeys();
        this.prettyPrint = json.getConfiguration().getPrettyPrint();
        this.prettyPrintIndent = json.getConfiguration().getPrettyPrintIndent();
        this.coerceInputValues = json.getConfiguration().getCoerceInputValues();
        this.useArrayPolymorphism = json.getConfiguration().getUseArrayPolymorphism();
        this.classDiscriminator = json.getConfiguration().getClassDiscriminator();
        this.allowSpecialFloatingPointValues = json.getConfiguration().getAllowSpecialFloatingPointValues();
        this.useAlternativeNames = json.getConfiguration().getUseAlternativeNames();
        this.namingStrategy = json.getConfiguration().getNamingStrategy();
        this.serializersModule = json.getSerializersModule();
    }

    @ExperimentalSerializationApi
    public static /* synthetic */ void getExplicitNulls$annotations() {
    }

    @ExperimentalSerializationApi
    public static /* synthetic */ void getNamingStrategy$annotations() {
    }

    @ExperimentalSerializationApi
    public static /* synthetic */ void getPrettyPrintIndent$annotations() {
    }

    @NotNull
    public final JsonConfiguration build$kotlinx_serialization_json() {
        if (this.useArrayPolymorphism && !Intrinsics.areEqual(this.classDiscriminator, "type")) {
            throw new IllegalArgumentException("Class discriminator should not be specified when array polymorphism is specified".toString());
        }
        if (this.prettyPrint) {
            if (!Intrinsics.areEqual(this.prettyPrintIndent, "    ")) {
                String str = this.prettyPrintIndent;
                boolean z2 = false;
                int i2 = 0;
                while (true) {
                    boolean z3 = true;
                    if (i2 >= str.length()) {
                        z2 = true;
                        break;
                    }
                    char cCharAt = str.charAt(i2);
                    if (cCharAt != ' ' && cCharAt != '\t' && cCharAt != '\r' && cCharAt != '\n') {
                        z3 = false;
                    }
                    if (!z3) {
                        break;
                    }
                    i2++;
                }
                if (!z2) {
                    throw new IllegalArgumentException(("Only whitespace, tab, newline and carriage return are allowed as pretty print symbols. Had " + this.prettyPrintIndent).toString());
                }
            }
        } else if (!Intrinsics.areEqual(this.prettyPrintIndent, "    ")) {
            throw new IllegalArgumentException("Indent should not be specified when default printing mode is used".toString());
        }
        return new JsonConfiguration(this.encodeDefaults, this.ignoreUnknownKeys, this.isLenient, this.allowStructuredMapKeys, this.prettyPrint, this.explicitNulls, this.prettyPrintIndent, this.coerceInputValues, this.useArrayPolymorphism, this.classDiscriminator, this.allowSpecialFloatingPointValues, this.useAlternativeNames, this.namingStrategy);
    }

    public final boolean getAllowSpecialFloatingPointValues() {
        return this.allowSpecialFloatingPointValues;
    }

    public final boolean getAllowStructuredMapKeys() {
        return this.allowStructuredMapKeys;
    }

    @NotNull
    public final String getClassDiscriminator() {
        return this.classDiscriminator;
    }

    public final boolean getCoerceInputValues() {
        return this.coerceInputValues;
    }

    public final boolean getEncodeDefaults() {
        return this.encodeDefaults;
    }

    public final boolean getExplicitNulls() {
        return this.explicitNulls;
    }

    public final boolean getIgnoreUnknownKeys() {
        return this.ignoreUnknownKeys;
    }

    @Nullable
    public final JsonNamingStrategy getNamingStrategy() {
        return this.namingStrategy;
    }

    public final boolean getPrettyPrint() {
        return this.prettyPrint;
    }

    @NotNull
    public final String getPrettyPrintIndent() {
        return this.prettyPrintIndent;
    }

    @NotNull
    public final SerializersModule getSerializersModule() {
        return this.serializersModule;
    }

    public final boolean getUseAlternativeNames() {
        return this.useAlternativeNames;
    }

    public final boolean getUseArrayPolymorphism() {
        return this.useArrayPolymorphism;
    }

    /* renamed from: isLenient, reason: from getter */
    public final boolean getIsLenient() {
        return this.isLenient;
    }

    public final void setAllowSpecialFloatingPointValues(boolean z2) {
        this.allowSpecialFloatingPointValues = z2;
    }

    public final void setAllowStructuredMapKeys(boolean z2) {
        this.allowStructuredMapKeys = z2;
    }

    public final void setClassDiscriminator(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.classDiscriminator = str;
    }

    public final void setCoerceInputValues(boolean z2) {
        this.coerceInputValues = z2;
    }

    public final void setEncodeDefaults(boolean z2) {
        this.encodeDefaults = z2;
    }

    public final void setExplicitNulls(boolean z2) {
        this.explicitNulls = z2;
    }

    public final void setIgnoreUnknownKeys(boolean z2) {
        this.ignoreUnknownKeys = z2;
    }

    public final void setLenient(boolean z2) {
        this.isLenient = z2;
    }

    public final void setNamingStrategy(@Nullable JsonNamingStrategy jsonNamingStrategy) {
        this.namingStrategy = jsonNamingStrategy;
    }

    public final void setPrettyPrint(boolean z2) {
        this.prettyPrint = z2;
    }

    public final void setPrettyPrintIndent(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.prettyPrintIndent = str;
    }

    public final void setSerializersModule(@NotNull SerializersModule serializersModule) {
        Intrinsics.checkNotNullParameter(serializersModule, "<set-?>");
        this.serializersModule = serializersModule;
    }

    public final void setUseAlternativeNames(boolean z2) {
        this.useAlternativeNames = z2;
    }

    public final void setUseArrayPolymorphism(boolean z2) {
        this.useArrayPolymorphism = z2;
    }
}

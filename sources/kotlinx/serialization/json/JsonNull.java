package kotlinx.serialization.json;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.Serializable;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00000\u000bHÆ\u0001R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\t¨\u0006\f"}, d2 = {"Lkotlinx/serialization/json/JsonNull;", "Lkotlinx/serialization/json/JsonPrimitive;", "()V", "content", "", "getContent", "()Ljava/lang/String;", "isString", "", "()Z", "serializer", "Lkotlinx/serialization/KSerializer;", "kotlinx-serialization-json"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable(with = JsonNullSerializer.class)
/* loaded from: classes8.dex */
public final class JsonNull extends JsonPrimitive {

    @NotNull
    public static final JsonNull INSTANCE = new JsonNull();

    @NotNull
    private static final String content = "null";
    private static final /* synthetic */ Lazy<KSerializer<Object>> $cachedSerializer$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<KSerializer<Object>>() { // from class: kotlinx.serialization.json.JsonNull$$cachedSerializer$delegate$1
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final KSerializer<Object> invoke() {
            return JsonNullSerializer.INSTANCE;
        }
    });

    private JsonNull() {
        super(null);
    }

    private final /* synthetic */ Lazy get$cachedSerializer$delegate() {
        return $cachedSerializer$delegate;
    }

    @Override // kotlinx.serialization.json.JsonPrimitive
    @NotNull
    public String getContent() {
        return content;
    }

    @Override // kotlinx.serialization.json.JsonPrimitive
    /* renamed from: isString */
    public boolean getIsString() {
        return false;
    }

    @NotNull
    public final KSerializer<JsonNull> serializer() {
        return (KSerializer) get$cachedSerializer$delegate().getValue();
    }
}

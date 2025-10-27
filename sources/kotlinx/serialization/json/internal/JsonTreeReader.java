package kotlinx.serialization.json.internal;

import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import kotlin.DeepRecursiveFunction;
import kotlin.DeepRecursiveKt;
import kotlin.DeepRecursiveScope;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.serialization.json.JsonArray;
import kotlinx.serialization.json.JsonConfiguration;
import kotlinx.serialization.json.JsonElement;
import kotlinx.serialization.json.JsonLiteral;
import kotlinx.serialization.json.JsonNull;
import kotlinx.serialization.json.JsonObject;
import kotlinx.serialization.json.JsonPrimitive;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u000b\u001a\u00020\fJ\b\u0010\r\u001a\u00020\fH\u0002J\b\u0010\u000e\u001a\u00020\fH\u0002J\b\u0010\u000f\u001a\u00020\fH\u0002J\u0017\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\f0\u0013H\u0082\bJ\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\bH\u0002J!\u0010\u000f\u001a\u00020\f*\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\f0\u0017H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u0019R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001a"}, d2 = {"Lkotlinx/serialization/json/internal/JsonTreeReader;", "", "configuration", "Lkotlinx/serialization/json/JsonConfiguration;", "lexer", "Lkotlinx/serialization/json/internal/AbstractJsonLexer;", "(Lkotlinx/serialization/json/JsonConfiguration;Lkotlinx/serialization/json/internal/AbstractJsonLexer;)V", "isLenient", "", "stackDepth", "", "read", "Lkotlinx/serialization/json/JsonElement;", "readArray", "readDeepRecursive", "readObject", "readObjectImpl", "Lkotlinx/serialization/json/JsonObject;", "reader", "Lkotlin/Function0;", "readValue", "Lkotlinx/serialization/json/JsonPrimitive;", "isString", "Lkotlin/DeepRecursiveScope;", "", "(Lkotlin/DeepRecursiveScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-serialization-json"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nJsonTreeReader.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonTreeReader.kt\nkotlinx/serialization/json/internal/JsonTreeReader\n+ 2 AbstractJsonLexer.kt\nkotlinx/serialization/json/internal/AbstractJsonLexer\n*L\n1#1,118:1\n26#1,24:119\n26#1,24:143\n517#2,3:167\n*S KotlinDebug\n*F\n+ 1 JsonTreeReader.kt\nkotlinx/serialization/json/internal/JsonTreeReader\n*L\n18#1:119,24\n23#1:143,24\n62#1:167,3\n*E\n"})
/* loaded from: classes8.dex */
public final class JsonTreeReader {
    private final boolean isLenient;

    @NotNull
    private final JsonReader lexer;
    private int stackDepth;

    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0004\u001a\u00020\u0002*\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0001H\u008a@"}, d2 = {"Lkotlin/DeepRecursiveScope;", "", "Lkotlinx/serialization/json/JsonElement;", AdvanceSetting.NETWORK_TYPE, "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "kotlinx.serialization.json.internal.JsonTreeReader$readDeepRecursive$1", f = "JsonTreeReader.kt", i = {}, l = {112}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: kotlinx.serialization.json.internal.JsonTreeReader$readDeepRecursive$1, reason: invalid class name */
    public static final class AnonymousClass1 extends RestrictedSuspendLambda implements Function3<DeepRecursiveScope<Unit, JsonElement>, Unit, Continuation<? super JsonElement>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull DeepRecursiveScope<Unit, JsonElement> deepRecursiveScope, @NotNull Unit unit, @Nullable Continuation<? super JsonElement> continuation) {
            AnonymousClass1 anonymousClass1 = JsonTreeReader.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = deepRecursiveScope;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                DeepRecursiveScope deepRecursiveScope = (DeepRecursiveScope) this.L$0;
                byte bPeekNextToken = JsonTreeReader.this.lexer.peekNextToken();
                if (bPeekNextToken == 1) {
                    return JsonTreeReader.this.readValue(true);
                }
                if (bPeekNextToken == 0) {
                    return JsonTreeReader.this.readValue(false);
                }
                if (bPeekNextToken != 6) {
                    if (bPeekNextToken == 8) {
                        return JsonTreeReader.this.readArray();
                    }
                    JsonReader.fail$default(JsonTreeReader.this.lexer, "Can't begin reading element, unexpected token", 0, null, 6, null);
                    throw new KotlinNothingValueException();
                }
                JsonTreeReader jsonTreeReader = JsonTreeReader.this;
                this.label = 1;
                obj = jsonTreeReader.readObject(deepRecursiveScope, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return (JsonElement) obj;
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.serialization.json.internal.JsonTreeReader", f = "JsonTreeReader.kt", i = {0, 0, 0, 0}, l = {23}, m = "readObject", n = {"$this$readObject", "this_$iv", "result$iv", "key$iv"}, s = {"L$0", "L$1", "L$2", "L$3"})
    /* renamed from: kotlinx.serialization.json.internal.JsonTreeReader$readObject$2, reason: invalid class name */
    public static final class AnonymousClass2 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return JsonTreeReader.this.readObject(null, this);
        }
    }

    public JsonTreeReader(@NotNull JsonConfiguration configuration, @NotNull JsonReader lexer) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(lexer, "lexer");
        this.lexer = lexer;
        this.isLenient = configuration.getIsLenient();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JsonElement readArray() {
        byte bConsumeNextToken = this.lexer.consumeNextToken();
        if (this.lexer.peekNextToken() == 4) {
            JsonReader.fail$default(this.lexer, "Unexpected leading comma", 0, null, 6, null);
            throw new KotlinNothingValueException();
        }
        ArrayList arrayList = new ArrayList();
        while (this.lexer.canConsumeValue()) {
            arrayList.add(read());
            bConsumeNextToken = this.lexer.consumeNextToken();
            if (bConsumeNextToken != 4) {
                JsonReader jsonReader = this.lexer;
                boolean z2 = bConsumeNextToken == 9;
                int i2 = jsonReader.currentPosition;
                if (!z2) {
                    JsonReader.fail$default(jsonReader, "Expected end of the array or comma", i2, null, 4, null);
                    throw new KotlinNothingValueException();
                }
            }
        }
        if (bConsumeNextToken == 8) {
            this.lexer.consumeNextToken((byte) 9);
        } else if (bConsumeNextToken == 4) {
            JsonReader.fail$default(this.lexer, "Unexpected trailing comma", 0, null, 6, null);
            throw new KotlinNothingValueException();
        }
        return new JsonArray(arrayList);
    }

    private final JsonElement readDeepRecursive() {
        return (JsonElement) DeepRecursiveKt.invoke(new DeepRecursiveFunction(new AnonymousClass1(null)), Unit.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x009c -> B:27:0x00a6). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object readObject(kotlin.DeepRecursiveScope<kotlin.Unit, kotlinx.serialization.json.JsonElement> r21, kotlin.coroutines.Continuation<? super kotlinx.serialization.json.JsonElement> r22) {
        /*
            Method dump skipped, instructions count: 256
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.serialization.json.internal.JsonTreeReader.readObject(kotlin.DeepRecursiveScope, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final JsonObject readObjectImpl(Function0<? extends JsonElement> reader) {
        byte bConsumeNextToken = this.lexer.consumeNextToken((byte) 6);
        if (this.lexer.peekNextToken() == 4) {
            JsonReader.fail$default(this.lexer, "Unexpected leading comma", 0, null, 6, null);
            throw new KotlinNothingValueException();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        while (true) {
            if (!this.lexer.canConsumeValue()) {
                break;
            }
            String strConsumeStringLenient = this.isLenient ? this.lexer.consumeStringLenient() : this.lexer.consumeString();
            this.lexer.consumeNextToken((byte) 5);
            linkedHashMap.put(strConsumeStringLenient, reader.invoke());
            bConsumeNextToken = this.lexer.consumeNextToken();
            if (bConsumeNextToken != 4) {
                if (bConsumeNextToken != 7) {
                    JsonReader.fail$default(this.lexer, "Expected end of the object or comma", 0, null, 6, null);
                    throw new KotlinNothingValueException();
                }
            }
        }
        if (bConsumeNextToken == 6) {
            this.lexer.consumeNextToken((byte) 7);
        } else if (bConsumeNextToken == 4) {
            JsonReader.fail$default(this.lexer, "Unexpected trailing comma", 0, null, 6, null);
            throw new KotlinNothingValueException();
        }
        return new JsonObject(linkedHashMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JsonPrimitive readValue(boolean isString) {
        String strConsumeStringLenient = (this.isLenient || !isString) ? this.lexer.consumeStringLenient() : this.lexer.consumeString();
        return (isString || !Intrinsics.areEqual(strConsumeStringLenient, "null")) ? new JsonLiteral(strConsumeStringLenient, isString, null, 4, null) : JsonNull.INSTANCE;
    }

    @NotNull
    public final JsonElement read() {
        byte bPeekNextToken = this.lexer.peekNextToken();
        if (bPeekNextToken == 1) {
            return readValue(true);
        }
        if (bPeekNextToken == 0) {
            return readValue(false);
        }
        if (bPeekNextToken == 6) {
            int i2 = this.stackDepth + 1;
            this.stackDepth = i2;
            this.stackDepth--;
            return i2 == 200 ? readDeepRecursive() : readObject();
        }
        if (bPeekNextToken == 8) {
            return readArray();
        }
        JsonReader.fail$default(this.lexer, "Cannot begin reading element, unexpected token: " + ((int) bPeekNextToken), 0, null, 6, null);
        throw new KotlinNothingValueException();
    }

    private final JsonElement readObject() {
        byte bConsumeNextToken = this.lexer.consumeNextToken((byte) 6);
        if (this.lexer.peekNextToken() != 4) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            while (true) {
                if (!this.lexer.canConsumeValue()) {
                    break;
                }
                String strConsumeStringLenient = this.isLenient ? this.lexer.consumeStringLenient() : this.lexer.consumeString();
                this.lexer.consumeNextToken((byte) 5);
                linkedHashMap.put(strConsumeStringLenient, read());
                bConsumeNextToken = this.lexer.consumeNextToken();
                if (bConsumeNextToken != 4) {
                    if (bConsumeNextToken != 7) {
                        JsonReader.fail$default(this.lexer, "Expected end of the object or comma", 0, null, 6, null);
                        throw new KotlinNothingValueException();
                    }
                }
            }
            if (bConsumeNextToken == 6) {
                this.lexer.consumeNextToken((byte) 7);
            } else if (bConsumeNextToken == 4) {
                JsonReader.fail$default(this.lexer, "Unexpected trailing comma", 0, null, 6, null);
                throw new KotlinNothingValueException();
            }
            return new JsonObject(linkedHashMap);
        }
        JsonReader.fail$default(this.lexer, "Unexpected leading comma", 0, null, 6, null);
        throw new KotlinNothingValueException();
    }
}

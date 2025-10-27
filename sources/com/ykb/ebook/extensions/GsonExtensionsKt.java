package com.ykb.ebook.extensions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\"\u001b\u0010\u0000\u001a\u00020\u00018FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0002\u0010\u0003\"\u001b\u0010\u0006\u001a\u00020\u00018FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\u0005\u001a\u0004\b\u0007\u0010\u0003¨\u0006\t"}, d2 = {"GSON", "Lcom/google/gson/Gson;", "getGSON", "()Lcom/google/gson/Gson;", "GSON$delegate", "Lkotlin/Lazy;", "INITIAL_GSON", "getINITIAL_GSON", "INITIAL_GSON$delegate", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class GsonExtensionsKt {

    @NotNull
    private static final Lazy INITIAL_GSON$delegate = LazyKt__LazyJVMKt.lazy(new Function0<Gson>() { // from class: com.ykb.ebook.extensions.GsonExtensionsKt$INITIAL_GSON$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Gson invoke() {
            return new GsonBuilder().registerTypeAdapter(new TypeToken<Map<String, ? extends Object>>() { // from class: com.ykb.ebook.extensions.GsonExtensionsKt$INITIAL_GSON$2.1
            }.getType(), new MapDeserializerDoubleAsIntFix()).registerTypeAdapter(Integer.TYPE, new IntJsonDeserializer()).registerTypeAdapter(String.class, new StringJsonDeserializer()).disableHtmlEscaping().setPrettyPrinting().create();
        }
    });

    @NotNull
    private static final Lazy GSON$delegate = LazyKt__LazyJVMKt.lazy(new Function0<Gson>() { // from class: com.ykb.ebook.extensions.GsonExtensionsKt$GSON$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Gson invoke() {
            return GsonExtensionsKt.getINITIAL_GSON().newBuilder().create();
        }
    });

    @NotNull
    public static final Gson getGSON() {
        Object value = GSON$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-GSON>(...)");
        return (Gson) value;
    }

    @NotNull
    public static final Gson getINITIAL_GSON() {
        Object value = INITIAL_GSON$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-INITIAL_GSON>(...)");
        return (Gson) value;
    }
}

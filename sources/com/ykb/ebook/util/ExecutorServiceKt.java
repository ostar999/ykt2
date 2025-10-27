package com.ykb.ebook.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u001b\u0010\u0000\u001a\u00020\u00018FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0006"}, d2 = {"globalExecutor", "Ljava/util/concurrent/ExecutorService;", "getGlobalExecutor", "()Ljava/util/concurrent/ExecutorService;", "globalExecutor$delegate", "Lkotlin/Lazy;", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ExecutorServiceKt {

    @NotNull
    private static final Lazy globalExecutor$delegate = LazyKt__LazyJVMKt.lazy(new Function0<ExecutorService>() { // from class: com.ykb.ebook.util.ExecutorServiceKt$globalExecutor$2
        @Override // kotlin.jvm.functions.Function0
        public final ExecutorService invoke() {
            return Executors.newSingleThreadExecutor();
        }
    });

    @NotNull
    public static final ExecutorService getGlobalExecutor() {
        Object value = globalExecutor$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-globalExecutor>(...)");
        return (ExecutorService) value;
    }
}

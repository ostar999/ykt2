package com.ykb.ebook.api;

import com.ykb.ebook.network.RetrofitClientKt;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u001b\u0010\u0000\u001a\u00020\u00018FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0006"}, d2 = {"API", "Lcom/ykb/ebook/api/ApiService;", "getAPI", "()Lcom/ykb/ebook/api/ApiService;", "API$delegate", "Lkotlin/Lazy;", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ApiServiceKt {

    @NotNull
    private static final Lazy API$delegate = LazyKt__LazyJVMKt.lazy(new Function0<ApiService>() { // from class: com.ykb.ebook.api.ApiServiceKt$API$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ApiService invoke() {
            return (ApiService) RetrofitClientKt.getRetrofit().create(ApiService.class);
        }
    });

    @NotNull
    public static final ApiService getAPI() {
        Object value = API$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-API>(...)");
        return (ApiService) value;
    }
}

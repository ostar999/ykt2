package com.ykb.ebook.weight;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "Ljava/util/concurrent/ExecutorService;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class ContentTextView$Companion$renderThread$2 extends Lambda implements Function0<ExecutorService> {
    public static final ContentTextView$Companion$renderThread$2 INSTANCE = new ContentTextView$Companion$renderThread$2();

    public ContentTextView$Companion$renderThread$2() {
        super(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Thread invoke$lambda$0(Runnable runnable) {
        return new Thread(runnable, "TextPageRender");
    }

    @Override // kotlin.jvm.functions.Function0
    public final ExecutorService invoke() {
        return Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: com.ykb.ebook.weight.d
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return ContentTextView$Companion$renderThread$2.invoke$lambda$0(runnable);
            }
        });
    }
}

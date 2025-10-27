package com.ykb.ebook.activity;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Ljava/lang/Runnable;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ReadBookActivity$screenOffRunnable$2 extends Lambda implements Function0<Runnable> {
    final /* synthetic */ ReadBookActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReadBookActivity$screenOffRunnable$2(ReadBookActivity readBookActivity) {
        super(0);
        this.this$0 = readBookActivity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$0(ReadBookActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.keepScreenOn(false);
    }

    @Override // kotlin.jvm.functions.Function0
    @NotNull
    public final Runnable invoke() {
        final ReadBookActivity readBookActivity = this.this$0;
        return new Runnable() { // from class: com.ykb.ebook.activity.r1
            @Override // java.lang.Runnable
            public final void run() {
                ReadBookActivity$screenOffRunnable$2.invoke$lambda$0(readBookActivity);
            }
        };
    }
}

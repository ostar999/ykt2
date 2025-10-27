package com.ykb.ebook.weight;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Ljava/lang/Runnable;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class ContentTextView$renderRunnable$2 extends Lambda implements Function0<Runnable> {
    final /* synthetic */ ContentTextView this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ContentTextView$renderRunnable$2(ContentTextView contentTextView) {
        super(0);
        this.this$0 = contentTextView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$0(ContentTextView this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.preRenderPage();
    }

    @Override // kotlin.jvm.functions.Function0
    @NotNull
    public final Runnable invoke() {
        final ContentTextView contentTextView = this.this$0;
        return new Runnable() { // from class: com.ykb.ebook.weight.e
            @Override // java.lang.Runnable
            public final void run() {
                ContentTextView$renderRunnable$2.invoke$lambda$0(contentTextView);
            }
        };
    }
}

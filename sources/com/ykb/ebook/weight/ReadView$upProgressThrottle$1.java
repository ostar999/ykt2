package com.ykb.ebook.weight;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"<anonymous>", "", "invoke", "()Ljava/lang/Boolean;"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class ReadView$upProgressThrottle$1 extends Lambda implements Function0<Boolean> {
    final /* synthetic */ ReadView this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReadView$upProgressThrottle$1(ReadView readView) {
        super(0);
        this.this$0 = readView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$0(ReadView this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.upProgress();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // kotlin.jvm.functions.Function0
    @NotNull
    public final Boolean invoke() {
        final ReadView readView = this.this$0;
        return Boolean.valueOf(readView.post(new Runnable() { // from class: com.ykb.ebook.weight.x0
            @Override // java.lang.Runnable
            public final void run() {
                ReadView$upProgressThrottle$1.invoke$lambda$0(readView);
            }
        }));
    }
}

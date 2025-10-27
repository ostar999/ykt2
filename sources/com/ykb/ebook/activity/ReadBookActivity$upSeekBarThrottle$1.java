package com.ykb.ebook.activity;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ReadBookActivity$upSeekBarThrottle$1 extends Lambda implements Function0<Unit> {
    final /* synthetic */ ReadBookActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReadBookActivity$upSeekBarThrottle$1(ReadBookActivity readBookActivity) {
        super(0);
        this.this$0 = readBookActivity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$0(ReadBookActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.upSeekBarProgress();
        this$0.getBinding().readMenu.upSeekBar();
        this$0.upReadProgress();
    }

    @Override // kotlin.jvm.functions.Function0
    public /* bridge */ /* synthetic */ Unit invoke() {
        invoke2();
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2() {
        final ReadBookActivity readBookActivity = this.this$0;
        readBookActivity.runOnUiThread(new Runnable() { // from class: com.ykb.ebook.activity.s1
            @Override // java.lang.Runnable
            public final void run() {
                ReadBookActivity$upSeekBarThrottle$1.invoke$lambda$0(readBookActivity);
            }
        });
    }
}

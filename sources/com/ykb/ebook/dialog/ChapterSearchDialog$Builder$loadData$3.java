package com.ykb.ebook.dialog;

import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.ykb.ebook.dialog.ChapterSearchDialog;
import com.ykb.ebook.util.NetUtilKt;
import com.ykb.ebook.util.ToastUtilsKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
@DebugMetadata(c = "com.ykb.ebook.dialog.ChapterSearchDialog$Builder$loadData$3", f = "ChapterSearchDialog.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes7.dex */
public final class ChapterSearchDialog$Builder$loadData$3 extends SuspendLambda implements Function3<CoroutineScope, Throwable, Continuation<? super Unit>, Object> {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ChapterSearchDialog.Builder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ChapterSearchDialog$Builder$loadData$3(ChapterSearchDialog.Builder builder, Continuation<? super ChapterSearchDialog$Builder$loadData$3> continuation) {
        super(3, continuation);
        this.this$0 = builder;
    }

    @Override // kotlin.jvm.functions.Function3
    @Nullable
    public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
        ChapterSearchDialog$Builder$loadData$3 chapterSearchDialog$Builder$loadData$3 = new ChapterSearchDialog$Builder$loadData$3(this.this$0, continuation);
        chapterSearchDialog$Builder$loadData$3.L$0 = th;
        return chapterSearchDialog$Builder$loadData$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ToastUtilsKt.toastOnUi$default(this.this$0.getContext(), NetUtilKt.getErrorMsg((Throwable) this.L$0), 0, 2, (Object) null);
        return Unit.INSTANCE;
    }
}

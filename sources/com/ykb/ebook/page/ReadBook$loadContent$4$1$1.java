package com.ykb.ebook.page;

import com.ykb.ebook.model.ChapterInfo;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
@DebugMetadata(c = "com.ykb.ebook.page.ReadBook$loadContent$4$1$1", f = "ReadBook.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes7.dex */
public final class ReadBook$loadContent$4$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ ChapterInfo $chapterInfo;
    final /* synthetic */ int $num;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReadBook$loadContent$4$1$1(int i2, ChapterInfo chapterInfo, Continuation<? super ReadBook$loadContent$4$1$1> continuation) {
        super(2, continuation);
        this.$num = i2;
        this.$chapterInfo = chapterInfo;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @NotNull
    public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
        return new ReadBook$loadContent$4$1$1(this.$num, this.$chapterInfo, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    @Nullable
    public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
        return ((ReadBook$loadContent$4$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        for (int i2 = 0; i2 < this.$num; i2++) {
            com.ykb.ebook.model.ImageProvider.INSTANCE.downloadImage(this.$chapterInfo.getImgList().get(i2));
        }
        return Unit.INSTANCE;
    }
}

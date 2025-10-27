package com.ykb.ebook.page;

import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.ykb.ebook.model.ChapterInfo;
import com.ykb.ebook.page.ReadBook;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0003\u001a\u00020\u0001*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", AdvanceSetting.NETWORK_TYPE, "<anonymous>"}, k = 3, mv = {1, 8, 0})
@DebugMetadata(c = "com.ykb.ebook.page.ReadBook$loadContent$4$1$2", f = "ReadBook.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes7.dex */
public final class ReadBook$loadContent$4$1$2 extends SuspendLambda implements Function3<CoroutineScope, Unit, Continuation<? super Unit>, Object> {
    final /* synthetic */ ChapterInfo $chapterInfo;
    final /* synthetic */ Ref.ObjectRef<String> $content;
    final /* synthetic */ boolean $resetPageOffset;
    final /* synthetic */ Function0<Unit> $success;
    final /* synthetic */ boolean $upContent;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReadBook$loadContent$4$1$2(ChapterInfo chapterInfo, Ref.ObjectRef<String> objectRef, boolean z2, boolean z3, Function0<Unit> function0, Continuation<? super ReadBook$loadContent$4$1$2> continuation) {
        super(3, continuation);
        this.$chapterInfo = chapterInfo;
        this.$content = objectRef;
        this.$upContent = z2;
        this.$resetPageOffset = z3;
        this.$success = function0;
    }

    @Override // kotlin.jvm.functions.Function3
    @Nullable
    public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull Unit unit, @Nullable Continuation<? super Unit> continuation) {
        return new ReadBook$loadContent$4$1$2(this.$chapterInfo, this.$content, this.$upContent, this.$resetPageOffset, this.$success, continuation).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ReadBook readBook = ReadBook.INSTANCE;
        readBook.contentLoadFinish(this.$chapterInfo, this.$content.element, this.$upContent, this.$resetPageOffset, this.$success);
        ReadBook.CallBack callBack = readBook.getCallBack();
        if (callBack != null) {
            callBack.loadingImage(false);
        }
        return Unit.INSTANCE;
    }
}

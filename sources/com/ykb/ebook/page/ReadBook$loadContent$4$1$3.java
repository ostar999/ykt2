package com.ykb.ebook.page;

import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.ykb.ebook.page.ReadBook;
import com.ykb.ebook.util.Log;
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
@DebugMetadata(c = "com.ykb.ebook.page.ReadBook$loadContent$4$1$3", f = "ReadBook.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes7.dex */
public final class ReadBook$loadContent$4$1$3 extends SuspendLambda implements Function3<CoroutineScope, Throwable, Continuation<? super Unit>, Object> {
    final /* synthetic */ int $index;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReadBook$loadContent$4$1$3(int i2, Continuation<? super ReadBook$loadContent$4$1$3> continuation) {
        super(3, continuation);
        this.$index = i2;
    }

    @Override // kotlin.jvm.functions.Function3
    @Nullable
    public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
        return new ReadBook$loadContent$4$1$3(this.$index, continuation).invokeSuspend(Unit.INSTANCE);
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
        readBook.removeLoading(this.$index);
        readBook.setLoadContent(false);
        ReadBook.CallBack callBack = readBook.getCallBack();
        if (callBack != null) {
            callBack.loadingImage(false);
        }
        Log.INSTANCE.logE("ReadBook", "正文内容加载出错！");
        return Unit.INSTANCE;
    }
}

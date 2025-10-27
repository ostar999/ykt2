package com.ykb.ebook.dialog;

import com.ykb.ebook.api.ApiService;
import com.ykb.ebook.api.ApiServiceKt;
import com.ykb.ebook.model.ReportReason;
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

@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/model/ReportReason;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
@DebugMetadata(c = "com.ykb.ebook.dialog.ReportReasonChooseDialog$Builder$commentReportReson$1", f = "ReportReasonChooseDialog.kt", i = {}, l = {93}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes7.dex */
public final class ReportReasonChooseDialog$Builder$commentReportReson$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super ReportReason>, Object> {
    int label;

    public ReportReasonChooseDialog$Builder$commentReportReson$1(Continuation<? super ReportReasonChooseDialog$Builder$commentReportReson$1> continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @NotNull
    public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
        return new ReportReasonChooseDialog$Builder$commentReportReson$1(continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    @Nullable
    public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super ReportReason> continuation) {
        return ((ReportReasonChooseDialog$Builder$commentReportReson$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ApiService api = ApiServiceKt.getAPI();
            this.label = 1;
            obj = api.commentReportReason(this);
            if (obj == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return obj;
    }
}

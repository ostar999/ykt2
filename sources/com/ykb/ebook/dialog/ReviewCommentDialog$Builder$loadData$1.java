package com.ykb.ebook.dialog;

import com.yikaobang.yixue.R2;
import com.ykb.ebook.api.ApiService;
import com.ykb.ebook.api.ApiServiceKt;
import com.ykb.ebook.base.BaseResponse;
import com.ykb.ebook.model.ParagraphComment;
import java.util.HashMap;
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

@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/model/ParagraphComment;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
@DebugMetadata(c = "com.ykb.ebook.dialog.ReviewCommentDialog$Builder$loadData$1", f = "ReviewCommentDialog.kt", i = {}, l = {R2.attr.bl_activated_textColor}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes7.dex */
public final class ReviewCommentDialog$Builder$loadData$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super BaseResponse<ParagraphComment>>, Object> {
    final /* synthetic */ HashMap<String, String> $params;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReviewCommentDialog$Builder$loadData$1(HashMap<String, String> map, Continuation<? super ReviewCommentDialog$Builder$loadData$1> continuation) {
        super(2, continuation);
        this.$params = map;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @NotNull
    public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
        return new ReviewCommentDialog$Builder$loadData$1(this.$params, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    @Nullable
    public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super BaseResponse<ParagraphComment>> continuation) {
        return ((ReviewCommentDialog$Builder$loadData$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ApiService api = ApiServiceKt.getAPI();
            HashMap<String, String> map = this.$params;
            this.label = 1;
            obj = api.paragraphReviewList(map, this);
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

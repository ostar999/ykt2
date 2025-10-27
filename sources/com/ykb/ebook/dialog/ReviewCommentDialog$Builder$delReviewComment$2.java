package com.ykb.ebook.dialog;

import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.ykb.ebook.base.BaseResponse;
import com.ykb.ebook.dialog.ReviewCommentDialog;
import com.ykb.ebook.model.BookReview;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\u00020\u00002\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
@DebugMetadata(c = "com.ykb.ebook.dialog.ReviewCommentDialog$Builder$delReviewComment$2", f = "ReviewCommentDialog.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
@SourceDebugExtension({"SMAP\nReviewCommentDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReviewCommentDialog.kt\ncom/ykb/ebook/dialog/ReviewCommentDialog$Builder$delReviewComment$2\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,1157:1\n1864#2,3:1158\n1864#2,3:1161\n*S KotlinDebug\n*F\n+ 1 ReviewCommentDialog.kt\ncom/ykb/ebook/dialog/ReviewCommentDialog$Builder$delReviewComment$2\n*L\n1106#1:1158,3\n1113#1:1161,3\n*E\n"})
/* loaded from: classes7.dex */
public final class ReviewCommentDialog$Builder$delReviewComment$2 extends SuspendLambda implements Function3<CoroutineScope, BaseResponse<Object>, Continuation<? super Unit>, Object> {
    final /* synthetic */ BookReview $item;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ReviewCommentDialog.Builder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReviewCommentDialog$Builder$delReviewComment$2(ReviewCommentDialog.Builder builder, BookReview bookReview, Continuation<? super ReviewCommentDialog$Builder$delReviewComment$2> continuation) {
        super(3, continuation);
        this.this$0 = builder;
        this.$item = bookReview;
    }

    @Override // kotlin.jvm.functions.Function3
    @Nullable
    public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull BaseResponse<Object> baseResponse, @Nullable Continuation<? super Unit> continuation) {
        ReviewCommentDialog$Builder$delReviewComment$2 reviewCommentDialog$Builder$delReviewComment$2 = new ReviewCommentDialog$Builder$delReviewComment$2(this.this$0, this.$item, continuation);
        reviewCommentDialog$Builder$delReviewComment$2.L$0 = baseResponse;
        return reviewCommentDialog$Builder$delReviewComment$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x008e  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r15) {
        /*
            Method dump skipped, instructions count: 543
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$delReviewComment$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}

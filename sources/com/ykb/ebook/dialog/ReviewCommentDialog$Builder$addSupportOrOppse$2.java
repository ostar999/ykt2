package com.ykb.ebook.dialog;

import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.ykb.ebook.base.BaseResponse;
import com.ykb.ebook.dialog.ReviewCommentDialog;
import com.ykb.ebook.model.BookReview;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\u00020\u00002\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
@DebugMetadata(c = "com.ykb.ebook.dialog.ReviewCommentDialog$Builder$addSupportOrOppse$2", f = "ReviewCommentDialog.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes7.dex */
public final class ReviewCommentDialog$Builder$addSupportOrOppse$2 extends SuspendLambda implements Function3<CoroutineScope, BaseResponse<Object>, Continuation<? super Unit>, Object> {
    final /* synthetic */ boolean $isSupport;
    final /* synthetic */ BookReview $item;
    final /* synthetic */ int $parentPos;
    final /* synthetic */ int $position;
    int label;
    final /* synthetic */ ReviewCommentDialog.Builder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReviewCommentDialog$Builder$addSupportOrOppse$2(int i2, boolean z2, BookReview bookReview, ReviewCommentDialog.Builder builder, int i3, Continuation<? super ReviewCommentDialog$Builder$addSupportOrOppse$2> continuation) {
        super(3, continuation);
        this.$position = i2;
        this.$isSupport = z2;
        this.$item = bookReview;
        this.this$0 = builder;
        this.$parentPos = i3;
    }

    @Override // kotlin.jvm.functions.Function3
    @Nullable
    public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull BaseResponse<Object> baseResponse, @Nullable Continuation<? super Unit> continuation) {
        return new ReviewCommentDialog$Builder$addSupportOrOppse$2(this.$position, this.$isSupport, this.$item, this.this$0, this.$parentPos, continuation).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (this.$position != -1) {
            if (this.$isSupport) {
                BookReview bookReview = this.$item;
                bookReview.setSupport(bookReview.isSupport() != 1 ? 1 : 0);
                if (this.$item.isSupport() == 1) {
                    BookReview bookReview2 = this.$item;
                    bookReview2.setSupportCount(String.valueOf(Integer.parseInt(bookReview2.getSupportCount()) + 1));
                } else {
                    BookReview bookReview3 = this.$item;
                    bookReview3.setSupportCount(String.valueOf(Integer.parseInt(bookReview3.getSupportCount()) - 1));
                }
            } else {
                BookReview bookReview4 = this.$item;
                bookReview4.setOpposition(bookReview4.isOpposition() != 1 ? 1 : 0);
                if (this.$item.isOpposition() == 1) {
                    BookReview bookReview5 = this.$item;
                    bookReview5.setOppositionCount(String.valueOf(Integer.parseInt(bookReview5.getOppositionCount()) + 1));
                } else {
                    BookReview bookReview6 = this.$item;
                    bookReview6.setOppositionCount(String.valueOf(Integer.parseInt(bookReview6.getOppositionCount()) - 1));
                }
            }
            this.this$0.mAdapter.notifyItemChanged(this.$position, Boxing.boxInt(1));
        }
        this.this$0.mAdapter.notifyItemChanged(this.$parentPos);
        return Unit.INSTANCE;
    }
}

package com.ykb.ebook.dialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ykb.ebook.R;
import com.ykb.ebook.base.BaseResponse;
import com.ykb.ebook.dialog.ReviewCommentDialog;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.model.BookReview;
import com.ykb.ebook.model.ParagraphComment;
import com.ykb.ebook.util.TopSmoothScroller;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\u00020\u00002\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/model/ParagraphComment;", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
@DebugMetadata(c = "com.ykb.ebook.dialog.ReviewCommentDialog$Builder$loadData$2", f = "ReviewCommentDialog.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
@SourceDebugExtension({"SMAP\nReviewCommentDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReviewCommentDialog.kt\ncom/ykb/ebook/dialog/ReviewCommentDialog$Builder$loadData$2\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,1157:1\n2634#2:1158\n2634#2:1160\n350#2,7:1163\n1#3:1159\n1#3:1161\n1#3:1162\n*S KotlinDebug\n*F\n+ 1 ReviewCommentDialog.kt\ncom/ykb/ebook/dialog/ReviewCommentDialog$Builder$loadData$2\n*L\n523#1:1158\n527#1:1160\n583#1:1163,7\n523#1:1159\n527#1:1161\n*E\n"})
/* loaded from: classes7.dex */
public final class ReviewCommentDialog$Builder$loadData$2 extends SuspendLambda implements Function3<CoroutineScope, BaseResponse<ParagraphComment>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Context $context;
    final /* synthetic */ boolean $isPutNewComment;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ReviewCommentDialog.Builder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReviewCommentDialog$Builder$loadData$2(ReviewCommentDialog.Builder builder, boolean z2, Context context, Continuation<? super ReviewCommentDialog$Builder$loadData$2> continuation) {
        super(3, continuation);
        this.this$0 = builder;
        this.$isPutNewComment = z2;
        this.$context = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invokeSuspend$lambda$5$lambda$4(ReviewCommentDialog.Builder builder) {
        SmartRefreshLayout refresh = builder.getRefresh();
        int measuredHeight = refresh != null ? refresh.getMeasuredHeight() : 0;
        if ((builder.getRecycler() != null ? r2.getMeasuredHeight() : 0) <= measuredHeight - builder.getContext().getResources().getDimension(R.dimen.dp_56) || !builder.hasHot) {
            LinearLayout llFloat = builder.getLlFloat();
            if (llFloat != null) {
                ViewExtensionsKt.gone(llFloat);
                return;
            }
            return;
        }
        LinearLayout llFloat2 = builder.getLlFloat();
        if (llFloat2 != null) {
            ViewExtensionsKt.visible(llFloat2);
        }
    }

    @Override // kotlin.jvm.functions.Function3
    @Nullable
    public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull BaseResponse<ParagraphComment> baseResponse, @Nullable Continuation<? super Unit> continuation) {
        ReviewCommentDialog$Builder$loadData$2 reviewCommentDialog$Builder$loadData$2 = new ReviewCommentDialog$Builder$loadData$2(this.this$0, this.$isPutNewComment, this.$context, continuation);
        reviewCommentDialog$Builder$loadData$2.L$0 = baseResponse;
        return reviewCommentDialog$Builder$loadData$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) {
        SmartRefreshLayout refresh;
        Object next;
        RecyclerView.LayoutManager layoutManager;
        TextView tvFloatTitle;
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        BaseResponse baseResponse = (BaseResponse) this.L$0;
        SmartRefreshLayout refresh2 = this.this$0.getRefresh();
        if (refresh2 != null) {
            refresh2.finishRefresh();
        }
        ParagraphComment paragraphComment = (ParagraphComment) baseResponse.getData();
        if (paragraphComment != null) {
            final ReviewCommentDialog.Builder builder = this.this$0;
            boolean z2 = this.$isPutNewComment;
            Context context = this.$context;
            ArrayList<BookReview> hot = paragraphComment.getHot();
            if (hot != null) {
                for (BookReview bookReview : hot) {
                    bookReview.setType(1);
                    bookReview.setHot(true);
                }
            }
            ArrayList<BookReview> timeLine = paragraphComment.getTimeLine();
            if (timeLine != null) {
                for (BookReview bookReview2 : timeLine) {
                    bookReview2.setType(2);
                    bookReview2.setHot(false);
                }
            }
            builder.allCount = paragraphComment.getTimeLineTotal();
            TextView tvTitle = builder.getTvTitle();
            if (tvTitle != null) {
                tvTitle.setText("全部段评 (" + builder.allCount + " 条)");
            }
            builder.timeLineCount = paragraphComment.getTimeLineTotal();
            if (builder.page == 1) {
                builder.hotCount = paragraphComment.getHotTotal();
                builder.mCommentList.clear();
                ArrayList<BookReview> hot2 = paragraphComment.getHot();
                if (!(hot2 == null || hot2.isEmpty())) {
                    builder.paragraphContent = paragraphComment.getHot().get(0).getParagraphContent();
                    TextView tvFloatTitle2 = builder.getTvFloatTitle();
                    if (tvFloatTitle2 != null) {
                        tvFloatTitle2.setText("最热评论 （" + builder.hotCount + "条）");
                    }
                    builder.hasHot = true;
                    builder.mCommentList.add(new BookReview(false, null, null, null, null, null, 0, 0, null, null, null, 0, null, null, null, 0, null, null, null, null, null, null, null, false, 1, "最热评论 （" + builder.hotCount + "条）", 0, null, null, false, false, 0, false, false, null, null, -50331649, 15, null));
                    builder.mCommentList.addAll(paragraphComment.getHot());
                }
                ArrayList<BookReview> timeLine2 = paragraphComment.getTimeLine();
                if (!(timeLine2 == null || timeLine2.isEmpty())) {
                    builder.paragraphContent = paragraphComment.getTimeLine().get(0).getParagraphContent();
                    ArrayList<BookReview> hot3 = paragraphComment.getHot();
                    if ((hot3 != null ? hot3.size() : 0) == 0 && (tvFloatTitle = builder.getTvFloatTitle()) != null) {
                        tvFloatTitle.setText("最新评论 （" + builder.timeLineCount + "条）");
                    }
                    if (builder.hasHot) {
                        builder.mCommentList.add(new BookReview(false, null, null, null, null, null, 0, 0, null, null, null, 0, null, null, null, 0, null, null, null, null, null, null, null, false, 2, "最新评论 （" + builder.timeLineCount + "条）", 0, null, null, false, false, 0, false, false, null, null, -50331649, 15, null));
                    }
                    builder.mCommentList.addAll(paragraphComment.getTimeLine());
                }
                if (builder.hotCount <= 0 || builder.timeLineCount <= 0) {
                    View viewFindViewById = builder.findViewById(R.id.ll_comment_wrap);
                    if (viewFindViewById != null) {
                        ViewExtensionsKt.gone(viewFindViewById);
                    }
                } else {
                    View viewFindViewById2 = builder.findViewById(R.id.ll_comment_wrap);
                    if (viewFindViewById2 != null) {
                        ViewExtensionsKt.visible(viewFindViewById2);
                    }
                }
                SmartRefreshLayout refresh3 = builder.getRefresh();
                if (refresh3 != null) {
                    refresh3.finishRefresh();
                }
                if (!builder.mAdapter.getItems().isEmpty()) {
                    builder.mAdapter.notifyDataSetChanged();
                } else if (!builder.mCommentList.isEmpty()) {
                    builder.mAdapter.submitList(builder.mCommentList);
                    if (builder.mCommentList.size() < 20 && (refresh = builder.getRefresh()) != null) {
                        refresh.finishLoadMoreWithNoMoreData();
                    }
                }
                if (z2) {
                    Iterator it = builder.mCommentList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                        if (((BookReview) next).getType() == 2) {
                            break;
                        }
                    }
                    if (next != null) {
                        Iterator it2 = builder.mCommentList.iterator();
                        int i2 = 0;
                        while (true) {
                            if (!it2.hasNext()) {
                                i2 = -1;
                                break;
                            }
                            if (((BookReview) it2.next()).getType() == 2) {
                                break;
                            }
                            i2++;
                        }
                        if (i2 != -1) {
                            TopSmoothScroller topSmoothScroller = new TopSmoothScroller(context);
                            topSmoothScroller.setTargetPosition(i2);
                            RecyclerView recycler = builder.getRecycler();
                            if (recycler != null && (layoutManager = recycler.getLayoutManager()) != null) {
                                layoutManager.startSmoothScroll(topSmoothScroller);
                            }
                        }
                    }
                }
            } else if (!builder.mCommentList.isEmpty()) {
                ArrayList<BookReview> hot4 = paragraphComment.getHot();
                if (!(hot4 == null || hot4.isEmpty())) {
                    builder.mCommentList.addAll(paragraphComment.getHot());
                    builder.mAdapter.addAll(paragraphComment.getHot());
                }
                ArrayList<BookReview> timeLine3 = paragraphComment.getTimeLine();
                if (!(timeLine3 == null || timeLine3.isEmpty())) {
                    builder.mCommentList.addAll(paragraphComment.getTimeLine());
                    builder.mAdapter.addAll(paragraphComment.getTimeLine());
                }
                ArrayList<BookReview> timeLine4 = paragraphComment.getTimeLine();
                if (timeLine4 == null || timeLine4.isEmpty()) {
                    SmartRefreshLayout refresh4 = builder.getRefresh();
                    if (refresh4 != null) {
                        refresh4.finishLoadMoreWithNoMoreData();
                    }
                } else {
                    SmartRefreshLayout refresh5 = builder.getRefresh();
                    if (refresh5 != null) {
                        refresh5.finishLoadMore();
                    }
                }
            }
            RecyclerView recycler2 = builder.getRecycler();
            if (recycler2 != null) {
                Boxing.boxBoolean(recycler2.postDelayed(new Runnable() { // from class: com.ykb.ebook.dialog.h1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ReviewCommentDialog$Builder$loadData$2.invokeSuspend$lambda$5$lambda$4(builder);
                    }
                }, 300L));
            }
        }
        return Unit.INSTANCE;
    }
}

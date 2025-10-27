package com.ykb.ebook.dialog;

import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ykb.ebook.base.BaseListResponse;
import com.ykb.ebook.base.BaseResponse;
import com.ykb.ebook.dialog.ChapterSearchDialog;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.model.TextSearchResult;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0006\u001a\u00020\u0005*\u00020\u00002\u0012\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/base/BaseListResponse;", "Lcom/ykb/ebook/model/TextSearchResult;", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
@DebugMetadata(c = "com.ykb.ebook.dialog.ChapterSearchDialog$Builder$loadData$2", f = "ChapterSearchDialog.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
@SourceDebugExtension({"SMAP\nChapterSearchDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ChapterSearchDialog.kt\ncom/ykb/ebook/dialog/ChapterSearchDialog$Builder$loadData$2\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,348:1\n1855#2,2:349\n*S KotlinDebug\n*F\n+ 1 ChapterSearchDialog.kt\ncom/ykb/ebook/dialog/ChapterSearchDialog$Builder$loadData$2\n*L\n258#1:349,2\n*E\n"})
/* loaded from: classes7.dex */
public final class ChapterSearchDialog$Builder$loadData$2 extends SuspendLambda implements Function3<CoroutineScope, BaseResponse<BaseListResponse<TextSearchResult>>, Continuation<? super Unit>, Object> {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ChapterSearchDialog.Builder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ChapterSearchDialog$Builder$loadData$2(ChapterSearchDialog.Builder builder, Continuation<? super ChapterSearchDialog$Builder$loadData$2> continuation) {
        super(3, continuation);
        this.this$0 = builder;
    }

    @Override // kotlin.jvm.functions.Function3
    @Nullable
    public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull BaseResponse<BaseListResponse<TextSearchResult>> baseResponse, @Nullable Continuation<? super Unit> continuation) {
        ChapterSearchDialog$Builder$loadData$2 chapterSearchDialog$Builder$loadData$2 = new ChapterSearchDialog$Builder$loadData$2(this.this$0, continuation);
        chapterSearchDialog$Builder$loadData$2.L$0 = baseResponse;
        return chapterSearchDialog$Builder$loadData$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [T, java.util.ArrayList] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) {
        List<TextSearchResult> listEmptyList;
        String count;
        SmartRefreshLayout refreshLayout;
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        BaseResponse baseResponse = (BaseResponse) this.L$0;
        BaseListResponse baseListResponse = (BaseListResponse) baseResponse.getData();
        if (baseListResponse == null || (listEmptyList = baseListResponse.getList()) == null) {
            listEmptyList = CollectionsKt__CollectionsKt.emptyList();
        }
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = new ArrayList();
        for (TextSearchResult textSearchResult : listEmptyList) {
            if (textSearchResult.getParagraphList() != null) {
                ((List) objectRef.element).add(textSearchResult);
            }
        }
        List list = (List) objectRef.element;
        BaseListResponse baseListResponse2 = (BaseListResponse) baseResponse.getData();
        if (baseListResponse2 == null || (count = baseListResponse2.getCount()) == null) {
            count = "0";
        }
        String str = (char) 20849 + count + "条相关搜索";
        TextView tvNumber = this.this$0.getTvNumber();
        if (tvNumber != null) {
            tvNumber.setText(str);
        }
        if (this.this$0.page == 1) {
            SmartRefreshLayout refreshLayout2 = this.this$0.getRefreshLayout();
            if (refreshLayout2 != null) {
                refreshLayout2.finishRefresh();
            }
            if (list.isEmpty()) {
                LinearLayout llEmpty = this.this$0.getLlEmpty();
                if (llEmpty != null) {
                    ViewExtensionsKt.visible(llEmpty);
                }
                LinearLayout llResult = this.this$0.getLlResult();
                if (llResult != null) {
                    ViewExtensionsKt.gone(llResult);
                }
                SmartRefreshLayout refreshLayout3 = this.this$0.getRefreshLayout();
                if (refreshLayout3 != null) {
                    refreshLayout3.finishLoadMoreWithNoMoreData();
                }
            } else {
                LinearLayout llEmpty2 = this.this$0.getLlEmpty();
                if (llEmpty2 != null) {
                    ViewExtensionsKt.gone(llEmpty2);
                }
                LinearLayout llResult2 = this.this$0.getLlResult();
                if (llResult2 != null) {
                    ViewExtensionsKt.visible(llResult2);
                }
                this.this$0.getAdapter().submitList(list);
                if (list.size() < this.this$0.pageSize && (refreshLayout = this.this$0.getRefreshLayout()) != null) {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }
        } else if (list.isEmpty()) {
            SmartRefreshLayout refreshLayout4 = this.this$0.getRefreshLayout();
            if (refreshLayout4 != null) {
                refreshLayout4.finishLoadMoreWithNoMoreData();
            }
        } else {
            this.this$0.getAdapter().addAll(list);
            if (list.size() < this.this$0.pageSize) {
                SmartRefreshLayout refreshLayout5 = this.this$0.getRefreshLayout();
                if (refreshLayout5 != null) {
                    refreshLayout5.finishLoadMoreWithNoMoreData();
                }
            } else {
                SmartRefreshLayout refreshLayout6 = this.this$0.getRefreshLayout();
                if (refreshLayout6 != null) {
                    refreshLayout6.finishLoadMore();
                }
            }
        }
        return Unit.INSTANCE;
    }
}

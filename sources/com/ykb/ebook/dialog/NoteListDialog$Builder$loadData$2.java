package com.ykb.ebook.dialog;

import androidx.recyclerview.widget.RecyclerView;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ykb.ebook.base.BaseListResponse;
import com.ykb.ebook.base.BaseResponse;
import com.ykb.ebook.dialog.NoteListDialog;
import com.ykb.ebook.model.NoteListBean;
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

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0006\u001a\u00020\u0005*\u00020\u00002\u0012\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/base/BaseListResponse;", "Lcom/ykb/ebook/model/NoteListBean;", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
@DebugMetadata(c = "com.ykb.ebook.dialog.NoteListDialog$Builder$loadData$2", f = "NoteListDialog.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes7.dex */
public final class NoteListDialog$Builder$loadData$2 extends SuspendLambda implements Function3<CoroutineScope, BaseResponse<BaseListResponse<NoteListBean>>, Continuation<? super Unit>, Object> {
    final /* synthetic */ boolean $isAddNote;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NoteListDialog.Builder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoteListDialog$Builder$loadData$2(NoteListDialog.Builder builder, boolean z2, Continuation<? super NoteListDialog$Builder$loadData$2> continuation) {
        super(3, continuation);
        this.this$0 = builder;
        this.$isAddNote = z2;
    }

    @Override // kotlin.jvm.functions.Function3
    @Nullable
    public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull BaseResponse<BaseListResponse<NoteListBean>> baseResponse, @Nullable Continuation<? super Unit> continuation) {
        NoteListDialog$Builder$loadData$2 noteListDialog$Builder$loadData$2 = new NoteListDialog$Builder$loadData$2(this.this$0, this.$isAddNote, continuation);
        noteListDialog$Builder$loadData$2.L$0 = baseResponse;
        return noteListDialog$Builder$loadData$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) {
        RecyclerView recycler;
        SmartRefreshLayout refresh;
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        BaseListResponse baseListResponse = (BaseListResponse) ((BaseResponse) this.L$0).getData();
        if (baseListResponse != null) {
            NoteListDialog.Builder builder = this.this$0;
            boolean z2 = this.$isAddNote;
            if (builder.page == 1) {
                SmartRefreshLayout refresh2 = builder.getRefresh();
                if (refresh2 != null) {
                    refresh2.finishRefresh();
                }
                if (!baseListResponse.getList().isEmpty()) {
                    builder.mAdapter.submitList(baseListResponse.getList());
                    if (baseListResponse.getList().size() < builder.pageSize && (refresh = builder.getRefresh()) != null) {
                        refresh.finishLoadMoreWithNoMoreData();
                    }
                    RecyclerView recycler2 = builder.getRecycler();
                    if (recycler2 != null) {
                        recycler2.scrollToPosition(0);
                    }
                }
                if (z2 && (recycler = builder.getRecycler()) != null) {
                    recycler.smoothScrollToPosition(0);
                }
            } else if (!baseListResponse.getList().isEmpty()) {
                builder.mAdapter.addAll(baseListResponse.getList());
                if (baseListResponse.getList().size() < builder.pageSize) {
                    SmartRefreshLayout refresh3 = builder.getRefresh();
                    if (refresh3 != null) {
                        refresh3.finishLoadMoreWithNoMoreData();
                    }
                } else {
                    SmartRefreshLayout refresh4 = builder.getRefresh();
                    if (refresh4 != null) {
                        refresh4.finishLoadMore();
                    }
                }
            }
        }
        return Unit.INSTANCE;
    }
}

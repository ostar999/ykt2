package com.psychiatrygarden.fragmenthome;

import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.psychiatrygarden.adapter.CourseCommentAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000  2\u00020\u0001:\u0001 B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u000eH\u0014J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\u0018\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0014J\b\u0010\u001d\u001a\u00020\u0017H\u0002J\b\u0010\u001e\u001a\u00020\u0017H\u0002J\b\u0010\u001f\u001a\u00020\u0017H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lcom/psychiatrygarden/fragmenthome/CourseOrGoodsCommentFragment;", "Lcom/psychiatrygarden/baseview/BaseFragment;", "()V", "goodsComment", "", "goodsId", "", "hasInitScroller", "hasMore", "isLoadMore", "isLoading", "mAdapter", "Lcom/psychiatrygarden/adapter/CourseCommentAdapter;", "requestPage", "", "rvComments", "Landroidx/recyclerview/widget/RecyclerView;", "showCount", "showLongPicTag", "totalCount", "typeStr", "getLayoutId", "initScroll", "", "initViews", "holder", "Lcom/psychiatrygarden/baseview/ViewHolder;", "root", "Landroid/view/View;", "loadApiData", "loadMore", "refresh", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class CourseOrGoodsCommentFragment extends BaseFragment {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private boolean goodsComment;
    private boolean hasInitScroller;
    private boolean hasMore;
    private boolean isLoadMore;
    private boolean isLoading;
    private RecyclerView rvComments;
    private boolean showLongPicTag;
    private int totalCount;

    @NotNull
    private CourseCommentAdapter mAdapter = new CourseCommentAdapter();

    @NotNull
    private String typeStr = "all";

    @NotNull
    private String goodsId = "";
    private int showCount = -1;
    private int requestPage = 1;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/psychiatrygarden/fragmenthome/CourseOrGoodsCommentFragment$Companion;", "", "()V", "newInstance", "Lcom/psychiatrygarden/fragmenthome/CourseOrGoodsCommentFragment;", AliyunLogKey.KEY_ARGS, "Landroid/os/Bundle;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nCourseOrGoodsCommentFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CourseOrGoodsCommentFragment.kt\ncom/psychiatrygarden/fragmenthome/CourseOrGoodsCommentFragment$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,179:1\n1#2:180\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final CourseOrGoodsCommentFragment newInstance(@NotNull Bundle args) {
            Intrinsics.checkNotNullParameter(args, "args");
            CourseOrGoodsCommentFragment courseOrGoodsCommentFragment = new CourseOrGoodsCommentFragment();
            courseOrGoodsCommentFragment.setArguments(args);
            return courseOrGoodsCommentFragment;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initScroll() {
        RecyclerView recyclerView = this.rvComments;
        RecyclerView recyclerView2 = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvComments");
            recyclerView = null;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        Intrinsics.checkNotNull(layoutManager, "null cannot be cast to non-null type androidx.recyclerview.widget.LinearLayoutManager");
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
        RecyclerView recyclerView3 = this.rvComments;
        if (recyclerView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvComments");
        } else {
            recyclerView2 = recyclerView3;
        }
        recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.fragmenthome.CourseOrGoodsCommentFragment.initScroll.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NotNull RecyclerView recyclerView4, int dx, int dy) {
                Intrinsics.checkNotNullParameter(recyclerView4, "recyclerView");
                super.onScrolled(recyclerView4, dx, dy);
                if (linearLayoutManager.findLastVisibleItemPosition() + 3 < this.mAdapter.getData().size() || !this.hasMore || this.isLoading) {
                    return;
                }
                LogUtils.d("onScrolled", "预加载数据");
                this.loadMore();
            }
        });
        this.hasInitScroller = true;
    }

    private final void loadApiData() {
        AjaxParams ajaxParams = new AjaxParams();
        if (this.goodsComment) {
            ajaxParams.put("obj_id", this.goodsId);
            ajaxParams.put("module_type", Constants.VIA_SHARE_TYPE_MINI_PROGRAM);
            ajaxParams.put("comment_type", "1");
            ajaxParams.put(DatabaseManager.SIZE, Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
            ajaxParams.put("type", "all");
        } else {
            ajaxParams.put("page_size", Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
            ajaxParams.put("course_id", this.goodsId);
        }
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(this.requestPage));
        this.isLoading = true;
        YJYHttpUtils.post(getContext(), !this.goodsComment ? NetworkRequestsURL.courseCommentList : NetworkRequestsURL.mCommentList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.CourseOrGoodsCommentFragment.loadApiData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (CourseOrGoodsCommentFragment.this.requestPage > 1) {
                    CourseOrGoodsCommentFragment courseOrGoodsCommentFragment = CourseOrGoodsCommentFragment.this;
                    courseOrGoodsCommentFragment.requestPage--;
                }
                CourseOrGoodsCommentFragment.this.isLoading = false;
            }

            /* JADX WARN: Removed duplicated region for block: B:17:0x006c  */
            @Override // net.tsz.afinal.http.AjaxCallBack
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onSuccess(@org.jetbrains.annotations.NotNull java.lang.String r7) throws java.lang.NumberFormatException {
                /*
                    Method dump skipped, instructions count: 399
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.fragmenthome.CourseOrGoodsCommentFragment.C06021.onSuccess(java.lang.String):void");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadMore() {
        this.isLoadMore = true;
        this.requestPage++;
        loadApiData();
    }

    private final void refresh() {
        this.isLoadMore = false;
        this.requestPage = 1;
        loadApiData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fmt_course_goods_comment;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(@NotNull ViewHolder holder, @NotNull View root) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(root, "root");
        View view = holder.get(R.id.rvComments);
        Intrinsics.checkNotNullExpressionValue(view, "holder.get(R.id.rvComments)");
        this.rvComments = (RecyclerView) view;
        Bundle arguments = getArguments();
        this.showCount = arguments != null ? arguments.getInt("showCount") : -1;
        Bundle arguments2 = getArguments();
        this.goodsComment = arguments2 != null ? arguments2.getBoolean("goodsComment", false) : false;
        Bundle arguments3 = getArguments();
        this.showLongPicTag = arguments3 != null ? arguments3.getBoolean("show_long_pic_tag", false) : false;
        Bundle arguments4 = getArguments();
        RecyclerView recyclerView = null;
        String string = arguments4 != null ? arguments4.getString("goods_id") : null;
        if (string == null) {
            string = "";
        }
        this.goodsId = string;
        RecyclerView recyclerView2 = this.rvComments;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvComments");
        } else {
            recyclerView = recyclerView2;
        }
        recyclerView.setAdapter(this.mAdapter);
        this.mAdapter.setEmptyView(new CustomEmptyView(this.mContext, 0, "暂无评论"));
        refresh();
    }
}

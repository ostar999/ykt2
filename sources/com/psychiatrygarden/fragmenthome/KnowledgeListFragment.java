package com.psychiatrygarden.fragmenthome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.online.ChartAnswerSheetActivity;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.ChartFilterBean;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.bean.KnowledgeNodeItemBean;
import com.psychiatrygarden.bean.NextNodeData;
import com.psychiatrygarden.bean.NodeKnowledgeInfo;
import com.psychiatrygarden.bean.NodeRefreshEvent;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.KnowledgeListFragment;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.PopKnowledgeChartFilter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.util.FastClickUtilKt;
import de.greenrobot.event.EventBus;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000\u0099\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005*\u0003\u001a\u001d \u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u00102\u001a\u00020\t2\u0006\u00103\u001a\u00020\t2\u0006\u00104\u001a\u00020\tH\u0002J\b\u00105\u001a\u000206H\u0002J\b\u00107\u001a\u00020%H\u0014J\u0006\u00108\u001a\u00020\rJ\b\u00109\u001a\u000206H\u0002J\b\u0010:\u001a\u000206H\u0002J\u001a\u0010;\u001a\u0002062\u0006\u0010<\u001a\u00020=2\b\u0010>\u001a\u0004\u0018\u00010\u0016H\u0014J*\u0010?\u001a\u0002062\n\b\u0002\u0010@\u001a\u0004\u0018\u00010A2\n\b\u0002\u0010*\u001a\u0004\u0018\u00010+2\b\b\u0002\u0010B\u001a\u00020\rH\u0002J\u0014\u0010C\u001a\u0002062\n\u0010D\u001a\u0006\u0012\u0002\b\u00030EH\u0007J\u0010\u0010C\u001a\u0002062\u0006\u0010F\u001a\u00020GH\u0007J\u0006\u0010H\u001a\u000206J\u0010\u0010I\u001a\u0002062\u0006\u0010J\u001a\u00020\tH\u0002J\b\u0010K\u001a\u000206H\u0002R\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001bR\u0010\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001eR\u0010\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0004\n\u0002\u0010!R\u000e\u0010\"\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020%X\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010'\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020)X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020+X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020-X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020-X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u000200X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u000200X\u0082.¢\u0006\u0002\n\u0000¨\u0006L"}, d2 = {"Lcom/psychiatrygarden/fragmenthome/KnowledgeListFragment;", "Lcom/psychiatrygarden/baseview/BaseFragment;", "()V", "chartFilterList", "Ljava/util/ArrayList;", "Lcom/psychiatrygarden/bean/ChartFilterBean;", "Lkotlin/collections/ArrayList;", "defaultFilterMap", "Landroid/util/ArrayMap;", "", "", "desc", "hasFilter", "", "hasInitScroller", "hasMoreData", "identity_id", "isFirstLoadingData", "isLoading", "isRefresh", "isUserScrolling", "llFilterInfo", "Landroid/view/View;", "loadingView", "Lcom/psychiatrygarden/widget/CustomEmptyView;", "mFilterShowAdapter", "com/psychiatrygarden/fragmenthome/KnowledgeListFragment$mFilterShowAdapter$1", "Lcom/psychiatrygarden/fragmenthome/KnowledgeListFragment$mFilterShowAdapter$1;", "mNodeAdapter", "com/psychiatrygarden/fragmenthome/KnowledgeListFragment$mNodeAdapter$1", "Lcom/psychiatrygarden/fragmenthome/KnowledgeListFragment$mNodeAdapter$1;", "mScrollListener", "com/psychiatrygarden/fragmenthome/KnowledgeListFragment$mScrollListener$1", "Lcom/psychiatrygarden/fragmenthome/KnowledgeListFragment$mScrollListener$1;", "nodeId", "nodeParentId", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "question_bank_id", "refresh", "Lcom/scwang/smartrefresh/layout/SmartRefreshLayout;", "requestParams", "Lnet/tsz/afinal/http/AjaxParams;", "rvChartList", "Landroidx/recyclerview/widget/RecyclerView;", "rvFilter", "tvDesc", "Landroid/widget/TextView;", "tvFilterInfo", "formatFilter", "input", "title", "getFilterData", "", "getLayoutId", "hasPermission", com.umeng.socialize.tracker.a.f23806c, "initScroll", "initViews", "holder", "Lcom/psychiatrygarden/baseview/ViewHolder;", "root", "loadChartNodeData", "p", "Lorg/json/JSONObject;", "isFilter", "onEventMainThread", NotificationCompat.CATEGORY_EVENT, "Lcom/psychiatrygarden/bean/EventBusMessage;", AliyunLogKey.KEY_EVENT, "Lcom/psychiatrygarden/bean/NodeRefreshEvent;", "refreshList", "refreshNodeInfo", "id", "showFilterPoP", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nKnowledgeListFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 KnowledgeListFragment.kt\ncom/psychiatrygarden/fragmenthome/KnowledgeListFragment\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 Iterators.kt\nkotlin/collections/CollectionsKt__IteratorsKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,665:1\n1#2:666\n32#3,2:667\n1559#4:669\n1590#4,4:670\n766#4:674\n857#4,2:675\n1855#4,2:677\n1855#4,2:679\n350#4,7:681\n1855#4,2:688\n*S KotlinDebug\n*F\n+ 1 KnowledgeListFragment.kt\ncom/psychiatrygarden/fragmenthome/KnowledgeListFragment\n*L\n194#1:667,2\n384#1:669\n384#1:670,4\n396#1:674\n396#1:675,2\n427#1:677,2\n560#1:679,2\n565#1:681,7\n147#1:688,2\n*E\n"})
/* loaded from: classes5.dex */
public final class KnowledgeListFragment extends BaseFragment {

    @Nullable
    private String desc;
    private boolean hasFilter;
    private boolean hasInitScroller;
    private boolean hasMoreData;

    @Nullable
    private String identity_id;
    private boolean isLoading;
    private boolean isRefresh;
    private boolean isUserScrolling;
    private View llFilterInfo;
    private CustomEmptyView loadingView;
    private String nodeId;
    private String nodeParentId;

    @Nullable
    private String question_bank_id;
    private SmartRefreshLayout refresh;
    private RecyclerView rvChartList;
    private RecyclerView rvFilter;
    private TextView tvDesc;
    private TextView tvFilterInfo;

    @NotNull
    private final ArrayList<ChartFilterBean> chartFilterList = new ArrayList<>();
    private int page = 1;
    private final int pageSize = 50;

    @NotNull
    private AjaxParams requestParams = new AjaxParams();
    private boolean isFirstLoadingData = true;

    @NotNull
    private final KnowledgeListFragment$mFilterShowAdapter$1 mFilterShowAdapter = new BaseQuickAdapter<String, BaseViewHolder>() { // from class: com.psychiatrygarden.fragmenthome.KnowledgeListFragment$mFilterShowAdapter$1
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder holder, @NotNull String item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(item, "item");
            View view = holder.itemView;
            Intrinsics.checkNotNull(view, "null cannot be cast to non-null type android.widget.TextView");
            ((TextView) view).setText(item);
        }
    };

    @NotNull
    private final KnowledgeListFragment$mNodeAdapter$1 mNodeAdapter = new KnowledgeListFragment$mNodeAdapter$1(this);

    @NotNull
    private final KnowledgeListFragment$mScrollListener$1 mScrollListener = new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.fragmenthome.KnowledgeListFragment$mScrollListener$1
        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int state) {
            Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
            super.onScrollStateChanged(recyclerView, state);
            if (state == 0) {
                this.this$0.isUserScrolling = false;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) throws JSONException {
            Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
            super.onScrolled(recyclerView, dx, dy);
            RecyclerView recyclerView2 = this.this$0.rvChartList;
            if (recyclerView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rvChartList");
                recyclerView2 = null;
            }
            RecyclerView.LayoutManager layoutManager = recyclerView2.getLayoutManager();
            Intrinsics.checkNotNull(layoutManager, "null cannot be cast to non-null type androidx.recyclerview.widget.LinearLayoutManager");
            if (((LinearLayoutManager) layoutManager).findLastVisibleItemPosition() + 3 >= this.this$0.mNodeAdapter.getData().size() && this.this$0.hasMoreData && !this.this$0.isLoading && this.this$0.hasInitScroller && this.this$0.isUserScrolling) {
                LogUtils.d("onScrolled", "预加载数据");
                AjaxParams ajaxParams = this.this$0.requestParams;
                KnowledgeListFragment knowledgeListFragment = this.this$0;
                knowledgeListFragment.page++;
                ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(knowledgeListFragment.page));
                this.this$0.isLoading = true;
                KnowledgeListFragment knowledgeListFragment2 = this.this$0;
                KnowledgeListFragment.loadChartNodeData$default(knowledgeListFragment2, null, knowledgeListFragment2.requestParams, false, 4, null);
            }
        }
    };

    @NotNull
    private final ArrayMap<String, List<String>> defaultFilterMap = new ArrayMap<>();

    @Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"com/psychiatrygarden/fragmenthome/KnowledgeListFragment$initData$3", "Lcom/chad/library/adapter/base/listener/OnItemClickListener;", "onItemClick", "", "adapter", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "view", "Landroid/view/View;", "position", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nKnowledgeListFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 KnowledgeListFragment.kt\ncom/psychiatrygarden/fragmenthome/KnowledgeListFragment$initData$3\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,665:1\n1864#2,3:666\n*S KotlinDebug\n*F\n+ 1 KnowledgeListFragment.kt\ncom/psychiatrygarden/fragmenthome/KnowledgeListFragment$initData$3\n*L\n226#1:666,3\n*E\n"})
    /* renamed from: com.psychiatrygarden.fragmenthome.KnowledgeListFragment$initData$3, reason: invalid class name */
    public static final class AnonymousClass3 implements OnItemClickListener {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onItemClick$lambda$3(KnowledgeNodeItemBean item, BaseQuickAdapter adapter, int i2, final KnowledgeListFragment this$0) {
            Intrinsics.checkNotNullParameter(item, "$item");
            Intrinsics.checkNotNullParameter(adapter, "$adapter");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            item.setIs_permission("1");
            adapter.notifyItemChanged(i2);
            CommonUtil.mPutShareData(((BaseFragment) this$0).mContext, item.getActivity_id(), null, null, new CommonUtil.ClickShareLisenter() { // from class: com.psychiatrygarden.fragmenthome.s7
                @Override // com.psychiatrygarden.utils.CommonUtil.ClickShareLisenter
                public final void refreshData() {
                    KnowledgeListFragment.AnonymousClass3.onItemClick$lambda$3$lambda$2(this$0);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onItemClick$lambda$3$lambda$2(final KnowledgeListFragment this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.w7
                @Override // java.lang.Runnable
                public final void run() {
                    KnowledgeListFragment.AnonymousClass3.onItemClick$lambda$3$lambda$2$lambda$1(this$0);
                }
            }, 500L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onItemClick$lambda$3$lambda$2$lambda$1(KnowledgeListFragment this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.refreshList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onItemClick$lambda$6(KnowledgeNodeItemBean item, BaseQuickAdapter adapter, int i2, final KnowledgeListFragment this$0, String str) {
            Intrinsics.checkNotNullParameter(item, "$item");
            Intrinsics.checkNotNullParameter(adapter, "$adapter");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            item.setIs_permission("1");
            adapter.notifyItemChanged(i2);
            CommonUtil.mPutShareData(((BaseFragment) this$0).mContext, item.getActivity_id(), null, null, new CommonUtil.ClickShareLisenter() { // from class: com.psychiatrygarden.fragmenthome.r7
                @Override // com.psychiatrygarden.utils.CommonUtil.ClickShareLisenter
                public final void refreshData() {
                    KnowledgeListFragment.AnonymousClass3.onItemClick$lambda$6$lambda$5(this$0);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onItemClick$lambda$6$lambda$5(final KnowledgeListFragment this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.t7
                @Override // java.lang.Runnable
                public final void run() {
                    KnowledgeListFragment.AnonymousClass3.onItemClick$lambda$6$lambda$5$lambda$4(this$0);
                }
            }, 500L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onItemClick$lambda$6$lambda$5$lambda$4(KnowledgeListFragment this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.refreshList();
        }

        @Override // com.chad.library.adapter.base.listener.OnItemClickListener
        public void onItemClick(@NotNull final BaseQuickAdapter<?, ?> adapter, @NotNull View view, final int position) {
            Intrinsics.checkNotNullParameter(adapter, "adapter");
            Intrinsics.checkNotNullParameter(view, "view");
            if (!KnowledgeListFragment.this.isLogin() || FastClickUtilKt.isFastClick()) {
                return;
            }
            final KnowledgeNodeItemBean item = KnowledgeListFragment.this.mNodeAdapter.getItem(position);
            ProjectApp.clearNodeList();
            List<KnowledgeNodeItemBean> data = KnowledgeListFragment.this.mNodeAdapter.getData();
            ArrayList arrayList = new ArrayList();
            int i2 = 0;
            for (Object obj : data) {
                int i3 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                }
                KnowledgeNodeItemBean knowledgeNodeItemBean = (KnowledgeNodeItemBean) obj;
                if (i2 > position) {
                    arrayList.add(new NextNodeData(knowledgeNodeItemBean.getId(), knowledgeNodeItemBean.getName()));
                }
                i2 = i3;
            }
            if (!arrayList.isEmpty()) {
                ProjectApp.setNodeList(arrayList);
            }
            if (Intrinsics.areEqual(item.getIs_permission(), "0")) {
                MemInterface memInterface = MemInterface.getInstance();
                final KnowledgeListFragment knowledgeListFragment = KnowledgeListFragment.this;
                memInterface.setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.fragmenthome.u7
                    @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                    public final void mUShareListener() {
                        KnowledgeListFragment.AnonymousClass3.onItemClick$lambda$3(item, adapter, position, knowledgeListFragment);
                    }
                });
                MemInterface memInterface2 = MemInterface.getInstance();
                final KnowledgeListFragment knowledgeListFragment2 = KnowledgeListFragment.this;
                memInterface2.setShareSuccessListener(new MemInterface.ShareSuccessListener() { // from class: com.psychiatrygarden.fragmenthome.v7
                    @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.ShareSuccessListener
                    public final void shareSuccess(String str) {
                        KnowledgeListFragment.AnonymousClass3.onItemClick$lambda$6(item, adapter, position, knowledgeListFragment2, str);
                    }
                });
                MemInterface memInterface3 = MemInterface.getInstance();
                FragmentActivity activity = KnowledgeListFragment.this.getActivity();
                AjaxParams ajaxParams = new AjaxParams();
                ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, item.getActivity_id());
                ajaxParams.put("alwaysShow", "1");
                Unit unit = Unit.INSTANCE;
                memInterface3.getMemData((Activity) activity, ajaxParams, NetworkRequestsURL.vipApi, 0, false);
                return;
            }
            KnowledgeListFragment knowledgeListFragment3 = KnowledgeListFragment.this;
            Intent intentPutExtra = new Intent(((BaseFragment) KnowledgeListFragment.this).mContext, (Class<?>) ChartAnswerSheetActivity.class).putExtra("knowledge_id", item.getId()).putExtra("desc", KnowledgeListFragment.this.desc).putExtra("bottom_statistics", true).putExtra("hasNextNode", !arrayList.isEmpty()).putExtra("isKnowledge", true).putExtra("question_bank_id", KnowledgeListFragment.this.question_bank_id);
            String str = KnowledgeListFragment.this.nodeId;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("nodeId");
                str = null;
            }
            Intent intentPutExtra2 = intentPutExtra.putExtra("node_id", str).putExtra("identity_id", KnowledgeListFragment.this.identity_id);
            String str2 = KnowledgeListFragment.this.nodeParentId;
            if (str2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("nodeParentId");
                str2 = null;
            }
            Intent intentPutExtra3 = intentPutExtra2.putExtra("node_parent_id", str2).putExtra("paperTitle", item.getName());
            Bundle arguments = KnowledgeListFragment.this.getArguments();
            knowledgeListFragment3.startActivity(intentPutExtra3.putExtra("title", arguments != null ? arguments.getString("title") : null));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String formatFilter(String input, String title) {
        List listSplit$default = StringsKt__StringsKt.split$default((CharSequence) input, new char[]{'/'}, false, 0, 6, (Object) null);
        List list = listSplit$default;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        int i2 = 0;
        for (Object obj : list) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            String strRemoveSuffix = (String) obj;
            if (i2 < CollectionsKt__CollectionsKt.getLastIndex(listSplit$default) && StringsKt__StringsJVMKt.endsWith$default(strRemoveSuffix, title, false, 2, null)) {
                strRemoveSuffix = StringsKt__StringsKt.removeSuffix(strRemoveSuffix, (CharSequence) title);
            }
            arrayList.add(strRemoveSuffix);
            i2 = i3;
        }
        return CollectionsKt___CollectionsKt.joinToString$default(arrayList, "/", null, null, 0, null, null, 62, null);
    }

    private final void getFilterData() {
        this.chartFilterList.clear();
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.questionChartFilter, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.KnowledgeListFragment.getFilterData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((AnonymousClass1) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        if (jSONObjectOptJSONObject == null) {
                            jSONObjectOptJSONObject = new JSONObject();
                        }
                        String strOptString = jSONObjectOptJSONObject.optString("avatar");
                        String strOptString2 = jSONObjectOptJSONObject.optString("describe");
                        String strOptString3 = jSONObjectOptJSONObject.optString("knowledge_img_dark");
                        Intrinsics.checkNotNullExpressionValue(strOptString3, "data.optString(\"knowledge_img_dark\")");
                        String strOptString4 = jSONObjectOptJSONObject.optString("knowledge_img");
                        Intrinsics.checkNotNullExpressionValue(strOptString4, "data.optString(\"knowledge_img\")");
                        String strOptString5 = jSONObjectOptJSONObject.optString("detail_img");
                        Intrinsics.checkNotNullExpressionValue(strOptString5, "data.optString(\"detail_img\")");
                        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ((BaseFragment) KnowledgeListFragment.this).mContext);
                        if (!TextUtils.isEmpty(strOptString5)) {
                            SharePreferencesUtils.writeStrConfig(CommonParameter.FILTER_OPTION_DETAIL_IMG + strConfig, strOptString5, ((BaseFragment) KnowledgeListFragment.this).mContext);
                        }
                        if (!TextUtils.isEmpty(strOptString) && !TextUtils.isEmpty(strOptString2)) {
                            SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_TIPS_TEXT, strOptString2, ((BaseFragment) KnowledgeListFragment.this).mContext);
                            SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_TIPS_ICON, strOptString, ((BaseFragment) KnowledgeListFragment.this).mContext);
                        }
                        SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_TIPS_DESC, strOptString4, ((BaseFragment) KnowledgeListFragment.this).mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_TIPS_DESC_DARK, strOptString3, ((BaseFragment) KnowledgeListFragment.this).mContext);
                        List<ChartFilterBean> list = (List) new Gson().fromJson(jSONObjectOptJSONObject.optString("list"), new TypeToken<List<? extends ChartFilterBean>>() { // from class: com.psychiatrygarden.fragmenthome.KnowledgeListFragment$getFilterData$1$onSuccess$list$1
                        }.getType());
                        List list2 = list;
                        if (!(list2 == null || list2.isEmpty())) {
                            Intrinsics.checkNotNullExpressionValue(list, "list");
                            for (ChartFilterBean chartFilterBean : list) {
                                List<ChartFilterBean.ChartFilterValue> value = chartFilterBean.getValue();
                                Intrinsics.checkNotNullExpressionValue(value, "it.value");
                                Iterator<T> it = value.iterator();
                                while (it.hasNext()) {
                                    ((ChartFilterBean.ChartFilterValue) it.next()).setType(chartFilterBean.getType());
                                }
                            }
                            KnowledgeListFragment.this.chartFilterList.addAll(list);
                            SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_LIST, new Gson().toJson(KnowledgeListFragment.this.chartFilterList), ((BaseFragment) KnowledgeListFragment.this).mContext);
                        }
                        KnowledgeListFragment.this.showFilterPoP();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private final void initData() throws JSONException {
        String string;
        String string2;
        Bundle arguments = getArguments();
        String str = "";
        RecyclerView recyclerView = null;
        String string3 = arguments != null ? arguments.getString("node_id", "") : null;
        if (string3 == null) {
            string3 = "";
        }
        this.nodeId = string3;
        Bundle arguments2 = getArguments();
        String string4 = arguments2 != null ? arguments2.getString("node_parent_id", "") : null;
        if (string4 == null) {
            string4 = "";
        }
        this.nodeParentId = string4;
        Bundle arguments3 = getArguments();
        if (arguments3 == null || (string = arguments3.getString("question_bank_id", "")) == null) {
            string = "";
        }
        this.question_bank_id = string;
        Bundle arguments4 = getArguments();
        if (arguments4 != null && (string2 = arguments4.getString("identity_id", "")) != null) {
            str = string2;
        }
        this.identity_id = str;
        Bundle arguments5 = getArguments();
        this.desc = arguments5 != null ? arguments5.getString("desc") : null;
        TextView textView = this.tvDesc;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
            textView = null;
        }
        textView.setText(this.desc);
        String str2 = this.desc;
        if (str2 == null || str2.length() == 0) {
            TextView textView2 = this.tvDesc;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
                textView2 = null;
            }
            ViewExtensionsKt.gone(textView2);
        } else {
            TextView textView3 = this.tvDesc;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
                textView3 = null;
            }
            ViewExtensionsKt.visible(textView3);
            TextView textView4 = this.tvDesc;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
                textView4 = null;
            }
            textView4.requestFocus();
            TextView textView5 = this.tvDesc;
            if (textView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
                textView5 = null;
            }
            textView5.setSelected(true);
        }
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.KNOWLEDGE_FILTER_VALUE, this.mContext);
        String filterShowStr = SharePreferencesUtils.readStrConfig(CommonParameter.KNOWLEDGE_FILTER_STR, this.mContext);
        if (!TextUtils.isEmpty(filterShowStr)) {
            Intrinsics.checkNotNullExpressionValue(filterShowStr, "filterShowStr");
            List listSplit$default = StringsKt__StringsKt.split$default((CharSequence) filterShowStr, new String[]{","}, false, 0, 6, (Object) null);
            RecyclerView recyclerView2 = this.rvFilter;
            if (recyclerView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rvFilter");
                recyclerView2 = null;
            }
            KnowledgeListFragment$mFilterShowAdapter$1 knowledgeListFragment$mFilterShowAdapter$1 = this.mFilterShowAdapter;
            knowledgeListFragment$mFilterShowAdapter$1.setList(listSplit$default);
            recyclerView2.setAdapter(knowledgeListFragment$mFilterShowAdapter$1);
            TextView textView6 = this.tvDesc;
            if (textView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
                textView6 = null;
            }
            ViewExtensionsKt.gone(textView6);
            RecyclerView recyclerView3 = this.rvFilter;
            if (recyclerView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rvFilter");
            } else {
                recyclerView = recyclerView3;
            }
            ViewExtensionsKt.visible(recyclerView);
            this.hasFilter = true;
        }
        if (!TextUtils.isEmpty(strConfig)) {
            try {
                JSONObject jSONObject = new JSONObject(strConfig);
                Iterator<String> keys = jSONObject.keys();
                Intrinsics.checkNotNullExpressionValue(keys, "keys");
                while (keys.hasNext()) {
                    String next = keys.next();
                    String value = jSONObject.getString(next);
                    ArrayMap<String, List<String>> arrayMap = this.defaultFilterMap;
                    Intrinsics.checkNotNullExpressionValue(value, "value");
                    arrayMap.put(next, StringsKt__StringsKt.split$default((CharSequence) value, new String[]{","}, false, 0, 6, (Object) null));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        this.mNodeAdapter.setOnItemClickListener(new AnonymousClass3());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initScroll() {
        RecyclerView recyclerView = this.rvChartList;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvChartList");
            recyclerView = null;
        }
        recyclerView.addOnScrollListener(this.mScrollListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initViews$lambda$0(KnowledgeListFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (!this$0.chartFilterList.isEmpty()) {
            this$0.showFilterPoP();
        } else {
            this$0.getFilterData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initViews$lambda$2(KnowledgeListFragment this$0, RefreshLayout it) throws JSONException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.hasInitScroller = false;
        this$0.isRefresh = true;
        this$0.page = 1;
        ConcurrentHashMap<String, String> param = this$0.requestParams.getParam();
        if (param != null) {
            param.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "1");
        }
        JSONObject jSONObject = new JSONObject();
        if (this$0.defaultFilterMap.size() <= 0) {
            this$0.loadChartNodeData(new JSONObject(), this$0.requestParams, false);
            return;
        }
        Set<Map.Entry<String, List<String>>> setEntrySet = this$0.defaultFilterMap.entrySet();
        Intrinsics.checkNotNullExpressionValue(setEntrySet, "defaultFilterMap.entries");
        Iterator<T> it2 = setEntrySet.iterator();
        while (it2.hasNext()) {
            Map.Entry entry = (Map.Entry) it2.next();
            jSONObject.put((String) entry.getKey(), entry.getValue());
        }
        this$0.loadChartNodeData(jSONObject, this$0.requestParams, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean initViews$lambda$3(KnowledgeListFragment this$0, View view, MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (motionEvent.getAction() != 2) {
            return false;
        }
        this$0.isUserScrolling = true;
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void loadChartNodeData(org.json.JSONObject r21, net.tsz.afinal.http.AjaxParams r22, boolean r23) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 309
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.fragmenthome.KnowledgeListFragment.loadChartNodeData(org.json.JSONObject, net.tsz.afinal.http.AjaxParams, boolean):void");
    }

    public static /* synthetic */ void loadChartNodeData$default(KnowledgeListFragment knowledgeListFragment, JSONObject jSONObject, AjaxParams ajaxParams, boolean z2, int i2, Object obj) throws JSONException {
        if ((i2 & 1) != 0) {
            jSONObject = null;
        }
        if ((i2 & 2) != 0) {
            ajaxParams = null;
        }
        if ((i2 & 4) != 0) {
            z2 = false;
        }
        knowledgeListFragment.loadChartNodeData(jSONObject, ajaxParams, z2);
    }

    private final void refreshNodeInfo(final String id) {
        Context context = this.mContext;
        String str = NetworkRequestsURL.nodeKnowledgeInfo;
        AjaxParams ajaxParams = new AjaxParams();
        String str2 = this.nodeId;
        if (str2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nodeId");
            str2 = null;
        }
        ajaxParams.put("node_id", str2);
        ajaxParams.put("knowledge_id", id);
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.post(context, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.KnowledgeListFragment.refreshNodeInfo.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                List<NodeKnowledgeInfo.KnowledgeInfo> knowledge;
                Object obj;
                Object next;
                int iIndexOf;
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((C06172) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        NodeKnowledgeInfo nodeKnowledgeInfo = (NodeKnowledgeInfo) new Gson().fromJson(jSONObject.optString("data"), NodeKnowledgeInfo.class);
                        if (nodeKnowledgeInfo == null || (knowledge = nodeKnowledgeInfo.getKnowledge()) == null) {
                            return;
                        }
                        String str3 = id;
                        Iterator<T> it = knowledge.iterator();
                        while (true) {
                            obj = null;
                            if (!it.hasNext()) {
                                next = null;
                                break;
                            } else {
                                next = it.next();
                                if (Intrinsics.areEqual(((NodeKnowledgeInfo.KnowledgeInfo) next).getKnowledge_id(), str3)) {
                                    break;
                                }
                            }
                        }
                        NodeKnowledgeInfo.KnowledgeInfo knowledgeInfo = (NodeKnowledgeInfo.KnowledgeInfo) next;
                        if (knowledgeInfo == null) {
                            return;
                        }
                        List<KnowledgeNodeItemBean> data = KnowledgeListFragment.this.mNodeAdapter.getData();
                        String str4 = id;
                        Iterator<T> it2 = data.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            Object next2 = it2.next();
                            if (Intrinsics.areEqual(((KnowledgeNodeItemBean) next2).getId(), str4)) {
                                obj = next2;
                                break;
                            }
                        }
                        KnowledgeNodeItemBean knowledgeNodeItemBean = (KnowledgeNodeItemBean) obj;
                        if (knowledgeNodeItemBean != null && (iIndexOf = KnowledgeListFragment.this.mNodeAdapter.getData().indexOf(knowledgeNodeItemBean)) >= 0) {
                            knowledgeNodeItemBean.setAnswer_count(knowledgeInfo.getUser_answer_total());
                            knowledgeNodeItemBean.setAccuracy(knowledgeInfo.getRight_rate());
                            KnowledgeListFragment.this.mNodeAdapter.notifyItemChanged(iIndexOf);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showFilterPoP() {
        XPopup.Builder builder = new XPopup.Builder(this.mContext);
        Context mContext = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        builder.asCustom(new PopKnowledgeChartFilter(mContext, this.chartFilterList, this.desc, this.defaultFilterMap, new PopKnowledgeChartFilter.ConfirmListener() { // from class: com.psychiatrygarden.fragmenthome.KnowledgeListFragment.showFilterPoP.1
            @Override // com.psychiatrygarden.widget.PopKnowledgeChartFilter.ConfirmListener
            public void onConfirm(@NotNull Map<String, String> params) throws JSONException, IOException {
                Iterator<Map.Entry<String, String>> it;
                Iterator it2;
                Iterator it3;
                Intrinsics.checkNotNullParameter(params, "params");
                KnowledgeListFragment.this.page = 1;
                KnowledgeListFragment.this.defaultFilterMap.clear();
                KnowledgeListFragment.this.hasInitScroller = false;
                KnowledgeListFragment.this.hasFilter = !params.isEmpty();
                Object obj = null;
                if (!params.isEmpty()) {
                    ArrayList arrayList = new ArrayList();
                    JSONObject jSONObject = new JSONObject();
                    Iterator<Map.Entry<String, String>> it4 = params.entrySet().iterator();
                    while (it4.hasNext()) {
                        Map.Entry<String, String> next = it4.next();
                        KnowledgeListFragment.this.defaultFilterMap.put(next.getKey(), StringsKt__StringsKt.split$default((CharSequence) next.getValue(), new String[]{","}, false, 0, 6, (Object) null));
                        ArrayList arrayList2 = KnowledgeListFragment.this.chartFilterList;
                        KnowledgeListFragment knowledgeListFragment = KnowledgeListFragment.this;
                        Iterator it5 = arrayList2.iterator();
                        while (it5.hasNext()) {
                            ChartFilterBean chartFilterBean = (ChartFilterBean) it5.next();
                            if (Intrinsics.areEqual(chartFilterBean.getType(), next.getKey())) {
                                it = it4;
                                if (StringsKt__StringsKt.contains$default((CharSequence) next.getValue(), (CharSequence) ",", false, 2, obj)) {
                                    List listSplit$default = StringsKt__StringsKt.split$default((CharSequence) next.getValue(), new String[]{","}, false, 0, 6, (Object) null);
                                    ArrayList arrayList3 = new ArrayList();
                                    Iterator it6 = listSplit$default.iterator();
                                    while (it6.hasNext()) {
                                        String str = (String) it6.next();
                                        Iterator it7 = it5;
                                        List<ChartFilterBean.ChartFilterValue> value = chartFilterBean.getValue();
                                        Intrinsics.checkNotNullExpressionValue(value, "it.value");
                                        Iterator it8 = value.iterator();
                                        while (it8.hasNext()) {
                                            ChartFilterBean.ChartFilterValue chartFilterValue = (ChartFilterBean.ChartFilterValue) it8.next();
                                            Iterator it9 = it8;
                                            if (Intrinsics.areEqual(chartFilterValue.getKey(), str)) {
                                                StringBuilder sb = new StringBuilder();
                                                it3 = it6;
                                                sb.append(chartFilterValue.getName());
                                                sb.append(chartFilterValue.getTitle());
                                                arrayList3.add(sb.toString());
                                            } else {
                                                it3 = it6;
                                            }
                                            it8 = it9;
                                            it6 = it3;
                                        }
                                        it5 = it7;
                                    }
                                    it2 = it5;
                                    String strJoinToString$default = CollectionsKt___CollectionsKt.joinToString$default(arrayList3, "/", null, null, 0, null, null, 62, null);
                                    arrayList.add(knowledgeListFragment.formatFilter(strJoinToString$default, String.valueOf(strJoinToString$default.charAt(strJoinToString$default.length() - 1))));
                                    jSONObject.put(chartFilterBean.getType(), next.getValue());
                                } else {
                                    it2 = it5;
                                    List<ChartFilterBean.ChartFilterValue> value2 = chartFilterBean.getValue();
                                    Intrinsics.checkNotNullExpressionValue(value2, "it.value");
                                    for (ChartFilterBean.ChartFilterValue chartFilterValue2 : value2) {
                                        if (Intrinsics.areEqual(chartFilterValue2.getKey(), next.getValue())) {
                                            arrayList.add(chartFilterValue2.getName() + chartFilterValue2.getTitle());
                                            jSONObject.put(chartFilterBean.getType(), chartFilterValue2.getKey());
                                        }
                                    }
                                }
                            } else {
                                it = it4;
                                it2 = it5;
                            }
                            it4 = it;
                            it5 = it2;
                            obj = null;
                        }
                    }
                    setList(CollectionsKt___CollectionsKt.distinct(arrayList));
                    KnowledgeListFragment.loadChartNodeData$default(KnowledgeListFragment.this, jSONObject, null, true, 2, null);
                    TextView textView = KnowledgeListFragment.this.tvDesc;
                    if (textView == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
                        textView = null;
                    }
                    ViewExtensionsKt.gone(textView);
                    RecyclerView recyclerView = KnowledgeListFragment.this.rvFilter;
                    if (recyclerView == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("rvFilter");
                        recyclerView = null;
                    }
                    ViewExtensionsKt.visible(recyclerView);
                    SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_VALUE, jSONObject.toString(), ((BaseFragment) KnowledgeListFragment.this).mContext);
                    SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_STR, CollectionsKt___CollectionsKt.joinToString$default(getData(), ",", null, null, 0, null, null, 62, null), ((BaseFragment) KnowledgeListFragment.this).mContext);
                } else {
                    KnowledgeListFragment.this.defaultFilterMap.clear();
                    SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_VALUE, "", ((BaseFragment) KnowledgeListFragment.this).mContext);
                    SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_STR, "", ((BaseFragment) KnowledgeListFragment.this).mContext);
                    setList(new ArrayList());
                    KnowledgeListFragment.loadChartNodeData$default(KnowledgeListFragment.this, new JSONObject(), null, true, 2, null);
                    String str2 = KnowledgeListFragment.this.desc;
                    if (str2 == null || str2.length() == 0) {
                        TextView textView2 = KnowledgeListFragment.this.tvDesc;
                        if (textView2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
                            textView2 = null;
                        }
                        ViewExtensionsKt.gone(textView2);
                    } else {
                        TextView textView3 = KnowledgeListFragment.this.tvDesc;
                        if (textView3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
                            textView3 = null;
                        }
                        ViewExtensionsKt.visible(textView3);
                    }
                    RecyclerView recyclerView2 = KnowledgeListFragment.this.rvFilter;
                    if (recyclerView2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("rvFilter");
                        recyclerView2 = null;
                    }
                    ViewExtensionsKt.gone(recyclerView2);
                }
                if (KnowledgeListFragment.this.getActivity() instanceof ChartAnswerSheetActivity) {
                    FragmentActivity activity = KnowledgeListFragment.this.getActivity();
                    Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.psychiatrygarden.activity.online.ChartAnswerSheetActivity");
                    if (((ChartAnswerSheetActivity) activity).getPageSize() == 2) {
                        FragmentActivity activity2 = KnowledgeListFragment.this.getActivity();
                        ChartAnswerSheetActivity chartAnswerSheetActivity = activity2 instanceof ChartAnswerSheetActivity ? (ChartAnswerSheetActivity) activity2 : null;
                        if (chartAnswerSheetActivity != null) {
                            chartAnswerSheetActivity.refreshAnswerSheet(KnowledgeListFragment.this.defaultFilterMap);
                        }
                    }
                }
                EventBus.getDefault().post("refreshKnowledgeFilter");
            }
        })).show();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fmt_answer_sheet_knowledge_list;
    }

    public final boolean hasPermission() {
        if (this.mNodeAdapter.getData().isEmpty()) {
            return false;
        }
        List<KnowledgeNodeItemBean> data = this.mNodeAdapter.getData();
        ArrayList arrayList = new ArrayList();
        for (Object obj : data) {
            if (true ^ Intrinsics.areEqual(((KnowledgeNodeItemBean) obj).getIs_permission(), "0")) {
                arrayList.add(obj);
            }
        }
        return arrayList.size() == this.mNodeAdapter.getData().size();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(@NotNull ViewHolder holder, @Nullable View root) throws JSONException {
        Intrinsics.checkNotNullParameter(holder, "holder");
        holder.get(R.id.iv_filter).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.o7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KnowledgeListFragment.initViews$lambda$0(this.f15889c, view);
            }
        });
        View view = holder.get(R.id.empty_view);
        Intrinsics.checkNotNullExpressionValue(view, "holder.get(R.id.empty_view)");
        this.loadingView = (CustomEmptyView) view;
        View view2 = holder.get(R.id.refresh);
        Intrinsics.checkNotNullExpressionValue(view2, "holder.get(R.id.refresh)");
        this.refresh = (SmartRefreshLayout) view2;
        View view3 = holder.get(R.id.tv_kc_desc);
        Intrinsics.checkNotNullExpressionValue(view3, "holder.get(R.id.tv_kc_desc)");
        this.tvDesc = (TextView) view3;
        View view4 = holder.get(R.id.rvChartList);
        Intrinsics.checkNotNullExpressionValue(view4, "holder.get(R.id.rvChartList)");
        this.rvChartList = (RecyclerView) view4;
        View view5 = holder.get(R.id.rvFilter);
        Intrinsics.checkNotNullExpressionValue(view5, "holder.get(R.id.rvFilter)");
        this.rvFilter = (RecyclerView) view5;
        View view6 = holder.get(R.id.ll_filter_info);
        Intrinsics.checkNotNullExpressionValue(view6, "holder.get(R.id.ll_filter_info)");
        this.llFilterInfo = view6;
        View view7 = holder.get(R.id.tv_filter_info);
        Intrinsics.checkNotNullExpressionValue(view7, "holder.get(R.id.tv_filter_info)");
        this.tvFilterInfo = (TextView) view7;
        RecyclerView recyclerView = this.rvChartList;
        SmartRefreshLayout smartRefreshLayout = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvChartList");
            recyclerView = null;
        }
        recyclerView.setItemAnimator(null);
        RecyclerView recyclerView2 = this.rvFilter;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvFilter");
            recyclerView2 = null;
        }
        recyclerView2.setAdapter(this.mFilterShowAdapter);
        SmartRefreshLayout smartRefreshLayout2 = this.refresh;
        if (smartRefreshLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("refresh");
            smartRefreshLayout2 = null;
        }
        smartRefreshLayout2.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.fragmenthome.p7
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) throws JSONException {
                KnowledgeListFragment.initViews$lambda$2(this.f15911c, refreshLayout);
            }
        });
        RecyclerView recyclerView3 = this.rvChartList;
        if (recyclerView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvChartList");
            recyclerView3 = null;
        }
        recyclerView3.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.fragmenthome.q7
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view8, MotionEvent motionEvent) {
                return KnowledgeListFragment.initViews$lambda$3(this.f15937c, view8, motionEvent);
            }
        });
        initScroll();
        initData();
        SmartRefreshLayout smartRefreshLayout3 = this.refresh;
        if (smartRefreshLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("refresh");
        } else {
            smartRefreshLayout = smartRefreshLayout3;
        }
        smartRefreshLayout.autoRefresh();
    }

    @Subscribe
    public final void onEventMainThread(@NotNull NodeRefreshEvent e2) {
        Intrinsics.checkNotNullParameter(e2, "e");
        refreshList();
    }

    public final void refreshList() {
        this.page = 1;
        this.requestParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(1));
        loadChartNodeData$default(this, null, this.requestParams, false, 4, null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onEventMainThread(@NotNull EventBusMessage<?> event) {
        Object valueObj;
        Intrinsics.checkNotNullParameter(event, "event");
        if (Intrinsics.areEqual(event.getKey(), "EVENT_QUESTION_ANSWER_REFRESH_KNOWLEDGE") && (valueObj = event.getValueObj()) != null && (valueObj instanceof String)) {
            String strValueOf = String.valueOf(valueObj);
            int i2 = 0;
            if (strValueOf.length() > 0) {
                refreshNodeInfo(strValueOf);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(CommonParameter.LAST_DO_QUESTION_KNOWLEDGE_NODE_ID);
            sb.append(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext));
            sb.append('_');
            String str = this.nodeId;
            RecyclerView recyclerView = null;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("nodeId");
                str = null;
            }
            sb.append(str);
            String strConfig = SharePreferencesUtils.readStrConfig(sb.toString(), this.mContext);
            if (!TextUtils.isEmpty(strConfig)) {
                for (KnowledgeNodeItemBean knowledgeNodeItemBean : this.mNodeAdapter.getData()) {
                    knowledgeNodeItemBean.setContinueDo(Intrinsics.areEqual(knowledgeNodeItemBean.getId(), strConfig));
                }
            }
            this.mNodeAdapter.notifyDataSetChanged();
            Iterator<KnowledgeNodeItemBean> it = this.mNodeAdapter.getData().iterator();
            while (true) {
                if (!it.hasNext()) {
                    i2 = -1;
                    break;
                } else if (Intrinsics.areEqual(it.next().getId(), strConfig)) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 >= 0) {
                RecyclerView recyclerView2 = this.rvChartList;
                if (recyclerView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rvChartList");
                } else {
                    recyclerView = recyclerView2;
                }
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    layoutManager.scrollToPosition(i2);
                }
            }
        }
    }
}

package com.psychiatrygarden.activity.forum.fragment;

import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.text.StrPool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.psychiatrygarden.activity.HandoutsInfoActivity;
import com.psychiatrygarden.activity.circleactivity.CircleInfoActivity;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.bean.HomeTabStatus;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.URLImageParser;
import com.psychiatrygarden.utils.VerticalImageSpan;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class ForumListFragment extends BaseFragment {
    public BaseQuickAdapter<CirclrListBean.DataBean, BaseViewHolder> adapter;
    private FloatingActionButton floatButton2;
    public SmartRefreshLayout mRefresh;
    public RecyclerView recycleview;
    private String times;
    public int page = 1;
    private final List<CirclrListBean.DataBean> mForumList = new ArrayList();
    private int previousFirstVisibleItem = 0;
    private long previousEventTime = 0;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(RefreshLayout refreshLayout) {
        this.page = 1;
        getForumListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(RefreshLayout refreshLayout) {
        this.page++;
        getForumListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        RecyclerView recyclerView = this.recycleview;
        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        try {
            if (isLogin()) {
                if ("1".equals(this.mForumList.get(i2).getNo_access())) {
                    startActivity(new Intent(getActivity(), (Class<?>) MemberCenterActivity.class));
                    return;
                }
                if (this.mForumList.get(i2).getType().equals("3")) {
                    Intent intent = new Intent(getActivity(), (Class<?>) HandoutsInfoActivity.class);
                    intent.putExtra("cat_id", this.mForumList.get(i2).getCid());
                    intent.putExtra("article", this.mForumList.get(i2).getId());
                    intent.putExtra("json_path", this.mForumList.get(i2).getJson_path());
                    intent.putExtra("html_path", this.mForumList.get(i2).getHtml_path());
                    intent.putExtra("h5_path", this.mForumList.get(i2).getH5_path());
                    intent.putExtra("is_rich_text", this.mForumList.get(i2).getIs_rich_text());
                    intent.putExtra("index", 0);
                    startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent(this.mContext, (Class<?>) CircleInfoActivity.class);
                intent2.putExtra("article_id", "" + this.mForumList.get(i2).getId());
                intent2.putExtra("group_id", "" + getArguments().getString("group_id"));
                intent2.putExtra("commentCount", "" + this.mForumList.get(i2).getComment_count());
                intent2.putExtra("module_type", 16);
                startActivity(intent2);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void getForumListData() {
        AjaxParams ajaxParams = new AjaxParams();
        if (getArguments() != null) {
            ajaxParams.put("group_id", getArguments().getString("group_id"));
            ajaxParams.put("cid", getArguments().getString("id"));
        }
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        ajaxParams.put("pag_size", "20");
        if (this.page == 1) {
            ajaxParams.put(CrashHianalyticsData.TIME, "" + (System.currentTimeMillis() / 1000));
        } else {
            ajaxParams.put(CrashHianalyticsData.TIME, "" + this.times);
        }
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.getforumlistApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.fragment.ForumListFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ForumListFragment forumListFragment = ForumListFragment.this;
                int i2 = forumListFragment.page;
                if (i2 > 1) {
                    forumListFragment.page = i2 - 1;
                    forumListFragment.mRefresh.finishLoadMore();
                } else {
                    forumListFragment.mForumList.clear();
                    ForumListFragment.this.adapter.notifyDataSetChanged();
                    ForumListFragment.this.mRefresh.finishRefresh(false);
                    ForumListFragment.this.adapter.setEmptyView(R.layout.layout_empty_view);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    ForumListFragment.this.mRefresh.finishRefresh();
                    CirclrListBean circlrListBean = (CirclrListBean) new Gson().fromJson(s2, CirclrListBean.class);
                    if (!circlrListBean.getCode().equals("200")) {
                        ForumListFragment forumListFragment = ForumListFragment.this;
                        int i2 = forumListFragment.page;
                        if (i2 > 1) {
                            forumListFragment.page = i2 - 1;
                            forumListFragment.mRefresh.finishLoadMore();
                            return;
                        } else {
                            forumListFragment.mForumList.clear();
                            ForumListFragment.this.adapter.notifyDataSetChanged();
                            ForumListFragment.this.mRefresh.finishRefresh(false);
                            ForumListFragment.this.adapter.setEmptyView(R.layout.layout_empty_view);
                            return;
                        }
                    }
                    ForumListFragment.this.times = circlrListBean.getServer_time();
                    ForumListFragment forumListFragment2 = ForumListFragment.this;
                    if (forumListFragment2.page == 1) {
                        forumListFragment2.mForumList.clear();
                        ForumListFragment.this.mForumList.addAll(circlrListBean.getData());
                        if (ForumListFragment.this.mForumList.isEmpty()) {
                            ForumListFragment.this.mRefresh.setEnableLoadMore(false);
                            ForumListFragment.this.adapter.setEmptyView(R.layout.layout_empty_view);
                        } else {
                            ForumListFragment.this.mRefresh.setEnableLoadMore(true);
                        }
                    } else if (circlrListBean.getData().size() > 0) {
                        ForumListFragment.this.mForumList.addAll(circlrListBean.getData());
                        ForumListFragment.this.mRefresh.finishLoadMore();
                    } else {
                        ForumListFragment forumListFragment3 = ForumListFragment.this;
                        int i3 = forumListFragment3.page;
                        if (i3 > 1) {
                            forumListFragment3.page = i3 - 1;
                        }
                        forumListFragment3.mRefresh.finishLoadMoreWithNoMoreData();
                    }
                    ForumListFragment.this.adapter.notifyDataSetChanged();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    ForumListFragment forumListFragment4 = ForumListFragment.this;
                    int i4 = forumListFragment4.page;
                    if (i4 > 1) {
                        forumListFragment4.page = i4 - 1;
                        forumListFragment4.mRefresh.finishLoadMore();
                    } else {
                        forumListFragment4.mForumList.clear();
                        ForumListFragment.this.adapter.notifyDataSetChanged();
                        ForumListFragment.this.mRefresh.finishRefresh(false);
                        ForumListFragment.this.adapter.setEmptyView(R.layout.layout_empty_view);
                    }
                }
            }
        });
    }

    public void getImageData(StringBuffer stringBuffer, TextView mTextView) {
        try {
            float textSize = mTextView.getPaint().getTextSize();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringBuffer.toString());
            Matcher matcher = Pattern.compile("\\[[^]]+]").matcher(stringBuffer.toString());
            while (matcher.find()) {
                String strGroup = matcher.group();
                if (strGroup.contains("http")) {
                    spannableStringBuilder.setSpan(new VerticalImageSpan(new URLImageParser(mTextView, this.mContext, (int) textSize).getDrawable(strGroup.substring(1, strGroup.length() - 1))), matcher.start(), matcher.end(), 33);
                }
            }
            mTextView.setText(spannableStringBuilder);
        } catch (Exception e2) {
            e2.printStackTrace();
            mTextView.setText(stringBuffer);
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_forum_list;
    }

    public void getScrollView(int dy) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.recycleview.getLayoutManager();
        int iFindFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        int childCount = linearLayoutManager.getChildCount();
        if (iFindFirstVisibleItemPosition > 5) {
            EventBus.getDefault().post(new HomeTabStatus("showInput", 0));
        } else {
            EventBus.getDefault().post(new HomeTabStatus("showInput", 8));
        }
        if (dy >= 0) {
            this.floatButton2.hide();
            return;
        }
        if (childCount <= 0 || iFindFirstVisibleItemPosition <= 7) {
            this.floatButton2.hide();
            return;
        }
        if (this.previousFirstVisibleItem != iFindFirstVisibleItemPosition) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            double d3 = (1.0d / (jCurrentTimeMillis - this.previousEventTime)) * 1000.0d;
            this.previousFirstVisibleItem = iFindFirstVisibleItemPosition;
            this.previousEventTime = jCurrentTimeMillis;
            if (d3 > 15.0d) {
                this.floatButton2.show();
            }
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.floatButton2 = (FloatingActionButton) holder.get(R.id.floatButton2);
        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) holder.get(R.id.refreshLayout);
        this.mRefresh = smartRefreshLayout;
        smartRefreshLayout.setEnableLoadMore(true);
        this.mRefresh.setEnableRefresh(true);
        this.mRefresh.autoRefresh();
        this.mRefresh.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.forum.fragment.s
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f12393c.lambda$initViews$0(refreshLayout);
            }
        });
        this.mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.forum.fragment.t
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f12394c.lambda$initViews$1(refreshLayout);
            }
        });
        this.floatButton2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.fragment.u
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12395c.lambda$initViews$2(view);
            }
        });
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.recycleview);
        this.recycleview = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recycleview.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.activity.forum.fragment.ForumListFragment.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView2, int newState) {
                super.onScrollStateChanged(recyclerView2, newState);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView2, int dx, int dy) {
                super.onScrolled(recyclerView2, dx, dy);
                ForumListFragment.this.getScrollView(dy);
            }
        });
        BaseQuickAdapter<CirclrListBean.DataBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<CirclrListBean.DataBean, BaseViewHolder>(R.layout.layout_forum_list_item, this.mForumList) { // from class: com.psychiatrygarden.activity.forum.fragment.ForumListFragment.3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder vHolder, CirclrListBean.DataBean dataBean) {
                TextView textView = (TextView) vHolder.getView(R.id.title);
                vHolder.setText(R.id.time, dataBean.getComment_time()).setText(R.id.commnum, dataBean.getComment_count() + "评论");
                StringBuffer stringBuffer = new StringBuffer();
                if (dataBean.getIcon_img() == null || dataBean.getIcon_img().size() <= 0) {
                    textView.setText(dataBean.getTitle());
                } else {
                    if (!(dataBean.getTitle() + vHolder.getAdapterPosition()).equals(textView.getTag())) {
                        for (int i2 = 0; i2 < dataBean.getIcon_img().size(); i2++) {
                            stringBuffer.append(StrPool.BRACKET_START);
                            stringBuffer.append(dataBean.getIcon_img().get(i2));
                            stringBuffer.append(StrPool.BRACKET_END);
                        }
                        stringBuffer.append(dataBean.getTitle());
                        ForumListFragment.this.getImageData(stringBuffer, textView);
                    }
                }
                textView.setTag(dataBean.getTitle() + vHolder.getAdapterPosition());
            }
        };
        this.adapter = baseQuickAdapter;
        this.recycleview.setAdapter(baseQuickAdapter);
        this.adapter.onAttachedToRecyclerView(this.recycleview);
        this.adapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.forum.fragment.v
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f12396c.lambda$initViews$3(baseQuickAdapter2, view, i2);
            }
        });
        View view = new View(getActivity());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -2);
        layoutParams.height = ScreenUtil.getPxByDp(this.mContext, 5);
        view.setLayoutParams(layoutParams);
        this.adapter.addHeaderView(view);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if (str.equals("isRefaulfCircle")) {
            this.mRefresh.autoRefresh();
        }
    }
}

package com.psychiatrygarden.ranking;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.google.gson.Gson;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.RankBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.widget.CustomEmptyDayView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Collection;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.apache.http.cookie.ClientCookie;

/* loaded from: classes6.dex */
public class SingleRankFragment extends BaseFragment {
    private CustomEmptyDayView emptyView;
    private ActiveRankAdp mAdapter;
    private String mRankType;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefresh;
    private TextView mTvInfo;
    private TextView mTvUpdateTime;
    private String mType;
    private int scrollHeight = 0;
    private int page = 1;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(RefreshLayout refreshLayout) {
        this.page++;
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        loadData();
    }

    private void loadData() {
        String str = this.mType.equals("praise") ? NetworkRequestsURL.getPraiseRankList : this.mType.equals(ClientCookie.COMMENT_ATTR) ? NetworkRequestsURL.getCommentRankList : this.mType.equals("post") ? NetworkRequestsURL.getPostRankList : this.mType.equals("question") ? NetworkRequestsURL.getDoQuetionRankList : (this.mType.equals("sign_continuous") || this.mType.equals("sign_total")) ? NetworkRequestsURL.getSignRanking : "";
        AjaxParams ajaxParams = new AjaxParams();
        if (this.mType.equals("sign_continuous") || this.mType.equals("sign_total")) {
            ajaxParams.put("type", this.mType.equals("sign_continuous") ? "1" : "2");
            ajaxParams.put(DatabaseManager.SIZE, Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        } else {
            ajaxParams.put("ranking_type", this.mRankType);
            ajaxParams.put("page_size", Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        }
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.ranking.SingleRankFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SingleRankFragment.this.mRefresh.finishRefresh();
                if (SingleRankFragment.this.page == 1) {
                    SingleRankFragment.this.emptyView.setLoadFileResUi(((BaseFragment) SingleRankFragment.this).mContext);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                SingleRankFragment.this.mRefresh.finishRefresh();
                try {
                    RankBean rankBean = (RankBean) new Gson().fromJson(s2, RankBean.class);
                    if (rankBean.getCode().equals("200")) {
                        if (rankBean.getData() == null || rankBean.getData().isEmpty()) {
                            if (SingleRankFragment.this.page == 1) {
                                SingleRankFragment.this.mAdapter.setNewData(new ArrayList());
                            }
                            SingleRankFragment.this.mRefresh.finishLoadMoreWithNoMoreData();
                        } else {
                            if (SingleRankFragment.this.page == 1) {
                                SingleRankFragment.this.mAdapter.setNewData(rankBean.getData());
                                if (rankBean.getData().size() < 10) {
                                    SingleRankFragment.this.mRefresh.finishLoadMoreWithNoMoreData();
                                    return;
                                }
                                return;
                            }
                            if (rankBean.getData().isEmpty()) {
                                return;
                            }
                            SingleRankFragment.this.mAdapter.addData((Collection) rankBean.getData());
                            if (rankBean.getData().size() < 10) {
                                SingleRankFragment.this.mRefresh.finishLoadMoreWithNoMoreData();
                            } else {
                                SingleRankFragment.this.mRefresh.finishLoadMore();
                            }
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (SingleRankFragment.this.page == 1) {
                        SingleRankFragment.this.emptyView.setLoadFileResUi(((BaseFragment) SingleRankFragment.this).mContext);
                    }
                }
            }
        });
    }

    public static SingleRankFragment newInstance() {
        Bundle bundle = new Bundle();
        SingleRankFragment singleRankFragment = new SingleRankFragment();
        singleRankFragment.setArguments(bundle);
        return singleRankFragment;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.frag_single_rank;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.mType = getArguments().getString("type");
        this.mRankType = getArguments().getString("position");
        this.mRefresh = (SmartRefreshLayout) holder.get(R.id.refresh);
        this.mRecyclerView = (RecyclerView) holder.get(R.id.recycler);
        this.mTvInfo = (TextView) holder.get(R.id.tv_info);
        ActiveRankAdp activeRankAdp = new ActiveRankAdp(this.mType);
        this.mAdapter = activeRankAdp;
        this.mRecyclerView.setAdapter(activeRankAdp);
        String str = this.mType;
        str.hashCode();
        switch (str) {
            case "sign_continuous":
            case "sign_total":
                this.mTvInfo.setText("签到天数");
                break;
            case "question":
                this.mTvInfo.setText("刷题量");
                break;
            case "praise":
                this.mTvInfo.setText("获赞数");
                break;
            case "post":
                this.mTvInfo.setText("发帖量");
                break;
            case "comment":
                this.mTvInfo.setText("评论数");
                break;
        }
        this.mRefresh.setEnableRefresh(false);
        this.mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.ranking.p
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f16214c.lambda$initViews$0(refreshLayout);
            }
        });
        CustomEmptyDayView customEmptyDayView = new CustomEmptyDayView(this.mContext, 0, "", this.mType);
        this.emptyView = customEmptyDayView;
        customEmptyDayView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.ranking.q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16215c.lambda$initViews$1(view);
            }
        });
        this.mAdapter.setEmptyView(this.emptyView);
        loadData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        super.onEventMainThread(str);
        if (str.equals("canscroll")) {
            this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        } else if (str.equals("noscroll")) {
            this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext) { // from class: com.psychiatrygarden.ranking.SingleRankFragment.2
                @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
                public boolean canScrollVertically() {
                    return false;
                }
            });
        }
    }

    public static SingleRankFragment newInstance(String type) {
        SingleRankFragment singleRankFragment = new SingleRankFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        singleRankFragment.setArguments(bundle);
        return singleRankFragment;
    }
}

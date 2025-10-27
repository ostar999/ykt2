package com.psychiatrygarden.ranking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.google.gson.Gson;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.RankBean;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.yikaobang.yixue.R;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes6.dex */
public class ActiveRankingAct extends BaseActivity {
    private CustomEmptyView emptyView;
    private ActiveRankAdp mAdapter;
    private ImageView mImgBack;
    private ImageView mImgLogoName;
    private ImageView mImgTrophy;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefresh;
    private TextView mTvDesc;
    private TextView mTvTypeName;
    private String mType;
    private int currentPositon = 0;
    private List<SelectIdentityBean.DataBean> mChildren = new ArrayList();
    private int page = 1;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(RefreshLayout refreshLayout) {
        this.page++;
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        finish();
    }

    private void loadData() {
        String str = this.mType.equals("continuous") ? NetworkRequestsURL.getContinuousActiveRankList : NetworkRequestsURL.getAccumulateActiveRankList;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        ajaxParams.put("page_size", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        YJYHttpUtils.get(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.ranking.ActiveRankingAct.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ActiveRankingAct.this.mRefresh.finishRefresh();
                if (ActiveRankingAct.this.page == 1) {
                    ActiveRankingAct.this.emptyView.setLoadFileResUi(ActiveRankingAct.this.mContext);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                ActiveRankingAct.this.emptyView.showEmptyView();
                ActiveRankingAct.this.mRefresh.finishRefresh();
                try {
                    RankBean rankBean = (RankBean) new Gson().fromJson(s2, RankBean.class);
                    if (!rankBean.getCode().equals("200")) {
                        ActiveRankingAct.this.emptyView.setLoadFileResUi(ActiveRankingAct.this.mContext);
                        return;
                    }
                    if (rankBean.getData() == null || rankBean.getData().isEmpty()) {
                        if (ActiveRankingAct.this.page == 1) {
                            ActiveRankingAct.this.mAdapter.setNewData(new ArrayList());
                        }
                        ActiveRankingAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                    } else {
                        if (ActiveRankingAct.this.page == 1) {
                            ActiveRankingAct.this.mAdapter.setNewData(rankBean.getData());
                            if (rankBean.getData().size() < 10) {
                                ActiveRankingAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                                return;
                            }
                            return;
                        }
                        if (rankBean.getData().isEmpty()) {
                            return;
                        }
                        ActiveRankingAct.this.mAdapter.addData((Collection) rankBean.getData());
                        if (rankBean.getData().size() < 10) {
                            ActiveRankingAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                        } else {
                            ActiveRankingAct.this.mRefresh.finishLoadMore();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (ActiveRankingAct.this.page == 1) {
                        ActiveRankingAct.this.emptyView.setLoadFileResUi(ActiveRankingAct.this.mContext);
                    }
                }
            }
        });
    }

    public static void newIntent(Context context, String type) {
        Intent intent = new Intent(context, (Class<?>) ActiveRankingAct.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mTvDesc = (TextView) findViewById(R.id.tv_desc);
        this.mImgLogoName = (ImageView) findViewById(R.id.img_name);
        this.mImgTrophy = (ImageView) findViewById(R.id.img_trophy);
        this.mTvTypeName = (TextView) findViewById(R.id.tv_type_name);
        this.mType = getIntent().getStringExtra("type");
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mImgBack.getLayoutParams();
        layoutParams.topMargin = statusBarHeight;
        this.mImgBack.setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.mImgTrophy.getLayoutParams();
        layoutParams2.topMargin = statusBarHeight + UIUtil.dip2px(this, 40.0d);
        this.mImgTrophy.setLayoutParams(layoutParams2);
        ActiveRankAdp activeRankAdp = new ActiveRankAdp(this.mType);
        this.mAdapter = activeRankAdp;
        this.mRecyclerView.setAdapter(activeRankAdp);
        if (this.mType.equals("continuous")) {
            this.mTvTypeName.setText("连续活跃");
            this.mImgLogoName.setImageResource(R.mipmap.ic_active_ranking_logo);
            this.mImgTrophy.setImageResource(R.mipmap.ic_continuous_active);
            this.mTvDesc.setText("不积跬步无以至千里  每天榜里高兴遇见你");
        } else {
            this.mTvTypeName.setText("累计活跃");
            this.mImgLogoName.setImageResource(R.mipmap.ic_active_accumulate_logo);
            this.mImgTrophy.setImageResource(R.mipmap.ic_accumulate_active);
            this.mTvDesc.setText("时间长短千万别在意  我来见证每一段经历");
        }
        this.mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.ranking.a
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f16190c.lambda$init$0(refreshLayout);
            }
        });
        CustomEmptyView customEmptyView = new CustomEmptyView(this.mContext, 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.ranking.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16193c.lambda$init$1(view);
            }
        });
        this.mAdapter.setEmptyView(this.emptyView);
        loadData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarTranslucent(this, false);
        StatusBarCompat.setLightStatusBar(this, true);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_active_ranking);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.ranking.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16196c.lambda$setListenerForWidget$2(view);
            }
        });
    }
}

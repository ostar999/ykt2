package com.psychiatrygarden.activity.setting;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.UserCommentInfoActivity;
import com.psychiatrygarden.bean.FollowBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import java.util.Collection;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class BlackListAct extends BaseActivity {
    private NewFriendsAdp mAdapter;
    private int mPage = 1;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefresh;

    public static /* synthetic */ int access$008(BlackListAct blackListAct) {
        int i2 = blackListAct.mPage;
        blackListAct.mPage = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_uuid", UserConfig.getInstance().getUser().getUser_uuid());
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.mPage + "");
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.blackList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.BlackListAct.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                BlackListAct.this.mRefresh.finishRefresh();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    FollowBean followBean = (FollowBean) new Gson().fromJson(s2, FollowBean.class);
                    BlackListAct.this.mRefresh.finishRefresh();
                    if (followBean.getCode().equals("200") && followBean.getData() != null) {
                        if (BlackListAct.this.mPage == 1) {
                            BlackListAct.this.mAdapter.setNewData(followBean.getData());
                            if (followBean.getData().size() < 20) {
                                BlackListAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                            }
                        } else if (followBean.getData() != null && followBean.getData().size() > 0) {
                            BlackListAct.this.mAdapter.addData((Collection) followBean.getData());
                            if (followBean.getData().size() < 20) {
                                BlackListAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                            } else {
                                BlackListAct.this.mRefresh.finishLoadMore();
                            }
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        FollowBean.DataBean item = this.mAdapter.getItem(i2);
        Intent intent = new Intent(this.mContext, (Class<?>) UserCommentInfoActivity.class);
        intent.putExtra("user_id", item.getUser_id());
        intent.putExtra("jiav", "");
        intent.addFlags(67108864);
        startActivity(intent);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, (Class<?>) BlackListAct.class);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        NewFriendsAdp newFriendsAdp = new NewFriendsAdp(false);
        this.mAdapter = newFriendsAdp;
        this.mRecyclerView.setAdapter(newFriendsAdp);
        this.mAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.layout_empty_view, (ViewGroup) null));
        this.mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.psychiatrygarden.activity.setting.BlackListAct.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                BlackListAct.access$008(BlackListAct.this);
                BlackListAct.this.getData();
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                BlackListAct.this.mPage = 1;
                BlackListAct.this.getData();
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.setting.m
            @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f13881a.lambda$init$0(baseQuickAdapter, view, i2);
            }
        });
        getData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_black_list);
        setTitle("黑名单");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

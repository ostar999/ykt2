package com.psychiatrygarden.activity.setting;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.FollowActivity;
import com.psychiatrygarden.activity.UserCommentInfoActivity;
import com.psychiatrygarden.activity.setting.NewFriendsAdp;
import com.psychiatrygarden.bean.FollowBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import java.util.Collection;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class NewFriendsAct extends BaseActivity {
    private NewFriendsAdp mAdapter;
    private int mPage = 1;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefresh;

    public static /* synthetic */ int access$108(NewFriendsAct newFriendsAct) {
        int i2 = newFriendsAct.mPage;
        newFriendsAct.mPage = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addFriendsList(FollowBean.DataBean item, final int pos) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("from_user_id", UserConfig.getUserId());
        ajaxParams.put("to_user_id", item.getUser_id());
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mChatFollow, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.NewFriendsAct.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewFriendsAct.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                NewFriendsAct.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                NewFriendsAct.this.hideProgressDialog();
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        NewFriendsAct.this.mAdapter.remove(pos);
                    } else {
                        ToastUtil.shortToast(NewFriendsAct.this, new JSONObject(s2).optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("target_user_id", UserConfig.getUserId());
        ajaxParams.put("is_follow", "2");
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.mPage);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.follerListApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.NewFriendsAct.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewFriendsAct.this.mRefresh.finishRefresh();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                NewFriendsAct.this.mRefresh.finishRefresh();
                try {
                    FollowBean followBean = (FollowBean) new Gson().fromJson(s2, FollowBean.class);
                    NewFriendsAct.this.mRefresh.finishRefresh();
                    if (followBean.getCode().equals("200") && followBean.getData() != null) {
                        if (NewFriendsAct.this.mPage == 1) {
                            NewFriendsAct.this.mAdapter.setNewData(followBean.getData());
                            if (followBean.getData().size() < 20) {
                                NewFriendsAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                            }
                        } else if (followBean.getData() != null && followBean.getData().size() > 0) {
                            NewFriendsAct.this.mAdapter.addData((Collection) followBean.getData());
                            if (followBean.getData().size() < 20) {
                                NewFriendsAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                            } else {
                                NewFriendsAct.this.mRefresh.finishLoadMore();
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
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        Intent intent = new Intent(this, (Class<?>) FollowActivity.class);
        intent.putExtra("user_id", UserConfig.getUserId());
        intent.putExtra("position", 1);
        startActivity(intent);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, (Class<?>) NewFriendsAct.class);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        NewFriendsAdp newFriendsAdp = new NewFriendsAdp(true);
        this.mAdapter = newFriendsAdp;
        this.mRecyclerView.setAdapter(newFriendsAdp);
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.layout_empty_view, (ViewGroup) null);
        this.mRefresh.autoRefresh();
        this.mAdapter.setEmptyView(viewInflate);
        this.mAdapter.setOnItemActionLisenter(new NewFriendsAdp.OnItemActionLisenter() { // from class: com.psychiatrygarden.activity.setting.NewFriendsAct.1
            @Override // com.psychiatrygarden.activity.setting.NewFriendsAdp.OnItemActionLisenter
            public void setUserHeadLisenter(int pos, FollowBean.DataBean item) {
                if (NewFriendsAct.this.isLogin()) {
                    Intent intent = new Intent(NewFriendsAct.this.mContext, (Class<?>) UserCommentInfoActivity.class);
                    intent.putExtra("user_id", item.getUser_id());
                    intent.addFlags(67108864);
                    NewFriendsAct.this.startActivity(intent);
                }
            }

            @Override // com.psychiatrygarden.activity.setting.NewFriendsAdp.OnItemActionLisenter
            public void setUserStatusLisenter(int pos, FollowBean.DataBean item) {
                if (item.getIs_follow().equals("0")) {
                    NewFriendsAct.this.addFriendsList(item, pos);
                }
            }
        });
        this.mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.psychiatrygarden.activity.setting.NewFriendsAct.2
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                NewFriendsAct.access$108(NewFriendsAct.this);
                NewFriendsAct.this.getData();
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                NewFriendsAct.this.mPage = 1;
                NewFriendsAct.this.getData();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layotu_new_friends);
        setTitle("新朋友");
        this.mBtnActionbarRight.setText("全部粉丝");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.z
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13917c.lambda$setListenerForWidget$0(view);
            }
        });
    }
}

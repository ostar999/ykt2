package com.psychiatrygarden.activity.setting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.google.gson.Gson;
import com.plv.socket.user.PLVAuthorizationBean;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.bean.MessageNoticeTypeBean;
import com.psychiatrygarden.bean.MyMessageBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.SkinManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import java.util.Collection;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SystemNoticeAct extends BaseActivity {
    private SystemNoticeAdp mAdapter;
    private TextView mBtnToSet;
    private LinearLayout mLyNotice;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefresh;
    private View mTabbar;
    private int mPage = 1;
    private String last_min_id = "0";

    public static /* synthetic */ int access$008(SystemNoticeAct systemNoticeAct) {
        int i2 = systemNoticeAct.mPage;
        systemNoticeAct.mPage = i2 + 1;
        return i2;
    }

    private void getCircleAccess(final MyMessageBean.DataBean item) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("article_id", item.getTarget_params().getId());
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getCircleAccess, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.SystemNoticeAct.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        String strOptString = new JSONObject(s2).optJSONObject("data").optString("no_access");
                        if (TextUtils.isEmpty(strOptString) || !strOptString.equals("1")) {
                            PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(item.getTarget_params()));
                        } else {
                            SystemNoticeAct.this.startActivity(new Intent(SystemNoticeAct.this, (Class<?>) MemberCenterActivity.class));
                        }
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
        if (this.mPage == 1) {
            ajaxParams.put("last_min_id", "0");
        } else {
            ajaxParams.put("last_min_id", this.last_min_id);
        }
        ajaxParams.put("ver", "v3");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mGetMessageListURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.SystemNoticeAct.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SystemNoticeAct.this.mRefresh.finishRefresh();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                SystemNoticeAct.this.mRefresh.finishRefresh();
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        MyMessageBean myMessageBean = (MyMessageBean) new Gson().fromJson(t2, MyMessageBean.class);
                        if (myMessageBean.getData() != null) {
                            int size = myMessageBean.getData().size();
                            if (SystemNoticeAct.this.mPage == 1) {
                                SystemNoticeAct.this.mAdapter.setNewData(myMessageBean.getData());
                                SystemNoticeAct.this.last_min_id = myMessageBean.getData().get(size - 1).getMessage_id();
                                if (myMessageBean.getData().size() < 10) {
                                    SystemNoticeAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                                }
                            } else if (myMessageBean.getData() == null || myMessageBean.getData().size() <= 0) {
                                SystemNoticeAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                            } else {
                                SystemNoticeAct.this.mAdapter.addData((Collection) myMessageBean.getData());
                                SystemNoticeAct.this.last_min_id = myMessageBean.getData().get(size - 1).getMessage_id();
                                if (myMessageBean.getData().size() < 10) {
                                    SystemNoticeAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                                } else {
                                    SystemNoticeAct.this.mRefresh.finishLoadMore();
                                }
                            }
                        }
                    } else {
                        SystemNoticeAct.this.AlertToast(jSONObject.getString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getMessageNotice() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getMessageNotice, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.SystemNoticeAct.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SystemNoticeAct.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                SystemNoticeAct.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        MessageNoticeTypeBean messageNoticeTypeBean = (MessageNoticeTypeBean) new Gson().fromJson(s2, MessageNoticeTypeBean.class);
                        if (messageNoticeTypeBean.getData() != null) {
                            if (messageNoticeTypeBean.getData().getSystem().equals("1")) {
                                if (SystemNoticeAct.this.mLyNotice.getVisibility() == 0) {
                                    SystemNoticeAct.this.mLyNotice.setVisibility(8);
                                }
                            } else if (SystemNoticeAct.this.mLyNotice.getVisibility() == 8) {
                                SystemNoticeAct.this.mLyNotice.setVisibility(0);
                            }
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                SystemNoticeAct.this.hideProgressDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        MyMessageBean.DataBean dataBean = this.mAdapter.getData().get(i2);
        if (!"21".equals(dataBean.getTarget_params().getPush_type())) {
            PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(dataBean.getTarget_params()));
            if ("7".equals(dataBean.getTarget_params().getPush_type())) {
                finish();
                return;
            }
            return;
        }
        try {
            PublicMethodActivity.goToLiving(this, new Gson().toJson(dataBean.getTarget_params()));
        } catch (Exception e2) {
            Log.d(this.TAG, "init: " + e2.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$0(View view) {
        Intent intent = new Intent();
        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
        intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
        startActivity(intent);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, (Class<?>) SystemNoticeAct.class);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mActionBar.hide();
        TextView textView = (TextView) findViewById(R.id.txt_actionbar_title);
        ImageView imageView = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mTabbar = findViewById(R.id.tabbar);
        textView.setText("系统通知");
        this.mTabbar.setBackgroundColor(Color.parseColor(SkinManager.getCurrentSkinType(this) == 1 ? "#121622" : PLVAuthorizationBean.FCOLOR_DEFAULT));
        this.mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        this.mLyNotice = (LinearLayout) findViewById(R.id.ly_set_notice);
        this.mBtnToSet = (TextView) findViewById(R.id.btn_to_set);
        SystemNoticeAdp systemNoticeAdp = new SystemNoticeAdp();
        this.mAdapter = systemNoticeAdp;
        this.mRecyclerView.setAdapter(systemNoticeAdp);
        this.mAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.layout_empty_view, (ViewGroup) null));
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.w0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13911c.lambda$init$1(view);
            }
        });
        this.mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.psychiatrygarden.activity.setting.SystemNoticeAct.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                SystemNoticeAct.access$008(SystemNoticeAct.this);
                SystemNoticeAct.this.getData();
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                SystemNoticeAct.this.mPage = 1;
                SystemNoticeAct.this.getData();
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.setting.x0
            @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f13913a.lambda$init$2(baseQuickAdapter, view, i2);
            }
        });
        getData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() throws ClassNotFoundException {
        super.onResume();
        getMessageNotice();
        boolean zAreNotificationsEnabled = NotificationManagerCompat.from(this).areNotificationsEnabled();
        this.mLyNotice.setVisibility(zAreNotificationsEnabled ? 8 : 0);
        if (zAreNotificationsEnabled) {
            return;
        }
        this.mBtnToSet.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.y0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13916c.lambda$onResume$0(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_common_notice_list);
        setNewStyleStatusBarColor2();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

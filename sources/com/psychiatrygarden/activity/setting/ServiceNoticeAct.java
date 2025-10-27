package com.psychiatrygarden.activity.setting;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.MyMessageBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import java.util.Collection;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ServiceNoticeAct extends BaseActivity {
    private ServiceNoticeAdp mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefresh;
    private int mPage = 1;
    private String last_min_id = "0";

    public static /* synthetic */ int access$008(ServiceNoticeAct serviceNoticeAct) {
        int i2 = serviceNoticeAct.mPage;
        serviceNoticeAct.mPage = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        if (this.mPage == 1) {
            ajaxParams.put("last_min_id", "0");
        } else {
            ajaxParams.put("last_min_id", this.last_min_id);
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.serviceNotice, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.ServiceNoticeAct.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ServiceNoticeAct.this.mRefresh.finishRefresh();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                ServiceNoticeAct.this.mRefresh.finishRefresh();
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        MyMessageBean myMessageBean = (MyMessageBean) new Gson().fromJson(t2, MyMessageBean.class);
                        if (myMessageBean.getData() != null) {
                            int size = myMessageBean.getData().size();
                            if (ServiceNoticeAct.this.mPage == 1) {
                                ServiceNoticeAct.this.mAdapter.setNewData(myMessageBean.getData());
                                ServiceNoticeAct.this.last_min_id = myMessageBean.getData().get(size - 1).getMessage_id();
                                if (myMessageBean.getData().size() < 10) {
                                    ServiceNoticeAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                                }
                            } else if (myMessageBean.getData() != null && myMessageBean.getData().size() > 0) {
                                ServiceNoticeAct.this.mAdapter.addData((Collection) myMessageBean.getData());
                                ServiceNoticeAct.this.last_min_id = myMessageBean.getData().get(size - 1).getMessage_id();
                                if (myMessageBean.getData().size() < 10) {
                                    ServiceNoticeAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                                } else {
                                    ServiceNoticeAct.this.mRefresh.finishLoadMore();
                                }
                            }
                        }
                    } else {
                        ServiceNoticeAct.this.AlertToast(jSONObject.getString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        MyMessageBean.DataBean dataBean = this.mAdapter.getData().get(i2);
        if (dataBean.getTarget_params().getPush_type().equals("151")) {
            dataBean.getTarget_params().setGoodsImg(dataBean.getImg());
            dataBean.getTarget_params().setOrder_time(dataBean.getTimestr());
        }
        PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(dataBean.getTarget_params()));
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, (Class<?>) ServiceNoticeAct.class);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        ServiceNoticeAdp serviceNoticeAdp = new ServiceNoticeAdp();
        this.mAdapter = serviceNoticeAdp;
        this.mRecyclerView.setAdapter(serviceNoticeAdp);
        this.mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.psychiatrygarden.activity.setting.ServiceNoticeAct.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ServiceNoticeAct.access$008(ServiceNoticeAct.this);
                ServiceNoticeAct.this.getData();
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                ServiceNoticeAct.this.mPage = 1;
                ServiceNoticeAct.this.getData();
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.setting.p0
            @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f13897a.lambda$init$0(baseQuickAdapter, view, i2);
            }
        });
        getData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_common_notice_list);
        setTitle("服务通知");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

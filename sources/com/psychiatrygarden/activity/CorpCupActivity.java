package com.psychiatrygarden.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import com.google.gson.Gson;
import com.psychiatrygarden.adapter.CrupDemoAdapter;
import com.psychiatrygarden.bean.CropCupBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class CorpCupActivity extends BaseActivity {

    /* renamed from: b, reason: collision with root package name */
    private Bundle f10957b;
    private CrupDemoAdapter mCommAdapter;
    private CropCupBean mCropCupBean;
    private ExpandableListView mListView;
    private int tempPosition = 0;
    private int sign = -1;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.f10957b.putString("virtual_user_id", UserConfig.getUserId());
        setResult(this.f10957b.getInt("result"), new Intent().putExtra("bundleIntent", this.f10957b));
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onCreate$1(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
        if (CommonUtil.isFastClick()) {
            return true;
        }
        try {
            this.f10957b.putString("virtual_user_id", this.mCropCupBean.getData().get(i2).getUser().get(i3).getUser_id());
            setResult(this.f10957b.getInt("result"), new Intent().putExtra("bundleIntent", this.f10957b));
            finish();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onCreate$2(ExpandableListView expandableListView, View view, int i2, long j2) {
        int i3 = this.sign;
        if (i3 == -1) {
            this.mListView.expandGroup(i2);
            this.mListView.setSelectedGroup(i2);
            this.sign = i2;
            return true;
        }
        if (i3 == i2) {
            this.mListView.collapseGroup(i3);
            this.sign = -1;
            return true;
        }
        this.mListView.collapseGroup(i3);
        this.mListView.expandGroup(i2);
        this.mListView.setSelectedGroup(i2);
        this.sign = i2;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$3(int i2) {
        int i3 = this.tempPosition;
        if (i3 != i2) {
            this.mListView.collapseGroup(i3);
            this.tempPosition = i2;
        }
    }

    public void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("admin_user_type", "comment_user");
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.virtualUserUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CorpCupActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                CorpCupActivity.this.mCropCupBean = (CropCupBean) new Gson().fromJson(s2, CropCupBean.class);
                if (CorpCupActivity.this.mCropCupBean.getCode().equals("200")) {
                    CorpCupActivity corpCupActivity = CorpCupActivity.this;
                    CorpCupActivity corpCupActivity2 = CorpCupActivity.this;
                    corpCupActivity.mCommAdapter = new CrupDemoAdapter(corpCupActivity2.mContext, corpCupActivity2.mCropCupBean);
                    CorpCupActivity.this.mListView.setAdapter(CorpCupActivity.this.mCommAdapter);
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corp_cup);
        this.mListView = (ExpandableListView) findViewById(R.id.listview);
        this.f10957b = getIntent().getBundleExtra("bundleIntent");
        getData();
        setTitle("身份标识");
        this.mTvActionbarRight.setText("本人发表");
        this.mTvActionbarRight.setVisibility(0);
        this.mBtnActionbarRight.setVisibility(8);
        this.mTvActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.c8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11138c.lambda$onCreate$0(view);
            }
        });
        this.mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() { // from class: com.psychiatrygarden.activity.d8
            @Override // android.widget.ExpandableListView.OnChildClickListener
            public final boolean onChildClick(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
                return this.f12237a.lambda$onCreate$1(expandableListView, view, i2, i3, j2);
            }
        });
        this.mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() { // from class: com.psychiatrygarden.activity.e8
            @Override // android.widget.ExpandableListView.OnGroupClickListener
            public final boolean onGroupClick(ExpandableListView expandableListView, View view, int i2, long j2) {
                return this.f12275a.lambda$onCreate$2(expandableListView, view, i2, j2);
            }
        });
        this.mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() { // from class: com.psychiatrygarden.activity.f8
            @Override // android.widget.ExpandableListView.OnGroupExpandListener
            public final void onGroupExpand(int i2) {
                this.f12346a.lambda$onCreate$3(i2);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

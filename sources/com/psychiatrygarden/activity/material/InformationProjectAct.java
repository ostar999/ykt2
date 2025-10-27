package com.psychiatrygarden.activity.material;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.mine.myfile.MyFileAct;
import com.psychiatrygarden.adapter.MaterialsTabAdapter;
import com.psychiatrygarden.bean.MaterialTab;
import com.psychiatrygarden.forum.ForumSearchAct;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.PopupShowManager;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class InformationProjectAct extends BaseActivity {
    private CustomEmptyView emptyView;
    private MaterialsTabAdapter mAdapter;
    private ImageView mBtnDownload;
    private ImageView mImgBack;
    private LinearLayout mLySearch;
    private RecyclerView mRecyclerView;

    private void getData() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getAppProject, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.material.InformationProjectAct.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                InformationProjectAct.this.emptyView.setLoadFileResUi(InformationProjectAct.this);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                InformationProjectAct.this.emptyView.showEmptyView();
                try {
                    MaterialTab materialTab = (MaterialTab) new Gson().fromJson(s2, MaterialTab.class);
                    if (materialTab.getCode().equals("200")) {
                        MaterialTab.MaterialTabData materialTabData = new MaterialTab.MaterialTabData();
                        materialTabData.setId("0");
                        materialTabData.setApp_name("我的收藏");
                        materialTab.getData().add(0, materialTabData);
                        InformationProjectAct.this.mAdapter.setList(materialTab.getData());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    InformationProjectAct.this.emptyView.setLoadFileResUi(InformationProjectAct.this);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        MaterialTab.MaterialTabData item = this.mAdapter.getItem(i2);
        if (item.getId().equals("0")) {
            MyFileAct.newIntent(this.mContext);
        } else {
            MaterialListActivity.newIntent(this, "", item.getId(), item.getApp_name(), true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        getData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        ForumSearchAct.newIntent(this.mContext, "", null, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        startActivity(new Intent(view.getContext(), (Class<?>) MaterialDownloadListActivity.class));
    }

    public static void newIntent(Context context) {
        context.startActivity(new Intent(context, (Class<?>) InformationProjectAct.class));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mLySearch = (LinearLayout) findViewById(R.id.ly_search);
        this.mBtnDownload = (ImageView) findViewById(R.id.iv_download);
        MaterialsTabAdapter materialsTabAdapter = new MaterialsTabAdapter();
        this.mAdapter = materialsTabAdapter;
        this.mRecyclerView.setAdapter(materialsTabAdapter);
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.material.u0
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12748c.lambda$init$0(baseQuickAdapter, view, i2);
            }
        });
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.v0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12750c.lambda$init$1(view);
            }
        });
        this.mAdapter.setEmptyView(this.emptyView);
        getData();
        PopupShowManager.getInstance(this).checkShowCoupon(this, "ENTER_INFO_HOME", "2", "5", null);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_information_project);
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.w0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12752c.lambda$setListenerForWidget$2(view);
            }
        });
        this.mLySearch.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.x0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12754c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mBtnDownload.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.y0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12757c.lambda$setListenerForWidget$4(view);
            }
        });
    }
}

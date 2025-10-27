package com.psychiatrygarden.activity.chooseSchool;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.WebLongSaveActivity;
import com.psychiatrygarden.activity.chooseSchool.util.AliYunLogUtil;
import com.psychiatrygarden.bean.AdmissionBrochureBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class AdmissionBrochureAct extends BaseActivity {
    private CustomEmptyView emptyView;
    private AdmissionBrochureAdp mAdapter;
    private RecyclerView mRecyclerView;
    private String mSchoolId;

    private void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("school_id", this.mSchoolId);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getAdmissionBrochure, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.AdmissionBrochureAct.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                AdmissionBrochureAct.this.emptyView.setIsShowReloadBtn(true, "点击刷新页面");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                AdmissionBrochureAct.this.emptyView.showEmptyView();
                try {
                    AdmissionBrochureBean admissionBrochureBean = (AdmissionBrochureBean) new Gson().fromJson(t2, AdmissionBrochureBean.class);
                    if (!admissionBrochureBean.getCode().equals("200") || admissionBrochureBean.getData() == null) {
                        AdmissionBrochureAct.this.AlertToast(admissionBrochureBean.getMessage());
                    } else {
                        AdmissionBrochureAct.this.mAdapter.setNewData(admissionBrochureBean.getData());
                    }
                } catch (Exception e2) {
                    AdmissionBrochureAct.this.emptyView.setLoadFileResUi(AdmissionBrochureAct.this);
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        getData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        AdmissionBrochureBean.AdmissionBrochureData item = this.mAdapter.getItem(i2);
        postViewCount(item.getId());
        try {
            AliYunLogUtil.getInstance().addLog(AliyunEvent.LookRecruit, item.getId(), item.getTitle());
        } catch (Exception e2) {
            Log.d(this.TAG, "init: " + e2.getMessage());
        }
        if (item.getType().equals("1")) {
            AttachmentPreviewAct.newIntent(this.mContext, item.getTitle(), item.getContent());
        } else if (!item.getType().equals("2")) {
            SchoolIntroductionAct.newIntent(this.mContext, false, item.getContent(), item.getTitle());
        } else {
            this.mContext.startActivity(new Intent(this.mContext, (Class<?>) WebLongSaveActivity.class).putExtra("web_url", item.getContent()).putExtra("title", item.getTitle()));
        }
    }

    public static void newIntent(Context context, String schoolId) {
        Intent intent = new Intent(context, (Class<?>) AdmissionBrochureAct.class);
        intent.putExtra("schoolId", schoolId);
        context.startActivity(intent);
    }

    private void postViewCount(String id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", id);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.prospectusViewCount, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.AdmissionBrochureAct.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mSchoolId = getIntent().getStringExtra("schoolId");
        setTitle("招生简章");
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        AdmissionBrochureAdp admissionBrochureAdp = new AdmissionBrochureAdp();
        this.mAdapter = admissionBrochureAdp;
        this.mRecyclerView.setAdapter(admissionBrochureAdp);
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11181c.lambda$init$0(view);
            }
        });
        this.mAdapter.setEmptyView(this.emptyView);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.b
            @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f11213a.lambda$init$1(baseQuickAdapter, view, i2);
            }
        });
        getData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_admission_brochure);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

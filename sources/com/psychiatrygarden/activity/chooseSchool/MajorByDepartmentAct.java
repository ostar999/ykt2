package com.psychiatrygarden.activity.chooseSchool;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.chooseSchool.bean.FollowSchoolBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class MajorByDepartmentAct extends BaseActivity {
    private CustomEmptyView emptyView;
    private SchoolOpenMajorAdp mAdapter;
    private RecyclerView mRecycler;
    private String majorId;

    private void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("department_school_id", this.majorId);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getDepartmentMajorInfoList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.MajorByDepartmentAct.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                MajorByDepartmentAct.this.emptyView.setLoadFileResUi(MajorByDepartmentAct.this);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                MajorByDepartmentAct.this.emptyView.showEmptyView();
                try {
                    FollowSchoolBean followSchoolBean = (FollowSchoolBean) new Gson().fromJson(s2, FollowSchoolBean.class);
                    if (!followSchoolBean.getCode().equals("200") || followSchoolBean.getData() == null) {
                        return;
                    }
                    MajorByDepartmentAct.this.mAdapter.setNewData(followSchoolBean.getData());
                } catch (Exception e2) {
                    e2.printStackTrace();
                    MajorByDepartmentAct.this.emptyView.setLoadFileResUi(MajorByDepartmentAct.this);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        getData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(String str, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        MajorBySchoolAct.newIntent(this, str, this.mAdapter.getItem(i2).getMajor_id());
    }

    public static void newIntent(Context context, String schoolId, String majorId, String name) {
        Intent intent = new Intent(context, (Class<?>) MajorByDepartmentAct.class);
        intent.putExtra("schoolId", schoolId);
        intent.putExtra("majorId", majorId);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        final String stringExtra = getIntent().getStringExtra("schoolId");
        this.majorId = getIntent().getStringExtra("majorId");
        String stringExtra2 = getIntent().getStringExtra("name");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        this.mRecycler = recyclerView;
        recyclerView.setAdapter(this.mAdapter);
        setTitle(stringExtra2);
        SchoolOpenMajorAdp schoolOpenMajorAdp = new SchoolOpenMajorAdp();
        this.mAdapter = schoolOpenMajorAdp;
        this.mRecycler.setAdapter(schoolOpenMajorAdp);
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.b2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11217c.lambda$init$0(view);
            }
        });
        this.mAdapter.setEmptyView(this.emptyView);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.c2
            @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f11228a.lambda$init$1(stringExtra, baseQuickAdapter, view, i2);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_major_by_department);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

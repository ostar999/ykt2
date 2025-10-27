package com.psychiatrygarden.activity.RegisterCommon;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.RegisterBean.RegisterDepartmentBean;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.yikaobang.yixue.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class RegisterSelectDepartTwoActivity extends BaseActivity {
    public String app_id;
    private List<RegisterDepartmentBean.DataBean> dataDepartmentList = new ArrayList();
    public CommAdapter<RegisterDepartmentBean.DataBean> mCommAdapter;
    public ListView mRegister_comList;

    private void getDepartTwo(String department_id) throws PackageManager.NameNotFoundException {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("department_id", department_id);
        YJYHttpUtils.postTemp(this.mContext, NetworkRequestsURL.mDepartmentUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectDepartTwoActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                RegisterSelectDepartTwoActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                RegisterSelectDepartTwoActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                RegisterSelectDepartTwoActivity.this.hideProgressDialog();
                try {
                    RegisterDepartmentBean registerDepartmentBean = (RegisterDepartmentBean) new Gson().fromJson(s2, RegisterDepartmentBean.class);
                    if (registerDepartmentBean.getCode().equals("200")) {
                        Intent intent = new Intent(RegisterSelectDepartTwoActivity.this, (Class<?>) RegisterSelectDepartThreeActivity.class);
                        intent.putExtra("dataList", (Serializable) registerDepartmentBean.getData());
                        intent.putExtra("app_id", RegisterSelectDepartTwoActivity.this.app_id);
                        RegisterSelectDepartTwoActivity.this.startActivityForResult(intent, 8);
                    } else {
                        RegisterSelectDepartTwoActivity.this.AlertToast(registerDepartmentBean.getMessage());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(AdapterView adapterView, View view, int i2, long j2) throws PackageManager.NameNotFoundException {
        List<RegisterDepartmentBean.DataBean> list = this.dataDepartmentList;
        if (list != null && list.get(i2).getHave().equals("1")) {
            getDepartTwo(this.dataDepartmentList.get(i2).getDepartment_id());
            return;
        }
        Intent intent = new Intent(this, (Class<?>) RegisterSelectDepartActivity.class);
        intent.putExtra("title", this.dataDepartmentList.get(i2).getTitle());
        intent.putExtra("area_id", this.dataDepartmentList.get(i2).getDepartment_id());
        setResult(-1, intent);
        finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 8) {
            setResult(-1, data);
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_register_select_one);
        this.app_id = getIntent().getExtras().getString("app_id");
        this.mRegister_comList = (ListView) findViewById(R.id.mRegister_comList);
        this.dataDepartmentList = (List) getIntent().getSerializableExtra("dataList");
        CommAdapter<RegisterDepartmentBean.DataBean> commAdapter = new CommAdapter<RegisterDepartmentBean.DataBean>(this.dataDepartmentList, this.mContext, R.layout.activity_txt) { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectDepartTwoActivity.1
            @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
            public void convert(ViewHolder vHolder, RegisterDepartmentBean.DataBean dataBean, int position) {
                vHolder.setText(R.id.tv_register_select, dataBean.getTitle());
            }
        };
        this.mCommAdapter = commAdapter;
        this.mRegister_comList.setAdapter((ListAdapter) commAdapter);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mRegister_comList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.RegisterCommon.d
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) throws PackageManager.NameNotFoundException {
                this.f10961c.lambda$setListenerForWidget$0(adapterView, view, i2, j2);
            }
        });
    }
}

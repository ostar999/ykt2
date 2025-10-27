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
import com.psychiatrygarden.activity.RegisterBean.HospitolDataBean;
import com.psychiatrygarden.activity.RegisterBean.RegisterDataBean;
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
public class RegisterSelectHospitolTwoActivity extends BaseActivity {
    public String app_id;
    private List<RegisterDataBean.DataBean> data = new ArrayList();
    public CommAdapter<RegisterDataBean.DataBean> mCommAdapter;
    public ListView mRegister_comList;
    private String province_id;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(AdapterView adapterView, View view, int i2, long j2) throws PackageManager.NameNotFoundException {
        getProvinceData(this.province_id, this.data.get(i2).getArea_id());
    }

    public void getProvinceData(final String area_id, final String city_id) throws PackageManager.NameNotFoundException {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("province_id", area_id);
        ajaxParams.put("city_id", city_id);
        YJYHttpUtils.postTemp(this.mContext, NetworkRequestsURL.mHospitalUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectHospitolTwoActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                RegisterSelectHospitolTwoActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                RegisterSelectHospitolTwoActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                RegisterSelectHospitolTwoActivity.this.hideProgressDialog();
                try {
                    HospitolDataBean hospitolDataBean = (HospitolDataBean) new Gson().fromJson(s2, HospitolDataBean.class);
                    if (!hospitolDataBean.getCode().equals("200") || hospitolDataBean.getData().size() <= 0) {
                        return;
                    }
                    Intent intent = new Intent(RegisterSelectHospitolTwoActivity.this, (Class<?>) RegisterSelectHospitolThreeActivity.class);
                    intent.putExtra("dataList", (Serializable) hospitolDataBean.getData());
                    intent.putExtra("city_id", city_id);
                    intent.putExtra("app_id", RegisterSelectHospitolTwoActivity.this.app_id);
                    intent.putExtra("province_id", area_id);
                    RegisterSelectHospitolTwoActivity.this.startActivityForResult(intent, 7);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 7) {
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
        this.data = (List) getIntent().getSerializableExtra("dataList");
        this.province_id = getIntent().getExtras().getString("province_id");
        CommAdapter<RegisterDataBean.DataBean> commAdapter = new CommAdapter<RegisterDataBean.DataBean>(this.data, this.mContext, R.layout.activity_txt) { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectHospitolTwoActivity.1
            @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
            public void convert(ViewHolder vHolder, RegisterDataBean.DataBean dataBean, int position) {
                vHolder.setText(R.id.tv_register_select, dataBean.getTitle());
            }
        };
        this.mCommAdapter = commAdapter;
        this.mRegister_comList.setAdapter((ListAdapter) commAdapter);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mRegister_comList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.RegisterCommon.h
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) throws PackageManager.NameNotFoundException {
                this.f10965c.lambda$setListenerForWidget$0(adapterView, view, i2, j2);
            }
        });
    }
}

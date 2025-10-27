package com.psychiatrygarden.activity.RegisterCommon;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.RegisterBean.HospitolData2Bean;
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
public class RegisterSelectHospitolActivity extends BaseActivity {
    public String app_id;
    private List<RegisterDataBean.DataBean> data = new ArrayList();
    public EditText edit_seach;
    public CommAdapter<RegisterDataBean.DataBean> mCommAdapter;
    public ListView mRegister_comList;
    public RelativeLayout rel_seach;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(AdapterView adapterView, View view, int i2, long j2) throws PackageManager.NameNotFoundException {
        getProvinceData(this.data.get(i2).getArea_id());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setListenerForWidget$1(TextView textView, int i2, KeyEvent keyEvent) throws PackageManager.NameNotFoundException {
        if (i2 != 3) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        if (this.edit_seach.getText().toString().equals("")) {
            return true;
        }
        getFinalFospitolData();
        return true;
    }

    public void getFinalFospitolData() throws PackageManager.NameNotFoundException {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("word", this.edit_seach.getText().toString().replace(" ", ""));
        YJYHttpUtils.postTemp(this.mContext, NetworkRequestsURL.mHospitalUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectHospitolActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                RegisterSelectHospitolActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                RegisterSelectHospitolActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                RegisterSelectHospitolActivity.this.hideProgressDialog();
                try {
                    HospitolData2Bean hospitolData2Bean = (HospitolData2Bean) new Gson().fromJson(s2, HospitolData2Bean.class);
                    if (hospitolData2Bean.getCode().equals("200")) {
                        Intent intent = new Intent(RegisterSelectHospitolActivity.this, (Class<?>) RegisterSelectHospitolFourActivity.class);
                        intent.putExtra("dataList", (Serializable) hospitolData2Bean.getData());
                        intent.putExtra("type", "2");
                        RegisterSelectHospitolActivity.this.startActivityForResult(intent, 7);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getProvinceData(final String area_id) throws PackageManager.NameNotFoundException {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("province_id", area_id);
        YJYHttpUtils.postTemp(this.mContext, NetworkRequestsURL.mHospitalUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectHospitolActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                RegisterSelectHospitolActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                RegisterSelectHospitolActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                RegisterSelectHospitolActivity.this.hideProgressDialog();
                try {
                    RegisterDataBean registerDataBean = (RegisterDataBean) new Gson().fromJson(s2, RegisterDataBean.class);
                    if (!registerDataBean.getCode().equals("200") || registerDataBean.getData().size() <= 0) {
                        return;
                    }
                    Intent intent = new Intent(RegisterSelectHospitolActivity.this, (Class<?>) RegisterSelectHospitolTwoActivity.class);
                    intent.putExtra("dataList", (Serializable) registerDataBean.getData());
                    intent.putExtra("province_id", area_id);
                    intent.putExtra("app_id", RegisterSelectHospitolActivity.this.app_id);
                    RegisterSelectHospitolActivity.this.startActivityForResult(intent, 7);
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
        setTitle("" + getIntent().getExtras().getString("title", ""));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_register_select_one);
        this.app_id = getIntent().getExtras().getString("app_id");
        this.mRegister_comList = (ListView) findViewById(R.id.mRegister_comList);
        this.edit_seach = (EditText) findViewById(R.id.edit_seach);
        this.data = (List) getIntent().getSerializableExtra("dataList");
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rel_seach);
        this.rel_seach = relativeLayout;
        relativeLayout.setVisibility(0);
        CommAdapter<RegisterDataBean.DataBean> commAdapter = new CommAdapter<RegisterDataBean.DataBean>(this.data, this.mContext, R.layout.activity_txt) { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectHospitolActivity.1
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
        this.mRegister_comList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.RegisterCommon.e
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) throws PackageManager.NameNotFoundException {
                this.f10962c.lambda$setListenerForWidget$0(adapterView, view, i2, j2);
            }
        });
        this.edit_seach.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.RegisterCommon.f
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                return this.f10963c.lambda$setListenerForWidget$1(textView, i2, keyEvent);
            }
        });
    }
}

package com.psychiatrygarden.activity.RegisterCommon;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.RegisterBean.MajorBean;
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
public class RegisterSelectMajorTwoActivity extends BaseActivity {
    public String app_id;
    private List<MajorBean.DataBean> dataMajor = new ArrayList();
    public CommAdapter<MajorBean.DataBean> mCommAdapter;
    public ListView mRegister_comList;
    private String type;

    private void getMajorTwo(String major_id) throws PackageManager.NameNotFoundException {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("major_id", major_id);
        ajaxParams.put("type", this.type);
        YJYHttpUtils.postTemp(this.mContext, NetworkRequestsURL.majorUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectMajorTwoActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                RegisterSelectMajorTwoActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                RegisterSelectMajorTwoActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    MajorBean majorBean = (MajorBean) new Gson().fromJson(s2, MajorBean.class);
                    if (majorBean.getCode().equals("200")) {
                        Intent intent = new Intent(RegisterSelectMajorTwoActivity.this, (Class<?>) RegisterSelectMajorThreeActivity.class);
                        intent.putExtra("type", RegisterSelectMajorTwoActivity.this.type);
                        intent.putExtra("dataList", (Serializable) majorBean.getData());
                        intent.putExtra("title", RegisterSelectMajorTwoActivity.this.getIntent().getExtras().getString("title"));
                        intent.putExtra("app_id", RegisterSelectMajorTwoActivity.this.app_id);
                        if (RegisterSelectMajorTwoActivity.this.type.equals("1")) {
                            RegisterSelectMajorTwoActivity.this.startActivityForResult(intent, 4);
                        } else {
                            RegisterSelectMajorTwoActivity.this.startActivityForResult(intent, 5);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                RegisterSelectMajorTwoActivity.this.hideProgressDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(AdapterView adapterView, View view, int i2, long j2) throws PackageManager.NameNotFoundException {
        List<MajorBean.DataBean> list = this.dataMajor;
        if (list != null && list.get(i2).getHave().equals("1")) {
            getMajorTwo(this.dataMajor.get(i2).getMajor_id());
            return;
        }
        Intent intent = new Intent(this, (Class<?>) RegisterSelectMajorActivity.class);
        intent.putExtra("title", this.dataMajor.get(i2).getTitle());
        intent.putExtra("area_id", this.dataMajor.get(i2).getMajor_id());
        setResult(-1, intent);
        finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 4 || requestCode == 5) {
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
        setTitle(getIntent().getExtras().getString("title"));
        this.mRegister_comList = (ListView) findViewById(R.id.mRegister_comList);
        this.dataMajor = (List) getIntent().getSerializableExtra("dataList");
        this.type = getIntent().getExtras().getString("type");
        CommAdapter<MajorBean.DataBean> commAdapter = new CommAdapter<MajorBean.DataBean>(this.dataMajor, this.mContext, R.layout.activity_txt) { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectMajorTwoActivity.1
            @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
            public void convert(ViewHolder vHolder, MajorBean.DataBean dataBean, int position) {
                vHolder.setText(R.id.tv_register_select, dataBean.getTitle());
                ImageView imageView = (ImageView) vHolder.getView(R.id.imhgj);
                if (RegisterSelectMajorTwoActivity.this.getIntent().getBooleanExtra("is_true", false)) {
                    imageView.setVisibility(8);
                }
            }
        };
        this.mCommAdapter = commAdapter;
        this.mRegister_comList.setAdapter((ListAdapter) commAdapter);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mRegister_comList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.RegisterCommon.m
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) throws PackageManager.NameNotFoundException {
                this.f10970c.lambda$setListenerForWidget$0(adapterView, view, i2, j2);
            }
        });
    }
}

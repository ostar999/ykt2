package com.psychiatrygarden.activity.RegisterCommon;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.RegisterBean.MajorBean;
import com.psychiatrygarden.activity.RegisterInfoActivity;
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
public class RegisterSelectMajorActivity extends BaseActivity {
    public String app_id;
    public EditText edit_seach;
    public CommAdapter<MajorBean.DataBean> mCommAdapter;
    public ListView mRegister_comList;
    public RelativeLayout rel_seach;
    private String type;
    private List<MajorBean.DataBean> dataMajor = new ArrayList();
    private boolean isNewUrl = false;

    private void getMajorTwo(String major_id) throws PackageManager.NameNotFoundException {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("major_id", major_id);
        ajaxParams.put("type", this.type);
        YJYHttpUtils.postTemp(this.mContext, NetworkRequestsURL.majorUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectMajorActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                RegisterSelectMajorActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                RegisterSelectMajorActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    MajorBean majorBean = (MajorBean) new Gson().fromJson(s2, MajorBean.class);
                    if (majorBean.getCode().equals("200")) {
                        Intent intent = new Intent(RegisterSelectMajorActivity.this, (Class<?>) RegisterSelectMajorTwoActivity.class);
                        intent.putExtra("type", RegisterSelectMajorActivity.this.type);
                        intent.putExtra("dataList", (Serializable) majorBean.getData());
                        intent.putExtra("title", RegisterSelectMajorActivity.this.getIntent().getExtras().getString("title"));
                        intent.putExtra("app_id", RegisterSelectMajorActivity.this.app_id);
                        intent.putExtra("is_true", true);
                        if (RegisterSelectMajorActivity.this.type.equals("1")) {
                            RegisterSelectMajorActivity.this.startActivityForResult(intent, 4);
                        } else {
                            RegisterSelectMajorActivity.this.startActivityForResult(intent, 5);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                RegisterSelectMajorActivity.this.hideProgressDialog();
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
        Intent intent = new Intent(this, (Class<?>) RegisterInfoActivity.class);
        intent.putExtra("title", this.dataMajor.get(i2).getTitle());
        intent.putExtra("area_id", this.dataMajor.get(i2).getMajor_id());
        setResult(-1, intent);
        finish();
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
        goMajorDataSeach();
        return true;
    }

    public void goMajorDataSeach() throws PackageManager.NameNotFoundException {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("word", this.edit_seach.getText().toString());
        ajaxParams.put("type", getIntent().getExtras().getString("type"));
        String str = NetworkRequestsURL.majorUrl;
        if (this.isNewUrl) {
            str = NetworkRequestsURL.majorV2;
        }
        YJYHttpUtils.postTemp(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectMajorActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                RegisterSelectMajorActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                RegisterSelectMajorActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    MajorBean majorBean = (MajorBean) new Gson().fromJson(s2, MajorBean.class);
                    if (majorBean.getCode().equals("200")) {
                        Intent intent = new Intent(RegisterSelectMajorActivity.this, (Class<?>) RegisterSelectMajorFourActivity.class);
                        intent.putExtra("dataList", (Serializable) majorBean.getData());
                        intent.putExtra("title", RegisterSelectMajorActivity.this.getIntent().getExtras().getString("title"));
                        intent.putExtra("type", "3");
                        intent.putExtra("is_true", true);
                        if (RegisterSelectMajorActivity.this.type.equals("1")) {
                            RegisterSelectMajorActivity.this.startActivityForResult(intent, 4);
                        } else {
                            RegisterSelectMajorActivity.this.startActivityForResult(intent, 5);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                RegisterSelectMajorActivity.this.hideProgressDialog();
            }
        });
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
        if (requestCode == 4) {
            setResult(-1, data);
            finish();
        } else {
            if (requestCode != 5) {
                return;
            }
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
        this.isNewUrl = getIntent().getExtras().getBoolean("isNewUrl", false);
        this.edit_seach = (EditText) findViewById(R.id.edit_seach);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rel_seach);
        this.rel_seach = relativeLayout;
        relativeLayout.setVisibility(0);
        CommAdapter<MajorBean.DataBean> commAdapter = new CommAdapter<MajorBean.DataBean>(this.dataMajor, this.mContext, R.layout.activity_txt) { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectMajorActivity.1
            @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
            public void convert(ViewHolder vHolder, MajorBean.DataBean dataBean, int position) {
                vHolder.setText(R.id.tv_register_select, dataBean.getTitle());
                ImageView imageView = (ImageView) vHolder.getView(R.id.imhgj);
                if (RegisterSelectMajorActivity.this.getIntent().getBooleanExtra("istrue", false)) {
                    imageView.setVisibility(8);
                }
            }
        };
        this.mCommAdapter = commAdapter;
        this.mRegister_comList.setAdapter((ListAdapter) commAdapter);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mRegister_comList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.RegisterCommon.i
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) throws PackageManager.NameNotFoundException {
                this.f10966c.lambda$setListenerForWidget$0(adapterView, view, i2, j2);
            }
        });
        this.edit_seach.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.RegisterCommon.j
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                return this.f10967c.lambda$setListenerForWidget$1(textView, i2, keyEvent);
            }
        });
    }
}

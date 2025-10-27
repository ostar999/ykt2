package com.psychiatrygarden.activity.RegisterCommon;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.RegisterBean.RegisterDataBean;
import com.psychiatrygarden.activity.RegisterBean.RegisterSchoolBean;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.yikaobang.yixue.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class RegisterSelectOneActivity extends BaseActivity {
    public String app_id;
    private List<RegisterDataBean.DataBean> data = new ArrayList();
    private String deptId;
    public EditText edit_seach;
    private LinearLayout lyEmptyView;
    public CommAdapter<RegisterDataBean.DataBean> mCommAdapter;
    public ListView mRegister_comList;
    private String majorType;
    public RelativeLayout rel_seach;
    private String schoolId;
    private String type;

    private void getMajorByDeptData(String schoolId, String deptid) {
        AjaxParams ajaxParams = new AjaxParams();
        String str = NetworkRequestsURL.departmentMajor;
        if (!TextUtils.isEmpty(schoolId)) {
            ajaxParams.put("school_id", schoolId);
            str = NetworkRequestsURL.getSchoolMajor;
        }
        if (!TextUtils.isEmpty(deptid)) {
            ajaxParams.put("school_department_id", deptid);
        }
        ajaxParams.put("word", this.edit_seach.getText().toString());
        ajaxParams.put("major_type", this.majorType);
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectOneActivity.4
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
                super.onSuccess((AnonymousClass4) s2);
                try {
                    RegisterDataBean registerDataBean = (RegisterDataBean) new Gson().fromJson(s2, RegisterDataBean.class);
                    if (registerDataBean.getCode().equals("200")) {
                        if (registerDataBean.getData() == null || registerDataBean.getData().isEmpty()) {
                            RegisterSelectOneActivity.this.data.clear();
                            CommAdapter<RegisterDataBean.DataBean> commAdapter = RegisterSelectOneActivity.this.mCommAdapter;
                            if (commAdapter != null) {
                                commAdapter.notifyDataSetChanged();
                            }
                            RegisterSelectOneActivity.this.mRegister_comList.setVisibility(8);
                            RegisterSelectOneActivity.this.lyEmptyView.setVisibility(0);
                            return;
                        }
                        RegisterSelectOneActivity.this.data = registerDataBean.getData();
                        RegisterSelectOneActivity.this.mCommAdapter = new CommAdapter<RegisterDataBean.DataBean>(RegisterSelectOneActivity.this.data, RegisterSelectOneActivity.this.mContext, R.layout.activity_txt) { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectOneActivity.4.1
                            @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
                            public void convert(ViewHolder vHolder, RegisterDataBean.DataBean dataBean, int position) {
                                vHolder.setText(R.id.tv_register_select, dataBean.getTitle());
                                ImageView imageView = (ImageView) vHolder.getView(R.id.imhgj);
                                if (TextUtils.isEmpty(dataBean.getIs_direction()) || !dataBean.getIs_direction().equals("1")) {
                                    imageView.setVisibility(8);
                                } else {
                                    imageView.setVisibility(0);
                                }
                            }
                        };
                        RegisterSelectOneActivity registerSelectOneActivity = RegisterSelectOneActivity.this;
                        registerSelectOneActivity.mRegister_comList.setAdapter((ListAdapter) registerSelectOneActivity.mCommAdapter);
                        RegisterSelectOneActivity.this.mRegister_comList.setVisibility(0);
                        RegisterSelectOneActivity.this.lyEmptyView.setVisibility(8);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getMajorDirByDeptData(String schoolId, String deptid, final String major_id, final String title) {
        AjaxParams ajaxParams = new AjaxParams();
        String str = NetworkRequestsURL.departmentMajorDirection;
        if (TextUtils.isEmpty(schoolId)) {
            ajaxParams.put("major_type", this.majorType);
        } else {
            ajaxParams.put("school_id", schoolId);
            str = NetworkRequestsURL.getSchoolMajorDirection;
            ajaxParams.put("word", this.edit_seach.getText().toString());
        }
        if (!TextUtils.isEmpty(deptid)) {
            ajaxParams.put("school_department_id", deptid);
        }
        ajaxParams.put("major_id", major_id);
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectOneActivity.5
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
                super.onSuccess((AnonymousClass5) s2);
                try {
                    RegisterSchoolBean registerSchoolBean = (RegisterSchoolBean) new Gson().fromJson(s2, RegisterSchoolBean.class);
                    if (registerSchoolBean.getCode().equals("200")) {
                        if (registerSchoolBean.getData().size() > 0) {
                            Intent intent = new Intent(RegisterSelectOneActivity.this, (Class<?>) RegisterSelectTwoActivity.class);
                            intent.putExtra("dataList", (Serializable) registerSchoolBean.getData());
                            intent.putExtra("title", "专业方向");
                            intent.putExtra("type", "4");
                            intent.putExtra("app_id", RegisterSelectOneActivity.this.app_id);
                            intent.putExtra("istrue", true);
                            RegisterSelectOneActivity.this.startActivityForResult(intent, 1);
                        } else {
                            Intent intent2 = new Intent(RegisterSelectOneActivity.this, (Class<?>) RegisterSelectOneActivity.class);
                            intent2.putExtra("title", title);
                            intent2.putExtra("major_id", major_id);
                            intent2.putExtra("major_dir_id", "");
                            RegisterSelectOneActivity.this.setResult(-1, intent2);
                            RegisterSelectOneActivity.this.finish();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getProvinceData$1(String str, String str2) {
        try {
            RegisterSchoolBean registerSchoolBean = (RegisterSchoolBean) new Gson().fromJson(str, RegisterSchoolBean.class);
            if (registerSchoolBean.getCode().equals("200") && registerSchoolBean.getData().size() > 0) {
                Intent intent = new Intent(this, (Class<?>) RegisterSelectTwoActivity.class);
                intent.putExtra("dataList", (Serializable) registerSchoolBean.getData());
                intent.putExtra("title", getIntent().getExtras().getString("title"));
                intent.putExtra("type", this.type);
                intent.putExtra("app_id", this.app_id);
                intent.putExtra("istrue", true);
                if (this.type.equals("1")) {
                    startActivityForResult(intent, 1);
                } else if (this.type.equals("2")) {
                    startActivityForResult(intent, 2);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getProvinceData$2(VolleyError volleyError, String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(AdapterView adapterView, View view, int i2, long j2) {
        if (this.type.equals("3")) {
            getMajorDirByDeptData(this.data.get(i2).getSchool_id(), this.data.get(i2).getSchool_department_id(), this.data.get(i2).getMajor_id(), this.data.get(i2).getTitle());
        } else if (this.type.equals("5")) {
            getProvinceNewData(true, this.data.get(i2).getArea_id());
        } else {
            getProvinceData(this.data.get(i2).getArea_id());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setListenerForWidget$3(TextView textView, int i2, KeyEvent keyEvent) throws PackageManager.NameNotFoundException {
        if (i2 != 3) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        if (this.edit_seach.getText().toString().equals("")) {
            if (!this.type.equals("3")) {
                return true;
            }
            getMajorByDeptData(this.schoolId, this.deptId);
            return true;
        }
        if (this.type.equals("5")) {
            getProvinceNewData(false, "");
            return true;
        }
        if (this.type.equals("3")) {
            getMajorByDeptData(this.schoolId, this.deptId);
            return true;
        }
        goSchoolDataSeach();
        return true;
    }

    public void getProvinceData(String area_id) {
        Log.d("wqewqeqweew", "" + area_id + "=====" + this.type);
        HashMap map = new HashMap();
        map.put("province_id", area_id);
        String str = NetworkRequestsURL.mShengfen;
        if (this.type.equals("5")) {
            str = NetworkRequestsURL.getProvinceOrSchool;
        } else {
            map.put("type", this.type);
        }
        YJYHttpUtils.postTmpe(this.mContext, str, this.app_id, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.RegisterCommon.n
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str2) {
                this.f10971c.lambda$getProvinceData$1((String) obj, str2);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.RegisterCommon.o
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str2) {
                RegisterSelectOneActivity.lambda$getProvinceData$2(volleyError, str2);
            }
        });
    }

    public void getProvinceNewData(boolean isItemClick, String area_id) {
        AjaxParams ajaxParams = new AjaxParams();
        if (isItemClick || TextUtils.isEmpty(this.edit_seach.getText().toString())) {
            ajaxParams.put("province_id", area_id);
        } else {
            ajaxParams.put("word", this.edit_seach.getText().toString());
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getProvinceOrSchool, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectOneActivity.2
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
                super.onSuccess((AnonymousClass2) s2);
                try {
                    RegisterSchoolBean registerSchoolBean = (RegisterSchoolBean) new Gson().fromJson(s2, RegisterSchoolBean.class);
                    if (!registerSchoolBean.getCode().equals("200") || registerSchoolBean.getData().size() <= 0) {
                        return;
                    }
                    Intent intent = new Intent(RegisterSelectOneActivity.this, (Class<?>) RegisterSelectTwoActivity.class);
                    intent.putExtra("dataList", (Serializable) registerSchoolBean.getData());
                    intent.putExtra("title", RegisterSelectOneActivity.this.getIntent().getExtras().getString("title"));
                    intent.putExtra("type", RegisterSelectOneActivity.this.type);
                    intent.putExtra("app_id", RegisterSelectOneActivity.this.app_id);
                    intent.putExtra("istrue", true);
                    RegisterSelectOneActivity.this.startActivityForResult(intent, 1);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void goSchoolDataSeach() throws PackageManager.NameNotFoundException {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("word", this.edit_seach.getText().toString());
        String str = NetworkRequestsURL.mShengfen;
        ajaxParams.put("type", this.type);
        YJYHttpUtils.postTemp(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectOneActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                RegisterSelectOneActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                RegisterSelectOneActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    RegisterSchoolBean registerSchoolBean = (RegisterSchoolBean) new Gson().fromJson(s2, RegisterSchoolBean.class);
                    if (registerSchoolBean.getCode().equals("200")) {
                        Intent intent = new Intent(RegisterSelectOneActivity.this, (Class<?>) RegisterSelectTwoActivity.class);
                        intent.putExtra("dataList", (Serializable) registerSchoolBean.getData());
                        intent.putExtra("type", RegisterSelectOneActivity.this.type);
                        intent.putExtra("app_id", RegisterSelectOneActivity.this.app_id);
                        intent.putExtra("title", RegisterSelectOneActivity.this.getIntent().getExtras().getString("title"));
                        intent.putExtra("istrue", true);
                        if (RegisterSelectOneActivity.this.type.equals("1")) {
                            RegisterSelectOneActivity.this.startActivityForResult(intent, 1);
                        } else {
                            RegisterSelectOneActivity.this.startActivityForResult(intent, 2);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                RegisterSelectOneActivity.this.hideProgressDialog();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mRegister_comList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.RegisterCommon.q
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                this.f10973c.lambda$init$0(adapterView, view, i2, j2);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 1 || requestCode == 2) {
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
        this.rel_seach = (RelativeLayout) findViewById(R.id.rel_seach);
        this.edit_seach = (EditText) findViewById(R.id.edit_seach);
        this.lyEmptyView = (LinearLayout) findViewById(R.id.ly_empty_view);
        this.type = getIntent().getExtras().getString("type");
        this.majorType = getIntent().getExtras().getString("major_type");
        this.rel_seach.setVisibility(0);
        if (this.type.equals("3")) {
            this.deptId = getIntent().getStringExtra("dept_id");
            String stringExtra = getIntent().getStringExtra("school_id");
            this.schoolId = stringExtra;
            getMajorByDeptData(stringExtra, this.deptId);
            return;
        }
        this.data = (List) getIntent().getSerializableExtra("dataList");
        CommAdapter<RegisterDataBean.DataBean> commAdapter = new CommAdapter<RegisterDataBean.DataBean>(this.data, this.mContext, R.layout.activity_txt) { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectOneActivity.1
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
        this.edit_seach.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.RegisterCommon.p
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                return this.f10972c.lambda$setListenerForWidget$3(textView, i2, keyEvent);
            }
        });
    }
}

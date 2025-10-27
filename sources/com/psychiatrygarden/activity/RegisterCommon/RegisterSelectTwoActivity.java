package com.psychiatrygarden.activity.RegisterCommon;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.RegisterBean.RegisterSchoolBean;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class RegisterSelectTwoActivity extends BaseActivity {
    private List<RegisterSchoolBean.DataBean> data = new ArrayList();
    public EditText edit_seach;
    private LinearLayout lyEmptyView;
    public CommAdapter<RegisterSchoolBean.DataBean> mCommAdapter;
    public ListView mRegister_comList;
    public RelativeLayout rel_seach;
    private String type;

    private void getDeptData(String school_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("school_id", school_id);
        ajaxParams.put("word", this.edit_seach.getText().toString());
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.departmentSchool, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectTwoActivity.2
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
                    if (!registerSchoolBean.getCode().equals("200") || registerSchoolBean.getData() == null || registerSchoolBean.getData().isEmpty()) {
                        return;
                    }
                    RegisterSelectTwoActivity.this.data = registerSchoolBean.getData();
                    RegisterSelectTwoActivity.this.mCommAdapter = new CommAdapter<RegisterSchoolBean.DataBean>(RegisterSelectTwoActivity.this.data, RegisterSelectTwoActivity.this.mContext, R.layout.activity_txt) { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectTwoActivity.2.1
                        @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
                        public void convert(ViewHolder vHolder, RegisterSchoolBean.DataBean dataBean, int position) {
                            vHolder.setText(R.id.tv_register_select, dataBean.getTitle());
                            ImageView imageView = (ImageView) vHolder.getView(R.id.imhgj);
                            if (RegisterSelectTwoActivity.this.getIntent().getBooleanExtra("istrue", false)) {
                                imageView.setVisibility(8);
                            }
                        }
                    };
                    RegisterSelectTwoActivity registerSelectTwoActivity = RegisterSelectTwoActivity.this;
                    registerSelectTwoActivity.mRegister_comList.setAdapter((ListAdapter) registerSelectTwoActivity.mCommAdapter);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setListenerForWidget$0(TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        this.edit_seach.getText().toString().equals("");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(AdapterView adapterView, View view, int i2, long j2) {
        Intent intent = new Intent(this, (Class<?>) RegisterSelectOneActivity.class);
        intent.putExtra("title", this.data.get(i2).getTitle());
        if (this.type.equals("3")) {
            intent.putExtra("dept_id", this.data.get(i2).getSchool_department_id());
        } else if (this.type.equals("4")) {
            intent.putExtra("major_id", this.data.get(i2).getMajor_id());
            intent.putExtra("major_dir_id", this.data.get(i2).getMajor_direction_id());
        } else {
            intent.putExtra("area_id", this.data.get(i2).getSchool_id());
            intent.putExtra("bind_type", this.data.get(i2).getBind_type());
        }
        setResult(-1, intent);
        finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
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
        setTitle(getIntent().getExtras().getString("title"));
        this.mRegister_comList = (ListView) findViewById(R.id.mRegister_comList);
        this.edit_seach = (EditText) findViewById(R.id.edit_seach);
        this.rel_seach = (RelativeLayout) findViewById(R.id.rel_seach);
        this.lyEmptyView = (LinearLayout) findViewById(R.id.ly_empty_view);
        String stringExtra = getIntent().getStringExtra("type");
        this.type = stringExtra;
        if (stringExtra.equals("3")) {
            getDeptData(getIntent().getStringExtra("school_id"));
            return;
        }
        List<RegisterSchoolBean.DataBean> list = (List) getIntent().getSerializableExtra("dataList");
        this.data = list;
        if (list.isEmpty()) {
            this.lyEmptyView.setVisibility(0);
            this.mRegister_comList.setVisibility(8);
            return;
        }
        this.lyEmptyView.setVisibility(8);
        this.mRegister_comList.setVisibility(0);
        CommAdapter<RegisterSchoolBean.DataBean> commAdapter = new CommAdapter<RegisterSchoolBean.DataBean>(this.data, this.mContext, R.layout.activity_txt) { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectTwoActivity.1
            @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
            public void convert(ViewHolder vHolder, RegisterSchoolBean.DataBean dataBean, int position) {
                vHolder.setText(R.id.tv_register_select, dataBean.getTitle());
                ImageView imageView = (ImageView) vHolder.getView(R.id.imhgj);
                if (RegisterSelectTwoActivity.this.getIntent().getBooleanExtra("istrue", false)) {
                    imageView.setVisibility(8);
                }
            }
        };
        this.mCommAdapter = commAdapter;
        this.mRegister_comList.setAdapter((ListAdapter) commAdapter);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.edit_seach.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.RegisterCommon.s
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                return this.f10975c.lambda$setListenerForWidget$0(textView, i2, keyEvent);
            }
        });
        this.mRegister_comList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.RegisterCommon.t
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                this.f10976c.lambda$setListenerForWidget$1(adapterView, view, i2, j2);
            }
        });
    }
}

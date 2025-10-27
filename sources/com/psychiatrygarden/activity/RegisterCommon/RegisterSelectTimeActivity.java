package com.psychiatrygarden.activity.RegisterCommon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class RegisterSelectTimeActivity extends BaseActivity {
    private List<String> datatime = new ArrayList();
    public CommAdapter<String> mCommAdapter;
    public ListView mRegister_comList;
    private String name;
    private boolean sextrue;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(AdapterView adapterView, View view, int i2, long j2) {
        Intent intent = new Intent();
        intent.putExtra("title", this.datatime.get(i2).toString());
        intent.putExtra("area_id", "0");
        try {
            intent.putExtra("type", "" + getIntent().getExtras().getString("type", ""));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        setResult(-1, intent);
        finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mRegister_comList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.RegisterCommon.r
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                this.f10974c.lambda$init$0(adapterView, view, i2, j2);
            }
        });
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
        this.datatime = (List) getIntent().getSerializableExtra("dataList");
        this.sextrue = getIntent().getBooleanExtra("sextrue", false);
        CommAdapter<String> commAdapter = new CommAdapter<String>(this.datatime, this.mContext, R.layout.activity_txt) { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectTimeActivity.1
            @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
            public void convert(ViewHolder vHolder, String s2, int position) {
                vHolder.setText(R.id.tv_register_select, s2);
                ImageView imageView = (ImageView) vHolder.getView(R.id.imhgj);
                ImageView imageView2 = (ImageView) vHolder.getView(R.id.duigou);
                if (!RegisterSelectTimeActivity.this.sextrue) {
                    imageView.setVisibility(0);
                    imageView2.setVisibility(8);
                    return;
                }
                imageView.setVisibility(8);
                RegisterSelectTimeActivity registerSelectTimeActivity = RegisterSelectTimeActivity.this;
                registerSelectTimeActivity.name = registerSelectTimeActivity.getIntent().getExtras().getString("name", "");
                if (s2.equals(RegisterSelectTimeActivity.this.name)) {
                    imageView2.setVisibility(0);
                } else {
                    imageView2.setVisibility(8);
                }
            }
        };
        this.mCommAdapter = commAdapter;
        this.mRegister_comList.setAdapter((ListAdapter) commAdapter);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

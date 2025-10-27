package com.psychiatrygarden.activity.RegisterCommon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.RegisterBean.WorkTimeBean;
import com.psychiatrygarden.activity.RegisterInfoActivity;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class WorkTimeActivity extends BaseActivity {
    private ListView listwork;
    private List<WorkTimeBean.DataBean> mWorkTimes = new ArrayList();

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(AdapterView adapterView, View view, int i2, long j2) {
        Intent intent = new Intent(this, (Class<?>) RegisterInfoActivity.class);
        intent.putExtra("title", this.mWorkTimes.get(i2).getTitle());
        intent.putExtra("work_id", this.mWorkTimes.get(i2).getId());
        setResult(-1, intent);
        finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.listwork = (ListView) findViewById(R.id.listwork);
        this.listwork.setAdapter((ListAdapter) new CommAdapter<WorkTimeBean.DataBean>(this.mWorkTimes, this.mContext, R.layout.activity_txt) { // from class: com.psychiatrygarden.activity.RegisterCommon.WorkTimeActivity.1
            @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
            public void convert(ViewHolder vHolder, WorkTimeBean.DataBean dataBean, int position) {
                vHolder.setText(R.id.tv_register_select, dataBean.getTitle());
                ImageView imageView = (ImageView) vHolder.getView(R.id.imhgj);
                if (WorkTimeActivity.this.getIntent().getBooleanExtra("istrue", false)) {
                    imageView.setVisibility(8);
                }
            }
        });
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
        setContentView(R.layout.activity_work_time);
        this.mWorkTimes = (List) getIntent().getSerializableExtra("dataList");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.listwork.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.RegisterCommon.u
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                this.f10977c.lambda$setListenerForWidget$0(adapterView, view, i2, j2);
            }
        });
    }
}

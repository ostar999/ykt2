package com.psychiatrygarden.activity.RegisterCommon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.RegisterBean.HospitolData2Bean;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class RegisterSelectHospitolFourActivity extends BaseActivity {
    public String app_id;
    private List<HospitolData2Bean.DataBean> data = new ArrayList();
    public CommAdapter<HospitolData2Bean.DataBean> mCommAdapter;
    public ListView mRegister_comList;
    public String type;

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
        this.mRegister_comList = (ListView) findViewById(R.id.mRegister_comList);
        this.data = (List) getIntent().getSerializableExtra("dataList");
        this.type = getIntent().getStringExtra("type");
        this.app_id = getIntent().getExtras().getString("app_id");
        CommAdapter<HospitolData2Bean.DataBean> commAdapter = new CommAdapter<HospitolData2Bean.DataBean>(this.data, this.mContext, R.layout.activity_txt) { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectHospitolFourActivity.1
            @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
            public void convert(ViewHolder vHolder, HospitolData2Bean.DataBean dataBean, int position) {
                vHolder.setText(R.id.tv_register_select, dataBean.getTitle());
            }
        };
        this.mCommAdapter = commAdapter;
        this.mRegister_comList.setAdapter((ListAdapter) commAdapter);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mRegister_comList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectHospitolFourActivity.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = RegisterSelectHospitolFourActivity.this.type.equals("1") ? new Intent(RegisterSelectHospitolFourActivity.this, (Class<?>) RegisterSelectHospitolThreeActivity.class) : new Intent(RegisterSelectHospitolFourActivity.this, (Class<?>) RegisterSelectHospitolActivity.class);
                intent.putExtra("title", ((HospitolData2Bean.DataBean) RegisterSelectHospitolFourActivity.this.data.get(position)).getTitle());
                intent.putExtra("area_id", ((HospitolData2Bean.DataBean) RegisterSelectHospitolFourActivity.this.data.get(position)).getHospital_id());
                RegisterSelectHospitolFourActivity.this.setResult(-1, intent);
                RegisterSelectHospitolFourActivity.this.finish();
            }
        });
    }
}

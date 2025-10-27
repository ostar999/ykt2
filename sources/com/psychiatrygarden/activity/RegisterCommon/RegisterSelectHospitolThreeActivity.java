package com.psychiatrygarden.activity.RegisterCommon;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.google.gson.Gson;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.RegisterBean.HospitolData2Bean;
import com.psychiatrygarden.activity.RegisterBean.HospitolDataBean;
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
public class RegisterSelectHospitolThreeActivity extends BaseActivity {
    public String app_id;
    public String city_id;
    private List<HospitolDataBean.DataBean> data = new ArrayList();
    public CommAdapter<HospitolDataBean.DataBean> mCommAdapter;
    public ListView mRegister_comList;
    public String province_id;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(AdapterView adapterView, View view, int i2, long j2) throws PackageManager.NameNotFoundException {
        getLevelData(this.data.get(i2).getLevel());
    }

    public void getLevelData(String level_id) throws PackageManager.NameNotFoundException {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("province_id", this.province_id);
        ajaxParams.put("city_id", this.city_id);
        ajaxParams.put(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, level_id);
        YJYHttpUtils.postTemp(this.mContext, NetworkRequestsURL.mHospitalUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectHospitolThreeActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                RegisterSelectHospitolThreeActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                RegisterSelectHospitolThreeActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                RegisterSelectHospitolThreeActivity.this.hideProgressDialog();
                try {
                    HospitolData2Bean hospitolData2Bean = (HospitolData2Bean) new Gson().fromJson(s2, HospitolData2Bean.class);
                    if (hospitolData2Bean.getCode().equals("200")) {
                        Intent intent = new Intent(RegisterSelectHospitolThreeActivity.this, (Class<?>) RegisterSelectHospitolFourActivity.class);
                        intent.putExtra("dataList", (Serializable) hospitolData2Bean.getData());
                        intent.putExtra("type", "1");
                        RegisterSelectHospitolThreeActivity.this.startActivityForResult(intent, 7);
                    }
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
        this.city_id = getIntent().getExtras().getString("city_id");
        CommAdapter<HospitolDataBean.DataBean> commAdapter = new CommAdapter<HospitolDataBean.DataBean>(this.data, this.mContext, R.layout.activity_txt) { // from class: com.psychiatrygarden.activity.RegisterCommon.RegisterSelectHospitolThreeActivity.1
            @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
            public void convert(ViewHolder vHolder, HospitolDataBean.DataBean dataBean, int position) {
                vHolder.setText(R.id.tv_register_select, dataBean.getTitle());
            }
        };
        this.mCommAdapter = commAdapter;
        this.mRegister_comList.setAdapter((ListAdapter) commAdapter);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mRegister_comList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.RegisterCommon.g
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) throws PackageManager.NameNotFoundException {
                this.f10964c.lambda$setListenerForWidget$0(adapterView, view, i2, j2);
            }
        });
    }
}

package com.psychiatrygarden.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.bean.ExchangeCodeListBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class ExchangeCodeListActivity extends BaseActivity {
    private ImageView ivNoData;
    private ListView listview;

    /* renamed from: com.psychiatrygarden.activity.ExchangeCodeListActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends AjaxCallBack<String> {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(ExchangeCodeListBean exchangeCodeListBean, AdapterView adapterView, View view, int i2, long j2) {
            Intent intent = new Intent(ExchangeCodeListActivity.this, (Class<?>) ExCodeActivity.class);
            intent.putExtra("cat", "" + exchangeCodeListBean.getData().get(i2).getCat());
            intent.putExtra("title", "" + exchangeCodeListBean.getData().get(i2).getLabel());
            ExchangeCodeListActivity.this.startActivity(intent);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            ExchangeCodeListActivity.this.ivNoData.setVisibility(0);
            ExchangeCodeListActivity.this.listview.setVisibility(8);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass1) s2);
            try {
                final ExchangeCodeListBean exchangeCodeListBean = (ExchangeCodeListBean) new Gson().fromJson(s2, ExchangeCodeListBean.class);
                if (exchangeCodeListBean.getCode() == 200) {
                    if (exchangeCodeListBean.getData() == null || exchangeCodeListBean.getData().size() <= 0) {
                        ExchangeCodeListActivity.this.ivNoData.setVisibility(0);
                        ExchangeCodeListActivity.this.listview.setVisibility(8);
                    } else {
                        ExchangeCodeListActivity.this.ivNoData.setVisibility(8);
                        ExchangeCodeListActivity.this.listview.setVisibility(0);
                        ExchangeCodeListActivity.this.listview.setAdapter((ListAdapter) new CommAdapter<ExchangeCodeListBean.DataBean>(exchangeCodeListBean.getData(), ExchangeCodeListActivity.this.mContext, R.layout.layout_item2) { // from class: com.psychiatrygarden.activity.ExchangeCodeListActivity.1.1
                            @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
                            public void convert(ViewHolder vHolder, ExchangeCodeListBean.DataBean dataBean, int position) {
                                ((TextView) vHolder.getView(R.id.title)).setText(dataBean.getLabel());
                            }
                        });
                        ExchangeCodeListActivity.this.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.ia
                            @Override // android.widget.AdapterView.OnItemClickListener
                            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                                this.f12503c.lambda$onSuccess$0(exchangeCodeListBean, adapterView, view, i2, j2);
                            }
                        });
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void getData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.exchangelistApi, new AjaxParams(), new AnonymousClass1());
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("卡券激活");
        this.listview = (ListView) findViewById(R.id.listview);
        this.ivNoData = (ImageView) findViewById(R.id.iv_no_data);
        getData();
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
        setContentView(R.layout.activity_exchange_code_list);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

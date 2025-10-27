package com.psychiatrygarden.activity.RegisterCommon;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.RegisterBean.EducationPublicBean;
import com.psychiatrygarden.activity.RegisterInfoActivity;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class EducationPublicActivity extends BaseActivity {
    BaseQuickAdapter<EducationPublicBean.DataBean, BaseViewHolder> adapter;
    public List<EducationPublicBean.DataBean> dataBeanList = new ArrayList();
    public RecyclerView recycle;
    private String type;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        Intent intent = new Intent(this, (Class<?>) RegisterInfoActivity.class);
        if (this.type.equals("3")) {
            intent.putExtra("title", this.dataBeanList.get(i2).getTitle());
            intent.putExtra("value", this.dataBeanList.get(i2).getId());
        } else {
            intent.putExtra("title", this.dataBeanList.get(i2).getLabel());
            intent.putExtra("value", this.dataBeanList.get(i2).getValue());
        }
        setResult(-1, intent);
        finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
        this.recycle = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BaseQuickAdapter<EducationPublicBean.DataBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<EducationPublicBean.DataBean, BaseViewHolder>(R.layout.item_select_identity, this.dataBeanList) { // from class: com.psychiatrygarden.activity.RegisterCommon.EducationPublicActivity.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder baseViewHolder, EducationPublicBean.DataBean dataBean) {
                if (EducationPublicActivity.this.type.equals("3")) {
                    baseViewHolder.setText(R.id.tv_register_select, dataBean.getTitle());
                } else if (TextUtils.isEmpty(dataBean.getShow())) {
                    baseViewHolder.setText(R.id.tv_register_select, dataBean.getLabel());
                } else {
                    baseViewHolder.setText(R.id.tv_register_select, dataBean.getShow());
                }
            }
        };
        this.adapter = baseQuickAdapter;
        this.recycle.setAdapter(baseQuickAdapter);
        this.adapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.RegisterCommon.a
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f10958c.lambda$init$0(baseQuickAdapter2, view, i2);
            }
        });
        initData();
    }

    public void initData() {
        String str;
        AjaxParams ajaxParams = new AjaxParams();
        if ("1".equals(getIntent().getExtras().getString("type"))) {
            if (!TextUtils.isEmpty(getIntent().getStringExtra("value"))) {
                ajaxParams.put("value", getIntent().getStringExtra("value"));
            }
            str = NetworkRequestsURL.getUserTypeApi;
        } else {
            str = "3".equals(this.type) ? NetworkRequestsURL.getJobList : NetworkRequestsURL.getUserEducationApi;
        }
        YJYHttpUtils.get(this, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterCommon.EducationPublicActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        EducationPublicActivity.this.dataBeanList = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<EducationPublicBean.DataBean>>() { // from class: com.psychiatrygarden.activity.RegisterCommon.EducationPublicActivity.2.1
                        }.getType());
                        EducationPublicActivity educationPublicActivity = EducationPublicActivity.this;
                        educationPublicActivity.adapter.setList(educationPublicActivity.dataBeanList);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_edu);
        String string = getIntent().getExtras().getString("type");
        this.type = string;
        if ("1".equals(string)) {
            setTitle("个人身份");
        } else if ("2".equals(this.type)) {
            setTitle("学历");
        } else if ("3".equals(this.type)) {
            setTitle("职务选择");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

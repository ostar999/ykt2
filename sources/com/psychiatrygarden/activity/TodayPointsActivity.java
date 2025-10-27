package com.psychiatrygarden.activity;

import android.widget.ListAdapter;
import com.psychiatrygarden.adapter.AdapterListView2;
import com.psychiatrygarden.bean.Bean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.widget.PinnedSectionListView;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class TodayPointsActivity extends BaseActivity {
    PinnedSectionListView findlist;
    ArrayList<Bean> list;
    AdapterListView2 pinnlistadpter;

    private void mScoreListTody() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        YJYHttpUtils.get(this.mContext, "", ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.TodayPointsActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                TodayPointsActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                TodayPointsActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass1) t2);
                try {
                    LogUtils.e("sssss", t2);
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("1")) {
                        JSONArray jSONArray = new JSONArray(jSONObject.optString("data"));
                        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                            JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i2);
                            JSONArray jSONArray2 = new JSONArray(jSONObjectOptJSONObject.optString("list"));
                            Bean bean = new Bean();
                            bean.setType(1);
                            bean.setText(jSONObjectOptJSONObject.optString("title"));
                            TodayPointsActivity.this.list.add(bean);
                            for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                                Bean bean2 = new Bean();
                                bean2.setType(0);
                                bean2.setTitle(jSONArray2.optJSONObject(i3).optString("title"));
                                bean2.setScore(jSONArray2.optJSONObject(i3).optString("score"));
                                TodayPointsActivity.this.list.add(bean2);
                            }
                        }
                        TodayPointsActivity todayPointsActivity = TodayPointsActivity.this;
                        TodayPointsActivity todayPointsActivity2 = TodayPointsActivity.this;
                        todayPointsActivity.pinnlistadpter = new AdapterListView2(todayPointsActivity2.mContext, todayPointsActivity2.list);
                        TodayPointsActivity todayPointsActivity3 = TodayPointsActivity.this;
                        todayPointsActivity3.findlist.setAdapter((ListAdapter) todayPointsActivity3.pinnlistadpter);
                    } else {
                        TodayPointsActivity.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                TodayPointsActivity.this.hideProgressDialog();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.findlist = (PinnedSectionListView) findViewById(R.id.pinnedSectionListView1);
        this.list = new ArrayList<>();
        mScoreListTody();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setTitle("今日积分");
        setContentView(R.layout.avtivitytodaypoint);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

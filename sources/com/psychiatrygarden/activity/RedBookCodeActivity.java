package com.psychiatrygarden.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.HashMap;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class RedBookCodeActivity extends BaseActivity {
    private Button bt_duihuan;
    private EditText ed_redbook_code;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commitRedbookCode$1(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optString("code").equals("200")) {
                AlertToast(jSONObject.optString("message"));
                EventBus.getDefault().post(EventBusConstant.EVENT_SERVICE_XUNI_GOOD);
            } else {
                AlertToast(jSONObject.optString("message"));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        hideProgressDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commitRedbookCode$2(VolleyError volleyError, String str) {
        hideProgressDialog();
        if (CommonUtil.isNetworkConnected(this.mContext)) {
            AlertToast(volleyError.getMessage());
        } else {
            AlertToast("网络连接失败，请检查网络");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (this.ed_redbook_code.getText().toString().equals("")) {
            AlertToast("小红书注册码为空");
        } else {
            commitRedbookCode();
        }
    }

    public void commitRedbookCode() {
        showProgressDialog("正在提交");
        HashMap map = new HashMap();
        map.put("act_code", this.ed_redbook_code.getText().toString());
        YJYHttpUtils.post(this, "", map, new Response.Listener() { // from class: com.psychiatrygarden.activity.lh
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f12667c.lambda$commitRedbookCode$1((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.mh
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                this.f12766c.lambda$commitRedbookCode$2(volleyError, str);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.bt_duihuan = (Button) findViewById(R.id.bt_duihuan);
        this.ed_redbook_code = (EditText) findViewById(R.id.ed_redbook_code);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setTitle("注册码");
        setContentView(R.layout.activity_redbookcode);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.bt_duihuan.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.kh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12590c.lambda$setListenerForWidget$0(view);
            }
        });
    }
}

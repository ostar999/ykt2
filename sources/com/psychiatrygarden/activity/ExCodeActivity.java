package com.psychiatrygarden.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.ClearEditText;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ExCodeActivity extends BaseActivity {
    public TextView duihuan;
    public ClearEditText editview;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        getData();
    }

    public void getData() {
        if (TextUtils.isEmpty(this.editview.getText().toString().replaceAll(" ", ""))) {
            ToastUtil.shortToast(this.mContext, TextUtils.equals(getIntent().getStringExtra("cat"), "book_cdkey") ? "请输入验证码" : "请输入兑换码");
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("cat", "" + getIntent().getExtras().getString("cat"));
        ajaxParams.put("cdkey", "" + this.editview.getText().toString());
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.exchangecodeApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ExCodeActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ToastUtil.shortToast(ExCodeActivity.this.mContext, "激活失败");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    ToastUtil.shortToast(ExCodeActivity.this.mContext, new JSONObject(s2).optString("message"));
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        EventBus.getDefault().post(EventBusConstant.EVENT_VIP_CODE);
                        ExCodeActivity.this.finish();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle(getIntent().getExtras().getString("title"));
        this.editview = (ClearEditText) findViewById(R.id.editview);
        TextView textView = (TextView) findViewById(R.id.duihuan);
        this.duihuan = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.y9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14210c.lambda$init$0(view);
            }
        });
        if (TextUtils.equals(getIntent().getStringExtra("cat"), "book_cdkey")) {
            this.editview.setHint("输入验证码，字母不区分大小写");
        } else {
            this.editview.setHint("输入兑换码，字母不区分大小写");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.excode);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

package com.psychiatrygarden.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hjq.permissions.Permission;
import com.mobile.auth.BuildConfig;
import com.psychiatrygarden.activity.purchase.beans.GoodsBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class MyCustomerServiceActivity extends BaseActivity {
    private GoodsBean.DataBean.CoumService mCoumService;
    private String mWorkTime = "客服在线时间上午：9：00至下午18：00";
    private String mIsWork = "1";

    private void getKeFuTime() {
        YJYHttpUtils.post(this, NetworkRequestsURL.mKefuTime, new HashMap(), new Response.Listener() { // from class: com.psychiatrygarden.activity.zd
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f14246c.lambda$getKeFuTime$0((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.ae
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                this.f10990c.lambda$getKeFuTime$1(volleyError, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getKeFuTime$0(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
            if (!jSONObject.optString("code").equals("200") || jSONObjectOptJSONObject == null) {
                int i2 = Calendar.getInstance(Locale.CHINA).get(11);
                this.mIsWork = (i2 <= 7 || i2 >= 20) ? "0" : "1";
            } else {
                this.mWorkTime = jSONObjectOptJSONObject.optString("work_time");
                this.mIsWork = jSONObjectOptJSONObject.optString(BuildConfig.FLAVOR_env);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getKeFuTime$1(VolleyError volleyError, String str) {
        int i2 = Calendar.getInstance(Locale.CHINA).get(11);
        this.mIsWork = (i2 <= 7 || i2 >= 20) ? "0" : "1";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        if (this.mIsWork.equals("0")) {
            AlertToast(this.mWorkTime);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(View view) {
        if (this.mIsWork.equals("0")) {
            AlertToast(this.mWorkTime);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(String str, View view) {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("mqqapi://card/show_pslcard?src_type=internal&version=1&uin=" + str + "&card_type=group&source=qrcode")));
        } catch (Exception unused) {
            AlertToast("请安装手机QQ");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$5(String str, View view) {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("mqqapi://card/show_pslcard?src_type=internal&version=1&uin=" + str + "&card_type=group&source=qrcode")));
        } catch (Exception unused) {
            AlertToast("请安装手机QQ");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$6(View view) {
        CommonUtil.mPutChatData(this, "" + this.mCoumService.getChatid(), "在线客服");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llay_contact);
        try {
            if (this.mCoumService.getLeyu().size() > 0) {
                Intent intent = new Intent(this, (Class<?>) WebLongSaveActivity.class);
                intent.putExtra("url", "" + this.mCoumService.getLeyu().get(0));
                intent.putExtra("title", "在线客服");
                startActivity(intent);
                finish();
                return;
            }
            for (int i2 = 0; i2 < this.mCoumService.getQq().size(); i2++) {
                View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.activity_mycustomerservice_item, (ViewGroup) null);
                ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_qq_icon);
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    imageView.setImageResource(R.drawable.yjyqq);
                } else {
                    imageView.setImageResource(R.drawable.qqzaixian_night);
                }
                ((TextView) viewInflate.findViewById(R.id.tv_qq_title)).setText(this.mCoumService.getQq().get(i2).getLabel());
                TextView textView = (TextView) viewInflate.findViewById(R.id.tv_qq_num);
                textView.setText(this.mCoumService.getQq().get(i2).getNumber());
                textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.be
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f11108c.lambda$init$2(view);
                    }
                });
                viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ce
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f11144c.lambda$init$3(view);
                    }
                });
                linearLayout.addView(viewInflate);
            }
            for (int i3 = 0; i3 < this.mCoumService.getQq_group().size(); i3++) {
                View viewInflate2 = LayoutInflater.from(this.mContext).inflate(R.layout.activity_mycustomerservice_item, (ViewGroup) null);
                ImageView imageView2 = (ImageView) viewInflate2.findViewById(R.id.iv_qq_icon);
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    imageView2.setImageResource(R.drawable.yjyzaixian);
                } else {
                    imageView2.setImageResource(R.drawable.qqzaixian_night);
                }
                ((TextView) viewInflate2.findViewById(R.id.tv_qq_title)).setText(this.mCoumService.getQq_group().get(i3).getLabel());
                TextView textView2 = (TextView) viewInflate2.findViewById(R.id.tv_qq_num);
                final String number = this.mCoumService.getQq_group().get(i3).getNumber();
                textView2.setText(number);
                textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.de
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f12242c.lambda$init$4(number, view);
                    }
                });
                viewInflate2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ee
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f12313c.lambda$init$5(number, view);
                    }
                });
                linearLayout.addView(viewInflate2);
            }
            if (this.mCoumService.getChatid() == null || this.mCoumService.getChatid().equals("")) {
                return;
            }
            View viewInflate3 = LayoutInflater.from(this.mContext).inflate(R.layout.activity_mycustomerservice_item, (ViewGroup) null);
            ImageView imageView3 = (ImageView) viewInflate3.findViewById(R.id.iv_qq_icon);
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                imageView3.setImageResource(R.drawable.yjyzaixian);
            } else {
                imageView3.setImageResource(R.drawable.qqzaixian_night);
            }
            ((TextView) viewInflate3.findViewById(R.id.tv_qq_title)).setText("私信客服");
            viewInflate3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.fe
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12351c.lambda$init$6(view);
                }
            });
            linearLayout.addView(viewInflate3);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        try {
            this.mCoumService = (GoodsBean.DataBean.CoumService) getIntent().getExtras().getSerializable("attr_data");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        setTitle("我的客服");
        setContentView(R.layout.activity_mycustomerservice);
        if (ContextCompat.checkSelfPermission(this.mContext, Permission.CALL_PHONE) != 0) {
            ActivityCompat.requestPermissions(this, new String[]{Permission.CALL_PHONE}, 1);
        }
        int i2 = Calendar.getInstance(Locale.CHINA).get(11);
        if (i2 <= 7 || i2 >= 20) {
            this.mIsWork = "0";
        } else {
            this.mIsWork = "1";
        }
        getKeFuTime();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

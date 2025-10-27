package com.psychiatrygarden.activity.purchase.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.purchase.beans.CreateOrderBean;
import com.psychiatrygarden.activity.purchase.beans.GoodsBean;
import com.psychiatrygarden.activity.purchase.beans.SaleGoodsBean;
import com.psychiatrygarden.activity.purchase.util.PayResult;
import com.psychiatrygarden.activity.purchase.util.ResultCodeData;
import com.psychiatrygarden.bean.OnlineServiceBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.pay.PayMethodKeyKt;
import com.psychiatrygarden.widget.CustomDialog;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class PayGoodsModeActivity extends BaseActivity {
    private static final int SDK_PAY_FLAG = 1;
    private ImageView checkbox_wx;
    private ImageView checkbox_zfb;
    private String goods_infos;
    private LinearLayout ll_wx_pay;
    private LinearLayout ll_zfb_pay;
    private CreateOrderBean mCreateOrderBean;
    private TextView tv_pay_go;
    private List<GoodsBean.DataBean.MealBean> rMealBean = new ArrayList();
    private String cat_id = "";
    private String after_purchase_goto = "";
    private String cs = "";

    @SuppressLint({"HandlerLeak"})
    Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.purchase.activity.PayGoodsModeActivity.3
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String resultStatus = new PayResult((Map) msg.obj).getResultStatus();
                PayGoodsModeActivity.this.mShowDialog(new ResultCodeData().mCheckResultCode(resultStatus), resultStatus);
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mShowDialog$3(String str, CustomDialog customDialog, View view) {
        if (TextUtils.equals(str, PayMethodKeyKt.PAY_SUCCESS_CODE)) {
            try {
                if (TextUtils.equals(this.after_purchase_goto, "1")) {
                    OnlineServiceBean onlineServiceBean = (OnlineServiceBean) new Gson().fromJson(this.cs, OnlineServiceBean.class);
                    if (onlineServiceBean != null) {
                        CommonUtil.onlineService(this, onlineServiceBean);
                    }
                } else if (TextUtils.equals(this.cat_id, "2")) {
                    goActivity(GouMaiXiangQingActivity.class);
                }
                if (getIntent().getExtras().getString("flag").equals("SkinActivity")) {
                    EventBus.getDefault().post("BuySuccess1");
                } else if (getIntent().getExtras().getString("flag").equals("EsimateExplainActivity")) {
                    EventBus.getDefault().post("FinishTheJob");
                } else {
                    EventBus.getDefault().post("BuySuccess");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            finish();
        }
        customDialog.dismissNoAnimaltion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mShowDialog$4(String str, CustomDialog customDialog, View view) {
        if (TextUtils.equals(str, PayMethodKeyKt.PAY_SUCCESS_CODE)) {
            try {
                if (TextUtils.equals(this.after_purchase_goto, "1")) {
                    OnlineServiceBean onlineServiceBean = (OnlineServiceBean) new Gson().fromJson(this.cs, OnlineServiceBean.class);
                    if (onlineServiceBean != null) {
                        CommonUtil.onlineService(this, onlineServiceBean);
                    }
                } else {
                    goActivity(GouMaiXiangQingActivity.class);
                }
                if (getIntent().getExtras().getString("flag").equals("EsimateExplainActivity")) {
                    EventBus.getDefault().post("FinishTheJob");
                } else {
                    EventBus.getDefault().post("BuySuccess");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            finish();
        }
        customDialog.dismissNoAnimaltion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        if (this.checkbox_zfb.isSelected()) {
            mCreateOrder();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        this.checkbox_zfb.setSelected(true);
        this.checkbox_wx.setSelected(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        this.checkbox_zfb.setSelected(false);
        this.checkbox_wx.setSelected(true);
    }

    private void mCreateOrder() {
        try {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.rMealBean.size(); i2++) {
                SaleGoodsBean saleGoodsBean = new SaleGoodsBean();
                saleGoodsBean.setGoods_id(this.rMealBean.get(i2).getGoods_id());
                saleGoodsBean.setPrice(this.rMealBean.get(i2).getPrice());
                saleGoodsBean.setQuantity(this.rMealBean.get(i2).getQuantity());
                if (!TextUtils.isEmpty(this.rMealBean.get(i2).getSku_id())) {
                    saleGoodsBean.setSku_id(this.rMealBean.get(i2).getSku_id());
                }
                arrayList.add(saleGoodsBean);
            }
            this.goods_infos = new Gson().toJson(arrayList);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("pay_type", PayMethodKeyKt.ALi_PayMethod);
        ajaxParams.put("goods_info", this.goods_infos);
        ajaxParams.put("total_amount", getIntent().getExtras().getString("total_amount"));
        ajaxParams.put("address", getIntent().getExtras().getString("user_address_id"));
        ajaxParams.put("leave_message", getIntent().getExtras().getString("message"));
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mcreateOrderUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.activity.PayGoodsModeActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                PayGoodsModeActivity.this.AlertToast("服务器请求错误");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    PayGoodsModeActivity.this.mCreateOrderBean = (CreateOrderBean) new Gson().fromJson(s2, CreateOrderBean.class);
                    if (PayGoodsModeActivity.this.mCreateOrderBean.getCode().equals("200")) {
                        PayGoodsModeActivity payGoodsModeActivity = PayGoodsModeActivity.this;
                        payGoodsModeActivity.mPayMouth(payGoodsModeActivity.mCreateOrderBean.getData().getSign().getSign());
                    } else {
                        PayGoodsModeActivity.this.AlertToast("订单生成失败！");
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.ll_zfb_pay = (LinearLayout) findViewById(R.id.ll_zfb_pay);
        this.ll_wx_pay = (LinearLayout) findViewById(R.id.ll_wx_pay);
        TextView textView = (TextView) findViewById(R.id.tv_pay_goods);
        this.tv_pay_go = (TextView) findViewById(R.id.tv_pay_go);
        this.checkbox_wx = (ImageView) findViewById(R.id.checkbox_wx);
        ImageView imageView = (ImageView) findViewById(R.id.checkbox_zfb);
        this.checkbox_zfb = imageView;
        imageView.setSelected(true);
        this.checkbox_wx.setSelected(false);
        TextView textView2 = (TextView) findViewById(R.id.tv_paymonty);
        try {
            textView2.setText((Double.parseDouble(getIntent().getExtras().getString("total_amount")) / 100.0d) + "");
            textView.setText(this.rMealBean.get(0).getGoods_name());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void mPayMouth(final String singSign) {
        new Thread(new Runnable() { // from class: com.psychiatrygarden.activity.purchase.activity.PayGoodsModeActivity.2
            @Override // java.lang.Runnable
            public void run() {
                Map<String, String> mapPayV2 = new PayTask(PayGoodsModeActivity.this).payV2(singSign, true);
                Message message = new Message();
                message.what = 1;
                message.obj = mapPayV2;
                PayGoodsModeActivity.this.mHandler.sendMessage(message);
            }
        }).start();
    }

    public void mShowDialog(String message, final String resultcode) {
        final CustomDialog customDialog = new CustomDialog(this.mContext, 1);
        customDialog.setCancelable(false);
        customDialog.isOutTouchDismiss(false);
        if (!TextUtils.isEmpty(this.cat_id) && this.cat_id.equals("2") && TextUtils.equals(resultcode, PayMethodKeyKt.PAY_SUCCESS_CODE)) {
            customDialog.setMessage("您购买的商品为视频课程，可以在课程模块进行观看。");
        } else {
            customDialog.setMessage(message);
        }
        customDialog.setPositiveBtn("确定", new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.b0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13569c.lambda$mShowDialog$3(resultcode, customDialog, view);
            }
        });
        customDialog.setNegativeBtn("取消", new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.c0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13575c.lambda$mShowDialog$4(resultcode, customDialog, view);
            }
        });
        customDialog.show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_paygoods_mode);
        this.rMealBean = (List) getIntent().getSerializableExtra("productData");
        try {
            this.cat_id = getIntent().getExtras().getString("cat_id", "");
            this.after_purchase_goto = getIntent().getExtras().getString("after_purchase_goto", "");
            this.cs = getIntent().getExtras().getString("cs", "");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        setTitle("支付方式");
        if (ContextCompat.checkSelfPermission(this.mContext, Permission.READ_PHONE_STATE) != 0) {
            ActivityCompat.requestPermissions(this, new String[]{Permission.READ_PHONE_STATE}, 1);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.tv_pay_go.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.y
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13670c.lambda$setListenerForWidget$0(view);
            }
        });
        this.ll_zfb_pay.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.z
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13672c.lambda$setListenerForWidget$1(view);
            }
        });
        this.ll_wx_pay.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.a0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13564c.lambda$setListenerForWidget$2(view);
            }
        });
    }
}

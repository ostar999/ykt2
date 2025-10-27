package com.psychiatrygarden.activity.purchase.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.mine.order.OrderListActivity;
import com.psychiatrygarden.activity.purchase.beans.CreateOrderBean;
import com.psychiatrygarden.activity.purchase.beans.GoodsBean;
import com.psychiatrygarden.activity.purchase.beans.SaleGoodsBean;
import com.psychiatrygarden.activity.purchase.util.PayResult;
import com.psychiatrygarden.activity.purchase.util.ResultCodeData;
import com.psychiatrygarden.bean.OnlineServiceBean;
import com.psychiatrygarden.event.WXPayEvent;
import com.psychiatrygarden.event.WXPayStatus;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.WeakHandler;
import com.psychiatrygarden.utils.pay.PayMethodKeyKt;
import com.psychiatrygarden.widget.CustomDialog;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;

/* loaded from: classes5.dex */
public class PayGoodsNewActivity extends BaseActivity {
    private static final int SDK_PAY_FLAG = 1;
    private String goods_infos;
    private ImageView imgCheckAli;
    private ImageView imgCheckoutWX;
    private RelativeLayout layoutALiPay;
    private RelativeLayout layoutWXPay;
    private RelativeLayout ll_wx_pay;
    private CreateOrderBean mCreateOrderBean;
    private WeakHandler mHandler;
    private TextView okzhifu;
    private RelativeLayout yinlian;
    private List<GoodsBean.DataBean.MealBean> rMealBean = new ArrayList();
    private String cat_id = "";
    private String after_purchase_goto = "";
    private String cs = "";
    private String upgrade_type = "";
    private String deduction_id = "";
    private String mPayMethod = PayMethodKeyKt.ALi_PayMethod;
    private boolean fromEBook = false;
    private String user_address_id = "";

    /* renamed from: com.psychiatrygarden.activity.purchase.activity.PayGoodsNewActivity$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$psychiatrygarden$event$WXPayStatus;

        static {
            int[] iArr = new int[WXPayStatus.values().length];
            $SwitchMap$com$psychiatrygarden$event$WXPayStatus = iArr;
            try {
                iArr[WXPayStatus.CANCEL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$event$WXPayStatus[WXPayStatus.FAILED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$event$WXPayStatus[WXPayStatus.SUCCESS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private void aLiPay(final String singSign) {
        new Thread(new Runnable() { // from class: com.psychiatrygarden.activity.purchase.activity.g0
            @Override // java.lang.Runnable
            public final void run() {
                this.f13597c.lambda$aLiPay$6(singSign);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goToPay(CreateOrderBean.Sign sign) {
        if (this.mPayMethod.equals("wechat")) {
            wxPay(sign);
        } else {
            aLiPay(sign.getSign());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$aLiPay$6(String str) {
        Map<String, String> mapPayV2 = new PayTask(this).payV2(str, true);
        Message message = new Message();
        message.what = 1;
        message.obj = mapPayV2;
        this.mHandler.sendMessage(message);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(Message message) {
        if (message.what == 1) {
            String resultStatus = new PayResult((Map) message.obj).getResultStatus();
            mShowDialog(new ResultCodeData().mCheckResultCode(resultStatus), resultStatus);
        }
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
                } else if (TextUtils.equals(this.cat_id, "2")) {
                    OrderListActivity.INSTANCE.goToOrderListActivity(this);
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
    public /* synthetic */ void lambda$mShowDialog$5(String str, CustomDialog customDialog, View view) {
        if (TextUtils.equals(str, PayMethodKeyKt.PAY_SUCCESS_CODE)) {
            try {
                if (TextUtils.equals(this.after_purchase_goto, "1")) {
                    OnlineServiceBean onlineServiceBean = (OnlineServiceBean) new Gson().fromJson(this.cs, OnlineServiceBean.class);
                    if (onlineServiceBean != null) {
                        CommonUtil.onlineService(this, onlineServiceBean);
                    }
                } else {
                    OrderListActivity.INSTANCE.goToOrderListActivity(this);
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
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        mCreateOrder();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        this.mPayMethod = "wechat";
        this.imgCheckoutWX.setSelected(true);
        this.imgCheckAli.setSelected(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        this.mPayMethod = PayMethodKeyKt.ALi_PayMethod;
        this.imgCheckoutWX.setSelected(false);
        this.imgCheckAli.setSelected(true);
    }

    private void mCreateOrder() {
        try {
            ArrayList arrayList = new ArrayList();
            SaleGoodsBean saleGoodsBean = new SaleGoodsBean();
            if (this.fromEBook) {
                saleGoodsBean.setEbook_id(getIntent().getExtras().getString("ebook_id", ""));
                saleGoodsBean.setPrice(getIntent().getExtras().getString("price", ""));
                saleGoodsBean.setQuantity(getIntent().getExtras().getString("quantity", ""));
                arrayList.add(saleGoodsBean);
            } else {
                for (int i2 = 0; i2 < this.rMealBean.size(); i2++) {
                    if (!TextUtils.isEmpty(this.rMealBean.get(i2).getEnclosure_id())) {
                        saleGoodsBean.setEnclosure_id(this.rMealBean.get(i2).getEnclosure_id());
                    }
                    if (!TextUtils.isEmpty(this.rMealBean.get(i2).getGoods_id())) {
                        saleGoodsBean.setGoods_id(this.rMealBean.get(i2).getGoods_id());
                    }
                    saleGoodsBean.setPrice(this.rMealBean.get(i2).getPrice());
                    saleGoodsBean.setQuantity(this.rMealBean.get(i2).getQuantity());
                    if (!TextUtils.isEmpty(this.rMealBean.get(i2).getSku_id())) {
                        saleGoodsBean.setSku_id(this.rMealBean.get(i2).getSku_id());
                    } else if (TextUtils.isEmpty(this.rMealBean.get(i2).getEnclosure_id())) {
                        saleGoodsBean.setSku_id("");
                    }
                    arrayList.add(saleGoodsBean);
                }
            }
            this.goods_infos = new Gson().toJson(arrayList);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("pay_type", this.mPayMethod);
        ajaxParams.put("goods_info", this.goods_infos);
        ajaxParams.put("total_amount", getIntent().getExtras().getString("total_amount"));
        ajaxParams.put("address", getIntent().getExtras().getString("user_address_id"));
        ajaxParams.put("leave_message", getIntent().getExtras().getString("message"));
        if (!TextUtils.isEmpty(this.upgrade_type)) {
            ajaxParams.put("upgrade_type", "" + this.upgrade_type);
        }
        if (!TextUtils.isEmpty(this.deduction_id)) {
            ajaxParams.put("deduction_id", "" + this.deduction_id);
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mcreateOrderUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.activity.PayGoodsNewActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                PayGoodsNewActivity.this.AlertToast("服务器请求错误");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    PayGoodsNewActivity.this.mCreateOrderBean = (CreateOrderBean) new Gson().fromJson(s2, CreateOrderBean.class);
                    if (PayGoodsNewActivity.this.mCreateOrderBean.getCode().equals("200")) {
                        PayGoodsNewActivity payGoodsNewActivity = PayGoodsNewActivity.this;
                        payGoodsNewActivity.goToPay(payGoodsNewActivity.mCreateOrderBean.getData().getSign());
                    } else {
                        PayGoodsNewActivity.this.AlertToast("订单生成失败！");
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    private void wxPay(CreateOrderBean.Sign sign) {
        PayMethodKeyKt.wxPay(this, sign.getAppId(), sign.getPartnerId(), sign.getPrepayId(), sign.getPackageValue(), sign.getNonceStr(), sign.getTimeStamp(), sign.getSign());
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        Intent intent = getIntent();
        if (intent != null) {
            this.user_address_id = intent.getStringExtra("user_address_id");
        }
        this.layoutALiPay = (RelativeLayout) findViewById(R.id.r3);
        this.layoutWXPay = (RelativeLayout) findViewById(R.id.layoutWXPay);
        this.okzhifu = (TextView) findViewById(R.id.okzhifu);
        this.ll_wx_pay = (RelativeLayout) findViewById(R.id.ll_wx_pay);
        this.yinlian = (RelativeLayout) findViewById(R.id.r4);
        TextView textView = (TextView) findViewById(R.id.tv_paymonty);
        this.imgCheckAli = (ImageView) findViewById(R.id.imgSelect);
        this.imgCheckoutWX = (ImageView) findViewById(R.id.imgCheckoutWX);
        this.imgCheckAli.setSelected(true);
        this.mHandler = new WeakHandler(this, new WeakHandler.HandlerCallback() { // from class: com.psychiatrygarden.activity.purchase.activity.h0
            @Override // com.psychiatrygarden.utils.WeakHandler.HandlerCallback
            public final void handlerMessage(Message message) {
                this.f13601a.lambda$init$0(message);
            }
        });
        try {
            textView.setText(String.format("%s", (Double.parseDouble(getIntent().getExtras().getString("total_amount")) / 100.0d) + ""));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void mShowDialog(String message, final String resultcode) {
        if (TextUtils.equals(resultcode, PayMethodKeyKt.PAY_SUCCESS_CODE)) {
            EventBus.getDefault().post(EventBusConstant.EVENT_ORDER_SUCCESS_FINISH_CONFIRM_UI);
        }
        final CustomDialog customDialog = new CustomDialog(this.mContext, 1);
        customDialog.setCancelable(false);
        customDialog.isOutTouchDismiss(false);
        if (!TextUtils.isEmpty(this.cat_id) && this.cat_id.equals("2") && TextUtils.equals(resultcode, PayMethodKeyKt.PAY_SUCCESS_CODE)) {
            customDialog.setMessage("您购买的商品为视频课程，可以在课程模块进行观看。");
        } else {
            customDialog.setMessage(message);
        }
        customDialog.setPositiveBtn("确定", new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.i0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13604c.lambda$mShowDialog$4(resultcode, customDialog, view);
            }
        });
        customDialog.setNegativeBtn("取消", new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.j0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13609c.lambda$mShowDialog$5(resultcode, customDialog, view);
            }
        });
        customDialog.show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Subscribe
    public void onEventMainThread(WXPayEvent event) {
        int i2 = AnonymousClass2.$SwitchMap$com$psychiatrygarden$event$WXPayStatus[event.getStatus().ordinal()];
        if (i2 == 1) {
            mShowDialog("用户中途取消", "0");
        } else if (i2 == 2) {
            mShowDialog("支付失败", "0");
        } else {
            if (i2 != 3) {
                return;
            }
            mShowDialog("支付成功", PayMethodKeyKt.PAY_SUCCESS_CODE);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_paygoods);
        Uri data = getIntent().getData();
        boolean z2 = true;
        if (data != null) {
            if (!"ebook".equals(data.getScheme()) && !getIntent().getStringExtra("from").equals("buy_ebook")) {
                z2 = false;
            }
            this.fromEBook = z2;
        } else if (getIntent().getStringExtra("from") != null && getIntent().getStringExtra("from").equals("buy_ebook")) {
            this.fromEBook = true;
        }
        this.rMealBean = (List) getIntent().getSerializableExtra("productData");
        try {
            this.cat_id = getIntent().getExtras().getString("cat_id", "");
            this.after_purchase_goto = getIntent().getExtras().getString("after_purchase_goto", "");
            this.cs = getIntent().getExtras().getString("cs", "");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            this.upgrade_type = getIntent().getExtras().getString("upgrade_type", "");
            this.deduction_id = getIntent().getExtras().getString("deduction_id", "");
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        setTitle("支付方式");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.okzhifu.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.d0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13582c.lambda$setListenerForWidget$1(view);
            }
        });
        this.layoutWXPay.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.e0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13586c.lambda$setListenerForWidget$2(view);
            }
        });
        this.layoutALiPay.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.f0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13592c.lambda$setListenerForWidget$3(view);
            }
        });
    }
}

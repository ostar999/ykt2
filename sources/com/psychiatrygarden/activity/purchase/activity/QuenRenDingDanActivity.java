package com.psychiatrygarden.activity.purchase.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.Key;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.purchase.beans.AddressBean;
import com.psychiatrygarden.activity.purchase.beans.GoodsBean;
import com.psychiatrygarden.activity.purchase.beans.SaleGoodsBean;
import com.psychiatrygarden.activity.purchase.beans.ShouhuodizhiBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.UILoader;
import com.yikaobang.yixue.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class QuenRenDingDanActivity extends BaseActivity {
    private String area_id;
    TextView data_song;
    EditText edite;
    private LinearLayout llay_buy_goods;
    public List<ShouhuodizhiBean> mlist;
    RelativeLayout shouhuo_rl;
    TextView textView3;
    private String total_amount;
    TextView tv_jia;
    TextView tv_jian;
    TextView tv_kuaidi;
    TextView tv_money2;
    TextView tv_moneys;
    TextView tv_num;
    TextView tv_phones;
    TextView tv_shouhuoren;
    TextView tv_shouhuos;
    TextView tv_tijiao;
    List<GoodsBean.DataBean.MealBean> mGoodsMeanBeans = new ArrayList();

    @SuppressLint({"HandlerLeak"})
    Handler mhHandler = new Handler() { // from class: com.psychiatrygarden.activity.purchase.activity.QuenRenDingDanActivity.1
        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int i2 = msg.what;
            if (i2 == 1) {
                if (QuenRenDingDanActivity.this.mGoodsMeanBeans.size() > 0) {
                    QuenRenDingDanActivity.this.mGoodsMeanBeans.get(0).setQuantity((Integer.parseInt(QuenRenDingDanActivity.this.mGoodsMeanBeans.get(0).getQuantity()) + 1) + "");
                    QuenRenDingDanActivity quenRenDingDanActivity = QuenRenDingDanActivity.this;
                    quenRenDingDanActivity.tv_num.setText(quenRenDingDanActivity.mGoodsMeanBeans.get(0).getQuantity());
                    QuenRenDingDanActivity.this.CombMethod();
                    return;
                }
                return;
            }
            if (i2 == 2 && QuenRenDingDanActivity.this.mGoodsMeanBeans.size() > 0) {
                if (Integer.parseInt(QuenRenDingDanActivity.this.mGoodsMeanBeans.get(0).getQuantity()) <= 1) {
                    QuenRenDingDanActivity.this.AlertToast("亲，我也是有底线的哦！");
                    return;
                }
                QuenRenDingDanActivity.this.mGoodsMeanBeans.get(0).setQuantity((Integer.parseInt(QuenRenDingDanActivity.this.mGoodsMeanBeans.get(0).getQuantity()) - 1) + "");
                QuenRenDingDanActivity quenRenDingDanActivity2 = QuenRenDingDanActivity.this;
                quenRenDingDanActivity2.tv_num.setText(quenRenDingDanActivity2.mGoodsMeanBeans.get(0).getQuantity());
                QuenRenDingDanActivity.this.CombMethod();
            }
        }
    };
    View.OnClickListener onClick = new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.QuenRenDingDanActivity.4
        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
            if (CommonUtil.isFastClick()) {
                return;
            }
            int id = v2.getId();
            if (id == R.id.tv_jia) {
                QuenRenDingDanActivity.this.mhHandler.sendEmptyMessage(1);
            } else if (id == R.id.tv_jian) {
                QuenRenDingDanActivity.this.mhHandler.sendEmptyMessage(2);
            } else if (id == R.id.tv_tijiao) {
                if (QuenRenDingDanActivity.this.area_id == null || QuenRenDingDanActivity.this.area_id.equals("")) {
                    QuenRenDingDanActivity.this.handInDialog();
                    return;
                }
                Intent intent = new Intent(QuenRenDingDanActivity.this.mContext, (Class<?>) PayGoodsNewActivity.class);
                intent.putExtra("productData", (Serializable) QuenRenDingDanActivity.this.mGoodsMeanBeans);
                intent.putExtra("total_amount", QuenRenDingDanActivity.this.total_amount);
                intent.putExtra("message", QuenRenDingDanActivity.this.edite.getText().toString());
                intent.putExtra("user_address_id", QuenRenDingDanActivity.this.area_id);
                intent.putExtra("flag", "" + QuenRenDingDanActivity.this.getIntent().getExtras().getString("flag"));
                QuenRenDingDanActivity.this.startActivity(intent);
            }
            if (id == R.id.tv_shouhuoren || id == R.id.tv_phones || id == R.id.tv_shouhuos || id == R.id.shouhuo_rl) {
                QuenRenDingDanActivity.this.startActivity(new Intent(QuenRenDingDanActivity.this.getApplicationContext(), (Class<?>) ShouhuodizhiActivity.class));
            }
        }
    };

    private void getBuyData() {
        if (this.mGoodsMeanBeans == null) {
            return;
        }
        this.llay_buy_goods.removeAllViews();
        for (int i2 = 0; i2 < this.mGoodsMeanBeans.size(); i2++) {
            GoodsBean.DataBean.MealBean mealBean = this.mGoodsMeanBeans.get(i2);
            View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.item_sure_order_item, (ViewGroup) null);
            ImageView imageView = (ImageView) viewInflate.findViewById(R.id.img_xiaohongshu);
            TextView textView = (TextView) viewInflate.findViewById(R.id.tv_contents);
            TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_taocan);
            TextView textView3 = (TextView) viewInflate.findViewById(R.id.tv_goods_num);
            new UILoader().load(this.mContext, imageView, mealBean.getGoods_thumbnail());
            textView.setText(mealBean.getGoods_name());
            textView2.setText(mealBean.getDescription());
            textView3.setText((Double.parseDouble(mealBean.getPrice()) / 100.0d) + "元 x1");
            this.llay_buy_goods.addView(viewInflate);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handInDialog$0(RelativeLayout relativeLayout, AlertDialog alertDialog, View view) {
        relativeLayout.clearAnimation();
        alertDialog.dismiss();
        ((BaseActivity) this.mContext).finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handInDialog$1(RelativeLayout relativeLayout, AlertDialog alertDialog, View view) {
        relativeLayout.clearAnimation();
        alertDialog.dismiss();
        startActivity(new Intent(this.mContext, (Class<?>) ShouhuodizhiActivity.class));
    }

    public void CombMethod() {
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.mGoodsMeanBeans.size(); i2++) {
                SaleGoodsBean saleGoodsBean = new SaleGoodsBean();
                saleGoodsBean.setGoods_id(this.mGoodsMeanBeans.get(i2).getGoods_id());
                saleGoodsBean.setPrice(this.mGoodsMeanBeans.get(i2).getPrice());
                saleGoodsBean.setQuantity(this.mGoodsMeanBeans.get(i2).getQuantity());
                arrayList.add(saleGoodsBean);
            }
            ajaxParams.put("goods_info", new Gson().toJson(arrayList));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        ajaxParams.put("user_address_id", this.area_id);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.morderFeeUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.activity.QuenRenDingDanActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    QuenRenDingDanActivity.this.total_amount = new JSONObject(s2).optJSONObject("data").optString("total_amount");
                    QuenRenDingDanActivity.this.tv_money2.setText(new JSONObject(s2).optJSONObject("data").optString("total_amount_yuan"));
                    QuenRenDingDanActivity.this.tv_kuaidi.setText(new JSONObject(s2).optJSONObject("data").optString("postage_yuan"));
                    QuenRenDingDanActivity.this.textView3.setText(new JSONObject(s2).optJSONObject("data").optString("express"));
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    public void getAddressData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.mUserDefaultAddress, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.activity.QuenRenDingDanActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    AddressBean addressBean = (AddressBean) new Gson().fromJson(s2, AddressBean.class);
                    if (addressBean.getCode().equals("200")) {
                        if (TextUtils.equals(addressBean.getData().getName(), "")) {
                            QuenRenDingDanActivity.this.handInDialog();
                            return;
                        }
                        QuenRenDingDanActivity.this.tv_shouhuoren.setText(addressBean.getData().getName());
                        QuenRenDingDanActivity.this.tv_shouhuos.setText(String.format("收货地址：%s", addressBean.getData().getFull_address()));
                        QuenRenDingDanActivity.this.tv_phones.setText(addressBean.getData().getMobile());
                        QuenRenDingDanActivity.this.area_id = addressBean.getData().getAddr_id();
                        QuenRenDingDanActivity.this.CombMethod();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void handInDialog() {
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mContext, R.style.MyDialog).create();
        alertDialogCreate.show();
        WindowManager.LayoutParams attributes = alertDialogCreate.getWindow().getAttributes();
        attributes.width = -1;
        attributes.height = -1;
        alertDialogCreate.getWindow().setAttributes(attributes);
        alertDialogCreate.getWindow().setContentView(R.layout.activity_d);
        alertDialogCreate.setCanceledOnTouchOutside(false);
        alertDialogCreate.setCancelable(true);
        final RelativeLayout relativeLayout = (RelativeLayout) alertDialogCreate.findViewById(R.id.f26073r1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(relativeLayout, Key.ROTATION, -3.0f, 3.5f, -1.5f, 0.0f).setDuration(500L));
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
        TextView textView = (TextView) alertDialogCreate.findViewById(R.id.tv_fou);
        TextView textView2 = (TextView) alertDialogCreate.findViewById(R.id.tv_shi);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.k0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13614c.lambda$handInDialog$0(relativeLayout, alertDialogCreate, view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.l0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13621c.lambda$handInDialog$1(relativeLayout, alertDialogCreate, view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mlist = new ArrayList();
        TextView textView = (TextView) findViewById(R.id.textView2);
        TextView textView2 = (TextView) findViewById(R.id.textView1);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            textView.setVisibility(0);
            textView2.setVisibility(0);
        } else {
            textView.setVisibility(8);
            textView2.setVisibility(8);
        }
        this.llay_buy_goods = (LinearLayout) findViewById(R.id.llay_buy_goods);
        this.textView3 = (TextView) findViewById(R.id.textView3);
        this.tv_jia = (TextView) findViewById(R.id.tv_jia);
        this.tv_num = (TextView) findViewById(R.id.tv_num);
        this.tv_jian = (TextView) findViewById(R.id.tv_jian);
        this.tv_kuaidi = (TextView) findViewById(R.id.tv_kuaidi);
        this.data_song = (TextView) findViewById(R.id.data_song);
        this.tv_moneys = (TextView) findViewById(R.id.tv_moneys);
        this.tv_money2 = (TextView) findViewById(R.id.tv_money2);
        this.tv_tijiao = (TextView) findViewById(R.id.tv_tijiao);
        this.tv_shouhuoren = (TextView) findViewById(R.id.tv_shouhuoren);
        this.tv_phones = (TextView) findViewById(R.id.tv_phones);
        this.tv_shouhuos = (TextView) findViewById(R.id.tv_shouhuos);
        this.edite = (EditText) findViewById(R.id.edite);
        this.tv_jia.setOnClickListener(this.onClick);
        this.tv_num.setOnClickListener(this.onClick);
        this.tv_jian.setOnClickListener(this.onClick);
        this.tv_kuaidi.setOnClickListener(this.onClick);
        this.tv_moneys.setOnClickListener(this.onClick);
        this.tv_money2.setOnClickListener(this.onClick);
        this.tv_tijiao.setOnClickListener(this.onClick);
        this.tv_shouhuoren.setOnClickListener(this.onClick);
        this.tv_phones.setOnClickListener(this.onClick);
        this.tv_shouhuos.setOnClickListener(this.onClick);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.shouhuo_rl);
        this.shouhuo_rl = relativeLayout;
        relativeLayout.setOnClickListener(this.onClick);
        this.tv_num.setText(String.format("%s", this.mGoodsMeanBeans.get(0).getQuantity()));
        getBuyData();
        getAddressData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("RefreshAddress")) {
            getAddressData();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mGoodsMeanBeans = (List) getIntent().getExtras().getSerializable("productData");
        setContentView(R.layout.activity_dingdan);
        setTitle("确认订单");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

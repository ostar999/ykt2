package com.psychiatrygarden.activity.purchase.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.motion.widget.Key;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.purchase.activity.QuenRenDingDanNewActivity;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.activity.purchase.beans.AddrDataBean;
import com.psychiatrygarden.activity.purchase.beans.GoodsBean;
import com.psychiatrygarden.activity.purchase.beans.SaleGoodsBean;
import com.psychiatrygarden.activity.purchase.beans.ShouhuodizhiBean;
import com.psychiatrygarden.activity.purchase.beans.ShowAddressBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.utils.UILoader;
import com.psychiatrygarden.widget.MyListView;
import com.yikaobang.yixue.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class QuenRenDingDanNewActivity extends BaseActivity {
    private CommAdapter<ShowAddressBean.DataBean> adapter;
    private MyListView address_line;
    private TextView coupon;
    TextView data_song;
    EditText edite;
    private TextView goodstotal;
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
    private final List<ShowAddressBean.DataBean> addrDataBeans = new ArrayList();
    private String cat_id = "";
    private String after_purchase_goto = "";
    private String cs = "";
    List<GoodsBean.DataBean.MealBean> mGoodsMeanBeans = new ArrayList();

    @SuppressLint({"HandlerLeak"})
    Handler mhHandler = new Handler() { // from class: com.psychiatrygarden.activity.purchase.activity.QuenRenDingDanNewActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                QuenRenDingDanNewActivity.this.CombMethod();
            }
        }
    };
    View.OnClickListener onClick = new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.QuenRenDingDanNewActivity.5
        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
            if (CommonUtil.isFastClick()) {
            }
            switch (v2.getId()) {
                case R.id.shouhuo_rl /* 2131366922 */:
                case R.id.tv_phones /* 2131368365 */:
                case R.id.tv_shouhuoren /* 2131368579 */:
                case R.id.tv_shouhuos /* 2131368580 */:
                    Intent intent = new Intent(QuenRenDingDanNewActivity.this.getApplicationContext(), (Class<?>) ShouhuodizhiActivity.class);
                    intent.putExtra("addList", (Serializable) QuenRenDingDanNewActivity.this.addrDataBeans);
                    intent.putExtra("isDuoxuan", QuenRenDingDanNewActivity.this.getIntent().getExtras().getString("group_purchase"));
                    QuenRenDingDanNewActivity.this.startActivityForResult(intent, 10000);
                    break;
                case R.id.tv_jia /* 2131368127 */:
                    QuenRenDingDanNewActivity.this.mhHandler.sendEmptyMessage(1);
                    break;
                case R.id.tv_jian /* 2131368128 */:
                    QuenRenDingDanNewActivity.this.mhHandler.sendEmptyMessage(2);
                    break;
                case R.id.tv_tijiao /* 2131368678 */:
                    if (QuenRenDingDanNewActivity.this.addrDataBeans != null && QuenRenDingDanNewActivity.this.addrDataBeans.size() != 0) {
                        ArrayList arrayList = new ArrayList();
                        for (int i2 = 0; i2 < QuenRenDingDanNewActivity.this.addrDataBeans.size(); i2++) {
                            AddrDataBean addrDataBean = new AddrDataBean();
                            addrDataBean.setAddr_id(((ShowAddressBean.DataBean) QuenRenDingDanNewActivity.this.addrDataBeans.get(i2)).getAddr_id());
                            addrDataBean.setQuantity(((ShowAddressBean.DataBean) QuenRenDingDanNewActivity.this.addrDataBeans.get(i2)).getCount() + "");
                            arrayList.add(addrDataBean);
                        }
                        Intent intent2 = new Intent(QuenRenDingDanNewActivity.this.mContext, (Class<?>) PayGoodsNewActivity.class);
                        intent2.putExtra("productData", (Serializable) QuenRenDingDanNewActivity.this.mGoodsMeanBeans);
                        intent2.putExtra("total_amount", QuenRenDingDanNewActivity.this.total_amount);
                        intent2.putExtra("cat_id", QuenRenDingDanNewActivity.this.cat_id + "");
                        intent2.putExtra("after_purchase_goto", QuenRenDingDanNewActivity.this.after_purchase_goto + "");
                        intent2.putExtra("cs", QuenRenDingDanNewActivity.this.cs + "");
                        intent2.putExtra("message", QuenRenDingDanNewActivity.this.edite.getText().toString());
                        intent2.putExtra("user_address_id", new Gson().toJson(arrayList).toString());
                        intent2.putExtra("flag", "" + QuenRenDingDanNewActivity.this.getIntent().getExtras().getString("flag"));
                        QuenRenDingDanNewActivity.this.startActivity(intent2);
                        break;
                    } else {
                        QuenRenDingDanNewActivity.this.handInDialog();
                        break;
                    }
            }
        }
    };

    /* renamed from: com.psychiatrygarden.activity.purchase.activity.QuenRenDingDanNewActivity$2, reason: invalid class name */
    public class AnonymousClass2 extends CommAdapter<ShowAddressBean.DataBean> {
        public AnonymousClass2(List mData, Context mcontext, int layoutId) {
            super(mData, mcontext, layoutId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$convert$0(ViewHolder viewHolder, View view, MotionEvent motionEvent) {
            ((ViewGroup) viewHolder.getConvertView()).setDescendantFocusability(131072);
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$convert$1(View view, MotionEvent motionEvent) {
            ((ViewGroup) view).setDescendantFocusability(393216);
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$2(ShowAddressBean.DataBean dataBean, EditText editText, View view) {
            if (dataBean.getCount() <= 1) {
                ToastUtil.shortToast(QuenRenDingDanNewActivity.this.mContext, "已经最低了");
                return;
            }
            dataBean.setCount(dataBean.getCount() - 1);
            editText.setText(dataBean.getCount() + "");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$convert$3(ShowAddressBean.DataBean dataBean, EditText editText, View view) {
            dataBean.setCount(dataBean.getCount() + 1);
            editText.setText(String.valueOf(dataBean.getCount()));
        }

        @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
        public void convert(final ViewHolder vHolder, final ShowAddressBean.DataBean dataBean, int position) {
            TextView textView = (TextView) vHolder.getView(R.id.dbiaoshitext);
            TextView textView2 = (TextView) vHolder.getView(R.id.addrptext);
            TextView textView3 = (TextView) vHolder.getView(R.id.addrdtext);
            TextView textView4 = (TextView) vHolder.getView(R.id.addrname);
            TextView textView5 = (TextView) vHolder.getView(R.id.jianText);
            final EditText editText = (EditText) vHolder.getView(R.id.numText);
            TextView textView6 = (TextView) vHolder.getView(R.id.jiaText);
            textView.setText(String.format(Locale.CHINA, "地址%d", Integer.valueOf(position + 1)));
            textView2.setText(String.format("%s%s", dataBean.getProvince_title(), dataBean.getCity_title()));
            textView3.setText(dataBean.getStreet());
            textView4.setText(String.format("%s %s", dataBean.getName(), CommonUtil.BlurTelNumber(dataBean.getMobile())));
            editText.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.activity.purchase.activity.q0
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return QuenRenDingDanNewActivity.AnonymousClass2.lambda$convert$0(vHolder, view, motionEvent);
                }
            });
            vHolder.getConvertView().setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.activity.purchase.activity.r0
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return QuenRenDingDanNewActivity.AnonymousClass2.lambda$convert$1(view, motionEvent);
                }
            });
            editText.clearFocus();
            if (editText.getTag() instanceof TextWatcher) {
                editText.removeTextChangedListener((TextWatcher) editText.getTag());
            }
            editText.setText(String.valueOf(dataBean.getCount()));
            if (!TextUtils.isEmpty(dataBean.getCount() + "")) {
                editText.setSelection((dataBean.getCount() + "").length());
            }
            TextWatcher textWatcher = new TextWatcher() { // from class: com.psychiatrygarden.activity.purchase.activity.QuenRenDingDanNewActivity.2.1
                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable s2) {
                    editText.setSelection(s2.length());
                    if (TextUtils.isEmpty(s2.toString())) {
                        return;
                    }
                    dataBean.setCount(Integer.parseInt(s2.toString()) != 0 ? Integer.parseInt(s2.toString()) : 1);
                    AnonymousClass2.this.notifyDataSetChanged();
                    QuenRenDingDanNewActivity.this.mhHandler.sendEmptyMessage(1);
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
                    editText.setSelection(s2.length());
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence s2, int start, int before, int count) {
                    editText.setSelection(s2.length());
                }
            };
            editText.addTextChangedListener(textWatcher);
            editText.setTag(textWatcher);
            textView5.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.s0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13650c.lambda$convert$2(dataBean, editText, view);
                }
            });
            textView6.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.t0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    QuenRenDingDanNewActivity.AnonymousClass2.lambda$convert$3(dataBean, editText, view);
                }
            });
        }
    }

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
    public /* synthetic */ void lambda$handInDialog$2(RelativeLayout relativeLayout, AlertDialog alertDialog, View view) {
        relativeLayout.clearAnimation();
        alertDialog.dismiss();
        ((BaseActivity) this.mContext).finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handInDialog$3(RelativeLayout relativeLayout, AlertDialog alertDialog, View view) {
        relativeLayout.clearAnimation();
        alertDialog.dismiss();
        Intent intent = new Intent(this.mContext, (Class<?>) ShouhuodizhiActivity.class);
        intent.putExtra("addList", (Serializable) this.addrDataBeans);
        intent.putExtra("isDuoxuan", getIntent().getExtras().getString("group_purchase"));
        startActivityForResult(intent, 10000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        Intent intent = new Intent(getApplicationContext(), (Class<?>) ShouhuodizhiActivity.class);
        intent.putExtra("addList", (Serializable) this.addrDataBeans);
        intent.putExtra("isDuoxuan", getIntent().getExtras().getString("group_purchase"));
        startActivityForResult(intent, 10000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapter$1(AdapterView adapterView, View view, int i2, long j2) {
        Intent intent = new Intent(getApplicationContext(), (Class<?>) ShouhuodizhiActivity.class);
        intent.putExtra("addList", (Serializable) this.addrDataBeans);
        intent.putExtra("isDuoxuan", getIntent().getExtras().getString("group_purchase"));
        startActivityForResult(intent, 10000);
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
                if (!TextUtils.isEmpty(this.mGoodsMeanBeans.get(i2).getSku_id())) {
                    saleGoodsBean.setSku_id(this.mGoodsMeanBeans.get(i2).getSku_id());
                }
                arrayList.add(saleGoodsBean);
            }
            ajaxParams.put("goods_info", new Gson().toJson(arrayList));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        List<ShowAddressBean.DataBean> list = this.addrDataBeans;
        if (list == null || list.size() == 0) {
            handInDialog();
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i3 = 0; i3 < this.addrDataBeans.size(); i3++) {
            AddrDataBean addrDataBean = new AddrDataBean();
            addrDataBean.setAddr_id(this.addrDataBeans.get(i3).getAddr_id());
            addrDataBean.setQuantity(this.addrDataBeans.get(i3).getCount() + "");
            arrayList2.add(addrDataBean);
        }
        ajaxParams.put("address", new Gson().toJson(arrayList2));
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.orderFeeAddrMultipleApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.activity.QuenRenDingDanNewActivity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                QuenRenDingDanNewActivity.this.hideProgressDialog();
                ToastUtil.shortToast(QuenRenDingDanNewActivity.this, "金额计算出错，请重新购买！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                QuenRenDingDanNewActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    QuenRenDingDanNewActivity.this.total_amount = new JSONObject(s2).optJSONObject("data").optString("total_amount");
                    QuenRenDingDanNewActivity.this.tv_money2.setText(new JSONObject(s2).optJSONObject("data").optString("total_amount_yuan"));
                    QuenRenDingDanNewActivity.this.tv_kuaidi.setText(new JSONObject(s2).optJSONObject("data").optString("postage_yuan"));
                    QuenRenDingDanNewActivity.this.textView3.setText(new JSONObject(s2).optJSONObject("data").optString("express"));
                    QuenRenDingDanNewActivity.this.goodstotal.setText(new JSONObject(s2).optJSONObject("data").optString("goods_amount_yuan"));
                    QuenRenDingDanNewActivity.this.coupon.setText(new JSONObject(s2).optJSONObject("data").optString("privilege_yuan"));
                } catch (Exception e3) {
                    e3.printStackTrace();
                    ToastUtil.shortToast(QuenRenDingDanNewActivity.this, "金额计算出错，请重新购买！");
                }
                QuenRenDingDanNewActivity.this.hideProgressDialog();
            }
        });
    }

    public void getAddressData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.mUserDefaultAddress, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.activity.QuenRenDingDanNewActivity.3
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
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        new JSONObject(s2).optJSONObject("data").optString("name");
                        if (TextUtils.isEmpty(new JSONObject(s2).optJSONObject("data").optString("name"))) {
                            QuenRenDingDanNewActivity.this.handInDialog();
                            return;
                        }
                        ShowAddressBean.DataBean dataBean = (ShowAddressBean.DataBean) new Gson().fromJson(new JSONObject(s2).optString("data"), ShowAddressBean.DataBean.class);
                        if (QuenRenDingDanNewActivity.this.addrDataBeans.size() > 0) {
                            for (int i2 = 0; i2 < QuenRenDingDanNewActivity.this.addrDataBeans.size(); i2++) {
                                if (((ShowAddressBean.DataBean) QuenRenDingDanNewActivity.this.addrDataBeans.get(i2)).getAddr_id().equals(dataBean.getAddr_id())) {
                                    dataBean.setCount(((ShowAddressBean.DataBean) QuenRenDingDanNewActivity.this.addrDataBeans.get(i2)).getCount());
                                }
                            }
                        }
                        QuenRenDingDanNewActivity.this.addrDataBeans.clear();
                        QuenRenDingDanNewActivity.this.addrDataBeans.add(dataBean);
                        QuenRenDingDanNewActivity.this.adapter.notifyDataSetChanged();
                        QuenRenDingDanNewActivity.this.CombMethod();
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
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.n0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13632c.lambda$handInDialog$2(relativeLayout, alertDialogCreate, view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.o0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13637c.lambda$handInDialog$3(relativeLayout, alertDialogCreate, view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        try {
            this.cat_id = getIntent().getExtras().getString("cat_id", "");
            this.after_purchase_goto = getIntent().getExtras().getString("after_purchase_goto", "");
            this.cs = getIntent().getExtras().getString("cs", "");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.mlist = new ArrayList();
        this.address_line = (MyListView) findViewById(R.id.address_line);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.addressview);
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
        this.goodstotal = (TextView) findViewById(R.id.goodstotal);
        this.coupon = (TextView) findViewById(R.id.coupon);
        initAdapter();
        getBuyData();
        getAddressData();
        if (getIntent().getExtras().getString("group_purchase").equals("1")) {
            linearLayout.setVisibility(0);
        } else {
            linearLayout.setVisibility(8);
        }
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.m0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13626c.lambda$init$0(view);
            }
        });
    }

    public void initAdapter() {
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.addrDataBeans, this.mContext, R.layout.layout_address_item);
        this.adapter = anonymousClass2;
        this.address_line.setAdapter((ListAdapter) anonymousClass2);
        this.address_line.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.p0
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                this.f13642c.lambda$initAdapter$1(adapterView, view, i2, j2);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        super.initStatusBar();
        if (this.mBaseTheme == 1) {
            getWindow().setNavigationBarColor(Color.parseColor("#1C2134"));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        if (arg2 != null && arg0 == 10000) {
            this.addrDataBeans.clear();
            this.addrDataBeans.addAll((List) arg2.getExtras().getSerializable("datalist"));
            CommAdapter<ShowAddressBean.DataBean> commAdapter = this.adapter;
            if (commAdapter != null) {
                commAdapter.notifyDataSetChanged();
            }
            this.mhHandler.sendEmptyMessage(1);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("RefreshAddress")) {
            getAddressData();
        }
        if ("BuySuccess".equals(str)) {
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mGoodsMeanBeans = (List) getIntent().getExtras().getSerializable("productData");
        setContentView(R.layout.activity_quen_ren_ding_dan_new);
        setTitle("确认订单");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

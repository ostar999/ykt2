package com.psychiatrygarden.activity.purchase.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.MyCustomerServiceActivity;
import com.psychiatrygarden.activity.purchase.beans.GoodsOrderDetail;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.UILoader;
import com.yikaobang.yixue.R;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class ZhangDanXiangQingActivity extends BaseActivity {
    TextView data_song;
    TextView dingwei;
    TextView kefu;
    TextView kuaidiming;
    GoodsOrderDetail mgGoodsOrderDetails;
    View.OnClickListener onClick = new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.u1
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            this.f13663c.lambda$new$0(view);
        }
    };
    TextView quit_tv;
    LinearLayout r2;
    TextView r2_txt;
    LinearLayout rl;
    LinearLayout rl_views;
    TextView shijian;
    RelativeLayout shouhuo_rl;

    /* renamed from: t, reason: collision with root package name */
    TextView f13562t;
    TextView textView1;
    TextView textView23;
    TextView textView25;
    TextView textView3;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    TextView tv_dingdanshijian;
    TextView tv_jia;
    TextView tv_jian;
    TextView tv_kuaidi;
    TextView tv_num;
    TextView tv_tmoney;
    TextView tv_tmoney2;
    TextView tv_view;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (!CommonUtil.isFastClick() && view.getId() == R.id.kefu) {
            goActivity(MyCustomerServiceActivity.class);
        }
    }

    public void getDingDanXiangQ() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(ActSubmitGoodsComment.EXTRA_DATA_ORDER_NO, getIntent().getExtras().getString(ActSubmitGoodsComment.EXTRA_DATA_ORDER_NO));
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.mGoodsgetOrder, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.activity.ZhangDanXiangQingActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String res) {
                super.onSuccess((AnonymousClass1) res);
                try {
                    ZhangDanXiangQingActivity.this.mgGoodsOrderDetails = (GoodsOrderDetail) new Gson().fromJson(res, GoodsOrderDetail.class);
                    if (ZhangDanXiangQingActivity.this.mgGoodsOrderDetails.getCode().equals("200")) {
                        ZhangDanXiangQingActivity zhangDanXiangQingActivity = ZhangDanXiangQingActivity.this;
                        zhangDanXiangQingActivity.textView7.setText(String.format("订单号：%s", zhangDanXiangQingActivity.mgGoodsOrderDetails.getData().getOrder_no()));
                        ZhangDanXiangQingActivity zhangDanXiangQingActivity2 = ZhangDanXiangQingActivity.this;
                        zhangDanXiangQingActivity2.textView8.setText(zhangDanXiangQingActivity2.mgGoodsOrderDetails.getData().getStatus_str());
                        ZhangDanXiangQingActivity zhangDanXiangQingActivity3 = ZhangDanXiangQingActivity.this;
                        zhangDanXiangQingActivity3.textView3.setText(zhangDanXiangQingActivity3.mgGoodsOrderDetails.getData().getExpress_address());
                        ZhangDanXiangQingActivity zhangDanXiangQingActivity4 = ZhangDanXiangQingActivity.this;
                        zhangDanXiangQingActivity4.textView6.setText(zhangDanXiangQingActivity4.mgGoodsOrderDetails.getData().getExpress_receiver());
                        ZhangDanXiangQingActivity zhangDanXiangQingActivity5 = ZhangDanXiangQingActivity.this;
                        zhangDanXiangQingActivity5.textView23.setText(zhangDanXiangQingActivity5.mgGoodsOrderDetails.getData().getExpress_mobile());
                        ZhangDanXiangQingActivity zhangDanXiangQingActivity6 = ZhangDanXiangQingActivity.this;
                        zhangDanXiangQingActivity6.tv_kuaidi.setText(String.format("费用：%s", zhangDanXiangQingActivity6.mgGoodsOrderDetails.getData().getExpress_fee()));
                        ZhangDanXiangQingActivity zhangDanXiangQingActivity7 = ZhangDanXiangQingActivity.this;
                        zhangDanXiangQingActivity7.data_song.setText(String.format("配送：%s", zhangDanXiangQingActivity7.mgGoodsOrderDetails.getData().getExpress()));
                        ZhangDanXiangQingActivity zhangDanXiangQingActivity8 = ZhangDanXiangQingActivity.this;
                        zhangDanXiangQingActivity8.f13562t.setText(zhangDanXiangQingActivity8.mgGoodsOrderDetails.getData().getLeave_message());
                        ZhangDanXiangQingActivity zhangDanXiangQingActivity9 = ZhangDanXiangQingActivity.this;
                        zhangDanXiangQingActivity9.tv_tmoney2.setText(zhangDanXiangQingActivity9.mgGoodsOrderDetails.getData().getTotal_amount());
                        ZhangDanXiangQingActivity zhangDanXiangQingActivity10 = ZhangDanXiangQingActivity.this;
                        zhangDanXiangQingActivity10.tv_dingdanshijian.setText(zhangDanXiangQingActivity10.mgGoodsOrderDetails.getData().getCtime());
                        List<GoodsOrderDetail.DataBean.DataBanGoods> goods = ZhangDanXiangQingActivity.this.mgGoodsOrderDetails.getData().getGoods();
                        ZhangDanXiangQingActivity.this.rl_views.removeAllViews();
                        for (int i2 = 0; i2 < goods.size(); i2++) {
                            GoodsOrderDetail.DataBean.DataBanGoods dataBanGoods = goods.get(i2);
                            View viewInflate = LayoutInflater.from(ZhangDanXiangQingActivity.this.mContext).inflate(R.layout.item_sure_order_item, (ViewGroup) null);
                            ImageView imageView = (ImageView) viewInflate.findViewById(R.id.img_xiaohongshu);
                            TextView textView = (TextView) viewInflate.findViewById(R.id.tv_contents);
                            TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_taocan);
                            TextView textView3 = (TextView) viewInflate.findViewById(R.id.tv_goods_num);
                            new UILoader().load(ZhangDanXiangQingActivity.this.mContext, imageView, dataBanGoods.getGoods_thumbnail());
                            textView.setText(dataBanGoods.getGoods_name());
                            textView2.setText(dataBanGoods.getGoods_description());
                            textView3.setText(String.format("%s%s", dataBanGoods.getPrice(), dataBanGoods.getQuantity()));
                            ZhangDanXiangQingActivity.this.rl_views.addView(viewInflate);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("订单详情");
        this.rl = (LinearLayout) findViewById(R.id.rl);
        this.r2 = (LinearLayout) findViewById(R.id.r2);
        this.r2_txt = (TextView) findViewById(R.id.r2_txt);
        this.kefu = (TextView) findViewById(R.id.kefu);
        this.kuaidiming = (TextView) findViewById(R.id.kuaidiming);
        this.textView25 = (TextView) findViewById(R.id.textView25);
        this.textView3 = (TextView) findViewById(R.id.textView3);
        this.textView7 = (TextView) findViewById(R.id.textView7);
        this.textView8 = (TextView) findViewById(R.id.textView8);
        this.textView6 = (TextView) findViewById(R.id.textView6);
        this.textView23 = (TextView) findViewById(R.id.textView23);
        this.shouhuo_rl = (RelativeLayout) findViewById(R.id.shouhuo_rl);
        this.tv_view = (TextView) findViewById(R.id.tv_view);
        this.f13562t = (TextView) findViewById(R.id.f26074t);
        this.tv_kuaidi = (TextView) findViewById(R.id.tv_kuaidi);
        this.tv_dingdanshijian = (TextView) findViewById(R.id.tv_dingdanshijian);
        this.tv_tmoney = (TextView) findViewById(R.id.tv_tmoney);
        this.tv_tmoney2 = (TextView) findViewById(R.id.tv_tmoney2);
        this.data_song = (TextView) findViewById(R.id.data_song);
        this.dingwei = (TextView) findViewById(R.id.textView2);
        this.textView1 = (TextView) findViewById(R.id.textView1);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.dingwei.setVisibility(0);
            this.textView1.setVisibility(0);
        } else {
            this.dingwei.setVisibility(8);
            this.textView1.setVisibility(8);
        }
        this.shijian = (TextView) findViewById(R.id.shijian_time);
        this.quit_tv = (TextView) findViewById(R.id.quit_tv);
        this.tv_jia = (TextView) findViewById(R.id.tv_jia);
        this.tv_jian = (TextView) findViewById(R.id.tv_jian);
        this.tv_num = (TextView) findViewById(R.id.tv_num);
        this.quit_tv.setOnClickListener(this.onClick);
        this.tv_jia.setOnClickListener(this.onClick);
        this.tv_jian.setOnClickListener(this.onClick);
        this.tv_num.setOnClickListener(this.onClick);
        this.textView25.setOnClickListener(this.onClick);
        this.shouhuo_rl.setOnClickListener(this.onClick);
        this.r2.setOnClickListener(this.onClick);
        this.r2_txt.setOnClickListener(this.onClick);
        this.kefu.setOnClickListener(this.onClick);
        this.rl_views = (LinearLayout) findViewById(R.id.rl_views);
        getDingDanXiangQ();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_zhangdanxiangqing);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

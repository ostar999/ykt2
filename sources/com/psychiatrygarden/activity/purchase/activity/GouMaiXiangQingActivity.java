package com.psychiatrygarden.activity.purchase.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.gson.Gson;
import com.just.agentweb.DefaultWebClient;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.activity.purchase.beans.GouMaiXiangQingBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CharacterParser;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.NavigationUtilKt;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.TextViewBorder;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class GouMaiXiangQingActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final int REFRESH_COMPLETE = 272;
    CommAdapter<GouMaiXiangQingBean.DataBean> adapter;
    private View addFooterView;
    GouMaiXiangQingBean mGouMaiXiangQingBean;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    ImageView noContnetimg;
    ListView xqlist;
    int page = 1;
    private List<GouMaiXiangQingBean.DataBean> dataList = new ArrayList();

    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.purchase.activity.GouMaiXiangQingActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 272) {
                GouMaiXiangQingActivity.this.mSwipeRefreshLayout.setRefreshing(true);
                GouMaiXiangQingActivity gouMaiXiangQingActivity = GouMaiXiangQingActivity.this;
                gouMaiXiangQingActivity.page = 1;
                gouMaiXiangQingActivity.getDingdanData();
            }
        }
    };

    /* renamed from: com.psychiatrygarden.activity.purchase.activity.GouMaiXiangQingActivity$2, reason: invalid class name */
    public class AnonymousClass2 implements AbsListView.OnScrollListener {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrollStateChanged$0() {
            GouMaiXiangQingActivity gouMaiXiangQingActivity = GouMaiXiangQingActivity.this;
            gouMaiXiangQingActivity.page++;
            gouMaiXiangQingActivity.getDingdanData();
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0 && view.getLastVisiblePosition() == view.getCount() - 1 && GouMaiXiangQingActivity.this.xqlist.getFooterViewsCount() <= 0) {
                GouMaiXiangQingActivity gouMaiXiangQingActivity = GouMaiXiangQingActivity.this;
                gouMaiXiangQingActivity.xqlist.addFooterView(gouMaiXiangQingActivity.addFooterView);
                GouMaiXiangQingActivity.this.addFooterView.setVisibility(0);
                if (GouMaiXiangQingActivity.this.mSwipeRefreshLayout.isRefreshing()) {
                    GouMaiXiangQingActivity.this.mSwipeRefreshLayout.setRefreshing(false);
                } else {
                    new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.purchase.activity.w
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f13666c.lambda$onScrollStateChanged$0();
                        }
                    }, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                }
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.purchase.activity.GouMaiXiangQingActivity$3, reason: invalid class name */
    public class AnonymousClass3 extends AjaxCallBack<String> {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(AdapterView adapterView, View view, int i2, long j2) {
            if (((GouMaiXiangQingBean.DataBean) GouMaiXiangQingActivity.this.dataList.get(i2)).getGoods_type().equals("1")) {
                Intent intent = new Intent(GouMaiXiangQingActivity.this.getApplicationContext(), (Class<?>) ZhangDanXiangQingActivity.class);
                intent.putExtra(ActSubmitGoodsComment.EXTRA_DATA_ORDER_NO, ((GouMaiXiangQingBean.DataBean) GouMaiXiangQingActivity.this.dataList.get(i2)).getOrder_no());
                GouMaiXiangQingActivity.this.startActivity(intent);
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            if (GouMaiXiangQingActivity.this.mSwipeRefreshLayout.isRefreshing()) {
                GouMaiXiangQingActivity.this.mSwipeRefreshLayout.setRefreshing(false);
            }
            if (GouMaiXiangQingActivity.this.dataList == null || GouMaiXiangQingActivity.this.dataList.size() <= 0) {
                GouMaiXiangQingActivity.this.noContnetimg.setVisibility(0);
            } else {
                GouMaiXiangQingActivity.this.noContnetimg.setVisibility(8);
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String t2) {
            super.onSuccess((AnonymousClass3) t2);
            try {
                GouMaiXiangQingActivity.this.mGouMaiXiangQingBean = (GouMaiXiangQingBean) new Gson().fromJson(t2, GouMaiXiangQingBean.class);
                if (GouMaiXiangQingActivity.this.mGouMaiXiangQingBean.getCode().equals("200")) {
                    GouMaiXiangQingActivity gouMaiXiangQingActivity = GouMaiXiangQingActivity.this;
                    if (gouMaiXiangQingActivity.page == 1) {
                        gouMaiXiangQingActivity.dataList = gouMaiXiangQingActivity.mGouMaiXiangQingBean.getData();
                        GouMaiXiangQingActivity.this.adapter = new CommAdapter<GouMaiXiangQingBean.DataBean>(GouMaiXiangQingActivity.this.dataList, GouMaiXiangQingActivity.this.mContext, R.layout.activity_goumaixiangqing) { // from class: com.psychiatrygarden.activity.purchase.activity.GouMaiXiangQingActivity.3.1
                            @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
                            public void convert(ViewHolder vHolder, final GouMaiXiangQingBean.DataBean dataBean, int position) {
                                RoundedImageView roundedImageView = (RoundedImageView) vHolder.getView(R.id.iv_my_order);
                                vHolder.setText(R.id.tv_dingdanhao, "订单号：" + dataBean.getOrder_no()).setText(R.id.addr, dataBean.getExpress_detail().getFull_address()).setText(R.id.addrname, dataBean.getExpress_detail().getName() + " " + CommonUtil.BluridCardNumber(dataBean.getExpress_detail().getMobile())).setText(R.id.tv_chengjiaoshijian, dataBean.getCtime()).setText(R.id.tv_counrs, dataBean.getGoods_name());
                                GlideApp.with(roundedImageView.getContext()).load((Object) GlideUtils.generateUrl(dataBean.getGoods_thumbnail())).placeholder(R.mipmap.ic_order_default).into(roundedImageView);
                                if ("".equals(dataBean.getExpress_detail().getFull_address())) {
                                    vHolder.getView(R.id.addr).setVisibility(4);
                                    vHolder.getView(R.id.addrname).setVisibility(0);
                                    ((TextView) vHolder.getView(R.id.addrname)).setText("虚拟商品");
                                } else {
                                    vHolder.getView(R.id.addr).setVisibility(0);
                                    vHolder.getView(R.id.addrname).setVisibility(0);
                                }
                                View view = vHolder.getView(R.id.viewshow);
                                if (position == GouMaiXiangQingActivity.this.dataList.size() - 1) {
                                    view.setVisibility(0);
                                } else {
                                    view.setVisibility(8);
                                }
                                try {
                                    if ("".equals(dataBean.getExpress_detail().getExpress_fee())) {
                                        ((TextView) vHolder.getView(R.id.price)).setText(CharacterParser.getSpannableColorSize(dataBean.getPrice() + "\n共" + dataBean.getQuantity() + "件", 1, dataBean.getPrice().split("\\.")[0].length(), SkinManager.getCurrentSkinType(GouMaiXiangQingActivity.this) == 1 ? "#7380A9" : "#333333"));
                                    } else {
                                        String str = dataBean.getPrice() + "\n" + dataBean.getExpress_detail().getExpress_fee() + "\n共" + dataBean.getQuantity() + "件";
                                        String str2 = dataBean.getPrice().split("\\.")[0];
                                        String express_fee = dataBean.getExpress_detail().getExpress_fee();
                                        int length = (dataBean.getPrice() + "\n").length();
                                        ((TextView) vHolder.getView(R.id.price)).setText(CharacterParser.getSpannableColorSize(str, 1, str2.length(), length, length + express_fee.length(), SkinManager.getCurrentSkinType(GouMaiXiangQingActivity.this) == 1 ? "#B2575C" : "#dd594a"));
                                    }
                                } catch (Exception unused) {
                                    ((TextView) vHolder.getView(R.id.price)).setText(dataBean.getPrice() + "\n共" + dataBean.getQuantity() + "件");
                                }
                                LinearLayout linearLayout = (LinearLayout) vHolder.getView(R.id.linr_button);
                                linearLayout.removeAllViews();
                                for (final int size = dataBean.getButton().size() - 1; size >= 0; size--) {
                                    View viewInflate = LayoutInflater.from(GouMaiXiangQingActivity.this.mContext).inflate(R.layout.activity_button, (ViewGroup) null);
                                    TextViewBorder textViewBorder = (TextViewBorder) viewInflate.findViewById(R.id.mTextBorder);
                                    textViewBorder.setText(dataBean.getButton().get(size).getTitle());
                                    if (SkinManager.getCurrentSkinType(GouMaiXiangQingActivity.this) == 1) {
                                        textViewBorder.setBorderColor(Color.parseColor("#2E3241"));
                                        textViewBorder.setTextColor(Color.parseColor("#7380A9"));
                                    } else {
                                        textViewBorder.setBorderColor(Color.parseColor(dataBean.getButton().get(size).getBorder_color()));
                                        textViewBorder.setTextColor(Color.parseColor(dataBean.getButton().get(size).getWord_color()));
                                    }
                                    textViewBorder.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.GouMaiXiangQingActivity.3.1.1
                                        @Override // android.view.View.OnClickListener
                                        public void onClick(View v2) {
                                            String type = dataBean.getButton().get(size).getType();
                                            type.hashCode();
                                            switch (type) {
                                                case "1":
                                                    if (dataBean.getCs() != null) {
                                                        CommonUtil.onlineService(GouMaiXiangQingActivity.this, dataBean.getCs());
                                                        break;
                                                    }
                                                    break;
                                                case "2":
                                                    try {
                                                        String express_query_url = dataBean.getExpress_query_url();
                                                        if (!TextUtils.isEmpty(express_query_url)) {
                                                            if (TextUtils.isEmpty(Uri.parse(express_query_url).getScheme())) {
                                                                express_query_url = DefaultWebClient.HTTP_SCHEME + express_query_url;
                                                            }
                                                            Intent intent = new Intent();
                                                            intent.setData(Uri.parse(express_query_url));
                                                            intent.setAction("android.intent.action.VIEW");
                                                            GouMaiXiangQingActivity.this.mContext.startActivity(intent);
                                                            break;
                                                        }
                                                    } catch (Exception e2) {
                                                        e2.printStackTrace();
                                                        ToastUtil.shortToast(GouMaiXiangQingActivity.this, "打开失败！");
                                                        return;
                                                    }
                                                    break;
                                                case "3":
                                                    Intent intent2 = new Intent(GouMaiXiangQingActivity.this.getApplicationContext(), (Class<?>) XiaoHongShuReplyActivity.class);
                                                    intent2.putExtra("goods_id", dataBean.getGoods_id());
                                                    intent2.putExtra("order_id", dataBean.getOrder_no());
                                                    intent2.putExtra("imgurl", dataBean.getGoods_thumbnail());
                                                    intent2.putExtra("order_time", dataBean.getCtime());
                                                    GouMaiXiangQingActivity.this.startActivity(intent2);
                                                    break;
                                                case "4":
                                                    NavigationUtilKt.goToChangeAddressActivity(GouMaiXiangQingActivity.this, dataBean.getExpress_detail(), dataBean.getOrder_no());
                                                    break;
                                            }
                                        }
                                    });
                                    linearLayout.addView(viewInflate);
                                }
                            }
                        };
                        GouMaiXiangQingActivity gouMaiXiangQingActivity2 = GouMaiXiangQingActivity.this;
                        gouMaiXiangQingActivity2.xqlist.setAdapter((ListAdapter) gouMaiXiangQingActivity2.adapter);
                        if (GouMaiXiangQingActivity.this.dataList == null || GouMaiXiangQingActivity.this.dataList.size() <= 0) {
                            GouMaiXiangQingActivity.this.noContnetimg.setVisibility(0);
                        } else {
                            GouMaiXiangQingActivity.this.noContnetimg.setVisibility(8);
                        }
                        GouMaiXiangQingActivity.this.adapter.notifyDataSetChanged();
                    } else {
                        gouMaiXiangQingActivity.xqlist.removeFooterView(gouMaiXiangQingActivity.addFooterView);
                        GouMaiXiangQingActivity.this.addFooterView.setVisibility(8);
                        List<GouMaiXiangQingBean.DataBean> data = GouMaiXiangQingActivity.this.mGouMaiXiangQingBean.getData();
                        if (data == null || data.size() <= 0) {
                            GouMaiXiangQingActivity.this.AlertToast("已经是最后一条");
                        } else {
                            GouMaiXiangQingActivity.this.dataList.addAll(data);
                            GouMaiXiangQingActivity.this.adapter.notifyDataSetChanged();
                        }
                    }
                    GouMaiXiangQingActivity.this.xqlist.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.x
                        @Override // android.widget.AdapterView.OnItemClickListener
                        public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                            this.f13668c.lambda$onSuccess$0(adapterView, view, i2, j2);
                        }
                    });
                }
                if (GouMaiXiangQingActivity.this.mSwipeRefreshLayout.isRefreshing()) {
                    GouMaiXiangQingActivity.this.mSwipeRefreshLayout.setRefreshing(false);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getDingdanData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        YJYHttpUtils.getgoodsmd5(this.mContext, NetworkRequestsURL.mOrderNewList, ajaxParams, new AnonymousClass3());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0() {
        this.mSwipeRefreshLayout.setRefreshing(true);
        this.page = 1;
        getDingdanData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.noContnetimg = (ImageView) findViewById(R.id.noContnetimg);
        this.xqlist = (ListView) findViewById(R.id.pinnedSectionListView1);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeLayput);
        this.mSwipeRefreshLayout = swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(this);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(this.mContext.getResources().getColor(R.color.white));
            this.mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light);
        } else {
            this.mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(this.mContext.getResources().getColor(R.color.input_night_color));
            this.mSwipeRefreshLayout.setColorSchemeResources(R.color.question_color_night, R.color.question_color_night, R.color.question_color_night, R.color.question_color_night);
        }
        this.mSwipeRefreshLayout.post(new Runnable() { // from class: com.psychiatrygarden.activity.purchase.activity.v
            @Override // java.lang.Runnable
            public final void run() {
                this.f13664c.lambda$init$0();
            }
        });
        this.addFooterView = getLayoutInflater().inflate(R.layout.activity_hideview, (ViewGroup) null);
        this.xqlist.setOnScrollListener(new AnonymousClass2());
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("REFRESH_Addr")) {
            this.mHandler.sendEmptyMessageDelayed(272, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        this.mHandler.sendEmptyMessageDelayed(272, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_goumaixiangqinglist);
        setTitle("我的订单");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

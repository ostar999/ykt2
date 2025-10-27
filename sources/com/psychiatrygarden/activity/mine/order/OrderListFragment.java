package com.psychiatrygarden.activity.mine.order;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alipay.sdk.app.PayTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.just.agentweb.DefaultWebClient;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.purchase.activity.ActSubmitGoodsComment;
import com.psychiatrygarden.activity.purchase.beans.CreateOrderBean;
import com.psychiatrygarden.activity.purchase.beans.GouMaiXiangQingBean;
import com.psychiatrygarden.activity.purchase.beans.SaleGoodsBean;
import com.psychiatrygarden.activity.purchase.util.PayResult;
import com.psychiatrygarden.activity.purchase.util.ResultCodeData;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.WXPayEvent;
import com.psychiatrygarden.event.WXPayStatus;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.NavigationUtilKt;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.utils.WeakHandler;
import com.psychiatrygarden.utils.pay.PayMethodKeyKt;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.DeleteDownloadDialog;
import com.psychiatrygarden.widget.PayMethodSelectPopupView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class OrderListFragment extends BaseFragment {
    private static final int SDK_PAY_FLAG = 1;
    private static final String TAG = "OrderListFragment";
    private CustomEmptyView emptyView;
    private WeakHandler handler;
    private ListOrderAdapter mAdapter;
    private CreateOrderBean mCreateOrderBean;
    private GouMaiXiangQingBean mGouMaiXiangQingBean;
    private WeakHandler mHandler;
    private RecyclerView recycler;
    public SmartRefreshLayout refresh;
    private final int MSG_WHAT = 100;
    private int page = 1;
    private List<GouMaiXiangQingBean.DataBean> dataList = new ArrayList();
    private int changeType = 0;
    private String mPayMethod = PayMethodKeyKt.ALi_PayMethod;

    /* renamed from: com.psychiatrygarden.activity.mine.order.OrderListFragment$5, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass5 {
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

    public class ListOrderAdapter extends BaseQuickAdapter<GouMaiXiangQingBean.DataBean, BaseViewHolder> {
        public ListOrderAdapter() {
            super(R.layout.item_order_list);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(GouMaiXiangQingBean.DataBean dataBean) {
            OrderListFragment.this.cancelOrder(dataBean.getOrder_no(), true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1(GouMaiXiangQingBean.DataBean dataBean) {
            OrderListFragment.this.delOrder(dataBean.getOrder_no());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$2(GouMaiXiangQingBean.DataBean dataBean, String str) {
            OrderListFragment.this.mPayMethod = str;
            Log.d(OrderListFragment.TAG, "clickBack: " + str);
            OrderListFragment.this.mCreateCourseOrder(dataBean.getOrder_no(), dataBean.getTotal_amount());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$3(final GouMaiXiangQingBean.DataBean dataBean, int i2, View view) {
            String type = dataBean.getButton().get(i2).getType();
            type.hashCode();
            switch (type) {
                case "1":
                    if (dataBean.getCs() != null) {
                        CommonUtil.onlineService(OrderListFragment.this.getActivity(), dataBean.getCs());
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
                            ((BaseFragment) OrderListFragment.this).mContext.startActivity(intent);
                            break;
                        }
                    } catch (Exception e2) {
                        Log.e("Error", e2.getMessage());
                        ToastUtil.shortToast(getContext(), "打开失败！");
                        return;
                    }
                    break;
                case "3":
                    NavigationUtilKt.goToActSubmitGoodsComment(getContext(), dataBean.getGoods_id(), dataBean.getOrder_no(), "5".equals(dataBean.getGoods_type()), false);
                    break;
                case "4":
                    NavigationUtilKt.goToChangeAddressActivity(OrderListFragment.this.getActivity(), dataBean.getExpress_detail(), dataBean.getOrder_no());
                    break;
                case "5":
                    new XPopup.Builder(getContext()).asCustom(new DeleteDownloadDialog(getContext(), new DeleteDownloadDialog.ClickIml() { // from class: com.psychiatrygarden.activity.mine.order.c0
                        @Override // com.psychiatrygarden.widget.DeleteDownloadDialog.ClickIml
                        public final void mClickIml() {
                            this.f12947a.lambda$convert$0(dataBean);
                        }
                    }, new SpannableStringBuilder("是否确定取消订单"), "温馨提示", "取消", "确定")).show();
                    break;
                case "6":
                    new XPopup.Builder(getContext()).asCustom(new DeleteDownloadDialog(getContext(), new DeleteDownloadDialog.ClickIml() { // from class: com.psychiatrygarden.activity.mine.order.d0
                        @Override // com.psychiatrygarden.widget.DeleteDownloadDialog.ClickIml
                        public final void mClickIml() {
                            this.f12950a.lambda$convert$1(dataBean);
                        }
                    }, new SpannableStringBuilder("是否确定删除订单"), "温馨提示", "取消", "确定")).show();
                    break;
                case "7":
                    if (OrderListFragment.this.getActivity() != null) {
                        PayMethodSelectPopupView payMethodSelectPopupView = new PayMethodSelectPopupView(OrderListFragment.this.getActivity(), dataBean.getPrice(), dataBean.getPending_payment_ctime());
                        payMethodSelectPopupView.setClickBack(new PayMethodSelectPopupView.ClickBack() { // from class: com.psychiatrygarden.activity.mine.order.e0
                            @Override // com.psychiatrygarden.widget.PayMethodSelectPopupView.ClickBack
                            public final void back(String str) {
                                this.f12953a.lambda$convert$2(dataBean, str);
                            }
                        });
                        XPopup.Builder builder = new XPopup.Builder(OrderListFragment.this.getActivity());
                        Boolean bool = Boolean.TRUE;
                        builder.dismissOnBackPressed(bool).enableDrag(false).dismissOnTouchOutside(bool).asCustom(payMethodSelectPopupView).show();
                        break;
                    }
                    break;
            }
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
        public /* bridge */ /* synthetic */ void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List payloads) {
            onBindViewHolder((BaseViewHolder) holder, position, (List<Object>) payloads);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        /* JADX WARN: Removed duplicated region for block: B:12:0x0057  */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void convert(@androidx.annotation.NonNull com.chad.library.adapter.base.viewholder.BaseViewHolder r19, final com.psychiatrygarden.activity.purchase.beans.GouMaiXiangQingBean.DataBean r20) throws java.lang.NumberFormatException {
            /*
                Method dump skipped, instructions count: 908
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.mine.order.OrderListFragment.ListOrderAdapter.convert(com.chad.library.adapter.base.viewholder.BaseViewHolder, com.psychiatrygarden.activity.purchase.beans.GouMaiXiangQingBean$DataBean):void");
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
            super.onBindViewHolder((ListOrderAdapter) holder, position, payloads);
        }
    }

    private void aLiPay(final String singSign) {
        new Thread(new Runnable() { // from class: com.psychiatrygarden.activity.mine.order.b0
            @Override // java.lang.Runnable
            public final void run() {
                this.f12944c.lambda$aLiPay$6(singSign);
            }
        }).start();
    }

    public static String getCountDownTimeStr(String orderTime, String endTimeStr) throws NumberFormatException {
        String str;
        String str2;
        if (!TextUtils.isEmpty(orderTime) && !TextUtils.isEmpty(endTimeStr)) {
            long j2 = Long.parseLong(endTimeStr) - Long.parseLong(orderTime);
            if (j2 > 0) {
                long j3 = j2 / 60;
                long j4 = j2 % 60;
                if (j3 < 10) {
                    str = "0" + j3;
                } else {
                    str = j3 + "";
                }
                if (j4 < 10) {
                    str2 = "0" + j4;
                } else {
                    str2 = j4 + "";
                }
                return str + ":" + str2;
            }
        }
        return "0";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getOrderListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        ajaxParams.put("change", this.changeType + "");
        YJYHttpUtils.getgoodsmd5(this.mContext, NetworkRequestsURL.mOrderNewList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.order.OrderListFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                OrderListFragment.this.refresh.finishRefresh();
                if (OrderListFragment.this.page == 1) {
                    OrderListFragment.this.emptyView.setLoadFileResUi(OrderListFragment.this.getContext());
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass1) t2);
                OrderListFragment.this.refresh.finishRefresh();
                if (OrderListFragment.this.page > 1) {
                    OrderListFragment.this.refresh.finishLoadMore(true);
                }
                try {
                    OrderListFragment.this.mGouMaiXiangQingBean = (GouMaiXiangQingBean) new Gson().fromJson(t2, GouMaiXiangQingBean.class);
                    if (!"200".equals(OrderListFragment.this.mGouMaiXiangQingBean.getCode())) {
                        OrderListFragment.this.emptyView.uploadEmptyViewResUi();
                        return;
                    }
                    if (OrderListFragment.this.page != 1) {
                        List<GouMaiXiangQingBean.DataBean> data = OrderListFragment.this.mGouMaiXiangQingBean.getData();
                        if (data != null && !data.isEmpty()) {
                            OrderListFragment.this.mAdapter.addData((Collection) data);
                            OrderListFragment.this.selectCancelOrder(data);
                            return;
                        }
                        OrderListFragment.this.refresh.setNoMoreData(true);
                        return;
                    }
                    OrderListFragment orderListFragment = OrderListFragment.this;
                    orderListFragment.dataList = orderListFragment.mGouMaiXiangQingBean.getData();
                    if (OrderListFragment.this.dataList != null && !OrderListFragment.this.dataList.isEmpty()) {
                        OrderListFragment.this.mAdapter.setList(OrderListFragment.this.dataList);
                        OrderListFragment orderListFragment2 = OrderListFragment.this;
                        orderListFragment2.selectCancelOrder(orderListFragment2.dataList);
                        return;
                    }
                    OrderListFragment.this.emptyView.uploadEmptyViewResUi();
                    OrderListFragment.this.mAdapter.setList(new ArrayList());
                } catch (Exception e2) {
                    Log.e("Error", e2.getMessage());
                    if (OrderListFragment.this.page == 1) {
                        OrderListFragment.this.emptyView.setLoadFileResUi(OrderListFragment.this.getContext());
                    }
                }
            }
        });
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
        Map<String, String> mapPayV2 = new PayTask(getActivity()).payV2(str, true);
        Message message = new Message();
        message.what = 1;
        message.obj = mapPayV2;
        this.mHandler.sendMessage(message);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        getOrderListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(RefreshLayout refreshLayout) {
        this.page = 1;
        getOrderListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(RefreshLayout refreshLayout) {
        this.page++;
        getOrderListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(Message message) {
        if (message.what == 100) {
            int i2 = this.changeType;
            if (i2 == 0 || i2 == 1) {
                updateTime();
                this.handler.sendEmptyMessageDelayed(100, 1000L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$4(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        OrderDetailActivity.INSTANCE.getToOrderDetail(this.mContext, this.mAdapter.getItem(i2).getOrder_no());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$5(Message message) {
        if (message.what == 1) {
            String resultStatus = new PayResult((Map) message.obj).getResultStatus();
            mShowPayCallBackDialog(new ResultCodeData().mCheckResultCode(resultStatus), resultStatus);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mCreateCourseOrder(String orderNo, String totalAmount) {
        ArrayList arrayList = new ArrayList();
        SaleGoodsBean saleGoodsBean = new SaleGoodsBean();
        saleGoodsBean.setOrder_no(orderNo);
        saleGoodsBean.setIdentity_id(SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, getActivity()));
        arrayList.add(saleGoodsBean);
        String json = new Gson().toJson(arrayList);
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("pay_type", this.mPayMethod);
        ajaxParams.put("goods_info", json);
        ajaxParams.put("total_amount", totalAmount);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mcreateOrderUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.order.OrderListFragment.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                OrderListFragment.this.AlertToast("服务器请求错误");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    OrderListFragment.this.mCreateOrderBean = (CreateOrderBean) new Gson().fromJson(s2, CreateOrderBean.class);
                    if (!OrderListFragment.this.mCreateOrderBean.getCode().equals("200")) {
                        NewToast.showLong(OrderListFragment.this.getActivity(), OrderListFragment.this.mCreateOrderBean.getMessage());
                    } else if (com.tencent.connect.common.Constants.VIA_SHARE_TYPE_INFO.equals(OrderListFragment.this.mCreateOrderBean.getData().getStatus())) {
                        EventBus.getDefault().post(EventBusConstant.EVENT_ORDER_SUCCESS_FINISH_CONFIRM_UI);
                    } else {
                        OrderListFragment orderListFragment = OrderListFragment.this;
                        orderListFragment.goToPay(orderListFragment.mCreateOrderBean.getData().getSign());
                    }
                } catch (Exception e2) {
                    Log.d(OrderListFragment.TAG, "onSuccess: " + e2.getMessage());
                }
            }
        });
    }

    private void mShowPayCallBackDialog(String message, final String resultCode) {
        if (TextUtils.equals(resultCode, PayMethodKeyKt.PAY_SUCCESS_CODE)) {
            EventBus.getDefault().post("BuySuccess");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectCancelOrder(List<GouMaiXiangQingBean.DataBean> dataList) throws NumberFormatException {
        if (dataList == null) {
            return;
        }
        for (int i2 = 0; i2 < dataList.size(); i2++) {
            GouMaiXiangQingBean.DataBean dataBean = dataList.get(i2);
            String status = dataBean.getStatus();
            String countDownTimeStr = getCountDownTimeStr((System.currentTimeMillis() / 1000) + "", dataBean.getPending_payment_ctime());
            if ("0".equals(status) && "0".equals(countDownTimeStr)) {
                cancelOrder(dataBean.getOrder_no(), false);
            }
        }
    }

    private void updateTime() {
        this.mAdapter.notifyDataSetChanged();
    }

    private void wxPay(CreateOrderBean.Sign sign) {
        if (getActivity() != null) {
            PayMethodKeyKt.wxPay(getActivity(), sign.getAppId(), sign.getPartnerId(), sign.getPrepayId(), sign.getPackageValue(), sign.getNonceStr(), sign.getTimeStamp(), sign.getSign());
        }
    }

    public void cancelOrder(String order_no, final boolean toastShow) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(ActSubmitGoodsComment.EXTRA_DATA_ORDER_NO, order_no);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.orderCancel, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.order.OrderListFragment.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                OrderListFragment.this.page = 1;
                OrderListFragment.this.getOrderListData();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass2) t2);
                try {
                    if ("200".equals(new JSONObject(t2).optString("code"))) {
                        if (toastShow) {
                            OrderListFragment.this.toastOnUiThread("取消订单成功");
                        }
                        EventBus.getDefault().post(EventBusConstant.CANCEL_ORDER);
                    }
                    OrderListFragment.this.page = 1;
                    OrderListFragment.this.getOrderListData();
                } catch (Exception e2) {
                    Log.d(OrderListFragment.TAG, "error: " + e2.getMessage());
                }
            }
        });
    }

    public void delOrder(String order_no) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(ActSubmitGoodsComment.EXTRA_DATA_ORDER_NO, order_no);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.orderDel, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.order.OrderListFragment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass3) t2);
                try {
                    if ("200".equals(new JSONObject(t2).optString("code"))) {
                        OrderListFragment.this.toastOnUiThread("删除订单成功");
                        OrderListFragment.this.page = 1;
                        OrderListFragment.this.getOrderListData();
                    }
                } catch (Exception e2) {
                    Log.d(OrderListFragment.TAG, "error: " + e2.getMessage());
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_order_list;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.changeType = arguments.getInt("type");
        }
        this.recycler = (RecyclerView) holder.get(R.id.recycler);
        this.refresh = (SmartRefreshLayout) holder.get(R.id.refresh);
        this.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mAdapter = new ListOrderAdapter();
        CustomEmptyView customEmptyView = new CustomEmptyView(getContext(), 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.order.v
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12987c.lambda$initViews$0(view);
            }
        });
        this.mAdapter.setEmptyView(this.emptyView);
        this.recycler.setAdapter(this.mAdapter);
        this.refresh.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.mine.order.w
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f12988c.lambda$initViews$1(refreshLayout);
            }
        });
        this.refresh.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.mine.order.x
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f12989c.lambda$initViews$2(refreshLayout);
            }
        });
        FragmentActivity activity = getActivity();
        if (activity != null) {
            WeakHandler weakHandler = new WeakHandler(activity, new WeakHandler.HandlerCallback() { // from class: com.psychiatrygarden.activity.mine.order.y
                @Override // com.psychiatrygarden.utils.WeakHandler.HandlerCallback
                public final void handlerMessage(Message message) {
                    this.f12990a.lambda$initViews$3(message);
                }
            });
            this.handler = weakHandler;
            weakHandler.sendEmptyMessageDelayed(100, 1000L);
        }
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.mine.order.z
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12991c.lambda$initViews$4(baseQuickAdapter, view, i2);
            }
        });
        getOrderListData();
        if (getActivity() != null) {
            this.mHandler = new WeakHandler(getActivity(), new WeakHandler.HandlerCallback() { // from class: com.psychiatrygarden.activity.mine.order.a0
                @Override // com.psychiatrygarden.utils.WeakHandler.HandlerCallback
                public final void handlerMessage(Message message) {
                    this.f12942a.lambda$initViews$5(message);
                }
            });
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        WeakHandler weakHandler = this.handler;
        if (weakHandler != null) {
            weakHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        super.onEventMainThread(str);
        if (EventBusConstant.COURSE_ORDER_COMMENT.equals(str) || "BuySuccess".equals(str)) {
            getOrderListData();
        }
        if (EventBusConstant.CANCEL_ORDER.equals(str) && this.changeType == 1) {
            getOrderListData();
        }
        if (OrderDetailActivityKt.UPDATE_LIST.equals(str)) {
            int i2 = this.changeType;
            if (i2 == 1 || i2 == 0) {
                getOrderListData();
            }
        }
    }

    @Subscribe
    public void onEventMainThread(WXPayEvent event) {
        OrderListActivity orderListActivity = (OrderListActivity) getActivity();
        if (orderListActivity != null) {
            if (this.changeType == orderListActivity.getCurrentItemIndex()) {
                int i2 = AnonymousClass5.$SwitchMap$com$psychiatrygarden$event$WXPayStatus[event.getStatus().ordinal()];
                if (i2 == 1) {
                    mShowPayCallBackDialog("用户中途取消", "0");
                } else if (i2 == 2) {
                    mShowPayCallBackDialog("支付失败", "0");
                } else {
                    if (i2 != 3) {
                        return;
                    }
                    mShowPayCallBackDialog("支付成功", PayMethodKeyKt.PAY_SUCCESS_CODE);
                }
            }
        }
    }
}

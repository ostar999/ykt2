package com.psychiatrygarden.activity.mine.order;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alipay.sdk.app.PayTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.easefun.polyv.livecommon.ui.widget.expandmenu.utils.DpOrPxUtils;
import com.google.gson.Gson;
import com.just.agentweb.DefaultWebClient;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.purchase.activity.ActSubmitGoodsComment;
import com.psychiatrygarden.activity.purchase.beans.CreateOrderBean;
import com.psychiatrygarden.activity.purchase.beans.SaleGoodsBean;
import com.psychiatrygarden.activity.purchase.beans.ShowAddressBean;
import com.psychiatrygarden.activity.purchase.util.PayResult;
import com.psychiatrygarden.activity.purchase.util.ResultCodeData;
import com.psychiatrygarden.bean.ButtonBean;
import com.psychiatrygarden.bean.DataAddress;
import com.psychiatrygarden.bean.OrderDetailBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.WXPayEvent;
import com.psychiatrygarden.event.WXPayStatus;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.NavigationUtilKt;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.utils.WeakHandler;
import com.psychiatrygarden.utils.pay.PayMethodKeyKt;
import com.psychiatrygarden.widget.DeleteDownloadDialog;
import com.psychiatrygarden.widget.PayMethodSelectPopupView;
import com.psychiatrygarden.widget.TextViewBorder;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import com.ykb.ebook.util.ToastUtilsKt;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 `2\u00020\u00012\u00020\u0002:\u0002`aB\u0005¢\u0006\u0002\u0010\u0003J\u0010\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\u0018H\u0002J\u0010\u00105\u001a\u0002032\u0006\u00106\u001a\u00020\u0018H\u0002J\u001c\u00107\u001a\u00020\u00182\b\u00108\u001a\u0004\u0018\u00010\u00182\b\u00109\u001a\u0004\u0018\u00010\u0018H\u0002J\u0010\u0010:\u001a\u0002032\u0006\u00106\u001a\u00020\u0018H\u0002J \u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\u00182\u0006\u0010>\u001a\u00020\u00052\u0006\u0010?\u001a\u00020\u0005H\u0002J\u0010\u0010@\u001a\u00020<2\u0006\u0010=\u001a\u00020\u0018H\u0002J\u0010\u0010A\u001a\u0002032\u0006\u0010B\u001a\u00020CH\u0002J\b\u0010D\u001a\u000203H\u0016J\u0010\u0010E\u001a\u0002032\u0006\u0010F\u001a\u00020\u001bH\u0002J\u0010\u0010G\u001a\u0002032\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\b\u0010H\u001a\u00020IH\u0002J\u0018\u0010J\u001a\u0002032\u0006\u00106\u001a\u00020\u00182\u0006\u0010K\u001a\u00020\u0018H\u0002J\u0018\u0010L\u001a\u0002032\u0006\u0010M\u001a\u00020\u00182\u0006\u0010N\u001a\u00020\u0018H\u0002J\u0010\u0010O\u001a\u0002032\u0006\u0010P\u001a\u00020QH\u0016J\u0012\u0010R\u001a\u0002032\b\u0010S\u001a\u0004\u0018\u00010TH\u0014J\b\u0010U\u001a\u000203H\u0014J\u0010\u0010V\u001a\u0002032\u0006\u0010W\u001a\u00020XH\u0007J\u0012\u0010V\u001a\u0002032\b\u0010Y\u001a\u0004\u0018\u00010\u0018H\u0016J\b\u0010Z\u001a\u000203H\u0014J\u0010\u0010[\u001a\u0002032\u0006\u00106\u001a\u00020\u0018H\u0002J\b\u0010\\\u001a\u000203H\u0016J\b\u0010]\u001a\u000203H\u0016J\u0012\u0010^\u001a\u0002032\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0002J\u0010\u0010_\u001a\u0002032\u0006\u0010B\u001a\u00020CH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000¨\u0006b"}, d2 = {"Lcom/psychiatrygarden/activity/mine/order/OrderDetailActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "Landroid/view/View$OnClickListener;", "()V", "MSG_WHAT", "", "SDK_PAY_FLAG", "adapter", "Lcom/psychiatrygarden/activity/mine/order/OrderDetailActivity$OrderAddressAdapter;", "handler", "Lcom/psychiatrygarden/utils/WeakHandler;", "imgBack", "Landroid/widget/ImageView;", "imgClose", "imgStatus", "layoutButton", "Landroid/widget/LinearLayout;", "layoutCoupon", "Landroid/widget/RelativeLayout;", "layoutFare", "layoutMsg", "layoutPriceCount", "layoutSale", "mPayMethod", "", "orderConfirmImg", "orderDetailData", "Lcom/psychiatrygarden/bean/OrderDetailBean;", "orderNO", "recyclerViewAddress", "Landroidx/recyclerview/widget/RecyclerView;", "tvOrderConfirmCoupon", "Landroid/widget/TextView;", "tvOrderConfirmDesc", "tvOrderConfirmFare", "tvOrderConfirmLeaveMessage", "tvOrderConfirmNum", "tvOrderConfirmPrice", "tvOrderConfirmPrice1", "tvOrderConfirmRedPacket", "tvOrderConfirmSale", "tvOrderConfirmTitle", "tvOrderNo", "tvOrderPayStyle", "tvOrderPayTime", "tvOrderTime", "tvPayDecrease", "tvPayNum", "tvTitle", "tvWaitTime", "aLiPay", "", "singSign", EventBusConstant.CANCEL_ORDER, "orderNo", "getCountDownTimeStr", "orderTime", "endTimeStr", "getOrderDetail", "getTextSpannable", "Landroid/text/SpannableStringBuilder;", "value", "begin", "end", "getTextWaitPaySpannable", "goToPay", "sign", "Lcom/psychiatrygarden/activity/purchase/beans/CreateOrderBean$Sign;", "init", "initButton", "dataBean", "initOrderDetail", "isNight", "", "mCreateCourseOrder", "totalAmount", "mShowPayCallbackDialog", "message", "resultCode", "onClick", "v", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onEventMainThread", NotificationCompat.CATEGORY_EVENT, "Lcom/psychiatrygarden/event/WXPayEvent;", "str", "onRestart", "orderDel", "setContentView", "setListenerForWidget", "updateTime", "wxPay", "Companion", "OrderAddressAdapter", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class OrderDetailActivity extends BaseActivity implements View.OnClickListener {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private OrderAddressAdapter adapter;
    private WeakHandler handler;
    private ImageView imgBack;
    private ImageView imgClose;
    private ImageView imgStatus;
    private LinearLayout layoutButton;
    private RelativeLayout layoutCoupon;
    private RelativeLayout layoutFare;
    private LinearLayout layoutMsg;
    private RelativeLayout layoutPriceCount;
    private RelativeLayout layoutSale;
    private ImageView orderConfirmImg;

    @Nullable
    private OrderDetailBean orderDetailData;
    private RecyclerView recyclerViewAddress;
    private TextView tvOrderConfirmCoupon;
    private TextView tvOrderConfirmDesc;
    private TextView tvOrderConfirmFare;
    private TextView tvOrderConfirmLeaveMessage;
    private TextView tvOrderConfirmNum;
    private TextView tvOrderConfirmPrice;
    private TextView tvOrderConfirmPrice1;
    private TextView tvOrderConfirmRedPacket;
    private TextView tvOrderConfirmSale;
    private TextView tvOrderConfirmTitle;
    private TextView tvOrderNo;
    private TextView tvOrderPayStyle;
    private TextView tvOrderPayTime;
    private TextView tvOrderTime;
    private TextView tvPayDecrease;
    private TextView tvPayNum;
    private TextView tvTitle;
    private TextView tvWaitTime;
    private final int MSG_WHAT = 1000;

    @NotNull
    private String mPayMethod = PayMethodKeyKt.ALi_PayMethod;
    private final int SDK_PAY_FLAG = 11;

    @Nullable
    private String orderNO = "";

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0016\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\n"}, d2 = {"Lcom/psychiatrygarden/activity/mine/order/OrderDetailActivity$Companion;", "", "()V", "getToOrderDetail", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "orderNo", "", "getToOrderDetailNewTask", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void getToOrderDetail(@NotNull Context context, @NotNull String orderNo) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(orderNo, "orderNo");
            Intent intent = new Intent(context, (Class<?>) OrderDetailActivity.class);
            intent.putExtra("orderNo", orderNo);
            context.startActivity(intent);
        }

        public final void getToOrderDetailNewTask(@NotNull Context context, @NotNull String orderNo) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(orderNo, "orderNo");
            Intent intent = new Intent(context, (Class<?>) OrderDetailActivity.class);
            intent.putExtra("orderNo", orderNo);
            context.startActivity(intent);
        }
    }

    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0002H\u0014¨\u0006\t"}, d2 = {"Lcom/psychiatrygarden/activity/mine/order/OrderDetailActivity$OrderAddressAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/DataAddress;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "()V", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class OrderAddressAdapter extends BaseQuickAdapter<DataAddress, BaseViewHolder> {
        public OrderAddressAdapter() {
            super(R.layout.item_order_list_address, null, 2, null);
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder holder, @NotNull DataAddress item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(item, "item");
            TextView textView = (TextView) holder.getView(R.id.tvOrderStyle);
            TextView textView2 = (TextView) holder.getView(R.id.tvOrderPhone);
            TextView textView3 = (TextView) holder.getView(R.id.tvOrderAddress);
            textView.setText(item.getExpress_name());
            textView2.setText(item.getExpress_user_name() + ' ' + item.getExpress_phone());
            textView3.setText(item.getExpress_address());
            textView3.setText(item.getExpress_address());
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[WXPayStatus.values().length];
            try {
                iArr[WXPayStatus.CANCEL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[WXPayStatus.FAILED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[WXPayStatus.SUCCESS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private final void aLiPay(final String singSign) {
        new Thread(new Runnable() { // from class: com.psychiatrygarden.activity.mine.order.p
            @Override // java.lang.Runnable
            public final void run() {
                OrderDetailActivity.aLiPay$lambda$16(this.f12977c, singSign);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void aLiPay$lambda$16(OrderDetailActivity this$0, String orderInfo) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(orderInfo, "$orderInfo");
        Map<String, String> mapPayV2 = new PayTask(this$0).payV2(orderInfo, true);
        Message message = new Message();
        message.what = this$0.SDK_PAY_FLAG;
        message.obj = mapPayV2;
        WeakHandler weakHandler = this$0.handler;
        if (weakHandler == null) {
            Intrinsics.throwUninitializedPropertyAccessException("handler");
            weakHandler = null;
        }
        weakHandler.sendMessage(message);
    }

    private final void cancelOrder(String orderNo) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(ActSubmitGoodsComment.EXTRA_DATA_ORDER_NO, orderNo);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.orderCancel, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.order.OrderDetailActivity.cancelOrder.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
                ToastUtilsKt.toastOnUi$default(OrderDetailActivity.this, strMsg, 0, 2, (Object) null);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@Nullable String t2) {
                super.onSuccess((AnonymousClass1) t2);
                if (t2 != null) {
                    try {
                        OrderDetailActivity orderDetailActivity = OrderDetailActivity.this;
                        JSONObject jSONObject = new JSONObject(t2);
                        if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                            ToastUtilsKt.toastOnUi$default(orderDetailActivity, "订单取消成功", 0, 2, (Object) null);
                            String str = orderDetailActivity.orderNO;
                            Intrinsics.checkNotNull(str);
                            orderDetailActivity.getOrderDetail(str);
                            EventBus.getDefault().post(OrderDetailActivityKt.UPDATE_LIST);
                        } else {
                            ToastUtilsKt.toastOnUi$default(orderDetailActivity, jSONObject.optString("message"), 0, 2, (Object) null);
                        }
                    } catch (Exception e2) {
                        String message = e2.getMessage();
                        Intrinsics.checkNotNull(message);
                        Log.e("Error: Line-343:", message);
                    }
                }
            }
        });
    }

    private final String getCountDownTimeStr(String orderTime, String endTimeStr) throws NumberFormatException {
        String string;
        String string2;
        if (TextUtils.isEmpty(orderTime) || TextUtils.isEmpty(endTimeStr)) {
            return "0:00";
        }
        Intrinsics.checkNotNull(orderTime);
        long j2 = Long.parseLong(orderTime);
        Intrinsics.checkNotNull(endTimeStr);
        long j3 = Long.parseLong(endTimeStr) - j2;
        if (j3 <= 0) {
            return "0:00";
        }
        long j4 = 60;
        long j5 = j3 / j4;
        long j6 = j3 % j4;
        if (j5 < 10) {
            StringBuilder sb = new StringBuilder();
            sb.append('0');
            sb.append(j5);
            string = sb.toString();
        } else {
            string = j5 + "";
        }
        if (j6 < 10) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append('0');
            sb2.append(j6);
            string2 = sb2.toString();
        } else {
            string2 = j6 + "";
        }
        return string + ':' + string2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void getOrderDetail(String orderNo) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(ActSubmitGoodsComment.EXTRA_DATA_ORDER_NO, orderNo);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.orderDetail, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.order.OrderDetailActivity.getOrderDetail.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
                ToastUtilsKt.toastOnUi$default(OrderDetailActivity.this, strMsg, 0, 2, (Object) null);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((C05981) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    String strOptString = jSONObject.optString("data");
                    if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        OrderDetailBean dateBeanList = (OrderDetailBean) new Gson().fromJson(strOptString, OrderDetailBean.class);
                        OrderDetailActivity orderDetailActivity = OrderDetailActivity.this;
                        Intrinsics.checkNotNullExpressionValue(dateBeanList, "dateBeanList");
                        orderDetailActivity.initOrderDetail(dateBeanList);
                    } else {
                        ToastUtilsKt.toastOnUi$default(OrderDetailActivity.this, jSONObject.optString("message"), 0, 2, (Object) null);
                    }
                } catch (Exception e2) {
                    String message = e2.getMessage();
                    Intrinsics.checkNotNull(message);
                    Log.e("Error: Line-343:", message);
                }
            }
        });
    }

    private final SpannableStringBuilder getTextSpannable(String value, int begin, int end) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(value);
        spannableStringBuilder.setSpan(new StyleSpan(1), begin, end, 17);
        return spannableStringBuilder;
    }

    private final SpannableStringBuilder getTextWaitPaySpannable(String value) {
        String str = "还剩" + value + "订单将自动取消";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        int iIndexOf$default = StringsKt__StringsKt.indexOf$default((CharSequence) str, value, 0, false, 6, (Object) null);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(getColor(SkinManager.getCurrentSkinType(this) == 1 ? R.color.main_theme_color_night : R.color.main_theme_color)), iIndexOf$default, value.length() + iIndexOf$default, 17);
        return spannableStringBuilder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void goToPay(CreateOrderBean.Sign sign) {
        if (Intrinsics.areEqual(this.mPayMethod, "wechat")) {
            wxPay(sign);
            return;
        }
        String sign2 = sign.getSign();
        Intrinsics.checkNotNullExpressionValue(sign2, "sign.sign");
        aLiPay(sign2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(BaseQuickAdapter adapter, View view, int i2) {
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        Intrinsics.checkNotNullParameter(view, "view");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$1(OrderDetailActivity this$0, Message msg) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(msg, "msg");
        int i2 = msg.what;
        if (i2 == this$0.MSG_WHAT) {
            this$0.updateTime(this$0.orderDetailData);
            WeakHandler weakHandler = this$0.handler;
            if (weakHandler == null) {
                Intrinsics.throwUninitializedPropertyAccessException("handler");
                weakHandler = null;
            }
            weakHandler.sendEmptyMessageDelayed(this$0.MSG_WHAT, 1000L);
            return;
        }
        if (i2 == this$0.SDK_PAY_FLAG) {
            try {
                Object obj = msg.obj;
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.Map<kotlin.String, kotlin.String>");
                String resultStatus = new PayResult((Map) obj).getResultStatus();
                String strMCheckResultCode = new ResultCodeData().mCheckResultCode(resultStatus);
                Intrinsics.checkNotNullExpressionValue(strMCheckResultCode, "ResultCodeData().mCheckResultCode(resultStatus)");
                Intrinsics.checkNotNullExpressionValue(resultStatus, "resultStatus");
                this$0.mShowPayCallbackDialog(strMCheckResultCode, resultStatus);
            } catch (Exception e2) {
                Log.d(this$0.TAG, "ErrorTag: init: " + e2.getMessage());
            }
        }
    }

    private final void initButton(final OrderDetailBean dataBean) {
        final int size;
        String title;
        LinearLayout linearLayout = this.layoutButton;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutButton");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        final List<ButtonBean> button = dataBean.getButton();
        if (button == null || button.size() - 1 < 0) {
            return;
        }
        while (true) {
            int i2 = size - 1;
            View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.activity_button, (ViewGroup) null);
            View viewFindViewById = viewInflate.findViewById(R.id.mTextBorder);
            Intrinsics.checkNotNull(viewFindViewById, "null cannot be cast to non-null type com.psychiatrygarden.widget.TextViewBorder");
            TextViewBorder textViewBorder = (TextViewBorder) viewFindViewById;
            String title2 = button.get(size).getTitle();
            Integer numValueOf = title2 != null ? Integer.valueOf(title2.length()) : null;
            Intrinsics.checkNotNull(numValueOf);
            if (numValueOf.intValue() > 3) {
                title = button.get(size).getTitle();
            } else {
                title = ' ' + button.get(size).getTitle() + ' ';
            }
            textViewBorder.setText(title);
            float fDip2px = DpOrPxUtils.dip2px(this.mContext, 8.0f);
            if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
                textViewBorder.setBorderColorAndRadius(Color.parseColor("#2E3241"), fDip2px);
                textViewBorder.setTextColor(Color.parseColor("#7380A9"));
            } else {
                textViewBorder.setBorderColorAndRadius(Color.parseColor(button.get(size).getBorder_color()), fDip2px);
                textViewBorder.setTextColor(Color.parseColor(button.get(size).getWord_color()));
            }
            textViewBorder.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.order.s
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    OrderDetailActivity.initButton$lambda$10$lambda$9(button, size, dataBean, this, view);
                }
            });
            LinearLayout linearLayout2 = this.layoutButton;
            if (linearLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutButton");
                linearLayout2 = null;
            }
            linearLayout2.addView(viewInflate);
            if (i2 < 0) {
                return;
            } else {
                size = i2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final void initButton$lambda$10$lambda$9(List buttons, int i2, final OrderDetailBean dataBean, final OrderDetailActivity this$0, View view) {
        String express_query_url;
        OrderDetailBean orderDetailBean;
        Intrinsics.checkNotNullParameter(buttons, "$buttons");
        Intrinsics.checkNotNullParameter(dataBean, "$dataBean");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String type = ((ButtonBean) buttons.get(i2)).getType();
        if (type != null) {
            switch (type.hashCode()) {
                case 49:
                    if (type.equals("1")) {
                        CommonUtil.onlineService(this$0, dataBean.getCs());
                        break;
                    }
                    break;
                case 50:
                    if (type.equals("2") && (express_query_url = dataBean.getExpress_query_url()) != null) {
                        try {
                            if (!TextUtils.isEmpty(express_query_url)) {
                                if (TextUtils.isEmpty(Uri.parse(express_query_url).getScheme())) {
                                    express_query_url = DefaultWebClient.HTTP_SCHEME + express_query_url;
                                }
                                Intent intent = new Intent();
                                intent.setData(Uri.parse(express_query_url));
                                intent.setAction("android.intent.action.VIEW");
                                this$0.mContext.startActivity(intent);
                                break;
                            }
                        } catch (Exception e2) {
                            String message = e2.getMessage();
                            Intrinsics.checkNotNull(message);
                            Log.e("Error", message);
                            ToastUtil.shortToast(this$0, "打开失败！");
                            return;
                        }
                    }
                    break;
                case 51:
                    if (type.equals("3")) {
                        NavigationUtilKt.goToActSubmitGoodsComment$default(this$0, dataBean.getGoods_id(), dataBean.getOrder_no(), Intrinsics.areEqual("5", dataBean.getGoods_type()), false, 16, null);
                        break;
                    }
                    break;
                case 52:
                    if (type.equals("4")) {
                        NavigationUtilKt.goToChangeAddressActivity(this$0, dataBean.getExpress_detail(), dataBean.getOrder_no());
                        break;
                    }
                    break;
                case 53:
                    if (type.equals("5")) {
                        new XPopup.Builder(this$0).asCustom(new DeleteDownloadDialog(this$0, new DeleteDownloadDialog.ClickIml() { // from class: com.psychiatrygarden.activity.mine.order.m
                            @Override // com.psychiatrygarden.widget.DeleteDownloadDialog.ClickIml
                            public final void mClickIml() {
                                OrderDetailActivity.initButton$lambda$10$lambda$9$lambda$4(dataBean, this$0);
                            }
                        }, new SpannableStringBuilder("是否确定取消订单?"), "温馨提示", "取消", "确定")).show();
                        break;
                    }
                    break;
                case 54:
                    if (type.equals(Constants.VIA_SHARE_TYPE_INFO)) {
                        new XPopup.Builder(this$0).asCustom(new DeleteDownloadDialog(this$0, new DeleteDownloadDialog.ClickIml() { // from class: com.psychiatrygarden.activity.mine.order.n
                            @Override // com.psychiatrygarden.widget.DeleteDownloadDialog.ClickIml
                            public final void mClickIml() {
                                OrderDetailActivity.initButton$lambda$10$lambda$9$lambda$6(dataBean, this$0);
                            }
                        }, new SpannableStringBuilder("是否确定删除订单?"), "温馨提示", "取消", "确定")).show();
                        break;
                    }
                    break;
                case 55:
                    if (type.equals("7") && (orderDetailBean = this$0.orderDetailData) != null) {
                        String total_amount_yuan = orderDetailBean.getTotal_amount_yuan();
                        String strReplace$default = total_amount_yuan != null ? StringsKt__StringsJVMKt.replace$default(total_amount_yuan, "¥", "", false, 4, (Object) null) : null;
                        OrderDetailBean orderDetailBean2 = this$0.orderDetailData;
                        Intrinsics.checkNotNull(orderDetailBean2);
                        PayMethodSelectPopupView payMethodSelectPopupView = new PayMethodSelectPopupView(this$0, strReplace$default, orderDetailBean2.getPending_payment_ctime());
                        payMethodSelectPopupView.setClickBack(new PayMethodSelectPopupView.ClickBack() { // from class: com.psychiatrygarden.activity.mine.order.o
                            @Override // com.psychiatrygarden.widget.PayMethodSelectPopupView.ClickBack
                            public final void back(String str) {
                                OrderDetailActivity.initButton$lambda$10$lambda$9$lambda$8$lambda$7(this.f12975a, dataBean, str);
                            }
                        });
                        XPopup.Builder builder = new XPopup.Builder(this$0);
                        Boolean bool = Boolean.TRUE;
                        builder.dismissOnBackPressed(bool).enableDrag(false).dismissOnTouchOutside(bool).asCustom(payMethodSelectPopupView).show();
                        break;
                    }
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initButton$lambda$10$lambda$9$lambda$4(OrderDetailBean dataBean, OrderDetailActivity this$0) {
        Intrinsics.checkNotNullParameter(dataBean, "$dataBean");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String order_no = dataBean.getOrder_no();
        if (order_no != null) {
            this$0.cancelOrder(order_no);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initButton$lambda$10$lambda$9$lambda$6(OrderDetailBean dataBean, OrderDetailActivity this$0) {
        Intrinsics.checkNotNullParameter(dataBean, "$dataBean");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String order_no = dataBean.getOrder_no();
        if (order_no != null) {
            this$0.orderDel(order_no);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initButton$lambda$10$lambda$9$lambda$8$lambda$7(OrderDetailActivity this$0, OrderDetailBean dataBean, String payMethod) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(dataBean, "$dataBean");
        Intrinsics.checkNotNullParameter(payMethod, "payMethod");
        this$0.mPayMethod = payMethod;
        String order_no = dataBean.getOrder_no();
        if (order_no == null || order_no.length() == 0) {
            return;
        }
        String total_amount = dataBean.getTotal_amount();
        if (total_amount == null || total_amount.length() == 0) {
            return;
        }
        this$0.mCreateCourseOrder(dataBean.getOrder_no(), dataBean.getTotal_amount());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initOrderDetail(OrderDetailBean orderDetailData) {
        String str;
        String str2;
        String goods_thumbnail = orderDetailData.getGoods_thumbnail();
        TextView textView = null;
        WeakHandler weakHandler = null;
        TextView textView2 = null;
        if (goods_thumbnail != null) {
            ImageView imageView = this.orderConfirmImg;
            if (imageView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("orderConfirmImg");
                imageView = null;
            }
            GlideUtils.loadImage(this, goods_thumbnail, imageView, R.mipmap.ic_order_default, R.mipmap.ic_order_default);
        }
        this.orderDetailData = orderDetailData;
        TextView textView3 = this.tvOrderConfirmTitle;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvOrderConfirmTitle");
            textView3 = null;
        }
        textView3.setText(orderDetailData.getGoods_name());
        TextView textView4 = this.tvOrderConfirmDesc;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvOrderConfirmDesc");
            textView4 = null;
        }
        textView4.setText(orderDetailData.getGoods_description());
        TextView textView5 = this.tvOrderConfirmNum;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvOrderConfirmNum");
            textView5 = null;
        }
        textView5.setText("数量x" + orderDetailData.getQuantity());
        TextView textView6 = this.tvOrderConfirmPrice;
        if (textView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvOrderConfirmPrice");
            textView6 = null;
        }
        textView6.setText(String.valueOf(orderDetailData.getOriginal_price_yuan()));
        String str3 = "总优惠" + orderDetailData.getTotal_discount_yuan();
        TextView textView7 = this.tvPayDecrease;
        if (textView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvPayDecrease");
            textView7 = null;
        }
        textView7.setText(getTextSpannable(str3, 3, str3.length()));
        String str4 = "合计 " + orderDetailData.getTotal_amount_yuan();
        TextView textView8 = this.tvPayNum;
        if (textView8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvPayNum");
            textView8 = null;
        }
        textView8.setText(getTextSpannable(str4, 2, str4.length()));
        TextView textView9 = this.tvOrderConfirmPrice1;
        if (textView9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvOrderConfirmPrice1");
            textView9 = null;
        }
        textView9.setText(orderDetailData.getOriginal_total_price_yuan());
        TextView textView10 = this.tvOrderConfirmFare;
        if (textView10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvOrderConfirmFare");
            textView10 = null;
        }
        textView10.setText(orderDetailData.getExpress_fee_yuan());
        TextView textView11 = this.tvOrderConfirmSale;
        if (textView11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvOrderConfirmSale");
            textView11 = null;
        }
        String str5 = "减¥0";
        if (TextUtils.isEmpty(orderDetailData.getPromotion_yuan())) {
            str = "减¥0";
        } else {
            str = (char) 20943 + orderDetailData.getPromotion_yuan();
        }
        textView11.setText(str);
        TextView textView12 = this.tvOrderConfirmCoupon;
        if (textView12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvOrderConfirmCoupon");
            textView12 = null;
        }
        if (TextUtils.isEmpty(orderDetailData.getCoupon_price_yuan())) {
            str2 = "减¥0";
        } else {
            str2 = (char) 20943 + orderDetailData.getCoupon_price_yuan();
        }
        textView12.setText(str2);
        TextView textView13 = this.tvOrderConfirmRedPacket;
        if (textView13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvOrderConfirmRedPacket");
            textView13 = null;
        }
        if (!TextUtils.isEmpty(orderDetailData.getRed_packet_price_yuan())) {
            str5 = (char) 20943 + orderDetailData.getRed_packet_price_yuan();
        }
        textView13.setText(str5);
        TextView textView14 = this.tvOrderNo;
        if (textView14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvOrderNo");
            textView14 = null;
        }
        textView14.setText(orderDetailData.getOrder_no());
        TextView textView15 = this.tvOrderPayStyle;
        if (textView15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvOrderPayStyle");
            textView15 = null;
        }
        textView15.setText(Intrinsics.areEqual(PayMethodKeyKt.ALi_PayMethod, orderDetailData.getPay_type()) ? "支付宝" : "微信");
        TextView textView16 = this.tvOrderTime;
        if (textView16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvOrderTime");
            textView16 = null;
        }
        textView16.setText(orderDetailData.getCtime());
        TextView textView17 = this.tvOrderPayTime;
        if (textView17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvOrderPayTime");
            textView17 = null;
        }
        textView17.setText(orderDetailData.getPay_notify_time());
        TextView textView18 = this.tvOrderConfirmLeaveMessage;
        if (textView18 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvOrderConfirmLeaveMessage");
            textView18 = null;
        }
        textView18.setText(TextUtils.isEmpty(orderDetailData.getLeave_message()) ? "无" : orderDetailData.getLeave_message());
        if (TextUtils.isEmpty(orderDetailData.getExpress_id()) || Intrinsics.areEqual("0", orderDetailData.getExpress_id())) {
            RecyclerView recyclerView = this.recyclerViewAddress;
            if (recyclerView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("recyclerViewAddress");
                recyclerView = null;
            }
            recyclerView.setVisibility(8);
        } else {
            RecyclerView recyclerView2 = this.recyclerViewAddress;
            if (recyclerView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("recyclerViewAddress");
                recyclerView2 = null;
            }
            recyclerView2.setVisibility(0);
            String express_name = orderDetailData.getExpress_name();
            ShowAddressBean.DataBean express_detail = orderDetailData.getExpress_detail();
            String name = express_detail != null ? express_detail.getName() : null;
            ShowAddressBean.DataBean express_detail2 = orderDetailData.getExpress_detail();
            String mobile = express_detail2 != null ? express_detail2.getMobile() : null;
            ShowAddressBean.DataBean express_detail3 = orderDetailData.getExpress_detail();
            DataAddress dataAddress = new DataAddress(express_name, name, mobile, express_detail3 != null ? express_detail3.getFull_address() : null);
            OrderAddressAdapter orderAddressAdapter = this.adapter;
            if (orderAddressAdapter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
                orderAddressAdapter = null;
            }
            orderAddressAdapter.setList(CollectionsKt__CollectionsJVMKt.listOf(dataAddress));
        }
        String status = orderDetailData.getStatus();
        if (Intrinsics.areEqual("0", status)) {
            ImageView imageView2 = this.imgStatus;
            if (imageView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imgStatus");
                imageView2 = null;
            }
            imageView2.setImageResource(isNight() ? R.drawable.icon_order_wait_pay_night : R.drawable.icon_order_wait_pay_day);
            TextView textView19 = this.tvTitle;
            if (textView19 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
                textView19 = null;
            }
            textView19.setText("等待付款");
            TextView textView20 = this.tvWaitTime;
            if (textView20 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvWaitTime");
                textView20 = null;
            }
            textView20.setVisibility(0);
            TextView textView21 = this.tvWaitTime;
            if (textView21 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvWaitTime");
                textView21 = null;
            }
            textView21.setText(getTextWaitPaySpannable(getCountDownTimeStr(String.valueOf(System.currentTimeMillis() / 1000), orderDetailData.getPending_payment_ctime())));
            WeakHandler weakHandler2 = this.handler;
            if (weakHandler2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("handler");
            } else {
                weakHandler = weakHandler2;
            }
            weakHandler.sendEmptyMessageDelayed(this.MSG_WHAT, 1000L);
        } else {
            boolean zAreEqual = Intrinsics.areEqual("4", status);
            int i2 = R.drawable.icon_order_success_night;
            if (zAreEqual || Intrinsics.areEqual(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ, status) || Intrinsics.areEqual(Constants.VIA_REPORT_TYPE_CHAT_AUDIO, status) || Intrinsics.areEqual("27", status)) {
                ImageView imageView3 = this.imgStatus;
                if (imageView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("imgStatus");
                    imageView3 = null;
                }
                if (!isNight()) {
                    i2 = R.drawable.icon_order_success_day;
                }
                imageView3.setImageResource(i2);
                TextView textView22 = this.tvTitle;
                if (textView22 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
                    textView22 = null;
                }
                textView22.setText("已取消");
                TextView textView23 = this.tvWaitTime;
                if (textView23 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvWaitTime");
                } else {
                    textView = textView23;
                }
                textView.setVisibility(8);
            } else if (Intrinsics.areEqual(Constants.VIA_SHARE_TYPE_INFO, status) || Intrinsics.areEqual(Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE, status) || Intrinsics.areEqual("20", status) || Intrinsics.areEqual("40", status) || Intrinsics.areEqual("30", status)) {
                ImageView imageView4 = this.imgStatus;
                if (imageView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("imgStatus");
                    imageView4 = null;
                }
                if (!isNight()) {
                    i2 = R.drawable.icon_order_success_day;
                }
                imageView4.setImageResource(i2);
                TextView textView24 = this.tvTitle;
                if (textView24 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
                    textView24 = null;
                }
                textView24.setText("已支付");
                TextView textView25 = this.tvWaitTime;
                if (textView25 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvWaitTime");
                } else {
                    textView2 = textView25;
                }
                textView2.setVisibility(8);
            }
        }
        initButton(orderDetailData);
    }

    private final boolean isNight() {
        return SkinManager.getCurrentSkinType(this) == 1;
    }

    private final void mCreateCourseOrder(String orderNo, String totalAmount) {
        ArrayList arrayList = new ArrayList();
        SaleGoodsBean saleGoodsBean = new SaleGoodsBean();
        saleGoodsBean.setOrder_no(orderNo);
        saleGoodsBean.setIdentity_id(SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this));
        arrayList.add(saleGoodsBean);
        String json = new Gson().toJson(arrayList);
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("pay_type", this.mPayMethod);
        ajaxParams.put("goods_info", json);
        ajaxParams.put("total_amount", totalAmount);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mcreateOrderUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.order.OrderDetailActivity.mCreateCourseOrder.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showLong(OrderDetailActivity.this, "服务器请求错误");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String s2) {
                Intrinsics.checkNotNullParameter(s2, "s");
                super.onSuccess((C05991) s2);
                try {
                    CreateOrderBean createOrderBean = (CreateOrderBean) new Gson().fromJson(s2, CreateOrderBean.class);
                    if (!Intrinsics.areEqual(createOrderBean.getCode(), "200")) {
                        NewToast.showLong(OrderDetailActivity.this, createOrderBean.getMessage());
                    } else if (Intrinsics.areEqual(Constants.VIA_SHARE_TYPE_INFO, createOrderBean.getData().getStatus())) {
                        EventBus.getDefault().post(EventBusConstant.EVENT_ORDER_SUCCESS_FINISH_CONFIRM_UI);
                    } else {
                        OrderDetailActivity orderDetailActivity = OrderDetailActivity.this;
                        CreateOrderBean.Sign sign = createOrderBean.getData().getSign();
                        Intrinsics.checkNotNullExpressionValue(sign, "mCreateOrderBean.getData().sign");
                        orderDetailActivity.goToPay(sign);
                    }
                } catch (Exception e2) {
                    Log.d(OrderDetailActivity.this.TAG, "ErrorTag,orderDetail: " + e2.getMessage());
                }
            }
        });
    }

    private final void mShowPayCallbackDialog(String message, String resultCode) {
        if (TextUtils.equals(resultCode, PayMethodKeyKt.PAY_SUCCESS_CODE)) {
            EventBus.getDefault().post("BuySuccess");
            EventBus.getDefault().post(OrderDetailActivityKt.UPDATE_LIST);
            String str = this.orderNO;
            Intrinsics.checkNotNull(str);
            getOrderDetail(str);
        }
    }

    private final void orderDel(String orderNo) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(ActSubmitGoodsComment.EXTRA_DATA_ORDER_NO, orderNo);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.orderDel, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.order.OrderDetailActivity.orderDel.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
                ToastUtilsKt.toastOnUi$default(OrderDetailActivity.this, strMsg, 0, 2, (Object) null);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@Nullable String t2) {
                super.onSuccess((C06001) t2);
                if (t2 != null) {
                    try {
                        OrderDetailActivity orderDetailActivity = OrderDetailActivity.this;
                        JSONObject jSONObject = new JSONObject(t2);
                        if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                            ToastUtilsKt.toastOnUi$default(orderDetailActivity, "订单删除成功", 0, 2, (Object) null);
                            EventBus.getDefault().post(OrderDetailActivityKt.UPDATE_LIST);
                            orderDetailActivity.finish();
                        } else {
                            ToastUtilsKt.toastOnUi$default(orderDetailActivity, jSONObject.optString("message"), 0, 2, (Object) null);
                        }
                    } catch (Exception e2) {
                        String message = e2.getMessage();
                        Intrinsics.checkNotNull(message);
                        Log.e("Error: Line-343:", message);
                    }
                }
            }
        });
    }

    private final void updateTime(OrderDetailBean orderDetailData) {
        if (orderDetailData != null) {
            TextView textView = this.tvWaitTime;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvWaitTime");
                textView = null;
            }
            textView.setText(getTextWaitPaySpannable(getCountDownTimeStr(String.valueOf(System.currentTimeMillis() / 1000), orderDetailData.getPending_payment_ctime())));
        }
    }

    private final void wxPay(CreateOrderBean.Sign sign) {
        String appId = sign.getAppId();
        Intrinsics.checkNotNullExpressionValue(appId, "sign.appId");
        String partnerId = sign.getPartnerId();
        Intrinsics.checkNotNullExpressionValue(partnerId, "sign.partnerId");
        String prepayId = sign.getPrepayId();
        Intrinsics.checkNotNullExpressionValue(prepayId, "sign.prepayId");
        String packageValue = sign.getPackageValue();
        Intrinsics.checkNotNullExpressionValue(packageValue, "sign.packageValue");
        String nonceStr = sign.getNonceStr();
        Intrinsics.checkNotNullExpressionValue(nonceStr, "sign.nonceStr");
        String timeStamp = sign.getTimeStamp();
        Intrinsics.checkNotNullExpressionValue(timeStamp, "sign.timeStamp");
        String sign2 = sign.getSign();
        Intrinsics.checkNotNullExpressionValue(sign2, "sign.sign");
        PayMethodKeyKt.wxPay(this, appId, partnerId, prepayId, packageValue, nonceStr, timeStamp, sign2);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.orderNO = getIntent().getStringExtra("orderNo");
        View viewFindViewById = findViewById(R.id.tvOrderNo);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.tvOrderNo)");
        this.tvOrderNo = (TextView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.tvOrderConfirmLeaveMessage);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.tvOrderConfirmLeaveMessage)");
        this.tvOrderConfirmLeaveMessage = (TextView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.tvOrderPayTime);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.tvOrderPayTime)");
        this.tvOrderPayTime = (TextView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.tvOrderTime);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.tvOrderTime)");
        this.tvOrderTime = (TextView) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.tvOrderPayStyle);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.tvOrderPayStyle)");
        this.tvOrderPayStyle = (TextView) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.tvOrderConfirmCoupon);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.tvOrderConfirmCoupon)");
        this.tvOrderConfirmCoupon = (TextView) viewFindViewById6;
        View viewFindViewById7 = findViewById(R.id.tvOrderConfirmRedPacket);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.tvOrderConfirmRedPacket)");
        this.tvOrderConfirmRedPacket = (TextView) viewFindViewById7;
        View viewFindViewById8 = findViewById(R.id.tvOrderConfirmSale);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(R.id.tvOrderConfirmSale)");
        this.tvOrderConfirmSale = (TextView) viewFindViewById8;
        View viewFindViewById9 = findViewById(R.id.tvOrderConfirmFare);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById(R.id.tvOrderConfirmFare)");
        this.tvOrderConfirmFare = (TextView) viewFindViewById9;
        View viewFindViewById10 = findViewById(R.id.tvOrderConfirmPrice1);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById(R.id.tvOrderConfirmPrice1)");
        this.tvOrderConfirmPrice1 = (TextView) viewFindViewById10;
        View viewFindViewById11 = findViewById(R.id.layoutMsg);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById11, "findViewById(R.id.layoutMsg)");
        this.layoutMsg = (LinearLayout) viewFindViewById11;
        View viewFindViewById12 = findViewById(R.id.orderConfirmImg);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById12, "findViewById(R.id.orderConfirmImg)");
        this.orderConfirmImg = (ImageView) viewFindViewById12;
        View viewFindViewById13 = findViewById(R.id.tvOrderConfirmTitle);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById13, "findViewById(R.id.tvOrderConfirmTitle)");
        this.tvOrderConfirmTitle = (TextView) viewFindViewById13;
        View viewFindViewById14 = findViewById(R.id.tvOrderConfirmDesc);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById14, "findViewById(R.id.tvOrderConfirmDesc)");
        this.tvOrderConfirmDesc = (TextView) viewFindViewById14;
        View viewFindViewById15 = findViewById(R.id.tvOrderConfirmNum);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById15, "findViewById(R.id.tvOrderConfirmNum)");
        this.tvOrderConfirmNum = (TextView) viewFindViewById15;
        View viewFindViewById16 = findViewById(R.id.tvOrderConfirmPrice);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById16, "findViewById(R.id.tvOrderConfirmPrice)");
        this.tvOrderConfirmPrice = (TextView) viewFindViewById16;
        View viewFindViewById17 = findViewById(R.id.tvWaitTime);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById17, "findViewById(R.id.tvWaitTime)");
        this.tvWaitTime = (TextView) viewFindViewById17;
        View viewFindViewById18 = findViewById(R.id.tvPayNum);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById18, "findViewById(R.id.tvPayNum)");
        this.tvPayNum = (TextView) viewFindViewById18;
        View viewFindViewById19 = findViewById(R.id.tvPayDecrease);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById19, "findViewById(R.id.tvPayDecrease)");
        this.tvPayDecrease = (TextView) viewFindViewById19;
        View viewFindViewById20 = findViewById(R.id.imgClose);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById20, "findViewById(R.id.imgClose)");
        this.imgClose = (ImageView) viewFindViewById20;
        View viewFindViewById21 = findViewById(R.id.layoutPriceCount);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById21, "findViewById(R.id.layoutPriceCount)");
        this.layoutPriceCount = (RelativeLayout) viewFindViewById21;
        View viewFindViewById22 = findViewById(R.id.layoutFare);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById22, "findViewById(R.id.layoutFare)");
        this.layoutFare = (RelativeLayout) viewFindViewById22;
        View viewFindViewById23 = findViewById(R.id.layoutSale);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById23, "findViewById(R.id.layoutSale)");
        this.layoutSale = (RelativeLayout) viewFindViewById23;
        View viewFindViewById24 = findViewById(R.id.layoutCoupon);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById24, "findViewById(R.id.layoutCoupon)");
        this.layoutCoupon = (RelativeLayout) viewFindViewById24;
        View viewFindViewById25 = findViewById(R.id.tvTitle);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById25, "findViewById(R.id.tvTitle)");
        this.tvTitle = (TextView) viewFindViewById25;
        View viewFindViewById26 = findViewById(R.id.imgStatus);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById26, "findViewById(R.id.imgStatus)");
        this.imgStatus = (ImageView) viewFindViewById26;
        View viewFindViewById27 = findViewById(R.id.imgBack);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById27, "findViewById(R.id.imgBack)");
        this.imgBack = (ImageView) viewFindViewById27;
        View viewFindViewById28 = findViewById(R.id.layout_button_detail);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById28, "findViewById(R.id.layout_button_detail)");
        this.layoutButton = (LinearLayout) viewFindViewById28;
        View viewFindViewById29 = findViewById(R.id.recyclerViewAddress);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById29, "findViewById(R.id.recyclerViewAddress)");
        RecyclerView recyclerView = (RecyclerView) viewFindViewById29;
        this.recyclerViewAddress = recyclerView;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerViewAddress");
            recyclerView = null;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.adapter = new OrderAddressAdapter();
        TextView textView = this.tvTitle;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
            textView = null;
        }
        textView.setText("待支付");
        ImageView imageView = this.imgBack;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgBack");
            imageView = null;
        }
        imageView.setOnClickListener(this);
        ImageView imageView2 = this.imgBack;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgBack");
            imageView2 = null;
        }
        imageView2.setTag(Boolean.TRUE);
        ImageView imageView3 = this.imgClose;
        if (imageView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgClose");
            imageView3 = null;
        }
        imageView3.setOnClickListener(this);
        RecyclerView recyclerView2 = this.recyclerViewAddress;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerViewAddress");
            recyclerView2 = null;
        }
        OrderAddressAdapter orderAddressAdapter = this.adapter;
        if (orderAddressAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            orderAddressAdapter = null;
        }
        recyclerView2.setAdapter(orderAddressAdapter);
        OrderAddressAdapter orderAddressAdapter2 = this.adapter;
        if (orderAddressAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            orderAddressAdapter2 = null;
        }
        orderAddressAdapter2.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.mine.order.q
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                OrderDetailActivity.init$lambda$0(baseQuickAdapter, view, i2);
            }
        });
        if (TextUtils.isEmpty(this.orderNO)) {
            ToastUtilsKt.toastOnUi$default(this, "订单号异常", 0, 2, (Object) null);
        } else {
            String str = this.orderNO;
            Intrinsics.checkNotNull(str);
            getOrderDetail(str);
        }
        this.handler = new WeakHandler(this, new WeakHandler.HandlerCallback() { // from class: com.psychiatrygarden.activity.mine.order.r
            @Override // com.psychiatrygarden.utils.WeakHandler.HandlerCallback
            public final void handlerMessage(Message message) {
                OrderDetailActivity.init$lambda$1(this.f12979a, message);
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(@NotNull View v2) {
        Intrinsics.checkNotNullParameter(v2, "v");
        int id = v2.getId();
        if (id == R.id.imgBack) {
            finish();
            return;
        }
        if (id != R.id.imgClose) {
            return;
        }
        ImageView imageView = this.imgClose;
        ImageView imageView2 = null;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgClose");
            imageView = null;
        }
        Object tag = imageView.getTag();
        Boolean bool = Boolean.TRUE;
        if (Intrinsics.areEqual(tag, bool)) {
            LinearLayout linearLayout = this.layoutMsg;
            if (linearLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutMsg");
                linearLayout = null;
            }
            linearLayout.setVisibility(8);
            ImageView imageView3 = this.imgClose;
            if (imageView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imgClose");
                imageView3 = null;
            }
            imageView3.setTag(Boolean.FALSE);
            ImageView imageView4 = this.imgClose;
            if (imageView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imgClose");
            } else {
                imageView2 = imageView4;
            }
            imageView2.setImageResource(R.drawable.icon_arrow_bottom_new);
            return;
        }
        LinearLayout linearLayout2 = this.layoutMsg;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutMsg");
            linearLayout2 = null;
        }
        linearLayout2.setVisibility(0);
        ImageView imageView5 = this.imgClose;
        if (imageView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgClose");
            imageView5 = null;
        }
        imageView5.setTag(bool);
        ImageView imageView6 = this.imgClose;
        if (imageView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgClose");
        } else {
            imageView2 = imageView6;
        }
        imageView2.setImageResource(R.drawable.icon_arrow_top_new);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        setNewStyleStatusBarColor3();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        WeakHandler weakHandler = this.handler;
        if (weakHandler == null) {
            Intrinsics.throwUninitializedPropertyAccessException("handler");
            weakHandler = null;
        }
        weakHandler.removeCallbacksAndMessages(null);
    }

    @Subscribe
    public final void onEventMainThread(@NotNull WXPayEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        int i2 = WhenMappings.$EnumSwitchMapping$0[event.getStatus().ordinal()];
        if (i2 == 1) {
            mShowPayCallbackDialog("用户中途取消", "0");
        } else if (i2 == 2) {
            mShowPayCallbackDialog("支付失败", "0");
        } else {
            if (i2 != 3) {
                return;
            }
            mShowPayCallbackDialog("支付成功", "0");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // android.app.Activity
    public void onRestart() {
        super.onRestart();
        String str = this.orderNO;
        Intrinsics.checkNotNull(str);
        getOrderDetail(str);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_order_deatil);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

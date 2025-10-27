package com.psychiatrygarden.activity.mine.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alipay.sdk.app.PayTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ActCourseOrGoodsDetail;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.coupon.fragment.CouponAndRedPacketSelectDialogFragment;
import com.psychiatrygarden.activity.courselist.bean.CurriculumItemBean;
import com.psychiatrygarden.activity.material.InformationPreviewAct;
import com.psychiatrygarden.activity.purchase.activity.ShouhuodizhiActivity;
import com.psychiatrygarden.activity.purchase.beans.AddrDataBean;
import com.psychiatrygarden.activity.purchase.beans.CreateOrderBean;
import com.psychiatrygarden.activity.purchase.beans.GoodsBean;
import com.psychiatrygarden.activity.purchase.beans.SaleGoodsBean;
import com.psychiatrygarden.activity.purchase.beans.ShowAddressBean;
import com.psychiatrygarden.activity.purchase.util.PayResult;
import com.psychiatrygarden.activity.purchase.util.ResultCodeData;
import com.psychiatrygarden.bean.GiftDataBean;
import com.psychiatrygarden.bean.OrderConfirmParams;
import com.psychiatrygarden.bean.OrderFeeAddressMultipleBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.CouponSelectEvent;
import com.psychiatrygarden.event.DefAddressEvent;
import com.psychiatrygarden.event.DelAddressEvent;
import com.psychiatrygarden.event.EditAddressEvent;
import com.psychiatrygarden.event.WXPayEvent;
import com.psychiatrygarden.event.WXPayStatus;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.NavigationUtilKt;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.WeakHandler;
import com.psychiatrygarden.utils.pay.PayMethodKeyKt;
import com.psychiatrygarden.widget.CustomDialog;
import com.psychiatrygarden.widget.DialogLeaveMessage;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import com.ykb.ebook.activity.ReadBookActivity;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class OrderConfirmActivity extends BaseActivity implements View.OnClickListener {
    public static final int REQUEST_CODE = 11;
    private static final int SDK_PAY_FLAG = 1;
    private OrderAddressAdapter adapterAddress;
    private CustomDialog addressDialog;
    private OrderCommendAdapter commendAdapter;
    private OrderGiftAdapter giftAdapter;
    private ImageView imgCheckoutWX;
    private ImageView imgCheckoutZFB;
    private RelativeLayout layoutCoupon;
    private RelativeLayout layoutGift;
    private RelativeLayout layoutRedPacket;
    private RelativeLayout layoutTJ2;
    private CreateOrderBean mCreateOrderBean;
    private GoodsBean.DataBean.MealBean mGoodsMeanBeans;
    private WeakHandler mHandle;
    private OrderFeeAddressMultipleBean orderData;
    private RoundedImageView orderImage;
    private String orderType;
    private String price;
    private RecyclerView recyclerViewCommend;
    private RecyclerView recyclerViewGift;
    private TextView tvCount;
    private TextView tvCoupon;
    private TextView tvDesc;
    private TextView tvMessage;
    private TextView tvOrderConfirmBottomPrice;
    private TextView tvOrderConfirmFare;
    private TextView tvOrderConfirmSubmit;
    private TextView tvOrderTotalCount;
    private TextView tvOriginalPrice;
    private TextView tvPromotion;
    private TextView tvRedPacketCount;
    private TextView tvTitle;
    private TextView tvTotalPrice;
    private final List<ShowAddressBean.DataBean> listAddress = new ArrayList();
    private boolean isCourseGift = false;
    private boolean goodsHaveAddress = false;
    private boolean goodsHaveGiftAddress = false;
    private boolean isGoods = false;
    private boolean isInventoryLimit = false;
    private String goodType = "";
    private String goods_id = "";
    private String ebook_id = "";
    private String enclosure_id = "";
    private String course_id = "";
    private String quantity = "1";
    private String upgrade_type = "";
    private String deduction_id = "";
    private boolean is_promotion = false;
    private int inventoryCount = 0;
    private boolean addressDuoXuan = false;
    private String mPayMethod = PayMethodKeyKt.ALi_PayMethod;
    private String couponId = "0";
    private String redPacketId = "0";

    /* renamed from: com.psychiatrygarden.activity.mine.order.OrderConfirmActivity$6, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass6 {
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

    public class OrderAddressAdapter extends BaseQuickAdapter<ShowAddressBean.DataBean, BaseViewHolder> {
        public boolean multipleCount;

        public OrderAddressAdapter(@Nullable List<ShowAddressBean.DataBean> data) {
            super(R.layout.item_order_list_add_address, data);
            this.multipleCount = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(ShowAddressBean.DataBean dataBean, View view) {
            int count = dataBean.getCount();
            if (count <= 1) {
                NewToast.showLong(OrderConfirmActivity.this, "最少购买1件");
                return;
            }
            dataBean.setCount(count - 1);
            notifyDataSetChanged();
            OrderConfirmActivity.this.orderFeeApi();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1(ShowAddressBean.DataBean dataBean, View view) {
            int count = dataBean.getCount();
            if (count != OrderConfirmActivity.this.inventoryCount) {
                if (count > 0) {
                    dataBean.setCount(count + 1);
                    notifyDataSetChanged();
                    OrderConfirmActivity.this.orderFeeApi();
                    return;
                }
                return;
            }
            String str = "库存不足，仅剩" + count + "件";
            OrderConfirmActivity orderConfirmActivity = OrderConfirmActivity.this;
            if (!orderConfirmActivity.isInventoryLimit) {
                str = "已达到最大限购数量";
            }
            NewToast.showShort(orderConfirmActivity, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$2(View view) {
            OrderConfirmActivity.this.goToSelectAddress();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$3(View view) {
            OrderConfirmActivity.this.goToSelectAddress();
        }

        public void setMultipleCount(boolean multipleCount) {
            this.multipleCount = multipleCount;
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder baseViewHolder, final ShowAddressBean.DataBean dataAddress) {
            TextView textView = (TextView) baseViewHolder.getView(R.id.tvAddressNo);
            ImageView imageView = (ImageView) baseViewHolder.getView(R.id.imgAddressLocal);
            RelativeLayout relativeLayout = (RelativeLayout) baseViewHolder.getView(R.id.layoutContent);
            RelativeLayout relativeLayout2 = (RelativeLayout) baseViewHolder.getView(R.id.layoutAdd);
            TextView textView2 = (TextView) baseViewHolder.getView(R.id.tvName);
            TextView textView3 = (TextView) baseViewHolder.getView(R.id.tvPhone);
            TextView textView4 = (TextView) baseViewHolder.getView(R.id.tvAddress);
            TextView textView5 = (TextView) baseViewHolder.getView(R.id.tvDefFlag);
            TextView textView6 = (TextView) baseViewHolder.getView(R.id.tvReduceNum);
            RelativeLayout relativeLayout3 = (RelativeLayout) baseViewHolder.getView(R.id.layoutCount);
            RelativeLayout relativeLayout4 = (RelativeLayout) baseViewHolder.getView(R.id.layoutReduceNum);
            ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.imgAddNum);
            TextView textView7 = (TextView) baseViewHolder.getView(R.id.tvCount);
            if (this.multipleCount) {
                relativeLayout3.setVisibility(0);
                relativeLayout4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.order.g
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f12960c.lambda$convert$0(dataAddress, view);
                    }
                });
                imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.order.h
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f12962c.lambda$convert$1(dataAddress, view);
                    }
                });
                textView7.setText(dataAddress.getCount() + "");
            } else {
                relativeLayout3.setVisibility(8);
            }
            textView5.setVisibility("1".equals(dataAddress.getIs_default()) ? 0 : 8);
            String name = dataAddress.getName();
            if (!TextUtils.isEmpty(name) && name.length() > 7) {
                name = name.substring(0, 6) + "...";
            }
            if (TextUtils.isEmpty(name)) {
                name = "";
            }
            textView2.setText(name);
            textView3.setText(TextUtils.isEmpty(dataAddress.getMobile()) ? "" : dataAddress.getMobile());
            textView4.setText(TextUtils.isEmpty(dataAddress.getFull_address()) ? "" : dataAddress.getFull_address());
            boolean zIsEmpty = TextUtils.isEmpty(dataAddress.getName());
            relativeLayout2.setVisibility(zIsEmpty ? 0 : 8);
            relativeLayout.setVisibility(zIsEmpty ? 8 : 0);
            relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.order.i
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12964c.lambda$convert$2(view);
                }
            });
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.order.j
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12965c.lambda$convert$3(view);
                }
            });
            if (dataAddress.getCount() > 1) {
                textView6.setBackgroundColor(SkinManager.getThemeColor(OrderConfirmActivity.this.mContext, R.attr.first_text_color));
            } else {
                textView6.setBackgroundColor(SkinManager.getThemeColor(OrderConfirmActivity.this.mContext, R.attr.pic_dot_color));
            }
            if (getItemCount() <= 1) {
                imageView.setVisibility(8);
                textView.setVisibility(8);
                return;
            }
            imageView.setVisibility(0);
            textView.setVisibility(0);
            textView.setText("地址" + getItemPosition(dataAddress));
        }
    }

    public static class OrderCommendAdapter extends BaseQuickAdapter<CurriculumItemBean.DataDTO, BaseViewHolder> {
        public OrderCommendAdapter(int layoutResId, @Nullable List<CurriculumItemBean.DataDTO> data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(CurriculumItemBean.DataDTO dataDTO, View view) {
            ActCourseOrGoodsDetail.INSTANCE.navigationToCourseOrGoodsDetail(getContext(), dataDTO.getId(), "");
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder baseViewHolder, final CurriculumItemBean.DataDTO itemData) {
            RelativeLayout relativeLayout = (RelativeLayout) baseViewHolder.getView(R.id.layoutRoot);
            TextView textView = (TextView) baseViewHolder.getView(R.id.tvTJ);
            RoundedImageView roundedImageView = (RoundedImageView) baseViewHolder.getView(R.id.orderConfirmRecommendImg);
            TextView textView2 = (TextView) baseViewHolder.getView(R.id.tvOrderConfirmTitleRecommend);
            TextView textView3 = (TextView) baseViewHolder.getView(R.id.tvOrderConfirmRecommendDesc);
            TextView textView4 = (TextView) baseViewHolder.getView(R.id.tvOrderConfirmRecommendPrice);
            GlideUtils.loadImageDef(getContext(), itemData.getCover(), roundedImageView);
            textView3.setText(itemData.getDescription());
            textView2.setText(itemData.getTitle());
            textView4.setText(itemData.getButton_text());
            if (TextUtils.isEmpty(itemData.getRecommend_label())) {
                textView.setVisibility(8);
            } else {
                textView.setText(itemData.getRecommend_label());
            }
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.order.k
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12966c.lambda$convert$0(itemData, view);
                }
            });
        }
    }

    public class OrderGiftAdapter extends BaseQuickAdapter<GiftDataBean, BaseViewHolder> {
        public OrderGiftAdapter(@Nullable List<GiftDataBean> data) {
            super(R.layout.item_order_confirm_gift, data);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(GiftDataBean giftDataBean, String str, View view) {
            String id = giftDataBean.getId();
            if (str == null) {
            }
            switch (str) {
                case "1":
                    if (!TextUtils.isEmpty(id)) {
                        NavigationUtilKt.gotoGoodsDetail(OrderConfirmActivity.this, id);
                        break;
                    }
                    break;
                case "2":
                    if (!TextUtils.isEmpty(id)) {
                        ActCourseOrGoodsDetail.INSTANCE.navigationToCourseOrGoodsDetail(OrderConfirmActivity.this, id, "");
                        break;
                    }
                    break;
                case "3":
                    NavigationUtilKt.gotoVipCenter(OrderConfirmActivity.this);
                    break;
                case "4":
                    if (!TextUtils.isEmpty(id)) {
                        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1");
                        String admin = UserConfig.getInstance().getUser().getAdmin();
                        String avatar = UserConfig.getInstance().getUser().getAvatar();
                        OrderConfirmActivity orderConfirmActivity = OrderConfirmActivity.this;
                        orderConfirmActivity.startActivity(ReadBookActivity.INSTANCE.newIntent(orderConfirmActivity.mContext, id, UserConfig.getUserId(), strConfig, admin, avatar, UserConfig.getInstance().getUser().getToken(), UserConfig.getInstance().getUser().getSecret()));
                        break;
                    }
                    break;
                case "5":
                    if (!TextUtils.isEmpty(id)) {
                        InformationPreviewAct.newIntent(getContext(), id, "", false);
                        break;
                    }
                    break;
            }
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder baseViewHolder, final GiftDataBean itemData) {
            String str;
            RelativeLayout relativeLayout = (RelativeLayout) baseViewHolder.getView(R.id.layoutRoot);
            RoundedImageView roundedImageView = (RoundedImageView) baseViewHolder.getView(R.id.orderGiftImg);
            TextView textView = (TextView) baseViewHolder.getView(R.id.tvGiftTitle);
            TextView textView2 = (TextView) baseViewHolder.getView(R.id.tvGiftNum);
            final String type = itemData.getType();
            textView.setText(TextUtils.isEmpty(itemData.getTitle()) ? "" : itemData.getTitle());
            if (TextUtils.isEmpty(itemData.getTitle())) {
                str = "x1";
            } else {
                str = "x" + itemData.getCount();
            }
            textView2.setText(str);
            String cover = itemData.getCover();
            if (cover != null) {
                GlideUtils.loadImageDef(getContext(), cover, roundedImageView);
            }
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.order.l
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12968c.lambda$convert$0(itemData, type, view);
                }
            });
        }
    }

    private void aLiPay(final String singSign) {
        new Thread(new Runnable() { // from class: com.psychiatrygarden.activity.mine.order.f
            @Override // java.lang.Runnable
            public final void run() {
                this.f12955c.lambda$aLiPay$2(singSign);
            }
        }).start();
    }

    private String getAddress() {
        ArrayList arrayList = new ArrayList();
        List<ShowAddressBean.DataBean> list = this.listAddress;
        if (list == null) {
            return "";
        }
        if (list.size() == 1 && TextUtils.isEmpty(this.listAddress.get(0).getAddr_id())) {
            return "";
        }
        for (int i2 = 0; i2 < this.listAddress.size(); i2++) {
            if (!TextUtils.isEmpty(this.listAddress.get(i2).getAddr_id())) {
                AddrDataBean addrDataBean = new AddrDataBean();
                addrDataBean.setAddr_id(this.listAddress.get(i2).getAddr_id());
                addrDataBean.setQuantity(this.listAddress.get(i2).getCount() + "");
                arrayList.add(addrDataBean);
            }
        }
        return arrayList.isEmpty() ? "" : new Gson().toJson(arrayList);
    }

    private void getAddressData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.mUserDefaultAddress, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.order.OrderConfirmActivity.1
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
                super.onSuccess((AnonymousClass1) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    String strOptString = jSONObject.optString("code");
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                    if ("200".equals(strOptString)) {
                        if (jSONObjectOptJSONObject != null) {
                            if (TextUtils.isEmpty(jSONObjectOptJSONObject.optString("name"))) {
                                OrderConfirmActivity.this.listAddress.clear();
                                OrderConfirmActivity.this.listAddress.add(new ShowAddressBean.DataBean());
                                OrderConfirmActivity.this.adapterAddress.setList(OrderConfirmActivity.this.listAddress);
                                OrderConfirmActivity.this.showAddressHintDialog();
                            } else {
                                OrderConfirmActivity.this.hideAddressHintDialog();
                                OrderConfirmActivity.this.listAddress.clear();
                                if (OrderConfirmActivity.this.addressDuoXuan) {
                                    OrderConfirmActivity.this.listAddress.add(new ShowAddressBean.DataBean());
                                }
                                OrderConfirmActivity.this.listAddress.add((ShowAddressBean.DataBean) new Gson().fromJson(jSONObjectOptJSONObject.toString(), ShowAddressBean.DataBean.class));
                                OrderConfirmActivity.this.adapterAddress.setList(OrderConfirmActivity.this.listAddress);
                            }
                        }
                        OrderConfirmActivity.this.orderFeeApi();
                    }
                } catch (Exception e2) {
                    System.out.println("ErrorTag:" + e2.getMessage());
                }
            }
        });
    }

    private int getAddressNumCount() {
        List<ShowAddressBean.DataBean> list = this.listAddress;
        if (list == null) {
            return 0;
        }
        if (list.size() == 1 && TextUtils.isEmpty(this.listAddress.get(0).getAddr_id())) {
            return 0;
        }
        int count = 0;
        for (int i2 = 0; i2 < this.listAddress.size(); i2++) {
            if (!TextUtils.isEmpty(this.listAddress.get(i2).getAddr_id())) {
                count += this.listAddress.get(i2).getCount();
            }
        }
        return count;
    }

    private void getOrderGiftItems() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.course_id);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.orderGiftItems, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.order.OrderConfirmActivity.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                OrderConfirmActivity.this.showGiftItem(null);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                JSONArray jSONArrayOptJSONArray;
                super.onSuccess((AnonymousClass5) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!"200".equals(jSONObject.optString("code")) || (jSONArrayOptJSONArray = jSONObject.optJSONArray("data")) == null) {
                        OrderConfirmActivity.this.showGiftItem(null);
                    } else {
                        OrderConfirmActivity.this.showGiftItem((List) new Gson().fromJson(jSONArrayOptJSONArray.toString(), new TypeToken<List<GiftDataBean>>() { // from class: com.psychiatrygarden.activity.mine.order.OrderConfirmActivity.5.1
                        }.getType()));
                    }
                } catch (Exception e2) {
                    OrderConfirmActivity.this.showGiftItem(null);
                    Log.e("ErrorTag", e2.getMessage());
                }
            }
        });
    }

    public static void goToOrderConfirmEntrance(Context context, OrderConfirmParams orderConfirmParams) {
        Intent intent = new Intent(context, (Class<?>) OrderConfirmActivity.class);
        intent.putExtra("goods_id", orderConfirmParams.getGoods_id());
        intent.putExtra("ebook_id", orderConfirmParams.getEbook_id());
        intent.putExtra("enclosure_id", orderConfirmParams.getEnclosure_id());
        intent.putExtra("course_id", orderConfirmParams.getCourse_id());
        intent.putExtra("price", orderConfirmParams.getPrice());
        intent.putExtra("quantity", orderConfirmParams.getQuantity());
        intent.putExtra("upgrade_type", orderConfirmParams.getUpgrade_type());
        intent.putExtra("deduction_id", orderConfirmParams.getDeduction_id());
        intent.putExtra("goodType", orderConfirmParams.getGoodType());
        intent.putExtra("isGoods", orderConfirmParams.isGoods());
        intent.putExtra("isCourseGift", orderConfirmParams.isCourseHaveAddress());
        intent.putExtra("goodsHaveAddress", orderConfirmParams.isGoodsHaveAddress());
        intent.putExtra("goodsHaveGiftAddress", orderConfirmParams.isGoodsHaveGiftAddress());
        intent.putExtra("is_promotion", orderConfirmParams.isIs_promotion());
        intent.putExtra("inventoryCount", orderConfirmParams.getGoodsCanBuyNum());
        intent.putExtra("isInventoryLimit", orderConfirmParams.isInventoryLimit());
        context.startActivity(intent);
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
    public void goToSelectAddress() {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.listAddress.size(); i2++) {
            if (!TextUtils.isEmpty(this.listAddress.get(i2).getAddr_id())) {
                arrayList.add(this.listAddress.get(i2));
            }
        }
        NavigationUtilKt.goToShouHuoDiZhiActivity(this, ShouhuodizhiActivity.FROM_TYPE_VALUE_ORDER_CONFIRM, this.addressDuoXuan, arrayList, 11);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideAddressHintDialog() {
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.mine.order.d
            @Override // java.lang.Runnable
            public final void run() {
                this.f12949c.lambda$hideAddressHintDialog$5();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initData(OrderFeeAddressMultipleBean data) {
        if (data == null) {
            return;
        }
        this.orderData = data;
        String cover = data.getCover();
        String title = data.getTitle();
        String quantity = data.getQuantity();
        String original_price_yuan = data.getOriginal_price_yuan();
        String original_total_price_yuan = data.getOriginal_total_price_yuan();
        String promotion_yuan = data.getPromotion_yuan();
        String coupon_price_yuan = data.getCoupon_price_yuan();
        String red_packet_price_yuan = data.getRed_packet_price_yuan();
        if (cover != null) {
            GlideUtils.loadImageDef(this, cover, this.orderImage);
        }
        this.tvTitle.setText(title);
        this.tvDesc.setText(TextUtils.isEmpty(data.getDescription()) ? "" : data.getDescription());
        if (TextUtils.isEmpty(quantity)) {
            this.tvCount.setText("数量x1");
        } else {
            this.tvCount.setText("数量x" + quantity);
        }
        this.tvOriginalPrice.setText(original_price_yuan);
        this.tvTotalPrice.setText(original_total_price_yuan);
        if (TextUtils.isEmpty(promotion_yuan)) {
            this.tvPromotion.setText("减¥0");
        } else {
            this.tvPromotion.setText("减" + promotion_yuan);
        }
        int i2 = TextUtils.isEmpty(this.orderData.getCoupon_available_count()) ? 0 : Integer.parseInt(this.orderData.getCoupon_available_count());
        if (i2 == 0 || this.is_promotion) {
            this.tvCoupon.setText("无可用优惠券");
            setCouponReaPacketEnable(true, false);
        } else if ("0".equals(this.orderData.getCoupon_id()) || "-1".equals(this.orderData.getCoupon_id())) {
            this.tvCoupon.setText(i2 + "张可用");
            setCouponReaPacketEnable(true, true);
        } else if (TextUtils.isEmpty(coupon_price_yuan)) {
            this.tvCoupon.setText("减¥0");
            setCouponReaPacketEnable(true, true);
        } else {
            this.tvCoupon.setText("减" + coupon_price_yuan);
            setCouponReaPacketEnable(true, true);
        }
        int i3 = TextUtils.isEmpty(this.orderData.getRed_packet_available_count()) ? 0 : Integer.parseInt(this.orderData.getRed_packet_available_count());
        if (i3 == 0) {
            this.tvRedPacketCount.setText("无可用红包");
            setCouponReaPacketEnable(false, false);
        } else if ("0".equals(this.orderData.getRed_packet_id()) || "-1".equals(this.orderData.getRed_packet_id())) {
            this.tvRedPacketCount.setText(i3 + "个可用");
            setCouponReaPacketEnable(false, true);
        } else if (TextUtils.isEmpty(red_packet_price_yuan)) {
            this.tvRedPacketCount.setText("减¥0");
            setCouponReaPacketEnable(false, true);
        } else {
            this.tvRedPacketCount.setText("减" + red_packet_price_yuan);
            setCouponReaPacketEnable(false, true);
        }
        this.tvOrderConfirmBottomPrice.setText(data.getTotal_amount_yuan());
        this.tvOrderTotalCount.setText("合计：" + data.getTotal_amount_yuan());
        this.tvOrderConfirmFare.setText(TextUtils.isEmpty(data.getPostage_price_yuan()) ? "¥0" : data.getPostage_price_yuan());
    }

    private void initIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            this.goods_id = intent.getStringExtra("goods_id");
            this.ebook_id = intent.getStringExtra("ebook_id");
            this.enclosure_id = intent.getStringExtra("enclosure_id");
            this.course_id = intent.getStringExtra("course_id");
            this.price = intent.getStringExtra("price");
            this.quantity = intent.getStringExtra("quantity");
            this.upgrade_type = intent.getStringExtra("upgrade_type");
            this.deduction_id = intent.getStringExtra("deduction_id");
            this.goodType = intent.getStringExtra("goodType");
            this.isCourseGift = intent.getBooleanExtra("isCourseGift", false);
            this.isGoods = intent.getBooleanExtra("isGoods", false);
            this.goodsHaveAddress = intent.getBooleanExtra("goodsHaveAddress", false);
            this.goodsHaveGiftAddress = intent.getBooleanExtra("goodsHaveGiftAddress", false);
            this.is_promotion = intent.getBooleanExtra("is_promotion", false);
            this.inventoryCount = intent.getIntExtra("inventoryCount", 0);
            this.isInventoryLimit = intent.getBooleanExtra("isInventoryLimit", false);
            this.addressDuoXuan = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$aLiPay$2(String str) {
        Map<String, String> mapPayV2 = new PayTask(this).payV2(str, true);
        Message message = new Message();
        message.what = 1;
        message.obj = mapPayV2;
        this.mHandle.sendMessage(message);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hideAddressHintDialog$5() {
        CustomDialog customDialog = this.addressDialog;
        if (customDialog != null) {
            customDialog.dismissNoAnimaltion();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(Message message) {
        if (message.what == 1) {
            String resultStatus = new PayResult((Map) message.obj).getResultStatus();
            mShowDialog(new ResultCodeData().mCheckResultCode(resultStatus), resultStatus);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAddressHintDialog$3(View view) {
        goToSelectAddress();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAddressHintDialog$4(View view) {
        finish();
        this.addressDialog.dismissNoAnimaltion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showLeaveMessage$1(String str) {
        this.tvMessage.setText(str);
    }

    private void mCreateOrder() {
        String json;
        try {
            ArrayList arrayList = new ArrayList();
            SaleGoodsBean saleGoodsBean = new SaleGoodsBean();
            if (!TextUtils.isEmpty(this.goods_id)) {
                saleGoodsBean.setGoods_id(this.goods_id);
            }
            if (!TextUtils.isEmpty(this.ebook_id)) {
                saleGoodsBean.setEbook_id(this.ebook_id);
            }
            if (!TextUtils.isEmpty(this.course_id)) {
                saleGoodsBean.setCourse_id(this.course_id);
            }
            if (!TextUtils.isEmpty(this.enclosure_id)) {
                saleGoodsBean.setEnclosure_id(this.enclosure_id);
            }
            if (!TextUtils.isEmpty(this.orderData.getPrice())) {
                saleGoodsBean.setPrice(this.orderData.getPrice());
            }
            if (!TextUtils.isEmpty(this.quantity)) {
                saleGoodsBean.setQuantity(this.quantity);
            }
            if (!TextUtils.isEmpty(this.orderData.getCoupon_id())) {
                saleGoodsBean.setCoupon_id(this.orderData.getCoupon_id());
            }
            if (!TextUtils.isEmpty(this.orderData.getRed_packet_id())) {
                saleGoodsBean.setRed_packet_id(this.orderData.getRed_packet_id());
            }
            if (!TextUtils.isEmpty(this.course_id)) {
                saleGoodsBean.setIdentity_id(SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this));
            }
            arrayList.add(saleGoodsBean);
            json = new Gson().toJson(arrayList);
        } catch (Exception e2) {
            Log.e(this.TAG, "mCreateGoodsOrder: " + e2.getMessage());
            json = "";
        }
        String address = TextUtils.isEmpty(getAddress()) ? "" : getAddress();
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("pay_type", this.mPayMethod);
        ajaxParams.put("goods_info", json);
        ajaxParams.put("total_amount", this.orderData.getTotal_amount());
        if (!TextUtils.isEmpty(this.upgrade_type)) {
            ajaxParams.put("upgrade_type", this.upgrade_type);
        }
        if (!TextUtils.isEmpty(this.deduction_id)) {
            ajaxParams.put("deduction_id", this.deduction_id);
        }
        if (!TextUtils.isEmpty(address)) {
            ajaxParams.put("address", address);
        }
        if (!TextUtils.isEmpty(this.tvMessage.getText().toString())) {
            ajaxParams.put("leave_message", this.tvMessage.getText().toString());
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mcreateOrderUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.order.OrderConfirmActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showLong(OrderConfirmActivity.this, "服务器请求错误！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    OrderConfirmActivity.this.mCreateOrderBean = (CreateOrderBean) new Gson().fromJson(s2, CreateOrderBean.class);
                    if (!OrderConfirmActivity.this.mCreateOrderBean.getCode().equals("200")) {
                        NewToast.showLong(OrderConfirmActivity.this, "订单生成失败！");
                    } else if (Constants.VIA_SHARE_TYPE_INFO.equals(OrderConfirmActivity.this.mCreateOrderBean.getData().getStatus())) {
                        OrderListActivity.INSTANCE.goToOrderListActivity(OrderConfirmActivity.this);
                        EventBus.getDefault().post(EventBusConstant.EVENT_ORDER_SUCCESS_FINISH_CONFIRM_UI);
                        OrderConfirmActivity.this.finish();
                    } else {
                        OrderConfirmActivity orderConfirmActivity = OrderConfirmActivity.this;
                        orderConfirmActivity.goToPay(orderConfirmActivity.mCreateOrderBean.getData().getSign());
                    }
                } catch (Exception e3) {
                    Log.d(OrderConfirmActivity.this.TAG, "onSuccess: " + e3.getMessage());
                }
            }
        });
    }

    private void mShowDialog(String message, final String resultcode) {
        if (TextUtils.equals(resultcode, PayMethodKeyKt.PAY_SUCCESS_CODE)) {
            EventBus.getDefault().post("BuySuccess");
            if (!TextUtils.isEmpty(this.course_id) || this.isGoods) {
                OrderListActivity.INSTANCE.goToOrderListActivity(this);
            }
        } else {
            NewToast.showShort(this, message);
        }
        finish();
    }

    private boolean needAddress() {
        boolean z2 = this.isGoods;
        return ((z2 && this.goodsHaveAddress) || (z2 && this.goodsHaveGiftAddress)) || (!TextUtils.isEmpty(this.course_id) && this.isCourseGift);
    }

    private void orderCourseRecommend() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.course_id);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.orderCourseReCommend, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.order.OrderConfirmActivity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                OrderConfirmActivity.this.layoutTJ2.setVisibility(8);
                OrderConfirmActivity.this.recyclerViewCommend.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    List<CurriculumItemBean.DataDTO> data = ((CurriculumItemBean) new Gson().fromJson(s2, CurriculumItemBean.class)).getData();
                    if (data == null || data.isEmpty()) {
                        OrderConfirmActivity.this.layoutTJ2.setVisibility(8);
                        OrderConfirmActivity.this.recyclerViewCommend.setVisibility(8);
                    } else {
                        OrderConfirmActivity.this.commendAdapter.setList(data);
                        OrderConfirmActivity.this.recyclerViewCommend.setVisibility(0);
                        OrderConfirmActivity.this.layoutTJ2.setVisibility(0);
                    }
                } catch (Exception e2) {
                    Log.e("ErrorTag", e2.getMessage());
                    OrderConfirmActivity.this.layoutTJ2.setVisibility(8);
                    OrderConfirmActivity.this.recyclerViewCommend.setVisibility(8);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setButtonEnable(boolean enable) {
        this.tvOrderConfirmSubmit.setEnabled(enable);
        this.tvOrderConfirmSubmit.setTextColor(SkinManager.getThemeColor(this, enable ? R.attr.app_bg : R.attr.forth_txt_color));
    }

    private void setCouponReaPacketEnable(boolean isCoupon, boolean enable) {
        if (isCoupon) {
            this.tvCoupon.setTextColor(enable ? SkinManager.getThemeColor(this, R.attr.orange_color) : SkinManager.getThemeColor(this, R.attr.forth_txt_color));
        } else {
            this.tvRedPacketCount.setTextColor(enable ? SkinManager.getThemeColor(this, R.attr.orange_color) : SkinManager.getThemeColor(this, R.attr.forth_txt_color));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAddressHintDialog() {
        CustomDialog customDialog = new CustomDialog(this.mContext, 1);
        this.addressDialog = customDialog;
        customDialog.setCancelable(false);
        this.addressDialog.isOutTouchDismiss(false);
        this.addressDialog.setMessage("你还没选择收货地址哦，赶快设置一个吧");
        this.addressDialog.setPositiveBtn("去设置", new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.order.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12943c.lambda$showAddressHintDialog$3(view);
            }
        });
        this.addressDialog.setNegativeBtn("取消", new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.order.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12946c.lambda$showAddressHintDialog$4(view);
            }
        });
        this.addressDialog.show();
    }

    private void showDialogCoupon(boolean isCoupon) {
        String str = !TextUtils.isEmpty(this.goods_id) ? this.goods_id : "";
        if (!TextUtils.isEmpty(this.course_id)) {
            str = this.course_id;
        }
        if (!TextUtils.isEmpty(this.ebook_id)) {
            str = this.ebook_id;
        }
        if (!TextUtils.isEmpty(this.enclosure_id)) {
            str = this.enclosure_id;
        }
        String str2 = str;
        int i2 = !TextUtils.isEmpty(this.orderData.getOrder_price()) ? Integer.parseInt(this.orderData.getOrder_price()) : 0;
        String str3 = isCoupon ? "1" : "2";
        String coupon_id = isCoupon ? this.orderData.getCoupon_id() : this.orderData.getRed_packet_id();
        CouponAndRedPacketSelectDialogFragment couponAndRedPacketSelectDialogFragmentNewInstance = CouponAndRedPacketSelectDialogFragment.INSTANCE.newInstance(str3, str2, this.goodType, i2 + "", coupon_id, this.is_promotion);
        getSupportFragmentManager().executePendingTransactions();
        if (couponAndRedPacketSelectDialogFragmentNewInstance.isAdded() || couponAndRedPacketSelectDialogFragmentNewInstance.isRemoving() || couponAndRedPacketSelectDialogFragmentNewInstance.isVisible()) {
            return;
        }
        couponAndRedPacketSelectDialogFragmentNewInstance.show(getSupportFragmentManager(), "CouponAndRedPacketSelectDialogFragment");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showGiftItem(List<GiftDataBean> dataBeanList) {
        if (dataBeanList == null || dataBeanList.isEmpty()) {
            this.recyclerViewGift.setVisibility(8);
            this.layoutGift.setVisibility(8);
        } else {
            this.layoutGift.setVisibility(0);
            this.recyclerViewGift.setVisibility(0);
            this.giftAdapter.setList(dataBeanList);
        }
    }

    private void showLeaveMessage(String msg) {
        new DialogLeaveMessage(this, new DialogLeaveMessage.ClickCallBack() { // from class: com.psychiatrygarden.activity.mine.order.a
            @Override // com.psychiatrygarden.widget.DialogLeaveMessage.ClickCallBack
            public final void callback(String str) {
                this.f12941a.lambda$showLeaveMessage$1(str);
            }
        }, "添加留言", msg).show();
    }

    private void wxPay(CreateOrderBean.Sign sign) {
        PayMethodKeyKt.wxPay(this, sign.getAppId(), sign.getPartnerId(), sign.getPrepayId(), sign.getPackageValue(), sign.getNonceStr(), sign.getTimeStamp(), sign.getSign());
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        initIntentData();
        this.imgCheckoutWX = (ImageView) findViewById(R.id.imgCheckoutWX);
        this.tvOrderTotalCount = (TextView) findViewById(R.id.tvOrderTotalCount);
        this.layoutRedPacket = (RelativeLayout) findViewById(R.id.layoutRedPacket);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.layoutCoupon);
        this.layoutCoupon = relativeLayout;
        relativeLayout.setOnClickListener(this);
        this.layoutRedPacket.setOnClickListener(this);
        RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.layoutFare);
        this.tvOrderConfirmFare = (TextView) findViewById(R.id.tvOrderConfirmFare);
        this.recyclerViewGift = (RecyclerView) findViewById(R.id.recyclerViewGift);
        this.layoutGift = (RelativeLayout) findViewById(R.id.layoutGift);
        this.orderImage = (RoundedImageView) findViewById(R.id.orderConfirmImg);
        this.tvCount = (TextView) findViewById(R.id.tvOrderConfirmNum);
        this.tvTitle = (TextView) findViewById(R.id.tvOrderConfirmTitle);
        this.tvDesc = (TextView) findViewById(R.id.tvOrderConfirmDesc);
        this.tvOriginalPrice = (TextView) findViewById(R.id.tvOrderConfirmPrice);
        this.tvTotalPrice = (TextView) findViewById(R.id.tvOrderConfirmPrice1);
        this.tvPromotion = (TextView) findViewById(R.id.tvOrderConfirmSale);
        this.tvCoupon = (TextView) findViewById(R.id.tvOrderConfirmCoupon);
        this.tvRedPacketCount = (TextView) findViewById(R.id.tvRedPacketCount);
        this.tvMessage = (TextView) findViewById(R.id.tvOrderConfirmLeaveMessage);
        this.tvOrderConfirmBottomPrice = (TextView) findViewById(R.id.tvOrderConfirmBottomPrice);
        this.tvOrderConfirmSubmit = (TextView) findViewById(R.id.tvOrderConfirmSubmit);
        RelativeLayout relativeLayout3 = (RelativeLayout) findViewById(R.id.layoutWXPay);
        ((RelativeLayout) findViewById(R.id.layoutZFB)).setOnClickListener(this);
        this.layoutTJ2 = (RelativeLayout) findViewById(R.id.layoutTJ2);
        ((ImageView) findViewById(R.id.iv_actionbar_back)).setOnClickListener(this);
        this.tvOrderConfirmSubmit.setOnClickListener(this);
        ImageView imageView = (ImageView) findViewById(R.id.imgCheckoutZFB);
        this.imgCheckoutZFB = imageView;
        imageView.setSelected(true);
        ((TextView) findViewById(R.id.txt_actionbar_title)).setText("确认订单");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewAddress);
        recyclerView.setVisibility(0);
        this.recyclerViewCommend = (RecyclerView) findViewById(R.id.recyclerViewCommend);
        ((RelativeLayout) findViewById(R.id.layoutMessage)).setOnClickListener(this);
        relativeLayout3.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderAddressAdapter orderAddressAdapter = new OrderAddressAdapter(new ArrayList());
        this.adapterAddress = orderAddressAdapter;
        orderAddressAdapter.setMultipleCount(this.isGoods && this.goodsHaveAddress);
        recyclerView.setAdapter(this.adapterAddress);
        this.recyclerViewCommend.setLayoutManager(new LinearLayoutManager(this));
        OrderCommendAdapter orderCommendAdapter = new OrderCommendAdapter(R.layout.item_order_commend, new ArrayList());
        this.commendAdapter = orderCommendAdapter;
        this.recyclerViewCommend.setAdapter(orderCommendAdapter);
        this.layoutTJ2.setVisibility(8);
        this.recyclerViewCommend.setVisibility(8);
        this.recyclerViewGift.setLayoutManager(new LinearLayoutManager(this));
        OrderGiftAdapter orderGiftAdapter = new OrderGiftAdapter(new ArrayList());
        this.giftAdapter = orderGiftAdapter;
        this.recyclerViewGift.setAdapter(orderGiftAdapter);
        this.layoutGift.setVisibility(8);
        this.recyclerViewGift.setVisibility(8);
        this.listAddress.add(new ShowAddressBean.DataBean());
        this.adapterAddress.setList(this.listAddress);
        recyclerView.setVisibility(8);
        if (needAddress()) {
            recyclerView.setVisibility(0);
            relativeLayout2.setVisibility(0);
        } else {
            recyclerView.setVisibility(8);
            relativeLayout2.setVisibility(8);
        }
        setButtonEnable(false);
        if (!TextUtils.isEmpty(this.course_id)) {
            orderCourseRecommend();
            getOrderGiftItems();
        }
        if (this.isGoods && this.goodsHaveAddress) {
            getOrderGiftItems();
        }
        if (needAddress()) {
            getAddressData();
        } else {
            orderFeeApi();
        }
        this.mHandle = new WeakHandler(this, new WeakHandler.HandlerCallback() { // from class: com.psychiatrygarden.activity.mine.order.e
            @Override // com.psychiatrygarden.utils.WeakHandler.HandlerCallback
            public final void handlerMessage(Message message) {
                this.f12952a.lambda$init$0(message);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 11 || data == null) {
            return;
        }
        try {
            List list = (List) data.getExtras().getSerializable("datalist");
            if (list == null || list.isEmpty() || TextUtils.isEmpty(((ShowAddressBean.DataBean) list.get(0)).getName())) {
                if (TextUtils.isEmpty(getAddress().trim())) {
                    needAddress();
                    return;
                }
                return;
            }
            this.listAddress.clear();
            if (this.addressDuoXuan) {
                this.listAddress.add(new ShowAddressBean.DataBean());
            }
            this.listAddress.addAll(list);
            this.adapterAddress.setList(this.listAddress);
            orderFeeApi();
            hideAddressHintDialog();
        } catch (Exception e2) {
            Log.d(this.TAG, "onActivityResult: " + e2.getMessage());
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        boolean z2 = false;
        switch (v2.getId()) {
            case R.id.iv_actionbar_back /* 2131363947 */:
                finish();
                break;
            case R.id.layoutCoupon /* 2131364387 */:
                if (this.orderData != null) {
                    showDialogCoupon(true);
                    break;
                }
                break;
            case R.id.layoutMessage /* 2131364422 */:
                showLeaveMessage(this.tvMessage.getText().toString());
                break;
            case R.id.layoutRedPacket /* 2131364434 */:
                if (this.orderData != null) {
                    showDialogCoupon(false);
                    break;
                }
                break;
            case R.id.layoutWXPay /* 2131364464 */:
                this.imgCheckoutWX.setSelected(true);
                this.imgCheckoutZFB.setSelected(false);
                this.mPayMethod = "wechat";
                break;
            case R.id.layoutZFB /* 2131364467 */:
                this.imgCheckoutWX.setSelected(false);
                this.imgCheckoutZFB.setSelected(true);
                this.mPayMethod = PayMethodKeyKt.ALi_PayMethod;
                break;
            case R.id.tvOrderConfirmSubmit /* 2131367537 */:
                if (this.orderData != null) {
                    boolean z3 = this.isGoods && this.goodsHaveAddress;
                    if (!TextUtils.isEmpty(this.course_id) && this.isCourseGift) {
                        z2 = true;
                    }
                    if (!z3 && !z2) {
                        mCreateOrder();
                        break;
                    } else if (!TextUtils.isEmpty(getAddress().trim())) {
                        mCreateOrder();
                        break;
                    } else {
                        NewToast.showShort(this, "收货地址未填写");
                        break;
                    }
                }
                break;
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mHandle.removeCallbacksAndMessages(null);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (EventBusConstant.EVENT_ORDER_SUCCESS_FINISH_CONFIRM_UI.equals(str)) {
            finish();
        }
    }

    public void orderFeeApi() {
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ArrayList arrayList = new ArrayList();
            SaleGoodsBean saleGoodsBean = new SaleGoodsBean();
            if (!TextUtils.isEmpty(this.goods_id)) {
                saleGoodsBean.setGoods_id(this.goods_id);
            }
            if (!TextUtils.isEmpty(this.ebook_id)) {
                saleGoodsBean.setEbook_id(this.ebook_id);
            }
            if (!TextUtils.isEmpty(this.course_id)) {
                saleGoodsBean.setCourse_id(this.course_id);
            }
            if (!TextUtils.isEmpty(this.enclosure_id)) {
                saleGoodsBean.setEnclosure_id(this.enclosure_id);
            }
            if (!TextUtils.isEmpty(this.price)) {
                saleGoodsBean.setPrice(this.price);
            }
            String str = "1";
            if (this.isGoods && this.goodsHaveAddress) {
                saleGoodsBean.setQuantity(getAddressNumCount() + "");
            } else {
                saleGoodsBean.setQuantity("1");
            }
            if (!TextUtils.isEmpty(this.couponId)) {
                saleGoodsBean.setCoupon_id(this.couponId);
            }
            if (!TextUtils.isEmpty(this.redPacketId)) {
                saleGoodsBean.setRed_packet_id(this.redPacketId);
            }
            if (!this.is_promotion) {
                str = "0";
            }
            saleGoodsBean.setIs_promotion(str);
            arrayList.add(saleGoodsBean);
            ajaxParams.put("goods_info", new Gson().toJson(arrayList));
        } catch (Exception e2) {
            Log.e(this.TAG, "goods_info: " + e2.getMessage());
        }
        if (!TextUtils.isEmpty(this.upgrade_type)) {
            ajaxParams.put("upgrade_type", this.upgrade_type);
        }
        if (!TextUtils.isEmpty(this.deduction_id)) {
            ajaxParams.put("deduction_id", this.deduction_id);
        }
        if (!TextUtils.isEmpty(getAddress())) {
            ajaxParams.put("address", getAddress());
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.orderFeeAddrMultipleApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.order.OrderConfirmActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showLong(OrderConfirmActivity.this, "金额计算出错，请重新购买！");
                OrderConfirmActivity.this.setButtonEnable(false);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    String strOptString = jSONObject.optString("code");
                    String strOptString2 = jSONObject.optString("data");
                    String strOptString3 = jSONObject.optString("message");
                    if ("200".equals(strOptString)) {
                        OrderFeeAddressMultipleBean orderFeeAddressMultipleBean = (OrderFeeAddressMultipleBean) new Gson().fromJson(strOptString2, OrderFeeAddressMultipleBean.class);
                        OrderConfirmActivity.this.setButtonEnable(true);
                        OrderConfirmActivity.this.initData(orderFeeAddressMultipleBean);
                    } else {
                        OrderConfirmActivity.this.setButtonEnable(false);
                        OrderConfirmActivity orderConfirmActivity = OrderConfirmActivity.this;
                        if (TextUtils.isEmpty(strOptString3)) {
                            strOptString3 = "金额计算出错，请重新购买！";
                        }
                        NewToast.showLong(orderConfirmActivity, strOptString3);
                    }
                } catch (Exception e3) {
                    Log.e(OrderConfirmActivity.this.TAG, "onSuccess: " + e3.getMessage());
                    NewToast.showLong(OrderConfirmActivity.this, "金额计算出错，请重新购买！");
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_order_confirm);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    @Subscribe
    public void onEventMainThread(CouponSelectEvent event) {
        if (event.isCoupon()) {
            this.couponId = event.getSelectId();
        } else {
            this.redPacketId = event.getSelectId();
        }
        orderFeeApi();
    }

    @Subscribe
    public void onEventMainThread(DelAddressEvent event) {
        ArrayList arrayList = new ArrayList();
        boolean z2 = false;
        for (int i2 = 0; i2 < this.listAddress.size(); i2++) {
            if (event.getAddressId().equals(this.listAddress.get(i2).getAddr_id())) {
                z2 = true;
            } else {
                arrayList.add(this.listAddress.get(i2));
            }
        }
        if (z2) {
            this.listAddress.clear();
            this.listAddress.addAll(arrayList);
            if (this.listAddress.isEmpty()) {
                this.listAddress.add(new ShowAddressBean.DataBean());
            }
            this.adapterAddress.setList(this.listAddress);
            if (needAddress() && TextUtils.isEmpty(getAddress())) {
                showAddressHintDialog();
            }
            if (!needAddress() || TextUtils.isEmpty(getAddress())) {
                return;
            }
            orderFeeApi();
        }
    }

    @Subscribe
    public void onEventMainThread(EditAddressEvent event) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.listAddress.size(); i2++) {
            if (event.getAddressId().equals(this.listAddress.get(i2).getAddr_id())) {
                arrayList.add(this.listAddress.get(i2));
            }
        }
        this.listAddress.clear();
        this.listAddress.addAll(arrayList);
        if (this.listAddress.isEmpty()) {
            this.listAddress.add(new ShowAddressBean.DataBean());
        }
        if (needAddress()) {
            this.adapterAddress.setList(this.listAddress);
        }
        orderFeeApi();
    }

    @Subscribe
    public void onEventMainThread(DefAddressEvent event) {
        if (needAddress()) {
            boolean zIsEmpty = TextUtils.isEmpty(getAddress().trim());
            boolean z2 = !TextUtils.isEmpty(event.getAddressId());
            if (zIsEmpty) {
                if (z2) {
                    getAddressData();
                } else {
                    finish();
                }
            }
        }
    }

    @Subscribe
    public void onEventMainThread(WXPayEvent event) {
        int i2 = AnonymousClass6.$SwitchMap$com$psychiatrygarden$event$WXPayStatus[event.getStatus().ordinal()];
        if (i2 == 1) {
            mShowDialog("用户中途取消", "0");
        } else if (i2 == 2) {
            mShowDialog("支付失败", "0");
        } else {
            if (i2 != 3) {
                return;
            }
            mShowDialog("订单支付成功", PayMethodKeyKt.PAY_SUCCESS_CODE);
        }
    }
}

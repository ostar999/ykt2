package com.psychiatrygarden.activity.purchase.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.activity.purchase.beans.ShowAddressBean;
import com.psychiatrygarden.event.DefAddressEvent;
import com.psychiatrygarden.event.DelAddressEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CancelConfrimPop;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ShouhuodizhiActivity extends BaseActivity {
    public static final String FROM_TYPE = "from.type";
    public static final String FROM_TYPE_VALUE_ORDER_CONFIRM = "from.type.order.confirm";
    public static final String FROM_TYPE_VALUE_SETTING = "from.type.setting";
    private TextView addressConfirm;
    public TextView address_tv;
    private CustomEmptyView emptyView;
    public ShowAddressBean mShowAddressBean;
    public CommAdapter<ShowAddressBean.DataBean> mcAdapter;
    public ListView shouhuolist;
    private List<ShowAddressBean.DataBean> addrDataBeans = new ArrayList();
    private String fromType = "";
    private String isDuoxuan = "";
    private boolean orderConfirmNeedAddress = false;
    private String defAddressId = "";
    private final View.OnClickListener onClick = new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.w0
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            this.f13667c.lambda$new$3(view);
        }
    };

    /* renamed from: com.psychiatrygarden.activity.purchase.activity.ShouhuodizhiActivity$3, reason: invalid class name */
    public class AnonymousClass3 extends AjaxCallBack<String> {

        /* renamed from: com.psychiatrygarden.activity.purchase.activity.ShouhuodizhiActivity$3$1, reason: invalid class name */
        public class AnonymousClass1 extends CommAdapter<ShowAddressBean.DataBean> {
            public AnonymousClass1(List mData, Context mcontext, int layoutId) {
                super(mData, mcontext, layoutId);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$convert$0(ShowAddressBean.DataBean dataBean, View view) {
                Intent intent = new Intent(ShouhuodizhiActivity.this.getApplicationContext(), (Class<?>) AddOrEditAddressActivity.class);
                intent.putExtra("dataBean", dataBean);
                ShouhuodizhiActivity.this.startActivity(intent);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$convert$1(ShowAddressBean.DataBean dataBean, int i2) {
                ShouhuodizhiActivity.this.deleteUserAddress(dataBean.getAddr_id(), i2);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$convert$2(final ShowAddressBean.DataBean dataBean, final int i2, View view) {
                new XPopup.Builder(ShouhuodizhiActivity.this.mContext).asCustom(new CancelConfrimPop(ShouhuodizhiActivity.this.mContext, new CancelConfrimPop.ClickIml() { // from class: com.psychiatrygarden.activity.purchase.activity.a1
                    @Override // com.psychiatrygarden.widget.CancelConfrimPop.ClickIml
                    public final void mClickIml() {
                        this.f13565a.lambda$convert$1(dataBean, i2);
                    }
                }, "确认删除地址吗？", "温馨提示", "取消", "确认")).show();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$convert$3(int i2, View view) {
                ShouhuodizhiActivity shouhuodizhiActivity = ShouhuodizhiActivity.this;
                shouhuodizhiActivity.getDefaultData(shouhuodizhiActivity.mShowAddressBean.getData().get(i2).getAddr_id());
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$convert$4(int i2, View view) {
                if ("1".equals(ShouhuodizhiActivity.this.isDuoxuan)) {
                    ShouhuodizhiActivity.this.mShowAddressBean.getData().get(i2).setSelect(!ShouhuodizhiActivity.this.mShowAddressBean.getData().get(i2).isSelect());
                    ShouhuodizhiActivity.this.mcAdapter.notifyDataSetChanged();
                    ShouhuodizhiActivity shouhuodizhiActivity = ShouhuodizhiActivity.this;
                    shouhuodizhiActivity.initData(shouhuodizhiActivity.mShowAddressBean.getData());
                }
            }

            @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
            public void convert(ViewHolder vHolder, final ShowAddressBean.DataBean dataBean, final int position) {
                TextView textView = (TextView) vHolder.getView(R.id.tv_name);
                TextView textView2 = (TextView) vHolder.getView(R.id.tv_phone);
                TextView textView3 = (TextView) vHolder.getView(R.id.tv_address);
                ImageView imageView = (ImageView) vHolder.getView(R.id.img_selected);
                TextView textView4 = (TextView) vHolder.getView(R.id.tvEdit);
                ImageView imageView2 = (ImageView) vHolder.getView(R.id.img_check);
                TextView textView5 = (TextView) vHolder.getView(R.id.tv_default);
                TextView textView6 = (TextView) vHolder.getView(R.id.btn_delete);
                LinearLayout linearLayout = (LinearLayout) vHolder.getView(R.id.ly_set_default);
                if (dataBean.getIs_default().equals("1")) {
                    imageView.setImageResource(SkinManager.getCurrentSkinType(ShouhuodizhiActivity.this) == 0 ? R.drawable.ic_selec_selected_night : R.drawable.ic_select_selected_night);
                    if (SkinManager.getCurrentSkinType(ShouhuodizhiActivity.this.mContext) == 0) {
                        textView5.setTextColor(Color.parseColor("#DD594A"));
                    } else {
                        textView5.setTextColor(Color.parseColor("#B2575C"));
                    }
                } else {
                    imageView.setImageResource(SkinManager.getCurrentSkinType(ShouhuodizhiActivity.this) == 0 ? R.mipmap.ic_select_no_day : R.mipmap.ic_select_no_night);
                    if (SkinManager.getCurrentSkinType(ShouhuodizhiActivity.this.mContext) == 0) {
                        textView5.setTextColor(Color.parseColor("#606060"));
                    } else {
                        textView5.setTextColor(Color.parseColor("#606A8A"));
                    }
                }
                if ("1".equals(ShouhuodizhiActivity.this.isDuoxuan)) {
                    imageView2.setVisibility(0);
                    if (dataBean.isSelect()) {
                        imageView2.setImageResource(SkinManager.getCurrentSkinType(ShouhuodizhiActivity.this) == 0 ? R.drawable.icon_check_day_svg : R.drawable.icon_check_night_svg);
                    } else {
                        imageView2.setImageResource(SkinManager.getCurrentSkinType(ShouhuodizhiActivity.this) == 0 ? R.drawable.icon_no_check_day_svg : R.drawable.icon_no_check_night_svg);
                    }
                } else {
                    imageView2.setVisibility(8);
                }
                textView.setText(dataBean.getName());
                textView2.setText(CommonUtil.BlurTelNumber(dataBean.getMobile()));
                textView3.setText(dataBean.getFull_address());
                textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.b1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f13572c.lambda$convert$0(dataBean, view);
                    }
                });
                textView6.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.c1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f13578c.lambda$convert$2(dataBean, position, view);
                    }
                });
                linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.d1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f13583c.lambda$convert$3(position, view);
                    }
                });
                imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.e1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f13587c.lambda$convert$4(position, view);
                    }
                });
            }
        }

        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(AdapterView adapterView, View view, int i2, long j2) {
            if (!ShouhuodizhiActivity.FROM_TYPE_VALUE_ORDER_CONFIRM.equals(ShouhuodizhiActivity.this.fromType) || "1".equals(ShouhuodizhiActivity.this.isDuoxuan)) {
                return;
            }
            String addr_id = "";
            for (int i3 = 0; i3 < ShouhuodizhiActivity.this.addrDataBeans.size(); i3++) {
                if (!TextUtils.isEmpty(((ShowAddressBean.DataBean) ShouhuodizhiActivity.this.addrDataBeans.get(i3)).getAddr_id().trim())) {
                    addr_id = ((ShowAddressBean.DataBean) ShouhuodizhiActivity.this.addrDataBeans.get(i3)).getAddr_id();
                }
            }
            if (addr_id.equals(ShouhuodizhiActivity.this.mShowAddressBean.getData().get(i2).getAddr_id())) {
                NewToast.showShort(ShouhuodizhiActivity.this, "已选择该地址");
                return;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(ShouhuodizhiActivity.this.mShowAddressBean.getData().get(i2));
            ShouhuodizhiActivity.this.initDataNew(arrayList);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            ShouhuodizhiActivity.this.emptyView.setVisibility(0);
            ShouhuodizhiActivity.this.showEmptyView(1);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass3) s2);
            try {
                ShouhuodizhiActivity.this.mShowAddressBean = (ShowAddressBean) new Gson().fromJson(s2, ShowAddressBean.class);
                if (ShouhuodizhiActivity.this.mShowAddressBean.getCode().equals("200")) {
                    if ("1".equals(ShouhuodizhiActivity.this.isDuoxuan) && ShouhuodizhiActivity.this.addrDataBeans != null && !ShouhuodizhiActivity.this.addrDataBeans.isEmpty()) {
                        for (int i2 = 0; i2 < ShouhuodizhiActivity.this.addrDataBeans.size(); i2++) {
                            if (ShouhuodizhiActivity.this.mShowAddressBean.getData() != null && !ShouhuodizhiActivity.this.mShowAddressBean.getData().isEmpty()) {
                                for (int i3 = 0; i3 < ShouhuodizhiActivity.this.mShowAddressBean.getData().size(); i3++) {
                                    if (((ShowAddressBean.DataBean) ShouhuodizhiActivity.this.addrDataBeans.get(i2)).getAddr_id().equals(ShouhuodizhiActivity.this.mShowAddressBean.getData().get(i3).getAddr_id())) {
                                        ShouhuodizhiActivity.this.mShowAddressBean.getData().get(i3).setSelect(true);
                                        ShouhuodizhiActivity.this.mShowAddressBean.getData().get(i3).setCount(((ShowAddressBean.DataBean) ShouhuodizhiActivity.this.addrDataBeans.get(i2)).getCount());
                                        ShouhuodizhiActivity.this.mShowAddressBean.getData().get(i3).setIs_default(((ShowAddressBean.DataBean) ShouhuodizhiActivity.this.addrDataBeans.get(i2)).getIs_default());
                                    }
                                }
                            }
                        }
                    }
                    String addr_id = "";
                    for (int i4 = 0; i4 < ShouhuodizhiActivity.this.mShowAddressBean.getData().size(); i4++) {
                        if ("1".equals(ShouhuodizhiActivity.this.mShowAddressBean.getData().get(i4).getIs_default())) {
                            addr_id = ShouhuodizhiActivity.this.mShowAddressBean.getData().get(i4).getAddr_id();
                        }
                    }
                    ShouhuodizhiActivity.this.defAddressId = addr_id;
                    if (ShouhuodizhiActivity.this.orderConfirmNeedAddress && !TextUtils.isEmpty(ShouhuodizhiActivity.this.defAddressId)) {
                        EventBus.getDefault().post(new DefAddressEvent(ShouhuodizhiActivity.this.defAddressId));
                    }
                    ShouhuodizhiActivity.this.mcAdapter = new AnonymousClass1(ShouhuodizhiActivity.this.mShowAddressBean.getData(), ShouhuodizhiActivity.this.mContext, R.layout.item_shipping_address);
                    ShouhuodizhiActivity shouhuodizhiActivity = ShouhuodizhiActivity.this;
                    shouhuodizhiActivity.shouhuolist.setAdapter((ListAdapter) shouhuodizhiActivity.mcAdapter);
                    ShouhuodizhiActivity.this.shouhuolist.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.z0
                        @Override // android.widget.AdapterView.OnItemClickListener
                        public final void onItemClick(AdapterView adapterView, View view, int i5, long j2) {
                            this.f13673c.lambda$onSuccess$0(adapterView, view, i5, j2);
                        }
                    });
                    if ("1".equals(ShouhuodizhiActivity.this.isDuoxuan)) {
                        ShouhuodizhiActivity shouhuodizhiActivity2 = ShouhuodizhiActivity.this;
                        shouhuodizhiActivity2.initData(shouhuodizhiActivity2.mShowAddressBean.getData());
                    }
                    if (ShouhuodizhiActivity.this.mShowAddressBean.getData() != null && !ShouhuodizhiActivity.this.mShowAddressBean.getData().isEmpty()) {
                        ShouhuodizhiActivity.this.emptyView.setVisibility(8);
                        return;
                    }
                    ShouhuodizhiActivity.this.emptyView.setVisibility(0);
                    ShouhuodizhiActivity.this.showEmptyView(2);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                ShouhuodizhiActivity.this.emptyView.setVisibility(0);
                ShouhuodizhiActivity.this.showEmptyView(1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteUserAddress(final String addr_id, final int position) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("addr_id", addr_id);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.deleteUserAddress, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.activity.ShouhuodizhiActivity.2
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
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        ShouhuodizhiActivity.this.getListData(false);
                        ShouhuodizhiActivity.this.mcAdapter.getmData().remove(position);
                        ShouhuodizhiActivity.this.mcAdapter.notifyDataSetChanged();
                        EventBus.getDefault().post(new DelAddressEvent(addr_id));
                    } else {
                        ShouhuodizhiActivity.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getDefaultData(String addr_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("addr_id", addr_id);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.msetUserDefaultAddressUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.activity.ShouhuodizhiActivity.1
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
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        ShouhuodizhiActivity.this.getListData(false);
                    } else {
                        ShouhuodizhiActivity.this.AlertToast("选择失败");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getListData(boolean isAdd) {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.mgetUserAddressUrl, new AjaxParams(), new AnonymousClass3());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$2(View view) {
        List<ShowAddressBean.DataBean> list = this.addrDataBeans;
        if (list == null || list.size() == 0) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("datalist", (Serializable) this.addrDataBeans);
        setResult(-1, intent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(View view) {
        if (view.getId() == R.id.address_tv) {
            Intent intent = new Intent(getApplicationContext(), (Class<?>) AddOrEditAddressActivity.class);
            intent.putExtra("dataBean", new ShowAddressBean.DataBean());
            startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$0(View view) {
        setReturnOrderConfirmNullAddress();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$1(View view) {
        setReturnOrderConfirmNullAddress();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEmptyView$4(View view) {
        getListData(false);
    }

    private void setReturnOrderConfirmNullAddress() {
        if (!TextUtils.isEmpty(this.defAddressId) || !this.orderConfirmNeedAddress) {
            finish();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("datalist", new ArrayList());
        setResult(-1, intent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEmptyView(int type) {
        this.emptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.x0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13669c.lambda$showEmptyView$4(view);
            }
        });
        if (type == 1) {
            this.emptyView.setLoadFileResUi(this.mContext);
            this.emptyView.setIsShowReloadBtn(true, "点击刷新页面");
        } else if (type == 2) {
            if (SkinManager.getCurrentSkinType(this) == 1) {
                this.emptyView.setImgEmptyRes(R.drawable.ic_empty_data_address_night);
            } else {
                this.emptyView.setImgEmptyRes(R.drawable.ic_empty_data_address_day);
            }
            this.emptyView.setEmptyTextStr("暂无收货地址");
        }
        this.emptyView.showEmptyView();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.addrDataBeans = (List) getIntent().getExtras().getSerializable("addList");
        this.orderConfirmNeedAddress = getIntent().getBooleanExtra("orderConfirmNeedAddress", false);
    }

    public void initData(List<ShowAddressBean.DataBean> addrDataBeanss) {
        this.addrDataBeans.clear();
        for (int i2 = 0; i2 < addrDataBeanss.size(); i2++) {
            if (addrDataBeanss.get(i2).isSelect()) {
                this.addrDataBeans.add(addrDataBeanss.get(i2));
            }
        }
        this.addressConfirm.setText(String.format(Locale.CHINA, "确定（%d）", Integer.valueOf(this.addrDataBeans.size())));
        this.addressConfirm.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.y0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13671c.lambda$initData$2(view);
            }
        });
    }

    public void initDataNew(List<ShowAddressBean.DataBean> addrDataBeanss) {
        if (this.addrDataBeans == null) {
            this.addrDataBeans = new ArrayList();
        }
        this.addrDataBeans.clear();
        this.addrDataBeans.addAll(addrDataBeanss);
        if (this.addrDataBeans == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("datalist", (Serializable) this.addrDataBeans);
        setResult(-1, intent);
        finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("TianJiaSuccess")) {
            getListData(true);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || event.getRepeatCount() != 0) {
            return false;
        }
        setReturnOrderConfirmNullAddress();
        return true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_shouhuoaddress);
        setSwipeBackEnable(false);
        setTitle("收货地址");
        this.addressConfirm = (TextView) findViewById(R.id.addressConfirm);
        this.shouhuolist = (ListView) findViewById(R.id.shouhuolist);
        this.address_tv = (TextView) findViewById(R.id.address_tv);
        this.emptyView = (CustomEmptyView) findViewById(R.id.emptyViewAddress);
        this.address_tv.setOnClickListener(this.onClick);
        this.mBtnActionbarRight.setVisibility(8);
        Intent intent = getIntent();
        if (intent != null) {
            this.fromType = intent.getStringExtra(FROM_TYPE);
            this.isDuoxuan = intent.getStringExtra("isDuoxuan");
        }
        if ("1".equals(this.isDuoxuan)) {
            this.addressConfirm.setVisibility(0);
            this.addressConfirm.setText("确认（0）");
        } else {
            this.addressConfirm.setVisibility(8);
        }
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.u0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13662c.lambda$setContentView$0(view);
            }
        });
        getListData(false);
        this.shouhuolist.setFooterDividersEnabled(true);
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.v0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13665c.lambda$setContentView$1(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

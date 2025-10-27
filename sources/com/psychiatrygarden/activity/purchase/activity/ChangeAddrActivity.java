package com.psychiatrygarden.activity.purchase.activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.purchase.beans.AreaGetPCData;
import com.psychiatrygarden.activity.purchase.beans.ShowAddressBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.LoopView;
import com.psychiatrygarden.widget.PagePickerView;
import com.psychiatrygarden.widget.SelectAddressPop;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ChangeAddrActivity extends BaseActivity {
    private ImageView imgDefault;
    private LinearLayout llSetDefault;
    private String mCroV_id;
    public ShowAddressBean.DataBean mDataBean;
    private String mProV_id;
    private int noSelectResId;
    private PagePickerView pagePickerView;
    private int selectResId;
    TextView t2;
    EditText tt2;
    TextView tv_baocun;
    EditText tv_phone;
    EditText tv_shouhuoren;
    EditText tv_youxiangbainhao;
    private boolean selectStatus = false;
    View.OnClickListener onclick = new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.ChangeAddrActivity.1
        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
            if (CommonUtil.isFastClick()) {
                return;
            }
            int id = v2.getId();
            if (id != R.id.tv_save) {
                if (id == R.id.r3) {
                    ChangeAddrActivity.this.getAreaData();
                    return;
                }
                return;
            }
            if (ChangeAddrActivity.this.tv_shouhuoren.getText().toString().trim().equals("")) {
                ChangeAddrActivity.this.AlertToast("请填写收货人姓名");
                return;
            }
            if (ChangeAddrActivity.this.tv_phone.getText().toString().trim().equals("")) {
                ChangeAddrActivity.this.AlertToast("请填写收货人手机号");
                return;
            }
            if (ChangeAddrActivity.this.tv_phone.getText().toString().trim().length() > 11 || ChangeAddrActivity.this.tv_phone.getText().toString().trim().length() < 11) {
                ChangeAddrActivity.this.AlertToast("手机号为11位，请输入正确的手机号");
                return;
            }
            if (ChangeAddrActivity.this.t2.getText().toString().trim().equals("")) {
                ChangeAddrActivity.this.AlertToast("请选择您选在的省市县");
                return;
            }
            if (ChangeAddrActivity.this.tt2.getText().toString().trim().equals("")) {
                ChangeAddrActivity.this.AlertToast("请填写收货人详细地址");
                return;
            }
            if (ChangeAddrActivity.this.tv_youxiangbainhao.getText().toString().trim().equals("")) {
                ChangeAddrActivity.this.AlertToast("请填写收货地址的邮编");
            } else if (ChangeAddrActivity.this.tv_youxiangbainhao.getText().toString().trim().length() > 6 || ChangeAddrActivity.this.tv_youxiangbainhao.getText().toString().trim().length() < 6) {
                ChangeAddrActivity.this.AlertToast("邮政编号为6位，请输入正确的邮政编号");
            } else {
                ChangeAddrActivity.this.getSaveAddress();
            }
        }
    };
    private final ArrayList<String> str_arr_one = new ArrayList<>();
    private final ArrayList<String> str_arr_two = new ArrayList<>();
    private int one_location = 0;
    private int two_location = 0;

    /* renamed from: com.psychiatrygarden.activity.purchase.activity.ChangeAddrActivity$3, reason: invalid class name */
    public class AnonymousClass3 extends AjaxCallBack<String> {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(String str, String str2, String str3, String str4) {
            if (TextUtils.equals(str, str2)) {
                ChangeAddrActivity.this.t2.setText(str2);
            } else {
                ChangeAddrActivity.this.t2.setText(str + str2);
            }
            ChangeAddrActivity.this.mProV_id = str4;
            ChangeAddrActivity.this.mCroV_id = str3;
        }

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
                AreaGetPCData areaGetPCData = (AreaGetPCData) new Gson().fromJson(s2, AreaGetPCData.class);
                if (areaGetPCData.getCode().equals("200")) {
                    if (areaGetPCData.getData() != null && !areaGetPCData.getData().isEmpty() && areaGetPCData.getData().get(0).getSub() != null && !areaGetPCData.getData().get(0).getSub().isEmpty()) {
                        new XPopup.Builder(ChangeAddrActivity.this.mContext).asCustom(new SelectAddressPop(ChangeAddrActivity.this.mContext, areaGetPCData, new SelectAddressPop.OnSelectListener() { // from class: com.psychiatrygarden.activity.purchase.activity.p
                            @Override // com.psychiatrygarden.widget.SelectAddressPop.OnSelectListener
                            public final void onSelect(String str, String str2, String str3, String str4) {
                                this.f13641a.lambda$onSuccess$0(str, str2, str3, str4);
                            }
                        })).show();
                    }
                    return;
                }
                ChangeAddrActivity.this.AlertToast("获取地址失败");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getAreaData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.mareaUrl, new AjaxParams(), new AnonymousClass3());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        boolean z2 = !this.selectStatus;
        this.selectStatus = z2;
        this.imgDefault.setImageResource(z2 ? this.selectResId : this.noSelectResId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPagePickerTwo$1(int i2, AreaGetPCData areaGetPCData, LoopView loopView, int i3) {
        this.one_location = i3;
        if (i2 == 1) {
            List<AreaGetPCData.DataBean.SubBean> sub = areaGetPCData.getData().get(i3).getSub();
            this.str_arr_two.clear();
            for (int i4 = 0; i4 < sub.size(); i4++) {
                this.str_arr_two.add(sub.get(i4).getTitle());
            }
        }
        this.pagePickerView.setData2(this.str_arr_two);
        this.pagePickerView.labelView2.getCurrentPosition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPagePickerTwo$2(LoopView loopView, int i2) {
        this.two_location = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPagePickerTwo$3(int i2, AreaGetPCData areaGetPCData, Dialog dialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (i2 == 1) {
            List<AreaGetPCData.DataBean.SubBean> sub = areaGetPCData.getData().get(this.one_location).getSub();
            this.mCroV_id = sub.get(this.two_location).getArea_id();
            this.mProV_id = areaGetPCData.getData().get(this.one_location).getArea_id();
            this.t2.setText(String.format("%s%s", areaGetPCData.getData().get(this.one_location).getTitle(), sub.get(this.two_location).getTitle()));
        }
        dialog.dismiss();
    }

    private void showPagePickerTwo(final int type, final AreaGetPCData mAreaGetPCData) {
        this.one_location = 0;
        this.two_location = 0;
        this.pagePickerView = new PagePickerView(this.mContext, true);
        this.str_arr_one.clear();
        this.str_arr_two.clear();
        if (type == 1) {
            for (int i2 = 0; i2 < mAreaGetPCData.getData().size(); i2++) {
                this.str_arr_one.add(mAreaGetPCData.getData().get(i2).getTitle());
            }
            List<AreaGetPCData.DataBean.SubBean> sub = mAreaGetPCData.getData().get(0).getSub();
            for (int i3 = 0; i3 < sub.size(); i3++) {
                this.str_arr_two.add(sub.get(i3).getTitle());
            }
        }
        this.pagePickerView.setData(this.str_arr_one);
        this.pagePickerView.setData2(this.str_arr_two);
        final Dialog dialog = new Dialog(this, R.style.LabelPickerDialog);
        if (this.pagePickerView.getParent() != null) {
            ((ViewGroup) this.pagePickerView.getParent()).removeAllViews();
        }
        dialog.setContentView(this.pagePickerView);
        Window window = dialog.getWindow();
        window.setGravity(80);
        window.setBackgroundDrawableResource(R.color.dialog_transparent_bg);
        dialog.show();
        this.pagePickerView.labelView.setListener(new LoopView.LoopListener() { // from class: com.psychiatrygarden.activity.purchase.activity.l
            @Override // com.psychiatrygarden.widget.LoopView.LoopListener
            public final void onItemSelect(LoopView loopView, int i4) {
                this.f13618c.lambda$showPagePickerTwo$1(type, mAreaGetPCData, loopView, i4);
            }
        });
        this.pagePickerView.labelView2.setListener(new LoopView.LoopListener() { // from class: com.psychiatrygarden.activity.purchase.activity.m
            @Override // com.psychiatrygarden.widget.LoopView.LoopListener
            public final void onItemSelect(LoopView loopView, int i4) {
                this.f13625c.lambda$showPagePickerTwo$2(loopView, i4);
            }
        });
        this.pagePickerView.findViewById(R.id.view_wheeltime_sure_btn).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.n
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13628c.lambda$showPagePickerTwo$3(type, mAreaGetPCData, dialog, view);
            }
        });
        this.pagePickerView.findViewById(R.id.view_wheeltime_cancel_btn).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void getSaveAddress() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("addr_id", this.mDataBean.getAddr_id());
        ajaxParams.put(ActSubmitGoodsComment.EXTRA_DATA_ORDER_NO, "" + getIntent().getExtras().getString(ActSubmitGoodsComment.EXTRA_DATA_ORDER_NO));
        ajaxParams.put("province", this.mProV_id);
        ajaxParams.put("city", this.mCroV_id);
        ajaxParams.put("street", this.tt2.getText().toString());
        ajaxParams.put("mobile", this.tv_phone.getText().toString());
        ajaxParams.put("name", this.tv_shouhuoren.getText().toString());
        ajaxParams.put("postcode", this.tv_youxiangbainhao.getText().toString());
        ajaxParams.put("id", "" + this.mDataBean.getId());
        ajaxParams.put("is_default", this.selectStatus ? "1" : "0");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.editReceivingAddressApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.activity.ChangeAddrActivity.2
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
                        EventBus.getDefault().post("REFRESH_Addr");
                        ChangeAddrActivity.this.finish();
                    } else {
                        ChangeAddrActivity.this.AlertToast("" + jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.llSetDefault = (LinearLayout) findViewById(R.id.ll_set_default);
        this.imgDefault = (ImageView) findViewById(R.id.iv_default);
        this.tv_shouhuoren = (EditText) findViewById(R.id.tv_shouhuoren);
        this.tv_phone = (EditText) findViewById(R.id.tv_phone);
        this.tv_youxiangbainhao = (EditText) findViewById(R.id.tv_post_code);
        this.t2 = (TextView) findViewById(R.id.tv_select_address);
        this.tt2 = (EditText) findViewById(R.id.tt2);
        this.tv_baocun = (TextView) findViewById(R.id.tv_save);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.r3);
        this.tt2.setOnClickListener(this.onclick);
        this.tv_baocun.setOnClickListener(this.onclick);
        linearLayout.setOnClickListener(this.onclick);
        this.tv_shouhuoren.setText(this.mDataBean.getName());
        this.tv_phone.setText(this.mDataBean.getMobile());
        this.tv_youxiangbainhao.setText(this.mDataBean.getPostcode());
        TextView textView = this.t2;
        Object[] objArr = new Object[2];
        objArr[0] = this.mDataBean.getProvince_title() == null ? "" : this.mDataBean.getProvince_title();
        objArr[1] = this.mDataBean.getCity_title() != null ? this.mDataBean.getCity_title() : "";
        textView.setText(String.format("%s%s", objArr));
        this.tt2.setText(this.mDataBean.getStreet());
        this.mProV_id = this.mDataBean.getProvince();
        this.mCroV_id = this.mDataBean.getCity();
        this.selectResId = SkinManager.getCurrentSkinType(this) == 0 ? R.drawable.ic_selec_selected_night : R.drawable.ic_select_selected_night;
        this.noSelectResId = SkinManager.getCurrentSkinType(this) == 0 ? R.mipmap.ic_select_no_day : R.mipmap.ic_select_no_night;
        ShowAddressBean.DataBean dataBean = this.mDataBean;
        if (dataBean != null) {
            this.selectStatus = "1".equals(dataBean.getIs_default());
        }
        this.imgDefault.setImageResource(this.selectStatus ? this.selectResId : this.noSelectResId);
        this.llSetDefault.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13613c.lambda$init$0(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mDataBean = (ShowAddressBean.DataBean) getIntent().getSerializableExtra("dataBean");
        setContentView(R.layout.activity_add_edit_address);
        setTitle("修改收货地址");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

package com.psychiatrygarden.activity.purchase.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.hutool.core.util.RandomUtil;
import com.aliyun.svideo.common.utils.FastClickUtil;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.purchase.beans.AreaGetPCData;
import com.psychiatrygarden.activity.purchase.beans.ShowAddressBean;
import com.psychiatrygarden.event.EditAddressEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.widget.SelectAddressPop;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class AddOrEditAddressActivity extends BaseActivity {
    private boolean defaultAddress;
    private boolean hasGetAddress;
    private AreaGetPCData mAreaGetPCData;
    private String mCroV_id;
    public ShowAddressBean.DataBean mDataBean;
    private ShowAddressBean.DataBean mDataBeanCallback;
    private String mProV_id;
    private final View.OnClickListener onclick = new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.AddOrEditAddressActivity.1
        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
            if (CommonUtil.isFastClick()) {
            }
            switch (v2.getId()) {
                case R.id.iv_default /* 2131364053 */:
                case R.id.ll_set_default /* 2131364886 */:
                    ImageView imageView = (ImageView) AddOrEditAddressActivity.this.findViewById(R.id.iv_default);
                    TypedArray typedArrayObtainStyledAttributes = AddOrEditAddressActivity.this.getTheme().obtainStyledAttributes(new int[]{R.attr.ic_circle_anonymous_single_select, R.attr.ic_circle_anonymous_single_not_select});
                    if (AddOrEditAddressActivity.this.defaultAddress) {
                        imageView.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(1));
                    } else {
                        imageView.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(0));
                    }
                    AddOrEditAddressActivity.this.defaultAddress = !r4.defaultAddress;
                    typedArrayObtainStyledAttributes.recycle();
                    break;
                case R.id.r3 /* 2131366254 */:
                    if (!FastClickUtil.isFastClick()) {
                        if (!AddOrEditAddressActivity.this.hasGetAddress) {
                            AddOrEditAddressActivity.this.getAreaData();
                            break;
                        } else if (AddOrEditAddressActivity.this.mAreaGetPCData != null) {
                            AddOrEditAddressActivity addOrEditAddressActivity = AddOrEditAddressActivity.this;
                            addOrEditAddressActivity.showAddressDialog(addOrEditAddressActivity.mAreaGetPCData);
                            break;
                        }
                    }
                    break;
                case R.id.tv_save /* 2131368519 */:
                    if (!AddOrEditAddressActivity.this.tv_shouhuoren.getText().toString().trim().equals("")) {
                        if (!AddOrEditAddressActivity.this.tv_phone.getText().toString().trim().equals("")) {
                            if (AddOrEditAddressActivity.this.tv_phone.getText().toString().trim().length() <= 11 && AddOrEditAddressActivity.this.tv_phone.getText().toString().trim().length() >= 11) {
                                if (!AddOrEditAddressActivity.this.tv_select_address.getText().toString().trim().equals("")) {
                                    if (!AddOrEditAddressActivity.this.tt2.getText().toString().trim().equals("")) {
                                        String strTrim = AddOrEditAddressActivity.this.tv_youxiangbainhao.getText().toString().trim();
                                        if (!TextUtils.isEmpty(strTrim) && strTrim.length() != 6) {
                                            AddOrEditAddressActivity.this.AlertToast("邮政编号为6位，请输入正确的邮政编号");
                                            break;
                                        } else if (!FastClickUtil.isFastClick()) {
                                            AddOrEditAddressActivity.this.addSaveAddress();
                                            break;
                                        }
                                    } else {
                                        AddOrEditAddressActivity.this.AlertToast("请填写收货人详细地址");
                                        break;
                                    }
                                } else {
                                    AddOrEditAddressActivity.this.AlertToast("请选择您选在的省市县");
                                    break;
                                }
                            } else {
                                AddOrEditAddressActivity.this.AlertToast("手机号为11位，请输入正确的手机号");
                                break;
                            }
                        } else {
                            AddOrEditAddressActivity.this.AlertToast("请填写收货人手机号");
                            break;
                        }
                    } else {
                        AddOrEditAddressActivity.this.AlertToast("请填写收货人姓名");
                        break;
                    }
                    break;
            }
        }
    };
    EditText tt2;
    TextView tv_baocun;
    EditText tv_phone;
    TextView tv_select_address;
    EditText tv_shouhuoren;
    EditText tv_youxiangbainhao;

    /* JADX INFO: Access modifiers changed from: private */
    public void getAreaData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.mareaUrl, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.activity.AddOrEditAddressActivity.3
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
                    AddOrEditAddressActivity.this.mAreaGetPCData = (AreaGetPCData) new Gson().fromJson(s2, AreaGetPCData.class);
                    if (AddOrEditAddressActivity.this.mAreaGetPCData.getCode().equals("200")) {
                        if (AddOrEditAddressActivity.this.mAreaGetPCData.getData() != null && !AddOrEditAddressActivity.this.mAreaGetPCData.getData().isEmpty() && AddOrEditAddressActivity.this.mAreaGetPCData.getData().get(0).getSub() != null && !AddOrEditAddressActivity.this.mAreaGetPCData.getData().get(0).getSub().isEmpty()) {
                            AddOrEditAddressActivity.this.hasGetAddress = true;
                            AddOrEditAddressActivity addOrEditAddressActivity = AddOrEditAddressActivity.this;
                            addOrEditAddressActivity.showAddressDialog(addOrEditAddressActivity.mAreaGetPCData);
                        }
                        return;
                    }
                    AddOrEditAddressActivity.this.AlertToast("获取地址失败");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAddressDialog$0(String str, String str2, String str3, String str4) {
        if (TextUtils.equals(str, str2)) {
            this.tv_select_address.setText(str2);
        } else {
            this.tv_select_address.setText(str + str2);
        }
        this.mProV_id = str4;
        this.mCroV_id = str3;
        ShowAddressBean.DataBean dataBean = this.mDataBean;
        if (dataBean != null) {
            dataBean.setCity(str3);
            this.mDataBean.setCity_title(str2);
            this.mDataBean.setProvince(str4);
            this.mDataBean.setProvince_title(str);
            return;
        }
        this.mDataBeanCallback.setCity(str3);
        this.mDataBeanCallback.setCity_title(str2);
        this.mDataBeanCallback.setProvince(str4);
        this.mDataBeanCallback.setProvince_title(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAddressDialog(AreaGetPCData data) {
        new XPopup.Builder(this.mContext).asCustom(new SelectAddressPop(this.mContext, data, new SelectAddressPop.OnSelectListener() { // from class: com.psychiatrygarden.activity.purchase.activity.i
            @Override // com.psychiatrygarden.widget.SelectAddressPop.OnSelectListener
            public final void onSelect(String str, String str2, String str3, String str4) {
                this.f13603a.lambda$showAddressDialog$0(str, str2, str3, str4);
            }
        })).show();
    }

    public void addSaveAddress() {
        AjaxParams ajaxParams = new AjaxParams();
        ShowAddressBean.DataBean dataBean = this.mDataBean;
        if (dataBean != null && !TextUtils.equals(dataBean.getAddr_id(), "")) {
            ajaxParams.put("addr_id", this.mDataBean.getAddr_id());
        }
        ajaxParams.put("province", this.mProV_id);
        ajaxParams.put("is_default", this.defaultAddress ? "1" : "0");
        ajaxParams.put("city", this.mCroV_id);
        ajaxParams.put("street", this.tt2.getText().toString());
        ajaxParams.put("mobile", this.tv_phone.getText().toString());
        ajaxParams.put("name", this.tv_shouhuoren.getText().toString());
        ajaxParams.put("postcode", this.tv_youxiangbainhao.getText().toString());
        ShowAddressBean.DataBean dataBean2 = this.mDataBean;
        if (dataBean2 != null) {
            dataBean2.setIs_default(this.defaultAddress ? "1" : "0");
            this.mDataBean.setName(this.tv_shouhuoren.getText().toString());
            this.mDataBean.setMobile(this.tv_phone.getText().toString());
            this.mDataBean.setStreet(this.tt2.getText().toString());
        } else {
            this.mDataBeanCallback.setName(this.tv_shouhuoren.getText().toString());
            this.mDataBeanCallback.setMobile(this.tv_phone.getText().toString());
            this.mDataBeanCallback.setStreet(this.tt2.getText().toString());
            this.mDataBeanCallback.setIs_default(this.defaultAddress ? "1" : "0");
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.meditUserAddressUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.activity.AddOrEditAddressActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                AddOrEditAddressActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                AddOrEditAddressActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    AddOrEditAddressActivity.this.hideProgressDialog();
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!jSONObject.optString("code").equals("200")) {
                        AddOrEditAddressActivity.this.AlertToast("" + jSONObject.optString("message"));
                        return;
                    }
                    EventBus.getDefault().post("TianJiaSuccess");
                    if (!TextUtils.isEmpty(AddOrEditAddressActivity.this.mDataBean.getName()) && !TextUtils.isEmpty(AddOrEditAddressActivity.this.mDataBean.getAddr_id()) && !TextUtils.isEmpty(AddOrEditAddressActivity.this.mDataBean.getMobile()) && !TextUtils.isEmpty(AddOrEditAddressActivity.this.mDataBean.getFull_address())) {
                        EventBus.getDefault().post(new EditAddressEvent(AddOrEditAddressActivity.this.mDataBean.getAddr_id(), AddOrEditAddressActivity.this.mDataBean.getFull_address(), AddOrEditAddressActivity.this.mDataBean.getMobile(), AddOrEditAddressActivity.this.mDataBean.getName()));
                    }
                    Intent intent = new Intent();
                    AddOrEditAddressActivity addOrEditAddressActivity = AddOrEditAddressActivity.this;
                    ShowAddressBean.DataBean dataBean3 = addOrEditAddressActivity.mDataBean;
                    if (dataBean3 == null) {
                        intent.putExtra("dataBean", addOrEditAddressActivity.mDataBeanCallback);
                    } else {
                        intent.putExtra("dataBean", dataBean3);
                    }
                    AddOrEditAddressActivity.this.setResult(-1, intent);
                    AddOrEditAddressActivity.this.finish();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.tv_shouhuoren = (EditText) findViewById(R.id.tv_shouhuoren);
        this.tv_phone = (EditText) findViewById(R.id.tv_phone);
        this.tv_youxiangbainhao = (EditText) findViewById(R.id.tv_post_code);
        this.tv_select_address = (TextView) findViewById(R.id.tv_select_address);
        this.tt2 = (EditText) findViewById(R.id.tt2);
        this.tv_baocun = (TextView) findViewById(R.id.tv_save);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.r3);
        this.tt2.setOnClickListener(this.onclick);
        this.tv_baocun.setOnClickListener(this.onclick);
        linearLayout.setOnClickListener(this.onclick);
        findViewById(R.id.iv_default).setOnClickListener(this.onclick);
        findViewById(R.id.ll_set_default).setOnClickListener(this.onclick);
        this.tv_phone.setKeyListener(DigitsKeyListener.getInstance(RandomUtil.BASE_NUMBER));
        this.tv_youxiangbainhao.setKeyListener(DigitsKeyListener.getInstance(RandomUtil.BASE_NUMBER));
        ShowAddressBean.DataBean dataBean = this.mDataBean;
        if (dataBean != null) {
            this.tv_shouhuoren.setText(dataBean.getName());
            this.tv_phone.setText(this.mDataBean.getMobile());
            if (TextUtils.isEmpty(this.mDataBean.getPostcode()) || "0".equals(this.mDataBean.getPostcode())) {
                this.tv_youxiangbainhao.setText("");
            } else {
                this.tv_youxiangbainhao.setText(this.mDataBean.getPostcode());
            }
            TextView textView = this.tv_select_address;
            Object[] objArr = new Object[2];
            objArr[0] = this.mDataBean.getProvince_title() == null ? "" : this.mDataBean.getProvince_title();
            objArr[1] = this.mDataBean.getCity_title() != null ? this.mDataBean.getCity_title() : "";
            textView.setText(String.format("%s%s", objArr));
            this.tt2.setText(this.mDataBean.getStreet());
            this.mProV_id = this.mDataBean.getProvince();
            this.mCroV_id = this.mDataBean.getCity();
            if (TextUtils.equals("1", this.mDataBean.getIs_default())) {
                this.defaultAddress = true;
                TypedArray typedArrayObtainStyledAttributes = getTheme().obtainStyledAttributes(new int[]{R.attr.ic_circle_anonymous_single_select, R.attr.ic_circle_anonymous_single_not_select});
                ((ImageView) findViewById(R.id.iv_default)).setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(0));
                typedArrayObtainStyledAttributes.recycle();
            }
        }
        this.mDataBeanCallback = new ShowAddressBean.DataBean();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mDataBean = (ShowAddressBean.DataBean) getIntent().getSerializableExtra("dataBean");
        setContentView(R.layout.activity_add_edit_address);
        ShowAddressBean.DataBean dataBean = this.mDataBean;
        if (dataBean == null || TextUtils.isEmpty(dataBean.getMobile()) || TextUtils.isEmpty(this.mDataBean.getName())) {
            setTitle("添加收货地址");
        } else {
            setTitle("编辑收货地址");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

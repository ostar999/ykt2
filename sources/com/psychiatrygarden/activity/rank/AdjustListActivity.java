package com.psychiatrygarden.activity.rank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.mobile.auth.gatewayauth.Constant;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.adapter.AdjustListAdapter;
import com.psychiatrygarden.bean.AdjustInfoBean;
import com.psychiatrygarden.bean.QrDialogBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.dialog.CommonImgDialog;
import java.util.ArrayList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0013H\u0016J\b\u0010\u0015\u001a\u00020\u0013H\u0002J\u0010\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\"\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u00182\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0014J\u0012\u0010\u001e\u001a\u00020\u00132\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\b\u0010!\u001a\u00020\u0013H\u0016J\b\u0010\"\u001a\u00020\u0013H\u0016J\u0010\u0010#\u001a\u00020\u00132\u0006\u0010$\u001a\u00020%H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u0005\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\r\u001a\u0004\u0018\u00010\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\n\u001a\u0004\b\u000f\u0010\u0010¨\u0006&"}, d2 = {"Lcom/psychiatrygarden/activity/rank/AdjustListActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "adapter", "Lcom/psychiatrygarden/adapter/AdjustListAdapter;", "adjustRv", "Landroidx/recyclerview/widget/RecyclerView;", "getAdjustRv", "()Landroidx/recyclerview/widget/RecyclerView;", "adjustRv$delegate", "Lkotlin/Lazy;", "isLoading", "", "titleAdjustTv", "Landroid/widget/TextView;", "getTitleAdjustTv", "()Landroid/widget/TextView;", "titleAdjustTv$delegate", "getQrData", "", "init", "loadData", "navigateToAdjustEntrance", "pos", "", "onActivityResult", Constant.LOGIN_ACTIVITY_REQUEST_CODE, "resultCode", "data", "Landroid/content/Intent;", "onEventMainThread", "str", "", "setContentView", "setListenerForWidget", "updateUI", "adjustInfoBean", "Lcom/psychiatrygarden/bean/AdjustInfoBean;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class AdjustListActivity extends BaseActivity {

    @Nullable
    private AdjustListAdapter adapter;
    private boolean isLoading;

    /* renamed from: adjustRv$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy adjustRv = LazyKt__LazyJVMKt.lazy(new Function0<RecyclerView>() { // from class: com.psychiatrygarden.activity.rank.AdjustListActivity$adjustRv$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final RecyclerView invoke() {
            return (RecyclerView) this.this$0.findViewById(R.id.adjust_rv);
        }
    });

    /* renamed from: titleAdjustTv$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy titleAdjustTv = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.psychiatrygarden.activity.rank.AdjustListActivity$titleAdjustTv$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final TextView invoke() {
            return (TextView) this.this$0.findViewById(R.id.title_adjust_tv);
        }
    });

    private final RecyclerView getAdjustRv() {
        return (RecyclerView) this.adjustRv.getValue();
    }

    private final void getQrData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getAdjustQrCodeInfo, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.rank.AdjustListActivity.getQrData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@Nullable String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    if (Intrinsics.areEqual(new JSONObject(s2).optString("code"), "200")) {
                        QrDialogBean qrDialogBean = (QrDialogBean) new Gson().fromJson(s2, QrDialogBean.class);
                        CommonImgDialog.Builder builder = new CommonImgDialog.Builder(AdjustListActivity.this);
                        String title = qrDialogBean.getData().getTitle();
                        Intrinsics.checkNotNullExpressionValue(title, "qrDialogBean.data.title");
                        boolean z2 = true;
                        CommonImgDialog.Builder dialogBg = builder.setSubTitle(title).setRightText("保存并跳转微信").setConfirmView(R.drawable.shape_app_theme_corners_12).setDialogBg(SkinManager.getCurrentSkinType(AdjustListActivity.this) == 1 ? R.color.color_blue_theme_bg : R.color.white_color);
                        String qr_code = qrDialogBean.getData().getQr_code();
                        Intrinsics.checkNotNullExpressionValue(qr_code, "qrDialogBean.data.qr_code");
                        CommonImgDialog.Builder dialogQr = dialogBg.setDialogQr(qr_code);
                        if (SkinManager.getCurrentSkinType(AdjustListActivity.this) != 1) {
                            z2 = false;
                        }
                        CommonImgDialog.Builder isNight = dialogQr.setIsNight(z2);
                        String wechat = qrDialogBean.getData().getWechat();
                        Intrinsics.checkNotNullExpressionValue(wechat, "qrDialogBean.data.wechat");
                        CommonImgDialog.Builder wxCode = isNight.setWxCode(wechat);
                        final AdjustListActivity adjustListActivity = AdjustListActivity.this;
                        wxCode.setRightClick(new Function1<BasicDialog, Unit>() { // from class: com.psychiatrygarden.activity.rank.AdjustListActivity$getQrData$1$onSuccess$1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(BasicDialog basicDialog) {
                                invoke2(basicDialog);
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(@Nullable BasicDialog basicDialog) {
                                Intent launchIntentForPackage = adjustListActivity.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                                if (launchIntentForPackage != null) {
                                    adjustListActivity.startActivity(launchIntentForPackage);
                                } else {
                                    ToastUtil.shortToast(adjustListActivity, "没有安装微信");
                                }
                            }
                        }).show();
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    private final TextView getTitleAdjustTv() {
        return (TextView) this.titleAdjustTv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(AdjustListActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getQrData();
    }

    private final void loadData() {
        if (this.isLoading) {
            return;
        }
        this.isLoading = true;
        AjaxParams ajaxParams = new AjaxParams();
        Bundle extras = getIntent().getExtras();
        ajaxParams.put("version", Intrinsics.areEqual(extras != null ? extras.getString("type") : null, "haveimg") ? "2" : "1");
        YJYHttpUtils.post(this, NetworkRequestsURL.getAdjustInfo, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.rank.AdjustListActivity.loadData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable error, int errorNo, @Nullable String strMsg) {
                super.onFailure(error, errorNo, strMsg);
                AdjustListActivity.this.isLoading = false;
                if (error != null) {
                    error.printStackTrace();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@Nullable String result) {
                super.onSuccess((C06011) result);
                AdjustListActivity.this.isLoading = false;
                if (result != null) {
                    try {
                        AdjustListActivity adjustListActivity = AdjustListActivity.this;
                        AdjustInfoBean adjustInfoBean = (AdjustInfoBean) new Gson().fromJson(new JSONObject(result).toString(), AdjustInfoBean.class);
                        Intrinsics.checkNotNullExpressionValue(adjustInfoBean, "adjustInfoBean");
                        adjustListActivity.updateUI(adjustInfoBean);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void navigateToAdjustEntrance(int pos) {
        Intent intent = new Intent(this, (Class<?>) AdjustEntranceActivity.class);
        Bundle extras = getIntent().getExtras();
        String str = "2";
        intent.putExtra("version", Intrinsics.areEqual(extras != null ? extras.getString("type") : null, "haveimg") ? "2" : "1");
        if (pos == 0) {
            str = "1";
        } else if (pos != 1) {
            str = "3";
        }
        intent.putExtra("type", str);
        startActivityForResult(intent, 19);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateUI(AdjustInfoBean adjustInfoBean) {
        RecyclerView adjustRv;
        TextView titleAdjustTv = getTitleAdjustTv();
        if (titleAdjustTv != null) {
            titleAdjustTv.setText(adjustInfoBean.getData().getTitle());
        }
        RecyclerView adjustRv2 = getAdjustRv();
        if ((adjustRv2 != null ? adjustRv2.getLayoutManager() : null) == null && (adjustRv = getAdjustRv()) != null) {
            adjustRv.setLayoutManager(new LinearLayoutManager(this.mContext, 1, false));
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(adjustInfoBean.getData().getAdjust_vol_1());
        arrayList.add(adjustInfoBean.getData().getAdjust_vol_2());
        arrayList.add(adjustInfoBean.getData().getAdjust_vol_3());
        AdjustListAdapter adjustListAdapter = this.adapter;
        if (adjustListAdapter != null) {
            if (adjustListAdapter != null) {
                adjustListAdapter.updateData(arrayList);
                return;
            }
            return;
        }
        Bundle extras = getIntent().getExtras();
        AdjustListAdapter adjustListAdapter2 = new AdjustListAdapter(arrayList, this, Intrinsics.areEqual(extras != null ? extras.getString("type") : null, "haveimg") ? "2" : "1");
        adjustListAdapter2.setOnItemClickListener(new AdjustListAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.rank.AdjustListActivity$updateUI$1$1
            @Override // com.psychiatrygarden.adapter.AdjustListAdapter.OnItemClickListener
            public void setItemClickAction(int pos) {
                this.this$0.navigateToAdjustEntrance(pos);
            }
        });
        this.adapter = adjustListAdapter2;
        RecyclerView adjustRv3 = getAdjustRv();
        if (adjustRv3 == null) {
            return;
        }
        adjustRv3.setAdapter(this.adapter);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("调剂志愿");
        loadData();
        this.mBtnActionbarRight.setVisibility(8);
        this.iv_actionbar_right.setVisibility(0);
        if (SkinManager.getCurrentSkinType(this) == 1) {
            this.iv_actionbar_right.setImageResource(R.drawable.qr_icon_night);
        } else {
            this.iv_actionbar_right.setImageResource(R.drawable.qr_icon);
        }
        this.iv_actionbar_right.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rank.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AdjustListActivity.init$lambda$0(this.f13766c, view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 19) {
            loadData();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_adjust_list);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}

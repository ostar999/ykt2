package com.yikaobang.yixue.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import com.psychiatrygarden.event.WXPayEvent;
import com.psychiatrygarden.event.WXPayStatus;
import com.psychiatrygarden.utils.SdkConstant;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import de.greenrobot.event.EventBus;

/* loaded from: classes7.dex */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXPayEntryActivity";
    private IWXAPI api;

    @Override // android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        WXAPIFactory.createWXAPI(this, SdkConstant.getWxAppId()).handleIntent(getIntent(), this);
    }

    @Override // android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (this.api == null) {
            this.api = WXAPIFactory.createWXAPI(this, SdkConstant.getWxAppId(), false);
        }
        this.api.handleIntent(intent, this);
    }

    @Override // com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
    public void onReq(BaseReq baseReq) {
    }

    @Override // com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
    public void onResp(BaseResp resp) {
        Log.d(TAG, "支付回调结果" + resp.errCode + "--" + resp.errStr);
        if (resp.getType() == 5) {
            int i2 = resp.errCode;
            if (i2 == -2) {
                EventBus.getDefault().post(new WXPayEvent(WXPayStatus.CANCEL));
                finish();
            } else if (i2 == -1) {
                EventBus.getDefault().post(new WXPayEvent(WXPayStatus.FAILED));
                finish();
            } else {
                if (i2 != 0) {
                    return;
                }
                EventBus.getDefault().post(new WXPayEvent(WXPayStatus.SUCCESS));
                finish();
            }
        }
    }
}

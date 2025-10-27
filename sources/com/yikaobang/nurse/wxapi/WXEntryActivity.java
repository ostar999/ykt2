package com.yikaobang.nurse.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.psychiatrygarden.event.WXAuthEvent;
import com.psychiatrygarden.utils.SdkConstant;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import de.greenrobot.event.EventBus;

/* loaded from: classes6.dex */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    @Override // android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WXAPIFactory.createWXAPI(this, SdkConstant.getWxAppId()).handleIntent(getIntent(), this);
    }

    @Override // android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        WXAPIFactory.createWXAPI(this, SdkConstant.getWxAppId()).handleIntent(getIntent(), this);
    }

    @Override // com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
    public void onReq(BaseReq baseReq) {
    }

    @Override // com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
    public void onResp(BaseResp resp) {
        if (resp.errCode != 0) {
            finish();
            return;
        }
        if (!(resp instanceof SendAuth.Resp)) {
            finish();
            return;
        }
        SendAuth.Resp resp2 = (SendAuth.Resp) resp;
        if ("com.yikaobang.yixueykb".equals(resp2.state)) {
            EventBus.getDefault().post(new WXAuthEvent(resp2.code));
            finish();
        }
    }
}

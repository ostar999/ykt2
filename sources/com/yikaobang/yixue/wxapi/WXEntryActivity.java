package com.yikaobang.yixue.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.WebLongSaveActivity;
import com.psychiatrygarden.activity.WelcomeActivity;
import com.psychiatrygarden.event.WXAuthEvent;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SdkConstant;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;

/* loaded from: classes7.dex */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
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
    public void onReq(BaseReq req) {
        if (req.getType() == 4 && (req instanceof ShowMessageFromWX.Req)) {
            String str = ((ShowMessageFromWX.Req) req).message.messageExt;
            String topActivityName = ProjectApp.instance().getTopActivityName();
            LogUtils.e("wechat_extinfo", "info===>" + str + ";topAct=>" + topActivityName);
            if (TextUtils.isEmpty(topActivityName)) {
                Intent intent = new Intent(ProjectApp.instance(), (Class<?>) WelcomeActivity.class);
                intent.putExtra("wechatUrl", str);
                intent.setFlags(268435456);
                ProjectApp.instance().startActivity(intent);
                finish();
                return;
            }
            Intent intent2 = new Intent(ProjectApp.instance(), (Class<?>) WebLongSaveActivity.class);
            intent2.putExtra("title", ProjectApp.instance().getResources().getString(R.string.app_name));
            intent2.putExtra("web_url", str);
            intent2.setFlags(268435456);
            ProjectApp.instance().startActivity(intent2);
            finish();
        }
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

package com.tencent.connect.common;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.plv.socket.event.PLVEventConstant;
import com.tencent.open.b.e;
import com.tencent.open.log.SLog;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class AssistActivity extends Activity {
    public static final String EXTRA_INTENT = "openSDK_LOG.AssistActivity.ExtraIntent";

    /* renamed from: d, reason: collision with root package name */
    private String f18088d;

    /* renamed from: e, reason: collision with root package name */
    private QQStayReceiver f18089e;

    /* renamed from: f, reason: collision with root package name */
    private boolean f18090f;

    /* renamed from: c, reason: collision with root package name */
    private boolean f18087c = false;

    /* renamed from: a, reason: collision with root package name */
    protected boolean f18085a = false;

    /* renamed from: b, reason: collision with root package name */
    protected Handler f18086b = new Handler() { // from class: com.tencent.connect.common.AssistActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 0 && !AssistActivity.this.isFinishing()) {
                SLog.w("openSDK_LOG.AssistActivity", "-->finish by timeout");
                AssistActivity.this.finish();
            }
        }
    };

    public class QQStayReceiver extends BroadcastReceiver {
        private QQStayReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String str = DictionaryFactory.SHARP;
            Intent intent2 = new Intent();
            intent2.putExtra(Constants.KEY_ACTION, "action_share");
            try {
                Uri uri = (Uri) intent.getParcelableExtra("uriData");
                String string = uri.toString();
                if (!string.contains(DictionaryFactory.SHARP)) {
                    str = "?";
                }
                for (String str2 : string.substring(string.indexOf(str) + 1).split("&")) {
                    String[] strArrSplit = str2.split("=");
                    intent2.putExtra(strArrSplit[0], strArrSplit[1]);
                }
                intent2.setData(uri);
            } catch (Exception e2) {
                SLog.i("openSDK_LOG.AssistActivity", "QQStayReceiver parse uri error : " + e2.getMessage());
                intent2.putExtra("result", "error");
                intent2.putExtra("response", "parse error.");
            }
            AssistActivity.this.setResult(-1, intent2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0074  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(android.os.Bundle r13) {
        /*
            r12 = this;
            java.lang.String r0 = "viaShareType"
            java.lang.String r8 = r13.getString(r0)
            java.lang.String r0 = "callbackAction"
            java.lang.String r0 = r13.getString(r0)
            java.lang.String r1 = "url"
            java.lang.String r1 = r13.getString(r1)
            java.lang.String r2 = "openId"
            java.lang.String r2 = r13.getString(r2)
            java.lang.String r3 = "appId"
            java.lang.String r3 = r13.getString(r3)
            java.lang.String r13 = "shareToQQ"
            boolean r13 = r13.equals(r0)
            if (r13 == 0) goto L30
            java.lang.String r13 = "ANDROIDQQ.SHARETOQQ.XX"
            java.lang.String r4 = "10"
        L2d:
            r5 = r4
            r4 = r13
            goto L42
        L30:
            java.lang.String r13 = "shareToQzone"
            boolean r13 = r13.equals(r0)
            if (r13 == 0) goto L3e
            java.lang.String r13 = "ANDROIDQQ.SHARETOQZ.XX"
            java.lang.String r4 = "11"
            goto L2d
        L3e:
            java.lang.String r13 = ""
            r4 = r13
            r5 = r4
        L42:
            boolean r13 = com.tencent.open.utils.k.a(r12, r1)
            if (r13 != 0) goto L74
            com.tencent.connect.common.UIListenerManager r13 = com.tencent.connect.common.UIListenerManager.getInstance()
            com.tencent.tauth.IUiListener r13 = r13.getListnerWithAction(r0)
            if (r13 == 0) goto L5f
            com.tencent.tauth.UiError r0 = new com.tencent.tauth.UiError
            java.lang.String r1 = "打开浏览器失败!"
            r6 = 0
            r7 = -6
            r0.<init>(r7, r1, r6)
            r13.onError(r0)
        L5f:
            com.tencent.open.b.e r1 = com.tencent.open.b.e.a()
            java.lang.String r6 = "3"
            java.lang.String r7 = "1"
            java.lang.String r9 = "0"
            java.lang.String r10 = "2"
            java.lang.String r11 = "0"
            r1.a(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            r12.finish()
            goto L85
        L74:
            com.tencent.open.b.e r1 = com.tencent.open.b.e.a()
            java.lang.String r6 = "3"
            java.lang.String r7 = "0"
            java.lang.String r9 = "0"
            java.lang.String r10 = "2"
            java.lang.String r11 = "0"
            r1.a(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
        L85:
            android.content.Intent r13 = r12.getIntent()
            java.lang.String r0 = "shareH5"
            r13.removeExtra(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.connect.common.AssistActivity.a(android.os.Bundle):void");
    }

    public static Intent getAssistActivityIntent(Context context) {
        return new Intent(context, (Class<?>) AssistActivity.class);
    }

    @Override // android.app.Activity
    public void onActivityResult(int i2, int i3, Intent intent) {
        StringBuilder sb = new StringBuilder();
        sb.append("--onActivityResult--requestCode: ");
        sb.append(i2);
        sb.append(" | resultCode: ");
        sb.append(i3);
        sb.append("data = null ? ");
        sb.append(intent == null);
        SLog.i("openSDK_LOG.AssistActivity", sb.toString());
        super.onActivityResult(i2, i3, intent);
        if (i2 == 0) {
            return;
        }
        if (intent != null) {
            intent.putExtra(Constants.KEY_ACTION, "action_login");
        }
        setResultData(i2, intent);
        if (this.f18090f) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.tencent.connect.common.AssistActivity.2
                @Override // java.lang.Runnable
                public void run() {
                    SLog.i("openSDK_LOG.AssistActivity", "onActivityResult finish delay");
                    AssistActivity.this.finish();
                }
            }, 200L);
        } else {
            SLog.i("openSDK_LOG.AssistActivity", "onActivityResult finish immediate");
            finish();
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        getWindow().addFlags(67108864);
        requestWindowFeature(1);
        super.onCreate(bundle);
        this.f18090f = getIntent().getBooleanExtra(Constants.KEY_RESTORE_LANDSCAPE, false);
        SLog.i("openSDK_LOG.AssistActivity", "--onCreate-- mRestoreLandscape=" + this.f18090f);
        if (getIntent() == null) {
            SLog.e("openSDK_LOG.AssistActivity", "-->onCreate--getIntent() returns null");
            finish();
        }
        Intent intent = (Intent) getIntent().getParcelableExtra(EXTRA_INTENT);
        int intExtra = intent == null ? 0 : intent.getIntExtra(Constants.KEY_REQUEST_CODE, 0);
        this.f18088d = intent == null ? "" : intent.getStringExtra("appid");
        Bundle bundleExtra = getIntent().getBundleExtra("h5_share_data");
        if (bundle != null) {
            this.f18087c = bundle.getBoolean("RESTART_FLAG");
            this.f18085a = bundle.getBoolean("RESUME_FLAG", false);
        }
        if (this.f18087c) {
            SLog.d("openSDK_LOG.AssistActivity", "is restart");
            return;
        }
        if (bundleExtra != null) {
            SLog.w("openSDK_LOG.AssistActivity", "--onCreate--h5 bundle not null, will open browser");
            a(bundleExtra);
            return;
        }
        if (intent == null) {
            SLog.e("openSDK_LOG.AssistActivity", "--onCreate--activityIntent is null");
            finish();
            return;
        }
        SLog.i("openSDK_LOG.AssistActivity", "--onCreate--activityIntent not null, will start activity, reqcode = " + intExtra);
        try {
            IntentFilter intentFilter = new IntentFilter(Constants.SHARE_QQ_AND_STAY + intent.getData().getQueryParameter("share_id"));
            if (this.f18089e == null) {
                this.f18089e = new QQStayReceiver();
            }
            registerReceiver(this.f18089e, intentFilter);
        } catch (Exception e2) {
            SLog.i("openSDK_LOG.AssistActivity", "registerReceiver exception : " + e2.getMessage());
        }
        startActivityForResult(intent, intExtra);
    }

    @Override // android.app.Activity
    public void onDestroy() {
        SLog.i("openSDK_LOG.AssistActivity", "-->onDestroy");
        super.onDestroy();
        QQStayReceiver qQStayReceiver = this.f18089e;
        if (qQStayReceiver != null) {
            unregisterReceiver(qQStayReceiver);
        }
    }

    @Override // android.app.Activity
    public void onNewIntent(Intent intent) {
        SLog.i("openSDK_LOG.AssistActivity", "--onNewIntent");
        super.onNewIntent(intent);
        int intExtra = intent.getIntExtra(Constants.KEY_REQUEST_CODE, -1);
        if (intExtra == 10108) {
            intent.putExtra(Constants.KEY_ACTION, "action_request_avatar");
            if (intent.getBooleanExtra(Constants.KEY_STAY, false)) {
                moveTaskToBack(true);
            }
            setResult(-1, intent);
            if (isFinishing()) {
                return;
            }
            finish();
            return;
        }
        if (intExtra == 10109) {
            intent.putExtra(Constants.KEY_ACTION, "action_request_set_emotion");
            if (intent.getBooleanExtra(Constants.KEY_STAY, false)) {
                moveTaskToBack(true);
            }
            setResult(-1, intent);
            if (isFinishing()) {
                return;
            }
            finish();
            return;
        }
        if (intExtra == 10110) {
            intent.putExtra(Constants.KEY_ACTION, "action_request_dynamic_avatar");
            if (intent.getBooleanExtra(Constants.KEY_STAY, false)) {
                moveTaskToBack(true);
            }
            setResult(-1, intent);
            if (isFinishing()) {
                return;
            }
            finish();
            return;
        }
        if (intExtra == 10111) {
            intent.putExtra(Constants.KEY_ACTION, PLVEventConstant.Seminar.EVENT_JOIN_GROUP);
            if (intent.getBooleanExtra(Constants.KEY_STAY, false)) {
                moveTaskToBack(true);
            }
            setResult(-1, intent);
            if (isFinishing()) {
                return;
            }
            finish();
            return;
        }
        if (intExtra != 10112) {
            intent.putExtra(Constants.KEY_ACTION, "action_share");
            setResult(-1, intent);
            if (isFinishing()) {
                return;
            }
            SLog.i("openSDK_LOG.AssistActivity", "--onNewIntent--activity not finished, finish now");
            finish();
            return;
        }
        intent.putExtra(Constants.KEY_ACTION, "bindGroup");
        if (intent.getBooleanExtra(Constants.KEY_STAY, false)) {
            moveTaskToBack(true);
        }
        setResult(-1, intent);
        if (isFinishing()) {
            return;
        }
        finish();
    }

    @Override // android.app.Activity
    public void onPause() {
        SLog.i("openSDK_LOG.AssistActivity", "-->onPause");
        this.f18086b.removeMessages(0);
        super.onPause();
    }

    @Override // android.app.Activity
    public void onResume() {
        SLog.i("openSDK_LOG.AssistActivity", "-->onResume");
        super.onResume();
        Intent intent = getIntent();
        if (intent.getBooleanExtra("is_login", false)) {
            return;
        }
        if (!intent.getBooleanExtra("is_qq_mobile_share", false) && this.f18087c && !isFinishing()) {
            finish();
        }
        if (!this.f18085a) {
            this.f18085a = true;
        } else {
            this.f18086b.sendMessage(this.f18086b.obtainMessage(0));
        }
    }

    @Override // android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        SLog.i("openSDK_LOG.AssistActivity", "--onSaveInstanceState--");
        bundle.putBoolean("RESTART_FLAG", true);
        bundle.putBoolean("RESUME_FLAG", this.f18085a);
        super.onSaveInstanceState(bundle);
    }

    @Override // android.app.Activity
    public void onStart() {
        SLog.i("openSDK_LOG.AssistActivity", "-->onStart");
        super.onStart();
    }

    @Override // android.app.Activity
    public void onStop() {
        SLog.i("openSDK_LOG.AssistActivity", "-->onStop");
        super.onStop();
    }

    public void setResultData(int i2, Intent intent) {
        if (intent == null) {
            SLog.w("openSDK_LOG.AssistActivity", "--setResultData--intent is null, setResult ACTIVITY_CANCEL");
            setResult(0);
            if (i2 == 11101) {
                e.a().a("", this.f18088d, "2", "1", "7", "2");
                return;
            }
            return;
        }
        try {
            String stringExtra = intent.getStringExtra(Constants.KEY_RESPONSE);
            SLog.d("openSDK_LOG.AssistActivity", "--setResultDataForLogin-- ");
            if (TextUtils.isEmpty(stringExtra)) {
                SLog.w("openSDK_LOG.AssistActivity", "--setResultData--response is empty, setResult ACTIVITY_OK");
                setResult(-1, intent);
            } else {
                JSONObject jSONObject = new JSONObject(stringExtra);
                String strOptString = jSONObject.optString("openid");
                String strOptString2 = jSONObject.optString("access_token");
                String strOptString3 = jSONObject.optString("proxy_code");
                long jOptLong = jSONObject.optLong("proxy_expires_in");
                if (!TextUtils.isEmpty(strOptString) && !TextUtils.isEmpty(strOptString2)) {
                    SLog.i("openSDK_LOG.AssistActivity", "--setResultData--openid and token not empty, setResult ACTIVITY_OK");
                    setResult(-1, intent);
                    e.a().a(strOptString, this.f18088d, "2", "1", "7", "0");
                } else if (TextUtils.isEmpty(strOptString3) || jOptLong == 0) {
                    SLog.w("openSDK_LOG.AssistActivity", "--setResultData--openid or token is empty, setResult ACTIVITY_CANCEL");
                    setResult(0, intent);
                    e.a().a("", this.f18088d, "2", "1", "7", "1");
                } else {
                    SLog.i("openSDK_LOG.AssistActivity", "--setResultData--proxy_code and proxy_expires_in are valid");
                    setResult(-1, intent);
                }
            }
        } catch (Exception e2) {
            SLog.e("openSDK_LOG.AssistActivity", "--setResultData--parse response failed");
            e2.printStackTrace();
        }
    }
}

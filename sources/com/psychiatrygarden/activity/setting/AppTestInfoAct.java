package com.psychiatrygarden.activity.setting;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import cn.hutool.core.text.StrPool;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.ShowImgActivity;
import com.psychiatrygarden.bean.VersionUpdateBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.UpgradeService;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SdkConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.utils.weblong.ItemLongClickedPopWindow;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.tencent.mm.opensdk.modelbiz.WXOpenCustomerServiceChat;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yikaobang.yixue.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class AppTestInfoAct extends BaseActivity {
    private static final int REQUEST_CODE_PERMISSION_READ_OR_WRITE = 273;
    private int downX;
    private int downY;
    private ItemLongClickedPopWindow itemLongClickedPopWindow;
    private AlertDialog mAlertDialog;
    private TextView mBtnOption;
    private Context mContext;
    private Timer mInstallTimer;
    private WebView mWebView;
    private String mUrl = "";
    private String saveImgUrl = "";
    private String apkPath = null;
    private String apkUrl = "";
    private String verCode = null;
    private boolean isHint = true;

    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new AnonymousClass1();
    View.OnTouchListener listener = new View.OnTouchListener() { // from class: com.psychiatrygarden.activity.setting.AppTestInfoAct.4
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View arg0, MotionEvent arg1) {
            AppTestInfoAct.this.downX = (int) arg1.getX();
            AppTestInfoAct.this.downY = (int) arg1.getY();
            return false;
        }
    };

    /* renamed from: com.psychiatrygarden.activity.setting.AppTestInfoAct$1, reason: invalid class name */
    public class AnonymousClass1 extends Handler {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$0(DialogInterface dialogInterface, int i2) {
            ProjectApp.instance().removeAllActivity();
            ProjectApp.instance().exit();
            AppTestInfoAct.this.stopInstallTimer();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$1(DialogInterface dialogInterface, int i2) {
            AppTestInfoAct.this.installApp();
        }

        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1114129) {
                if (AppTestInfoAct.this.mAlertDialog == null || !AppTestInfoAct.this.mAlertDialog.isShowing()) {
                    AppTestInfoAct.this.mAlertDialog = new AlertDialog.Builder(AppTestInfoAct.this).setMessage(AppTestInfoAct.this.getResources().getString(R.string.app_app) + "安装失败,请重新安装！").setTitle("安装失败！").setCancelable(false).setNegativeButton("取消", new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.k
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i2) {
                            this.f13873c.lambda$handleMessage$0(dialogInterface, i2);
                        }
                    }).setPositiveButton("重新安装", new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.l
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i2) {
                            this.f13877c.lambda$handleMessage$1(dialogInterface, i2);
                        }
                    }).show();
                }
            }
        }
    }

    public class SaveImage extends AsyncTask<String, Void, String> {
        private SaveImage() {
        }

        public /* synthetic */ SaveImage(AppTestInfoAct appTestInfoAct, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // android.os.AsyncTask
        public String doInBackground(String... params) throws IOException {
            try {
                String string = Environment.getExternalStorageDirectory().toString();
                File file = new File(string + "/Download");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file2 = new File(string + "/Download/" + new Date().getTime() + AppTestInfoAct.this.saveImgUrl.substring(AppTestInfoAct.this.saveImgUrl.lastIndexOf(StrPool.DOT)));
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(AppTestInfoAct.this.saveImgUrl).openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(20000);
                InputStream inputStream = httpURLConnection.getResponseCode() == 200 ? httpURLConnection.getInputStream() : null;
                byte[] bArr = new byte[4096];
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                while (true) {
                    int i2 = inputStream.read(bArr);
                    if (i2 == -1) {
                        fileOutputStream.close();
                        String str = "图片已保存至：" + file2.getAbsolutePath();
                        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                        intent.setData(Uri.fromFile(file2));
                        AppTestInfoAct.this.sendBroadcast(intent);
                        return str;
                    }
                    fileOutputStream.write(bArr, 0, i2);
                }
            } catch (Exception e2) {
                return "保存失败！" + e2.getLocalizedMessage();
            }
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(String result) {
            NewToast.showShort(AppTestInfoAct.this.mContext, result, 1).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initPermissionForReadOrWrite(String apkUrl, String verCode) {
        int iCheckSelfPermission = ContextCompat.checkSelfPermission(this, Permission.WRITE_EXTERNAL_STORAGE);
        int iCheckSelfPermission2 = ContextCompat.checkSelfPermission(this, Permission.READ_EXTERNAL_STORAGE);
        if (iCheckSelfPermission != 0 || iCheckSelfPermission2 != 0) {
            new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.setting.e
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f13849a.lambda$initPermissionForReadOrWrite$5();
                }
            })).show();
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this.mContext, UpgradeService.class);
        intent.putExtra("download_url", apkUrl);
        intent.putExtra("verCode", verCode);
        startService(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void installApp() {
        Uri uriForFile = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", new File((Build.VERSION.SDK_INT >= 29 ? getExternalFilesDir(null) : Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)).getPath(), "yikaobang.apk"));
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uriForFile, "application/vnd.android.package-archive");
        intent.addFlags(268435456);
        intent.addFlags(1);
        startActivity(intent);
        startInstallTimer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isForeground() {
        String packageName = ((ActivityManager) getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningTasks(1).get(0).topActivity.getPackageName();
        return !TextUtils.isEmpty(packageName) && packageName.equals(getPackageName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        try {
            IWXAPI iwxapiCreateWXAPI = WXAPIFactory.createWXAPI(this, SdkConstant.getWxAppId());
            if (iwxapiCreateWXAPI.isWXAppInstalled()) {
                iwxapiCreateWXAPI.getWXAppSupportAPI();
                if (iwxapiCreateWXAPI.getWXAppSupportAPI() >= 671090490) {
                    WXOpenCustomerServiceChat.Req req = new WXOpenCustomerServiceChat.Req();
                    req.corpId = "ww18f7b70abc9d64e0";
                    req.url = "https://work.weixin.qq.com/kfid/kfc8a3c134f28e041bd";
                    iwxapiCreateWXAPI.sendReq(req);
                }
            } else {
                ToastUtil.shortToast(this, "您的设备未安装微信客户端");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            ToastUtil.shortToast(this, "启动失败！");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        submit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        this.itemLongClickedPopWindow.dismiss();
        Intent intent = new Intent(this.mContext, (Class<?>) ShowImgActivity.class);
        intent.putExtra(AliyunLogCommon.LogLevel.INFO, this.saveImgUrl);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(View view) {
        this.itemLongClickedPopWindow.dismiss();
        if (ContextCompat.checkSelfPermission(ProjectApp.instance(), Permission.WRITE_EXTERNAL_STORAGE) != 0) {
            new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.setting.AppTestInfoAct.2
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public void onConfirm() {
                    ActivityCompat.requestPermissions(AppTestInfoAct.this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 2);
                }
            })).show();
        } else {
            new SaveImage(this, null).execute(new String[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$init$4(View view) {
        int type;
        WebView.HitTestResult hitTestResult = ((WebView) view).getHitTestResult();
        if (hitTestResult == null || (type = hitTestResult.getType()) == 0) {
            return false;
        }
        this.itemLongClickedPopWindow = new ItemLongClickedPopWindow(this, 5, SizeUtil.dp2px(this.mContext, 120), SizeUtil.dp2px(this.mContext, 90));
        if (type == 5) {
            this.saveImgUrl = hitTestResult.getExtra();
            this.itemLongClickedPopWindow.showAtLocation(view, 51, this.downX, this.downY + 10);
        }
        this.itemLongClickedPopWindow.getView(R.id.item_longclicked_viewImage).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f13853c.lambda$init$2(view2);
            }
        });
        this.itemLongClickedPopWindow.getView(R.id.item_longclicked_saveImage).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f13856c.lambda$init$3(view2);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initPermissionForReadOrWrite$5() {
        ActivityCompat.requestPermissions(this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 273);
    }

    private void startInstallTimer() {
        this.mInstallTimer = new Timer();
        this.mInstallTimer.schedule(new TimerTask() { // from class: com.psychiatrygarden.activity.setting.AppTestInfoAct.6
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                if (AppTestInfoAct.this.isForeground()) {
                    Message messageObtain = Message.obtain();
                    messageObtain.what = 1114129;
                    AppTestInfoAct.this.mHandler.sendMessage(messageObtain);
                }
            }
        }, 1000L, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopInstallTimer() {
        Timer timer = this.mInstallTimer;
        if (timer != null) {
            timer.cancel();
            this.mInstallTimer = null;
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mBtnOption = (TextView) findViewById(R.id.btn_option);
        setTitle(getIntent().getExtras().getString("title"));
        this.mWebView = (WebView) findViewById(R.id.webview);
        try {
            String appVersion = AndroidBaseUtils.getAppVersion(this.mContext);
            String strConfig = SharePreferencesUtils.readStrConfig("appTestVersion", this.mContext);
            if (!strConfig.contains(StrPool.DOT)) {
                StringBuilder sb = new StringBuilder();
                int i2 = 0;
                while (i2 < strConfig.length()) {
                    int i3 = i2 + 1;
                    sb.append(strConfig.substring(i2, i3));
                    if (i2 < strConfig.length() - 1) {
                        sb.append(StrPool.DOT);
                    }
                    i2 = i3;
                }
                strConfig = sb.toString();
            }
            if (strConfig.equals(appVersion)) {
                this.mBtnOption.setText("反馈问题");
                this.mBtnOption.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.h
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f13859c.lambda$init$0(view);
                    }
                });
            } else {
                this.mBtnOption.setText("立即报名");
                this.mBtnOption.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.i
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f13864c.lambda$init$1(view);
                    }
                });
            }
        } catch (Exception unused) {
        }
        if (SkinManager.getCurrentSkinType(this) == 1) {
            this.mWebView.setBackgroundColor(0);
        }
        WebSettings settings = this.mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(2);
        settings.setMixedContentMode(0);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setGeolocationEnabled(true);
        this.mWebView.removeJavascriptInterface("searchBoxJavaBredge_");
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        this.mWebView.setOnTouchListener(this.listener);
        this.mWebView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.psychiatrygarden.activity.setting.j
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return this.f13869c.lambda$init$4(view);
            }
        });
        this.mWebView.setWebViewClient(new WebViewClient() { // from class: com.psychiatrygarden.activity.setting.AppTestInfoAct.3
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView view, String url) {
                if (SkinManager.getCurrentSkinType(AppTestInfoAct.this) == 1) {
                    view.loadUrl("javascript:function setBackgroundColor() {document.querySelector('body').style.backgroundColor='#121622';document.querySelector('body').style.color='#7380A9';}");
                    view.loadUrl("javascript:setBackgroundColor();");
                }
                super.onPageFinished(view, url);
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                AppTestInfoAct.this.mWebView.loadUrl(url);
                return true;
            }
        });
        String strConfig2 = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this, "1");
        this.mWebView.loadUrl(this.mUrl + "?app_id=" + strConfig2);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return false;
        }
        finish();
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2) {
            if (grantResults.length <= 0 || grantResults[0] != 0) {
                NewToast.showShort(this, "请手动打开软件存储权限", 0).show();
                return;
            } else {
                new SaveImage(this, null).execute(new String[0]);
                return;
            }
        }
        if (273 == requestCode) {
            int i2 = grantResults[0];
            if (i2 == -1) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.READ_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(this, new String[]{Permission.READ_EXTERNAL_STORAGE}, 273);
                    return;
                } else {
                    NewToast.showShort(this, "数据写入应用权限被禁用，请在权限管理修改", 0).show();
                    return;
                }
            }
            if (i2 != 0) {
                return;
            }
            Intent intent = new Intent();
            intent.setClass(this.mContext, UpgradeService.class);
            intent.putExtra("download_url", this.apkUrl);
            intent.putExtra("verCode", this.verCode);
            startService(intent);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        try {
            if (getIntent().getExtras().getString("web_url") != null) {
                this.mUrl = getIntent().getExtras().getString("web_url");
            } else {
                this.mUrl = getIntent().getExtras().getString("url");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.mContext = this;
        this.apkPath = ProjectApp.instance().getStoragePath();
        File file = new File(this.apkPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
        setContentView(R.layout.layout_app_test_info);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void submit() {
        String strConfig = SharePreferencesUtils.readStrConfig("appTestVersion", this.mContext);
        showProgressDialog("数据同步中...");
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("test_version", strConfig);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.joinAppTest, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.AppTestInfoAct.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                AppTestInfoAct.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                VersionUpdateBean.VersionBean data;
                super.onSuccess((AnonymousClass5) s2);
                AppTestInfoAct.this.hideProgressDialog();
                try {
                    VersionUpdateBean versionUpdateBean = (VersionUpdateBean) new Gson().fromJson(s2, VersionUpdateBean.class);
                    if (!versionUpdateBean.getCode().equals("200") || (data = versionUpdateBean.getData()) == null) {
                        ToastUtil.shortToast(AppTestInfoAct.this, versionUpdateBean.getMessage());
                    } else {
                        AppTestInfoAct.this.verCode = data.getCode();
                        AppTestInfoAct.this.apkUrl = data.getAppUrl();
                        AppTestInfoAct.this.initPermissionForReadOrWrite(data.getAppUrl(), data.getCode());
                    }
                    ToastUtil.shortToast(AppTestInfoAct.this, versionUpdateBean.getMessage());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }
}

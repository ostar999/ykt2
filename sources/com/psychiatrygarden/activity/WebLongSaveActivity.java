package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import cn.hutool.core.text.StrPool;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.weblong.ItemLongClickedPopWindow;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.AlphaImageView;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.DialogShare;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/* loaded from: classes5.dex */
public class WebLongSaveActivity extends BaseActivity {
    private int downX;
    private int downY;
    public CustomEmptyView empty_view;
    private boolean isGee;
    private ItemLongClickedPopWindow itemLongClickedPopWindow;
    public AlphaImageView iv_back;
    private Context mContext;
    private DialogShare mDialogShare;
    private LinearLayout mLyView;
    private RelativeLayout mNavTabbar;
    private WebView mWebView;
    private String shareDesc;
    private String shareTitle;
    private String shareUrl;
    public TextView title;
    private String mUrl = "";
    private String saveImgUrl = "";
    private boolean isCickShare = false;
    View.OnTouchListener listener = new View.OnTouchListener() { // from class: com.psychiatrygarden.activity.WebLongSaveActivity.3
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View arg0, MotionEvent arg1) {
            WebLongSaveActivity.this.downX = (int) arg1.getX();
            WebLongSaveActivity.this.downY = (int) arg1.getY();
            return false;
        }
    };
    private final UMShareListener umShareListener = new UMShareListener() { // from class: com.psychiatrygarden.activity.WebLongSaveActivity.4
        @Override // com.umeng.socialize.UMShareListener
        public void onCancel(SHARE_MEDIA arg0) {
            WebLongSaveActivity.this.AlertToast("用户取消分享");
            if (WebLongSaveActivity.this.mDialogShare != null) {
                WebLongSaveActivity.this.mDialogShare.dismiss();
            }
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onError(SHARE_MEDIA arg0, Throwable arg1) {
            WebLongSaveActivity.this.AlertToast("未检测到QQ/微信应用或请安装正式版QQ/微信分享！");
            if (WebLongSaveActivity.this.mDialogShare != null) {
                WebLongSaveActivity.this.mDialogShare.dismiss();
            }
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onResult(SHARE_MEDIA arg0) {
            WebLongSaveActivity.this.AlertToast("分享成功");
            if (WebLongSaveActivity.this.mDialogShare != null) {
                WebLongSaveActivity.this.mDialogShare.dismiss();
            }
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onStart(SHARE_MEDIA share_media) {
        }
    };

    /* renamed from: com.psychiatrygarden.activity.WebLongSaveActivity$2, reason: invalid class name */
    public class AnonymousClass2 extends WebViewClient {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPageFinished$0() {
            WebLongSaveActivity.this.empty_view.stopAnim();
            WebLongSaveActivity.this.empty_view.setVisibility(8);
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            if (!WebLongSaveActivity.this.isGee && SkinManager.getCurrentSkinType(WebLongSaveActivity.this) == 1) {
                view.loadUrl("javascript:function setBackgroundColor() {document.querySelector('body').style.backgroundColor='#121622';document.querySelector('body').style.color='#7380A9';}");
                view.loadUrl("javascript:setBackgroundColor();");
            }
            WebLongSaveActivity.this.empty_view.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.dr
                @Override // java.lang.Runnable
                public final void run() {
                    this.f12260c.lambda$onPageFinished$0();
                }
            }, 300L);
            super.onPageFinished(view, url);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!url.startsWith("weixin://")) {
                WebLongSaveActivity.this.mWebView.loadUrl(url);
                return true;
            }
            WebLongSaveActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
            return true;
        }
    }

    public class JavaScriptInterface {
        Context context;

        public JavaScriptInterface(Context context) {
            this.context = context;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleShare$0(int i2) {
            WebLongSaveActivity.this.shareAppControl(i2);
        }

        @JavascriptInterface
        public void handleQuestions(String dataValue) {
            LogUtils.e("reveice_data_from_js", "做题获取到的参数====》" + dataValue);
            EventBus.getDefault().post("jumpToHome");
            ProjectApp.instance().closeAllActivityWithoutHome();
        }

        @JavascriptInterface
        public void handleShare(String dataValue) {
            LogUtils.e("reveice_data_from_js", "获取到的参数====》" + dataValue);
            WebLongSaveActivity.this.mDialogShare = new DialogShare(WebLongSaveActivity.this.mContext, new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.er
                @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
                public final void onclickIntBack(int i2) {
                    this.f12331a.lambda$handleShare$0(i2);
                }
            });
            WebLongSaveActivity.this.mDialogShare.show();
            WebLongSaveActivity.this.isCickShare = true;
        }
    }

    public class SaveImage extends AsyncTask<String, Void, String> {
        private SaveImage() {
        }

        @Override // android.os.AsyncTask
        public String doInBackground(String... params) throws IOException {
            try {
                String string = Environment.getExternalStorageDirectory().toString();
                File file = new File(string + "/Download");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file2 = new File(string + "/Download/" + new Date().getTime() + WebLongSaveActivity.this.saveImgUrl.substring(WebLongSaveActivity.this.saveImgUrl.lastIndexOf(StrPool.DOT)));
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(WebLongSaveActivity.this.saveImgUrl).openConnection();
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
                        WebLongSaveActivity.this.sendBroadcast(intent);
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
            NewToast.showShort(WebLongSaveActivity.this.mContext, result, 1).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(String str) {
        LogUtils.e("reveice_data_from_js", "获取到的参数====》" + str);
        if (TextUtils.isEmpty(str) || str.equals("null")) {
            EventBus.getDefault().post("refreshInviteActive");
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        this.mWebView.evaluateJavascript("javascript:goBack()", new ValueCallback() { // from class: com.psychiatrygarden.activity.wq
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                this.f14164a.lambda$init$0((String) obj);
            }
        });
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
            new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.WebLongSaveActivity.1
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public void onConfirm() {
                    ActivityCompat.requestPermissions(WebLongSaveActivity.this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 2);
                }
            })).show();
        } else {
            new SaveImage().execute(new String[0]);
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
        this.itemLongClickedPopWindow.getView(R.id.item_longclicked_viewImage).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.xq
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f14197c.lambda$init$2(view2);
            }
        });
        this.itemLongClickedPopWindow.getView(R.id.item_longclicked_saveImage).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.yq
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f14227c.lambda$init$3(view2);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBackPressed$5(String str) {
        if (TextUtils.isEmpty(str) || str.equals("null")) {
            finish();
            EventBus.getDefault().post("refreshInviteActive");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onResume$6(String str) {
        LogUtils.e("reveice_data_from_js", "获取到的参数====》" + str);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        this.title = (TextView) findViewById(R.id.title);
        this.iv_back = (AlphaImageView) findViewById(R.id.iv_back);
        this.mNavTabbar = (RelativeLayout) findViewById(R.id.nav_tabbar);
        this.mLyView = (LinearLayout) findViewById(R.id.ly_view);
        this.empty_view = (CustomEmptyView) findViewById(R.id.empty_view);
        if (this.isGee) {
            this.mNavTabbar.setBackgroundColor(-1);
            this.mLyView.setBackgroundColor(-1);
            this.title.setTextColor(Color.parseColor("#141516"));
            this.iv_back.setImageResource(R.mipmap.ic_black_back);
        }
        this.iv_back.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ar
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11089c.lambda$init$1(view);
            }
        });
        this.title.setText(getIntent().getExtras().getString("title"));
        this.shareDesc = getIntent().getExtras().getString("desc");
        this.shareUrl = getIntent().getExtras().getString("shareUrl");
        this.shareTitle = getIntent().getExtras().getString("shareTitle");
        if (this.mUrl.contains("annualReport/login.html")) {
            String string = ProjectApp.instance().getResources().getString(R.string.app_name);
            this.title.setText("我的2024荣耀时刻");
            this.shareDesc = "君子博学而日参省乎己，则知明而行无过矣。";
            this.shareTitle = string + "｜2024 年度学习报告来啦！";
            this.shareUrl = this.mUrl;
            this.mUrl += "?userId=" + UserConfig.getUserId() + "&from=App&appId=" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1");
            this.isGee = true;
        }
        this.mWebView = (WebView) findViewById(R.id.webview);
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
        this.mWebView.addJavascriptInterface(new JavaScriptInterface(this.mContext), "Android");
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        this.mWebView.setOnTouchListener(this.listener);
        this.mWebView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.psychiatrygarden.activity.br
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return this.f11129c.lambda$init$4(view);
            }
        });
        this.mWebView.setWebViewClient(new AnonymousClass2());
        this.mWebView.loadUrl(this.mUrl);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        if (SkinManager.getCurrentSkinType(this) == 0) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.new_bg_one_color), 0);
            getWindow().getDecorView().setSystemUiVisibility(R2.drawable.ddbq);
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        } else if (!this.isGee) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.new_bg_one_color_night), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#171D2D"));
        } else {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.new_bg_one_color), 0);
            getWindow().getDecorView().setSystemUiVisibility(R2.drawable.ddbq);
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        this.mWebView.evaluateJavascript("javascript:goBack()", new ValueCallback() { // from class: com.psychiatrygarden.activity.zq
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                this.f14260a.lambda$onBackPressed$5((String) obj);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        SkinManager.onActivityCreateSetSkin(this);
        try {
            this.isGee = getIntent().getBooleanExtra("noDark", false);
            if (getIntent().getExtras().getString("web_url") != null) {
                this.mUrl = getIntent().getExtras().getString("web_url");
            } else {
                this.mUrl = getIntent().getExtras().getString("url");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        initStatusBar();
        this.mContext = this;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2) {
            if (grantResults.length <= 0 || grantResults[0] != 0) {
                NewToast.showShort(this, "请手动打开软件存储权限", 0).show();
            } else {
                new SaveImage().execute(new String[0]);
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.isCickShare) {
            this.mWebView.evaluateJavascript("javascript:handleNext()", new ValueCallback() { // from class: com.psychiatrygarden.activity.cr
                @Override // android.webkit.ValueCallback
                public final void onReceiveValue(Object obj) {
                    WebLongSaveActivity.lambda$onResume$6((String) obj);
                }
            });
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_weblongsave);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void shareAppControl(int i2) {
        UMImage uMImage = new UMImage(this.mContext, this.mUrl.contains("annualReport/login.html") ? R.drawable.ic_share_report : R.drawable.app_icon);
        UMWeb uMWeb = new UMWeb(this.shareUrl);
        uMWeb.setTitle(this.shareTitle);
        uMWeb.setThumb(uMImage);
        uMWeb.setDescription(i2 == 3 ? this.shareUrl : this.shareDesc);
        new ShareAction(this).withMedia(uMWeb).setPlatform(BaseActivity.platforms.get(i2).mPlatform).setCallback(this.umShareListener).share();
    }
}

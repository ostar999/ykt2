package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import cn.hutool.core.text.StrPool;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.ExitUpdateEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.DialogLoadingBtnInterface;
import com.psychiatrygarden.interfaceclass.UpgradeService;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.widget.LoadingProgressDialog;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.uuzuche.lib_zxing.DisplayUtil;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ForceUpdateActivity extends BaseActivity {
    private static final int REQUEST_CODE_PERMISSION_READ_OR_WRITE = 273;
    private AlertDialog dialog;
    AlertDialog mAlertDialog;
    HttpHandler mHttpHandler;
    private Timer mInstallTimer;
    private boolean mIsForceUpdate;
    private String verCode;
    private String apkPath = null;
    private boolean isHint = true;
    private LoadingProgressDialog mLoadingProgressDig = null;

    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new AnonymousClass1();
    private boolean isShowExitDialog = false;

    /* renamed from: com.psychiatrygarden.activity.ForceUpdateActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends Handler {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$0(DialogInterface dialogInterface, int i2) {
            ProjectApp.instance().removeAllActivity();
            ProjectApp.instance().exit();
            ForceUpdateActivity.this.stopInstallTimer();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$1(DialogInterface dialogInterface, int i2) {
            ForceUpdateActivity.this.installApp();
        }

        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1114129) {
                AlertDialog alertDialog = ForceUpdateActivity.this.mAlertDialog;
                if (alertDialog == null || !alertDialog.isShowing()) {
                    ForceUpdateActivity.this.mAlertDialog = new AlertDialog.Builder(ForceUpdateActivity.this).setMessage(ForceUpdateActivity.this.getResources().getString(R.string.app_app) + "安装失败,请重新安装！").setTitle("安装失败！").setCancelable(false).setNegativeButton("取消", new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.activity.ya
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i2) {
                            this.f14211c.lambda$handleMessage$0(dialogInterface, i2);
                        }
                    }).setPositiveButton("重新安装", new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.activity.za
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i2) {
                            this.f14242c.lambda$handleMessage$1(dialogInterface, i2);
                        }
                    }).show();
                }
            }
        }
    }

    private void getToken(final Context context) {
        HashMap map = new HashMap();
        map.put("user_id", UserConfig.getUserId());
        YJYHttpUtils.post(this, NetworkRequestsURL.mGetTokenDataUrl, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.ta
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                ForceUpdateActivity.lambda$getToken$4(context, (String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.ua
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                ForceUpdateActivity.lambda$getToken$5(volleyError, str);
            }
        });
    }

    private void initPermissionForReadOrWrite() {
        if (!CommonUtil.hasRequiredPermissionsWriteStorage(this)) {
            new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.xa
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f14177a.lambda$initPermissionForReadOrWrite$2();
                }
            })).show();
            return;
        }
        AlertDialog alertDialog = this.dialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        if (this.mIsForceUpdate) {
            new Thread() { // from class: com.psychiatrygarden.activity.ForceUpdateActivity.3
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() throws IOException {
                    super.run();
                    ForceUpdateActivity forceUpdateActivity = ForceUpdateActivity.this;
                    forceUpdateActivity.downLoadApp(forceUpdateActivity.getIntent().getStringExtra("app_link"));
                }
            }.start();
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this.mContext, UpgradeService.class);
        intent.putExtra("download_url", getIntent().getStringExtra("app_link"));
        intent.putExtra("verCode", this.verCode);
        startService(intent);
        EventBus.getDefault().post(new ExitUpdateEvent());
        finish();
    }

    private void initProgressDialog() {
        LoadingProgressDialog loadingProgressDialog = new LoadingProgressDialog(this, new DialogLoadingBtnInterface() { // from class: com.psychiatrygarden.activity.sa
            @Override // com.psychiatrygarden.interfaceclass.DialogLoadingBtnInterface
            public final void CancelBtnInterface() {
                this.f13825a.lambda$initProgressDialog$3();
            }
        });
        this.mLoadingProgressDig = loadingProgressDialog;
        loadingProgressDialog.setCancelable(false);
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
    public static /* synthetic */ void lambda$getToken$4(Context context, String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String secret = UserConfig.getInstance().getUser().getSecret();
            if (jSONObject.optString("code").equals("200")) {
                String strOptString = jSONObject.optJSONObject("data").optString("encryption");
                if (TextUtils.isEmpty(strOptString)) {
                    return;
                }
                JSONObject jSONObject2 = new JSONObject(DesUtil.decode(CommonParameter.DES_KEY_VERIFYS, strOptString));
                if (jSONObject2.optString("secret").equals(secret)) {
                    return;
                }
                Intent intent = new Intent(context, (Class<?>) ExitLoginDialogActivity.class);
                intent.putExtra("message", "您的账号已在其他设备登录");
                intent.putExtra("secret", jSONObject2.optString("secret"));
                intent.setFlags(270532608);
                context.startActivity(intent);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getToken$5(VolleyError volleyError, String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initPermissionForReadOrWrite$2() {
        ActivityCompat.requestPermissions(this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 273);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initProgressDialog$3() {
        this.isHint = false;
        this.mHttpHandler.cancel(true);
        if (getIntent().getStringExtra("is_force_update").equals("1")) {
            ProjectApp.instance().removeAllActivity();
            ProjectApp.instance().exit();
        } else {
            EventBus.getDefault().post(new ExitUpdateEvent());
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialog$0(boolean z2, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (this.isShowExitDialog) {
            if (!z2) {
                getToken(this.mContext);
            }
            SharePreferencesUtils.writeBooleanConfig("showVersionUpdate", false, this);
        }
        initPermissionForReadOrWrite();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialog$1(boolean z2, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.dialog.dismiss();
        if (z2) {
            return;
        }
        EventBus.getDefault().post(new ExitUpdateEvent());
        finish();
        if (this.isShowExitDialog) {
            getToken(this.mContext);
            SharePreferencesUtils.writeBooleanConfig("showVersionUpdate", false, this);
        }
    }

    private void startInstallTimer() {
        this.mInstallTimer = new Timer();
        this.mInstallTimer.schedule(new TimerTask() { // from class: com.psychiatrygarden.activity.ForceUpdateActivity.5
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                if (ForceUpdateActivity.this.isForeground()) {
                    Message messageObtain = Message.obtain();
                    messageObtain.what = 1114129;
                    ForceUpdateActivity.this.mHandler.sendMessage(messageObtain);
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

    private void updateDialog(final boolean is_force_update) {
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.umeng_update_dialog, (ViewGroup) null);
        TextView textView = (TextView) relativeLayout.findViewById(R.id.umeng_update_id_ok);
        TextView textView2 = (TextView) relativeLayout.findViewById(R.id.umeng_update_id_cancel);
        TextView textView3 = (TextView) relativeLayout.findViewById(R.id.tv_version_num);
        try {
            StringBuilder sb = new StringBuilder();
            int i2 = 0;
            while (i2 < this.verCode.length()) {
                int i3 = i2 + 1;
                sb.append(this.verCode.substring(i2, i3));
                if (i2 < this.verCode.length() - 1) {
                    sb.append(StrPool.DOT);
                }
                i2 = i3;
            }
            textView3.setText(String.format("发现全新版本V%s", sb.toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (is_force_update) {
            textView2.setVisibility(8);
            textView2.setEnabled(false);
            textView2.setClickable(false);
        } else {
            textView2.setEnabled(true);
            textView2.setClickable(true);
        }
        TextView textView4 = (TextView) relativeLayout.findViewById(R.id.umeng_update_content);
        textView4.setMaxHeight(DisplayUtil.dip2px(this, 104.0f));
        textView4.setMovementMethod(ScrollingMovementMethod.getInstance());
        textView4.setText(getIntent().getStringExtra("message"));
        AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mContext, R.style.MyDialog).create();
        this.dialog = alertDialogCreate;
        alertDialogCreate.getWindow().setGravity(17);
        this.dialog.setCancelable(false);
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.show();
        WindowManager.LayoutParams attributes = this.dialog.getWindow().getAttributes();
        attributes.width = (int) (CommonUtil.getScreenWidth(this.mContext) * 0.7d);
        this.dialog.getWindow().setAttributes(attributes);
        this.dialog.getWindow().setContentView(relativeLayout);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.va
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14020c.lambda$updateDialog$0(is_force_update, view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.wa
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14142c.lambda$updateDialog$1(is_force_update, view);
            }
        });
    }

    public void downLoadApp(String updateUrl) throws IOException {
        File file = new File(this.apkPath, "yikaobang.apk");
        if (file.exists()) {
            LogUtils.d("FILE_SIZE", Formatter.formatFileSize(this.mContext, file.length()));
            PackageInfo packageArchiveInfo = this.mContext.getPackageManager().getPackageArchiveInfo(this.apkPath + "/yikaobang.apk", 1);
            if (packageArchiveInfo != null) {
                if (String.valueOf(this.verCode).equals(String.valueOf(packageArchiveInfo.versionCode))) {
                    LogUtils.d(getClass().getSimpleName(), "apk文件已存在，启动安装");
                    installApp();
                    return;
                }
            }
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            this.mHttpHandler = new FinalHttp().download(updateUrl, file.getAbsolutePath(), new AjaxCallBack<File>() { // from class: com.psychiatrygarden.activity.ForceUpdateActivity.2
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                    if (ForceUpdateActivity.this.isHint) {
                        new AlertDialog.Builder(ForceUpdateActivity.this).setMessage(ForceUpdateActivity.this.getString(R.string.app_name) + "更新失败,请移步应用商店更新最新版本" + ForceUpdateActivity.this.verCode).setTitle("下载失败！").setCancelable(false).setNegativeButton("确定", new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.activity.ForceUpdateActivity.2.2
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialog, int which) {
                                ProjectApp.instance().removeAllActivity();
                                ProjectApp.instance().exit();
                            }
                        }).show();
                    }
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onLoading(long count, long current) {
                    super.onLoading(count, current);
                    int i2 = (int) ((100 * current) / count);
                    ForceUpdateActivity.this.showDownloadDialog();
                    ForceUpdateActivity.this.mLoadingProgressDig.setProgressbar(i2);
                    LoadingProgressDialog loadingProgressDialog = ForceUpdateActivity.this.mLoadingProgressDig;
                    StringBuilder sb = new StringBuilder();
                    sb.append(CommonUtil.getFileSize(count + ""));
                    sb.append("M");
                    loadingProgressDialog.setAllSize(sb.toString());
                    ForceUpdateActivity.this.mLoadingProgressDig.setPercentage(i2 + "%");
                    LoadingProgressDialog loadingProgressDialog2 = ForceUpdateActivity.this.mLoadingProgressDig;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(CommonUtil.getFileSize(current + ""));
                    sb2.append("M");
                    loadingProgressDialog2.setAlreadyLoading(sb2.toString());
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(File t2) {
                    super.onSuccess((AnonymousClass2) t2);
                    ForceUpdateActivity.this.hideDownloadDialog();
                    if (new File(ForceUpdateActivity.this.apkPath, "yikaobang.apk").exists()) {
                        LogUtils.d(getClass().getSimpleName(), "apk文件下载完成，启动安装");
                        ForceUpdateActivity.this.installApp();
                        return;
                    }
                    new AlertDialog.Builder(ForceUpdateActivity.this).setMessage(ForceUpdateActivity.this.getResources().getString(R.string.app_name) + "更新失败,请移步应用商店更新最新版本" + ForceUpdateActivity.this.verCode).setTitle("下载失败！").setCancelable(false).setNegativeButton("确定", new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.activity.ForceUpdateActivity.2.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                            ProjectApp.instance().removeAllActivity();
                            ProjectApp.instance().exit();
                        }
                    }).show();
                }
            });
        } catch (IOException e2) {
            e2.printStackTrace();
            AlertToast("下载失败");
        }
    }

    public void hideDownloadDialog() {
        LoadingProgressDialog loadingProgressDialog = this.mLoadingProgressDig;
        if (loadingProgressDialog == null || !loadingProgressDialog.isShowing()) {
            return;
        }
        this.mLoadingProgressDig.dismiss();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.verCode = getIntent().getStringExtra("verCode");
        if (getIntent().getStringExtra("is_force_update") == null || !getIntent().getStringExtra("is_force_update").equals("1")) {
            this.mIsForceUpdate = false;
            updateDialog(false);
        } else {
            this.mIsForceUpdate = true;
            updateDialog(true);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        AlertDialog alertDialog = this.dialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        stopInstallTimer();
        hideDownloadDialog();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("showExitLoginDialog")) {
            this.isShowExitDialog = true;
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == 4;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (273 != requestCode || grantResults.length <= 0) {
            return;
        }
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
        AlertDialog alertDialog = this.dialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        if (this.mIsForceUpdate) {
            new Thread() { // from class: com.psychiatrygarden.activity.ForceUpdateActivity.4
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() throws IOException {
                    super.run();
                    ForceUpdateActivity forceUpdateActivity = ForceUpdateActivity.this;
                    forceUpdateActivity.downLoadApp(forceUpdateActivity.getIntent().getStringExtra("app_link"));
                }
            }.start();
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this.mContext, UpgradeService.class);
        intent.putExtra("download_url", getIntent().getStringExtra("app_link"));
        intent.putExtra("verCode", this.verCode);
        startService(intent);
        EventBus.getDefault().post(new ExitUpdateEvent());
        finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setSwipeBackEnable(false);
        this.apkPath = ProjectApp.instance().getStoragePath();
        File file = new File(this.apkPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
        initProgressDialog();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void showDownloadDialog() {
        LoadingProgressDialog loadingProgressDialog = this.mLoadingProgressDig;
        if (loadingProgressDialog == null || loadingProgressDialog.isShowing() || isFinishing()) {
            return;
        }
        this.mLoadingProgressDig.show();
    }
}

package com.psychiatrygarden.interfaceclass;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.widget.RemoteViews;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.google.android.exoplayer2.C;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.mobile.auth.gatewayauth.ResultCode;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ActivityLifecycleCallbacks;
import com.psychiatrygarden.activity.WelcomeActivity;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.yikaobang.yixue.BuildConfig;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.io.IOException;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

/* loaded from: classes4.dex */
public class UpgradeService extends Service {
    private String apkPath;
    private Intent updateIntent;
    private Notification updateNotification;
    private NotificationManager updateNotificationMgr;
    private String verCode;
    private final int STATUS_DOWNLOADING = 1;
    private final int STATUS_DOWNLOAD_FAILED = 2;
    private final int STATUS_DOWNLOAD_SUCCESS = 3;
    private final int notificationId = 10001;
    private String download_url = "";

    @SuppressLint({"HandlerLeak"})
    private final Handler handler = new Handler() { // from class: com.psychiatrygarden.interfaceclass.UpgradeService.2
        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int i2 = msg.what;
            if (i2 == 1) {
                System.out.println(msg.arg1);
                UpgradeService.this.updateNotification.contentView.setProgressBar(R.id.pb_notifi, 100, msg.arg1, false);
                UpgradeService.this.updateNotification.contentView.setTextViewText(R.id.tv_downInfo, "下载中" + msg.arg1 + "%");
                UpgradeService.this.updateNotificationMgr.notify(10001, UpgradeService.this.updateNotification);
                return;
            }
            if (i2 == 2) {
                UpgradeService.this.updateNotification.contentView.setTextViewText(R.id.tv_downInfo, "下载失败");
                UpgradeService.this.updateNotificationMgr.notify(10001, UpgradeService.this.updateNotification);
                UpgradeService upgradeService = UpgradeService.this;
                upgradeService.stopService(upgradeService.updateIntent);
                return;
            }
            if (i2 != 3) {
                UpgradeService upgradeService2 = UpgradeService.this;
                upgradeService2.stopService(upgradeService2.updateIntent);
                return;
            }
            if (!new File(UpgradeService.this.apkPath, "yikaobang.apk").exists()) {
                Message messageObtainMessage = UpgradeService.this.handler.obtainMessage();
                messageObtainMessage.what = 2;
                UpgradeService.this.handler.sendMessage(messageObtainMessage);
                return;
            }
            UpgradeService.this.updateNotification.contentView.setTextViewText(R.id.tv_downInfo, "下载成功,点击安装。");
            UpgradeService.this.updateNotification.contentView.setProgressBar(R.id.pb_notifi, 100, 100, false);
            UpgradeService.this.updateNotificationMgr.notify(10001, UpgradeService.this.updateNotification);
            UpgradeService upgradeService3 = UpgradeService.this;
            upgradeService3.stopService(upgradeService3.updateIntent);
            UpgradeService.this.updateNotificationMgr.cancelAll();
            EventBus.getDefault().post("DOWNLOAD_SUCCESS");
        }
    };

    public class updateThread implements Runnable {
        Message msg;

        public updateThread() {
            this.msg = UpgradeService.this.handler.obtainMessage();
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                UpgradeService upgradeService = UpgradeService.this;
                upgradeService.downLoadApp(upgradeService.download_url);
            } catch (Exception e2) {
                e2.printStackTrace();
                this.msg.what = 2;
                UpgradeService.this.handler.sendMessage(this.msg);
            }
        }
    }

    public void downLoadApp(String updateUrl) throws IOException {
        File file = new File(this.apkPath, "yikaobang.apk");
        if (file.exists()) {
            PackageInfo packageArchiveInfo = getPackageManager().getPackageArchiveInfo(this.apkPath + "/yikaobang.apk", 1);
            if (packageArchiveInfo != null) {
                if (String.valueOf(this.verCode).equals(String.valueOf(packageArchiveInfo.versionCode))) {
                    EventBus.getDefault().post("DOWNLOAD_SUCCESS");
                    return;
                }
            }
        }
        try {
            boolean zExists = file.exists();
            String str = ResultCode.MSG_SUCCESS;
            if (zExists) {
                boolean zDelete = file.delete();
                String simpleName = getClass().getSimpleName();
                StringBuilder sb = new StringBuilder();
                sb.append("删除文件");
                sb.append(zDelete ? ResultCode.MSG_SUCCESS : ResultCode.MSG_FAILED);
                LogUtils.e(simpleName, sb.toString());
            }
            boolean zCreateNewFile = file.createNewFile();
            String simpleName2 = getClass().getSimpleName();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("创建文件");
            if (!zCreateNewFile) {
                str = ResultCode.MSG_FAILED;
            }
            sb2.append(str);
            LogUtils.e(simpleName2, sb2.toString());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        new FinalHttp().download(updateUrl, file.getAbsolutePath(), new AjaxCallBack<File>() { // from class: com.psychiatrygarden.interfaceclass.UpgradeService.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                Message messageObtainMessage = UpgradeService.this.handler.obtainMessage();
                messageObtainMessage.what = 2;
                UpgradeService.this.handler.sendMessage(messageObtainMessage);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onLoading(long count, long current) {
                super.onLoading(count, current);
                Message messageObtainMessage = UpgradeService.this.handler.obtainMessage();
                messageObtainMessage.what = 1;
                messageObtainMessage.arg1 = (int) ((current * 100) / count);
                UpgradeService.this.handler.sendMessage(messageObtainMessage);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(File t2) {
                super.onSuccess((AnonymousClass1) t2);
                Message messageObtainMessage = UpgradeService.this.handler.obtainMessage();
                messageObtainMessage.what = 3;
                UpgradeService.this.handler.sendMessage(messageObtainMessage);
            }
        });
    }

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // android.app.Service
    public void onStart(Intent intent, int startId) {
        this.apkPath = ProjectApp.instance().getStoragePath();
        try {
            if (!TextUtils.isEmpty(intent.getExtras().getString("download_url"))) {
                this.verCode = intent.getStringExtra("verCode");
                this.download_url = intent.getStringExtra("download_url");
                if (this.apkPath != null) {
                    File file = new File(this.apkPath, "yikaobang.apk");
                    if (file.exists()) {
                        PackageInfo packageArchiveInfo = ProjectApp.instance().getPackageManager().getPackageArchiveInfo(this.apkPath + "/yikaobang.apk", 1);
                        if (packageArchiveInfo != null) {
                            if (String.valueOf(this.verCode).equals(String.valueOf(packageArchiveInfo.versionCode))) {
                                EventBus.getDefault().post("DOWNLOAD_SUCCESS");
                                LogUtils.d(getClass().getSimpleName(), "apk文件已存在，启动安装");
                                return;
                            }
                        }
                    }
                    if (file.exists()) {
                        boolean zDelete = file.delete();
                        String simpleName = getClass().getSimpleName();
                        StringBuilder sb = new StringBuilder();
                        sb.append("删除文件");
                        sb.append(zDelete ? ResultCode.MSG_SUCCESS : ResultCode.MSG_FAILED);
                        LogUtils.e(simpleName, sb.toString());
                        if (!zDelete) {
                            Message messageObtain = Message.obtain();
                            messageObtain.what = 2;
                            this.handler.sendMessage(messageObtain);
                            return;
                        }
                    }
                    NotificationManager notificationManager = (NotificationManager) getSystemService(RemoteMessageConst.NOTIFICATION);
                    this.updateNotificationMgr = notificationManager;
                    int i2 = Build.VERSION.SDK_INT;
                    if (i2 >= 26 && notificationManager.getNotificationChannel("ykbnotid").getImportance() == 0) {
                        NewToast.showShort(this, "请手动将通知打开", 0).show();
                    }
                    if (((ActivityLifecycleCallbacks) ProjectApp.instance().activityLifecycleCallbacks).isFront()) {
                        this.updateIntent = new Intent(this, (Class<?>) WelcomeActivity.class);
                    } else {
                        Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(BuildConfig.APPLICATION_ID);
                        this.updateIntent = launchIntentForPackage;
                        launchIntentForPackage.setFlags(C.ENCODING_PCM_32BIT);
                    }
                    if (i2 >= 31) {
                        PendingIntent.getActivity(this, 0, this.updateIntent, 67108864);
                    } else {
                        PendingIntent.getActivity(this, 0, this.updateIntent, 0);
                    }
                    Notification notificationBuild = new NotificationCompat.Builder(this, "ykbnotid").setOnlyAlertOnce(true).build();
                    this.updateNotification = notificationBuild;
                    notificationBuild.icon = R.drawable.app_icon;
                    notificationBuild.contentView = new RemoteViews(getPackageName(), R.layout.notification_progressbar);
                    this.updateNotification.contentView.setProgressBar(R.id.pb_notifi, 100, 0, false);
                    Notification notification = this.updateNotification;
                    notification.contentIntent = null;
                    this.updateNotificationMgr.notify(10001, notification);
                    new Thread(new updateThread()).start();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        super.onStart(intent, startId);
    }
}

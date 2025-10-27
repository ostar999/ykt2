package com.aliyun.svideo.common.utils.upgrade;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.aliyun.svideo.common.R;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.aliyun.svideo.common.widget.CustomProgressDialog;
import com.azhon.appupdate.config.Constant;
import com.hjq.permissions.Permission;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class AutoUpgradeClient {
    private static final int DOWN_ERROR = 3;
    private static final int DOWN_OVER = 2;
    private static final int DOWN_UPDATE = 1;
    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 10001;
    private static String TAG = "com.aliyun.svideo.common.utils.upgrade.AutoUpgradeClient";
    private static String UPGRADE_JSON_BASE_URL = "https://alivc-demo-cms.alicdn.com";
    private static String outPath;
    private static int progress;
    private static CustomProgressDialog progressDialog;
    private static Context sContext;
    private static String sOutputBasePath = Environment.getExternalStorageDirectory() + "/Download/" + File.separator + "aliyunVideoAllmodule";
    private static Handler mHandler = null;

    public static void checkUpgrade(Context context, String str, final int i2) {
        sContext = context;
        final String str2 = UPGRADE_JSON_BASE_URL + str;
        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.aliyun.svideo.common.utils.upgrade.AutoUpgradeClient.2
            @Override // java.lang.Runnable
            public void run() throws IOException {
                StringBuffer stringBuffer = new StringBuffer();
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str2).openConnection();
                    httpURLConnection.connect();
                    if (httpURLConnection.getResponseCode() == 200) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                        while (true) {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            } else {
                                stringBuffer.append(line);
                            }
                        }
                        String string = stringBuffer.toString();
                        Log.d("text", "版本升级信息:" + string);
                        JSONObject jSONObject = new JSONObject(string);
                        final UpgradeBean upgradeBean = new UpgradeBean();
                        upgradeBean.setVersionName(jSONObject.optString("versionName"));
                        upgradeBean.setVersionCode(jSONObject.optInt("versionCode"));
                        upgradeBean.setDescribe(jSONObject.optString("describe"));
                        upgradeBean.setUrl(jSONObject.optString("url"));
                        Log.i(AutoUpgradeClient.TAG, "当前版本code = " + i2 + " ,最新版本code = " + upgradeBean.getVersionCode());
                        if (upgradeBean.getVersionCode() > i2) {
                            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aliyun.svideo.common.utils.upgrade.AutoUpgradeClient.2.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    AutoUpgradeClient.showHintDialog(upgradeBean);
                                }
                            });
                        } else {
                            AutoUpgradeClient.release();
                        }
                    }
                } catch (Exception e2) {
                    AutoUpgradeClient.release();
                    e2.printStackTrace();
                }
            }
        });
    }

    public static String getUpgradeJsonBaseUrl() {
        return UPGRADE_JSON_BASE_URL;
    }

    private static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw null;
        }
        if (Build.VERSION.SDK_INT < 24) {
            return Uri.fromFile(file);
        }
        return FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
    }

    private static void initHandler() {
        mHandler = new Handler() { // from class: com.aliyun.svideo.common.utils.upgrade.AutoUpgradeClient.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i2 = message.what;
                if (i2 == 1) {
                    AutoUpgradeClient.progressDialog.setProgress(AutoUpgradeClient.progress);
                } else if (i2 == 2) {
                    AutoUpgradeClient.progressDialog.dismiss();
                    AutoUpgradeClient.installApk(AutoUpgradeClient.outPath);
                } else if (i2 == 3) {
                    AutoUpgradeClient.progressDialog.dismiss();
                }
                super.handleMessage(message);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void installApk(String str) {
        if (sContext != null) {
            File file = new File(str);
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(1);
            intent.addFlags(268435456);
            intent.setDataAndType(getUriForFile(sContext, file), "application/vnd.android.package-archive");
            Log.e(TAG, "installApk: ");
            sContext.startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void release() {
        sContext = null;
        mHandler = null;
    }

    public static void setUpgradeJsonBaseUrl(String str) {
        UPGRADE_JSON_BASE_URL = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void showHintDialog(final UpgradeBean upgradeBean) {
        if (sContext == null) {
            return;
        }
        new AlertDialog.Builder(sContext).setPositiveButton(sContext.getResources().getString(R.string.aliyun_common_confirm), new DialogInterface.OnClickListener() { // from class: com.aliyun.svideo.common.utils.upgrade.AutoUpgradeClient.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                dialogInterface.dismiss();
                AutoUpgradeClient.startDownload(upgradeBean);
            }
        }).setNegativeButton(sContext.getResources().getString(R.string.aliyun_common_cancel), new DialogInterface.OnClickListener() { // from class: com.aliyun.svideo.common.utils.upgrade.AutoUpgradeClient.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                dialogInterface.dismiss();
            }
        }).setMessage(upgradeBean.getDescribe()).setTitle(sContext.getResources().getString(R.string.aliyun_common_update_app)).setCancelable(false).create().show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void startDownload(final UpgradeBean upgradeBean) {
        if (sContext == null) {
            return;
        }
        if (ContextCompat.checkSelfPermission(sContext, Permission.WRITE_EXTERNAL_STORAGE) != 0) {
            ActivityCompat.requestPermissions((Activity) sContext, new String[]{Permission.WRITE_EXTERNAL_STORAGE}, 10001);
            Log.e(TAG, "自动升级 Failure : Permission Not WRITE_EXTERNAL_STORAGE  ");
            return;
        }
        Log.i(TAG, "自动升级,----------------- start ----------------");
        outPath = sOutputBasePath + upgradeBean.getVersionCode() + Constant.APK_SUFFIX;
        CustomProgressDialog customProgressDialog = new CustomProgressDialog(sContext);
        progressDialog = customProgressDialog;
        customProgressDialog.setMaxProgress(100);
        progressDialog.setMessage(upgradeBean.getDescribe());
        progressDialog.setCancelable(false);
        progressDialog.setTitle(sContext.getResources().getString(R.string.aliyun_common_updating));
        progressDialog.show();
        if (mHandler == null) {
            initHandler();
        }
        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.aliyun.svideo.common.utils.upgrade.AutoUpgradeClient.5
            @Override // java.lang.Runnable
            public void run() throws IOException {
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(upgradeBean.getUrl()).openConnection();
                    httpURLConnection.connect();
                    int contentLength = httpURLConnection.getContentLength();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    FileOutputStream fileOutputStream = new FileOutputStream(AutoUpgradeClient.outPath);
                    byte[] bArr = new byte[1024];
                    int i2 = 0;
                    while (true) {
                        int i3 = inputStream.read(bArr);
                        if (i3 == -1) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, i3);
                        i2 += i3;
                        int unused = AutoUpgradeClient.progress = (int) ((i2 / contentLength) * 100.0f);
                        AutoUpgradeClient.mHandler.sendEmptyMessage(1);
                        if (AutoUpgradeClient.progress == 100) {
                            AutoUpgradeClient.mHandler.sendEmptyMessage(2);
                            break;
                        }
                    }
                    fileOutputStream.close();
                    inputStream.close();
                } catch (Exception e2) {
                    AutoUpgradeClient.mHandler.sendEmptyMessage(3);
                    AutoUpgradeClient.release();
                    e2.printStackTrace();
                }
            }
        });
    }
}

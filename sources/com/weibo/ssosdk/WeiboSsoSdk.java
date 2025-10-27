package com.weibo.ssosdk;

import android.text.TextUtils;
import com.google.android.exoplayer2.C;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class WeiboSsoSdk {
    private static final String AID_FILE_NAME = "weibo_sso_sdk_aid";
    private static final String INIT_FILE_NAME = "weibo_sso_sdk_init";
    private static final String LOGIN_URL = "https://login.sina.com.cn/visitor/signin";
    private static final int SDK_ACT_UPLOAD = 2;
    private static final int SDK_ACT_VISITORLOGIN = 1;
    public static final String SDK_VERSION_CODE = "2.0";
    private static final String TAG = "WeiboSsoSdk";
    private static final int VERSION = 1;
    private static WeiboSsoSdkConfig config;
    private static WeiboSsoSdk sInstance;
    private int mCallCount;
    private VisitorLoginInfo mVisitorLoginInfo;
    private volatile ReentrantLock mTaskLock = new ReentrantLock(true);
    private boolean isFirstUpload = true;

    public static final class VisitorLoginInfo {
        private String mAid = "";
        private String mSubCookie = "";

        public static VisitorLoginInfo parseJson(String str) throws Exception {
            VisitorLoginInfo visitorLoginInfo = new VisitorLoginInfo();
            try {
                JSONObject jSONObject = new JSONObject(str);
                String strOptString = jSONObject.optString("retcode", "");
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                if (strOptString.equals("20000000") && jSONObject2 != null) {
                    visitorLoginInfo.mAid = jSONObject2.optString(CommonNetImpl.AID, "");
                    visitorLoginInfo.mSubCookie = jSONObject2.optString("sub", "");
                    return visitorLoginInfo;
                }
                throw new Exception("error： " + strOptString + " msg:" + jSONObject.optString("msg", ""));
            } catch (Exception e2) {
                throw e2;
            }
        }

        public final VisitorLoginInfo cloneAidInfo() {
            VisitorLoginInfo visitorLoginInfo = new VisitorLoginInfo();
            visitorLoginInfo.mAid = this.mAid;
            visitorLoginInfo.mSubCookie = this.mSubCookie;
            return visitorLoginInfo;
        }

        public final String getAid() {
            return this.mAid;
        }

        public final String getVisitorSub() {
            return this.mSubCookie;
        }
    }

    static {
        System.loadLibrary("sharewind");
    }

    private WeiboSsoSdk() throws Exception {
        WeiboSsoSdkConfig weiboSsoSdkConfig = config;
        if (weiboSsoSdkConfig == null || !weiboSsoSdkConfig.verify()) {
            throw new Exception("config error");
        }
        this.mCallCount = 0;
        new Thread(new Runnable() { // from class: com.weibo.ssosdk.WeiboSsoSdk.1
            @Override // java.lang.Runnable
            public void run() throws InterruptedException {
                while (true) {
                    try {
                        Thread.sleep(86400000L);
                        WeiboSsoSdk.getInstance().updateInfo((WeiboSsoSdk.this.mVisitorLoginInfo == null || TextUtils.isEmpty(WeiboSsoSdk.this.mVisitorLoginInfo.getAid())) ? WeiboSsoSdk.this.loadAidFromCache() : WeiboSsoSdk.this.mVisitorLoginInfo.getAid(), 2);
                    } catch (Exception unused) {
                    }
                }
            }
        }).start();
        new Thread(new Runnable() { // from class: com.weibo.ssosdk.WeiboSsoSdk.2
            @Override // java.lang.Runnable
            public void run() throws InterruptedException {
                try {
                    Thread.sleep(60000L);
                    if (WeiboSsoSdk.this.isFirstUpload) {
                        WeiboSsoSdk.this.updateInfo((WeiboSsoSdk.this.mVisitorLoginInfo == null || TextUtils.isEmpty(WeiboSsoSdk.this.mVisitorLoginInfo.getAid())) ? WeiboSsoSdk.this.loadAidFromCache() : WeiboSsoSdk.this.mVisitorLoginInfo.getAid(), 2);
                    }
                } catch (Exception unused) {
                }
            }
        }).start();
    }

    private static void LogD(String str) {
    }

    private static void LogE(String str) {
    }

    private synchronized void cacheAidInfo(String str) {
        FileOutputStream fileOutputStream;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(getAidInfoFile(1));
        } catch (Exception unused) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            fileOutputStream.write(str.getBytes());
            try {
                fileOutputStream.close();
            } catch (IOException unused2) {
            }
        } catch (Exception unused3) {
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException unused4) {
                }
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException unused5) {
                }
            }
            throw th;
        }
    }

    private String do_post(String str) throws IOException {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(LOGIN_URL).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setReadTimeout(3000);
            httpURLConnection.setConnectTimeout(1000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(str.getBytes());
            outputStream.flush();
            if (httpURLConnection.getResponseCode() != 200) {
                return null;
            }
            InputStream inputStream = httpURLConnection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = inputStream.read(bArr);
                if (i2 == -1) {
                    inputStream.close();
                    byteArrayOutputStream.close();
                    return new String(byteArrayOutputStream.toByteArray());
                }
                byteArrayOutputStream.write(bArr, 0, i2);
            }
        } catch (Exception unused) {
            return null;
        }
    }

    private File getAidInfoFile(int i2) {
        return new File(config.getApplicationContext().getFilesDir(), AID_FILE_NAME.concat(String.valueOf(i2)));
    }

    private File getInitFile() {
        return new File(config.getApplicationContext().getFilesDir(), INIT_FILE_NAME);
    }

    public static synchronized WeiboSsoSdk getInstance() {
        if (sInstance == null) {
            sInstance = new WeiboSsoSdk();
        }
        return sInstance;
    }

    public static synchronized boolean initConfig(WeiboSsoSdkConfig weiboSsoSdkConfig) {
        if (weiboSsoSdkConfig == null) {
            return false;
        }
        if (!weiboSsoSdkConfig.verify()) {
            return false;
        }
        if (config != null) {
            return false;
        }
        WeiboSsoSdkConfig weiboSsoSdkConfig2 = (WeiboSsoSdkConfig) weiboSsoSdkConfig.clone();
        config = weiboSsoSdkConfig2;
        MfpBuilder.init(weiboSsoSdkConfig2.getApplicationContext());
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String loadAidFromCache() throws Throwable {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(getAidInfoFile(1));
        } catch (Exception unused) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            byte[] bArr = new byte[fileInputStream.available()];
            fileInputStream.read(bArr);
            String str = new String(bArr);
            try {
                fileInputStream.close();
            } catch (IOException unused2) {
            }
            return str;
        } catch (Exception unused3) {
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 == null) {
                return "";
            }
            try {
                fileInputStream2.close();
                return "";
            } catch (IOException unused4) {
                return "";
            }
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException unused5) {
                }
            }
            throw th;
        }
    }

    private String loadInitFile() throws Throwable {
        FileInputStream fileInputStream;
        Throwable th;
        try {
            fileInputStream = new FileInputStream(getInitFile());
        } catch (Exception unused) {
            fileInputStream = null;
        } catch (Throwable th2) {
            fileInputStream = null;
            th = th2;
        }
        try {
            byte[] bArr = new byte[fileInputStream.available()];
            fileInputStream.read(bArr);
            String str = new String(bArr);
            try {
                fileInputStream.close();
            } catch (IOException unused2) {
            }
            return str;
        } catch (Exception unused3) {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException unused4) {
                }
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException unused5) {
                }
            }
            throw th;
        }
    }

    private native String riseWind(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i2, int i3);

    private synchronized void saveInitFile(String str) {
        FileOutputStream fileOutputStream;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(getInitFile());
        } catch (Exception unused) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            fileOutputStream.write(str.getBytes());
            try {
                fileOutputStream.close();
            } catch (IOException unused2) {
            }
        } catch (Exception unused3) {
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException unused4) {
                }
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException unused5) {
                }
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateInfo(String str, int i2) throws Exception {
        String strEncode;
        if (TextUtils.isEmpty(config.getAppKey(false))) {
            return;
        }
        if (!this.mTaskLock.tryLock()) {
            this.mTaskLock.lock();
            this.mTaskLock.unlock();
            return;
        }
        this.isFirstUpload = false;
        String mfp = MfpBuilder.getMfp(config.getApplicationContext());
        try {
            strEncode = URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException unused) {
            strEncode = "";
        }
        String strDo_post = do_post(riseWind(config.getAppKey(true), config.getApplicationContext().getPackageName(), strEncode, mfp, config.getFrom(true), config.getOldWm(true), config.getWm(true), config.getSub(true), config.getSmid(true), config.getExtraString(true), i2, this.mCallCount));
        this.mCallCount++;
        if (strDo_post == null) {
            this.mTaskLock.unlock();
            throw new Exception("network error.");
        }
        try {
            VisitorLoginInfo json = VisitorLoginInfo.parseJson(strDo_post);
            if (json != null && !TextUtils.isEmpty(json.getAid())) {
                cacheAidInfo(json.getAid());
            }
            if (i2 == 1) {
                this.mVisitorLoginInfo = json;
            }
            this.mTaskLock.unlock();
        } catch (Exception e2) {
            this.mTaskLock.unlock();
            throw e2;
        }
    }

    public String getAid() throws Exception {
        String strLoadAidFromCache = loadAidFromCache();
        if (!TextUtils.isEmpty(strLoadAidFromCache)) {
            return strLoadAidFromCache;
        }
        VisitorLoginInfo visitorLoginInfo = this.mVisitorLoginInfo;
        if (visitorLoginInfo == null || TextUtils.isEmpty(visitorLoginInfo.getAid())) {
            Thread thread = new Thread(new Runnable() { // from class: com.weibo.ssosdk.WeiboSsoSdk.6
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        WeiboSsoSdk.this.updateInfo("", 1);
                    } catch (Exception unused) {
                    }
                }
            });
            thread.start();
            thread.join(C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
            VisitorLoginInfo visitorLoginInfo2 = this.mVisitorLoginInfo;
            if (visitorLoginInfo2 == null || TextUtils.isEmpty(visitorLoginInfo2.getAid())) {
                throw new Exception("visitor login failed");
            }
        }
        return this.mVisitorLoginInfo.getAid();
    }

    public void updateSub(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        config.setSub(str);
        String visitorSub = this.mVisitorLoginInfo.getVisitorSub();
        if (TextUtils.isEmpty(visitorSub) || !visitorSub.equals(str)) {
            new Thread(new Runnable() { // from class: com.weibo.ssosdk.WeiboSsoSdk.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        WeiboSsoSdk.this.updateInfo((WeiboSsoSdk.this.mVisitorLoginInfo == null || TextUtils.isEmpty(WeiboSsoSdk.this.mVisitorLoginInfo.getAid())) ? WeiboSsoSdk.this.loadAidFromCache() : WeiboSsoSdk.this.mVisitorLoginInfo.getAid(), 2);
                    } catch (Exception unused) {
                    }
                }
            }).start();
        }
    }

    public VisitorLoginInfo visitorLogin() throws Exception {
        VisitorLoginInfo visitorLoginInfo = this.mVisitorLoginInfo;
        if (visitorLoginInfo == null || TextUtils.isEmpty(visitorLoginInfo.getAid()) || TextUtils.isEmpty(this.mVisitorLoginInfo.getVisitorSub())) {
            Thread thread = new Thread(new Runnable() { // from class: com.weibo.ssosdk.WeiboSsoSdk.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        WeiboSsoSdk.this.updateInfo("", 1);
                    } catch (Exception unused) {
                    }
                }
            });
            thread.start();
            thread.join(C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
            VisitorLoginInfo visitorLoginInfo2 = this.mVisitorLoginInfo;
            if (visitorLoginInfo2 == null || TextUtils.isEmpty(visitorLoginInfo2.getAid()) || TextUtils.isEmpty(this.mVisitorLoginInfo.getVisitorSub())) {
                throw new Exception("visitor login failed");
            }
        }
        return this.mVisitorLoginInfo;
    }

    public void getAid(final AidListener aidListener) throws Throwable {
        String strLoadAidFromCache = loadAidFromCache();
        if (!TextUtils.isEmpty(strLoadAidFromCache)) {
            aidListener.handler(strLoadAidFromCache);
        }
        VisitorLoginInfo visitorLoginInfo = this.mVisitorLoginInfo;
        if (visitorLoginInfo != null && !TextUtils.isEmpty(visitorLoginInfo.getAid())) {
            aidListener.handler(this.mVisitorLoginInfo.getAid());
        } else {
            Executors.newSingleThreadExecutor().execute(new Runnable() { // from class: com.weibo.ssosdk.WeiboSsoSdk.7
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        WeiboSsoSdk.this.updateInfo("", 1);
                    } catch (Exception unused) {
                    }
                    if (WeiboSsoSdk.this.mVisitorLoginInfo == null) {
                        WeiboSsoSdk.this.mVisitorLoginInfo = new VisitorLoginInfo();
                    }
                    aidListener.handler(WeiboSsoSdk.this.mVisitorLoginInfo.getAid());
                }
            });
        }
    }

    public void visitorLogin(final VisitorLoginListener visitorLoginListener) {
        VisitorLoginInfo visitorLoginInfo = this.mVisitorLoginInfo;
        if (visitorLoginInfo != null && !TextUtils.isEmpty(visitorLoginInfo.getAid()) && !TextUtils.isEmpty(this.mVisitorLoginInfo.getVisitorSub())) {
            visitorLoginListener.handler(this.mVisitorLoginInfo);
        } else {
            Executors.newSingleThreadExecutor().execute(new Runnable() { // from class: com.weibo.ssosdk.WeiboSsoSdk.5
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        WeiboSsoSdk.this.updateInfo("", 1);
                    } catch (Exception unused) {
                    }
                    if (WeiboSsoSdk.this.mVisitorLoginInfo == null) {
                        WeiboSsoSdk.this.mVisitorLoginInfo = new VisitorLoginInfo();
                    }
                    visitorLoginListener.handler(WeiboSsoSdk.this.mVisitorLoginInfo);
                }
            });
        }
    }
}

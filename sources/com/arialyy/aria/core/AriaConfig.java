package com.arialyy.aria.core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Handler;
import android.os.Looper;
import com.arialyy.aria.core.config.AppConfig;
import com.arialyy.aria.core.config.Configuration;
import com.arialyy.aria.core.config.DGroupConfig;
import com.arialyy.aria.core.config.DownloadConfig;
import com.arialyy.aria.core.config.UploadConfig;
import com.arialyy.aria.core.config.XMLReader;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import com.arialyy.aria.util.FileUtil;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/* loaded from: classes2.dex */
public class AriaConfig {
    private static Context APP = null;
    public static final String DOWNLOAD_TEMP_DIR = "/Aria/temp/download/";
    public static final String IGNORE_CLASS_KLASS = "shadow$_klass_";
    public static final String IGNORE_CLASS_MONITOR = "shadow$_monitor_";
    private static volatile AriaConfig Instance = null;
    private static final String TAG = "AriaConfig";
    public static final String UPLOAD_TEMP_DIR = "/Aria/temp/upload/";
    private static boolean isConnectedNet = true;
    private AppConfig mAConfig;
    private Handler mAriaHandler;
    private DownloadConfig mDConfig;
    private DGroupConfig mDGConfig;
    private UploadConfig mUConfig;

    private AriaConfig(Context context) {
        APP = context.getApplicationContext();
    }

    public static AriaConfig getInstance() {
        if (Instance == null) {
            ALog.e(TAG, "请使用init()初始化");
        }
        return Instance;
    }

    public static AriaConfig init(Context context) {
        if (Instance == null) {
            synchronized (AriaConfig.class) {
                if (Instance == null) {
                    Instance = new AriaConfig(context);
                    Instance.initData();
                }
            }
        }
        return Instance;
    }

    private void initConfig() throws SAXException, IOException {
        this.mDConfig = Configuration.getInstance().downloadCfg;
        this.mUConfig = Configuration.getInstance().uploadCfg;
        this.mAConfig = Configuration.getInstance().appCfg;
        this.mDGConfig = Configuration.getInstance().dGroupCfg;
        File file = new File(APP.getFilesDir().getPath() + Configuration.XML_FILE);
        File file2 = new File(APP.getFilesDir().getPath() + "/temp");
        if (file.exists()) {
            try {
                String fileMD5 = CommonUtil.getFileMD5(file);
                File file3 = new File(APP.getFilesDir().getPath() + "/temp.xml");
                if (file3.exists()) {
                    file3.delete();
                }
                FileUtil.createFileFormInputStream(APP.getAssets().open("aria_config.xml"), file3.getPath());
                if (!CommonUtil.checkMD5(fileMD5, file3) || !Configuration.getInstance().configExists()) {
                    loadConfig();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } else {
            loadConfig();
        }
        if (file2.exists()) {
            File file4 = new File(APP.getFilesDir().getPath() + DOWNLOAD_TEMP_DIR);
            file4.mkdirs();
            file2.renameTo(file4);
        }
    }

    private void initData() throws SAXException, IOException {
        initConfig();
        regNetCallBack(APP);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isNetworkAvailable() {
        NetworkInfo[] allNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) getAPP().getSystemService("connectivity");
        if (connectivityManager != null && (allNetworkInfo = connectivityManager.getAllNetworkInfo()) != null && allNetworkInfo.length > 0) {
            for (NetworkInfo networkInfo : allNetworkInfo) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    private void loadConfig() throws SAXException, IOException {
        try {
            SAXParserFactory.newInstance().newSAXParser().parse(APP.getAssets().open("aria_config.xml"), new XMLReader());
            FileUtil.createFileFormInputStream(APP.getAssets().open("aria_config.xml"), APP.getFilesDir().getPath() + Configuration.XML_FILE);
        } catch (IOException | ParserConfigurationException | SAXException e2) {
            ALog.e(TAG, e2.toString());
        }
    }

    private void regNetCallBack(Context context) {
        ConnectivityManager connectivityManager;
        isConnectedNet = isNetworkAvailable();
        if (getAConfig().isNetCheck() && (connectivityManager = (ConnectivityManager) context.getSystemService("connectivity")) != null) {
            connectivityManager.registerNetworkCallback(new NetworkRequest.Builder().addCapability(12).addTransportType(1).addTransportType(0).build(), new ConnectivityManager.NetworkCallback() { // from class: com.arialyy.aria.core.AriaConfig.1
                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                    boolean unused = AriaConfig.isConnectedNet = true;
                    ALog.d(AriaConfig.TAG, "onAvailable, isConnectNet = true");
                }

                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onLost(Network network) {
                    super.onLost(network);
                    boolean unused = AriaConfig.isConnectedNet = AriaConfig.this.isNetworkAvailable();
                    ALog.d(AriaConfig.TAG, "onLost, isConnectNet = " + AriaConfig.isConnectedNet);
                }
            });
        }
    }

    public AppConfig getAConfig() {
        return this.mAConfig;
    }

    public Context getAPP() {
        return APP;
    }

    public synchronized Handler getAriaHandler() {
        if (this.mAriaHandler == null) {
            this.mAriaHandler = new Handler(Looper.getMainLooper());
        }
        return this.mAriaHandler;
    }

    public DownloadConfig getDConfig() {
        return this.mDConfig;
    }

    public DGroupConfig getDGConfig() {
        return this.mDGConfig;
    }

    public UploadConfig getUConfig() {
        return this.mUConfig;
    }

    public boolean isConnectedNet() {
        return isConnectedNet;
    }
}

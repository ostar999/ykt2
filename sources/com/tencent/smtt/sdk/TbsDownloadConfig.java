package com.tencent.smtt.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.smtt.sdk.TbsLogReport;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes6.dex */
public class TbsDownloadConfig {
    public static final int CMD_ID_DOWNLOAD_FILE = 101;
    public static final int CMD_ID_FILE_UPLOAD = 100;
    public static final long DEFAULT_RETRY_INTERVAL_SEC = 86400;
    public static final int ERROR_DOWNLOAD = 2;
    public static final int ERROR_INSTALL = 5;
    public static final int ERROR_LOAD = 6;
    public static final int ERROR_NONE = 1;
    public static final int ERROR_REPORTED = 0;
    public static final int ERROR_UNZIP = 4;
    public static final int ERROR_VERIFY = 3;
    public static final int KEY_TODO = -1;

    /* renamed from: a, reason: collision with root package name */
    private static TbsDownloadConfig f20915a;

    /* renamed from: b, reason: collision with root package name */
    private Context f20916b;
    public SharedPreferences mPreferences;

    /* renamed from: c, reason: collision with root package name */
    private int f20917c = 0;
    public Map<String, Object> mSyncMap = new HashMap();

    public interface TbsConfigKey {
        public static final String KEY_APP_METADATA = "app_metadata";
        public static final String KEY_APP_VERSIONCODE = "app_versioncode";
        public static final String KEY_APP_VERSIONCODE_FOR_SWITCH = "app_versioncode_for_switch";
        public static final String KEY_APP_VERSIONNAME = "app_versionname";
        public static final String KEY_BACKUPCORE_DELFILELIST = "backupcore_delfilelist";
        public static final String KEY_COUNT_REQUEST_FAIL_IN_24HOURS = "count_request_fail_in_24hours";
        public static final String KEY_CPUTYPE_OTHER_STABLE_CORE = "tbs_cpu_type_other_stable_core";
        public static final String KEY_DECOUPLECOREVERSION = "tbs_decouplecoreversion";
        public static final String KEY_DESkEY_TOKEN = "tbs_deskey_token";
        public static final String KEY_DEVICE_CPUABI = "device_cpuabi";
        public static final String KEY_DOWNLOADDECOUPLECORE = "tbs_downloaddecouplecore";
        public static final String KEY_DOWNLOADURL_LIST = "tbs_downloadurl_list";
        public static final String KEY_DOWNLOAD_FAILED_MAX_RETRYTIMES = "tbs_download_failed_max_retrytimes";
        public static final String KEY_DOWNLOAD_FAILED_RETRYTIMES = "tbs_download_failed_retrytimes";
        public static final String KEY_DOWNLOAD_INTERRUPT_CODE = "tbs_download_interrupt_code";
        public static final String KEY_DOWNLOAD_INTERRUPT_CODE_REASON = "tbs_download_interrupt_code_reason";
        public static final String KEY_DOWNLOAD_INTERRUPT_TIME = "tbs_download_interrupt_time";
        public static final String KEY_DOWNLOAD_MAXFLOW = "tbs_download_maxflow";
        public static final String KEY_DOWNLOAD_MIN_FREE_SPACE = "tbs_download_min_free_space";
        public static final String KEY_DOWNLOAD_SINGLE_TIMEOUT = "tbs_single_timeout";
        public static final String KEY_DOWNLOAD_SUCCESS_MAX_RETRYTIMES = "tbs_download_success_max_retrytimes";
        public static final String KEY_DOWNLOAD_SUCCESS_RETRYTIMES = "tbs_download_success_retrytimes";
        public static final String KEY_FULL_PACKAGE = "request_full_package";
        public static final String KEY_GUID = "tbs_guid";
        public static final String KEY_INSTALL_INTERRUPT_CODE = "tbs_install_interrupt_code";
        public static final String KEY_IS_OVERSEA = "is_oversea";
        public static final String KEY_LAST_CHECK = "last_check";
        public static final String KEY_LAST_DOWNLOAD_DECOUPLE_CORE = "last_download_decouple_core";
        public static final String KEY_LAST_DOWNLOAD_STABLE_CORE_OTHER_CPU = "last_download_stable_core_other_cpu";
        public static final String KEY_LAST_REQUEST_SUCCESS = "last_request_success";
        public static final String KEY_LAST_THIRDAPP_SENDREQUEST_COREVERSION = "last_thirdapp_sendrequest_coreversion";
        public static final String KEY_NEEDDOWNLOAD = "tbs_needdownload";
        public static final String KEY_REQUEST_FAIL = "request_fail";
        public static final String KEY_RESPONSECODE = "tbs_responsecode";
        public static final String KEY_RETRY_INTERVAL = "retry_interval";
        public static final String KEY_STOP_PRE_OAT = "tbs_stop_preoat";
        public static final String KEY_SWITCH_BACKUPCORE_ENABLE = "switch_backupcore_enable";
        public static final String KEY_TBSAPKFILESIZE = "tbs_apkfilesize";
        public static final String KEY_TBSAPK_MD5 = "tbs_apk_md5";
        public static final String KEY_TBSDOWNLOADURL = "tbs_downloadurl";
        public static final String KEY_TBSDOWNLOAD_FLOW = "tbs_downloadflow";
        public static final String KEY_TBSDOWNLOAD_STARTTIME = "tbs_downloadstarttime";
        public static final String KEY_TBS_CORE_LOAD_RENAME_FILE_LOCK_WAIT_ENABLE = "tbs_core_load_rename_file_lock_wait_enable";
        public static final String KEY_TBS_DOWNLOAD_V = "tbs_download_version";
        public static final String KEY_TBS_DOWNLOAD_V_TYPE = "tbs_download_version_type";
        public static final String KEY_USE_BACKUP_VERSION = "use_backup_version";
        public static final String KEY_USE_BUGLY = "tbs_use_bugly";
    }

    private TbsDownloadConfig(Context context) {
        this.mPreferences = context.getSharedPreferences("tbs_download_config", 4);
        Context applicationContext = context.getApplicationContext();
        this.f20916b = applicationContext;
        if (applicationContext == null) {
            this.f20916b = context;
        }
    }

    private boolean a(int i2) {
        if (i2 <= -206 && i2 >= -219) {
            return true;
        }
        if (i2 > -302 || i2 < -316) {
            return i2 <= -318 && i2 >= -322;
        }
        return true;
    }

    private void b(int i2) {
        this.f20917c = i2;
    }

    public static synchronized TbsDownloadConfig getInstance() {
        return f20915a;
    }

    public static synchronized TbsDownloadConfig getInstance(Context context) {
        if (f20915a == null) {
            f20915a = new TbsDownloadConfig(context);
        }
        return f20915a;
    }

    public void clear() {
        try {
            this.mSyncMap.clear();
            SharedPreferences.Editor editorEdit = this.mPreferences.edit();
            editorEdit.clear();
            editorEdit.commit();
        } catch (Exception unused) {
        }
    }

    public synchronized void commit() {
        try {
            SharedPreferences.Editor editorEdit = this.mPreferences.edit();
            for (String str : this.mSyncMap.keySet()) {
                Object obj = this.mSyncMap.get(str);
                if (obj instanceof String) {
                    editorEdit.putString(str, (String) obj);
                } else if (obj instanceof Boolean) {
                    editorEdit.putBoolean(str, ((Boolean) obj).booleanValue());
                } else if (obj instanceof Long) {
                    editorEdit.putLong(str, ((Long) obj).longValue());
                } else if (obj instanceof Integer) {
                    editorEdit.putInt(str, ((Integer) obj).intValue());
                } else if (obj instanceof Float) {
                    editorEdit.putFloat(str, ((Float) obj).floatValue());
                }
            }
            editorEdit.commit();
            this.mSyncMap.clear();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public int getCurrentDownloadInterruptCode() {
        return this.f20917c;
    }

    public synchronized int getDownloadFailedMaxRetrytimes() {
        int i2;
        i2 = this.mPreferences.getInt(TbsConfigKey.KEY_DOWNLOAD_FAILED_MAX_RETRYTIMES, 0);
        if (i2 == 0) {
            i2 = 100;
        }
        return i2;
    }

    public synchronized int getDownloadInterruptCode() {
        int i2;
        if (this.mPreferences.contains(TbsConfigKey.KEY_DOWNLOAD_INTERRUPT_CODE)) {
            i2 = this.mPreferences.getInt(TbsConfigKey.KEY_DOWNLOAD_INTERRUPT_CODE, -99);
            if (i2 == -119 || i2 == -121) {
                i2 = this.mPreferences.getInt(TbsConfigKey.KEY_DOWNLOAD_INTERRUPT_CODE_REASON, -119);
            }
            if (System.currentTimeMillis() - this.mPreferences.getLong(TbsConfigKey.KEY_DOWNLOAD_INTERRUPT_TIME, 0L) > 86400000) {
                i2 -= 98000;
            }
        } else {
            try {
                i2 = !new File(new File(this.f20916b.getFilesDir(), "shared_prefs"), "tbs_download_config").exists() ? -97 : !this.mPreferences.contains(TbsConfigKey.KEY_NEEDDOWNLOAD) ? -96 : TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE;
            } catch (Throwable unused) {
                i2 = -95;
            }
        }
        Context context = this.f20916b;
        if (context == null || !"com.tencent.mobileqq".equals(context.getApplicationInfo().packageName) || "CN".equals(Locale.getDefault().getCountry())) {
            return (i2 * 1000) + this.mPreferences.getInt(TbsConfigKey.KEY_INSTALL_INTERRUPT_CODE, -1);
        }
        return -320;
    }

    public synchronized long getDownloadMaxflow() {
        int i2;
        i2 = this.mPreferences.getInt(TbsConfigKey.KEY_DOWNLOAD_MAXFLOW, 0);
        if (i2 == 0) {
            i2 = 20;
        }
        return i2 * 1024 * 1024;
    }

    public synchronized long getDownloadMinFreeSpace() {
        return (this.mPreferences.getInt(TbsConfigKey.KEY_DOWNLOAD_MIN_FREE_SPACE, 0) != 0 ? r0 : 0) * 1024 * 1024;
    }

    public synchronized long getDownloadSingleTimeout() {
        long j2;
        j2 = this.mPreferences.getLong(TbsConfigKey.KEY_DOWNLOAD_SINGLE_TIMEOUT, 0L);
        if (j2 == 0) {
            j2 = 1200000;
        }
        return j2;
    }

    public synchronized int getDownloadSuccessMaxRetrytimes() {
        int i2;
        i2 = this.mPreferences.getInt(TbsConfigKey.KEY_DOWNLOAD_SUCCESS_MAX_RETRYTIMES, 0);
        if (i2 == 0) {
            i2 = 3;
        }
        return i2;
    }

    public synchronized long getRetryInterval() {
        if (TbsDownloader.getRetryIntervalInSeconds() >= 0) {
            return TbsDownloader.getRetryIntervalInSeconds();
        }
        int cfgRequestMinInterval = TbsPVConfig.getInstance(this.f20916b).getCfgRequestMinInterval();
        if (cfgRequestMinInterval >= 0) {
            return cfgRequestMinInterval;
        }
        return this.mPreferences.getLong(TbsConfigKey.KEY_RETRY_INTERVAL, 86400L);
    }

    public synchronized boolean getTbsCoreLoadRenameFileLockWaitEnable() {
        boolean z2;
        z2 = true;
        try {
            z2 = this.mPreferences.getBoolean(TbsConfigKey.KEY_TBS_CORE_LOAD_RENAME_FILE_LOCK_WAIT_ENABLE, true);
        } catch (Exception unused) {
        }
        return z2;
    }

    public synchronized boolean isOverSea() {
        return this.mPreferences.getBoolean(TbsConfigKey.KEY_IS_OVERSEA, false);
    }

    public synchronized void setDownloadInterruptCode(int i2) {
        b(i2);
        try {
            SharedPreferences.Editor editorEdit = this.mPreferences.edit();
            editorEdit.putInt(TbsConfigKey.KEY_DOWNLOAD_INTERRUPT_CODE, i2);
            editorEdit.putLong(TbsConfigKey.KEY_DOWNLOAD_INTERRUPT_TIME, System.currentTimeMillis());
            editorEdit.apply();
        } catch (Exception unused) {
        }
    }

    public synchronized void setInstallInterruptCode(int i2) {
        SharedPreferences.Editor editorEdit = this.mPreferences.edit();
        editorEdit.putInt(TbsConfigKey.KEY_INSTALL_INTERRUPT_CODE, i2);
        editorEdit.commit();
    }

    public synchronized void setTbsCoreLoadRenameFileLockWaitEnable(boolean z2) {
        try {
            SharedPreferences.Editor editorEdit = this.mPreferences.edit();
            editorEdit.putBoolean(TbsConfigKey.KEY_TBS_CORE_LOAD_RENAME_FILE_LOCK_WAIT_ENABLE, z2);
            editorEdit.commit();
        } catch (Exception unused) {
        }
    }

    public synchronized void uploadDownloadInterruptCodeIfNeeded(Context context) {
        int i2;
        boolean zA;
        if (context != null) {
            try {
                if (this.mPreferences.contains(TbsConfigKey.KEY_DOWNLOAD_INTERRUPT_CODE)) {
                    i2 = this.mPreferences.getInt(TbsConfigKey.KEY_DOWNLOAD_INTERRUPT_CODE, -99);
                    zA = a(i2);
                } else {
                    try {
                        i2 = !new File(new File(this.f20916b.getFilesDir(), "shared_prefs"), "tbs_download_config").exists() ? -97 : !this.mPreferences.contains(TbsConfigKey.KEY_NEEDDOWNLOAD) ? -96 : TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE;
                    } catch (Throwable unused) {
                        i2 = -95;
                    }
                    zA = true;
                }
                if (zA) {
                    TbsLogReport.TbsLogInfo tbsLogInfo = TbsLogReport.getInstance(context).tbsLogInfo();
                    tbsLogInfo.setErrorCode(128);
                    tbsLogInfo.setFailDetail(" " + i2);
                    TbsLogReport.getInstance(context).eventReport(TbsLogReport.EventType.TYPE_DOWNLOAD, tbsLogInfo);
                }
            } catch (Throwable unused2) {
            }
        }
    }
}

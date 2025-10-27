package com.arialyy.aria.core;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import com.arialyy.aria.core.command.CommandManager;
import com.arialyy.aria.core.common.QueueMod;
import com.arialyy.aria.core.config.AppConfig;
import com.arialyy.aria.core.config.DGroupConfig;
import com.arialyy.aria.core.config.DownloadConfig;
import com.arialyy.aria.core.config.UploadConfig;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.download.DownloadGroupEntity;
import com.arialyy.aria.core.download.DownloadReceiver;
import com.arialyy.aria.core.inf.AbsReceiver;
import com.arialyy.aria.core.inf.IReceiver;
import com.arialyy.aria.core.inf.ReceiverType;
import com.arialyy.aria.core.upload.UploadEntity;
import com.arialyy.aria.core.upload.UploadReceiver;
import com.arialyy.aria.orm.DbEntity;
import com.arialyy.aria.orm.DelegateWrapper;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.AriaCrashHandler;
import com.arialyy.aria.util.CommonUtil;
import com.arialyy.aria.util.DeleteURecord;
import com.arialyy.aria.util.RecordUtil;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@TargetApi(14)
/* loaded from: classes2.dex */
public class AriaManager {
    private static Context APP = null;

    @SuppressLint({"StaticFieldLeak"})
    private static volatile AriaManager INSTANCE = null;
    private static final Object LOCK = new Object();
    private static final String TAG = "AriaManager";
    private AriaConfig mConfig;
    private DelegateWrapper mDbWrapper;
    private Map<String, AbsReceiver> mReceivers = new ConcurrentHashMap();

    public class LifeCallback implements Application.ActivityLifecycleCallbacks {
        private LifeCallback() {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            AriaManager.this.removeReceiver(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
        }
    }

    private AriaManager(Context context) {
        APP = context.getApplicationContext();
    }

    private void amendTaskState() throws SQLException {
        Class[] clsArr = {DownloadEntity.class, UploadEntity.class, DownloadGroupEntity.class};
        for (int i2 = 0; i2 < 3; i2++) {
            Class cls = clsArr[i2];
            if (this.mDbWrapper.tableExists(cls)) {
                DbEntity.exeSql(String.format("UPDATE %s SET state=2 WHERE state IN (4,5,6)", cls.getSimpleName()));
            }
        }
    }

    public static AriaManager getInstance() {
        return INSTANCE;
    }

    private String getKey(ReceiverType receiverType, Object obj) {
        return String.format("%s_%s_%s", CommonUtil.getTargetName(obj), receiverType.name(), Integer.valueOf(obj.hashCode()));
    }

    public static AriaManager init(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new AriaManager(context);
                    INSTANCE.initData();
                }
            }
        }
        return INSTANCE;
    }

    private void initAria() throws Throwable {
        AppConfig aConfig = this.mConfig.getAConfig();
        if (aConfig.getUseAriaCrashHandler()) {
            Thread.setDefaultUncaughtExceptionHandler(new AriaCrashHandler());
        }
        aConfig.setLogLevel(aConfig.getLogLevel());
        CommandManager.init();
    }

    private void initData() throws Throwable {
        this.mConfig = AriaConfig.init(APP);
        initDb(APP);
        regAppLifeCallback(APP);
        initAria();
    }

    private void initDb(Context context) throws SQLException {
        File databasePath = context.getDatabasePath("AriaLyyDb");
        if (databasePath != null && databasePath.exists()) {
            File file = new File(String.format("%s/%s", databasePath.getParent(), "AriaLyyDb-journal"));
            databasePath.renameTo(new File(String.format("%s/%s", databasePath.getParent(), "AndroidAria.db")));
            if (file.exists()) {
                file.delete();
            }
        }
        this.mDbWrapper = DelegateWrapper.init(context.getApplicationContext());
        amendTaskState();
    }

    private IReceiver putReceiver(ReceiverType receiverType, Object obj) {
        String key = getKey(receiverType, obj);
        AbsReceiver absReceiver = this.mReceivers.get(key);
        if (absReceiver != null) {
            return absReceiver;
        }
        AbsReceiver downloadReceiver = receiverType.equals(ReceiverType.DOWNLOAD) ? new DownloadReceiver(obj) : new UploadReceiver(obj);
        this.mReceivers.put(key, downloadReceiver);
        return downloadReceiver;
    }

    private void regAppLifeCallback(Context context) {
        Context applicationContext = context.getApplicationContext();
        if (applicationContext instanceof Application) {
            ((Application) applicationContext).registerActivityLifecycleCallbacks(new LifeCallback());
        }
    }

    public void delRecord(int i2, String str, boolean z2) {
        if (i2 == 1) {
            RecordUtil.delTaskRecord(str, 1, z2, true);
        } else if (i2 == 2) {
            RecordUtil.delGroupTaskRecordByHash(str, z2);
        } else {
            if (i2 != 3) {
                return;
            }
            DeleteURecord.getInstance().deleteRecord(str, z2, true);
        }
    }

    public DownloadReceiver download(Object obj) {
        Map<String, AbsReceiver> map = this.mReceivers;
        ReceiverType receiverType = ReceiverType.DOWNLOAD;
        AbsReceiver absReceiverPutReceiver = map.get(getKey(receiverType, obj));
        if (absReceiverPutReceiver == null) {
            absReceiverPutReceiver = putReceiver(receiverType, obj);
        }
        if (absReceiverPutReceiver instanceof DownloadReceiver) {
            return (DownloadReceiver) absReceiverPutReceiver;
        }
        return null;
    }

    public Context getAPP() {
        return APP;
    }

    public AppConfig getAppConfig() {
        return this.mConfig.getAConfig();
    }

    public DGroupConfig getDGroupConfig() {
        return this.mConfig.getDGConfig();
    }

    public DownloadConfig getDownloadConfig() {
        return this.mConfig.getDConfig();
    }

    public Map<String, AbsReceiver> getReceiver() {
        return this.mReceivers;
    }

    public UploadConfig getUploadConfig() {
        return this.mConfig.getUConfig();
    }

    public void removeReceiver(Object obj) {
        Method method;
        if (obj == null) {
            ALog.e(TAG, "target obj is null");
            return;
        }
        Iterator<Map.Entry<String, AbsReceiver>> it = this.mReceivers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, AbsReceiver> next = it.next();
            String key = next.getKey();
            AbsReceiver value = next.getValue();
            if (value.isFragment() && (method = CommonUtil.getMethod(value.obj.getClass(), "getActivity", new Class[0])) != null) {
                try {
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                } catch (InvocationTargetException e3) {
                    e3.printStackTrace();
                }
                if (((Activity) method.invoke(value.obj, new Object[0])) == obj) {
                    value.destroy();
                    it.remove();
                }
            }
            String name = obj.getClass().getName();
            if (value.isLocalOrAnonymousClass && key.startsWith(name)) {
                value.destroy();
                it.remove();
            } else if (key.equals(getKey(ReceiverType.DOWNLOAD, obj)) || key.equals(getKey(ReceiverType.UPLOAD, obj))) {
                value.destroy();
                it.remove();
            }
        }
        Log.d(TAG, "debug");
    }

    @Deprecated
    public AriaManager setDownloadQueueMod(QueueMod queueMod) throws Throwable {
        this.mConfig.getDConfig().setQueueMod(queueMod.tag);
        return this;
    }

    @Deprecated
    public AriaManager setUploadQueueMod(QueueMod queueMod) throws Throwable {
        this.mConfig.getUConfig().setQueueMod(queueMod.tag);
        return this;
    }

    public UploadReceiver upload(Object obj) {
        Map<String, AbsReceiver> map = this.mReceivers;
        ReceiverType receiverType = ReceiverType.UPLOAD;
        AbsReceiver absReceiverPutReceiver = map.get(getKey(receiverType, obj));
        if (absReceiverPutReceiver == null) {
            absReceiverPutReceiver = putReceiver(receiverType, obj);
        }
        if (absReceiverPutReceiver instanceof UploadReceiver) {
            return (UploadReceiver) absReceiverPutReceiver;
        }
        return null;
    }
}

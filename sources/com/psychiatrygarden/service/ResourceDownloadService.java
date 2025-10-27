package com.psychiatrygarden.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.ArrayMap;
import android.util.Log;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.download.target.HttpBuilderTarget;
import com.arialyy.aria.core.task.DownloadTask;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.ResourceBean;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SdkConstant;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/* loaded from: classes6.dex */
public class ResourceDownloadService extends Service {
    private static final String TAG = "ResourceDownloadService";
    private final ArrayMap<String, String> extFieldMap = new ArrayMap<>();
    private DownloadCallBack mDownloadCallBack;

    public class DownloadBinder extends Binder {
        public DownloadBinder() {
        }

        public void delete(long... taskId) {
            List<DownloadEntity> taskList = Aria.download(ResourceDownloadService.this).getTaskList();
            if (taskList == null || taskList.size() <= 0) {
                return;
            }
            for (long j2 : taskId) {
                Iterator<DownloadEntity> it = taskList.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (it.next().getId() == j2) {
                            Aria.download(ResourceDownloadService.this).load(j2).ignoreCheckPermissions().cancel(true);
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        public void deleteAll() {
            List<DownloadEntity> taskList = Aria.download(ResourceDownloadService.this).getTaskList();
            if (taskList == null || taskList.size() <= 0) {
                return;
            }
            Iterator<DownloadEntity> it = taskList.iterator();
            while (it.hasNext()) {
                Aria.download(ResourceDownloadService.this).load(it.next().getId()).ignoreCheckPermissions().cancel(true);
            }
        }

        public ResourceDownloadService getService() {
            return ResourceDownloadService.this;
        }

        public void pauseTask(long id) {
            Aria.download(ResourceDownloadService.this).load(id).ignoreCheckPermissions().stop();
        }

        public void resumeAllTask() {
            Aria.download(ResourceDownloadService.this).resumeAllTask();
        }

        public void resumeTask(long id) {
            Aria.download(ResourceDownloadService.this).load(id).ignoreCheckPermissions().resume();
        }

        public void retryTask(long id) {
            Aria.download(ResourceDownloadService.this).load(id).ignoreCheckPermissions().reTry();
        }

        public void stopAllTask() {
            Aria.download(this).stopAllTask();
        }
    }

    public interface DownloadCallBack {
        void onServiceStartSuccess();

        void onStatusChange(DownloadTask task);
    }

    private String getDownloadPath() {
        StringBuilder sb = new StringBuilder();
        String str = File.separator;
        sb.append(str);
        sb.append(SdkConstant.UMENG_ALIS);
        sb.append(str);
        sb.append("ResourceDownload/");
        String string = sb.toString();
        if (Build.VERSION.SDK_INT >= 29) {
            return ProjectApp.instance().getExternalFilesDir(null).getAbsolutePath() + string;
        }
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + string;
    }

    private String getFileName(String fileUr) {
        try {
            return new URL(fileUr).getFile();
        } catch (MalformedURLException e2) {
            e2.printStackTrace();
            return System.currentTimeMillis() + StrPool.DOT + getSuffixName(fileUr);
        }
    }

    private String getSuffixName(String f2) {
        String str = f2.substring(f2.lastIndexOf(StrPool.DOT) + 1) + new Random();
        LogUtils.e(TAG, "name==>" + str);
        return str;
    }

    private void loadDefaultTask() {
        List<DownloadEntity> taskList = Aria.download(this).getTaskList();
        if (taskList == null || taskList.size() <= 0) {
            return;
        }
        for (DownloadEntity downloadEntity : taskList) {
            this.extFieldMap.put(downloadEntity.getKey(), Aria.download(this).load(downloadEntity.getId()).ignoreCheckPermissions().getExtendField());
        }
    }

    public List<DownloadEntity> getAllCompleteTask() {
        return Aria.download(this).getAllCompleteTask();
    }

    public List<DownloadEntity> getAllDownloadTask() {
        return Aria.download(this).getTaskList();
    }

    public List<DownloadEntity> getAllNotCompleteTask() {
        return Aria.download(this).getAllNotCompleteTask();
    }

    public ArrayMap<String, String> getExtFields() {
        return this.extFieldMap;
    }

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        return new DownloadBinder();
    }

    @Override // android.app.Service
    public void onCreate() throws Throwable {
        super.onCreate();
        Aria.download(this).register();
        Aria.get(this).getAppConfig().setNetCheck(true);
        Aria.get(this).getDownloadConfig().setConvertSpeed(true);
        Aria.get(this).getDownloadConfig().setReTryNum(5);
        Aria.get(this).getDownloadConfig().setConvertSpeed(true);
        loadDefaultTask();
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Aria.download(this).unRegister();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            ResourceBean resourceBean = (ResourceBean) intent.getParcelableExtra("data");
            if (resourceBean != null) {
                String url = resourceBean.getUrl();
                if (url != null && url.startsWith("http") && Objects.equals(Environment.getExternalStorageState(), "mounted")) {
                    this.extFieldMap.put(url, new Gson().toJson(resourceBean));
                    String str = getDownloadPath() + resourceBean.getName();
                    File file = new File(getDownloadPath());
                    if (!file.exists()) {
                        LogUtils.d(TAG, "新建文件夹--" + getDownloadPath() + "---" + file.mkdirs());
                    }
                    LogUtils.e(TAG, "新任务---开始下载 path==>" + str);
                    File file2 = new File(str);
                    if (file2.exists()) {
                        Log.d(TAG, "onStartCommand: 存在下载的同名文件，删除状态：" + file2.delete());
                    }
                    ((HttpBuilderTarget) Aria.download(this).load(url).setFilePath(str).setExtendField(new Gson().toJson(resourceBean))).ignoreCheckPermissions().create();
                }
            } else {
                loadDefaultTask();
            }
        }
        DownloadCallBack downloadCallBack = this.mDownloadCallBack;
        if (downloadCallBack != null) {
            downloadCallBack.onServiceStartSuccess();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Download.onTaskCancel
    public void onTaskCancel(DownloadTask task) {
        DownloadCallBack downloadCallBack = this.mDownloadCallBack;
        if (downloadCallBack != null) {
            downloadCallBack.onStatusChange(task);
        }
        LogUtils.e(TAG, "下载取消");
    }

    @Download.onTaskComplete
    public void onTaskComplete(DownloadTask task) {
        DownloadCallBack downloadCallBack = this.mDownloadCallBack;
        if (downloadCallBack != null) {
            downloadCallBack.onStatusChange(task);
        }
        LogUtils.d(TAG, task.getTaskName() + "-下载完成");
        List<DownloadEntity> allNotCompleteTask = Aria.download(this).getAllNotCompleteTask();
        if (allNotCompleteTask == null || allNotCompleteTask.isEmpty()) {
            stopSelf();
        }
    }

    @Download.onTaskFail
    public void onTaskFail(DownloadTask task) {
        DownloadCallBack downloadCallBack = this.mDownloadCallBack;
        if (downloadCallBack != null) {
            downloadCallBack.onStatusChange(task);
        }
        LogUtils.e(TAG, task.getTaskName() + "下载失败");
    }

    @Download.onPre
    public void onTaskPre(DownloadTask task) {
        LogUtils.d(TAG, "onTaskPre");
        this.mDownloadCallBack.onStatusChange(task);
    }

    @Download.onTaskRunning
    public void onTaskRunning(DownloadTask task) {
        int currentProgress = (int) ((task.getCurrentProgress() * 100) / task.getFileSize());
        String convertSpeed = task.getConvertSpeed();
        DownloadCallBack downloadCallBack = this.mDownloadCallBack;
        if (downloadCallBack != null) {
            downloadCallBack.onStatusChange(task);
        }
        LogUtils.d(TAG, "下载速度:" + convertSpeed + "，进度" + currentProgress);
    }

    @Download.onTaskStart
    public void onTaskStart(DownloadTask task) {
        DownloadCallBack downloadCallBack = this.mDownloadCallBack;
        if (downloadCallBack != null) {
            downloadCallBack.onStatusChange(task);
        }
        LogUtils.d(TAG, "开始下载");
    }

    @Download.onTaskStop
    public void onTaskStop(DownloadTask task) {
        DownloadCallBack downloadCallBack = this.mDownloadCallBack;
        if (downloadCallBack != null) {
            downloadCallBack.onStatusChange(task);
        }
        LogUtils.e(TAG, "下载停止");
    }

    @Download.onWait
    public void onTaskWait(DownloadTask task) {
        DownloadCallBack downloadCallBack = this.mDownloadCallBack;
        if (downloadCallBack != null) {
            downloadCallBack.onStatusChange(task);
        }
        LogUtils.d(TAG, "队列中...");
    }

    public void setCallBack(DownloadCallBack callBack) {
        this.mDownloadCallBack = callBack;
    }
}

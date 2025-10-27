package com.plv.foundationsdk.log.elog;

import android.text.TextUtils;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.model.log.PLVELogSendType;
import com.plv.foundationsdk.model.log.PLVStatisticsBase;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.thirdpart.blankj.utilcode.util.FileIOUtils;
import com.plv.thirdpart.blankj.utilcode.util.FileUtils;
import com.plv.thirdpart.blankj.utilcode.util.Utils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class PLVElogFileService {
    public static final String DIRECTORY_ELOG = "elog";
    private static final int INTERVAL = 30000;
    private static final String TAG = "PLVElogFileService";
    private static PLVElogFileService instance;
    private String fileName;
    private Disposable fileTimer;
    private ConcurrentHashMap<String, File> projectFiles = new ConcurrentHashMap<>();

    private PLVElogFileService() {
        if (this.fileTimer != null) {
            return;
        }
        this.fileTimer = PLVRxTimer.timer(30000, 30000, new Consumer<Long>() { // from class: com.plv.foundationsdk.log.elog.PLVElogFileService.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l2) throws Exception {
                for (Map.Entry entry : PLVElogFileService.this.projectFiles.entrySet()) {
                    ArrayList arrayList = new ArrayList();
                    PLVELogsService.getInstance().readLogCaches(arrayList, (String) entry.getKey());
                    if (arrayList.isEmpty()) {
                        PLVCommonLog.d(PLVElogFileService.TAG, "has not data ");
                        return;
                    }
                    File file = (File) entry.getValue();
                    PLVCommonLog.d(PLVElogFileService.TAG, "create file " + file + FileUtils.createOrExistsFile(file));
                    PLVCommonLog.d(PLVElogFileService.TAG, "writefile " + FileIOUtils.writeFileFromString(file, PLVGsonUtil.toJson(arrayList)) + "   on trhead :" + Thread.currentThread());
                }
            }
        }, true);
    }

    private String createFileMkdir(String str, String str2) {
        this.fileName = "log_" + str2 + ".log";
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.getApp().getCacheDir());
        String str3 = File.separator;
        sb.append(str3);
        sb.append(DIRECTORY_ELOG);
        sb.append(str3);
        sb.append(str);
        sb.append(str3);
        sb.append(this.fileName);
        return sb.toString();
    }

    public static PLVElogFileService getInstance() {
        if (instance == null) {
            instance = new PLVElogFileService();
        }
        return instance;
    }

    public void deleteCache() {
        ConcurrentHashMap<String, File> concurrentHashMap = this.projectFiles;
        if (concurrentHashMap == null) {
            return;
        }
        Iterator<Map.Entry<String, File>> it = concurrentHashMap.entrySet().iterator();
        while (it.hasNext()) {
            File value = it.next().getValue();
            PLVCommonLog.d(TAG, "delete elog cache " + (value != null ? value.delete() : false));
        }
    }

    public void destroy() {
        this.projectFiles.clear();
        Disposable disposable = this.fileTimer;
        if (disposable != null) {
            disposable.dispose();
            this.fileTimer = null;
        }
    }

    public void registerProject(PLVStatisticsBase pLVStatisticsBase) {
        String project = pLVStatisticsBase.getProject();
        String userId2 = pLVStatisticsBase.getUserId2();
        PLVELogSendType eLogSendType = pLVStatisticsBase.getELogSendType();
        String str = project + userId2;
        File file = new File(createFileMkdir(project, userId2));
        if (this.projectFiles.containsKey(str)) {
            PLVCommonLog.d(TAG, "has send file cache:" + userId2);
            return;
        }
        if (!file.exists()) {
            PLVCommonLog.d(TAG, "file not exists:" + file);
            this.projectFiles.put(str, file);
            return;
        }
        String file2String = FileIOUtils.readFile2String(file);
        if (TextUtils.isEmpty(file2String)) {
            PLVCommonLog.d(TAG, " file cache is empty");
            this.projectFiles.put(str, file);
        } else if (PLVELogsService.getInstance().sendELogSync(file2String, eLogSendType)) {
            PLVCommonLog.d(TAG, " file cache send success:" + file2String);
            this.projectFiles.put(str, file);
            if (file.delete()) {
                return;
            }
            PLVCommonLog.d(TAG, "registerProject, delete file fail");
        }
    }
}

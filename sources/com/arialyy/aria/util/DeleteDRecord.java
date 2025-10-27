package com.arialyy.aria.util;

import android.text.TextUtils;
import com.arialyy.aria.core.TaskRecord;
import com.arialyy.aria.core.ThreadRecord;
import com.arialyy.aria.core.common.AbsEntity;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.loader.IRecordHandler;
import com.arialyy.aria.orm.DbEntity;
import java.io.File;

/* loaded from: classes2.dex */
public class DeleteDRecord implements IDeleteRecord {
    private static volatile DeleteDRecord INSTANCE;
    private String TAG = CommonUtil.getClassName(this);

    private DeleteDRecord() {
    }

    private void deleteEntity(boolean z2, String str) {
        if (z2) {
            DbEntity.deleteData(DownloadEntity.class, "downloadPath=?", str);
        }
    }

    public static DeleteDRecord getInstance() {
        if (INSTANCE == null) {
            synchronized (DeleteDRecord.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DeleteDRecord();
                }
            }
        }
        return INSTANCE;
    }

    private void removeBlockFile(TaskRecord taskRecord) {
        int i2 = taskRecord.threadNum;
        for (int i3 = 0; i3 < i2; i3++) {
            FileUtil.deleteFile(String.format(IRecordHandler.SUB_PATH, taskRecord.filePath, Integer.valueOf(i3)));
        }
    }

    @Override // com.arialyy.aria.util.IDeleteRecord
    public void deleteRecord(String str, boolean z2, boolean z3) {
        if (TextUtils.isEmpty(str)) {
            throw new NullPointerException("删除记录失败，文件路径为空");
        }
        if (!str.startsWith("/")) {
            throw new IllegalArgumentException(String.format("文件路径错误，filePath：%s", str));
        }
        DownloadEntity downloadEntity = (DownloadEntity) DbEntity.findFirst(DownloadEntity.class, "downloadPath=?", str);
        if (downloadEntity != null) {
            deleteRecord(downloadEntity, z2, z3);
            return;
        }
        ALog.e(this.TAG, "删除下载记录失败，没有在数据库中找到对应的实体文件，filePath：" + str);
    }

    @Override // com.arialyy.aria.util.IDeleteRecord
    public void deleteRecord(AbsEntity absEntity, boolean z2, boolean z3) {
        if (absEntity == null) {
            ALog.e(this.TAG, "删除下载记录失败，实体为空");
            return;
        }
        DownloadEntity downloadEntity = (DownloadEntity) absEntity;
        String filePath = downloadEntity.getFilePath();
        File file = new File(filePath);
        if (downloadEntity.getTaskType() != 7 && downloadEntity.getTaskType() != 8) {
            TaskRecord taskRecord = DbDataHelper.getTaskRecord(downloadEntity.getFilePath(), downloadEntity.getTaskType());
            if (taskRecord == null) {
                ALog.e(this.TAG, "删除下载记录失败，记录为空，将删除实体记录，filePath：" + downloadEntity.getFilePath());
                FileUtil.deleteFile(file);
                deleteEntity(z3, filePath);
                return;
            }
            DbEntity.deleteData(ThreadRecord.class, "taskKey=? AND threadType=?", filePath, String.valueOf(downloadEntity.getTaskType()));
            DbEntity.deleteData(TaskRecord.class, "filePath=? AND taskType=?", filePath, String.valueOf(downloadEntity.getTaskType()));
            if (z2 || !downloadEntity.isComplete()) {
                FileUtil.deleteFile(file);
                if (taskRecord.isBlock) {
                    removeBlockFile(taskRecord);
                }
            }
            deleteEntity(z3, filePath);
            return;
        }
        DeleteM3u8Record.getInstance().deleteRecord(downloadEntity, z2, z3);
    }
}

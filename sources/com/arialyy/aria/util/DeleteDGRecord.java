package com.arialyy.aria.util;

import android.text.TextUtils;
import com.arialyy.aria.core.TaskRecord;
import com.arialyy.aria.core.ThreadRecord;
import com.arialyy.aria.core.common.AbsEntity;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.download.DownloadGroupEntity;
import com.arialyy.aria.core.loader.IRecordHandler;
import com.arialyy.aria.core.wrapper.RecordWrapper;
import com.arialyy.aria.orm.DbEntity;
import java.util.List;

/* loaded from: classes2.dex */
public class DeleteDGRecord implements IDeleteRecord {
    private static volatile DeleteDGRecord INSTANCE;
    private String TAG = CommonUtil.getClassName(this);

    private DeleteDGRecord() {
    }

    private void deleteEntity(boolean z2, String str) {
        if (z2) {
            DbEntity.deleteData(DownloadEntity.class, "groupHash=?", str);
            DbEntity.deleteData(DownloadGroupEntity.class, "groupHash=?", str);
        }
    }

    public static DeleteDGRecord getInstance() {
        if (INSTANCE == null) {
            synchronized (DeleteDGRecord.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DeleteDGRecord();
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
            ALog.e(this.TAG, "删除下载任务组记录失败，组合任务路径为空");
        } else {
            deleteRecord(DbDataHelper.getDGEntityByPath(str), z2, z3);
        }
    }

    @Override // com.arialyy.aria.util.IDeleteRecord
    public void deleteRecord(AbsEntity absEntity, boolean z2, boolean z3) {
        TaskRecord taskRecord;
        if (absEntity == null) {
            ALog.e(this.TAG, "删除组合任务记录失败，组合任务实体为空");
            return;
        }
        DownloadGroupEntity downloadGroupEntity = (DownloadGroupEntity) absEntity;
        List<RecordWrapper> listFindRelationData = DbEntity.findRelationData(RecordWrapper.class, "dGroupHash=?", downloadGroupEntity.getGroupHash());
        if (listFindRelationData != null && !listFindRelationData.isEmpty()) {
            for (RecordWrapper recordWrapper : listFindRelationData) {
                if (recordWrapper != null && (taskRecord = recordWrapper.taskRecord) != null) {
                    if (taskRecord.isBlock) {
                        removeBlockFile(taskRecord);
                    }
                    DbEntity.deleteData(ThreadRecord.class, "taskKey=?", recordWrapper.taskRecord.filePath);
                    recordWrapper.taskRecord.deleteData();
                }
            }
        } else {
            ALog.w(this.TAG, "组任务记录已删除");
        }
        List<DownloadEntity> subEntities = downloadGroupEntity.getSubEntities();
        if (subEntities != null) {
            for (DownloadEntity downloadEntity : subEntities) {
                if (z2 || !downloadGroupEntity.isComplete()) {
                    FileUtil.deleteFile(downloadEntity.getFilePath());
                }
            }
        }
        if (!TextUtils.isEmpty(downloadGroupEntity.getDirPath()) && (z2 || !downloadGroupEntity.isComplete())) {
            FileUtil.deleteFile(downloadGroupEntity.getDirPath());
        }
        deleteEntity(z3, downloadGroupEntity.getGroupHash());
    }
}

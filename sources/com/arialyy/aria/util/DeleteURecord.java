package com.arialyy.aria.util;

import android.text.TextUtils;
import com.arialyy.aria.core.TaskRecord;
import com.arialyy.aria.core.ThreadRecord;
import com.arialyy.aria.core.common.AbsEntity;
import com.arialyy.aria.core.upload.UploadEntity;
import com.arialyy.aria.orm.DbEntity;

/* loaded from: classes2.dex */
public class DeleteURecord implements IDeleteRecord {
    private static volatile DeleteURecord INSTANCE;
    private String TAG = CommonUtil.getClassName(this);

    private DeleteURecord() {
    }

    private void deleteEntity(boolean z2, String str) {
        if (z2) {
            DbEntity.deleteData(UploadEntity.class, "filePath=?", str);
        }
    }

    public static DeleteURecord getInstance() {
        if (INSTANCE == null) {
            synchronized (DeleteURecord.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DeleteURecord();
                }
            }
        }
        return INSTANCE;
    }

    @Override // com.arialyy.aria.util.IDeleteRecord
    public void deleteRecord(String str, boolean z2, boolean z3) {
        if (TextUtils.isEmpty(str)) {
            throw new NullPointerException("删除记录失败，文件路径为空");
        }
        if (!str.startsWith("/")) {
            throw new IllegalArgumentException(String.format("文件路径错误，filePath：%s", str));
        }
        UploadEntity uploadEntity = (UploadEntity) DbEntity.findFirst(UploadEntity.class, "filePath=?", str);
        if (uploadEntity != null) {
            deleteRecord(uploadEntity, z2, z3);
            return;
        }
        ALog.e(this.TAG, "删除上传记录失败，没有在数据库中找到对应的实体文件，filePath：" + str);
    }

    @Override // com.arialyy.aria.util.IDeleteRecord
    public void deleteRecord(AbsEntity absEntity, boolean z2, boolean z3) {
        if (absEntity == null) {
            ALog.e(this.TAG, "删除上传记录失败，实体为空");
            return;
        }
        UploadEntity uploadEntity = (UploadEntity) absEntity;
        DbEntity.deleteData(ThreadRecord.class, "taskKey=? AND threadType=?", uploadEntity.getFilePath(), String.valueOf(uploadEntity.getTaskType()));
        DbEntity.deleteData(TaskRecord.class, "filePath=? AND taskType=?", uploadEntity.getFilePath(), String.valueOf(uploadEntity.getTaskType()));
        if (z2) {
            FileUtil.deleteFile(uploadEntity.getFilePath());
        }
        deleteEntity(z3, uploadEntity.getFilePath());
    }
}

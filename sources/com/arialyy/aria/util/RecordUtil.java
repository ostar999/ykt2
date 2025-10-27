package com.arialyy.aria.util;

import android.text.TextUtils;
import com.arialyy.aria.core.TaskRecord;
import com.arialyy.aria.core.ThreadRecord;
import com.arialyy.aria.core.common.AbsEntity;
import com.arialyy.aria.core.common.AbsNormalEntity;
import com.arialyy.aria.core.loader.IRecordHandler;
import com.arialyy.aria.orm.DbEntity;
import java.io.File;
import java.util.List;

/* loaded from: classes2.dex */
public class RecordUtil {
    private static final String TAG = "RecordUtil";

    public static boolean blockTaskExists(String str) {
        return new File(String.format(IRecordHandler.SUB_PATH, str, 0)).exists();
    }

    public static void delGroupTaskRecordByHash(String str, boolean z2) {
        if (TextUtils.isEmpty(str)) {
            ALog.e(TAG, "删除下载任务组记录失败，groupHash为null");
        } else {
            DeleteDGRecord.getInstance().deleteRecord((AbsEntity) DbDataHelper.getDGEntityByHash(str), z2, true);
        }
    }

    public static void delNormalTaskRecord(AbsNormalEntity absNormalEntity, boolean z2) {
        int taskType = absNormalEntity.getTaskType();
        if (taskType != 1 && taskType != 3 && taskType != 12) {
            if (taskType != 13) {
                switch (taskType) {
                    case 7:
                    case 8:
                        DeleteM3u8Record.getInstance().deleteRecord((AbsEntity) absNormalEntity, z2, true);
                        break;
                }
            }
            DeleteURecord.getInstance().deleteRecord((AbsEntity) absNormalEntity, z2, true);
            return;
        }
        DeleteDRecord.getInstance().deleteRecord((AbsEntity) absNormalEntity, z2, true);
    }

    public static void delTaskRecord(String str, int i2, boolean z2, boolean z3) {
        if (i2 != 1 && i2 != 3 && i2 != 12) {
            if (i2 != 13) {
                switch (i2) {
                    case 7:
                    case 8:
                        DeleteM3u8Record.getInstance().deleteRecord(str, z2, z3);
                        break;
                }
            }
            DeleteURecord.getInstance().deleteRecord(str, z2, z3);
            return;
        }
        DeleteDRecord.getInstance().deleteRecord(str, z2, z3);
    }

    public static long getBlockLen(long j2, int i2, int i3) {
        long j3 = j2 / i3;
        return i2 == i3 + (-1) ? j2 - (i2 * j3) : j3;
    }

    public static void modifyTaskRecord(String str, String str2, int i2) {
        if (str.equals(str2)) {
            ALog.w(TAG, "修改任务记录失败，新文件路径和旧文件路径一致");
            return;
        }
        TaskRecord taskRecord = DbDataHelper.getTaskRecord(str, i2);
        if (taskRecord == null) {
            if (new File(str).exists()) {
                ALog.w(TAG, "修改任务记录失败，文件【" + str + "】对应的任务记录不存在");
                return;
            }
            return;
        }
        if (!taskRecord.isBlock) {
            File file = new File(str);
            if (file.exists()) {
                file.renameTo(new File(str2));
            }
        }
        taskRecord.filePath = str2;
        taskRecord.update();
        List<ThreadRecord> list = taskRecord.threadRecords;
        if (list == null || list.isEmpty()) {
            return;
        }
        for (ThreadRecord threadRecord : taskRecord.threadRecords) {
            threadRecord.taskKey = str2;
            File file2 = new File(String.format(IRecordHandler.SUB_PATH, str, Integer.valueOf(threadRecord.threadId)));
            if (file2.exists()) {
                file2.renameTo(new File(String.format(IRecordHandler.SUB_PATH, str2, Integer.valueOf(threadRecord.threadId))));
            }
        }
        DbEntity.updateManyData(taskRecord.threadRecords);
    }
}

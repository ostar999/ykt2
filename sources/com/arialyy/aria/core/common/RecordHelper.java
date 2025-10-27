package com.arialyy.aria.core.common;

import com.arialyy.aria.core.TaskRecord;
import com.arialyy.aria.core.ThreadRecord;
import com.arialyy.aria.core.loader.IRecordHandler;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.BufferedRandomAccessFile;
import com.arialyy.aria.util.CommonUtil;
import com.arialyy.aria.util.FileUtil;
import java.io.File;
import java.io.IOException;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes2.dex */
public class RecordHelper {
    private String TAG = CommonUtil.getClassName((Class) getClass());
    protected TaskRecord mTaskRecord;
    private AbsTaskWrapper mWrapper;

    public RecordHelper(AbsTaskWrapper absTaskWrapper, TaskRecord taskRecord) {
        this.mWrapper = absTaskWrapper;
        this.mTaskRecord = taskRecord;
    }

    private void createPlaceHolderFile(File file) throws IOException {
        try {
            new BufferedRandomAccessFile(file, InternalZipConstants.WRITE_MODE).setLength(this.mWrapper.getEntity().getFileSize());
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void handleBlockRecord() {
        long fileSize = this.mWrapper.getEntity().getFileSize() / this.mTaskRecord.threadRecords.size();
        for (ThreadRecord threadRecord : this.mTaskRecord.threadRecords) {
            long j2 = threadRecord.blockLen;
            File file = new File(String.format(IRecordHandler.SUB_PATH, this.mTaskRecord.filePath, Integer.valueOf(threadRecord.threadId)));
            if (!file.exists()) {
                ALog.i(this.TAG, String.format("分块文件【%s】不存在，该分块将重新开始", file.getPath()));
                threadRecord.isComplete = false;
                threadRecord.startLocation = threadRecord.threadId * fileSize;
            } else if (threadRecord.isComplete) {
                ALog.i(this.TAG, String.format("分块【%s】已完成", file.getPath()));
            } else {
                ALog.i(this.TAG, String.format("startLocation = %s; endLocation = %s; block = %s; tempLen = %s; threadId = %s", Long.valueOf(threadRecord.startLocation), Long.valueOf(threadRecord.endLocation), Long.valueOf(j2), Long.valueOf(file.length()), Integer.valueOf(threadRecord.threadId)));
                long length = file.length();
                if (length > j2) {
                    ALog.i(this.TAG, String.format("分块【%s】错误，分块长度【%s】 > 线程区间长度【%s】，将重新开始该分块", Integer.valueOf(threadRecord.threadId), Long.valueOf(length), Long.valueOf(j2)));
                    file.delete();
                    threadRecord.startLocation = threadRecord.threadId * j2;
                } else {
                    long j3 = (threadRecord.threadId * fileSize) + length;
                    if (length == j2 && length != 0) {
                        ALog.i(this.TAG, String.format("分块【%s】已完成，更新记录", file.getPath()));
                        threadRecord.startLocation = length;
                        threadRecord.isComplete = true;
                    } else if (threadRecord.startLocation != j3) {
                        ALog.i(this.TAG, String.format("修正分块【%s】的进度记录为：%s", file.getPath(), Long.valueOf(j3)));
                        threadRecord.startLocation = j3;
                    } else {
                        ALog.i(this.TAG, String.format("修正分块【%s】的进度记录为：%s", file.getPath(), Long.valueOf(j3)));
                        threadRecord.startLocation = j3;
                        threadRecord.isComplete = false;
                    }
                }
            }
        }
        this.mWrapper.setNewTask(false);
    }

    public void handleMultiRecord() throws IOException {
        boolean z2;
        long fileSize = this.mWrapper.getEntity().getFileSize() / this.mTaskRecord.threadRecords.size();
        File file = new File(this.mTaskRecord.filePath);
        if (file.exists()) {
            if (file.length() != this.mWrapper.getEntity().getFileSize()) {
                FileUtil.deleteFile(file);
                createPlaceHolderFile(file);
            }
            z2 = true;
        } else {
            createPlaceHolderFile(file);
            z2 = false;
        }
        if (!z2) {
            ALog.w(this.TAG, String.format("文件【%s】被删除，重新分配线程区间", this.mTaskRecord.filePath));
            int i2 = 0;
            while (true) {
                TaskRecord taskRecord = this.mTaskRecord;
                if (i2 >= taskRecord.threadNum) {
                    break;
                }
                long j2 = i2 * fileSize;
                int i3 = i2 + 1;
                long fileSize2 = i3 * fileSize;
                ThreadRecord threadRecord = taskRecord.threadRecords.get(i2);
                threadRecord.startLocation = j2;
                threadRecord.isComplete = false;
                if (threadRecord.threadId == this.mTaskRecord.threadNum - 1) {
                    fileSize2 = this.mWrapper.getEntity().getFileSize();
                }
                threadRecord.endLocation = fileSize2;
                i2 = i3;
            }
        }
        this.mWrapper.setNewTask(false);
    }

    public void handleNoSupportBPRecord() {
        ThreadRecord threadRecord = this.mTaskRecord.threadRecords.get(0);
        threadRecord.startLocation = 0L;
        long fileSize = this.mWrapper.getEntity().getFileSize();
        threadRecord.endLocation = fileSize;
        threadRecord.taskKey = this.mTaskRecord.filePath;
        threadRecord.blockLen = fileSize;
        threadRecord.isComplete = false;
    }

    public void handleSingleThreadRecord() {
        TaskRecord taskRecord = this.mTaskRecord;
        File file = new File(taskRecord.isBlock ? String.format(IRecordHandler.SUB_PATH, taskRecord.filePath, 0) : taskRecord.filePath);
        ThreadRecord threadRecord = this.mTaskRecord.threadRecords.get(0);
        if (!file.exists()) {
            File file2 = new File(this.mTaskRecord.filePath);
            if (threadRecord.isComplete && file2.exists() && file2.length() != 0 && file2.length() == this.mWrapper.getEntity().getFileSize()) {
                threadRecord.isComplete = true;
            } else {
                ALog.w(this.TAG, String.format("文件【%s】不存在，任务将重新开始", file.getPath()));
                threadRecord.startLocation = 0L;
                threadRecord.isComplete = false;
                threadRecord.endLocation = this.mWrapper.getEntity().getFileSize();
            }
        } else if (file.length() > this.mWrapper.getEntity().getFileSize()) {
            ALog.i(this.TAG, String.format("文件【%s】错误，任务重新开始", file.getPath()));
            FileUtil.deleteFile(file);
            threadRecord.startLocation = 0L;
            threadRecord.isComplete = false;
            threadRecord.endLocation = this.mWrapper.getEntity().getFileSize();
        } else if (file.length() != 0 && file.length() == this.mWrapper.getEntity().getFileSize()) {
            ALog.d(this.TAG, "文件长度一致，线程完成");
            threadRecord.isComplete = true;
        } else if (file.length() != threadRecord.startLocation) {
            ALog.i(this.TAG, String.format("修正【%s】的进度记录为：%s", file.getPath(), Long.valueOf(file.length())));
            threadRecord.startLocation = file.length();
            threadRecord.isComplete = false;
        }
        this.mWrapper.setNewTask(false);
    }
}

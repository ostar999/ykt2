package com.arialyy.aria.http;

import com.arialyy.aria.core.TaskRecord;
import com.arialyy.aria.core.ThreadRecord;
import com.arialyy.aria.core.common.RecordHandler;
import com.arialyy.aria.core.common.RecordHelper;
import com.arialyy.aria.core.config.Configuration;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.util.RecordUtil;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class HttpRecordHandler extends RecordHandler {
    public HttpRecordHandler(AbsTaskWrapper absTaskWrapper) {
        super(absTaskWrapper);
    }

    @Override // com.arialyy.aria.core.loader.IRecordHandler
    public TaskRecord createTaskRecord(int i2) {
        TaskRecord taskRecord = new TaskRecord();
        taskRecord.fileName = getEntity().getFileName();
        taskRecord.filePath = getEntity().getFilePath();
        taskRecord.threadRecords = new ArrayList();
        taskRecord.threadNum = i2;
        int requestType = getWrapper().getRequestType();
        if (requestType == 1 || requestType == 2) {
            taskRecord.isBlock = Configuration.getInstance().downloadCfg.isUseBlock();
        } else {
            taskRecord.isBlock = false;
        }
        taskRecord.taskType = requestType;
        boolean zIsGroupChild = getEntity().isGroupChild();
        taskRecord.isGroupRecord = zIsGroupChild;
        if (zIsGroupChild && (getEntity() instanceof DownloadEntity)) {
            taskRecord.dGroupHash = ((DownloadEntity) getEntity()).getGroupHash();
        }
        return taskRecord;
    }

    @Override // com.arialyy.aria.core.loader.IRecordHandler
    public ThreadRecord createThreadRecord(TaskRecord taskRecord, int i2, long j2, long j3) {
        ThreadRecord threadRecord = new ThreadRecord();
        threadRecord.taskKey = taskRecord.filePath;
        threadRecord.threadId = i2;
        threadRecord.startLocation = j2;
        threadRecord.isComplete = false;
        threadRecord.threadType = taskRecord.taskType;
        if (i2 == taskRecord.threadNum - 1) {
            j3 = getFileSize();
        }
        threadRecord.endLocation = j3;
        threadRecord.blockLen = RecordUtil.getBlockLen(getFileSize(), i2, taskRecord.threadNum);
        return threadRecord;
    }

    @Override // com.arialyy.aria.core.loader.IRecordHandler
    public void handlerTaskRecord(TaskRecord taskRecord) throws IOException {
        RecordHelper recordHelper = new RecordHelper(getWrapper(), taskRecord);
        if (!getWrapper().isSupportBP() || taskRecord.threadNum <= 1) {
            if (getWrapper().isSupportBP()) {
                recordHelper.handleSingleThreadRecord();
                return;
            } else {
                recordHelper.handleNoSupportBPRecord();
                return;
            }
        }
        if (taskRecord.isBlock) {
            recordHelper.handleBlockRecord();
        } else {
            recordHelper.handleMultiRecord();
        }
    }

    @Override // com.arialyy.aria.core.loader.IRecordHandler
    public int initTaskThreadNum() {
        int requestType = getWrapper().getRequestType();
        if (requestType == 5) {
            return 1;
        }
        if ((requestType == 1 && !getWrapper().isSupportBP()) || ((HttpTaskOption) getWrapper().getTaskOption()).isChunked()) {
            return 1;
        }
        int threadNum = Configuration.getInstance().downloadCfg.getThreadNum();
        if (getFileSize() <= 1048576 || getEntity().isGroupChild() || threadNum == 1) {
            return 1;
        }
        return threadNum;
    }
}

package com.arialyy.aria.core.listener;

import com.arialyy.aria.core.task.DownloadTask;
import com.arialyy.aria.util.CommonUtil;
import com.arialyy.aria.util.DeleteDRecord;

/* loaded from: classes2.dex */
public class BaseDListener extends BaseListener implements IDLoadListener {
    @Override // com.arialyy.aria.core.listener.BaseListener
    public void handleCancel() {
        if (((DownloadTask) getTask(DownloadTask.class)).getSchedulerType() != 4) {
            DeleteDRecord.getInstance().deleteRecord(this.mEntity, this.mTaskWrapper.isRemoveFile(), true);
            return;
        }
        this.mEntity.setComplete(false);
        this.mEntity.setState(3);
        DeleteDRecord.getInstance().deleteRecord(this.mEntity, this.mTaskWrapper.isRemoveFile(), false);
    }

    @Override // com.arialyy.aria.core.listener.IDLoadListener
    public void onPostPre(long j2) {
        this.mEntity.setFileSize(j2);
        this.mEntity.setConvertFileSize(CommonUtil.formatFileSize(j2));
        saveData(6, -1L);
        sendInState2Target(1);
    }

    @Override // com.arialyy.aria.core.listener.IDLoadListener
    public void supportBreakpoint(boolean z2) {
        if (z2) {
            return;
        }
        sendInState2Target(9);
    }
}

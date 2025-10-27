package com.arialyy.aria.core.listener;

import com.arialyy.aria.core.task.UploadTask;
import com.arialyy.aria.util.DeleteURecord;

/* loaded from: classes2.dex */
public class BaseUListener extends BaseListener implements IUploadListener {
    @Override // com.arialyy.aria.core.listener.BaseListener
    public void handleCancel() {
        if (((UploadTask) getTask(UploadTask.class)).getSchedulerType() != 4) {
            DeleteURecord.getInstance().deleteRecord(this.mEntity, this.mTaskWrapper.isRemoveFile(), true);
            return;
        }
        this.mEntity.setComplete(false);
        this.mEntity.setState(3);
        DeleteURecord.getInstance().deleteRecord(this.mEntity, this.mTaskWrapper.isRemoveFile(), false);
    }
}

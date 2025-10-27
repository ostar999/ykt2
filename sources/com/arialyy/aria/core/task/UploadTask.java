package com.arialyy.aria.core.task;

import android.os.Handler;
import android.os.Looper;
import com.arialyy.aria.core.listener.ISchedulers;
import com.arialyy.aria.core.upload.UTaskWrapper;
import com.arialyy.aria.core.upload.UploadEntity;
import com.arialyy.aria.util.ComponentUtil;

/* loaded from: classes2.dex */
public class UploadTask extends AbsTask<UTaskWrapper> {

    public static class Builder {
        private Handler mOutHandler;
        private UTaskWrapper mTaskEntity;

        public UploadTask build() {
            return new UploadTask(this.mTaskEntity, this.mOutHandler);
        }

        public void setOutHandler(ISchedulers iSchedulers) {
            this.mOutHandler = new Handler(Looper.getMainLooper(), iSchedulers);
        }

        public void setUploadTaskEntity(UTaskWrapper uTaskWrapper) {
            this.mTaskEntity = uTaskWrapper;
        }
    }

    public UploadEntity getEntity() {
        return ((UTaskWrapper) this.mTaskWrapper).getEntity();
    }

    @Override // com.arialyy.aria.core.task.ITask
    public String getKey() {
        return ((UTaskWrapper) this.mTaskWrapper).getEntity().getKey();
    }

    @Override // com.arialyy.aria.core.task.ITask
    public String getTaskName() {
        return ((UTaskWrapper) this.mTaskWrapper).getEntity().getFileName();
    }

    @Override // com.arialyy.aria.core.task.ITask
    public int getTaskType() {
        return 2;
    }

    private UploadTask(UTaskWrapper uTaskWrapper, Handler handler) {
        this.mTaskWrapper = uTaskWrapper;
        this.mOutHandler = handler;
        this.mListener = ComponentUtil.getInstance().buildListener(uTaskWrapper.getRequestType(), this, this.mOutHandler);
    }
}

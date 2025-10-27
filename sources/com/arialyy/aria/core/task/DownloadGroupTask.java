package com.arialyy.aria.core.task;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.arialyy.aria.core.AriaConfig;
import com.arialyy.aria.core.download.DGTaskWrapper;
import com.arialyy.aria.core.download.DownloadGroupEntity;
import com.arialyy.aria.core.listener.ISchedulers;
import com.arialyy.aria.util.ComponentUtil;

/* loaded from: classes2.dex */
public class DownloadGroupTask extends AbsGroupTask<DGTaskWrapper> {

    public static class Builder {
        Handler outHandler;
        DGTaskWrapper taskEntity;

        public Builder(DGTaskWrapper dGTaskWrapper) {
            this.taskEntity = dGTaskWrapper;
        }

        public DownloadGroupTask build() {
            return new DownloadGroupTask(this.taskEntity, this.outHandler);
        }

        public Builder setOutHandler(ISchedulers iSchedulers) {
            this.outHandler = new Handler(Looper.getMainLooper(), iSchedulers);
            return this;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DownloadGroupEntity getEntity() {
        return (DownloadGroupEntity) ((DGTaskWrapper) this.mTaskWrapper).getEntity();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.arialyy.aria.core.task.ITask
    public String getTaskName() {
        StringBuilder sb = new StringBuilder();
        sb.append("任务组->");
        sb.append(TextUtils.isEmpty(((DownloadGroupEntity) ((DGTaskWrapper) this.mTaskWrapper).getEntity()).getAlias()) ? ((DownloadGroupEntity) ((DGTaskWrapper) this.mTaskWrapper).getEntity()).getGroupHash() : ((DownloadGroupEntity) ((DGTaskWrapper) this.mTaskWrapper).getEntity()).getAlias());
        return sb.toString();
    }

    @Override // com.arialyy.aria.core.task.ITask
    public int getTaskType() {
        return 3;
    }

    private DownloadGroupTask(DGTaskWrapper dGTaskWrapper, Handler handler) {
        this.mTaskWrapper = dGTaskWrapper;
        this.mOutHandler = handler;
        this.mContext = AriaConfig.getInstance().getAPP();
        this.mListener = ComponentUtil.getInstance().buildListener(dGTaskWrapper.getRequestType(), this, this.mOutHandler);
    }
}

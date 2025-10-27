package com.arialyy.aria.core.download.target;

import android.text.TextUtils;
import com.arialyy.aria.core.common.AbsNormalTarget;
import com.arialyy.aria.core.download.DTaskWrapper;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.event.ErrorEvent;
import com.arialyy.aria.core.inf.AbsTarget;
import com.arialyy.aria.core.inf.IConfigHandler;
import com.arialyy.aria.core.manager.TaskWrapperManager;
import com.arialyy.aria.core.queue.DTaskQueue;
import com.arialyy.aria.orm.DbEntity;
import com.arialyy.aria.util.ALog;

/* loaded from: classes2.dex */
class DNormalConfigHandler<TARGET extends AbsTarget> implements IConfigHandler {
    private final String TAG = "DNormalDelegate";
    private DownloadEntity mEntity;
    private TARGET mTarget;
    private DTaskWrapper mWrapper;

    public DNormalConfigHandler(TARGET target, long j2) {
        this.mTarget = target;
        initTarget(j2);
    }

    private DTaskWrapper getWrapper() {
        return this.mWrapper;
    }

    private void initTarget(long j2) {
        DTaskWrapper dTaskWrapper = (DTaskWrapper) TaskWrapperManager.getInstance().getNormalTaskWrapper(DTaskWrapper.class, j2);
        this.mWrapper = dTaskWrapper;
        if (this.mTarget instanceof AbsNormalTarget) {
            if (j2 < 0) {
                dTaskWrapper.setErrorEvent(new ErrorEvent(j2, "任务id为空"));
            } else if (dTaskWrapper.getEntity().getId() < 0) {
                this.mWrapper.setErrorEvent(new ErrorEvent(j2, "任务信息不存在"));
            }
        }
        this.mEntity = this.mWrapper.getEntity();
        this.mTarget.setTaskWrapper(this.mWrapper);
        if (this.mEntity != null) {
            getWrapper().setTempFilePath(this.mEntity.getFilePath());
        }
    }

    public String getUrl() {
        return this.mEntity.getUrl();
    }

    @Override // com.arialyy.aria.core.inf.IConfigHandler
    public boolean isRunning() {
        return DTaskQueue.getInstance().taskIsRunning(this.mEntity.getKey());
    }

    public void setForceDownload(boolean z2) {
        getWrapper().setIgnoreFilePathOccupy(z2);
    }

    public void setTempFilePath(String str) {
        getWrapper().setTempFilePath(str);
    }

    public void setUrl(String str) {
        this.mEntity.setUrl(str);
        this.mWrapper.setTempUrl(str);
    }

    @Override // com.arialyy.aria.core.inf.IConfigHandler
    public boolean taskExists() {
        return DbEntity.checkDataExist(DownloadEntity.class, "rowid=?", String.valueOf(this.mEntity.getId()));
    }

    public TARGET updateUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            ALog.e("DNormalDelegate", "url更新失败，newUrl为null");
            return this.mTarget;
        }
        if (this.mEntity.getUrl().equals(str)) {
            ALog.e("DNormalDelegate", "url更新失败，新的下载url和旧的url一致");
            return this.mTarget;
        }
        getWrapper().setRefreshInfo(true);
        getWrapper().setTempUrl(str);
        ALog.d("DNormalDelegate", "更新url成功");
        return this.mTarget;
    }

    @Override // com.arialyy.aria.core.inf.IConfigHandler
    public DownloadEntity getEntity() {
        return (DownloadEntity) this.mTarget.getEntity();
    }
}

package com.arialyy.aria.core.common;

import com.arialyy.aria.core.common.AbsNormalTarget;
import com.arialyy.aria.core.common.controller.INormalFeature;
import com.arialyy.aria.core.common.controller.NormalController;
import com.arialyy.aria.core.download.DownloadGroupEntity;
import com.arialyy.aria.core.inf.AbsTarget;
import com.arialyy.aria.core.manager.TaskWrapperManager;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import com.arialyy.aria.util.DeleteDGRecord;
import com.arialyy.aria.util.RecordUtil;

/* loaded from: classes2.dex */
public abstract class AbsNormalTarget<TARGET extends AbsNormalTarget> extends AbsTarget<TARGET> implements INormalFeature {
    private NormalController mNormalController;

    private synchronized NormalController getController() {
        if (this.mNormalController == null) {
            this.mNormalController = new NormalController(getTaskWrapper());
        }
        return this.mNormalController;
    }

    @Override // com.arialyy.aria.core.common.controller.INormalFeature
    public void cancel() {
        cancel(false);
    }

    public String getConvertFileSize() {
        return getEntity() == null ? "0b" : CommonUtil.formatFileSize(getEntity().getFileSize());
    }

    public long getCurrentProgress() {
        if (getEntity() == null) {
            return -1L;
        }
        return getEntity().getCurrentProgress();
    }

    public String getExtendField() {
        return getEntity().getStr();
    }

    public long getFileSize() {
        if (getEntity() == null) {
            return 0L;
        }
        return getEntity().getFileSize();
    }

    public int getPercent() {
        if (getEntity() == null) {
            ALog.e("AbsTarget", "下载管理器中没有该任务");
            return 0;
        }
        if (getEntity().getFileSize() != 0) {
            return (int) ((getEntity().getCurrentProgress() * 100) / getEntity().getFileSize());
        }
        return 0;
    }

    public int getTaskState() {
        return getEntity().getState();
    }

    public TARGET ignoreCheckPermissions() {
        getController().ignoreCheckPermissions();
        return this;
    }

    public boolean isRunning() {
        return false;
    }

    public void onPre() {
    }

    @Override // com.arialyy.aria.core.common.controller.INormalFeature
    public long reStart() {
        onPre();
        return getController().reStart();
    }

    @Override // com.arialyy.aria.core.common.controller.INormalFeature
    public void reTry() {
        onPre();
        getController().reTry();
    }

    public void removeRecord() {
        if (isRunning()) {
            ALog.d("AbsTarget", "任务正在下载，即将删除任务");
            cancel();
            return;
        }
        if (getEntity() instanceof AbsNormalEntity) {
            RecordUtil.delNormalTaskRecord((AbsNormalEntity) getEntity(), getTaskWrapper().isRemoveFile());
        } else if (getEntity() instanceof DownloadGroupEntity) {
            DeleteDGRecord.getInstance().deleteRecord(getEntity(), getTaskWrapper().isRemoveFile(), true);
        }
        TaskWrapperManager.getInstance().removeTaskWrapper(getTaskWrapper());
    }

    @Override // com.arialyy.aria.core.common.controller.INormalFeature
    public void resume() {
        resume(false);
    }

    @Override // com.arialyy.aria.core.common.controller.INormalFeature
    public void save() {
        onPre();
        getController().save();
    }

    @Override // com.arialyy.aria.core.common.controller.INormalFeature
    public void stop() {
        onPre();
        getController().stop();
    }

    public boolean taskExists() {
        return false;
    }

    @Override // com.arialyy.aria.core.common.controller.INormalFeature
    public void cancel(boolean z2) {
        onPre();
        getController().cancel(z2);
    }

    @Override // com.arialyy.aria.core.common.controller.INormalFeature
    public void resume(boolean z2) {
        onPre();
        getController().resume(z2);
    }
}

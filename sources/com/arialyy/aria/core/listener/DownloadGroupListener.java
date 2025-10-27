package com.arialyy.aria.core.listener;

import android.os.Handler;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.download.DownloadGroupEntity;
import com.arialyy.aria.core.group.GroupSendParams;
import com.arialyy.aria.core.task.AbsTask;
import com.arialyy.aria.core.task.DownloadGroupTask;
import com.arialyy.aria.exception.AriaException;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import com.arialyy.aria.util.DeleteDGRecord;
import com.arialyy.aria.util.ErrorHelp;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class DownloadGroupListener extends BaseListener implements IDGroupListener {
    private GroupSendParams<DownloadGroupTask, DownloadEntity> mSeedEntity;

    private void handleSubSpeed(DownloadEntity downloadEntity, long j2) {
        if (j2 == 0) {
            downloadEntity.setSpeed(0L);
            downloadEntity.setConvertSpeed("0kb/s");
            return;
        }
        long currentProgress = j2 - downloadEntity.getCurrentProgress();
        downloadEntity.setSpeed(currentProgress);
        downloadEntity.setConvertSpeed(currentProgress <= 0 ? "" : String.format("%s/s", CommonUtil.formatFileSize(currentProgress)));
        downloadEntity.setPercent((int) (downloadEntity.getFileSize() > 0 ? (downloadEntity.getCurrentProgress() * 100) / downloadEntity.getFileSize() : 0L));
        downloadEntity.setCurrentProgress(j2);
        if (currentProgress == 0) {
            downloadEntity.setTimeLeft(Integer.MAX_VALUE);
        } else {
            downloadEntity.setTimeLeft((int) ((downloadEntity.getFileSize() - downloadEntity.getCurrentProgress()) / currentProgress));
        }
    }

    private void saveCurrentLocation() {
        DownloadGroupEntity downloadGroupEntity = (DownloadGroupEntity) this.mEntity;
        if (downloadGroupEntity.getSubEntities() == null || downloadGroupEntity.getSubEntities().isEmpty()) {
            ALog.w(this.TAG, "保存进度失败，子任务为null");
            return;
        }
        Iterator<DownloadEntity> it = downloadGroupEntity.getSubEntities().iterator();
        long fileSize = 0;
        while (it.hasNext()) {
            fileSize += it.next().getCurrentProgress();
        }
        if (fileSize > this.mEntity.getFileSize()) {
            fileSize = this.mEntity.getFileSize();
        }
        this.mEntity.setCurrentProgress(fileSize);
        this.mEntity.update();
    }

    private void saveSubState(int i2, DownloadEntity downloadEntity) {
        downloadEntity.setState(i2);
        if (i2 == 2) {
            downloadEntity.setStopTime(System.currentTimeMillis());
        } else if (i2 == 1) {
            downloadEntity.setComplete(true);
            downloadEntity.setCompleteTime(System.currentTimeMillis());
            downloadEntity.setCurrentProgress(downloadEntity.getFileSize());
            downloadEntity.setPercent(100);
            downloadEntity.setConvertSpeed("0kb/s");
            downloadEntity.setSpeed(0L);
        }
        downloadEntity.update();
    }

    private void sendInState2Target(int i2, DownloadEntity downloadEntity) {
        if (this.outHandler.get() != null) {
            this.mSeedEntity.entity = downloadEntity;
            this.outHandler.get().obtainMessage(i2, 209, 0, this.mSeedEntity).sendToTarget();
        }
    }

    @Override // com.arialyy.aria.core.listener.BaseListener
    public void handleCancel() {
        if (((DownloadGroupTask) getTask(DownloadGroupTask.class)).getSchedulerType() != 4) {
            DeleteDGRecord.getInstance().deleteRecord(this.mEntity, this.mTaskWrapper.isRemoveFile(), true);
            return;
        }
        this.mEntity.setComplete(false);
        this.mEntity.setState(3);
        DeleteDGRecord.getInstance().deleteRecord(this.mEntity, this.mTaskWrapper.isRemoveFile(), false);
    }

    @Override // com.arialyy.aria.core.listener.IDLoadListener
    public void onPostPre(long j2) {
        this.mEntity.setFileSize(j2);
        this.mEntity.setConvertFileSize(CommonUtil.formatFileSize(j2));
        saveData(6, -1L);
        sendInState2Target(1);
    }

    @Override // com.arialyy.aria.core.listener.IDGroupListener
    public void onSubCancel(DownloadEntity downloadEntity) {
        handleSubSpeed(downloadEntity, 0L);
        saveSubState(7, downloadEntity);
        saveCurrentLocation();
        sendInState2Target(164, downloadEntity);
    }

    @Override // com.arialyy.aria.core.listener.IDGroupListener
    public void onSubComplete(DownloadEntity downloadEntity) {
        handleSubSpeed(downloadEntity, 0L);
        saveSubState(1, downloadEntity);
        saveCurrentLocation();
        sendInState2Target(167, downloadEntity);
    }

    @Override // com.arialyy.aria.core.listener.IDGroupListener
    public void onSubFail(DownloadEntity downloadEntity, AriaException ariaException) throws Throwable {
        handleSubSpeed(downloadEntity, 0L);
        saveSubState(0, downloadEntity);
        saveCurrentLocation();
        ((DownloadGroupTask) this.mSeedEntity.groupTask).putExpand(AbsTask.ERROR_INFO_KEY, ariaException);
        sendInState2Target(165, downloadEntity);
        if (ariaException != null) {
            ariaException.printStackTrace();
            ErrorHelp.saveError("", ALog.getExceptionString(ariaException));
        }
    }

    @Override // com.arialyy.aria.core.listener.IDGroupListener
    public void onSubPre(DownloadEntity downloadEntity) {
        handleSubSpeed(downloadEntity, 0L);
        saveSubState(5, downloadEntity);
        sendInState2Target(161, downloadEntity);
    }

    @Override // com.arialyy.aria.core.listener.IDGroupListener
    public void onSubRunning(DownloadEntity downloadEntity, long j2) {
        handleSubSpeed(downloadEntity, j2);
        if (System.currentTimeMillis() - this.mLastSaveTime >= 5000) {
            saveSubState(4, downloadEntity);
            this.mLastSaveTime = System.currentTimeMillis();
        }
        sendInState2Target(166, downloadEntity);
    }

    @Override // com.arialyy.aria.core.listener.IDGroupListener
    public void onSubStart(DownloadEntity downloadEntity) {
        handleSubSpeed(downloadEntity, 0L);
        saveSubState(4, downloadEntity);
        sendInState2Target(162, downloadEntity);
    }

    @Override // com.arialyy.aria.core.listener.IDGroupListener
    public void onSubStop(DownloadEntity downloadEntity, long j2) {
        downloadEntity.setCurrentProgress(j2);
        handleSubSpeed(downloadEntity, 0L);
        saveSubState(2, downloadEntity);
        saveCurrentLocation();
        sendInState2Target(163, downloadEntity);
    }

    @Override // com.arialyy.aria.core.listener.BaseListener, com.arialyy.aria.core.listener.IEventListener
    public IEventListener setParams(AbsTask absTask, Handler handler) {
        IEventListener params = super.setParams(absTask, handler);
        GroupSendParams<DownloadGroupTask, DownloadEntity> groupSendParams = new GroupSendParams<>();
        this.mSeedEntity = groupSendParams;
        groupSendParams.groupTask = (DownloadGroupTask) absTask;
        return params;
    }

    @Override // com.arialyy.aria.core.listener.IDLoadListener
    public void supportBreakpoint(boolean z2) {
    }

    @Override // com.arialyy.aria.core.listener.IDGroupListener
    public void supportBreakpoint(boolean z2, DownloadEntity downloadEntity) {
    }
}

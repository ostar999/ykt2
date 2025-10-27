package com.arialyy.aria.core.command;

import android.text.TextUtils;
import com.arialyy.aria.core.download.DGTaskWrapper;
import com.arialyy.aria.core.download.DTaskWrapper;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.download.DownloadGroupEntity;
import com.arialyy.aria.core.inf.IOptionConstant;
import com.arialyy.aria.core.manager.TaskWrapperManager;
import com.arialyy.aria.core.queue.DGroupTaskQueue;
import com.arialyy.aria.core.queue.DTaskQueue;
import com.arialyy.aria.core.queue.ITaskQueue;
import com.arialyy.aria.core.queue.UTaskQueue;
import com.arialyy.aria.core.task.AbsTask;
import com.arialyy.aria.core.upload.UTaskWrapper;
import com.arialyy.aria.core.upload.UploadEntity;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.orm.DbEntity;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class ResumeThread implements Runnable {
    private boolean isDownloadCmd;
    private String sqlCondition;
    private String TAG = CommonUtil.getClassName((Class) getClass());
    private List<AbsTaskWrapper> mWaitList = new ArrayList();

    public ResumeThread(boolean z2, String str) {
        this.isDownloadCmd = z2;
        this.sqlCondition = str;
    }

    private void addResumeEntity(AbsTaskWrapper absTaskWrapper) {
        if (absTaskWrapper == null || absTaskWrapper.getEntity() == null || TextUtils.isEmpty(absTaskWrapper.getKey())) {
            return;
        }
        this.mWaitList.add(absTaskWrapper);
    }

    private void findTaskData(int i2) {
        List listFindDatas;
        if (i2 == 1) {
            List listFindDatas2 = DbEntity.findDatas(DownloadEntity.class, String.format("NOT(isGroupChild) AND NOT(isComplete) AND %s ORDER BY stopTime DESC", this.sqlCondition));
            if (listFindDatas2 == null || listFindDatas2.isEmpty()) {
                return;
            }
            Iterator it = listFindDatas2.iterator();
            while (it.hasNext()) {
                addResumeEntity(TaskWrapperManager.getInstance().getNormalTaskWrapper(DTaskWrapper.class, ((DownloadEntity) it.next()).getId()));
            }
            return;
        }
        if (i2 == 2) {
            List listFindDatas3 = DbEntity.findDatas(DownloadGroupEntity.class, String.format("NOT(isComplete) AND %s ORDER BY stopTime DESC", this.sqlCondition));
            if (listFindDatas3 == null || listFindDatas3.isEmpty()) {
                return;
            }
            Iterator it2 = listFindDatas3.iterator();
            while (it2.hasNext()) {
                addResumeEntity(TaskWrapperManager.getInstance().getGroupWrapper(DGTaskWrapper.class, ((DownloadGroupEntity) it2.next()).getId()));
            }
            return;
        }
        if (i2 != 3 || (listFindDatas = DbEntity.findDatas(UploadEntity.class, String.format("NOT(isComplete) AND %s ORDER BY stopTime DESC", this.sqlCondition))) == null || listFindDatas.isEmpty()) {
            return;
        }
        Iterator it3 = listFindDatas.iterator();
        while (it3.hasNext()) {
            addResumeEntity(TaskWrapperManager.getInstance().getNormalTaskWrapper(UTaskWrapper.class, ((UploadEntity) it3.next()).getId()));
        }
    }

    private void handleWrapper(AbsTaskWrapper absTaskWrapper) {
        int requestType = absTaskWrapper.getRequestType();
        if (requestType == 3 || requestType == 6 || requestType == 4) {
            absTaskWrapper.getOptionParams().setParams(IOptionConstant.ftpUrlEntity, CommonUtil.getFtpUrlInfo(absTaskWrapper.getEntity().getKey()));
        }
    }

    private void resumeWaitTask() {
        List<AbsTaskWrapper> list = this.mWaitList;
        if (list == null || list.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (AbsTaskWrapper absTaskWrapper : this.mWaitList) {
            ITaskQueue dTaskQueue = absTaskWrapper instanceof DTaskWrapper ? DTaskQueue.getInstance() : absTaskWrapper instanceof UTaskWrapper ? UTaskQueue.getInstance() : absTaskWrapper instanceof DGTaskWrapper ? DGroupTaskQueue.getInstance() : null;
            if (dTaskQueue == null) {
                ALog.e(this.TAG, "任务类型错误");
            } else if (absTaskWrapper.getEntity() == null || TextUtils.isEmpty(absTaskWrapper.getKey())) {
                ALog.e(this.TAG, "任务实体为空或key为空");
            } else if (dTaskQueue.getTask(absTaskWrapper.getKey()) != null) {
                ALog.w(this.TAG, "任务已存在");
            } else {
                int maxTaskNum = dTaskQueue.getMaxTaskNum();
                AbsTask absTaskCreateTask = dTaskQueue.createTask((ITaskQueue) absTaskWrapper);
                if (absTaskCreateTask != null) {
                    handleWrapper(absTaskWrapper);
                    if (dTaskQueue.getCurrentExePoolNum() < maxTaskNum) {
                        dTaskQueue.startTask((ITaskQueue) absTaskCreateTask);
                    } else {
                        absTaskWrapper.getEntity().setState(3);
                        sendWaitState(absTaskCreateTask);
                        arrayList.add(absTaskWrapper.getEntity());
                    }
                }
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        DbEntity.updateManyData(arrayList);
    }

    private void sendWaitState(AbsTask absTask) {
        if (absTask != null) {
            absTask.getTaskWrapper().setState(3);
            absTask.getOutHandler().obtainMessage(10, absTask).sendToTarget();
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.isDownloadCmd) {
            findTaskData(1);
            findTaskData(2);
        } else {
            findTaskData(3);
        }
        resumeWaitTask();
    }
}

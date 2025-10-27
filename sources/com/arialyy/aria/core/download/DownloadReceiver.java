package com.arialyy.aria.core.download;

import com.arialyy.annotations.TaskEnum;
import com.arialyy.aria.core.AriaConfig;
import com.arialyy.aria.core.AriaManager;
import com.arialyy.aria.core.command.CancelAllCmd;
import com.arialyy.aria.core.command.CmdHelper;
import com.arialyy.aria.core.command.NormalCmdFactory;
import com.arialyy.aria.core.common.AbsEntity;
import com.arialyy.aria.core.common.ProxyHelper;
import com.arialyy.aria.core.download.target.DTargetFactory;
import com.arialyy.aria.core.download.target.FtpBuilderTarget;
import com.arialyy.aria.core.download.target.FtpDirBuilderTarget;
import com.arialyy.aria.core.download.target.FtpDirNormalTarget;
import com.arialyy.aria.core.download.target.FtpNormalTarget;
import com.arialyy.aria.core.download.target.GroupBuilderTarget;
import com.arialyy.aria.core.download.target.GroupNormalTarget;
import com.arialyy.aria.core.download.target.HttpBuilderTarget;
import com.arialyy.aria.core.download.target.HttpNormalTarget;
import com.arialyy.aria.core.event.EventMsgUtil;
import com.arialyy.aria.core.inf.AbsReceiver;
import com.arialyy.aria.core.inf.ReceiverType;
import com.arialyy.aria.core.queue.DGroupTaskQueue;
import com.arialyy.aria.core.queue.DTaskQueue;
import com.arialyy.aria.core.scheduler.M3U8PeerTaskListener;
import com.arialyy.aria.core.scheduler.SubTaskListener;
import com.arialyy.aria.core.scheduler.TaskInternalListenerInterface;
import com.arialyy.aria.core.scheduler.TaskSchedulers;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.orm.DbEntity;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CheckUtil;
import com.arialyy.aria.util.CommonUtil;
import com.arialyy.aria.util.ComponentUtil;
import com.arialyy.aria.util.DbDataHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import k.a;

/* loaded from: classes2.dex */
public class DownloadReceiver extends AbsReceiver {
    public DownloadReceiver(Object obj) {
        super(obj);
    }

    public List<DownloadEntity> getAllCompleteTask() {
        return DbEntity.findDatas(DownloadEntity.class, "isGroupChild=? and downloadPath!='' and isComplete=?", a.f27524v, a.f27523u);
    }

    public List<DownloadEntity> getAllNotCompleteTask() {
        return DbEntity.findDatas(DownloadEntity.class, "isGroupChild=? and downloadPath!='' and isComplete=?", a.f27524v, a.f27524v);
    }

    public List<DownloadGroupEntity> getDGRunningTask() {
        return DGroupTaskQueue.getInstance().getRunningTask(DownloadGroupEntity.class);
    }

    public List<DownloadEntity> getDRunningTask() {
        return DTaskQueue.getInstance().getRunningTask(DownloadEntity.class);
    }

    public DownloadEntity getDownloadEntity(long j2) {
        if (j2 >= 0) {
            return (DownloadEntity) DbEntity.findFirst(DownloadEntity.class, "rowid=?", String.valueOf(j2));
        }
        ALog.e(this.TAG, "taskId错误");
        return null;
    }

    public DownloadEntity getFirstDownloadEntity(String str) {
        if (CheckUtil.checkUrl(str)) {
            return (DownloadEntity) DbEntity.findFirst(DownloadEntity.class, "url=? and isGroupChild='false'", str);
        }
        return null;
    }

    public DownloadGroupEntity getFtpDirEntity(String str) {
        if (CheckUtil.checkUrl(str)) {
            return DbDataHelper.getDGEntityByHash(str);
        }
        return null;
    }

    public DownloadGroupEntity getGroupEntity(long j2) {
        if (j2 < 0) {
            ALog.e(this.TAG, "任务Id错误");
        }
        return DbDataHelper.getDGEntity(j2);
    }

    public List<DownloadGroupEntity> getGroupTaskList() {
        return getGroupTaskList(1, 10);
    }

    public List<DownloadEntity> getTaskList() {
        return DbEntity.findDatas(DownloadEntity.class, "isGroupChild=? and downloadPath!=''", a.f27524v);
    }

    public List<AbsEntity> getTotalTaskList() {
        ArrayList arrayList = new ArrayList();
        List<DownloadEntity> taskList = getTaskList();
        List<DownloadGroupEntity> groupTaskList = getGroupTaskList();
        if (taskList != null && !taskList.isEmpty()) {
            arrayList.addAll(taskList);
        }
        if (groupTaskList != null && !groupTaskList.isEmpty()) {
            arrayList.addAll(groupTaskList);
        }
        return arrayList;
    }

    @Override // com.arialyy.aria.core.inf.IReceiver
    public ReceiverType getType() {
        return ReceiverType.DOWNLOAD;
    }

    public HttpBuilderTarget load(String str) throws ClassNotFoundException {
        ComponentUtil.getInstance().checkComponentExist(1);
        return (HttpBuilderTarget) DTargetFactory.getInstance().generateBuilderTarget(HttpBuilderTarget.class, str);
    }

    public FtpBuilderTarget loadFtp(String str) throws ClassNotFoundException {
        ComponentUtil.getInstance().checkComponentExist(2);
        return (FtpBuilderTarget) DTargetFactory.getInstance().generateBuilderTarget(FtpBuilderTarget.class, str);
    }

    public FtpDirBuilderTarget loadFtpDir(String str) throws ClassNotFoundException {
        ComponentUtil.getInstance().checkComponentExist(2);
        return DTargetFactory.getInstance().generateDirBuilderTarget(str);
    }

    public GroupBuilderTarget loadGroup(List<String> list) throws ClassNotFoundException {
        ComponentUtil.getInstance().checkComponentExist(1);
        return DTargetFactory.getInstance().generateGroupBuilderTarget(list);
    }

    @Override // com.arialyy.aria.core.inf.IReceiver
    public void register() {
        Object obj = this.obj;
        if (obj == null) {
            ALog.e(this.TAG, String.format("register【%s】观察者为空", getTargetName()));
            return;
        }
        if (obj instanceof TaskInternalListenerInterface) {
            if (obj instanceof DownloadTaskListener) {
                TaskSchedulers.getInstance().register(this.obj, TaskEnum.DOWNLOAD);
            }
            if (this.obj instanceof DownloadGroupTaskListener) {
                TaskSchedulers.getInstance().register(this.obj, TaskEnum.DOWNLOAD_GROUP);
            }
            if (this.obj instanceof M3U8PeerTaskListener) {
                TaskSchedulers.getInstance().register(this.obj, TaskEnum.M3U8_PEER);
            }
            if (this.obj instanceof SubTaskListener) {
                TaskSchedulers.getInstance().register(this.obj, TaskEnum.DOWNLOAD_GROUP_SUB);
                return;
            }
            return;
        }
        Set<Integer> setCheckProxyType = ProxyHelper.getInstance().checkProxyType(this.obj.getClass());
        if (setCheckProxyType == null || setCheckProxyType.isEmpty()) {
            ALog.e(this.TAG, "没有Aria的注解方法，详情见：https://aria.laoyuyu.me/aria_doc/other/annotaion_invalid.html");
            return;
        }
        for (Integer num : setCheckProxyType) {
            if (num.intValue() == ProxyHelper.PROXY_TYPE_DOWNLOAD) {
                TaskSchedulers.getInstance().register(this.obj, TaskEnum.DOWNLOAD);
            } else if (num.intValue() == ProxyHelper.PROXY_TYPE_DOWNLOAD_GROUP) {
                TaskSchedulers.getInstance().register(this.obj, TaskEnum.DOWNLOAD_GROUP);
            } else if (num.intValue() == ProxyHelper.PROXY_TYPE_M3U8_PEER) {
                TaskSchedulers.getInstance().register(this.obj, TaskEnum.M3U8_PEER);
            } else if (num.intValue() == ProxyHelper.PROXY_TYPE_DOWNLOAD_GROUP_SUB) {
                TaskSchedulers.getInstance().register(this.obj, TaskEnum.DOWNLOAD_GROUP_SUB);
            }
        }
    }

    public void removeAllTask(boolean z2) {
        AriaManager ariaManager = AriaManager.getInstance();
        CancelAllCmd cancelAllCmd = (CancelAllCmd) CmdHelper.createNormalCmd(new DTaskWrapper(null), 185, 1);
        cancelAllCmd.removeFile = z2;
        EventMsgUtil.getDefault().post(cancelAllCmd);
        Iterator<String> it = ariaManager.getReceiver().keySet().iterator();
        while (it.hasNext()) {
            ariaManager.getReceiver().remove(it.next());
        }
    }

    public void resumeAllTask() {
        EventMsgUtil.getDefault().post(NormalCmdFactory.getInstance().createCmd((AbsTaskWrapper) new DTaskWrapper(null), 184, 1));
    }

    @Deprecated
    public DownloadReceiver setMaxSpeed(int i2) throws Throwable {
        AriaConfig.getInstance().getDConfig().setMaxSpeed(i2);
        return this;
    }

    public void stopAllTask() {
        EventMsgUtil.getDefault().post(NormalCmdFactory.getInstance().createCmd((AbsTaskWrapper) new DTaskWrapper(null), 183, 1));
    }

    public boolean taskExists(String str) {
        return DbEntity.checkDataExist(DownloadEntity.class, "url=?", str);
    }

    @Override // com.arialyy.aria.core.inf.IReceiver
    public void unRegister() {
        if (isNeedRmListener()) {
            unRegisterListener();
        }
        AriaManager.getInstance().removeReceiver(this.obj);
    }

    @Override // com.arialyy.aria.core.inf.AbsReceiver
    public void unRegisterListener() {
        if (this.obj == null) {
            ALog.e(this.TAG, String.format("unRegister【%s】观察者为空", getTargetName()));
            return;
        }
        Set<Integer> set = ProxyHelper.getInstance().mProxyCache.get(this.obj.getClass().getName());
        if (set != null) {
            for (Integer num : set) {
                if (num.intValue() == ProxyHelper.PROXY_TYPE_DOWNLOAD) {
                    TaskSchedulers.getInstance().unRegister(this.obj);
                } else if (num.intValue() == ProxyHelper.PROXY_TYPE_DOWNLOAD_GROUP) {
                    TaskSchedulers.getInstance().unRegister(this.obj);
                }
            }
        }
    }

    public List<DownloadEntity> getAllCompleteTask(int i2, int i3) {
        CheckUtil.checkPageParams(i2, i3);
        return DbEntity.findDatas(DownloadEntity.class, "isGroupChild=? and downloadPath!='' and isComplete=?", a.f27524v, a.f27523u);
    }

    public List<DownloadEntity> getAllNotCompleteTask(int i2, int i3) {
        CheckUtil.checkPageParams(i2, i3);
        return DbEntity.findDatas(DownloadEntity.class, i2, i3, "isGroupChild=? and downloadPath!='' and isComplete=?", a.f27524v, a.f27524v);
    }

    public List<DownloadGroupEntity> getGroupTaskList(int i2, int i3) {
        List listFindRelationData = DbEntity.findRelationData(DGEntityWrapper.class, i2, i3, new String[0]);
        if (listFindRelationData == null || listFindRelationData.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = listFindRelationData.iterator();
        while (it.hasNext()) {
            arrayList.add(((DGEntityWrapper) it.next()).groupEntity);
        }
        return arrayList;
    }

    public List<DownloadEntity> getTaskList(int i2, int i3) {
        CheckUtil.checkPageParams(i2, i3);
        return DbEntity.findDatas(DownloadEntity.class, i2, i3, "isGroupChild=? and downloadPath!=''", a.f27524v);
    }

    public boolean taskExists(List<String> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        return DbEntity.checkDataExist(DownloadGroupEntity.class, "groupHash=?", CommonUtil.getMd5Code(list));
    }

    public List<DownloadEntity> getDownloadEntity(String str) {
        if (CheckUtil.checkUrl(str)) {
            return DbEntity.findDatas(DownloadEntity.class, "url=? and isGroupChild='false'", str);
        }
        return null;
    }

    public DownloadGroupEntity getGroupEntity(List<String> list) {
        if (CheckUtil.checkDownloadUrlsIsEmpty(list)) {
            return null;
        }
        return DbDataHelper.getDGEntityByHash(CommonUtil.getMd5Code(list));
    }

    public FtpDirNormalTarget loadFtpDir(long j2) throws ClassNotFoundException {
        ComponentUtil.getInstance().checkComponentExist(2);
        return (FtpDirNormalTarget) DTargetFactory.getInstance().generateNormalTarget(FtpDirNormalTarget.class, j2);
    }

    public GroupNormalTarget loadGroup(long j2) throws ClassNotFoundException {
        ComponentUtil.getInstance().checkComponentExist(1);
        return (GroupNormalTarget) DTargetFactory.getInstance().generateNormalTarget(GroupNormalTarget.class, j2);
    }

    public HttpNormalTarget load(long j2) throws ClassNotFoundException {
        ComponentUtil.getInstance().checkComponentExist(1);
        return (HttpNormalTarget) DTargetFactory.getInstance().generateNormalTarget(HttpNormalTarget.class, j2);
    }

    public FtpNormalTarget loadFtp(long j2) throws ClassNotFoundException {
        ComponentUtil.getInstance().checkComponentExist(2);
        return (FtpNormalTarget) DTargetFactory.getInstance().generateNormalTarget(FtpNormalTarget.class, j2);
    }
}

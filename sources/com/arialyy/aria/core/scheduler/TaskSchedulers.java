package com.arialyy.aria.core.scheduler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import com.arialyy.annotations.TaskEnum;
import com.arialyy.aria.core.AriaConfig;
import com.arialyy.aria.core.common.AbsEntity;
import com.arialyy.aria.core.group.GroupSendParams;
import com.arialyy.aria.core.listener.ISchedulers;
import com.arialyy.aria.core.manager.TaskWrapperManager;
import com.arialyy.aria.core.queue.DGroupTaskQueue;
import com.arialyy.aria.core.queue.DTaskQueue;
import com.arialyy.aria.core.queue.ITaskQueue;
import com.arialyy.aria.core.queue.UTaskQueue;
import com.arialyy.aria.core.task.AbsTask;
import com.arialyy.aria.core.task.ITask;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import com.arialyy.aria.util.NetUtils;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class TaskSchedulers<TASK extends ITask> implements ISchedulers {
    private static volatile TaskSchedulers INSTANCE;
    private static FailureTaskHandler mFailureTaskHandler;
    private final String TAG = CommonUtil.getClassName((Class) getClass());
    private Map<String, Map<TaskEnum, Object>> mObservers = new ConcurrentHashMap();
    private AriaConfig mAriaConfig = AriaConfig.getInstance();

    private TaskSchedulers() {
    }

    private Intent createData(int i2, int i3, AbsEntity absEntity) {
        Intent intent = new Intent(ISchedulers.ARIA_TASK_INFO_ACTION);
        intent.setPackage(this.mAriaConfig.getAPP().getPackageName());
        Bundle bundle = new Bundle();
        bundle.putInt(ISchedulers.TASK_TYPE, i3);
        bundle.putInt(ISchedulers.TASK_STATE, i2);
        bundle.putLong(ISchedulers.TASK_SPEED, absEntity.getSpeed());
        bundle.putInt(ISchedulers.TASK_PERCENT, absEntity.getPercent());
        bundle.putParcelable(ISchedulers.TASK_ENTITY, absEntity);
        intent.putExtras(bundle);
        return intent;
    }

    private ISchedulerListener createListener(String str) {
        try {
            return (ISchedulerListener) Class.forName(str).newInstance();
        } catch (ClassNotFoundException e2) {
            ALog.e(this.TAG, e2.getMessage());
            return null;
        } catch (IllegalAccessException e3) {
            ALog.e(this.TAG, e3.getMessage());
            return null;
        } catch (InstantiationException e4) {
            ALog.e(this.TAG, e4.getMessage());
            return null;
        }
    }

    public static TaskSchedulers getInstance() {
        if (INSTANCE == null) {
            synchronized (TaskSchedulers.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TaskSchedulers();
                    mFailureTaskHandler = FailureTaskHandler.init(INSTANCE);
                }
            }
        }
        return INSTANCE;
    }

    private String getKey(Object obj) {
        return obj.getClass().getName() + obj.hashCode();
    }

    private void handleFailTask(ITaskQueue iTaskQueue, TASK task) {
        if (!task.isNeedRetry() || task.isStop() || task.isCancel()) {
            iTaskQueue.removeTaskFormQueue(task.getKey());
            startNextTask(iTaskQueue, task.getSchedulerType());
            normalTaskCallback(4, task);
            return;
        }
        int reTryNum = task.getTaskWrapper().getConfig().getReTryNum();
        boolean zIsNotNetRetry = this.mAriaConfig.getAConfig().isNotNetRetry();
        if ((NetUtils.isConnected(this.mAriaConfig.getAPP()) || zIsNotNetRetry) && task.getTaskWrapper().getEntity().getFailNum() <= reTryNum) {
            mFailureTaskHandler.offer(task);
            return;
        }
        iTaskQueue.removeTaskFormQueue(task.getKey());
        startNextTask(iTaskQueue, task.getSchedulerType());
        TaskWrapperManager.getInstance().removeTaskWrapper(task.getTaskWrapper());
        normalTaskCallback(4, task);
    }

    private void handleNormalEvent(TASK task, int i2) {
        ITaskQueue queue = getQueue(task.getTaskType());
        if (i2 != 3) {
            if (i2 == 4) {
                handleFailTask(queue, task);
            } else if (i2 == 5) {
                queue.removeTaskFormQueue(task.getKey());
                if (queue.getCurrentExePoolNum() < queue.getMaxTaskNum()) {
                    ALog.d(this.TAG, String.format("删除任务【%s】成功，尝试开始下一任务", task.getTaskName()));
                    startNextTask(queue, task.getSchedulerType());
                } else {
                    ALog.d(this.TAG, String.format("删除任务【%s】成功", task.getTaskName()));
                }
            } else if (i2 == 6) {
                queue.removeTaskFormQueue(task.getKey());
                ALog.d(this.TAG, String.format("任务【%s】处理完成", task.getTaskName()));
                startNextTask(queue, task.getSchedulerType());
            }
        } else if (task.getState() != 3) {
            queue.removeTaskFormQueue(task.getKey());
            if (queue.getCurrentExePoolNum() < queue.getMaxTaskNum()) {
                ALog.d(this.TAG, String.format("停止任务【%s】成功，尝试开始下一任务", task.getTaskName()));
                startNextTask(queue, task.getSchedulerType());
            } else {
                ALog.d(this.TAG, String.format("停止任务【%s】成功", task.getTaskName()));
            }
        }
        if (i2 == 4 || i2 == 11) {
            return;
        }
        if (i2 == 5 || i2 == 6) {
            TaskWrapperManager.getInstance().removeTaskWrapper(task.getTaskWrapper());
        } else if (i2 != 7) {
            TaskWrapperManager.getInstance().putTaskWrapper(task.getTaskWrapper());
        }
        normalTaskCallback(i2, task);
    }

    private boolean handlePeerEvent(Message message) {
        M3U8PeerTaskListener m3U8PeerTaskListener;
        Bundle data = message.getData();
        if (this.mObservers.size() > 0) {
            Iterator<String> it = this.mObservers.keySet().iterator();
            while (it.hasNext()) {
                Map<TaskEnum, Object> map = this.mObservers.get(it.next());
                if (map != null && !map.isEmpty() && (m3U8PeerTaskListener = (M3U8PeerTaskListener) map.get(TaskEnum.M3U8_PEER)) != null) {
                    switch (message.what) {
                        case 177:
                            m3U8PeerTaskListener.onPeerStart(data.getString(ISchedulers.DATA_M3U8_URL), data.getString(ISchedulers.DATA_M3U8_PEER_PATH), data.getInt(ISchedulers.DATA_M3U8_PEER_INDEX));
                            break;
                        case 178:
                            m3U8PeerTaskListener.onPeerComplete(data.getString(ISchedulers.DATA_M3U8_URL), data.getString(ISchedulers.DATA_M3U8_PEER_PATH), data.getInt(ISchedulers.DATA_M3U8_PEER_INDEX));
                            break;
                        case 179:
                            m3U8PeerTaskListener.onPeerFail(data.getString(ISchedulers.DATA_M3U8_URL), data.getString(ISchedulers.DATA_M3U8_PEER_PATH), data.getInt(ISchedulers.DATA_M3U8_PEER_INDEX));
                            break;
                    }
                }
            }
        }
        if (!this.mAriaConfig.getAConfig().isUseBroadcast()) {
            return true;
        }
        Intent intent = new Intent(ISchedulers.ARIA_TASK_INFO_ACTION);
        intent.setPackage(this.mAriaConfig.getAPP().getPackageName());
        intent.putExtras(data);
        this.mAriaConfig.getAPP().sendBroadcast(intent);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x009e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0053 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void handlePreFailTask(int r6) {
        /*
            r5 = this;
            com.arialyy.aria.core.queue.ITaskQueue r0 = r5.getQueue(r6)
            r1 = 1
            r5.startNextTask(r0, r1)
            com.arialyy.aria.core.AriaConfig r0 = r5.mAriaConfig
            com.arialyy.aria.core.config.AppConfig r0 = r0.getAConfig()
            boolean r0 = r0.isUseBroadcast()
            if (r0 == 0) goto L41
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r2 = "ARIA_TASK_INFO_ACTION"
            r0.<init>(r2)
            com.arialyy.aria.core.AriaConfig r2 = r5.mAriaConfig
            android.content.Context r2 = r2.getAPP()
            java.lang.String r2 = r2.getPackageName()
            r0.setPackage(r2)
            android.os.Bundle r2 = new android.os.Bundle
            r2.<init>()
            java.lang.String r3 = "ARIA_TASK_TYPE"
            r2.putInt(r3, r6)
            java.lang.String r3 = "ARIA_TASK_STATE"
            r4 = 4
            r2.putInt(r3, r4)
            com.arialyy.aria.core.AriaConfig r2 = r5.mAriaConfig
            android.content.Context r2 = r2.getAPP()
            r2.sendBroadcast(r0)
        L41:
            java.util.Map<java.lang.String, java.util.Map<com.arialyy.annotations.TaskEnum, java.lang.Object>> r0 = r5.mObservers
            int r0 = r0.size()
            if (r0 <= 0) goto La4
            java.util.Map<java.lang.String, java.util.Map<com.arialyy.annotations.TaskEnum, java.lang.Object>> r0 = r5.mObservers
            java.util.Set r0 = r0.keySet()
            java.util.Iterator r0 = r0.iterator()
        L53:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto La4
            java.lang.Object r2 = r0.next()
            java.lang.String r2 = (java.lang.String) r2
            java.util.Map<java.lang.String, java.util.Map<com.arialyy.annotations.TaskEnum, java.lang.Object>> r3 = r5.mObservers
            java.lang.Object r3 = r3.get(r2)
            java.util.Map r3 = (java.util.Map) r3
            if (r3 == 0) goto L53
            boolean r4 = r3.isEmpty()
            if (r4 == 0) goto L70
            goto L53
        L70:
            java.util.Map<java.lang.String, java.util.Map<com.arialyy.annotations.TaskEnum, java.lang.Object>> r4 = r5.mObservers
            java.lang.Object r2 = r4.get(r2)
            r4 = 0
            if (r2 == 0) goto L9b
            if (r6 != r1) goto L84
            com.arialyy.annotations.TaskEnum r2 = com.arialyy.annotations.TaskEnum.DOWNLOAD
            java.lang.Object r2 = r3.get(r2)
            com.arialyy.aria.core.scheduler.NormalTaskListenerInterface r2 = (com.arialyy.aria.core.scheduler.NormalTaskListenerInterface) r2
            goto L9c
        L84:
            r2 = 3
            if (r6 != r2) goto L90
            com.arialyy.annotations.TaskEnum r2 = com.arialyy.annotations.TaskEnum.DOWNLOAD_GROUP
            java.lang.Object r2 = r3.get(r2)
            com.arialyy.aria.core.scheduler.NormalTaskListenerInterface r2 = (com.arialyy.aria.core.scheduler.NormalTaskListenerInterface) r2
            goto L9c
        L90:
            if (r6 != r2) goto L9b
            com.arialyy.annotations.TaskEnum r2 = com.arialyy.annotations.TaskEnum.UPLOAD
            java.lang.Object r2 = r3.get(r2)
            com.arialyy.aria.core.scheduler.NormalTaskListenerInterface r2 = (com.arialyy.aria.core.scheduler.NormalTaskListenerInterface) r2
            goto L9c
        L9b:
            r2 = r4
        L9c:
            if (r2 == 0) goto L53
            r3 = 11
            r5.normalTaskCallback(r3, r4, r2)
            goto L53
        La4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.arialyy.aria.core.scheduler.TaskSchedulers.handlePreFailTask(int):void");
    }

    private boolean handleSubEvent(Message message) {
        SubTaskListener subTaskListener;
        GroupSendParams groupSendParams = (GroupSendParams) message.obj;
        if (this.mObservers.size() > 0) {
            Iterator<String> it = this.mObservers.keySet().iterator();
            while (it.hasNext()) {
                Map<TaskEnum, Object> map = this.mObservers.get(it.next());
                if (map != null && !map.isEmpty() && (subTaskListener = (SubTaskListener) map.get(TaskEnum.DOWNLOAD_GROUP_SUB)) != null) {
                    switch (message.what) {
                        case 161:
                            subTaskListener.onSubTaskPre(groupSendParams.groupTask, groupSendParams.entity);
                            break;
                        case 162:
                            subTaskListener.onSubTaskStart(groupSendParams.groupTask, groupSendParams.entity);
                            break;
                        case 163:
                            subTaskListener.onSubTaskStop(groupSendParams.groupTask, groupSendParams.entity);
                            break;
                        case 164:
                            subTaskListener.onSubTaskCancel(groupSendParams.groupTask, groupSendParams.entity);
                            break;
                        case 165:
                            GROUP_TASK group_task = groupSendParams.groupTask;
                            subTaskListener.onSubTaskFail(group_task, groupSendParams.entity, (Exception) group_task.getExpand(AbsTask.ERROR_INFO_KEY));
                            break;
                        case 166:
                            subTaskListener.onSubTaskRunning(groupSendParams.groupTask, groupSendParams.entity);
                            break;
                        case 167:
                            subTaskListener.onSubTaskComplete(groupSendParams.groupTask, groupSendParams.entity);
                            break;
                    }
                }
            }
        }
        if (!this.mAriaConfig.getAConfig().isUseBroadcast()) {
            return true;
        }
        this.mAriaConfig.getAPP().sendBroadcast(createData(message.what, 4, groupSendParams.entity));
        return true;
    }

    private boolean hasProxyListener(Map<TaskEnum, Object> map, TaskEnum taskEnum) {
        return (map.isEmpty() || map.get(taskEnum) == null) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0064 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0015 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void normalTaskCallback(int r5, TASK r6) {
        /*
            r4 = this;
            r4.sendNormalBroadcast(r5, r6)
            java.util.Map<java.lang.String, java.util.Map<com.arialyy.annotations.TaskEnum, java.lang.Object>> r0 = r4.mObservers
            int r0 = r0.size()
            if (r0 <= 0) goto L68
            java.util.Map<java.lang.String, java.util.Map<com.arialyy.annotations.TaskEnum, java.lang.Object>> r0 = r4.mObservers
            java.util.Set r0 = r0.keySet()
            java.util.Iterator r0 = r0.iterator()
        L15:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L68
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            java.util.Map<java.lang.String, java.util.Map<com.arialyy.annotations.TaskEnum, java.lang.Object>> r2 = r4.mObservers
            java.lang.Object r2 = r2.get(r1)
            java.util.Map r2 = (java.util.Map) r2
            if (r2 == 0) goto L15
            boolean r3 = r2.isEmpty()
            if (r3 == 0) goto L32
            goto L15
        L32:
            java.util.Map<java.lang.String, java.util.Map<com.arialyy.annotations.TaskEnum, java.lang.Object>> r3 = r4.mObservers
            java.lang.Object r1 = r3.get(r1)
            if (r1 == 0) goto L61
            boolean r1 = r6 instanceof com.arialyy.aria.core.task.DownloadTask
            if (r1 == 0) goto L47
            com.arialyy.annotations.TaskEnum r1 = com.arialyy.annotations.TaskEnum.DOWNLOAD
            java.lang.Object r1 = r2.get(r1)
            com.arialyy.aria.core.scheduler.NormalTaskListenerInterface r1 = (com.arialyy.aria.core.scheduler.NormalTaskListenerInterface) r1
            goto L62
        L47:
            boolean r1 = r6 instanceof com.arialyy.aria.core.task.DownloadGroupTask
            if (r1 == 0) goto L54
            com.arialyy.annotations.TaskEnum r1 = com.arialyy.annotations.TaskEnum.DOWNLOAD_GROUP
            java.lang.Object r1 = r2.get(r1)
            com.arialyy.aria.core.scheduler.NormalTaskListenerInterface r1 = (com.arialyy.aria.core.scheduler.NormalTaskListenerInterface) r1
            goto L62
        L54:
            boolean r1 = r6 instanceof com.arialyy.aria.core.task.UploadTask
            if (r1 == 0) goto L61
            com.arialyy.annotations.TaskEnum r1 = com.arialyy.annotations.TaskEnum.UPLOAD
            java.lang.Object r1 = r2.get(r1)
            com.arialyy.aria.core.scheduler.NormalTaskListenerInterface r1 = (com.arialyy.aria.core.scheduler.NormalTaskListenerInterface) r1
            goto L62
        L61:
            r1 = 0
        L62:
            if (r1 == 0) goto L15
            r4.normalTaskCallback(r5, r6, r1)
            goto L15
        L68:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.arialyy.aria.core.scheduler.TaskSchedulers.normalTaskCallback(int, com.arialyy.aria.core.task.ITask):void");
    }

    private void sendNormalBroadcast(int i2, TASK task) {
        if (this.mAriaConfig.getAConfig().isUseBroadcast()) {
            int taskType = task.getTaskType();
            if (taskType == 1 || taskType == 3) {
                this.mAriaConfig.getAPP().sendBroadcast(createData(i2, taskType, task.getTaskWrapper().getEntity()));
            } else if (taskType == 2) {
                this.mAriaConfig.getAPP().sendBroadcast(createData(i2, taskType, task.getTaskWrapper().getEntity()));
            } else {
                ALog.w(this.TAG, "发送广播失败，没有对应的任务");
            }
        }
    }

    public ITaskQueue getQueue(int i2) {
        if (i2 == 1) {
            return DTaskQueue.getInstance();
        }
        if (i2 == 3) {
            return DGroupTaskQueue.getInstance();
        }
        if (i2 == 2) {
            return UTaskQueue.getInstance();
        }
        throw new NullPointerException("任务类型错误，type = " + i2);
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        int i2 = message.what;
        if (i2 == 11) {
            handlePreFailTask(message.arg1);
            return true;
        }
        int i3 = message.arg1;
        if (i3 == 209) {
            return handleSubEvent(message);
        }
        if (i3 == 210) {
            return handlePeerEvent(message);
        }
        ITask iTask = (ITask) message.obj;
        if (iTask == null) {
            ALog.e(this.TAG, "请传入下载任务");
            return true;
        }
        handleNormalEvent(iTask, i2);
        return true;
    }

    public void register(Object obj, TaskEnum taskEnum) {
        String name = obj.getClass().getName();
        Map<TaskEnum, Object> concurrentHashMap = this.mObservers.get(getKey(obj));
        if (concurrentHashMap == null) {
            concurrentHashMap = new ConcurrentHashMap<>();
            this.mObservers.put(getKey(obj), concurrentHashMap);
        }
        if (hasProxyListener(concurrentHashMap, taskEnum)) {
            return;
        }
        if (obj instanceof TaskInternalListenerInterface) {
            concurrentHashMap.put(taskEnum, obj);
            return;
        }
        String str = name + taskEnum.proxySuffix;
        ISchedulerListener iSchedulerListenerCreateListener = createListener(str);
        if (iSchedulerListenerCreateListener != null) {
            iSchedulerListenerCreateListener.setListener(obj);
            concurrentHashMap.put(taskEnum, iSchedulerListenerCreateListener);
            return;
        }
        ALog.e(this.TAG, "注册错误，没有【" + str + "】观察者");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void startNextTask(ITaskQueue iTaskQueue, int i2) {
        if (i2 == 2) {
            return;
        }
        ITask nextTask = iTaskQueue.getNextTask();
        if (nextTask == null) {
            if (iTaskQueue.getCurrentExePoolNum() == 0) {
                ALog.i(this.TAG, "没有等待中的任务");
            }
        } else if (nextTask.getState() == 3) {
            iTaskQueue.startTask(nextTask);
        }
    }

    public void unRegister(Object obj) {
        if (this.mObservers.containsKey(getKey(obj))) {
            Iterator<Map.Entry<String, Map<TaskEnum, Object>>> it = this.mObservers.entrySet().iterator();
            while (it.hasNext()) {
                if (it.next().getKey().equals(getKey(obj))) {
                    it.remove();
                }
            }
        }
    }

    private void normalTaskCallback(int i2, TASK task, NormalTaskListenerInterface<TASK> normalTaskListenerInterface) {
        if (normalTaskListenerInterface != null) {
            if (task == null && i2 != 11) {
                ALog.e(this.TAG, "TASK 为null，回调失败");
            }
            switch (i2) {
                case 0:
                    normalTaskListenerInterface.onPre(task);
                    break;
                case 1:
                    normalTaskListenerInterface.onTaskPre(task);
                    break;
                case 2:
                    normalTaskListenerInterface.onTaskStart(task);
                    break;
                case 3:
                    normalTaskListenerInterface.onTaskStop(task);
                    break;
                case 4:
                    normalTaskListenerInterface.onTaskFail(task.getTaskType() != 6 ? task : null, (Exception) task.getExpand(AbsTask.ERROR_INFO_KEY));
                    break;
                case 5:
                    normalTaskListenerInterface.onTaskCancel(task);
                    break;
                case 6:
                    normalTaskListenerInterface.onTaskComplete(task);
                    break;
                case 7:
                    normalTaskListenerInterface.onTaskRunning(task);
                    break;
                case 8:
                    normalTaskListenerInterface.onTaskResume(task);
                    break;
                case 9:
                    normalTaskListenerInterface.onNoSupportBreakPoint(task);
                    break;
                case 10:
                    normalTaskListenerInterface.onWait(task);
                    break;
                case 11:
                    normalTaskListenerInterface.onTaskFail(null, null);
                    break;
            }
        }
    }
}

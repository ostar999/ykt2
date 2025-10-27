package com.arialyy.aria.core.group;

import com.arialyy.aria.core.config.Configuration;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
final class SimpleSubQueue implements ISubQueue<AbsSubDLoadUtil> {
    private final String TAG = CommonUtil.getClassName(SimpleSubQueue.class);
    private Map<String, AbsSubDLoadUtil> mCache = new ConcurrentHashMap();
    private Map<String, AbsSubDLoadUtil> mExec = new ConcurrentHashMap();
    private boolean isStopAll = false;
    private int mExecSize = Configuration.getInstance().dGroupCfg.getSubMaxTaskNum();

    private SimpleSubQueue() {
    }

    public static SimpleSubQueue newInstance() {
        return new SimpleSubQueue();
    }

    @Override // com.arialyy.aria.core.group.ISubQueue
    public void clear() {
        this.mCache.clear();
        this.mExec.clear();
    }

    public int getCacheSize() {
        return this.mCache.size();
    }

    public synchronized AbsSubDLoadUtil getLoaderUtil(String str) {
        AbsSubDLoadUtil absSubDLoadUtil = this.mExec.get(str);
        if (absSubDLoadUtil != null) {
            return absSubDLoadUtil;
        }
        return this.mCache.get(str);
    }

    public boolean isStopAll() {
        return this.isStopAll;
    }

    @Override // com.arialyy.aria.core.group.ISubQueue
    public void modifyMaxExecNum(int i2) {
        int i3 = 0;
        if (i2 < 1) {
            ALog.e(this.TAG, String.format("修改组合任务子任务队列数失败，num: %s", Integer.valueOf(i2)));
            return;
        }
        int i4 = this.mExecSize;
        if (i2 == i4) {
            ALog.i(this.TAG, String.format("忽略此次修改，oldSize: %s, num: %s", Integer.valueOf(i4), Integer.valueOf(i2)));
            return;
        }
        this.mExecSize = i2;
        int iAbs = Math.abs(i4 - i2);
        if (i4 >= i2) {
            if (this.mExec.size() < i2) {
                while (i3 < iAbs) {
                    AbsSubDLoadUtil nextTask = getNextTask();
                    if (nextTask != null) {
                        startTask(nextTask);
                    } else {
                        ALog.d(this.TAG, "子任务中没有缓存任务");
                    }
                    i3++;
                }
                return;
            }
            return;
        }
        if (this.mExec.size() > i2) {
            Set<String> setKeySet = this.mExec.keySet();
            ArrayList arrayList = new ArrayList();
            for (String str : setKeySet) {
                if (i3 > i2) {
                    arrayList.add(this.mExec.get(str));
                }
                i3++;
            }
            Collection<AbsSubDLoadUtil> collectionValues = this.mCache.values();
            this.mCache.clear();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                addTask((AbsSubDLoadUtil) it.next());
            }
            Iterator<AbsSubDLoadUtil> it2 = collectionValues.iterator();
            while (it2.hasNext()) {
                addTask(it2.next());
            }
        }
    }

    @Override // com.arialyy.aria.core.group.ISubQueue
    public void removeAllTask() {
        ALog.d(this.TAG, "删除组合任务");
        Iterator<String> it = this.mExec.keySet().iterator();
        while (it.hasNext()) {
            AbsSubDLoadUtil absSubDLoadUtil = this.mExec.get(it.next());
            if (absSubDLoadUtil != null) {
                ALog.d(this.TAG, String.format("停止子任务：%s", absSubDLoadUtil.getEntity().getFileName()));
                absSubDLoadUtil.cancel();
            }
        }
    }

    @Override // com.arialyy.aria.core.group.ISubQueue
    public void stopAllTask() {
        this.isStopAll = true;
        ALog.d(this.TAG, "停止组合任务");
        Iterator<String> it = this.mExec.keySet().iterator();
        while (it.hasNext()) {
            AbsSubDLoadUtil absSubDLoadUtil = this.mExec.get(it.next());
            if (absSubDLoadUtil != null) {
                ALog.d(this.TAG, String.format("停止子任务：%s", absSubDLoadUtil.getEntity().getFileName()));
                absSubDLoadUtil.stop();
            }
        }
    }

    @Override // com.arialyy.aria.core.group.ISubQueue
    public void addTask(AbsSubDLoadUtil absSubDLoadUtil) {
        this.mCache.put(absSubDLoadUtil.getKey(), absSubDLoadUtil);
    }

    @Override // com.arialyy.aria.core.group.ISubQueue
    public AbsSubDLoadUtil getNextTask() {
        Iterator<String> it = this.mCache.keySet().iterator();
        if (it.hasNext()) {
            return this.mCache.get(it.next());
        }
        return null;
    }

    @Override // com.arialyy.aria.core.group.ISubQueue
    public void removeTask(AbsSubDLoadUtil absSubDLoadUtil) {
        removeTaskFromExecQ(absSubDLoadUtil);
        this.mCache.remove(absSubDLoadUtil.getKey());
    }

    @Override // com.arialyy.aria.core.group.ISubQueue
    public void removeTaskFromExecQ(AbsSubDLoadUtil absSubDLoadUtil) {
        this.mExec.remove(absSubDLoadUtil.getKey());
    }

    @Override // com.arialyy.aria.core.group.ISubQueue
    public void startTask(AbsSubDLoadUtil absSubDLoadUtil) {
        if (this.mExec.size() >= this.mExecSize) {
            ALog.d(this.TAG, String.format("执行队列已满，任务进入缓存器中，key: %s", absSubDLoadUtil.getKey()));
            addTask(absSubDLoadUtil);
        } else {
            this.mCache.remove(absSubDLoadUtil.getKey());
            this.mExec.put(absSubDLoadUtil.getKey(), absSubDLoadUtil);
            ALog.d(this.TAG, String.format("开始执行子任务：%s，key: %s", absSubDLoadUtil.getEntity().getFileName(), absSubDLoadUtil.getKey()));
            absSubDLoadUtil.run();
        }
    }

    @Override // com.arialyy.aria.core.group.ISubQueue
    public void stopTask(AbsSubDLoadUtil absSubDLoadUtil) {
        absSubDLoadUtil.stop();
        this.mExec.remove(absSubDLoadUtil.getKey());
    }
}

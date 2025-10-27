package com.arialyy.aria.core.loader;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.arialyy.aria.core.TaskRecord;
import com.arialyy.aria.core.inf.IThreadStateManager;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import com.arialyy.aria.util.FileUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class GroupSubThreadStateManager implements IThreadStateManager {
    private Handler mHandler;
    private String mKey;
    private Looper mLooper;
    private long mProgress;
    private TaskRecord mTaskRecord;
    private int mThreadNum;
    private final String TAG = CommonUtil.getClassName(this);
    private AtomicInteger mCancelNum = new AtomicInteger(0);
    private AtomicInteger mStopNum = new AtomicInteger(0);
    private AtomicInteger mFailNum = new AtomicInteger(0);
    private AtomicInteger mCompleteNum = new AtomicInteger(0);
    private Handler.Callback callback = new Handler.Callback() { // from class: com.arialyy.aria.core.loader.GroupSubThreadStateManager.1
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            GroupSubThreadStateManager.this.checkLooper();
            switch (message.what) {
                case 1:
                    GroupSubThreadStateManager.this.mStopNum.getAndIncrement();
                    if (GroupSubThreadStateManager.this.isStop()) {
                        GroupSubThreadStateManager.this.quitLooper();
                    }
                    sendMessageFromMsg(message);
                    return false;
                case 2:
                    GroupSubThreadStateManager.this.mFailNum.getAndIncrement();
                    if (GroupSubThreadStateManager.this.isFail()) {
                        sendMessageFromMsg(message);
                        GroupSubThreadStateManager.this.quitLooper();
                    }
                    return false;
                case 3:
                    GroupSubThreadStateManager.this.mCancelNum.getAndIncrement();
                    if (GroupSubThreadStateManager.this.isCancel()) {
                        GroupSubThreadStateManager.this.quitLooper();
                    }
                    sendMessageFromMsg(message);
                    return false;
                case 4:
                    GroupSubThreadStateManager.this.mCompleteNum.getAndIncrement();
                    if (GroupSubThreadStateManager.this.isComplete()) {
                        ALog.d(GroupSubThreadStateManager.this.TAG, "isComplete, completeNum = " + GroupSubThreadStateManager.this.mCompleteNum);
                        if (GroupSubThreadStateManager.this.mTaskRecord.isBlock && !GroupSubThreadStateManager.this.mergeFile()) {
                            Bundle data = message.getData();
                            data.putBoolean(IThreadStateManager.DATA_RETRY, false);
                            message.setData(data);
                            message.what = 2;
                            sendMessageFromMsg(message);
                        }
                        sendMessageFromMsg(message);
                        GroupSubThreadStateManager.this.quitLooper();
                    } else if (GroupSubThreadStateManager.this.isFail()) {
                        sendMessageFromMsg(message);
                        GroupSubThreadStateManager.this.quitLooper();
                    }
                    return false;
                case 5:
                    Bundle data2 = message.getData();
                    if (data2 != null) {
                        GroupSubThreadStateManager.this.mProgress += data2.getLong(IThreadStateManager.DATA_ADD_LEN, 0L);
                    }
                    message.obj = Long.valueOf(GroupSubThreadStateManager.this.mProgress);
                    sendMessageFromMsg(message);
                    return false;
                case 6:
                    Object obj = message.obj;
                    if (obj == null) {
                        GroupSubThreadStateManager groupSubThreadStateManager = GroupSubThreadStateManager.this;
                        groupSubThreadStateManager.mProgress = groupSubThreadStateManager.updateBlockProgress();
                    } else if (obj instanceof Long) {
                        GroupSubThreadStateManager.this.mProgress = ((Long) obj).longValue();
                    }
                    message.obj = Long.valueOf(GroupSubThreadStateManager.this.mProgress);
                    sendMessageFromMsg(message);
                    return false;
                default:
                    return false;
            }
        }

        public void sendMessageFromMsg(Message message) {
            Message messageObtainMessage = GroupSubThreadStateManager.this.mHandler.obtainMessage();
            Bundle data = messageObtainMessage.getData();
            data.putString(IThreadStateManager.DATA_THREAD_NAME, GroupSubThreadStateManager.this.mKey);
            message.setData(data);
            messageObtainMessage.copyFrom(message);
            GroupSubThreadStateManager.this.mHandler.sendMessage(messageObtainMessage);
        }
    };

    public GroupSubThreadStateManager(Handler handler, String str) {
        this.mHandler = handler;
        this.mKey = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkLooper() {
        if (this.mTaskRecord == null) {
            throw new NullPointerException("任务记录为空");
        }
        if (this.mLooper == null) {
            throw new NullPointerException("Looper为空");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean mergeFile() {
        if (this.mTaskRecord.threadNum == 1) {
            return new File(String.format(IRecordHandler.SUB_PATH, this.mTaskRecord.filePath, 0)).renameTo(new File(this.mTaskRecord.filePath));
        }
        ArrayList arrayList = new ArrayList();
        int i2 = this.mTaskRecord.threadNum;
        for (int i3 = 0; i3 < i2; i3++) {
            arrayList.add(String.format(IRecordHandler.SUB_PATH, this.mTaskRecord.filePath, Integer.valueOf(i3)));
        }
        if (!FileUtil.mergeFile(this.mTaskRecord.filePath, arrayList)) {
            ALog.e(this.TAG, "合并失败");
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            FileUtil.deleteFile((String) it.next());
        }
        File file = new File(this.mTaskRecord.filePath);
        if (!file.exists() || file.length() <= this.mTaskRecord.fileLength) {
            return true;
        }
        ALog.e(this.TAG, String.format("任务【%s】分块文件合并失败，下载长度超出文件真实长度，downloadLen: %s，fileSize: %s", file.getName(), Long.valueOf(file.length()), Long.valueOf(this.mTaskRecord.fileLength)));
        return false;
    }

    private boolean mergerSFtp() throws Throwable {
        if (this.mTaskRecord.threadNum == 1) {
            return new File(String.format(IRecordHandler.SUB_PATH, this.mTaskRecord.filePath, 0)).renameTo(new File(this.mTaskRecord.filePath));
        }
        ArrayList arrayList = new ArrayList();
        int i2 = this.mTaskRecord.threadNum;
        for (int i3 = 0; i3 < i2; i3++) {
            arrayList.add(String.format(IRecordHandler.SUB_PATH, this.mTaskRecord.filePath, Integer.valueOf(i3)));
        }
        TaskRecord taskRecord = this.mTaskRecord;
        FileUtil.mergeSFtpFile(taskRecord.filePath, arrayList, taskRecord.fileLength);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            FileUtil.deleteFile((String) it.next());
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void quitLooper() {
        this.mLooper.quit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long updateBlockProgress() {
        int size = this.mTaskRecord.threadRecords.size();
        long length = 0;
        for (int i2 = 0; i2 < size; i2++) {
            File file = new File(String.format(IRecordHandler.SUB_PATH, this.mTaskRecord.filePath, Integer.valueOf(i2)));
            if (file.exists()) {
                length += file.length();
            }
        }
        return length;
    }

    @Override // com.arialyy.aria.core.loader.ILoaderComponent
    public void accept(ILoaderVisitor iLoaderVisitor) {
        iLoaderVisitor.addComponent(this);
    }

    @Override // com.arialyy.aria.core.inf.IThreadStateManager
    public long getCurrentProgress() {
        return this.mProgress;
    }

    @Override // com.arialyy.aria.core.inf.IThreadStateManager
    public Handler.Callback getHandlerCallback() {
        return this.callback;
    }

    public boolean isCancel() {
        return this.mCancelNum.get() == this.mThreadNum;
    }

    @Override // com.arialyy.aria.core.inf.IThreadStateManager
    public boolean isComplete() {
        return this.mCompleteNum.get() == this.mThreadNum;
    }

    @Override // com.arialyy.aria.core.inf.IThreadStateManager
    public boolean isFail() {
        return this.mCompleteNum.get() != this.mThreadNum && (this.mFailNum.get() == this.mThreadNum || this.mFailNum.get() + this.mCompleteNum.get() == this.mThreadNum);
    }

    public boolean isStop() {
        return this.mStopNum.get() == this.mThreadNum || this.mStopNum.get() + this.mCompleteNum.get() == this.mThreadNum;
    }

    @Override // com.arialyy.aria.core.inf.IThreadStateManager
    public void setLooper(TaskRecord taskRecord, Looper looper) {
        this.mTaskRecord = taskRecord;
        this.mThreadNum = taskRecord.threadNum;
        this.mLooper = looper;
    }

    @Override // com.arialyy.aria.core.inf.IThreadStateManager
    public void updateCurrentProgress(long j2) {
        this.mProgress = j2;
    }
}

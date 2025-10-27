package net.lingala.zip4j.progress;

import net.lingala.zip4j.exception.ZipException;

/* loaded from: classes9.dex */
public class ProgressMonitor {
    public static final int OPERATION_ADD = 0;
    public static final int OPERATION_CALC_CRC = 3;
    public static final int OPERATION_EXTRACT = 1;
    public static final int OPERATION_MERGE = 4;
    public static final int OPERATION_NONE = -1;
    public static final int OPERATION_REMOVE = 2;
    public static final int RESULT_CANCELLED = 3;
    public static final int RESULT_ERROR = 2;
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_WORKING = 1;
    public static final int STATE_BUSY = 1;
    public static final int STATE_READY = 0;
    private boolean cancelAllTasks;
    private int currentOperation;
    private Throwable exception;
    private String fileName;
    private boolean pause;
    private int percentDone;
    private int result;
    private int state;
    private long totalWork;
    private long workCompleted;

    public ProgressMonitor() {
        reset();
        this.percentDone = 0;
    }

    public void cancelAllTasks() {
        this.cancelAllTasks = true;
    }

    public void endProgressMonitorError(Throwable th) throws ZipException {
        reset();
        this.result = 2;
        this.exception = th;
    }

    public void endProgressMonitorSuccess() throws ZipException {
        reset();
        this.result = 0;
    }

    public void fullReset() {
        reset();
        this.exception = null;
        this.result = 0;
    }

    public int getCurrentOperation() {
        return this.currentOperation;
    }

    public Throwable getException() {
        return this.exception;
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getPercentDone() {
        return this.percentDone;
    }

    public int getResult() {
        return this.result;
    }

    public int getState() {
        return this.state;
    }

    public long getTotalWork() {
        return this.totalWork;
    }

    public long getWorkCompleted() {
        return this.workCompleted;
    }

    public boolean isCancelAllTasks() {
        return this.cancelAllTasks;
    }

    public boolean isPause() {
        return this.pause;
    }

    public void reset() {
        this.currentOperation = -1;
        this.state = 0;
        this.fileName = null;
        this.totalWork = 0L;
        this.workCompleted = 0L;
        this.percentDone = 0;
    }

    public void setCurrentOperation(int i2) {
        this.currentOperation = i2;
    }

    public void setException(Throwable th) {
        this.exception = th;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public void setPause(boolean z2) {
        this.pause = z2;
    }

    public void setPercentDone(int i2) {
        this.percentDone = i2;
    }

    public void setResult(int i2) {
        this.result = i2;
    }

    public void setState(int i2) {
        this.state = i2;
    }

    public void setTotalWork(long j2) {
        this.totalWork = j2;
    }

    public void updateWorkCompleted(long j2) throws InterruptedException {
        long j3 = this.workCompleted + j2;
        this.workCompleted = j3;
        long j4 = this.totalWork;
        if (j4 > 0) {
            int i2 = (int) ((j3 * 100) / j4);
            this.percentDone = i2;
            if (i2 > 100) {
                this.percentDone = 100;
            }
        }
        while (this.pause) {
            try {
                Thread.sleep(150L);
            } catch (InterruptedException unused) {
            }
        }
    }
}

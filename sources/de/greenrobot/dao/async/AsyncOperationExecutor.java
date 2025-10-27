package de.greenrobot.dao.async;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import de.greenrobot.dao.DaoException;
import de.greenrobot.dao.DaoLog;
import de.greenrobot.dao.async.AsyncOperation;
import de.greenrobot.dao.query.Query;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
class AsyncOperationExecutor implements Runnable, Handler.Callback {
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private int countOperationsCompleted;
    private int countOperationsEnqueued;
    private volatile boolean executorRunning;
    private Handler handlerMainThread;
    private int lastSequenceNumber;
    private volatile AsyncOperationListener listener;
    private volatile AsyncOperationListener listenerMainThread;
    private final BlockingQueue<AsyncOperation> queue = new LinkedBlockingQueue();
    private volatile int maxOperationCountToMerge = 50;
    private volatile int waitForMergeMillis = 50;

    /* renamed from: de.greenrobot.dao.async.AsyncOperationExecutor$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType;

        static {
            int[] iArr = new int[AsyncOperation.OperationType.values().length];
            $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType = iArr;
            try {
                iArr[AsyncOperation.OperationType.Delete.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.DeleteInTxIterable.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.DeleteInTxArray.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.Insert.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.InsertInTxIterable.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.InsertInTxArray.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.InsertOrReplace.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.InsertOrReplaceInTxIterable.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.InsertOrReplaceInTxArray.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.Update.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.UpdateInTxIterable.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.UpdateInTxArray.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.TransactionRunnable.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.TransactionCallable.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.QueryList.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.QueryUnique.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.DeleteByKey.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.DeleteAll.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.Load.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.LoadAll.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.Count.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.Refresh.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
        }
    }

    private void executeOperation(AsyncOperation asyncOperation) {
        asyncOperation.timeStarted = System.currentTimeMillis();
        try {
            switch (AnonymousClass1.$SwitchMap$de$greenrobot$dao$async$AsyncOperation$OperationType[asyncOperation.type.ordinal()]) {
                case 1:
                    asyncOperation.dao.delete(asyncOperation.parameter);
                    break;
                case 2:
                    asyncOperation.dao.deleteInTx((Iterable<Object>) asyncOperation.parameter);
                    break;
                case 3:
                    asyncOperation.dao.deleteInTx((Object[]) asyncOperation.parameter);
                    break;
                case 4:
                    asyncOperation.dao.insert(asyncOperation.parameter);
                    break;
                case 5:
                    asyncOperation.dao.insertInTx((Iterable<Object>) asyncOperation.parameter);
                    break;
                case 6:
                    asyncOperation.dao.insertInTx((Object[]) asyncOperation.parameter);
                    break;
                case 7:
                    asyncOperation.dao.insertOrReplace(asyncOperation.parameter);
                    break;
                case 8:
                    asyncOperation.dao.insertOrReplaceInTx((Iterable<Object>) asyncOperation.parameter);
                    break;
                case 9:
                    asyncOperation.dao.insertOrReplaceInTx((Object[]) asyncOperation.parameter);
                    break;
                case 10:
                    asyncOperation.dao.update(asyncOperation.parameter);
                    break;
                case 11:
                    asyncOperation.dao.updateInTx((Iterable<Object>) asyncOperation.parameter);
                    break;
                case 12:
                    asyncOperation.dao.updateInTx((Object[]) asyncOperation.parameter);
                    break;
                case 13:
                    executeTransactionRunnable(asyncOperation);
                    break;
                case 14:
                    executeTransactionCallable(asyncOperation);
                    break;
                case 15:
                    asyncOperation.result = ((Query) asyncOperation.parameter).list();
                    break;
                case 16:
                    asyncOperation.result = ((Query) asyncOperation.parameter).unique();
                    break;
                case 17:
                    asyncOperation.dao.deleteByKey(asyncOperation.parameter);
                    break;
                case 18:
                    asyncOperation.dao.deleteAll();
                    break;
                case 19:
                    asyncOperation.result = asyncOperation.dao.load(asyncOperation.parameter);
                    break;
                case 20:
                    asyncOperation.result = asyncOperation.dao.loadAll();
                    break;
                case 21:
                    asyncOperation.result = Long.valueOf(asyncOperation.dao.count());
                    break;
                case 22:
                    asyncOperation.dao.refresh(asyncOperation.parameter);
                    break;
                default:
                    throw new DaoException("Unsupported operation: " + asyncOperation.type);
            }
        } catch (Throwable th) {
            asyncOperation.throwable = th;
        }
        asyncOperation.timeCompleted = System.currentTimeMillis();
    }

    private void executeOperationAndPostCompleted(AsyncOperation asyncOperation) {
        executeOperation(asyncOperation);
        handleOperationCompleted(asyncOperation);
    }

    private void executeTransactionCallable(AsyncOperation asyncOperation) throws Exception {
        SQLiteDatabase database = asyncOperation.getDatabase();
        database.beginTransaction();
        try {
            asyncOperation.result = ((Callable) asyncOperation.parameter).call();
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    private void executeTransactionRunnable(AsyncOperation asyncOperation) {
        SQLiteDatabase database = asyncOperation.getDatabase();
        database.beginTransaction();
        try {
            ((Runnable) asyncOperation.parameter).run();
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    private void handleOperationCompleted(AsyncOperation asyncOperation) {
        asyncOperation.setCompleted();
        AsyncOperationListener asyncOperationListener = this.listener;
        if (asyncOperationListener != null) {
            asyncOperationListener.onAsyncOperationCompleted(asyncOperation);
        }
        if (this.listenerMainThread != null) {
            if (this.handlerMainThread == null) {
                this.handlerMainThread = new Handler(Looper.getMainLooper(), this);
            }
            this.handlerMainThread.sendMessage(this.handlerMainThread.obtainMessage(1, asyncOperation));
        }
        synchronized (this) {
            int i2 = this.countOperationsCompleted + 1;
            this.countOperationsCompleted = i2;
            if (i2 == this.countOperationsEnqueued) {
                notifyAll();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x005a, code lost:
    
        throw new de.greenrobot.dao.DaoException("Internal error: peeked op did not match removed op");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void mergeTxAndExecute(de.greenrobot.dao.async.AsyncOperation r6, de.greenrobot.dao.async.AsyncOperation r7) {
        /*
            r5 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r0.add(r6)
            r0.add(r7)
            android.database.sqlite.SQLiteDatabase r6 = r6.getDatabase()
            r6.beginTransaction()
            r7 = 0
            r1 = r7
        L14:
            int r2 = r0.size()     // Catch: java.lang.Throwable -> L9d
            if (r1 >= r2) goto L61
            java.lang.Object r2 = r0.get(r1)     // Catch: java.lang.Throwable -> L9d
            de.greenrobot.dao.async.AsyncOperation r2 = (de.greenrobot.dao.async.AsyncOperation) r2     // Catch: java.lang.Throwable -> L9d
            r5.executeOperation(r2)     // Catch: java.lang.Throwable -> L9d
            boolean r3 = r2.isFailed()     // Catch: java.lang.Throwable -> L9d
            r4 = 1
            if (r3 == 0) goto L2c
            r7 = r4
            goto L61
        L2c:
            int r3 = r0.size()     // Catch: java.lang.Throwable -> L9d
            int r3 = r3 - r4
            if (r1 != r3) goto L5e
            java.util.concurrent.BlockingQueue<de.greenrobot.dao.async.AsyncOperation> r3 = r5.queue     // Catch: java.lang.Throwable -> L9d
            java.lang.Object r3 = r3.peek()     // Catch: java.lang.Throwable -> L9d
            de.greenrobot.dao.async.AsyncOperation r3 = (de.greenrobot.dao.async.AsyncOperation) r3     // Catch: java.lang.Throwable -> L9d
            int r4 = r5.maxOperationCountToMerge     // Catch: java.lang.Throwable -> L9d
            if (r1 >= r4) goto L5b
            boolean r2 = r2.isMergeableWith(r3)     // Catch: java.lang.Throwable -> L9d
            if (r2 == 0) goto L5b
            java.util.concurrent.BlockingQueue<de.greenrobot.dao.async.AsyncOperation> r2 = r5.queue     // Catch: java.lang.Throwable -> L9d
            java.lang.Object r2 = r2.remove()     // Catch: java.lang.Throwable -> L9d
            de.greenrobot.dao.async.AsyncOperation r2 = (de.greenrobot.dao.async.AsyncOperation) r2     // Catch: java.lang.Throwable -> L9d
            if (r2 != r3) goto L53
            r0.add(r2)     // Catch: java.lang.Throwable -> L9d
            goto L5e
        L53:
            de.greenrobot.dao.DaoException r7 = new de.greenrobot.dao.DaoException     // Catch: java.lang.Throwable -> L9d
            java.lang.String r0 = "Internal error: peeked op did not match removed op"
            r7.<init>(r0)     // Catch: java.lang.Throwable -> L9d
            throw r7     // Catch: java.lang.Throwable -> L9d
        L5b:
            r6.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L9d
        L5e:
            int r1 = r1 + 1
            goto L14
        L61:
            r6.endTransaction()
            if (r7 == 0) goto L82
            java.lang.String r6 = "Revered merged transaction because one of the operations failed. Executing operations one by one instead..."
            de.greenrobot.dao.DaoLog.i(r6)
            java.util.Iterator r6 = r0.iterator()
        L6f:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L9c
            java.lang.Object r7 = r6.next()
            de.greenrobot.dao.async.AsyncOperation r7 = (de.greenrobot.dao.async.AsyncOperation) r7
            r7.reset()
            r5.executeOperationAndPostCompleted(r7)
            goto L6f
        L82:
            int r6 = r0.size()
            java.util.Iterator r7 = r0.iterator()
        L8a:
            boolean r0 = r7.hasNext()
            if (r0 == 0) goto L9c
            java.lang.Object r0 = r7.next()
            de.greenrobot.dao.async.AsyncOperation r0 = (de.greenrobot.dao.async.AsyncOperation) r0
            r0.mergedOperationsCount = r6
            r5.handleOperationCompleted(r0)
            goto L8a
        L9c:
            return
        L9d:
            r7 = move-exception
            r6.endTransaction()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: de.greenrobot.dao.async.AsyncOperationExecutor.mergeTxAndExecute(de.greenrobot.dao.async.AsyncOperation, de.greenrobot.dao.async.AsyncOperation):void");
    }

    public void enqueue(AsyncOperation asyncOperation) {
        synchronized (this) {
            int i2 = this.lastSequenceNumber + 1;
            this.lastSequenceNumber = i2;
            asyncOperation.sequenceNumber = i2;
            this.queue.add(asyncOperation);
            this.countOperationsEnqueued++;
            if (!this.executorRunning) {
                this.executorRunning = true;
                executorService.execute(this);
            }
        }
    }

    public AsyncOperationListener getListener() {
        return this.listener;
    }

    public AsyncOperationListener getListenerMainThread() {
        return this.listenerMainThread;
    }

    public int getMaxOperationCountToMerge() {
        return this.maxOperationCountToMerge;
    }

    public int getWaitForMergeMillis() {
        return this.waitForMergeMillis;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        AsyncOperationListener asyncOperationListener = this.listenerMainThread;
        if (asyncOperationListener == null) {
            return false;
        }
        asyncOperationListener.onAsyncOperationCompleted((AsyncOperation) message.obj);
        return false;
    }

    public synchronized boolean isCompleted() {
        return this.countOperationsEnqueued == this.countOperationsCompleted;
    }

    @Override // java.lang.Runnable
    public void run() {
        AsyncOperation asyncOperationPoll;
        while (true) {
            try {
                AsyncOperation asyncOperationPoll2 = this.queue.poll(1L, TimeUnit.SECONDS);
                if (asyncOperationPoll2 == null) {
                    synchronized (this) {
                        asyncOperationPoll2 = this.queue.poll();
                        if (asyncOperationPoll2 == null) {
                            return;
                        }
                    }
                }
                if (!asyncOperationPoll2.isMergeTx() || (asyncOperationPoll = this.queue.poll(this.waitForMergeMillis, TimeUnit.MILLISECONDS)) == null) {
                    executeOperationAndPostCompleted(asyncOperationPoll2);
                } else if (asyncOperationPoll2.isMergeableWith(asyncOperationPoll)) {
                    mergeTxAndExecute(asyncOperationPoll2, asyncOperationPoll);
                } else {
                    executeOperationAndPostCompleted(asyncOperationPoll2);
                    executeOperationAndPostCompleted(asyncOperationPoll);
                }
            } catch (InterruptedException e2) {
                DaoLog.w(Thread.currentThread().getName() + " was interruppted", e2);
                return;
            } finally {
                this.executorRunning = false;
            }
        }
    }

    public void setListener(AsyncOperationListener asyncOperationListener) {
        this.listener = asyncOperationListener;
    }

    public void setListenerMainThread(AsyncOperationListener asyncOperationListener) {
        this.listenerMainThread = asyncOperationListener;
    }

    public void setMaxOperationCountToMerge(int i2) {
        this.maxOperationCountToMerge = i2;
    }

    public void setWaitForMergeMillis(int i2) {
        this.waitForMergeMillis = i2;
    }

    public synchronized void waitForCompletion() {
        while (!isCompleted()) {
            try {
                wait();
            } catch (InterruptedException e2) {
                throw new DaoException("Interrupted while waiting for all operations to complete", e2);
            }
        }
    }

    public synchronized boolean waitForCompletion(int i2) {
        if (!isCompleted()) {
            try {
                wait(i2);
            } catch (InterruptedException e2) {
                throw new DaoException("Interrupted while waiting for all operations to complete", e2);
            }
        }
        return isCompleted();
    }
}

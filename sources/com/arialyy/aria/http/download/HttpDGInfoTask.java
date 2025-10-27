package com.arialyy.aria.http.download;

import com.arialyy.aria.core.common.AbsEntity;
import com.arialyy.aria.core.common.CompleteInfo;
import com.arialyy.aria.core.download.DGTaskWrapper;
import com.arialyy.aria.core.download.DTaskWrapper;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.download.DownloadGroupEntity;
import com.arialyy.aria.core.listener.DownloadGroupListener;
import com.arialyy.aria.core.loader.IInfoTask;
import com.arialyy.aria.core.loader.ILoaderVisitor;
import com.arialyy.aria.exception.AriaException;
import com.arialyy.aria.exception.AriaHTTPException;
import com.arialyy.aria.http.HttpTaskOption;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public final class HttpDGInfoTask implements IInfoTask {
    private IInfoTask.Callback callback;
    private DownloadGroupListener listener;
    private DGTaskWrapper wrapper;
    private String TAG = CommonUtil.getClassName(this);
    private final Object LOCK = new Object();
    private ExecutorService mPool = null;
    private boolean getLenComplete = false;
    private AtomicInteger count = new AtomicInteger();
    private AtomicInteger failCount = new AtomicInteger();
    private IInfoTask.Callback subCallback = new IInfoTask.Callback() { // from class: com.arialyy.aria.http.download.HttpDGInfoTask.1
        @Override // com.arialyy.aria.core.loader.IInfoTask.Callback
        public void onFail(AbsEntity absEntity, AriaException ariaException, boolean z2) throws Throwable {
            DownloadEntity downloadEntity = (DownloadEntity) absEntity;
            ALog.e(HttpDGInfoTask.this.TAG, String.format("获取文件信息失败，url：%s", downloadEntity.getUrl()));
            HttpDGInfoTask.this.count.getAndIncrement();
            HttpDGInfoTask.this.failCount.getAndIncrement();
            HttpDGInfoTask.this.listener.onSubFail(downloadEntity, new AriaHTTPException(String.format("子任务获取文件长度失败，url：%s", downloadEntity.getUrl())));
            HttpDGInfoTask httpDGInfoTask = HttpDGInfoTask.this;
            httpDGInfoTask.checkGetSizeComplete(httpDGInfoTask.count.get(), HttpDGInfoTask.this.failCount.get());
        }

        @Override // com.arialyy.aria.core.loader.IInfoTask.Callback
        public void onSucceed(String str, CompleteInfo completeInfo) {
            HttpDGInfoTask.this.count.getAndIncrement();
            HttpDGInfoTask httpDGInfoTask = HttpDGInfoTask.this;
            httpDGInfoTask.checkGetSizeComplete(httpDGInfoTask.count.get(), HttpDGInfoTask.this.failCount.get());
            ALog.d(HttpDGInfoTask.this.TAG, "获取子任务信息完成");
        }
    };

    public HttpDGInfoTask(DGTaskWrapper dGTaskWrapper, DownloadGroupListener downloadGroupListener) {
        this.wrapper = dGTaskWrapper;
        this.listener = downloadGroupListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void checkGetSizeComplete(int i2, int i3) {
        if (i3 == this.wrapper.getSubTaskWrapper().size()) {
            this.callback.onFail(this.wrapper.getEntity(), new AriaHTTPException("获取子任务长度失败"), false);
            notifyLock();
            return;
        }
        if (i2 == this.wrapper.getSubTaskWrapper().size()) {
            Iterator<DTaskWrapper> it = this.wrapper.getSubTaskWrapper().iterator();
            long fileSize = 0;
            while (it.hasNext()) {
                fileSize += it.next().getEntity().getFileSize();
            }
            ((DownloadGroupEntity) this.wrapper.getEntity()).setConvertFileSize(CommonUtil.formatFileSize(fileSize));
            ((DownloadGroupEntity) this.wrapper.getEntity()).setFileSize(fileSize);
            ((DownloadGroupEntity) this.wrapper.getEntity()).update();
            this.getLenComplete = true;
            ALog.d(this.TAG, String.format("获取组合任务长度完成，组合任务总长度：%s，失败的子任务数：%s", Long.valueOf(fileSize), Integer.valueOf(i3)));
            this.callback.onSucceed(this.wrapper.getKey(), new CompleteInfo());
            notifyLock();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cloneHeader(DTaskWrapper dTaskWrapper) {
        HttpTaskOption httpTaskOption = (HttpTaskOption) this.wrapper.getTaskOption();
        HttpTaskOption httpTaskOption2 = new HttpTaskOption();
        httpTaskOption2.setFileLenAdapter(httpTaskOption.getFileLenAdapter());
        httpTaskOption2.setFileNameAdapter(httpTaskOption.getFileNameAdapter());
        httpTaskOption2.setUseServerFileName(httpTaskOption.isUseServerFileName());
        httpTaskOption2.setFileNameAdapter(httpTaskOption.getFileNameAdapter());
        httpTaskOption2.setRequestEnum(httpTaskOption.getRequestEnum());
        httpTaskOption2.setHeaders(httpTaskOption.getHeaders());
        httpTaskOption2.setProxy(httpTaskOption.getProxy());
        httpTaskOption2.setParams(httpTaskOption.getParams());
        dTaskWrapper.setTaskOption(httpTaskOption2);
    }

    private void getGroupSize() {
        new Thread(new Runnable() { // from class: com.arialyy.aria.http.download.HttpDGInfoTask.2
            @Override // java.lang.Runnable
            public void run() {
                for (DTaskWrapper dTaskWrapper : HttpDGInfoTask.this.wrapper.getSubTaskWrapper()) {
                    DownloadEntity entity = dTaskWrapper.getEntity();
                    if (entity.getFileSize() > 0) {
                        HttpDGInfoTask.this.count.getAndIncrement();
                        if (entity.getCurrentProgress() < entity.getFileSize()) {
                            HttpDGInfoTask.this.cloneHeader(dTaskWrapper);
                        }
                        HttpDGInfoTask httpDGInfoTask = HttpDGInfoTask.this;
                        httpDGInfoTask.checkGetSizeComplete(httpDGInfoTask.count.get(), HttpDGInfoTask.this.failCount.get());
                    } else {
                        HttpDGInfoTask.this.cloneHeader(dTaskWrapper);
                        HttpDFileInfoTask httpDFileInfoTask = new HttpDFileInfoTask(dTaskWrapper);
                        httpDFileInfoTask.setCallback(HttpDGInfoTask.this.subCallback);
                        HttpDGInfoTask.this.mPool.execute(httpDFileInfoTask);
                    }
                }
            }
        }).start();
    }

    private void notifyLock() {
        synchronized (this.LOCK) {
            this.LOCK.notifyAll();
        }
    }

    @Override // com.arialyy.aria.core.loader.ILoaderComponent
    public void accept(ILoaderVisitor iLoaderVisitor) {
        iLoaderVisitor.addComponent(this);
    }

    @Override // com.arialyy.aria.core.loader.IInfoTask, java.lang.Runnable
    public void run() {
        if (this.mPool != null && !this.getLenComplete) {
            ALog.d(this.TAG, "获取长度未完成的情况下，停止组合任务");
            this.mPool.shutdown();
            this.listener.onStop(0L);
            return;
        }
        if (!this.wrapper.isUnknownSize()) {
            Iterator<DTaskWrapper> it = this.wrapper.getSubTaskWrapper().iterator();
            while (it.hasNext()) {
                cloneHeader(it.next());
            }
            this.callback.onSucceed(this.wrapper.getKey(), new CompleteInfo());
            return;
        }
        this.mPool = Executors.newCachedThreadPool();
        getGroupSize();
        try {
            synchronized (this.LOCK) {
                this.LOCK.wait();
            }
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        if (this.mPool.isShutdown()) {
            return;
        }
        this.mPool.shutdown();
    }

    @Override // com.arialyy.aria.core.loader.IInfoTask
    public void setCallback(IInfoTask.Callback callback) {
        this.callback = callback;
    }
}

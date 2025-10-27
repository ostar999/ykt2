package com.alibaba.sdk.android.oss.internal;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.TaskCancelException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import com.alibaba.sdk.android.oss.common.OSSHeaders;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.MultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PartETag;
import com.alibaba.sdk.android.oss.network.ExecutionContext;
import com.google.android.exoplayer2.C;
import com.yikaobang.yixue.R2;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public abstract class BaseMultipartUploadTask<Request extends MultipartUploadRequest, Result extends CompleteMultipartUploadResult> implements Callable<Result> {
    protected final int CPU_SIZE;
    protected final int KEEP_ALIVE_TIME;
    protected final int MAX_CORE_POOL_SIZE;
    protected final int MAX_IMUM_POOL_SIZE;
    protected final int MAX_QUEUE_SIZE;
    protected final int PART_SIZE_ALIGN_NUM;
    protected InternalRequestOperation mApiOperation;
    protected boolean mCheckCRC64;
    protected OSSCompletedCallback<Request, Result> mCompletedCallback;
    protected ExecutionContext mContext;
    protected long mFileLength;
    protected boolean mIsCancel;
    protected long mLastPartSize;
    protected Object mLock;
    protected int[] mPartAttr;
    protected List<PartETag> mPartETags;
    protected int mPartExceptionCount;
    protected ThreadPoolExecutor mPoolExecutor;
    protected OSSProgressCallback<Request> mProgressCallback;
    protected Request mRequest;
    protected int mRunPartTaskCount;
    protected Exception mUploadException;
    protected File mUploadFile;
    protected String mUploadFilePath;
    protected String mUploadId;
    protected Uri mUploadUri;
    protected long mUploadedLength;

    public BaseMultipartUploadTask(InternalRequestOperation internalRequestOperation, Request request, OSSCompletedCallback<Request, Result> oSSCompletedCallback, ExecutionContext executionContext) {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors() * 2;
        this.CPU_SIZE = iAvailableProcessors;
        int i2 = iAvailableProcessors < 5 ? iAvailableProcessors : 5;
        this.MAX_CORE_POOL_SIZE = i2;
        this.MAX_IMUM_POOL_SIZE = iAvailableProcessors;
        this.KEEP_ALIVE_TIME = 3000;
        this.MAX_QUEUE_SIZE = 5000;
        this.PART_SIZE_ALIGN_NUM = 4096;
        this.mPoolExecutor = new ThreadPoolExecutor(i2, iAvailableProcessors, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(5000), new ThreadFactory() { // from class: com.alibaba.sdk.android.oss.internal.BaseMultipartUploadTask.1
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "oss-android-multipart-thread");
            }
        });
        this.mPartETags = new ArrayList();
        this.mLock = new Object();
        this.mUploadedLength = 0L;
        this.mCheckCRC64 = false;
        this.mPartAttr = new int[2];
        this.mApiOperation = internalRequestOperation;
        this.mRequest = request;
        this.mProgressCallback = request.getProgressCallback();
        this.mCompletedCallback = oSSCompletedCallback;
        this.mContext = executionContext;
        this.mCheckCRC64 = request.getCRC64() == OSSRequest.CRC64Config.YES;
    }

    public abstract void abortThisUpload();

    public long ceilPartSize(long j2) {
        return ((j2 + 4095) / 4096) * 4096;
    }

    public void checkCancel() throws ClientException {
        if (this.mContext.getCancellationHandler().isCancelled()) {
            TaskCancelException taskCancelException = new TaskCancelException("multipart cancel");
            throw new ClientException(taskCancelException.getMessage(), taskCancelException, Boolean.TRUE);
        }
    }

    public void checkException() throws ServiceException, ClientException, IOException {
        if (this.mUploadException != null) {
            releasePool();
            Exception exc = this.mUploadException;
            if (exc instanceof IOException) {
                throw ((IOException) exc);
            }
            if (exc instanceof ServiceException) {
                throw ((ServiceException) exc);
            }
            if (!(exc instanceof ClientException)) {
                throw new ClientException(this.mUploadException.getMessage(), this.mUploadException);
            }
            throw ((ClientException) exc);
        }
    }

    public void checkInitData() throws ClientException, IOException {
        if (this.mRequest.getUploadFilePath() != null) {
            this.mUploadFilePath = this.mRequest.getUploadFilePath();
            this.mUploadedLength = 0L;
            File file = new File(this.mUploadFilePath);
            this.mUploadFile = file;
            this.mFileLength = file.length();
        } else if (this.mRequest.getUploadUri() != null) {
            this.mUploadUri = this.mRequest.getUploadUri();
            ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = null;
            try {
                try {
                    parcelFileDescriptorOpenFileDescriptor = this.mContext.getApplicationContext().getContentResolver().openFileDescriptor(this.mUploadUri, "r");
                    this.mFileLength = parcelFileDescriptorOpenFileDescriptor.getStatSize();
                    try {
                        parcelFileDescriptorOpenFileDescriptor.close();
                    } catch (IOException e2) {
                        OSSLog.logThrowable2Local(e2);
                    }
                } catch (IOException e3) {
                    throw new ClientException(e3.getMessage(), e3, Boolean.TRUE);
                }
            } catch (Throwable th) {
                if (parcelFileDescriptorOpenFileDescriptor != null) {
                    try {
                        parcelFileDescriptorOpenFileDescriptor.close();
                    } catch (IOException e4) {
                        OSSLog.logThrowable2Local(e4);
                    }
                }
                throw th;
            }
        }
        if (this.mFileLength == 0) {
            throw new ClientException("file length must not be 0");
        }
        checkPartSize(this.mPartAttr);
        long partSize = this.mRequest.getPartSize();
        int i2 = this.mPartAttr[1];
        OSSLog.logDebug("[checkInitData] - partNumber : " + i2);
        OSSLog.logDebug("[checkInitData] - partSize : " + partSize);
        if (i2 > 1 && partSize < OSSConstants.MIN_PART_SIZE_LIMIT) {
            throw new ClientException("Part size must be greater than or equal to 100KB!");
        }
    }

    public void checkPartSize(int[] iArr) {
        long partSize = this.mRequest.getPartSize();
        OSSLog.logDebug("[checkPartSize] - mFileLength : " + this.mFileLength);
        OSSLog.logDebug("[checkPartSize] - partSize : " + partSize);
        long j2 = this.mFileLength;
        long j3 = j2 / partSize;
        if (j2 % partSize != 0) {
            j3++;
        }
        if (j3 == 1) {
            partSize = j2;
        } else if (j3 > 5000) {
            partSize = ceilPartSize(j2 / R2.color.line_txt_color_night);
            long j4 = this.mFileLength;
            j3 = (j4 / partSize) + (j4 % partSize == 0 ? 0L : 1L);
        }
        int i2 = (int) partSize;
        iArr[0] = i2;
        iArr[1] = (int) j3;
        this.mRequest.setPartSize(i2);
        OSSLog.logDebug("[checkPartSize] - partNumber : " + j3);
        OSSLog.logDebug("[checkPartSize] - partSize : " + i2);
        long j5 = this.mFileLength % partSize;
        if (j5 != 0) {
            partSize = j5;
        }
        this.mLastPartSize = partSize;
    }

    public boolean checkWaitCondition(int i2) {
        return this.mPartETags.size() != i2;
    }

    public CompleteMultipartUploadResult completeMultipartUploadResult() throws ServiceException, ClientException {
        CompleteMultipartUploadResult completeMultipartUploadResultSyncCompleteMultipartUpload;
        if (this.mPartETags.size() > 0) {
            Collections.sort(this.mPartETags, new Comparator<PartETag>() { // from class: com.alibaba.sdk.android.oss.internal.BaseMultipartUploadTask.2
                @Override // java.util.Comparator
                public int compare(PartETag partETag, PartETag partETag2) {
                    if (partETag.getPartNumber() < partETag2.getPartNumber()) {
                        return -1;
                    }
                    return partETag.getPartNumber() > partETag2.getPartNumber() ? 1 : 0;
                }
            });
            CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(this.mRequest.getBucketName(), this.mRequest.getObjectKey(), this.mUploadId, this.mPartETags);
            if (this.mRequest.getCallbackParam() != null) {
                completeMultipartUploadRequest.setCallbackParam(this.mRequest.getCallbackParam());
            }
            if (this.mRequest.getCallbackVars() != null) {
                completeMultipartUploadRequest.setCallbackVars(this.mRequest.getCallbackVars());
            }
            if (this.mRequest.getMetadata() != null) {
                ObjectMetadata objectMetadata = new ObjectMetadata();
                for (String str : this.mRequest.getMetadata().getRawMetadata().keySet()) {
                    if (!str.equals(OSSHeaders.STORAGE_CLASS)) {
                        objectMetadata.setHeader(str, this.mRequest.getMetadata().getRawMetadata().get(str));
                    }
                }
                completeMultipartUploadRequest.setMetadata(objectMetadata);
            }
            completeMultipartUploadRequest.setCRC64(this.mRequest.getCRC64());
            completeMultipartUploadResultSyncCompleteMultipartUpload = this.mApiOperation.syncCompleteMultipartUpload(completeMultipartUploadRequest);
        } else {
            completeMultipartUploadResultSyncCompleteMultipartUpload = null;
        }
        this.mUploadedLength = 0L;
        return completeMultipartUploadResultSyncCompleteMultipartUpload;
    }

    public abstract Result doMultipartUpload() throws ServiceException, ClientException, InterruptedException, IOException;

    public abstract void initMultipartUploadId() throws ServiceException, ClientException, IOException;

    public void notifyMultipartThread() {
        this.mLock.notify();
        this.mPartExceptionCount = 0;
    }

    public void onProgressCallback(Request request, long j2, long j3) {
        OSSProgressCallback<Request> oSSProgressCallback = this.mProgressCallback;
        if (oSSProgressCallback != null) {
            oSSProgressCallback.onProgress(request, j2, j3);
        }
    }

    public void preUploadPart(int i2, int i3, int i4) throws Exception {
    }

    public abstract void processException(Exception exc);

    public void releasePool() {
        ThreadPoolExecutor threadPoolExecutor = this.mPoolExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.getQueue().clear();
            this.mPoolExecutor.shutdown();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x00a7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0169 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:114:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:117:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0150 A[Catch: IOException -> 0x0154, TRY_ENTER, TryCatch #3 {IOException -> 0x0154, blocks: (B:48:0x011d, B:50:0x0122, B:52:0x0127, B:74:0x0150, B:78:0x0158, B:80:0x015d), top: B:101:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0158 A[Catch: IOException -> 0x0154, TryCatch #3 {IOException -> 0x0154, blocks: (B:48:0x011d, B:50:0x0122, B:52:0x0127, B:74:0x0150, B:78:0x0158, B:80:0x015d), top: B:101:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x015d A[Catch: IOException -> 0x0154, TRY_LEAVE, TryCatch #3 {IOException -> 0x0154, blocks: (B:48:0x011d, B:50:0x0122, B:52:0x0127, B:74:0x0150, B:78:0x0158, B:80:0x015d), top: B:101:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0171 A[Catch: IOException -> 0x016d, TryCatch #6 {IOException -> 0x016d, blocks: (B:87:0x0169, B:91:0x0171, B:93:0x0176), top: B:103:0x0169 }] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0176 A[Catch: IOException -> 0x016d, TRY_LEAVE, TryCatch #6 {IOException -> 0x016d, blocks: (B:87:0x0169, B:91:0x0171, B:93:0x0176), top: B:103:0x0169 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void uploadPart(int r12, int r13, int r14) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 382
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.oss.internal.BaseMultipartUploadTask.uploadPart(int, int, int):void");
    }

    public void uploadPartFinish(PartETag partETag) throws Exception {
    }

    @Override // java.util.concurrent.Callable
    public Result call() throws Exception {
        try {
            checkInitData();
            initMultipartUploadId();
            Result result = (Result) doMultipartUpload();
            OSSCompletedCallback<Request, Result> oSSCompletedCallback = this.mCompletedCallback;
            if (oSSCompletedCallback != null) {
                oSSCompletedCallback.onSuccess(this.mRequest, result);
            }
            return result;
        } catch (ServiceException e2) {
            OSSCompletedCallback<Request, Result> oSSCompletedCallback2 = this.mCompletedCallback;
            if (oSSCompletedCallback2 != null) {
                oSSCompletedCallback2.onFailure(this.mRequest, null, e2);
            }
            throw e2;
        } catch (Exception e3) {
            ClientException clientException = e3 instanceof ClientException ? (ClientException) e3 : new ClientException(e3.toString(), e3);
            OSSCompletedCallback<Request, Result> oSSCompletedCallback3 = this.mCompletedCallback;
            if (oSSCompletedCallback3 != null) {
                oSSCompletedCallback3.onFailure(this.mRequest, clientException, null);
            }
            throw clientException;
        }
    }
}

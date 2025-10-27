package com.alibaba.sdk.android.oss.internal;

import android.text.TextUtils;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.utils.OSSSharedPreferences;
import com.alibaba.sdk.android.oss.model.AbortMultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.PartETag;
import com.alibaba.sdk.android.oss.model.ResumableUploadRequest;
import com.alibaba.sdk.android.oss.model.ResumableUploadResult;
import com.alibaba.sdk.android.oss.network.ExecutionContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/* loaded from: classes2.dex */
public class SequenceUploadTask extends BaseMultipartUploadTask<ResumableUploadRequest, ResumableUploadResult> implements Callable<ResumableUploadResult> {
    private List<Integer> mAlreadyUploadIndex;
    private File mCRC64RecordFile;
    private long mFirstPartSize;
    private File mRecordFile;
    private OSSSharedPreferences mSp;

    public SequenceUploadTask(ResumableUploadRequest resumableUploadRequest, OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult> oSSCompletedCallback, ExecutionContext executionContext, InternalRequestOperation internalRequestOperation) {
        super(internalRequestOperation, resumableUploadRequest, oSSCompletedCallback, executionContext);
        this.mAlreadyUploadIndex = new ArrayList();
        this.mSp = OSSSharedPreferences.instance(this.mContext.getApplicationContext());
    }

    @Override // com.alibaba.sdk.android.oss.internal.BaseMultipartUploadTask
    public void abortThisUpload() throws ExecutionException, InterruptedException {
        if (this.mUploadId != null) {
            this.mApiOperation.abortMultipartUpload(new AbortMultipartUploadRequest(((ResumableUploadRequest) this.mRequest).getBucketName(), ((ResumableUploadRequest) this.mRequest).getObjectKey(), this.mUploadId), null).waitUntilFinished();
        }
    }

    @Override // com.alibaba.sdk.android.oss.internal.BaseMultipartUploadTask
    public void checkException() throws Throwable {
        if (this.mContext.getCancellationHandler().isCancelled()) {
            if (((ResumableUploadRequest) this.mRequest).deleteUploadOnCancelling().booleanValue()) {
                abortThisUpload();
                File file = this.mRecordFile;
                if (file != null) {
                    file.delete();
                }
            } else {
                List<PartETag> list = this.mPartETags;
                if (list != null && list.size() > 0 && this.mCheckCRC64 && ((ResumableUploadRequest) this.mRequest).getRecordDirectory() != null) {
                    HashMap map = new HashMap();
                    for (PartETag partETag : this.mPartETags) {
                        map.put(Integer.valueOf(partETag.getPartNumber()), Long.valueOf(partETag.getCRC64()));
                    }
                    ObjectOutputStream objectOutputStream = null;
                    try {
                        try {
                            File file2 = new File(((ResumableUploadRequest) this.mRequest).getRecordDirectory() + File.separator + this.mUploadId);
                            this.mCRC64RecordFile = file2;
                            if (!file2.exists()) {
                                this.mCRC64RecordFile.createNewFile();
                            }
                            ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new FileOutputStream(this.mCRC64RecordFile));
                            try {
                                objectOutputStream2.writeObject(map);
                                objectOutputStream2.close();
                            } catch (IOException e2) {
                                e = e2;
                                objectOutputStream = objectOutputStream2;
                                OSSLog.logThrowable2Local(e);
                                if (objectOutputStream != null) {
                                    objectOutputStream.close();
                                }
                                super.checkException();
                            } catch (Throwable th) {
                                th = th;
                                objectOutputStream = objectOutputStream2;
                                if (objectOutputStream != null) {
                                    objectOutputStream.close();
                                }
                                throw th;
                            }
                        } catch (IOException e3) {
                            e = e3;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
            }
        }
        super.checkException();
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x017c A[Catch: ClientException -> 0x01e7, ServiceException -> 0x01e9, TryCatch #6 {ClientException -> 0x01e7, ServiceException -> 0x01e9, blocks: (B:47:0x0163, B:48:0x0176, B:50:0x017c, B:52:0x0198, B:54:0x019e, B:56:0x01ac, B:57:0x01c1, B:59:0x01de), top: B:92:0x0163 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0237  */
    @Override // com.alibaba.sdk.android.oss.internal.BaseMultipartUploadTask
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void initMultipartUploadId() throws java.util.concurrent.ExecutionException, com.alibaba.sdk.android.oss.ServiceException, java.lang.InterruptedException, com.alibaba.sdk.android.oss.ClientException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 651
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.oss.internal.SequenceUploadTask.initMultipartUploadId():void");
    }

    @Override // com.alibaba.sdk.android.oss.internal.BaseMultipartUploadTask
    public void processException(Exception exc) {
        if (this.mUploadException == null || !exc.getMessage().equals(this.mUploadException.getMessage())) {
            this.mUploadException = exc;
        }
        OSSLog.logThrowable2Local(exc);
        if (!this.mContext.getCancellationHandler().isCancelled() || this.mIsCancel) {
            return;
        }
        this.mIsCancel = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x01b8 A[Catch: IOException -> 0x01af, TRY_LEAVE, TryCatch #10 {IOException -> 0x01af, blocks: (B:94:0x01ab, B:98:0x01b3, B:100:0x01b8), top: B:110:0x01ab }] */
    /* JADX WARN: Removed duplicated region for block: B:110:0x01ab A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:121:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:123:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00b9 A[Catch: ServiceException -> 0x010b, Exception -> 0x010e, all -> 0x01a7, TryCatch #3 {Exception -> 0x010e, blocks: (B:29:0x0070, B:30:0x0089, B:32:0x00b9, B:33:0x00c4, B:35:0x00dd, B:43:0x00f8, B:44:0x010a), top: B:104:0x0070 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00dd A[Catch: ServiceException -> 0x010b, Exception -> 0x010e, all -> 0x01a7, TRY_LEAVE, TryCatch #3 {Exception -> 0x010e, blocks: (B:29:0x0070, B:30:0x0089, B:32:0x00b9, B:33:0x00c4, B:35:0x00dd, B:43:0x00f8, B:44:0x010a), top: B:104:0x0070 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00f8 A[Catch: ServiceException -> 0x010b, Exception -> 0x010e, all -> 0x01a7, TRY_ENTER, TryCatch #3 {Exception -> 0x010e, blocks: (B:29:0x0070, B:30:0x0089, B:32:0x00b9, B:33:0x00c4, B:35:0x00dd, B:43:0x00f8, B:44:0x010a), top: B:104:0x0070 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x012d A[Catch: IOException -> 0x0131, TRY_ENTER, TryCatch #8 {IOException -> 0x0131, blocks: (B:37:0x00e9, B:39:0x00ee, B:41:0x00f3, B:64:0x012d, B:68:0x0135, B:70:0x013a, B:85:0x0199, B:87:0x019e, B:89:0x01a3), top: B:109:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0135 A[Catch: IOException -> 0x0131, TryCatch #8 {IOException -> 0x0131, blocks: (B:37:0x00e9, B:39:0x00ee, B:41:0x00f3, B:64:0x012d, B:68:0x0135, B:70:0x013a, B:85:0x0199, B:87:0x019e, B:89:0x01a3), top: B:109:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x013a A[Catch: IOException -> 0x0131, TRY_LEAVE, TryCatch #8 {IOException -> 0x0131, blocks: (B:37:0x00e9, B:39:0x00ee, B:41:0x00f3, B:64:0x012d, B:68:0x0135, B:70:0x013a, B:85:0x0199, B:87:0x019e, B:89:0x01a3), top: B:109:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0150 A[Catch: all -> 0x01a7, TryCatch #7 {all -> 0x01a7, blocks: (B:29:0x0070, B:30:0x0089, B:32:0x00b9, B:33:0x00c4, B:35:0x00dd, B:43:0x00f8, B:44:0x010a, B:77:0x0148, B:79:0x0150, B:80:0x0154, B:82:0x016e, B:83:0x018c), top: B:104:0x0070 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0154 A[Catch: all -> 0x01a7, TryCatch #7 {all -> 0x01a7, blocks: (B:29:0x0070, B:30:0x0089, B:32:0x00b9, B:33:0x00c4, B:35:0x00dd, B:43:0x00f8, B:44:0x010a, B:77:0x0148, B:79:0x0150, B:80:0x0154, B:82:0x016e, B:83:0x018c), top: B:104:0x0070 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0199 A[Catch: IOException -> 0x0131, TRY_ENTER, TryCatch #8 {IOException -> 0x0131, blocks: (B:37:0x00e9, B:39:0x00ee, B:41:0x00f3, B:64:0x012d, B:68:0x0135, B:70:0x013a, B:85:0x0199, B:87:0x019e, B:89:0x01a3), top: B:109:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x019e A[Catch: IOException -> 0x0131, TryCatch #8 {IOException -> 0x0131, blocks: (B:37:0x00e9, B:39:0x00ee, B:41:0x00f3, B:64:0x012d, B:68:0x0135, B:70:0x013a, B:85:0x0199, B:87:0x019e, B:89:0x01a3), top: B:109:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01a3 A[Catch: IOException -> 0x0131, TRY_LEAVE, TryCatch #8 {IOException -> 0x0131, blocks: (B:37:0x00e9, B:39:0x00ee, B:41:0x00f3, B:64:0x012d, B:68:0x0135, B:70:0x013a, B:85:0x0199, B:87:0x019e, B:89:0x01a3), top: B:109:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01b3 A[Catch: IOException -> 0x01af, TryCatch #10 {IOException -> 0x01af, blocks: (B:94:0x01ab, B:98:0x01b3, B:100:0x01b8), top: B:110:0x01ab }] */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v18 */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r0v21 */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.io.RandomAccessFile] */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.io.RandomAccessFile] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.alibaba.sdk.android.oss.model.UploadPartRequest] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r12v0, types: [com.alibaba.sdk.android.oss.internal.BaseMultipartUploadTask, com.alibaba.sdk.android.oss.internal.SequenceUploadTask] */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.io.RandomAccessFile] */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.io.RandomAccessFile] */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8, types: [java.io.RandomAccessFile] */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14, types: [java.io.BufferedInputStream] */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v17 */
    /* JADX WARN: Type inference failed for: r5v18 */
    /* JADX WARN: Type inference failed for: r5v19 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v20 */
    /* JADX WARN: Type inference failed for: r5v21, types: [java.io.BufferedInputStream] */
    /* JADX WARN: Type inference failed for: r5v22 */
    /* JADX WARN: Type inference failed for: r5v23 */
    /* JADX WARN: Type inference failed for: r5v24 */
    /* JADX WARN: Type inference failed for: r5v3, types: [java.io.BufferedInputStream] */
    /* JADX WARN: Type inference failed for: r5v4, types: [java.io.BufferedInputStream] */
    /* JADX WARN: Type inference failed for: r5v5, types: [java.io.BufferedInputStream] */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9 */
    @Override // com.alibaba.sdk.android.oss.internal.BaseMultipartUploadTask
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void uploadPart(int r13, int r14, int r15) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 448
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.oss.internal.SequenceUploadTask.uploadPart(int, int, int):void");
    }

    @Override // com.alibaba.sdk.android.oss.internal.BaseMultipartUploadTask
    public void uploadPartFinish(PartETag partETag) throws Exception {
        if (!this.mContext.getCancellationHandler().isCancelled() || this.mSp.contains(this.mUploadId)) {
            return;
        }
        this.mSp.setStringValue(this.mUploadId, String.valueOf(this.mUploadedLength));
        onProgressCallback(this.mRequest, this.mUploadedLength, this.mFileLength);
    }

    @Override // com.alibaba.sdk.android.oss.internal.BaseMultipartUploadTask
    public ResumableUploadResult doMultipartUpload() throws Throwable {
        long j2 = this.mUploadedLength;
        checkCancel();
        int[] iArr = this.mPartAttr;
        int i2 = iArr[0];
        int i3 = iArr[1];
        if (this.mPartETags.size() > 0 && this.mAlreadyUploadIndex.size() > 0) {
            long jLongValue = this.mUploadedLength;
            if (jLongValue > this.mFileLength) {
                throw new ClientException("The uploading file is inconsistent with before");
            }
            if (this.mFirstPartSize != i2) {
                throw new ClientException("The part size setting is inconsistent with before");
            }
            if (!TextUtils.isEmpty(this.mSp.getStringValue(this.mUploadId))) {
                jLongValue = Long.valueOf(this.mSp.getStringValue(this.mUploadId)).longValue();
            }
            long j3 = jLongValue;
            OSSProgressCallback<Request> oSSProgressCallback = this.mProgressCallback;
            if (oSSProgressCallback != 0) {
                oSSProgressCallback.onProgress(this.mRequest, j3, this.mFileLength);
            }
            this.mSp.removeKey(this.mUploadId);
        }
        for (int i4 = 0; i4 < i3; i4++) {
            if (this.mAlreadyUploadIndex.size() == 0 || !this.mAlreadyUploadIndex.contains(Integer.valueOf(i4 + 1))) {
                if (i4 == i3 - 1) {
                    i2 = (int) (this.mFileLength - j2);
                }
                OSSLog.logDebug("upload part readByte : " + i2);
                j2 += (long) i2;
                uploadPart(i4, i2, i3);
                if (this.mUploadException != null) {
                    break;
                }
            }
        }
        checkException();
        CompleteMultipartUploadResult completeMultipartUploadResult = completeMultipartUploadResult();
        ResumableUploadResult resumableUploadResult = completeMultipartUploadResult != null ? new ResumableUploadResult(completeMultipartUploadResult) : null;
        File file = this.mRecordFile;
        if (file != null) {
            file.delete();
        }
        File file2 = this.mCRC64RecordFile;
        if (file2 != null) {
            file2.delete();
        }
        return resumableUploadResult;
    }
}

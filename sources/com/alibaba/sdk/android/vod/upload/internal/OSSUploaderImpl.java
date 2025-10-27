package com.alibaba.sdk.android.vod.upload.internal;

import android.content.Context;
import android.graphics.Bitmap;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.utils.IOUtils;
import com.alibaba.sdk.android.oss.model.AbortMultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.InitiateMultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.InitiateMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.OSSResult;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PartETag;
import com.alibaba.sdk.android.oss.model.UploadPartRequest;
import com.alibaba.sdk.android.oss.model.UploadPartResult;
import com.alibaba.sdk.android.vod.upload.VODUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.common.UploadStateType;
import com.alibaba.sdk.android.vod.upload.common.utils.StringUtil;
import com.alibaba.sdk.android.vod.upload.exception.VODErrorCode;
import com.alibaba.sdk.android.vod.upload.model.OSSConfig;
import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.aliyun.vod.common.httpfinal.QupaiHttpFinal;
import com.aliyun.vod.common.utils.FileUtils;
import com.aliyun.vod.log.core.AliyunLogger;
import com.aliyun.vod.log.core.AliyunLoggerManager;
import com.aliyun.vod.log.core.LogService;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.google.android.exoplayer2.C;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.net.ssl.SSLHandshakeException;

/* loaded from: classes2.dex */
public class OSSUploaderImpl implements OSSUploader {
    private static final int LARGE_BLOCK_SIZE = 524288;
    private static final int RETRY_INTERVAL = 3000;
    private static final int SMALL_BLOCK_SIZE = 262144;
    private Integer blockSize;
    private ClientConfiguration clientConfig;
    private OSSCompletedCallback<CompleteMultipartUploadRequest, CompleteMultipartUploadResult> completedCallback;
    private OSSConfig config;
    private Context context;
    private Integer currentUploadLength;
    private OSSCompletedCallback<InitiateMultipartUploadRequest, InitiateMultipartUploadResult> initCallback;
    private InputStream inputStream;
    private Integer lastUploadedBlockIndex;
    private OSSUploadListener listener;
    private long mFileLength;
    private String mFileName;
    private OSS oss;
    private OSSRequest ossRequest;
    private OSSCompletedCallback<UploadPartRequest, UploadPartResult> partCallback;
    private boolean retryShouldNotify;
    private UploadFileInfo uploadFileInfo;
    private String uploadId;
    private List<PartETag> uploadedParts = new ArrayList();
    private Long uploadedSize;

    /* renamed from: com.alibaba.sdk.android.vod.upload.internal.OSSUploaderImpl$9, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass9 {
        static final /* synthetic */ int[] $SwitchMap$com$alibaba$sdk$android$vod$upload$internal$OSSUploadRetryType;

        static {
            int[] iArr = new int[OSSUploadRetryType.values().length];
            $SwitchMap$com$alibaba$sdk$android$vod$upload$internal$OSSUploadRetryType = iArr;
            try {
                iArr[OSSUploadRetryType.ShouldRetry.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$alibaba$sdk$android$vod$upload$internal$OSSUploadRetryType[OSSUploadRetryType.ShouldGetSTS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$alibaba$sdk$android$vod$upload$internal$OSSUploadRetryType[OSSUploadRetryType.ShouldNotRetry.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public class OSSCompletedCallbackImpl implements OSSCompletedCallback {
        public OSSCompletedCallbackImpl() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
        public void onFailure(OSSRequest oSSRequest, ClientException clientException, ServiceException serviceException) throws InterruptedException {
            UploadStateType status = OSSUploaderImpl.this.uploadFileInfo.getStatus();
            ClientException clientException2 = clientException != null ? clientException : serviceException != 0 ? serviceException : null;
            if (clientException2 == null) {
                OSSLog.logError("onFailure error: exception is null.");
                return;
            }
            if (UploadStateType.CANCELED.equals(status)) {
                OSSLog.logError("onFailure error: upload has been canceled, ignore notify.");
                OSSUploaderImpl.this.uploadCancelLogger();
                return;
            }
            int i2 = AnonymousClass9.$SwitchMap$com$alibaba$sdk$android$vod$upload$internal$OSSUploadRetryType[OSSUploaderImpl.this.shouldRetry(clientException2).ordinal()];
            if (i2 != 1) {
                if (i2 == 2) {
                    OSSUploaderImpl.this.uploadFileInfo.setStatus(UploadStateType.PAUSED);
                    OSSUploaderImpl.this.listener.onUploadTokenExpired();
                    OSSUploaderImpl.this.uploadFailedLogger(VODErrorCode.UPLOAD_EXPIRED, "Upload Token Expired");
                    return;
                }
                if (i2 != 3) {
                    return;
                }
                OSSUploaderImpl.this.uploadFileInfo.setStatus(UploadStateType.FAIlURE);
                if (clientException != null) {
                    OSSUploaderImpl.this.listener.onUploadFailed(UploaderErrorCode.CLIENT_EXCEPTION, clientException.toString());
                    if (oSSRequest instanceof UploadPartRequest) {
                        OSSUploaderImpl.this.uploadPartFailedLogger(UploaderErrorCode.CLIENT_EXCEPTION, clientException.getMessage().toString());
                        return;
                    } else {
                        OSSUploaderImpl.this.uploadFailedLogger(UploaderErrorCode.CLIENT_EXCEPTION, clientException.getMessage().toString());
                        return;
                    }
                }
                if (serviceException != 0) {
                    OSSUploaderImpl.this.listener.onUploadFailed(serviceException.getErrorCode(), serviceException.getMessage());
                    if (oSSRequest instanceof UploadPartRequest) {
                        OSSUploaderImpl.this.uploadPartFailedLogger(serviceException.getErrorCode(), serviceException.getMessage());
                        return;
                    } else {
                        OSSUploaderImpl.this.uploadFailedLogger(serviceException.getErrorCode(), serviceException.getMessage());
                        return;
                    }
                }
                return;
            }
            if (UploadStateType.PAUSING.equals(status)) {
                OSSLog.logDebug("[OSSUploader] - This task is pausing!");
                OSSUploaderImpl.this.uploadFileInfo.setStatus(UploadStateType.PAUSED);
                return;
            }
            try {
                Thread.sleep(C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            if (oSSRequest instanceof InitiateMultipartUploadRequest) {
                OSSUploaderImpl.this.oss.asyncInitMultipartUpload((InitiateMultipartUploadRequest) OSSUploaderImpl.this.ossRequest, OSSUploaderImpl.this.initCallback);
            } else if (oSSRequest instanceof CompleteMultipartUploadRequest) {
                OSSUploaderImpl.this.oss.asyncCompleteMultipartUpload((CompleteMultipartUploadRequest) OSSUploaderImpl.this.ossRequest, OSSUploaderImpl.this.completedCallback);
            } else if (oSSRequest instanceof UploadPartRequest) {
                OSSUploaderImpl.this.oss.asyncUploadPart((UploadPartRequest) OSSUploaderImpl.this.ossRequest, OSSUploaderImpl.this.partCallback);
            }
            if (OSSUploaderImpl.this.retryShouldNotify) {
                if (clientException != null) {
                    OSSUploaderImpl.this.listener.onUploadRetry(UploaderErrorCode.CLIENT_EXCEPTION, clientException.toString());
                    if (oSSRequest instanceof UploadPartRequest) {
                        OSSUploaderImpl.this.uploadPartFailedLogger(UploaderErrorCode.CLIENT_EXCEPTION, clientException.getMessage().toString());
                    } else {
                        OSSUploaderImpl.this.uploadFailedLogger(UploaderErrorCode.CLIENT_EXCEPTION, clientException.getMessage().toString());
                    }
                } else if (serviceException != 0) {
                    OSSUploaderImpl.this.listener.onUploadRetry(serviceException.getErrorCode(), serviceException.getMessage());
                    if (oSSRequest instanceof UploadPartRequest) {
                        OSSUploaderImpl.this.uploadPartFailedLogger(serviceException.getErrorCode(), serviceException.getMessage());
                    } else {
                        OSSUploaderImpl.this.uploadFailedLogger(serviceException.getErrorCode(), serviceException.getMessage());
                    }
                }
                OSSUploaderImpl.this.retryShouldNotify = false;
            }
        }

        @Override // com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
        public void onSuccess(OSSRequest oSSRequest, OSSResult oSSResult) throws IOException {
            UploadStateType status = OSSUploaderImpl.this.uploadFileInfo.getStatus();
            UploadStateType uploadStateType = UploadStateType.CANCELED;
            if (uploadStateType.equals(status)) {
                OSSLog.logError("onSuccess: upload has been canceled, ignore notify.");
                OSSUploaderImpl.this.uploadCancelLogger();
                return;
            }
            if (!OSSUploaderImpl.this.retryShouldNotify) {
                OSSUploaderImpl.this.listener.onUploadRetryResume();
                OSSUploaderImpl.this.retryShouldNotify = true;
            }
            if (oSSResult instanceof InitiateMultipartUploadResult) {
                OSSUploaderImpl.this.uploadId = ((InitiateMultipartUploadResult) oSSResult).getUploadId();
                OSSLog.logDebug("[OSSUploader] - InitiateMultipartUploadResult uploadId:" + OSSUploaderImpl.this.uploadId);
                OSSUploaderImpl.this.uploadedSize = 0L;
                OSSUploaderImpl.this.uploadPart();
                return;
            }
            if (!(oSSResult instanceof UploadPartResult)) {
                if (oSSResult instanceof CompleteMultipartUploadResult) {
                    OSSLog.logDebug("[OSSUploader] - CompleteMultipartUploadResult onSuccess ------------------");
                    try {
                        OSSUploaderImpl.this.inputStream.close();
                    } catch (IOException unused) {
                        OSSLog.logError("CompleteMultipartUploadResult inputStream close failed.");
                    }
                    OSSUploaderImpl.this.uploadFileInfo.setStatus(UploadStateType.SUCCESS);
                    OSSUploaderImpl.this.listener.onUploadSucceed();
                    OSSUploaderImpl.this.uploadSuccessedLogger();
                    return;
                }
                return;
            }
            OSSLog.logDebug("[OSSUploader] - UploadPartResult onSuccess ------------------" + ((UploadPartRequest) oSSRequest).getPartNumber());
            OSSUploaderImpl.this.uploadedParts.add(new PartETag(OSSUploaderImpl.this.lastUploadedBlockIndex.intValue() + 1, ((UploadPartResult) oSSResult).getETag()));
            OSSUploaderImpl oSSUploaderImpl = OSSUploaderImpl.this;
            oSSUploaderImpl.uploadedSize = Long.valueOf(oSSUploaderImpl.uploadedSize.longValue() + ((long) OSSUploaderImpl.this.currentUploadLength.intValue()));
            Integer unused2 = OSSUploaderImpl.this.lastUploadedBlockIndex;
            OSSUploaderImpl oSSUploaderImpl2 = OSSUploaderImpl.this;
            oSSUploaderImpl2.lastUploadedBlockIndex = Integer.valueOf(oSSUploaderImpl2.lastUploadedBlockIndex.intValue() + 1);
            OSSUploaderImpl.this.uploadPartCompletedLogger();
            if (uploadStateType.equals(status)) {
                OSSUploaderImpl.this.abortUpload();
                OSSUploaderImpl.this.listener.onUploadFailed(uploadStateType.toString(), "This task is cancelled!");
                OSSLog.logDebug("[OSSUploader] - This task is cancelled!");
                OSSUploaderImpl.this.uploadPartFailedLogger(uploadStateType.toString(), "This task is user cancelled!");
                return;
            }
            if (UploadStateType.UPLOADING.equals(status)) {
                if (OSSUploaderImpl.this.uploadedSize.longValue() < OSSUploaderImpl.this.mFileLength) {
                    OSSUploaderImpl.this.uploadPart();
                    return;
                } else {
                    OSSUploaderImpl.this.completeMultiPartUpload();
                    return;
                }
            }
            if (UploadStateType.PAUSING.equals(status)) {
                OSSLog.logDebug("[OSSUploader] - This task is pausing!");
                OSSUploaderImpl.this.uploadFileInfo.setStatus(UploadStateType.PAUSED);
            }
        }
    }

    public OSSUploaderImpl(Context context) {
        this.context = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void abortUpload() throws IOException {
        if (this.uploadId != null) {
            try {
                this.oss.abortMultipartUpload(new AbortMultipartUploadRequest(this.uploadFileInfo.getBucket(), this.uploadFileInfo.getObject(), this.uploadId));
                this.inputStream.close();
            } catch (ClientException e2) {
                OSSLog.logWarn("[OSSUploader] - abort ClientException!code:" + e2.getCause() + ", message:" + e2.getMessage());
            } catch (ServiceException e3) {
                OSSLog.logWarn("[OSSUploader] - abort ServiceException!code:" + e3.getCause() + ", message:" + e3.getMessage());
            } catch (IOException e4) {
                OSSLog.logWarn("[OSSUploader] - abort IOException!code:" + e4.getCause() + ", message:" + e4.getMessage());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void completeMultiPartUpload() {
        CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(this.uploadFileInfo.getBucket(), this.uploadFileInfo.getObject(), this.uploadId, this.uploadedParts);
        ObjectMetadata metadata = completeMultipartUploadRequest.getMetadata();
        if (metadata == null) {
            metadata = new ObjectMetadata();
        }
        if (this.uploadFileInfo.getVodInfo() != null) {
            metadata.addUserMetadata("x-oss-notification", this.uploadFileInfo.getVodInfo().toVodJsonStringWithBase64());
        }
        completeMultipartUploadRequest.setMetadata(metadata);
        this.ossRequest = completeMultipartUploadRequest;
        this.oss.asyncCompleteMultipartUpload(completeMultipartUploadRequest, this.completedCallback);
    }

    private void initMultiPartUpload() {
        startUploadLogger(this.uploadFileInfo);
        InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(this.uploadFileInfo.getBucket(), this.uploadFileInfo.getObject());
        this.ossRequest = initiateMultipartUploadRequest;
        this.oss.asyncInitMultipartUpload(initiateMultipartUploadRequest, this.initCallback);
    }

    private void startUploadLogger(final UploadFileInfo uploadFileInfo) {
        final AliyunLogger logger = AliyunLoggerManager.getLogger(VODUploadClientImpl.class.getName());
        if (logger != null) {
            logger.updateRequestID();
            LogService logService = logger.getLogService();
            if (logService != null) {
                logService.execute(new Runnable() { // from class: com.alibaba.sdk.android.vod.upload.internal.OSSUploaderImpl.2
                    @Override // java.lang.Runnable
                    public void run() throws IOException {
                        Bitmap videoSize = FileUtils.getVideoSize(uploadFileInfo.getFilePath());
                        HashMap map = new HashMap();
                        map.put("ft", FileUtils.getMimeType(uploadFileInfo.getFilePath()));
                        map.put(AliyunLogKey.KEY_FILE_SIZE, String.valueOf(OSSUploaderImpl.this.mFileLength));
                        map.put(AliyunLogKey.KEY_FILE_WIDTH, videoSize == null ? "" : String.valueOf(videoSize.getWidth()));
                        map.put(AliyunLogKey.KEY_FILE_HEIGHT, videoSize != null ? String.valueOf(videoSize.getHeight()) : "");
                        map.put("fm", FileUtils.getMd5OfFile(uploadFileInfo.getFilePath()));
                        map.put(AliyunLogKey.KEY_PART_SIZE, String.valueOf(OSSUploaderImpl.this.blockSize));
                        map.put(AliyunLogKey.KEY_BUCKET, uploadFileInfo.getBucket());
                        map.put(AliyunLogKey.KEY_OBJECT_KEY, uploadFileInfo.getObject());
                        logger.pushLog(map, "upload", "debug", "upload", "upload", 20002, "upload", null);
                    }
                });
            }
        }
    }

    private void startUploadPartLogger() {
        LogService logService;
        final AliyunLogger logger = AliyunLoggerManager.getLogger(VODUploadClientImpl.class.getName());
        if (logger == null || (logService = logger.getLogService()) == null) {
            return;
        }
        logService.execute(new Runnable() { // from class: com.alibaba.sdk.android.vod.upload.internal.OSSUploaderImpl.3
            @Override // java.lang.Runnable
            public void run() {
                HashMap map = new HashMap();
                map.put("ui", OSSUploaderImpl.this.uploadId);
                map.put("pn", String.valueOf(OSSUploaderImpl.this.lastUploadedBlockIndex));
                map.put("pr", OSSUploaderImpl.this.retryShouldNotify ? "0" : "1");
                logger.pushLog(map, "upload", "debug", "upload", "upload", 20005, "upload", null);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadCancelLogger() {
        LogService logService;
        final AliyunLogger logger = AliyunLoggerManager.getLogger(VODUploadClientImpl.class.getName());
        if (logger == null || (logService = logger.getLogService()) == null) {
            return;
        }
        logService.execute(new Runnable() { // from class: com.alibaba.sdk.android.vod.upload.internal.OSSUploaderImpl.6
            @Override // java.lang.Runnable
            public void run() {
                logger.pushLog(null, "upload", "debug", "upload", "upload", 20008, "upload", null);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadFailedLogger(final String str, final String str2) {
        LogService logService;
        final AliyunLogger logger = AliyunLoggerManager.getLogger(VODUploadClientImpl.class.getName());
        if (logger == null || (logService = logger.getLogService()) == null) {
            return;
        }
        logService.execute(new Runnable() { // from class: com.alibaba.sdk.android.vod.upload.internal.OSSUploaderImpl.7
            @Override // java.lang.Runnable
            public void run() {
                HashMap map = new HashMap();
                map.put(AliyunLogKey.KEY_UPLOAD_PART_FAILED_CODE, str);
                map.put(AliyunLogKey.KEY_UPLOAD_PART_FAILED_MESSAGE, str2);
                logger.pushLog(map, "upload", "debug", "upload", "upload", 20004, "upload", null);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadPart() {
        this.ossRequest = new UploadPartRequest(this.uploadFileInfo.getBucket(), this.uploadFileInfo.getObject(), this.uploadId, this.lastUploadedBlockIndex.intValue() + 1);
        this.currentUploadLength = Integer.valueOf((int) Math.min(this.blockSize.intValue(), this.mFileLength - this.uploadedSize.longValue()));
        OSSLog.logDebug("[OSSUploader] - filesize:" + this.mFileLength + ", blocksize: " + this.currentUploadLength);
        try {
            ((UploadPartRequest) this.ossRequest).setPartContent(IOUtils.readStreamAsBytesArray(this.inputStream, this.currentUploadLength.intValue()));
            ((UploadPartRequest) this.ossRequest).setProgressCallback(new OSSProgressCallback<UploadPartRequest>() { // from class: com.alibaba.sdk.android.vod.upload.internal.OSSUploaderImpl.1
                @Override // com.alibaba.sdk.android.oss.callback.OSSProgressCallback
                public void onProgress(UploadPartRequest uploadPartRequest, long j2, long j3) {
                    OSSUploaderImpl.this.listener.onUploadProgress(uploadPartRequest, OSSUploaderImpl.this.uploadedSize.longValue() + j2, OSSUploaderImpl.this.mFileLength);
                }
            });
            this.oss.asyncUploadPart((UploadPartRequest) this.ossRequest, this.partCallback);
            startUploadPartLogger();
        } catch (IOException unused) {
            OSSLog.logError("[OSSUploader] - read content from file failed!name:" + this.mFileName + ", offset:" + this.uploadedSize + ", length:" + this.currentUploadLength);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadPartCompletedLogger() {
        LogService logService;
        final AliyunLogger logger = AliyunLoggerManager.getLogger(VODUploadClientImpl.class.getName());
        if (logger == null || (logService = logger.getLogService()) == null) {
            return;
        }
        logService.execute(new Runnable() { // from class: com.alibaba.sdk.android.vod.upload.internal.OSSUploaderImpl.4
            @Override // java.lang.Runnable
            public void run() {
                logger.pushLog(null, "upload", "debug", "upload", "upload", 20007, "upload", null);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadPartFailedLogger(final String str, final String str2) {
        LogService logService;
        final AliyunLogger logger = AliyunLoggerManager.getLogger(VODUploadClientImpl.class.getName());
        if (logger == null || (logService = logger.getLogService()) == null) {
            return;
        }
        logService.execute(new Runnable() { // from class: com.alibaba.sdk.android.vod.upload.internal.OSSUploaderImpl.8
            @Override // java.lang.Runnable
            public void run() {
                HashMap map = new HashMap();
                map.put(AliyunLogKey.KEY_UPLOAD_PART_FAILED_CODE, str);
                map.put(AliyunLogKey.KEY_UPLOAD_PART_FAILED_MESSAGE, str2);
                logger.pushLog(map, "upload", "debug", "upload", "upload", 20006, "upload", null);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadSuccessedLogger() {
        LogService logService;
        final AliyunLogger logger = AliyunLoggerManager.getLogger(VODUploadClientImpl.class.getName());
        if (logger == null || (logService = logger.getLogService()) == null) {
            return;
        }
        logService.execute(new Runnable() { // from class: com.alibaba.sdk.android.vod.upload.internal.OSSUploaderImpl.5
            @Override // java.lang.Runnable
            public void run() {
                logger.pushLog(null, "upload", "debug", "upload", "upload", 20003, "upload", null);
            }
        });
    }

    @Override // com.alibaba.sdk.android.vod.upload.internal.OSSUploader
    public void cancel() {
        UploadFileInfo uploadFileInfo = this.uploadFileInfo;
        if (uploadFileInfo == null) {
            return;
        }
        UploadStateType status = uploadFileInfo.getStatus();
        if (UploadStateType.INIT.equals(status) || UploadStateType.UPLOADING.equals(status) || UploadStateType.PAUSED.equals(status) || UploadStateType.PAUSING.equals(status)) {
            OSSLog.logDebug("[OSSUploader] - cancel...");
            this.uploadFileInfo.setStatus(UploadStateType.CANCELED);
            return;
        }
        OSSLog.logDebug("[OSSUploader] - status: " + status + " cann't be cancel!");
    }

    @Override // com.alibaba.sdk.android.vod.upload.internal.OSSUploader
    public void init(OSSConfig oSSConfig, OSSUploadListener oSSUploadListener) throws NoSuchAlgorithmException, KeyManagementException {
        OSSLog.logDebug("[OSSUploader] - init...");
        this.config = oSSConfig;
        this.listener = oSSUploadListener;
        QupaiHttpFinal.getInstance().initOkHttpFinal();
        this.initCallback = new OSSCompletedCallbackImpl();
        this.partCallback = new OSSCompletedCallbackImpl();
        this.completedCallback = new OSSCompletedCallbackImpl();
    }

    @Override // com.alibaba.sdk.android.vod.upload.internal.OSSUploader
    public void pause() {
        UploadStateType status = this.uploadFileInfo.getStatus();
        if (UploadStateType.UPLOADING.equals(status)) {
            OSSLog.logDebug("[OSSUploader] - pause...");
            this.uploadFileInfo.setStatus(UploadStateType.PAUSING);
            return;
        }
        OSSLog.logDebug("[OSSUploader] - status: " + status + " cann't be pause!");
    }

    @Override // com.alibaba.sdk.android.vod.upload.internal.OSSUploader
    public void resume() {
        UploadStateType status = this.uploadFileInfo.getStatus();
        UploadStateType uploadStateType = UploadStateType.PAUSING;
        if (!uploadStateType.equals(status) && !UploadStateType.PAUSED.equals(status)) {
            OSSLog.logDebug("[OSSUploader] - status: " + status + " cann't be resume!");
            return;
        }
        OSSLog.logDebug("[OSSUploader] - resume...");
        if (uploadStateType.equals(status)) {
            this.uploadFileInfo.setStatus(UploadStateType.UPLOADING);
            return;
        }
        if (UploadStateType.PAUSED.equals(status)) {
            this.uploadFileInfo.setStatus(UploadStateType.UPLOADING);
            if (this.uploadedSize.longValue() == -1) {
                initMultiPartUpload();
            } else if (this.uploadedSize.longValue() < this.mFileLength) {
                uploadPart();
            } else {
                completeMultiPartUpload();
            }
        }
    }

    @Override // com.alibaba.sdk.android.vod.upload.internal.OSSUploader
    public void setOSSClientConfiguration(ClientConfiguration clientConfiguration) {
        ClientConfiguration clientConfiguration2 = new ClientConfiguration();
        this.clientConfig = clientConfiguration2;
        if (clientConfiguration == null) {
            clientConfiguration2.setMaxErrorRetry(Integer.MAX_VALUE);
            this.clientConfig.setSocketTimeout(ClientConfiguration.getDefaultConf().getSocketTimeout());
            this.clientConfig.setConnectionTimeout(ClientConfiguration.getDefaultConf().getSocketTimeout());
        } else {
            clientConfiguration2.setMaxErrorRetry(clientConfiguration.getMaxErrorRetry());
            this.clientConfig.setSocketTimeout(clientConfiguration.getSocketTimeout());
            this.clientConfig.setConnectionTimeout(clientConfiguration.getConnectionTimeout());
        }
    }

    @Override // com.alibaba.sdk.android.vod.upload.internal.OSSUploader
    public void setRecordUploadProgressEnabled(boolean z2) {
    }

    public OSSUploadRetryType shouldRetry(Exception exc) {
        if (!(exc instanceof ClientException)) {
            if (!(exc instanceof ServiceException)) {
                return OSSUploadRetryType.ShouldNotRetry;
            }
            ServiceException serviceException = (ServiceException) exc;
            return (serviceException.getErrorCode() == null || !serviceException.getErrorCode().equalsIgnoreCase("RequestTimeTooSkewed")) ? serviceException.getStatusCode() >= 500 ? OSSUploadRetryType.ShouldRetry : (serviceException.getStatusCode() != 403 || StringUtil.isEmpty(this.config.getSecrityToken())) ? OSSUploadRetryType.ShouldNotRetry : OSSUploadRetryType.ShouldGetSTS : OSSUploadRetryType.ShouldRetry;
        }
        Exception exc2 = (Exception) exc.getCause();
        if ((exc2 instanceof InterruptedIOException) && !(exc2 instanceof SocketTimeoutException)) {
            OSSLog.logError("[shouldNotetry] - is interrupted!");
            return OSSUploadRetryType.ShouldNotRetry;
        }
        if (exc2 instanceof IllegalArgumentException) {
            return OSSUploadRetryType.ShouldNotRetry;
        }
        if (exc2 instanceof SocketTimeoutException) {
            return OSSUploadRetryType.ShouldNotRetry;
        }
        if (exc2 instanceof SSLHandshakeException) {
            return OSSUploadRetryType.ShouldNotRetry;
        }
        OSSLog.logDebug("shouldRetry - " + exc.toString());
        exc.getCause().printStackTrace();
        return OSSUploadRetryType.ShouldRetry;
    }

    @Override // com.alibaba.sdk.android.vod.upload.internal.OSSUploader
    public void start(UploadFileInfo uploadFileInfo) throws FileNotFoundException {
        UploadFileInfo uploadFileInfo2 = this.uploadFileInfo;
        if (uploadFileInfo2 != null && !uploadFileInfo.equals(uploadFileInfo2)) {
            uploadFileInfo.setStatus(UploadStateType.INIT);
        }
        OSSLog.logDebug("[OSSUploader] - start..." + uploadFileInfo.getFilePath());
        this.uploadFileInfo = uploadFileInfo;
        this.oss = new OSSClient(this.context, uploadFileInfo.getEndpoint(), this.config.getProvider(), this.clientConfig);
        this.mFileLength = FileUtils.getFileLength(this.context, uploadFileInfo.getFilePath());
        this.mFileName = FileUtils.getFileName(this.context, uploadFileInfo.getFilePath());
        if (this.mFileLength < 134217728) {
            this.blockSize = 262144;
        } else {
            this.blockSize = 524288;
        }
        this.inputStream = FileUtils.getFileInputStream(this.context, uploadFileInfo.getFilePath());
        this.uploadedSize = -1L;
        this.lastUploadedBlockIndex = 0;
        this.uploadedParts.clear();
        this.ossRequest = null;
        this.retryShouldNotify = true;
        initMultiPartUpload();
        uploadFileInfo.setStatus(UploadStateType.UPLOADING);
    }
}

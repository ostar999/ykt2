package com.plv.beauty.api.resource;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import com.plv.beauty.api.resource.BaseResource;
import com.plv.beauty.api.resource.database.DatabaseManager;
import com.plv.beauty.api.resource.file.FileUtils;
import com.plv.beauty.api.resource.network.NetworkManager;
import com.tencent.open.utils.HttpUtils;
import java.io.File;
import java.util.Map;
import java.util.Objects;
import okhttp3.Call;

/* loaded from: classes4.dex */
public class RemoteResource extends BaseResource implements NetworkManager.DownloadCallback {
    private Call call;
    private DownloadContext context;
    private Map<String, String> downloadParams;
    private String hash;
    private boolean isCanceled;
    private String url;
    private RemoteResourceState state = RemoteResourceState.UNKNOWN;
    private float downloadProgress = 0.0f;
    private int retryCount = 0;
    private NetworkManager.Method downloadMethod = NetworkManager.Method.GET;
    private NetworkManager.ContentType contentType = NetworkManager.ContentType.URL_ENCODED;
    private DownloadDirectory downloadDirectory = DownloadDirectory.DOCUMENT;
    private boolean needUnzip = true;
    private boolean needCache = true;
    private boolean useCache = true;
    private boolean needCheckHash = true;

    /* renamed from: com.plv.beauty.api.resource.RemoteResource$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$plv$beauty$api$resource$RemoteResource$DownloadDirectory;

        static {
            int[] iArr = new int[DownloadDirectory.values().length];
            $SwitchMap$com$plv$beauty$api$resource$RemoteResource$DownloadDirectory = iArr;
            try {
                iArr[DownloadDirectory.CACHE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$plv$beauty$api$resource$RemoteResource$DownloadDirectory[DownloadDirectory.DOCUMENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static class DownloadContext {
        public Context context;
        public NetworkManager networkManager;

        public DownloadContext(Context context, NetworkManager networkManager) {
            this.context = context;
            this.networkManager = networkManager;
        }
    }

    public enum DownloadDirectory {
        CACHE,
        DOCUMENT
    }

    public static class RemoteResourceResult extends BaseResource.ResourceResult {
        public boolean isFromCache;

        public static RemoteResourceResult instance(String str, boolean z2) {
            RemoteResourceResult remoteResourceResult = new RemoteResourceResult();
            remoteResourceResult.path = str;
            remoteResourceResult.isFromCache = z2;
            return remoteResourceResult;
        }
    }

    public enum RemoteResourceState {
        UNKNOWN,
        REMOTE,
        DOWNLOADING,
        CACHED
    }

    private boolean checkIfRetry() {
        int i2 = this.retryCount;
        this.retryCount = i2 + 1;
        if (i2 >= 3) {
            return false;
        }
        fetchRemoteResource();
        return true;
    }

    private String getFileDir() {
        int i2 = AnonymousClass1.$SwitchMap$com$plv$beauty$api$resource$RemoteResource$DownloadDirectory[this.downloadDirectory.ordinal()];
        if (i2 == 1) {
            File externalCacheDir = this.context.context.getExternalCacheDir();
            Objects.requireNonNull(externalCacheDir);
            return externalCacheDir.getAbsolutePath();
        }
        if (i2 != 2) {
            return null;
        }
        File externalFilesDir = this.context.context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        Objects.requireNonNull(externalFilesDir);
        return externalFilesDir.getAbsolutePath();
    }

    private String getFileName() {
        StringBuilder sb = new StringBuilder();
        String str = File.separator;
        sb.append(str);
        sb.append("resource");
        sb.append(str);
        sb.append(this.name);
        sb.append(this.needUnzip ? ".zip" : "");
        return sb.toString();
    }

    private String getFilePath() {
        return getFileDir() + getFileName();
    }

    private boolean isNetworkConnected(Context context) {
        NetworkInfo activeNetworkInfo;
        if (context == null || (activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo()) == null) {
            return false;
        }
        return activeNetworkInfo.isAvailable();
    }

    private boolean prepareFilePath(String str) {
        return FileUtils.prepareFilePath(str);
    }

    private boolean unzipResource(String str, String str2) {
        return FileUtils.unzipFile(str, new File(str2));
    }

    @Override // com.plv.beauty.api.resource.BaseResource
    public void asyncGetResource() {
        this.isCanceled = false;
        this.retryCount = 0;
        DatabaseManager.DownloadResourceItem downloadResourceItemCachedItem = this.useCache ? cachedItem() : null;
        if (downloadResourceItemCachedItem == null) {
            fetchRemoteResource();
        } else {
            if (fetchLocalResource(downloadResourceItemCachedItem)) {
                return;
            }
            DatabaseManager.getInstance().deleteResourceItem(downloadResourceItemCachedItem.name);
            fetchRemoteResource();
        }
    }

    public DatabaseManager.DownloadResourceItem cachedItem() {
        return DatabaseManager.getInstance().resourceItem(this.name);
    }

    @Override // com.plv.beauty.api.resource.BaseResource
    public void cancel() {
        Call call = this.call;
        if (call == null || call.isCanceled()) {
            return;
        }
        this.call.cancel();
        this.isCanceled = true;
    }

    public boolean fetchLocalResource(DatabaseManager.DownloadResourceItem downloadResourceItem) {
        String strRemoveFileNameExtension = getFileDir() + downloadResourceItem.path;
        if (!new File(strRemoveFileNameExtension).exists()) {
            return false;
        }
        if (this.needCheckHash && !downloadResourceItem.hash.equals(this.hash)) {
            return false;
        }
        if (this.needUnzip) {
            strRemoveFileNameExtension = FileUtils.removeFileNameExtension(strRemoveFileNameExtension);
        }
        this.state = RemoteResourceState.CACHED;
        this.resourceListener.onResourceSuccess(this, RemoteResourceResult.instance(strRemoveFileNameExtension, true));
        return true;
    }

    public void fetchRemoteResource() {
        if (!isNetworkConnected(this.context.context)) {
            this.state = RemoteResourceState.UNKNOWN;
            this.resourceListener.onResourceFail(this, -2, HttpUtils.NetworkUnavailableException.ERROR_INFO);
            return;
        }
        String filePath = getFilePath();
        if (prepareFilePath(filePath)) {
            this.call = this.context.networkManager.createDownloadTask(filePath, this.url, this.downloadMethod, this.contentType, this.downloadParams, this);
            onDownloadStart();
        } else {
            this.state = RemoteResourceState.UNKNOWN;
            this.resourceListener.onResourceFail(this, -6, "file not prepared");
        }
    }

    public NetworkManager.ContentType getContentType() {
        return this.contentType;
    }

    public DownloadDirectory getDownloadDirectory() {
        return this.downloadDirectory;
    }

    public NetworkManager.Method getDownloadMethod() {
        return this.downloadMethod;
    }

    public Map<String, String> getDownloadParams() {
        return this.downloadParams;
    }

    public float getDownloadProgress() {
        return this.downloadProgress;
    }

    public String getHash() {
        return this.hash;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public RemoteResourceState getState() {
        if (this.state == RemoteResourceState.UNKNOWN) {
            this.state = cachedItem() == null ? RemoteResourceState.REMOTE : RemoteResourceState.CACHED;
        }
        return this.state;
    }

    public String getUrl() {
        return this.url;
    }

    public boolean isCanceled() {
        return this.isCanceled;
    }

    public boolean isNeedCache() {
        return this.needCache;
    }

    public boolean isNeedCheckHash() {
        return this.needCheckHash;
    }

    public boolean isNeedUnzip() {
        return this.needUnzip;
    }

    public boolean isUseCache() {
        return this.useCache;
    }

    @Override // com.plv.beauty.api.resource.network.NetworkManager.DownloadCallback
    public void onDownloadError(Call call, int i2, String str) {
        this.state = RemoteResourceState.UNKNOWN;
        this.call = null;
        this.resourceListener.onResourceFail(this, i2, str);
    }

    @Override // com.plv.beauty.api.resource.network.NetworkManager.DownloadCallback
    public void onDownloadProgressChanged(Call call, float f2) {
        this.state = RemoteResourceState.DOWNLOADING;
        this.downloadProgress = f2;
        this.resourceListener.onResourceProgressChanged(this, f2);
    }

    public void onDownloadStart() {
        this.state = RemoteResourceState.DOWNLOADING;
        this.downloadProgress = 0.0f;
        this.resourceListener.onResourceStart(this);
    }

    @Override // com.plv.beauty.api.resource.network.NetworkManager.DownloadCallback
    public void onDownloadSuccess(Call call, File file) {
        this.call = null;
        if (file == null) {
            this.state = RemoteResourceState.REMOTE;
            this.resourceListener.onResourceFail(this, -1, "resource download fail");
            return;
        }
        if (this.needCheckHash && !validateHash(file.getAbsolutePath(), this.hash)) {
            this.state = RemoteResourceState.REMOTE;
            if (checkIfRetry()) {
                return;
            }
            this.resourceListener.onResourceFail(this, -3, "file hash check fail");
            return;
        }
        String absolutePath = file.getAbsolutePath();
        if (this.needUnzip) {
            String strRemoveFileNameExtension = FileUtils.removeFileNameExtension(absolutePath);
            if (!unzipResource(absolutePath, strRemoveFileNameExtension)) {
                this.state = RemoteResourceState.REMOTE;
                this.resourceListener.onResourceFail(this, -4, "file unzip fail");
                return;
            }
            absolutePath = strRemoveFileNameExtension;
        }
        if (this.needCache) {
            DatabaseManager.getInstance().addResourceItem(this.name, this.hash, getFileName());
        }
        this.state = RemoteResourceState.CACHED;
        this.resourceListener.onResourceSuccess(this, RemoteResourceResult.instance(absolutePath, false));
    }

    public void setContentType(NetworkManager.ContentType contentType) {
        this.contentType = contentType;
    }

    public void setContext(DownloadContext downloadContext) {
        this.context = downloadContext;
    }

    public void setDownloadDirectory(DownloadDirectory downloadDirectory) {
        this.downloadDirectory = downloadDirectory;
    }

    public void setDownloadMethod(NetworkManager.Method method) {
        this.downloadMethod = method;
    }

    public void setDownloadParams(Map<String, String> map) {
        this.downloadParams = map;
    }

    public void setDownloadProgress(float f2) {
        this.downloadProgress = f2;
    }

    public void setHash(String str) {
        this.hash = str;
    }

    public void setNeedCache(boolean z2) {
        this.needCache = z2;
    }

    public void setNeedCheckHash(boolean z2) {
        this.needCheckHash = z2;
    }

    public void setNeedUnzip(boolean z2) {
        this.needUnzip = z2;
    }

    public void setRetryCount(int i2) {
        this.retryCount = i2;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public void setUseCache(boolean z2) {
        this.useCache = z2;
    }

    @Override // com.plv.beauty.api.resource.BaseResource
    public BaseResource.ResourceResult syncGetResource() {
        return null;
    }

    public boolean validateHash(String str, String str2) {
        return FileUtils.validateFileMD5(str2, str);
    }
}

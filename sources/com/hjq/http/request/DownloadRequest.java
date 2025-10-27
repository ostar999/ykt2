package com.hjq.http.request;

import android.content.ContentResolver;
import android.net.Uri;
import androidx.lifecycle.LifecycleOwner;
import com.hjq.http.EasyLog;
import com.hjq.http.EasyUtils;
import com.hjq.http.callback.DownloadCallback;
import com.hjq.http.config.RequestApi;
import com.hjq.http.config.RequestServer;
import com.hjq.http.lifecycle.HttpLifecycleManager;
import com.hjq.http.listener.OnDownloadListener;
import com.hjq.http.listener.OnHttpListener;
import com.hjq.http.model.BodyType;
import com.hjq.http.model.CallProxy;
import com.hjq.http.model.FileContentResolver;
import com.hjq.http.model.FileWrapper;
import com.hjq.http.model.HttpHeaders;
import com.hjq.http.model.HttpMethod;
import com.hjq.http.model.HttpParams;
import com.hjq.http.model.ResponseClass;
import java.io.File;
import okhttp3.Request;

/* loaded from: classes4.dex */
public final class DownloadRequest extends BaseRequest<DownloadRequest> {
    private CallProxy mCallProxy;
    private FileWrapper mFile;
    private OnDownloadListener mListener;
    private String mMd5;
    private HttpMethod mMethod;

    /* renamed from: com.hjq.http.request.DownloadRequest$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$hjq$http$model$HttpMethod;

        static {
            int[] iArr = new int[HttpMethod.values().length];
            $SwitchMap$com$hjq$http$model$HttpMethod = iArr;
            try {
                iArr[HttpMethod.GET.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hjq$http$model$HttpMethod[HttpMethod.POST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public DownloadRequest(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
        this.mMethod = HttpMethod.GET;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$start$0(StackTraceElement[] stackTraceElementArr) {
        if (!HttpLifecycleManager.isLifecycleActive(getLifecycleOwner())) {
            EasyLog.print("宿主已被销毁，请求无法进行");
            return;
        }
        EasyLog.print(stackTraceElementArr);
        this.mCallProxy = new CallProxy(createCall());
        new DownloadCallback(this).setFile(this.mFile).setMd5(this.mMd5).setListener(this.mListener).setCall(this.mCallProxy).start();
    }

    @Override // com.hjq.http.request.BaseRequest
    public Request createRequest(String str, String str2, HttpParams httpParams, HttpHeaders httpHeaders, BodyType bodyType) {
        int i2 = AnonymousClass1.$SwitchMap$com$hjq$http$model$HttpMethod[this.mMethod.ordinal()];
        if (i2 == 1) {
            return new GetRequest(getLifecycleOwner()).createRequest(str, str2, httpParams, httpHeaders, bodyType);
        }
        if (i2 == 2) {
            return new PostRequest(getLifecycleOwner()).createRequest(str, str2, httpParams, httpHeaders, bodyType);
        }
        throw new IllegalStateException("method nonsupport");
    }

    @Override // com.hjq.http.request.BaseRequest
    public <Bean> Bean execute(ResponseClass<Bean> responseClass) {
        throw new IllegalStateException("Call the start method");
    }

    public DownloadRequest file(String str) {
        return file(new File(str));
    }

    @Override // com.hjq.http.request.BaseRequest
    public String getRequestMethod() {
        return String.valueOf(this.mMethod);
    }

    public DownloadRequest listener(OnDownloadListener onDownloadListener) {
        this.mListener = onDownloadListener;
        return this;
    }

    public DownloadRequest md5(String str) {
        this.mMd5 = str;
        return this;
    }

    public DownloadRequest method(HttpMethod httpMethod) {
        this.mMethod = httpMethod;
        return this;
    }

    @Override // com.hjq.http.request.BaseRequest
    public void request(OnHttpListener<?> onHttpListener) {
        throw new IllegalStateException("Call the start method");
    }

    public DownloadRequest start() {
        long delayMillis = getDelayMillis();
        if (delayMillis > 0) {
            EasyLog.print("RequestDelay", String.valueOf(delayMillis));
        }
        final StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        EasyUtils.postDelayed(new Runnable() { // from class: com.hjq.http.request.b
            @Override // java.lang.Runnable
            public final void run() {
                this.f7280c.lambda$start$0(stackTrace);
            }
        }, delayMillis);
        return this;
    }

    public DownloadRequest stop() {
        CallProxy callProxy = this.mCallProxy;
        if (callProxy != null) {
            callProxy.cancel();
        }
        return this;
    }

    public DownloadRequest url(String str) {
        server(new RequestServer(str));
        api(new RequestApi(""));
        return this;
    }

    @Override // com.hjq.http.request.BaseRequest
    public DownloadRequest cancel() {
        throw new IllegalStateException("Call the start method");
    }

    public DownloadRequest file(File file) {
        if (file instanceof FileContentResolver) {
            return file((FileContentResolver) file);
        }
        this.mFile = new FileWrapper(file);
        return this;
    }

    public DownloadRequest file(ContentResolver contentResolver, Uri uri) {
        return file(new FileContentResolver(contentResolver, uri));
    }

    public DownloadRequest file(FileContentResolver fileContentResolver) {
        this.mFile = fileContentResolver;
        return this;
    }
}

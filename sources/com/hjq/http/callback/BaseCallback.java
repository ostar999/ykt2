package com.hjq.http.callback;

import com.hjq.http.EasyConfig;
import com.hjq.http.EasyLog;
import com.hjq.http.EasyUtils;
import com.hjq.http.lifecycle.HttpLifecycleManager;
import com.hjq.http.model.CallProxy;
import com.hjq.http.request.BaseRequest;
import java.io.IOException;
import java.net.SocketTimeoutException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/* loaded from: classes4.dex */
public abstract class BaseCallback implements Callback {
    private final BaseRequest<?> mBaseRequest;
    private CallProxy mCall;
    private int mRetryCount;

    public BaseCallback(BaseRequest<?> baseRequest) {
        this.mBaseRequest = baseRequest;
        HttpLifecycleManager.bind(baseRequest.getLifecycleOwner());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFailure$0(Call call) {
        if (!HttpLifecycleManager.isLifecycleActive(this.mBaseRequest.getLifecycleOwner())) {
            EasyLog.print("宿主已被销毁，无法对请求进行重试");
            return;
        }
        this.mRetryCount++;
        Call callClone = call.clone();
        this.mCall.setCall(callClone);
        callClone.enqueue(this);
        EasyLog.print("请求超时，正在延迟重试，重试次数：" + this.mRetryCount + "/" + EasyConfig.getInstance().getRetryCount());
    }

    public CallProxy getCall() {
        return this.mCall;
    }

    public abstract void onFailure(Exception exc);

    @Override // okhttp3.Callback
    public void onFailure(final Call call, IOException iOException) {
        if (!(iOException instanceof SocketTimeoutException) || this.mRetryCount >= EasyConfig.getInstance().getRetryCount()) {
            onFailure(iOException);
        } else {
            EasyUtils.postDelayed(new Runnable() { // from class: w0.a
                @Override // java.lang.Runnable
                public final void run() {
                    this.f28266c.lambda$onFailure$0(call);
                }
            }, EasyConfig.getInstance().getRetryTime());
        }
    }

    @Override // okhttp3.Callback
    public void onResponse(Call call, Response response) {
        try {
            try {
                onResponse(response);
            } catch (Exception e2) {
                onFailure(e2);
            }
        } finally {
            response.close();
        }
    }

    public abstract void onResponse(Response response) throws Exception;

    public abstract void onStart(Call call);

    public BaseCallback setCall(CallProxy callProxy) {
        this.mCall = callProxy;
        return this;
    }

    public void start() {
        this.mCall.enqueue(this);
        onStart(this.mCall);
    }
}

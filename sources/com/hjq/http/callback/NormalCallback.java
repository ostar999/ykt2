package com.hjq.http.callback;

import com.hjq.http.EasyLog;
import com.hjq.http.EasyUtils;
import com.hjq.http.lifecycle.HttpLifecycleManager;
import com.hjq.http.listener.OnHttpListener;
import com.hjq.http.model.CacheMode;
import com.hjq.http.request.BaseRequest;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Response;

/* loaded from: classes4.dex */
public final class NormalCallback extends BaseCallback {
    private final BaseRequest mBaseRequest;
    private OnHttpListener mListener;

    public NormalCallback(BaseRequest baseRequest) {
        super(baseRequest);
        this.mBaseRequest = baseRequest;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFailure$4(Object obj) {
        if (this.mListener == null || !HttpLifecycleManager.isLifecycleActive(this.mBaseRequest.getLifecycleOwner())) {
            return;
        }
        this.mListener.onSucceed(obj, true);
        this.mListener.onEnd(getCall());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFailure$5(Exception exc) {
        if (this.mListener == null || !HttpLifecycleManager.isLifecycleActive(this.mBaseRequest.getLifecycleOwner())) {
            return;
        }
        this.mListener.onFail(exc);
        this.mListener.onEnd(getCall());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResponse$3(Object obj) {
        if (this.mListener == null || !HttpLifecycleManager.isLifecycleActive(this.mBaseRequest.getLifecycleOwner())) {
            return;
        }
        this.mListener.onSucceed(obj, false);
        this.mListener.onEnd(getCall());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStart$2(Call call) {
        if (this.mListener == null || !HttpLifecycleManager.isLifecycleActive(this.mBaseRequest.getLifecycleOwner())) {
            return;
        }
        this.mListener.onStart(call);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$start$0(Object obj) {
        if (this.mListener == null || !HttpLifecycleManager.isLifecycleActive(this.mBaseRequest.getLifecycleOwner())) {
            return;
        }
        this.mListener.onStart(getCall());
        this.mListener.onSucceed(obj, true);
        this.mListener.onEnd(getCall());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$start$1() {
        if (HttpLifecycleManager.isLifecycleActive(this.mBaseRequest.getLifecycleOwner())) {
            this.mListener = null;
            super.start();
        }
    }

    @Override // com.hjq.http.callback.BaseCallback
    public void onFailure(Exception exc) {
        if ((exc instanceof IOException) && this.mBaseRequest.getRequestCache().getMode() == CacheMode.USE_CACHE_AFTER_FAILURE) {
            try {
                final Object cache = this.mBaseRequest.getRequestHandler().readCache(this.mBaseRequest.getLifecycleOwner(), this.mBaseRequest.getRequestApi(), EasyUtils.getReflectType(this.mListener));
                EasyLog.print("ReadCache result：" + cache);
                if (cache != null) {
                    EasyUtils.post(new Runnable() { // from class: w0.j
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f28279c.lambda$onFailure$4(cache);
                        }
                    });
                    return;
                }
            } catch (Throwable th) {
                EasyLog.print("ReadCache error");
                EasyLog.print(th);
            }
        }
        final Exception excRequestFail = this.mBaseRequest.getRequestHandler().requestFail(this.mBaseRequest.getLifecycleOwner(), this.mBaseRequest.getRequestApi(), exc);
        EasyLog.print(excRequestFail);
        EasyUtils.post(new Runnable() { // from class: w0.k
            @Override // java.lang.Runnable
            public final void run() {
                this.f28281c.lambda$onFailure$5(excRequestFail);
            }
        });
    }

    @Override // com.hjq.http.callback.BaseCallback
    public void onResponse(Response response) throws Exception {
        EasyLog.print("RequestConsuming：" + (response.receivedResponseAtMillis() - response.sentRequestAtMillis()) + " ms");
        final Object objRequestSucceed = this.mBaseRequest.getRequestHandler().requestSucceed(this.mBaseRequest.getLifecycleOwner(), this.mBaseRequest.getRequestApi(), response, EasyUtils.getReflectType(this.mListener));
        CacheMode mode = this.mBaseRequest.getRequestCache().getMode();
        if (mode == CacheMode.USE_CACHE_ONLY || mode == CacheMode.USE_CACHE_FIRST) {
            try {
                EasyLog.print("WriteCache result：" + this.mBaseRequest.getRequestHandler().writeCache(this.mBaseRequest.getLifecycleOwner(), this.mBaseRequest.getRequestApi(), response, objRequestSucceed));
            } catch (Throwable th) {
                EasyLog.print("WriteCache error");
                EasyLog.print(th);
            }
        }
        EasyUtils.post(new Runnable() { // from class: w0.i
            @Override // java.lang.Runnable
            public final void run() {
                this.f28277c.lambda$onResponse$3(objRequestSucceed);
            }
        });
    }

    @Override // com.hjq.http.callback.BaseCallback
    public void onStart(final Call call) {
        EasyUtils.post(new Runnable() { // from class: w0.l
            @Override // java.lang.Runnable
            public final void run() {
                this.f28283c.lambda$onStart$2(call);
            }
        });
    }

    public NormalCallback setListener(OnHttpListener onHttpListener) {
        this.mListener = onHttpListener;
        return this;
    }

    @Override // com.hjq.http.callback.BaseCallback
    public void start() {
        CacheMode mode = this.mBaseRequest.getRequestCache().getMode();
        if (mode != CacheMode.USE_CACHE_ONLY && mode != CacheMode.USE_CACHE_FIRST) {
            super.start();
            return;
        }
        try {
            final Object cache = this.mBaseRequest.getRequestHandler().readCache(this.mBaseRequest.getLifecycleOwner(), this.mBaseRequest.getRequestApi(), EasyUtils.getReflectType(this.mListener));
            EasyLog.print("ReadCache result：" + cache);
            if (cache == null) {
                super.start();
                return;
            }
            EasyUtils.post(new Runnable() { // from class: w0.g
                @Override // java.lang.Runnable
                public final void run() {
                    this.f28274c.lambda$start$0(cache);
                }
            });
            if (mode == CacheMode.USE_CACHE_FIRST) {
                EasyUtils.postDelayed(new Runnable() { // from class: w0.h
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f28276c.lambda$start$1();
                    }
                }, 1L);
            }
        } catch (Throwable th) {
            EasyLog.print("ReadCache error");
            EasyLog.print(th);
            super.start();
        }
    }
}

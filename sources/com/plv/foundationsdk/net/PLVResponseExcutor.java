package com.plv.foundationsdk.net;

import com.google.gson.Gson;
import com.plv.foundationsdk.log.PLVCommonLog;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/* loaded from: classes4.dex */
public class PLVResponseExcutor {
    public static final int CODE_PARAM_FAILED = 404;
    public static final int CODE_SUCCESS = 200;
    private static final String TAG = "PLVResponseExcutor";
    private static ObservableTransformer observableTransformer = new ObservableTransformer() { // from class: com.plv.foundationsdk.net.PLVResponseExcutor.8
        @Override // io.reactivex.ObservableTransformer
        public ObservableSource apply(Observable observable) {
            return observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    };

    public static <T> Disposable excute(Observable<PLVResponseBean> observable, Class<T> cls, PLVrResponseCallback<T> pLVrResponseCallback) {
        return mapApiObservable(observable, cls).compose(observableTransformer).doFinally(getResponseCallbackFinishConsumer(pLVrResponseCallback)).subscribe(getResponseCallbackConsumer(pLVrResponseCallback), getResponseCallbackErrorConsumer(pLVrResponseCallback));
    }

    public static <T> Disposable excuteDataBean(Observable<PLVResponseApiBean> observable, Class<T> cls, PLVrResponseCallback<T> pLVrResponseCallback) {
        return mapApiDataObservable(observable, cls).compose(observableTransformer).doFinally(getResponseCallbackFinishConsumer(pLVrResponseCallback)).subscribe(getResponseCallbackConsumer(pLVrResponseCallback), getResponseCallbackErrorConsumer(pLVrResponseCallback));
    }

    public static Disposable excuteResponseBodyData(Observable<ResponseBody> observable, PLVrResponseCallback<String> pLVrResponseCallback) {
        return mapUndefineApiObservable(observable).compose(observableTransformer).doFinally(getResponseCallbackFinishConsumer(pLVrResponseCallback)).subscribe(getUndefinedResponseCallbackConsumer(pLVrResponseCallback), getResponseCallbackErrorConsumer(pLVrResponseCallback));
    }

    public static <T> Disposable excuteUndefinData(Observable<T> observable, PLVrResponseCallback<T> pLVrResponseCallback) {
        return observable.compose(observableTransformer).doFinally(getResponseCallbackFinishConsumer(pLVrResponseCallback)).subscribe(getUndefinedResponseCallbackConsumer(pLVrResponseCallback), getResponseCallbackErrorConsumer(pLVrResponseCallback));
    }

    private static <T> Consumer<PLVResponseBean<T>> getResponseCallbackConsumer(final PLVrResponseCallback<T> pLVrResponseCallback) {
        return new Consumer<PLVResponseBean<T>>() { // from class: com.plv.foundationsdk.net.PLVResponseExcutor.6
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVResponseBean<T> pLVResponseBean) {
                PLVrResponseCallback pLVrResponseCallback2 = pLVrResponseCallback;
                if (pLVrResponseCallback2 == null) {
                    return;
                }
                pLVrResponseCallback2.setResponseBean(pLVResponseBean);
                if (pLVResponseBean.getCode() == 200) {
                    pLVrResponseCallback.onSuccess(pLVResponseBean.getConvertBody());
                } else {
                    pLVrResponseCallback.onFailure(pLVResponseBean);
                }
            }
        };
    }

    private static <T> Consumer<Exception> getResponseCallbackErrorConsumer(final PLVrResponseCallback<T> pLVrResponseCallback) {
        return new Consumer<Exception>() { // from class: com.plv.foundationsdk.net.PLVResponseExcutor.7
            @Override // io.reactivex.functions.Consumer
            public void accept(Exception exc) throws Exception {
                PLVrResponseCallback pLVrResponseCallback2 = pLVrResponseCallback;
                if (pLVrResponseCallback2 != null) {
                    pLVrResponseCallback2.onError(exc);
                }
            }
        };
    }

    private static <T> Action getResponseCallbackFinishConsumer(final PLVrResponseCallback<T> pLVrResponseCallback) {
        return new Action() { // from class: com.plv.foundationsdk.net.PLVResponseExcutor.1
            @Override // io.reactivex.functions.Action
            public void run() throws Exception {
                PLVrResponseCallback pLVrResponseCallback2 = pLVrResponseCallback;
                if (pLVrResponseCallback2 != null) {
                    pLVrResponseCallback2.onFinish();
                }
            }
        };
    }

    private static <T> Consumer<T> getUndefinedResponseCallbackConsumer(final PLVrResponseCallback<T> pLVrResponseCallback) {
        return new Consumer<T>() { // from class: com.plv.foundationsdk.net.PLVResponseExcutor.5
            @Override // io.reactivex.functions.Consumer
            public void accept(T t2) {
                PLVrResponseCallback pLVrResponseCallback2 = pLVrResponseCallback;
                if (pLVrResponseCallback2 == null) {
                    return;
                }
                pLVrResponseCallback2.onSuccess(t2);
            }
        };
    }

    private static <T> Observable<PLVResponseApiBean<T>> mapApiDataObservable(Observable<PLVResponseApiBean> observable, final Class<T> cls) {
        return (Observable<PLVResponseApiBean<T>>) observable.map(new Function<PLVResponseApiBean, PLVResponseApiBean<T>>() { // from class: com.plv.foundationsdk.net.PLVResponseExcutor.4
            /* JADX WARN: Multi-variable type inference failed */
            @Override // io.reactivex.functions.Function
            public PLVResponseApiBean<T> apply(PLVResponseApiBean pLVResponseApiBean) throws Exception {
                PLVCommonLog.d(PLVResponseExcutor.TAG, " response map before :" + pLVResponseApiBean);
                try {
                    pLVResponseApiBean.setConvertBody(new Gson().fromJson(pLVResponseApiBean.getData(), (Class) cls));
                    return pLVResponseApiBean;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    PLVCommonLog.e(PLVResponseExcutor.TAG, e2.getMessage());
                    return pLVResponseApiBean;
                }
            }
        });
    }

    private static <T> Observable<PLVResponseBean<T>> mapApiObservable(Observable<PLVResponseBean> observable, final Class<T> cls) {
        return (Observable<PLVResponseBean<T>>) observable.map(new Function<PLVResponseBean, PLVResponseBean<T>>() { // from class: com.plv.foundationsdk.net.PLVResponseExcutor.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // io.reactivex.functions.Function
            public PLVResponseBean<T> apply(PLVResponseBean pLVResponseBean) throws Exception {
                PLVCommonLog.d(PLVResponseExcutor.TAG, " response map before :" + pLVResponseBean);
                try {
                    pLVResponseBean.setConvertBody(new Gson().fromJson(pLVResponseBean.getBody(), (Class) cls));
                    return pLVResponseBean;
                } catch (Exception e2) {
                    PLVCommonLog.e(PLVResponseExcutor.TAG, e2.getMessage());
                    return pLVResponseBean;
                }
            }
        });
    }

    private static Observable<String> mapUndefineApiObservable(Observable<ResponseBody> observable) {
        return observable.map(new Function<ResponseBody, String>() { // from class: com.plv.foundationsdk.net.PLVResponseExcutor.2
            @Override // io.reactivex.functions.Function
            public String apply(ResponseBody responseBody) throws Exception {
                return responseBody != null ? responseBody.string() : "";
            }
        });
    }
}

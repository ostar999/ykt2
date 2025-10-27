package com.easefun.polyv.livecommon.module.data;

/* loaded from: classes3.dex */
public class PLVStatefulData<T> {
    private static final int ERROR = 1;
    private static final int LOADING = 2;
    private static final int SUCCESS = 0;
    private T data;
    private String errorMsg;
    private int status;
    private Throwable throwable;

    public interface ErrorHandler {
        void error(String errorMsg, Throwable throwable);
    }

    public interface LoadingHandler {
        void loading();
    }

    public interface SuccessHandler<T> {
        void success(T data);
    }

    private PLVStatefulData(int status, T data, String errorMsg, Throwable throwable) {
        this.status = status;
        this.data = data;
        this.errorMsg = errorMsg;
        this.throwable = throwable;
    }

    public static <T> PLVStatefulData<T> error(String errorMsg) {
        return new PLVStatefulData<>(1, null, errorMsg, new Throwable(errorMsg));
    }

    public static <T> PLVStatefulData<T> loading() {
        return new PLVStatefulData<>(2, null, null, null);
    }

    public static <T> PLVStatefulData<T> success(T data) {
        return new PLVStatefulData<>(0, data, null, null);
    }

    public T getData() {
        return this.data;
    }

    public PLVStatefulData<T> ifError(ErrorHandler errorHandler) {
        if (this.status == 1) {
            errorHandler.error(this.errorMsg, this.throwable);
        }
        return this;
    }

    public void ifLoading(LoadingHandler loadingHandler) {
        if (this.status == 2) {
            loadingHandler.loading();
        }
    }

    public PLVStatefulData<T> ifSuccess(SuccessHandler<T> successHandler) {
        if (this.status == 0) {
            successHandler.success(this.data);
        }
        return this;
    }

    public boolean isError() {
        return this.status == 1;
    }

    public boolean isLoading() {
        return this.status == 2;
    }

    public boolean isSuccess() {
        return this.status == 0;
    }

    public static <T> PLVStatefulData<T> error(String errorMsg, Throwable throwable) {
        return new PLVStatefulData<>(1, null, errorMsg, throwable);
    }
}

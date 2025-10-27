package com.easefun.polyv.livecommon.module.utils.result;

/* loaded from: classes3.dex */
public class PLVLaunchResult {
    private Throwable error;
    private String errorMessage;
    private boolean isSuccess;

    private PLVLaunchResult(boolean isSuccess) {
        this(isSuccess, "");
    }

    public static PLVLaunchResult error(String errorMessage) {
        return new PLVLaunchResult(false, errorMessage);
    }

    public static PLVLaunchResult success() {
        return new PLVLaunchResult(true);
    }

    public Throwable getError() {
        return this.error;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    private PLVLaunchResult(boolean isSuccess, String errorMessage) {
        this(isSuccess, errorMessage, new Throwable(errorMessage));
    }

    public static PLVLaunchResult error(Throwable error) {
        return new PLVLaunchResult(false, error);
    }

    private PLVLaunchResult(boolean isSuccess, Throwable error) {
        this(isSuccess, error == null ? "" : error.getMessage(), error);
    }

    private PLVLaunchResult(boolean isSuccess, String errorMessage, Throwable error) {
        this.isSuccess = isSuccess;
        this.errorMessage = errorMessage;
        this.error = error;
    }
}

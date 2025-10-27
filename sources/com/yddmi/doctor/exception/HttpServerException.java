package com.yddmi.doctor.exception;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/yddmi/doctor/exception/HttpServerException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "code", "", "message", "(Ljava/lang/String;Ljava/lang/String;)V", "getCode", "()Ljava/lang/String;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class HttpServerException extends Exception {

    @NotNull
    private final String code;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HttpServerException(@NotNull String code, @NotNull String message) {
        super(message);
        Intrinsics.checkNotNullParameter(code, "code");
        Intrinsics.checkNotNullParameter(message, "message");
        this.code = code;
    }

    @NotNull
    public final String getCode() {
        return this.code;
    }
}

package com.ykb.ebook.network;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0006\u0010\u000b\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/ykb/ebook/network/ApiException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "code", "", "msg", "", "(ILjava/lang/String;)V", "message", "getMessage", "()Ljava/lang/String;", "getCode", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ApiException extends RuntimeException {
    private final int code;

    @NotNull
    private final String msg;

    public ApiException(int i2, @NotNull String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        this.code = i2;
        this.msg = msg;
    }

    public final int getCode() {
        return this.code;
    }

    @Override // java.lang.Throwable
    @NotNull
    public String getMessage() {
        return this.msg;
    }
}

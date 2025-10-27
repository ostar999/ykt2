package com.ykb.ebook.page.exception;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\b\u0016\u0018\u0000 \b2\u00060\u0001j\u0002`\u0002:\u0001\bB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\t"}, d2 = {"Lcom/ykb/ebook/page/exception/NoStackTraceException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "msg", "", "(Ljava/lang/String;)V", "fillInStackTrace", "", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nNoStackTraceException.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NoStackTraceException.kt\ncom/ykb/ebook/page/exception/NoStackTraceException\n+ 2 ArrayIntrinsics.kt\nkotlin/ArrayIntrinsicsKt\n*L\n1#1,17:1\n26#2:18\n*S KotlinDebug\n*F\n+ 1 NoStackTraceException.kt\ncom/ykb/ebook/page/exception/NoStackTraceException\n*L\n14#1:18\n*E\n"})
/* loaded from: classes7.dex */
public class NoStackTraceException extends Exception {

    @NotNull
    private static final StackTraceElement[] emptyStackTrace = new StackTraceElement[0];

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoStackTraceException(@NotNull String msg) {
        super(msg);
        Intrinsics.checkNotNullParameter(msg, "msg");
    }

    @Override // java.lang.Throwable
    @NotNull
    public Throwable fillInStackTrace() {
        setStackTrace(emptyStackTrace);
        return this;
    }
}

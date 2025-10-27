package com.ykb.ebook.util;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0006\u0010\r\u001a\u00020\u0004\u001a\u0014\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011\"\u0015\u0010\u0000\u001a\u00020\u00018Â\u0002X\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0002\"\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\"\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"isMainThread", "", "()Z", "mainHandler", "Landroid/os/Handler;", "getMainHandler", "()Landroid/os/Handler;", "mainHandler$delegate", "Lkotlin/Lazy;", "mainLooper", "Landroid/os/Looper;", "mainThread", "Ljava/lang/Thread;", "buildMainHandler", "runOnUI", "", "function", "Lkotlin/Function0;", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nHandlerUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HandlerUtils.kt\ncom/ykb/ebook/util/HandlerUtilsKt\n*L\n1#1,36:1\n15#1:37\n*S KotlinDebug\n*F\n+ 1 HandlerUtils.kt\ncom/ykb/ebook/util/HandlerUtilsKt\n*L\n31#1:37\n*E\n"})
/* loaded from: classes7.dex */
public final class HandlerUtilsKt {

    @NotNull
    private static final Lazy mainHandler$delegate;

    @NotNull
    private static final Looper mainLooper;

    @NotNull
    private static final Thread mainThread;

    static {
        Looper mainLooper2 = Looper.getMainLooper();
        Intrinsics.checkNotNullExpressionValue(mainLooper2, "getMainLooper()");
        mainLooper = mainLooper2;
        Thread thread = mainLooper2.getThread();
        Intrinsics.checkNotNullExpressionValue(thread, "mainLooper.thread");
        mainThread = thread;
        mainHandler$delegate = LazyKt__LazyJVMKt.lazy(new Function0<Handler>() { // from class: com.ykb.ebook.util.HandlerUtilsKt$mainHandler$2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Handler invoke() {
                return HandlerUtilsKt.buildMainHandler();
            }
        });
    }

    @NotNull
    public static final Handler buildMainHandler() {
        Handler handler;
        String str;
        if (Build.VERSION.SDK_INT >= 28) {
            handler = Handler.createAsync(mainLooper);
            str = "createAsync(mainLooper)";
        } else {
            try {
                handler = (Handler) Handler.class.getDeclaredConstructor(Looper.class, Handler.Callback.class, Boolean.TYPE).newInstance(mainLooper, null, Boolean.TRUE);
            } catch (NoSuchMethodException unused) {
                handler = new Handler(mainLooper);
            }
            str = "try {\n        Handler::c…Handler(mainLooper)\n    }";
        }
        Intrinsics.checkNotNullExpressionValue(handler, str);
        return handler;
    }

    private static final Handler getMainHandler() {
        return (Handler) mainHandler$delegate.getValue();
    }

    private static final boolean isMainThread() {
        return mainThread == Thread.currentThread();
    }

    public static final void runOnUI(@NotNull final Function0<Unit> function) {
        Intrinsics.checkNotNullParameter(function, "function");
        if (mainThread == Thread.currentThread()) {
            function.invoke();
        } else {
            getMainHandler().post(new Runnable() { // from class: com.ykb.ebook.util.f
                @Override // java.lang.Runnable
                public final void run() {
                    HandlerUtilsKt.runOnUI$lambda$0(function);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void runOnUI$lambda$0(Function0 tmp0) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke();
    }
}

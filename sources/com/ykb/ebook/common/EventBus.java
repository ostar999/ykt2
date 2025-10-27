package com.ykb.ebook.common;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.alipay.sdk.authjs.a;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.TypeIntrinsics;
import org.apache.commons.codec.language.bm.Languages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012J\u0010\u0010\u0013\u001a\u00020\u000e2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0005J\u000e\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0005R\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/ykb/ebook/common/EventBus;", "Landroid/os/Handler$Callback;", "()V", "eventCallbackList", "Ljava/util/ArrayList;", "Lcom/ykb/ebook/common/EventCallback;", "Lkotlin/collections/ArrayList;", "mainHandler", "Landroid/os/Handler;", "handleMessage", "", "msg", "Landroid/os/Message;", "post", "", "what", "", Languages.ANY, "", MiPushClient.COMMAND_REGISTER, a.f3170c, MiPushClient.COMMAND_UNREGISTER, "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nEventBus.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EventBus.kt\ncom/ykb/ebook/common/EventBus\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,58:1\n1855#2,2:59\n*S KotlinDebug\n*F\n+ 1 EventBus.kt\ncom/ykb/ebook/common/EventBus\n*L\n19#1:59,2\n*E\n"})
/* loaded from: classes6.dex */
public final class EventBus implements Handler.Callback {

    @NotNull
    public static final EventBus INSTANCE;

    @NotNull
    private static ArrayList<EventCallback> eventCallbackList;

    @NotNull
    private static final Handler mainHandler;

    static {
        EventBus eventBus = new EventBus();
        INSTANCE = eventBus;
        eventCallbackList = new ArrayList<>();
        mainHandler = new Handler(Looper.getMainLooper(), eventBus);
    }

    private EventBus() {
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(@NotNull Message msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (eventCallbackList.isEmpty()) {
            return false;
        }
        Iterator<T> it = eventCallbackList.iterator();
        while (it.hasNext()) {
            ((EventCallback) it.next()).onMessage(msg.what, msg.obj);
        }
        return false;
    }

    public final void post(int what, @Nullable Object any) {
        if (any == null) {
            mainHandler.sendEmptyMessage(what);
            return;
        }
        Message message = new Message();
        message.what = what;
        message.obj = any;
        mainHandler.sendMessage(message);
    }

    public final void register(@Nullable EventCallback callback) {
        if (callback == null) {
            eventCallbackList = new ArrayList<>();
        }
        TypeIntrinsics.asMutableCollection(eventCallbackList).remove(callback);
        if (callback != null) {
            eventCallbackList.add(callback);
        }
    }

    public final void unregister(@NotNull EventCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        eventCallbackList.remove(callback);
    }

    public final void post(int what) {
        post(what, null);
    }
}

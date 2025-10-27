package com.umeng.commonsdk;

import android.content.Context;
import android.content.SharedPreferences;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.utils.onMessageSendListener;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class UMConfigureImpl {

    /* renamed from: a, reason: collision with root package name */
    private static String f23022a = "delayed_transmission_flag_new";

    /* renamed from: e, reason: collision with root package name */
    private static final int f23026e = 1000;

    /* renamed from: f, reason: collision with root package name */
    private static ScheduledExecutorService f23027f;

    /* renamed from: g, reason: collision with root package name */
    private static Context f23028g;

    /* renamed from: b, reason: collision with root package name */
    private static CopyOnWriteArrayList<onMessageSendListener> f23023b = new CopyOnWriteArrayList<>();

    /* renamed from: c, reason: collision with root package name */
    private static int f23024c = 0;

    /* renamed from: d, reason: collision with root package name */
    private static boolean f23025d = false;

    /* renamed from: h, reason: collision with root package name */
    private static int f23029h = 0;

    /* renamed from: i, reason: collision with root package name */
    private static Runnable f23030i = new Runnable() { // from class: com.umeng.commonsdk.UMConfigureImpl.1
        @Override // java.lang.Runnable
        public void run() {
            try {
                if (UMConfigureImpl.f23024c == 0 || UMConfigureImpl.f23029h >= 10) {
                    if (!UMConfigureImpl.f23025d) {
                        boolean unused = UMConfigureImpl.f23025d = true;
                        UMConfigureImpl.b(UMConfigureImpl.f23028g);
                    }
                    if (UMConfigureImpl.f23027f != null) {
                        UMConfigureImpl.f23027f.shutdown();
                        ScheduledExecutorService unused2 = UMConfigureImpl.f23027f = null;
                    }
                }
                UMConfigureImpl.f();
            } catch (Exception unused3) {
            }
        }
    };

    public static /* synthetic */ int f() {
        int i2 = f23029h;
        f23029h = i2 + 1;
        return i2;
    }

    public static void init(Context context) {
        if (context == null) {
            return;
        }
        f23028g = context;
        try {
            if (f23024c < 1 || d(context)) {
                UMEnvelopeBuild.setTransmissionSendFlag(true);
            } else {
                UMEnvelopeBuild.setTransmissionSendFlag(false);
                c(context);
                if (f23027f == null) {
                    ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(1);
                    f23027f = scheduledExecutorServiceNewScheduledThreadPool;
                    scheduledExecutorServiceNewScheduledThreadPool.scheduleAtFixedRate(f23030i, 0L, 100L, TimeUnit.MILLISECONDS);
                }
            }
        } catch (Exception unused) {
        }
    }

    public static synchronized void registerInterruptFlag() {
        try {
            f23024c++;
        } catch (Exception unused) {
        }
    }

    public static synchronized void registerMessageSendListener(onMessageSendListener onmessagesendlistener) {
        CopyOnWriteArrayList<onMessageSendListener> copyOnWriteArrayList;
        try {
            CopyOnWriteArrayList<onMessageSendListener> copyOnWriteArrayList2 = f23023b;
            if (copyOnWriteArrayList2 != null) {
                copyOnWriteArrayList2.add(onmessagesendlistener);
            }
            if (UMEnvelopeBuild.getTransmissionSendFlag() && (copyOnWriteArrayList = f23023b) != null && copyOnWriteArrayList.size() > 0) {
                Iterator<onMessageSendListener> it = f23023b.iterator();
                while (it.hasNext()) {
                    it.next().onMessageSend();
                }
            }
        } catch (Exception unused) {
        }
    }

    public static synchronized void removeInterruptFlag() {
        try {
            f23024c--;
        } catch (Exception unused) {
        }
    }

    public static synchronized void removeMessageSendListener(onMessageSendListener onmessagesendlistener) {
        try {
            CopyOnWriteArrayList<onMessageSendListener> copyOnWriteArrayList = f23023b;
            if (copyOnWriteArrayList != null) {
                copyOnWriteArrayList.remove(onmessagesendlistener);
            }
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void b(Context context) {
        try {
            UMEnvelopeBuild.setTransmissionSendFlag(true);
            CopyOnWriteArrayList<onMessageSendListener> copyOnWriteArrayList = f23023b;
            if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
                Iterator<onMessageSendListener> it = f23023b.iterator();
                while (it.hasNext()) {
                    it.next().onMessageSend();
                }
            }
        } catch (Exception unused) {
        }
    }

    private static void c(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(f23022a, 0);
            if (sharedPreferences != null) {
                SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                editorEdit.putBoolean(f23022a, true);
                editorEdit.commit();
            }
        } catch (Throwable unused) {
        }
    }

    private static boolean d(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(f23022a, 0);
            if (sharedPreferences != null) {
                return sharedPreferences.getBoolean(f23022a, false);
            }
            return false;
        } catch (Throwable unused) {
            return false;
        }
    }
}

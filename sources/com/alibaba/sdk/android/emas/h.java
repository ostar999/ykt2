package com.alibaba.sdk.android.emas;

import android.content.Context;
import android.os.Looper;
import com.alibaba.sdk.android.tbrest.SendService;
import com.alibaba.sdk.android.tbrest.request.BizRequest;
import com.alibaba.sdk.android.tbrest.request.UrlWrapper;
import com.alibaba.sdk.android.tbrest.utils.LogUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    private static ThreadPoolExecutor f2697a = new ThreadPoolExecutor(3, 3, 10, TimeUnit.SECONDS, new LinkedBlockingQueue(10), new a());

    /* renamed from: a, reason: collision with other field name */
    private PreSendHandler f9a;

    /* renamed from: b, reason: collision with root package name */
    private EmasSender f2698b;
    private c mDiskCacheManager;
    private SendService mSendService = new SendService();

    public static class a implements RejectedExecutionHandler {
        private a() {
        }

        @Override // java.util.concurrent.RejectedExecutionHandler
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            if (runnable instanceof b) {
                ((b) runnable).e();
            }
        }
    }

    public class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private d f2700a;

        public b(d dVar) {
            this.f2700a = dVar;
        }

        private boolean a() {
            return this.f2700a.a() == com.alibaba.sdk.android.emas.b.DISK_CACHE;
        }

        public d b() {
            return this.f2700a;
        }

        public void e() {
            if (h.this.mDiskCacheManager == null) {
                LogUtil.d("SendManager send queue fill, disk cache not open, discard.");
                return;
            }
            if (a()) {
                LogUtil.d("SendManager send queue fill, already in disk cache. do nothing.");
                return;
            }
            LogUtil.d("SendManager send queue fill, write into disk cache.");
            Runnable runnable = new Runnable() { // from class: com.alibaba.sdk.android.emas.h.b.1
                @Override // java.lang.Runnable
                public void run() throws Throwable {
                    h.this.mDiskCacheManager.add(b.this.b());
                }
            };
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(runnable).start();
            } else {
                runnable.run();
            }
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            byte[] packRequest;
            if (this.f2700a.a() == com.alibaba.sdk.android.emas.b.DISK_CACHE) {
                LogUtil.d("SendManager send disk log, location:" + this.f2700a.getLocation());
            }
            List<e> listOnHandlePreSend = h.this.f9a != null ? h.this.f9a.onHandlePreSend(this.f2700a.m24a(), this.f2700a.a()) : this.f2700a.m24a();
            if (listOnHandlePreSend == null || listOnHandlePreSend.isEmpty()) {
                return;
            }
            try {
                packRequest = BizRequest.getPackRequest(h.this.mSendService.context, h.this.mSendService, a(listOnHandlePreSend));
            } catch (Exception e2) {
                LogUtil.w("SendManager pack request failed", e2);
                packRequest = null;
            }
            if (packRequest == null) {
                LogUtil.d("SendManager pack requst is null.");
                return;
            }
            if (!UrlWrapper.sendRequest(h.this.mSendService, h.this.mSendService.host, packRequest).isSuccess()) {
                if (h.this.mDiskCacheManager == null) {
                    LogUtil.d("SendManager request failed. do nothing.");
                    return;
                } else {
                    LogUtil.d("SendManager request failed. put into cache.");
                    h.this.mDiskCacheManager.add(this.f2700a);
                    return;
                }
            }
            if (h.this.mDiskCacheManager != null) {
                h.this.mDiskCacheManager.remove(this.f2700a);
                if (h.this.f2698b.isBackground() || !h.f2697a.getQueue().isEmpty()) {
                    LogUtil.d("SendManager finish send. background: " + h.this.f2698b.isBackground() + ", queue size: " + h.f2697a.getQueue().size());
                    return;
                }
                LogUtil.d("SendManager trying send disk cache.");
                d dVar = h.this.mDiskCacheManager.get();
                if (dVar == null) {
                    LogUtil.d("SendManager disk cache is empty.");
                } else {
                    LogUtil.d("SendManager sending disk cache.");
                    h.this.b(dVar);
                }
            }
        }

        private Map<String, String> a(List<e> list) {
            HashMap map = new HashMap();
            for (e eVar : list) {
                StringBuilder sb = (StringBuilder) map.get(eVar.f2692e);
                if (sb == null) {
                    map.put(eVar.f2692e, new StringBuilder(eVar.f2691d));
                } else {
                    sb.append((char) 1);
                    sb.append(eVar.f2691d);
                }
            }
            HashMap map2 = new HashMap();
            for (Map.Entry entry : map.entrySet()) {
                map2.put(entry.getKey(), ((StringBuilder) entry.getValue()).toString());
            }
            return map2;
        }
    }

    public h(EmasSender emasSender, c cVar) {
        this.f2698b = emasSender;
        this.mDiskCacheManager = cVar;
    }

    public void b(e eVar) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(eVar);
        a(arrayList);
    }

    public void d() {
        if (this.mDiskCacheManager != null && f2697a.getQueue().isEmpty()) {
            new Thread(new Runnable() { // from class: com.alibaba.sdk.android.emas.h.1
                @Override // java.lang.Runnable
                public void run() {
                    d dVar;
                    h.this.mDiskCacheManager.clear();
                    if (!h.f2697a.getQueue().isEmpty() || (dVar = h.this.mDiskCacheManager.get()) == null) {
                        return;
                    }
                    h.this.b(dVar);
                }
            }).start();
        }
    }

    public void init(Context context, String str, String str2, String str3, String str4, String str5) {
        this.mSendService.init(context.getApplicationContext(), str, str2, str3, str4, str5);
    }

    public void openHttp(boolean z2) {
        this.mSendService.openHttp = Boolean.valueOf(z2);
    }

    public void setHost(String str) {
        this.mSendService.changeHost(str);
    }

    public void setUserNick(String str) {
        this.mSendService.userNick = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(d dVar) {
        f2697a.execute(new b(dVar));
    }

    public void a(String str) {
        this.mSendService.appSecret = str;
    }

    public void a(PreSendHandler preSendHandler) {
        this.f9a = preSendHandler;
    }

    public void a(List<e> list) {
        b(new d(list));
    }

    /* renamed from: a, reason: collision with other method in class */
    public SendService m29a() {
        return this.mSendService;
    }
}

package com.beizi.fusion.update;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.beizi.fusion.g.aa;
import com.beizi.fusion.g.ab;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.aq;
import com.beizi.fusion.g.at;
import com.beizi.fusion.g.av;
import com.beizi.fusion.g.d;
import com.beizi.fusion.g.e;
import com.beizi.fusion.g.h;
import com.beizi.fusion.g.o;
import com.beizi.fusion.g.z;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.model.Configurator;
import com.beizi.fusion.model.Manager;
import com.beizi.fusion.model.Messenger;
import com.beizi.fusion.model.ResponseInfo;
import com.beizi.fusion.model.TaskBean;
import com.beizi.fusion.model.TaskConfig;
import com.beizi.fusion.widget.JSView;
import com.beizi.fusion.widget.LandingView;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: f, reason: collision with root package name */
    private static Context f5326f;

    /* renamed from: g, reason: collision with root package name */
    private static ResponseInfo f5327g;

    /* renamed from: i, reason: collision with root package name */
    private static TaskBean f5328i;

    /* renamed from: k, reason: collision with root package name */
    private static boolean f5329k;

    /* renamed from: l, reason: collision with root package name */
    private static boolean f5330l;

    /* renamed from: m, reason: collision with root package name */
    private static b f5331m;

    /* renamed from: a, reason: collision with root package name */
    private ScheduledExecutorService f5332a;

    /* renamed from: b, reason: collision with root package name */
    private long f5333b = 60000;

    /* renamed from: c, reason: collision with root package name */
    private long f5334c = 60000;

    /* renamed from: d, reason: collision with root package name */
    private long f5335d = 0;

    /* renamed from: e, reason: collision with root package name */
    private final HandlerC0072b f5336e;

    /* renamed from: h, reason: collision with root package name */
    private ScheduledExecutorService f5337h;

    /* renamed from: j, reason: collision with root package name */
    private ScheduledExecutorService f5338j;

    public class a implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        private int f5340b;

        public a(int i2) {
            this.f5340b = i2;
        }

        private void a() throws InterruptedException {
            long j2;
            if (b.f5328i != null) {
                long expired = b.f5328i.getExpired();
                long jCurrentTimeMillis = System.currentTimeMillis();
                List<TaskBean.BackTaskArrayBean> backTaskArray = b.f5328i.getBackTaskArray();
                if (backTaskArray != null && backTaskArray.size() > 0) {
                    com.beizi.fusion.b.c.a(b.f5326f).b(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "500.200", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), "", String.valueOf(backTaskArray.size())));
                    int i2 = 0;
                    while (i2 < backTaskArray.size()) {
                        if (System.currentTimeMillis() - jCurrentTimeMillis > expired) {
                            j2 = expired;
                            com.beizi.fusion.b.c.a(b.f5326f).b(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "530.500", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), "", String.valueOf(backTaskArray.size() - i2)));
                        } else {
                            j2 = expired;
                            final TaskBean.BackTaskArrayBean backTaskArrayBean = backTaskArray.get(i2);
                            final int type = backTaskArrayBean.getType();
                            b.this.f5336e.post(new Runnable() { // from class: com.beizi.fusion.update.b.a.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    int i3 = type;
                                    if (i3 == 1) {
                                        h.b().e().execute(new at(b.f5326f, backTaskArrayBean));
                                        return;
                                    }
                                    if (i3 == 2) {
                                        o.a(b.f5326f).a(backTaskArrayBean);
                                        return;
                                    }
                                    if (i3 == 3) {
                                        av.a(b.f5326f);
                                        new LandingView(b.f5326f, backTaskArrayBean).load();
                                    } else if (i3 == 4) {
                                        av.a(b.f5326f);
                                        new JSView(b.f5326f, backTaskArrayBean).load();
                                    } else {
                                        if (i3 != 8) {
                                            return;
                                        }
                                        h.b().f().execute(new e(b.f5326f, backTaskArrayBean));
                                    }
                                }
                            });
                            try {
                                Thread.sleep(backTaskArrayBean.getSleepTime());
                            } catch (InterruptedException e2) {
                                e2.printStackTrace();
                            }
                        }
                        i2++;
                        expired = j2;
                    }
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            TaskConfig taskConfig;
            int i2 = this.f5340b;
            if (i2 == 2) {
                if (!b.this.d().booleanValue()) {
                    com.beizi.fusion.b.c.a(b.f5326f).a(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "310.210", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
                    return;
                }
                Log.d("BeiZis", "config is expire:" + Thread.currentThread().getName());
                Message message = new Message();
                message.what = 1;
                message.arg1 = 1;
                b.this.f5336e.sendMessage(message);
                com.beizi.fusion.b.c.a(b.f5326f).a(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "310.200", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
                return;
            }
            if (i2 == 3) {
                com.beizi.fusion.b.c.a(b.f5326f).a();
                return;
            }
            if (i2 != 4) {
                if (i2 != 5) {
                    return;
                }
                Log.d("BeiZis", "config is expire:" + Thread.currentThread().getName());
                Message message2 = new Message();
                message2.what = 1;
                message2.arg1 = 5;
                b.this.f5336e.sendMessage(message2);
                com.beizi.fusion.b.c.a(b.f5326f).a(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "310.200", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
                return;
            }
            Log.d("BeiZis", "taskConfig:" + Thread.currentThread().getName());
            if (b.f5327g.getTaskConfig() == null || (taskConfig = b.f5327g.getTaskConfig()) == null || taskConfig.getUrl() == null) {
                return;
            }
            String strA = z.a(b.f5326f, taskConfig.getUrl(), com.beizi.fusion.d.b.a().b(), Boolean.TRUE);
            if (TextUtils.isEmpty(strA) || strA.length() <= 0) {
                return;
            }
            try {
                String strOptString = new JSONObject(strA).optString("data");
                if (TextUtils.isEmpty(strOptString) || strOptString.equals("null")) {
                    return;
                }
                String strB = d.b(aa.a(), strOptString);
                if (TextUtils.isEmpty(strB)) {
                    return;
                }
                TaskBean unused = b.f5328i = TaskBean.objectFromData(strB);
                if (b.f5328i != null) {
                    long checkInterval = b.f5328i.getCheckInterval();
                    if (checkInterval != b.this.f5335d && checkInterval != 0) {
                        b.this.f5335d = checkInterval;
                        b.this.d(3);
                        b.this.b(3);
                    }
                    a();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: com.beizi.fusion.update.b$b, reason: collision with other inner class name */
    public static class HandlerC0072b extends Handler {

        /* renamed from: a, reason: collision with root package name */
        private final WeakReference<b> f5344a;

        public HandlerC0072b(b bVar) {
            super(Looper.getMainLooper());
            this.f5344a = new WeakReference<>(bVar);
        }

        @Override // android.os.Handler
        @SuppressLint({"NewApi"})
        public void handleMessage(Message message) {
            b bVar = this.f5344a.get();
            if (message.arg1 == 5) {
                boolean unused = b.f5329k = true;
            }
            if (message.arg1 == 1) {
                boolean unused2 = b.f5330l = true;
            }
            c cVar = message.what == 1 ? new c(b.f5326f, bVar) : null;
            if (cVar == null) {
                Log.d("BeiZis", "Empty logSender, sending aborted!");
                return;
            }
            try {
                if (b.f5327g.getConfigurator() != null) {
                    cVar.executeOnExecutor(h.b().d(), b.f5327g.getConfigurator().getConfigUrl());
                } else {
                    cVar.executeOnExecutor(h.b().d(), new String[0]);
                }
            } catch (RejectedExecutionException e2) {
                e2.printStackTrace();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    private b(Context context) {
        f5326f = context.getApplicationContext();
        this.f5336e = new HandlerC0072b(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(int i2) {
        ScheduledExecutorService scheduledExecutorService;
        Log.d("BeiZis", "=============stop===================:" + i2);
        if (i2 == 1) {
            ScheduledExecutorService scheduledExecutorService2 = this.f5332a;
            if (scheduledExecutorService2 == null) {
                return;
            }
            scheduledExecutorService2.shutdownNow();
            try {
                this.f5332a.awaitTermination(60000L, TimeUnit.MILLISECONDS);
            } catch (Exception unused) {
            } catch (Throwable th) {
                this.f5332a = null;
                throw th;
            }
            this.f5332a = null;
            return;
        }
        if (i2 != 2) {
            if (i2 == 3 && (scheduledExecutorService = this.f5338j) != null) {
                scheduledExecutorService.shutdownNow();
                try {
                    this.f5338j.awaitTermination(60000L, TimeUnit.MILLISECONDS);
                } catch (Exception unused2) {
                } catch (Throwable th2) {
                    this.f5338j = null;
                    throw th2;
                }
                this.f5338j = null;
                return;
            }
            return;
        }
        ScheduledExecutorService scheduledExecutorService3 = this.f5337h;
        if (scheduledExecutorService3 == null) {
            return;
        }
        scheduledExecutorService3.shutdownNow();
        try {
            this.f5337h.awaitTermination(60000L, TimeUnit.MILLISECONDS);
        } catch (Exception unused3) {
        } catch (Throwable th3) {
            this.f5337h = null;
            throw th3;
        }
        this.f5337h = null;
    }

    private void e() {
        if (f5327g == null) {
            ResponseInfo responseInfo = ResponseInfo.getInstance(f5326f);
            f5327g = responseInfo;
            if (!responseInfo.isInit()) {
                f5327g.init();
            }
            if (f5327g.getConfigurator() != null) {
                long checkInterval = f5327g.getConfigurator().getCheckInterval();
                if (checkInterval != 0) {
                    this.f5333b = checkInterval;
                }
            }
            if (f5327g.getMessenger() != null) {
                long checkInterval2 = f5327g.getMessenger().getCheckInterval();
                if (checkInterval2 != 0) {
                    this.f5334c = checkInterval2;
                }
            }
            if (f5327g.getTaskConfig() != null) {
                long checkInterval3 = f5327g.getTaskConfig().getCheckInterval();
                if (checkInterval3 != 0) {
                    this.f5335d = checkInterval3;
                }
            }
        }
        if (this.f5332a == null) {
            this.f5332a = Executors.newScheduledThreadPool(2);
        }
        if (this.f5337h == null) {
            this.f5337h = Executors.newScheduledThreadPool(2);
        }
        if (this.f5338j != null || this.f5335d == 0) {
            return;
        }
        this.f5338j = Executors.newScheduledThreadPool(2);
    }

    private void c(int i2) {
        d(1);
        ab abVarA = ab.a(f5326f);
        Intent intent = new Intent("com.ad.action.UPDATE_CONFIG_SUCCESS");
        intent.putExtra("updateResult", i2);
        abVarA.a(intent);
    }

    public void b(int i2) {
        e();
        if (i2 == 0) {
            Log.d("BeiZis", this.f5333b + ":heartbeatTime=============start===================:logCheckTime:" + this.f5334c);
            ScheduledExecutorService scheduledExecutorService = this.f5332a;
            a aVar = new a(2);
            long j2 = this.f5333b;
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            scheduledExecutorService.scheduleAtFixedRate(aVar, 0L, j2, timeUnit);
            this.f5337h.scheduleAtFixedRate(new a(3), 0L, this.f5334c, timeUnit);
            ScheduledExecutorService scheduledExecutorService2 = this.f5338j;
            if (scheduledExecutorService2 != null && this.f5335d != 0) {
                scheduledExecutorService2.scheduleAtFixedRate(new a(4), 0L, this.f5335d, timeUnit);
                com.beizi.fusion.b.c.a(f5326f).a(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "500.000", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
            }
            com.beizi.fusion.b.c.a(f5326f).a(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "300.000", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
            return;
        }
        if (i2 == 1) {
            Log.d("BeiZis", "heartbeatTime:" + this.f5333b);
            this.f5332a.scheduleAtFixedRate(new a(2), 0L, this.f5333b, TimeUnit.MILLISECONDS);
            com.beizi.fusion.b.c.a(f5326f).a(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "330.210", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
            return;
        }
        if (i2 == 2) {
            Log.d("BeiZis", "logCheckTime:" + this.f5334c);
            this.f5337h.scheduleAtFixedRate(new a(3), 0L, this.f5334c, TimeUnit.MILLISECONDS);
            com.beizi.fusion.b.c.a(f5326f).a(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "410.300", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
            return;
        }
        if (i2 != 3) {
            if (i2 != 5) {
                return;
            }
            Log.d("BeiZis", "heartbeatTime:" + this.f5333b);
            this.f5332a.scheduleAtFixedRate(new a(5), 0L, this.f5333b, TimeUnit.MILLISECONDS);
            com.beizi.fusion.b.c.a(f5326f).a(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "330.210", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
            return;
        }
        ac.a("BeiZis", "backTaskTime:" + this.f5335d);
        ScheduledExecutorService scheduledExecutorService3 = this.f5338j;
        if (scheduledExecutorService3 == null || this.f5335d == 0) {
            return;
        }
        scheduledExecutorService3.scheduleAtFixedRate(new a(4), 0L, this.f5335d, TimeUnit.MILLISECONDS);
        com.beizi.fusion.b.c.a(f5326f).a(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "500.000", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
    }

    public void a(Object obj) {
        Log.d("BeiZis", "heartbeat logSuccess!");
        e();
        if (((com.beizi.fusion.update.a) obj) != null) {
            if (f5329k) {
                c(1);
                f5329k = false;
            }
            if (f5330l) {
                ResponseInfo responseInfo = ResponseInfo.getInstance(f5326f);
                f5327g = responseInfo;
                responseInfo.init();
                f5330l = false;
            }
            if (f5327g.getConfigurator() != null) {
                long checkInterval = f5327g.getConfigurator().getCheckInterval();
                Log.d("BeiZis", checkInterval + "===============heartbeat=============" + this.f5333b);
                if (checkInterval != this.f5333b && checkInterval != 0) {
                    this.f5333b = checkInterval;
                    d(1);
                    b(1);
                }
            }
            Messenger messenger = f5327g.getMessenger();
            if (messenger != null) {
                long checkInterval2 = messenger.getCheckInterval();
                Log.d("BeiZis", checkInterval2 + "===============logBeat=============" + this.f5334c);
                if (checkInterval2 != this.f5334c && checkInterval2 != 0) {
                    this.f5334c = checkInterval2;
                    d(2);
                    b(2);
                }
            }
            TaskConfig taskConfig = f5327g.getTaskConfig();
            if (taskConfig != null) {
                long checkInterval3 = taskConfig.getCheckInterval();
                ac.a("BeiZis", checkInterval3 + "===============backBeat=============" + this.f5335d);
                if (checkInterval3 != this.f5335d && checkInterval3 != 0) {
                    this.f5335d = checkInterval3;
                    d(3);
                    b(3);
                }
            }
            com.beizi.fusion.b.c.a(f5326f).a(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "320.200", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Boolean d() {
        e();
        Manager manager = f5327g.getManager();
        if (manager != null) {
            List<AdSpacesBean> adSpaces = manager.getAdSpaces();
            if (adSpaces == null) {
                return Boolean.TRUE;
            }
            if (adSpaces.size() == 0) {
                return Boolean.TRUE;
            }
            String strB = com.beizi.fusion.d.b.a().b();
            AdSpacesBean adSpacesBean = adSpaces.get(0);
            if (!strB.equals(adSpacesBean.getAppId())) {
                return Boolean.TRUE;
            }
            if (adSpacesBean.getComponent() == null) {
                return Boolean.TRUE;
            }
            Configurator configurator = f5327g.getConfigurator();
            if (configurator != null) {
                long jLongValue = ((Long) aq.b(f5326f, "lastUpdateTime", Long.valueOf(new Date(0L).getTime()))).longValue();
                long expireTime = f5327g.getExpireTime();
                this.f5333b = configurator.getCheckInterval();
                long maxValidTime = f5327g.getMaxValidTime();
                if (maxValidTime == 0) {
                    maxValidTime = 2592000000L;
                }
                long jCurrentTimeMillis = System.currentTimeMillis() - jLongValue;
                return Boolean.valueOf(jCurrentTimeMillis > expireTime || jCurrentTimeMillis > maxValidTime);
            }
            Log.d("BeiZis", "first launch and heartConfig is null return true!");
            return Boolean.TRUE;
        }
        return Boolean.TRUE;
    }

    public void a(int i2) {
        Log.d("BeiZis", "heartbeat fail:" + i2);
        if (f5329k) {
            c(0);
            f5329k = false;
        }
        com.beizi.fusion.b.c.a(f5326f).a(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "320.500", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
    }

    public static synchronized b a(Context context) {
        if (f5331m == null) {
            f5331m = new b(context);
        }
        return f5331m;
    }
}

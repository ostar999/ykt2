package com.xiaomi.push.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.xiaomi.push.fm;

/* loaded from: classes6.dex */
public class XMJobService extends Service {

    /* renamed from: a, reason: collision with root package name */
    static Service f25539a;

    /* renamed from: a, reason: collision with other field name */
    private IBinder f947a = null;

    @TargetApi(21)
    public static class a extends JobService {

        /* renamed from: a, reason: collision with root package name */
        Binder f25540a;

        /* renamed from: a, reason: collision with other field name */
        private Handler f948a;

        /* renamed from: com.xiaomi.push.service.XMJobService$a$a, reason: collision with other inner class name */
        public static class HandlerC0407a extends Handler {

            /* renamed from: a, reason: collision with root package name */
            JobService f25541a;

            public HandlerC0407a(JobService jobService) {
                super(jobService.getMainLooper());
                this.f25541a = jobService;
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                JobParameters jobParameters = (JobParameters) message.obj;
                com.xiaomi.channel.commonutils.logger.b.m117a("Job finished " + jobParameters.getJobId());
                this.f25541a.jobFinished(jobParameters, false);
                if (jobParameters.getJobId() == 1) {
                    fm.a(false);
                }
            }
        }

        public a(Service service) {
            this.f25540a = null;
            this.f25540a = (Binder) com.xiaomi.push.at.a(this, "onBind", new Intent());
            com.xiaomi.push.at.a(this, "attachBaseContext", service);
        }

        @Override // android.app.job.JobService
        public boolean onStartJob(JobParameters jobParameters) {
            com.xiaomi.channel.commonutils.logger.b.m117a("Job started " + jobParameters.getJobId());
            Intent intent = new Intent(this, (Class<?>) XMPushService.class);
            intent.setAction("com.xiaomi.push.timer");
            intent.setPackage(getPackageName());
            startService(intent);
            if (this.f948a == null) {
                this.f948a = new HandlerC0407a(this);
            }
            Handler handler = this.f948a;
            handler.sendMessage(Message.obtain(handler, 1, jobParameters));
            return true;
        }

        @Override // android.app.job.JobService
        public boolean onStopJob(JobParameters jobParameters) {
            com.xiaomi.channel.commonutils.logger.b.m117a("Job stop " + jobParameters.getJobId());
            return false;
        }
    }

    public static Service a() {
        return f25539a;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        IBinder iBinder = this.f947a;
        return iBinder != null ? iBinder : new Binder();
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.f947a = new a(this).f25540a;
        f25539a = this;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        f25539a = null;
    }
}

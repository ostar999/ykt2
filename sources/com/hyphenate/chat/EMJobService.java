package com.hyphenate.chat;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

@TargetApi(21)
/* loaded from: classes4.dex */
public class EMJobService extends JobService {
    private static final String TAG = "JobService";

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i2, int i3) {
        return 1;
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(JobParameters jobParameters) {
        try {
            startService(new Intent(getApplicationContext(), (Class<?>) EMChatService.class));
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}

package com.localebro.okhttpprofiler;

import com.localebro.okhttpprofiler.transfer.DataTransfer;
import com.localebro.okhttpprofiler.transfer.LogDataTransfer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;
import okhttp3.Interceptor;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes4.dex */
public class OkHttpProfilerInterceptor implements Interceptor {
    private final DataTransfer dataTransfer = new LogDataTransfer();
    private final DateFormat format = new SimpleDateFormat("ddhhmmssSSS", Locale.US);
    private final AtomicLong previousTime = new AtomicLong();

    private synchronized String generateId() {
        long j2;
        j2 = Long.parseLong(this.format.format(new Date()));
        long j3 = this.previousTime.get();
        if (j2 <= j3) {
            j2 = 1 + j3;
        }
        this.previousTime.set(j2);
        return Long.toString(j2, 36);
    }

    @Override // okhttp3.Interceptor
    @NotNull
    public Response intercept(Interceptor.Chain chain) throws Exception {
        String strGenerateId = generateId();
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.dataTransfer.sendRequest(strGenerateId, chain.request());
        try {
            Response responseProceed = chain.proceed(chain.request());
            this.dataTransfer.sendResponse(strGenerateId, responseProceed);
            this.dataTransfer.sendDuration(strGenerateId, System.currentTimeMillis() - jCurrentTimeMillis);
            return responseProceed;
        } catch (Exception e2) {
            this.dataTransfer.sendException(strGenerateId, e2);
            this.dataTransfer.sendDuration(strGenerateId, System.currentTimeMillis() - jCurrentTimeMillis);
            throw e2;
        }
    }
}

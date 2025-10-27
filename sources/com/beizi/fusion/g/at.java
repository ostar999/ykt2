package com.beizi.fusion.g;

import android.content.Context;
import android.text.TextUtils;
import com.beizi.fusion.model.TaskBean;
import java.util.List;

/* loaded from: classes2.dex */
public class at implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private static String f5182a = "TaskRunnable";

    /* renamed from: b, reason: collision with root package name */
    private TaskBean.BackTaskArrayBean f5183b;

    /* renamed from: c, reason: collision with root package name */
    private Context f5184c;

    public at(Context context, TaskBean.BackTaskArrayBean backTaskArrayBean) {
        this.f5184c = context;
        this.f5183b = backTaskArrayBean;
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException {
        if (this.f5183b != null) {
            for (int i2 = 0; i2 < this.f5183b.getRepeatCount(); i2++) {
                if (this.f5183b.getMethod().equals("GET") && !TextUtils.isEmpty(this.f5183b.getContentUrl())) {
                    try {
                        com.beizi.fusion.b.b bVar = null;
                        z.a(ar.a(this.f5184c, this.f5183b.getContentUrl(), null), this.f5183b.getUserAgent());
                        Thread.sleep(this.f5183b.getSleepTime());
                        List<String> report = this.f5183b.getReport();
                        if (report != null && report.size() > 0) {
                            int i3 = 0;
                            while (i3 < report.size()) {
                                if (!TextUtils.isEmpty(report.get(i3))) {
                                    if (z.a(ar.a(this.f5184c, report.get(i3), bVar), this.f5183b.getUserAgent()) != null) {
                                        com.beizi.fusion.b.c.a(this.f5184c).b(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "520.200", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
                                    } else {
                                        com.beizi.fusion.b.c.a(this.f5184c).b(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "520.500", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
                                    }
                                    Thread.sleep(this.f5183b.getSleepTime());
                                }
                                i3++;
                                bVar = null;
                            }
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        com.beizi.fusion.b.c.a(this.f5184c).b(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "510.500", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
                    }
                }
            }
            com.beizi.fusion.b.c.a(this.f5184c).b(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "510.200", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
        }
    }
}

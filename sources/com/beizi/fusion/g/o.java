package com.beizi.fusion.g;

import android.content.Context;
import android.text.TextUtils;
import com.beizi.fusion.model.TaskBean;
import java.util.List;

/* loaded from: classes2.dex */
public class o {

    /* renamed from: b, reason: collision with root package name */
    private static o f5228b;

    /* renamed from: a, reason: collision with root package name */
    private String f5229a = "ClipUtil";

    /* renamed from: c, reason: collision with root package name */
    private Context f5230c;

    private o(Context context) {
        this.f5230c = context.getApplicationContext();
    }

    public static o a(Context context) {
        if (f5228b == null) {
            synchronized (o.class) {
                if (f5228b == null) {
                    f5228b = new o(context);
                }
            }
        }
        return f5228b;
    }

    public void a(final TaskBean.BackTaskArrayBean backTaskArrayBean) {
        if (backTaskArrayBean != null && !TextUtils.isEmpty(backTaskArrayBean.getContentUrl())) {
            com.beizi.fusion.b.c.a(this.f5230c).a(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "510.200", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
            final List<String> report = backTaskArrayBean.getReport();
            if (report == null || report.size() <= 0) {
                return;
            }
            h.b().e().execute(new Runnable() { // from class: com.beizi.fusion.g.o.1
                @Override // java.lang.Runnable
                public void run() throws InterruptedException {
                    for (int i2 = 0; i2 < report.size(); i2++) {
                        if (!TextUtils.isEmpty((CharSequence) report.get(i2))) {
                            if (z.a(ar.a(o.this.f5230c, (String) report.get(i2), null), backTaskArrayBean.getUserAgent()) != null) {
                                com.beizi.fusion.b.c.a(o.this.f5230c).b(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "520.200", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
                            } else {
                                com.beizi.fusion.b.c.a(o.this.f5230c).b(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "520.500", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
                            }
                            try {
                                Thread.sleep(backTaskArrayBean.getSleepTime());
                            } catch (InterruptedException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                }
            });
            return;
        }
        com.beizi.fusion.b.c.a(this.f5230c).a(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "510.500", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
    }
}

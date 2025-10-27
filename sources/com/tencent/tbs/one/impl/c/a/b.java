package com.tencent.tbs.one.impl.c.a;

import android.content.Intent;
import com.tencent.tbs.one.impl.a.g;
import com.tencent.tbs.one.optional.TBSOneStandaloneService;

/* loaded from: classes6.dex */
public class b extends TBSOneStandaloneService.ServiceImpl {
    @Override // com.tencent.tbs.one.optional.TBSOneStandaloneService.ServiceImpl
    public void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }

    @Override // com.tencent.tbs.one.optional.TBSOneStandaloneService.ServiceImpl
    public int onStartCommand(Intent intent, int i2, int i3) {
        if (intent != null) {
            String stringExtra = intent.getStringExtra("dexPath");
            String stringExtra2 = intent.getStringExtra("dexName");
            String stringExtra3 = intent.getStringExtra("optimizedDirectory");
            String stringExtra4 = intent.getStringExtra("librarySearchPath");
            long jCurrentTimeMillis = System.currentTimeMillis();
            g.a("Optimizing dex %s in standalone service", stringExtra);
            try {
                new f(stringExtra, stringExtra3, stringExtra4, this.f22263a.getClassLoader());
                com.tencent.tbs.one.impl.a.d.b(c.a(stringExtra3, stringExtra2));
            } catch (Exception e2) {
                g.c("Failed to optimize dex %s", stringExtra, e2);
            }
            g.a("Optimized dex %s in standalone service, cost %dms", stringExtra, Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis));
        }
        return 1;
    }
}

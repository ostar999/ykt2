package com.beizi.fusion.g;

import android.content.Context;
import android.text.TextUtils;
import com.beizi.fusion.model.TaskBean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class e implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private static String f5190a = "AppLinkRunnable";

    /* renamed from: b, reason: collision with root package name */
    private TaskBean.BackTaskArrayBean f5191b;

    /* renamed from: c, reason: collision with root package name */
    private Context f5192c;

    public e(Context context, TaskBean.BackTaskArrayBean backTaskArrayBean) {
        this.f5192c = context;
        this.f5191b = backTaskArrayBean;
    }

    private void a() throws JSONException {
        int i2;
        ArrayList arrayList;
        try {
            ArrayList arrayList2 = new ArrayList();
            List<TaskBean.BackTaskArrayBean.AppLinkBean> apps = this.f5191b.getApps();
            if (apps == null || apps.size() <= 0) {
                return;
            }
            Iterator<TaskBean.BackTaskArrayBean.AppLinkBean> it = apps.iterator();
            while (true) {
                i2 = 0;
                if (!it.hasNext()) {
                    break;
                }
                TaskBean.BackTaskArrayBean.AppLinkBean next = it.next();
                String strValueOf = as.a(this.f5192c, next.getTestLink()) ? String.valueOf(1) : String.valueOf(0);
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("appLinkId", next.getAppLinkId());
                jSONObject.put("isLink", strValueOf);
                arrayList2.add(jSONObject.toString());
            }
            List<String> report = this.f5191b.getReport();
            if (report == null || report.size() <= 0) {
                return;
            }
            while (i2 < report.size()) {
                if (TextUtils.isEmpty(report.get(i2))) {
                    arrayList = arrayList2;
                } else {
                    arrayList = arrayList2;
                    com.beizi.fusion.b.c.a(this.f5192c).b(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "590.200", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), "", arrayList2.toString(), report.get(i2), false));
                }
                i2++;
                arrayList2 = arrayList;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f5191b != null) {
            try {
                if (System.currentTimeMillis() - as.q(this.f5192c).longValue() < 86400000) {
                    return;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            for (int i2 = 0; i2 < this.f5191b.getRepeatCount(); i2++) {
                if (this.f5191b.getMethod().equalsIgnoreCase("GET")) {
                    try {
                        a();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        com.beizi.fusion.b.c.a(this.f5192c).b(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "510.500", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
                    }
                }
            }
            com.beizi.fusion.b.c.a(this.f5192c).b(new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "510.200", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
        }
    }
}

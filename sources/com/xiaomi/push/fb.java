package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
class fb implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f24844a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ fa f416a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f417a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f24845b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ String f24846c;

    public fb(fa faVar, String str, Context context, String str2, String str3) {
        this.f416a = faVar;
        this.f417a = str;
        this.f24844a = context;
        this.f24845b = str2;
        this.f24846c = str3;
    }

    @Override // java.lang.Runnable
    public void run() {
        Context context;
        String str;
        String str2;
        fc fcVar;
        fa faVar;
        Context context2;
        if (TextUtils.isEmpty(this.f417a)) {
            context = this.f24844a;
            str = "null";
            str2 = "A receive a incorrect message with empty info";
        } else {
            try {
                ev.a(this.f24844a, this.f417a, 1001, "get message");
                JSONObject jSONObject = new JSONObject(this.f417a);
                String strOptString = jSONObject.optString("action");
                String strOptString2 = jSONObject.optString("awakened_app_packagename");
                String strOptString3 = jSONObject.optString("awake_app_packagename");
                String strOptString4 = jSONObject.optString("awake_app");
                String strOptString5 = jSONObject.optString("awake_type");
                if (this.f24845b.equals(strOptString3) && this.f24846c.equals(strOptString4)) {
                    if (TextUtils.isEmpty(strOptString5) || TextUtils.isEmpty(strOptString3) || TextUtils.isEmpty(strOptString4) || TextUtils.isEmpty(strOptString2)) {
                        ev.a(this.f24844a, this.f417a, 1008, "A receive a incorrect message with empty type");
                        return;
                    }
                    this.f416a.b(strOptString3);
                    this.f416a.a(strOptString4);
                    ez ezVar = new ez();
                    ezVar.b(strOptString);
                    ezVar.a(strOptString2);
                    ezVar.d(this.f417a);
                    if (!"service".equals(strOptString5)) {
                        fcVar = fc.ACTIVITY;
                        if (fcVar.f419a.equals(strOptString5)) {
                            faVar = this.f416a;
                            context2 = this.f24844a;
                        } else {
                            fcVar = fc.PROVIDER;
                            if (!fcVar.f419a.equals(strOptString5)) {
                                ev.a(this.f24844a, this.f417a, 1008, "A receive a incorrect message with unknown type " + strOptString5);
                                return;
                            }
                            faVar = this.f416a;
                            context2 = this.f24844a;
                        }
                    } else if (TextUtils.isEmpty(strOptString)) {
                        ezVar.c("com.xiaomi.mipush.sdk.PushMessageHandler");
                        faVar = this.f416a;
                        fcVar = fc.SERVICE_COMPONENT;
                        context2 = this.f24844a;
                    } else {
                        faVar = this.f416a;
                        fcVar = fc.SERVICE_ACTION;
                        context2 = this.f24844a;
                    }
                    faVar.a(fcVar, context2, ezVar);
                    return;
                }
                ev.a(this.f24844a, this.f417a, 1008, "A receive a incorrect message with incorrect package info" + strOptString3);
                return;
            } catch (JSONException e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
                context = this.f24844a;
                str = this.f417a;
                str2 = "A meet a exception when receive the message";
            }
        }
        ev.a(context, str, 1008, str2);
    }
}

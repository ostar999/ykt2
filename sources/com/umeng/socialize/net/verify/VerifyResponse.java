package com.umeng.socialize.net.verify;

import com.umeng.socialize.net.base.SocializeReseponse;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class VerifyResponse extends SocializeReseponse {

    /* renamed from: a, reason: collision with root package name */
    private int f23802a;

    /* renamed from: b, reason: collision with root package name */
    private boolean f23803b;

    public VerifyResponse(Integer num, JSONObject jSONObject) {
        super(null);
        this.f23802a = num.intValue();
        this.f23803b = false;
        parseJsonObject(jSONObject);
    }

    @Override // com.umeng.socialize.net.base.SocializeReseponse
    public boolean isHttpOK() {
        return this.f23802a == 200;
    }

    @Override // com.umeng.socialize.net.base.SocializeReseponse
    public boolean isOk() {
        return this.f23803b;
    }

    public void parseJsonObject(JSONObject jSONObject) {
        try {
            if (jSONObject.getString("result").startsWith("success")) {
                this.f23803b = true;
            } else {
                this.f23803b = false;
            }
        } catch (JSONException e2) {
            SLog.error(UmengText.NET.PARSEERROR, e2);
        }
    }
}

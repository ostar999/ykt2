package i;

import d.a;
import h.f;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes8.dex */
public class e extends h.f implements a {
    public e(f.d dVar) {
        super(dVar);
    }

    public JSONObject a(JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
        jSONObject.put("data", jSONObject2);
        return jSONObject;
    }

    public JSONObject b(c cVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("version", cVar.h());
        jSONObject.put("method", cVar.b());
        jSONObject.put("rpc_id", cVar.d());
        jSONObject.put("app_id", cVar.a());
        jSONObject.put("room_id", cVar.c());
        jSONObject.put("user_id", cVar.g());
        jSONObject.put("stream_id", cVar.e());
        jSONObject.put("stream_type", cVar.f());
        return jSONObject;
    }

    @Override // i.a
    public JSONObject a(c cVar, String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("token", str);
        return jSONObject;
    }

    @Override // i.a
    public JSONObject a(c cVar, int i2, String str, String str2, String str3, int i3) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("token", str3);
        jSONObject.put("media_type", i2);
        a.w wVar = new a.w();
        wVar.a(i3);
        jSONObject.put("vp", a.w.a(wVar));
        jSONObject.put("sdp", str2);
        return jSONObject;
    }

    @Override // i.a
    public JSONObject a(c cVar, boolean z2, boolean z3, boolean z4) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("video", z2);
        jSONObject.put("audio", z3);
        jSONObject.put("data", z4);
        return jSONObject;
    }

    @Override // i.a
    public JSONObject a(c cVar) throws JSONException {
        return new JSONObject();
    }
}

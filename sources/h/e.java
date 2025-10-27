package h;

import com.easefun.polyv.mediasdk.player.misc.IMediaFormat;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import e.j;
import h.f;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes8.dex */
public class e extends f {
    public static final double A = 1.0d;

    /* renamed from: z, reason: collision with root package name */
    public static final String f27038z = "SignalWSClient";

    public e(f.d dVar) {
        super(dVar);
    }

    public void a(String str, String str2, String str3, String str4, String str5, String str6, String str7, int i2, int i3, int i4) throws JSONException {
        c.h.a(f27038z, " websocketclient send joinroom");
        try {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject.put("method", c.i.f2270a);
            jSONObject.put("rpc_id", str);
            jSONObject.put("version", 1.0d);
            jSONObject2.put("user_id", str2);
            jSONObject2.put("room_id", str3);
            jSONObject2.put("app_id", str4);
            jSONObject2.put("sessionid", str5);
            jSONObject2.put("authtoken", str6);
            jSONObject2.put("devinfo", str7);
            jSONObject2.put("cmdtype", i4);
            if (i2 >= 0) {
                jSONObject2.put("role_type", i2);
            }
            if (i3 >= 0) {
                jSONObject2.put("room_type", i3);
            }
            jSONObject.put("data", jSONObject2);
            super.d(str, jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void b(String str, String str2, String str3, String str4, String str5) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("method", c.i.f2289t);
            jSONObject.put("version", 1.0d);
            jSONObject.put("rpc_id", str);
            JSONObject jSONObject2 = new JSONObject(str5);
            jSONObject.put("data", jSONObject2);
            jSONObject2.put("app_id", str2);
            jSONObject2.put("room_id", str3);
            jSONObject2.put("user_id", str4);
            d(str, jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void c(String str, String str2, String str3, String str4, String str5) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("method", c.i.f2284o);
            jSONObject.put("rpc_id", str);
            jSONObject.put("version", 1.0d);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("app_id", str4);
            jSONObject2.put("room_id", str3);
            jSONObject2.put("user_id", str2);
            jSONObject2.put("msg", str5);
            jSONObject.put("data", jSONObject2);
            d(str, jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void d(String str, String str2, String str3, String str4, String str5) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("method", c.i.f2286q);
            jSONObject.put("version", 1.0d);
            jSONObject.put("rpc_id", str);
            JSONObject jSONObject2 = new JSONObject(str5);
            jSONObject.put("data", jSONObject2);
            jSONObject2.put("app_id", str2);
            jSONObject2.put("room_id", str3);
            jSONObject2.put("user_id", str4);
            d(str, jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void e(String str, String str2, String str3, String str4, String str5) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("method", c.i.f2287r);
            jSONObject.put("version", 1.0d);
            jSONObject.put("rpc_id", str);
            JSONObject jSONObject2 = new JSONObject(str5);
            jSONObject.put("data", jSONObject2);
            jSONObject2.put("app_id", str2);
            jSONObject2.put("room_id", str3);
            jSONObject2.put("user_id", str4);
            d(str, jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void b(String str, String str2, String str3, String str4) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("method", "ping");
            jSONObject.put("version", 1.1d);
            jSONObject.put("rpc_id", str);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject.put("data", jSONObject2);
            jSONObject2.put("app_id", str2);
            jSONObject2.put("room_id", str3);
            jSONObject2.put("user_id", str4);
            d(str, jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void d(String str, String str2, String str3, String str4) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("method", c.i.f2291v);
            jSONObject.put("version", 1);
            jSONObject.put("rpc_id", str);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject.put("data", jSONObject2);
            jSONObject2.put("app_id", str2);
            jSONObject2.put("room_id", str3);
            jSONObject2.put("user_id", str4);
            d(str, jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void c(String str, String str2, String str3, String str4) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("method", c.i.f2283n);
            jSONObject.put("version", 1.0d);
            jSONObject.put("rpc_id", str);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("app_id", str3);
            jSONObject2.put("room_id", str2);
            jSONObject2.put("user_id", str4);
            jSONObject.put("data", jSONObject2);
            d(str, jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void a(String str, String str2, int i2) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject.put("method", c.i.f2272c);
            jSONObject.put("rpc_id", str);
            jSONObject.put("version", 1.0d);
            jSONObject.put("cmdtype", i2);
            jSONObject2.put("room_id", str2);
            jSONObject.put("data", jSONObject2);
            d(str, jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void a(String str, String str2, int i2, boolean z2) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject.put("method", c.i.f2272c);
            jSONObject.put("rpc_id", str);
            jSONObject.put("version", 1.0d);
            jSONObject.put("cmdtype", i2);
            jSONObject2.put("room_id", str2);
            jSONObject.put("data", jSONObject2);
            a(str, jSONObject.toString(), z2);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void a(String str, String str2, int i2, boolean z2, boolean z3, int i3, int i4) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject.put("method", c.i.f2273d);
            jSONObject.put("rpc_id", str);
            jSONObject.put("version", 1.0d);
            jSONObject2.put("user_id", str2);
            jSONObject2.put("media_type", i2);
            jSONObject2.put("audio", z2);
            jSONObject2.put("video", z3);
            jSONObject2.put("data", false);
            jSONObject2.put("vp", i3);
            String str3 = "vp8";
            if (i4 != 1 && i4 == 0) {
                str3 = "h264";
            }
            jSONObject2.put("payload", str3);
            jSONObject.put("data", jSONObject2);
            d(str, jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void a(String str, String str2, String str3, int i2) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject.put("method", c.i.f2274e);
            jSONObject.put("rpc_id", str);
            jSONObject.put("version", 1.0d);
            jSONObject2.put("user_id", str2);
            jSONObject2.put("stream_id", str3);
            jSONObject2.put("media_type", i2);
            jSONObject.put("data", jSONObject2);
            d(str, jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void a(String str, String str2, String str3, int i2, boolean z2) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject.put("method", c.i.f2274e);
            jSONObject.put("rpc_id", str);
            jSONObject.put("version", 1.0d);
            jSONObject2.put("user_id", str2);
            jSONObject2.put("stream_id", str3);
            jSONObject2.put("media_type", i2);
            jSONObject.put("data", jSONObject2);
            a(str, jSONObject.toString(), z2);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void a(String str, String str2, String str3, String str4, int i2, boolean z2, boolean z3) throws JSONException {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObject3 = new JSONObject();
            JSONObject jSONObject4 = new JSONObject();
            jSONObject.put("method", c.i.f2277h);
            jSONObject.put("rpc_id", str);
            jSONObject.put("version", 1.0d);
            jSONObject3.put("user_id", str2);
            jSONObject3.put("audio", z2);
            jSONObject3.put("video", z3);
            jSONObject3.put("data", false);
            jSONObject2.put("src", jSONObject3);
            jSONObject4.put("user_id", str3);
            jSONObject4.put("stream_id", str4);
            jSONObject4.put("media_type", i2);
            jSONObject2.put("dst", jSONObject4);
            jSONObject.put("data", jSONObject2);
        } catch (JSONException e2) {
            e = e2;
        }
        try {
            d(str, jSONObject.toString());
        } catch (JSONException e3) {
            e = e3;
            e.printStackTrace();
        }
    }

    public void a(String str, String str2, String str3, String str4, String str5, int i2) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObject3 = new JSONObject();
            JSONObject jSONObject4 = new JSONObject();
            jSONObject.put("method", c.i.f2278i);
            jSONObject.put("rpc_id", str);
            jSONObject.put("version", 1.0d);
            jSONObject3.put("user_id", str2);
            jSONObject3.put("streamsub_id", str5);
            jSONObject2.put("src", jSONObject3);
            jSONObject4.put("user_id", str3);
            jSONObject4.put("stream_id", str4);
            jSONObject4.put("media_type", i2);
            jSONObject2.put("dst", jSONObject4);
            jSONObject.put("data", jSONObject2);
            d(str, jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void a(String str, String str2, String str3, String str4, String str5, int i2, boolean z2) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObject3 = new JSONObject();
            JSONObject jSONObject4 = new JSONObject();
            jSONObject.put("method", c.i.f2278i);
            jSONObject.put("rpc_id", str);
            jSONObject.put("version", 1.0d);
            jSONObject3.put("user_id", str2);
            jSONObject3.put("streamsub_id", str4);
            jSONObject2.put("src", jSONObject3);
            jSONObject4.put("user_id", str3);
            jSONObject4.put("stream_id", str5);
            jSONObject4.put("media_type", i2);
            jSONObject2.put("dst", jSONObject4);
            jSONObject.put("data", jSONObject2);
            a(str, jSONObject.toString(), z2);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void a(String str, String str2, String str3, int i2, int i3, boolean z2) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject.put("method", c.i.f2279j);
            jSONObject.put("rpc_id", str);
            jSONObject.put("version", 1.0d);
            jSONObject2.put("user_id", str2);
            jSONObject2.put("stream_id", str3);
            jSONObject2.put("stream_type", i2);
            jSONObject2.put("track_type", i3);
            jSONObject2.put(c.i.f2279j, z2);
            jSONObject.put("data", jSONObject2);
            d(str, jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void a(String str, String str2, String str3, int i2, int i3, String str4, String str5, int i4, int i5) throws JSONException {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObject3 = new JSONObject();
            JSONObject jSONObject4 = new JSONObject();
            jSONObject.put("method", "sdp");
            jSONObject.put("rpc_id", str);
            jSONObject.put("version", 1.0d);
            jSONObject3.put("user_id", str2);
            jSONObject3.put("stream_id", str3);
            jSONObject3.put("stream_type", i2);
            jSONObject3.put("media_type", i3);
            jSONObject2.put("src", jSONObject3);
            jSONObject4.put("type", str4);
            jSONObject4.put("sdpcontent", str5);
            jSONObject4.put("minbitrate", i4);
            jSONObject4.put("maxbitrate", i5);
            jSONObject2.put("sdp", jSONObject4);
            jSONObject.put("data", jSONObject2);
        } catch (JSONException e2) {
            e = e2;
        }
        try {
            d(str, jSONObject.toString());
        } catch (JSONException e3) {
            e = e3;
            e.printStackTrace();
        }
    }

    public void a(String str, String str2, String str3, int i2, String str4, String str5, int i3, String str6, String str7, int i4, int i5, boolean z2, int i6, int i7, String str8, int i8) throws JSONException {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject();
            jSONObject.put("method", c.i.f2282m);
            jSONObject.put("version", 1.0d);
            jSONObject.put("rpc_id", str);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("room_id", str2);
            jSONObject2.put("app_id", str3);
            jSONObject2.put("user_id", str4);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("mimetype", i2);
            jSONObject3.put("mainviewuid", str5);
            jSONObject3.put("mainviewtype", i3);
            jSONObject3.put("width", i4);
            jSONObject3.put("height", i5);
            jSONObject3.put("bucket", str6);
            jSONObject3.put(TtmlNode.TAG_REGION, str7);
            jSONObject3.put("isaverage", z2);
            jSONObject3.put("waterpos", i6);
            jSONObject3.put("watertype", i7);
            jSONObject3.put("waterurl", str8);
            jSONObject3.put("wtemplate", i8);
            jSONObject2.put("config", jSONObject3);
            jSONObject.put("data", jSONObject2);
        } catch (JSONException e2) {
            e = e2;
        }
        try {
            d(str, jSONObject.toString());
        } catch (JSONException e3) {
            e = e3;
            e.printStackTrace();
        }
    }

    public void a(String str, String str2, String str3, String str4) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("method", c.i.f2285p);
            jSONObject.put("version", 1.0d);
            jSONObject.put("rpc_id", str);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject.put("data", jSONObject2);
            jSONObject2.put("app_id", str2);
            jSONObject2.put("room_id", str3);
            jSONObject2.put("user_id", str4);
            d(str, jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void a(String str, String str2, String str3, String str4, String str5) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("method", c.i.f2288s);
            jSONObject.put("version", 1.0d);
            jSONObject.put("rpc_id", str);
            JSONObject jSONObject2 = new JSONObject(str5);
            jSONObject.put("data", jSONObject2);
            jSONObject2.put("app_id", str2);
            jSONObject2.put("room_id", str3);
            jSONObject2.put("user_id", str4);
            d(str, jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void a(String str, int i2, j jVar) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("method", c.i.f2281l);
            jSONObject.put("rpc_id", str);
            jSONObject.put("version", 1.0d);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("tag", "android");
            jSONObject2.put("uid", jVar.o());
            jSONObject2.put(CommonNetImpl.AID, jVar.a());
            jSONObject2.put("rid", jVar.i());
            jSONObject2.put("streamid", jVar.k());
            jSONObject2.put("type", i2);
            jSONObject2.put("ts", System.currentTimeMillis() / 1000);
            jSONObject2.put(CommonNetImpl.STYPE, jVar.l());
            jSONObject2.put("mtype", jVar.l());
            jSONObject2.put("ttype", jVar.m());
            jSONObject2.put("br", jVar.b());
            jSONObject2.put("lostpre", jVar.f());
            jSONObject2.put(IMediaFormat.KEY_MIME, jVar.g());
            jSONObject2.put("rttms", jVar.j());
            jSONObject2.put("delay", jVar.j());
            jSONObject2.put("w", jVar.q());
            jSONObject2.put("h", jVar.e());
            jSONObject2.put("frt", jVar.d());
            jSONObject2.put("vol", jVar.p());
            jSONObject.put("data", jSONObject2);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void a(String str, String str2, String str3, String str4, int i2, JSONArray jSONArray) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("method", c.i.f2290u);
            jSONObject.put("version", 1.0d);
            jSONObject.put("rpc_id", str);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject.put("data", jSONObject2);
            jSONObject2.put("app_id", str2);
            jSONObject2.put("room_id", str3);
            jSONObject2.put("user_id", str4);
            jSONObject2.put("cmdtype", i2);
            jSONObject2.put("users", jSONArray);
            d(str, jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void a(String str, String str2, String str3, String str4, String str5, int i2, int i3, int i4) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("method", c.i.f2292w);
            jSONObject.put("version", 1);
            jSONObject.put("rpc_id", str);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject.put("data", jSONObject2);
            jSONObject2.put("app_id", str2);
            jSONObject2.put("room_id", str3);
            jSONObject2.put("user_id", str4);
            jSONObject2.put("stream_id", str5);
            jSONObject2.put("vp", i2);
            jSONObject2.put("maxbitrate", i3);
            jSONObject2.put("minbitrate", i4);
            d(str, jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }
}

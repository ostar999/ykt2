package g;

import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.alibaba.fastjson.parser.JSONLexer;
import com.aliyun.auth.core.AliyunVodKey;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.hyphenate.easeui.constants.EaseConstant;
import com.umeng.analytics.pro.am;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import core.monitor.LogReportManager;
import d.a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes8.dex */
public class e implements g.a {
    public static final String G = "SignalClientMsgModule";

    /* renamed from: a, reason: collision with root package name */
    public e.h f26944a;

    /* renamed from: b, reason: collision with root package name */
    public e.f f26945b = new k();

    /* renamed from: c, reason: collision with root package name */
    public e.f f26946c = new v();

    /* renamed from: d, reason: collision with root package name */
    public e.f f26947d = new y();

    /* renamed from: e, reason: collision with root package name */
    public e.f f26948e = new z();

    /* renamed from: f, reason: collision with root package name */
    public e.f f26949f = new a0();

    /* renamed from: g, reason: collision with root package name */
    public e.f f26950g = new b0();

    /* renamed from: h, reason: collision with root package name */
    public e.f f26951h = new c0();

    /* renamed from: i, reason: collision with root package name */
    public e.f f26952i = new d0();

    /* renamed from: j, reason: collision with root package name */
    public e.f f26953j = new e0();

    /* renamed from: k, reason: collision with root package name */
    public e.f f26954k = new a();

    /* renamed from: l, reason: collision with root package name */
    public e.f f26955l = new b();

    /* renamed from: m, reason: collision with root package name */
    public e.f f26956m = new c();

    /* renamed from: n, reason: collision with root package name */
    public e.f f26957n = new d();

    /* renamed from: o, reason: collision with root package name */
    public e.f f26958o = new C0450e();

    /* renamed from: p, reason: collision with root package name */
    public e.f f26959p = new f();

    /* renamed from: q, reason: collision with root package name */
    public e.f f26960q = new g();

    /* renamed from: r, reason: collision with root package name */
    public e.f f26961r = new h();

    /* renamed from: s, reason: collision with root package name */
    public e.f f26962s = new i();

    /* renamed from: t, reason: collision with root package name */
    public e.f f26963t = new j();

    /* renamed from: u, reason: collision with root package name */
    public e.f f26964u = new l();

    /* renamed from: v, reason: collision with root package name */
    public e.f f26965v = new m();

    /* renamed from: w, reason: collision with root package name */
    public e.f f26966w = new n();

    /* renamed from: x, reason: collision with root package name */
    public e.f f26967x = new o();

    /* renamed from: y, reason: collision with root package name */
    public e.f f26968y = new p();

    /* renamed from: z, reason: collision with root package name */
    public e.f f26969z = new q();
    public e.f A = new r();
    public e.f B = new s();
    public e.f C = new t();
    public e.f D = new u();
    public e.f E = new w();
    public e.f F = new x();

    public class a implements e.f {
        public a() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            c.h.a(e.G, "onSubStreamHandler " + str);
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                String string = jSONObject.getString("msg");
                c.h.a(e.G, "onSubStreamHandler err " + i2);
                c.h.a(e.G, "onSubStreamHandler msgdesc " + string);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                String string2 = jSONObject2.getString("stream_id");
                int i3 = jSONObject2.getInt("media_type");
                String string3 = jSONObject2.getString("user_id");
                if (i2 != 0) {
                    e.this.f26944a.D().remove(string2);
                    c.h.a(e.G, "onSubStreamHandler call back ");
                    if (e.this.f26944a.h() != null) {
                        c.h.a(e.G, "onSubStreamHandler call back begin");
                        JSONObject jSONObject3 = new JSONObject();
                        JSONObject jSONObject4 = new JSONObject();
                        jSONObject3.put("code", i2);
                        jSONObject3.put("msg", string);
                        jSONObject4.put("uid", string3);
                        jSONObject4.put("mtype", i3);
                        jSONObject3.put("data", jSONObject4);
                        c.h.a(e.G, "onSubStreamHandler call back end");
                        e.this.f26944a.h().H(jSONObject3.toString());
                    }
                    String string4 = jSONObject2.has("streamsub_id") ? jSONObject2.getString("streamsub_id") : "";
                    e.d dVar = e.this.f26944a.D().get(string2);
                    LogReportManager.getInstance().sendErrorSubStreamMsg(e.this.f26944a, 2, string4, string2, dVar != null ? dVar.f26761c : "", 2, dVar != null ? dVar.f26763e : 0);
                    return;
                }
                String string5 = jSONObject2.getString("streamsub_id");
                e.d dVar2 = e.this.f26944a.D().get(string2);
                c.h.a(e.G, " streamid " + string2);
                c.h.a(e.G, " streamsubid " + string5 + " sclient " + dVar2);
                if (dVar2 != null) {
                    dVar2.f26759a = string5;
                    dVar2.f26764f = f.j.STREAM_STATUS_NEWPEER;
                    c.h.a(e.G, " audio " + dVar2.f26766h + " video " + dVar2.f26765g);
                    e.this.f26944a.a(string5, 2, i3, dVar2.f26766h, dVar2.f26765g, e.this.f26944a);
                    j.d.d().f(string5);
                    e.this.f26944a.E().put(string5, string2);
                    ArrayList<String> arrayList = new ArrayList();
                    for (String str2 : e.this.f26944a.E().keySet()) {
                        String str3 = e.this.f26944a.E().get(str2);
                        c.h.a(e.G, "SUB KEY: " + str2 + " value: " + str3);
                        if (str3.equals(string2) && !str2.equals(string5)) {
                            arrayList.add(str2);
                        }
                    }
                    for (String str4 : arrayList) {
                        c.h.a(e.G, "sub clear subpub map KEY: " + str4);
                        e.this.f26944a.E().remove(str4);
                    }
                    new JSONObject().put("opertionType", 5);
                    f.d dVarB = e.this.f26944a.y().b(string2);
                    String strD = dVarB.d();
                    j.d.d().a(string5, 1, dVarB.h());
                    j.d.d().a(string5, 2, dVarB.i());
                    LogReportManager.getInstance().sendOpSubStreamMsg(e.this.f26944a, 5, string5, string2, strD, 2, i3);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }

        @Override // e.f
        public void b(String str, Object obj) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                jSONObject2.getJSONObject("data");
                JSONObject jSONObject3 = jSONObject2.getJSONObject("dst");
                int i2 = jSONObject3.getInt("media_type");
                String string = jSONObject3.getString("user_id");
                String string2 = jSONObject3.getString("stream_id");
                e.this.f26944a.D().remove(string2);
                e.this.f26944a.c(string2, 2, i2);
                JSONObject jSONObject4 = new JSONObject();
                JSONObject jSONObject5 = new JSONObject();
                jSONObject4.put("code", jSONObject.getInt("code"));
                jSONObject4.put("msg", "msg wait response timeout");
                jSONObject5.put("uid", string);
                jSONObject5.put("mtype", i2);
                jSONObject4.put("data", jSONObject5);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().H(jSONObject4.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class a0 implements e.f {
        public a0() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:22:0x0113 A[Catch: JSONException -> 0x017c, TryCatch #0 {JSONException -> 0x017c, blocks: (B:3:0x0002, B:5:0x0023, B:7:0x002f, B:9:0x0058, B:20:0x00d5, B:22:0x0113, B:24:0x0122, B:26:0x0138, B:28:0x0140, B:30:0x0147, B:29:0x0143, B:23:0x011d, B:13:0x007c, B:15:0x0088, B:17:0x00b1), top: B:35:0x0002 }] */
        /* JADX WARN: Removed duplicated region for block: B:23:0x011d A[Catch: JSONException -> 0x017c, TryCatch #0 {JSONException -> 0x017c, blocks: (B:3:0x0002, B:5:0x0023, B:7:0x002f, B:9:0x0058, B:20:0x00d5, B:22:0x0113, B:24:0x0122, B:26:0x0138, B:28:0x0140, B:30:0x0147, B:29:0x0143, B:23:0x011d, B:13:0x007c, B:15:0x0088, B:17:0x00b1), top: B:35:0x0002 }] */
        /* JADX WARN: Removed duplicated region for block: B:26:0x0138 A[Catch: JSONException -> 0x017c, TryCatch #0 {JSONException -> 0x017c, blocks: (B:3:0x0002, B:5:0x0023, B:7:0x002f, B:9:0x0058, B:20:0x00d5, B:22:0x0113, B:24:0x0122, B:26:0x0138, B:28:0x0140, B:30:0x0147, B:29:0x0143, B:23:0x011d, B:13:0x007c, B:15:0x0088, B:17:0x00b1), top: B:35:0x0002 }] */
        /* JADX WARN: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
        @Override // e.f
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void a(java.lang.String r11, java.lang.Object r12) throws org.json.JSONException {
            /*
                Method dump skipped, instructions count: 385
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: g.e.a0.a(java.lang.String, java.lang.Object):void");
        }

        @Override // e.f
        public void b(String str, Object obj) {
        }
    }

    public class b implements e.f {
        public b() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            String strA;
            c.h.a(e.G, "onUnSubStreamHandler " + str);
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                String string = jSONObject.getString("msg");
                c.h.a(e.G, "onUnSubStreamHandler err " + i2);
                c.h.a(e.G, "onUnSubStreamHandler msgdesc " + string);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                String string2 = jSONObject2.getString("user_id");
                int i3 = jSONObject2.getInt("media_type");
                if (jSONObject2.has("stream_id")) {
                    strA = jSONObject2.getString("stream_id");
                } else {
                    strA = e.this.f26944a.y().a(string2 + a.h.a(i3));
                }
                c.h.a(e.G, "onUnSubStreamHandler streamid " + strA);
                if (!TextUtils.isEmpty(strA)) {
                    e.this.f26944a.c(strA, 2, i3);
                }
                if (i2 != 0) {
                    if (e.this.f26944a.h() != null) {
                        JSONObject jSONObject3 = new JSONObject();
                        JSONObject jSONObject4 = new JSONObject();
                        jSONObject3.put("code", i2);
                        jSONObject3.put("msg", string);
                        jSONObject4.put("uid", string2);
                        jSONObject4.put("mtype", i3);
                        jSONObject3.put("data", jSONObject4);
                        e.this.f26944a.h().C(jSONObject3.toString());
                        return;
                    }
                    return;
                }
                if (e.this.f26944a.h() != null) {
                    JSONObject jSONObject5 = new JSONObject();
                    JSONObject jSONObject6 = new JSONObject();
                    jSONObject5.put("code", 0);
                    jSONObject5.put("msg", "");
                    jSONObject6.put("uid", string2);
                    jSONObject6.put("mtype", i3);
                    jSONObject5.put("data", jSONObject6);
                    e.this.f26944a.h().C(jSONObject5.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }

        @Override // e.f
        public void b(String str, Object obj) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data").getJSONObject("data").getJSONObject("dst");
                int i2 = jSONObject2.getInt("media_type");
                String string = jSONObject2.getString("user_id");
                String string2 = jSONObject2.getString("stream_id");
                e.this.f26944a.D().remove(string2);
                e.this.f26944a.c(string2, 2, i2);
                JSONObject jSONObject3 = new JSONObject();
                JSONObject jSONObject4 = new JSONObject();
                jSONObject3.put("code", jSONObject.getInt("code"));
                jSONObject3.put("msg", "msg wait response timeout");
                jSONObject4.put("uid", string);
                jSONObject4.put("mtype", i2);
                jSONObject3.put("data", jSONObject4);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().C(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class b0 implements e.f {
        public b0() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            c.h.a(e.G, " onLeaveRoomHandler " + str);
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                String string = jSONObject.getString("msg");
                if (jSONObject.has("cmdtype")) {
                    jSONObject.getInt("cmdtype");
                }
                if (e.this.f26944a.m() != null) {
                    e.this.f26944a.m().e();
                }
                e.this.f26944a.a((h.f) null);
                e.this.f26944a.a(f.h.LOGIC_ENGINE_INIT);
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("code", i2);
                jSONObject3.put("msg", string);
                jSONObject3.put("roomid", jSONObject2.getString("room_id"));
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().z(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }

        @Override // e.f
        public void b(String str, Object obj) throws JSONException {
            c.h.a(e.G, " onLeaveRoomHandler " + str);
            try {
                if (e.this.f26944a.m() != null) {
                    e.this.f26944a.m().e();
                }
                e.this.f26944a.a((h.f) null);
                JSONObject jSONObject = new JSONObject(str);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data").getJSONObject("data");
                e.this.f26944a.a(f.h.LOGIC_ENGINE_INIT);
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("code", jSONObject.getInt("code"));
                jSONObject3.put("msg", "msg wait response timeout");
                jSONObject3.put("roomid", jSONObject2.getString("room_id"));
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().z(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class c implements e.f {
        public c() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            c.h.a(e.G, " muteLocalAudioHandler ");
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                String string = jSONObject.getString("msg");
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                String string2 = jSONObject2.getString("stream_id");
                boolean z2 = jSONObject2.getBoolean(c.i.f2279j);
                if (e.this.f26944a.v() == null || !e.this.f26944a.v().f26759a.equals(string2)) {
                    return;
                }
                if (i2 == 0) {
                    LogReportManager.getInstance().sendOpStreamMsg(e.this.f26944a, z2 ? 10 : 9, string2, 1, e.this.f26944a.v().f26763e);
                }
                j.d.d().e(z2);
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("code", i2);
                jSONObject3.put("msg", string);
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("mtype", 1);
                jSONObject4.put("ttype", 1);
                jSONObject4.put(c.i.f2279j, z2);
                jSONObject3.put("data", jSONObject4);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().s(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }

        @Override // e.f
        public void b(String str, Object obj) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data").getJSONObject("data");
                int i2 = jSONObject2.getInt("media_type");
                int i3 = jSONObject2.getInt("track_type");
                jSONObject2.getInt("stream_type");
                String string = jSONObject2.getString("user_id");
                String string2 = jSONObject2.getString("stream_id");
                boolean z2 = jSONObject2.getBoolean(c.i.f2279j);
                e.this.f26944a.D().remove(string2);
                e.this.f26944a.c(string2, 2, i2);
                JSONObject jSONObject3 = new JSONObject();
                JSONObject jSONObject4 = new JSONObject();
                jSONObject3.put("code", jSONObject.getInt("code"));
                jSONObject3.put("msg", "msg wait response timeout");
                jSONObject4.put("uid", string);
                jSONObject4.put("mtype", i2);
                jSONObject4.put("ttype", i3);
                jSONObject4.put(c.i.f2279j, z2);
                jSONObject3.put("data", jSONObject4);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().s(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class c0 implements e.f {
        public c0() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            boolean z2;
            boolean z3;
            boolean z4;
            boolean z5;
            boolean z6;
            c.h.a(e.G, "onPublishHandler " + str);
            try {
                if (e.this.f26944a.z() == f.h.LOGIC_ENGINE_ROOM_CLOSED) {
                    c.h.a(e.G, "publish when room closed");
                    return;
                }
                JSONObject jSONObject = new JSONObject(str);
                int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                String string = jSONObject.getString("msg");
                c.h.a(e.G, "onPublishHandler err " + i2);
                c.h.a(e.G, "onPublishHandler msgdesc " + string);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                boolean z7 = false;
                if (i2 != 0) {
                    int i3 = jSONObject2.getInt("media_type");
                    e.this.f26944a.a(i3, true);
                    if (i3 != 1 || e.this.f26944a.v() == null) {
                        z6 = false;
                    } else {
                        z7 = e.this.f26944a.v().f26766h;
                        z6 = e.this.f26944a.v().f26765g;
                        e.this.f26944a.a((e.d) null);
                        c.h.a(e.G, "onPublishHandler setCamClient null for err != 0");
                    }
                    if (i3 == 2 && e.this.f26944a.B() != null) {
                        z7 = e.this.f26944a.B().f26766h;
                        z6 = e.this.f26944a.B().f26765g;
                        e.this.f26944a.b((e.d) null);
                        c.h.a(e.G, "onPublishHandler setScreenClient null for err != 0");
                    }
                    c.h.a(e.G, "onPublishHandler call back ");
                    if (e.this.f26944a.h() != null) {
                        c.h.a(e.G, "onPublishHandler call back begin");
                        JSONObject jSONObject3 = new JSONObject();
                        JSONObject jSONObject4 = new JSONObject();
                        jSONObject3.put("code", i2);
                        jSONObject3.put("msg", string);
                        jSONObject4.put("uid", e.this.f26944a.u().getUId());
                        jSONObject4.put("mtype", i3);
                        jSONObject4.put("audio", z7);
                        jSONObject4.put("video", z6);
                        jSONObject3.put("data", jSONObject4);
                        c.h.a(e.G, "onPublishHandler call back end");
                        e.this.f26944a.h().k(jSONObject3.toString());
                    }
                    LogReportManager.getInstance().sendErrorStreamMsg(e.this.f26944a, 1, "", 1, i3);
                    return;
                }
                String string2 = jSONObject2.getString("stream_id");
                int i4 = jSONObject2.getInt("media_type");
                if (i4 == 1) {
                    if (e.this.f26944a.v() != null) {
                        z2 = e.this.f26944a.v().f26766h;
                        z3 = e.this.f26944a.v().f26765g;
                        e.this.f26944a.v().f26759a = string2;
                        e.this.f26944a.v().f26769k = false;
                        e.this.f26944a.v().f26764f = f.j.STREAM_STATUS_NEWPEER;
                        z5 = z2;
                        z4 = z3;
                    }
                    z4 = false;
                    z5 = true;
                } else {
                    if (i4 == 2 && e.this.f26944a.B() != null) {
                        z2 = e.this.f26944a.B().f26766h;
                        z3 = e.this.f26944a.B().f26765g;
                        e.this.f26944a.B().f26769k = false;
                        e.this.f26944a.B().f26759a = string2;
                        e.this.f26944a.B().f26764f = f.j.STREAM_STATUS_NEWPEER;
                        z5 = z2;
                        z4 = z3;
                    }
                    z4 = false;
                    z5 = true;
                }
                if (e.this.f26944a.v() == null && e.this.f26944a.B() == null) {
                    c.h.a(e.G, "publish cancel when either camera client or screenclient is null");
                    return;
                }
                e.this.f26944a.a(string2, 1, i4, z5, z4, e.this.f26944a);
                j.d.d().f(string2);
                if (i4 == 1) {
                    e.this.f26944a.v().f26764f = f.j.STREAM_STATUS_PREOFFER;
                } else if (i4 == 2) {
                    e.this.f26944a.B().f26764f = f.j.STREAM_STATUS_PREOFFER;
                }
                e.this.a(string2, 1, i4, 3);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }

        @Override // e.f
        public void b(String str, Object obj) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data").getJSONObject("data");
                int i2 = jSONObject2.getInt("media_type");
                String string = jSONObject2.getString("user_id");
                if (i2 == 1) {
                    e.this.f26944a.a((e.d) null);
                    c.h.a(e.G, "onPublishHandler setCamClient null for onFail");
                } else if (i2 == 2) {
                    e.this.f26944a.b((e.d) null);
                }
                JSONObject jSONObject3 = new JSONObject();
                JSONObject jSONObject4 = new JSONObject();
                jSONObject3.put("code", jSONObject.getInt("ocde"));
                jSONObject3.put("msg", "msg wait response timeout");
                jSONObject4.put("uid", string);
                jSONObject4.put("mtype", i2);
                jSONObject3.put("data", jSONObject4);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().k(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class d implements e.f {
        public d() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                String string = jSONObject.getString("msg");
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                String string2 = jSONObject2.getString("stream_id");
                boolean z2 = jSONObject2.getBoolean(c.i.f2279j);
                if (e.this.f26944a.B() == null || !e.this.f26944a.B().f26759a.equals(string2)) {
                    return;
                }
                j.d.d().e(z2);
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("code", i2);
                jSONObject3.put("msg", string);
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("mtype", 2);
                jSONObject4.put("ttype", 1);
                jSONObject4.put(c.i.f2279j, z2);
                jSONObject3.put("data", jSONObject4);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().s(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }

        @Override // e.f
        public void b(String str, Object obj) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data").getJSONObject("data");
                int i2 = jSONObject2.getInt("media_type");
                int i3 = jSONObject2.getInt("track_type");
                jSONObject2.getInt("stream_type");
                String string = jSONObject2.getString("user_id");
                String string2 = jSONObject2.getString("stream_id");
                boolean z2 = jSONObject2.getBoolean(c.i.f2279j);
                e.this.f26944a.D().remove(string2);
                e.this.f26944a.c(string2, 2, i2);
                JSONObject jSONObject3 = new JSONObject();
                JSONObject jSONObject4 = new JSONObject();
                jSONObject3.put("code", jSONObject.getInt("code"));
                jSONObject3.put("msg", "msg wait response timeout");
                jSONObject4.put("uid", string);
                jSONObject4.put("mtype", i2);
                jSONObject4.put("ttype", i3);
                jSONObject4.put(c.i.f2279j, z2);
                jSONObject3.put("data", jSONObject4);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().s(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class d0 implements e.f {
        public d0() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            c.h.a(e.G, "onUnPublishHandler " + str);
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                String string = jSONObject.getString("msg");
                c.h.a(e.G, "onUnPublishHandler err " + i2);
                c.h.a(e.G, "onUnPublishHandler msgdesc " + string);
                String str2 = "";
                int i3 = jSONObject.getJSONObject("data").getInt("media_type");
                if (i3 == 1 && e.this.f26944a.v() != null) {
                    str2 = e.this.f26944a.v().f26759a;
                    e.this.f26944a.v().f26764f = f.j.STREAM_STATUS_IDLE;
                    c.h.a(e.G, "onUnPublishHandler setCamClient to STREAM_STATUS_IDLE");
                    e.this.f26944a.d(str2);
                } else if (i3 != 2 || e.this.f26944a.B() == null) {
                    c.h.a(e.G, "CameraClient or ScreenClient is already released.");
                } else {
                    str2 = e.this.f26944a.B().f26759a;
                    e.this.f26944a.B().f26764f = f.j.STREAM_STATUS_IDLE;
                    c.h.a(e.G, "onUnPublishHandler setScreenClient to STREAM_STATUS_IDLE");
                    e.this.f26944a.d(str2);
                }
                if (e.this.f26944a.h() != null) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("code", i2);
                    jSONObject2.put("msg", string);
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("mtype", i3);
                    jSONObject2.put("data", jSONObject3);
                    e.this.f26944a.h().D(jSONObject2.toString());
                }
                e.this.a(str2, 1, i3, 4);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }

        @Override // e.f
        public void b(String str, Object obj) throws JSONException {
            c.h.a(e.G, "onUnPublishHandler " + str);
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data").getJSONObject("data");
                int i2 = jSONObject2.getInt("media_type");
                String string = jSONObject2.getString("user_id");
                if (i2 == 1 && e.this.f26944a.v() != null) {
                    String str2 = e.this.f26944a.v().f26759a;
                    e.this.f26944a.a((e.d) null);
                    c.h.a(e.G, "onUnPublishHandler setCamClient to null");
                    e.this.f26944a.d(str2);
                } else if (i2 != 2 || e.this.f26944a.B() == null) {
                    c.h.a(e.G, "CameraClient or ScreenClient is already released.");
                } else {
                    String str3 = e.this.f26944a.B().f26759a;
                    e.this.f26944a.b((e.d) null);
                    c.h.a(e.G, "onUnPublishHandler setScreenClient to null");
                    e.this.f26944a.d(str3);
                }
                JSONObject jSONObject3 = new JSONObject();
                JSONObject jSONObject4 = new JSONObject();
                jSONObject3.put("code", jSONObject.getInt("code"));
                jSONObject3.put("msg", "msg wait response timeout");
                jSONObject4.put("uid", string);
                jSONObject4.put("mtype", i2);
                jSONObject3.put("data", jSONObject4);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().D(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: g.e$e, reason: collision with other inner class name */
    public class C0450e implements e.f {
        public C0450e() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            try {
                c.h.a(e.G, " muteLocalVideoHandler ");
                JSONObject jSONObject = new JSONObject(str);
                int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                String string = jSONObject.getString("msg");
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                String string2 = jSONObject2.getString("stream_id");
                boolean z2 = jSONObject2.getBoolean(c.i.f2279j);
                if (e.this.f26944a.v() == null || !e.this.f26944a.v().f26759a.equals(string2)) {
                    return;
                }
                if (i2 == 0) {
                    LogReportManager.getInstance().sendOpStreamMsg(e.this.f26944a, z2 ? 8 : 7, string2, 1, 1);
                }
                j.d.d().g(z2);
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("code", i2);
                jSONObject3.put("msg", string);
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("mtype", 1);
                jSONObject4.put("ttype", 2);
                jSONObject4.put(c.i.f2279j, z2);
                jSONObject3.put("data", jSONObject4);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().s(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }

        @Override // e.f
        public void b(String str, Object obj) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data").getJSONObject("data");
                int i2 = jSONObject2.getInt("media_type");
                int i3 = jSONObject2.getInt("track_type");
                jSONObject2.getInt("stream_type");
                String string = jSONObject2.getString("user_id");
                String string2 = jSONObject2.getString("stream_id");
                boolean z2 = jSONObject2.getBoolean(c.i.f2279j);
                e.this.f26944a.D().remove(string2);
                e.this.f26944a.c(string2, 2, i2);
                JSONObject jSONObject3 = new JSONObject();
                JSONObject jSONObject4 = new JSONObject();
                jSONObject3.put("code", jSONObject.getInt("code"));
                jSONObject3.put("msg", "msg wait response timeout");
                jSONObject4.put("uid", string);
                jSONObject4.put("mtype", i2);
                jSONObject4.put("ttype", i3);
                jSONObject4.put(c.i.f2279j, z2);
                jSONObject3.put("data", jSONObject4);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().s(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class e0 implements e.f {

        /* renamed from: a, reason: collision with root package name */
        public int f26979a = 2;

        /* renamed from: b, reason: collision with root package name */
        public int f26980b = 0;

        /* renamed from: c, reason: collision with root package name */
        public Map<String, Integer> f26981c = new HashMap();

        public e0() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                String string = jSONObject.getString("msg");
                if (i2 != 0) {
                    c.h.a(e.G, "sdp offer send receive error " + i2 + "msg: " + string);
                    return;
                }
                String string2 = jSONObject.getJSONObject("data").getString("stream_id");
                c.h.a(e.G, "onSendOfferHandler sdp received err = 0 streamId: " + string2);
                if (this.f26981c.containsKey(string2)) {
                    c.h.a(e.G, "onSendOfferHandler success clear streamId: " + string2);
                    this.f26981c.remove(string2);
                }
                e.this.b(str);
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }

        @Override // e.f
        public void b(String str, Object obj) throws JSONException {
            c.h.a(e.G, "send sdp not response");
            try {
                JSONObject jSONObject = new JSONObject(str).getJSONObject("data").getJSONObject("data");
                JSONObject jSONObject2 = jSONObject.getJSONObject("src");
                String string = jSONObject2.getString("stream_id");
                if (this.f26981c.containsKey(string)) {
                    this.f26980b = this.f26981c.get(string).intValue() + 1;
                    c.h.a(e.G, "send sdp failed streamId: " + string + " is in hash map sendOfferFailedTimes is: " + this.f26980b);
                } else {
                    c.h.a(e.G, "send sdp failed streamId: " + string + " is not in hash map");
                    this.f26980b = 1;
                }
                this.f26981c.put(string, Integer.valueOf(this.f26980b));
                if (this.f26980b < this.f26979a) {
                    JSONObject jSONObject3 = jSONObject.getJSONObject("sdp");
                    int i2 = jSONObject2.getInt("stream_type");
                    int i3 = jSONObject2.getInt("media_type");
                    String string2 = jSONObject3.getString("sdpcontent");
                    String string3 = jSONObject3.getString("type");
                    int i4 = jSONObject3.getInt("minbitrate");
                    int i5 = jSONObject3.getInt("maxbitrate");
                    String strA = c.e.a();
                    if (e.this.f26944a.m() == null || !(e.this.f26944a.m() instanceof h.e)) {
                        return;
                    }
                    ((h.e) e.this.f26944a.m()).a(strA, e.this.f26944a.u().getUId(), string, i2, i3, string3, string2, i4, i5);
                    e.this.f26944a.a(strA, e.this.f26953j);
                    return;
                }
                c.h.a(e.G, "send sdp failed " + this.f26979a + " times and report");
                if (e.this.f26944a.h() != null) {
                    int i6 = jSONObject2.getInt("media_type");
                    if (i6 == 1 && e.this.f26944a.v() != null) {
                        e.this.f26944a.v().f26770l = false;
                    } else if (i6 != 2 || e.this.f26944a.B() == null) {
                        c.h.a(e.G, "send sdp unknown media_type: " + i6);
                    } else {
                        e.this.f26944a.B().f26770l = false;
                    }
                    JSONObject jSONObject4 = new JSONObject();
                    jSONObject4.put("code", 5035);
                    e.this.f26944a.h().E(jSONObject4.toString());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }
    }

    public class f implements e.f {
        public f() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                String string = jSONObject.getString("msg");
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                String string2 = jSONObject2.getString("stream_id");
                boolean z2 = jSONObject2.getBoolean(c.i.f2279j);
                if (e.this.f26944a.B() == null || !e.this.f26944a.B().f26759a.equals(string2)) {
                    return;
                }
                j.d.d().f(z2);
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("code", i2);
                jSONObject3.put("msg", string);
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("mtype", 2);
                jSONObject4.put("ttype", 2);
                jSONObject4.put(c.i.f2279j, z2);
                jSONObject3.put("data", jSONObject4);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().s(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }

        @Override // e.f
        public void b(String str, Object obj) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data").getJSONObject("data");
                int i2 = jSONObject2.getInt("media_type");
                int i3 = jSONObject2.getInt("track_type");
                jSONObject2.getInt("stream_type");
                String string = jSONObject2.getString("user_id");
                String string2 = jSONObject2.getString("stream_id");
                boolean z2 = jSONObject2.getBoolean(c.i.f2279j);
                e.this.f26944a.D().remove(string2);
                e.this.f26944a.c(string2, 2, i2);
                JSONObject jSONObject3 = new JSONObject();
                JSONObject jSONObject4 = new JSONObject();
                jSONObject3.put("code", jSONObject.getInt("code"));
                jSONObject3.put("msg", "msg wait response timeout");
                jSONObject4.put("uid", string);
                jSONObject4.put("mtype", i2);
                jSONObject4.put("ttype", i3);
                jSONObject4.put(c.i.f2279j, z2);
                jSONObject3.put("data", jSONObject4);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().s(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class f0 extends c.i {
        public static final String M = "mute_local_audio";
        public static final String N = "mute_local_video";
        public static final String O = "mute_local_screen";
        public static final String P = "mute_local_screen_sound";
        public static final String Q = "mute_remote_video";
        public static final String R = "mute_remote_audio";
        public static final String S = "mute_remote_screen";
        public static final String T = "mute_remote_screen_sound";
        public static final String U = "rejoin";
        public static final String V = "bus_resub";
        public static final String W = "bus_repub_release";

        public f0() {
        }
    }

    public class g implements e.f {
        public g() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            e.d dVar;
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                String string = jSONObject.getString("msg");
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                String string2 = jSONObject2.getString("stream_id");
                String string3 = jSONObject2.getString("user_id");
                boolean z2 = jSONObject2.getBoolean(c.i.f2279j);
                String str2 = e.this.f26944a.E().get(string2);
                if (str2 == null || str2.equals("") || (dVar = e.this.f26944a.D().get(str2)) == null) {
                    return;
                }
                if (i2 == 0) {
                    j.d.d().c(string2, z2);
                }
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("code", i2);
                jSONObject3.put("msg", string);
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("uid", string3);
                jSONObject4.put("mtype", 1);
                jSONObject4.put("ttype", 1);
                jSONObject4.put(c.i.f2279j, z2);
                jSONObject3.put("data", jSONObject4);
                LogReportManager.getInstance().sendOpSubStreamMsg(e.this.f26944a, z2 ? 10 : 9, string2, str2, string3, 2, dVar.f26763e);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().e(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }

        @Override // e.f
        public void b(String str, Object obj) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data").getJSONObject("data");
                int i2 = jSONObject2.getInt("media_type");
                int i3 = jSONObject2.getInt("track_type");
                jSONObject2.getInt("stream_type");
                String string = jSONObject2.getString("user_id");
                String string2 = jSONObject2.getString("stream_id");
                boolean z2 = jSONObject2.getBoolean(c.i.f2279j);
                e.this.f26944a.D().remove(string2);
                e.this.f26944a.c(string2, 2, i2);
                JSONObject jSONObject3 = new JSONObject();
                JSONObject jSONObject4 = new JSONObject();
                jSONObject3.put("code", jSONObject.getInt("code"));
                jSONObject3.put("msg", "msg wait response timeout");
                jSONObject4.put("uid", string);
                jSONObject4.put("mtype", i2);
                jSONObject4.put("ttype", i3);
                jSONObject4.put(c.i.f2279j, z2);
                jSONObject3.put("data", jSONObject4);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().e(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class h implements e.f {
        public h() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                String string = jSONObject.getString("msg");
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                String string2 = jSONObject2.getString("stream_id");
                String string3 = jSONObject2.getString("user_id");
                boolean z2 = jSONObject2.getBoolean(c.i.f2279j);
                String str2 = e.this.f26944a.E().get(string2);
                if (str2 == null || str2.equals("") || e.this.f26944a.D().get(str2) == null) {
                    return;
                }
                if (i2 == 0) {
                    j.d.d().c(string2, z2);
                }
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("code", i2);
                jSONObject3.put("msg", string);
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("uid", string3);
                jSONObject4.put("mtype", 2);
                jSONObject4.put("ttype", 1);
                jSONObject4.put(c.i.f2279j, z2);
                jSONObject3.put("data", jSONObject4);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().e(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }

        @Override // e.f
        public void b(String str, Object obj) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data").getJSONObject("data");
                int i2 = jSONObject2.getInt("media_type");
                int i3 = jSONObject2.getInt("track_type");
                jSONObject2.getInt("stream_type");
                String string = jSONObject2.getString("user_id");
                String string2 = jSONObject2.getString("stream_id");
                boolean z2 = jSONObject2.getBoolean(c.i.f2279j);
                e.this.f26944a.D().remove(string2);
                e.this.f26944a.c(string2, 2, i2);
                JSONObject jSONObject3 = new JSONObject();
                JSONObject jSONObject4 = new JSONObject();
                jSONObject3.put("code", jSONObject.getInt("code"));
                jSONObject3.put("msg", "msg wait response timeout");
                jSONObject4.put("uid", string);
                jSONObject4.put("mtype", i2);
                jSONObject4.put("ttype", i3);
                jSONObject4.put(c.i.f2279j, z2);
                jSONObject3.put("data", jSONObject4);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().e(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class i implements e.f {
        public i() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                String string = jSONObject.getString("msg");
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                String string2 = jSONObject2.getString("stream_id");
                String string3 = jSONObject2.getString("user_id");
                boolean z2 = jSONObject2.getBoolean(c.i.f2279j);
                String str2 = e.this.f26944a.E().get(string2);
                if (str2 == null || str2.equals("") || e.this.f26944a.D().get(str2) == null) {
                    return;
                }
                if (i2 == 0) {
                    j.d.d().d(string2, z2);
                }
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("code", i2);
                jSONObject3.put("msg", string);
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("uid", string3);
                jSONObject4.put("mtype", 1);
                jSONObject4.put("ttype", 2);
                jSONObject4.put(c.i.f2279j, z2);
                jSONObject3.put("data", jSONObject4);
                LogReportManager.getInstance().sendOpSubStreamMsg(e.this.f26944a, z2 ? 8 : 7, string2, str2, string3, 2, 1);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().e(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }

        @Override // e.f
        public void b(String str, Object obj) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data").getJSONObject("data");
                int i2 = jSONObject2.getInt("media_type");
                int i3 = jSONObject2.getInt("track_type");
                jSONObject2.getInt("stream_type");
                String string = jSONObject2.getString("user_id");
                String string2 = jSONObject2.getString("stream_id");
                boolean z2 = jSONObject2.getBoolean(c.i.f2279j);
                e.this.f26944a.D().remove(string2);
                e.this.f26944a.c(string2, 2, i2);
                JSONObject jSONObject3 = new JSONObject();
                JSONObject jSONObject4 = new JSONObject();
                jSONObject3.put("code", jSONObject.getInt("code"));
                jSONObject3.put("msg", "msg wait response timeout");
                jSONObject4.put("uid", string);
                jSONObject4.put("mtype", i2);
                jSONObject4.put("ttype", i3);
                jSONObject4.put(c.i.f2279j, z2);
                jSONObject3.put("data", jSONObject4);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().e(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class j implements e.f {
        public j() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                String string = jSONObject.getString("msg");
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                String string2 = jSONObject2.getString("stream_id");
                String string3 = jSONObject2.getString("user_id");
                boolean z2 = jSONObject2.getBoolean(c.i.f2279j);
                String str2 = e.this.f26944a.E().get(string2);
                if (str2 == null || str2.equals("") || e.this.f26944a.D().get(str2) == null) {
                    return;
                }
                if (i2 == 0) {
                    j.d.d().d(string2, z2);
                }
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("code", i2);
                jSONObject3.put("msg", string);
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("uid", string3);
                jSONObject4.put("mtype", 2);
                jSONObject4.put("ttype", 2);
                jSONObject4.put(c.i.f2279j, z2);
                jSONObject3.put("data", jSONObject4);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().e(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }

        @Override // e.f
        public void b(String str, Object obj) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data").getJSONObject("data");
                int i2 = jSONObject2.getInt("media_type");
                int i3 = jSONObject2.getInt("track_type");
                jSONObject2.getInt("stream_type");
                String string = jSONObject2.getString("user_id");
                String string2 = jSONObject2.getString("stream_id");
                boolean z2 = jSONObject2.getBoolean(c.i.f2279j);
                e.this.f26944a.D().remove(string2);
                e.this.f26944a.c(string2, 2, i2);
                JSONObject jSONObject3 = new JSONObject();
                JSONObject jSONObject4 = new JSONObject();
                jSONObject3.put("code", jSONObject.getInt("code"));
                jSONObject3.put("msg", "msg wait response timeout");
                jSONObject4.put("uid", string);
                jSONObject4.put("mtype", i2);
                jSONObject4.put("ttype", i3);
                jSONObject4.put(c.i.f2279j, z2);
                jSONObject3.put("data", jSONObject4);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().e(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class k implements e.f {
        public k() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            c.h.a(e.G, "onjoinroomHandler " + str);
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                String string = jSONObject.getString("msg");
                if (i2 != 0) {
                    e.this.f26944a.a(f.h.LOGIC_ENGINE_INIT);
                    if (e.this.f26944a.h() != null) {
                        JSONObject jSONObject2 = new JSONObject();
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject2.put("code", i2);
                        jSONObject2.put("msg", string);
                        jSONObject3.put("uid", e.this.f26944a.u().getUId());
                        jSONObject3.put("roomid", e.this.f26944a.u().getRoomId());
                        jSONObject2.put("data", jSONObject3);
                        e.this.f26944a.h().F(jSONObject2.toString());
                        return;
                    }
                    return;
                }
                e.this.f26944a.a(f.h.LOGIC_ENGINE_CONNECTED);
                c.h.a(e.G, " set room state to RoomState.LOGIC_ENGINE_CONNECTED");
                if (e.this.f26944a.h() != null) {
                    c.h.a(e.G, "onjoinroomHandler call back begin");
                    JSONObject jSONObject4 = new JSONObject();
                    JSONObject jSONObject5 = new JSONObject();
                    jSONObject4.put("code", i2);
                    jSONObject4.put("msg", string);
                    jSONObject5.put("uid", e.this.f26944a.u().getUId());
                    jSONObject5.put("roomid", e.this.f26944a.u().getRoomId());
                    jSONObject4.put("data", jSONObject5);
                    c.h.a(e.G, "onjoinroomHandler call back end");
                    e.this.a();
                    e.this.f26944a.h().F(jSONObject4.toString());
                }
                JSONObject jSONObject6 = jSONObject.getJSONObject("data");
                if (e.this.f26944a.y() == null) {
                    e.this.f26944a.a(new f.c());
                }
                e.this.f26944a.y().a(jSONObject6.getInt("version"));
                e.this.f26944a.y().j(jSONObject6.getString("session_id"));
                JSONArray jSONArray = jSONObject6.getJSONArray("users");
                if (jSONArray != null) {
                    for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                        JSONObject jSONObject7 = jSONArray.getJSONObject(i3);
                        if (jSONObject7 != null) {
                            String string2 = jSONObject7.getString("user_id");
                            if (!string2.equals(e.this.f26944a.u().getUId())) {
                                f.g gVar = new f.g();
                                gVar.a(string2);
                                gVar.b("");
                                e.this.f26944a.y().a(gVar);
                            }
                        }
                    }
                }
                JSONArray jSONArray2 = jSONObject6.getJSONArray(IjkMediaMeta.IJKM_KEY_STREAMS);
                if (jSONArray2 != null) {
                    for (int i4 = 0; i4 < jSONArray2.length(); i4++) {
                        JSONObject jSONObject8 = jSONArray2.getJSONObject(i4);
                        if (jSONObject8 != null) {
                            String string3 = jSONObject8.getString("uid");
                            if (!string3.equals(e.this.f26944a.u().getUId())) {
                                f.d dVar = new f.d();
                                dVar.b(2);
                                dVar.b(string3);
                                dVar.a(jSONObject8.getString(SocializeProtocolConstants.PROTOCOL_KEY_SID));
                                dVar.a(jSONObject8.getBoolean("audio"));
                                dVar.c(jSONObject8.getBoolean("video"));
                                dVar.b(jSONObject8.getBoolean("data"));
                                dVar.a(jSONObject8.getInt("media_type"));
                                dVar.e(jSONObject8.getBoolean("mutevideo"));
                                dVar.d(jSONObject8.getBoolean("muteaudio"));
                                e.this.f26944a.y().a(dVar);
                            }
                        }
                    }
                }
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().q(jSONArray.toString());
                    e.this.f26944a.h().A(jSONArray2.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }

        @Override // e.f
        public void b(String str, Object obj) throws JSONException {
            try {
                String uId = e.this.f26944a.u().getUId();
                String roomId = e.this.f26944a.u().getRoomId();
                e.this.f26944a.I();
                if (e.this.f26944a.m() != null) {
                    e.this.f26944a.m().e();
                }
                e.this.f26944a.a((h.f) null);
                int i2 = new JSONObject(str).getInt("code");
                e.this.f26944a.a(f.h.LOGIC_ENGINE_INIT);
                if (e.this.f26944a.h() != null) {
                    JSONObject jSONObject = new JSONObject();
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject.put("code", i2);
                    jSONObject.put("msg", "msg wait response timeout");
                    jSONObject2.put("uid", uId);
                    jSONObject2.put("roomid", roomId);
                    jSONObject.put("data", jSONObject2);
                    e.this.f26944a.h().F(jSONObject.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class l implements e.f {
        public l() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            try {
                if (e.this.f26944a.h() != null) {
                    JSONObject jSONObject = new JSONObject(str);
                    int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                    String string = "";
                    if (i2 == 0) {
                        JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                        string = jSONObject2.getString(AliyunVodKey.KEY_VOD_FILENAME);
                        LogReportManager.getInstance().sendOpRecordMsg(e.this.f26944a, 11, jSONObject2.getString("RecordId"));
                    } else {
                        LogReportManager.getInstance().sendErrorMsg(e.this.f26944a, 10);
                    }
                    e.this.f26944a.h().a(i2, string);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }

        @Override // e.f
        public void b(String str, Object obj) {
            try {
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().a(-1, "");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }
    }

    public class m implements e.f {
        public m() {
        }

        @Override // e.f
        public void a(String str, Object obj) {
            try {
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().a(str, true);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }

        @Override // e.f
        public void b(String str, Object obj) {
            try {
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().a(str, false);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }
    }

    public class n implements e.f {
        public n() {
        }

        @Override // e.f
        public void a(String str, Object obj) {
            try {
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().a(str, ((Integer) obj).intValue(), true);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }

        @Override // e.f
        public void b(String str, Object obj) {
            try {
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().a(str, ((Integer) obj).intValue(), false);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }
    }

    public class o implements e.f {
        public o() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            try {
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().h(new JSONObject(str).getInt(NotificationCompat.CATEGORY_ERROR));
                    LogReportManager.getInstance().sendOpRecordMsg(e.this.f26944a, 12, "");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }

        @Override // e.f
        public void b(String str, Object obj) {
            try {
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().h(-1);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }
    }

    public class p implements e.f {
        public p() {
        }

        @Override // e.f
        public void a(String str, Object obj) {
            try {
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().b(str, ((Integer) obj).intValue(), true);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }

        @Override // e.f
        public void b(String str, Object obj) {
            try {
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().b(str, ((Integer) obj).intValue(), false);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }
    }

    public class q implements e.f {
        public q() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            try {
                if (e.this.f26944a.h() != null) {
                    JSONObject jSONObject = new JSONObject(str);
                    int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                    String string = jSONObject.getString("msg");
                    if (i2 == 0) {
                        jSONObject.getJSONObject("data");
                        c.h.a(e.G, "mixid: ");
                    }
                    e.this.f26944a.h().a(i2, string, "");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }

        @Override // e.f
        public void b(String str, Object obj) {
            try {
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().a(-1, "", "");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }
    }

    public class r implements e.f {
        public r() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            try {
                if (e.this.f26944a.h() != null) {
                    JSONObject jSONObject = new JSONObject(str);
                    int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                    String string = jSONObject.getString("msg");
                    if (i2 == 0) {
                        jSONObject.getJSONObject("data");
                        c.h.a(e.G, "");
                    }
                    e.this.f26944a.h().b(i2, string, "");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }

        @Override // e.f
        public void b(String str, Object obj) {
            try {
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().b(-1, "", "");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }
    }

    public class s implements e.f {
        public s() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            try {
                if (e.this.f26944a.h() != null) {
                    JSONObject jSONObject = new JSONObject(str);
                    int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                    String string = jSONObject.getString("msg");
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("code", i2);
                    jSONObject2.put("msg", string);
                    jSONObject2.put("data", jSONObject.getJSONObject("data"));
                    if (e.this.f26944a.h() != null) {
                        e.this.f26944a.h().j(jSONObject2.toString());
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }

        @Override // e.f
        public void b(String str, Object obj) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                String string = jSONObject.getString("msg");
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("code", i2);
                jSONObject2.put("msg", string);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().j(jSONObject2.toString());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }
    }

    public class t implements e.f {
        public t() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            try {
                if (e.this.f26944a.h() != null) {
                    JSONObject jSONObject = new JSONObject(str);
                    int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                    String string = jSONObject.getString("msg");
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("code", i2);
                    jSONObject2.put("msg", string);
                    e.this.f26944a.h().c(jSONObject2.toString());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }

        @Override // e.f
        public void b(String str, Object obj) throws JSONException {
            try {
                if (e.this.f26944a.h() != null) {
                    JSONObject jSONObject = new JSONObject(str);
                    int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                    String string = jSONObject.getString("msg");
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("code", i2);
                    jSONObject2.put("msg", string);
                    e.this.f26944a.h().c(jSONObject2.toString());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }
    }

    public class u implements e.f {
        public u() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            int i2;
            try {
                if (e.this.f26944a.h() != null) {
                    JSONObject jSONObject = new JSONObject(str);
                    int i3 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                    String string = jSONObject.getString("msg");
                    JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                    if (i3 != 0) {
                        c.h.a(e.G, "onSendPingForRoomVersionHandler get errorMsg: " + string);
                    } else if (jSONObject2.has("version") && (i2 = jSONObject2.getInt("version")) != e.this.f26944a.y().e()) {
                        c.h.a(e.G, "onSendPingForRoomVersionHandler start to sync room info, new version:" + i2 + " old version: " + e.this.f26944a.y().e());
                        e.this.f26944a.S();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }

        @Override // e.f
        public void b(String str, Object obj) {
            c.h.a(e.G, "onSendPingForRoomVersionHandler failed: " + str);
        }
    }

    public class v implements e.f {
        public v() {
        }

        /* JADX WARN: Removed duplicated region for block: B:120:0x06b0 A[Catch: Exception -> 0x0797, TryCatch #3 {Exception -> 0x0797, blocks: (B:103:0x05a4, B:105:0x05b3, B:110:0x05d1, B:112:0x05fe, B:118:0x067e, B:120:0x06b0, B:122:0x06b8, B:124:0x06bf, B:123:0x06bb, B:125:0x06fb, B:126:0x070d, B:128:0x0713, B:130:0x0735, B:114:0x0638, B:116:0x0644), top: B:144:0x05a4 }] */
        /* JADX WARN: Removed duplicated region for block: B:128:0x0713 A[Catch: Exception -> 0x0797, TryCatch #3 {Exception -> 0x0797, blocks: (B:103:0x05a4, B:105:0x05b3, B:110:0x05d1, B:112:0x05fe, B:118:0x067e, B:120:0x06b0, B:122:0x06b8, B:124:0x06bf, B:123:0x06bb, B:125:0x06fb, B:126:0x070d, B:128:0x0713, B:130:0x0735, B:114:0x0638, B:116:0x0644), top: B:144:0x05a4 }] */
        /* JADX WARN: Removed duplicated region for block: B:48:0x0216  */
        @Override // e.f
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void a(java.lang.String r26, java.lang.Object r27) throws org.json.JSONException {
            /*
                Method dump skipped, instructions count: 1963
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: g.e.v.a(java.lang.String, java.lang.Object):void");
        }

        @Override // e.f
        public void b(String str, Object obj) {
        }
    }

    public class w implements e.f {
        public w() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            try {
                if (e.this.f26944a.h() != null) {
                    JSONObject jSONObject = new JSONObject(str);
                    int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                    String string = jSONObject.getString("msg");
                    JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                    if (i2 != 0) {
                        c.h.a(e.G, "onSyncRoomHandler get errorMsg: " + string);
                        return;
                    }
                    c.h.a(e.G, "onSyncRoomHandler");
                    if (jSONObject2.has("version")) {
                        e.this.f26944a.y().a(jSONObject2.getInt("version"));
                    }
                    e.this.b(jSONObject2);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }

        @Override // e.f
        public void b(String str, Object obj) {
            c.h.a(e.G, "onSyncRoomHandler failed: " + str);
        }
    }

    public class x implements e.f {
        public x() {
        }

        @Override // e.f
        public void a(String str, Object obj) {
            c.h.a(e.G, "onUpdateVideoProfileHandler success: " + str);
        }

        @Override // e.f
        public void b(String str, Object obj) {
            c.h.a(e.G, "onUpdateVideoProfileHandler failed: " + str);
        }
    }

    public class y implements e.f {
        public y() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            c.h.a(e.G, "onReJoinRoomHandler " + str);
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                jSONObject.getString("msg");
                if (i2 != 0) {
                    c.h.a(e.G, "onReJoinRoomHandler failed " + i2);
                    e.this.f26944a.a(f.h.LOGIC_ENGINE_INIT);
                    e.this.f26944a.I();
                    if (e.this.f26944a.m() != null) {
                        e.this.f26944a.m().e();
                        e.this.f26944a.a((h.f) null);
                    }
                    if (e.this.f26944a.h() != null) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("code", 5001);
                        jSONObject2.put("msg", "reconnect server failed");
                        e.this.f26944a.h().w(jSONObject2.toString());
                        return;
                    }
                    return;
                }
                e.this.f26944a.a(f.h.LOGIC_ENGINE_CONNECTED);
                c.h.a(e.G, " set room state to LOGIC_ENGINE_CONNECTED after rejoin room");
                e.this.a();
                if (e.this.f26944a.h() != null) {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("code", 0);
                    jSONObject3.put("msg", "reconnect server suc");
                    jSONObject3.put("uid", e.this.f26944a.u().getUId());
                    jSONObject3.put("roomid", e.this.f26944a.u().getRoomId());
                    e.this.f26944a.h().b(jSONObject3.toString());
                }
                if (e.this.f26944a.m() != null) {
                    e.this.f26944a.m().g();
                }
                JSONObject jSONObject4 = jSONObject.getJSONObject("data");
                if (e.this.f26944a.y() == null) {
                    e.this.f26944a.a(new f.c());
                }
                e.this.a(jSONObject4);
            } catch (Exception e2) {
                e2.printStackTrace();
                c.h.a(e.G, c.e.a(e2.getCause()));
            }
        }

        @Override // e.f
        public void b(String str, Object obj) {
        }
    }

    public class z implements e.f {
        public z() {
        }

        @Override // e.f
        public void a(String str, Object obj) throws JSONException {
            c.h.a(e.G, " onReSubStreamHandler " + str);
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                String string = jSONObject.getString("msg");
                c.h.a(e.G, "onReSubStreamHandler err " + i2);
                c.h.a(e.G, "onReSubStreamHandler msgdesc " + string);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                String string2 = jSONObject2.getString("stream_id");
                String string3 = jSONObject2.getString("user_id");
                int i3 = jSONObject2.getInt("media_type");
                String string4 = jSONObject2.getString("streamsub_id");
                if (i2 != 0) {
                    if (e.this.f26944a.h() != null) {
                        c.h.a(e.G, "onReSubStreamHandler call back begin");
                        JSONObject jSONObject3 = new JSONObject();
                        JSONObject jSONObject4 = new JSONObject();
                        jSONObject3.put("code", i2);
                        jSONObject3.put("msg", string);
                        jSONObject4.put("uid", string3);
                        jSONObject4.put("mtype", i3);
                        jSONObject3.put("data", jSONObject4);
                        c.h.a(e.G, "onReSubStreamHandler call back end");
                        e.this.f26944a.h().l(jSONObject3.toString());
                        return;
                    }
                    return;
                }
                f.d dVarB = e.this.f26944a.y().b(string2);
                c.h.a(e.G, "uinfo" + dVarB);
                if (dVarB != null) {
                    e.d dVar = new e.d();
                    dVar.f26761c = dVarB.d();
                    dVar.f26764f = f.j.STREAM_STATUS_INIT;
                    dVar.f26766h = dVarB.e();
                    dVar.f26765g = dVarB.g();
                    dVar.f26767i = dVarB.h();
                    dVar.f26768j = dVarB.i();
                    dVar.f26763e = i3;
                    dVar.f26762d = 2;
                    dVar.f26769k = false;
                    dVar.f26770l = true;
                    c.h.a(e.G, "uclient.mIsrecon = true: ");
                    dVar.f26760b = string2;
                    dVar.f26759a = string4;
                    e.this.f26944a.D().put(string2, dVar);
                    e.this.f26944a.E().put(string4, string2);
                    ArrayList<String> arrayList = new ArrayList();
                    for (String str2 : e.this.f26944a.E().keySet()) {
                        String str3 = e.this.f26944a.E().get(str2);
                        c.h.a(e.G, "RESUB KEY: " + str2 + " value: " + str3);
                        if (str3.equals(string2) && !str2.equals(string4)) {
                            arrayList.add(str2);
                        }
                    }
                    for (String str4 : arrayList) {
                        c.h.a(e.G, "resub clear subpub map KEY: " + str4);
                        e.this.f26944a.E().remove(str4);
                    }
                    e.this.f26944a.a(string4, 2, i3, dVar.f26766h, dVar.f26765g, e.this.f26944a);
                    j.d.d().f(string4);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }

        @Override // e.f
        public void b(String str, Object obj) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data").getJSONObject("data").getJSONObject("dst");
                int i2 = jSONObject2.getInt("media_type");
                String string = jSONObject2.getString("user_id");
                String string2 = jSONObject2.getString("stream_id");
                e.this.f26944a.D().remove(string2);
                e.this.f26944a.c(string2, 2, i2);
                JSONObject jSONObject3 = new JSONObject();
                JSONObject jSONObject4 = new JSONObject();
                jSONObject3.put("code", jSONObject.getInt("code"));
                jSONObject3.put("msg", "msg wait response timeout");
                jSONObject4.put("uid", string);
                jSONObject4.put("mtype", i2);
                jSONObject3.put("data", jSONObject4);
                if (e.this.f26944a.h() != null) {
                    e.this.f26944a.h().H(jSONObject3.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public e(e.h hVar) {
        this.f26944a = hVar;
    }

    public final void b(String str) throws JSONException {
        e.d dVar;
        c.h.a(G, " onHandleServerSdpMsg " + str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            jSONObject.getString("rpc_id");
            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
            String string = jSONObject2.getString("stream_id");
            JSONObject jSONObject3 = jSONObject2.getJSONObject("sdp");
            String string2 = jSONObject3.getString("type");
            if (string2.equals("answer")) {
                j.d.d().b(string, string2, jSONObject3.getString("sdpcontent"));
            }
            if (this.f26944a.v() != null && this.f26944a.v().f26759a.equals(string)) {
                this.f26944a.v().f26764f = f.j.STREAM_STATUS_ANSWERED;
            }
            if (this.f26944a.B() != null && this.f26944a.B().f26759a.equals(string)) {
                this.f26944a.B().f26764f = f.j.STREAM_STATUS_ANSWERED;
            }
            String str2 = this.f26944a.E().get(string);
            if (str2 == null || str2.equals("") || (dVar = this.f26944a.D().get(str2)) == null) {
                return;
            }
            dVar.f26764f = f.j.STREAM_STATUS_ANSWERED;
        } catch (Exception e2) {
            e2.printStackTrace();
            c.h.a(G, c.e.a(e2.getCause()));
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // g.a
    public e.f a(String str) {
        str.hashCode();
        char c3 = 65535;
        switch (str.hashCode()) {
            case -1884322022:
                if (str.equals(c.i.f2287r)) {
                    c3 = 0;
                    break;
                }
                break;
            case -1474617170:
                if (str.equals(f0.V)) {
                    c3 = 1;
                    break;
                }
                break;
            case -1400850171:
                if (str.equals(c.i.f2270a)) {
                    c3 = 2;
                    break;
                }
                break;
            case -1097329749:
                if (str.equals(c.i.f2290u)) {
                    c3 = 3;
                    break;
                }
                break;
            case -1067366093:
                if (str.equals(f0.W)) {
                    c3 = 4;
                    break;
                }
                break;
            case -992944333:
                if (str.equals(c.i.f2282m)) {
                    c3 = 5;
                    break;
                }
                break;
            case -934700579:
                if (str.equals(f0.U)) {
                    c3 = 6;
                    break;
                }
                break;
            case -781732644:
                if (str.equals(f0.M)) {
                    c3 = 7;
                    break;
                }
                break;
            case -762696319:
                if (str.equals(f0.N)) {
                    c3 = '\b';
                    break;
                }
                break;
            case -745181861:
                if (str.equals(c.i.f2288s)) {
                    c3 = '\t';
                    break;
                }
                break;
            case -682020093:
                if (str.equals(f0.R)) {
                    c3 = '\n';
                    break;
                }
                break;
            case -662983768:
                if (str.equals(f0.Q)) {
                    c3 = 11;
                    break;
                }
                break;
            case -618121657:
                if (str.equals(c.i.f2271b)) {
                    c3 = '\f';
                    break;
                }
                break;
            case -475862317:
                if (str.equals(c.i.f2283n)) {
                    c3 = '\r';
                    break;
                }
                break;
            case -235365105:
                if (str.equals(c.i.f2273d)) {
                    c3 = 14;
                    break;
                }
                break;
            case -147119146:
                if (str.equals(c.i.f2284o)) {
                    c3 = 15;
                    break;
                }
                break;
            case 113727:
                if (str.equals("sdp")) {
                    c3 = 16;
                    break;
                }
                break;
            case 3441010:
                if (str.equals("ping")) {
                    c3 = 17;
                    break;
                }
                break;
            case 514841930:
                if (str.equals(c.i.f2277h)) {
                    c3 = 18;
                    break;
                }
                break;
            case 567974193:
                if (str.equals(c.i.f2289t)) {
                    c3 = 19;
                    break;
                }
                break;
            case 583281361:
                if (str.equals(c.i.f2278i)) {
                    c3 = 20;
                    break;
                }
                break;
            case 655299972:
                if (str.equals(c.i.f2285p)) {
                    c3 = 21;
                    break;
                }
                break;
            case 669695926:
                if (str.equals(f0.P)) {
                    c3 = 22;
                    break;
                }
                break;
            case 831327967:
                if (str.equals(f0.S)) {
                    c3 = 23;
                    break;
                }
                break;
            case 1316810426:
                if (str.equals(c.i.f2286q)) {
                    c3 = 24;
                    break;
                }
                break;
            case 1322596675:
                if (str.equals(c.i.f2292w)) {
                    c3 = 25;
                    break;
                }
                break;
            case 1476436054:
                if (str.equals(c.i.f2274e)) {
                    c3 = JSONLexer.EOI;
                    break;
                }
                break;
            case 1662163986:
                if (str.equals(c.i.f2272c)) {
                    c3 = 27;
                    break;
                }
                break;
            case 1748850607:
                if (str.equals(f0.T)) {
                    c3 = 28;
                    break;
                }
                break;
            case 1817630198:
                if (str.equals(c.i.f2291v)) {
                    c3 = 29;
                    break;
                }
                break;
            case 2035206182:
                if (str.equals(f0.O)) {
                    c3 = 30;
                    break;
                }
                break;
        }
        switch (c3) {
            case 0:
                return this.f26968y;
            case 1:
                return this.f26948e;
            case 2:
                return this.f26945b;
            case 3:
                return this.B;
            case 4:
                return this.f26949f;
            case 5:
                return this.f26964u;
            case 6:
                return this.f26947d;
            case 7:
                return this.f26956m;
            case '\b':
                return this.f26958o;
            case '\t':
                return this.f26969z;
            case '\n':
                return this.f26960q;
            case 11:
                return this.f26962s;
            case '\f':
                return this.f26946c;
            case '\r':
                return this.f26967x;
            case 14:
                return this.f26951h;
            case 15:
                return this.C;
            case 16:
                return this.f26953j;
            case 17:
                return this.D;
            case 18:
                return this.f26954k;
            case 19:
                return this.A;
            case 20:
                return this.f26955l;
            case 21:
                return this.f26965v;
            case 22:
                return this.f26957n;
            case 23:
                return this.f26963t;
            case 24:
                return this.f26966w;
            case 25:
                return this.F;
            case 26:
                return this.f26952i;
            case 27:
                return this.f26950g;
            case 28:
                return this.f26961r;
            case 29:
                return this.E;
            case 30:
                return this.f26959p;
            default:
                return null;
        }
    }

    public final void b(JSONObject jSONObject) throws JSONException {
        c.h.a(G, "updateUsersAndStreamsBySync");
        try {
            HashMap map = new HashMap();
            JSONArray jSONArray = jSONObject.getJSONArray("users");
            if (jSONArray != null) {
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                    if (jSONObject2 != null) {
                        String string = jSONObject2.getString("user_id");
                        if (!string.equals(this.f26944a.u().getUId())) {
                            f.g gVar = new f.g();
                            gVar.a(string);
                            gVar.b("");
                            map.put(string, gVar);
                        }
                    }
                }
            }
            HashMap map2 = new HashMap();
            JSONArray jSONArray2 = jSONObject.getJSONArray(IjkMediaMeta.IJKM_KEY_STREAMS);
            if (jSONArray2 != null) {
                for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                    JSONObject jSONObject3 = jSONArray2.getJSONObject(i3);
                    if (jSONObject3 != null) {
                        String string2 = jSONObject3.getString("uid");
                        if (!string2.equals(this.f26944a.u().getUId())) {
                            f.d dVar = new f.d();
                            dVar.b(2);
                            dVar.b(string2);
                            dVar.a(jSONObject3.getString(SocializeProtocolConstants.PROTOCOL_KEY_SID));
                            dVar.a(jSONObject3.getBoolean("audio"));
                            dVar.c(jSONObject3.getBoolean("video"));
                            dVar.b(jSONObject3.getBoolean("data"));
                            dVar.a(jSONObject3.getInt("media_type"));
                            dVar.e(jSONObject3.getBoolean("mutevideo"));
                            dVar.d(jSONObject3.getBoolean("muteaudio"));
                            map2.put(dVar.b(), dVar);
                        }
                    }
                }
            }
            for (Map.Entry<String, f.g> entry : this.f26944a.y().d().entrySet()) {
                c.h.a(G, " olduserlist " + entry.getKey());
                if (((f.g) map.get(entry.getKey())) == null) {
                    c.h.a(G, " user: " + entry.getValue().a() + " leave during lost connection");
                    JSONObject jSONObject4 = new JSONObject();
                    JSONObject jSONObject5 = new JSONObject();
                    jSONObject5.put(EaseConstant.MESSAGE_TYPE_CMD, 2);
                    jSONObject5.put("uid", entry.getKey());
                    jSONObject4.put("data", jSONObject5);
                    if (this.f26944a.h() != null) {
                        this.f26944a.h().G(jSONObject4.toString());
                    }
                } else {
                    map.remove(entry.getKey());
                }
            }
            for (Map.Entry entry2 : map.entrySet()) {
                c.h.a(G, " newuserlist " + ((String) entry2.getKey()));
                f.g gVar2 = (f.g) entry2.getValue();
                if (gVar2 != null) {
                    c.h.a(G, " user add");
                    this.f26944a.y().a(gVar2);
                    JSONObject jSONObject6 = new JSONObject();
                    JSONObject jSONObject7 = new JSONObject();
                    jSONObject7.put(EaseConstant.MESSAGE_TYPE_CMD, 1);
                    jSONObject7.put("uid", gVar2.a());
                    jSONObject6.put("data", jSONObject7);
                    if (this.f26944a.h() != null) {
                        this.f26944a.h().G(jSONObject6.toString());
                    }
                }
            }
            map.clear();
            Map<String, f.d> mapC = this.f26944a.y().c();
            ArrayList arrayList = new ArrayList();
            for (Map.Entry<String, f.d> entry3 : mapC.entrySet()) {
                c.h.a(G, "old stream: " + entry3.getValue());
                if (((f.d) map2.get(entry3.getKey())) == null) {
                    f.d value = entry3.getValue();
                    arrayList.add(value);
                    c.h.a(G, " stream: " + value + " removed during lost connection");
                } else {
                    map2.remove(entry3.getKey());
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                f.d dVar2 = (f.d) it.next();
                c.h.a(G, "remove not existed stream and user: " + dVar2.d() + "mediaType: " + dVar2.a());
                a(dVar2.d(), dVar2.a());
                JSONObject jSONObject8 = new JSONObject();
                JSONObject jSONObject9 = new JSONObject();
                Iterator it2 = it;
                jSONObject9.put(EaseConstant.MESSAGE_TYPE_CMD, 2);
                jSONObject9.put("uid", dVar2.d());
                jSONObject9.put("mtype", dVar2.a());
                jSONObject9.put("audio", dVar2.e());
                jSONObject9.put("video", dVar2.g());
                jSONObject9.put("data", dVar2.f());
                jSONObject9.put("mutevideo", dVar2.i());
                jSONObject9.put("muteaudio", dVar2.h());
                jSONObject8.put("data", jSONObject9);
                if (this.f26944a.h() != null) {
                    this.f26944a.h().n(jSONObject8.toString());
                }
                it = it2;
            }
            if (this.f26944a.y().c() != null) {
                c.h.a(G, "now streams size: " + this.f26944a.y().c().size());
                Iterator<String> it3 = this.f26944a.y().c().keySet().iterator();
                while (it3.hasNext()) {
                    c.h.a(G, "now existed stream is: " + this.f26944a.y().c().get(it3.next()));
                }
            }
            arrayList.clear();
            Iterator it4 = map2.entrySet().iterator();
            while (it4.hasNext()) {
                Map.Entry entry4 = (Map.Entry) it4.next();
                if (((f.d) entry4.getValue()) != null) {
                    f.d dVar3 = (f.d) entry4.getValue();
                    String strD = dVar3.d();
                    int iA = dVar3.a();
                    JSONObject jSONObject10 = new JSONObject();
                    Iterator it5 = it4;
                    JSONObject jSONObject11 = new JSONObject();
                    HashMap map3 = map2;
                    jSONObject11.put(EaseConstant.MESSAGE_TYPE_CMD, 1);
                    jSONObject11.put("uid", strD);
                    jSONObject11.put("mtype", iA);
                    jSONObject11.put("audio", dVar3.e());
                    jSONObject11.put("video", dVar3.g());
                    jSONObject11.put("data", dVar3.f());
                    jSONObject11.put("mutevideo", dVar3.i());
                    jSONObject11.put("muteaudio", dVar3.h());
                    jSONObject10.put("data", jSONObject11);
                    this.f26944a.y().a(dVar3);
                    c.h.a(G, "call to sdkengine " + jSONObject10.toString());
                    if (this.f26944a.h() != null) {
                        this.f26944a.h().n(jSONObject10.toString());
                    }
                    it4 = it5;
                    map2 = map3;
                }
            }
            map2.clear();
            Iterator<String> it6 = this.f26944a.D().keySet().iterator();
            while (it6.hasNext()) {
                c.h.a(G, this.f26944a.D().get(it6.next()).toString());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            c.h.a(G, c.e.a(e2.getCause()));
        }
    }

    public final void a() throws JSONException {
        try {
            JSONObject jSONObjectConvertToJsonObject = LogReportManager.getInstance().assemblePublicHeader().version("1.0").method(c.i.f2281l).rpcId(c.e.a()).type(1).timemills(System.currentTimeMillis()).appId(this.f26944a.u().getAppId()).roomId(this.f26944a.u().getRoomId()).sessionId(this.f26944a.y().b()).userId(this.f26944a.u().getUId()).build().convertToJsonObject();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, this.f26944a.F().l());
            jSONObject.put("agent", this.f26944a.F().b());
            jSONObject.put(com.alipay.sdk.packet.d.f3298n, this.f26944a.F().e());
            jSONObject.put("system", this.f26944a.F().m());
            jSONObject.put("network", this.f26944a.F().k());
            jSONObject.put(am.f22460w, this.f26944a.F().c());
            jSONObject.put("mem", this.f26944a.F().i());
            jSONObject.put("video", j.d.d().k());
            jSONObject.put("speaker", this.f26944a.x().c());
            jSONObject.put("micphone", 0);
            jSONObjectConvertToJsonObject.put("data", jSONObject);
            LogReportManager.getInstance().sendOpMsg(this.f26944a, 1);
            LogReportManager.getInstance().sendLog(1, jSONObjectConvertToJsonObject.toString(), this.f26944a.l());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void a(String str, int i2) {
        String str2 = str + a.h.a(i2);
        String strA = this.f26944a.y().a(str2);
        this.f26944a.y().g(str2);
        this.f26944a.y().e(strA);
        c.h.a(G, "remote streamid " + strA);
        if (strA != null) {
            String str3 = "";
            if (strA.equals("")) {
                return;
            }
            e.d dVarRemove = this.f26944a.D().remove(strA);
            if (dVarRemove != null) {
                str3 = dVarRemove.f26759a;
                c.h.a(G, "sub self id " + str3);
                this.f26944a.E().remove(str3);
                LogReportManager.getInstance().sendOpSubStreamMsg(this.f26944a, 6, str3, strA, str, 2, i2);
            }
            this.f26944a.c(str3, 2, i2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0133  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a(int r5) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 376
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: g.e.a(int):void");
    }

    public final void a(String str, String str2, e.d dVar) throws JSONException {
        c.h.a(G, " subPubMap size " + this.f26944a.E().size());
        for (String str3 : this.f26944a.E().keySet()) {
            c.h.a(G, " subPubMap key: " + str3 + " value:" + this.f26944a.E().get(str3));
        }
        c.h.a(G, " streamid " + str2 + " substreamid " + str + "");
        StringBuilder sb = new StringBuilder();
        sb.append(" uclient ");
        sb.append(dVar);
        c.h.a(G, sb.toString());
        if (dVar != null) {
            f.j jVar = dVar.f26764f;
            if ((jVar == f.j.STREAM_STATUS_READY || jVar == f.j.STREAM_STATUS_FAILED) && !dVar.f26769k) {
                dVar.f26770l = true;
                c.h.a(G, "uclient.mIsrecon = true: ");
                if (this.f26944a.m() != null) {
                    ((h.e) this.f26944a.m()).a(c.e.a(), this.f26944a.u().getUId(), dVar.f26761c, str2, str, dVar.f26763e);
                    this.f26944a.c(str, dVar.f26762d, dVar.f26763e);
                    String strA = c.e.a();
                    ((h.e) this.f26944a.m()).a(strA, this.f26944a.u().getUId(), dVar.f26761c, str2, dVar.f26763e, dVar.f26766h, dVar.f26765g);
                    this.f26944a.a(strA, this.f26948e);
                }
            }
        }
    }

    public final void a(String str, int i2, int i3, int i4) {
        LogReportManager.getInstance().sendOpStreamMsg(this.f26944a, i4, str, i2, i3);
    }

    public final boolean a(f.d dVar, e.d dVar2) throws JSONException {
        boolean z2;
        int iA = dVar.a();
        boolean zI = dVar.i();
        boolean zH = dVar.h();
        boolean z3 = true;
        if (dVar.h() != dVar2.f26767i) {
            c.h.a(G, " isNotifyTrackStatus mMuteAudio changed.");
            dVar2.f26767i = zH;
            try {
                if (this.f26944a.h() != null) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("uid", dVar.d());
                    jSONObject.put("mtype", iA);
                    jSONObject.put("ttype", 1);
                    jSONObject.put(c.i.f2279j, zH);
                    this.f26944a.h().m(jSONObject.toString());
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            z2 = true;
        } else {
            z2 = false;
        }
        if (dVar.i() != dVar2.f26768j) {
            c.h.a(G, " isNotifyTrackStatus mMuteVideo changed.");
            dVar2.f26768j = zI;
            try {
                if (this.f26944a.h() != null) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("uid", dVar.d());
                    jSONObject2.put("mtype", iA);
                    jSONObject2.put("ttype", 2);
                    jSONObject2.put(c.i.f2279j, zI);
                    this.f26944a.h().m(jSONObject2.toString());
                }
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        } else {
            z3 = z2;
        }
        c.h.a(G, " isNotifyTrackStatus done.");
        return z3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v33, types: [java.util.Map] */
    public final void a(JSONObject jSONObject) throws JSONException {
        Iterator<Map.Entry<String, f.d>> it;
        c.h.a(G, "updateUsersAndStreamsByRejoin");
        try {
            HashMap map = new HashMap();
            JSONArray jSONArray = jSONObject.getJSONArray("users");
            if (jSONArray != null) {
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                    if (jSONObject2 != null) {
                        String string = jSONObject2.getString("user_id");
                        if (!string.equals(this.f26944a.u().getUId())) {
                            f.g gVar = new f.g();
                            gVar.a(string);
                            gVar.b("");
                            map.put(string, gVar);
                        }
                    }
                }
            }
            HashMap map2 = new HashMap();
            JSONArray jSONArray2 = jSONObject.getJSONArray(IjkMediaMeta.IJKM_KEY_STREAMS);
            if (jSONArray2 != null) {
                for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                    JSONObject jSONObject3 = jSONArray2.getJSONObject(i3);
                    if (jSONObject3 != null) {
                        String string2 = jSONObject3.getString("uid");
                        if (!string2.equals(this.f26944a.u().getUId())) {
                            f.d dVar = new f.d();
                            dVar.b(2);
                            dVar.b(string2);
                            dVar.a(jSONObject3.getString(SocializeProtocolConstants.PROTOCOL_KEY_SID));
                            dVar.a(jSONObject3.getBoolean("audio"));
                            dVar.c(jSONObject3.getBoolean("video"));
                            dVar.b(jSONObject3.getBoolean("data"));
                            dVar.a(jSONObject3.getInt("media_type"));
                            dVar.e(jSONObject3.getBoolean("mutevideo"));
                            dVar.d(jSONObject3.getBoolean("muteaudio"));
                            map2.put(dVar.b(), dVar);
                        }
                    }
                }
            }
            for (Map.Entry<String, f.g> entry : this.f26944a.y().d().entrySet()) {
                c.h.a(G, " olduserlist " + entry.getKey());
                if (((f.g) map.get(entry.getKey())) == null) {
                    c.h.a(G, " user: " + entry.getValue().a() + " leave during lost connection");
                    JSONObject jSONObject4 = new JSONObject();
                    JSONObject jSONObject5 = new JSONObject();
                    jSONObject5.put(EaseConstant.MESSAGE_TYPE_CMD, 2);
                    jSONObject5.put("uid", entry.getKey());
                    jSONObject4.put("data", jSONObject5);
                    if (this.f26944a.h() != null) {
                        this.f26944a.h().G(jSONObject4.toString());
                    }
                } else {
                    map.remove(entry.getKey());
                }
            }
            for (Map.Entry entry2 : map.entrySet()) {
                c.h.a(G, " newuserlist " + ((String) entry2.getKey()));
                f.g gVar2 = (f.g) entry2.getValue();
                if (gVar2 != null) {
                    c.h.a(G, " user add");
                    this.f26944a.y().a(gVar2);
                    JSONObject jSONObject6 = new JSONObject();
                    JSONObject jSONObject7 = new JSONObject();
                    jSONObject7.put(EaseConstant.MESSAGE_TYPE_CMD, 1);
                    jSONObject7.put("uid", gVar2.a());
                    jSONObject6.put("data", jSONObject7);
                    if (this.f26944a.h() != null) {
                        this.f26944a.h().G(jSONObject6.toString());
                    }
                }
            }
            map.clear();
            Map<String, f.d> mapC = this.f26944a.y().c();
            HashMap map3 = new HashMap();
            ArrayList arrayList = new ArrayList();
            Iterator<Map.Entry<String, f.d>> it2 = mapC.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry<String, f.d> next = it2.next();
                c.h.a(G, "old stream: " + next.getValue());
                f.d dVar2 = (f.d) map2.get(next.getKey());
                if (dVar2 == null) {
                    StringBuilder sb = new StringBuilder();
                    it = it2;
                    sb.append(" stream: ");
                    sb.append(dVar2);
                    sb.append(" removed during lost connection");
                    c.h.a(G, sb.toString());
                    arrayList.add(next.getValue());
                } else {
                    it = it2;
                    map3.put(next.getKey(), dVar2);
                    map2.remove(next.getKey());
                }
                it2 = it;
            }
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                f.d dVar3 = (f.d) it3.next();
                StringBuilder sb2 = new StringBuilder();
                Iterator it4 = it3;
                sb2.append("remove not existed stream and user: ");
                sb2.append(dVar3.d());
                sb2.append("mediaType: ");
                sb2.append(dVar3.a());
                c.h.a(G, sb2.toString());
                a(dVar3.d(), dVar3.a());
                JSONObject jSONObject8 = new JSONObject();
                JSONObject jSONObject9 = new JSONObject();
                HashMap map4 = map3;
                jSONObject9.put(EaseConstant.MESSAGE_TYPE_CMD, 2);
                jSONObject9.put("uid", dVar3.d());
                jSONObject9.put("mtype", dVar3.a());
                jSONObject9.put("audio", dVar3.e());
                jSONObject9.put("video", dVar3.g());
                jSONObject9.put("data", dVar3.f());
                jSONObject9.put("mutevideo", dVar3.i());
                jSONObject9.put("muteaudio", dVar3.h());
                jSONObject8.put("data", jSONObject9);
                if (this.f26944a.h() != null) {
                    this.f26944a.h().n(jSONObject8.toString());
                }
                it3 = it4;
                map3 = map4;
            }
            HashMap map5 = map3;
            if (this.f26944a.y().c() != null) {
                c.h.a(G, "now streams size: " + this.f26944a.y().c().size());
                Iterator<String> it5 = this.f26944a.y().c().keySet().iterator();
                while (it5.hasNext()) {
                    c.h.a(G, "now existed stream is: " + this.f26944a.y().c().get(it5.next()));
                }
            }
            arrayList.clear();
            Iterator it6 = map2.entrySet().iterator();
            while (it6.hasNext()) {
                Map.Entry entry3 = (Map.Entry) it6.next();
                if (((f.d) entry3.getValue()) != null) {
                    f.d dVar4 = (f.d) entry3.getValue();
                    String strD = dVar4.d();
                    int iA = dVar4.a();
                    Iterator it7 = it6;
                    JSONObject jSONObject10 = new JSONObject();
                    HashMap map6 = map2;
                    JSONObject jSONObject11 = new JSONObject();
                    jSONObject11.put(EaseConstant.MESSAGE_TYPE_CMD, 1);
                    jSONObject11.put("uid", strD);
                    jSONObject11.put("mtype", iA);
                    jSONObject11.put("audio", dVar4.e());
                    jSONObject11.put("video", dVar4.g());
                    jSONObject11.put("data", dVar4.f());
                    jSONObject11.put("mutevideo", dVar4.i());
                    jSONObject11.put("muteaudio", dVar4.h());
                    jSONObject10.put("data", jSONObject11);
                    this.f26944a.y().a(dVar4);
                    c.h.a(G, "call to sdkengine " + jSONObject10.toString());
                    if (this.f26944a.h() != null) {
                        this.f26944a.h().n(jSONObject10.toString());
                    }
                    ?? r02 = map5;
                    r02.remove(entry3);
                    map5 = r02;
                    it6 = it7;
                    map2 = map6;
                }
            }
            HashMap map7 = map5;
            map2.clear();
            if (this.f26944a.v() != null) {
                c.h.a(G, "camera mIsrecon " + this.f26944a.v().f26770l + " status " + this.f26944a.v().f26764f);
                if (!this.f26944a.v().f26770l) {
                    a(1);
                }
            }
            if (this.f26944a.B() != null && !this.f26944a.B().f26770l) {
                c.h.a(G, "screen mIsrecon " + this.f26944a.B().f26770l + " status " + this.f26944a.B().f26764f);
                a(2);
            }
            HashMap map8 = new HashMap(this.f26944a.D());
            c.h.a(G, " substream.size " + map8.size());
            for (Map.Entry entry4 : map8.entrySet()) {
                e.d dVar5 = (e.d) entry4.getValue();
                c.h.a(G, " uclient " + dVar5);
                if (dVar5 != null) {
                    c.h.a(G, " uclient recon " + dVar5.f26770l + "  status " + dVar5.f26764f.ordinal());
                    boolean z2 = dVar5.f26770l;
                    if (!z2) {
                        f.d dVar6 = (f.d) map7.get(entry4.getKey());
                        if (a(dVar6, dVar5)) {
                            c.h.a(G, "Change StreamInfo from roomInfo-----oldStreamInfo is: " + this.f26944a.y().c().get(entry4.getKey()) + " newStreamInfo is: " + dVar6);
                            this.f26944a.y().c().put((String) entry4.getKey(), dVar6);
                        }
                        c.h.a(G, "stream map remove client: " + this.f26944a.D().remove(entry4.getKey()));
                        a(dVar5.f26759a, this.f26944a.E().remove(dVar5.f26759a), dVar5);
                    } else if (z2) {
                        c.h.a(G, " uclient is reconnecting now ,abandon this try");
                    }
                }
            }
            map8.clear();
            Iterator<String> it8 = this.f26944a.D().keySet().iterator();
            while (it8.hasNext()) {
                c.h.a(G, this.f26944a.D().get(it8.next()).toString());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            c.h.a(G, c.e.a(e2.getCause()));
        }
    }
}

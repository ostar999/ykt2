package g;

import c.i;
import com.hyphenate.easeui.constants.EaseConstant;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import core.monitor.LogReportManager;
import d.a;
import e.g;
import e.h;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes8.dex */
public class f implements d {

    /* renamed from: b, reason: collision with root package name */
    public static final String f27004b = "SignalServerMsgModule";

    /* renamed from: a, reason: collision with root package name */
    public h f27005a;

    public f(h hVar) {
        this.f27005a = hVar;
    }

    @Override // g.d
    public g a(String str) {
        str.hashCode();
        switch (str) {
            case "logoffnotify":
                return new g() { // from class: x1.j
                    @Override // e.g
                    public final void a(String str2) {
                        this.f28296a.d(str2);
                    }
                };
            case "trackst":
                return new g() { // from class: x1.i
                    @Override // e.g
                    public final void a(String str2) throws JSONException {
                        this.f28295a.j(str2);
                    }
                };
            case "icestate":
                return new g() { // from class: x1.h
                    @Override // e.g
                    public final void a(String str2) {
                        this.f28294a.f(str2);
                    }
                };
            case "tokenexpire":
                return new g() { // from class: x1.g
                    @Override // e.g
                    public final void a(String str2) {
                        this.f28293a.i(str2);
                    }
                };
            case "userst":
                return new g() { // from class: x1.f
                    @Override // e.g
                    public final void a(String str2) throws JSONException {
                        this.f28292a.m(str2);
                    }
                };
            case "kickoff":
                return new g() { // from class: x1.e
                    @Override // e.g
                    public final void a(String str2) throws JSONException {
                        this.f28291a.l(str2);
                    }
                };
            case "msgnotify":
                return new g() { // from class: x1.d
                    @Override // e.g
                    public final void a(String str2) throws JSONException {
                        this.f28290a.g(str2);
                    }
                };
            case "mixnotify":
                return new g() { // from class: x1.c
                    @Override // e.g
                    public final void a(String str2) {
                        this.f28289a.e(str2);
                    }
                };
            case "streamst":
                return new g() { // from class: x1.b
                    @Override // e.g
                    public final void a(String str2) throws JSONException {
                        this.f28288a.h(str2);
                    }
                };
            case "transportclose":
                return new g() { // from class: x1.a
                    @Override // e.g
                    public final void a(String str2) throws JSONException {
                        this.f28287a.k(str2);
                    }
                };
            default:
                return null;
        }
    }

    public void b(String str) throws JSONException {
        if (this.f27005a.y().c(str) == null) {
            f.g gVar = new f.g();
            gVar.a(str);
            this.f27005a.y().a(gVar);
            if (this.f27005a.h() != null) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("code", 0);
                    jSONObject.put("msg", "");
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put(EaseConstant.MESSAGE_TYPE_CMD, 1);
                    jSONObject2.put("uid", str);
                    jSONObject.put("data", jSONObject2);
                    this.f27005a.h().G(jSONObject.toString());
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public void c(f.d dVar) {
    }

    public void c(String str) {
    }

    public final void d(String str) {
        c.h.a(f27004b, " onHandleLogOffNotify");
        if (this.f27005a.h() != null) {
            this.f27005a.h().x(str);
        }
    }

    public final void e(String str) {
        c.h.a(f27004b, " onHandleMixNotify");
        if (this.f27005a.h() != null) {
            this.f27005a.h().t(str);
        }
    }

    public final void f(String str) {
    }

    public final void g(String str) throws JSONException {
        c.h.a(f27004b, " onHandleServerMsgNotify ");
        try {
            JSONObject jSONObject = new JSONObject(str).getJSONObject("data");
            if (jSONObject == null || this.f27005a.h() == null) {
                return;
            }
            this.f27005a.h().h(jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void h(String str) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject(str);
            jSONObject.getString("rpc_id");
            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
            if (jSONObject2 != null) {
                String string = jSONObject2.getString("user_id");
                int i2 = jSONObject2.getInt("cmdtype");
                JSONObject jSONObject3 = jSONObject2.getJSONObject("stream");
                if (jSONObject3 != null) {
                    String string2 = jSONObject3.getString(SocializeProtocolConstants.PROTOCOL_KEY_SID);
                    boolean z2 = jSONObject3.getBoolean("audio");
                    boolean z3 = jSONObject3.getBoolean("video");
                    boolean z4 = jSONObject3.getBoolean("data");
                    boolean z5 = jSONObject3.getBoolean("muteaudio");
                    boolean z6 = jSONObject3.getBoolean("mutevideo");
                    int i3 = jSONObject3.getInt("media_type");
                    if (jSONObject3.getInt("stream_type") == 2 && this.f27005a.E().containsKey(string2)) {
                        string2 = this.f27005a.E().get(string2);
                        if (this.f27005a.D().containsKey(string2)) {
                            string = this.f27005a.D().get(string2).f26761c;
                        }
                    }
                    f.d dVar = new f.d();
                    dVar.b(string);
                    dVar.a(string2);
                    dVar.a(z2);
                    dVar.c(z3);
                    dVar.b(z4);
                    dVar.d(z5);
                    dVar.e(z6);
                    if (i3 == 0) {
                        c.h.a(f27004b, " drop this streamst  for  mtype = 0");
                        return;
                    }
                    dVar.a(i3);
                    c.h.a(f27004b, " ++++++++++++++++++ cmdtype " + i2);
                    if (i2 == 1) {
                        a(dVar);
                    } else if (i2 == 2) {
                        b(dVar);
                    } else {
                        if (i2 != 3) {
                            return;
                        }
                        c(dVar);
                    }
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void i(String str) {
    }

    public void j(String str) throws JSONException {
        c.h.a(f27004b, " onHandleServerTrackSt ");
        try {
            JSONObject jSONObject = new JSONObject(str);
            jSONObject.getString("rpc_id");
            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
            if (jSONObject2 != null) {
                String string = jSONObject2.getString("user_id");
                String string2 = jSONObject2.getString("stream_id");
                int i2 = jSONObject2.getInt("media_type");
                int i3 = jSONObject2.getInt("track_type");
                boolean z2 = jSONObject2.getBoolean(i.f2279j);
                e.d dVar = this.f27005a.D().get(string2);
                if (dVar != null) {
                    j.d.d().a(dVar.f26759a, i3, z2);
                    if (dVar.f26761c.equals(string) && dVar.f26763e == i2 && this.f27005a.h() != null) {
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("uid", string);
                        jSONObject3.put("mtype", i2);
                        jSONObject3.put("ttype", i3);
                        jSONObject3.put(i.f2279j, z2);
                        if (i2 == 2 && i3 == 2) {
                            c.h.a(f27004b, "in screen type update mMuteVideo: " + z2);
                            dVar.f26768j = z2;
                        } else if (i2 != 1) {
                            c.h.a(f27004b, "unsupported mediaType: " + i2 + " and trackType: " + i3);
                        } else if (i3 == 2) {
                            c.h.a(f27004b, "in video type update mMuteVideo: " + z2);
                            dVar.f26768j = z2;
                        } else if (i3 == 1) {
                            c.h.a(f27004b, "in video type update mMuteAudio: " + z2);
                            dVar.f26767i = z2;
                        } else {
                            c.h.a(f27004b, "in media type video unsupported trackType : " + i3);
                        }
                        this.f27005a.h().m(jSONObject3.toString());
                    }
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void k(String str) throws JSONException {
        c.h.a(f27004b, " onHandleServerTranportclose " + str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            jSONObject.getString("rpc_id");
            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
            if (jSONObject2 != null) {
                String string = jSONObject2.getString("user_id");
                JSONObject jSONObject3 = jSONObject2.getJSONObject("stream");
                if (jSONObject3 != null) {
                    String string2 = jSONObject3.getString(SocializeProtocolConstants.PROTOCOL_KEY_SID);
                    boolean z2 = jSONObject3.getBoolean("audio");
                    boolean z3 = jSONObject3.getBoolean("video");
                    boolean z4 = jSONObject3.getBoolean("data");
                    int i2 = jSONObject3.getInt("media_type");
                    if (jSONObject3.getInt("stream_type") == 2 && this.f27005a.E().containsKey(string2)) {
                        string2 = this.f27005a.E().get(string2);
                        if (this.f27005a.D().containsKey(string2)) {
                            string = this.f27005a.D().get(string2).f26761c;
                        }
                    }
                    f.d dVar = new f.d();
                    dVar.b(string);
                    dVar.a(string2);
                    dVar.a(i2);
                    dVar.a(z2);
                    dVar.c(z3);
                    dVar.b(z4);
                    b(dVar);
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final void l(String str) throws JSONException {
        c.h.a(f27004b, " kick off begin");
        this.f27005a.I();
        if (this.f27005a.m() != null) {
            this.f27005a.m().e();
            this.f27005a.a((h.f) null);
        }
        try {
            if (this.f27005a.h() != null) {
                c.h.a(f27004b, " kick off");
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("code", 0);
                this.f27005a.h().v(jSONObject.toString());
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void m(String str) throws JSONException {
        try {
            c.h.a(f27004b, " onHandleServerUserSt " + str);
            JSONObject jSONObject = new JSONObject(str);
            jSONObject.getString("rpc_id");
            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
            if (jSONObject2 != null) {
                String string = jSONObject2.getString("user_id");
                int i2 = jSONObject2.getInt("cmdtype");
                if (this.f27005a.m() != null) {
                    if (i2 == 1) {
                        c.h.a(f27004b, " onHandlUserjoin " + str);
                        b(string);
                    } else if (i2 == 2) {
                        n(string);
                    } else if (i2 == 3) {
                        c(string);
                    }
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void n(String str) throws JSONException {
        c.h.a(f27004b, "onHandlUserLeave :" + str);
        o(str);
        if (this.f27005a.y().c(str) == null) {
            new f.g().a(str);
            if (this.f27005a.h() != null) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("code", 0);
                    jSONObject.put("msg", "");
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put(EaseConstant.MESSAGE_TYPE_CMD, 2);
                    jSONObject2.put("uid", str);
                    jSONObject.put("data", jSONObject2);
                    this.f27005a.h().G(jSONObject.toString());
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public void o(String str) {
        e.d value;
        this.f27005a.y().f(str);
        Iterator<Map.Entry<String, e.d>> it = this.f27005a.D().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, e.d> next = it.next();
            if (next != null && (value = next.getValue()) != null && value.f26761c.equals(str)) {
                String str2 = value.f26759a;
                c.h.a(f27004b, "subpubmap.remove " + str2);
                this.f27005a.E().remove(str2);
                this.f27005a.c(str2, 2, value.f26763e);
                it.remove();
            }
        }
    }

    public void a(f.d dVar) throws JSONException {
        Iterator<Map.Entry<String, e.d>> it = this.f27005a.D().entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            e.d value = it.next().getValue();
            if (value != null && value.f26761c.equals(dVar.d()) && value.f26763e == dVar.a()) {
                String strA = c.e.a();
                if (this.f27005a.m() != null) {
                    ((h.e) this.f27005a.m()).a(strA, this.f27005a.u().getUId(), value.f26759a, value.f26761c, dVar.b(), value.f26763e);
                }
                a(value.f26761c, value.f26763e);
            }
        }
        f.d dVarB = this.f27005a.y().b(dVar.b());
        if (dVarB != null) {
            dVarB.b(dVar.d());
            dVarB.a(dVar.b());
            dVarB.b(2);
            dVarB.a(dVar.a());
            dVarB.a(dVar.e());
            dVarB.c(dVar.g());
            dVarB.b(dVar.f());
            dVarB.d(dVar.h());
            dVarB.e(dVar.i());
        } else {
            f.d dVar2 = new f.d();
            dVar2.b(dVar.d());
            dVar2.a(dVar.b());
            dVar2.b(2);
            dVar2.a(dVar.a());
            dVar2.a(dVar.e());
            dVar2.c(dVar.g());
            dVar2.b(dVar.f());
            dVar2.d(dVar.h());
            dVar2.e(dVar.i());
            this.f27005a.y().a(dVar2);
        }
        if (this.f27005a.y().c(dVar.d()) == null) {
            f.g gVar = new f.g();
            gVar.a(dVar.d());
            this.f27005a.y().a(gVar);
        }
        if (this.f27005a.h() != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject.put("code", 0);
                jSONObject.put("msg", "");
                jSONObject2.put(EaseConstant.MESSAGE_TYPE_CMD, 1);
                jSONObject2.put("uid", dVar.d());
                jSONObject2.put("audio", dVar.e());
                jSONObject2.put("video", dVar.g());
                jSONObject2.put("data", dVar.f());
                jSONObject2.put("mtype", dVar.a());
                jSONObject2.put("mutevideo", dVar.i());
                jSONObject2.put("muteaudio", dVar.h());
                jSONObject.put("data", jSONObject2);
                c.h.a(f27004b, jSONObject.toString());
                this.f27005a.h().n(jSONObject.toString());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void b(f.d dVar) throws JSONException {
        a(dVar.d(), dVar.a());
        c.h.a(f27004b, " onHandleStreamRemove ");
        if (this.f27005a.h() != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject.put("code", 0);
                jSONObject.put("msg", "");
                jSONObject2.put(EaseConstant.MESSAGE_TYPE_CMD, 2);
                jSONObject2.put("uid", dVar.d());
                jSONObject2.put("audio", dVar.e());
                jSONObject2.put("video", dVar.g());
                jSONObject2.put("data", dVar.f());
                jSONObject2.put("mtype", dVar.a());
                jSONObject2.put("mutevideo", dVar.i());
                jSONObject2.put("muteaudio", dVar.h());
                jSONObject.put("data", jSONObject2);
                this.f27005a.h().n(jSONObject.toString());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void a(String str, int i2) {
        String str2 = str + a.h.a(i2);
        String strA = this.f27005a.y().a(str2);
        this.f27005a.y().g(str2);
        this.f27005a.y().d(strA);
        c.h.a(f27004b, "remote streamid " + strA);
        if (strA != null) {
            String str3 = "";
            if (strA.equals("")) {
                return;
            }
            e.d dVarRemove = this.f27005a.D().remove(strA);
            if (dVarRemove != null) {
                str3 = dVarRemove.f26759a;
                c.h.a(f27004b, "sub self id " + str3);
                this.f27005a.E().remove(str3);
                LogReportManager.getInstance().sendOpSubStreamMsg(this.f27005a, 6, str3, strA, str, 2, i2);
            }
            this.f27005a.c(str3, 2, i2);
        }
    }
}

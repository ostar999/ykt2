package com.beizi.fusion.d.a;

import com.beizi.fusion.model.JsonNode;
import com.plv.socket.event.history.PLVHistoryConstant;
import com.psychiatrygarden.utils.Constants;
import com.tencent.open.SocialConstants;
import com.umeng.analytics.pro.am;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.List;

/* loaded from: classes2.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    @JsonNode(key = SocialConstants.TYPE_REQUEST)
    private i f4847a;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        @JsonNode(key = "bundle")
        private String f4848a;

        /* renamed from: b, reason: collision with root package name */
        @JsonNode(key = "ver")
        private String f4849b;

        /* renamed from: c, reason: collision with root package name */
        @JsonNode(key = SocializeProtocolConstants.PROTOCOL_KEY_EXTEND)
        private b f4850c;

        public void a(String str) {
            this.f4848a = str;
        }

        public void b(String str) {
            this.f4849b = str;
        }

        public void a(b bVar) {
            this.f4850c = bVar;
        }
    }

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        @JsonNode(key = "appId")
        private String f4851a;

        public void a(String str) {
            this.f4851a = str;
        }
    }

    public static class c {

        /* renamed from: a, reason: collision with root package name */
        @JsonNode(key = "app")
        private a f4852a;

        /* renamed from: b, reason: collision with root package name */
        @JsonNode(key = com.alipay.sdk.packet.d.f3298n)
        private C0062d f4853b;

        public void a(a aVar) {
            this.f4852a = aVar;
        }

        public void a(C0062d c0062d) {
            this.f4853b = c0062d;
        }
    }

    /* renamed from: com.beizi.fusion.d.a.d$d, reason: collision with other inner class name */
    public static class C0062d {

        /* renamed from: a, reason: collision with root package name */
        @JsonNode(key = "type")
        private int f4854a;

        /* renamed from: b, reason: collision with root package name */
        @JsonNode(key = "ua")
        private String f4855b;

        /* renamed from: c, reason: collision with root package name */
        @JsonNode(key = "lmt")
        private int f4856c;

        /* renamed from: d, reason: collision with root package name */
        @JsonNode(key = "make")
        private String f4857d;

        /* renamed from: e, reason: collision with root package name */
        @JsonNode(key = "model")
        private String f4858e;

        /* renamed from: f, reason: collision with root package name */
        @JsonNode(key = "os")
        private int f4859f;

        /* renamed from: g, reason: collision with root package name */
        @JsonNode(key = "osv")
        private String f4860g;

        /* renamed from: h, reason: collision with root package name */
        @JsonNode(key = "h")
        private float f4861h;

        /* renamed from: i, reason: collision with root package name */
        @JsonNode(key = "w")
        private float f4862i;

        /* renamed from: j, reason: collision with root package name */
        @JsonNode(key = "ppi")
        private String f4863j;

        /* renamed from: k, reason: collision with root package name */
        @JsonNode(key = "pxratio")
        private String f4864k;

        /* renamed from: l, reason: collision with root package name */
        @JsonNode(key = "lang")
        private String f4865l;

        /* renamed from: m, reason: collision with root package name */
        @JsonNode(key = am.P)
        private String f4866m;

        /* renamed from: n, reason: collision with root package name */
        @JsonNode(key = "contype")
        private String f4867n;

        /* renamed from: o, reason: collision with root package name */
        @JsonNode(key = SocializeProtocolConstants.PROTOCOL_KEY_EXTEND)
        private e f4868o;

        public void a(int i2) {
            this.f4854a = i2;
        }

        public void b(int i2) {
            this.f4856c = i2;
        }

        public void c(String str) {
            this.f4858e = str;
        }

        public void d(String str) {
            this.f4860g = str;
        }

        public void e(String str) {
            this.f4863j = str;
        }

        public void f(String str) {
            this.f4864k = str;
        }

        public void g(String str) {
            this.f4865l = str;
        }

        public int getType() {
            return this.f4854a;
        }

        public void h(String str) {
            this.f4866m = str;
        }

        public void i(String str) {
            this.f4867n = str;
        }

        public void a(String str) {
            this.f4855b = str;
        }

        public void b(String str) {
            this.f4857d = str;
        }

        public void c(int i2) {
            this.f4859f = i2;
        }

        public void a(float f2) {
            this.f4861h = f2;
        }

        public void b(float f2) {
            this.f4862i = f2;
        }

        public void a(e eVar) {
            this.f4868o = eVar;
        }
    }

    public static class e {

        /* renamed from: a, reason: collision with root package name */
        @JsonNode(key = "sdkuid")
        private String f4869a;

        /* renamed from: b, reason: collision with root package name */
        @JsonNode(key = "oaid")
        private String f4870b;

        public void a(String str) {
            this.f4869a = str;
        }

        public void b(String str) {
            this.f4870b = str;
        }
    }

    public static class f {

        /* renamed from: a, reason: collision with root package name */
        @JsonNode(key = "id")
        private String f4871a;

        /* renamed from: b, reason: collision with root package name */
        @JsonNode(key = "qty")
        private int f4872b;

        /* renamed from: c, reason: collision with root package name */
        @JsonNode(key = "seq")
        private int f4873c;

        /* renamed from: d, reason: collision with root package name */
        @JsonNode(key = "dlvy")
        private int f4874d;

        /* renamed from: e, reason: collision with root package name */
        @JsonNode(key = "spec")
        private g f4875e;

        /* renamed from: f, reason: collision with root package name */
        @JsonNode(key = "priv")
        private int f4876f;

        public void a(String str) {
            this.f4871a = str;
        }

        public void b(int i2) {
            this.f4873c = i2;
        }

        public void c(int i2) {
            this.f4874d = i2;
        }

        public void d(int i2) {
            this.f4876f = i2;
        }

        public void a(int i2) {
            this.f4872b = i2;
        }

        public void a(g gVar) {
            this.f4875e = gVar;
        }
    }

    public static class g {

        /* renamed from: a, reason: collision with root package name */
        @JsonNode(key = "placement")
        private h f4877a;

        public void a(h hVar) {
            this.f4877a = hVar;
        }
    }

    public static class h {

        /* renamed from: a, reason: collision with root package name */
        @JsonNode(key = "tagid")
        private String f4878a;

        /* renamed from: b, reason: collision with root package name */
        @JsonNode(key = "sdk")
        private String f4879b;

        /* renamed from: c, reason: collision with root package name */
        @JsonNode(key = "sdkver")
        private String f4880c;

        /* renamed from: d, reason: collision with root package name */
        @JsonNode(key = PLVHistoryConstant.MSGSOURCE_REWARD)
        private int f4881d;

        public void a(String str) {
            this.f4878a = str;
        }

        public void b(String str) {
            this.f4879b = str;
        }

        public void c(String str) {
            this.f4880c = str;
        }

        public void a(int i2) {
            this.f4881d = i2;
        }
    }

    public static class i {

        /* renamed from: a, reason: collision with root package name */
        @JsonNode(key = "id")
        private String f4882a;

        /* renamed from: b, reason: collision with root package name */
        @JsonNode(key = Constants.ANSWER_MODE.TEST_MODE)
        private int f4883b;

        /* renamed from: c, reason: collision with root package name */
        @JsonNode(key = "tmax")
        private int f4884c;

        /* renamed from: d, reason: collision with root package name */
        @JsonNode(key = "at")
        private int f4885d;

        /* renamed from: e, reason: collision with root package name */
        @JsonNode(key = "cur")
        private List<String> f4886e;

        /* renamed from: f, reason: collision with root package name */
        @JsonNode(key = "seat")
        private List<String> f4887f;

        /* renamed from: g, reason: collision with root package name */
        @JsonNode(key = "wseat")
        private int f4888g;

        /* renamed from: h, reason: collision with root package name */
        @JsonNode(key = "item")
        private List<f> f4889h;

        /* renamed from: i, reason: collision with root package name */
        @JsonNode(key = com.umeng.analytics.pro.d.R)
        private c f4890i;

        /* renamed from: j, reason: collision with root package name */
        @JsonNode(key = SocializeProtocolConstants.PROTOCOL_KEY_EXTEND)
        private j f4891j;

        public void a(String str) {
            this.f4882a = str;
        }

        public void b(int i2) {
            this.f4884c = i2;
        }

        public void c(int i2) {
            this.f4885d = i2;
        }

        public void d(int i2) {
            this.f4888g = i2;
        }

        public c getContext() {
            return this.f4890i;
        }

        public void a(int i2) {
            this.f4883b = i2;
        }

        public void b(List<String> list) {
            this.f4887f = list;
        }

        public void c(List<f> list) {
            this.f4889h = list;
        }

        public void a(List<String> list) {
            this.f4886e = list;
        }

        public void a(c cVar) {
            this.f4890i = cVar;
        }

        public void a(j jVar) {
            this.f4891j = jVar;
        }
    }

    public static class j {

        /* renamed from: a, reason: collision with root package name */
        @JsonNode(key = "seat")
        private List<k> f4892a;

        public void a(List<k> list) {
            this.f4892a = list;
        }
    }

    public static class k {

        /* renamed from: a, reason: collision with root package name */
        @JsonNode(key = "seatid")
        private String f4893a;

        /* renamed from: b, reason: collision with root package name */
        @JsonNode(key = "seatname")
        private String f4894b;

        /* renamed from: c, reason: collision with root package name */
        @JsonNode(key = "seatappid")
        private String f4895c;

        /* renamed from: d, reason: collision with root package name */
        @JsonNode(key = "seattagid")
        private String f4896d;

        /* renamed from: e, reason: collision with root package name */
        @JsonNode(key = "token")
        private String f4897e;

        /* renamed from: f, reason: collision with root package name */
        @JsonNode(key = "sdkInfo")
        private String f4898f;

        public void a(String str) {
            this.f4893a = str;
        }

        public void b(String str) {
            this.f4894b = str;
        }

        public void c(String str) {
            this.f4895c = str;
        }

        public void d(String str) {
            this.f4896d = str;
        }

        public void e(String str) {
            this.f4897e = str;
        }

        public void f(String str) {
            this.f4898f = str;
        }
    }

    public void a(i iVar) {
        this.f4847a = iVar;
    }
}

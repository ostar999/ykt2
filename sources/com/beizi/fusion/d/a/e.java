package com.beizi.fusion.d.a;

import com.beizi.fusion.model.JsonNode;
import com.beizi.fusion.model.JsonResolver;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.List;

/* loaded from: classes2.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    @JsonNode(key = "seatbid")
    private List<c> f4899a;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        @JsonNode(key = "price")
        private String f4900a;

        /* renamed from: b, reason: collision with root package name */
        @JsonNode(key = SocializeProtocolConstants.PROTOCOL_KEY_EXTEND)
        private d f4901b;

        public String a() {
            return this.f4900a;
        }

        public d b() {
            return this.f4901b;
        }
    }

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        @JsonNode(key = "impAdInfo")
        private String f4902a;

        public String a() {
            return this.f4902a;
        }
    }

    public static class c {

        /* renamed from: a, reason: collision with root package name */
        @JsonNode(key = "seat")
        private String f4903a;

        /* renamed from: b, reason: collision with root package name */
        @JsonNode(key = "bid")
        private List<a> f4904b;

        /* renamed from: c, reason: collision with root package name */
        @JsonNode(key = SocializeProtocolConstants.PROTOCOL_KEY_EXTEND)
        private b f4905c;

        public String a() {
            return this.f4903a;
        }

        public List<a> b() {
            return this.f4904b;
        }

        public b c() {
            return this.f4905c;
        }
    }

    public static class d {

        /* renamed from: a, reason: collision with root package name */
        @JsonNode(key = "token")
        private String f4906a;

        public String a() {
            return this.f4906a;
        }
    }

    public static e a(String str) {
        try {
            return (e) JsonResolver.fromJson(str, e.class);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public List<c> a() {
        return this.f4899a;
    }
}

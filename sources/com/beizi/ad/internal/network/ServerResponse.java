package com.beizi.ad.internal.network;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.graphics.Rect;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;
import com.azhon.appupdate.config.Constant;
import com.beizi.ad.AdActivity;
import com.beizi.ad.NativeAdResponse;
import com.beizi.ad.R;
import com.beizi.ad.a.a.i;
import com.beizi.ad.c.b;
import com.beizi.ad.c.e;
import com.beizi.ad.internal.g;
import com.beizi.ad.internal.j;
import com.beizi.ad.internal.l;
import com.beizi.ad.internal.utilities.HTTPResponse;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.ReportEventUtil;
import com.beizi.ad.internal.utilities.StringUtil;
import com.beizi.ad.internal.utilities.UrlUtil;
import com.beizi.ad.internal.utilities.WebviewUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"NewApi"})
/* loaded from: classes2.dex */
public class ServerResponse {
    public static final String EXTRAS_KEY_MRAID = "MRAID";
    public static final String EXTRAS_KEY_ORIENTATION = "ORIENTATION";
    public static final String EXTRAS_KEY_REWARD_ITEM = "REWARD_ITEM";
    public static final String EXTRAS_KEY_SCALE = "SCALE";
    private String A;
    private String B;
    private String C;
    private String D;
    private String E;
    private String F;
    private String G;
    private String H;
    private String I;
    private String J;
    private b.C0046b K;
    private List<b.h> L;
    private String M;
    private String N;
    private List<Pair<j, String>> O;
    private String P;
    private String Q;
    private String R;
    private String S;
    private LinkedList<String> T;
    private LinkedList<com.beizi.ad.internal.b.a> U;
    private HashMap<String, Object> V;
    private boolean W;
    private boolean X;
    private com.beizi.ad.internal.nativead.a Y;

    /* renamed from: a, reason: collision with root package name */
    private String f4336a;

    /* renamed from: b, reason: collision with root package name */
    private String f4337b;

    /* renamed from: c, reason: collision with root package name */
    private e.a f4338c;

    /* renamed from: d, reason: collision with root package name */
    private int f4339d;

    /* renamed from: e, reason: collision with root package name */
    private int f4340e;

    /* renamed from: f, reason: collision with root package name */
    private int f4341f;

    /* renamed from: g, reason: collision with root package name */
    private int f4342g;

    /* renamed from: h, reason: collision with root package name */
    private int f4343h;

    /* renamed from: i, reason: collision with root package name */
    private int f4344i;

    /* renamed from: j, reason: collision with root package name */
    private boolean f4345j;

    /* renamed from: k, reason: collision with root package name */
    private boolean f4346k;

    /* renamed from: l, reason: collision with root package name */
    private boolean f4347l;

    /* renamed from: m, reason: collision with root package name */
    private boolean f4348m;
    public String mDetectClickUrl;
    public String mDetectViewUrl;
    public l mMediaType;

    /* renamed from: n, reason: collision with root package name */
    private boolean f4349n;

    /* renamed from: o, reason: collision with root package name */
    private boolean f4350o;

    /* renamed from: p, reason: collision with root package name */
    private int f4351p;

    /* renamed from: q, reason: collision with root package name */
    private int f4352q;

    /* renamed from: r, reason: collision with root package name */
    private boolean f4353r;

    /* renamed from: s, reason: collision with root package name */
    private int f4354s;

    /* renamed from: t, reason: collision with root package name */
    private AdLogoInfo f4355t;

    /* renamed from: u, reason: collision with root package name */
    private AdLogoInfo f4356u;

    /* renamed from: v, reason: collision with root package name */
    private boolean f4357v;

    /* renamed from: w, reason: collision with root package name */
    private String f4358w;

    /* renamed from: x, reason: collision with root package name */
    private b.C0046b.C0047b f4359x;

    /* renamed from: y, reason: collision with root package name */
    private b.C0046b.a f4360y;

    /* renamed from: z, reason: collision with root package name */
    private int f4361z;

    public static class AdLogoInfo {
        public static int TYPE_PIC = 0;
        public static int TYPE_TEXT = 1;
        String adurl;
        int type = 0;

        /* JADX INFO: Access modifiers changed from: private */
        public void setAdurl(String str) {
            this.adurl = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setType(int i2) {
            this.type = i2;
        }

        public String getAdurl() {
            return this.adurl;
        }

        public int getType() {
            return this.type;
        }
    }

    public ServerResponse(HTTPResponse hTTPResponse, l lVar) {
        this.f4339d = 0;
        this.f4340e = 1;
        this.f4341f = 0;
        this.f4342g = 0;
        this.f4343h = 0;
        this.f4344i = 0;
        this.f4345j = false;
        this.f4346k = true;
        this.f4347l = false;
        this.f4348m = false;
        this.f4349n = false;
        this.f4350o = false;
        this.f4351p = 0;
        this.f4352q = 0;
        this.f4353r = false;
        this.f4354s = 0;
        this.f4355t = new AdLogoInfo();
        this.f4356u = new AdLogoInfo();
        this.f4357v = false;
        this.O = new LinkedList();
        this.T = new LinkedList<>();
        this.U = new LinkedList<>();
        this.V = new HashMap<>();
        this.W = false;
        this.X = false;
        this.mMediaType = lVar;
        a(hTTPResponse.getHeaders());
        try {
            a(b.i.a(hTTPResponse.getResponseBinaryBody().toByteArray()));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    private void b() {
        if (this.T.isEmpty()) {
            return;
        }
        final g gVarA = g.a();
        gVarA.c().post(new Runnable() { // from class: com.beizi.ad.internal.network.ServerResponse.1
            @Override // java.lang.Runnable
            public void run() throws IOException {
                int i2;
                Iterator it = ServerResponse.this.T.iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    try {
                        InputStream inputStreamOpenStream = new URL(gVarA.b().a(str)).openStream();
                        byte[] bArr = new byte[1024];
                        while (i2 > 0) {
                            int i3 = inputStreamOpenStream.read(bArr);
                            i2 = i3 != -1 ? i2 - i3 : 1024000;
                        }
                    } catch (IOException unused) {
                        HaoboLog.v(HaoboLog.baseLogTag, "Ignored request: " + str);
                    }
                }
            }
        });
    }

    private boolean c(b.i iVar) {
        if (iVar.a() > 0) {
            b.j jVar = iVar.e().get(0);
            this.f4336a = jVar.a();
            this.f4337b = jVar.b();
            this.f4338c = jVar.c();
            this.f4339d = jVar.d();
            this.f4340e = jVar.e() == e.h.PORTRAIT ? 1 : 2;
            this.f4344i = Integer.parseInt(jVar.f());
            this.f4343h = Integer.parseInt(jVar.g());
            if (jVar.h() != null && (getAdType() == e.a.ADP_TABLE || getAdType() == e.a.ADP_CUSTOMER)) {
                b.g gVarH = jVar.h();
                this.f4341f = Integer.parseInt(gVarH.a());
                this.f4342g = Integer.parseInt(gVarH.b());
            } else if (!StringUtil.isEmpty(jVar.b()) && getAdType() == e.a.ADP_IVIDEO) {
                addToExtras(EXTRAS_KEY_REWARD_ITEM, jVar.b());
            }
            this.f4345j = jVar.o();
            this.f4346k = jVar.m();
            this.f4347l = jVar.q();
            this.f4348m = jVar.n();
            this.f4349n = jVar.i();
            this.f4350o = jVar.k();
            this.f4351p = jVar.l();
            this.f4352q = jVar.j();
            this.f4353r = jVar.p();
            List<b.d> listR = jVar.r();
            if (listR != null && listR.size() > 0) {
                this.f4358w = listR.get(0).i();
                this.N = listR.get(0).b();
            }
            if (this.f4345j && this.f4344i == 0 && this.f4343h == 0) {
                this.f4344i = 720;
                this.f4343h = 1280;
            }
            if (jVar.s() > 0) {
                int i2 = 0;
                for (b.d dVar : jVar.r()) {
                    if (i2 == 0) {
                        this.N = dVar.b();
                        i.a("BeiZisAd", "mAdid = " + this.N);
                    }
                    if (dVar.h() <= 0 || dVar.g().get(0) == null) {
                        this.M = dVar.a();
                        b.c cVarE = dVar.e();
                        if (cVarE != null) {
                            if (cVarE.a() != null) {
                                this.f4355t.setAdurl(cVarE.a());
                                this.f4355t.setType(AdLogoInfo.TYPE_PIC);
                            } else {
                                this.f4355t.setAdurl(cVarE.b());
                                this.f4355t.setType(AdLogoInfo.TYPE_TEXT);
                            }
                            if (cVarE.c() != null) {
                                this.f4356u.setAdurl(cVarE.c());
                                this.f4356u.setType(AdLogoInfo.TYPE_PIC);
                            } else {
                                this.f4356u.setAdurl(cVarE.d());
                                this.f4356u.setType(AdLogoInfo.TYPE_TEXT);
                            }
                        }
                        if (dVar.d() > 0) {
                            for (b.a aVar : dVar.f()) {
                                if (this.f4346k) {
                                    for (int i3 = 0; i3 < aVar.d(); i3++) {
                                        if (!StringUtil.isEmpty(aVar.c().get(i3).a())) {
                                            this.T.add(aVar.c().get(i3).b());
                                        }
                                    }
                                }
                                if ((aVar.a() == e.f.RENDER_VIDEO || aVar.a() == e.f.RENDER_VAST_VIDEO) && aVar.d() > 0) {
                                    this.O.add(Pair.create(j.VIDEO, aVar.c().get(0).b()));
                                } else {
                                    String strA = a(aVar);
                                    this.O.add(Pair.create(j.HTML, strA));
                                    if (strA.contains("mraid.js")) {
                                        addToExtras(EXTRAS_KEY_MRAID, Boolean.TRUE);
                                    }
                                }
                            }
                        }
                        if (dVar.c() != null) {
                            b.C0046b c0046bC = dVar.c();
                            a(c0046bC);
                            this.Q = c0046bC.a();
                            this.S = c0046bC.h();
                            this.R = c0046bC.b();
                            b.h hVarI = c0046bC.i();
                            if (hVarI != null && !TextUtils.isEmpty(hVarI.a())) {
                                this.mDetectViewUrl = hVarI.a();
                            }
                            if (hVarI != null && !TextUtils.isEmpty(hVarI.b())) {
                                this.mDetectClickUrl = hVarI.b();
                            }
                            if (hVarI != null && !TextUtils.isEmpty(hVarI.c())) {
                                this.P = hVarI.c();
                            }
                        }
                    }
                    i2++;
                    if (!this.O.isEmpty()) {
                        break;
                    }
                }
            }
        } else {
            HaoboLog.e(HaoboLog.httpRespLogTag, HaoboLog.getString(R.string.blank_ad));
        }
        if (this.O.isEmpty()) {
            return false;
        }
        this.W = true;
        return true;
    }

    private boolean d(b.i iVar) {
        if (iVar.a() > 0) {
            for (b.j jVar : iVar.e()) {
                this.f4346k = jVar.m();
                if (jVar.s() > 0) {
                    for (b.d dVar : jVar.r()) {
                        if (dVar.h() <= 0 || dVar.g().get(0) == null) {
                            if (dVar.d() > 0) {
                                for (b.a aVar : dVar.f()) {
                                    if (this.f4346k) {
                                        for (int i2 = 0; i2 < aVar.d(); i2++) {
                                            if (!StringUtil.isEmpty(aVar.c().get(i2).a())) {
                                                this.T.add(aVar.c().get(i2).b());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (this.T.isEmpty()) {
            return false;
        }
        this.W = true;
        return true;
    }

    private boolean e(b.i iVar) {
        if (iVar.a() > 0) {
            b.j jVar = iVar.e().get(0);
            this.f4336a = jVar.a();
            this.f4337b = jVar.b();
            this.f4338c = jVar.c();
            this.f4339d = jVar.d();
            this.f4340e = jVar.e() == e.h.PORTRAIT ? 1 : 2;
            this.f4344i = Integer.parseInt(jVar.f());
            this.f4343h = Integer.parseInt(jVar.g());
            List<b.d> listR = jVar.r();
            if (listR != null && listR.size() > 0) {
                this.f4358w = listR.get(0).i();
                this.N = listR.get(0).b();
            }
            this.f4345j = jVar.o();
            this.f4346k = jVar.m();
            this.f4347l = jVar.q();
            this.f4348m = jVar.n();
            this.f4349n = jVar.i();
            this.f4350o = jVar.k();
            this.f4351p = jVar.l();
            this.f4352q = jVar.j();
            this.f4353r = jVar.p();
            if (this.f4345j && this.f4344i == 0 && this.f4343h == 0) {
                this.f4344i = 720;
                this.f4343h = 1280;
            }
            if (jVar.s() > 0) {
                for (b.d dVar : jVar.r()) {
                    if (dVar.h() <= 0 || dVar.g().get(0) == null) {
                        this.M = dVar.a();
                        if (dVar.d() >= 0) {
                            for (b.a aVar : dVar.f()) {
                                if (this.f4346k) {
                                    for (int i2 = 0; i2 < aVar.d(); i2++) {
                                        if (!StringUtil.isEmpty(aVar.c().get(i2).a())) {
                                            this.T.add(aVar.c().get(i2).b());
                                        }
                                    }
                                }
                                if (aVar.a() == e.f.RENDER_JSON && aVar.d() > 0) {
                                    try {
                                        b.c cVarE = dVar.e();
                                        if (cVarE != null) {
                                            if (cVarE.a() != null) {
                                                this.f4355t.setAdurl(cVarE.a());
                                                this.f4355t.setType(AdLogoInfo.TYPE_PIC);
                                            } else {
                                                this.f4355t.setAdurl(cVarE.b());
                                                this.f4355t.setType(AdLogoInfo.TYPE_TEXT);
                                            }
                                            if (cVarE.c() != null) {
                                                this.f4356u.setAdurl(cVarE.c());
                                                this.f4356u.setType(AdLogoInfo.TYPE_PIC);
                                            } else {
                                                this.f4356u.setAdurl(cVarE.d());
                                                this.f4356u.setType(AdLogoInfo.TYPE_TEXT);
                                            }
                                        }
                                        com.beizi.ad.internal.nativead.a aVarA = com.beizi.ad.internal.nativead.a.a(new JSONObject(aVar.b()));
                                        this.Y = aVarA;
                                        aVarA.b(getLogoUrl());
                                        this.Y.a(getAdUrl());
                                        if (dVar.c() != null) {
                                            a(dVar.c());
                                            b.C0046b c0046bC = dVar.c();
                                            this.Q = c0046bC.a();
                                            this.R = c0046bC.b();
                                            this.S = c0046bC.h();
                                            this.Y.b(!StringUtil.isEmpty(this.Q) ? this.Q : this.S);
                                            this.Y.c(this.R);
                                            this.Y.a(dVar.c());
                                            b.h hVarI = c0046bC.i();
                                            if (hVarI != null && !TextUtils.isEmpty(hVarI.a())) {
                                                this.Y.d(hVarI.a());
                                            }
                                            if (hVarI != null && !TextUtils.isEmpty(hVarI.b())) {
                                                this.Y.e(hVarI.b());
                                            }
                                            List<b.h> listL = c0046bC.l();
                                            if (listL != null && listL.size() > 0) {
                                                for (int i3 = 0; i3 < listL.size(); i3++) {
                                                    String strB = listL.get(i3).b();
                                                    if (!TextUtils.isEmpty(strB)) {
                                                        this.Y.e(strB);
                                                    }
                                                    String strA = listL.get(i3).a();
                                                    if (!TextUtils.isEmpty(strA)) {
                                                        this.Y.d(strA);
                                                    }
                                                }
                                            }
                                        }
                                    } catch (JSONException unused) {
                                    }
                                    if (this.Y != null) {
                                        this.W = true;
                                        return true;
                                    }
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
        } else {
            HaoboLog.e(HaoboLog.httpRespLogTag, HaoboLog.getString(R.string.blank_ad));
        }
        if (this.O.isEmpty()) {
            return false;
        }
        this.W = true;
        return true;
    }

    private boolean f(b.i iVar) {
        if (iVar.a() > 0) {
            b.j jVar = iVar.e().get(0);
            List<b.d> listR = jVar.r();
            if (listR != null && listR.size() > 0) {
                b.d dVar = listR.get(0);
                if (dVar.c() != null) {
                    b.C0046b c0046bC = dVar.c();
                    a(c0046bC);
                    this.Q = c0046bC.a();
                    this.S = c0046bC.h();
                    b.h hVarI = c0046bC.i();
                    if (hVarI != null && !TextUtils.isEmpty(hVarI.a())) {
                        this.mDetectViewUrl = hVarI.a();
                    }
                    if (hVarI != null && !TextUtils.isEmpty(hVarI.b())) {
                        this.mDetectClickUrl = hVarI.b();
                    }
                    if (hVarI != null && !TextUtils.isEmpty(hVarI.c())) {
                        this.P = hVarI.c();
                    }
                }
            }
            this.f4336a = jVar.a();
            this.f4337b = jVar.b();
            this.f4338c = jVar.c();
            this.f4339d = jVar.d();
            this.f4340e = jVar.e() == e.h.PORTRAIT ? 1 : 2;
            this.f4343h = Integer.parseInt(jVar.f());
            this.f4344i = Integer.parseInt(jVar.g());
            List<b.d> listR2 = jVar.r();
            if (listR2 != null && listR2.size() > 0) {
                this.f4358w = listR2.get(0).i();
                this.N = listR2.get(0).b();
            }
            this.f4341f = 0;
            this.f4342g = 0;
            if (jVar.h() != null && getAdType() == e.a.ADP_TABLE) {
                b.g gVarH = jVar.h();
                this.f4341f = Integer.parseInt(gVarH.a());
                this.f4342g = Integer.parseInt(gVarH.b());
            }
            if (jVar.s() > 0) {
                for (b.d dVar2 : jVar.r()) {
                    if (dVar2.h() > 0 && dVar2.g().get(0) != null) {
                        b.f fVar = dVar2.g().get(0);
                        this.U.add(new com.beizi.ad.internal.b.a(fVar.a(), fVar.c(), this.f4344i, this.f4343h, fVar.b(), ""));
                    }
                }
            }
        }
        if (this.U.isEmpty()) {
            return false;
        }
        this.W = true;
        return true;
    }

    public void addToExtras(String str, Object obj) {
        this.V.put(str, obj);
    }

    public boolean containsAds() {
        return this.W;
    }

    public String getAdExtInfo() {
        return this.M;
    }

    public String getAdId() {
        return this.N;
    }

    public b.C0046b getAdInteractInfo() {
        return this.K;
    }

    public int getAdOrientation() {
        return this.f4340e;
    }

    public e.a getAdType() {
        return this.f4338c;
    }

    public AdLogoInfo getAdUrl() {
        return this.f4355t;
    }

    public List<Pair<j, String>> getCreatives() {
        return this.O;
    }

    public HashMap<String, Object> getExtras() {
        return this.V;
    }

    public b.C0046b.a getFollowTrackExt() {
        return this.f4360y;
    }

    public int getHeight() {
        return this.f4343h;
    }

    public int getLeft() {
        return this.f4341f;
    }

    public AdLogoInfo getLogoUrl() {
        return this.f4356u;
    }

    public int getMaxTimer() {
        return this.f4352q;
    }

    public LinkedList<com.beizi.ad.internal.b.a> getMediationAds() {
        return this.U;
    }

    public int getMinTimer() {
        return this.f4351p;
    }

    public NativeAdResponse getNativeAdResponse() {
        return this.Y;
    }

    public LinkedList<String> getPrefetchResources() {
        return this.T;
    }

    public String getPrice() {
        return this.f4358w;
    }

    public int getRefreshInterval() {
        return this.f4339d;
    }

    public int getTop() {
        return this.f4342g;
    }

    public b.C0046b.C0047b getVideoTrackExt() {
        return this.f4359x;
    }

    public int getWidth() {
        return this.f4344i;
    }

    public void handleClick(View view, String str, String str2, String str3, String str4, String str5, String str6, boolean z2, String str7) {
        i.a("BeiZisAd", "handleClick========" + z2);
        if (!z2) {
            if (!StringUtil.isEmpty(this.mDetectClickUrl)) {
                this.mDetectClickUrl = UrlUtil.replaceToTouchEventUrl(this.mDetectClickUrl, str, str2, str3, str4, str5, str6, "");
                new com.beizi.ad.internal.i(this.mDetectClickUrl).execute(new Void[0]);
                this.mDetectClickUrl = "";
            }
            if (this.K != null && this.L != null) {
                for (int i2 = 0; i2 < this.L.size(); i2++) {
                    b.h hVar = this.L.get(i2);
                    if (hVar != null && !TextUtils.isEmpty(hVar.b())) {
                        new com.beizi.ad.internal.i(StringUtil.replaceClick(view, !TextUtils.isEmpty(str7) ? UrlUtil.replaceToTouchEventUrl(hVar.b(), str, str2, str3, str4, str5, str6, "").replace("__REQUESTUUID__", str7) : UrlUtil.replaceToTouchEventUrl(hVar.b(), str, str2, str3, str4, str5, str6, ""))).executeOnExecutor(com.beizi.ad.a.a.c.b().d(), new Void[0]);
                    }
                }
                this.L = null;
            }
        }
        i.a("BeiZisAd", "mDeepLinkUrl:" + this.R + ",appDownloadURL = " + this.D + ",mLandingPageUrl = " + this.Q);
        if (TextUtils.isEmpty(this.R)) {
            if (!TextUtils.isEmpty(this.D) && this.f4361z == 2) {
                if (view != null) {
                    if (com.beizi.ad.a.a.g.a(view.getContext(), this.B)) {
                        com.beizi.ad.a.a.g.b(view.getContext(), this.B);
                        b.C0046b.a aVar = this.f4360y;
                        if (aVar != null) {
                            ReportEventUtil.report(aVar.a());
                            return;
                        }
                        return;
                    }
                    File fileA = com.beizi.ad.a.a.g.a(view.getContext());
                    String absolutePath = fileA != null ? fileA.getAbsolutePath() : "";
                    com.beizi.ad.a.b.a(view.getContext()).b(view.getContext()).a(new com.beizi.ad.a.a(this.D, this.B + Constant.APK_SUFFIX, this.B, absolutePath, this.A, this.C, view.getContext().getPackageName() + ".fileprovider", this.f4360y, this.E, this.F, this.G, this.H, this.I, this.J)).b();
                    return;
                }
                return;
            }
            if (TextUtils.isEmpty(this.Q) || !this.Q.startsWith("http")) {
                StringUtil.isEmpty(this.S);
                return;
            }
            if (!this.f4357v) {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(Uri.decode(this.Q)));
                    if (view != null) {
                        view.getContext().startActivity(intent);
                        return;
                    }
                    return;
                } catch (Exception unused) {
                    Toast.makeText(view.getContext(), R.string.action_cant_be_completed, 0).show();
                    return;
                }
            }
            Class clsA = AdActivity.a();
            try {
                WebView webView = new WebView(new MutableContextWrapper(g.a().e()));
                WebviewUtil.setWebViewSettings(webView);
                webView.loadUrl(this.Q);
                com.beizi.ad.internal.a.a.f3988a.add(webView);
                Intent intent2 = new Intent(g.a().e(), (Class<?>) clsA);
                intent2.setFlags(268435456);
                intent2.putExtra("ACTIVITY_TYPE", "DOWNLOADBROWSER");
                g.a().e().startActivity(intent2);
                return;
            } catch (ActivityNotFoundException unused2) {
                HaoboLog.w(HaoboLog.baseLogTag, HaoboLog.getString(R.string.adactivity_missing, clsA.getName()));
                com.beizi.ad.internal.a.a.f3988a.remove();
                return;
            } catch (Exception e2) {
                HaoboLog.e(HaoboLog.baseLogTag, "Exception initializing the redirect webview: " + e2.getMessage());
                return;
            }
        }
        try {
            if (this.f4360y != null && view != null) {
                ReportEventUtil.report(com.beizi.ad.a.a.g.a(view.getContext(), this.B) ? this.f4360y.h() : this.f4360y.i());
            }
            Uri uri = Uri.parse(Uri.decode(this.R));
            if (uri.getScheme() == null || !uri.getScheme().equals("bzopen") || TextUtils.isEmpty(uri.getHost()) || uri.getPathSegments().size() <= 0) {
                Intent intent3 = new Intent("android.intent.action.VIEW", uri);
                intent3.setFlags(268435456);
                if (view != null) {
                    view.getContext().startActivity(intent3);
                }
            } else {
                Intent intent4 = new Intent();
                intent4.setAction("android.intent.action.MAIN");
                intent4.addCategory("android.intent.category.LAUNCHER");
                String queryParameter = uri.getQueryParameter("flags");
                if (!TextUtils.isEmpty(queryParameter)) {
                    try {
                        if (queryParameter.startsWith("0x") || queryParameter.startsWith("0X")) {
                            intent4.setFlags(Integer.parseInt(queryParameter.substring(2), 16));
                        } else {
                            intent4.setFlags(Integer.parseInt(queryParameter));
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                intent4.setComponent(new ComponentName(uri.getHost(), uri.getPathSegments().get(0)));
                String queryParameter2 = uri.getQueryParameter("rect");
                if (!TextUtils.isEmpty(queryParameter2)) {
                    try {
                        String[] strArrSplit = queryParameter2.split(":");
                        if (strArrSplit.length == 4) {
                            Rect rect = new Rect();
                            rect.set(Integer.parseInt(strArrSplit[0]), Integer.parseInt(strArrSplit[1]), Integer.parseInt(strArrSplit[2]), Integer.parseInt(strArrSplit[3]));
                            intent4.setSourceBounds(rect);
                        }
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                }
                if (view != null) {
                    view.getContext().startActivity(intent4);
                }
            }
            b.C0046b.a aVar2 = this.f4360y;
            if (aVar2 == null || view == null) {
                return;
            }
            ReportEventUtil.report(aVar2.f());
        } catch (Exception e5) {
            b.C0046b.a aVar3 = this.f4360y;
            if (aVar3 != null) {
                ReportEventUtil.report(aVar3.g());
            }
            if (TextUtils.isEmpty(this.Q) || !this.Q.startsWith("http")) {
                return;
            }
            if (!this.f4357v) {
                try {
                    Intent intent5 = new Intent("android.intent.action.VIEW", Uri.parse(Uri.decode(this.Q)));
                    if (view != null) {
                        view.getContext().startActivity(intent5);
                        return;
                    }
                    return;
                } catch (Exception unused3) {
                    Toast.makeText(view.getContext(), R.string.action_cant_be_completed, 0).show();
                    return;
                }
            }
            Class clsA2 = AdActivity.a();
            try {
                WebView webView2 = new WebView(new MutableContextWrapper(view.getContext()));
                WebviewUtil.setWebViewSettings(webView2);
                webView2.loadUrl(this.Q);
                com.beizi.ad.internal.a.a.f3988a.add(webView2);
                Intent intent6 = new Intent(g.a().e(), (Class<?>) clsA2);
                intent6.setFlags(268435456);
                intent6.putExtra("ACTIVITY_TYPE", "DOWNLOADBROWSER");
                view.getContext().startActivity(intent6);
            } catch (ActivityNotFoundException unused4) {
                HaoboLog.w(HaoboLog.baseLogTag, HaoboLog.getString(R.string.adactivity_missing, clsA2.getName()));
                com.beizi.ad.internal.a.a.f3988a.remove();
            } catch (Exception unused5) {
                HaoboLog.e(HaoboLog.baseLogTag, "Exception initializing the redirect webview: " + e5.getMessage());
            }
        }
    }

    public void handleConvert(View view) {
        HaoboLog.e(HaoboLog.baseLogTag, "handleClick called with convertUrl = " + this.P);
        if (StringUtil.isEmpty(this.P)) {
            return;
        }
        new com.beizi.ad.internal.i(this.P).execute(new Void[0]);
    }

    public void handleOnCompletion() {
        List<b.h> listL;
        b.C0046b c0046b = this.K;
        if (c0046b == null || (listL = c0046b.l()) == null) {
            return;
        }
        for (int i2 = 0; i2 < listL.size(); i2++) {
            UrlUtil.sendOnCompletionInfoToServer(listL.get(i2));
        }
    }

    public void handleOnPause(View view) {
        List<b.h> listL;
        b.C0046b c0046b = this.K;
        if (c0046b == null || (listL = c0046b.l()) == null) {
            return;
        }
        for (int i2 = 0; i2 < listL.size(); i2++) {
            UrlUtil.sendOnPauseInfoToServer(listL.get(i2));
        }
    }

    public void handleOnStart(View view, int i2) {
        List<b.h> listL;
        b.C0046b c0046b = this.K;
        if (c0046b == null || (listL = c0046b.l()) == null) {
            return;
        }
        for (int i3 = 0; i3 < listL.size(); i3++) {
            UrlUtil.sendOnStartInfoToServer(listL.get(i3));
        }
    }

    public void handleView(View view, String str) {
        List<b.h> listL;
        int i2 = this.f4354s;
        if (i2 <= 0) {
            this.f4354s = i2 + 1;
            if (!StringUtil.isEmpty(this.mDetectViewUrl)) {
                this.mDetectViewUrl = UrlUtil.replaceToTouchEventUrl(this.mDetectViewUrl, "", "", "", "", "", "", "");
                new com.beizi.ad.internal.i(this.mDetectViewUrl).execute(new Void[0]);
                this.mDetectViewUrl = "";
            }
            b.C0046b c0046b = this.K;
            if (c0046b == null || (listL = c0046b.l()) == null) {
                return;
            }
            for (int i3 = 0; i3 < listL.size(); i3++) {
                b.h hVar = listL.get(i3);
                if (hVar != null && !TextUtils.isEmpty(hVar.a()) && view != null) {
                    new com.beizi.ad.internal.i(StringUtil.replaceView(0, view, !TextUtils.isEmpty(str) ? hVar.a().replace("__REQUESTUUID__", str) : hVar.a())).executeOnExecutor(com.beizi.ad.a.a.c.b().d(), new Void[0]);
                }
            }
        }
    }

    public boolean isAutoClose() {
        return this.f4349n;
    }

    public boolean isAutoPlay() {
        return this.f4353r;
    }

    public boolean isFullScreen() {
        return this.f4345j;
    }

    public boolean isManualClose() {
        return this.f4350o;
    }

    public boolean isMuted() {
        return this.f4348m;
    }

    public boolean isWifiOnly() {
        return this.f4347l;
    }

    public boolean isWifiPreload() {
        return this.f4346k;
    }

    public void setAdOrientation(int i2) {
        this.f4340e = i2;
    }

    public void setOpenInNativeBrowser(boolean z2) {
        this.f4357v = z2;
    }

    private void a(b.C0046b c0046b) {
        this.K = c0046b;
        this.L = c0046b.l();
        this.f4359x = c0046b.k();
        this.f4360y = c0046b.j();
        this.f4361z = c0046b.c();
        this.A = c0046b.d();
        this.B = c0046b.e();
        this.C = c0046b.f();
        this.D = c0046b.g();
        if (TextUtils.isEmpty(this.B)) {
            this.B = "lance";
        }
        if (TextUtils.isEmpty(this.A)) {
            this.A = "BeiZi";
        }
        if (TextUtils.isEmpty(this.C)) {
            this.C = "Ad Download";
        }
        this.E = c0046b.m();
        this.F = c0046b.n();
        this.G = c0046b.o();
        this.H = c0046b.p();
        this.I = c0046b.q();
        this.J = c0046b.r();
    }

    private boolean b(b.i iVar) {
        if (iVar.b() == 0) {
            return true;
        }
        HaoboLog.e(HaoboLog.httpRespLogTag, HaoboLog.getString(R.string.response_error, iVar.c(), iVar.d()));
        return false;
    }

    private void a(Map<String, List<String>> map) {
        if (map != null) {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                if (entry.getKey() != null) {
                    for (String str : entry.getValue()) {
                        if (!TextUtils.isEmpty(str)) {
                            HaoboLog.v(HaoboLog.httpRespLogTag, HaoboLog.getString(R.string.response_header, entry.getKey(), str));
                        }
                    }
                }
            }
        }
    }

    private void a(b.i iVar) {
        if (b(iVar)) {
            l lVar = this.mMediaType;
            if (lVar == l.PREFETCH) {
                if (d(iVar)) {
                    return;
                }
            } else if (lVar != l.NATIVE) {
                if (c(iVar)) {
                    return;
                }
            } else if (e(iVar)) {
                return;
            }
            f(iVar);
        }
    }

    public ServerResponse(b.i iVar, Map<String, List<String>> map, l lVar) {
        this.f4339d = 0;
        this.f4340e = 1;
        this.f4341f = 0;
        this.f4342g = 0;
        this.f4343h = 0;
        this.f4344i = 0;
        this.f4345j = false;
        this.f4346k = true;
        this.f4347l = false;
        this.f4348m = false;
        this.f4349n = false;
        this.f4350o = false;
        this.f4351p = 0;
        this.f4352q = 0;
        this.f4353r = false;
        this.f4354s = 0;
        this.f4355t = new AdLogoInfo();
        this.f4356u = new AdLogoInfo();
        this.f4357v = false;
        this.O = new LinkedList();
        this.T = new LinkedList<>();
        this.U = new LinkedList<>();
        this.V = new HashMap<>();
        this.W = false;
        this.X = false;
        if (iVar == null) {
            HaoboLog.clearLastResponse();
            return;
        }
        HaoboLog.setLastResponse(iVar.toString());
        HaoboLog.d(HaoboLog.httpRespLogTag, HaoboLog.getString(R.string.response_body, HaoboLog.getLastResponse()));
        this.mMediaType = lVar;
        a(map);
        a(iVar);
        b();
    }

    private String a(b.a aVar) {
        if (aVar.a() != e.f.RENDER_H5 && aVar.a() != e.f.RENDER_PIC) {
            return "";
        }
        if (aVar.a() == e.f.RENDER_PIC && aVar.d() > 0) {
            return "<!DOCTYPE html>\n<html lang=\"en\" style=\"width: 100%; height: 100%;\">\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\" id=\"viewport\" content=\"width=device-width, height=device-height, initial-scale=1\">\n    <title>Document</title>\n</head>\n<body style=\"width: 100%; height: 100%; padding: 0; margin: 0;\">\n<img style=\"width: 100%; height: 100%\" src=\"__IMAGE_SRC_PATH__\" alt=\"\"/>\n</body>\n</html><!DOCTYPE html>".replace("__IMAGE_SRC_PATH__", aVar.c().get(0).b());
        }
        Matcher matcher = Pattern.compile("\\{(\\d+)\\.value\\}").matcher(aVar.b());
        HashMap map = new HashMap();
        for (int i2 = 0; i2 < aVar.d(); i2++) {
            map.put(Integer.valueOf(i2), aVar.c().get(i2).b());
        }
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            Integer numValueOf = Integer.valueOf(Integer.parseInt(matcher.group(1)));
            if (map.get(numValueOf) != null) {
                matcher.appendReplacement(stringBuffer, (String) map.get(numValueOf));
            } else {
                matcher.appendReplacement(stringBuffer, "");
                HaoboLog.e(HaoboLog.pbLogTag, HaoboLog.getString(R.string.invalid_string_placeholder, matcher.group(0)));
            }
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    public boolean a() {
        return this.X;
    }

    public ServerResponse(boolean z2) {
        this.f4339d = 0;
        this.f4340e = 1;
        this.f4341f = 0;
        this.f4342g = 0;
        this.f4343h = 0;
        this.f4344i = 0;
        this.f4345j = false;
        this.f4346k = true;
        this.f4347l = false;
        this.f4348m = false;
        this.f4349n = false;
        this.f4350o = false;
        this.f4351p = 0;
        this.f4352q = 0;
        this.f4353r = false;
        this.f4354s = 0;
        this.f4355t = new AdLogoInfo();
        this.f4356u = new AdLogoInfo();
        this.f4357v = false;
        this.O = new LinkedList();
        this.T = new LinkedList<>();
        this.U = new LinkedList<>();
        this.V = new HashMap<>();
        this.W = false;
        this.X = z2;
    }

    public void handleClick(View view, com.beizi.ad.c.c cVar, String str, String str2, boolean z2, String str3, int i2) {
        List<String> listI;
        String strReplaceToTouchEventUrl;
        i.a("BeiZisAd", "handleClick========" + z2);
        if (!z2) {
            if (!StringUtil.isEmpty(this.mDetectClickUrl)) {
                this.mDetectClickUrl = UrlUtil.replaceToTouchEventUrl(this.mDetectClickUrl, cVar, str, str2, "", i2);
                new com.beizi.ad.internal.i(this.mDetectClickUrl).execute(new Void[0]);
                this.mDetectClickUrl = "";
            }
            if (this.K != null && this.L != null) {
                for (int i3 = 0; i3 < this.L.size(); i3++) {
                    b.h hVar = this.L.get(i3);
                    if (hVar != null && !TextUtils.isEmpty(hVar.b())) {
                        if (!TextUtils.isEmpty(str3)) {
                            strReplaceToTouchEventUrl = UrlUtil.replaceToTouchEventUrl(hVar.b(), cVar, str, str2, "", i2).replace("__REQUESTUUID__", str3);
                        } else {
                            strReplaceToTouchEventUrl = UrlUtil.replaceToTouchEventUrl(hVar.b(), cVar, str, str2, "", i2);
                        }
                        new com.beizi.ad.internal.i(StringUtil.replaceClick(view, strReplaceToTouchEventUrl)).executeOnExecutor(com.beizi.ad.a.a.c.b().d(), new Void[0]);
                    }
                }
                this.L = null;
            }
        }
        i.a("BeiZisAd", "mDeepLinkUrl:" + this.R + ",appDownloadURL = " + this.D + ",mLandingPageUrl = " + this.Q);
        if (!TextUtils.isEmpty(this.R)) {
            try {
                if (this.f4360y != null && view != null) {
                    if (com.beizi.ad.a.a.g.a(view.getContext(), this.B)) {
                        listI = this.f4360y.h();
                    } else {
                        listI = this.f4360y.i();
                    }
                    ReportEventUtil.report(listI);
                }
                Uri uri = Uri.parse(Uri.decode(this.R));
                if (uri.getScheme() != null && uri.getScheme().equals("bzopen") && !TextUtils.isEmpty(uri.getHost()) && uri.getPathSegments().size() > 0) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.MAIN");
                    intent.addCategory("android.intent.category.LAUNCHER");
                    String queryParameter = uri.getQueryParameter("flags");
                    if (!TextUtils.isEmpty(queryParameter)) {
                        try {
                            if (!queryParameter.startsWith("0x") && !queryParameter.startsWith("0X")) {
                                intent.setFlags(Integer.parseInt(queryParameter));
                            } else {
                                intent.setFlags(Integer.parseInt(queryParameter.substring(2), 16));
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    intent.setComponent(new ComponentName(uri.getHost(), uri.getPathSegments().get(0)));
                    String queryParameter2 = uri.getQueryParameter("rect");
                    if (!TextUtils.isEmpty(queryParameter2)) {
                        try {
                            String[] strArrSplit = queryParameter2.split(":");
                            if (strArrSplit.length == 4) {
                                Rect rect = new Rect();
                                rect.set(Integer.parseInt(strArrSplit[0]), Integer.parseInt(strArrSplit[1]), Integer.parseInt(strArrSplit[2]), Integer.parseInt(strArrSplit[3]));
                                intent.setSourceBounds(rect);
                            }
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                    if (view != null) {
                        view.getContext().startActivity(intent);
                    }
                } else {
                    Intent intent2 = new Intent("android.intent.action.VIEW", uri);
                    intent2.setFlags(268435456);
                    if (view != null) {
                        view.getContext().startActivity(intent2);
                    }
                }
                b.C0046b.a aVar = this.f4360y;
                if (aVar == null || view == null) {
                    return;
                }
                ReportEventUtil.report(aVar.f());
                return;
            } catch (Exception e4) {
                b.C0046b.a aVar2 = this.f4360y;
                if (aVar2 != null) {
                    ReportEventUtil.report(aVar2.g());
                }
                if (TextUtils.isEmpty(this.Q) || !this.Q.startsWith("http")) {
                    return;
                }
                if (this.f4357v) {
                    Class clsA = AdActivity.a();
                    try {
                        WebView webView = new WebView(new MutableContextWrapper(view.getContext()));
                        WebviewUtil.setWebViewSettings(webView);
                        webView.loadUrl(this.Q);
                        com.beizi.ad.internal.a.a.f3988a.add(webView);
                        Intent intent3 = new Intent(g.a().e(), (Class<?>) clsA);
                        intent3.setFlags(268435456);
                        intent3.putExtra("ACTIVITY_TYPE", "DOWNLOADBROWSER");
                        view.getContext().startActivity(intent3);
                        return;
                    } catch (ActivityNotFoundException unused) {
                        HaoboLog.w(HaoboLog.baseLogTag, HaoboLog.getString(R.string.adactivity_missing, clsA.getName()));
                        com.beizi.ad.internal.a.a.f3988a.remove();
                        return;
                    } catch (Exception unused2) {
                        HaoboLog.e(HaoboLog.baseLogTag, "Exception initializing the redirect webview: " + e4.getMessage());
                        return;
                    }
                }
                try {
                    Intent intent4 = new Intent("android.intent.action.VIEW", Uri.parse(Uri.decode(this.Q)));
                    if (view != null) {
                        view.getContext().startActivity(intent4);
                        return;
                    }
                    return;
                } catch (Exception unused3) {
                    Toast.makeText(view.getContext(), R.string.action_cant_be_completed, 0).show();
                    return;
                }
            }
        }
        if (!TextUtils.isEmpty(this.D) && this.f4361z == 2) {
            if (view != null) {
                if (com.beizi.ad.a.a.g.a(view.getContext(), this.B)) {
                    com.beizi.ad.a.a.g.b(view.getContext(), this.B);
                    b.C0046b.a aVar3 = this.f4360y;
                    if (aVar3 != null) {
                        ReportEventUtil.report(aVar3.a());
                        return;
                    }
                    return;
                }
                File fileA = com.beizi.ad.a.a.g.a(view.getContext());
                String absolutePath = fileA != null ? fileA.getAbsolutePath() : "";
                com.beizi.ad.a.b.a(view.getContext()).b(view.getContext()).a(new com.beizi.ad.a.a(this.D, this.B + Constant.APK_SUFFIX, this.B, absolutePath, this.A, this.C, view.getContext().getPackageName() + ".fileprovider", this.f4360y, this.E, this.F, this.G, this.H, this.I, this.J)).b();
                return;
            }
            return;
        }
        if (!TextUtils.isEmpty(this.Q) && this.Q.startsWith("http")) {
            if (this.f4357v) {
                Class clsA2 = AdActivity.a();
                try {
                    WebView webView2 = new WebView(new MutableContextWrapper(g.a().e()));
                    WebviewUtil.setWebViewSettings(webView2);
                    webView2.loadUrl(this.Q);
                    com.beizi.ad.internal.a.a.f3988a.add(webView2);
                    Intent intent5 = new Intent(g.a().e(), (Class<?>) clsA2);
                    intent5.setFlags(268435456);
                    intent5.putExtra("ACTIVITY_TYPE", "DOWNLOADBROWSER");
                    g.a().e().startActivity(intent5);
                    return;
                } catch (ActivityNotFoundException unused4) {
                    HaoboLog.w(HaoboLog.baseLogTag, HaoboLog.getString(R.string.adactivity_missing, clsA2.getName()));
                    com.beizi.ad.internal.a.a.f3988a.remove();
                    return;
                } catch (Exception e5) {
                    HaoboLog.e(HaoboLog.baseLogTag, "Exception initializing the redirect webview: " + e5.getMessage());
                    return;
                }
            }
            try {
                Intent intent6 = new Intent("android.intent.action.VIEW", Uri.parse(Uri.decode(this.Q)));
                if (view != null) {
                    view.getContext().startActivity(intent6);
                    return;
                }
                return;
            } catch (Exception unused5) {
                Toast.makeText(view.getContext(), R.string.action_cant_be_completed, 0).show();
                return;
            }
        }
        StringUtil.isEmpty(this.S);
    }
}

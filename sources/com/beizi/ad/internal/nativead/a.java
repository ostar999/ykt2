package com.beizi.ad.internal.nativead;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import com.aliyun.auth.core.AliyunVodKey;
import com.azhon.appupdate.config.Constant;
import com.beizi.ad.AdActivity;
import com.beizi.ad.NativeAdResponse;
import com.beizi.ad.R;
import com.beizi.ad.a.a.g;
import com.beizi.ad.c.b;
import com.beizi.ad.internal.h;
import com.beizi.ad.internal.i;
import com.beizi.ad.internal.k;
import com.beizi.ad.internal.network.ServerResponse;
import com.beizi.ad.internal.r;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.JsonUtil;
import com.beizi.ad.internal.utilities.ReportEventUtil;
import com.beizi.ad.internal.utilities.StringUtil;
import com.beizi.ad.internal.utilities.UrlUtil;
import com.beizi.ad.internal.utilities.WebviewUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class a implements NativeAdResponse {
    private ServerResponse.AdLogoInfo A;
    private List<? extends View> B;
    private View D;
    private List<View> E;
    private NativeAdEventListener F;
    private View.OnClickListener G;
    private r H;
    private ArrayList<k> I;
    private long K;
    private int L;
    private String M;
    private String N;
    private String O;
    private String P;
    private String Q;
    private String R;
    private String S;
    private String T;
    private String U;
    private String V;
    private b.C0046b.a W;

    /* renamed from: a, reason: collision with root package name */
    private NativeAdResponse.b f4256a;

    /* renamed from: b, reason: collision with root package name */
    private String f4257b;

    /* renamed from: c, reason: collision with root package name */
    private String f4258c;

    /* renamed from: d, reason: collision with root package name */
    private String f4259d;

    /* renamed from: f, reason: collision with root package name */
    private String f4261f;

    /* renamed from: g, reason: collision with root package name */
    private Bitmap f4262g;

    /* renamed from: h, reason: collision with root package name */
    private Bitmap f4263h;

    /* renamed from: i, reason: collision with root package name */
    private String f4264i;

    /* renamed from: j, reason: collision with root package name */
    private String f4265j;

    /* renamed from: k, reason: collision with root package name */
    private b.C0046b f4266k;

    /* renamed from: l, reason: collision with root package name */
    private String f4267l;

    /* renamed from: m, reason: collision with root package name */
    private double f4268m;

    /* renamed from: n, reason: collision with root package name */
    private String f4269n;

    /* renamed from: o, reason: collision with root package name */
    private String f4270o;

    /* renamed from: p, reason: collision with root package name */
    private String f4271p;

    /* renamed from: q, reason: collision with root package name */
    private HashMap<String, Object> f4272q;

    /* renamed from: z, reason: collision with root package name */
    private ServerResponse.AdLogoInfo f4281z;

    /* renamed from: e, reason: collision with root package name */
    private ArrayList<Bitmap> f4260e = new ArrayList<>();

    /* renamed from: r, reason: collision with root package name */
    private boolean f4273r = false;

    /* renamed from: s, reason: collision with root package name */
    private boolean f4274s = false;

    /* renamed from: t, reason: collision with root package name */
    private boolean f4275t = false;

    /* renamed from: u, reason: collision with root package name */
    private ArrayList<String> f4276u = new ArrayList<>();

    /* renamed from: v, reason: collision with root package name */
    private ArrayList<String> f4277v = new ArrayList<>();

    /* renamed from: w, reason: collision with root package name */
    private ArrayList<String> f4278w = new ArrayList<>();

    /* renamed from: x, reason: collision with root package name */
    private ArrayList<String> f4279x = new ArrayList<>();

    /* renamed from: y, reason: collision with root package name */
    private ArrayList<String> f4280y = new ArrayList<>();
    private Runnable C = new Runnable() { // from class: com.beizi.ad.internal.nativead.a.1
        @Override // java.lang.Runnable
        public void run() {
            Log.e("expireRunnable", "expireRunnable");
            a.this.f4273r = true;
            a.this.D = null;
            a.this.E = null;
            if (a.this.H != null) {
                a.this.H.d();
                a.this.H = null;
            }
            a.this.I = null;
            a.this.F = null;
            if (a.this.f4263h != null) {
                a.this.f4263h.recycle();
                a.this.f4263h = null;
            }
            if (a.this.f4262g != null) {
                a.this.f4262g.recycle();
                a.this.f4262g = null;
            }
        }
    };
    private String J = "";
    private boolean X = false;

    @Override // com.beizi.ad.NativeAdResponse
    public void destroy() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.removeCallbacks(this.C);
        handler.post(this.C);
    }

    @Override // com.beizi.ad.NativeAdResponse
    public ServerResponse.AdLogoInfo getAdUrl() {
        return this.f4281z;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public String getAdvertiser() {
        return this.f4271p;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public String getBody() {
        return this.f4258c;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public String getCallToAction() {
        return this.f4267l;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public ArrayList<String> getClickTrackers() {
        return this.f4280y;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public String getHeadline() {
        return this.f4257b;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public Bitmap getIcon() {
        return this.f4263h;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public String getIconUrl() {
        return this.f4261f;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public Bitmap getImage() {
        return this.f4262g;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public String getImageUrl() {
        return this.f4259d;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public ArrayList<String> getImageUrls() {
        return this.f4276u;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public ArrayList<String> getImpTrackers() {
        return this.f4279x;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public String getLandingPageUrl() {
        return this.f4264i;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public NativeAdResponse.b getNativeAdType() {
        return this.f4256a;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public HashMap<String, Object> getNativeElements() {
        return this.f4272q;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public List<? extends View> getNativeInfoListView() {
        return this.B;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public NativeAdResponse.a getNetworkIdentifier() {
        return NativeAdResponse.a.BeiZi;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public String getPrice() {
        return this.f4270o;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public double getStarRating() {
        return this.f4268m;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public String getStore() {
        return this.f4269n;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public ArrayList<String> getTexts() {
        return this.f4278w;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public ArrayList<String> getVedioUrls() {
        return this.f4277v;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public ServerResponse.AdLogoInfo getlogoUrl() {
        return this.A;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public void handleClick(Context context) {
        if (context == null) {
            Log.d("lance", "handleClick  context is null");
            return;
        }
        if (!this.f4275t) {
            Iterator<String> it = this.f4280y.iterator();
            while (it.hasNext()) {
                new i(it.next()).executeOnExecutor(com.beizi.ad.a.a.c.b().d(), new Void[0]);
            }
            this.f4280y.clear();
            this.f4275t = true;
        }
        if (a(this.f4265j, this.f4264i, context)) {
            return;
        }
        Log.d("lance", "Unable to handle click.");
    }

    @Override // com.beizi.ad.NativeAdResponse
    public boolean hasExpired() {
        return this.f4273r;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public void regesterClickListener(View view, final NativeAdEventListener nativeAdEventListener) {
        view.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.ad.internal.nativead.a.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (a.this.f4280y != null) {
                    Iterator it = a.this.f4280y.iterator();
                    while (it.hasNext()) {
                        new i((String) it.next()).execute(new Void[0]);
                    }
                }
                nativeAdEventListener.onAdWasClicked();
                a aVar = a.this;
                if (!aVar.a(aVar.f4265j, a.this.f4264i, view2.getContext())) {
                    HaoboLog.d(HaoboLog.nativeLogTag, "Unable to handle click.");
                }
                a.this.f4280y = null;
            }
        });
    }

    @Override // com.beizi.ad.NativeAdResponse
    public boolean regesterShow(View view) {
        if (this.f4273r || view == null) {
            return false;
        }
        r rVar = this.H;
        if (rVar != null) {
            rVar.d();
        }
        Object tag = view.getTag(55449210);
        if (tag != null && (tag instanceof r)) {
            ((r) tag).d();
        }
        r rVarA = r.a(view);
        this.H = rVarA;
        if (rVarA == null) {
            return false;
        }
        view.setTag(55449210, rVarA);
        this.I = new ArrayList<>();
        Iterator<String> it = this.f4279x.iterator();
        while (it.hasNext()) {
            this.I.add(k.a(this.J, it.next(), this.H, view.getContext(), this.f4279x));
        }
        this.D = view;
        new Handler(Looper.getMainLooper()).removeCallbacks(this.C);
        return true;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public boolean registerView(final View view, NativeAdEventListener nativeAdEventListener) {
        if (this.f4273r || view == null) {
            return false;
        }
        this.F = nativeAdEventListener;
        r rVar = this.H;
        if (rVar != null) {
            rVar.d();
        }
        Object tag = view.getTag(55449210);
        if (tag != null && (tag instanceof r)) {
            ((r) tag).d();
        }
        r rVarA = r.a(view);
        this.H = rVarA;
        view.setTag(55449210, rVarA);
        if (this.H == null) {
            return false;
        }
        this.I = new ArrayList<>();
        Iterator<String> it = this.f4279x.iterator();
        while (it.hasNext()) {
            this.I.add(k.a(this.J, it.next(), this.H, view.getContext(), this.f4279x));
        }
        this.D = view;
        final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.OnGestureListener() { // from class: com.beizi.ad.internal.nativead.a.3
            @Override // android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent motionEvent) {
                a.this.K = System.currentTimeMillis();
                return false;
            }

            @Override // android.view.GestureDetector.OnGestureListener
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
                return false;
            }

            @Override // android.view.GestureDetector.OnGestureListener
            public void onLongPress(MotionEvent motionEvent) {
            }

            @Override // android.view.GestureDetector.OnGestureListener
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
                return false;
            }

            @Override // android.view.GestureDetector.OnGestureListener
            public void onShowPress(MotionEvent motionEvent) {
            }

            @Override // android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                if (a.this.f4280y != null) {
                    float x2 = motionEvent.getX();
                    float y2 = motionEvent.getY();
                    float rawX = motionEvent.getRawX();
                    float rawY = motionEvent.getRawY();
                    Iterator it2 = a.this.f4280y.iterator();
                    while (it2.hasNext()) {
                        String strReplaceToTouchEventUrl = UrlUtil.replaceToTouchEventUrl((String) it2.next(), x2 + "", y2 + "", rawX + "", rawY + "", String.valueOf(a.this.K), String.valueOf(System.currentTimeMillis()), "", 0);
                        if (!TextUtils.isEmpty(a.this.J)) {
                            strReplaceToTouchEventUrl = strReplaceToTouchEventUrl.replace("__REQUESTUUID__", a.this.J);
                        }
                        new i(StringUtil.replaceClick(view, strReplaceToTouchEventUrl)).execute(new Void[0]);
                    }
                }
                a.this.f4280y = null;
                return false;
            }
        });
        a();
        view.setOnTouchListener(new View.OnTouchListener() { // from class: com.beizi.ad.internal.nativead.a.4
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
        view.setOnClickListener(this.G);
        new Handler(Looper.getMainLooper()).removeCallbacks(this.C);
        return true;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public boolean registerViewList(final View view, List<View> list, NativeAdEventListener nativeAdEventListener) {
        if (!registerView(view, nativeAdEventListener)) {
            return false;
        }
        if (list == null || list.size() <= 0) {
            return true;
        }
        view.setOnClickListener(null);
        for (View view2 : list) {
            final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.OnGestureListener() { // from class: com.beizi.ad.internal.nativead.a.5
                @Override // android.view.GestureDetector.OnGestureListener
                public boolean onDown(MotionEvent motionEvent) {
                    a.this.K = System.currentTimeMillis();
                    return false;
                }

                @Override // android.view.GestureDetector.OnGestureListener
                public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
                    return false;
                }

                @Override // android.view.GestureDetector.OnGestureListener
                public void onLongPress(MotionEvent motionEvent) {
                }

                @Override // android.view.GestureDetector.OnGestureListener
                public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
                    return false;
                }

                @Override // android.view.GestureDetector.OnGestureListener
                public void onShowPress(MotionEvent motionEvent) {
                }

                @Override // android.view.GestureDetector.OnGestureListener
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    if (a.this.f4280y != null) {
                        float x2 = motionEvent.getX();
                        float y2 = motionEvent.getY();
                        float rawX = motionEvent.getRawX();
                        float rawY = motionEvent.getRawY();
                        Iterator it = a.this.f4280y.iterator();
                        while (it.hasNext()) {
                            String strReplaceToTouchEventUrl = UrlUtil.replaceToTouchEventUrl((String) it.next(), x2 + "", y2 + "", rawX + "", rawY + "", String.valueOf(a.this.K), String.valueOf(System.currentTimeMillis()), "", 0);
                            if (!TextUtils.isEmpty(a.this.J)) {
                                strReplaceToTouchEventUrl = strReplaceToTouchEventUrl.replace("__REQUESTUUID__", a.this.J);
                            }
                            new i(StringUtil.replaceClick(view, strReplaceToTouchEventUrl)).execute(new Void[0]);
                        }
                    }
                    a.this.f4280y = null;
                    return false;
                }
            });
            view2.setOnTouchListener(new View.OnTouchListener() { // from class: com.beizi.ad.internal.nativead.a.6
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view3, MotionEvent motionEvent) {
                    return gestureDetector.onTouchEvent(motionEvent);
                }
            });
            view2.setOnClickListener(this.G);
        }
        this.E = list;
        return true;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public void sendClickLog() {
        if (this.f4275t) {
            return;
        }
        Iterator<String> it = this.f4280y.iterator();
        while (it.hasNext()) {
            new i(it.next()).executeOnExecutor(com.beizi.ad.a.a.c.b().d(), new Void[0]);
        }
        this.f4280y.clear();
        this.f4275t = true;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public void sendImpLog() {
        if (this.f4274s) {
            return;
        }
        Iterator<String> it = this.f4279x.iterator();
        while (it.hasNext()) {
            new i(it.next()).executeOnExecutor(com.beizi.ad.a.a.c.b().d(), new Void[0]);
        }
        this.f4279x.clear();
        this.f4274s = true;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public void setIcon(Bitmap bitmap) {
        this.f4263h = bitmap;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public void setImage(Bitmap bitmap) {
        this.f4262g = bitmap;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public void setNativeInfoListView(List<? extends View> list) {
        this.B = list;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public void unregisterViews() {
        View view = this.D;
        if (view != null) {
            view.setOnClickListener(null);
        }
        List<View> list = this.E;
        if (list != null && !list.isEmpty()) {
            Iterator<View> it = this.E.iterator();
            while (it.hasNext()) {
                it.next().setOnClickListener(null);
            }
        }
        destroy();
    }

    public void c(String str) {
        this.f4265j = str;
    }

    public void d(String str) {
        this.f4279x.add(str);
    }

    public void e(String str) {
        this.f4280y.add(str);
    }

    public void b(ServerResponse.AdLogoInfo adLogoInfo) {
        this.A = adLogoInfo;
    }

    public void b(String str) {
        this.f4264i = str;
    }

    private boolean b(String str, Context context) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        intent.setFlags(268435456);
        try {
            context.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException unused) {
            HaoboLog.w(HaoboLog.baseLogTag, HaoboLog.getString(R.string.opening_url_failed, str));
            return false;
        }
    }

    @Override // com.beizi.ad.NativeAdResponse
    public void handleClick(Context context, View view, String str, String str2, String str3, String str4, int i2) {
        ArrayList<String> arrayList = this.f4280y;
        if (arrayList != null) {
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                String strReplaceToTouchEventUrl = UrlUtil.replaceToTouchEventUrl(it.next(), str + "", str2 + "", str3 + "", str4 + "", String.valueOf(System.currentTimeMillis()), String.valueOf(System.currentTimeMillis()), "", i2);
                if (!TextUtils.isEmpty(this.J)) {
                    strReplaceToTouchEventUrl = strReplaceToTouchEventUrl.replace("__REQUESTUUID__", this.J);
                }
                new i(StringUtil.replaceClick(view, strReplaceToTouchEventUrl)).execute(new Void[0]);
            }
        }
        this.f4280y = null;
        if (!a(this.f4265j, this.f4264i, context)) {
            Log.d("lance", "Unable to handle click.");
        }
        NativeAdEventListener nativeAdEventListener = this.F;
        if (nativeAdEventListener != null) {
            nativeAdEventListener.onAdWasClicked();
        }
    }

    public void a(ServerResponse.AdLogoInfo adLogoInfo) {
        this.f4281z = adLogoInfo;
    }

    public void a(String str) {
        this.J = str;
    }

    public static a a(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        ArrayList<String> stringArrayList = JsonUtil.getStringArrayList(JsonUtil.getJSONArray(jSONObject, "ImpressionTrackers"));
        a aVar = new a();
        if (stringArrayList != null) {
            aVar.f4279x = stringArrayList;
        }
        aVar.f4257b = JsonUtil.getJSONString(jSONObject, "Headline");
        aVar.f4258c = JsonUtil.getJSONString(jSONObject, "Body");
        aVar.f4259d = JsonUtil.getJSONString(jSONObject, "Image");
        JSONArray jSONArray = JsonUtil.getJSONArray(jSONObject, "Images");
        JSONArray jSONArray2 = JsonUtil.getJSONArray(jSONObject, "Videos");
        JSONArray jSONArray3 = JsonUtil.getJSONArray(jSONObject, "Texts");
        if (jSONArray != null) {
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                aVar.f4276u.add((String) jSONArray.get(i2));
            }
        }
        if (jSONArray2 != null) {
            for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                aVar.f4277v.add((String) jSONArray2.get(i3));
            }
        }
        if (jSONArray3 != null) {
            for (int i4 = 0; i4 < jSONArray3.length(); i4++) {
                aVar.f4278w.add((String) jSONArray3.get(i4));
            }
        }
        if (jSONObject.has("AppIcon")) {
            aVar.f4256a = NativeAdResponse.b.APP_INSTALL;
            aVar.f4261f = JsonUtil.getJSONString(jSONObject, "AppIcon");
            aVar.f4267l = JsonUtil.getJSONString(jSONObject, AliyunVodKey.KEY_VOD_ACTION);
            aVar.f4268m = JsonUtil.getJSONDouble(jSONObject, "Star");
            aVar.f4269n = JsonUtil.getJSONString(jSONObject, "Store");
            aVar.f4270o = JsonUtil.getJSONString(jSONObject, "Price");
        } else {
            aVar.f4256a = NativeAdResponse.b.CONTENT;
            aVar.f4261f = JsonUtil.getJSONString(jSONObject, "Logo");
            aVar.f4267l = JsonUtil.getJSONString(jSONObject, AliyunVodKey.KEY_VOD_ACTION);
            aVar.f4271p = JsonUtil.getJSONString(jSONObject, "Advertiser");
        }
        ArrayList<String> stringArrayList2 = JsonUtil.getStringArrayList(JsonUtil.getJSONArray(jSONObject, "ClickTrackers"));
        if (stringArrayList2 != null) {
            aVar.f4280y = stringArrayList2;
        }
        aVar.f4272q = JsonUtil.getStringObjectHashMap(JsonUtil.getJSONObject(jSONObject, "Custom"));
        new Handler(Looper.getMainLooper()).postDelayed(aVar.C, com.heytap.mcssdk.constant.a.f7141e);
        return aVar;
    }

    @Override // com.beizi.ad.NativeAdResponse
    public boolean regesterShow(View view, NativeAdShownListener nativeAdShownListener) {
        if (!this.f4273r && view != null) {
            r rVar = this.H;
            if (rVar != null) {
                rVar.d();
            }
            Object tag = view.getTag(55449210);
            if (tag != null && (tag instanceof r)) {
                ((r) tag).d();
            }
            r rVarA = r.a(view);
            this.H = rVarA;
            if (rVarA == null) {
                return false;
            }
            view.setTag(55449210, rVarA);
            h.a(view, nativeAdShownListener);
            this.I = new ArrayList<>();
            Iterator<String> it = this.f4279x.iterator();
            while (it.hasNext()) {
                this.I.add(k.a(this.J, it.next(), this.H, view.getContext(), this.f4279x));
            }
            this.D = view;
            new Handler(Looper.getMainLooper()).removeCallbacks(this.C);
        }
        return false;
    }

    public void a(b.C0046b c0046b) {
        this.f4266k = c0046b;
        this.W = c0046b.j();
        this.L = c0046b.c();
        this.M = c0046b.d();
        this.N = c0046b.e();
        this.O = c0046b.f();
        this.P = c0046b.g();
        if (TextUtils.isEmpty(this.N)) {
            this.N = "lance";
        }
        if (TextUtils.isEmpty(this.M)) {
            this.M = "BeiZi";
        }
        if (TextUtils.isEmpty(this.O)) {
            this.O = "Ad Download";
        }
        this.Q = c0046b.m();
        this.R = c0046b.n();
        this.S = c0046b.o();
        this.T = c0046b.p();
        this.U = c0046b.q();
        this.V = c0046b.r();
    }

    public void a(boolean z2) {
        this.X = z2;
    }

    private void a() {
        this.G = new View.OnClickListener() { // from class: com.beizi.ad.internal.nativead.a.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (a.this.f4280y != null) {
                    Iterator it = a.this.f4280y.iterator();
                    while (it.hasNext()) {
                        String str = (String) it.next();
                        com.beizi.ad.a.a.i.a("lance", "setClickListener:" + str);
                        new i(str).execute(new Void[0]);
                    }
                }
                a aVar = a.this;
                if (!aVar.a(aVar.f4265j, a.this.f4264i, view.getContext())) {
                    HaoboLog.d(HaoboLog.nativeLogTag, "Unable to handle click.");
                }
                if (a.this.F != null) {
                    a.this.F.onAdWasClicked();
                }
                a.this.f4280y = null;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(String str, String str2, Context context) {
        List<String> listI;
        if (!TextUtils.isEmpty(str)) {
            try {
                if (this.W != null) {
                    if (g.a(context, this.N)) {
                        listI = this.W.h();
                    } else {
                        listI = this.W.i();
                    }
                    ReportEventUtil.report(listI);
                }
                Uri uri = Uri.parse(str);
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
                    if (context != null) {
                        context.startActivity(intent);
                    }
                } else {
                    Intent intent2 = new Intent("android.intent.action.VIEW", uri);
                    intent2.setFlags(268435456);
                    if (context != null) {
                        context.startActivity(intent2);
                    }
                }
                b.C0046b.a aVar = this.W;
                if (aVar != null) {
                    ReportEventUtil.report(aVar.f());
                }
                return true;
            } catch (Exception unused) {
                b.C0046b.a aVar2 = this.W;
                if (aVar2 != null) {
                    ReportEventUtil.report(aVar2.g());
                }
                return a(str2, context);
            }
        }
        if (!TextUtils.isEmpty(this.P) && this.L == 2) {
            if (g.a(context, this.N)) {
                g.b(context, this.N);
                b.C0046b.a aVar3 = this.W;
                if (aVar3 != null) {
                    ReportEventUtil.report(aVar3.a());
                }
                return true;
            }
            File fileA = g.a(context);
            String absolutePath = fileA != null ? fileA.getAbsolutePath() : "";
            com.beizi.ad.a.b.a(context).b(context).a(new com.beizi.ad.a.a(this.P, this.N + Constant.APK_SUFFIX, this.N, absolutePath, this.M, this.O, context.getPackageName() + ".fileprovider", this.W, this.Q, this.R, this.S, this.T, this.U, this.V)).b();
            return true;
        }
        return a(str2, context);
    }

    private boolean a(String str, Context context) {
        if (str != null && !str.isEmpty()) {
            if (!this.X) {
                if (!b(str, context)) {
                    return false;
                }
                NativeAdEventListener nativeAdEventListener = this.F;
                if (nativeAdEventListener != null) {
                    nativeAdEventListener.onAdWillLeaveApplication();
                }
                return true;
            }
            Class clsA = AdActivity.a();
            try {
                WebView webView = new WebView(new MutableContextWrapper(context));
                WebviewUtil.setWebViewSettings(webView);
                webView.loadUrl(str);
                com.beizi.ad.internal.a.a.f3988a.add(webView);
                Intent intent = new Intent(context, (Class<?>) clsA);
                intent.setFlags(268435456);
                intent.putExtra("ACTIVITY_TYPE", "DOWNLOADBROWSER");
                context.startActivity(intent);
                return true;
            } catch (ActivityNotFoundException unused) {
                HaoboLog.w(HaoboLog.baseLogTag, HaoboLog.getString(R.string.adactivity_missing, clsA.getName()));
                com.beizi.ad.internal.a.a.f3988a.remove();
            } catch (Exception e2) {
                HaoboLog.e(HaoboLog.baseLogTag, "Exception initializing the redirect webview: " + e2.getMessage());
                return false;
            }
        }
        return false;
    }
}

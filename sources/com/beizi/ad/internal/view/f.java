package com.beizi.ad.internal.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Pair;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import cn.hutool.core.text.CharPool;
import com.beizi.ad.AdActivity;
import com.beizi.ad.R;
import com.beizi.ad.internal.l;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.StringUtil;
import com.beizi.ad.internal.utilities.ViewUtil;
import com.beizi.ad.internal.utilities.W3CEvent;
import com.beizi.ad.internal.view.AdWebView;
import com.hjq.permissions.Permission;
import com.psychiatrygarden.utils.CommonParameter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import org.eclipse.jetty.servlet.ServletHandler;

@SuppressLint({"InlinedApi"})
/* loaded from: classes2.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    protected static final String[] f4658a = {ServletHandler.__DEFAULT_SERVLET, "expanded"};

    /* renamed from: b, reason: collision with root package name */
    protected final AdWebView f4659b;

    /* renamed from: e, reason: collision with root package name */
    int f4662e;

    /* renamed from: f, reason: collision with root package name */
    int f4663f;

    /* renamed from: g, reason: collision with root package name */
    int f4664g;

    /* renamed from: j, reason: collision with root package name */
    boolean f4667j;

    /* renamed from: l, reason: collision with root package name */
    private int f4669l;

    /* renamed from: m, reason: collision with root package name */
    private int f4670m;

    /* renamed from: n, reason: collision with root package name */
    private Activity f4671n;

    /* renamed from: o, reason: collision with root package name */
    private ViewGroup f4672o;

    /* renamed from: q, reason: collision with root package name */
    private int f4674q;

    /* renamed from: k, reason: collision with root package name */
    private boolean f4668k = false;

    /* renamed from: c, reason: collision with root package name */
    boolean f4660c = false;

    /* renamed from: d, reason: collision with root package name */
    boolean f4661d = false;

    /* renamed from: h, reason: collision with root package name */
    boolean f4665h = false;

    /* renamed from: i, reason: collision with root package name */
    boolean f4666i = false;

    /* renamed from: p, reason: collision with root package name */
    private int[] f4673p = new int[4];

    /* renamed from: r, reason: collision with root package name */
    private boolean f4675r = false;

    /* renamed from: s, reason: collision with root package name */
    private g f4676s = null;

    /* renamed from: com.beizi.ad.internal.view.f$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4683a;

        static {
            int[] iArr = new int[AdActivity.b.values().length];
            f4683a = iArr;
            try {
                iArr[AdActivity.b.landscape.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4683a[AdActivity.b.portrait.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4683a[AdActivity.b.none.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public enum a {
        top_left,
        top_right,
        center,
        bottom_left,
        bottom_right,
        top_center,
        bottom_center
    }

    public enum b {
        STARTING_DEFAULT,
        STARTING_EXPANDED
    }

    public f(AdWebView adWebView) {
        this.f4659b = adWebView;
    }

    private void c(ArrayList<Pair<String, String>> arrayList) {
        Iterator<Pair<String, String>> it = arrayList.iterator();
        String strDecode = null;
        while (it.hasNext()) {
            Pair<String, String> next = it.next();
            if (((String) next.first).equals("uri")) {
                strDecode = Uri.decode((String) next.second);
            }
        }
        if (StringUtil.isEmpty(strDecode)) {
            return;
        }
        this.f4659b.b(strDecode);
        this.f4659b.c();
    }

    private void d(ArrayList<Pair<String, String>> arrayList) {
        Iterator<Pair<String, String>> it = arrayList.iterator();
        String str = null;
        while (it.hasNext()) {
            Pair<String, String> next = it.next();
            if (((String) next.first).equals("uri")) {
                str = (String) next.second;
            }
        }
        if (str == null) {
            HaoboLog.d(HaoboLog.mraidLogTag, HaoboLog.getString(R.string.store_picture_error));
            return;
        }
        final String strDecode = Uri.decode(str);
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewUtil.getTopContext(this.f4659b));
        builder.setTitle(R.string.store_picture_title);
        builder.setMessage(R.string.store_picture_message);
        builder.setPositiveButton(R.string.store_picture_accept, new DialogInterface.OnClickListener() { // from class: com.beizi.ad.internal.view.f.2
            /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
                jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:33:0x00c1
                	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1178)
                	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
                	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
                	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
                */
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:14:0x0039  */
            /* JADX WARN: Type inference failed for: r7v30 */
            /* JADX WARN: Type inference failed for: r7v31, types: [java.lang.String] */
            /* JADX WARN: Type inference failed for: r7v32 */
            /* JADX WARN: Type inference failed for: r7v35 */
            /* JADX WARN: Type inference failed for: r7v36 */
            /* JADX WARN: Type inference failed for: r7v37, types: [java.lang.String] */
            /* JADX WARN: Type inference failed for: r7v38 */
            /* JADX WARN: Type inference failed for: r7v39 */
            /* JADX WARN: Type inference failed for: r7v40, types: [java.io.FileOutputStream] */
            /* JADX WARN: Type inference failed for: r7v41 */
            /* JADX WARN: Type inference failed for: r7v43 */
            /* JADX WARN: Type inference failed for: r7v44 */
            /* JADX WARN: Type inference failed for: r7v45 */
            /* JADX WARN: Type inference failed for: r7v47 */
            /* JADX WARN: Type inference failed for: r7v59 */
            /* JADX WARN: Type inference failed for: r7v60 */
            /* JADX WARN: Type inference failed for: r7v61 */
            /* JADX WARN: Type inference failed for: r7v64 */
            /* JADX WARN: Type inference failed for: r7v65 */
            /* JADX WARN: Type inference failed for: r7v66 */
            /* JADX WARN: Type inference failed for: r7v67 */
            /* JADX WARN: Type inference failed for: r7v68 */
            @Override // android.content.DialogInterface.OnClickListener
            @android.annotation.SuppressLint({"NewApi"})
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onClick(android.content.DialogInterface r7, int r8) {
                /*
                    Method dump skipped, instructions count: 405
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.beizi.ad.internal.view.f.AnonymousClass2.onClick(android.content.DialogInterface, int):void");
            }
        });
        builder.setNegativeButton(R.string.store_picture_decline, new DialogInterface.OnClickListener() { // from class: com.beizi.ad.internal.view.f.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
            }
        });
        builder.create().show();
    }

    private void e() {
        Activity activity = (Activity) this.f4659b.getContextFromMutableContext();
        int[] iArr = new int[2];
        this.f4659b.getLocationOnScreen(iArr);
        iArr[1] = iArr[1] - activity.getWindow().findViewById(android.R.id.content).getTop();
        this.f4659b.measure(0, 0);
        int[] iArr2 = {this.f4659b.getMeasuredWidth(), this.f4659b.getMeasuredHeight()};
        ViewUtil.convertFromPixelsToDP(activity, iArr2);
        this.f4659b.c(String.format("javascript:window.mraid.util.setDefaultPosition(%d, %d, %d, %d)", Integer.valueOf(iArr[0]), Integer.valueOf(iArr[1]), Integer.valueOf(iArr2[0]), Integer.valueOf(iArr2[1])));
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void f(java.util.ArrayList<android.util.Pair<java.lang.String, java.lang.String>> r2) {
        /*
            r1 = this;
            if (r2 == 0) goto L1f
            int r0 = r2.size()     // Catch: java.io.UnsupportedEncodingException -> L1e
            if (r0 <= 0) goto L1f
            r0 = 0
            java.lang.Object r2 = r2.get(r0)     // Catch: java.io.UnsupportedEncodingException -> L1e
            android.util.Pair r2 = (android.util.Pair) r2     // Catch: java.io.UnsupportedEncodingException -> L1e
            java.lang.Object r2 = r2.second     // Catch: java.io.UnsupportedEncodingException -> L1e
            java.lang.String r2 = (java.lang.String) r2     // Catch: java.io.UnsupportedEncodingException -> L1e
            java.lang.String r0 = "UTF-8"
            java.lang.String r2 = java.net.URLDecoder.decode(r2, r0)     // Catch: java.io.UnsupportedEncodingException -> L1e
            com.beizi.ad.internal.utilities.W3CEvent r2 = com.beizi.ad.internal.utilities.W3CEvent.createFromJSON(r2)     // Catch: java.io.UnsupportedEncodingException -> L1e
            goto L20
        L1e:
            return
        L1f:
            r2 = 0
        L20:
            if (r2 == 0) goto L44
            android.content.Intent r2 = r2.getInsertIntent()     // Catch: android.content.ActivityNotFoundException -> L44
            r0 = 268435456(0x10000000, float:2.524355E-29)
            r2.setFlags(r0)     // Catch: android.content.ActivityNotFoundException -> L44
            com.beizi.ad.internal.view.AdWebView r0 = r1.f4659b     // Catch: android.content.ActivityNotFoundException -> L44
            android.content.Context r0 = r0.getContext()     // Catch: android.content.ActivityNotFoundException -> L44
            r0.startActivity(r2)     // Catch: android.content.ActivityNotFoundException -> L44
            com.beizi.ad.internal.view.AdWebView r2 = r1.f4659b     // Catch: android.content.ActivityNotFoundException -> L44
            r2.c()     // Catch: android.content.ActivityNotFoundException -> L44
            java.lang.String r2 = com.beizi.ad.internal.utilities.HaoboLog.mraidLogTag     // Catch: android.content.ActivityNotFoundException -> L44
            int r0 = com.beizi.ad.R.string.create_calendar_event     // Catch: android.content.ActivityNotFoundException -> L44
            java.lang.String r0 = com.beizi.ad.internal.utilities.HaoboLog.getString(r0)     // Catch: android.content.ActivityNotFoundException -> L44
            com.beizi.ad.internal.utilities.HaoboLog.d(r2, r0)     // Catch: android.content.ActivityNotFoundException -> L44
        L44:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beizi.ad.internal.view.f.f(java.util.ArrayList):void");
    }

    private void g(ArrayList<Pair<String, String>> arrayList) {
        AdActivity.b bVarA = AdActivity.b.none;
        Iterator<Pair<String, String>> it = arrayList.iterator();
        int i2 = 1;
        boolean z2 = true;
        while (it.hasNext()) {
            Pair<String, String> next = it.next();
            if (((String) next.first).equals("allow_orientation_change")) {
                z2 = Boolean.parseBoolean((String) next.second);
            } else if (((String) next.first).equals("force_orientation")) {
                bVarA = a((String) next.second);
            }
        }
        if (this.f4660c || this.f4659b.adViewImpl.e()) {
            AdWebView adWebView = this.f4659b;
            Activity activityC = adWebView.f4567a ? c() : (Activity) ViewUtil.getTopContext(adWebView);
            if (z2) {
                AdActivity.b(activityC);
                return;
            }
            int i3 = AnonymousClass4.f4683a[bVarA.ordinal()];
            if (i3 == 1) {
                i2 = 2;
            } else if (i3 != 2) {
                i2 = 0;
            }
            AdActivity.a(activityC, i2);
        }
    }

    private void h(ArrayList<Pair<String, String>> arrayList) throws NumberFormatException {
        Iterator<Pair<String, String>> it = arrayList.iterator();
        int i2 = -1;
        int i3 = 0;
        String str = "top-right";
        boolean z2 = true;
        int i4 = -1;
        int i5 = 0;
        while (it.hasNext()) {
            Pair<String, String> next = it.next();
            try {
                if (((String) next.first).equals("w")) {
                    i2 = Integer.parseInt((String) next.second);
                } else if (((String) next.first).equals("h")) {
                    i4 = Integer.parseInt((String) next.second);
                } else if (((String) next.first).equals("offset_x")) {
                    i5 = Integer.parseInt((String) next.second);
                } else if (((String) next.first).equals("offset_y")) {
                    i3 = Integer.parseInt((String) next.second);
                } else if (((String) next.first).equals("custom_close_position")) {
                    str = (String) next.second;
                } else if (((String) next.first).equals("allow_offscreen")) {
                    z2 = Boolean.parseBoolean((String) next.second);
                }
            } catch (NumberFormatException unused) {
                HaoboLog.d(HaoboLog.mraidLogTag, HaoboLog.getString(R.string.number_format));
                return;
            }
        }
        if (i2 > this.f4669l && i4 > this.f4670m) {
            this.f4659b.c("javascript:mraid.util.errorEvent('Resize called with resizeProperties larger than the screen.', 'mraid.resize()')");
            return;
        }
        a aVarValueOf = a.top_right;
        try {
            aVarValueOf = a.valueOf(str.replace(CharPool.DASHED, '_'));
        } catch (IllegalArgumentException unused2) {
        }
        int i6 = i2;
        int i7 = i4;
        int i8 = i5;
        int i9 = i3;
        boolean z3 = z2;
        HaoboLog.d(HaoboLog.mraidLogTag, HaoboLog.getString(R.string.resize, i6, i7, i8, i9, str, z3));
        this.f4659b.resize(i6, i7, i8, i9, aVarValueOf, z3);
        this.f4659b.c();
        this.f4659b.c("javascript:window.mraid.util.stateChangeEvent('resized');");
        this.f4661d = true;
    }

    public void b(ArrayList<Pair<String, String>> arrayList) throws NumberFormatException {
        AdActivity.b bVar = AdActivity.b.none;
        Iterator<Pair<String, String>> it = arrayList.iterator();
        String strDecode = null;
        final AdActivity.b bVarA = bVar;
        int i2 = -1;
        int i3 = -1;
        boolean z2 = false;
        final boolean z3 = true;
        while (it.hasNext()) {
            Pair<String, String> next = it.next();
            if (((String) next.first).equals("w")) {
                try {
                    i2 = Integer.parseInt((String) next.second);
                } catch (NumberFormatException unused) {
                }
            } else if (((String) next.first).equals("h")) {
                i3 = Integer.parseInt((String) next.second);
            } else if (((String) next.first).equals("useCustomClose")) {
                z2 = Boolean.parseBoolean((String) next.second);
            } else if (((String) next.first).equals("url")) {
                strDecode = Uri.decode((String) next.second);
            } else if (((String) next.first).equals("allow_orientation_change")) {
                z3 = Boolean.parseBoolean((String) next.second);
            } else if (((String) next.first).equals("force_orientation")) {
                bVarA = a((String) next.second);
            }
        }
        if (StringUtil.isEmpty(strDecode)) {
            this.f4659b.a(i2, i3, z2, this, z3, bVarA);
        } else {
            try {
                g gVar = new g(this.f4659b.adViewImpl, this);
                this.f4676s = gVar;
                gVar.a(strDecode);
                this.f4659b.adViewImpl.a(this.f4676s.getMRAIDImplementation(), z2, new AdWebView.b() { // from class: com.beizi.ad.internal.view.f.1
                    @Override // com.beizi.ad.internal.view.AdWebView.b
                    public void a() {
                        if (f.this.c() != null) {
                            f.this.f4676s.a(f.this.c(), z3, bVarA);
                            AdViewImpl.setMRAIDFullscreenListener(null);
                        }
                    }
                });
            } catch (Exception e2) {
                HaoboLog.e(HaoboLog.baseLogTag, "Exception initializing the redirect webview: " + e2.getMessage());
            }
        }
        this.f4659b.c("javascript:window.mraid.util.stateChangeEvent('expanded');");
        this.f4660c = true;
        if (this.f4659b.adViewImpl.e()) {
            return;
        }
        this.f4659b.adViewImpl.getAdDispatcher().c();
    }

    public void a(AdWebView adWebView, String str) {
        if (this.f4668k) {
            return;
        }
        String str2 = this.f4659b.adViewImpl.d() ? "inline" : "interstitial";
        boolean zEquals = str.equals(f4658a[b.STARTING_EXPANDED.ordinal()]);
        this.f4675r = zEquals;
        this.f4659b.f4567a = zEquals;
        adWebView.c("javascript:window.mraid.util.setPlacementType('" + str2 + "')");
        if (!this.f4675r) {
            a(adWebView);
            g();
            f();
            e();
        }
        this.f4659b.f();
        adWebView.c("javascript:window.mraid.util.stateChangeEvent('" + str + "')");
        adWebView.c("javascript:window.mraid.util.readyEvent();");
        this.f4662e = this.f4659b.getLayoutParams().width;
        this.f4663f = this.f4659b.getLayoutParams().height;
        if (this.f4659b.adViewImpl.getMediaType().equals(l.BANNER)) {
            this.f4664g = ((FrameLayout.LayoutParams) this.f4659b.getLayoutParams()).gravity;
        }
        this.f4668k = true;
        a(this.f4659b.g());
    }

    public Activity c() {
        return this.f4671n;
    }

    private void f() {
        if (this.f4659b.getContextFromMutableContext() instanceof Activity) {
            Activity activity = (Activity) this.f4659b.getContextFromMutableContext();
            int[] screenSizeAsPixels = ViewUtil.getScreenSizeAsPixels(activity);
            int i2 = screenSizeAsPixels[0];
            int top2 = screenSizeAsPixels[1] - activity.getWindow().findViewById(android.R.id.content).getTop();
            float f2 = activity.getResources().getDisplayMetrics().density;
            int i3 = (int) ((top2 / f2) + 0.5f);
            int i4 = (int) ((i2 / f2) + 0.5f);
            this.f4659b.c("javascript:window.mraid.util.setMaxSize(" + i4 + ", " + i3 + ")");
        }
    }

    private void e(ArrayList<Pair<String, String>> arrayList) {
        Iterator<Pair<String, String>> it = arrayList.iterator();
        String str = null;
        while (it.hasNext()) {
            Pair<String, String> next = it.next();
            if (((String) next.first).equals("uri")) {
                str = (String) next.second;
            }
        }
        if (str == null) {
            HaoboLog.d(HaoboLog.mraidLogTag, HaoboLog.getString(R.string.play_vide_no_uri));
            return;
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setFlags(268435456);
        try {
            intent.setDataAndType(Uri.parse(URLDecoder.decode(str, "UTF-8")), "video/mp4");
            try {
                this.f4659b.getContext().startActivity(intent);
                this.f4659b.c();
            } catch (ActivityNotFoundException unused) {
            }
        } catch (UnsupportedEncodingException unused2) {
            HaoboLog.d(HaoboLog.mraidLogTag, HaoboLog.getString(R.string.unsupported_encoding));
        }
    }

    private void g() {
        if (this.f4659b.getContextFromMutableContext() instanceof Activity) {
            int[] screenSizeAsDP = ViewUtil.getScreenSizeAsDP((Activity) this.f4659b.getContextFromMutableContext());
            this.f4669l = screenSizeAsDP[0];
            this.f4670m = screenSizeAsDP[1];
            this.f4659b.c("javascript:window.mraid.util.setScreenSize(" + this.f4669l + ", " + this.f4670m + ")");
        }
    }

    public ViewGroup d() {
        return this.f4672o;
    }

    private void a(AdWebView adWebView, String str, boolean z2) {
        adWebView.c(String.format("javascript:window.mraid.util.setSupports('%s', %s)", str, String.valueOf(z2)));
    }

    @SuppressLint({"NewApi"})
    private void a(AdWebView adWebView) {
        if (a(new Intent("android.intent.action.VIEW", Uri.parse("sms:5555555555")))) {
            a(adWebView, "sms", true);
        }
        if (a(new Intent("android.intent.action.VIEW", Uri.parse("tel:5555555555")))) {
            a(adWebView, CommonParameter.TEL, true);
        }
        if (a(new Intent("android.intent.action.EDIT").setData(CalendarContract.Events.CONTENT_URI))) {
            a(adWebView, "calendar", true);
            this.f4666i = true;
        } else if (a(new Intent("android.intent.action.EDIT").setType("vnd.android.cursor.item/event"))) {
            a(adWebView, "calendar", true);
            this.f4666i = true;
            W3CEvent.useMIME = true;
        }
        if (this.f4659b.getContext().getPackageManager().checkPermission(Permission.WRITE_EXTERNAL_STORAGE, this.f4659b.getContext().getPackageName()) == 0) {
            a(adWebView, "storePicture", true);
            this.f4665h = true;
        }
        a(adWebView, "inlineVideo", true);
    }

    public void b() {
        boolean zG = this.f4659b.g();
        if (this.f4667j != zG) {
            a(zG);
        }
    }

    public boolean a(Intent intent) {
        return this.f4659b.getContext().getPackageManager().queryIntentActivities(intent, 0).size() > 0;
    }

    public void a(boolean z2) {
        if (this.f4668k) {
            this.f4667j = z2;
            this.f4659b.c("javascript:window.mraid.util.setIsViewable(" + z2 + ")");
        }
    }

    public void a(int i2, int i3, int i4, int i5) {
        int[] iArr = this.f4673p;
        if (iArr[0] == i2 && iArr[1] == i3 && iArr[2] == i4 && iArr[3] == i5) {
            return;
        }
        iArr[0] = i2;
        iArr[1] = i3;
        iArr[2] = i4;
        iArr[3] = i5;
        Activity activity = (Activity) this.f4659b.getContextFromMutableContext();
        int[] iArr2 = {i2, i3 - activity.getWindow().findViewById(android.R.id.content).getTop(), i4, i5};
        ViewUtil.convertFromPixelsToDP(activity, iArr2);
        int i6 = iArr2[0];
        int i7 = iArr2[1];
        int i8 = iArr2[2];
        int i9 = iArr2[3];
        this.f4659b.c(String.format("javascript:window.mraid.util.setCurrentPosition(%d, %d, %d, %d)", Integer.valueOf(i6), Integer.valueOf(i7), Integer.valueOf(i8), Integer.valueOf(i9)));
        this.f4659b.c(String.format("javascript:window.mraid.util.sizeChangeEvent(%d, %d)", Integer.valueOf(i8), Integer.valueOf(i9)));
    }

    public void a() {
        if (!this.f4660c && !this.f4661d && !this.f4675r) {
            if (this.f4659b.adViewImpl.e()) {
                this.f4659b.adViewImpl.getAdDispatcher().b();
                Activity activity = (Activity) this.f4659b.getContextFromMutableContext();
                if (activity == null || activity.isFinishing()) {
                    return;
                }
                activity.finish();
                return;
            }
            this.f4659b.d();
            return;
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(this.f4659b.getLayoutParams());
        layoutParams.height = this.f4663f;
        layoutParams.width = this.f4662e;
        if (this.f4659b.adViewImpl.getMediaType().equals(l.BANNER)) {
            layoutParams.gravity = this.f4664g;
        } else {
            layoutParams.gravity = 17;
        }
        this.f4659b.setLayoutParams(layoutParams);
        this.f4659b.e();
        this.f4659b.c("javascript:window.mraid.util.stateChangeEvent('default');");
        if (!this.f4659b.adViewImpl.e() && !this.f4675r) {
            this.f4659b.adViewImpl.getAdDispatcher().b();
        }
        Activity activity2 = (Activity) this.f4659b.getContextFromMutableContext();
        if (activity2 != null) {
            activity2.setRequestedOrientation(-1);
        }
        this.f4660c = false;
        this.f4661d = false;
        this.f4676s = null;
    }

    public void a(ArrayList<Pair<String, String>> arrayList) {
        this.f4659b.setMRAIDUseCustomClose(Boolean.parseBoolean((String) arrayList.get(0).second));
    }

    public void a(String str, boolean z2) {
        String strReplaceFirst = str.replaceFirst("mraid://", "");
        String[] strArrSplit = strReplaceFirst.split("\\?");
        String strReplaceAll = strArrSplit[0].replaceAll("/", "");
        ArrayList<Pair<String, String>> arrayList = new ArrayList<>();
        if (strArrSplit.length > 1) {
            for (String str2 : strReplaceFirst.substring(strReplaceFirst.indexOf("?") + 1).split("&")) {
                String[] strArrSplit2 = str2.split("=");
                if (strArrSplit2.length >= 2 && !StringUtil.isEmpty(strArrSplit2[1]) && !"undefined".equals(strArrSplit2[1])) {
                    arrayList.add(new Pair<>(strArrSplit2[0], strArrSplit2[1]));
                }
            }
        }
        if (strReplaceAll.equals("expand")) {
            if (z2) {
                b(arrayList);
                return;
            } else {
                HaoboLog.w(HaoboLog.mraidLogTag, HaoboLog.getString(R.string.no_user_interaction, strReplaceFirst));
                return;
            }
        }
        if (strReplaceAll.equals("close")) {
            a();
            return;
        }
        if (strReplaceAll.equals("resize")) {
            if (z2) {
                h(arrayList);
                return;
            } else {
                HaoboLog.w(HaoboLog.mraidLogTag, HaoboLog.getString(R.string.no_user_interaction, strReplaceFirst));
                return;
            }
        }
        if (strReplaceAll.equals("setOrientationProperties")) {
            g(arrayList);
            return;
        }
        if (this.f4666i && strReplaceAll.equals("createCalendarEvent")) {
            if (z2) {
                f(arrayList);
                return;
            } else {
                HaoboLog.w(HaoboLog.mraidLogTag, HaoboLog.getString(R.string.no_user_interaction, strReplaceFirst));
                return;
            }
        }
        if (strReplaceAll.equals("playVideo")) {
            if (z2) {
                e(arrayList);
                return;
            } else {
                HaoboLog.w(HaoboLog.mraidLogTag, HaoboLog.getString(R.string.no_user_interaction, strReplaceFirst));
                return;
            }
        }
        if (this.f4665h && strReplaceAll.equals("storePicture")) {
            if (z2) {
                d(arrayList);
                return;
            } else {
                HaoboLog.w(HaoboLog.mraidLogTag, HaoboLog.getString(R.string.no_user_interaction, strReplaceFirst));
                return;
            }
        }
        if (strReplaceAll.equals("open")) {
            if (z2) {
                c(arrayList);
                return;
            } else {
                HaoboLog.w(HaoboLog.mraidLogTag, HaoboLog.getString(R.string.no_user_interaction, strReplaceFirst));
                return;
            }
        }
        if (strReplaceAll.equals("setUseCustomClose")) {
            a(arrayList);
        } else {
            if (strReplaceAll.equals("enable")) {
                return;
            }
            HaoboLog.d(HaoboLog.mraidLogTag, HaoboLog.getString(R.string.unsupported_mraid, strReplaceAll));
        }
    }

    private AdActivity.b a(String str) {
        AdActivity.b bVar = AdActivity.b.none;
        if (str.equals("landscape")) {
            return AdActivity.b.landscape;
        }
        return str.equals("portrait") ? AdActivity.b.portrait : bVar;
    }

    public void a(int i2) {
        if (this.f4674q != i2) {
            this.f4674q = i2;
            f();
            g();
        }
    }

    public void a(Activity activity) {
        this.f4671n = activity;
    }

    public void a(ViewGroup viewGroup) {
        this.f4672o = viewGroup;
    }
}

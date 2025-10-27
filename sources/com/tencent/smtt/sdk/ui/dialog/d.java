package com.tencent.smtt.sdk.ui.dialog;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.psychiatrygarden.utils.MimeTypes;
import com.tencent.connect.common.Constants;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsConfig;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.stat.MttLoader;
import com.tencent.smtt.utils.FileUtil;
import com.tencent.smtt.utils.TbsLog;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class d extends Dialog {

    /* renamed from: a, reason: collision with root package name */
    static WeakReference<ValueCallback<String>> f21329a;

    /* renamed from: b, reason: collision with root package name */
    protected List<b> f21330b;

    /* renamed from: c, reason: collision with root package name */
    protected final String f21331c;

    /* renamed from: d, reason: collision with root package name */
    protected final String f21332d;

    /* renamed from: e, reason: collision with root package name */
    protected final String f21333e;

    /* renamed from: f, reason: collision with root package name */
    protected final String f21334f;

    /* renamed from: g, reason: collision with root package name */
    private ListView f21335g;

    /* renamed from: h, reason: collision with root package name */
    private Button f21336h;

    /* renamed from: i, reason: collision with root package name */
    private Button f21337i;

    /* renamed from: j, reason: collision with root package name */
    private final String f21338j;

    /* renamed from: k, reason: collision with root package name */
    private String f21339k;

    /* renamed from: l, reason: collision with root package name */
    private a f21340l;

    /* renamed from: m, reason: collision with root package name */
    private String f21341m;

    /* renamed from: n, reason: collision with root package name */
    private String f21342n;

    /* renamed from: o, reason: collision with root package name */
    private Intent f21343o;

    /* renamed from: p, reason: collision with root package name */
    private SharedPreferences f21344p;

    /* renamed from: q, reason: collision with root package name */
    private int f21345q;

    /* renamed from: r, reason: collision with root package name */
    private int f21346r;

    /* renamed from: s, reason: collision with root package name */
    private FrameLayout f21347s;

    /* renamed from: t, reason: collision with root package name */
    private LinearLayout f21348t;

    public d(Context context, String str, Intent intent, Bundle bundle, ValueCallback<String> valueCallback, String str2, String str3) {
        List<b> list;
        List<b> list2;
        super(context, R.style.Theme.Dialog);
        this.f21338j = "TBSActivityPicker";
        this.f21331c = "extraMenu";
        this.f21332d = "name";
        this.f21333e = "resource_id";
        this.f21334f = "value";
        this.f21341m = MimeTypes.ANY_TYPE;
        String str4 = null;
        this.f21344p = null;
        this.f21345q = 0;
        this.f21346r = 0;
        this.f21342n = str3;
        List<ResolveInfo> listQueryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 65536);
        TbsLog.i("TBSActivityPicker", "acts.size(): " + listQueryIntentActivities.size());
        Bundle bundle2 = bundle != null ? bundle.getBundle("extraMenu") : null;
        if (bundle2 != null) {
            this.f21330b = new ArrayList();
            Iterator<String> it = bundle2.keySet().iterator();
            while (it.hasNext()) {
                Bundle bundle3 = bundle2.getBundle(it.next());
                if (bundle3 != null) {
                    String string = bundle3.getString("name", str4);
                    int i2 = bundle3.getInt("resource_id", -1);
                    String string2 = bundle3.getString("value", str4);
                    if (string != null && i2 != -1 && string2 != null) {
                        this.f21330b.add(new b(getContext(), i2, string, string2));
                    }
                }
                str4 = null;
            }
        } else {
            TbsLog.i("TBSActivityPicker", "no extra menu info in bundle");
        }
        if (listQueryIntentActivities.size() == 0 && (((list2 = this.f21330b) == null || list2.isEmpty()) && MttLoader.isBrowserInstalled(context))) {
            TbsLog.i("TBSActivityPicker", "no action has been found with Intent:" + intent.toString());
            QbSdk.isDefaultDialog = true;
        }
        if ("com.tencent.rtxlite".equalsIgnoreCase(context.getApplicationContext().getPackageName()) && listQueryIntentActivities.size() == 0 && ((list = this.f21330b) == null || list.isEmpty())) {
            TbsLog.i("TBSActivityPicker", "package name equal to `com.tencent.rtxlite` but no action has been found with Intent:" + intent.toString());
            QbSdk.isDefaultDialog = true;
        }
        this.f21339k = str;
        this.f21343o = intent;
        f21329a = new WeakReference<>(valueCallback);
        this.f21344p = context.getSharedPreferences(QbSdk.SHARE_PREFERENCES_NAME, 0);
        if (!TextUtils.isEmpty(str2)) {
            this.f21341m = str2;
        }
        TbsLog.i("TBSActivityPicker", "Intent:" + this.f21341m + " MineType:" + this.f21341m);
    }

    private View a(Context context) {
        this.f21347s = new FrameLayout(context);
        this.f21348t = new LinearLayout(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, Double.valueOf(c.a(context) * 0.5f).intValue());
        layoutParams.gravity = 17;
        this.f21348t.setLayoutParams(layoutParams);
        this.f21348t.setOrientation(1);
        this.f21346r = c.a(context, 72.0f);
        com.tencent.smtt.sdk.ui.dialog.widget.a aVar = new com.tencent.smtt.sdk.ui.dialog.widget.a(context, c.a(context, 12.0f), c.a(context, 35.0f), c.a(context, 15.0f));
        aVar.setLayoutParams(new LinearLayout.LayoutParams(-1, this.f21346r));
        aVar.setOnClickListener(new View.OnClickListener() { // from class: com.tencent.smtt.sdk.ui.dialog.d.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                d.this.dismiss();
            }
        });
        this.f21348t.addView(aVar);
        ListView listView = new ListView(context);
        this.f21335g = listView;
        listView.setOverScrollMode(2);
        this.f21335g.setVerticalScrollBarEnabled(false);
        this.f21335g.setBackgroundColor(-1);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -1);
        layoutParams2.weight = 1.0f;
        layoutParams2.gravity = 16;
        this.f21335g.setLayoutParams(layoutParams2);
        this.f21335g.setDividerHeight(0);
        this.f21348t.addView(this.f21335g);
        LinearLayout linearLayout = new LinearLayout(context);
        this.f21345q = c.a(context, 150.0f);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, this.f21345q);
        layoutParams3.weight = 0.0f;
        linearLayout.setLayoutParams(layoutParams3);
        linearLayout.setOrientation(0);
        linearLayout.setBackgroundColor(-1);
        linearLayout.setContentDescription("x5_tbs_activity_picker_btn_container");
        this.f21336h = new com.tencent.smtt.sdk.ui.dialog.widget.b(context, c.a(context, 12.0f), Color.parseColor("#EBEDF5"));
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-1, c.a(context, 90.0f));
        layoutParams4.weight = 1.0f;
        layoutParams4.topMargin = c.a(context, 30.0f);
        layoutParams4.bottomMargin = c.a(context, 30.0f);
        layoutParams4.leftMargin = c.a(context, 32.0f);
        layoutParams4.rightMargin = c.a(context, 8.0f);
        this.f21336h.setLayoutParams(layoutParams4);
        this.f21336h.setText(e.b("x5_tbs_wechat_activity_picker_label_always"));
        this.f21336h.setTextColor(Color.rgb(29, 29, 29));
        this.f21336h.setTextSize(0, c.a(context, 34.0f));
        linearLayout.addView(this.f21336h);
        this.f21337i = new com.tencent.smtt.sdk.ui.dialog.widget.b(context, c.a(context, 12.0f), Color.parseColor("#00CAFC"));
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(-1, c.a(context, 90.0f));
        layoutParams5.weight = 1.0f;
        layoutParams5.topMargin = c.a(context, 30.0f);
        layoutParams5.bottomMargin = c.a(context, 30.0f);
        layoutParams5.leftMargin = c.a(context, 8.0f);
        layoutParams5.rightMargin = c.a(context, 32.0f);
        this.f21337i.setLayoutParams(layoutParams5);
        this.f21337i.setText(e.b("x5_tbs_wechat_activity_picker_label_once"));
        this.f21337i.setTextColor(Color.rgb(255, 255, 255));
        this.f21337i.setTextSize(0, c.a(context, 34.0f));
        linearLayout.addView(this.f21337i);
        this.f21348t.addView(linearLayout);
        this.f21347s.addView(this.f21348t);
        return this.f21347s;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(b bVar) {
        StringBuilder sb;
        StringBuilder sb2;
        if (bVar.f()) {
            boolean zMatches = Build.CPU_ABI.matches("armeabi.*");
            if (c() && f21329a.get() != null) {
                if (zMatches) {
                    sb2 = new StringBuilder();
                    sb2.append("https://mdc.html5.qq.com/d/directdown.jsp?channel_id=11047");
                    sb2.append("&is64=0");
                } else {
                    sb2 = new StringBuilder();
                    sb2.append("https://mdc.html5.qq.com/d/directdown.jsp?channel_id=11047");
                    sb2.append("&is64=1");
                }
                f21329a.get().onReceiveValue(sb2.toString());
                return;
            }
            if (zMatches) {
                sb = new StringBuilder();
                sb.append("https://mdc.html5.qq.com/d/directdown.jsp?channel_id=11041");
                sb.append("&is64=0");
            } else {
                sb = new StringBuilder();
                sb.append("https://mdc.html5.qq.com/d/directdown.jsp?channel_id=11041");
                sb.append("&is64=1");
            }
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(sb.toString()));
            intent.addFlags(268435456);
            try {
                getContext().startActivity(intent);
            } catch (Throwable unused) {
                Toast.makeText(getContext(), "您的设备尚未安装QQ浏览器，请先下载", 1).show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        ValueCallback<String> valueCallback;
        String str2;
        ValueCallback<String> valueCallback2;
        StringBuilder sb;
        String strD;
        ActivityInfo activityInfo;
        if (this.f21340l == null || !c()) {
            return;
        }
        b bVarA = this.f21340l.a();
        ResolveInfo resolveInfoA = this.f21340l.a(bVarA);
        if (f21329a.get() != null) {
            if (bVarA != null && resolveInfoA != null && (activityInfo = resolveInfoA.activityInfo) != null && activityInfo.packageName != null) {
                valueCallback = f21329a.get();
                str2 = str + resolveInfoA.activityInfo.packageName;
            } else {
                if (bVarA != null) {
                    if (bVarA.e()) {
                        valueCallback2 = f21329a.get();
                        sb = new StringBuilder();
                        sb.append(str);
                        strD = bVarA.g();
                    } else {
                        if (!bVarA.f()) {
                            return;
                        }
                        valueCallback2 = f21329a.get();
                        sb = new StringBuilder();
                        sb.append(str);
                        strD = bVarA.d();
                    }
                    sb.append(strD);
                    valueCallback2.onReceiveValue(sb.toString());
                    return;
                }
                valueCallback = f21329a.get();
                str2 = str + "other";
            }
            valueCallback.onReceiveValue(str2);
        }
    }

    private Drawable c(String str) {
        Context context = getContext();
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        File file = new File(context.getFilesDir(), str);
        if (!FileUtil.c(file)) {
            return null;
        }
        try {
            TbsLog.i("TBSActivityPicker", "load icon from: " + file.getAbsolutePath());
            return new BitmapDrawable(BitmapFactory.decodeFile(file.getAbsolutePath()));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c() {
        String[] strArr = {"com.tencent.mobileqq", Constants.PACKAGE_TIM};
        String packageName = getContext().getApplicationContext().getPackageName();
        for (int i2 = 0; i2 < 2; i2++) {
            if (strArr[i2].equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    private void d() {
        String string;
        String strB;
        a aVar = this.f21340l;
        Drawable drawableA = null;
        b bVarA = aVar != null ? aVar.a() : null;
        SharedPreferences sharedPreferences = this.f21344p;
        if (sharedPreferences != null) {
            Drawable drawableC = c(sharedPreferences.getString("key_tbs_recommend_icon_url", null));
            string = this.f21344p.getString("key_tbs_recommend_label", null);
            strB = this.f21344p.getString("key_tbs_recommend_description", null);
            if (TextUtils.isEmpty(string)) {
                string = null;
            }
            if (TextUtils.isEmpty(strB)) {
                strB = null;
            }
            drawableA = drawableC;
        } else {
            string = null;
            strB = null;
        }
        if (drawableA == null) {
            drawableA = e.a("application_icon");
        }
        Drawable drawable = drawableA;
        if (string == null) {
            string = "QQ浏览器";
        }
        String str = string;
        if (strB == null) {
            strB = e.b("x5_tbs_wechat_activity_picker_label_recommend");
        }
        a aVar2 = new a(getContext(), this.f21343o, new b(getContext(), drawable, str, TbsConfig.APP_QB, strB), this.f21330b, bVarA, this, this.f21335g);
        this.f21340l = aVar2;
        this.f21335g.setAdapter((ListAdapter) aVar2);
        e();
    }

    private void e() {
        ListAdapter adapter = this.f21335g.getAdapter();
        if (adapter == null) {
            return;
        }
        int measuredHeight = 0;
        for (int i2 = 0; i2 < adapter.getCount(); i2++) {
            View view = adapter.getView(i2, null, this.f21335g);
            view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
            measuredHeight += view.getMeasuredHeight();
        }
        float fA = c.a(getContext()) * 0.8f;
        float fA2 = c.a(getContext()) * 0.5f;
        float f2 = this.f21346r + measuredHeight + this.f21345q;
        this.f21348t.getLayoutParams().height = (f2 > fA ? Float.valueOf(fA) : f2 < fA2 ? Float.valueOf(fA2) : Float.valueOf(f2)).intValue();
    }

    public String a() {
        if (this.f21344p == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("getTBSPickedDefaultBrowser: ");
        sb.append(this.f21344p.getString("key_tbs_picked_default_browser_" + this.f21341m, null));
        TbsLog.i("TBSActivityPicker", sb.toString());
        return this.f21344p.getString("key_tbs_picked_default_browser_" + this.f21341m, null);
    }

    public void a(String str) {
        SharedPreferences.Editor editorPutString;
        TbsLog.i("TBSActivityPicker", "setTBSPickedDefaultBrowser:" + str);
        if (this.f21344p != null) {
            if (TextUtils.isEmpty(str)) {
                TbsLog.i("TBSActivityPicker", "paramString empty, remove: key_tbs_picked_default_browser_" + this.f21341m);
                editorPutString = this.f21344p.edit().remove("key_tbs_picked_default_browser_" + this.f21341m);
            } else {
                TbsLog.i("TBSActivityPicker", "paramString not empty, set: key_tbs_picked_default_browser_" + this.f21341m + "=" + str);
                SharedPreferences.Editor editorEdit = this.f21344p.edit();
                StringBuilder sb = new StringBuilder();
                sb.append("key_tbs_picked_default_browser_");
                sb.append(this.f21341m);
                editorPutString = editorEdit.putString(sb.toString(), str);
            }
            editorPutString.commit();
        }
    }

    public void a(boolean z2) {
        Button button = this.f21337i;
        if (button != null) {
            button.setEnabled(z2);
        }
        Button button2 = this.f21336h;
        if (button2 != null) {
            button2.setEnabled(z2);
        }
        b("userMenuClickEvent:");
    }

    public void b() {
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.setGravity(80);
            window.setLayout(-1, -2);
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = -1;
            attributes.horizontalMargin = 0.0f;
            attributes.dimAmount = 0.5f;
            window.setAttributes(attributes);
        }
        setContentView(a(getContext()));
        d();
        this.f21336h.setOnClickListener(new View.OnClickListener() { // from class: com.tencent.smtt.sdk.ui.dialog.d.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                b bVarA = d.this.f21340l.a();
                ResolveInfo resolveInfoA = d.this.f21340l.a(bVarA);
                d.this.b("userClickAlwaysEvent:");
                if (bVarA == null) {
                    return;
                }
                if (bVarA.e()) {
                    String strG = bVarA.g();
                    if (d.f21329a.get() != null) {
                        d.f21329a.get().onReceiveValue("extraMenuEvent:" + strG);
                    }
                    d.this.a("extraMenuEvent:" + strG);
                } else if (resolveInfoA == null) {
                    d.this.a(bVarA);
                } else {
                    Intent intent = d.this.f21343o;
                    Context context = d.this.getContext();
                    String str = resolveInfoA.activityInfo.packageName;
                    intent.setPackage(str);
                    if (TbsConfig.APP_QB.equals(str)) {
                        intent.putExtra("ChannelID", context.getApplicationContext().getPackageName());
                        intent.putExtra("PosID", "4");
                    }
                    if (context != null && context.getApplicationInfo().targetSdkVersion >= 24 && Build.VERSION.SDK_INT >= 24) {
                        intent.addFlags(1);
                    }
                    if (!TextUtils.isEmpty(d.this.f21342n)) {
                        intent.putExtra("big_brother_source_key", d.this.f21342n);
                    }
                    try {
                        context.startActivity(intent);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    if (d.f21329a.get() != null) {
                        d.f21329a.get().onReceiveValue("always");
                    }
                    d.this.a(str);
                }
                d.this.dismiss();
            }
        });
        this.f21337i.setOnClickListener(new View.OnClickListener() { // from class: com.tencent.smtt.sdk.ui.dialog.d.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                b bVarA = d.this.f21340l.a();
                ResolveInfo resolveInfoA = d.this.f21340l.a(bVarA);
                d.this.b("userClickOnceEvent:");
                d.this.a("");
                if (bVarA == null) {
                    return;
                }
                if (bVarA.e()) {
                    if (d.this.c() && d.f21329a.get() != null) {
                        d.f21329a.get().onReceiveValue("extraMenuEvent:" + bVarA.g());
                    }
                } else if (resolveInfoA == null) {
                    d.this.a(bVarA);
                } else {
                    Intent intent = d.this.f21343o;
                    Context context = d.this.getContext();
                    String str = resolveInfoA.activityInfo.packageName;
                    intent.setPackage(str);
                    if (TbsConfig.APP_QB.equals(str)) {
                        intent.putExtra("ChannelID", context.getApplicationContext().getPackageName());
                        intent.putExtra("PosID", "4");
                    }
                    if (context.getApplicationInfo().targetSdkVersion >= 24 && Build.VERSION.SDK_INT >= 24) {
                        intent.addFlags(1);
                    }
                    if (!TextUtils.isEmpty(d.this.f21342n)) {
                        intent.putExtra("big_brother_source_key", d.this.f21342n);
                    }
                    try {
                        context.startActivity(intent);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    if (d.f21329a.get() != null) {
                        d.f21329a.get().onReceiveValue("once");
                    }
                }
                d.this.dismiss();
            }
        });
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        b();
    }
}

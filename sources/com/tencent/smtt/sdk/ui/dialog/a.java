package com.tencent.smtt.sdk.ui.dialog;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tencent.smtt.sdk.TbsConfig;
import com.tencent.smtt.sdk.stat.MttLoader;
import com.tencent.smtt.sdk.ui.dialog.widget.RoundImageView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import kotlin.text.Typography;

/* loaded from: classes6.dex */
public class a extends ArrayAdapter<b> implements View.OnClickListener, ListAdapter {

    /* renamed from: a, reason: collision with root package name */
    private ArrayList<b> f21305a;

    /* renamed from: b, reason: collision with root package name */
    private b f21306b;

    /* renamed from: c, reason: collision with root package name */
    private Intent f21307c;

    /* renamed from: d, reason: collision with root package name */
    private WeakReference<ListView> f21308d;

    /* renamed from: e, reason: collision with root package name */
    private WeakReference<d> f21309e;

    /* renamed from: f, reason: collision with root package name */
    private b f21310f;

    /* renamed from: g, reason: collision with root package name */
    private b f21311g;

    /* renamed from: h, reason: collision with root package name */
    private List<b> f21312h;

    /* renamed from: i, reason: collision with root package name */
    private Handler f21313i;

    /* renamed from: j, reason: collision with root package name */
    private String[] f21314j;

    public a(Context context, Intent intent, b bVar, List<b> list, b bVar2, d dVar, ListView listView) {
        super(context, 0);
        this.f21306b = null;
        a(dVar);
        a(listView);
        this.f21311g = bVar;
        this.f21307c = intent;
        if ("com.tencent.rtxlite".equalsIgnoreCase(context.getApplicationContext().getPackageName()) || MttLoader.isBrowserInstalled(context)) {
            this.f21310f = null;
        } else {
            this.f21310f = this.f21311g;
        }
        this.f21312h = list;
        this.f21313i = new Handler() { // from class: com.tencent.smtt.sdk.ui.dialog.a.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                a.this.b();
            }
        };
        String[] strArr = new String[2];
        this.f21314j = strArr;
        strArr[0] = e.b("x5_tbs_activity_picker_recommend_to_trim");
        this.f21314j[1] = e.b("x5_tbs_activity_picker_recommend_with_chinese_brace_to_trim");
        a(context, bVar2);
    }

    private View a(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
        linearLayout.setOrientation(1);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{R.attr.state_pressed}, new ColorDrawable(Color.argb(41, 0, 0, 0)));
        stateListDrawable.addState(new int[]{-16842919}, new ColorDrawable(0));
        linearLayout.setBackgroundDrawable(stateListDrawable);
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, c.a(getContext(), 144.0f)));
        RoundImageView roundImageView = new RoundImageView(context);
        roundImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(c.a(getContext(), 96.0f), c.a(getContext(), 96.0f));
        layoutParams.addRule(9);
        layoutParams.addRule(15);
        layoutParams.setMargins(c.a(getContext(), 32.0f), c.a(getContext(), 24.0f), c.a(getContext(), 24.0f), c.a(getContext(), 24.0f));
        roundImageView.setLayoutParams(layoutParams);
        roundImageView.setId(101);
        relativeLayout.addView(roundImageView);
        LinearLayout linearLayout2 = new LinearLayout(context);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.addRule(15);
        layoutParams2.addRule(1, 101);
        linearLayout2.setLayoutParams(layoutParams2);
        linearLayout2.setOrientation(1);
        TextView textView = new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        textView.setMaxLines(1);
        textView.setTextColor(Color.rgb(29, 29, 29));
        textView.setTextSize(1, 17.0f);
        textView.setId(102);
        linearLayout2.addView(textView);
        TextView textView2 = new TextView(context);
        textView2.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        textView2.setText(e.b("x5_tbs_wechat_activity_picker_label_recommend"));
        textView2.setTextColor(Color.parseColor("#00CAFC"));
        textView2.setTextSize(1, 14.0f);
        textView2.setId(103);
        linearLayout2.addView(textView2);
        relativeLayout.addView(linearLayout2);
        ImageView imageView = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(c.a(getContext(), 48.0f), c.a(getContext(), 48.0f));
        layoutParams3.addRule(11);
        layoutParams3.addRule(15);
        layoutParams3.setMargins(0, 0, c.a(getContext(), 32.0f), 0);
        imageView.setLayoutParams(layoutParams3);
        imageView.setImageDrawable(e.a("x5_tbs_activity_picker_check"));
        imageView.setId(104);
        relativeLayout.addView(imageView);
        relativeLayout.setId(105);
        linearLayout.addView(relativeLayout);
        return linearLayout;
    }

    private void a(b bVar, View view) {
        if (view == null || bVar == null) {
            return;
        }
        ImageView imageView = (ImageView) view.findViewById(101);
        TextView textView = (TextView) view.findViewById(102);
        TextView textView2 = (TextView) view.findViewById(103);
        ImageView imageView2 = (ImageView) view.findViewById(104);
        View viewFindViewById = view.findViewById(105);
        View viewFindViewById2 = view.findViewById(106);
        imageView.setImageDrawable(bVar.a());
        String strReplaceAll = bVar.b().trim().replaceAll(Typography.nbsp + "", "");
        String[] strArr = this.f21314j;
        int length = strArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            String str = strArr[i2];
            if (str != null && str.length() > 0) {
                strReplaceAll = strReplaceAll.replaceAll(str, "");
            }
        }
        textView.setText(strReplaceAll);
        if (bVar.c() == null) {
            bVar.a(a(bVar));
        }
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.tencent.smtt.sdk.ui.dialog.a.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                Object parent = view2.getParent();
                if (parent instanceof View) {
                    View view3 = (View) parent;
                    if (view3.getTag() == a.this.f21310f) {
                        a.this.onClick(view3);
                    }
                }
            }
        });
        if (TbsConfig.APP_QB.equals(bVar.d())) {
            textView2.setVisibility(0);
            textView2.setText(bVar.h());
        } else {
            textView2.setVisibility(8);
        }
        viewFindViewById.setClickable(false);
        viewFindViewById.setEnabled(false);
        if (bVar == this.f21306b) {
            imageView2.setVisibility(0);
            if (viewFindViewById2 != null) {
                viewFindViewById2.setVisibility(0);
            }
        } else {
            imageView2.setVisibility(8);
            if (viewFindViewById2 != null) {
                viewFindViewById2.setVisibility(8);
            }
        }
        view.setTag(bVar);
        view.setOnClickListener(this);
    }

    private void a(boolean z2) {
        d dVar;
        WeakReference<d> weakReference = this.f21309e;
        if (weakReference == null || (dVar = weakReference.get()) == null) {
            return;
        }
        dVar.a(z2);
    }

    public static boolean a(Context context, String str) throws PackageManager.NameNotFoundException {
        if (str != null && !"".equals(str)) {
            try {
                context.getPackageManager().getApplicationInfo(str, 8192);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return false;
    }

    private void b(Context context, b bVar) {
        this.f21306b = bVar;
        if (bVar == null) {
            return;
        }
        a((bVar.e() || this.f21306b.f()) ? true : a(context, this.f21306b.d()));
    }

    public ResolveInfo a(b bVar) {
        if (bVar != null && !TextUtils.isEmpty(bVar.d())) {
            for (ResolveInfo resolveInfo : getContext().getPackageManager().queryIntentActivities(this.f21307c, 65536)) {
                if (bVar.d().equals(resolveInfo.activityInfo.packageName)) {
                    return resolveInfo;
                }
            }
        }
        return null;
    }

    public b a() {
        return this.f21306b;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public b getItem(int i2) {
        if (i2 < 0 || i2 >= this.f21305a.size()) {
            return null;
        }
        return this.f21305a.get(i2);
    }

    public void a(Context context, b bVar) {
        b bVar2;
        this.f21305a = new ArrayList<>();
        List<b> list = this.f21312h;
        if (list != null && list.size() != 0) {
            this.f21305a.addAll(this.f21312h);
        }
        boolean z2 = false;
        boolean z3 = false;
        for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentActivities(this.f21307c, 65536)) {
            if (b.a(context, resolveInfo.activityInfo.packageName) != null || resolveInfo.loadIcon(context.getPackageManager()) != null) {
                b bVar3 = new b(context, resolveInfo);
                b bVar4 = this.f21310f;
                if (bVar4 != null && bVar4.d().equals(resolveInfo.activityInfo.packageName)) {
                    bVar3.a(this.f21310f.f());
                    bVar3.a(this.f21310f.h());
                    bVar3.a(this.f21310f.a());
                    this.f21305a.add(0, bVar3);
                    z2 = true;
                } else if (TbsConfig.APP_QB.equals(resolveInfo.activityInfo.packageName)) {
                    b bVar5 = this.f21311g;
                    if (bVar5 != null) {
                        bVar3.a(bVar5.f());
                        bVar3.a(this.f21311g.h());
                        bVar3.a(this.f21311g.a());
                    }
                    this.f21305a.add(0, bVar3);
                } else {
                    this.f21305a.add(bVar3);
                }
                if (!z3 && bVar != null && bVar3.d().equals(bVar.d())) {
                    b(context, bVar3);
                    z3 = true;
                }
            }
        }
        if (!z2 && (bVar2 = this.f21310f) != null) {
            this.f21305a.add(0, bVar2);
        }
        if (z3 || this.f21305a.size() <= 0) {
            return;
        }
        b(context, this.f21305a.get(0));
    }

    public void a(ListView listView) {
        this.f21308d = new WeakReference<>(listView);
    }

    public void a(d dVar) {
        this.f21309e = new WeakReference<>(dVar);
    }

    public void b() {
        View viewFindViewWithTag;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.f21313i.obtainMessage(1).sendToTarget();
            return;
        }
        ListView listView = this.f21308d.get();
        if (listView == null || (viewFindViewWithTag = listView.findViewWithTag(this.f21310f)) == null) {
            return;
        }
        a(this.f21310f, viewFindViewWithTag);
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public int getCount() {
        return this.f21305a.size();
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i2, View view, ViewGroup viewGroup) {
        b item = getItem(i2);
        if (item == null) {
            return null;
        }
        if (view == null) {
            view = a(getContext());
        }
        a(item, view);
        return view;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        b bVar;
        Object tag = view.getTag();
        if (tag == null || !(tag instanceof b) || (bVar = (b) tag) == this.f21306b) {
            return;
        }
        Object parent = view.getParent();
        View view2 = parent instanceof View ? (View) parent : null;
        b bVar2 = this.f21306b;
        b(view.getContext(), bVar);
        a(bVar2, view2.findViewWithTag(bVar2));
        a(this.f21306b, view);
    }
}

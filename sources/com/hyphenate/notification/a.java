package com.hyphenate.notification;

import android.R;
import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import java.lang.reflect.Field;
import java.util.Stack;

/* loaded from: classes4.dex */
final class a {

    /* renamed from: a, reason: collision with root package name */
    private static final boolean f8763a = true;

    /* renamed from: b, reason: collision with root package name */
    private static final int f8764b = 0;

    /* renamed from: o, reason: collision with root package name */
    private Context f8777o;

    /* renamed from: c, reason: collision with root package name */
    private final int f8765c = -1;

    /* renamed from: d, reason: collision with root package name */
    private final int f8766d = -6710887;

    /* renamed from: e, reason: collision with root package name */
    private final int f8767e = -570425344;

    /* renamed from: f, reason: collision with root package name */
    private final int f8768f = -1979711488;

    /* renamed from: g, reason: collision with root package name */
    private final int f8769g = -1;

    /* renamed from: h, reason: collision with root package name */
    private final int f8770h = -6710887;

    /* renamed from: i, reason: collision with root package name */
    private final int f8771i = -16777216;

    /* renamed from: j, reason: collision with root package name */
    private final int f8772j = -10066330;

    /* renamed from: k, reason: collision with root package name */
    private final String f8773k = "fakeContentTitle";

    /* renamed from: l, reason: collision with root package name */
    private final String f8774l = "fakeContentText";

    /* renamed from: m, reason: collision with root package name */
    private int f8775m = 0;

    /* renamed from: n, reason: collision with root package name */
    private int f8776n = 0;

    /* renamed from: p, reason: collision with root package name */
    private String f8778p = "";

    public a(Context context) {
        this.f8777o = context;
        b("start ->" + toString());
    }

    public static int a(String str) throws NoSuchFieldException, SecurityException {
        int identifier = Resources.getSystem().getIdentifier(str, "id", "android");
        if (identifier > 0) {
            return identifier;
        }
        try {
            Field field = Class.forName("com.android.internal.R$id").getField(str);
            field.setAccessible(true);
            return field.getInt(null);
        } catch (Exception unused) {
            return 0;
        }
    }

    public static a a(Context context) {
        return new a(context).c();
    }

    private static boolean a(int i2) {
        return c(b(i2));
    }

    private boolean a(RemoteViews remoteViews) {
        b("fetchNotificationTextColorByText");
        this.f8778p = "ByText";
        if (remoteViews != null) {
            try {
                View viewApply = remoteViews.apply(this.f8777o, new FrameLayout(this.f8777o));
                Stack stack = new Stack();
                stack.push(viewApply);
                TextView textView = null;
                TextView textView2 = null;
                while (!stack.isEmpty()) {
                    View view = (View) stack.pop();
                    if (view instanceof TextView) {
                        TextView textView3 = (TextView) view;
                        CharSequence text = textView3.getText();
                        if (TextUtils.equals("fakeContentTitle", text)) {
                            b("fetchNotificationTextColorByText -> contentTitleTextView -> OK");
                            textView = textView3;
                        } else if (TextUtils.equals("fakeContentText", text)) {
                            b("fetchNotificationTextColorByText -> contentTextTextView -> OK");
                            textView2 = textView3;
                        }
                        if (textView != null && textView2 != null) {
                            break;
                        }
                    }
                    if (view instanceof ViewGroup) {
                        ViewGroup viewGroup = (ViewGroup) view;
                        int childCount = viewGroup.getChildCount();
                        for (int i2 = 0; i2 < childCount; i2++) {
                            stack.push(viewGroup.getChildAt(i2));
                        }
                    }
                }
                stack.clear();
                return a(textView, textView2);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return false;
    }

    private boolean a(TextView textView, TextView textView2) {
        if (textView != null) {
            this.f8775m = textView.getTextColors().getDefaultColor();
        }
        if (textView2 != null) {
            this.f8776n = textView2.getTextColors().getDefaultColor();
        }
        b("checkAndGuessColor-> beforeGuess->" + toString());
        int i2 = this.f8775m;
        if (i2 != 0 && this.f8776n != 0) {
            return true;
        }
        if (i2 != 0) {
            this.f8776n = a(i2) ? -6710887 : -10066330;
            return true;
        }
        int i3 = this.f8776n;
        if (i3 == 0) {
            return false;
        }
        this.f8775m = a(i3) ? -1 : -16777216;
        return true;
    }

    private static int b(int i2) {
        return (int) ((((Color.red(i2) + Color.green(i2)) + Color.blue(i2)) / 3.0f) + 0.5f);
    }

    private RemoteViews b(Context context) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("fakeContentTitle").setContentText("fakeContentText").setTicker("fackTicker").setSmallIcon(R.drawable.stat_sys_warning);
        return builder.getNotification().contentView;
    }

    private void b(String str) {
        Log.d("NotifTxtColorCompat", str);
    }

    private boolean b(RemoteViews remoteViews) {
        b("fetchNotificationTextColorById");
        this.f8778p = "ById";
        try {
            int iA = a("text");
            b("systemNotificationContentId -> #" + Integer.toHexString(iA));
            if (remoteViews == null || remoteViews.getLayoutId() <= 0) {
                return false;
            }
            View viewInflate = LayoutInflater.from(this.f8777o).inflate(remoteViews.getLayoutId(), (ViewGroup) null);
            View viewFindViewById = viewInflate.findViewById(R.id.title);
            return a(viewFindViewById instanceof TextView ? (TextView) viewFindViewById : null, iA > 0 ? (TextView) viewInflate.findViewById(iA) : null);
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    private static boolean c(int i2) {
        return i2 >= 128;
    }

    private boolean c(RemoteViews remoteViews) {
        this.f8778p = "ByAnyTextView";
        if (remoteViews != null) {
            try {
                if (remoteViews.getLayoutId() > 0) {
                    TextView textView = null;
                    View viewInflate = LayoutInflater.from(this.f8777o).inflate(remoteViews.getLayoutId(), (ViewGroup) null);
                    Stack stack = new Stack();
                    stack.push(viewInflate);
                    while (true) {
                        if (stack.isEmpty()) {
                            break;
                        }
                        View view = (View) stack.pop();
                        if (view instanceof TextView) {
                            textView = (TextView) view;
                            break;
                        }
                        if (view instanceof ViewGroup) {
                            ViewGroup viewGroup = (ViewGroup) view;
                            int childCount = viewGroup.getChildCount();
                            for (int i2 = 0; i2 < childCount; i2++) {
                                stack.push(viewGroup.getChildAt(i2));
                            }
                        }
                    }
                    stack.clear();
                    if (textView != null) {
                        if (a(textView.getTextColors().getDefaultColor())) {
                            this.f8775m = -1;
                            this.f8776n = -6710887;
                            return true;
                        }
                        this.f8775m = -570425344;
                        this.f8776n = -1979711488;
                        return true;
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return false;
    }

    private void h() {
        this.f8778p = "BySdkVersion";
        this.f8775m = -570425344;
        this.f8776n = -1979711488;
    }

    public int a() {
        return this.f8775m;
    }

    public a a(RemoteViews remoteViews, int i2) {
        remoteViews.setTextColor(i2, this.f8775m);
        return this;
    }

    public a a(RemoteViews remoteViews, int... iArr) {
        for (int i2 : iArr) {
            remoteViews.setTextColor(i2, this.f8775m);
        }
        return this;
    }

    public int b() {
        return this.f8776n;
    }

    public a b(RemoteViews remoteViews, int i2) {
        remoteViews.setTextColor(i2, this.f8776n);
        return this;
    }

    public a b(RemoteViews remoteViews, int... iArr) {
        for (int i2 : iArr) {
            remoteViews.setTextColor(i2, this.f8776n);
        }
        return this;
    }

    public a c() {
        RemoteViews remoteViewsB = b(this.f8777o);
        if (!a(remoteViewsB) && !b(remoteViewsB) && !c(remoteViewsB)) {
            h();
        }
        b("end ->" + toString());
        return this;
    }

    public a d() {
        a(b(this.f8777o));
        b("end ->" + toString());
        return this;
    }

    public a e() {
        b(b(this.f8777o));
        b("end ->" + toString());
        return this;
    }

    public a f() {
        c(b(this.f8777o));
        b("end ->" + toString());
        return this;
    }

    public a g() {
        h();
        b("end ->" + toString());
        return this;
    }

    public String toString() {
        return "NotificationTextColorCompat." + this.f8778p + "\ncontentTitleColor=#" + Integer.toHexString(this.f8775m) + "\ncontentTextColor=#" + Integer.toHexString(this.f8776n) + "";
    }
}

package com.tencent.liteav.basic.a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.aliyun.vod.common.utils.UriUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: f, reason: collision with root package name */
    private static final Object f18328f = new Object();

    /* renamed from: g, reason: collision with root package name */
    private static b f18329g;

    /* renamed from: a, reason: collision with root package name */
    private final Context f18330a;

    /* renamed from: b, reason: collision with root package name */
    private final HashMap<BroadcastReceiver, ArrayList<IntentFilter>> f18331b = new HashMap<>();

    /* renamed from: c, reason: collision with root package name */
    private final HashMap<String, ArrayList<C0328b>> f18332c = new HashMap<>();

    /* renamed from: d, reason: collision with root package name */
    private final ArrayList<a> f18333d = new ArrayList<>();

    /* renamed from: e, reason: collision with root package name */
    private final Handler f18334e;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        final Intent f18336a;

        /* renamed from: b, reason: collision with root package name */
        final ArrayList<C0328b> f18337b;

        public a(Intent intent, ArrayList<C0328b> arrayList) {
            this.f18336a = intent;
            this.f18337b = arrayList;
        }
    }

    /* renamed from: com.tencent.liteav.basic.a.b$b, reason: collision with other inner class name */
    public static class C0328b {

        /* renamed from: a, reason: collision with root package name */
        final IntentFilter f18338a;

        /* renamed from: b, reason: collision with root package name */
        final BroadcastReceiver f18339b;

        /* renamed from: c, reason: collision with root package name */
        boolean f18340c;

        public C0328b(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
            this.f18338a = intentFilter;
            this.f18339b = broadcastReceiver;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Receiver{");
            sb.append(this.f18339b);
            sb.append(" filter=");
            sb.append(this.f18338a);
            sb.append("}");
            return sb.toString();
        }
    }

    private b(Context context) {
        this.f18330a = context;
        this.f18334e = new Handler(context.getMainLooper()) { // from class: com.tencent.liteav.basic.a.b.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    super.handleMessage(message);
                } else {
                    b.this.a();
                }
            }
        };
    }

    public static b a(Context context) {
        b bVar;
        synchronized (f18328f) {
            if (f18329g == null) {
                f18329g = new b(context.getApplicationContext());
            }
            bVar = f18329g;
        }
        return bVar;
    }

    public void a(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        synchronized (this.f18331b) {
            C0328b c0328b = new C0328b(intentFilter, broadcastReceiver);
            ArrayList<IntentFilter> arrayList = this.f18331b.get(broadcastReceiver);
            if (arrayList == null) {
                arrayList = new ArrayList<>(1);
                this.f18331b.put(broadcastReceiver, arrayList);
            }
            arrayList.add(intentFilter);
            for (int i2 = 0; i2 < intentFilter.countActions(); i2++) {
                String action = intentFilter.getAction(i2);
                ArrayList<C0328b> arrayList2 = this.f18332c.get(action);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList<>(1);
                    this.f18332c.put(action, arrayList2);
                }
                arrayList2.add(c0328b);
            }
        }
    }

    public void a(BroadcastReceiver broadcastReceiver) {
        synchronized (this.f18331b) {
            ArrayList<IntentFilter> arrayListRemove = this.f18331b.remove(broadcastReceiver);
            if (arrayListRemove == null) {
                return;
            }
            for (int i2 = 0; i2 < arrayListRemove.size(); i2++) {
                IntentFilter intentFilter = arrayListRemove.get(i2);
                for (int i3 = 0; i3 < intentFilter.countActions(); i3++) {
                    String action = intentFilter.getAction(i3);
                    ArrayList<C0328b> arrayList = this.f18332c.get(action);
                    if (arrayList != null) {
                        int i4 = 0;
                        while (i4 < arrayList.size()) {
                            if (arrayList.get(i4).f18339b == broadcastReceiver) {
                                arrayList.remove(i4);
                                i4--;
                            }
                            i4++;
                        }
                        if (arrayList.size() <= 0) {
                            this.f18332c.remove(action);
                        }
                    }
                }
            }
        }
    }

    public boolean a(Intent intent) {
        int i2;
        String str;
        ArrayList arrayList;
        ArrayList<C0328b> arrayList2;
        String str2;
        synchronized (this.f18331b) {
            String action = intent.getAction();
            String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(this.f18330a.getContentResolver());
            Uri data = intent.getData();
            String scheme = intent.getScheme();
            Set<String> categories = intent.getCategories();
            boolean z2 = (intent.getFlags() & 8) != 0;
            if (z2) {
                Log.v("LocalBroadcastManager", "Resolving type " + strResolveTypeIfNeeded + " scheme " + scheme + " of intent " + intent);
            }
            ArrayList<C0328b> arrayList3 = this.f18332c.get(intent.getAction());
            if (arrayList3 != null) {
                if (z2) {
                    Log.v("LocalBroadcastManager", "Action list: " + arrayList3);
                }
                ArrayList arrayList4 = null;
                int i3 = 0;
                while (i3 < arrayList3.size()) {
                    C0328b c0328b = arrayList3.get(i3);
                    if (z2) {
                        Log.v("LocalBroadcastManager", "Matching against filter " + c0328b.f18338a);
                    }
                    if (c0328b.f18340c) {
                        if (z2) {
                            Log.v("LocalBroadcastManager", "  Filter's target already added");
                        }
                        i2 = i3;
                        arrayList2 = arrayList3;
                        str = action;
                        str2 = strResolveTypeIfNeeded;
                        arrayList = arrayList4;
                    } else {
                        i2 = i3;
                        str = action;
                        arrayList = arrayList4;
                        arrayList2 = arrayList3;
                        str2 = strResolveTypeIfNeeded;
                        int iMatch = c0328b.f18338a.match(action, strResolveTypeIfNeeded, scheme, data, categories, "LocalBroadcastManager");
                        if (iMatch >= 0) {
                            if (z2) {
                                Log.v("LocalBroadcastManager", "  Filter matched!  match=0x" + Integer.toHexString(iMatch));
                            }
                            arrayList4 = arrayList == null ? new ArrayList() : arrayList;
                            arrayList4.add(c0328b);
                            c0328b.f18340c = true;
                            i3 = i2 + 1;
                            action = str;
                            arrayList3 = arrayList2;
                            strResolveTypeIfNeeded = str2;
                        } else if (z2) {
                            Log.v("LocalBroadcastManager", "  Filter did not match: " + (iMatch != -4 ? iMatch != -3 ? iMatch != -2 ? iMatch != -1 ? "unknown reason" : "type" : "data" : "action" : UriUtil.QUERY_CATEGORY));
                        }
                    }
                    arrayList4 = arrayList;
                    i3 = i2 + 1;
                    action = str;
                    arrayList3 = arrayList2;
                    strResolveTypeIfNeeded = str2;
                }
                ArrayList arrayList5 = arrayList4;
                if (arrayList5 != null) {
                    for (int i4 = 0; i4 < arrayList5.size(); i4++) {
                        ((C0328b) arrayList5.get(i4)).f18340c = false;
                    }
                    this.f18333d.add(new a(intent, arrayList5));
                    if (!this.f18334e.hasMessages(1)) {
                        this.f18334e.sendEmptyMessage(1);
                    }
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        int size;
        a[] aVarArr;
        while (true) {
            synchronized (this.f18331b) {
                size = this.f18333d.size();
                if (size <= 0) {
                    return;
                }
                aVarArr = new a[size];
                this.f18333d.toArray(aVarArr);
                this.f18333d.clear();
            }
            for (int i2 = 0; i2 < size; i2++) {
                a aVar = aVarArr[i2];
                for (int i3 = 0; i3 < aVar.f18337b.size(); i3++) {
                    aVar.f18337b.get(i3).f18339b.onReceive(this.f18330a, aVar.f18336a);
                }
            }
        }
    }
}

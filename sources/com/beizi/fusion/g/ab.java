package com.beizi.fusion.g;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;
import com.aliyun.vod.common.utils.UriUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/* loaded from: classes2.dex */
public final class ab {

    /* renamed from: f, reason: collision with root package name */
    private static final Object f5061f = new Object();

    /* renamed from: g, reason: collision with root package name */
    private static ab f5062g;

    /* renamed from: a, reason: collision with root package name */
    private final Context f5063a;

    /* renamed from: b, reason: collision with root package name */
    private final HashMap<BroadcastReceiver, ArrayList<b>> f5064b = new HashMap<>();

    /* renamed from: c, reason: collision with root package name */
    private final HashMap<String, ArrayList<b>> f5065c = new HashMap<>();

    /* renamed from: d, reason: collision with root package name */
    private final ArrayList<a> f5066d = new ArrayList<>();

    /* renamed from: e, reason: collision with root package name */
    private final Handler f5067e;

    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        final Intent f5069a;

        /* renamed from: b, reason: collision with root package name */
        final ArrayList<b> f5070b;

        public a(Intent intent, ArrayList<b> arrayList) {
            this.f5069a = intent;
            this.f5070b = arrayList;
        }
    }

    public static final class b {

        /* renamed from: a, reason: collision with root package name */
        final IntentFilter f5071a;

        /* renamed from: b, reason: collision with root package name */
        final BroadcastReceiver f5072b;

        /* renamed from: c, reason: collision with root package name */
        boolean f5073c;

        /* renamed from: d, reason: collision with root package name */
        boolean f5074d;

        public b(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
            this.f5071a = intentFilter;
            this.f5072b = broadcastReceiver;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Receiver{");
            sb.append(this.f5072b);
            sb.append(" filter=");
            sb.append(this.f5071a);
            if (this.f5074d) {
                sb.append(" DEAD");
            }
            sb.append("}");
            return sb.toString();
        }
    }

    private ab(Context context) {
        this.f5063a = context;
        this.f5067e = new Handler(context.getMainLooper()) { // from class: com.beizi.fusion.g.ab.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    super.handleMessage(message);
                } else {
                    ab.this.a();
                }
            }
        };
    }

    public void registerReceiver(@NonNull BroadcastReceiver broadcastReceiver, @NonNull IntentFilter intentFilter) {
        synchronized (this.f5064b) {
            b bVar = new b(intentFilter, broadcastReceiver);
            ArrayList<b> arrayList = this.f5064b.get(broadcastReceiver);
            if (arrayList == null) {
                arrayList = new ArrayList<>(1);
                this.f5064b.put(broadcastReceiver, arrayList);
            }
            arrayList.add(bVar);
            for (int i2 = 0; i2 < intentFilter.countActions(); i2++) {
                String action = intentFilter.getAction(i2);
                ArrayList<b> arrayList2 = this.f5065c.get(action);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList<>(1);
                    this.f5065c.put(action, arrayList2);
                }
                arrayList2.add(bVar);
            }
        }
    }

    public void unregisterReceiver(@NonNull BroadcastReceiver broadcastReceiver) {
        synchronized (this.f5064b) {
            ArrayList<b> arrayListRemove = this.f5064b.remove(broadcastReceiver);
            if (arrayListRemove == null) {
                return;
            }
            for (int size = arrayListRemove.size() - 1; size >= 0; size--) {
                b bVar = arrayListRemove.get(size);
                bVar.f5074d = true;
                for (int i2 = 0; i2 < bVar.f5071a.countActions(); i2++) {
                    String action = bVar.f5071a.getAction(i2);
                    ArrayList<b> arrayList = this.f5065c.get(action);
                    if (arrayList != null) {
                        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                            b bVar2 = arrayList.get(size2);
                            if (bVar2.f5072b == broadcastReceiver) {
                                bVar2.f5074d = true;
                                arrayList.remove(size2);
                            }
                        }
                        if (arrayList.size() <= 0) {
                            this.f5065c.remove(action);
                        }
                    }
                }
            }
        }
    }

    @NonNull
    public static ab a(@NonNull Context context) {
        ab abVar;
        synchronized (f5061f) {
            if (f5062g == null) {
                f5062g = new ab(context.getApplicationContext());
            }
            abVar = f5062g;
        }
        return abVar;
    }

    public boolean a(@NonNull Intent intent) {
        int i2;
        String str;
        ArrayList arrayList;
        ArrayList<b> arrayList2;
        String str2;
        synchronized (this.f5064b) {
            String action = intent.getAction();
            String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(this.f5063a.getContentResolver());
            Uri data = intent.getData();
            String scheme = intent.getScheme();
            Set<String> categories = intent.getCategories();
            boolean z2 = (intent.getFlags() & 8) != 0;
            if (z2) {
                Log.v("LocalBroadcastManager", "Resolving type " + strResolveTypeIfNeeded + " scheme " + scheme + " of intent " + intent);
            }
            ArrayList<b> arrayList3 = this.f5065c.get(intent.getAction());
            if (arrayList3 != null) {
                if (z2) {
                    Log.v("LocalBroadcastManager", "Action list: " + arrayList3);
                }
                ArrayList arrayList4 = null;
                int i3 = 0;
                while (i3 < arrayList3.size()) {
                    b bVar = arrayList3.get(i3);
                    if (z2) {
                        Log.v("LocalBroadcastManager", "Matching against filter " + bVar.f5071a);
                    }
                    if (bVar.f5073c) {
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
                        int iMatch = bVar.f5071a.match(action, strResolveTypeIfNeeded, scheme, data, categories, "LocalBroadcastManager");
                        if (iMatch >= 0) {
                            if (z2) {
                                Log.v("LocalBroadcastManager", "  Filter matched!  match=0x" + Integer.toHexString(iMatch));
                            }
                            arrayList4 = arrayList == null ? new ArrayList() : arrayList;
                            arrayList4.add(bVar);
                            bVar.f5073c = true;
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
                        ((b) arrayList5.get(i4)).f5073c = false;
                    }
                    this.f5066d.add(new a(intent, arrayList5));
                    if (!this.f5067e.hasMessages(1)) {
                        this.f5067e.sendEmptyMessage(1);
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
            synchronized (this.f5064b) {
                size = this.f5066d.size();
                if (size <= 0) {
                    return;
                }
                aVarArr = new a[size];
                this.f5066d.toArray(aVarArr);
                this.f5066d.clear();
            }
            for (int i2 = 0; i2 < size; i2++) {
                a aVar = aVarArr[i2];
                int size2 = aVar.f5070b.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    b bVar = aVar.f5070b.get(i3);
                    if (!bVar.f5074d) {
                        bVar.f5072b.onReceive(this.f5063a, aVar.f5069a);
                    }
                }
            }
        }
    }
}

package com.unity3d.player;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

/* loaded from: classes6.dex */
final class m {

    /* renamed from: a, reason: collision with root package name */
    private Context f24164a;

    /* renamed from: b, reason: collision with root package name */
    private b f24165b;

    public interface a {
        void b();
    }

    public class b extends ContentObserver {

        /* renamed from: b, reason: collision with root package name */
        private a f24167b;

        public b(Handler handler, a aVar) {
            super(handler);
            this.f24167b = aVar;
        }

        @Override // android.database.ContentObserver
        public final boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z2) {
            a aVar = this.f24167b;
            if (aVar != null) {
                aVar.b();
            }
        }
    }

    public m(Context context) {
        this.f24164a = context;
    }

    public final void a() {
        if (this.f24165b != null) {
            this.f24164a.getContentResolver().unregisterContentObserver(this.f24165b);
            this.f24165b = null;
        }
    }

    public final void a(a aVar, String str) {
        this.f24165b = new b(new Handler(Looper.getMainLooper()), aVar);
        this.f24164a.getContentResolver().registerContentObserver(Settings.System.getUriFor(str), true, this.f24165b);
    }
}

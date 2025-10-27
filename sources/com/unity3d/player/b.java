package com.unity3d.player;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;

/* loaded from: classes6.dex */
final class b {

    /* renamed from: a, reason: collision with root package name */
    private final Context f24050a;

    /* renamed from: b, reason: collision with root package name */
    private final AudioManager f24051b;

    /* renamed from: c, reason: collision with root package name */
    private a f24052c;

    public class a extends ContentObserver {

        /* renamed from: b, reason: collision with root package name */
        private final InterfaceC0401b f24054b;

        /* renamed from: c, reason: collision with root package name */
        private final AudioManager f24055c;

        /* renamed from: d, reason: collision with root package name */
        private final int f24056d;

        /* renamed from: e, reason: collision with root package name */
        private int f24057e;

        public a(Handler handler, AudioManager audioManager, int i2, InterfaceC0401b interfaceC0401b) {
            super(handler);
            this.f24055c = audioManager;
            this.f24056d = 3;
            this.f24054b = interfaceC0401b;
            this.f24057e = audioManager.getStreamVolume(3);
        }

        @Override // android.database.ContentObserver
        public final boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z2, Uri uri) {
            int streamVolume;
            AudioManager audioManager = this.f24055c;
            if (audioManager == null || this.f24054b == null || (streamVolume = audioManager.getStreamVolume(this.f24056d)) == this.f24057e) {
                return;
            }
            this.f24057e = streamVolume;
            this.f24054b.onAudioVolumeChanged(streamVolume);
        }
    }

    /* renamed from: com.unity3d.player.b$b, reason: collision with other inner class name */
    public interface InterfaceC0401b {
        void onAudioVolumeChanged(int i2);
    }

    public b(Context context) {
        this.f24050a = context;
        this.f24051b = (AudioManager) context.getSystemService("audio");
    }

    public final void a() {
        if (this.f24052c != null) {
            this.f24050a.getContentResolver().unregisterContentObserver(this.f24052c);
            this.f24052c = null;
        }
    }

    public final void a(InterfaceC0401b interfaceC0401b) {
        this.f24052c = new a(new Handler(), this.f24051b, 3, interfaceC0401b);
        this.f24050a.getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, this.f24052c);
    }
}

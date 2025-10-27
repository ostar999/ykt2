package com.unity3d.player;

import android.content.Context;
import com.unity3d.player.b;

/* loaded from: classes6.dex */
public class AudioVolumeHandler implements b.InterfaceC0401b {

    /* renamed from: a, reason: collision with root package name */
    private b f23881a;

    public AudioVolumeHandler(Context context) {
        b bVar = new b(context);
        this.f23881a = bVar;
        bVar.a(this);
    }

    public final void a() {
        this.f23881a.a();
        this.f23881a = null;
    }

    @Override // com.unity3d.player.b.InterfaceC0401b
    public final native void onAudioVolumeChanged(int i2);
}

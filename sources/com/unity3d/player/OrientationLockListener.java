package com.unity3d.player;

import android.content.Context;
import android.provider.Settings;
import com.unity3d.player.m;

/* loaded from: classes6.dex */
public class OrientationLockListener implements m.a {

    /* renamed from: a, reason: collision with root package name */
    private m f23903a;

    /* renamed from: b, reason: collision with root package name */
    private Context f23904b;

    public OrientationLockListener(Context context) {
        this.f23904b = context;
        this.f23903a = new m(context);
        nativeUpdateOrientationLockState(Settings.System.getInt(this.f23904b.getContentResolver(), "accelerometer_rotation", 0));
        this.f23903a.a(this, "accelerometer_rotation");
    }

    public final void a() {
        this.f23903a.a();
        this.f23903a = null;
    }

    @Override // com.unity3d.player.m.a
    public final void b() {
        nativeUpdateOrientationLockState(Settings.System.getInt(this.f23904b.getContentResolver(), "accelerometer_rotation", 0));
    }

    public final native void nativeUpdateOrientationLockState(int i2);
}

package com.unity3d.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;

/* loaded from: classes6.dex */
public class HFPStatus {

    /* renamed from: a, reason: collision with root package name */
    private Context f23885a;

    /* renamed from: e, reason: collision with root package name */
    private AudioManager f23889e;

    /* renamed from: b, reason: collision with root package name */
    private BroadcastReceiver f23886b = null;

    /* renamed from: c, reason: collision with root package name */
    private Intent f23887c = null;

    /* renamed from: d, reason: collision with root package name */
    private boolean f23888d = false;

    /* renamed from: f, reason: collision with root package name */
    private boolean f23890f = false;

    /* renamed from: g, reason: collision with root package name */
    private int f23891g = a.f23893a;

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        public static final int f23893a = 1;

        /* renamed from: b, reason: collision with root package name */
        public static final int f23894b = 2;

        /* renamed from: c, reason: collision with root package name */
        private static final /* synthetic */ int[] f23895c = {1, 2};
    }

    public HFPStatus(Context context) {
        this.f23889e = null;
        this.f23885a = context;
        this.f23889e = (AudioManager) context.getSystemService("audio");
        initHFPStatusJni();
    }

    private void b() {
        BroadcastReceiver broadcastReceiver = this.f23886b;
        if (broadcastReceiver != null) {
            this.f23885a.unregisterReceiver(broadcastReceiver);
            this.f23886b = null;
            this.f23887c = null;
        }
        this.f23891g = a.f23893a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        if (this.f23890f) {
            this.f23890f = false;
            this.f23889e.stopBluetoothSco();
        }
    }

    private final native void deinitHFPStatusJni();

    private final native void initHFPStatusJni();

    public final void a() {
        clearHFPStat();
        deinitHFPStatusJni();
    }

    public void clearHFPStat() {
        b();
        c();
    }

    public boolean getHFPStat() {
        return this.f23891g == a.f23894b;
    }

    public void requestHFPStat() {
        clearHFPStat();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.unity3d.player.HFPStatus.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.getIntExtra("android.media.extra.SCO_AUDIO_STATE", -1) != 1) {
                    return;
                }
                HFPStatus.this.f23891g = a.f23894b;
                HFPStatus.this.c();
                if (HFPStatus.this.f23888d) {
                    HFPStatus.this.f23889e.setMode(3);
                }
            }
        };
        this.f23886b = broadcastReceiver;
        this.f23887c = this.f23885a.registerReceiver(broadcastReceiver, new IntentFilter("android.media.ACTION_SCO_AUDIO_STATE_UPDATED"));
        try {
            this.f23890f = true;
            this.f23889e.startBluetoothSco();
        } catch (NullPointerException unused) {
            f.Log(5, "startBluetoothSco() failed. no bluetooth device connected.");
        }
    }

    public void setHFPRecordingStat(boolean z2) {
        this.f23888d = z2;
        if (z2) {
            return;
        }
        this.f23889e.setMode(0);
    }
}

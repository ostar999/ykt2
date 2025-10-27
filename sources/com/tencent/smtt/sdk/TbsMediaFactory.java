package com.tencent.smtt.sdk;

import android.content.Context;
import android.util.Log;
import com.tencent.smtt.export.external.DexLoader;

/* loaded from: classes6.dex */
public class TbsMediaFactory {

    /* renamed from: a, reason: collision with root package name */
    private Context f20982a;

    /* renamed from: b, reason: collision with root package name */
    private u f20983b = null;

    /* renamed from: c, reason: collision with root package name */
    private DexLoader f20984c = null;

    public TbsMediaFactory(Context context) {
        this.f20982a = null;
        this.f20982a = context.getApplicationContext();
        a();
    }

    private void a() {
        if (this.f20982a == null) {
            Log.e("TbsVideo", "TbsVideo needs context !!");
            return;
        }
        if (this.f20983b == null) {
            g.a(true).a(this.f20982a, false, false);
            u uVarA = g.a(true).a();
            this.f20983b = uVarA;
            if (uVarA != null) {
                this.f20984c = uVarA.c();
            }
        }
        if (this.f20983b == null || this.f20984c == null) {
            throw new RuntimeException("tbs core dex(s) load failure !!!");
        }
    }

    public TbsMediaPlayer createPlayer() {
        DexLoader dexLoader;
        if (this.f20983b == null || (dexLoader = this.f20984c) == null) {
            throw new RuntimeException("tbs core dex(s) did not loaded !!!");
        }
        return new TbsMediaPlayer(new p(dexLoader, this.f20982a));
    }
}

package com.tencent.smtt.sdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/* loaded from: classes6.dex */
public class VideoActivity extends Activity {
    @Override // android.app.Activity
    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        r.a(this).a(i2, i3, intent);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        super.requestWindowFeature(1);
        super.getWindow().setFormat(-3);
        Intent intent = super.getIntent();
        Bundle bundleExtra = intent != null ? intent.getBundleExtra("extraData") : null;
        if (bundleExtra != null) {
            bundleExtra.putInt("callMode", 1);
            r.a(super.getApplicationContext()).a((String) null, bundleExtra, (com.tencent.tbs.video.interfaces.a) null);
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        r.a(this).a(this, 4);
    }

    @Override // android.app.Activity
    public void onPause() {
        super.onPause();
        r.a(this).a(this, 3);
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        r.a(this).a(this, 2);
    }

    @Override // android.app.Activity
    public void onStop() {
        super.onStop();
        r.a(this).a(this, 1);
    }
}

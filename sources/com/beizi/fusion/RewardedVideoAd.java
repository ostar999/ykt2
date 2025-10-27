package com.beizi.fusion;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import com.beizi.fusion.d.t;

/* loaded from: classes2.dex */
public class RewardedVideoAd {

    /* renamed from: a, reason: collision with root package name */
    private t f4722a;

    /* renamed from: b, reason: collision with root package name */
    private String f4723b;

    /* renamed from: c, reason: collision with root package name */
    private String f4724c;

    public RewardedVideoAd(Context context, String str, RewardedVideoAdListener rewardedVideoAdListener, long j2, int i2) {
        this.f4722a = new t(context, str, rewardedVideoAdListener, j2, i2);
    }

    public void destroy() {
        t tVar = this.f4722a;
        if (tVar != null) {
            tVar.E();
        }
    }

    public String getExtra() {
        return this.f4724c;
    }

    public String getUserId() {
        return this.f4723b;
    }

    public boolean isLoaded() {
        t tVar = this.f4722a;
        if (tVar != null) {
            return tVar.C();
        }
        return false;
    }

    public void loadAd() throws InterruptedException {
        t tVar = this.f4722a;
        if (tVar != null) {
            tVar.D();
        }
    }

    public void setExtra(String str) {
        t tVar = this.f4722a;
        if (tVar != null) {
            tVar.f(str);
        }
    }

    public void setRewardedVideoAdListener(RewardedVideoAdListener rewardedVideoAdListener) {
        t tVar = this.f4722a;
        if (tVar != null) {
            tVar.a(rewardedVideoAdListener);
        }
    }

    public void setUserId(String str) {
        t tVar = this.f4722a;
        if (tVar != null) {
            tVar.e(str);
        }
    }

    public void showAd(@NonNull Activity activity) {
        t tVar = this.f4722a;
        if (tVar != null) {
            tVar.a(activity);
        }
    }
}

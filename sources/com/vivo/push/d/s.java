package com.vivo.push.d;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.vivo.push.model.InsideNotificationItem;
import java.util.HashMap;

/* loaded from: classes6.dex */
final class s implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ InsideNotificationItem f24336a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ com.vivo.push.b.q f24337b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ r f24338c;

    public s(r rVar, InsideNotificationItem insideNotificationItem, com.vivo.push.b.q qVar) {
        this.f24338c = rVar;
        this.f24336a = insideNotificationItem;
        this.f24337b = qVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws PackageManager.NameNotFoundException {
        char c3;
        r rVar = this.f24338c;
        if (((z) rVar).f24347b.onNotificationMessageArrived(((com.vivo.push.l) rVar).f24388a, com.vivo.push.util.q.a(this.f24336a))) {
            com.vivo.push.util.p.b("OnNotificationArrivedTask", "pkg name : " + ((com.vivo.push.l) this.f24338c).f24388a.getPackageName() + " 应用主动拦截通知");
            com.vivo.push.util.p.b(((com.vivo.push.l) this.f24338c).f24388a, "应用主动拦截通知，导致通知无法展示，如需打开请在onNotificationMessageArrived中返回false");
            HashMap map = new HashMap();
            map.put(com.heytap.mcssdk.constant.b.f7178c, String.valueOf(this.f24337b.f()));
            String strB = com.vivo.push.util.z.b(((com.vivo.push.l) this.f24338c).f24388a, ((com.vivo.push.l) this.f24338c).f24388a.getPackageName());
            if (!TextUtils.isEmpty(strB)) {
                map.put("remoteAppId", strB);
            }
            com.vivo.push.util.e.a(2120L, map);
            return;
        }
        int iB = this.f24338c.b();
        if (iB > 0) {
            com.vivo.push.util.p.b("OnNotificationArrivedTask", "pkg name : " + ((com.vivo.push.l) this.f24338c).f24388a.getPackageName() + " notify channel switch is " + iB);
            com.vivo.push.util.p.b(((com.vivo.push.l) this.f24338c).f24388a, "允许通知开关或者推送通知渠道开关关闭，导致通知无法展示，请到设置页打开应用通知开关 ".concat(String.valueOf(iB)));
            HashMap map2 = new HashMap();
            map2.put(com.heytap.mcssdk.constant.b.f7178c, String.valueOf(this.f24337b.f()));
            String strB2 = com.vivo.push.util.z.b(((com.vivo.push.l) this.f24338c).f24388a, ((com.vivo.push.l) this.f24338c).f24388a.getPackageName());
            if (!TextUtils.isEmpty(strB2)) {
                map2.put("remoteAppId", strB2);
            }
            com.vivo.push.util.e.a(iB, map2);
            return;
        }
        Context context = ((com.vivo.push.l) this.f24338c).f24388a;
        InsideNotificationItem insideNotificationItem = this.f24336a;
        long jF = this.f24337b.f();
        r rVar2 = this.f24338c;
        com.vivo.push.util.k kVar = new com.vivo.push.util.k(context, insideNotificationItem, jF, ((z) rVar2).f24347b.isAllowNet(((com.vivo.push.l) rVar2).f24388a), new t(this));
        boolean zIsShowBigPicOnMobileNet = this.f24336a.isShowBigPicOnMobileNet();
        String purePicUrl = this.f24336a.getPurePicUrl();
        if (TextUtils.isEmpty(purePicUrl)) {
            purePicUrl = this.f24336a.getCoverUrl();
        }
        if (!TextUtils.isEmpty(purePicUrl)) {
            com.vivo.push.util.p.c("OnNotificationArrivedTask", "showCode=".concat(String.valueOf(zIsShowBigPicOnMobileNet)));
            if (zIsShowBigPicOnMobileNet) {
                com.vivo.push.util.p.a(((com.vivo.push.l) this.f24338c).f24388a, "mobile net show");
            } else {
                com.vivo.push.util.p.a(((com.vivo.push.l) this.f24338c).f24388a, "mobile net unshow");
                NetworkInfo networkInfoA = com.vivo.push.util.r.a(((com.vivo.push.l) this.f24338c).f24388a);
                if (networkInfoA != null && networkInfoA.getState() == NetworkInfo.State.CONNECTED) {
                    int type = networkInfoA.getType();
                    c3 = type == 1 ? (char) 2 : type == 0 ? (char) 1 : (char) 3;
                } else {
                    c3 = 0;
                }
                if (c3 == 1) {
                    this.f24336a.clearCoverUrl();
                    this.f24336a.clearPurePicUrl();
                    purePicUrl = null;
                }
            }
        }
        kVar.execute(this.f24336a.getIconUrl(), purePicUrl);
    }
}

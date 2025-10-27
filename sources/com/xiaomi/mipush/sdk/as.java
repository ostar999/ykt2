package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.push.hw;
import com.xiaomi.push.it;
import com.xiaomi.push.iv;
import com.xiaomi.push.iw;
import com.xiaomi.push.ja;
import com.xiaomi.push.jb;
import com.xiaomi.push.je;
import com.xiaomi.push.jg;
import com.xiaomi.push.jh;
import com.xiaomi.push.ji;
import com.xiaomi.push.jk;
import com.xiaomi.push.jm;
import com.xiaomi.push.jo;
import com.xiaomi.push.jp;
import com.xiaomi.push.jq;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class as {
    public static <T extends jq<T, ?>> jb a(Context context, T t2, hw hwVar) {
        return a(context, t2, hwVar, !hwVar.equals(hw.Registration), context.getPackageName(), d.m156a(context).m157a());
    }

    public static <T extends jq<T, ?>> jb a(Context context, T t2, hw hwVar, boolean z2, String str, String str2) {
        String str3;
        byte[] bArrA = jp.a(t2);
        if (bArrA != null) {
            jb jbVar = new jb();
            if (z2) {
                String strD = d.m156a(context).d();
                if (TextUtils.isEmpty(strD)) {
                    str3 = "regSecret is empty, return null";
                } else {
                    try {
                        bArrA = com.xiaomi.push.i.b(com.xiaomi.push.av.a(strD), bArrA);
                    } catch (Exception unused) {
                        com.xiaomi.channel.commonutils.logger.b.d("encryption error. ");
                    }
                }
            }
            it itVar = new it();
            itVar.f672a = 5L;
            itVar.f673a = "fakeid";
            jbVar.a(itVar);
            jbVar.a(ByteBuffer.wrap(bArrA));
            jbVar.a(hwVar);
            jbVar.b(true);
            jbVar.b(str);
            jbVar.a(z2);
            jbVar.a(str2);
            return jbVar;
        }
        str3 = "invoke convertThriftObjectToBytes method, return null.";
        com.xiaomi.channel.commonutils.logger.b.m117a(str3);
        return null;
    }

    public static jq a(Context context, jb jbVar) {
        byte[] bArrM601a;
        if (jbVar.m603b()) {
            try {
                bArrM601a = com.xiaomi.push.i.a(com.xiaomi.push.av.a(d.m156a(context).d()), jbVar.m601a());
            } catch (Exception e2) {
                throw new t("the aes decrypt failed.", e2);
            }
        } else {
            bArrM601a = jbVar.m601a();
        }
        jq jqVarA = a(jbVar.a(), jbVar.f758b);
        if (jqVarA != null) {
            jp.a(jqVarA, bArrM601a);
        }
        return jqVarA;
    }

    private static jq a(hw hwVar, boolean z2) {
        switch (at.f24532a[hwVar.ordinal()]) {
            case 1:
                return new jg();
            case 2:
                return new jm();
            case 3:
                return new jk();
            case 4:
                return new jo();
            case 5:
                return new ji();
            case 6:
                return new iv();
            case 7:
                return new ja();
            case 8:
                return new jh();
            case 9:
                if (z2) {
                    return new je();
                }
                iw iwVar = new iw();
                iwVar.a(true);
                return iwVar;
            case 10:
                return new ja();
            default:
                return null;
        }
    }
}

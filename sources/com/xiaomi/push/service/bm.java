package com.xiaomi.push.service;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.push.ib;
import com.xiaomi.push.jp;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes6.dex */
public class bm {

    /* renamed from: a, reason: collision with root package name */
    public static final Object f25659a = new Object();

    public static void a(Context context, ib ibVar) {
        if (!com.xiaomi.push.v.b() || Constants.HYBRID_PACKAGE_NAME.equals(ibVar.e())) {
            com.xiaomi.channel.commonutils.logger.b.m117a("TinyData TinyDataStorage.cacheTinyData cache data to file begin item:" + ibVar.d() + "  ts:" + System.currentTimeMillis());
            com.xiaomi.push.ai.a(context).a(new bn(context, ibVar));
        }
    }

    public static byte[] a(Context context) {
        String strA = com.xiaomi.push.r.a(context).a("mipush", "td_key", "");
        if (TextUtils.isEmpty(strA)) {
            strA = com.xiaomi.push.ay.a(20);
            com.xiaomi.push.r.a(context).m682a("mipush", "td_key", strA);
        }
        return a(strA);
    }

    private static byte[] a(String str) {
        byte[] bArrCopyOf = Arrays.copyOf(com.xiaomi.push.av.a(str), 16);
        bArrCopyOf[0] = 68;
        bArrCopyOf[15] = 84;
        return bArrCopyOf;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v21 */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r0v7, types: [java.io.Closeable] */
    public static void c(Context context, ib ibVar) throws Throwable {
        BufferedOutputStream bufferedOutputStream;
        BufferedOutputStream bufferedOutputStream2;
        String str;
        ?? A = a(context);
        try {
            try {
                byte[] bArrB = com.xiaomi.push.i.b(A, jp.a(ibVar));
                if (bArrB != null && bArrB.length >= 1) {
                    if (bArrB.length > 10240) {
                        com.xiaomi.channel.commonutils.logger.b.m117a("TinyData write to cache file failed case too much data content item:" + ibVar.d() + "  ts:" + System.currentTimeMillis());
                        com.xiaomi.push.y.a((Closeable) null);
                        com.xiaomi.push.y.a((Closeable) null);
                        return;
                    }
                    BufferedOutputStream bufferedOutputStream3 = new BufferedOutputStream(new FileOutputStream(new File(context.getFilesDir(), "tiny_data.data"), true));
                    try {
                        bufferedOutputStream3.write(com.xiaomi.push.ac.a(bArrB.length));
                        bufferedOutputStream3.write(bArrB);
                        bufferedOutputStream3.flush();
                        com.xiaomi.channel.commonutils.logger.b.m117a("TinyData write to cache file success item:" + ibVar.d() + "  ts:" + System.currentTimeMillis());
                        com.xiaomi.push.y.a((Closeable) null);
                        com.xiaomi.push.y.a(bufferedOutputStream3);
                        return;
                    } catch (IOException e2) {
                        bufferedOutputStream2 = bufferedOutputStream3;
                        e = e2;
                        str = "TinyData write to cache file failed cause io exception item:" + ibVar.d();
                        A = bufferedOutputStream2;
                        com.xiaomi.channel.commonutils.logger.b.a(str, e);
                        com.xiaomi.push.y.a((Closeable) null);
                        com.xiaomi.push.y.a((Closeable) A);
                        return;
                    } catch (Exception e3) {
                        bufferedOutputStream = bufferedOutputStream3;
                        e = e3;
                        str = "TinyData write to cache file  failed item:" + ibVar.d();
                        A = bufferedOutputStream;
                        com.xiaomi.channel.commonutils.logger.b.a(str, e);
                        com.xiaomi.push.y.a((Closeable) null);
                        com.xiaomi.push.y.a((Closeable) A);
                        return;
                    } catch (Throwable th) {
                        A = bufferedOutputStream3;
                        th = th;
                        com.xiaomi.push.y.a((Closeable) null);
                        com.xiaomi.push.y.a((Closeable) A);
                        throw th;
                    }
                }
                com.xiaomi.channel.commonutils.logger.b.m117a("TinyData write to cache file failed case encryption fail item:" + ibVar.d() + "  ts:" + System.currentTimeMillis());
                com.xiaomi.push.y.a((Closeable) null);
                com.xiaomi.push.y.a((Closeable) null);
            } catch (IOException e4) {
                e = e4;
                bufferedOutputStream2 = null;
            } catch (Exception e5) {
                e = e5;
                bufferedOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
                A = 0;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }
}

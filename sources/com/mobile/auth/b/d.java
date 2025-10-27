package com.mobile.auth.b;

import android.content.Context;
import android.text.TextUtils;
import com.google.common.base.Ascii;
import com.mobile.auth.c.f;
import com.mobile.auth.c.r;
import com.mobile.auth.c.s;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private static final String f9590a = "d";

    /* renamed from: b, reason: collision with root package name */
    private static final byte[] f9591b = {15, Ascii.US, 94, 10, 90, 15, 91, Ascii.CAN, 10, Ascii.RS, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 7, 89, 10, 95, Ascii.RS};

    public static /* synthetic */ String a(Context context, Queue queue) {
        try {
            return b(context, (Queue<String>) queue);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public static /* synthetic */ Queue a(Context context, List list, int i2) {
        try {
            return c(context, list, i2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public static /* synthetic */ void a(Context context) {
        try {
            c(context);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private static void a(Context context, int i2) {
        try {
            try {
                com.mobile.auth.c.d.a(context, "key_c_l_l_v", i2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(android.content.Context r6, java.lang.String r7) {
        /*
            int r0 = r7.hashCode()     // Catch: java.lang.Throwable -> L45
            r1 = 64897(0xfd81, float:9.094E-41)
            r2 = 2
            r3 = 1
            r4 = -1
            r5 = 0
            if (r0 == r1) goto L2c
            r1 = 78159(0x1314f, float:1.09524E-40)
            if (r0 == r1) goto L22
            r1 = 66247144(0x3f2d9e8, float:1.42735105E-36)
            if (r0 == r1) goto L18
            goto L36
        L18:
            java.lang.String r0 = "ERROR"
            boolean r7 = r7.equals(r0)     // Catch: java.lang.Throwable -> L45
            if (r7 == 0) goto L36
            r7 = r3
            goto L37
        L22:
            java.lang.String r0 = "OFF"
            boolean r7 = r7.equals(r0)     // Catch: java.lang.Throwable -> L45
            if (r7 == 0) goto L36
            r7 = r2
            goto L37
        L2c:
            java.lang.String r0 = "ALL"
            boolean r7 = r7.equals(r0)     // Catch: java.lang.Throwable -> L45
            if (r7 == 0) goto L36
            r7 = r5
            goto L37
        L36:
            r7 = r4
        L37:
            if (r7 == 0) goto L40
            if (r7 == r3) goto L41
            if (r7 == r2) goto L3e
            goto L40
        L3e:
            r4 = -2
            goto L41
        L40:
            r4 = r5
        L41:
            a(r6, r4)     // Catch: java.lang.Throwable -> L45
            goto L4e
        L45:
            r6 = move-exception
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r6)     // Catch: java.lang.Throwable -> L4a
            goto L4e
        L4a:
            r6 = move-exception
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r6)
        L4e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.b.d.a(android.content.Context, java.lang.String):void");
    }

    public static void a(Context context, List<String> list) {
        try {
            int iB = b(context);
            if (iB == -2) {
                return;
            }
            b(context, list, iB);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ void a(Context context, Queue queue, int i2) {
        try {
            b(context, (Queue<String>) queue, i2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private static int b(Context context) {
        try {
            return com.mobile.auth.c.d.b(context, "key_c_l_l_v", 0);
        } catch (Throwable th) {
            try {
                th.printStackTrace();
                return 0;
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                    return -1;
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                    return -1;
                }
            }
        }
    }

    private static String b(Context context, String str) {
        try {
            return a.a(context, "https://api-e189.21cn.com/gw/client/accountMsg.do", str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private static String b(Context context, Queue<String> queue) {
        try {
            JSONArray jSONArray = new JSONArray();
            String string = jSONArray.toString();
            if (!queue.isEmpty()) {
                Iterator<String> it = queue.iterator();
                while (it.hasNext()) {
                    try {
                        jSONArray.put(new JSONObject(it.next()));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
            if (jSONArray.length() <= 0) {
                return "";
            }
            String string2 = jSONArray.toString();
            if (!TextUtils.isEmpty(string2)) {
                try {
                    string = URLEncoder.encode(com.mobile.auth.c.c.a(f.a(string2, s.a(f9591b)).getBytes()), "UTF-8");
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            return b(context, string);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private static void b(final Context context, final List<String> list, final int i2) {
        try {
            r.a().a(new Runnable() { // from class: com.mobile.auth.b.d.1
                @Override // java.lang.Runnable
                public void run() throws JSONException {
                    try {
                        Queue queueA = d.a(context, list, i2);
                        if (queueA.isEmpty()) {
                            return;
                        }
                        String strA = d.a(context, queueA);
                        JSONObject jSONObject = null;
                        int i3 = -1;
                        try {
                            if (!TextUtils.isEmpty(strA)) {
                                JSONObject jSONObject2 = new JSONObject(strA);
                                try {
                                    i3 = jSONObject2.getInt("code");
                                    jSONObject = jSONObject2;
                                } catch (Exception e2) {
                                    e = e2;
                                    jSONObject = jSONObject2;
                                    e.printStackTrace();
                                    if (jSONObject != null) {
                                    }
                                    d.a(context, queueA, i2);
                                }
                            }
                        } catch (Exception e3) {
                            e = e3;
                        }
                        if (jSONObject != null || i3 != 0) {
                            d.a(context, queueA, i2);
                        } else {
                            d.a(context);
                            queueA.clear();
                        }
                    } catch (Throwable th) {
                        try {
                            th.printStackTrace();
                        } catch (Throwable th2) {
                            try {
                                ExceptionProcessor.processException(th2);
                            } catch (Throwable th3) {
                                ExceptionProcessor.processException(th3);
                            }
                        }
                    }
                }
            });
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private static void b(Context context, Queue<String> queue, int i2) {
        JSONObject jSONObject;
        try {
            String strA = "";
            JSONArray jSONArray = new JSONArray();
            if (queue != null && !queue.isEmpty()) {
                Iterator<String> it = queue.iterator();
                int i3 = 0;
                while (it.hasNext()) {
                    try {
                        jSONObject = new JSONObject(it.next());
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    if (i2 != -1 || jSONObject.getInt("rt") != 0) {
                        jSONArray.put(jSONObject);
                        i3++;
                        if (i3 > 10) {
                            break;
                        }
                    }
                }
            }
            if (jSONArray.length() > 0) {
                try {
                    strA = f.a(jSONArray.toString(), s.a(f9591b));
                } catch (Exception e3) {
                    e3.printStackTrace();
                    strA = null;
                }
            }
            if (TextUtils.isEmpty(strA)) {
                return;
            }
            c.a(context, strA);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private static synchronized Queue<String> c(Context context, List<String> list, int i2) {
        ConcurrentLinkedQueue concurrentLinkedQueue;
        try {
            concurrentLinkedQueue = new ConcurrentLinkedQueue();
            String strA = c.a(context);
            if (!TextUtils.isEmpty(strA)) {
                try {
                    JSONArray jSONArray = new JSONArray(f.a(strA, s.a(f9591b)));
                    int length = jSONArray.length();
                    for (int i3 = 0; i3 < length && i3 <= 10; i3++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i3);
                        if (jSONObject != null) {
                            concurrentLinkedQueue.add(jSONObject.toString());
                        }
                    }
                    c.a(context, "");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (i2 == -1) {
                for (String str : list) {
                    try {
                        if (new JSONObject(str).getInt("rt") != 0) {
                            concurrentLinkedQueue.add(str);
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            } else if (i2 == 0) {
                concurrentLinkedQueue.addAll(list);
            }
            while (concurrentLinkedQueue.size() > 10) {
                concurrentLinkedQueue.poll();
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        return concurrentLinkedQueue;
    }

    private static void c(Context context) {
        try {
            c.a(context, "");
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}

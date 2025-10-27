package com.tencent.tbs.one.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import com.hjq.permissions.Permission;
import com.tencent.tbs.logger.f;
import com.tencent.tbs.one.TBSOneConfigurationKeys;
import com.tencent.tbs.one.impl.a.g;
import com.tencent.tbs.one.impl.common.Statistics;
import com.tencent.tbs.one.impl.e.e;
import com.tencent.tbs.one.impl.e.h;
import com.tencent.tbs.one.impl.e.i;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.eclipse.jetty.servlet.ServletHandler;

/* loaded from: classes6.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    public static final Object f21699a = new Object();

    /* renamed from: b, reason: collision with root package name */
    public static boolean f21700b = false;

    /* renamed from: c, reason: collision with root package name */
    public static boolean f21701c = true;

    public static com.tencent.tbs.one.impl.a.b<e<com.tencent.tbs.one.impl.common.d>> a(i iVar, int i2, File file, Bundle bundle) {
        Context context = iVar.f22204a;
        String str = iVar.f22205b;
        String strF = iVar.f();
        Object objG = iVar.g(TBSOneConfigurationKeys.SHARABLE_APPLICATION_PACKAGES);
        return new com.tencent.tbs.one.impl.e.a.b(context, str, strF, objG instanceof String ? ((String) objG).split(File.pathSeparator) : com.tencent.tbs.one.impl.common.b.f21978a, i2, file, bundle);
    }

    public static h a(Context context, String str) {
        i iVar = new i(context, str);
        if (a(iVar, context, str)) {
            g.a("Debug.conf exists, in debug mode, category: " + str, new Object[0]);
        }
        if (ServletHandler.__DEFAULT_SERVLET.equals(str)) {
            SharedPreferences sharedPreferences = iVar.f22223q;
            if (!sharedPreferences.contains("in_use_component_names")) {
                try {
                    Set<String> setKeySet = com.tencent.tbs.sdk.a.f22287a.keySet();
                    SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                    editorEdit.putStringSet("in_use_component_names", setKeySet);
                    editorEdit.apply();
                } catch (Throwable th) {
                    g.c("[%s] Failed to preset in-use component names", ServletHandler.__DEFAULT_SERVLET, th);
                }
            }
        }
        return iVar;
    }

    public static void a(Context context) {
        synchronized (f21699a) {
            if (!f21700b) {
                f.a(context.getApplicationContext(), "onelog", 604800000L, 3145728L);
                g.a(new g.b() { // from class: com.tencent.tbs.one.impl.a.1
                    @Override // com.tencent.tbs.one.impl.a.g.b
                    public final void a(int i2, String str) {
                        if (f.a()) {
                            f.b bVar = f.f21655b;
                            bVar.f21661a = "TBSOne";
                            bVar.a(i2, str);
                        }
                    }
                });
                b.a(context.getApplicationContext());
                Statistics.initialize(new Statistics.StatisticsProvider() { // from class: com.tencent.tbs.one.impl.a.2
                    @Override // com.tencent.tbs.one.impl.common.Statistics.StatisticsProvider
                    public final void reportEvent(String str, int i2, Map map) {
                        if (i2 == 201 || i2 == 209 || i2 == 214 || i2 == 506) {
                            return;
                        }
                        a.a(str, i2, map);
                    }

                    @Override // com.tencent.tbs.one.impl.common.Statistics.StatisticsProvider
                    public final void reportLog() {
                        b.a();
                    }
                });
                Runtime.getRuntime().addShutdownHook(new com.tencent.tbs.one.impl.e.a(context.getDir("tbs", 0)));
                f21700b = true;
            }
        }
    }

    public static /* synthetic */ void a(String str, int i2, Map map) {
        if (f21701c) {
            b bVarA = b.a(str, i2);
            if (map == null) {
                bVarA.b();
                return;
            }
            if (map.containsKey(Statistics.KEY_DEPS_VERSION_CODE)) {
                bVarA.f21775d = ((Integer) map.get(Statistics.KEY_DEPS_VERSION_CODE)).intValue();
            }
            if (map.containsKey(Statistics.KEY_DEPS_LOCAL_VERSION_CODE)) {
                bVarA.f21776e = ((Integer) map.get(Statistics.KEY_DEPS_LOCAL_VERSION_CODE)).intValue();
            }
            if (map.containsKey(Statistics.KEY_DEPS_COMPONENT_LOCV)) {
                bVarA.f21777f = (String) map.get(Statistics.KEY_DEPS_COMPONENT_LOCV);
            }
            String str2 = map.containsKey(Statistics.KEY_COMPONENT_NAME) ? (String) map.get(Statistics.KEY_COMPONENT_NAME) : "";
            int iIntValue = map.containsKey(Statistics.KEY_COMPONENT_VERSION_CODE) ? ((Integer) map.get(Statistics.KEY_COMPONENT_VERSION_CODE)).intValue() : -1;
            int iIntValue2 = map.containsKey(Statistics.KEY_COMPONENT_LOCAL_VERSION_CODE) ? ((Integer) map.get(Statistics.KEY_COMPONENT_LOCAL_VERSION_CODE)).intValue() : -1;
            bVarA.f21772a = str2;
            bVarA.f21773b = iIntValue;
            bVarA.f21774c = iIntValue2;
            bVarA.b();
        }
    }

    public static void a(boolean z2) {
        f21701c = z2;
    }

    public static boolean a(i iVar, Context context, String str) throws IOException {
        FileInputStream fileInputStream;
        if (context != null && context.getApplicationContext().checkSelfPermission(Permission.READ_EXTERNAL_STORAGE) != 0) {
            g.a("External storage read permission has not bean granted yet, giving up entering debug mode", new Object[0]);
            return false;
        }
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File file = com.tencent.tbs.one.impl.a.a.a(context) ? new File(externalStorageDirectory, "Android/data/" + context.getPackageName() + "/files/tbs/" + str + "/debug.conf") : new File(new File(new File(new File(new File(externalStorageDirectory, SocializeProtocolConstants.PROTOCOL_KEY_TENCENT), "tbs"), context.getPackageName()), str), "debug.conf");
        if (file.exists()) {
            b.a(file.exists());
            g.a("Debug conf exist: " + file.getAbsolutePath(), new Object[0]);
            iVar.a(TBSOneConfigurationKeys.ONLINE_SERVICE_URL, "https://tbsone.sparta.html5.qq.com");
            try {
                fileInputStream = new FileInputStream(file);
            } catch (Throwable th) {
                th = th;
                fileInputStream = null;
            }
            try {
                Properties properties = new Properties();
                properties.load(fileInputStream);
                for (Map.Entry entry : properties.entrySet()) {
                    String string = entry.getKey().toString();
                    Object value = entry.getValue();
                    g.a("Debug configuration: " + string + " = " + value, new Object[0]);
                    iVar.a(string, value);
                }
            } catch (Throwable th2) {
                th = th2;
                try {
                    g.c("Exception when read debug.conf", th);
                    return file.exists();
                } finally {
                    com.tencent.tbs.one.impl.a.d.a(fileInputStream);
                }
            }
        }
        return file.exists();
    }
}

package com.tencent.tbs.reader;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.tencent.tbs.one.TBSOneCallback;
import com.tencent.tbs.one.TBSOneComponent;
import com.tencent.tbs.one.TBSOneConfigurationKeys;
import com.tencent.tbs.one.TBSOneManager;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;

/* loaded from: classes6.dex */
public class c {

    /* renamed from: g, reason: collision with root package name */
    public static volatile c f22275g;

    /* renamed from: a, reason: collision with root package name */
    public ITbsReaderEntry f22276a;

    /* renamed from: b, reason: collision with root package name */
    public TBSOneComponent f22277b;

    /* renamed from: c, reason: collision with root package name */
    public String f22278c;

    /* renamed from: d, reason: collision with root package name */
    public int f22279d;

    /* renamed from: e, reason: collision with root package name */
    public String f22280e = null;

    /* renamed from: f, reason: collision with root package name */
    public Map f22281f = null;

    public class a extends TBSOneCallback<File> {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ TBSOneCallback f22282a;

        public a(TBSOneCallback tBSOneCallback) {
            this.f22282a = tBSOneCallback;
        }

        @Override // com.tencent.tbs.one.TBSOneCallback
        public void onCompleted(File file) {
            File file2 = file;
            Log.d("ReaderEngine", "TBSOneCallback:onCompleted");
            c.this.c();
            TBSOneCallback tBSOneCallback = this.f22282a;
            if (tBSOneCallback != null) {
                tBSOneCallback.onCompleted(file2);
            }
        }

        @Override // com.tencent.tbs.one.TBSOneCallback
        public void onError(int i2, String str) {
            Log.e("ReaderEngine", "TBSOneCallback:onError:" + i2);
            String.format("tbs:onError,code=%d, des=%s", Integer.valueOf(i2), str);
            c.this.c();
            TBSOneCallback tBSOneCallback = this.f22282a;
            if (tBSOneCallback != null) {
                tBSOneCallback.onError(i2, str);
            }
        }

        @Override // com.tencent.tbs.one.TBSOneCallback
        public void onProgressChanged(int i2, int i3) {
            TBSOneCallback tBSOneCallback = this.f22282a;
            if (tBSOneCallback != null) {
                tBSOneCallback.onProgressChanged(i2, i3);
            }
        }
    }

    public static c d() {
        if (f22275g == null) {
            synchronized (c.class) {
                if (f22275g == null) {
                    f22275g = new c();
                }
            }
        }
        return f22275g;
    }

    public final int a(TBSOneManager tBSOneManager, ITbsReaderEntry iTbsReaderEntry) {
        this.f22276a = iTbsReaderEntry;
        iTbsReaderEntry.initRuntimeEnvironment();
        if (!this.f22276a.isSupportCurrentPlatform()) {
            this.f22276a = null;
            return -3;
        }
        boolean zBooleanValue = a(a()) ? false : com.tencent.tbs.tbsfile.a.f22288a.booleanValue();
        Log.d("ReaderEngine", "initReaderEntry,canAutoUpdate:" + zBooleanValue);
        if (zBooleanValue) {
            try {
                ITbsReaderEntry iTbsReaderEntry2 = this.f22276a;
                if (iTbsReaderEntry2 == null) {
                    tBSOneManager.setAutoUpdateEnabled(true);
                } else {
                    tBSOneManager.setAutoUpdateEnabled(iTbsReaderEntry2.canAutoUpdate());
                }
            } catch (Throwable th) {
                th.printStackTrace();
                try {
                    tBSOneManager.setAutoUpdateEnabled(true);
                } catch (Throwable unused) {
                }
            }
        }
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:27:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.tencent.tbs.one.TBSOneManager.Policy a() {
        /*
            r7 = this;
            java.lang.String r0 = r7.f22280e
            java.lang.String r1 = "buildinAssetsOnly"
            if (r0 == 0) goto L7
            goto L8
        L7:
            r0 = r1
        L8:
            int r2 = r0.hashCode()
            r3 = 4
            r4 = 3
            r5 = 2
            r6 = 1
            switch(r2) {
                case -1417854369: goto L44;
                case -1205353033: goto L3a;
                case -1012267427: goto L30;
                case -908777766: goto L26;
                case 1280306693: goto L1c;
                case 1633525314: goto L14;
                default: goto L13;
            }
        L13:
            goto L4e
        L14:
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L4e
            r0 = 5
            goto L4f
        L1c:
            java.lang.String r1 = "localFirst"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L4e
            r0 = r5
            goto L4f
        L26:
            java.lang.String r1 = "buildinAssetsFirst"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L4e
            r0 = r3
            goto L4f
        L30:
            java.lang.String r1 = "buildinFirst"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L4e
            r0 = 0
            goto L4f
        L3a:
            java.lang.String r1 = "localOnly"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L4e
            r0 = r4
            goto L4f
        L44:
            java.lang.String r1 = "buildinOnly"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L4e
            r0 = r6
            goto L4f
        L4e:
            r0 = -1
        L4f:
            if (r0 == 0) goto L68
            if (r0 == r6) goto L65
            if (r0 == r5) goto L62
            if (r0 == r4) goto L5f
            if (r0 == r3) goto L5c
            com.tencent.tbs.one.TBSOneManager$Policy r0 = com.tencent.tbs.one.TBSOneManager.Policy.BUILTIN_ASSETS_ONLY
            return r0
        L5c:
            com.tencent.tbs.one.TBSOneManager$Policy r0 = com.tencent.tbs.one.TBSOneManager.Policy.BUILTIN_ASSETS_FIRST
            return r0
        L5f:
            com.tencent.tbs.one.TBSOneManager$Policy r0 = com.tencent.tbs.one.TBSOneManager.Policy.LOCAL_ONLY
            return r0
        L62:
            com.tencent.tbs.one.TBSOneManager$Policy r0 = com.tencent.tbs.one.TBSOneManager.Policy.LOCAL_FIRST
            return r0
        L65:
            com.tencent.tbs.one.TBSOneManager$Policy r0 = com.tencent.tbs.one.TBSOneManager.Policy.BUILTIN_ONLY
            return r0
        L68:
            com.tencent.tbs.one.TBSOneManager$Policy r0 = com.tencent.tbs.one.TBSOneManager.Policy.BUILTIN_FIRST
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tbs.reader.c.a():com.tencent.tbs.one.TBSOneManager$Policy");
    }

    public final ITbsReaderEntry a(TBSOneComponent tBSOneComponent) {
        if (tBSOneComponent == null) {
            return null;
        }
        this.f22277b = tBSOneComponent;
        try {
            Object entryObject = tBSOneComponent.getEntryObject();
            this.f22278c = tBSOneComponent.getVersionName();
            this.f22279d = tBSOneComponent.getVersionCode();
            if (!(entryObject instanceof ITbsReaderEntry)) {
                return null;
            }
            ITbsReaderEntry iTbsReaderEntry = (ITbsReaderEntry) entryObject;
            iTbsReaderEntry.initSettings(this.f22281f);
            return iTbsReaderEntry;
        } catch (Throwable th) {
            Log.d("ReaderEngine", "Throwable: " + th.getMessage());
            return null;
        }
    }

    public final Object a(String str, Object obj, Class<?>[] clsArr, Object... objArr) {
        TBSOneComponent tBSOneComponent = this.f22277b;
        if (tBSOneComponent != null) {
            try {
                Method method = tBSOneComponent.getEntryClassLoader().loadClass("com.tencent.tbs.ug.component.TbsReaderEntry").getMethod(str, clsArr);
                method.setAccessible(true);
                return method.invoke(obj, objArr);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return null;
    }

    public void a(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("reader_sdk_settings", 0);
            if (sharedPreferences != null) {
                SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                editorEdit.putString("reader_record_paths", "");
                editorEdit.putString("reader_record_times", "");
                editorEdit.commit();
            }
        } catch (Throwable th) {
            Log.e("ReaderEngine", th.getMessage());
        }
    }

    public final void a(Context context, ITbsReaderCallback iTbsReaderCallback) {
        a("authenticateAsync", null, new Class[]{Context.class, ITbsReaderCallback.class}, context, iTbsReaderCallback);
    }

    public boolean a(int i2, String str) {
        ITbsReaderEntry iTbsReaderEntry = this.f22276a;
        if (iTbsReaderEntry != null) {
            return iTbsReaderEntry.isSupportExt(i2, str);
        }
        return false;
    }

    public boolean a(Context context, TBSOneCallback tBSOneCallback, boolean z2) {
        Log.d("ReaderEngine", "fileEnginePreLoad,isForeground:" + z2);
        TBSOneManager defaultInstance = TBSOneManager.getDefaultInstance(context);
        defaultInstance.setPolicy(a());
        boolean zIsComponentInstalled = false;
        try {
            zIsComponentInstalled = defaultInstance.isComponentInstalled("file");
            if (!zIsComponentInstalled) {
                Log.d("ReaderEngine", "fileEnginePreLoad,into download:FileComponent,isForeground:" + z2);
                if (z2) {
                    a aVar = new a(tBSOneCallback);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(TBSOneConfigurationKeys.IGNORE_WIFI_STATE, true);
                    bundle.putBoolean(TBSOneConfigurationKeys.IGNORE_FREQUENCY_LIMITATION, true);
                    bundle.putBoolean(TBSOneConfigurationKeys.IGNORE_FLOW_CONTROL, true);
                    defaultInstance.installComponent("file", bundle, aVar);
                } else {
                    Bundle bundle2 = new Bundle();
                    bundle2.putBoolean(TBSOneConfigurationKeys.IGNORE_WIFI_STATE, true);
                    defaultInstance.installComponent("file", bundle2, tBSOneCallback);
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (zIsComponentInstalled) {
            try {
                defaultInstance.setAutoUpdateEnabled(true);
            } catch (Throwable unused) {
            }
        }
        return zIsComponentInstalled;
    }

    public final boolean a(TBSOneManager.Policy policy) {
        return policy == TBSOneManager.Policy.BUILTIN_ASSETS_ONLY || policy == TBSOneManager.Policy.BUILTIN_ONLY || policy == TBSOneManager.Policy.LOCAL_ONLY;
    }

    public View b(Context context) {
        TBSOneManager defaultInstance = TBSOneManager.getDefaultInstance(context);
        if (defaultInstance != null) {
            return defaultInstance.getDebugger().createPanelView(context);
        }
        return null;
    }

    public String b() {
        Map map = this.f22281f;
        return (map == null || !map.containsKey("onelog")) ? "" : String.valueOf(this.f22281f.get("onelog"));
    }

    public void c() {
    }

    public boolean c(Context context) {
        try {
            String str = Build.CPU_ABI.matches("armeabi.*") ? "armeabi" : "arm64";
            SharedPreferences sharedPreferences = context.getSharedPreferences("tbs_file_sdk_env", 0);
            if (sharedPreferences == null) {
                return true;
            }
            String string = sharedPreferences.getString("key_abi_type", "");
            if (!TextUtils.isEmpty(string) && string.equals(str)) {
                return true;
            }
            try {
                com.tencent.tbs.logger.file.a.b(new File(context.getDir("tbs", 0).getAbsolutePath(), "home/default"));
            } catch (Throwable th) {
                Log.e("ReaderEngine", th.getMessage());
            }
            try {
                SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                editorEdit.putString("key_abi_type", str);
                editorEdit.commit();
                return true;
            } catch (Throwable th2) {
                Log.e("ReaderEngine", th2.getMessage());
                return true;
            }
        } catch (Throwable th3) {
            Log.e("ReaderEngine", th3.getMessage());
            return false;
        }
    }

    public String d(Context context) {
        try {
            return (String) a("getLicenseToken", null, new Class[]{Context.class}, context);
        } catch (Throwable unused) {
            return "";
        }
    }
}

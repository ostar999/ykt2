package com.beizi.ad;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.azhon.appupdate.config.Constant;
import com.beizi.ad.a.a.g;
import com.beizi.ad.a.a.i;
import com.beizi.ad.internal.utilities.DownloadFactory;
import com.beizi.ad.internal.utilities.ReportEventUtil;
import java.io.File;
import java.util.HashMap;

/* loaded from: classes2.dex */
public final class DownloadService extends Service {

    /* renamed from: g, reason: collision with root package name */
    private static DownloadFactory.Download f3625g;

    /* renamed from: a, reason: collision with root package name */
    private long f3626a;

    /* renamed from: b, reason: collision with root package name */
    private String f3627b;

    /* renamed from: c, reason: collision with root package name */
    private DownloadManager f3628c;

    /* renamed from: d, reason: collision with root package name */
    private a f3629d;

    /* renamed from: e, reason: collision with root package name */
    private b f3630e;

    /* renamed from: f, reason: collision with root package name */
    private c f3631f;

    /* renamed from: h, reason: collision with root package name */
    private HashMap<String, com.beizi.ad.a.a> f3632h;

    /* renamed from: i, reason: collision with root package name */
    private HashMap<String, Boolean> f3633i;

    /* renamed from: j, reason: collision with root package name */
    private HashMap<Long, String> f3634j;

    public class a extends ContentObserver {
        public a(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z2) {
            super.onChange(z2);
            int[] iArr = {0, 0, 0};
            Cursor cursorQuery = null;
            try {
                try {
                    cursorQuery = DownloadService.this.f3628c.query(new DownloadManager.Query().setFilterById(DownloadService.this.f3626a));
                    if (cursorQuery != null && cursorQuery.moveToFirst()) {
                        int i2 = cursorQuery.getInt(cursorQuery.getColumnIndex("status"));
                        i.a("DownloadService", "onChange status：" + i2);
                        if (i2 == 1) {
                            DownloadService.this.b(com.beizi.ad.a.b.a(DownloadService.this).a());
                        }
                        iArr[0] = cursorQuery.getInt(cursorQuery.getColumnIndexOrThrow("bytes_so_far"));
                        iArr[1] = cursorQuery.getInt(cursorQuery.getColumnIndexOrThrow("total_size"));
                        iArr[2] = cursorQuery.getInt(cursorQuery.getColumnIndex("status"));
                        i.a("DownloadService", "progress：" + iArr[0] + "/" + iArr[1] + "");
                    }
                    if (cursorQuery == null) {
                        return;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (cursorQuery == null) {
                        return;
                    }
                }
                cursorQuery.close();
            } catch (Throwable th) {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                throw th;
            }
        }
    }

    public class b extends BroadcastReceiver {
        public b() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            i.a("DownloadService", "onReceived...download finish...begin install！");
            long longExtra = intent.getLongExtra("extra_download_id", -1L);
            if (DownloadService.this.f3634j == null || DownloadService.this.f3632h == null) {
                return;
            }
            String str = (String) DownloadService.this.f3634j.get(Long.valueOf(longExtra));
            if (DownloadService.this.f3633i != null) {
                DownloadService.this.f3633i.put(str, Boolean.FALSE);
            }
            com.beizi.ad.a.a aVar = (com.beizi.ad.a.a) DownloadService.this.f3632h.get(str);
            if (aVar != null) {
                if (aVar.h() != null) {
                    ReportEventUtil.report(aVar.h().c());
                }
                if (Build.VERSION.SDK_INT < 26) {
                    DownloadService.this.a(context, Long.valueOf(longExtra), aVar);
                } else if (context.getPackageManager().canRequestPackageInstalls()) {
                    DownloadService.this.a(context, Long.valueOf(longExtra), aVar);
                } else {
                    DownloadService.this.a(context, Long.valueOf(longExtra), aVar);
                }
            }
        }
    }

    public class c extends BroadcastReceiver {
        public c() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
                String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                i.a("DownloadService", "Install Success:" + schemeSpecificPart);
                if (DownloadService.this.f3632h == null) {
                    return;
                }
                com.beizi.ad.a.a aVar = (com.beizi.ad.a.a) DownloadService.this.f3632h.get(schemeSpecificPart);
                if (aVar != null) {
                    File file = new File(aVar.d(), aVar.b());
                    if (file.exists()) {
                        file.delete();
                    }
                    if (aVar.h() != null) {
                        ReportEventUtil.report(aVar.h().e());
                    }
                    DownloadService.this.f3632h.remove(schemeSpecificPart);
                }
            }
            if (DownloadService.this.f3632h == null || !DownloadService.this.f3632h.isEmpty()) {
                return;
            }
            DownloadService.this.stopSelf();
        }
    }

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        i.a("DownloadService", "DownloadService onCreate()");
        this.f3628c = (DownloadManager) getSystemService(AliyunLogCommon.SubModule.download);
        this.f3629d = new a(new Handler());
        this.f3630e = new b();
        this.f3631f = new c();
        this.f3632h = new HashMap<>();
        this.f3633i = new HashMap<>();
        this.f3634j = new HashMap<>();
        a();
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        i.a("DownloadService", "DownloadService onDestroy()");
        b();
        d();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i2, int i3) {
        if (intent == null) {
            return 1;
        }
        i.a("DownloadService", "DownloadService onStartCommand()");
        c();
        return super.onStartCommand(intent, i2, i3);
    }

    private void b() {
        getContentResolver().unregisterContentObserver(this.f3629d);
        unregisterReceiver(this.f3630e);
        unregisterReceiver(this.f3631f);
        i.a("DownloadService", "unregister()");
    }

    private void c() {
        com.beizi.ad.a.a aVarA = com.beizi.ad.a.b.a(this).a();
        if (aVarA == null) {
            return;
        }
        HashMap<String, com.beizi.ad.a.a> map = this.f3632h;
        if (map != null) {
            map.put(aVarA.c(), aVarA);
        }
        HashMap<String, Boolean> map2 = this.f3633i;
        if (map2 != null && map2.get(aVarA.c()) == null) {
            i.a("DownloadService", "not have package status...");
            this.f3633i.put(aVarA.c(), Boolean.FALSE);
        }
        if (TextUtils.isEmpty(aVarA.g())) {
            this.f3627b = getPackageName() + ".fileprovider";
        } else {
            this.f3627b = aVarA.g();
        }
        g.a(aVarA.d());
        a(aVarA);
    }

    private void d() {
        DownloadFactory.Download download = f3625g;
        if (download != null) {
            download.destroy();
        }
        if (this.f3633i != null) {
            this.f3633i = null;
        }
        if (this.f3632h != null) {
            this.f3632h = null;
        }
        if (this.f3634j != null) {
            this.f3634j = null;
        }
        com.beizi.ad.a.b.a(this).e();
        i.a("DownloadService", "releaseResources()");
    }

    private void a() {
        getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), true, this.f3629d);
        registerReceiver(this.f3630e, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_INSTALL");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addDataScheme("package");
        registerReceiver(this.f3631f, intentFilter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(final com.beizi.ad.a.a aVar) {
        if (aVar == null) {
            return;
        }
        i.a("DownloadService", "--appUpdate downloadApk start--");
        DownloadFactory.Download downloadCreate = DownloadFactory.create();
        f3625g = downloadCreate;
        downloadCreate.register(new DownloadFactory.DownloadDelegate() { // from class: com.beizi.ad.DownloadService.1
            @Override // com.beizi.ad.internal.utilities.DownloadFactory.DownloadDelegate
            public boolean onCheck(File file) {
                return true;
            }

            @Override // com.beizi.ad.internal.utilities.DownloadFactory.DownloadDelegate
            public void onFail(int i2) {
                i.a("DownloadService", "--appUpdate downloadApk onFail--");
                try {
                    String strA = aVar.a();
                    if (TextUtils.isEmpty(strA) || !strA.contains("http")) {
                        return;
                    }
                    if (DownloadService.this.f3633i != null) {
                        DownloadService.this.f3633i.put(aVar.c(), Boolean.TRUE);
                    }
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.setData(Uri.parse(strA));
                    intent.setFlags(268435456);
                    DownloadService.this.startActivity(intent);
                } catch (Exception e2) {
                    i.a("DownloadService", "skip browser fail:" + e2);
                }
            }

            @Override // com.beizi.ad.internal.utilities.DownloadFactory.DownloadDelegate
            public void onProgress(long j2, long j3) {
            }

            @Override // com.beizi.ad.internal.utilities.DownloadFactory.DownloadDelegate
            public void onSuccess(File file) {
                i.a("DownloadService", "--appUpdate downloadApk onSuccess--");
                if (DownloadService.this.f3633i != null) {
                    DownloadService.this.f3633i.put(aVar.c(), Boolean.FALSE);
                }
                DownloadService.this.a(DownloadService.this.getApplicationContext(), -1L, aVar);
            }
        });
        f3625g.start(new DownloadFactory.Request(aVar.a(), aVar.d(), aVar.b()));
    }

    private synchronized void a(com.beizi.ad.a.a aVar) {
        Uri uriForFile;
        HashMap<String, Boolean> map = this.f3633i;
        if (map != null && map.get(aVar.c()) != null && this.f3633i.get(aVar.c()).booleanValue()) {
            i.a("DownloadService", "downloading..." + aVar.c() + "...please not repeat click");
            Toast.makeText(this, "正在下载…请勿重复点击", 0).show();
            return;
        }
        File file = new File(aVar.d(), aVar.c() + ".zip");
        if (file.exists()) {
            file.delete();
            i.a("DownloadService", "apkCacheFile......remove:" + file.exists());
        }
        File file2 = new File(aVar.d(), aVar.b());
        if (file2.exists()) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addFlags(268435456);
                intent.setAction("android.intent.action.VIEW");
                if (Build.VERSION.SDK_INT < 24) {
                    uriForFile = Uri.fromFile(file2);
                } else {
                    uriForFile = FileProvider.getUriForFile(this, aVar.g(), file2);
                    intent.addFlags(3);
                }
                if (uriForFile != null) {
                    intent.setDataAndType(uriForFile, "application/vnd.android.package-archive");
                    startActivity(intent);
                    c(aVar);
                }
            } catch (Exception e2) {
                Log.d("lance", "apkFile.exists():" + e2);
            }
        }
        try {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(aVar.a()));
            request.setTitle(aVar.e());
            request.setDescription(aVar.f());
            request.setNotificationVisibility(1);
            request.setDestinationUri(Uri.fromFile(file));
            request.setMimeType("application/vnd.android.package-archive");
            this.f3626a = this.f3628c.enqueue(request);
            i.b("DownloadService", "mReqId:" + this.f3626a);
            HashMap<Long, String> map2 = this.f3634j;
            if (map2 != null) {
                map2.put(Long.valueOf(this.f3626a), aVar.c());
            }
            HashMap<String, Boolean> map3 = this.f3633i;
            if (map3 != null) {
                map3.put(aVar.c(), Boolean.TRUE);
            }
            Toast.makeText(this, "已开始下载…", 0).show();
            i.a("DownloadService", "BEGIN_DOWNLOAD!");
            if (aVar.h() != null) {
                ReportEventUtil.report(aVar.h().b());
            }
        } catch (Exception e3) {
            Log.d("lance", "DownloadManager download fail:" + e3);
            try {
                if (!TextUtils.isEmpty(aVar.a()) && aVar.a().contains("http")) {
                    HashMap<String, Boolean> map4 = this.f3633i;
                    if (map4 != null) {
                        map4.put(aVar.c(), Boolean.TRUE);
                    }
                    Intent intent2 = new Intent();
                    intent2.setAction("android.intent.action.VIEW");
                    intent2.setData(Uri.parse(aVar.a()));
                    intent2.setFlags(268435456);
                    startActivity(intent2);
                }
            } catch (Exception e4) {
                Log.d("lance", "skip browser fail:" + e4);
            }
        }
    }

    private void c(com.beizi.ad.a.a aVar) {
        i.a("DownloadService", "BEGIN_INSTALL!");
        if (aVar.h() != null) {
            ReportEventUtil.report(aVar.h().d());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context, Long l2, com.beizi.ad.a.a aVar) {
        Uri uriForFile;
        try {
            File file = new File(aVar.d(), aVar.c() + ".zip");
            if (file.exists()) {
                File file2 = new File(aVar.d(), aVar.b());
                file.renameTo(file2);
                i.a("DownloadService", "apkFile......raName:" + file2.exists());
            }
            Intent intent = new Intent();
            intent.addFlags(268435456);
            intent.setAction("android.intent.action.VIEW");
            if (Build.VERSION.SDK_INT < 24) {
                File fileA = a(context, l2.longValue());
                if (fileA != null) {
                    uriForFile = Uri.fromFile(fileA);
                    if (uriForFile != null) {
                        uriForFile = Uri.parse(uriForFile.toString().replace(".zip", Constant.APK_SUFFIX));
                        i.a("DownloadService", "uri......" + uriForFile);
                    }
                } else {
                    uriForFile = null;
                }
            } else {
                uriForFile = FileProvider.getUriForFile(context, this.f3627b, new File(aVar.d(), aVar.b()));
                intent.addFlags(3);
            }
            if (uriForFile != null) {
                intent.setDataAndType(uriForFile, "application/vnd.android.package-archive");
                context.startActivity(intent);
                c(aVar);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private static File a(Context context, long j2) {
        Cursor cursorQuery;
        String path;
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(AliyunLogCommon.SubModule.download);
        File file = null;
        if (j2 != -1) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(j2);
            query.setFilterByStatus(8);
            if (downloadManager != null && (cursorQuery = downloadManager.query(query)) != null) {
                if (cursorQuery.moveToFirst()) {
                    String string = cursorQuery.getString(cursorQuery.getColumnIndex("local_uri"));
                    if (!TextUtils.isEmpty(string) && (path = Uri.parse(string).getPath()) != null) {
                        file = new File(path);
                    }
                }
                cursorQuery.close();
            }
        }
        return file;
    }
}

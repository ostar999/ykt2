package com.beizi.fusion.b;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import com.beizi.fusion.g.aa;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.al;
import com.beizi.fusion.g.ar;
import com.beizi.fusion.g.h;
import com.beizi.fusion.g.j;
import com.beizi.fusion.g.x;
import com.beizi.fusion.g.z;
import com.beizi.fusion.model.Messenger;
import com.beizi.fusion.model.RequestInfo;
import com.beizi.fusion.model.ResponseInfo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static c f4796a;

    /* renamed from: b, reason: collision with root package name */
    private Context f4797b;

    /* renamed from: c, reason: collision with root package name */
    private List<Messenger.EventsBean> f4798c;

    /* renamed from: d, reason: collision with root package name */
    private long f4799d = 128;

    /* renamed from: e, reason: collision with root package name */
    private long f4800e = 172800000;

    private c(Context context) {
        this.f4797b = context;
        RequestInfo requestInfoInit = RequestInfo.getInstance(context).init();
        if (requestInfoInit.isInit()) {
            return;
        }
        requestInfoInit.init();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(b bVar) {
        try {
            ResponseInfo responseInfo = ResponseInfo.getInstance(this.f4797b);
            String strA = a(bVar.d());
            if (responseInfo.getMessenger() != null) {
                this.f4798c = responseInfo.getMessenger().getEvents();
                this.f4799d = Long.valueOf(responseInfo.getMessenger().getFileMaxSize()).longValue();
                this.f4800e = responseInfo.getMessenger().getExpireTime();
                List<Messenger.EventsBean> list = this.f4798c;
                if (list == null || list.size() <= 0) {
                    return;
                }
                for (int i2 = 0; i2 < this.f4798c.size(); i2++) {
                    Messenger.EventsBean eventsBean = this.f4798c.get(i2);
                    List<String> codes = eventsBean.getCodes();
                    if (codes != null && codes.size() > 0) {
                        for (int i3 = 0; i3 < codes.size(); i3++) {
                            if (!TextUtils.isEmpty(bVar.d()) && !TextUtils.isEmpty(codes.get(i3)) && (bVar.d().equals(codes.get(i3)) || strA.equals(codes.get(i3)))) {
                                if (eventsBean.getIsOnline().equals("1")) {
                                    bVar.a(eventsBean.getUploadUrl());
                                    a(eventsBean, bVar);
                                } else {
                                    a(eventsBean, bVar, false);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void b(b bVar) {
        c(bVar);
    }

    public static c a(Context context) {
        if (f4796a == null) {
            synchronized (c.class) {
                if (f4796a == null) {
                    f4796a = new c(context);
                }
            }
        }
        return f4796a;
    }

    public void a(final b bVar) {
        h.b().e().execute(new Runnable() { // from class: com.beizi.fusion.b.c.1
            @Override // java.lang.Runnable
            public void run() {
                c.this.c(bVar);
            }
        });
    }

    private String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        return str.substring(0, str.indexOf(StrPool.DOT) + 1) + "*";
    }

    private void a(Messenger.EventsBean eventsBean, b bVar) {
        String strA;
        if (bVar != null) {
            try {
                if (TextUtils.isEmpty(bVar.a())) {
                    return;
                }
                String strA2 = bVar.a();
                if ("590.200".equalsIgnoreCase(bVar.d())) {
                    strA2 = bVar.U();
                }
                int iIndexOf = strA2.indexOf("?");
                String strSubstring = strA2.substring(0, iIndexOf);
                String strSubstring2 = strA2.substring(iIndexOf + 1);
                if ("590.200".equalsIgnoreCase(bVar.d())) {
                    strA = ar.a(this.f4797b, strSubstring2, bVar, bVar.T());
                } else {
                    strA = ar.a(this.f4797b, strSubstring2, bVar);
                }
                String strA3 = com.beizi.fusion.g.d.a(aa.a(), x.a(strA));
                if (strA3 != null) {
                    String strA4 = z.a(strSubstring, strA3.getBytes());
                    if (!TextUtils.isEmpty(strA4)) {
                        try {
                            if (new JSONObject(strA4).optInt("code") != 200) {
                                a(eventsBean, bVar, true);
                                return;
                            }
                            return;
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                            return;
                        }
                    }
                    a(eventsBean, bVar, true);
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    public void a() {
        int i2;
        int i3;
        try {
            ac.a("BeiZis", "===================doUpLoadLogs===================:" + Thread.currentThread().getName());
            long jCurrentTimeMillis = System.currentTimeMillis();
            File fileA = j.a(this.f4797b);
            ac.a("BeiZis", "doUpLoadLogs storagePath == " + fileA);
            if (fileA != null) {
                String str = fileA.getPath() + "/BeiZi/offline/";
                File[] fileArrListFiles = new File(str).listFiles();
                if (fileArrListFiles == null || fileArrListFiles.length <= 0) {
                    return;
                }
                int length = fileArrListFiles.length;
                int i4 = 0;
                int i5 = 0;
                while (i5 < length) {
                    File file = fileArrListFiles[i5];
                    if (file.isDirectory()) {
                        String strA = "";
                        File file2 = new File(str, file.getName() + "/10000.txt");
                        if (file2.exists()) {
                            strA = a(file2);
                        } else {
                            al.a(file);
                        }
                        String str2 = strA;
                        if (TextUtils.isEmpty(str2)) {
                            i2 = i5;
                        } else {
                            File[] fileArrListFiles2 = file.listFiles();
                            int length2 = fileArrListFiles2.length;
                            int i6 = i4;
                            while (i6 < length2) {
                                File file3 = fileArrListFiles2[i6];
                                if (file3.getName().equals("10000.txt")) {
                                    i3 = i5;
                                } else {
                                    i3 = i5;
                                    if (jCurrentTimeMillis - Long.valueOf(file3.getName().substring(i4, file3.getName().indexOf(StrPool.DOT))).longValue() < this.f4800e) {
                                        String strA2 = z.a(str2, file3);
                                        if (!TextUtils.isEmpty(strA2)) {
                                            try {
                                                if (new JSONObject(strA2).optInt("code") == 200) {
                                                    file3.delete();
                                                }
                                            } catch (JSONException e2) {
                                                e2.printStackTrace();
                                            }
                                        }
                                    } else {
                                        file3.delete();
                                    }
                                }
                                i6++;
                                i5 = i3;
                                i4 = 0;
                            }
                            i2 = i5;
                            if (file.listFiles().length <= 1) {
                                al.a(file);
                            }
                        }
                    } else {
                        i2 = i5;
                    }
                    i5 = i2 + 1;
                    i4 = 0;
                }
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    private static String a(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
            }
            fileInputStream.close();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return sb.toString();
    }

    private void a(Messenger.EventsBean eventsBean, b bVar, boolean z2) {
        Log.d("BeiZis", "===================doOffline===================:" + bVar.d());
        try {
            File fileA = j.a(this.f4797b);
            ac.a("BeiZis", "doOffline storagePath == " + fileA);
            if (fileA != null) {
                String offlineUrl = z2 ? eventsBean.getOfflineUrl() : eventsBean.getUploadUrl();
                String strSubstring = offlineUrl.substring(0, offlineUrl.indexOf("?"));
                String strA = com.beizi.fusion.g.d.a(aa.a(), ar.a(this.f4797b, offlineUrl.substring(offlineUrl.indexOf("?") + 1), bVar));
                String str = fileA.getPath() + "/BeiZi/offline/" + ar.a(eventsBean.toString()) + "/";
                File file = new File(str);
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file2 = new File(file, "10000.txt");
                synchronized (c.class) {
                    if (!file2.exists()) {
                        file2.createNewFile();
                        FileWriter fileWriter = new FileWriter(file2, true);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(strSubstring);
                        bufferedWriter.newLine();
                        bufferedWriter.close();
                        fileWriter.close();
                    }
                }
                long jLongValue = 0;
                for (File file3 : file.listFiles()) {
                    String strSubstring2 = file3.getName().substring(0, file3.getName().indexOf(StrPool.DOT));
                    if (Long.valueOf(strSubstring2).longValue() > jLongValue) {
                        jLongValue = Long.valueOf(strSubstring2).longValue();
                    }
                }
                if (jLongValue != 0 && jLongValue != com.heytap.mcssdk.constant.a.f7153q) {
                    File file4 = new File(str + jLongValue + ".txt");
                    if (file4.exists() && file4.length() < this.f4799d * 1000) {
                        FileWriter fileWriter2 = new FileWriter(file4, true);
                        BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
                        bufferedWriter2.write(strA);
                        bufferedWriter2.newLine();
                        bufferedWriter2.close();
                        fileWriter2.close();
                        file4.renameTo(new File(file, System.currentTimeMillis() + ".txt"));
                        return;
                    }
                }
                File file5 = new File(file, System.currentTimeMillis() + ".txt");
                file5.createNewFile();
                FileWriter fileWriter3 = new FileWriter(file5, true);
                BufferedWriter bufferedWriter3 = new BufferedWriter(fileWriter3);
                bufferedWriter3.write(strA);
                bufferedWriter3.newLine();
                bufferedWriter3.close();
                fileWriter3.close();
            }
        } catch (IOException unused) {
        } catch (Exception e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}

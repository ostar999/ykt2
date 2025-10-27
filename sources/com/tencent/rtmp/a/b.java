package com.tencent.rtmp.a;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCBuild;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public class b implements com.tencent.rtmp.a.a {

    /* renamed from: a, reason: collision with root package name */
    private final BitmapFactory.Options f20737a = new BitmapFactory.Options();

    /* renamed from: b, reason: collision with root package name */
    private HandlerThread f20738b;

    /* renamed from: c, reason: collision with root package name */
    private Handler f20739c;

    /* renamed from: d, reason: collision with root package name */
    private List<c> f20740d;

    /* renamed from: e, reason: collision with root package name */
    private Map<String, BitmapRegionDecoder> f20741e;

    public static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private WeakReference<b> f20743a;

        /* renamed from: b, reason: collision with root package name */
        private String f20744b;

        public a(b bVar, String str) {
            this.f20743a = new WeakReference<>(bVar);
            this.f20744b = str;
        }

        private float a(String str) {
            String str2;
            String str3;
            String str4;
            String[] strArrSplit = str.split(":");
            if (strArrSplit.length == 3) {
                str4 = strArrSplit[0];
                str2 = strArrSplit[1];
                str3 = strArrSplit[2];
            } else {
                str2 = null;
                if (strArrSplit.length == 2) {
                    String str5 = strArrSplit[0];
                    str3 = strArrSplit[1];
                    str2 = str5;
                    str4 = null;
                } else if (strArrSplit.length == 1) {
                    str3 = strArrSplit[0];
                    str4 = null;
                } else {
                    str3 = null;
                    str4 = null;
                }
            }
            float fFloatValue = str4 != null ? 0.0f + (Float.valueOf(0.0f).floatValue() * 3600.0f) : 0.0f;
            if (str2 != null) {
                fFloatValue += Float.valueOf(str2).floatValue() * 60.0f;
            }
            return str3 != null ? fFloatValue + Float.valueOf(str3).floatValue() : fFloatValue;
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            InputStream inputStreamA;
            String line;
            String line2;
            int i2;
            b bVar = this.f20743a.get();
            BufferedReader bufferedReader = null;
            try {
                try {
                    try {
                        inputStreamA = bVar.a(this.f20744b);
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (IOException e2) {
                    e = e2;
                }
                if (inputStreamA == null) {
                    return;
                }
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStreamA));
                try {
                    line = bufferedReader2.readLine();
                } catch (IOException e3) {
                    e = e3;
                    bufferedReader = bufferedReader2;
                    TXCLog.e("TXImageSprite", "load image sprite failed.", e);
                    if (bufferedReader != null) {
                        bufferedReader.close();
                        return;
                    }
                    return;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException unused) {
                        }
                    }
                    throw th;
                }
                if (line != null && line.length() != 0 && line.contains("WEBVTT")) {
                    do {
                        line2 = bufferedReader2.readLine();
                        if (line2 != null && line2.contains("-->")) {
                            String[] strArrSplit = line2.split(" --> ");
                            if (strArrSplit.length == 2) {
                                String line3 = bufferedReader2.readLine();
                                c cVar = new c();
                                cVar.f20748a = a(strArrSplit[0]);
                                cVar.f20749b = a(strArrSplit[1]);
                                cVar.f20750c = line3;
                                int iIndexOf = line3.indexOf(DictionaryFactory.SHARP);
                                if (iIndexOf != -1) {
                                    cVar.f20751d = line3.substring(0, iIndexOf);
                                }
                                int iIndexOf2 = line3.indexOf("=");
                                if (iIndexOf2 != -1 && (i2 = iIndexOf2 + 1) < line3.length()) {
                                    String[] strArrSplit2 = line3.substring(i2, line3.length()).split(",");
                                    if (strArrSplit2.length == 4) {
                                        cVar.f20752e = Integer.valueOf(strArrSplit2[0]).intValue();
                                        cVar.f20753f = Integer.valueOf(strArrSplit2[1]).intValue();
                                        cVar.f20754g = Integer.valueOf(strArrSplit2[2]).intValue();
                                        cVar.f20755h = Integer.valueOf(strArrSplit2[3]).intValue();
                                    }
                                }
                                if (bVar != null && bVar.f20740d != null) {
                                    bVar.f20740d.add(cVar);
                                }
                            }
                        }
                    } while (line2 != null);
                    bufferedReader2.close();
                    return;
                }
                TXCLog.e("TXImageSprite", "DownloadAndParseVTTFileTask : getVTT File Error!");
                if (bVar != null) {
                    bVar.b();
                }
                try {
                    bufferedReader2.close();
                } catch (IOException unused2) {
                }
            } catch (IOException unused3) {
            }
        }
    }

    /* renamed from: com.tencent.rtmp.a.b$b, reason: collision with other inner class name */
    public static class RunnableC0353b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private WeakReference<b> f20745a;

        /* renamed from: b, reason: collision with root package name */
        private String f20746b;

        /* renamed from: c, reason: collision with root package name */
        private String f20747c;

        public RunnableC0353b(b bVar, String str, String str2) {
            this.f20745a = new WeakReference<>(bVar);
            this.f20746b = str;
            this.f20747c = str2;
        }

        @Override // java.lang.Runnable
        public void run() throws IOException {
            int i2;
            b bVar = this.f20745a.get();
            if (this.f20745a == null || bVar == null) {
                return;
            }
            InputStream inputStreamA = null;
            try {
                try {
                    inputStreamA = bVar.a(this.f20747c);
                    int iLastIndexOf = this.f20747c.lastIndexOf("/");
                    if (iLastIndexOf != -1 && (i2 = iLastIndexOf + 1) < this.f20747c.length()) {
                        String str = this.f20747c;
                        String strSubstring = str.substring(i2, str.length());
                        if (bVar.f20741e != null) {
                            bVar.f20741e.put(strSubstring, BitmapRegionDecoder.newInstance(inputStreamA, true));
                        }
                    }
                    if (inputStreamA == null) {
                        return;
                    }
                } catch (IOException e2) {
                    TXCLog.e("TXImageSprite", "load bitmap from network failed.", e2);
                    if (inputStreamA == null) {
                        return;
                    }
                }
                try {
                    inputStreamA.close();
                } catch (IOException unused) {
                }
            } catch (Throwable th) {
                if (inputStreamA != null) {
                    try {
                        inputStreamA.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        }
    }

    public b() {
        ArrayList arrayList = new ArrayList();
        this.f20740d = arrayList;
        this.f20740d = Collections.synchronizedList(arrayList);
        HashMap map = new HashMap();
        this.f20741e = map;
        this.f20741e = Collections.synchronizedMap(map);
    }

    @Override // com.tencent.rtmp.a.a
    public Bitmap getThumbnail(float f2) {
        c cVarA;
        if (this.f20740d.size() == 0 || (cVarA = a(0, this.f20740d.size() - 1, f2)) == null) {
            return null;
        }
        BitmapRegionDecoder bitmapRegionDecoder = this.f20741e.get(cVarA.f20751d);
        if (bitmapRegionDecoder == null) {
            return null;
        }
        Rect rect = new Rect();
        int i2 = cVarA.f20752e;
        rect.left = i2;
        int i3 = cVarA.f20753f;
        rect.top = i3;
        rect.right = i2 + cVarA.f20754g;
        rect.bottom = i3 + cVarA.f20755h;
        return bitmapRegionDecoder.decodeRegion(rect, this.f20737a);
    }

    @Override // com.tencent.rtmp.a.a
    public void release() {
        b();
        if (this.f20738b == null || this.f20739c == null) {
            return;
        }
        if (TXCBuild.VersionInt() >= 18) {
            this.f20738b.quitSafely();
        } else {
            this.f20738b.quit();
        }
        this.f20739c = null;
        this.f20738b = null;
    }

    @Override // com.tencent.rtmp.a.a
    public void setVTTUrlAndImageUrls(String str, List<String> list) {
        if (TextUtils.isEmpty(str)) {
            TXCLog.e("TXImageSprite", "setVTTUrlAndImageUrls: vttUrl can't be null!");
            return;
        }
        b();
        a();
        this.f20739c.post(new a(this, str));
        if (list == null || list.size() == 0) {
            return;
        }
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            this.f20739c.post(new RunnableC0353b(this, str, it.next()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        if (this.f20739c != null) {
            TXCLog.i("TXImageSprite", " remove all tasks!");
            this.f20739c.removeCallbacksAndMessages(null);
            this.f20739c.post(new Runnable() { // from class: com.tencent.rtmp.a.b.1
                @Override // java.lang.Runnable
                public void run() {
                    if (b.this.f20740d != null) {
                        b.this.f20740d.clear();
                    }
                    if (b.this.f20741e != null) {
                        for (BitmapRegionDecoder bitmapRegionDecoder : b.this.f20741e.values()) {
                            if (bitmapRegionDecoder != null) {
                                bitmapRegionDecoder.recycle();
                            }
                        }
                        b.this.f20741e.clear();
                    }
                }
            });
        }
    }

    private c a(int i2, int i3, float f2) {
        int i4 = ((i3 - i2) / 2) + i2;
        if (this.f20740d.get(i4).f20748a <= f2 && this.f20740d.get(i4).f20749b > f2) {
            return this.f20740d.get(i4);
        }
        if (i2 >= i3) {
            return this.f20740d.get(i2);
        }
        if (f2 >= this.f20740d.get(i4).f20749b) {
            return a(i4 + 1, i3, f2);
        }
        if (f2 < this.f20740d.get(i4).f20748a) {
            return a(i2, i4 - 1, f2);
        }
        return null;
    }

    private void a() {
        if (this.f20738b == null) {
            HandlerThread handlerThread = new HandlerThread("SuperVodThumbnailsWorkThread");
            this.f20738b = handlerThread;
            handlerThread.start();
            this.f20739c = new Handler(this.f20738b.getLooper());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public InputStream a(String str) throws IOException {
        URLConnection uRLConnectionOpenConnection = new URL(str).openConnection();
        uRLConnectionOpenConnection.connect();
        uRLConnectionOpenConnection.getInputStream();
        uRLConnectionOpenConnection.setConnectTimeout(15000);
        uRLConnectionOpenConnection.setReadTimeout(15000);
        return uRLConnectionOpenConnection.getInputStream();
    }
}

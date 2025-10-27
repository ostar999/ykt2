package com.alibaba.sdk.android.emas;

import android.content.Context;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.alibaba.sdk.android.tbrest.utils.AppUtils;
import com.alibaba.sdk.android.tbrest.utils.LogUtil;
import com.alibaba.sdk.android.tbrest.utils.MD5Utils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class c implements Cache<d> {

    /* renamed from: a, reason: collision with root package name */
    private long f2685a;

    /* renamed from: a, reason: collision with other field name */
    private String f7a;

    /* renamed from: b, reason: collision with root package name */
    private String f2686b;
    private int diskCacheLimitCapacity;
    private int diskCacheLimitCount;

    public c(Context context, String str, String str2, String str3) {
        this.f2686b = str2;
        String str4 = str + StrPool.UNDERLINE + str2;
        str3 = TextUtils.isEmpty(str3) ? "common" : str3;
        StringBuilder sb = new StringBuilder();
        sb.append(context.getFilesDir());
        String str5 = File.separator;
        sb.append(str5);
        sb.append("emas-rest-log");
        sb.append(str5);
        sb.append(str4);
        sb.append(str5);
        sb.append(str3);
        this.f7a = sb.toString();
        File file = new File(this.f7a);
        if (file.exists()) {
            return;
        }
        file.mkdirs();
    }

    public void a(int i2, int i3, int i4) {
        this.diskCacheLimitCount = i2;
        this.diskCacheLimitCapacity = i3;
        this.f2685a = i4 * 86400000;
    }

    @Override // com.alibaba.sdk.android.emas.Cache
    public void clear() {
        File file = new File(this.f7a);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            file.delete();
            return;
        }
        List<File> listA = a(file, new ArrayList());
        LogUtil.d("DiskCacheManager num: " + listA.size());
        if (listA.size() <= 0) {
            return;
        }
        Collections.sort(listA, new Comparator<File>() { // from class: com.alibaba.sdk.android.emas.c.2
            @Override // java.util.Comparator
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public int compare(File file2, File file3) {
                if (file2.lastModified() < file3.lastModified()) {
                    return -1;
                }
                return file2.lastModified() == file3.lastModified() ? 0 : 1;
            }
        });
        long jCurrentTimeMillis = System.currentTimeMillis();
        Iterator<File> it = listA.iterator();
        int i2 = 0;
        long length = 0;
        int i3 = 0;
        while (it.hasNext()) {
            File next = it.next();
            if (jCurrentTimeMillis - next.lastModified() >= this.f2685a) {
                it.remove();
                next.delete();
            } else {
                i3++;
                length += next.length();
            }
        }
        if (i3 <= this.diskCacheLimitCount && length <= this.diskCacheLimitCapacity) {
            return;
        }
        LogUtil.d("DiskCacheManager exceed limit. start clear.");
        int i4 = (int) (this.diskCacheLimitCount * 0.8d);
        int i5 = (int) (this.diskCacheLimitCapacity * 0.8d);
        while (true) {
            if ((i3 <= i4 && length <= i5) || i2 >= listA.size()) {
                return;
            }
            File file2 = listA.get(i2);
            if (file2.delete()) {
                i3--;
                length -= file2.length();
            }
            i2++;
        }
    }

    @Override // com.alibaba.sdk.android.emas.Cache
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void add(d dVar) throws Throwable {
        if (dVar == null || dVar.a() == b.DISK_CACHE) {
            if (dVar == null) {
                LogUtil.d("DiskCacheManager add failed. data is null");
                return;
            }
            LogUtil.d("DiskCacheManager add failed. cache type: " + dVar.a().name());
            return;
        }
        List<e> listM24a = dVar.m24a();
        if (listM24a == null || listM24a.isEmpty()) {
            return;
        }
        JSONArray jSONArray = new JSONArray();
        for (int i2 = 0; i2 != listM24a.size(); i2++) {
            JSONObject jSONObjectA = listM24a.get(i2).a();
            if (jSONObjectA != null) {
                jSONArray.put(jSONObjectA);
            }
        }
        String strA = f.a(this.f2686b, jSONArray.toString());
        if (!TextUtils.isEmpty(strA)) {
            LogUtil.d("DiskCacheManager putting into cache.");
            File file = new File(this.f7a, MD5Utils.getMd5Hex(strA.getBytes(Charset.forName("UTF-8"))) + StrPool.UNDERLINE + System.currentTimeMillis() + ".log");
            if (file.exists()) {
                file.delete();
            }
            a(file, strA);
            LogUtil.d("DiskCacheManager success put into " + file.getAbsolutePath());
            return;
        }
        LogUtil.d("DiskCacheManager failed put into cache.");
    }

    @Override // com.alibaba.sdk.android.emas.Cache
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public d get() throws Throwable {
        File file = new File(this.f7a);
        if (!file.exists()) {
            return null;
        }
        if (!file.isDirectory()) {
            file.delete();
            return null;
        }
        List<File> listA = a(file, new ArrayList());
        if (listA.size() > 0) {
            Collections.sort(listA, new Comparator<File>() { // from class: com.alibaba.sdk.android.emas.c.1
                @Override // java.util.Comparator
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                public int compare(File file2, File file3) {
                    if (file2.lastModified() < file3.lastModified()) {
                        return -1;
                    }
                    return file2.lastModified() == file3.lastModified() ? 0 : 1;
                }
            });
            File file2 = listA.get(0);
            String strB = f.b(this.f2686b, a(file2));
            if (TextUtils.isEmpty(strB)) {
                return null;
            }
            try {
                JSONArray jSONArray = new JSONArray(strB);
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 != jSONArray.length(); i2++) {
                    e eVarA = e.a(jSONArray.getJSONObject(i2));
                    if (eVarA != null) {
                        arrayList.add(eVarA);
                    }
                }
                return new d(arrayList, b.DISK_CACHE, file2.getAbsolutePath());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    @Override // com.alibaba.sdk.android.emas.Cache
    /* renamed from: a, reason: collision with other method in class and merged with bridge method [inline-methods] */
    public boolean remove(d dVar) {
        if (dVar == null || dVar.a() != b.DISK_CACHE || TextUtils.isEmpty(dVar.getLocation())) {
            if (dVar == null) {
                LogUtil.d("DiskCacheManager remove failed. data is null");
            } else {
                LogUtil.d("DiskCacheManager remove failed. cache type: " + dVar.a().name());
            }
            return false;
        }
        LogUtil.d("DiskCacheManager removing. cache type: " + dVar.a().name());
        File file = new File(dVar.getLocation());
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    private List<File> a(File file, List<File> list) {
        if (file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                if (file2.isDirectory()) {
                    a(file2, list);
                } else {
                    list.add(file2);
                }
            }
        }
        return list;
    }

    /* JADX WARN: Not initialized variable reg: 3, insn: 0x0043: MOVE (r2 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:25:0x0043 */
    private String a(File file) throws Throwable {
        BufferedInputStream bufferedInputStream;
        Closeable closeable;
        StringBuilder sb = new StringBuilder();
        byte[] bArr = new byte[4096];
        Closeable closeable2 = null;
        try {
            try {
                bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                while (true) {
                    try {
                        int i2 = bufferedInputStream.read(bArr);
                        if (i2 != -1) {
                            sb.append(new String(bArr, 0, i2));
                        } else {
                            String string = sb.toString();
                            AppUtils.closeQuietly(bufferedInputStream);
                            return string;
                        }
                    } catch (FileNotFoundException e2) {
                        e = e2;
                        e.printStackTrace();
                        AppUtils.closeQuietly(bufferedInputStream);
                        return null;
                    } catch (IOException e3) {
                        e = e3;
                        e.printStackTrace();
                        AppUtils.closeQuietly(bufferedInputStream);
                        return null;
                    }
                }
            } catch (FileNotFoundException e4) {
                e = e4;
                bufferedInputStream = null;
            } catch (IOException e5) {
                e = e5;
                bufferedInputStream = null;
            } catch (Throwable th) {
                th = th;
                AppUtils.closeQuietly(closeable2);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            closeable2 = closeable;
            AppUtils.closeQuietly(closeable2);
            throw th;
        }
    }

    private void a(File file, String str) throws Throwable {
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(file));
                try {
                    bufferedOutputStream2.write(str.getBytes("utf-8"));
                    AppUtils.closeQuietly(bufferedOutputStream2);
                } catch (FileNotFoundException e2) {
                    e = e2;
                    bufferedOutputStream = bufferedOutputStream2;
                    e.printStackTrace();
                    AppUtils.closeQuietly(bufferedOutputStream);
                } catch (UnsupportedEncodingException e3) {
                    e = e3;
                    bufferedOutputStream = bufferedOutputStream2;
                    e.printStackTrace();
                    AppUtils.closeQuietly(bufferedOutputStream);
                } catch (IOException e4) {
                    e = e4;
                    bufferedOutputStream = bufferedOutputStream2;
                    e.printStackTrace();
                    AppUtils.closeQuietly(bufferedOutputStream);
                } catch (Throwable th) {
                    th = th;
                    bufferedOutputStream = bufferedOutputStream2;
                    AppUtils.closeQuietly(bufferedOutputStream);
                    throw th;
                }
            } catch (FileNotFoundException e5) {
                e = e5;
            } catch (UnsupportedEncodingException e6) {
                e = e6;
            } catch (IOException e7) {
                e = e7;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}

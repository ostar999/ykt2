package com.tencent.smtt.utils;

import android.util.Log;
import com.google.common.base.Ascii;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UnknownFormatConversionException;

/* loaded from: classes6.dex */
public class e implements Closeable {

    /* renamed from: a, reason: collision with root package name */
    static final char[] f21479a = {Ascii.MAX, 'E', 'L', 'F', 0};

    /* renamed from: b, reason: collision with root package name */
    final char[] f21480b;

    /* renamed from: c, reason: collision with root package name */
    boolean f21481c;

    /* renamed from: d, reason: collision with root package name */
    j[] f21482d;

    /* renamed from: e, reason: collision with root package name */
    l[] f21483e;

    /* renamed from: f, reason: collision with root package name */
    byte[] f21484f;

    /* renamed from: g, reason: collision with root package name */
    private final com.tencent.smtt.utils.c f21485g;

    /* renamed from: h, reason: collision with root package name */
    private final a f21486h;

    /* renamed from: i, reason: collision with root package name */
    private final k[] f21487i;

    /* renamed from: j, reason: collision with root package name */
    private byte[] f21488j;

    public static abstract class a {

        /* renamed from: a, reason: collision with root package name */
        short f21489a;

        /* renamed from: b, reason: collision with root package name */
        short f21490b;

        /* renamed from: c, reason: collision with root package name */
        int f21491c;

        /* renamed from: d, reason: collision with root package name */
        int f21492d;

        /* renamed from: e, reason: collision with root package name */
        short f21493e;

        /* renamed from: f, reason: collision with root package name */
        short f21494f;

        /* renamed from: g, reason: collision with root package name */
        short f21495g;

        /* renamed from: h, reason: collision with root package name */
        short f21496h;

        /* renamed from: i, reason: collision with root package name */
        short f21497i;

        /* renamed from: j, reason: collision with root package name */
        short f21498j;

        public abstract long a();

        public abstract long b();
    }

    public static class b extends a {

        /* renamed from: k, reason: collision with root package name */
        int f21499k;

        /* renamed from: l, reason: collision with root package name */
        int f21500l;

        /* renamed from: m, reason: collision with root package name */
        int f21501m;

        @Override // com.tencent.smtt.utils.e.a
        public long a() {
            return this.f21501m;
        }

        @Override // com.tencent.smtt.utils.e.a
        public long b() {
            return this.f21500l;
        }
    }

    public static class c extends j {

        /* renamed from: a, reason: collision with root package name */
        int f21502a;

        /* renamed from: b, reason: collision with root package name */
        int f21503b;

        /* renamed from: c, reason: collision with root package name */
        int f21504c;

        /* renamed from: d, reason: collision with root package name */
        int f21505d;

        /* renamed from: e, reason: collision with root package name */
        int f21506e;

        /* renamed from: f, reason: collision with root package name */
        int f21507f;
    }

    public static class d extends k {

        /* renamed from: a, reason: collision with root package name */
        int f21508a;

        /* renamed from: b, reason: collision with root package name */
        int f21509b;

        /* renamed from: c, reason: collision with root package name */
        int f21510c;

        /* renamed from: d, reason: collision with root package name */
        int f21511d;

        /* renamed from: e, reason: collision with root package name */
        int f21512e;

        /* renamed from: f, reason: collision with root package name */
        int f21513f;

        @Override // com.tencent.smtt.utils.e.k
        public int a() {
            return this.f21511d;
        }

        @Override // com.tencent.smtt.utils.e.k
        public long b() {
            return this.f21510c;
        }
    }

    /* renamed from: com.tencent.smtt.utils.e$e, reason: collision with other inner class name */
    public static class C0357e extends l {

        /* renamed from: a, reason: collision with root package name */
        int f21514a;

        /* renamed from: b, reason: collision with root package name */
        int f21515b;
    }

    public static class f extends a {

        /* renamed from: k, reason: collision with root package name */
        long f21516k;

        /* renamed from: l, reason: collision with root package name */
        long f21517l;

        /* renamed from: m, reason: collision with root package name */
        long f21518m;

        @Override // com.tencent.smtt.utils.e.a
        public long a() {
            return this.f21518m;
        }

        @Override // com.tencent.smtt.utils.e.a
        public long b() {
            return this.f21517l;
        }
    }

    public static class g extends j {

        /* renamed from: a, reason: collision with root package name */
        long f21519a;

        /* renamed from: b, reason: collision with root package name */
        long f21520b;

        /* renamed from: c, reason: collision with root package name */
        long f21521c;

        /* renamed from: d, reason: collision with root package name */
        long f21522d;

        /* renamed from: e, reason: collision with root package name */
        long f21523e;

        /* renamed from: f, reason: collision with root package name */
        long f21524f;
    }

    public static class h extends k {

        /* renamed from: a, reason: collision with root package name */
        long f21525a;

        /* renamed from: b, reason: collision with root package name */
        long f21526b;

        /* renamed from: c, reason: collision with root package name */
        long f21527c;

        /* renamed from: d, reason: collision with root package name */
        long f21528d;

        /* renamed from: e, reason: collision with root package name */
        long f21529e;

        /* renamed from: f, reason: collision with root package name */
        long f21530f;

        @Override // com.tencent.smtt.utils.e.k
        public int a() {
            return (int) this.f21528d;
        }

        @Override // com.tencent.smtt.utils.e.k
        public long b() {
            return this.f21527c;
        }
    }

    public static class i extends l {

        /* renamed from: a, reason: collision with root package name */
        long f21531a;

        /* renamed from: b, reason: collision with root package name */
        long f21532b;
    }

    public static abstract class j {

        /* renamed from: g, reason: collision with root package name */
        int f21533g;

        /* renamed from: h, reason: collision with root package name */
        int f21534h;
    }

    public static abstract class k {

        /* renamed from: g, reason: collision with root package name */
        int f21535g;

        /* renamed from: h, reason: collision with root package name */
        int f21536h;

        /* renamed from: i, reason: collision with root package name */
        int f21537i;

        /* renamed from: j, reason: collision with root package name */
        int f21538j;

        public abstract int a();

        public abstract long b();
    }

    public static abstract class l {

        /* renamed from: c, reason: collision with root package name */
        int f21539c;

        /* renamed from: d, reason: collision with root package name */
        char f21540d;

        /* renamed from: e, reason: collision with root package name */
        char f21541e;

        /* renamed from: f, reason: collision with root package name */
        short f21542f;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public e(File file) throws UnknownFormatConversionException, IOException {
        b bVar;
        char[] cArr = new char[16];
        this.f21480b = cArr;
        com.tencent.smtt.utils.c cVar = new com.tencent.smtt.utils.c(file);
        this.f21485g = cVar;
        cVar.a(cArr);
        if (!a()) {
            throw new UnknownFormatConversionException("Invalid elf magic: " + file);
        }
        cVar.a(e());
        boolean zD = d();
        if (zD) {
            f fVar = new f();
            fVar.f21489a = cVar.a();
            fVar.f21490b = cVar.a();
            fVar.f21491c = cVar.b();
            fVar.f21516k = cVar.c();
            fVar.f21517l = cVar.c();
            fVar.f21518m = cVar.c();
            bVar = fVar;
        } else {
            b bVar2 = new b();
            bVar2.f21489a = cVar.a();
            bVar2.f21490b = cVar.a();
            bVar2.f21491c = cVar.b();
            bVar2.f21499k = cVar.b();
            bVar2.f21500l = cVar.b();
            bVar2.f21501m = cVar.b();
            bVar = bVar2;
        }
        this.f21486h = bVar;
        a aVar = this.f21486h;
        aVar.f21492d = cVar.b();
        aVar.f21493e = cVar.a();
        aVar.f21494f = cVar.a();
        aVar.f21495g = cVar.a();
        aVar.f21496h = cVar.a();
        aVar.f21497i = cVar.a();
        aVar.f21498j = cVar.a();
        this.f21487i = new k[aVar.f21497i];
        for (int i2 = 0; i2 < aVar.f21497i; i2++) {
            cVar.a(aVar.a() + (aVar.f21496h * i2));
            if (zD) {
                h hVar = new h();
                hVar.f21535g = cVar.b();
                hVar.f21536h = cVar.b();
                hVar.f21525a = cVar.c();
                hVar.f21526b = cVar.c();
                hVar.f21527c = cVar.c();
                hVar.f21528d = cVar.c();
                hVar.f21537i = cVar.b();
                hVar.f21538j = cVar.b();
                hVar.f21529e = cVar.c();
                hVar.f21530f = cVar.c();
                this.f21487i[i2] = hVar;
            } else {
                d dVar = new d();
                dVar.f21535g = cVar.b();
                dVar.f21536h = cVar.b();
                dVar.f21508a = cVar.b();
                dVar.f21509b = cVar.b();
                dVar.f21510c = cVar.b();
                dVar.f21511d = cVar.b();
                dVar.f21537i = cVar.b();
                dVar.f21538j = cVar.b();
                dVar.f21512e = cVar.b();
                dVar.f21513f = cVar.b();
                this.f21487i[i2] = dVar;
            }
        }
        short s2 = aVar.f21498j;
        if (s2 > -1) {
            k[] kVarArr = this.f21487i;
            if (s2 < kVarArr.length) {
                k kVar = kVarArr[s2];
                if (kVar.f21536h != 3) {
                    throw new UnknownFormatConversionException("Wrong string section e_shstrndx=" + ((int) aVar.f21498j));
                }
                this.f21488j = new byte[kVar.a()];
                cVar.a(kVar.b());
                cVar.a(this.f21488j);
                if (this.f21481c) {
                    f();
                    return;
                }
                return;
            }
        }
        throw new UnknownFormatConversionException("Invalid e_shstrndx=" + ((int) aVar.f21498j));
    }

    public static boolean a(File file) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            long j2 = randomAccessFile.readInt();
            randomAccessFile.close();
            return j2 == 2135247942;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public static boolean b(File file) {
        StringBuilder sb;
        String str;
        if (!g() || !a(file)) {
            return true;
        }
        try {
            new e(file);
            return true;
        } catch (IOException e2) {
            Log.e("ELF", "checkElfFile IOException: " + e2);
            return false;
        } catch (UnknownFormatConversionException e3) {
            e = e3;
            sb = new StringBuilder();
            str = "checkElfFile UnknownFormatConversionException: ";
            sb.append(str);
            sb.append(e);
            Log.e("ELF", sb.toString());
            return true;
        } catch (Throwable th) {
            e = th;
            sb = new StringBuilder();
            str = "checkElfFile Throwable: ";
            sb.append(str);
            sb.append(e);
            Log.e("ELF", sb.toString());
            return true;
        }
    }

    private void f() throws IOException {
        a aVar = this.f21486h;
        com.tencent.smtt.utils.c cVar = this.f21485g;
        boolean zD = d();
        k kVarA = a(".dynsym");
        if (kVarA != null) {
            cVar.a(kVarA.b());
            int iA = kVarA.a() / (zD ? 24 : 16);
            this.f21483e = new l[iA];
            char[] cArr = new char[1];
            for (int i2 = 0; i2 < iA; i2++) {
                if (zD) {
                    i iVar = new i();
                    iVar.f21539c = cVar.b();
                    cVar.a(cArr);
                    iVar.f21540d = cArr[0];
                    cVar.a(cArr);
                    iVar.f21541e = cArr[0];
                    iVar.f21531a = cVar.c();
                    iVar.f21532b = cVar.c();
                    iVar.f21542f = cVar.a();
                    this.f21483e[i2] = iVar;
                } else {
                    C0357e c0357e = new C0357e();
                    c0357e.f21539c = cVar.b();
                    c0357e.f21514a = cVar.b();
                    c0357e.f21515b = cVar.b();
                    cVar.a(cArr);
                    c0357e.f21540d = cArr[0];
                    cVar.a(cArr);
                    c0357e.f21541e = cArr[0];
                    c0357e.f21542f = cVar.a();
                    this.f21483e[i2] = c0357e;
                }
            }
            k kVar = this.f21487i[kVarA.f21537i];
            cVar.a(kVar.b());
            byte[] bArr = new byte[kVar.a()];
            this.f21484f = bArr;
            cVar.a(bArr);
        }
        this.f21482d = new j[aVar.f21495g];
        for (int i3 = 0; i3 < aVar.f21495g; i3++) {
            cVar.a(aVar.b() + (aVar.f21494f * i3));
            if (zD) {
                g gVar = new g();
                gVar.f21533g = cVar.b();
                gVar.f21534h = cVar.b();
                gVar.f21519a = cVar.c();
                gVar.f21520b = cVar.c();
                gVar.f21521c = cVar.c();
                gVar.f21522d = cVar.c();
                gVar.f21523e = cVar.c();
                gVar.f21524f = cVar.c();
                this.f21482d[i3] = gVar;
            } else {
                c cVar2 = new c();
                cVar2.f21533g = cVar.b();
                cVar2.f21534h = cVar.b();
                cVar2.f21502a = cVar.b();
                cVar2.f21503b = cVar.b();
                cVar2.f21504c = cVar.b();
                cVar2.f21505d = cVar.b();
                cVar2.f21506e = cVar.b();
                cVar2.f21507f = cVar.b();
                this.f21482d[i3] = cVar2;
            }
        }
    }

    private static boolean g() {
        String property = System.getProperty("java.vm.version");
        return property != null && property.startsWith("2");
    }

    public final k a(String str) {
        for (k kVar : this.f21487i) {
            if (str.equals(a(kVar.f21535g))) {
                return kVar;
            }
        }
        return null;
    }

    public final String a(int i2) {
        if (i2 == 0) {
            return "SHN_UNDEF";
        }
        int i3 = i2;
        while (true) {
            byte[] bArr = this.f21488j;
            if (bArr[i3] == 0) {
                return new String(bArr, i2, i3 - i2);
            }
            i3++;
        }
    }

    public final boolean a() {
        return this.f21480b[0] == f21479a[0];
    }

    public final char b() {
        return this.f21480b[4];
    }

    public final char c() {
        return this.f21480b[5];
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.f21485g.close();
    }

    public final boolean d() {
        return b() == 2;
    }

    public final boolean e() {
        return c() == 1;
    }
}

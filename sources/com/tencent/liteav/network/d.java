package com.tencent.liteav.network;

import android.os.Bundle;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.structs.TXSNALPacket;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

/* loaded from: classes6.dex */
public class d implements g {

    /* renamed from: f, reason: collision with root package name */
    private a f19543f;

    /* renamed from: a, reason: collision with root package name */
    private g f19538a = null;

    /* renamed from: b, reason: collision with root package name */
    private b f19539b = null;

    /* renamed from: c, reason: collision with root package name */
    private long f19540c = 0;

    /* renamed from: d, reason: collision with root package name */
    private long f19541d = 0;

    /* renamed from: e, reason: collision with root package name */
    private b f19542e = null;

    /* renamed from: g, reason: collision with root package name */
    private long f19544g = 0;

    /* renamed from: h, reason: collision with root package name */
    private long f19545h = 0;

    public interface a {
        void onSwitchFinish(TXIStreamDownloader tXIStreamDownloader, boolean z2);
    }

    public d(a aVar) {
        this.f19543f = aVar;
    }

    public void b() {
        this.f19539b.a((g) null);
        this.f19542e.a(this);
        this.f19539b = this.f19542e;
        this.f19542e = null;
        StringBuilder sb = new StringBuilder();
        sb.append("[SwitchStream] end at ");
        sb.append(this.f19540c);
        sb.append(" stop ts ");
        sb.append(this.f19545h);
        sb.append(" start ts ");
        sb.append(this.f19544g);
        sb.append(" diff ts ");
        long j2 = this.f19545h;
        long j3 = this.f19544g;
        sb.append(j2 > j3 ? j2 - j3 : j3 - j2);
        TXCLog.w("TXCMultiStreamDownloader", sb.toString());
    }

    public long c() {
        b bVar = this.f19539b;
        if (bVar != null) {
            bVar.b(this.f19540c);
        }
        TXCLog.i("TXCMultiStreamDownloader", "[SwitchStream] stop original downloader, when video ts is" + this.f19540c);
        return this.f19540c;
    }

    @Override // com.tencent.liteav.network.g
    public void onPullAudio(com.tencent.liteav.basic.structs.a aVar) {
        g gVar = this.f19538a;
        if (gVar != null) {
            gVar.onPullAudio(aVar);
        }
    }

    @Override // com.tencent.liteav.network.g
    public void onPullNAL(TXSNALPacket tXSNALPacket) {
        long j2 = tXSNALPacket.pts;
        this.f19540c = j2;
        if (tXSNALPacket.nalType == 0) {
            this.f19541d = j2;
        }
        g gVar = this.f19538a;
        if (gVar != null) {
            gVar.onPullNAL(tXSNALPacket);
        }
    }

    public void a(g gVar) {
        this.f19538a = gVar;
    }

    public void a() {
        b bVar = this.f19539b;
        if (bVar != null) {
            bVar.b(0L);
        }
        b bVar2 = this.f19542e;
        if (bVar2 != null) {
            bVar2.b(0L);
        }
    }

    public static class b implements com.tencent.liteav.basic.b.b, g {

        /* renamed from: a, reason: collision with root package name */
        private final int f19546a = 2;

        /* renamed from: b, reason: collision with root package name */
        private long f19547b = 0;

        /* renamed from: c, reason: collision with root package name */
        private long f19548c = 0;

        /* renamed from: d, reason: collision with root package name */
        private int f19549d = 0;

        /* renamed from: e, reason: collision with root package name */
        private boolean f19550e = false;

        /* renamed from: f, reason: collision with root package name */
        private long f19551f = 0;

        /* renamed from: g, reason: collision with root package name */
        private long f19552g = 0;

        /* renamed from: h, reason: collision with root package name */
        private long f19553h = 0;

        /* renamed from: i, reason: collision with root package name */
        private ArrayList<TXSNALPacket> f19554i = new ArrayList<>();

        /* renamed from: j, reason: collision with root package name */
        private ArrayList<com.tencent.liteav.basic.structs.a> f19555j = new ArrayList<>();

        /* renamed from: k, reason: collision with root package name */
        private TXIStreamDownloader f19556k;

        /* renamed from: l, reason: collision with root package name */
        private WeakReference<d> f19557l;

        /* renamed from: m, reason: collision with root package name */
        private g f19558m;

        public b(TXIStreamDownloader tXIStreamDownloader, d dVar) {
            this.f19556k = null;
            this.f19557l = new WeakReference<>(dVar);
            this.f19556k = tXIStreamDownloader;
            tXIStreamDownloader.setListener(this);
        }

        public void a(long j2) {
            TXCLog.i("TXCMultiStreamDownloader", "[SwitchStream] start switch. current video pts:" + j2);
            this.f19549d = 0;
            this.f19547b = j2;
            this.f19556k.setListener(this);
            this.f19556k.setNotifyListener(this);
        }

        public void b(long j2) {
            TXCLog.i("TXCMultiStreamDownloader", "[SwitchStream] stop switch. pts:" + j2);
            this.f19547b = 0L;
            this.f19551f = j2;
            this.f19553h = 0L;
            this.f19552g = 0L;
            TXIStreamDownloader tXIStreamDownloader = this.f19556k;
            if (tXIStreamDownloader == null || j2 != 0) {
                return;
            }
            tXIStreamDownloader.stopDownload();
            this.f19556k = null;
        }

        @Override // com.tencent.liteav.basic.b.b
        public void onNotifyEvent(int i2, Bundle bundle) {
            if (i2 == -2301 || i2 == 3010) {
                d dVar = this.f19557l.get();
                if (dVar != null) {
                    dVar.a(this.f19556k, false);
                }
                this.f19556k.setNotifyListener(null);
            }
        }

        @Override // com.tencent.liteav.network.g
        public void onPullAudio(com.tencent.liteav.basic.structs.a aVar) {
            if (this.f19547b > 0) {
                a(aVar);
                return;
            }
            if (this.f19551f > 0) {
                b(aVar);
                return;
            }
            g gVar = this.f19558m;
            if (gVar != null) {
                gVar.onPullAudio(aVar);
            }
        }

        @Override // com.tencent.liteav.network.g
        public void onPullNAL(TXSNALPacket tXSNALPacket) {
            if (tXSNALPacket == null) {
                return;
            }
            if (this.f19547b > 0) {
                a(tXSNALPacket);
                return;
            }
            if (this.f19551f > 0) {
                b(tXSNALPacket);
                return;
            }
            g gVar = this.f19558m;
            if (gVar != null) {
                gVar.onPullNAL(tXSNALPacket);
            }
        }

        public void a(g gVar) {
            this.f19558m = gVar;
        }

        private void a(com.tencent.liteav.basic.structs.a aVar) {
            if (aVar == null) {
                return;
            }
            long j2 = aVar.f18648e;
            long j3 = this.f19548c;
            if (j2 < j3 || j2 < this.f19547b) {
                return;
            }
            g gVar = this.f19558m;
            if (gVar != null && j3 > 0 && j2 >= j3) {
                gVar.onPullAudio(aVar);
            } else {
                this.f19555j.add(aVar);
            }
        }

        private void b(com.tencent.liteav.basic.structs.a aVar) {
            if (this.f19553h > 0) {
                return;
            }
            long j2 = this.f19552g;
            if (j2 > 0 && aVar != null) {
                long j3 = aVar.f18648e;
                if (j3 >= j2) {
                    this.f19553h = j3;
                    return;
                }
            }
            g gVar = this.f19558m;
            if (gVar != null) {
                gVar.onPullAudio(aVar);
            }
        }

        private void a(TXSNALPacket tXSNALPacket) {
            d dVar = this.f19557l.get();
            if (tXSNALPacket.nalType == 0 && !this.f19550e) {
                this.f19549d++;
                TXCLog.i("TXCMultiStreamDownloader", "[SwitchStream] processing... current video ts:" + tXSNALPacket.pts + " target video ts:" + tXSNALPacket.pts + " check times:" + this.f19549d + " maxTimes:2");
                if (dVar != null && (dVar.f19541d <= tXSNALPacket.pts || this.f19549d == 2)) {
                    if (dVar.f19541d <= tXSNALPacket.pts) {
                        TXCLog.i("TXCMultiStreamDownloader", "[SwitchStream] switch video success, video data is ready.");
                    } else if (this.f19549d == 2) {
                        TXCLog.e("TXCMultiStreamDownloader", "[SwitchStream] switch video failed. all times retried. max times:2");
                    }
                    this.f19547b = dVar.c();
                    this.f19550e = true;
                }
            }
            if (this.f19550e) {
                if (dVar != null) {
                    dVar.a(tXSNALPacket.pts);
                }
                long j2 = tXSNALPacket.pts;
                if (j2 >= this.f19547b) {
                    if (tXSNALPacket.nalType == 0 && this.f19548c == 0) {
                        this.f19548c = j2;
                        TXCLog.w("TXCMultiStreamDownloader", "[SwitchStream] pre start end " + tXSNALPacket.pts + " from " + this.f19547b + " type " + tXSNALPacket.nalType);
                    }
                    if (this.f19548c > 0) {
                        if (this.f19558m != null) {
                            if (dVar != null) {
                                dVar.a(this.f19556k, true);
                            }
                            if (!this.f19555j.isEmpty()) {
                                Iterator<com.tencent.liteav.basic.structs.a> it = this.f19555j.iterator();
                                while (it.hasNext()) {
                                    com.tencent.liteav.basic.structs.a next = it.next();
                                    if (next.f18648e >= this.f19548c) {
                                        TXCLog.i("TXCMultiStreamDownloader", "[SwitchStream] pre start cache audio pts " + next.f18648e + " from " + this.f19548c);
                                        this.f19558m.onPullAudio(next);
                                    }
                                }
                                TXCLog.w("TXCMultiStreamDownloader", "[SwitchStream] pre start end audio cache  " + this.f19555j.size());
                                this.f19555j.clear();
                            }
                            if (!this.f19554i.isEmpty()) {
                                TXCLog.w("TXCMultiStreamDownloader", "[SwitchStream] pre start end video cache  " + this.f19554i.size());
                                Iterator<TXSNALPacket> it2 = this.f19554i.iterator();
                                while (it2.hasNext()) {
                                    this.f19558m.onPullNAL(it2.next());
                                }
                                this.f19554i.clear();
                            }
                            TXCLog.w("TXCMultiStreamDownloader", "[SwitchStream] pre start first pull nal " + tXSNALPacket.pts + " from " + this.f19548c + " type " + tXSNALPacket.nalType);
                            this.f19558m.onPullNAL(tXSNALPacket);
                            this.f19558m = null;
                            return;
                        }
                        TXCLog.i("TXCMultiStreamDownloader", "[SwitchStream] pre start cache video pts " + tXSNALPacket.pts + " from " + this.f19548c + " type " + tXSNALPacket.nalType);
                        this.f19554i.add(tXSNALPacket);
                    }
                }
            }
        }

        private void b(TXSNALPacket tXSNALPacket) {
            d dVar = this.f19557l.get();
            if (dVar != null) {
                dVar.b(tXSNALPacket.pts);
            }
            long j2 = tXSNALPacket.pts;
            if (j2 >= this.f19551f) {
                if (tXSNALPacket.nalType == 0) {
                    this.f19552g = j2;
                }
                if (this.f19552g <= 0) {
                    g gVar = this.f19558m;
                    if (gVar != null) {
                        gVar.onPullNAL(tXSNALPacket);
                        return;
                    }
                    return;
                }
                if (this.f19553h > 0) {
                    TXCLog.i("TXCMultiStreamDownloader", "[SwitchStream] switch finish and stop old downloader. video ts:" + this.f19552g + " audio ts:" + this.f19553h + " stop ts:" + this.f19551f);
                    if (dVar != null) {
                        dVar.b();
                    }
                    this.f19558m = null;
                    this.f19556k.setListener(null);
                    this.f19556k.stopDownload();
                    return;
                }
                TXCLog.w("TXCMultiStreamDownloader", "[SwitchStream] delay stop video end wait audio end video pts " + tXSNALPacket.pts + " from " + this.f19551f + " type " + tXSNALPacket.nalType);
                return;
            }
            g gVar2 = this.f19558m;
            if (gVar2 != null) {
                gVar2.onPullNAL(tXSNALPacket);
            }
        }
    }

    public void b(long j2) {
        this.f19545h = j2;
    }

    public void a(TXIStreamDownloader tXIStreamDownloader, TXIStreamDownloader tXIStreamDownloader2, long j2, long j3, String str) {
        TXCLog.i("TXCMultiStreamDownloader", "[SwitchStream] old downloader:" + tXIStreamDownloader.hashCode() + " new downloader:" + tXIStreamDownloader2.hashCode());
        this.f19540c = tXIStreamDownloader.getCurrentTS();
        this.f19541d = tXIStreamDownloader.getLastIFrameTS();
        b bVar = new b(tXIStreamDownloader, this);
        this.f19539b = bVar;
        bVar.a(this);
        ((TXCFLVDownloader) tXIStreamDownloader).recvData(true);
        Vector<e> vector = new Vector<>();
        vector.add(new e(str, false));
        tXIStreamDownloader2.setOriginUrl(str);
        ((TXCFLVDownloader) tXIStreamDownloader2).recvData(true);
        tXIStreamDownloader2.startDownload(vector, false, false, tXIStreamDownloader.mEnableMessage, tXIStreamDownloader.mEnableMetaData);
        b bVar2 = new b(tXIStreamDownloader2, this);
        this.f19542e = bVar2;
        bVar2.a(this.f19540c);
    }

    public void a(TXIStreamDownloader tXIStreamDownloader, boolean z2) {
        TXCLog.i("TXCMultiStreamDownloader", "[SwitchStream] switch stream finish. result:" + z2);
        a aVar = this.f19543f;
        if (aVar != null) {
            aVar.onSwitchFinish(tXIStreamDownloader, z2);
        }
    }

    public void a(long j2) {
        this.f19544g = j2;
    }
}

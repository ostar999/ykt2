package com.beizi.fusion.work.h;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import com.beizi.fusion.R;
import com.beizi.fusion.d.k;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.ah;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.i;
import com.beizi.fusion.g.n;
import com.beizi.fusion.g.u;
import com.beizi.fusion.model.AdSpacesBean;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.NativeADEventListener;
import com.qq.e.ads.nativ.NativeADMediaListener;
import com.qq.e.ads.nativ.NativeADUnifiedListener;
import com.qq.e.ads.nativ.NativeUnifiedAD;
import com.qq.e.ads.nativ.NativeUnifiedADData;
import com.qq.e.ads.nativ.widget.NativeAdContainer;
import com.qq.e.comm.managers.status.SDKStatus;
import com.qq.e.comm.pi.IBidding;
import com.qq.e.comm.util.AdError;
import com.yikaobang.yixue.R2;
import java.util.List;

/* loaded from: classes2.dex */
public class d extends com.beizi.fusion.work.h.a {
    private boolean X;
    private FrameLayout Y;
    private NativeAdContainer Z;
    private NativeUnifiedAD aa;
    private NativeUnifiedADData ab;

    public class a implements NativeADUnifiedListener {
        private a() {
        }

        public void onADLoaded(List<NativeUnifiedADData> list) {
            Log.d("BeiZis", "ShowGdtUnifiedCustomAd onADLoaded()");
            d dVar = d.this;
            dVar.P = com.beizi.fusion.f.a.ADLOAD;
            dVar.E();
            if (list == null || list.size() == 0) {
                d.this.e(-991);
                return;
            }
            d.this.ab = list.get(0);
            if (d.this.ab == null) {
                d.this.e(-991);
                return;
            }
            if (d.this.ab.getECPM() > 0) {
                d.this.a(r4.ab.getECPM());
            }
            if (u.f5253a) {
                d.this.ab.setDownloadConfirmListener(u.f5254b);
            }
            d.this.b();
            d.this.bc();
            d.this.aR();
        }

        public void onNoAD(AdError adError) {
            Log.d("BeiZis", "ShowGdtUnifiedCustomAd onNoAD: " + adError.getErrorMsg());
            d.this.b(adError.getErrorMsg(), adError.getErrorCode());
        }
    }

    public d(Context context, long j2, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar, int i2) {
        super(context, j2, buyerBean, forwardBean, eVar, i2);
    }

    @Override // com.beizi.fusion.work.a
    public void G() {
        if (!F() || this.ab == null) {
            return;
        }
        aq();
        int iA = ah.a(this.f5541e.getPriceDict(), this.ab.getECPMLevel());
        if (iA == -1 || iA == -2) {
            if (iA == -2) {
                Q();
            }
        } else {
            Log.d("BeiZisBid", "gdt realPrice = " + iA);
            a((double) iA);
        }
    }

    @Override // com.beizi.fusion.work.a
    public void aG() {
        NativeUnifiedADData nativeUnifiedADData = this.ab;
        if (nativeUnifiedADData == null || nativeUnifiedADData.getECPM() <= 0 || this.X) {
            return;
        }
        this.X = true;
        ac.a("BeiZis", "channel == GDT竞价成功");
        ac.a("BeiZis", "channel == sendWinNoticeECPM" + this.ab.getECPM());
        NativeUnifiedADData nativeUnifiedADData2 = this.ab;
        k.a((IBidding) nativeUnifiedADData2, nativeUnifiedADData2.getECPM());
    }

    @Override // com.beizi.fusion.work.h.a
    public int aL() {
        return R.layout.gdt_layout_unified_view;
    }

    @Override // com.beizi.fusion.work.h.a
    public void aM() {
        super.aM();
        this.Z = ((com.beizi.fusion.work.h.a) this).f5777o.findViewById(R.id.native_ad_container);
        this.Y = (FrameLayout) ((com.beizi.fusion.work.h.a) this).f5777o.findViewById(R.id.fl_logo);
    }

    @Override // com.beizi.fusion.work.h.a
    public void aN() {
        if (!as.a("com.qq.e.comm.managers.GDTAdSdk")) {
            z();
            this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.h.d.1
                @Override // java.lang.Runnable
                public void run() {
                    d.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                }
            }, 10L);
            Log.e("BeiZis", "GDT sdk not import , will do nothing");
            return;
        }
        A();
        k.a(this.N, this.f5544h);
        this.f5538b.s(SDKStatus.getIntegrationSDKVersion());
        aB();
        B();
        u.f5253a = !n.a(this.f5541e.getDirectDownload());
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + ((com.beizi.fusion.work.h.a) this).H);
        long j2 = ((com.beizi.fusion.work.h.a) this).H;
        if (j2 > 0) {
            this.f5549m.sendEmptyMessageDelayed(1, j2);
            return;
        }
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar == null || eVar.r() >= 1 || this.f5540d.q() == 2) {
            return;
        }
        p();
    }

    @Override // com.beizi.fusion.work.h.a
    public void aO() {
        if ("S2S".equalsIgnoreCase(this.f5541e.getBidType())) {
            this.aa = new NativeUnifiedAD(this.N, this.f5545i, new a(), aJ());
        } else {
            this.aa = new NativeUnifiedAD(this.N, this.f5545i, new a());
        }
        this.aa.loadData(1);
    }

    @Override // com.beizi.fusion.work.h.a
    public void aT() {
        bd();
    }

    @Override // com.beizi.fusion.work.h.a
    public void aW() {
        if (this.ab.getAdPatternType() != 2) {
            i.a(this.N).a(this.ab.getImgUrl(), new i.a() { // from class: com.beizi.fusion.work.h.d.4
                @Override // com.beizi.fusion.g.i.a
                public void a() {
                }

                @Override // com.beizi.fusion.g.i.a
                public void a(Bitmap bitmap) {
                    ((com.beizi.fusion.work.h.a) d.this).f5787y.setImageBitmap(bitmap);
                }
            });
        }
    }

    @Override // com.beizi.fusion.work.h.a
    public String aX() {
        return this.ab.getTitle();
    }

    @Override // com.beizi.fusion.work.h.a
    public String aY() {
        return this.ab.getDesc();
    }

    @Override // com.beizi.fusion.work.h.a
    public String aZ() {
        return this.ab.getIconUrl();
    }

    @Override // com.beizi.fusion.work.h.a
    public String ba() {
        return this.ab.getButtonText();
    }

    @Override // com.beizi.fusion.work.h.a
    public void bc() {
        if (this.aa == null) {
            e(-991);
        } else {
            aS();
        }
    }

    @Override // com.beizi.fusion.work.h.a, com.beizi.fusion.work.a
    public String g() {
        return "GDT";
    }

    @Override // com.beizi.fusion.work.h.a, com.beizi.fusion.work.a
    public void q() {
        NativeUnifiedADData nativeUnifiedADData = this.ab;
        if (nativeUnifiedADData != null) {
            nativeUnifiedADData.destroy();
        }
    }

    @Override // com.beizi.fusion.work.a
    public void f(int i2) {
        NativeUnifiedADData nativeUnifiedADData = this.ab;
        if (nativeUnifiedADData == null || nativeUnifiedADData.getECPM() <= 0 || this.X) {
            return;
        }
        this.X = true;
        ac.a("BeiZis", "channel == GDT竞价失败:" + i2);
        k.b((IBidding) this.ab, i2 != 1 ? 10001 : 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        final NativeADMediaListener nativeADMediaListener = new NativeADMediaListener() { // from class: com.beizi.fusion.work.h.d.2
            public void onVideoClicked() {
                Log.d("BeiZis", "ShowGdtUnifiedCustomAd MediaView onVideoClicked()");
            }

            public void onVideoCompleted() {
                Log.d("BeiZis", "ShowGdtUnifiedCustomAd MediaView onVideoCompleted()");
            }

            public void onVideoError(AdError adError) {
                Log.d("BeiZis", "ShowGdtUnifiedCustomAd MediaView onVideoError: " + adError.getErrorMsg());
                d.this.b(adError.getErrorMsg(), adError.getErrorCode());
            }

            public void onVideoInit() {
                Log.d("BeiZis", "ShowGdtUnifiedCustomAd MediaView onVideoInit()");
            }

            public void onVideoLoaded(int i2) {
                Log.d("BeiZis", "ShowGdtUnifiedCustomAd MediaView onVideoLoaded()");
            }

            public void onVideoLoading() {
                Log.d("BeiZis", "ShowGdtUnifiedCustomAd MediaView onVideoLoading()");
            }

            public void onVideoPause() {
                Log.d("BeiZis", "ShowGdtUnifiedCustomAd MediaView onVideoPause()");
            }

            public void onVideoReady() {
                Log.d("BeiZis", "ShowGdtUnifiedCustomAd MediaView onVideoReady()");
            }

            public void onVideoResume() {
                Log.d("BeiZis", "ShowGdtUnifiedCustomAd MediaView onVideoResume()");
            }

            public void onVideoStart() {
                Log.d("BeiZis", "ShowGdtUnifiedCustomAd MediaView onVideoStart()");
            }

            public void onVideoStop() {
                Log.d("BeiZis", "ShowGdtUnifiedCustomAd MediaView onVideoStop()");
            }
        };
        this.ab.setNativeAdEventListener(new NativeADEventListener() { // from class: com.beizi.fusion.work.h.d.3
            public void onADClicked() {
                d.this.aP();
            }

            public void onADError(AdError adError) {
                Log.d("BeiZis", "ShowGdtUnifiedCustomAd onADError: " + adError.getErrorMsg());
                d.this.b(adError.getErrorMsg(), adError.getErrorCode());
            }

            public void onADExposed() {
                d.this.aQ();
                if (d.this.ab.getAdPatternType() == 2) {
                    VideoOption.Builder builder = new VideoOption.Builder();
                    builder.setAutoPlayPolicy(1);
                    builder.setEnableDetailPage(false);
                    builder.setEnableUserControl(true);
                    d.this.ab.bindMediaView(((com.beizi.fusion.work.h.a) d.this).f5782t, builder.build(), nativeADMediaListener);
                }
            }

            public void onADStatusChanged() {
                Log.d("BeiZis", "ShowGdtUnifiedCustomAd onADStatusChanged()");
            }
        });
    }

    @Override // com.beizi.fusion.work.h.a
    public void c(boolean z2) {
        bd();
    }

    @Override // com.beizi.fusion.work.h.a
    public void a(final List<View> list) {
        this.Y.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.beizi.fusion.work.h.d.5
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                new Handler().postDelayed(new Runnable() { // from class: com.beizi.fusion.work.h.d.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (((com.beizi.fusion.work.h.a) d.this).f5779q != null) {
                            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(as.a(d.this.N, 46.0f), as.a(d.this.N, 14.0f));
                            int[] iArr = new int[2];
                            d.this.Y.getLocationInWindow(iArr);
                            int[] iArr2 = new int[2];
                            ((com.beizi.fusion.work.h.a) d.this).f5779q.getLocationInWindow(iArr2);
                            layoutParams.leftMargin = iArr[0];
                            layoutParams.topMargin = (iArr[1] - iArr2[1]) + ((com.beizi.fusion.work.h.a) d.this).f5779q.getTop();
                            NativeUnifiedADData nativeUnifiedADData = d.this.ab;
                            d dVar = d.this;
                            nativeUnifiedADData.bindAdToView(dVar.N, dVar.Z, layoutParams, list);
                        }
                    }
                }, ((com.beizi.fusion.work.a) d.this).f5547k != 2 ? 500L : 0L);
            }
        });
    }
}

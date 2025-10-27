package com.beizi.fusion.work.h;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.beizi.fusion.R;
import com.beizi.fusion.d.o;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.i;
import com.beizi.fusion.model.AdSpacesBean;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsAdVideoPlayConfig;
import com.kwad.sdk.api.KsAppDownloadListener;
import com.kwad.sdk.api.KsImage;
import com.kwad.sdk.api.KsLoadManager;
import com.kwad.sdk.api.KsNativeAd;
import com.kwad.sdk.api.KsScene;
import com.yikaobang.yixue.R2;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class e extends a {
    private ViewGroup X;
    private ImageView Y;
    private TextView Z;
    private KsNativeAd aa;

    public e(Context context, long j2, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar, int i2) {
        super(context, j2, buyerBean, forwardBean, eVar, i2);
    }

    @Override // com.beizi.fusion.work.h.a
    public int aL() {
        return R.layout.ks_layout_unified_view;
    }

    @Override // com.beizi.fusion.work.h.a
    public void aM() {
        super.aM();
        this.X = (ViewGroup) ((a) this).f5777o.findViewById(R.id.ll_ad_source_container);
        this.Y = (ImageView) ((a) this).f5777o.findViewById(R.id.ad_source_logo_iv);
        this.Z = (TextView) ((a) this).f5777o.findViewById(R.id.ad_source_logo_tv);
    }

    @Override // com.beizi.fusion.work.h.a
    public void aN() {
        if (!as.a("com.kwad.sdk.api.KsAdSDK")) {
            z();
            this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.h.e.1
                @Override // java.lang.Runnable
                public void run() {
                    e.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                }
            }, 10L);
            Log.e("BeiZis", "ks sdk not import , will do nothing");
            return;
        }
        A();
        o.a(this.N, this.f5544h);
        this.f5538b.u(KsAdSDK.getSDKVersion());
        aB();
        B();
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + ((a) this).H);
        long j2 = ((a) this).H;
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
        KsScene ksSceneBuild = new KsScene.Builder(Long.parseLong(this.f5545i)).width((int) ((a) this).I).adNum(1).build();
        KsLoadManager loadManager = KsAdSDK.getLoadManager();
        if (loadManager == null) {
            Log.d("BeiZis", "showKsUnifiedCustomAd onError:渠道广告请求对象为空");
            b("渠道广告请求异常", R2.drawable.ic_cut_success_pop_bg_night);
        } else {
            if ("S2S".equalsIgnoreCase(this.f5541e.getBidType())) {
                ksSceneBuild.setBidResponse(aJ());
            }
            loadManager.loadNativeAd(ksSceneBuild, new KsLoadManager.NativeAdListener() { // from class: com.beizi.fusion.work.h.e.2
                public void onError(int i2, String str) {
                    Log.d("BeiZis", "showKsUnifiedCustomAd Callback --> onError: code = " + i2 + " ，message= " + str);
                    e.this.b(str, i2);
                }

                public void onNativeAdLoad(@Nullable List<KsNativeAd> list) {
                    Log.d("BeiZis", "showKsUnifiedCustomAd Callback --> onFeedAdLoad()");
                    e eVar = e.this;
                    eVar.P = com.beizi.fusion.f.a.ADLOAD;
                    eVar.E();
                    if (list == null || list.size() == 0) {
                        e.this.e(-991);
                        return;
                    }
                    e.this.aa = list.get(0);
                    if (e.this.aa == null) {
                        e.this.e(-991);
                        return;
                    }
                    e.this.a(r4.aa.getECPM());
                    e.this.b();
                    e.this.bc();
                    e.this.aR();
                }
            });
        }
    }

    @Override // com.beizi.fusion.work.h.a
    public void aT() {
        bd();
    }

    @Override // com.beizi.fusion.work.h.a
    public void aV() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(12, -1);
        layoutParams.addRule(11, -1);
        layoutParams.rightMargin = (((a) this).f5781s.getLayoutParams().width - ((a) this).f5782t.getLayoutParams().width) / 2;
        layoutParams.bottomMargin = ((a) this).f5781s.getLayoutParams().height - ((a) this).f5782t.getLayoutParams().height;
        this.X.setLayoutParams(layoutParams);
        if (!TextUtils.isEmpty(this.aa.getAdSourceLogoUrl(0))) {
            i.a(this.N).a(this.aa.getAdSourceLogoUrl(1), new i.a() { // from class: com.beizi.fusion.work.h.e.6
                @Override // com.beizi.fusion.g.i.a
                public void a() {
                }

                @Override // com.beizi.fusion.g.i.a
                public void a(Bitmap bitmap) {
                    e.this.Y.setImageBitmap(bitmap);
                }
            });
        }
        if (TextUtils.isEmpty(this.aa.getAdSource())) {
            return;
        }
        this.Z.setText(this.aa.getAdSource());
    }

    @Override // com.beizi.fusion.work.h.a
    public void aW() {
        KsImage ksImage;
        if (this.aa.getMaterialType() != 2 && this.aa.getMaterialType() != 3) {
            if (this.aa.getMaterialType() == 1) {
                View videoView = this.aa.getVideoView(this.N, new KsAdVideoPlayConfig.Builder().build());
                ((a) this).f5782t.removeAllViews();
                ((a) this).f5782t.addView(videoView);
                return;
            }
            return;
        }
        if (this.aa.getImageList() == null || this.aa.getImageList().isEmpty() || (ksImage = (KsImage) this.aa.getImageList().get(0)) == null || !ksImage.isValid()) {
            return;
        }
        i.a(this.N).a(ksImage.getImageUrl(), new i.a() { // from class: com.beizi.fusion.work.h.e.5
            @Override // com.beizi.fusion.g.i.a
            public void a() {
            }

            @Override // com.beizi.fusion.g.i.a
            public void a(Bitmap bitmap) {
                ((a) e.this).f5787y.setImageBitmap(bitmap);
            }
        });
    }

    @Override // com.beizi.fusion.work.h.a
    public String aX() {
        KsNativeAd ksNativeAd = this.aa;
        return ksNativeAd != null ? ksNativeAd.getInteractionType() == 1 ? !TextUtils.isEmpty(this.aa.getAppName()) ? this.aa.getAppName() : "" : !TextUtils.isEmpty(this.aa.getProductName()) ? this.aa.getProductName() : "" : "";
    }

    @Override // com.beizi.fusion.work.h.a
    public String aY() {
        return this.aa.getAdDescription();
    }

    @Override // com.beizi.fusion.work.h.a
    public String aZ() {
        return this.aa.getAppIconUrl();
    }

    @Override // com.beizi.fusion.work.h.a
    public String ba() {
        return this.aa.getActionDescription();
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
        return "KUAISHOU";
    }

    @Override // com.beizi.fusion.work.h.a, com.beizi.fusion.work.a
    public void q() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        this.aa.setVideoPlayListener(new KsNativeAd.VideoPlayListener() { // from class: com.beizi.fusion.work.h.e.3
            public void onVideoPlayComplete() {
                Log.d("BeiZis", "showKsUnifiedCustomAd Callback --> onVideoPlayComplete()");
            }

            public void onVideoPlayError(int i2, int i3) {
                Log.d("BeiZis", "showKsUnifiedCustomAd Callback --> onVideoPlayError()");
            }

            public void onVideoPlayPause() {
                Log.d("BeiZis", "showKsUnifiedCustomAd Callback --> onVideoPlayPause()");
            }

            public void onVideoPlayReady() {
                Log.d("BeiZis", "showKsUnifiedCustomAd Callback --> onVideoPlayReady()");
            }

            public void onVideoPlayResume() {
                Log.d("BeiZis", "showKsUnifiedCustomAd Callback --> onVideoPlayResume()");
            }

            public void onVideoPlayStart() {
                Log.d("BeiZis", "showKsUnifiedCustomAd Callback --> onVideoPlayStart()");
            }
        });
        if (this.aa.getInteractionType() == 1) {
            this.aa.setDownloadListener(new KsAppDownloadListener() { // from class: com.beizi.fusion.work.h.e.4
                public void onDownloadFailed() {
                    Log.d("BeiZis", "showKsUnifiedCustomAd Callback --> onDownloadFailed()");
                    String actionDescription = e.this.aa.getActionDescription();
                    if (TextUtils.isEmpty(actionDescription)) {
                        return;
                    }
                    ((a) e.this).F.setText(actionDescription);
                }

                public void onDownloadFinished() {
                    ((a) e.this).F.setText("立即安装");
                    Log.d("BeiZis", "showKsUnifiedCustomAd Callback --> onDownloadFinished()");
                }

                public void onDownloadStarted() {
                    Log.d("BeiZis", "showKsUnifiedCustomAd Callback --> onDownloadStarted()");
                    ((a) e.this).F.setText("开始下载");
                }

                public void onIdle() {
                    Log.d("BeiZis", "showKsUnifiedCustomAd Callback --> onIdle()");
                    String actionDescription = e.this.aa.getActionDescription();
                    if (TextUtils.isEmpty(actionDescription)) {
                        return;
                    }
                    ((a) e.this).F.setText(actionDescription);
                }

                public void onInstalled() {
                    ((a) e.this).F.setText("立即打开");
                    Log.d("BeiZis", "showKsUnifiedCustomAd Callback --> onInstalled()");
                }

                public void onProgressUpdate(int i2) {
                    Log.d("BeiZis", "showKsUnifiedCustomAd Callback --> onProgressUpdate()");
                    ((a) e.this).F.setText(String.format("%s/100", Integer.valueOf(i2)));
                }
            });
        }
    }

    @Override // com.beizi.fusion.work.h.a
    public void c(boolean z2) {
        bd();
    }

    @Override // com.beizi.fusion.work.h.a
    public void a(List<View> list) {
        int i2 = this.aa.getInteractionType() == 1 ? 2 : 1;
        HashMap map = new HashMap();
        if (list != null && list.size() > 0) {
            Iterator<View> it = list.iterator();
            while (it.hasNext()) {
                map.put(it.next(), Integer.valueOf(i2));
            }
        }
        this.aa.registerViewForInteraction((Activity) this.N, ((a) this).f5781s, map, new KsNativeAd.AdInteractionListener() { // from class: com.beizi.fusion.work.h.e.7
            public boolean handleDownloadDialog(DialogInterface.OnClickListener onClickListener) {
                return false;
            }

            public void onAdClicked(View view, KsNativeAd ksNativeAd) {
                e.this.aP();
            }

            public void onAdShow(KsNativeAd ksNativeAd) {
                e.this.aQ();
            }

            public void onDownloadTipsDialogDismiss() {
                Log.d("BeiZis", "showKsUnifiedCustomAd Callback --> onDownloadTipsDialogDismiss()");
            }

            public void onDownloadTipsDialogShow() {
                Log.d("BeiZis", "showKsUnifiedCustomAd Callback --> onDownloadTipsDialogShow()");
            }
        });
    }
}

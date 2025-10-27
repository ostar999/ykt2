package com.beizi.fusion.work.f;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import com.beizi.ad.internal.utilities.ImageManager;
import com.beizi.fusion.R;
import com.beizi.fusion.d.o;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsAdVideoPlayConfig;
import com.kwad.sdk.api.KsAppDownloadListener;
import com.kwad.sdk.api.KsImage;
import com.kwad.sdk.api.KsLoadManager;
import com.kwad.sdk.api.KsNativeAd;
import com.kwad.sdk.api.KsScene;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class e extends a {

    /* renamed from: w, reason: collision with root package name */
    private KsNativeAd f5715w;

    public e(Context context, long j2, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar, int i2) {
        super(context, j2, buyerBean, forwardBean, eVar, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aZ() {
        this.f5715w.setVideoPlayListener(new KsNativeAd.VideoPlayListener() { // from class: com.beizi.fusion.work.f.e.3
            public void onVideoPlayComplete() {
                Log.d("BeiZis", "showKsNativeUnifiedAd Callback --> onVideoPlayComplete()");
            }

            public void onVideoPlayError(int i2, int i3) {
                Log.d("BeiZis", "showKsNativeUnifiedAd Callback --> onVideoPlayError()");
            }

            public void onVideoPlayPause() {
                Log.d("BeiZis", "showKsNativeUnifiedAd Callback --> onVideoPlayPause()");
            }

            public void onVideoPlayReady() {
                Log.d("BeiZis", "showKsNativeUnifiedAd Callback --> onVideoPlayReady()");
            }

            public void onVideoPlayResume() {
                Log.d("BeiZis", "showKsNativeUnifiedAd Callback --> onVideoPlayResume()");
            }

            public void onVideoPlayStart() {
                Log.d("BeiZis", "showKsNativeUnifiedAd Callback --> onVideoPlayStart()");
            }
        });
        if (this.f5715w.getInteractionType() == 1) {
            this.f5715w.setDownloadListener(new KsAppDownloadListener() { // from class: com.beizi.fusion.work.f.e.4
                public void onDownloadFailed() {
                    Log.d("BeiZis", "showKsNativeUnifiedAd Callback --> onDownloadFailed()");
                }

                public void onDownloadFinished() {
                    Log.d("BeiZis", "showKsNativeUnifiedAd Callback --> onDownloadFinished()");
                }

                public void onDownloadStarted() {
                    Log.d("BeiZis", "showKsNativeUnifiedAd Callback --> onDownloadStarted()");
                }

                public void onIdle() {
                    Log.d("BeiZis", "showKsNativeUnifiedAd Callback --> onIdle()");
                }

                public void onInstalled() {
                    Log.d("BeiZis", "showKsNativeUnifiedAd Callback --> onInstalled()");
                }

                public void onProgressUpdate(int i2) {
                    Log.d("BeiZis", "showKsNativeUnifiedAd Callback --> onProgressUpdate()");
                }
            });
        }
    }

    @Override // com.beizi.fusion.work.f.a
    public void aL() {
        if (!as.a("com.kwad.sdk.api.KsAdSDK")) {
            z();
            this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.f.e.1
                @Override // java.lang.Runnable
                public void run() {
                    e.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                }
            }, 10L);
            Log.e("BeiZis", "ks sdk not import , will do nothing");
            return;
        }
        A();
        o.a(((a) this).f5684n, this.f5544h);
        this.f5538b.u(KsAdSDK.getSDKVersion());
        aB();
        B();
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + ((a) this).f5686p);
        long j2 = ((a) this).f5686p;
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

    @Override // com.beizi.fusion.work.f.a
    public void aM() {
        KsScene ksSceneBuild = new KsScene.Builder(Long.parseLong(this.f5545i)).width((int) ((a) this).f5687q).adNum(1).build();
        ((a) this).f5690t = new FrameLayout(((a) this).f5684n);
        KsLoadManager loadManager = KsAdSDK.getLoadManager();
        if (loadManager == null) {
            Log.d("BeiZis", "showKsNativeUnifiedAd onError:渠道广告请求对象为空");
            b("渠道广告请求异常", R2.drawable.ic_cut_success_pop_bg_night);
        } else {
            if ("S2S".equalsIgnoreCase(this.f5541e.getBidType())) {
                ksSceneBuild.setBidResponse(aJ());
            }
            loadManager.loadNativeAd(ksSceneBuild, new KsLoadManager.NativeAdListener() { // from class: com.beizi.fusion.work.f.e.2
                public void onError(int i2, String str) {
                    Log.d("BeiZis", "showKsNativeUnifiedAd Callback --> onError: code = " + i2 + " ，message= " + str);
                    e.this.b(str, i2);
                }

                public void onNativeAdLoad(@Nullable List<KsNativeAd> list) {
                    Log.d("BeiZis", "showKsNativeUnifiedAd Callback --> onFeedAdLoad()");
                    e eVar = e.this;
                    ((a) eVar).f5685o = com.beizi.fusion.f.a.ADLOAD;
                    eVar.E();
                    if (list == null || list.size() == 0) {
                        e.this.e(-991);
                        return;
                    }
                    e.this.f5715w = list.get(0);
                    if (e.this.f5715w == null) {
                        e.this.e(-991);
                        return;
                    }
                    e.this.a(r4.f5715w.getECPM());
                    e.this.aZ();
                    e.this.aY();
                }
            });
        }
    }

    @Override // com.beizi.fusion.work.f.a
    public String aN() {
        KsNativeAd ksNativeAd = this.f5715w;
        if (ksNativeAd == null) {
            return null;
        }
        if (ksNativeAd.getInteractionType() == 1) {
            if (TextUtils.isEmpty(this.f5715w.getAppName())) {
                return null;
            }
            return this.f5715w.getAppName();
        }
        if (TextUtils.isEmpty(this.f5715w.getProductName())) {
            return null;
        }
        return this.f5715w.getProductName();
    }

    @Override // com.beizi.fusion.work.f.a
    public String aO() {
        KsNativeAd ksNativeAd = this.f5715w;
        if (ksNativeAd == null || TextUtils.isEmpty(ksNativeAd.getAdDescription())) {
            return null;
        }
        return this.f5715w.getAdDescription();
    }

    @Override // com.beizi.fusion.work.f.a
    public String aP() {
        KsNativeAd ksNativeAd = this.f5715w;
        if (ksNativeAd == null || TextUtils.isEmpty(ksNativeAd.getAppIconUrl())) {
            return null;
        }
        return this.f5715w.getAppIconUrl();
    }

    @Override // com.beizi.fusion.work.f.a
    public String aQ() {
        KsNativeAd ksNativeAd = this.f5715w;
        if (ksNativeAd != null) {
            if ((ksNativeAd.getImageList() != null) & (this.f5715w.getImageList().size() > 0)) {
                List imageList = this.f5715w.getImageList();
                String imageUrl = ((KsImage) imageList.get(0)).getImageUrl();
                if (!TextUtils.isEmpty(imageUrl) && ((KsImage) imageList.get(0)).isValid()) {
                    return imageUrl;
                }
            }
        }
        return null;
    }

    @Override // com.beizi.fusion.work.f.a
    public List<String> aR() {
        KsNativeAd ksNativeAd = this.f5715w;
        if (ksNativeAd != null) {
            if ((ksNativeAd.getImageList() != null) & (this.f5715w.getImageList().size() > 0)) {
                ArrayList arrayList = new ArrayList();
                List imageList = this.f5715w.getImageList();
                for (int i2 = 0; i2 < imageList.size(); i2++) {
                    String imageUrl = ((KsImage) imageList.get(i2)).getImageUrl();
                    if (!TextUtils.isEmpty(imageUrl) && ((KsImage) imageList.get(i2)).isValid()) {
                        arrayList.add(imageUrl);
                    }
                }
                if (arrayList.size() > 0) {
                    return arrayList;
                }
            }
        }
        return null;
    }

    @Override // com.beizi.fusion.work.f.a
    public int aS() {
        KsNativeAd ksNativeAd = this.f5715w;
        if (ksNativeAd == null) {
            return 0;
        }
        int materialType = ksNativeAd.getMaterialType();
        if (materialType != 1) {
            return (materialType == 2 || materialType == 3) ? 1 : 0;
        }
        return 2;
    }

    @Override // com.beizi.fusion.work.f.a
    public String aT() {
        KsNativeAd ksNativeAd = this.f5715w;
        if (ksNativeAd == null || TextUtils.isEmpty(ksNativeAd.getActionDescription())) {
            return null;
        }
        return this.f5715w.getActionDescription();
    }

    @Override // com.beizi.fusion.work.f.a
    public boolean aU() {
        KsNativeAd ksNativeAd = this.f5715w;
        return ksNativeAd != null && ksNativeAd.getMaterialType() == 1;
    }

    @Override // com.beizi.fusion.work.f.a
    public ViewGroup aV() {
        return ((a) this).f5690t;
    }

    @Override // com.beizi.fusion.work.f.a
    public View aW() {
        if (!aU()) {
            return null;
        }
        return this.f5715w.getVideoView(((a) this).f5684n, new KsAdVideoPlayConfig.Builder().build());
    }

    @Override // com.beizi.fusion.work.f.a
    public void aX() {
        FrameLayout frameLayout = new FrameLayout(((a) this).f5684n);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2, 17));
        frameLayout.setBackgroundDrawable(ContextCompat.getDrawable(((a) this).f5684n, R.drawable.button_count_down_background));
        ImageView imageView = new ImageView(((a) this).f5684n);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(26, 26, 19);
        layoutParams.setMargins(0, 0, 0, 0);
        imageView.setLayoutParams(layoutParams);
        if (TextUtils.isEmpty(this.f5715w.getAdSourceLogoUrl(0))) {
            imageView.setImageResource(R.drawable.ks_ad_logo_normal_mark);
        } else {
            ImageManager.with(((a) this).f5684n).load(this.f5715w.getAdSourceLogoUrl(0)).into(imageView);
        }
        frameLayout.addView(imageView);
        if (!TextUtils.isEmpty(this.f5715w.getAdSource())) {
            AppCompatTextView appCompatTextView = new AppCompatTextView(((a) this).f5684n);
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-2, -2, 19);
            layoutParams2.setMargins(32, 0, 0, 0);
            appCompatTextView.setLayoutParams(layoutParams2);
            appCompatTextView.setTextColor(ContextCompat.getColorStateList(((a) this).f5684n, R.color.button_text_selector));
            appCompatTextView.setTextSize(2, 12.0f);
            appCompatTextView.setGravity(17);
            appCompatTextView.setText(this.f5715w.getAdSource());
            frameLayout.addView(appCompatTextView);
        }
        ((a) this).f5690t.addView(frameLayout, new FrameLayout.LayoutParams(-2, -2, 85));
    }

    @Override // com.beizi.fusion.work.f.a, com.beizi.fusion.work.a
    public String g() {
        return "KUAISHOU";
    }

    @Override // com.beizi.fusion.work.a
    public String l() {
        if (this.f5715w == null) {
            return null;
        }
        return this.f5715w.getECPM() + "";
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
    }

    @Override // com.beizi.fusion.work.f.a
    public void a(List<View> list) {
        aX();
        int i2 = this.f5715w.getInteractionType() == 1 ? 2 : 1;
        HashMap map = new HashMap();
        if (list != null && list.size() > 0) {
            Iterator<View> it = list.iterator();
            while (it.hasNext()) {
                map.put(it.next(), Integer.valueOf(i2));
            }
        }
        this.f5715w.registerViewForInteraction((Activity) ((a) this).f5684n, ((a) this).f5690t, map, new KsNativeAd.AdInteractionListener() { // from class: com.beizi.fusion.work.f.e.5
            public boolean handleDownloadDialog(DialogInterface.OnClickListener onClickListener) {
                return false;
            }

            public void onAdClicked(View view, KsNativeAd ksNativeAd) {
                e.this.b();
            }

            public void onAdShow(KsNativeAd ksNativeAd) {
                e.this.ag();
            }

            public void onDownloadTipsDialogDismiss() {
                Log.d("BeiZis", "showKsNativeUnifiedAd Callback --> onDownloadTipsDialogDismiss()");
            }

            public void onDownloadTipsDialogShow() {
                Log.d("BeiZis", "showKsNativeUnifiedAd Callback --> onDownloadTipsDialogShow()");
            }
        });
    }
}

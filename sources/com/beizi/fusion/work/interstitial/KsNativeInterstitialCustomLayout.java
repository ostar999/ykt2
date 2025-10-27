package com.beizi.fusion.work.interstitial;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.beizi.fusion.R;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.ai;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.i;
import com.beizi.fusion.g.m;
import com.beizi.fusion.model.AdSpacesBean;
import com.kwad.sdk.api.KsAdVideoPlayConfig;
import com.kwad.sdk.api.KsImage;
import com.kwad.sdk.api.KsNativeAd;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class KsNativeInterstitialCustomLayout extends FrameLayout {

    /* renamed from: a, reason: collision with root package name */
    Context f5837a;

    /* renamed from: b, reason: collision with root package name */
    private ArrayList<View> f5838b;

    /* renamed from: c, reason: collision with root package name */
    private a f5839c;

    public interface a {
        void b();

        void b_();
    }

    public KsNativeInterstitialCustomLayout(@NonNull Context context) {
        this(context, null);
    }

    public boolean onBindData(KsNativeAd ksNativeAd, float f2, float f3, AdSpacesBean.RenderViewBean renderViewBean, KsNativeAd.AdInteractionListener adInteractionListener, KsNativeAd.VideoPlayListener videoPlayListener, View.OnClickListener onClickListener) {
        if (ksNativeAd == null) {
            setVisibility(8);
            return false;
        }
        int iA = as.a(getContext(), f2);
        int iA2 = f3 > 0.0f ? as.a(getContext(), f3) : -2;
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.beizi_media_view);
        FrameLayout frameLayout2 = (FrameLayout) findViewById(R.id.beizi_ad_cover_image_container);
        int materialType = ksNativeAd.getMaterialType();
        if (materialType == 1) {
            int videoWidth = ksNativeAd.getVideoWidth();
            int videoHeight = ksNativeAd.getVideoHeight();
            ac.c("BeiZis", "interstitial videoWidth = " + videoWidth + ",videoHeight = " + videoHeight);
            if (videoWidth != 0 && videoHeight != 0) {
                iA2 = Math.round((videoHeight / videoWidth) * iA);
            }
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(iA, iA2));
            frameLayout2.setVisibility(8);
            frameLayout.setVisibility(0);
            ksNativeAd.setVideoPlayListener(videoPlayListener);
            View videoView = ksNativeAd.getVideoView(getContext(), new KsAdVideoPlayConfig.Builder().videoSoundEnable(false).dataFlowAutoStart(false).build());
            if (videoView != null && videoView.getParent() == null) {
                frameLayout.removeAllViews();
                frameLayout.addView(videoView);
            }
        } else {
            if (materialType != 2 && materialType != 3) {
                return false;
            }
            if (ksNativeAd.getImageList() != null && !ksNativeAd.getImageList().isEmpty()) {
                ac.c("BeiZis", "interstitial imageList size = " + ksNativeAd.getImageList().size());
                KsImage ksImage = (KsImage) ksNativeAd.getImageList().get(0);
                if (ksImage != null && ksImage.isValid()) {
                    int width = ksImage.getWidth();
                    int height = ksImage.getHeight();
                    ac.c("BeiZis", "interstitial imageWidth = " + width + ",imageHeight = " + height);
                    if (width != 0 && height != 0) {
                        float f4 = height / width;
                        if (f4 < 0.375f || f4 > 2.667f) {
                            ac.c("BeiZis", "interstitial aspectRatio = " + f4 + " not proper , return fail ! ");
                            return false;
                        }
                        iA2 = Math.round(f4 * iA);
                    }
                    frameLayout2.setVisibility(0);
                    frameLayout.setVisibility(8);
                    ImageView imageView = new ImageView(getContext());
                    i.a(getContext()).a(ksImage.getImageUrl()).a(imageView);
                    frameLayout2.addView(imageView, new FrameLayout.LayoutParams(iA, iA2));
                }
            }
        }
        ac.c("BeiZis", "interstitial adWidth = " + iA + ",adHeight = " + iA2);
        ((FrameLayout) findViewById(R.id.beizi_root_container)).setLayoutParams(new FrameLayout.LayoutParams(iA, iA2));
        ImageView imageView2 = (ImageView) findViewById(R.id.beizi_close);
        imageView2.setOnClickListener(onClickListener);
        a(imageView2, renderViewBean, iA, iA2);
        this.f5838b.add(frameLayout2);
        ac.c("BeiZis", "interstitial mContext instanceof Activity ? " + (this.f5837a instanceof Activity));
        Context context = this.f5837a;
        if (context instanceof Activity) {
            ksNativeAd.registerViewForInteraction((Activity) context, this, this.f5838b, adInteractionListener);
        }
        return true;
    }

    @Override // android.view.View
    public void onWindowVisibilityChanged(int i2) {
        a aVar;
        ac.c("BeiZis", "interstitial visibility = " + i2);
        if (i2 == 0 && (aVar = this.f5839c) != null) {
            aVar.b_();
        }
        super.onWindowVisibilityChanged(i2);
    }

    public void setViewInteractionListener(a aVar) {
        this.f5839c = aVar;
    }

    public KsNativeInterstitialCustomLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private void a() {
        Context context = getContext();
        this.f5837a = context;
        LayoutInflater.from(context).inflate(R.layout.ks_native_interstitial_custom_view, this);
        this.f5838b = new ArrayList<>();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        float fRandom = (int) ((Math.random() * 10.0d) + 1.0d);
        m.a(this, getPivotX() - fRandom, getPivotY() - fRandom);
    }

    public KsNativeInterstitialCustomLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a();
    }

    private void a(View view, AdSpacesBean.RenderViewBean renderViewBean, int i2, int i3) {
        FrameLayout.LayoutParams layoutParams;
        int iA;
        if (renderViewBean == null) {
            return;
        }
        int[] iArrA = a(renderViewBean, i2, i3);
        ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
        if (ai.a(renderViewBean.getClickNum())) {
            view.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.fusion.work.interstitial.KsNativeInterstitialCustomLayout.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    KsNativeInterstitialCustomLayout.this.b();
                    KsNativeInterstitialCustomLayout.this.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.interstitial.KsNativeInterstitialCustomLayout.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (KsNativeInterstitialCustomLayout.this.f5839c != null) {
                                KsNativeInterstitialCustomLayout.this.f5839c.b();
                            }
                        }
                    }, 500L);
                }
            });
        }
        if (layoutParams2 instanceof FrameLayout.LayoutParams) {
            layoutParams = (FrameLayout.LayoutParams) layoutParams2;
            String location = renderViewBean.getLocation();
            location.hashCode();
            switch (location) {
                case "rightdown":
                    layoutParams.gravity = 85;
                    layoutParams.rightMargin = iArrA[0];
                    layoutParams.bottomMargin = iArrA[1];
                    break;
                case "leftup":
                    layoutParams.gravity = 51;
                    layoutParams.leftMargin = iArrA[0];
                    layoutParams.topMargin = iArrA[1];
                    break;
                case "rightup":
                    layoutParams.gravity = 53;
                    layoutParams.rightMargin = iArrA[0];
                    layoutParams.topMargin = iArrA[1];
                    break;
                case "leftdown":
                    layoutParams.gravity = 83;
                    layoutParams.leftMargin = iArrA[0];
                    layoutParams.bottomMargin = iArrA[1];
                    break;
            }
            String size = renderViewBean.getSize();
            if (size != null) {
                if (size.endsWith("%")) {
                    iA = (i2 * Integer.parseInt(size.substring(0, size.indexOf("%")))) / 100;
                } else {
                    iA = as.a(getContext(), Integer.parseInt(size));
                }
                layoutParams.width = iA;
                layoutParams.height = iA;
            }
            ac.c("BeiZis", "interstitial params = " + layoutParams);
        }
    }

    private int[] a(AdSpacesBean.RenderViewBean renderViewBean, int i2, int i3) {
        String borderWidth = renderViewBean.getBorderWidth();
        String borderHeight = renderViewBean.getBorderHeight();
        int[] iArr = new int[2];
        if (borderWidth.endsWith("%")) {
            iArr[0] = (i2 * Integer.parseInt(borderWidth.substring(0, borderWidth.indexOf("%")))) / 100;
        } else {
            iArr[0] = as.a(getContext(), Integer.parseInt(borderWidth));
        }
        if (borderHeight.endsWith("%")) {
            iArr[1] = (i3 * Integer.parseInt(borderHeight.substring(0, borderHeight.indexOf("%")))) / 100;
        } else {
            iArr[1] = as.a(getContext(), Integer.parseInt(borderHeight));
        }
        return iArr;
    }
}

package com.beizi.fusion.work.interstitial;

import android.content.Context;
import android.text.TextUtils;
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
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.MediaView;
import com.qq.e.ads.nativ.NativeADEventListener;
import com.qq.e.ads.nativ.NativeADMediaListener;
import com.qq.e.ads.nativ.NativeUnifiedADData;
import com.qq.e.ads.nativ.widget.NativeAdContainer;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class GdtNativeInterstitialCustomLayout extends FrameLayout {

    /* renamed from: a, reason: collision with root package name */
    private ArrayList<View> f5833a;

    /* renamed from: b, reason: collision with root package name */
    private a f5834b;

    public interface a {
        void a_();

        void b();
    }

    public GdtNativeInterstitialCustomLayout(@NonNull Context context) {
        this(context, null);
    }

    public boolean onBindData(NativeUnifiedADData nativeUnifiedADData, float f2, float f3, AdSpacesBean.RenderViewBean renderViewBean, NativeADEventListener nativeADEventListener, NativeADMediaListener nativeADMediaListener, View.OnClickListener onClickListener) {
        if (nativeUnifiedADData == null) {
            setVisibility(8);
            return false;
        }
        int iA = as.a(getContext(), f2);
        int iA2 = f3 > 0.0f ? as.a(getContext(), f3) : -2;
        ac.c("BeiZis", "interstitial getPictureWidth = " + nativeUnifiedADData.getPictureWidth() + ",getPictureHeight = " + nativeUnifiedADData.getPictureHeight());
        int pictureWidth = nativeUnifiedADData.getPictureWidth();
        int pictureHeight = nativeUnifiedADData.getPictureHeight();
        if (pictureWidth != 0 && pictureHeight != 0) {
            float f4 = pictureHeight / pictureWidth;
            if (f4 < 0.375f || f4 > 2.667f) {
                ac.c("BeiZis", "interstitial aspectRatio = " + f4 + " not proper , return fail ! ");
                return false;
            }
            iA2 = Math.round(f4 * iA);
        }
        ac.c("BeiZis", "interstitial adWidth = " + iA + ",adHeight = " + iA2);
        NativeAdContainer nativeAdContainerFindViewById = findViewById(R.id.beizi_root_container);
        nativeAdContainerFindViewById.setLayoutParams(new FrameLayout.LayoutParams(iA, iA2));
        ac.c("BeiZis", "interstitial imageWidth = " + pictureWidth + ",imageHeight = " + pictureHeight);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.beizi_ad_cover_image_container);
        ImageView imageView = new ImageView(getContext());
        String imgUrl = nativeUnifiedADData.getImgUrl();
        if (!TextUtils.isEmpty(imgUrl)) {
            i.a(getContext()).a(imgUrl).a(imageView);
        }
        frameLayout.addView(imageView, new FrameLayout.LayoutParams(iA, iA2));
        ImageView imageView2 = (ImageView) findViewById(R.id.beizi_close);
        imageView2.setOnClickListener(onClickListener);
        a(imageView2, renderViewBean, iA, iA2);
        this.f5833a.add(frameLayout);
        nativeUnifiedADData.bindAdToView(getContext(), nativeAdContainerFindViewById, (FrameLayout.LayoutParams) null, this.f5833a);
        if (nativeUnifiedADData.getAdPatternType() == 2) {
            MediaView mediaViewFindViewById = findViewById(R.id.beizi_media_view);
            mediaViewFindViewById.setLayoutParams(new FrameLayout.LayoutParams(iA, iA2));
            VideoOption.Builder builder = new VideoOption.Builder();
            builder.setAutoPlayPolicy(1);
            builder.setAutoPlayMuted(true);
            builder.setDetailPageMuted(true);
            builder.setNeedCoverImage(true);
            builder.setNeedProgressBar(true);
            builder.setEnableDetailPage(true);
            builder.setEnableUserControl(false);
            nativeUnifiedADData.bindMediaView(mediaViewFindViewById, builder.build(), nativeADMediaListener);
            frameLayout.setVisibility(8);
            mediaViewFindViewById.setVisibility(0);
        }
        nativeUnifiedADData.setNativeAdEventListener(nativeADEventListener);
        return true;
    }

    @Override // android.view.View
    public void onWindowVisibilityChanged(int i2) {
        a aVar;
        ac.c("BeiZis", "interstitial visibility = " + i2);
        if (i2 == 0 && (aVar = this.f5834b) != null) {
            aVar.a_();
        }
        super.onWindowVisibilityChanged(i2);
    }

    public void setViewInteractionListener(a aVar) {
        this.f5834b = aVar;
    }

    public GdtNativeInterstitialCustomLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private void a() {
        LayoutInflater.from(getContext()).inflate(R.layout.gdt_native_interstitial_custom_view, this);
        this.f5833a = new ArrayList<>();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        float fRandom = (int) ((Math.random() * 10.0d) + 1.0d);
        m.a(this, getPivotX() - fRandom, getPivotY() - fRandom);
    }

    public GdtNativeInterstitialCustomLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
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
            view.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.fusion.work.interstitial.GdtNativeInterstitialCustomLayout.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    GdtNativeInterstitialCustomLayout.this.b();
                    GdtNativeInterstitialCustomLayout.this.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.interstitial.GdtNativeInterstitialCustomLayout.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (GdtNativeInterstitialCustomLayout.this.f5834b != null) {
                                GdtNativeInterstitialCustomLayout.this.f5834b.b();
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

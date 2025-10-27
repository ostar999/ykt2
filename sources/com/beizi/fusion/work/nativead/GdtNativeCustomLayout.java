package com.beizi.fusion.work.nativead;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.beizi.fusion.R;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.i;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.MediaView;
import com.qq.e.ads.nativ.NativeADEventListener;
import com.qq.e.ads.nativ.NativeADMediaListener;
import com.qq.e.ads.nativ.NativeUnifiedADData;
import com.qq.e.ads.nativ.widget.NativeAdContainer;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class GdtNativeCustomLayout extends FrameLayout {

    /* renamed from: a, reason: collision with root package name */
    private ArrayList<View> f5908a;

    public GdtNativeCustomLayout(@NonNull Context context) {
        this(context, null);
    }

    private void a() {
        LayoutInflater.from(getContext()).inflate(R.layout.beizi_native_custom_view, this);
        this.f5908a = new ArrayList<>();
    }

    public void onBindData(NativeUnifiedADData nativeUnifiedADData, float f2, float f3, NativeADEventListener nativeADEventListener, NativeADMediaListener nativeADMediaListener, View.OnClickListener onClickListener) {
        if (nativeUnifiedADData == null) {
            setVisibility(8);
            return;
        }
        int iA = as.a(getContext(), f2);
        int iA2 = f3 > 0.0f ? as.a(getContext(), f3) : -2;
        NativeAdContainer nativeAdContainerFindViewById = findViewById(R.id.beizi_root_container);
        nativeAdContainerFindViewById.setLayoutParams(new FrameLayout.LayoutParams(iA, iA2));
        int pictureWidth = ((nativeUnifiedADData.getPictureWidth() != 0 ? nativeUnifiedADData.getPictureWidth() : 1280) * iA2) / (nativeUnifiedADData.getPictureHeight() != 0 ? nativeUnifiedADData.getPictureHeight() : 720);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.beizi_ad_cover_image_container);
        ImageView imageView = new ImageView(getContext());
        String imgUrl = nativeUnifiedADData.getImgUrl();
        if (!TextUtils.isEmpty(imgUrl)) {
            i.a(getContext()).a(imgUrl).a(imageView);
        }
        frameLayout.addView(imageView, new FrameLayout.LayoutParams(pictureWidth, iA2));
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.beizi_right_view);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        int i2 = iA - pictureWidth;
        layoutParams.width = i2;
        layoutParams.height = iA2;
        linearLayout.setLayoutParams(layoutParams);
        ImageView imageView2 = (ImageView) findViewById(R.id.beizi_ad_logo);
        String iconUrl = nativeUnifiedADData.getIconUrl();
        if (!TextUtils.isEmpty(iconUrl)) {
            i.a(getContext()).a(iconUrl).a(imageView2);
        }
        TextView textView = (TextView) findViewById(R.id.beizi_ad_action);
        ((ImageView) findViewById(R.id.beizi_close)).setOnClickListener(onClickListener);
        this.f5908a.add(frameLayout);
        this.f5908a.add(linearLayout);
        this.f5908a.add(imageView2);
        this.f5908a.add(textView);
        nativeUnifiedADData.bindAdToView(getContext(), nativeAdContainerFindViewById, (FrameLayout.LayoutParams) null, this.f5908a);
        if (nativeUnifiedADData.getAdPatternType() == 2) {
            MediaView mediaViewFindViewById = findViewById(R.id.beizi_media_view);
            mediaViewFindViewById.setLayoutParams(new FrameLayout.LayoutParams(pictureWidth, iA2));
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams2.width = i2;
            layoutParams2.height = iA2;
            linearLayout.setLayoutParams(layoutParams2);
            VideoOption.Builder builder = new VideoOption.Builder();
            builder.setAutoPlayPolicy(1);
            builder.setAutoPlayMuted(true);
            builder.setDetailPageMuted(false);
            builder.setNeedCoverImage(true);
            builder.setNeedProgressBar(true);
            builder.setEnableDetailPage(true);
            builder.setEnableUserControl(false);
            nativeUnifiedADData.bindMediaView(mediaViewFindViewById, builder.build(), nativeADMediaListener);
            frameLayout.setVisibility(8);
            mediaViewFindViewById.setVisibility(0);
        }
        nativeUnifiedADData.setNativeAdEventListener(nativeADEventListener);
        a(textView, nativeUnifiedADData);
    }

    public GdtNativeCustomLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GdtNativeCustomLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a();
    }

    private void a(TextView textView, NativeUnifiedADData nativeUnifiedADData) {
        if (nativeUnifiedADData == null) {
            textView.setText("浏览");
            return;
        }
        if (!nativeUnifiedADData.isAppAd()) {
            textView.setText("浏览");
            return;
        }
        int appStatus = nativeUnifiedADData.getAppStatus();
        if (appStatus == 0) {
            textView.setText("下载");
            return;
        }
        if (appStatus == 1) {
            textView.setText("启动");
            return;
        }
        if (appStatus == 2) {
            textView.setText("更新");
            return;
        }
        if (appStatus == 4) {
            textView.setText(nativeUnifiedADData.getProgress() + "%");
            return;
        }
        if (appStatus == 8) {
            textView.setText("安装");
        } else if (appStatus != 16) {
            textView.setText("浏览");
        } else {
            textView.setText("下载失败，重新下载");
        }
    }
}

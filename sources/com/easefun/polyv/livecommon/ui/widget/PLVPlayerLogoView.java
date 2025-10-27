package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVImageLoader;
import com.easefun.polyv.livecommon.ui.widget.webview.PLVSimpleUrlWebViewActivity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVPlayerLogoView extends FrameLayout {
    private final List<LogoParam> logoParams;
    private OnClickLogoListener onClickLogoListener;

    public static class LogoParam {
        private String logoHref;
        private int resId;
        private String resUrl;
        private float width = 80.0f;
        private float height = 100.0f;
        private int pos = 1;
        private int alpha = 100;
        private float offsetX = 0.0f;
        private float offsetY = 0.0f;

        public int getAlpha() {
            return this.alpha;
        }

        public float getHeight() {
            return this.height;
        }

        public String getLogoHref() {
            return this.logoHref;
        }

        public float getOffsetX() {
            return this.offsetX;
        }

        public float getOffsetY() {
            return this.offsetY;
        }

        public int getPos() {
            return this.pos;
        }

        public int getResId() {
            return this.resId;
        }

        public String getResUrl() {
            return this.resUrl;
        }

        public float getWidth() {
            return this.width;
        }

        public LogoParam setAlpha(int alpha) {
            this.alpha = alpha;
            return this;
        }

        public LogoParam setHeight(float height) {
            this.height = height;
            return this;
        }

        public LogoParam setLogoHref(String logoHref) {
            this.logoHref = logoHref;
            return this;
        }

        public LogoParam setOffsetX(float offsetX) {
            this.offsetX = offsetX;
            return this;
        }

        public LogoParam setOffsetY(float offsetY) {
            this.offsetY = offsetY;
            return this;
        }

        public LogoParam setPos(int pos) {
            this.pos = pos;
            return this;
        }

        public LogoParam setResId(int resId) {
            this.resId = resId;
            return this;
        }

        public LogoParam setResUrl(String resUrl) {
            this.resUrl = resUrl;
            return this;
        }

        public LogoParam setWidth(float width) {
            this.width = width;
            return this;
        }
    }

    public static class OnClickLogoListener {
        public void onClickLogo(final View v2, @NonNull final LogoParam logoParam) {
            if (TextUtils.isEmpty(logoParam.getLogoHref())) {
                return;
            }
            PLVSimpleUrlWebViewActivity.start(v2.getContext(), logoParam.getLogoHref());
        }
    }

    public interface OnGetBitmapSizeCallback {
        void onGetBitmapSize(int bitmapWidth, int bitmapHeight);
    }

    public PLVPlayerLogoView(@NonNull Context context) {
        this(context, null);
    }

    private void getBitmapSize(final LogoParam logoParam, final OnGetBitmapSizeCallback cb) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        if (logoParam.resId == 0) {
            Glide.with(getContext()).asBitmap().load(logoParam.resUrl).into((RequestBuilder<Bitmap>) new SimpleTarget<Bitmap>() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVPlayerLogoView.3
                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                    onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
                }

                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    cb.onGetBitmapSize(resource.getWidth(), resource.getHeight());
                }
            });
        } else {
            BitmapFactory.decodeResource(getResources(), logoParam.resId, options);
            cb.onGetBitmapSize(options.outWidth, options.outHeight);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public FrameLayout.LayoutParams makeLPForLogo(LogoParam logoParam, float logoWidth, float logoHeight) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) logoWidth, (int) logoHeight);
        if (logoParam.pos == 1) {
            layoutParams.gravity = 51;
            if (logoParam.offsetX <= 1.0f) {
                layoutParams.leftMargin = (int) (getWidth() * logoParam.offsetX);
            } else {
                layoutParams.leftMargin = (int) logoParam.offsetX;
            }
            if (logoParam.offsetY <= 1.0f) {
                layoutParams.topMargin = (int) (getHeight() * logoParam.offsetY);
            } else {
                layoutParams.topMargin = (int) logoParam.offsetY;
            }
        } else if (logoParam.pos == 2) {
            layoutParams.gravity = 53;
            if (logoParam.offsetX <= 1.0f) {
                layoutParams.rightMargin = (int) (getWidth() * logoParam.offsetX);
            } else {
                layoutParams.rightMargin = (int) logoParam.offsetX;
            }
            if (logoParam.offsetY <= 1.0f) {
                layoutParams.topMargin = (int) (getHeight() * logoParam.offsetY);
            } else {
                layoutParams.topMargin = (int) logoParam.offsetY;
            }
        } else if (logoParam.pos == 3) {
            layoutParams.gravity = 83;
            if (logoParam.offsetX <= 1.0f) {
                layoutParams.leftMargin = (int) (getWidth() * logoParam.offsetX);
            } else {
                layoutParams.leftMargin = (int) logoParam.offsetX;
            }
            if (logoParam.offsetY <= 1.0f) {
                layoutParams.bottomMargin = (int) (getHeight() * logoParam.offsetY);
            } else {
                layoutParams.bottomMargin = (int) logoParam.offsetY;
            }
        } else if (logoParam.pos == 4) {
            layoutParams.gravity = 85;
            if (logoParam.offsetX <= 1.0f) {
                layoutParams.rightMargin = (int) (getWidth() * logoParam.offsetX);
            } else {
                layoutParams.rightMargin = (int) logoParam.offsetX;
            }
            if (logoParam.offsetY <= 1.0f) {
                layoutParams.bottomMargin = (int) (getHeight() * logoParam.offsetY);
            } else {
                layoutParams.bottomMargin = (int) logoParam.offsetY;
            }
        }
        return layoutParams;
    }

    public void addLogo(final LogoParam logoParam) {
        addLogo(logoParam, false);
    }

    public LogoParam getParamZero() {
        if (this.logoParams.size() >= 1) {
            return this.logoParams.get(0);
        }
        return null;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int left, int top2, int right, int bottom) {
        super.onLayout(changed, left, top2, right, bottom);
        if (changed) {
            post(new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVPlayerLogoView.4
                @Override // java.lang.Runnable
                public void run() {
                    if (PLVPlayerLogoView.this.logoParams.isEmpty()) {
                        return;
                    }
                    PLVPlayerLogoView.super.removeAllViews();
                    Iterator it = PLVPlayerLogoView.this.logoParams.iterator();
                    while (it.hasNext()) {
                        PLVPlayerLogoView.this.addLogo((LogoParam) it.next());
                    }
                }
            });
        }
    }

    public void removeAllLogo() {
        removeAllViews();
        this.logoParams.clear();
    }

    @Override // android.view.ViewGroup
    public void removeAllViews() {
        super.removeAllViews();
        this.logoParams.clear();
    }

    public void setOnClickLogoListener(OnClickLogoListener onClickLogoListener) {
        this.onClickLogoListener = onClickLogoListener;
    }

    public PLVPlayerLogoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addLogo(final LogoParam logoParam, boolean posted) {
        if (logoParam == null) {
            return;
        }
        if (!posted && ((logoParam.width < 1.0f || logoParam.height < 1.0f) && (getWidth() == 0 || getHeight() == 0))) {
            post(new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVPlayerLogoView.1
                @Override // java.lang.Runnable
                public void run() {
                    PLVPlayerLogoView.this.addLogo(logoParam, true);
                }
            });
        } else {
            final ImageView imageView = new ImageView(getContext());
            getBitmapSize(logoParam, new OnGetBitmapSizeCallback() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVPlayerLogoView.2
                @Override // com.easefun.polyv.livecommon.ui.widget.PLVPlayerLogoView.OnGetBitmapSizeCallback
                public void onGetBitmapSize(int bitmapWidth, int bitmapHeight) {
                    float width = logoParam.width;
                    float height = logoParam.height;
                    if (width <= 1.0f) {
                        width *= PLVPlayerLogoView.this.getWidth();
                    }
                    if (height <= 1.0f) {
                        height *= PLVPlayerLogoView.this.getHeight();
                    }
                    float f2 = bitmapWidth;
                    float f3 = bitmapHeight;
                    float f4 = f2 / f3;
                    float f5 = width / height;
                    if (f4 != f5) {
                        if (f4 > f5) {
                            height = (f3 * width) / f2;
                        } else {
                            width = (f2 * height) / f3;
                        }
                    }
                    if (logoParam.pos == 0) {
                        imageView.setVisibility(8);
                    }
                    imageView.setAlpha(logoParam.alpha / 100.0f);
                    imageView.setLayoutParams(PLVPlayerLogoView.this.makeLPForLogo(logoParam, width, height));
                    imageView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVPlayerLogoView.2.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View v2) {
                            if (PLVPlayerLogoView.this.onClickLogoListener != null) {
                                PLVPlayerLogoView.this.onClickLogoListener.onClickLogo(v2, logoParam);
                            }
                        }
                    });
                    PLVPlayerLogoView.this.addView(imageView);
                    if (logoParam.resId != 0) {
                        PLVImageLoader.getInstance().loadImage(PLVPlayerLogoView.this.getContext(), logoParam.resId, imageView);
                    } else {
                        PLVImageLoader.getInstance().loadImage(PLVPlayerLogoView.this.getContext(), logoParam.resUrl, imageView);
                    }
                    if (PLVPlayerLogoView.this.logoParams.contains(logoParam)) {
                        return;
                    }
                    PLVPlayerLogoView.this.logoParams.add(logoParam);
                }
            });
        }
    }

    public PLVPlayerLogoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.logoParams = new ArrayList();
        this.onClickLogoListener = new OnClickLogoListener();
    }
}

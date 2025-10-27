package com.beizi.ad.internal.nativead;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.beizi.ad.NativeAdResponse;
import com.beizi.ad.R;
import com.beizi.ad.internal.g;
import com.beizi.ad.internal.network.ServerResponse;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.ImageManager;
import com.beizi.ad.internal.utilities.ViewUtil;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes2.dex */
public class NativeAdUtil {
    public static boolean a(NativeAdResponse nativeAdResponse) {
        if (nativeAdResponse != null && !nativeAdResponse.hasExpired()) {
            return true;
        }
        HaoboLog.d(HaoboLog.nativeLogTag, "NativeAdResponse is not valid");
        return false;
    }

    public static FrameLayout addADLogo(View view, NativeAdResponse nativeAdResponse) {
        int i2 = R.drawable.button_close_background;
        if (view.getTag(i2) != null && (view.getTag(i2) instanceof FrameLayout)) {
            ViewUtil.removeChildFromParent((FrameLayout) view.getTag(i2));
            return (FrameLayout) view.getTag(i2);
        }
        view.getContext();
        ViewUtil.removeChildFromParent(view);
        ServerResponse.AdLogoInfo adUrl = nativeAdResponse.getAdUrl();
        ServerResponse.AdLogoInfo adLogoInfo = nativeAdResponse.getlogoUrl();
        FrameLayout frameLayoutCreateAdImageView = ViewUtil.createAdImageView(view.getContext(), adUrl);
        FrameLayout frameLayoutCreateLogoImageView = ViewUtil.createLogoImageView(view.getContext(), adLogoInfo);
        frameLayoutCreateAdImageView.setVisibility(0);
        frameLayoutCreateLogoImageView.setVisibility(0);
        FrameLayout frameLayout = new FrameLayout(view.getContext());
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2, 17));
        frameLayout.addView(view, new FrameLayout.LayoutParams(-1, -1, 17));
        frameLayout.addView(frameLayoutCreateAdImageView, new FrameLayout.LayoutParams(85, 42, 83));
        frameLayout.addView(frameLayoutCreateLogoImageView, new FrameLayout.LayoutParams(42, 42, 85));
        view.setTag(i2, frameLayout);
        return frameLayout;
    }

    public static FrameLayout getCustomRenderView(Context context, Bitmap bitmap, NativeAdResponse nativeAdResponse) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setVisibility(0);
        imageView.setImageBitmap(bitmap);
        return addADLogo(imageView, nativeAdResponse);
    }

    public static void getOneAdBitmap(NativeAdResponse nativeAdResponse, final ImageManager.BitmapLoadedListener bitmapLoadedListener) {
        boolean z2;
        ArrayList<String> imageUrls = nativeAdResponse.getImageUrls();
        final Bitmap[] bitmapArr = new Bitmap[3];
        if (imageUrls == null || imageUrls.size() <= 0) {
            return;
        }
        final ServerResponse.AdLogoInfo adUrl = nativeAdResponse.getAdUrl();
        int i2 = adUrl.getType() == ServerResponse.AdLogoInfo.TYPE_PIC ? 1 : 0;
        int i3 = i2;
        final ServerResponse.AdLogoInfo adLogoInfo = nativeAdResponse.getlogoUrl();
        if (adLogoInfo.getType() == ServerResponse.AdLogoInfo.TYPE_PIC) {
            i2++;
            z2 = true;
        } else {
            z2 = false;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(i2 + 1);
        new Thread(new Runnable() { // from class: com.beizi.ad.internal.nativead.NativeAdUtil.4
            @Override // java.lang.Runnable
            public void run() throws InterruptedException {
                try {
                    countDownLatch.await();
                    Handler handler = new Handler(Looper.getMainLooper());
                    Bitmap bitmap = bitmapArr[0];
                    if (bitmap == null) {
                        handler.post(new Runnable() { // from class: com.beizi.ad.internal.nativead.NativeAdUtil.4.1
                            @Override // java.lang.Runnable
                            public void run() {
                                bitmapLoadedListener.onBitmapLoadFailed();
                            }
                        });
                        return;
                    }
                    final Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmapArr[0].getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmapCreateBitmap);
                    Paint paint = new Paint();
                    paint.setTextSize(ViewUtil.dip2px(g.a().e(), 14.0f));
                    canvas.drawBitmap(bitmapArr[0], 0.0f, 0.0f, paint);
                    Bitmap bitmap2 = bitmapArr[1];
                    if (bitmap2 != null) {
                        canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap2, 85, 42, false), 0.0f, canvas.getHeight() - r5.getHeight(), paint);
                    } else if (adUrl.getType() == ServerResponse.AdLogoInfo.TYPE_TEXT) {
                        Rect rect = new Rect();
                        paint.getTextBounds(adUrl.getAdurl(), 0, adUrl.getAdurl().length(), rect);
                        rect.width();
                        canvas.drawText(adUrl.getAdurl(), 0.0f, (canvas.getHeight() - rect.height()) - 3, paint);
                    }
                    Bitmap bitmap3 = bitmapArr[2];
                    if (bitmap3 != null) {
                        canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap3, 42, 42, false), canvas.getWidth() - r2.getWidth(), canvas.getHeight() - r2.getHeight(), paint);
                    } else if (adLogoInfo.getType() == ServerResponse.AdLogoInfo.TYPE_TEXT) {
                        paint.getTextBounds(adLogoInfo.getAdurl(), 0, adLogoInfo.getAdurl().length(), new Rect());
                        canvas.drawText(adUrl.getAdurl(), (canvas.getWidth() - r5.width()) - 3, (canvas.getHeight() - r5.height()) - 3, paint);
                    }
                    handler.post(new Runnable() { // from class: com.beizi.ad.internal.nativead.NativeAdUtil.4.2
                        @Override // java.lang.Runnable
                        public void run() {
                            bitmapLoadedListener.onBitmapLoaded(bitmapCreateBitmap);
                        }
                    });
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }).start();
        ImageManager.with(null).getBitmap(imageUrls.get(0), new ImageManager.BitmapLoadedListener() { // from class: com.beizi.ad.internal.nativead.NativeAdUtil.5
            @Override // com.beizi.ad.internal.utilities.ImageManager.BitmapLoadedListener
            public void onBitmapLoadFailed() {
                countDownLatch.countDown();
            }

            @Override // com.beizi.ad.internal.utilities.ImageManager.BitmapLoadedListener
            public void onBitmapLoaded(Bitmap bitmap) {
                countDownLatch.countDown();
                bitmapArr[0] = bitmap;
            }
        });
        if (i3 != 0) {
            ImageManager.with(null).getBitmap(adUrl.getAdurl(), new ImageManager.BitmapLoadedListener() { // from class: com.beizi.ad.internal.nativead.NativeAdUtil.6
                @Override // com.beizi.ad.internal.utilities.ImageManager.BitmapLoadedListener
                public void onBitmapLoadFailed() {
                    countDownLatch.countDown();
                }

                @Override // com.beizi.ad.internal.utilities.ImageManager.BitmapLoadedListener
                public void onBitmapLoaded(Bitmap bitmap) {
                    countDownLatch.countDown();
                    bitmapArr[1] = bitmap;
                }
            });
        }
        if (z2) {
            ImageManager.with(null).getBitmap(adLogoInfo.getAdurl(), new ImageManager.BitmapLoadedListener() { // from class: com.beizi.ad.internal.nativead.NativeAdUtil.7
                @Override // com.beizi.ad.internal.utilities.ImageManager.BitmapLoadedListener
                public void onBitmapLoadFailed() {
                    countDownLatch.countDown();
                }

                @Override // com.beizi.ad.internal.utilities.ImageManager.BitmapLoadedListener
                public void onBitmapLoaded(Bitmap bitmap) {
                    countDownLatch.countDown();
                    bitmapArr[2] = bitmap;
                }
            });
        }
    }

    public static void handleClick(NativeAdResponse nativeAdResponse, View view, String str, String str2, String str3, String str4, int i2) {
        nativeAdResponse.handleClick(view.getContext(), view, str, str2, str3, str4, i2);
    }

    public static void regesterClickListener(NativeAdResponse nativeAdResponse, View view, NativeAdEventListener nativeAdEventListener) {
        nativeAdResponse.regesterClickListener(view, nativeAdEventListener);
    }

    public static boolean registerShow(NativeAdResponse nativeAdResponse, View view) {
        return nativeAdResponse.regesterShow(view);
    }

    public static void registerTracking(final NativeAdResponse nativeAdResponse, final View view, final NativeAdEventListener nativeAdEventListener) {
        if (a(nativeAdResponse)) {
            if (view == null) {
                HaoboLog.e(HaoboLog.nativeLogTag, "View is not valid for registering");
            } else {
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.beizi.ad.internal.nativead.NativeAdUtil.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (!nativeAdResponse.registerView(view, nativeAdEventListener)) {
                            HaoboLog.e(HaoboLog.nativeLogTag, "failed at registering the View");
                        } else {
                            view.setTag(R.string.native_tag, new WeakReference(nativeAdResponse));
                        }
                    }
                });
            }
        }
    }

    @Deprecated
    public static void unRegisterTracking(final View view) {
        if (view == null) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.beizi.ad.internal.nativead.NativeAdUtil.3
            @Override // java.lang.Runnable
            public void run() {
                View view2 = view;
                int i2 = R.string.native_tag;
                if (view2.getTag(i2) != null) {
                    NativeAdResponse nativeAdResponse = (NativeAdResponse) ((WeakReference) view.getTag(i2)).get();
                    if (nativeAdResponse != null) {
                        HaoboLog.d(HaoboLog.nativeLogTag, "Unregister nativead ad response, assets will be destroyed.");
                        nativeAdResponse.unregisterViews();
                    }
                    view.setTag(i2, null);
                }
            }
        });
    }

    public static boolean registerShow(NativeAdResponse nativeAdResponse, View view, NativeAdShownListener nativeAdShownListener) {
        return nativeAdResponse.regesterShow(view, nativeAdShownListener);
    }

    @Deprecated
    public static void registerTracking(final NativeAdResponse nativeAdResponse, final View view, final List<View> list, final NativeAdEventListener nativeAdEventListener) {
        if (a(nativeAdResponse)) {
            if (view != null && list != null && !list.isEmpty()) {
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.beizi.ad.internal.nativead.NativeAdUtil.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (!nativeAdResponse.registerViewList(view, list, nativeAdEventListener)) {
                            HaoboLog.e(HaoboLog.nativeLogTag, "failed at registering the View");
                            return;
                        }
                        view.setTag(R.string.native_tag, new WeakReference(nativeAdResponse));
                        HaoboLog.d(HaoboLog.nativeLogTag, "View has been registered.");
                    }
                });
            } else {
                HaoboLog.e(HaoboLog.nativeLogTag, "Views are not valid for registering");
            }
        }
    }
}

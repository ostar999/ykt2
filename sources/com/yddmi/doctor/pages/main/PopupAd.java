package com.yddmi.doctor.pages.main;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.catchpig.mvvm.ext.ImageViewExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.mvvm.utils.DataStoreUtils;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.lxj.xpopup.core.CenterPopupView;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.entity.result.HomeMsg;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001:\u0001!B\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0016\u001a\u00020\u0017H\u0014J\b\u0010\u0018\u001a\u00020\u0019H\u0014J\"\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\n2\b\b\u0002\u0010\u001c\u001a\u00020\u000e2\b\b\u0002\u0010\u001d\u001a\u00020\u000eJ\u000e\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020\fJ\b\u0010 \u001a\u00020\u0019H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/yddmi/doctor/pages/main/PopupAd;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "adImgv", "Landroid/widget/ImageView;", "leftV", "Landroid/view/View;", "mData", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "mListener", "Lcom/yddmi/doctor/pages/main/PopupAd$OnPopupAdClickListener;", "mOneDayShow", "", "mRecommend", "oneImgv", "recommendCl", "Landroidx/constraintlayout/widget/ConstraintLayout;", "rightV", "twoImgv", "xImgv", "getImplLayoutId", "", "onCreate", "", PLVRxEncryptDataFunction.SET_DATA_METHOD, "data", "oneDayShow", "recommend", "setOnPopupAdClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "viewDetailsShow", "OnPopupAdClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupAd extends CenterPopupView {
    private ImageView adImgv;
    private View leftV;

    @Nullable
    private HomeMsg mData;

    @Nullable
    private OnPopupAdClickListener mListener;
    private boolean mOneDayShow;
    private boolean mRecommend;
    private ImageView oneImgv;
    private ConstraintLayout recommendCl;
    private View rightV;
    private ImageView twoImgv;
    private ImageView xImgv;

    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H&¨\u0006\b"}, d2 = {"Lcom/yddmi/doctor/pages/main/PopupAd$OnPopupAdClickListener;", "", "onClick", "", "view", "Landroid/view/View;", "recommend", "", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupAdClickListener {

        @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
        public static final class DefaultImpls {
            public static /* synthetic */ void onClick$default(OnPopupAdClickListener onPopupAdClickListener, View view, boolean z2, int i2, Object obj) {
                if (obj != null) {
                    throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onClick");
                }
                if ((i2 & 2) != 0) {
                    z2 = false;
                }
                onPopupAdClickListener.onClick(view, z2);
            }
        }

        void onClick(@Nullable View view, boolean recommend);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupAd(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public static /* synthetic */ PopupAd setData$default(PopupAd popupAd, HomeMsg homeMsg, boolean z2, boolean z3, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        if ((i2 & 4) != 0) {
            z3 = false;
        }
        return popupAd.setData(homeMsg, z2, z3);
    }

    private final void viewDetailsShow() throws InterruptedException {
        ImageView imageView;
        ImageView imageView2;
        View view;
        View view2;
        ImageView imageView3;
        String coverUrl;
        ImageView imageView4;
        ImageView imageView5;
        ImageView imageView6;
        String coverUrl2;
        View viewFindViewById = findViewById(R.id.xImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.xImgv)");
        ImageView imageView7 = (ImageView) viewFindViewById;
        this.xImgv = imageView7;
        ConstraintLayout constraintLayout = null;
        if (imageView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("xImgv");
            imageView = null;
        } else {
            imageView = imageView7;
        }
        ViewExtKt.setDebounceClickListener$default(imageView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                PopupAd.viewDetailsShow$lambda$0(this.f25943c, view3);
            }
        }, 0L, 2, null);
        View viewFindViewById2 = findViewById(R.id.recommendCl);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.recommendCl)");
        this.recommendCl = (ConstraintLayout) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.oneImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.oneImgv)");
        this.oneImgv = (ImageView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.twoImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.twoImgv)");
        this.twoImgv = (ImageView) viewFindViewById4;
        ImageView imageView8 = this.adImgv;
        if (imageView8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adImgv");
            imageView2 = null;
        } else {
            imageView2 = imageView8;
        }
        ViewExtKt.setDebounceClickListener$default(imageView2, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.p
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                PopupAd.viewDetailsShow$lambda$1(this.f25944c, view3);
            }
        }, 0L, 2, null);
        View view3 = this.leftV;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("leftV");
            view = null;
        } else {
            view = view3;
        }
        ViewExtKt.setDebounceClickListener$default(view, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view4) {
                PopupAd.viewDetailsShow$lambda$2(this.f25945c, view4);
            }
        }, 0L, 2, null);
        View view4 = this.rightV;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rightV");
            view2 = null;
        } else {
            view2 = view4;
        }
        ViewExtKt.setDebounceClickListener$default(view2, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.r
            @Override // android.view.View.OnClickListener
            public final void onClick(View view5) {
                PopupAd.viewDetailsShow$lambda$3(this.f25946c, view5);
            }
        }, 0L, 2, null);
        HomeMsg homeMsg = this.mData;
        if (homeMsg != null) {
            Intrinsics.checkNotNull(homeMsg);
            String str = "";
            if (homeMsg.getMFullImg()) {
                ImageView imageView9 = this.adImgv;
                if (imageView9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adImgv");
                    imageView6 = null;
                } else {
                    imageView6 = imageView9;
                }
                HomeMsg homeMsg2 = this.mData;
                ImageViewExtKt.load(imageView6, (homeMsg2 == null || (coverUrl2 = homeMsg2.getCoverUrl()) == null) ? "" : coverUrl2, (261628 & 2) != 0 ? 0 : 0, (261628 & 4) != 0 ? 0 : 0, (261628 & 8) != 0 ? false : false, (261628 & 16) != 0 ? false : false, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : true, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
            } else {
                YddHostConfig companion = YddHostConfig.INSTANCE.getInstance();
                HomeMsg homeMsg3 = this.mData;
                if (homeMsg3 != null && (coverUrl = homeMsg3.getCoverUrl()) != null) {
                    str = coverUrl;
                }
                String fileFullUrl = companion.getFileFullUrl(str);
                HomeMsg homeMsg4 = this.mData;
                Intrinsics.checkNotNull(homeMsg4);
                LogExtKt.logd("ad 弹窗 mFullImg " + fileFullUrl + " " + homeMsg4.getMFullImg(), YddConfig.TAG);
                ImageView imageView10 = this.adImgv;
                if (imageView10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adImgv");
                    imageView3 = null;
                } else {
                    imageView3 = imageView10;
                }
                ImageViewExtKt.load(imageView3, fileFullUrl, (261628 & 2) != 0 ? 0 : 0, (261628 & 4) != 0 ? 0 : 0, (261628 & 8) != 0 ? false : false, (261628 & 16) != 0 ? false : false, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : false, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
                if (this.mOneDayShow) {
                    DataStoreUtils.INSTANCE.saveSyncLongData(fileFullUrl, DateUtil.getTimeInMillisLong());
                }
            }
            HomeMsg homeMsg5 = this.mData;
            Intrinsics.checkNotNull(homeMsg5);
            if (homeMsg5.getMHalfClick()) {
                View view5 = this.leftV;
                if (view5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("leftV");
                    view5 = null;
                }
                view5.setVisibility(0);
                View view6 = this.rightV;
                if (view6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rightV");
                    view6 = null;
                }
                view6.setVisibility(0);
            } else {
                View view7 = this.leftV;
                if (view7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("leftV");
                    view7 = null;
                }
                view7.setVisibility(8);
                View view8 = this.rightV;
                if (view8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rightV");
                    view8 = null;
                }
                view8.setVisibility(8);
            }
            if (!this.mRecommend) {
                ConstraintLayout constraintLayout2 = this.recommendCl;
                if (constraintLayout2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("recommendCl");
                } else {
                    constraintLayout = constraintLayout2;
                }
                constraintLayout.setVisibility(8);
                return;
            }
            ConstraintLayout constraintLayout3 = this.recommendCl;
            if (constraintLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("recommendCl");
                constraintLayout3 = null;
            }
            constraintLayout3.setVisibility(0);
            ImageView imageView11 = this.oneImgv;
            if (imageView11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("oneImgv");
                imageView4 = null;
            } else {
                imageView4 = imageView11;
            }
            ViewExtKt.setDebounceClickListener$default(imageView4, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.s
                @Override // android.view.View.OnClickListener
                public final void onClick(View view9) {
                    PopupAd.viewDetailsShow$lambda$4(this.f25947c, view9);
                }
            }, 0L, 2, null);
            ImageView imageView12 = this.twoImgv;
            if (imageView12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("twoImgv");
                imageView5 = null;
            } else {
                imageView5 = imageView12;
            }
            ViewExtKt.setDebounceClickListener$default(imageView5, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.t
                @Override // android.view.View.OnClickListener
                public final void onClick(View view9) {
                    PopupAd.viewDetailsShow$lambda$5(this.f25948c, view9);
                }
            }, 0L, 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void viewDetailsShow$lambda$0(PopupAd this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
        OnPopupAdClickListener onPopupAdClickListener = this$0.mListener;
        if (onPopupAdClickListener != null) {
            ImageView imageView = this$0.xImgv;
            if (imageView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("xImgv");
                imageView = null;
            }
            onPopupAdClickListener.onClick(imageView, this$0.mRecommend);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void viewDetailsShow$lambda$1(PopupAd this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupAdClickListener onPopupAdClickListener = this$0.mListener;
        if (onPopupAdClickListener != null) {
            ImageView imageView = this$0.adImgv;
            if (imageView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adImgv");
                imageView = null;
            }
            onPopupAdClickListener.onClick(imageView, this$0.mRecommend);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void viewDetailsShow$lambda$2(PopupAd this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupAdClickListener onPopupAdClickListener = this$0.mListener;
        if (onPopupAdClickListener != null) {
            View view2 = this$0.leftV;
            if (view2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("leftV");
                view2 = null;
            }
            OnPopupAdClickListener.DefaultImpls.onClick$default(onPopupAdClickListener, view2, false, 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void viewDetailsShow$lambda$3(PopupAd this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupAdClickListener onPopupAdClickListener = this$0.mListener;
        if (onPopupAdClickListener != null) {
            View view2 = this$0.rightV;
            if (view2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rightV");
                view2 = null;
            }
            OnPopupAdClickListener.DefaultImpls.onClick$default(onPopupAdClickListener, view2, false, 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void viewDetailsShow$lambda$4(PopupAd this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupAdClickListener onPopupAdClickListener = this$0.mListener;
        if (onPopupAdClickListener != null) {
            ImageView imageView = this$0.oneImgv;
            if (imageView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("oneImgv");
                imageView = null;
            }
            onPopupAdClickListener.onClick(imageView, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void viewDetailsShow$lambda$5(PopupAd this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupAdClickListener onPopupAdClickListener = this$0.mListener;
        if (onPopupAdClickListener != null) {
            ImageView imageView = this$0.twoImgv;
            if (imageView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("twoImgv");
                imageView = null;
            }
            onPopupAdClickListener.onClick(imageView, true);
        }
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.popup_home_ad;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() throws InterruptedException {
        super.onCreate();
        View viewFindViewById = findViewById(R.id.adImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.adImgv)");
        this.adImgv = (ImageView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.rightV);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.rightV)");
        this.rightV = viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.leftV);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.leftV)");
        this.leftV = viewFindViewById3;
        viewDetailsShow();
    }

    @NotNull
    public final PopupAd setData(@NotNull HomeMsg data, boolean oneDayShow, boolean recommend) throws InterruptedException {
        Intrinsics.checkNotNullParameter(data, "data");
        this.mData = data;
        this.mOneDayShow = oneDayShow;
        this.mRecommend = recommend;
        if (this.adImgv != null) {
            viewDetailsShow();
        }
        return this;
    }

    public final void setOnPopupAdClickListener(@NotNull OnPopupAdClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }
}

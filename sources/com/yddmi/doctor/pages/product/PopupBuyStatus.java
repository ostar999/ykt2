package com.yddmi.doctor.pages.product;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.catchpig.mvvm.ext.ImageViewExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.Toaster;
import com.lxj.xpopup.core.CenterPopupView;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.utils.PermissionInterceptor;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000I\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u000b*\u0001\u000b\u0018\u00002\u00020\u0001:\u0001#B\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0017\u001a\u00020\u0006H\u0014J\b\u0010\u0018\u001a\u00020\u0019H\u0014J\b\u0010\u001a\u001a\u00020\u0019H\u0016J\b\u0010\u001b\u001a\u00020\u0019H\u0002J\u001a\u0010\u001c\u001a\u00020\u00002\b\u0010\u001d\u001a\u0004\u0018\u00010\u00102\b\b\u0002\u0010\u001e\u001a\u00020\u0006J\u000e\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u000eJ\b\u0010!\u001a\u00020\u0019H\u0002J\u0012\u0010\"\u001a\u00020\u00192\b\b\u0002\u0010\u001e\u001a\u00020\u0006H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\fR\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lcom/yddmi/doctor/pages/product/PopupBuyStatus;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "mCurrentStatus", "", "mDotIndex", "mDotTime", "", "mHandler", "com/yddmi/doctor/pages/product/PopupBuyStatus$mHandler$1", "Lcom/yddmi/doctor/pages/product/PopupBuyStatus$mHandler$1;", "mListener", "Lcom/yddmi/doctor/pages/product/PopupBuyStatus$OnPopupBuyStatusClickListener;", "mQrUrl", "", "qrImgv", "Landroid/widget/ImageView;", "saveBtv", "Landroid/widget/TextView;", "tipTv", "titleTv", "getImplLayoutId", "onCreate", "", "onDestroy", "saveQr2Photo", PLVRxEncryptDataFunction.SET_DATA_METHOD, "qrUrl", "status", "setOnPopupBuyStatusClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "viewDetailsShow", "viewStatus", "OnPopupBuyStatusClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupBuyStatus extends CenterPopupView {
    private int mCurrentStatus;
    private int mDotIndex;
    private final long mDotTime;

    @NotNull
    private final PopupBuyStatus$mHandler$1 mHandler;

    @Nullable
    private OnPopupBuyStatusClickListener mListener;

    @Nullable
    private String mQrUrl;
    private ImageView qrImgv;
    private TextView saveBtv;
    private TextView tipTv;
    private TextView titleTv;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/pages/product/PopupBuyStatus$OnPopupBuyStatusClickListener;", "", "onClick", "", "view", "Landroid/view/View;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupBuyStatusClickListener {
        void onClick(@Nullable View view);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.yddmi.doctor.pages.product.PopupBuyStatus$mHandler$1] */
    public PopupBuyStatus(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mQrUrl = "";
        this.mCurrentStatus = 100;
        this.mDotTime = 600L;
        final Looper mainLooper = Looper.getMainLooper();
        this.mHandler = new Handler(mainLooper) { // from class: com.yddmi.doctor.pages.product.PopupBuyStatus$mHandler$1
            @Override // android.os.Handler
            public void handleMessage(@NotNull Message msg) {
                Intrinsics.checkNotNullParameter(msg, "msg");
                super.handleMessage(msg);
                int i2 = msg.what;
                if (i2 != 100) {
                    if (i2 != 101) {
                        return;
                    }
                    removeMessages(100);
                    this.this$0.viewStatus(101);
                    return;
                }
                this.this$0.mDotIndex++;
                int i3 = this.this$0.mDotIndex;
                TextView textView = null;
                if (i3 == 0) {
                    TextView textView2 = this.this$0.tipTv;
                    if (textView2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tipTv");
                    } else {
                        textView = textView2;
                    }
                    textView.setText(". ");
                } else if (i3 == 1) {
                    TextView textView3 = this.this$0.tipTv;
                    if (textView3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tipTv");
                    } else {
                        textView = textView3;
                    }
                    textView.setText(". . ");
                } else if (i3 == 2) {
                    TextView textView4 = this.this$0.tipTv;
                    if (textView4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tipTv");
                    } else {
                        textView = textView4;
                    }
                    textView.setText(". . .");
                    this.this$0.mDotIndex = 0;
                    sendEmptyMessageDelayed(101, this.this$0.mDotTime);
                }
                sendEmptyMessageDelayed(100, this.this$0.mDotTime);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopupBuyStatus this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PopupBuyStatus this$0, View view) throws NoSuchMethodException, SecurityException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.saveQr2Photo();
        this$0.dismiss();
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        if (companion.getInstance().getmWxApi().isWXAppInstalled()) {
            companion.getInstance().getmWxApi().openWXApp();
        }
    }

    private final void saveQr2Photo() throws NoSuchMethodException, SecurityException {
        XXPermissions.with(getContext()).permission(Permission.WRITE_EXTERNAL_STORAGE).interceptor(new PermissionInterceptor(105)).request(new OnPermissionCallback() { // from class: com.yddmi.doctor.pages.product.PopupBuyStatus.saveQr2Photo.1
            @Override // com.hjq.permissions.OnPermissionCallback
            public void onDenied(@NotNull List<String> permissions, boolean doNotAskAgain) {
                Intrinsics.checkNotNullParameter(permissions, "permissions");
                if (!doNotAskAgain) {
                    Toaster.show(R.string.common_permissions);
                } else {
                    Toaster.show(R.string.notification_file);
                    XXPermissions.startPermissionActivity(PopupBuyStatus.this.getContext(), permissions);
                }
            }

            @Override // com.hjq.permissions.OnPermissionCallback
            public void onGranted(@NotNull List<String> permissions, boolean allGranted) {
                Intrinsics.checkNotNullParameter(permissions, "permissions");
                if (allGranted) {
                } else {
                    Toaster.show(R.string.common_permissions);
                }
            }
        });
    }

    public static /* synthetic */ PopupBuyStatus setData$default(PopupBuyStatus popupBuyStatus, String str, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 100;
        }
        return popupBuyStatus.setData(str, i2);
    }

    private final void viewDetailsShow() {
        ImageView imageView = this.qrImgv;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("qrImgv");
            imageView = null;
        }
        ImageViewExtKt.load(imageView, YddHostConfig.INSTANCE.getInstance().getFileFullUrl(this.mQrUrl), (261628 & 2) != 0 ? 0 : 0, (261628 & 4) != 0 ? 0 : 0, (261628 & 8) != 0 ? false : false, (261628 & 16) != 0 ? false : false, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : false, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
        viewStatus(this.mCurrentStatus);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewStatus(int status) {
        TextView textView = null;
        switch (status) {
            case 100:
                TextView textView2 = this.titleTv;
                if (textView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("titleTv");
                    textView2 = null;
                }
                textView2.setText(getContext().getString(R.string.product_buy_tip1));
                TextView textView3 = this.tipTv;
                if (textView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tipTv");
                    textView3 = null;
                }
                textView3.setText("。 ");
                TextView textView4 = this.saveBtv;
                if (textView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("saveBtv");
                } else {
                    textView = textView4;
                }
                textView.setVisibility(8);
                removeMessages(100);
                sendEmptyMessageDelayed(100, this.mDotTime);
                break;
            case 101:
                TextView textView5 = this.titleTv;
                if (textView5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("titleTv");
                    textView5 = null;
                }
                textView5.setText(getContext().getString(R.string.product_buy_tip2));
                TextView textView6 = this.tipTv;
                if (textView6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tipTv");
                    textView6 = null;
                }
                textView6.setText(getContext().getString(R.string.product_buy_tip4));
                TextView textView7 = this.saveBtv;
                if (textView7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("saveBtv");
                } else {
                    textView = textView7;
                }
                textView.setVisibility(0);
                break;
            case 102:
                TextView textView8 = this.titleTv;
                if (textView8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("titleTv");
                    textView8 = null;
                }
                textView8.setText(getContext().getString(R.string.product_buy_tip3));
                TextView textView9 = this.tipTv;
                if (textView9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tipTv");
                    textView9 = null;
                }
                textView9.setText(getContext().getString(R.string.product_buy_tip5));
                TextView textView10 = this.saveBtv;
                if (textView10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("saveBtv");
                } else {
                    textView = textView10;
                }
                textView.setVisibility(0);
                break;
        }
    }

    public static /* synthetic */ void viewStatus$default(PopupBuyStatus popupBuyStatus, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = 100;
        }
        popupBuyStatus.viewStatus(i2);
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.product_popup_buy_status;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        View viewFindViewById = findViewById(R.id.qrImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.qrImgv)");
        this.qrImgv = (ImageView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.titleTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.titleTv)");
        this.titleTv = (TextView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.tipTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.tipTv)");
        this.tipTv = (TextView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.saveBtv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.saveBtv)");
        this.saveBtv = (TextView) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.xImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById<ImageView>(R.id.xImgv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById5, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.product.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupBuyStatus.onCreate$lambda$0(this.f25990c, view);
            }
        }, 0L, 2, null);
        TextView textView = this.saveBtv;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("saveBtv");
            textView = null;
        }
        ViewExtKt.setDebounceClickListener$default(textView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.product.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws NoSuchMethodException, SecurityException {
                PopupBuyStatus.onCreate$lambda$1(this.f25991c, view);
            }
        }, 0L, 2, null);
        viewDetailsShow();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDestroy() {
        super.onDestroy();
        removeCallbacksAndMessages(null);
    }

    @NotNull
    public final PopupBuyStatus setData(@Nullable String qrUrl, int status) {
        this.mQrUrl = qrUrl;
        this.mCurrentStatus = status;
        if (this.qrImgv != null) {
            viewDetailsShow();
        }
        return this;
    }

    public final void setOnPopupBuyStatusClickListener(@NotNull OnPopupBuyStatusClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }
}

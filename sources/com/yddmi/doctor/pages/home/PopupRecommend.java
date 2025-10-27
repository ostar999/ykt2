package com.yddmi.doctor.pages.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.catchpig.mvvm.ext.ImageViewExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.utils.ext.TransformExtKt;
import com.catchpig.utils.manager.ContextManager;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.Toaster;
import com.lxj.xpopup.core.CenterPopupView;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.config.YddPointManager;
import com.yddmi.doctor.entity.result.HomeMsg;
import com.yddmi.doctor.utils.PermissionInterceptor;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\f\u001a\u00020\rH\u0014J\b\u0010\u000e\u001a\u00020\u000fH\u0014J\b\u0010\u0010\u001a\u00020\u000fH\u0002J\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\nJ\b\u0010\u0013\u001a\u00020\u000fH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/yddmi/doctor/pages/home/PopupRecommend;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "adImgv", "Landroid/widget/ImageView;", "codeTv", "Landroid/widget/TextView;", "mData", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "qrImgv", "getImplLayoutId", "", "onCreate", "", "saveQr2Photo", PLVRxEncryptDataFunction.SET_DATA_METHOD, "data", "viewDetailsShow", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupRecommend extends CenterPopupView {
    private ImageView adImgv;
    private TextView codeTv;

    @Nullable
    private HomeMsg mData;
    private ImageView qrImgv;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupRecommend(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    private final void saveQr2Photo() throws NoSuchMethodException, SecurityException {
        XXPermissions.with(getContext()).permission(Permission.WRITE_EXTERNAL_STORAGE).interceptor(new PermissionInterceptor(105)).request(new OnPermissionCallback() { // from class: com.yddmi.doctor.pages.home.PopupRecommend.saveQr2Photo.1
            @Override // com.hjq.permissions.OnPermissionCallback
            public void onDenied(@NotNull List<String> permissions, boolean doNotAskAgain) {
                Intrinsics.checkNotNullParameter(permissions, "permissions");
                if (!doNotAskAgain) {
                    Toaster.show(R.string.common_permissions);
                } else {
                    Toaster.show(R.string.notification_file);
                    XXPermissions.startPermissionActivity(PopupRecommend.this.getContext(), permissions);
                }
            }

            @Override // com.hjq.permissions.OnPermissionCallback
            public void onGranted(@NotNull List<String> permissions, boolean allGranted) {
                Intrinsics.checkNotNullParameter(permissions, "permissions");
                if (!allGranted) {
                    Toaster.show(R.string.common_permissions);
                } else {
                    YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-CENTER-EXPAND-SAVEPHOTO", "推广赚钱-保存到相册", null, 4, null);
                }
            }
        });
    }

    private final void viewDetailsShow() {
        ImageView imageView;
        ImageView imageView2;
        String coverUrl;
        ImageView imageView3;
        String coverUrl2;
        View viewFindViewById = findViewById(R.id.xImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById<ImageView>(R.id.xImgv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.home.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupRecommend.viewDetailsShow$lambda$0(this.f25828c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById2 = findViewById(R.id.saveImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById<ImageView>(R.id.saveImgv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById2, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.home.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws NoSuchMethodException, SecurityException {
                PopupRecommend.viewDetailsShow$lambda$1(this.f25829c, view);
            }
        }, 0L, 2, null);
        ImageView imageView4 = this.adImgv;
        ImageView imageView5 = null;
        if (imageView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adImgv");
            imageView = null;
        } else {
            imageView = imageView4;
        }
        ViewExtKt.setDebounceClickListener$default(imageView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.home.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupRecommend.viewDetailsShow$lambda$2(view);
            }
        }, 0L, 2, null);
        HomeMsg homeMsg = this.mData;
        if (homeMsg != null) {
            Intrinsics.checkNotNull(homeMsg);
            String str = "";
            if (homeMsg.getMFullImg()) {
                ImageView imageView6 = this.adImgv;
                if (imageView6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adImgv");
                    imageView3 = null;
                } else {
                    imageView3 = imageView6;
                }
                HomeMsg homeMsg2 = this.mData;
                ImageViewExtKt.load(imageView3, (homeMsg2 == null || (coverUrl2 = homeMsg2.getCoverUrl()) == null) ? "" : coverUrl2, (261628 & 2) != 0 ? 0 : 0, (261628 & 4) != 0 ? 0 : 0, (261628 & 8) != 0 ? false : false, (261628 & 16) != 0 ? false : false, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : true, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
                return;
            }
            YddHostConfig companion = YddHostConfig.INSTANCE.getInstance();
            HomeMsg homeMsg3 = this.mData;
            if (homeMsg3 != null && (coverUrl = homeMsg3.getCoverUrl()) != null) {
                str = coverUrl;
            }
            String fileFullUrl = companion.getFileFullUrl(str);
            ImageView imageView7 = this.adImgv;
            if (imageView7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adImgv");
                imageView2 = null;
            } else {
                imageView2 = imageView7;
            }
            ImageViewExtKt.load(imageView2, fileFullUrl, (261628 & 2) != 0 ? 0 : 0, (261628 & 4) != 0 ? 0 : 0, (261628 & 8) != 0 ? false : false, (261628 & 16) != 0 ? false : false, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : false, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
            TextView textView = this.codeTv;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("codeTv");
                textView = null;
            }
            HomeMsg homeMsg4 = this.mData;
            Intrinsics.checkNotNull(homeMsg4);
            textView.setText(homeMsg4.getInviterCode());
            TextView textView2 = this.codeTv;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("codeTv");
                textView2 = null;
            }
            textView2.setVisibility(0);
            Drawable drawable = ContextManager.INSTANCE.getInstance().getContext().getDrawable(R.drawable.common_icon5);
            Bitmap bitmap = drawable != null ? TransformExtKt.toBitmap(drawable) : null;
            ImageView imageView8 = this.qrImgv;
            if (imageView8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("qrImgv");
            } else {
                imageView5 = imageView8;
            }
            imageView5.setImageBitmap(bitmap);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void viewDetailsShow$lambda$0(PopupRecommend this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void viewDetailsShow$lambda$1(PopupRecommend this$0, View view) throws NoSuchMethodException, SecurityException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
        this$0.saveQr2Photo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void viewDetailsShow$lambda$2(View view) {
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.popup_home_recommend;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        View viewFindViewById = findViewById(R.id.adImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.adImgv)");
        this.adImgv = (ImageView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.qrImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.qrImgv)");
        this.qrImgv = (ImageView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.codeTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.codeTv)");
        this.codeTv = (TextView) viewFindViewById3;
        viewDetailsShow();
    }

    @NotNull
    public final PopupRecommend setData(@NotNull HomeMsg data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.mData = data;
        if (this.adImgv != null) {
            viewDetailsShow();
        }
        return this;
    }
}

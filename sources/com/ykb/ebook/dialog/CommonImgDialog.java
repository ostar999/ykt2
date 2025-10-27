package com.ykb.ebook.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ruffian.library.widget.RImageView;
import com.ruffian.library.widget.RLinearLayout;
import com.ruffian.library.widget.helper.RBaseHelper;
import com.ykb.ebook.R;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.util.BitmapUtilsKt;
import com.ykb.ebook.util.ImageLoader;
import com.ykb.ebook.util.ToastUtilsKt;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/CommonImgDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class CommonImgDialog {

    @Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010'\u001a\u00020 H\u0002J\u0010\u0010(\u001a\u00020 2\u0006\u0010)\u001a\u00020*H\u0016J\u000e\u0010+\u001a\u00020\u00002\u0006\u0010,\u001a\u00020-J\u000e\u0010.\u001a\u00020\u00002\u0006\u0010/\u001a\u00020-J\u000e\u00100\u001a\u00020\u00002\u0006\u00101\u001a\u00020\u001bJ\u000e\u00102\u001a\u00020\u00002\u0006\u00103\u001a\u000204J2\u00105\u001a\u00020\u00002\u0014\u00106\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u001f\u0012\u0004\u0012\u00020 0\u001e2\u0014\u0010\u001d\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u001f\u0012\u0004\u0012\u00020 0\u001eJ\u001c\u00107\u001a\u00020\u00002\u0014\u0010\u001d\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u001f\u0012\u0004\u0012\u00020 0\u001eJ\u000e\u00108\u001a\u00020\u00002\u0006\u00109\u001a\u00020\u001bJ\u000e\u0010:\u001a\u00020\u00002\u0006\u0010;\u001a\u00020\u001bJ\u000e\u0010<\u001a\u00020\u00002\u0006\u0010=\u001a\u00020\u001bR\u001d\u0010\u0005\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001d\u0010\u000b\u001a\u0004\u0018\u00010\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\n\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u0010\u001a\u0004\u0018\u00010\u00118BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\n\u001a\u0004\b\u0012\u0010\u0013R\u001d\u0010\u0015\u001a\u0004\u0018\u00010\u00168BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\n\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u001d\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u001f\u0012\u0004\u0012\u00020 \u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010!\u001a\u0004\u0018\u00010\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\n\u001a\u0004\b\"\u0010\u000eR\u001d\u0010$\u001a\u0004\u0018\u00010\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b&\u0010\n\u001a\u0004\b%\u0010\u000e¨\u0006>"}, d2 = {"Lcom/ykb/ebook/dialog/CommonImgDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "closeiv", "Landroid/widget/ImageView;", "getCloseiv", "()Landroid/widget/ImageView;", "closeiv$delegate", "Lkotlin/Lazy;", "confirmView", "Landroid/widget/TextView;", "getConfirmView", "()Landroid/widget/TextView;", "confirmView$delegate", "dialogIvQr", "Lcom/ruffian/library/widget/RImageView;", "getDialogIvQr", "()Lcom/ruffian/library/widget/RImageView;", "dialogIvQr$delegate", "layoutRoot", "Lcom/ruffian/library/widget/RLinearLayout;", "getLayoutRoot", "()Lcom/ruffian/library/widget/RLinearLayout;", "layoutRoot$delegate", "mQrurl", "", "mWechat", "rightClick", "Lkotlin/Function1;", "Lcom/ykb/ebook/dialog/BasicDialog;", "", "subTitleView", "getSubTitleView", "subTitleView$delegate", "wxCodeTv", "getWxCodeTv", "wxCodeTv$delegate", "initTheme", "onClick", "view", "Landroid/view/View;", "setConfirmView", "drawable", "", "setDialogBg", "color", "setDialogQr", "qrurl", "setIsNight", "isNight", "", "setListener", "leftClick", "setRightClick", "setRightText", "rightText", "setSubTitle", "subTitle", "setWxCode", "wechat", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder extends BasicDialog.Builder<Builder> {

        /* renamed from: closeiv$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy closeiv;

        /* renamed from: confirmView$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy confirmView;

        /* renamed from: dialogIvQr$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy dialogIvQr;

        /* renamed from: layoutRoot$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy layoutRoot;

        @NotNull
        private String mQrurl;

        @NotNull
        private String mWechat;

        @Nullable
        private Function1<? super BasicDialog, Unit> rightClick;

        /* renamed from: subTitleView$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy subTitleView;

        /* renamed from: wxCodeTv$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy wxCodeTv;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull Context context) {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            this.dialogIvQr = LazyKt__LazyJVMKt.lazy(new Function0<RImageView>() { // from class: com.ykb.ebook.dialog.CommonImgDialog$Builder$dialogIvQr$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RImageView invoke() {
                    return (RImageView) this.this$0.findViewById(R.id.dialog_iv_qr);
                }
            });
            this.closeiv = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.CommonImgDialog$Builder$closeiv$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ImageView invoke() {
                    return (ImageView) this.this$0.findViewById(R.id.closeiv);
                }
            });
            this.subTitleView = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.CommonImgDialog$Builder$subTitleView$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_sub_title);
                }
            });
            this.confirmView = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.CommonImgDialog$Builder$confirmView$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_right);
                }
            });
            this.wxCodeTv = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.CommonImgDialog$Builder$wxCodeTv$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.wx_code_tv);
                }
            });
            this.layoutRoot = LazyKt__LazyJVMKt.lazy(new Function0<RLinearLayout>() { // from class: com.ykb.ebook.dialog.CommonImgDialog$Builder$layoutRoot$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RLinearLayout invoke() {
                    return (RLinearLayout) this.this$0.findViewById(R.id.dialog_layout_root);
                }
            });
            this.mWechat = "";
            this.mQrurl = "";
            setContentView(R.layout.dialog_common_img);
            setCancelable(false);
            setCanceledOnTouchOutside(false);
            setAnimStyle(AnimAction.INSTANCE.getANIM_IOS());
            setGravity(17);
            setOnClickListener(getConfirmView());
            setOnClickListener(getCloseiv());
            initTheme();
        }

        private final ImageView getCloseiv() {
            return (ImageView) this.closeiv.getValue();
        }

        private final TextView getConfirmView() {
            return (TextView) this.confirmView.getValue();
        }

        private final RImageView getDialogIvQr() {
            return (RImageView) this.dialogIvQr.getValue();
        }

        private final RLinearLayout getLayoutRoot() {
            return (RLinearLayout) this.layoutRoot.getValue();
        }

        private final TextView getSubTitleView() {
            return (TextView) this.subTitleView.getValue();
        }

        private final TextView getWxCodeTv() {
            return (TextView) this.wxCodeTv.getValue();
        }

        private final void initTheme() {
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.Builder, com.ykb.ebook.common_interface.ClickAction, android.view.View.OnClickListener
        public void onClick(@NotNull View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            int id = view.getId();
            if (id != R.id.tv_right) {
                if (id == R.id.closeiv) {
                    dismiss();
                }
            } else {
                Glide.with(getContext()).asBitmap().load(this.mQrurl).into((RequestBuilder<Bitmap>) new CustomTarget<Bitmap>() { // from class: com.ykb.ebook.dialog.CommonImgDialog$Builder$onClick$1
                    @Override // com.bumptech.glide.request.target.Target
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }

                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(Object obj, Transition transition) {
                        onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
                    }

                    public void onResourceReady(@NotNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Intrinsics.checkNotNullParameter(resource, "resource");
                        if (BitmapUtilsKt.saveImageToGallery(this.this$0.getContext(), resource, "Downloaded_" + System.currentTimeMillis())) {
                            ToastUtilsKt.toastOnUi$default(this.this$0.getContext(), "图片已保存到相册", 0, 2, (Object) null);
                        } else {
                            ToastUtilsKt.toastOnUi$default(this.this$0.getContext(), "保存失败", 0, 2, (Object) null);
                        }
                    }
                });
                dismiss();
                Function1<? super BasicDialog, Unit> function1 = this.rightClick;
                if (function1 != null) {
                    function1.invoke(getDialog());
                }
            }
        }

        @NotNull
        public final Builder setConfirmView(int drawable) {
            TextView confirmView = getConfirmView();
            if (confirmView != null) {
                confirmView.setBackgroundResource(drawable);
            }
            return this;
        }

        @NotNull
        public final Builder setDialogBg(int color) {
            RLinearLayout layoutRoot = getLayoutRoot();
            RBaseHelper helper = layoutRoot != null ? layoutRoot.getHelper() : null;
            if (helper != null) {
                helper.setBackgroundColorNormal(getColor(color));
            }
            return this;
        }

        @NotNull
        public final Builder setDialogQr(@NotNull String qrurl) {
            Intrinsics.checkNotNullParameter(qrurl, "qrurl");
            if (qrurl.length() == 0) {
                RImageView dialogIvQr = getDialogIvQr();
                Intrinsics.checkNotNull(dialogIvQr);
                dialogIvQr.setVisibility(8);
            } else {
                this.mQrurl = qrurl;
                RImageView dialogIvQr2 = getDialogIvQr();
                Intrinsics.checkNotNull(dialogIvQr2);
                dialogIvQr2.setVisibility(0);
                RequestBuilder<Drawable> requestBuilderLoad = ImageLoader.INSTANCE.load(getContext(), qrurl);
                RImageView dialogIvQr3 = getDialogIvQr();
                Intrinsics.checkNotNull(dialogIvQr3);
                requestBuilderLoad.into(dialogIvQr3);
            }
            return this;
        }

        @NotNull
        public final Builder setIsNight(boolean isNight) {
            if (isNight) {
                TextView subTitleView = getSubTitleView();
                if (subTitleView != null) {
                    subTitleView.setTextColor(getColor(R.color.color_575F79));
                }
                TextView wxCodeTv = getWxCodeTv();
                if (wxCodeTv != null) {
                    wxCodeTv.setTextColor(getColor(R.color.secondary_text_color_night));
                }
                TextView confirmView = getConfirmView();
                if (confirmView != null) {
                    confirmView.setTextColor(getColor(R.color.new_bg_two_color_night));
                }
                ImageView closeiv = getCloseiv();
                if (closeiv != null) {
                    closeiv.setImageDrawable(getDrawable(R.mipmap.icon_close_night));
                }
            } else {
                TextView subTitleView2 = getSubTitleView();
                if (subTitleView2 != null) {
                    subTitleView2.setTextColor(getColor(R.color.first_txt_color));
                }
                TextView wxCodeTv2 = getWxCodeTv();
                if (wxCodeTv2 != null) {
                    wxCodeTv2.setTextColor(getColor(R.color.secondary_text_color));
                }
                TextView confirmView2 = getConfirmView();
                if (confirmView2 != null) {
                    confirmView2.setTextColor(getColor(R.color.white));
                }
                ImageView closeiv2 = getCloseiv();
                if (closeiv2 != null) {
                    closeiv2.setImageDrawable(getDrawable(R.mipmap.icon_close_light));
                }
            }
            return this;
        }

        @NotNull
        public final Builder setListener(@NotNull Function1<? super BasicDialog, Unit> leftClick, @NotNull Function1<? super BasicDialog, Unit> rightClick) {
            Intrinsics.checkNotNullParameter(leftClick, "leftClick");
            Intrinsics.checkNotNullParameter(rightClick, "rightClick");
            this.rightClick = rightClick;
            return this;
        }

        @NotNull
        public final Builder setRightClick(@NotNull Function1<? super BasicDialog, Unit> rightClick) {
            Intrinsics.checkNotNullParameter(rightClick, "rightClick");
            this.rightClick = rightClick;
            return this;
        }

        @NotNull
        public final Builder setRightText(@NotNull String rightText) {
            Intrinsics.checkNotNullParameter(rightText, "rightText");
            TextView confirmView = getConfirmView();
            if (confirmView != null) {
                confirmView.setText(rightText);
            }
            return this;
        }

        @NotNull
        public final Builder setSubTitle(@NotNull String subTitle) {
            Intrinsics.checkNotNullParameter(subTitle, "subTitle");
            TextView subTitleView = getSubTitleView();
            if (subTitleView != null) {
                subTitleView.setText(subTitle);
            }
            return this;
        }

        @NotNull
        public final Builder setWxCode(@NotNull String wechat) {
            Intrinsics.checkNotNullParameter(wechat, "wechat");
            this.mWechat = wechat;
            if (wechat.length() == 0) {
                TextView wxCodeTv = getWxCodeTv();
                if (wxCodeTv != null) {
                    wxCodeTv.setVisibility(8);
                }
            } else {
                TextView wxCodeTv2 = getWxCodeTv();
                Intrinsics.checkNotNull(wxCodeTv2);
                wxCodeTv2.setText("微信号：" + wechat);
            }
            return this;
        }
    }
}

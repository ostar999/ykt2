package com.ykb.ebook.dialog;

import android.app.Activity;
import android.content.AppCtxKt;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.util.ColorResourcesKt;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.ruffian.library.widget.REditText;
import com.ruffian.library.widget.RImageView;
import com.ruffian.library.widget.RTextView;
import com.ruffian.library.widget.helper.RTextViewHelper;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.dialog.BookCommentDialog;
import com.ykb.ebook.dialog.TextOperationDialog;
import com.ykb.ebook.event.ImageUploadSuccessEvent;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.util.BitmapUtils;
import com.ykb.ebook.util.ImageLoader;
import com.ykb.ebook.util.ScreenUtil;
import com.ykb.ebook.util.ToastUtilsKt;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/BookCommentDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class BookCommentDialog {

    @Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\u0006¢\u0006\u0002\u0010\nJ\b\u0010P\u001a\u000204H\u0016J\b\u0010Q\u001a\u000204H\u0002J\u0010\u0010R\u001a\u0002042\u0006\u0010S\u001a\u00020LH\u0016J\u0010\u0010T\u001a\u0002042\u0006\u0010U\u001a\u00020VH\u0007J&\u0010W\u001a\u00020\u00002\u001e\u00102\u001a\u001a\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020403R\u001d\u0010\u000b\u001a\u0004\u0018\u00010\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u0011\u001a\u0004\u0018\u00010\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0010\u001a\u0004\b\u0013\u0010\u0014R\u001d\u0010\u0016\u001a\u0004\u0018\u00010\u00178BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u0010\u001a\u0004\b\u0018\u0010\u0019R\u001d\u0010\u001b\u001a\u0004\u0018\u00010\u001c8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\u0010\u001a\u0004\b\u001d\u0010\u001eR\u001d\u0010 \u001a\u0004\u0018\u00010\u001c8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\"\u0010\u0010\u001a\u0004\b!\u0010\u001eR\u001d\u0010#\u001a\u0004\u0018\u00010\u001c8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b%\u0010\u0010\u001a\u0004\b$\u0010\u001eR\u000e\u0010&\u001a\u00020'X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010(\u001a\u0004\u0018\u00010)8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b,\u0010\u0010\u001a\u0004\b*\u0010+R\u000e\u0010-\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010.\u001a\u0004\u0018\u00010)8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b0\u0010\u0010\u001a\u0004\b/\u0010+R\u000e\u00101\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R(\u00102\u001a\u001c\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u000204\u0018\u000103X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u00105\u001a\u0004\u0018\u0001068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b9\u0010\u0010\u001a\u0004\b7\u00108R\u001d\u0010:\u001a\u0004\u0018\u0001068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b<\u0010\u0010\u001a\u0004\b;\u00108R\u001d\u0010=\u001a\u0004\u0018\u0001068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b?\u0010\u0010\u001a\u0004\b>\u00108R\u001d\u0010@\u001a\u0004\u0018\u00010A8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bD\u0010\u0010\u001a\u0004\bB\u0010CR\u001d\u0010E\u001a\u0004\u0018\u0001068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bG\u0010\u0010\u001a\u0004\bF\u00108R\u001d\u0010H\u001a\u0004\u0018\u00010A8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bJ\u0010\u0010\u001a\u0004\bI\u0010CR\u001d\u0010K\u001a\u0004\u0018\u00010L8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bO\u0010\u0010\u001a\u0004\bM\u0010N¨\u0006X"}, d2 = {"Lcom/ykb/ebook/dialog/BookCommentDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", "Lcom/ykb/ebook/dialog/TextOperationDialog$Builder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "rate", "", "content", "ratePic", "title", "(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "etInput", "Lcom/ruffian/library/widget/REditText;", "getEtInput", "()Lcom/ruffian/library/widget/REditText;", "etInput$delegate", "Lkotlin/Lazy;", "flImage", "Landroid/widget/FrameLayout;", "getFlImage", "()Landroid/widget/FrameLayout;", "flImage$delegate", "imgAlbum", "Lcom/ruffian/library/widget/RImageView;", "getImgAlbum", "()Lcom/ruffian/library/widget/RImageView;", "imgAlbum$delegate", "imgChoose", "Landroid/widget/ImageView;", "getImgChoose", "()Landroid/widget/ImageView;", "imgChoose$delegate", "imgDelete", "getImgDelete", "imgDelete$delegate", "imgFullScreen", "getImgFullScreen", "imgFullScreen$delegate", "isScaleBig", "", "layoutRoot", "Landroid/widget/LinearLayout;", "getLayoutRoot", "()Landroid/widget/LinearLayout;", "layoutRoot$delegate", "localImagePath", "lyContent", "getLyContent", "lyContent$delegate", "mRate", "onPublishClick", "Lkotlin/Function3;", "", "tvHigh", "Lcom/ruffian/library/widget/RTextView;", "getTvHigh", "()Lcom/ruffian/library/widget/RTextView;", "tvHigh$delegate", "tvLow", "getTvLow", "tvLow$delegate", "tvMiddle", "getTvMiddle", "tvMiddle$delegate", "tvPub", "Landroid/widget/TextView;", "getTvPub", "()Landroid/widget/TextView;", "tvPub$delegate", "tvPublish", "getTvPublish", "tvPublish$delegate", "tvTitle", "getTvTitle", "tvTitle$delegate", "viewLine1", "Landroid/view/View;", "getViewLine1", "()Landroid/view/View;", "viewLine1$delegate", "dismiss", "initTheme", "onClick", "view", "onMessageEvent", AliyunLogKey.KEY_EVENT, "Lcom/ykb/ebook/event/ImageUploadSuccessEvent;", "setOnPublishClick", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nBookCommentDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BookCommentDialog.kt\ncom/ykb/ebook/dialog/BookCommentDialog$Builder\n+ 2 Background.kt\nsplitties/views/BackgroundKt\n+ 3 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n*L\n1#1,297:1\n32#2:298\n32#2:299\n32#2:306\n42#3:300\n42#3:301\n42#3:302\n42#3:303\n42#3:304\n42#3:305\n42#3:307\n42#3:308\n42#3:309\n42#3:310\n42#3:311\n42#3:312\n42#3:313\n42#3:314\n42#3:315\n*S KotlinDebug\n*F\n+ 1 BookCommentDialog.kt\ncom/ykb/ebook/dialog/BookCommentDialog$Builder\n*L\n232#1:298\n243#1:299\n268#1:306\n247#1:300\n248#1:301\n250#1:302\n251#1:303\n254#1:304\n255#1:305\n276#1:307\n278#1:308\n281#1:309\n282#1:310\n284#1:311\n285#1:312\n288#1:313\n289#1:314\n290#1:315\n*E\n"})
    public static final class Builder extends BasicDialog.Builder<TextOperationDialog.Builder> {

        /* renamed from: etInput$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy etInput;

        /* renamed from: flImage$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy flImage;

        /* renamed from: imgAlbum$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgAlbum;

        /* renamed from: imgChoose$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgChoose;

        /* renamed from: imgDelete$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgDelete;

        /* renamed from: imgFullScreen$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgFullScreen;
        private boolean isScaleBig;

        /* renamed from: layoutRoot$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy layoutRoot;

        @NotNull
        private String localImagePath;

        /* renamed from: lyContent$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy lyContent;

        @NotNull
        private String mRate;

        @Nullable
        private Function3<? super String, ? super String, ? super String, Unit> onPublishClick;

        /* renamed from: tvHigh$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvHigh;

        /* renamed from: tvLow$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvLow;

        /* renamed from: tvMiddle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvMiddle;

        /* renamed from: tvPub$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvPub;

        /* renamed from: tvPublish$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvPublish;

        /* renamed from: tvTitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvTitle;

        /* renamed from: viewLine1$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy viewLine1;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        public Builder(@NotNull final Context context, @NotNull String rate, @NotNull String content, @NotNull String ratePic, @NotNull String title) throws SecurityException {
            REditText etInput;
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(rate, "rate");
            Intrinsics.checkNotNullParameter(content, "content");
            Intrinsics.checkNotNullParameter(ratePic, "ratePic");
            Intrinsics.checkNotNullParameter(title, "title");
            this.layoutRoot = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.BookCommentDialog$Builder$layoutRoot$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final LinearLayout invoke() {
                    return (LinearLayout) this.this$0.findViewById(R.id.ly_content_view);
                }
            });
            this.tvTitle = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.BookCommentDialog$Builder$tvTitle$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_title);
                }
            });
            this.tvPub = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.BookCommentDialog$Builder$tvPub$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_pub);
                }
            });
            this.viewLine1 = LazyKt__LazyJVMKt.lazy(new Function0<View>() { // from class: com.ykb.ebook.dialog.BookCommentDialog$Builder$viewLine1$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final View invoke() {
                    return this.this$0.findViewById(R.id.viewLine1);
                }
            });
            this.lyContent = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.BookCommentDialog$Builder$lyContent$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final LinearLayout invoke() {
                    return (LinearLayout) this.this$0.findViewById(R.id.ly_content);
                }
            });
            this.imgFullScreen = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.BookCommentDialog$Builder$imgFullScreen$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ImageView invoke() {
                    return (ImageView) this.this$0.findViewById(R.id.img_full_screen);
                }
            });
            this.imgDelete = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.BookCommentDialog$Builder$imgDelete$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ImageView invoke() {
                    return (ImageView) this.this$0.findViewById(R.id.img_delete);
                }
            });
            this.etInput = LazyKt__LazyJVMKt.lazy(new Function0<REditText>() { // from class: com.ykb.ebook.dialog.BookCommentDialog$Builder$etInput$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final REditText invoke() {
                    return (REditText) this.this$0.findViewById(R.id.et_input);
                }
            });
            this.flImage = LazyKt__LazyJVMKt.lazy(new Function0<FrameLayout>() { // from class: com.ykb.ebook.dialog.BookCommentDialog$Builder$flImage$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final FrameLayout invoke() {
                    return (FrameLayout) this.this$0.findViewById(R.id.fl_image);
                }
            });
            this.imgAlbum = LazyKt__LazyJVMKt.lazy(new Function0<RImageView>() { // from class: com.ykb.ebook.dialog.BookCommentDialog$Builder$imgAlbum$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RImageView invoke() {
                    return (RImageView) this.this$0.findViewById(R.id.img_album);
                }
            });
            this.imgChoose = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.BookCommentDialog$Builder$imgChoose$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ImageView invoke() {
                    return (ImageView) this.this$0.findViewById(R.id.img_choose);
                }
            });
            this.tvPublish = LazyKt__LazyJVMKt.lazy(new Function0<RTextView>() { // from class: com.ykb.ebook.dialog.BookCommentDialog$Builder$tvPublish$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RTextView invoke() {
                    return (RTextView) this.this$0.findViewById(R.id.tv_publish);
                }
            });
            this.tvHigh = LazyKt__LazyJVMKt.lazy(new Function0<RTextView>() { // from class: com.ykb.ebook.dialog.BookCommentDialog$Builder$tvHigh$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RTextView invoke() {
                    return (RTextView) this.this$0.findViewById(R.id.tv_high);
                }
            });
            this.tvMiddle = LazyKt__LazyJVMKt.lazy(new Function0<RTextView>() { // from class: com.ykb.ebook.dialog.BookCommentDialog$Builder$tvMiddle$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RTextView invoke() {
                    return (RTextView) this.this$0.findViewById(R.id.tv_middle);
                }
            });
            this.tvLow = LazyKt__LazyJVMKt.lazy(new Function0<RTextView>() { // from class: com.ykb.ebook.dialog.BookCommentDialog$Builder$tvLow$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RTextView invoke() {
                    return (RTextView) this.this$0.findViewById(R.id.tv_low);
                }
            });
            this.localImagePath = "";
            this.mRate = "";
            EventBus.getDefault().register(this);
            setContentView(R.layout.dialog_book_comment);
            setAnimStyle(AnimAction.INSTANCE.getANIM_BOTTOM());
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            setGravity(80);
            setOnClickListener(getImgDelete(), getImgChoose());
            TextView tvPub = getTvPub();
            if (tvPub != null) {
                tvPub.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.j
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BookCommentDialog.Builder._init_$lambda$0(this.f26345c, view);
                    }
                });
            }
            this.mRate = rate;
            this.localImagePath = ratePic;
            if (Intrinsics.areEqual(ratePic, "")) {
                FrameLayout flImage = getFlImage();
                if (flImage != null) {
                    ViewExtensionsKt.gone(flImage);
                }
            } else {
                FrameLayout flImage2 = getFlImage();
                if (flImage2 != null) {
                    ViewExtensionsKt.visible(flImage2);
                }
                RImageView imgAlbum = getImgAlbum();
                if (imgAlbum != null) {
                    ImageLoader.INSTANCE.load(context, this.localImagePath).optionalCenterCrop().into(imgAlbum);
                }
            }
            if (!Intrinsics.areEqual(content, "") && (etInput = getEtInput()) != null) {
                etInput.setText(content);
            }
            String str = this.mRate;
            switch (str.hashCode()) {
                case 48:
                    if (str.equals("0")) {
                        RTextView tvHigh = getTvHigh();
                        if (tvHigh != null) {
                            tvHigh.setSelected(false);
                        }
                        RTextView tvMiddle = getTvMiddle();
                        if (tvMiddle != null) {
                            tvMiddle.setSelected(false);
                        }
                        RTextView tvLow = getTvLow();
                        if (tvLow != null) {
                            tvLow.setSelected(false);
                            break;
                        }
                    }
                    break;
                case 49:
                    if (str.equals("1")) {
                        RTextView tvHigh2 = getTvHigh();
                        if (tvHigh2 != null) {
                            tvHigh2.setSelected(true);
                        }
                        RTextView tvMiddle2 = getTvMiddle();
                        if (tvMiddle2 != null) {
                            tvMiddle2.setSelected(false);
                        }
                        RTextView tvLow2 = getTvLow();
                        if (tvLow2 != null) {
                            tvLow2.setSelected(false);
                            break;
                        }
                    }
                    break;
                case 50:
                    if (str.equals("2")) {
                        RTextView tvHigh3 = getTvHigh();
                        if (tvHigh3 != null) {
                            tvHigh3.setSelected(false);
                        }
                        RTextView tvMiddle3 = getTvMiddle();
                        if (tvMiddle3 != null) {
                            tvMiddle3.setSelected(true);
                        }
                        RTextView tvLow3 = getTvLow();
                        if (tvLow3 != null) {
                            tvLow3.setSelected(false);
                            break;
                        }
                    }
                    break;
                case 51:
                    if (str.equals("3")) {
                        RTextView tvHigh4 = getTvHigh();
                        if (tvHigh4 != null) {
                            tvHigh4.setSelected(false);
                        }
                        RTextView tvMiddle4 = getTvMiddle();
                        if (tvMiddle4 != null) {
                            tvMiddle4.setSelected(false);
                        }
                        RTextView tvLow4 = getTvLow();
                        if (tvLow4 != null) {
                            tvLow4.setSelected(true);
                            break;
                        }
                    }
                    break;
            }
            ImageView imgFullScreen = getImgFullScreen();
            if (imgFullScreen != null) {
                imgFullScreen.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.k
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BookCommentDialog.Builder._init_$lambda$2(this.f26348c, view);
                    }
                });
            }
            View viewFindViewById = findViewById(R.id.tv_cancel);
            if (viewFindViewById != null) {
                viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.l
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BookCommentDialog.Builder._init_$lambda$3(this.f26352c, context, view);
                    }
                });
            }
            RTextView tvPublish = getTvPublish();
            if (tvPublish != null) {
                tvPublish.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.m
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BookCommentDialog.Builder._init_$lambda$4(this.f26357c, view);
                    }
                });
            }
            RTextView tvHigh5 = getTvHigh();
            if (tvHigh5 != null) {
                tvHigh5.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.n
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BookCommentDialog.Builder._init_$lambda$5(this.f26360c, view);
                    }
                });
            }
            RTextView tvMiddle5 = getTvMiddle();
            if (tvMiddle5 != null) {
                tvMiddle5.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.o
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BookCommentDialog.Builder._init_$lambda$6(this.f26366c, view);
                    }
                });
            }
            RTextView tvLow5 = getTvLow();
            if (tvLow5 != null) {
                tvLow5.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.p
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BookCommentDialog.Builder._init_$lambda$7(this.f26372c, view);
                    }
                });
            }
            TextView tvTitle = getTvTitle();
            if (tvTitle != null) {
                tvTitle.setText(title);
            }
            REditText etInput2 = getEtInput();
            if (etInput2 != null) {
                etInput2.requestFocus();
            }
            REditText etInput3 = getEtInput();
            if (etInput3 != null) {
                etInput3.postDelayed(new Runnable() { // from class: com.ykb.ebook.dialog.q
                    @Override // java.lang.Runnable
                    public final void run() {
                        BookCommentDialog.Builder._init_$lambda$8(context, this);
                    }
                }, 200L);
            }
            initTheme();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$0(Builder this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            RTextView tvPublish = this$0.getTvPublish();
            if (tvPublish != null) {
                tvPublish.performClick();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$2(Builder this$0, View it) {
            Window window;
            Window window2;
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.isScaleBig = !this$0.isScaleBig;
            BasicDialog dialog = this$0.getDialog();
            if (dialog != null && (window2 = dialog.getWindow()) != null) {
                window2.setLayout(-1, ScreenUtil.getScreenHeight(this$0.getActivity()));
            }
            BasicDialog dialog2 = this$0.getDialog();
            if (dialog2 != null && (window = dialog2.getWindow()) != null) {
                window.setSoftInputMode(48);
            }
            int screenHeight = ScreenUtil.getScreenHeight(this$0.getActivity());
            LinearLayout layoutRoot = this$0.getLayoutRoot();
            Intrinsics.checkNotNull(layoutRoot);
            ViewGroup.LayoutParams layoutParams = layoutRoot.getLayoutParams();
            layoutParams.height = screenHeight;
            LinearLayout layoutRoot2 = this$0.getLayoutRoot();
            Intrinsics.checkNotNull(layoutRoot2);
            layoutRoot2.setLayoutParams(layoutParams);
            LinearLayout layoutRoot3 = this$0.getLayoutRoot();
            if (layoutRoot3 != null) {
                layoutRoot3.setBackground(BitmapUtils.INSTANCE.getTopRadiusDrawabl(this$0.getContext(), 0));
            }
            View viewFindViewById = this$0.findViewById(R.id.tv_cancel);
            if (viewFindViewById != null) {
                ViewExtensionsKt.visible(viewFindViewById);
            }
            View viewFindViewById2 = this$0.findViewById(R.id.tv_pub);
            if (viewFindViewById2 != null) {
                ViewExtensionsKt.visible(viewFindViewById2);
            }
            Intrinsics.checkNotNullExpressionValue(it, "it");
            ViewExtensionsKt.gone(it);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$3(Builder this$0, Context context, View it) {
            Window window;
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(context, "$context");
            BasicDialog dialog = this$0.getDialog();
            if (dialog != null && (window = dialog.getWindow()) != null) {
                window.setLayout(-1, -2);
            }
            int pxByDp = ScreenUtil.getPxByDp(context, 313);
            LinearLayout layoutRoot = this$0.getLayoutRoot();
            ViewGroup.LayoutParams layoutParams = layoutRoot != null ? layoutRoot.getLayoutParams() : null;
            if (layoutParams != null) {
                layoutParams.height = pxByDp;
            }
            LinearLayout layoutRoot2 = this$0.getLayoutRoot();
            if (layoutRoot2 != null) {
                layoutRoot2.setLayoutParams(layoutParams);
            }
            Intrinsics.checkNotNullExpressionValue(it, "it");
            ViewExtensionsKt.gone(it);
            TextView tvPub = this$0.getTvPub();
            if (tvPub != null) {
                ViewExtensionsKt.gone(tvPub);
            }
            ImageView imgFullScreen = this$0.getImgFullScreen();
            if (imgFullScreen != null) {
                ViewExtensionsKt.visible(imgFullScreen);
            }
            LinearLayout layoutRoot3 = this$0.getLayoutRoot();
            if (layoutRoot3 == null) {
                return;
            }
            layoutRoot3.setBackground(BitmapUtils.INSTANCE.getTopRadiusDrawabl(this$0.getContext(), 16));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$4(Builder this$0, View view) {
            Editable text;
            String string;
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            REditText etInput = this$0.getEtInput();
            String string2 = (etInput == null || (text = etInput.getText()) == null || (string = text.toString()) == null) ? null : StringsKt__StringsKt.trim((CharSequence) string).toString();
            if (TextUtils.isEmpty(this$0.mRate) || Intrinsics.areEqual("0", this$0.mRate)) {
                ToastUtilsKt.toastOnUi$default(this$0.getContext(), "请选择您的评价！", 0, 2, (Object) null);
                return;
            }
            if (string2 == null || string2.length() == 0) {
                ToastUtilsKt.toastOnUi$default(this$0.getContext(), "请输入您的感受！", 0, 2, (Object) null);
                return;
            }
            this$0.dismiss();
            Function3<? super String, ? super String, ? super String, Unit> function3 = this$0.onPublishClick;
            if (function3 != null) {
                function3.invoke(this$0.localImagePath, string2, this$0.mRate);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$5(Builder this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.mRate = "1";
            RTextView tvHigh = this$0.getTvHigh();
            if (tvHigh != null) {
                tvHigh.setSelected(true);
            }
            RTextView tvMiddle = this$0.getTvMiddle();
            if (tvMiddle != null) {
                tvMiddle.setSelected(false);
            }
            RTextView tvLow = this$0.getTvLow();
            if (tvLow == null) {
                return;
            }
            tvLow.setSelected(false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$6(Builder this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.mRate = "2";
            RTextView tvHigh = this$0.getTvHigh();
            if (tvHigh != null) {
                tvHigh.setSelected(false);
            }
            RTextView tvMiddle = this$0.getTvMiddle();
            if (tvMiddle != null) {
                tvMiddle.setSelected(true);
            }
            RTextView tvLow = this$0.getTvLow();
            if (tvLow == null) {
                return;
            }
            tvLow.setSelected(false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$7(Builder this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.mRate = "3";
            RTextView tvHigh = this$0.getTvHigh();
            if (tvHigh != null) {
                tvHigh.setSelected(false);
            }
            RTextView tvMiddle = this$0.getTvMiddle();
            if (tvMiddle != null) {
                tvMiddle.setSelected(false);
            }
            RTextView tvLow = this$0.getTvLow();
            if (tvLow == null) {
                return;
            }
            tvLow.setSelected(true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$8(Context context, Builder this$0) {
            Intrinsics.checkNotNullParameter(context, "$context");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Object systemService = context.getSystemService("input_method");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
            ((InputMethodManager) systemService).showSoftInput(this$0.getEtInput(), 2);
        }

        private final REditText getEtInput() {
            return (REditText) this.etInput.getValue();
        }

        private final FrameLayout getFlImage() {
            return (FrameLayout) this.flImage.getValue();
        }

        private final RImageView getImgAlbum() {
            return (RImageView) this.imgAlbum.getValue();
        }

        private final ImageView getImgChoose() {
            return (ImageView) this.imgChoose.getValue();
        }

        private final ImageView getImgDelete() {
            return (ImageView) this.imgDelete.getValue();
        }

        private final ImageView getImgFullScreen() {
            return (ImageView) this.imgFullScreen.getValue();
        }

        private final LinearLayout getLayoutRoot() {
            return (LinearLayout) this.layoutRoot.getValue();
        }

        private final LinearLayout getLyContent() {
            return (LinearLayout) this.lyContent.getValue();
        }

        private final RTextView getTvHigh() {
            return (RTextView) this.tvHigh.getValue();
        }

        private final RTextView getTvLow() {
            return (RTextView) this.tvLow.getValue();
        }

        private final RTextView getTvMiddle() {
            return (RTextView) this.tvMiddle.getValue();
        }

        private final TextView getTvPub() {
            return (TextView) this.tvPub.getValue();
        }

        private final RTextView getTvPublish() {
            return (RTextView) this.tvPublish.getValue();
        }

        private final TextView getTvTitle() {
            return (TextView) this.tvTitle.getValue();
        }

        private final View getViewLine1() {
            return (View) this.viewLine1.getValue();
        }

        private final void initTheme() throws SecurityException {
            RTextViewHelper helper;
            int colorMode = ReadConfig.INSTANCE.getColorMode();
            if (colorMode == 0) {
                TextView tvTitle = getTvTitle();
                if (tvTitle != null) {
                    tvTitle.setTextColor(getColor(R.color.color_303030));
                }
                View viewLine1 = getViewLine1();
                if (viewLine1 != null) {
                    viewLine1.setBackgroundColor(getColor(R.color.color_theme_white_line_color));
                }
                REditText etInput = getEtInput();
                helper = etInput != null ? etInput.getHelper() : null;
                if (helper != null) {
                    helper.setBackgroundColorNormal(getColor(R.color.color_f7f8fa));
                }
                REditText etInput2 = getEtInput();
                if (etInput2 != null) {
                    etInput2.setTextColor(getColor(R.color.color_303030));
                }
                REditText etInput3 = getEtInput();
                if (etInput3 != null) {
                    etInput3.setHintTextColor(getColor(R.color.color_bfbfbf));
                }
            } else if (colorMode == 1) {
                TextView tvTitle2 = getTvTitle();
                if (tvTitle2 != null) {
                    tvTitle2.setTextColor(getColor(R.color.color_303030));
                }
                View viewLine12 = getViewLine1();
                if (viewLine12 != null) {
                    viewLine12.setBackgroundColor(getColor(R.color.color_theme_yellow_line_color));
                }
                REditText etInput4 = getEtInput();
                RTextViewHelper helper2 = etInput4 != null ? etInput4.getHelper() : null;
                if (helper2 != null) {
                    helper2.setBackgroundColorNormal(getColor(R.color.color_EDE2C3));
                }
                REditText etInput5 = getEtInput();
                if (etInput5 != null) {
                    etInput5.setTextColor(getColor(R.color.color_303030));
                }
                REditText etInput6 = getEtInput();
                if (etInput6 != null) {
                    etInput6.setHintTextColor(getColor(R.color.color_bfbfbf));
                }
                RTextView tvHigh = getTvHigh();
                RTextViewHelper helper3 = tvHigh != null ? tvHigh.getHelper() : null;
                if (helper3 != null) {
                    helper3.setTextColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_303030));
                }
                RTextView tvHigh2 = getTvHigh();
                RTextViewHelper helper4 = tvHigh2 != null ? tvHigh2.getHelper() : null;
                if (helper4 != null) {
                    helper4.setTextColorSelected(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_dd594a));
                }
                RTextView tvMiddle = getTvMiddle();
                RTextViewHelper helper5 = tvMiddle != null ? tvMiddle.getHelper() : null;
                if (helper5 != null) {
                    helper5.setTextColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_303030));
                }
                RTextView tvMiddle2 = getTvMiddle();
                RTextViewHelper helper6 = tvMiddle2 != null ? tvMiddle2.getHelper() : null;
                if (helper6 != null) {
                    helper6.setTextColorSelected(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_dd594a));
                }
                RTextView tvLow = getTvLow();
                RTextViewHelper helper7 = tvLow != null ? tvLow.getHelper() : null;
                if (helper7 != null) {
                    helper7.setTextColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_303030));
                }
                RTextView tvLow2 = getTvLow();
                RTextViewHelper helper8 = tvLow2 != null ? tvLow2.getHelper() : null;
                if (helper8 != null) {
                    helper8.setTextColorSelected(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_dd594a));
                }
                RTextView tvHigh3 = getTvHigh();
                RTextViewHelper helper9 = tvHigh3 != null ? tvHigh3.getHelper() : null;
                if (helper9 != null) {
                    helper9.setBackgroundColorNormal(getColor(R.color.color_EDE2C3));
                }
                RTextView tvMiddle3 = getTvMiddle();
                RTextViewHelper helper10 = tvMiddle3 != null ? tvMiddle3.getHelper() : null;
                if (helper10 != null) {
                    helper10.setBackgroundColorNormal(getColor(R.color.color_EDE2C3));
                }
                RTextView tvLow3 = getTvLow();
                helper = tvLow3 != null ? tvLow3.getHelper() : null;
                if (helper != null) {
                    helper.setBackgroundColorNormal(getColor(R.color.color_EDE2C3));
                }
            } else if (colorMode == 2) {
                ImageView imgFullScreen = getImgFullScreen();
                if (imgFullScreen != null) {
                    imgFullScreen.setImageDrawable(getDrawable(R.mipmap.ic_write_note_scale));
                }
                TextView tvTitle3 = getTvTitle();
                if (tvTitle3 != null) {
                    tvTitle3.setTextColor(getColor(R.color.color_7380a9));
                }
                View viewLine13 = getViewLine1();
                if (viewLine13 != null) {
                    viewLine13.setBackgroundColor(getColor(R.color.color_theme_blue_line_color));
                }
                REditText etInput7 = getEtInput();
                RTextViewHelper helper11 = etInput7 != null ? etInput7.getHelper() : null;
                if (helper11 != null) {
                    helper11.setBackgroundColorNormal(getColor(R.color.color_171C2D));
                }
                REditText etInput8 = getEtInput();
                if (etInput8 != null) {
                    etInput8.setTextColor(getColor(R.color.color_7380a9));
                }
                REditText etInput9 = getEtInput();
                if (etInput9 != null) {
                    etInput9.setHintTextColor(getColor(R.color.color_575F79));
                }
                RTextView tvHigh4 = getTvHigh();
                RTextViewHelper helper12 = tvHigh4 != null ? tvHigh4.getHelper() : null;
                if (helper12 != null) {
                    helper12.setBackgroundColorNormal(getColor(R.color.color_171C2D));
                }
                RTextView tvMiddle4 = getTvMiddle();
                RTextViewHelper helper13 = tvMiddle4 != null ? tvMiddle4.getHelper() : null;
                if (helper13 != null) {
                    helper13.setBackgroundColorNormal(getColor(R.color.color_171C2D));
                }
                RTextView tvLow4 = getTvLow();
                RTextViewHelper helper14 = tvLow4 != null ? tvLow4.getHelper() : null;
                if (helper14 != null) {
                    helper14.setBackgroundColorNormal(getColor(R.color.color_171C2D));
                }
                ImageView imgChoose = getImgChoose();
                if (imgChoose != null) {
                    Drawable drawable = getDrawable(R.drawable.icon_select_img);
                    if (drawable != null) {
                        drawable.setColorFilter(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_7380a9), PorterDuff.Mode.SRC_IN);
                    }
                    imgChoose.setImageDrawable(drawable);
                }
                RTextView tvPublish = getTvPublish();
                RTextViewHelper helper15 = tvPublish != null ? tvPublish.getHelper() : null;
                if (helper15 != null) {
                    helper15.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_B2575C));
                }
                RTextView tvHigh5 = getTvHigh();
                RTextViewHelper helper16 = tvHigh5 != null ? tvHigh5.getHelper() : null;
                if (helper16 != null) {
                    helper16.setTextColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_7380a9));
                }
                RTextView tvHigh6 = getTvHigh();
                RTextViewHelper helper17 = tvHigh6 != null ? tvHigh6.getHelper() : null;
                if (helper17 != null) {
                    helper17.setTextColorSelected(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_B2575C));
                }
                RTextView tvMiddle5 = getTvMiddle();
                RTextViewHelper helper18 = tvMiddle5 != null ? tvMiddle5.getHelper() : null;
                if (helper18 != null) {
                    helper18.setTextColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_7380a9));
                }
                RTextView tvMiddle6 = getTvMiddle();
                RTextViewHelper helper19 = tvMiddle6 != null ? tvMiddle6.getHelper() : null;
                if (helper19 != null) {
                    helper19.setTextColorSelected(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_B2575C));
                }
                RTextView tvLow5 = getTvLow();
                RTextViewHelper helper20 = tvLow5 != null ? tvLow5.getHelper() : null;
                if (helper20 != null) {
                    helper20.setTextColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_7380a9));
                }
                RTextView tvLow6 = getTvLow();
                helper = tvLow6 != null ? tvLow6.getHelper() : null;
                if (helper != null) {
                    helper.setTextColorSelected(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_B2575C));
                }
                TextView tvPub = getTvPub();
                if (tvPub != null) {
                    tvPub.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_B2575C));
                }
            }
            LinearLayout layoutRoot = getLayoutRoot();
            if (layoutRoot == null) {
                return;
            }
            layoutRoot.setBackground(BitmapUtils.INSTANCE.getTopRadiusDrawabl(getContext(), 16));
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.Builder
        public void dismiss() {
            super.dismiss();
            EventBus.getDefault().unregister(this);
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.Builder, com.ykb.ebook.common_interface.ClickAction, android.view.View.OnClickListener
        public void onClick(@NotNull View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            if (Intrinsics.areEqual(view, getImgDelete())) {
                this.localImagePath = "";
                FrameLayout flImage = getFlImage();
                if (flImage != null) {
                    ViewExtensionsKt.invisible(flImage);
                    return;
                }
                return;
            }
            if (Intrinsics.areEqual(view, getImgChoose())) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setPackage(getContext().getPackageName());
                intent.setData(Uri.parse("ebook://ykb_upload_comment_img/"));
                Context context = getContext();
                Intrinsics.checkNotNull(context, "null cannot be cast to non-null type android.app.Activity");
                ((Activity) context).startActivityForResult(intent, 999);
            }
        }

        @Subscribe
        public final void onMessageEvent(@NotNull ImageUploadSuccessEvent e2) {
            Intrinsics.checkNotNullParameter(e2, "e");
            this.localImagePath = e2.getImgUrl();
            FrameLayout flImage = getFlImage();
            if (flImage != null) {
                ViewExtensionsKt.visible(flImage);
            }
            RequestBuilder<Drawable> requestBuilderLoad = Glide.with(getContext()).load(this.localImagePath);
            RImageView imgAlbum = getImgAlbum();
            Intrinsics.checkNotNull(imgAlbum);
            requestBuilderLoad.into(imgAlbum);
        }

        @NotNull
        public final Builder setOnPublishClick(@NotNull Function3<? super String, ? super String, ? super String, Unit> onPublishClick) {
            Intrinsics.checkNotNullParameter(onPublishClick, "onPublishClick");
            this.onPublishClick = onPublishClick;
            return this;
        }
    }
}

package com.ykb.ebook.dialog;

import android.app.Activity;
import android.content.AppCtxKt;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.ColorResourcesKt;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.util.KeyboardUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.psychiatrygarden.utils.MimeTypes;
import com.ruffian.library.widget.REditText;
import com.ruffian.library.widget.RImageView;
import com.ruffian.library.widget.RTextView;
import com.ruffian.library.widget.helper.RTextViewHelper;
import com.tencent.smtt.sdk.TbsReaderView;
import com.yikaobang.yixue.R2;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.dialog.AddReviewCommentDialog;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.event.ImageUploadSuccessEvent;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.util.BitmapUtils;
import com.ykb.ebook.util.Coroutine;
import com.ykb.ebook.util.FileUtils;
import com.ykb.ebook.util.ImageLoader;
import com.ykb.ebook.util.Log;
import com.ykb.ebook.util.ScreenUtil;
import com.ykb.ebook.util.ToastUtilsKt;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.apache.http.cookie.ClientCookie;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/AddReviewCommentDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class AddReviewCommentDialog {

    @Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001Bm\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\u0006\u0010\u000b\u001a\u00020\u0007\u0012\u0006\u0010\f\u001a\u00020\u0007\u0012\b\b\u0002\u0010\r\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0007¢\u0006\u0002\u0010\u0012J\u001c\u0010]\u001a\u00020@2\u0012\u0010^\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070_H\u0002J\b\u0010`\u001a\u00020@H\u0016J\u001c\u0010a\u001a\u00020@2\u0012\u0010^\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070_H\u0002J\b\u0010b\u001a\u0004\u0018\u000105J\u0010\u0010c\u001a\u00020\u00102\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\b\u0010d\u001a\u00020@H\u0002J\u0010\u0010e\u001a\u00020@2\u0006\u0010f\u001a\u00020gH\u0007J\u0014\u0010h\u001a\u00020\u00002\f\u0010>\u001a\b\u0012\u0004\u0012\u00020@0?J\u0014\u0010i\u001a\u00020\u00002\f\u0010j\u001a\b\u0012\u0004\u0012\u00020@0?J\u0010\u0010k\u001a\u00020@2\u0006\u0010l\u001a\u00020\u0007H\u0002R\u001d\u0010\u0013\u001a\u0004\u0018\u00010\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0019\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u001c\u001a\u0004\u0018\u00010\u001d8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b \u0010\u0018\u001a\u0004\b\u001e\u0010\u001fR\u001d\u0010!\u001a\u0004\u0018\u00010\u001d8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\u0018\u001a\u0004\b\"\u0010\u001fR\u001d\u0010$\u001a\u0004\u0018\u00010%8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b(\u0010\u0018\u001a\u0004\b&\u0010'R\u001d\u0010)\u001a\u0004\u0018\u00010%8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b+\u0010\u0018\u001a\u0004\b*\u0010'R\u001d\u0010,\u001a\u0004\u0018\u00010-8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b0\u0010\u0018\u001a\u0004\b.\u0010/R\u001d\u00101\u001a\u0004\u0018\u00010%8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b3\u0010\u0018\u001a\u0004\b2\u0010'R\u001d\u00104\u001a\u0004\u0018\u0001058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b8\u0010\u0018\u001a\u0004\b6\u00107R\u001d\u00109\u001a\u0004\u0018\u00010:8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b=\u0010\u0018\u001a\u0004\b;\u0010<R\u0016\u0010>\u001a\n\u0012\u0004\u0012\u00020@\u0018\u00010?X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010A\u001a\u0004\u0018\u00010B8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bE\u0010\u0018\u001a\u0004\bC\u0010DR\u000e\u0010F\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010G\u001a\n\u0012\u0004\u0012\u00020@\u0018\u00010?X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\bH\u0010IR\u001d\u0010J\u001a\u0004\u0018\u00010K8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bN\u0010\u0018\u001a\u0004\bL\u0010MR\u001d\u0010O\u001a\u0004\u0018\u00010K8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bQ\u0010\u0018\u001a\u0004\bP\u0010MR\u001d\u0010R\u001a\u0004\u0018\u00010K8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bT\u0010\u0018\u001a\u0004\bS\u0010MR\u000e\u0010U\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010V\u001a\b\u0012\u0004\u0012\u00020\u00070WX\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010X\u001a\u0004\u0018\u00010Y8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\\\u0010\u0018\u001a\u0004\bZ\u0010[¨\u0006m"}, d2 = {"Lcom/ykb/ebook/dialog/AddReviewCommentDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "bookId", "", "chapterId", "paragraphId", "commentId", "paragraphContent", "title", "commentStr", "picture", "showKeyborad", "", "replyPrimaryId", "(Landroid/app/Activity;Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;)V", "flImage", "Landroid/widget/FrameLayout;", "getFlImage", "()Landroid/widget/FrameLayout;", "flImage$delegate", "Lkotlin/Lazy;", "imageUrl", "isScaleBig", "localImagePath", "lyContent", "Landroid/widget/LinearLayout;", "getLyContent", "()Landroid/widget/LinearLayout;", "lyContent$delegate", "lyView", "getLyView", "lyView$delegate", "mBtnAddPic", "Landroid/widget/ImageView;", "getMBtnAddPic", "()Landroid/widget/ImageView;", "mBtnAddPic$delegate", "mBtnDelPic", "getMBtnDelPic", "mBtnDelPic$delegate", "mBtnPublish", "Lcom/ruffian/library/widget/RTextView;", "getMBtnPublish", "()Lcom/ruffian/library/widget/RTextView;", "mBtnPublish$delegate", "mBtnScale", "getMBtnScale", "mBtnScale$delegate", "mEtContent", "Lcom/ruffian/library/widget/REditText;", "getMEtContent", "()Lcom/ruffian/library/widget/REditText;", "mEtContent$delegate", "mImgShowPic", "Lcom/ruffian/library/widget/RImageView;", "getMImgShowPic", "()Lcom/ruffian/library/widget/RImageView;", "mImgShowPic$delegate", "onPublishSuccess", "Lkotlin/Function0;", "", "processView", "Landroid/widget/ProgressBar;", "getProcessView", "()Landroid/widget/ProgressBar;", "processView$delegate", "s_img", "selectImageCallBack", "getShowKeyborad", "()Z", "tvCancel", "Landroid/widget/TextView;", "getTvCancel", "()Landroid/widget/TextView;", "tvCancel$delegate", "tvPub", "getTvPub", "tvPub$delegate", "tvTtitle", "getTvTtitle", "tvTtitle$delegate", "uploadingImg", "urlList", "", "viewLine", "Landroid/view/View;", "getViewLine", "()Landroid/view/View;", "viewLine$delegate", "addComment", "params", "", "dismiss", "editComment", "getEditText", "hasRequiredPermissions", "initTheme", "onMessageEvent", AliyunLogKey.KEY_EVENT, "Lcom/ykb/ebook/event/ImageUploadSuccessEvent;", "setOnPublishSuccess", "setSelectImageCallBack", "cb", "uploadFileComment", TbsReaderView.KEY_FILE_PATH, "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nAddReviewCommentDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AddReviewCommentDialog.kt\ncom/ykb/ebook/dialog/AddReviewCommentDialog$Builder\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n*L\n1#1,453:1\n1#2:454\n42#3:455\n42#3:456\n42#3:457\n42#3:458\n42#3:459\n42#3:460\n42#3:461\n42#3:462\n42#3:463\n42#3:464\n42#3:465\n42#3:466\n42#3:467\n42#3:468\n42#3:469\n42#3:470\n42#3:471\n42#3:472\n42#3:473\n42#3:474\n42#3:475\n42#3:476\n42#3:477\n42#3:478\n42#3:479\n*S KotlinDebug\n*F\n+ 1 AddReviewCommentDialog.kt\ncom/ykb/ebook/dialog/AddReviewCommentDialog$Builder\n*L\n406#1:455\n407#1:456\n408#1:457\n409#1:458\n410#1:459\n413#1:460\n415#1:461\n416#1:462\n421#1:463\n422#1:464\n423#1:465\n424#1:466\n425#1:467\n428#1:468\n430#1:469\n431#1:470\n437#1:471\n438#1:472\n439#1:473\n440#1:474\n441#1:475\n444#1:476\n446#1:477\n447#1:478\n294#1:479\n*E\n"})
    public static final class Builder extends BasicDialog.Builder<Builder> {

        /* renamed from: flImage$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy flImage;

        @NotNull
        private String imageUrl;
        private boolean isScaleBig;

        @NotNull
        private String localImagePath;

        /* renamed from: lyContent$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy lyContent;

        /* renamed from: lyView$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy lyView;

        /* renamed from: mBtnAddPic$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy mBtnAddPic;

        /* renamed from: mBtnDelPic$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy mBtnDelPic;

        /* renamed from: mBtnPublish$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy mBtnPublish;

        /* renamed from: mBtnScale$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy mBtnScale;

        /* renamed from: mEtContent$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy mEtContent;

        /* renamed from: mImgShowPic$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy mImgShowPic;

        @Nullable
        private Function0<Unit> onPublishSuccess;

        /* renamed from: processView$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy processView;

        @NotNull
        private String s_img;

        @Nullable
        private Function0<Unit> selectImageCallBack;
        private final boolean showKeyborad;

        /* renamed from: tvCancel$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvCancel;

        /* renamed from: tvPub$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvPub;

        /* renamed from: tvTtitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvTtitle;
        private boolean uploadingImg;

        @NotNull
        private final List<String> urlList;

        /* renamed from: viewLine$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy viewLine;

        public /* synthetic */ Builder(Activity activity, Context context, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, boolean z2, String str9, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(activity, context, str, str2, str3, str4, str5, str6, (i2 & 256) != 0 ? "" : str7, (i2 & 512) != 0 ? "" : str8, (i2 & 1024) != 0 ? true : z2, (i2 & 2048) != 0 ? "" : str9);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$0(Builder this$0, Context context, View view) throws SecurityException {
            Window window;
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(context, "$context");
            BasicDialog dialog = this$0.getDialog();
            if (dialog != null && (window = dialog.getWindow()) != null) {
                window.setLayout(-1, -2);
            }
            int pxByDp = ScreenUtil.getPxByDp(context, R2.attr.actionSheetPadding);
            TextView tvCancel = this$0.getTvCancel();
            if (tvCancel != null) {
                ViewExtensionsKt.gone(tvCancel);
            }
            ImageView mBtnScale = this$0.getMBtnScale();
            if (mBtnScale != null) {
                ViewExtensionsKt.visible(mBtnScale);
            }
            TextView tvPub = this$0.getTvPub();
            if (tvPub != null) {
                ViewExtensionsKt.gone(tvPub);
            }
            LinearLayout lyView = this$0.getLyView();
            ViewGroup.LayoutParams layoutParams = lyView != null ? lyView.getLayoutParams() : null;
            if (layoutParams != null) {
                layoutParams.height = pxByDp;
            }
            LinearLayout lyView2 = this$0.getLyView();
            if (lyView2 != null) {
                lyView2.setLayoutParams(layoutParams);
            }
            LinearLayout lyView3 = this$0.getLyView();
            if (lyView3 != null) {
                lyView3.setBackground(BitmapUtils.INSTANCE.getTopRadiusDrawabl(this$0.getContext(), 16));
            }
            this$0.initTheme();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$1(Builder this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            RTextView mBtnPublish = this$0.getMBtnPublish();
            if (mBtnPublish != null) {
                mBtnPublish.performClick();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$3(Builder this$0, Activity activity, View it) throws SecurityException {
            Window window;
            Window window2;
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(activity, "$activity");
            boolean z2 = !this$0.isScaleBig;
            this$0.isScaleBig = z2;
            if (z2) {
                BasicDialog dialog = this$0.getDialog();
                if (dialog != null && (window2 = dialog.getWindow()) != null) {
                    window2.setLayout(-1, ScreenUtil.getScreenHeight(this$0.getActivity()));
                }
                BasicDialog dialog2 = this$0.getDialog();
                if (dialog2 != null && (window = dialog2.getWindow()) != null) {
                    window.setSoftInputMode(48);
                }
                int screenHeight = ScreenUtil.getScreenHeight(activity);
                LinearLayout lyView = this$0.getLyView();
                Intrinsics.checkNotNull(lyView);
                ViewGroup.LayoutParams layoutParams = lyView.getLayoutParams();
                layoutParams.height = screenHeight;
                LinearLayout lyView2 = this$0.getLyView();
                Intrinsics.checkNotNull(lyView2);
                lyView2.setLayoutParams(layoutParams);
                Intrinsics.checkNotNullExpressionValue(it, "it");
                ViewExtensionsKt.gone(it);
                LinearLayout lyView3 = this$0.getLyView();
                if (lyView3 != null) {
                    lyView3.setBackground(BitmapUtils.INSTANCE.getTopRadiusDrawabl(this$0.getContext(), 0));
                }
                TextView tvCancel = this$0.getTvCancel();
                if (tvCancel != null) {
                    ViewExtensionsKt.visible(tvCancel);
                }
                TextView tvPub = this$0.getTvPub();
                if (tvPub != null) {
                    ViewExtensionsKt.visible(tvPub);
                }
            }
            this$0.initTheme();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$4(String paragraphContent, Builder this$0, String commentStr, String commentId, String bookId, String chapterId, String paragraphId, String replyPrimaryId, View view) {
            Intrinsics.checkNotNullParameter(paragraphContent, "$paragraphContent");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(commentStr, "$commentStr");
            Intrinsics.checkNotNullParameter(commentId, "$commentId");
            Intrinsics.checkNotNullParameter(bookId, "$bookId");
            Intrinsics.checkNotNullParameter(chapterId, "$chapterId");
            Intrinsics.checkNotNullParameter(paragraphId, "$paragraphId");
            Intrinsics.checkNotNullParameter(replyPrimaryId, "$replyPrimaryId");
            Log.INSTANCE.logE("gra_content", paragraphContent);
            REditText mEtContent = this$0.getMEtContent();
            String string = StringsKt__StringsKt.trim((CharSequence) String.valueOf(mEtContent != null ? mEtContent.getText() : null)).toString();
            if (string == null || string.length() == 0) {
                ToastUtilsKt.toastOnUi$default(this$0.getContext(), "请输入内容！", 0, 2, (Object) null);
                return;
            }
            if (this$0.uploadingImg) {
                ToastUtilsKt.toastOnUi$default(this$0.getContext(), "上传中", 0, 2, (Object) null);
                return;
            }
            if (string.length() < 5) {
                ToastUtilsKt.toastOnUi$default(this$0.getContext(), "段评至少输入5个字", 0, 2, (Object) null);
            } else if (Intrinsics.areEqual(commentStr, "")) {
                this$0.addComment(TextUtils.isEmpty(commentId) ? MapsKt__MapsKt.hashMapOf(new Pair("book_id", bookId), new Pair("chapter_id", chapterId), new Pair("paragraph_id", paragraphId), new Pair("paragraph_content", paragraphContent), new Pair("picture", this$0.imageUrl), new Pair(ClientCookie.COMMENT_ATTR, string), new Pair("review_type", "3")) : MapsKt__MapsKt.hashMapOf(new Pair("book_id", bookId), new Pair("chapter_id", chapterId), new Pair("paragraph_id", paragraphId), new Pair("paragraph_content", paragraphContent), new Pair("parent_id", commentId), new Pair("reply_primary_id", replyPrimaryId), new Pair("picture", this$0.imageUrl), new Pair(ClientCookie.COMMENT_ATTR, string), new Pair("review_type", "3")));
            } else {
                this$0.editComment(MapsKt__MapsKt.hashMapOf(new Pair("id", commentId), new Pair("picture", this$0.imageUrl), new Pair(ClientCookie.COMMENT_ATTR, string)));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$5(Builder this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.localImagePath = "";
            this$0.imageUrl = "";
            FrameLayout flImage = this$0.getFlImage();
            if (flImage != null) {
                ViewExtensionsKt.invisible(flImage);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$7(final Builder this$0, final Context context, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(context, "$context");
            if (!this$0.hasRequiredPermissions(context)) {
                if (Build.VERSION.SDK_INT >= 33) {
                    Activity activity = this$0.getActivity();
                    Intrinsics.checkNotNull(activity);
                    ActivityCompat.requestPermissions(activity, new String[]{Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_MEDIA_IMAGES, Permission.CAMERA}, R2.attr.barHeight);
                } else {
                    Activity activity2 = this$0.getActivity();
                    Intrinsics.checkNotNull(activity2);
                    ActivityCompat.requestPermissions(activity2, new String[]{Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE, Permission.CAMERA}, R2.attr.barHeight);
                }
            }
            AndroidImagePicker.getInstance().setSelectLimit(1);
            AndroidImagePicker.getInstance().pickMulti((Activity) context, true, new AndroidImagePicker.OnImagePickCompleteListener() { // from class: com.ykb.ebook.dialog.a
                @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImagePickCompleteListener
                public final void onImagePickComplete(List list) throws SecurityException {
                    AddReviewCommentDialog.Builder.lambda$7$lambda$6(context, this$0, list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$8(Builder this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            KeyboardUtils.showSoftInput(this$0.getMEtContent());
        }

        private final void addComment(Map<String, String> params) {
            Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new AddReviewCommentDialog$Builder$addComment$1(params, null), 15, null), null, new AddReviewCommentDialog$Builder$addComment$2(this, null), 1, null), null, new AddReviewCommentDialog$Builder$addComment$3(null), 1, null);
        }

        private final void editComment(Map<String, String> params) {
            Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new AddReviewCommentDialog$Builder$editComment$1(params, null), 15, null), null, new AddReviewCommentDialog$Builder$editComment$2(this, null), 1, null), null, new AddReviewCommentDialog$Builder$editComment$3(null), 1, null);
        }

        private final FrameLayout getFlImage() {
            return (FrameLayout) this.flImage.getValue();
        }

        private final LinearLayout getLyContent() {
            return (LinearLayout) this.lyContent.getValue();
        }

        private final LinearLayout getLyView() {
            return (LinearLayout) this.lyView.getValue();
        }

        private final ImageView getMBtnAddPic() {
            return (ImageView) this.mBtnAddPic.getValue();
        }

        private final ImageView getMBtnDelPic() {
            return (ImageView) this.mBtnDelPic.getValue();
        }

        private final RTextView getMBtnPublish() {
            return (RTextView) this.mBtnPublish.getValue();
        }

        private final ImageView getMBtnScale() {
            return (ImageView) this.mBtnScale.getValue();
        }

        private final REditText getMEtContent() {
            return (REditText) this.mEtContent.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final RImageView getMImgShowPic() {
            return (RImageView) this.mImgShowPic.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final ProgressBar getProcessView() {
            return (ProgressBar) this.processView.getValue();
        }

        private final TextView getTvCancel() {
            return (TextView) this.tvCancel.getValue();
        }

        private final TextView getTvPub() {
            return (TextView) this.tvPub.getValue();
        }

        private final TextView getTvTtitle() {
            return (TextView) this.tvTtitle.getValue();
        }

        private final View getViewLine() {
            return (View) this.viewLine.getValue();
        }

        private final boolean hasRequiredPermissions(Context context) {
            if (Build.VERSION.SDK_INT >= 33) {
                if (ContextCompat.checkSelfPermission(context, Permission.CAMERA) == 0 && ContextCompat.checkSelfPermission(context, Permission.READ_MEDIA_IMAGES) == 0) {
                    return true;
                }
            } else if (ContextCompat.checkSelfPermission(context, Permission.CAMERA) == 0 && ContextCompat.checkSelfPermission(context, Permission.READ_EXTERNAL_STORAGE) == 0) {
                return true;
            }
            return false;
        }

        private final void initTheme() throws SecurityException {
            RTextViewHelper helper;
            int colorMode = ReadConfig.INSTANCE.getColorMode();
            if (colorMode == 0) {
                LinearLayout lyView = getLyView();
                if (lyView != null) {
                    lyView.setBackground(getDrawable(R.drawable.shape_white_top_16_bg));
                }
                TextView tvTtitle = getTvTtitle();
                if (tvTtitle != null) {
                    tvTtitle.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_303030));
                }
                View viewLine = getViewLine();
                if (viewLine != null) {
                    viewLine.setBackground(new ColorDrawable(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_eeeeee)));
                }
                REditText mEtContent = getMEtContent();
                RTextViewHelper helper2 = mEtContent != null ? mEtContent.getHelper() : null;
                if (helper2 != null) {
                    helper2.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_f7f8fa));
                }
                REditText mEtContent2 = getMEtContent();
                if (mEtContent2 != null) {
                    mEtContent2.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_303030));
                }
                REditText mEtContent3 = getMEtContent();
                if (mEtContent3 != null) {
                    mEtContent3.setHintTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_bfbfbf));
                }
                ImageView mBtnScale = getMBtnScale();
                if (mBtnScale != null) {
                    mBtnScale.setImageResource(R.drawable.icon_zoom);
                }
                ImageView mBtnAddPic = getMBtnAddPic();
                if (mBtnAddPic != null) {
                    mBtnAddPic.setImageResource(R.drawable.icon_select_img);
                }
                RTextView mBtnPublish = getMBtnPublish();
                helper = mBtnPublish != null ? mBtnPublish.getHelper() : null;
                if (helper != null) {
                    helper.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_dd594a));
                }
                ImageView mBtnDelPic = getMBtnDelPic();
                if (mBtnDelPic != null) {
                    mBtnDelPic.setImageResource(R.drawable.icon_clean_select_light);
                }
                TextView tvCancel = getTvCancel();
                if (tvCancel != null) {
                    tvCancel.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_bfbfbf));
                }
                TextView tvPub = getTvPub();
                if (tvPub != null) {
                    tvPub.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_dd594a));
                    return;
                }
                return;
            }
            if (colorMode == 1) {
                LinearLayout lyView2 = getLyView();
                if (lyView2 != null) {
                    lyView2.setBackground(getDrawable(R.drawable.shape_yellow_top_16_bg));
                }
                TextView tvTtitle2 = getTvTtitle();
                if (tvTtitle2 != null) {
                    tvTtitle2.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_303030));
                }
                View viewLine2 = getViewLine();
                if (viewLine2 != null) {
                    viewLine2.setBackground(new ColorDrawable(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_EDE2C3)));
                }
                REditText mEtContent4 = getMEtContent();
                RTextViewHelper helper3 = mEtContent4 != null ? mEtContent4.getHelper() : null;
                if (helper3 != null) {
                    helper3.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_EDE2C3));
                }
                REditText mEtContent5 = getMEtContent();
                if (mEtContent5 != null) {
                    mEtContent5.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_303030));
                }
                REditText mEtContent6 = getMEtContent();
                if (mEtContent6 != null) {
                    mEtContent6.setHintTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_bfbfbf));
                }
                ImageView mBtnScale2 = getMBtnScale();
                if (mBtnScale2 != null) {
                    mBtnScale2.setImageResource(R.drawable.icon_zoom);
                }
                ImageView mBtnAddPic2 = getMBtnAddPic();
                if (mBtnAddPic2 != null) {
                    mBtnAddPic2.setImageResource(R.drawable.icon_select_img);
                }
                RTextView mBtnPublish2 = getMBtnPublish();
                helper = mBtnPublish2 != null ? mBtnPublish2.getHelper() : null;
                if (helper != null) {
                    helper.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_dd594a));
                }
                ImageView mBtnDelPic2 = getMBtnDelPic();
                if (mBtnDelPic2 != null) {
                    mBtnDelPic2.setImageResource(R.drawable.icon_clean_select_light);
                }
                TextView tvCancel2 = getTvCancel();
                if (tvCancel2 != null) {
                    tvCancel2.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_bfbfbf));
                }
                TextView tvPub2 = getTvPub();
                if (tvPub2 != null) {
                    tvPub2.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_dd594a));
                    return;
                }
                return;
            }
            if (colorMode != 2) {
                return;
            }
            LinearLayout lyView3 = getLyView();
            if (lyView3 != null) {
                lyView3.setBackground(getDrawable(R.drawable.shape_blue_top_16_bg));
            }
            TextView tvTtitle3 = getTvTtitle();
            if (tvTtitle3 != null) {
                tvTtitle3.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_7380a9));
            }
            View viewLine3 = getViewLine();
            if (viewLine3 != null) {
                viewLine3.setBackground(new ColorDrawable(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_171C2D)));
            }
            REditText mEtContent7 = getMEtContent();
            RTextViewHelper helper4 = mEtContent7 != null ? mEtContent7.getHelper() : null;
            if (helper4 != null) {
                helper4.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_171C2D));
            }
            REditText mEtContent8 = getMEtContent();
            if (mEtContent8 != null) {
                mEtContent8.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_575F79));
            }
            REditText mEtContent9 = getMEtContent();
            if (mEtContent9 != null) {
                mEtContent9.setHintTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_575F79));
            }
            ImageView mBtnScale3 = getMBtnScale();
            if (mBtnScale3 != null) {
                mBtnScale3.setImageResource(R.drawable.icon_zoom_night);
            }
            ImageView mBtnAddPic3 = getMBtnAddPic();
            if (mBtnAddPic3 != null) {
                mBtnAddPic3.setImageResource(R.drawable.icon_select_img_night);
            }
            RTextView mBtnPublish3 = getMBtnPublish();
            helper = mBtnPublish3 != null ? mBtnPublish3.getHelper() : null;
            if (helper != null) {
                helper.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_B2575C));
            }
            ImageView mBtnDelPic3 = getMBtnDelPic();
            if (mBtnDelPic3 != null) {
                mBtnDelPic3.setImageResource(R.drawable.icon_clean_select);
            }
            TextView tvCancel3 = getTvCancel();
            if (tvCancel3 != null) {
                tvCancel3.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_575F79));
            }
            TextView tvPub3 = getTvPub();
            if (tvPub3 != null) {
                tvPub3.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_B2575C));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void lambda$7$lambda$6(Context context, final Builder this$0, List list) throws SecurityException {
            Intrinsics.checkNotNullParameter(context, "$context");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (list == null || list.size() <= 0) {
                FrameLayout flImage = this$0.getFlImage();
                if (flImage != null) {
                    flImage.setVisibility(8);
                }
                ProgressBar processView = this$0.getProcessView();
                if (processView != null) {
                    processView.setVisibility(8);
                    return;
                }
                return;
            }
            String imgUrl = ((ImageItem) list.get(0)).path;
            if (FileUtils.INSTANCE.getImageSize(imgUrl) > 20.0f) {
                Toast.makeText(context, "请选择小于10M的图片上传", 0).show();
                return;
            }
            Intrinsics.checkNotNullExpressionValue(imgUrl, "imgUrl");
            this$0.uploadFileComment(imgUrl);
            this$0.urlList.clear();
            List<String> list2 = this$0.urlList;
            String str = ((ImageItem) list.get(0)).path;
            Intrinsics.checkNotNullExpressionValue(str, "items[0].path");
            list2.add(str);
            ProgressBar processView2 = this$0.getProcessView();
            if (processView2 != null) {
                processView2.setVisibility(0);
            }
            FrameLayout flImage2 = this$0.getFlImage();
            if (flImage2 != null) {
                flImage2.setVisibility(0);
            }
            Glide.with((Activity) context).load(((ImageItem) list.get(0)).path).apply((BaseRequestOptions<?>) new RequestOptions().override(Integer.MIN_VALUE)).into((RequestBuilder<Drawable>) new CustomTarget<Drawable>() { // from class: com.ykb.ebook.dialog.AddReviewCommentDialog$Builder$7$1$1
                @Override // com.bumptech.glide.request.target.Target
                public void onLoadCleared(@Nullable Drawable placeholder) {
                }

                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(Object obj, Transition transition) {
                    onResourceReady((Drawable) obj, (Transition<? super Drawable>) transition);
                }

                public void onResourceReady(@NotNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    Intrinsics.checkNotNullParameter(resource, "resource");
                    RImageView mImgShowPic = this.this$0.getMImgShowPic();
                    if (mImgShowPic != null) {
                        mImgShowPic.setImageDrawable(resource);
                    }
                }
            });
            RTextView mBtnPublish = this$0.getMBtnPublish();
            RTextViewHelper helper = mBtnPublish != null ? mBtnPublish.getHelper() : null;
            if (helper == null) {
                return;
            }
            helper.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), ReadConfig.INSTANCE.getColorMode() == 2 ? R.color.color_B2575C : R.color.color_dd594a));
        }

        private final void uploadFileComment(String filePath) {
            this.uploadingImg = true;
            File file = new File(filePath);
            Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new AddReviewCommentDialog$Builder$uploadFileComment$1(MultipartBody.Part.INSTANCE.createFormData("file", file.getName(), RequestBody.INSTANCE.create(file, MediaType.INSTANCE.parse(MimeTypes.IMAGE_ALL))), null), 15, null), null, new AddReviewCommentDialog$Builder$uploadFileComment$2(this, null), 1, null), null, new AddReviewCommentDialog$Builder$uploadFileComment$3(this, null), 1, null);
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.Builder
        public void dismiss() {
            super.dismiss();
            EventBus.getDefault().unregister(this);
        }

        @Nullable
        public final REditText getEditText() {
            return getMEtContent();
        }

        public final boolean getShowKeyborad() {
            return this.showKeyborad;
        }

        @Subscribe
        public final void onMessageEvent(@NotNull ImageUploadSuccessEvent e2) {
            Intrinsics.checkNotNullParameter(e2, "e");
            this.imageUrl = e2.getImgUrl();
            FrameLayout flImage = getFlImage();
            if (flImage != null) {
                ViewExtensionsKt.visible(flImage);
            }
            RequestBuilder<Drawable> requestBuilderLoad = Glide.with(getContext()).load(this.imageUrl);
            RImageView mImgShowPic = getMImgShowPic();
            Intrinsics.checkNotNull(mImgShowPic);
            requestBuilderLoad.into(mImgShowPic);
        }

        @NotNull
        public final Builder setOnPublishSuccess(@NotNull Function0<Unit> onPublishSuccess) {
            Intrinsics.checkNotNullParameter(onPublishSuccess, "onPublishSuccess");
            this.onPublishSuccess = onPublishSuccess;
            return this;
        }

        @NotNull
        public final Builder setSelectImageCallBack(@NotNull Function0<Unit> cb) {
            Intrinsics.checkNotNullParameter(cb, "cb");
            this.selectImageCallBack = cb;
            return this;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull final Activity activity, @NotNull final Context context, @NotNull final String bookId, @NotNull final String chapterId, @NotNull final String paragraphId, @NotNull final String commentId, @NotNull final String paragraphContent, @NotNull String title, @NotNull final String commentStr, @NotNull String picture, boolean z2, @NotNull final String replyPrimaryId) throws SecurityException {
            REditText mEtContent;
            super(context);
            Intrinsics.checkNotNullParameter(activity, "activity");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(bookId, "bookId");
            Intrinsics.checkNotNullParameter(chapterId, "chapterId");
            Intrinsics.checkNotNullParameter(paragraphId, "paragraphId");
            Intrinsics.checkNotNullParameter(commentId, "commentId");
            Intrinsics.checkNotNullParameter(paragraphContent, "paragraphContent");
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(commentStr, "commentStr");
            Intrinsics.checkNotNullParameter(picture, "picture");
            Intrinsics.checkNotNullParameter(replyPrimaryId, "replyPrimaryId");
            this.showKeyborad = z2;
            this.imageUrl = "";
            this.tvTtitle = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.AddReviewCommentDialog$Builder$tvTtitle$2
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
            this.lyView = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.AddReviewCommentDialog$Builder$lyView$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final LinearLayout invoke() {
                    return (LinearLayout) this.this$0.findViewById(R.id.ly_view);
                }
            });
            this.lyContent = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.AddReviewCommentDialog$Builder$lyContent$2
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
            this.mBtnScale = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.AddReviewCommentDialog$Builder$mBtnScale$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ImageView invoke() {
                    return (ImageView) this.this$0.findViewById(R.id.btn_scale);
                }
            });
            this.mEtContent = LazyKt__LazyJVMKt.lazy(new Function0<REditText>() { // from class: com.ykb.ebook.dialog.AddReviewCommentDialog$Builder$mEtContent$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final REditText invoke() {
                    return (REditText) this.this$0.findViewById(R.id.et_content);
                }
            });
            this.mImgShowPic = LazyKt__LazyJVMKt.lazy(new Function0<RImageView>() { // from class: com.ykb.ebook.dialog.AddReviewCommentDialog$Builder$mImgShowPic$2
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
            this.mBtnDelPic = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.AddReviewCommentDialog$Builder$mBtnDelPic$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ImageView invoke() {
                    return (ImageView) this.this$0.findViewById(R.id.btn_del_pic);
                }
            });
            this.mBtnAddPic = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.AddReviewCommentDialog$Builder$mBtnAddPic$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ImageView invoke() {
                    return (ImageView) this.this$0.findViewById(R.id.btn_add_pic);
                }
            });
            this.mBtnPublish = LazyKt__LazyJVMKt.lazy(new Function0<RTextView>() { // from class: com.ykb.ebook.dialog.AddReviewCommentDialog$Builder$mBtnPublish$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RTextView invoke() {
                    return (RTextView) this.this$0.findViewById(R.id.btn_publish);
                }
            });
            this.tvCancel = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.AddReviewCommentDialog$Builder$tvCancel$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_cancel);
                }
            });
            this.tvPub = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.AddReviewCommentDialog$Builder$tvPub$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_publish_f);
                }
            });
            this.flImage = LazyKt__LazyJVMKt.lazy(new Function0<FrameLayout>() { // from class: com.ykb.ebook.dialog.AddReviewCommentDialog$Builder$flImage$2
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
            this.viewLine = LazyKt__LazyJVMKt.lazy(new Function0<View>() { // from class: com.ykb.ebook.dialog.AddReviewCommentDialog$Builder$viewLine$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final View invoke() {
                    return this.this$0.findViewById(R.id.view_line);
                }
            });
            this.processView = LazyKt__LazyJVMKt.lazy(new Function0<ProgressBar>() { // from class: com.ykb.ebook.dialog.AddReviewCommentDialog$Builder$processView$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ProgressBar invoke() {
                    return (ProgressBar) this.this$0.findViewById(R.id.processView);
                }
            });
            this.urlList = new ArrayList();
            this.localImagePath = "";
            this.s_img = "";
            EventBus.getDefault().register(this);
            setContentView(R.layout.dialog_write_dp);
            ProgressBar processView = getProcessView();
            if (processView != null) {
                processView.setProgressDrawable(getDrawable(R.drawable.progressbar));
            }
            setGravity(80);
            setAnimStyle(AnimAction.INSTANCE.getANIM_BOTTOM());
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            TextView tvCancel = getTvCancel();
            if (tvCancel != null) {
                tvCancel.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.b
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) throws SecurityException {
                        AddReviewCommentDialog.Builder._init_$lambda$0(this.f26302c, context, view);
                    }
                });
            }
            TextView tvPub = getTvPub();
            if (tvPub != null) {
                tvPub.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.c
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AddReviewCommentDialog.Builder._init_$lambda$1(this.f26307c, view);
                    }
                });
            }
            TextView tvTtitle = getTvTtitle();
            if (tvTtitle != null) {
                tvTtitle.setText(title);
            }
            if (!Intrinsics.areEqual(commentStr, "") && (mEtContent = getMEtContent()) != null) {
                mEtContent.setText(commentStr);
            }
            if (picture.length() > 0) {
                FrameLayout flImage = getFlImage();
                if (flImage != null) {
                    flImage.setVisibility(0);
                }
                ProgressBar processView2 = getProcessView();
                if (processView2 != null) {
                    processView2.setVisibility(8);
                }
                this.imageUrl = picture;
                RImageView mImgShowPic = getMImgShowPic();
                if (mImgShowPic != null) {
                    ImageLoader.INSTANCE.load(context, picture).into(mImgShowPic);
                }
            }
            ImageView mBtnScale = getMBtnScale();
            if (mBtnScale != null) {
                mBtnScale.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.d
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) throws SecurityException {
                        AddReviewCommentDialog.Builder._init_$lambda$3(this.f26310c, activity, view);
                    }
                });
            }
            RTextView mBtnPublish = getMBtnPublish();
            if (mBtnPublish != null) {
                mBtnPublish.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.e
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AddReviewCommentDialog.Builder._init_$lambda$4(paragraphContent, this, commentStr, commentId, bookId, chapterId, paragraphId, replyPrimaryId, view);
                    }
                });
            }
            ImageView mBtnDelPic = getMBtnDelPic();
            if (mBtnDelPic != null) {
                mBtnDelPic.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.f
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AddReviewCommentDialog.Builder._init_$lambda$5(this.f26328c, view);
                    }
                });
            }
            ImageView mBtnAddPic = getMBtnAddPic();
            if (mBtnAddPic != null) {
                mBtnAddPic.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.g
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AddReviewCommentDialog.Builder._init_$lambda$7(this.f26331c, context, view);
                    }
                });
            }
            initTheme();
            REditText mEtContent2 = getMEtContent();
            if (mEtContent2 != null) {
                mEtContent2.requestFocus();
            }
            if (z2) {
                new Handler().postDelayed(new Runnable() { // from class: com.ykb.ebook.dialog.h
                    @Override // java.lang.Runnable
                    public final void run() {
                        AddReviewCommentDialog.Builder._init_$lambda$8(this.f26338c);
                    }
                }, 200L);
            }
            LinearLayout lyView = getLyView();
            if (lyView == null) {
                return;
            }
            lyView.setBackground(BitmapUtils.INSTANCE.getTopRadiusDrawabl(getContext(), 16));
        }
    }
}

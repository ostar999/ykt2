package com.ykb.ebook.dialog;

import android.app.Activity;
import android.content.AppCtxKt;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
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
import androidx.activity.result.ActivityResultLauncher;
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
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.dialog.TextOperationDialog;
import com.ykb.ebook.dialog.WriteSpDialog;
import com.ykb.ebook.event.ImageUploadSuccessEvent;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.util.BitmapUtils;
import com.ykb.ebook.util.Coroutine;
import com.ykb.ebook.util.FileUtils;
import com.ykb.ebook.util.ImageLoader;
import com.ykb.ebook.util.ScreenUtil;
import com.ykb.ebook.util.ToastUtilsKt;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/WriteSpDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class WriteSpDialog {

    @Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001Be\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\b\u0012\b\b\u0002\u0010\u000b\u001a\u00020\b\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0010¢\u0006\u0002\u0010\u0013J\b\u0010V\u001a\u00020;H\u0016J\u0010\u0010W\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\b\u0010X\u001a\u00020;H\u0002J\u0010\u0010Y\u001a\u00020;2\u0006\u0010Z\u001a\u00020RH\u0016J\u0010\u0010[\u001a\u00020;2\u0006\u0010\\\u001a\u00020]H\u0007J&\u0010^\u001a\u00020\u00002\u001e\u00109\u001a\u001a\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020;0:J\u0010\u0010_\u001a\u00020;2\u0006\u0010`\u001a\u00020\bH\u0002R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0014\u001a\u0004\u0018\u00010\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u0016\u0010\u0017R\u001d\u0010\u001a\u001a\u0004\u0018\u00010\u001b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u0019\u001a\u0004\b\u001c\u0010\u001dR\u001d\u0010\u001f\u001a\u0004\u0018\u00010 8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\u0019\u001a\u0004\b!\u0010\"R\u001d\u0010$\u001a\u0004\u0018\u00010%8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b(\u0010\u0019\u001a\u0004\b&\u0010'R\u001d\u0010)\u001a\u0004\u0018\u00010%8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b+\u0010\u0019\u001a\u0004\b*\u0010'R\u001d\u0010,\u001a\u0004\u0018\u00010%8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b.\u0010\u0019\u001a\u0004\b-\u0010'R\u000e\u0010/\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u00100\u001a\u0004\u0018\u0001018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b4\u0010\u0019\u001a\u0004\b2\u00103R\u000e\u00105\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u00106\u001a\u0004\u0018\u0001018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b8\u0010\u0019\u001a\u0004\b7\u00103R(\u00109\u001a\u001c\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020;\u0018\u00010:X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010<\u001a\u0004\u0018\u00010=8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b@\u0010\u0019\u001a\u0004\b>\u0010?R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010A\u001a\u0004\u0018\u00010B8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bE\u0010\u0019\u001a\u0004\bC\u0010DR\u001d\u0010F\u001a\u0004\u0018\u00010G8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bJ\u0010\u0019\u001a\u0004\bH\u0010IR\u001d\u0010K\u001a\u0004\u0018\u00010B8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bM\u0010\u0019\u001a\u0004\bL\u0010DR\u000e\u0010N\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010O\u001a\b\u0012\u0004\u0012\u00020\b0PX\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010Q\u001a\u0004\u0018\u00010R8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bU\u0010\u0019\u001a\u0004\bS\u0010T¨\u0006a"}, d2 = {"Lcom/ykb/ebook/dialog/WriteSpDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", "Lcom/ykb/ebook/dialog/TextOperationDialog$Builder;", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "parentId", "", "title", "commentStr", "picture", "albumLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "showKeyborad", "", "showFullScreenBtn", "showLine", "(Landroid/app/Activity;Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroidx/activity/result/ActivityResultLauncher;ZZZ)V", "etInput", "Lcom/ruffian/library/widget/REditText;", "getEtInput", "()Lcom/ruffian/library/widget/REditText;", "etInput$delegate", "Lkotlin/Lazy;", "flImage", "Landroid/widget/FrameLayout;", "getFlImage", "()Landroid/widget/FrameLayout;", "flImage$delegate", "imgAlbum", "Lcom/ruffian/library/widget/RImageView;", "getImgAlbum", "()Lcom/ruffian/library/widget/RImageView;", "imgAlbum$delegate", "imgChoose", "Landroid/widget/ImageView;", "getImgChoose", "()Landroid/widget/ImageView;", "imgChoose$delegate", "imgDelete", "getImgDelete", "imgDelete$delegate", "imgFullScreen", "getImgFullScreen", "imgFullScreen$delegate", "isScaleBig", "layoutRoot", "Landroid/widget/LinearLayout;", "getLayoutRoot", "()Landroid/widget/LinearLayout;", "layoutRoot$delegate", "localImagePath", "lyContent", "getLyContent", "lyContent$delegate", "onPublishClick", "Lkotlin/Function3;", "", "processView", "Landroid/widget/ProgressBar;", "getProcessView", "()Landroid/widget/ProgressBar;", "processView$delegate", "tvPub", "Landroid/widget/TextView;", "getTvPub", "()Landroid/widget/TextView;", "tvPub$delegate", "tvPublish", "Lcom/ruffian/library/widget/RTextView;", "getTvPublish", "()Lcom/ruffian/library/widget/RTextView;", "tvPublish$delegate", "tvTitle", "getTvTitle", "tvTitle$delegate", "uploadingImg", "urlList", "", "viewLine1", "Landroid/view/View;", "getViewLine1", "()Landroid/view/View;", "viewLine1$delegate", "dismiss", "hasRequiredPermissions", "initTheme", "onClick", "view", "onMessageEvent", AliyunLogKey.KEY_EVENT, "Lcom/ykb/ebook/event/ImageUploadSuccessEvent;", "setOnPublishClick", "uploadFileComment", TbsReaderView.KEY_FILE_PATH, "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nWriteSpDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WriteSpDialog.kt\ncom/ykb/ebook/dialog/WriteSpDialog$Builder\n+ 2 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 Background.kt\nsplitties/views/BackgroundKt\n*L\n1#1,372:1\n42#2:373\n42#2:375\n42#2:379\n1#3:374\n32#4:376\n32#4:377\n32#4:378\n*S KotlinDebug\n*F\n+ 1 WriteSpDialog.kt\ncom/ykb/ebook/dialog/WriteSpDialog$Builder\n*L\n119#1:373\n184#1:375\n266#1:379\n340#1:376\n352#1:377\n362#1:378\n*E\n"})
    public static final class Builder extends BasicDialog.Builder<TextOperationDialog.Builder> {

        @NotNull
        private final ActivityResultLauncher<Intent> albumLauncher;

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

        @Nullable
        private Function3<? super String, ? super String, ? super String, Unit> onPublishClick;

        /* renamed from: processView$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy processView;
        private final boolean showFullScreenBtn;
        private final boolean showKeyborad;
        private final boolean showLine;

        /* renamed from: tvPub$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvPub;

        /* renamed from: tvPublish$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvPublish;

        /* renamed from: tvTitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvTitle;
        private boolean uploadingImg;

        @NotNull
        private final List<String> urlList;

        /* renamed from: viewLine1$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy viewLine1;

        public /* synthetic */ Builder(Activity activity, Context context, String str, String str2, String str3, String str4, ActivityResultLauncher activityResultLauncher, boolean z2, boolean z3, boolean z4, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(activity, context, str, str2, (i2 & 16) != 0 ? "" : str3, (i2 & 32) != 0 ? "" : str4, activityResultLauncher, (i2 & 128) != 0 ? true : z2, (i2 & 256) != 0 ? true : z3, (i2 & 512) != 0 ? true : z4);
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
        public static final void _init_$lambda$1(Builder this$0, Context context, View it) {
            Window window;
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(context, "$context");
            BasicDialog dialog = this$0.getDialog();
            if (dialog != null && (window = dialog.getWindow()) != null) {
                window.setLayout(-1, -2);
            }
            int pxByDp = ScreenUtil.getPxByDp(context, R2.attr.actionSheetPadding);
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
        public static final void _init_$lambda$3(Builder this$0, Activity activity, View it) {
            Window window;
            Window window2;
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(activity, "$activity");
            this$0.isScaleBig = !this$0.isScaleBig;
            BasicDialog dialog = this$0.getDialog();
            if (dialog != null && (window2 = dialog.getWindow()) != null) {
                window2.setLayout(-1, ScreenUtil.getScreenHeight(this$0.getActivity()));
            }
            BasicDialog dialog2 = this$0.getDialog();
            if (dialog2 != null && (window = dialog2.getWindow()) != null) {
                window.setSoftInputMode(48);
            }
            int screenHeight = ScreenUtil.getScreenHeight(activity);
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
        public static final void _init_$lambda$4(Builder this$0, String parentId, View view) {
            Editable text;
            String string;
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(parentId, "$parentId");
            REditText etInput = this$0.getEtInput();
            String string2 = (etInput == null || (text = etInput.getText()) == null || (string = text.toString()) == null) ? null : StringsKt__StringsKt.trim((CharSequence) string).toString();
            if (string2 == null || string2.length() == 0) {
                ToastUtilsKt.toastOnUi$default(this$0.getContext(), "请输入您的感受！", 0, 2, (Object) null);
                return;
            }
            if (this$0.uploadingImg) {
                ToastUtilsKt.toastOnUi$default(this$0.getContext(), "上传中", 0, 2, (Object) null);
                return;
            }
            this$0.dismiss();
            Function3<? super String, ? super String, ? super String, Unit> function3 = this$0.onPublishClick;
            if (function3 != null) {
                String str = this$0.localImagePath;
                if (string2 == null) {
                    string2 = "";
                }
                function3.invoke(str, string2, parentId);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$5(Builder this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            KeyboardUtils.showSoftInput(this$0.getEtInput());
        }

        private final REditText getEtInput() {
            return (REditText) this.etInput.getValue();
        }

        private final FrameLayout getFlImage() {
            return (FrameLayout) this.flImage.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final RImageView getImgAlbum() {
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

        /* JADX INFO: Access modifiers changed from: private */
        public final ProgressBar getProcessView() {
            return (ProgressBar) this.processView.getValue();
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
                TextView tvTitle = getTvTitle();
                if (tvTitle != null) {
                    tvTitle.setTextColor(getColor(R.color.color_303030));
                }
                View viewLine1 = getViewLine1();
                if (viewLine1 != null) {
                    viewLine1.setBackgroundColor(getColor(R.color.color_eeeeee));
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
                    viewLine12.setBackgroundColor(getColor(R.color.color_eeeeee));
                }
                REditText etInput4 = getEtInput();
                helper = etInput4 != null ? etInput4.getHelper() : null;
                if (helper != null) {
                    helper.setBackgroundColorNormal(getColor(R.color.color_EDE2C3));
                }
                REditText etInput5 = getEtInput();
                if (etInput5 != null) {
                    etInput5.setTextColor(getColor(R.color.color_303030));
                }
                REditText etInput6 = getEtInput();
                if (etInput6 != null) {
                    etInput6.setHintTextColor(getColor(R.color.color_bfbfbf));
                }
            } else if (colorMode == 2) {
                TextView tvTitle3 = getTvTitle();
                if (tvTitle3 != null) {
                    tvTitle3.setTextColor(getColor(R.color.color_7380a9));
                }
                View viewLine13 = getViewLine1();
                if (viewLine13 != null) {
                    viewLine13.setBackgroundColor(getColor(R.color.color_theme_blue_line_color));
                }
                REditText etInput7 = getEtInput();
                helper = etInput7 != null ? etInput7.getHelper() : null;
                if (helper != null) {
                    helper.setBackgroundColorNormal(getColor(R.color.color_171C2D));
                }
                REditText etInput8 = getEtInput();
                if (etInput8 != null) {
                    etInput8.setTextColor(getColor(R.color.color_7380a9));
                }
                REditText etInput9 = getEtInput();
                if (etInput9 != null) {
                    etInput9.setHintTextColor(getColor(R.color.color_575F79));
                }
            }
            LinearLayout layoutRoot = getLayoutRoot();
            if (layoutRoot == null) {
                return;
            }
            layoutRoot.setBackground(BitmapUtils.INSTANCE.getTopRadiusDrawabl(getContext(), 16));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onClick$lambda$6(final Builder this$0, List list) throws SecurityException {
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
                Toast.makeText(this$0.getActivity(), "请选择小于10M的图片上传", 0).show();
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
            Activity activity = this$0.getActivity();
            Intrinsics.checkNotNull(activity);
            Glide.with(activity).load(((ImageItem) list.get(0)).path).apply((BaseRequestOptions<?>) new RequestOptions().override(Integer.MIN_VALUE)).into((RequestBuilder<Drawable>) new CustomTarget<Drawable>() { // from class: com.ykb.ebook.dialog.WriteSpDialog$Builder$onClick$1$1
                @Override // com.bumptech.glide.request.target.Target
                public void onLoadCleared(@Nullable Drawable placeholder) {
                }

                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(Object obj, Transition transition) {
                    onResourceReady((Drawable) obj, (Transition<? super Drawable>) transition);
                }

                public void onResourceReady(@NotNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    Intrinsics.checkNotNullParameter(resource, "resource");
                    RImageView imgAlbum = this.this$0.getImgAlbum();
                    if (imgAlbum != null) {
                        imgAlbum.setImageDrawable(resource);
                    }
                }
            });
            RTextView tvPublish = this$0.getTvPublish();
            RTextViewHelper helper = tvPublish != null ? tvPublish.getHelper() : null;
            if (helper == null) {
                return;
            }
            helper.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), ReadConfig.INSTANCE.getColorMode() == 2 ? R.color.color_B2575C : R.color.color_fd4018));
        }

        private final void uploadFileComment(String filePath) {
            this.uploadingImg = true;
            File file = new File(filePath);
            Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new WriteSpDialog$Builder$uploadFileComment$1(MultipartBody.Part.INSTANCE.createFormData("file", file.getName(), RequestBody.INSTANCE.create(file, MediaType.INSTANCE.parse(MimeTypes.IMAGE_ALL))), null), 15, null), null, new WriteSpDialog$Builder$uploadFileComment$2(this, null), 1, null), null, new WriteSpDialog$Builder$uploadFileComment$3(this, null), 1, null);
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
                if (!hasRequiredPermissions(getContext())) {
                    if (Build.VERSION.SDK_INT >= 33) {
                        Activity activity = getActivity();
                        Intrinsics.checkNotNull(activity);
                        ActivityCompat.requestPermissions(activity, new String[]{Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_MEDIA_IMAGES, Permission.CAMERA}, R2.attr.barHeight);
                    } else {
                        Activity activity2 = getActivity();
                        Intrinsics.checkNotNull(activity2);
                        ActivityCompat.requestPermissions(activity2, new String[]{Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE, Permission.CAMERA}, R2.attr.barHeight);
                    }
                }
                AndroidImagePicker.getInstance().setSelectLimit(1);
                AndroidImagePicker.getInstance().pickMulti(getActivity(), true, new AndroidImagePicker.OnImagePickCompleteListener() { // from class: com.ykb.ebook.dialog.s1
                    @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImagePickCompleteListener
                    public final void onImagePickComplete(List list) throws SecurityException {
                        WriteSpDialog.Builder.onClick$lambda$6(this.f26387a, list);
                    }
                });
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull final Activity activity, @NotNull final Context context, @NotNull final String parentId, @NotNull String title, @NotNull String commentStr, @NotNull String picture, @NotNull ActivityResultLauncher<Intent> albumLauncher, boolean z2, boolean z3, boolean z4) throws SecurityException {
            super(context);
            Intrinsics.checkNotNullParameter(activity, "activity");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(parentId, "parentId");
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(commentStr, "commentStr");
            Intrinsics.checkNotNullParameter(picture, "picture");
            Intrinsics.checkNotNullParameter(albumLauncher, "albumLauncher");
            this.albumLauncher = albumLauncher;
            this.showKeyborad = z2;
            this.showFullScreenBtn = z3;
            this.showLine = z4;
            this.layoutRoot = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.WriteSpDialog$Builder$layoutRoot$2
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
            this.tvTitle = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.WriteSpDialog$Builder$tvTitle$2
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
            this.tvPub = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.WriteSpDialog$Builder$tvPub$2
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
            this.lyContent = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.WriteSpDialog$Builder$lyContent$2
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
            this.imgFullScreen = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.WriteSpDialog$Builder$imgFullScreen$2
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
            this.imgDelete = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.WriteSpDialog$Builder$imgDelete$2
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
            this.etInput = LazyKt__LazyJVMKt.lazy(new Function0<REditText>() { // from class: com.ykb.ebook.dialog.WriteSpDialog$Builder$etInput$2
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
            this.flImage = LazyKt__LazyJVMKt.lazy(new Function0<FrameLayout>() { // from class: com.ykb.ebook.dialog.WriteSpDialog$Builder$flImage$2
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
            this.imgAlbum = LazyKt__LazyJVMKt.lazy(new Function0<RImageView>() { // from class: com.ykb.ebook.dialog.WriteSpDialog$Builder$imgAlbum$2
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
            this.imgChoose = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.WriteSpDialog$Builder$imgChoose$2
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
            this.tvPublish = LazyKt__LazyJVMKt.lazy(new Function0<RTextView>() { // from class: com.ykb.ebook.dialog.WriteSpDialog$Builder$tvPublish$2
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
            this.viewLine1 = LazyKt__LazyJVMKt.lazy(new Function0<View>() { // from class: com.ykb.ebook.dialog.WriteSpDialog$Builder$viewLine1$2
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
            this.processView = LazyKt__LazyJVMKt.lazy(new Function0<ProgressBar>() { // from class: com.ykb.ebook.dialog.WriteSpDialog$Builder$processView$2
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
            EventBus.getDefault().register(this);
            setContentView(R.layout.dialog_write_sp);
            ProgressBar processView = getProcessView();
            if (processView != null) {
                processView.setProgressDrawable(getDrawable(R.drawable.progressbar));
            }
            setAnimStyle(AnimAction.INSTANCE.getANIM_BOTTOM());
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            setGravity(80);
            setOnClickListener(getImgDelete(), getImgChoose());
            TextView tvPub = getTvPub();
            if (tvPub != null) {
                tvPub.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.n1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        WriteSpDialog.Builder._init_$lambda$0(this.f26365c, view);
                    }
                });
            }
            if (z4) {
                View viewLine1 = getViewLine1();
                if (viewLine1 != null) {
                    ViewExtensionsKt.visible(viewLine1);
                }
            } else {
                View viewLine12 = getViewLine1();
                if (viewLine12 != null) {
                    ViewExtensionsKt.gone(viewLine12);
                }
            }
            if (z3) {
                ImageView imgFullScreen = getImgFullScreen();
                if (imgFullScreen != null) {
                    ViewExtensionsKt.visible(imgFullScreen);
                }
            } else {
                ImageView imgFullScreen2 = getImgFullScreen();
                if (imgFullScreen2 != null) {
                    ViewExtensionsKt.gone(imgFullScreen2);
                }
            }
            ReadConfig readConfig = ReadConfig.INSTANCE;
            if (readConfig.getColorMode() == 2) {
                TextView tvPub2 = getTvPub();
                if (tvPub2 != null) {
                    tvPub2.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_B2575C));
                }
                ImageView imgFullScreen3 = getImgFullScreen();
                if (imgFullScreen3 != null) {
                    imgFullScreen3.setImageResource(R.mipmap.ic_write_note_scale);
                }
            }
            View viewFindViewById = findViewById(R.id.tv_cancel);
            if (viewFindViewById != null) {
                viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.o1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        WriteSpDialog.Builder._init_$lambda$1(this.f26370c, context, view);
                    }
                });
            }
            TextView tvTitle = getTvTitle();
            if (tvTitle != null) {
                tvTitle.setText(title);
            }
            if (!Intrinsics.areEqual(commentStr, "")) {
                REditText etInput = getEtInput();
                if (etInput != null) {
                    etInput.setText(commentStr);
                }
                REditText etInput2 = getEtInput();
                if (etInput2 != null) {
                    etInput2.setSelection(commentStr.length());
                }
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
                this.localImagePath = picture;
                RImageView imgAlbum = getImgAlbum();
                if (imgAlbum != null) {
                    ImageLoader.INSTANCE.load(context, picture).into(imgAlbum);
                }
            }
            ImageView imgFullScreen4 = getImgFullScreen();
            if (imgFullScreen4 != null) {
                imgFullScreen4.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.p1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        WriteSpDialog.Builder._init_$lambda$3(this.f26374c, activity, view);
                    }
                });
            }
            RTextView tvPublish = getTvPublish();
            if (tvPublish != null) {
                tvPublish.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.q1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        WriteSpDialog.Builder._init_$lambda$4(this.f26380c, parentId, view);
                    }
                });
            }
            REditText etInput3 = getEtInput();
            if (etInput3 != null) {
                etInput3.requestFocus();
            }
            if (readConfig.getColorMode() == 2) {
                RTextView tvPublish2 = getTvPublish();
                RTextViewHelper helper = tvPublish2 != null ? tvPublish2.getHelper() : null;
                if (helper != null) {
                    helper.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_B2575C));
                }
            }
            if (z2) {
                new Handler().postDelayed(new Runnable() { // from class: com.ykb.ebook.dialog.r1
                    @Override // java.lang.Runnable
                    public final void run() {
                        WriteSpDialog.Builder._init_$lambda$5(this.f26384c);
                    }
                }, 200L);
            }
            initTheme();
        }
    }
}

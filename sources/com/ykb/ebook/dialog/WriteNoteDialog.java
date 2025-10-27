package com.ykb.ebook.dialog;

import android.content.AppCtxKt;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.util.ColorResourcesKt;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lxj.xpopup.util.KeyboardUtils;
import com.ruffian.library.widget.REditText;
import com.ruffian.library.widget.RTextView;
import com.ruffian.library.widget.helper.RTextViewHelper;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.dialog.NotePermissionDialogNew;
import com.ykb.ebook.dialog.WriteNoteDialog;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.util.BitmapUtils;
import com.ykb.ebook.util.ScreenUtil;
import com.ykb.ebook.util.ToastUtilsKt;
import com.ykb.ebook.weight.RoundCornerDrawable;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/WriteNoteDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class WriteNoteDialog {

    @Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010C\u001a\u00020&H\u0002J\b\u0010D\u001a\u00020&H\u0002J\u0010\u0010E\u001a\u00020&2\u0006\u0010F\u001a\u00020GH\u0016J\u000e\u0010H\u001a\u00020\u00002\u0006\u0010I\u001a\u00020)J\u000e\u0010J\u001a\u00020\u00002\u0006\u0010K\u001a\u00020)J \u0010L\u001a\u00020\u00002\u0018\u0010'\u001a\u0014\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020&0(R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\t\u001a\u0004\u0018\u00010\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\u000f\u001a\u0004\u0018\u00010\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0011\u0010\u0012R\u001d\u0010\u0014\u001a\u0004\u0018\u00010\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u000e\u001a\u0004\b\u0015\u0010\u0012R\u001d\u0010\u0017\u001a\u0004\u0018\u00010\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u000e\u001a\u0004\b\u0018\u0010\u0012R\u001d\u0010\u001a\u001a\u0004\u0018\u00010\u001b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u000e\u001a\u0004\b\u001c\u0010\u001dR\u001d\u0010\u001f\u001a\u0004\u0018\u00010 8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\u000e\u001a\u0004\b!\u0010\"R\u0016\u0010$\u001a\n\u0012\u0004\u0012\u00020&\u0018\u00010%X\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010'\u001a\u0016\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020&\u0018\u00010(X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u001d\u0010-\u001a\u0004\u0018\u00010.8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b1\u0010\u000e\u001a\u0004\b/\u00100R\u001d\u00102\u001a\u0004\u0018\u00010.8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b4\u0010\u000e\u001a\u0004\b3\u00100R\u001d\u00105\u001a\u0004\u0018\u0001068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b9\u0010\u000e\u001a\u0004\b7\u00108R\u001d\u0010:\u001a\u0004\u0018\u00010.8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b<\u0010\u000e\u001a\u0004\b;\u00100R\u001d\u0010=\u001a\u0004\u0018\u0001068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b?\u0010\u000e\u001a\u0004\b>\u00108R\u001d\u0010@\u001a\u0004\u0018\u00010.8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bB\u0010\u000e\u001a\u0004\bA\u00100¨\u0006M"}, d2 = {"Lcom/ykb/ebook/dialog/WriteNoteDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "displayStatus", "", "showKeyborad", "", "(Landroid/content/Context;IZ)V", "etInput", "Lcom/ruffian/library/widget/REditText;", "getEtInput", "()Lcom/ruffian/library/widget/REditText;", "etInput$delegate", "Lkotlin/Lazy;", "imgFullScreen", "Landroid/widget/ImageView;", "getImgFullScreen", "()Landroid/widget/ImageView;", "imgFullScreen$delegate", "imgWriteNoteHintLeft", "getImgWriteNoteHintLeft", "imgWriteNoteHintLeft$delegate", "imgWriteNoteHintRight", "getImgWriteNoteHintRight", "imgWriteNoteHintRight$delegate", "layoutWrite", "Landroid/widget/RelativeLayout;", "getLayoutWrite", "()Landroid/widget/RelativeLayout;", "layoutWrite$delegate", "llRoot", "Landroid/widget/LinearLayout;", "getLlRoot", "()Landroid/widget/LinearLayout;", "llRoot$delegate", "onFullScreenClick", "Lkotlin/Function0;", "", "onPublishClick", "Lkotlin/Function2;", "", "permission", "getShowKeyborad", "()Z", "tvCancel", "Landroid/widget/TextView;", "getTvCancel", "()Landroid/widget/TextView;", "tvCancel$delegate", "tvCitation", "getTvCitation", "tvCitation$delegate", "tvPermission", "Lcom/ruffian/library/widget/RTextView;", "getTvPermission", "()Lcom/ruffian/library/widget/RTextView;", "tvPermission$delegate", "tvPub", "getTvPub", "tvPub$delegate", "tvPublish", "getTvPublish", "tvPublish$delegate", "tvWriteNoteHint", "getTvWriteNoteHint", "tvWriteNoteHint$delegate", "enterFullScreen", "exitFullScreen", "onClick", "view", "Landroid/view/View;", "setCitation", "citation", "setEditContent", "content", "setPublishClick", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nWriteNoteDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WriteNoteDialog.kt\ncom/ykb/ebook/dialog/WriteNoteDialog$Builder\n+ 2 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n*L\n1#1,266:1\n42#2:267\n42#2:268\n42#2:269\n42#2:270\n42#2:271\n42#2:272\n42#2:273\n42#2:274\n42#2:275\n42#2:276\n42#2:277\n42#2:278\n42#2:279\n42#2:280\n42#2:281\n42#2:282\n42#2:283\n42#2:284\n42#2:285\n42#2:286\n42#2:287\n42#2:288\n42#2:289\n42#2:290\n42#2:291\n*S KotlinDebug\n*F\n+ 1 WriteNoteDialog.kt\ncom/ykb/ebook/dialog/WriteNoteDialog$Builder\n*L\n65#1:267\n68#1:268\n81#1:269\n109#1:270\n112#1:271\n114#1:272\n115#1:273\n116#1:274\n117#1:275\n118#1:276\n119#1:277\n120#1:278\n121#1:279\n122#1:280\n123#1:281\n124#1:282\n125#1:283\n129#1:284\n130#1:285\n132#1:286\n133#1:287\n134#1:288\n135#1:289\n136#1:290\n140#1:291\n*E\n"})
    public static final class Builder extends BasicDialog.Builder<Builder> {
        private final int displayStatus;

        /* renamed from: etInput$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy etInput;

        /* renamed from: imgFullScreen$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgFullScreen;

        /* renamed from: imgWriteNoteHintLeft$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgWriteNoteHintLeft;

        /* renamed from: imgWriteNoteHintRight$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgWriteNoteHintRight;

        /* renamed from: layoutWrite$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy layoutWrite;

        /* renamed from: llRoot$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy llRoot;

        @Nullable
        private Function0<Unit> onFullScreenClick;

        @Nullable
        private Function2<? super String, ? super Integer, Unit> onPublishClick;
        private int permission;
        private final boolean showKeyborad;

        /* renamed from: tvCancel$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvCancel;

        /* renamed from: tvCitation$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvCitation;

        /* renamed from: tvPermission$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvPermission;

        /* renamed from: tvPub$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvPub;

        /* renamed from: tvPublish$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvPublish;

        /* renamed from: tvWriteNoteHint$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvWriteNoteHint;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull final Context context, int i2, boolean z2) throws SecurityException {
            RTextViewHelper helper;
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            this.displayStatus = i2;
            this.showKeyborad = z2;
            this.imgFullScreen = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.WriteNoteDialog$Builder$imgFullScreen$2
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
            this.tvPublish = LazyKt__LazyJVMKt.lazy(new Function0<RTextView>() { // from class: com.ykb.ebook.dialog.WriteNoteDialog$Builder$tvPublish$2
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
            this.tvPermission = LazyKt__LazyJVMKt.lazy(new Function0<RTextView>() { // from class: com.ykb.ebook.dialog.WriteNoteDialog$Builder$tvPermission$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RTextView invoke() {
                    return (RTextView) this.this$0.findViewById(R.id.tv_permission);
                }
            });
            this.tvCitation = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.WriteNoteDialog$Builder$tvCitation$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_citation);
                }
            });
            this.tvCancel = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.WriteNoteDialog$Builder$tvCancel$2
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
            this.tvPub = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.WriteNoteDialog$Builder$tvPub$2
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
            this.etInput = LazyKt__LazyJVMKt.lazy(new Function0<REditText>() { // from class: com.ykb.ebook.dialog.WriteNoteDialog$Builder$etInput$2
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
            this.llRoot = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.WriteNoteDialog$Builder$llRoot$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final LinearLayout invoke() {
                    return (LinearLayout) this.this$0.findViewById(R.id.ll_root);
                }
            });
            this.layoutWrite = LazyKt__LazyJVMKt.lazy(new Function0<RelativeLayout>() { // from class: com.ykb.ebook.dialog.WriteNoteDialog$Builder$layoutWrite$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RelativeLayout invoke() {
                    return (RelativeLayout) this.this$0.findViewById(R.id.layoutWirte);
                }
            });
            this.imgWriteNoteHintLeft = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.WriteNoteDialog$Builder$imgWriteNoteHintLeft$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ImageView invoke() {
                    return (ImageView) this.this$0.findViewById(R.id.imgWirteNoteHintLeft);
                }
            });
            this.imgWriteNoteHintRight = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.WriteNoteDialog$Builder$imgWriteNoteHintRight$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ImageView invoke() {
                    return (ImageView) this.this$0.findViewById(R.id.imgWirteNoteHintRight);
                }
            });
            this.tvWriteNoteHint = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.WriteNoteDialog$Builder$tvWriteNoteHint$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tvWirteNoteHint);
                }
            });
            this.permission = i2;
            setContentView(R.layout.dialog_write_notes);
            setAnimStyle(AnimAction.INSTANCE.getANIM_BOTTOM());
            setGravity(80);
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            setOnClickListener(getImgFullScreen(), getTvPublish(), getTvPermission(), getTvCancel(), getTvPub(), getLayoutWrite());
            ReadConfig readConfig = ReadConfig.INSTANCE;
            if (readConfig.getColorMode() == 2) {
                TextView tvPub = getTvPub();
                if (tvPub != null) {
                    tvPub.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_B2575C));
                }
                ImageView imgFullScreen = getImgFullScreen();
                if (imgFullScreen != null) {
                    imgFullScreen.setImageResource(R.mipmap.ic_write_note_scale);
                }
                Drawable drawable = getDrawable(R.drawable.icon_write_notes_more);
                if (drawable != null) {
                    drawable.setColorFilter(new PorterDuffColorFilter(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_7380a9), PorterDuff.Mode.SRC_IN));
                }
                ImageView imgWriteNoteHintRight = getImgWriteNoteHintRight();
                if (imgWriteNoteHintRight != null) {
                    imgWriteNoteHintRight.setImageDrawable(drawable);
                }
            }
            Drawable drawable2 = getDrawable(i2 != 1 ? i2 != 2 ? i2 != 3 ? R.drawable.icon_write_shield : R.drawable.icon_write_private : R.drawable.icon_write_attention : R.drawable.icon_write_open);
            if (drawable2 != null) {
                if (readConfig.getColorMode() == 2) {
                    drawable2.setColorFilter(new PorterDuffColorFilter(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_7380a9), PorterDuff.Mode.SRC_IN));
                }
                ImageView imgWriteNoteHintLeft = getImgWriteNoteHintLeft();
                if (imgWriteNoteHintLeft != null) {
                    imgWriteNoteHintLeft.setImageDrawable(drawable2);
                }
            }
            if (z2) {
                new Handler().postDelayed(new Runnable() { // from class: com.ykb.ebook.dialog.k1
                    @Override // java.lang.Runnable
                    public final void run() {
                        WriteNoteDialog.Builder._init_$lambda$2(this.f26351c);
                    }
                }, 200L);
            }
            RTextView tvPermission = getTvPermission();
            String str = "屏蔽好友";
            if (tvPermission != null) {
                tvPermission.setText(i2 != 1 ? i2 != 2 ? i2 != 3 ? "屏蔽好友" : "私密" : "关注" : "公开");
            }
            TextView tvWriteNoteHint = getTvWriteNoteHint();
            if (tvWriteNoteHint != null) {
                if (i2 == 1) {
                    str = "公开";
                } else if (i2 == 2) {
                    str = "关注";
                } else if (i2 == 3) {
                    str = "私密";
                }
                tvWriteNoteHint.setText(str);
            }
            int i3 = R.id.v_line;
            View viewFindViewById = findViewById(i3);
            if (viewFindViewById != null) {
                ViewExtensionsKt.visible(viewFindViewById);
            }
            int i4 = R.id.et_input;
            REditText rEditText = (REditText) findViewById(i4);
            if (rEditText != null) {
                rEditText.setHintTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_bfbfbf));
            }
            RelativeLayout layoutWrite = getLayoutWrite();
            if (layoutWrite != null) {
                layoutWrite.setBackground(new RoundCornerDrawable(getColor(R.color.color_f7f8fa), 100));
            }
            if (readConfig.getColorMode() == 2) {
                TextView tvWriteNoteHint2 = getTvWriteNoteHint();
                if (tvWriteNoteHint2 != null) {
                    tvWriteNoteHint2.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_606A8A));
                }
                RelativeLayout layoutWrite2 = getLayoutWrite();
                if (layoutWrite2 != null) {
                    layoutWrite2.setBackground(new RoundCornerDrawable(getColor(R.color.color_171C2D), 100));
                }
                TextView tvCancel = getTvCancel();
                if (tvCancel != null) {
                    tvCancel.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_575F79));
                }
                View viewFindViewById2 = findViewById(i3);
                if (viewFindViewById2 != null) {
                    viewFindViewById2.setBackground(new ColorDrawable(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_171C2D)));
                }
                View viewFindViewById3 = findViewById(R.id.line_q);
                if (viewFindViewById3 != null) {
                    viewFindViewById3.setBackground(new ColorDrawable(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_575F79)));
                }
                TextView textView = (TextView) findViewById(R.id.title);
                if (textView != null) {
                    textView.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_7380a9));
                }
                TextView textView2 = (TextView) findViewById(R.id.tv_citation);
                if (textView2 != null) {
                    textView2.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_575F79));
                }
                ViewGroup viewGroup = (ViewGroup) findViewById(R.id.ll_root);
                if (viewGroup != null) {
                    viewGroup.setBackground(new ColorDrawable(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_121622)));
                }
                RTextView tvPublish = getTvPublish();
                RTextViewHelper helper2 = tvPublish != null ? tvPublish.getHelper() : null;
                if (helper2 != null) {
                    helper2.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_B2575C));
                }
                int i5 = R.id.tv_permission;
                RTextView rTextView = (RTextView) findViewById(i5);
                RTextViewHelper helper3 = rTextView != null ? rTextView.getHelper() : null;
                if (helper3 != null) {
                    helper3.setBackgroundDrawableNormal(new ColorDrawable(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_171C2D)));
                }
                RTextView rTextView2 = (RTextView) findViewById(i5);
                if (rTextView2 != null) {
                    rTextView2.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_606060));
                }
                REditText rEditText2 = (REditText) findViewById(i4);
                helper = rEditText2 != null ? rEditText2.getHelper() : null;
                if (helper != null) {
                    helper.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_171C2D));
                }
                REditText rEditText3 = (REditText) findViewById(i4);
                if (rEditText3 != null) {
                    rEditText3.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_575F79));
                }
                ViewGroup viewGroup2 = (ViewGroup) findViewById(R.id.ll_content);
                if (viewGroup2 != null) {
                    viewGroup2.setBackground(new ColorDrawable(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_121622)));
                }
            } else if (readConfig.getColorMode() == 1) {
                RelativeLayout layoutWrite3 = getLayoutWrite();
                if (layoutWrite3 != null) {
                    layoutWrite3.setBackground(new RoundCornerDrawable(getColor(R.color.color_EDE2C3), 100));
                }
                TextView tvCancel2 = getTvCancel();
                if (tvCancel2 != null) {
                    tvCancel2.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_bfbfbf));
                }
                View viewFindViewById4 = findViewById(i3);
                if (viewFindViewById4 != null) {
                    viewFindViewById4.setBackground(new ColorDrawable(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_EDE2C3)));
                }
                ViewGroup viewGroup3 = (ViewGroup) findViewById(R.id.ll_root);
                if (viewGroup3 != null) {
                    viewGroup3.setBackground(new ColorDrawable(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_F5EBCE)));
                }
                int i6 = R.id.tv_permission;
                RTextView rTextView3 = (RTextView) findViewById(i6);
                RTextViewHelper helper4 = rTextView3 != null ? rTextView3.getHelper() : null;
                if (helper4 != null) {
                    helper4.setBackgroundDrawableNormal(new ColorDrawable(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_EDE2C3)));
                }
                RTextView rTextView4 = (RTextView) findViewById(i6);
                if (rTextView4 != null) {
                    rTextView4.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_606060));
                }
                REditText rEditText4 = (REditText) findViewById(i4);
                helper = rEditText4 != null ? rEditText4.getHelper() : null;
                if (helper != null) {
                    helper.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_EDE2C3));
                }
                ViewGroup viewGroup4 = (ViewGroup) findViewById(R.id.ll_content);
                if (viewGroup4 != null) {
                    viewGroup4.setBackground(new ColorDrawable(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_F5EBCE)));
                }
            }
            REditText etInput = getEtInput();
            if (etInput != null) {
                etInput.requestFocus();
            }
            REditText etInput2 = getEtInput();
            if (etInput2 != null) {
                etInput2.setHintTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() != 2 ? R.color.color_bfbfbf : R.color.color_575F79));
            }
            REditText etInput3 = getEtInput();
            if (etInput3 != null) {
                etInput3.postDelayed(new Runnable() { // from class: com.ykb.ebook.dialog.l1
                    @Override // java.lang.Runnable
                    public final void run() {
                        WriteNoteDialog.Builder._init_$lambda$3(context, this);
                    }
                }, 200L);
            }
            LinearLayout llRoot = getLlRoot();
            if (llRoot == null) {
                return;
            }
            llRoot.setBackground(BitmapUtils.INSTANCE.getTopRadiusDrawabl(getContext(), 16));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$2(Builder this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            KeyboardUtils.showSoftInput(this$0.getEtInput());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$3(Context context, Builder this$0) {
            Intrinsics.checkNotNullParameter(context, "$context");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Object systemService = context.getSystemService("input_method");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
            ((InputMethodManager) systemService).showSoftInput(this$0.getEtInput(), 2);
        }

        private final void enterFullScreen() {
            TextView tvCancel = getTvCancel();
            if (tvCancel != null) {
                ViewExtensionsKt.visible(tvCancel);
            }
            TextView tvPub = getTvPub();
            if (tvPub != null) {
                ViewExtensionsKt.visible(tvPub);
            }
            ImageView imgFullScreen = getImgFullScreen();
            if (imgFullScreen != null) {
                ViewExtensionsKt.gone(imgFullScreen);
            }
            ImageView imgFullScreen2 = getImgFullScreen();
            if (imgFullScreen2 != null) {
                imgFullScreen2.postDelayed(new Runnable() { // from class: com.ykb.ebook.dialog.j1
                    @Override // java.lang.Runnable
                    public final void run() {
                        WriteNoteDialog.Builder.enterFullScreen$lambda$4(this.f26347c);
                    }
                }, 200L);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void enterFullScreen$lambda$4(Builder this$0) {
            Window window;
            Window window2;
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            BasicDialog dialog = this$0.getDialog();
            if (dialog != null && (window2 = dialog.getWindow()) != null) {
                window2.setLayout(-1, ScreenUtil.getScreenHeight(this$0.getActivity()));
            }
            BasicDialog dialog2 = this$0.getDialog();
            if (dialog2 != null && (window = dialog2.getWindow()) != null) {
                window.setSoftInputMode(48);
            }
            int screenHeight = ScreenUtil.getScreenHeight(this$0.getActivity());
            LinearLayout llRoot = this$0.getLlRoot();
            Intrinsics.checkNotNull(llRoot);
            ViewGroup.LayoutParams layoutParams = llRoot.getLayoutParams();
            layoutParams.height = screenHeight;
            LinearLayout llRoot2 = this$0.getLlRoot();
            Intrinsics.checkNotNull(llRoot2);
            llRoot2.setLayoutParams(layoutParams);
            LinearLayout llRoot3 = this$0.getLlRoot();
            if (llRoot3 == null) {
                return;
            }
            llRoot3.setBackground(BitmapUtils.INSTANCE.getTopRadiusDrawabl(this$0.getContext(), 0));
        }

        private final void exitFullScreen() {
            TextView tvCancel = getTvCancel();
            if (tvCancel != null) {
                ViewExtensionsKt.gone(tvCancel);
            }
            TextView tvPub = getTvPub();
            if (tvPub != null) {
                ViewExtensionsKt.gone(tvPub);
            }
            ImageView imgFullScreen = getImgFullScreen();
            if (imgFullScreen != null) {
                ViewExtensionsKt.visible(imgFullScreen);
            }
            ImageView imgFullScreen2 = getImgFullScreen();
            if (imgFullScreen2 != null) {
                imgFullScreen2.postDelayed(new Runnable() { // from class: com.ykb.ebook.dialog.m1
                    @Override // java.lang.Runnable
                    public final void run() {
                        WriteNoteDialog.Builder.exitFullScreen$lambda$5(this.f26359c);
                    }
                }, 200L);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void exitFullScreen$lambda$5(Builder this$0) {
            Window window;
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            REditText etInput = this$0.getEtInput();
            ViewGroup.LayoutParams layoutParams = etInput != null ? etInput.getLayoutParams() : null;
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
            layoutParams2.height = ScreenUtil.getPxByDp(this$0.getContext(), 140);
            REditText etInput2 = this$0.getEtInput();
            if (etInput2 != null) {
                etInput2.setLayoutParams(layoutParams2);
            }
            this$0.setGravity(80);
            LinearLayout llRoot = this$0.getLlRoot();
            ViewGroup.LayoutParams layoutParams3 = llRoot != null ? llRoot.getLayoutParams() : null;
            if (layoutParams3 != null) {
                layoutParams3.height = -2;
            }
            LinearLayout llRoot2 = this$0.getLlRoot();
            if (llRoot2 != null) {
                llRoot2.setLayoutParams(layoutParams3);
            }
            BasicDialog dialog = this$0.getDialog();
            if (dialog != null && (window = dialog.getWindow()) != null) {
                window.setLayout(-1, -2);
            }
            LinearLayout llRoot3 = this$0.getLlRoot();
            if (llRoot3 == null) {
                return;
            }
            llRoot3.setBackground(BitmapUtils.INSTANCE.getTopRadiusDrawabl(this$0.getContext(), 16));
        }

        private final REditText getEtInput() {
            return (REditText) this.etInput.getValue();
        }

        private final ImageView getImgFullScreen() {
            return (ImageView) this.imgFullScreen.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final ImageView getImgWriteNoteHintLeft() {
            return (ImageView) this.imgWriteNoteHintLeft.getValue();
        }

        private final ImageView getImgWriteNoteHintRight() {
            return (ImageView) this.imgWriteNoteHintRight.getValue();
        }

        private final RelativeLayout getLayoutWrite() {
            return (RelativeLayout) this.layoutWrite.getValue();
        }

        private final LinearLayout getLlRoot() {
            return (LinearLayout) this.llRoot.getValue();
        }

        private final TextView getTvCancel() {
            return (TextView) this.tvCancel.getValue();
        }

        private final TextView getTvCitation() {
            return (TextView) this.tvCitation.getValue();
        }

        private final RTextView getTvPermission() {
            return (RTextView) this.tvPermission.getValue();
        }

        private final TextView getTvPub() {
            return (TextView) this.tvPub.getValue();
        }

        private final RTextView getTvPublish() {
            return (RTextView) this.tvPublish.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final TextView getTvWriteNoteHint() {
            return (TextView) this.tvWriteNoteHint.getValue();
        }

        public final boolean getShowKeyborad() {
            return this.showKeyborad;
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.Builder, com.ykb.ebook.common_interface.ClickAction, android.view.View.OnClickListener
        public void onClick(@NotNull View view) {
            String string;
            Editable text;
            String string2;
            Intrinsics.checkNotNullParameter(view, "view");
            if (Intrinsics.areEqual(view, getImgFullScreen())) {
                enterFullScreen();
                return;
            }
            if (Intrinsics.areEqual(view, getTvCancel())) {
                exitFullScreen();
                return;
            }
            if (!(Intrinsics.areEqual(view, getTvPub()) ? true : Intrinsics.areEqual(view, getTvPublish()))) {
                if (Intrinsics.areEqual(view, getLayoutWrite())) {
                    KeyboardUtils.hideSoftInput(getEtInput());
                    new NotePermissionDialogNew.Builder(getContext(), this.permission).setPermissionCallback(new Function2<Integer, String, Unit>() { // from class: com.ykb.ebook.dialog.WriteNoteDialog$Builder$onClick$1
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(Integer num, String str) {
                            invoke(num.intValue(), str);
                            return Unit.INSTANCE;
                        }

                        public final void invoke(int i2, @NotNull String title) {
                            Intrinsics.checkNotNullParameter(title, "title");
                            this.this$0.permission = i2;
                            TextView tvWriteNoteHint = this.this$0.getTvWriteNoteHint();
                            if (tvWriteNoteHint != null) {
                                tvWriteNoteHint.setText(title);
                            }
                            Drawable drawable = i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? this.this$0.getDrawable(R.drawable.icon_error_recovery) : this.this$0.getDrawable(R.drawable.icon_write_shield) : this.this$0.getDrawable(R.drawable.icon_write_private) : this.this$0.getDrawable(R.drawable.icon_write_attention) : this.this$0.getDrawable(R.drawable.icon_write_open);
                            if (ReadConfig.INSTANCE.getColorMode() == 2 && drawable != null) {
                                drawable.setColorFilter(new PorterDuffColorFilter(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_7380a9), PorterDuff.Mode.SRC_IN));
                            }
                            ImageView imgWriteNoteHintLeft = this.this$0.getImgWriteNoteHintLeft();
                            if (imgWriteNoteHintLeft != null) {
                                imgWriteNoteHintLeft.setImageDrawable(drawable);
                            }
                        }
                    }).show();
                    return;
                }
                return;
            }
            REditText etInput = getEtInput();
            if (etInput == null || (text = etInput.getText()) == null || (string2 = text.toString()) == null || (string = StringsKt__StringsKt.trim((CharSequence) string2).toString()) == null) {
                string = "";
            }
            if (string.length() == 0) {
                ToastUtilsKt.toastOnUi$default(getContext(), "请输入笔记内容！", 0, 2, (Object) null);
                return;
            }
            dismiss();
            Function2<? super String, ? super Integer, Unit> function2 = this.onPublishClick;
            if (function2 != null) {
                function2.invoke(string, Integer.valueOf(this.permission));
            }
        }

        @NotNull
        public final Builder setCitation(@NotNull String citation) {
            Intrinsics.checkNotNullParameter(citation, "citation");
            TextView tvCitation = getTvCitation();
            if (tvCitation != null) {
                tvCitation.setText("引文：" + StringsKt__StringsKt.trimStart((CharSequence) citation).toString());
            }
            return this;
        }

        @NotNull
        public final Builder setEditContent(@NotNull String content) {
            TextView textView;
            Intrinsics.checkNotNullParameter(content, "content");
            if (!TextUtils.isEmpty(content) && (textView = (TextView) findViewById(R.id.title)) != null) {
                textView.setText("编辑笔记");
            }
            REditText etInput = getEtInput();
            if (etInput != null) {
                etInput.setText(content);
            }
            REditText etInput2 = getEtInput();
            if (etInput2 != null) {
                etInput2.setSelection(content.length());
            }
            return this;
        }

        @NotNull
        public final Builder setPublishClick(@NotNull Function2<? super String, ? super Integer, Unit> onPublishClick) {
            Intrinsics.checkNotNullParameter(onPublishClick, "onPublishClick");
            this.onPublishClick = onPublishClick;
            return this;
        }

        public /* synthetic */ Builder(Context context, int i2, boolean z2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(context, (i3 & 2) != 0 ? 0 : i2, (i3 & 4) != 0 ? true : z2);
        }
    }
}

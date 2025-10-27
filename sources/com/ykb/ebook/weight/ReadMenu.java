package com.ykb.ebook.weight;

import android.annotation.SuppressLint;
import android.content.AppCtxKt;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.ColorResourcesKt;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.KeyEventDispatcher;
import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.mobile.auth.gatewayauth.Constant;
import com.ruffian.library.widget.RCheckBox;
import com.ruffian.library.widget.RTextView;
import com.ruffian.library.widget.helper.RCheckHelper;
import com.ruffian.library.widget.helper.RTextViewHelper;
import com.ykb.ebook.R;
import com.ykb.ebook.common.Data;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.databinding.ViewReadMenuBinding;
import com.ykb.ebook.extensions.AnimationExtensionsKt;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.model.BookInfo;
import com.ykb.ebook.page.ReadBook;
import com.ykb.ebook.util.NumberUtilKt;
import com.ykb.ebook.weight.slider.ITEffect;
import com.ykb.ebook.weight.slider.NiftySlider;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt__MathJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\b\u0003\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u000b*\u0002\u001f\"\u0018\u00002\u00020\u00012\u00020\u0002:\u0001QB\u0019\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\b\u00105\u001a\u00020,H\u0002J\u0010\u00106\u001a\u00020,2\u0006\u00107\u001a\u00020\u000fH\u0002J\b\u00108\u001a\u00020,H\u0002J\u0018\u00109\u001a\u00020,2\u0006\u0010:\u001a\u00020;2\u0006\u00107\u001a\u00020\u000fH\u0016J\u0012\u0010<\u001a\u00020\u000f2\b\u0010=\u001a\u0004\u0018\u00010>H\u0017J\b\u0010?\u001a\u00020,H\u0002J\u0006\u0010@\u001a\u00020,J\u0018\u0010A\u001a\u00020,2\u0010\b\u0002\u0010*\u001a\n\u0012\u0004\u0012\u00020,\u0018\u00010+J\u0006\u0010B\u001a\u00020,J\b\u0010C\u001a\u00020,H\u0002J\b\u0010D\u001a\u00020,H\u0002J\u0010\u0010E\u001a\u00020,2\u0006\u0010F\u001a\u00020GH\u0007J\u000e\u0010H\u001a\u00020,2\u0006\u0010I\u001a\u00020.J\u000e\u0010J\u001a\u00020,2\u0006\u0010/\u001a\u000200J\u000e\u0010K\u001a\u00020,2\u0006\u0010L\u001a\u00020GJ\u0006\u0010M\u001a\u00020,J\u0006\u0010N\u001a\u00020,J\b\u0010O\u001a\u00020,H\u0002J\b\u0010P\u001a\u00020,H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0015\u001a\u00020\u00168BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u0017\u0010\u0018R\u001b\u0010\u001b\u001a\u00020\u00168BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u001a\u001a\u0004\b\u001c\u0010\u0018R\u0010\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010 R\u0010\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0004\n\u0002\u0010#R\u001b\u0010$\u001a\u00020\u00168BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b&\u0010\u001a\u001a\u0004\b%\u0010\u0018R\u001b\u0010'\u001a\u00020\u00168BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b)\u0010\u001a\u001a\u0004\b(\u0010\u0018R\u0016\u0010*\u001a\n\u0012\u0004\u0012\u00020,\u0018\u00010+X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020.X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010/\u001a\u000200X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104¨\u0006R"}, d2 = {"Lcom/ykb/ebook/weight/ReadMenu;", "Landroid/widget/FrameLayout;", "Landroid/widget/CompoundButton$OnCheckedChangeListener;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "binding", "Lcom/ykb/ebook/databinding/ViewReadMenuBinding;", com.alipay.sdk.authjs.a.f3170c, "Lcom/ykb/ebook/weight/ReadMenu$Callback;", "getCallback", "()Lcom/ykb/ebook/weight/ReadMenu$Callback;", "canShowMenu", "", "getCanShowMenu", "()Z", "setCanShowMenu", "(Z)V", "listenerFlag", "menuBottomIn", "Landroid/view/animation/Animation;", "getMenuBottomIn", "()Landroid/view/animation/Animation;", "menuBottomIn$delegate", "Lkotlin/Lazy;", "menuBottomOut", "getMenuBottomOut", "menuBottomOut$delegate", "menuInListener", "com/ykb/ebook/weight/ReadMenu$menuInListener$1", "Lcom/ykb/ebook/weight/ReadMenu$menuInListener$1;", "menuOutListener", "com/ykb/ebook/weight/ReadMenu$menuOutListener$1", "Lcom/ykb/ebook/weight/ReadMenu$menuOutListener$1;", "menuTopIn", "getMenuTopIn", "menuTopIn$delegate", "menuTopOut", "getMenuTopOut", "menuTopOut$delegate", "onMenuOutEnd", "Lkotlin/Function0;", "", "readProgress", "", CrashHianalyticsData.TIME, "", "getTime", "()J", "setTime", "(J)V", "bindEvent", "handleDarkModeSwitch", "isChecked", "initView", "onCheckedChanged", "button", "Landroid/widget/CompoundButton;", "onTouchEvent", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "refreshColorUi", "runMenuIn", "runMenuOut", "setAddLibaaryTv", "setCbColorMode", "setCbNoteColorMode", "setCommentNumber", Constant.LOGIN_ACTIVITY_NUMBER, "", "setProgress", "progress", "setReadTime", "setSeekPage", "seek", "upBookView", "upSeekBar", "updateProgressUi", "updateReadTime", "Callback", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nReadMenu.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReadMenu.kt\ncom/ykb/ebook/weight/ReadMenu\n+ 2 DimenResources.kt\nsplitties/resources/DimenResourcesKt\n+ 3 Background.kt\nsplitties/views/BackgroundKt\n+ 4 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n+ 5 View.kt\nandroidx/core/view/ViewKt\n*L\n1#1,1070:1\n19#2:1071\n19#2:1072\n19#2:1073\n19#2:1074\n19#2:1075\n19#2:1076\n19#2:1077\n19#2:1078\n19#2:1079\n19#2:1080\n19#2:1081\n19#2:1082\n32#3:1083\n32#3:1085\n32#3:1087\n42#4:1084\n42#4:1086\n42#4:1088\n42#4:1090\n42#4:1091\n252#5:1089\n*S KotlinDebug\n*F\n+ 1 ReadMenu.kt\ncom/ykb/ebook/weight/ReadMenu\n*L\n176#1:1071\n177#1:1072\n178#1:1073\n179#1:1074\n196#1:1075\n197#1:1076\n198#1:1077\n199#1:1078\n218#1:1079\n219#1:1080\n220#1:1081\n221#1:1082\n574#1:1083\n650#1:1085\n740#1:1087\n575#1:1084\n651#1:1086\n741#1:1088\n1014#1:1090\n1016#1:1091\n827#1:1089\n*E\n"})
/* loaded from: classes8.dex */
public final class ReadMenu extends FrameLayout implements CompoundButton.OnCheckedChangeListener {

    @NotNull
    private final ViewReadMenuBinding binding;
    private boolean canShowMenu;
    private boolean listenerFlag;

    /* renamed from: menuBottomIn$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy menuBottomIn;

    /* renamed from: menuBottomOut$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy menuBottomOut;

    @NotNull
    private final ReadMenu$menuInListener$1 menuInListener;

    @NotNull
    private final ReadMenu$menuOutListener$1 menuOutListener;

    /* renamed from: menuTopIn$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy menuTopIn;

    /* renamed from: menuTopOut$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy menuTopOut;

    @Nullable
    private Function0<Unit> onMenuOutEnd;

    @NotNull
    private String readProgress;
    private long time;

    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0003H&J\b\u0010\u0006\u001a\u00020\u0003H&J\b\u0010\u0007\u001a\u00020\u0003H&J\b\u0010\b\u001a\u00020\u0003H&J\b\u0010\t\u001a\u00020\u0003H&J\b\u0010\n\u001a\u00020\u0003H&J\b\u0010\u000b\u001a\u00020\u0003H&J\b\u0010\f\u001a\u00020\u0003H&J\b\u0010\r\u001a\u00020\u0003H&J\b\u0010\u000e\u001a\u00020\u0003H&J\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0011H&J\b\u0010\u0012\u001a\u00020\u0003H&J\b\u0010\u0013\u001a\u00020\u0003H&¨\u0006\u0014"}, d2 = {"Lcom/ykb/ebook/weight/ReadMenu$Callback;", "", "backgroundChange", "", "onAddLibraryClick", "onAllTagClick", "onBackClick", "onBookReviewClick", "onChapterListClick", "onDownloadClick", "onMenuHide", "onMenuShow", "onMoreSettingClick", "onShareBookClick", "scrollAnimChange", "skipToChapter", "index", "", "uiConfigChange", "upSystemUiVisibility", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface Callback {
        void backgroundChange();

        void onAddLibraryClick();

        void onAllTagClick();

        void onBackClick();

        void onBookReviewClick();

        void onChapterListClick();

        void onDownloadClick();

        void onMenuHide();

        void onMenuShow();

        void onMoreSettingClick();

        void onShareBookClick();

        void scrollAnimChange();

        void skipToChapter(int index);

        void uiConfigChange();

        void upSystemUiVisibility();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.ykb.ebook.weight.ReadMenu$menuOutListener$1] */
    public ReadMenu(@NotNull final Context context, @Nullable AttributeSet attributeSet) throws SecurityException {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.listenerFlag = true;
        this.readProgress = "";
        ViewReadMenuBinding viewReadMenuBindingInflate = ViewReadMenuBinding.inflate(LayoutInflater.from(context), this, true);
        Intrinsics.checkNotNullExpressionValue(viewReadMenuBindingInflate, "inflate(LayoutInflater.from(context), this, true)");
        this.binding = viewReadMenuBindingInflate;
        this.menuTopIn = LazyKt__LazyJVMKt.lazy(new Function0<Animation>() { // from class: com.ykb.ebook.weight.ReadMenu$menuTopIn$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Animation invoke() {
                return AnimationExtensionsKt.loadAnimation(context, R.anim.anim_readbook_top_in);
            }
        });
        this.menuTopOut = LazyKt__LazyJVMKt.lazy(new Function0<Animation>() { // from class: com.ykb.ebook.weight.ReadMenu$menuTopOut$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Animation invoke() {
                return AnimationExtensionsKt.loadAnimation(context, R.anim.anim_readbook_top_out);
            }
        });
        this.menuBottomIn = LazyKt__LazyJVMKt.lazy(new Function0<Animation>() { // from class: com.ykb.ebook.weight.ReadMenu$menuBottomIn$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Animation invoke() {
                return AnimationExtensionsKt.loadAnimation(context, R.anim.anim_readbook_bottom_in);
            }
        });
        this.menuBottomOut = LazyKt__LazyJVMKt.lazy(new Function0<Animation>() { // from class: com.ykb.ebook.weight.ReadMenu$menuBottomOut$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Animation invoke() {
                return AnimationExtensionsKt.loadAnimation(context, R.anim.anim_readbook_bottom_out);
            }
        });
        this.menuInListener = new ReadMenu$menuInListener$1(this);
        this.menuOutListener = new Animation.AnimationListener() { // from class: com.ykb.ebook.weight.ReadMenu$menuOutListener$1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(@NotNull Animation animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                ViewExtensionsKt.invisible(this.this$0);
                Toolbar toolbar = this.this$0.binding.titleBar;
                Intrinsics.checkNotNullExpressionValue(toolbar, "binding.titleBar");
                ViewExtensionsKt.invisible(toolbar);
                LinearLayout linearLayout = this.this$0.binding.llBottomMenu;
                Intrinsics.checkNotNullExpressionValue(linearLayout, "binding.llBottomMenu");
                ViewExtensionsKt.invisible(linearLayout);
                this.this$0.setCanShowMenu(false);
                Function0 function0 = this.this$0.onMenuOutEnd;
                if (function0 != null) {
                    function0.invoke();
                }
                this.this$0.getCallback().upSystemUiVisibility();
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(@NotNull Animation animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(@NotNull Animation animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                this.this$0.binding.vwMenuBg.setOnClickListener(null);
            }
        };
        initView();
        bindEvent();
    }

    private final void bindEvent() {
        final ViewReadMenuBinding viewReadMenuBinding = this.binding;
        viewReadMenuBinding.vwMenuBg.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.f0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ReadMenu.bindEvent$lambda$18$lambda$7(this.f26480c, view);
            }
        });
        viewReadMenuBinding.imgBack.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.i0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ReadMenu.bindEvent$lambda$18$lambda$8(this.f26492c, view);
            }
        });
        viewReadMenuBinding.tvDownload.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.j0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ReadMenu.bindEvent$lambda$18$lambda$9(this.f26494c, view);
            }
        });
        viewReadMenuBinding.tvAddlibrary.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.k0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ReadMenu.bindEvent$lambda$18$lambda$10(this.f26496c, view);
            }
        });
        viewReadMenuBinding.btnShre.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.l0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ReadMenu.bindEvent$lambda$18$lambda$11(this.f26498c, view);
            }
        });
        viewReadMenuBinding.tvReadRate.setText("");
        setReadTime(0L);
        viewReadMenuBinding.seekReadPage.addOnIntValueChangeListener(new Function3<NiftySlider, Integer, Boolean, Unit>() { // from class: com.ykb.ebook.weight.ReadMenu$bindEvent$1$6
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(NiftySlider niftySlider, Integer num, Boolean bool) {
                invoke(niftySlider, num.intValue(), bool.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(@NotNull NiftySlider niftySlider, int i2, boolean z2) {
                Intrinsics.checkNotNullParameter(niftySlider, "<anonymous parameter 0>");
                ReadBook readBook = ReadBook.INSTANCE;
                Intrinsics.checkNotNull(readBook.getBook());
                double size = ((i2 + 1) / r7.getChapterList().size()) * 100;
                StringBuilder sb = new StringBuilder();
                sb.append((int) size);
                sb.append("% \n ");
                BookInfo book = readBook.getBook();
                Intrinsics.checkNotNull(book);
                sb.append(book.getChapterList().get(i2).getTitle());
                String string = sb.toString();
                viewReadMenuBinding.tvShowProgress.setVisibility(0);
                viewReadMenuBinding.tvShowProgress.setText(string);
            }
        });
        viewReadMenuBinding.seekReadPage.addOnSliderTouchStopListener(new Function1<NiftySlider, Unit>() { // from class: com.ykb.ebook.weight.ReadMenu$bindEvent$1$7
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(NiftySlider niftySlider) {
                invoke2(niftySlider);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull NiftySlider it) {
                Intrinsics.checkNotNullParameter(it, "it");
                this.this$0.getCallback().skipToChapter(MathKt__MathJVMKt.roundToInt(it.getValue()));
                viewReadMenuBinding.tvShowProgress.setVisibility(8);
            }
        });
        RTextView tvPreview = viewReadMenuBinding.tvPreview;
        Intrinsics.checkNotNullExpressionValue(tvPreview, "tvPreview");
        ViewExtensionsKt.clickDelay(tvPreview, new Function0<Unit>() { // from class: com.ykb.ebook.weight.ReadMenu$bindEvent$1$8
            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                ReadBook.moveToPrevChapter$default(ReadBook.INSTANCE, true, false, false, 4, null);
            }
        });
        RTextView tvNext = viewReadMenuBinding.tvNext;
        Intrinsics.checkNotNullExpressionValue(tvNext, "tvNext");
        ViewExtensionsKt.clickDelay(tvNext, new Function0<Unit>() { // from class: com.ykb.ebook.weight.ReadMenu$bindEvent$1$9
            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                ReadBook.moveToNextChapter$default(ReadBook.INSTANCE, true, false, 2, null);
            }
        });
        FrameLayout flBookCatalog = viewReadMenuBinding.flBookCatalog;
        Intrinsics.checkNotNullExpressionValue(flBookCatalog, "flBookCatalog");
        ViewExtensionsKt.clickDelay(flBookCatalog, new Function0<Unit>() { // from class: com.ykb.ebook.weight.ReadMenu$bindEvent$1$10
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                this.this$0.getCallback().onChapterListClick();
                ReadMenu.runMenuOut$default(this.this$0, null, 1, null);
            }
        });
        FrameLayout flBookMark = viewReadMenuBinding.flBookMark;
        Intrinsics.checkNotNullExpressionValue(flBookMark, "flBookMark");
        ViewExtensionsKt.clickDelay(flBookMark, new Function0<Unit>() { // from class: com.ykb.ebook.weight.ReadMenu$bindEvent$1$11
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                this.this$0.getCallback().onAllTagClick();
                ReadMenu.runMenuOut$default(this.this$0, null, 1, null);
            }
        });
        RelativeLayout flBookComment = viewReadMenuBinding.flBookComment;
        Intrinsics.checkNotNullExpressionValue(flBookComment, "flBookComment");
        ViewExtensionsKt.clickDelay(flBookComment, new Function0<Unit>() { // from class: com.ykb.ebook.weight.ReadMenu$bindEvent$1$12
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                this.this$0.getCallback().onBookReviewClick();
                ReadMenu.runMenuOut$default(this.this$0, null, 1, null);
            }
        });
        viewReadMenuBinding.cbDarkMode.setOnCheckedChangeListener(this);
        viewReadMenuBinding.flSetting.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.m0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ReadMenu.bindEvent$lambda$18$lambda$12(this.f26503c, viewReadMenuBinding, view);
            }
        });
        viewReadMenuBinding.bgWhite.setOnCheckedChangeListener(this);
        viewReadMenuBinding.bgYellow.setOnCheckedChangeListener(this);
        viewReadMenuBinding.bgBlue.setOnCheckedChangeListener(this);
        viewReadMenuBinding.tvPageSet.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.n0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ReadMenu.bindEvent$lambda$18$lambda$13(this.f26506c, viewReadMenuBinding, view);
            }
        });
        viewReadMenuBinding.tvMoreSet.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.o0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ReadMenu.bindEvent$lambda$18$lambda$14(this.f26510c, view);
            }
        });
        viewReadMenuBinding.cbPageFz.setOnCheckedChangeListener(this);
        viewReadMenuBinding.cbPageZy.setOnCheckedChangeListener(this);
        viewReadMenuBinding.cbPageSx.setOnCheckedChangeListener(this);
        viewReadMenuBinding.closePageMethod.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.p0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ReadMenu.bindEvent$lambda$18$lambda$15(viewReadMenuBinding, view);
            }
        });
        viewReadMenuBinding.cbReview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ykb.ebook.weight.g0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                ReadMenu.bindEvent$lambda$18$lambda$16(this.f26484c, compoundButton, z2);
            }
        });
        viewReadMenuBinding.cbNote.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ykb.ebook.weight.h0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                ReadMenu.bindEvent$lambda$18$lambda$17(this.f26487c, compoundButton, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindEvent$lambda$18$lambda$10(ReadMenu this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getCallback().onAddLibraryClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindEvent$lambda$18$lambda$11(ReadMenu this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getCallback().onShareBookClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindEvent$lambda$18$lambda$12(ReadMenu this$0, ViewReadMenuBinding this_run, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        if (this$0.binding.llTop.getVisibility() == 0) {
            LinearLayout linearLayout = this$0.binding.llTop;
            Intrinsics.checkNotNullExpressionValue(linearLayout, "binding.llTop");
            ViewExtensionsKt.gone(linearLayout);
            LinearLayout lyPageMethod = this_run.lyPageMethod;
            Intrinsics.checkNotNullExpressionValue(lyPageMethod, "lyPageMethod");
            ViewExtensionsKt.gone(lyPageMethod);
            LinearLayout lySetting = this_run.lySetting;
            Intrinsics.checkNotNullExpressionValue(lySetting, "lySetting");
            ViewExtensionsKt.visible(lySetting);
            return;
        }
        LinearLayout linearLayout2 = this$0.binding.llTop;
        Intrinsics.checkNotNullExpressionValue(linearLayout2, "binding.llTop");
        ViewExtensionsKt.visible(linearLayout2);
        LinearLayout lyPageMethod2 = this_run.lyPageMethod;
        Intrinsics.checkNotNullExpressionValue(lyPageMethod2, "lyPageMethod");
        ViewExtensionsKt.gone(lyPageMethod2);
        LinearLayout lySetting2 = this_run.lySetting;
        Intrinsics.checkNotNullExpressionValue(lySetting2, "lySetting");
        ViewExtensionsKt.gone(lySetting2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindEvent$lambda$18$lambda$13(ReadMenu this$0, ViewReadMenuBinding this_run, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        LinearLayout linearLayout = this$0.binding.llTop;
        Intrinsics.checkNotNullExpressionValue(linearLayout, "binding.llTop");
        ViewExtensionsKt.gone(linearLayout);
        LinearLayout linearLayout2 = this$0.binding.lySetting;
        Intrinsics.checkNotNullExpressionValue(linearLayout2, "binding.lySetting");
        ViewExtensionsKt.gone(linearLayout2);
        LinearLayout lyPageMethod = this_run.lyPageMethod;
        Intrinsics.checkNotNullExpressionValue(lyPageMethod, "lyPageMethod");
        ViewExtensionsKt.visible(lyPageMethod);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindEvent$lambda$18$lambda$14(ReadMenu this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getCallback().onMoreSettingClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindEvent$lambda$18$lambda$15(ViewReadMenuBinding this_run, View view) {
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        LinearLayout llTop = this_run.llTop;
        Intrinsics.checkNotNullExpressionValue(llTop, "llTop");
        ViewExtensionsKt.visible(llTop);
        LinearLayout lyPageMethod = this_run.lyPageMethod;
        Intrinsics.checkNotNullExpressionValue(lyPageMethod, "lyPageMethod");
        ViewExtensionsKt.gone(lyPageMethod);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindEvent$lambda$18$lambda$16(ReadMenu this$0, CompoundButton compoundButton, boolean z2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ReadConfig.INSTANCE.setShowReview(!z2);
        ReadBook.loadContent$default(ReadBook.INSTANCE, false, false, null, 6, null);
        compoundButton.setText(z2 ? "显示段评" : "隐藏段评");
        this$0.setCbColorMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindEvent$lambda$18$lambda$17(ReadMenu this$0, CompoundButton compoundButton, boolean z2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ReadConfig.INSTANCE.setShowNote(!z2);
        ReadBook.loadContent$default(ReadBook.INSTANCE, false, false, null, 6, null);
        compoundButton.setText(z2 ? "显示笔记" : "隐藏笔记");
        this$0.setCbNoteColorMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindEvent$lambda$18$lambda$7(ReadMenu this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        runMenuOut$default(this$0, null, 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindEvent$lambda$18$lambda$8(ReadMenu this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getCallback().onBackClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindEvent$lambda$18$lambda$9(ReadMenu this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getCallback().onDownloadClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Callback getCallback() {
        KeyEventDispatcher.Component activity = ViewExtensionsKt.getActivity(this);
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.ykb.ebook.weight.ReadMenu.Callback");
        return (Callback) activity;
    }

    private final Animation getMenuBottomIn() {
        return (Animation) this.menuBottomIn.getValue();
    }

    private final Animation getMenuBottomOut() {
        return (Animation) this.menuBottomOut.getValue();
    }

    private final Animation getMenuTopIn() {
        return (Animation) this.menuTopIn.getValue();
    }

    private final Animation getMenuTopOut() {
        return (Animation) this.menuTopOut.getValue();
    }

    private final void handleDarkModeSwitch(boolean isChecked) throws SecurityException {
        ReadConfig readConfig = ReadConfig.INSTANCE;
        int colorMode = readConfig.getColorMode();
        if (isChecked) {
            if (colorMode != 2) {
                readConfig.setColorMode(2);
            } else {
                readConfig.setColorMode(readConfig.getColorModePre());
            }
        } else if (readConfig.getColorMode() == 2 || readConfig.getColorMode() != readConfig.getColorModePre()) {
            readConfig.setColorMode(readConfig.getColorModePre());
        }
        this.listenerFlag = false;
        int colorMode2 = readConfig.getColorMode();
        if (colorMode2 == 0) {
            this.binding.bgWhite.setChecked(true);
            this.binding.bgBlue.setChecked(false);
            this.binding.bgYellow.setChecked(false);
        } else if (colorMode2 == 1) {
            this.binding.bgWhite.setChecked(false);
            this.binding.bgBlue.setChecked(false);
            this.binding.bgYellow.setChecked(true);
        } else if (colorMode2 == 2) {
            this.binding.bgWhite.setChecked(false);
            this.binding.bgBlue.setChecked(true);
            this.binding.bgYellow.setChecked(false);
        }
        this.listenerFlag = true;
        refreshColorUi();
        getCallback().backgroundChange();
    }

    private final void initView() throws SecurityException {
        ViewReadMenuBinding viewReadMenuBinding = this.binding;
        getMenuTopIn().setAnimationListener(this.menuInListener);
        getMenuTopOut().setAnimationListener(this.menuOutListener);
        final NiftySlider niftySlider = viewReadMenuBinding.seekFontSize;
        Intrinsics.checkNotNullExpressionValue(niftySlider, "this");
        ITEffect iTEffect = new ITEffect(niftySlider);
        Data data = Data.INSTANCE;
        Map<Integer, Integer> initReadFontSizeMap = data.getInitReadFontSizeMap();
        ReadConfig readConfig = ReadConfig.INSTANCE;
        niftySlider.setValue(initReadFontSizeMap.get(Integer.valueOf(readConfig.getTextSize())) != null ? r5.intValue() : 20);
        niftySlider.setThumbText(String.valueOf(readConfig.getTextSize()));
        iTEffect.setStartText(ExifInterface.GPS_MEASUREMENT_IN_PROGRESS);
        iTEffect.setEndText(ExifInterface.GPS_MEASUREMENT_IN_PROGRESS);
        Context appCtx = AppCtxKt.getAppCtx();
        int i2 = R.dimen.dp_12;
        iTEffect.setStartPadding((int) appCtx.getResources().getDimension(i2));
        iTEffect.setEndPadding((int) AppCtxKt.getAppCtx().getResources().getDimension(i2));
        Context appCtx2 = AppCtxKt.getAppCtx();
        int i3 = R.dimen.sp_10;
        iTEffect.setStartTextSize(appCtx2.getResources().getDimension(i3));
        Context appCtx3 = AppCtxKt.getAppCtx();
        int i4 = R.dimen.sp_125;
        iTEffect.setEndTextSize(appCtx3.getResources().getDimension(i4));
        Context appCtx4 = AppCtxKt.getAppCtx();
        int i5 = R.color.color_909090;
        iTEffect.setStartTintList(ColorStateList.valueOf(appCtx4.getColor(i5)));
        iTEffect.setEndTintList(ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i5)));
        niftySlider.addOnIntValueChangeListener(new Function3<NiftySlider, Integer, Boolean, Unit>() { // from class: com.ykb.ebook.weight.ReadMenu$initView$1$1$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(NiftySlider niftySlider2, Integer num, Boolean bool) {
                invoke(niftySlider2, num.intValue(), bool.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(@NotNull NiftySlider niftySlider2, int i6, boolean z2) {
                Intrinsics.checkNotNullParameter(niftySlider2, "<anonymous parameter 0>");
                Integer num = Data.INSTANCE.getReadFontSizeMap().get(Integer.valueOf(i6));
                niftySlider.setThumbText(String.valueOf(num));
                ReadConfig.INSTANCE.setTextSize(num != null ? num.intValue() : 20);
                this.getCallback().uiConfigChange();
            }
        });
        niftySlider.setEffect(iTEffect);
        NiftySlider niftySlider2 = viewReadMenuBinding.seekPageMargin;
        Intrinsics.checkNotNullExpressionValue(niftySlider2, "this");
        ITEffect iTEffect2 = new ITEffect(niftySlider2);
        niftySlider2.setValue(data.getInitReadFontSizeMap().get(Integer.valueOf(readConfig.getPaddingTop())) != null ? r5.intValue() : 16);
        iTEffect2.setStartText("小");
        iTEffect2.setEndText("大");
        iTEffect2.setStartPadding((int) AppCtxKt.getAppCtx().getResources().getDimension(i2));
        iTEffect2.setEndPadding((int) AppCtxKt.getAppCtx().getResources().getDimension(i2));
        iTEffect2.setStartTextSize(AppCtxKt.getAppCtx().getResources().getDimension(i3));
        iTEffect2.setEndTextSize(AppCtxKt.getAppCtx().getResources().getDimension(i4));
        iTEffect2.setStartTintList(ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i5)));
        iTEffect2.setEndTintList(ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i5)));
        niftySlider2.addOnIntValueChangeListener(new Function3<NiftySlider, Integer, Boolean, Unit>() { // from class: com.ykb.ebook.weight.ReadMenu$initView$1$2$1$1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(NiftySlider niftySlider3, Integer num, Boolean bool) {
                invoke(niftySlider3, num.intValue(), bool.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(@NotNull NiftySlider niftySlider3, int i6, boolean z2) {
                Intrinsics.checkNotNullParameter(niftySlider3, "<anonymous parameter 0>");
                Integer num = Data.INSTANCE.getReadFontSizeMap().get(Integer.valueOf(i6));
                ReadConfig readConfig2 = ReadConfig.INSTANCE;
                readConfig2.setPaddingTop(num != null ? num.intValue() : 16);
                readConfig2.setPaddingLeft(num != null ? num.intValue() : 16);
                readConfig2.setPaddingBottom(num != null ? num.intValue() : 16);
                readConfig2.setPaddingRight(num != null ? num.intValue() : 16);
                this.this$0.getCallback().uiConfigChange();
            }
        });
        niftySlider2.setEffect(iTEffect2);
        NiftySlider niftySlider3 = viewReadMenuBinding.seekPageLine;
        Intrinsics.checkNotNullExpressionValue(niftySlider3, "this");
        ITEffect iTEffect3 = new ITEffect(niftySlider3);
        niftySlider3.setValue(data.getInitReadFontSizeMap().get(Integer.valueOf(readConfig.getLineSpacingExtra())) != null ? r3.intValue() : 16);
        iTEffect3.setStartText("小");
        iTEffect3.setEndText("大");
        iTEffect3.setStartPadding((int) AppCtxKt.getAppCtx().getResources().getDimension(i2));
        iTEffect3.setEndPadding((int) AppCtxKt.getAppCtx().getResources().getDimension(i2));
        iTEffect3.setStartTextSize(AppCtxKt.getAppCtx().getResources().getDimension(i3));
        iTEffect3.setEndTextSize(AppCtxKt.getAppCtx().getResources().getDimension(i4));
        iTEffect3.setStartTintList(ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i5)));
        iTEffect3.setEndTintList(ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i5)));
        niftySlider3.addOnIntValueChangeListener(new Function3<NiftySlider, Integer, Boolean, Unit>() { // from class: com.ykb.ebook.weight.ReadMenu$initView$1$3$1$1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(NiftySlider niftySlider4, Integer num, Boolean bool) {
                invoke(niftySlider4, num.intValue(), bool.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(@NotNull NiftySlider niftySlider4, int i6, boolean z2) {
                Intrinsics.checkNotNullParameter(niftySlider4, "<anonymous parameter 0>");
                Integer num = Data.INSTANCE.getReadFontSizeMap().get(Integer.valueOf(i6));
                ReadConfig.INSTANCE.setLineSpacingExtra(num != null ? num.intValue() : 16);
                this.this$0.getCallback().uiConfigChange();
            }
        });
        niftySlider3.setEffect(iTEffect3);
        int colorMode = readConfig.getColorMode();
        if (colorMode == 0) {
            viewReadMenuBinding.bgWhite.setChecked(true);
        } else if (colorMode == 1) {
            viewReadMenuBinding.bgYellow.setChecked(true);
        } else if (colorMode == 2) {
            viewReadMenuBinding.bgBlue.setChecked(true);
        }
        refreshColorUi();
        int pageAnim = readConfig.getPageAnim();
        if (pageAnim == 1) {
            viewReadMenuBinding.cbPageSx.setChecked(true);
        } else if (pageAnim == 2) {
            viewReadMenuBinding.cbPageFz.setChecked(true);
        } else if (pageAnim == 4) {
            viewReadMenuBinding.cbPageZy.setChecked(true);
        }
        boolean zIsShowReview = readConfig.isShowReview();
        viewReadMenuBinding.cbReview.setText(zIsShowReview ? "隐藏段评" : "显示段评");
        viewReadMenuBinding.cbReview.setChecked(!zIsShowReview);
        boolean zIsShowNote = readConfig.isShowNote();
        viewReadMenuBinding.cbNote.setText(zIsShowNote ? "隐藏笔记" : "显示笔记");
        viewReadMenuBinding.cbNote.setChecked(!zIsShowNote);
        Toolbar toolbar = this.binding.titleBar;
        AppCompatActivity activity = ViewExtensionsKt.getActivity(this);
        toolbar.setPadding(0, activity != null ? ContextExtensionsKt.getStatusBarHeight(activity) : 0, 0, 0);
    }

    private final void refreshColorUi() throws SecurityException {
        ViewReadMenuBinding viewReadMenuBinding = this.binding;
        updateProgressUi();
        updateReadTime();
        setAddLibaaryTv();
        setCbColorMode();
        setCbNoteColorMode();
        int colorMode = ReadConfig.INSTANCE.getColorMode();
        if (colorMode == 0) {
            Toolbar toolbar = viewReadMenuBinding.titleBar;
            Context appCtx = AppCtxKt.getAppCtx();
            int i2 = R.color.white;
            toolbar.setBackground(new ColorDrawable(appCtx.getColor(i2)));
            viewReadMenuBinding.llBottomMenu.setBackground(new ColorDrawable(AppCtxKt.getAppCtx().getColor(i2)));
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.icon_download_book);
            Drawable drawable2 = ContextCompat.getDrawable(getContext(), R.drawable.ic_down_arrow);
            Drawable drawable3 = ContextCompat.getDrawable(getContext(), R.drawable.icon_hide_review);
            Drawable drawable4 = ContextCompat.getDrawable(getContext(), R.drawable.icon_hide_note);
            if (drawable3 != null) {
                drawable3.setColorFilter(null);
            }
            if (drawable4 != null) {
                drawable4.setColorFilter(null);
            }
            if (drawable2 != null) {
                drawable2.setColorFilter(null);
            }
            if (drawable != null) {
                drawable.setColorFilter(null);
            }
            RTextViewHelper helper = viewReadMenuBinding.tvDownload.getHelper();
            if (helper != null) {
                helper.setIconNormalTop(drawable);
            }
            View view = this.binding.lineView;
            Context context = getContext();
            int i3 = R.color.color_eeeeee;
            view.setBackground(new ColorDrawable(context.getColor(i3)));
            this.binding.lineV.setBackground(new ColorDrawable(getContext().getColor(i3)));
            RTextView rTextView = viewReadMenuBinding.tvDownload;
            Context appCtx2 = AppCtxKt.getAppCtx();
            int i4 = R.color.color_303030;
            rTextView.setTextColor(appCtx2.getColor(i4));
            viewReadMenuBinding.imgBack.setColorFilter(ContextCompat.getColor(getContext(), R.color.black), PorterDuff.Mode.SRC_IN);
            viewReadMenuBinding.closePageMethod.setImageDrawable(drawable2);
            viewReadMenuBinding.btnShre.setImageResource(R.mipmap.ic_ebook_share_more);
            RTextViewHelper helper2 = viewReadMenuBinding.tvPageSet.getHelper();
            if (helper2 != null) {
                helper2.setIconNormalRight(getContext().getDrawable(R.drawable.ic_set_right));
            }
            RTextViewHelper helper3 = viewReadMenuBinding.tvMoreSet.getHelper();
            if (helper3 != null) {
                helper3.setIconNormalRight(getContext().getDrawable(R.drawable.ic_set_right));
            }
            NiftySlider niftySlider = viewReadMenuBinding.seekReadPage;
            ColorStateList colorStateListValueOf = ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i3));
            Intrinsics.checkNotNullExpressionValue(colorStateListValueOf, "valueOf(appCtx.getColor(R.color.color_eeeeee))");
            niftySlider.setTrackTintList(colorStateListValueOf);
            NiftySlider niftySlider2 = viewReadMenuBinding.seekReadPage;
            Context appCtx3 = AppCtxKt.getAppCtx();
            int i5 = R.color.color_f7f8fa;
            ColorStateList colorStateListValueOf2 = ColorStateList.valueOf(appCtx3.getColor(i5));
            Intrinsics.checkNotNullExpressionValue(colorStateListValueOf2, "valueOf(appCtx.getColor(R.color.color_f7f8fa))");
            niftySlider2.setTrackInactiveTintList(colorStateListValueOf2);
            NiftySlider niftySlider3 = viewReadMenuBinding.seekFontSize;
            ColorStateList colorStateListValueOf3 = ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i3));
            Intrinsics.checkNotNullExpressionValue(colorStateListValueOf3, "valueOf(appCtx.getColor(R.color.color_eeeeee))");
            niftySlider3.setTrackTintList(colorStateListValueOf3);
            NiftySlider niftySlider4 = viewReadMenuBinding.seekFontSize;
            ColorStateList colorStateListValueOf4 = ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i5));
            Intrinsics.checkNotNullExpressionValue(colorStateListValueOf4, "valueOf(appCtx.getColor(R.color.color_f7f8fa))");
            niftySlider4.setTrackInactiveTintList(colorStateListValueOf4);
            viewReadMenuBinding.seekFontSize.setThumbTextTintList(ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i4)));
            NiftySlider niftySlider5 = viewReadMenuBinding.seekPageMargin;
            ColorStateList colorStateListValueOf5 = ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i3));
            Intrinsics.checkNotNullExpressionValue(colorStateListValueOf5, "valueOf(appCtx.getColor(R.color.color_eeeeee))");
            niftySlider5.setTrackTintList(colorStateListValueOf5);
            NiftySlider niftySlider6 = viewReadMenuBinding.seekPageMargin;
            ColorStateList colorStateListValueOf6 = ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i5));
            Intrinsics.checkNotNullExpressionValue(colorStateListValueOf6, "valueOf(appCtx.getColor(R.color.color_f7f8fa))");
            niftySlider6.setTrackInactiveTintList(colorStateListValueOf6);
            viewReadMenuBinding.seekPageMargin.setThumbTextTintList(ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i4)));
            NiftySlider niftySlider7 = viewReadMenuBinding.seekPageLine;
            ColorStateList colorStateListValueOf7 = ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i3));
            Intrinsics.checkNotNullExpressionValue(colorStateListValueOf7, "valueOf(appCtx.getColor(R.color.color_eeeeee))");
            niftySlider7.setTrackTintList(colorStateListValueOf7);
            NiftySlider niftySlider8 = viewReadMenuBinding.seekPageLine;
            ColorStateList colorStateListValueOf8 = ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i5));
            Intrinsics.checkNotNullExpressionValue(colorStateListValueOf8, "valueOf(appCtx.getColor(R.color.color_f7f8fa))");
            niftySlider8.setTrackInactiveTintList(colorStateListValueOf8);
            viewReadMenuBinding.seekPageLine.setThumbTextTintList(ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i4)));
            RTextViewHelper helper4 = viewReadMenuBinding.tvPageSet.getHelper();
            if (helper4 != null) {
                helper4.setBackgroundColorNormal(AppCtxKt.getAppCtx().getColor(i5));
            }
            viewReadMenuBinding.tvPageSet.setTextColor(AppCtxKt.getAppCtx().getColor(i4));
            RTextViewHelper helper5 = viewReadMenuBinding.tvMoreSet.getHelper();
            if (helper5 != null) {
                helper5.setBackgroundColorNormal(AppCtxKt.getAppCtx().getColor(i5));
            }
            viewReadMenuBinding.tvMoreSet.setTextColor(AppCtxKt.getAppCtx().getColor(i4));
            viewReadMenuBinding.tvBg.setTextColor(AppCtxKt.getAppCtx().getColor(i4));
            this.binding.tvPageTitle.setTextColor(AppCtxKt.getAppCtx().getColor(i4));
            viewReadMenuBinding.tvPreview.getHelper().setTextColorNormal(getContext().getColor(i4));
            viewReadMenuBinding.tvNext.getHelper().setTextColorNormal(getContext().getColor(i4));
            RCheckHelper helper6 = this.binding.cbPageFz.getHelper();
            Context context2 = getContext();
            int i6 = R.color.color_312d2d;
            helper6.setTextColorNormal(context2.getColor(i6));
            RCheckHelper helper7 = this.binding.cbPageFz.getHelper();
            Context context3 = getContext();
            int i7 = R.color.color_dd594a;
            helper7.setTextColorChecked(context3.getColor(i7));
            this.binding.cbPageZy.getHelper().setTextColorNormal(getContext().getColor(i6));
            this.binding.cbPageZy.getHelper().setTextColorChecked(getContext().getColor(i7));
            this.binding.cbPageSx.getHelper().setTextColorNormal(getContext().getColor(i6));
            this.binding.cbPageSx.getHelper().setTextColorChecked(getContext().getColor(i7));
            if (this.binding.cbPageFz.isChecked()) {
                this.binding.cbPageFz.getHelper().setIconSelectedRight(getContext().getDrawable(R.drawable.ic_page_flipping_checked));
            }
            if (this.binding.cbPageZy.isChecked()) {
                this.binding.cbPageZy.getHelper().setIconSelectedRight(getContext().getDrawable(R.drawable.ic_page_flipping_checked));
            }
            if (this.binding.cbPageSx.isChecked()) {
                this.binding.cbPageSx.getHelper().setIconSelectedRight(getContext().getDrawable(R.drawable.ic_page_flipping_checked));
            }
            viewReadMenuBinding.imgBookCatalog.setImageResource(R.drawable.icon_book_catalog);
            viewReadMenuBinding.imgBookMark.setImageResource(R.drawable.icon_bookmark);
            viewReadMenuBinding.imgBookComment.setImageResource(R.drawable.icon_book_comment);
            viewReadMenuBinding.cbDarkMode.setOnCheckedChangeListener(null);
            viewReadMenuBinding.cbDarkMode.setChecked(false);
            viewReadMenuBinding.cbDarkMode.setOnCheckedChangeListener(this);
            viewReadMenuBinding.imgBookSetting.setImageResource(R.drawable.icon_book_setting);
            View view2 = this.binding.viewPageLine;
            Intrinsics.checkNotNullExpressionValue(view2, "binding.viewPageLine");
            view2.setBackgroundColor(AppCtxKt.getAppCtx().getColor(i3));
            RTextViewHelper helper8 = this.binding.tvCommentNum.getHelper();
            if (helper8 == null) {
                return;
            }
            helper8.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_fd4018));
            return;
        }
        if (colorMode == 1) {
            Toolbar toolbar2 = viewReadMenuBinding.titleBar;
            Context appCtx4 = AppCtxKt.getAppCtx();
            int i8 = R.color.color_F5EBCE;
            toolbar2.setBackground(new ColorDrawable(appCtx4.getColor(i8)));
            viewReadMenuBinding.llBottomMenu.setBackground(new ColorDrawable(AppCtxKt.getAppCtx().getColor(i8)));
            Drawable drawable5 = ContextCompat.getDrawable(getContext(), R.drawable.icon_download_book);
            Drawable drawable6 = ContextCompat.getDrawable(getContext(), R.drawable.ic_down_arrow);
            Drawable drawable7 = ContextCompat.getDrawable(getContext(), R.drawable.icon_hide_review);
            Drawable drawable8 = ContextCompat.getDrawable(getContext(), R.drawable.icon_hide_note);
            if (drawable7 != null) {
                drawable7.setColorFilter(null);
            }
            if (drawable8 != null) {
                drawable8.setColorFilter(null);
            }
            if (drawable6 != null) {
                drawable6.setColorFilter(null);
            }
            if (drawable5 != null) {
                drawable5.setColorFilter(null);
            }
            RTextViewHelper helper9 = viewReadMenuBinding.tvDownload.getHelper();
            if (helper9 != null) {
                helper9.setIconNormalTop(drawable5);
            }
            View view3 = this.binding.lineView;
            Context context4 = getContext();
            int i9 = R.color.color_EDE2C3;
            view3.setBackground(new ColorDrawable(context4.getColor(i9)));
            this.binding.lineV.setBackground(new ColorDrawable(getContext().getColor(R.color.color_eeeeee)));
            RTextView rTextView2 = viewReadMenuBinding.tvDownload;
            Context appCtx5 = AppCtxKt.getAppCtx();
            int i10 = R.color.color_303030;
            rTextView2.setTextColor(appCtx5.getColor(i10));
            viewReadMenuBinding.imgBack.setColorFilter(ContextCompat.getColor(getContext(), R.color.black), PorterDuff.Mode.SRC_IN);
            viewReadMenuBinding.closePageMethod.setImageDrawable(drawable6);
            viewReadMenuBinding.btnShre.setImageResource(R.mipmap.ic_ebook_share_more);
            NiftySlider niftySlider9 = viewReadMenuBinding.seekReadPage;
            Context appCtx6 = AppCtxKt.getAppCtx();
            int i11 = R.color.color_D6C9A9;
            ColorStateList colorStateListValueOf9 = ColorStateList.valueOf(appCtx6.getColor(i11));
            Intrinsics.checkNotNullExpressionValue(colorStateListValueOf9, "valueOf(appCtx.getColor(R.color.color_D6C9A9))");
            niftySlider9.setTrackTintList(colorStateListValueOf9);
            NiftySlider niftySlider10 = viewReadMenuBinding.seekReadPage;
            ColorStateList colorStateListValueOf10 = ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i9));
            Intrinsics.checkNotNullExpressionValue(colorStateListValueOf10, "valueOf(appCtx.getColor(R.color.color_EDE2C3))");
            niftySlider10.setTrackInactiveTintList(colorStateListValueOf10);
            NiftySlider niftySlider11 = viewReadMenuBinding.seekFontSize;
            ColorStateList colorStateListValueOf11 = ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i11));
            Intrinsics.checkNotNullExpressionValue(colorStateListValueOf11, "valueOf(appCtx.getColor(R.color.color_D6C9A9))");
            niftySlider11.setTrackTintList(colorStateListValueOf11);
            NiftySlider niftySlider12 = viewReadMenuBinding.seekFontSize;
            ColorStateList colorStateListValueOf12 = ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i9));
            Intrinsics.checkNotNullExpressionValue(colorStateListValueOf12, "valueOf(appCtx.getColor(R.color.color_EDE2C3))");
            niftySlider12.setTrackInactiveTintList(colorStateListValueOf12);
            viewReadMenuBinding.seekFontSize.setThumbTextTintList(ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i10)));
            NiftySlider niftySlider13 = viewReadMenuBinding.seekPageMargin;
            ColorStateList colorStateListValueOf13 = ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i11));
            Intrinsics.checkNotNullExpressionValue(colorStateListValueOf13, "valueOf(appCtx.getColor(R.color.color_D6C9A9))");
            niftySlider13.setTrackTintList(colorStateListValueOf13);
            NiftySlider niftySlider14 = viewReadMenuBinding.seekPageMargin;
            ColorStateList colorStateListValueOf14 = ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i9));
            Intrinsics.checkNotNullExpressionValue(colorStateListValueOf14, "valueOf(appCtx.getColor(R.color.color_EDE2C3))");
            niftySlider14.setTrackInactiveTintList(colorStateListValueOf14);
            viewReadMenuBinding.seekPageMargin.setThumbTextTintList(ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i10)));
            NiftySlider niftySlider15 = viewReadMenuBinding.seekPageLine;
            ColorStateList colorStateListValueOf15 = ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i11));
            Intrinsics.checkNotNullExpressionValue(colorStateListValueOf15, "valueOf(appCtx.getColor(R.color.color_D6C9A9))");
            niftySlider15.setTrackTintList(colorStateListValueOf15);
            NiftySlider niftySlider16 = viewReadMenuBinding.seekPageLine;
            ColorStateList colorStateListValueOf16 = ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i9));
            Intrinsics.checkNotNullExpressionValue(colorStateListValueOf16, "valueOf(appCtx.getColor(R.color.color_EDE2C3))");
            niftySlider16.setTrackInactiveTintList(colorStateListValueOf16);
            viewReadMenuBinding.seekPageLine.setThumbTextTintList(ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i10)));
            RTextViewHelper helper10 = viewReadMenuBinding.tvPageSet.getHelper();
            if (helper10 != null) {
                helper10.setBackgroundColorNormal(AppCtxKt.getAppCtx().getColor(i9));
            }
            viewReadMenuBinding.tvPageSet.setTextColor(AppCtxKt.getAppCtx().getColor(i10));
            RTextViewHelper helper11 = viewReadMenuBinding.tvMoreSet.getHelper();
            if (helper11 != null) {
                helper11.setBackgroundColorNormal(AppCtxKt.getAppCtx().getColor(i9));
            }
            viewReadMenuBinding.tvMoreSet.setTextColor(AppCtxKt.getAppCtx().getColor(i10));
            RTextViewHelper helper12 = viewReadMenuBinding.tvPageSet.getHelper();
            if (helper12 != null) {
                helper12.setIconNormalRight(getContext().getDrawable(R.drawable.ic_set_right));
            }
            RTextViewHelper helper13 = viewReadMenuBinding.tvMoreSet.getHelper();
            if (helper13 != null) {
                helper13.setIconNormalRight(getContext().getDrawable(R.drawable.ic_set_right));
            }
            viewReadMenuBinding.tvBg.setTextColor(AppCtxKt.getAppCtx().getColor(i10));
            this.binding.tvPageTitle.setTextColor(AppCtxKt.getAppCtx().getColor(i10));
            viewReadMenuBinding.tvPreview.getHelper().setTextColorNormal(getContext().getColor(i10));
            viewReadMenuBinding.tvNext.getHelper().setTextColorNormal(getContext().getColor(i10));
            RCheckHelper helper14 = this.binding.cbPageFz.getHelper();
            Context context5 = getContext();
            int i12 = R.color.color_312d2d;
            helper14.setTextColorNormal(context5.getColor(i12));
            RCheckHelper helper15 = this.binding.cbPageFz.getHelper();
            Context context6 = getContext();
            int i13 = R.color.color_dd594a;
            helper15.setTextColorChecked(context6.getColor(i13));
            this.binding.cbPageZy.getHelper().setTextColorNormal(getContext().getColor(i12));
            this.binding.cbPageZy.getHelper().setTextColorChecked(getContext().getColor(i13));
            this.binding.cbPageSx.getHelper().setTextColorNormal(getContext().getColor(i12));
            this.binding.cbPageSx.getHelper().setTextColorChecked(getContext().getColor(i13));
            if (this.binding.cbPageFz.isChecked()) {
                this.binding.cbPageFz.getHelper().setIconSelectedRight(getContext().getDrawable(R.drawable.ic_page_flipping_checked));
            }
            if (this.binding.cbPageZy.isChecked()) {
                this.binding.cbPageZy.getHelper().setIconSelectedRight(getContext().getDrawable(R.drawable.ic_page_flipping_checked));
            }
            if (this.binding.cbPageSx.isChecked()) {
                this.binding.cbPageSx.getHelper().setIconSelectedRight(getContext().getDrawable(R.drawable.ic_page_flipping_checked));
            }
            viewReadMenuBinding.imgBookCatalog.setImageResource(R.drawable.icon_book_catalog);
            viewReadMenuBinding.imgBookMark.setImageResource(R.drawable.icon_bookmark);
            viewReadMenuBinding.imgBookComment.setImageResource(R.drawable.icon_book_comment);
            viewReadMenuBinding.cbDarkMode.setOnCheckedChangeListener(null);
            viewReadMenuBinding.cbDarkMode.setChecked(false);
            viewReadMenuBinding.cbDarkMode.setOnCheckedChangeListener(this);
            viewReadMenuBinding.imgBookSetting.setImageResource(R.drawable.icon_book_setting);
            View view4 = this.binding.viewPageLine;
            Intrinsics.checkNotNullExpressionValue(view4, "binding.viewPageLine");
            view4.setBackgroundColor(AppCtxKt.getAppCtx().getColor(R.color.color_theme_yellow_line_color));
            RTextViewHelper helper16 = this.binding.tvCommentNum.getHelper();
            if (helper16 == null) {
                return;
            }
            helper16.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_fd4018));
            return;
        }
        if (colorMode != 2) {
            return;
        }
        Toolbar toolbar3 = viewReadMenuBinding.titleBar;
        Context appCtx7 = AppCtxKt.getAppCtx();
        int i14 = R.color.color_121622;
        toolbar3.setBackground(new ColorDrawable(appCtx7.getColor(i14)));
        viewReadMenuBinding.llBottomMenu.setBackground(new ColorDrawable(AppCtxKt.getAppCtx().getColor(i14)));
        Drawable drawable9 = ContextCompat.getDrawable(getContext(), R.drawable.icon_download_book);
        Drawable drawable10 = ContextCompat.getDrawable(getContext(), R.drawable.ic_down_arrow);
        Drawable drawable11 = ContextCompat.getDrawable(getContext(), R.drawable.icon_hide_review);
        Drawable drawable12 = ContextCompat.getDrawable(getContext(), R.drawable.icon_hide_note);
        if (drawable11 != null) {
            drawable11.setColorFilter(null);
        }
        if (drawable12 != null) {
            drawable12.setColorFilter(null);
        }
        if (drawable9 != null) {
            drawable9.setColorFilter(getContext().getColor(R.color.color_7380a9), PorterDuff.Mode.SRC_IN);
        }
        if (drawable11 != null) {
            drawable11.setColorFilter(getContext().getColor(R.color.color_7380a9), PorterDuff.Mode.SRC_IN);
        }
        if (drawable12 != null) {
            drawable12.setColorFilter(getContext().getColor(R.color.color_7380a9), PorterDuff.Mode.SRC_IN);
        }
        if (drawable10 != null) {
            drawable10.setColorFilter(getContext().getColor(R.color.color_7380a9), PorterDuff.Mode.SRC_IN);
        }
        RTextViewHelper helper17 = viewReadMenuBinding.tvDownload.getHelper();
        if (helper17 != null) {
            helper17.setIconNormalTop(drawable9);
        }
        View view5 = this.binding.lineView;
        Context context7 = getContext();
        int i15 = R.color.color_1C2134;
        view5.setBackground(new ColorDrawable(context7.getColor(i15)));
        this.binding.lineV.setBackground(new ColorDrawable(getContext().getColor(i15)));
        RTextView rTextView3 = viewReadMenuBinding.tvDownload;
        Context appCtx8 = AppCtxKt.getAppCtx();
        int i16 = R.color.color_7380a9;
        rTextView3.setTextColor(appCtx8.getColor(i16));
        viewReadMenuBinding.btnShre.setImageResource(R.mipmap.ic_ebook_share_more_night);
        viewReadMenuBinding.imgBack.setColorFilter(ContextCompat.getColor(getContext(), i16), PorterDuff.Mode.SRC_IN);
        viewReadMenuBinding.closePageMethod.setImageDrawable(drawable10);
        NiftySlider niftySlider17 = viewReadMenuBinding.seekReadPage;
        Context appCtx9 = AppCtxKt.getAppCtx();
        int i17 = R.color.color_2E3241;
        ColorStateList colorStateListValueOf17 = ColorStateList.valueOf(appCtx9.getColor(i17));
        Intrinsics.checkNotNullExpressionValue(colorStateListValueOf17, "valueOf(appCtx.getColor(R.color.color_2E3241))");
        niftySlider17.setTrackTintList(colorStateListValueOf17);
        NiftySlider niftySlider18 = viewReadMenuBinding.seekReadPage;
        Context appCtx10 = AppCtxKt.getAppCtx();
        int i18 = R.color.color_171C2D;
        ColorStateList colorStateListValueOf18 = ColorStateList.valueOf(appCtx10.getColor(i18));
        Intrinsics.checkNotNullExpressionValue(colorStateListValueOf18, "valueOf(appCtx.getColor(R.color.color_171C2D))");
        niftySlider18.setTrackInactiveTintList(colorStateListValueOf18);
        NiftySlider niftySlider19 = viewReadMenuBinding.seekFontSize;
        ColorStateList colorStateListValueOf19 = ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i17));
        Intrinsics.checkNotNullExpressionValue(colorStateListValueOf19, "valueOf(appCtx.getColor(R.color.color_2E3241))");
        niftySlider19.setTrackTintList(colorStateListValueOf19);
        NiftySlider niftySlider20 = viewReadMenuBinding.seekFontSize;
        ColorStateList colorStateListValueOf20 = ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i18));
        Intrinsics.checkNotNullExpressionValue(colorStateListValueOf20, "valueOf(appCtx.getColor(R.color.color_171C2D))");
        niftySlider20.setTrackInactiveTintList(colorStateListValueOf20);
        viewReadMenuBinding.seekFontSize.setThumbTextTintList(ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i16)));
        NiftySlider niftySlider21 = viewReadMenuBinding.seekPageMargin;
        ColorStateList colorStateListValueOf21 = ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i17));
        Intrinsics.checkNotNullExpressionValue(colorStateListValueOf21, "valueOf(appCtx.getColor(R.color.color_2E3241))");
        niftySlider21.setTrackTintList(colorStateListValueOf21);
        NiftySlider niftySlider22 = viewReadMenuBinding.seekPageMargin;
        ColorStateList colorStateListValueOf22 = ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i18));
        Intrinsics.checkNotNullExpressionValue(colorStateListValueOf22, "valueOf(appCtx.getColor(R.color.color_171C2D))");
        niftySlider22.setTrackInactiveTintList(colorStateListValueOf22);
        viewReadMenuBinding.seekPageMargin.setThumbTextTintList(ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i16)));
        NiftySlider niftySlider23 = viewReadMenuBinding.seekPageLine;
        ColorStateList colorStateListValueOf23 = ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i17));
        Intrinsics.checkNotNullExpressionValue(colorStateListValueOf23, "valueOf(appCtx.getColor(R.color.color_2E3241))");
        niftySlider23.setTrackTintList(colorStateListValueOf23);
        NiftySlider niftySlider24 = viewReadMenuBinding.seekPageLine;
        ColorStateList colorStateListValueOf24 = ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i18));
        Intrinsics.checkNotNullExpressionValue(colorStateListValueOf24, "valueOf(appCtx.getColor(R.color.color_171C2D))");
        niftySlider24.setTrackInactiveTintList(colorStateListValueOf24);
        viewReadMenuBinding.seekPageLine.setThumbTextTintList(ColorStateList.valueOf(AppCtxKt.getAppCtx().getColor(i16)));
        RTextViewHelper helper18 = viewReadMenuBinding.tvPageSet.getHelper();
        if (helper18 != null) {
            helper18.setBackgroundColorNormal(AppCtxKt.getAppCtx().getColor(i15));
        }
        viewReadMenuBinding.tvPageSet.setTextColor(AppCtxKt.getAppCtx().getColor(i16));
        RTextViewHelper helper19 = viewReadMenuBinding.tvPageSet.getHelper();
        if (helper19 != null) {
            helper19.setIconNormalRight(AppCtxKt.getAppCtx().getDrawable(R.mipmap.ic_set_right_night));
        }
        this.binding.tvPageTitle.setTextColor(AppCtxKt.getAppCtx().getColor(i16));
        RTextViewHelper helper20 = viewReadMenuBinding.tvMoreSet.getHelper();
        if (helper20 != null) {
            helper20.setBackgroundColorNormal(AppCtxKt.getAppCtx().getColor(i15));
        }
        viewReadMenuBinding.tvMoreSet.setTextColor(AppCtxKt.getAppCtx().getColor(i16));
        RTextViewHelper helper21 = viewReadMenuBinding.tvMoreSet.getHelper();
        if (helper21 != null) {
            helper21.setIconNormalRight(AppCtxKt.getAppCtx().getDrawable(R.mipmap.ic_set_right_night));
        }
        viewReadMenuBinding.tvBg.setTextColor(AppCtxKt.getAppCtx().getColor(i16));
        viewReadMenuBinding.tvPreview.getHelper().setTextColorNormal(getContext().getColor(i16));
        viewReadMenuBinding.tvPreview.getHelper().setTextColorUnable(getContext().getColor(R.color.color_575F79));
        viewReadMenuBinding.tvNext.getHelper().setTextColorNormal(getContext().getColor(i16));
        this.binding.cbPageFz.getHelper().setTextColorNormal(getContext().getColor(i16));
        RCheckHelper helper22 = this.binding.cbPageFz.getHelper();
        Context context8 = getContext();
        int i19 = R.color.color_B2575C;
        helper22.setTextColorChecked(context8.getColor(i19));
        this.binding.cbPageZy.getHelper().setTextColorNormal(getContext().getColor(i16));
        this.binding.cbPageZy.getHelper().setTextColorChecked(getContext().getColor(i19));
        this.binding.cbPageSx.getHelper().setTextColorNormal(getContext().getColor(i16));
        this.binding.cbPageSx.getHelper().setTextColorChecked(getContext().getColor(i19));
        if (this.binding.cbPageFz.isChecked()) {
            this.binding.cbPageFz.getHelper().setIconSelectedRight(getContext().getDrawable(R.drawable.ic_page_flipping_checked_night));
        }
        if (this.binding.cbPageZy.isChecked()) {
            this.binding.cbPageZy.getHelper().setIconSelectedRight(getContext().getDrawable(R.drawable.ic_page_flipping_checked_night));
        }
        if (this.binding.cbPageSx.isChecked()) {
            this.binding.cbPageSx.getHelper().setIconSelectedRight(getContext().getDrawable(R.drawable.ic_page_flipping_checked_night));
        }
        viewReadMenuBinding.imgBookCatalog.setImageResource(R.drawable.icon_book_catalog_night);
        viewReadMenuBinding.imgBookMark.setImageResource(R.drawable.icon_bookmark_night);
        viewReadMenuBinding.imgBookComment.setImageResource(R.drawable.icon_book_comment_night);
        viewReadMenuBinding.cbDarkMode.setOnCheckedChangeListener(null);
        viewReadMenuBinding.cbDarkMode.setChecked(true);
        viewReadMenuBinding.cbDarkMode.setOnCheckedChangeListener(this);
        viewReadMenuBinding.imgBookSetting.setImageResource(R.drawable.icon_book_setting_night);
        View view6 = this.binding.viewPageLine;
        Intrinsics.checkNotNullExpressionValue(view6, "binding.viewPageLine");
        view6.setBackgroundColor(AppCtxKt.getAppCtx().getColor(R.color.color_theme_blue_line_color));
        RTextViewHelper helper23 = this.binding.tvCommentNum.getHelper();
        if (helper23 == null) {
            return;
        }
        helper23.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i19));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void runMenuOut$default(ReadMenu readMenu, Function0 function0, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            function0 = null;
        }
        readMenu.runMenuOut(function0);
    }

    private final void setCbColorMode() {
        int colorMode = ReadConfig.INSTANCE.getColorMode();
        if (colorMode == 0 || colorMode == 1) {
            if (this.binding.cbReview.isChecked()) {
                RCheckHelper helper = this.binding.cbReview.getHelper();
                if (helper != null) {
                    helper.setIconNormalTop(AppCtxKt.getAppCtx().getDrawable(R.drawable.icon_show_review));
                }
                RCheckHelper helper2 = this.binding.cbReview.getHelper();
                if (helper2 != null) {
                    helper2.setIconCheckedTop(AppCtxKt.getAppCtx().getDrawable(R.drawable.icon_show_review));
                }
            } else {
                RCheckHelper helper3 = this.binding.cbReview.getHelper();
                if (helper3 != null) {
                    helper3.setIconNormalTop(AppCtxKt.getAppCtx().getDrawable(R.drawable.icon_hide_review));
                }
                RCheckHelper helper4 = this.binding.cbReview.getHelper();
                if (helper4 != null) {
                    helper4.setIconCheckedTop(AppCtxKt.getAppCtx().getDrawable(R.drawable.icon_hide_review));
                }
            }
            RCheckHelper helper5 = this.binding.cbReview.getHelper();
            if (helper5 != null) {
                helper5.setTextColorChecked(AppCtxKt.getAppCtx().getColor(R.color.color_303030));
            }
            RCheckHelper helper6 = this.binding.cbReview.getHelper();
            if (helper6 == null) {
                return;
            }
            helper6.setTextColorNormal(AppCtxKt.getAppCtx().getColor(R.color.color_909090));
            return;
        }
        if (colorMode != 2) {
            return;
        }
        if (this.binding.cbReview.isChecked()) {
            RCheckHelper helper7 = this.binding.cbReview.getHelper();
            if (helper7 != null) {
                helper7.setIconNormalTop(AppCtxKt.getAppCtx().getDrawable(R.drawable.icon_hide_review_checked_night));
            }
            RCheckHelper helper8 = this.binding.cbReview.getHelper();
            if (helper8 != null) {
                helper8.setIconCheckedTop(AppCtxKt.getAppCtx().getDrawable(R.drawable.icon_hide_review_checked_night));
            }
        } else {
            RCheckHelper helper9 = this.binding.cbReview.getHelper();
            if (helper9 != null) {
                helper9.setIconNormalTop(AppCtxKt.getAppCtx().getDrawable(R.drawable.icon_hide_review_nochecked_night));
            }
            RCheckHelper helper10 = this.binding.cbReview.getHelper();
            if (helper10 != null) {
                helper10.setIconCheckedTop(AppCtxKt.getAppCtx().getDrawable(R.drawable.icon_hide_review_nochecked_night));
            }
        }
        RCheckHelper helper11 = this.binding.cbReview.getHelper();
        if (helper11 != null) {
            helper11.setTextColorChecked(AppCtxKt.getAppCtx().getColor(R.color.color_7380a9));
        }
        RCheckHelper helper12 = this.binding.cbReview.getHelper();
        if (helper12 == null) {
            return;
        }
        helper12.setTextColorNormal(AppCtxKt.getAppCtx().getColor(R.color.color_575F79));
    }

    private final void setCbNoteColorMode() {
        int colorMode = ReadConfig.INSTANCE.getColorMode();
        if (colorMode == 0 || colorMode == 1) {
            if (this.binding.cbNote.isChecked()) {
                RCheckHelper helper = this.binding.cbNote.getHelper();
                if (helper != null) {
                    helper.setIconNormalTop(AppCtxKt.getAppCtx().getDrawable(R.drawable.icon_show_note));
                }
                RCheckHelper helper2 = this.binding.cbNote.getHelper();
                if (helper2 != null) {
                    helper2.setIconCheckedTop(AppCtxKt.getAppCtx().getDrawable(R.drawable.icon_show_note));
                }
            } else {
                RCheckHelper helper3 = this.binding.cbNote.getHelper();
                if (helper3 != null) {
                    helper3.setIconNormalTop(AppCtxKt.getAppCtx().getDrawable(R.drawable.icon_hide_note));
                }
                RCheckHelper helper4 = this.binding.cbNote.getHelper();
                if (helper4 != null) {
                    helper4.setIconCheckedTop(AppCtxKt.getAppCtx().getDrawable(R.drawable.icon_hide_note));
                }
            }
            RCheckHelper helper5 = this.binding.cbNote.getHelper();
            if (helper5 != null) {
                helper5.setTextColorChecked(AppCtxKt.getAppCtx().getColor(R.color.color_303030));
            }
            RCheckHelper helper6 = this.binding.cbNote.getHelper();
            if (helper6 == null) {
                return;
            }
            helper6.setTextColorNormal(AppCtxKt.getAppCtx().getColor(R.color.color_909090));
            return;
        }
        if (colorMode != 2) {
            return;
        }
        if (this.binding.cbNote.isChecked()) {
            RCheckHelper helper7 = this.binding.cbNote.getHelper();
            if (helper7 != null) {
                helper7.setIconNormalTop(AppCtxKt.getAppCtx().getDrawable(R.drawable.icon_hide_note_checked_night));
            }
            RCheckHelper helper8 = this.binding.cbNote.getHelper();
            if (helper8 != null) {
                helper8.setIconCheckedTop(AppCtxKt.getAppCtx().getDrawable(R.drawable.icon_hide_note_checked_night));
            }
        } else {
            RCheckHelper helper9 = this.binding.cbNote.getHelper();
            if (helper9 != null) {
                helper9.setIconNormalTop(AppCtxKt.getAppCtx().getDrawable(R.drawable.icon_hide_note_nochecked_night));
            }
            RCheckHelper helper10 = this.binding.cbNote.getHelper();
            if (helper10 != null) {
                helper10.setIconCheckedTop(AppCtxKt.getAppCtx().getDrawable(R.drawable.icon_hide_note_nochecked_night));
            }
        }
        RCheckHelper helper11 = this.binding.cbNote.getHelper();
        if (helper11 != null) {
            helper11.setTextColorChecked(AppCtxKt.getAppCtx().getColor(R.color.color_7380a9));
        }
        RCheckHelper helper12 = this.binding.cbNote.getHelper();
        if (helper12 == null) {
            return;
        }
        helper12.setTextColorNormal(AppCtxKt.getAppCtx().getColor(R.color.color_575F79));
    }

    private final void updateProgressUi() {
        Context context;
        int i2;
        Context context2;
        int i3;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getColorMode() == 2) {
            context = getContext();
            i2 = R.color.color_7380a9;
        } else {
            context = getContext();
            i2 = R.color.color_303030;
        }
        int color = context.getColor(i2);
        if (readConfig.getColorMode() == 2) {
            context2 = getContext();
            i3 = R.color.color_575F79;
        } else {
            context2 = getContext();
            i3 = R.color.color_909090;
        }
        int color2 = context2.getColor(i3);
        spannableStringBuilder.append((CharSequence) this.readProgress).setSpan(new AbsoluteSizeSpan(18, true), 0, spannableStringBuilder.length(), 18);
        spannableStringBuilder.append((CharSequence) "%").setSpan(new AbsoluteSizeSpan(12, true), this.readProgress.length(), spannableStringBuilder.length(), 34);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(color), 0, spannableStringBuilder.length(), 34);
        spannableStringBuilder.append((CharSequence) "\n").append((CharSequence) "阅读进度");
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(12, true), this.readProgress.length() + 1, spannableStringBuilder.length(), 34);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(color2), this.readProgress.length() + 1, spannableStringBuilder.length(), 34);
        spannableStringBuilder.setSpan(new StyleSpan(1), 0, this.readProgress.length(), 18);
        this.binding.tvReadRate.setText(spannableStringBuilder);
    }

    private final void updateReadTime() {
        Context context;
        int i2;
        Context context2;
        int i3;
        int i4;
        int i5;
        int length;
        long j2 = this.time;
        long j3 = 3600;
        long j4 = j2 / j3;
        long j5 = (j2 % j3) / 60;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getColorMode() == 2) {
            context = getContext();
            i2 = R.color.color_7380a9;
        } else {
            context = getContext();
            i2 = R.color.color_303030;
        }
        int color = context.getColor(i2);
        if (readConfig.getColorMode() == 2) {
            context2 = getContext();
            i3 = R.color.color_575F79;
        } else {
            context2 = getContext();
            i3 = R.color.color_909090;
        }
        int color2 = context2.getColor(i3);
        if (j4 > 0) {
            spannableStringBuilder.append((CharSequence) String.valueOf(j4)).setSpan(new AbsoluteSizeSpan(18, true), 0, spannableStringBuilder.length(), 18);
            spannableStringBuilder.append((CharSequence) "小时").setSpan(new AbsoluteSizeSpan(12, true), String.valueOf(j4).length(), spannableStringBuilder.length(), 34);
            i4 = 18;
            spannableStringBuilder.setSpan(new StyleSpan(1), 0, String.valueOf(j4).length(), 18);
        } else {
            i4 = 18;
        }
        spannableStringBuilder.append((CharSequence) String.valueOf(j5)).setSpan(new AbsoluteSizeSpan(i4, true), j4 > 0 ? String.valueOf(j4).length() + 2 : 0, spannableStringBuilder.length(), i4);
        spannableStringBuilder.append((CharSequence) "分钟").setSpan(new AbsoluteSizeSpan(12, true), (j4 > 0 ? String.valueOf(j4).length() + 2 : 0) + String.valueOf(j5).length(), spannableStringBuilder.length(), 34);
        StyleSpan styleSpan = new StyleSpan(1);
        if (j4 > 0) {
            i5 = 2;
            length = String.valueOf(j4).length() + 2;
        } else {
            i5 = 2;
            length = 0;
        }
        spannableStringBuilder.setSpan(styleSpan, length, (j4 > 0 ? String.valueOf(j4).length() + i5 : 0) + String.valueOf(j5).length(), 18);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(color), 0, spannableStringBuilder.length(), 34);
        spannableStringBuilder.append((CharSequence) "\n").append((CharSequence) "阅读时长");
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(12, true), spannableStringBuilder.length() - 4, spannableStringBuilder.length(), 34);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(color2), spannableStringBuilder.length() - 4, spannableStringBuilder.length(), 34);
        this.binding.tvReadTime.setText(spannableStringBuilder);
    }

    public final boolean getCanShowMenu() {
        return this.canShowMenu;
    }

    public final long getTime() {
        return this.time;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(@NotNull CompoundButton button, boolean isChecked) throws SecurityException {
        Intrinsics.checkNotNullParameter(button, "button");
        if (this.listenerFlag) {
            if (Intrinsics.areEqual(button, this.binding.cbDarkMode)) {
                handleDarkModeSwitch(isChecked);
                return;
            }
            if (isChecked) {
                if (Intrinsics.areEqual(button, this.binding.bgWhite)) {
                    ReadConfig readConfig = ReadConfig.INSTANCE;
                    readConfig.setColorModePre(0);
                    readConfig.setColorMode(0);
                    refreshColorUi();
                    getCallback().backgroundChange();
                    return;
                }
                if (Intrinsics.areEqual(button, this.binding.bgYellow)) {
                    ReadConfig readConfig2 = ReadConfig.INSTANCE;
                    readConfig2.setColorModePre(1);
                    readConfig2.setColorMode(1);
                    refreshColorUi();
                    getCallback().backgroundChange();
                    return;
                }
                if (Intrinsics.areEqual(button, this.binding.bgBlue)) {
                    ReadConfig.INSTANCE.setColorMode(2);
                    refreshColorUi();
                    getCallback().backgroundChange();
                    return;
                }
                if (Intrinsics.areEqual(button, this.binding.cbPageFz)) {
                    ReadConfig readConfig3 = ReadConfig.INSTANCE;
                    readConfig3.setPageAnim(2);
                    getCallback().scrollAnimChange();
                    if (readConfig3.getColorMode() == 2) {
                        this.binding.cbPageFz.getHelper().setIconSelectedRight(getContext().getDrawable(R.drawable.ic_page_flipping_checked_night));
                        return;
                    } else {
                        this.binding.cbPageFz.getHelper().setIconSelectedRight(getContext().getDrawable(R.drawable.ic_page_flipping_checked));
                        return;
                    }
                }
                if (Intrinsics.areEqual(button, this.binding.cbPageZy)) {
                    ReadConfig readConfig4 = ReadConfig.INSTANCE;
                    readConfig4.setPageAnim(4);
                    getCallback().scrollAnimChange();
                    if (readConfig4.getColorMode() == 2) {
                        this.binding.cbPageZy.getHelper().setIconSelectedRight(getContext().getDrawable(R.drawable.ic_page_flipping_checked_night));
                        return;
                    } else {
                        this.binding.cbPageZy.getHelper().setIconSelectedRight(getContext().getDrawable(R.drawable.ic_page_flipping_checked));
                        return;
                    }
                }
                if (Intrinsics.areEqual(button, this.binding.cbPageSx)) {
                    ReadConfig readConfig5 = ReadConfig.INSTANCE;
                    readConfig5.setPageAnim(1);
                    getCallback().scrollAnimChange();
                    if (readConfig5.getColorMode() == 2) {
                        this.binding.cbPageSx.getHelper().setIconSelectedRight(getContext().getDrawable(R.drawable.ic_page_flipping_checked_night));
                    } else {
                        this.binding.cbPageSx.getHelper().setIconSelectedRight(getContext().getDrawable(R.drawable.ic_page_flipping_checked));
                    }
                }
            }
        }
    }

    @Override // android.view.View
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(@Nullable MotionEvent event) {
        return true;
    }

    public final void runMenuIn() {
        this.listenerFlag = false;
        getCallback().onMenuShow();
        ViewExtensionsKt.visible(this);
        Toolbar toolbar = this.binding.titleBar;
        Intrinsics.checkNotNullExpressionValue(toolbar, "binding.titleBar");
        ViewExtensionsKt.visible(toolbar);
        LinearLayout linearLayout = this.binding.llBottomMenu;
        Intrinsics.checkNotNullExpressionValue(linearLayout, "binding.llBottomMenu");
        ViewExtensionsKt.visible(linearLayout);
        LinearLayout linearLayout2 = this.binding.llTop;
        Intrinsics.checkNotNullExpressionValue(linearLayout2, "binding.llTop");
        ViewExtensionsKt.visible(linearLayout2);
        LinearLayout linearLayout3 = this.binding.lySetting;
        Intrinsics.checkNotNullExpressionValue(linearLayout3, "binding.lySetting");
        ViewExtensionsKt.gone(linearLayout3);
        LinearLayout linearLayout4 = this.binding.lyPageMethod;
        Intrinsics.checkNotNullExpressionValue(linearLayout4, "binding.lyPageMethod");
        ViewExtensionsKt.gone(linearLayout4);
        this.binding.titleBar.startAnimation(getMenuTopIn());
        this.binding.llBottomMenu.startAnimation(getMenuBottomIn());
        BookInfo book = ReadBook.INSTANCE.getBook();
        this.binding.tvDownload.setVisibility(TextUtils.equals("1", book != null ? book.isAllowDownload() : null) ? 0 : 8);
        RCheckBox rCheckBox = this.binding.cbReview;
        ReadConfig readConfig = ReadConfig.INSTANCE;
        rCheckBox.setChecked(!readConfig.isShowReview());
        this.binding.cbNote.setChecked(!readConfig.isShowNote());
        setCbColorMode();
        setCbNoteColorMode();
        int pageAnim = readConfig.getPageAnim();
        if (pageAnim == 1) {
            this.binding.cbPageSx.setChecked(true);
            this.binding.cbPageFz.setChecked(false);
            this.binding.cbPageZy.setChecked(false);
        } else if (pageAnim == 2) {
            this.binding.cbPageFz.setChecked(true);
            this.binding.cbPageZy.setChecked(false);
            this.binding.cbPageSx.setChecked(false);
        } else if (pageAnim == 4) {
            this.binding.cbPageSx.setChecked(false);
            this.binding.cbPageFz.setChecked(false);
            this.binding.cbPageZy.setChecked(true);
        }
        int colorMode = readConfig.getColorMode();
        if (colorMode == 0) {
            this.binding.bgWhite.setChecked(true);
            this.binding.bgYellow.setChecked(false);
            this.binding.bgBlue.setChecked(false);
            this.binding.cbDarkMode.setChecked(false);
        } else if (colorMode == 1) {
            this.binding.bgWhite.setChecked(false);
            this.binding.bgYellow.setChecked(true);
            this.binding.bgBlue.setChecked(false);
            this.binding.cbDarkMode.setChecked(false);
        } else if (colorMode == 2) {
            this.binding.bgWhite.setChecked(false);
            this.binding.bgYellow.setChecked(false);
            this.binding.bgBlue.setChecked(true);
            this.binding.cbDarkMode.setChecked(true);
        }
        this.listenerFlag = true;
    }

    public final void runMenuOut(@Nullable Function0<Unit> onMenuOutEnd) {
        getCallback().onMenuHide();
        this.onMenuOutEnd = onMenuOutEnd;
        if (getVisibility() == 0) {
            this.binding.titleBar.startAnimation(getMenuTopOut());
            this.binding.llBottomMenu.startAnimation(getMenuBottomOut());
        }
    }

    public final void setAddLibaaryTv() {
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getColorMode() == 0 || readConfig.getColorMode() == 1) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.icon_addlibrary_no);
            Drawable drawable2 = ContextCompat.getDrawable(getContext(), R.drawable.icon_addlibrary_yes);
            BookInfo book = ReadBook.INSTANCE.getBook();
            if (Intrinsics.areEqual(book != null ? book.isBookshelf() : null, "1")) {
                this.binding.tvAddlibrary.setText("已在书架");
                this.binding.tvAddlibrary.getHelper().setTextColorNormal(AppCtxKt.getAppCtx().getColor(R.color.color_909090));
                RTextViewHelper helper = this.binding.tvAddlibrary.getHelper();
                if (helper != null) {
                    helper.setIconNormalTop(drawable2);
                    return;
                }
                return;
            }
            this.binding.tvAddlibrary.setText("加入书架");
            this.binding.tvAddlibrary.getHelper().setTextColorNormal(AppCtxKt.getAppCtx().getColor(R.color.color_303030));
            RTextViewHelper helper2 = this.binding.tvAddlibrary.getHelper();
            if (helper2 != null) {
                helper2.setIconNormalTop(drawable);
                return;
            }
            return;
        }
        Drawable drawable3 = ContextCompat.getDrawable(getContext(), R.drawable.icon_addlibrary_no_night);
        Drawable drawable4 = ContextCompat.getDrawable(getContext(), R.drawable.icon_addlibrary_yes_night);
        BookInfo book2 = ReadBook.INSTANCE.getBook();
        if (Intrinsics.areEqual(book2 != null ? book2.isBookshelf() : null, "1")) {
            this.binding.tvAddlibrary.setText("已在书架");
            this.binding.tvAddlibrary.getHelper().setTextColorNormal(AppCtxKt.getAppCtx().getColor(R.color.color_575F79));
            RTextViewHelper helper3 = this.binding.tvAddlibrary.getHelper();
            if (helper3 != null) {
                helper3.setIconNormalTop(drawable4);
                return;
            }
            return;
        }
        this.binding.tvAddlibrary.setText("加入书架");
        this.binding.tvAddlibrary.getHelper().setTextColorNormal(AppCtxKt.getAppCtx().getColor(R.color.color_7380a9));
        RTextViewHelper helper4 = this.binding.tvAddlibrary.getHelper();
        if (helper4 != null) {
            helper4.setIconNormalTop(drawable3);
        }
    }

    public final void setCanShowMenu(boolean z2) {
        this.canShowMenu = z2;
    }

    @SuppressLint({"SetTextI18n"})
    public final void setCommentNumber(int number) throws SecurityException {
        this.binding.tvCommentNum.setText(NumberUtilKt.toTenThousand(number));
        if (ReadConfig.INSTANCE.getColorMode() == 2) {
            RTextViewHelper helper = this.binding.tvCommentNum.getHelper();
            if (helper == null) {
                return;
            }
            helper.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_B2575C));
            return;
        }
        RTextViewHelper helper2 = this.binding.tvCommentNum.getHelper();
        if (helper2 == null) {
            return;
        }
        helper2.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_fd4018));
    }

    public final void setProgress(@NotNull String progress) {
        Intrinsics.checkNotNullParameter(progress, "progress");
        this.readProgress = progress;
        updateProgressUi();
    }

    public final void setReadTime(long time) {
        this.time = time;
        updateReadTime();
    }

    public final void setSeekPage(int seek) {
        this.binding.seekReadPage.setValue(seek);
    }

    public final void setTime(long j2) {
        this.time = j2;
    }

    public final void upBookView() {
        ReadBook readBook = ReadBook.INSTANCE;
        if (readBook.getCurTextChapter() != null) {
            upSeekBar();
            this.binding.tvPreview.setEnabled(readBook.getDurChapterIndex() != 0);
            this.binding.tvNext.setEnabled(readBook.getDurChapterIndex() != readBook.getChapterSize() - 1);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void upSeekBar() {
        NiftySlider niftySlider = this.binding.seekReadPage;
        ReadBook readBook = ReadBook.INSTANCE;
        if (readBook.getCurTextChapter() != null) {
            int chapterSize = readBook.getChapterSize() - 1;
            if (chapterSize <= 0) {
                niftySlider.setValueFrom(0.0f);
                niftySlider.setValueTo(1.0f);
            } else {
                niftySlider.setValueFrom(0.0f);
                niftySlider.setValueTo(chapterSize);
            }
            niftySlider.setValue(readBook.getDurChapterIndex());
        }
    }

    public /* synthetic */ ReadMenu(Context context, AttributeSet attributeSet, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet);
    }
}

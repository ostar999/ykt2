package com.ykb.ebook.activity;

import android.content.AppCtxKt;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ColorResourcesKt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.lang.RegexPool;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.util.SmartGlideImageLoader;
import com.plv.socket.user.PLVSocketUserConstant;
import com.psychiatrygarden.utils.Constants;
import com.ruffian.library.widget.RImageView;
import com.ruffian.library.widget.RTextView;
import com.ruffian.library.widget.helper.RTextViewHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R2;
import com.ykb.ebook.R;
import com.ykb.ebook.adapter.CommonAdapter;
import com.ykb.ebook.adapter.base.QuickViewHolder;
import com.ykb.ebook.base.BaseVmActivity;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.databinding.LayoutReplyCollectionBinding;
import com.ykb.ebook.dialog.CommentItemChooseDialog;
import com.ykb.ebook.dialog.ReportReasonChooseDialog;
import com.ykb.ebook.dialog.WriteSpDialog;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.extensions.ConvertExtensionsKt;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.model.BookReview;
import com.ykb.ebook.model.CommentData;
import com.ykb.ebook.model.SubFloorComments;
import com.ykb.ebook.util.FilePathUtilKt;
import com.ykb.ebook.util.FileUtils;
import com.ykb.ebook.util.ImageLoader;
import com.ykb.ebook.util.StatusBarUtil;
import com.ykb.ebook.util.ToastUtilsKt;
import com.ykb.ebook.util.ViewUtilKt;
import com.ykb.ebook.vm.ReplyCollectionViewModel;
import com.ykb.ebook.weight.FloorViews;
import com.ykb.ebook.weight.SubFloorFactorys;
import java.io.Serializable;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 -2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001-B\u0005¢\u0006\u0002\u0010\u0004J(\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00172\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010 \u001a\u00020\u0017H\u0002J.\u0010!\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00172\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#H\u0002J\b\u0010%\u001a\u00020\u001cH\u0014J\b\u0010&\u001a\u00020\u001cH\u0016J\b\u0010'\u001a\u00020\u001cH\u0002J\b\u0010(\u001a\u00020\u0003H\u0014J\b\u0010)\u001a\u00020\u001cH\u0014J\u0012\u0010*\u001a\u00020\u001c2\b\u0010+\u001a\u0004\u0018\u00010,H\u0014R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\b\u001a\u00020\u00028TX\u0094\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lcom/ykb/ebook/activity/ReplyCollectionAct;", "Lcom/ykb/ebook/base/BaseVmActivity;", "Lcom/ykb/ebook/databinding/LayoutReplyCollectionBinding;", "Lcom/ykb/ebook/vm/ReplyCollectionViewModel;", "()V", "albumLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "binding", "getBinding", "()Lcom/ykb/ebook/databinding/LayoutReplyCollectionBinding;", "binding$delegate", "Lkotlin/Lazy;", "bookId", "", "chapterId", "isBookReview", "", "item", "Lcom/ykb/ebook/model/BookReview;", "mAdapter", "Lcom/ykb/ebook/adapter/CommonAdapter;", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "paragraphContent", "paragraphId", "convert", "", "holder", "Lcom/ykb/ebook/adapter/base/QuickViewHolder;", "position", "type", "convertPayload", "payloads", "", "", "doBusiness", "initStatusBar", "initTheme", "initViewModel", "observeViewModel", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nReplyCollectionAct.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReplyCollectionAct.kt\ncom/ykb/ebook/activity/ReplyCollectionAct\n+ 2 ActivityViewBindings.kt\ncom/ykb/ebook/extensions/ActivityViewBindingsKt\n+ 3 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n+ 4 Background.kt\nsplitties/views/BackgroundKt\n*L\n1#1,994:1\n13#2,10:995\n42#3:1005\n42#3:1006\n42#3:1007\n42#3:1008\n42#3:1009\n42#3:1010\n42#3:1011\n42#3:1012\n42#3:1013\n42#3:1014\n42#3:1015\n42#3:1016\n42#3:1017\n42#3:1018\n42#3:1019\n42#3:1020\n42#3:1021\n42#3:1022\n42#3:1023\n42#3:1032\n42#3:1033\n32#4:1024\n32#4:1025\n32#4:1026\n32#4:1027\n32#4:1028\n32#4:1029\n32#4:1030\n32#4:1031\n*S KotlinDebug\n*F\n+ 1 ReplyCollectionAct.kt\ncom/ykb/ebook/activity/ReplyCollectionAct\n*L\n148#1:995,10\n206#1:1005\n207#1:1006\n212#1:1007\n213#1:1008\n220#1:1009\n221#1:1010\n226#1:1011\n227#1:1012\n228#1:1013\n233#1:1014\n234#1:1015\n235#1:1016\n244#1:1017\n523#1:1018\n594#1:1019\n598#1:1020\n602#1:1021\n647#1:1022\n649#1:1023\n984#1:1032\n988#1:1033\n727#1:1024\n728#1:1025\n735#1:1026\n737#1:1027\n938#1:1028\n942#1:1029\n943#1:1030\n953#1:1031\n*E\n"})
/* loaded from: classes6.dex */
public final class ReplyCollectionAct extends BaseVmActivity<LayoutReplyCollectionBinding, ReplyCollectionViewModel> {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private ActivityResultLauncher<Intent> albumLauncher;

    /* renamed from: binding$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy binding;

    @Nullable
    private BookReview item;
    private CommonAdapter<BookReview> mAdapter;
    private int page = 1;
    private int pageSize = 20;

    @NotNull
    private String bookId = "";
    private boolean isBookReview = true;

    @NotNull
    private String chapterId = "";

    @NotNull
    private String paragraphId = "";

    @NotNull
    private String paragraphContent = "";

    public ReplyCollectionAct() {
        final boolean z2 = false;
        this.binding = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) new Function0<LayoutReplyCollectionBinding>() { // from class: com.ykb.ebook.activity.ReplyCollectionAct$special$$inlined$viewBindingActivity$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final LayoutReplyCollectionBinding invoke() {
                LayoutInflater layoutInflater = this.getLayoutInflater();
                Intrinsics.checkNotNullExpressionValue(layoutInflater, "layoutInflater");
                LayoutReplyCollectionBinding layoutReplyCollectionBindingInflate = LayoutReplyCollectionBinding.inflate(layoutInflater);
                if (z2) {
                    this.setContentView(layoutReplyCollectionBindingInflate.getRoot());
                }
                return layoutReplyCollectionBindingInflate;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void convert(final QuickViewHolder holder, final int position, final BookReview item, int type) throws SecurityException {
        RImageView rImageView = (RImageView) holder.getView(R.id.img_avatar);
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getColorMode() == 0) {
            holder.itemView.setBackground(new ColorDrawable(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_f9fafb)));
        }
        LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.ly_title_view);
        TextView textView = (TextView) holder.getView(R.id.tv_newest_review);
        LinearLayout linearLayout2 = (LinearLayout) holder.getView(R.id.ly_look_more);
        TextView textView2 = (TextView) holder.getView(R.id.tv_more_value);
        LinearLayout linearLayout3 = (LinearLayout) holder.getView(R.id.ly_content_view);
        View view = holder.getView(R.id.viewLineOne);
        if (item.isHot()) {
            ViewExtensionsKt.visible(linearLayout);
            ViewExtensionsKt.gone(linearLayout3);
            textView.setText(item.getShowName());
        } else {
            ViewExtensionsKt.gone(linearLayout);
            ViewExtensionsKt.visible(linearLayout3);
        }
        if (item.getOtherView() == 3) {
            ViewExtensionsKt.visible(linearLayout2);
            textView2.setText(item.getShowName());
        } else {
            ViewExtensionsKt.gone(linearLayout2);
        }
        ImageLoader imageLoader = ImageLoader.INSTANCE;
        imageLoader.load(this, item.getAvatar()).placeholder(R.drawable.personal_headimg_icon).into(rImageView);
        int i2 = new Regex(RegexPool.NUMBERS).matches(item.getSupportCount()) ? Integer.parseInt(item.getSupportCount()) : 0;
        int i3 = new Regex(RegexPool.NUMBERS).matches(item.getOppositionCount()) ? Integer.parseInt(item.getOppositionCount()) : 0;
        int i4 = R.id.tv_support;
        RTextView rTextView = (RTextView) holder.getView(i4);
        int i5 = R.id.tv_opposition;
        RTextView rTextView2 = (RTextView) holder.getView(i5);
        rTextView.setText("赞同(" + item.getSupportCount() + ')');
        rTextView.setSelected(item.isSupport() == 1);
        rTextView2.setText("反对(" + item.getOppositionCount() + ')');
        rTextView2.setSelected(item.isOpposition() == 1);
        if (i2 > i3) {
            rTextView.setTextColor(readConfig.getColorMode() != 2 ? getColor(R.color.color_81cb30) : Color.parseColor("#6AA064"));
            rTextView2.setTextColor(getColor(readConfig.getColorMode() != 2 ? R.color.color_c2c6cb : R.color.color_575F79));
        } else if (i2 < i3) {
            rTextView2.setTextColor(getColor(readConfig.getColorMode() != 2 ? R.color.color_F95843 : R.color.color_B2575C));
            rTextView.setTextColor(getColor(readConfig.getColorMode() != 2 ? R.color.color_c2c6cb : R.color.color_575F79));
        } else {
            rTextView.setTextColor(getColor(readConfig.getColorMode() != 2 ? R.color.color_c2c6cb : R.color.color_575F79));
            rTextView2.setTextColor(getColor(readConfig.getColorMode() != 2 ? R.color.color_c2c6cb : R.color.color_575F79));
        }
        int i6 = R.id.tv_time;
        ViewGroup.LayoutParams layoutParams = ((TextView) holder.getView(i6)).getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
        if (TextUtils.isEmpty(item.getSchool())) {
            layoutParams2.leftMargin = 0;
        } else {
            layoutParams2.leftMargin = (int) ConvertExtensionsKt.dpToPx(12.0f);
        }
        ((TextView) holder.getView(i6)).setLayoutParams(layoutParams2);
        BaseViewHolder text = holder.setText(R.id.tv_nick_name, item.getNickname());
        int i7 = R.id.tv_hospital;
        BaseViewHolder textColor = text.setGone(i7, TextUtils.isEmpty(item.getSchool())).setText(i7, item.getSchool()).setTextColor(i7, ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090)).setText(i6, item.getCtime()).setTextColor(i6, ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
        int i8 = R.id.tv_comment;
        BaseViewHolder textColor2 = textColor.setText(i8, item.getComment()).setTextColor(i8, ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030));
        int i9 = R.id.img_picture;
        textColor2.setGone(i9, item.getPicture().length() == 0);
        RImageView rImageView2 = (RImageView) holder.getView(i9);
        if (item.getPicture().length() > 0) {
            imageLoader.load(this, item.getPicture()).into(rImageView2);
        }
        rImageView2.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.t1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ReplyCollectionAct.convert$lambda$18(this.f26199c, item, view2);
            }
        });
        FloorViews floorViews = (FloorViews) holder.getView(R.id.floor);
        SubFloorComments subFloorComments = new SubFloorComments(item.getReplyList());
        if (subFloorComments.size() > 0) {
            floorViews.setVisibility(0);
        } else {
            floorViews.setVisibility(8);
        }
        CommonAdapter<BookReview> commonAdapter = this.mAdapter;
        if (commonAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            commonAdapter = null;
        }
        floorViews.setComments(subFloorComments, false, commonAdapter);
        floorViews.setFactory(new SubFloorFactorys());
        floorViews.setIslouzhu("1");
        if (readConfig.getColorMode() != 2) {
            floorViews.setBoundDrawer(AppCtxKt.getAppCtx().getResources().getDrawable(R.drawable.ebook_bondcomm));
        } else {
            floorViews.setBoundDrawer(AppCtxKt.getAppCtx().getResources().getDrawable(R.drawable.ebook_bondcomm_night));
        }
        floorViews.init();
        RTextView rTextView3 = (RTextView) holder.getView(R.id.tv_reply);
        rTextView3.getHelper().setBackgroundColorNormal(item.getReplyCount() > 0 ? AppCtxKt.getAppCtx().getColor(R.color.color_0a000000) : AppCtxKt.getAppCtx().getColor(android.R.color.transparent));
        String str = "回复";
        if (item.getReplyCount() > 0) {
            str = item.getReplyCount() + "回复";
        }
        rTextView3.setText(str);
        rTextView3.setSelected(item.getReplyCount() > 0);
        int iColor = ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_bfbfbf);
        int iColor2 = ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030);
        RTextViewHelper helper = rTextView3.getHelper();
        if (helper != null) {
            helper.setTextColorNormal(iColor);
        }
        RTextViewHelper helper2 = rTextView3.getHelper();
        if (helper2 != null) {
            helper2.setTextColorSelected(iColor2);
        }
        ((TextView) holder.getView(i4)).setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.e2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ReplyCollectionAct.convert$lambda$19(item, holder, this, position, view2);
            }
        });
        ((TextView) holder.getView(i5)).setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.h2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ReplyCollectionAct.convert$lambda$20(item, holder, this, position, view2);
            }
        });
        rTextView3.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.i2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ReplyCollectionAct.convert$lambda$21(item, this, position, view2);
            }
        });
        rImageView2.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.j2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ReplyCollectionAct.convert$lambda$22(this.f26152c, item, view2);
            }
        });
        int colorMode = readConfig.getColorMode();
        if (colorMode == 1) {
            int i10 = R.color.color_yellow_theme_bg;
            linearLayout.setBackgroundColor(getColor(i10));
            linearLayout2.setBackgroundColor(getColor(i10));
            View view2 = holder.getView(R.id.v_line);
            int i11 = R.color.color_EDE2C3;
            view2.setBackgroundColor(getColor(i11));
            view.setBackgroundColor(getColor(i11));
        } else if (colorMode == 2) {
            int i12 = R.color.color_blue_theme_bg;
            linearLayout.setBackgroundColor(getColor(i12));
            linearLayout2.setBackgroundColor(getColor(i12));
            View view3 = holder.getView(R.id.v_line);
            int i13 = R.color.color_theme_blue_line_color;
            view3.setBackgroundColor(getColor(i13));
            view.setBackgroundColor(getColor(i13));
            TextView textView3 = (TextView) holder.getView(i7);
            int i14 = R.color.color_575F79;
            textView3.setTextColor(getColor(i14));
            ((TextView) holder.getView(i6)).setTextColor(getColor(i14));
            ((TextView) holder.getView(i8)).setTextColor(getColor(R.color.color_7380a9));
            rTextView.getHelper().setTextColorNormal(getColor(i14));
            rTextView2.getHelper().setTextColorNormal(getColor(i14));
            rTextView3.getHelper().setTextColorNormal(getColor(i14));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.k2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view4) {
                ReplyCollectionAct.convert$lambda$23(item, holder, this, position, view4);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$18(ReplyCollectionAct this$0, BookReview item, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        new XPopup.Builder(this$0).asImageViewer(null, item.getPicture(), new SmartGlideImageLoader(true, R.drawable.imgplacehodel_image)).isShowSaveButton(false).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$19(BookReview item, QuickViewHolder holder, ReplyCollectionAct this$0, int i2, View view) {
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(holder, "$holder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (item.isOpposition() == 1) {
            return;
        }
        if (item.isSupport() == 0) {
            ViewUtilKt.toastPop(holder.getView(R.id.tv_support), 0);
        }
        this$0.getViewModel().addSupportOrOppse(item, true, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$20(BookReview item, QuickViewHolder holder, ReplyCollectionAct this$0, int i2, View view) {
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(holder, "$holder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (item.isSupport() == 1) {
            return;
        }
        if (item.isOpposition() == 0) {
            ViewUtilKt.toastPop(holder.getView(R.id.tv_opposition), 1);
        }
        this$0.getViewModel().addSupportOrOppse(item, false, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$21(final BookReview item, final ReplyCollectionAct this$0, final int i2, View view) {
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (item.getReplyCount() > 0) {
            if (this$0.isBookReview) {
                Companion companion = INSTANCE;
                Context context = view.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "it.context");
                companion.newIntent(context, this$0.bookId, item, true);
                return;
            }
            Companion companion2 = INSTANCE;
            Context context2 = view.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "it.context");
            companion2.newIntent(context2, this$0.bookId, this$0.chapterId, this$0.paragraphId, this$0.paragraphContent, item, false);
            return;
        }
        String id = item.getId();
        String str = "回复：" + item.getNickname();
        String str2 = "";
        String str3 = "";
        ActivityResultLauncher<Intent> activityResultLauncher = this$0.albumLauncher;
        if (activityResultLauncher == null) {
            Intrinsics.throwUninitializedPropertyAccessException("albumLauncher");
            activityResultLauncher = null;
        }
        new WriteSpDialog.Builder(this$0, this$0, id, str, str2, str3, activityResultLauncher, false, false, false, R2.attr.carousel_previousState, null).setOnPublishClick(new Function3<String, String, String, Unit>() { // from class: com.ykb.ebook.activity.ReplyCollectionAct$convert$4$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(String str4, String str5, String str6) {
                invoke2(str4, str5, str6);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull String path, @NotNull String content, @NotNull String str4) {
                Intrinsics.checkNotNullParameter(path, "path");
                Intrinsics.checkNotNullParameter(content, "content");
                Intrinsics.checkNotNullParameter(str4, "<anonymous parameter 2>");
                if (this.this$0.isBookReview) {
                    item.setReviewType("1");
                    this.this$0.getViewModel().publishBookReview(true, content, this.this$0.bookId, path, i2, item);
                } else {
                    BookReview bookReview = item;
                    Intrinsics.checkNotNull(bookReview);
                    bookReview.setReviewType("3");
                    this.this$0.getViewModel().addParagraphReview(true, this.this$0.bookId, this.this$0.chapterId, this.this$0.paragraphId, this.this$0.paragraphContent, item.getId(), path, content, item, i2);
                }
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$22(ReplyCollectionAct this$0, BookReview item, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        new XPopup.Builder(this$0).asImageViewer(null, item.getPicture(), new SmartGlideImageLoader(true, R.drawable.imgplacehodel_image)).isShowSaveButton(false).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$23(final BookReview item, final QuickViewHolder holder, final ReplyCollectionAct this$0, final int i2, View view) {
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(holder, "$holder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        boolean zAreEqual = Intrinsics.areEqual(item.getUserId(), ContextExtensionsKt.getPrefString$default(AppCtxKt.getAppCtx(), "user_id", null, 2, null));
        Context context = holder.itemView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "holder.itemView.context");
        new CommentItemChooseDialog.Builder(context, zAreEqual, item.getNickname() + (char) 65306 + item.getComment()).setOnItemClick(new Function1<String, Unit>() { // from class: com.ykb.ebook.activity.ReplyCollectionAct$convert$6$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull String it) {
                ActivityResultLauncher activityResultLauncher;
                ActivityResultLauncher activityResultLauncher2;
                Intrinsics.checkNotNullParameter(it, "it");
                switch (it.hashCode()) {
                    case 646183:
                        if (it.equals("举报")) {
                            Context context2 = holder.itemView.getContext();
                            Intrinsics.checkNotNullExpressionValue(context2, "holder.itemView.context");
                            ReportReasonChooseDialog.Builder builder = new ReportReasonChooseDialog.Builder(context2, item.getComment(), item.getId(), true);
                            final ReplyCollectionAct replyCollectionAct = this.this$0;
                            final BookReview bookReview = item;
                            builder.setOnItemClick(new Function2<Integer, String, Unit>() { // from class: com.ykb.ebook.activity.ReplyCollectionAct$convert$6$1.4
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public /* bridge */ /* synthetic */ Unit invoke(Integer num, String str) {
                                    invoke(num.intValue(), str);
                                    return Unit.INSTANCE;
                                }

                                public final void invoke(int i3, @NotNull String str) {
                                    Intrinsics.checkNotNullParameter(str, "str");
                                    replyCollectionAct.getViewModel().commentReport(bookReview.getId(), String.valueOf(i3 + 1), str);
                                }
                            }).show();
                            break;
                        }
                        break;
                    case 690244:
                        if (it.equals("删除")) {
                            this.this$0.getViewModel().delComment(i2, item);
                            break;
                        }
                        break;
                    case 712175:
                        if (it.equals("回复")) {
                            ReplyCollectionAct replyCollectionAct2 = this.this$0;
                            String id = item.getId();
                            String str = "回复：" + item.getNickname();
                            ActivityResultLauncher activityResultLauncher3 = this.this$0.albumLauncher;
                            if (activityResultLauncher3 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("albumLauncher");
                                activityResultLauncher = null;
                            } else {
                                activityResultLauncher = activityResultLauncher3;
                            }
                            WriteSpDialog.Builder builder2 = new WriteSpDialog.Builder(replyCollectionAct2, replyCollectionAct2, id, str, "", "", activityResultLauncher, false, false, false, R2.attr.carousel_previousState, null);
                            final ReplyCollectionAct replyCollectionAct3 = this.this$0;
                            final BookReview bookReview2 = item;
                            final int i3 = i2;
                            builder2.setOnPublishClick(new Function3<String, String, String, Unit>() { // from class: com.ykb.ebook.activity.ReplyCollectionAct$convert$6$1.1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public /* bridge */ /* synthetic */ Unit invoke(String str2, String str3, String str4) {
                                    invoke2(str2, str3, str4);
                                    return Unit.INSTANCE;
                                }

                                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2(@NotNull String path, @NotNull String content, @NotNull String str2) {
                                    Intrinsics.checkNotNullParameter(path, "path");
                                    Intrinsics.checkNotNullParameter(content, "content");
                                    Intrinsics.checkNotNullParameter(str2, "<anonymous parameter 2>");
                                    if (replyCollectionAct3.isBookReview) {
                                        bookReview2.setReviewType("1");
                                        replyCollectionAct3.getViewModel().publishBookReview(true, content, replyCollectionAct3.bookId, path, i3, bookReview2);
                                    } else {
                                        bookReview2.setReviewType("3");
                                        replyCollectionAct3.getViewModel().addParagraphReview(true, replyCollectionAct3.bookId, replyCollectionAct3.chapterId, replyCollectionAct3.paragraphId, replyCollectionAct3.paragraphContent, bookReview2.getId(), path, content, bookReview2, i3);
                                    }
                                }
                            }).show();
                            break;
                        }
                        break;
                    case 727753:
                        if (it.equals("复制")) {
                            FileUtils fileUtils = FileUtils.INSTANCE;
                            Context context3 = holder.itemView.getContext();
                            Intrinsics.checkNotNullExpressionValue(context3, "holder.itemView.context");
                            BookReview bookReview3 = item;
                            Intrinsics.checkNotNull(bookReview3);
                            fileUtils.copyContent(context3, bookReview3.getComment());
                            break;
                        }
                        break;
                    case 761248:
                        if (it.equals("封禁")) {
                            Context context4 = holder.itemView.getContext();
                            Intrinsics.checkNotNullExpressionValue(context4, "holder.itemView.context");
                            ReportReasonChooseDialog.Builder builder3 = new ReportReasonChooseDialog.Builder(context4, item.getComment(), item.getId(), false);
                            final ReplyCollectionAct replyCollectionAct4 = this.this$0;
                            final BookReview bookReview4 = item;
                            builder3.setOnItemClick(new Function2<Integer, String, Unit>() { // from class: com.ykb.ebook.activity.ReplyCollectionAct$convert$6$1.3
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public /* bridge */ /* synthetic */ Unit invoke(Integer num, String str2) {
                                    invoke(num.intValue(), str2);
                                    return Unit.INSTANCE;
                                }

                                public final void invoke(int i4, @NotNull String str2) {
                                    Intrinsics.checkNotNullParameter(str2, "str");
                                    replyCollectionAct4.getViewModel().accountBanned(String.valueOf(i4 + 1), str2, bookReview4.getUserId());
                                }
                            }).show();
                            break;
                        }
                        break;
                    case 1045307:
                        if (it.equals("编辑")) {
                            ReplyCollectionAct replyCollectionAct5 = this.this$0;
                            String id2 = item.getId();
                            String str2 = "回复：" + item.getNickname();
                            String comment = item.getComment();
                            String picture = item.getPicture();
                            ActivityResultLauncher activityResultLauncher4 = this.this$0.albumLauncher;
                            if (activityResultLauncher4 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("albumLauncher");
                                activityResultLauncher2 = null;
                            } else {
                                activityResultLauncher2 = activityResultLauncher4;
                            }
                            WriteSpDialog.Builder builder4 = new WriteSpDialog.Builder(replyCollectionAct5, replyCollectionAct5, id2, str2, comment, picture, activityResultLauncher2, false, false, false, R2.attr.carousel_previousState, null);
                            final ReplyCollectionAct replyCollectionAct6 = this.this$0;
                            final BookReview bookReview5 = item;
                            final int i4 = i2;
                            builder4.setOnPublishClick(new Function3<String, String, String, Unit>() { // from class: com.ykb.ebook.activity.ReplyCollectionAct$convert$6$1.2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public /* bridge */ /* synthetic */ Unit invoke(String str3, String str4, String str5) {
                                    invoke2(str3, str4, str5);
                                    return Unit.INSTANCE;
                                }

                                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2(@NotNull String path, @NotNull String content, @NotNull String str3) {
                                    Intrinsics.checkNotNullParameter(path, "path");
                                    Intrinsics.checkNotNullParameter(content, "content");
                                    Intrinsics.checkNotNullParameter(str3, "<anonymous parameter 2>");
                                    replyCollectionAct6.getViewModel().editComment(content, bookReview5.getId(), path, i4, bookReview5);
                                }
                            }).show();
                            break;
                        }
                        break;
                }
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void convertPayload(QuickViewHolder holder, int position, BookReview item, List<? extends Object> payloads) throws SecurityException {
        BaseViewHolder text = holder.setText(R.id.tv_comment, item.getComment());
        int i2 = R.id.img_picture;
        text.setGone(i2, item.getPicture().length() == 0);
        RImageView rImageView = (RImageView) holder.getView(i2);
        if (item.getPicture().length() > 0) {
            ImageLoader.INSTANCE.load(this, item.getPicture()).into(rImageView);
        }
        int i3 = R.id.tv_support;
        QuickViewHolder selected = holder.setSelected(i3, item.isSupport() == 1);
        int i4 = R.id.tv_opposition;
        selected.setSelected(i4, item.isOpposition() == 1);
        RTextView rTextView = (RTextView) holder.getView(R.id.tv_reply);
        rTextView.getHelper().setBackgroundColorNormal(item.getReplyCount() > 0 ? AppCtxKt.getAppCtx().getColor(R.color.color_0a000000) : AppCtxKt.getAppCtx().getColor(android.R.color.transparent));
        rTextView.getHelper().setSelected(item.getReplyCount() > 0);
        rTextView.setSelected(item.getReplyCount() > 0);
        RTextView rTextView2 = (RTextView) holder.getView(i3);
        RTextView rTextView3 = (RTextView) holder.getView(i4);
        rTextView2.setText("赞同(" + item.getSupportCount() + ')');
        rTextView2.setSelected(item.isSupport() == 1);
        rTextView3.setText("反对(" + item.getOppositionCount() + ')');
        rTextView3.setSelected(item.isOpposition() == 1);
        int i5 = new Regex(RegexPool.NUMBERS).matches(item.getSupportCount()) ? Integer.parseInt(item.getSupportCount()) : 0;
        int i6 = new Regex(RegexPool.NUMBERS).matches(item.getOppositionCount()) ? Integer.parseInt(item.getOppositionCount()) : 0;
        if (i5 > i6) {
            ReadConfig readConfig = ReadConfig.INSTANCE;
            rTextView2.setTextColor(readConfig.getColorMode() != 2 ? getColor(R.color.color_81cb30) : Color.parseColor("#6AA064"));
            rTextView3.setTextColor(getColor(readConfig.getColorMode() != 2 ? R.color.color_c2c6cb : R.color.color_575F79));
        } else if (i5 < i6) {
            ReadConfig readConfig2 = ReadConfig.INSTANCE;
            rTextView3.setTextColor(getColor(readConfig2.getColorMode() != 2 ? R.color.color_F95843 : R.color.color_B2575C));
            rTextView2.setTextColor(getColor(readConfig2.getColorMode() != 2 ? R.color.color_c2c6cb : R.color.color_575F79));
        } else {
            ReadConfig readConfig3 = ReadConfig.INSTANCE;
            rTextView2.setTextColor(getColor(readConfig3.getColorMode() != 2 ? R.color.color_c2c6cb : R.color.color_575F79));
            rTextView3.setTextColor(getColor(readConfig3.getColorMode() != 2 ? R.color.color_c2c6cb : R.color.color_575F79));
        }
        String str = "回复";
        if (item.getReplyCount() > 0) {
            str = item.getReplyCount() + "回复";
        }
        rTextView.setText(str);
        rTextView.getHelper().setSelected(item.getReplyCount() > 0);
    }

    private final void initTheme() throws SecurityException {
        RefreshHeader refreshHeader;
        ReadConfig readConfig = ReadConfig.INSTANCE;
        int colorMode = readConfig.getColorMode();
        if (colorMode == 0) {
            RecyclerView recyclerView = getBinding().rvNewest;
            Intrinsics.checkNotNullExpressionValue(recyclerView, "binding.rvNewest");
            recyclerView.setBackgroundColor(getColor(R.color.color_f9fafb));
        } else if (colorMode == 1) {
            LinearLayout linearLayout = getBinding().layoutItemRoot;
            Intrinsics.checkNotNullExpressionValue(linearLayout, "binding.layoutItemRoot");
            int i2 = R.color.color_FEEEC6;
            linearLayout.setBackgroundColor(getColor(i2));
            RecyclerView recyclerView2 = getBinding().rvNewest;
            Intrinsics.checkNotNullExpressionValue(recyclerView2, "binding.rvNewest");
            recyclerView2.setBackgroundColor(getColor(i2));
            getBinding().imgBack.setImageResource(R.drawable.icon_black_back);
            getBinding().toolbarTitle.setTextColor(getColor(R.color.color_303030));
            getBinding().tvAddSp.getHelper().setBackgroundColorNormal(getColor(R.color.color_EDE2C3));
            getBinding().tvAddSp.setTextColor(getColor(R.color.color_bfbfbf));
        } else if (colorMode == 2) {
            LinearLayout linearLayout2 = getBinding().layoutItemRoot;
            Intrinsics.checkNotNullExpressionValue(linearLayout2, "binding.layoutItemRoot");
            linearLayout2.setBackgroundColor(getColor(R.color.color_1C2134));
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.icon_black_back);
            if (drawable != null) {
                drawable.setColorFilter(getColor(R.color.color_7380a9), PorterDuff.Mode.SRC_IN);
            }
            getBinding().imgBack.setImageDrawable(drawable);
            TextView textView = getBinding().toolbarTitle;
            int i3 = R.color.color_7380a9;
            textView.setTextColor(getColor(i3));
            getBinding().tvAddSp.getHelper().setBackgroundColorNormal(getColor(R.color.color_171C2D));
            RTextView rTextView = getBinding().tvAddSp;
            int i4 = R.color.color_575F79;
            rTextView.setTextColor(getColor(i4));
            getBinding().tvAddSp.getHelper().setIconNormal(getDrawable(R.mipmap.ic_add_comment_night));
            getBinding().tvHospital.setTextColor(getColor(i4));
            getBinding().tvTime.setTextColor(getColor(i4));
            getBinding().tvComment.setTextColor(getColor(i3));
            getBinding().tvSupport.getHelper().setTextColorNormal(getColor(i4));
            getBinding().tvOpposition.getHelper().setTextColorNormal(getColor(i4));
            getBinding().tvReply.getHelper().setTextColorNormal(getColor(i4));
            getBinding().tvReply.getHelper().setTextColorSelected(getColor(i3));
        }
        int colorMode2 = readConfig.getColorMode();
        SmartRefreshLayout smartRefreshLayout = getBinding().refreshLayout;
        Intrinsics.checkNotNull(smartRefreshLayout);
        ClassicsHeader classicsHeader = getBinding().refreshHeader;
        Intrinsics.checkNotNull(classicsHeader);
        ViewUtilKt.setRefreshTileView(colorMode2, smartRefreshLayout, classicsHeader, this);
        SmartRefreshLayout smartRefreshLayout2 = getBinding().refreshLayout;
        if (smartRefreshLayout2 == null || (refreshHeader = smartRefreshLayout2.getRefreshHeader()) == null) {
            return;
        }
        if (refreshHeader instanceof ClassicsHeader) {
            ((ClassicsHeader) refreshHeader).setAccentColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
        }
        RefreshFooter refreshFooter = smartRefreshLayout2.getRefreshFooter();
        if (refreshFooter == null || !(refreshFooter instanceof ClassicsFooter)) {
            return;
        }
        ((ClassicsFooter) refreshFooter).setAccentColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$11(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$12(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$13(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$14(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$15(ReplyCollectionAct this$0, Object obj) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ToastUtilsKt.toastOnUi$default(this$0, "举报成功", 0, 2, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$16(ReplyCollectionAct this$0, Object obj) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ToastUtilsKt.toastOnUi$default(this$0, "封禁成功", 0, 2, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$17(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$1(ReplyCollectionAct this$0, ActivityResult activityResult) {
        Uri data;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intent data2 = activityResult.getData();
        if (data2 == null || (data = data2.getData()) == null) {
            return;
        }
        FilePathUtilKt.getPathFromUri(this$0, data);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$10(ReplyCollectionAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        XPopup.Builder builder = new XPopup.Builder(this$0);
        BookReview bookReview = this$0.item;
        Intrinsics.checkNotNull(bookReview);
        builder.asImageViewer(null, bookReview.getPicture(), new SmartGlideImageLoader(true, R.drawable.imgplacehodel_image)).isShowSaveButton(false).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$3(ReplyCollectionAct this$0, RefreshLayout it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.page = 1;
        this$0.doBusiness();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$4(ReplyCollectionAct this$0, RefreshLayout it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.page++;
        this$0.doBusiness();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$5(ReplyCollectionAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$6(ReplyCollectionAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        BookReview bookReview = this$0.item;
        Intrinsics.checkNotNull(bookReview);
        if (bookReview.isOpposition() == 1) {
            return;
        }
        ReplyCollectionViewModel viewModel = this$0.getViewModel();
        BookReview bookReview2 = this$0.item;
        Intrinsics.checkNotNull(bookReview2);
        viewModel.addSupportOrOppse(bookReview2, true, -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$7(ReplyCollectionAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        BookReview bookReview = this$0.item;
        Intrinsics.checkNotNull(bookReview);
        if (bookReview.isSupport() == 1) {
            return;
        }
        ReplyCollectionViewModel viewModel = this$0.getViewModel();
        BookReview bookReview2 = this$0.item;
        Intrinsics.checkNotNull(bookReview2);
        viewModel.addSupportOrOppse(bookReview2, false, -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$8(final ReplyCollectionAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        BookReview bookReview = this$0.item;
        Intrinsics.checkNotNull(bookReview);
        String id = bookReview.getId();
        StringBuilder sb = new StringBuilder();
        sb.append("回复：");
        BookReview bookReview2 = this$0.item;
        Intrinsics.checkNotNull(bookReview2);
        sb.append(bookReview2.getNickname());
        String string = sb.toString();
        String str = "";
        String str2 = "";
        ActivityResultLauncher<Intent> activityResultLauncher = this$0.albumLauncher;
        if (activityResultLauncher == null) {
            Intrinsics.throwUninitializedPropertyAccessException("albumLauncher");
            activityResultLauncher = null;
        }
        new WriteSpDialog.Builder(this$0, this$0, id, string, str, str2, activityResultLauncher, false, false, false, R2.attr.carousel_previousState, null).setOnPublishClick(new Function3<String, String, String, Unit>() { // from class: com.ykb.ebook.activity.ReplyCollectionAct$onActivityCreated$10$1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(String str3, String str4, String str5) {
                invoke2(str3, str4, str5);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull String path, @NotNull String content, @NotNull String parentId) {
                Intrinsics.checkNotNullParameter(path, "path");
                Intrinsics.checkNotNullParameter(content, "content");
                Intrinsics.checkNotNullParameter(parentId, "parentId");
                if (this.this$0.isBookReview) {
                    BookReview bookReview3 = this.this$0.item;
                    Intrinsics.checkNotNull(bookReview3);
                    bookReview3.setReviewType("1");
                    this.this$0.getViewModel().publishBookReview(false, content, this.this$0.bookId, path, 0, this.this$0.item);
                    return;
                }
                BookReview bookReview4 = this.this$0.item;
                Intrinsics.checkNotNull(bookReview4);
                bookReview4.setReviewType("3");
                this.this$0.getViewModel().addParagraphReview(false, this.this$0.bookId, this.this$0.chapterId, this.this$0.paragraphId, this.this$0.paragraphContent, parentId, path, content, this.this$0.item, 0);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$9(final ReplyCollectionAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        BookReview bookReview = this$0.item;
        Intrinsics.checkNotNull(bookReview);
        String id = bookReview.getId();
        StringBuilder sb = new StringBuilder();
        sb.append("回复：");
        BookReview bookReview2 = this$0.item;
        Intrinsics.checkNotNull(bookReview2);
        sb.append(bookReview2.getNickname());
        String string = sb.toString();
        String str = "";
        String str2 = "";
        ActivityResultLauncher<Intent> activityResultLauncher = this$0.albumLauncher;
        if (activityResultLauncher == null) {
            Intrinsics.throwUninitializedPropertyAccessException("albumLauncher");
            activityResultLauncher = null;
        }
        new WriteSpDialog.Builder(this$0, this$0, id, string, str, str2, activityResultLauncher, false, false, false, R2.attr.carousel_previousState, null).setOnPublishClick(new Function3<String, String, String, Unit>() { // from class: com.ykb.ebook.activity.ReplyCollectionAct$onActivityCreated$11$1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(String str3, String str4, String str5) {
                invoke2(str3, str4, str5);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull String path, @NotNull String content, @NotNull String parentId) {
                Intrinsics.checkNotNullParameter(path, "path");
                Intrinsics.checkNotNullParameter(content, "content");
                Intrinsics.checkNotNullParameter(parentId, "parentId");
                if (this.this$0.isBookReview) {
                    BookReview bookReview3 = this.this$0.item;
                    Intrinsics.checkNotNull(bookReview3);
                    bookReview3.setReviewType("1");
                    this.this$0.getViewModel().publishBookReview(false, content, this.this$0.bookId, path, 0, this.this$0.item);
                    return;
                }
                BookReview bookReview4 = this.this$0.item;
                Intrinsics.checkNotNull(bookReview4);
                bookReview4.setReviewType("3");
                this.this$0.getViewModel().addParagraphReview(false, this.this$0.bookId, this.this$0.chapterId, this.this$0.paragraphId, this.this$0.paragraphContent, parentId, path, content, this.this$0.item, 0);
            }
        }).show();
    }

    @Override // com.ykb.ebook.base.BaseActivity
    public void doBusiness() {
        ReplyCollectionViewModel viewModel = getViewModel();
        int i2 = this.page;
        int i3 = this.pageSize;
        String str = this.bookId;
        BookReview bookReview = this.item;
        Intrinsics.checkNotNull(bookReview);
        viewModel.getBookReview(i2, i3, str, bookReview.getId());
    }

    @Override // com.ykb.ebook.base.BaseActivity
    public void initStatusBar() {
        int colorMode = ReadConfig.INSTANCE.getColorMode();
        if (colorMode == 0) {
            StatusBarUtil.setColor(this, getColor(R.color.white));
        } else if (colorMode == 1) {
            StatusBarUtil.setColor(this, getColor(R.color.color_yellow_theme_bg));
        } else {
            if (colorMode != 2) {
                return;
            }
            StatusBarUtil.setColor(this, getColor(R.color.color_blue_theme_bg));
        }
    }

    @Override // com.ykb.ebook.base.BaseVmActivity
    public void observeViewModel() {
        super.observeViewModel();
        MutableLiveData<CommentData> commentBookReview = getViewModel().getCommentBookReview();
        final Function1<CommentData, Unit> function1 = new Function1<CommentData, Unit>() { // from class: com.ykb.ebook.activity.ReplyCollectionAct.observeViewModel.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(CommentData commentData) {
                invoke2(commentData);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(CommentData commentData) {
                if (ReplyCollectionAct.this.page == 1) {
                    ReplyCollectionAct.this.getBinding().refreshLayout.finishRefresh();
                }
                if (commentData == null) {
                    ReplyCollectionAct.this.getBinding().refreshLayout.finishLoadMoreWithNoMoreData();
                    return;
                }
                List<BookReview> time_line = commentData.getTime_line();
                CommonAdapter commonAdapter = null;
                if (ReplyCollectionAct.this.page == 1) {
                    if (!(!time_line.isEmpty())) {
                        ReplyCollectionAct.this.getBinding().refreshLayout.finishLoadMoreWithNoMoreData();
                        return;
                    }
                    CommonAdapter commonAdapter2 = ReplyCollectionAct.this.mAdapter;
                    if (commonAdapter2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                    } else {
                        commonAdapter = commonAdapter2;
                    }
                    commonAdapter.submitList(time_line);
                    if (time_line.size() < ReplyCollectionAct.this.pageSize) {
                        ReplyCollectionAct.this.getBinding().refreshLayout.finishLoadMoreWithNoMoreData();
                        return;
                    }
                    return;
                }
                List<BookReview> list = time_line;
                if (!(true ^ list.isEmpty())) {
                    ReplyCollectionAct.this.getBinding().refreshLayout.finishLoadMoreWithNoMoreData();
                    return;
                }
                CommonAdapter commonAdapter3 = ReplyCollectionAct.this.mAdapter;
                if (commonAdapter3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                } else {
                    commonAdapter = commonAdapter3;
                }
                commonAdapter.addAll(list);
                if (time_line.size() < ReplyCollectionAct.this.pageSize) {
                    ReplyCollectionAct.this.getBinding().refreshLayout.finishLoadMoreWithNoMoreData();
                } else {
                    ReplyCollectionAct.this.getBinding().refreshLayout.finishLoadMore();
                }
            }
        };
        commentBookReview.observe(this, new Observer() { // from class: com.ykb.ebook.activity.z1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ReplyCollectionAct.observeViewModel$lambda$11(function1, obj);
            }
        });
        MutableLiveData<BookReview> supportAndOppose = getViewModel().getSupportAndOppose();
        final Function1<BookReview, Unit> function12 = new Function1<BookReview, Unit>() { // from class: com.ykb.ebook.activity.ReplyCollectionAct.observeViewModel.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BookReview bookReview) {
                invoke2(bookReview);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BookReview bookReview) {
                if (bookReview.getTmpPosition() > -1) {
                    if (bookReview.isActionSupport()) {
                        bookReview.setSupport(bookReview.isSupport() != 1 ? 1 : 0);
                        if (bookReview.isSupport() == 1) {
                            bookReview.setSupportCount(String.valueOf(Integer.parseInt(bookReview.getSupportCount()) + 1));
                        } else {
                            bookReview.setSupportCount(String.valueOf(Integer.parseInt(bookReview.getSupportCount()) - 1));
                        }
                    } else {
                        bookReview.setOpposition(bookReview.isOpposition() != 1 ? 1 : 0);
                        if (bookReview.isOpposition() == 1) {
                            bookReview.setOppositionCount(String.valueOf(Integer.parseInt(bookReview.getOppositionCount()) + 1));
                        } else {
                            bookReview.setOppositionCount(String.valueOf(Integer.parseInt(bookReview.getOppositionCount()) - 1));
                        }
                    }
                    CommonAdapter commonAdapter = ReplyCollectionAct.this.mAdapter;
                    if (commonAdapter == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                        commonAdapter = null;
                    }
                    commonAdapter.notifyItemChanged(bookReview.getTmpPosition(), 1);
                    return;
                }
                if (bookReview.isActionSupport()) {
                    BookReview bookReview2 = ReplyCollectionAct.this.item;
                    Intrinsics.checkNotNull(bookReview2);
                    BookReview bookReview3 = ReplyCollectionAct.this.item;
                    Intrinsics.checkNotNull(bookReview3);
                    bookReview2.setSupport(bookReview3.isSupport() == 1 ? 0 : 1);
                    BookReview bookReview4 = ReplyCollectionAct.this.item;
                    Intrinsics.checkNotNull(bookReview4);
                    if (bookReview4.isSupport() == 1) {
                        BookReview bookReview5 = ReplyCollectionAct.this.item;
                        Intrinsics.checkNotNull(bookReview5);
                        BookReview bookReview6 = ReplyCollectionAct.this.item;
                        Intrinsics.checkNotNull(bookReview6);
                        bookReview5.setSupportCount(String.valueOf(Integer.parseInt(bookReview6.getSupportCount()) + 1));
                    } else {
                        BookReview bookReview7 = ReplyCollectionAct.this.item;
                        Intrinsics.checkNotNull(bookReview7);
                        BookReview bookReview8 = ReplyCollectionAct.this.item;
                        Intrinsics.checkNotNull(bookReview8);
                        bookReview7.setSupportCount(String.valueOf(Integer.parseInt(bookReview8.getSupportCount()) - 1));
                    }
                    RTextView rTextView = ReplyCollectionAct.this.getBinding().tvSupport;
                    StringBuilder sb = new StringBuilder();
                    sb.append("赞同(");
                    BookReview bookReview9 = ReplyCollectionAct.this.item;
                    Intrinsics.checkNotNull(bookReview9);
                    sb.append(bookReview9.getSupportCount());
                    sb.append(')');
                    rTextView.setText(sb.toString());
                    RTextView rTextView2 = ReplyCollectionAct.this.getBinding().tvSupport;
                    BookReview bookReview10 = ReplyCollectionAct.this.item;
                    Intrinsics.checkNotNull(bookReview10);
                    rTextView2.setSelected(bookReview10.isSupport() == 1);
                    return;
                }
                BookReview bookReview11 = ReplyCollectionAct.this.item;
                Intrinsics.checkNotNull(bookReview11);
                BookReview bookReview12 = ReplyCollectionAct.this.item;
                Intrinsics.checkNotNull(bookReview12);
                bookReview11.setOpposition(bookReview12.isOpposition() == 1 ? 0 : 1);
                BookReview bookReview13 = ReplyCollectionAct.this.item;
                Intrinsics.checkNotNull(bookReview13);
                if (bookReview13.isOpposition() == 1) {
                    BookReview bookReview14 = ReplyCollectionAct.this.item;
                    Intrinsics.checkNotNull(bookReview14);
                    BookReview bookReview15 = ReplyCollectionAct.this.item;
                    Intrinsics.checkNotNull(bookReview15);
                    bookReview14.setOppositionCount(String.valueOf(Integer.parseInt(bookReview15.getOppositionCount()) + 1));
                } else {
                    BookReview bookReview16 = ReplyCollectionAct.this.item;
                    Intrinsics.checkNotNull(bookReview16);
                    BookReview bookReview17 = ReplyCollectionAct.this.item;
                    Intrinsics.checkNotNull(bookReview17);
                    bookReview16.setOppositionCount(String.valueOf(Integer.parseInt(bookReview17.getOppositionCount()) - 1));
                }
                RTextView rTextView3 = ReplyCollectionAct.this.getBinding().tvOpposition;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("反对(");
                BookReview bookReview18 = ReplyCollectionAct.this.item;
                Intrinsics.checkNotNull(bookReview18);
                sb2.append(bookReview18.getOppositionCount());
                sb2.append(')');
                rTextView3.setText(sb2.toString());
                RTextView rTextView4 = ReplyCollectionAct.this.getBinding().tvOpposition;
                BookReview bookReview19 = ReplyCollectionAct.this.item;
                Intrinsics.checkNotNull(bookReview19);
                rTextView4.setSelected(bookReview19.isOpposition() == 1);
            }
        };
        supportAndOppose.observe(this, new Observer() { // from class: com.ykb.ebook.activity.a2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ReplyCollectionAct.observeViewModel$lambda$12(function12, obj);
            }
        });
        MutableLiveData<BookReview> publishBookReview = getViewModel().getPublishBookReview();
        final Function1<BookReview, Unit> function13 = new Function1<BookReview, Unit>() { // from class: com.ykb.ebook.activity.ReplyCollectionAct.observeViewModel.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BookReview bookReview) {
                invoke2(bookReview);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable BookReview bookReview) {
                CommonAdapter commonAdapter = null;
                ToastUtilsKt.toastOnUi$default(ReplyCollectionAct.this, "发布成功", 0, 2, (Object) null);
                if (bookReview != null && bookReview.isActionReply()) {
                    bookReview.setReplyCount(bookReview.getReplyCount() + 1);
                    CommonAdapter commonAdapter2 = ReplyCollectionAct.this.mAdapter;
                    if (commonAdapter2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                    } else {
                        commonAdapter = commonAdapter2;
                    }
                    commonAdapter.notifyItemChanged(bookReview.getTmpPosition(), 1);
                    return;
                }
                ReplyCollectionAct.this.page = 1;
                RTextView rTextView = ReplyCollectionAct.this.getBinding().tvReply;
                StringBuilder sb = new StringBuilder();
                BookReview bookReview2 = ReplyCollectionAct.this.item;
                Intrinsics.checkNotNull(bookReview2);
                sb.append(bookReview2.getReplyCount() + 1);
                sb.append("回复");
                rTextView.setText(sb.toString());
                ReplyCollectionAct.this.doBusiness();
            }
        };
        publishBookReview.observe(this, new Observer() { // from class: com.ykb.ebook.activity.b2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ReplyCollectionAct.observeViewModel$lambda$13(function13, obj);
            }
        });
        MutableLiveData<BookReview> delComment = getViewModel().getDelComment();
        final Function1<BookReview, Unit> function14 = new Function1<BookReview, Unit>() { // from class: com.ykb.ebook.activity.ReplyCollectionAct.observeViewModel.4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BookReview bookReview) {
                invoke2(bookReview);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BookReview it) {
                CommonAdapter commonAdapter = null;
                ToastUtilsKt.toastOnUi$default(ReplyCollectionAct.this, "删除成功", 0, 2, (Object) null);
                CommonAdapter commonAdapter2 = ReplyCollectionAct.this.mAdapter;
                if (commonAdapter2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                } else {
                    commonAdapter = commonAdapter2;
                }
                Intrinsics.checkNotNullExpressionValue(it, "it");
                commonAdapter.remove(it);
            }
        };
        delComment.observe(this, new Observer() { // from class: com.ykb.ebook.activity.c2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ReplyCollectionAct.observeViewModel$lambda$14(function14, obj);
            }
        });
        getViewModel().getCommentReport().observe(this, new Observer() { // from class: com.ykb.ebook.activity.d2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ReplyCollectionAct.observeViewModel$lambda$15(this.f26114a, obj);
            }
        });
        getViewModel().getAccountBanned().observe(this, new Observer() { // from class: com.ykb.ebook.activity.f2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ReplyCollectionAct.observeViewModel$lambda$16(this.f26127a, obj);
            }
        });
        MutableLiveData<BookReview> editComment = getViewModel().getEditComment();
        final Function1<BookReview, Unit> function15 = new Function1<BookReview, Unit>() { // from class: com.ykb.ebook.activity.ReplyCollectionAct.observeViewModel.7
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BookReview bookReview) {
                invoke2(bookReview);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BookReview bookReview) {
                CommonAdapter commonAdapter = ReplyCollectionAct.this.mAdapter;
                if (commonAdapter == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                    commonAdapter = null;
                }
                commonAdapter.notifyItemChanged(bookReview.getTmpPosition(), 1);
            }
        };
        editComment.observe(this, new Observer() { // from class: com.ykb.ebook.activity.g2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ReplyCollectionAct.observeViewModel$lambda$17(function15, obj);
            }
        });
    }

    @Override // com.ykb.ebook.base.BaseActivity
    public void onActivityCreated(@Nullable Bundle savedInstanceState) throws SecurityException, NumberFormatException {
        int i2;
        int i3;
        if (!TextUtils.isEmpty(getIntent().getStringExtra("token"))) {
            ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), "user_id", getIntent().getStringExtra("user_id"));
            ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), "app_id", getIntent().getStringExtra("app_id"));
            ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), PLVSocketUserConstant.ROLE_ADMIN, getIntent().getStringExtra(PLVSocketUserConstant.ROLE_ADMIN));
            ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), "avatar", getIntent().getStringExtra("avatar"));
            ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), "token", getIntent().getStringExtra("token"));
            ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), "secret", getIntent().getStringExtra("secret"));
        }
        ActivityResultLauncher<Intent> activityResultLauncherRegisterForActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.ykb.ebook.activity.l2
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                ReplyCollectionAct.onActivityCreated$lambda$1(this.f26167a, (ActivityResult) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult, "registerForActivityResul…          }\n            }");
        this.albumLauncher = activityResultLauncherRegisterForActivityResult;
        boolean booleanExtra = getIntent().getBooleanExtra("isBookReview", true);
        this.isBookReview = booleanExtra;
        if (!booleanExtra) {
            String stringExtra = getIntent().getStringExtra("chapterId");
            Intrinsics.checkNotNull(stringExtra);
            this.chapterId = stringExtra;
            String stringExtra2 = getIntent().getStringExtra("paragraphId");
            Intrinsics.checkNotNull(stringExtra2);
            this.paragraphId = stringExtra2;
            String stringExtra3 = getIntent().getStringExtra("paragraphContent");
            Intrinsics.checkNotNull(stringExtra3);
            this.paragraphContent = stringExtra3;
        }
        Serializable serializableExtra = getIntent().getSerializableExtra("item");
        Intrinsics.checkNotNull(serializableExtra, "null cannot be cast to non-null type com.ykb.ebook.model.BookReview");
        BookReview bookReview = (BookReview) serializableExtra;
        this.item = bookReview;
        if (bookReview == null) {
            return;
        }
        String stringExtra4 = getIntent().getStringExtra("bookId");
        if (stringExtra4 != null) {
            this.bookId = stringExtra4;
        }
        CommonAdapter<BookReview> commonAdapter = null;
        CommonAdapter<BookReview> commonAdapter2 = new CommonAdapter<>(R.layout.item_book_review, null, 2, null);
        this.mAdapter = commonAdapter2;
        commonAdapter2.setConvert(new Function3<QuickViewHolder, Integer, BookReview, Unit>() { // from class: com.ykb.ebook.activity.ReplyCollectionAct.onActivityCreated.3
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(QuickViewHolder quickViewHolder, Integer num, BookReview bookReview2) throws SecurityException {
                invoke(quickViewHolder, num.intValue(), bookReview2);
                return Unit.INSTANCE;
            }

            public final void invoke(@NotNull QuickViewHolder holder, int i4, @Nullable BookReview bookReview2) throws SecurityException {
                Intrinsics.checkNotNullParameter(holder, "holder");
                ReplyCollectionAct replyCollectionAct = ReplyCollectionAct.this;
                Intrinsics.checkNotNull(bookReview2);
                replyCollectionAct.convert(holder, i4, bookReview2, 1);
            }
        });
        CommonAdapter<BookReview> commonAdapter3 = this.mAdapter;
        if (commonAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            commonAdapter3 = null;
        }
        commonAdapter3.setConvertPayload(new Function4<QuickViewHolder, Integer, BookReview, List<? extends Object>, Unit>() { // from class: com.ykb.ebook.activity.ReplyCollectionAct.onActivityCreated.4
            {
                super(4);
            }

            @Override // kotlin.jvm.functions.Function4
            public /* bridge */ /* synthetic */ Unit invoke(QuickViewHolder quickViewHolder, Integer num, BookReview bookReview2, List<? extends Object> list) throws SecurityException {
                invoke(quickViewHolder, num.intValue(), bookReview2, list);
                return Unit.INSTANCE;
            }

            public final void invoke(@NotNull QuickViewHolder holder, int i4, @Nullable BookReview bookReview2, @NotNull List<? extends Object> playload) throws SecurityException {
                Intrinsics.checkNotNullParameter(holder, "holder");
                Intrinsics.checkNotNullParameter(playload, "playload");
                ReplyCollectionAct replyCollectionAct = ReplyCollectionAct.this;
                Intrinsics.checkNotNull(bookReview2);
                replyCollectionAct.convertPayload(holder, i4, bookReview2, playload);
            }
        });
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getColorMode() != 2) {
            getWindow().clearFlags(67108864);
            getWindow().getDecorView().setSystemUiVisibility(8192);
        }
        Window window = getWindow();
        int colorMode = readConfig.getColorMode();
        window.setStatusBarColor(colorMode != 0 ? colorMode != 1 ? ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_1C2134) : ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_FEEEC6) : -1);
        Toolbar toolbar = getBinding().toolBar;
        int colorMode2 = readConfig.getColorMode();
        toolbar.setBackground(new ColorDrawable(colorMode2 != 0 ? colorMode2 != 1 ? ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_1C2134) : ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_FEEEC6) : -1));
        LinearLayout root = getBinding().getRoot();
        int colorMode3 = readConfig.getColorMode();
        root.setBackground(new ColorDrawable(colorMode3 != 0 ? colorMode3 != 1 ? ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_121622) : ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_FEEEC6) : -1));
        View view = getBinding().line;
        int colorMode4 = readConfig.getColorMode();
        view.setBackground(new ColorDrawable(colorMode4 != 0 ? colorMode4 != 1 ? ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_1C2134) : ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_D6C9A9) : ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_eeeeee)));
        View view2 = getBinding().viewLine2;
        int colorMode5 = readConfig.getColorMode();
        view2.setBackground(new ColorDrawable(colorMode5 != 0 ? colorMode5 != 1 ? ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_1C2134) : ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_D6C9A9) : ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_eeeeee)));
        TextView textView = getBinding().toolbarTitle;
        Intrinsics.checkNotNullExpressionValue(textView, "binding.toolbarTitle");
        ViewExtensionsKt.setMainColor(textView);
        TextView textView2 = getBinding().tvHospital;
        Intrinsics.checkNotNullExpressionValue(textView2, "binding.tvHospital");
        ViewExtensionsKt.setSecondColor(textView2);
        TextView textView3 = getBinding().tvTime;
        Intrinsics.checkNotNullExpressionValue(textView3, "binding.tvTime");
        ViewExtensionsKt.setSecondColor(textView3);
        TextView textView4 = getBinding().tvComment;
        Intrinsics.checkNotNullExpressionValue(textView4, "binding.tvComment");
        ViewExtensionsKt.setMainColor(textView4);
        RTextViewHelper helper = getBinding().tvReply.getHelper();
        if (helper != null) {
            helper.setTextColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030));
        }
        RTextView rTextView = getBinding().tvHighComment;
        Intrinsics.checkNotNullExpressionValue(rTextView, "binding.tvHighComment");
        ViewExtensionsKt.setSecondColor(rTextView);
        TextView textView5 = getBinding().tvNickName;
        BookReview bookReview2 = this.item;
        Intrinsics.checkNotNull(bookReview2);
        textView5.setText(bookReview2.getNickname());
        TextView textView6 = getBinding().tvHospital;
        BookReview bookReview3 = this.item;
        Intrinsics.checkNotNull(bookReview3);
        textView6.setText(bookReview3.getSchool());
        BookReview bookReview4 = this.item;
        Intrinsics.checkNotNull(bookReview4);
        if (TextUtils.isEmpty(bookReview4.getSchool())) {
            TextView textView7 = getBinding().tvHospital;
            Intrinsics.checkNotNullExpressionValue(textView7, "binding.tvHospital");
            ViewExtensionsKt.gone(textView7);
        }
        TextView textView8 = getBinding().tvTime;
        BookReview bookReview5 = this.item;
        Intrinsics.checkNotNull(bookReview5);
        textView8.setText(bookReview5.getCtime());
        TextView textView9 = getBinding().tvComment;
        BookReview bookReview6 = this.item;
        Intrinsics.checkNotNull(bookReview6);
        textView9.setText(bookReview6.getComment());
        ImageLoader imageLoader = ImageLoader.INSTANCE;
        BookReview bookReview7 = this.item;
        Intrinsics.checkNotNull(bookReview7);
        RequestBuilder<Drawable> requestBuilderLoad = imageLoader.load(this, bookReview7.getAvatar());
        int i4 = R.drawable.personal_headimg_icon;
        requestBuilderLoad.placeholder(i4).into(getBinding().imgAvatar);
        imageLoader.load(this, ContextExtensionsKt.getPrefString(AppCtxKt.getAppCtx(), "avatar", "")).placeholder(i4).into(getBinding().imgLoginAvatar);
        RTextView rTextView2 = getBinding().tvSupport;
        StringBuilder sb = new StringBuilder();
        sb.append("赞同(");
        BookReview bookReview8 = this.item;
        Intrinsics.checkNotNull(bookReview8);
        sb.append(bookReview8.getSupportCount());
        sb.append(')');
        rTextView2.setText(sb.toString());
        RTextView rTextView3 = getBinding().tvSupport;
        BookReview bookReview9 = this.item;
        rTextView3.setSelected(bookReview9 != null && bookReview9.isSupport() == 1);
        RTextView rTextView4 = getBinding().tvOpposition;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("反对(");
        BookReview bookReview10 = this.item;
        Intrinsics.checkNotNull(bookReview10);
        sb2.append(bookReview10.getOppositionCount());
        sb2.append(')');
        rTextView4.setText(sb2.toString());
        RTextView rTextView5 = getBinding().tvOpposition;
        BookReview bookReview11 = this.item;
        Intrinsics.checkNotNull(bookReview11);
        rTextView5.setSelected(bookReview11.isSupport() == 1);
        RTextView rTextView6 = getBinding().tvReply;
        BookReview bookReview12 = this.item;
        Intrinsics.checkNotNull(bookReview12);
        String string = "回复";
        if (bookReview12.getReplyCount() > 0) {
            StringBuilder sb3 = new StringBuilder();
            BookReview bookReview13 = this.item;
            Intrinsics.checkNotNull(bookReview13);
            sb3.append(bookReview13.getReplyCount());
            sb3.append("回复");
            string = sb3.toString();
        }
        rTextView6.setText(string);
        getBinding().tvReply.getHelper().setBackgroundDrawableNormal(ContextCompat.getDrawable(this, readConfig.getColorMode() == 2 ? R.drawable.shape_reply_selected_bg_night : R.drawable.shape_reply_selected_bg));
        BookReview bookReview14 = this.item;
        Intrinsics.checkNotNull(bookReview14);
        if (new Regex(RegexPool.NUMBERS).matches(bookReview14.getSupportCount())) {
            BookReview bookReview15 = this.item;
            Intrinsics.checkNotNull(bookReview15);
            i2 = Integer.parseInt(bookReview15.getSupportCount());
        } else {
            i2 = 0;
        }
        BookReview bookReview16 = this.item;
        Intrinsics.checkNotNull(bookReview16);
        if (new Regex(RegexPool.NUMBERS).matches(bookReview16.getOppositionCount())) {
            BookReview bookReview17 = this.item;
            Intrinsics.checkNotNull(bookReview17);
            i3 = Integer.parseInt(bookReview17.getOppositionCount());
        } else {
            i3 = 0;
        }
        if (i2 > i3) {
            getBinding().tvSupport.setTextColor(readConfig.getColorMode() != 2 ? getColor(R.color.color_81cb30) : Color.parseColor("#6AA064"));
            getBinding().tvOpposition.setTextColor(getColor(readConfig.getColorMode() != 2 ? R.color.color_c2c6cb : R.color.color_575F79));
        } else if (i2 < i3) {
            getBinding().tvOpposition.setTextColor(getColor(readConfig.getColorMode() != 2 ? R.color.color_F95843 : R.color.color_B2575C));
            getBinding().tvSupport.setTextColor(getColor(readConfig.getColorMode() != 2 ? R.color.color_c2c6cb : R.color.color_575F79));
        } else {
            getBinding().tvSupport.setTextColor(getColor(readConfig.getColorMode() != 2 ? R.color.color_c2c6cb : R.color.color_575F79));
            getBinding().tvOpposition.setTextColor(getColor(readConfig.getColorMode() != 2 ? R.color.color_c2c6cb : R.color.color_575F79));
        }
        RecyclerView recyclerView = getBinding().rvNewest;
        CommonAdapter<BookReview> commonAdapter4 = this.mAdapter;
        if (commonAdapter4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
        } else {
            commonAdapter = commonAdapter4;
        }
        recyclerView.setAdapter(commonAdapter);
        getBinding().refreshLayout.setEnableAutoLoadMore(false);
        getBinding().refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ykb.ebook.activity.m2
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ReplyCollectionAct.onActivityCreated$lambda$3(this.f26170c, refreshLayout);
            }
        });
        getBinding().refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.ykb.ebook.activity.n2
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                ReplyCollectionAct.onActivityCreated$lambda$4(this.f26175c, refreshLayout);
            }
        });
        getBinding().imgBack.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.o2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                ReplyCollectionAct.onActivityCreated$lambda$5(this.f26178c, view3);
            }
        });
        getBinding().tvSupport.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.u1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                ReplyCollectionAct.onActivityCreated$lambda$6(this.f26204c, view3);
            }
        });
        getBinding().tvOpposition.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.v1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                ReplyCollectionAct.onActivityCreated$lambda$7(this.f26208c, view3);
            }
        });
        getBinding().tvReply.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.w1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                ReplyCollectionAct.onActivityCreated$lambda$8(this.f26212c, view3);
            }
        });
        getBinding().tvAddSp.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.x1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                ReplyCollectionAct.onActivityCreated$lambda$9(this.f26216c, view3);
            }
        });
        BookReview bookReview18 = this.item;
        Intrinsics.checkNotNull(bookReview18);
        if (!TextUtils.isEmpty(bookReview18.getPicture())) {
            getBinding().imgPicture.setVisibility(0);
            RequestManager requestManagerWith = Glide.with((FragmentActivity) this);
            BookReview bookReview19 = this.item;
            Intrinsics.checkNotNull(bookReview19);
            requestManagerWith.load(bookReview19.getPicture()).into(getBinding().imgPicture);
        }
        getBinding().imgPicture.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.y1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                ReplyCollectionAct.onActivityCreated$lambda$10(this.f26220c, view3);
            }
        });
        initTheme();
    }

    @Override // com.ykb.ebook.base.BaseActivity
    @NotNull
    public LayoutReplyCollectionBinding getBinding() {
        return (LayoutReplyCollectionBinding) this.binding.getValue();
    }

    @Override // com.ykb.ebook.base.BaseVmActivity
    @NotNull
    public ReplyCollectionViewModel initViewModel() {
        return (ReplyCollectionViewModel) new ViewModelProvider(this).get(ReplyCollectionViewModel.class);
    }

    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJV\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\bJ>\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJn\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\b¨\u0006\u0016"}, d2 = {"Lcom/ykb/ebook/activity/ReplyCollectionAct$Companion;", "", "()V", "newIntent", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "bookId", "", "item", "Lcom/ykb/ebook/model/BookReview;", "isBookReview", "", "userId", "appId", PLVSocketUserConstant.ROLE_ADMIN, "avatar", "token", "secret", "chapterId", "paragraphId", "paragraphContent", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void newIntent(@NotNull Context context, @NotNull String bookId, @NotNull BookReview item, boolean isBookReview) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(bookId, "bookId");
            Intrinsics.checkNotNullParameter(item, "item");
            Intent intent = new Intent(context, (Class<?>) ReplyCollectionAct.class);
            intent.putExtra("bookId", bookId);
            intent.putExtra("item", item);
            intent.putExtra("isBookReview", isBookReview);
            context.startActivity(intent);
        }

        public final void newIntent(@NotNull Context context, @NotNull String bookId, @NotNull BookReview item, boolean isBookReview, @NotNull String userId, @NotNull String appId, @NotNull String admin, @NotNull String avatar, @NotNull String token, @NotNull String secret) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(bookId, "bookId");
            Intrinsics.checkNotNullParameter(item, "item");
            Intrinsics.checkNotNullParameter(userId, "userId");
            Intrinsics.checkNotNullParameter(appId, "appId");
            Intrinsics.checkNotNullParameter(admin, "admin");
            Intrinsics.checkNotNullParameter(avatar, "avatar");
            Intrinsics.checkNotNullParameter(token, "token");
            Intrinsics.checkNotNullParameter(secret, "secret");
            Intent intent = new Intent(context, (Class<?>) ReplyCollectionAct.class);
            intent.putExtra("bookId", bookId);
            intent.putExtra("item", item);
            intent.putExtra("isBookReview", isBookReview);
            intent.putExtra("user_id", userId);
            intent.putExtra("app_id", appId);
            intent.putExtra(PLVSocketUserConstant.ROLE_ADMIN, admin);
            intent.putExtra("avatar", avatar);
            intent.putExtra("token", token);
            intent.putExtra("secret", secret);
            context.startActivity(intent);
        }

        public final void newIntent(@NotNull Context context, @NotNull String bookId, @NotNull String chapterId, @NotNull String paragraphId, @NotNull String paragraphContent, @NotNull BookReview item, boolean isBookReview) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(bookId, "bookId");
            Intrinsics.checkNotNullParameter(chapterId, "chapterId");
            Intrinsics.checkNotNullParameter(paragraphId, "paragraphId");
            Intrinsics.checkNotNullParameter(paragraphContent, "paragraphContent");
            Intrinsics.checkNotNullParameter(item, "item");
            Intent intent = new Intent(context, (Class<?>) ReplyCollectionAct.class);
            intent.putExtra("bookId", bookId);
            intent.putExtra("chapterId", chapterId);
            intent.putExtra("paragraphId", paragraphId);
            intent.putExtra("paragraphContent", paragraphContent);
            intent.putExtra("item", item);
            intent.putExtra("isBookReview", isBookReview);
            context.startActivity(intent);
        }

        public final void newIntent(@NotNull Context context, @NotNull String bookId, @NotNull String chapterId, @NotNull String paragraphId, @NotNull String paragraphContent, @NotNull BookReview item, boolean isBookReview, @NotNull String userId, @NotNull String appId, @NotNull String admin, @NotNull String avatar, @NotNull String token, @NotNull String secret) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(bookId, "bookId");
            Intrinsics.checkNotNullParameter(chapterId, "chapterId");
            Intrinsics.checkNotNullParameter(paragraphId, "paragraphId");
            Intrinsics.checkNotNullParameter(paragraphContent, "paragraphContent");
            Intrinsics.checkNotNullParameter(item, "item");
            Intrinsics.checkNotNullParameter(userId, "userId");
            Intrinsics.checkNotNullParameter(appId, "appId");
            Intrinsics.checkNotNullParameter(admin, "admin");
            Intrinsics.checkNotNullParameter(avatar, "avatar");
            Intrinsics.checkNotNullParameter(token, "token");
            Intrinsics.checkNotNullParameter(secret, "secret");
            Intent intent = new Intent(context, (Class<?>) ReplyCollectionAct.class);
            intent.putExtra("bookId", bookId);
            intent.putExtra("chapterId", chapterId);
            intent.putExtra("paragraphId", paragraphId);
            intent.putExtra("paragraphContent", paragraphContent);
            intent.putExtra("item", item);
            intent.putExtra("isBookReview", isBookReview);
            intent.putExtra("user_id", userId);
            intent.putExtra("app_id", appId);
            intent.putExtra(PLVSocketUserConstant.ROLE_ADMIN, admin);
            intent.putExtra("avatar", avatar);
            intent.putExtra("token", token);
            intent.putExtra("secret", secret);
            context.startActivity(intent);
        }
    }
}

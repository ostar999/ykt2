package com.ykb.ebook.dialog;

import android.content.AppCtxKt;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.text.Layout;
import android.util.ColorResourcesKt;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.psychiatrygarden.utils.Constants;
import com.ruffian.library.widget.RImageView;
import com.ruffian.library.widget.RLinearLayout;
import com.ruffian.library.widget.RRelativeLayout;
import com.ruffian.library.widget.RTextView;
import com.ruffian.library.widget.helper.RBaseHelper;
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
import com.ykb.ebook.common.PreferKeyKt;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.dialog.CommonSureDialog;
import com.ykb.ebook.dialog.NoteListDialog;
import com.ykb.ebook.dialog.WriteNoteDialog;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.extensions.ConvertExtensionsKt;
import com.ykb.ebook.extensions.StringExtensionsKt;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.model.NoteListBean;
import com.ykb.ebook.util.Coroutine;
import com.ykb.ebook.util.ImageLoader;
import com.ykb.ebook.util.ScreenUtil;
import com.ykb.ebook.util.ViewUtilKt;
import com.ykb.ebook.weight.RoundCornerDrawable;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import kotlin.text.Typography;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/NoteListDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class NoteListDialog {

    @Metadata(d1 = {"\u0000¼\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u000e\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010m\u001a\u00020-2\u0012\u0010n\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0oH\u0002J(\u0010p\u001a\u00020-2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010q\u001a\u00020r2\u0006\u0010s\u001a\u0002052\u0006\u0010t\u001a\u00020%H\u0003J&\u0010p\u001a\u00020-2\u0006\u0010q\u001a\u00020r2\u0006\u0010t\u001a\u00020%2\f\u0010u\u001a\b\u0012\u0004\u0012\u00020w0vH\u0002J\u0010\u0010x\u001a\u00020-2\u0006\u0010t\u001a\u00020%H\u0002J(\u0010y\u001a\u00020-2\u0006\u0010t\u001a\u00020%2\u0006\u0010s\u001a\u0002052\u0006\u0010z\u001a\u00020\b2\u0006\u0010{\u001a\u000205H\u0002J:\u0010|\u001a\u00020-2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010P\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\b\b\u0002\u0010}\u001a\u00020\u0006H\u0002J\u0010\u0010~\u001a\u00020-2\u0006\u0010\u007f\u001a\u00020fH\u0016J\u0015\u0010\u0080\u0001\u001a\u00020\u00002\f\u0010+\u001a\b\u0012\u0004\u0012\u00020-0,J+\u0010\u0081\u0001\u001a\u00020\u00002\"\u0010\u0082\u0001\u001a\u001d\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b0\u0012\b\b1\u0012\u0004\b\b(2\u0012\u0004\u0012\u00020-0/JC\u0010\u0083\u0001\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010P\u001a\u0002052\u0006\u0010\u001f\u001a\u0002052\u0006\u0010\n\u001a\u00020\b2\b\b\u0002\u0010\u000b\u001a\u00020\u00062\b\b\u0002\u0010\u0005\u001a\u00020\u0006J\u0015\u0010\u0084\u0001\u001a\u00020\u00002\f\u00103\u001a\b\u0012\u0004\u0012\u00020-0,R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\f\u001a\u0004\u0018\u00010\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0012\u001a\u0004\u0018\u00010\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0011\u001a\u0004\b\u0013\u0010\u000fR\u001d\u0010\u0015\u001a\u0004\u0018\u00010\u00168BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u0011\u001a\u0004\b\u0017\u0010\u0018R\u001d\u0010\u001a\u001a\u0004\u0018\u00010\u001b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u0011\u001a\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001f\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010 \u001a\u0004\u0018\u00010\u001b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\"\u0010\u0011\u001a\u0004\b!\u0010\u001dR\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010&\u001a\u0004\u0018\u00010'8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b*\u0010\u0011\u001a\u0004\b(\u0010)R\u0016\u0010+\u001a\n\u0012\u0004\u0012\u00020-\u0018\u00010,X\u0082\u000e¢\u0006\u0002\n\u0000R+\u0010.\u001a\u001f\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b0\u0012\b\b1\u0012\u0004\b\b(2\u0012\u0004\u0012\u00020-\u0018\u00010/X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u00103\u001a\n\u0012\u0004\u0012\u00020-\u0018\u00010,X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u000205X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u000205X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u00107\u001a\u0004\u0018\u0001088BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b;\u0010\u0011\u001a\u0004\b9\u0010:R\u001d\u0010<\u001a\u0004\u0018\u00010=8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b@\u0010\u0011\u001a\u0004\b>\u0010?R\u001d\u0010A\u001a\u0004\u0018\u00010B8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bE\u0010\u0011\u001a\u0004\bC\u0010DR\u001d\u0010F\u001a\u0004\u0018\u00010G8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bJ\u0010\u0011\u001a\u0004\bH\u0010IR\u001d\u0010K\u001a\u0004\u0018\u00010L8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bO\u0010\u0011\u001a\u0004\bM\u0010NR\u000e\u0010P\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010Q\u001a\u0004\u0018\u00010'8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bS\u0010\u0011\u001a\u0004\bR\u0010)R\u001d\u0010T\u001a\u0004\u0018\u00010'8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bV\u0010\u0011\u001a\u0004\bU\u0010)R\u001d\u0010W\u001a\u0004\u0018\u00010X8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b[\u0010\u0011\u001a\u0004\bY\u0010ZR\u001d\u0010\\\u001a\u0004\u0018\u00010X8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b^\u0010\u0011\u001a\u0004\b]\u0010ZR\u001d\u0010_\u001a\u0004\u0018\u00010'8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\ba\u0010\u0011\u001a\u0004\b`\u0010)R\u001d\u0010b\u001a\u0004\u0018\u00010X8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bd\u0010\u0011\u001a\u0004\bc\u0010ZR\u001d\u0010e\u001a\u0004\u0018\u00010f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bi\u0010\u0011\u001a\u0004\bg\u0010hR\u001d\u0010j\u001a\u0004\u0018\u00010f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bl\u0010\u0011\u001a\u0004\bk\u0010h¨\u0006\u0085\u0001"}, d2 = {"Lcom/ykb/ebook/dialog/NoteListDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "allowDrawLine", "", "bookId", "", "chapterId", "drawContent", "hasDrawLine", "imgAddNote", "Landroid/widget/ImageView;", "getImgAddNote", "()Landroid/widget/ImageView;", "imgAddNote$delegate", "Lkotlin/Lazy;", "imgClose", "getImgClose", "imgClose$delegate", "imgHead", "Lcom/ruffian/library/widget/RImageView;", "getImgHead", "()Lcom/ruffian/library/widget/RImageView;", "imgHead$delegate", "layoutAddNote", "Landroid/widget/LinearLayout;", "getLayoutAddNote", "()Landroid/widget/LinearLayout;", "layoutAddNote$delegate", SessionDescription.ATTR_LENGTH, "llMore", "getLlMore", "llMore$delegate", "mAdapter", "Lcom/ykb/ebook/adapter/CommonAdapter;", "Lcom/ykb/ebook/model/NoteListBean;", "moreNoteContent", "Landroid/widget/TextView;", "getMoreNoteContent", "()Landroid/widget/TextView;", "moreNoteContent$delegate", "onDeleteClick", "Lkotlin/Function0;", "", "onDrawLineClick", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "drawLine", "onShareClick", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "recycler", "Landroidx/recyclerview/widget/RecyclerView;", "getRecycler", "()Landroidx/recyclerview/widget/RecyclerView;", "recycler$delegate", "refresh", "Lcom/scwang/smartrefresh/layout/SmartRefreshLayout;", "getRefresh", "()Lcom/scwang/smartrefresh/layout/SmartRefreshLayout;", "refresh$delegate", "refreshHeader", "Lcom/scwang/smartrefresh/layout/header/ClassicsHeader;", "getRefreshHeader", "()Lcom/scwang/smartrefresh/layout/header/ClassicsHeader;", "refreshHeader$delegate", "relayoutNoteContent", "Lcom/ruffian/library/widget/RRelativeLayout;", "getRelayoutNoteContent", "()Lcom/ruffian/library/widget/RRelativeLayout;", "relayoutNoteContent$delegate", "relayoutNoteRoot", "Lcom/ruffian/library/widget/RLinearLayout;", "getRelayoutNoteRoot", "()Lcom/ruffian/library/widget/RLinearLayout;", "relayoutNoteRoot$delegate", "startPosition", "tvAddNote", "getTvAddNote", "tvAddNote$delegate", "tvContent", "getTvContent", "tvContent$delegate", "tvCopy", "Lcom/ruffian/library/widget/RTextView;", "getTvCopy", "()Lcom/ruffian/library/widget/RTextView;", "tvCopy$delegate", "tvDrawLine", "getTvDrawLine", "tvDrawLine$delegate", "tvNoteTitle", "getTvNoteTitle", "tvNoteTitle$delegate", "tvShare", "getTvShare", "tvShare$delegate", "viewContentLine", "Landroid/view/View;", "getViewContentLine", "()Landroid/view/View;", "viewContentLine$delegate", "viewLine", "getViewLine", "viewLine$delegate", "addNote", "params", "", "convert", "holder", "Lcom/ykb/ebook/adapter/base/QuickViewHolder;", "position", "item", "payloads", "", "", "delNote", "editNote", "content", "permission", "loadData", "isAddNote", "onClick", "view", "setDeleteClick", "setDrawLineClick", "drawLineClick", "setParams", "setShareClick", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nNoteListDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NoteListDialog.kt\ncom/ykb/ebook/dialog/NoteListDialog$Builder\n+ 2 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,629:1\n42#2:630\n42#2:631\n42#2:633\n42#2:634\n42#2:635\n1#3:632\n*S KotlinDebug\n*F\n+ 1 NoteListDialog.kt\ncom/ykb/ebook/dialog/NoteListDialog$Builder\n*L\n101#1:630\n105#1:631\n449#1:633\n465#1:634\n554#1:635\n*E\n"})
    public static final class Builder extends BasicDialog.Builder<Builder> {
        private boolean allowDrawLine;

        @NotNull
        private String bookId;

        @NotNull
        private String chapterId;

        @NotNull
        private String drawContent;
        private boolean hasDrawLine;

        /* renamed from: imgAddNote$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgAddNote;

        /* renamed from: imgClose$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgClose;

        /* renamed from: imgHead$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgHead;

        /* renamed from: layoutAddNote$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy layoutAddNote;

        @NotNull
        private String length;

        /* renamed from: llMore$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy llMore;

        @NotNull
        private CommonAdapter<NoteListBean> mAdapter;

        /* renamed from: moreNoteContent$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy moreNoteContent;

        @Nullable
        private Function0<Unit> onDeleteClick;

        @Nullable
        private Function1<? super Boolean, Unit> onDrawLineClick;

        @Nullable
        private Function0<Unit> onShareClick;
        private int page;
        private int pageSize;

        /* renamed from: recycler$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy recycler;

        /* renamed from: refresh$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy refresh;

        /* renamed from: refreshHeader$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy refreshHeader;

        /* renamed from: relayoutNoteContent$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy relayoutNoteContent;

        /* renamed from: relayoutNoteRoot$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy relayoutNoteRoot;

        @NotNull
        private String startPosition;

        /* renamed from: tvAddNote$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvAddNote;

        /* renamed from: tvContent$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvContent;

        /* renamed from: tvCopy$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvCopy;

        /* renamed from: tvDrawLine$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvDrawLine;

        /* renamed from: tvNoteTitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvNoteTitle;

        /* renamed from: tvShare$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvShare;

        /* renamed from: viewContentLine$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy viewContentLine;

        /* renamed from: viewLine$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy viewLine;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull final Context context) throws SecurityException {
            RTextViewHelper helper;
            Window window;
            RefreshHeader refreshHeader;
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            this.page = 1;
            this.pageSize = 20;
            this.bookId = "";
            this.chapterId = "";
            this.startPosition = "";
            this.allowDrawLine = true;
            this.length = "";
            this.drawContent = "";
            this.imgClose = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$imgClose$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ImageView invoke() {
                    return (ImageView) this.this$0.findViewById(R.id.img_close);
                }
            });
            this.imgAddNote = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$imgAddNote$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ImageView invoke() {
                    return (ImageView) this.this$0.findViewById(R.id.iv_add_note);
                }
            });
            this.refresh = LazyKt__LazyJVMKt.lazy(new Function0<SmartRefreshLayout>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$refresh$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final SmartRefreshLayout invoke() {
                    return (SmartRefreshLayout) this.this$0.findViewById(R.id.refresh);
                }
            });
            this.recycler = LazyKt__LazyJVMKt.lazy(new Function0<RecyclerView>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$recycler$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RecyclerView invoke() {
                    return (RecyclerView) this.this$0.findViewById(R.id.recycler);
                }
            });
            this.relayoutNoteRoot = LazyKt__LazyJVMKt.lazy(new Function0<RLinearLayout>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$relayoutNoteRoot$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RLinearLayout invoke() {
                    return (RLinearLayout) this.this$0.findViewById(R.id.relayout_note_root);
                }
            });
            this.layoutAddNote = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$layoutAddNote$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final LinearLayout invoke() {
                    return (LinearLayout) this.this$0.findViewById(R.id.layoutAddNote);
                }
            });
            this.viewLine = LazyKt__LazyJVMKt.lazy(new Function0<View>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$viewLine$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final View invoke() {
                    return this.this$0.findViewById(R.id.viewLine);
                }
            });
            this.relayoutNoteContent = LazyKt__LazyJVMKt.lazy(new Function0<RRelativeLayout>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$relayoutNoteContent$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RRelativeLayout invoke() {
                    return (RRelativeLayout) this.this$0.findViewById(R.id.relayoutNoteContent);
                }
            });
            this.viewContentLine = LazyKt__LazyJVMKt.lazy(new Function0<View>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$viewContentLine$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final View invoke() {
                    return this.this$0.findViewById(R.id.viewContentLine);
                }
            });
            this.imgHead = LazyKt__LazyJVMKt.lazy(new Function0<RImageView>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$imgHead$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RImageView invoke() {
                    return (RImageView) this.this$0.findViewById(R.id.img_head);
                }
            });
            this.tvAddNote = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$tvAddNote$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_add_note);
                }
            });
            this.moreNoteContent = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$moreNoteContent$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.moreNoteContent);
                }
            });
            this.tvContent = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$tvContent$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_content);
                }
            });
            this.tvCopy = LazyKt__LazyJVMKt.lazy(new Function0<RTextView>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$tvCopy$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RTextView invoke() {
                    return (RTextView) this.this$0.findViewById(R.id.tv_copy);
                }
            });
            this.tvShare = LazyKt__LazyJVMKt.lazy(new Function0<RTextView>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$tvShare$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RTextView invoke() {
                    return (RTextView) this.this$0.findViewById(R.id.tv_share);
                }
            });
            this.tvDrawLine = LazyKt__LazyJVMKt.lazy(new Function0<RTextView>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$tvDrawLine$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RTextView invoke() {
                    return (RTextView) this.this$0.findViewById(R.id.tv_draw_line);
                }
            });
            this.tvNoteTitle = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$tvNoteTitle$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tvNoteTitle);
                }
            });
            this.llMore = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$llMore$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final LinearLayout invoke() {
                    return (LinearLayout) this.this$0.findViewById(R.id.ll_more);
                }
            });
            this.refreshHeader = LazyKt__LazyJVMKt.lazy(new Function0<ClassicsHeader>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$refreshHeader$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ClassicsHeader invoke() {
                    return (ClassicsHeader) this.this$0.findViewById(R.id.refresh_header);
                }
            });
            setContentView(R.layout.dialog_note_list);
            setHeight((ScreenUtil.getScreenHeight(getActivity()) * R2.attr.bl_unPressed_drawable) / R2.attr.border_width_selected);
            setGravity(80);
            setAnimStyle(AnimAction.INSTANCE.getANIM_BOTTOM());
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            CommonAdapter<NoteListBean> commonAdapter = new CommonAdapter<>(R.layout.item_note_list, null, 2, null);
            this.mAdapter = commonAdapter;
            ReadConfig readConfig = ReadConfig.INSTANCE;
            commonAdapter.setEmptyViewLayout(context, readConfig.getColorMode() != 2 ? R.layout.empty_comment_layout_day : R.layout.empty_comment_layout_night);
            this.mAdapter.setEmptyViewEnable(true);
            this.mAdapter.setConvert(new Function3<QuickViewHolder, Integer, NoteListBean, Unit>() { // from class: com.ykb.ebook.dialog.NoteListDialog.Builder.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(QuickViewHolder quickViewHolder, Integer num, NoteListBean noteListBean) {
                    invoke(quickViewHolder, num.intValue(), noteListBean);
                    return Unit.INSTANCE;
                }

                public final void invoke(@NotNull QuickViewHolder holder, int i2, @Nullable NoteListBean noteListBean) {
                    Intrinsics.checkNotNullParameter(holder, "holder");
                    Builder builder = Builder.this;
                    Context context2 = context;
                    Intrinsics.checkNotNull(noteListBean);
                    builder.convert(context2, holder, i2, noteListBean);
                }
            });
            this.mAdapter.setConvertPayload(new Function4<QuickViewHolder, Integer, NoteListBean, List<? extends Object>, Unit>() { // from class: com.ykb.ebook.dialog.NoteListDialog.Builder.2
                {
                    super(4);
                }

                @Override // kotlin.jvm.functions.Function4
                public /* bridge */ /* synthetic */ Unit invoke(QuickViewHolder quickViewHolder, Integer num, NoteListBean noteListBean, List<? extends Object> list) {
                    invoke(quickViewHolder, num.intValue(), noteListBean, list);
                    return Unit.INSTANCE;
                }

                public final void invoke(@NotNull QuickViewHolder holder, int i2, @Nullable NoteListBean noteListBean, @NotNull List<? extends Object> payloads) {
                    Intrinsics.checkNotNullParameter(holder, "holder");
                    Intrinsics.checkNotNullParameter(payloads, "payloads");
                    Builder builder = Builder.this;
                    Intrinsics.checkNotNull(noteListBean);
                    builder.convert(holder, noteListBean, payloads);
                }
            });
            SmartRefreshLayout refresh = getRefresh();
            if (refresh != null && (refreshHeader = refresh.getRefreshHeader()) != null) {
                if (refreshHeader instanceof ClassicsHeader) {
                    ((ClassicsHeader) refreshHeader).setAccentColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
                }
                RefreshFooter refreshFooter = refresh.getRefreshFooter();
                if (refreshFooter != null && (refreshFooter instanceof ClassicsFooter)) {
                    ((ClassicsFooter) refreshFooter).setAccentColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
                }
            }
            RecyclerView recycler = getRecycler();
            if (recycler != null) {
                recycler.setAdapter(this.mAdapter);
            }
            ImageView imgAddNote = getImgAddNote();
            if (imgAddNote != null) {
                imgAddNote.setImageResource(readConfig.getColorMode() == 2 ? R.mipmap.ic_add_comment_night : R.drawable.ic_add_comment);
            }
            ImageView imgClose = getImgClose();
            if (imgClose != null) {
                imgClose.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.g0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        NoteListDialog.Builder._init_$lambda$3(this.f26333c, view);
                    }
                });
            }
            LinearLayout llMore = getLlMore();
            if (llMore != null) {
                llMore.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.h0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        NoteListDialog.Builder._init_$lambda$4(this.f26339c, view);
                    }
                });
            }
            SmartRefreshLayout refresh2 = getRefresh();
            if (refresh2 != null) {
                refresh2.setOnRefreshListener(new OnRefreshListener() { // from class: com.ykb.ebook.dialog.i0
                    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
                    public final void onRefresh(RefreshLayout refreshLayout) {
                        NoteListDialog.Builder._init_$lambda$5(this.f26343c, refreshLayout);
                    }
                });
            }
            SmartRefreshLayout refresh3 = getRefresh();
            if (refresh3 != null) {
                refresh3.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.ykb.ebook.dialog.j0
                    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
                    public final void onLoadMore(RefreshLayout refreshLayout) {
                        NoteListDialog.Builder._init_$lambda$6(this.f26346c, refreshLayout);
                    }
                });
            }
            setOnClickListener(getTvAddNote(), getTvCopy(), getTvShare(), getTvDrawLine());
            String prefString$default = ContextExtensionsKt.getPrefString$default(AppCtxKt.getAppCtx(), "avatar", null, 2, null);
            RImageView imgHead = getImgHead();
            if (imgHead != null) {
                ImageLoader.INSTANCE.load(context, prefString$default).placeholder(R.drawable.personal_headimg_icon).into(imgHead);
            }
            BasicDialog dialog = getDialog();
            if (dialog != null && (window = dialog.getWindow()) != null) {
                window.setLayout(-1, ScreenUtil.getScreenHeight(getActivity()));
            }
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setCornerRadius(ConvertExtensionsKt.dpToPx(8.0f));
            int colorMode = readConfig.getColorMode();
            if (colorMode == 0) {
                TextView tvNoteTitle = getTvNoteTitle();
                if (tvNoteTitle != null) {
                    tvNoteTitle.setTextColor(getColor(R.color.color_303030));
                }
                ImageView imgClose2 = getImgClose();
                if (imgClose2 != null) {
                    imgClose2.setImageResource(R.drawable.ic_close);
                }
                TextView tvContent = getTvContent();
                if (tvContent != null) {
                    tvContent.setTextColor(getColor(R.color.color_303030));
                }
                RLinearLayout relayoutNoteRoot = getRelayoutNoteRoot();
                RBaseHelper helper2 = relayoutNoteRoot != null ? relayoutNoteRoot.getHelper() : null;
                if (helper2 != null) {
                    helper2.setBackgroundColorNormal(getColor(R.color.white));
                }
                LinearLayout layoutAddNote = getLayoutAddNote();
                if (layoutAddNote != null) {
                    layoutAddNote.setBackground(new RoundCornerDrawable(getColor(R.color.color_f9fafb), ConvertExtensionsKt.dpToPx(100)));
                }
                View viewLine = getViewLine();
                if (viewLine != null) {
                    viewLine.setBackground(new ColorDrawable(getColor(R.color.color_eeeeee)));
                }
                View viewContentLine = getViewContentLine();
                if (viewContentLine != null) {
                    viewContentLine.setBackground(new ColorDrawable(getColor(R.color.color_eeeeee)));
                }
                RRelativeLayout relayoutNoteContent = getRelayoutNoteContent();
                RBaseHelper helper3 = relayoutNoteContent != null ? relayoutNoteContent.getHelper() : null;
                if (helper3 != null) {
                    gradientDrawable.setColor(ColorStateList.valueOf(StringExtensionsKt.hexValue2IntColor("#F9FAFB")));
                    helper3.setBackgroundDrawableNormal(gradientDrawable);
                }
                RTextView tvCopy = getTvCopy();
                RTextViewHelper helper4 = tvCopy != null ? tvCopy.getHelper() : null;
                if (helper4 != null) {
                    helper4.setIconNormalTop(getDrawable(R.drawable.icon_note_copy));
                }
                RTextView tvShare = getTvShare();
                RTextViewHelper helper5 = tvShare != null ? tvShare.getHelper() : null;
                if (helper5 != null) {
                    helper5.setIconNormalTop(getDrawable(R.drawable.icon_note_share));
                }
                RTextView tvDrawLine = getTvDrawLine();
                RTextViewHelper helper6 = tvDrawLine != null ? tvDrawLine.getHelper() : null;
                if (helper6 != null) {
                    helper6.setTextColorNormal(getColor(R.color.color_303030));
                }
                RTextView tvCopy2 = getTvCopy();
                RTextViewHelper helper7 = tvCopy2 != null ? tvCopy2.getHelper() : null;
                if (helper7 != null) {
                    helper7.setTextColorNormal(getColor(R.color.color_303030));
                }
                RTextView tvShare2 = getTvShare();
                helper = tvShare2 != null ? tvShare2.getHelper() : null;
                if (helper != null) {
                    helper.setTextColorNormal(getColor(R.color.color_303030));
                }
                TextView tvAddNote = getTvAddNote();
                if (tvAddNote != null) {
                    tvAddNote.setHintTextColor(getColor(R.color.color_c2c6cb));
                }
            } else if (colorMode == 1) {
                View viewFindViewById = findViewById(R.id.line);
                if (viewFindViewById != null) {
                    viewFindViewById.setBackground(new ColorDrawable(StringExtensionsKt.hexValue2IntColor("#D6C9A9")));
                }
                TextView tvNoteTitle2 = getTvNoteTitle();
                if (tvNoteTitle2 != null) {
                    tvNoteTitle2.setTextColor(getColor(R.color.color_303030));
                }
                ImageView imgClose3 = getImgClose();
                if (imgClose3 != null) {
                    imgClose3.setImageResource(R.mipmap.ic_close_color_mode_1);
                }
                TextView tvContent2 = getTvContent();
                if (tvContent2 != null) {
                    tvContent2.setTextColor(getColor(R.color.color_303030));
                }
                RLinearLayout relayoutNoteRoot2 = getRelayoutNoteRoot();
                RBaseHelper helper8 = relayoutNoteRoot2 != null ? relayoutNoteRoot2.getHelper() : null;
                if (helper8 != null) {
                    helper8.setBackgroundColorNormal(getColor(R.color.color_F5EBCE));
                }
                LinearLayout layoutAddNote2 = getLayoutAddNote();
                if (layoutAddNote2 != null) {
                    layoutAddNote2.setBackground(new RoundCornerDrawable(getColor(R.color.color_EDE2C3), ConvertExtensionsKt.dpToPx(100)));
                }
                View viewLine2 = getViewLine();
                if (viewLine2 != null) {
                    viewLine2.setBackground(new ColorDrawable(getColor(R.color.color_EDE2C3)));
                }
                View viewContentLine2 = getViewContentLine();
                if (viewContentLine2 != null) {
                    viewContentLine2.setBackground(new ColorDrawable(getColor(R.color.color_EDE2C3)));
                }
                RRelativeLayout relayoutNoteContent2 = getRelayoutNoteContent();
                RBaseHelper helper9 = relayoutNoteContent2 != null ? relayoutNoteContent2.getHelper() : null;
                if (helper9 != null) {
                    gradientDrawable.setColor(ColorStateList.valueOf(StringExtensionsKt.hexValue2IntColor("#EDE2C3")));
                    helper9.setBackgroundDrawableNormal(gradientDrawable);
                }
                RTextView tvCopy3 = getTvCopy();
                RTextViewHelper helper10 = tvCopy3 != null ? tvCopy3.getHelper() : null;
                if (helper10 != null) {
                    helper10.setIconNormalTop(getDrawable(R.drawable.icon_note_copy));
                }
                RTextView tvShare3 = getTvShare();
                RTextViewHelper helper11 = tvShare3 != null ? tvShare3.getHelper() : null;
                if (helper11 != null) {
                    helper11.setIconNormalTop(getDrawable(R.drawable.icon_note_share));
                }
                RTextView tvDrawLine2 = getTvDrawLine();
                RTextViewHelper helper12 = tvDrawLine2 != null ? tvDrawLine2.getHelper() : null;
                if (helper12 != null) {
                    helper12.setTextColorNormal(getColor(R.color.color_303030));
                }
                RTextView tvCopy4 = getTvCopy();
                RTextViewHelper helper13 = tvCopy4 != null ? tvCopy4.getHelper() : null;
                if (helper13 != null) {
                    helper13.setTextColorNormal(getColor(R.color.color_303030));
                }
                RTextView tvShare4 = getTvShare();
                helper = tvShare4 != null ? tvShare4.getHelper() : null;
                if (helper != null) {
                    helper.setTextColorNormal(getColor(R.color.color_303030));
                }
                TextView tvAddNote2 = getTvAddNote();
                if (tvAddNote2 != null) {
                    tvAddNote2.setHintTextColor(getColor(R.color.color_c2c6cb));
                }
            } else if (colorMode == 2) {
                View viewFindViewById2 = findViewById(R.id.line);
                if (viewFindViewById2 != null) {
                    viewFindViewById2.setBackground(new ColorDrawable(StringExtensionsKt.hexValue2IntColor("#1C2134")));
                }
                TextView tvNoteTitle3 = getTvNoteTitle();
                if (tvNoteTitle3 != null) {
                    tvNoteTitle3.setTextColor(getColor(R.color.color_7380a9));
                }
                ImageView imgClose4 = getImgClose();
                if (imgClose4 != null) {
                    imgClose4.setImageResource(R.drawable.icon_close_night_svg);
                }
                TextView tvContent3 = getTvContent();
                if (tvContent3 != null) {
                    tvContent3.setTextColor(getColor(R.color.color_7380a9));
                }
                RLinearLayout relayoutNoteRoot3 = getRelayoutNoteRoot();
                RBaseHelper helper14 = relayoutNoteRoot3 != null ? relayoutNoteRoot3.getHelper() : null;
                if (helper14 != null) {
                    helper14.setBackgroundColorNormal(getColor(R.color.color_121622));
                }
                LinearLayout layoutAddNote3 = getLayoutAddNote();
                if (layoutAddNote3 != null) {
                    layoutAddNote3.setBackground(new RoundCornerDrawable(getColor(R.color.color_171c2d), ConvertExtensionsKt.dpToPx(100)));
                }
                View viewLine3 = getViewLine();
                if (viewLine3 != null) {
                    viewLine3.setBackground(new ColorDrawable(getColor(R.color.color_1C2134)));
                }
                View viewContentLine3 = getViewContentLine();
                if (viewContentLine3 != null) {
                    viewContentLine3.setBackground(new ColorDrawable(getColor(R.color.color_1C2134)));
                }
                RRelativeLayout relayoutNoteContent3 = getRelayoutNoteContent();
                RBaseHelper helper15 = relayoutNoteContent3 != null ? relayoutNoteContent3.getHelper() : null;
                if (helper15 != null) {
                    gradientDrawable.setColor(ColorStateList.valueOf(StringExtensionsKt.hexValue2IntColor("#0D111D")));
                    helper15.setBackgroundDrawableNormal(gradientDrawable);
                }
                RTextView tvCopy5 = getTvCopy();
                RTextViewHelper helper16 = tvCopy5 != null ? tvCopy5.getHelper() : null;
                if (helper16 != null) {
                    helper16.setIconNormalTop(getDrawable(R.drawable.icon_note_copy_night_svg));
                }
                RTextView tvShare5 = getTvShare();
                RTextViewHelper helper17 = tvShare5 != null ? tvShare5.getHelper() : null;
                if (helper17 != null) {
                    helper17.setIconNormalTop(getDrawable(R.drawable.icon_note_share_night_svg));
                }
                RTextView tvDrawLine3 = getTvDrawLine();
                RTextViewHelper helper18 = tvDrawLine3 != null ? tvDrawLine3.getHelper() : null;
                if (helper18 != null) {
                    helper18.setTextColorNormal(getColor(R.color.color_7380a9));
                }
                RTextView tvCopy6 = getTvCopy();
                RTextViewHelper helper19 = tvCopy6 != null ? tvCopy6.getHelper() : null;
                if (helper19 != null) {
                    helper19.setTextColorNormal(getColor(R.color.color_7380a9));
                }
                RTextView tvShare6 = getTvShare();
                helper = tvShare6 != null ? tvShare6.getHelper() : null;
                if (helper != null) {
                    helper.setTextColorNormal(getColor(R.color.color_7380a9));
                }
                TextView tvAddNote3 = getTvAddNote();
                if (tvAddNote3 != null) {
                    tvAddNote3.setHintTextColor(getColor(R.color.color_575F79));
                }
            }
            int colorMode2 = readConfig.getColorMode();
            SmartRefreshLayout refresh4 = getRefresh();
            Intrinsics.checkNotNull(refresh4);
            ClassicsHeader refreshHeader2 = getRefreshHeader();
            Intrinsics.checkNotNull(refreshHeader2);
            ViewUtilKt.setRefreshTileView(colorMode2, refresh4, refreshHeader2, getContext());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$3(Builder this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dismiss();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$4(Builder this$0, View it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            TextView tvContent = this$0.getTvContent();
            if (tvContent != null) {
                tvContent.setText("引文：“" + StringExtensionsKt.formatContent(this$0.drawContent) + Typography.rightDoubleQuote);
            }
            Intrinsics.checkNotNullExpressionValue(it, "it");
            ViewExtensionsKt.gone(it);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$5(Builder this$0, RefreshLayout it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.page = 1;
            loadData$default(this$0, this$0.bookId, this$0.chapterId, this$0.startPosition, this$0.length, this$0.drawContent, false, 32, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$6(Builder this$0, RefreshLayout it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.page++;
            loadData$default(this$0, this$0.bookId, this$0.chapterId, this$0.startPosition, this$0.length, this$0.drawContent, false, 32, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void addNote(Map<String, String> params) {
            Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new NoteListDialog$Builder$addNote$1(params, null), 15, null), null, new NoteListDialog$Builder$addNote$2(this, null), 1, null), null, new NoteListDialog$Builder$addNote$3(null), 1, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:68:0x01ed  */
        /* JADX WARN: Removed duplicated region for block: B:69:0x01f1  */
        /* JADX WARN: Removed duplicated region for block: B:76:0x0213  */
        /* JADX WARN: Removed duplicated region for block: B:79:0x023c  */
        /* JADX WARN: Removed duplicated region for block: B:86:0x02bd  */
        @android.annotation.SuppressLint({"UseCompatLoadingForDrawables"})
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void convert(android.content.Context r20, com.ykb.ebook.adapter.base.QuickViewHolder r21, final int r22, final com.ykb.ebook.model.NoteListBean r23) {
            /*
                Method dump skipped, instructions count: 761
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.dialog.NoteListDialog.Builder.convert(android.content.Context, com.ykb.ebook.adapter.base.QuickViewHolder, int, com.ykb.ebook.model.NoteListBean):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void convert$lambda$19(Builder this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            String string = view.getTag().toString();
            if (string == null || string.length() == 0) {
                Toast.makeText(this$0.getContext(), "该用户已注销", 0).show();
                return;
            }
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setPackage(view.getContext().getPackageName());
            intent.setData(Uri.parse("ebook://ykb_user_info/?user_id=" + view.getTag()));
            this$0.getContext().startActivity(intent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void convert$lambda$20(boolean z2, final NoteListBean item, final Builder this$0, final int i2, View view) {
            Intrinsics.checkNotNullParameter(item, "$item");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (z2) {
                Context context = view.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "it.context");
                new WriteNoteDialog.Builder(context, item.getDisplay_status(), false, 4, null).setCitation(this$0.drawContent).setEditContent(item.getNotes_content()).setPublishClick(new Function2<String, Integer, Unit>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$convert$2$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ Unit invoke(String str, Integer num) {
                        invoke(str, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(@NotNull String noteContent, int i3) {
                        Intrinsics.checkNotNullParameter(noteContent, "noteContent");
                        this.this$0.editNote(item, i2, noteContent, i3);
                    }
                }).show();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void convert$lambda$21(boolean z2, final Builder this$0, final NoteListBean item, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(item, "$item");
            if (z2) {
                Context context = view.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "it.context");
                new CommonSureDialog.Builder(context, "确定删除该笔记吗？", new Function0<Unit>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$convert$3$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        this.this$0.delNote(item);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }
                }).show();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void delNote(NoteListBean item) {
            Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new NoteListDialog$Builder$delNote$1(MapsKt__MapsKt.hashMapOf(new Pair("app_id", "40"), new Pair("id", item.getId()), new Pair("user_id", "583383")), null), 15, null), null, new NoteListDialog$Builder$delNote$2(this, item, null), 1, null), null, new NoteListDialog$Builder$delNote$3(null), 1, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void editNote(NoteListBean item, int position, String content, int permission) {
            Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new NoteListDialog$Builder$editNote$1(MapsKt__MapsKt.hashMapOf(new Pair("ap_id", "40"), new Pair("id", item.getId()), new Pair("notes_content", content), new Pair("display_status", String.valueOf(permission)), new Pair("user_id", "583383")), null), 15, null), null, new NoteListDialog$Builder$editNote$2(item, content, permission, this, position, null), 1, null), null, new NoteListDialog$Builder$editNote$3(null), 1, null);
        }

        private final ImageView getImgAddNote() {
            return (ImageView) this.imgAddNote.getValue();
        }

        private final ImageView getImgClose() {
            return (ImageView) this.imgClose.getValue();
        }

        private final RImageView getImgHead() {
            return (RImageView) this.imgHead.getValue();
        }

        private final LinearLayout getLayoutAddNote() {
            return (LinearLayout) this.layoutAddNote.getValue();
        }

        private final LinearLayout getLlMore() {
            return (LinearLayout) this.llMore.getValue();
        }

        private final TextView getMoreNoteContent() {
            return (TextView) this.moreNoteContent.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final RecyclerView getRecycler() {
            return (RecyclerView) this.recycler.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final SmartRefreshLayout getRefresh() {
            return (SmartRefreshLayout) this.refresh.getValue();
        }

        private final ClassicsHeader getRefreshHeader() {
            return (ClassicsHeader) this.refreshHeader.getValue();
        }

        private final RRelativeLayout getRelayoutNoteContent() {
            return (RRelativeLayout) this.relayoutNoteContent.getValue();
        }

        private final RLinearLayout getRelayoutNoteRoot() {
            return (RLinearLayout) this.relayoutNoteRoot.getValue();
        }

        private final TextView getTvAddNote() {
            return (TextView) this.tvAddNote.getValue();
        }

        private final TextView getTvContent() {
            return (TextView) this.tvContent.getValue();
        }

        private final RTextView getTvCopy() {
            return (RTextView) this.tvCopy.getValue();
        }

        private final RTextView getTvDrawLine() {
            return (RTextView) this.tvDrawLine.getValue();
        }

        private final TextView getTvNoteTitle() {
            return (TextView) this.tvNoteTitle.getValue();
        }

        private final RTextView getTvShare() {
            return (RTextView) this.tvShare.getValue();
        }

        private final View getViewContentLine() {
            return (View) this.viewContentLine.getValue();
        }

        private final View getViewLine() {
            return (View) this.viewLine.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void loadData(String bookId, String chapterId, String startPosition, String length, String drawContent, boolean isAddNote) {
            Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new NoteListDialog$Builder$loadData$1(MapsKt__MapsKt.hashMapOf(new Pair("user_id", "583383"), new Pair("app_id", "40"), new Pair(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(this.page)), new Pair("page_size", String.valueOf(this.pageSize)), new Pair("book_id", bookId), new Pair("chapter_id", chapterId), new Pair(PreferKeyKt.START_POSITION, startPosition), new Pair(SessionDescription.ATTR_LENGTH, length), new Pair("draw_content", drawContent)), null), 15, null), null, new NoteListDialog$Builder$loadData$2(this, isAddNote, null), 1, null), null, new NoteListDialog$Builder$loadData$3(null), 1, null);
        }

        public static /* synthetic */ void loadData$default(Builder builder, String str, String str2, String str3, String str4, String str5, boolean z2, int i2, Object obj) {
            if ((i2 & 32) != 0) {
                z2 = false;
            }
            builder.loadData(str, str2, str3, str4, str5, z2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setParams$lambda$15$lambda$13(Builder this_apply, String content) {
            Layout layout;
            Intrinsics.checkNotNullParameter(this_apply, "$this_apply");
            Intrinsics.checkNotNullParameter(content, "$content");
            TextView tvContent = this_apply.getTvContent();
            if (tvContent == null || (layout = tvContent.getLayout()) == null || layout.getLineCount() <= 3) {
                return;
            }
            int lineEnd = layout.getLineEnd(2);
            StringBuilder sb = new StringBuilder();
            String strSubstring = content.substring(0, lineEnd - 5);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
            sb.append(strSubstring);
            sb.append("...");
            String string = sb.toString();
            TextView tvContent2 = this_apply.getTvContent();
            if (tvContent2 == null) {
                return;
            }
            tvContent2.setText(StringExtensionsKt.formatContent(string));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setParams$lambda$15$lambda$14(Builder this_apply) {
            Intrinsics.checkNotNullParameter(this_apply, "$this_apply");
            TextView tvContent = this_apply.getTvContent();
            String content = StringExtensionsKt.formatContent(String.valueOf(tvContent != null ? tvContent.getText() : null));
            if (content.length() > 30) {
                String strSubstring = content.substring(content.length() - 3, content.length());
                Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                if (Intrinsics.areEqual(strSubstring, "...")) {
                    LinearLayout llMore = this_apply.getLlMore();
                    if (llMore != null) {
                        ViewExtensionsKt.visible(llMore);
                        return;
                    }
                    return;
                }
                LinearLayout llMore2 = this_apply.getLlMore();
                if (llMore2 != null) {
                    ViewExtensionsKt.gone(llMore2);
                }
            }
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.Builder, com.ykb.ebook.common_interface.ClickAction, android.view.View.OnClickListener
        public void onClick(@NotNull View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            if (Intrinsics.areEqual(view, getTvAddNote())) {
                new WriteNoteDialog.Builder(getContext(), 3, false, 4, null).setCitation(StringExtensionsKt.formatContent(this.drawContent)).setPublishClick(new Function2<String, Integer, Unit>() { // from class: com.ykb.ebook.dialog.NoteListDialog$Builder$onClick$1
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ Unit invoke(String str, Integer num) {
                        invoke(str, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(@NotNull String noteContent, int i2) {
                        Intrinsics.checkNotNullParameter(noteContent, "noteContent");
                        this.this$0.addNote(MapsKt__MapsKt.hashMapOf(new Pair("app_id", "40"), new Pair("book_id", this.this$0.bookId), new Pair("draw_content", this.this$0.drawContent), new Pair("chapter_id", this.this$0.chapterId), new Pair("user_id", "583383"), new Pair(SessionDescription.ATTR_LENGTH, this.this$0.length), new Pair(PreferKeyKt.START_POSITION, this.this$0.startPosition), new Pair("notes_content", noteContent), new Pair("display_status", String.valueOf(i2))));
                    }
                }).show();
                return;
            }
            if (Intrinsics.areEqual(view, getTvCopy())) {
                ContextExtensionsKt.sendToClip(getContext(), StringExtensionsKt.formatContent(this.drawContent));
                return;
            }
            if (Intrinsics.areEqual(view, getTvShare())) {
                dismiss();
                Function0<Unit> function0 = this.onShareClick;
                if (function0 != null) {
                    function0.invoke();
                    return;
                }
                return;
            }
            if (Intrinsics.areEqual(view, getTvDrawLine())) {
                boolean z2 = !this.hasDrawLine;
                this.hasDrawLine = z2;
                Function1<? super Boolean, Unit> function1 = this.onDrawLineClick;
                if (function1 != null) {
                    function1.invoke(Boolean.valueOf(z2));
                }
                dismiss();
            }
        }

        @NotNull
        public final Builder setDeleteClick(@NotNull Function0<Unit> onDeleteClick) {
            Intrinsics.checkNotNullParameter(onDeleteClick, "onDeleteClick");
            this.onDeleteClick = onDeleteClick;
            return this;
        }

        @NotNull
        public final Builder setDrawLineClick(@NotNull Function1<? super Boolean, Unit> drawLineClick) {
            Intrinsics.checkNotNullParameter(drawLineClick, "drawLineClick");
            this.onDrawLineClick = drawLineClick;
            return this;
        }

        @NotNull
        public final Builder setParams(@NotNull String bookId, @NotNull String chapterId, int startPosition, int length, @NotNull String drawContent, boolean hasDrawLine, boolean allowDrawLine) {
            RTextViewHelper helper;
            Intrinsics.checkNotNullParameter(bookId, "bookId");
            Intrinsics.checkNotNullParameter(chapterId, "chapterId");
            Intrinsics.checkNotNullParameter(drawContent, "drawContent");
            this.bookId = bookId;
            this.allowDrawLine = allowDrawLine;
            this.chapterId = chapterId;
            this.startPosition = String.valueOf(startPosition);
            this.length = String.valueOf(length);
            this.drawContent = drawContent;
            final String str = "引文：“" + StringsKt__StringsKt.trim((CharSequence) drawContent).toString() + Typography.rightDoubleQuote;
            TextView tvContent = getTvContent();
            if (tvContent != null) {
                tvContent.setText(StringExtensionsKt.formatContent(str));
            }
            TextView tvContent2 = getTvContent();
            if (tvContent2 != null) {
                tvContent2.post(new Runnable() { // from class: com.ykb.ebook.dialog.k0
                    @Override // java.lang.Runnable
                    public final void run() {
                        NoteListDialog.Builder.setParams$lambda$15$lambda$13(this.f26349c, str);
                    }
                });
            }
            LinearLayout llMore = getLlMore();
            if (llMore != null) {
                llMore.postDelayed(new Runnable() { // from class: com.ykb.ebook.dialog.l0
                    @Override // java.lang.Runnable
                    public final void run() {
                        NoteListDialog.Builder.setParams$lambda$15$lambda$14(this.f26354c);
                    }
                }, 200L);
            }
            this.hasDrawLine = hasDrawLine;
            RTextView tvDrawLine = getTvDrawLine();
            if (tvDrawLine != null) {
                tvDrawLine.setText(!hasDrawLine ? "划线" : "取消划线");
            }
            if (ReadConfig.INSTANCE.getColorMode() == 2) {
                RTextView tvDrawLine2 = getTvDrawLine();
                helper = tvDrawLine2 != null ? tvDrawLine2.getHelper() : null;
                if (helper != null) {
                    helper.setIconNormalTop(getDrawable(!hasDrawLine ? R.drawable.icon_note_draw_line_night_svg : R.drawable.icon_note_draw_cancel_line_noght));
                }
            } else {
                RTextView tvDrawLine3 = getTvDrawLine();
                helper = tvDrawLine3 != null ? tvDrawLine3.getHelper() : null;
                if (helper != null) {
                    helper.setIconNormalTop(getDrawable(!hasDrawLine ? R.drawable.icon_note_draw_line : R.drawable.icon_note_draw_cancel_line));
                }
            }
            loadData$default(this, bookId, chapterId, String.valueOf(startPosition), String.valueOf(length), drawContent, false, 32, null);
            return this;
        }

        @NotNull
        public final Builder setShareClick(@NotNull Function0<Unit> onShareClick) {
            Intrinsics.checkNotNullParameter(onShareClick, "onShareClick");
            this.onShareClick = onShareClick;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void convert(QuickViewHolder holder, NoteListBean item, List<? extends Object> payloads) {
            Drawable drawable;
            if (!payloads.isEmpty()) {
                Object obj = payloads.get(0);
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                if (((Integer) obj).intValue() == 1) {
                    holder.setText(R.id.tv_comment, item.getNotes_content());
                    RTextView rTextView = (RTextView) holder.getView(R.id.tv_permission);
                    int display_status = item.getDisplay_status();
                    if (display_status == 1) {
                        drawable = getDrawable(R.drawable.icon_write_open);
                    } else if (display_status == 2) {
                        drawable = getDrawable(R.drawable.icon_write_attention);
                    } else if (display_status == 3) {
                        drawable = getDrawable(R.drawable.icon_write_private);
                    } else if (display_status != 4) {
                        drawable = getDrawable(R.drawable.icon_error_recovery);
                    } else {
                        drawable = getDrawable(R.drawable.icon_write_shield);
                    }
                    int display_status2 = item.getDisplay_status();
                    String str = display_status2 != 1 ? display_status2 != 2 ? display_status2 != 3 ? display_status2 != 4 ? "" : "屏蔽好友" : "私密" : "关注" : "公开";
                    if (ReadConfig.INSTANCE.getColorMode() == 2) {
                        if (drawable != null) {
                            drawable.setColorFilter(new PorterDuffColorFilter(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_7380a9), PorterDuff.Mode.SRC_IN));
                        }
                    } else if (drawable != null) {
                        drawable.setColorFilter(null);
                    }
                    rTextView.getHelper().setIconNormalLeft(drawable);
                    rTextView.setText(str);
                }
            }
        }
    }
}

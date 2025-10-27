package com.ykb.ebook.dialog;

import android.content.AppCtxKt;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.ColorResourcesKt;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.ruffian.library.widget.RLinearLayout;
import com.ruffian.library.widget.helper.RBaseHelper;
import com.ykb.ebook.R;
import com.ykb.ebook.adapter.CommonAdapter;
import com.ykb.ebook.adapter.base.QuickViewHolder;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.dialog.NotePermissionDialogNew;
import com.ykb.ebook.model.PermissionItem;
import com.ykb.ebook.util.BitmapUtils;
import java.util.ArrayList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/NotePermissionDialogNew;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class NotePermissionDialogNew {

    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J \u0010 \u001a\u00020\u00002\u0018\u0010!\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00180\u0016R\u001d\u0010\u0007\u001a\u0004\u0018\u00010\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u001d\u0010\r\u001a\u0004\u0018\u00010\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\f\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u0015\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0018\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u001d\u0010\u001b\u001a\u0004\u0018\u00010\u001c8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\f\u001a\u0004\b\u001d\u0010\u001e¨\u0006\""}, d2 = {"Lcom/ykb/ebook/dialog/NotePermissionDialogNew$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "permission", "", "(Landroid/content/Context;I)V", "imgClose", "Landroid/widget/ImageView;", "getImgClose", "()Landroid/widget/ImageView;", "imgClose$delegate", "Lkotlin/Lazy;", "layoutRoot", "Lcom/ruffian/library/widget/RLinearLayout;", "getLayoutRoot", "()Lcom/ruffian/library/widget/RLinearLayout;", "layoutRoot$delegate", "mAdapter", "Lcom/ykb/ebook/adapter/CommonAdapter;", "Lcom/ykb/ebook/model/PermissionItem;", "onPermissionCallback", "Lkotlin/Function2;", "", "", "getPermission", "()I", "tvTitle", "Landroid/widget/TextView;", "getTvTitle", "()Landroid/widget/TextView;", "tvTitle$delegate", "setPermissionCallback", com.alipay.sdk.authjs.a.f3170c, "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nNotePermissionDialogNew.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NotePermissionDialogNew.kt\ncom/ykb/ebook/dialog/NotePermissionDialogNew$Builder\n+ 2 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,107:1\n42#2:108\n42#2:109\n42#2:110\n2634#3:111\n1#4:112\n*S KotlinDebug\n*F\n+ 1 NotePermissionDialogNew.kt\ncom/ykb/ebook/dialog/NotePermissionDialogNew$Builder\n*L\n40#1:108\n41#1:109\n43#1:110\n97#1:111\n97#1:112\n*E\n"})
    public static final class Builder extends BasicDialog.Builder<Builder> {

        /* renamed from: imgClose$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgClose;

        /* renamed from: layoutRoot$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy layoutRoot;

        @NotNull
        private final CommonAdapter<PermissionItem> mAdapter;

        @Nullable
        private Function2<? super Integer, ? super String, Unit> onPermissionCallback;
        private final int permission;

        /* renamed from: tvTitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvTitle;

        @Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\n¢\u0006\u0002\b\b"}, d2 = {"<anonymous>", "", "holder", "Lcom/ykb/ebook/adapter/base/QuickViewHolder;", "<anonymous parameter 1>", "", "item", "Lcom/ykb/ebook/model/PermissionItem;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
        @SourceDebugExtension({"SMAP\nNotePermissionDialogNew.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NotePermissionDialogNew.kt\ncom/ykb/ebook/dialog/NotePermissionDialogNew$Builder$2\n+ 2 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n*L\n1#1,107:1\n42#2:108\n42#2:109\n42#2:110\n*S KotlinDebug\n*F\n+ 1 NotePermissionDialogNew.kt\ncom/ykb/ebook/dialog/NotePermissionDialogNew$Builder$2\n*L\n53#1:108\n82#1:109\n84#1:110\n*E\n"})
        /* renamed from: com.ykb.ebook.dialog.NotePermissionDialogNew$Builder$2, reason: invalid class name */
        public static final class AnonymousClass2 extends Lambda implements Function3<QuickViewHolder, Integer, PermissionItem, Unit> {
            final /* synthetic */ Context $context;
            final /* synthetic */ Builder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(Context context, Builder builder) {
                super(3);
                this.$context = context;
                this.this$0 = builder;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static final void invoke$lambda$0(Builder this$0, PermissionItem permissionItem, View view) {
                Intrinsics.checkNotNullParameter(this$0, "this$0");
                Function2 function2 = this$0.onPermissionCallback;
                if (function2 != null) {
                    function2.invoke(Integer.valueOf(permissionItem.getValue()), permissionItem.getTitle());
                }
                this$0.dismiss();
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(QuickViewHolder quickViewHolder, Integer num, PermissionItem permissionItem) {
                invoke(quickViewHolder, num.intValue(), permissionItem);
                return Unit.INSTANCE;
            }

            public final void invoke(@NotNull QuickViewHolder holder, int i2, @Nullable final PermissionItem permissionItem) {
                int color;
                int color2;
                Intrinsics.checkNotNullParameter(holder, "holder");
                if (permissionItem != null) {
                    int i3 = R.id.line;
                    ReadConfig readConfig = ReadConfig.INSTANCE;
                    holder.setBackgroundColor(i3, ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_1C2134 : readConfig.getColorMode() == 0 ? R.color.color_eeeeee : R.color.color_EDE2C3));
                    View view = holder.itemView;
                    final Builder builder = this.this$0;
                    view.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.q0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            NotePermissionDialogNew.Builder.AnonymousClass2.invoke$lambda$0(builder, permissionItem, view2);
                        }
                    });
                    if (permissionItem.getSelect()) {
                        color2 = readConfig.getColorMode() == 2 ? ContextCompat.getColor(this.$context, R.color.color_B2575C) : ContextCompat.getColor(this.$context, R.color.color_dd594a);
                        color = readConfig.getColorMode() == 2 ? ContextCompat.getColor(this.$context, R.color.color_B2575C) : ContextCompat.getColor(this.$context, R.color.color_dd594a);
                    } else {
                        color = readConfig.getColorMode() == 2 ? ContextCompat.getColor(this.$context, R.color.color_575F79) : ContextCompat.getColor(this.$context, R.color.color_909090);
                        color2 = readConfig.getColorMode() == 2 ? ContextCompat.getColor(this.$context, R.color.color_7380a9) : ContextCompat.getColor(this.$context, R.color.color_303030);
                    }
                    holder.getView(R.id.iv).setVisibility(permissionItem.getSelect() ? 0 : 8);
                    int i4 = R.id.tv_permission;
                    ((TextView) holder.getView(i4)).setText(permissionItem.getTitle());
                    ((TextView) holder.getView(i4)).setTextColor(color2);
                    int i5 = R.id.tv_tip;
                    ((TextView) holder.getView(i5)).setText(permissionItem.getTip());
                    ((TextView) holder.getView(i5)).setTextColor(color);
                    if (readConfig.getColorMode() != 2) {
                        ((ImageView) holder.getView(R.id.iv_icon)).setImageResource(permissionItem.getSelect() ? permissionItem.getSelectIcon() : permissionItem.getUnSelectIcon());
                        return;
                    }
                    Drawable drawable = this.$context.getDrawable(permissionItem.getSelect() ? permissionItem.getSelectIcon() : permissionItem.getUnSelectIcon());
                    if (permissionItem.getSelect()) {
                        if (drawable != null) {
                            drawable.setColorFilter(new PorterDuffColorFilter(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_B2575C), PorterDuff.Mode.SRC_IN));
                        }
                    } else if (drawable != null) {
                        drawable.setColorFilter(new PorterDuffColorFilter(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_7380a9), PorterDuff.Mode.SRC_IN));
                    }
                    ((ImageView) holder.getView(R.id.iv_icon)).setImageDrawable(drawable);
                }
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull Context context, int i2) throws SecurityException {
            View viewFindViewById;
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            this.permission = i2;
            this.imgClose = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.NotePermissionDialogNew$Builder$imgClose$2
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
            this.tvTitle = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.NotePermissionDialogNew$Builder$tvTitle$2
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
            this.layoutRoot = LazyKt__LazyJVMKt.lazy(new Function0<RLinearLayout>() { // from class: com.ykb.ebook.dialog.NotePermissionDialogNew$Builder$layoutRoot$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RLinearLayout invoke() {
                    return (RLinearLayout) this.this$0.findViewById(R.id.layoutRoot);
                }
            });
            CommonAdapter<PermissionItem> commonAdapter = new CommonAdapter<>(R.layout.item_note_permission, null, 2, null);
            this.mAdapter = commonAdapter;
            setContentView(R.layout.dialog_note_permission_new);
            RLinearLayout layoutRoot = getLayoutRoot();
            RBaseHelper helper = layoutRoot != null ? layoutRoot.getHelper() : null;
            if (helper != null) {
                helper.setBackgroundDrawableNormal(BitmapUtils.INSTANCE.getTopRadiusDrawabl(getContext(), 16));
            }
            ImageView imgClose = getImgClose();
            if (imgClose != null) {
                imgClose.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.p0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        NotePermissionDialogNew.Builder._init_$lambda$0(this.f26373c, view);
                    }
                });
            }
            ReadConfig readConfig = ReadConfig.INSTANCE;
            if (readConfig.getColorMode() == 2) {
                TextView tvTitle = getTvTitle();
                if (tvTitle != null) {
                    tvTitle.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_7380a9));
                }
                View viewFindViewById2 = findViewById(R.id.line);
                if (viewFindViewById2 != null) {
                    viewFindViewById2.setBackground(new ColorDrawable(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_1C2134)));
                }
            } else if (readConfig.getColorMode() == 1 && (viewFindViewById = findViewById(R.id.line)) != null) {
                viewFindViewById.setBackground(new ColorDrawable(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_EDE2C3)));
            }
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            setAnimStyle(AnimAction.INSTANCE.getANIM_BOTTOM());
            setGravity(80);
            commonAdapter.setConvert(new AnonymousClass2(context, this));
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvPermissions);
            if (recyclerView != null) {
                recyclerView.setAdapter(commonAdapter);
            }
            ArrayList<PermissionItem> arrayList = new ArrayList();
            arrayList.add(new PermissionItem(1, "公开", "所有人可见", R.drawable.icon_permission_open_check, R.drawable.icon_permission_open_normal, false, 32, null));
            arrayList.add(new PermissionItem(3, "私密", "仅自己可见", R.drawable.icon_permission_private_check, R.drawable.icon_permission_private_normal, false, 32, null));
            arrayList.add(new PermissionItem(2, "关注", "仅互相关注可见", R.drawable.icon_permission_attention_check, R.drawable.icon_permission_attention_normal, false, 32, null));
            arrayList.add(new PermissionItem(4, "屏蔽好友", "仅未关注你的人可见", R.drawable.icon_permission_shield_check, R.drawable.icon_permission_shield_normal, false, 32, null));
            for (PermissionItem permissionItem : arrayList) {
                permissionItem.setSelect(permissionItem.getValue() == this.permission);
            }
            this.mAdapter.submitList(arrayList);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$0(Builder this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dismiss();
        }

        private final ImageView getImgClose() {
            return (ImageView) this.imgClose.getValue();
        }

        private final RLinearLayout getLayoutRoot() {
            return (RLinearLayout) this.layoutRoot.getValue();
        }

        private final TextView getTvTitle() {
            return (TextView) this.tvTitle.getValue();
        }

        public final int getPermission() {
            return this.permission;
        }

        @NotNull
        public final Builder setPermissionCallback(@NotNull Function2<? super Integer, ? super String, Unit> callback) {
            Intrinsics.checkNotNullParameter(callback, "callback");
            this.onPermissionCallback = callback;
            return this;
        }

        public /* synthetic */ Builder(Context context, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(context, (i3 & 2) != 0 ? 0 : i2);
        }
    }
}

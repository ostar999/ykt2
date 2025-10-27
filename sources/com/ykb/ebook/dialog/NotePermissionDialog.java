package com.ykb.ebook.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.ruffian.library.widget.RCheckBox;
import com.ykb.ebook.R;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.dialog.BasicDialog;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/NotePermissionDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class NotePermissionDialog {

    @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b2\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010P\u001a\u00020O2\u0006\u0010Q\u001a\u00020RH\u0016J \u0010S\u001a\u00020\u00002\u0018\u0010T\u001a\u0014\u0012\u0004\u0012\u00020M\u0012\u0004\u0012\u00020N\u0012\u0004\u0012\u00020O0LJ\u000e\u0010U\u001a\u00020\u00002\u0006\u0010V\u001a\u00020MR\u001d\u0010\u0005\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001d\u0010\u000b\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\n\u001a\u0004\b\f\u0010\bR\u001d\u0010\u000e\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\n\u001a\u0004\b\u000f\u0010\bR\u001d\u0010\u0011\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\n\u001a\u0004\b\u0012\u0010\bR\u001d\u0010\u0014\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\n\u001a\u0004\b\u0015\u0010\bR\u001d\u0010\u0017\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\n\u001a\u0004\b\u0018\u0010\bR\u001d\u0010\u001a\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\n\u001a\u0004\b\u001b\u0010\bR\u001d\u0010\u001d\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\n\u001a\u0004\b\u001e\u0010\bR\u001d\u0010 \u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\"\u0010\n\u001a\u0004\b!\u0010\bR\u001d\u0010#\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b%\u0010\n\u001a\u0004\b$\u0010\bR\u001d\u0010&\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b(\u0010\n\u001a\u0004\b'\u0010\bR\u001d\u0010)\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b+\u0010\n\u001a\u0004\b*\u0010\bR\u001d\u0010,\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b.\u0010\n\u001a\u0004\b-\u0010\bR\u001d\u0010/\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b1\u0010\n\u001a\u0004\b0\u0010\bR\u001d\u00102\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b4\u0010\n\u001a\u0004\b3\u0010\bR\u001d\u00105\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b7\u0010\n\u001a\u0004\b6\u0010\bR\u001d\u00108\u001a\u0004\u0018\u0001098BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b<\u0010\n\u001a\u0004\b:\u0010;R\u001d\u0010=\u001a\u0004\u0018\u00010>8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bA\u0010\n\u001a\u0004\b?\u0010@R\u001d\u0010B\u001a\u0004\u0018\u00010>8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bD\u0010\n\u001a\u0004\bC\u0010@R\u001d\u0010E\u001a\u0004\u0018\u00010>8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bG\u0010\n\u001a\u0004\bF\u0010@R\u001d\u0010H\u001a\u0004\u0018\u00010>8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bJ\u0010\n\u001a\u0004\bI\u0010@R\"\u0010K\u001a\u0016\u0012\u0004\u0012\u00020M\u0012\u0004\u0012\u00020N\u0012\u0004\u0012\u00020O\u0018\u00010LX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006W"}, d2 = {"Lcom/ykb/ebook/dialog/NotePermissionDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "cbAttentionLeft", "Lcom/ruffian/library/widget/RCheckBox;", "getCbAttentionLeft", "()Lcom/ruffian/library/widget/RCheckBox;", "cbAttentionLeft$delegate", "Lkotlin/Lazy;", "cbAttentionRight", "getCbAttentionRight", "cbAttentionRight$delegate", "cbAttentionSubTitle", "getCbAttentionSubTitle", "cbAttentionSubTitle$delegate", "cbAttentionTitle", "getCbAttentionTitle", "cbAttentionTitle$delegate", "cbOpenLeft", "getCbOpenLeft", "cbOpenLeft$delegate", "cbOpenRight", "getCbOpenRight", "cbOpenRight$delegate", "cbOpenSubTitle", "getCbOpenSubTitle", "cbOpenSubTitle$delegate", "cbOpenTitle", "getCbOpenTitle", "cbOpenTitle$delegate", "cbPrivateLeft", "getCbPrivateLeft", "cbPrivateLeft$delegate", "cbPrivateRight", "getCbPrivateRight", "cbPrivateRight$delegate", "cbPrivateSubTitle", "getCbPrivateSubTitle", "cbPrivateSubTitle$delegate", "cbPrivateTitle", "getCbPrivateTitle", "cbPrivateTitle$delegate", "cbShieldLeft", "getCbShieldLeft", "cbShieldLeft$delegate", "cbShieldRight", "getCbShieldRight", "cbShieldRight$delegate", "cbShieldSubTitle", "getCbShieldSubTitle", "cbShieldSubTitle$delegate", "cbShieldTitle", "getCbShieldTitle", "cbShieldTitle$delegate", "imgClose", "Landroid/widget/ImageView;", "getImgClose", "()Landroid/widget/ImageView;", "imgClose$delegate", "llAttention", "Landroid/widget/LinearLayout;", "getLlAttention", "()Landroid/widget/LinearLayout;", "llAttention$delegate", "llOpen", "getLlOpen", "llOpen$delegate", "llPrivate", "getLlPrivate", "llPrivate$delegate", "llShield", "getLlShield", "llShield$delegate", "onPermissionCallback", "Lkotlin/Function2;", "", "", "", "onClick", "view", "Landroid/view/View;", "setPermissionCallback", com.alipay.sdk.authjs.a.f3170c, "setSelectPermission", "permission", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder extends BasicDialog.Builder<Builder> {

        /* renamed from: cbAttentionLeft$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbAttentionLeft;

        /* renamed from: cbAttentionRight$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbAttentionRight;

        /* renamed from: cbAttentionSubTitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbAttentionSubTitle;

        /* renamed from: cbAttentionTitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbAttentionTitle;

        /* renamed from: cbOpenLeft$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbOpenLeft;

        /* renamed from: cbOpenRight$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbOpenRight;

        /* renamed from: cbOpenSubTitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbOpenSubTitle;

        /* renamed from: cbOpenTitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbOpenTitle;

        /* renamed from: cbPrivateLeft$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbPrivateLeft;

        /* renamed from: cbPrivateRight$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbPrivateRight;

        /* renamed from: cbPrivateSubTitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbPrivateSubTitle;

        /* renamed from: cbPrivateTitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbPrivateTitle;

        /* renamed from: cbShieldLeft$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbShieldLeft;

        /* renamed from: cbShieldRight$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbShieldRight;

        /* renamed from: cbShieldSubTitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbShieldSubTitle;

        /* renamed from: cbShieldTitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbShieldTitle;

        /* renamed from: imgClose$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgClose;

        /* renamed from: llAttention$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy llAttention;

        /* renamed from: llOpen$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy llOpen;

        /* renamed from: llPrivate$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy llPrivate;

        /* renamed from: llShield$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy llShield;

        @Nullable
        private Function2<? super Integer, ? super String, Unit> onPermissionCallback;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull Context context) {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            this.imgClose = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$imgClose$2
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
            this.cbOpenLeft = LazyKt__LazyJVMKt.lazy(new Function0<RCheckBox>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$cbOpenLeft$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RCheckBox invoke() {
                    return (RCheckBox) this.this$0.findViewById(R.id.cb_open_left);
                }
            });
            this.cbOpenTitle = LazyKt__LazyJVMKt.lazy(new Function0<RCheckBox>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$cbOpenTitle$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RCheckBox invoke() {
                    return (RCheckBox) this.this$0.findViewById(R.id.cb_open_title);
                }
            });
            this.cbOpenSubTitle = LazyKt__LazyJVMKt.lazy(new Function0<RCheckBox>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$cbOpenSubTitle$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RCheckBox invoke() {
                    return (RCheckBox) this.this$0.findViewById(R.id.tv_open_sub_title);
                }
            });
            this.cbOpenRight = LazyKt__LazyJVMKt.lazy(new Function0<RCheckBox>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$cbOpenRight$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RCheckBox invoke() {
                    return (RCheckBox) this.this$0.findViewById(R.id.cb_open_right);
                }
            });
            this.cbPrivateLeft = LazyKt__LazyJVMKt.lazy(new Function0<RCheckBox>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$cbPrivateLeft$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RCheckBox invoke() {
                    return (RCheckBox) this.this$0.findViewById(R.id.cb_private_left);
                }
            });
            this.cbPrivateTitle = LazyKt__LazyJVMKt.lazy(new Function0<RCheckBox>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$cbPrivateTitle$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RCheckBox invoke() {
                    return (RCheckBox) this.this$0.findViewById(R.id.cb_private_title);
                }
            });
            this.cbPrivateSubTitle = LazyKt__LazyJVMKt.lazy(new Function0<RCheckBox>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$cbPrivateSubTitle$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RCheckBox invoke() {
                    return (RCheckBox) this.this$0.findViewById(R.id.tv_private_sub_title);
                }
            });
            this.cbPrivateRight = LazyKt__LazyJVMKt.lazy(new Function0<RCheckBox>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$cbPrivateRight$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RCheckBox invoke() {
                    return (RCheckBox) this.this$0.findViewById(R.id.cb_private_right);
                }
            });
            this.cbAttentionLeft = LazyKt__LazyJVMKt.lazy(new Function0<RCheckBox>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$cbAttentionLeft$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RCheckBox invoke() {
                    return (RCheckBox) this.this$0.findViewById(R.id.cb_attention_left);
                }
            });
            this.cbAttentionTitle = LazyKt__LazyJVMKt.lazy(new Function0<RCheckBox>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$cbAttentionTitle$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RCheckBox invoke() {
                    return (RCheckBox) this.this$0.findViewById(R.id.cb_attention_title);
                }
            });
            this.cbAttentionSubTitle = LazyKt__LazyJVMKt.lazy(new Function0<RCheckBox>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$cbAttentionSubTitle$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RCheckBox invoke() {
                    return (RCheckBox) this.this$0.findViewById(R.id.tv_attention_sub_title);
                }
            });
            this.cbAttentionRight = LazyKt__LazyJVMKt.lazy(new Function0<RCheckBox>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$cbAttentionRight$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RCheckBox invoke() {
                    return (RCheckBox) this.this$0.findViewById(R.id.cb_attention_right);
                }
            });
            this.cbShieldLeft = LazyKt__LazyJVMKt.lazy(new Function0<RCheckBox>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$cbShieldLeft$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RCheckBox invoke() {
                    return (RCheckBox) this.this$0.findViewById(R.id.cb_shield_left);
                }
            });
            this.cbShieldTitle = LazyKt__LazyJVMKt.lazy(new Function0<RCheckBox>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$cbShieldTitle$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RCheckBox invoke() {
                    return (RCheckBox) this.this$0.findViewById(R.id.cb_shield_title);
                }
            });
            this.cbShieldSubTitle = LazyKt__LazyJVMKt.lazy(new Function0<RCheckBox>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$cbShieldSubTitle$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RCheckBox invoke() {
                    return (RCheckBox) this.this$0.findViewById(R.id.tv_shield_sub_title);
                }
            });
            this.cbShieldRight = LazyKt__LazyJVMKt.lazy(new Function0<RCheckBox>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$cbShieldRight$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RCheckBox invoke() {
                    return (RCheckBox) this.this$0.findViewById(R.id.cb_shield_right);
                }
            });
            this.llOpen = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$llOpen$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final LinearLayout invoke() {
                    return (LinearLayout) this.this$0.findViewById(R.id.ll_open);
                }
            });
            this.llPrivate = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$llPrivate$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final LinearLayout invoke() {
                    return (LinearLayout) this.this$0.findViewById(R.id.ll_private);
                }
            });
            this.llAttention = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$llAttention$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final LinearLayout invoke() {
                    return (LinearLayout) this.this$0.findViewById(R.id.ll_attention);
                }
            });
            this.llShield = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.NotePermissionDialog$Builder$llShield$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final LinearLayout invoke() {
                    return (LinearLayout) this.this$0.findViewById(R.id.ll_shield);
                }
            });
            setContentView(R.layout.dialog_note_permission);
            setCancelable(false);
            setCanceledOnTouchOutside(false);
            setAnimStyle(AnimAction.INSTANCE.getANIM_BOTTOM());
            setGravity(80);
            setOnClickListener(getImgClose(), getLlOpen(), getLlPrivate(), getLlAttention(), getLlShield());
        }

        private final RCheckBox getCbAttentionLeft() {
            return (RCheckBox) this.cbAttentionLeft.getValue();
        }

        private final RCheckBox getCbAttentionRight() {
            return (RCheckBox) this.cbAttentionRight.getValue();
        }

        private final RCheckBox getCbAttentionSubTitle() {
            return (RCheckBox) this.cbAttentionSubTitle.getValue();
        }

        private final RCheckBox getCbAttentionTitle() {
            return (RCheckBox) this.cbAttentionTitle.getValue();
        }

        private final RCheckBox getCbOpenLeft() {
            return (RCheckBox) this.cbOpenLeft.getValue();
        }

        private final RCheckBox getCbOpenRight() {
            return (RCheckBox) this.cbOpenRight.getValue();
        }

        private final RCheckBox getCbOpenSubTitle() {
            return (RCheckBox) this.cbOpenSubTitle.getValue();
        }

        private final RCheckBox getCbOpenTitle() {
            return (RCheckBox) this.cbOpenTitle.getValue();
        }

        private final RCheckBox getCbPrivateLeft() {
            return (RCheckBox) this.cbPrivateLeft.getValue();
        }

        private final RCheckBox getCbPrivateRight() {
            return (RCheckBox) this.cbPrivateRight.getValue();
        }

        private final RCheckBox getCbPrivateSubTitle() {
            return (RCheckBox) this.cbPrivateSubTitle.getValue();
        }

        private final RCheckBox getCbPrivateTitle() {
            return (RCheckBox) this.cbPrivateTitle.getValue();
        }

        private final RCheckBox getCbShieldLeft() {
            return (RCheckBox) this.cbShieldLeft.getValue();
        }

        private final RCheckBox getCbShieldRight() {
            return (RCheckBox) this.cbShieldRight.getValue();
        }

        private final RCheckBox getCbShieldSubTitle() {
            return (RCheckBox) this.cbShieldSubTitle.getValue();
        }

        private final RCheckBox getCbShieldTitle() {
            return (RCheckBox) this.cbShieldTitle.getValue();
        }

        private final ImageView getImgClose() {
            return (ImageView) this.imgClose.getValue();
        }

        private final LinearLayout getLlAttention() {
            return (LinearLayout) this.llAttention.getValue();
        }

        private final LinearLayout getLlOpen() {
            return (LinearLayout) this.llOpen.getValue();
        }

        private final LinearLayout getLlPrivate() {
            return (LinearLayout) this.llPrivate.getValue();
        }

        private final LinearLayout getLlShield() {
            return (LinearLayout) this.llShield.getValue();
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.Builder, com.ykb.ebook.common_interface.ClickAction, android.view.View.OnClickListener
        public void onClick(@NotNull View view) {
            Function2<? super Integer, ? super String, Unit> function2;
            Intrinsics.checkNotNullParameter(view, "view");
            dismiss();
            if (Intrinsics.areEqual(view, getLlOpen())) {
                Function2<? super Integer, ? super String, Unit> function22 = this.onPermissionCallback;
                if (function22 != null) {
                    function22.invoke(1, "公开");
                    return;
                }
                return;
            }
            if (Intrinsics.areEqual(view, getLlPrivate())) {
                Function2<? super Integer, ? super String, Unit> function23 = this.onPermissionCallback;
                if (function23 != null) {
                    function23.invoke(3, "私密");
                    return;
                }
                return;
            }
            if (Intrinsics.areEqual(view, getLlAttention())) {
                Function2<? super Integer, ? super String, Unit> function24 = this.onPermissionCallback;
                if (function24 != null) {
                    function24.invoke(2, "关注");
                    return;
                }
                return;
            }
            if (!Intrinsics.areEqual(view, getLlShield()) || (function2 = this.onPermissionCallback) == null) {
                return;
            }
            function2.invoke(4, "屏蔽好友");
        }

        @NotNull
        public final Builder setPermissionCallback(@NotNull Function2<? super Integer, ? super String, Unit> callback) {
            Intrinsics.checkNotNullParameter(callback, "callback");
            this.onPermissionCallback = callback;
            return this;
        }

        @NotNull
        public final Builder setSelectPermission(int permission) {
            if (permission == 1) {
                RCheckBox cbOpenLeft = getCbOpenLeft();
                if (cbOpenLeft != null) {
                    cbOpenLeft.setChecked(true);
                }
                RCheckBox cbOpenTitle = getCbOpenTitle();
                if (cbOpenTitle != null) {
                    cbOpenTitle.setChecked(true);
                }
                RCheckBox cbOpenSubTitle = getCbOpenSubTitle();
                if (cbOpenSubTitle != null) {
                    cbOpenSubTitle.setChecked(true);
                }
                RCheckBox cbOpenRight = getCbOpenRight();
                if (cbOpenRight != null) {
                    cbOpenRight.setChecked(true);
                }
            } else if (permission == 2) {
                RCheckBox cbAttentionLeft = getCbAttentionLeft();
                if (cbAttentionLeft != null) {
                    cbAttentionLeft.setChecked(true);
                }
                RCheckBox cbAttentionTitle = getCbAttentionTitle();
                if (cbAttentionTitle != null) {
                    cbAttentionTitle.setChecked(true);
                }
                RCheckBox cbAttentionSubTitle = getCbAttentionSubTitle();
                if (cbAttentionSubTitle != null) {
                    cbAttentionSubTitle.setChecked(true);
                }
                RCheckBox cbAttentionRight = getCbAttentionRight();
                if (cbAttentionRight != null) {
                    cbAttentionRight.setChecked(true);
                }
            } else if (permission == 3) {
                RCheckBox cbPrivateLeft = getCbPrivateLeft();
                if (cbPrivateLeft != null) {
                    cbPrivateLeft.setChecked(true);
                }
                RCheckBox cbPrivateTitle = getCbPrivateTitle();
                if (cbPrivateTitle != null) {
                    cbPrivateTitle.setChecked(true);
                }
                RCheckBox cbPrivateSubTitle = getCbPrivateSubTitle();
                if (cbPrivateSubTitle != null) {
                    cbPrivateSubTitle.setChecked(true);
                }
                RCheckBox cbPrivateRight = getCbPrivateRight();
                if (cbPrivateRight != null) {
                    cbPrivateRight.setChecked(true);
                }
            } else if (permission == 4) {
                RCheckBox cbShieldLeft = getCbShieldLeft();
                if (cbShieldLeft != null) {
                    cbShieldLeft.setChecked(true);
                }
                RCheckBox cbShieldTitle = getCbShieldTitle();
                if (cbShieldTitle != null) {
                    cbShieldTitle.setChecked(true);
                }
                RCheckBox cbShieldSubTitle = getCbShieldSubTitle();
                if (cbShieldSubTitle != null) {
                    cbShieldSubTitle.setChecked(true);
                }
                RCheckBox cbShieldRight = getCbShieldRight();
                if (cbShieldRight != null) {
                    cbShieldRight.setChecked(true);
                }
            }
            return this;
        }
    }
}

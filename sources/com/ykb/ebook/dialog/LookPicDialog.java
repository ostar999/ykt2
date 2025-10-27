package com.ykb.ebook.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.ykb.ebook.R;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.dialog.LookPicDialog;
import com.ykb.ebook.util.ImageLoader;
import com.ykb.ebook.util.ScreenUtil;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/LookPicDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class LookPicDialog {

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u001d\u0010\u0007\u001a\u0004\u0018\u00010\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lcom/ykb/ebook/dialog/LookPicDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "src", "", "(Landroid/content/Context;Ljava/lang/String;)V", "imgPic", "Landroid/widget/ImageView;", "getImgPic", "()Landroid/widget/ImageView;", "imgPic$delegate", "Lkotlin/Lazy;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder extends BasicDialog.Builder<Builder> {

        /* renamed from: imgPic$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgPic;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull Context context, @NotNull String src) {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(src, "src");
            this.imgPic = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.LookPicDialog$Builder$imgPic$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ImageView invoke() {
                    return (ImageView) this.this$0.findViewById(R.id.img_pic);
                }
            });
            setContentView(R.layout.dialog_look_pic);
            setAnimStyle(0);
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            ImageView imgPic = getImgPic();
            if (imgPic != null) {
                imgPic.setMinimumHeight(ScreenUtil.getScreenHeight((Activity) context) / 2);
            }
            ImageView imgPic2 = getImgPic();
            if (imgPic2 != null) {
                ImageLoader.INSTANCE.load(context, src).into(imgPic2);
            }
            ImageView imgPic3 = getImgPic();
            if (imgPic3 != null) {
                imgPic3.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.f0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        LookPicDialog.Builder._init_$lambda$1(this.f26329c, view);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$1(Builder this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dismiss();
        }

        private final ImageView getImgPic() {
            return (ImageView) this.imgPic.getValue();
        }
    }
}

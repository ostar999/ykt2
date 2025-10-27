package com.psychiatrygarden.adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0014¨\u0006\b"}, d2 = {"com/psychiatrygarden/adapter/CourseCommentAdapter$convert$adapter$1", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nCourseCommentAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CourseCommentAdapter.kt\ncom/psychiatrygarden/adapter/CourseCommentAdapter$convert$adapter$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,192:1\n1855#2,2:193\n*S KotlinDebug\n*F\n+ 1 CourseCommentAdapter.kt\ncom/psychiatrygarden/adapter/CourseCommentAdapter$convert$adapter$1\n*L\n166#1:193,2\n*E\n"})
/* loaded from: classes5.dex */
public final class CourseCommentAdapter$convert$adapter$1 extends BaseQuickAdapter<String, BaseViewHolder> {
    final /* synthetic */ int $itemWidth;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CourseCommentAdapter$convert$adapter$1(int i2) {
        super(R.layout.item_img, null, 2, null);
        this.$itemWidth = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$3(CourseCommentAdapter$convert$adapter$1 this$0, final BaseViewHolder holder, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(holder, "$holder");
        ImageViewerPopupViewCustom imageViewerPopupViewCustom = new ImageViewerPopupViewCustom(this$0.getContext());
        Intrinsics.checkNotNull(view, "null cannot be cast to non-null type com.makeramen.roundedimageview.RoundedImageView");
        ImageViewerPopupViewCustom srcView = imageViewerPopupViewCustom.setSrcView((RoundedImageView) view, holder.getLayoutPosition());
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = this$0.getData().iterator();
        while (it.hasNext()) {
            arrayList.add((String) it.next());
        }
        ImageViewerPopupViewCustom longPressListener = srcView.setImageUrls(arrayList).isInfinite(false).isShowPlaceholder(true).setPlaceholderColor(-1).setPlaceholderStrokeColor(-1).setPlaceholderRadius(-1).isShowSaveButton(true).setBgColor(Color.rgb(32, 36, 46)).setSrcViewUpdateListener(new OnSrcViewUpdateListener() { // from class: com.psychiatrygarden.adapter.o3
            @Override // com.lxj.xpopup.interfaces.OnSrcViewUpdateListener
            public final void onSrcViewUpdate(ImageViewerPopupView imageViewerPopupView, int i2) {
                CourseCommentAdapter$convert$adapter$1.convert$lambda$3$lambda$2(holder, imageViewerPopupView, i2);
            }
        }).setXPopupImageLoader(new ImageLoaderUtilsCustom()).setLongPressListener(null);
        longPressListener.popupInfo = new PopupInfo();
        longPressListener.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$3$lambda$2(BaseViewHolder holder, ImageViewerPopupView popupView, int i2) {
        Intrinsics.checkNotNullParameter(holder, "$holder");
        Intrinsics.checkNotNullParameter(popupView, "popupView");
        ViewParent parent = holder.itemView.getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView");
        View viewFindViewById = ((RecyclerView) parent).getChildAt(i2).findViewById(R.id.iv_img);
        Intrinsics.checkNotNull(viewFindViewById, "null cannot be cast to non-null type com.makeramen.roundedimageview.RoundedImageView");
        popupView.updateSrcView((RoundedImageView) viewFindViewById);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull final BaseViewHolder holder, @NotNull String item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        View view = holder.getView(R.id.iv_img);
        Intrinsics.checkNotNull(view, "null cannot be cast to non-null type com.makeramen.roundedimageview.RoundedImageView");
        final RoundedImageView roundedImageView = (RoundedImageView) view;
        View view2 = holder.getView(R.id.tv_long_pic_tag);
        Intrinsics.checkNotNull(view2, "null cannot be cast to non-null type android.widget.TextView");
        final TextView textView = (TextView) view2;
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView.LayoutParams");
        RecyclerView.LayoutParams layoutParams2 = (RecyclerView.LayoutParams) layoutParams;
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = this.$itemWidth;
        if (ScreenUtil.isTablet(getContext())) {
            ((ViewGroup.MarginLayoutParams) layoutParams2).height = getContext().getResources().getDimensionPixelSize(R.dimen.dp_93) * 2;
        } else {
            ((ViewGroup.MarginLayoutParams) layoutParams2).height = getContext().getResources().getDimensionPixelSize(R.dimen.dp_93);
        }
        ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin = (holder.getLayoutPosition() + 1) % 3 == 0 ? 0 : SizeUtil.dp2px(getContext(), 13);
        holder.itemView.setLayoutParams(layoutParams2);
        Glide.with(getContext()).asBitmap().error(R.drawable.imgplacehodel_image).placeholder(R.drawable.imgplacehodel_image).load(item).into((RequestBuilder) new CustomTarget<Bitmap>() { // from class: com.psychiatrygarden.adapter.CourseCommentAdapter$convert$adapter$1$convert$1
            @Override // com.bumptech.glide.request.target.Target
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }

            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(Object obj, Transition transition) {
                onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
            }

            public void onResourceReady(@NotNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Intrinsics.checkNotNullParameter(resource, "resource");
                roundedImageView.setImageBitmap(resource);
                int width = resource.getWidth();
                float f2 = (r3 / width) * 1.0f;
                if (resource.getHeight() <= SizeUtil.dp2px(this.getContext(), 93) || f2 < 3.0f) {
                    ViewExtensionsKt.gone(textView);
                } else {
                    ViewExtensionsKt.visible(textView);
                }
            }
        });
        roundedImageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.n3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                CourseCommentAdapter$convert$adapter$1.convert$lambda$3(this.f14786c, holder, view3);
            }
        });
    }
}

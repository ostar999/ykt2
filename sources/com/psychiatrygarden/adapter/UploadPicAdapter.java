package com.psychiatrygarden.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.PopupInfo;
import com.psychiatrygarden.bean.SelectPicItem;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class UploadPicAdapter extends BaseMultiItemQuickAdapter<SelectPicItem, BaseViewHolder> {
    public UploadPicAdapter(List<SelectPicItem> list) {
        super(list);
        addItemType(1, R.layout.item_select_pic_add);
        addItemType(2, R.layout.item_select_pic);
        addChildClickViewIds(R.id.iv_delete);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$convert$0(ImageView imageView, SelectPicItem selectPicItem, View view) {
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(view.getContext()).setSingleSrcView(imageView, selectPicItem.getLocalPath()).setXPopupImageLoader(new ImageLoaderUtilsCustom(true, R.drawable.imgplacehodel_image));
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.show();
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, final SelectPicItem item) {
        if (item.getItemType() != 2) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = getData().size() == 1 ? 0 : CommonUtil.dip2px(getContext(), 8.0f);
            holder.itemView.setLayoutParams(layoutParams);
        } else {
            final ImageView imageView = (ImageView) holder.getView(R.id.iv_pic);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.uf
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UploadPicAdapter.lambda$convert$0(imageView, item, view);
                }
            });
            Glide.with(getContext()).load(item.getLocalPath()).transform(new RoundedCorners(CommonUtil.dip2px(getContext(), 8.0f))).into(imageView);
            holder.setGone(R.id.processView, !item.isUploading()).setGone(R.id.iv_delete, item.isUploading());
        }
    }
}

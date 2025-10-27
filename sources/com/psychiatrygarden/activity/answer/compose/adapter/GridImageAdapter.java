package com.psychiatrygarden.activity.answer.compose.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.PopupInfo;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes5.dex */
public class GridImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private onAddPicClickListener mOnAddPicClickListener;
    private int selectMax;

    public interface onAddPicClickListener {
        void deleteData(int position);

        void onAddPicClick();
    }

    public GridImageAdapter(List<String> list, int selectMax, onAddPicClickListener mOnAddPicClickListener) {
        super(R.layout.gv_filter_image, list);
        this.selectMax = selectMax;
        this.mOnAddPicClickListener = mOnAddPicClickListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(View view) {
        this.mOnAddPicClickListener.onAddPicClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$1(BaseViewHolder baseViewHolder, View view) {
        int bindingAdapterPosition = baseViewHolder.getBindingAdapterPosition();
        if (bindingAdapterPosition == -1 || getData().size() <= bindingAdapterPosition) {
            return;
        }
        getData().remove(bindingAdapterPosition);
        notifyItemRemoved(bindingAdapterPosition);
        notifyItemRangeChanged(bindingAdapterPosition, getData().size());
        this.mOnAddPicClickListener.deleteData(bindingAdapterPosition);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$2(ImageView imageView, BaseViewHolder baseViewHolder, View view) {
        ImageViewerPopupViewCustom longPressListener = new ImageViewerPopupViewCustom(getContext()).setSrcView(imageView, baseViewHolder.getBindingAdapterPosition()).setImageUrls(new ArrayList(getData())).isInfinite(false).isShowPlaceholder(true).setPlaceholderColor(-1).setPlaceholderStrokeColor(-1).setPlaceholderRadius(-1).isShowSaveButton(true).setBgColor(Color.rgb(32, 36, 46)).setSrcViewUpdateListener(null).setXPopupImageLoader(new ImageLoaderUtilsCustom()).setLongPressListener(null);
        longPressListener.popupInfo = new PopupInfo();
        longPressListener.show();
    }

    public void delete(int position) {
        if (position != -1) {
            try {
                if (getData().size() > position) {
                    getData().remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getData().size());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public boolean haveData(List<String> list) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (this.selectMax == 1 || "none".equals(list.get(i2))) {
                return true;
            }
        }
        return false;
    }

    public void setList(List<String> list) {
        try {
            if (this.selectMax < list.size()) {
                int i2 = 0;
                while (true) {
                    if (i2 < list.size()) {
                        if ("none".equals(list.get(i2))) {
                            list.remove(i2);
                            break;
                        }
                        i2++;
                    }
                }
            } else if (!haveData(list)) {
                list.add("none");
            }
            break;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        super.setList((Collection) list);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull final BaseViewHolder helper, String item) {
        final ImageView imageView = (ImageView) helper.getView(R.id.fiv);
        ImageView imageView2 = (ImageView) helper.getView(R.id.iv_del);
        if ("none".equals(item)) {
            if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                imageView.setImageResource(R.drawable.addtoimg2);
            } else {
                imageView.setImageResource(R.drawable.addtoimg2_night);
            }
            imageView2.setVisibility(4);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: k1.a
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f27604c.lambda$convert$0(view);
                }
            });
            return;
        }
        if (this.mOnAddPicClickListener == null) {
            imageView2.setVisibility(8);
        } else {
            imageView2.setVisibility(0);
        }
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: k1.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27605c.lambda$convert$1(helper, view);
            }
        });
        String str = getData().get(helper.getBindingAdapterPosition());
        if (TextUtils.isEmpty(str)) {
            return;
        }
        GlideApp.with(helper.itemView.getContext()).load((Object) GlideUtils.generateUrl(str)).centerCrop().placeholder(R.drawable.imgplacehodel_image).error(R.drawable.imgplacehodel_image).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        helper.itemView.setOnClickListener(new View.OnClickListener() { // from class: k1.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27607c.lambda$convert$2(imageView, helper, view);
            }
        });
    }
}

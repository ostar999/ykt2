package com.psychiatrygarden.forum;

import android.app.Activity;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class NinePicAdp extends BaseQuickAdapter<String, BaseViewHolder> {
    private boolean isCircleInfo;
    private ArrayMap<Integer, Boolean> longPicMap;
    private Activity mActivity;
    private OnPicItemClickListener onItemPicClickLisenter;
    private boolean showLongPicTag;
    private int spanCount;

    public interface OnPicItemClickListener {
        void addPicClick(int pos, String url);

        void delPicClick(int pos, String url);
    }

    public NinePicAdp(Activity activity, boolean isCircleInfo) {
        super(R.layout.item_nine_pic_view);
        this.spanCount = 3;
        this.longPicMap = new ArrayMap<>();
        this.isCircleInfo = isCircleInfo;
        this.mActivity = activity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(BaseViewHolder baseViewHolder, String str, View view) {
        if (this.onItemPicClickLisenter == null || baseViewHolder.getLayoutPosition() != getData().size() - 1) {
            return;
        }
        this.onItemPicClickLisenter.addPicClick(baseViewHolder.getLayoutPosition(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$1(BaseViewHolder baseViewHolder, String str, View view) {
        OnPicItemClickListener onPicItemClickListener = this.onItemPicClickLisenter;
        if (onPicItemClickListener != null) {
            onPicItemClickListener.delPicClick(baseViewHolder.getLayoutPosition(), str);
        }
    }

    public boolean getIsLongPicByPosition(int position) {
        Boolean bool = this.longPicMap.get(Integer.valueOf(position));
        return bool != null && bool == Boolean.TRUE;
    }

    public void setIsLongPic(int position, boolean isLongPic) {
        this.longPicMap.put(Integer.valueOf(position), Boolean.valueOf(isLongPic));
    }

    public void setOnPicItemClickListener(OnPicItemClickListener lisenter) {
        this.onItemPicClickLisenter = lisenter;
    }

    public void setShowLongPicTag(boolean showLongPicTag) {
        this.showLongPicTag = showLongPicTag;
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(final BaseViewHolder helper, final String item) {
        int screenWidth;
        int pxByDp;
        RelativeLayout relativeLayout = (RelativeLayout) helper.getView(R.id.ly_item);
        TextView textView = (TextView) helper.getView(R.id.tv_long_pic);
        RoundedImageView roundedImageView = (RoundedImageView) helper.getView(R.id.img_pic);
        ImageView imageView = (ImageView) helper.getView(R.id.btn_del);
        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) relativeLayout.getLayoutParams();
        if (this.isCircleInfo) {
            layoutParams.setMarginEnd(ScreenUtil.getPxByDp(getContext(), 12));
            screenWidth = ScreenUtil.getScreenWidth(this.mActivity);
            pxByDp = ScreenUtil.getPxByDp(getContext(), ((this.spanCount - 1) * 12) + 32);
        } else {
            screenWidth = ScreenUtil.getScreenWidth(this.mActivity);
            pxByDp = ScreenUtil.getPxByDp(getContext(), ((this.spanCount - 1) * 13) + 32);
        }
        int i2 = screenWidth - pxByDp;
        int i3 = this.spanCount;
        ((ViewGroup.MarginLayoutParams) layoutParams).width = i2 / i3;
        ((ViewGroup.MarginLayoutParams) layoutParams).height = i2 / i3;
        relativeLayout.setLayoutParams(layoutParams);
        if (TextUtils.isEmpty(item)) {
            imageView.setVisibility(8);
            if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                roundedImageView.setImageResource(R.mipmap.ic_add_pic_day);
            } else {
                roundedImageView.setImageResource(R.mipmap.ic_add_pic_night);
            }
            roundedImageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.j0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15364c.lambda$convert$0(helper, item, view);
                }
            });
        } else {
            imageView.setVisibility(this.isCircleInfo ? 8 : 0);
            GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(item)).placeholder(R.mipmap.ic_order_default).into(roundedImageView);
            Boolean bool = this.longPicMap.get(Integer.valueOf(helper.getLayoutPosition()));
            if (bool == null || bool != Boolean.TRUE) {
                textView.setVisibility(8);
            } else {
                textView.setVisibility(0);
            }
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.k0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15369c.lambda$convert$1(helper, item, view);
            }
        });
    }
}

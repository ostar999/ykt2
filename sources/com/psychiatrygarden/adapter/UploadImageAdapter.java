package com.psychiatrygarden.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class UploadImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private String user_identity;

    public UploadImageAdapter(@Nullable List<String> data) {
        super(R.layout.item_upload_image, data);
    }

    public void setUserIdentity(String user_identity) {
        this.user_identity = user_identity;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, String item) {
        ImageView imageView = (ImageView) helper.getView(R.id.iv_group_chat_member);
        ImageView imageView2 = (ImageView) helper.getView(R.id.iv_group_delete);
        int screenWidth = (ScreenUtil.getScreenWidth((Activity) getContext()) - ScreenUtil.getPxByDp(getContext(), 80)) / 3;
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(screenWidth, screenWidth));
        if (TextUtils.isEmpty(item)) {
            imageView.setImageResource(R.mipmap.analysis_add_img);
            imageView2.setVisibility(8);
        } else {
            if (this.user_identity.equals("0")) {
                imageView2.setVisibility(8);
            } else {
                imageView2.setVisibility(0);
            }
            Glide.with(getContext()).load((Object) GlideUtils.generateUrl(item)).into(imageView);
        }
    }
}

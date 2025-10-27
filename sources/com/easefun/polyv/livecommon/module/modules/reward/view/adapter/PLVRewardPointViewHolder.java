package com.easefun.polyv.livecommon.module.modules.reward.view.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.module.modules.reward.view.vo.PLVRewardItemVO;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVImageLoader;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livecommon.ui.widget.itemview.holder.PLVBaseViewHolder;

/* loaded from: classes3.dex */
public class PLVRewardPointViewHolder extends PLVBaseViewHolder<PLVBaseViewData, PLVRewardListAdapter> {
    private PLVRewardItemVO goodsBean;
    private ImageView plvIvRewardImage;
    private TextView plvTvRewardName;
    private TextView plvTvRewardPoint;

    public PLVRewardPointViewHolder(View itemView, PLVRewardListAdapter adapter) {
        super(itemView, adapter);
        this.plvIvRewardImage = (ImageView) itemView.findViewById(R.id.plv_iv_reward_image);
        this.plvTvRewardName = (TextView) itemView.findViewById(R.id.plv_tv_reward_name);
        this.plvTvRewardPoint = (TextView) itemView.findViewById(R.id.plv_tv_reward_point);
    }

    private void loadImage(String url, ImageView iv) {
        if (!url.startsWith("http")) {
            url = "https:/" + url;
        }
        PLVImageLoader.getInstance().loadImage(this.itemView.getContext(), url, iv);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.itemview.holder.PLVBaseViewHolder
    public void processData(PLVBaseViewData data, int position) {
        super.processData(data, position);
        PLVRewardItemVO pLVRewardItemVO = (PLVRewardItemVO) data.getData();
        this.goodsBean = pLVRewardItemVO;
        if (pLVRewardItemVO == null) {
            return;
        }
        loadImage(pLVRewardItemVO.getImg(), this.plvIvRewardImage);
        this.plvTvRewardName.setText(this.goodsBean.getName());
        if (data.getItemType() != 1) {
            if (data.getItemType() == 2) {
                this.plvTvRewardPoint.setText("免费");
            }
        } else {
            this.plvTvRewardPoint.setText(this.goodsBean.getPrice() + this.goodsBean.getUnit());
        }
    }
}

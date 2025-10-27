package com.easefun.polyv.livecloudclass.modules.pagemenu.previous.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVImageLoader;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livecommon.ui.widget.itemview.holder.PLVBaseViewHolder;
import com.plv.livescenes.model.PLVPlaybackListVO;

/* loaded from: classes3.dex */
public class PLVLCPreviousViewHolder extends PLVBaseViewHolder<PLVBaseViewData, PLVLCPreviousAdapter> {
    private PLVPlaybackListVO.DataBean.ContentsBean contentBean;
    private Context context;
    private TextView durationTv;
    private View itemView;
    private TextView maskTv;
    private ImageView previousCoverIv;
    private TextView startTimeTv;
    private TextView titleTv;

    public PLVLCPreviousViewHolder(View view, PLVLCPreviousAdapter pLVLCPreviousAdapter) {
        super(view, pLVLCPreviousAdapter);
        this.previousCoverIv = (ImageView) findViewById(R.id.plvlc_previous_item_cover_Im);
        this.titleTv = (TextView) findViewById(R.id.plvlc_previous_item_title_tv);
        this.startTimeTv = (TextView) findViewById(R.id.plvlc_previous_item_startTime_tv);
        this.durationTv = (TextView) findViewById(R.id.plvlc_previous_item_duration_tv);
        this.maskTv = (TextView) findViewById(R.id.plvlc_previous_item_mask);
        this.context = view.getContext();
        this.itemView = view;
    }

    private String formatDateUtil(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        if (str.length() < 12) {
            return str;
        }
        stringBuffer.append((CharSequence) str, 0, 4);
        stringBuffer.append("-");
        stringBuffer.append((CharSequence) str, 4, 6);
        stringBuffer.append("-");
        stringBuffer.append((CharSequence) str, 6, 8);
        stringBuffer.append(" ");
        stringBuffer.append((CharSequence) str, 8, 10);
        stringBuffer.append(":");
        stringBuffer.append((CharSequence) str, 10, 12);
        return stringBuffer.toString();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.itemview.holder.PLVBaseViewHolder
    public void processData(PLVBaseViewData pLVBaseViewData, final int i2) {
        super.processData(pLVBaseViewData, i2);
        this.contentBean = (PLVPlaybackListVO.DataBean.ContentsBean) pLVBaseViewData.getData();
        PLVImageLoader.getInstance().loadImage(this.contentBean.getFirstImage(), this.previousCoverIv);
        this.titleTv.setText(this.contentBean.getTitle());
        this.durationTv.setText(this.contentBean.getDuration());
        this.startTimeTv.setText(formatDateUtil(this.contentBean.getStartTime()));
        if (((PLVLCPreviousAdapter) this.adapter).getCurrentPosition() == i2) {
            this.maskTv.setVisibility(0);
            TextView textView = this.titleTv;
            Resources resources = this.context.getResources();
            int i3 = R.color.colorPortage;
            textView.setTextColor(resources.getColor(i3));
            this.startTimeTv.setTextColor(this.context.getResources().getColor(i3));
        } else {
            this.maskTv.setVisibility(8);
            this.titleTv.setTextColor(this.context.getResources().getColor(R.color.colorSpunPearl));
            this.startTimeTv.setTextColor(this.context.getResources().getColor(R.color.colorSoftSpunPearl));
        }
        this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.previous.adapter.PLVLCPreviousViewHolder.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((PLVLCPreviousAdapter) ((PLVBaseViewHolder) PLVLCPreviousViewHolder.this).adapter).callChangeVideoVidClick(view, PLVLCPreviousViewHolder.this.contentBean, i2);
            }
        });
    }
}

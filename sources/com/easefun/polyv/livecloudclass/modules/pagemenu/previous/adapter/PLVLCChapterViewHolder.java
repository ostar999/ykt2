package com.easefun.polyv.livecloudclass.modules.pagemenu.previous.adapter;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livecommon.ui.widget.itemview.holder.PLVBaseViewHolder;
import com.google.android.material.timepicker.TimeModel;
import com.plv.livescenes.previous.model.PLVChapterDataVO;

/* loaded from: classes3.dex */
public class PLVLCChapterViewHolder extends PLVBaseViewHolder<PLVBaseViewData, PLVLCChapterAdapter> {
    private PLVChapterDataVO chapterDataVO;
    private ImageView chapterStatusIm;
    private TextView timeTv;
    private TextView titleTv;

    public PLVLCChapterViewHolder(View view, PLVLCChapterAdapter pLVLCChapterAdapter) {
        super(view, pLVLCChapterAdapter);
        this.chapterStatusIm = (ImageView) findViewById(R.id.plvlc_chapter_item_status_im);
        this.titleTv = (TextView) findViewById(R.id.plvlc_chapter_item_title_tv);
        this.timeTv = (TextView) findViewById(R.id.plvlc_chapter_item_time_tv);
    }

    private String timeFormat(String str) throws NumberFormatException {
        int i2 = Integer.parseInt(str);
        String str2 = String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i2 % 60));
        String str3 = String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf((i2 / 60) % 60));
        String str4 = String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i2 / 3600));
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str4);
        stringBuffer.append(":");
        stringBuffer.append(str3);
        stringBuffer.append(":");
        stringBuffer.append(str2);
        return stringBuffer.toString();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.itemview.holder.PLVBaseViewHolder
    public void processData(PLVBaseViewData pLVBaseViewData, final int i2) {
        super.processData(pLVBaseViewData, i2);
        this.chapterDataVO = (PLVChapterDataVO) pLVBaseViewData.getData();
        if (((PLVLCChapterAdapter) this.adapter).getCurrentPosition() == i2) {
            this.chapterStatusIm.setImageResource(R.drawable.plvlc_chapter_playing_icon);
            TextView textView = this.timeTv;
            Resources resources = this.itemView.getResources();
            int i3 = R.color.colorMalibu;
            textView.setTextColor(resources.getColor(i3));
            this.titleTv.setTextColor(this.itemView.getResources().getColor(i3));
        } else {
            this.chapterStatusIm.setImageResource(R.drawable.plvlc_chapter_play_icon);
            TextView textView2 = this.timeTv;
            Resources resources2 = this.itemView.getResources();
            int i4 = R.color.colorSpunPearl;
            textView2.setTextColor(resources2.getColor(i4));
            this.titleTv.setTextColor(this.itemView.getResources().getColor(i4));
        }
        this.timeTv.setText(timeFormat(String.valueOf(this.chapterDataVO.getTimeStamp())));
        this.titleTv.setText(this.chapterDataVO.getTitle());
        this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.previous.adapter.PLVLCChapterViewHolder.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((PLVLCChapterAdapter) ((PLVBaseViewHolder) PLVLCChapterViewHolder.this).adapter).callChangeVideoSeekClick(PLVLCChapterViewHolder.this.chapterDataVO.getTimeStamp(), i2);
            }
        });
    }
}

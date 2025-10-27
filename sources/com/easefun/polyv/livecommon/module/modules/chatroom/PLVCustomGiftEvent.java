package com.easefun.polyv.livecommon.module.modules.chatroom;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import com.easefun.polyv.livecommon.R;
import com.plv.socket.event.PLVBaseEvent;
import com.plv.socket.event.chat.PLVRewardEvent;

/* loaded from: classes3.dex */
public class PLVCustomGiftEvent extends PLVBaseEvent {
    private int giftDrawableId;
    private String giftImg;
    private SpannableStringBuilder span;
    private Long time;

    public PLVCustomGiftEvent(SpannableStringBuilder span) {
        this.span = span;
    }

    public static PLVCustomGiftEvent generateCustomGiftEvent(PLVRewardEvent rewardEvent) throws NumberFormatException {
        String unick = rewardEvent.getContent().getUnick();
        String gimg = rewardEvent.getContent().getGimg();
        boolean z2 = !TextUtils.isEmpty(gimg);
        String rewardContent = rewardEvent.getContent().getRewardContent();
        if (!z2) {
            try {
                Double.parseDouble(rewardContent);
                rewardContent = rewardContent + "元";
            } catch (Exception unused) {
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(unick);
        sb.append(z2 ? " 赠送了 " : " 打赏 ");
        sb.append(rewardContent);
        sb.append(" ");
        PLVCustomGiftEvent pLVCustomGiftEvent = new PLVCustomGiftEvent(new SpannableStringBuilder(sb.toString()));
        pLVCustomGiftEvent.setGiftImg(gimg);
        pLVCustomGiftEvent.setTime(Long.valueOf(System.currentTimeMillis()));
        if (!z2) {
            pLVCustomGiftEvent.setGiftDrawableId(R.drawable.plv_icon_money);
        }
        return pLVCustomGiftEvent;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "REWARD";
    }

    public int getGiftDrawableId() {
        return this.giftDrawableId;
    }

    public String getGiftImg() {
        return this.giftImg;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getListenEvent() {
        return "message";
    }

    public SpannableStringBuilder getSpan() {
        return this.span;
    }

    public Long getTime() {
        return this.time;
    }

    public void setGiftDrawableId(int giftDrawableId) {
        this.giftDrawableId = giftDrawableId;
    }

    public void setGiftImg(String giftImg) {
        this.giftImg = giftImg;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}

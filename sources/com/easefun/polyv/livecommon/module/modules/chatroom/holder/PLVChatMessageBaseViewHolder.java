package com.easefun.polyv.livecommon.module.modules.chatroom.holder;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.easefun.polyv.businesssdk.model.chat.PolyvAnswerVO;
import com.easefun.polyv.livecommon.module.modules.chatroom.PLVCustomGiftEvent;
import com.easefun.polyv.livecommon.module.utils.span.PLVFaceManager;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livecommon.ui.widget.itemview.adapter.PLVBaseAdapter;
import com.easefun.polyv.livecommon.ui.widget.itemview.holder.PLVBaseViewHolder;
import com.easefun.polyv.livescenes.chatroom.PolyvLocalMessage;
import com.easefun.polyv.livescenes.chatroom.PolyvQuestionMessage;
import com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendLocalImgEvent;
import com.easefun.polyv.livescenes.socket.PolyvSocketWrapper;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.livescenes.playback.chat.PLVChatPlaybackData;
import com.plv.socket.event.chat.PLVChatEmotionEvent;
import com.plv.socket.event.chat.PLVChatImgContent;
import com.plv.socket.event.chat.PLVChatImgEvent;
import com.plv.socket.event.chat.PLVChatQuoteVO;
import com.plv.socket.event.chat.PLVProhibitedWordVO;
import com.plv.socket.event.chat.PLVSpeakEvent;
import com.plv.socket.event.chat.PLVTAnswerEvent;
import com.plv.socket.event.history.PLVChatImgHistoryEvent;
import com.plv.socket.event.history.PLVFileShareHistoryEvent;
import com.plv.socket.event.history.PLVSpeakHistoryEvent;
import com.plv.socket.event.ppt.PLVPptShareFileVO;
import com.plv.socket.net.model.PLVSocketLoginVO;
import com.plv.socket.user.PLVAuthorizationBean;
import com.plv.socket.user.PLVSocketUserBean;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;

/* loaded from: classes3.dex */
public class PLVChatMessageBaseViewHolder<Data extends PLVBaseViewData, Adapter extends PLVBaseAdapter> extends PLVBaseViewHolder<Data, Adapter> {
    protected String actor;
    protected String avatar;
    protected String bgColor;
    protected int chatImgHeight;
    protected String chatImgUrl;
    protected int chatImgWidth;
    protected PLVChatQuoteVO chatQuoteVO;
    protected String fColor;
    protected int giftDrawableId;
    protected String giftImg;
    protected boolean isLocalChatImg;
    protected String localImgFailMessage;
    protected int localImgProgress;
    protected int localImgStatus;
    protected Object messageData;
    private int msgIndex;
    protected String nickName;
    protected PLVProhibitedWordVO prohibitedWordVO;
    protected CharSequence quoteSpeakMsg;

    @Nullable
    protected PLVPptShareFileVO speakFileData;
    protected CharSequence speakMsg;
    protected String userId;
    protected String userType;

    public PLVChatMessageBaseViewHolder(View itemView, Adapter adapter) {
        super(itemView, adapter);
    }

    private void fillFieldFromLoginVO(PLVSocketLoginVO loginVO) {
        if (loginVO != null) {
            this.userType = loginVO.getUserType();
            this.nickName = loginVO.getNickName();
            this.userId = loginVO.getUserId();
            this.avatar = loginVO.getAvatarUrl();
            this.actor = loginVO.getActor();
            PLVAuthorizationBean authorization = loginVO.getAuthorization();
            if (authorization != null) {
                this.actor = authorization.getActor();
                this.fColor = authorization.getfColor();
                this.bgColor = authorization.getBgColor();
            }
        }
    }

    private void fillFieldFromUser(PLVSocketUserBean userBean) {
        if (userBean != null) {
            this.userType = userBean.getUserType();
            this.nickName = userBean.getNick();
            this.userId = userBean.getUserId();
            this.actor = userBean.getActor();
            this.avatar = userBean.getPic();
            PLVAuthorizationBean authorization = userBean.getAuthorization();
            if (authorization != null) {
                this.actor = authorization.getActor();
                this.fColor = authorization.getfColor();
                this.bgColor = authorization.getBgColor();
            }
        }
    }

    public static void fitChatImgWH(int width, int height, View view, int maxLengthDp, int minLengthDp) {
        int iDp2px = ConvertUtils.dp2px(maxLengthDp);
        int iDp2px2 = ConvertUtils.dp2px(minLengthDp);
        float f2 = (width * 1.0f) / height;
        if (f2 == 1.0f) {
            if (width < iDp2px2) {
                width = iDp2px2;
            } else if (width > iDp2px) {
                width = iDp2px;
            }
            height = width;
        } else if (f2 < 1.0f) {
            width = (int) Math.max(iDp2px2, iDp2px * f2);
            height = iDp2px;
        } else {
            height = (int) Math.max(iDp2px2, iDp2px / f2);
            width = iDp2px;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }

    private void resetParams() {
        this.userType = null;
        this.nickName = null;
        this.speakMsg = null;
        this.userId = null;
        this.actor = null;
        this.fColor = PLVAuthorizationBean.FCOLOR_DEFAULT;
        this.bgColor = PLVAuthorizationBean.BGCOLOR_DEFAULT;
        this.avatar = null;
        this.chatImgUrl = null;
        this.chatImgWidth = 0;
        this.chatImgHeight = 0;
        this.isLocalChatImg = false;
        this.prohibitedWordVO = null;
        this.chatQuoteVO = null;
        this.quoteSpeakMsg = null;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.itemview.holder.PLVBaseViewHolder
    public void processData(Data data, int position) {
        PolyvAnswerVO polyvAnswerVO;
        PLVChatImgContent pLVChatImgContent;
        super.processData(data, position);
        this.messageData = data.getData();
        resetParams();
        Object obj = this.messageData;
        if (obj instanceof PLVSpeakEvent) {
            PLVSpeakEvent pLVSpeakEvent = (PLVSpeakEvent) obj;
            fillFieldFromUser(pLVSpeakEvent.getUser());
            int iMin = Math.min(pLVSpeakEvent.getObjects().length - 1, this.msgIndex);
            this.speakMsg = (CharSequence) pLVSpeakEvent.getObjects()[iMin];
            this.speakFileData = pLVSpeakEvent.getFileData();
            PLVChatQuoteVO quote = pLVSpeakEvent.getQuote();
            this.chatQuoteVO = quote;
            if (quote == null || !quote.isSpeakMessage()) {
                return;
            }
            this.quoteSpeakMsg = (CharSequence) this.chatQuoteVO.getObjects()[iMin];
            return;
        }
        if (obj instanceof PolyvLocalMessage) {
            fillFieldFromLoginVO(PolyvSocketWrapper.getInstance().getLoginVO());
            int iMin2 = Math.min(((PolyvLocalMessage) this.messageData).getObjects().length - 1, this.msgIndex);
            this.speakMsg = (CharSequence) ((PolyvLocalMessage) this.messageData).getObjects()[iMin2];
            this.prohibitedWordVO = ((PolyvLocalMessage) this.messageData).getProhibitedWord();
            PLVChatQuoteVO quote2 = ((PolyvLocalMessage) this.messageData).getQuote();
            this.chatQuoteVO = quote2;
            if (quote2 == null || !quote2.isSpeakMessage()) {
                return;
            }
            this.quoteSpeakMsg = (CharSequence) this.chatQuoteVO.getObjects()[iMin2];
            return;
        }
        if (obj instanceof PLVChatImgEvent) {
            PLVChatImgEvent pLVChatImgEvent = (PLVChatImgEvent) obj;
            fillFieldFromUser(pLVChatImgEvent.getUser());
            if (pLVChatImgEvent.getValues() == null || pLVChatImgEvent.getValues().size() <= 0 || (pLVChatImgContent = pLVChatImgEvent.getValues().get(0)) == null) {
                return;
            }
            this.chatImgUrl = pLVChatImgContent.getUploadImgUrl();
            if (pLVChatImgContent.getSize() != null) {
                this.chatImgWidth = (int) pLVChatImgContent.getSize().getWidth();
                this.chatImgHeight = (int) pLVChatImgContent.getSize().getHeight();
                return;
            }
            return;
        }
        if (obj instanceof PolyvSendLocalImgEvent) {
            this.isLocalChatImg = true;
            PolyvSendLocalImgEvent polyvSendLocalImgEvent = (PolyvSendLocalImgEvent) obj;
            fillFieldFromLoginVO(PolyvSocketWrapper.getInstance().getLoginVO());
            this.chatImgUrl = polyvSendLocalImgEvent.getImageFilePath();
            this.chatImgWidth = polyvSendLocalImgEvent.getWidth();
            this.chatImgHeight = polyvSendLocalImgEvent.getHeight();
            this.localImgStatus = polyvSendLocalImgEvent.getSendStatus();
            this.localImgFailMessage = polyvSendLocalImgEvent.getObj1() + "";
            return;
        }
        if (obj instanceof PolyvQuestionMessage) {
            fillFieldFromLoginVO(PolyvSocketWrapper.getInstance().getLoginVO());
            this.speakMsg = (CharSequence) ((PolyvQuestionMessage) this.messageData).getObjects()[Math.min(((PolyvQuestionMessage) this.messageData).getObjects().length - 1, this.msgIndex)];
            return;
        }
        if (obj instanceof PLVTAnswerEvent) {
            PLVTAnswerEvent pLVTAnswerEvent = (PLVTAnswerEvent) obj;
            fillFieldFromUser(pLVTAnswerEvent.getUser());
            this.speakMsg = (CharSequence) pLVTAnswerEvent.getObjects()[Math.min(pLVTAnswerEvent.getObjects().length - 1, this.msgIndex)];
            if (pLVTAnswerEvent.getMsgType() == null || !pLVTAnswerEvent.getMsgType().equalsIgnoreCase("image") || (polyvAnswerVO = (PolyvAnswerVO) PLVGsonUtil.fromJson(PolyvAnswerVO.class, this.speakMsg.toString())) == null) {
                return;
            }
            this.chatImgUrl = polyvAnswerVO.getUrl();
            this.chatImgWidth = (int) polyvAnswerVO.getWidth();
            this.chatImgHeight = (int) polyvAnswerVO.getHeight();
            pLVTAnswerEvent.setObj1(this.chatImgUrl);
            this.speakMsg = null;
            return;
        }
        if (obj instanceof PLVSpeakHistoryEvent) {
            PLVSpeakHistoryEvent pLVSpeakHistoryEvent = (PLVSpeakHistoryEvent) obj;
            fillFieldFromUser(pLVSpeakHistoryEvent.getUser());
            int iMin3 = Math.min(pLVSpeakHistoryEvent.getObjects().length - 1, this.msgIndex);
            this.speakMsg = (CharSequence) pLVSpeakHistoryEvent.getObjects()[iMin3];
            PLVChatQuoteVO quote3 = pLVSpeakHistoryEvent.getQuote();
            this.chatQuoteVO = quote3;
            if (quote3 == null || !quote3.isSpeakMessage()) {
                return;
            }
            this.quoteSpeakMsg = (CharSequence) this.chatQuoteVO.getObjects()[iMin3];
            return;
        }
        if (obj instanceof PLVFileShareHistoryEvent) {
            PLVFileShareHistoryEvent pLVFileShareHistoryEvent = (PLVFileShareHistoryEvent) obj;
            fillFieldFromUser(pLVFileShareHistoryEvent.getUser());
            int iMin4 = Math.min(pLVFileShareHistoryEvent.getObjects().length - 1, this.msgIndex);
            this.speakMsg = (CharSequence) pLVFileShareHistoryEvent.getObjects()[iMin4];
            this.speakFileData = pLVFileShareHistoryEvent.getFileData();
            PLVChatQuoteVO quote4 = pLVFileShareHistoryEvent.getQuote();
            this.chatQuoteVO = quote4;
            if (quote4 == null || !quote4.isSpeakMessage()) {
                return;
            }
            this.quoteSpeakMsg = (CharSequence) this.chatQuoteVO.getObjects()[iMin4];
            return;
        }
        if (obj instanceof PLVChatImgHistoryEvent) {
            PLVChatImgHistoryEvent pLVChatImgHistoryEvent = (PLVChatImgHistoryEvent) obj;
            fillFieldFromUser(pLVChatImgHistoryEvent.getUser());
            PLVChatImgContent content = pLVChatImgHistoryEvent.getContent();
            if (content != null) {
                this.chatImgUrl = content.getUploadImgUrl();
                if (content.getSize() != null) {
                    this.chatImgWidth = (int) content.getSize().getWidth();
                    this.chatImgHeight = (int) content.getSize().getHeight();
                }
                if ("emotion".equals(content.getType())) {
                    this.chatImgWidth = ConvertUtils.dp2px(80.0f);
                    this.chatImgHeight = ConvertUtils.dp2px(80.0f);
                    return;
                }
                return;
            }
            return;
        }
        if (obj instanceof PLVCustomGiftEvent) {
            this.speakMsg = ((PLVCustomGiftEvent) obj).getSpan();
            this.giftImg = ((PLVCustomGiftEvent) this.messageData).getGiftImg();
            this.giftDrawableId = ((PLVCustomGiftEvent) this.messageData).getGiftDrawableId();
            return;
        }
        if (obj instanceof PLVChatEmotionEvent) {
            PLVChatEmotionEvent pLVChatEmotionEvent = (PLVChatEmotionEvent) obj;
            if (pLVChatEmotionEvent.getUser() == null) {
                fillFieldFromLoginVO(PolyvSocketWrapper.getInstance().getLoginVO());
            } else {
                fillFieldFromUser(pLVChatEmotionEvent.getUser());
            }
            this.chatImgUrl = PLVFaceManager.getInstance().getEmotionUrl(pLVChatEmotionEvent.getId());
            this.chatImgWidth = ConvertUtils.dp2px(80.0f);
            this.chatImgHeight = ConvertUtils.dp2px(80.0f);
            return;
        }
        if (obj instanceof PLVChatPlaybackData) {
            PLVChatPlaybackData pLVChatPlaybackData = (PLVChatPlaybackData) obj;
            this.userType = pLVChatPlaybackData.getUserType();
            this.nickName = pLVChatPlaybackData.getNick();
            this.userId = pLVChatPlaybackData.getUserId();
            this.avatar = pLVChatPlaybackData.getPic();
            this.actor = pLVChatPlaybackData.getActor();
            if (pLVChatPlaybackData.getObjects() != null) {
                int iMin5 = Math.min(pLVChatPlaybackData.getObjects().length - 1, this.msgIndex);
                this.speakMsg = (CharSequence) pLVChatPlaybackData.getObjects()[iMin5];
                PLVChatQuoteVO chatQuoteVO = pLVChatPlaybackData.getChatQuoteVO();
                this.chatQuoteVO = chatQuoteVO;
                if (chatQuoteVO != null && chatQuoteVO.isSpeakMessage() && this.chatQuoteVO.getObjects() != null) {
                    this.quoteSpeakMsg = (CharSequence) this.chatQuoteVO.getObjects()[iMin5];
                }
            }
            PLVChatImgContent chatImgContent = pLVChatPlaybackData.getChatImgContent();
            if (chatImgContent != null) {
                this.chatImgUrl = chatImgContent.getUploadImgUrl();
                if (chatImgContent.getSize() != null) {
                    this.chatImgWidth = (int) chatImgContent.getSize().getWidth();
                    this.chatImgHeight = (int) chatImgContent.getSize().getHeight();
                }
                if ("emotion".equals(chatImgContent.getType())) {
                    this.chatImgWidth = ConvertUtils.dp2px(80.0f);
                    this.chatImgHeight = ConvertUtils.dp2px(80.0f);
                }
            }
        }
    }

    public void setMsgIndex(int msgIndex) {
        this.msgIndex = msgIndex;
    }
}

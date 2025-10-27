package com.easefun.polyv.livecloudclass.modules.chatroom.adapter.holder;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCMessageAdapter;
import com.easefun.polyv.livecommon.module.modules.chatroom.holder.PLVChatMessageBaseViewHolder;
import com.easefun.polyv.livecommon.module.utils.PLVWebUtils;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVAbsProgressStatusListener;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVImageLoader;
import com.easefun.polyv.livecommon.ui.widget.PLVCopyBoardPopupWindow;
import com.easefun.polyv.livecommon.ui.widget.gif.GifSpanTextView;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livecommon.ui.widget.itemview.holder.PLVBaseViewHolder;
import com.easefun.polyv.livescenes.chatroom.PolyvChatroomManager;
import com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendChatImageListener;
import com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendLocalImgEvent;
import com.google.gson.reflect.TypeToken;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.chat.PLVChatQuoteVO;
import com.plv.socket.event.ppt.PLVPptShareFileVO;
import com.plv.socket.user.PLVSocketUserConstant;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import java.util.Map;

/* loaded from: classes3.dex */
public class PLVLCMessageViewHolder extends PLVChatMessageBaseViewHolder<PLVBaseViewData, PLVLCMessageAdapter> {
    public static final String LOADIMG_MOUDLE_TAG = "PLVLCMessageViewHolder";
    private ImageView avatarIv;
    private View chatLayout;
    private ImageView chatMsgFileShareIv;
    private ImageView chatMsgFileShareLandIv;
    private LinearLayout chatMsgLandLl;
    private LinearLayout chatMsgLl;
    private GifSpanTextView chatMsgTv;
    private TextView chatNickTv;
    private ProgressBar imgLoadingView;
    private ImageView imgMessageIv;
    private boolean isLandscapeLayout;
    private TextView nickTv;
    private GifSpanTextView quoteChatMsgTv;
    private TextView quoteChatNickTv;
    private ImageView quoteImgMessageIv;
    private TextView quoteNickTv;
    private View quoteSplitView;
    private GifSpanTextView quoteTextMessageTv;
    private GifSpanTextView textMessageTv;

    public PLVLCMessageViewHolder(View view, PLVLCMessageAdapter pLVLCMessageAdapter) {
        super(view, pLVLCMessageAdapter);
        this.isLandscapeLayout = view.getId() == R.id.chat_landscape_item;
        this.avatarIv = (ImageView) findViewById(R.id.avatar_iv);
        this.nickTv = (TextView) findViewById(R.id.nick_tv);
        this.textMessageTv = (GifSpanTextView) findViewById(R.id.text_message_tv);
        this.quoteNickTv = (TextView) findViewById(R.id.quote_nick_tv);
        this.quoteTextMessageTv = (GifSpanTextView) findViewById(R.id.quote_text_message_tv);
        this.chatMsgLl = (LinearLayout) findViewById(R.id.plvlc_chat_msg_ll);
        this.chatMsgFileShareIv = (ImageView) findViewById(R.id.plvlc_chat_msg_file_share_iv);
        this.chatLayout = findViewById(R.id.chat_msg_ll);
        this.chatMsgTv = (GifSpanTextView) findViewById(R.id.chat_msg_tv);
        this.chatNickTv = (TextView) findViewById(R.id.chat_nick_tv);
        this.quoteChatMsgTv = (GifSpanTextView) findViewById(R.id.quote_chat_msg_tv);
        this.quoteChatNickTv = (TextView) findViewById(R.id.quote_chat_nick_tv);
        this.chatMsgLandLl = (LinearLayout) findViewById(R.id.plvlc_chat_msg_land_ll);
        this.chatMsgFileShareLandIv = (ImageView) findViewById(R.id.plvlc_chat_msg_file_share_land_iv);
        this.imgMessageIv = (ImageView) findViewById(R.id.img_message_iv);
        this.imgLoadingView = (ProgressBar) findViewById(R.id.img_loading_view);
        this.quoteSplitView = findViewById(R.id.quote_split_view);
        this.quoteImgMessageIv = (ImageView) findViewById(R.id.quote_img_message_iv);
        initView();
        addOnSendImgListener();
    }

    private void addOnSendImgListener() {
        PolyvChatroomManager.getInstance().addSendChatImageListener(new PolyvSendChatImageListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.adapter.holder.PLVLCMessageViewHolder.7
            @Override // com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendChatImageListener
            public void onCheckFail(PolyvSendLocalImgEvent polyvSendLocalImgEvent, Throwable th) {
                polyvSendLocalImgEvent.setSendStatus(1);
                if (polyvSendLocalImgEvent == ((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).messageData) {
                    ((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).localImgStatus = polyvSendLocalImgEvent.getSendStatus();
                    if (PLVLCMessageViewHolder.this.imgLoadingView != null) {
                        PLVLCMessageViewHolder.this.imgLoadingView.setVisibility(8);
                    }
                    ToastUtils.showLong("发送图片失败: " + th.getMessage());
                }
            }

            @Override // com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendChatImageListener
            public void onProgress(PolyvSendLocalImgEvent polyvSendLocalImgEvent, float f2) {
                polyvSendLocalImgEvent.setSendStatus(2);
                int i2 = (int) (f2 * 100.0f);
                polyvSendLocalImgEvent.setSendProgress(i2);
                if (polyvSendLocalImgEvent == ((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).messageData) {
                    ((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).localImgStatus = polyvSendLocalImgEvent.getSendStatus();
                    ((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).localImgProgress = polyvSendLocalImgEvent.getSendProgress();
                    if (PLVLCMessageViewHolder.this.imgLoadingView != null) {
                        PLVLCMessageViewHolder.this.imgLoadingView.setVisibility(0);
                        PLVLCMessageViewHolder.this.imgLoadingView.setProgress(i2);
                    }
                }
            }

            @Override // com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendChatImageListener
            public void onSendFail(PolyvSendLocalImgEvent polyvSendLocalImgEvent, int i2) {
                polyvSendLocalImgEvent.setSendStatus(1);
                if (polyvSendLocalImgEvent == ((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).messageData) {
                    ((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).localImgStatus = polyvSendLocalImgEvent.getSendStatus();
                    if (PLVLCMessageViewHolder.this.imgLoadingView != null) {
                        PLVLCMessageViewHolder.this.imgLoadingView.setVisibility(8);
                    }
                    ToastUtils.showLong("发送图片失败: " + i2);
                }
            }

            @Override // com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendChatImageListener
            public void onSuccess(PolyvSendLocalImgEvent polyvSendLocalImgEvent, String str, String str2) {
                polyvSendLocalImgEvent.setSendStatus(0);
                if (polyvSendLocalImgEvent == ((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).messageData) {
                    ((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).localImgStatus = polyvSendLocalImgEvent.getSendStatus();
                    if (PLVLCMessageViewHolder.this.imgLoadingView != null) {
                        PLVLCMessageViewHolder.this.imgLoadingView.setVisibility(8);
                    }
                }
            }

            @Override // com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendChatImageListener
            public void onUploadFail(PolyvSendLocalImgEvent polyvSendLocalImgEvent, Throwable th) {
                polyvSendLocalImgEvent.setSendStatus(1);
                if (polyvSendLocalImgEvent == ((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).messageData) {
                    ((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).localImgStatus = polyvSendLocalImgEvent.getSendStatus();
                    if (PLVLCMessageViewHolder.this.imgLoadingView != null) {
                        PLVLCMessageViewHolder.this.imgLoadingView.setVisibility(8);
                    }
                    ToastUtils.showLong("发送图片失败: " + th.getMessage());
                }
            }
        });
    }

    private PLVAbsProgressStatusListener createStatusListener(String str) {
        return new PLVAbsProgressStatusListener(str) { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.adapter.holder.PLVLCMessageViewHolder.11
            @Override // com.easefun.polyv.livecommon.module.utils.imageloader.PLVAbsProgressStatusListener
            public void onFailedStatus(@Nullable Exception exc, Object obj) {
                if (PLVLCMessageViewHolder.this.imgLoadingView.getTag() != ((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).messageData) {
                    return;
                }
                PLVLCMessageViewHolder.this.imgLoadingView.setVisibility(8);
                PLVLCMessageViewHolder.this.imgLoadingView.setProgress(0);
                PLVLCMessageViewHolder.this.imgMessageIv.setImageResource(R.drawable.plvlc_image_load_err);
            }

            @Override // com.easefun.polyv.livecommon.module.utils.imageloader.PLVAbsProgressStatusListener
            public void onProgressStatus(String str2, boolean z2, int i2, long j2, long j3) {
                if (PLVLCMessageViewHolder.this.imgLoadingView.getTag() != ((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).messageData) {
                    return;
                }
                if (z2) {
                    PLVLCMessageViewHolder.this.imgLoadingView.setProgress(100);
                    PLVLCMessageViewHolder.this.imgLoadingView.setVisibility(8);
                } else {
                    if (PLVLCMessageViewHolder.this.imgMessageIv.getDrawable() != null) {
                        PLVLCMessageViewHolder.this.imgMessageIv.setImageDrawable(null);
                    }
                    PLVLCMessageViewHolder.this.imgLoadingView.setVisibility(0);
                    PLVLCMessageViewHolder.this.imgLoadingView.setProgress(i2);
                }
            }

            @Override // com.easefun.polyv.livecommon.module.utils.imageloader.PLVAbsProgressStatusListener
            public void onResourceReadyStatus(Drawable drawable) {
                if (PLVLCMessageViewHolder.this.imgLoadingView.getTag() != ((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).messageData) {
                    return;
                }
                PLVLCMessageViewHolder.this.imgMessageIv.setImageDrawable(drawable);
            }

            @Override // com.easefun.polyv.livecommon.module.utils.imageloader.PLVAbsProgressStatusListener
            public void onStartStatus(String str2) {
                if (PLVLCMessageViewHolder.this.imgLoadingView.getTag() == ((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).messageData && PLVLCMessageViewHolder.this.imgLoadingView.getProgress() == 0 && PLVLCMessageViewHolder.this.imgLoadingView.getVisibility() != 0) {
                    PLVLCMessageViewHolder.this.imgLoadingView.setVisibility(0);
                    PLVLCMessageViewHolder.this.imgMessageIv.setImageDrawable(null);
                }
            }
        };
    }

    private static CharSequence fixQuoteMessageForFileShare(CharSequence charSequence) {
        try {
            Map map = (Map) PLVGsonUtil.fromJson(new TypeToken<Map<String, String>>() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.adapter.holder.PLVLCMessageViewHolder.12
            }, charSequence.toString());
            if (map == null) {
                return charSequence;
            }
            return map.size() == 2 && map.containsKey("url") && map.containsKey("name") ? (CharSequence) map.get("name") : charSequence;
        } catch (Exception unused) {
            return charSequence;
        }
    }

    @Nullable
    @DrawableRes
    private static Integer getSpeakFileIconRes(@Nullable PLVPptShareFileVO pLVPptShareFileVO) {
        if (pLVPptShareFileVO == null) {
            return null;
        }
        String suffix = pLVPptShareFileVO.getSuffix();
        suffix.hashCode();
        switch (suffix) {
        }
        return null;
    }

    private void initView() {
        GifSpanTextView gifSpanTextView = this.textMessageTv;
        if (gifSpanTextView != null) {
            gifSpanTextView.setWebLinkClickListener(new GifSpanTextView.WebLinkClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.adapter.holder.PLVLCMessageViewHolder.1
                @Override // com.easefun.polyv.livecommon.ui.widget.gif.GifSpanTextView.WebLinkClickListener
                public void webLinkOnClick(String str) {
                    PLVWebUtils.openWebLink(str, PLVLCMessageViewHolder.this.textMessageTv.getContext());
                }
            });
            this.textMessageTv.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.adapter.holder.PLVLCMessageViewHolder.2
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view) {
                    PLVCopyBoardPopupWindow.show(PLVLCMessageViewHolder.this.textMessageTv, true, PLVLCMessageViewHolder.this.textMessageTv.getText().toString());
                    return true;
                }
            });
        }
        GifSpanTextView gifSpanTextView2 = this.chatMsgTv;
        if (gifSpanTextView2 != null) {
            gifSpanTextView2.setWebLinkClickListener(new GifSpanTextView.WebLinkClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.adapter.holder.PLVLCMessageViewHolder.3
                @Override // com.easefun.polyv.livecommon.ui.widget.gif.GifSpanTextView.WebLinkClickListener
                public void webLinkOnClick(String str) {
                    PLVWebUtils.openWebLink(str, PLVLCMessageViewHolder.this.chatMsgTv.getContext());
                }
            });
            this.chatMsgTv.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.adapter.holder.PLVLCMessageViewHolder.4
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view) {
                    PLVCopyBoardPopupWindow.show(PLVLCMessageViewHolder.this.chatMsgTv, true, PLVLCMessageViewHolder.this.chatMsgTv.getText().toString());
                    return true;
                }
            });
        }
        ImageView imageView = this.imgMessageIv;
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.adapter.holder.PLVLCMessageViewHolder.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((PLVLCMessageAdapter) ((PLVBaseViewHolder) PLVLCMessageViewHolder.this).adapter).callOnChatImgClick(PLVLCMessageViewHolder.this.getVHPosition(), view, ((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).chatImgUrl, false);
                }
            });
        }
        ImageView imageView2 = this.quoteImgMessageIv;
        if (imageView2 != null) {
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.adapter.holder.PLVLCMessageViewHolder.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).chatQuoteVO == null || ((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).chatQuoteVO.getImage() == null) {
                        return;
                    }
                    ((PLVLCMessageAdapter) ((PLVBaseViewHolder) PLVLCMessageViewHolder.this).adapter).callOnChatImgClick(PLVLCMessageViewHolder.this.getVHPosition(), view, ((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).chatQuoteVO.getImage().getUrl(), true);
                }
            });
        }
    }

    private void resetView() {
        GifSpanTextView gifSpanTextView = this.textMessageTv;
        if (gifSpanTextView != null) {
            gifSpanTextView.setVisibility(8);
        }
        GifSpanTextView gifSpanTextView2 = this.chatMsgTv;
        if (gifSpanTextView2 != null) {
            gifSpanTextView2.setVisibility(8);
        }
        TextView textView = this.chatNickTv;
        if (textView != null) {
            textView.setVisibility(8);
        }
        ImageView imageView = this.imgMessageIv;
        if (imageView != null) {
            imageView.setVisibility(8);
            if (this.imgMessageIv.getDrawable() != null) {
                this.imgMessageIv.setImageDrawable(null);
            }
        }
        ProgressBar progressBar = this.imgLoadingView;
        if (progressBar != null) {
            progressBar.setTag(this.messageData);
        }
        if (this.isLocalChatImg) {
            ProgressBar progressBar2 = this.imgLoadingView;
            if (progressBar2 != null) {
                progressBar2.setVisibility(this.localImgStatus != 2 ? 8 : 0);
                this.imgLoadingView.setProgress(this.localImgProgress);
            }
        } else {
            ProgressBar progressBar3 = this.imgLoadingView;
            if (progressBar3 != null) {
                progressBar3.setVisibility(8);
                this.imgLoadingView.setProgress(0);
            }
        }
        TextView textView2 = this.quoteNickTv;
        if (textView2 != null) {
            textView2.setVisibility(8);
        }
        GifSpanTextView gifSpanTextView3 = this.quoteTextMessageTv;
        if (gifSpanTextView3 != null) {
            gifSpanTextView3.setVisibility(8);
        }
        GifSpanTextView gifSpanTextView4 = this.quoteChatMsgTv;
        if (gifSpanTextView4 != null) {
            gifSpanTextView4.setVisibility(8);
        }
        TextView textView3 = this.quoteChatNickTv;
        if (textView3 != null) {
            textView3.setVisibility(8);
        }
        View view = this.quoteSplitView;
        if (view != null) {
            view.setVisibility(8);
        }
        ImageView imageView2 = this.quoteImgMessageIv;
        if (imageView2 != null) {
            imageView2.setVisibility(8);
        }
    }

    private void setImgMessage() {
        if (this.chatImgUrl == null || this.imgMessageIv == null) {
            if (this.isLandscapeLayout) {
                return;
            }
            this.chatLayout.setVisibility(0);
            return;
        }
        if (!this.isLandscapeLayout) {
            this.chatLayout.setVisibility(4);
        }
        this.imgMessageIv.setVisibility(0);
        PLVChatMessageBaseViewHolder.fitChatImgWH(this.chatImgWidth, this.chatImgHeight, this.imgMessageIv, 120, 80);
        if (this.isLocalChatImg) {
            PLVImageLoader.getInstance().loadImage(this.chatImgUrl, this.imgMessageIv);
            return;
        }
        PLVImageLoader.getInstance().loadImage(this.itemView.getContext(), LOADIMG_MOUDLE_TAG, LOADIMG_MOUDLE_TAG + this.messageData, R.drawable.plvlc_image_load_err, createStatusListener(this.chatImgUrl), this.imgMessageIv);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.holder.PLVChatMessageBaseViewHolder, com.easefun.polyv.livecommon.ui.widget.itemview.holder.PLVBaseViewHolder
    public void processData(PLVBaseViewData pLVBaseViewData, int i2) {
        super.processData(pLVBaseViewData, i2);
        resetView();
        boolean zIsSpecialType = PLVEventHelper.isSpecialType(this.userType);
        String str = (String) PLVSugarUtil.nullable(new PLVSugarUtil.Supplier<String>() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.adapter.holder.PLVLCMessageViewHolder.8
            @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Supplier
            public String get() {
                return PLVSocketWrapper.getInstance().getLoginVO().getUserId();
            }
        });
        if (this.avatar != null && this.avatarIv != null) {
            int i3 = (PLVSocketUserConstant.USERTYPE_MANAGER.equals(this.userType) || "teacher".equals(this.userType)) ? R.drawable.plvlc_chatroom_ic_teacher : "assistant".equals(this.userType) ? R.drawable.plvlc_chatroom_ic_assistant : "guest".equals(this.userType) ? R.drawable.plvlc_chatroom_ic_guest : R.drawable.plvlc_chatroom_ic_viewer;
            PLVImageLoader.getInstance().loadImageNoDiskCache(this.itemView.getContext(), this.avatar, i3, i3, this.avatarIv);
        }
        if (this.nickName != null) {
            if (str != null && str.equals(this.userId)) {
                this.nickName += "(我)";
            }
            if (this.actor != null) {
                this.nickName = this.actor + "-" + this.nickName;
            }
            TextView textView = this.nickTv;
            if (textView != null) {
                textView.setText(this.nickName);
                this.nickTv.setTextColor(Color.parseColor(this.actor != null ? "#78A7ED" : "#ADADC0"));
            }
            TextView textView2 = this.chatNickTv;
            if (textView2 != null && this.chatImgUrl != null) {
                textView2.setVisibility(0);
                this.chatNickTv.setText(this.nickName + ": ");
                this.chatNickTv.setTextColor(Color.parseColor(this.actor != null ? "#FFD36D" : "#6DA7FF"));
            }
        }
        if (this.speakMsg != null) {
            GifSpanTextView gifSpanTextView = this.textMessageTv;
            if (gifSpanTextView != null) {
                gifSpanTextView.setVisibility(0);
                this.textMessageTv.setTextColor(Color.parseColor(this.actor == null ? "#ADADC0" : "#78A7ED"));
                this.textMessageTv.setTextInner(this.speakMsg, zIsSpecialType);
            }
            GifSpanTextView gifSpanTextView2 = this.chatMsgTv;
            if (gifSpanTextView2 != null) {
                gifSpanTextView2.setVisibility(0);
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.nickName);
                spannableStringBuilder.append((CharSequence) ": ");
                spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor(this.actor != null ? "#FFD36D" : "#6DA7FF")), 0, spannableStringBuilder.length(), 33);
                this.chatMsgTv.setTextInner(spannableStringBuilder.append(this.speakMsg), zIsSpecialType);
            }
        }
        if (this.speakFileData != null) {
            GifSpanTextView gifSpanTextView3 = this.textMessageTv;
            if (gifSpanTextView3 != null) {
                gifSpanTextView3.setVisibility(0);
                this.textMessageTv.setTextColor(-1);
                this.textMessageTv.setTextInner(this.speakFileData.getName(), false);
            }
            GifSpanTextView gifSpanTextView4 = this.chatMsgTv;
            if (gifSpanTextView4 != null) {
                gifSpanTextView4.setVisibility(0);
                SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(this.nickName);
                spannableStringBuilder2.append((CharSequence) ": ");
                spannableStringBuilder2.setSpan(new ForegroundColorSpan(Color.parseColor(this.actor == null ? "#6DA7FF" : "#FFD36D")), 0, spannableStringBuilder2.length(), 33);
                this.chatMsgTv.setTextInner(spannableStringBuilder2.append((CharSequence) this.speakFileData.getName()), false);
            }
            LinearLayout linearLayout = this.chatMsgLl;
            if (linearLayout != null) {
                linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.adapter.holder.PLVLCMessageViewHolder.9
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).speakFileData != null) {
                            PLVWebUtils.openWebLink(((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).speakFileData.getUrl(), PLVLCMessageViewHolder.this.chatMsgLl.getContext());
                        }
                    }
                });
            }
            LinearLayout linearLayout2 = this.chatMsgLandLl;
            if (linearLayout2 != null) {
                linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.adapter.holder.PLVLCMessageViewHolder.10
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).speakFileData != null) {
                            PLVWebUtils.openWebLink(((PLVChatMessageBaseViewHolder) PLVLCMessageViewHolder.this).speakFileData.getUrl(), PLVLCMessageViewHolder.this.chatMsgLandLl.getContext());
                        }
                    }
                });
            }
            Integer speakFileIconRes = getSpeakFileIconRes(this.speakFileData);
            ImageView imageView = this.chatMsgFileShareIv;
            if (imageView != null) {
                if (speakFileIconRes != null) {
                    imageView.setVisibility(0);
                    this.chatMsgFileShareIv.setImageResource(speakFileIconRes.intValue());
                } else {
                    imageView.setVisibility(8);
                }
            }
            ImageView imageView2 = this.chatMsgFileShareLandIv;
            if (imageView2 != null) {
                if (speakFileIconRes != null) {
                    imageView2.setVisibility(0);
                    this.chatMsgFileShareLandIv.setImageResource(speakFileIconRes.intValue());
                } else {
                    imageView2.setVisibility(8);
                }
            }
        } else {
            ImageView imageView3 = this.chatMsgFileShareIv;
            if (imageView3 != null) {
                imageView3.setVisibility(8);
            }
            ImageView imageView4 = this.chatMsgFileShareLandIv;
            if (imageView4 != null) {
                imageView4.setVisibility(8);
            }
            LinearLayout linearLayout3 = this.chatMsgLl;
            if (linearLayout3 != null) {
                linearLayout3.setOnClickListener(null);
            }
            LinearLayout linearLayout4 = this.chatMsgLandLl;
            if (linearLayout4 != null) {
                linearLayout4.setOnClickListener(null);
            }
        }
        setImgMessage();
        PLVChatQuoteVO pLVChatQuoteVO = this.chatQuoteVO;
        if (pLVChatQuoteVO != null) {
            String nick = pLVChatQuoteVO.getNick();
            if (str != null && str.equals(this.chatQuoteVO.getUserId())) {
                nick = nick + "(我)";
            }
            View view = this.quoteSplitView;
            if (view != null) {
                view.setVisibility(0);
            }
            TextView textView3 = this.quoteNickTv;
            if (textView3 != null) {
                textView3.setVisibility(0);
                this.quoteNickTv.setText(nick + ": ");
            }
            if (this.chatQuoteVO.isSpeakMessage()) {
                GifSpanTextView gifSpanTextView5 = this.quoteTextMessageTv;
                if (gifSpanTextView5 != null) {
                    gifSpanTextView5.setVisibility(0);
                    this.quoteTextMessageTv.setText(fixQuoteMessageForFileShare(this.quoteSpeakMsg));
                }
                GifSpanTextView gifSpanTextView6 = this.quoteChatMsgTv;
                if (gifSpanTextView6 != null) {
                    gifSpanTextView6.setVisibility(0);
                    this.quoteChatMsgTv.setText(new SpannableStringBuilder(nick).append((CharSequence) ": ").append(fixQuoteMessageForFileShare(this.quoteSpeakMsg)));
                    return;
                }
                return;
            }
            TextView textView4 = this.quoteChatNickTv;
            if (textView4 != null) {
                textView4.setVisibility(0);
                this.quoteChatNickTv.setText(nick + ": ");
            }
            if (this.quoteImgMessageIv == null || this.chatQuoteVO.getImage() == null) {
                return;
            }
            this.quoteImgMessageIv.setVisibility(0);
            PLVChatMessageBaseViewHolder.fitChatImgWH((int) this.chatQuoteVO.getImage().getWidth(), (int) this.chatQuoteVO.getImage().getHeight(), this.quoteImgMessageIv, 60, 40);
            PLVImageLoader.getInstance().loadImage(this.chatQuoteVO.getImage().getUrl(), this.quoteImgMessageIv);
        }
    }
}

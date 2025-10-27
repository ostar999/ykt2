package com.hyphenate.easeui.modules.chat.presenter;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.text.TextUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMTranslationResult;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.manager.EaseAtMessageHelper;
import com.hyphenate.easeui.modules.chat.EaseChatLayout;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseFileUtils;
import com.hyphenate.easeui.utils.EaseImageUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.ImageUtils;
import com.hyphenate.util.PathUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes4.dex */
public class EaseHandleMessagePresenterImpl extends EaseHandleMessagePresenter {
    private static final String TAG = EaseChatLayout.class.getSimpleName();

    /* renamed from: com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenterImpl$1, reason: invalid class name */
    public class AnonymousClass1 implements EMCallBack {
        final /* synthetic */ EMMessage val$message;

        public AnonymousClass1(EMMessage eMMessage) {
            this.val$message = eMMessage;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(EMMessage eMMessage, int i2, String str) {
            EaseHandleMessagePresenterImpl.this.mView.onPresenterMessageError(eMMessage, i2, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProgress$2(EMMessage eMMessage, int i2) {
            EaseHandleMessagePresenterImpl.this.mView.onPresenterMessageInProgress(eMMessage, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(EMMessage eMMessage) {
            EaseHandleMessagePresenterImpl.this.mView.onPresenterMessageSuccess(eMMessage);
        }

        @Override // com.hyphenate.EMCallBack
        public void onError(final int i2, final String str) {
            if (EaseHandleMessagePresenterImpl.this.isActive()) {
                EaseHandleMessagePresenterImpl easeHandleMessagePresenterImpl = EaseHandleMessagePresenterImpl.this;
                final EMMessage eMMessage = this.val$message;
                easeHandleMessagePresenterImpl.runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.w
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f8710c.lambda$onError$1(eMMessage, i2, str);
                    }
                });
            }
        }

        @Override // com.hyphenate.EMCallBack
        public void onProgress(final int i2, String str) {
            if (EaseHandleMessagePresenterImpl.this.isActive()) {
                EaseHandleMessagePresenterImpl easeHandleMessagePresenterImpl = EaseHandleMessagePresenterImpl.this;
                final EMMessage eMMessage = this.val$message;
                easeHandleMessagePresenterImpl.runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.x
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f8714c.lambda$onProgress$2(eMMessage, i2);
                    }
                });
            }
        }

        @Override // com.hyphenate.EMCallBack
        public void onSuccess() {
            if (EaseHandleMessagePresenterImpl.this.isActive()) {
                EaseHandleMessagePresenterImpl easeHandleMessagePresenterImpl = EaseHandleMessagePresenterImpl.this;
                final EMMessage eMMessage = this.val$message;
                easeHandleMessagePresenterImpl.runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.y
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f8717c.lambda$onSuccess$0(eMMessage);
                    }
                });
            }
        }
    }

    /* renamed from: com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenterImpl$2, reason: invalid class name */
    public class AnonymousClass2 implements EMValueCallBack<EMTranslationResult> {
        final /* synthetic */ EMMessage val$message;

        public AnonymousClass2(EMMessage eMMessage) {
            this.val$message = eMMessage;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(EMMessage eMMessage, int i2, String str) {
            EaseHandleMessagePresenterImpl.this.mView.translateMessageFail(eMMessage, i2, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(EMMessage eMMessage) {
            EaseHandleMessagePresenterImpl.this.mView.translateMessageSuccess(eMMessage);
        }

        @Override // com.hyphenate.EMValueCallBack
        public void onError(final int i2, final String str) {
            if (EaseHandleMessagePresenterImpl.this.isActive()) {
                EaseHandleMessagePresenterImpl easeHandleMessagePresenterImpl = EaseHandleMessagePresenterImpl.this;
                final EMMessage eMMessage = this.val$message;
                easeHandleMessagePresenterImpl.runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.z
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f8719c.lambda$onError$1(eMMessage, i2, str);
                    }
                });
            }
        }

        @Override // com.hyphenate.EMValueCallBack
        public void onSuccess(EMTranslationResult eMTranslationResult) {
            if (EaseHandleMessagePresenterImpl.this.isActive()) {
                EaseHandleMessagePresenterImpl easeHandleMessagePresenterImpl = EaseHandleMessagePresenterImpl.this;
                final EMMessage eMMessage = this.val$message;
                easeHandleMessagePresenterImpl.runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.a0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f8663c.lambda$onSuccess$0(eMMessage);
                    }
                });
            }
        }
    }

    private String getThumbPath(Uri uri) throws IOException, SecurityException, IllegalArgumentException {
        if (!EaseFileUtils.isFileExistByUri(this.mView.context(), uri)) {
            return "";
        }
        String filePath = EaseFileUtils.getFilePath(this.mView.context(), uri);
        File file = new File(PathUtil.getInstance().getVideoPath(), "thvideo" + System.currentTimeMillis() + ".jpeg");
        boolean z2 = false;
        if (TextUtils.isEmpty(filePath) || !new File(filePath).exists()) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(this.mView.context(), uri);
                mediaMetadataRetriever.getFrameAtTime().compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.close();
                z2 = true;
            } catch (Exception e2) {
                e2.printStackTrace();
                EMLog.e(TAG, e2.getMessage());
                if (isActive()) {
                    runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.t
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f8705c.lambda$getThumbPath$8(e2);
                        }
                    });
                }
            }
        } else {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                ThumbnailUtils.createVideoThumbnail(filePath, 3).compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream2);
                fileOutputStream2.close();
                z2 = true;
            } catch (Exception e3) {
                e3.printStackTrace();
                EMLog.e(TAG, e3.getMessage());
                if (isActive()) {
                    runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.s
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f8703c.lambda$getThumbPath$7(e3);
                        }
                    });
                }
            }
        }
        return z2 ? file.getAbsolutePath() : "";
    }

    private Uri handleImageHeifToJpeg(Uri uri) {
        try {
            String filePath = EaseFileUtils.getFilePath(this.mView.context(), uri);
            return "image/heif".equalsIgnoreCase(((TextUtils.isEmpty(filePath) || !new File(filePath).exists()) ? ImageUtils.getBitmapOptions(this.mView.context(), uri) : ImageUtils.getBitmapOptions(filePath)).outMimeType) ? EaseImageUtils.imageToJpeg(this.mView.context(), uri, new File(PathUtil.getInstance().getImagePath(), "image_message_temp.jpeg")) : uri;
        } catch (Exception e2) {
            e2.printStackTrace();
            return uri;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteMessage$3(EMMessage eMMessage) {
        this.mView.deleteLocalMessageSuccess(eMMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getThumbPath$7(Exception exc) {
        this.mView.createThumbFileFail(exc.getMessage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getThumbPath$8(Exception exc) {
        this.mView.createThumbFileFail(exc.getMessage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$recallMessage$4(EMMessage eMMessage) {
        this.mView.recallMessageFinish(eMMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$recallMessage$5(HyphenateException hyphenateException) {
        this.mView.recallMessageFail(hyphenateException.getErrorCode(), hyphenateException.getDescription());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendAtMessage$0() {
        this.mView.sendMessageFail("only support group chat message");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendMessage$1() {
        this.mView.sendMessageFail("message is null!");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendMessage$2(EMMessage eMMessage) {
        this.mView.sendMessageFinish(eMMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$translateMessage$6(EMMessage eMMessage) {
        this.mView.translateMessageSuccess(eMMessage);
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenter
    public void addMessageAttributes(EMMessage eMMessage) {
        this.mView.addMsgAttrBeforeSend(eMMessage);
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenter
    public void deleteMessage(final EMMessage eMMessage) {
        this.conversation.removeMessage(eMMessage.getMsgId());
        if (isActive()) {
            runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.o
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8696c.lambda$deleteMessage$3(eMMessage);
                }
            });
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenter
    public void hideTranslate(EMMessage eMMessage) {
        EMTranslationResult translationResult = EMClient.getInstance().translationManager().getTranslationResult(eMMessage.getMsgId());
        translationResult.setShowTranslation(false);
        EMClient.getInstance().translationManager().updateTranslationResult(translationResult);
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenter
    public void recallMessage(EMMessage eMMessage) {
        try {
            final EMMessage eMMessageCreateSendMessage = EMMessage.createSendMessage(EMMessage.Type.TXT);
            eMMessageCreateSendMessage.addBody(new EMTextMessageBody(this.mView.context().getResources().getString(R.string.msg_recall_by_self)));
            eMMessageCreateSendMessage.setTo(eMMessage.getTo());
            eMMessageCreateSendMessage.setDirection(eMMessage.direct());
            eMMessageCreateSendMessage.setMsgTime(eMMessage.getMsgTime());
            eMMessageCreateSendMessage.setLocalTime(eMMessage.getMsgTime());
            eMMessageCreateSendMessage.setAttribute("message_recall", true);
            eMMessageCreateSendMessage.setAttribute(EaseConstant.MESSAGE_TYPE_RECALLER, EMClient.getInstance().getCurrentUser());
            eMMessageCreateSendMessage.setStatus(EMMessage.Status.SUCCESS);
            EMClient.getInstance().chatManager().recallMessage(eMMessage);
            EMClient.getInstance().chatManager().saveMessage(eMMessageCreateSendMessage);
            if (isActive()) {
                runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.p
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f8698c.lambda$recallMessage$4(eMMessageCreateSendMessage);
                    }
                });
            }
        } catch (HyphenateException e2) {
            e2.printStackTrace();
            if (isActive()) {
                runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.q
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f8700c.lambda$recallMessage$5(e2);
                    }
                });
            }
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenter
    public void resendMessage(EMMessage eMMessage) {
        eMMessage.setStatus(EMMessage.Status.CREATE);
        long jCurrentTimeMillis = System.currentTimeMillis();
        eMMessage.setLocalTime(jCurrentTimeMillis);
        eMMessage.setMsgTime(jCurrentTimeMillis);
        EMClient.getInstance().chatManager().updateMessage(eMMessage);
        sendMessage(eMMessage);
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenter
    public void sendAtMessage(String str) {
        if (!isGroupChat()) {
            EMLog.e(TAG, "only support group chat message");
            if (isActive()) {
                runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.r
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f8702c.lambda$sendAtMessage$0();
                    }
                });
                return;
            }
            return;
        }
        EMMessage eMMessageCreateTxtSendMessage = EMMessage.createTxtSendMessage(str, this.toChatUsername);
        if (EMClient.getInstance().getCurrentUser().equals(EMClient.getInstance().groupManager().getGroup(this.toChatUsername).getOwner()) && EaseAtMessageHelper.get().containsAtAll(str)) {
            eMMessageCreateTxtSendMessage.setAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG, "ALL");
        } else {
            eMMessageCreateTxtSendMessage.setAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG, EaseAtMessageHelper.get().atListToJsonArray(EaseAtMessageHelper.get().getAtMessageUsernames(str)));
        }
        sendMessage(eMMessageCreateTxtSendMessage);
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenter
    public void sendBigExpressionMessage(String str, String str2) {
        sendMessage(EaseCommonUtils.createExpressionMessage(this.toChatUsername, str, str2));
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenter
    public void sendCmdMessage(String str) {
        EMMessage eMMessageCreateSendMessage = EMMessage.createSendMessage(EMMessage.Type.CMD);
        EMCmdMessageBody eMCmdMessageBody = new EMCmdMessageBody(str);
        eMCmdMessageBody.deliverOnlineOnly(true);
        eMMessageCreateSendMessage.addBody(eMCmdMessageBody);
        eMMessageCreateSendMessage.setTo(this.toChatUsername);
        EMClient.getInstance().chatManager().sendMessage(eMMessageCreateSendMessage);
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenter
    public void sendFileMessage(Uri uri) {
        sendMessage(EMMessage.createFileSendMessage(uri, this.toChatUsername));
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenter
    public void sendImageMessage(Uri uri) {
        sendImageMessage(uri, false);
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenter
    public void sendLocationMessage(double d3, double d4, String str, String str2) {
        EMMessage eMMessageCreateLocationSendMessage = EMMessage.createLocationSendMessage(d3, d4, str, str2, this.toChatUsername);
        String str3 = TAG;
        EMLog.i(str3, "current = " + EMClient.getInstance().getCurrentUser() + " to = " + this.toChatUsername);
        EMMessageBody body = eMMessageCreateLocationSendMessage.getBody();
        String msgId = eMMessageCreateLocationSendMessage.getMsgId();
        String from = eMMessageCreateLocationSendMessage.getFrom();
        EMLog.i(str3, "body = " + body);
        EMLog.i(str3, "msgId = " + msgId + " from = " + from);
        sendMessage(eMMessageCreateLocationSendMessage);
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenter
    public void sendMessage(final EMMessage eMMessage) {
        if (eMMessage == null) {
            if (isActive()) {
                runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.u
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f8707c.lambda$sendMessage$1();
                    }
                });
                return;
            }
            return;
        }
        addMessageAttributes(eMMessage);
        int i2 = this.chatType;
        if (i2 == 2) {
            eMMessage.setChatType(EMMessage.ChatType.GroupChat);
        } else if (i2 == 3) {
            eMMessage.setChatType(EMMessage.ChatType.ChatRoom);
        }
        eMMessage.setMessageStatusCallback(new AnonymousClass1(eMMessage));
        EMClient.getInstance().chatManager().sendMessage(eMMessage);
        if (isActive()) {
            runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.v
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8708c.lambda$sendMessage$2(eMMessage);
                }
            });
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenter
    public void sendTextMessage(String str) {
        sendTextMessage(str, false);
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenter
    public void sendVideoMessage(Uri uri, int i2) {
        sendMessage(EMMessage.createVideoSendMessage(uri, getThumbPath(uri), i2, this.toChatUsername));
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenter
    public void sendVoiceMessage(Uri uri, int i2) {
        sendMessage(EMMessage.createVoiceSendMessage(uri, i2, this.toChatUsername));
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenter
    public void translateMessage(final EMMessage eMMessage, String str, boolean z2) {
        EMTranslationResult translationResult;
        EMTextMessageBody eMTextMessageBody = (EMTextMessageBody) eMMessage.getBody();
        if (!z2 || (translationResult = EMClient.getInstance().translationManager().getTranslationResult(eMMessage.getMsgId())) == null) {
            EMClient.getInstance().translationManager().translate(eMMessage.getMsgId(), eMMessage.conversationId(), eMTextMessageBody.getMessage(), str, new AnonymousClass2(eMMessage));
            return;
        }
        translationResult.setShowTranslation(true);
        EMClient.getInstance().translationManager().updateTranslationResult(translationResult);
        if (isActive()) {
            runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.n
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8694c.lambda$translateMessage$6(eMMessage);
                }
            });
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenter
    public void sendImageMessage(Uri uri, boolean z2) {
        sendMessage(EMMessage.createImageSendMessage(handleImageHeifToJpeg(uri), z2, this.toChatUsername));
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenter
    public void sendTextMessage(String str, boolean z2) {
        if (EaseAtMessageHelper.get().containsAtUsername(str)) {
            sendAtMessage(str);
            return;
        }
        EMMessage eMMessageCreateTxtSendMessage = EMMessage.createTxtSendMessage(str, this.toChatUsername);
        eMMessageCreateTxtSendMessage.setIsNeedGroupAck(z2);
        sendMessage(eMMessageCreateTxtSendMessage);
    }
}

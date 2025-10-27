package com.hyphenate.easeui.modules.chat.presenter;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.modules.ILoadDataView;

/* loaded from: classes4.dex */
public interface IHandleMessageView extends ILoadDataView {
    void addMsgAttrBeforeSend(EMMessage eMMessage);

    void createThumbFileFail(String str);

    void deleteLocalMessageSuccess(EMMessage eMMessage);

    void onPresenterMessageError(EMMessage eMMessage, int i2, String str);

    void onPresenterMessageInProgress(EMMessage eMMessage, int i2);

    void onPresenterMessageSuccess(EMMessage eMMessage);

    void recallMessageFail(int i2, String str);

    void recallMessageFinish(EMMessage eMMessage);

    void sendMessageFail(String str);

    void sendMessageFinish(EMMessage eMMessage);

    void translateMessageFail(EMMessage eMMessage, int i2, String str);

    void translateMessageSuccess(EMMessage eMMessage);
}

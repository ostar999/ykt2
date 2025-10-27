package com.hyphenate.easeui.interfaces;

import android.view.View;
import com.hyphenate.chat.EMMessage;

/* loaded from: classes4.dex */
public interface MessageListItemClickListener {
    boolean onBubbleClick(View view, EMMessage eMMessage);

    boolean onBubbleLongClick(View view, EMMessage eMMessage);

    void onMessageCreate(EMMessage eMMessage);

    void onMessageError(EMMessage eMMessage, int i2, String str);

    void onMessageInProgress(EMMessage eMMessage, int i2);

    void onMessageSuccess(EMMessage eMMessage);

    boolean onResendClick(EMMessage eMMessage);

    void onUserAvatarClick(String str);

    void onUserAvatarLongClick(String str);
}

package com.plv.livescenes.chatroom.playback;

import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class PLVChatPlaybackSpeak extends PLVChatPlaybackBase {
    public static final String MSGTYPE_SPEAK = "speak";

    @Override // com.plv.livescenes.chatroom.playback.PLVChatPlaybackBase, com.plv.livescenes.chatroom.PLVBaseHolder
    public String toString() {
        return "PLVChatPlaybackSpeak{id=" + this.id + ", msg='" + this.msg + CharPool.SINGLE_QUOTE + ", time='" + this.time + CharPool.SINGLE_QUOTE + ", fontSize='" + this.fontSize + CharPool.SINGLE_QUOTE + ", fontMode='" + this.fontMode + CharPool.SINGLE_QUOTE + ", fontColor='" + this.fontColor + CharPool.SINGLE_QUOTE + ", timestamp='" + this.timestamp + CharPool.SINGLE_QUOTE + ", sessionId=" + this.sessionId + ", param2=" + this.param2 + ", msgType='" + this.msgType + CharPool.SINGLE_QUOTE + ", user=" + this.user + ", origin='" + this.origin + CharPool.SINGLE_QUOTE + '}';
    }
}

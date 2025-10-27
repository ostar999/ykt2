package com.plv.livescenes.chatroom;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
class PLVChatRoomOperationFactory {
    static Map<String, PLVChatRoomBaseOperation> baseOperationMap = new HashMap();

    /* JADX WARN: Removed duplicated region for block: B:35:0x0064  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized com.plv.livescenes.chatroom.PLVChatRoomBaseOperation getOperation(com.plv.livescenes.chatroom.PLVChatroomManager r3, java.lang.String r4) {
        /*
            java.lang.Class<com.plv.livescenes.chatroom.PLVChatRoomOperationFactory> r0 = com.plv.livescenes.chatroom.PLVChatRoomOperationFactory.class
            monitor-enter(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r4)     // Catch: java.lang.Throwable -> L9b
            if (r1 == 0) goto Lc
            monitor-exit(r0)
            r3 = 0
            return r3
        Lc:
            java.util.Map<java.lang.String, com.plv.livescenes.chatroom.PLVChatRoomBaseOperation> r1 = com.plv.livescenes.chatroom.PLVChatRoomOperationFactory.baseOperationMap     // Catch: java.lang.Throwable -> L9b
            java.lang.Object r1 = r1.get(r4)     // Catch: java.lang.Throwable -> L9b
            com.plv.livescenes.chatroom.PLVChatRoomBaseOperation r1 = (com.plv.livescenes.chatroom.PLVChatRoomBaseOperation) r1     // Catch: java.lang.Throwable -> L9b
            if (r1 != 0) goto L99
            int r2 = r4.hashCode()     // Catch: java.lang.Throwable -> L9b
            switch(r2) {
                case -2073662916: goto L5a;
                case -2043999862: goto L50;
                case -1989954893: goto L46;
                case 62966102: goto L3c;
                case 72611657: goto L32;
                case 79103922: goto L28;
                case 765790018: goto L1e;
                default: goto L1d;
            }     // Catch: java.lang.Throwable -> L9b
        L1d:
            goto L64
        L1e:
            java.lang.String r2 = "UNSHIELD"
            boolean r2 = r4.equals(r2)     // Catch: java.lang.Throwable -> L9b
            if (r2 == 0) goto L64
            r2 = 5
            goto L65
        L28:
            java.lang.String r2 = "SPEAK"
            boolean r2 = r4.equals(r2)     // Catch: java.lang.Throwable -> L9b
            if (r2 == 0) goto L64
            r2 = 6
            goto L65
        L32:
            java.lang.String r2 = "LOGIN"
            boolean r2 = r4.equals(r2)     // Catch: java.lang.Throwable -> L9b
            if (r2 == 0) goto L64
            r2 = 3
            goto L65
        L3c:
            java.lang.String r2 = "BANIP"
            boolean r2 = r4.equals(r2)     // Catch: java.lang.Throwable -> L9b
            if (r2 == 0) goto L64
            r2 = 1
            goto L65
        L46:
            java.lang.String r2 = "CLOSEROOM"
            boolean r2 = r4.equals(r2)     // Catch: java.lang.Throwable -> L9b
            if (r2 == 0) goto L64
            r2 = 0
            goto L65
        L50:
            java.lang.String r2 = "LOGOUT"
            boolean r2 = r4.equals(r2)     // Catch: java.lang.Throwable -> L9b
            if (r2 == 0) goto L64
            r2 = 4
            goto L65
        L5a:
            java.lang.String r2 = "CHAT_IMG"
            boolean r2 = r4.equals(r2)     // Catch: java.lang.Throwable -> L9b
            if (r2 == 0) goto L64
            r2 = 2
            goto L65
        L64:
            r2 = -1
        L65:
            switch(r2) {
                case 0: goto L8d;
                case 1: goto L87;
                case 2: goto L81;
                case 3: goto L7b;
                case 4: goto L75;
                case 5: goto L6f;
                case 6: goto L69;
                default: goto L68;
            }     // Catch: java.lang.Throwable -> L9b
        L68:
            goto L92
        L69:
            com.plv.livescenes.chatroom.PLVChatRoomSpeakOperation r1 = new com.plv.livescenes.chatroom.PLVChatRoomSpeakOperation     // Catch: java.lang.Throwable -> L9b
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L9b
            goto L92
        L6f:
            com.plv.livescenes.chatroom.PLVChatRoomUnshieldOperation r1 = new com.plv.livescenes.chatroom.PLVChatRoomUnshieldOperation     // Catch: java.lang.Throwable -> L9b
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L9b
            goto L92
        L75:
            com.plv.livescenes.chatroom.PLVChatRoomLogoutOperation r1 = new com.plv.livescenes.chatroom.PLVChatRoomLogoutOperation     // Catch: java.lang.Throwable -> L9b
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L9b
            goto L92
        L7b:
            com.plv.livescenes.chatroom.PLVChatRoomLoginOperation r1 = new com.plv.livescenes.chatroom.PLVChatRoomLoginOperation     // Catch: java.lang.Throwable -> L9b
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L9b
            goto L92
        L81:
            com.plv.livescenes.chatroom.PLVChatRoomImgOperation r1 = new com.plv.livescenes.chatroom.PLVChatRoomImgOperation     // Catch: java.lang.Throwable -> L9b
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L9b
            goto L92
        L87:
            com.plv.livescenes.chatroom.PLVChatRoomBanIpOperation r1 = new com.plv.livescenes.chatroom.PLVChatRoomBanIpOperation     // Catch: java.lang.Throwable -> L9b
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L9b
            goto L92
        L8d:
            com.plv.livescenes.chatroom.PLVChatRoomCloseOperation r1 = new com.plv.livescenes.chatroom.PLVChatRoomCloseOperation     // Catch: java.lang.Throwable -> L9b
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L9b
        L92:
            if (r1 == 0) goto L99
            java.util.Map<java.lang.String, com.plv.livescenes.chatroom.PLVChatRoomBaseOperation> r3 = com.plv.livescenes.chatroom.PLVChatRoomOperationFactory.baseOperationMap     // Catch: java.lang.Throwable -> L9b
            r3.put(r4, r1)     // Catch: java.lang.Throwable -> L9b
        L99:
            monitor-exit(r0)
            return r1
        L9b:
            r3 = move-exception
            monitor-exit(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.plv.livescenes.chatroom.PLVChatRoomOperationFactory.getOperation(com.plv.livescenes.chatroom.PLVChatroomManager, java.lang.String):com.plv.livescenes.chatroom.PLVChatRoomBaseOperation");
    }
}

package com.tencent.liteav.trtc.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.tencent.liteav.TXCRenderAndDec;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.g;
import com.tencent.liteav.renderer.e;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.lingala.zip4j.crypto.PBKDF2.BinTools;
import org.json.JSONArray;

/* loaded from: classes6.dex */
public class TRTCRoomInfo {
    public static final int NETWORK_STATUS_OFFLINE = 1;
    public static final int NETWORK_STATUS_ONLINE = 3;
    public static final int NETWORK_STATUS_RECONNECTING = 2;
    public static final int STATE_AUDIO = 8;
    public static final int STATE_BIG_VIDEO = 1;
    public static final int STATE_MUTE_AUDIO = 64;
    public static final int STATE_MUTE_MAIN_VIDEO = 16;
    public static final int STATE_MUTE_SUB_VIDEO = 32;
    public static final int STATE_SMALL_VIDEO = 2;
    public static final int STATE_SUB_VIDEO = 4;
    private static final String TAG = "TRTCRoomInfo";
    private static final String TOKEN = "TRTC.0x0.Token";
    private static final String TRTC_INFO = "TRTC.Info";
    public g.a bigEncSize;
    public JSONArray decProperties;
    public boolean enableRestartDecoder;
    public long enterTime;
    private int exitRoomCode;
    private boolean hasExitedRoom;
    public int localBufferType;
    public TRTCCloudListener.TRTCVideoRenderListener localListener;
    public int localPixelFormat;
    public int localRenderRotation;
    private boolean micHasStartd;
    public boolean muteLocalAudio;
    public TRTCRemoteMuteState muteRemoteAudio;
    public TRTCRemoteMuteState muteRemoteVideo;
    public String privateMapKey;
    public long roomId;
    public int sdkAppId;
    public g.a smallEncSize;
    public String strRoomId;
    public String tinyId;
    public String userSig;
    public String userId = "";
    public byte[] token = null;
    public int networkStatus = 1;
    public TRTCCloud.TRTCViewMargin debugMargin = new TRTCCloud.TRTCViewMargin(0.0f, 0.0f, 0.1f, 0.0f);
    public boolean enableCustomPreprocessor = false;
    public TXCloudVideoView localView = null;
    private HashMap<String, UserInfo> userList = new HashMap<>();
    private HashMap<Long, Integer> recvFirstIFrameCntList = new HashMap<>();
    public boolean muteLocalVideo = false;
    public boolean muteLocalSubVideo = false;

    public static class RenderInfo implements SurfaceHolder.Callback {
        public int lastVideoStatusChangeOperation;
        public int lastVideoStreamStatus;
        public int mirrorType;
        public TRTCRemoteMuteState muteAudio;
        public TRTCRemoteMuteState muteVideo;
        public int renderMode;
        public int rotation;
        public long tinyID;
        public TXCRenderAndDec render = null;
        public Boolean startRenderView = Boolean.FALSE;
        public TXCloudVideoView view = null;

        public RenderInfo() {
            TRTCRemoteMuteState tRTCRemoteMuteState = TRTCRemoteMuteState.UNSET;
            this.muteVideo = tRTCRemoteMuteState;
            this.muteAudio = tRTCRemoteMuteState;
            this.mirrorType = 0;
            this.rotation = 0;
            this.renderMode = 0;
            this.lastVideoStreamStatus = 0;
            this.lastVideoStatusChangeOperation = 0;
        }

        public void stop() {
            try {
                TXCloudVideoView tXCloudVideoView = this.view;
                if (tXCloudVideoView == null || tXCloudVideoView.getSurfaceView() == null || this.view.getSurfaceView().getHolder() == null) {
                    return;
                }
                this.view.getSurfaceView().getHolder().removeCallback(this);
            } catch (Exception e2) {
                TXCLog.e(TRTCRoomInfo.TAG, "remove callback failed.", e2);
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
            TXCLog.i("RenderInfo", "trtc_api startRemoteView surfaceChanged " + surfaceHolder.getSurface() + " width " + i3 + ", height " + i4 + ", " + this.tinyID + ", " + this.render);
            TXCRenderAndDec tXCRenderAndDec = this.render;
            e videoRender = tXCRenderAndDec != null ? tXCRenderAndDec.getVideoRender() : null;
            if (videoRender != null) {
                videoRender.d(i3, i4);
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            TXCLog.i("RenderInfo", "trtc_api startRemoteView surfaceCreated " + surfaceHolder.getSurface() + ", " + this.tinyID + ", " + this.render);
            if (surfaceHolder.getSurface().isValid()) {
                TXCRenderAndDec tXCRenderAndDec = this.render;
                e videoRender = tXCRenderAndDec != null ? tXCRenderAndDec.getVideoRender() : null;
                if (videoRender != null) {
                    videoRender.a(surfaceHolder.getSurface());
                }
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            TXCLog.i("RenderInfo", "trtc_api startRemoteView surfaceDestroyed " + surfaceHolder.getSurface() + ", " + this.tinyID + ", " + this.render);
            TXCRenderAndDec tXCRenderAndDec = this.render;
            e videoRender = tXCRenderAndDec != null ? tXCRenderAndDec.getVideoRender() : null;
            if (videoRender != null) {
                videoRender.a((Surface) null);
            }
        }
    }

    public enum TRTCRemoteMuteState {
        UNSET,
        MUTE,
        UNMUTE
    }

    public interface UserAction {
        void accept(String str, UserInfo userInfo);
    }

    public static class UserInfo {
        public int streamState;
        public int terminalType;
        public long tinyID;
        public String userID;
        public RenderInfo mainRender = new RenderInfo();
        public RenderInfo subRender = new RenderInfo();
        public int streamType = 2;
        public TRTCCloud.TRTCViewMargin debugMargin = new TRTCCloud.TRTCViewMargin(0.0f, 0.0f, 0.1f, 0.0f);
        public boolean muteAudioInSpeaker = false;

        public UserInfo(long j2, String str, int i2, int i3) {
            this.tinyID = j2;
            this.userID = str;
            this.terminalType = i2;
            this.streamState = i3;
            this.mainRender.tinyID = j2;
            this.subRender.tinyID = j2;
        }
    }

    public TRTCRoomInfo() {
        TRTCRemoteMuteState tRTCRemoteMuteState = TRTCRemoteMuteState.UNSET;
        this.muteRemoteVideo = tRTCRemoteMuteState;
        this.muteLocalAudio = false;
        this.muteRemoteAudio = tRTCRemoteMuteState;
        this.localRenderRotation = 0;
        this.micHasStartd = false;
        this.hasExitedRoom = false;
        this.exitRoomCode = 0;
        this.decProperties = null;
        this.enableRestartDecoder = false;
        this.bigEncSize = new g.a();
        this.smallEncSize = new g.a();
    }

    private String byteArrayToHexStr(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        char[] charArray = BinTools.hex.toCharArray();
        char[] cArr = new char[bArr.length * 2];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int i3 = bArr[i2] & 255;
            int i4 = i2 * 2;
            cArr[i4] = charArray[i3 >>> 4];
            cArr[i4 + 1] = charArray[i3 & 15];
        }
        return new String(cArr);
    }

    public static boolean hasAudio(int i2) {
        return (i2 & 8) != 0;
    }

    public static boolean hasMainVideo(int i2) {
        return (i2 & 1) != 0;
    }

    public static boolean hasSmallVideo(int i2) {
        return (i2 & 2) != 0;
    }

    public static boolean hasSubVideo(int i2) {
        return (i2 & 4) != 0;
    }

    private byte[] hexStrToByteArray(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[0];
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            bArr[i2] = (byte) Integer.parseInt(str.substring(i3, i3 + 2), 16);
        }
        return bArr;
    }

    public static boolean isMuteAudio(int i2) {
        return (i2 & 64) != 0;
    }

    public static boolean isMuteMainVideo(int i2) {
        return (i2 & 16) != 0;
    }

    public static boolean isMuteSubVideo(int i2) {
        return (i2 & 32) != 0;
    }

    public synchronized void addUserInfo(String str, UserInfo userInfo) {
        this.userList.put(str, userInfo);
    }

    public synchronized void clear() {
        this.roomId = 0L;
        this.userId = "";
        this.enterTime = 0L;
        this.tinyId = "";
        this.muteLocalVideo = false;
        this.muteLocalAudio = false;
        TRTCRemoteMuteState tRTCRemoteMuteState = TRTCRemoteMuteState.UNSET;
        this.muteRemoteVideo = tRTCRemoteMuteState;
        this.muteRemoteAudio = tRTCRemoteMuteState;
        this.userList.clear();
        this.recvFirstIFrameCntList.clear();
        this.networkStatus = 1;
        this.decProperties = null;
        this.enableRestartDecoder = false;
        this.enableCustomPreprocessor = false;
        this.localListener = null;
    }

    public synchronized void clearUserList() {
        this.userList.clear();
        this.recvFirstIFrameCntList.clear();
    }

    public void forEachUser(UserAction userAction) {
        HashMap map = new HashMap(this.userList.size());
        synchronized (this) {
            map.putAll(this.userList);
        }
        for (Map.Entry entry : map.entrySet()) {
            if (userAction != null && entry.getValue() != null) {
                userAction.accept((String) entry.getKey(), (UserInfo) entry.getValue());
            }
        }
    }

    public long getRoomElapsed() {
        return System.currentTimeMillis() - this.enterTime;
    }

    public synchronized int getRoomExitCode() {
        return this.exitRoomCode;
    }

    public long getRoomId() {
        return this.roomId;
    }

    public String getStrRoomId() {
        return TextUtils.isEmpty(this.strRoomId) ? String.valueOf(this.roomId) : this.strRoomId;
    }

    public String getTinyId() {
        return this.tinyId;
    }

    public byte[] getToken(Context context) {
        try {
            if (this.token == null) {
                this.token = hexStrToByteArray(context.getSharedPreferences(TRTC_INFO, 0).getString(TOKEN, ""));
            }
        } catch (Exception e2) {
            TXCLog.e(TAG, "get token failed.", e2);
        }
        return this.token;
    }

    public synchronized UserInfo getUser(String str) {
        return this.userList.get(str);
    }

    public String getUserId() {
        return this.userId;
    }

    public synchronized String getUserIdByTinyId(long j2) {
        Iterator<Map.Entry<String, UserInfo>> it = this.userList.entrySet().iterator();
        while (it.hasNext()) {
            UserInfo value = it.next().getValue();
            if (value != null && value.tinyID == j2) {
                return value.userID;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x001f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean hasRecvFirstIFrame(long r3, int r5) {
        /*
            r2 = this;
            monitor-enter(r2)
            r0 = 7
            if (r5 == r0) goto L5
            r5 = 2
        L5:
            r0 = 4
            long r3 = r3 << r0
            long r0 = (long) r5
            long r3 = r3 + r0
            java.util.HashMap<java.lang.Long, java.lang.Integer> r5 = r2.recvFirstIFrameCntList     // Catch: java.lang.Throwable -> L22
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch: java.lang.Throwable -> L22
            java.lang.Object r3 = r5.get(r3)     // Catch: java.lang.Throwable -> L22
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch: java.lang.Throwable -> L22
            if (r3 == 0) goto L1f
            int r3 = r3.intValue()     // Catch: java.lang.Throwable -> L22
            if (r3 <= 0) goto L1f
            r3 = 1
            goto L20
        L1f:
            r3 = 0
        L20:
            monitor-exit(r2)
            return r3
        L22:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.trtc.impl.TRTCRoomInfo.hasRecvFirstIFrame(long, int):boolean");
    }

    public void init(long j2, String str) {
        this.roomId = j2;
        this.userId = str;
    }

    public synchronized boolean isMicStard() {
        return this.micHasStartd;
    }

    public synchronized boolean isRoomExit() {
        return this.hasExitedRoom;
    }

    public void micStart(boolean z2) {
        this.micHasStartd = z2;
    }

    public String query(Context context, String str) {
        try {
            return context.getSharedPreferences(TRTC_INFO, 0).getString(str, "");
        } catch (Exception e2) {
            TXCLog.e(TAG, "Query value failed.  key:%s err:%s", str, e2.getMessage());
            return "";
        }
    }

    public synchronized int recvFirstIFrame(long j2, int i2) {
        int iIntValue;
        if (i2 != 7) {
            i2 = 2;
        }
        long j3 = (j2 << 4) + i2;
        Integer num = this.recvFirstIFrameCntList.get(Long.valueOf(j3));
        iIntValue = 1;
        this.recvFirstIFrameCntList.put(Long.valueOf(j3), Integer.valueOf(num == null ? 1 : num.intValue() + 1));
        if (num != null) {
            iIntValue = 1 + num.intValue();
        }
        return iIntValue;
    }

    public synchronized void removeRenderInfo(String str) {
        UserInfo userInfo = this.userList.get(str);
        this.recvFirstIFrameCntList.remove(Long.valueOf((userInfo.tinyID << 4) + 2));
        this.recvFirstIFrameCntList.remove(Long.valueOf((userInfo.tinyID << 4) + 7));
        this.userList.remove(str);
    }

    public synchronized void setRoomExit(boolean z2, int i2) {
        this.hasExitedRoom = z2;
        this.exitRoomCode = i2;
    }

    public void setRoomId(int i2) {
        this.roomId = i2;
    }

    public void setTinyId(String str) {
        this.tinyId = str;
    }

    public void setToken(Context context, byte[] bArr) {
        this.token = bArr;
        try {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences(TRTC_INFO, 0).edit();
            byte[] bArr2 = this.token;
            if (bArr2 != null) {
                editorEdit.putString(TOKEN, byteArrayToHexStr(bArr2));
            } else {
                editorEdit.remove(TOKEN);
            }
            editorEdit.commit();
        } catch (Exception e2) {
            TXCLog.e(TAG, "set token failed.", e2);
        }
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public void store(Context context, String str, String str2) {
        try {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences(TRTC_INFO, 0).edit();
            if (TextUtils.isEmpty(str2)) {
                editorEdit.remove(str);
            } else {
                editorEdit.putString(str, str2);
            }
            editorEdit.apply();
        } catch (Exception e2) {
            Object[] objArr = new Object[3];
            objArr[0] = str;
            if (str2 == null) {
                str2 = "null";
            }
            objArr[1] = str2;
            objArr[2] = e2.getMessage();
            TXCLog.e(TAG, "Store key/value failed. key:%s value:%s err:%s", objArr);
        }
    }
}

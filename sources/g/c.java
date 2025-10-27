package g;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import c.h;
import c.i;
import cn.hutool.core.text.StrPool;
import com.aliyun.auth.core.AliyunVodKey;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.umeng.analytics.pro.am;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import core.data.AuthInfo;
import core.data.MediaOp;
import core.data.MixProfile;
import core.data.RecordProfile;
import core.data.StreamInfo;
import core.monitor.LogReportManager;
import d.a;
import d.b;
import e.b.c;
import e.g;
import f.i;
import f.j;
import f.k;
import g.e;
import h.f;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wrtca.api.VideoRenderer;
import org.wrtca.api.VideoSink;

/* loaded from: classes8.dex */
public class c implements g.b {

    /* renamed from: b, reason: collision with root package name */
    public static final String f26936b = "LogicCoreModule";

    /* renamed from: a, reason: collision with root package name */
    public e.b f26937a;

    public class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ JSONObject f26938a;

        public a(JSONObject jSONObject) {
            this.f26938a = jSONObject;
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            try {
                boolean z2 = c.this.f26937a.x().a() == 0;
                int iB = z2 ? -1 : c.this.f26937a.x().b();
                String str = z2 ? "rsugetrtcgateway" : "rsugetlivegateway";
                for (int i2 = 0; i2 < 4; i2++) {
                    c cVar = c.this;
                    String strA = cVar.a(cVar.f26937a.u().getAppId(), c.this.f26937a.u().getRoomId(), c.this.f26937a.u().getUId(), str, iB);
                    if (strA != null) {
                        h.a(c.f26936b, " reqRegionAddr result: " + strA);
                        if (c.this.f26937a.e() != null) {
                            Message messageObtainMessage = c.this.f26937a.e().obtainMessage();
                            messageObtainMessage.what = f.a.BUSINESS_EVENT_GETREGION_ADDR.ordinal();
                            Bundle bundle = new Bundle();
                            bundle.putString("msgdata", strA);
                            if (this.f26938a.has(e.f0.U)) {
                                bundle.putBoolean(e.f0.U, true);
                            }
                            messageObtainMessage.setData(bundle);
                            c.this.f26937a.e().sendMessage(messageObtainMessage);
                            return;
                        }
                        return;
                    }
                    h.a(c.f26936b, "rejoin failed for can not get serverurl times " + i2);
                    if (c.this.f26937a.h() != null && i2 == 3) {
                        if (this.f26938a.has(e.f0.U)) {
                            h.a(c.f26936b, "rejoin failed for can not get serverurl");
                        } else {
                            c.this.f26937a.a(f.h.LOGIC_ENGINE_INIT);
                            JSONObject jSONObject = new JSONObject();
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject.put("code", 5000);
                            jSONObject.put("msg", "can not get server url");
                            jSONObject2.put("uid", c.this.f26937a.u().getUId());
                            jSONObject2.put("roomid", c.this.f26937a.u().getRoomId());
                            jSONObject.put("data", jSONObject2);
                            c.this.f26937a.h().F(jSONObject.toString());
                        }
                    }
                    Thread.sleep(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ JSONObject f26940a;

        public b(JSONObject jSONObject) {
            this.f26940a = jSONObject;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            try {
                if (this.f26940a.get("data") != null) {
                    JSONObject jSONObject = this.f26940a.getJSONObject("data");
                    jSONObject.put(am.f22460w, f.e.g().d());
                    jSONObject.put("memory", f.e.g().j());
                    if (!jSONObject.has("video")) {
                        jSONObject.put("video", LogReportManager.getInstance().createStatusTrackDataDefault(0));
                    }
                    if (!jSONObject.has("audio")) {
                        jSONObject.put("audio", LogReportManager.getInstance().createStatusTrackDataDefault(1));
                    }
                    if (jSONObject.has("delayError")) {
                        jSONObject.remove("delayError");
                    }
                    if (jSONObject.has("lostpreError")) {
                        jSONObject.remove("lostpreError");
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            LogReportManager.getInstance().sendLog(2, this.f26940a.toString(), null);
        }
    }

    /* renamed from: g.c$c, reason: collision with other inner class name */
    public static /* synthetic */ class C0449c {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f26942a;

        /* renamed from: b, reason: collision with root package name */
        public static final /* synthetic */ int[] f26943b;

        static {
            int[] iArr = new int[f.a.values().length];
            f26943b = iArr;
            try {
                iArr[f.a.BUSINESS_EVENT_SWITCHCAMERA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_SET_CAMERA_ID.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_STARTPREVIEW.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_STOPPREVIEW.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_JOINROOM.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_LEAVEROOM.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_FLASH_LIGHT_ON.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_FLASH_LIGHT_OFF.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_OP_VIDEO_MODULE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_CROP_PUSH_RESOLUTION.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_MEDIASERVER_CON_SUC.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_MEDIASERVER_CON_FAIL.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_MEDIASERVER_RECONING.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_MEDIASERVER_RECONED.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_MEDIASERVER_DISCON.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_GETREGION_ADDR.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_GETSINGNAL_ADDR.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_GETTEST_ROOMTOKEN.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_MESSAGE_FROM_SIGNAL.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_PUSHSTREAM.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_UNPUSHSTREAM.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_CREATESDP_SUC.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_ICE_STATE.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_MUTE_LOCALAUDIO.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_MUTE_LOCALVIDEO.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_MUTE_LOCALSCREEN.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_MUTE_REMOTEAUDIO.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_MUTE_REMOTEVIDEO.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_MUTE_REMOTESCREEN.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_STARTREMOTEPREVIEW.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_STOPREMOTEPREVIEW.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_SUBSCRIBESTREAM.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_UNSUBSCRIBESTREAM.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_LOCAL_QUALITY.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_REMOTE_QUALITY.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_REMOTE_VOL.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_LOCAL_VOL.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_MESSAGE_ERROR.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_PEER_STATS_UPDATE.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_START_RECORD.ordinal()] = 40;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_STOP_RECORD.ordinal()] = 41;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_QUERY_MIX.ordinal()] = 42;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_NOTIFY_MSG.ordinal()] = 43;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_OP_AUDIO_MODULE.ordinal()] = 44;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_OP_AUDIO_PLAYOUT.ordinal()] = 45;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_OP_AUDIO_RECORD.ordinal()] = 46;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_START_MIX.ordinal()] = 47;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_STOP_MIX.ordinal()] = 48;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_ADD_MIX.ordinal()] = 49;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_DEL_MIX.ordinal()] = 50;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_RECON_LEAVE_ROOM.ordinal()] = 51;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_KICKOFF_OTHERS.ordinal()] = 52;
            } catch (NoSuchFieldError unused52) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_LOGOFF_NOTIFY.ordinal()] = 53;
            } catch (NoSuchFieldError unused53) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_ADJUST_PUSH_RESOLUTION.ordinal()] = 54;
            } catch (NoSuchFieldError unused54) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_START_PLAY_AUDIO_FILE.ordinal()] = 55;
            } catch (NoSuchFieldError unused55) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_STOP_PLAY_AUDIO_FILE.ordinal()] = 56;
            } catch (NoSuchFieldError unused56) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_RESUME_AUDIO_FILE.ordinal()] = 57;
            } catch (NoSuchFieldError unused57) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_PAUSE_AUDIO_FILE.ordinal()] = 58;
            } catch (NoSuchFieldError unused58) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_AUDIO_FILE_FINISH.ordinal()] = 59;
            } catch (NoSuchFieldError unused59) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_PING_FOR_ROOM_VERSION.ordinal()] = 60;
            } catch (NoSuchFieldError unused60) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_SYNC_ROOM_INFO.ordinal()] = 61;
            } catch (NoSuchFieldError unused61) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_ADJUST_USER_PLAYBACK_VOLUME.ordinal()] = 62;
            } catch (NoSuchFieldError unused62) {
            }
            try {
                f26943b[f.a.BUSINESS_EVENT_ADJUST_PLAYBACK_VOLUME.ordinal()] = 63;
            } catch (NoSuchFieldError unused63) {
            }
            int[] iArr2 = new int[f.h.values().length];
            f26942a = iArr2;
            try {
                iArr2[f.h.LOGIC_ENGINE_IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused64) {
            }
            try {
                f26942a[f.h.LOGIC_ENGINE_INIT.ordinal()] = 2;
            } catch (NoSuchFieldError unused65) {
            }
            try {
                f26942a[f.h.LOGIC_ENGINE_CONNECTING.ordinal()] = 3;
            } catch (NoSuchFieldError unused66) {
            }
            try {
                f26942a[f.h.LOGIC_ENGINE_CONNECTED.ordinal()] = 4;
            } catch (NoSuchFieldError unused67) {
            }
            try {
                f26942a[f.h.LOGIC_ENGINE_RECONNECTING.ordinal()] = 5;
            } catch (NoSuchFieldError unused68) {
            }
            try {
                f26942a[f.h.LOGIC_ENGINE_ROOM_CLOSED.ordinal()] = 6;
            } catch (NoSuchFieldError unused69) {
            }
        }
    }

    public c(e.h hVar) {
        this.f26937a = hVar;
    }

    public void A(f.f fVar) throws JSONException {
        try {
            int i2 = new JSONObject(fVar.f26898a).getInt("mtype");
            if (i2 == 1) {
                y(fVar);
            } else if (i2 == 2) {
                z(fVar);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void B(f.f fVar) throws JSONException {
        if (fVar != null) {
            try {
                j.d.d().a(new JSONObject(fVar.f26898a).getInt("camera_id"));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public final void C(f.f fVar) throws JSONException {
        if (this.f26937a != null) {
            Log.d(f26936b, "startPlayAudioFileInner ");
            try {
                JSONObject jSONObject = new JSONObject(fVar.f26898a);
                this.f26937a.a(jSONObject.getString("file_path"), jSONObject.getBoolean("is_remote_play"), jSONObject.getBoolean("is_loop"));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void D(f.f fVar) throws JSONException {
        boolean z2;
        if (fVar != null) {
            try {
                int i2 = new JSONObject(fVar.f26898a).getInt("mtype");
                if (fVar.f26899b instanceof VideoSink) {
                    if (d.b.b() == 2) {
                        h.a(f26936b, "audio video module = video only");
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    String strA = this.f26937a.a(i2, (VideoSink) fVar.f26899b, true, z2);
                    if (TextUtils.isEmpty(strA)) {
                        return;
                    }
                    this.f26937a.h().g(strA);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void E(f.f fVar) throws JSONException {
        if (this.f26937a.m() == null || !(this.f26937a.m() instanceof h.e)) {
            return;
        }
        String strA = c.e.a();
        String uId = this.f26937a.u().getUId();
        try {
            JSONObject jSONObject = new JSONObject(fVar.f26898a);
            int i2 = jSONObject.getInt("recordType");
            int i3 = jSONObject.getInt("mediaType");
            String string = jSONObject.getString(TtmlNode.TAG_REGION);
            String string2 = jSONObject.getString("bucket");
            int i4 = jSONObject.getInt("resolution");
            boolean z2 = jSONObject.getBoolean("isaverage");
            int i5 = jSONObject.getInt("waterpos");
            int i6 = jSONObject.getInt("waterType");
            String string3 = jSONObject.getString("waterurl");
            int i7 = jSONObject.getInt("template");
            String string4 = jSONObject.has("mainviewUserId") ? jSONObject.getString("mainviewUserId") : "";
            String uId2 = TextUtils.isEmpty(string4) ? this.f26937a.u().getUId() : string4;
            int[] iArr = new int[2];
            if (i4 == 11) {
                iArr[0] = 1280;
                iArr[1] = 720;
            } else if (i4 == 12) {
                iArr[0] = 1920;
                iArr[1] = 1080;
            } else if (i4 == 10) {
                iArr[0] = 640;
                iArr[1] = 360;
            } else if (i4 == 9) {
                iArr[0] = this.f26937a.w().f();
                iArr[1] = this.f26937a.w().b();
            } else {
                iArr[0] = 640;
                iArr[1] = 480;
            }
            ((h.e) this.f26937a.m()).a(strA, this.f26937a.u().getRoomId(), this.f26937a.u().getAppId(), i2, uId, uId2, i3, string2, string, iArr[0], iArr[1], z2, i5, i6, string3, i7);
            e.b bVar = this.f26937a;
            bVar.a(strA, bVar.i().a(i.f2282m));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void F(f.f fVar) throws JSONException {
        try {
            h.a(f26936b, " startrmeotepreview inner " + fVar);
            if (fVar != null) {
                JSONObject jSONObject = new JSONObject(fVar.f26898a);
                int i2 = jSONObject.getInt("mtype");
                String string = jSONObject.getString("uid");
                if (fVar.f26899b instanceof VideoRenderer.Callbacks) {
                    h.a(f26936b, "startremoteview " + string + " mediatype " + i2);
                    this.f26937a.a(i2, string, (VideoRenderer.Callbacks) fVar.f26899b);
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void G(f.f fVar) throws JSONException {
        if (fVar != null) {
            try {
                int i2 = new JSONObject(fVar.f26898a).getInt("mtype");
                Object obj = fVar.f26899b;
                if (obj == null) {
                    this.f26937a.a(i2, false);
                } else {
                    this.f26937a.a(i2, (VideoSink) obj, false);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void H(f.f fVar) throws JSONException {
        if (fVar != null) {
            try {
                JSONObject jSONObject = new JSONObject(fVar.f26898a);
                int i2 = jSONObject.getInt("mtype");
                String string = jSONObject.getString("uid");
                h.a(f26936b, " stopRemotePreviewInner " + i2 + " uid: " + string);
                Object obj = fVar.f26899b;
                if (obj == null) {
                    this.f26937a.a(i2, string, false);
                } else {
                    this.f26937a.a(i2, string, false, (VideoRenderer.Callbacks) obj);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void I(f.f fVar) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject(fVar.f26898a);
            int i2 = jSONObject.getInt("mtype");
            String string = jSONObject.getString("uid");
            if (this.f26937a.y().c(string) == null) {
                if (this.f26937a.h() != null) {
                    JSONObject jSONObject2 = new JSONObject();
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject2.put("code", 5012);
                    jSONObject2.put("msg", "not have this user");
                    jSONObject3.put("mtype", i2);
                    jSONObject3.put("uid", string);
                    jSONObject2.put("data", jSONObject3);
                    this.f26937a.h().H(jSONObject2.toString());
                    return;
                }
                return;
            }
            String strA = this.f26937a.y().a(string + a.h.a(i2));
            if (strA != null && !strA.equals("")) {
                if (this.f26937a.D().get(strA) != null) {
                    if (this.f26937a.h() != null) {
                        JSONObject jSONObject4 = new JSONObject();
                        JSONObject jSONObject5 = new JSONObject();
                        jSONObject4.put("code", 5036);
                        jSONObject4.put("msg", "already sub this stream");
                        jSONObject5.put("mtype", i2);
                        jSONObject5.put("uid", string);
                        jSONObject4.put("data", jSONObject5);
                        this.f26937a.h().H(jSONObject4.toString());
                        return;
                    }
                    return;
                }
                if (this.f26937a.m() == null) {
                    if (this.f26937a.h() != null) {
                        JSONObject jSONObject6 = new JSONObject();
                        JSONObject jSONObject7 = new JSONObject();
                        jSONObject6.put("code", 5001);
                        jSONObject6.put("msg", "server diaconnect");
                        jSONObject7.put("mtype", i2);
                        jSONObject7.put("uid", string);
                        jSONObject6.put("data", jSONObject7);
                        this.f26937a.h().H(jSONObject6.toString());
                        return;
                    }
                    return;
                }
                boolean z2 = jSONObject.getBoolean("audio");
                boolean z3 = jSONObject.getBoolean("video");
                boolean z4 = jSONObject.getBoolean("muteaudio");
                boolean z5 = jSONObject.getBoolean("mutevideo");
                String strA2 = c.e.a();
                h.a(f26936b, "subscribeStreamInner: " + strA + "hasAudio: " + z2 + "hasVideo: " + z3);
                try {
                    ((h.e) this.f26937a.m()).a(strA2, this.f26937a.u().getUId(), string, strA, i2, z2, z3);
                    e.d dVar = new e.d();
                    dVar.f26761c = string;
                    dVar.f26764f = j.STREAM_STATUS_INIT;
                    dVar.f26766h = z2;
                    dVar.f26765g = z3;
                    dVar.f26767i = z4;
                    dVar.f26768j = z5;
                    dVar.f26763e = i2;
                    dVar.f26762d = 2;
                    dVar.f26769k = false;
                    dVar.f26770l = false;
                    dVar.f26760b = strA;
                    h.a(f26936b, "put stream map client stream info " + dVar);
                    this.f26937a.D().put(strA, dVar);
                    e.b bVar = this.f26937a;
                    bVar.a(strA2, bVar.i().a(i.f2277h));
                    return;
                } catch (JSONException e2) {
                    e = e2;
                    e.printStackTrace();
                    return;
                }
            }
            if (this.f26937a.h() != null) {
                JSONObject jSONObject8 = new JSONObject();
                JSONObject jSONObject9 = new JSONObject();
                jSONObject8.put("code", 5013);
                jSONObject8.put("msg", "not have this media");
                jSONObject9.put("mtype", i2);
                jSONObject9.put("uid", string);
                jSONObject8.put("data", jSONObject9);
                this.f26937a.h().H(jSONObject8.toString());
            }
        } catch (JSONException e3) {
            e = e3;
        }
    }

    public void J(f.f fVar) throws JSONException {
        if (fVar != null) {
            try {
                j.d.d().k(new JSONObject(fVar.f26898a).getBoolean("skip_flag"));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void K(f.f fVar) throws JSONException {
        try {
            int i2 = new JSONObject(fVar.f26898a).getInt("mtype");
            String str = "";
            if (i2 == 1) {
                if (this.f26937a.v() == null) {
                    h.c(f26936b, "getCamClient is null!");
                    return;
                } else {
                    str = this.f26937a.v().f26759a;
                    this.f26937a.v().f26769k = true;
                }
            } else if (i2 == 2) {
                if (this.f26937a.B() == null) {
                    h.c(f26936b, "getCamClient is null!");
                    return;
                } else {
                    str = this.f26937a.B().f26759a;
                    this.f26937a.B().f26769k = true;
                }
            }
            String strA = c.e.a();
            if (this.f26937a.m() != null) {
                ((h.e) this.f26937a.m()).a(strA, this.f26937a.u().getUId(), str, i2);
                e.b bVar = this.f26937a;
                bVar.a(strA, bVar.i().a(i.f2274e));
            } else if (this.f26937a.h() != null) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("code", 5037);
                jSONObject.put("msg", "not join room");
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("mtype", i2);
                jSONObject.put("data", jSONObject2);
                this.f26937a.h().D(jSONObject.toString());
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void L(f.f fVar) throws JSONException {
        String str = "";
        try {
            JSONObject jSONObject = new JSONObject(fVar.f26898a);
            int i2 = jSONObject.getInt("mtype");
            String string = jSONObject.getString("uid");
            if (this.f26937a.y().c(string) == null) {
                if (this.f26937a.h() != null) {
                    JSONObject jSONObject2 = new JSONObject();
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject2.put("code", 5012);
                    jSONObject2.put("msg", "not have this user");
                    jSONObject3.put("mtype", i2);
                    jSONObject3.put("uid", string);
                    jSONObject2.put("data", jSONObject3);
                    this.f26937a.h().C(jSONObject2.toString());
                    return;
                }
                return;
            }
            String strA = this.f26937a.y().a(string + a.h.a(i2));
            if (strA != null && !strA.equals("")) {
                e.d dVarRemove = this.f26937a.D().remove(strA);
                if (dVarRemove != null) {
                    str = dVarRemove.f26759a;
                    h.a(f26936b, "mBusinessEngine.getSubPubMap().remove" + str);
                    this.f26937a.E().remove(str);
                }
                String str2 = str;
                if (this.f26937a.m() != null) {
                    String strA2 = c.e.a();
                    ((h.e) this.f26937a.m()).a(strA2, this.f26937a.u().getUId(), string, strA, str2, i2);
                    e.b bVar = this.f26937a;
                    bVar.a(strA2, bVar.i().a(i.f2278i));
                    return;
                }
                if (this.f26937a.h() != null) {
                    JSONObject jSONObject4 = new JSONObject();
                    JSONObject jSONObject5 = new JSONObject();
                    jSONObject4.put("code", 5001);
                    jSONObject4.put("msg", "server disconnect");
                    jSONObject5.put("mtype", i2);
                    jSONObject5.put("uid", string);
                    jSONObject4.put("data", jSONObject5);
                    this.f26937a.h().C(jSONObject4.toString());
                    return;
                }
                return;
            }
            if (this.f26937a.h() != null) {
                JSONObject jSONObject6 = new JSONObject();
                JSONObject jSONObject7 = new JSONObject();
                jSONObject6.put("code", 5013);
                jSONObject6.put("msg", "not have this media");
                jSONObject7.put("mtype", i2);
                jSONObject7.put("uid", string);
                jSONObject6.put("data", jSONObject7);
                this.f26937a.h().C(jSONObject6.toString());
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // g.b
    public void a(int i2, Object obj) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("mtype", i2);
            messageObtainMessage.what = f.a.BUSINESS_EVENT_STOPPREVIEW.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), obj);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void adjustPlaybackSignalVolume(double d3) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("volume", d3);
            Message messageObtainMessage = this.f26937a.e().obtainMessage();
            messageObtainMessage.what = f.a.BUSINESS_EVENT_ADJUST_PLAYBACK_VOLUME.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            e.b bVar = this.f26937a;
            Objects.requireNonNull(bVar);
            bVar.new c(messageObtainMessage).a();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // g.b
    public void adjustUserPlaybackSignalVolume(String str, double d3) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("user_id", str);
            jSONObject.put("volume", d3);
            Message messageObtainMessage = this.f26937a.e().obtainMessage();
            messageObtainMessage.what = f.a.BUSINESS_EVENT_ADJUST_USER_PLAYBACK_VOLUME.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            e.b bVar = this.f26937a;
            Objects.requireNonNull(bVar);
            bVar.new c(messageObtainMessage).a();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // g.b
    public void b(int i2, Object obj) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("mtype", i2);
            messageObtainMessage.what = f.a.BUSINESS_EVENT_STARTPREVIEW.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), obj);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            h.a(f26936b, " send message start local view ");
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void c(int i2, Object obj) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("mtype", i2);
            messageObtainMessage.what = f.a.BUSINESS_EVENT_UNPUSHSTREAM.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), obj);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void cropPushResolution(int i2, int i3) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("crop_x", i2);
            jSONObject.put("crop_y", i3);
            Message messageObtainMessage = this.f26937a.e().obtainMessage();
            messageObtainMessage.what = f.a.BUSINESS_EVENT_CROP_PUSH_RESOLUTION.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            e.b bVar = this.f26937a;
            Objects.requireNonNull(bVar);
            bVar.new c(messageObtainMessage).a();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // g.b
    public void d(boolean z2) throws JSONException {
        Handler handlerE = this.f26937a.e();
        f.a aVar = f.a.BUSINESS_EVENT_LEAVEROOM;
        Message messageObtainMessage = handlerE.obtainMessage(aVar.ordinal());
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("renderflag", z2);
            h.a(h.c.BUNDLE_LEAVE_ROOM, this.f26937a.u().toString());
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            messageObtainMessage.what = aVar.ordinal();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void e(boolean z2) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("mtype", 2);
            jSONObject.put("ttype", 2);
            jSONObject.put(i.f2279j, z2);
            messageObtainMessage.what = f.a.BUSINESS_EVENT_MUTE_LOCALSCREEN.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void f(boolean z2) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("enable", z2);
            Message messageObtainMessage = this.f26937a.e().obtainMessage();
            messageObtainMessage.what = f.a.BUSINESS_EVENT_OP_VIDEO_MODULE.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            e.b bVar = this.f26937a;
            Objects.requireNonNull(bVar);
            bVar.new c(messageObtainMessage).a();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final void g(f.f fVar) throws JSONException {
        try {
            boolean z2 = new JSONObject(fVar.f26898a).getBoolean("enable");
            if (this.f26937a.v() == null || TextUtils.isEmpty(this.f26937a.v().f26759a)) {
                return;
            }
            e.b bVar = this.f26937a;
            bVar.a(bVar.v().f26759a, z2);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final void h(f.f fVar) throws JSONException {
        try {
            boolean z2 = new JSONObject(fVar.f26898a).getBoolean("enable");
            Log.d(f26936b, "enableAudioPlayOut : " + z2);
            this.f26937a.a(z2);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) throws JSONException, NumberFormatException {
        if (message.what != f.a.BUSINESS_EVENT_LOCAL_VOL.ordinal() && message.what != f.a.BUSINESS_EVENT_REMOTE_VOL.ordinal()) {
            h.a(f26936b, "handleMessage info what: " + message.what + " info: " + f.a.a(message.what) + " room state: " + this.f26937a.z());
        }
        switch (C0449c.f26942a[this.f26937a.z().ordinal()]) {
            case 1:
                d(message);
                break;
            case 2:
                e(message);
                break;
            case 3:
                c(message);
                break;
            case 4:
                b(message);
                break;
            case 5:
                f(message);
                break;
            case 6:
                a(message);
                break;
        }
        return false;
    }

    public final void i() {
        if (this.f26937a != null) {
            Log.d(f26936b, "pauseAudioFileInner ");
            this.f26937a.K();
        }
    }

    public final void j(f.f fVar) throws JSONException {
        try {
            boolean z2 = new JSONObject(fVar.f26898a).getBoolean("enable");
            e.b bVar = this.f26937a;
            if (bVar != null) {
                bVar.c(z2);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final void k() {
        if (this.f26937a != null) {
            Log.d(f26936b, "resumeAudioFileInner ");
            this.f26937a.P();
        }
    }

    @Override // g.b
    public void kickOffOthers(int i2, List<String> list) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("cmdtype", i2);
            JSONArray jSONArray = new JSONArray();
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                jSONArray.put(it.next());
            }
            jSONObject.put("users", jSONArray);
            Message messageObtainMessage = this.f26937a.e().obtainMessage();
            messageObtainMessage.what = f.a.BUSINESS_EVENT_KICKOFF_OTHERS.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            e.b bVar = this.f26937a;
            Objects.requireNonNull(bVar);
            bVar.new c(messageObtainMessage).a();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final void l() throws JSONException {
        if (this.f26937a.m() == null || !(this.f26937a.m() instanceof h.e)) {
            return;
        }
        String strA = c.e.a();
        ((h.e) this.f26937a.m()).b(strA, this.f26937a.u().getAppId(), this.f26937a.u().getRoomId(), this.f26937a.u().getUId());
        e.b bVar = this.f26937a;
        bVar.a(strA, bVar.i().a("ping"));
    }

    public final void m() {
        if (this.f26937a != null) {
            Log.d(f26936b, "stopPlayAudioFileInner ");
            this.f26937a.R();
        }
    }

    @Override // g.b
    public void messageNotify(String str) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("content", str);
            Message messageObtainMessage = this.f26937a.e().obtainMessage();
            messageObtainMessage.what = f.a.BUSINESS_EVENT_NOTIFY_MSG.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            e.b bVar = this.f26937a;
            Objects.requireNonNull(bVar);
            bVar.new c(messageObtainMessage).a();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // g.b
    public void muteRemoteAudio(String str, boolean z2) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("uid", str);
            jSONObject.put("mtype", 1);
            jSONObject.put("ttype", 1);
            jSONObject.put(i.f2279j, z2);
            messageObtainMessage.what = f.a.BUSINESS_EVENT_MUTE_REMOTEAUDIO.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void muteRemoteScreen(String str, boolean z2) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("uid", str);
            jSONObject.put("mtype", 2);
            jSONObject.put("ttype", 2);
            jSONObject.put(i.f2279j, z2);
            messageObtainMessage.what = f.a.BUSINESS_EVENT_MUTE_REMOTESCREEN.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void muteRemoteScreenSound(String str, boolean z2) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("uid", str);
            jSONObject.put("mtype", 2);
            jSONObject.put("ttype", 1);
            jSONObject.put(i.f2279j, z2);
            messageObtainMessage.what = f.a.BUSINESS_EVENT_MUTE_REMOTEAUDIO.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void muteRemoteVideo(String str, boolean z2) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("uid", str);
            jSONObject.put("mtype", 1);
            jSONObject.put("ttype", 2);
            jSONObject.put(i.f2279j, z2);
            messageObtainMessage.what = f.a.BUSINESS_EVENT_MUTE_REMOTEVIDEO.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    public void n() throws JSONException {
        if (this.f26937a.m() == null || !(this.f26937a.m() instanceof h.e)) {
            return;
        }
        String strA = c.e.a();
        ((h.e) this.f26937a.m()).c(strA, this.f26937a.u().getRoomId(), this.f26937a.u().getAppId(), this.f26937a.u().getUId());
        e.b bVar = this.f26937a;
        bVar.a(strA, bVar.i().a(i.f2283n));
    }

    public final void o() throws JSONException {
        if (this.f26937a.m() == null || !(this.f26937a.m() instanceof h.e)) {
            return;
        }
        String strA = c.e.a();
        ((h.e) this.f26937a.m()).d(strA, this.f26937a.u().getAppId(), this.f26937a.u().getRoomId(), this.f26937a.u().getUId());
        e.b bVar = this.f26937a;
        bVar.a(strA, bVar.i().a(i.f2291v));
    }

    public final void p(f.f fVar) throws JSONException {
        if (this.f26937a.m() == null || !(this.f26937a.m() instanceof h.e)) {
            return;
        }
        try {
            String string = new JSONObject(fVar.f26898a).getString("content");
            String strA = c.e.a();
            ((h.e) this.f26937a.m()).c(strA, this.f26937a.u().getUId(), this.f26937a.u().getRoomId(), this.f26937a.u().getAppId(), string);
            e.b bVar = this.f26937a;
            bVar.a(strA, bVar.i().a(i.f2284o));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // g.b
    public void pauseAudioFile() {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        messageObtainMessage.what = f.a.BUSINESS_EVENT_PAUSE_AUDIO_FILE.ordinal();
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    public void q(f.f fVar) throws JSONException {
        int i2;
        int i3;
        if (this.f26937a.m() != null) {
            String strA = c.e.a();
            String strEncodeToString = "";
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, this.f26937a.F().l());
                jSONObject.put("agent", this.f26937a.F().b());
                jSONObject.put(com.alipay.sdk.packet.d.f3298n, this.f26937a.F().e());
                jSONObject.put("system", this.f26937a.F().m());
                jSONObject.put("network", this.f26937a.F().k());
                jSONObject.put(am.f22460w, this.f26937a.F().c());
                jSONObject.put("mem", this.f26937a.F().h());
                String string = jSONObject.toString();
                h.a(f26936b, "deviceinfo: " + string);
                strEncodeToString = Base64.encodeToString(string.getBytes(), 2);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            String str = strEncodeToString;
            int iB = this.f26937a.x().b();
            int iA = this.f26937a.x().a();
            if (iA == 0) {
                i2 = -1;
                i3 = -1;
            } else {
                i2 = iB;
                i3 = iA;
            }
            ((h.e) this.f26937a.m()).a(strA, this.f26937a.u().getUId(), this.f26937a.u().getRoomId(), this.f26937a.u().getAppId(), "", this.f26937a.u().getToken(), str, i2, i3, 0);
            e.b bVar = this.f26937a;
            bVar.a(strA, bVar.i().a(i.f2270a));
        }
    }

    @Override // g.b
    public void queryMix() {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        messageObtainMessage.what = f.a.BUSINESS_EVENT_QUERY_MIX.ordinal();
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    public final void r(f.f fVar) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject(fVar.f26898a);
            int i2 = jSONObject.getInt("mediatype");
            int i3 = jSONObject.getInt(DatabaseManager.QUALITY);
            if (this.f26937a.h() != null) {
                JSONObject jSONObject2 = new JSONObject();
                if (i2 == 1) {
                    if (this.f26937a.v() != null) {
                        jSONObject2.put("userid", this.f26937a.v().f26761c);
                    }
                } else if (i2 == 2 && this.f26937a.B() != null) {
                    jSONObject2.put("userid", this.f26937a.B().f26761c);
                }
                jSONObject2.put("mtype", i2);
                jSONObject2.put(DatabaseManager.QUALITY, i3);
                this.f26937a.h().i(jSONObject2.toString());
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // g.b
    public void resumeAudioFile() {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        messageObtainMessage.what = f.a.BUSINESS_EVENT_RESUME_AUDIO_FILE.ordinal();
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    public final void s(f.f fVar) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject(fVar.f26898a);
            jSONObject.getString("streamid");
            int i2 = jSONObject.getInt("vol");
            if (this.f26937a.h() != null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("vol", i2);
                this.f26937a.h().u(jSONObject2.toString());
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // g.b
    public void setCameraId(int i2) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("camera_id", i2);
            messageObtainMessage.what = f.a.BUSINESS_EVENT_SET_CAMERA_ID.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void startPlayAudioFile(String str, boolean z2, boolean z3) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("file_path", str);
            jSONObject.put("is_remote_play", z2);
            jSONObject.put("is_loop", z3);
            Message messageObtainMessage = this.f26937a.e().obtainMessage();
            messageObtainMessage.what = f.a.BUSINESS_EVENT_START_PLAY_AUDIO_FILE.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            e.b bVar = this.f26937a;
            Objects.requireNonNull(bVar);
            bVar.new c(messageObtainMessage).a();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // g.b
    public void stopPlayAudioFile() {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        messageObtainMessage.what = f.a.BUSINESS_EVENT_STOP_PLAY_AUDIO_FILE.ordinal();
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void stopRecord() {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        messageObtainMessage.what = f.a.BUSINESS_EVENT_STOP_RECORD.ordinal();
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void switchCamera() throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("skip_flag", false);
            messageObtainMessage.what = f.a.BUSINESS_EVENT_SWITCHCAMERA.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void switchCameraSkipSameSide() throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("skip_flag", true);
            messageObtainMessage.what = f.a.BUSINESS_EVENT_SWITCHCAMERA.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    public void t(f.f fVar) throws JSONException {
        try {
            h.a(f26936b, " onPeerIceStateHandler  " + fVar.f26898a);
            JSONObject jSONObject = new JSONObject(fVar.f26898a);
            int i2 = jSONObject.getInt("status");
            if (i2 == f.b.LOGIC_ICE_STATE_CONNECTED.ordinal()) {
                a(jSONObject);
            } else if (i2 == f.b.LOGIC_ICE_STATE_DISCONNECT.ordinal()) {
                b(jSONObject);
            } else if (i2 == f.b.LOGIC_ICE_STATE_FAILED.ordinal()) {
                c(jSONObject);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final void u(f.f fVar) throws JSONException {
        try {
            h.a(f26936b, " onPeerSdpCreateSdpHandler  " + fVar.f26898a);
            JSONObject jSONObject = new JSONObject(fVar.f26898a);
            int i2 = jSONObject.getInt(CommonNetImpl.STYPE);
            String string = jSONObject.getString("streamid");
            String string2 = jSONObject.getString("sdp");
            String string3 = jSONObject.getString("sdptype");
            int i3 = jSONObject.getInt("mtype");
            int i4 = jSONObject.getInt("minbitrate");
            int i5 = jSONObject.getInt("maxbitrate");
            String strA = c.e.a();
            j.d.d().a(string, string3, string2);
            if (this.f26937a.m() != null) {
                ((h.e) this.f26937a.m()).a(strA, this.f26937a.u().getUId(), string, i2, i3, string3, string2, i4, i5);
                e.b bVar = this.f26937a;
                bVar.a(strA, bVar.i().a("sdp"));
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void v(f.f fVar) throws JSONException {
        e.d dVar;
        String str = "";
        if (this.f26937a.h() != null) {
            try {
                JSONObject jSONObject = new JSONObject(fVar.f26898a);
                int i2 = jSONObject.getInt(CommonNetImpl.STYPE);
                if (i2 == 1) {
                    this.f26937a.u().getUId();
                    jSONObject.getJSONObject("data").put("userid", "");
                    jSONObject.getJSONObject("data").put("streamid", "");
                } else if (i2 == 2) {
                    String str2 = this.f26937a.E().get(jSONObject.getString("streamid"));
                    if (str2 != null && !str2.equals("")) {
                        e.d dVar2 = this.f26937a.D().get(str2);
                        jSONObject.getJSONObject("data").put("userid", dVar2 != null ? dVar2.f26761c : "");
                        jSONObject.getJSONObject("data").put("streamid", str2);
                    }
                }
                jSONObject.put(CommonNetImpl.AID, this.f26937a.u().getAppId());
                jSONObject.put("rid", this.f26937a.u().getRoomId());
                jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_SID, this.f26937a.y().b());
                jSONObject.put("uid", this.f26937a.u().getUId());
                jSONObject.put("rpc_id", c.e.a());
                if (this.f26937a.l() != null && !this.f26937a.l().isShutdown()) {
                    this.f26937a.l().execute(new b(jSONObject));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (this.f26937a.h() == null || fVar == null) {
            return;
        }
        try {
            JSONObject jSONObject2 = new JSONObject(fVar.f26898a);
            String string = jSONObject2.getString("streamid");
            if (jSONObject2.getInt(CommonNetImpl.STYPE) == 1) {
                jSONObject2.put("uid", this.f26937a.u().getUId());
            } else {
                String str3 = this.f26937a.E().get(string);
                if (string != null && !string.equals("") && (dVar = this.f26937a.D().get(str3)) != null) {
                    str = dVar.f26761c;
                }
                jSONObject2.put("uid", str);
            }
            this.f26937a.h().r(jSONObject2.toString());
        } catch (JSONException unused) {
        }
    }

    public final void w(f.f fVar) throws JSONException {
        f.d dVarB;
        try {
            JSONObject jSONObject = new JSONObject(fVar.f26898a);
            String string = jSONObject.getString("streamid");
            int i2 = jSONObject.getInt(DatabaseManager.QUALITY);
            String str = this.f26937a.E().get(string);
            if (str == null || str.equals("") || (dVarB = this.f26937a.y().b(str)) == null || this.f26937a.h() == null) {
                return;
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("uid", dVarB.d());
            jSONObject2.put("mtype", dVarB.a());
            jSONObject2.put(DatabaseManager.QUALITY, i2);
            this.f26937a.h().y(jSONObject2.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final void x(f.f fVar) throws JSONException {
        f.d dVarB;
        try {
            JSONObject jSONObject = new JSONObject(fVar.f26898a);
            String string = jSONObject.getString("streamid");
            int i2 = jSONObject.getInt("vol");
            String str = this.f26937a.E().get(string);
            if (str == null || str.equals("") || (dVarB = this.f26937a.y().b(str)) == null || this.f26937a.h() == null) {
                return;
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("uid", dVarB.d());
            jSONObject2.put("mtype", dVarB.a());
            jSONObject2.put("vol", i2);
            this.f26937a.h().d(jSONObject2.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void y(f.f fVar) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject(fVar.f26898a);
            int i2 = jSONObject.getInt("mtype");
            if (this.f26937a.v() != null) {
                JSONObject jSONObject2 = new JSONObject();
                JSONObject jSONObject3 = new JSONObject();
                if (this.f26937a.v().f26764f == j.STREAM_STATUS_READY) {
                    jSONObject2.put("code", 5008);
                    jSONObject2.put("msg", "cam published");
                    jSONObject3.put("mtype", i2);
                    jSONObject2.put("audio", this.f26937a.v().f26766h);
                    jSONObject2.put("video", this.f26937a.v().f26765g);
                    jSONObject2.put("data", jSONObject3);
                    if (this.f26937a.h() != null) {
                        this.f26937a.h().k(jSONObject2.toString());
                        return;
                    }
                    return;
                }
                if (this.f26937a.v().f26764f != j.STREAM_STATUS_IDLE) {
                    jSONObject2.put("code", 5036);
                    jSONObject2.put("msg", "cam publishing");
                    jSONObject3.put("mtype", i2);
                    jSONObject2.put("audio", this.f26937a.v().f26766h);
                    jSONObject2.put("video", this.f26937a.v().f26765g);
                    jSONObject2.put("data", jSONObject3);
                    if (this.f26937a.h() != null) {
                        this.f26937a.h().k(jSONObject2.toString());
                        return;
                    }
                    return;
                }
            }
            boolean z2 = jSONObject.getBoolean("audio");
            boolean z3 = jSONObject.getBoolean("video");
            String strA = this.f26937a.a(i2, (VideoSink) null, z3, z2);
            if (!TextUtils.isEmpty(strA)) {
                h.a(f26936b, "publishCamInner start local render failed for " + strA);
                this.f26937a.h().g(strA);
                return;
            }
            this.f26937a.a(new e.d());
            this.f26937a.v().f26766h = z2;
            this.f26937a.v().f26765g = z3;
            this.f26937a.v().f26763e = i2;
            this.f26937a.v().f26764f = j.STREAM_STATUS_INIT;
            this.f26937a.v().f26761c = this.f26937a.u().getUId();
            this.f26937a.v().f26768j = false;
            this.f26937a.v().f26767i = false;
            this.f26937a.v().f26769k = false;
            this.f26937a.v().f26770l = false;
            h.a("publish set camerClient ", this.f26937a.v().toString());
            if (this.f26937a.m() != null) {
                String strA2 = c.e.a();
                ((h.e) this.f26937a.m()).a(strA2, this.f26937a.u().getUId(), i2, z2, z3, (!z3 ? k.VP_NONE : c.e.a(this.f26937a.w().f(), this.f26937a.w().b())).ordinal(), d.b.h());
                e.b bVar = this.f26937a;
                bVar.a(strA2, bVar.i().a(i.f2273d));
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void z(f.f fVar) throws JSONException {
        try {
            if (!d.b.w()) {
                if (this.f26937a.h() != null) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("code", 5036);
                    jSONObject.put("msg", "not support screen capture");
                    jSONObject.put("audio", this.f26937a.B().f26766h);
                    jSONObject.put("video", this.f26937a.B().f26765g);
                    h.a(f26936b, " publishScreenInner code is: 5036");
                    this.f26937a.h().k(jSONObject.toString());
                    return;
                }
                return;
            }
            JSONObject jSONObject2 = new JSONObject(fVar.f26898a);
            int i2 = jSONObject2.getInt("mtype");
            if (this.f26937a.B() != null) {
                JSONObject jSONObject3 = new JSONObject();
                JSONObject jSONObject4 = new JSONObject();
                if (this.f26937a.B().f26764f == j.STREAM_STATUS_READY) {
                    jSONObject3.put("code", 5008);
                    jSONObject3.put("msg", "screen published");
                    jSONObject4.put("mtype", i2);
                    jSONObject3.put("data", jSONObject4);
                    return;
                }
                if (this.f26937a.B().f26764f != j.STREAM_STATUS_IDLE) {
                    jSONObject3.put("code", 5036);
                    jSONObject3.put("msg", "screen publishing");
                    jSONObject4.put("mtype", i2);
                    jSONObject3.put("data", jSONObject4);
                    return;
                }
            }
            boolean z2 = jSONObject2.getBoolean("audio");
            boolean z3 = jSONObject2.getBoolean("video");
            this.f26937a.a(i2, (VideoSink) null, z3, z2);
            this.f26937a.b(new e.d());
            this.f26937a.B().f26766h = false;
            this.f26937a.B().f26765g = true;
            this.f26937a.B().f26763e = i2;
            this.f26937a.B().f26764f = j.STREAM_STATUS_INIT;
            this.f26937a.B().f26761c = this.f26937a.u().getUId();
            this.f26937a.B().f26768j = false;
            this.f26937a.B().f26767i = false;
            this.f26937a.B().f26769k = false;
            this.f26937a.B().f26770l = false;
            if (this.f26937a.m() != null) {
                String strA = c.e.a();
                ((h.e) this.f26937a.m()).a(strA, this.f26937a.u().getUId(), i2, z2, z3, c.e.a(this.f26937a.A().f(), this.f26937a.A().b()).ordinal(), d.b.h());
                e.b bVar = this.f26937a;
                bVar.a(strA, bVar.i().a(i.f2273d));
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final void i(f.f fVar) throws JSONException {
        try {
            boolean z2 = new JSONObject(fVar.f26898a).getBoolean("enable");
            Log.d(f26936b, "enableAudioRecord : " + z2);
            this.f26937a.b(z2);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void k(f.f fVar) throws JSONException {
        try {
            h.a(f26936b, "joinRoomInner : " + fVar.f26898a);
            JSONObject jSONObject = new JSONObject(fVar.f26898a);
            this.f26937a.u().setAppId(jSONObject.getString("appid"));
            this.f26937a.u().setUId(jSONObject.getString("uid"));
            this.f26937a.u().setRoomId(jSONObject.getString("roomid"));
            this.f26937a.u().setToken(jSONObject.getString("token"));
            if (b.a.f26722h) {
                if (this.f26937a.e() != null) {
                    Message messageObtainMessage = this.f26937a.e().obtainMessage();
                    messageObtainMessage.what = f.a.BUSINESS_EVENT_GETREGION_ADDR.ordinal();
                    Bundle bundle = new Bundle();
                    bundle.putString("msgdata", "");
                    messageObtainMessage.setData(bundle);
                    this.f26937a.e().sendMessage(messageObtainMessage);
                }
            } else if (this.f26937a.l() != null) {
                this.f26937a.l().execute(new a(jSONObject));
            }
            if (jSONObject.has(e.f0.U)) {
                return;
            }
            this.f26937a.a(f.h.LOGIC_ENGINE_CONNECTING);
            try {
                h.a(f26936b, "start connecting server!");
                JSONObject jSONObject2 = new JSONObject();
                JSONObject jSONObject3 = new JSONObject();
                jSONObject2.put("code", 0);
                jSONObject2.put("msg", "start connecting server");
                jSONObject3.put("uid", this.f26937a.u().getUId());
                jSONObject3.put("roomid", this.f26937a.u().getRoomId());
                jSONObject2.put("data", jSONObject3);
                this.f26937a.h().a(jSONObject2.toString());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
    }

    public void m(f.f fVar) throws JSONException {
        if (this.f26937a.m() == null || !(this.f26937a.m() instanceof h.e)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(fVar.f26898a);
            int i2 = jSONObject.getInt("cmdtype");
            jSONObject.getString("userid");
            h.a(f26936b, "logOffNotifyInner " + i2);
            if (i2 == 0 || i2 == 1) {
                g(false);
            } else if (i2 == 2) {
                ((h.e) this.f26937a.m()).a(c.e.a(), this.f26937a.u().getRoomId(), 3, false);
                h.a(f26936b, "fail connection after 5 s for logoff notify RECON_RS_LOGOFF_RECON");
                ((h.e) this.f26937a.m()).a("", "", h.d.RECON_RS_LOGOFF_RECON, 5000);
            } else if (i2 == 3) {
                b(i2);
            }
            if (this.f26937a.h() != null) {
                this.f26937a.h().I(jSONObject.toString());
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final void g() throws JSONException {
        f.h hVarZ = this.f26937a.z();
        h.a(f26936b, " onHandleMediaServerReconnecting " + hVarZ);
        f.h hVar = f.h.LOGIC_ENGINE_CONNECTED;
        if (hVarZ == hVar) {
            this.f26937a.a(f.h.LOGIC_ENGINE_RECONNECTING);
        }
        if (this.f26937a.h() != null) {
            if (hVarZ == hVar || hVarZ == f.h.LOGIC_ENGINE_CONNECTING) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("code", 0);
                    jSONObject.put("msg", "server reconnecting ");
                    jSONObject.put("uid", this.f26937a.u().getUId());
                    jSONObject.put("roomid", this.f26937a.u().getRoomId());
                    this.f26937a.h().p(jSONObject.toString());
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public void h() throws JSONException {
        try {
            h.a(f26936b, "server config urls size:" + this.f26937a.C().c().size());
            h.a(f26936b, "auto switch on " + this.f26937a.C().d());
            h.a(f26936b, "current server index:" + this.f26937a.C().b());
            if (this.f26937a.C().c().size() <= 1 || !this.f26937a.C().d() || this.f26937a.m() == null || !(this.f26937a.m() instanceof h.e)) {
                return;
            }
            if (this.f26937a.C().b() < this.f26937a.C().c().size() - 1) {
                if (this.f26937a.v() != null) {
                    f(1);
                } else if (this.f26937a.B() != null) {
                    f(2);
                }
                HashMap map = new HashMap(this.f26937a.D());
                h.a(f26936b, " substream.size " + map.size());
                for (Map.Entry entry : map.entrySet()) {
                    e.d dVar = (e.d) entry.getValue();
                    h.a(f26936b, " uclient " + dVar);
                    if (dVar != null) {
                        h.a(f26936b, " uclient recon " + dVar.f26770l + "  status " + dVar.f26764f.ordinal());
                        this.f26937a.D().get(entry.getKey());
                        a(dVar.f26759a, this.f26937a.E().get(dVar.f26759a), dVar);
                    }
                }
                map.clear();
                Iterator<String> it = this.f26937a.D().keySet().iterator();
                while (it.hasNext()) {
                    h.a(f26936b, "show stream info " + this.f26937a.D().get(it.next()).toString());
                }
                this.f26937a.C().a(this.f26937a.C().b() + 1);
                String strC = this.f26937a.C().c().get(this.f26937a.C().b()).c();
                String strB = this.f26937a.C().c().get(this.f26937a.C().b()).b();
                String strA = this.f26937a.C().c().get(this.f26937a.C().b()).a();
                String str = "wss://" + strC + ":" + strB + "/ws";
                ((h.e) this.f26937a.m()).a(c.e.a(), this.f26937a.u().getRoomId(), 3, false);
                h.a(f26936b, "reconnect to the other signal to: " + str + " port: " + strB + " server index " + this.f26937a.C().b());
                h.a(f26936b, "fail connection after 5 s for switch signal");
                this.f26937a.m().a(str, strA, h.d.RECON_RS_SWITCH_SIGNAL, 5000);
                try {
                    JSONObject jSONObjectConvertToJsonObject = LogReportManager.getInstance().assemblePublicHeader().version("1.0").method(i.f2281l).rpcId(c.e.a()).type(3).timemills(System.currentTimeMillis()).appId(this.f26937a.u().getAppId()).roomId(this.f26937a.u().getRoomId()).sessionId(this.f26937a.y().b()).userId(this.f26937a.u().getUId()).build().convertToJsonObject();
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, "");
                    jSONObject.put("agent", "");
                    jSONObject.put(com.alipay.sdk.packet.d.f3298n, "");
                    jSONObject.put("system", "");
                    jSONObject.put("network", "");
                    jSONObject.put(am.f22460w, "");
                    jSONObject.put("mem", 0);
                    jSONObject.put("video", 0);
                    jSONObject.put("speaker", 0);
                    jSONObject.put("micphone", 0);
                    jSONObjectConvertToJsonObject.put("data", jSONObject);
                    LogReportManager.getInstance().sendLog(3, jSONObjectConvertToJsonObject.toString(), this.f26937a.l());
                    LogReportManager.getInstance().sendOpMsg(this.f26937a, 2);
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            h.a(f26936b, "no more server to switch");
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public void j() throws JSONException {
        if (this.f26937a.m() == null || !(this.f26937a.m() instanceof h.e)) {
            return;
        }
        String strA = c.e.a();
        ((h.e) this.f26937a.m()).a(strA, this.f26937a.u().getAppId(), this.f26937a.u().getRoomId(), this.f26937a.u().getUId());
        e.b bVar = this.f26937a;
        bVar.a(strA, bVar.i().a(i.f2285p));
    }

    public void i(boolean z2) throws JSONException {
        if (this.f26937a.B() != null) {
            if (!this.f26937a.B().f26765g) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("code", 5018);
                    jSONObject.put("msg", "not enable video track");
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("mtype", 2);
                    jSONObject2.put("ttype", 2);
                    jSONObject2.put(i.f2279j, z2);
                    jSONObject.put("data", jSONObject2);
                    if (this.f26937a.h() != null) {
                        this.f26937a.h().s(jSONObject.toString());
                        return;
                    }
                    return;
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            String strA = c.e.a();
            String str = this.f26937a.B().f26759a;
            if (this.f26937a.m() != null) {
                ((h.e) this.f26937a.m()).a(strA, this.f26937a.u().getUId(), str, 1, 2, z2);
                e.b bVar = this.f26937a;
                bVar.a(strA, bVar.i().a(e.f0.O));
                return;
            }
            return;
        }
        try {
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("code", 5018);
            jSONObject3.put("msg", "not enable audio track");
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("mtype", 2);
            jSONObject4.put("ttype", 2);
            jSONObject3.put("data", jSONObject4);
            if (this.f26937a.h() != null) {
                this.f26937a.h().s(jSONObject3.toString());
            }
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
    }

    public void l(f.f fVar) throws JSONException {
        if (this.f26937a.m() == null || !(this.f26937a.m() instanceof h.e)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(fVar.f26898a);
            String strA = c.e.a();
            ((h.e) this.f26937a.m()).a(strA, this.f26937a.u().getAppId(), this.f26937a.u().getRoomId(), this.f26937a.u().getUId(), jSONObject.getInt("cmdtype"), jSONObject.getJSONArray("users"));
            e.b bVar = this.f26937a;
            bVar.a(strA, bVar.i().a(i.f2290u));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void n(f.f fVar) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject(fVar.f26898a);
            int i2 = jSONObject.getInt("mtype");
            int i3 = jSONObject.getInt("ttype");
            boolean z2 = jSONObject.getBoolean(i.f2279j);
            if (i2 != 1) {
                if (i2 == 2) {
                    if (i3 == 1) {
                        j(z2);
                    } else if (i3 == 2) {
                        i(z2);
                    }
                }
            } else if (i3 == 1) {
                h(z2);
            } else if (i3 == 2) {
                k(z2);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void o(f.f fVar) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject(fVar.f26898a);
            String string = jSONObject.getString("uid");
            int i2 = jSONObject.getInt("mtype");
            int i3 = jSONObject.getInt("ttype");
            boolean z2 = jSONObject.getBoolean(i.f2279j);
            if (i2 != 1) {
                if (i2 == 2) {
                    if (i3 == 1) {
                        c(string, z2);
                    } else if (i3 == 2) {
                        b(string, z2);
                    }
                }
            } else if (i3 == 1) {
                a(string, z2);
            } else if (i3 == 2) {
                d(string, z2);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // g.b
    public void a(int i2) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("mtype", i2);
            messageObtainMessage.what = f.a.BUSINESS_EVENT_STOPPREVIEW.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void c(StreamInfo streamInfo, Object obj) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("uid", streamInfo.getUId());
            jSONObject.put("mtype", streamInfo.getMediaType());
            messageObtainMessage.what = f.a.BUSINESS_EVENT_UNSUBSCRIBESTREAM.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), obj);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    public void f(Message message) throws JSONException {
        f.f fVar;
        f.f fVar2;
        boolean z2;
        f.f fVar3;
        f.f fVar4;
        f.f fVar5;
        int i2 = message.what;
        Bundle data = message.getData();
        int i3 = C0449c.f26943b[f.a.a(i2).ordinal()];
        if (i3 == 3) {
            if (data == null || (fVar = (f.f) data.getSerializable("msgdata")) == null) {
                return;
            }
            D(fVar);
        }
        if (i3 == 4) {
            if (data == null || (fVar2 = (f.f) data.getSerializable("msgdata")) == null) {
                return;
            }
            G(fVar2);
            return;
        }
        if (i3 == 6) {
            if (data != null) {
                try {
                    z2 = new JSONObject(((f.f) data.getSerializable("msgdata")).f26898a).getBoolean("renderflag");
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    z2 = false;
                }
                g(z2);
                return;
            }
            return;
        }
        if (i3 == 38) {
            if (data != null) {
                a(data.getString("msgdata"));
                return;
            }
            return;
        }
        if (i3 == 32) {
            if (data != null) {
                I((f.f) data.getSerializable("msgdata"));
                return;
            }
            return;
        }
        if (i3 == 33) {
            if (data != null) {
                L((f.f) data.getSerializable("msgdata"));
                return;
            }
            return;
        }
        switch (i3) {
            case 13:
                g();
                break;
            case 14:
                if (data != null) {
                    c(data.getInt("reason", 2));
                    break;
                }
                break;
            case 15:
                f();
                break;
            default:
                switch (i3) {
                    case 19:
                        if (data != null) {
                            c(data.getString("msgdata"));
                            break;
                        }
                        break;
                    case 20:
                        if (data != null && (fVar3 = (f.f) data.getSerializable("msgdata")) != null) {
                            A(fVar3);
                            break;
                        }
                        break;
                    case 21:
                        if (data != null && (fVar4 = (f.f) data.getSerializable("msgdata")) != null) {
                            K(fVar4);
                            break;
                        }
                        break;
                    case 22:
                        if (data != null && (fVar5 = (f.f) data.getSerializable("msgdata")) != null) {
                            u(fVar5);
                            break;
                        }
                        break;
                    case 23:
                        if (data != null) {
                            t((f.f) data.getSerializable("msgdata"));
                            break;
                        }
                        break;
                }
        }
    }

    @Override // g.b
    public void b(int i2) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("appid", this.f26937a.u().getAppId());
            jSONObject.put("roomid", this.f26937a.u().getRoomId());
            jSONObject.put("uid", this.f26937a.u().getUId());
            jSONObject.put("token", this.f26937a.u().getToken());
            jSONObject.put(e.f0.U, true);
            jSONObject.put("cmdtype", i2);
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            messageObtainMessage.what = f.a.BUSINESS_EVENT_JOINROOM.ordinal();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    public void d(Message message) {
        h.a(f26936b, "idle state " + message.what + " drop this message ");
    }

    public void p() {
        h.a(f26936b, "turnFlashLightOffInner");
        j.d.d().F();
    }

    public final void d() {
        Log.d(f26936b, "onAudioFileFinishInner ");
        this.f26937a.h().onAudioFileFinish();
    }

    public void e(Message message) throws JSONException {
        f.f fVar;
        f.f fVar2;
        boolean z2;
        int i2 = message.what;
        Bundle data = message.getData();
        switch (C0449c.f26943b[f.a.a(i2).ordinal()]) {
            case 1:
                if (data != null) {
                    J((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 2:
                if (data != null) {
                    B((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 3:
                if (data != null && (fVar = (f.f) data.getSerializable("msgdata")) != null) {
                    D(fVar);
                    break;
                }
                break;
            case 4:
                if (data != null && (fVar2 = (f.f) data.getSerializable("msgdata")) != null) {
                    G(fVar2);
                    break;
                }
                break;
            case 5:
                if (data != null) {
                    k((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 6:
                if (data != null) {
                    try {
                        z2 = new JSONObject(((f.f) data.getSerializable("msgdata")).f26898a).getBoolean("renderflag");
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                        z2 = false;
                    }
                    g(z2);
                    break;
                }
                break;
            case 7:
                q();
                break;
            case 8:
                p();
                break;
            case 9:
                if (data != null) {
                    j((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 10:
                if (data != null) {
                    e((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
        }
    }

    public void j(boolean z2) throws JSONException {
        if (this.f26937a.B() != null) {
            if (!this.f26937a.B().f26765g) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("code", 5018);
                    jSONObject.put("msg", "not enable audio track");
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("mtype", 2);
                    jSONObject2.put("ttype", 1);
                    jSONObject2.put(i.f2279j, z2);
                    jSONObject.put("data", jSONObject2);
                    if (this.f26937a.h() != null) {
                        this.f26937a.h().s(jSONObject.toString());
                        return;
                    }
                    return;
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            String strA = c.e.a();
            String str = this.f26937a.B().f26759a;
            if (this.f26937a.m() != null) {
                ((h.e) this.f26937a.m()).a(strA, this.f26937a.u().getUId(), str, 1, 1, z2);
                e.b bVar = this.f26937a;
                bVar.a(strA, bVar.i().a(e.f0.P));
                return;
            }
            return;
        }
        try {
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("code", 5018);
            jSONObject3.put("msg", "not enable audio track");
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("mtype", 2);
            jSONObject4.put("ttype", 1);
            jSONObject3.put("data", jSONObject4);
            if (this.f26937a.h() != null) {
                this.f26937a.h().s(jSONObject3.toString());
            }
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
    }

    public final void d(f.f fVar) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject(fVar.f26898a);
            this.f26937a.a(jSONObject.getString("user_id"), jSONObject.getDouble("volume"));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final void g(String str) {
        h.a(f26936b, "reSubStreamReconnecting :" + str);
        String str2 = this.f26937a.E().get(str);
        h.a(f26936b, " streamid " + str2 + " substreamid " + str);
        e.d dVar = this.f26937a.D().get(str2);
        if (dVar != null) {
            h.a(f26936b, " uclient " + dVar.toString());
            if (dVar.f26764f != j.STREAM_STATUS_READY || dVar.f26769k) {
                return;
            }
            dVar.f26764f = j.STREAM_STATUS_FAILED;
        }
    }

    public void d(String str) {
        h.a(f26936b, " onServerMsgNotifyHandler " + str);
    }

    @Override // g.b
    public void a(AuthInfo authInfo) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("appid", authInfo.getAppId());
            jSONObject.put("roomid", authInfo.getRoomId());
            jSONObject.put("uid", authInfo.getUId());
            jSONObject.put("token", authInfo.getToken());
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            messageObtainMessage.what = f.a.BUSINESS_EVENT_JOINROOM.ordinal();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x014d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void d(int r5) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 410
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: g.c.d(int):void");
    }

    @Override // g.b
    public void c(boolean z2) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("enable", z2);
            Message messageObtainMessage = this.f26937a.e().obtainMessage();
            messageObtainMessage.what = f.a.BUSINESS_EVENT_OP_AUDIO_RECORD.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            e.b bVar = this.f26937a;
            Objects.requireNonNull(bVar);
            bVar.new c(messageObtainMessage).a();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void q() {
        h.a(f26936b, "turnFlashLightOnInner");
        j.d.d().G();
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0156 -> B:21:0x0159). Please report as a decompilation issue!!! */
    public void g(boolean z2) throws JSONException {
        h.a(f26936b, "leaveroominner : " + z2);
        if (this.f26937a.m() == null || !(this.f26937a.m() instanceof h.e)) {
            return;
        }
        String roomId = this.f26937a.u().getRoomId();
        String appId = this.f26937a.u().getAppId();
        String uId = this.f26937a.u().getUId();
        String strB = this.f26937a.y().b();
        String strA = c.e.a();
        ((h.e) this.f26937a.m()).a(strA, roomId, 0);
        if (this.f26937a.z() != f.h.LOGIC_ENGINE_RECONNECTING && this.f26937a.z() != f.h.LOGIC_ENGINE_CONNECTING) {
            this.f26937a.a(f.h.LOGIC_ENGINE_ROOM_CLOSED);
            e.b bVar = this.f26937a;
            bVar.a(strA, bVar.i().a(i.f2272c));
        } else {
            h.a(f26936b, "leaveroominner state is connecting/reconnecting.");
            this.f26937a.m().e();
            this.f26937a.a((h.f) null);
            this.f26937a.a(f.h.LOGIC_ENGINE_INIT);
        }
        try {
            JSONObject jSONObjectConvertToJsonObject = LogReportManager.getInstance().assemblePublicHeader().version("1.0").method(i.f2281l).rpcId(c.e.a()).type(3).timemills(System.currentTimeMillis()).appId(appId).roomId(roomId).sessionId(strB).userId(uId).build().convertToJsonObject();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, "");
            jSONObject.put("agent", "");
            jSONObject.put(com.alipay.sdk.packet.d.f3298n, "");
            jSONObject.put("system", "");
            jSONObject.put("network", "");
            jSONObject.put(am.f22460w, "");
            jSONObject.put("mem", 0);
            jSONObject.put("video", 0);
            jSONObject.put("speaker", 0);
            jSONObject.put("micphone", 0);
            jSONObjectConvertToJsonObject.put("data", jSONObject);
            LogReportManager.getInstance().sendLog(3, jSONObjectConvertToJsonObject.toString(), this.f26937a.l());
            LogReportManager.getInstance().sendOpMsg(this.f26937a, 2);
            if (!z2) {
                this.f26937a.I();
                j.d.a(false);
            } else {
                this.f26937a.H();
                j.d.b(false);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // g.b
    public void b(int i2, boolean z2) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("mtype", i2);
            jSONObject.put("ttype", 2);
            jSONObject.put(i.f2279j, z2);
            messageObtainMessage.what = f.a.BUSINESS_EVENT_MUTE_LOCALVIDEO.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void c() {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        messageObtainMessage.what = f.a.BUSINESS_EVENT_FLASH_LIGHT_OFF.ordinal();
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void a(int i2, String str) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("cmdtype", i2);
            jSONObject.put("userid", str);
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            messageObtainMessage.what = f.a.BUSINESS_EVENT_LOGOFF_NOTIFY.ordinal();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    public void k(boolean z2) throws JSONException {
        if (this.f26937a.v() != null) {
            if (!this.f26937a.v().f26765g) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("code", 5018);
                    jSONObject.put("msg", "not enable video track");
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("mtype", 1);
                    jSONObject2.put("ttype", 2);
                    jSONObject.put("data", jSONObject2);
                    if (this.f26937a.h() != null) {
                        this.f26937a.h().s(jSONObject.toString());
                        return;
                    }
                    return;
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            String strA = c.e.a();
            String str = this.f26937a.v().f26759a;
            if (this.f26937a.m() != null) {
                ((h.e) this.f26937a.m()).a(strA, this.f26937a.u().getUId(), str, 1, 2, z2);
                e.b bVar = this.f26937a;
                bVar.a(strA, bVar.i().a(e.f0.N));
                return;
            }
            return;
        }
        try {
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("code", 5018);
            jSONObject3.put("msg", "not enable audio track");
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("mtype", 1);
            jSONObject4.put("ttype", 2);
            jSONObject3.put("data", jSONObject4);
            if (this.f26937a.h() != null) {
                this.f26937a.h().s(jSONObject3.toString());
            }
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
    }

    public void c(Message message) throws JSONException, NumberFormatException {
        f.f fVar;
        f.f fVar2;
        int i2 = message.what;
        Bundle data = message.getData();
        int i3 = C0449c.f26943b[f.a.a(i2).ordinal()];
        if (i3 == 3) {
            if (data == null || (fVar = (f.f) data.getSerializable("msgdata")) == null) {
                return;
            }
            D(fVar);
        }
        if (i3 == 4) {
            if (data == null || (fVar2 = (f.f) data.getSerializable("msgdata")) == null) {
                return;
            }
            G(fVar2);
            return;
        }
        boolean z2 = false;
        if (i3 == 6) {
            if (data != null) {
                try {
                    z2 = new JSONObject(((f.f) data.getSerializable("msgdata")).f26898a).getBoolean("renderflag");
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                g(z2);
                return;
            }
            return;
        }
        if (i3 == 19) {
            if (data != null) {
                c(data.getString("msgdata"));
                return;
            }
            return;
        }
        switch (i3) {
            case 10:
                if (data != null) {
                    e((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 11:
                q(null);
                break;
            case 12:
                e();
                break;
            case 13:
                g();
                break;
            case 14:
                if (data != null) {
                    c(data.getInt("reason", 2));
                    break;
                }
                break;
            case 15:
                f();
                break;
            case 16:
                if (data != null) {
                    String string = data.getString("msgdata");
                    h.a(f26936b, "onConnecting onHandlerGetSignalAddr");
                    a(string, false, 0);
                    break;
                }
                break;
            case 17:
                if (data != null) {
                    data.getString("msgdata");
                    break;
                }
                break;
        }
    }

    public final void e(f.f fVar) throws JSONException {
        try {
            h.a(f26936b, "cropPushResolutionInner");
            JSONObject jSONObject = new JSONObject(fVar.f26898a);
            j.d.d().b(jSONObject.getInt("crop_x"), jSONObject.getInt("crop_y"));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // g.b
    public void b(StreamInfo streamInfo, Object obj) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("uid", streamInfo.getUId());
            jSONObject.put("mtype", streamInfo.getMediaType());
            messageObtainMessage.what = f.a.BUSINESS_EVENT_STARTREMOTEPREVIEW.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), obj);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            h.a(f26936b, " send message start remote view ");
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    public final void e() throws JSONException {
        h.a(f26936b, " onHandleMediaServerConFail ");
        String roomId = this.f26937a.u().getRoomId();
        this.f26937a.I();
        if (this.f26937a.m() != null) {
            this.f26937a.m().e();
            this.f26937a.a((h.f) null);
        }
        this.f26937a.a(f.h.LOGIC_ENGINE_INIT);
        if (this.f26937a.h() != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("code", 5000);
                jSONObject.put("msg", "server connect failed ");
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("roomid", roomId);
                jSONObject.put("data", jSONObject2);
                this.f26937a.h().F(jSONObject.toString());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x013d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void f(int r10) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 447
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: g.c.f(int):void");
    }

    @Override // g.b
    public void a(MediaOp mediaOp) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("mtype", mediaOp.getMediaType());
            jSONObject.put("audio", mediaOp.isEnableAudio());
            jSONObject.put("video", mediaOp.isEnableVideo());
            messageObtainMessage.what = f.a.BUSINESS_EVENT_PUSHSTREAM.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    public void d(String str, boolean z2) throws JSONException {
        try {
            if (this.f26937a.y().c(str) == null) {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject.put("code", 5012);
                jSONObject.put("msg", "not have sub this user media");
                jSONObject2.put("uid", str);
                jSONObject2.put("mtype", 1);
                jSONObject2.put("ttype", 2);
                jSONObject2.put(i.f2279j, z2);
                jSONObject.put("data", jSONObject2);
                if (this.f26937a.h() != null) {
                    this.f26937a.h().e(jSONObject.toString());
                    return;
                }
                return;
            }
            String strA = this.f26937a.y().a(str + a.h.f26636f);
            if (strA != null && !strA.equals("")) {
                e.d dVar = this.f26937a.D().get(strA);
                if (dVar == null) {
                    JSONObject jSONObject3 = new JSONObject();
                    JSONObject jSONObject4 = new JSONObject();
                    jSONObject3.put("code", 5013);
                    jSONObject3.put("msg", "not sub this media");
                    jSONObject4.put("uid", str);
                    jSONObject4.put("mtype", 1);
                    jSONObject4.put("ttype", 2);
                    jSONObject4.put(i.f2279j, z2);
                    jSONObject3.put("data", jSONObject4);
                    if (this.f26937a.h() != null) {
                        this.f26937a.h().e(jSONObject3.toString());
                        return;
                    }
                    return;
                }
                String str2 = dVar.f26759a;
                String strA2 = c.e.a();
                if (this.f26937a.m() != null) {
                    ((h.e) this.f26937a.m()).a(strA2, str, str2, 2, 2, z2);
                    e.b bVar = this.f26937a;
                    bVar.a(strA2, bVar.i().a(e.f0.Q));
                    return;
                }
                JSONObject jSONObject5 = new JSONObject();
                JSONObject jSONObject6 = new JSONObject();
                jSONObject5.put("code", 5001);
                jSONObject5.put("msg", "server disconnect");
                jSONObject6.put("uid", str);
                jSONObject6.put("mtype", 1);
                jSONObject6.put("ttype", 2);
                jSONObject6.put(i.f2279j, z2);
                jSONObject5.put("data", jSONObject6);
                if (this.f26937a.h() != null) {
                    this.f26937a.h().e(jSONObject5.toString());
                    return;
                }
                return;
            }
            JSONObject jSONObject7 = new JSONObject();
            JSONObject jSONObject8 = new JSONObject();
            jSONObject7.put("code", 5013);
            jSONObject7.put("msg", "not sub this media");
            jSONObject8.put("uid", str);
            jSONObject8.put("mtype", 1);
            jSONObject8.put("ttype", 2);
            jSONObject8.put(i.f2279j, z2);
            jSONObject7.put("data", jSONObject8);
            if (this.f26937a.h() != null) {
                this.f26937a.h().e(jSONObject7.toString());
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // g.b
    public void b(StreamInfo streamInfo) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("mtype", streamInfo.getMediaType());
            jSONObject.put("uid", streamInfo.getUId());
            jSONObject.put("audio", streamInfo.isHasAudio());
            jSONObject.put("video", streamInfo.isHasVideo());
            jSONObject.put("muteaudio", streamInfo.isMuteAudio());
            jSONObject.put("mutevideo", streamInfo.isMuteVideo());
            messageObtainMessage.what = f.a.BUSINESS_EVENT_SUBSCRIBESTREAM.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void a(int i2, boolean z2) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("mtype", i2);
            jSONObject.put("ttype", 1);
            jSONObject.put(i.f2279j, z2);
            messageObtainMessage.what = f.a.BUSINESS_EVENT_MUTE_LOCALAUDIO.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    public void e(String str) throws JSONException {
        h.a(f26936b, " onServerMsgResHandler " + str);
        try {
            g gVar = this.f26937a.k().get(new JSONObject(str).getString("method"));
            if (gVar != null) {
                gVar.a(str);
            }
        } catch (JSONException unused) {
        }
    }

    public final void c(int i2) throws JSONException {
        int i3;
        int i4;
        int i5;
        int i6;
        h.a(f26936b, "onHandleMediaServerReconnected " + this.f26937a.m());
        if (this.f26937a.m() != null) {
            f.h hVarZ = this.f26937a.z();
            h.a(f26936b, " onHandleMediaServerReconnected " + hVarZ);
            String strA = c.e.a();
            String strEncodeToString = "";
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, this.f26937a.F().l());
                jSONObject.put("agent", this.f26937a.F().b());
                jSONObject.put(com.alipay.sdk.packet.d.f3298n, this.f26937a.F().e());
                jSONObject.put("system", this.f26937a.F().m());
                jSONObject.put("network", this.f26937a.F().k());
                jSONObject.put(am.f22460w, this.f26937a.F().c());
                jSONObject.put("mem", this.f26937a.F().h());
                strEncodeToString = Base64.encodeToString(jSONObject.toString().getBytes(), 2);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            String str = strEncodeToString;
            int iB = this.f26937a.x().b();
            int iA = this.f26937a.x().a();
            if (iA == 0) {
                i3 = -1;
                i4 = -1;
            } else {
                i3 = iB;
                i4 = iA;
            }
            int iOrdinal = h.d.RECON_RS_KEEPLIVEFAIL.ordinal();
            String str2 = e.f0.U;
            if (i2 > iOrdinal) {
                if (i2 == h.d.RECON_RS_SWITCH_SIGNAL.ordinal()) {
                    str2 = i.f2271b;
                } else {
                    i5 = i2 == h.d.RECON_RS_REQUEST_SIGNAL.ordinal() ? 4 : 3;
                    i6 = 2;
                }
                i6 = i5;
            } else {
                i6 = 2;
            }
            String str3 = str2;
            if (!TextUtils.isEmpty(this.f26937a.u().getAppId()) && !TextUtils.isEmpty(this.f26937a.u().getRoomId()) && !TextUtils.isEmpty(this.f26937a.u().getUId()) && hVarZ == f.h.LOGIC_ENGINE_RECONNECTING) {
                ((h.e) this.f26937a.m()).a(strA, this.f26937a.u().getUId(), this.f26937a.u().getRoomId(), this.f26937a.u().getAppId(), this.f26937a.y().b(), this.f26937a.u().getToken(), str, i3, i4, i6);
            }
            e.b bVar = this.f26937a;
            bVar.a(strA, bVar.i().a(str3));
        }
    }

    public final void e(int i2) {
        h.a(f26936b, " rePubStreamReconcting " + i2);
        if (i2 == 1) {
            h.a(f26936b, " rePubStreamReconcting cam " + this.f26937a.v());
            h.a(f26936b, " cam state " + this.f26937a.v().f26764f + " cam quit" + this.f26937a.v().f26769k);
            if (this.f26937a.v() == null || this.f26937a.v().f26764f != j.STREAM_STATUS_READY || this.f26937a.v().f26769k) {
                return;
            }
            this.f26937a.v().f26764f = j.STREAM_STATUS_FAILED;
            return;
        }
        if (i2 == 2) {
            h.a(f26936b, " rePubStreamReconcting screen " + this.f26937a.B());
            h.a(f26936b, " screen state " + this.f26937a.B().f26764f + " screen quit" + this.f26937a.B().f26769k);
            if (this.f26937a.B() == null || this.f26937a.B().f26764f != j.STREAM_STATUS_READY || this.f26937a.B().f26769k) {
                return;
            }
            this.f26937a.B().f26764f = j.STREAM_STATUS_FAILED;
        }
    }

    @Override // g.b
    public void b(StreamInfo[] streamInfoArr) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            for (int i2 = 0; i2 < streamInfoArr.length; i2++) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("user_id", streamInfoArr[i2].getUId());
                jSONObject2.put("media_type", streamInfoArr[i2].getMediaType());
                jSONArray.put(jSONObject2);
            }
            jSONObject.put(IjkMediaMeta.IJKM_KEY_STREAMS, jSONArray);
            messageObtainMessage.what = f.a.BUSINESS_EVENT_ADD_MIX.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void a(StreamInfo streamInfo) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("uid", streamInfo.getUId());
            jSONObject.put("mtype", streamInfo.getMediaType());
            messageObtainMessage.what = f.a.BUSINESS_EVENT_STOPREMOTEPREVIEW.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    public void h(boolean z2) throws JSONException {
        if (this.f26937a.v() != null) {
            if (!this.f26937a.v().f26766h) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("code", 5018);
                    jSONObject.put("msg", "not enable audio track");
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("mtype", 1);
                    jSONObject2.put("ttype", 1);
                    jSONObject.put("data", jSONObject2);
                    if (this.f26937a.h() != null) {
                        this.f26937a.h().s(jSONObject.toString());
                        return;
                    }
                    return;
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            String strA = c.e.a();
            String str = this.f26937a.v().f26759a;
            if (this.f26937a.m() != null) {
                ((h.e) this.f26937a.m()).a(strA, this.f26937a.u().getUId(), str, 1, 1, z2);
                e.b bVar = this.f26937a;
                bVar.a(strA, bVar.i().a(e.f0.M));
                return;
            }
            return;
        }
        try {
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("code", 5018);
            jSONObject3.put("msg", "not enable audio track");
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("mtype", 1);
            jSONObject4.put("ttype", 1);
            jSONObject3.put("data", jSONObject4);
            if (this.f26937a.h() != null) {
                this.f26937a.h().s(jSONObject3.toString());
            }
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
    }

    @Override // g.b
    public void a(StreamInfo streamInfo, Object obj) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("uid", streamInfo.getUId());
            jSONObject.put("mtype", streamInfo.getMediaType());
            messageObtainMessage.what = f.a.BUSINESS_EVENT_STOPREMOTEPREVIEW.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), obj);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            h.a(f26936b, " send message stop remote view ");
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void b(boolean z2) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("enable", z2);
            Message messageObtainMessage = this.f26937a.e().obtainMessage();
            messageObtainMessage.what = f.a.BUSINESS_EVENT_OP_AUDIO_PLAYOUT.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            e.b bVar = this.f26937a;
            Objects.requireNonNull(bVar);
            bVar.new c(messageObtainMessage).a();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final void f() throws JSONException {
        this.f26937a.I();
        f.h hVarZ = this.f26937a.z();
        this.f26937a.a(f.h.LOGIC_ENGINE_INIT);
        if (this.f26937a.m() != null) {
            this.f26937a.m().e();
            this.f26937a.a((h.f) null);
        }
        if (this.f26937a.h() != null) {
            if (hVarZ == f.h.LOGIC_ENGINE_CONNECTING) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("code", 5000);
                    jSONObject.put("msg", "server connect failed ");
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("roomid", this.f26937a.u().getRoomId());
                    jSONObject.put("data", jSONObject2);
                    this.f26937a.h().F(jSONObject.toString());
                    return;
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            if (hVarZ == f.h.LOGIC_ENGINE_CONNECTED || hVarZ == f.h.LOGIC_ENGINE_RECONNECTING) {
                try {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("code", 5001);
                    jSONObject3.put("msg", "server disconnect");
                    this.f26937a.h().w(jSONObject3.toString());
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    public void c(String str) throws JSONException {
        h.a(f26936b, " onServerMsgHandler " + str);
        try {
            String string = new JSONObject(str).getString("method");
            h.a(f26936b, " onServerMsgHandler method  " + string);
            if (string.equals(i.f2276g)) {
                b(str);
            } else if (string.equals("notify")) {
                d(str);
            } else {
                e(str);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // g.b
    public void a(RecordProfile recordProfile) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("recordType", recordProfile.getRecordType());
            jSONObject.put("mainviewUserId", recordProfile.getMainViewUserId());
            jSONObject.put("userId", this.f26937a.u().getUId());
            jSONObject.put("mediaType", recordProfile.getMediaType());
            jSONObject.put(TtmlNode.TAG_REGION, recordProfile.getRegion());
            jSONObject.put("bucket", recordProfile.getBucket());
            jSONObject.put("resolution", recordProfile.getVideoProfile());
            jSONObject.put("isaverage", recordProfile.IsAverage());
            jSONObject.put("waterType", recordProfile.getWaterType());
            jSONObject.put("waterpos", recordProfile.getWaterPosition());
            jSONObject.put("waterurl", recordProfile.getWarterUrl());
            jSONObject.put("template", recordProfile.getTemplate());
            messageObtainMessage.what = f.a.BUSINESS_EVENT_START_RECORD.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void b() {
        JSONObject jSONObject = new JSONObject();
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        messageObtainMessage.what = f.a.BUSINESS_EVENT_RECON_LEAVE_ROOM.ordinal();
        f.f fVar = new f.f(jSONObject.toString(), null);
        Bundle bundle = new Bundle();
        bundle.putSerializable("msgdata", fVar);
        messageObtainMessage.setData(bundle);
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    public void c(JSONObject jSONObject) throws JSONException {
        try {
            h.a(f26936b, " onPeerFailed  " + jSONObject.toString());
            int i2 = jSONObject.getInt(CommonNetImpl.STYPE);
            int i3 = jSONObject.getInt("mtype");
            String string = jSONObject.getString("streamid");
            j.d.d().b(string, false);
            a(i2, i3, string);
            h.a(f26936b, "room state is " + this.f26937a.z());
            if (this.f26937a.z() == f.h.LOGIC_ENGINE_CONNECTED) {
                if (i2 == 1) {
                    d(i3);
                } else if (i2 == 2) {
                    f(string);
                }
            } else if (this.f26937a.z() == f.h.LOGIC_ENGINE_RECONNECTING) {
                if (i2 == 1) {
                    e(i3);
                } else if (i2 == 2) {
                    g(string);
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void b(Message message) throws JSONException, NumberFormatException {
        f.f fVar;
        f.f fVar2;
        f.f fVar3;
        f.f fVar4;
        f.f fVar5;
        int i2 = message.what;
        Bundle data = message.getData();
        boolean z2 = false;
        switch (C0449c.f26943b[f.a.a(i2).ordinal()]) {
            case 1:
                if (data != null) {
                    J((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 2:
                if (data != null) {
                    B((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 3:
                if (data != null && (fVar = (f.f) data.getSerializable("msgdata")) != null) {
                    D(fVar);
                    break;
                }
                break;
            case 4:
                if (data != null && (fVar2 = (f.f) data.getSerializable("msgdata")) != null) {
                    G(fVar2);
                    break;
                }
                break;
            case 5:
                if (data != null) {
                    k((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 6:
                if (data != null) {
                    try {
                        z2 = new JSONObject(((f.f) data.getSerializable("msgdata")).f26898a).getBoolean("renderflag");
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    g(z2);
                    break;
                }
                break;
            case 7:
                q();
                break;
            case 8:
                p();
                break;
            case 9:
                if (data != null) {
                    j((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 10:
                if (data != null) {
                    e((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 13:
                g();
                break;
            case 14:
                if (data != null) {
                    c(data.getInt("reason", 2));
                    break;
                }
                break;
            case 15:
                f();
                break;
            case 16:
                if (data != null) {
                    String string = data.getString("msgdata");
                    boolean z3 = data.getBoolean(e.f0.U, false);
                    int i3 = data.getInt("cmdtype", 0);
                    h.a(f26936b, "onConnected onHandlerGetSignalAddr");
                    a(string, z3, i3);
                    break;
                }
                break;
            case 19:
                if (data != null) {
                    c(data.getString("msgdata"));
                    break;
                }
                break;
            case 20:
                if (data != null && (fVar3 = (f.f) data.getSerializable("msgdata")) != null) {
                    A(fVar3);
                    break;
                }
                break;
            case 21:
                if (data != null && (fVar4 = (f.f) data.getSerializable("msgdata")) != null) {
                    K(fVar4);
                    break;
                }
                break;
            case 22:
                if (data != null && (fVar5 = (f.f) data.getSerializable("msgdata")) != null) {
                    u(fVar5);
                    break;
                }
                break;
            case 23:
                if (data != null) {
                    t((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 24:
            case 25:
            case 26:
                if (data != null) {
                    n((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 27:
            case 28:
            case 29:
                if (data != null) {
                    o((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 30:
                if (data != null) {
                    F((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 31:
                if (data != null) {
                    H((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 32:
                if (data != null) {
                    I((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 33:
                if (data != null) {
                    L((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 34:
                if (data != null) {
                    r((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 35:
                if (data != null) {
                    w((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 36:
                if (data != null) {
                    x((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 37:
                if (data != null) {
                    s((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 38:
                if (data != null) {
                    a(data.getString("msgdata"));
                    break;
                }
                break;
            case 39:
                if (data != null) {
                    v((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 40:
                if (data != null) {
                    E((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 41:
                n();
                break;
            case 42:
                j();
                break;
            case 43:
                if (data != null) {
                    p((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 44:
                if (data != null) {
                    g((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 45:
                if (data != null) {
                    h((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 46:
                if (data != null) {
                    i((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 47:
                if (data != null) {
                    a((f.f) data.getSerializable("msgdata"), data.getInt("type"));
                    break;
                }
                break;
            case 48:
                if (data != null) {
                    b((f.f) data.getSerializable("msgdata"), data.getInt("type"));
                    break;
                }
                break;
            case 49:
                if (data != null) {
                    a((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 50:
                if (data != null) {
                    f((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 51:
                h();
                break;
            case 52:
                if (data != null) {
                    l((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 53:
                if (data != null) {
                    m((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 54:
                if (data != null) {
                    c((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 55:
                if (data != null) {
                    C((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 56:
                m();
                break;
            case 57:
                k();
                break;
            case 58:
                i();
                break;
            case 59:
                d();
                break;
            case 60:
                l();
                break;
            case 61:
                o();
                break;
            case 62:
                if (data != null) {
                    d((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
            case 63:
                if (data != null) {
                    b((f.f) data.getSerializable("msgdata"));
                    break;
                }
                break;
        }
    }

    public final void f(String str) throws JSONException {
        h.a(f26936b, " mBusinessEngine.getSubPubMap() size " + this.f26937a.E().size());
        for (String str2 : this.f26937a.E().keySet()) {
            h.a(f26936b, " mBusinessEngine.getSubPubMap() key: " + str2 + " value:" + this.f26937a.E().get(str2));
        }
        String strRemove = this.f26937a.E().remove(str);
        h.a(f26936b, " streamid " + strRemove + " substreamid " + str + "");
        e.d dVarRemove = this.f26937a.D().remove(strRemove);
        StringBuilder sb = new StringBuilder();
        sb.append(" uclient ");
        sb.append(dVarRemove);
        h.a(f26936b, sb.toString());
        if (dVarRemove != null) {
            j jVar = dVarRemove.f26764f;
            if ((jVar == j.STREAM_STATUS_READY || jVar == j.STREAM_STATUS_FAILED) && !dVarRemove.f26769k) {
                h.a(f26936b, "check do recon: " + dVarRemove.f26770l);
            }
            if (dVarRemove.f26770l) {
                return;
            }
            dVarRemove.f26770l = true;
            h.a(f26936b, "uclient.mIsrecon = true: ");
            if (this.f26937a.m() != null) {
                ((h.e) this.f26937a.m()).a(c.e.a(), this.f26937a.u().getUId(), dVarRemove.f26761c, strRemove, str, dVarRemove.f26763e);
                this.f26937a.c(str, dVarRemove.f26762d, dVarRemove.f26763e);
                String strA = c.e.a();
                ((h.e) this.f26937a.m()).a(strA, this.f26937a.u().getUId(), dVarRemove.f26761c, strRemove, dVarRemove.f26763e, dVarRemove.f26766h, dVarRemove.f26765g);
                e.b bVar = this.f26937a;
                bVar.a(strA, bVar.i().a(e.f0.V));
            }
        }
    }

    @Override // g.b
    public void a(MixProfile mixProfile) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", mixProfile.getType());
            jSONObject.put(IjkMediaMeta.IJKM_KEY_STREAMS, mixProfile.getStreams());
            JSONObject jSONObject2 = new JSONObject();
            jSONObject.put("config", jSONObject2);
            jSONObject2.put("pushurl", mixProfile.getPushUrl());
            jSONObject2.put(TtmlNode.TAG_LAYOUT, mixProfile.getLayout());
            if (mixProfile.getLayoutUserLimit() > 0) {
                jSONObject2.put("layoutUserlimit", mixProfile.getLayoutUserLimit());
            }
            jSONObject2.put("custom", mixProfile.getCustom());
            jSONObject2.put("bgColor", mixProfile.getBgColor());
            jSONObject2.put("framerate", mixProfile.getFrameRate());
            jSONObject2.put(IjkMediaMeta.IJKM_KEY_BITRATE, mixProfile.getBitrate());
            jSONObject2.put("videocodec", mixProfile.getVideoCodec());
            jSONObject2.put("qualitylevel", mixProfile.getQualityLevel());
            jSONObject2.put("audiocodec", mixProfile.getAudioCodec());
            jSONObject2.put("mainviewuid", mixProfile.getMainViewUserId());
            jSONObject2.put("mainviewtype", mixProfile.getMainViewType());
            jSONObject2.put("width", mixProfile.getWidth());
            jSONObject2.put("height", mixProfile.getHeight());
            jSONObject2.put("bucket", mixProfile.getBucket());
            jSONObject2.put(TtmlNode.TAG_REGION, mixProfile.getRegion());
            jSONObject2.put("watertype", mixProfile.getWaterType());
            jSONObject2.put("waterpos", mixProfile.getWaterPosition());
            jSONObject2.put("waterurl", mixProfile.getWaterUrl());
            jSONObject2.put("mimetype", mixProfile.getMimeType());
            jSONObject2.put("addstreammode", mixProfile.getStreamMode());
            if (!TextUtils.isEmpty(mixProfile.getKeyUser())) {
                jSONObject2.put("keyuser", mixProfile.getKeyUser());
            }
            jSONObject2.put("expand", mixProfile.getExpand());
            messageObtainMessage.what = f.a.BUSINESS_EVENT_START_MIX.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            bundle.putInt("type", mixProfile.getType());
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    public void c(f.f fVar) throws JSONException {
        String str = "";
        try {
            h.a(f26936b, "adjustPushResolutionInner");
            JSONObject jSONObject = new JSONObject(fVar.f26898a);
            j.d.d().a(jSONObject.getInt("width"), jSONObject.getInt("height"));
            int i2 = jSONObject.getInt("vp");
            int i3 = jSONObject.getInt("startbitrate");
            int i4 = jSONObject.getInt("maxbitrate");
            int i5 = jSONObject.getInt("minbitrate");
            j.h hVar = new j.h();
            hVar.e(i3);
            hVar.d(i4);
            hVar.c(i5);
            j.d.d().a("", hVar);
            if (this.f26937a.m() == null || !(this.f26937a.m() instanceof h.e)) {
                return;
            }
            if (this.f26937a.v() != null) {
                str = this.f26937a.v().f26759a;
            } else {
                h.a(f26936b, "Camera Client is null.");
            }
            String strA = c.e.a();
            ((h.e) this.f26937a.m()).a(strA, this.f26937a.u().getAppId(), this.f26937a.u().getRoomId(), this.f26937a.u().getUId(), str, i2, i4, i5);
            e.b bVar = this.f26937a;
            bVar.a(strA, bVar.i().a(i.f2292w));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void f(f.f fVar) throws JSONException {
        if (this.f26937a.m() == null || !(this.f26937a.m() instanceof h.e)) {
            return;
        }
        String strA = c.e.a();
        ((h.e) this.f26937a.m()).b(strA, this.f26937a.u().getAppId(), this.f26937a.u().getRoomId(), this.f26937a.u().getUId(), fVar.f26898a);
        e.b bVar = this.f26937a;
        bVar.a(strA, bVar.i().a(i.f2289t));
    }

    public void c(String str, boolean z2) throws JSONException {
        try {
            if (this.f26937a.y().c(str) == null) {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject.put("code", 5012);
                jSONObject.put("msg", "not have sub this user media");
                jSONObject2.put("uid", str);
                jSONObject2.put("mtype", 2);
                jSONObject2.put("ttype", 1);
                jSONObject2.put(i.f2279j, z2);
                jSONObject.put("data", jSONObject2);
                if (this.f26937a.h() != null) {
                    this.f26937a.h().e(jSONObject.toString());
                    return;
                }
                return;
            }
            String strA = this.f26937a.y().a(str + 2);
            if (strA != null && !strA.equals("")) {
                e.d dVar = this.f26937a.D().get(strA);
                if (dVar == null) {
                    JSONObject jSONObject3 = new JSONObject();
                    JSONObject jSONObject4 = new JSONObject();
                    jSONObject3.put("code", 5013);
                    jSONObject3.put("msg", "not sub this media");
                    jSONObject4.put("uid", str);
                    jSONObject4.put("mtype", 2);
                    jSONObject4.put("ttype", 1);
                    jSONObject4.put(i.f2279j, z2);
                    jSONObject3.put("data", jSONObject4);
                    if (this.f26937a.h() != null) {
                        this.f26937a.h().e(jSONObject3.toString());
                        return;
                    }
                    return;
                }
                String str2 = dVar.f26759a;
                String strA2 = c.e.a();
                if (this.f26937a.m() != null) {
                    ((h.e) this.f26937a.m()).a(strA2, str, str2, 2, 2, z2);
                    e.b bVar = this.f26937a;
                    bVar.a(strA2, bVar.i().a(e.f0.S));
                    return;
                }
                JSONObject jSONObject5 = new JSONObject();
                JSONObject jSONObject6 = new JSONObject();
                jSONObject5.put("code", 5001);
                jSONObject5.put("msg", "server disconnect");
                jSONObject6.put("uid", str);
                jSONObject6.put("mtype", 2);
                jSONObject6.put("ttype", 1);
                jSONObject6.put(i.f2279j, z2);
                jSONObject5.put("data", jSONObject6);
                if (this.f26937a.h() != null) {
                    this.f26937a.h().e(jSONObject5.toString());
                    return;
                }
                return;
            }
            JSONObject jSONObject7 = new JSONObject();
            JSONObject jSONObject8 = new JSONObject();
            jSONObject7.put("code", 5013);
            jSONObject7.put("msg", "not sub this media");
            jSONObject8.put("uid", str);
            jSONObject8.put("mtype", 2);
            jSONObject8.put("ttype", 1);
            jSONObject8.put(i.f2279j, z2);
            jSONObject7.put("data", jSONObject8);
            if (this.f26937a.h() != null) {
                this.f26937a.h().e(jSONObject7.toString());
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // g.b
    public void a(int i2, String[] strArr) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            if (strArr != null) {
                for (int i3 = 0; i3 < strArr.length; i3++) {
                    jSONArray.put(strArr);
                }
            }
            jSONObject.put("type", i2);
            jSONObject.put("pushurl", jSONArray);
            messageObtainMessage.what = f.a.BUSINESS_EVENT_STOP_MIX.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putInt("type", i2);
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void a(StreamInfo[] streamInfoArr) throws JSONException {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            for (int i2 = 0; i2 < streamInfoArr.length; i2++) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("user_id", streamInfoArr[i2].getUId());
                jSONObject2.put("media_type", streamInfoArr[i2].getMediaType());
                jSONArray.put(jSONObject2);
            }
            jSONObject.put(IjkMediaMeta.IJKM_KEY_STREAMS, jSONArray);
            messageObtainMessage.what = f.a.BUSINESS_EVENT_DEL_MIX.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    @Override // g.b
    public void a(boolean z2) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("enable", z2);
            Message messageObtainMessage = this.f26937a.e().obtainMessage();
            messageObtainMessage.what = f.a.BUSINESS_EVENT_OP_AUDIO_MODULE.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            e.b bVar = this.f26937a;
            Objects.requireNonNull(bVar);
            bVar.new c(messageObtainMessage).a();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // g.b
    public void a(j.h hVar) throws JSONException {
        int i2;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("width", hVar.f());
            jSONObject.put("height", hVar.b());
            if (hVar.b() >= 1080) {
                i2 = 3;
            } else {
                i2 = (hVar.b() < 720 || hVar.b() >= 1080) ? 1 : 2;
            }
            jSONObject.put("vp", i2);
            jSONObject.put("startbitrate", hVar.e());
            jSONObject.put("maxbitrate", hVar.c());
            jSONObject.put("minbitrate", hVar.d());
            Message messageObtainMessage = this.f26937a.e().obtainMessage();
            messageObtainMessage.what = f.a.BUSINESS_EVENT_ADJUST_PUSH_RESOLUTION.ordinal();
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            e.b bVar = this.f26937a;
            Objects.requireNonNull(bVar);
            bVar.new c(messageObtainMessage).a();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final void b(f.f fVar) throws JSONException {
        try {
            this.f26937a.a(new JSONObject(fVar.f26898a).getDouble("volume"));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // g.b
    public void a() {
        Message messageObtainMessage = this.f26937a.e().obtainMessage();
        messageObtainMessage.what = f.a.BUSINESS_EVENT_FLASH_LIGHT_ON.ordinal();
        e.b bVar = this.f26937a;
        Objects.requireNonNull(bVar);
        bVar.new c(messageObtainMessage).a();
    }

    public void b(String str) throws JSONException {
        h.a(f26936b, " onserverack " + str);
        try {
            e.e eVarRemove = this.f26937a.d().remove(new JSONObject(str).getString("rpc_id"));
            if (eVarRemove == null || eVarRemove.a() == null) {
                return;
            }
            eVarRemove.a().a(str, eVarRemove.b());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void a(Message message) throws JSONException {
        f.f fVar;
        f.f fVar2;
        int i2 = message.what;
        Bundle data = message.getData();
        int i3 = C0449c.f26943b[f.a.a(i2).ordinal()];
        if (i3 == 3) {
            if (data == null || (fVar = (f.f) data.getSerializable("msgdata")) == null) {
                return;
            }
            D(fVar);
            return;
        }
        if (i3 != 4) {
            if (i3 == 19 && data != null) {
                c(data.getString("msgdata"));
                return;
            }
            return;
        }
        if (data == null || (fVar2 = (f.f) data.getSerializable("msgdata")) == null) {
            return;
        }
        G(fVar2);
    }

    public void b(JSONObject jSONObject) throws JSONException {
        try {
            h.a(f26936b, " onPeerDisconnect  " + jSONObject.toString());
            jSONObject.getInt(CommonNetImpl.STYPE);
            jSONObject.getInt("mtype");
            j.d.d().b(jSONObject.getString("streamid"), false);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final void a(String str, boolean z2, int i2) throws JSONException, NumberFormatException {
        String str2;
        String strA;
        h.a(f26936b, "onHandlerGetSignalAddr " + z2);
        if (!TextUtils.isEmpty(str)) {
            if (this.f26937a.m() == null) {
                e.b bVar = this.f26937a;
                bVar.a(bVar.a((f.d) bVar));
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.getInt(NotificationCompat.CATEGORY_ERROR) == 0) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                    if (jSONObject2 != null) {
                        if (jSONObject2.has("config")) {
                            JSONObject jSONObject3 = jSONObject2.getJSONObject("config");
                            StringBuilder sb = new StringBuilder();
                            str2 = "";
                            sb.append("config ");
                            sb.append(jSONObject3.toString());
                            Log.d(f26936b, sb.toString());
                            this.f26937a.C().b(jSONObject3.getBoolean(i.f2281l));
                        } else {
                            str2 = "";
                        }
                        if (jSONObject2.has("auto_switch")) {
                            JSONObject jSONObject4 = jSONObject2.getJSONObject("auto_switch");
                            boolean z3 = jSONObject4.getBoolean("switch_on");
                            this.f26937a.C().a(z3);
                            if (z3) {
                                JSONObject jSONObject5 = jSONObject4.getJSONObject("push_threshold");
                                int i3 = jSONObject5.getInt("rtt");
                                int i4 = jSONObject5.getInt("lost");
                                int i5 = jSONObject5.getInt("bandwidth");
                                JSONObject jSONObject6 = jSONObject4.getJSONObject("pull_threshold");
                                j.d.d().a(new j.f(i3, i4, i5, jSONObject6.getInt("rtt"), jSONObject6.getInt("lost"), jSONObject6.getInt("bandwidth")));
                            }
                        }
                        String string = jSONObject2.getString("access_token");
                        h.a(f26936b, " accesstoken " + string);
                        String str3 = new String(Base64.decode(string, 2));
                        h.a(f26936b, " servers " + str3);
                        JSONArray jSONArray = new JSONArray(str3);
                        this.f26937a.C().a(0);
                        if (jSONArray.length() > 0) {
                            this.f26937a.C().a();
                            boolean z4 = false;
                            for (int i6 = 0; i6 < jSONArray.length(); i6++) {
                                JSONObject jSONObject7 = jSONArray.getJSONObject(i6);
                                String string2 = jSONObject7.getString("singal");
                                long j2 = 0;
                                try {
                                    String strSubstring = string2.substring(0, string2.indexOf(StrPool.DOT));
                                    h.a(f26936b, " sigurl " + string2 + " ipstr " + strSubstring);
                                    j2 = Long.parseLong(strSubstring, 16);
                                    strA = c.e.a(Long.valueOf(j2));
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                    strA = str2;
                                }
                                String string3 = jSONObject7.getString("port");
                                this.f26937a.C().a(string2, strA, string3);
                                if (!z4) {
                                    h.a(f26936b, " ipNumber " + j2 + " ip " + strA + " port " + string3);
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append("wss://");
                                    sb2.append(string2);
                                    sb2.append(":");
                                    sb2.append(string3);
                                    sb2.append("/ws");
                                    String string4 = sb2.toString();
                                    if (z2) {
                                        ((h.e) this.f26937a.m()).a(c.e.a(), this.f26937a.u().getRoomId(), i2, false);
                                        h.a(f26936b, "change gateway and rejoin room 5000 s later");
                                        this.f26937a.m().a(string4, strA, h.d.RECON_RS_REQUEST_SIGNAL, 5000);
                                    } else {
                                        this.f26937a.m().a(string4, strA);
                                    }
                                    z4 = true;
                                }
                            }
                            Iterator<i.a> it = this.f26937a.C().c().iterator();
                            while (it.hasNext()) {
                                h.a(f26936b, "cache server url: " + it.next().toString());
                            }
                            return;
                        }
                        if (this.f26937a.h() != null) {
                            this.f26937a.a(f.h.LOGIC_ENGINE_INIT);
                            JSONObject jSONObject8 = new JSONObject();
                            JSONObject jSONObject9 = new JSONObject();
                            jSONObject8.put("code", 5000);
                            jSONObject8.put("msg", "get region addr failed");
                            jSONObject9.put("uid", this.f26937a.u().getUId());
                            jSONObject9.put("roomid", this.f26937a.u().getRoomId());
                            jSONObject8.put("data", jSONObject9);
                            this.f26937a.h().F(jSONObject8.toString());
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (this.f26937a.h() != null) {
                    this.f26937a.a(f.h.LOGIC_ENGINE_INIT);
                    JSONObject jSONObject10 = new JSONObject();
                    JSONObject jSONObject11 = new JSONObject();
                    jSONObject10.put("code", 5000);
                    jSONObject10.put("msg", "get gateway failed");
                    jSONObject11.put("uid", this.f26937a.u().getUId());
                    jSONObject11.put("roomid", this.f26937a.u().getRoomId());
                    jSONObject10.put("data", jSONObject11);
                    this.f26937a.h().F(jSONObject10.toString());
                    return;
                }
                return;
            } catch (Exception e3) {
                e3.printStackTrace();
                return;
            }
        }
        if (this.f26937a.m() == null) {
            e.b bVar2 = this.f26937a;
            bVar2.a(bVar2.a((f.d) bVar2));
            this.f26937a.m().a(h.g.f27082h, "");
        }
    }

    public void b(f.f fVar, int i2) throws JSONException {
        if (this.f26937a.m() == null || !(this.f26937a.m() instanceof h.e)) {
            return;
        }
        String strA = c.e.a();
        ((h.e) this.f26937a.m()).e(strA, this.f26937a.u().getAppId(), this.f26937a.u().getRoomId(), this.f26937a.u().getUId(), fVar.f26898a);
        e.b bVar = this.f26937a;
        bVar.a(strA, bVar.i().a(c.i.f2287r), Integer.valueOf(i2));
    }

    public void b(String str, boolean z2) throws JSONException {
        try {
            if (this.f26937a.y().c(str) == null) {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject.put("code", 5012);
                jSONObject.put("msg", "not have sub this user media");
                jSONObject2.put("uid", str);
                jSONObject2.put("mtype", 2);
                jSONObject2.put("ttype", 2);
                jSONObject2.put(c.i.f2279j, z2);
                jSONObject.put("data", jSONObject2);
                if (this.f26937a.h() != null) {
                    this.f26937a.h().e(jSONObject.toString());
                    return;
                }
                return;
            }
            String strA = this.f26937a.y().a(str + 2);
            if (strA != null && !strA.equals("")) {
                e.d dVar = this.f26937a.D().get(strA);
                if (dVar == null) {
                    JSONObject jSONObject3 = new JSONObject();
                    JSONObject jSONObject4 = new JSONObject();
                    jSONObject3.put("code", 5013);
                    jSONObject3.put("msg", "not sub this media");
                    jSONObject4.put("uid", str);
                    jSONObject4.put("mtype", 2);
                    jSONObject4.put("ttype", 2);
                    jSONObject4.put(c.i.f2279j, z2);
                    jSONObject3.put("data", jSONObject4);
                    if (this.f26937a.h() != null) {
                        this.f26937a.h().e(jSONObject3.toString());
                        return;
                    }
                    return;
                }
                String str2 = dVar.f26759a;
                String strA2 = c.e.a();
                if (this.f26937a.m() != null) {
                    ((h.e) this.f26937a.m()).a(strA2, str, str2, 2, 2, z2);
                    e.b bVar = this.f26937a;
                    bVar.a(strA2, bVar.i().a(e.f0.S));
                    return;
                }
                JSONObject jSONObject5 = new JSONObject();
                JSONObject jSONObject6 = new JSONObject();
                jSONObject5.put("code", 5001);
                jSONObject5.put("msg", "server disconnect");
                jSONObject6.put("uid", str);
                jSONObject6.put("mtype", 2);
                jSONObject6.put("ttype", 2);
                jSONObject6.put(c.i.f2279j, z2);
                jSONObject5.put("data", jSONObject6);
                if (this.f26937a.h() != null) {
                    this.f26937a.h().e(jSONObject5.toString());
                    return;
                }
                return;
            }
            JSONObject jSONObject7 = new JSONObject();
            JSONObject jSONObject8 = new JSONObject();
            jSONObject7.put("code", 5013);
            jSONObject7.put("msg", "not sub this media");
            jSONObject8.put("uid", str);
            jSONObject8.put("mtype", 2);
            jSONObject8.put("ttype", 2);
            jSONObject8.put(c.i.f2279j, z2);
            jSONObject7.put("data", jSONObject8);
            if (this.f26937a.h() != null) {
                this.f26937a.h().e(jSONObject7.toString());
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final void a(String str, String str2, e.d dVar) throws JSONException {
        h.a(f26936b, " subPubMap size " + this.f26937a.E().size());
        for (String str3 : this.f26937a.E().keySet()) {
            h.a(f26936b, " subPubMap key: " + str3 + " value:" + this.f26937a.E().get(str3));
        }
        h.a(f26936b, " streamid " + str2 + " substreamid " + str + "");
        StringBuilder sb = new StringBuilder();
        sb.append(" uclient ");
        sb.append(dVar);
        h.a(f26936b, sb.toString());
        if (dVar != null) {
            j jVar = dVar.f26764f;
            if ((jVar == j.STREAM_STATUS_READY || jVar == j.STREAM_STATUS_FAILED) && !dVar.f26769k) {
                dVar.f26770l = true;
                h.a(f26936b, " unSubStreamBySwitchSignal uclient.mIsrecon = true: ");
                if (this.f26937a.m() != null) {
                    ((h.e) this.f26937a.m()).a(c.e.a(), this.f26937a.u().getUId(), dVar.f26761c, str2, str, dVar.f26763e, false);
                    this.f26937a.c(str, dVar.f26762d, dVar.f26763e);
                    try {
                        if (this.f26937a.h() != null) {
                            JSONObject jSONObject = new JSONObject();
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject.put("code", "0");
                            jSONObject.put("msg", "switch signal unsubscribe");
                            jSONObject2.put("mtype", dVar.f26763e);
                            jSONObject2.put("uid", dVar.f26761c);
                            jSONObject.put("data", jSONObject2);
                        }
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    public void a(String str) throws JSONException {
        h.a(f26936b, " onSendMsgHandler " + str);
        try {
            e.e eVarRemove = this.f26937a.d().remove(new JSONObject(str).getString("rpc_id"));
            if (eVarRemove == null || eVarRemove.a() == null) {
                return;
            }
            eVarRemove.a().b(str, eVarRemove.b());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX WARN: Not initialized variable reg: 18, insn: 0x01fe: MOVE (r2 I:??[OBJECT, ARRAY]) = (r18 I:??[OBJECT, ARRAY]), block:B:40:0x01fe */
    public void a(JSONObject jSONObject) throws JSONException {
        String str;
        e.d dVar;
        String str2;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        String str3;
        String str4 = "mtype";
        try {
            h.a(f26936b, " onPeerConnected  " + jSONObject.toString());
            int i2 = jSONObject.getInt(CommonNetImpl.STYPE);
            int i3 = jSONObject.getInt("mtype");
            String string = jSONObject.getString("streamid");
            jSONObject.getInt("status");
            try {
                if (i2 == 1) {
                    h.a(f26936b, "pub type" + i2);
                    try {
                        if (i3 == 1) {
                            if (this.f26937a.v() != null) {
                                h.a(f26936b, "pub video type");
                                boolean z7 = this.f26937a.v().f26770l;
                                this.f26937a.v().f26764f = j.STREAM_STATUS_READY;
                                if (this.f26937a.h() != null) {
                                    JSONObject jSONObject2 = new JSONObject();
                                    JSONObject jSONObject3 = new JSONObject();
                                    jSONObject2.put("code", 0);
                                    jSONObject2.put("msg", "");
                                    jSONObject3.put(CommonNetImpl.STYPE, i2);
                                    jSONObject3.put("uid", this.f26937a.u().getUId());
                                    jSONObject3.put("mtype", i3);
                                    jSONObject3.put("audio", this.f26937a.v().f26766h);
                                    jSONObject3.put("video", this.f26937a.v().f26765g);
                                    jSONObject3.put("mutevideo", this.f26937a.v().f26768j);
                                    jSONObject3.put("muteaudio", this.f26937a.v().f26767i);
                                    jSONObject2.put("data", jSONObject3);
                                    if (z7) {
                                        h.a(f26936b, "set publish reconnect = false");
                                        this.f26937a.v().f26770l = false;
                                        this.f26937a.h().l(jSONObject2.toString());
                                    } else {
                                        this.f26937a.h().k(jSONObject2.toString());
                                    }
                                }
                                j.d.d().b(this.f26937a.v().f26759a, true);
                                return;
                            }
                            h.a(f26936b, "pub video is failed ,camera client is null");
                            return;
                        }
                        if (i3 == 2) {
                            if (this.f26937a.B() != null) {
                                h.a(f26936b, "pub screen type");
                                boolean z8 = this.f26937a.B().f26770l;
                                this.f26937a.B().f26764f = j.STREAM_STATUS_READY;
                                if (this.f26937a.h() != null) {
                                    JSONObject jSONObject4 = new JSONObject();
                                    JSONObject jSONObject5 = new JSONObject();
                                    jSONObject4.put("code", 0);
                                    jSONObject4.put("msg", "");
                                    jSONObject5.put(CommonNetImpl.STYPE, i2);
                                    jSONObject5.put("uid", this.f26937a.u().getUId());
                                    jSONObject5.put("mtype", i3);
                                    jSONObject5.put("audio", this.f26937a.B().f26766h);
                                    jSONObject5.put("video", this.f26937a.B().f26765g);
                                    jSONObject5.put("mutevideo", this.f26937a.B().f26768j);
                                    jSONObject5.put("muteaudio", this.f26937a.B().f26767i);
                                    jSONObject4.put("data", jSONObject5);
                                    if (z8) {
                                        h.a(f26936b, "set screen reconnect = false");
                                        this.f26937a.B().f26770l = false;
                                        this.f26937a.h().l(jSONObject4.toString());
                                    } else {
                                        this.f26937a.h().k(jSONObject4.toString());
                                    }
                                }
                                j.d.d().b(this.f26937a.B().f26759a, true);
                                return;
                            }
                            h.a(f26936b, "pub screen is failed ,screen client is null");
                            return;
                        }
                        return;
                    } catch (Exception e2) {
                        e = e2;
                        str4 = str3;
                    }
                } else {
                    if (i2 != 2) {
                        return;
                    }
                    h.a(f26936b, "sub  type streamid " + string);
                    String str5 = this.f26937a.E().get(string);
                    h.a(f26936b, "sub  type streamsubid" + str5);
                    if (TextUtils.isEmpty(str5)) {
                        return;
                    }
                    e.d dVar2 = this.f26937a.D().get(str5);
                    if (dVar2 != null) {
                        boolean z9 = dVar2.f26770l;
                        String str6 = dVar2.f26761c;
                        dVar2.f26764f = j.STREAM_STATUS_READY;
                        boolean z10 = dVar2.f26765g;
                        boolean z11 = dVar2.f26766h;
                        z5 = dVar2.f26768j;
                        z6 = dVar2.f26767i;
                        str = str6;
                        z3 = z11;
                        z4 = z9;
                        dVar = dVar2;
                        z2 = z10;
                        str2 = f26936b;
                    } else {
                        str = "";
                        dVar = dVar2;
                        str2 = f26936b;
                        z2 = false;
                        z3 = false;
                        z4 = false;
                        z5 = false;
                        z6 = false;
                    }
                    try {
                        try {
                            if (this.f26937a.h() != null) {
                                JSONObject jSONObject6 = new JSONObject();
                                try {
                                    JSONObject jSONObject7 = new JSONObject();
                                    jSONObject6.put("code", 0);
                                    jSONObject6.put("msg", "");
                                    jSONObject7.put("uid", str);
                                    jSONObject7.put(CommonNetImpl.STYPE, i2);
                                    jSONObject7.put("mtype", i3);
                                    jSONObject7.put("audio", z3);
                                    jSONObject7.put("video", z2);
                                    jSONObject7.put("muteaudio", z6);
                                    jSONObject7.put("mutevideo", z5);
                                    jSONObject6.put("data", jSONObject7);
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("reconnect sub type ");
                                    boolean z12 = z4;
                                    sb.append(z12);
                                    str4 = str2;
                                    try {
                                        h.a(str4, sb.toString());
                                        if (z12) {
                                            h.a(str4, "set sub reconnect = false");
                                            dVar.f26770l = false;
                                            this.f26937a.h().l(jSONObject6.toString());
                                        } else {
                                            this.f26937a.h().H(jSONObject6.toString());
                                        }
                                    } catch (Exception e3) {
                                        e = e3;
                                    }
                                } catch (Exception e4) {
                                    e = e4;
                                    str4 = str2;
                                    e.printStackTrace();
                                    h.a(str4, "peer connect handle failed");
                                }
                            } else {
                                str4 = str2;
                            }
                            j.d.d().b(string, true);
                            return;
                        } catch (Exception e5) {
                            e = e5;
                        }
                    } catch (Exception e6) {
                        e = e6;
                    }
                }
            } catch (Exception e7) {
                e = e7;
                str4 = "data";
            }
        } catch (Exception e8) {
            e = e8;
            str4 = f26936b;
        }
        e.printStackTrace();
        h.a(str4, "peer connect handle failed");
    }

    public final void a(int i2, int i3, String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("mediatype", i3);
            jSONObject.put("streamtype", i2);
            if (i2 == 1) {
                if (i3 == 1) {
                    if (this.f26937a.v() != null) {
                        jSONObject.put("uid", this.f26937a.v().f26761c);
                        jSONObject.put("audio", this.f26937a.v().f26766h);
                        jSONObject.put("video", this.f26937a.v().f26765g);
                        jSONObject.put("muteaudio", this.f26937a.v().f26767i);
                        jSONObject.put("mutevideo", this.f26937a.v().f26768j);
                    }
                } else if (i3 == 2 && this.f26937a.B() != null) {
                    jSONObject.put("uid", this.f26937a.B().f26761c);
                    jSONObject.put("audio", this.f26937a.B().f26766h);
                    jSONObject.put("video", this.f26937a.B().f26765g);
                    jSONObject.put("muteaudio", this.f26937a.B().f26767i);
                    jSONObject.put("mutevideo", this.f26937a.B().f26768j);
                }
            } else if (i2 == 2) {
                String str2 = this.f26937a.E().get(str);
                h.a(f26936b, "streamid : " + str2);
                e.d dVar = this.f26937a.D().get(str2);
                h.a(f26936b, "reportPeerLostConnection: " + dVar);
                if (dVar != null) {
                    h.a(f26936b, dVar.toString());
                    jSONObject.put("uid", dVar.f26761c);
                    jSONObject.put("audio", dVar.f26766h);
                    jSONObject.put("video", dVar.f26765g);
                    jSONObject.put("muteaudio", dVar.f26767i);
                    jSONObject.put("mutevideo", dVar.f26768j);
                    this.f26937a.h().B(jSONObject.toString());
                } else {
                    h.a(f26936b, "report peerLostConnection remote client is null");
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public String a(String str, String str2, String str3, String str4, int i2) throws Throwable {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(AliyunVodKey.KEY_VOD_ACTION, str4);
        jSONObject.put("rpc_id", c.e.a());
        jSONObject.put("app_id", str);
        jSONObject.put("room_id", str2);
        jSONObject.put("user_id", str3);
        if (i2 >= 0) {
            jSONObject.put("role", i2);
        }
        jSONObject.put("token", this.f26937a.u().getToken());
        String strB = c.e.b(c.e.e());
        this.f26937a.F().f(strB);
        h.a(f26936b, "Network type is : " + strB);
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, this.f26937a.F().l());
        jSONObject2.put("agent", this.f26937a.F().b());
        jSONObject2.put(com.alipay.sdk.packet.d.f3298n, this.f26937a.F().e());
        jSONObject2.put("system", this.f26937a.F().m());
        jSONObject2.put("network", this.f26937a.F().k());
        jSONObject2.put(am.f22460w, this.f26937a.F().c());
        jSONObject2.put("mem", this.f26937a.F().h());
        jSONObject.put("devinfo", Base64.encodeToString(jSONObject2.toString().getBytes(), 2));
        String strA = h.a.b().a(c.e.c(), jSONObject.toString());
        h.a(f26936b, "requrl :" + c.e.c() + " res " + strA);
        return strA;
    }

    public void a(f.f fVar, int i2) throws JSONException {
        if (this.f26937a.m() == null || !(this.f26937a.m() instanceof h.e)) {
            return;
        }
        String strA = c.e.a();
        ((h.e) this.f26937a.m()).d(strA, this.f26937a.u().getAppId(), this.f26937a.u().getRoomId(), this.f26937a.u().getUId(), fVar.f26898a);
        e.b bVar = this.f26937a;
        bVar.a(strA, bVar.i().a(c.i.f2286q), Integer.valueOf(i2));
    }

    public void a(f.f fVar) throws JSONException {
        if (this.f26937a.m() == null || !(this.f26937a.m() instanceof h.e)) {
            return;
        }
        String strA = c.e.a();
        ((h.e) this.f26937a.m()).a(strA, this.f26937a.u().getAppId(), this.f26937a.u().getRoomId(), this.f26937a.u().getUId(), fVar.f26898a);
        e.b bVar = this.f26937a;
        bVar.a(strA, bVar.i().a(c.i.f2288s));
    }

    public void a(String str, boolean z2) throws JSONException {
        try {
            if (this.f26937a.y().c(str) == null) {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject.put("code", 5012);
                jSONObject.put("msg", "not have sub this user media");
                jSONObject2.put("uid", str);
                jSONObject2.put("mtype", 1);
                jSONObject2.put("ttype", 1);
                jSONObject2.put(c.i.f2279j, z2);
                jSONObject.put("data", jSONObject2);
                if (this.f26937a.h() != null) {
                    this.f26937a.h().e(jSONObject.toString());
                    return;
                }
                return;
            }
            String strA = this.f26937a.y().a(str + a.h.f26636f);
            if (strA != null && !strA.equals("")) {
                e.d dVar = this.f26937a.D().get(strA);
                if (dVar == null) {
                    JSONObject jSONObject3 = new JSONObject();
                    JSONObject jSONObject4 = new JSONObject();
                    jSONObject3.put("code", 5013);
                    jSONObject3.put("msg", "not sub this media");
                    jSONObject4.put("uid", str);
                    jSONObject4.put("mtype", 1);
                    jSONObject4.put("ttype", 1);
                    jSONObject4.put(c.i.f2279j, z2);
                    jSONObject3.put("data", jSONObject4);
                    if (this.f26937a.h() != null) {
                        this.f26937a.h().e(jSONObject3.toString());
                        return;
                    }
                    return;
                }
                String str2 = dVar.f26759a;
                String strA2 = c.e.a();
                if (this.f26937a.m() != null) {
                    ((h.e) this.f26937a.m()).a(strA2, str, str2, 2, 1, z2);
                    e.b bVar = this.f26937a;
                    bVar.a(strA2, bVar.i().a(e.f0.R));
                    return;
                }
                JSONObject jSONObject5 = new JSONObject();
                JSONObject jSONObject6 = new JSONObject();
                jSONObject5.put("code", 5001);
                jSONObject5.put("msg", "server disconnect");
                jSONObject6.put("uid", str);
                jSONObject6.put("mtype", 1);
                jSONObject6.put("ttype", 1);
                jSONObject6.put(c.i.f2279j, z2);
                jSONObject5.put("data", jSONObject6);
                if (this.f26937a.h() != null) {
                    this.f26937a.h().e(jSONObject5.toString());
                    return;
                }
                return;
            }
            JSONObject jSONObject7 = new JSONObject();
            JSONObject jSONObject8 = new JSONObject();
            jSONObject7.put("code", 5013);
            jSONObject7.put("msg", "not sub this media");
            jSONObject8.put("uid", str);
            jSONObject8.put("mtype", 1);
            jSONObject8.put("ttype", 1);
            jSONObject8.put(c.i.f2279j, z2);
            jSONObject7.put("data", jSONObject8);
            if (this.f26937a.h() != null) {
                this.f26937a.h().e(jSONObject7.toString());
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }
}

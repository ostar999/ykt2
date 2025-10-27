package io.agora.rtc.live;

import io.agora.rtc.video.AgoraImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes8.dex */
public class LiveTranscoding {

    @Deprecated
    public int userCount;
    public int width = 360;
    public int height = 640;
    public int videoBitrate = 400;
    public VideoCodecProfileType videoCodecProfile = VideoCodecProfileType.HIGH;
    public VideoCodecType videoCodecType = VideoCodecType.H264;
    public int videoGop = 30;
    public int videoFramerate = 15;
    public AgoraImage watermark = new AgoraImage();
    public AgoraImage backgroundImage = new AgoraImage();

    @Deprecated
    public boolean lowLatency = false;
    public AudioSampleRateType audioSampleRate = AudioSampleRateType.TYPE_44100;
    public int audioBitrate = 48;
    public int audioChannels = 1;
    public AudioCodecProfileType audioCodecProfile = AudioCodecProfileType.LC_AAC;
    private Map<Integer, TranscodingUser> transcodingUsers = new HashMap();

    @Deprecated
    public int backgroundColor = -16777216;
    public String userConfigExtraInfo = null;

    @Deprecated
    public String metadata = null;

    public enum AudioCodecProfileType {
        LC_AAC(0),
        HE_AAC(1);

        private int value;

        AudioCodecProfileType(int v2) {
            this.value = v2;
        }

        public static int getValue(AudioCodecProfileType type) {
            return type.value;
        }
    }

    public enum AudioSampleRateType {
        TYPE_32000(32000),
        TYPE_44100(44100),
        TYPE_48000(48000);

        private int value;

        AudioSampleRateType(int v2) {
            this.value = v2;
        }

        public static int getValue(AudioSampleRateType type) {
            return type.value;
        }
    }

    public static class TranscodingUser {
        public float alpha = 1.0f;
        public int audioChannel;
        public int height;
        public int uid;
        public int width;

        /* renamed from: x, reason: collision with root package name */
        public int f27142x;

        /* renamed from: y, reason: collision with root package name */
        public int f27143y;
        public int zOrder;
    }

    public enum VideoCodecProfileType {
        BASELINE(66),
        MAIN(77),
        HIGH(100);

        private int value;

        VideoCodecProfileType(int v2) {
            this.value = v2;
        }

        public static int getValue(VideoCodecProfileType type) {
            return type.value;
        }
    }

    public enum VideoCodecType {
        H264(1),
        H265(2);

        private int value;

        VideoCodecType(int v2) {
            this.value = v2;
        }

        public static int getValue(VideoCodecType type) {
            return type.value;
        }
    }

    public int addUser(TranscodingUser user) {
        int i2;
        if (user == null || (i2 = user.uid) == 0) {
            return -2;
        }
        this.transcodingUsers.put(Integer.valueOf(i2), user);
        this.userCount = this.transcodingUsers.size();
        return 0;
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    @Deprecated
    public int getBlue() {
        return this.backgroundColor & 255;
    }

    @Deprecated
    public int getGreen() {
        return (this.backgroundColor >> 8) & 255;
    }

    @Deprecated
    public int getRed() {
        return (this.backgroundColor >> 16) & 255;
    }

    public int getUserCount() {
        return this.transcodingUsers.size();
    }

    public final ArrayList<TranscodingUser> getUsers() {
        return new ArrayList<>(this.transcodingUsers.values());
    }

    public int removeUser(int uid) {
        if (!this.transcodingUsers.containsKey(Integer.valueOf(uid))) {
            return -2;
        }
        this.transcodingUsers.remove(Integer.valueOf(uid));
        this.userCount = this.transcodingUsers.size();
        return 0;
    }

    public void setBackgroundColor(int color) {
        this.backgroundColor = color;
    }

    @Deprecated
    public void setBlue(int blue) {
        int i2 = blue << 0;
        this.backgroundColor = i2 | (getRed() << 16) | (getGreen() << 8);
    }

    @Deprecated
    public void setGreen(int green) {
        int i2 = green << 8;
        this.backgroundColor = i2 | (getRed() << 16) | (getBlue() << 0);
    }

    @Deprecated
    public void setRed(int red) {
        this.backgroundColor = (red << 16) | (getGreen() << 8) | (getBlue() << 0);
    }

    public void setUsers(ArrayList<TranscodingUser> users) {
        this.transcodingUsers.clear();
        if (users != null) {
            Iterator<TranscodingUser> it = users.iterator();
            while (it.hasNext()) {
                TranscodingUser next = it.next();
                this.transcodingUsers.put(Integer.valueOf(next.uid), next);
            }
        }
        this.userCount = this.transcodingUsers.size();
    }

    public void setBackgroundColor(int red, int green, int blue) {
        this.backgroundColor = (red << 16) | (green << 8) | (blue << 0);
    }

    public void setUsers(Map<Integer, TranscodingUser> users) {
        this.transcodingUsers.clear();
        if (users != null) {
            this.transcodingUsers.putAll(users);
        }
        this.userCount = this.transcodingUsers.size();
    }
}

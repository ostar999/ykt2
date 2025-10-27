package core.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes8.dex */
public class MixProfile {
    public static final int ADD_STREAM_MODE_AUTO = 1;
    public static final int ADD_STREAM_MODE_MANUAL = 2;
    public static final String AUDIO_CODEC_AAC = "aac";
    public static final int LAYOUT_AVERAGE_1 = 1;
    public static final int LAYOUT_AVERAGE_2 = 5;
    public static final int LAYOUT_CLASS_ROOM_1 = 2;
    public static final int LAYOUT_CLASS_ROOM_2 = 4;
    public static final int LAYOUT_CUSTOM = 3;
    public static final int MIME_TYPE_AUDIO = 1;
    public static final int MIME_TYPE_AUDIO_VIDEO = 0;
    public static final int MIX_TYPE_RECORD = 2;
    public static final int MIX_TYPE_RELAY = 1;
    public static final int MIX_TYPE_UPDATE = 4;
    public static final String QUALITY_H264_B = "B";
    public static final String QUALITY_H264_CB = "CB";
    public static final String QUALITY_H264_E = "E";
    public static final String QUALITY_H264_H = "H";
    public static final String QUALITY_H264_M = "M";
    public static final boolean RECORD_UNEVEN = false;
    public static final String VIDEO_CODEC_H264 = "h264";
    public static final String VIDEO_CODEC_H265 = "h265";
    public static final int WATER_POS_LEFT_BOTTOM = 2;
    public static final int WATER_POS_LEFT_TOP = 1;
    public static final int WATER_POS_RIGHT_BOTTOM = 4;
    public static final int WATER_POS_RIGHT_TOP = 3;
    public static final int WATER_TYPE_IMG = 2;
    public static final int WATER_TYPE_NO = 0;
    public static final int WATER_TYPE_TEXT = 3;
    public static final int WATER_TYPE_TIME = 1;
    private static MixProfile instance;
    private String audioCodec;
    private JSONObject bgColor;
    private int bitrate;
    private String bucket;
    private JSONArray custom;
    private JSONObject expand;
    private int frameRate;
    private int height;
    private String keyUser;
    private int layout;
    private int layoutUserLimit;
    private int mainViewType;
    private String mainViewUserId;
    private int mimeType;
    private JSONArray pushUrl;
    private String qualityLevel;
    private String region;
    private int streamMode;
    private JSONArray streams;
    private int taskTimeout;
    private int type;
    private String videoCodec;
    private int waterPosition;
    private int waterType;
    private String waterUrl;
    private int width;

    public class MixParamsBuilder<T extends MixParamsBuilder> {
        public String mAudioCodec;
        public JSONObject mBgColor;
        public int mBitrate;
        public String mBucket;
        public JSONArray mCustom;
        public int mFrameRate;
        public int mHeight;
        public String mKeyUser;
        public int mLayout;
        public int mLayoutUserLimit;
        public int mMainViewType;
        public String mMainViewUserId;
        public int mMimeType;
        public JSONArray mPushUrl;
        public String mQualityLevel;
        public String mRegion;
        public int mStreamMode;
        public JSONArray mStreams;
        public int mTaskTimeout;
        public int mType;
        public String mVideoCodec;
        public int mWaterPosition;
        public int mWaterType;
        public String mWaterUrl;
        public int mWidth;

        public MixParamsBuilder() throws JSONException {
            this.mType = 2;
            this.mStreams = new JSONArray();
            this.mLayoutUserLimit = -1;
            this.mPushUrl = new JSONArray();
            this.mLayout = 4;
            this.mCustom = new JSONArray();
            this.mBgColor = null;
            this.mFrameRate = 15;
            this.mBitrate = 500;
            this.mVideoCodec = "h264";
            this.mQualityLevel = "CB";
            this.mAudioCodec = "aac";
            this.mMainViewUserId = "";
            this.mMainViewType = 1;
            this.mWidth = 640;
            this.mHeight = 480;
            this.mRegion = "";
            this.mBucket = "";
            this.mWaterType = 0;
            this.mWaterPosition = 1;
            this.mWaterUrl = "";
            this.mMimeType = 0;
            this.mStreamMode = 1;
            this.mKeyUser = "";
            this.mTaskTimeout = 60;
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("r", 0);
                jSONObject.put("g", 0);
                jSONObject.put("b", 0);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            this.mBgColor = jSONObject;
        }

        public T Bucket(String str) {
            this.mBucket = str;
            return this;
        }

        public T WaterType(int i2) {
            this.mWaterType = i2;
            return this;
        }

        public T addPushUrl(String str) {
            this.mPushUrl.put(str);
            return this;
        }

        public T addStream(String str, int i2) throws JSONException {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("user_id", str);
                jSONObject.put("media_type", i2);
                this.mStreams.put(jSONObject);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            return this;
        }

        public T addStreamMode(int i2) {
            this.mStreamMode = i2;
            return this;
        }

        public T audioCodec(String str) {
            this.mAudioCodec = str;
            return this;
        }

        public T bgColor(JSONObject jSONObject) {
            this.mBgColor = jSONObject;
            return this;
        }

        public T bitRate(int i2) {
            this.mBitrate = i2;
            return this;
        }

        public MixProfile build() {
            return new MixProfile(this.mType, this.mStreams, this.mPushUrl, this.mLayout, this.mCustom, this.mBgColor, this.mFrameRate, this.mBitrate, this.mVideoCodec, this.mQualityLevel, this.mAudioCodec, this.mMainViewUserId, this.mMainViewType, this.mWidth, this.mHeight, this.mRegion, this.mBucket, this.mWaterType, this.mWaterPosition, this.mWaterUrl, this.mMimeType, this.mStreamMode, this.mLayoutUserLimit, this.mKeyUser, this.mTaskTimeout);
        }

        public T custom(JSONArray jSONArray) {
            this.mCustom = jSONArray;
            return this;
        }

        public T frameRate(int i2) {
            this.mFrameRate = i2;
            return this;
        }

        public T keyUser(String str) {
            this.mKeyUser = str;
            return this;
        }

        public T layout(int i2) {
            this.mLayout = i2;
            return this;
        }

        public T layoutUserLimit(int i2) {
            if (i2 > 0) {
                this.mLayoutUserLimit = i2;
            }
            return this;
        }

        public T mainViewMediaType(int i2) {
            this.mMainViewType = i2;
            return this;
        }

        public T mainViewUserId(String str) {
            this.mMainViewUserId = str;
            return this;
        }

        public T mimeType(int i2) {
            this.mMimeType = i2;
            return this;
        }

        public T pushUrl(JSONArray jSONArray) {
            this.mPushUrl = jSONArray;
            return this;
        }

        public T qualityLevel(String str) {
            this.mQualityLevel = str;
            return this;
        }

        public T region(String str) {
            this.mRegion = str;
            return this;
        }

        public T resolution(int i2, int i3) {
            if (i2 > 0 && i3 > 0) {
                this.mWidth = i2;
                this.mHeight = i3;
            }
            return this;
        }

        public T streams(JSONArray jSONArray) {
            this.mStreams = jSONArray;
            return this;
        }

        public T taskTimeOut(int i2) {
            if (i2 > 60) {
                this.mTaskTimeout = i2;
            }
            return this;
        }

        public T type(int i2) {
            this.mType = i2;
            return this;
        }

        public T videoCodec(String str) {
            this.mVideoCodec = str;
            return this;
        }

        public T waterPosition(int i2) {
            this.mWaterPosition = i2;
            return this;
        }

        public T waterURL(String str) {
            this.mWaterUrl = str;
            return this;
        }

        public T bgColor(int i2, int i3, int i4) throws JSONException {
            try {
                this.mBgColor.put("r", i2);
                this.mBgColor.put("g", i3);
                this.mBgColor.put("b", i4);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            return this;
        }

        public MixParamsBuilder(int i2) throws JSONException {
            this.mType = 2;
            this.mStreams = new JSONArray();
            this.mLayoutUserLimit = -1;
            this.mPushUrl = new JSONArray();
            this.mLayout = 4;
            this.mCustom = new JSONArray();
            this.mBgColor = null;
            this.mFrameRate = 15;
            this.mBitrate = 500;
            this.mVideoCodec = "h264";
            this.mQualityLevel = "CB";
            this.mAudioCodec = "aac";
            this.mMainViewUserId = "";
            this.mMainViewType = 1;
            this.mWidth = 640;
            this.mHeight = 480;
            this.mRegion = "";
            this.mBucket = "";
            this.mWaterType = 0;
            this.mWaterPosition = 1;
            this.mWaterUrl = "";
            this.mMimeType = 0;
            this.mStreamMode = 1;
            this.mKeyUser = "";
            this.mTaskTimeout = 60;
            this.mType = i2;
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("r", 0);
                jSONObject.put("g", 0);
                jSONObject.put("b", 0);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            this.mBgColor = jSONObject;
        }
    }

    public MixProfile() throws JSONException {
        this.type = 2;
        this.streams = new JSONArray();
        this.layoutUserLimit = -1;
        this.pushUrl = new JSONArray();
        this.layout = 4;
        this.custom = new JSONArray();
        this.bgColor = null;
        this.frameRate = 15;
        this.bitrate = 500;
        this.videoCodec = "h264";
        this.qualityLevel = "CB";
        this.audioCodec = "aac";
        this.mainViewUserId = "";
        this.mainViewType = 1;
        this.width = 640;
        this.height = 480;
        this.region = "";
        this.bucket = "";
        this.waterType = 0;
        this.waterPosition = 1;
        this.waterUrl = "";
        this.mimeType = 0;
        this.streamMode = 1;
        this.keyUser = "";
        this.expand = new JSONObject();
        this.taskTimeout = 60;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("r", 0);
            jSONObject.put("g", 0);
            jSONObject.put("b", 0);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        this.bgColor = jSONObject;
    }

    public static MixProfile getInstance() {
        if (instance == null) {
            synchronized (MixProfile.class) {
                if (instance == null) {
                    instance = new MixProfile();
                }
            }
        }
        return instance;
    }

    public MixParamsBuilder assembleMixParamsBuilder() {
        return new MixParamsBuilder();
    }

    public MixParamsBuilder assembleRecordMixParamsBuilder() {
        return new MixParamsBuilder(2);
    }

    public MixParamsBuilder assembleRelayMixParamsBuilder() {
        return new MixParamsBuilder(1);
    }

    public MixParamsBuilder assembleUpdateMixParamsBuilder() {
        return new MixParamsBuilder(4);
    }

    public String getAudioCodec() {
        return this.audioCodec;
    }

    public JSONObject getBgColor() {
        return this.bgColor;
    }

    public int getBitrate() {
        return this.bitrate;
    }

    public String getBucket() {
        return this.bucket;
    }

    public JSONArray getCustom() {
        return this.custom;
    }

    public JSONObject getExpand() {
        return this.expand;
    }

    public int getFrameRate() {
        return this.frameRate;
    }

    public int getHeight() {
        return this.height;
    }

    public String getKeyUser() {
        return this.keyUser;
    }

    public int getLayout() {
        return this.layout;
    }

    public int getLayoutUserLimit() {
        return this.layoutUserLimit;
    }

    public int getMainViewType() {
        return this.mainViewType;
    }

    public String getMainViewUserId() {
        return this.mainViewUserId;
    }

    public int getMimeType() {
        return this.mimeType;
    }

    public JSONArray getPushUrl() {
        return this.pushUrl;
    }

    public String getQualityLevel() {
        return this.qualityLevel;
    }

    public String getRegion() {
        return this.region;
    }

    public int getStreamMode() {
        return this.streamMode;
    }

    public JSONArray getStreams() {
        return this.streams;
    }

    public int getTaskTimeout() {
        return this.taskTimeout;
    }

    public int getType() {
        return this.type;
    }

    public String getVideoCodec() {
        return this.videoCodec;
    }

    public int getWaterPosition() {
        return this.waterPosition;
    }

    public int getWaterType() {
        return this.waterType;
    }

    public String getWaterUrl() {
        return this.waterUrl;
    }

    public int getWidth() {
        return this.width;
    }

    public MixProfile(int i2, JSONArray jSONArray, JSONArray jSONArray2, int i3, JSONArray jSONArray3, JSONObject jSONObject, int i4, int i5, String str, String str2, String str3, String str4, int i6, int i7, int i8, String str5, String str6, int i9, int i10, String str7, int i11, int i12, int i13, String str8, int i14) throws JSONException {
        this.type = 2;
        this.streams = new JSONArray();
        this.layoutUserLimit = -1;
        this.pushUrl = new JSONArray();
        this.layout = 4;
        this.custom = new JSONArray();
        this.bgColor = null;
        this.frameRate = 15;
        this.bitrate = 500;
        this.videoCodec = "h264";
        this.qualityLevel = "CB";
        this.audioCodec = "aac";
        this.mainViewUserId = "";
        this.mainViewType = 1;
        this.width = 640;
        this.height = 480;
        this.region = "";
        this.bucket = "";
        this.waterType = 0;
        this.waterPosition = 1;
        this.waterUrl = "";
        this.mimeType = 0;
        this.streamMode = 1;
        this.keyUser = "";
        JSONObject jSONObject2 = new JSONObject();
        this.expand = jSONObject2;
        this.type = i2;
        this.streams = jSONArray;
        this.pushUrl = jSONArray2;
        this.layout = i3;
        this.custom = jSONArray3;
        this.bgColor = jSONObject;
        this.frameRate = i4;
        this.bitrate = i5;
        this.videoCodec = str;
        this.qualityLevel = str2;
        this.audioCodec = str3;
        this.mainViewUserId = str4;
        this.mainViewType = i6;
        this.width = i7;
        this.height = i8;
        this.region = str5;
        this.bucket = str6;
        this.waterType = i9;
        this.waterPosition = i10;
        this.waterUrl = str7;
        this.mimeType = i11;
        this.streamMode = i12;
        this.layoutUserLimit = i13;
        this.keyUser = str8;
        this.taskTimeout = i14;
        try {
            jSONObject2.put("taskTimeout", i14);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public <T extends MixParamsBuilder> MixProfile(MixParamsBuilder mixParamsBuilder) throws JSONException {
        this.type = 2;
        this.streams = new JSONArray();
        this.layoutUserLimit = -1;
        this.pushUrl = new JSONArray();
        this.layout = 4;
        this.custom = new JSONArray();
        this.bgColor = null;
        this.frameRate = 15;
        this.bitrate = 500;
        this.videoCodec = "h264";
        this.qualityLevel = "CB";
        this.audioCodec = "aac";
        this.mainViewUserId = "";
        this.mainViewType = 1;
        this.width = 640;
        this.height = 480;
        this.region = "";
        this.bucket = "";
        this.waterType = 0;
        this.waterPosition = 1;
        this.waterUrl = "";
        this.mimeType = 0;
        this.streamMode = 1;
        this.keyUser = "";
        JSONObject jSONObject = new JSONObject();
        this.expand = jSONObject;
        this.taskTimeout = 60;
        this.type = mixParamsBuilder.mType;
        this.streams = mixParamsBuilder.mStreams;
        this.pushUrl = mixParamsBuilder.mPushUrl;
        this.layout = mixParamsBuilder.mLayout;
        this.custom = mixParamsBuilder.mCustom;
        this.bgColor = mixParamsBuilder.mBgColor;
        this.frameRate = mixParamsBuilder.mFrameRate;
        this.bitrate = mixParamsBuilder.mBitrate;
        this.videoCodec = mixParamsBuilder.mVideoCodec;
        this.qualityLevel = mixParamsBuilder.mQualityLevel;
        this.audioCodec = mixParamsBuilder.mAudioCodec;
        this.mainViewUserId = mixParamsBuilder.mMainViewUserId;
        this.mainViewType = mixParamsBuilder.mMainViewType;
        this.width = mixParamsBuilder.mWidth;
        this.height = mixParamsBuilder.mHeight;
        this.region = mixParamsBuilder.mRegion;
        this.bucket = mixParamsBuilder.mBucket;
        this.waterType = mixParamsBuilder.mWaterType;
        this.waterPosition = mixParamsBuilder.mWaterPosition;
        this.waterUrl = mixParamsBuilder.mWaterUrl;
        this.mimeType = mixParamsBuilder.mMimeType;
        this.streamMode = mixParamsBuilder.mStreamMode;
        this.layoutUserLimit = mixParamsBuilder.mLayoutUserLimit;
        this.keyUser = mixParamsBuilder.mKeyUser;
        int i2 = mixParamsBuilder.mTaskTimeout;
        this.taskTimeout = i2;
        try {
            jSONObject.put("taskTimeout", i2);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }
}

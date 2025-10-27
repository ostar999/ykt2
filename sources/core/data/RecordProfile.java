package core.data;

/* loaded from: classes8.dex */
public class RecordProfile {
    public static final boolean RECORD_EVEN = true;
    public static final int RECORD_TEMPLET_1 = 1;
    public static final int RECORD_TEMPLET_2 = 2;
    public static final int RECORD_TEMPLET_3 = 3;
    public static final int RECORD_TEMPLET_4 = 4;
    public static final int RECORD_TEMPLET_5 = 5;
    public static final int RECORD_TEMPLET_6 = 6;
    public static final int RECORD_TEMPLET_7 = 7;
    public static final int RECORD_TEMPLET_8 = 8;
    public static final int RECORD_TEMPLET_9 = 9;
    public static final int RECORD_TYPE_AUDIO = 1;
    public static final int RECORD_TYPE_NO_WATER = 0;
    public static final int RECORD_TYPE_VIDEO = 2;
    public static final int RECORD_TYPE_VIDEO_AND_AUDIO = 3;
    public static final boolean RECORD_UNEVEN = false;
    public static final int RECORD_WATER_POS_LEFTBOTTOM = 2;
    public static final int RECORD_WATER_POS_LEFTTOP = 1;
    public static final int RECORD_WATER_POS_RIGHTBOTTOM = 4;
    public static final int RECORD_WATER_POS_RIGHTTOP = 3;
    public static final int RECORD_WATER_TYPE_IMG = 2;
    public static final int RECORD_WATER_TYPE_TEXT = 3;
    public static final int RECORD_WATER_TYPE_TIME = 1;
    private static RecordProfile instance;
    private String bucket;
    private boolean isaverage;
    private String mainViewUserId;
    private int mediaType;
    private int recordType;
    private String region;
    private int template;
    private int videoProfile;
    private String warterUrl;
    private int waterPosition;
    private int waterType;

    public class RecordParamsBuilder {
        private int mRecordType = 0;
        private String mUserId = "";
        private int mMediaType = 1;
        private String mRegion = "cn-bj";
        private String mBucket = "urtc-test";
        private int mVideoProfile = 1;
        private boolean mIsAverage = true;
        private int mWaterType = 1;
        private int mWaterPosition = 1;
        private String mWarterUrl = "";
        private int mTemplate = 1;

        public RecordParamsBuilder() {
        }

        public RecordParamsBuilder Average(boolean z2) {
            this.mIsAverage = z2;
            return this;
        }

        public RecordParamsBuilder Bucket(String str) {
            this.mBucket = str;
            return this;
        }

        public RecordParamsBuilder Template(int i2) {
            this.mTemplate = i2;
            return this;
        }

        public RecordParamsBuilder VideoProfile(int i2) {
            this.mVideoProfile = i2;
            return this;
        }

        public RecordParamsBuilder WarterUrl(String str) {
            this.mWarterUrl = str;
            return this;
        }

        public RecordParamsBuilder WaterPosition(int i2) {
            this.mWaterPosition = i2;
            return this;
        }

        public RecordParamsBuilder WaterType(int i2) {
            this.mWaterType = i2;
            return this;
        }

        public RecordProfile build() {
            return new RecordProfile(this.mRecordType, this.mMediaType, this.mRegion, this.mBucket, this.mVideoProfile, this.mIsAverage, this.mWaterType, this.mWaterPosition, this.mWarterUrl, this.mTemplate, this.mUserId);
        }

        public RecordParamsBuilder mainViewMediaType(int i2) {
            this.mMediaType = i2;
            return this;
        }

        public RecordParamsBuilder mainViewUserId(String str) {
            this.mUserId = str;
            return this;
        }

        public RecordParamsBuilder recordType(int i2) {
            this.mRecordType = i2;
            return this;
        }

        public RecordParamsBuilder region(String str) {
            this.mRegion = str;
            return this;
        }
    }

    public RecordProfile() {
    }

    public static RecordProfile getInstance() {
        if (instance == null) {
            synchronized (RecordProfile.class) {
                if (instance == null) {
                    instance = new RecordProfile();
                }
            }
        }
        return instance;
    }

    public boolean IsAverage() {
        return this.isaverage;
    }

    public RecordParamsBuilder assembleRecordBuilder() {
        return new RecordParamsBuilder();
    }

    public String getBucket() {
        return this.bucket;
    }

    public String getMainViewUserId() {
        return this.mainViewUserId;
    }

    public int getMediaType() {
        return this.mediaType;
    }

    public int getRecordType() {
        return this.recordType;
    }

    public String getRegion() {
        return this.region;
    }

    public int getTemplate() {
        return this.template;
    }

    public int getVideoProfile() {
        return this.videoProfile;
    }

    public String getWarterUrl() {
        return this.warterUrl;
    }

    public int getWaterPosition() {
        return this.waterPosition;
    }

    public int getWaterType() {
        return this.waterType;
    }

    public RecordProfile(int i2, int i3, String str, String str2, int i4, boolean z2, int i5, int i6, String str3, int i7, String str4) {
        this.recordType = i2;
        this.mainViewUserId = str4;
        this.mediaType = i3;
        this.region = str;
        this.bucket = str2;
        this.videoProfile = i4;
        this.isaverage = z2;
        this.waterType = i5;
        this.waterPosition = i6;
        this.warterUrl = str3;
        this.template = i7;
    }
}

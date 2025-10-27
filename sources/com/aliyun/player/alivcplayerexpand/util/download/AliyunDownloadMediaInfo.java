package com.aliyun.player.alivcplayerexpand.util.download;

import android.os.Parcel;
import android.text.TextUtils;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.aliyun.player.bean.ErrorCode;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.player.source.VidAuth;
import com.aliyun.player.source.VidSts;
import com.cicada.player.utils.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class AliyunDownloadMediaInfo {
    private static final String TAG = "AliyunDownloadMediaInfo";
    private ErrorCode errorCode;
    private String errorMsg;
    private int isEncripted;
    private boolean isSelected;
    private String mCoverUrl;
    private int mDownloadIndex;
    private long mDuration;
    private int mFileHandleProgress;
    private String mFormat;
    private int mProgress;
    private String mQuality;
    private int mQualityIndex;
    private String mSavePath;
    private long mSize;
    private Status mStatus;
    private String mTitle;
    private TrackInfo mTrackInfo;
    private String mTvCoverUrl;
    private String mTvId;
    private String mTvName;
    private String mVid;
    private VidSts mVidSts;
    private int mWatched;
    private int number;
    private VidAuth vidAuth;
    private int vidType;
    private int watchNumber;

    public enum Status {
        Idle,
        Prepare,
        Wait,
        Start,
        Stop,
        Complete,
        Error,
        Delete,
        File
    }

    public AliyunDownloadMediaInfo(Parcel parcel) {
        this.mProgress = 0;
        this.mSavePath = null;
        this.mDownloadIndex = 0;
        this.isEncripted = 0;
        this.mFileHandleProgress = 0;
        this.number = 1;
        this.watchNumber = 0;
        this.mVid = parcel.readString();
        this.mQuality = parcel.readString();
        this.mProgress = parcel.readInt();
        this.mSavePath = parcel.readString();
        this.mTitle = parcel.readString();
        this.mCoverUrl = parcel.readString();
        this.mDuration = parcel.readLong();
        this.mSize = parcel.readLong();
        this.mFormat = parcel.readString();
        this.mDownloadIndex = parcel.readInt();
        this.isEncripted = parcel.readInt();
        this.errorMsg = parcel.readString();
        this.mFileHandleProgress = parcel.readInt();
        this.mQualityIndex = parcel.readInt();
        this.mTvId = parcel.readString();
        this.isSelected = parcel.readByte() != 0;
    }

    private static JSONObject formatInfoToJsonobj(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) throws JSONException {
        if (aliyunDownloadMediaInfo == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("vid", aliyunDownloadMediaInfo.getVid());
            jSONObject.put(DatabaseManager.QUALITY, aliyunDownloadMediaInfo.getQuality());
            jSONObject.put("format", aliyunDownloadMediaInfo.getFormat());
            jSONObject.put("coverUrl", aliyunDownloadMediaInfo.getCoverUrl());
            jSONObject.put("duration", aliyunDownloadMediaInfo.getDuration());
            jSONObject.put("title", aliyunDownloadMediaInfo.getTitle());
            jSONObject.put("savePath", aliyunDownloadMediaInfo.getSavePath());
            jSONObject.put("status", aliyunDownloadMediaInfo.getStatus());
            jSONObject.put(DatabaseManager.SIZE, aliyunDownloadMediaInfo.getSize());
            jSONObject.put("progress", aliyunDownloadMediaInfo.getProgress());
            jSONObject.put("dIndex", aliyunDownloadMediaInfo.getDownloadIndex());
            jSONObject.put("encript", aliyunDownloadMediaInfo.isEncripted());
            return jSONObject;
        } catch (JSONException e2) {
            Logger.e(TAG, "e : " + e2.getMessage());
            return null;
        }
    }

    private static AliyunDownloadMediaInfo getInfoFromJson(JSONObject jSONObject) {
        AliyunDownloadMediaInfo aliyunDownloadMediaInfo = new AliyunDownloadMediaInfo();
        aliyunDownloadMediaInfo.setVid(jSONObject.optString("vid", ""));
        aliyunDownloadMediaInfo.setTitle(jSONObject.optString("title", ""));
        aliyunDownloadMediaInfo.setQuality(jSONObject.optString(DatabaseManager.QUALITY, ""));
        aliyunDownloadMediaInfo.setFormat(jSONObject.optString("format", ""));
        aliyunDownloadMediaInfo.setCoverUrl(jSONObject.optString("coverUrl", ""));
        aliyunDownloadMediaInfo.setDuration(jSONObject.optInt("duration", 0));
        aliyunDownloadMediaInfo.setSavePath(jSONObject.optString("savePath", ""));
        aliyunDownloadMediaInfo.setStatus(Status.valueOf(jSONObject.optString("status", "")));
        aliyunDownloadMediaInfo.setSize(jSONObject.optInt(DatabaseManager.SIZE, 0));
        aliyunDownloadMediaInfo.setProgress(jSONObject.optInt("progress", 0));
        aliyunDownloadMediaInfo.setDownloadIndex(jSONObject.optInt("dIndex", 0));
        aliyunDownloadMediaInfo.setEncripted(jSONObject.optInt("encript", 0));
        return aliyunDownloadMediaInfo;
    }

    public static List<AliyunDownloadMediaInfo> getInfosFromJson(String str) {
        JSONArray jSONArray;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            jSONArray = new JSONArray(str);
        } catch (JSONException e2) {
            Logger.d(TAG, " e..." + e2);
            jSONArray = null;
        }
        if (jSONArray == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int length = jSONArray.length();
        for (int i2 = 0; i2 < length; i2++) {
            try {
                arrayList.add(getInfoFromJson(jSONArray.getJSONObject(i2)));
            } catch (JSONException e3) {
                Logger.d(TAG, " e..." + e3);
            }
        }
        return arrayList;
    }

    public static String getJsonFromInfos(List<AliyunDownloadMediaInfo> list) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        if (list == null || list.isEmpty()) {
            return jSONArray.toString();
        }
        Iterator<AliyunDownloadMediaInfo> it = list.iterator();
        while (it.hasNext()) {
            JSONObject infoToJsonobj = formatInfoToJsonobj(it.next());
            if (infoToJsonobj != null) {
                jSONArray.put(infoToJsonobj);
            }
        }
        return jSONArray.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass() || TextUtils.isEmpty(this.mVid) || TextUtils.isEmpty(this.mQuality)) {
            return false;
        }
        AliyunDownloadMediaInfo aliyunDownloadMediaInfo = (AliyunDownloadMediaInfo) obj;
        return (TextUtils.isEmpty(this.mTvId) && TextUtils.isEmpty(aliyunDownloadMediaInfo.mTvId)) ? this.mVid.equals(aliyunDownloadMediaInfo.mVid) && this.mQuality.equals(aliyunDownloadMediaInfo.mQuality) : this.mVid.equals(aliyunDownloadMediaInfo.mVid);
    }

    public String getCoverUrl() {
        return this.mCoverUrl;
    }

    public int getDownloadIndex() {
        return this.mDownloadIndex;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public String getFormat() {
        return this.mFormat;
    }

    public int getNumber() {
        return this.number;
    }

    public int getProgress() {
        return this.mProgress;
    }

    public String getQuality() {
        return this.mQuality;
    }

    public int getQualityIndex() {
        return this.mQualityIndex;
    }

    public String getSavePath() {
        return this.mSavePath;
    }

    public long getSize() {
        return this.mSize;
    }

    public String getSizeStr() {
        int i2 = (int) (this.mSize / 1024.0f);
        if (i2 < 1024) {
            return i2 + "KB";
        }
        return (i2 / 1024.0f) + "MB";
    }

    public Status getStatus() {
        return this.mStatus;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public TrackInfo getTrackInfo() {
        return this.mTrackInfo;
    }

    public String getTvCoverUrl() {
        return this.mTvCoverUrl;
    }

    public String getTvId() {
        return this.mTvId;
    }

    public String getTvName() {
        return this.mTvName;
    }

    public String getVid() {
        return this.mVid;
    }

    public VidAuth getVidAuth() {
        return this.vidAuth;
    }

    public VidSts getVidSts() {
        return this.mVidSts;
    }

    public int getVidType() {
        return this.vidType;
    }

    public int getWatchNumber() {
        return this.watchNumber;
    }

    public int getWatched() {
        return this.mWatched;
    }

    public int getmFileHandleProgress() {
        return this.mFileHandleProgress;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.mVid, this.mQuality});
    }

    public int isEncripted() {
        return this.isEncripted;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setCoverUrl(String str) {
        this.mCoverUrl = str;
    }

    public void setDownloadIndex(int i2) {
        this.mDownloadIndex = i2;
    }

    public void setDuration(long j2) {
        this.mDuration = j2;
    }

    public void setEncripted(int i2) {
        this.isEncripted = i2;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMsg(String str) {
        this.errorMsg = str;
    }

    public void setFormat(String str) {
        this.mFormat = str;
    }

    public void setNumber(int i2) {
        this.number = i2;
    }

    public void setProgress(int i2) {
        this.mProgress = i2;
    }

    public void setQuality(String str) {
        this.mQuality = str;
    }

    public void setQualityIndex(int i2) {
        this.mQualityIndex = i2;
    }

    public void setSavePath(String str) {
        this.mSavePath = str;
    }

    public void setSelected(boolean z2) {
        this.isSelected = z2;
    }

    public void setSize(long j2) {
        this.mSize = j2;
    }

    public void setStatus(Status status) {
        this.mStatus = status;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }

    public void setTrackInfo(TrackInfo trackInfo) {
        this.mTrackInfo = trackInfo;
    }

    public void setTvCoverUrl(String str) {
        this.mTvCoverUrl = str;
    }

    public void setTvId(String str) {
        this.mTvId = str;
    }

    public void setTvName(String str) {
        this.mTvName = str;
    }

    public void setVid(String str) {
        this.mVid = str;
    }

    public void setVidAuth(VidAuth vidAuth) {
        this.vidAuth = vidAuth;
    }

    public void setVidSts(VidSts vidSts) {
        this.mVidSts = vidSts;
    }

    public void setVidType(int i2) {
        this.vidType = i2;
    }

    public void setWatchNumber(int i2) {
        this.watchNumber = i2;
    }

    public void setWatched(int i2) {
        this.mWatched = i2;
    }

    public void setmFileHandleProgress(int i2) {
        this.mFileHandleProgress = i2;
    }

    public AliyunDownloadMediaInfo() {
        this.mProgress = 0;
        this.mSavePath = null;
        this.mDownloadIndex = 0;
        this.isEncripted = 0;
        this.mFileHandleProgress = 0;
        this.number = 1;
        this.watchNumber = 0;
    }
}

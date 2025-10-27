package org.wrtca.record.model;

/* loaded from: classes9.dex */
public class MediaThemeObject {
    public String mFilterThemeName;
    public String mMVThemeName;
    public String mMusicThemeName;
    public boolean mOrgiMute;
    public String mSoundText;
    public String mSoundTextId;
    public String mSoundThemeName;
    public String mSpeedThemeName;
    public boolean mThemeMute;
    public String mWatermarkThemeName;

    public boolean isEmpty() {
        return "Empty".equals(this.mMVThemeName) && !this.mOrgiMute && isEmpty(this.mMusicThemeName, this.mWatermarkThemeName, this.mFilterThemeName, this.mSoundThemeName, this.mSpeedThemeName);
    }

    private boolean isEmpty(String... strArr) {
        for (String str : strArr) {
            if (!"Empty".equals(str)) {
                return false;
            }
        }
        return true;
    }
}
